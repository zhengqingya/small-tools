### 部署

```shell
cnpm run build:prod
sh small-tools-web-run.sh
```

### 运行

```shell
docker run -d --name small-tools-web -p 80:80 --restart=always registry.cn-hangzhou.aliyuncs.com/zhengqing/small-tools-web:prod
```

### 全局格式化代码

```
# 安装 gogocode-cli
npm install gogocode-cli -g

# 格式化源代码
gogocode -s ./src -t gogocode-plugin-prettier  -o ./src
```