 package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyun.oss.model.OSSObject;

import database.DeleteDB;
import database.SelectDB;
import object.PresentInfo;
import object.PresentPhoto;
import object.PreviousPhoto;
import util.ALiYunUtil;
import util.DateUtil;
import util.Source;

@WebServlet("/servlet/RetreatServlet")
public class RetreatServlet extends HttpServlet {
	
	//创建阿里云的Util类
	private ALiYunUtil aLiYunUtil = new ALiYunUtil();
	//设置需要删除文件的ArrayList
	private ArrayList<String> deleteFileKeys = null;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//解决中文乱码
		response.setContentType("text/xml;charset=utf-8");
		//获取到输出html信息的写者
		PrintWriter out = response.getWriter();
		
		//获取用户名和电话、
		String userName = (String) request.getSession().getAttribute("user_name");
		String userPhone = (String) request.getSession().getAttribute("user_phone");
		//获取今天的日期
		Date today = new Date();
		//格式化今天的日期
		String todayDate = DateUtil.dateToString(today);
		
		//判断今天是否上传
		//合并三个变量变成主键
		String presentInfoKey = userName + "_" + userPhone + "_" + todayDate;
		//定义查询语句
		String isRecordSQL = "select * from present_info where present_info_id = '" + presentInfoKey + "'";
		//定义查询的对象
		SelectDB selectDB = new SelectDB();

		try {
			//开始查询，并或的Array List
			ArrayList<PresentInfo> presentInfos = selectDB.selectPresentInfo(isRecordSQL);
			//如果列表的数量是0，则直接返回，并提示今天没有上传
			if(presentInfos.size() == 0) {
				//返回的内容是今天没有提交
				out.write("not submit");
				out.flush();
				return ;
			}
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		/*下面为监测已经提交表单的处理*/
		
		//定义删除数据的对象
		DeleteDB deleteDB = new DeleteDB();
		//定义删除present_info的语句
		String delPresentInfoSQL = "delete from present_info where present_info_id ='" + presentInfoKey + "'";
		
		try {
			//删除present_info的数据
			deleteDB.delete(delPresentInfoSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//获取present_photo数据
		//定义user_id
		String userID = userName + "_" + userPhone;
		//定义查询语句
		String queryPresentInfoSQL = "select * from present_photo where user_id = '" + userID + "' and date = '" + todayDate + "'";
		//定义删除数据的语句
		String delPresentPhotoSQL = "delete from present_photo where user_id = '" + userID + "' and date = '" + todayDate + "'";
				
		try {
			//获取查询到的图片信息
			ArrayList<PresentPhoto> presentPhotos = selectDB.selectPresentPhoto(queryPresentInfoSQL);
			
			//开始删除数据
			deleteDB.delete(delPresentPhotoSQL);
			
			//初始化需要删除的文件的列表
			deleteFileKeys = new ArrayList<String>();
			OSSObject object = new OSSObject();
			//根据获取到的信息删除图片文件
			for(PresentPhoto presentPhoto : presentPhotos) {
				System.out.println("删除的文件是：" + presentPhoto.getFilePath());
				//将文件的路径添加到列表中
				deleteFileKeys.add(presentPhoto.getFilePath());
			}
			//利用阿里云的对象来删除数据
			aLiYunUtil.deleteFile(deleteFileKeys);
			
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		//当全部信息都删除完成后发送成功的信息
		out.write("success");
		out.flush();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//调用doPost方法
		doPost(req, resp);
	}
	
}
