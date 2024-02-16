/*
 * CN51SummaryFilterQuery.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.util.Objects;

/**
 * @author A-1556
 *
 */
public class CN51SummaryFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("MRA:GPABILLING");

	private static final String CLASS_NAME = "CN51SummaryFilterQuery";
	private static final String INVSTA_FINALIZED = "F";
	private static final String INVSTA_PROFORMA = "P";
	private CN51SummaryFilterVO cn51SummaryFilterVO;

	private String baseQuery;
	private StringBuilder stringBuilder=null;
	int index ;
    public CN51SummaryFilterQuery(String baseQuery,CN51SummaryFilterVO cn51SummaryFilterVO)
    throws SystemException {
        //super();
    	this.baseQuery = baseQuery;
    	this.cn51SummaryFilterVO = cn51SummaryFilterVO;
    }

    /**
     * @return String
     */
    public String getNativeQuery() {
    	log.entering(CLASS_NAME,"getNativeQuery");

    		 index=0;
	    	 stringBuilder = new StringBuilder(baseQuery);

	    	this.setParameter(++index,cn51SummaryFilterVO.getCompanyCode());

	        if(cn51SummaryFilterVO.getFromDate() != null ){
	        	stringBuilder.append(" AND (TO_NUMBER(TO_CHAR(C51SMY.BLGPRDFRM,'YYYYMMDD')))  >= ? ");
	        	this.setParameter(++index, Integer.parseInt( cn51SummaryFilterVO.getFromDate().toStringFormat("yyyyMMdd").substring(0, 8)));
	        }

	        if(cn51SummaryFilterVO.getToDate() != null){
	        	stringBuilder.append(" AND (TO_NUMBER(TO_CHAR(C51SMY.BLGPRDTOO,'YYYYMMDD'))) <= ? ");
	        	this.setParameter(++index, Integer.parseInt( cn51SummaryFilterVO.getToDate().toStringFormat("yyyyMMdd").substring(0, 8)) );
	        }

	        String gpaCodeFilter = cn51SummaryFilterVO.getGpaCode();
	        if(gpaCodeFilter != null && gpaCodeFilter.length() > 0){
	        	stringBuilder.append(" AND C51SMY.GPACOD = ? ");
	        	this.setParameter(++index,cn51SummaryFilterVO.getGpaCode());
	        }
	        /**
	         * @author a-3447 for int bug fix starts 
	         */
	        if(cn51SummaryFilterVO.getInvoiceNumber() != null && cn51SummaryFilterVO.getInvoiceNumber().length() > 0){
	        	stringBuilder.append(" AND C51SMY.INVNUM = ? ");
	        	this.setParameter(++index,cn51SummaryFilterVO.getInvoiceNumber());
	        }
	        
	        if(cn51SummaryFilterVO.getInvoiceStatus() != null && cn51SummaryFilterVO.getInvoiceStatus().length() > 0){
	        	if(INVSTA_FINALIZED.equals(cn51SummaryFilterVO.getInvoiceStatus()) ||INVSTA_PROFORMA.equals(cn51SummaryFilterVO.getInvoiceStatus())){
	        	stringBuilder.append(" AND C51SMY.INVSTA = ? ");
	        	}else{
	        	stringBuilder.append(" AND C51SMY.STLSTA = ? ");
	        	}
	        	this.setParameter(++index,cn51SummaryFilterVO.getInvoiceStatus());
	        }
	       // stringBuilder.append(" ORDER BY C51SMY.INVNUM,C51SMY.GPACOD ");//Added for ICRD-201846
	        /**
	         * @author a-3447 for int bug fix ends 
	         */

	      setPASSFilter(cn51SummaryFilterVO);

	     log.exiting(CLASS_NAME,"getNativeQuery");
	     return stringBuilder.toString();
    }

	private void setPASSFilter(CN51SummaryFilterVO cn51SummaryFilterVO) {
		
	    if(cn51SummaryFilterVO.getPeriodNumber()!=null && cn51SummaryFilterVO.getPeriodNumber().length()>0){
        	
         	stringBuilder.append(" AND C51SMY.PRDNUM = ?  ");
        	this.setParameter(++index,cn51SummaryFilterVO.getPeriodNumber());
        	
        }
	    
		if(cn51SummaryFilterVO.isAddNew() && Objects.nonNull(cn51SummaryFilterVO.getPassFileName()) && !cn51SummaryFilterVO.getPassFileName().isEmpty()){
			stringBuilder.append(" AND ( INSTR(LOWER(C51SMY.INTFCDFILNAM),LOWER('").append(cn51SummaryFilterVO.getPassFileName()).append("'))> 0 OR C51SMY.INTFCDFILNAM IS NULL ) ");
		}
		else if (Objects.nonNull(cn51SummaryFilterVO.getPassFileName()) && !cn51SummaryFilterVO.getPassFileName().isEmpty()) {
			stringBuilder.append(" AND INSTR(LOWER(C51SMY.INTFCDFILNAM),LOWER('").append(cn51SummaryFilterVO.getPassFileName()).append("'))> 0 ");
		}
		
		if (cn51SummaryFilterVO.isPASS()) {
			stringBuilder.append(" AND C51SMY.POATYP = ?");
			this.setParameter(++index, "PA");
		}
		if(cn51SummaryFilterVO.getBranchOffice()!=null){
			stringBuilder.append(" AND   C66.BRHOFC = ?");
			this.setParameter(++index, cn51SummaryFilterVO.getBranchOffice());
		}
	}
	
	
	


}
