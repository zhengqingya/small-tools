package com.zhengqing.common.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

/**
 * <p>
 * 全局常用变量
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2019/10/12 14:47
 */
public interface AppConstant {

    /**
     * 解决返回json字符串中文乱码问题
     */
    String CONTENT_TYPE = "application/json;charset=utf-8";

    /**
     * db多数据源数据
     */
    String DATASOURCE_MASTER = "master";
    String DATASOURCE_TEST = "db-test";

    /**
     * 实体类名
     */
    String ENTITY_NAME = "${entity}";

    /**
     * 接口url
     */
    Map<String, String> URL_MAPPING_MAP = new HashMap<>();

    /**
     * 密码加密相关
     */
    String DEFAULT_PASSWORD = "123456";
    String SALT = "zhengqing";
    int HASH_ITERATIONS = 1;

    /**
     * 用于登录密码加密解密
     */
    String DES_KEY = "deskeyzq";

    /**
     * 请求头类型： application/x-www-form-urlencoded ： form表单格式 application/json ： json格式
     */
    String REQUEST_HEADERS_CONTENT_TYPE = "application/json";

    /**
     * 系统超级管理员id
     */
    Integer SYSTEM_SUPER_ADMIN_USER_ID = 1;

    /**
     * 登录者角色
     */
    String ROLE_LOGIN = "role_login";

    /**
     * 所有第一级菜单父类id
     */
    Integer MENU_PARENT_ID = 0;

    /**
     * 所有第一级项目关联包父类id、父包名
     */
    Integer PROJECT_RE_PACKAGE_PARENT_ID = 0;
    String PROJECT_RE_PACKAGE_PARENT_NAME = "com.zhengqing.demo";

    /**
     * 系统权限相关
     */
    String WRONG_PASSWORD = "密码错误!";
    String WRONG_OLD_PASSWORD = "原密码错误!";
    String NO_USERNAME = "用户名不存在!";
    String NO_PERMISSION = "请联系管理员为其分配角色!";
    String NO_TOKEN = "TOKEN已过期请重新登录!";
    String WRONG_USERNAME_PASSWORD = "用户名或密码错误!";

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ 文件系列 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    /**
     * 系统分隔符
     */
    String SEPARATOR_SPRIT = "/";
    /**
     * 分隔符 - 逗号
     */
    String SEPARATOR_COMMA = ",";

    /**
     * 获取项目根目录
     */
    String PROJECT_ROOT_DIRECTORY = System.getProperty("user.dir").replaceAll("\\\\", SEPARATOR_SPRIT);

    String IMG_DOMAIN = "";

    /**
     * 导入导出文件相关
     */
    String DEFAULT_REPORT_IMPORT_FOLDER = "/report/import";
    String DEFAULT_REPORT_EXPORT_FOLDER = "/report/export";

    /**
     * csdn域名前缀
     */
    String CSDN_DOMAIN_PREFIX = "https://blog.csdn.net/";
    /**
     * csdn博客临时存储路径
     */
    String FILE_PATH_CSDN_BLOG_IMPORT_SRC = PROJECT_ROOT_DIRECTORY + "/tmp/import/blog";
    String FILE_PATH_CSDN_BLOG_IMPORT_ZIP = PROJECT_ROOT_DIRECTORY + "/tmp/import/blog.zip";
    String FILE_PATH_CSDN_BLOG_EXPORT_SRC = PROJECT_ROOT_DIRECTORY + "/tmp/export/blog";
    String FILE_PATH_CSDN_BLOG_EXPORT_ZIP = PROJECT_ROOT_DIRECTORY + "/tmp/export/blog.zip";
    String FILE_PATH_CSDN_BLOG_EXPORT_EXCEL = PROJECT_ROOT_DIRECTORY + "/tmp/export/blog/excel.xls";
    String FILE_PATH_CSDN_BLOG_EXPORT_HTML =
        PROJECT_ROOT_DIRECTORY + "/tmp/export/blog/html/" + System.currentTimeMillis() + "/";
    /**
     * 代码生成临时存储路径
     */
    String FILE_PATH_CODE_GENERATOR_DATA_PATH = PROJECT_ROOT_DIRECTORY + "/tmp/upload";
    String FILE_PATH_CODE_GENERATOR_SRC_CODE = PROJECT_ROOT_DIRECTORY + "/tmp/upload/generate_code";
    String FILE_PATH_CODE_GENERATOR_TEMPLATE_CODE = PROJECT_ROOT_DIRECTORY + "/tmp/upload/template_code";

    String FILE_PATH_CODE_GENERATOR_FILE_NAME_DATA =
        PROJECT_ROOT_DIRECTORY + "/tmp/upload/handle_generate_file_name_data";
    String FILE_PATH_CODE_GENERATOR_ZIP = PROJECT_ROOT_DIRECTORY + "/tmp/upload/code.zip";

    /**
     * 数据库导出word文档路径
     */
    String FILE_PATH_DB_WORD = PROJECT_ROOT_DIRECTORY + "/tmp/db/数据库信息.doc";

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ redis缓存系列 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    /**
     * 系统缓存
     */
    String CACHE_SYS_MENU_TREE = "SYS_MENU_TREE";
    String CACHE_SYS_DICT_PREFIX = "SYS_DICT_";
    /**
     * 个人缓存
     */
    String CACHE_SYS_USER_INFO_PREFIX = "SYS_USER_INFO_";
    String CACHE_SYS_PERMISSION_PREFIX = "SYS_PERMISSION_";

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ 业务系列 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    /**
     * 当前页、每页显示数量
     */
    String PAGE_NUM = "pageNum";
    String PAGE_SIZE = "pageSize";
    /**
     * 用户ID、用户名、消息上下文Key 【注：key名称一定不要和前端请求参数中的属性名一样，否则会拿不到真正的值！！！】
     */
    String CONTEXT_KEY_USER_ID = "small_tools_user_id";
    String CONTEXT_KEY_USERNAME = "small_tools_username";
    String CONTEXT_KEY_MESSAGE_LIST = "small_tools_message";

    Integer DEFAULT_CONTEXT_KEY_USER_ID = 0;
    String DEFAULT_CONTEXT_KEY_USERNAME = "未知";

    /**
     * 请求头 - token
     */
    String REQUEST_HEADER_TOKEN = "ZQ-X-TOKEN";
    /**
     * 缓存默认过期时间 - 24小时
     */
    Long DEFAULT_EXPIRES_TIME = 24 * 60 * 60 * 1000L;

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ rpc服务 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    String BASE_PACKAGES = "com.zhengqing";
    String APPLICATION_NAME_SYSTEM = "system";
    String APPLICATION_NAME_BASIC = "basic";

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ rpc服务 ↓↓↓↓↓↓ ============================
    // ===============================================================================

    /**
     * rpc服务调用不需要封装返回值的api
     */
    List<String> RPC_CLIENT_URL_LIST = Lists.newArrayList("/hello", "/index", "/rpc/client");

    // ===============================================================================
    // ============================ ↓↓↓↓↓↓ 其它 ↓↓↓↓↓↓ ============================
    // ===============================================================================

}
