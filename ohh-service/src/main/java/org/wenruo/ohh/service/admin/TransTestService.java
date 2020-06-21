package org.wenruo.ohh.service.admin;

/**
 * @author wendebao
 * @Date 2020/3/23
 **/
public interface TransTestService<T> {
    /**
     * 插入数据
     * @param t
     */
    void insert(T t);

    void  newTrans(T t);

    /**
     * 抛出runtimeException异常
     * @param t
     */
    void throwRuntimeException(T t);

    /**
     * 插入数据后抛出异常
     * @param t
     */
    void insertAndThrowsRuntimeException(T t);


    /**
     * 抛出异常
     */
    void throwNewRuntimeException();
}
