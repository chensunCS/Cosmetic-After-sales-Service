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
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

/**
 * This sample demonstrates how to list objects under specfied bucket 
 * from Aliyun OSS using the OSS SDK for Java.
 */
public class ListObjectsSample {
    
    private static String endpoint = "<endpoint>";
    private static String accessKeyId = "<accessKeyId>";
    private static String accessKeySecret = "<accessKeySecret>";
    private static String bucketName = "<bucketName>";
    
    public static void main(String[] args) throws IOException {        

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        
        try {
            final String content = "Hello OSS";
            final String keyPrefix = "MyObjectKey";
            
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
            }
            
            // 鍑嗗鐜锛屾彃鍏�100涓祴璇昈bject
            List<String> keys = new ArrayList<String>();
            for (int i = 0; i < 100; i++) {
                String key = keyPrefix + i;
                InputStream instream = new ByteArrayInputStream(content.getBytes());
                client.putObject(bucketName, key, instream);
                keys.add(key);
            }
            System.out.println("Put " + keys.size() + " objects completed." );
            
            ObjectListing objectListing = null;
            String nextMarker = null;
            final int maxKeys = 30;
            List<OSSObjectSummary> sums = null;
            
            // 浣跨敤榛樿鍙傛暟鑾峰彇瀛樺偍绌洪棿鐨勬枃浠跺垪琛紝榛樿鏈�澶氳繑鍥�100鏉�
            System.out.println("Default paramters:");
            objectListing = client.listObjects(bucketName);
            sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                System.out.println("\t" + s.getKey());
            }
            
            // 鎸囧畾鏈�澶ц繑鍥炴暟閲忥紝鏈�澶氫笉鑳借秴杩�1000鏉�
            System.out.println("With max keys:");
            objectListing = client.listObjects(new ListObjectsRequest(bucketName).
                    withMaxKeys(200));
            
            sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                System.out.println("\t" + s.getKey());
            }
            
            // 杩斿洖鎸囧畾鍓嶇紑鐨凮bject锛岄粯璁ゆ渶澶氳繑鍥�100鏉�
            System.out.println("With prefix:");
            objectListing = client.listObjects(new ListObjectsRequest(bucketName).withPrefix(keyPrefix));
            
            sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                System.out.println("\t" + s.getKey());
            }
            
            // 浠庢寚瀹氱殑鏌怬bject鍚庤繑鍥烇紝榛樿鏈�澶�100鏉�
            System.out.println("With marker: ");
            objectListing = client.listObjects(new ListObjectsRequest(bucketName).withMarker(keyPrefix + "11"));
            
            sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                System.out.println("\t" + s.getKey());
            }
            
            // 鍒嗛〉鑾峰彇鎵�鏈塐bject锛屾瘡椤祄axKeys鏉bject
            System.out.println("List all objects:");
            nextMarker = null;
            do {
                objectListing = client.listObjects(new ListObjectsRequest(bucketName).
                        withMarker(nextMarker).withMaxKeys(maxKeys));
                
                sums = objectListing.getObjectSummaries();
                for (OSSObjectSummary s : sums) {
                    System.out.println("\t" + s.getKey());
                }
                
                nextMarker = objectListing.getNextMarker();
                
            } while (objectListing.isTruncated());
            
            
            // 鍒嗛〉鎵�鏈夎幏鍙栦粠鐗瑰畾Object鍚庣殑鎵�鏈夌殑Object锛屾瘡椤祄axKeys鏉bject
            System.out.println("List all objects after marker:");
            nextMarker = keyPrefix + "11";
            do {
                objectListing = client.listObjects(new ListObjectsRequest(bucketName).
                        withMarker(nextMarker).withMaxKeys(maxKeys));
                
                sums = objectListing.getObjectSummaries();
                for (OSSObjectSummary s : sums) {
                    System.out.println("\t" + s.getKey());
                }
                
                nextMarker = objectListing.getNextMarker();
                
            } while (objectListing.isTruncated());
            
            // 鍒嗛〉鎵�鏈夎幏鍙栨寚瀹氬墠缂�鐨凮bject锛屾瘡椤祄axKeys鏉bject
            System.out.println("List all objects with prefix:");
            nextMarker = null;
            do {
                objectListing = client.listObjects(new ListObjectsRequest(bucketName).
                        withPrefix(keyPrefix + "1").withMarker(nextMarker).withMaxKeys(maxKeys));
                
                sums = objectListing.getObjectSummaries();
                for (OSSObjectSummary s : sums) {
                    System.out.println("\t" + s.getKey());
                }
                
                nextMarker = objectListing.getNextMarker();
                
            } while (objectListing.isTruncated());
            
            // 娓呯悊娴嬭瘯鐜锛屽垹闄ゅ垱寤虹殑Object
            System.out.println("Deleting all objects:");
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(
                    new DeleteObjectsRequest(bucketName).withKeys(keys));
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
