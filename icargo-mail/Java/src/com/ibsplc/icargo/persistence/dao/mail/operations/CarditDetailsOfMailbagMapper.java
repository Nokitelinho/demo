
/*
 * CarditDetailsOfMailbagMapper.java Created on  Feb 27, 2007	
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
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;


/**
 * @author A-1883
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		   Feb 27, 2007			  	 A-1883		Created
 */
public class CarditDetailsOfMailbagMapper implements Mapper<MailbagHistoryVO> {

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailbagHistoryVO map(ResultSet rs) throws SQLException {
		String stationCode = rs.getString("STNCOD");
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagHistoryVO.setMailbagId(rs.getString("MALIDR"));
		if (rs.getTimestamp("CDTRCVDAT") != null) {
			mailbagHistoryVO.setScanDate(new LocalDate(stationCode,
					Location.ARP, rs.getTimestamp("CDTRCVDAT")));
			mailbagHistoryVO.setMessageTime(new LocalDate(stationCode,
					Location.ARP, rs.getTimestamp("CDTRCVDAT")));
		}
		mailbagHistoryVO.setFlightNumber(rs.getString("FLTNUM"));
		if(rs.getTimestamp("FLTDAT")!=null)
			{
				mailbagHistoryVO.setFlightDate(new LocalDate(stationCode, Location.ARP, rs.getTimestamp("FLTDAT")));
			}
		//Added by A-7540
		mailbagHistoryVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		mailbagHistoryVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		mailbagHistoryVO.setDsn(rs.getString("DSN"));
		mailbagHistoryVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagHistoryVO.setYear(rs.getInt("YER"));
		mailbagHistoryVO.setMailRemarks(rs.getString("MALRMK"));
		mailbagHistoryVO.setMailClass(rs.getString("MALSUBCLS").substring(0,1));
		mailbagHistoryVO.setMailCategoryCode(rs.getString("MALCTG"));
		mailbagHistoryVO.setRsn(rs.getString("RSN"));
		mailbagHistoryVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));
		if(rs.getTimestamp("REQDLVTIM") != null){
			mailbagHistoryVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
					rs.getTimestamp("REQDLVTIM")));
		}
		
		
		mailbagHistoryVO.setCarrierCode(rs.getString("FLTCARCOD"));
		//Added A-8164-ICRD-293138 starts
		mailbagHistoryVO.setCarrierId(rs.getInt("CARIDR"));  
		mailbagHistoryVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailbagHistoryVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		//Added A-8164-ICRD-293138 ends
		mailbagHistoryVO.setMailStatus(MailConstantsVO.CARDIT_EVENT);
		mailbagHistoryVO.setScannedPort(stationCode);
		mailbagHistoryVO.setPou(rs.getString("POU"));
		mailbagHistoryVO.setScanUser(rs.getString("SCNUSR"));
		//Added by A-8164 for ICRD-323182
		mailbagHistoryVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACTWGT")));
		if (rs.getString("MALSRVLVL") !=null) {//added by A-8353 for ICRD-ICRD-327150
			mailbagHistoryVO.setMailSerLvl(rs.getString("MALSRVLVL"));
		}
		if (rs.getString("ORGCOD") !=null) {
			mailbagHistoryVO.setOrigin(rs.getString("ORGCOD"));
		}
		if (rs.getString("DSTCOD") !=null) {
			mailbagHistoryVO.setDestination(rs.getString("DSTCOD"));
		}
		if(rs.getTimestamp("CSGDAT") != null){
			mailbagHistoryVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
					rs.getTimestamp("CSGDAT")));
		}
		if(rs.getString("CSGDOCNUM") != null){
			mailbagHistoryVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		}
		if(rs.getTimestamp("TRPSRVENDTIM") != null){
			mailbagHistoryVO.setTransportSrvWindow(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
					rs.getTimestamp("TRPSRVENDTIM")));
		}
		return mailbagHistoryVO;
	}

}
