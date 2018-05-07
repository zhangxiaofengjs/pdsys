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

--  テーブル pdsys.bom_supplier_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `bom_supplier_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_bom_id` int(11) NOT NULL DEFAULT '0' COMMENT '原包材ID',
  `c_supplier_id` int(11) NOT NULL DEFAULT '0' COMMENT '供应商ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='原包材和供应商的关系';

-- テーブル pdsys.bom_supplier_tbl: ~8 rows (約) のデータをダンプしています
DELETE FROM `bom_supplier_tbl`;
/*!40000 ALTER TABLE `bom_supplier_tbl` DISABLE KEYS */;
INSERT INTO `bom_supplier_tbl` (`c_id`, `c_bom_id`, `c_supplier_id`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 2, 1),
	(4, 3, 1),
	(5, 5, 3),
	(6, 4, 3),
	(7, 6, 1),
	(8, 7, 2),
	(9, 7, 1);
/*!40000 ALTER TABLE `bom_supplier_tbl` ENABLE KEYS */;

--  テーブル pdsys.bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT 'BOM品番',
  `c_name` varchar(50) NOT NULL COMMENT 'BOM名',
  `c_unit_id` int(11) NOT NULL COMMENT '单位ID',
  `c_price` float NOT NULL DEFAULT '0' COMMENT '单价',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '原材(0) 包材(1)',
  `c_comment` varchar(50) NOT NULL COMMENT '说明',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='BOM定义表';

-- テーブル pdsys.bom_tbl: ~8 rows (約) のデータをダンプしています
DELETE FROM `bom_tbl`;
/*!40000 ALTER TABLE `bom_tbl` DISABLE KEYS */;
INSERT INTO `bom_tbl` (`c_id`, `c_pn`, `c_name`, `c_unit_id`, `c_price`, `c_type`, `c_comment`) VALUES
	(1, 'WX001', '外箱', 1, 2, 1, '硅胶巴郎草，熊'),
	(2, 'WX002', '外箱', 1, 5.17, 1, '三个小兔子人'),
	(3, 'TZ001', '台纸', 2, 0.11, 0, '硅胶巴郎草，熊'),
	(4, 'OPPD001', '切角OPP袋2369#', 3, 0.085, 1, '硅胶巴郎草'),
	(5, 'GJ001', '硅胶', 4, 1000, 0, ''),
	(6, 'BGJ001', '不干胶', 6, 0.035, 0, ''),
	(7, 'BZ001', '杯身纸', 4, 0.115, 0, ''),
	(8, 'OPPD002', 'OPP中袋', 3, 0.023, 1, '85*18+7+7cm'),
	(9, 'WX003', '外箱', 1, 0.061, 1, '');
/*!40000 ALTER TABLE `bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.customer_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `customer_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '顾客名',
  `c_address` varchar(50) DEFAULT NULL COMMENT '地址',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='顾客表 ';

-- テーブル pdsys.customer_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `customer_tbl`;
/*!40000 ALTER TABLE `customer_tbl` DISABLE KEYS */;
INSERT INTO `customer_tbl` (`c_id`, `c_name`, `c_address`, `c_phone`) VALUES
	(1, 'SONY', '日本横滨', '188-8888-8888'),
	(2, 'SHARP', '日本东京', '199-9999-9999');
/*!40000 ALTER TABLE `customer_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID bom_tbl::c_id',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- テーブル pdsys.delivery_bom_tbl: ~6 rows (約) のデータをダンプしています
DELETE FROM `delivery_bom_tbl`;
/*!40000 ALTER TABLE `delivery_bom_tbl` DISABLE KEYS */;
INSERT INTO `delivery_bom_tbl` (`c_id`, `c_delivery_id`, `c_ref_id`, `c_num`) VALUES
	(1, 9, 6, 11),
	(2, 9, 7, 1),
	(3, 9, 5, 1),
	(4, 4, 1, 1245),
	(6, 8, 7, 2);
/*!40000 ALTER TABLE `delivery_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单表备件';

-- テーブル pdsys.delivery_machine_part_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `delivery_machine_part_tbl`;
/*!40000 ALTER TABLE `delivery_machine_part_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_machine_part_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID pn_tbl::c_id',
  `c_order_id` int(11) NOT NULL COMMENT '出库订单ID',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='出库单表PN';

-- テーブル pdsys.delivery_pn_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `delivery_pn_tbl`;
/*!40000 ALTER TABLE `delivery_pn_tbl` DISABLE KEYS */;
INSERT INTO `delivery_pn_tbl` (`c_id`, `c_delivery_id`, `c_ref_id`, `c_order_id`, `c_semi_produced_num`, `c_produced_num`) VALUES
	(4, 6, 1, 1, 0.2, 3),
	(5, 6, 4, 2, 0.3, 1),
	(6, 6, 4, 1, 0.6, 2),
	(7, 7, 4, 1, 0.6, 2);
/*!40000 ALTER TABLE `delivery_pn_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(50) NOT NULL DEFAULT '0' COMMENT '单号',
  `c_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `c_delivery_time` datetime DEFAULT NULL COMMENT '出库时间',
  `c_state` int(11) DEFAULT '0' COMMENT '出库单状态 出库中(0) 已出库(1) 已删除(2)',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '种类 PN(0) BOM(1) MACHINEPART(2)',
  `c_comment` varchar(50) NOT NULL,
  `c_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `c_update_user_id` int(11) DEFAULT NULL COMMENT '更新者用户ID',
  `c_create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`c_id`),
  KEY `c_id` (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- テーブル pdsys.delivery_tbl: ~11 rows (約) のデータをダンプしています
DELETE FROM `delivery_tbl`;
/*!40000 ALTER TABLE `delivery_tbl` DISABLE KEYS */;
INSERT INTO `delivery_tbl` (`c_id`, `c_no`, `c_user_id`, `c_delivery_time`, `c_state`, `c_type`, `c_comment`, `c_update_time`, `c_update_user_id`, `c_create_time`) VALUES
	(1, '0', 1, NULL, 1, 0, '123', '2018-05-04 14:15:09', NULL, '2018-05-02 18:48:02'),
	(2, '0', 1, NULL, 1, 0, '', '2018-05-04 14:15:10', NULL, '2018-05-02 18:52:04'),
	(3, '0', 1, NULL, 1, 0, '', '2018-05-04 14:15:11', NULL, '2018-05-02 18:53:13'),
	(4, '1-20180502-1', 1, '2018-05-01 00:00:00', 1, 1, '', '2018-05-04 17:35:03', NULL, '2018-05-02 18:54:19'),
	(5, '0-20180502-1', 1, NULL, 1, 0, '', '2018-05-04 14:15:35', NULL, '2018-05-02 18:58:43'),
	(6, '0-20180502-0', 1, '2018-05-03 10:21:01', 1, 0, '', '2018-05-05 11:04:53', NULL, '2018-05-02 19:00:18'),
	(7, '2-20180504-0', 1, '2018-05-05 10:07:05', 1, 0, '', '2018-05-05 11:07:06', NULL, '2018-05-04 12:34:43'),
	(8, '1-20180504-0', 1, '2018-05-04 16:40:02', 1, 1, '', '2018-05-04 17:40:02', NULL, '2018-05-04 12:36:12'),
	(9, '1-20180504-1', 1, '2018-05-04 23:59:59', 1, 1, '', '2018-05-04 17:40:46', NULL, '2018-05-04 12:42:30'),
	(10, '0-20180504-1', 1, NULL, 0, 0, '', '2018-05-04 14:15:37', NULL, '2018-05-04 14:15:37'),
	(11, '0-20180504-11', 2, NULL, 0, 0, '', '2018-05-04 14:15:51', NULL, '2018-05-04 14:15:51');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备表';

-- テーブル pdsys.device_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `device_tbl`;
/*!40000 ALTER TABLE `device_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID bom_tbl::c_id',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `ENTRYPN` (`c_entry_id`,`c_ref_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='入库单表BOM';

-- テーブル pdsys.entry_bom_tbl: ~9 rows (約) のデータをダンプしています
DELETE FROM `entry_bom_tbl`;
/*!40000 ALTER TABLE `entry_bom_tbl` DISABLE KEYS */;
INSERT INTO `entry_bom_tbl` (`c_id`, `c_entry_id`, `c_ref_id`, `c_num`) VALUES
	(1, 13, 6, 321),
	(2, 13, 7, 2),
	(3, 13, 3, 23),
	(4, 13, 8, 333),
	(5, 14, 8, 1),
	(6, 15, 6, 4),
	(8, 15, 3, 10),
	(9, 16, 5, 1),
	(10, 16, 7, 1);
/*!40000 ALTER TABLE `entry_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID machine_part_tbl::c_id',
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
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `ENTRYPN` (`c_entry_id`,`c_ref_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='入库单表PN';

-- テーブル pdsys.entry_pn_tbl: ~6 rows (約) のデータをダンプしています
DELETE FROM `entry_pn_tbl`;
/*!40000 ALTER TABLE `entry_pn_tbl` DISABLE KEYS */;
INSERT INTO `entry_pn_tbl` (`c_id`, `c_entry_id`, `c_ref_id`, `c_semi_produced_num`, `c_produced_num`) VALUES
	(1, 3, 1, 0, 2),
	(3, 3, 2, 0, 1),
	(4, 3, 4, 1, 0),
	(5, 3, 3, 2, 0),
	(6, 4, 1, 0, 1),
	(7, 4, 2, 1, 0);
/*!40000 ALTER TABLE `entry_pn_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(50) NOT NULL DEFAULT '0' COMMENT '入库单号',
  `c_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `c_entry_time` datetime DEFAULT NULL COMMENT '入库时间',
  `c_state` int(11) NOT NULL DEFAULT '0' COMMENT '计划(0) 已入库(1) 删除(2)',
  `c_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '种类 PN(0) BOM(1) MACHINEPART(2)',
  `c_comment` varchar(50) DEFAULT NULL,
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `c_update_user_id` int(11) DEFAULT NULL COMMENT '更新者用户ID',
  PRIMARY KEY (`c_id`),
  KEY `c_id` (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- テーブル pdsys.entry_tbl: ~19 rows (約) のデータをダンプしています
DELETE FROM `entry_tbl`;
/*!40000 ALTER TABLE `entry_tbl` DISABLE KEYS */;
INSERT INTO `entry_tbl` (`c_id`, `c_no`, `c_user_id`, `c_entry_time`, `c_state`, `c_create_time`, `c_type`, `c_comment`, `c_update_time`, `c_update_user_id`) VALUES
	(3, '0-20180502-0', 1, '2018-05-02 15:30:37', 1, '2018-05-02 14:45:11', 0, '123', '2018-05-02 16:30:37', NULL),
	(4, '0-20180502-1', 1, '2018-05-02 15:36:28', 1, '2018-05-02 16:11:52', 0, '', '2018-05-02 16:36:28', NULL),
	(7, '0-20180502-2', 1, NULL, 1, '2018-05-02 16:14:39', 0, '', '2018-05-04 13:53:53', NULL),
	(8, '1-20180502-1', 1, NULL, 0, '2018-05-02 16:50:50', 1, 'sss', '2018-05-02 16:50:50', NULL),
	(9, '1-20180502-2', 1, NULL, 0, '2018-05-02 16:52:03', 1, '', '2018-05-02 16:52:03', NULL),
	(10, '0-20180502-3', 1, NULL, 1, '2018-05-02 16:53:57', 0, '', '2018-05-04 13:53:55', NULL),
	(11, '0-20180502-4', 1, NULL, 1, '2018-05-02 16:55:52', 0, '', '2018-05-04 13:53:56', NULL),
	(12, '0-20180502-5', 1, NULL, 1, '2018-05-02 16:57:40', 0, '', '2018-05-04 13:53:58', NULL),
	(13, '1-20180502-3', 1, '2018-05-02 16:00:45', 1, '2018-05-02 16:57:45', 1, '', '2018-05-02 17:00:45', NULL),
	(14, '1-20180502-6', 1, '2018-05-02 16:07:14', 1, '2018-05-02 17:06:55', 1, '', '2018-05-02 17:07:13', NULL),
	(15, '1-20180504-0', 1, '2018-05-04 11:33:18', 1, '2018-05-04 11:55:19', 1, '111', '2018-05-04 12:33:18', NULL),
	(16, '1-20180504-1', 1, '2018-05-04 11:34:14', 1, '2018-05-04 12:33:52', 1, '', '2018-05-04 12:34:13', NULL),
	(17, '1-20180504-2', 1, NULL, 0, '2018-05-04 12:42:12', 1, '', '2018-05-04 12:42:12', NULL),
	(18, '1-20180504-00', 1, NULL, 0, '2018-05-04 12:46:35', 1, '', '2018-05-04 12:46:35', NULL),
	(19, '1-20180504-', 1, NULL, 0, '2018-05-04 12:47:27', 1, '', '2018-05-04 12:47:27', NULL),
	(20, '1-20180504-000', 1, NULL, 0, '2018-05-04 13:31:04', 1, '', '2018-05-04 13:31:04', NULL),
	(21, '1-20180504-333', 1, NULL, 0, '2018-05-04 13:36:07', 1, '', '2018-05-04 13:36:07', NULL),
	(22, '0-20180504-0', 1, NULL, 1, '2018-05-04 13:44:28', 0, '', '2018-05-04 13:53:59', NULL),
	(23, '0-20180504-1', 1, NULL, 1, '2018-05-04 13:44:46', 0, '', '2018-05-04 13:54:00', NULL),
	(24, '0-20180504-3', 1, NULL, 1, '2018-05-04 13:45:24', 0, '', '2018-05-04 13:54:01', NULL),
	(25, '0-20180504-4', 1, NULL, 1, '2018-05-04 13:46:17', 0, '', '2018-05-04 13:57:29', NULL),
	(26, '0-20180504-44', 1, NULL, 0, '2018-05-04 13:57:31', 0, '', '2018-05-04 13:57:31', NULL);
/*!40000 ALTER TABLE `entry_tbl` ENABLE KEYS */;

--  テーブル pdsys.image_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `image_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_url` varchar(50) DEFAULT NULL,
  `c_alt` varchar(50) DEFAULT NULL,
  `c_comment` varchar(50) DEFAULT NULL,
  `c_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`c_id`),
  KEY `c_id` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='当前品目子类的图片';

-- テーブル pdsys.image_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `image_tbl`;
/*!40000 ALTER TABLE `image_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `image_tbl` ENABLE KEYS */;

--  テーブル pdsys.machine_part_relation_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `machine_part_relation_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_machine_id` int(11) NOT NULL COMMENT '设备ID',
  `c_machine_part_id` int(11) NOT NULL COMMENT '零件ID',
  `c_maitenace_part_num` float NOT NULL DEFAULT '0' COMMENT '保养所需备件数量',
  PRIMARY KEY (`c_id`),
  KEY `c_machine_id` (`c_machine_id`),
  KEY `c_machine_part_id` (`c_machine_part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备-零件定义表';

-- テーブル pdsys.machine_part_relation_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `machine_part_relation_tbl`;
/*!40000 ALTER TABLE `machine_part_relation_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_part_relation_tbl` ENABLE KEYS */;

--  テーブル pdsys.machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT '零件品番',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  `c_supplier_id` int(11) DEFAULT NULL COMMENT '供应商ID',
  `c_unit_id` int(11) DEFAULT NULL COMMENT '单位ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备零件定义表';

-- テーブル pdsys.machine_part_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `machine_part_tbl`;
/*!40000 ALTER TABLE `machine_part_tbl` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机器定义';

-- テーブル pdsys.machine_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `machine_tbl`;
/*!40000 ALTER TABLE `machine_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_tbl` ENABLE KEYS */;

--  テーブル pdsys.order_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `order_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_order_id` int(11) NOT NULL COMMENT '订单号',
  `c_pn_id` int(11) NOT NULL COMMENT '品番ID',
  `c_num` float NOT NULL COMMENT '数量',
  `c_delivered_num` int(11) NOT NULL DEFAULT '0' COMMENT '已出货数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='订单条目表';

-- テーブル pdsys.order_pn_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `order_pn_tbl`;
/*!40000 ALTER TABLE `order_pn_tbl` DISABLE KEYS */;
INSERT INTO `order_pn_tbl` (`c_id`, `c_order_id`, `c_pn_id`, `c_num`, `c_delivered_num`) VALUES
	(1, 1, 1, 4, 0),
	(2, 1, 4, 3, 0),
	(3, 2, 4, 5, 0),
	(4, 2, 3, 2, 0);
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
  `c_master_id` int(11) DEFAULT NULL COMMENT '负责人ID',
  `c_customer_id` int(11) DEFAULT NULL COMMENT '顾客ID',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_no` (`c_no`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- テーブル pdsys.order_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `order_tbl`;
/*!40000 ALTER TABLE `order_tbl` DISABLE KEYS */;
INSERT INTO `order_tbl` (`c_id`, `c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`, `c_comment`, `c_master_id`, `c_customer_id`) VALUES
	(1, '20180503', '2018-05-03', '2018-05-03', NULL, 0, '123', 1, 1),
	(2, '20180504', '2018-05-03', '2018-05-03', NULL, 0, '', 1, 2),
	(3, '20180502', '2018-05-03', '2018-05-03', NULL, 1, '123', 1, 1);
/*!40000 ALTER TABLE `order_tbl` ENABLE KEYS */;

--  テーブル pdsys.place_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `place_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地点';

-- テーブル pdsys.place_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `place_tbl`;
/*!40000 ALTER TABLE `place_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `place_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_cls_bom_relation_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_cls_bom_relation_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_cls_id` int(11) NOT NULL COMMENT '制品分类ID',
  `c_bom_id` int(11) NOT NULL COMMENT '原包材ID',
  `c_use_num` float NOT NULL COMMENT '单位使用原包材量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='pn，bom的关联表';

-- テーブル pdsys.pn_cls_bom_relation_tbl: ~21 rows (約) のデータをダンプしています
DELETE FROM `pn_cls_bom_relation_tbl`;
/*!40000 ALTER TABLE `pn_cls_bom_relation_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_bom_relation_tbl` (`c_id`, `c_cls_id`, `c_bom_id`, `c_use_num`) VALUES
	(1, 1, 5, 0.0001),
	(2, 1, 3, 0.002),
	(3, 1, 8, 0.5),
	(4, 1, 1, 0.001),
	(5, 2, 3, 0.002),
	(6, 2, 1, 0.001),
	(7, 3, 3, 0.003),
	(8, 3, 4, 0.002),
	(9, 4, 5, 0.00003),
	(10, 4, 4, 0.02),
	(11, 5, 5, 0.02),
	(12, 5, 8, 0.05),
	(13, 6, 5, 0.06),
	(14, 6, 1, 0.002),
	(15, 7, 5, 0.001),
	(16, 7, 1, 0.002),
	(17, 10, 7, 0.0002),
	(18, 10, 9, 0.005),
	(19, 8, 7, 0.001),
	(20, 8, 9, 0.002),
	(21, 9, 7, 0.001),
	(22, 9, 9, 0.002);
/*!40000 ALTER TABLE `pn_cls_bom_relation_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_cls_relation_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_cls_relation_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn_id` int(11) NOT NULL COMMENT '品目ID',
  `c_cls_id` int(11) NOT NULL COMMENT '子类ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '子类每单位数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='品目和子类对应表';

-- テーブル pdsys.pn_cls_relation_tbl: ~8 rows (約) のデータをダンプしています
DELETE FROM `pn_cls_relation_tbl`;
/*!40000 ALTER TABLE `pn_cls_relation_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_relation_tbl` (`c_id`, `c_pn_id`, `c_cls_id`, `c_num`) VALUES
	(1, 1, 1, 20),
	(2, 1, 2, 30),
	(3, 1, 3, 50),
	(4, 2, 4, 60),
	(5, 2, 5, 10),
	(6, 2, 6, 60),
	(7, 2, 7, 70),
	(8, 3, 8, 6),
	(9, 3, 9, 4),
	(10, 4, 10, 10);
/*!40000 ALTER TABLE `pn_cls_relation_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_cls_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_cls_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='品番子分类';

-- テーブル pdsys.pn_cls_tbl: ~8 rows (約) のデータをダンプしています
DELETE FROM `pn_cls_tbl`;
/*!40000 ALTER TABLE `pn_cls_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_tbl` (`c_id`, `c_name`) VALUES
	(1, '草绿色'),
	(2, '粉色'),
	(3, '橙色'),
	(4, '绿色'),
	(5, '粉色'),
	(6, '蓝色'),
	(7, '草绿色'),
	(8, '红'),
	(9, '蓝'),
	(10, '30P纸杯');
/*!40000 ALTER TABLE `pn_cls_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT '品番',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  `c_unit_id` int(11) NOT NULL COMMENT '单位ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='品番定义表';

-- テーブル pdsys.pn_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `pn_tbl`;
/*!40000 ALTER TABLE `pn_tbl` DISABLE KEYS */;
INSERT INTO `pn_tbl` (`c_id`, `c_pn`, `c_name`, `c_unit_id`) VALUES
	(1, '4978446002369', '硅胶巴郎草', 7),
	(2, '4978446002376', '硅胶巴郎大熊', 8),
	(3, '4978446505099', '水点印刷杯', 3),
	(4, '4978446010340', '30P纸杯', 3);
/*!40000 ALTER TABLE `pn_tbl` ENABLE KEYS */;

--  テーブル pdsys.purchase_bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `purchase_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_purchase_id` int(11) NOT NULL COMMENT '采购单号',
  `c_bom_id` int(11) NOT NULL COMMENT '原包材ID',
  `c_num` float NOT NULL COMMENT '数量',
  `c_price` float NOT NULL COMMENT '单价',
  `c_supplier_id` int(11) NOT NULL COMMENT '供应商ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='采购明细单';

-- テーブル pdsys.purchase_bom_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `purchase_bom_tbl`;
/*!40000 ALTER TABLE `purchase_bom_tbl` DISABLE KEYS */;
INSERT INTO `purchase_bom_tbl` (`c_id`, `c_purchase_id`, `c_bom_id`, `c_num`, `c_price`, `c_supplier_id`) VALUES
	(1, 1, 1, 1, 1, 1),
	(2, 2, 1, 1, 1, 1),
	(3, 3, 1, 1, 1, 1);
/*!40000 ALTER TABLE `purchase_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.purchase_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `purchase_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) NOT NULL COMMENT '采购人',
  `c_state` int(11) NOT NULL COMMENT '状态 计划(0) 下单(1) 到货入库(2)',
  `c_create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成时间',
  `c_purchase_date` datetime DEFAULT NULL COMMENT '下单时间',
  `c_arrive_date` datetime DEFAULT NULL COMMENT '到货时间',
  `c_entry_id` int(11) DEFAULT NULL COMMENT '入库单ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='采购单';

-- テーブル pdsys.purchase_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `purchase_tbl`;
/*!40000 ALTER TABLE `purchase_tbl` DISABLE KEYS */;
INSERT INTO `purchase_tbl` (`c_id`, `c_user_id`, `c_state`, `c_create_date`, `c_purchase_date`, `c_arrive_date`, `c_entry_id`) VALUES
	(1, 1, 1, '2018-05-05 12:10:24', '2018-05-05 11:10:16', '2018-05-05 11:10:20', 1),
	(2, 1, 0, '2018-05-05 12:10:24', '2018-05-05 11:10:16', '2018-05-05 11:10:20', 1);
/*!40000 ALTER TABLE `purchase_tbl` ENABLE KEYS */;

--  テーブル pdsys.supplier_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `supplier_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '供应商名',
  `c_address` varchar(100) DEFAULT NULL COMMENT '地址',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='供应商表';

-- テーブル pdsys.supplier_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `supplier_tbl`;
/*!40000 ALTER TABLE `supplier_tbl` DISABLE KEYS */;
INSERT INTO `supplier_tbl` (`c_id`, `c_name`, `c_address`, `c_phone`) VALUES
	(1, '鑫泰', '广州', '188-8888-8888'),
	(2, '羽智', '南京', '188-8888-1234'),
	(3, '天三', '上海', '188-8888-5678');
/*!40000 ALTER TABLE `supplier_tbl` ENABLE KEYS */;

--  テーブル pdsys.unit_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `unit_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '单位 例：箱',
  `c_sub_name` varchar(50) NOT NULL DEFAULT '0' COMMENT '子单位 例：个',
  `c_ratio` float NOT NULL DEFAULT '0' COMMENT '和子单位的换算，如1箱=100个',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='单位定义表';

-- テーブル pdsys.unit_tbl: ~8 rows (約) のデータをダンプしています
DELETE FROM `unit_tbl`;
/*!40000 ALTER TABLE `unit_tbl` DISABLE KEYS */;
INSERT INTO `unit_tbl` (`c_id`, `c_name`, `c_sub_name`, `c_ratio`) VALUES
	(1, '只', '只', 1),
	(2, '箱', '枚', 10),
	(3, '箱', '只', 10),
	(4, '吨', '', 0),
	(5, '卷', '', 0),
	(6, '箱', '卷', 20),
	(7, '箱', '个', 100),
	(8, '箱', '个', 200);
/*!40000 ALTER TABLE `unit_tbl` ENABLE KEYS */;

--  テーブル pdsys.user_role_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_role_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `c_role` char(6) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- テーブル pdsys.user_role_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `user_role_tbl`;
/*!40000 ALTER TABLE `user_role_tbl` DISABLE KEYS */;
INSERT INTO `user_role_tbl` (`c_id`, `c_user_id`, `c_role`) VALUES
	(1, 1, 'admin'),
	(2, 1, 'device'),
	(3, 2, 'device');
/*!40000 ALTER TABLE `user_role_tbl` ENABLE KEYS */;

--  テーブル pdsys.user_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(50) NOT NULL COMMENT '工番',
  `c_name` varchar(50) NOT NULL COMMENT '名字',
  `c_password` varchar(100) DEFAULT NULL COMMENT '密码',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `c_address` varchar(50) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户定义表';

-- テーブル pdsys.user_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `user_tbl`;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` (`c_id`, `c_no`, `c_name`, `c_password`, `c_phone`, `c_address`) VALUES
	(1, 'admin', 'admin', '$2a$10$dnpMNxdLkJ2ckCMwj3l4UuEP42HmFf8d3oAouLapbsZ7X8Re/mAZi', NULL, NULL),
	(2, '001', '测试用户1', '$2a$10$VgG6UAoBkFICcCmHmMicOO3woAAj6oXL/1dGtQQ6PP1QzWBdotrvu', '188-8888-8888', '南通'),
	(3, '002', '测试用户2', '$2a$10$6BFYVLPbJ2ub2524xuuto.tiisTOTS45UPWQ/BRxUnzT/5x6GhxnC', '199-9999-9999', '上海');
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;

--  テーブル pdsys.warehouse_bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `warehouse_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_bom_id` int(11) NOT NULL DEFAULT '0' COMMENT 'BOMID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='BOM仓库表';

-- テーブル pdsys.warehouse_bom_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `warehouse_bom_tbl`;
/*!40000 ALTER TABLE `warehouse_bom_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_bom_tbl` (`c_id`, `c_bom_id`, `c_num`) VALUES
	(1, 6, 303),
	(2, 7, 0),
	(3, 8, 334),
	(4, 3, 33),
	(5, 5, 0);
/*!40000 ALTER TABLE `warehouse_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.warehouse_machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `warehouse_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_machine_part_id` int(11) NOT NULL DEFAULT '0' COMMENT '机器备件ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备备件仓库表';

-- テーブル pdsys.warehouse_machine_part_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `warehouse_machine_part_tbl`;
/*!40000 ALTER TABLE `warehouse_machine_part_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse_machine_part_tbl` ENABLE KEYS */;

--  テーブル pdsys.warehouse_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `warehouse_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn_id` int(11) DEFAULT NULL COMMENT '订单条目ID',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='订单条目仓库表 		\r\n';

-- テーブル pdsys.warehouse_pn_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `warehouse_pn_tbl`;
/*!40000 ALTER TABLE `warehouse_pn_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_pn_tbl` (`c_id`, `c_pn_id`, `c_semi_produced_num`, `c_produced_num`) VALUES
	(1, 1, 0, 0),
	(2, 2, 1, 1),
	(3, 4, 1, 3),
	(4, 3, 2, 3);
/*!40000 ALTER TABLE `warehouse_pn_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
