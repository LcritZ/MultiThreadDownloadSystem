package ThreadPool;

import entity.DownLoadFile;
import javafx.beans.property.SetProperty;

/**
 * Created by Lcrit_Z on 2017/6/4.
 */
public class ThreadDispatch {
    public DownLoadFile downFile;
    private DownLoadThread[] pool;

    public ThreadDispatch(DownLoadFile downFile) {
        this.downFile = downFile;
        startDownLoad();
    }
    
    private void startDownLoad() {
        this.pool = new DownLoadThread[downFile.getThreadNum()];
        for (int i = 0; i <pool.length; i ++){
            this.pool[i] = new DownLoadThread(i,downFile);
            this.pool[i].setPriority(i+1);
            this.pool[i].start();
        }
    }
    
    public void multiDown() {
        new ListenThread(downFile,pool);
    }
}
