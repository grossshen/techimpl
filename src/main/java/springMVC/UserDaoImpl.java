package springMVC;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 16:45
 */
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {
    @Override
    public void insert() {
        System.out.println("我在dao着呢");
    }
}
