#!/bin/bash

####################################
# @description 服务运行脚本
# @params $? => 代表上一个命令执行后的退出状态: 0->成功,1->失败
#         $1 => 脚本第1个参数
#         $2 => 脚本第2个参数
# @example => sh small-tools-api-run-local.sh start demo
# @author zhengqingya
# @date 2021/7/17 8:39 下午
####################################


# 在执行过程中若遇到使用了未定义的变量或命令返回值为非零，将直接报错退出
set -eu

# 检查参数个数
if [ "${#}" -lt 2 ]; then
	echo "\033[41;37m 脚本使用示例: sh small-tools-api-run-local.sh start|stop|restart app \033[0m"
	exit
fi

# 获取脚本第一个参数
APP_OPT=${1}
# 端口
APP_PORT=10000
# 程序根目录
APP_HOME='/Users/zhengqingya/IT_zhengqing/code/small-tools/small-tools-api'
# 名称
APP_NAME=${2}
# jar名 | war名
APP_JAR=${APP_NAME}.jar
# 程序jar目录
APP_JAR_HOME="${APP_HOME}/target"
# 日志名
APP_LOG_NAME=${APP_NAME}
# 日志根目录
APP_LOG_HOME="${APP_HOME}/logs"
# 程序运行参数
APP_PROFILE=dev
APP_NACOS_SERVER_ADDR='127.0.0.1:8848'
JAVA_OPTS="-XX:+UseG1GC -Xms64m -Xmx64m -Xmn16m -XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=100m -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=8 -Dfile.encoding="UTF-8" -Ddefault.client.encoding="UTF-8" -Dfile.encoding="UTF-8" -Duser.language="Zh" -Duser.region="CN" -Dspring.profiles.active=${APP_PROFILE} -Dspring.cloud.nacos.discovery.server-addr=${APP_NACOS_SERVER_ADDR}"


echo "本次操作服务名：[${APP_NAME}]"
echo "本次操作选择：[${APP_OPT}]"

# 判断jar目录是否存在
if [ -d "${APP_JAR_HOME}" ];
then
    echo "程序jar目录: ${APP_JAR_HOME}"
else
    echo "程序jar目录不存在，手动创建一下吧"
    # 创建目录存放jar包
    mkdir ${APP_JAR_HOME}
fi

# 判断日志目录是否存在
if [ -d "${APP_LOG_HOME}" ];
then
    echo "程序日志目录: ${APP_LOG_HOME}"
else
    echo "程序日志目录不存在，手动创建一下吧"
    # 创建日志目录
    mkdir ${APP_LOG_HOME}
fi


# 停止
function stop(){
  echo "<-------------------------------------->"
  echo "[${APP_NAME}] ... stop ..."
  # 查看该jar进程
  pid=`ps -ef | grep ${APP_JAR} | grep -v 'grep' | awk '{print $2}'`
  echo "[${APP_NAME}] pid="${pid}
  # 存在则kill,不存在打印一下吧
  if [ "${pid}" ]; then
    kill -9 ${pid}
      # 检查kill是否成功
      if [ "$?" -eq 0 ]; then
          echo "[${APP_NAME}] stop success"
      else
          echo "[${APP_NAME}] stop fail"
      fi
  else
    echo "[${APP_NAME}] 进程不存在"
  fi
}


# 打包 - common
function buildCommon(){
  echo "<-------------------------------------->"
  echo "[${APP_NAME}] ... build common ..."
  cd ${APP_HOME}/common
  echo "当前路径:`pwd`"
  mvn clean install -Dmaven.test.skip=true
  echo "[${APP_NAME}] build common success"
}

# 打包
function build(){
  buildCommon

  echo "<-------------------------------------->"
  echo "[${APP_NAME}] ... build ..."

  # 判断`APP_NAME`是否包含`gateway` 或者 等于`gateway-web`
  is_gateway=0
  if [[ ${APP_NAME} = *'gateway'* ]] || [[ ${APP_NAME} = 'gateway-web' ]];then
    echo "网关特殊处理"
    is_gateway=1
    cd ${APP_HOME}/gateway/${APP_NAME}
  else
    echo "普通服务处理"
    is_gateway=0
    cd ${APP_HOME}/service/${APP_NAME}
  fi

  echo "当前服务是否为网关：${is_gateway}"
  echo "当前路径:`pwd`"

  # 打包
  mvn clean install -Dmaven.test.skip=true

  # 拷贝jar
  if [[ ${is_gateway} = 1 ]];then
     cp ${APP_HOME}/${APP_NAME}/target/*.jar ${APP_JAR_HOME}/
  else
     cp ${APP_HOME}/service/${APP_NAME}/target/*.jar ${APP_JAR_HOME}/
  fi

  echo "[${APP_NAME}] build success"
}


# 运行
function start(){
  build
  echo "<-------------------------------------->"
  echo "[${APP_NAME}] ... start ..."
  cd ${APP_JAR_HOME}
  echo "当前路径:`pwd`"
  # 赋予可读可写可执行权限
  chmod 777 ${APP_JAR}
  echo "启动命令: nohup java -jar ${APP_JAR} ${JAVA_OPTS} >> ${APP_LOG_HOME}/${APP_LOG_NAME}.log 2>&1 &"
  nohup java -jar ${APP_JAR} ${JAVA_OPTS} >> ${APP_LOG_HOME}/${APP_LOG_NAME}.log 2>&1 &
  if [ "$?" -eq 0 ]; then
    echo "[${APP_NAME}] start success"
  else
    echo "[${APP_NAME}] start fail"
  fi
}


# 重启
function restart(){
  echo "<-------------------------------------->"
  echo "[${APP_NAME}] ... restart ..."
	stop
	start
}


# 多分支条件判断执行参数
case "${APP_OPT}" in
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
	echo "\033[41;37m 提示:不支持参数 命令 -> ${APP_OPT} \033[0m"
	;;
esac
