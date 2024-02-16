/*
 * ReservationListingMultiMapper.java Created on Jan 9, 2006
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
 * @author A-1619
 * 
 */
public class ReservationListingMultiMapper implements Mapper<ReservationVO> {

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ReservationVO map(ResultSet rs) throws SQLException {
		ReservationVO reservationVO = new ReservationVO();
		reservationVO.setCompanyCode(rs.getString("CMPCOD"));
		reservationVO.setAirlineCode(rs.getString("TWOAPHCOD"));
		reservationVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
		reservationVO.setCustomerCode(rs.getString("CUSCOD"));
		reservationVO.setDocumentNumber(rs.getString("DOCNUM"));
		// reservationVO.getDocumentNumbers(rs.get);
		reservationVO.setDocumentStatus(rs.getString("DOCSTA"));
		reservationVO.setAirportCode(rs.getString("ARPCOD"));
		reservationVO.setDocumentType(rs.getString("DOCTYP"));
		Date expiryDate = rs.getDate("EXPDAT");
		if (expiryDate != null) {
			reservationVO.setExpiryDate(new LocalDate(
					rs.getString("ARPCOD"),Location.ARP,expiryDate));
		}
		Date reservationDate = rs.getDate("RESDAT");
		if (reservationDate != null) {
			reservationVO.setReservationDate(new LocalDate(
					rs.getString("ARPCOD"), Location.ARP, reservationDate));
		}
		reservationVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		reservationVO.setReservationRemarks(rs.getString("RESRMK"));
		reservationVO.setShipmentPrefix(rs.getString("SHPPFX"));
		Date updateTime = rs.getDate("LSTUPDTIM");
		if (updateTime != null) {
			reservationVO.setLastUpdateTime(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, updateTime));
		}
		return reservationVO;
	}

}
