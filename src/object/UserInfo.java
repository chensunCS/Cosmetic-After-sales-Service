package object;

import java.util.ArrayList;

public class UserInfo {
	
	private String userID = "";		//用户ID
	private String userName = "";	//用户姓名
	private String userPhone = "";	//用户电话
	private String userGender = "";	//用户性别
	private int userAge = 10;		//用户年龄
	private String userAddress = "";//用户住址
	private String userSkinAttr = "";//用户目前皮肤属性
	private String userSkinProblem = "";//用户目前皮肤问题
	private String userPresentProduct = "";//用户目前使用的产品
	private String remark = "";		//用户备注
	
	private ArrayList<PreviousPhoto> previousPhotos = null;//用户注册的照片
	private ArrayList<PresentPhoto> presentPhotos = null;	//用户提交的图片
	private ArrayList<ArrayList<PresentPhoto>> presentPhotoSet = null;//用户提交的图集
	
	
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
