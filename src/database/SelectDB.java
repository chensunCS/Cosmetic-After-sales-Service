package database;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

import object.*;
import util.DateUtil;
import util.JdbcUtils_JNDI;

public class SelectDB {
	
	
	//定义数据库连接所需要的资源
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    /**
     * 计算注册用户的数量
     */
    public int countTable(String countSQL) {
    	//定义数量变量
    	int number = 0;
    	try {
            //获取数据库连接
            conn = JdbcUtils_JNDI.getConnection();
            //获取连接数据库后的资源
            preparedStatement = conn.prepareStatement(countSQL);
            resultSet = preparedStatement.executeQuery();
            //获取数据
            while (resultSet.next()) {
            	number = Integer.parseInt(resultSet.getString("count"));
            }
	    }catch (Exception e) {
	        e.printStackTrace();
	    }finally{
	        //释放资源
	        JdbcUtils_JNDI.release(conn, preparedStatement, resultSet);
	    }
	    
    	//返回数量
    	return number;
    }
	
	/**
	 * 选取user_info表的数据
	 */
	public ArrayList<UserInfo> selectUserInfo(String userInfoSQL) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//创建Video的ArrayList<Video>对象
		ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();
		//定义临时用户对象
		UserInfo tempUserInfo = null;
		
        try{
            //获取数据库连接
            conn = JdbcUtils_JNDI.getConnection();
            //获取连接数据库后的资源
            preparedStatement = conn.prepareStatement(userInfoSQL);
            resultSet = preparedStatement.executeQuery();
            //便利返回的结果
	        while (resultSet.next()) {
	        	//新创建一个UserInfo对象
	        	tempUserInfo = new UserInfo();
	        	//设置该对象的属性
	        	tempUserInfo.setUserID(resultSet.getString("user_id"));
	        	tempUserInfo.setUserName(resultSet.getString("user_name"));
	        	tempUserInfo.setRemark(resultSet.getString("remark"));
	        	tempUserInfo.setUserAddress(resultSet.getString("user_address"));
	        	tempUserInfo.setUserAge(resultSet.getInt("user_age"));
	        	tempUserInfo.setUserGender(resultSet.getString("user_gender"));
	        	tempUserInfo.setUserPhone(resultSet.getString("user_phone"));
	        	tempUserInfo.setUserPresentProduct(resultSet.getString("user_present_product"));
	        	tempUserInfo.setUserSkinAttr(resultSet.getString("user_skin_attr"));
	        	tempUserInfo.setUserSkinProblem(resultSet.getString("user_skin_problem"));
	        	//将新创建的对象添加到Array List中
	        	userInfos.add(tempUserInfo);
	        }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            JdbcUtils_JNDI.release(conn, preparedStatement, resultSet);
        }

	    //返回整个ArrayList<Video>
	    return userInfos;
	}
	
	/**
	 * 选取present_info表的数据
	 */
	public ArrayList<PresentInfo> selectPresentInfo(String presentInfoSQL) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//创建Video的ArrayList<Video>对象
		ArrayList<PresentInfo> presentInfos = new ArrayList<PresentInfo>();
		//定义临时用户对象
		PresentInfo tempPresentInfo = null;
		
        try{
            //获取数据库连接
            conn = JdbcUtils_JNDI.getConnection();
            //获取连接数据库后的资源
            preparedStatement = conn.prepareStatement(presentInfoSQL);
            resultSet = preparedStatement.executeQuery();
            //便利返回的结果
	        while (resultSet.next()) {
	        	//新创建一个UserInfo对象
	        	tempPresentInfo = new PresentInfo();
	        	//设置该对象的属性
	        	tempPresentInfo.setUserID(resultSet.getString("user_id"));
	        	tempPresentInfo.setPresentInfoID(resultSet.getString("present_info_id"));
	        	tempPresentInfo.setRemark(resultSet.getString("remark"));
	        	tempPresentInfo.setUseDate(DateUtil.SQLDateToUtilDate(resultSet.getDate("use_date")));
	        	tempPresentInfo.setUseFeeling(resultSet.getString("use_feeling"));
	        	tempPresentInfo.setUseProblem(resultSet.getString("use_problem"));
	        	tempPresentInfo.setUseProduct(resultSet.getString("use_product"));
	        	//将新创建的对象添加到Array List中
	        	presentInfos.add(tempPresentInfo);
	        }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            JdbcUtils_JNDI.release(conn, preparedStatement, resultSet);
        }

	    //返回整个ArrayList<Video>
	    return presentInfos;
	}
	
	/**
	 * 选取present_photo表的数据
	 */
	public ArrayList<PresentPhoto> selectPresentPhoto(String presentPhoto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//创建Video的ArrayList<Video>对象
		ArrayList<PresentPhoto> presentPhotos = new ArrayList<PresentPhoto>();
		//定义临时用户对象
		PresentPhoto tempPresentPhoto = null;
		
        try{
            //获取数据库连接
            conn = JdbcUtils_JNDI.getConnection();
            //获取连接数据库后的资源
            preparedStatement = conn.prepareStatement(presentPhoto);
            resultSet = preparedStatement.executeQuery();
            //便利返回的结果
	        while (resultSet.next()) {
	        	//新创建一个UserInfo对象
	        	tempPresentPhoto = new PresentPhoto();
	        	//设置该对象的属性
	        	tempPresentPhoto.setUserID(resultSet.getString("user_id"));
	        	tempPresentPhoto.setDate(DateUtil.SQLDateToUtilDate(resultSet.getDate("date")));
	        	tempPresentPhoto.setFileName(resultSet.getString("file_name"));
	        	tempPresentPhoto.setFilePath(resultSet.getString("file_path"));
	        	tempPresentPhoto.setPresentPhotoID(resultSet.getString("present_photo_id"));
	        	//将新创建的对象添加到Array List中
	        	presentPhotos.add(tempPresentPhoto);
	        }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            JdbcUtils_JNDI.release(conn, preparedStatement, resultSet);
        }

	    //返回整个ArrayList<Video>
	    return presentPhotos;
	}
	
	/**
	 * 选取previous_photo表的数据
	 */
	public ArrayList<PreviousPhoto> selectPreviousPhoto(String previousPhoto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//创建Video的ArrayList<Video>对象
		ArrayList<PreviousPhoto> previousPhotos = new ArrayList<PreviousPhoto>();
		//定义临时用户对象
		PreviousPhoto tempPreviousPhoto = null;
		
        try{
            //获取数据库连接
            conn = JdbcUtils_JNDI.getConnection();
            //获取连接数据库后的资源
            preparedStatement = conn.prepareStatement(previousPhoto);
            resultSet = preparedStatement.executeQuery();
            //便利返回的结果
	        while (resultSet.next()) {
	        	//新创建一个PreviousPhoto对象
	        	tempPreviousPhoto = new PreviousPhoto();
	        	//设置该对象的属性
	        	tempPreviousPhoto.setUserID(resultSet.getString("user_id"));
	        	tempPreviousPhoto.setPreviousPhotoID(resultSet.getString("previous_photo_id"));
	        	tempPreviousPhoto.setDate(DateUtil.SQLDateToUtilDate(resultSet.getDate("date")));
	        	tempPreviousPhoto.setFileName(resultSet.getString("file_name"));
	        	tempPreviousPhoto.setFilePath(resultSet.getString("file_path"));
	        	//将新创建的对象添加到Array List中
	        	previousPhotos.add(tempPreviousPhoto);
	        }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            JdbcUtils_JNDI.release(conn, preparedStatement, resultSet);
        }

	    //返回整个ArrayList<PreviousPhoto>
	    return previousPhotos;
	}

}
