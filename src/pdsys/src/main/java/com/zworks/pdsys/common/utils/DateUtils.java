package com.zworks.pdsys.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 * 
 * @author ZHAI
 * 
 * @date 2018-01-22
 */
public class DateUtils {
	
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    
    public static Date parse(String strDate) {
	    try {
	    	SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
	        Date date= df.parse(strDate);
	        return date;
	    } catch(ParseException px) {
	        px.printStackTrace();
	    }
	    return null;
    }
    
    public static Date addDay(Date date, int days) {
    	if(date == null) {
    		return null;
    	}
    	Calendar ca = Calendar.getInstance();
    	ca.setTime(date);
    	ca.add(Calendar.DATE, days);
    	
    	return ca.getTime();
    }

	public static Date now() {
		Calendar ca = Calendar.getInstance();
		return ca.getTime();
	}

	public static Date thisMonthStart() {
		Date now = now();
		Calendar ca = Calendar.getInstance();
    	ca.setTime(now);
    	ca.set(Calendar.DAY_OF_MONTH, 1);
    	return ca.getTime();
	}

	public static Date endOfDay(Date end) {
		if(end == null) {
			return end;
		}
		Calendar ca = Calendar.getInstance();
    	ca.setTime(end);
    	ca.set(Calendar.HOUR_OF_DAY, 23);
    	ca.set(Calendar.MINUTE, 59);
    	ca.set(Calendar.SECOND, 59);
    	ca.set(Calendar.MILLISECOND, 999);
    	return ca.getTime();
	}

	public static Date startOfDay(Date start) {
		if(start == null) {
			return start;
		}
		Calendar ca = Calendar.getInstance();
    	ca.setTime(start);
    	ca.set(Calendar.HOUR_OF_DAY, 0);
    	ca.set(Calendar.MINUTE, 0);
    	ca.set(Calendar.SECOND, 0);
    	ca.set(Calendar.MILLISECOND, 0);
    	return ca.getTime();
	}
	
	public static Date getCurrentDate() {
		Date now = now();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
    	try {
			return sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return now;
	}
}
