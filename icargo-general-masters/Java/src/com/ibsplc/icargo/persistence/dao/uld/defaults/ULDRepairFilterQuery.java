/*
 * ULDRepairFilterQuery.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 */
public class ULDRepairFilterQuery extends PageableNativeQuery<ULDRepairDetailsListVO> {

	private Log log = LogFactory.getLogger("ULD");

	private ULDRepairFilterVO uldRepairFilterVO = null;
	private String baseQuery;

  /**
   * 
   * @param uldRepairFilterVO
   * @param baseQuery
   * @throws SystemException
   */
    public ULDRepairFilterQuery(
    		ULDRepairFilterVO uldRepairFilterVO ,
		String baseQuery,ULDRepairListMapper mapper) throws SystemException {
    	super(uldRepairFilterVO.getTotalRecords(),mapper);
    	this.uldRepairFilterVO = uldRepairFilterVO;
    	this.baseQuery = baseQuery;
    }


  /**
   *
   * @return
   */
    public String getNativeQuery() {
    	log.entering("ULDRepairFilterQuery","getNativeQuery");
    	int index = 0;
    	StringBuilder sbd = new StringBuilder(baseQuery);
    	this.setParameter(++index , uldRepairFilterVO.getCompanyCode());
    	if(uldRepairFilterVO.getUldNumber() != null &&
    			uldRepairFilterVO.getUldNumber().trim().length() > 0){
    		sbd.append(" AND ULD.ULDNUM = ? ");
    		this.setParameter(++index , uldRepairFilterVO.getUldNumber());
    	}
    	if(uldRepairFilterVO.getRepairHead() != null &&
    			uldRepairFilterVO.getRepairHead().trim().length() > 0){
    		sbd.append(" AND RPR.RPRHED = ? ");
    		this.setParameter(++index , uldRepairFilterVO.getRepairHead());
    	}
    	if(uldRepairFilterVO.getUldTypeCode() != null && 
    			uldRepairFilterVO.getUldTypeCode().trim().length() > 0){
    		sbd.append(" AND ULD.ULDTYP = ? ");
    		this.setParameter(++index , uldRepairFilterVO.getUldTypeCode());
    	}
    	if(uldRepairFilterVO.getUldStatus() != null && 
    			uldRepairFilterVO.getUldStatus().trim().length() > 0){
    		sbd.append(" AND ULD.OVLSTA = ? ");
    		this.setParameter(++index , uldRepairFilterVO.getUldStatus());
    	}
    	if(uldRepairFilterVO.getRepairStatus() != null && 
    			uldRepairFilterVO.getRepairStatus().trim().length() > 0){
    		sbd.append(" AND ULD.RPRSTA = ? ");
    		this.setParameter(++index, uldRepairFilterVO.getRepairStatus());
    	}
    	if(uldRepairFilterVO.getCurrentStation() != null && 
    			uldRepairFilterVO.getCurrentStation().trim().length() > 0){
    		sbd.append(" AND ULD.CURARP = ? ");
    		this.setParameter(++index , uldRepairFilterVO.getCurrentStation());
    	}
    	if(uldRepairFilterVO.getRepairStation() != null && 
    			uldRepairFilterVO.getRepairStation().trim().length() > 0){
    		sbd.append(" AND RPR.RPRARP = ? ");
    		this.setParameter(++index , uldRepairFilterVO.getRepairStation());
    	}
    	if(uldRepairFilterVO.getFromDate() != null){
    		sbd.append(" AND RPR.RPRDAT >= ? ");
    		this.setParameter(++index , uldRepairFilterVO.getFromDate());
    	}
    	// Modified as part of bug ICRD-178351
    	LocalDate toDate = null;  
    	if(uldRepairFilterVO.getToDate() != null){
    		sbd.append(" AND RPR.RPRDAT <= ? ");
    		toDate =  new LocalDate(uldRepairFilterVO.getToDate(), true);
    		toDate.set(14, toDate.getActualMaximum(14));
    		toDate.set(13, toDate.getActualMaximum(13));
    		toDate.set(12, toDate.getActualMaximum(12));
    		toDate.set(11, toDate.getActualMaximum(11));
    		this.setParameter(++index , toDate);	 
    	// Modified as part of bug ICRD-178351 ends
    	}
    	sbd.append("ORDER BY ULDNUM");
    	sbd.append( " ) RESULT_TABLE" );
    	log.log(Log.INFO, "!!!!!QUERY", sbd.toString());
		return sbd.toString();
    }
}
