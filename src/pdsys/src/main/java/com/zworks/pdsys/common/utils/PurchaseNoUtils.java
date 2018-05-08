package com.zworks.pdsys.common.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseNoUtils {
    /**
     * 当前时间的采购单编号的生成
     */
    public static String createPurchaseNo() {
    	Format format = new SimpleDateFormat("yyyyMMddHHmmss");
    	return "D_"+format.format(new Date());
    }
        
}