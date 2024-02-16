/*
 * BlgLineByChargeFilterQuery.java Created on Jul 1, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Jul 1, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Jul 1, 2015 A-5255
 * First draft
 */

public class BlgLineByChargeFilterQuery extends NativeQuery{
	private String baseQuery ;

	private Page<BillingLineVO> billingLineVOs;

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	private static final String CLASS_NAME = "BlgLineByChargeFilterQuery";
	 /**
     * @param baseQuery
     * @param billingLineVOs
     * @throws SystemException
     */
    public BlgLineByChargeFilterQuery(String baseQuery,Page<BillingLineVO> billingLineVOs)
    throws SystemException {
    	this.baseQuery = baseQuery;
    	this.billingLineVOs = billingLineVOs;
    }


    public String getNativeQuery() {
    	log.entering(CLASS_NAME,"getNativeQuery");
    	StringBuilder filterQuery
    						= new StringBuilder(this.baseQuery);
    	int index = 0;
    	int count = 0;
    	this.setParameter(++index,billingLineVOs.get(count).getCompanyCode());
    	filterQuery.append(" AND ");
    	for(BillingLineVO vo : billingLineVOs){
    		if(count != 0) {
				filterQuery.append(" OR ");
			}
    		filterQuery.append(" ( LGLINRATBSS.BLGMTXCOD = '")
    		.append(vo.getBillingMatrixId()).append("'")
    		.append(" AND  LGLINRATBSS.BLGLINSEQNUM = '")
    		.append(vo.getBillingLineSequenceNumber())
    		.append("' ) ");
    		count ++;
    	}

    	filterQuery.append(" ORDER BY BLGMTXCOD,BLGLINSEQNUM, CMPCOD,FRMWGT ASC ");//Modified for ICRD-149261
        log.log(Log.FINE, "<-- the final Filter Query --> \n\n", filterQuery.toString());
		return filterQuery.toString();
    }



}
