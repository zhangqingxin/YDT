/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50616
 Source Host           : localhost
 Source Database       : yongdiantong

 Target Server Version : 50616
 File Encoding         : utf-8

 Date: 08/24/2014 13:24:29 PM
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `back_treenode`
-- ----------------------------
BEGIN;
INSERT INTO `back_treenode` VALUES ('1', null, '系统管理', null, '0'), ('2', '/admin/dianfei/orderadmin', '电费交费', '1', '1'), ('3', '/admin/yewubanli/orderadmin', '业务办理', '1', '1');
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
INSERT INTO `meta_dianfei` VALUES ('1', '123123', '11', '123123', '123', '1', '2014-08-21 01:20:19', '23123', '0', '0', '123123123123');
COMMIT;

-- ----------------------------
--  Table structure for `meta_dianfei_meta_image`
-- ----------------------------
DROP TABLE IF EXISTS `meta_dianfei_meta_image`;
CREATE TABLE `meta_dianfei_meta_image` (
  `meta_dianfei_id` bigint(20) NOT NULL,
  `imagelist_id` bigint(20) NOT NULL,
  UNIQUE KEY `imagelist_id` (`imagelist_id`),
  KEY `FK315D96E21535AD70` (`meta_dianfei_id`),
  KEY `FK315D96E275E7D758` (`imagelist_id`),
  CONSTRAINT `FK315D96E275E7D758` FOREIGN KEY (`imagelist_id`) REFERENCES `meta_image` (`id`),
  CONSTRAINT `FK315D96E21535AD70` FOREIGN KEY (`meta_dianfei_id`) REFERENCES `meta_dianfei` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `date` datetime DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `isdelete` int(11) NOT NULL,
  `orderstate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5392558B47140EFE` (`user_id`),
  CONSTRAINT `FK5392558B47140EFE` FOREIGN KEY (`user_id`) REFERENCES `meta_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `meta_image`
-- ----------------------------
DROP TABLE IF EXISTS `meta_image`;
CREATE TABLE `meta_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) DEFAULT NULL,
  `size` bigint(20) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
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
  `date` datetime DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `isdelete` int(11) NOT NULL,
  `orderNum` varchar(255) DEFAULT NULL,
  `orderstate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK47F6876847140EFE` (`user_id`),
  CONSTRAINT `FK47F6876847140EFE` FOREIGN KEY (`user_id`) REFERENCES `meta_customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `meta_yewubanli`
-- ----------------------------
BEGIN;
INSERT INTO `meta_yewubanli` VALUES ('1', '87674546456487686', '11.12', '15588877655', '1', '1', '2014-08-22 23:04:36', '98798789', '0', '123456', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
