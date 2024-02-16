/*
 * ListDamageReportFilterQuery.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 */
public class ListDamageReportFilterQuery extends PageableNativeQuery<ULDDamageDetailsListVO> {
	
	private Log log = LogFactory.getLogger("ULD");
	
	private ULDDamageFilterVO uldDamageFilterVO = null;
	private String baseQuery;
	
 /**
  * 
  * @param uldDamageFilterVO
  * @param baseQuery
  * @throws SystemException
  */
    public ListDamageReportFilterQuery(
    		Mapper<ULDDamageDetailsListVO> mapper,ULDDamageFilterVO uldDamageFilterVO ,
		String baseQuery) throws SystemException {
    	super(uldDamageFilterVO.getTotalRecords(),mapper);
    	this.uldDamageFilterVO = uldDamageFilterVO;
    	this.baseQuery = baseQuery;
    
    }


  /**
   *
   * @return
   */
    public String getNativeQuery() {
    	log.entering("ListDamageReportFilterQuery","Constructor");
    	int index = 0;
    	StringBuilder sbd = new StringBuilder(baseQuery);
    	
    	this.setParameter(++index , uldDamageFilterVO .getCompanyCode());
    	
    	if(uldDamageFilterVO.getUldNumber() != null && 
    			uldDamageFilterVO.getUldNumber().trim().length() > 0){
    		sbd.append(" AND ULD.ULDNUM = ? ");
    		this.setParameter(++index , uldDamageFilterVO.getUldNumber());
    	}
    	if(uldDamageFilterVO.getDamageReferenceNumber() > 0){
    		sbd.append(" AND DMG.DMGREFNUM = ? ");
    		this.setParameter(++index , uldDamageFilterVO.getDamageReferenceNumber());
    	}
    	if(uldDamageFilterVO.getUldTypeCode() != null && 
    			uldDamageFilterVO.getUldTypeCode().trim().length() > 0){
    		sbd.append(" AND ULD.ULDTYP = ? ");
    		this.setParameter(++index , uldDamageFilterVO.getUldTypeCode());
    	}
    	if(uldDamageFilterVO.getUldStatus() != null && 
    			uldDamageFilterVO.getUldStatus().trim().length() > 0){
    		sbd.append(" AND ULD.OVLSTA = ? ");
    		this.setParameter(++index , uldDamageFilterVO.getUldStatus());
    	}
    	if(uldDamageFilterVO.getDamageStatus() != null &&
    			uldDamageFilterVO.getDamageStatus().trim().length() > 0){
    		sbd.append(" AND ULD.DMGSTA = ? ");
    		this.setParameter(++index , uldDamageFilterVO.getDamageStatus());
    	}
    	if(uldDamageFilterVO.getCurrentStation() != null && 
    			uldDamageFilterVO.getCurrentStation().trim().length() > 0){
    		sbd.append(" AND ULD.CURARP = ? ");
    		this.setParameter(++index , uldDamageFilterVO.getCurrentStation());
    	}
    	if(uldDamageFilterVO.getReportedStation() != null && 
    			uldDamageFilterVO.getReportedStation().trim().length() > 0){
    		sbd.append(" AND DMG.RPTARP = ? ");
    		this.setParameter(++index , uldDamageFilterVO.getReportedStation());
    	}
    	if(uldDamageFilterVO.getFromDate() != null){
    		//Modified as [art of bug ICRD-1575 by A-3767 on 14Apr11
    		sbd.append(" AND TRUNC(DMG.DMGRPTDAT )>= ? ");
    		this.setParameter(++index , uldDamageFilterVO.getFromDate());
    	}
    	if(uldDamageFilterVO.getToDate() != null){
    		//Modified as [art of bug ICRD-1575 by A-3767 on 14Apr11
    		sbd.append(" AND TRUNC(DMG.DMGRPTDAT )<= ? ");
    		this.setParameter(++index , uldDamageFilterVO.getToDate());
    	}
    	//added for CRQ_311 o 09Apr08
    	if(uldDamageFilterVO.getPartyType() !=  null &&
    			uldDamageFilterVO.getPartyType().trim().length()>0){
    		sbd.append(" AND DMG.PTYTYP = ? ");
    		this.setParameter(++index , uldDamageFilterVO.getPartyType());
    		if(uldDamageFilterVO.getParty() != null &&
    				uldDamageFilterVO.getParty().trim().length()>0){
    			sbd.append(" AND DMG.PTYCOD = ? ");
        		this.setParameter(++index , uldDamageFilterVO.getParty());
    		}
    	}
    	if(uldDamageFilterVO.getFacilityType() != null &&
    			uldDamageFilterVO.getFacilityType().length()>0){
    		sbd.append(" AND DMG.FACTYP = ? ");
    		this.setParameter(++index , uldDamageFilterVO.getFacilityType());
    		if(uldDamageFilterVO.getLocation() != null &&
    				uldDamageFilterVO.getLocation().length()>0){
    			sbd.append(" AND DMG.LOC = ? ");
        		this.setParameter(++index , uldDamageFilterVO.getLocation());
    		}
    	}
    	sbd.append("ORDER BY ULDNUM");
    	log.log(Log.INFO, "!!!!!Query", sbd);
		return sbd.toString();
    }
}
