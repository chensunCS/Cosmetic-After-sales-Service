package object;

import java.util.Date;

public class PresentInfo {
	
	private String userID = "";		  //用户ID
	private String presentInfoID = "";//当前信息的ID
	private Date useDate = new Date();//使用产品的日期
	private String useProduct = "";//使用产品
	private String useFeeling = "";//使用后的感觉
	private String useProblem = "";//使用中的问题
	private String remark = "";	//用户的备注
	
	//setter
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setPresentInfoID(String presentInfoID) {
		this.presentInfoID = presentInfoID;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public void setUseProduct(String useProduct) {
		this.useProduct = useProduct;
	}
	public void setUseFeeling(String useFeeling) {
		this.useFeeling = useFeeling;
	}
	public void setUseProblem(String useProblem) {
		this.useProblem = useProblem;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	//getter
	public String getUserID() {
		return this.userID;
	}
	public String getPresentInfoID() {
		return this.presentInfoID;
	}
	public Date getUseDate() {
		return this.useDate;
	}
	public String getUseProduct() {
		return this.useProduct;
	}
	public String getUseFeeling() {
		return this.useFeeling;
	}
	public String getUseProblem() {
		return this.useProblem;
	}
	public String getRemark() {
		return this.remark;
	}

}
