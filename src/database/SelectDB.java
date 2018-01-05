package database;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

import object.*;
import util.DateUtil;
import util.JdbcUtils_JNDI;

public class SelectDB {
	
	
	//�������ݿ���������Ҫ����Դ
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    /**
     * ����ע���û�������
     */
    public int countTable(String countSQL) {
    	//������������
    	int number = 0;
    	try {
            //��ȡ���ݿ�����
            conn = JdbcUtils_JNDI.getConnection();
            //��ȡ�������ݿ�����Դ
            preparedStatement = conn.prepareStatement(countSQL);
            resultSet = preparedStatement.executeQuery();
            //��ȡ����
            while (resultSet.next()) {
            	number = Integer.parseInt(resultSet.getString("count"));
            }
	    }catch (Exception e) {
	        e.printStackTrace();
	    }finally{
	        //�ͷ���Դ
	        JdbcUtils_JNDI.release(conn, preparedStatement, resultSet);
	    }
	    
    	//��������
    	return number;
    }
	
	/**
	 * ѡȡuser_info�������
	 */
	public ArrayList<UserInfo> selectUserInfo(String userInfoSQL) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//����Video��ArrayList<Video>����
		ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();
		//������ʱ�û�����
		UserInfo tempUserInfo = null;
		
        try{
            //��ȡ���ݿ�����
            conn = JdbcUtils_JNDI.getConnection();
            //��ȡ�������ݿ�����Դ
            preparedStatement = conn.prepareStatement(userInfoSQL);
            resultSet = preparedStatement.executeQuery();
            //�������صĽ��
	        while (resultSet.next()) {
	        	//�´���һ��UserInfo����
	        	tempUserInfo = new UserInfo();
	        	//���øö��������
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
	        	//���´����Ķ�����ӵ�Array List��
	        	userInfos.add(tempUserInfo);
	        }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //�ͷ���Դ
            JdbcUtils_JNDI.release(conn, preparedStatement, resultSet);
        }

	    //��������ArrayList<Video>
	    return userInfos;
	}
	
	/**
	 * ѡȡpresent_info�������
	 */
	public ArrayList<PresentInfo> selectPresentInfo(String presentInfoSQL) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//����Video��ArrayList<Video>����
		ArrayList<PresentInfo> presentInfos = new ArrayList<PresentInfo>();
		//������ʱ�û�����
		PresentInfo tempPresentInfo = null;
		
        try{
            //��ȡ���ݿ�����
            conn = JdbcUtils_JNDI.getConnection();
            //��ȡ�������ݿ�����Դ
            preparedStatement = conn.prepareStatement(presentInfoSQL);
            resultSet = preparedStatement.executeQuery();
            //�������صĽ��
	        while (resultSet.next()) {
	        	//�´���һ��UserInfo����
	        	tempPresentInfo = new PresentInfo();
	        	//���øö��������
	        	tempPresentInfo.setUserID(resultSet.getString("user_id"));
	        	tempPresentInfo.setPresentInfoID(resultSet.getString("present_info_id"));
	        	tempPresentInfo.setRemark(resultSet.getString("remark"));
	        	tempPresentInfo.setUseDate(DateUtil.SQLDateToUtilDate(resultSet.getDate("use_date")));
	        	tempPresentInfo.setUseFeeling(resultSet.getString("use_feeling"));
	        	tempPresentInfo.setUseProblem(resultSet.getString("use_problem"));
	        	tempPresentInfo.setUseProduct(resultSet.getString("use_product"));
	        	//���´����Ķ�����ӵ�Array List��
	        	presentInfos.add(tempPresentInfo);
	        }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //�ͷ���Դ
            JdbcUtils_JNDI.release(conn, preparedStatement, resultSet);
        }

	    //��������ArrayList<Video>
	    return presentInfos;
	}
	
	/**
	 * ѡȡpresent_photo�������
	 */
	public ArrayList<PresentPhoto> selectPresentPhoto(String presentPhoto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//����Video��ArrayList<Video>����
		ArrayList<PresentPhoto> presentPhotos = new ArrayList<PresentPhoto>();
		//������ʱ�û�����
		PresentPhoto tempPresentPhoto = null;
		
        try{
            //��ȡ���ݿ�����
            conn = JdbcUtils_JNDI.getConnection();
            //��ȡ�������ݿ�����Դ
            preparedStatement = conn.prepareStatement(presentPhoto);
            resultSet = preparedStatement.executeQuery();
            //�������صĽ��
	        while (resultSet.next()) {
	        	//�´���һ��UserInfo����
	        	tempPresentPhoto = new PresentPhoto();
	        	//���øö��������
	        	tempPresentPhoto.setUserID(resultSet.getString("user_id"));
	        	tempPresentPhoto.setDate(DateUtil.SQLDateToUtilDate(resultSet.getDate("date")));
	        	tempPresentPhoto.setFileName(resultSet.getString("file_name"));
	        	tempPresentPhoto.setFilePath(resultSet.getString("file_path"));
	        	tempPresentPhoto.setPresentPhotoID(resultSet.getString("present_photo_id"));
	        	//���´����Ķ�����ӵ�Array List��
	        	presentPhotos.add(tempPresentPhoto);
	        }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //�ͷ���Դ
            JdbcUtils_JNDI.release(conn, preparedStatement, resultSet);
        }

	    //��������ArrayList<Video>
	    return presentPhotos;
	}
	
	/**
	 * ѡȡprevious_photo�������
	 */
	public ArrayList<PreviousPhoto> selectPreviousPhoto(String previousPhoto) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//����Video��ArrayList<Video>����
		ArrayList<PreviousPhoto> previousPhotos = new ArrayList<PreviousPhoto>();
		//������ʱ�û�����
		PreviousPhoto tempPreviousPhoto = null;
		
        try{
            //��ȡ���ݿ�����
            conn = JdbcUtils_JNDI.getConnection();
            //��ȡ�������ݿ�����Դ
            preparedStatement = conn.prepareStatement(previousPhoto);
            resultSet = preparedStatement.executeQuery();
            //�������صĽ��
	        while (resultSet.next()) {
	        	//�´���һ��PreviousPhoto����
	        	tempPreviousPhoto = new PreviousPhoto();
	        	//���øö��������
	        	tempPreviousPhoto.setUserID(resultSet.getString("user_id"));
	        	tempPreviousPhoto.setPreviousPhotoID(resultSet.getString("previous_photo_id"));
	        	tempPreviousPhoto.setDate(DateUtil.SQLDateToUtilDate(resultSet.getDate("date")));
	        	tempPreviousPhoto.setFileName(resultSet.getString("file_name"));
	        	tempPreviousPhoto.setFilePath(resultSet.getString("file_path"));
	        	//���´����Ķ�����ӵ�Array List��
	        	previousPhotos.add(tempPreviousPhoto);
	        }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //�ͷ���Դ
            JdbcUtils_JNDI.release(conn, preparedStatement, resultSet);
        }

	    //��������ArrayList<PreviousPhoto>
	    return previousPhotos;
	}

}
