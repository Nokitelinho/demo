/*
 * @(#) QueryManager.java 1.0 Mar 28, 2005
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence.query;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeProcedure;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * The abstract base class of all Query Mangers. Concrete implementations of
 * QueryManager are obtained using the concrete implementation for a Query DAO.
 * Typically each sub-module will have a DAO implementation extending from
 * <i>AbstractQueryDAO</i>. Each DAO implementation is associated with an
 * instance of QueryManger, this being associated with a query definition file.
 * <p/>
 * Typically,
 *
 * <pre>
 *                 public class UserSqlDAO extends AbstractQueryDAO implements UserDAO{
 *                      ----------
 *                      ---------
 *                 }
 * </pre>
 * <p>
 * The DAO implementation is then obtained as,
 * <p/>
 *
 * <pre>
 * EntityManager em = PersistenceController.getEntityManager();
 *
 * UserDAO userDAO = (UserDAO) em.getQueryDAO(SUB_MODULE_NAME);
 * </pre>
 * <p>
 * <p/>
 * The QueryManger instance can then be obtained within the DAO impelementation
 * like,
 * <p/>
 *
 * <pre>
 * QueryManager qm = getQueryManager();
 * </pre>
 */
/*
 * Revision History
 * Revision         Date                        Author          Description
 * 1.0              Mar 28, 2005                Binu K          First draft
 */

public class QueryManager {

    static final Logger logger = LoggerFactory.getLogger(QueryManager.class);

    private final String moduleName;
    private final QueryDAO queryDAO;
    protected final Map<String, String> nativeQueryMappings;
    protected final Map<String, String> procedureMappings;
    private final javax.persistence.EntityManager entityManager;


    public QueryManager(String moduleName, QueryDAO queryDAO, Map<String, String> nativeQueryMappings, Map<String, String> procedureMappings, javax.persistence.EntityManager entityManager) {
        this.moduleName = moduleName;
        this.queryDAO = queryDAO;
        this.nativeQueryMappings = nativeQueryMappings;
        this.procedureMappings = procedureMappings;
        this.entityManager = entityManager;
    }

    /**
     * Return the query string for a named native query
     *
     * @param name the name of the named native query
     * @return the query string
     */
    public String getNamedNativeQueryString(String name) {
        return nativeQueryMappings.get(name);
    }

    /**
     * Return the query string for a named native procedure call
     *
     * @param name the name of the named native procedure
     * @return
     */
    public String getNamedNativeProcedureCall(String name) {
        return procedureMappings.get(name);
    }

    /**
     * Create a procedure instance using the procedure call string mapped to the
     * key
     *
     * @param key the key for the procedure call mapping
     * @return a Procedure instance
     */
    public Procedure createNamedNativeProcedure(String key)
            {
        final String callQuery = getNamedNativeProcedureCall(key);
        if (callQuery == null) {
            logger.error("No named procedure for key {}", key);
            throw new SystemException("NO NAMED PROCEDURE CALL FOR KEY " + key + " EXISTS!");
        }
        return new NativeProcedure(this.entityManager.unwrap(Session.class)) {
            public String getNativeCall() {
                return callQuery;
            }
        };
    }

    /**
     * Create a Query instance using the native query string mapped to the key
     *
     * @param key the key for the query string mapping
     * @return a Query instance
     */
    public Query createNamedNativeQuery(String key) {
        String sqlQuery = getNamedNativeQueryString(key);
        if (sqlQuery == null) {
            logger.error("No named query exist for key {}", key);
            throw new SystemException("NO NAMED QUERY FOR KEY " + key + " EXISTS!");
        }
        return createNativeQuery(sqlQuery);
    }

    /**
     * Create a Query instance using the actual native query string
     *
     * @param sqlString -
     *                  the native query string
     * @return the Query Instance
     */
    public Query createNativeQuery(String sqlString) {
        final String queryString = sqlString;
        Session session = this.entityManager.unwrap(Session.class);
        return new NativeQuery(session) {
            @Override
            public String getNativeQuery() {
                return queryString;
            }
        };
    }

    /**
     * @return the moduleName
     */
    protected String getModuleName() {
        return moduleName;
    }

    /**
     * @return the queryDAO
     */
    protected <T extends QueryDAO> T getQueryDAO() {
        return (T) queryDAO;
    }

}
