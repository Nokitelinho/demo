/*
 * UCMExceptionFlightMapper.java Created on Jan 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-1936
 * This Class is used to map the  Result set into the Value Object..
 *
 */
public class UCMExceptionFlightMapper implements Mapper<UCMExceptionFlightVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");

	/**
	 * @author A-1936
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public UCMExceptionFlightVO map(ResultSet rs) throws SQLException { 
		UCMExceptionFlightVO exceptionFlightVO = new UCMExceptionFlightVO();
		log.entering("UCMExceptionFlightMapper","map --Method");
		exceptionFlightVO.setCarrierCode(rs.getString("FLTCARCOD"));
		exceptionFlightVO.setCarrierId(rs.getInt("FLTCARIDR"));
		if (rs.getDate("FLTDAT") != null) {
        	exceptionFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("FLTDAT")));
		}
		exceptionFlightVO.setFlightNumber(rs.getString("FLTNUM"));
		exceptionFlightVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		exceptionFlightVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		return exceptionFlightVO;

	}
}