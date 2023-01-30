package org.wenruo.ohh.dao.plugs.page;

import java.util.ArrayList;

public class Page<T> extends ArrayList<T> {
    /**
     * 页码
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     *
     */
    private int offset;
    /**
     * 总记录数
     */
    private long total;
    public Page() {
        super();
    }

    public Page(int pageNum, int pageSize) {
        super(0);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.offset=pageSize*(pageNum-1);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
