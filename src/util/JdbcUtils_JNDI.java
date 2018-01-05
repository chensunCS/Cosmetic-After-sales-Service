package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcUtils_JNDI {
    
    private static DataSource ds = null;
    //�ھ�̬������д������ݿ����ӳ�
    static{
        try{
             //��ʼ��JNDI
            Context initCtx = new InitialContext();
             //�õ�JNDI����
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
             //��JNDI�����м���nameΪjdbc/datasource������Դ
            ds = (DataSource)envCtx.lookup("jdbc/datasource");
        }catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static Connection getConnection() throws SQLException{
        //������Դ�л�ȡ���ݿ�����
        return ds.getConnection();
    }
    
    public static void release(Connection conn,Statement st,ResultSet rs){
        if(rs!=null){
            try{
                //�رմ洢��ѯ�����ResultSet����
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(st!=null){
            try{
                //�رո���ִ��SQL�����Statement����
                st.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if(conn!=null){
            try{
                //��Connection���Ӷ��󻹸����ݿ����ӳ�
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}