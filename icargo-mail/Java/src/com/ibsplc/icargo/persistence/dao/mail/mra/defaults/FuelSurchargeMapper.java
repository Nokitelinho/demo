/*
 * FuelSurchargeMapper.java created on APR 24,2009
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.FuelSurchargeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */

public class FuelSurchargeMapper implements Mapper<FuelSurchargeVO>{
	 
	  private Log log = LogFactory.getLogger("MRA_DEFAULTS");
	/**
	 * Method returns the Billing line values from database
	 *
	 * * @author A-2391
	 * * * @param rs
	 * * * @return FuelSurchargeVO
	 * * * @throws SQLException
	 * * * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	  
	public FuelSurchargeVO map( ResultSet rs ) throws SQLException {
		FuelSurchargeVO fuelSurchargeVO = new FuelSurchargeVO();
		fuelSurchargeVO.setCompanyCode( rs.getString( "CMPCOD" ) );
		fuelSurchargeVO.setRateCharge( rs.getString( "RATIND" ) );
		fuelSurchargeVO.setValues(String.valueOf( rs.getDouble( "FULCHG" )) );
		fuelSurchargeVO.setCurrency(rs.getString( "CURCOD" ) );
		fuelSurchargeVO.setSeqNum(rs.getInt("SEQNUM"));
		
		if( rs.getDate( "VLDFRM" ) != null) {
			fuelSurchargeVO.setValidityStartDate( new LocalDate
			 ( LocalDate.NO_STATION, Location.NONE, rs.getDate( "VLDFRM" ) ) );
		}
		if( rs.getDate( "VLDTOO" ) != null ) {
			fuelSurchargeVO.setValidityEndDate( new LocalDate
			 ( LocalDate.NO_STATION, Location.NONE, rs.getDate( "VLDTOO" ) ) );
		}
		fuelSurchargeVO.setCountry(rs.getString( "CNTNAM" ) );
		log.log(Log.INFO, "vo in mapper ", fuelSurchargeVO);
		return fuelSurchargeVO;
	}
}
