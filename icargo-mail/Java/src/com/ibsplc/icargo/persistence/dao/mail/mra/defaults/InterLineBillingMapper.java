/*
 * InterLineBillingMapper.java Created on Aug 8,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingDetailVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * @author A-3434
 *
 */
public class InterLineBillingMapper implements Mapper<AirlineBillingDetailVO>{
	private static final String CURRENCY = "NZD";
	public AirlineBillingDetailVO map(ResultSet RS) throws SQLException {
		
	
		AirlineBillingDetailVO airlineBillingDetailVO =new AirlineBillingDetailVO();
		
		airlineBillingDetailVO.setOriginOfficeOfExchange(RS.getString("SECFRM"));
		airlineBillingDetailVO.setDestinationOfficeOfExchange(RS.getString("SECTOO"));
		airlineBillingDetailVO.setCarrierCode(RS.getString("FLTCARIDR"));
		airlineBillingDetailVO.setInterLineBillingType(RS.getString("INTBLGTYP"));
		airlineBillingDetailVO.setClearancePeriod(RS.getString("CLRPRD"));
		airlineBillingDetailVO.setInvoicenumber(RS.getString("INVNUM"));
			
		if(RS.getDate("DSNDAT")!= null){
			airlineBillingDetailVO.setInvoiceDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,RS.getDate("DSNDAT")));
		}
		
		airlineBillingDetailVO.setMailSubclass(RS.getString("SUBCLSGRP"));
		airlineBillingDetailVO.setReceivedWeight(RS.getDouble("TOTWGT"));
		airlineBillingDetailVO.setApplicablerate(RS.getDouble("APLRAT"));
		
		Money amount;		 
		 
		 try {
				amount = CurrencyHelper.getMoney(CURRENCY);
				amount.setAmount(RS.getDouble("BLDAMTLSTUR"));   //Modified as part of ICRD-265471
				airlineBillingDetailVO.setBillableAmount(amount);	    						
			} catch (CurrencyException e) {
				// TODO Auto-generated catch block
				e.getErrorCode();
			}		
		
		airlineBillingDetailVO.setBillingStatus(RS.getString("MALSTA"));
		return airlineBillingDetailVO;

	
	}
	
	
 }