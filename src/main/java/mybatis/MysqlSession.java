package mybatis;

public interface MysqlSession {
    <T> T selectOne(String id);
    <T> T getMapper(Class<T> tClass);
}
