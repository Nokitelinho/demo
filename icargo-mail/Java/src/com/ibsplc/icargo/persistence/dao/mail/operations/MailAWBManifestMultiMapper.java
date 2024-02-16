/*
 * MailAWBManifestMultiMapper.java Created on Jan 17, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
//import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;  

/**
 *
 * @author A-1739
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		  Jan 17, 2007			A-1739		Created
 */
public class MailAWBManifestMultiMapper implements MultiMapper<MailManifestVO> {


	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String MASTERDOCUMENTNUMBER="MSTDOCNUM";
	private static final String SHIPMENTPREFIX="SHPPFX";
	private static final String CSGDOCNUM="CSGDOCNUM";
	private static final String ACPBAG="ACPBAG";
	private static final String ACPWGT="ACPWGT";
	/**
	 * Jan 17, 2007, A-1739
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 */
	public List<MailManifestVO> map(ResultSet rs) throws SQLException {
		log.entering("MailAWBManifestMultiMapper", "map");

		MailManifestVO mailManifestVO = new MailManifestVO();
		List<MailManifestVO> mailManifests = new ArrayList<MailManifestVO>();
		mailManifests.add(mailManifestVO);
		Collection<ContainerDetailsVO> containerDetails =
			new ArrayList<ContainerDetailsVO>();
		mailManifestVO.setContainerDetails(containerDetails);
		ContainerDetailsVO containerDetailsVO = null;
		DSNVO dsnVO = null;
		Collection<DSNVO> despatches = null;
		String containerNum = null;
		int segSerialNum  = 0;
		int docOwnerId = 0;
		String masterDocNum = null;
		String shipmentPrefix = null;
		int duplicateNum = 0;
		int sequenceNum = 0;
		String docOwnerCode = null;
	    String shipmentCode = null;
	    String shipmentDescription = null;

		String currContainerKey = null;
		String prevContainerKey = null;
		String currDespatchKey = null;
		String prevDespatchKey = null;
		int uldTotalbags = 0;
		double uldTotalWeight = 0;
		int totalbags = 0;
		double totalWeight = 0;
		String origin=null;
		String destination=null;
		Collection<MailbagVO> mailbags=null;
		MailbagVO mailbagVO=null;
		while(rs.next()) {
			 containerNum = rs.getString("ULDNUM");
			 segSerialNum = rs.getInt("SEGSERNUM");

			 currContainerKey = new StringBuilder().append(containerNum).
			 								append(segSerialNum).toString();
			 if(!currContainerKey.equals(prevContainerKey)) {
				 uldTotalbags = 0;
				 uldTotalWeight = 0;
				 dsnVO = null;
				 prevContainerKey = currContainerKey;
				 containerDetailsVO = new ContainerDetailsVO();
				 containerDetailsVO.setContainerNumber(containerNum);
				 containerDetailsVO.setSegmentSerialNumber(segSerialNum);
				 populateContainerDetailsVO(containerDetailsVO, rs);
				 containerDetails.add(containerDetailsVO);
				 despatches = new ArrayList<DSNVO>();
				 containerDetailsVO.setDsnVOs(despatches);
				 mailbags=new ArrayList<>();
				 containerDetailsVO.setMailDetails(mailbags);
				 containerDetailsVO.setContour(rs.getString("ULDCTR"));//A-8061 ICRD-318033
				 prevDespatchKey = null;
			 }
			//Added for ICRD-204912 starts
			 else{
				 containerDetailsVO.setTotalBags(containerDetailsVO.getTotalBags()+rs.getInt("CONBAGCNT"));
				/* try{
					 containerDetailsVO.setTotalWeight(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, 
							 UnitConstants.WEIGHT_UNIT_KILOGRAM,
							 containerDetailsVO.getTotalWeight()+rs.getDouble("CONBAGWGT")));
				   }catch(UnitException unitException) {
					   containerDetailsVO.setTotalWeight(containerDetailsVO.getTotalWeight()+
							   rs.getDouble("CONBAGWGT"));
				   }*/
				 try {
					containerDetailsVO.setTotalWeight(Measure.addMeasureValues(containerDetailsVO.getTotalWeight(),new Measure(UnitConstants.MAIL_WGT, rs.getDouble("CONBAGWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM)));//added by A-7371,modified by A-8353
				} catch (UnitException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE,"UnitException",e.getMessage());
				   }
			 }
			 //Added for ICRD-204912 ends
			 docOwnerId = rs.getInt("DOCOWRIDR");
			 masterDocNum = rs.getString("MSTDOCNUM");
			 shipmentPrefix=rs.getString("SHPPFX");
				duplicateNum = rs.getInt("DUPNUM");
				sequenceNum = rs.getInt("SEQNUM");
				docOwnerCode = rs.getString("DOCOWRCOD");
				shipmentCode =  rs.getString("SCCCOD");
				shipmentDescription =  rs.getString("SHPDES");
				origin =  rs.getString("ORG");
				destination =  rs.getString("DST");    
			 currDespatchKey = new StringBuilder().append(currContainerKey).
			 							append(docOwnerId).append(masterDocNum).
			 							append(duplicateNum).append(sequenceNum).
			 							append(docOwnerCode).append(origin).append(destination).toString();
			 log.log(Log.FINE, "\n\n currDespatchKey  ", currDespatchKey);
			log.log(Log.FINE, "\n\n prevDespatchKey  ", prevDespatchKey);
			//Modified by A-7794 as part of ICRD-250441
			if(!currDespatchKey.equals(prevDespatchKey)){
				 prevDespatchKey = currDespatchKey;
				 dsnVO = new DSNVO();
				 if((docOwnerId != 0 && masterDocNum!=null &&  masterDocNum.length() >0 )){
				 dsnVO.setDocumentOwnerIdentifier(docOwnerId);
				 dsnVO.setMasterDocumentNumber(masterDocNum);
				 dsnVO.setDuplicateNumber(duplicateNum);
				 dsnVO.setSequenceNumber(sequenceNum);
				 dsnVO.setDocumentOwnerCode(shipmentPrefix);
				 dsnVO.setShipmentCode(shipmentCode);
				 dsnVO.setShipmentDescription(shipmentDescription);
				 }
				 populateDSNDetails(rs, dsnVO);
				 despatches.add(dsnVO);
				 //uldTotalbags += dsnVO.getBags();
				 //uldTotalWeight += dsnVO.getWeight();
				 totalbags += dsnVO.getBags();
				 //totalWeight += dsnVO.getWeight();
				 totalWeight += dsnVO.getWeight().getRoundedDisplayValue();//added by A-7371
			 }
			else {
				
				 totalbags += rs.getInt(ACPBAG);
				 totalWeight += rs.getDouble(ACPWGT);
					dsnVO.setBags(dsnVO.getBags() + rs.getInt(ACPBAG));
				try {
					dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(), new Measure(UnitConstants.MAIL_WGT,
							rs.getDouble(ACPWGT), 0.0, UnitConstants.WEIGHT_UNIT_KILOGRAM)));
				} catch (UnitException e) {
					
					log.log(Log.SEVERE,e);
				}

			}
			mailbagVO=new MailbagVO();
			populateMailbagDetails(rs,mailbagVO);
			mailbags.add(mailbagVO); 
			try {
				containerDetailsVO.setNetWeight(Measure.addMeasureValues(containerDetailsVO.getNetWeight(),new Measure(UnitConstants.MAIL_WGT, rs.getDouble("NETWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM)));
			} catch (UnitException e) {
				log.log(Log.FINE,e);
			}
			 //containerDetailsVO.setTotalBags(uldTotalbags);
			 //containerDetailsVO.setTotalWeight(uldTotalWeight);
		}
		mailManifestVO.setTotalbags(totalbags);
		//mailManifestVO.setTotalWeight(totalWeight);
		//mailManifestVO.setTotalWeight(totalWeight);
		mailManifestVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,0.0,totalWeight,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
		log.exiting("MailAWBManifestMultiMapper", "mailManifests--"+mailManifests);
		return mailManifests;
	}
	/**
	 * TODO Purpose
	 * Jan 17, 2007, A-1739
	 * @param rs
	 * @param dsnVO
	 * @throws SQLException
	 */
	private void populateDSNDetails(ResultSet rs, DSNVO dsnVO) throws SQLException {
		dsnVO.setOrigin(rs.getString("ORG"));
		dsnVO.setDestination(rs.getString("DST"));
		dsnVO.setBags(rs.getInt(ACPBAG));
		//dsnVO.setWeight(rs.getDouble("ACPWGT"));
		dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble(ACPWGT),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
		dsnVO.setStatedBags(rs.getInt("STDBAG"));
		//dsnVO.setStatedWeight(rs.getDouble("STDWGT"));
		dsnVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("STDWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
		dsnVO.setShipmentCount(rs.getInt("SHPPCS"));
		//dsnVO.setShipmentWeight(rs.getDouble("SHPWGT"));
		dsnVO.setShipmentWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("SHPWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
		//dsnVO.setShipmentVolume(rs.getDouble("SHPVOL"));
		dsnVO.setShipmentVolume(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("SHPVOL")));//added by A-7371
		//dsnVO.setAcceptedVolume(rs.getDouble("ACPVOL"));
		dsnVO.setAcceptedVolume(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPVOL")));//added by A-7371
	}
	/**
	 * TODO Purpose
	 * Jan 17, 2007, A-1739
	 * @param containerDetailsVO
	 * @param rs
	 */
	private void populateContainerDetailsVO(
			ContainerDetailsVO containerDetailsVO, ResultSet rs)
	throws SQLException{
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setPol(rs.getString("POL"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		containerDetailsVO.setTotalBags(rs.getInt("CONBAGCNT")); 
		//containerDetailsVO.setTotalWeight(rs.getDouble("CONBAGWGT"));
		containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CONBAGWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371
		if("U".equals(rs.getString("CONTYP"))){ 
			//containerDetailsVO.setTareWeight(rs.getString("TARWGT"));
			containerDetailsVO.setTareWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("TARWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371
		}
		
		containerDetailsVO.setContainerType("U".equals(rs.getString("CONTYP"))?"N":"Y");
		containerDetailsVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACTULDWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		containerDetailsVO.setRemarks(Objects.nonNull(rs.getString("RMK")) && !rs.getString("RMK").isEmpty()?rs.getString("RMK"):"");
		containerDetailsVO.setContentId(rs.getString("CNTIDR"));
		containerDetailsVO.setGrossWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("GRSWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		containerDetailsVO.setNetWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("NETWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
	}
	private void populateMailbagDetails(ResultSet rs, MailbagVO mailbagVO)throws SQLException {
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setOrigin(rs.getString("ORG"));
		mailbagVO.setDestination(rs.getString("DST"));
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("MALWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		StringBuilder awbNumber=new StringBuilder();
		if(Objects.nonNull(rs.getString(MASTERDOCUMENTNUMBER)) && Objects.nonNull(rs.getString(SHIPMENTPREFIX))) {
		awbNumber.append(rs.getString(SHIPMENTPREFIX)).append("-").append(rs.getString(MASTERDOCUMENTNUMBER));
		}
		mailbagVO.setAwbNumber(awbNumber.toString());
		mailbagVO.setConsignmentNumber(!CSGDOCNUM.isEmpty()?CSGDOCNUM:"");
		mailbagVO.setVolume(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("MALVOL"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
	}
}
