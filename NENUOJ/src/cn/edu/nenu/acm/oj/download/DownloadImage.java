package cn.edu.nenu.acm.oj.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class DownloadImage {

    public  void down(String imgUrl,String fileURL,String indexName) {  
        try {  
            BufferedInputStream in = new BufferedInputStream(new URL(imgUrl)  
                    .openStream());  
  
            int index = imgUrl.lastIndexOf("/");  
            String sName = imgUrl.substring(index + 1, imgUrl.length());  
            System.out.println(sName + "\n" + fileURL + indexName + sName);  

            File img = new File(fileURL + indexName + sName);  

            BufferedOutputStream out = new BufferedOutputStream(  
                    new FileOutputStream(img));  
            byte[] buf = new byte[2048];  
            int length = in.read(buf);  
            while (length != -1) {  
                out.write(buf, 0, length);  
                length = in.read(buf);  
            }    
            in.close();  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
