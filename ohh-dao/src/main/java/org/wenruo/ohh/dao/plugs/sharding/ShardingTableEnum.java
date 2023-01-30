package org.wenruo.ohh.dao.plugs.sharding;

public enum  ShardingTableEnum{

    TEST("test","id"),
    ;

    private  String tableName;

    private String shardingColumn;

    ShardingTableEnum(String tableName, String shardingColumn) {
        this.tableName = tableName;
        this.shardingColumn = shardingColumn;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getShardingColumn() {
        return shardingColumn;
    }

    public void setShardingColumn(String shardingColumn) {
        this.shardingColumn = shardingColumn;
    }
}
