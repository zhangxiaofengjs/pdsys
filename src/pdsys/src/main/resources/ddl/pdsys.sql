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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='原包材和供应商的关系';

-- テーブル pdsys.bom_supplier_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `bom_supplier_tbl`;
/*!40000 ALTER TABLE `bom_supplier_tbl` DISABLE KEYS */;
INSERT INTO `bom_supplier_tbl` (`c_id`, `c_bom_id`, `c_supplier_id`) VALUES
	(1, 1, 3),
	(3, 1, 2),
	(4, 1, 1);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='BOM定义表';

-- テーブル pdsys.bom_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `bom_tbl`;
/*!40000 ALTER TABLE `bom_tbl` DISABLE KEYS */;
INSERT INTO `bom_tbl` (`c_id`, `c_pn`, `c_name`, `c_unit_id`, `c_price`, `c_type`, `c_comment`) VALUES
	(1, '11', '-11', 2, 0, 0, ''),
	(2, '2', '2', 1, 0, 1, '');
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
	(1, 'SONY', '日本横滨', '166-6666-6666'),
	(2, 'SHARP', '东京', '178-9878-6329');
/*!40000 ALTER TABLE `customer_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID bom_tbl::c_id',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- テーブル pdsys.delivery_bom_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `delivery_bom_tbl`;
/*!40000 ALTER TABLE `delivery_bom_tbl` DISABLE KEYS */;
INSERT INTO `delivery_bom_tbl` (`c_id`, `c_delivery_id`, `c_ref_id`, `c_num`) VALUES
	(1, 5, 1, 1),
	(4, 5, 6, 3);
/*!40000 ALTER TABLE `delivery_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='出库单表备件';

-- テーブル pdsys.delivery_machine_part_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `delivery_machine_part_tbl`;
/*!40000 ALTER TABLE `delivery_machine_part_tbl` DISABLE KEYS */;
INSERT INTO `delivery_machine_part_tbl` (`c_id`, `c_delivery_id`, `c_ref_id`, `c_num`) VALUES
	(2, 7, 1, 66),
	(5, 7, 4, 1);
/*!40000 ALTER TABLE `delivery_machine_part_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID order_pn_tbl::c_id',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  `c_defective_num` float NOT NULL DEFAULT '0' COMMENT '不良品',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='出库单表PN';

-- テーブル pdsys.delivery_pn_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `delivery_pn_tbl`;
/*!40000 ALTER TABLE `delivery_pn_tbl` DISABLE KEYS */;
INSERT INTO `delivery_pn_tbl` (`c_id`, `c_delivery_id`, `c_ref_id`, `c_semi_produced_num`, `c_produced_num`, `c_defective_num`) VALUES
	(1, 1, 2, 200, 200, 0),
	(2, 1, 3, 0, 200, 0),
	(3, 4, 1, 1, 2, 0),
	(4, 6, 1, 0, 0, 1);
/*!40000 ALTER TABLE `delivery_pn_tbl` ENABLE KEYS */;

--  テーブル pdsys.delivery_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- テーブル pdsys.delivery_tbl: ~7 rows (約) のデータをダンプしています
DELETE FROM `delivery_tbl`;
/*!40000 ALTER TABLE `delivery_tbl` DISABLE KEYS */;
INSERT INTO `delivery_tbl` (`c_id`, `c_user_id`, `c_delivery_time`, `c_state`, `c_type`, `c_comment`, `c_update_time`, `c_update_user_id`, `c_create_time`) VALUES
	(1, 3, NULL, 0, 0, '', '2018-04-19 11:50:21', NULL, '2018-04-19 11:50:21'),
	(2, 2, NULL, 0, 0, '123', '2018-04-23 14:29:37', NULL, '2018-04-23 14:29:37'),
	(3, 2, NULL, 0, 0, '1223', '2018-04-23 14:31:27', NULL, '2018-04-23 14:31:27'),
	(4, 1, '2018-04-23 14:04:54', 1, 0, '12333', '2018-04-23 15:04:54', NULL, '2018-04-23 14:58:21'),
	(5, 2, '2018-04-23 14:44:35', 1, 1, '666', '2018-04-23 15:44:34', NULL, '2018-04-23 15:13:33'),
	(6, 1, '2018-04-23 17:59:21', 1, 0, '', '2018-04-23 18:59:21', NULL, '2018-04-23 18:53:19'),
	(7, 1, '2018-04-26 11:46:37', 1, 2, '2222', '2018-04-26 12:46:37', NULL, '2018-04-26 11:56:05');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='设备表';

-- テーブル pdsys.device_tbl: ~5 rows (約) のデータをダンプしています
DELETE FROM `device_tbl`;
/*!40000 ALTER TABLE `device_tbl` DISABLE KEYS */;
INSERT INTO `device_tbl` (`c_id`, `c_no`, `c_machine_id`, `c_place_id`, `c_user_id`, `c_maitenaced_date`, `c_state`) VALUES
	(1, 'M01', 1, 1, 2, '2018-04-19 11:13:36', 0),
	(2, 'M02', 1, 1, 2, '2018-04-19 11:13:52', 0),
	(3, 'M03', 2, 2, 3, '2018-04-19 11:14:10', 1),
	(4, 'M04', 3, 2, 3, '2018-04-19 11:14:25', 2),
	(5, 'M05', 2, 1, 2, '2018-04-19 11:14:44', 0);
/*!40000 ALTER TABLE `device_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID bom_tbl::c_id',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='入库单表BOM';

-- テーブル pdsys.entry_bom_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `entry_bom_tbl`;
/*!40000 ALTER TABLE `entry_bom_tbl` DISABLE KEYS */;
INSERT INTO `entry_bom_tbl` (`c_id`, `c_entry_id`, `c_ref_id`, `c_num`) VALUES
	(1, 6, 2, 6),
	(2, 6, 5, 3),
	(4, 7, 1, 1245);
/*!40000 ALTER TABLE `entry_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID machine_part_tbl::c_id',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='入库单表备件';

-- テーブル pdsys.entry_machine_part_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `entry_machine_part_tbl`;
/*!40000 ALTER TABLE `entry_machine_part_tbl` DISABLE KEYS */;
INSERT INTO `entry_machine_part_tbl` (`c_id`, `c_entry_id`, `c_ref_id`, `c_num`) VALUES
	(2, 12, 3, 22),
	(3, 12, 4, 1),
	(4, 14, 1, 1000);
/*!40000 ALTER TABLE `entry_machine_part_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  `c_defective_num` float NOT NULL DEFAULT '0' COMMENT '不良品',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='入库单表PN';

-- テーブル pdsys.entry_pn_tbl: ~7 rows (約) のデータをダンプしています
DELETE FROM `entry_pn_tbl`;
/*!40000 ALTER TABLE `entry_pn_tbl` DISABLE KEYS */;
INSERT INTO `entry_pn_tbl` (`c_id`, `c_entry_id`, `c_ref_id`, `c_semi_produced_num`, `c_produced_num`, `c_defective_num`) VALUES
	(1, 1, 1, 200, 300, 0),
	(2, 1, 2, 0, 500, 0),
	(3, 1, 3, 600, 0, 0),
	(4, 4, 1, 1, 1, 0),
	(5, 8, 4, 1, 2, 0),
	(6, 9, 1, 1, 2, 3),
	(7, 11, 1, 0, 1, 0);
/*!40000 ALTER TABLE `entry_pn_tbl` ENABLE KEYS */;

--  テーブル pdsys.entry_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `entry_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- テーブル pdsys.entry_tbl: ~15 rows (約) のデータをダンプしています
DELETE FROM `entry_tbl`;
/*!40000 ALTER TABLE `entry_tbl` DISABLE KEYS */;
INSERT INTO `entry_tbl` (`c_id`, `c_user_id`, `c_entry_time`, `c_state`, `c_create_time`, `c_type`, `c_comment`, `c_update_time`, `c_update_user_id`) VALUES
	(1, 2, '2018-04-19 10:50:03', 1, '2018-04-19 11:15:36', 0, '0', '2018-04-19 11:50:03', NULL),
	(2, 1, NULL, 0, '2018-04-23 12:24:11', 0, '0', '2018-04-23 12:24:11', NULL),
	(3, 1, NULL, 0, '2018-04-23 12:24:51', 0, '0', '2018-04-23 12:24:51', NULL),
	(4, 1, '2018-04-23 11:28:55', 1, '2018-04-23 12:25:05', 0, '0', '2018-04-23 12:28:55', NULL),
	(5, 1, NULL, 0, '2018-04-23 12:28:17', 0, '23234', '2018-04-23 12:28:17', NULL),
	(6, 2, '2018-04-23 13:05:37', 1, '2018-04-23 12:33:32', 1, '234', '2018-04-23 14:05:36', NULL),
	(7, 1, NULL, 0, '2018-04-23 15:07:59', 1, '', '2018-04-23 15:07:59', NULL),
	(8, 1, '2018-04-23 17:07:15', 1, '2018-04-23 18:06:54', 0, '333', '2018-04-23 18:07:14', NULL),
	(9, 1, '2018-04-23 17:52:58', 1, '2018-04-23 18:41:55', 0, '', '2018-04-23 18:52:58', NULL),
	(10, 1, NULL, 0, '2018-04-25 14:33:11', 0, '', '2018-04-25 14:33:11', NULL),
	(11, 1, NULL, 0, '2018-04-25 14:34:31', 0, '', '2018-04-25 14:34:31', NULL),
	(12, 1, '2018-04-25 14:32:13', 1, '2018-04-25 14:49:10', 2, '88', '2018-04-25 15:32:13', NULL),
	(13, 1, NULL, 0, '2018-04-25 14:56:02', 2, '11', '2018-04-25 14:56:02', NULL),
	(14, 1, '2018-04-26 09:43:45', 1, '2018-04-26 10:43:31', 2, '11', '2018-04-26 10:43:45', NULL),
	(15, 1, NULL, 0, '2018-04-26 11:54:01', 2, '223344', '2018-04-26 11:54:01', NULL),
	(16, 1, NULL, 0, '2018-04-27 15:30:50', 2, '', '2018-04-27 15:30:50', NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='设备-零件定义表';

-- テーブル pdsys.machine_part_relation_tbl: ~5 rows (約) のデータをダンプしています
DELETE FROM `machine_part_relation_tbl`;
/*!40000 ALTER TABLE `machine_part_relation_tbl` DISABLE KEYS */;
INSERT INTO `machine_part_relation_tbl` (`c_id`, `c_machine_id`, `c_machine_part_id`, `c_maitenace_part_num`) VALUES
	(1, 1, 1, 1.2),
	(2, 1, 2, 1),
	(3, 2, 3, 8),
	(4, 3, 2, 1),
	(5, 1, 4, 1);
/*!40000 ALTER TABLE `machine_part_relation_tbl` ENABLE KEYS */;

--  テーブル pdsys.machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT '零件品番',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  `c_supplier_id` int(11) DEFAULT NULL COMMENT '供应商ID',
  `c_unit_id` int(11) DEFAULT NULL COMMENT '单位ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='设备零件定义表';

-- テーブル pdsys.machine_part_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `machine_part_tbl`;
/*!40000 ALTER TABLE `machine_part_tbl` DISABLE KEYS */;
INSERT INTO `machine_part_tbl` (`c_id`, `c_pn`, `c_name`, `c_supplier_id`, `c_unit_id`) VALUES
	(1, 'L01', '轴承', 4, 2),
	(2, 'L02', '润滑油', 4, 4),
	(3, 'L03', '螺丝', 4, 2),
	(4, 'L04', '发动机', 4, 2);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='机器定义';

-- テーブル pdsys.machine_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `machine_tbl`;
/*!40000 ALTER TABLE `machine_tbl` DISABLE KEYS */;
INSERT INTO `machine_tbl` (`c_id`, `c_pn`, `c_name`, `c_unit_id`, `c_maitenace_period`, `c_supplier_id`) VALUES
	(1, 'J01', '模具压铸机', 5, 15, 4),
	(2, 'J02', '裁纸机', 5, 30, 4),
	(3, 'J03', '自动包装机', 5, 60, 4);
/*!40000 ALTER TABLE `machine_tbl` ENABLE KEYS */;

--  テーブル pdsys.order_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `order_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_order_id` int(11) NOT NULL COMMENT '订单号',
  `c_pn_id` int(11) NOT NULL COMMENT '品番ID',
  `c_num` float NOT NULL COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='订单条目表';

-- テーブル pdsys.order_pn_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `order_pn_tbl`;
/*!40000 ALTER TABLE `order_pn_tbl` DISABLE KEYS */;
INSERT INTO `order_pn_tbl` (`c_id`, `c_order_id`, `c_pn_id`, `c_num`) VALUES
	(1, 1, 1, 10000),
	(2, 1, 2, 66),
	(3, 1, 2, 2000),
	(4, 2, 1, 2000);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- テーブル pdsys.order_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `order_tbl`;
/*!40000 ALTER TABLE `order_tbl` DISABLE KEYS */;
INSERT INTO `order_tbl` (`c_id`, `c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`, `c_comment`, `c_master_id`, `c_customer_id`) VALUES
	(1, 'D-20180501-001', '2018-04-19', '2018-05-19', NULL, NULL, '纳期严守', 2, 1),
	(2, 'D-20180501-002', '2018-04-19', '2018-07-19', NULL, NULL, '', 3, 2);
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
	(1, '1车间'),
	(2, '2车间');
/*!40000 ALTER TABLE `place_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_cls_bom_relation_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_cls_bom_relation_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_cls_id` int(11) NOT NULL COMMENT '制品分类ID',
  `c_bom_id` int(11) NOT NULL COMMENT '原包材ID',
  `c_use_num` float NOT NULL COMMENT '单位使用原包材量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='pn，bom的关联表';

-- テーブル pdsys.pn_cls_bom_relation_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `pn_cls_bom_relation_tbl`;
/*!40000 ALTER TABLE `pn_cls_bom_relation_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_bom_relation_tbl` (`c_id`, `c_cls_id`, `c_bom_id`, `c_use_num`) VALUES
	(1, 1, 1, 1),
	(2, 1, 2, 2),
	(3, 2, 2, 2),
	(4, 3, 2, 2);
/*!40000 ALTER TABLE `pn_cls_bom_relation_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_cls_relation_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_cls_relation_tbl` (
  `c_pn_id` int(11) NOT NULL COMMENT '品目ID',
  `c_cls_id` int(11) NOT NULL COMMENT '子类ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '子类每单位数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品目和子类对应表';

-- テーブル pdsys.pn_cls_relation_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `pn_cls_relation_tbl`;
/*!40000 ALTER TABLE `pn_cls_relation_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_relation_tbl` (`c_pn_id`, `c_cls_id`, `c_num`) VALUES
	(1, 1, 0),
	(1, 2, 0),
	(2, 3, 0),
	(2, 4, 0);
/*!40000 ALTER TABLE `pn_cls_relation_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_cls_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_cls_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='品番子分类';

-- テーブル pdsys.pn_cls_tbl: ~5 rows (約) のデータをダンプしています
DELETE FROM `pn_cls_tbl`;
/*!40000 ALTER TABLE `pn_cls_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_tbl` (`c_id`, `c_name`) VALUES
	(1, '红色'),
	(2, '蓝色'),
	(3, '不锈钢'),
	(4, '硅胶');
/*!40000 ALTER TABLE `pn_cls_tbl` ENABLE KEYS */;

--  テーブル pdsys.pn_machine_relation_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `pn_machine_relation_tbl` (
  `c_pn_id` int(11) NOT NULL COMMENT '品番ID',
  `c_machine_id` int(11) NOT NULL COMMENT '机器ID',
  `c_produce_num` float NOT NULL DEFAULT '0' COMMENT '机器每天制造的品番数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品番机器关联表';

-- テーブル pdsys.pn_machine_relation_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `pn_machine_relation_tbl`;
/*!40000 ALTER TABLE `pn_machine_relation_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `pn_machine_relation_tbl` ENABLE KEYS */;

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
	(1, 'X01', '纸杯', 1),
	(2, 'X02', '蛋糕模具', 2),
	(3, '1111', '111111', 3),
	(4, '222', '666', 1);
/*!40000 ALTER TABLE `pn_tbl` ENABLE KEYS */;

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
	(1, '1', '1', '1'),
	(2, '2', '', ''),
	(3, '22', '', '');
/*!40000 ALTER TABLE `supplier_tbl` ENABLE KEYS */;

--  テーブル pdsys.unit_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `unit_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '单位 例：箱',
  `c_sub_name` varchar(50) NOT NULL DEFAULT '0' COMMENT '子单位 例：个',
  `c_ratio` float NOT NULL DEFAULT '0' COMMENT '和子单位的换算，如1箱=100个',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='单位定义表';

-- テーブル pdsys.unit_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `unit_tbl`;
/*!40000 ALTER TABLE `unit_tbl` DISABLE KEYS */;
INSERT INTO `unit_tbl` (`c_id`, `c_name`, `c_sub_name`, `c_ratio`) VALUES
	(1, '箱', '个', 100),
	(2, '箱', '个', 50),
	(3, '盒子', '枚', 30);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='BOM仓库表';

-- テーブル pdsys.warehouse_bom_tbl: ~6 rows (約) のデータをダンプしています
DELETE FROM `warehouse_bom_tbl`;
/*!40000 ALTER TABLE `warehouse_bom_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_bom_tbl` (`c_id`, `c_bom_id`, `c_num`) VALUES
	(1, 1, 32),
	(2, 2, 68),
	(3, 3, 6),
	(4, 4, 77),
	(6, 6, 94),
	(7, 5, 3);
/*!40000 ALTER TABLE `warehouse_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.warehouse_machine_part_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `warehouse_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_machine_part_id` int(11) NOT NULL DEFAULT '0' COMMENT '机器备件ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='设备备件仓库表';

-- テーブル pdsys.warehouse_machine_part_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `warehouse_machine_part_tbl`;
/*!40000 ALTER TABLE `warehouse_machine_part_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_machine_part_tbl` (`c_id`, `c_machine_part_id`, `c_num`) VALUES
	(1, 1, 897),
	(2, 2, 5),
	(4, 4, 0);
/*!40000 ALTER TABLE `warehouse_machine_part_tbl` ENABLE KEYS */;

--  テーブル pdsys.warehouse_pn_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `warehouse_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_order_pn_id` int(11) DEFAULT NULL COMMENT '订单条目ID',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  `c_defective_num` float NOT NULL DEFAULT '0' COMMENT '不良品',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='订单条目仓库表 		\r\n';

-- テーブル pdsys.warehouse_pn_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `warehouse_pn_tbl`;
/*!40000 ALTER TABLE `warehouse_pn_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_pn_tbl` (`c_id`, `c_order_pn_id`, `c_semi_produced_num`, `c_produced_num`, `c_defective_num`) VALUES
	(1, 1, 305, 305, 2),
	(2, 2, 0, 500, 0),
	(3, 3, 600, 0, 0),
	(4, 4, 1, 2, 0);
/*!40000 ALTER TABLE `warehouse_pn_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
