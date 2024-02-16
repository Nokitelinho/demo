/*
 * GPAReportingDetailsFilterQuery.java Created on Feb 22, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import com.ibsplc.icargo.framework.util.time.LocalDate;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;

/**
 * @author A-1945
 */

/*
*
* Revision History
* Version	 	Date      		    Author			Description
* 0.1			Feb 22, 2007 	  	A-1945			Initial draft
*
*/
public class GPAReportingDetailsFilterQuery extends NativeQuery {
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA GPAREPORTING");
	
    private String baseQuery;
    
    private GPAReportingFilterVO gpaReportFilterVO;

    /**
     * 
     * @param baseQuery
     * @param gpaReportFilterVO
     * @throws SystemException
     */
    public GPAReportingDetailsFilterQuery(String baseQuery,
			GPAReportingFilterVO gpaReportFilterVO) throws SystemException {
		this.baseQuery = baseQuery;
		this.gpaReportFilterVO = gpaReportFilterVO;
	}

    /**
	 * return String
	 */
    public String getNativeQuery() {
    	log.entering("GPAReportingDetailsFilterQuery", "getNativeQuery");
        StringBuilder filterQuery = new StringBuilder(baseQuery);
        String poaCode = gpaReportFilterVO.getPoaCode();
       // String country = gpaReportFilterVO.getCountry();
        LocalDate fromDate = gpaReportFilterVO.getReportingPeriodFrom();
        LocalDate toDate = gpaReportFilterVO.getReportingPeriodTo();
        int index = 0;
        this.setParameter(++index, gpaReportFilterVO.getCompanyCode());
        if(poaCode != null && poaCode.trim().length() > 0) {
            filterQuery.append(" AND RPT.POACOD = ?");
            this.setParameter(++index, poaCode);
        }

        if(fromDate != null) {
            filterQuery.append(" AND BIL.REPPRDFRM = ?");
            this.setParameter(++index, fromDate);
        }

        if(toDate != null) {
            filterQuery.append(" AND BIL.REPPRDTO = ? ");
            this.setParameter(++index, toDate);
        }
        /*
        if(country != null && country.trim().length() > 0) {
            filterQuery.append(" AND RPT.CNTCOD = ?");
            this.setParameter(++index, country);
        }
        */
        filterQuery.append(" ORDER BY RPT.CMPCOD, RPT.POACOD, RPT.BLGBAS, RPT.REPPRDFRMSTR, RPT.REPPRDTOOSTR,RPT.SERNUM");
        log.exiting("GPAReportingDetailsFilterQuery", "getNativeQuery");
        return filterQuery.toString();
    }
}