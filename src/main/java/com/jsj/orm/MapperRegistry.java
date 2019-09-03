package com.jsj.orm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangshenjie
 */
public class MapperRegistry {
    private static MapperRegistry instance;
    private Map<String, BaseMapper> daoInstanceMap = new HashMap<>();

    public static void init() {
        instance = new MapperRegistry();
        //todo 扫描并注册所有mapper
    }

    public static MapperRegistry getInstance() {
        return instance;
    }

    private MapperRegistry() {
    }

    public void register(BaseMapper daoImpl) {
        daoInstanceMap.put(daoImpl.getClass().getName(), daoImpl);
    }

    public <T extends BaseMapper> T getDAO(Class<T> clazz) {
        return (T) daoInstanceMap.get(clazz.getName());
    }
}
