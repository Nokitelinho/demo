/*
 * ContainerAssignmentMapper.java Created on JULY 18, 2023
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author a-10383
 * This class is used to map the Resultset into the Vos
 */
public class ContainerWeightCaptureMapper implements Mapper<ContainerAssignmentVO> {

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ContainerAssignmentVO map(ResultSet rs) throws SQLException {
		

		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		String assignedPort = rs.getString("ASGPRT");
		containerAssignmentVO.setCompanyCode(rs.getString("CMPCOD"));
		containerAssignmentVO.setAirportCode(assignedPort);
		containerAssignmentVO.setContainerNumber(rs.getString("CONNUM"));
		containerAssignmentVO.setFlightNumber(rs.getString("FLTNUM"));
		containerAssignmentVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerAssignmentVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerAssignmentVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		if (rs.getDate("FLTDAT") != null) {
			containerAssignmentVO.setFlightDate(new LocalDate(assignedPort,
					Location.ARP, rs.getDate("FLTDAT")));
		}
		containerAssignmentVO.setCarrierCode(rs.getString("FLTCARCOD"));
		containerAssignmentVO.setDestination(rs.getString("DSTCOD"));
		containerAssignmentVO.setPou(rs.getString("POU"));
		containerAssignmentVO.setRemark(rs.getString("RMK"));
		containerAssignmentVO.setContainerType(rs.getString("CONTYP"));
		if(rs.getDouble("ACTULDWGT")>0)
		{
			containerAssignmentVO.setActualWeight(
					new Measure(UnitConstants.WEIGHT,rs.getDouble("ACTULDWGT")));
		}
		if (rs.getDate("ASGDAT") != null) {
			containerAssignmentVO.setAssignedDate(new LocalDate(assignedPort,
					Location.ARP, rs.getTimestamp("ASGDAT")));
		}
		if(rs.getDouble("DSPWGT")>0)
		{
			containerAssignmentVO.setNetWeight(
					new Measure(UnitConstants.WEIGHT,rs.getDouble("DSPWGT")));
		}
		
		containerAssignmentVO.setUnit(rs.getString("DSPWGTUNT"));
		containerAssignmentVO.setWeightStatus(rs.getString("ACTWGTSTA"));
		containerAssignmentVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		return containerAssignmentVO;
	
	
	}
}