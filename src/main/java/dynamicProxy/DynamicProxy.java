package dynamicProxy;

import lombok.Cleanup;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 14:59
 *
 * 动态代理jdk实现
 */
public class DynamicProxy {
    public static void main(String[] args){
        createProxyClassFile(Man.class,"Proxy1");
        Man man = new Poorguy();
        ManHandler manHandler = new ManHandler(man);
        Man proxyMan = (Man) Proxy.newProxyInstance(man.getClass().getClassLoader(),
                new Class[]{Man.class},manHandler);
        System.out.println(proxyMan.getClass().getName());

        proxyMan.read();

        createProxyClassFile(Man.class,"Proxy0");
    }
    public static void createProxyClassFile(Class c,String name)  {
        byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{c});
        try {
            @Cleanup FileOutputStream fileOutputStream = new FileOutputStream(name+".class");
            fileOutputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
