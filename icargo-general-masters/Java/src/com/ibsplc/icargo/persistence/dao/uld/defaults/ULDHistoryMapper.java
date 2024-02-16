/*
 * ULDHistoryMapper.java Created on Oct 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHistoryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author A-2619
 * 
 */

public class ULDHistoryMapper implements Mapper<ULDHistoryVO> {

	private Log log = LogFactory.getLogger("ULD");

	/**
	 * 
	 * @param rs
	 * @return Collection<ULDHistoryVO>
	 * @throws SQLException
	 */
	public ULDHistoryVO map(ResultSet rs) throws SQLException {

		ULDHistoryVO uldHistoryVO = new ULDHistoryVO();
		uldHistoryVO.setCompanyCode(rs.getString("CMPCOD"));
		uldHistoryVO.setUldNumber(rs.getString("ULDNUM"));
		uldHistoryVO.setUldStatus(rs.getString("STA"));
		uldHistoryVO.setFromStation(rs.getString("FRMARP"));
		uldHistoryVO.setToStation(rs.getString("TOOARP"));
		if (rs.getTimestamp("DAT") != null) {
			uldHistoryVO
					.setUldTransactionDate(new LocalDate(LocalDate.NO_STATION,
							Location.NONE, rs.getTimestamp("DAT")));
		}
		uldHistoryVO.setCarrierCode(rs.getString("CARIDR"));
		uldHistoryVO.setFlightNumber(rs.getString("FLTNUM"));
		uldHistoryVO.setRemarks(rs.getString("RMK"));

		return uldHistoryVO;
	}
}
