package object;

import java.util.Date;

public class PreviousPhoto {
	
	private String userID = "";		//用户ID
	private String previousPhotoID = "";//当前照片信息的ID
	private Date date = new Date();//文件上传的日期
	private String fileName = "";//文件名
	private String filePath = "";//文件路径
	
	//setter
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setPreviousPhotoID(String previousPhotoID) {
		this.previousPhotoID = previousPhotoID;
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
	public String getPreviousPhotoID() {
		return this.previousPhotoID;
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
