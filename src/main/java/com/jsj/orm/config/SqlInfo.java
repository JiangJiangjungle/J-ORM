package com.jsj.orm.config;

import com.jsj.orm.constant.SqlCommandType;
import com.jsj.orm.map.ResultMap;
import lombok.Data;

import java.util.List;

@Data
public class SqlInfo {
    private String methodName;
    private String sql;
    private SqlCommandType commandType;
    /**
     * 映射对象的包名
     */
    private String resultType;
    private List<ResultMap> resultMaps;


    public boolean returnsSingle() {
        return !List.class.getName().equals(resultType);
    }
}
