/*
 * MailbagPresentMapper.java Created on Jun 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * TODO Add the purpose of this class
 *
 * @author A-1739
 *
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 23, 2006 A-1739 First Draft
 *
 */
public class MailbagPresentMapper implements Mapper<MailbagVO> {

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public MailbagVO map(ResultSet rs) throws SQLException {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		mailbagVO.setUldNumber(rs.getString("ULDNUM"));
		mailbagVO.setContainerType(rs.getString("CONTYP"));
		mailbagVO.setPol(rs.getString("POL"));
		mailbagVO.setFinalDestination(rs.getString("DST"));
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
	     mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
	     mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailbagVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		mailbagVO.setOperationalStatus(rs.getString("OPRSTA"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		if(mailbagVO.getFlightSequenceNumber() > 0 && mailbagVO.getPol()!=null && rs.getDate("FLTDAT")!=null) {
			LocalDate flightDate = new LocalDate(
					mailbagVO.getPol(), Location.ARP,  rs.getDate("FLTDAT"));
			mailbagVO.setFlightDate(new LocalDate(flightDate, true));
		}
		mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		mailbagVO.setPou(rs.getString("POU"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));  
		mailbagVO.setPaCode(rs.getString("POACOD"));
		mailbagVO.setTransferFlag(rs.getString("TRAFLG"));
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
		
		if(rs.getString("INVULDNUM")!=null){
			if((rs.getString("INVULDNUM").equals(rs.getString("INVCONNUM"))) && 
					!(rs.getString("INVULDNUM").startsWith(MailConstantsVO.CONST_BULK)) ){
				mailbagVO.setInventoryContainerType(MailConstantsVO.ULD_TYPE);
			}else {
				mailbagVO.setInventoryContainerType(MailConstantsVO.BULK_TYPE);
			}
		}
		if(rs.getString("INVCONNUM")!=null){
			mailbagVO.setInventoryContainer(rs.getString("INVCONNUM"));
		}else{
			mailbagVO.setInventoryContainer(rs.getString("CONNUM"));
			mailbagVO.setInventoryContainerType(rs.getString("CONTYP"));
		}
		mailbagVO.setFlightStatus(rs.getString("CLSFLG"));
		mailbagVO.setConsignmentDate(new LocalDate(
				LocalDate.NO_STATION, Location.NONE,  rs.getDate("DSPDAT")));
		return mailbagVO;
	}

}
