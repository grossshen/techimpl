package dynamicProxy;

import dynamicProxy.trial.Man;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 17:42
 */
@AllArgsConstructor
public class InvocationHandlerImpl implements InvocationHandler {
    private Man man;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(man, null);
        after();
        return invoke;
    }

    private void before() {
        System.out.println("运行前");
    }

    private void after() {
        System.out.println("运行后");
    }

}
