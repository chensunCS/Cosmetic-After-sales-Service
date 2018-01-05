package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DBUtil {
	
	//数据库的名字（静态类型）
	private static final String dbName = "Babybed";
		
	//根据表名匹配相关的表头
	public static String[] matchTableTitle(String tableName) {
		//根据表名来匹配相应的表头
		if(tableName.equals(Source.userInfoTable)) {
			return Source.userInfoTitle;
		}
		else if(tableName.equals(Source.presentInfoTable)) {
			return Source.presentInfoTitle;
		}
		else if(tableName.equals(Source.previousPhotoTable)) {
			return Source.previousPhotoTitle;
		}
		else if(tableName.equals(Source.presentPhotoTable)) {
			return Source.presentPhotoTitle;
		}
		else if(tableName.equals(Source.registerInfoTable)) {
			return Source.registerInfoTitle;
		}
		else if(tableName.equals(Source.feedBackInfoTable)) {
			return Source.feedbackInfoTitle;
		}
		else {
			return null;
		}
	}
	
	//获取数据库的连接connection
	private static  Connection getConnected() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		String mySqlUrl = "jdbc:mysql:" + "//localhost:3306/" + dbName;
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver).newInstance(); 
		Properties userInfo = new Properties();
		userInfo.put("user", "root");
		userInfo.put("password", "123456");
		return DriverManager.getConnection(mySqlUrl, userInfo);
	}

}
