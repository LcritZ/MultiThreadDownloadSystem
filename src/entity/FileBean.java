package entity;

import util.DownloadUtil;
import util.URLResult;

/**
 * Created by Lcrit_Z on 2017/6/4.
 */
public class FileBean {
    private String fileName;

    private String downloadURL;
    
    private URLResult result;
    
    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }
    
    public URLResult getResult() {
        return result;
    }

    public void setResult(URLResult result) {
        this.result = result;
    }

    private long size;
    
    private String type;
    
    private int threadNum;
    
    private boolean isValidate;
    
    private long preSize;
    
    private long downLength;
    
    private float downSpeed;
    
    private long[][] downPos;
    
    private boolean isFinished;
    
    private boolean isError;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
        
    public long getSize() {
        //System.out.println("size of file: " + size);
        return size;
    }

    public void setSize() {
        this.size = result.fileLength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public boolean isValidate() {
        return isValidate;
    }

    public void setValidate(boolean validate) {
        isValidate = validate;
    }

    public long getPreSize() {
        return preSize;
    }

    public void setPreSize(long preSize) {
        this.preSize = preSize;
    }

    public long getDownLength() {
        return downLength;
    }

    public void setDownLength(long downLength) {
            this.downLength += downLength;
    }

    public float getDownSpeed() {
        return downSpeed;
    }

    public void setDownSpeed() {
        downSpeed = (downLength - preSize)/1024.0f;
        preSize = downLength;
        downSpeed = Math.round(downSpeed*100)/100.0f;
    }

    public long[][] getDownPos() {
        return downPos;
    }

    public void setDownPos() {
        this.downPos = new long[threadNum][2];
        long len = result.fileLength;
        long block = (len/threadNum+1);
        for (int i = 0; i< threadNum; i++) {
            len = len-block;
            downPos[i][0] = i*block;
            downPos[i][1] = block;
            if (len < 0) {
                downPos[i][1] = block+len;
            }
        }
        
    }

    public boolean isFinished() {
        if ( downLength >= size ) {
            isFinished = true;
            return true;
        }
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
    
    public int getDownLoadProgress(){
        return Math.round(100.0f*downLength/getSize());
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "fileName='" + fileName + '\'' +
                '}';
    }
}
