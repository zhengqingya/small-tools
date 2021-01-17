# 项目部署


### 后端

```shell script
docker run -d -p 5000:5000 --restart=always --name spring-boot-code-generator -v E:\IT_zhengqing\code\me-workspace\最新代码生成器\code-api\docker\app.jar:/tmp/app.jar java:latest java -jar /tmp/app.jar
```

### 前端

```shell script
cnpm install

cnpm run build:stage
```

### knife4j
https://doc.xiaominfo.com/knife4j/#spring-boot%E5%8D%95%E6%9C%8D%E5%8A%A1%E6%9E%B6%E6%9E%84
