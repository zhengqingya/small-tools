### 部署

```shell
cnpm run build:prod
sh small-tools-web-run.sh
```

### 运行

```shell
docker run -d --name small-tools-web -p 80:80 --restart=always registry.cn-hangzhou.aliyuncs.com/zhengqing/small-tools-web:prod
```