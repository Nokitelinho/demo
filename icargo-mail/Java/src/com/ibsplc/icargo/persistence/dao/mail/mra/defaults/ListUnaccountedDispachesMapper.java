/*
 * ListUnaccountedDispachesMapper.java Created on Aug26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

/**
 *
 * @author a-2107
 *
 */
public class ListUnaccountedDispachesMapper implements Mapper<UnaccountedDispatchesDetailsVO> {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ListUnaccountedDispaches";

	/**
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public UnaccountedDispatchesDetailsVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		UnaccountedDispatchesDetailsVO unaccountedDispatchesDetailsVO = new UnaccountedDispatchesDetailsVO();

		if (rs.getString("DSN") != null) {
			unaccountedDispatchesDetailsVO.setDsn(rs.getString("DSN"));
		}
		if (rs.getString("ORIGIN") != null) {
			unaccountedDispatchesDetailsVO.setOrigin(rs.getString("ORIGIN"));
		}
		if (rs.getString("DESTN") != null) {
			unaccountedDispatchesDetailsVO.setDestination(rs.getString("DESTN"));
		}
		if (rs.getString("FLIGHTNO") != null) {
			unaccountedDispatchesDetailsVO.setFlightNumber(rs.getString("FLIGHTNO"));
		}
		if (rs.getString("FLTDATE") != null) {
			unaccountedDispatchesDetailsVO.setFlightDate(rs.getString("FLTDATE"));
		}
		if (rs.getString("BILLTYPE") != null) {
			unaccountedDispatchesDetailsVO.setBillType(rs.getString("BILLTYPE"));
		}
		if (rs.getString("CURRENCY") != null) {
			unaccountedDispatchesDetailsVO.setCurrency(rs.getString("CURRENCY"));
		}
		if (rs.getString("RATE") != null) {
			unaccountedDispatchesDetailsVO.setRate(TextFormatter.formatDouble(rs.getDouble("RATE"),4));
		}
		if (rs.getString("RATE") != null) {
			unaccountedDispatchesDetailsVO.setRates(rs.getDouble("RATE"));
		}
		if (rs.getString("CMPCOD") != null) {
			unaccountedDispatchesDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		}
		if (rs.getString("WGT") != null) {
			unaccountedDispatchesDetailsVO.setWeight(rs.getDouble("WGT"));
		}
		if (rs.getString("MALCTGCOD") != null) {
			unaccountedDispatchesDetailsVO.setMailCategory(rs.getString("MALCTGCOD"));
		}
		if (rs.getString("MALSUBCLS") != null) {
			unaccountedDispatchesDetailsVO.setMailSubClass(rs.getString("MALSUBCLS"));
			Character malClass=unaccountedDispatchesDetailsVO.getMailSubClass().charAt(0);
			unaccountedDispatchesDetailsVO.setMailClass(malClass.toString());
		}
		
		Date acceptedDate = rs.getDate("ACPDAT");
		if(acceptedDate != null){
			unaccountedDispatchesDetailsVO.setAcceptedDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE,acceptedDate));
		}else{
			unaccountedDispatchesDetailsVO.setAcceptedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,false));
		}
		if (rs.getString("AMOUNT") != null) {
			Money presentCharge = null;
			try{
			if(rs.getString("CURRENCY")!= null){
			presentCharge = CurrencyHelper.getMoney(rs.getString("CURRENCY"));
			presentCharge.setAmount(rs.getDouble("AMOUNT"));
			unaccountedDispatchesDetailsVO.setAmount(presentCharge);
			}
			}catch(CurrencyException e){
				throw new SQLException();
			}

		}
		if (rs.getString("SECFRM") != null) {
			unaccountedDispatchesDetailsVO.setSectorFrom(rs.getString("SECFRM"));
		}
		if (rs.getString("SECTO") != null) {
			unaccountedDispatchesDetailsVO.setSectorTo(rs.getString("SECTO"));
		}
		if (rs.getString("PRORATEDAMOUNTINNZD") != null) {
			Money proratedAmt = null;
			try{

				proratedAmt = CurrencyHelper.getMoney("NZD");

			}catch(CurrencyException e){
				throw new SQLException();
			}
			proratedAmt.setAmount(rs.getDouble("PRORATEDAMOUNTINNZD"));
			unaccountedDispatchesDetailsVO.setProratedAmt(proratedAmt);
		}
		if (rs.getString("PRORATEDAMOUNTINCTR") != null) {
			Money proratedAmtinCtr = null;
			try{

				if(rs.getString("CURRENCY")!= null){
					proratedAmtinCtr = CurrencyHelper.getMoney(rs.getString("CURRENCY"));
					proratedAmtinCtr.setAmount(rs.getDouble("PRORATEDAMOUNTINCTR"));
					unaccountedDispatchesDetailsVO.setProratedAmtinCtrcur(proratedAmtinCtr);

			}
			}catch(CurrencyException e){
				throw new SQLException();
			}
			
		}
		if (rs.getString("REASONCODE") != null) {
			unaccountedDispatchesDetailsVO.setReason(rs.getString("REASONCODE"));
		}
		if (rs.getString("BLGBAS") != null) {
			unaccountedDispatchesDetailsVO.setBilBase(rs.getString("BLGBAS"));
		}
		if (rs.getString("CSGSEQNUM") != null) {
			unaccountedDispatchesDetailsVO.setDsnSqnNo(rs.getString("CSGSEQNUM"));
		}
		if (rs.getString("CSGDOCNUM") != null) {
			unaccountedDispatchesDetailsVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
		}
		if (rs.getString("POACOD") != null) {
			unaccountedDispatchesDetailsVO.setPostalCde(rs.getString("POACOD"));
		}
		if (rs.getString("SERNUM") != null) {
			unaccountedDispatchesDetailsVO.setSerialNo(rs.getInt("SERNUM"));
		}
		if (rs.getString("SEGSERNUM") != null) {
			unaccountedDispatchesDetailsVO.setSegmentSerialNo(rs.getInt("SEGSERNUM"));
		}
		if (rs.getString("FLTSEQNUM") != null) {
			unaccountedDispatchesDetailsVO.setFlightSeqNo(rs.getInt("FLTSEQNUM"));
		}
		if (rs.getString("LSTUPDUSR") != null) {
			unaccountedDispatchesDetailsVO.setLastUpdateduser(rs.getString("LSTUPDUSR"));
		}
		log.exiting(CLASS_NAME, "map");
		return unaccountedDispatchesDetailsVO;
	}

}








