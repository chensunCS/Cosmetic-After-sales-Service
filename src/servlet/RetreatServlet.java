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
	
	//���������Ƶ�Util��
	private ALiYunUtil aLiYunUtil = new ALiYunUtil();
	//������Ҫɾ���ļ���ArrayList
	private ArrayList<String> deleteFileKeys = null;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//�����������
		response.setContentType("text/xml;charset=utf-8");
		//��ȡ�����html��Ϣ��д��
		PrintWriter out = response.getWriter();
		
		//��ȡ�û����͵绰��
		String userName = (String) request.getSession().getAttribute("user_name");
		String userPhone = (String) request.getSession().getAttribute("user_phone");
		//��ȡ���������
		Date today = new Date();
		//��ʽ�����������
		String todayDate = DateUtil.dateToString(today);
		
		//�жϽ����Ƿ��ϴ�
		//�ϲ����������������
		String presentInfoKey = userName + "_" + userPhone + "_" + todayDate;
		//�����ѯ���
		String isRecordSQL = "select * from present_info where present_info_id = '" + presentInfoKey + "'";
		//�����ѯ�Ķ���
		SelectDB selectDB = new SelectDB();

		try {
			//��ʼ��ѯ�������Array List
			ArrayList<PresentInfo> presentInfos = selectDB.selectPresentInfo(isRecordSQL);
			//����б��������0����ֱ�ӷ��أ�����ʾ����û���ϴ�
			if(presentInfos.size() == 0) {
				//���ص������ǽ���û���ύ
				out.write("not submit");
				out.flush();
				return ;
			}
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		/*����Ϊ����Ѿ��ύ���Ĵ���*/
		
		//����ɾ�����ݵĶ���
		DeleteDB deleteDB = new DeleteDB();
		//����ɾ��present_info�����
		String delPresentInfoSQL = "delete from present_info where present_info_id ='" + presentInfoKey + "'";
		
		try {
			//ɾ��present_info������
			deleteDB.delete(delPresentInfoSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//��ȡpresent_photo����
		//����user_id
		String userID = userName + "_" + userPhone;
		//�����ѯ���
		String queryPresentInfoSQL = "select * from present_photo where user_id = '" + userID + "' and date = '" + todayDate + "'";
		//����ɾ�����ݵ����
		String delPresentPhotoSQL = "delete from present_photo where user_id = '" + userID + "' and date = '" + todayDate + "'";
				
		try {
			//��ȡ��ѯ����ͼƬ��Ϣ
			ArrayList<PresentPhoto> presentPhotos = selectDB.selectPresentPhoto(queryPresentInfoSQL);
			
			//��ʼɾ������
			deleteDB.delete(delPresentPhotoSQL);
			
			//��ʼ����Ҫɾ�����ļ����б�
			deleteFileKeys = new ArrayList<String>();
			OSSObject object = new OSSObject();
			//���ݻ�ȡ������Ϣɾ��ͼƬ�ļ�
			for(PresentPhoto presentPhoto : presentPhotos) {
				System.out.println("ɾ�����ļ��ǣ�" + presentPhoto.getFilePath());
				//���ļ���·����ӵ��б���
				deleteFileKeys.add(presentPhoto.getFilePath());
			}
			//���ð����ƵĶ�����ɾ������
			aLiYunUtil.deleteFile(deleteFileKeys);
			
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		//��ȫ����Ϣ��ɾ����ɺ��ͳɹ�����Ϣ
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
