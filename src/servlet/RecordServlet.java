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
* @Description: TODO(这里用一句话描述这个类的作用)
*
*/ 

@WebServlet("/servlet/RecordServlet")
public class RecordServlet extends HttpServlet {
	
	private String userName = "";
	private String userPhone = "";
	
	private String userID = "";		  //用户ID
	private String presentInfoID = "";//记录ID
	private Date useDate = new Date();//使用产品的日期
	private String useProduct = "";//使用产品
	private String useFeeling = "";//使用后的感觉
	private String useProblem = "";//使用中的问题
	private String userRemark = "";	//用户的备注
	
	private String fileID = "";//文件ID
	private Date uploadDate = new Date();//上传图片日期
	
	private ArrayList<PresentPhoto> presentPhotos = null;//定义照片的集合
	
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
                
                //初始化照片集合
                presentPhotos = new ArrayList<PresentPhoto>();
                
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
                    
                    for(FileItem item : list){
                        //如果fileitem中封装的是普通输入项的数据
                        if(item.isFormField()){
                            String name = item.getFieldName();
                            //解决普通输入项的数据的中文乱码问题
                            String value = item.getString("UTF-8");
                            //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                            System.out.println(name + "=" + value);
                            
                            //获取上传信息
                            getPresentInfo(name, value);
                            
                            //判断是否存在相同的使用日期
                            if(getUploadDateRecord(name, value) > 0) {
                            	//已经填写过该使用日期
                            	writer.write("same upload date");
                            	writer.flush();
                            	return ;
                            }
                        }else{//如果fileitem中封装的是上传文件
                            //得到上传的文件名称，
                            uploadFilePath = item.getName();
                            
                            //获取文件流
                            uploadInputStream = item.getInputStream();
                            
                            if(uploadFilePath==null || uploadFilePath.trim().equals("")){
                                continue;
                            }
                            //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                            //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                            uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf("\\")+1);
                            
                            //判断是否存在相同名字的文件
                            if(getPhotoNum(userID, DateUtil.dateToString(uploadDate), uploadFileName) > 0) {
                            	//存在相同的文件
                            	writer.write("same file@" + uploadFileName);
                            	writer.flush();
                            	return ;
                            }
                            
                            //得到上传文件的扩展名
                            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
                            //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                            System.out.println("上传的文件的扩展名是："+fileExtName);
                            
                            //得到文件的保存目录
                            String storagePath = makeStorePath(fileID, userInfoPath);
                            System.out.println("上传路径是：" + storagePath + uploadFileName);
                            
                            //上传文件到服务器上，并判断是否上传成功
                            if(!aLiYunUtil.uploadFile(uploadInputStream, storagePath + uploadFileName)) {
                            	//如果上传不成功则跳出该函数
                            	writer.write("fail");
                            	writer.flush();
                            	return ;
                            }
                            
                            //根据文件名以及文件格式创建对象
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
                
                //最终将用户信息存入到数据库中
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
                //将用户上传图片的信息存到数据库中
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
     * 获取上传的基本信息
     */
    private void getPresentInfo(String name, String value) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, ParseException {
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
    		//生成目前记录的ID
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
     * 获取上传日期的记录数量
     */
    private int getUploadDateRecord(String name, String value) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	//如果获取的是用户名，则进入判断
    	if(name.equals("upload_date")) {
    		//定义用户的数量
    		int userNum = 0;
    		//定义select语句
    		String presentInfoSQL = "select * from present_photo where date = '" + value + "' and user_id='" + userID + "'";
    		userNum = new SelectDB().selectPresentPhoto(presentInfoSQL).size();
    		//返回用户数量
    		return userNum;
    	}
    	
    	return 0;
    }
    
    /**
     * 获取上传日期中特定图片的数量
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    private int getPhotoNum(String userID, String uploadDate, String fileName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	//定义数量
    	int photoNum = 0;
    	//定义需要检查的图片ID
    	String testPresentPhotoID = userID + "_" + uploadDate + "_" + fileName;
    	//定义查询你语句
    	String presentPhotoSQL = "select * from present_photo where present_photo_id = '" + testPresentPhotoID + "'";
    	photoNum = new SelectDB().selectPresentPhoto(presentPhotoSQL).size();
    	//返回图片的数量
    	return photoNum;
    }
    
    /**
     * 根据不同的文件后缀名，使用不同的文件路径
    */ 
    private String makeStorePath(String userID, String userInfoPath){
        //构造新的保存目录
        String dir = "";
        dir = userInfoPath + "/" + userID + "/present_photo/" + DateUtil.dateToString(uploadDate) + "/";//	upload/userID/present_photo/uploadPhotoDate

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
    private void setPresentInfoObj() throws ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

       	PresentInfo presentInfo = new PresentInfo();
       	//将获取到的信息存入UserInfo中
       	presentInfo.setRemark(userRemark);
       	presentInfo.setUseDate(useDate);
       	presentInfo.setUseFeeling(useFeeling);
       	presentInfo.setUseProblem(useProblem);
       	presentInfo.setUseProduct(useProduct);
       	presentInfo.setPresentInfoID(presentInfoID);
       	presentInfo.setUserID(userID);
       	//更新数据库
       	insertDB.insertPresentInfo(presentInfo);
    }
    
    /**
     * 创建上传图片的对象
     */
    private void setPresentPhotoObj(String fileName, String filePath) {
    	//生成图片对象
    	PresentPhoto presentPhoto = new PresentPhoto();
    	//架构获取到的信息存入PreviousPhoto
    	presentPhoto.setUserID(userID);
    	presentPhoto.setFileName(fileName);
    	presentPhoto.setFilePath(filePath + fileName);
    	presentPhoto.setDate(uploadDate);
    	presentPhoto.setPresentPhotoID(userID + "_" + DateUtil.dateToString(uploadDate) + "_" + fileName);
    	//将对象添加到列表中
    	presentPhotos.add(presentPhoto);
    }

    /**
     *  doPost方法
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}