### 本地运行

```shell
# 安装依赖
cnpm install

# 运行
cnpm run dev
```

### 部署

```shell
# 构建出dist文件夹，然后将打包后的文件放到nginx中...
cnpm run build:prod

# sh small-tools-web-run.sh
```

---

### 线上运行

> tips: 暂存，不用看这个...

```shell
docker run -d --name small-tools-web -p 80:80 --restart=always registry.cn-hangzhou.aliyuncs.com/zhengqing/small-tools-web:prod
```

### 全局格式化代码

> tips: 暂存，不用看这个...
> vue2 迁移 vue3

```
# 安装 gogocode-cli
npm install gogocode-cli -g

# 格式化源代码
gogocode -s ./src -t gogocode-plugin-prettier  -o ./src

# 从 Vue2 转换成 Vue3
gogocode -s ./src -t gogocode-plugin-vue  -o ./src

# 从 ElementUI 转换成 Element Plus
gogocode -s ./src -t gogocode-plugin-element  -o ./src
```

---

[点击查看前端项目搭建文档](https://gitee.com/zhengqingya/java-developer-document/tree/master/%E7%9F%A5%E8%AF%86%E5%BA%93/%E5%89%8D%E7%AB%AF/02-%E9%A1%B9%E7%9B%AE%E5%AE%9E%E6%88%98)
