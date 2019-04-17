package springMVC;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 16:39
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Qualifier("userDaoImpl")
    private UserDao userDao;

    @Override
    public void insert() {
        System.out.println("UserServiceImpl.insert() start");
        userDao.insert();
        System.out.println("UserServiceImpl.insert() end");
    }
}
