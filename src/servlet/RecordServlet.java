package servlet;

import java.io.File;
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

import object.PresentInfo;
import object.PresentPhoto;
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

@WebServlet("/servlet/RecordServlet")
public class RecordServlet extends HttpServlet {
	
	private String userName = "";
	private String userPhone = "";
	
	private String userID = "";		  //�û�ID
	private String presentInfoID = "";//��¼ID
	private Date useDate = new Date();//ʹ�ò�Ʒ������
	private String useProduct = "";//ʹ�ò�Ʒ
	private String useFeeling = "";//ʹ�ú�ĸо�
	private String useProblem = "";//ʹ���е�����
	private String userRemark = "";	//�û��ı�ע
	
	private String fileID = "";//�ļ�ID
	private Date uploadDate = new Date();//�ϴ�ͼƬ����
	
	private ArrayList<PresentPhoto> presentPhotos = null;//������Ƭ�ļ���
	
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
                
                //��ʼ����Ƭ����
                presentPhotos = new ArrayList<PresentPhoto>();
                
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
                    
                    for(FileItem item : list){
                        //���fileitem�з�װ������ͨ�����������
                        if(item.isFormField()){
                            String name = item.getFieldName();
                            //�����ͨ����������ݵ�������������
                            String value = item.getString("UTF-8");
                            //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                            System.out.println(name + "=" + value);
                            
                            //��ȡ�ϴ���Ϣ
                            getPresentInfo(name, value);
                            
                            //�ж��Ƿ������ͬ��ʹ������
                            if(getUploadDateRecord(name, value) > 0) {
                            	//�Ѿ���д����ʹ������
                            	writer.write("same upload date");
                            	writer.flush();
                            	return ;
                            }
                        }else{//���fileitem�з�װ�����ϴ��ļ�
                            //�õ��ϴ����ļ����ƣ�
                            uploadFilePath = item.getName();
                            
                            //��ȡ�ļ���
                            uploadInputStream = item.getInputStream();
                            
                            if(uploadFilePath==null || uploadFilePath.trim().equals("")){
                                continue;
                            }
                            //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
                            //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
                            uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf("\\")+1);
                            
                            //�ж��Ƿ������ͬ���ֵ��ļ�
                            if(getPhotoNum(userID, DateUtil.dateToString(uploadDate), uploadFileName) > 0) {
                            	//������ͬ���ļ�
                            	writer.write("same file@" + uploadFileName);
                            	writer.flush();
                            	return ;
                            }
                            
                            //�õ��ϴ��ļ�����չ��
                            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
                            //�����Ҫ�����ϴ����ļ����ͣ���ô����ͨ���ļ�����չ�����ж��ϴ����ļ������Ƿ�Ϸ�
                            System.out.println("�ϴ����ļ�����չ���ǣ�"+fileExtName);
                            
                            //�õ��ļ��ı���Ŀ¼
                            String storagePath = makeStorePath(fileID, userInfoPath);
                            System.out.println("�ϴ�·���ǣ�" + storagePath + uploadFileName);
                            
                            //�ϴ��ļ����������ϣ����ж��Ƿ��ϴ��ɹ�
                            if(!aLiYunUtil.uploadFile(uploadInputStream, storagePath + uploadFileName)) {
                            	//����ϴ����ɹ��������ú���
                            	writer.write("fail");
                            	writer.flush();
                            	return ;
                            }
                            
                            //�����ļ����Լ��ļ���ʽ��������
                            setPresentPhotoObj(uploadFileName, storagePath);
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
					setPresentInfoObj();
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | ParseException
						| SQLException e) {
					e.printStackTrace();
                    writer.write("fail");
                    writer.flush();
					return ;
				}
                //���û��ϴ�ͼƬ����Ϣ�浽���ݿ���
                for(int i = 0 ; i < presentPhotos.size() ; i++) {
                	try {
						insertDB.insertPresentPhoto(presentPhotos.get(i));
					} catch (InstantiationException | IllegalAccessException
							| ClassNotFoundException | SQLException e) {
						e.printStackTrace();
	                    writer.write("fail");
	                    writer.flush();
						return ;
					}
                }
    }
    
    /**
     * ��ȡ�ϴ��Ļ�����Ϣ
     */
    private void getPresentInfo(String name, String value) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParseException {
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
    	case "use_date":
    		useDate = DateUtil.stringToDate(value);
    		break;
    	case "use_product":
    		useProduct = value;
    		break;
    	case "use_feeling":
    		useFeeling = value;
    		break;
    	case "use_problem":
    		useProblem = value;
    		break;
    	case "upload_date":
    		uploadDate = DateUtil.stringToDate(value);
    		//����Ŀǰ��¼��ID
    		presentInfoID = userID + "_" + value;
    		break;
    	case "user_remark":
    		userRemark = value;
    		break;
    	default:
    		break;
    	}
    }
    
    /**
     * ��ȡ�ϴ����ڵļ�¼����
     */
    private int getUploadDateRecord(String name, String value) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	//�����ȡ�����û�����������ж�
    	if(name.equals("upload_date")) {
    		//�����û�������
    		int userNum = 0;
    		//����select���
    		String presentInfoSQL = "select * from present_photo where date = '" + value + "' and user_id='" + userID + "'";
    		userNum = new SelectDB().selectPresentPhoto(presentInfoSQL).size();
    		//�����û�����
    		return userNum;
    	}
    	
    	return 0;
    }
    
    /**
     * ��ȡ�ϴ��������ض�ͼƬ������
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    private int getPhotoNum(String userID, String uploadDate, String fileName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	//��������
    	int photoNum = 0;
    	//������Ҫ����ͼƬID
    	String testPresentPhotoID = userID + "_" + uploadDate + "_" + fileName;
    	//�����ѯ�����
    	String presentPhotoSQL = "select * from present_photo where present_photo_id = '" + testPresentPhotoID + "'";
    	photoNum = new SelectDB().selectPresentPhoto(presentPhotoSQL).size();
    	//����ͼƬ������
    	return photoNum;
    }
    
    /**
     * ���ݲ�ͬ���ļ���׺����ʹ�ò�ͬ���ļ�·��
    */ 
    private String makeStorePath(String userID, String userInfoPath){
        //�����µı���Ŀ¼
        String dir = "";
        dir = userInfoPath + "/" + userID + "/present_photo/" + DateUtil.dateToString(uploadDate) + "/";//	upload/userID/present_photo/uploadPhotoDate

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
    private void setPresentInfoObj() throws ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

       	PresentInfo presentInfo = new PresentInfo();
       	//����ȡ������Ϣ����UserInfo��
       	presentInfo.setRemark(userRemark);
       	presentInfo.setUseDate(useDate);
       	presentInfo.setUseFeeling(useFeeling);
       	presentInfo.setUseProblem(useProblem);
       	presentInfo.setUseProduct(useProduct);
       	presentInfo.setPresentInfoID(presentInfoID);
       	presentInfo.setUserID(userID);
       	//�������ݿ�
       	insertDB.insertPresentInfo(presentInfo);
    }
    
    /**
     * �����ϴ�ͼƬ�Ķ���
     */
    private void setPresentPhotoObj(String fileName, String filePath) {
    	//����ͼƬ����
    	PresentPhoto presentPhoto = new PresentPhoto();
    	//�ܹ���ȡ������Ϣ����PreviousPhoto
    	presentPhoto.setUserID(userID);
    	presentPhoto.setFileName(fileName);
    	presentPhoto.setFilePath(filePath + fileName);
    	presentPhoto.setDate(uploadDate);
    	presentPhoto.setPresentPhotoID(userID + "_" + DateUtil.dateToString(uploadDate) + "_" + fileName);
    	//��������ӵ��б���
    	presentPhotos.add(presentPhoto);
    }

    /**
     *  doPost����
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}