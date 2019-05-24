package mybatis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 17:13
 * 已经解析完毕的XML生成的类
 */
public class UserMapperXML {
    public static final String namespace = "mybatis";
    private static Map<String, String> methodSqlMap = new HashMap<>();
    static {
        methodSqlMap.put("selectUser","select * from student where id=%s");
    }
    public static String getMethodSql(String method){
        return methodSqlMap.get(method);
    }
}
