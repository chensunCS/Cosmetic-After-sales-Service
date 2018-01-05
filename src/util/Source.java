package util;

public class Source {
	
	//管理员的ID和密码
	public static String adminID = "fangwei2016111";
	public static String adminPassword = "Qq680321";
	
	//上传文件的路径
	public static String uploadPath = "CosmeticRecord/WebContent/User_info";
	//数据库的用户名
	public static String dbUserName = "root";
	//数据库的密码
	public static String password = "123456";
	//下载excel文件的路径
	public static String excelPath = "C:/eclipse workspace/CosmeticRecord/WebContent/Excel_file";
	//Bucket存在的域名
	public static String domainPath = "http://cosmetic-record.oss-cn-shanghai.aliyuncs.com";
	
	//SQL的基本资源
	//数据库的表
	public static String userInfoTable = "user_info";
	public static String presentInfoTable = "present_info";
	public static String previousPhotoTable = "previous_photo";
	public static String presentPhotoTable = "present_photo";
	//自定义的表
	public static String registerInfoTable = "register_info";
	public static String feedBackInfoTable = "feed_back_info";
	//原生表的表头
	public static String[] userInfoTitle = {"用户ID", "用户名", "用户电话", "用户性别", "用户年龄", "用户地址", "用户皮肤属性", "用户皮肤的问题", "用户目前使用的产品", "备注"};
	public static String[] presentInfoTitle = {"用户ID", "该条反馈记录的ID", "用户的使用日期", "用户目前使用的产品", "使用产品后的感觉", "使用中产生的问题", "备注"};
	public static String[] previousPhotoTitle = {"用户ID", "注册时提交图片的ID", "注册日期", "图片文件名", "图片文件路径"};
	public static String[] presentPhotoTitle = {"用户ID", "填写反馈时提交图片的ID", "上传日期", "图片文件名", "图片文件路径"};
	//自定义表格的表头
	public static String[] registerInfoTitle = {
		"用户ID", "用户名", "用户电话", "用户性别", "用户年龄", "用户地址", "用户皮肤属性", "用户皮肤的问题", "用户目前使用的产品", "备注"
		, "注册时提交图片的ID", "注册日期", "图片文件名", "图片文件路径"
	};
	public static String[] feedbackInfoTitle = {
		"用户ID", "该条反馈记录的ID", "用户的使用日期", "用户目前使用的产品", "使用产品后的感觉", "使用中产生的问题", "备注"
		,"用户ID", "填写反馈时提交图片的ID", "上传日期", "图片文件名", "图片文件路径"
	};
	//基本查询语句
	public static String queryUserInfo = "select * from user_info";
	public static String queryPresentInfo = "select * from present_info";
	public static String queryPreviousPhoto = "select * from previous_photo";
	public static String queryPresentPhoto = "select * from present_photo";
	//特殊查询语句
	public static String queryRegisterInfo = "select * from user_info join previous_photo as register_info using (user_id)";
	public static String queryFeedBackInfo = "select * from present_info join present_photo as feed_back_info using (user_id)";
}
