package dynamicProxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 18:13
 */
public class MyProxy {
    private static final String RT = "\r";

    public static Object newProxyInstance(MyClassLoader loader, Class<?> interfaces, InvocationHandler handler) {
        if (handler == null) {
            throw new NullPointerException();
        }
        Method[] methods = interfaces.getMethods();
        StringBuffer proxyClassString = new StringBuffer();
        proxyClassString
                .append("package").append(loader.getProxyClassPackage()).append(";").append(RT)
                .append("import java.lang.reflect.Method;").append(RT)
                .append("public class $MyProxy0 implements").append(interfaces.getName()).append("{").append(RT)
                .append("InvocationHandler h;").append(RT)
                .append("public $MyProxy0(InvocationHandler h{").append(RT)
                .append("this.h=h;}").append(RT)
                .append(getMethodString(methods, interfaces)).append("}");
        String filename = loader.getDir() + File.separator + "$MyProxy0.java";
        File myProxyFile = new File(filename);
        compile(proxyClassString, myProxyFile);
        try {
            Class $myProxy0 = loader.findClass("$MyProxy0");
            Constructor constructor = $myProxy0.getConstructor(InvocationHandler.class);
            Object obj = constructor.newInstance(handler);
            return obj;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getMethodString(Method[] methods, Class<?> interfaces) {
        StringBuffer methodStringBuffer = new StringBuffer();
        for (Method method : methods) {
            methodStringBuffer
                    .append("public void").append(method.getName())
                    .append("()").append("throws Throwable{")
                    .append("Method method1=").append(interfaces.getName())
                    .append(".class.getMethod(\"").append(method.getName())
                    .append("\", new Class[]{});")
                    .append("this.h.invoke(this,method1,null);}").append(RT);
        }
        return methodStringBuffer.toString();
    }

    private static void compile(StringBuffer proxyClassString, File myProxyFile) {
        FileCopyUtils.byteArrayToFile(proxyClassString.toString().getBytes(), myProxyFile);
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable javaFileObjects = standardJavaFileManager.getJavaFileObjects(myProxyFile);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardJavaFileManager, null, null, null, javaFileObjects);
        task.call();
        try {
            standardJavaFileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
