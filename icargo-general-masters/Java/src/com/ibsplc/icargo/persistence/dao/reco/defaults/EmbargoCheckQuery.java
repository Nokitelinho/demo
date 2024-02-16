/*
 * EmbargoCheckQuery.java Created on Sep 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.reco.defaults;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

/**
 * @author A-1358
 * 
 * This class implements the Embargo List Query
 */
public class EmbargoCheckQuery extends NativeQuery {
    
    public EmbargoCheckQuery() throws SystemException {        
    }
    
    /**
     * Implementation of Filter Query to lis embargos
     */
	/*@Override*/
	public String getNativeQuery() {
	    return null;
	}
}

