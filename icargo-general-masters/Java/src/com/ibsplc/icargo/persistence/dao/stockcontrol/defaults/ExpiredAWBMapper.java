/*
 * ExpiredAWBMapper.java Created on Feb 23, 2006
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

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1954
 *
 */
public class ExpiredAWBMapper implements Mapper<ReservationVO> {

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public ReservationVO map(ResultSet rs) throws SQLException {    	
    	ReservationVO reservationVO = new ReservationVO();
    	reservationVO.setCompanyCode(rs.getString("AWBCMPCOD"));
    	reservationVO.setAirportCode(rs.getString("AWBARPCOD"));
    	reservationVO.setAirlineIdentifier(rs.getInt("AWBARLIDR"));
    	reservationVO.setDocumentNumber(rs.getString("AWBDOCNUM"));
    	Date expiryDate = rs.getDate("AWBEXPDAT");
    	if(expiryDate != null){
    		reservationVO.setExpiryDate(new LocalDate(
    				rs.getString("AWBARPCOD"), Location.ARP, expiryDate));
    	}
    	reservationVO.setDocumentStatus(rs.getString("AWBDOCSTA"));
    	return reservationVO;
    }
}
