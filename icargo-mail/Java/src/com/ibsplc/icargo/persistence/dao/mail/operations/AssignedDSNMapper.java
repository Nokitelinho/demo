/*
 * AssignedDSNMapper.java 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * 
 *
 */
public class AssignedDSNMapper implements Mapper<DespatchDetailsVO> {

	private Log log = LogFactory.getLogger("MTK_DEFAULTS");
	private DSNEnquiryFilterVO dSNEnquiryFilterVO;	
	/**
	 * 
	 * @param dSNEnquiryFilterVO
	 */
	public AssignedDSNMapper(DSNEnquiryFilterVO dSNEnquiryFilterVO) {
		super();
		this.dSNEnquiryFilterVO = dSNEnquiryFilterVO;
	}
	/**
	 * Constructor
	 */
	public AssignedDSNMapper() {
		super();
		
	}	
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

		String airport = rs.getString("ARPCOD");
		log.log(Log.FINE,"inside mapper==&&&>");
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
		despatchDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		despatchDetailsVO.setUldNumber(rs.getString("ULDNUM"));
		despatchDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		despatchDetailsVO.setAcceptedBags(rs.getInt("ACPBAG"));
		//despatchDetailsVO.setAcceptedWeight(rs.getDouble("ACPWGT"));
		despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT")));
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
		despatchDetailsVO.setCapNotAcceptedStatus(rs.getString("DSNSTA"));
		despatchDetailsVO.setDelivered("Y".equals(rs.getString("DLVFLG"))?true:false);//A-5219
		despatchDetailsVO.setPltEnabledFlag("Y".equals(rs.getString("PLTENBFLG"))?"true":"false");
		despatchDetailsVO.setCsgOrigin(rs.getString("ORGARPCOD")); //added by a-5133 as part of CR ICRD-19158 
		despatchDetailsVO.setCsgDestination(rs.getString("DSTARPCOD")); //added by a-5133 as part of CR ICRD-19158 
		 
		if (rs.getString("POU") != null) {
			despatchDetailsVO.setPou(rs.getString("POU")); 
			
		}else if(rs.getString("DSTCOD")!=null){
			despatchDetailsVO.setPou(rs.getString("DSTCOD")); 
		}else {
			despatchDetailsVO.setPou(airport);
		}
		
		if (rs.getTimestamp("FLTDAT") != null && 
				airport != null && airport.trim().length() > 0) {
			despatchDetailsVO.setFlightDate(new LocalDate(airport,
					Location.ARP, rs.getDate("FLTDAT")));
		}

		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));

		if (rs.getTimestamp("CSGDAT") != null  && 
				airport != null && airport.trim().length() > 0) {
			despatchDetailsVO.setConsignmentDate(new LocalDate(airport,
					Location.ARP, rs.getTimestamp("CSGDAT")));
		}
		if (rs.getDate("ACPDAT") != null  && 
				airport != null && airport.trim().length() > 0) {
			despatchDetailsVO.setAcceptedDate(new LocalDate(airport,
					Location.ARP, rs.getDate("ACPDAT")));
		}

		if (rs.getDate("RCVDAT") != null && 
				airport != null && airport.trim().length() > 0) {
			despatchDetailsVO.setReceivedDate(new LocalDate(airport,
					Location.ARP, rs.getDate("RCVDAT")));
		}

		despatchDetailsVO.setStatedBags(rs.getInt("STDBAG"));
		//despatchDetailsVO.setStatedWeight(rs.getDouble("STDWGT"));
		despatchDetailsVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("STDWGT")));//added by A-7371
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		if(dSNEnquiryFilterVO != null && dSNEnquiryFilterVO.getOperationType()!= null){
			despatchDetailsVO.setOperationType(dSNEnquiryFilterVO.getOperationType());
		}

		despatchDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
		despatchDetailsVO.setTransferFlag(rs.getString("TRAFLG"));
		despatchDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		despatchDetailsVO.setRemarks(rs.getString("RMKS"));	
		despatchDetailsVO.setTransferredPieces(rs.getInt("TRFBAG"));
		//despatchDetailsVO.setTransferredWeight(rs.getDouble("TRFWGT"));
		despatchDetailsVO.setTransferredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("TRFWGT")));//added by A-7371
		despatchDetailsVO.setAlreadyTransferredPieces(rs.getInt("TRFBAG"));
		//despatchDetailsVO.setAlreadyTransferredWeight(rs.getDouble("TRFWGT"));
		
		
		
		if (rs.getString("LATSTA") != null) {
			despatchDetailsVO.setLatestStatus(rs.getString("LATSTA")); 			
		}
		if (rs.getString("OFLBAG") != null) {
			despatchDetailsVO.setOffloadedBags(rs.getInt("OFLBAG")); 			
		}
		if (rs.getString("OFLWGT") != null) {
			//despatchDetailsVO.setOffloadedWeight(rs.getDouble("OFLWGT"));
			despatchDetailsVO.setOffloadedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("OFLWGT")));//added by A-7371
		}		
		
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
		return despatchDetailsVO;
		

	}
	

}
