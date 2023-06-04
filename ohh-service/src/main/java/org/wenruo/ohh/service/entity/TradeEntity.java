package org.wenruo.ohh.service.entity;

import lombok.Builder;
import lombok.Data;
import org.wenruo.ohh.service.enums.TradeTypeEnum;

/**
 * @author wendb
 * @date 2023/5/7
 */
@Data
@Builder
public class TradeEntity {
   private String orderNo;
   private String accountId;
   private TradeTypeEnum tradeType;
   private long tradeAmount;



}
