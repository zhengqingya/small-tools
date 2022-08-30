### Redis

```shell
# 当前目录下所有文件赋予权限(读、写、执行)
chmod -R 777 ./redis
# 运行
docker-compose -f ./docker-compose-redis.yml -p redis up -d
# 连接redis
docker exec -it redis redis-cli -a 123456  # 密码为123456
```
