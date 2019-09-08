package com.jsj.orm.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangshenjie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapperInfo {
    private String mapperInterface;
    /**
     * key为接口中的方法名
     */
    private Map<String, SqlInfo> sqlInfoMap = new HashMap<>(4);

    public void addSqlInfo(SqlInfo sqlInfo) {
        this.sqlInfoMap.put(sqlInfo.getMethodName(), sqlInfo);
    }

    public SqlInfo getSqlInfo(Method method) {
        return sqlInfoMap.get(method.getName());
    }
}
