package org.wenruo.ohh.dao.mapper;

import org.springframework.stereotype.Repository;
import org.wenruo.ohh.dao.model.Account;
import org.wenruo.ohh.dao.model.AccountFlow;

import java.util.List;

@Repository
public interface AccountFlowMapper {
    int insert(AccountFlow flow);

    List<AccountFlow> selectByOrderNo(String orderNo);
}