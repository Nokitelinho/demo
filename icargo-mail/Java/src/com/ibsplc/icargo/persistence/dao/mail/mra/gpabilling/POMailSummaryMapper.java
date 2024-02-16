/**
 *  POMailSummaryMapper.java Created on May 11, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.POMailSummaryDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class POMailSummaryMapper implements Mapper<POMailSummaryDetailsVO>{

	private Log log = LogFactory.getLogger("MRA GPABILLING");
	public POMailSummaryDetailsVO map(ResultSet rs)
	throws SQLException {
		Double dueAmount;
		Money dueAmountInBillingCurr= null;
		log.entering("POMailSummaryMapper","map");
		POMailSummaryDetailsVO poMailSummaryDetailsVO=new POMailSummaryDetailsVO();
		poMailSummaryDetailsVO.setSubClassCode(rs.getString("SUBCLSGRP"));
		poMailSummaryDetailsVO.setMailType(rs.getString("MALCTGCOD"));

		poMailSummaryDetailsVO.setOriginStation(rs.getString("ORGCOD"));
		poMailSummaryDetailsVO.setDestinationStation(rs.getString("DSTCOD"));
		poMailSummaryDetailsVO.setFlightNumber(rs.getString("FLTNUMS"));
		if(rs.getDate("FLTDAT")!=null){
			poMailSummaryDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("FLTDAT") ));
		}		
		poMailSummaryDetailsVO.setWeight(rs.getDouble("UPDWGT"));
		poMailSummaryDetailsVO.setDistance(rs.getDouble("CTYPIRDIS"));
		poMailSummaryDetailsVO.setBillingCurrency(rs.getString("CTRCURCOD"));
		poMailSummaryDetailsVO.setRate(rs.getDouble("BLGRAT"));
		try{
			if(poMailSummaryDetailsVO.getBillingCurrency()!=null && poMailSummaryDetailsVO.getBillingCurrency().trim().length()>0){
				Money amount=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
				amount.setAmount(rs.getDouble("WGTCHG"));
				poMailSummaryDetailsVO.setAmount(amount);
			}			
		}
		catch(CurrencyException e){
			log.log(Log.FINE,  "CurrencyException");
		}
		poMailSummaryDetailsVO.setServiceTax(rs.getDouble("SRVTAX"));
		poMailSummaryDetailsVO.setTds(rs.getDouble("TDS"));		
		try{
			if(poMailSummaryDetailsVO.getBillingCurrency()!=null && poMailSummaryDetailsVO.getBillingCurrency().trim().length()>0){
				Money payableAmount=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
				payableAmount.setAmount(rs.getDouble("NETAMT"));
				poMailSummaryDetailsVO.setPayableAmount(payableAmount);
			}			
			if(poMailSummaryDetailsVO.getBillingCurrency()!=null && poMailSummaryDetailsVO.getBillingCurrency().trim().length()>0){
				Money chequeAmount=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
				chequeAmount.setAmount(rs.getDouble("STLAMT"));
				poMailSummaryDetailsVO.setChequeAmount(chequeAmount);
			}				
		}
		catch(CurrencyException e){
			log.log(Log.FINE,  "CurrencyException");
		}
		poMailSummaryDetailsVO.setChequeNumber(rs.getString("CHQNUM"));
		if(rs.getDate("CHQDAT")!=null){
			poMailSummaryDetailsVO.setChequeDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("CHQDAT") ));
		}

		if(rs.getDate("STLDAT")!=null){
			poMailSummaryDetailsVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("STLDAT") ));
		}
		poMailSummaryDetailsVO.setRemarks(rs.getString("RMK"));
		if(poMailSummaryDetailsVO.getPayableAmount()!=null && poMailSummaryDetailsVO.getChequeAmount()!=null ){
			dueAmount=poMailSummaryDetailsVO.getPayableAmount().getAmount()-poMailSummaryDetailsVO.getChequeAmount().getAmount();
			try {
				if(poMailSummaryDetailsVO.getBillingCurrency()!= null){

					dueAmountInBillingCurr = CurrencyHelper.getMoney(poMailSummaryDetailsVO.getBillingCurrency());
					if(dueAmount<0){
						dueAmountInBillingCurr.setAmount(0);
					}
					else{
						dueAmountInBillingCurr.setAmount(dueAmount);

					}

				}

			} catch (CurrencyException e) {
				// TODO Auto-generated catch block				
			}

		}

		poMailSummaryDetailsVO.setDueAmount(dueAmountInBillingCurr);

		return poMailSummaryDetailsVO;

	}

}
