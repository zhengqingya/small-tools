#!/bin/bash

# 服务名
SERVICE_NAME=$2
SERVICE_HOME='/Users/zhengqingya/IT_zhengqing/code/small-tools/small-tools-api'
APP_PROFILE=dev
APP_NACOS_SERVER_ADDR='127.0.0.1:8848'
JAVA_OPTS="-XX:+UseG1GC -Xms64m -Xmx64m -Xmn16m -XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=100m -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=8 -Dfile.encoding="UTF-8" -Ddefault.client.encoding="UTF-8" -Dfile.encoding="UTF-8" -Duser.language="Zh" -Duser.region="CN" -Dspring.profiles.active=${APP_PROFILE} -Dspring.cloud.nacos.discovery.server-addr=${APP_NACOS_SERVER_ADDR}"


# 停止
function stop(){
  echo "<-------------------------------------->"
  echo "[${SERVICE_NAME}] ... stop ..."
  # 查看该jar进程
  pid=`ps -ef|grep ${SERVICE_NAME}.jar|grep -v 'grep' | awk '{print $2}'`
  echo "[${SERVICE_NAME}] pid="${pid}
  # 存在则kill,不存在打印一下吧
  if [ "${pid}" ]; then
    kill -9 ${pid}
      # 检查kill是否成功
      if [ $? -eq 0 ]; then
          echo "[${SERVICE_NAME}] stop success"
      else
          echo "[${SERVICE_NAME}] stop fail"
      fi
  else
    echo "[${SERVICE_NAME}] 进程不存在"
  fi
}

# 打包 - common
function buildCommon(){
  echo "<-------------------------------------->"
  echo "[${SERVICE_NAME}] ... build-common ..."
  cd ${SERVICE_HOME}/common
  echo "当前路径:`pwd`"
  mvn clean install -Dmaven.test.skip=true
  echo "[${SERVICE_NAME}] build-common success"
}

# 打包
function build(){
#  buildCommon

  # 让mac的maven全局配置生效
  source ~/.bash_profile
  echo "<-------------------------------------->"
  echo "[${SERVICE_NAME}] ... build ..."

  # 判断`SERVICE_NAME`是否包含`gateway` 或者 等于`gateway-web`
  is_gateway = 0
  if [[ ${SERVICE_NAME} = *'gateway'* ]] || [[ ${SERVICE_NAME} = 'gateway-web' ]];then
    echo "网关特殊处理"
    is_gateway=1
    cd ${SERVICE_HOME}/gateway/${SERVICE_NAME}
  else
    echo "普通服务处理"
    is_gateway=0
    cd ${SERVICE_HOME}/service/${SERVICE_NAME}
  fi

  echo "当前服务是否为网关：${is_gateway}"

  echo "当前路径:`pwd`"

  mvn clean install -Dmaven.test.skip=true
  # 创建target目录存放jar包
  rm -rf ${SERVICE_HOME}/docker/target
  mkdir ${SERVICE_HOME}/docker/target

  # 拷贝jar
  if [[ ${is_gateway} = 1 ]];then
     cp ${SERVICE_HOME}/${SERVICE_NAME}/target/*.jar ${SERVICE_HOME}/docker/target/
  else
     cp ${SERVICE_HOME}/service/${SERVICE_NAME}/target/*.jar ${SERVICE_HOME}/docker/target/
  fi

  echo "[${SERVICE_NAME}] build success"
}

# 运行
function start(){
  build
  echo "<-------------------------------------->"
  echo "[${SERVICE_NAME}] ... start ..."
  cd ${SERVICE_HOME}/docker/target
  echo "当前路径:`pwd`"
  echo "启动命令: nohup java -jar ${JAVA_OPTS} ${SERVICE_NAME}.jar >> ${SERVICE_HOME}/logs/${SERVICE_NAME}.log  2>&1 &"
  nohup java -jar ${JAVA_OPTS} ${SERVICE_NAME}.jar >> ${SERVICE_HOME}/logs/${SERVICE_NAME}.log  2>&1 &
  if [ $? -eq 0 ]; then
    echo "[${SERVICE_NAME}] start success"
  else
    echo "[${SERVICE_NAME}] start fail"
  fi
}

# 重启
function restart(){
  echo "<-------------------------------------->"
  echo "[${SERVICE_NAME}] ... restart ..."
	stop
	start
}


# 检查参数个数
if [ $# -lt 2 ]; then
	echo -e "\033[41;37m 脚本参数使用: boot.sh start(启动)|restart(重启)|stop(停止) app(服务名) \033[0m"
	exit;
else
  echo "当前路径:`pwd`"
  echo "当前操作服务名：${SERVICE_NAME}"
fi

case $1 in
  "build")
		build
		;;
	"stop")
		stop
		;;
	"start")
		start
		;;
	"restart")
		restart
		;;
	*)
	  echo -e "\033[41;37m 提示:不支持参数 命令 -> $1 \033[0m"
	  ;;
esac
