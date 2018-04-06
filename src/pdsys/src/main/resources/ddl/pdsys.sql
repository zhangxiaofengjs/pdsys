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

--  テーブル pdsys.delivery_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `delivery_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_delivery_time` datetime DEFAULT NULL COMMENT '出库时间',
  `c_state` int(11) NOT NULL DEFAULT '0' COMMENT '出库单状态 出库中(0) 已出库(1)',
  `c_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `c_update_user_id` int(11) DEFAULT NULL COMMENT '更新者用户ID',
  PRIMARY KEY (`c_id`),
  KEY `c_id` (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='出库单表';

-- テーブル pdsys.delivery_tbl: ~17 rows (約) のデータをダンプしています
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
	(18, 1, NULL, 0, '2018-04-05 11:26:58', 0),
	(19, 1, NULL, 0, '2018-04-06 19:05:13', NULL),
	(20, 1, NULL, 0, '2018-04-06 19:05:21', NULL),
	(21, 1, NULL, 0, '2018-04-06 19:05:35', NULL),
	(22, 1, NULL, 0, '2018-04-06 19:06:02', NULL),
	(23, 1, NULL, 0, '2018-04-06 19:06:43', NULL),
	(24, 1, NULL, 0, '2018-04-06 19:08:23', NULL);
/*!40000 ALTER TABLE `delivery_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
