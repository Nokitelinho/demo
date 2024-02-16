/*
 * ListCN51DetailsMapper.java created on Feb 16, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2049
 * 
 */
	public class ListCN51DetailsMapper implements Mapper<AirlineCN51SummaryVO> {

		private Log log = LogFactory.getLogger("MRA DEFAULTS");

		private static final String CLASS_NAME = "ListCCAMapper";

		/**
		 * 
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		public AirlineCN51SummaryVO map(ResultSet rs) throws SQLException {
			log.entering(CLASS_NAME, "map");
			double totAmtContractCurr = 0.0D;
			double totAmrForCN51Details = 0.0D;
			double netDifferenceAmt = 0.0D;
			AirlineCN51SummaryVO summaryVO = new AirlineCN51SummaryVO();
			summaryVO.setCompanycode(rs.getString("CMPCOD"));
			summaryVO.setAirlineidr(rs.getInt("ARLIDR"));
			summaryVO.setInvoicenumber(rs.getString("INVNUM"));
			summaryVO
					.setInterlinebillingtype(rs.getString("INTBLGTYP"));
			summaryVO.setClearanceperiod(rs.getString("CLRPRD"));
			summaryVO.setCn51status(rs.getString("C51STA"));
			summaryVO.setInvStatus(rs.getString("INVSTA"));
			summaryVO.setBillingcurrencycode(rs.getString("BLGCURCOD"));
			summaryVO.setContractCurrencycode(rs.getString("CRTCURCOD"));
			summaryVO.setListingCurrency(rs.getString("LSTCURCOD"));  //Modified by A-7929 as part of ICRD_265471
			
			summaryVO.setC51Amount(rs.getDouble("C51AMTLSTCUR"));  //Modified by A-7929 as part of ICRD_265471
				
			totAmtContractCurr = rs.getDouble("TOTSMYCRTAMT");
			
			
			summaryVO
				.setTotalAmountInContractCurrency(totAmtContractCurr);
		
			
			summaryVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
			if(rs.getTimestamp("LSTUPDTIM")!=null){
				summaryVO.setLastUpdatedTime(new  LocalDate(LocalDate.NO_STATION,Location.NONE,
						                        rs.getTimestamp("LSTUPDTIM")));
			}
			
			return summaryVO;
		}

}
