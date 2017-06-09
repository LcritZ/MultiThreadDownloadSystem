package main;

import ThreadPool.ThreadDispatch;
import entity.DownLoadFile;

import java.util.Scanner;

/**
 * Created by Lcrit_Z on 2017/6/5.
 */
public class Start {
    
    public void welcome() {
        System.out.println("----welcome to multithread download system!------");
    }
    
    public int getInt(String info) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(info);
        int res = -1;
        if (scanner.hasNextInt()) {
            res = scanner.nextInt();
        }
        return res;
     }
    
    public String getString(String info) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(info);
        String res = "";
        if (scanner.hasNextLine()) {
            res = scanner.nextLine();
        }
        return res;
    }
    
    public void login() {
        welcome();
        int isLogin = getInt("please input choice: \n\t 1: download  2: exit ");
        if (isLogin == 1) {
            boolean flag = false;
            String filename = getString("please input file name； ");
            String url = "file:///" + filename;
            int threadnum = getInt("please input thread num: ");
            String destination = getString("please input destination: ");
            DownLoadFile fileentity = DownLoadFile.getFileentity("http://localhost:8080/deepservlet/download", destination, threadnum);
            if (fileentity.isValidate() == false) {
                System.out.println("resource has error: "+ fileentity.getDownloadURL());
                return;
            }
            fileentity.setSize();
            System.out.println(fileentity.getSize()+" in satrt");
            ThreadDispatch dispatch = new ThreadDispatch(fileentity);
            dispatch.multiDown();
            while (!flag) {
                flag = fileentity.isFinished();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        System.out.println(flag);
        System.out.println("download successful");
        System.exit(10);
        
        } else {
            System.exit(10);
        }
    }

    public static void main(String[] args) {
        new Start().login();
    }
    
}
