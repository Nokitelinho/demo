/*
 * AcceptedDsnsInFlightMultiMapper.java Created on May 29, 2006
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
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
 * @author a-1936
 * This class is used to map the ResultSet into Vos returned to the Request...
 */
public class AcceptedDsnsInFlightMultiMapper implements MultiMapper<ContainerDetailsVO> {

    	private Log log= LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	 	private static final String DSPWGTUNT = "DSPWGTUNT";
	 	private static final String CSGACPWGT= "CSGACPWGT";
	 	private static final String MSTDOCNUM = "MSTDOCNUM";
	 	private static final String SHPPFX = "SHPPFX";
	 	
    	
    	/**
    	 * @author a-1936
    	 * @param rs 
    	 * @throws SQLException
    	 * @return
    	 */
    	public List<ContainerDetailsVO>  map(ResultSet rs) throws SQLException {
    		log.entering("AcceptedDsnsInFlightMultiMapper", "map(ResultSet rs");
    		List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		    ContainerDetailsVO containerDetailsVO = new  ContainerDetailsVO(); 
		    containerDetailsVOsList.add(containerDetailsVO);
		    String currDespatchKey = null;
		    String prevDespatchKey = null;
		    MailbagVO mailbagVO = null;
		    String currMailbagKey = null;
		    String prevMailbagKey = null;
		    String mailType="M";
		    DespatchDetailsVO despatchDetailsVO = null;
		    String currDmgMailbagKey = null;
		    Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		    Collection<DespatchDetailsVO> despatches = new ArrayList<DespatchDetailsVO>();
		    Map<String, MailbagVO> mailbagMap = new LinkedHashMap<String, MailbagVO>();
			Map<String, DamagedMailbagVO> damagedMailbagMap = new LinkedHashMap<String, DamagedMailbagVO>();
			Measure totalWeight = null;
			Measure receivedWeight =null;
			Measure deliveredWeight = null;
		 while (rs.next()){
				
			 if( containerDetailsVO.getContainerNumber()==null){
				 containerDetailsVO.setContainerNumber(rs.getString("CONNUM"));
				 containerDetailsVO.setContainerType(rs.getString("CONTYP"));
				//Added as part of ICRD-239331 starts
				 containerDetailsVO.setContentId(rs.getString("CNTIDR"));
				 containerDetailsVO.setPou(rs.getString("POU"));
				 containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
				//Added as part of ICRD-239331 ends
			} 
			 containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			 containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
				 containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
				 containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
				 containerDetailsVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				 containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
				 containerDetailsVO.setPol(rs.getString("ASGPRT"));
				 containerDetailsVO.setContainerType(rs.getString("CONTYP"));
				 containerDetailsVO.setUldFulIndFlag(rs.getString("ULDFULIND"));
				 containerDetailsVO.setUldReferenceNo(rs.getLong("ULDREFNUM"));
			String csgDocNum = rs.getString("CSGDOCNUM");
			mailType= rs.getString("MALTYP");
			 	log.log(Log.FINE, "csgDocNum", csgDocNum);
				if("D".equals(mailType)){
                    currDespatchKey = new StringBuilder().append(rs.getString("DSN"))
				    .append(rs.getString("ORGEXGOFC"))
				    .append(rs.getString("DSTEXGOFC")).append(
				     rs.getString("MALSUBCLS"))
				    .append(rs.getString("MALCTGCOD"))
				    .append(rs.getInt("YER")).
					 append(csgDocNum)
					 .append(rs.getString("POACOD")).append(
					 rs.getInt("CSGSEQNUM")).toString();
                
                    log.log(Log.FINE, "THE CONSIGNEMENT KEY", currDespatchKey);
				if (!currDespatchKey.equals(prevDespatchKey)) {
						despatchDetailsVO = new DespatchDetailsVO();
						populateDespatchDetails(despatchDetailsVO, rs);
						despatches.add(despatchDetailsVO);
						prevDespatchKey = currDespatchKey;
					}
				}
			
			   String mailbagId = rs.getString("MALIDR");
			   if(mailbagId != null) {
				 currMailbagKey = mailbagId;
				 log.log(Log.FINE, "currMailbagKey ", currMailbagKey);
				 //
				 if(currMailbagKey!= null && !mailbagMap.containsKey(currMailbagKey)){
						//if (currMailbagKey != null) {
							log.log(Log.FINE, "curramilbag key ", currMailbagKey);
							//if (currMailbagKey != null
							//		&& !currMailbagKey.equals(prevMailbagKey)) {
					mailbagVO = new MailbagVO();
					populateMailbagDetails(mailbagVO, rs);
							//	prevMailbagKey = currMailbagKey;
								mailbagMap.put(currMailbagKey, mailbagVO);
					mailbagVOs.add(mailbagVO);
					//Added as part of ICRD-340690 starts
					if(rs.getInt("RCVBAG")!=0) {
						containerDetailsVO.setReceivedBags(containerDetailsVO.getReceivedBags() + 1);
					}
					 try {
						 if (containerDetailsVO.getReceivedWeight() == null) {
							 containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, 0.0));
						 }
						 if(rs.getInt("RCVBAG")!=0) {
							 receivedWeight = Measure.addMeasureValues(receivedWeight, (new Measure(UnitConstants.MAIL_WGT, rs.getDouble("WGT"))));
							 containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, receivedWeight.getSystemValue(), 0.0, rs.getString(DSPWGTUNT)));
							}
						 }catch (UnitException e) {
							 log.log(Log.INFO, e);
						 }
					 if(rs.getInt("DLVBAG")!=0) {
							containerDetailsVO.setDeliveredBags(containerDetailsVO.getDeliveredBags() + 1);
						}
						 try {
							 if (containerDetailsVO.getDeliveredWeight() == null) {
								 containerDetailsVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, 0.0));
							 }
							 if(rs.getInt("DLVBAG")!=0) {
								 deliveredWeight = Measure.addMeasureValues(deliveredWeight, (new Measure(UnitConstants.MAIL_WGT, rs.getDouble("WGT"))));
								 containerDetailsVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, deliveredWeight.getSystemValue(), 0.0, rs.getString(DSPWGTUNT)));
								}
							 }catch (UnitException e) {
								 log.log(Log.INFO, e);
							 }
					 containerDetailsVO.setTotalBags(containerDetailsVO.getTotalBags() + 1);
					 try {
						 if (containerDetailsVO.getTotalWeight() == null) {
							 containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, 0.0));
						 }
						 /**
						  * Add up values in system units and then convert the system value using the display unit
						  * This is used to handle the case when the container has mail bags in more than one display units
						  */
						 totalWeight = Measure.addMeasureValues(totalWeight, (new Measure(UnitConstants.MAIL_WGT, rs.getDouble("WGT"))));
						 containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, totalWeight.getSystemValue(), 0.0, rs.getString(DSPWGTUNT)));
					 } catch (UnitException e) {
						 log.log(Log.INFO, e);
					 }
					//Added as part of ICRD-340690 ends
							//}
						}
				 else
					{
				if(rs.getString("DMGCOD") != null) {
						currDmgMailbagKey = new StringBuilder().append(currMailbagKey).append(rs.getString("DMGCOD")).toString();
						mailbagVO = mailbagMap.get(currMailbagKey);
						if(!damagedMailbagMap.containsKey(currDmgMailbagKey)){
								if(mailbagVO.getDamagedMailbags()!= null && mailbagVO.getDamagedMailbags().size()>0) {
									DamagedMailbagVO damagedMailbagVO = populateDamageDetails(rs);
									mailbagVO.getDamagedMailbags().add(damagedMailbagVO);
									}
								}
							}
						}
				//
			}
			
		}
		
		 containerDetailsVO.setMailDetails(mailbagVOs);
		 containerDetailsVO.setDesptachDetailsVOs(despatches);
         log.log(Log.FINE, "THE container Details Vo Finally ",
				containerDetailsVO);
		return containerDetailsVOsList;
	}

	

	/**
	 * A-1936
	 * @param despatchDetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateDespatchDetails(DespatchDetailsVO despatchDetailsVO,
			ResultSet rs) throws SQLException {
		 String currentPort = rs.getString("ASGPRT");
		 despatchDetailsVO.setAcceptedBags(rs.getInt("CSGACPBAG"));
		 despatchDetailsVO.setPrevAcceptedBags(rs.getInt("CSGACPBAG"));
			if (rs.getDate("ACPDAT") != null) {
		 despatchDetailsVO.setAcceptedDate(new LocalDate(currentPort,
				Location.ARP, rs.getDate("ACPDAT")));
			}
		 despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		 despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble(CSGACPWGT)));//added by A-7371
		 despatchDetailsVO.setPrevAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble(CSGACPWGT)));//added by A-7371
		 despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		 despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
			if (rs.getDate("CSGDAT") != null) {
		 despatchDetailsVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, 
				 Location.NONE, rs.getDate("CSGDAT")));
			}
		 despatchDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		 despatchDetailsVO.setDestinationOfficeOfExchange(rs
				.getString("DSTEXGOFC"));
		 despatchDetailsVO.setDsn(rs.getString("DSN"));
		 despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		 despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		//Added to include the DSN PK 
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setStatedBags(rs.getInt("CSGSTDBAG"));
		//despatchDetailsVO.setStatedWeight(rs.getDouble("CSGSTDWGT"));
		despatchDetailsVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CSGSTDWGT")));//added by A-7371
		despatchDetailsVO.setPrevStatedBags(rs.getInt("CSGSTDBAG"));
		//despatchDetailsVO.setPrevStatedWeight(rs.getDouble("CSGSTDWGT"));
		despatchDetailsVO.setPrevStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CSGSTDWGT")));//added by A-7371
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		//despatchDetailsVO.setStatedVolume(rs.getDouble("STDVOL"));
		despatchDetailsVO.setStatedVolume(new Measure(UnitConstants.VOLUME,rs.getDouble("STDVOL")));//added by A-7371
		//despatchDetailsVO.setAcceptedVolume(rs.getDouble("ACPVOL"));
		despatchDetailsVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME,rs.getDouble("ACPVOL")));//added by A-7371

	}
	/**
	 * A-1936
	 * @param mailbagVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateMailbagDetails(MailbagVO mailbagVO, ResultSet rs)
			throws SQLException {
			Collection<DamagedMailbagVO> damagedVOs = new ArrayList <DamagedMailbagVO>();
			String currDmgMailbagKey = null;
		    mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
			String mailbagId = rs.getString("MALIDR");
			mailbagVO.setMailbagId(mailbagId);
			mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
			mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
			mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
			mailbagVO.setMailSubclass( rs.getString("MALSUBCLS"));
			mailbagVO.setYear(rs.getInt("YER"));
			mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
			mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
			mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
			mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
			mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble(CSGACPWGT),rs.getDouble("DSPWGT"),rs.getString(DSPWGTUNT)));//added by A-7371
			/*
			 * Added By as the  part of all the ANZ  Mail Tracking Bug Fix ..
			 * 
			 * 
			 */
			mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
			mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM")); 
			mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
			mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			// Modified as part of bug ICRD-96381 by A-5526 starts
			String scannedPort = rs.getString("ASGPRT");
			if(rs.getTimestamp("SCNDAT")!=null){     
			Timestamp scandateAndTime = rs.getTimestamp("SCNDAT");
			if(scandateAndTime!=null && scannedPort!=null){
			mailbagVO.setScannedDate(new LocalDate(scannedPort, Location.ARP,scandateAndTime));
			}}
			// Modified as part of bug ICRD-96381 by A-5526 ends
			mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
			mailbagVO.setScannedPort(scannedPort);
			mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
			mailbagVO.setTransferFromCarrier(rs.getString("MALFRMCARCOD"));
			//mailbagVO.setVolume(rs.getDouble("VOL"));
			mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,0.0,rs.getDouble("VOL"),rs.getString("VOLUNT")));//modified by A-8353
			Timestamp lastUpdateTime = rs.getTimestamp("MALLSTUPDTIM");
			if(lastUpdateTime != null) {
				mailbagVO.setLastUpdateTime(
					new LocalDate(LocalDate.NO_STATION, Location.NONE, 
						lastUpdateTime));
			}
			/**
			 * Added for MRA irregularity report
			 */
			
			mailbagVO.setConsignmentNumber(rs.getString("MALCSGDOCNUM"));
			mailbagVO.setConsignmentSequenceNumber(rs.getInt("MALCSGDOCSEQ"));
			mailbagVO.setPaCode(rs.getString("MALPOACOD"));
			mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));
			mailbagVO.setBellyCartId(rs.getString("BLYCRTIDR"));
			// Added as part of CRQ ICRD-118163 by A-5526 starts
			mailbagVO.setScannedPort(rs.getString("SCNPRT"));
			mailbagVO.setMailStatus(rs.getString("MALSTA")); 
			mailbagVO.setOperationalStatus(rs.getString("OPRSTA"));        
			// Added as part of CRQ ICRD-118163 by A-5526 ends
			mailbagVO.setSealNumber(rs.getString("SELNUM"));
			
			//Added as a part of ICRD-197419 by A-7540
			mailbagVO.setMailRemarks(rs.getString("MALRMK"));
			//Added by A-7540
			mailbagVO.setContainerNumber(rs.getString("CONNUM"));
			//Added as part of bug ICRD-274236 by A-5526 starts
			mailbagVO.setSealNumber(rs.getString("ARRSELNUM"));
			//Added as part of bug ICRD-274236 by A-5526 ends
			if(rs.getString("DMGCOD") != null) {
			currDmgMailbagKey = new StringBuilder().append(mailbagId).append(rs.getString("DMGCOD")).toString();
			DamagedMailbagVO damagedMailbagVO = populateDamageDetails(rs);
			damagedVOs.add(damagedMailbagVO);
			mailbagVO.setDamagedMailbags(damagedVOs);
			}
			//Added as part of ICRD-239331 starts
			mailbagVO.setOrigin(rs.getString("ORGCOD"));
			mailbagVO.setDestination(rs.getString("DSTCOD"));
			mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
			//Added as part of ICRD-239331 ends
			
			mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
			mailbagVO.setMailDestination(rs.getString("DSTCOD"));
			mailbagVO.setAcceptancePostalContainerNumber(rs.getString("ACPPOACONNUM"));
			mailbagVO.setAcceptanceAirportCode(rs.getString("ACPARPCOD"));
			mailbagVO.setDocumentNumber(rs.getString(MSTDOCNUM));
			mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
			mailbagVO.setShipmentPrefix(rs.getString(SHPPFX));
			mailbagVO.setSecurityStatusCode(rs.getString("SECSTACOD"));
			if(null!=rs.getString(SHPPFX)&&null!=rs.getString(MSTDOCNUM)){
				mailbagVO.setAwbNumber(rs.getString(SHPPFX)+"-"+rs.getString(MSTDOCNUM));
				}
			mailbagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
			mailbagVO.setSequenceNumber(rs.getInt("SEQNUM"));
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
}
