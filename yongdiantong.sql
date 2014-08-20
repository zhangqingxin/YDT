/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50616
 Source Host           : localhost
 Source Database       : yongdiantong

 Target Server Version : 50616
 File Encoding         : utf-8

 Date: 08/21/2014 01:34:43 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `back_adminuser`
-- ----------------------------
DROP TABLE IF EXISTS `back_adminuser`;
CREATE TABLE `back_adminuser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `isAdmin` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `showname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `back_adminuser`
-- ----------------------------
BEGIN;
INSERT INTO `back_adminuser` VALUES ('1', b'1', '11111', null, 'test');
COMMIT;

-- ----------------------------
--  Table structure for `back_treenode`
-- ----------------------------
DROP TABLE IF EXISTS `back_treenode`;
CREATE TABLE `back_treenode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `back_treenode`
-- ----------------------------
BEGIN;
INSERT INTO `back_treenode` VALUES ('1', null, '系统管理', null, '0'), ('2', '/admin/dianfei/orderadmin', '电费交费', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `meta_customer`
-- ----------------------------
DROP TABLE IF EXISTS `meta_customer`;
CREATE TABLE `meta_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `registerId` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `meta_customer`
-- ----------------------------
BEGIN;
INSERT INTO `meta_customer` VALUES ('1', '测试用户的测试地址', 'zhangqingxin@163.com', '111111', '张小新', '123456789', '15588855115', 'zqx');
COMMIT;

-- ----------------------------
--  Table structure for `meta_dianfei`
-- ----------------------------
DROP TABLE IF EXISTS `meta_dianfei`;
CREATE TABLE `meta_dianfei` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `isdelete` int(11) NOT NULL,
  `orderstate` varchar(255) DEFAULT NULL,
  `orderNum` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD3495A9E47140EFE` (`user_id`),
  CONSTRAINT `FKD3495A9E47140EFE` FOREIGN KEY (`user_id`) REFERENCES `meta_customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `meta_dianfei`
-- ----------------------------
BEGIN;
INSERT INTO `meta_dianfei` VALUES ('1', '123123', '11', '123123', '123', '1', '2014-08-21 01:20:19', '23123', '0', '1', '123123123123');
COMMIT;

-- ----------------------------
--  Table structure for `meta_gongchengfuwu`
-- ----------------------------
DROP TABLE IF EXISTS `meta_gongchengfuwu`;
CREATE TABLE `meta_gongchengfuwu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5392558B47140EFE` (`user_id`),
  CONSTRAINT `FK5392558B47140EFE` FOREIGN KEY (`user_id`) REFERENCES `meta_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `meta_yewubanli`
-- ----------------------------
DROP TABLE IF EXISTS `meta_yewubanli`;
CREATE TABLE `meta_yewubanli` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK47F6876847140EFE` (`user_id`),
  CONSTRAINT `FK47F6876847140EFE` FOREIGN KEY (`user_id`) REFERENCES `meta_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
