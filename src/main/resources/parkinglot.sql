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

 Date: 08/04/2023 14:05:05
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total_spots` int(11) NULL DEFAULT NULL,
  `current_spots` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of garage
-- ----------------------------
BEGIN;
INSERT INTO `garage` (`id`, `total_spots`, `current_spots`) VALUES (1, 100, 100);
COMMIT;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plate` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `permit_type` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
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
  `membership` BIT(1),
  `reservation` BIT(1),
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
INSERT INTO `parklot` (`id`, `Type`, `A1`, `A2`, `A3`, `A4`, `A5`, `A6`, `A7`, `A8`, `A9`, `A10`, `A11`, `A12`, `A13`, `A14`, `A15`, `A16`, `A17`, `A18`, `A19`, `A20`, `A21`, `A22`, `A23`, `A24`, `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15`, `B16`, `B17`, `B18`, `B19`, `B20`, `B21`, `B22`, `B23`, `B24`) VALUES (1, 'Car', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY');
INSERT INTO `parklot` (`id`, `Type`, `A1`, `A2`, `A3`, `A4`, `A5`, `A6`, `A7`, `A8`, `A9`, `A10`, `A11`, `A12`, `A13`, `A14`, `A15`, `A16`, `A17`, `A18`, `A19`, `A20`, `A21`, `A22`, `A23`, `A24`, `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15`, `B16`, `B17`, `B18`, `B19`, `B20`, `B21`, `B22`, `B23`, `B24`) VALUES (2, 'Car', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY');
INSERT INTO `parklot` (`id`, `Type`, `A1`, `A2`, `A3`, `A4`, `A5`, `A6`, `A7`, `A8`, `A9`, `A10`, `A11`, `A12`, `A13`, `A14`, `A15`, `A16`, `A17`, `A18`, `A19`, `A20`, `A21`, `A22`, `A23`, `A24`, `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15`, `B16`, `B17`, `B18`, `B19`, `B20`, `B21`, `B22`, `B23`, `B24`) VALUES (3, 'Car', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'sss', 'EMPTY', 'sss', 'sss', 'sss', 'sss', 'sss', 'EMPTY', 'sss', 'sss', 'sss', 'EMPTY', 'sss', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'sss', 'EMPTY', 'sss', 'sss', 'sss', 'sss', 'sss', 'EMPTY', 'sss', 'sss', 'EMPTY', 'sss', 'sss', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY');
INSERT INTO `parklot` (`id`, `Type`, `A1`, `A2`, `A3`, `A4`, `A5`, `A6`, `A7`, `A8`, `A9`, `A10`, `A11`, `A12`, `A13`, `A14`, `A15`, `A16`, `A17`, `A18`, `A19`, `A20`, `A21`, `A22`, `A23`, `A24`, `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15`, `B16`, `B17`, `B18`, `B19`, `B20`, `B21`, `B22`, `B23`, `B24`) VALUES (4, 'Car', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY');
INSERT INTO `parklot` (`id`, `Type`, `A1`, `A2`, `A3`, `A4`, `A5`, `A6`, `A7`, `A8`, `A9`, `A10`, `A11`, `A12`, `A13`, `A14`, `A15`, `A16`, `A17`, `A18`, `A19`, `A20`, `A21`, `A22`, `A23`, `A24`, `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15`, `B16`, `B17`, `B18`, `B19`, `B20`, `B21`, `B22`, `B23`, `B24`) VALUES (5, 'Car', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY');
INSERT INTO `parklot` (`id`, `Type`, `A1`, `A2`, `A3`, `A4`, `A5`, `A6`, `A7`, `A8`, `A9`, `A10`, `A11`, `A12`, `A13`, `A14`, `A15`, `A16`, `A17`, `A18`, `A19`, `A20`, `A21`, `A22`, `A23`, `A24`, `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15`, `B16`, `B17`, `B18`, `B19`, `B20`, `B21`, `B22`, `B23`, `B24`) VALUES (6, 'Car', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY');
INSERT INTO `parklot` (`id`, `Type`, `A1`, `A2`, `A3`, `A4`, `A5`, `A6`, `A7`, `A8`, `A9`, `A10`, `A11`, `A12`, `A13`, `A14`, `A15`, `A16`, `A17`, `A18`, `A19`, `A20`, `A21`, `A22`, `A23`, `A24`, `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15`, `B16`, `B17`, `B18`, `B19`, `B20`, `B21`, `B22`, `B23`, `B24`) VALUES (7, 'Motorcycle', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY');
INSERT INTO `parklot` (`id`, `Type`, `A1`, `A2`, `A3`, `A4`, `A5`, `A6`, `A7`, `A8`, `A9`, `A10`, `A11`, `A12`, `A13`, `A14`, `A15`, `A16`, `A17`, `A18`, `A19`, `A20`, `A21`, `A22`, `A23`, `A24`, `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15`, `B16`, `B17`, `B18`, `B19`, `B20`, `B21`, `B22`, `B23`, `B24`) VALUES (8, 'Motorcycle', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY');
INSERT INTO `parklot` (`id`, `Type`, `A1`, `A2`, `A3`, `A4`, `A5`, `A6`, `A7`, `A8`, `A9`, `A10`, `A11`, `A12`, `A13`, `A14`, `A15`, `A16`, `A17`, `A18`, `A19`, `A20`, `A21`, `A22`, `A23`, `A24`, `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15`, `B16`, `B17`, `B18`, `B19`, `B20`, `B21`, `B22`, `B23`, `B24`) VALUES (9, 'Motorcycle', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY');
INSERT INTO `parklot` (`id`, `Type`, `A1`, `A2`, `A3`, `A4`, `A5`, `A6`, `A7`, `A8`, `A9`, `A10`, `A11`, `A12`, `A13`, `A14`, `A15`, `A16`, `A17`, `A18`, `A19`, `A20`, `A21`, `A22`, `A23`, `A24`, `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15`, `B16`, `B17`, `B18`, `B19`, `B20`, `B21`, `B22`, `B23`, `B24`) VALUES (10, 'Bicycle', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY', 'EMPTY');
INSERT INTO `appointment` (`id`, `name`, `date`, `license`, `parklot`) VALUES (1, 'stc', '2023/3/26 13:00-14:00', 'ABC', 1);
INSERT INTO `appointment` (`id`, `name`, `date`, `license`, `parklot`) VALUES (2, 'stc', NULL, NULL, NULL);
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

-- ----------------------------
-- Table structure for memberfee
-- ----------------------------
DROP TABLE IF EXISTS `memberfee`;
CREATE TABLE `memberfee` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `monthly_pay` int(11) DEFAULT NULL,
                             `yearly_pay` int(11) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
