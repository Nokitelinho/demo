/*
 * DespatchDetailsMapper.java Created on June 26, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * 
 * @author A-2037 This class is used to Map the Resultset into DespatchDetailsVO
 */
public class DespatchDetailsMapper implements Mapper<DespatchDetailsVO> {
    /**
     * @param rs
     * @return
     * @throws SQLException
     */
	public DespatchDetailsVO map(ResultSet rs) throws SQLException {
		DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
		despatchDetailsVO.setAcceptedBags(rs.getInt("ACPBAG"));
		despatchDetailsVO.setPrevAcceptedBags(rs.getInt("ACPBAG"));
		if (rs.getTimestamp("ACPDAT") != null) {
			despatchDetailsVO.setAcceptedDate(new LocalDate( 
					LocalDate.NO_STATION, Location.NONE, rs
							.getTimestamp("ACPDAT")));
		}
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		//despatchDetailsVO.setAcceptedWeight(rs.getDouble("ACPWGT"));
		despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT")));//added by A-7371
		//despatchDetailsVO.setPrevAcceptedWeight(rs.getDouble("ACPWGT"));
		despatchDetailsVO.setPrevAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT")));//added by A-7371
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getTimestamp("CSGDAT") != null) {
			despatchDetailsVO.setConsignmentDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getTimestamp("CSGDAT")));
		}
		despatchDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs
				.getString("DSTEXGOFC"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setPltEnabledFlag(rs.getString("PLTENBFLG"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setStatedBags(rs.getInt("STDBAG"));
		//despatchDetailsVO.setStatedWeight(rs.getDouble("STDWGT"));
		despatchDetailsVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("STDWGT")));//added by A-7371
		despatchDetailsVO.setPrevStatedBags(rs.getInt("STDBAG"));
		//despatchDetailsVO.setPrevStatedWeight(rs.getDouble("STDWGT"));
		despatchDetailsVO.setPrevStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("STDWGT")));//added by A-7371
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		//Added to include the DSN PK
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));

		// For Arrival
		despatchDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
		//despatchDetailsVO.setReceivedWeight(rs.getDouble("RCVWGT"));
		despatchDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCVWGT")));//added by A-7371
		despatchDetailsVO.setDeliveredBags(rs.getInt("DLVBAG"));
		//despatchDetailsVO.setDeliveredWeight(rs.getDouble("DLVWGT"));
		despatchDetailsVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DLVWGT")));//added by A-7371
		despatchDetailsVO.setPrevReceivedBags(rs.getInt("RCVBAG"));
		//despatchDetailsVO.setPrevReceivedWeight(rs.getDouble("RCVWGT"));
		despatchDetailsVO.setPrevReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCVWGT")));//added by A-7371
		despatchDetailsVO.setPrevDeliveredBags(rs.getInt("DLVBAG"));
		//despatchDetailsVO.setPrevDeliveredWeight(rs.getDouble("DLVWGT"));
		despatchDetailsVO.setPrevDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DLVWGT")));//added by A-7371
		despatchDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		despatchDetailsVO.setTransferFlag(rs.getString("TRAFLG"));
		return despatchDetailsVO;
	}

}
