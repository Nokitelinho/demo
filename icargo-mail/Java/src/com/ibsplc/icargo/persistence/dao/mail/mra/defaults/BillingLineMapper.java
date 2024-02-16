/*
 * BillingLineMapper.java created on Mar 06, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1872
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		  Mar 6, 2007			  A-1872		Created
 */
public class BillingLineMapper implements Mapper<BillingLineVO>{
	  private static final String STA_EXPIRED = "E";
	/**
	 * Method returns the Billing line values from database
	 *
	 * * @author A-1872
	 * * Mar 6, 2007
	 * * * @param rs
	 * * * @return billingLineVO
	 * * * @throws SQLException
	 * * * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public BillingLineVO map( ResultSet rs ) throws SQLException {
		BillingLineVO billingLineVO = new BillingLineVO();
		billingLineVO.setCompanyCode( rs.getString( "CMPCODLIN" ) );
		billingLineVO.setBillingLineSequenceNumber( rs.getInt( "BLGLINSEQNUMLIN" ) );
		billingLineVO.setBillingMatrixId( rs.getString( "BLGMTXCODLIN" ) );

		billingLineVO.setBillingLineStatus( rs.getString( "BLGLINSTALIN" ) );
		billingLineVO.setAirlineIdentifier(rs.getInt("BLGMTXARLIDR"));
		billingLineVO.setPoaCode(rs.getString("BLGMTXPOACOD"));
		billingLineVO.setApplicableRate(rs.getDouble("RATE"));
		billingLineVO.setAirlineCode(rs.getString("BILPTYARLCOD"));
		billingLineVO.setBillingSector(rs.getString("BILSEC"));
		billingLineVO.setBillingBasis(rs.getString("BLGBAS"));
		if(rs.getString("CURRENCY")!=null &&
				rs.getString("CURRENCY").trim().length()>0){
			billingLineVO.setCurrencyCode(rs.getString("CURRENCY"));
		}

		if( rs.getDate( "VLDSTRDATLIN" ) != null) {
			billingLineVO.setValidityStartDate( new LocalDate
			 ( LocalDate.NO_STATION, Location.NONE, rs.getDate( "VLDSTRDATLIN" ) ) );
		}
		if( rs.getDate( "VLDENDDATLIN" ) != null ) {
			billingLineVO.setValidityEndDate( new LocalDate
			 ( LocalDate.NO_STATION, Location.NONE, rs.getDate( "VLDENDDATLIN" ) ) );
		}
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		//Commented as part of ICRD-106032
		/*if(billingLineVO.getValidityEndDate() != null &&
            	currentDate.after(billingLineVO.getValidityEndDate()))
		 {
			billingLineVO.setBillingLineStatus(STA_EXPIRED);
			//else
				//billingLineVO.setBillingCategory(rs.getString("BLGLINSTALIN"));
		}*/

		billingLineVO.setRevenueExpenditureFlag( rs.getString( "REVEXPFLGLIN" ) );
		return billingLineVO;
	}
}
