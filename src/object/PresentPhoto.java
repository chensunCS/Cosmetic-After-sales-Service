package object;

import java.util.Date;

public class PresentPhoto {
	
	private String userID = "";		//�û�ID
	private String presentPhotoID = "";//ͼƬID
	private Date date = new Date();//�ϴ�ͼƬ����
	private String fileName = "";//�ļ���
	private String filePath = "";//�ļ�·��
	
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