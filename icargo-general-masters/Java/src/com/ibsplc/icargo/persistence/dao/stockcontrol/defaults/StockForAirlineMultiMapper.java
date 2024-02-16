/*
 * StockForAirlineMultiMapper.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * @author A-1619
 *
 */
public class StockForAirlineMultiMapper implements MultiMapper<StockAllocationVO> {

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<StockAllocationVO> map(ResultSet rs) throws SQLException {
		HashMap<String,StockAllocationVO> stockAllocationMap = new HashMap<String,StockAllocationVO>();
		HashMap<String,RangeVO> rangeMap = new HashMap<String,RangeVO>();
		StockAllocationVO stockAllocationVO = null;
		String stockKey ="";
		String rangeKey ="";

		while(rs.next()){
			String companyCode = rs.getString("STKCMPCOD");
			String stockHolderCode = rs.getString("STKSTKHLDCOD");
			String documentType= rs.getString("STKDOCTYP");
			String documentSubType = rs.getString("STKDOCSUBTYP");

			stockKey = new StringBuilder(companyCode)
								.append(rs.getString("STKARLIDR"))
								.append(documentType)
								.append(documentSubType)
								.append(stockHolderCode)
								.toString();

			if( ! stockAllocationMap.containsKey(stockKey)){

				stockAllocationVO = new StockAllocationVO();
				stockAllocationVO.setCompanyCode(companyCode);
				stockAllocationVO.setAirlineIdentifier(rs.getInt("STKARLIDR"));
				stockAllocationVO.setDocumentType(documentType);
				stockAllocationVO.setDocumentSubType(documentSubType);
				stockAllocationVO.setRemarks(rs.getString("STKSTKRMK"));
				stockAllocationVO.setStockHolderCode(stockHolderCode);

				stockAllocationMap.put(stockKey, stockAllocationVO);

			}else{
				stockAllocationVO = stockAllocationMap.get(stockKey);
			}

			rangeKey = new StringBuilder(stockKey)
									.append(rs.getString("RNGRNGSEQNUM"))
									.toString();

			if(!rangeMap.containsKey(rangeKey)){

				if(rs.getString("RNGRNGSEQNUM") != null){
					RangeVO rangeVO = new RangeVO();
					rangeVO.setCompanyCode(companyCode);
					rangeVO.setAirlineIdentifier(rs.getInt("STKARLIDR"));
					rangeVO.setDocumentType(documentType);
					rangeVO.setDocumentSubType(documentSubType);
					rangeVO.setSequenceNumber(rs.getString("RNGRNGSEQNUM"));
					rangeVO.setStartRange(rs.getString("RNGSTARNG"));
					rangeVO.setEndRange(rs.getString("RNGENDRNG"));
					rangeVO.setNumberOfDocuments(rs.getLong("RNGCOUNT"));
					rangeVO.setStockHolderCode(stockHolderCode);
					Timestamp stockAcceptanceTime = rs.getTimestamp("RNGRNGACPDAT");
					if(stockAcceptanceTime != null){
						rangeVO.setStockAcceptanceDate(new LocalDate(
								LocalDate.NO_STATION, 
								Location.NONE, 
								stockAcceptanceTime));
					}

					rangeMap.put(rangeKey,rangeVO);

					if(stockAllocationVO.getRanges() == null){
						stockAllocationVO.setRanges(new ArrayList<RangeVO>());
					}
					stockAllocationVO.getRanges().add(rangeVO);
				}
			}
		}
		return new ArrayList<StockAllocationVO>(stockAllocationMap.values());
	}

}
