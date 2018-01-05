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
		//��ȡ������д���û�ID������
		String userName = request.getParameter("user_name");
		String userPhone = request.getParameter("user_phone");
		System.out.println("�����û����ǣ�" + userName);
		System.out.println("����ĵ绰�ǣ�" + userPhone);
		//�����������
		response.setContentType("text/xml;charset=utf-8");
		//��ȡ�����html��Ϣ��д��
		PrintWriter out = response.getWriter();
		 
		//�ж�������û����͵绰�Ƿ�Ϸ�
		try {
			if(isUserExist(userName, userPhone)) {
				  System.out.println("��¼�ɹ���");
				  //�����û�ID��ֵ��session��
				  request.getSession().setAttribute("user_name", userName);
				  request.getSession().setAttribute("user_phone", userPhone);
				  System.out.println("��ӭ������" + request.getSession().getAttribute("user_name"));
				  //�޸�����
				  out.write(userName);
				  out.flush();
				  return;
			  }
			  else {
				  System.out.println("�û�����������󣬵�¼ʧ�ܣ�");
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
		//�����û����б�
		ArrayList<UserInfo> userInfos = null;
		//����ѡ���SQL���
		String checkExistSQL = "select * from user_info where user_name = '" + userName + "' and user_phone = '" + userPhone + "'";
		//��ȡ�û��б�
		userInfos = (new SelectDB()).selectUserInfo(checkExistSQL);
		//��ȡ�û�����
		if(userInfos.size() > 0) {
			//�û�����
			return true;
		}
		else {
			//�û�������
			return false;
		}
	}
}
