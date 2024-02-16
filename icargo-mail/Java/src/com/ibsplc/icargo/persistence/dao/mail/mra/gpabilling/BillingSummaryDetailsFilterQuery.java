/*
 * BillingSummaryDetailsFilterQuery.java Created on Mar 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class BillingSummaryDetailsFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("MRA:GPABILLING");

	private static final String CLASS_NAME = "BillingSummaryDetailsFilterQuery";

	private BillingSummaryDetailsFilterVO billingSummaryDetailsFilterVO;

	private String baseQuery;

    public BillingSummaryDetailsFilterQuery(String baseQuery,BillingSummaryDetailsFilterVO billingSummaryDetailsFilterVO)
    throws SystemException {
        //super();
    	this.baseQuery = baseQuery;
    	this.billingSummaryDetailsFilterVO = billingSummaryDetailsFilterVO;
    }

    /**
     * @return String
     */
    public String getNativeQuery() {
    	log.entering(CLASS_NAME,"getNativeQuery");

	    	int index = 0;
	    	StringBuilder stringBuilder = new StringBuilder(baseQuery);

	    	this.setParameter(++index,billingSummaryDetailsFilterVO.getCompanyCode());

	        if(billingSummaryDetailsFilterVO.getFromDate() != null ){
	        	
	        	stringBuilder.append("  AND to_number(to_char( C51.BLGPRDFRM,'yyyymmdd')) >= ? ");
	        	this.setParameter(++index,Integer.parseInt(billingSummaryDetailsFilterVO.getFromDate().toStringFormat("yyyyMMdd").substring(0, 8)) );    
	        }

	        if(billingSummaryDetailsFilterVO.getToDate() != null){
	        	
	        	stringBuilder.append(" AND to_number(to_char(  C51.BLGPRDTOO,'yyyymmdd'))  <= ? ");
	        	this.setParameter(++index,Integer.parseInt(billingSummaryDetailsFilterVO.getToDate().toStringFormat("yyyyMMdd").substring(0, 8)));
	        }

	       if(billingSummaryDetailsFilterVO.getGpaCode()!=null 
	    		   && billingSummaryDetailsFilterVO.getGpaCode().trim().length()>0){
	    	   stringBuilder.append(" AND C51.GPACOD= ? ");
	        	this.setParameter(++index,billingSummaryDetailsFilterVO.getGpaCode());
	        }
	      
	       if(billingSummaryDetailsFilterVO.getCountry()!=null
	    		   && billingSummaryDetailsFilterVO.getCountry().trim().length()>0){
	    	   stringBuilder.append(" AND MST.CNTCOD= ? ");
	        	this.setParameter(++index,billingSummaryDetailsFilterVO.getCountry());
	       }

	     log.exiting(CLASS_NAME,"getNativeQuery");
	     return stringBuilder.toString();
    }



}
