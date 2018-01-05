package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.PreviousPhoto;
import object.UserInfo;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import database.InsertDB;
import database.SelectDB;
import util.ALiYunUtil;
import util.DateUtil;
import util.Source;
import util.TranslateUtil;

import javax.servlet.annotation.*;

/**
* @ClassName: UploadHandleServlet
* @Description: TODO(������һ�仰��������������)
*
*/ 

@WebServlet("/servlet/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	
	//�û�ID
	private String userID = "";
	//�ļ�ID
	private String fileID = "";
	//�û���
	private String userName = "";
	//�û��绰
	private String userPhone = "";
	//�û��Ա�
	private String userGender = "Ů";
	//�û�����
	private int userAge = 0;
	//�û�סַ
	private String userAddress = "";
	//�û�Ƥ������
	private String userSkinAttr = "";
	//�û�Ƥ������״
	private String userSkinProblem = "";
	//�û�Ŀǰ��ʹ�õĲ�Ʒ
	private String userPresentProduct = "";
	//�ϴ�ͼƬ����
	private Date uploadPhotoDate = new Date();
	//�û���ע
	private String userRemark = "";
	//�洢ͼƬ��ArrayList
	private ArrayList<PreviousPhoto> previousPhotos = null;
	//����������ݿ�Ķ���
	private InsertDB insertDB = new InsertDB();
	
	//����·��
	private String userInfoPath = "";
	//��Ҫ�ϴ��ļ���·��
	private String uploadFilePath = "";
	//��Ҫ�ϴ��ļ�������
	private String uploadFileName = "";
	//�ļ�����չ��
	private String fileExtName = "";
	//��Ҫ�ϴ��ļ�
	private InputStream uploadInputStream = null;
	
	//���������Ƶ��ϴ�����
	private ALiYunUtil aLiYunUtil = new ALiYunUtil();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    
				//�����������
				response.setContentType("text/xml;charset=utf-8");
				//��ȡ�����html��Ϣ��д��
				PrintWriter writer = response.getWriter();
    	
                //�õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
                userInfoPath = Source.uploadPath;
                //��������·��
                System.out.println(userInfoPath);
                
                //��ʼ��Array List
                previousPhotos = new ArrayList<PreviousPhoto>();
                
                try{
                    //ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
                    //1������һ��DiskFileItemFactory����
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    //���ù����Ļ������Ĵ�С�����ϴ����ļ���С�����������Ĵ�Сʱ���ͻ�����һ����ʱ�ļ���ŵ�ָ������ʱĿ¼���С�
                    factory.setSizeThreshold(1024*100);//���û������Ĵ�СΪ100KB�������ָ������ô�������Ĵ�СĬ����10KB
                    //2������һ���ļ��ϴ�������
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    //�����ļ��ϴ�����
                    upload.setProgressListener(new ProgressListener(){
                        public void update(long pBytesRead, long pContentLength, int arg2) {
                        }
                    });
                     //����ϴ��ļ�������������
                    upload.setHeaderEncoding("UTF-8"); 
                    //3���ж��ύ�����������Ƿ����ϴ���������
                    if(!ServletFileUpload.isMultipartContent(request)){
                        //���մ�ͳ��ʽ��ȡ����
                        return;
                    }
                    //�����ϴ������ļ��Ĵ�С�����ֵ��Ŀǰ������Ϊ1024*1024�ֽڣ�Ҳ����200MB
                    upload.setFileSizeMax(1024*1024*200);
                    //�����ϴ��ļ����������ֵ�����ֵ=ͬʱ�ϴ��Ķ���ļ��Ĵ�С�����ֵ�ĺͣ�Ŀǰ����Ϊ1000MB
                    upload.setSizeMax(1024*1024*1000);
                    //4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
                    List<FileItem> list = upload.parseRequest(request);
                    
                    //��ʼ�����ϴ�������
                    for(FileItem item : list){
                        //���fileitem�з�װ������ͨ�����������
                        if(item.isFormField()){
                            String name = item.getFieldName();
                            //�����ͨ����������ݵ�������������
                            String value = item.getString("UTF-8");
                            //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                            System.out.println(name + "=" + value);
                            
                            //��ȡ�ϴ���Ϣ
                            getRegisterInfo(name, value);
                            
                            //�ж��Ƿ�����û�
                            if(getUserNum(name, value) > 0) {
                            	//����������˳����ϴ�����������Ӧ����Ϣ
                            	writer.write("occupied");
                            	writer.flush();
                            	return ;
                            }
                        }else{//���fileitem�з�װ�����ϴ��ļ�
                            //�õ��ϴ����ļ����ƣ�
                            uploadFilePath = item.getName();
                            
                            //��ȡ�ļ���
                            uploadInputStream = item.getInputStream();
                            
                            //�ж�·���Ƿ�Ϊ��
                            if(uploadFilePath==null || uploadFilePath.trim().equals("")){
                                continue;
                            }
                            
                            //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
                            //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
                            uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf("\\")+1);
                            
                            //�õ��ϴ��ļ�����չ��
                            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
                            //�����Ҫ�����ϴ����ļ����ͣ���ô����ͨ���ļ�����չ�����ж��ϴ����ļ������Ƿ�Ϸ�
                            System.out.println("�ϴ����ļ�����չ���ǣ�"+fileExtName);
                            
                            //�õ��ļ��ı���Ŀ¼
                            String storagePath = makeStorePath(fileID, userInfoPath);
                            System.out.println("�ϴ�·���ǣ�" + storagePath + uploadFileName);
                            System.out.println("�����ļ�·���ǣ�" + uploadFilePath);
                            
                            //�ϴ��ļ����������ϣ����ж��Ƿ��ϴ��ɹ�
                            if(!aLiYunUtil.uploadFile(uploadInputStream, storagePath + uploadFileName)) {
                            	//����ϴ����ɹ��������ú���
                            	writer.write("fail");
                            	writer.flush();
                            	return ;
                            }
                            
                            //�����ļ����Լ��ļ���ʽ�������󣬲����ö���浽���ݿ���
                            setPreviousPhoto(uploadFileName, storagePath);
                        }
                    }
                }catch (FileUploadBase.FileSizeLimitExceededException e) {
                    e.printStackTrace();
                    writer.write("single exceed");
                    writer.flush();
                    return;
                }catch (FileUploadBase.SizeLimitExceededException e) {
                    e.printStackTrace();
                    writer.write("all exceed");
                    writer.flush();
                    return;
                }catch (Exception e) {
                    writer.write("fail");
                    writer.flush();
                    e.printStackTrace();
                    return ;
                }
                
                //���ս��û���Ϣ���뵽���ݿ���
                try {
                	//���û���ע����Ϣ���뵽���ݿ���
					setUserInfoObj();
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | ParseException
						| SQLException e) {
					e.printStackTrace();
                    writer.write("fail");
                    writer.flush();
					return ;
				}
                System.out.println("size:" + previousPhotos.size());
                //���û��ϴ�ͼƬ����Ϣ�浽���ݿ���
                for(int i = 0 ; i < previousPhotos.size() ; i++) {
                	try {
						insertDB.insertPreviousPhoto(previousPhotos.get(i));
					} catch (InstantiationException | IllegalAccessException
							| ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
	                    writer.write("fail");
	                    writer.flush();
						return ;
					}
                }
    }
    
    /**
     * �ж��û��Ƿ��Ѿ�ע��ķ���
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    private int getUserNum(String name, String value) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	//�����ȡ�����û�����������ж�
    	if(name.equals("user_phone")) {
    		//�����û�������
    		int userNum = 0;
    		//����select���
    		String userInfoSQL = "select * from user_info where user_phone = '" + value + "'";
    		userNum = new SelectDB().selectUserInfo(userInfoSQL).size();
    		//�����û�����
    		return userNum;
    	}
    	
    	return 0;
    }
    
    /**
     * ��ȡ�ϴ��Ļ�����Ϣ
     */
    private void getRegisterInfo(String name, String value) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParseException {
    	//��ȡ��������
    	switch(name) {
    	case "user_name":
    		userName = value;
    		break;
    	case "user_phone":
    		userPhone = value;
    		//����userID
    		userID = userName + "_" + userPhone;
    		//�����ļ�ID
    		fileID = TranslateUtil.getPingYin(userName) + "_" + userPhone;
    		break;
    	case "user_gender":
   			userGender = "��";
    		break;
    	case "user_age":
    		userAge = Integer.parseInt(value);
    		break;
    	case "user_address":
    		userAddress = value;
    		break;
    	case "user_skin_attr":
    		userSkinAttr = value;
    		break;
    	case "user_skin_problem":
    		userSkinProblem = value;
    		break;
    	case "user_present_product":
    		userPresentProduct = value;
    		break;
    	case "upload_photo_date":
    		uploadPhotoDate = DateUtil.stringToDate(value);
    		break;
    	case "user_remark":
    		userRemark = value;
    		break;
    	default:
    		break;
    	}
    }
    
    
    /**
     * ���ݲ�ͬ���ļ���׺����ʹ�ò�ͬ���ļ�·��
    */ 
    private String makeStorePath(String userID,String userInfoPath){
        //�����µı���Ŀ¼
        String dir = "";
        dir = userInfoPath + "/" + userID + "/previous_photo/";//	upload/userID/previous_photo

        //File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
        File file = new File(dir);
        //���Ŀ¼������
        if(!file.exists()){
            //����Ŀ¼
            file.mkdirs();
        }
        return dir;
    }
    
    /**
     * �����Ƿ�Ϊ��Ƶ������Ӧ�Ķ���
     */
    private void setUserInfoObj() throws ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

       	UserInfo userInfo = new UserInfo();
       	//����ȡ������Ϣ����UserInfo��
       	userInfo.setUserID(userName + "_" + userPhone);
       	userInfo.setUserName(userName);
       	userInfo.setUserGender(userGender);
       	//�����Ա�
       	userGender = "Ů";
       	userInfo.setUserPhone(userPhone);
       	userInfo.setRemark(userRemark);
       	userInfo.setUserAddress(userAddress);
       	userInfo.setUserAge(userAge);
       	userInfo.setUserPresentProduct(userPresentProduct);
       	userInfo.setUserSkinAttr(userSkinAttr);
       	userInfo.setUserSkinProblem(userSkinProblem);
       	//�������ݿ�
       	insertDB.insertUserInfo(userInfo);
    }
    
    /**
     * �����ϴ�ͼƬ�Ķ���
     */
    private void setPreviousPhoto(String uploadFileName, String storagePath) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	//����ͼƬ����
    	PreviousPhoto previousPhoto = new PreviousPhoto();
    	//�ܹ���ȡ������Ϣ����PreviousPhoto
    	previousPhoto.setUserID(userID);
    	previousPhoto.setPreviousPhotoID(userID + "_" + DateUtil.dateToString(uploadPhotoDate) + "_" + uploadFileName);
    	previousPhoto.setDate(uploadPhotoDate);
    	previousPhoto.setFileName(uploadFileName);
    	previousPhoto.setFilePath(storagePath + uploadFileName);
    	//�������Ķ���ŵ�Array List��
    	previousPhotos.add(previousPhoto);
    }

    /**
     *  doPost����
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}