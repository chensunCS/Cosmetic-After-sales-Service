package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.JdbcUtils_JNDI;

public class DeleteDB {
	
	public int delete(String sql) throws SQLException {
		//��ȡ���ݿ�����
	    Connection conn = JdbcUtils_JNDI.getConnection();
	    int index = 0;
	    //��ȡԤ��������
	    PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);;
	    try {
	        //��ȡ��Ҫɾ��������
	        index = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally {
	    	//�ر�Ԥ���������
	        pstmt.close();
	        conn.close();
	    }
	    return index;
	}

}
