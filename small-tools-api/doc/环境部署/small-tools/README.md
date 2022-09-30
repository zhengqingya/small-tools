# 环境部署

> 组件环境部署可参考 https://gitee.com/zhengqingya/docker-compose

```shell
# 当前目录下所有文件赋予权限(读、写、执行)
chmod -R 777 ./redis
chmod -R 777 ./rabbitmq

# 组件部署
# tips: [ rabbitmq: 如果之前有安装过，需要清除浏览器缓存和删除rabbitmq相关的存储数据(如:这里映射到宿主机的data数据目录)，再重装，否则会出现一定问题！ ]
docker-compose -f ./docker-compose.yml -p small-tools up -d mysql nacos redis rabbitmq sentinel xxl-job-admin
```

---

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
