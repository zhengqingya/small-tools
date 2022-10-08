# 环境部署

> 组件环境部署可参考 https://gitee.com/zhengqingya/docker-compose

```shell
# 当前目录下所有文件赋予权限(读、写、执行)
chmod -R 777 ./redis
chmod -R 777 ./rabbitmq
chmod -R 777 ./elk

# 组件部署
# tips: [ rabbitmq: 如果之前有安装过，需要清除浏览器缓存和删除rabbitmq相关的存储数据(如:这里映射到宿主机的data数据目录)，再重装，否则会出现一定问题！ ]
#       [ `mysql5.7/docker-entrypoint-initdb.d`下包含了一些其它组件需要的初始化SQL脚本 ]
docker-compose -f ./docker-compose.yml -p small-tools up -d mysql nacos redis rabbitmq sentinel xxl-job-admin
# tips: 若nacos启动失败可尝试等mysql中的sql脚本初始化成功后再重启nacos
docker-compose restart nacos

# 其它组件:根据自己的条件去启动 ---------------------------------------------

# seata -- tips: 需修改下`docker-compose.yml`中的`SEATA_IP`值
docker-compose -p small-tools up -d seata

# canal
docker-compose -p small-tools up -d canal_admin canal_server

# ELK
docker-compose -p small-tools up -d elasticsearch kibana logstash
# 若运行之后启动日志再次报相关权限问题，再次给新产生的文件赋予权限
chmod -R 777 ./elk
```

---

> tips：下面是各个组件相关的一些配置信息

### MySQL

连接信息

```shell
ip: 127.0.0.1
port: 3306
username: root
password: root
```

### Nacos

> tips：如果nacos启动失败可能是mysql容器的初始化脚本`nacos_config.sql`未执行完，可等1分钟再重试。

访问地址：[`http://127.0.0.1:8848/nacos`](http://127.0.0.1:8848/nacos)
登录账号密码默认：`nacos/nacos`

### Redis

```shell
# 连接redis
docker exec -it redis redis-cli -a 123456  # 密码为123456
```

### RabbitMQ

```shell
# 进入容器
docker exec -it rabbitmq /bin/bash
# 启用延时插件
rabbitmq-plugins enable rabbitmq_delayed_message_exchange
# 查看已安装插件
rabbitmq-plugins list
```

web管理端：[`http://127.0.0.1:15672`](http://127.0.0.1:15672)
登录账号密码：`admin/admin`

### Sentinel

访问地址：[`http://127.0.0.1:8858`](http://127.0.0.1:8858)
登录账号密码：`sentinel/sentinel`

### XXL-JOB - 分布式任务调度平台

访问地址：[`http://127.0.0.1:9003/xxl-job-admin`](http://127.0.0.1:9003/xxl-job-admin)
默认登录账号密码：`admin/123456`

### Seata - 分布式事务

访问seata控制台：[`http://127.0.0.1:7091`](http://127.0.0.1:7091)
登录账号密码默认：`seata/seata`

![seata-login.png](images/seata-login.png)

![seata-TransactionInfo.png](images/seata-TransactionInfo.png)

![seata-GlobalLockInfo.png](images/seata-GlobalLockInfo.png)

nacos服务注册

![seata-nacos.png](images/seata-nacos.png)

### canal

阿里巴巴 MySQL binlog 增量订阅&消费组件

访问地址：[`http://127.0.0.1:8089`](http://127.0.0.1:8089)
默认登录账号密码：`admin/123456`

### ELK

1. ES访问地址：[`http://127.0.0.1:9200`](http://127.0.0.1:9200)
   默认账号密码：`elastic/123456`
2. kibana访问地址：[`http://127.0.0.1:5601`](http://127.0.0.1:5601)
   默认账号密码：`elastic/123456`

#### 设置ES密码

```shell
# 进入容器
docker exec -it elasticsearch /bin/bash
# 设置密码-随机生成密码
# elasticsearch-setup-passwords auto
# 设置密码-手动设置密码
elasticsearch-setup-passwords interactive
```
