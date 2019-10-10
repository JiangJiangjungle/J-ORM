package com.jsj.orm;

import com.jsj.orm.config.Configuration;
import com.jsj.orm.map.ResultMapHandler;
import com.jsj.orm.transaction.DefaultTransactionFactory;
import com.jsj.orm.transaction.Transaction;
import com.jsj.orm.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 可以直接使用，也可以通过继承BaseMapper进行使用
 * 由于实际调用了Executor的方法进行操作，所以BaseMapper也是一次性的。
 *
 * @author jiangshenjie
 */
public class BaseMapper implements Mapper {
    private TransactionFactory transactionFactory;
    private DataSource dataSource;
    private Configuration configuration;
    /**
     * 是否自动提交事务
     */
    private boolean autoCommit;
    private Executor executor = null;

    public BaseMapper(DataSource dataSource, boolean autoCommit) {
        this(new Configuration(), dataSource, new DefaultTransactionFactory(), autoCommit);
    }

    public BaseMapper(Configuration configuration, DataSource dataSource, TransactionFactory transactionFactory, boolean autoCommit) {
        this.transactionFactory = transactionFactory;
        this.dataSource = dataSource;
        this.configuration = configuration;
        this.autoCommit = autoCommit;
    }

    @Override
    public <E> List<E> selectList(String sql, ResultMapHandler<E> resultMapHandler, Object... params) {
        List<E> results = new ArrayList<>(0);
        if (isExecutorCreated(true)) {
            try {
                results = executor.query(sql, resultMapHandler, params);
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public <E> E selectOne(String sql, ResultMapHandler<E> resultMapHandler, Object... params) {
        List<E> result = selectList(sql, resultMapHandler, params);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public boolean update(String sql, Object... params) {
        boolean updated = false;
        if (isExecutorCreated(true)) {
            try {
                updated = executor.update(sql, params);
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
        return updated;
    }

    @Override
    public void commit() {
        if (isExecutorCreated(false)) {
            try {
                executor.commit();
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
    }

    @Override
    public void rollback() {
        if (isExecutorCreated(false)) {
            try {
                executor.rollback();
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
    }

    /**
     * 检查executor是否已经被创建
     *
     * @param createIfNotExisted 是否创建executor
     * @return
     */
    private boolean isExecutorCreated(boolean createIfNotExisted) {
        if (executor == null && createIfNotExisted) {
            Transaction transaction = transactionFactory.newTransaction(dataSource, autoCommit);
            executor = new BaseExecutor(this.configuration, transaction);
        }
        return executor != null;
    }
}
