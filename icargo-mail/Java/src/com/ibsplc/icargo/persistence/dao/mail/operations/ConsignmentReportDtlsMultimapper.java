/*
 * ConsignmentReportDtlsMultimapper.java Created on July 18, 2006
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

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1883
 * 
 */
public class ConsignmentReportDtlsMultimapper implements
		MultiMapper<ConsignmentDocumentVO> {

	private Log log = LogFactory.getLogger("mail.operations");

	/**
	 * 
	 * @param rs
	 * @return List<ConsignmentDocumentVO>
	 * @throws SQLException
	 */
	public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
		log.entering("ConsignmentDetailsMultimapper", "map");
		List<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		String currentKey = null;
		String previousKey = null;
		StringBuilder stringBuilder = null;
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = null;
		Collection<MailInConsignmentVO> mailInConsignmentVOs = null;
		ConsignmentDocumentVO consignmentDocumentVO = null;
		MailInConsignmentVO mailInConsignmentVO = null;
		RoutingInConsignmentVO routingInConsignmentVO = null;
		String mailCurrentKey = null;
		String mailPreviousKey = null;
		StringBuilder mailKey = null;
		Collection<Integer> routingSerialNumbers = null;
		while (rs.next()) {
			if (consignmentDocumentVOs == null) {
				consignmentDocumentVOs = new ArrayList<ConsignmentDocumentVO>();
			}
			stringBuilder = new StringBuilder();
			currentKey = stringBuilder.append(rs.getString("CMPCOD")).append(
					rs.getString("CSGDOCNUM")).append(rs.getString("POACOD"))
					.append(rs.getInt("CSGSEQNUM")).toString();
			log.log(Log.FINE, "CurrentKey : ", currentKey);
			log.log(Log.FINE, "PreviousKey : ", previousKey);
			if (!currentKey.equals(previousKey)) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				collectConsignmentDetails(consignmentDocumentVO, rs);
				mailInConsignmentVOs = new ArrayList<MailInConsignmentVO>();
				routingInConsignmentVOs = new ArrayList<RoutingInConsignmentVO>();
				consignmentDocumentVO
						.setMailInConsignmentcollVOs(mailInConsignmentVOs);
				consignmentDocumentVO
						.setRoutingInConsignmentVOs(routingInConsignmentVOs);
				consignmentDocumentVOs.add(consignmentDocumentVO);
				previousKey = currentKey;
			}
			/* Collecting mail details */
			mailKey = new StringBuilder();
			mailKey.append(rs.getString("DSN")).append(
					rs.getString("ORGEXGOFC"))
					.append(rs.getString("DSTEXGOFC")).append(
							rs.getString("MALSUBCLS")).append(
							rs.getString("MALCTGCOD")).append(rs.getInt("YER"))
					.append(rs.getLong("MALSEQNUM"));
			mailCurrentKey = mailKey.toString();
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
				mailInConsignmentVOs.add(mailInConsignmentVO);
				mailPreviousKey = mailCurrentKey;
			}
			/* Collecting Routing Details */
			if (rs.getInt("RTGSERNUM") > 0) {
				routingInConsignmentVO = new RoutingInConsignmentVO();
				routingInConsignmentVO.setCompanyCode(consignmentDocumentVO
						.getCompanyCode());
				routingInConsignmentVO
						.setConsignmentNumber(consignmentDocumentVO
								.getConsignmentNumber());
				routingInConsignmentVO.setPaCode(consignmentDocumentVO
						.getPaCode());
				routingInConsignmentVO
						.setConsignmentSequenceNumber(consignmentDocumentVO
								.getConsignmentSequenceNumber());
				collectRoutingDetails(routingInConsignmentVO, rs);
				if (routingSerialNumbers == null) {
					routingSerialNumbers = new ArrayList<Integer>();
				}
				if (!routingSerialNumbers.contains(Integer
						.valueOf(routingInConsignmentVO
								.getRoutingSerialNumber()))) {
					routingSerialNumbers.add(Integer
							.valueOf(routingInConsignmentVO
									.getRoutingSerialNumber()));
					routingInConsignmentVOs.add(routingInConsignmentVO);
				}
			}
		}
		log.exiting("ConsignmentDetailsMultimapper", "map");
		return consignmentDocumentVOs;
	}

	/**
	 * @param consignmentDocumentVO
	 * @param rs
	 * @throws SQLException
	 */
	private void collectConsignmentDetails(
			ConsignmentDocumentVO consignmentDocumentVO, ResultSet rs)
			throws SQLException {
		log.entering("ConsignmentDetailsMultimapper",
				"collectConsignmentDetails");
		consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getDate("CSGDAT") != null) {
			consignmentDocumentVO.setConsignmentDate(new LocalDate(rs
					.getString("ARPCOD"), Location.ARP, rs.getDate("CSGDAT")));
		}
		consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		consignmentDocumentVO.setConsignmentSequenceNumber(rs
				.getInt("CSGSEQNUM"));
		consignmentDocumentVO.setOperation(rs.getString("OPRTYP"));
		consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
		consignmentDocumentVO.setRemarks(rs.getString("RMK"));
		consignmentDocumentVO.setStatedBags(rs.getInt("STDBAG"));
		//consignmentDocumentVO.setStatedWeight(rs.getDouble("STDWGT"));
		consignmentDocumentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("STDWGT")));//added by A-7371
		consignmentDocumentVO.setType(rs.getString("CSGTYP"));
		consignmentDocumentVO.setAirportCode(rs.getString("ARPCOD"));
		//Added as part of CRQ- ICRD-103713 by A-5526 starts
		consignmentDocumentVO.setSubType(rs.getString("SUBTYP"));
		//Modified by A-7540 
		if(rs.getString("OPRORG") != null){
		consignmentDocumentVO.setOperatorOrigin(rs.getString("OPRORG"));
		}
		else{
			consignmentDocumentVO.setOperatorOrigin(rs.getString("POACOD"));
		}
		consignmentDocumentVO.setOperatorDestination(rs.getString("OPRDST"));
		consignmentDocumentVO.setOoeDescription(rs.getString("ORGEXGOFCDES"));
		consignmentDocumentVO.setDoeDescription(rs.getString("DSTEXGOFCDES"));
		consignmentDocumentVO.setTransportationMeans(rs.getString("TRPMNS"));
		consignmentDocumentVO.setConsignmentPriority(rs.getString("CSGPRI"));
		consignmentDocumentVO.setFlightDetails(rs.getString("FLTDTL"));
		consignmentDocumentVO.setFlightRoute(rs.getString("FLTRUT"));
		if (rs.getDate("FSTFLTDEPDAT") != null) {
			consignmentDocumentVO.setFirstFlightDepartureDate(new LocalDate(rs
					.getString("ARPCOD"), Location.ARP, rs.getTimestamp("FSTFLTDEPDAT")));
		}
		consignmentDocumentVO.setAirlineCode(rs.getString("ARLCOD"));
		//Added as part of CRQ- ICRD-103713 by A-5526 ends
		/*
		 * Added By Karthick to include the Last Update Time and the Last Update User as a Part of 
		 * Optimistic Concurrency Changes
		 * 
		 * 
		 */
		if (rs.getTimestamp("LSTUPDTIM") != null) {
			consignmentDocumentVO.setLastUpdateTime(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getTimestamp("LSTUPDTIM")));
		}
		consignmentDocumentVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		log.exiting("ConsignmentDetailsMultimapper",
				"collectConsignmentDetails");
	}

	/**
	 * @param mailInConsignmentVO
	 * @param rs
	 * @throws SQLException
	 */
	private void collectMailDetails(MailInConsignmentVO mailInConsignmentVO,
			ResultSet rs) throws SQLException {
		log.entering("ConsignmentDetailsMultimapper", "collectMailDetails");
		mailInConsignmentVO.setDsn(rs.getString("DSN"));
		mailInConsignmentVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		mailInConsignmentVO.setDestinationExchangeOffice(rs
				.getString("DSTEXGOFC"));
		//Added to include the MailClass
		mailInConsignmentVO.setMailClass(rs.getString("MALCLS"));
		mailInConsignmentVO.setYear(rs.getInt("YER"));
		mailInConsignmentVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
		//Added to include  the DSN PK
		mailInConsignmentVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailInConsignmentVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailInConsignmentVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailInConsignmentVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailInConsignmentVO.setRegisteredOrInsuredIndicator(rs
				.getString("REGIND"));
		//mailInConsignmentVO.setStatedWeight(rs.getDouble("WGT"));
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353 for ICRD-260603
		mailInConsignmentVO.setUldNumber(rs.getString("ULDNUM"));
		mailInConsignmentVO.setDeclaredValue(rs.getDouble("DCLVAL"));
		mailInConsignmentVO.setCurrencyCode(rs.getString("CURCOD"));
		mailInConsignmentVO.setStatedBags(rs.getInt("BAGCNT"));
		log.exiting("ConsignmentDetailsMultimapper", "collectMailDetails");
	}

	/**
	 * @param routingInConsignmentVO
	 * @param rs
	 * @throws SQLException
	 */
	private void collectRoutingDetails(
			RoutingInConsignmentVO routingInConsignmentVO, ResultSet rs)
			throws SQLException {
		log.entering("ConsignmentDetailsMultimapper", "collectRoutingDetails");
		routingInConsignmentVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
		routingInConsignmentVO.setOnwardCarrierCode(rs.getString("FLTCARCOD"));
		routingInConsignmentVO.setOnwardFlightNumber(rs.getString("FLTNUM"));
		if (rs.getDate("FLTDAT") != null) {
			routingInConsignmentVO.setOnwardFlightDate(new LocalDate(rs
					.getString("POL"), Location.ARP, rs.getDate("FLTDAT")));
		}
		routingInConsignmentVO.setOnwardCarrierId(rs.getInt("FLTCARIDR"));
		routingInConsignmentVO.setPou(rs.getString("POU"));
		routingInConsignmentVO.setPol(rs.getString("POL"));
		log.exiting("ConsignmentDetailsMultimapper", "collectRoutingDetails");
	}
}
