package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import excelUtil.ExportXlsx;
import util.ALiYunUtil;
import util.DBUtil;
import util.DateUtil;
import util.Source;

@WebServlet("/servlet/ExportExcelServlet")
public class ExportExcelServlet extends HttpServlet {
	
	//���尢���Ʒ������
	private ALiYunUtil aLiYunUtil = new ALiYunUtil();
	//����Excel�ļ���������
	private FileInputStream excelFileInputStream = null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�����������
		response.setContentType("text/xml;charset=utf-8");
		//��ȡ�����html��Ϣ��д��
		PrintWriter out = response.getWriter();
		
		//��ȡ�����Ͳ�ѯ���
		String tableName = request.getParameter("table_name");
		String sqlQuery = request.getParameter("sql_query");
		//���ݱ�����ƥ����صı�ͷ
		String [] tableTitle = DBUtil.matchTableTitle(tableName);
		
    	//��ȡ���������
    	Date today = new Date();
    	//������ת�����ַ���
    	String todayDate = DateUtil.dateToString(today);
    	//����Excel�ļ�·��
    	String excelFileName = tableName + "_" + todayDate + ".xlsx";
    	String excelFilePath = Source.excelPath + "/" + excelFileName;
    	//����Excel�ļ����ϴ�·��
    	String uploadExcelPath = "CosmeticRecord/WebContent/Excel_file/" + excelFileName;
    	//����Excel��URL
    	String excelFileURL = Source.domainPath + "/CosmeticRecord/WebContent/Excel_file/" + excelFileName;
    	
    	//�½�����Excel�ļ��Ķ���
    	ExportXlsx exportXlsx = new ExportXlsx();
    	
    	try {
    		//��������������Excel�ļ�
			exportXlsx.exportCustomTables(sqlQuery, tableName, tableTitle, excelFilePath);
			
            //��ȡExcel�ļ���inputstream
            excelFileInputStream = new FileInputStream(excelFilePath);
            //���ļ��ϴ�����������
            aLiYunUtil.uploadFile(excelFileInputStream, uploadExcelPath);
            
            System.out.println(excelFileURL);
			
	    	//�ɹ������󷵻سɹ�����Ϣ
	    	out.write("success&" + excelFileName + "&" + excelFileURL);
	    	out.flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//����doPost����
		doPost(req, resp);
	}

}
