package org.wenruo.ohh.service.enums;

/**
 * @author wendb
 * @date 2023/5/7
 */
public enum TradeTypeEnum {

    IN(1,""),
    OUT(2,""),
    FROZEN(3,""),
    UNFROZEN(4,""),
    ;


    private int code;
    private String des;

    public int getCode() {
        return code;
    }

    TradeTypeEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }
}
