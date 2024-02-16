/*
 * GPABillingDtlsMapper.java Created on Jl 10,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

/**
 * @author A-2391
 *
 */

public class GPABillingDtlsMapper implements Mapper<GPABillingDetailsVO> {

	private static final String CLASS_NAME = "GPABillingDtlsMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");
	private static final String CURRENCY = "NZD";
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
    public GPABillingDetailsVO map(ResultSet rs) throws SQLException {
    	log.entering(CLASS_NAME,"Mapper");

    	GPABillingDetailsVO gPABillingDetailsVO=new GPABillingDetailsVO();

    	gPABillingDetailsVO.setDsn(rs.getString("DSN"));
    	//modified for ICRD-257542
    	gPABillingDetailsVO.setBillingBase(rs.getString("BLGBAS"));//Added by A-5931
    	gPABillingDetailsVO.setBillingStatus(rs.getString("BLGSTA"));
    	gPABillingDetailsVO.setApplicableRate(rs.getDouble("APLRAT"));
    	
    	 Money amount;		 
		 if(rs.getString("CURCOD")!=null){
		 try {
				amount = CurrencyHelper.getMoney(rs.getString("CURCOD"));
				amount.setAmount(rs.getDouble("WGTCHG"));
				gPABillingDetailsVO.setAmountBillable(amount);	    						
			} catch (CurrencyException e) {
				// TODO Auto-generated catch block
				e.getErrorCode();
			} 
		 }
    	
    	gPABillingDetailsVO.setYear(rs.getInt("YER"));
    	gPABillingDetailsVO.setRemarks(rs.getString("RMK"));
    	gPABillingDetailsVO.setCcaNo(rs.getString("CCAREFNUM"));	
    	//Modified for ICRD-153772 starts
    	if(rs.getDate("BILPRDFRM")!=null){//modified for icrd-257974
    	LocalDate lDateFrom=new  LocalDate(LocalDate.NO_STATION,Location.NONE, rs.getDate("BILPRDFRM"));
    	gPABillingDetailsVO.setBlgPrdFrm(lDateFrom.toDisplayDateOnlyFormat());
    	}
    	if(rs.getDate("BILPRDTOO")!=null){//modified for icrd-257974
    	LocalDate lDateToo=new  LocalDate(LocalDate.NO_STATION,Location.NONE, rs.getDate("BILPRDTOO"));
    	gPABillingDetailsVO.setBlgPrdTo(lDateToo.toDisplayDateOnlyFormat());
    	}
    	//Modified for ICRD-153772 ends
    	gPABillingDetailsVO.setInvoiceNumber(rs.getString("INVNUM"));
    	gPABillingDetailsVO.setGpaName(rs.getString("POANAM"));
    	if(rs.getDate("BLDDAT")!=null){	
  		Date date=TimeConvertor.dateOnly((rs.getDate("BLDDAT")));
  		String invdat=TimeConvertor.toNormalString(date);
    	gPABillingDetailsVO.setInvDate(invdat);
        	}
    	gPABillingDetailsVO.setGpaCode(rs.getString("GPACOD"));
    	gPABillingDetailsVO.setCurrencyCode(rs.getString("CURCOD"));
    	gPABillingDetailsVO.setInvStatus(rs.getString("INVSTA"));
    	
    	//gPABillingDetailsVO.setSurchargeAmtBillable(rs.getDouble("GPACOD"));
    	//gPABillingDetailsVO.setGrossAmount(rs.getDouble("WGTCHG")+rs.getDouble("OTHCHG"));
    	//Added for ICRD-164740 starts
    	Money grsamt;		 
		 if(rs.getString("CURCOD")!=null){
		 try {
			 	grsamt = CurrencyHelper.getMoney(rs.getString("CURCOD"));
			 	grsamt.setAmount(rs.getDouble("WGTCHG")+rs.getDouble("OTHCHG"));
				gPABillingDetailsVO.setGrossAmount(grsamt.getRoundedAmount());	    						
			} catch (CurrencyException e) {
				log.log(Log.INFO,"CurrencyException found");
			} 
		 }
		//Added for ICRD-164740 ends
    	//gPABillingDetailsVO.setTaxAmount();
    	gPABillingDetailsVO.setNetAmount(rs.getDouble("NETAMT"));
    	try{
    	if(rs.getString("CURCOD")!=null && rs.getString("CURCOD").trim().length()>0){
			Money surchargeAmt=CurrencyHelper.getMoney(rs.getString("CURCOD"));
			surchargeAmt.setAmount(rs.getDouble("OTHCHG"));
			gPABillingDetailsVO.setSurchargeAmtBillable(surchargeAmt);
			
			Money taxAmount=CurrencyHelper.getMoney(rs.getString("CURCOD"));
			taxAmount.setAmount(rs.getDouble("SRVTAX"));
			gPABillingDetailsVO.setTaxAmount(taxAmount);
			
		}
    	}catch(CurrencyException currencyException){
    		log.log(Log.FINE, currencyException.getMessage());
    	}
    
        return gPABillingDetailsVO;
    }

}
