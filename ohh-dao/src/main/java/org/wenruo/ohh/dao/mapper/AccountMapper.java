package org.wenruo.ohh.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wenruo.ohh.dao.model.Account;

@Repository
public interface AccountMapper {
    int updateAmount(@Param("accountId") String accountId, @Param("amount") Long amount, @Param("frozenAmount") Long frozenAmount);
    int updateAmountSqlError(@Param("accountId") String accountId, @Param("amount") Long amount, @Param("frozenAmount") Long frozenAmount);

    Account lockByAccountId(String accountId);
    Account selectByAccountId(String accountId);
}