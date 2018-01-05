package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.ListBucketsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectAcl;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;

/**
 * This sample demonstrates how to get started with basic requests to Aliyun OSS 
 * using the OSS SDK for Java.
 */
public class GetStartedSample {
    
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "<accessKeyId>";
    private static String accessKeySecret = "<accessKeySecret>";
    private static String bucketName = "<bucketName>";
    
    private static String key = "MyObjectKey";
    
    public static void main(String[] args) throws IOException {
        //创建oss的客户端
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        
        System.out.println("Getting Started with OSS SDK for Java\n");
        
        try {

            //确定该bucket是否存在
            if (!ossClient.doesBucketExist(bucketName)) {
                //如果不存在，则创建一个
                System.out.println("Creating bucket " + bucketName + "\n");
                ossClient.createBucket(bucketName);
                //创建bucket的请求
                CreateBucketRequest createBucketRequest= new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }

            /*
             * List the buckets in your account
             */
            System.out.println("Listing buckets");
            //创建显示bucket列表的请求
            ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
            //设置最大的数目为500个
            listBucketsRequest.setMaxKeys(500);
            //输出bucket的名字
            for (Bucket bucket : ossClient.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println();
            
            //上传一个文件到oss上
            System.out.println("Uploading a new object to OSS from a file\n");
            //首先创建文件，然后创建上传文件的请求，最后将文件上传
            ossClient.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));
            
            //判断一个文件是否在bucket中
            boolean exists = ossClient.doesObjectExist(bucketName, key);
            System.out.println("Does object " + bucketName + " exist? " + exists + "\n");
            
            /*
             * acl:控制访问列表
             */
            //设置控制访问权限为公开的
            ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
            //设置控制访问权限为默认
            ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.Default);
            
            //获取控制访问权限
            ObjectAcl objectAcl = ossClient.getObjectAcl(bucketName, key);
            System.out.println("ACL:" + objectAcl.getPermission().toString());
            
            //下载一个文件
            System.out.println("Downloading an object");
            //直接返回对象
            OSSObject object = ossClient.getObject(bucketName, key);
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            displayTextInputStream(object.getObjectContent());

            /*
             * List objects in your bucket by prefix
             */
            System.out.println("Listing objects");
            ObjectListing objectListing = ossClient.listObjects(bucketName, "My");
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
            }
            System.out.println();

            /*
             * Delete an object
             */
            System.out.println("Deleting an object\n");
            ossClient.deleteObject(bucketName, key);
            
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
            ossClient.shutdown();
        }
    }
    
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("oss-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("0123456789011234567890\n");
        writer.close();

        return file;
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
        
        reader.close();
    }

}
