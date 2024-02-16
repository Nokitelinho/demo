/*
 * GPABillingDetailsFilterQuery.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.flown;


import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

/**
 * @author A-1556
 *
 */
public class FlownMailFilterQuery extends NativeQuery {
	
	

    /**
     * @throws com.ibsplc.xibase.server.framework.exceptions.SystemException
     */
    public FlownMailFilterQuery(FlownMailFilterVO  flownMailFilterVO,String baseQuery) throws SystemException {
        super();
       

    }

    /**
     * @param 
     * @return String
     */
    public String getNativeQuery() {
        return null;
    }

    /**
     * @param 
     * @return 
     */
    public void operation1() {
    }

}
