package com.jsj.orm.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字段映射关系
 *
 * @author jiangshenjie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMap {
    /**
     * 数据表中的对应字段名
     */
    private String tableColumnName;
    /**
     * 映射对象中的字段名
     */
    private String objectFieldName;
}
