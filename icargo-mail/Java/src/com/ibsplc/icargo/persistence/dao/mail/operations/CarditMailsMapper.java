/*
 * CarditMailsMapper.java Created on Apr 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
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
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2553
 * 
 */
public class CarditMailsMapper implements Mapper<MailbagVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * @author a-2553 This method is used to set the values from the resultset
	 *         into Mapper
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public MailbagVO map(ResultSet rs) throws SQLException {
		log.log(Log.INFO, "Entering the CarditMailsMapper");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSPSRLNUM"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFF"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RCPSRLNUM"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFF"));
		//log.log(Log.FINE, "(rs.getString.substring(13,14)&&**&&**&&", (rs.getString("MALIDR")).substring(13,14));
		mailbagVO.setMailClass((rs.getString("MALSUBCOD")).substring(0,1));
		// Added to include the DSN PK
		mailbagVO.setMailSubclass(rs.getString("MALSUBCOD"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setYear(rs.getInt("DSPYER"));	
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSTRCPNUM"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("RCPRGDINS"));
		Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCPWGT"));
		mailbagVO.setWeight(wgt);//added by A-7371
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setPaCode(rs.getString("SDRIDR"));
		mailbagVO.setCarrierCode(rs.getString("CARCOD"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setUpliftAirport(rs.getString("POL"));
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
		//addded by a-7531 for icrd-192536
		mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));
		mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));	
		if(rs.getDate("DEPTIM")!=null){
			mailbagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("DEPTIM")));
		}
		mailbagVO.setUldNumber(rs.getString("CONNUM"));
		if (rs.getDate("CSGCMPDAT") != null) {
			mailbagVO.setConsignmentDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getDate("CSGCMPDAT")));
		}
		//Added as part of ICRD-214795 starts
		if (rs.getTimestamp("REQDLVTIM") != null) {
			mailbagVO.setReqDeliveryTime(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getTimestamp("REQDLVTIM")));
		}
		//Added as part of IASCB-33693 starts
				if (rs.getTimestamp("TRPSRVENDTIM") != null) {
					mailbagVO.setTransWindowEndTime(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, rs
									.getTimestamp("TRPSRVENDTIM")));
				}
		//Added as part of ICRD-214795 ends
		if("Y".equals(rs.getString("ACP"))){
			mailbagVO.setAccepted("Y");		
		}else{
			mailbagVO.setAccepted("N");
		}
		//to check error   IASCB-37980
		if("N".equals(mailbagVO.getAccepted())) { 
			if(rs.getString("SCNDTL")!=null && rs.getString("SCNDTL").trim().length()>0) {
				String[] scanDetails = rs.getString("SCNDTL").split("~");
				if(scanDetails!=null && scanDetails.length ==3) {
					String scannedDate = scanDetails[2];  
					LocalDate scanDate = new LocalDate(scanDetails[0], Location.ARP,true);
					scanDate.setDateAndTime(scannedDate);
					mailbagVO.setScannedDate(new LocalDate(scanDetails[0], Location.ARP,true));
					mailbagVO.setScannedUser(scanDetails[1]);
				}
				//E means Error
				mailbagVO.setAccepted("E");
			}	
		}
		if(rs.getString("SCNDAT")!=null && rs.getString("SCNDAT").trim().length()>0) {
			mailbagVO.setScannedDate(new LocalDate(
					rs.getString("POL"), Location.ARP, rs
							.getTimestamp("SCNDAT")));
			//E means Error 
			if("N".equals(mailbagVO.getAccepted())) {
			  mailbagVO.setAccepted("E");
			}
		}	
		//a-8061 added for ICRD-241291 begin
		mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
		mailbagVO.setSequenceNumber(rs.getInt("SEQNUM"));
		mailbagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		
		//a-8061 added for ICRD-241291 end
		
		//Added by A-7929 as part of ICRD_241437 starts ...   
		
		
		if(rs.getInt("FLTSEQNUM") != 0){
		mailbagVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		}
		if (rs.getString("POU") != null) {
			mailbagVO.setPou(rs.getString("POU"));
		}
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		if(rs.getDouble("VOL")!= 0) {
			mailbagVO.setVol(rs.getDouble("VOL"));//added by A-8353  
		//mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,rs.getDouble("VOL")));//added by A-7371
		}
		if(rs.getString("VOLUNT")!=null){  
			mailbagVO.setVolUnit(rs.getString("VOLUNT"));//added by A-8353
		}
		if(rs.getLong("MALSEQNUM") != 0){
			mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));//added by A-8353 for ICRD-338598
		}
		if (rs.getString("MALSRVLVL") != null) {
			mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
		}
		
		//Added by A-7929 as part of ICRD_241437 ends ...   
		
		mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		mailbagVO.setMailbagDataSource(rs.getString("MALSRC")); 
				
		log.log(Log.FINEST, "THE MAILBAG VO IS FOUND TO BE ", mailbagVO);
		return mailbagVO;

	}

}
