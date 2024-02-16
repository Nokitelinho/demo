/*
 * OffloadMailBagMapper.java Created on Jun 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * @author A-1936
 *
 */
public class OffloadMailBagMapper implements
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
		   mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		   mailbagVO.setMailClass(rs.getString("MALCLS")); 
		   //Added to include  the DSN PK 
		   mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		   mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		   
		   mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		   mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		   mailbagVO.setYear(rs.getInt("YER"));
		   mailbagVO.setMailbagId(rs.getString("MALIDR"));
		   mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		   mailbagVO.setUldNumber(rs.getString("ULDNUM"));
		   mailbagVO.setPou(rs.getString("POU"));
		   mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		   mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		   mailbagVO.setScannedPort(rs.getString("ASGPRT"));
		   mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		   mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
           mailbagVO.setContainerType(rs.getString("CONTYP"));
		   mailbagVO.setAcceptanceFlag(rs.getString("ACPFLG")); 
		   mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		   Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"));
		   mailbagVO.setWeight(wgt);//added by A-7371
		   mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		   mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		   mailbagVO.setFinalDestination(rs.getString("DSTCOD"));
		   mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		   mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		   mailbagVO.setPaCode(rs.getString("POACOD"));
		   mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
		   String airport = rs.getString("ASGPRT");
		   if (rs.getDate("FLTDAT") != null) {
			   mailbagVO.setFlightDate(new LocalDate(airport,
						Location.ARP, rs.getDate("FLTDAT")));
			}
		   mailbagVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		   //Added as part of bug ICRD-153648 by A-5526 starts
		   mailbagVO.setOperationalStatus(rs.getString("OPRSTA"));
		 //Added as part of bug ICRD-153648 by A-5526 ends
		   return mailbagVO;
		  
	 }
	
}

