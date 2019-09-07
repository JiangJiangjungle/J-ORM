package com.jsj.orm.map;

/**
 * 字段映射关系
 *
 * @author jiangshenjie
 */
public class ResultMap {
    /**
     * 数据表中的对应字段名
     */
    private String columnName;
    /**
     * 映射对象中的字段名
     */
    private String fieldName;

    public ResultMap() {
    }

    public ResultMap(String columnName, String fieldName) {
        this.columnName = columnName;
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getColumnName() {
        return columnName;
    }
}
