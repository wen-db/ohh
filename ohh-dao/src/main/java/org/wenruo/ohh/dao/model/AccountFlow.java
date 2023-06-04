package org.wenruo.ohh.dao.model;

import lombok.Data;

/**
 * @author wendb
 * @date 2023/5/7
 */

@Data
public class AccountFlow {
    private Long id;
    private String orderNo;
    private String accountId;
    private Long tradeAmount;
    private Integer tradeType;
    private Long afterAmount;
    private Long frozenAmount;
    private Long afterFrozenAmount;
}
