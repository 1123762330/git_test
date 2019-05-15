/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.157
 Source Server Type    : MySQL
 Source Server Version : 100313
 Source Host           : 192.168.0.157:3306
 Source Schema         : bpos-reward

 Target Server Type    : MySQL
 Target Server Version : 100313
 File Encoding         : 65001

 Date: 15/05/2019 10:13:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for reward_balance
-- ----------------------------
DROP TABLE IF EXISTS `reward_balance`;
CREATE TABLE `reward_balance`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '子账户名称',
  `account_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子账户地址',
  `account_balance` double(250, 2) NULL DEFAULT NULL COMMENT '子账户余额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reward_balance
-- ----------------------------
INSERT INTO `reward_balance` VALUES (38, 'zhmi0909', 'LR66pDXqHuqK25WMukR3zezYdnEpY1iMxv', 0.80);

-- ----------------------------
-- Table structure for reward_log
-- ----------------------------
DROP TABLE IF EXISTS `reward_log`;
CREATE TABLE `reward_log`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `coin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ispaid` int(11) NOT NULL,
  `isNotPaid` double NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reward_log
-- ----------------------------
INSERT INTO `reward_log` VALUES (68, 'ltc', 'zhmi0909', 'LR66pDXqHuqK25WMukR3zezYdnEpY1iMxv', 'zhmi0909:1000.0:0.19999999999999996', 1000, 0.19999999999999996, '2019-05-14 10:33:26');
INSERT INTO `reward_log` VALUES (69, 'ltc', 'zhmi0909', 'LR66pDXqHuqK25WMukR3zezYdnEpY1iMxv', 'zhmi0909:1000.0:0.8', 1000, 0.8, '2019-05-14 10:42:02');

-- ----------------------------
-- Table structure for reward_setting
-- ----------------------------
DROP TABLE IF EXISTS `reward_setting`;
CREATE TABLE `reward_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coin` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币种名称，唯一',
  `threshold` double(255, 0) NULL DEFAULT NULL COMMENT '单份奖励需要的余额数量阈值',
  `rewardCount` int(11) NULL DEFAULT NULL COMMENT '单份奖励对应的奖励额度',
  `postUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '奖励份额提交地址',
  `enabled` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否启用奖励',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reward_setting
-- ----------------------------
INSERT INTO `reward_setting` VALUES (1, 'ltc', 1, 1000, 'http://ltcapi.xnpool.cn/mpos-doge', 'Y');

SET FOREIGN_KEY_CHECKS = 1;
