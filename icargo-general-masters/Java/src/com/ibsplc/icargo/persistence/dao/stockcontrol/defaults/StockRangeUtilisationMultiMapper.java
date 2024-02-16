/*
 * StockRangeUtilisationMultiMapper.java Created on Jun 14, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-1953
 *
 */
public class StockRangeUtilisationMultiMapper implements MultiMapper<StockAllocationVO> {

	private Log log = LogFactory.getLogger("STOCK UTILISATION");


    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public List<StockAllocationVO> map(ResultSet rs) throws SQLException {


    	List<StockAllocationVO> stockAllocationVOs = null;
    	Collection<RangeVO> rangeVOs = null;
    	StockAllocationVO stockAllocationVO = null;
    	RangeVO rangeVO = null;
    	String utilisationPK = " ";
    	while(rs.next()){
			StringBuilder utlPkBuilder = new StringBuilder();
			utlPkBuilder.append(rs.getString("CMPCOD")).append(rs.getInt("ARLIDR"))
				 .append(rs.getString("DOCTYP")).append(rs.getString("DOCSUBTYP"))
				 .append(rs.getString("STKHLDCOD"));
			String utlPK = utlPkBuilder.toString();
			log.log(Log.FINE, "----STKRNGUTL OLD PK---->", utilisationPK);
			log.log(Log.FINE, "----STKRNGUTL NEW PK---->", utlPK);
			if(!utilisationPK.equals(utlPK)){

				if(!" ".equals(utilisationPK)){
						if(stockAllocationVO!=null){
							if(stockAllocationVOs ==null){
								stockAllocationVOs = new ArrayList<StockAllocationVO>();
							}
							stockAllocationVOs.add(stockAllocationVO);
						}
					}
					stockAllocationVO = new StockAllocationVO();
					stockAllocationVO.setCompanyCode(rs.getString("CMPCOD"));
					stockAllocationVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
					stockAllocationVO.setDocumentType(rs.getString("DOCTYP"));
					stockAllocationVO.setDocumentSubType(rs.getString("DOCSUBTYP"));
					stockAllocationVO.setStockHolderCode(rs.getString("STKHLDCOD"));
					rangeVOs = new ArrayList<RangeVO>();
					rangeVO = new RangeVO();
					rangeVO.setStartRange(rs.getString("MSTDOCNUM"));
					rangeVO.setEndRange(rs.getString("MSTDOCNUM"));
					rangeVOs.add(rangeVO);
					stockAllocationVO.setRanges(rangeVOs);

			}
			else{
				rangeVO = new RangeVO();
				rangeVO.setStartRange(rs.getString("MSTDOCNUM"));
				rangeVO.setEndRange(rs.getString("MSTDOCNUM"));
				stockAllocationVO.getRanges().add(rangeVO);
			}
			utilisationPK = utlPK;
    	}
    	if(stockAllocationVO!=null){
			if(stockAllocationVOs==null){
				stockAllocationVOs = new ArrayList<StockAllocationVO>();
			}
			stockAllocationVOs.add(stockAllocationVO);
		}

		log.log(Log.FINE, "@@@@@@@--STOCK ALLOCATION VOS--@@@@@@--->",
				stockAllocationVOs);
		return stockAllocationVOs;
     }
}
