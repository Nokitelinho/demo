/*
 * ExistingMailbagsMapper.java Created on Apr 28, 2008
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

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * @author A-2553
 *
 */
public class ExistingMailbagsMapper implements
   Mapper<MailbagVO> {
 
	
	 /**
	  * @param rs
	  * @return
	  * @throws SQLException
	  */
	//private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	 public MailbagVO map(ResultSet rs)
	    throws SQLException{
		   MailbagVO mailbagVO=new MailbagVO();
		   mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		   mailbagVO.setMailbagId(rs.getString("MALIDR"));
		   mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		   mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		   mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		   mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		   mailbagVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		   mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		   mailbagVO.setFlightStatus(rs.getString("CLSFLG"));
		   mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		   mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		   mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		   mailbagVO.setMailClass(rs.getString("MALCLS"));
		   mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		   mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		   mailbagVO.setYear(rs.getInt("YER"));
		   mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		   mailbagVO.setContainerType(rs.getString("CONTYP"));
		   mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		   mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		   if(rs.getDate("FLTDAT")!=null){			   
			   mailbagVO.setFlightDate((new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("FLTDAT"))));				
		   }
		   /*
			  * Added By RENO K ABRAHAM for Mail Allocation
			  * UBR number,Currency Code,Mail Rate is taken from MTKDSNULDSEG & MTKDSNCONSEG 
			  * CAPBKGMST is joined with these tables for lastUpdateTime of booking.
			  * CAPBKGFLTDTL is joined with CAPBKGMST for lastUpdateTime of Flight booking.
			  */
			 mailbagVO.setUbrNumber(rs.getString("UBRNUM"));
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
					 mailbagVO.setBookingLastUpdateTime(bookingLastUpdateTime);
					 mailbagVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
				 }
			 }
			 //END AirNZ CR : Mail Allocation
		   return mailbagVO;	  
	 }	
}

