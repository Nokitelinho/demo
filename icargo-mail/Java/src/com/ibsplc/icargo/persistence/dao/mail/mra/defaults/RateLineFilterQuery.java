/*
 * RateLineFilterQuery.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class RateLineFilterQuery extends PageableNativeQuery<RateLineVO> {
	
	
	
	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private String baseQuery;
	private RateLineFilterVO rateLineFilterVO;
	/**
	 *
	 * @param rateLineDetailsMapper 
	 * @param TotalRecordCount 
	 * @param baseQuery
	 * @param rateLineFilterVO
	 * @throws SystemException
	 *
	 */
    public RateLineFilterQuery(int totalRecordCount, RateLineDetailsMapper rateLineDetailsMapper, String baseQuery,RateLineFilterVO rateLineFilterVO) throws SystemException {
    	super(totalRecordCount,rateLineDetailsMapper);
    	log.entering("RateLineFilterQuery","constructor");

        this.baseQuery=baseQuery;
        this.rateLineFilterVO=rateLineFilterVO;
        log.exiting("RateLineFilterQuery","constructor");
    }

    /**
	 * 
	 * @param rs
	 * @return
	 * @throws 
	 */
    public String getNativeQuery() {
    	log.entering("RateLine","nativequery");
    	String companyCode=rateLineFilterVO.getCompanyCode();
    	String rateCardID=rateLineFilterVO.getRateCardID();
    	String ratelineStatus=rateLineFilterVO.getRatelineStatus();
        String origin=rateLineFilterVO.getOrigin();
        String destination=rateLineFilterVO.getDestination();
        LocalDate startDate=rateLineFilterVO.getStartDate();
        LocalDate endDate=rateLineFilterVO.getEndDate();
        String orgDstLevel = (rateLineFilterVO.getOrgDstLevel() != null &&
        					rateLineFilterVO.getOrgDstLevel().trim().length() > 0) ? rateLineFilterVO.getOrgDstLevel() : null;	
        int index=0;
        this.setParameter(++index,companyCode);

        StringBuilder sbul = new StringBuilder(baseQuery);
        if(rateCardID!=null && rateCardID.trim().length()>0){
        	sbul.append(" AND RATLIN.RATCRDCOD=? ");
        	  this.setParameter(++index,rateCardID);
        }

        if(ratelineStatus!=null && ratelineStatus.trim().length()>0){
        	if("E".equals(ratelineStatus)){
        		LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
        		sbul.append(" AND RATLIN.VLDENDDAT <? ");
        		this.setParameter(++index,currentDate.toSqlDate());
        	}else{
        	LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
        	sbul.append(" AND RATLIN.VLDENDDAT >=? ");
        	sbul.append(" AND RATLIN.RATLINSTA=? ");
        	this.setParameter(++index,currentDate.toSqlDate());
      	  	this.setParameter(++index,ratelineStatus);
        	}
        }
        if(origin!=null && origin.trim().length()>0){
        	sbul.append(" AND RATLIN.ORGCOD=? ");
      	  this.setParameter(++index,origin);
        }
        if(destination!=null && destination.trim().length()>0){
        	sbul.append(" AND RATLIN.DSTCOD=? ");
      	  this.setParameter(++index,destination);
        }
        if (startDate != null && endDate != null) {
			sbul.append("AND ? BETWEEN RATLIN.VLDSTRDAT  AND RATLIN.VLDENDDAT  "
					+ " AND ?  BETWEEN RATLIN.VLDSTRDAT  AND RATLIN.VLDENDDAT  ");
			this.setParameter(++index, startDate.toSqlDate());
			this.setParameter(++index, endDate.toSqlDate());
		}
        if(orgDstLevel != null){
        	sbul.append(" AND RATLIN.ORGDSTLVL = ? ");
        	this.setParameter(++index,orgDstLevel);
        }
        sbul.append(" ORDER BY RATLIN.ORGCOD,RATLIN.DSTCOD ");
        return sbul.toString();
    }

}
