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
	
	//定义阿里云服务的类
	private ALiYunUtil aLiYunUtil = new ALiYunUtil();
	//定义Excel文件的输入流
	private FileInputStream excelFileInputStream = null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//解决中文乱码
		response.setContentType("text/xml;charset=utf-8");
		//获取到输出html信息的写者
		PrintWriter out = response.getWriter();
		
		//获取表名和查询语句
		String tableName = request.getParameter("table_name");
		String sqlQuery = request.getParameter("sql_query");
		//根据表名来匹配相关的表头
		String [] tableTitle = DBUtil.matchTableTitle(tableName);
		
    	//获取今天的日期
    	Date today = new Date();
    	//将日期转换成字符串
    	String todayDate = DateUtil.dateToString(today);
    	//建立Excel文件路径
    	String excelFileName = tableName + "_" + todayDate + ".xlsx";
    	String excelFilePath = Source.excelPath + "/" + excelFileName;
    	//定义Excel文件的上传路径
    	String uploadExcelPath = "CosmeticRecord/WebContent/Excel_file/" + excelFileName;
    	//定义Excel的URL
    	String excelFileURL = Source.domainPath + "/CosmeticRecord/WebContent/Excel_file/" + excelFileName;
    	
    	//新建导出Excel文件的对象
    	ExportXlsx exportXlsx = new ExportXlsx();
    	
    	try {
    		//根据需求来导出Excel文件
			exportXlsx.exportCustomTables(sqlQuery, tableName, tableTitle, excelFilePath);
			
            //获取Excel文件的inputstream
            excelFileInputStream = new FileInputStream(excelFilePath);
            //将文件上传到阿里云上
            aLiYunUtil.uploadFile(excelFileInputStream, uploadExcelPath);
            
            System.out.println(excelFileURL);
			
	    	//成功导出后返回成功的信息
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
		//调用doPost方法
		doPost(req, resp);
	}

}
