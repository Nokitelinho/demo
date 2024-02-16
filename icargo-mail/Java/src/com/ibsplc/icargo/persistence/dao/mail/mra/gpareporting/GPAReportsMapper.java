/*
 * GPAReportsMapper.java created on Mar 7, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2245
 *
 */
public class GPAReportsMapper implements Mapper<GPAReportingClaimDetailsVO>{

	 private Log log = LogFactory.getLogger("MAILTRACKING MRA GPAREPORTING");
	 /**
	  * @author A-2245
	  * @param rs
	  * @throws SQLException
	  * @return GPAReportingClaimDetailsVO
	  */
	public GPAReportingClaimDetailsVO map(ResultSet rs) throws SQLException {
		log.entering("GPAReportingClaimDetailsMapper","map");
		GPAReportingClaimDetailsVO gpaReportingClaimDetailsVO=new GPAReportingClaimDetailsVO();
		gpaReportingClaimDetailsVO.setAssignedUser(rs.getString("CLMASDUSR"));
		gpaReportingClaimDetailsVO.setPoaCode(rs.getString("CLMPOACOD"));
		gpaReportingClaimDetailsVO.setPoaName(rs.getString("MSTPOANAM"));
		gpaReportingClaimDetailsVO.setCountryCode(rs.getString("MSTCNTCOD"));
		if(rs.getTimestamp("CLMREPPRDFRM")!=null){
			gpaReportingClaimDetailsVO.setReportingPeriodFrom(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CLMREPPRDFRM")));
		}
		if(rs.getTimestamp("CLMREPPRDTO")!=null){
			gpaReportingClaimDetailsVO.setReportingPeriodTo(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CLMREPPRDTO")));
		}
		gpaReportingClaimDetailsVO.setExceptionCode(rs.getString("CLMEXPCODE"));
		gpaReportingClaimDetailsVO.setDsnNumber(rs.getString("CLMDSN"));
		if(rs.getTimestamp("CLMASDTIM")!=null){
			gpaReportingClaimDetailsVO.setAssignedDate(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CLMASDTIM")));
		}
		if(rs.getTimestamp("CLMRSDTIM")!=null){
			gpaReportingClaimDetailsVO.setResolvedDate(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CLMRSDTIM")));
		}
		gpaReportingClaimDetailsVO.setTotalExceptions(rs.getInt("TOTEXP"));
		gpaReportingClaimDetailsVO.setTotalPendingExceptions(rs.getInt("TOTPDGEXP"));
		gpaReportingClaimDetailsVO.setTotalResolvedExceptions(rs.getInt("TOTRSDEXP"));
		log.exiting("GPAReportingClaimDetailsMapper","map");
		return gpaReportingClaimDetailsVO;
	}
}
