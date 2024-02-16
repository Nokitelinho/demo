/*
 * ProrationFactorMapper.java Created on Mar 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2518
 * 
 */
public class ProrationFactorMapper implements Mapper<ProrationFactorVO> {

	private Log log = LogFactory.getLogger("CRA PRORATION");

	private static final String CLASS_NAME = "ProrationFactorMapper";

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ProrationFactorVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		ProrationFactorVO prorationFactorVo = new ProrationFactorVO();
		prorationFactorVo.setCompanyCode(rs.getString("CMPCOD"));
		prorationFactorVo.setOriginCityCode(rs.getString("FRMCTY"));
		prorationFactorVo.setDestinationCityCode(rs.getString("TOOCTY"));
		prorationFactorVo.setDestinationCityName(rs.getString("CTYNAM"));
		prorationFactorVo.setProrationFactor(rs.getDouble("PROFCT"));
		prorationFactorVo.setProrationFactorSource(rs.getString("FCTSRC"));
		prorationFactorVo.setProrationFactorStatus(rs.getString("FCTSTA"));
		prorationFactorVo.setSequenceNumber(rs.getInt("SEQNUM"));
		if (rs.getDate("VLDFRM") != null) {
			prorationFactorVo.setFromDate(new LocalDate(NO_STATION, NONE, rs
					.getDate("VLDFRM")));
		}
		if (rs.getDate("VLDTOO") != null) {
			prorationFactorVo.setToDate(new LocalDate(NO_STATION, NONE, rs
					.getDate("VLDTOO")));
		}
		/**
		 * for optimistic locking
		 */
		prorationFactorVo.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
		if (rs.getTimestamp("LSTUPDTIM") != null) {
			prorationFactorVo.setLastUpdatedTime(new LocalDate(NO_STATION, NONE, rs
					.getTimestamp("LSTUPDTIM")));
		}
		log.exiting(CLASS_NAME, "map");
		return prorationFactorVo;
	}
}
