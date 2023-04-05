/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50740 (5.7.40-log)
 Source Host           : localhost:3306
 Source Schema         : parkinglot

 Target Server Type    : MySQL
 Target Server Version : 50740 (5.7.40-log)
 File Encoding         : 65001

 Date: 04/04/2023 20:49:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `license` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parklot` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of appointment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for billtable
-- ----------------------------
DROP TABLE IF EXISTS `billtable`;
CREATE TABLE `billtable`  (
  `id` int(11) NOT NULL,
  `hour` int(11) NULL DEFAULT NULL,
  `car_type` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT '',
  `first_price` int(255) NULL DEFAULT NULL,
  `second_price` int(11) NULL DEFAULT NULL,
  `max_price` int(11) NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of billtable
-- ----------------------------
BEGIN;
INSERT INTO `billtable` (`id`, `hour`, `car_type`, `first_price`, `second_price`, `max_price`, `comment`) VALUES (1, 1, 'car', 0, 5, 40, ''), (2, 1, 'motorcycle', 0, 5, 40, ''), (3, 1, 'bicycle', 0, 5, 40, '');
COMMIT;

-- ----------------------------
-- Table structure for garage
-- ----------------------------
DROP TABLE IF EXISTS `garage`;
CREATE TABLE `garage`  (
  `id` int(11) NOT NULL,
  `total_spots` int(11) NULL DEFAULT NULL,
  `current_spots` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of garage
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int(11) NOT NULL,
  `plate` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `permite_type` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of member
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for parkforall
-- ----------------------------
DROP TABLE IF EXISTS `parkforall`;
CREATE TABLE `parkforall`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_num` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `park_num` int(11) NULL DEFAULT NULL,
  `plate` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `entrance` datetime NULL DEFAULT NULL,
  `exit_time` datetime NULL DEFAULT NULL,
  `parking_fee` int(11) NULL DEFAULT NULL,
  `total_parking_time` mediumtext CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `car_type` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of parkforall
-- ----------------------------
BEGIN;
INSERT INTO `parkforall` (`id`, `card_num`, `park_num`, `plate`, `entrance`, `exit_time`, `parking_fee`, `total_parking_time`, `car_type`) VALUES (1, '123', 23, '213', '2023-03-18 14:40:27', '2023-03-18 16:40:19', 123, '231', NULL);
COMMIT;

-- ----------------------------
-- Table structure for parkinfo
-- ----------------------------
DROP TABLE IF EXISTS `parkinfo`;
CREATE TABLE `parkinfo`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `park_num` int(11) NULL DEFAULT NULL,
  `card_num` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plate` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `entrance` datetime NULL DEFAULT NULL,
  `car_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `plate`(`plate`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of parkinfo
-- ----------------------------
BEGIN;
INSERT INTO `parkinfo` (`id`, `park_num`, `card_num`, `plate`, `entrance`, `car_type`) VALUES (4, 0, NULL, '123', '2023-03-18 14:25:47', NULL), (5, 0, NULL, 'gggg', '2023-03-18 14:37:46', NULL);
COMMIT;

-- ----------------------------
-- Table structure for parklot
-- ----------------------------
DROP TABLE IF EXISTS `parklot`;
CREATE TABLE `parklot`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `A1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A7` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A8` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A9` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A10` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A11` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A12` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A13` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A14` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A15` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A16` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A17` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A18` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A19` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A20` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A21` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A22` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A23` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `A24` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B7` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B8` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B9` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B10` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B11` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B12` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B13` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B14` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B15` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B16` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B17` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B18` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B19` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B20` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B21` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B22` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B23` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  `B24` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EMPTY',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 810 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of parklot
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for parkslot
-- ----------------------------
DROP TABLE IF EXISTS `parkslot`;
CREATE TABLE `parkslot`  (
  `id` int(11) NOT NULL,
  `type` varchar(11) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `park_id` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `plate` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of parkslot
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for parkspace
-- ----------------------------
DROP TABLE IF EXISTS `parkspace`;
CREATE TABLE `parkspace`  (
  `id` int(11) NOT NULL,
  `block` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `space_num` int(11) NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `is_reserve` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of parkspace
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment`  (
  `id` int(11) NOT NULL,
  `parking_fee` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `car_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of payment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
  `id` int(11) NOT NULL,
  `type` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_am_1-2` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_am_3-4` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_am_5-6` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_am_7-8` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_am_9-10` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_am_11-12` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_pm_1-2` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_pm_3-4` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_pm_5-6` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_pm_7-8` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T_pm_9-10` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `T-pm_11-12` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of reservation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for reserves
-- ----------------------------
DROP TABLE IF EXISTS `reserves`;
CREATE TABLE `reserves`  (
  `id` int(11) NOT NULL,
  `parking_space_id` int(11) NULL DEFAULT NULL,
  `reserve_start` datetime NULL DEFAULT NULL,
  `reserve_end` datetime NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of reserves
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` int(11) NULL DEFAULT 3 COMMENT '1: super admin, 2: admin, 3: user',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Q1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `A1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Q2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `A2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `username`, `password`, `role`, `email`, `phone`, `address`, `Q1`, `A1`, `Q2`, `A2`) VALUES (1, 'admin', 'admin', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL), (2, 'admin1', 'admin1', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
