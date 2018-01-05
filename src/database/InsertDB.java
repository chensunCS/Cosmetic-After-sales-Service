package database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.text.DefaultEditorKit.InsertTabAction;

import object.PresentInfo;
import object.PresentPhoto;
import object.PreviousPhoto;
import object.UserInfo;
import util.DateUtil;
import util.JdbcUtils_JNDI;

public class InsertDB {
	
	//���ݿ�����֣���̬���ͣ�
	private static final String dbName = "cosmeticDB";
	
	//�������ݿ���������Ҫ����Դ
    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;
	
	//�������ݿ�Ĺ�����
	public InsertDB(){
		
	}
	
	//�����û�ע�����Ϣ
	public int insertUserInfo (UserInfo userInfo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int insertIndex = 0;

        try{
            //��ȡ���ݿ�����
            conn = JdbcUtils_JNDI.getConnection();
            //����sql���
            String sql = "insert into user_info values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            //���ñ�����ֵ
            st.setString(1, userInfo.getUserID());
            st.setString(2, userInfo.getUserName());
            st.setString(3, userInfo.getUserPhone());
            st.setString(4, userInfo.getUserGender());
            st.setInt(5, userInfo.getUserAge());
            st.setString(6, userInfo.getUserAddress());
            st.setString(7, userInfo.getUserSkinAttr());
            st.setString(8, userInfo.getUserSkinProblem());
            st.setString(9, userInfo.getUserPresentProduct());
            st.setString(10, userInfo.getRemark());
            //�������ݿ�
            insertIndex = st.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //�ͷ���Դ
            JdbcUtils_JNDI.release(conn, st, rs);
        }
		
		//���ز��������
		return insertIndex;
	}
	
	//�����û�ע��ʱ���ϴ�ͼƬ����Ϣ
	public int insertPreviousPhoto (PreviousPhoto previousPhoto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int insertIndex = 0;

        try{
            //��ȡ���ݿ�����
            conn = JdbcUtils_JNDI.getConnection();
            //����sql���
            String sql = "insert into previous_photo values(?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            //���ñ�����ֵ
            st.setString(1, previousPhoto.getUserID());
            st.setString(2, previousPhoto.getPreviousPhotoID());
            st.setDate(3, DateUtil.utilDateToSQLDate(previousPhoto.getDate()));
            st.setString(4, previousPhoto.getFileName());
            st.setString(5, previousPhoto.getFilePath());
            //�������ݿ�
            insertIndex = st.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //�ͷ���Դ
            JdbcUtils_JNDI.release(conn, st, rs);
        }
		
		//���ز��������
		return insertIndex;
	}
	
	//�����û�Ŀǰ����Ϣ
	public int insertPresentInfo (PresentInfo presentInfo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int insertIndex = 0;

        try{
            //��ȡ���ݿ�����
            conn = JdbcUtils_JNDI.getConnection();
            //����sql���
            String sql = "insert into present_info values(?, ?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            //���ñ�����ֵ
            st.setString(1, presentInfo.getUserID());
            st.setString(2, presentInfo.getPresentInfoID());
            st.setDate(3, DateUtil.utilDateToSQLDate(presentInfo.getUseDate()));
            st.setString(4, presentInfo.getUseProduct());
            st.setString(5, presentInfo.getUseFeeling());
            st.setString(6, presentInfo.getUseProblem());
            st.setString(7, presentInfo.getRemark());
            //�������ݿ�
            insertIndex = st.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //�ͷ���Դ
            JdbcUtils_JNDI.release(conn, st, rs);
        }
		
		//���ز��������
		return insertIndex;
	}
	
	//�����û����ʱ���ϴ�ͼƬ����Ϣ
	public int insertPresentPhoto (PresentPhoto presentPhoto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int insertIndex = 0;

        try{
            //��ȡ���ݿ�����
            conn = JdbcUtils_JNDI.getConnection();
            //����sql���
            String sql = "insert into present_photo values(?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            //���ñ�����ֵ
            st.setString(1, presentPhoto.getUserID());
            st.setString(2, presentPhoto.getPresentPhotoID());
            st.setDate(3, DateUtil.utilDateToSQLDate(presentPhoto.getDate()));
            st.setString(4, presentPhoto.getFileName());
            st.setString(5, presentPhoto.getFilePath());
            //�������ݿ�
            insertIndex = st.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //�ͷ���Դ
            JdbcUtils_JNDI.release(conn, st, rs);
        }
		
		//���ز��������
		return insertIndex;
	}

}
