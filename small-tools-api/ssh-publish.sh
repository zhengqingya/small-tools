#!/bin/bash

####################################
# @description 远程服务器-部署脚本
# @params $? => 代表上一个命令执行后的退出状态: 0->成功,1->失败
# @example => sh ssh-publish.sh
# @author zhengqingya
# @date 2021/10/6 12:12 上午
####################################

# 在执行过程中若遇到使用了未定义的变量或命令返回值为非零，将直接报错退出
set -eu

# 检查参数个数
if [ "${#}" -ne 2 ]; then
	echo "\033[41;37m 脚本参数不合法！！！  \033[0m"
	exit
fi

# 当前时间
CURRENT_TIME=`date +"%Y-%m-%d %H:%M:%S"`

# 执行脚本时传过来的参数
DOCKER_REGISTRY_AUTH_CMD=${1}
PROJECT_SERVICE_LOG_HOME=${2}
current_app_name=${3}
app_docker_image_name=${4}
app_docker_image_tag=${5}
app_docker_image=${6}
ETH_VALUE=${7}
current_app_port=${8}
JAVA_OPTS=${9}

# 本机内网ip
INET_IP=`ifconfig ${ETH_VALUE} | grep inet | grep -v inet6 | awk '{print $2}'`


# 远程服务器下依次执行如下命令

# ----------------------------------------------------------------------------------------------------------------------
# 初始化
function init() {
  # 镜像仓库认证
  ${DOCKER_REGISTRY_AUTH_CMD}

  # 创建日志文件
  mkdir -p ${PROJECT_SERVICE_LOG_HOME}
  cd ${PROJECT_SERVICE_LOG_HOME}
  touch ${current_app_name}.log
}
init

# ----------------------------------------------------------------------------------------------------------------------
# 判断当前端口是否被占用，没被占用返回0，反之1
function Listening {
   TCPListeningnum=`netstat -an | grep ":$1 " | awk '$1 == "tcp" && $NF == "LISTEN" {print $0}' | wc -l`
   UDPListeningnum=`netstat -an | grep ":$1 " | awk '$1 == "udp" && $NF == "0.0.0.0:*" {print $0}' | wc -l`
   (( Listeningnum = TCPListeningnum + UDPListeningnum ))
   if [ $Listeningnum == 0 ]; then
       echo "0"
   else
       echo "1"
   fi
}

# 获取随机端口
function get_random_port {
  # 随机端口值
  random_port=0
  temp_num=0
  while [ ${random_port} == 0 ]; do
     # 指定区间随机数
     temp_num=`shuf -i ${1}-${2} -n1`
  #       echo "temp_num=${temp_num}"
     if [ `Listening ${temp_num}` == 0 ] ; then
        random_port=${temp_num}
     fi
  done
  echo "${random_port}"
}

# 获取sentinel随机端口
SENTINEL_PORT=`get_random_port 30100 30200`
echo "${CURRENT_TIME} sentinel随机端口号: ${SENTINEL_PORT}" >> ${PROJECT_SERVICE_LOG_HOME}/run.log

# ----------------------------------------------------------------------------------------------------------------------
# 运行程序
function run() {

  # 删除旧容器
  docker ps -a | grep ${app_docker_image_name} | grep ${app_docker_image_tag} | awk '{print $1}' | xargs -i docker stop {} | xargs -i docker rm {}

  # 删除旧镜像
  docker images | grep -E ${app_docker_image_name} | grep ${app_docker_image_tag}| awk '{print $3}' | uniq | xargs -I {} docker rmi --force {}

  # 拉取新镜像
  docker pull ${app_docker_image}

  # 运行日志记录
  echo "****** ${CURRENT_TIME} docker run -d -p ${current_app_port}:${current_app_port} -p ${SENTINEL_PORT}:${SENTINEL_PORT} \
  -e JAVA_OPTS="${JAVA_OPTS} -Dspring.cloud.nacos.discovery.ip=${INET_IP} \
  -Dspring.cloud.sentinel.transport.client-ip=${INET_IP} -Dspring.cloud.sentinel.transport.port=${SENTINEL_PORT}" \
  -v ${PROJECT_SERVICE_LOG_HOME}/${current_app_name}.log:/home/${current_app_name}.log \
  --name ${current_app_name} ${app_docker_image}" >> ${PROJECT_SERVICE_LOG_HOME}/run.log

  # 运行
  docker run -d -p ${current_app_port}:${current_app_port} -p ${SENTINEL_PORT}:${SENTINEL_PORT} \
  -e JAVA_OPTS="${JAVA_OPTS} -Dspring.cloud.nacos.discovery.ip=${INET_IP} \
  -Dspring.cloud.sentinel.transport.client-ip=${INET_IP} -Dspring.cloud.sentinel.transport.port=${SENTINEL_PORT}" \
  -v ${PROJECT_SERVICE_LOG_HOME}/${current_app_name}.log:/home/${current_app_name}.log \
  --name ${current_app_name} ${app_docker_image}

}
run
