package com.giimall.mybatis.injector.enums;


/**
 * MybatisPlus 扩展支持 SQL 方法
 * @author wangLiuJing
 * @date 2022/06/23
 */
public enum SqlMethodIgnoreLogic {

    /**
     * 查询
     */
    SELECT_BY_ID_IGNORE_LOGIC("selectByIdIgnoreLogic", "根据ID 查询一条数据(忽略逻辑删除条件)", "SELECT %s FROM %s WHERE %s=#{%s}"),
    SELECT_BY_MAP_IGNORE_LOGIC("selectByMapIgnoreLogic", "根据columnMap 查询一条数据(忽略逻辑删除条件)", "<script>SELECT %s FROM %s %s\n</script>"),
    SELECT_BATCH_BY_IDS_IGNORE_LOGIC("selectBatchIdsIgnoreLogic", "根据ID集合，批量查询数据(忽略逻辑删除条件)", "<script>SELECT %s FROM %s WHERE %s IN (%s)\n</script>"),
    SELECT_COUNT_IGNORE_LOGIC("selectCountIgnoreLogic", "查询满足条件总记录数(忽略逻辑删除条件)", "<script>%s SELECT COUNT(%s) FROM %s %s %s\n</script>"),
    SELECT_LIST_IGNORE_LOGIC("selectListIgnoreLogic", "查询满足条件所有数据(忽略逻辑删除条件)", "<script>%s SELECT %s FROM %s %s %s %s\n</script>"),
    SELECT_PAGE_IGNORE_LOGIC("selectPageIgnoreLogic", "查询满足条件所有数据（并翻页）(忽略逻辑删除条件)", "<script>%s SELECT %s FROM %s %s %s %s\n</script>"),
    SELECT_MAPS_IGNORE_LOGIC("selectMapsIgnoreLogic", "查询满足条件所有数据(忽略逻辑删除条件)", "<script>%s SELECT %s FROM %s %s %s %s\n</script>"),
    SELECT_MAPS_PAGE_IGNORE_LOGIC("selectMapsPageIgnoreLogic", "查询满足条件所有数据（并翻页）(忽略逻辑删除条件)", "<script>\n %s SELECT %s FROM %s %s %s %s\n</script>"),
    SELECT_OBJS_IGNORE_LOGIC("selectObjsIgnoreLogic", "查询满足条件所有数据(忽略逻辑删除条件)", "<script>%s SELECT %s FROM %s %s %s %s\n</script>");

    private final String method;
    private final String desc;
    private final String sql;

    SqlMethodIgnoreLogic(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }

}
