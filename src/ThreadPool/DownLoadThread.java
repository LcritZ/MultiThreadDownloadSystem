package ThreadPool;

import entity.DownLoadFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lcrit_Z on 2017/6/4.
 */
public class DownLoadThread extends Thread {
    
    private InputStream in;
    
    private RandomAccessFile out;
    
    private URL downURL;
    
    private long block;
    
    private boolean isFinished = false;
    
    private int threadID = -1;
    
    private boolean isError = false;
    
    private DownLoadFile downLoadFile;
    
    private long startPos;

    public DownLoadThread(int threadID, DownLoadFile downLoadFile) {
        this.block = downLoadFile.getDownPos()[threadID][1];
        this.threadID = threadID;
        this.downLoadFile = downLoadFile;
        startPos = downLoadFile.getDownPos()[threadID][0];
    } 

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isError() {
        return isError;
    }
    
    public void run(){
        HttpURLConnection connection;
        try {
            downURL = new URL(downLoadFile.getDownloadURL());
            connection = (HttpURLConnection) downURL.openConnection();
            String rangepro = "bytes="+startPos+"-";
            connection.setRequestProperty("Range",rangepro);
            in = connection.getInputStream();
            out = new RandomAccessFile(downLoadFile.getFileName(),"rw");
            
        } catch (MalformedURLException e) {
            System.out.println(" url has error type");
            System.err.println(" resource error:"+downLoadFile.getDownloadURL()
                    +" thread: "+threadID);
            isError = true;
            return;
        } catch (IOException e) {
            System.err.println(" resource error: "+downLoadFile.getDownloadURL()
                    +" thread: "+threadID);
            isError = true;
            return;
        }
        
        byte[] buffer = new byte[1024];
        int offset = 0;
        long localSize = 0;
        long step = 0;
        try {
            out.seek(startPos);
            step = (block > 1024) ? 1024 : block;
            while ((offset = in.read(buffer,0,(int)step)) > 0 && localSize <= block){
                if (downLoadFile.isError()){
                    break;
                }
                out.write(buffer,0,offset);
                localSize += offset;
                downLoadFile.getDownPos()[threadID][0] = startPos + localSize;
                downLoadFile.getDownPos()[threadID][1] = block - localSize;
                step = block - localSize;
                step = (step > 1024) ? 1024 : step;
                downLoadFile.setDownLength(offset);
                
            }
            out.close();
            in.close();
            connection.disconnect();
            if (!(downLoadFile.isError())){
                isFinished = true;
                System.out.println(" file: "+downLoadFile.getFileName()+ "   has been downloaded");
                this.interrupt();
            }
        } catch (IOException e) {
            System.out.println(" file: " + downLoadFile.getDownloadURL()+"  download error: "+threadID);
            isError = true;
            isFinished = false;
            try {
                connection.disconnect();
                in.close();
                out.close();
            } catch (IOException e1) {
            }
        }

    }
}
