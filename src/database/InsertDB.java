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
	
	//数据库的名字（静态类型）
	private static final String dbName = "cosmeticDB";
	
	//定义数据库连接所需要的资源
    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;
	
	//更新数据库的构造器
	public InsertDB(){
		
	}
	
	//插入用户注册的信息
	public int insertUserInfo (UserInfo userInfo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int insertIndex = 0;

        try{
            //获取数据库连接
            conn = JdbcUtils_JNDI.getConnection();
            //定义sql语句
            String sql = "insert into user_info values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            //设置变量的值
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
            //更新数据库
            insertIndex = st.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            JdbcUtils_JNDI.release(conn, st, rs);
        }
		
		//返回插入的行数
		return insertIndex;
	}
	
	//插入用户注册时候上传图片的信息
	public int insertPreviousPhoto (PreviousPhoto previousPhoto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int insertIndex = 0;

        try{
            //获取数据库连接
            conn = JdbcUtils_JNDI.getConnection();
            //定义sql语句
            String sql = "insert into previous_photo values(?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            //设置变量的值
            st.setString(1, previousPhoto.getUserID());
            st.setString(2, previousPhoto.getPreviousPhotoID());
            st.setDate(3, DateUtil.utilDateToSQLDate(previousPhoto.getDate()));
            st.setString(4, previousPhoto.getFileName());
            st.setString(5, previousPhoto.getFilePath());
            //更新数据库
            insertIndex = st.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            JdbcUtils_JNDI.release(conn, st, rs);
        }
		
		//返回插入的行数
		return insertIndex;
	}
	
	//插入用户目前的信息
	public int insertPresentInfo (PresentInfo presentInfo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int insertIndex = 0;

        try{
            //获取数据库连接
            conn = JdbcUtils_JNDI.getConnection();
            //定义sql语句
            String sql = "insert into present_info values(?, ?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            //设置变量的值
            st.setString(1, presentInfo.getUserID());
            st.setString(2, presentInfo.getPresentInfoID());
            st.setDate(3, DateUtil.utilDateToSQLDate(presentInfo.getUseDate()));
            st.setString(4, presentInfo.getUseProduct());
            st.setString(5, presentInfo.getUseFeeling());
            st.setString(6, presentInfo.getUseProblem());
            st.setString(7, presentInfo.getRemark());
            //更新数据库
            insertIndex = st.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            JdbcUtils_JNDI.release(conn, st, rs);
        }
		
		//返回插入的行数
		return insertIndex;
	}
	
	//插入用户填表时候上传图片的信息
	public int insertPresentPhoto (PresentPhoto presentPhoto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int insertIndex = 0;

        try{
            //获取数据库连接
            conn = JdbcUtils_JNDI.getConnection();
            //定义sql语句
            String sql = "insert into present_photo values(?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            //设置变量的值
            st.setString(1, presentPhoto.getUserID());
            st.setString(2, presentPhoto.getPresentPhotoID());
            st.setDate(3, DateUtil.utilDateToSQLDate(presentPhoto.getDate()));
            st.setString(4, presentPhoto.getFileName());
            st.setString(5, presentPhoto.getFilePath());
            //更新数据库
            insertIndex = st.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            JdbcUtils_JNDI.release(conn, st, rs);
        }
		
		//返回插入的行数
		return insertIndex;
	}

}
