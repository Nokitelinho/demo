/*
 * StockDetailsSummaryMapper.java Created on May19,2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/*
 * @@author Chippy Mathew
 * */
public class StockDetailsSummaryMapper implements Mapper<StockDetailsVO> {

	public StockDetailsVO map(ResultSet rs) throws SQLException {
		/*
		 * CMPCOD,STKHLDCOD,DOCTYP,DOCSUBTYP, TXNDATSTR, TXNDATUTC, OPGBAL,
		 * RCVSTK, ALCSTK, TFDSTK, RTNSTK, RTNUTLSTK, BLKLSTSTK, UTLSTK, CLSBAL
		 */
		long closingBalance = 0;
		StockDetailsVO stockDetailsVO = new StockDetailsVO();
		stockDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		stockDetailsVO.setStockHolderCode(rs.getString("STKHLDCOD"));
		stockDetailsVO.setDocumentType(rs.getString("DOCTYP"));
		stockDetailsVO.setDocumentSubType(rs.getString("DOCSUBTYP"));
		// stockDetailsVO.setStartDate(new
		// LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("TXNDATSTR")));
		if (rs.getTimestamp("TXNDATUTC") != null) {
			stockDetailsVO.setTransactionDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getTimestamp("TXNDATUTC")));
		}
		stockDetailsVO
				.setOpeningBalance(Long.parseLong(rs.getString("OPGBAL")));
		stockDetailsVO.setReceivedStock(Long.parseLong(rs.getString("RCVSTK")));
		stockDetailsVO
				.setAllocatedStock(Long.parseLong(rs.getString("ALCSTK")));
		stockDetailsVO.setTransferredStock(Long.parseLong(rs
				.getString("TFDSTK")));
		stockDetailsVO.setReturnedStock(Long.parseLong(rs.getString("RTNSTK")));
		stockDetailsVO.setReturnedUtilizedStock(Long.parseLong(rs
				.getString("RTNUTLSTK")));
		stockDetailsVO.setBlacklistedStock(Long.parseLong(rs
				.getString("BLKLSTSTK")));
		stockDetailsVO.setUtilizedStock(Long.parseLong(rs.getString("UTLSTK")));
		// PI_STKHLD_REC.OPGBAL + PI_STKHLD_REC.RCVSTK + PI_STKHLD_REC.RTNUTLSTK
		// - PI_STKHLD_REC.ALCSTK - PI_STKHLD_REC.TFDSTK - PI_STKHLD_REC.RTNSTK
		// - PI_STKHLD_REC.UTLSTK-PI_STKHLD_REC.BLKLSTSTK;
		closingBalance = stockDetailsVO.getOpeningBalance()
				+ stockDetailsVO.getReceivedStock()
				+ stockDetailsVO.getReturnedUtilizedStock()
				- stockDetailsVO.getAllocatedStock()
				- stockDetailsVO.getTransferredStock()
				- stockDetailsVO.getReturnedStock()
				- stockDetailsVO.getUtilizedStock()
				- stockDetailsVO.getBlacklistedStock();
		/*if (rs.getString("CLSBAL") != null)
			stockDetailsVO.setAvailableStock(Long.parseLong(rs
					.getString("CLSBAL")));*/
		stockDetailsVO.setAvailableStock(closingBalance);
		return stockDetailsVO;
	}
}