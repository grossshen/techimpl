package mybatis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 17:25
 */
@NoArgsConstructor
@AllArgsConstructor
public class MapperProxyHandler implements InvocationHandler {
    private MysqlSession mysqlSession;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperClass=method.getDeclaringClass().getName();
        if (UserMapperXML.namespace.equals(mapperClass)) {
            String methodName = method.getName();
            String originSql = UserMapperXML.getMethodSql(methodName);

            String formattedSql = String.format(originSql, String.valueOf(args[0]));
            return mysqlSession.selectOne(formattedSql);
        }
        return null;
    }
}
