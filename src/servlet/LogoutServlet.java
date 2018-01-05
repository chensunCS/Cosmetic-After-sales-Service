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


@WebServlet("/servlet/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		///移除session中的值
		request.getSession().removeAttribute("user_name");
		request.getSession().removeAttribute("user_phone");
		//解决中文乱码
		response.setContentType("text/xml;charset=utf-8");
		//获取到输出html信息的写者
		PrintWriter out = response.getWriter();
		
		//返回结果
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
