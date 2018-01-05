package util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.PutObjectRequest;

public class ALiYunUtil {
	//����OSS�ķ����
	private OSSClient client = null;
	
    private static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId = "LTAIOKIhzKGNtUev";
    private static String accessKeySecret = "o98DS392XkuW6BPZTY9Kx1iHbd3CWM";
    
    private static String bucketName = "cosmetic-record";
    
    //�ϴ��ļ��ķ������򵥵ķ�����
    public boolean uploadFile(InputStream inputStream, String key) throws IOException {
    	
    	//�����ͻ���
    	client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    	
        try {
        	//��ʾ�ϴ�����ʾ��Ϣ
            System.out.println("Uploading a new object to OSS from a file\n");
            
            //��ʼ�ϴ��ļ�
            client.putObject(bucketName, key, inputStream);
            
            System.out.println("finished put");
            
        	//�����ϴ����ļ�Ϊ�ɶ�
        	client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
            //��������쳣�������ñ���Ϊfalse
            return false;
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            //��������쳣�������ñ���Ϊfalse
            return false;
        } finally {
        	//�رտͻ���
        	client.shutdown();
        	//���������ر�
        	inputStream.close();
        }
    	
    	//����ϴ������򷵻�true
    	return true;

    }
    
    //ɾ���ļ��ķ���
    public boolean deleteFile(ArrayList<String> keys) {
    	
    	//�����ͻ���
    	client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    	
        try {
            //ɾ���ļ��������ȴ���һ��ɾ���ļ���������󣬲���key��ӵ��������ϣ����շ���ɾ���ļ�����Ľ��
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
            
            //��ɾ���ļ�����Ľ���л�ȡ�������Ϣ�б�
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            for (String object : deletedObjects) {
                System.out.println("\t" + object);
            }
            

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
            //������ܳɹ�ɾ���ļ�
            return false;
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            //������ܳɹ�ɾ���ļ�
            return false;
        } finally {
        	//�رտͻ���
        	client.shutdown();
        }
        
        //����ɹ�ɾ���ļ�
        return true;
    }
    
}
