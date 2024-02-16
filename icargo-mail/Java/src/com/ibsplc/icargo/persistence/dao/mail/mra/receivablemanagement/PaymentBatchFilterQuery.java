/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.PaymentBatchFilterQuery.java
 *
 *	Created by	:	A-4809
 *	Created on	:	12-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement;

import java.util.Objects;

import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.PaymentBatchFilterQuery.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	12-Nov-2021	:	Draft
 */
public class PaymentBatchFilterQuery extends PageableNativeQuery<PaymentBatchDetailsVO>{
	

	private String baseQuery;
	
	private PaymentBatchFilterVO paymentBatchFilterVO;
	private boolean isOracleDataSource;	
	
	public PaymentBatchFilterQuery(int defaultPageSize,int totalRecordCount,PaymentBatchDetailsMapper paymentBatchDetailsMapper,
			String baseQuery,PaymentBatchFilterVO paymentBatchFilterVO) throws SystemException{
		super(defaultPageSize,totalRecordCount,paymentBatchDetailsMapper);
		this.baseQuery=baseQuery;
		this.paymentBatchFilterVO =paymentBatchFilterVO;
	}
	
	@Override
	 public String getNativeQuery() {
	    	String companyCode = paymentBatchFilterVO.getCompanyCode();
	        LocalDate fromDate=paymentBatchFilterVO.getBatchFrom();
	        LocalDate toDate=paymentBatchFilterVO.getBatchTo();
	    	int index=0;
	    	this.setParameter(++index,companyCode);
	    	StringBuilder sbul = new StringBuilder(baseQuery);
	    	if(fromDate != null && toDate != null){
	    		if(isOracleDataSource) { 
	    		sbul.append( "AND TO_NUMBER(TO_CHAR(STL.STLDAT,'YYYYMMDD')) BETWEEN ? AND ?");
	    		this.setParameter(++index, fromDate.toSqlDate().toString().replace("-", ""));
	    		this.setParameter(++index, toDate.toSqlDate().toString().replace("-", ""));
	    		}else {
	    			sbul.append( "AND TO_NUMBER(TO_CHAR(STL.STLDAT,'YYYYMMDD')) BETWEEN ? :: numeric AND ? :: numeric ");
		    		this.setParameter(++index, fromDate.toSqlDate().toString().replace("-", ""));
		    		this.setParameter(++index, toDate.toSqlDate().toString().replace("-", ""));
	    		}    	
	    	}
	    	if(!isNullAndEmpty(paymentBatchFilterVO.getPaCode())){
	    		sbul.append("AND STL.POACOD = ?");
	    		this.setParameter(++index, paymentBatchFilterVO.getPaCode());
	    	}
	    	if(!isNullAndEmpty(paymentBatchFilterVO.getBatchStatus())){
	    		sbul.append("AND STL.BTHSTA = ?");
	    		this.setParameter(++index, paymentBatchFilterVO.getBatchStatus());	
	    	}
			if(!isNullAndEmpty(paymentBatchFilterVO.getBatchId())){
	    		sbul.append("AND STL.BTHSTLIDR = ?");
	    		this.setParameter(++index, paymentBatchFilterVO.getBatchId());
	    	}
	    	if(paymentBatchFilterVO.getBatchDate() != null){
	    		if(isOracleDataSource) { 
	    		sbul.append("AND TO_NUMBER(TO_CHAR(STL.STLDAT,'YYYYMMDD')) = TO_NUMBER(TO_CHAR(?,'YYYYMMDD'))");
	    		this.setParameter(++index, paymentBatchFilterVO.getBatchDate().toSqlDate());
	    		}else {
	    			sbul.append("AND TO_NUMBER(TO_CHAR(STL.STLDAT::Date,'YYYYMMDD')) = TO_NUMBER(TO_CHAR(?::Date,'YYYYMMDD'))");
		    		this.setParameter(++index, paymentBatchFilterVO.getBatchDate().toSqlDate());
	    		}
	    	}
	    	return sbul.toString();
	 }

	 /**
	  * 	Method		:	PaymentBatchFilterQuery.isNullAndEmpty
	  *	Added by 	:	A-4809 on 12-Nov-2021
	  * 	Used for 	:
	  *	Parameters	:	@param s
	  *	Parameters	:	@return 
	  *	Return type	: 	boolean
	  */
	  private boolean isNullAndEmpty(String s) {
	        return Objects.isNull(s)|| s.trim().isEmpty();
	    }
	 
}
