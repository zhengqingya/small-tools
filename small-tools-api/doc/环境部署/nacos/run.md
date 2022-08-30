### Nacos

> tips: 一定要先导入`nacos_config.sql`,再启动nacos，才能正常读取到配置信息，不然会有缓存问题

```shell
# 需自己建库`nacos_config`, 并执行`./nacos_2.0.3/nacos_config.sql`脚本
# 修改`./docker-compose-nacos-2.0.3.yml`中的mysql连接信息再运行
docker-compose -f ./docker-compose-nacos-2.0.3.yml -p nacos_v2.0.3 up -d
```

访问地址：[`http://127.0.0.1:8848/nacos`](http://127.0.0.1:8848/nacos)
登录账号密码默认：`nacos/nacos`