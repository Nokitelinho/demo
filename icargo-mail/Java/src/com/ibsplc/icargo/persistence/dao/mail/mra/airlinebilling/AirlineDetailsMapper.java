/*
 * AirlineDetailsMapper.java Created on July 22, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;

/**
 * @author A-3434
 *
 */
public class AirlineDetailsMapper implements Mapper<AirlineForBillingVO> {

    /**
     * @param resultSet
     * @return AirlineForBillingVO
     * @exception SQLException
     *
     */

    public AirlineForBillingVO map(ResultSet resultSet) throws SQLException {
        AirlineForBillingVO airlineForBillingVO = new AirlineForBillingVO();
        
        
        Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
        log.log(Log.FINE, "Inside Mapper Class AirlineDetailsMapper");
        try {
            airlineForBillingVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
            airlineForBillingVO.setClearancePeriod(resultSet.getString("CLRPRD"));
            airlineForBillingVO.setCompanyCode(resultSet.getString("CMPCOD"));
            airlineForBillingVO.setAirlineCode(resultSet.getString("ARLCOD"));
            airlineForBillingVO.setAirlineNumber(resultSet.getString("ARLNUM"));
            airlineForBillingVO.setAirLineName(resultSet.getString("ARLNAM"));

            
           // billingCurrency = resultSet.getString("BLGCUR");
            airlineForBillingVO.setBillingCurrency("USD");
            
             
             //MISBLG FOR FORM2
            
            Money miscBillingTotal = CurrencyHelper.getMoney(airlineForBillingVO.getBillingCurrency());
            miscBillingTotal.setAmount(resultSet.getDouble("OUTTOTAMTLSTCUR"));  //Modified by A-7929 as part of ICRD-265471
            airlineForBillingVO.setMiscBillingTotal(miscBillingTotal);
            Money miscAmountInBilling = CurrencyHelper.getMoney(airlineForBillingVO.getBillingCurrency());
            miscAmountInBilling.setAmount(resultSet.getDouble("OUTMISAMTLSTCUR"));  //Modified by A-7929 as part of ICRD-265471
            airlineForBillingVO.setMiscAmountInBilling(miscAmountInBilling);
            
			
			airlineForBillingVO.setLastUpdateUser(resultSet.getString("LSTUPDUSR"));
			if(resultSet.getTimestamp("LSTUPDTIM")!= null){
				airlineForBillingVO.setLastUpdateTime(new LocalDate
				(LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
			}
			
			
			

        } catch(CurrencyException e) {
            throw new SQLException(e.getErrorCode());
        }
        log.log(Log.FINE, "Mapper Class Completed>>AirlineDetailsMapper");
        return airlineForBillingVO;
    }

}
