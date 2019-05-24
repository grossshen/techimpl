package dynamicProxy;

import java.io.File;
import java.util.Arrays;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 17:45
 */
public class MyClassLoader extends ClassLoader {
    //代理类加载路径
    private File dir;
    private String proxyClassPackage;
    public String getProxyClassPackage(){
        return proxyClassPackage;
    }
    public File getDir(){
        return dir;
    }
    public MyClassLoader(String path,String proxyClassPackage){
        this.dir = new File(path);
        this.proxyClassPackage = proxyClassPackage;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if(dir!=null){
            File classFile = new File(dir, name + ".class");
            if(classFile.exists()){
                byte[] classBytes = FileCopyUtils.fileToByteArray(classFile);
                return defineClass(proxyClassPackage + "." + name, classBytes, 0, classBytes.length);
            }
        }
        return super.findClass(name);
    }
}
