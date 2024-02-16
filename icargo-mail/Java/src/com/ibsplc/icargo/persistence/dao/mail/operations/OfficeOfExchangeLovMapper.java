/*
 * OfficeOfExchangeLovMapper.java Created on June 21, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * This class is used to map the ResultSet to OfficeOfExchangeVO
 * @author A-2037
 */
public class OfficeOfExchangeLovMapper implements Mapper<OfficeOfExchangeVO>{
	
	 /**
    * @param rs
    * @return 
    * @throws SQLException
    */
  public OfficeOfExchangeVO map(ResultSet rs) throws SQLException {
	  OfficeOfExchangeVO officeOfExchangeVO=new OfficeOfExchangeVO();	   
	   officeOfExchangeVO.setCode(rs.getString("EXGOFCCOD"));
	   officeOfExchangeVO.setCodeDescription(rs.getString("EXGCODDES"));
	   officeOfExchangeVO.setCompanyCode(rs.getString("CMPCOD"));	   
	   officeOfExchangeVO.setAirportCode(rs.getString("ARPCOD"));
	   officeOfExchangeVO.setPoaCode(rs.getString("POACOD"));
	   return officeOfExchangeVO;
  }

}
