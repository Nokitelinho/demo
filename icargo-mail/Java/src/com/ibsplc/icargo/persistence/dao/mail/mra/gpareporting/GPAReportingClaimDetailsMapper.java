/*
 * GPAReportingClaimDetailsMapper.java created on Feb 22, 2007
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
 * @author A-2280
 *
 */
public class GPAReportingClaimDetailsMapper implements Mapper<GPAReportingClaimDetailsVO>{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA GPAREPORTING");
	/**
	 * @author A-2280
	 * @param rs
	 * @throws SQLException
	 * @return GPAReportingClaimDetailsVO
	 */
	public GPAReportingClaimDetailsVO map(ResultSet rs) throws SQLException {
		log.entering("GPAReportingClaimDetailsMapper","map");
		GPAReportingClaimDetailsVO gpaReportingClaimDetailsVO=new GPAReportingClaimDetailsVO();
		gpaReportingClaimDetailsVO.setCompanyCode(rs.getString("CLAIMCMPCOD"));
		gpaReportingClaimDetailsVO.setPoaCode(rs.getString("CLAIMPOACOD"));
		gpaReportingClaimDetailsVO.setBillingBasis(rs.getString("CLAIMBLGBAS"));
		gpaReportingClaimDetailsVO.setBasisType(rs.getString("CLAIMBASTYP"));
		gpaReportingClaimDetailsVO.setDsnNumber(rs.getString("CLAIMDSN"));
		gpaReportingClaimDetailsVO.setExceptionCode(rs.getString("CLAIMEXPCODE"));
		gpaReportingClaimDetailsVO.setExceptionSequenceNumber(rs.getInt("CLAIMEXPSEQNUM"));
		gpaReportingClaimDetailsVO.setMailCategoryCode(rs.getString("CLAIMMALCTGCOD"));
		gpaReportingClaimDetailsVO.setMailSubClass(rs.getString("CLAIMMALSUBCLS"));
		gpaReportingClaimDetailsVO.setOriginOfficeOfExchange(rs.getString("CLAIMORGEXGOFC"));
		gpaReportingClaimDetailsVO.setDestOfficeOfExchange(rs.getString("CLAIMDSTEXGOFC"));
		gpaReportingClaimDetailsVO.setActualRate(rs.getDouble("CLAIMACTRAT"));
		gpaReportingClaimDetailsVO.setActualWeight(rs.getDouble("CLAIMACTWGT"));
		gpaReportingClaimDetailsVO.setReportedRate(rs.getDouble("CLAIMRPTRAT"));
		gpaReportingClaimDetailsVO.setReportedWeight(rs.getDouble("CLAIMRPTWGT"));
		gpaReportingClaimDetailsVO.setYear(rs.getString("CLAIMYER"));
		gpaReportingClaimDetailsVO.setReportingFromString(rs.getString("CLAIMREPPRDFRMSTR"));
		gpaReportingClaimDetailsVO.setReportingToString(rs.getString("CLAIMREPPRDTOSTR"));
		if(rs.getTimestamp("CLAIMASDTIM")!=null){
			gpaReportingClaimDetailsVO.setAssignedDate(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CLAIMASDTIM")));

		}
		gpaReportingClaimDetailsVO.setAssignedUser(rs.getString("CLAIMASDUSR"));
		if(rs.getTimestamp("CLAIMREPPRDFRM")!=null){
			gpaReportingClaimDetailsVO.setReportingPeriodFrom(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CLAIMREPPRDFRM")));

		}
		if(rs.getTimestamp("CLAIMREPPRDTO")!=null){
			gpaReportingClaimDetailsVO.setReportingPeriodTo(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CLAIMREPPRDTO")));

		}
		if(rs.getTimestamp("CLAIMRSDTIM")!=null){
			gpaReportingClaimDetailsVO.setResolvedDate(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CLAIMRSDTIM")));

		}

		/**
		 * @author a-3447 for AirNZ CR -175 Starts
		 */

		if (rs.getString("CCAREFNUM") != null) {
			gpaReportingClaimDetailsVO.setCcaRefNum(rs.getString("CCAREFNUM"));

		}

		if (rs.getString("CCASTA") != null) {
			gpaReportingClaimDetailsVO.setCcaStatus(rs.getString("CCASTA"));

		}

		gpaReportingClaimDetailsVO.setActualCharge(rs.getDouble("ACTCHG"));

		gpaReportingClaimDetailsVO.setReportedCharge(rs.getDouble("RPTCHG"));

		
		/**
		 * @author a-3447 for AirNZ CR -175 Ends
		 */		



		// gpaReportingClaimDetailsVO.setAssignedDate(new LocalDa)

		//gpaReportingClaimDetailsVO.setResolvedDate()
		gpaReportingClaimDetailsVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
		if(rs.getTimestamp("LSTUPDTIM")!=null){
			gpaReportingClaimDetailsVO.setLastUpdatedime(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
		}
		log.exiting("GPAReportingClaimDetailsMapper","map");
		return gpaReportingClaimDetailsVO;
	}

}
