package org.wenruo.ohh.dao.mapper;

import org.springframework.stereotype.Repository;
import org.wenruo.ohh.dao.model.AdminLoginLog;
@Repository
public interface AdminLoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminLoginLog record);

    int insertSelective(AdminLoginLog record);

    AdminLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminLoginLog record);

    int updateByPrimaryKey(AdminLoginLog record);
}