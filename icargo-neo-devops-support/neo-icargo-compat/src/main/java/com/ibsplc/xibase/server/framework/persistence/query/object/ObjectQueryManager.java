/*
 * @(#) ObjectQueryManager.java 1.0 May 15, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 *
 * Warning: This software is protected by copyright law and international treaties and conventions.
 * Unauthorized use, distribution or reproduction of this software, or of any parts thereof,
 * may result in prosecution and penalties.
 */

package com.ibsplc.xibase.server.framework.persistence.query.object;

import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.QueryManager;
import com.ibsplc.xibase.server.framework.persistence.query.object.hql.HibernateObjectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Binu K
 */

/**<pre>
 * Revision History
 * Revision 	Date      	           Author			Description
 * 0.1			May 15, 2007				   Binu K			First draft
 </pre>
 */
public class ObjectQueryManager extends QueryManager {

    static final Logger logger = LoggerFactory.getLogger(ObjectQueryManager.class);

    final javax.persistence.EntityManager entityManager;

    public ObjectQueryManager(String moduleName, QueryDAO queryDAO,
                              Map<String, String> nativeQueryMappings, Map<String, String> procedureMappings, javax.persistence.EntityManager entityManager) {
        super(moduleName, queryDAO, nativeQueryMappings, procedureMappings, entityManager);
        this.entityManager = entityManager;
    }

    public ObjectQuery createNamedQuery(String key)
            throws PersistenceException{
        String ejbQLString = getNamedNativeQueryString(key);
        return createQuery(ejbQLString);
    }

    public ObjectQuery createQuery(String ejbqlString)
            throws PersistenceException{
        javax.persistence.Query query = entityManager.createQuery(ejbqlString);
        return new HibernateObjectQuery(query, ejbqlString);
    }
}
