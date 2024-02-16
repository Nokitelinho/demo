/*
 * GPAReportingDetailsMultiMapper.java Created on Feb 22, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFlightDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.PageAwareMultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1945
 */

/*
 * 
 * Revision History Version Date Author Description 0.1 Feb 22, 2007 A-1945
 * Initial draft
 * 
 */
public class GPAReportingDetailsMultiMapper extends
		PageAwareMultiMapper<GPAReportingDetailsVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING MRA GPAREPORTING");

	/**
	 * @param rs
	 * @return gpaReportingDetails
	 * @throws SQLException
	 */
	public List<GPAReportingDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering("GPAReportingDetailsMultiMapper", "map");
		List<GPAReportingDetailsVO> gpaReportingDetails = new ArrayList<GPAReportingDetailsVO>();
		GPAReportingDetailsVO gpaReportingDetailsVO = null;
		String gpaKey = null;
		String gpaPrev = null;
		while (hasNext()) {
			gpaKey = new StringBuilder().append(rs.getString("CMPCOD")).append(
					rs.getString("BLGBAS")).append(rs.getString("POACOD"))
					.append(rs.getString("BILIDR")).append(rs.getInt("SERNUM")).toString();
			if (!gpaKey.equals(gpaPrev)) {
				gpaPrev = gpaKey;
				gpaReportingDetailsVO = new GPAReportingDetailsVO();
				gpaReportingDetails.add(gpaReportingDetailsVO);
				gpaReportingDetailsVO
						.setGpaReportingFlightDetailsVOs(new ArrayList<GPAReportingFlightDetailsVO>());
				try{
					gpaReportingDetailsVO.setCurrencyCode(rs.getString("STLCURCOD"));
				if(rs.getString("STLCURCOD")!=null && rs.getString("STLCURCOD").trim().length()>0){
					Money amt=CurrencyHelper.getMoney(rs.getString("STLCURCOD"));
					amt.setAmount(rs.getDouble("AMT"));
				gpaReportingDetailsVO.setAmount(amt);
				Money totalAmt=CurrencyHelper.getMoney(rs.getString("STLCURCOD"));
				totalAmt.setAmount(rs.getDouble("TOTAMT"));
				gpaReportingDetailsVO.setTotal(totalAmt);
				}
				}
				catch(CurrencyException e){
					e.getErrorCode();
				}
				gpaReportingDetailsVO.setCompanyCode(rs.getString(""));
				if (rs.getDate("DSNDAT") != null) {
					gpaReportingDetailsVO.setDsnDate(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, rs
									.getDate("DSNDAT")));
				}
				gpaReportingDetailsVO.setDsnNumber(rs.getString("DSN"));
				gpaReportingDetailsVO.setBasistype(rs.getString("BASTYP"));
				gpaReportingDetailsVO.setCountryCode(rs.getString("CNTCOD"));
				gpaReportingDetailsVO
						.setMailCategory(rs.getString("MALCTGCOD"));
				gpaReportingDetailsVO
						.setMailSubClass(rs.getString("MALSUBCLS"));
				gpaReportingDetailsVO.setActualMailSubClass(rs
						.getString("SUBCLSGRP"));
				gpaReportingDetailsVO.setNoOfMailBags(rs.getInt("NUMMALBAG"));
				gpaReportingDetailsVO.setOriginOfficeExchange(rs
						.getString("ORGEXGOFC"));
				gpaReportingDetailsVO.setDestinationOfficeExchange(rs
						.getString("DSTEXGOFC"));
				gpaReportingDetailsVO.setPoaCode(rs.getString("POACOD"));
				gpaReportingDetailsVO.setRate(rs.getDouble("RAT"));
				if (rs.getDate("REPPRDFRM") != null) {
					gpaReportingDetailsVO.setReportingFrom(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, rs
									.getDate("REPPRDFRM")));
				}
				gpaReportingDetailsVO
						.setReportingStatus(rs.getString("MALSTA"));
				if (rs.getDate("REPPRDTO") != null) {
					gpaReportingDetailsVO.setReportingTo(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, rs
									.getDate("REPPRDTO")));
				}
				gpaReportingDetailsVO.setSequenceNumber(rs.getInt("SERNUM"));
				gpaReportingDetailsVO.setTax(rs.getDouble("TAX"));
				
				gpaReportingDetailsVO.setWeight(rs.getDouble("WGT"));
				gpaReportingDetailsVO.setBillingBasis(rs.getString("BLGBAS"));
				gpaReportingDetailsVO.setBillingIdentifier(rs.getString("BILIDR"));
				gpaReportingDetailsVO.setReportingFromString(rs
						.getString("REPPRDFRMSTR"));
				gpaReportingDetailsVO.setReportingToString(rs
						.getString("REPPRDTOOSTR"));
				gpaReportingDetailsVO.setYear(rs.getString("YER"));
				gpaReportingDetailsVO.setHighestNumberedReceptacle(rs
						.getString("HSN"));
				gpaReportingDetailsVO.setRegisteredOrInsuredIndicator(rs
						.getString("REGIND"));
				gpaReportingDetailsVO.setReceptacleSerialNumber(rs
						.getString("RSN"));
				gpaReportingDetailsVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
				if(rs.getTimestamp("LSTUPDTIM")!=null){
					gpaReportingDetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
			        }
				increment();
			}
			if (rs.getInt("FLTSEQNUM") > 0) {
				GPAReportingFlightDetailsVO gpaReportingFlightDetailsVO = new GPAReportingFlightDetailsVO();
				gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs().add(
						gpaReportingFlightDetailsVO);
				gpaReportingFlightDetailsVO.setFlightCarrierCode(rs
						.getString("FLTCARCOD"));
				gpaReportingFlightDetailsVO.setFlightNumber(rs
						.getString("FLTNUM"));
				gpaReportingFlightDetailsVO.setCarriageFrom(rs
						.getString("CARFRM"));
				gpaReportingFlightDetailsVO.setCarriageTo(rs
						.getString("CARTOO"));
				gpaReportingFlightDetailsVO.setFlightSequenceNumber(rs
						.getInt("FLTSEQNUM"));
			}
		}
		log.exiting("GPAReportingDetailsMultiMapper", "map");
		return gpaReportingDetails;
	}
}
