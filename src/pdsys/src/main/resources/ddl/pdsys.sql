-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.21 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 pdsys 的数据库结构
CREATE DATABASE IF NOT EXISTS `pdsys` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pdsys`;

-- 导出  表 pdsys.bom_tbl 结构
CREATE TABLE IF NOT EXISTS `bom_tbl` (
  `c_id` int(11) NOT NULL COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT 'BOM品番',
  `c_name` varchar(50) NOT NULL COMMENT 'BOM名',
  `c_unit_id` int(11) NOT NULL COMMENT '单位ID',
  `c_supplier_id` int(11) NOT NULL COMMENT '供应商ID',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '原材(0) 包材(1)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BOM定义表';

-- 正在导出表  pdsys.bom_tbl 的数据：~4 rows (大约)
DELETE FROM `bom_tbl`;
/*!40000 ALTER TABLE `bom_tbl` DISABLE KEYS */;
INSERT INTO `bom_tbl` (`c_id`, `c_pn`, `c_name`, `c_unit_id`, `c_supplier_id`, `c_type`) VALUES
	(1, 'X01', '杯身纸', 1, 1, 0),
	(2, 'X012', '塑料包装袋', 2, 2, 1),
	(3, 'X031', '杯底纸', 3, 1, 0),
	(4, 'X0S1', '包装箱', 4, 1, 1);
/*!40000 ALTER TABLE `bom_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.customer_tbl 结构
CREATE TABLE IF NOT EXISTS `customer_tbl` (
  `c_id` int(11) DEFAULT NULL COMMENT 'ID',
  `c_name` varchar(50) DEFAULT NULL COMMENT '顾客名',
  `c_address` varchar(50) DEFAULT NULL COMMENT '地址',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '联系方式'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='顾客表 ';

-- 正在导出表  pdsys.customer_tbl 的数据：~2 rows (大约)
DELETE FROM `customer_tbl`;
/*!40000 ALTER TABLE `customer_tbl` DISABLE KEYS */;
INSERT INTO `customer_tbl` (`c_id`, `c_name`, `c_address`, `c_phone`) VALUES
	(1, '王子造纸', '开发区002号', '888-888-588'),
	(2, '王子造飞机', '前门大街001号', '888-888-888');
/*!40000 ALTER TABLE `customer_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.delivery_bom_tbl 结构
CREATE TABLE IF NOT EXISTS `delivery_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- 正在导出表  pdsys.delivery_bom_tbl 的数据：~16 rows (大约)
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

-- 导出  表 pdsys.delivery_machine_part_tbl 结构
CREATE TABLE IF NOT EXISTS `delivery_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='出库单表备件';

-- 正在导出表  pdsys.delivery_machine_part_tbl 的数据：~2 rows (大约)
DELETE FROM `delivery_machine_part_tbl`;
/*!40000 ALTER TABLE `delivery_machine_part_tbl` DISABLE KEYS */;
INSERT INTO `delivery_machine_part_tbl` (`c_id`, `c_delivery_id`, `c_ref_id`, `c_num`) VALUES
	(11, 18, 1, 1333),
	(12, 18, 5, 1333);
/*!40000 ALTER TABLE `delivery_machine_part_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.delivery_pn_tbl 结构
CREATE TABLE IF NOT EXISTS `delivery_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='出库单表PN';

-- 正在导出表  pdsys.delivery_pn_tbl 的数据：~8 rows (大约)
DELETE FROM `delivery_pn_tbl`;
/*!40000 ALTER TABLE `delivery_pn_tbl` DISABLE KEYS */;
INSERT INTO `delivery_pn_tbl` (`c_id`, `c_delivery_id`, `c_ref_id`, `c_num`) VALUES
	(1, 17, 1, 907),
	(2, 17, 2, 907),
	(5, 18, 1, 1),
	(6, 18, 2, 1),
	(7, 12, 1, 1),
	(8, 12, 2, 1),
	(9, 12, 3, 1),
	(10, 12, 4, 1);
/*!40000 ALTER TABLE `delivery_pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.delivery_tbl 结构
CREATE TABLE IF NOT EXISTS `delivery_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_delivery_time` datetime DEFAULT NULL COMMENT '出库时间',
  `c_state` int(11) NOT NULL DEFAULT '0' COMMENT '出库单状态 出库中(0) 已出库(1)',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `c_update_user_id` int(11) NOT NULL COMMENT '更新者用户ID',
  PRIMARY KEY (`c_id`),
  KEY `c_id` (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- 正在导出表  pdsys.delivery_tbl 的数据：~11 rows (大约)
DELETE FROM `delivery_tbl`;
/*!40000 ALTER TABLE `delivery_tbl` DISABLE KEYS */;
INSERT INTO `delivery_tbl` (`c_id`, `c_user_id`, `c_delivery_time`, `c_state`, `c_update_time`, `c_update_user_id`) VALUES
	(6, 18, NULL, 0, '2018-04-05 11:26:58', 0),
	(9, 999, NULL, 0, '2018-04-05 11:26:58', 0),
	(10, 188, NULL, 0, '2018-04-05 11:26:58', 0),
	(11, 4, NULL, 0, '2018-04-05 11:26:58', 0),
	(12, 2, NULL, 0, '2018-04-05 11:26:58', 0),
	(13, 333, NULL, 0, '2018-04-05 11:26:58', 0),
	(14, 333, NULL, 0, '2018-04-05 11:26:58', 0),
	(15, 333, NULL, 0, '2018-04-05 11:26:58', 0),
	(16, 333, NULL, 0, '2018-04-05 11:26:58', 0),
	(17, 16, NULL, 0, '2018-04-05 11:26:58', 0),
	(18, 1, NULL, 0, '2018-04-05 11:26:58', 0);
/*!40000 ALTER TABLE `delivery_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.entry_bom_tbl 结构
CREATE TABLE IF NOT EXISTS `entry_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单表BOM';

-- 正在导出表  pdsys.entry_bom_tbl 的数据：~0 rows (大约)
DELETE FROM `entry_bom_tbl`;
/*!40000 ALTER TABLE `entry_bom_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry_bom_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.entry_machine_part_tbl 结构
CREATE TABLE IF NOT EXISTS `entry_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单表备件';

-- 正在导出表  pdsys.entry_machine_part_tbl 的数据：~0 rows (大约)
DELETE FROM `entry_machine_part_tbl`;
/*!40000 ALTER TABLE `entry_machine_part_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry_machine_part_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.entry_pn_tbl 结构
CREATE TABLE IF NOT EXISTS `entry_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单表PN';

-- 正在导出表  pdsys.entry_pn_tbl 的数据：~0 rows (大约)
DELETE FROM `entry_pn_tbl`;
/*!40000 ALTER TABLE `entry_pn_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry_pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.entry_tbl 结构
CREATE TABLE IF NOT EXISTS `entry_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_entry_time` datetime DEFAULT NULL COMMENT '入库时间',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `c_update_user_id` int(11) NOT NULL COMMENT '更新者用户ID',
  PRIMARY KEY (`c_id`),
  KEY `c_id` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- 正在导出表  pdsys.entry_tbl 的数据：~0 rows (大约)
DELETE FROM `entry_tbl`;
/*!40000 ALTER TABLE `entry_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.machine_part_tbl 结构
CREATE TABLE IF NOT EXISTS `machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT '零件品番',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  `c_supplier_id` int(11) DEFAULT NULL COMMENT '供应商ID',
  `c_unit_id` int(11) DEFAULT NULL COMMENT '单位ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='设备零件定义表';

-- 正在导出表  pdsys.machine_part_tbl 的数据：~2 rows (大约)
DELETE FROM `machine_part_tbl`;
/*!40000 ALTER TABLE `machine_part_tbl` DISABLE KEYS */;
INSERT INTO `machine_part_tbl` (`c_id`, `c_pn`, `c_name`, `c_supplier_id`, `c_unit_id`) VALUES
	(1, 'A2', '轴承', 3, 4),
	(2, 'A3', '螺丝', 4, 5);
/*!40000 ALTER TABLE `machine_part_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.order_pn_tbl 结构
CREATE TABLE IF NOT EXISTS `order_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_order_id` int(11) DEFAULT NULL COMMENT '订单号',
  `c_pn_id` int(11) DEFAULT NULL COMMENT '品番ID',
  `c_pn_cls_id` int(11) DEFAULT NULL COMMENT '子类名ID',
  `c_num` float DEFAULT NULL COMMENT '数量',
  `c_reject_ratio` float DEFAULT '0' COMMENT '不良率 0-1',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='订单条目表';

-- 正在导出表  pdsys.order_pn_tbl 的数据：~4 rows (大约)
DELETE FROM `order_pn_tbl`;
/*!40000 ALTER TABLE `order_pn_tbl` DISABLE KEYS */;
INSERT INTO `order_pn_tbl` (`c_id`, `c_order_id`, `c_pn_id`, `c_pn_cls_id`, `c_num`, `c_reject_ratio`) VALUES
	(1, 1, 1, 1, 2000.8, 0.2),
	(2, 2, 1, 2, 233001, 0),
	(3, 2, 2, 3, 2033300, 0),
	(4, 2, 2, 4, 2000.83, 0);
/*!40000 ALTER TABLE `order_pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.order_tbl 结构
CREATE TABLE IF NOT EXISTS `order_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(50) NOT NULL COMMENT '订单编号',
  `c_order_date` date DEFAULT NULL COMMENT '下单时间',
  `c_ship_dead_date` date DEFAULT NULL COMMENT '交货期限',
  `c_ship_date` date DEFAULT NULL COMMENT '交货时间',
  `c_state` int(11) DEFAULT NULL COMMENT '状态 0：计划中 1：生产中 2：已完成 3：已删除',
  `c_comment` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_no` (`c_no`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- 正在导出表  pdsys.order_tbl 的数据：~7 rows (大约)
DELETE FROM `order_tbl`;
/*!40000 ALTER TABLE `order_tbl` DISABLE KEYS */;
INSERT INTO `order_tbl` (`c_id`, `c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`, `c_comment`) VALUES
	(1, 'JK-900-200', '2018-03-28', '2018-03-30', '2018-03-28', 0, NULL),
	(2, 'JK-900-201', '2018-03-28', '2018-03-30', '2018-03-28', 1, NULL),
	(3, 'JK-900-202', '2018-03-28', '2018-03-30', '2018-03-28', 0, NULL),
	(4, 'JK-900-203', '2018-03-28', '2018-03-30', '2018-03-28', 1, NULL),
	(5, 'JK-900-204', '2018-03-28', '2018-03-30', '2018-03-28', 2, NULL),
	(6, 'JK-900-205', '2018-03-28', '2018-03-30', '2018-03-28', 2, NULL),
	(7, 'JK-900-206', '2018-03-28', '2018-03-30', '2018-03-28', 3, NULL);
/*!40000 ALTER TABLE `order_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.pn_bom_relation_tbl 结构
CREATE TABLE IF NOT EXISTS `pn_bom_relation_tbl` (
  `c_pn_id` int(11) NOT NULL,
  `c_bom_id` int(11) NOT NULL,
  `c_use_num` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  pdsys.pn_bom_relation_tbl 的数据：~0 rows (大约)
DELETE FROM `pn_bom_relation_tbl`;
/*!40000 ALTER TABLE `pn_bom_relation_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `pn_bom_relation_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.pn_cls_relation_tbl 结构
CREATE TABLE IF NOT EXISTS `pn_cls_relation_tbl` (
  `c_pn_id` int(11) NOT NULL,
  `c_cls_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品目和子类对应表';

-- 正在导出表  pdsys.pn_cls_relation_tbl 的数据：~6 rows (大约)
DELETE FROM `pn_cls_relation_tbl`;
/*!40000 ALTER TABLE `pn_cls_relation_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_relation_tbl` (`c_pn_id`, `c_cls_id`) VALUES
	(1, 1),
	(1, 2),
	(2, 3),
	(2, 4),
	(3, 4),
	(3, 3);
/*!40000 ALTER TABLE `pn_cls_relation_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.pn_cls_tbl 结构
CREATE TABLE IF NOT EXISTS `pn_cls_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='品番子分类';

-- 正在导出表  pdsys.pn_cls_tbl 的数据：~4 rows (大约)
DELETE FROM `pn_cls_tbl`;
/*!40000 ALTER TABLE `pn_cls_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_tbl` (`c_id`, `c_name`) VALUES
	(1, '红色'),
	(2, '蓝色'),
	(3, '大号'),
	(4, '小号');
/*!40000 ALTER TABLE `pn_cls_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.pn_tbl 结构
CREATE TABLE IF NOT EXISTS `pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_pn` varchar(50) NOT NULL COMMENT '品番',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  `c_unit_id` int(11) NOT NULL COMMENT '单位ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='品番定义表';

-- 正在导出表  pdsys.pn_tbl 的数据：~4 rows (大约)
DELETE FROM `pn_tbl`;
/*!40000 ALTER TABLE `pn_tbl` DISABLE KEYS */;
INSERT INTO `pn_tbl` (`c_id`, `c_pn`, `c_name`, `c_unit_id`) VALUES
	(1, 'X01', '纸杯', 1),
	(2, 'X0555', '蛋糕托盘', 2),
	(3, 'X03', '面包盒', 3),
	(4, 'X031', '硅胶烤盘', 1);
/*!40000 ALTER TABLE `pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.supplier_tbl 结构
CREATE TABLE IF NOT EXISTS `supplier_tbl` (
  `c_id` int(11) NOT NULL COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '供应商名',
  `c_address` varchar(100) DEFAULT NULL COMMENT '地址',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '联系方式'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商表';

-- 正在导出表  pdsys.supplier_tbl 的数据：~4 rows (大约)
DELETE FROM `supplier_tbl`;
/*!40000 ALTER TABLE `supplier_tbl` DISABLE KEYS */;
INSERT INTO `supplier_tbl` (`c_id`, `c_name`, `c_address`, `c_phone`) VALUES
	(1, '王子造纸', '南大街100号', '18888888888'),
	(2, '诚信塑料制品厂', '开发区某路100号', '2888888888'),
	(3, '大生轴承', '开发区某路130号', '3888888888'),
	(4, '小生螺丝', '开发区某路130号', '3988888888');
/*!40000 ALTER TABLE `supplier_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.unit_tbl 结构
CREATE TABLE IF NOT EXISTS `unit_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) NOT NULL COMMENT '单位',
  `c_sub_unit_id` int(11) NOT NULL DEFAULT '-1' COMMENT '子单位ID',
  `c_ratio` float NOT NULL DEFAULT '0' COMMENT '和子单位的换算，如1箱=100个',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='单位定义表';

-- 正在导出表  pdsys.unit_tbl 的数据：~5 rows (大约)
DELETE FROM `unit_tbl`;
/*!40000 ALTER TABLE `unit_tbl` DISABLE KEYS */;
INSERT INTO `unit_tbl` (`c_id`, `c_name`, `c_sub_unit_id`, `c_ratio`) VALUES
	(1, '吨', -1, 0),
	(2, '卷', -1, 0),
	(3, '捆', -1, 0),
	(4, '个', -1, 0),
	(5, '包', -1, 0);
/*!40000 ALTER TABLE `unit_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.user_tbl 结构
CREATE TABLE IF NOT EXISTS `user_tbl` (
  `c_id` int(11) DEFAULT NULL COMMENT 'ID',
  `c_no` varchar(50) DEFAULT NULL COMMENT '工番',
  `c_name` varchar(50) DEFAULT NULL COMMENT '名字',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `c_address` varchar(50) DEFAULT NULL COMMENT '地址'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户定义表';

-- 正在导出表  pdsys.user_tbl 的数据：~2 rows (大约)
DELETE FROM `user_tbl`;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` (`c_id`, `c_no`, `c_name`, `c_phone`, `c_address`) VALUES
	(1, 'x01', '李四', '1123', '前门大街'),
	(2, 'x02', '张三', '1156', '日本皇宫');
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.warehouse_bom_tbl 结构
CREATE TABLE IF NOT EXISTS `warehouse_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_bom_id` int(11) NOT NULL DEFAULT '0' COMMENT 'BOMID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='BOM仓库表';

-- 正在导出表  pdsys.warehouse_bom_tbl 的数据：~4 rows (大约)
DELETE FROM `warehouse_bom_tbl`;
/*!40000 ALTER TABLE `warehouse_bom_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_bom_tbl` (`c_id`, `c_bom_id`, `c_num`) VALUES
	(1, 1, 999),
	(2, 3, 6586),
	(3, 2, 666666),
	(4, 4, 98);
/*!40000 ALTER TABLE `warehouse_bom_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.warehouse_machine_part_tbl 结构
CREATE TABLE IF NOT EXISTS `warehouse_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_machine_part_id` int(11) NOT NULL DEFAULT '0' COMMENT '机器备件ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='设备备件仓库表';

-- 正在导出表  pdsys.warehouse_machine_part_tbl 的数据：~2 rows (大约)
DELETE FROM `warehouse_machine_part_tbl`;
/*!40000 ALTER TABLE `warehouse_machine_part_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_machine_part_tbl` (`c_id`, `c_machine_part_id`, `c_num`) VALUES
	(1, 1, 333),
	(5, 2, 555);
/*!40000 ALTER TABLE `warehouse_machine_part_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.warehouse_pn_tbl 结构
CREATE TABLE IF NOT EXISTS `warehouse_pn_tbl` (
  `c_id` int(11) DEFAULT NULL COMMENT 'ID',
  `c_order_pn_id` int(11) DEFAULT NULL COMMENT '订单条目ID',
  `c_type` int(11) DEFAULT NULL COMMENT '半成品(0) 成品(1)',
  `c_num` float DEFAULT NULL COMMENT '数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单条目仓库表 		\r\n';

-- 正在导出表  pdsys.warehouse_pn_tbl 的数据：~4 rows (大约)
DELETE FROM `warehouse_pn_tbl`;
/*!40000 ALTER TABLE `warehouse_pn_tbl` DISABLE KEYS */;
INSERT INTO `warehouse_pn_tbl` (`c_id`, `c_order_pn_id`, `c_type`, `c_num`) VALUES
	(1, 1, 1, 1256),
	(2, 2, 0, 698),
	(3, 3, 0, 698),
	(4, 4, 0, 698);
/*!40000 ALTER TABLE `warehouse_pn_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
