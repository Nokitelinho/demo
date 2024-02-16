/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.MailBagsForPAWBCreationMapper.java
 *
 *	Created by	:	A-9998
 *
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
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


import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.persistence.dao.mail.operations.MailBagsForPAWBCreationMapper.java
 * 
 * ---------------------------------------------------  A-9998 :
 * 
 */

public class MailBagsForPAWBCreationMapper implements MultiMapper<CarditPawbDetailsVO> {
	
	private static final Log LOGGER = LogFactory.getLogger("MailBagsForPAWBCreationMapper");
	 public static final String SOURCE_INDICATOR_ACCEPTED = "ACP";
	
	public List<CarditPawbDetailsVO> map(ResultSet rs) throws SQLException { 
		LOGGER.entering("MailBagsInPAWBmapper", "map");
		List<CarditPawbDetailsVO> carditPawbDetailsVOs = new ArrayList<>();
		Collection<RoutingInConsignmentVO> consignmentRoutingVOs =null;
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = null;
		Collection<MailInConsignmentVO> mailInConsignmentVOs = null;
		Collection<MailInConsignmentVO> mailInCondignmentVOss= null; 
		Collection<Integer> routingSerialNumbers = null;
		Collection<Long> consignmentScreeningSerialNumbers = null;
		RoutingInConsignmentVO consignmentRoutingVO = null;
		ConsignmentScreeningVO consignmentScreeningVO = null;
		ConsignmentDocumentVO consignmentDocumentVO = null;
		CarditPawbDetailsVO carditPawbDetailsVO = null;
		String currentKey = null;
		String previousKey = null;
		StringBuilder stringBuilder = null;
		String mailCurrentKey = null;
		String mailPreviousKey = null;
		StringBuilder mailKey = null;
		StringBuilder routingKey = null;
		String routingCurrentKey = null;
		String routingPreviousKey = null;
		StringBuilder screeningKey = null;
		String screeningCurrentKey = null;
		String screeningPreviousKey = null;
		
		while (rs.next()) {
			stringBuilder = new StringBuilder();
			currentKey = stringBuilder.append(rs.getString("CMPCOD")).append(
					rs.getString("CSGDOCNUM")).append(rs.getString("POACOD"))
					.append(rs.getInt("CSGSEQNUM")).toString();
			LOGGER.log(Log.FINE, "CurrentKey : ", currentKey);
			LOGGER.log(Log.FINE, "PreviousKey : ", previousKey);
			
			if (!currentKey.equals(previousKey)) {
				carditPawbDetailsVO =  new CarditPawbDetailsVO();
				consignmentDocumentVO = new ConsignmentDocumentVO();
				mailInConsignmentVOs = new ArrayList<>();
				mailInCondignmentVOss = new ArrayList<>();
				consignmentRoutingVOs = new ArrayList<>();
				consignmentScreeningVOs = new ArrayList<>();
				
				collectConsignmentDocumentDetails(consignmentDocumentVO, rs);
				collectCarditPAWBDetails(carditPawbDetailsVO,rs);
				
				consignmentDocumentVO
				.setRoutingInConsignmentVOs(consignmentRoutingVOs);
				Page<MailInConsignmentVO> mailInConsignmentVOss = new Page<>((ArrayList<MailInConsignmentVO>)mailInCondignmentVOss,0,0,0,0,0,false);
				consignmentDocumentVO
				.setMailInConsignmentVOs(mailInConsignmentVOss);
				
				carditPawbDetailsVO
				.setMailInConsignmentVOs(mailInConsignmentVOs);
				carditPawbDetailsVO
				.setConsignmentRoutingVOs(consignmentRoutingVOs);
				carditPawbDetailsVO
				.setConsignmentScreeningVOs(consignmentScreeningVOs);
				carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
				carditPawbDetailsVOs.add(carditPawbDetailsVO);
				previousKey = currentKey;
			}
			
			mailKey = new StringBuilder();
			mailCurrentKey = mailKey.append(currentKey).append(rs.getLong("MALSEQNUM")).toString();
			mailPreviousKey = setMailInConsignmentVosAndUpdateMailKey(rs, mailInConsignmentVOs, mailInCondignmentVOss,
					consignmentDocumentVO, carditPawbDetailsVO, mailCurrentKey, mailPreviousKey);
			
			routingKey = new StringBuilder();
			routingCurrentKey = routingKey.append(currentKey).append(rs.getInt("RTGSERNUM")).toString();
			if (!routingCurrentKey.equals(routingPreviousKey)){
				consignmentRoutingVO = new RoutingInConsignmentVO();
				setRoutingDetailsFromConsignmentVos(consignmentRoutingVO, consignmentDocumentVO);
				populateRoutingDetails(consignmentRoutingVO, rs);
				routingSerialNumbers = setRoutingSerialNumber(consignmentRoutingVOs, consignmentRoutingVO,
						routingSerialNumbers);
				consignmentRoutingVOs.add(consignmentRoutingVO);
				routingPreviousKey = routingCurrentKey;
			}
			
			screeningKey = new StringBuilder();
			screeningCurrentKey = screeningKey.append(currentKey).append(rs.getInt("SERNUM")).toString();
			if (!screeningCurrentKey.equals(screeningPreviousKey)){
				
				consignmentScreeningVO = new ConsignmentScreeningVO();
				setScreeningDetailsFromConsignmentVos(consignmentScreeningVO, consignmentDocumentVO);
				populateConsignmentScreeningDetails(consignmentScreeningVO, rs);
				consignmentScreeningSerialNumbers = setConsignmentScreeningSerialNumber(consignmentScreeningVOs,
						consignmentScreeningVO, consignmentScreeningSerialNumbers);
				consignmentScreeningVOs.add(consignmentScreeningVO);
				screeningPreviousKey = screeningCurrentKey;
			}
			
		}
		LOGGER.exiting("MailBagsInPAWBmapper", "map");
		return carditPawbDetailsVOs;
	}


	private void setScreeningDetailsFromConsignmentVos(ConsignmentScreeningVO consignmentScreeningVO,
			ConsignmentDocumentVO consignmentDocumentVO) {
		consignmentScreeningVO.setCompanyCode(consignmentDocumentVO
				.getCompanyCode());
		consignmentScreeningVO
				.setConsignmentNumber(consignmentDocumentVO
						.getConsignmentNumber());
		consignmentScreeningVO.setPaCode(consignmentDocumentVO
				.getPaCode());
		consignmentScreeningVO
				.setConsignmentSequenceNumber(consignmentDocumentVO
						.getConsignmentSequenceNumber());
	}


	private void setRoutingDetailsFromConsignmentVos(RoutingInConsignmentVO consignmentRoutingVO,
			ConsignmentDocumentVO consignmentDocumentVO) {
		consignmentRoutingVO.setCompanyCode(consignmentDocumentVO
				.getCompanyCode());
		consignmentRoutingVO
				.setConsignmentNumber(consignmentDocumentVO
						.getConsignmentNumber());
		consignmentRoutingVO.setPaCode(consignmentDocumentVO
				.getPaCode());
		consignmentRoutingVO
				.setConsignmentSequenceNumber(consignmentDocumentVO
						.getConsignmentSequenceNumber());
	}


	private String setMailInConsignmentVosAndUpdateMailKey(ResultSet rs,
			Collection<MailInConsignmentVO> mailInConsignmentVOs, Collection<MailInConsignmentVO> mailInCondignmentVOss,
			ConsignmentDocumentVO consignmentDocumentVO, CarditPawbDetailsVO carditPawbDetailsVO, String mailCurrentKey,
			String mailPreviousKey) throws SQLException {
		MailInConsignmentVO mailInConsignmentVO;
		if (!mailCurrentKey.equals(mailPreviousKey)) {
			mailInConsignmentVO = new MailInConsignmentVO();
			
			mailInConsignmentVO.setCompanyCode(consignmentDocumentVO
					.getCompanyCode());
			mailInConsignmentVO.setConsignmentNumber(consignmentDocumentVO
					.getConsignmentNumber());
			mailInConsignmentVO
					.setPaCode(consignmentDocumentVO.getPaCode());
			mailInConsignmentVO
					.setConsignmentSequenceNumber(consignmentDocumentVO
							.getConsignmentSequenceNumber());
			collectMailDetails(mailInConsignmentVO, rs);
			
			carditPawbDetailsVO.setTotalPieces(carditPawbDetailsVO.getTotalPieces()+1);
			Double weight = carditPawbDetailsVO.getTotalWeight().getDisplayValue();
			weight = weight + mailInConsignmentVO.getStatedWeight().getDisplayValue();
			carditPawbDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, 0.0,weight,UnitConstants.WEIGHT_UNIT_KILOGRAM));
			mailInConsignmentVOs.add(mailInConsignmentVO);
			mailInCondignmentVOss.add(mailInConsignmentVO);
			mailPreviousKey = mailCurrentKey;
		}
		return mailPreviousKey;
	}


	private Collection<Integer> setRoutingSerialNumber(Collection<RoutingInConsignmentVO> consignmentRoutingVOs,
			RoutingInConsignmentVO consignmentRoutingVO, Collection<Integer> routingSerialNumbers) {
		if (routingSerialNumbers == null) {
			routingSerialNumbers = new ArrayList<>();
		}
		if (!routingSerialNumbers.contains(Integer
				.valueOf(consignmentRoutingVO
						.getRoutingSerialNumber()))) {
			routingSerialNumbers.add(Integer
					.valueOf(consignmentRoutingVO
							.getRoutingSerialNumber()));
			consignmentRoutingVOs.add(consignmentRoutingVO);
		}
		return routingSerialNumbers;
	}


	private Collection<Long> setConsignmentScreeningSerialNumber(
			Collection<ConsignmentScreeningVO> consignmentScreeningVOs, ConsignmentScreeningVO consignmentScreeningVO,
			Collection<Long> consignmentScreeningSerialNumbers) {
		if (consignmentScreeningSerialNumbers == null) {
			consignmentScreeningSerialNumbers = new ArrayList<>();
		}
		if (!consignmentScreeningSerialNumbers.contains(Long
				.valueOf(consignmentScreeningVO
						.getSerialNumber()))) {
			consignmentScreeningSerialNumbers.add(Long
					.valueOf(consignmentScreeningVO
							.getSerialNumber()));
			consignmentScreeningVOs.add(consignmentScreeningVO);
		}
		return consignmentScreeningSerialNumbers;
	}

	
	private void populateRoutingDetails(RoutingInConsignmentVO routingInConsignmentVO,
			ResultSet rs) throws SQLException {
		routingInConsignmentVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
		routingInConsignmentVO.setPol(rs.getString("POL"));
		routingInConsignmentVO.setPou(rs.getString("POU"));
		routingInConsignmentVO.setOnwardFlightNumber(rs.getString("FLTNUM"));
		routingInConsignmentVO.setOnwardCarrierCode(rs.getString("FLTCARCOD"));
		routingInConsignmentVO.setOnwardCarrierId(rs.getInt("FLTCARIDR"));
		if (rs.getDate("FLTDAT") != null) {
			routingInConsignmentVO.setOnwardFlightDate(new LocalDate(rs
					.getString("POL"), Location.ARP, rs.getDate("FLTDAT")));
		}
		routingInConsignmentVO.setOnwardCarrierSeqNum(rs.getLong("FLTSEQNUM"));
		
	}
	
	private void populateConsignmentScreeningDetails(ConsignmentScreeningVO consignmentScreeningVO,
			ResultSet rs) throws SQLException {
		
		consignmentScreeningVO.setScreenDetailType(rs.getString("SCRDTLTYP"));
		if(consignmentScreeningVO.getScreenDetailType() != null){
			consignmentScreeningVO.setSecurityReasonCode(consignmentScreeningVO.getScreenDetailType());
		}
		consignmentScreeningVO.setScreeningMethodCode(rs.getString("SECSCRMTHCOD"));
		consignmentScreeningVO.setScreenLevelValue(rs.getString("SCRLVL"));
		consignmentScreeningVO.setScreeningAuthority(rs.getString("SCRAPLAUT"));
		consignmentScreeningVO.setAgentType(rs.getString("AGTTYP"));
		consignmentScreeningVO.setAgentID(rs.getString("AGTIDR"));
		consignmentScreeningVO.setCountryCode(rs.getString("CNTCOD"));
		consignmentScreeningVO.setApplicableRegTransportDirection(rs.getString("APLREGTRPDIR"));
		consignmentScreeningVO.setApplicableRegBorderAgencyAuthority(rs.getString("APLREGBRDAGYAUT"));
		consignmentScreeningVO.setApplicableRegReferenceID(rs.getString("APLREGREFIDR"));
		consignmentScreeningVO.setApplicableRegFlag(rs.getString("APLREGFLG"));
		consignmentScreeningVO.setIsoCountryCode(rs.getString("CNTCOD"));
		consignmentScreeningVO.setExpiryDate(rs.getString("EXPDAT"));
		if (rs.getTimestamp("SECSTADAT") != null) {
			consignmentScreeningVO.setSecurityStatusDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("SECSTADAT")));
		}
		consignmentScreeningVO.setSecurityStatusParty(rs.getString("SECSTAPTY"));
		consignmentScreeningVO.setAdditionalSecurityInfo(rs.getString("ADLSECINF"));
	}
	
	 private void collectConsignmentDocumentDetails(ConsignmentDocumentVO consignmentDocumentVO,
			ResultSet rs) throws SQLException {  
		 consignmentDocumentVO.setDestinationUpuCode(rs.getString("CSGDSTEXGOFCCOD"));
		 consignmentDocumentVO.setOriginUpuCode(rs.getString("CSGORGEXGOFCCOD"));
		 consignmentDocumentVO.setShipperUpuCode(rs.getString("SHPUPUCOD"));
		 consignmentDocumentVO.setConsigneeUpuCode(rs.getString("CNSUPUCOD"));
		 consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
		 consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		 consignmentDocumentVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		 consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
		 consignmentDocumentVO.setAirportCode(rs.getString("ARPCOD"));
		 consignmentDocumentVO.setOperation(rs.getString("OPRTYP"));
		 if(rs.getString("MSTDOCNUM")!= null){
			 
			 consignmentDocumentVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		 }
		 if(rs.getString("SHPPFX")!= null){
			 
			 consignmentDocumentVO.setShipmentPrefix(rs.getString("SHPPFX"));
		 }
		
	}
	 
	 private void collectCarditPAWBDetails(CarditPawbDetailsVO carditPawbDetailsVO,
				ResultSet rs) throws SQLException {  
		 carditPawbDetailsVO.setConsignmentDestination(rs.getString("CSGDSTEXGOFCCOD"));
		 carditPawbDetailsVO.setConsignmentOrigin(rs.getString("CSGORGEXGOFCCOD"));
		 carditPawbDetailsVO.setShipperCode(rs.getString("SHPUPUCOD"));
		 carditPawbDetailsVO.setConsigneeCode(rs.getString("CNSUPUCOD"));
		 carditPawbDetailsVO.setSourceIndicator(SOURCE_INDICATOR_ACCEPTED);
		 carditPawbDetailsVO.setTotalPieces(0);
		 carditPawbDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, 0.0,0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));	
		}
	
		
	private void collectMailDetails(MailInConsignmentVO mailInConsignmentVO,
			ResultSet rs) throws SQLException {
		mailInConsignmentVO.setDsn(rs.getString("DSN"));
		mailInConsignmentVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		mailInConsignmentVO.setDestinationExchangeOffice(rs
				.getString("DSTEXGOFC"));
		mailInConsignmentVO.setMailClass(rs.getString("MALCLS"));
		mailInConsignmentVO.setYear(rs.getInt("YER"));
		mailInConsignmentVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
		mailInConsignmentVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailInConsignmentVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailInConsignmentVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailInConsignmentVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailInConsignmentVO.setRegisteredOrInsuredIndicator(rs
				.getString("REGIND"));
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		mailInConsignmentVO.setUldNumber(rs.getString("ULDNUM"));
		mailInConsignmentVO.setDeclaredValue(rs.getDouble("DCLVAL"));
		mailInConsignmentVO.setCurrencyCode(rs.getString("CURCOD"));
		mailInConsignmentVO.setStatedBags(rs.getInt("BAGCNT"));
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
		mailInConsignmentVO.setMailDuplicateNumber(rs.getInt("DUPNUM"));
		mailInConsignmentVO.setSequenceNumberOfMailbag(rs.getInt("SEQNUM"));
		mailInConsignmentVO.setContractIDNumber(rs.getString("CTRNUM"));
		
	}
			 
}
