package mybatis;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 17:12
 */
public interface UserMapper {
    User selectUser(int id);

    void insertUser(User user);
}
