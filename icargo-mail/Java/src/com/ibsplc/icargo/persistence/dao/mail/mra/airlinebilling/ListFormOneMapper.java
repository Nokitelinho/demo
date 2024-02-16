/*
 * ListFormOneMapper.java Created on July 18, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author A-3434
 *
 */
public class ListFormOneMapper implements Mapper<FormOneVO> {

	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING");


	    /**@author A-3434
	     * @param rs
	     * @throws SQLException
	     * @return FormOneVO
	     * implemented method for mapping result set with the vo
	     */
	    public FormOneVO map(ResultSet rs) throws SQLException {
	    	
	        log.entering("ListFormOneMapper","map");
	        FormOneVO  formOneVo = null;
	        try {
	            formOneVo = new FormOneVO();
	            formOneVo.setAirlineCode(rs.getString("ARLCOD"));
	            formOneVo.setAirlineIdr(rs.getInt("ARLIDR"));
	            formOneVo.setAirlineNumber(rs.getString("ARLNUM"));
	            formOneVo.setClassType(rs.getString("CLSTYP"));
	            formOneVo.setExchangeRateBillingCurrency(rs.getDouble("EXGRATLSTBLGCUR"));
	            
	            String billingCurrency = rs.getString("BLGCURCOD");
	            if((!("".equals(billingCurrency)))||
	            billingCurrency!=null){
	            formOneVo.setBillingCurrency(billingCurrency);
	            
	             
	             //BLGAMT FOR FORM1
	            
	            Money billingTotal = CurrencyHelper.getMoney(billingCurrency);
	            billingTotal.setAmount(rs.getDouble("TOTAMTBLGCUR"));
	            formOneVo.setBillingTotalAmt(billingTotal);
	            }
	            String listingCurrency = rs.getString("LSTCURCOD");
	            if((!("".equals(listingCurrency)))||
	            		listingCurrency!=null){
	            formOneVo.setListingCurrency(listingCurrency);
	            
	             //BLGAMT FOR FORM1
	            
	            Money listingTotal = CurrencyHelper.getMoney(listingCurrency);
	            listingTotal.setAmount(rs.getDouble("TOTAMTLSTCUR"));
	            formOneVo.setListingTotalAmt(listingTotal);
	            }
	        }
	     catch(CurrencyException e) {
	        throw new SQLException(e.getErrorCode());
	    }
	            return formOneVo;
	    }
	}

