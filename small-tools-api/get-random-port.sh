#!/bin/bash

####################################
# @description 获取一个指定区间内未被占用的随机端口号
# @params $? => 代表上一个命令执行后的退出状态: 0->成功,1->失败
#         ${1} => 端口区间最小值
#         ${2} => 端口区间最大值
# @example => sh get-random-port.sh
# @author zhengqingya
# @date 2021/10/5 4:32 下午
####################################

# 在执行过程中若遇到使用了未定义的变量或命令返回值为非零，将直接报错退出
set -eu

# 检查参数个数
if [ "${#}" -ne 2 ]; then
	echo "\033[41;37m 脚本使用示例: sh get-random-port.sh 1 1000  \033[0m"
	exit
fi

# 校验区间值
if [ ${1} -ge ${2} ]; then
	echo "端口区间最小值不能大于等于最大值！"
	exit
fi

PORT=0

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
   temp_num=0
   while [ ${PORT} == 0 ]; do
       # 指定区间随机数
       temp_num=`shuf -i ${1}-${2} -n1`
#       echo "temp_num=${temp_num}"
       if [ `Listening ${temp_num}` == 0 ] ; then
          PORT=${temp_num}
       fi
   done
   echo "${PORT}"
}

get_random_port ${1} ${2}
