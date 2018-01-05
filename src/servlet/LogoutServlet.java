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
		///�Ƴ�session�е�ֵ
		request.getSession().removeAttribute("user_name");
		request.getSession().removeAttribute("user_phone");
		//�����������
		response.setContentType("text/xml;charset=utf-8");
		//��ȡ�����html��Ϣ��д��
		PrintWriter out = response.getWriter();
		
		//���ؽ��
		out.write("success");
		out.flush();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//����doPost����
		doPost(req, resp);
	}
}
