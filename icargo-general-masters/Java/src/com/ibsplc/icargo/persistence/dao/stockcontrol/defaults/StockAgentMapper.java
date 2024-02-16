/*
 * StockAgentMapper.java Created on Jul 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1954
 *
 */
public class StockAgentMapper implements Mapper<StockAgentVO> {

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public StockAgentVO map(ResultSet rs) throws SQLException {
    	StockAgentVO stockAgentVO = new StockAgentVO();
    	stockAgentVO.setAgentCode(rs.getString("AGTAGTCOD"));
    	stockAgentVO.setStockHolderCode(rs.getString("AGTSTKHLDCOD"));
    	stockAgentVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
    	stockAgentVO.setOperationFlag(StockAgentVO.OPERATION_FLAG_UPDATE);
    	return stockAgentVO;
    }
}
