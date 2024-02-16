/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.MailbagForArrivalMultiMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Sep 3, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.MailbagForArrivalMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Sep 3, 2016	:	Draft
 */
public class MailbagForArrivalMultiMapper implements MultiMapper<ContainerDetailsVO>{
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String CLASS ="MailbagForArrivalMultiMapper";
	private static final String SCNPRT = "SCNPRT";

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 *	Added by 			: A-4809 on Sep 3, 2016
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS, "ResultSet");
		List<ContainerDetailsVO> containerDetails = new ArrayList<ContainerDetailsVO>();
		String currContainerKey = null;
		String prevContainerKey = null;
		ContainerDetailsVO containerDetailsVO = null;
		DSNVO dsnVO = null;
		Collection<DSNVO> dsnVOs = null;
		Collection<MailbagVO> mailbagVOs = null;
		String currDSNKey = null;
		String prevDSNKey = null;
		String currMailbagKey = null;
		String prevMailbagKey = null;
		MailbagVO mailbagVO = null;
		String currDespatchKey = null;
		String prevDespatchKey = null;
		DespatchDetailsVO despatchDetailsVO=null;
		Collection<DespatchDetailsVO> despatches = null;
		Map<String, ArrayList<String>> csgDocForDSN = new HashMap<String, ArrayList<String>>();
		String dsnKey = null;
		while (rs.next()) {
			
			currContainerKey = new StringBuilder().append(
					rs.getString("CMPCOD")).append(rs.getInt("FLTCARIDR"))
					.append(rs.getString("FLTNUM")).append(
							rs.getLong("FLTSEQNUM")).append(
							rs.getInt("SEGSERNUM")).append(
							rs.getString("ULDNUM")).toString();

			log.log(Log.FINE, "currContainerKey ", currContainerKey);
			if (!currContainerKey.equals(prevContainerKey)) {
				containerDetailsVO = new ContainerDetailsVO();
				populateContainerDetails(containerDetailsVO, rs);
				dsnVOs = new ArrayList<DSNVO>();
				containerDetailsVO.setDsnVOs(dsnVOs);
				mailbagVOs = new ArrayList<MailbagVO>();
				despatches=new ArrayList<DespatchDetailsVO>();
				containerDetailsVO.setMailDetails(mailbagVOs);
				containerDetailsVO.setDesptachDetailsVOs(despatches);
				containerDetails.add(containerDetailsVO);
			    prevContainerKey = currContainerKey;
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
						csgDocForDSN.put(dsnKey, csgDetails);
					}
				}
				if(!currDSNKey.equals(prevDSNKey)) {

					dsnVO = new DSNVO();
					populateDSNDetails(dsnVO, rs);
					dsnVOs.add(dsnVO);
					prevDSNKey = currDSNKey;
				}else{
					if(rs.getString("CSGDOCNUM")!=null && 
							rs.getString("CSGDOCNUM").length() > 0){
						dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
					}
					if(rs.getString("POACOD")!=null && 
							rs.getString("POACOD").length() > 0){
						dsnVO.setPaCode(rs.getString("POACOD"));
					}
				}
			}

			if (dsnVO != null) {
				if (MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())) {

					currMailbagKey = rs.getString("MALIDR");
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
						populateMailbagDetails(mailbagVO, rs);
						if(MailConstantsVO.FLAG_YES.equals(
								mailbagVO.getTransferFlag())) {
							dsnVO.setTransferFlag(MailConstantsVO.FLAG_YES);
						}
						prevMailbagKey = currMailbagKey;
						mailbagVOs.add(mailbagVO);
					}
				}
			}
			if(rs.getString("ULDCSGDOCNUM") != null){
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
			    }else if (rs.getString("CONCSGDOCNUM") != null){
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
		}
		
		return containerDetails;
	}

	/**
	 * 	Method		:	MailbagForArrivalMultiMapper.populateDespatchDetailsForBulk
	 *	Added by 	:	A-4809 on Oct 1, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param despatchDetailsVO
	 *	Parameters	:	@param rs 
	 *	Return type	: 	void
	 * @throws SQLException 
	 */
	private void populateDespatchDetailsForBulk(
			DespatchDetailsVO despatchDetailsVO, ResultSet rs) throws SQLException {
	     despatchDetailsVO.setAcceptedBags(rs.getInt("CONACPBAG"));
		 despatchDetailsVO.setPrevAcceptedBags(rs.getInt("CONACPBAG"));
			if (rs.getTimestamp("CONACPDAT") != null) {
				despatchDetailsVO.setAcceptedDate(new LocalDate( 
						rs.getString("POL"), Location.ARP, rs
								.getTimestamp("CONACPDAT")));
			}
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
		despatchDetailsVO.setDestinationOfficeOfExchange(rs.getString("DSTEXGOFC"));
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
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
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
		despatchDetailsVO.setStatedVolume(new Measure(UnitConstants.VOLUME,rs.getDouble("BULKVOL")));//added by A-7371
		despatchDetailsVO.setRemarks(rs.getString("RMKS"));
		
	}

	/**
	 * 	Method		:	MailbagForArrivalMultiMapper.populateDespatchDetailsForULD
	 *	Added by 	:	A-4809 on Oct 1, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param despatchDetailsVO
	 *	Parameters	:	@param rs 
	 *	Return type	: 	void
	 * @throws SQLException 
	 */
	private void populateDespatchDetailsForULD(
			DespatchDetailsVO despatchDetailsVO, ResultSet rs) throws SQLException {
	     despatchDetailsVO.setAcceptedBags(rs.getInt("ULDACPBAG"));
		 despatchDetailsVO.setPrevAcceptedBags(rs.getInt("ULDACPBAG"));
			if (rs.getTimestamp("ULDACPDAT") != null) {
				despatchDetailsVO.setAcceptedDate(new LocalDate( 
						rs.getString("POL"), Location.ARP, rs
								.getTimestamp("ULDACPDAT")));
			}
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
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setReceivedBags(rs.getInt("ULDRCVBAG"));
		//despatchDetailsVO.setReceivedWeight(rs.getDouble("ULDRCVWGT"));
		despatchDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDRCVWGT")));//added by A-7371
		despatchDetailsVO.setDeliveredBags(rs.getInt("ULDDLVBAG"));
		//despatchDetailsVO.setDeliveredWeight(rs.getDouble("ULDDLVWGT"));
		despatchDetailsVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDDLVWGT")));//added by A-7371
		despatchDetailsVO.setPrevReceivedBags(rs.getInt("ULDRCVBAG"));
		//despatchDetailsVO.setPrevReceivedWeight(rs.getDouble("ULDRCVWGT"));
		despatchDetailsVO.setPrevReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDRCVWGT")));//added by A-7371
		despatchDetailsVO.setPrevDeliveredBags(rs.getInt("ULDDLVBAG"));
		//despatchDetailsVO.setPrevDeliveredWeight(rs.getDouble("ULDDLVBAG"));
		despatchDetailsVO.setPrevDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ULDDLVBAG")));//added by A-7371
		despatchDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
		despatchDetailsVO.setUldNumber(despatchDetailsVO.getContainerNumber());
		despatchDetailsVO.setTransferFlag(rs.getString("CSGULDTRAFLG"));
		despatchDetailsVO.setAirportCode(rs.getString("POU"));
		despatchDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		//despatchDetailsVO.setStatedVolume(rs.getDouble("STDVOL"));
		despatchDetailsVO.setStatedVolume(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("STDVOL")));//added by A-7371
		 /**
		  * dsnarpuld table is removed from query hence inventory container number and type set from 
		  * mtkconmst
		  */
		despatchDetailsVO.setContainerForInventory(rs.getString("INVCONNUM"));
		despatchDetailsVO.setContainerTypeAtAirport(rs.getString("CONTYP"));
	}

	/**
	 * 	Method		:	MailbagForArrivalMultiMapper.populateMailbagDetails
	 *	Added by 	:	A-4809 on Oct 1, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@param rs 
	 *	Return type	: 	void
	 */
	private void populateMailbagDetails(MailbagVO mailbagVO, ResultSet rs) throws SQLException{
		//Collection<DamagedMailbagVO> damagedVOs = new ArrayList <DamagedMailbagVO>();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		String mailbagId = rs.getString("MALIDR");
		mailbagVO.setMailbagId(mailbagId);
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setMailSubclass( rs.getString("MALSUBCLS"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		 mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
		 mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		 mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
		 mailbagVO.setDeliveredFlag(rs.getString("DLVSTA"));
		 mailbagVO.setTransferFlag(rs.getString("MALTRAFLG"));
		// mailbagVO.setVolume(rs.getDouble("VOL"));
		 mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,rs.getDouble("VOL")));//added by A-7371
		 mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
		 mailbagVO.setMraStatus(rs.getString("MALMRASTA"));
		 /**
		  * Scan date picking from history table removed
		  * As ALL status is not stamped in history table
		 if(rs.getTimestamp("SCNDAT")!=null){
			mailbagVO.setScannedDate(new LocalDate(rs.getString("POU"),
			Location.ARP, rs.getTimestamp("SCNDAT")));
		 }*/
		 mailbagVO.setScannedPort(rs.getString(SCNPRT));
		 mailbagVO.setSealNumber(rs.getString("SELNUM")); 
		 mailbagVO.setArrivalSealNumber(rs.getString("ARRSELNUM"));     
		 if(rs.getTimestamp("MALLSTUPDTIM")!=null){
			mailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, rs.getTimestamp("MALLSTUPDTIM")));
		 }
		 mailbagVO.setAcceptanceFlag(rs.getString("MALACPSTA"));
		 mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));             
		 
		 mailbagVO.setMailStatus(rs.getString("MALSTA"));
		 mailbagVO.setFromFightNumber(rs.getString("MALFLTNUM"));
		 mailbagVO.setFromCarrierId(rs.getInt("MALFLTCARIDR"));
		 mailbagVO.setFromFlightSequenceNumber(rs.getLong("MALFLTSEQNUM"));
		 //Added as a part of ICRD-197419 by a-7540
		 mailbagVO.setMailRemarks(rs.getString("MALRMK"));
		if (Objects.nonNull(rs.getDate("SCNDAT")) && Objects.nonNull(rs.getString(SCNPRT))) {
			mailbagVO.setScannedDate(new LocalDate(rs.getString(SCNPRT), Location.ARP, true));
		}
		mailbagVO.setOrigin(rs.getString("ORGCOD"));
		mailbagVO.setDestination(rs.getString("MALDSTCOD"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setContainerNumber(rs.getString("MALCONNUM"));
		 
		 /**
		  * malarpuld table is removed from query hence inventory container number and type set from 
		  * mtkconmst
		  */
		 mailbagVO.setInventoryContainer(rs.getString("INVCONNUM"));
		 mailbagVO.setInventoryContainerType(rs.getString("CONTYP"));
			/**
			 * Damage tables removed from query. Need to check if this is used in code flow
			if(rs.getString("DMGCOD") != null) {
				DamagedMailbagVO damagedMailbagVO = populateDamageDetails(rs);
				damagedVOs.add(damagedMailbagVO);
				mailbagVO.setDamagedMailbags(damagedVOs);
				}
				*/
	}

	/**
	 * 	Method		:	MailbagForArrivalMultiMapper.populateDSNDetails
	 *	Added by 	:	A-4809 on Oct 1, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param dsnVO
	 *	Parameters	:	@param rs 
	 *	Return type	: 	void
	 * @throws SQLException 
	 */
	private void populateDSNDetails(DSNVO dsnVO, ResultSet rs) throws SQLException {
		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		dsnVO.setMailClass(rs.getString("MALCLS"));
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
		dsnVO.setPrevReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNRCVWGT")));//added by A-7371
		dsnVO.setPrevDeliveredBags(rs.getInt("DLVBAG"));
		dsnVO.setPrevDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DLVWGT")));//added by A-7371
		dsnVO.setPol(rs.getString("ORGEXGOFC").substring(2, 5)) ;
		dsnVO.setDestination(rs.getString("DSTEXGOFC").substring(2, 5)) ;
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
		if(rs.getDate("CONCSGDAT")!=null){
		dsnVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("CONCSGDAT")));
		}
		dsnVO.setCsgSeqNum(rs.getInt("CONCSGSEQNUM"));
		if(rs.getDate("CONACPDAT")!=null){
		dsnVO.setAcceptedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("CONACPDAT")));
		}
		if(rs.getDate("CONRCVDAT")!=null){
		dsnVO.setReceivedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,rs.getDate("CONRCVDAT")));
		}
		if(rs.getInt("TRFBAG")>=0){
			dsnVO.setTransferredPieces(rs.getInt("TRFBAG"));
		}
		if(rs.getDouble("TRFWGT")>=0){
			//dsnVO.setTransferredWeight(rs.getDouble("TRFWGT"));
			dsnVO.setTransferredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("TRFWGT")));//added by A-7371
		}
		dsnVO.setRemarks(rs.getString("RMKS"));
		 dsnVO.setCurrencyCode(rs.getString("CURCOD"));
		 dsnVO.setMailrate(rs.getDouble("MALRAT"));
		 /**
		  * 
		 Capacity tables removed from query.need to check if this used in code
		 dsnVO.setUbrNumber(rs.getString("UBRNUM")); 
		 Timestamp bookingUpdateTime = rs.getTimestamp("BKGLSTUPDTIM");
		 Timestamp bookingFlightDetailUpdateTime = rs.getTimestamp("BKGFLTUPDTIM");
		 LocalDate bookingLastUpdateTime=new LocalDate(LocalDate.NO_STATION, Location.NONE,true); 
		 if(bookingUpdateTime != null && bookingFlightDetailUpdateTime != null) {
			 if(bookingUpdateTime.after(bookingFlightDetailUpdateTime)) {
				 bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE,bookingUpdateTime);
			 }else{
				 bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE,bookingFlightDetailUpdateTime);
			 }
				 dsnVO.setBookingLastUpdateTime(bookingLastUpdateTime);
				 dsnVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
		 }
		 */
	}

	/**
	 * 	Method		:	MailbagForArrivalMultiMapper.populateContainerDetails
	 *	Added by 	:	A-4809 on Oct 1, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param containerDetailsVO
	 *	Parameters	:	@param rs 
	 *	Return type	: 	void
	 * @throws SQLException 
	 */
	private void populateContainerDetails(
			ContainerDetailsVO containerDetailsVO, ResultSet rs) throws SQLException {
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setPol(rs.getString("POL"));
		containerDetailsVO.setFlightStatus(rs.getString("CLSFLG"));
		containerDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
		containerDetailsVO.setTotalBags(rs.getInt("BAGCNT"));
		//containerDetailsVO.setTotalWeight(rs.getDouble("BAGWGT"));
		containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("BAGWGT")));//added by A-7371
		containerDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
		//containerDetailsVO.setReceivedWeight(rs.getDouble("RCVWGT"));
		containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCVWGT")));//added by A-7371
		containerDetailsVO.setArrivedStatus(rs.getString("CONARRSTA"));
		containerDetailsVO.setDeliveredStatus(rs.getString("DLVFLG"));
		if (containerDetailsVO.getContainerNumber().startsWith(
				MailConstantsVO.CONST_BULK)) {
			containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		} else {
			containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
			containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
			containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		}
        containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
        containerDetailsVO.setTransferFlag(rs.getString("CONTRAFLG")); 
        containerDetailsVO.setReleasedFlag(rs.getString("RELFLG"));    
        containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
        containerDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
        if(rs.getTimestamp("CONLSTUPDTIM")!=null){
             containerDetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CONLSTUPDTIM"))) ;
        } 
        if(rs.getTimestamp("SEGLSTUPDTIM")!=null){
            containerDetailsVO.setUldLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("SEGLSTUPDTIM"))) ;
        }
        containerDetailsVO.setIntact(rs.getString("INTFLG"));
        containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
        containerDetailsVO.setPaCode(rs.getString("SBCODE"));
        if(rs.getString("REMARK")!=null){
        containerDetailsVO.setRemarks(rs.getString("REMARK"));
        }
	}
	
	
}
