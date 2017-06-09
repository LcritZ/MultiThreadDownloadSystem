package entity;

import util.DownloadUtil;
import util.URLResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Lcrit_Z on 2017/6/4.
 */
public class DownLoadFile extends FileBean {
    
    private static DownLoadFile fileentity;
    
    private DownLoadFile(String url,String filename, int num) {
        URLResult result = DownloadUtil.testURL(url);
        this.setResult(result);
        if (result.resultType == 1 && result.fileLength>0 && setSaveFile(filename)){
            this.setDownloadURL(url);
            this.setValidate(true);
            this.setThreadNum(num);
            this.setDownPos();
            //this.setSize();
        }else {
            this.setDownloadURL(url);
            this.setValidate(false);
        }
    }
    
    
    public static DownLoadFile getFileentity(String url,String filename, int num) {
        if (fileentity == null) {
            synchronized (DownLoadFile.class) {
                fileentity =  new DownLoadFile(url,filename, num);
            }
        }
        return fileentity;
    }
    
    private boolean setSaveFile(String filename) {
        File tempFile = new File(filename);
        try {
            RandomAccessFile out = new RandomAccessFile(tempFile,"rw");
            out.setLength(getResult().fileLength);
            out.close();
            this.setFileName(filename);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    
    
    
    @Override
    public String toString() {        
        return "DownLoadFile{}"+super.toString();
    }
}
