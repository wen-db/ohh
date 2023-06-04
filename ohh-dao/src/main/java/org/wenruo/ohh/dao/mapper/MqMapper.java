package org.wenruo.ohh.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wenruo.ohh.dao.model.LocalMq;

import java.util.List;

@Repository
public interface MqMapper {
    int updateStatus(@Param("ukey") String ukey, @Param("status") Integer status);

    LocalMq selectByUkey(String ukey);
    int insert(LocalMq localMq);

    List<LocalMq> selectByStatus(int status);

}