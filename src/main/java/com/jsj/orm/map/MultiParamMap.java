package com.jsj.orm.map;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangshenjie
 */
@Data
@NoArgsConstructor
public class MultiParamMap {
    private String id;
    private String className;
    private List<String> fieldNames;

    public MultiParamMap(String id, String className, List<String> fieldNames) {
        this.id = id;
        this.className = className;
        this.fieldNames = fieldNames;
    }

    public void addParam(int index, String field) {
        if (fieldNames == null) {
            fieldNames = new ArrayList<>();
        }
        fieldNames.add(index, field);
    }
}
