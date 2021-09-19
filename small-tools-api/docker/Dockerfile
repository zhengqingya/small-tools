#FROM openjdk:8-jre-alpine   【 注：jre中并没有携带工具文件，但arthas需要依赖lib包和bin包里面的包和工具，arthas需要jps工具和lib包里的内容 具体参考：https://www.cnblogs.com/sky-chen/p/9887777.html 】
#FROM openjdk:8-jdk-alpine
#RUN apk add --update ttf-dejavu fontconfig && rm -rf /var/cache/apk/*
# 注：此镜像支持字体！
FROM registry.cn-hangzhou.aliyuncs.com/zhengqing/openjdk:8-jdk-alpine-font


# 维护者信息
MAINTAINER zhengqingya

# 传参数据
ARG APP_PROFILE=prod
ARG APP_NACOS_SERVER_ADDR=www.zhengqingya.com:8848

# 设置环境变量
ENV APP_SLEEP 3
ENV APP_PROFILE ${APP_PROFILE}
ENV APP_NACOS_SERVER_ADDR ${APP_NACOS_SERVER_ADDR}
ENV JAVA_OPTS -XX:+UseG1GC -Xms64m -Xmx64m -Xmn16m -XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=100m -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=8 -Ddefault.client.encoding="UTF-8" -Dfile.encoding="UTF-8" -Duser.language="Zh" -Duser.region="CN" -Dspring.profiles.active=${APP_PROFILE} -Dspring.cloud.nacos.discovery.server-addr=${APP_NACOS_SERVER_ADDR}
# 远程调试参数： -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=50001


CMD echo "****** Start... ******"
CMD echo "****** APP_PROFILE: ${APP_PROFILE} ******"
CMD echo "****** APP_NACOS_SERVER_ADDR: ${APP_NACOS_SERVER_ADDR} ******"

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

ADD *.jar /
RUN sh -c 'touch /'

VOLUME /tmp

# 对外暴漏的端口号: gateway、system、basic、tool
# [注：EXPOSE指令只是声明容器运行时提供的服务端口，给读者看有哪些端口，在运行时只会开启程序自身的端口！！]
EXPOSE 1218
EXPOSE 20010
#EXPOSE 20020
EXPOSE 20030


CMD echo "gateway will start in ${APP_SLEEP}s..." && sleep ${APP_SLEEP} && nohup java ${JAVA_OPTS} -jar gateway.jar & \
    echo "system will start in ${APP_SLEEP}s..." && sleep ${APP_SLEEP} && /bin/sh -c set -e && nohup java ${JAVA_OPTS} -jar system.jar & \
#    echo "basic will start in ${APP_SLEEP}s..." && sleep ${APP_SLEEP} && /bin/sh -c set -e && nohup java ${JAVA_OPTS} -jar basic.jar & \
    echo "tool will start in ${APP_SLEEP}s..." && sleep ${APP_SLEEP} && /bin/sh -c set -e && java ${JAVA_OPTS} -jar tool.jar
