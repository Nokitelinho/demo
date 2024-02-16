/*
 * RateLineErrorMapper.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineErrorVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1556
 * Mapper for getting a RateLineErrorVO.
 *
 * 						Revision History
 *
 * Version  Date 		 		Author 				Description
 *
 * 0.1       jan 19,2007        A-1556             Initial draft
 * 0.2 		Jan 24, 2007  		A-2270
 *
 *
 */
public class RateLineErrorMapper implements Mapper<RateLineErrorVO> {

	/**
	 *
	 * Jan 24, 2007, A-2270
	 * @param rs
	 * @return RateLineErrorVO
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *
	 */
    public RateLineErrorVO map(ResultSet rs) throws SQLException {
    	RateLineErrorVO rateLineErrorVO =
    		                        new RateLineErrorVO();

    	rateLineErrorVO.setCompanyCode(rs.getString("CMPCOD"));
    	rateLineErrorVO.setCurrentRateCardID(rs.getString("RATCRDCOD"));
    	rateLineErrorVO.setOrigin(rs.getString("ORGCOD"));
    	rateLineErrorVO.setDestination(rs.getString("DSTCOD"));
    	if(rs.getDate("VLDSTRDAT")!= null){
    		rateLineErrorVO.setCurrentValidityStartDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,rs.getDate("VLDSTRDAT")));
		}
    	if(rs.getDate("VLDENDDAT")!= null){
    		rateLineErrorVO.setCurrentValidityEndDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,rs.getDate("VLDENDDAT")));
		}
    return rateLineErrorVO;
    }

}










