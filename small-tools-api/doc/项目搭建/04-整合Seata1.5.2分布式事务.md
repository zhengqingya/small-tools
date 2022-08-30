@[TOC](文章目录)

### 一、前言

1. springboot 2.7.0
2. springcloudalibaba 2021.0.1.0
3. seata1.5.2

### 二、docker-compose部署seata

> tips: 相应配置记得自行修改

```shell
git clone https://gitee.com/zhengqingya/docker-compose.git
cd docker-compose/Linux/seata/1.5.2
# 修改seata配置文件`./seata-server/resources/application.yml`
# 修改`docker-compose-seata.yml`相关IP配置
# nacos命名空间`prod`下新建配置`seata-server.properties`
# 新建数据库`seata-server`，导入sql脚本`./sql/seata-server.sql`
# 运行
docker-compose -f docker-compose-seata.yml -p seata up -d
# 进入容器
# docker exec -it seata-server sh
# 查看日志
docker logs -f seata-server
```

访问seata控制台：`ip地址:7091`
登录账号密码默认：`seata/seata`

![在这里插入图片描述](https://img-blog.csdnimg.cn/5dcebdc241dc4638bad10cebdd4bb467.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/1eaf783087a247539e078657fe28e648.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/563024dc360548b08913c3fc8f695472.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/539f4a4ff16d485cad4546164f1f6738.png)

#### 部分配置文件

`Linux/seata/1.5.2/docker-compose-seata.yml`

```yml
# 可参考 https://seata.io/zh-cn/docs/ops/deploy-by-docker-compose.html
version: '3'

# 网桥 -> 方便相互通讯
networks:
  seata:
    driver: bridge

services:
  seata:
    image: registry.cn-hangzhou.aliyuncs.com/zhengqing/seata-server:1.5.2      # 原镜像`seataio/seata-server:1.5.2`
    container_name: seata-server                                  # 容器名为'seata-server'
    restart: unless-stopped                                       # 指定容器退出后的重启策略为始终重启，但是不考虑在Docker守护进程启动时就已经停止了的容器
    volumes:                                                      # 数据卷挂载路径设置,将本机目录映射到容器目录
      - "./seata-server/resources/application.yml:/seata-server/resources/application.yml"
    environment:                        # 设置环境变量,相当于docker run命令中的-e
      TZ: Asia/Shanghai
      LANG: en_US.UTF-8
      # 注册到nacos上的ip。客户端将通过该ip访问seata服务。
      # 注意公网ip和内网ip的差异。
      SEATA_IP: www.zhengqingya.com
      # 指定seata服务启动端口
      SEATA_PORT: 8091
    ports:                              # 映射端口
      - "7091:7091"
      - "8091:8091"
    networks:
      - seata
```

`Linux/seata/1.5.2/seata-server/resources/application.yml`

```yml
server:
  port: 7091

spring:
  application:
    name: seata-server

logging:
  config: classpath:logback-spring.xml
  file:
    path: ${user.home}/logs/seata

console:
  user:
    username: seata
    password: seata

seata:
  config:
    type: nacos
    nacos:
      server-addr: 127.0.0.1:8848
      group: SEATA_GROUP
      namespace: prod
      username: nacos
      password: nacos
      data-id: seata-server.properties
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: 127.0.0.1:8848
      group: SEATA_GROUP
      namespace: prod
      cluster: default
      username: nacos
      password: nacos
  security:
    secretKey: SeataSecretKey0c382ef121d778043159209298fd40bf3850a017
    tokenValidityInMilliseconds: 1800000
    ignore:
      urls: /,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-fe/public/**,/api/v1/auth/login
```

`Linux/seata/1.5.2/nacos-config/seata-server.properties`

```
# 可参考 https://github.com/seata/seata/blob/develop/script/config-center/config.txt

# 存储模式
store.mode=db

store.db.datasource=druid
store.db.dbType=mysql
# 需要根据mysql的版本调整driverClassName
# mysql8及以上版本对应的driver：com.mysql.cj.jdbc.Driver
# mysql8以下版本的driver：com.mysql.jdbc.Driver
store.db.driverClassName=com.mysql.jdbc.Driver
# 注意根据生产实际情况调整参数host和port
store.db.url=jdbc:mysql://www.zhengqingya.com:3306/seata-server?useUnicode=true&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false
# 数据库用户名
store.db.user=root
# 用户名密码
store.db.password=root

# Transaction routing rules configuration, only for the client
service.vgroupMapping.default_tx_group=default
service.vgroupMapping.my_test_tx_group=default
service.vgroupMapping.user-tx-group=default
service.vgroupMapping.order-tx-group=default
service.vgroupMapping.demo-tx-group=default
service.vgroupMapping.system-tx-group=default
```

### 三、springcloud引入seata

> tips: 小编这里将seata单独抽离了一个公共模块，提供给业务模块使用
> ![在这里插入图片描述](https://img-blog.csdnimg.cn/a07b828b68be4728b35deb2562b067b2.png)

#### 1、引入seata依赖

最外层父pom.xml中统一管理seata版本

```xml
<dependencyManagement>
	<dependencies>
  		<!-- seata分布式事务 -->
        <!-- https://mvnrepository.com/artifact/io.seata/seata-spring-boot-starter -->
        <dependency>
             <groupId>io.seata</groupId>
             <artifactId>seata-spring-boot-starter</artifactId>
             <version>1.5.2</version>
         </dependency>
	</dependencies>
</dependencyManagement>
```

seata模块中引入

```xml
<dependencies>
        <!-- 最外层父pom.xml中统一管理seata版本 -->
        <!-- seata -->
        <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-seata -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>io.seata</groupId>
                    <artifactId>seata-spring-boot-starter</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- https://mvnrepository.com/artifact/io.seata/seata-spring-boot-starter -->
        <dependency>
            <groupId>io.seata</groupId>
            <artifactId>seata-spring-boot-starter</artifactId>
        </dependency>
    </dependencies>
```


#### 2、application-seata.yml

```yml
# seata配置
seata:
  # 是否开启seata
  enabled: true
  # Seata 应用编号，默认为 ${spring.application.name}
  application-id: ${spring.application.name}
  # Seata 事务组编号，用于 TC 集群名
  tx-service-group: ${spring.application.name}-tx-group
  # 自动代理
  enable-auto-data-source-proxy: true
  # 服务配置项
  #  service:
  #    # 虚拟组和分组的映射
  #    vgroup-mapping:
  #      test-tx-group: default
  #    # 分组和 Seata 服务的映射
  #    grouplist:
  #      default: 127.0.0.1:8091
  config:
    type: nacos
    nacos:
      serverAddr: ${spring.cloud.nacos.config.server-addr}
      group: SEATA_GROUP
      namespace: ${spring.cloud.nacos.config.namespace}
      username: ${spring.cloud.nacos.config.username}
      password: ${spring.cloud.nacos.config.password}
      dataId: seata-server.properties
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: ${spring.cloud.nacos.config.server-addr}
      group: SEATA_GROUP
      namespace: ${spring.cloud.nacos.config.namespace}
      username: ${spring.cloud.nacos.config.username}
      password: ${spring.cloud.nacos.config.password}

# 数据源配置见：application-db.yml
spring:
  datasource:
    dynamic:
      seata: true    # 开启seata代理，开启后默认每个数据源都代理，如果某个不需要代理可单独关闭
```

#### 3、undo_log.sql

> tips: 在要使用seata分布式事务的数据库下创建表`undo_log.sql`

```sql
CREATE TABLE `undo_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id` bigint(20) NOT NULL,
    `xid` varchar(100) NOT NULL,
    `context` varchar(128) NOT NULL,
    `rollback_info` longblob NOT NULL,
    `log_status` int(11) NOT NULL,
    `log_created` datetime NOT NULL,
    `log_modified` datetime NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

#### 4、业务模块使用seata

引入seata

```xml
<dependency>
  	<groupId>com.zhengqing</groupId>
    <artifactId>seata</artifactId>
</dependency>
```

#### 5、@GlobalTransactional

在主入口中使用注解`@GlobalTransactional`实现分布式事务

![在这里插入图片描述](https://img-blog.csdnimg.cn/62f7eb4be79b4b379af6978f1c8ce396.png)

### 本文案例demo源码

[https://gitee.com/zhengqingya/small-tools.git](https://gitee.com/zhengqingya/small-tools.git)


--- 

> 今日分享语句：
> 如果你想在这个世界上获得成功，当你进入某个沙龙时，你必须让你的虚荣心向别人的虚荣心致敬。

