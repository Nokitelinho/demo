/*
 * MailArrivalMultiMapper.java Created on Aug 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The mapper for fetching arrival details of a flight
 *
 * @author A-1739
 *
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Aug 7, 2006 A-1739 First Draft
 */
public class MailArrivalMultiMapper implements MultiMapper<ContainerDetailsVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String MALIDR = "MALIDR";
	private static final String ULD_NUMBER = "ULDNUM";

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {

		List<ContainerDetailsVO> containerDetails = null;
		String currContainerKey = null;
		String prevContainerKey = null;
		ContainerDetailsVO containerDetailsVO = null;
		DSNVO dsnVO = null;
		Collection<DSNVO> dsnVOs = null;
		Collection<MailbagVO> mailbagVOs = null;
		String currDSNKey = null;
		String prevDSNKey = null;
		String currMailKey = null;
		String prevMailKey = null;
		int prevRoutingNum=0;
		String currMailbagKey = null;
		String prevMailbagKey = null;
		MailbagVO mailbagVO = null;
		String currDespatchKey = null;
		String prevDespatchKey = null;
		DespatchDetailsVO despatchDetailsVO=null;
		Collection<DespatchDetailsVO> despatches = null;
		containerDetails = new ArrayList<ContainerDetailsVO>();
		Map<String, ArrayList<String>> csgDocForDSN = new HashMap<String, ArrayList<String>>();
		String childId = "";
		String dsnKey = null;
		String prevChildId = "";
		StringBuilder onwardFlightBuilder = null;
		String pou = null;
		String flightNumber = null;
		String carrierCode = null;
		while (rs.next()) {

			currContainerKey = new StringBuilder().append(
					rs.getString("CMPCOD")).append(rs.getInt("FLTCARIDR"))
					.append(rs.getString("FLTNUM")).append(
							rs.getLong("FLTSEQNUM")).append(
							rs.getInt("SEGSERNUM")).append(
							rs.getString(ULD_NUMBER)).toString();

			log.log(Log.FINE, "currContainerKey ", currContainerKey);
			if (!currContainerKey.equals(prevContainerKey)) {
				containerDetailsVO = new ContainerDetailsVO();
				populateContainerDetails(containerDetailsVO, rs);
				prevRoutingNum=0;
				dsnVOs = new ArrayList<DSNVO>();
				containerDetailsVO.setDsnVOs(dsnVOs);
				mailbagVOs = new ArrayList<MailbagVO>();
				despatches=new ArrayList<DespatchDetailsVO>();
				containerDetailsVO.setMailDetails(mailbagVOs);
				containerDetailsVO.setDesptachDetailsVOs(despatches);
				containerDetails.add(containerDetailsVO);
			    prevContainerKey = currContainerKey;
			    onwardFlightBuilder = null;
			}


			dsnKey = new StringBuilder().append(rs.getString("DSN"))
					.append(rs.getString("ORGEXGOFC"))
					.append(rs.getString("DSTEXGOFC")).append(
							rs.getString("MALSUBCLS")).append(
							rs.getString("MALCTGCOD")).append(rs.getInt("YER"))
					.toString();
			
			
			currDSNKey = new StringBuilder().
						append(currContainerKey).append(dsnKey).toString();
			log.log(Log.FINE, "CurrDSNKey ", currDSNKey);
			if (rs.getString("DSN") != null){
				if(dsnKey != null && dsnKey.trim().length() > 0 && 
						!(csgDocForDSN.containsKey(dsnKey))){
					ArrayList<String> csgDetails = new ArrayList<String>();
					if((rs.getString("CSGDOCNUM") != null && 
							rs.getString("CSGDOCNUM").length() > 0) &&
							(rs.getString("POACOD")!=null && 
									rs.getString("POACOD").length() > 0)){
						csgDetails.add(rs.getString("CSGDOCNUM"));
						csgDetails.add(rs.getString("POACOD"));
						//CONSTRUCTING THE MAP FOR DSN & CSG DETAILS
						csgDocForDSN.put(dsnKey, csgDetails);
					}
				}
				if(!currDSNKey.equals(prevDSNKey)) {

					dsnVO = new DSNVO();
					populateDSNDetails(dsnVO, rs);
					dsnVOs.add(dsnVO);
					prevDSNKey = currDSNKey;
					prevMailKey=rs.getString(MALIDR);
				}else{
					if(rs.getString("CSGDOCNUM")!=null && 
							rs.getString("CSGDOCNUM").length() > 0){
						dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
					}
					if(rs.getString("POACOD")!=null && 
							rs.getString("POACOD").length() > 0){
						dsnVO.setPaCode(rs.getString("POACOD"));
					}
					currMailKey = rs.getString(MALIDR);
					
					
					if (currMailKey != null  
							&& !currMailKey.equals(prevMailKey)) {
						
					dsnVO.setBags(dsnVO.getBags()+rs.getInt("ACPBAG"));
					//dsnVO.setWeight(dsnVO.getWeight()+rs.getDouble("ACPWGT"));
					try {
						dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(),new Measure(UnitConstants.MAIL_WGT, rs.getDouble("ACPWGT"))));
					} catch (UnitException e4) {
						// TODO Auto-generated catch block
						log.log(Log.SEVERE,"UnitException",e4.getMessage());
					}//added by A-7371
						
					dsnVO.setReceivedBags(dsnVO.getReceivedBags()+rs.getInt("DSNRCVBAG"));
					//dsnVO.setReceivedWeight(dsnVO.getReceivedWeight()+rs.getDouble("DSNRCVWGT"));
					try {
						dsnVO.setReceivedWeight(Measure.addMeasureValues(dsnVO.getReceivedWeight(),new Measure(UnitConstants.MAIL_WGT, rs.getDouble("DSNRCVWGT"))));
					} catch (UnitException e3) {
						// TODO Auto-generated catch block
						log.log(Log.SEVERE,"UnitException",e3.getMessage());
					}//added by A-7371
					dsnVO.setDeliveredBags(dsnVO.getDeliveredBags()+rs.getInt("DLVBAG"));
					//dsnVO.setDeliveredWeight(dsnVO.getDeliveredWeight()+rs.getDouble("DLVWGT"));
					try {
						dsnVO.setDeliveredWeight(Measure.addMeasureValues(dsnVO.getDeliveredWeight(), new Measure(UnitConstants.MAIL_WGT, rs.getDouble("DLVWGT"))));
					} catch (UnitException e2) {
						// TODO Auto-generated catch block
						log.log(Log.SEVERE,"UnitException",e2.getMessage());
					}//added by A-7371
					dsnVO.setPrevReceivedBags(dsnVO.getPrevReceivedBags()+rs.getInt("DSNRCVBAG"));
					//dsnVO.setPrevReceivedWeight(dsnVO.getPrevReceivedWeight()+rs.getDouble("DSNRCVWGT"));
					try {
						dsnVO.setPrevReceivedWeight(Measure.addMeasureValues(dsnVO.getPrevReceivedWeight(),new Measure(UnitConstants.MAIL_WGT,  rs.getDouble("DSNRCVWGT"))));
					} catch (UnitException e1) {
						// TODO Auto-generated catch block
						log.log(Log.SEVERE,"UnitException",e1.getMessage());
					}//added by A-7371
					dsnVO.setPrevDeliveredBags(dsnVO.getPrevDeliveredBags()+rs.getInt("DLVBAG"));
					//dsnVO.setPrevDeliveredWeight(dsnVO.getPrevDeliveredWeight()+rs.getDouble("DLVWGT"));
					try {
						dsnVO.setPrevDeliveredWeight(Measure.addMeasureValues(dsnVO.getPrevDeliveredWeight(), new Measure(UnitConstants.MAIL_WGT, rs.getDouble("DLVWGT"))));
					} catch (UnitException e) {
						// TODO Auto-generated catch block
						log.log(Log.SEVERE,"UnitException",e.getMessage());
					}//added by A-7371
					prevMailKey = currMailKey;
					}else {
						prevMailKey = currMailKey;
					}
				}
			}

			if (dsnVO != null && MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag()) && Objects.nonNull(rs.getString(MALIDR))) {

					currMailbagKey = rs.getString(ULD_NUMBER)+rs.getString(MALIDR);
					log.log(Log.FINE, "curramilbag key ", currMailbagKey);
					if (currMailbagKey != null
							&& !currMailbagKey.equals(prevMailbagKey)) {
						mailbagVO = new MailbagVO();
						mailbagVO.setCarrierId(
								containerDetailsVO.getCarrierId());
						mailbagVO.setFlightNumber(
								containerDetailsVO.getFlightNumber());
						mailbagVO.setFlightSequenceNumber(
								containerDetailsVO.getFlightSequenceNumber());
						mailbagVO.setSegmentSerialNumber(
								containerDetailsVO.getSegmentSerialNumber());
						mailbagVO.setPou(
								containerDetailsVO.getPou());
						mailbagVO.setPol(
								containerDetailsVO.getPol());
						mailbagVO.setContainerNumber(
								containerDetailsVO.getContainerNumber());
						mailbagVO.setContainerType(
							containerDetailsVO.getContainerType()); 
						/*
						 * Added By Karthick V 
						 * 
						 */
						
						populateMailbagDetails(mailbagVO, rs);
						if(MailConstantsVO.FLAG_YES.equals(
								mailbagVO.getTransferFlag())) {
							dsnVO.setTransferFlag(MailConstantsVO.FLAG_YES);
						}
						prevMailbagKey = currMailbagKey;
						mailbagVOs.add(mailbagVO);
					}
			}
			
			log.log(Log.FINE, "RTGSERNUM :", rs.getInt("RTGSERNUM"));
			if (rs.getInt("RTGSERNUM") > 0) {
				childId = new StringBuffer(currContainerKey).append(
						rs.getString("RTGSERNUM")).toString();
				log.log(Log.FINE, "THE CHILD ID IS FOUND TO BE ", childId);
				int currRoutingNum=rs.getInt("RTGSERNUM");
				

				if (!childId.equals(prevChildId)) {
					
					if(currRoutingNum > prevRoutingNum){
					
			        flightNumber = rs.getString("ONWFLTNUM");
					pou = rs.getString("RTGPOU");
					carrierCode = rs.getString("ONWFLTCARCOD");
					if (rs.getString("ONWFLTDAT") != null
							&& flightNumber != null
							&& flightNumber.trim().length() > 0
							&& carrierCode != null
							&& carrierCode.trim().length() > 0 
							&& pou != null
							&& pou.trim().length() > 0) {
		
						if (onwardFlightBuilder == null) {
							onwardFlightBuilder = new StringBuilder();
						} else {
							onwardFlightBuilder.append(",");
						}
		
						onwardFlightBuilder.append(carrierCode).append("-")
								.append(flightNumber).append("-").append(
										rs.getString("ONWFLTDAT")).append("-")
								.append(pou);
					}
					if (onwardFlightBuilder != null && !currContainerKey.contains("BULK")) {
						containerDetailsVO.setOnwardFlights(onwardFlightBuilder.toString());
					}
					prevChildId = childId;
					prevRoutingNum=currRoutingNum;
					}
				}
			}
			
            /*
             * Modified By Karthick V as the part of the NCA Mail Tracking Bug fIx 
             */ 			
			if(rs.getString("ULDCSGDOCNUM") != null&&
					MailConstantsVO.CARDITENQ_MODE_DESP.equals(rs.getString("MALTYP"))){
                currDespatchKey = new StringBuilder().
				 append(currDSNKey).append(rs.getString("ULDCSGDOCNUM"))
				 .append(rs.getString("ULDPOACOD")).append(
				 rs.getInt("ULDCSGSEQNUM")).toString();
                log.log(Log.FINE, "THE CONSIGNEMENT KEY", currDespatchKey);
				if (!currDespatchKey.equals(prevDespatchKey)) {
					despatchDetailsVO = new DespatchDetailsVO(); 
					despatchDetailsVO.setContainerType(containerDetailsVO.getContainerType());
					
					populateDespatchDetailsForULD(despatchDetailsVO, rs);
					if(MailConstantsVO.FLAG_YES.equals(
							despatchDetailsVO.getTransferFlag())) {
						dsnVO.setTransferFlag(MailConstantsVO.FLAG_YES);
					}
					despatches.add(despatchDetailsVO);
					prevDespatchKey = currDespatchKey;
				 }
			    }else if (rs.getString("CONCSGDOCNUM") != null&&
			    		MailConstantsVO.CARDITENQ_MODE_DESP.equals(rs.getString("MALTYP"))){
               currDespatchKey = new StringBuilder().
				  append(currDSNKey).append(rs.getString("CONCSGDOCNUM"))
				  .append(rs.getString("CONPOACOD")).append(
				  rs.getInt("CONCSGSEQNUM")).toString();
                 log.log(Log.FINE, "THE CONSIGNEMENT KEY", currDespatchKey);
				if (!currDespatchKey.equals(prevDespatchKey)) {
					despatchDetailsVO = new DespatchDetailsVO();
					despatchDetailsVO.setContainerType(containerDetailsVO.getContainerType());
					populateDespatchDetailsForBulk(despatchDetailsVO, rs);
					despatches.add(despatchDetailsVO);
					prevDespatchKey = currDespatchKey;
				}
      		 }
			//Added by A-6991 for ICRD-259900
			Date date = rs.getDate("FLTDAT");
			if (date != null) {
				containerDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
						Location.NONE, date));	
			}
		}
		//SUGGESTING CONSIGNMENT DOC NUMBER AND POACOD FOR SIMILAR DSNS
		//STARTS
		autoSuggestConsignmentForDSN(containerDetails,csgDocForDSN);
		//END
		return containerDetails;
	}

	/**
	 * @param containerDetails
	 * @param csgDocForDSN
	 */
	private void autoSuggestConsignmentForDSN(List<ContainerDetailsVO> containerDetails,
			Map<String, ArrayList<String>> csgDocForDSN ){
		if(containerDetails != null){
			String dsnKey = null;
			for(ContainerDetailsVO cntDetails : containerDetails){
				for(DSNVO dsnVO : cntDetails.getDsnVOs()){
					if(dsnVO.getCsgDocNum() == null || 
							(dsnVO.getCsgDocNum() != null && dsnVO.getCsgDocNum().length() == 0)){
						dsnKey = new StringBuilder().append(dsnVO.getDsn())
						.append(dsnVO.getOriginExchangeOffice())
						.append(dsnVO.getDestinationExchangeOffice())
						.append(dsnVO.getMailSubclass())
						.append(dsnVO.getMailCategoryCode())
						.append(String.valueOf(dsnVO.getYear()))
						.toString();
						if(csgDocForDSN != null && 
								csgDocForDSN.size() > 0 && 
								csgDocForDSN.containsKey(dsnKey)){
							ArrayList<String> csgDetails = csgDocForDSN.get(dsnKey);
							if(csgDetails != null){
								dsnVO.setCsgDocNum(csgDetails.get(0));
								dsnVO.setPaCode(csgDetails.get(1));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * A-1739
	 *
	 * @param mailbagVO
	 * @param rs
	 * @throws SQLException
	 */
	
	private void populateMailbagDetails(MailbagVO mailbagVO, ResultSet rs)
			throws SQLException {
		Collection<DamagedMailbagVO> damagedVOs = new ArrayList <DamagedMailbagVO>();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		String mailbagId = rs.getString(MALIDR);
		mailbagVO.setMailbagId(mailbagId);
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setMailSubclass( rs.getString("MALSUBCLS"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		//mailbagVO.setWeight(rs.getDouble("WGT"));
		 mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
		 mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		 mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
		 mailbagVO.setDeliveredFlag(rs.getString("DLVSTA"));
		 mailbagVO.setTransferFlag(rs.getString("MALTRAFLG"));
		 //mailbagVO.setVolume(rs.getDouble("VOL"));
		 mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,rs.getDouble("VOL")));//added by A-7371
		 mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
		 mailbagVO.setMraStatus(rs.getString("MALMRASTA"));
		 if(rs.getTimestamp("SCNDAT")!=null){
			mailbagVO.setScannedDate(new LocalDate(rs.getString("POU"),
			Location.ARP, rs.getTimestamp("SCNDAT")));
		 }
		 //Added as part of Bug ICRD-129234 starts
		 if(rs.getTimestamp("SCNDATTIME")!=null){
				mailbagVO.setLatestScannedDate(new LocalDate(rs.getString("POU"),
				Location.ARP, rs.getTimestamp("SCNDATTIME")));
			 }
		 //Added as part of Bug ICRD-129234 ends
		 mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		 mailbagVO.setSealNumber(rs.getString("SELNUM")); 
		 mailbagVO.setArrivalSealNumber(rs.getString("ARRSELNUM"));  
		 
		 //Added as a part of ICRD-197419 by a-7540
		 mailbagVO.setMailRemarks(rs.getString("MALRMK"));	
		 
		 /*
		  * Added By Karthick V as the Part of the Optimistic Locking
		  * 
		  * 
		  */
		 if(rs.getTimestamp("MALLSTUPDTIM")!=null){
			mailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, rs.getTimestamp("MALLSTUPDTIM")));
		 }
		 mailbagVO.setAcceptanceFlag(rs.getString("MALACPSTA"));
		 /*
		  * Added BY Karthick V to set the Inventory Container and the Type of the Inventory Container..
		  * 
		  */
		 mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));             
		 mailbagVO.setInventoryContainer(rs.getString("INVCONNUM"));
		 mailbagVO.setMailStatus(rs.getString("MALSTA"));
		 mailbagVO.setFromFightNumber(rs.getString("MALFLTNUM"));
		 mailbagVO.setFromCarrierId(rs.getInt("MALFLTCARIDR"));
		 mailbagVO.setFromFlightSequenceNumber(rs.getLong("MALFLTSEQNUM"));
		 //Added as part of ICRD-211205 starts
		 mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
		 mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
		 mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));
		 mailbagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		 mailbagVO.setSequenceNumber(rs.getInt("SEQNUM"));
		//Added as part of ICRD-211205 ends
		 /*
		  * Done as a part og Bug Fix. BUG 24191
		  * START
		  */
			 /*if(rs.getString("INVULDNUM")!=null){
			  mailbagVO.setInventoryContainerType(rs.getString("INVCONNUM").equals(rs.getString("INVULDNUM"))?MailConstantsVO.ULD_TYPE:MailConstantsVO.BULK_TYPE);
			 }*/
			if(rs.getString("INVULDNUM")!=null){
				if((rs.getString("INVULDNUM").equals(rs.getString("INVCONNUM"))) && 
						!(rs.getString("INVULDNUM").startsWith(MailConstantsVO.CONST_BULK)) ){
					mailbagVO.setInventoryContainerType(MailConstantsVO.ULD_TYPE);
				}else {
					mailbagVO.setInventoryContainerType(MailConstantsVO.BULK_TYPE);
				}
			}
			if(rs.getString("DMGCOD") != null) {
				DamagedMailbagVO damagedMailbagVO = populateDamageDetails(rs);
				damagedVOs.add(damagedMailbagVO);
				mailbagVO.setDamagedMailbags(damagedVOs);
				}
			//END
			//Added as part of ICRD-334027 starts
			Date date = rs.getDate("FLTDAT");
			if (date != null) {
				mailbagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
						Location.NONE, date));	
			}
			//Added as part of ICRD-334027 ends
			
			 //Added by A-7929 as part of ICRD-346830
			mailbagVO.setOnTimeDelivery(rs.getString("ONNTIMDLVFLG"));
			mailbagVO.setScanningWavedFlag(rs.getString("SCNWVDFLG"));
			mailbagVO.setDestination(rs.getString("DSTCOD"));
			mailbagVO.setPaCode(rs.getString("POACOD"));
			mailbagVO.setOrigin(rs.getString("ORGCOD"));
			mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
			mailbagVO.setMailDestination(rs.getString("DSTCOD"));
			mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));

	}
	private DamagedMailbagVO populateDamageDetails(ResultSet rs) throws SQLException{
		DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
		damagedMailbagVO.setDamageCode(rs.getString("DMGCOD"));
		damagedMailbagVO.setAirportCode(rs.getString("ARPCOD"));
		damagedMailbagVO.setDamageDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("DMGDAT")));
		damagedMailbagVO.setDamageDescription(rs.getString("DMGDES"));
		damagedMailbagVO.setRemarks(rs.getString("RMK"));
		return damagedMailbagVO;
	}

	/**
	 * A-1739
	 *
	 * @param dsnVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateDSNDetails(DSNVO dsnVO, ResultSet rs)
			throws SQLException {
		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		dsnVO.setMailClass(rs.getString("MALCLS"));
         //Added to include the DSN PK
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
		dsnVO.setBags(rs.getInt("ACPBAG"));
		//dsnVO.setWeight(rs.getDouble("ACPWGT"));
		dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT")));//added by A-7371
		dsnVO.setReceivedBags(rs.getInt("DSNRCVBAG"));
		//dsnVO.setReceivedWeight(rs.getDouble("DSNRCVWGT"));
		dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNRCVWGT")));//added by A-7371
		dsnVO.setDeliveredBags(rs.getInt("DLVBAG"));
		//dsnVO.setDeliveredWeight(rs.getDouble("DLVWGT"));
		dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DLVWGT")));//added by A-7371
		dsnVO.setPrevReceivedBags(rs.getInt("DSNRCVBAG"));
		//dsnVO.setPrevReceivedWeight(rs.getDouble("DSNRCVWGT"));
		dsnVO.setPrevReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNRCVWGT")));//added by A-7371
		dsnVO.setPrevDeliveredBags(rs.getInt("DLVBAG"));
		//dsnVO.setPrevDeliveredWeight(rs.getDouble("DLVWGT"));
		dsnVO.setPrevDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DLVWGT")));//added by A-7371
		//Added as part of bug-fix icrd-16928.
		dsnVO.setPol(rs.getString("ORGCOD")) ;
		dsnVO.setDestination(rs.getString("DSTCOD")) ;
		
		//Added for SAA 403 STARTS
		if(rs.getString("CSGDOCNUM")!=null){
			dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
		}else{
			dsnVO.setCsgDocNum(rs.getString("CONCSGDOCNUM"));
		}
		if(rs.getString("POACOD")!=null){
			dsnVO.setPaCode(rs.getString("POACOD"));
		}else{
			dsnVO.setPaCode(rs.getString("CONPOACOD"));
		}
		//Added as part of CRQ_ICRD-7844
		if(rs.getDate("CONCSGDAT")!=null)
		{
		dsnVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("CONCSGDAT")));
		}
		dsnVO.setCsgSeqNum(rs.getInt("CONCSGSEQNUM"));
		
		if(rs.getDate("CONACPDAT")!=null)
		{
		dsnVO.setAcceptedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("CONACPDAT")));
		}
		
		if(rs.getDate("CONRCVDAT")!=null)
		{
		dsnVO.setReceivedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("CONRCVDAT")));
		}
		//Added by A-4810 for bug-fix-icrd-9151.
		if(rs.getInt("TRFBAG")>=0)
		{
			dsnVO.setTransferredPieces(rs.getInt("TRFBAG"));
		}
		if(rs.getDouble("TRFWGT")>=0)
		{
			//dsnVO.setTransferredWeight(rs.getDouble("TRFWGT"));
			dsnVO.setTransferredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("TRFWGT")));//added by A-7371
		}
		dsnVO.setRemarks(rs.getString("RMKS"));
//		Added for SAA 403 ENDS
		 /*
		  * Added By RENO K ABRAHAM for Mail Allocation
		  * UBR number,Currency Code,Mail Rate is taken from MTKDSNULDSEG & MTKDSNCONSEG 
		  * CAPBKGMST is joined with these tables for lastUpdateTime of booking.
		  * CAPBKGFLTDTL is joined with CAPBKGMST for lastUpdateTime of Flight booking.
		  */
		 dsnVO.setUbrNumber(rs.getString("UBRNUM"));
		 dsnVO.setCurrencyCode(rs.getString("CURCOD"));
		 dsnVO.setMailrate(rs.getDouble("MALRAT"));
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
				 dsnVO.setBookingLastUpdateTime(bookingLastUpdateTime);
				 dsnVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
			 }
		 }
		 if(rs.getString("RTGAVL")!=null && "Y".equals(rs.getString("RTGAVL")))
			 {
			 dsnVO.setRoutingAvl("Y");
			 }
		 //END AirNZ CR : Mail Allocation
		 //Added as part of ICRD-211205 starts
		 dsnVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		 dsnVO.setDocumentOwnerIdentifier(rs.getInt("DOCOWRIDR"));
		 dsnVO.setDocumentOwnerCode(rs.getString("SHPPFX"));
		 dsnVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		 dsnVO.setSequenceNumber(rs.getInt("SEQNUM"));
		//Added as part of ICRD-211205 ends
	}

	/**
	 * A-1739
	 *
	 * @param containerDetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateContainerDetails(
			ContainerDetailsVO containerDetailsVO, ResultSet rs)
			throws SQLException {
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setPol(rs.getString("POL"));
		containerDetailsVO.setFlightStatus(rs.getString("CLSFLG"));
		containerDetailsVO.setContainerNumber(rs.getString(ULD_NUMBER));
		containerDetailsVO.setTotalBags(rs.getInt("BAGCNT"));
		//containerDetailsVO.setTotalWeight(rs.getDouble("BAGWGT"));
		containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("BAGWGT")));//added by A-7371
		containerDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
		//containerDetailsVO.setReceivedWeight(rs.getDouble("RCVWGT"));
		containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCVWGT")));//added by A-7371
		/*
		 * ADDED BY RENO K ABRAHAM FOR SB ULD - ARRIVED/DELIVERED
		 */
		containerDetailsVO.setArrivedStatus(rs.getString("CONARRSTA"));
		containerDetailsVO.setDeliveredStatus(rs.getString("DLVFLG"));
		//END
		if (containerDetailsVO.getContainerNumber().startsWith(
				MailConstantsVO.CONST_BULK)) {
			containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			 //ADDED By karthick V to include  the Destination Code  so 
			 //so that it has to be shown in the User Interface..
			containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		} else {
			containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
			// for ULDResdit
			containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
			containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		}
        containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
        containerDetailsVO.setTransferFlag(rs.getString("CONTRAFLG")); 
        containerDetailsVO.setReleasedFlag(rs.getString("RELFLG"));    
        containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
        /*
         * Added By Karthick V as  the part of the Optimistic Concurrency 
         * 
         * 
         */
        if(rs.getTimestamp("CONLSTUPDTIM")!=null){
             containerDetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CONLSTUPDTIM"))) ;
        } 
        if(rs.getTimestamp("SEGLSTUPDTIM")!=null){
            containerDetailsVO.setUldLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("SEGLSTUPDTIM"))) ;
        }
        /*
         * ADDED BY RENO K ABRAHAM FOR INTACT
         */
        containerDetailsVO.setIntact(rs.getString("INTFLG"));
        /*
         * FOR M39 (CARDIT_1.2/RESDIT_1.1)
         */
        containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
        containerDetailsVO.setPaCode(rs.getString("SBCODE"));
        if(rs.getString("REMARK")!=null){
        containerDetailsVO.setRemarks(rs.getString("REMARK"));
        }
        containerDetailsVO.setTransitFlag(rs.getString("TRNFLG"));
	}

	/**
	 * This method is used to find the DespatchDetails For the ULD.
	 * @param despatchDetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateDespatchDetailsForULD(
			DespatchDetailsVO  despatchDetailsVO, ResultSet rs)
			throws SQLException {
	     despatchDetailsVO.setAcceptedBags(rs.getInt("ULDACPBAG"));
		 despatchDetailsVO.setPrevAcceptedBags(rs.getInt("ULDACPBAG"));
			if (rs.getTimestamp("ULDACPDAT") != null) {
				despatchDetailsVO.setAcceptedDate(new LocalDate( 
						rs.getString("POL"), Location.ARP, rs
								.getTimestamp("ULDACPDAT")));
			}
		// despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		 //despatchDetailsVO.setAcceptedWeight(rs.getDouble("ULDACPWGT"));
		 despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDACPWGT")));//added by A-7371
		 //despatchDetailsVO.setPrevAcceptedWeight(rs.getDouble("ULDACPWGT"));
		 despatchDetailsVO.setPrevAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDACPWGT")));//added by A-7371
		 despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		 despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getTimestamp("ULDCSGDAT") != null) {
			despatchDetailsVO.setConsignmentDate(new LocalDate(
					rs.getString("POL"), Location.ARP, rs
							.getTimestamp("ULDCSGDAT")));
		}
		despatchDetailsVO.setConsignmentNumber(rs.getString("ULDCSGDOCNUM"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs
				.getString("DSTEXGOFC"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setPltEnabledFlag(rs.getString("PLTENBFLG"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setStatedBags(rs.getInt("ULDSTDBAG"));
		//despatchDetailsVO.setStatedWeight(rs.getDouble("ULDSTDWGT"));
		despatchDetailsVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDSTDWGT")));//added by A-7371
		despatchDetailsVO.setPrevStatedBags(rs.getInt("ULDSTDBAG"));
		//despatchDetailsVO.setPrevStatedWeight(rs.getDouble("ULDSTDWGT"));
		despatchDetailsVO.setPrevStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDSTDWGT")));//added by A-7371
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setPaCode(rs.getString("ULDPOACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("ULDCSGSEQNUM"));
		//Added to include the DSN PK
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));

		// For Arrival
		despatchDetailsVO.setReceivedBags(rs.getInt("ULDRCVBAG"));
		//despatchDetailsVO.setReceivedWeight(rs.getDouble("ULDRCVWGT"));
		despatchDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDRCVWGT")));//added by A-7371
		despatchDetailsVO.setDeliveredBags(rs.getInt("ULDDLVBAG"));
		despatchDetailsVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDDLVWGT")));//added by A-7371
		despatchDetailsVO.setPrevReceivedBags(rs.getInt("ULDRCVBAG"));
		//despatchDetailsVO.setPrevReceivedWeight(rs.getDouble("ULDRCVWGT"));
		despatchDetailsVO.setPrevReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDRCVWGT")));//added by A-7371
		despatchDetailsVO.setPrevDeliveredBags(rs.getInt("ULDDLVBAG"));
		//despatchDetailsVO.setPrevDeliveredWeight(rs.getDouble("ULDDLVBAG"));
		despatchDetailsVO.setPrevDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDDLVBAG")));//added by A-7371
		despatchDetailsVO.setContainerNumber(rs.getString(ULD_NUMBER));
		despatchDetailsVO.setUldNumber(despatchDetailsVO.getContainerNumber());
		despatchDetailsVO.setTransferFlag(rs.getString("CSGULDTRAFLG"));
		despatchDetailsVO.setAirportCode(rs.getString("POU"));
		despatchDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		//despatchDetailsVO.setStatedVolume(rs.getDouble("STDVOL"));
		despatchDetailsVO.setStatedVolume(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("STDVOL")));//added by A-7371

		despatchDetailsVO.setContainerForInventory(rs.getString("INVCONNUM"));
		if(rs.getString("INVULDNUM")!=null){
			if((rs.getString("INVULDNUM").equals(rs.getString("INVCONNUM"))) && 
					!(rs.getString("INVULDNUM").startsWith(MailConstantsVO.CONST_BULK)) ){
				despatchDetailsVO.setContainerTypeAtAirport(MailConstantsVO.ULD_TYPE);
			}else {
				despatchDetailsVO.setContainerTypeAtAirport(MailConstantsVO.BULK_TYPE);
			}
		}
	}
	
	
	/**
	 * This method is used to find the DespatchDetails For the ULD.
	 * @param despatchDetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateDespatchDetailsForBulk(
			DespatchDetailsVO  despatchDetailsVO, ResultSet rs)
			throws SQLException {
	     despatchDetailsVO.setAcceptedBags(rs.getInt("CONACPBAG"));
		 despatchDetailsVO.setPrevAcceptedBags(rs.getInt("CONACPBAG"));
			if (rs.getTimestamp("CONACPDAT") != null) {
				despatchDetailsVO.setAcceptedDate(new LocalDate( 
						rs.getString("POL"), Location.ARP, rs
								.getTimestamp("CONACPDAT")));
			}
		// despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		 //despatchDetailsVO.setAcceptedWeight(rs.getDouble("CONACPWGT"));
	     despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CONACPWGT")));//added by A-7371
		 //despatchDetailsVO.setPrevAcceptedWeight(rs.getDouble("CONACPWGT"));
	     despatchDetailsVO.setPrevAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CONACPWGT")));//added by A-7371
		 despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		 despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getTimestamp("CONCSGDAT") != null) {
			despatchDetailsVO.setConsignmentDate(new LocalDate(
					rs.getString("POL"), Location.ARP, rs
							.getTimestamp("CONCSGDAT")));
		}
		despatchDetailsVO.setConsignmentNumber(rs.getString("CONCSGDOCNUM"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs
				.getString("DSTEXGOFC"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setPltEnabledFlag(rs.getString("PLTENBFLG"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setStatedBags(rs.getInt("CONSTDBAG"));
		//despatchDetailsVO.setStatedWeight(rs.getDouble("CONSTDWGT"));
		despatchDetailsVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CONSTDWGT")));//added by A-7371
		despatchDetailsVO.setPrevStatedBags(rs.getInt("CONSTDBAG"));
		//despatchDetailsVO.setPrevStatedWeight(rs.getDouble("CONSTDWGT"));
		despatchDetailsVO.setPrevStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CONSTDWGT")));//added by A-7371
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setPaCode(rs.getString("CONPOACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CONCSGSEQNUM"));
		//Added to include the DSN PK
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));

		// For Arrival
		despatchDetailsVO.setReceivedBags(rs.getInt("CONRCVBAG"));
		//despatchDetailsVO.setReceivedWeight(rs.getDouble("CONRCVWGT"));
		despatchDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CONRCVWGT")));//added by A-7371
		despatchDetailsVO.setDeliveredBags(rs.getInt("CONDLVBAG"));
		//despatchDetailsVO.setDeliveredWeight(rs.getDouble("CONDLVWGT"));
		despatchDetailsVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CONDLVWGT")));//added by A-7371
		despatchDetailsVO.setPrevReceivedBags(rs.getInt("CONRCVBAG"));
		//despatchDetailsVO.setPrevReceivedWeight(rs.getDouble("CONRCVWGT"));
		despatchDetailsVO.setPrevReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CONRCVWGT")));//added by A-7371
		despatchDetailsVO.setPrevDeliveredBags(rs.getInt("CONDLVBAG"));
		//despatchDetailsVO.setPrevDeliveredWeight(rs.getDouble("CONDLVBAG"));
		despatchDetailsVO.setPrevDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CONDLVBAG")));//added by A-7371
		despatchDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		despatchDetailsVO.setTransferFlag(rs.getString("CSGCONTRAFLG")); 
		despatchDetailsVO.setAirportCode(rs.getString("POU"));
		//despatchDetailsVO.setStatedVolume(rs.getDouble("BULKVOL"));
		despatchDetailsVO.setStatedVolume(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("BULKVOL")));//added by A-7371
		despatchDetailsVO.setRemarks(rs.getString("RMKS"));
		
	}
	
	
}
