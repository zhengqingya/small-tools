### docker-compose部署canal

```shell
# 准备
git clone https://gitee.com/zhengqingya/docker-compose.git
cd docker-compose/Liunx

# 导入初始化SQL
Liunx/canal/canal_admin/canal_manager.sql

# 运行  (tips:先修改配置文件信息)
docker-compose -f docker-compose-canal.yml -p canal up -d
```
