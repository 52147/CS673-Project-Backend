/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50740 (5.7.40-log)
 Source Host           : localhost:3306
 Source Schema         : parking

 Target Server Type    : MySQL
 Target Server Version : 50740 (5.7.40-log)
 File Encoding         : 65001

 Date: 20/02/2023 21:13:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
    `id` int(11) NOT NULL auto_increment,
    `username` varchar(255) default NULL,
    `password` varchar(255) default NULL,
    `role` int(11) default 3 COMMENT '1: super admin, 2: admin, 3: user',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `username`, `password`, `role`) VALUES ('0', 'admin', 'admin', 1);

-- ----------------------------
-- Table structure for `parkinfo`
-- ----------------------------
DROP TABLE IF EXISTS `parkinfo`;
CREATE TABLE `parkinfo` (
    `id` int(11) NOT NULL auto_increment,
    `park_num` int(11) default NULL,
    `card_num` varchar(50) default NULL,
    `plate` varchar(50) default NULL ,
    `entrance` datetime default NULL,
    PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parkinfo
-- ----------------------------

-- ----------------------------
-- Table structure for parkforall
-- ----------------------------
DROP TABLE IF EXISTS `parkforall`;
CREATE TABLE `parkforall`  (
                               `id` int(11) NOT NULL,
                               `card_num` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
                               `park_num` int(11) NULL DEFAULT NULL,
                               `plate` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
                               `entrance` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
                               `exit` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of parkforall
-- ----------------------------

