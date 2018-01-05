package util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	//������׼���ڸ�ʽ
	public static String buildDateString(String year, String month, String day) {
		return year + "-" + month + "-" + day;
	}
	
	//������ת�����ַ���
	public static String dateToString(Date date) {
		//������ת�����ַ���
		String dateString = (new SimpleDateFormat("yyyy-MM-dd")).format(date); 
		//���������ַ���
		return dateString;
	}
	
	//��ʱ��ת�����ַ���
	public static String timeToString(Date time) {
		//��ʱ��ת�����ַ���
		String timeString = (new SimpleDateFormat("hh:mm:ss")).format(time);
		//����ʱ���ַ���
		return timeString;
	}
	
	//������ʱ��ת�����ַ���
	public static String datetimeToString(Date datetime) {
		//������ʱ��ת����zifuchuan
		String datetimeString = (new SimpleDateFormat("yyyy-MM-dd hh:mm")).format(datetime);
		//��������ʱ���ַ���
		return datetimeString;
	}
	
	//���ַ���ת��������
	public static Date stringToDate(String dateString) throws ParseException {
		//�������ַ�����ʽ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//���������ַ���
		Date date = sdf.parse(dateString);
		//����date����
		return date;
	}
	
	//���ַ���ת����ʱ��
	public static Time stringToTime(String timeString) throws ParseException {
		//��ʱ���ַ�����ʽ��
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		//����ʱ���ַ���
		Date tempDate = sdf.parse(timeString);
		//����date����
		return new Time(tempDate.getTime());
	}
	
	//��Util������ת����SQL����
	public static java.sql.Date utilDateToSQLDate(java.util.Date utilDate) {
		//����SQL��date
		java.sql.Date sqlDate = null;
		sqlDate = new java.sql.Date(utilDate.getTime());
		//����SQLDate����
		return sqlDate;
	}
	
	//��SQL����ת����Util����
	public static java.util.Date SQLDateToUtilDate(java.sql.Date sqlDate) {
		//����Util��date
		java.util.Date utilDate = null;
		utilDate = new java.util.Date(sqlDate.getTime());
		//����UtilDate����
		return utilDate;
	}

}
