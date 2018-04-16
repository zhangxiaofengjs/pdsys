-- --------------------------------------------------------
-- ホスト:                          127.0.0.1
-- サーバーのバージョン:                   5.7.21 - MySQL Community Server (GPL)
-- サーバー OS:                      Win64
-- HeidiSQL バージョン:               9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- pdsys のデータベース構造をダンプしています
CREATE DATABASE IF NOT EXISTS `pdsys` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pdsys`;

--  テーブル pdsys.bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT 'BOM品番',
  `c_name` varchar(50) NOT NULL COMMENT 'BOM名',
  `c_unit_id` int(11) NOT NULL COMMENT '单位ID',
  `c_supplier_id` int(11) NOT NULL COMMENT '供应商ID',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '原材(0) 包材(1)',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='BOM定义表';

-- テーブル pdsys.bom_tbl: ~12 rows (約) のデータをダンプしています
DELETE FROM `bom_tbl`;
/*!40000 ALTER TABLE `bom_tbl` DISABLE KEYS */;
INSERT INTO `bom_tbl` (`c_id`, `c_pn`, `c_name`, `c_unit_id`, `c_supplier_id`, `c_type`) VALUES
	(1, 'bom1', '杯身纸', 1, 1, 0),
	(2, 'bom2', '塑料包装袋', 2, 2, 1),
	(3, 'bom3', '杯底纸', 5, 2, 1),
	(4, 'bom4', '包装箱', 4, 1, 1),
	(5, 'bom5', '彩带', 1, 1, 0),
	(6, 'bom6', '塑料盒', 2, 2, 1),
	(7, '11', '22', 2, 2, 0),
	(8, '336', '3336', 4, 3, 1),
	(9, '222', '222', 12, 1, 0),
	(10, '333', '333', 1, 6, 1),
	(11, '888', '999', 4, 2, 0),
	(12, '111', '222', 1, 11, 0);
/*!40000 ALTER TABLE `bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.customer_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `customer_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '顾客名',
  `c_address` varchar(50) DEFAULT NULL COMMENT '地址',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='顾客表 ';

-- テーブル pdsys.customer_tbl: ~7 rows (約) のデータをダンプしています
DELETE FROM `customer_tbl`;
/*!40000 ALTER TABLE `customer_tbl` DISABLE KEYS */;
INSERT INTO `customer_tbl` (`c_id`, `c_name`, `c_address`, `c_phone`) VALUES
	(1, '王子造纸1', '开发区002号1', '888-888-5881'),
	(2, '王子造飞机11qq', '前门大街001号33', '888-888-88822'),
	(3, 'rf', '2232', '1232'),
	(4, '', '', ''),
	(8, '11', '3', '2'),
	(9, '2', '4', '3'),
	(10, '2222', '', '');
/*!40000 ALTER TABLE `customer_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- テーブル pdsys.delivery_bom_tbl: ~16 rows (約) のデータをダンプしています
DELETE FROM `delivery_bom_tbl`;
/*!40000 ALTER TABLE `delivery_bom_tbl` DISABLE KEYS */;
INSERT INTO `delivery_bom_tbl` (`c_id`, `c_delivery_id`, `c_ref_id`, `c_num`) VALUES
	(33, 1, 1, 111),
	(34, 8, 1, 6),
	(35, 8, 2, 6),
	(38, 6, 1, 1),
	(39, 6, 2, 1),
	(40, 1, 1, 1),
	(41, 9, 2, 1),
	(42, 1, 1, 1),
	(43, 10, 2, 1),
	(44, 10, 1, 1),
	(45, 1, 1, 1),
	(46, 11, 2, 1),
	(47, 11, 1, 1),
	(48, 17, 1, 999),
	(49, 17, 2, 999),
	(50, 17, 4, 999);
/*!40000 ALTER TABLE `delivery_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='出库单表备件';

-- テーブル pdsys.delivery_machine_part_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `delivery_machine_part_tbl`;
/*!40000 ALTER TABLE `delivery_machine_part_tbl` DISABLE KEYS */;
INSERT INTO `delivery_machine_part_tbl` (`c_id`, `c_delivery_id`, `c_ref_id`, `c_num`) VALUES
	(11, 18, 1, 1333),
	(12, 18, 5, 1333);
/*!40000 ALTER TABLE `delivery_machine_part_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='出库单表PN';

-- テーブル pdsys.delivery_pn_tbl: ~16 rows (約) のデータをダンプしています
DELETE FROM `delivery_pn_tbl`;
/*!40000 ALTER TABLE `delivery_pn_tbl` DISABLE KEYS */;
INSERT INTO `delivery_pn_tbl` (`c_id`, `c_delivery_id`, `c_ref_id`, `c_semi_produced_num`, `c_produced_num`) VALUES
	(1, 17, 1, 0, 907),
	(2, 17, 2, 0, 907),
	(7, 12, 1, 0, 1),
	(8, 12, 2, 0, 1),
	(9, 12, 3, 0, 1),
	(10, 12, 4, 0, 1),
	(11, 0, 3, 0, 111),
	(19, 22, 4, 0, 1),
	(20, 24, 2, 0, 1),
	(21, 24, 2, 0, 100000),
	(22, 0, 4, 0, 111),
	(23, 26, 4, 0, 1),
	(24, 28, 2, 2, 1),
	(25, 28, 1, 2, 1),
	(26, 37, 3, 0, 0),
	(27, 37, 3, 0, 0);
/*!40000 ALTER TABLE `delivery_pn_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `c_delivery_time` datetime DEFAULT NULL COMMENT '出库时间',
  `c_state` int(11) DEFAULT '0' COMMENT '出库单状态 出库中(0) 已出库(1) 已删除(2)',
  `c_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `c_update_user_id` int(11) DEFAULT NULL COMMENT '更新者用户ID',
  `c_create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`c_id`),
  KEY `c_id` (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- テーブル pdsys.delivery_tbl: ~30 rows (約) のデータをダンプしています
DELETE FROM `delivery_tbl`;
/*!40000 ALTER TABLE `delivery_tbl` DISABLE KEYS */;
INSERT INTO `delivery_tbl` (`c_id`, `c_user_id`, `c_delivery_time`, `c_state`, `c_update_time`, `c_update_user_id`, `c_create_time`) VALUES
	(6, 18, NULL, 0, '2018-04-05 11:26:58', 0, '2018-04-07 09:29:22'),
	(9, 999, NULL, 0, '2018-04-05 11:26:58', 0, '2018-04-07 09:29:22'),
	(10, 188, NULL, 0, '2018-04-05 11:26:58', 0, '2018-04-07 09:29:22'),
	(11, 4, NULL, 0, '2018-04-05 11:26:58', 0, '2018-04-07 09:29:22'),
	(12, 2, NULL, 0, '2018-04-05 11:26:58', 0, '2018-04-07 09:29:22'),
	(13, 333, NULL, 0, '2018-04-05 11:26:58', 0, '2018-04-07 09:29:22'),
	(14, 333, NULL, 0, '2018-04-05 11:26:58', 0, '2018-04-07 09:29:22'),
	(15, 333, NULL, 0, '2018-04-05 11:26:58', 0, '2018-04-07 09:29:22'),
	(16, 333, NULL, 0, '2018-04-05 11:26:58', 0, '2018-04-07 09:29:22'),
	(17, 16, NULL, 0, '2018-04-05 11:26:58', 0, '2018-04-07 09:29:22'),
	(19, 1, NULL, 0, '2018-04-07 08:49:30', NULL, '2018-04-07 09:29:22'),
	(20, 1, NULL, 0, '2018-04-07 09:00:35', NULL, '2018-04-07 09:29:22'),
	(21, 1, NULL, 2, '2018-04-07 13:09:18', NULL, '2018-04-07 09:32:55'),
	(22, 1, '2018-04-07 12:37:01', 1, '2018-04-07 12:37:01', NULL, '2018-04-07 11:03:37'),
	(23, 1, NULL, 2, '2018-04-07 13:11:08', NULL, '2018-04-07 13:11:04'),
	(24, 1, NULL, 0, '2018-04-07 13:13:15', NULL, '2018-04-07 13:13:15'),
	(25, 1, '2018-04-10 20:51:11', 1, '2018-04-10 21:51:11', NULL, '2018-04-10 21:51:05'),
	(26, 1, NULL, 0, '2018-04-13 11:41:00', NULL, '2018-04-13 11:41:00'),
	(27, 1, NULL, 0, '2018-04-13 15:35:57', NULL, '2018-04-13 15:35:57'),
	(28, 1, NULL, 0, '2018-04-13 15:44:19', NULL, '2018-04-13 15:44:19'),
	(29, 1, NULL, 0, '2018-04-15 11:28:12', NULL, '2018-04-15 11:28:12'),
	(30, 1, NULL, 0, '2018-04-15 11:28:12', NULL, '2018-04-15 11:28:12'),
	(31, 1, NULL, 0, '2018-04-15 11:28:12', NULL, '2018-04-15 11:28:12'),
	(32, 1, NULL, 0, '2018-04-15 11:28:12', NULL, '2018-04-15 11:28:12'),
	(33, 1, NULL, 0, '2018-04-15 11:28:12', NULL, '2018-04-15 11:28:12'),
	(34, 1, NULL, 0, '2018-04-15 11:28:12', NULL, '2018-04-15 11:28:12'),
	(35, 1, NULL, 0, '2018-04-15 11:28:12', NULL, '2018-04-15 11:28:12'),
	(36, 1, NULL, 0, '2018-04-15 11:28:12', NULL, '2018-04-15 11:28:12'),
	(37, 1, NULL, 0, '2018-04-15 11:28:12', NULL, '2018-04-15 11:28:12'),
	(38, 1, NULL, 0, '2018-04-15 11:28:12', NULL, '2018-04-15 11:28:12');
/*!40000 ALTER TABLE `delivery_tbl` ENABLE KEYS */;

--  テーブル pdsys.device_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `device_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(50) DEFAULT NULL COMMENT '编号',
  `c_machine_id` int(11) DEFAULT NULL COMMENT '机器设备ID',
  `c_place_id` int(11) DEFAULT NULL COMMENT '使用地点',
  `c_user_id` int(11) DEFAULT NULL COMMENT '负责人',
  `c_maitenaced_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上次保养时间',
  `c_state` int(11) NOT NULL DEFAULT '0' COMMENT '状态 运行中(0) 维护中(1) 故障(2) 废除(3)',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='设备表';

-- テーブル pdsys.device_tbl: ~7 rows (約) のデータをダンプしています
DELETE FROM `device_tbl`;
/*!40000 ALTER TABLE `device_tbl` DISABLE KEYS */;
INSERT INTO `device_tbl` (`c_id`, `c_no`, `c_machine_id`, `c_place_id`, `c_user_id`, `c_maitenaced_date`, `c_state`) VALUES
	(1, '#001', 1, 1, 1, '2018-04-08 09:40:22', 1),
	(2, '#002', 1, 2, 2, '2018-04-10 09:40:22', 1),
	(3, '#003', 2, 1, 1, '2018-04-05 09:40:22', 2),
	(4, '#004', 2, 1, 1, '2018-03-31 09:40:22', 3),
	(5, '5673', 2, 2, 2, NULL, 0),
	(6, '999', 1, 1, 1, '2018-04-08 10:45:17', 0),
	(7, '666', 2, 2, 1, '2018-04-15 11:35:29', 0);
/*!40000 ALTER TABLE `device_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单表BOM';

-- テーブル pdsys.entry_bom_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `entry_bom_tbl`;
/*!40000 ALTER TABLE `entry_bom_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单表备件';

-- テーブル pdsys.entry_machine_part_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `entry_machine_part_tbl`;
/*!40000 ALTER TABLE `entry_machine_part_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry_machine_part_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='入库单表PN';

-- テーブル pdsys.entry_pn_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `entry_pn_tbl`;
/*!40000 ALTER TABLE `entry_pn_tbl` DISABLE KEYS */;
INSERT INTO `entry_pn_tbl` (`c_id`, `c_entry_id`, `c_ref_id`, `c_semi_produced_num`, `c_produced_num`) VALUES
	(16, 10, 3, 0, 0),
	(17, 10, 2, 1, 0),
	(18, 12, 4, 1, 22);
/*!40000 ALTER TABLE `entry_pn_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `c_entry_time` datetime DEFAULT NULL COMMENT '入库时间',
  `c_state` int(11) NOT NULL DEFAULT '0' COMMENT '计划(0) 已入库(1) 删除(2)',
  `c_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `c_update_user_id` int(11) DEFAULT NULL COMMENT '更新者用户ID',
  PRIMARY KEY (`c_id`),
  KEY `c_id` (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- テーブル pdsys.entry_tbl: ~12 rows (約) のデータをダンプしています
DELETE FROM `entry_tbl`;
/*!40000 ALTER TABLE `entry_tbl` DISABLE KEYS */;
INSERT INTO `entry_tbl` (`c_id`, `c_user_id`, `c_entry_time`, `c_state`, `c_create_time`, `c_update_time`, `c_update_user_id`) VALUES
	(1, 1, NULL, 2, '2018-04-07 14:23:12', '2018-04-07 14:25:10', NULL),
	(2, 1, '2018-04-07 14:43:13', 0, '2018-04-07 14:26:55', '2018-04-07 15:21:00', NULL),
	(3, 1, '2018-04-07 15:21:15', 1, '2018-04-07 14:44:12', '2018-04-07 15:21:15', NULL),
	(4, 1, NULL, 0, '2018-04-09 10:54:35', '2018-04-09 10:54:35', NULL),
	(5, 1, '2018-04-09 09:55:26', 1, '2018-04-09 10:55:12', '2018-04-09 10:55:25', NULL),
	(6, 1, NULL, 0, '2018-04-13 15:10:00', '2018-04-13 15:10:00', NULL),
	(7, 1, '2018-04-13 14:35:36', 1, '2018-04-13 15:10:59', '2018-04-13 15:35:35', NULL),
	(8, 1, NULL, 0, '2018-04-13 17:12:18', '2018-04-13 17:12:18', NULL),
	(9, 1, NULL, 0, '2018-04-14 09:37:28', '2018-04-14 09:37:28', NULL),
	(10, 1, '2018-04-14 11:20:40', 1, '2018-04-14 09:53:37', '2018-04-14 12:20:39', NULL),
	(11, 1, NULL, 0, '2018-04-15 11:24:30', '2018-04-15 11:24:30', NULL),
	(12, 1, '2018-04-15 10:25:36', 1, '2018-04-15 11:25:17', '2018-04-15 11:25:35', NULL);
/*!40000 ALTER TABLE `entry_tbl` ENABLE KEYS */;

--  テーブル pdsys.machine_part_relation_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `machine_part_relation_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_machine_id` int(11) NOT NULL COMMENT '设备ID',
  `c_machine_part_id` int(11) NOT NULL COMMENT '零件ID',
  `c_maitenace_part_num` int(11) NOT NULL DEFAULT '0' COMMENT '保养所需备件数量',
  PRIMARY KEY (`c_id`),
  KEY `c_machine_id` (`c_machine_id`),
  KEY `c_machine_part_id` (`c_machine_part_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='设备-零件定义表';

-- テーブル pdsys.machine_part_relation_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `machine_part_relation_tbl`;
/*!40000 ALTER TABLE `machine_part_relation_tbl` DISABLE KEYS */;
INSERT INTO `machine_part_relation_tbl` (`c_id`, `c_machine_id`, `c_machine_part_id`, `c_maitenace_part_num`) VALUES
	(1, 1, 1, 33),
	(2, 1, 2, 2),
	(3, 2, 1, 44);
/*!40000 ALTER TABLE `machine_part_relation_tbl` ENABLE KEYS */;

--  テーブル pdsys.machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT '零件品番',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  `c_supplier_id` int(11) DEFAULT NULL COMMENT '供应商ID',
  `c_unit_id` int(11) DEFAULT NULL COMMENT '单位ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='设备零件定义表';

-- テーブル pdsys.machine_part_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `machine_part_tbl`;
/*!40000 ALTER TABLE `machine_part_tbl` DISABLE KEYS */;
INSERT INTO `machine_part_tbl` (`c_id`, `c_pn`, `c_name`, `c_supplier_id`, `c_unit_id`) VALUES
	(1, 'A2', '轴承', 3, 4),
	(2, 'A3', '螺丝', 4, 5);
/*!40000 ALTER TABLE `machine_part_tbl` ENABLE KEYS */;

--  テーブル pdsys.machine_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `machine_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_pn` varchar(50) DEFAULT NULL,
  `c_name` varchar(50) DEFAULT NULL,
  `c_unit_id` int(11) DEFAULT NULL,
  `c_maitenace_period` float DEFAULT '0',
  `c_supplier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='机器定义';

-- テーブル pdsys.machine_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `machine_tbl`;
/*!40000 ALTER TABLE `machine_tbl` DISABLE KEYS */;
INSERT INTO `machine_tbl` (`c_id`, `c_pn`, `c_name`, `c_unit_id`, `c_maitenace_period`, `c_supplier_id`) VALUES
	(1, 'AK001', '压铸机床', 1, 2, 3),
	(2, 'AK003', '压铸机床333', 2, 56, 1),
	(3, '22211115', '2225', 5, 15333, 1);
/*!40000 ALTER TABLE `machine_tbl` ENABLE KEYS */;

--  テーブル pdsys.order_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `order_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_order_id` int(11) DEFAULT NULL COMMENT '订单号',
  `c_pn_id` int(11) DEFAULT NULL COMMENT '品番ID',
  `c_pn_cls_id` int(11) DEFAULT NULL COMMENT '子类名ID',
  `c_num` float DEFAULT NULL COMMENT '数量',
  `c_reject_ratio` float DEFAULT '0' COMMENT '不良率 0-1',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='订单条目表';

-- テーブル pdsys.order_pn_tbl: ~8 rows (約) のデータをダンプしています
DELETE FROM `order_pn_tbl`;
/*!40000 ALTER TABLE `order_pn_tbl` DISABLE KEYS */;
INSERT INTO `order_pn_tbl` (`c_id`, `c_order_id`, `c_pn_id`, `c_pn_cls_id`, `c_num`, `c_reject_ratio`) VALUES
	(1, 2, 1, 1, 500, 0),
	(2, 2, 1, 2, 10000, 0),
	(3, 2, 2, 3, 20000, 0),
	(4, 2, 2, 4, 30000, 0),
	(6, 11, 2, 4, 1, 0),
	(7, 7, 1, 2, 11, 0),
	(8, 12, 1, 2, 100, 0),
	(9, 12, 1, 1, 666, 0);
/*!40000 ALTER TABLE `order_pn_tbl` ENABLE KEYS */;

--  テーブル pdsys.order_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `order_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(50) NOT NULL COMMENT '订单编号',
  `c_order_date` date DEFAULT NULL COMMENT '下单时间',
  `c_ship_dead_date` date DEFAULT NULL COMMENT '交货期限',
  `c_ship_date` date DEFAULT NULL COMMENT '交货时间',
  `c_state` int(11) DEFAULT NULL COMMENT '状态 0：计划中 1：生产中 2：已完成 3：已删除',
  `c_comment` varchar(100) DEFAULT NULL COMMENT '备注',
  `c_master_id` int(11) DEFAULT NULL,
  `c_customer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_no` (`c_no`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- テーブル pdsys.order_tbl: ~12 rows (約) のデータをダンプしています
DELETE FROM `order_tbl`;
/*!40000 ALTER TABLE `order_tbl` DISABLE KEYS */;
INSERT INTO `order_tbl` (`c_id`, `c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`, `c_comment`, `c_master_id`, `c_customer_id`) VALUES
	(1, 'JK-900-200', '2018-03-28', '2018-03-30', '2018-03-28', 3, NULL, NULL, NULL),
	(2, 'JK-900-201', '2018-03-28', '2018-03-30', '2018-03-28', 1, NULL, NULL, NULL),
	(3, 'JK-900-202', '2018-03-28', '2018-03-30', '2018-03-28', 0, NULL, NULL, NULL),
	(4, 'JK-900-203', '2018-03-28', '2018-03-30', '2018-03-28', 1, NULL, NULL, NULL),
	(5, 'JK-900-204', '2018-03-28', '2018-03-30', '2018-03-28', 2, NULL, NULL, NULL),
	(6, 'JK-900-205', '2018-03-28', '2018-03-30', '2018-03-28', 2, NULL, NULL, NULL),
	(7, 'JK-900-206', '2018-03-28', '2018-03-30', '2018-03-28', 3, NULL, NULL, NULL),
	(8, '22', NULL, NULL, NULL, 3, NULL, NULL, NULL),
	(9, '66', NULL, NULL, NULL, 3, NULL, NULL, NULL),
	(10, '77', '2018-04-10', '2018-04-12', NULL, NULL, NULL, NULL, NULL),
	(11, '88', '2018-04-10', '2018-04-10', NULL, 3, '88', 1, 1),
	(12, '11', '2018-04-15', '2018-04-15', NULL, NULL, '', 1, 1);
/*!40000 ALTER TABLE `order_tbl` ENABLE KEYS */;

--  テーブル pdsys.place_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `place_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='地点';

-- テーブル pdsys.place_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `place_tbl`;
/*!40000 ALTER TABLE `place_tbl` DISABLE KEYS */;
INSERT INTO `place_tbl` (`c_id`, `c_name`) VALUES
	(1, '2车间'),
	(2, '3车间');
/*!40000 ALTER TABLE `place_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_bom_relation_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_bom_relation_tbl` (
  `c_pn_id` int(11) NOT NULL,
  `c_bom_id` int(11) NOT NULL,
  `c_use_num` float NOT NULL,
  `c_cls_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- テーブル pdsys.pn_bom_relation_tbl: ~9 rows (約) のデータをダンプしています
DELETE FROM `pn_bom_relation_tbl`;
/*!40000 ALTER TABLE `pn_bom_relation_tbl` DISABLE KEYS */;
INSERT INTO `pn_bom_relation_tbl` (`c_pn_id`, `c_bom_id`, `c_use_num`, `c_cls_id`) VALUES
	(1, 1, 20, 2),
	(1, 3, 50, 2),
	(2, 5, 40, 4),
	(1, 2, 20, 1),
	(1, 4, 30, 2),
	(2, 6, 60, 3),
	(3, 1, 3, NULL),
	(3, 5, 2, NULL),
	(3, 4, 4, NULL);
/*!40000 ALTER TABLE `pn_bom_relation_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_cls_relation_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_cls_relation_tbl` (
  `c_pn_id` int(11) NOT NULL,
  `c_cls_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品目和子类对应表';

-- テーブル pdsys.pn_cls_relation_tbl: ~6 rows (約) のデータをダンプしています
DELETE FROM `pn_cls_relation_tbl`;
/*!40000 ALTER TABLE `pn_cls_relation_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_relation_tbl` (`c_pn_id`, `c_cls_id`) VALUES
	(1, 1),
	(1, 2),
	(2, 3),
	(3, 4),
	(3, 6),
	(3, 5);
/*!40000 ALTER TABLE `pn_cls_relation_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_cls_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_cls_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='品番子分类';

-- テーブル pdsys.pn_cls_tbl: ~7 rows (約) のデータをダンプしています
DELETE FROM `pn_cls_tbl`;
/*!40000 ALTER TABLE `pn_cls_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_tbl` (`c_id`, `c_name`) VALUES
	(1, '红色'),
	(2, '蓝色'),
	(3, '大号'),
	(4, '小号'),
	(5, 'aaa'),
	(6, '222'),
	(7, '555');
/*!40000 ALTER TABLE `pn_cls_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT '品番',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  `c_unit_id` int(11) NOT NULL COMMENT '单位ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='品番定义表';

-- テーブル pdsys.pn_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `pn_tbl`;
/*!40000 ALTER TABLE `pn_tbl` DISABLE KEYS */;
INSERT INTO `pn_tbl` (`c_id`, `c_pn`, `c_name`, `c_unit_id`) VALUES
	(1, 'pn1', '纸杯', 1),
	(2, 'pn2', '蛋糕托盘', 2),
	(3, '111', '11221', 1),
	(4, '11', '111', 2);
/*!40000 ALTER TABLE `pn_tbl` ENABLE KEYS */;

--  テーブル pdsys.supplier_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `supplier_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '供应商名',
  `c_address` varchar(100) DEFAULT NULL COMMENT '地址',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='供应商表';

-- テーブル pdsys.supplier_tbl: ~13 rows (約) のデータをダンプしています
DELETE FROM `supplier_tbl`;
/*!40000 ALTER TABLE `supplier_tbl` DISABLE KEYS */;
INSERT INTO `supplier_tbl` (`c_id`, `c_name`, `c_address`, `c_phone`) VALUES
	(1, '王子造纸', '南大街100号', '18888888888'),
	(2, '诚信塑料制品厂', '开发区某路100号', '2888888888'),
	(3, '大生轴承', '开发区某路130号', '3888888888'),
	(4, '小生螺丝', '开发区某路130号', '3988888888'),
	(5, '333', '33333', '3333'),
	(6, '222', '22222', '222'),
	(7, '2233333', '', ''),
	(8, '22', '44', '333'),
	(9, '45', '777', '666'),
	(10, '11', '333', '22'),
	(11, '2ac', '', 'sd'),
	(12, '4', '1', '3'),
	(13, '42', '2', '3');
/*!40000 ALTER TABLE `supplier_tbl` ENABLE KEYS */;

--  テーブル pdsys.unit_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `unit_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '单位',
  `c_sub_unit_id` int(11) NOT NULL DEFAULT '-1' COMMENT '子单位ID',
  `c_ratio` float NOT NULL DEFAULT '0' COMMENT '和子单位的换算，如1箱=100个',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='单位定义表';

-- テーブル pdsys.unit_tbl: ~20 rows (約) のデータをダンプしています
DELETE FROM `unit_tbl`;
/*!40000 ALTER TABLE `unit_tbl` DISABLE KEYS */;
INSERT INTO `unit_tbl` (`c_id`, `c_name`, `c_sub_unit_id`, `c_ratio`) VALUES
	(1, '吨', -1, 0),
	(2, '卷', -1, 0),
	(3, '捆', -1, 0),
	(4, '个', -1, 0),
	(5, '包', -1, 0),
	(6, 'rrrr', -1, 0),
	(7, 'rrrr', -1, 0),
	(8, 'rrrr', -1, 0),
	(9, '5', -1, 0),
	(10, '6', -1, 0),
	(11, '7', -1, 0),
	(12, '222', -1, 0),
	(13, '22', -1, 0),
	(14, '33', -1, 0),
	(15, '55', -1, 0),
	(16, '44', -1, 0),
	(17, '441', -1, 0),
	(18, '321', -1, 0),
	(19, '123', -1, 0),
	(20, '654', -1, 0);
/*!40000 ALTER TABLE `unit_tbl` ENABLE KEYS */;

--  テーブル pdsys.user_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(50) NOT NULL COMMENT '工番',
  `c_name` varchar(50) NOT NULL COMMENT '名字',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `c_address` varchar(50) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户定义表';

-- テーブル pdsys.user_tbl: ~6 rows (約) のデータをダンプしています
DELETE FROM `user_tbl`;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` (`c_id`, `c_no`, `c_name`, `c_phone`, `c_address`) VALUES
	(1, 'x01', '李四1', '1123', '????'),
	(2, 'x02', '张三1', '1156', '????'),
	(3, 'x03', 'x003', '11', '22'),
	(4, 'ss', '????张三', '', ''),
	(5, 'a', 'aaa啊啊啊', '', ''),
	(6, '1', '2223', '4', '5');
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;

--  テーブル pdsys.warehouse_bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `warehouse_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_bom_id` int(11) NOT NULL DEFAULT '0' COMMENT 'BOMID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='BOM仓库表';

-- テーブル pdsys.warehouse_bom_tbl: ~6 rows (約) のデータをダンプしています
DELETE FROM `warehouse_bom_tbl`;
/*!40000 ALTER TABLE `warehouse_bom_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_bom_tbl` (`c_id`, `c_bom_id`, `c_num`) VALUES
	(1, 1, 3),
	(2, 3, 6586),
	(3, 2, 666666),
	(4, 4, 98),
	(5, 5, 1000),
	(6, 6, 200);
/*!40000 ALTER TABLE `warehouse_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.warehouse_machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `warehouse_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_machine_part_id` int(11) NOT NULL DEFAULT '0' COMMENT '机器备件ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='设备备件仓库表';

-- テーブル pdsys.warehouse_machine_part_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `warehouse_machine_part_tbl`;
/*!40000 ALTER TABLE `warehouse_machine_part_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_machine_part_tbl` (`c_id`, `c_machine_part_id`, `c_num`) VALUES
	(1, 1, 333),
	(5, 2, 555);
/*!40000 ALTER TABLE `warehouse_machine_part_tbl` ENABLE KEYS */;

--  テーブル pdsys.warehouse_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `warehouse_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_order_pn_id` int(11) DEFAULT NULL COMMENT '订单条目ID',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='订单条目仓库表 		\r\n';

-- テーブル pdsys.warehouse_pn_tbl: ~6 rows (約) のデータをダンプしています
DELETE FROM `warehouse_pn_tbl`;
/*!40000 ALTER TABLE `warehouse_pn_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_pn_tbl` (`c_id`, `c_order_pn_id`, `c_semi_produced_num`, `c_produced_num`) VALUES
	(1, 4, 0, 12),
	(2, 4, 46, 81),
	(3, 3, 0, 14),
	(4, 3, 1, 13),
	(5, 2, 0, 10),
	(6, 2, 10, 9);
/*!40000 ALTER TABLE `warehouse_pn_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
