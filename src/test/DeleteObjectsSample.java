package test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;

/**
 * This sample demonstrates how to delete objects under specfied bucket 
 * from Aliyun OSS using the OSS SDK for Java.
 */
public class DeleteObjectsSample {
    
    private static String endpoint = "*** Provide OSS endpoint ***";
    private static String accessKeyId = "*** Provide your AccessKeyId ***";
    private static String accessKeySecret = "*** Provide your AccessKeySecret ***";

    private static String bucketName = "*** Provide bucket name ***";
    
    public static void main(String[] args) throws IOException {        
        /*
         * Constructs a client instance with your account for accessing OSS
         */
    	//����һ��OSS�Ŀͻ���
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        
        try {
            //���ཫ�ļ��ϴ�����������
            final String content = "Thank you for using Aliyun Object Storage Service";
            //��ȡ����ǰ׺
            final String keyPrefix = "MyObjectKey";
            //����key�ļ���
            List<String> keys = new ArrayList<String>();
            for (int i = 0; i < 100; i++) {
                String key = keyPrefix + i;
                InputStream instream = new ByteArrayInputStream(content.getBytes());
                //��content�������ϴ�����������
                client.putObject(bucketName, key, instream);
                System.out.println("Succeed to put object " + key);
                //���ϴ��ļ���key���뵽������
                keys.add(key);
            }
            System.out.println();
            
            //���ո��ϴ����ļ�ɾ����
            System.out.println("\nDeleting all objects:");
            //ɾ���ļ��������ȴ���һ��ɾ���ļ���������󣬲���key��ӵ��������ϣ����շ���ɾ���ļ�����Ľ��
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
            
            //��ɾ���ļ�����Ľ���л�ȡ�������Ϣ�б�
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            for (String object : deletedObjects) {
                System.out.println("\t" + object);
            }
            System.out.println();

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
    }
}
