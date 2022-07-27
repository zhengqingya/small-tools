#!/bin/bash


# 设置变量
APP_PROFILE='dev'
APP_NAME='small-tools-web'
APP_IMAGE_NAME='registry.cn-hangzhou.aliyuncs.com/zhengqing/'${APP_NAME}
APP_IMAGE=${APP_IMAGE_NAME}':'${APP_PROFILE}
APP_PORT='88'



echo '环境：'${APP_PROFILE}
echo '操作容器名：'${APP_NAME}
echo '操作镜像：'${APP_IMAGE}


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
  # 环境准备
  echo '当前路径：'
  pwd
  cnpm install
  cnpm run build:${APP_PROFILE}
}

buildImage(){
  # 构建Docker镜像
  echo '当前路径：'
  pwd
  docker build -t ${APP_IMAGE} . --no-cache
}


pushImage(){
  # push镜像
  docker push ${APP_IMAGE}
}


run(){
  # 启动容器
  echo '启动容器Start...'
  docker run -d \
  -p ${APP_PORT}:80 \
  --restart=always --name ${APP_NAME} ${APP_IMAGE}
  echo '启动容器End...'
}


openPort(){
  # 开启所需端口
  firewall-cmd --zone=public --add-port=${APP_PORT}/tcp --permanent
  #	重启
	service firewalld restart
}


# 执行shell函数
deleteContainer
deleteImage
initEnv
buildImage
#pushImage
run
