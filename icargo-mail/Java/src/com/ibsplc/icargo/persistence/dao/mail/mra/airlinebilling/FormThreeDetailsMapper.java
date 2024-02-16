/* FormThreeDetailsMapper.java Created on Jul 28,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author a-3108
 * 
 */
public class FormThreeDetailsMapper implements MultiMapper<AirlineForBillingVO>{
	
	private Log log = LogFactory.getLogger("FormThreeDetailsMapper");

	private static final String CLASS_NAME = "FormThreeDetailsMapper";

	/**
	 * @return AirlineCN66DetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	public List<AirlineForBillingVO> map(ResultSet rs) throws SQLException {
		Collection<AirlineForBillingVO> airlineForBillingVOs=new ArrayList<AirlineForBillingVO>();
		AirlineForBillingVO airlineForBillingVO=null;
		while(rs.next()){
			try {
			airlineForBillingVO=new AirlineForBillingVO();
			if(rs.getString("CMPCOD")!=null){
			airlineForBillingVO.setCompanyCode(rs.getString("CMPCOD"));
			}
			if(rs.getString("ARLIDR")!=null){
			airlineForBillingVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
			}
			if(rs.getString("ARLCOD")!=null){
			airlineForBillingVO.setAirlineCode(rs.getString("ARLCOD"));
			}
			if(rs.getString("ARLNUM")!=null){
			airlineForBillingVO.setAirlineNumber(rs.getString("ARLNUM"));
			}
			if(rs.getString("CLRPRD")!=null){
            airlineForBillingVO.setClearancePeriod(rs.getString("CLRPRD"));			
			}			
			Money miscAmount= CurrencyHelper.getMoney("USD");
			miscAmount.setAmount(rs.getDouble("INWMISAMTLSTCUR"));  //Modified by A-7929 as part of ICRD-265471
			airlineForBillingVO.setMiscAmountInBilling(miscAmount);
			Money totAmount= CurrencyHelper.getMoney("USD");
			totAmount.setAmount(rs.getDouble("INWTOTAMTLSTCUR"));  //Modified by A-7929 as part of ICRD-265471
			airlineForBillingVO.setTotalAmountInBilling(totAmount);
			Money creditAmount= CurrencyHelper.getMoney("USD");
			creditAmount.setAmount(rs.getDouble("INWCRDAMTLSTCUR"));  //Modified by A-7929 as part of ICRD-265471
			airlineForBillingVO.setCreditAmountInBilling(creditAmount);
			Money netAmount= CurrencyHelper.getMoney("USD");
			netAmount.setAmount(rs.getDouble("INWNETAMT"));
			airlineForBillingVO.setNetValueInBilling(netAmount);			
			
			if(rs.getString("FORTHRSTA")!=null){
	            airlineForBillingVO.setStatus(rs.getString("FORTHRSTA"));
			}
			
			if(rs.getDate("CREDAT")!=null){
				Date creationDat = rs.getDate("CREDAT");		
				
				airlineForBillingVO.setCreationDate(new LocalDate("***",Location.NONE, creationDat));
			}
			airlineForBillingVOs.add(airlineForBillingVO);
			}
			catch(CurrencyException e) {
	            throw new SQLException(e.getErrorCode());
	        }
			
		}
		log.log(Log.INFO, "inside mapper", airlineForBillingVOs);
		return (ArrayList<AirlineForBillingVO>)airlineForBillingVOs;
	}

}
