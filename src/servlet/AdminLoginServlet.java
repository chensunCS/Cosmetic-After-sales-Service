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
		//�����������
		response.setContentType("text/xml;charset=utf-8");
		//��ȡ�����html��Ϣ��д��
		PrintWriter out = response.getWriter();
		
		//��ȡ�����ID������
		String adminID = request.getParameter("admin_id");
		String adminPassword = request.getParameter("admin_password");
		
		//�ж��Ƿ�Ϊ��
		if(adminID == null || adminPassword == null) {
			out.write("empty");
			out.flush();
			//ֱ����ֹ
			return ;
		}
		
		//�������ID����������ж�
		if(adminID.equals(Source.adminID) && adminPassword.equals(Source.adminPassword)) {
			//���������ȷ������Session�ı���
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
		//����doPost����
		doPost(req, resp);
	}
}
