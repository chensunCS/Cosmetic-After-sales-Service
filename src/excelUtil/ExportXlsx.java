package excelUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.dsig.XMLObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.ALiYunUtil;
import util.DateUtil;
import util.JdbcUtils_JNDI;
import util.Source;

public class ExportXlsx {
	
    private Connection conn = null;
    private DatabaseMetaData databaseMetaData = null;
    private ResultSetMetaData rsmd = null;
    private ResultSet tableRs = null;
    private ResultSet rowRs = null;
    private Statement st = null;
    private List<String> tableList = null;
    //定义Excel资源
    private Workbook book = null;
    private Sheet sheet = null;
	
    public ExportXlsx() throws IOException {

    }
    
    //根据数据库中的全部表格导出到Excel文件
    public void exportOriginTables(String databaseName,String filename) throws SQLException, IOException {

        //获得连接
        conn = JdbcUtils_JNDI.getConnection();
        //获取数据库元数据
        databaseMetaData=conn.getMetaData();
        //获取全部的表格
        tableRs = databaseMetaData.getTables("cosmeticDB", "cosmeticDB", "", new String[]{"TABLE"});
        //新建Array List列表
        tableList = new ArrayList<String>();
        //将表的名字添加到列表中
        while(tableRs.next()){
            tableList.add(tableRs.getString("TABLE_NAME"));
        }
        //创建语句statement
        st=conn.createStatement();
        //创建workbook对象
        book=new XSSFWorkbook();
        //遍历列表中的表名
        for(String tbName:tableList){
        	//查询每个表的全部数据
            String sql="select * from "+databaseName+"."+tbName;
            //获取该表的全部数据结果
            rowRs = st.executeQuery(sql);
            //获取结果的元数据
            rsmd = rowRs.getMetaData();
            //获取列的数目
            int coloums=rsmd.getColumnCount();
            //创建一个表格
            sheet =book.createSheet(tbName);
            //定义行的数量
            int rowNum=0;
            //创建行
            Row row=sheet.createRow(rowNum++);
            //遍历列，将每个cell填好
            for(int i=0;i<coloums;i++){
                Cell cell=row.createCell(i);
                cell.setCellValue(rsmd.getColumnName(i+1));
            }
            //遍历表格
            while(rowRs.next()){
            	//创建行
                Row row2=sheet.createRow(rowNum++);
                //填好cell
                for(int i=0;i<coloums;i++){
                    Cell cell2=row2.createCell(i);
                    cell2.setCellValue(rowRs.getString(i+1));
                }
            }

        }

        try {
        	//将数据写出到文件中
            book.write(new FileOutputStream(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
	        //释放资源
	        JdbcUtils_JNDI.release(conn, st, rowRs);
	        JdbcUtils_JNDI.release(conn, st, tableRs);
	        book.close();
        }
    }
    
    //将自定义表格导出到表格中
    public void exportCustomTables(String sql, String tableName, String[] tableTitle, String excelFilePath) throws SQLException, IOException {
        //获得连接
    	conn = JdbcUtils_JNDI.getConnection();
        //获取数据库元数据
        databaseMetaData=conn.getMetaData();
        //创建语句statement
        Statement customSt = conn.createStatement();
        //创建workbook对象
        Workbook book=new XSSFWorkbook();
        //获取该表的全部数据结果
        rowRs = customSt.executeQuery(sql);
        //获取结果的元数据
        ResultSetMetaData rsmd = rowRs.getMetaData();
        //获取列的数目
        int coloums=rsmd.getColumnCount();
        //创建一个表格
        Sheet sheet =book.createSheet(tableName);
        //定义行的数量
        int rowNum=0;
        //创建行
        Row row=sheet.createRow(rowNum++);
        //遍历列，将表头的cell填好
        for(int i = 0; i < coloums; i++){
            Cell cell=row.createCell(i);
            cell.setCellValue(tableTitle[i]);
        }
        //遍历表格，将剩下的格子（表体）填好
        while(rowRs.next()){
          	//创建行
            Row row2=sheet.createRow(rowNum++);
            //填好cell
            for(int i=0;i<coloums;i++){
            	//创建单元格
	            Cell cell2=row2.createCell(i);
	            //设置单元格的值
	            cell2.setCellValue(rowRs.getString(i+1));
	            //设置单元格的格式为字符串
	            cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
            }
        }


        try {
        	//将数据写出到文件中
            book.write(new FileOutputStream(excelFilePath));
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
	        //释放资源
	        JdbcUtils_JNDI.release(conn, st, rowRs);
	        book.close();
        }
    }
}