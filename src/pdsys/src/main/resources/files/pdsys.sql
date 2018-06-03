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

-- 导出  表 pdsys.approval_info_tbl 结构
CREATE TABLE IF NOT EXISTS `approval_info_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_type` int(11) NOT NULL COMMENT '种类 采购单审批(0)',
  `c_node_id` int(11) NOT NULL COMMENT '批复节点',
  `c_approval_user_id` int(11) DEFAULT NULL COMMENT '批复者ID',
  `c_request_user_id` int(11) DEFAULT NULL COMMENT '提交者ID',
  `c_state` int(11) NOT NULL DEFAULT '0' COMMENT '批复状态 WORKING, WAIT, NG, OK;',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='批复结果表';

-- 正在导出表  pdsys.approval_info_tbl 的数据：~3 rows (大约)
DELETE FROM `approval_info_tbl`;
/*!40000 ALTER TABLE `approval_info_tbl` DISABLE KEYS */;
INSERT INTO `approval_info_tbl` (`c_id`, `c_type`, `c_node_id`, `c_approval_user_id`, `c_request_user_id`, `c_state`, `c_update_time`) VALUES
	(1, 0, 1, 2, 2, 3, '2018-06-03 15:26:32'),
	(2, 0, 1, NULL, NULL, 0, '2018-06-03 15:41:25'),
	(3, 0, 1, NULL, 3, 1, '2018-06-03 16:01:27');
/*!40000 ALTER TABLE `approval_info_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.approval_node_tbl 结构
CREATE TABLE IF NOT EXISTS `approval_node_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_next_id` int(11) DEFAULT '0' COMMENT '下一个节点',
  `c_name` varchar(50) DEFAULT '0' COMMENT '节点名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='承认节点表';

-- 正在导出表  pdsys.approval_node_tbl 的数据：~1 rows (大约)
DELETE FROM `approval_node_tbl`;
/*!40000 ALTER TABLE `approval_node_tbl` DISABLE KEYS */;
INSERT INTO `approval_node_tbl` (`c_id`, `c_next_id`, `c_name`) VALUES
	(2, 0, '采购单批复');
/*!40000 ALTER TABLE `approval_node_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.approval_node_user_relation_tbl 结构
CREATE TABLE IF NOT EXISTS `approval_node_user_relation_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_node_id` int(11) NOT NULL COMMENT '节点ID',
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='批复节点和用户关联表';

-- 正在导出表  pdsys.approval_node_user_relation_tbl 的数据：~5 rows (大约)
DELETE FROM `approval_node_user_relation_tbl`;
/*!40000 ALTER TABLE `approval_node_user_relation_tbl` DISABLE KEYS */;
INSERT INTO `approval_node_user_relation_tbl` (`c_id`, `c_node_id`, `c_user_id`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(4, 4, 3),
	(5, 2, 3),
	(6, 2, 2);
/*!40000 ALTER TABLE `approval_node_user_relation_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.bom_supplier_tbl 结构
CREATE TABLE IF NOT EXISTS `bom_supplier_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_bom_id` int(11) NOT NULL DEFAULT '0' COMMENT '原包材ID',
  `c_supplier_id` int(11) NOT NULL DEFAULT '0' COMMENT '供应商ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='原包材和供应商的关系';

-- 正在导出表  pdsys.bom_supplier_tbl 的数据：~1 rows (大约)
DELETE FROM `bom_supplier_tbl`;
/*!40000 ALTER TABLE `bom_supplier_tbl` DISABLE KEYS */;
INSERT INTO `bom_supplier_tbl` (`c_id`, `c_update_time`, `c_bom_id`, `c_supplier_id`) VALUES
	(1, '2018-06-03 14:52:49', 1, 1);
/*!40000 ALTER TABLE `bom_supplier_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.bom_tbl 结构
CREATE TABLE IF NOT EXISTS `bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_pn` varchar(50) NOT NULL COMMENT 'BOM品番',
  `c_name` varchar(50) NOT NULL COMMENT 'BOM名',
  `c_unit_id` int(11) NOT NULL COMMENT '单位ID',
  `c_price` float NOT NULL DEFAULT '0' COMMENT '单价',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '原材(0) 包材(1)',
  `c_comment` varchar(50) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='BOM定义表';

-- 正在导出表  pdsys.bom_tbl 的数据：~1 rows (大约)
DELETE FROM `bom_tbl`;
/*!40000 ALTER TABLE `bom_tbl` DISABLE KEYS */;
INSERT INTO `bom_tbl` (`c_id`, `c_update_time`, `c_pn`, `c_name`, `c_unit_id`, `c_price`, `c_type`, `c_comment`) VALUES
	(1, '2018-06-02 16:31:40', 'X', '1', 1, 1, 0, '');
/*!40000 ALTER TABLE `bom_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.customer_tbl 结构
CREATE TABLE IF NOT EXISTS `customer_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_name` varchar(50) NOT NULL COMMENT '顾客名',
  `c_address` varchar(50) DEFAULT NULL COMMENT '地址',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='顾客表 ';

-- 正在导出表  pdsys.customer_tbl 的数据：~0 rows (大约)
DELETE FROM `customer_tbl`;
/*!40000 ALTER TABLE `customer_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.delivery_bom_tbl 结构
CREATE TABLE IF NOT EXISTS `delivery_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID bom_tbl::c_id',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- 正在导出表  pdsys.delivery_bom_tbl 的数据：~1 rows (大约)
DELETE FROM `delivery_bom_tbl`;
/*!40000 ALTER TABLE `delivery_bom_tbl` DISABLE KEYS */;
INSERT INTO `delivery_bom_tbl` (`c_id`, `c_update_time`, `c_delivery_id`, `c_ref_id`, `c_num`) VALUES
	(1, '2018-05-30 16:10:48', 3, 7, 2);
/*!40000 ALTER TABLE `delivery_bom_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.delivery_machine_part_tbl 结构
CREATE TABLE IF NOT EXISTS `delivery_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单表备件';

-- 正在导出表  pdsys.delivery_machine_part_tbl 的数据：~0 rows (大约)
DELETE FROM `delivery_machine_part_tbl`;
/*!40000 ALTER TABLE `delivery_machine_part_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_machine_part_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.delivery_pn_tbl 结构
CREATE TABLE IF NOT EXISTS `delivery_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '出库品目ID pn_tbl::c_id',
  `c_order_id` int(11) NOT NULL COMMENT '出库订单ID',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量(暂时没用，准备废弃）',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单表PN';

-- 正在导出表  pdsys.delivery_pn_tbl 的数据：~0 rows (大约)
DELETE FROM `delivery_pn_tbl`;
/*!40000 ALTER TABLE `delivery_pn_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.delivery_semi_pn_tbl 结构
CREATE TABLE IF NOT EXISTS `delivery_semi_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_delivery_id` int(11) NOT NULL COMMENT '出库单ID',
  `c_pn_id` int(11) NOT NULL COMMENT '出库品目ID pn_tbl::c_id',
  `c_pn_cls_id` int(11) NOT NULL COMMENT '出库子类ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单表半成品PN';

-- 正在导出表  pdsys.delivery_semi_pn_tbl 的数据：~0 rows (大约)
DELETE FROM `delivery_semi_pn_tbl`;
/*!40000 ALTER TABLE `delivery_semi_pn_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_semi_pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.delivery_tbl 结构
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- 正在导出表  pdsys.delivery_tbl 的数据：~0 rows (大约)
DELETE FROM `delivery_tbl`;
/*!40000 ALTER TABLE `delivery_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.device_repair_tbl 结构
CREATE TABLE IF NOT EXISTS `device_repair_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_device_id` int(11) NOT NULL COMMENT '设备ID',
  `c_trouble_id` int(11) NOT NULL COMMENT '故障ID',
  `c_repair_date` datetime DEFAULT NULL COMMENT '维修日期',
  `c_comment` varchar(50) NOT NULL COMMENT '备注',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备维修记录';

-- 正在导出表  pdsys.device_repair_tbl 的数据：~0 rows (大约)
DELETE FROM `device_repair_tbl`;
/*!40000 ALTER TABLE `device_repair_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_repair_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.device_tbl 结构
CREATE TABLE IF NOT EXISTS `device_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_no` varchar(50) DEFAULT NULL COMMENT '编号',
  `c_machine_id` int(11) DEFAULT NULL COMMENT '机器设备ID',
  `c_place_id` int(11) DEFAULT NULL COMMENT '使用地点',
  `c_user_id` int(11) DEFAULT NULL COMMENT '负责人',
  `c_maitenaced_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上次保养时间',
  `c_state` int(11) NOT NULL DEFAULT '0' COMMENT '状态 运行中(0) 维护中(1) 故障(2) 废除(3)',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备表';

-- 正在导出表  pdsys.device_tbl 的数据：~0 rows (大约)
DELETE FROM `device_tbl`;
/*!40000 ALTER TABLE `device_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.entry_bom_tbl 结构
CREATE TABLE IF NOT EXISTS `entry_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID bom_tbl::c_id',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `ENTRYPN` (`c_entry_id`,`c_ref_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单表BOM';

-- 正在导出表  pdsys.entry_bom_tbl 的数据：~0 rows (大约)
DELETE FROM `entry_bom_tbl`;
/*!40000 ALTER TABLE `entry_bom_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry_bom_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.entry_machine_part_tbl 结构
CREATE TABLE IF NOT EXISTS `entry_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID machine_part_tbl::c_id',
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
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_ref_id` int(11) NOT NULL COMMENT '入库品目ID',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量(暂时没用，准备废弃）',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `ENTRYPN` (`c_entry_id`,`c_ref_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单表PN';

-- 正在导出表  pdsys.entry_pn_tbl 的数据：~0 rows (大约)
DELETE FROM `entry_pn_tbl`;
/*!40000 ALTER TABLE `entry_pn_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry_pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.entry_semi_pn_tbl 结构
CREATE TABLE IF NOT EXISTS `entry_semi_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_entry_id` int(11) NOT NULL COMMENT '入库单ID',
  `c_pn_id` int(11) NOT NULL COMMENT '入库品目ID',
  `c_pn_cls_id` int(11) NOT NULL COMMENT '入库品目子类ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `ENTRYPN` (`c_entry_id`,`c_pn_id`,`c_pn_cls_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单表半成品PN';

-- 正在导出表  pdsys.entry_semi_pn_tbl 的数据：~0 rows (大约)
DELETE FROM `entry_semi_pn_tbl`;
/*!40000 ALTER TABLE `entry_semi_pn_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry_semi_pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.entry_tbl 结构
CREATE TABLE IF NOT EXISTS `entry_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(50) NOT NULL DEFAULT '0' COMMENT '入库单号',
  `c_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `c_entry_time` datetime DEFAULT NULL COMMENT '入库时间',
  `c_state` int(11) NOT NULL DEFAULT '0' COMMENT '计划(0) 已入库(1)',
  `c_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '种类 PN(0) BOM(1) MACHINEPART(2) SEMIPN(3)',
  `c_comment` varchar(50) DEFAULT NULL,
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `c_update_user_id` int(11) DEFAULT NULL COMMENT '更新者用户ID',
  PRIMARY KEY (`c_id`),
  KEY `c_id` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- 正在导出表  pdsys.entry_tbl 的数据：~0 rows (大约)
DELETE FROM `entry_tbl`;
/*!40000 ALTER TABLE `entry_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.image_tbl 结构
CREATE TABLE IF NOT EXISTS `image_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_name` varchar(50) DEFAULT NULL,
  `c_url` varchar(50) DEFAULT NULL,
  `c_alt` varchar(50) DEFAULT NULL,
  `c_comment` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`c_id`),
  KEY `c_id` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='当前品目子类的图片';

-- 正在导出表  pdsys.image_tbl 的数据：~0 rows (大约)
DELETE FROM `image_tbl`;
/*!40000 ALTER TABLE `image_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `image_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.machine_part_relation_tbl 结构
CREATE TABLE IF NOT EXISTS `machine_part_relation_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_machine_id` int(11) NOT NULL COMMENT '设备ID',
  `c_machine_part_id` int(11) NOT NULL COMMENT '零件ID',
  `c_maitenace_part_num` float NOT NULL DEFAULT '0' COMMENT '保养所需备件数量',
  PRIMARY KEY (`c_id`),
  KEY `c_machine_id` (`c_machine_id`),
  KEY `c_machine_part_id` (`c_machine_part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备-零件定义表';

-- 正在导出表  pdsys.machine_part_relation_tbl 的数据：~0 rows (大约)
DELETE FROM `machine_part_relation_tbl`;
/*!40000 ALTER TABLE `machine_part_relation_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_part_relation_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.machine_part_tbl 结构
CREATE TABLE IF NOT EXISTS `machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_pn` varchar(50) NOT NULL COMMENT '零件品番',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  `c_supplier_id` int(11) DEFAULT NULL COMMENT '供应商ID',
  `c_unit_id` int(11) DEFAULT NULL COMMENT '单位ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备零件定义表';

-- 正在导出表  pdsys.machine_part_tbl 的数据：~0 rows (大约)
DELETE FROM `machine_part_tbl`;
/*!40000 ALTER TABLE `machine_part_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_part_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.machine_tbl 结构
CREATE TABLE IF NOT EXISTS `machine_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_pn` varchar(50) DEFAULT NULL,
  `c_name` varchar(50) DEFAULT NULL,
  `c_unit_id` int(11) DEFAULT NULL,
  `c_maitenace_period` float DEFAULT '0',
  `c_supplier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机器定义';

-- 正在导出表  pdsys.machine_tbl 的数据：~0 rows (大约)
DELETE FROM `machine_tbl`;
/*!40000 ALTER TABLE `machine_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.machine_trouble_tbl 结构
CREATE TABLE IF NOT EXISTS `machine_trouble_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_code` varchar(50) NOT NULL COMMENT '故障CODE',
  `c_comment` varchar(50) NOT NULL COMMENT '故障描述',
  PRIMARY KEY (`c_id`),
  KEY `c_comment` (`c_comment`,`c_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机器故障表';

-- 正在导出表  pdsys.machine_trouble_tbl 的数据：~0 rows (大约)
DELETE FROM `machine_trouble_tbl`;
/*!40000 ALTER TABLE `machine_trouble_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine_trouble_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.notice_tbl 结构
CREATE TABLE IF NOT EXISTS `notice_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_type` int(11) NOT NULL COMMENT '种类 设备保养(0) 采购单承认(1)',
  `c_content` varchar(200) NOT NULL COMMENT '内容',
  `c_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `c_state` int(11) NOT NULL DEFAULT '0' COMMENT '未读(0) 已读(1)',
  `c_ref_id` int(11) DEFAULT NULL COMMENT '链接到其他必要的ID',
  `c_sender_id` int(11) NOT NULL DEFAULT '0' COMMENT '发送者 0为系统 其余参照user_tbl::c_id',
  `c_receiver_id` int(11) DEFAULT '0' COMMENT '接受者 0为全员',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  pdsys.notice_tbl 的数据：~0 rows (大约)
DELETE FROM `notice_tbl`;
/*!40000 ALTER TABLE `notice_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.order_pn_tbl 结构
CREATE TABLE IF NOT EXISTS `order_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_order_id` int(11) NOT NULL COMMENT '订单号',
  `c_pn_id` int(11) NOT NULL COMMENT '品番ID',
  `c_num` float NOT NULL COMMENT '数量',
  `c_delivered_num` int(11) NOT NULL DEFAULT '0' COMMENT '已出货数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='订单条目表';

-- 正在导出表  pdsys.order_pn_tbl 的数据：~1 rows (大约)
DELETE FROM `order_pn_tbl`;
/*!40000 ALTER TABLE `order_pn_tbl` DISABLE KEYS */;
INSERT INTO `order_pn_tbl` (`c_id`, `c_update_time`, `c_order_id`, `c_pn_id`, `c_num`, `c_delivered_num`) VALUES
	(1, '2018-06-02 16:40:56', 1, 1, 222, 0);
/*!40000 ALTER TABLE `order_pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.order_tbl 结构
CREATE TABLE IF NOT EXISTS `order_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- 正在导出表  pdsys.order_tbl 的数据：~1 rows (大约)
DELETE FROM `order_tbl`;
/*!40000 ALTER TABLE `order_tbl` DISABLE KEYS */;
INSERT INTO `order_tbl` (`c_id`, `c_update_time`, `c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`, `c_comment`, `c_master_id`, `c_customer_id`) VALUES
	(1, '2018-06-02 16:40:47', '15', '2018-06-02', '2018-06-02', NULL, 0, '', 2, NULL);
/*!40000 ALTER TABLE `order_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.place_tbl 结构
CREATE TABLE IF NOT EXISTS `place_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地点';

-- 正在导出表  pdsys.place_tbl 的数据：~0 rows (大约)
DELETE FROM `place_tbl`;
/*!40000 ALTER TABLE `place_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `place_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.pn_bom_relation_tbl 结构
CREATE TABLE IF NOT EXISTS `pn_bom_relation_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_pn_id` int(11) NOT NULL COMMENT '制品ID',
  `c_bom_id` int(11) NOT NULL COMMENT '原包材ID',
  `c_use_num` float NOT NULL COMMENT '单位使用原包材量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='pn，bom的关联表';

-- 正在导出表  pdsys.pn_bom_relation_tbl 的数据：~1 rows (大约)
DELETE FROM `pn_bom_relation_tbl`;
/*!40000 ALTER TABLE `pn_bom_relation_tbl` DISABLE KEYS */;
INSERT INTO `pn_bom_relation_tbl` (`c_id`, `c_update_time`, `c_pn_id`, `c_bom_id`, `c_use_num`) VALUES
	(1, '2018-06-02 16:40:31', 1, 1, 1);
/*!40000 ALTER TABLE `pn_bom_relation_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.pn_cls_bom_relation_tbl 结构
CREATE TABLE IF NOT EXISTS `pn_cls_bom_relation_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_cls_id` int(11) NOT NULL COMMENT '制品分类ID',
  `c_bom_id` int(11) NOT NULL COMMENT '原包材ID',
  `c_use_num` float NOT NULL COMMENT 'PN子类个数所需使用原包材量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='pn，bom的关联表';

-- 正在导出表  pdsys.pn_cls_bom_relation_tbl 的数据：~1 rows (大约)
DELETE FROM `pn_cls_bom_relation_tbl`;
/*!40000 ALTER TABLE `pn_cls_bom_relation_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_bom_relation_tbl` (`c_id`, `c_update_time`, `c_cls_id`, `c_bom_id`, `c_use_num`) VALUES
	(1, '2018-06-02 16:40:36', 2, 1, 2);
/*!40000 ALTER TABLE `pn_cls_bom_relation_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.pn_cls_relation_tbl 结构
CREATE TABLE IF NOT EXISTS `pn_cls_relation_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_pn_id` int(11) NOT NULL COMMENT '品目ID',
  `c_cls_id` int(11) NOT NULL COMMENT '子类ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '子类每单位数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='品目和子类对应表';

-- 正在导出表  pdsys.pn_cls_relation_tbl 的数据：~1 rows (大约)
DELETE FROM `pn_cls_relation_tbl`;
/*!40000 ALTER TABLE `pn_cls_relation_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_relation_tbl` (`c_id`, `c_update_time`, `c_pn_id`, `c_cls_id`, `c_num`) VALUES
	(1, '2018-06-02 16:40:22', 1, 2, 1);
/*!40000 ALTER TABLE `pn_cls_relation_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.pn_cls_tbl 结构
CREATE TABLE IF NOT EXISTS `pn_cls_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='品番子分类';

-- 正在导出表  pdsys.pn_cls_tbl 的数据：~1 rows (大约)
DELETE FROM `pn_cls_tbl`;
/*!40000 ALTER TABLE `pn_cls_tbl` DISABLE KEYS */;
INSERT INTO `pn_cls_tbl` (`c_id`, `c_update_time`, `c_name`) VALUES
	(2, '2018-06-02 16:40:22', '111');
/*!40000 ALTER TABLE `pn_cls_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.pn_tbl 结构
CREATE TABLE IF NOT EXISTS `pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_pn` varchar(50) NOT NULL COMMENT '品番',
  `c_name` varchar(50) NOT NULL COMMENT '名称',
  `c_unit_id` int(11) NOT NULL COMMENT '单位ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='品番定义表';

-- 正在导出表  pdsys.pn_tbl 的数据：~1 rows (大约)
DELETE FROM `pn_tbl`;
/*!40000 ALTER TABLE `pn_tbl` DISABLE KEYS */;
INSERT INTO `pn_tbl` (`c_id`, `c_update_time`, `c_pn`, `c_name`, `c_unit_id`) VALUES
	(1, '2018-06-02 16:31:46', '111', '11111', 1);
/*!40000 ALTER TABLE `pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.purchase_bom_tbl 结构
CREATE TABLE IF NOT EXISTS `purchase_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_purchase_id` int(11) NOT NULL COMMENT '采购单号',
  `c_bom_id` int(11) NOT NULL COMMENT '原包材ID',
  `c_num` float NOT NULL COMMENT '数量',
  `c_price` float NOT NULL COMMENT '单价',
  `c_supplier_id` int(11) DEFAULT NULL COMMENT '供应商ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='采购明细单';

-- 正在导出表  pdsys.purchase_bom_tbl 的数据：~5 rows (大约)
DELETE FROM `purchase_bom_tbl`;
/*!40000 ALTER TABLE `purchase_bom_tbl` DISABLE KEYS */;
INSERT INTO `purchase_bom_tbl` (`c_id`, `c_update_time`, `c_purchase_id`, `c_bom_id`, `c_num`, `c_price`, `c_supplier_id`) VALUES
	(1, '2018-06-02 16:42:23', 1, 1, 666, 1, NULL),
	(2, '2018-06-02 16:44:26', 2, 1, 666, 1, NULL),
	(3, '2018-06-03 14:53:00', 3, 1, 666, 1, 1),
	(4, '2018-06-03 15:41:25', 4, 1, 666, 1, NULL),
	(5, '2018-06-03 16:01:23', 5, 1, 666, 1, 1);
/*!40000 ALTER TABLE `purchase_bom_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.purchase_tbl 结构
CREATE TABLE IF NOT EXISTS `purchase_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_no` varchar(50) NOT NULL COMMENT '采购单号',
  `c_user_id` int(11) NOT NULL COMMENT '采购人',
  `c_state` int(11) NOT NULL DEFAULT '0' COMMENT '状态 计划(0) 下单(1) 到货入库(2)',
  `c_create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成时间',
  `c_purchase_date` datetime DEFAULT NULL COMMENT '下单时间',
  `c_arrive_date` datetime DEFAULT NULL COMMENT '到货时间',
  `c_entry_id` int(11) DEFAULT NULL COMMENT '入库单ID',
  `c_approval_info_id` int(11) NOT NULL DEFAULT '0' COMMENT '批复节点',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='采购单';

-- 正在导出表  pdsys.purchase_tbl 的数据：~2 rows (大约)
DELETE FROM `purchase_tbl`;
/*!40000 ALTER TABLE `purchase_tbl` DISABLE KEYS */;
INSERT INTO `purchase_tbl` (`c_id`, `c_update_time`, `c_no`, `c_user_id`, `c_state`, `c_create_date`, `c_purchase_date`, `c_arrive_date`, `c_entry_id`, `c_approval_info_id`) VALUES
	(3, '2018-06-03 15:26:32', 'D_2018060217235', 2, 1, '2018-06-02 18:23:18', '2018-06-03 15:26:32', NULL, NULL, 1),
	(5, '2018-06-03 15:51:28', 'D_2018060315515', 2, 0, '2018-06-03 15:51:28', NULL, NULL, NULL, 3);
/*!40000 ALTER TABLE `purchase_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.supplier_tbl 结构
CREATE TABLE IF NOT EXISTS `supplier_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_name` varchar(50) NOT NULL COMMENT '供应商名',
  `c_address` varchar(100) DEFAULT NULL COMMENT '地址',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='供应商表';

-- 正在导出表  pdsys.supplier_tbl 的数据：~1 rows (大约)
DELETE FROM `supplier_tbl`;
/*!40000 ALTER TABLE `supplier_tbl` DISABLE KEYS */;
INSERT INTO `supplier_tbl` (`c_id`, `c_update_time`, `c_name`, `c_address`, `c_phone`) VALUES
	(1, '2018-06-03 14:52:47', '4444', '', '');
/*!40000 ALTER TABLE `supplier_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.unit_tbl 结构
CREATE TABLE IF NOT EXISTS `unit_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_name` varchar(50) NOT NULL COMMENT '单位 例：箱',
  `c_sub_name` varchar(50) NOT NULL DEFAULT '0' COMMENT '子单位 例：个',
  `c_ratio` float NOT NULL DEFAULT '0' COMMENT '和子单位的换算，如1箱=100个',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='单位定义表';

-- 正在导出表  pdsys.unit_tbl 的数据：~1 rows (大约)
DELETE FROM `unit_tbl`;
/*!40000 ALTER TABLE `unit_tbl` DISABLE KEYS */;
INSERT INTO `unit_tbl` (`c_id`, `c_update_time`, `c_name`, `c_sub_name`, `c_ratio`) VALUES
	(1, '2018-06-02 16:31:38', '11221', '11', 0);
/*!40000 ALTER TABLE `unit_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.user_role_tbl 结构
CREATE TABLE IF NOT EXISTS `user_role_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `c_role` char(50) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- 正在导出表  pdsys.user_role_tbl 的数据：~27 rows (大约)
DELETE FROM `user_role_tbl`;
/*!40000 ALTER TABLE `user_role_tbl` DISABLE KEYS */;
INSERT INTO `user_role_tbl` (`c_id`, `c_update_time`, `c_user_id`, `c_role`) VALUES
	(1, '2018-05-30 11:11:17', 1, 'admin'),
	(51, '2018-06-03 17:09:50', 3, 'admin'),
	(52, '2018-06-03 17:09:50', 3, 'e_order'),
	(53, '2018-06-03 17:09:50', 3, 'e_purchase'),
	(54, '2018-06-03 17:09:51', 3, 'app_purchase'),
	(55, '2018-06-03 17:09:51', 3, 'e_warehouse_entry_pn'),
	(56, '2018-06-03 17:09:51', 3, 'e_warehouse_entry_bom'),
	(57, '2018-06-03 17:09:51', 3, 'e_warehouse_entry_device'),
	(58, '2018-06-03 17:09:51', 3, 'e_warehouse_delivery_pn'),
	(59, '2018-06-03 17:09:51', 3, 'e_warehouse_delivery_bom'),
	(60, '2018-06-03 17:09:51', 3, 'e_warehouse_delivery_device'),
	(61, '2018-06-03 17:09:51', 3, 'e_device'),
	(62, '2018-06-03 17:09:51', 3, 'e_user'),
	(63, '2018-06-03 17:09:51', 3, 'e_master'),
	(64, '2018-06-03 17:10:07', 2, 'admin'),
	(65, '2018-06-03 17:10:07', 2, 'e_order'),
	(66, '2018-06-03 17:10:07', 2, 'e_purchase'),
	(67, '2018-06-03 17:10:07', 2, 'app_purchase'),
	(68, '2018-06-03 17:10:07', 2, 'e_warehouse_entry_pn'),
	(69, '2018-06-03 17:10:07', 2, 'e_warehouse_entry_bom'),
	(70, '2018-06-03 17:10:07', 2, 'e_warehouse_entry_device'),
	(71, '2018-06-03 17:10:07', 2, 'e_warehouse_delivery_pn'),
	(72, '2018-06-03 17:10:07', 2, 'e_warehouse_delivery_bom'),
	(73, '2018-06-03 17:10:07', 2, 'e_warehouse_delivery_device'),
	(74, '2018-06-03 17:10:07', 2, 'e_device'),
	(75, '2018-06-03 17:10:07', 2, 'e_user'),
	(76, '2018-06-03 17:10:07', 2, 'e_master');
/*!40000 ALTER TABLE `user_role_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.user_tbl 结构
CREATE TABLE IF NOT EXISTS `user_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_no` varchar(50) NOT NULL COMMENT '工番',
  `c_name` varchar(50) NOT NULL COMMENT '名字',
  `c_password` varchar(100) DEFAULT NULL COMMENT '密码',
  `c_phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `c_address` varchar(50) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户定义表';

-- 正在导出表  pdsys.user_tbl 的数据：~3 rows (大约)
DELETE FROM `user_tbl`;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` (`c_id`, `c_update_time`, `c_no`, `c_name`, `c_password`, `c_phone`, `c_address`) VALUES
	(1, '2018-05-30 11:11:23', 'admin', 'admin', '$2a$10$qbN23rlzj2.OoQv0F7kPfePrOVm7ehvdTzYMTZ54nKxXpYagbhqD.', NULL, NULL),
	(2, '2018-06-02 16:31:19', '1', '1', '$2a$10$26fo5QTMAs.K6yqSft5hXeg2Dha2J8mCPbo6rtR.hic9i4ESNCv8q', NULL, NULL),
	(3, '2018-06-03 15:37:46', '2', '2', '$2a$10$26fo5QTMAs.K6yqSft5hXeg2Dha2J8mCPbo6rtR.hic9i4ESNCv8q', NULL, NULL);
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.warehouse_bom_tbl 结构
CREATE TABLE IF NOT EXISTS `warehouse_bom_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_bom_id` int(11) NOT NULL DEFAULT '0' COMMENT 'BOMID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BOM仓库表';

-- 正在导出表  pdsys.warehouse_bom_tbl 的数据：~0 rows (大约)
DELETE FROM `warehouse_bom_tbl`;
/*!40000 ALTER TABLE `warehouse_bom_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse_bom_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.warehouse_machine_part_tbl 结构
CREATE TABLE IF NOT EXISTS `warehouse_machine_part_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_machine_part_id` int(11) NOT NULL DEFAULT '0' COMMENT '机器备件ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备备件仓库表';

-- 正在导出表  pdsys.warehouse_machine_part_tbl 的数据：~0 rows (大约)
DELETE FROM `warehouse_machine_part_tbl`;
/*!40000 ALTER TABLE `warehouse_machine_part_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse_machine_part_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.warehouse_pn_tbl 结构
CREATE TABLE IF NOT EXISTS `warehouse_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_pn_id` int(11) DEFAULT NULL COMMENT '订单条目ID',
  `c_semi_produced_num` float NOT NULL DEFAULT '0' COMMENT '半成品数量(暂时没用，准备废弃）',
  `c_produced_num` float NOT NULL DEFAULT '0' COMMENT '成品数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单条目仓库表 		\r\n';

-- 正在导出表  pdsys.warehouse_pn_tbl 的数据：~0 rows (大约)
DELETE FROM `warehouse_pn_tbl`;
/*!40000 ALTER TABLE `warehouse_pn_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse_pn_tbl` ENABLE KEYS */;

-- 导出  表 pdsys.warehouse_semi_pn_tbl 结构
CREATE TABLE IF NOT EXISTS `warehouse_semi_pn_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `c_pn_id` int(11) NOT NULL COMMENT '订单条目ID',
  `c_pn_cls_id` int(11) NOT NULL DEFAULT '0' COMMENT '子类ID',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单条目半成品仓库表 		\r\n';

-- 正在导出表  pdsys.warehouse_semi_pn_tbl 的数据：~0 rows (大约)
DELETE FROM `warehouse_semi_pn_tbl`;
/*!40000 ALTER TABLE `warehouse_semi_pn_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse_semi_pn_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
