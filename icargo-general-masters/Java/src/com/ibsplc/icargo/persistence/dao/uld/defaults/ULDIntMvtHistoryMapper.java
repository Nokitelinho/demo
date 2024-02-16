/*
 * ULDIntMvtHistoryMapper.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-2412
 * 
 */
public class ULDIntMvtHistoryMapper implements Mapper<ULDIntMvtDetailVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");

	
	
	public ULDIntMvtDetailVO map(ResultSet rs) throws SQLException {
		log.entering("ULDMovementHistoryMapper", "map");
		ULDIntMvtDetailVO uldIntMvtDetailVO = new ULDIntMvtDetailVO();
		uldIntMvtDetailVO.setCompanyCode(rs.getString("CMPCOD"));
		uldIntMvtDetailVO.setAgentCode(rs.getString("AGTCOD"));
		uldIntMvtDetailVO.setAgentName(rs.getString("AGTNAM"));
		uldIntMvtDetailVO.setAirport(rs.getString("ARPCOD"));
		LocalDate date = null;
		if (uldIntMvtDetailVO.getAirport() != null && rs.getTimestamp("MVTDAT") != null){
			date = new LocalDate(uldIntMvtDetailVO.getAirport(), Location.ARP,
					rs.getTimestamp("MVTDAT"));
		} else {
			if (rs.getTimestamp("MVTDAT") != null) {
			date = new LocalDate(LocalDate.NO_STATION, Location.NONE, rs
					.getTimestamp("MVTDAT"));
			}
		}

		if (date != null) {
			String mvtDate = TimeConvertor.toStringFormat(date.toCalendar(),
					TimeConvertor.ADVANCED_DATE_FORMAT);
			uldIntMvtDetailVO.setMvtDate(date);
			uldIntMvtDetailVO.setDisplayMvtDate(mvtDate);
		}
		uldIntMvtDetailVO.setContent(rs.getString("CNT"));
		uldIntMvtDetailVO.setFromLocation(rs.getString("FRMLOC"));
		uldIntMvtDetailVO.setToLocation(rs.getString("TOOLOC"));
		uldIntMvtDetailVO.setMvtType(rs.getString("MVTTYP"));
		uldIntMvtDetailVO.setRemark(rs.getString("RMK"));
		uldIntMvtDetailVO.setIntSequenceNumber(rs.getString("INTSEQNUM"));
		uldIntMvtDetailVO.setIntSerialNumber(rs.getInt("INTSERNUM"));
		uldIntMvtDetailVO.setUldNumber(rs.getString("ULDNUM"));
		uldIntMvtDetailVO.setReturnStatus(rs.getString("RTNSTA"));

		log.log(Log.FINE, "uldIntMvtDetailVO----", uldIntMvtDetailVO);
		log.exiting("ULDIntMvtHistoryMapper", "map");
		return uldIntMvtDetailVO;
	}
}
