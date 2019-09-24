package com.jsj.orm.map;

import com.jsj.orm.exception.ResultCastException;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 默认的对象映射器
 * @param <T>
 * @author jiangshenjie
 */
public class DefaultResultMapHandler<T> implements ResultMapHandler<T> {
    /**
     * 映射对象类型
     */
    private Class<T> clz;
    /**
     * 字段映射关系
     */
    private List<ResultMap> resultMaps;

    public DefaultResultMapHandler(@NonNull Class<T> clz, @NonNull List<ResultMap> resultMaps) {
        this.clz = clz;
        this.resultMaps = resultMaps;
    }

    @Override
    public T mapper(@NonNull Map<String, Object> results) throws ResultCastException {
        T instance;
        try {
            instance = clz.newInstance();
            for (ResultMap resultMap : resultMaps) {
                String fieldName = resultMap.getFieldName();
                Field field = clz.getDeclaredField(fieldName);
                if (field == null) {
                    throw new ResultCastException(String.format("No such field [%s] !", fieldName));
                }
                field.setAccessible(true);
                Object fieldValue = results.get(resultMap.getColumnName());
                field.set(instance, fieldValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultCastException("Result Cast failed !");
        }
        return instance;
    }
}
