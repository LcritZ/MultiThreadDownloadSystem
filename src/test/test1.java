package test;

/**
 * Created by Lcrit_Z on 2017/6/4.
 */
public class test1 {
    public static void main(String[] args) {
        final Object o = new Object();
        Thread t1 = new Thread(){
            @Override
            public void run() {
                synchronized (o){
                    try {
                        o.wait();
                        System.out.println("t1 wait");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        
        t1.start();
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Thread t2 = new Thread(){
            @Override
            public void run() {
                synchronized (o){                   
                    synchronized (o) {
                        o.notifyAll();
                        System.out.println("t2 notify");
                    }
                }
            }
        };

        t2.start();
    }
}
