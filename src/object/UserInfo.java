package object;

import java.util.ArrayList;

public class UserInfo {
	
	private String userID = "";		//�û�ID
	private String userName = "";	//�û�����
	private String userPhone = "";	//�û��绰
	private String userGender = "";	//�û��Ա�
	private int userAge = 10;		//�û�����
	private String userAddress = "";//�û�סַ
	private String userSkinAttr = "";//�û�ĿǰƤ������
	private String userSkinProblem = "";//�û�ĿǰƤ������
	private String userPresentProduct = "";//�û�Ŀǰʹ�õĲ�Ʒ
	private String remark = "";		//�û���ע
	
	private ArrayList<PreviousPhoto> previousPhotos = null;//�û�ע�����Ƭ
	private ArrayList<PresentPhoto> presentPhotos = null;	//�û��ύ��ͼƬ
	private ArrayList<ArrayList<PresentPhoto>> presentPhotoSet = null;//�û��ύ��ͼ��
	
	
	//setter
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public void setUserSkinAttr(String userSkinAttr) {
		this.userSkinAttr = userSkinAttr;
	}
	public void setUserSkinProblem(String userSkinProblem) {
		this.userSkinProblem = userSkinProblem;
	}
	public void setUserPresentProduct(String userPresentProduct) {
		this.userPresentProduct = userPresentProduct;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setPreviousPhotos(ArrayList<PreviousPhoto> previousPhotos) {
		this.previousPhotos = previousPhotos;
	}
	public void setPresentPhotos(ArrayList<PresentPhoto> presentPhotos) {
		this.presentPhotos = presentPhotos;
	}
	public void setPresentPhotoSet(ArrayList<ArrayList<PresentPhoto>> presentPhotoSet) {
		this.presentPhotoSet = presentPhotoSet;
	}
	
	//getter
	public String getUserID() {
		return this.userID;
	}
	public String getUserName() {
		return this.userName;
	}
	public String getUserGender() {
		return this.userGender;
	}
	public String getUserPhone() {
		return this.userPhone;
	}
	public int getUserAge() {
		return this.userAge;
	}
	public String getUserAddress() {
		return this.userAddress;
	}
	public String getUserSkinAttr() {
		return this.userSkinAttr;
	}
	public String getUserSkinProblem() {
		return this.userSkinProblem;
	}
	public String getUserPresentProduct() {
		return this.userPresentProduct;
	}
	public String getRemark() {
		return this.remark;
	}
	public ArrayList<PreviousPhoto> getPreviousPhotos() {
		return this.previousPhotos;
	}
	public ArrayList<PresentPhoto> getPresentPhotos() {
		return this.presentPhotos;
	}
	public ArrayList<ArrayList<PresentPhoto>> getPresentPhotoSet() {
		return this.presentPhotoSet;
	}

}
