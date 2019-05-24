package mybatis;

import lombok.Cleanup;

import java.sql.*;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/17 17:06
 */
public class ExecutorImpl implements Executor {
    @Override
    public <T> T query(String statement) {
        @Cleanup Connection connection=null;
        @Cleanup PreparedStatement preparedStatement=null;
        @Cleanup ResultSet resultSet=null;

        try {
            connection= DriverManager.getConnection("url","name","password");
            preparedStatement = connection.prepareStatement(statement);
            resultSet = preparedStatement.executeQuery();

            User user=null;
            if(resultSet.next()){
                user = new User();
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
            }
            return (T)user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
