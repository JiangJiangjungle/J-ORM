package com.jsj.orm.map;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据表与对象中的字段映射关系
 *
 * @author jiangshenjie
 */
@Data
@NoArgsConstructor
public class MultiResultMap {
    private String id;
    private String className;
    private List<ResultMap> resultMaps;

    public MultiResultMap(String id, String className, List<ResultMap> resultMaps) {
        this.id = id;
        this.className = className;
        this.resultMaps = resultMaps;
    }

    public MultiResultMap(String id, String className, Map<String, String> resultMaps) {
        this.id = id;
        this.className = className;
        this.resultMaps = new ArrayList<>(resultMaps.size());
        for (Map.Entry<String, String> entry : resultMaps.entrySet()) {
            this.resultMaps.add(new ResultMap(entry.getKey(), entry.getValue()));
        }
    }

    public void addResultMap(String tableColumnName, String objectFieldName) {
        if (resultMaps == null) {
            resultMaps = new ArrayList<>();
        }
        resultMaps.add(new ResultMap(tableColumnName, objectFieldName));
    }
}
