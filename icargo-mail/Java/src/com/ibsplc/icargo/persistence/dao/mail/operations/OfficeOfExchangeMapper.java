/*
 * OfficeOfExchangeMapper.java Created on June 07, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * @author A-2037
 * This class is used to map the ResultSet to OfficeOfExchangeVO
  */
public class OfficeOfExchangeMapper implements Mapper<OfficeOfExchangeVO>{
	
	
	
	
	/**
     * @param rs
     * @return 
     * @throws SQLException
     */
   public OfficeOfExchangeVO map(ResultSet rs) throws SQLException {
	   OfficeOfExchangeVO officeOfExchangeVO=new OfficeOfExchangeVO();
	   if(MailConstantsVO.FLAG_ACTIVE.equals(rs.getString("ACTFLG"))){
	     officeOfExchangeVO.setActive(true);
	   }
	   else{
		   officeOfExchangeVO.setActive(false);
	   }
	   officeOfExchangeVO.setCityCode(rs.getString("CTYCOD"));
	   officeOfExchangeVO.setCode(rs.getString("EXGOFCCOD"));
	   officeOfExchangeVO.setCodeDescription(rs.getString("EXGCODDES"));
	   officeOfExchangeVO.setCompanyCode(rs.getString("CMPCOD"));
	   officeOfExchangeVO.setCountryCode(rs.getString("CNTCOD"));
	   officeOfExchangeVO.setAirportCode(rs.getString("ARPCOD"));
	   officeOfExchangeVO.setOfficeCode(rs.getString("OFCCOD"));
	   officeOfExchangeVO.setPoaCode(rs.getString("POACOD"));
	   Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
	     if(lstUpdTime != null) {
	    	 officeOfExchangeVO.setLastUpdateTime(
	    		 new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
	     }
	   officeOfExchangeVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
	 //Added as part of CRQ ICRD-111886 by A-5526 starts
	   officeOfExchangeVO.setMailboxId(rs.getString("MALBOXID"));       
	 //Added as part of CRQ ICRD-111886 by A-5526 ends
	   return officeOfExchangeVO;
   }
   

}
