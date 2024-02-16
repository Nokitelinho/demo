/*
 * ULDMovementFilterQuery.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 */
public class ULDMovementFilterQuery extends PageableNativeQuery<ULDMovementDetailVO> {

	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	private ULDMovementFilterVO uldMovementFilterVO;
	private String query;
	
    /**
     * @throws SystemException
     * @param uldMovementFilterVO
     * @param baseQry
     */
    public ULDMovementFilterQuery(
            ULDMovementFilterVO uldMovementFilterVO,
		String baseQry, ULDMovementHistoryMapper multiMapper) throws SystemException {
    	super(uldMovementFilterVO.getTotalRecords(),multiMapper);
    	
    	this.uldMovementFilterVO = uldMovementFilterVO;
    	this.query = baseQry;
    	
    }
  /**
   *
   * @return
   */
    public String getNativeQuery() {
    	log.entering("ULDMovementFilterQuery","getNativeQuery");
        StringBuilder stringBuilder = new StringBuilder().append(query);
    	int index = 0;
        if(uldMovementFilterVO.getFromDate() != null){
        	log
					.log(Log.FINE, "From Date-->", uldMovementFilterVO.getFromDate());
			LocalDate fromDate = uldMovementFilterVO.getFromDate();
        	stringBuilder.append(" AND TRUNC(DTL.LSTMVTDAT) >= ? ");
            this.setParameter(++index,fromDate);
        }
            if(uldMovementFilterVO.getToDate() != null){
        	log.log(Log.FINE, "To Date-->", uldMovementFilterVO.getToDate());
			LocalDate toDate = uldMovementFilterVO.getToDate();
            stringBuilder.append(" AND TRUNC(DTL.LSTMVTDAT) <= ?  ");
            this.setParameter(++index,toDate);
        }
        stringBuilder.append(" AND DTL.CMPCOD = ? ");
        // Added by Preet on 18th Feb for ULD 205--starts
        stringBuilder.append(" AND DTL.ULDNUM = ? ");
        // Added by Preet on 18th Feb for ULD 205--ends
        //Sorting of records in descending order for bug fix 
        stringBuilder.append(" ORDER BY dtl.LSTMVTDATUTC DESC ");
        this.setParameter(++index,
        		uldMovementFilterVO.getCompanyCode().toUpperCase());
        // Added by Preet on 18th Feb for ULD 205--starts
        this.setParameter(++index,
        		uldMovementFilterVO.getUldNumber().toUpperCase());
        log.log(Log.FINE, "stringBuilder-->", stringBuilder.toString());
		log.exiting("ULDMovementFilterQuery","getNativeQuery");
    	return stringBuilder.toString();
    }
    
}
