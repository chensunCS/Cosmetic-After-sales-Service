package util;

public class Source {
	
	//����Ա��ID������
	public static String adminID = "fangwei2016111";
	public static String adminPassword = "Qq680321";
	
	//�ϴ��ļ���·��
	public static String uploadPath = "CosmeticRecord/WebContent/User_info";
	//���ݿ���û���
	public static String dbUserName = "root";
	//���ݿ������
	public static String password = "123456";
	//����excel�ļ���·��
	public static String excelPath = "C:/eclipse workspace/CosmeticRecord/WebContent/Excel_file";
	//Bucket���ڵ�����
	public static String domainPath = "http://cosmetic-record.oss-cn-shanghai.aliyuncs.com";
	
	//SQL�Ļ�����Դ
	//���ݿ�ı�
	public static String userInfoTable = "user_info";
	public static String presentInfoTable = "present_info";
	public static String previousPhotoTable = "previous_photo";
	public static String presentPhotoTable = "present_photo";
	//�Զ���ı�
	public static String registerInfoTable = "register_info";
	public static String feedBackInfoTable = "feed_back_info";
	//ԭ����ı�ͷ
	public static String[] userInfoTitle = {"�û�ID", "�û���", "�û��绰", "�û��Ա�", "�û�����", "�û���ַ", "�û�Ƥ������", "�û�Ƥ��������", "�û�Ŀǰʹ�õĲ�Ʒ", "��ע"};
	public static String[] presentInfoTitle = {"�û�ID", "����������¼��ID", "�û���ʹ������", "�û�Ŀǰʹ�õĲ�Ʒ", "ʹ�ò�Ʒ��ĸо�", "ʹ���в���������", "��ע"};
	public static String[] previousPhotoTitle = {"�û�ID", "ע��ʱ�ύͼƬ��ID", "ע������", "ͼƬ�ļ���", "ͼƬ�ļ�·��"};
	public static String[] presentPhotoTitle = {"�û�ID", "��д����ʱ�ύͼƬ��ID", "�ϴ�����", "ͼƬ�ļ���", "ͼƬ�ļ�·��"};
	//�Զ�����ı�ͷ
	public static String[] registerInfoTitle = {
		"�û�ID", "�û���", "�û��绰", "�û��Ա�", "�û�����", "�û���ַ", "�û�Ƥ������", "�û�Ƥ��������", "�û�Ŀǰʹ�õĲ�Ʒ", "��ע"
		, "ע��ʱ�ύͼƬ��ID", "ע������", "ͼƬ�ļ���", "ͼƬ�ļ�·��"
	};
	public static String[] feedbackInfoTitle = {
		"�û�ID", "����������¼��ID", "�û���ʹ������", "�û�Ŀǰʹ�õĲ�Ʒ", "ʹ�ò�Ʒ��ĸо�", "ʹ���в���������", "��ע"
		,"�û�ID", "��д����ʱ�ύͼƬ��ID", "�ϴ�����", "ͼƬ�ļ���", "ͼƬ�ļ�·��"
	};
	//������ѯ���
	public static String queryUserInfo = "select * from user_info";
	public static String queryPresentInfo = "select * from present_info";
	public static String queryPreviousPhoto = "select * from previous_photo";
	public static String queryPresentPhoto = "select * from present_photo";
	//�����ѯ���
	public static String queryRegisterInfo = "select * from user_info join previous_photo as register_info using (user_id)";
	public static String queryFeedBackInfo = "select * from present_info join present_photo as feed_back_info using (user_id)";
}
