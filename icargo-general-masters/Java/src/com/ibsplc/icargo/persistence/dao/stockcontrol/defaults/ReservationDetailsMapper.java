/*
 * ReservationDetailsMapper.java Created on Mar 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1954
 *
 */
public class ReservationDetailsMapper implements Mapper<ReservationVO> {

	/**
	 * @param arg0
	 * @return
	 * @throws SQLException
	 */
	public ReservationVO map(ResultSet rs) throws SQLException {
		ReservationVO reservationVO = new ReservationVO();
		reservationVO.setAirportCode(rs.getString("RESARPCOD"));
		reservationVO.setCustomerCode(rs.getString("CUSCOD"));
		reservationVO.setAgentCode(rs.getString("AGTCOD"));
		reservationVO.setStockHolderCode(rs.getString("STKHLDCOD"));
		return reservationVO;
	}

}
