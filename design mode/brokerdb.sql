/*
 Navicat MySQL Data Transfer

 Source Server         : bookstore2
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : brokerdb

 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 05/29/2017 17:53:08 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `br_order`
-- ----------------------------
DROP TABLE IF EXISTS `br_order`;
CREATE TABLE `br_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `t_given_price` double(10,2) NOT NULL,
  `t_type` varchar(255) NOT NULL,
  `t_number` int(10) unsigned DEFAULT NULL,
  `t_left_number` int(10) unsigned DEFAULT NULL,
  `t_time` datetime DEFAULT NULL,
  `t_firm` int(10) unsigned DEFAULT NULL,
  `t_productCode` int(10) unsigned DEFAULT NULL,
  `t_possession` varchar(3) DEFAULT NULL,
  `t_status` varchar(10) NOT NULL,
  `t_traderorderid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `firm` (`t_firm`),
  KEY `productCode` (`t_productCode`),
  KEY `t_traderorderid` (`t_traderorderid`),
  CONSTRAINT `t_firm` FOREIGN KEY (`t_firm`) REFERENCES `firm` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `t_productCode` FOREIGN KEY (`t_productCode`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `br_order`
-- ----------------------------
BEGIN;
INSERT INTO `br_order` VALUES ('27', '13.60', '1', '10', '1', '2017-05-23 20:18:46', '1', '1', 'aid', 'canceled', '1'), ('28', '13.60', '1', '10', '1', '2017-05-23 20:19:34', '1', '1', 'aid', 'canceled', '1'), ('29', '13.60', '1', '10', '1', '2017-05-23 20:20:34', '1', '1', 'aid', 'canceled', '1'), ('30', '13.60', '1', '10', '1', '2017-05-23 20:21:23', '1', '1', 'aid', 'canceled', '1'), ('31', '13.60', '1', '10', '1', '2017-05-26 18:52:23', '1', '1', 'aid', 'canceled', '1'), ('32', '13.60', '1', '10', '1', '2017-05-26 18:53:09', '1', '1', 'aid', 'canceled', '1'), ('33', '13.60', '1', '10', '1', '2017-05-26 18:53:48', '1', '1', 'aid', 'canceled', '1'), ('34', '13.60', '1', '10', '1', '2017-05-26 18:55:29', '1', '1', 'aid', 'canceled', '1'), ('35', '13.60', '1', '10', '1', '2017-05-26 18:56:00', '1', '1', 'aid', 'canceled', '1'), ('36', '13.60', '1', '10', '1', '2017-05-26 18:57:06', '1', '1', 'aid', 'canceled', '1');
COMMIT;

-- ----------------------------
--  Table structure for `br_transaction`
-- ----------------------------
DROP TABLE IF EXISTS `br_transaction`;
CREATE TABLE `br_transaction` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `price` double(10,2) DEFAULT NULL,
  `number` int(10) unsigned DEFAULT NULL,
  `t_time` datetime NOT NULL,
  `askId` int(10) unsigned NOT NULL,
  `bidId` int(10) unsigned NOT NULL,
  `askFirm` varchar(255) DEFAULT NULL,
  `bidFirm` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `askId` (`askId`),
  KEY `bidId` (`bidId`),
  CONSTRAINT `askId` FOREIGN KEY (`askId`) REFERENCES `br_order` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `bidId` FOREIGN KEY (`bidId`) REFERENCES `br_order` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `br_transaction`
-- ----------------------------
BEGIN;
INSERT INTO `br_transaction` VALUES ('1', '15.60', '10', '2017-05-26 18:55:29', '27', '28', null, null), ('2', '15.60', '10', '2017-05-26 18:56:00', '27', '28', null, null), ('3', '15.60', '10', '2017-05-26 18:57:06', '27', '28', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `firm`
-- ----------------------------
DROP TABLE IF EXISTS `firm`;
CREATE TABLE `firm` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ip` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `firm`
-- ----------------------------
BEGIN;
INSERT INTO `firm` VALUES ('1', 'jiaoda', null, '127.0.0.1');
COMMIT;

-- ----------------------------
--  Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(6) NOT NULL,
  `listingDate` date DEFAULT NULL,
  `deliveryDate` datetime DEFAULT NULL,
  `productName` varchar(10) DEFAULT NULL,
  `category1` varchar(10) NOT NULL,
  `category2` varchar(10) NOT NULL,
  PRIMARY KEY (`id`,`category1`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `product`
-- ----------------------------
BEGIN;
INSERT INTO `product` VALUES ('1', '123456', null, null, null, '', '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
