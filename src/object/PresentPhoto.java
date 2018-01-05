package object;

import java.util.Date;

public class PresentPhoto {
	
	private String userID = "";		//用户ID
	private String presentPhotoID = "";//图片ID
	private Date date = new Date();//上传图片日期
	private String fileName = "";//文件名
	private String filePath = "";//文件路径
	
	//setter
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setPresentPhotoID(String presentPhotoID) {
		this.presentPhotoID = presentPhotoID;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	//getter
	public String getUserID() {
		return this.userID;
	}
	public String getPresentPhotoID() {
		return this.presentPhotoID;
	}
	public Date getDate() {
		return this.date;
	}
	public String getFileName() {
		return this.fileName;
	}
	public String getFilePath() {
		return this.filePath;
	}
}