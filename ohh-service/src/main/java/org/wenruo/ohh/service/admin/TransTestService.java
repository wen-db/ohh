package org.wenruo.ohh.service.admin;

/**
 * @author wendebao
 * @Date 2020/3/23
 **/
public interface TransTestService<T> {

    void tesInsert(T t);
    void testThrowRuntimeException(T t);
    void testInsertAndThrowsRuntimeException(T t);
}
