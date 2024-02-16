/*
 * CN51DetailsPrintFilterQuery.java Created on Mar 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsPrintFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class CN51DetailsPrintFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("MRA:GPABILLING");

	private static final String CLASS_NAME = "CN51DetailsPrintFilterQuery";

	private CN51DetailsPrintFilterVO cn51DetailsPrintFilterVO;

	private String baseQuery;

    public CN51DetailsPrintFilterQuery(String baseQuery,CN51DetailsPrintFilterVO cn51DetailsPrintFilterVO)
    throws SystemException {
        //super();
    	this.baseQuery = baseQuery;
    	this.cn51DetailsPrintFilterVO = cn51DetailsPrintFilterVO;
    }

    /**
     * @return String
     */
    public String getNativeQuery() {
    	log.entering(CLASS_NAME,"getNativeQuery");

	    	int index = 0;
	    	StringBuilder stringBuilder = new StringBuilder(baseQuery);

	    	this.setParameter(++index,cn51DetailsPrintFilterVO.getCompanyCode());

	        if(cn51DetailsPrintFilterVO.getFromDate() != null ){
	        	stringBuilder.append(" AND to_number(to_char(SMY.BLGPRDFRM,'YYYYMMDD')) >= ? ");
	        	this.setParameter(++index,Integer.parseInt(cn51DetailsPrintFilterVO.getFromDate().toStringFormat("yyyyMMdd").substring(0, 8)) );
	        }

	        if(cn51DetailsPrintFilterVO.getToDate() != null){
	        	stringBuilder.append(" AND to_number(to_char(SMY.BLGPRDTOO,'YYYYMMDD')) <= ? ");
	        	this.setParameter(++index, Integer.parseInt(cn51DetailsPrintFilterVO.getToDate().toStringFormat("yyyyMMdd").substring(0, 8)) );
	        }

	       if(cn51DetailsPrintFilterVO.getGpaCode()!=null 
	    		   && cn51DetailsPrintFilterVO.getGpaCode().trim().length()>0){
	    	   stringBuilder.append(" AND SMY.GPACOD= ? ");
	        	this.setParameter(++index,cn51DetailsPrintFilterVO.getGpaCode());
	        }
	      
	       stringBuilder.append("ORDER BY DTL.ORGCOD,DTL.DSTCOD,DTL.MALCTGCOD,DTL.SUBCLSGRP"); 

	     log.exiting(CLASS_NAME,"getNativeQuery");
	     return stringBuilder.toString();
    }



}
