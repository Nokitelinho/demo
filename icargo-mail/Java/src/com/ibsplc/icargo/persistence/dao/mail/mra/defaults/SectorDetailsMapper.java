/*
 * SectorDetailsMapper.java Created on Aug 20, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * SectorDetailsMapper for Flight details
 * 
 * @author A-3429
 * 
 */
public class SectorDetailsMapper implements Mapper<SectorRevenueDetailsVO> {

	/**
	 * @param resultSet
	 * @return SectorRevenueDetailsVO
	 * @throws SQLException
	 */

	public SectorRevenueDetailsVO map(ResultSet resultSet) throws SQLException {

		SectorRevenueDetailsVO sectorDetailsVO = new SectorRevenueDetailsVO();
		sectorDetailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
		sectorDetailsVO.setFlightCarrierId(resultSet.getInt("FLTCARIDR"));
		sectorDetailsVO.setFlightNumber(resultSet.getString("FLTNUM"));
		sectorDetailsVO.setFlightSequenceNumber(resultSet.getInt("FLTSEQNUM"));
		sectorDetailsVO.setSegmentDestination(resultSet.getString("SEGDST"));
		sectorDetailsVO.setSegmentOrigin(resultSet.getString("SEGORG"));
		sectorDetailsVO.setSegmentSerialNumber(resultSet.getInt("SEGSERNUM"));
		sectorDetailsVO.setFlightSegmentStatus(resultSet.getString("SEGSTA"));
		sectorDetailsVO.setFlightStatus(resultSet.getString("FLTSTA"));

		return sectorDetailsVO;
	}

}
