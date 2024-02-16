/*
 * ULDServiceabilityMapper.java Created on Sep 1, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This class implements a Mapper<<ULDServiceabilityVO>
 * @author A-2052
 *
 */
public class ULDServiceabilityMapper implements Mapper<ULDServiceabilityVO> {

	private Log log = LogFactory.getLogger("ULDServiceabilityMapper");

	/**
	 *
	 * @param rs
	 * @return List<ULDServiceabilityVO>
	 * @throws SQLException
	 */
	public ULDServiceabilityVO map(ResultSet rs) throws SQLException {

		log.entering("ULDServiceabilityMapper", "map");
		ULDServiceabilityVO serviceVO = new ULDServiceabilityVO();
		serviceVO.setCompanyCode(rs.getString("CMPCOD"));
		serviceVO.setPartyType(rs.getString("PTYTYP"));
		serviceVO.setSequenceNumber(rs.getString("SEQNUM"));
		serviceVO.setCode(rs.getString("SRVCOD"));
		serviceVO.setDescription(rs.getString("SRVDES"));
		serviceVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
		if (rs.getTimestamp("LSTUPDTIM") != null) {
			serviceVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getTimestamp("LSTUPDTIM")));
		}
		return serviceVO;
	}
}
