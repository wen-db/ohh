package org.wenruo.ohh.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wenruo.ohh.dao.model.AdminUser;
import org.wenruo.ohh.dao.plugs.page.Page;

import java.util.List;

@Repository
public interface AdminUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long id);
    List<AdminUser> adminPageList( @Param("offset")int offset,@Param("pageSize")int pageSize);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
}