/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.AWBAttachedMailbagDetailsMapper.java
 *
 *	Created by	:	a-7779
 *	Created on	:	31-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
//merge solved  by A-5437
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;


/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.AWBAttachedMailbagDetailsMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	31-Aug-2017	:	Draft
 */
public class AWBAttachedMailbagDetailsMapper implements MultiMapper<ScannedMailDetailsVO> {
	
	public List<ScannedMailDetailsVO> map(ResultSet rs) throws SQLException {
	//public ScannedMailDetailsVO map(ResultSet rs) throws SQLException {
		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs = null;
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		while(rs.next()){
		MailbagVO mailBagVO = new MailbagVO();
		mailBagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailBagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailBagVO.setMailClass(rs.getString("MALCLS"));
		mailBagVO.setYear(rs.getInt("YER"));
		mailBagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailBagVO.setMailbagId(rs.getString("MALIDR"));
		mailBagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailBagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailBagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailBagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		//mailBagVO.setWeight(rs.getDouble("WGT"));
		mailBagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
		mailBagVO.setMailStatus(rs.getString("MALSTA"));
		mailBagVO.setLatestStatus(rs.getString("MALSTA"));
		mailBagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailBagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailBagVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		mailBagVO.setFlightStatus(rs.getString("FLTSTA"));
		mailBagVO.setStationCode(rs.getString("STNCOD"));
		if(!"Y".equals(rs.getString("SPLIND"))&&("ACP".equals(rs.getString("MALSTA"))|| "ASG".equals(rs.getString("MALSTA"))||("ARR".equals(rs.getString("MALSTA"))))){
		mailBagVO.setFlightNumber(rs.getString("MALMSTFLTNUM"));
		//mailBagVO.setFlightDate(flightDate)
		mailBagVO.setFlightSequenceNumber(rs.getInt("MALMSTFLTSEQNUM"));
		}
		mailBagVO.setFlightStatus(rs.getString("FLTSTA"));
		mailBagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailBagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		//mailBagVO.setLegSerialNumber(legSerialNumber)
		mailBagVO.setContainerNumber(rs.getString("CONNUM"));
		mailBagVO.setContainerType(rs.getString("CONTYP"));
		//As a Demo fix , setting the container NUmber as BULK and container type as B after discussion with Santhi-Need a detailed discussion and analysis on the same after demo
		/*LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		String containerNumberStr = new StringBuilder("B").append("-").append(currentDate.toDisplayFormat()).toString(); 
		String containerNumber =  new StringBuilder("B").append("-").append(containerNumberStr.substring(14, 22)).toString();
		mailBagVO.setContainerNumber(containerNumber);
		mailBagVO.setContainerType("B");*/
		//mailBagVO.setDespatchId(despatchId)
		mailBagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailBagVO.setDoe(rs.getString("DSTEXGOFC"));
		//Coding for ICRD-253863 by A-5219 start
		mailBagVO.setShipmentPrefix(rs.getString("SHPPFX"));
		mailBagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
		mailBagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		mailBagVO.setSequenceNumber(rs.getInt("SEQNUM"));
		//Coding for ICRD-253863 by A-5219 end
		if("-1".equals(rs.getString("MALMSTFLTNUM"))&&"-1".equals(rs.getString("MALMSTFLTSEQNUM"))){
			mailBagVO.setOffloadedRemarks("OFLMAILBAG");
		}
		
		
		if(rs.getString("MALIDR")!=null && rs.getString("MALIDR").trim().length()>0){
			mailBagVO.setMailbagId(rs.getString("MALIDR"));
		}else{
			String mailbagId = new StringBuilder()
				            .append(rs.getString("ORGEXGOFC"))
				            .append(rs.getString("DSTEXGOFC"))
							.append(rs.getString("MALCTGCOD"))
							.append(rs.getString("MALSUBCLS"))
							.append(rs.getInt("YER"))
							.append(rs.getString("DSN"))
							.append(rs.getString("RSN"))
							.append(rs.getString("HSN"))
							.append(rs.getString("REGIND"))
							.append(rs.getString("WGT")).toString();
			mailBagVO.setMailbagId(mailbagId);
		}
		//mailBagVO.setMailSource(mailSource)
		//mailBagVO.setPaBuiltFlag(paBuiltFlag)
		mailBagVO.setPol(rs.getString("SCNPRT"));
		mailBagVO.setPou(rs.getString("POU"));
		mailBagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		mailBagVO.setScannedPort(rs.getString("SCNPRT"));
		//mailBagVO.setScannedUser(scannedUser)*/
		//mailBagVO.setPol(pol)
		mailBagVOs.add(mailBagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);	
		if("Y".equals(rs.getString("SPLIND"))){
			scannedMailDetailsVO.setSplitBooking(true);
		}else{
			scannedMailDetailsVO.setSplitBooking(false);
		}
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(rs.getString("CMPCOD"));
		containerVO.setPou(rs.getString("POU"));
		containerVO.setCarrierCode(rs.getString("FLTCARCOD"));
		containerVO.setFlightNumber(rs.getString("FLTNUM"));
		containerVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		containerVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerVO.setFlightStatus(rs.getString("FLTSTA"));
		containerVO.setContainerNumber(rs.getString("CONNUM"));
		containerVO.setType(rs.getString("CONTYP"));
		containerVO.setPol(rs.getString("SCNPRT"));
		containerVO.setAssignedPort(rs.getString("SCNPRT"));
		containerVOs.add(containerVO);
		scannedMailDetailsVO.setScannedContainerDetails(containerVOs);
		scannedMailDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		//scannedMailDetailsVO.setAirportCode(airportCode)
		//As a Demo fix , setting the container NUmber as BULK and container type as B after discussion with Santhi-Need a detailed discussion and analysis on the same after demo
		/*scannedMailDetailsVO.setContainerNumber(containerNumber);
		scannedMailDetailsVO.setContainerType("B");*/
		scannedMailDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		scannedMailDetailsVO.setContainerType(rs.getString("CONTYP"));
		scannedMailDetailsVO.setDestination(rs.getString("POU"));
		//scannedMailDetailsVO.setContainerProcessPoint(containerProcessPoint)
		//scannedMailDetailsVO.setFlightDate(new LocalDate(Location.NONE,Location.ARP, rs.getDate("FLTDAT")));
		scannedMailDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		scannedMailDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		//scannedMailDetailsVO.setCarrierCode(rs.getString(columnIndex))
		scannedMailDetailsVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		scannedMailDetailsVO.setFlightStatus(rs.getString("FLTSTA"));
		scannedMailDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		scannedMailDetailsVO.setPol(rs.getString("SCNPRT"));
		//scannedMailDetailsVO.setPols(pols)*/
		scannedMailDetailsVO.setPou(rs.getString("POU"));
		/*scannedMailDetailsVO.setProcessPoint(processPoint)
		scannedMailDetailsVO.setScannedUser(scannedUser)*/
		scannedMailDetailsVO.setStatus(rs.getString("MALSTA"));
		/*scannedMailDetailsVO.setMailSource(mailSource)
		scannedMailDetailsVO.setOperationFlag(operationFlag)
		scannedMailDetailsVO.setOperationTime(operationTime)
		scannedMailDetailsVO.setOwnAirlineCode(ownAirlineCode)*/
		
		if(!"Y".equals(rs.getString("SPLIND"))&&("ACP".equals(rs.getString("MALSTA")) || "ASG".equals(rs.getString("MALSTA")) || "TRA".equals(rs.getString("MALSTA")) )){//ICRD-316411
			scannedMailDetailsVO.setFlightNumber(rs.getString("MALMSTFLTNUM"));
			scannedMailDetailsVO.setFlightSequenceNumber(rs.getInt("MALMSTFLTSEQNUM"));
		}

		}
		if(scannedMailDetailsVO.getMailDetails()!=null && scannedMailDetailsVO.getMailDetails().size()>0){
			scannedMailDetailsVOs = new ArrayList<ScannedMailDetailsVO>();
		scannedMailDetailsVOs.add(scannedMailDetailsVO);
		}
		if(scannedMailDetailsVOs == null){
			scannedMailDetailsVOs = new ArrayList<ScannedMailDetailsVO>();
		}
		return (ArrayList<ScannedMailDetailsVO>)scannedMailDetailsVOs;
		
	}
}

			
	

