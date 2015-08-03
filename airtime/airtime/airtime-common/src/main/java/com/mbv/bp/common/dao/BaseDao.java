package com.mbv.bp.common.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapException;
import com.mbv.bp.common.config.SqlConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.SQLException;
import java.util.List;


public abstract class BaseDao<T> {

    protected static final Log log = LogFactory.getLog(BaseDao.class);

    protected SqlMapClient sqlMapClient;

    protected BaseDao() {
        sqlMapClient = SqlConfig.getAtSqlMapInstance();
    }
    
    protected BaseDao(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    @SuppressWarnings("unchecked")
    protected Boolean select(T obj, String sqlQuery) throws DaoException {
        try {
            T result = (T) sqlMapClient.queryForObject(sqlQuery, obj, obj);
            return (result != null);
        } catch (SQLException e) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + e.getMessage(), e);
        } catch (SqlMapException sqlMapEx) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + sqlMapEx.getMessage(), sqlMapEx);
        }
    }

    protected Boolean insert(T obj, String sqlQuery) throws DaoException {
        try {
            sqlMapClient.insert(sqlQuery, obj);
            return true;
        } catch (SQLException e) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + e.getMessage(), e);
        } catch (SqlMapException sqlMapEx) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + sqlMapEx.getMessage(), sqlMapEx);
        }

    }

    protected Boolean update(T obj, String sqlQuery) throws DaoException {
        try {
        	int i=sqlMapClient.update(sqlQuery, obj);
            if (i>0)
            	return true;
            else return false;
        } catch (SQLException e) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + e.getMessage(), e);
        } catch (SqlMapException sqlMapEx) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + sqlMapEx.getMessage(), sqlMapEx);
        }
    }

    protected Boolean delete(T obj, String sqlQuery) throws DaoException {
        try {
            sqlMapClient.delete(sqlQuery, obj);
            return true;
        } catch (SQLException e) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + e.getMessage(), e);
        } catch (SqlMapException sqlMapEx) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + sqlMapEx.getMessage(), sqlMapEx);
        }
    }

    @SuppressWarnings("unchecked")
    protected List<T> selectList(T obj, String sqlQuery) throws DaoException {
        try {
            return sqlMapClient.queryForList(sqlQuery, obj);
        } catch (SQLException e) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + e.getMessage(), e);
        } catch (SqlMapException sqlMapEx) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + sqlMapEx.getMessage(), sqlMapEx);
        }
    }

    protected Object selectObject(T obj, String sqlQuery) throws DaoException {
        try {
            return sqlMapClient.queryForObject(sqlQuery, obj);
        } catch (SQLException e) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + e.getMessage(), e);
        } catch (SqlMapException sqlMapEx) {
            throw new DaoException("Error running SQL statement: " + sqlQuery +
                    ". Error message is " + sqlMapEx.getMessage(), sqlMapEx);
        }
    }

}
