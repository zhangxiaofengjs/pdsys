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
  `c_id` int(11) NOT NULL COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT 'BOM品番',
  `c_name` varchar(50) NOT NULL COMMENT 'BOM名',
  `c_unit_id` int(11) NOT NULL COMMENT '单位ID',
  `c_supplier_id` int(11) NOT NULL COMMENT '供应商ID',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '原材(0) 包材(1)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BOM定义表';

-- テーブル pdsys.bom_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `bom_tbl`;
/*!40000 ALTER TABLE `bom_tbl` DISABLE KEYS */;
INSERT INTO `bom_tbl` (`c_id`, `c_pn`, `c_name`, `c_unit_id`, `c_supplier_id`, `c_type`) VALUES
	(1, 'X01', '杯身纸', 1, 1, 0),
	(2, 'X012', '塑料包装袋', 2, 2, 1),
	(3, 'X031', '杯底纸', 3, 1, 0),
	(4, 'X0S1', '包装箱', 4, 1, 1);
/*!40000 ALTER TABLE `bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.order_item_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `order_item_tbl` (
  `c_id` int(11) DEFAULT NULL COMMENT 'ID',
  `c_order_id` int(11) DEFAULT NULL COMMENT '订单号',
  `c_pn_id` int(11) DEFAULT NULL COMMENT '品番ID',
  `c_num` float DEFAULT NULL COMMENT '数量',
  `c_reject_ratio` float DEFAULT NULL COMMENT '不良率 0-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单条目表';

-- テーブル pdsys.order_item_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `order_item_tbl`;
/*!40000 ALTER TABLE `order_item_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item_tbl` ENABLE KEYS */;

--  テーブル pdsys.order_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `order_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(50) NOT NULL COMMENT '订单编号',
  `c_order_date` datetime DEFAULT NULL COMMENT '下单时间',
  `c_ship_dead_date` datetime DEFAULT NULL COMMENT '交货期限',
  `c_ship_date` datetime DEFAULT NULL COMMENT '交货时间',
  `c_state` int(11) DEFAULT NULL COMMENT '状态 0：计划中 1：生产中 2：已完成 3：已删除',
  `c_comment` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_no` (`c_no`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- テーブル pdsys.order_tbl: ~7 rows (約) のデータをダンプしています
DELETE FROM `order_tbl`;
/*!40000 ALTER TABLE `order_tbl` DISABLE KEYS */;
INSERT INTO `order_tbl` (`c_id`, `c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`, `c_comment`) VALUES
	(1, 'JK-900-200', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', 0, NULL),
	(2, 'JK-900-201', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', 1, NULL),
	(3, 'JK-900-202', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', 0, NULL),
	(4, 'JK-900-203', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', 1, NULL),
	(5, 'JK-900-204', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', 2, NULL),
	(6, 'JK-900-205', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', 2, NULL),
	(7, 'JK-900-206', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', 3, NULL);
/*!40000 ALTER TABLE `order_tbl` ENABLE KEYS */;

--  テーブル pdsys.supplier_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `supplier_tbl` (
  `c_id` int(11) NOT NULL COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '供应商名',
  `c_address` varchar(100) DEFAULT NULL COMMENT '地址',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '联系方式'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商表';

-- テーブル pdsys.supplier_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `supplier_tbl`;
/*!40000 ALTER TABLE `supplier_tbl` DISABLE KEYS */;
INSERT INTO `supplier_tbl` (`c_id`, `c_name`, `c_address`, `c_phone`) VALUES
	(1, '王子造纸', '南大街100号', '18888888888'),
	(2, '诚信塑料制品厂', '开发区某路100号', '2888888888');
/*!40000 ALTER TABLE `supplier_tbl` ENABLE KEYS */;

--  テーブル pdsys.unit_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `unit_tbl` (
  `c_id` int(11) NOT NULL COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '单位',
  `c_sub_unit_id` int(11) NOT NULL DEFAULT '-1' COMMENT '子单位ID',
  `c_ratio` float NOT NULL DEFAULT '0' COMMENT '和子单位的换算，如1箱=100个'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单位定义表';

-- テーブル pdsys.unit_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `unit_tbl`;
/*!40000 ALTER TABLE `unit_tbl` DISABLE KEYS */;
INSERT INTO `unit_tbl` (`c_id`, `c_name`, `c_sub_unit_id`, `c_ratio`) VALUES
	(1, '吨', -1, 0),
	(2, '卷', -1, 0),
	(3, '捆', -1, 0),
	(4, '个', -1, 0);
/*!40000 ALTER TABLE `unit_tbl` ENABLE KEYS */;

--  テーブル pdsys.warehouse_bom_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `warehouse_bom_tbl` (
  `c_id` int(11) DEFAULT '0' COMMENT 'ID',
  `c_bom_id` int(11) DEFAULT '0' COMMENT 'BOMID',
  `c_num` float DEFAULT '0' COMMENT '数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BOM仓库表';

-- テーブル pdsys.warehouse_bom_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `warehouse_bom_tbl`;
/*!40000 ALTER TABLE `warehouse_bom_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_bom_tbl` (`c_id`, `c_bom_id`, `c_num`) VALUES
	(1, 1, 999),
	(2, 3, 6586),
	(3, 2, 666666),
	(4, 4, 98);
/*!40000 ALTER TABLE `warehouse_bom_tbl` ENABLE KEYS */;

--  テーブル pdsys.warehouse_order_item_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `warehouse_order_item_tbl` (
  `c_id` int(11) DEFAULT NULL COMMENT 'ID',
  `c_order_item_id` int(11) DEFAULT NULL COMMENT '订单条目ID',
  `c_type` int(11) DEFAULT NULL COMMENT '半成品(0) 成品(1)',
  `c_num` float DEFAULT NULL COMMENT '数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单条目仓库表 		\r\n';

-- テーブル pdsys.warehouse_order_item_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `warehouse_order_item_tbl`;
/*!40000 ALTER TABLE `warehouse_order_item_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse_order_item_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
