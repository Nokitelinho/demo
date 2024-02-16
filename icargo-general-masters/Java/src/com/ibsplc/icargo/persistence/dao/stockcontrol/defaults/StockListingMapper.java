/*
 * StockListingMapper.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1619
 *
 */
public class StockListingMapper implements Mapper<StockVO> {

    /* (non-Javadoc)
     * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
     */
    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public StockVO map(ResultSet rs) throws SQLException {
    	
    	StockVO stockVO = new StockVO();
    	stockVO.setDocumentType(rs.getString("DOCTYP"));
    	stockVO.setDocumentSubType(rs.getString("DOCSUBTYP"));
    	stockVO.setReorderLevel(rs.getInt("REOLVL"));
    	stockVO.setAirlineId(rs.getInt("ARLIDR"));
    	stockVO.setTotalStock(rs.getDouble("TOTAL"));
    	return stockVO;
    }

}
