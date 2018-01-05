package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CreateDB {
	
	private String protocol = "jdbc:mysql:";
	private String dbName = "cosmeticDB";
	private String username = "root";
	private String password = "123456";
	private Properties userInfo;
	
	public CreateDB() {
		//初始化数据库的属性
		userInfo = new Properties();
		userInfo.put("user", username);
		userInfo.put("password", password);
	}
	
	public void createDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		//新建驱动器的对象
		String dbUrl = protocol + "//localhost:3306/" + dbName + "?useSSL=true"; 
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver).newInstance();
		//获取数据库的连接
		Connection connection = DriverManager.getConnection(dbUrl, userInfo);
		//创建Statement
		Statement statement = connection.createStatement();
		
		//创建注册用户信息table的sql语句
		String userInfo = "create table user_info ("
							+ "user_id varchar(200) not null,"
							+ "user_name varchar(100) not null,"
							+ "user_phone varchar(200) not null,"
							+ "user_gender varchar(100),"
							+ "user_age int,"
							+ "user_address varchar(500),"
							+ "user_skin_attr varchar(200),"
							+ "user_skin_problem varchar(500),"
							+ "user_present_product varchar(200),"
							+ "remark varchar(500),"
							+ "primary key (user_id)"
							+ ")";
		
		//创建用户使用产品后的信息的SQL语句
		String presentInfo = "create table present_info ("
							+ "user_id varchar(200) not null,"
							+ "present_info_id varchar(200) not null,"
							+ "use_date date not null,"
							+ "use_product varchar(500),"
							+ "use_feeling varchar(500),"
							+ "use_problem varchar(500),"
							+ "remark varchar(500),"
							+ "primary key (present_info_id),"
							+ "foreign key (user_id) references user_info(user_id)"
							+ ")";
		
		//创建注册用户时候的照片的信息SQL语句
		String previousPhoto = "create table previous_photo ("
							+ "user_id varchar(200) not null,"
							+ "previous_photo_id varchar(200) not null,"
							+ "date date not null,"
							+ "file_name varchar(500),"
							+ "file_path varchar(500),"
							+ "primary key (previous_photo_id),"
							+ "foreign key (user_id) references user_info(user_id)"
							+ ")";
		
		//创建填写试用体验的照片信息的SQL语句
		String presentPhoto = "create table present_photo ("
							+ "user_id varchar(200) not null,"
							+ "present_photo_id varchar(200) not null,"
							+ "date date not null,"
							+ "file_name varchar(500),"
							+ "file_path varchar(500),"
							+ "primary key (present_photo_id),"
							+ "foreign key (user_id) references user_info(user_id)"
							+ ")";

		//创建表									
		statement.executeUpdate(userInfo);
		statement.executeUpdate(presentInfo);
		statement.executeUpdate(previousPhoto);
		statement.executeUpdate(presentPhoto);

		
		connection.close();
		
		System.out.println("create successfully!");
	}
	
}
