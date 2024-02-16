/*
 * CaptureInvoiceMapper.java Created on July 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447
 * CaptureInvoiceMapper for CaptureInvoice Screen
 *
 * Revision History
 *
 * Version  Date  Author  Description
 *
 * 0.1   7-8-08   Muralee Initial draft
 */
public class CaptureInvoiceMapper implements Mapper<AirlineCN51SummaryVO> {
	private Log log = LogFactory.getLogger("--MRA---AIRLINEBILLING---");

	/**
	 * @author A-3447
	 * @param rs
	 * @throws SQLException
	 * @return FormOneVO implemented method for mapping result set with the vo
	 */
	public AirlineCN51SummaryVO map(ResultSet rs) throws SQLException {
		log.entering("CaptureInvoiceFormMapper", "--Entering---");
		AirlineCN51SummaryVO airlineCN51SummaryVO = null;
		airlineCN51SummaryVO = new AirlineCN51SummaryVO();
		airlineCN51SummaryVO.setListingCurrency(rs.getString("LISTCUR"));

		/**
		 * Currency Conversions
		 */
		try {
			//String listingCurrency = rs.getString("LSTCUR");  
			String listingCurrency = rs.getString("LSTCURCOD");  //Modified by A-7929 as part of ICRD-265471
			airlineCN51SummaryVO.setListingCurrency(listingCurrency);
			Money listingTotal = CurrencyHelper.getMoney(listingCurrency);
			//listingTotal.setAmount(rs.getDouble("NETAMT"));
			listingTotal.setAmount(rs.getDouble("NETAMTLSTCUR")); //Modified by A-7929 as part of ICRD-265471
			airlineCN51SummaryVO.setNetAmount(listingTotal);
			Money exchangeRate = CurrencyHelper.getMoney(listingCurrency);
			//exchangeRate.setAmount(rs.getDouble("EXGRAT"));   //Modified by A-7929 as part of ICRD-265471
			exchangeRate.setAmount(rs.getDouble("EXGRATCTRLST"));
			airlineCN51SummaryVO.setExchangeRate(exchangeRate);
			airlineCN51SummaryVO.setNetAmount(listingTotal);
			Money amountInUsd = CurrencyHelper.getMoney(listingCurrency);
			//amountInUsd.setAmount(rs.getDouble("AMTINUSD"));  //Modified by A-7929 as part of ICRD-265471
			amountInUsd.setAmount(rs.getDouble("NETAMTUSD"));
			airlineCN51SummaryVO.setAmountInusd(amountInUsd);
			log.log(Log.FINE, "--Currency Converted : --->",
					airlineCN51SummaryVO.getAmountInusd());

		} catch (CurrencyException e) {
			throw new SQLException(e.getErrorCode());
		}

		if (rs.getDate("INVRCPTDAT") != null) {
			airlineCN51SummaryVO.setInvRcvdate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getDate("INVRCPTDAT")));

		}
		airlineCN51SummaryVO.setTotWt(rs.getDouble("TOTWGT"));
		airlineCN51SummaryVO.setInvStatus(rs.getString("INVSTA"));
		airlineCN51SummaryVO.setInvFormstatus(rs.getString("INVFRMONESTA"));

		Timestamp upTime = rs.getTimestamp("LSTUPDTIM");

		if (upTime != null) {
			LocalDate date = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					upTime);
			airlineCN51SummaryVO.setLastUpdatedTime(date);

		}
		airlineCN51SummaryVO.setInvoiceSrcFlag(rs.getString("INVSRC"));
		airlineCN51SummaryVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
		airlineCN51SummaryVO.setCompanycode(rs.getString("CMPCOD"));
		airlineCN51SummaryVO.setAirlineidr(rs.getInt("ARLIDR"));
		airlineCN51SummaryVO.setInvoicenumber(rs.getString("INVNUM"));
		airlineCN51SummaryVO.setInterlinebillingtype(rs.getString("INTBLGTYP"));
		airlineCN51SummaryVO.setClearanceperiod(rs.getString("CLRPRD"));
		airlineCN51SummaryVO.setInvStatus(rs.getString("INVSTA"));
		airlineCN51SummaryVO.setInvFormstatus(rs.getString("INVFRMONESTA"));
		//airlineCN51SummaryVO.setAirlinecode(rs.getString("ARLCOD"));
		if (rs.getDate("INVRCPTDAT") != null) {
			airlineCN51SummaryVO.setInvRcvdate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getTimestamp("INVRCPTDAT")));

		}
		airlineCN51SummaryVO.setAirlinecode(rs.getString("ARLCOD"));
		log.log(Log.FINE, "-AirlineCN51SummaryVO  from Mapper->",
				airlineCN51SummaryVO);
		return airlineCN51SummaryVO;
	}
}
