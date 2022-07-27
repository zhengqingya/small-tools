> 组件环境部署可参考 https://gitee.com/zhengqingya/docker-compose

### MySQL

```shell
docker-compose -f ./mysql/docker-compose-mysql5.7.yml -p mysql5.7 up -d
```

连接信息

```shell
ip: 127.0.0.1
port: 3306
username: root
password: root
```

### Nacos

```shell
# 需自己建库`nacos_config`, 并执行`./nacos/nacos_2.0.3/nacos_config.sql`脚本
# 修改`./nacos/docker-compose-nacos-2.0.3.yml`中的mysql连接信息再运行
docker-compose -f ./nacos/docker-compose-nacos-2.0.3.yml -p nacos_v2.0.3 up -d
```

访问地址：[`ip地址:8848/nacos`](http://127.0.0.1:8848/nacos)
登录账号密码默认：`nacos/nacos`

### RabbitMQ

```shell
# 当前目录下所有文件赋予权限(读、写、执行)
chmod -R 777 ./rabbitmq
# 运行 [ 注：如果之前有安装过，需要清除浏览器缓存和删除rabbitmq相关的存储数据(如:这里映射到宿主机的data数据目录)，再重装，否则会出现一定问题！ ]
docker-compose -f ./rabbitmq/docker-compose-rabbitmq.yml -p rabbitmq up -d

# 进入容器
docker exec -it rabbitmq /bin/bash
# 启用延时插件
rabbitmq-plugins enable rabbitmq_delayed_message_exchange
# 查看已安装插件
rabbitmq-plugins list
```

web管理端：[`ip地址:15672`](http://127.0.0.1:15672)
登录账号密码：`admin/admin`

### Redis

```shell
# 当前目录下所有文件赋予权限(读、写、执行)
chmod -R 777 ./redis
# 运行
docker-compose -f ./redis/docker-compose-redis.yml -p redis up -d
# 连接redis
docker exec -it redis redis-cli -a 123456  # 密码为123456
```

### Sentinel

```shell
docker-compose -f ./sentinel/docker-compose-sentinel.yml -p sentinel up -d
```

访问地址：[`ip地址:8858`](http://127.0.0.1:8858)
登录账号密码：`sentinel/sentinel`

### XXL-JOB - 分布式任务调度平台

```shell
# 导入数据库`./xxl-job/tables_xxl_job.sql`
docker-compose -f ./xxl-job/docker-compose-xxl-job.yml -p xxl-job up -d
```

访问地址：[`http://ip地址:9003/xxl-job-admin`](http://127.0.0.1:9003/xxl-job-admin)
默认登录账号密码：`admin/123456`
