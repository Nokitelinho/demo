/*
 * @(#) AbstractObjectQueryDAO.java 1.0 May 15, 2007
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

import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;

/**
 * @author Binu K
 */

/**<pre>
 * Revision History
 * Revision 	Date      	           Author			Description
 * 0.1			May 15, 2007				   Binu K			First draft
 </pre>
 */
public class AbstractObjectQueryDAO implements QueryDAO {

    private ObjectQueryManager queryManager;

    public final ObjectQueryManager getQueryManager() {
        return queryManager;
    }

    public final void setQueryManager(ObjectQueryManager queryManager) {
        this.queryManager = queryManager;
    }

    public boolean isOracleDataSource() {
        return false;
    }


}

