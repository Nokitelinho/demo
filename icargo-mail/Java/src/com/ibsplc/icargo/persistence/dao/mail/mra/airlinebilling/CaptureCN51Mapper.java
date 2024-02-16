/*
 * CaptureCN51Mapper.java Created on Jan 13, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3429
 */
public class CaptureCN51Mapper implements Mapper<AirlineCN51DetailsVO> {

	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING");

	private static final String CLASS_NAME = "CaptureCN51Mapper";

	/**
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public AirlineCN51DetailsVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		AirlineCN51DetailsVO airlineCN51DetailsVO = new AirlineCN51DetailsVO();
		airlineCN51DetailsVO.setCompanycode(rs.getString("CMPCOD"));
		airlineCN51DetailsVO.setAirlineidr(rs.getInt("ARLIDR"));
		airlineCN51DetailsVO.setInterlinebillingtype(rs.getString("INTBLGTYP"));
		airlineCN51DetailsVO.setSequenceNumber(rs.getInt("SEQNUM"));
		airlineCN51DetailsVO.setClearanceperiod(rs.getString("CLRPRD"));
		airlineCN51DetailsVO.setInvoicenumber(rs.getString("INVNUM"));
		airlineCN51DetailsVO.setCarriagefrom(rs.getString("CARFRM"));
		airlineCN51DetailsVO.setCarriageto(rs.getString("CARTOO"));
		airlineCN51DetailsVO.setMailcategory(rs.getString("MALCTGCOD"));
		airlineCN51DetailsVO.setMailsubclass(rs.getString("SUBCLSGRP"));
		//airlineCN51DetailsVO.setListingcurrencycode(rs.getString("CURCOD")); 
		airlineCN51DetailsVO.setListingcurrencycode(rs.getString("LSTCURCOD"));  //Modified by A-7929 as part of ICRD-265471
		airlineCN51DetailsVO.setTotalAmount(rs.getDouble("TOTDTLCRTAMT"));
		/*
		 * if(rs.getString("MALSUBCLS").startsWith("C")){
		 * airlineCN51DetailsVO.setMailsubclass(MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_CP);
		 * }else{
		 * airlineCN51DetailsVO.setMailsubclass(MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_LC); }
		 */
		airlineCN51DetailsVO.setTotalweight(rs.getDouble("TOTWGT"));
		try {
			if (rs.getString("LSTCURCOD") != null) {   //Modified by A-7929 as part of ICRD-265471
				Money totChg = CurrencyHelper.getMoney(rs.getString("LSTCURCOD"));
				totChg.setAmount(rs.getDouble("TOTDTLCRTAMT"));
				airlineCN51DetailsVO.setTotalamountincontractcurrency(totChg);
			}
		} catch (CurrencyException currencyException) {
			log.log(Log.INFO, "CurrencyException found");
		}
		airlineCN51DetailsVO.setApplicablerate(rs.getDouble("APLRAT"));
		return airlineCN51DetailsVO;
	}
}
