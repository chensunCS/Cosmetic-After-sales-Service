package object;

import java.util.Date;

public class PresentInfo {
	
	private String userID = "";		  //�û�ID
	private String presentInfoID = "";//��ǰ��Ϣ��ID
	private Date useDate = new Date();//ʹ�ò�Ʒ������
	private String useProduct = "";//ʹ�ò�Ʒ
	private String useFeeling = "";//ʹ�ú�ĸо�
	private String useProblem = "";//ʹ���е�����
	private String remark = "";	//�û��ı�ע
	
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
