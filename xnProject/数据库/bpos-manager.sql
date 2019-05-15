/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.157
 Source Server Type    : MySQL
 Source Server Version : 100313
 Source Host           : 192.168.0.157:3306
 Source Schema         : bpos-manager

 Target Server Type    : MySQL
 Target Server Version : 100313
 File Encoding         : 65001

 Date: 15/05/2019 10:13:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_cointype
-- ----------------------------
DROP TABLE IF EXISTS `t_cointype`;
CREATE TABLE `t_cointype`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'coinId',
  `coin_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币种名',
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_cointype
-- ----------------------------
INSERT INTO `t_cointype` VALUES (1, 'BTC');
INSERT INTO `t_cointype` VALUES (2, 'BCHABC');
INSERT INTO `t_cointype` VALUES (3, 'LTC');
INSERT INTO `t_cointype` VALUES (4, 'DASH');
INSERT INTO `t_cointype` VALUES (5, 'ETH');
INSERT INTO `t_cointype` VALUES (6, 'ZEC');
INSERT INTO `t_cointype` VALUES (7, 'ETN');
INSERT INTO `t_cointype` VALUES (8, 'DCR');
INSERT INTO `t_cointype` VALUES (9, 'BCH');
INSERT INTO `t_cointype` VALUES (10, 'BSV');
INSERT INTO `t_cointype` VALUES (11, 'ZET');

-- ----------------------------
-- Table structure for t_merge
-- ----------------------------
DROP TABLE IF EXISTS `t_merge`;
CREATE TABLE `t_merge`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `mine_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '矿机名称',
  `hashrate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `power` int(255) NOT NULL COMMENT '矿机功耗 单位W',
  `coin_id` int(255) NOT NULL,
  `electricity` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `coin_id`(`coin_id`) USING BTREE,
  CONSTRAINT `t_merge_ibfk_1` FOREIGN KEY (`coin_id`) REFERENCES `t_cointype` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_merge
-- ----------------------------
INSERT INTO `t_merge` VALUES (1, '神马矿机 D1', '44.00T', 2200, 8, 0.4);
INSERT INTO `t_merge` VALUES (2, '蚂蚁矿机 DR5', '34.00T', 1800, 8, 0.4);
INSERT INTO `t_merge` VALUES (3, '芯动科技 A10', '485.00M', 700, 5, 0.4);
INSERT INTO `t_merge` VALUES (4, '神马矿机 M10S', '55.00T', 3575, 1, 0.4);
INSERT INTO `t_merge` VALUES (5, '芯动科技 T3', '43.00T', 2100, 1, 0.4);
INSERT INTO `t_merge` VALUES (6, '蚂蚁矿机 Z9', '40.8K', 1150, 6, 0.4);
INSERT INTO `t_merge` VALUES (7, '神马矿机 M10', '33.00T', 2180, 1, 0.4);
INSERT INTO `t_merge` VALUES (8, '蚂蚁矿机 S15', '28.00T', 1596, 1, 0.4);
INSERT INTO `t_merge` VALUES (9, '芯动科技 T2T-32T', '32.00T', 2200, 1, 0.4);
INSERT INTO `t_merge` VALUES (11, '思创优矿机 U1', '11.50T', 1600, 8, 0.4);
INSERT INTO `t_merge` VALUES (12, '蚂蚁矿机 T15', '23.00T', 1541, 1, 0.4);
INSERT INTO `t_merge` VALUES (14, '蚂蚁矿机 D5', '119.00G', 1566, 4, 0.4);
INSERT INTO `t_merge` VALUES (15, '蚂蚁矿机 S11', '20.50T', 1530, 1, 0.4);
INSERT INTO `t_merge` VALUES (16, '芯动科技 T2T-15T', '25.00T', 2150, 1, 0.4);
INSERT INTO `t_merge` VALUES (17, '蚂蚁矿机 E3', '190.00M', 810, 5, 0.4);
INSERT INTO `t_merge` VALUES (19, '阿瓦隆矿机 A911', '18.00T', 1440, 1, 0.4);
INSERT INTO `t_merge` VALUES (20, '雪豹矿机 B1+', '24.00T', 2320, 1, 0.4);
INSERT INTO `t_merge` VALUES (21, '芯动科技 A6', '1250.00M', 1580, 3, 0.4);
INSERT INTO `t_merge` VALUES (22, '芯动科技 T2', '17.20T', 1570, 1, 0.4);
INSERT INTO `t_merge` VALUES (23, '蚂蚁矿机 DR3', '7.80T', 1410, 8, 0.4);
INSERT INTO `t_merge` VALUES (24, '蚂蚁矿机 Z9mini', '10.00K', 300, 6, 0.4);
INSERT INTO `t_merge` VALUES (25, '芯动科技T1', '16.00T', 1500, 1, 0.4);
INSERT INTO `t_merge` VALUES (26, '阿瓦隆矿机 A921', '20.00T', 2050, 1, 0.4);
INSERT INTO `t_merge` VALUES (27, '雪豹矿机 B1', '16.00T', 1510, 1, 0.4);
INSERT INTO `t_merge` VALUES (28, '蚂蚁矿机 S9 Hydro', '18.00T', 1900, 1, 0.4);
INSERT INTO `t_merge` VALUES (29, '雪豹矿机 A1', '49.00T', 6210, 1, 0.4);
INSERT INTO `t_merge` VALUES (30, '蚂蚁矿机 S9j', '14.50T', 1520, 1, 0.4);
INSERT INTO `t_merge` VALUES (31, '蚂蚁矿机 S9i', '14.00T', 1460, 1, 0.4);
INSERT INTO `t_merge` VALUES (32, '翼比特矿机 E10', '18.00T', 2010, 1, 0.4);
INSERT INTO `t_merge` VALUES (33, '蚂蚁矿机 S9', '13.50T', 1395, 1, 0.4);
INSERT INTO `t_merge` VALUES (34, '芯动科技 A4+', '620.00M', 750, 3, 0.4);
INSERT INTO `t_merge` VALUES (35, '芯动科技 A7', '84.00G', 1660, 4, 0.4);
INSERT INTO `t_merge` VALUES (36, '蚂蚁矿机 S9i', '13.00T', 1400, 1, 0.4);
INSERT INTO `t_merge` VALUES (37, '蚂蚁矿机 S9i', '13.50T', 1490, 1, 0.4);
INSERT INTO `t_merge` VALUES (38, '阿瓦隆矿机 A841', '13.00T', 1450, 1, 0.4);
INSERT INTO `t_merge` VALUES (39, '阿瓦隆矿机 A851', '14.50T', 1680, 1, 0.4);
INSERT INTO `t_merge` VALUES (40, '阿瓦隆矿机 A821', '11.00T', 1250, 1, 0.4);
INSERT INTO `t_merge` VALUES (41, '芯动科技 A5+', '65.00G', 1510, 4, 0.4);
INSERT INTO `t_merge` VALUES (42, '翼比特矿机 E9.2', '12.00T', 1570, 1, 0.4);
INSERT INTO `t_merge` VALUES (43, '蚂蚁矿机 L3++', '580.00M', 1050, 3, 0.4);
INSERT INTO `t_merge` VALUES (44, '芯动科技 A5', '32.50G', 760, 4, 0.4);
INSERT INTO `t_merge` VALUES (45, '翼比特矿机E9.3', '16.00T', 2170, 1, 0.4);

-- ----------------------------
-- Table structure for t_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_setting`;
CREATE TABLE `t_setting`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '名称',
  `fee` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '费率',
  `blockPrice` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `rpchost` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'ip',
  `node` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '节点',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `marke` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '备注',
  `unit` bigint(255) NULL DEFAULT NULL,
  `isAccept` enum('Y','N') CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT 'Y' COMMENT 'y，n',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_setting
-- ----------------------------
INSERT INTO `t_setting` VALUES (1, 'eth', '1', 3.18, 'http://192.168.0.44:8982', 'eth1.xnpool.cn:8008,eth2.xnpool.cn:8008,eth3.xnpool.cn:8008', '戊二醛翁', '', 1000, 'Y');
INSERT INTO `t_setting` VALUES (2, 'ltc', '1', 50.00, 'http://192.168.0.44:8982', 'ltc.xnpool.cn:3333,ltc.xnpool.cn:25,ltc.xnpool.cn:8888', '11', '', 1024, 'Y');
INSERT INTO `t_setting` VALUES (3, 'btc', '1', 60.00, 'http://192.168.0.44:8982', 'ltc.xnpool.cn:3333,ltc.xnpool.cn:25,ltc.xnpool.cn:8888', '测试', NULL, 1024, 'Y');
INSERT INTO `t_setting` VALUES (4, 'etc', '1', 88.00, 'http://192.168.0.44:8982', 'ltc.xnpool.cn:3333,ltc.xnpool.cn:25,ltc.xnpool.cn:8888', '测试', NULL, 100, 'Y');

SET FOREIGN_KEY_CHECKS = 1;
