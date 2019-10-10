# J-ORM
一个简单ORM框架：
* 提供自定义参数和结构映射；
* 多种使用方式

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

* 直接调用BaseMapper方法
* 继承BaseMapper（参考示例中的UserMapperImpl）
* 基于Java Config和动态代理生成Mapper

### 简单使用示例

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

### 基于Java Config和动态代理生成Mapper

    private Configuration newConfiguration() throws Exception {
        Configuration configuration = new Configuration();
        //注册结果映射器
        String id = "0";
        String className = "com.jsj.orm.UserDO";
        MultiResultMap multiResultMap = new MultiResultMap();
        multiResultMap.setId(id);
        multiResultMap.setClassName(className);
        multiResultMap.addResultMap("id", "id");
        multiResultMap.addResultMap("user_name", "userName");
        multiResultMap.addResultMap("phone", "phone");
        multiResultMap.addResultMap("balance", "balance");
        multiResultMap.addResultMap("create_time", "createTime");
        configuration.registerResultMapHandler(multiResultMap);

        //注册参数映射器
        MultiParamMap multiParamMap = new MultiParamMap();
        multiParamMap.setId(id);
        multiParamMap.setClassName(className);
        multiParamMap.addParam(0, "userName");
        multiParamMap.addParam(1, "phone");
        multiParamMap.addParam(2, "balance");
        multiParamMap.addParam(3, "id");
        configuration.registerParamMapHandler(multiParamMap);

        //注册selectOne方法
        MethodDefinition methodDefinition = new MethodDefinition();
        methodDefinition.setInterfaceName("com.jsj.orm.dao.UserMapper");
        methodDefinition.setExecuteType(ExecuteType.SELECT_ONE);
        methodDefinition.setHandlerId(id);
        methodDefinition.setSql("select * from `tb_user` where id =?");
        methodDefinition.setMethodName("selectOne");
        methodDefinition.setParameterTypes(new String[]{"java.lang.Long"});
        configuration.registerMethod(methodDefinition.getMethod(), methodDefinition);

        //注册selectName方法
        methodDefinition = new MethodDefinition();
        methodDefinition.setInterfaceName("com.jsj.orm.dao.UserMapper");
        methodDefinition.setExecuteType(ExecuteType.SELECT_ONE);
        methodDefinition.setHandlerId("String");
        methodDefinition.setSql("select user_name from `tb_user` where id =?");
        methodDefinition.setMethodName("selectName");
        methodDefinition.setParameterTypes(new String[]{"java.lang.Long"});
        configuration.registerMethod(methodDefinition.getMethod(), methodDefinition);

        //注册updateName方法
        methodDefinition = new MethodDefinition();
        methodDefinition.setInterfaceName("com.jsj.orm.dao.UserMapper");
        methodDefinition.setExecuteType(ExecuteType.UPDATE);
        methodDefinition.setSql("update `tb_user` set user_name=? where id =?");
        methodDefinition.setMethodName("updateName");
        methodDefinition.setParameterTypes(new String[]{"java.lang.String", "java.lang.Long"});
        configuration.registerMethod(methodDefinition.getMethod(), methodDefinition);

        //注册update方法
        methodDefinition = new MethodDefinition();
        methodDefinition.setExecuteType(ExecuteType.UPDATE);
        methodDefinition.setHandlerId(id);
        methodDefinition.setSql("update `tb_user` set user_name=? , phone=? , balance=? where id =?");
        methodDefinition.setInterfaceName("com.jsj.orm.dao.UserMapper");
        methodDefinition.setMethodName("update");
        methodDefinition.setParameterTypes(new String[]{"com.jsj.orm.UserDO"});
        configuration.registerMethod(methodDefinition.getMethod(), methodDefinition);

        return configuration;
    }

    private MapperProxyFactory getMapperProxyFactory(Configuration configuration, DataSource dataSource) {
        return new MapperProxyFactory(configuration, dataSource);
    }

    private UserMapper getUserMapper() throws Exception {
        DataSource dataSource = getDataSource();
        Configuration configuration = newConfiguration();
        MapperProxyFactory mapperProxyFactory = getMapperProxyFactory(configuration, dataSource);
        return mapperProxyFactory.getMapper(UserMapper.class);
    }



