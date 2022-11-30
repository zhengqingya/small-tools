/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql_3306
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 127.0.0.1:3306
 Source Schema         : nacos_config

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 08/10/2022 16:32:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建数据库
CREATE database if NOT EXISTS `nacos_config`;
-- 使用数据库
USE `nacos_config`;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (3, 'demo.yml', 'small-tools', 'spring:\n  application:\n    name: demo # 应用名称\n\n  # cloud:\n  #   config:\n  #     # 相同配置下优先使用本地配置 (注：需在nacos上配置才生效！)\n  #     override-none: true\n  #     allow-override: true\n  #     override-system-properties: false\n\n  rabbitmq:\n    moduleList:\n      # 订单延时队列，到了过期的时间会被转发到订单取消队列\n      - routing-key: test.create.routing.key\n        queue:\n          name: test.delay.queue\n          dead-letter-exchange: test.exchange\n          dead-letter-routing-key: test.close.routing.key\n          arguments:\n            # 5秒 （单位：毫秒）  --  tips:mq最大延时4294967295毫秒(即49.7103天)\n            x-message-ttl: 5000\n        exchange:\n          name: test.exchange\n      # 订单取消队列\n      - routing-key: test.close.routing.key\n        queue:\n          name: test.close.queue\n        exchange:\n          name: test.exchange\n\n      - routing-key: demo.test.routing.key\n        queue:\n          name: demo.test.queue\n        exchange:\n          name: demo.exchange\n      - routing-key: demo.test_delay.routing.key\n        queue:\n          name: demo.test_delay.queue\n        exchange:\n          name: demo.delay.exchange\n          type: delay\n\n\n# XXL-JOB 配置\nxxl:\n  job:\n    admin:\n      addresses: http://172.16.22.244:9003/xxl-job-admin # 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\n', '0694f886fd6842fec80a5250c9627f1f', '2022-10-08 16:25:51', '2022-10-08 16:25:51', NULL, '172.22.0.1', '', 'dev', 'demo服务配置文件', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (4, 'common.yml', 'small-tools', '# ================================== ↓↓↓↓↓↓ small-tools配置 ↓↓↓↓↓↓ ==================================\r\nsmall-tools:\r\n  # 相关组件服务通用ip\r\n  ip: 127.0.0.1\r\n\r\n# ========================== ↓↓↓↓↓↓ 七牛云配置 ↓↓↓↓↓↓ ==========================\r\nqiniu:\r\n  accessKey: xxx\r\n  secretKey: xxx\r\n', 'b55f1a2a74555795dcea1572ba62990d', '2022-10-08 16:25:51', '2022-10-08 16:25:51', NULL, '172.22.0.1', '', 'dev', '公共配置文件', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (5, 'seata-server.properties', 'SEATA_GROUP', '# 可参考 https://github.com/seata/seata/blob/develop/script/config-center/config.txt\r\n\r\n# 存储模式\r\nstore.mode=db\r\n\r\nstore.db.datasource=druid\r\nstore.db.dbType=mysql\r\n# 需要根据mysql的版本调整driverClassName\r\n# mysql8及以上版本对应的driver：com.mysql.cj.jdbc.Driver\r\n# mysql8以下版本的driver：com.mysql.jdbc.Driver\r\nstore.db.driverClassName=com.mysql.jdbc.Driver\r\n# 注意根据生产实际情况调整参数host和port\r\nstore.db.url=jdbc:mysql://mysql:3306/seata-server?useUnicode=true&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false\r\n# 数据库用户名\r\nstore.db.user=root\r\n# 用户名密码\r\nstore.db.password=root\r\n\r\n# Transaction routing rules configuration, only for the client\r\nservice.vgroupMapping.default_tx_group=default\r\nservice.vgroupMapping.my_test_tx_group=default\r\nservice.vgroupMapping.user-tx-group=default\r\nservice.vgroupMapping.order-tx-group=default\r\nservice.vgroupMapping.demo-tx-group=default\r\nservice.vgroupMapping.system-tx-group=default\r\n', '25c8416f74d8704d05a845548fdddeab', '2022-10-08 16:31:57', '2022-10-08 16:31:57', 'nacos', '172.22.0.1', '', 'dev', NULL, NULL, NULL, 'properties', NULL);

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(64) UNSIGNED NOT NULL,
  `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (0, 1, 'demo.yml', 'small-tools', '', 'spring:\r\n  application:\r\n    name: demo # 应用名称\r\n\r\n  # cloud:\r\n  #   config:\r\n  #     # 相同配置下优先使用本地配置 (注：需在nacos上配置才生效！)\r\n  #     override-none: true\r\n  #     allow-override: true\r\n  #     override-system-properties: false\r\n\r\n  rabbitmq:\r\n    moduleList:\r\n      # 订单延时队列，到了过期的时间会被转发到订单取消队列\r\n      - routing-key: test.create.routing.key\r\n        queue:\r\n          name: test.delay.queue\r\n          dead-letter-exchange: test.exchange\r\n          dead-letter-routing-key: test.close.routing.key\r\n          arguments:\r\n            # 5秒 （单位：毫秒）  --  tips:mq最大延时4294967295毫秒(即49.7103天)\r\n            x-message-ttl: 5000\r\n        exchange:\r\n          name: test.exchange\r\n      # 订单取消队列\r\n      - routing-key: test.close.routing.key\r\n        queue:\r\n          name: test.close.queue\r\n        exchange:\r\n          name: test.exchange\r\n\r\n      - routing-key: demo.test.routing.key\r\n        queue:\r\n          name: demo.test.queue\r\n        exchange:\r\n          name: demo.exchange\r\n      - routing-key: demo.test_delay.routing.key\r\n        queue:\r\n          name: demo.test_delay.queue\r\n        exchange:\r\n          name: demo.delay.exchange\r\n          type: delay\r\n\r\n\r\n# XXL-JOB 配置\r\nxxl:\r\n  job:\r\n    admin:\r\n      addresses: http://172.16.22.244:9003/xxl-job-admin # 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\r\n', '800f445a1c301f243524dca04459d2eb', '2022-09-30 18:38:53', '2022-09-30 18:38:53', 'nacos', '172.22.0.1', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (0, 2, 'common.yml', 'small-tools', '', '# ================================== ↓↓↓↓↓↓ small-tools配置 ↓↓↓↓↓↓ ==================================\r\nsmall-tools:\r\n  # 相关组件服务通用ip\r\n  ip: 127.0.0.1\r\n\r\n# ========================== ↓↓↓↓↓↓ 七牛云配置 ↓↓↓↓↓↓ ==========================\r\nqiniu:\r\n  accessKey: xxx\r\n  secretKey: xxx\r\n', 'b55f1a2a74555795dcea1572ba62990d', '2022-09-30 18:40:34', '2022-09-30 18:40:34', 'nacos', '172.22.0.1', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (1, 3, 'demo.yml', 'small-tools', '', 'spring:\r\n  application:\r\n    name: demo # 应用名称\r\n\r\n  # cloud:\r\n  #   config:\r\n  #     # 相同配置下优先使用本地配置 (注：需在nacos上配置才生效！)\r\n  #     override-none: true\r\n  #     allow-override: true\r\n  #     override-system-properties: false\r\n\r\n  rabbitmq:\r\n    moduleList:\r\n      # 订单延时队列，到了过期的时间会被转发到订单取消队列\r\n      - routing-key: test.create.routing.key\r\n        queue:\r\n          name: test.delay.queue\r\n          dead-letter-exchange: test.exchange\r\n          dead-letter-routing-key: test.close.routing.key\r\n          arguments:\r\n            # 5秒 （单位：毫秒）  --  tips:mq最大延时4294967295毫秒(即49.7103天)\r\n            x-message-ttl: 5000\r\n        exchange:\r\n          name: test.exchange\r\n      # 订单取消队列\r\n      - routing-key: test.close.routing.key\r\n        queue:\r\n          name: test.close.queue\r\n        exchange:\r\n          name: test.exchange\r\n\r\n      - routing-key: demo.test.routing.key\r\n        queue:\r\n          name: demo.test.queue\r\n        exchange:\r\n          name: demo.exchange\r\n      - routing-key: demo.test_delay.routing.key\r\n        queue:\r\n          name: demo.test_delay.queue\r\n        exchange:\r\n          name: demo.delay.exchange\r\n          type: delay\r\n\r\n\r\n# XXL-JOB 配置\r\nxxl:\r\n  job:\r\n    admin:\r\n      addresses: http://172.16.22.244:9003/xxl-job-admin # 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\r\n', '800f445a1c301f243524dca04459d2eb', '2022-09-30 18:40:51', '2022-09-30 18:40:51', 'nacos', '172.22.0.1', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (0, 4, 'demo.yml', 'small-tools', '', 'spring:\n  application:\n    name: demo # 应用名称\n\n  # cloud:\n  #   config:\n  #     # 相同配置下优先使用本地配置 (注：需在nacos上配置才生效！)\n  #     override-none: true\n  #     allow-override: true\n  #     override-system-properties: false\n\n  rabbitmq:\n    moduleList:\n      # 订单延时队列，到了过期的时间会被转发到订单取消队列\n      - routing-key: test.create.routing.key\n        queue:\n          name: test.delay.queue\n          dead-letter-exchange: test.exchange\n          dead-letter-routing-key: test.close.routing.key\n          arguments:\n            # 5秒 （单位：毫秒）  --  tips:mq最大延时4294967295毫秒(即49.7103天)\n            x-message-ttl: 5000\n        exchange:\n          name: test.exchange\n      # 订单取消队列\n      - routing-key: test.close.routing.key\n        queue:\n          name: test.close.queue\n        exchange:\n          name: test.exchange\n\n      - routing-key: demo.test.routing.key\n        queue:\n          name: demo.test.queue\n        exchange:\n          name: demo.exchange\n      - routing-key: demo.test_delay.routing.key\n        queue:\n          name: demo.test_delay.queue\n        exchange:\n          name: demo.delay.exchange\n          type: delay\n\n\n# XXL-JOB 配置\nxxl:\n  job:\n    admin:\n      addresses: http://172.16.22.244:9003/xxl-job-admin # 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\n', '0694f886fd6842fec80a5250c9627f1f', '2022-10-08 16:25:50', '2022-10-08 16:25:51', NULL, '172.22.0.1', 'I', 'dev');
INSERT INTO `his_config_info` VALUES (0, 5, 'common.yml', 'small-tools', '', '# ================================== ↓↓↓↓↓↓ small-tools配置 ↓↓↓↓↓↓ ==================================\r\nsmall-tools:\r\n  # 相关组件服务通用ip\r\n  ip: 127.0.0.1\r\n\r\n# ========================== ↓↓↓↓↓↓ 七牛云配置 ↓↓↓↓↓↓ ==========================\r\nqiniu:\r\n  accessKey: xxx\r\n  secretKey: xxx\r\n', 'b55f1a2a74555795dcea1572ba62990d', '2022-10-08 16:25:51', '2022-10-08 16:25:51', NULL, '172.22.0.1', 'I', 'dev');
INSERT INTO `his_config_info` VALUES (1, 6, 'demo.yml', 'small-tools', '', 'spring:\n  application:\n    name: demo # 应用名称\n\n  # cloud:\n  #   config:\n  #     # 相同配置下优先使用本地配置 (注：需在nacos上配置才生效！)\n  #     override-none: true\n  #     allow-override: true\n  #     override-system-properties: false\n\n  rabbitmq:\n    moduleList:\n      # 订单延时队列，到了过期的时间会被转发到订单取消队列\n      - routing-key: test.create.routing.key\n        queue:\n          name: test.delay.queue\n          dead-letter-exchange: test.exchange\n          dead-letter-routing-key: test.close.routing.key\n          arguments:\n            # 5秒 （单位：毫秒）  --  tips:mq最大延时4294967295毫秒(即49.7103天)\n            x-message-ttl: 5000\n        exchange:\n          name: test.exchange\n      # 订单取消队列\n      - routing-key: test.close.routing.key\n        queue:\n          name: test.close.queue\n        exchange:\n          name: test.exchange\n\n      - routing-key: demo.test.routing.key\n        queue:\n          name: demo.test.queue\n        exchange:\n          name: demo.exchange\n      - routing-key: demo.test_delay.routing.key\n        queue:\n          name: demo.test_delay.queue\n        exchange:\n          name: demo.delay.exchange\n          type: delay\n\n\n# XXL-JOB 配置\nxxl:\n  job:\n    admin:\n      addresses: http://172.16.22.244:9003/xxl-job-admin # 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"；为空则关闭自动注册；\n', '0694f886fd6842fec80a5250c9627f1f', '2022-10-08 16:25:53', '2022-10-08 16:25:54', NULL, '172.22.0.1', 'D', 'prod');
INSERT INTO `his_config_info` VALUES (2, 7, 'common.yml', 'small-tools', '', '# ================================== ↓↓↓↓↓↓ small-tools配置 ↓↓↓↓↓↓ ==================================\r\nsmall-tools:\r\n  # 相关组件服务通用ip\r\n  ip: 127.0.0.1\r\n\r\n# ========================== ↓↓↓↓↓↓ 七牛云配置 ↓↓↓↓↓↓ ==========================\r\nqiniu:\r\n  accessKey: xxx\r\n  secretKey: xxx\r\n', 'b55f1a2a74555795dcea1572ba62990d', '2022-10-08 16:25:53', '2022-10-08 16:25:54', NULL, '172.22.0.1', 'D', 'prod');
INSERT INTO `his_config_info` VALUES (0, 8, 'seata-server.properties', 'SEATA_GROUP', '', '# 可参考 https://github.com/seata/seata/blob/develop/script/config-center/config.txt\r\n\r\n# 存储模式\r\nstore.mode=db\r\n\r\nstore.db.datasource=druid\r\nstore.db.dbType=mysql\r\n# 需要根据mysql的版本调整driverClassName\r\n# mysql8及以上版本对应的driver：com.mysql.cj.jdbc.Driver\r\n# mysql8以下版本的driver：com.mysql.jdbc.Driver\r\nstore.db.driverClassName=com.mysql.jdbc.Driver\r\n# 注意根据生产实际情况调整参数host和port\r\nstore.db.url=jdbc:mysql://mysql:3306/seata-server?useUnicode=true&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false\r\n# 数据库用户名\r\nstore.db.user=root\r\n# 用户名密码\r\nstore.db.password=root\r\n\r\n# Transaction routing rules configuration, only for the client\r\nservice.vgroupMapping.default_tx_group=default\r\nservice.vgroupMapping.my_test_tx_group=default\r\nservice.vgroupMapping.user-tx-group=default\r\nservice.vgroupMapping.order-tx-group=default\r\nservice.vgroupMapping.demo-tx-group=default\r\nservice.vgroupMapping.system-tx-group=default\r\n', '25c8416f74d8704d05a845548fdddeab', '2022-10-08 16:31:56', '2022-10-08 16:31:57', 'nacos', '172.22.0.1', 'I', 'dev');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (1, '1', 'dev', 'dev', 'dev', 'nacos', 1658904015032, 1658904015032);
INSERT INTO `tenant_info` VALUES (2, '1', 'test', 'test', 'test', 'nacos', 1658904019788, 1658904019788);
INSERT INTO `tenant_info` VALUES (3, '1', 'prod', 'prod', 'prod', 'nacos', 1658904027886, 1658904027886);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
