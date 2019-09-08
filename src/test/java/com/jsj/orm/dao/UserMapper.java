package com.jsj.orm.dao;

import com.jsj.orm.UserDO;

public interface UserMapper {
    void createTableIfNotExists();

    UserDO selectOne(Long id);

    String selectName(Long id);

    boolean update(String userName, Long id);
}
