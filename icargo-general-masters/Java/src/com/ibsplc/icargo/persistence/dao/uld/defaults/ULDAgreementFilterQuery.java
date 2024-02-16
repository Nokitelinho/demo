/*
 * ULDAgreementFilterQuery.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1950
 *
 */
public class ULDAgreementFilterQuery extends PageableNativeQuery<ULDAgreementVO> {
	
	 private Log log = LogFactory.getLogger(" ULD_DEFAULTS");
	 
	 private ULDAgreementFilterVO uldAgreementFilterVO;
	 private String baseQuery ;

/**
 * 
 * @param uldAgreementFilterVO
 * @param baseQuery
 * @throws SystemException
 */
    public ULDAgreementFilterQuery(ULDAgreementFilterVO uldAgreementFilterVO,
    		String baseQuery,ULDAgreementMapper uLDAgreementMapper) throws SystemException {
		super(uldAgreementFilterVO.getTotalRecordsCount(),uLDAgreementMapper);
		this.baseQuery = baseQuery;
		this.uldAgreementFilterVO = uldAgreementFilterVO;
    }

  /**
   *
   * @return
   */
    public String getNativeQuery() {
    	
    	log.entering("ULDAgreementFilterQuery","getNativeQuery");
    	int index = 0 ;
    	StringBuilder stbld = new StringBuilder().append(baseQuery);
    	this.setParameter(++index , uldAgreementFilterVO.getCompanyCode());
    	
    	if(uldAgreementFilterVO.getAgreementNumber() != null 
    			&& uldAgreementFilterVO.getAgreementNumber().trim().length() > 0){
    		stbld.append(" AND AGRMNTNUM = ? ");
    		this.setParameter(++index , uldAgreementFilterVO.getAgreementNumber());
    	}
    	if(uldAgreementFilterVO.getPartyType() != null 
    			&& uldAgreementFilterVO.getPartyType().trim().length() > 0 ){
    		stbld.append(" AND PTYTYP = ? ");
    		this.setParameter(++index , uldAgreementFilterVO.getPartyType());
    	}
    	if(uldAgreementFilterVO.getPartyCode() != null 
    			&& uldAgreementFilterVO.getPartyCode().trim().length() > 0 ){
    		stbld.append(" AND PTYCOD = ? ");
    		this.setParameter(++index , uldAgreementFilterVO.getPartyCode());
    	}
    	if(uldAgreementFilterVO.getFromPartyType() != null 
    			&& uldAgreementFilterVO.getFromPartyType().trim().length() > 0 ){
    		stbld.append(" AND FRMPTYTYP = ? ");
    		this.setParameter(++index , uldAgreementFilterVO.getFromPartyType());
    	}
    	if(uldAgreementFilterVO.getFromPartyCode() != null 
    			&& uldAgreementFilterVO.getFromPartyCode().trim().length() > 0 ){
    		stbld.append(" AND FRMPTYCOD = ? ");
    		this.setParameter(++index , uldAgreementFilterVO.getFromPartyCode());
    	}
    	if(uldAgreementFilterVO.getAgreementDate() != null ){
    		stbld.append(" AND AGRMNTDAT = ? ");
    		this.setParameter(++index , uldAgreementFilterVO.getAgreementDate().toCalendar());
    	}
    	if(uldAgreementFilterVO.getTxnType() != null 
    			&& uldAgreementFilterVO.getTxnType().trim().length() > 0){
    		stbld.append(" AND TXNTYP = ? ");
    		this.setParameter(++index , uldAgreementFilterVO.getTxnType());
    	}
    	if(uldAgreementFilterVO.getAgreementStatus() != null 
    			&& uldAgreementFilterVO.getAgreementStatus().trim().length() > 0){
    		stbld.append(" AND AGRMNTSTA = ? ");
    		this.setParameter(++index , uldAgreementFilterVO.getAgreementStatus());
    	}
    	if(uldAgreementFilterVO.getAgreementFromDate() != null){
    		stbld.append(" AND AGRMNTFRMDAT >= ? ");
    		this.setParameter(++index , uldAgreementFilterVO.getAgreementFromDate().toCalendar());
    	}
    	if(uldAgreementFilterVO.getAgreementToDate() != null){
    		stbld.append(" AND AGRMNTTOODAT <= ? ");
    		this.setParameter(++index , uldAgreementFilterVO.getAgreementToDate().toCalendar());
    	}
    	
    	stbld.append(" ORDER BY AGRMNTNUM  ");
    	
    	log.log(Log.INFO, "!!!!QUERY", stbld.toString());
		return stbld.toString();
    }
}
