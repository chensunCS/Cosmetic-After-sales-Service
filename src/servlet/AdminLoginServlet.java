package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyun.oss.common.comm.ServiceClient.Request;

import util.Source;

@WebServlet("/servlet/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//解决中文乱码
		response.setContentType("text/xml;charset=utf-8");
		//获取到输出html信息的写者
		PrintWriter out = response.getWriter();
		
		//获取输入的ID和密码
		String adminID = request.getParameter("admin_id");
		String adminPassword = request.getParameter("admin_password");
		
		//判断是否为空
		if(adminID == null || adminPassword == null) {
			out.write("empty");
			out.flush();
			//直接终止
			return ;
		}
		
		//对输入的ID和密码进行判断
		if(adminID.equals(Source.adminID) && adminPassword.equals(Source.adminPassword)) {
			//如果输入正确则设置Session的变量
			request.getSession().setAttribute("admin_id", adminID);
			request.getSession().setAttribute("admin_password", adminPassword);
			
			out.write("success");
			out.flush();
		}
		else {
			out.write("error");
			out.flush();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//调用doPost方法
		doPost(req, resp);
	}
}
