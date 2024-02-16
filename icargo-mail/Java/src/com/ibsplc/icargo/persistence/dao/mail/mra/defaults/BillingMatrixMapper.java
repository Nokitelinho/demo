/*
 * BillingMatrixMapper.java created on Feb 27, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class BillingMatrixMapper implements Mapper<BillingMatrixVO>{

	private static final String CLASS_NAME = "BillingMatrixMapper";

	private static final String STA_EXPIRED = "E";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public BillingMatrixVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME,"map");
		BillingMatrixVO billingMatrixVO=new BillingMatrixVO();
		billingMatrixVO.setCompanyCode(rs.getString("BLGMTXCMPCOD"));		
		billingMatrixVO.setBillingMatrixId(rs.getString("BLGMTXBLGMTXCOD"));


		billingMatrixVO.setDescription(rs.getString("BLGMTXBLGMTXDES"));		
		billingMatrixVO.setTotalBillinglines(rs.getInt("BLGMTXTOTBLGLIN"));
		if(rs.getDate("BLGMTXVLDSTRDAT")!=null){
			billingMatrixVO.setValidityStartDate(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getDate("BLGMTXVLDSTRDAT")));

		}
		if(rs.getDate("BLGMTXVLDENDDAT")!=null){
			billingMatrixVO.setValidityEndDate(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,rs.getDate("BLGMTXVLDENDDAT")));
		}
		LocalDate curDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		//Commented as part of ICRD-106032
		//the condition check is used to check the expired rate line status
		if(billingMatrixVO.getValidityEndDate() != null &&
				curDate.after(billingMatrixVO.getValidityEndDate())&&"A".equalsIgnoreCase(rs.getString("BLGMTXBLGMTXSTA"))){
			billingMatrixVO.setBillingMatrixStatus(STA_EXPIRED);

		}
		else{
			billingMatrixVO.setBillingMatrixStatus(rs.getString("BLGMTXBLGMTXSTA"));

		}
	
		/**@a-3447
		 * added for bugfix 15185
		 * 
		 */

		billingMatrixVO.setPoaCode(rs.getString("BILPTYPOACOD"));
		billingMatrixVO.setAirlineCode(rs.getString("BILPTYARLCOD"));
		billingMatrixVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
		if (rs.getTimestamp("LSTUPDTIM") != null) {
			billingMatrixVO.setLastUpdatedTime(new LocalDate(NO_STATION, NONE, rs
					.getTimestamp("LSTUPDTIM")));
		}
		log.exiting(CLASS_NAME,"map");
		return billingMatrixVO;
	}

}
