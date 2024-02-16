/*
 * ContainerAssignmentMapper.java Created on May 29, 2006
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
 * @author a-1303
 * This class is used to map the Resultset into the Vos
 */
public class ContainerAssignmentMapper implements Mapper<ContainerAssignmentVO> {

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
		// containerAssignmentVO.setDestination(rs.getString("DSTCOD"));
		containerAssignmentVO.setFlightNumber(rs.getString("FLTNUM"));
		containerAssignmentVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerAssignmentVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerAssignmentVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerAssignmentVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		containerAssignmentVO.setFlightStatus(rs.getString("CLSFLG"));
		if (rs.getDate("FLTDAT") != null) {
			containerAssignmentVO.setFlightDate(new LocalDate(assignedPort,
					Location.ARP, rs.getDate("FLTDAT")));
		}
		containerAssignmentVO.setCarrierCode(rs.getString("FLTCARCOD"));
		containerAssignmentVO.setDestination(rs.getString("DSTCOD"));
		containerAssignmentVO.setAcceptanceFlag(rs.getString("ACPFLG"));
		containerAssignmentVO.setPou(rs.getString("POU"));
		containerAssignmentVO.setRemark(rs.getString("RMK"));
		containerAssignmentVO.setContainerType(rs.getString("CONTYP"));
		containerAssignmentVO.setArrivalFlag(rs.getString("ARRSTA"));
		containerAssignmentVO.setTransferFlag(rs.getString("TRAFLG"));
		containerAssignmentVO.setJourneyID(rs.getString("CONJRNIDR"));  
		containerAssignmentVO.setShipperBuiltCode(rs.getString("SBCODE"));
		containerAssignmentVO.setTransitFlag(rs.getString("TRNFLG"));
		containerAssignmentVO.setTransactionCode(rs.getString("TXNCOD"));
		containerAssignmentVO.setReleasedFlag(rs.getString("RELFLG"));
		containerAssignmentVO.setOffloadStatus(rs.getString("OFLFLG"));
		containerAssignmentVO.setPoaFlag(rs.getString("POAFLG"));
		if(rs.getDouble("ACTULDWGT")>0)
			containerAssignmentVO.setActualWeight(
					new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACTULDWGT")));
		if (rs.getDate("ASGDAT") != null) {
			containerAssignmentVO.setAssignedDate(new LocalDate(assignedPort,
					Location.ARP, rs.getTimestamp("ASGDAT")));
		}
		containerAssignmentVO.setUldFulIndFlag(rs.getString("ULDFULIND"));
		containerAssignmentVO.setUldReferenceNo(rs.getLong("ULDREFNUM"));
		containerAssignmentVO.setActWgtSta(rs.getString("ACTWGTSTA"));
		return containerAssignmentVO;
	}

}
