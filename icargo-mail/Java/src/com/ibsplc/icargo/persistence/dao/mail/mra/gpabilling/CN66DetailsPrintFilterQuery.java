/*
 * CN66DetailsPrintFilterQuery.java Created on Mar 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class CN66DetailsPrintFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("MRA:GPABILLING");

	private static final String CLASS_NAME = "CN51DetailsPrintFilterQuery";

	private CN66DetailsPrintFilterVO cn66DetailsPrintFilterVO;

	private String baseQuery;

    public CN66DetailsPrintFilterQuery(String baseQuery,CN66DetailsPrintFilterVO cn66DetailsPrintFilterVO)
    throws SystemException {
        //super();
    	this.baseQuery = baseQuery;
    	this.cn66DetailsPrintFilterVO = cn66DetailsPrintFilterVO;
    }

    /**
     * @return String
     */
    public String getNativeQuery() {
    	log.entering(CLASS_NAME,"getNativeQuery");

	    	int index = 0;
	    	StringBuilder stringBuilder = new StringBuilder(baseQuery);

	   	this.setParameter(++index,cn66DetailsPrintFilterVO.getCompanyCode());

	        if(cn66DetailsPrintFilterVO.getFromDate() != null ){
	        	stringBuilder.append(" AND TO_NUMBER(TO_CHAR(SMY.BLGPRDFRM,'YYYYMMDD')) >= ? ");
	        	this.setParameter(++index,Integer.parseInt(cn66DetailsPrintFilterVO.getFromDate().toStringFormat("yyyyMMdd").substring(0, 8)) );
	        }

	        if(cn66DetailsPrintFilterVO.getToDate() != null){
	        	stringBuilder.append(" AND TO_NUMBER(TO_CHAR(SMY.BLGPRDTOO,'YYYYMMDD')) <= ? ");
	        	this.setParameter(++index,Integer.parseInt(cn66DetailsPrintFilterVO.getToDate().toStringFormat("yyyyMMdd").substring(0, 8)) );
	        }

	       if(cn66DetailsPrintFilterVO.getGpaCode()!=null 
	    		   && cn66DetailsPrintFilterVO.getGpaCode().trim().length()>0){
	    	   stringBuilder.append(" AND SMY.GPACOD= ? ");
	        	this.setParameter(++index,cn66DetailsPrintFilterVO.getGpaCode());
	        }
	      
	       stringBuilder.append("ORDER BY DTL.ORGCOD,DTL.DSTCOD,DTL.MALCTGCOD,DTL.SUBCLSGRP"); 

	     log.exiting(CLASS_NAME,"getNativeQuery");
	     return stringBuilder.toString();
    }



}
