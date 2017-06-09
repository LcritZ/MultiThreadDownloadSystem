package ThreadPool;

import entity.DownLoadFile;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lcrit_Z on 2017/6/4.
 */
public class ListenThread {
    
    private DownLoadFile downFile;
    private Timer timer;
    private boolean isFinished;
    private boolean isError;
    private DownLoadThread[] threadPool;

    public ListenThread(DownLoadFile file, DownLoadThread[] pool) {
        this.downFile = file;
        this.threadPool = pool;
        timer = new Timer();
        timer.schedule(new Mytimer(),0,1000);
    }
    final private void listenFinished(){
        boolean flag = true;
        for (int i = 0; i < threadPool.length; i++) {
            if (threadPool[i].isFinished() == false) {
                flag = false;
            }
            if (threadPool[i].isError()) {
                isError = true;
                break;
            }
        }
        isFinished = flag;
    }
    class Mytimer extends TimerTask{

        @Override
        public void run() {
            listenFinished();
            if (isFinished) {
                timer.cancel();
                downFile.setFinished(true);
                System.out.println("resource: " + downFile.getDownloadURL() + " finished at: "+downFile.getFileName());
                this.cancel();
            }else {
                if (isError){
                    timer.cancel();
                    downFile.setError(true);
                    System.out.println("resource: " + downFile.getDownloadURL() +" has error");
                }else {
                    downFile.setDownSpeed();
                    System.out.println("resource: "+downFile.getDownloadURL() + " has been download:"+downFile.getDownLength()/1024f+" M" 
                            + " download speed:  "+downFile.getDownSpeed()/1024f+" M/s  download progress: "+ downFile.getDownLoadProgress()+"%");
                    
                }
            }
        }
    }
}


