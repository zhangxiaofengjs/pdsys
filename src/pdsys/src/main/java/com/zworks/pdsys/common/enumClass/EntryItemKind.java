package com.zworks.pdsys.common.enumClass;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2018/08/19
 */
public enum EntryItemKind {
	NORMAL, //正常品(0)
	DEFECTIVE, //次品或者备件的故障品(1)
	FIXRETURN, //返修入库(2)
	SCRAP, //报废品入库(3)
}
