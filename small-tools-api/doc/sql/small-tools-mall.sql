/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql_3306
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 127.0.0.1:3306
 Source Schema         : small-tools-mall

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 30/09/2022 16:43:20
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oms_logistic
-- ----------------------------
DROP TABLE IF EXISTS `oms_logistic`;
CREATE TABLE `oms_logistic`
(
    `id`                bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`         int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `logistics_company` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物流公司',
    `logistics_code`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '快递公司编码',
    `logistics_no`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物流单号',
    `receiver_phone`    varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人电话（SF快递查询物流必用字段）',
    `trace_json`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '事件轨迹集',
    `deliverer_name`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '送货人名称',
    `deliverer_phone`   varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '送货人电话号码',
    `location`          varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在城市',
    `status`            int(2) NOT NULL DEFAULT 0 COMMENT '物流状态',
    `status_ex`         int(2) NULL DEFAULT NULL COMMENT '增值物流状态',
    `create_time`       datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-物流信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_logistic
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`
(
    `order_no`            bigint(20) NOT NULL COMMENT '订单编号',
    `tenant_id`           int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `wx_openid`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信openid',
    `user_id`             bigint(20) NOT NULL COMMENT '用户ID',
    `username`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
    `user_phone`          varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户电话',
    `user_sex`            tinyint(4) NOT NULL DEFAULT -1 COMMENT '性别(0->未知;1->男;2->女)',
    `pay_type`            tinyint(4) NULL DEFAULT NULL COMMENT '支付类型(1->微信 2->支付宝)',
    `pay_no`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付流水号',
    `pay_time`            datetime NULL DEFAULT NULL COMMENT '支付时间',
    `un_pay_end_time`     datetime NULL DEFAULT NULL COMMENT '未支付结束时间',
    `total_price`         int(11) NOT NULL DEFAULT 0 COMMENT '商品原价总金额(单位:分)',
    `freight`             int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '运费(单位:分 0:包邮)',
    `pay_price`           int(11) NULL DEFAULT 0 COMMENT '实付总金额(单位:分)',
    `order_status`        tinyint(4) UNSIGNED NOT NULL DEFAULT 1 COMMENT '订单状态(1->待支付 2->已取消 3->待发货 4->待收货 5->已完成 6->已退款)',
    `order_source`        tinyint(4) UNSIGNED NULL DEFAULT 1 COMMENT '订单来源(1->微信小程序 2->支付宝小程序)',
    `order_remark`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注',
    `buyer_msg`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '买家留言',
    `is_buyer_rate`       tinyint(2) NULL DEFAULT 0 COMMENT '买家是否已经评价(0->否 1->是)',
    `order_end_time`      datetime NULL DEFAULT NULL COMMENT '订单完成时间',
    `order_close_time`    datetime NULL DEFAULT NULL COMMENT '订单关闭时间',
    `deliver_type`        tinyint(4) NULL DEFAULT NULL COMMENT '发货方式(1->快递 2->无需发货)',
    `receiver_name`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
    `receiver_phone`      varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
    `receiver_address`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '收货人地址',
    `stock_check_type`    tinyint(2) NOT NULL COMMENT '库存校验类型(1->下单校验 2->支付校验)',
    `after_sale_end_time` datetime NULL DEFAULT NULL COMMENT '售后处理截止时间',
    `auto_receipt_time`   datetime NULL DEFAULT NULL COMMENT '自动收货时间',
    `is_send_coupon`      tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否发放优惠券(1->是，0->否)',
    `create_time`         datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         datetime                                                     NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`           bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
    `update_by`           bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
    `is_deleted`          tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-订单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order`
VALUES (1534726306686697472, 1, 'xxx', 1, 'admin', '15183303003', 1, NULL, NULL, NULL, '2022-06-09 10:42:20', 10, 0, 10,
        2, 1, '', NULL, 0, NULL, '2022-06-09 10:42:44', 1, '皮卡丘', '88888888', '四川省成都市高新区天府三街', 2, NULL, NULL, 0,
        '2022-06-09 10:37:20', '2022-06-09 10:42:44', 0, 0, 0);
INSERT INTO `oms_order`
VALUES (1534726770547359744, 1, 'xxx', 1, 'admin', '15183303003', 1, NULL, NULL, NULL, '2022-06-09 10:44:11', 10, 0, 10,
        2, 1, '', NULL, 0, NULL, '2022-06-09 10:44:11', 1, '皮卡丘', '88888888', '四川省成都市高新区天府三街', 2, NULL, NULL, 0,
        '2022-06-09 10:39:11', '2022-06-09 10:44:11', 0, 0, 0);
INSERT INTO `oms_order`
VALUES (1534727102006427648, 1, 'xxx', 1, 'admin', '15183303003', 1, NULL, NULL, NULL, '2022-06-09 10:45:30', 10, 0, 10,
        2, 1, '', NULL, 0, NULL, '2022-06-09 10:46:52', 1, '皮卡丘', '88888888', '四川省成都市高新区天府三街', 2, NULL, NULL, 0,
        '2022-06-09 10:40:30', '2022-06-09 10:46:52', 0, 0, 0);
INSERT INTO `oms_order`
VALUES (1534727462980812800, 1, 'xxx', 1, 'admin', '15183303003', 1, NULL, NULL, NULL, '2022-06-09 10:46:56', 10, 0, 10,
        2, 1, '', NULL, 0, NULL, '2022-06-09 10:47:02', 1, '皮卡丘', '88888888', '四川省成都市高新区天府三街', 2, NULL, NULL, 0,
        '2022-06-09 10:41:56', '2022-06-09 10:47:02', 0, 0, 0);
INSERT INTO `oms_order`
VALUES (1534728119984979968, 1, 'xxx', 1, 'admin', '15183303003', 1, NULL, NULL, NULL, '2022-06-09 10:49:32', 10, 0, 10,
        2, 1, '', NULL, 0, NULL, '2022-06-09 10:49:48', 1, '皮卡丘', '88888888', '四川省成都市高新区天府三街', 2, NULL, NULL, 0,
        '2022-06-09 10:44:32', '2022-06-09 10:49:48', 0, 0, 0);
INSERT INTO `oms_order`
VALUES (1534728195633446912, 1, 'xxx', 1, 'admin', '15183303003', 1, NULL, NULL, NULL, '2022-06-09 10:49:50', 10, 0, 10,
        2, 1, '', NULL, 0, NULL, '2022-06-09 10:50:23', 1, '皮卡丘', '88888888', '四川省成都市高新区天府三街', 2, NULL, NULL, 0,
        '2022-06-09 10:44:50', '2022-06-09 10:50:23', 0, 0, 0);
INSERT INTO `oms_order`
VALUES (1534729113984696320, 1, 'xxx', 1, 'admin', '15183303003', 1, NULL, NULL, NULL, '2022-06-09 10:53:29', 10, 0, 10,
        2, 1, '', NULL, 0, NULL, '2022-06-09 10:53:29', 1, '皮卡丘', '88888888', '四川省成都市高新区天府三街', 2, NULL, NULL, 0,
        '2022-06-09 10:48:29', '2022-06-09 10:53:29', 0, 0, 0);

-- ----------------------------
-- Table structure for oms_order_after_sale
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_after_sale`;
CREATE TABLE `oms_order_after_sale`
(
    `after_sale_no`             bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`                 int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `user_id`                   bigint(20) NOT NULL COMMENT '用户ID',
    `username`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
    `user_phone`                varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户电话',
    `order_no`                  bigint(20) NOT NULL COMMENT '订单编号',
    `after_type`                tinyint(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '售后类型(1-退款 2-退货退款 3-换货)',
    `after_status`              tinyint(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '售后状态(1->用户申请售后 2->用户撤销申请 3->同意申请 4->拒绝申请 5->申请退款 6->同意退款 7->拒绝退款 8->退款中 9->售后完成 10->已关闭)',
    `after_reason`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款,退/换货 原因',
    `after_explain`             varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
    `pay_price`                 int(11) NOT NULL DEFAULT 0 COMMENT '订单实付总金额(单位:分)',
    `freight`                   int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单运费(单位:分 0:包邮)',
    `procedure_price`           int(11) NULL DEFAULT NULL COMMENT '手续费(单位:分)',
    `apply_refund_price`        int(11) UNSIGNED NULL DEFAULT NULL COMMENT '申请退款金额 (单位：分)',
    `refund_price`              int(11) UNSIGNED NULL DEFAULT 0 COMMENT '实际退款金额 (单位：分)',
    `refund_time`               datetime NULL DEFAULT NULL COMMENT '退款时间',
    `cert_img_json`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '凭证图',
    `apply_time`                datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '售后申请时间',
    `return_logistics_company`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退/换货 物流公司',
    `return_logistics_code`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退/换货 物流公司编码',
    `return_logistics_no`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退/换货 物流单号',
    `return_address`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退/换货 收货地址',
    `again_logistics_no`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商店重发物流单号',
    `handler_id`                bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '处理人ID',
    `handler_name`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人姓名',
    `handler_result_for_refund` tinyint(1) NULL DEFAULT NULL COMMENT '处理人结果-处理退款（1->同意 0->拒绝）',
    `handler_remark_for_refund` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人备注-处理退款',
    `handler_time_for_refund`   datetime NULL DEFAULT NULL COMMENT '处理人处理时间-处理退款',
    `handler_result_for_apply`  tinyint(1) NULL DEFAULT NULL COMMENT '处理人结果-处理申请（1->同意 0->拒绝）',
    `handler_remark_for_apply`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人备注-处理申请',
    `handler_time_for_apply`    datetime NULL DEFAULT NULL COMMENT '处理人处理时间-处理申请',
    `seller_auto_close_time`    datetime NULL DEFAULT NULL COMMENT '售后卖家自动关闭时间',
    `buyer_auto_close_time`     datetime NULL DEFAULT NULL COMMENT '售后买家自动关闭时间',
    `close_time`                datetime NULL DEFAULT NULL COMMENT '售后关闭时间',
    `close_remark`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '售后关闭备注',
    `receiver_name`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
    `receiver_phone`            varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
    `receiver_address`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '收货人地址',
    `create_time`               datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`               datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`                 bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
    `update_by`                 bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
    `is_deleted`                tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`after_sale_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-售后表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_after_sale
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_after_sale_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_after_sale_item`;
CREATE TABLE `oms_order_after_sale_item`
(
    `id`            bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`     int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `after_sale_no` bigint(20) NOT NULL COMMENT '售后编号',
    `order_no`      bigint(20) NOT NULL COMMENT '订单编号',
    `order_item_id` bigint(20) NOT NULL COMMENT '订单详情id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-售后详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_after_sale_item
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`
(
    `id`             bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`      int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `order_no`       bigint(20) NOT NULL COMMENT '订单编号',
    `user_id`        bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
    `spu_id`         bigint(20) NOT NULL COMMENT '商品ID',
    `sku_id`         bigint(20) NOT NULL COMMENT '商品sku-id',
    `name`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '名称',
    `cover_img`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封面图',
    `spec_list`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品规格属性',
    `num`            int(11) UNSIGNED NOT NULL COMMENT '数量',
    `price`          int(11) NOT NULL DEFAULT 0 COMMENT '单价(单位:分)  ',
    `total_price`    int(11) NOT NULL DEFAULT 0 COMMENT '总价(单位:分)',
    `type`           tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '类型(101->实物 102->虚拟-优惠券)',
    `status`         tinyint(4) UNSIGNED NOT NULL COMMENT '状态(1->待支付 2->已取消 3->未发货 4->已发货 5->已完成)',
    `is_rate`        tinyint(2) NULL DEFAULT 0 COMMENT '买家是否已经评价(0->否 1->是)',
    `coupon_id`      bigint(20) NULL DEFAULT NULL COMMENT '优惠券ID',
    `coupon_name`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠券名字',
    `coupon_num`     int(11) NULL DEFAULT NULL COMMENT '优惠券数量',
    `is_send_coupon` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否发放优惠券(1->是，0->否)',
    `create_time`    datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                                                      NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX            `idx_order_no`(`order_no`) USING BTREE COMMENT '订单编号'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-订单详情' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item`
VALUES (1534726306820915200, 1, 1534726306686697472, 1, 1534420706752856064, 1534420706920628224, '熊猫限定帆布袋',
        'http://www.zhengqingya.com:9002/default/b1f263076e2147388251db3682df5f46.jpg',
        '[{\"attrValueId\":\"1\",\"attrKeyId\":\"1\",\"attrKeyName\":\"颜色\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101,
        2, 0, NULL, NULL, NULL, 0, '2022-06-09 10:37:26', '2022-06-09 10:42:44');
INSERT INTO `oms_order_item`
VALUES (1534726770614468608, 1, 1534726770547359744, 1, 1534420706752856064, 1534420706920628224, '熊猫限定帆布袋',
        'http://www.zhengqingya.com:9002/default/b1f263076e2147388251db3682df5f46.jpg',
        '[{\"attrValueId\":\"1\",\"attrKeyId\":\"1\",\"attrKeyName\":\"颜色\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101,
        2, 0, NULL, NULL, NULL, 0, '2022-06-09 10:39:11', '2022-06-09 10:44:10');
INSERT INTO `oms_order_item`
VALUES (1534727102069342208, 1, 1534727102006427648, 1, 1534420706752856064, 1534420706920628224, '熊猫限定帆布袋',
        'http://www.zhengqingya.com:9002/default/b1f263076e2147388251db3682df5f46.jpg',
        '[{\"attrValueId\":\"1\",\"attrKeyId\":\"1\",\"attrKeyName\":\"颜色\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101,
        2, 0, NULL, NULL, NULL, 0, '2022-06-09 10:41:49', '2022-06-09 10:46:52');
INSERT INTO `oms_order_item`
VALUES (1534727463115030528, 1, 1534727462980812800, 1, 1534420706752856064, 1534420706920628224, '熊猫限定帆布袋',
        'http://www.zhengqingya.com:9002/default/b1f263076e2147388251db3682df5f46.jpg',
        '[{\"attrValueId\":\"1\",\"attrKeyId\":\"1\",\"attrKeyName\":\"颜色\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101,
        2, 0, NULL, NULL, NULL, 0, '2022-06-09 10:41:58', '2022-06-09 10:47:01');
INSERT INTO `oms_order_item`
VALUES (1534728120052088832, 1, 1534728119984979968, 1, 1534420706752856064, 1534420706920628224, '熊猫限定帆布袋',
        'http://www.zhengqingya.com:9002/default/b1f263076e2147388251db3682df5f46.jpg',
        '[{\"attrValueId\":\"1\",\"attrKeyId\":\"1\",\"attrKeyName\":\"颜色\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101,
        2, 0, NULL, NULL, NULL, 0, '2022-06-09 10:44:44', '2022-06-09 10:49:48');
INSERT INTO `oms_order_item`
VALUES (1534728195696361472, 1, 1534728195633446912, 1, 1534420706752856064, 1534420706920628224, '熊猫限定帆布袋',
        'http://www.zhengqingya.com:9002/default/b1f263076e2147388251db3682df5f46.jpg',
        '[{\"attrValueId\":\"1\",\"attrKeyId\":\"1\",\"attrKeyName\":\"颜色\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101,
        2, 0, NULL, NULL, NULL, 0, '2022-06-09 10:45:07', '2022-06-09 10:50:23');
INSERT INTO `oms_order_item`
VALUES (1534729114068582400, 1, 1534729113984696320, 1, 1534420706752856064, 1534420706920628224, '熊猫限定帆布袋',
        'http://www.zhengqingya.com:9002/default/b1f263076e2147388251db3682df5f46.jpg',
        '[{\"attrValueId\":\"1\",\"attrKeyId\":\"1\",\"attrKeyName\":\"颜色\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101,
        2, 0, NULL, NULL, NULL, 0, '2022-06-09 10:48:29', '2022-06-09 10:53:29');

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`
(
    `id`                                     int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `tenant_id`                              int(11) UNSIGNED NULL DEFAULT NULL COMMENT '租户ID',
    `auto_receive_overtime`                  int(11) NULL DEFAULT NULL COMMENT '发货后？毫秒后自动确认收货',
    `un_pay_close_overtime`                  int(11) NULL DEFAULT NULL COMMENT '待付款订单？毫秒后自动关闭',
    `buyer_apply_after_sale_handle_overtime` int(11) NULL DEFAULT NULL COMMENT '买家发起售后申请？毫秒后，卖家未处理，自动关闭',
    `after_sale_buyer_deliver_overtime`      int(11) NULL DEFAULT NULL COMMENT '待买家发货(买家申请售后，卖家同意后，买家未填写退货返回物流单号)？毫秒后自动关闭',
    `buyer_apply_after_sale_overtime`        int(11) NULL DEFAULT NULL COMMENT '买家确认收货？毫秒后无法发起售后申请',
    `stock_check_type`                       tinyint(4) NULL DEFAULT NULL COMMENT '减库存设置（1：提交订单减库存 2：付款减库存）',
    `create_time`                            datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`                            datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`                              bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
    `update_by`                              bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
    `is_deleted`                             tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-订单配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_setting
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_shipping
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_shipping`;
CREATE TABLE `oms_order_shipping`
(
    `id`                bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`         int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `order_no`          bigint(20) NOT NULL COMMENT '订单编号',
    `receiver_name`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '收货人姓名',
    `receiver_phone`    varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '收货人电话',
    `receiver_address`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '收货人地址',
    `deliverer_name`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货人',
    `deliverer_phone`   varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货人电话',
    `deliverer_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货地址',
    `deliver_time`      datetime NULL DEFAULT NULL COMMENT '发货时间',
    `logistics_company` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司',
    `logistics_code`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司编码',
    `logistics_no`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
    `receipt_time`      datetime NULL DEFAULT NULL COMMENT '收货时间',
    `auto_receipt_time` datetime NULL DEFAULT NULL COMMENT '自动收货时间',
    `wx_notice_msg`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信通知消息',
    `create_time`       datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime                                                      NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`         bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
    `update_by`         bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX               `idx_order_no`(`order_no`) USING BTREE COMMENT '订单编号'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-订单配送表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_shipping
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_shipping_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_shipping_item`;
CREATE TABLE `oms_order_shipping_item`
(
    `id`            bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`     int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `order_no`      bigint(20) NOT NULL COMMENT '订单编号',
    `order_item_id` bigint(20) NOT NULL COMMENT '订单详情id',
    `shipping_id`   bigint(20) NOT NULL COMMENT '配送id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-订单配送详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_shipping_item
-- ----------------------------

-- ----------------------------
-- Table structure for pms_attr_key
-- ----------------------------
DROP TABLE IF EXISTS `pms_attr_key`;
CREATE TABLE `pms_attr_key`
(
    `id`            bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`     int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `attr_key_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性key名称',
    `sort`          int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
    `create_time`   datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                                               NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_by`     bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
    `update_by`     bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
    `is_deleted`    tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-属性key' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_attr_key
-- ----------------------------
INSERT INTO `pms_attr_key`
VALUES (1532281238671458304, 1, '颜色', 1, '2022-06-02 16:41:30', '2022-06-02 16:41:30', 0, 0, 0);
INSERT INTO `pms_attr_key`
VALUES (1532283711813451776, 1, '尺寸', 1, '2022-06-02 16:51:20', '2022-06-02 16:51:20', 0, 0, 0);

-- ----------------------------
-- Table structure for pms_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_attr_value`;
CREATE TABLE `pms_attr_value`
(
    `id`              bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`       int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `attr_key_id`     bigint(20) NOT NULL COMMENT '属性key',
    `attr_value_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性value值',
    `sort`            int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
    `create_time`     datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime                                               NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_by`       bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
    `update_by`       bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
    `is_deleted`      tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-属性value' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_attr_value
-- ----------------------------
INSERT INTO `pms_attr_value`
VALUES (1532284024536563712, 1, 1532281238671458304, '蓝色', 1, '2022-06-02 16:52:35', '2022-06-02 16:53:09', 0, 0, 0);
INSERT INTO `pms_attr_value`
VALUES (1532284050088263680, 1, 1532281238671458304, '红色', 1, '2022-06-02 16:52:41', '2022-06-02 16:53:09', 0, 0, 0);
INSERT INTO `pms_attr_value`
VALUES (1532284070791348224, 1, 1532281238671458304, '白色', 1, '2022-06-02 16:52:46', '2022-06-02 16:53:09', 0, 0, 0);
INSERT INTO `pms_attr_value`
VALUES (1532284250869596160, 1, 1532283711813451776, 'M', 1, '2022-06-02 16:53:29', '2022-06-02 16:53:51', 0, 0, 0);
INSERT INTO `pms_attr_value`
VALUES (1532284264874377216, 1, 1532283711813451776, 'X', 1, '2022-06-02 16:53:32', '2022-06-02 16:53:49', 0, 0, 0);
INSERT INTO `pms_attr_value`
VALUES (1532284292691001344, 1, 1532283711813451776, 'XL', 1, '2022-06-02 16:53:39', '2022-06-02 16:53:39', 0, 0, 0);
INSERT INTO `pms_attr_value`
VALUES (1532284305848532992, 1, 1532283711813451776, 'L', 1, '2022-06-02 16:53:42', '2022-06-02 16:53:42', 0, 0, 0);

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`
(
    `id`          bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`   int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `parent_id`   bigint(20) NOT NULL COMMENT '父分类id',
    `name`        varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
    `sort`        int(11) UNSIGNED NOT NULL COMMENT '排序',
    `is_show`     tinyint(1) NOT NULL COMMENT '是否显示(0->否,1->是)',
    `create_time` datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                               NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_by`   bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
    `update_by`   bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category`
VALUES (1532285889399619584, 1, 0, 'NICE', 1, 1, '2022-06-02 16:59:59', '2022-06-02 16:59:59', 0, 0, 0);
INSERT INTO `pms_category`
VALUES (1532285975026335744, 1, 0, '六一专属', 1, 1, '2022-06-02 17:00:20', '2022-06-02 17:00:20', 0, 0, 0);
INSERT INTO `pms_category`
VALUES (1533982865094737920, 1, 0, 'GOODS', 1, 1, '2022-06-07 09:23:10', '2022-06-07 09:23:10', 0, 0, 0);

-- ----------------------------
-- Table structure for pms_category_attr_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_attr_relation`;
CREATE TABLE `pms_category_attr_relation`
(
    `id`          bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`   int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `category_id` bigint(20) NOT NULL COMMENT '分类id',
    `attr_id`     bigint(20) NOT NULL COMMENT '属性id',
    `sort`        int(11) UNSIGNED NOT NULL COMMENT '排序',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_by`   bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
    `update_by`   bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-分类关联属性' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category_attr_relation
-- ----------------------------

-- ----------------------------
-- Table structure for pms_category_spu_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_spu_relation`;
CREATE TABLE `pms_category_spu_relation`
(
    `id`          bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`   int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `category_id` bigint(20) NOT NULL COMMENT '分类id',
    `spu_id`      bigint(20) NOT NULL COMMENT '商品id',
    `sort`        int(11) UNSIGNED NOT NULL COMMENT '排序',
    `is_show`     tinyint(1) NOT NULL COMMENT '是否显示(0->否,1->是)',
    `is_put`      tinyint(1) NOT NULL COMMENT '是否上架(0->否,1->是)',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_by`   bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
    `update_by`   bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-分类关联商品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category_spu_relation
-- ----------------------------
INSERT INTO `pms_category_spu_relation`
VALUES (1534424038364020736, 1, 1532285889399619584, 1534420706752856064, 1, 1, 1, '2022-06-08 14:36:14',
        '2022-06-08 14:36:45', 0, 0, 0);

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`
(
    `id`            bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`     int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `spu_id`        bigint(20) NOT NULL COMMENT '商品ID',
    `code`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格编码',
    `spec_list`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品规格-属性',
    `cost_price`    int(11) UNSIGNED NULL DEFAULT NULL COMMENT '成本价(单位:分)',
    `sell_price`    int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '销售单价(单位:分)',
    `presell_price` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '预售价格(单位:分)',
    `limit_count`   int(11) UNSIGNED NULL DEFAULT NULL COMMENT '每人限购',
    `total_stock`   int(11) UNSIGNED NOT NULL COMMENT '总库存',
    `use_stock`     int(11) UNSIGNED NOT NULL COMMENT '已用库存',
    `usable_stock`  int(11) UNSIGNED NOT NULL COMMENT '可用库存',
    `cover_img`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图',
    `is_show`       tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否显示(0->否 1->是)',
    `sort`          int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
    `create_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-商品规格' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku`
VALUES (1534420706920628224, 1, 1534420706752856064, 'ABC',
        '[{\"attrKeyId\":\"1\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1\",\"attrValueName\":\"蓝色\"}]', NULL, 10, 10,
        1, 4, 0, 4, 'http://www.zhengqingya.com:9002/default/b1f263076e2147388251db3682df5f46.jpg', 1, 0,
        '2022-06-08 14:22:59', '2022-06-08 15:41:29', 0);

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`
(
    `id`                  bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`           int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `name`                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
    `sort`                int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
    `type`                tinyint(2) UNSIGNED NULL DEFAULT NULL COMMENT '类型(101->实物 102->虚拟-优惠券)',
    `coupon_id`           bigint(20) NULL DEFAULT NULL COMMENT '优惠券ID',
    `coupon_name`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠券名字',
    `coupon_num`          int(11) NULL DEFAULT NULL COMMENT '优惠券数量',
    `cover_img`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图',
    `slide_img_list`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '轮播图',
    `detail_img_list`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品详情图',
    `line_price`          int(11) UNSIGNED NULL DEFAULT NULL COMMENT '商品划线价格(单位:分)',
    `freight`             int(11) UNSIGNED NOT NULL COMMENT '运费(单位:分 0:包邮)',
    `attr_list`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品属性-配置规格时使用',
    `is_presell`          tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否预售(0->否；1->是)',
    `presell_start_time`  datetime NULL DEFAULT NULL COMMENT '预售开始时间',
    `presell_end_time`    datetime NULL DEFAULT NULL COMMENT '预售结束时间',
    `presell_deliver_day` int(11) NULL DEFAULT NULL COMMENT '预售-发货日期(购买之后？天之后发货)',
    `is_put`              tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否上架(0->下架；1->上架)',
    `is_show`             tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否显示(0->隐藏；1->显示)',
    `service_list`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品关联服务',
    `explain_list`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品关联说明',
    `description`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品详情-文案',
    `create_time`         datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         datetime                                                     NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`           bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
    `update_by`           bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
    `is_deleted`          tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-商品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu`
VALUES (1534420706752856064, 1, '熊猫限定帆布袋', 1, 101, 1, '买一送一', 100,
        'http://www.zhengqingya.com:9002/default/b1f263076e2147388251db3682df5f46.jpg',
        '[{\"url\":\"https://www.zhengqingya.com/label.png\",\"name\":\"熊猫限定帆布袋\"}]',
        '[{\"url\":\"https://www.zhengqingya.com/label.png\",\"name\":\"熊猫限定帆布袋\"}]', 100, 0,
        '[{\"attrKeyId\":\"1\",\"attrKeyName\":\"颜色\",\"attrValueList\":[{\"attrValueId\":\"1\",\"attrValueName\":\"蓝色\"}]}]',
        1, '2021-08-25 09:00:00', '2021-08-26 23:59:59', 15, 1, 1,
        '[{\"code\":\"btn\",\"remark\":\"this is the add.\",\"sort\":1,\"name\":\"添加\",\"value\":\"add\"}]',
        '[{\"code\":\"btn\",\"remark\":\"this is the add.\",\"sort\":1,\"name\":\"添加\",\"value\":\"add\"}]', NULL,
        '2022-06-08 14:22:59', '2022-06-08 15:41:20', 0, 0, 0);

-- ----------------------------
-- Table structure for pms_spu_rate
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_rate`;
CREATE TABLE `pms_spu_rate`
(
    `id`              bigint(20) NOT NULL COMMENT '主键ID',
    `tenant_id`       int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `parent_id`       bigint(20) NOT NULL DEFAULT 0 COMMENT '父ID',
    `order_item_id`   bigint(20) NOT NULL COMMENT '订单详情id',
    `spu_id`          bigint(20) NOT NULL COMMENT '商品id',
    `sku_id`          bigint(20) NOT NULL COMMENT '商品sku-id',
    `spec_list`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品规格属性',
    `operator_type`   tinyint(2) NOT NULL COMMENT '操作人类型(1->买家 2->商家)',
    `operator_id`     bigint(20) NOT NULL COMMENT '操作人id',
    `operator_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '操作人名称',
    `operator_icon`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人头像',
    `resource_json`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评价图片或视频',
    `content`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评价内容',
    `is_show`         tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否显示(0->隐藏；1->显示)',
    `desc_level`      tinyint(4) NULL DEFAULT NULL COMMENT '描述相符',
    `logistics_level` tinyint(4) NULL DEFAULT NULL COMMENT '物流服务',
    `service_level`   tinyint(4) NULL DEFAULT NULL COMMENT '服务态度',
    `is_img`          tinyint(1) NOT NULL COMMENT '是否含有图片(0->否,1->是)',
    `is_video`        tinyint(1) NOT NULL COMMENT '是否含有视频(0->否,1->是)',
    `rate_type`       tinyint(4) NOT NULL COMMENT '评价分类(1->好评,2->差评,3->一般)',
    `create_time`     datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime                                                      NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`       bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
    `update_by`       bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
    `is_deleted`      tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-商品评价' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_rate
-- ----------------------------

-- ----------------------------
-- Table structure for pms_spu_rate_reply_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_rate_reply_relation`;
CREATE TABLE `pms_spu_rate_reply_relation`
(
    `id`              bigint(20) NOT NULL COMMENT 'ID',
    `tenant_id`       int(11) UNSIGNED NOT NULL COMMENT '租户ID',
    `product_id`      bigint(20) NOT NULL COMMENT '商品id',
    `product_rate_id` bigint(20) NOT NULL COMMENT '商品评价信息id',
    `replier_id`      bigint(20) NOT NULL COMMENT '回复人id',
    `replier_name`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '回复人名称',
    `content`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容',
    `is_show`         tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否显示(0->隐藏；1->显示)',
    `create_time`     datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime                                                      NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`       bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
    `update_by`       bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
    `is_deleted`      tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-商品评价关联回复' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_rate_reply_relation
-- ----------------------------

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`   int(11) NOT NULL COMMENT '租户ID',
    `openid`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid',
    `nickname`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `phone`       varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
    `sex`         tinyint(4) NULL DEFAULT NULL COMMENT '性别(0:未知 1:男  2:女)',
    `birthday`    date NULL DEFAULT NULL COMMENT '生日',
    `avatar_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_by`   bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
    `update_by`   bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
    `is_deleted`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_user
-- ----------------------------
INSERT INTO `ums_user`
VALUES (1, 1, '666', '郑清', '15183304000', 1, '2022-06-10', NULL, '2022-06-10 16:10:24', '2022-06-10 16:10:28', 1, 1, 0);

SET
FOREIGN_KEY_CHECKS = 1;
