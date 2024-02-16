/*
 * AirlineExceptionsMapper.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2407
 * 
 * Mapper for getting  Airline Exceptions
 */

public class AirlineExceptionsMapper implements Mapper<AirlineExceptionsVO> {

	private Log log = LogFactory.getLogger("AirlineExceptionsMapper");

	//private static final String BLANK = "";
	private static final String CURRENCY = "USD";
	private static final double ZERO = 0.0;

	/**
	 * @param rs
	 * @throws SQLException
	 */

	public AirlineExceptionsVO map(ResultSet rs) throws SQLException {

		log.entering("AirlineExceptionsMapper---------", "Map Method");

		String statusFlag = "N";

		AirlineExceptionsVO airlineExceptionsVO = new AirlineExceptionsVO();

		airlineExceptionsVO.setCompanyCode(rs.getString("CMPCOD"));
		airlineExceptionsVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
		airlineExceptionsVO.setAirlineCode(rs.getString("ARLCOD"));
		airlineExceptionsVO.setExceptionCode(rs.getString("EXPCOD"));
		airlineExceptionsVO.setSerialNumber(rs.getInt("SERNUM"));
		airlineExceptionsVO.setInvoiceNumber(rs.getString("INVNUM"));
		airlineExceptionsVO.setOrgOfficeOfExchange(rs.getString("ORGEXGOFC"));
		airlineExceptionsVO.setDestOfficeOfExchange(rs.getString("DSTEXGOFC"));
		airlineExceptionsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		airlineExceptionsVO.setMailSubClass(rs.getString("MALSUBCLS"));
		airlineExceptionsVO.setYear(rs.getInt("YER"));
		airlineExceptionsVO.setReceptacleSerialNumber(rs.getString("RSN"));
		airlineExceptionsVO.setHighestNumberIndicator(rs.getString("HSN"));
		airlineExceptionsVO.setRegisteredIndicator(rs.getString("REGIND"));
		airlineExceptionsVO.setClearancePeriod(rs.getString("CLRPRD"));
		airlineExceptionsVO.setDespatchSerNo(rs.getString("DSN"));
		airlineExceptionsVO.setProvRate(rs.getDouble("PVNRAT"));
		airlineExceptionsVO.setProvWeight(rs.getDouble("PVNWGT"));
		airlineExceptionsVO.setRptdWeight(rs.getDouble("RPDWGT"));
		airlineExceptionsVO.setMemCode(rs.getString("MEMCOD"));
		airlineExceptionsVO.setAssigneeCode(rs.getString("ASGCOD"));
		//Added By Deepthi
		airlineExceptionsVO.setProvAmt(rs.getDouble("PVNAMTLSTCUR"));   //Modified by A-7929 as part of ICRD-265471
		airlineExceptionsVO.setReportedRate(rs.getDouble("RPDRAT"));
		airlineExceptionsVO.setRptdWeight(rs.getDouble("RPDWGT"));
		airlineExceptionsVO.setReportedAmt(rs.getDouble("RPDAMTLSTCUR"));  //Modified by A-7929 as part of ICRD-265471
		airlineExceptionsVO.setContactCurrency(CURRENCY);
		airlineExceptionsVO.setBillingCurrency(CURRENCY);
		airlineExceptionsVO.setMemStaus(rs.getString("MEMSTA"));
		
		airlineExceptionsVO.setRejectedAmt(ZERO);
		airlineExceptionsVO.setBillingBasis(rs.getString("BLGBAS"));
		airlineExceptionsVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
		airlineExceptionsVO.setCsgSeqNum(rs.getInt("CSGSEQNUM"));
		airlineExceptionsVO.setPoaCode(rs.getString("POACOD"));
		airlineExceptionsVO.setRemark(rs.getString("RMK"));
		airlineExceptionsVO.setExceptionStatus(rs.getString("EXPSTA"));
		LocalDate assgnDate = rs.getDate("ASGDAT") == null ? null
				: new LocalDate(LocalDate.NO_STATION, Location.NONE, rs
						.getDate("ASGDAT"));

		airlineExceptionsVO.setAssignedDate(assgnDate);

		airlineExceptionsVO.setOperationalFlag(statusFlag);

		airlineExceptionsVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
		if (rs.getTimestamp("LSTUPDTIM") != null) {
			airlineExceptionsVO.setLastUpdatedTime(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getTimestamp("LSTUPDTIM")));
		}
		airlineExceptionsVO.setOrigin(rs.getString("ORGCOD"));
		airlineExceptionsVO.setDestination(rs.getString("DSTCOD"));
		airlineExceptionsVO.setSeqNumber(rs.getInt("SEQNUM"));
		airlineExceptionsVO.setSectorFrom(rs.getString("SECFRM"));
		airlineExceptionsVO.setSectoTo(rs.getString("SECTOO"));
		log.exiting("AirlineExceptionsMapper", "Map Method");
		log.log(Log.FINE, "Map Method", airlineExceptionsVO);
		return airlineExceptionsVO;
	}

}
