/*
 * UnaccountedDispachesTotalMapper.java Created on Dec12, 2008
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 *
 * @author a-2107
 *
 */
public class UnaccountedDispachesTotalMapper implements Mapper<UnaccountedDispatchesVO> {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "UnaccountedDispachesTotalMapper";

	/**
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public UnaccountedDispatchesVO map(ResultSet rs1) throws SQLException {
		log.entering(CLASS_NAME, "map");
		UnaccountedDispatchesVO unaccountedDispatchesVO = new UnaccountedDispatchesVO();
		Money presentWtCharge = null;
			unaccountedDispatchesVO.setNoOfDispatches(String.valueOf(rs1.getInt("TOTALNOOFDESP")));
		
		try {
				log.log(Log.FINE, "TOTALWGTCHG", rs1.getDouble("TOTALWGTCHG"));
				presentWtCharge = CurrencyHelper.getMoney("NZD");
				presentWtCharge.setAmount(rs1.getDouble("TOTALWGTCHG"));					
				unaccountedDispatchesVO.setPropratedAmt(presentWtCharge);
				unaccountedDispatchesVO.setCompanyCode(rs1.getString("CMPCOD"));
				unaccountedDispatchesVO.setRate(rs1.getDouble("RAT"));
				unaccountedDispatchesVO.setWeight(rs1.getDouble("WGT"));
				unaccountedDispatchesVO.setCurrency(rs1.getString("CURRENCY"));
				unaccountedDispatchesVO.setReasonCode(rs1.getString("REASONCODE"));
				Date acceptedDate = rs1.getDate("ACPDAT");
				
				if(acceptedDate != null){
					unaccountedDispatchesVO.setAcceptedDate(
							new LocalDate(LocalDate.NO_STATION, Location.NONE,acceptedDate));
				}
			} catch (CurrencyException e) {
			   throw new SQLException();
			}	
		
		return unaccountedDispatchesVO;
	}

}








