package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.JdbcUtils_JNDI;

public class DeleteDB {
	
	public int delete(String sql) throws SQLException {
		//获取数据库连接
	    Connection conn = JdbcUtils_JNDI.getConnection();
	    int index = 0;
	    //获取预定义的语句
	    PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);;
	    try {
	        //获取需要删除的索引
	        index = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally {
	    	//关闭预定义和连接
	        pstmt.close();
	        conn.close();
	    }
	    return index;
	}

}
