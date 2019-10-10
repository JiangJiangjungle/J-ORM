# J-ORM
简洁版ORM框架：提供自定义映射：）

### 主要接口

    /**
     * @author jiangshenjie
     */
    public interface Mapper {
    
        /**
         * 查询操作
         *
         * @param sql
         * @param resultMapHandler
         * @param params
         * @param <E>
         * @return 封装对象
         */
        <E> E selectOne(String sql, ResultMapHandler<E> resultMapHandler, Object... params);
    
        /**
         * 查询操作
         *
         * @param sql
         * @param resultMapHandler
         * @param params
         * @param <E> 根据一行返回结果封装的对象
         * @return 对象列表
         */
        <E> List<E> selectList(String sql, ResultMapHandler<E> resultMapHandler, Object... params);
    
        /**
         * 更新操作
         *
         * @param sql
         * @param params
         * @return 执行结果
         */
        boolean update(String sql, Object... params);
    
        /**
         * 事务提交
         */
        void commit();
    
        /**
         * 事务回滚
         */
        void rollback();
    }
    
    /**
     * 映射器：数据表字段-> 对象
     *
     * @param <T>
     * @author jiangshenjie
     */
    public interface ResultMapHandler<T> {
        /**
         * 映射处理
         *
         * @param results 封装类sql响应结果的map：key为数据表的字段名，value为字段值
         * @return 返回的映射对象
         */
        T mapper(Map<String, Object> results);
    }
    
    /**
     * 映射器：对象->sql参数数组
     *
     * @param <T>
     * @author jiangshenjie
     */
    public interface ParamMapHandler<T> {
        /**
         * 映射成参数数组
         *
         * @param e 待映射的对象
         * @return 对应到sql中的参数数组，按顺序排列
         */
        Object[] mapper(T e);
    }

### 使用方法

* 继承BaseMapper（参考示例中的UserMapper）
* 直接调用BaseMapper方法
* 基于Java Config的动态代理生成

### 简单示例

    @Test
    public void selectName() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        String userName = userMapper.selectName(1L);
        System.out.println(userName);
    }

    @Test
    public void selectOne() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        UserDO userDO = userMapper.selectOne(1L);
        System.out.println(userDO);
    }

    @Test
    public void updateOne() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setUserName("jsj");
        userDO.setPhone("12312412");
        userDO.setBalance(new BigDecimal(666.6));
        userMapper.update(userDO);
        System.out.println("---------select-----------");
        selectOne();
    }

    @Test
    public void updateName() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        userMapper.updateName("tom_" + System.currentTimeMillis(), 1L);
        System.out.println("---------select-----------");
        selectOne();
    }



