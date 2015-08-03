package com.mbv.bp.common.config;
import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.mbv.bp.common.constants.AppConfig;
import java.io.Reader;


public class SqlConfig {

    private static SqlMapClient atSqlMap = null;
    
    private static final Log log = LogFactory.getLog(SqlConfig.class);

    static {
        try {
            atSqlMap = buildSqlMapClient(AppConfig.AT_SQL_MAP_CONFIG_FILE);
        } catch (Exception e) {
            log.error("Error initializing SQL map client: " + e.getMessage(), e);
            atSqlMap=null;
        }
    }

    
    public static SqlMapClient getAtSqlMapInstance() {

        return atSqlMap;
    }
    
    public static void initAtSqlMapInstance(SqlMapClient sqlMap) {
        atSqlMap=sqlMap;
    }
    
    private static SqlMapClient buildSqlMapClient(String resource) throws Exception {
        Reader reader = Resources.getResourceAsReader(resource);
        return SqlMapClientBuilder.buildSqlMapClient(reader);
    }

}

