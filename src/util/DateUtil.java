package util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	//创建标准日期格式
	public static String buildDateString(String year, String month, String day) {
		return year + "-" + month + "-" + day;
	}
	
	//将日期转换成字符串
	public static String dateToString(Date date) {
		//将日期转化成字符串
		String dateString = (new SimpleDateFormat("yyyy-MM-dd")).format(date); 
		//返回日期字符串
		return dateString;
	}
	
	//将时间转换成字符串
	public static String timeToString(Date time) {
		//将时间转换成字符串
		String timeString = (new SimpleDateFormat("hh:mm:ss")).format(time);
		//返回时间字符串
		return timeString;
	}
	
	//将日期时间转换成字符串
	public static String datetimeToString(Date datetime) {
		//将日期时间转换成zifuchuan
		String datetimeString = (new SimpleDateFormat("yyyy-MM-dd hh:mm")).format(datetime);
		//返回日期时间字符串
		return datetimeString;
	}
	
	//将字符串转换成日期
	public static Date stringToDate(String dateString) throws ParseException {
		//将日期字符串格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//解析日期字符串
		Date date = sdf.parse(dateString);
		//返回date对象
		return date;
	}
	
	//将字符串转换成时间
	public static Time stringToTime(String timeString) throws ParseException {
		//将时间字符串格式化
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		//解析时间字符串
		Date tempDate = sdf.parse(timeString);
		//返回date对象
		return new Time(tempDate.getTime());
	}
	
	//经Util的日期转换成SQL日期
	public static java.sql.Date utilDateToSQLDate(java.util.Date utilDate) {
		//定义SQL的date
		java.sql.Date sqlDate = null;
		sqlDate = new java.sql.Date(utilDate.getTime());
		//返回SQLDate对象
		return sqlDate;
	}
	
	//将SQL日期转换成Util日期
	public static java.util.Date SQLDateToUtilDate(java.sql.Date sqlDate) {
		//定义Util的date
		java.util.Date utilDate = null;
		utilDate = new java.util.Date(sqlDate.getTime());
		//返回UtilDate对象
		return utilDate;
	}

}
