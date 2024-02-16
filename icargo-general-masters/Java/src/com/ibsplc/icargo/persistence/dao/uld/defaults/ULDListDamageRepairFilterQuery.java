/*
 * ULDListDamageRepairFilterQuery.java Created on May  5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.util.Objects;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3093
 *
 */
public class ULDListDamageRepairFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("ULD");

	private UldDmgRprFilterVO uldRepairFilterVO = null;
	private String baseQuery;

  /**
   * 
   * @param uldRepairFilterVO
   * @param baseQuery
   * @throws SystemException
   */
    public ULDListDamageRepairFilterQuery(
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
    	log.entering("ULDListDamageRepairFilterQuery","getNativeQuery");
    	int index = 0;
    	StringBuilder sbd = new StringBuilder(baseQuery);
    	this.setParameter(++index , uldRepairFilterVO.getCompanyCode());
    	if(uldRepairFilterVO.getUldNumber() != null &&
    			uldRepairFilterVO.getUldNumber().trim().length() > 0){
    		log.log(Log.INFO, "!!!!!uldRepairFilterVO.getUldNumber()",
					uldRepairFilterVO.getUldNumber());
			this.setParameter(++index , uldRepairFilterVO.getUldNumber());
    	}
        if(Objects.nonNull(uldRepairFilterVO.getFromDate())) {
        	LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
           fromDate.setDate(uldRepairFilterVO.getFromDate());
            sbd.append(" AND  TRUNC(DMG.DMGRPTDAT) >= to_date(?, 'yyyy-MM-dd') ");
            this.setParameter(++index, fromDate.toSqlDate().toString());
            }
        if(Objects.nonNull(uldRepairFilterVO.getToDate())) {
        	LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
        	toDate.setDate(uldRepairFilterVO.getToDate());
            sbd.append(" AND  TRUNC(DMG.DMGRPTDAT) <= to_date(?, 'yyyy-MM-dd') ");
            this.setParameter(++index, toDate.toSqlDate().toString());
    	
            }
         sbd.append(" ORDER BY DMG.DMGRPTDAT DESC");
    	log.log(Log.INFO, "!!!!!QUERY", sbd.toString());
		return sbd.toString();
    }
}
