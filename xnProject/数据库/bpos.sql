/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.157
 Source Server Type    : MySQL
 Source Server Version : 100313
 Source Host           : 192.168.0.157:3306
 Source Schema         : bpos

 Target Server Type    : MySQL
 Target Server Version : 100313
 File Encoding         : 65001

 Date: 15/05/2019 10:14:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blocks
-- ----------------------------
DROP TABLE IF EXISTS `blocks`;
CREATE TABLE `blocks`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `height` int(10) UNSIGNED NOT NULL,
  `blockhash` char(65) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `confirmations` int(10) NOT NULL,
  `amount` double NOT NULL,
  `difficulty` double NOT NULL,
  `time` int(11) NOT NULL,
  `accounted` tinyint(1) NOT NULL DEFAULT 0,
  `account_id` int(255) UNSIGNED NULL DEFAULT NULL,
  `worker_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'unknown',
  `shares` bigint(30) UNSIGNED NULL DEFAULT NULL,
  `share_id` bigint(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `height`(`height`, `blockhash`) USING BTREE,
  INDEX `time`(`time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Discovered blocks persisted from Litecoin Service' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for coin_addresses
-- ----------------------------
DROP TABLE IF EXISTS `coin_addresses`;
CREATE TABLE `coin_addresses`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `currency` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `coin_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ap_threshold` float(6, 3) NULL DEFAULT 0,
  `reward_balance` decimal(50, 30) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account_id`(`account_id`) USING BTREE,
  INDEX `coin_address`(`coin_address`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for error
-- ----------------------------
DROP TABLE IF EXISTS `error`;
CREATE TABLE `error`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `plugins_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 685 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for invitations
-- ----------------------------
DROP TABLE IF EXISTS `invitations`;
CREATE TABLE `invitations`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` int(11) UNSIGNED NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token_id` int(11) NOT NULL,
  `is_activated` tinyint(1) NOT NULL DEFAULT 0,
  `time` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for minerstats
-- ----------------------------
DROP TABLE IF EXISTS `minerstats`;
CREATE TABLE `minerstats`  (
  `id` bigint(20) NOT NULL,
  `poolid` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `miner` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `worker` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `hashrate` double NOT NULL DEFAULT 0,
  `sharespersecond` double NOT NULL DEFAULT 0,
  `created` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for monitoring
-- ----------------------------
DROP TABLE IF EXISTS `monitoring`;
CREATE TABLE `monitoring`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Monitoring events from cronjobs' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` int(10) UNSIGNED NOT NULL,
  `header` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  `active` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notification_settings
-- ----------------------------
DROP TABLE IF EXISTS `notification_settings`;
CREATE TABLE `notification_settings`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `account_id` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account_id_type`(`account_id`, `type`) USING BTREE,
  INDEX `account_id`(`account_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `data` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `time` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  `account_id` int(10) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `active`(`active`) USING BTREE,
  INDEX `data`(`data`) USING BTREE,
  INDEX `account_id`(`account_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for payouts
-- ----------------------------
DROP TABLE IF EXISTS `payouts`;
CREATE TABLE `payouts`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `time` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  `completed` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `account_id`(`account_id`, `completed`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for plugins_state
-- ----------------------------
DROP TABLE IF EXISTS `plugins_state`;
CREATE TABLE `plugins_state`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `success` int(11) NULL DEFAULT NULL,
  `failed` int(11) NULL DEFAULT NULL,
  `plugins_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pool_worker
-- ----------------------------
DROP TABLE IF EXISTS `pool_worker`;
CREATE TABLE `pool_worker`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `account_id` int(255) NOT NULL,
  `username` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `difficulty` float NOT NULL DEFAULT 0,
  `monitor` tinyint(1) NOT NULL DEFAULT 0,
  `last_access_time` timestamp(0) NOT NULL DEFAULT current_timestamp(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `account_id`(`account_id`) USING BTREE,
  INDEX `pool_worker_username`(`username`(10)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for poolstats
-- ----------------------------
DROP TABLE IF EXISTS `poolstats`;
CREATE TABLE `poolstats`  (
  `id` bigint(20) NOT NULL,
  `poolid` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `connectedminers` int(11) NOT NULL DEFAULT 0,
  `poolhashrate` double NOT NULL DEFAULT 0,
  `sharespersecond` double NOT NULL DEFAULT 0,
  `networkhashrate` double NOT NULL DEFAULT 0,
  `networkdifficulty` double NOT NULL DEFAULT 0,
  `lastnetworkblocktime` timestamp(0) NULL DEFAULT NULL,
  `blockheight` bigint(20) NOT NULL DEFAULT 0,
  `connectedpeers` int(11) NOT NULL DEFAULT 0,
  `created` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for settings
-- ----------------------------
DROP TABLE IF EXISTS `settings`;
CREATE TABLE `settings`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `setting`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shares
-- ----------------------------
DROP TABLE IF EXISTS `shares`;
CREATE TABLE `shares`  (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `rem_host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `our_result` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `upstream_result` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reason` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `solution` varchar(257) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `difficulty` float NOT NULL DEFAULT 0,
  `time` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `time`(`time`) USING BTREE,
  INDEX `upstream_result`(`upstream_result`) USING BTREE,
  INDEX `our_result`(`our_result`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `shares_username`(`username`(10)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1324030 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shares_archive
-- ----------------------------
DROP TABLE IF EXISTS `shares_archive`;
CREATE TABLE `shares_archive`  (
  `id` bigint(30) UNSIGNED NOT NULL AUTO_INCREMENT,
  `share_id` bigint(30) UNSIGNED NOT NULL,
  `username` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `our_result` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `upstream_result` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `block_id` int(10) UNSIGNED NOT NULL,
  `difficulty` float NOT NULL DEFAULT 0,
  `time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `share_id`(`share_id`) USING BTREE,
  INDEX `time`(`time`) USING BTREE,
  INDEX `our_result`(`our_result`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Archive shares for potential later debugging purposes' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shares_copy
-- ----------------------------
DROP TABLE IF EXISTS `shares_copy`;
CREATE TABLE `shares_copy`  (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `rem_host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `our_result` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `upstream_result` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reason` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `solution` varchar(257) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `difficulty` float NOT NULL DEFAULT 0,
  `time` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `time`(`time`) USING BTREE,
  INDEX `upstream_result`(`upstream_result`) USING BTREE,
  INDEX `our_result`(`our_result`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `shares_username`(`username`(10)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 631437 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shares_history
-- ----------------------------
DROP TABLE IF EXISTS `shares_history`;
CREATE TABLE `shares_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `difficulty` float NOT NULL,
  `count` int(11) NOT NULL,
  `time` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  `our_result` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y',
  PRIMARY KEY (`id`, `time`) USING BTREE,
  INDEX `time`(`time`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `our_result`(`our_result`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4022704 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic PARTITION BY RANGE (unix_timestamp(`time`))
PARTITIONS 17
(PARTITION `p_shares_history20190430` VALUES LESS THAN (1556596800) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190501` VALUES LESS THAN (1556683200) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190502` VALUES LESS THAN (1556769600) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190503` VALUES LESS THAN (1556856000) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190504` VALUES LESS THAN (1556942400) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190505` VALUES LESS THAN (1557028800) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190506` VALUES LESS THAN (1557115200) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190507` VALUES LESS THAN (1557201600) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190508` VALUES LESS THAN (1557288000) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190509` VALUES LESS THAN (1557374400) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190510` VALUES LESS THAN (1557460800) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190511` VALUES LESS THAN (1557547200) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190512` VALUES LESS THAN (1557633600) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190513` VALUES LESS THAN (1557720000) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190514` VALUES LESS THAN (1557806400) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190515` VALUES LESS THAN (1557892800) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p_shares_history20190516` VALUES LESS THAN (1557979200) ENGINE = InnoDB) MAX_ROWS = 0 MIN_ROWS = 0 )
;

-- ----------------------------
-- Table structure for shares_middle
-- ----------------------------
DROP TABLE IF EXISTS `shares_middle`;
CREATE TABLE `shares_middle`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '子账号+矿机',
  `difficulty` float NULL DEFAULT NULL COMMENT '算力',
  `count` int(11) NULL DEFAULT NULL COMMENT '数据条数',
  `time` timestamp(0) NOT NULL DEFAULT current_timestamp(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '时间',
  `our_result` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 843762 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for statistics_shares
-- ----------------------------
DROP TABLE IF EXISTS `statistics_shares`;
CREATE TABLE `statistics_shares`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` int(10) UNSIGNED NOT NULL,
  `block_id` int(10) UNSIGNED NOT NULL,
  `valid` bigint(20) NOT NULL,
  `invalid` bigint(20) NOT NULL DEFAULT 0,
  `pplns_valid` bigint(20) NOT NULL,
  `pplns_invalid` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `account_id`(`account_id`) USING BTREE,
  INDEX `block_id`(`block_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for statistics_users
-- ----------------------------
DROP TABLE IF EXISTS `statistics_users`;
CREATE TABLE `statistics_users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `hashrate` bigint(20) UNSIGNED NOT NULL,
  `workers` int(11) NOT NULL,
  `sharerate` float NOT NULL,
  `timestamp` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `account_id_timestamp`(`account_id`, `timestamp`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for statistics_workers
-- ----------------------------
DROP TABLE IF EXISTS `statistics_workers`;
CREATE TABLE `statistics_workers`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `difficulty` float NOT NULL,
  `count` int(11) NOT NULL,
  `time` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  `coin_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `our_result` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y',
  PRIMARY KEY (`id`, `time`) USING BTREE,
  INDEX `time`(`time`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `coin_address`(`coin_address`) USING BTREE,
  INDEX `our_result`(`our_result`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic PARTITION BY RANGE (unix_timestamp(`time`))
PARTITIONS 1
(PARTITION `p_statistics_workers20190212` VALUES LESS THAN (1549947600) ENGINE = InnoDB) MAX_ROWS = 0 MIN_ROWS = 0 )
;

-- ----------------------------
-- Table structure for t_cointype
-- ----------------------------
DROP TABLE IF EXISTS `t_cointype`;
CREATE TABLE `t_cointype`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'coinId',
  `coin_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币种名',
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
-- Table structure for token_types
-- ----------------------------
DROP TABLE IF EXISTS `token_types`;
CREATE TABLE `token_types`  (
  `id` tinyint(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `expiration` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tokens
-- ----------------------------
DROP TABLE IF EXISTS `tokens`;
CREATE TABLE `tokens`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `token` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` tinyint(4) NOT NULL,
  `time` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE,
  INDEX `account_id`(`account_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for transactions
-- ----------------------------
DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `account_id` int(255) UNSIGNED NOT NULL,
  `type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `coin_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `amount` decimal(50, 30) NULL DEFAULT 0,
  `block_id` int(255) NULL DEFAULT NULL,
  `timestamp` timestamp(0) NOT NULL DEFAULT current_timestamp(0),
  `txid` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `archived` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `block_id`(`block_id`) USING BTREE,
  INDEX `account_id`(`account_id`) USING BTREE,
  INDEX `type`(`type`) USING BTREE,
  INDEX `archived`(`archived`) USING BTREE,
  INDEX `account_id_archived`(`account_id`, `archived`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2471 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_settings
-- ----------------------------
DROP TABLE IF EXISTS `user_settings`;
CREATE TABLE `user_settings`  (
  `account_id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`account_id`, `name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Procedure structure for Proc_createPartition_by_day
-- ----------------------------
DROP PROCEDURE IF EXISTS `Proc_createPartition_by_day`;
delimiter ;;
CREATE PROCEDURE `Proc_createPartition_by_day`(IN in_tablename VARCHAR(100))
BEGIN
  #每天自动增加分区...
set @c_day=DATE_SUB(curdate(),INTERVAL -2 DAY);
set @p_c_day=DATE_FORMAT(@c_day,'%Y%m%d');
set @p_name=concat('p_',in_tablename,@p_c_day);
set @p_sql=concat('ALTER TABLE  ',in_tablename,'  ADD PARTITION (PARTITION  ',@p_name,'   VALUES LESS THAN (UNIX_TIMESTAMP ("',@c_day,' 00:00:00")))');
select @p_sql;
PREPARE auto_create_partion from @p_sql;
execute auto_create_partion;
END
;;
delimiter ;

-- ----------------------------
-- Event structure for auto_createPartition
-- ----------------------------
DROP EVENT IF EXISTS `auto_createPartition`;
delimiter ;;
CREATE EVENT `auto_createPartition`
ON SCHEDULE
EVERY '1' DAY STARTS '2019-04-30 00:00:00'
ON COMPLETION PRESERVE
COMMENT 'shares_history每天自动增加分区'
DO CALL Proc_createPartition_by_day('shares_history')
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
