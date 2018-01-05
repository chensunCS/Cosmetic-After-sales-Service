package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.UserInfo;
import database.SelectDB;
import database.InsertDB;


@WebServlet("/servlet/LoginServlet")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		System.out.println("aa"+"bb");
		//获取表单中填写的用户ID和密码
		String userName = request.getParameter("user_name");
		String userPhone = request.getParameter("user_phone");
		System.out.println("输入用户名是：" + userName);
		System.out.println("输入的电话是：" + userPhone);
		//解决中文乱码
		response.setContentType("text/xml;charset=utf-8");
		//获取到输出html信息的写者
		PrintWriter out = response.getWriter();
		 
		//判断输入的用户名和电话是否合法
		try {
			if(isUserExist(userName, userPhone)) {
				  System.out.println("登录成功！");
				  //设置用户ID的值到session中
				  request.getSession().setAttribute("user_name", userName);
				  request.getSession().setAttribute("user_phone", userPhone);
				  System.out.println("欢迎回来，" + request.getSession().getAttribute("user_name"));
				  //修改内容
				  out.write(userName);
				  out.flush();
				  return;
			  }
			  else {
				  System.out.println("用户名或密码错误，登录失败！");
				  out.write("error");
				  out.flush();
				  return;
			  }
		 } catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	private boolean isUserExist(String userName, String userPhone) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//定义用户的列表
		ArrayList<UserInfo> userInfos = null;
		//定义选择的SQL语句
		String checkExistSQL = "select * from user_info where user_name = '" + userName + "' and user_phone = '" + userPhone + "'";
		//获取用户列表
		userInfos = (new SelectDB()).selectUserInfo(checkExistSQL);
		//获取用户数量
		if(userInfos.size() > 0) {
			//用户存在
			return true;
		}
		else {
			//用户不存在
			return false;
		}
	}
}
