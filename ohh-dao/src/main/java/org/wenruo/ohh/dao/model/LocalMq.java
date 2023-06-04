package org.wenruo.ohh.dao.model;

import lombok.Data;

/**
 * @author wendb
 * @date 2023/5/7
 */

@Data
public class LocalMq {
    private Long id;
    private String ukey;
    private String topic;
    private String event;
    private Integer status;
    private String content;
}
