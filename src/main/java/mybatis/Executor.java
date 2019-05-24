package mybatis;

public interface Executor {
    <T>T query(String statement);
}
