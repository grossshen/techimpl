package springMVC;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 15:48
 */
@WebServlet(name = "dispatcherServlet",urlPatterns = "/*",loadOnStartup = 1,
        initParams = {@WebInitParam(name = "base-package",value = "springMVC")})
public class DispatcherServlet extends HttpServlet {
    private String basePackage="";
    //扫描到的所有组件包路径
    private List<String> packageNames = new ArrayList<>();
    //存放类名和类实例的映射
    private Map<String, Object> instanceMap = new HashMap<>();
    //包路径和类名的映射
    private Map<String, String> nameMap = new HashMap<>();
    //地址和方法的映射
    private Map<String, Method> urlMethodMap = new HashMap<>();
    //方法和类的包路径的映射
    private Map<Method, String> methodPackageMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException{
        basePackage = config.getInitParameter("base-package");

        scanBasePackage(basePackage);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri=req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.replaceAll(contextPath, "");

        Method method = urlMethodMap.get(path);
        if (method != null) {
            String packageName = methodPackageMap.get(method);
            String controllerName = nameMap.get(packageName);

            UserController userController = (UserController) instanceMap.get(controllerName);
            method.setAccessible(true);
            try {
                method.invoke(userController);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    //扫描基包
    private void scanBasePackage(String basePackage){
        //getResource得到的是.形式，替换成/形式路径
        URL url = this.getClass().getClassLoader().getResource(basePackage.replace("\\.", "/"));
        File basePackageFile = new File(url.getPath());
        System.out.println("scan:"+basePackageFile);
        File[] chileFiles = basePackageFile.listFiles();
        for (File file : chileFiles) {
            if(file.isDirectory()){
                scanBasePackage(basePackage+"."+file.getName());
            }else if(file.isFile()){
                //去掉.class后缀
                packageNames.add(basePackage + "." + file.getName().split("\\.")[0]);
            }
        }
    }
    //实例化扫描到的组件
    private void instance(List<String> packageNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(packageNames.size()<1){
            return;
        }
        for(String string:packageNames){
            Class c = Class.forName(string);

            if(c.isAnnotationPresent(Controller.class)){
                Controller controller = (Controller) c.getAnnotation(Controller.class);
                String controllerName = controller.value();

                instanceMap.put(controllerName, c.newInstance());
                nameMap.put(string, controllerName);
                System.out.println("Controller:"+string+",value:"+controller.value());
            }else if(c.isAnnotationPresent(Service.class)){
                Service service = (Service) c.getAnnotation(Service.class);
                String serviceName=service.value();

                instanceMap.put(serviceName, c.newInstance());
                nameMap.put(string, serviceName);
                System.out.println("Service:"+string+",value:"+service.value());
            }else if(c.isAnnotationPresent(Repository.class)){
                Repository repository = (Repository) c.getAnnotation(Repository.class);
                String repositoryName = repository.value();

                instanceMap.put(repositoryName, c.newInstance());
                nameMap.put(string, repositoryName);
                System.out.println("Repository:"+string+",value:"+repository.value());
            }
        }
    }
//    依赖注入
    private void springIOC() throws ClassNotFoundException,IllegalAccessException{
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            Field[] fields=entry.getValue().getClass().getDeclaredFields();

            for(Field field:fields){
                if(field.isAnnotationPresent(Qualifier.class)){
                    String name=field.getAnnotation(Qualifier.class).value();
                    field.setAccessible(true);
                    field.set(entry.getValue(), instanceMap.get(name));
                }
            }
        }
    }
//    url映射
    private void handlerUrlMethodMap() throws ClassNotFoundException {
        if(packageNames.size()<1){
            return;
        }
        for(String string:packageNames){
            Class c = Class.forName(string);
            if(c.isAnnotationPresent(Controller.class)){
                Method[] methods = c.getMethods();
                StringBuffer baseUrl = new StringBuffer();
                if(c.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping requestMapping = (RequestMapping) c.getAnnotation(RequestMapping.class);
                    baseUrl.append(requestMapping.value());
                }
                for(Method method:methods){
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping requestMapping = (RequestMapping) method.getAnnotation(RequestMapping.class);
                        baseUrl.append(requestMapping.value());

                        urlMethodMap.put(baseUrl.toString(), method);
                        methodPackageMap.put(method,string);
                    }
                }
            }
        }
    }
}
