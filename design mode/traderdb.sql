/*
 Navicat MySQL Data Transfer

 Source Server         : bookstore2
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : traderdb

 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 05/29/2017 17:52:59 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `broker`
-- ----------------------------
DROP TABLE IF EXISTS `broker`;
CREATE TABLE `broker` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `broker`
-- ----------------------------
BEGIN;
INSERT INTO `broker` VALUES ('4', 'qingqing'), ('5', 'qingqing'), ('6', 'qingqing'), ('7', 'qingqing'), ('8', 'qingqing'), ('9', 'qingqing'), ('10', 'qingqing'), ('11', 'qingqing'), ('12', 'qingqing');
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
  `productName` varchar(255) DEFAULT NULL,
  `category1` varchar(10) NOT NULL,
  `category2` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `product`
-- ----------------------------
BEGIN;
INSERT INTO `product` VALUES ('3', '234', '2017-05-26', '2017-05-26 18:18:32', 'rice', '', ''), ('4', '234', '2017-05-26', '2017-05-26 18:21:05', 'rice', '', ''), ('5', '234', '2017-05-26', '2017-05-26 18:22:00', 'rice', '', ''), ('6', '234', '2017-05-26', '2017-05-26 18:22:48', 'rice', '', ''), ('7', '234', '2017-05-26', '2017-05-26 18:26:26', 'rice', '', ''), ('8', '234', '2017-05-26', '2017-05-26 18:27:12', 'rice', '', ''), ('9', '234', '2017-05-26', '2017-05-26 18:29:12', 'rice', '', ''), ('10', '234', '2017-05-26', '2017-05-26 18:30:04', 'rice', '', ''), ('11', '234', '2017-05-26', '2017-05-26 18:30:46', 'rice', '', '');
COMMIT;

-- ----------------------------
--  Table structure for `tr_order`
-- ----------------------------
DROP TABLE IF EXISTS `tr_order`;
CREATE TABLE `tr_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `t_given_price` double(10,0) NOT NULL,
  `t_status` varchar(10) NOT NULL,
  `t_left_number` int(10) NOT NULL,
  `t_time` date NOT NULL,
  `t_brokerid` int(10) unsigned NOT NULL,
  `t_productid` int(10) unsigned NOT NULL,
  `t_traderid` int(10) unsigned NOT NULL,
  `t_brokerorderid` int(10) unsigned NOT NULL,
  `t_possession` varchar(3) NOT NULL,
  `t_number` int(10) NOT NULL,
  `t_type` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `brokerid` (`t_brokerid`) USING BTREE,
  KEY `productid` (`t_productid`) USING BTREE,
  KEY `traderid` (`t_traderid`) USING BTREE,
  KEY `brokerorderid` (`t_brokerorderid`),
  CONSTRAINT `brokerid` FOREIGN KEY (`t_brokerid`) REFERENCES `broker` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `productid` FOREIGN KEY (`t_productid`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `traderid` FOREIGN KEY (`t_traderid`) REFERENCES `trader` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `tr_order`
-- ----------------------------
BEGIN;
INSERT INTO `tr_order` VALUES ('4', '12', 'unfinished', '10', '2017-05-26', '8', '7', '6', '28', 'ask', '10', '1'), ('5', '12', 'unfinished', '10', '2017-05-26', '9', '8', '7', '28', 'ask', '10', '1'), ('6', '12', 'unfinished', '10', '2017-05-26', '10', '9', '8', '28', 'ask', '10', '1'), ('7', '12', 'unfinished', '10', '2017-05-26', '11', '10', '9', '28', 'ask', '10', '1'), ('8', '12', 'unfinished', '10', '2017-05-26', '12', '11', '10', '28', 'ask', '10', '1');
COMMIT;

-- ----------------------------
--  Table structure for `tr_transaction`
-- ----------------------------
DROP TABLE IF EXISTS `tr_transaction`;
CREATE TABLE `tr_transaction` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `price` float(10,0) unsigned NOT NULL,
  `number` int(10) unsigned NOT NULL,
  `t_time` date NOT NULL,
  `askid` int(10) unsigned NOT NULL,
  `bidid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `askid` (`askid`) USING BTREE,
  KEY `bidid` (`bidid`) USING BTREE,
  CONSTRAINT `askid2` FOREIGN KEY (`askid`) REFERENCES `tr_order` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `bidid2` FOREIGN KEY (`bidid`) REFERENCES `tr_order` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `tr_transaction`
-- ----------------------------
BEGIN;
INSERT INTO `tr_transaction` VALUES ('1', '13', '10', '2017-05-26', '6', '6'), ('2', '13', '10', '2017-05-26', '6', '6'), ('3', '13', '100', '2017-05-26', '7', '7'), ('4', '13', '100', '2017-05-26', '7', '7');
COMMIT;

-- ----------------------------
--  Table structure for `trader`
-- ----------------------------
DROP TABLE IF EXISTS `trader`;
CREATE TABLE `trader` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `trader`
-- ----------------------------
BEGIN;
INSERT INTO `trader` VALUES ('2', 'qq', '123'), ('3', 'qq', '123'), ('4', 'qq', '123'), ('5', 'qq', '123'), ('6', 'qq', '123'), ('7', 'qq', '123'), ('8', 'qq', '123'), ('9', 'qq', '123'), ('10', 'qq', '123');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
