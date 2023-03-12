/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50634 (5.6.34)
 Source Host           : localhost:3306
 Source Schema         : parkinglot

 Target Server Type    : MySQL
 Target Server Version : 50634 (5.6.34)
 File Encoding         : 65001

 Date: 11/03/2023 23:42:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for billtable
-- ----------------------------
DROP TABLE IF EXISTS `billtable`;
CREATE TABLE `billtable` (
                             `id` int(11) NOT NULL,
                             `hour` int(11) DEFAULT NULL,
                             `cartype` varchar(255) DEFAULT '',
                             `firstprice` int(255) DEFAULT NULL,
                             `secondprice` int(11) DEFAULT NULL,
                             `maxprice` int(11) DEFAULT NULL,
                             `comment` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of billtable
-- ----------------------------
BEGIN;
INSERT INTO `billtable` (`id`, `hour`, `cartype`, `firstprice`, `secondprice`, `maxprice`, `comment`) VALUES (1, 2, 'bicycle', 2, 2, 2, '2222');
COMMIT;

-- ----------------------------
-- Table structure for parkforall
-- ----------------------------
DROP TABLE IF EXISTS `parkforall`;
CREATE TABLE `parkforall` (
                              `id` int(11) NOT NULL,
                              `card_num` varchar(255) DEFAULT NULL,
                              `park_num` int(11) DEFAULT NULL,
                              `plate` varchar(255) DEFAULT NULL,
                              `entrance` datetime DEFAULT NULL,
                              `exit` datetime DEFAULT NULL,
                              `parking_fee` int(11) DEFAULT NULL,
                              `total_parking_time` mediumtext,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of parkforall
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for parkinfo
-- ----------------------------
DROP TABLE IF EXISTS `parkinfo`;
CREATE TABLE `parkinfo` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `park_num` int(11) DEFAULT NULL,
                            `card_num` varchar(50) DEFAULT NULL,
                            `plate` varchar(50) DEFAULT NULL,
                            `entrance` datetime DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `plate` (`plate`) USING BTREE,
                            CONSTRAINT `parkinfo_ibfk_1` FOREIGN KEY (`plate`) REFERENCES `persons` (`plate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parkinfo
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for parkspace
-- ----------------------------
DROP TABLE IF EXISTS `parkspace`;
CREATE TABLE `parkspace` (
                             `car` int(11) DEFAULT NULL,
                             `electric_car` int(11) DEFAULT NULL,
                             `truck` int(11) DEFAULT NULL,
                             `van` int(11) DEFAULT NULL,
                             `motor` int(11) DEFAULT NULL,
                             `disabled` int(11) DEFAULT NULL,
                             `id` int(11) NOT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of parkspace
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
                           `id` int(11) NOT NULL,
                           `parking_fee` varchar(255) DEFAULT NULL,
                           `plate` varchar(255) DEFAULT NULL,
                           `car_type` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of payment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `username` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `role` int(11) DEFAULT '3' COMMENT '1: super admin, 2: admin, 3: user',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `username`, `password`, `role`) VALUES (1, 'admin', 'admin', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
