# Docker操作说明

### 使用示例命令

```shell
# 打包镜像 -f:指定Dockerfile文件路径
docker build -f Dockerfile --build-arg JAVA_OPTS="-XX:+UseG1GC -Xms64m -Xmx64m -Xmn16m -XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=100m -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=8 -Ddefault.client.encoding=UTF-8 -Dfile.encoding=UTF-8 -Duser.language=Zh -Duser.region=CN" --build-arg APP_NAME="demo" --build-arg APP_PORT="80" -t "registry.cn-hangzhou.aliyuncs.com/zhengqingya/demo:dev" . --no-cache

# 推送镜像
docker push registry.cn-hangzhou.aliyuncs.com/zhengqingya/demo:dev

# 拉取镜像
docker pull registry.cn-hangzhou.aliyuncs.com/zhengqingya/demo:dev

# 运行
docker run -d -p 80:80 -v /home/zhengqingya/demo.log:/home/demo.log --name demo registry.cn-hangzhou.aliyuncs.com/zhengqingya/demo:dev

# 删除旧容器
#docker ps -a | grep demo | grep dev | awk '{print $1}' | xargs -i docker stop {} | xargs -i docker rm {}
docker ps -a | grep demo | grep dev | awk '{print $1}' | xargs -I docker stop {} | xargs -I docker rm {}

# 删除旧镜像
docker images | grep -E demo | grep dev | awk '{print $3}' | uniq | xargs -I {} docker rmi --force {}
```

---

### registry.cn-hangzhou.aliyuncs.com/zhengqing/openjdk:8-jdk-alpine

###### Dockerfile

```shell
FROM openjdk:8-jdk-alpine

# 解决时差8小时问题
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 支持字体
RUN apk add --update ttf-dejavu fontconfig && rm -rf /var/cache/apk/*
```

###### 构建镜像

```shell
# 构建镜像 注：有点慢
docker build -t registry.cn-hangzhou.aliyuncs.com/zhengqing/openjdk:8-jdk-alpine . --no-cache
# 推送镜像
docker push registry.cn-hangzhou.aliyuncs.com/zhengqing/openjdk:8-jdk-alpine

# Dockerfile中引用新镜像
# FROM registry.cn-hangzhou.aliyuncs.com/zhengqing/openjdk:8-jdk-alpine
```
