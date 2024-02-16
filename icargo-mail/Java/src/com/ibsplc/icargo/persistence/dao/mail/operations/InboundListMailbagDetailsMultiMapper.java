package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.InboundListMailbagDetailsMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	28-Dec-2018		:	Draft
 */
public class InboundListMailbagDetailsMultiMapper implements 
		MultiMapper<MailbagVO> {

	private Log log = LogFactory.getLogger("Mail Operations");

	private static final String CLASS_NAME = "InboundListMailbagDetailsMultiMapper";
	
	public List<MailbagVO> map(ResultSet rs) throws SQLException {
		
 		log.entering(CLASS_NAME, "map");
		
		List<MailbagVO> mailbagVOsList = new ArrayList<MailbagVO>();
		while (rs.next()) {
			MailbagVO mailbagVO = new MailbagVO();
			if(rs.getString("MALIDR")!=null){
			mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
			String mailbagId = rs.getString("MALIDR");
			mailbagVO.setMailbagId(mailbagId);
			mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
			mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
			mailbagVO.setMailClass(rs.getString("MALCLS"));
			mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
			mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
			mailbagVO.setYear(rs.getInt("YER"));
			mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
			mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
			mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
			mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND")); 
			mailbagVO.setSealNumber(rs.getString("SELNUM"));
			mailbagVO.setOrigin(rs.getString("ORGCOD"));
			mailbagVO.setDestination(rs.getString("DSTCOD"));
			if(0!=rs.getDouble("WGT"))
				mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"),rs.getDouble("DSPWGT"),rs.getString("DSPWGTUNT")));
			if(0!=rs.getDouble("WGT"))
				mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"))); 
			
			mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
			mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
			mailbagVO.setDeliveredFlag(rs.getString("DLVSTA"));
			mailbagVO.setTransferFlag(rs.getString("MALTRAFLG"));
			mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));
			//mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,rs.getDouble("VOL")));
			if(rs.getDouble("VOL")!= 0) {
				mailbagVO.setVol(rs.getDouble("VOL"));//added by A-8353  
			}
			if(rs.getString("VOLUNT")!=null){
				mailbagVO.setVolUnit(rs.getString("VOLUNT"));//added by A-8353
			}
			if(rs.getTimestamp("SCNDAT")!=null){
				mailbagVO.setScannedDate(new LocalDate(rs.getString("POU"),
				Location.ARP, rs.getTimestamp("SCNDAT")));
			 }
			mailbagVO.setMailStatus(rs.getString("MALSTA"));
			mailbagVO.setTransferFromCarrier(rs.getString("TRFCARCOD"));
			mailbagVO.setFromFightNumber(rs.getString("FLTNUM"));
			mailbagVO.setFromCarrierId(rs.getInt("FLTCARIDR"));
			mailbagVO.setFromFlightSequenceNumber(rs.getLong("FLTSEQNUM")); 
			mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
			//Added by A-7540
			mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));	
			mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));
			mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
			mailbagVO.setPaCode(rs.getString("MALPOA"));
			mailbagVO.setMailRemarks(rs.getString("MALRMK"));
			mailbagVO.setRoutingAvlFlag(rs.getString("RTGAVL"));
			mailbagVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
			mailbagVO.setTransitFlag(rs.getString("TRNFLG"));
			//Added by A-7929 as part of ICRD-346830
			mailbagVO.setOnTimeDelivery(rs.getString("ONNTIMDLVFLG"));
			mailbagVO.setScanningWavedFlag(rs.getString("SCNWVDFLG"));
			
			//String onward=new StringBuffer().append("")
			//mailbagVO.set
			if(rs.getTimestamp("TRPSRVENDTIM") != null){
				 mailbagVO.setTransWindowEndTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
							rs.getTimestamp("TRPSRVENDTIM")));
			 }
			 if(rs.getString("MALSRVLVL")!=null){
					mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
			}
			 if(rs.getTimestamp("REQDLVTIM") != null){
				 mailbagVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
							rs.getTimestamp("REQDLVTIM")));
			 }
			mailbagVOsList.add(mailbagVO);
			}
		}
		return mailbagVOsList;
	}
}
