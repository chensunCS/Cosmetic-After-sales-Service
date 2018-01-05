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
		
    	//创建客户端
    	OSSClient client = new OSSClient(internalEndPoint, accessKeyId, accessKeySecret);
    	
        try {
        	//显示上传的提示信息
            System.out.println("创建上传文件的请求");
            
            //创建上传文件的请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, new File("D:/header.jpg"));
            
            System.out.println("完成上传文件请求的创建");
            
            //开始上传文件
            client.putObject(putObjectRequest);
            
            System.out.println("上传文件已经完成");
            
        	//设置上传的文件为可读
        	client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

        } catch (OSSException oe) {
        } catch (ClientException ce) {
        } finally {
        	//关闭客户端
        	client.shutdown();
        }
	}
}
