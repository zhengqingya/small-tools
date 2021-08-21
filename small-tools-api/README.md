# 说明

### nacos

> 先导入`nacos_config.sql`,再启动nacos，才能正常读取到配置信息，不然会有缓存问题

### error

###### Caused by: java.lang.IllegalStateException: No fallback instance of type class com.zhengqing.system.feign.fallback.IDictClientFallback found for feign client system

需要扫描feign包以及它的回调包fallback

```
@ComponentScan(basePackages = {"com.zhengqing.system", "com.zhengqing.common", "com.zhengqing.basic"})
```

### 打包问题

> 子模块如果不想打包则如下配置：

```
<build>
    <plugins>
        <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <!-- 跳过父模块的此打包插件 -->
          <skip>true</skip>
          <finalName>${project.name}</finalName>
        </configuration>
        </plugin>
    </plugins>
</build>
```

### 项目部署

```shell
# 服务器运行
docker run -d -e APP_PROFILE=prod -p 1218:1218 -p 20010:20010 -p 20030:20030 --restart=always --name small-tools-api registry.cn-hangzhou.aliyuncs.com/zhengqing/small-tools-api:prod

# 查看容器运行内存信息  【参数`mem_limit: 1000m` # 最大使用内存】
docker stats small-tools-api
# CONTAINER ID        NAME                CPU %               MEM USAGE / LIMIT     MEM %               NET I/O             BLOCK I/O           PIDS
# df0f8f22629c        small-tools-api     398.44%             1.022GiB / 18.66GiB   5.47%               79kB / 40.5kB       0B / 0B             234
```

### 数据清理

```sql
SELECT CONCAT('DELETE FROM ', TABLE_NAME, ' where is_deleted=1;')
FROM information_schema.TABLES
WHERE table_schema = 'small-tools';
```
