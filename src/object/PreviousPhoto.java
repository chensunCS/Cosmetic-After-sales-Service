package object;

import java.util.Date;

public class PreviousPhoto {
	
	private String userID = "";		//�û�ID
	private String previousPhotoID = "";//��ǰ��Ƭ��Ϣ��ID
	private Date date = new Date();//�ļ��ϴ�������
	private String fileName = "";//�ļ���
	private String filePath = "";//�ļ�·��
	
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
