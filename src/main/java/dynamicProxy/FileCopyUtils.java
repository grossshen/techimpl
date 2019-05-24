package dynamicProxy;

import lombok.Cleanup;

import java.io.*;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 17:50
 */
public class FileCopyUtils {
    public static byte[] fileToByteArray(File file){
        byte[] buffer=null;

        try {
            @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
            @Cleanup ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] bytes = new byte[1024];
            int n;
            while ((n=fileInputStream.read(bytes))!=-1){
                byteArrayOutputStream.write(bytes,0,n);
            }
            buffer = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void byteArrayToFile(byte[] bytes, File file) {
        @Cleanup BufferedOutputStream bufferedOutputStream = null;
        @Cleanup FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
