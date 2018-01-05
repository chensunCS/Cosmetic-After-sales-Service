package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.JdbcUtils_JNDI;

public class JNDITest extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	//�������ݿ���������Ҫ����Դ
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            //��ȡ���ݿ�����
            conn = JdbcUtils_JNDI.getConnection();
            //����sql���
            String sql = "insert into test1(name) values(?)";
            st = conn.prepareStatement(sql);
            //���ñ�����ֵ
            st.setString(1, "gacl");
            //�������ݿ�
            st.executeUpdate();
            //��ȡ���ݿ��Զ����ɵ�����
            rs = st.getGeneratedKeys();
            if(rs.next()){
                System.out.println(rs.getInt(1));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //�ͷ���Դ
            JdbcUtils_JNDI.release(conn, st, rs);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}