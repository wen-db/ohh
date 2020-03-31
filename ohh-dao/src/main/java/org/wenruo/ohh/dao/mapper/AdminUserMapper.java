package org.wenruo.ohh.dao.mapper;

import org.springframework.stereotype.Repository;
import org.wenruo.ohh.dao.model.AdminUser;
@Repository
public interface AdminUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
}