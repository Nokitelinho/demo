/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.defaults.BillingLineDetailsMultiMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Oct 29, 2015
 *
 *  Copyright 2015 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.defaults.BillingLineDetailsMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Oct 29, 2015	:	Draft
 */
public class BillingLineDetailsMultiMapper implements MultiMapper<BillingLineVO>{
	  private static final String STA_EXPIRED = "E";
	  private Log log = LogFactory.getLogger("MAILTRACKING MRA BILLINGLINE");

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 *	Added by 			: A-4809 on Oct 29, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public List<BillingLineVO> map(ResultSet rs) throws SQLException {
		 log.entering("BillingLineMapper", "map");
		List<BillingLineVO> blgLineDetails =
	             new ArrayList<BillingLineVO>();
	    		 BillingLineVO billingLineVO = null;
	    		 String blgLineKey = null;
	    		 String blgLinePrev = null;
	    		 while(rs.next()) {
	    			 blgLineKey = new StringBuilder().append(rs.getString("BLGMTXCODLIN"))
								.append(rs.getString("CMPCODLIN"))
								.append(rs.getInt("BLGLINSEQNUMLIN")).toString();
	    			 if(!blgLineKey.equals(blgLinePrev)) {
	    				 billingLineVO = new BillingLineVO();
	    				 blgLineDetails.add(billingLineVO);
	    				 constructBillingLineVO(billingLineVO,rs);
	    				 blgLinePrev = blgLineKey;
	    			 }
	    		 }

	    		  log.exiting("BillingLineMultiMapper", "map");
	    		  return blgLineDetails;
	}

	/**
	 * 	Method		:	BillingLineDetailsMultiMapper.constructBillingLineVO
	 *	Added by 	:	A-4809 on Oct 29, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param billingLineVO
	 *	Parameters	:	@param rs 
	 *	Return type	: 	void
	 * @throws SQLException 
	 */
	private void constructBillingLineVO(BillingLineVO billingLineVO,
			ResultSet rs) throws SQLException {
		billingLineVO.setCompanyCode( rs.getString( "CMPCODLIN" ) );
		billingLineVO.setBillingLineSequenceNumber( rs.getInt( "BLGLINSEQNUMLIN" ) );
		billingLineVO.setBillingMatrixId( rs.getString( "BLGMTXCODLIN" ) );
		billingLineVO.setBillingLineStatus( rs.getString( "BLGLINSTALIN" ) );
		billingLineVO.setAirlineIdentifier(rs.getInt("BLGMTXARLIDR"));
		billingLineVO.setPoaCode(rs.getString("BLGMTXPOACOD"));
		billingLineVO.setApplicableRate(rs.getDouble("RATE"));
		billingLineVO.setAirlineCode(rs.getString("BILPTYARLCOD"));
		billingLineVO.setBillingSector(rs.getString("BILSEC"));
		billingLineVO.setUnitCode(rs.getString("UNTCOD"));
		billingLineVO.setBillingBasis(rs.getString("BLGBAS"));
		if(rs.getString("CURRENCY")!=null &&
				rs.getString("CURRENCY").trim().length()>0){
			billingLineVO.setCurrencyCode(rs.getString("CURRENCY"));
		}
		//Commented as part of ICRD-106032
		//LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		/*if(billingLineVO.getValidityEndDate() != null &&
            	currentDate.after(billingLineVO.getValidityEndDate()))
		 {
			billingLineVO.setBillingLineStatus(STA_EXPIRED);
			//else
				//billingLineVO.setBillingCategory(rs.getString("BLGLINSTALIN"));
		}*/
		if( rs.getDate( "VLDSTRDATLIN" ) != null) {
			billingLineVO.setValidityStartDate( new LocalDate
			 ( LocalDate.NO_STATION, Location.NONE, rs.getDate( "VLDSTRDATLIN" ) ) );
		}
		if( rs.getDate( "VLDENDDATLIN" ) != null ) {
			billingLineVO.setValidityEndDate( new LocalDate
			 ( LocalDate.NO_STATION, Location.NONE, rs.getDate( "VLDENDDATLIN" ) ) );
		}
		billingLineVO.setRevenueExpenditureFlag( rs.getString( "REVEXPFLGLIN" ) );
		
	}

}
