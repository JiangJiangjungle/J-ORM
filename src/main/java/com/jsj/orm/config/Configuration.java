package com.jsj.orm.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author jiangshenjie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {
    /**
     * key为dao接口的包名
     */
    private Map<String, MapperInfo> mapperInfoMap;

    public MapperInfo getMapperInfo(Class<?> mapperInterface) {
        return mapperInfoMap.get(mapperInterface.getName());
    }
}
