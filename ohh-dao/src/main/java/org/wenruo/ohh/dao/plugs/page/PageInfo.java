package org.wenruo.ohh.dao.plugs.page;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo<T> {
    //总记录数
    protected long total;
    //结果集
    protected List<T> list;
}
