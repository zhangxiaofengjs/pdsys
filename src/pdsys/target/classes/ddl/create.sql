CREATE TABLE `order_tbl` (
	`c_id` bigint NOT NULL AUTO_INCREMENT,
	`c_no` varchar(50) NOT NULL COMMENT '订单编号',
	`c_order_date` datetime COMMENT '下单时间',
	`c_ship_dead_date` datetime COMMENT '交货期限',
	`c_ship_date` datetime COMMENT '交货时间',
	`c_state` tinyint COMMENT '状态 0：计划中 1：生产中 2：已完成 3：已删除',
	`c_comment` varchar(50) COMMENT '备注',
	PRIMARY KEY (`c_id`),
	UNIQUE INDEX (`c_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

INSERT INTO `order_tbl` (`c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`) VALUES ('JK-900-200', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', '0');
INSERT INTO `order_tbl` (`c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`) VALUES ('JK-900-201', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', '1');
INSERT INTO `order_tbl` (`c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`) VALUES ('JK-900-202', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', '0');
INSERT INTO `order_tbl` (`c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`) VALUES ('JK-900-203', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', '1');
INSERT INTO `order_tbl` (`c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`) VALUES ('JK-900-204', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', '2');
INSERT INTO `order_tbl` (`c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`) VALUES ('JK-900-205', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', '2');
INSERT INTO `order_tbl` (`c_no`, `c_order_date`, `c_ship_dead_date`, `c_ship_date`, `c_state`) VALUES ('JK-900-206', '2018-03-28 15:54:39', '2018-03-30 15:54:45', '2018-03-28 15:54:56', '3');