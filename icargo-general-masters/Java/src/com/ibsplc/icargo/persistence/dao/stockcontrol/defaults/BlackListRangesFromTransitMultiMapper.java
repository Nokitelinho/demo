/*
 * StockListingMapper.java Created on Mar 14, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * @author A-4443
 *
 */
public class BlackListRangesFromTransitMultiMapper implements MultiMapper {

    /* (non-Javadoc)
     * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
     */
    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public List map(ResultSet rs) throws SQLException {
    	Collection<TransitStockVO> transitStockVOs = new ArrayList<TransitStockVO>();
    	TransitStockVO transitStockVO ;
    	while(rs.next()) 
       {
    		transitStockVO = new TransitStockVO();
    	transitStockVO.setCompanyCode(rs.getString("CMPCOD"));
    	transitStockVO.setStockHolderCode(rs.getString("STKHLDCOD"));
    	transitStockVO.setMissingStartRange(rs.getString("MISSTARNG"));
    	transitStockVO.setMissingEndRange(rs.getString("MISENDRNG"));
    	transitStockVO.setAsciiMissingEndRange(rs.getLong("ASCMISENDRNG"));  
    	transitStockVO.setAsciiMissingStartRange(rs.getLong("ASCMISSTARNG"));   
    	transitStockVO.setSequenceNumber(rs.getString("SEQNUM"));
    	transitStockVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
    	transitStockVO.setDocumentType(rs.getString("DOCTYP"));
    	transitStockVO.setDocumentSubType(rs.getString("DOCSUBTYP"));
    	transitStockVO.setActualEndRange(rs.getString("ACTENDRNG"));
    	transitStockVO.setActualStartRange(rs.getString("ACTSTARNG"));
    	//transitStockVO.setConfirmDate(new LocalDate("***", Location.NONE, rs.getTimestamp("CFRDAT")));
    	transitStockVO.setConfirmStatus(rs.getString("CFRSTA")); 
    	transitStockVO.setLastUpdateTime(new LocalDate("***", Location.NONE, rs.getTimestamp("LSTUPDTIM")));
    	transitStockVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
    	//transitStockVO.setManual(rs.getBoolean("MNLFLG"));
    	transitStockVO.setMissingNumberOfDocs(rs.getInt("MISNUMDOC"));  	  
    	transitStockVO.setNumberOfDocs(rs.getInt("NUMDOC"));	
    	transitStockVO.setStockControlFor(rs.getString("FRMSTKHLDCOD"));
    	transitStockVO.setTxnCode(rs.getString("TXNCOD"));
    	transitStockVO.setMissingRemarks(rs.getString("MISRMK"));     
    	if (rs.getTimestamp("TXNDAT") != null) {   
				transitStockVO.setTxnDate(new LocalDate(LocalDate.NO_STATION,
						Location.NONE, rs.getTimestamp("TXNDAT")));
		}
    	transitStockVOs.add(transitStockVO);    	
       }
    	return new ArrayList(transitStockVOs);
    }

}
