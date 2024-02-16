/*
 * DespatchInDSNMapper.java Created on Jun 06 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-2553
 * 
 */
public class DespatchInDSNMapper implements Mapper<DespatchDetailsVO> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public DespatchDetailsVO map(ResultSet rs) throws SQLException {

		String airport = rs.getString("ASGPRT");

		DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs
				.getString("DSTEXGOFC"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS")); 
		//Added to include the DSN PK
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		despatchDetailsVO.setUldNumber(rs.getString("ULDNUM"));
		despatchDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		despatchDetailsVO.setAcceptedBags(rs.getInt("CSGACPBAG"));
		//despatchDetailsVO.setAcceptedWeight(rs.getDouble("CSGACPWGT"));
		despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CSGACPWGT")));//added by A-7371
		despatchDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
		//despatchDetailsVO.setReceivedWeight(rs.getDouble("RCVWGT"));
		despatchDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCVWGT")));//added by A-7371
		despatchDetailsVO.setPrevReceivedBags(rs.getInt("RCVBAG"));
		//despatchDetailsVO.setDeliveredWeight(rs.getDouble("DLVWGT"));
		despatchDetailsVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DLVWGT")));//added by A-7371
		despatchDetailsVO.setDeliveredBags(rs.getInt("DLVBAG"));
		//despatchDetailsVO.setPrevReceivedWeight(rs.getDouble("RCVWGT"));
		despatchDetailsVO.setPrevReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCVWGT")));//added by A-7371
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setAirportCode(airport);
		despatchDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		despatchDetailsVO.setContainerType(rs.getString("CONTYP"));
		despatchDetailsVO.setDestination(rs.getString("DSTCOD"));
		despatchDetailsVO.setPou(rs.getString("POU")); 
		
		if (rs.getTimestamp("FLTDAT") != null) {
			despatchDetailsVO.setFlightDate(new LocalDate(airport,
					Location.ARP, rs.getDate("FLTDAT")));
		}

		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));

		if (rs.getTimestamp("CSGDAT") != null) {
			despatchDetailsVO.setConsignmentDate(new LocalDate(airport,
					Location.ARP, rs.getTimestamp("CSGDAT")));
		}
		if (rs.getDate("ACPDAT") != null) {
			despatchDetailsVO.setAcceptedDate(new LocalDate(airport,
					Location.ARP, rs.getDate("ACPDAT")));
		}

		if (rs.getDate("RCVDAT") != null) {
			despatchDetailsVO.setReceivedDate(new LocalDate(airport,
					Location.ARP, rs.getDate("RCVDAT")));
		}

		despatchDetailsVO.setStatedBags(rs.getInt("CSGSTDBAG"));
		//despatchDetailsVO.setStatedWeight(rs.getDouble("CSGSTDWGT"));
		despatchDetailsVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CSGSTDWGT")));//added by A-7371
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));

		despatchDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
		despatchDetailsVO.setTransferFlag(rs.getString("TRAFLG"));
		despatchDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		 /*
		  * Added By RENO K ABRAHAM for Mail Allocation
		  * UBR number,Currency Code,Mail Rate is taken from MTKDSNULDSEG & MTKDSNCONSEG 
		  * CAPBKGMST is joined with these tables for lastUpdateTime of booking.
		  * CAPBKGFLTDTL is joined with CAPBKGMST for lastUpdateTime of Flight booking.
		  */
		 despatchDetailsVO.setUbrNumber(rs.getString("UBRNUM"));
		 Timestamp bookingUpdateTime = rs.getTimestamp("BKGLSTUPDTIM");
		 Timestamp bookingFlightDetailUpdateTime = rs.getTimestamp("BKGFLTUPDTIM");
		 LocalDate bookingLastUpdateTime=new LocalDate(LocalDate.NO_STATION, Location.NONE,true); 
		 
		 if(bookingUpdateTime != null && 
				 bookingFlightDetailUpdateTime != null) {
			 if(bookingUpdateTime.after(bookingFlightDetailUpdateTime)) {
				 bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE,
						 bookingUpdateTime);
			 }else {
				 bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE,
						 bookingFlightDetailUpdateTime);
			 }
			 if(bookingLastUpdateTime!=null) {
				 despatchDetailsVO.setBookingLastUpdateTime(bookingLastUpdateTime);
				 despatchDetailsVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
			 }
		 }
		 //END AirNZ CR : Mail Allocation
		return despatchDetailsVO;

	}

}
