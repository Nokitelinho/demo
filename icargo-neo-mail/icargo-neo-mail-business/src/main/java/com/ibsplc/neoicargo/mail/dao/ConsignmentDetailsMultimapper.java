/*
 * ConsignmentDetailsMultimapper.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.mail.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.vo.RoutingInConsignmentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;

/**
 * @author a-1883
 * 
 */
public class ConsignmentDetailsMultimapper implements
		MultiMapper<ConsignmentDocumentVO> {

	private Log log = LogFactory.getLogger("mail.operations");
	Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
	LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);

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
				routingInConsignmentVOs = new ArrayList<RoutingInConsignmentVO>();
				consignmentDocumentVO
						.setRoutingInConsignmentVOs(routingInConsignmentVOs);
				consignmentDocumentVOs.add(consignmentDocumentVO);
				previousKey = currentKey;
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
			consignmentDocumentVO.setConsignmentDate(localDateUtil.getLocalDate(rs
					.getString("ARPCOD"), rs.getDate("CSGDAT")));
		}
		consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		consignmentDocumentVO.setConsignmentSequenceNumber(rs
				.getInt("CSGSEQNUM"));
		consignmentDocumentVO.setOperation(rs.getString("OPRTYP"));
		consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
		consignmentDocumentVO.setRemarks(rs.getString("RMK"));
		consignmentDocumentVO.setStatedBags(rs.getInt("STDBAG"));
		//consignmentDocumentVO.setStatedWeight(rs.getDouble("STDWGT"));
		consignmentDocumentVO.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDWGT"))));//added by A-7371
		consignmentDocumentVO.setType(rs.getString("CSGTYP"));
        //Added as part of CRQ ICRD-103713 by A-5526 starts
		consignmentDocumentVO.setSubType(rs.getString("SUBTYP"));
		//Added as part of CRQ ICRD-103713 by A-5526 ends
		consignmentDocumentVO.setAirportCode(rs.getString("ARPCOD")); 
		/*
		 * Added By Karthick to include the Last Update Time and the Last Update User as a Part of 
		 * Optimistic Concurrency Changes
		 * 
		 * 
		 */
		if(rs.getTimestamp("LSTUPDTIM")!=null){
		consignmentDocumentVO.setLastUpdateTime(localDateUtil.getLocalDate(null,rs.getTimestamp("LSTUPDTIM")));
		}
		consignmentDocumentVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		if(rs.getString("MSTDOCNUM")!=null) {
			consignmentDocumentVO.setConsignmentMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		}
		log.exiting("ConsignmentDetailsMultimapper",
				"collectConsignmentDetails");
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
		if (rs.getDate("FLTDAT") != null && rs
				.getString("POL")!=null) {
			routingInConsignmentVO.setOnwardFlightDate(localDateUtil.getLocalDate(rs
					.getString("POL"), rs.getDate("FLTDAT")));
		}
		routingInConsignmentVO.setOnwardCarrierId(rs.getInt("FLTCARIDR"));
		if (rs.getString("POU")!=null && rs.getString("POL")!=null)
		{
		routingInConsignmentVO.setPou(rs.getString("POU"));
		routingInConsignmentVO.setPol(rs.getString("POL"));
		}
		if(rs.getString("TRSSTG")!=null){
			routingInConsignmentVO.setTransportStageQualifier(rs.getString("TRSSTG"));
		}
		log.exiting("ConsignmentDetailsMultimapper", "collectRoutingDetails");
	}
}
