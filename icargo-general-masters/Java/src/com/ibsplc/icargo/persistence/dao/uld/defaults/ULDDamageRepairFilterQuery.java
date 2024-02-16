/*
 * ULDDamageRepairFilterQuery.java Created on May 18, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3093
 *
 */
public class ULDDamageRepairFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("ULD");

	private UldDmgRprFilterVO uldRepairFilterVO = null;
	private String baseQuery;

  /**
   * 
   * @param uldRepairFilterVO
   * @param baseQuery
   * @throws SystemException
   */
    public ULDDamageRepairFilterQuery(
    		UldDmgRprFilterVO uldRepairFilterVO ,
		String baseQuery) throws SystemException { 
    	super();
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
    	if(uldRepairFilterVO.getToDate() != null){
    		sbd.append(" AND RPR.RPRDAT <= ? ");
    		this.setParameter(++index , uldRepairFilterVO.getToDate());
    	}
    	log.log(Log.INFO, "!!!!!QUERY", sbd.toString());
		return sbd.toString();
    }
}
