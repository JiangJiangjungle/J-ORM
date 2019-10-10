package com.jsj.orm;

/**
 * sql操作类型
 *
 * @author jiangshenjie
 */
public enum ExecuteType {
    /**
     * 单个记录查询
     */
    SELECT_ONE,
    /**
     * 列表查询
     */
    SELECT_LIST,
    /**
     * 更新，新增和删除操作
     */
    UPDATE
}
