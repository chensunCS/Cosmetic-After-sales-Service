package test;

import java.io.IOException;
import java.sql.SQLException;

import excelUtil.ExportXlsx;
import util.Source;

public class ExcelTest {

	public static void main(String[] args) throws SQLException, IOException {
		String fileName = "C:/eclipse workspace/CosmeticRecord/WebContent/test.xlsx";
		String sql = "select * from user_info";
		ExportXlsx demo = new ExportXlsx();
		demo.exportCustomTables(sql, "user_info", Source.userInfoTitle, fileName);
	}

}
