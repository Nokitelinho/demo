/*
 * StockRequestForOALMapper.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1619
 *
 */
public class StockRequestForOALMapper implements Mapper<StockRequestForOALVO> {

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public StockRequestForOALVO map(ResultSet rs) throws SQLException {
    	StockRequestForOALVO forOALVO = new StockRequestForOALVO();
    	forOALVO.setModeOfCommunication(rs.getString("MODCOM"));
    	forOALVO.setContent(rs.getString("REQCNT"));
    	forOALVO.setSerialNumber(rs.getInt("SERNUM"));
    	forOALVO.setRequestCompleted(false);
    	Date tim = rs.getDate("REQDAT"); 
    	if(tim!=null){
    		forOALVO.setRequestedDate(
    				new LocalDate(rs.getString("ARPCOD"), Location.ARP, tim));
    	}
    	Date time = rs.getDate("REQDAT"); 
    	if(time!=null){
    		forOALVO.setActionTime(new LocalDate(
    				rs.getString("ARPCOD"), Location.ARP, time));
    	}
    	forOALVO.setAddress(rs.getString("REQADR"));
    	return forOALVO;
    }

}
