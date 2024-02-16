/*
 * MailbagHistoryMapper.java Created on June 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * 
 * @author A-2037 This class is used to map the ResultSet into MailbagHistoryVO
 * 
 */
public class MailbagHistoryMapper implements Mapper<MailbagHistoryVO> {
	
	
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailbagHistoryVO map(ResultSet rs) throws SQLException {
		
	
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagHistoryVO.setMailbagId(rs.getString("MALIDR"));
		if(rs.getString("SCNPRT") !=null){
			String scannedPort = rs.getString("SCNPRT");
			if (rs.getTimestamp("SCNDAT") != null) {
				mailbagHistoryVO.setScanDate(new LocalDate(scannedPort,	Location.ARP, rs.getTimestamp("SCNDAT")));
			}
			mailbagHistoryVO.setScannedPort(scannedPort);
			if (rs.getTimestamp("FLTDAT") != null) {
				mailbagHistoryVO.setFlightDate(new LocalDate(scannedPort, Location.ARP, rs.getTimestamp("FLTDAT")));
			}
		}
		
		
		//Added by A-5945 for ICRD-118215 starts
		if(rs.getString("DSTEXGOFC") !=null){
			mailbagHistoryVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		}
		if(rs.getString("ORGEXGOFC") !=null){
			mailbagHistoryVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		}
		
		if (rs.getString("DSN") !=null) {
			mailbagHistoryVO.setDsn(rs.getString("DSN"));
		}
		if (rs.getInt("MALHISIDR") !=0) {
			mailbagHistoryVO.setHistorySequenceNumber(rs.getInt("MALHISIDR"));
		}
		if (rs.getString("MALCTGCOD") !=null) {
			mailbagHistoryVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		}
		if (rs.getString("MALSUBCLS") !=null) {
			mailbagHistoryVO.setMailSubclass(rs.getString("MALSUBCLS"));
		}
		if (rs.getInt("YER") !=0) {
			mailbagHistoryVO.setYear(rs.getInt("YER"));
			//Added by A-5945 for ICRD-118215 ends
		}
		if (rs.getString("MALRMK") !=null) {
			//Added as a part of ICRD-197419 by a-7540
			mailbagHistoryVO.setMailRemarks(rs.getString("MALRMK"));
		}
		if (rs.getString("MALSTA") !=null) {
			mailbagHistoryVO.setMailStatus(rs.getString("MALSTA"));
		}
		String mailStatus;
		if (rs.getString("MALSTA") !=null) {
			mailStatus = rs.getString("MALSTA");
			if(!"RTN".equals(mailStatus)){
				mailbagHistoryVO.setContainerNumber(rs.getString("CONNUM"));
			}
		}
		if (rs.getString("FLTNUM") !=null) {
			mailbagHistoryVO.setFlightNumber(rs.getString("FLTNUM"));
		}
		if (rs.getLong("FLTSEQNUM") !=0) {
			mailbagHistoryVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		}
		if (rs.getString("CONTYP") !=null) {
			mailbagHistoryVO.setContainerType(rs.getString("CONTYP"));
		}
		if (rs.getString("FLTCARCOD") !=null) {
			mailbagHistoryVO.setCarrierCode(rs.getString("FLTCARCOD"));
		}
		
		if (rs.getString("POU") !=null) {
			mailbagHistoryVO.setPou(rs.getString("POU"));
		}
		if (rs.getString("SCNUSR") !=null) {
			mailbagHistoryVO.setScanUser(rs.getString("SCNUSR"));
		}
		if (rs.getTimestamp("UTCSCNDAT") != null) {
			mailbagHistoryVO.setUtcScanDate(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
					rs.getTimestamp("UTCSCNDAT")));
		}

		if (rs.getTimestamp("MSGTIM") != null) {
			mailbagHistoryVO.setMessageTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
					rs.getTimestamp("MSGTIM")));
		}
		if (rs.getString("POAULDFLG") == null) {
			mailbagHistoryVO.setPaBuiltFlag("N");
		} else if (rs.getString("POAULDFLG") != null) {
			mailbagHistoryVO.setPaBuiltFlag(rs.getString("POAULDFLG"));
		}
		if (rs.getString("MALSUBCLS") !=null) {
			//Added for ICRD-205027 starts
			mailbagHistoryVO.setMailClass(rs.getString("MALSUBCLS").substring(
					0, 1));
		}
		if (rs.getString("MALCTG") !=null) {
			mailbagHistoryVO.setMailCategoryCode(rs.getString("MALCTG"));
		}
		if (rs.getString("RSN") !=null) {
			mailbagHistoryVO.setRsn(rs.getString("RSN"));
		}
		//mailbagHistoryVO.setWeight(rs.getDouble("WGT"));
		mailbagHistoryVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
		//Added for ICRD-205027 ends
		//Added for ICRD-214795 starts
		if(rs.getTimestamp("REQDLVTIM") != null){
			mailbagHistoryVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
					rs.getTimestamp("REQDLVTIM")));
		}
		//Added for ICRD-214795 ends
		if(rs.getString("DOCOWRIDR") !=null && !rs.getString("DOCOWRIDR").isEmpty() &&  !"0".equals(rs.getString("DOCOWRIDR"))){
			StringBuilder awbnumber = new StringBuilder();
			String docOwnerId=rs.getString("DOCOWRIDR");
			if(docOwnerId!=null && docOwnerId.length()>3){    
				docOwnerId=docOwnerId.substring(1, 4);
			}else{    
				docOwnerId=rs.getString("DOCOWRIDR");                
			}
			awbnumber.append(docOwnerId);
			awbnumber.append("-");
		if(rs.getString("MSTDOCNUM") !=null){
				awbnumber.append(rs.getString("MSTDOCNUM"));	
				}
			mailbagHistoryVO.setMasterDocumentNumber(awbnumber.toString());
		}else if(rs.getString("MSTDOCNUM") !=null){
		mailbagHistoryVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));	
		}
		
		if(rs.getString("ORG") !=null){
			mailbagHistoryVO.setOrigin(rs.getString("ORG"));
		}
		if(rs.getString("MALTYP") !=null){
			mailbagHistoryVO.setMalType(rs.getString("MALTYP"));	
		}
		if(rs.getString("MALCLS") !=null){
			mailbagHistoryVO.setMailClass(rs.getString("MALCLS"));		
		}
		if(rs.getString("DST") !=null){
			mailbagHistoryVO.setDestination(rs.getString("DST"));
		}
		if(rs.getInt("ACPBAG") !=0){
			mailbagHistoryVO.setPieces(rs.getInt("ACPBAG"));
		}
		
		if(rs.getString("ARPCOD") !=null){
			mailbagHistoryVO.setAirportCode(rs.getString("ARPCOD"));
		}
		if(rs.getString("DLVSTA") !=null){
			mailbagHistoryVO.setDeliveryStatus(rs.getString("DLVSTA"));
		}
		if (rs.getString("ONNTIMDLVFLG") !=null) {
			mailbagHistoryVO.setOnTimeDelivery(rs.getString("ONNTIMDLVFLG"));
		}
		if(rs.getString("ADDINF") !=null){//Added by a-7871 for ICRD-240184
			mailbagHistoryVO.setAdditionalInfo(rs.getString("ADDINF"));
		}
			//Added by A-8164 for ICRD-323182
		mailbagHistoryVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACTWGT")));
		if (rs.getString("MALSRVLVL") !=null) {//added by A-8353 for ICRD-ICRD-327150
			mailbagHistoryVO.setMailSerLvl(rs.getString("MALSRVLVL"));
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
		if(rs.getString("POACOD") != null){
			mailbagHistoryVO.setPoacod(rs.getString("POACOD"));

		}					
		
		//added for IASCB-50357
		mailbagHistoryVO.setMailSource(rs.getString("MALSRC"));
		mailbagHistoryVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagHistoryVO.setAcceptancePostalContainerNumber(rs.getString("ACPPOACONNUM"));

		return mailbagHistoryVO;
	}
}