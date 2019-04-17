package dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 15:04
 */
public class ManHandler implements InvocationHandler {
    private Man man;
    public ManHandler(Man man){
        this.man=man;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(man,null);
        after();
        return null;
    }
    public void before(){
        System.out.println("荒废了一个白天，准备看会书");
    }
    public void after(){
        System.out.println("反正白天都荒废了，晚上还是玩吧，明天再学习");
    }
}
