#!/bin/bash


# 设置变量
APP_PROFILE='prod'
APP_NAME='small-tools-api'
APP_IMAGE_NAME='registry.cn-hangzhou.aliyuncs.com/zhengqing/'${APP_NAME}
APP_IMAGE=${APP_IMAGE_NAME}':'${APP_PROFILE}
APP_PORT_GATEWAY='1218'
APP_PORT_SYSTEM='20010'
#APP_PORT_BASIC='20020'
APP_PORT_TOOL='20030'
APP_PORT_REMOTE='50001'
APP_NACOS_SERVER_ADDR='www.zhengqingya.com:8848'



echo '环境：'${APP_PROFILE}
echo '操作容器名：'${APP_NAME}
echo '操作镜像：'${APP_IMAGE}
echo 'nacos服务地址：'${APP_NACOS_SERVER_ADDR}


deleteContainer(){
  # 判断是否容器存在
  docker ps -a | grep ${APP_NAME} &> /dev/null
  # 如果存在，关闭并删除该容器
  if [ $? -eq 0 ]
  then
      echo '开始停止并移除容器：'${APP_NAME}
      docker stop ${APP_NAME}
      docker rm ${APP_NAME}
  else
      echo '不存在容器：'${APP_NAME}
  fi
}


deleteImage(){
  # 判断是否镜像存在
  docker images | grep ${APP_IMAGE_NAME} &> /dev/null
  # 如果存在，删除该镜像
  if [ $? -eq 0 ]
  then
      echo '开始移除镜像：'${APP_IMAGE_NAME}
      docker rmi $(docker images | grep ${APP_IMAGE_NAME} | awk "{print $3}")
  else
      echo '不存在镜像：'${APP_IMAGE_NAME}
  fi
}


initEnv(){
  echo '初始化环境：'
  find ./docker -name '*.jar' -type f -print -exec rm -rf {} \;
  mvn clean
  mvn install
}


buildImage(){
  # 构建Docker镜像
  cd docker
  echo '当前路径：'
  pwd
  docker build -t ${APP_IMAGE} . --build-arg APP_PROFILE=${APP_PROFILE} --build-arg APP_NACOS_SERVER_ADDR=${APP_NACOS_SERVER_ADDR}  --no-cache
}


pushImage(){
  # push镜像
  docker push ${APP_IMAGE}
}


run(){
  # 启动容器
  echo '启动容器Start...'
  docker run -d -e PROFILE=${APP_PROFILE} \
  -p ${APP_PORT_GATEWAY}:${APP_PORT_GATEWAY} \
  -p ${APP_PORT_SYSTEM}:${APP_PORT_SYSTEM} \
  -p ${APP_PORT_TOOL}:${APP_PORT_TOOL} \
  --restart=always --name ${APP_NAME} ${APP_IMAGE}
  echo '启动容器End...'
}


openPort(){
  # 开启所需端口
  firewall-cmd --zone=public --add-port=80/tcp --permanent
  firewall-cmd --zone=public --add-port=${APP_PORT_GATEWAY}/tcp --permanent
  firewall-cmd --zone=public --add-port=${APP_PORT_SYSTEM}/tcp --permanent
  firewall-cmd --zone=public --add-port=${APP_PORT_TOOL}/tcp --permanent
  #	重启
	service firewalld restart
}


# 执行
deleteContainer
deleteImage
initEnv
buildImage
pushImage
#openPort
#run
