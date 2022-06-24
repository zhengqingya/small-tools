package com.zhengqing.gateway.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.SecurityConstant;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * <p> 网关自定义鉴权管理器 </p>
 *
 * @author zhengqingya
 * @description 实际鉴权处
 * @date 2022/6/11 4:34 PM
 */
@Slf4j
@Component
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        if (request.getMethod() == HttpMethod.OPTIONS) {
            // 预检请求放行
            return Mono.just(new AuthorizationDecision(true));
        }
        String method = request.getMethodValue();
        String path = request.getURI().getPath();
        // "GET:/web/api/user/*"
        String restfulPath = method + ":" + path;

        String token = request.getHeaders().getFirst(SecurityConstant.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token) || !StrUtil.startWithIgnoreCase(token, SecurityConstant.JWT_PREFIX)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        if (path.contains(ServiceConstant.SERVICE_API_PREFIX_MINI)) {
            // C端请求需认证不需鉴权 -- 放行
            return Mono.just(new AuthorizationDecision(true));
        }

        /**
         * URL鉴权
         * [URL-角色集合]
         * [{'key':'GET:/web/api/user/*','value':['ADMIN','TEST']},...]
         */
        Map<Object, Object> urlPermReRoleMap = RedisUtil.hGetAll(SecurityConstant.URL_PERM_RE_ROLES);

        // 根据请求路径获取有访问权限的角色列表
        List<String> authorizedRoleList = Lists.newLinkedList();
        // 是否需要鉴权，默认未设置拦截规则不需鉴权
        boolean isCheck = false;
        PathMatcher pathMatcher = new AntPathMatcher();
        for (Map.Entry<Object, Object> permRoles : urlPermReRoleMap.entrySet()) {
            String perm = (String) permRoles.getKey();
            if (pathMatcher.match(perm, restfulPath)) {
                List<String> roleCodeList = JSON.parseArray((String) permRoles.getValue(), String.class);
                authorizedRoleList.addAll(roleCodeList);
                isCheck = true;
            }
        }

        if (!isCheck) {
            // 没有设置拦截规则放行
            return Mono.just(new AuthorizationDecision(true));
        }

        // 判断JWT中携带的用户角色是否有权限访问
        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    /**
                     * B端校验角色取之前授权在auth服务存入的角色信息值
                     * {@link com.zhengqing.auth.security.core.userdetails.sys.SysUserDetails#SysUserDetails(com.zhengqing.system.model.vo.SysUserPermVO)}
                     * 也可以通过 {@link com.zhengqing.common.core.util.JwtUtil#parse(String)} 解析token查看其中的角色权限
                     * {"exp":1655219213,"user_name":"郑清","authorities":["persion","super_admin"],"jti":"091b6ad0-1ca3-4ad9-a756-f55f65979e3a","client_id":"demo","scope":["all"]}
                     */
                    // ROLE_ADMIN 移除前缀 ROLE_ 得到用户的角色编码 ADMIN
                    String roleCode = StrUtil.removePrefix(authority, SecurityConstant.AUTHORITY_PREFIX);
                    return CollectionUtil.isNotEmpty(authorizedRoleList) && authorizedRoleList.contains(roleCode);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }

}
