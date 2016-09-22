package com.zqw.gmh.count.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	public static Timestamp getFirstDayOfMonth(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar   cal_1=Calendar.getInstance();//获取当前日期 
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String firstDay = format.format(cal_1.getTime());
        firstDay+=" 00:00:00";
        Timestamp tt =  Timestamp.valueOf(firstDay);
        return tt;
	}
	
	public static Timestamp getLastDayOfMonth(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 Calendar cale = Calendar.getInstance();  
		 cale.add(Calendar.MONTH, 1);
	     cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
	    String lastDay = format.format(cale.getTime());
	    lastDay += " 23:59:59";
	    return Timestamp.valueOf(lastDay);
	}
	public static void main(String[] args) {
		System.out.println(getFirstDayOfMonth());
		System.out.println(getLastDayOfMonth());
	}
}
