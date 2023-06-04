package org.wenruo.ohh.dao.model;

import lombok.Data;

/**
 * @author wendb
 * @date 2023/5/7
 */

@Data
public class Account {
    private Long id;
    private String accountId;
    private Long amount;
    private Long frozenAmount;
}
