package util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class DownloadUtil {
    public static URLResult testURL(String url){
        URLResult result = new URLResult();
        URL temp_url;
        try {
            temp_url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) temp_url.openConnection();
            if (connection.getResponseCode()>400) {
                System.out.println("resource: "+temp_url+" server response error");
                result.resultType = -1;
                result.url = null;
                return result;
            }else {
                System.out.println("resource: "+temp_url+"  is valid");
                result.url = temp_url;
                result.resultType = 1;
                result.fileLength = connection.getContentLength();
                if (result.fileLength == -1) {
                    System.out.println("can not get file length: "+temp_url);
                    result.resultType = 0;
                }else {
                    result.resultType = 1;
                    System.out.println(result.fileLength+" in util");
                    System.out.println("file length : "+Math.round(result.fileLength/1024*100)/100.0+"k");
                }
                result.fileName = connection.getURL().getFile();
                result.http_MIME = getMIME(connection);
                return result;
            }
        } catch (MalformedURLException e) {
            System.out.println("format error!!: "+url);
            result.url = null;
            result.resultType = -2;
            return result;
        } catch (IOException e) {
            System.out.println("connection error: "+url);
            result.url = null;
            result.resultType = -3;
            return result;
        }
    }
    
    private static HashMap<String, String> getMIME(HttpURLConnection http){
        HashMap<String,String> http_MIME = new HashMap<String,String>();
        for (int i = 0; ; i++) {
            String mine = http.getHeaderField(i);
            if (mine == null) 
                break;
            http_MIME.put(http.getHeaderFieldKey(i),http.getHeaderField(i));
        }
        return http_MIME;
    }
    
    
}
