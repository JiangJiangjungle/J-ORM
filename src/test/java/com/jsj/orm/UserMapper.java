package com.jsj.orm;

import com.jsj.orm.map.BasicResultMapHandler;
import com.jsj.orm.map.DefaultResultMapHandler;
import com.jsj.orm.map.ResultMap;
import com.jsj.orm.map.ResultMapHandler;

import javax.sql.DataSource;
import java.util.ArrayList;

public class UserMapper extends BaseMapper {
    private final String createTable = "CREATE TABLE `tb_user` (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "  `user_name` varchar(50) NOT NULL COMMENT '用户名',\n" +
            "  `phone` varchar(20) NOT NULL COMMENT '手机号码',\n" +
            "  `balance` decimal(18,3) NOT NULL COMMENT '现金',\n" +
            "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

    private final String selectOne = "select * from `tb_user` where id =?";

    private final String selectName = "select user_name from `tb_user` where id =?";

    private final String updateUserDO = "update `tb_user` set user_name=? where id =?";

    private final ResultMapHandler<UserDO> resultMapHandler = new DefaultResultMapHandler<>(UserDO.class,
            new ArrayList<ResultMap>() {{
                add(new ResultMap("id", "id"));
                add(new ResultMap("user_name", "userName"));
                add(new ResultMap("phone", "phone"));
                add(new ResultMap("balance", "balance"));
                add(new ResultMap("create_time", "createTime"));
            }}
    );

    public UserMapper(DataSource dataSource) {
        this(dataSource, true);
    }

    public UserMapper(DataSource dataSource, boolean autoCommit) {
        super(dataSource, autoCommit);
    }

    public UserDO selectOne(Long id) {
        return selectOne(selectOne, resultMapHandler, id);
    }

    public boolean update(String userName, Long id) {
        return update(updateUserDO, userName, id);
    }

    public String selectName(Long id) {
        return selectOne(selectName, new BasicResultMapHandler<>(String.class, "user_name"), id);
    }

    public void createTableIfNotExists() {
        update(createTable);
    }
}
