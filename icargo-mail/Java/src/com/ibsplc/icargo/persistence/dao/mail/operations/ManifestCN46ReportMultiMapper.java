/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ManifestCN46ReportMultiMapper.java
 *
 *	Created by	:	A-10647
 *	Created on	:	08-Nov-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
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
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ManifestCN46ReportMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10647	:	08-Nov-2022	:	Draft
 */
public class ManifestCN46ReportMultiMapper implements MultiMapper<ConsignmentDocumentVO> {
	private static final Log LOGGER = LogFactory.getLogger("mail.operations");
	private static final String  MANIFEST_CN46_REPORT ="ManifestCN46ReportMultiMapper";
	private static final String MALCTGCOD = "MALCTGCOD";
	private static final String ORGEXGOFC = "ORGEXGOFC";
	private static final String POACOD = "POACOD";
	private static final String DSTEXGOFC = "DSTEXGOFC";
	private static final String ARPCOD = "ARPCOD";




	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 *	Added by 			: A-10647 on 08-Nov-2022
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
		LOGGER.entering(MANIFEST_CN46_REPORT, "map");
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
				consignmentDocumentVOs = new ArrayList<>();
			}
			stringBuilder = new StringBuilder();
			currentKey = stringBuilder.append(rs.getString(MALCTGCOD)).append(
					rs.getString(ORGEXGOFC)).append(rs.getString(POACOD)).toString();
			LOGGER.log(Log.FINE, "CurrentKey : ", currentKey);
			LOGGER.log(Log.FINE, "PreviousKey : ", previousKey);
			if (!currentKey.equals(previousKey)) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				collectConsignmentDetails(consignmentDocumentVO, rs);
				mailInConsignmentVOs = new ArrayList<>();
				routingInConsignmentVOs = new ArrayList<>();
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
					rs.getString(ORGEXGOFC))
					.append(rs.getString(DSTEXGOFC)).append(
							rs.getString("MALSUBCLS")).append(
							rs.getString(MALCTGCOD)).append(rs.getInt("YER"))
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
					routingSerialNumbers = new ArrayList<>();
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
		LOGGER.exiting("MANIFEST_CN46_REPORT", "map");
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
		LOGGER.entering(MANIFEST_CN46_REPORT,
				"collectConsignmentDetails");
		consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getDate("CSGDAT") != null) {
			consignmentDocumentVO.setConsignmentDate(new LocalDate(rs
					.getString(ARPCOD), Location.ARP, rs.getDate("CSGDAT")));
		}
		consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		consignmentDocumentVO.setConsignmentSequenceNumber(rs
				.getInt("CSGSEQNUM"));
		consignmentDocumentVO.setOperation(rs.getString("OPRTYP"));
		consignmentDocumentVO.setPaCode(rs.getString(POACOD));
		consignmentDocumentVO.setRemarks(rs.getString("RMK"));
		consignmentDocumentVO.setStatedBags(rs.getInt("STDBAG"));
		consignmentDocumentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("STDWGT")));
		consignmentDocumentVO.setType(rs.getString("CSGTYP"));
		consignmentDocumentVO.setAirportCode(rs.getString(ARPCOD));
		if(rs.getString(MALCTGCOD).equals("A")) {
			consignmentDocumentVO.setSubType("CN38");	
		}
		if(rs.getString(MALCTGCOD).equals("B")) {
			consignmentDocumentVO.setSubType("CN41");	
		}
		
		if(rs.getString("OPRORG") != null){
		consignmentDocumentVO.setOperatorOrigin(rs.getString("OPRORG"));
		}
		else{
			consignmentDocumentVO.setOperatorOrigin(rs.getString(POACOD));
		}
		consignmentDocumentVO.setOperatorDestination(rs.getString("OPRDST"));
		consignmentDocumentVO.setOoeDescription(rs.getString("ORGEXGOFCDES"));
		consignmentDocumentVO.setDoeDescription(rs.getString(DSTEXGOFC));
		consignmentDocumentVO.setTransportationMeans(rs.getString("TRPMNS"));
		consignmentDocumentVO.setConsignmentPriority(rs.getString("CSGPRI"));
		consignmentDocumentVO.setFlightDetails(rs.getString("FLTDTL"));
		consignmentDocumentVO.setFlightRoute(rs.getString("FLTRUT"));
		if(consignmentDocumentVO.getFlightRoute()==null||consignmentDocumentVO.getFlightRoute().isEmpty()) {
			consignmentDocumentVO.setFlightRoute(rs.getString("POLARPNAM")+"-"+rs.getString("POUARPNAM"));
		}
		if(consignmentDocumentVO.getFlightDetails()==null||consignmentDocumentVO.getFlightDetails().isEmpty()) {
			consignmentDocumentVO.setFlightDetails(rs.getString("FLTOWN")+"-"+ rs.getString("FLTNUM"));
		}
		if (rs.getDate("FSTFLTDEPDAT") != null) {
			consignmentDocumentVO.setFirstFlightDepartureDate(new LocalDate(rs
					.getString(ARPCOD), Location.ARP, rs.getTimestamp("FSTFLTDEPDAT")));
		}
		consignmentDocumentVO.setAirlineCode(rs.getString("ARLCOD"));
		if(consignmentDocumentVO.getAirlineCode()==null || consignmentDocumentVO.getAirlineCode().isEmpty()) {
			consignmentDocumentVO.setAirlineCode(rs.getString("FLTOWN"));
		}
	
		if (rs.getTimestamp("LSTUPDTIM") != null) {
			consignmentDocumentVO.setLastUpdateTime(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getTimestamp("LSTUPDTIM")));
		}
		consignmentDocumentVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		LOGGER.exiting(MANIFEST_CN46_REPORT,
				"collectConsignmentDetails");
	}

	/**
	 * @param mailInConsignmentVO
	 * @param rs
	 * @throws SQLException
	 */
	private void collectMailDetails(MailInConsignmentVO mailInConsignmentVO,
			ResultSet rs) throws SQLException {
		LOGGER.entering(MANIFEST_CN46_REPORT, "collectMailDetails");
		mailInConsignmentVO.setDsn(rs.getString("DSN"));
		mailInConsignmentVO.setOriginExchangeOffice(rs.getString(ORGEXGOFC));
		mailInConsignmentVO.setDestinationExchangeOffice(rs
				.getString(DSTEXGOFC));
		//Added to include the MailClass
		mailInConsignmentVO.setMailClass(rs.getString("MALCLS"));
		mailInConsignmentVO.setYear(rs.getInt("YER"));
		mailInConsignmentVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
		//Added to include  the DSN PK
		mailInConsignmentVO.setMailCategoryCode(rs.getString(MALCTGCOD));
		mailInConsignmentVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailInConsignmentVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailInConsignmentVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailInConsignmentVO.setRegisteredOrInsuredIndicator(rs
				.getString("REGIND"));
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353 for ICRD-260603
		mailInConsignmentVO.setUldNumber(rs.getString("ULDNUM"));
		mailInConsignmentVO.setDeclaredValue(rs.getDouble("DCLVAL"));
		mailInConsignmentVO.setCurrencyCode(rs.getString("CURCOD"));
		mailInConsignmentVO.setStatedBags(rs.getInt("BAGCNT"));
		LOGGER.exiting(MANIFEST_CN46_REPORT, "collectMailDetails");
	}

	/**
	 * @param routingInConsignmentVO
	 * @param rs
	 * @throws SQLException
	 */
	private void collectRoutingDetails(
			RoutingInConsignmentVO routingInConsignmentVO, ResultSet rs)
			throws SQLException {
		LOGGER.entering(MANIFEST_CN46_REPORT, "collectRoutingDetails");
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
		LOGGER.exiting(MANIFEST_CN46_REPORT, "collectRoutingDetails");
	}
	

}
