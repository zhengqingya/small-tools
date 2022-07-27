@[TOC](文章目录)

### 前言

封装的统一响应体格式如下：

```json
{
  "code": 200,
  "msg": "SUCCESS",
  "data": [
    "hello world"
  ]
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/f43ba97f3723424ba52d09377af24d00.png)

> 具体可参考源码：[https://gitee.com/zhengqingya/small-tools](https://gitee.com/zhengqingya/small-tools)

### 实现

###### 1、封装返回值处理类

```java
public class MyHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler returnValueHandler;

    public MyHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler returnValueHandler) {
        this.returnValueHandler = returnValueHandler;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return returnValueHandler.supportsReturnType(methodParameter);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        // 判断外层是否由ApiResult包裹
        if (returnValue instanceof ApiResult) {
            returnValueHandler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
            return;
        }
        // 判断该api是否需要是否处理返回值
        String servletPath = nativeRequest.getServletPath();
        boolean ifHandleReturnValue = true;
        for (String api : AppConstant.RETURN_VALUE_HANDLER_EXCLUDE_API_LIST) {
            if (servletPath.contains(api)) {
                ifHandleReturnValue = false;
                break;
            }
        }
        returnValueHandler.handleReturnValue(ifHandleReturnValue ? ApiResult.ok(returnValue) : returnValue, returnType, mavContainer, webRequest);
    }

}
```

###### 2、配置自定义Handler处理返回值

```java
@Configuration
public class ReturnValueConfig implements InitializingBean {

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> unmodifiableList = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> list = new ArrayList<>(unmodifiableList.size());
        for (HandlerMethodReturnValueHandler returnValueHandler : unmodifiableList) {
            if (returnValueHandler instanceof RequestResponseBodyMethodProcessor) {
                list.add(new MyHandlerMethodReturnValueHandler(returnValueHandler));
            } else {
                list.add(returnValueHandler);
            }
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(list);
    }

}
```

### api

在我们Controller层的api直接返回数据，然后会经过上面的配置统一处理响应体，由`ApiResult`包裹
这种全局统一响应配置好，小伙子们又可以happy了 `^_^`
![在这里插入图片描述](https://img-blog.csdnimg.cn/04ed6ce79494407586448c22120c820f.png)


---

> 今日分享语句：
> 让我们将事前的忧虑，换为事前的思考和计划吧！
