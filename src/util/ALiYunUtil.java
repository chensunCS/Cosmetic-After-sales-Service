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
	//定义OSS的服务端
	private OSSClient client = null;
	
    private static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId = "LTAIOKIhzKGNtUev";
    private static String accessKeySecret = "o98DS392XkuW6BPZTY9Kx1iHbd3CWM";
    
    private static String bucketName = "cosmetic-record";
    
    //上传文件的方法（简单的方法）
    public boolean uploadFile(InputStream inputStream, String key) throws IOException {
    	
    	//创建客户端
    	client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    	
        try {
        	//显示上传的提示信息
            System.out.println("Uploading a new object to OSS from a file\n");
            
            //开始上传文件
            client.putObject(bucketName, key, inputStream);
            
            System.out.println("finished put");
            
        	//设置上传的文件为可读
        	client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
            //如果出现异常，则设置变量为false
            return false;
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            //如果出现异常，则设置变量为false
            return false;
        } finally {
        	//关闭客户端
        	client.shutdown();
        	//将输入流关闭
        	inputStream.close();
        }
    	
    	//如果上传正常则返回true
    	return true;

    }
    
    //删除文件的方法
    public boolean deleteFile(ArrayList<String> keys) {
    	
    	//创建客户端
    	client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    	
        try {
            //删除文件对象，首先创建一个删除文件对象的请求，并将key添加到该请求上，最终返回删除文件对象的结果
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
            
            //从删除文件对象的结果中获取对象的信息列表
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
            //如果不能成功删除文件
            return false;
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            //如果不能成功删除文件
            return false;
        } finally {
        	//关闭客户端
        	client.shutdown();
        }
        
        //如果成功删除文件
        return true;
    }
    
}
