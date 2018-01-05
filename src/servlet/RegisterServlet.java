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
* @Description: TODO(这里用一句话描述这个类的作用)
*
*/ 

@WebServlet("/servlet/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	
	//用户ID
	private String userID = "";
	//文件ID
	private String fileID = "";
	//用户名
	private String userName = "";
	//用户电话
	private String userPhone = "";
	//用户性别
	private String userGender = "女";
	//用户年龄
	private int userAge = 0;
	//用户住址
	private String userAddress = "";
	//用户皮肤属性
	private String userSkinAttr = "";
	//用户皮肤的现状
	private String userSkinProblem = "";
	//用户目前所使用的产品
	private String userPresentProduct = "";
	//上传图片日期
	private Date uploadPhotoDate = new Date();
	//用户备注
	private String userRemark = "";
	//存储图片的ArrayList
	private ArrayList<PreviousPhoto> previousPhotos = null;
	//定义更新数据库的对象
	private InsertDB insertDB = new InsertDB();
	
	//保存路径
	private String userInfoPath = "";
	//需要上传文件的路径
	private String uploadFilePath = "";
	//需要上传文件的名字
	private String uploadFileName = "";
	//文件的扩展名
	private String fileExtName = "";
	//需要上传文件
	private InputStream uploadInputStream = null;
	
	//创建阿里云的上传对象
	private ALiYunUtil aLiYunUtil = new ALiYunUtil();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    
				//解决中文乱码
				response.setContentType("text/xml;charset=utf-8");
				//获取到输出html信息的写者
				PrintWriter writer = response.getWriter();
    	
                //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
                userInfoPath = Source.uploadPath;
                //输出保存的路径
                System.out.println(userInfoPath);
                
                //初始化Array List
                previousPhotos = new ArrayList<PreviousPhoto>();
                
                try{
                    //使用Apache文件上传组件处理文件上传步骤：
                    //1、创建一个DiskFileItemFactory工厂
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
                    factory.setSizeThreshold(1024*100);//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
                    //2、创建一个文件上传解析器
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    //监听文件上传进度
                    upload.setProgressListener(new ProgressListener(){
                        public void update(long pBytesRead, long pContentLength, int arg2) {
                        }
                    });
                     //解决上传文件名的中文乱码
                    upload.setHeaderEncoding("UTF-8"); 
                    //3、判断提交上来的数据是否是上传表单的数据
                    if(!ServletFileUpload.isMultipartContent(request)){
                        //按照传统方式获取数据
                        return;
                    }
                    //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是200MB
                    upload.setFileSizeMax(1024*1024*200);
                    //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为1000MB
                    upload.setSizeMax(1024*1024*1000);
                    //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
                    List<FileItem> list = upload.parseRequest(request);
                    
                    //开始遍历上传的内容
                    for(FileItem item : list){
                        //如果fileitem中封装的是普通输入项的数据
                        if(item.isFormField()){
                            String name = item.getFieldName();
                            //解决普通输入项的数据的中文乱码问题
                            String value = item.getString("UTF-8");
                            //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                            System.out.println(name + "=" + value);
                            
                            //获取上传信息
                            getRegisterInfo(name, value);
                            
                            //判断是否存在用户
                            if(getUserNum(name, value) > 0) {
                            	//如果存在则退出该上传，并返回相应的信息
                            	writer.write("occupied");
                            	writer.flush();
                            	return ;
                            }
                        }else{//如果fileitem中封装的是上传文件
                            //得到上传的文件名称，
                            uploadFilePath = item.getName();
                            
                            //获取文件流
                            uploadInputStream = item.getInputStream();
                            
                            //判断路径是否为空
                            if(uploadFilePath==null || uploadFilePath.trim().equals("")){
                                continue;
                            }
                            
                            //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                            //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                            uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf("\\")+1);
                            
                            //得到上传文件的扩展名
                            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
                            //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                            System.out.println("上传的文件的扩展名是："+fileExtName);
                            
                            //得到文件的保存目录
                            String storagePath = makeStorePath(fileID, userInfoPath);
                            System.out.println("上传路径是：" + storagePath + uploadFileName);
                            System.out.println("本地文件路径是：" + uploadFilePath);
                            
                            //上传文件到服务器上，并判断是否上传成功
                            if(!aLiYunUtil.uploadFile(uploadInputStream, storagePath + uploadFileName)) {
                            	//如果上传不成功则跳出该函数
                            	writer.write("fail");
                            	writer.flush();
                            	return ;
                            }
                            
                            //根据文件名以及文件格式创建对象，并将该对象存到数据库中
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
                
                //最终将用户信息存入到数据库中
                try {
                	//将用户的注册信息存入到数据库中
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
                //将用户上传图片的信息存到数据库中
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
     * 判断用户是否已经注册的方法
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    private int getUserNum(String name, String value) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	//如果获取的是用户名，则进入判断
    	if(name.equals("user_phone")) {
    		//定义用户的数量
    		int userNum = 0;
    		//定义select语句
    		String userInfoSQL = "select * from user_info where user_phone = '" + value + "'";
    		userNum = new SelectDB().selectUserInfo(userInfoSQL).size();
    		//返回用户数量
    		return userNum;
    	}
    	
    	return 0;
    }
    
    /**
     * 获取上传的基本信息
     */
    private void getRegisterInfo(String name, String value) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParseException {
    	//获取表单的内容
    	switch(name) {
    	case "user_name":
    		userName = value;
    		break;
    	case "user_phone":
    		userPhone = value;
    		//生成userID
    		userID = userName + "_" + userPhone;
    		//创建文件ID
    		fileID = TranslateUtil.getPingYin(userName) + "_" + userPhone;
    		break;
    	case "user_gender":
   			userGender = "男";
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
     * 根据不同的文件后缀名，使用不同的文件路径
    */ 
    private String makeStorePath(String userID,String userInfoPath){
        //构造新的保存目录
        String dir = "";
        dir = userInfoPath + "/" + userID + "/previous_photo/";//	upload/userID/previous_photo

        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if(!file.exists()){
            //创建目录
            file.mkdirs();
        }
        return dir;
    }
    
    /**
     * 根据是否为视频生成相应的对象
     */
    private void setUserInfoObj() throws ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

       	UserInfo userInfo = new UserInfo();
       	//将获取到的信息存入UserInfo中
       	userInfo.setUserID(userName + "_" + userPhone);
       	userInfo.setUserName(userName);
       	userInfo.setUserGender(userGender);
       	//重置性别
       	userGender = "女";
       	userInfo.setUserPhone(userPhone);
       	userInfo.setRemark(userRemark);
       	userInfo.setUserAddress(userAddress);
       	userInfo.setUserAge(userAge);
       	userInfo.setUserPresentProduct(userPresentProduct);
       	userInfo.setUserSkinAttr(userSkinAttr);
       	userInfo.setUserSkinProblem(userSkinProblem);
       	//更新数据库
       	insertDB.insertUserInfo(userInfo);
    }
    
    /**
     * 创建上传图片的对象
     */
    private void setPreviousPhoto(String uploadFileName, String storagePath) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	//生成图片对象
    	PreviousPhoto previousPhoto = new PreviousPhoto();
    	//架构获取到的信息存入PreviousPhoto
    	previousPhoto.setUserID(userID);
    	previousPhoto.setPreviousPhotoID(userID + "_" + DateUtil.dateToString(uploadPhotoDate) + "_" + uploadFileName);
    	previousPhoto.setDate(uploadPhotoDate);
    	previousPhoto.setFileName(uploadFileName);
    	previousPhoto.setFilePath(storagePath + uploadFileName);
    	//将新增的对象放到Array List中
    	previousPhotos.add(previousPhoto);
    }

    /**
     *  doPost方法
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}