/*
 * ULDAgreementDetailsPaginationFilterQuery.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8445
 *
 */
public class ULDAgreementDetailsPaginationFilterQuery extends PageableNativeQuery<ULDAgreementDetailsVO> {
	
	 private Log log = LogFactory.getLogger(" ULD_DEFAULTS");
	 
	 private ULDAgreementFilterVO uldAgreementFilterVO;
	 private String baseQuery ;

/**
 * 
 * @param uldAgreementFilterVO
 * @param baseQuery
 * @throws SystemException
 */
    public ULDAgreementDetailsPaginationFilterQuery(ULDAgreementFilterVO uldAgreementFilterVO,
    		String baseQuery,MultiMapper<ULDAgreementDetailsVO> uLDAgreementDetailsMultiMapper) throws SystemException {
		super(uldAgreementFilterVO.getTotalRecordsCount(),uLDAgreementDetailsMultiMapper);
		this.baseQuery = baseQuery;
		this.uldAgreementFilterVO = uldAgreementFilterVO;
    }

  /**
   *
   * @return
   */
    public String getNativeQuery() {
    	
    	log.entering("ULDAgreementDetailsPaginationFilterQuery","getNativeQuery");
    	int index = 0 ;
    	StringBuilder stbld = new StringBuilder().append(baseQuery);
    	this.setParameter(++index , uldAgreementFilterVO.getCompanyCode());
    	
    	if(uldAgreementFilterVO.getAgreementNumber() != null 
    			&& uldAgreementFilterVO.getAgreementNumber().trim().length() > 0){
    		this.setParameter(++index , uldAgreementFilterVO.getAgreementNumber());
    	}
    	
    	if(uldAgreementFilterVO.getUldTypeCodeFilter() != null 
    			&& uldAgreementFilterVO.getUldTypeCodeFilter().trim().length() > 0){
    		this.setParameter(++index , uldAgreementFilterVO.getUldTypeCodeFilter());
    	}
    	
    	log.log(Log.INFO, "!!!!QUERY : ", stbld.toString());
		return stbld.toString();
    }
}
