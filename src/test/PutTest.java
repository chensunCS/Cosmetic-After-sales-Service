package test;

import java.io.File;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectRequest;

public class PutTest {
	
	private static String internalEndPoint = "http://oss-cn-shanghai-internal.aliyuncs.com";
//    private static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId = "LTAIOKIhzKGNtUev";
    private static String accessKeySecret = "o98DS392XkuW6BPZTY9Kx1iHbd3CWM";
    
    private static String bucketName = "cosmetic-record";
    private static String key = "test.jpg";
	
	public static void main(String[] args) {
		
    	//�����ͻ���
    	OSSClient client = new OSSClient(internalEndPoint, accessKeyId, accessKeySecret);
    	
        try {
        	//��ʾ�ϴ�����ʾ��Ϣ
            System.out.println("�����ϴ��ļ�������");
            
            //�����ϴ��ļ�������
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, new File("D:/header.jpg"));
            
            System.out.println("����ϴ��ļ�����Ĵ���");
            
            //��ʼ�ϴ��ļ�
            client.putObject(putObjectRequest);
            
            System.out.println("�ϴ��ļ��Ѿ����");
            
        	//�����ϴ����ļ�Ϊ�ɶ�
        	client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

        } catch (OSSException oe) {
        } catch (ClientException ce) {
        } finally {
        	//�رտͻ���
        	client.shutdown();
        }
	}
}
