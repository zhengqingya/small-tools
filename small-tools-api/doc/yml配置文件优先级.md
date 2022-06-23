优先级依次从高到低

> 当扫描到第一个配置文件后，就不会去扫描后面低优先级位置的配置文件了。

`bootstrap.yml` > `bootstrap.yaml` > `application.yml`


---


在本项目中
基础公共配置: `small-tools-api/common/web/src/main/resources/bootstrap.yaml` => 基础组件配置
其它模块配置: `small-tools-api/service/system/src/main/resources/bootstrap.yml` => 各模块端口、应用名称等