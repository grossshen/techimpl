package mybatis;

import java.lang.reflect.Proxy;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 17:19
 */
public class MysqlSessionImpl implements MysqlSession {
    private Executor executor = new ExecutorImpl();
    @Override
    public <T> T selectOne(String sql) {
        return executor.query(sql);
    }

    @Override
    public <T> T getMapper(Class<T> interfaces) {
        return (T) Proxy.newProxyInstance(
                interfaces.getClassLoader(), new Class[]{interfaces}, new MapperProxyHandler(this)
        );
    }
}
