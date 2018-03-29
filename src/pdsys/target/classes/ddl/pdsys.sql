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

--  テーブル pdsys.order_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `order_tbl` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_no` varchar(50) NOT NULL COMMENT '订单编号',
  `c_order_date` datetime DEFAULT NULL COMMENT '下单时间',
  `c_ship_dead_date` datetime DEFAULT NULL COMMENT '交货期限',
  `c_ship_date` datetime DEFAULT NULL COMMENT '交货时间',
  `c_state` tinyint(4) DEFAULT NULL COMMENT '状态 0：计划中 1：生产中 2：已完成 3：已删除',
  `c_comment` varchar(50) DEFAULT NULL COMMENT '备注',
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

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
