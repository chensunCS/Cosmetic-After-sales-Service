package test;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This sample demonstrates how to post object under specfied bucket from Aliyun
 * OSS using the OSS SDK for Java.
 */
public class PostObjectSample {

    // 闇�瑕佷笂浼犵殑鏈湴鏂囦欢锛岀‘淇濊鏂囦欢瀛樺湪
    private String localFilePath = "<localFile>";
    // OSS鍩熷悕锛屽http://oss-cn-hangzhou.aliyuncs.com
    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    // AccessKey璇风櫥褰昲ttps://ak-console.aliyun.com/#/鏌ョ湅
    private String accessKeyId = "<accessKeyId>";
    private String accessKeySecret = "<accessKeySecret>";
    // 浣犱箣鍓嶅垱寤虹殑bucket锛岀‘淇濊繖涓猙ucket宸茬粡鍒涘缓
    private String bucketName = "<bucketName>";
    // 涓婁紶鏂囦欢鍚庣殑object鍚嶇О
    private String key = "yourKey";

    
    private void PostObject() throws Exception {
        // 鎻愪氦琛ㄥ崟鐨刄RL涓篵ucket鍩熷悕
        String urlStr = endpoint.replace("http://", "http://" + bucketName+ ".");
        // 琛ㄥ崟鍩�
        Map<String, String> textMap = new LinkedHashMap<String, String>();
        
        // key
        textMap.put("key", this.key);
        // Content-Disposition
        textMap.put("Content-Disposition", "attachment;filename="
                + localFilePath);
        // OSSAccessKeyId
        textMap.put("OSSAccessKeyId", accessKeyId);
        // policy
        String policy = "{\"expiration\": \"2120-01-01T12:00:00.000Z\",\"conditions\": [[\"content-length-range\", 0, 104857600]]}";
        String encodePolicy = new String(Base64.encodeBase64(policy.getBytes()));
        textMap.put("policy", encodePolicy);
        // Signature
        String signaturecom = com.aliyun.oss.common.auth.ServiceSignature
                .create().computeSignature(accessKeySecret, encodePolicy);
        textMap.put("Signature", signaturecom);

        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("file", localFilePath);

        String ret = formUpload(urlStr, textMap, fileMap);
        
        System.out.println("Post Object [" + this.key + "] to bucket [" + bucketName + "]");
        System.out.println("post reponse:" + ret);
    }

    private static String formUpload(String urlStr, Map<String, String> textMap, 
            Map<String, String> fileMap) throws Exception {
        String res = "";
        HttpURLConnection conn = null;
        String boundary = "9431149156168";
        
        try {
        	//设置URL
            URL url = new URL(urlStr);
            //设置URL里面的参数，并获取URL的连接
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", 
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            //获取连接的输出流
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                //获取textMap的迭代器
                Iterator<Entry<String, String>> iter = textMap.entrySet().iterator();
                int i = 0;
                
                //遍历textMap
                while (iter.hasNext()) {
                	//获取每一个textMap的每一个键值对
                    Entry<String, String> entry = iter.next();
                    //获取键
                    String inputName = entry.getKey();
                    //获取值
                    String inputValue = entry.getValue();
                    
                    //判断值是否为空
                    if (inputValue == null) {
                        continue;
                    }
                    
                    if (i == 0) {
                        strBuf.append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    } else {
                        strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    }

                    i++;
                }
                
                //最终将请求发送到服务器上
                out.write(strBuf.toString().getBytes());
            }

            // file
            if (fileMap != null) {
            	//获取文件的fileMap
                Iterator<Entry<String, String>> iter = fileMap.entrySet().iterator();
                
                //遍历fileMap
                while (iter.hasNext()) {
                	//获取每一个键值对
                    Entry<String, String> entry = iter.next();
                    //获取键
                    String inputName = (String) entry.getKey();
                    //获取值
                    String inputValue = (String) entry.getValue();
                    
                    //如果值为空
                    if (inputValue == null) {
                        continue;
                    }
                    
                    //根据值创建一个文件
                    File file = new File(inputValue);
                    String filename = file.getName();
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    if (contentType == null || contentType.equals("")) {
                        contentType = "application/octet-stream";
                    }

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(boundary)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type: " + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
                
                StringBuffer strBuf = new StringBuffer();
                out.write(strBuf.toString().getBytes());
            }

            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 璇诲彇杩斿洖鏁版嵁
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.err.println("Send post request exception: " + e);
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        
        return res;
    }

    public static void main(String[] args) throws Exception {
        PostObjectSample ossPostObject = new PostObjectSample();
        ossPostObject.PostObject();
    }

}
