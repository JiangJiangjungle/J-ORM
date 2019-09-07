package com.jsj.orm.map;

import com.jsj.orm.exception.ResultCastException;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @param <E>
 * @author jiangshenjie
 */
public class DefaultResultMapHandler<E> implements ResultMapHandler<E> {
    /**
     * 映射对象类型
     */
    private Class<E> clz;
    /**
     * 映射器
     */
    private List<ResultMap> resultMaps;

    public DefaultResultMapHandler(@NonNull Class<E> clz, @NonNull List<ResultMap> resultMaps) {
        this.clz = clz;
        this.resultMaps = resultMaps;
    }

    @Override
    public E mapper(@NonNull Map<String, Object> results) throws ResultCastException {
        E instance;
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
