package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ConsignmentDetailsForUploadMultiMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-6245	:	22-Dec-2020	:	Draft
 */
@Slf4j
public class ConsignmentDetailsForUploadMultiMapper implements MultiMapper<ConsignmentDocumentVO> {
	private static final String CLASS_NAME = "ConsignmentDetailsForUploadMultiMapper";
	private static final String CMPCOD = "CMPCOD";
	private static final String CSGDOCNUM = "CSGDOCNUM";
	private static final String MALBOXIDR = "MALBOXIDR";
	private static final String CSGDAT = "CSGDAT";
	private static final String LEGONEPOL = "LEGONEPOL";
	private static final String LEGTWOPOL = "LEGTWOPOL";
	private static final String FILE_UPLOAD = "FILUPL";
	private static final String LEGONEFLTNUM = "LEGONEFLTNUM";
	private static final String LEGTWOFLTNUM = "LEGTWOFLTNUM";
	private static final String LEGTHRFLTNUM = "LEGTHRFLTNUM";

	@Override
	public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		List<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
		ConsignmentDocumentVO consignmentDocumentVO = null;
		HashMap<String, ConsignmentDocumentVO> consignmentMap = new HashMap<>();
		int lineCount = 1;
		while (rs.next()) {
			consignmentDocumentVO = new ConsignmentDocumentVO();
			//TODO: Neo to correct
			//String key = getKey();
			String key = null;
			if (!consignmentMap.containsKey(key)) {
				populateConsignmentDocumentDetails(rs, consignmentDocumentVO);
			} else {
				consignmentDocumentVO = consignmentMap.get(key);
				populateMailInConsignmentDetails(rs, consignmentDocumentVO);
			}
			consignmentDocumentVO.setLineCount(++lineCount);
			consignmentMap.put(key, consignmentDocumentVO);
		}
		if (!consignmentMap.isEmpty()) {
			consignmentMap.entrySet().stream().forEach(e -> consignmentDocumentVOs.add(e.getValue()));
		}
		log.debug(CLASS_NAME + " : " + "map" + " Exiting");
		return consignmentDocumentVOs;
	}

	private void populateConsignmentDocumentDetails(ResultSet rs, ConsignmentDocumentVO consignmentDocumentVO)
			throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		consignmentDocumentVO.setCompanyCode(rs.getString(CMPCOD));
		consignmentDocumentVO.setConsignmentNumber(rs.getString(CSGDOCNUM));
		consignmentDocumentVO.setPaCode(rs.getString(MALBOXIDR));
		if (isNotNullAndEmpty(rs.getString(CSGDAT))) {
			consignmentDocumentVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getDate("CSGDAT")));
		}

		consignmentDocumentVO.setStatedBags(1);
		consignmentDocumentVO.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0)));
		consignmentDocumentVO.setConsignmentPriority(MailConstantsVO.FLAG_NO);
		consignmentDocumentVO.setType(rs.getString("CSGTYP"));
		consignmentDocumentVO.setSubType(rs.getString("CSGSUBTYP"));
		consignmentDocumentVO.setOperation(rs.getString("OPRTYP"));
		consignmentDocumentVO.setRemarks(rs.getString("RMK"));
		consignmentDocumentVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
		populateMailInConsignmentDetails(rs, consignmentDocumentVO);
		populateRoutingInConsignmentDetails(rs, consignmentDocumentVO);
		populateConsignmentScreeningDetails(rs, consignmentDocumentVO);
	}

	private void populateMailInConsignmentDetails(ResultSet rs, ConsignmentDocumentVO consignmentDocumentVO)
			throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		if (consignmentDocumentVO.getMailInConsignment() == null) {
			consignmentDocumentVO.setMailInConsignment(new ArrayList<MailInConsignmentVO>());
		}
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode(rs.getString(CMPCOD));
		mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
		mailInConsignmentVO.setConsignmentNumber(rs.getString(CSGDOCNUM));
		mailInConsignmentVO.setPaCode(rs.getString(MALBOXIDR));
		mailInConsignmentVO.setUldNumber(rs.getString("ULDNUM"));
		if (isNotNullAndEmpty(rs.getString(CSGDAT))) {
			mailInConsignmentVO
					.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getDate(CSGDAT)));
		}
		mailInConsignmentVO.setOperationFlag(MailInConsignmentVO.OPERATION_FLAG_INSERT);
		mailInConsignmentVO.setMailSource("SHR118");
		consignmentDocumentVO.getMailInConsignment().add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(new Page<MailInConsignmentVO>(
				(ArrayList<MailInConsignmentVO>) consignmentDocumentVO.getMailInConsignment(), 0, 0, 0, 0, 0, false));
	}

	private void populateRoutingInConsignmentDetails(ResultSet rs, ConsignmentDocumentVO consignmentDocumentVO)
			throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		RoutingInConsignmentVO routingInConsignmentVO = null;
		if (consignmentDocumentVO.getRoutingInConsignmentVOs() == null) {
			consignmentDocumentVO.setRoutingInConsignmentVOs(new ArrayList<RoutingInConsignmentVO>());
		}
		if (isNotNullAndEmpty(rs.getString(LEGONEFLTNUM))) {
			routingInConsignmentVO = new RoutingInConsignmentVO();
			routingInConsignmentVO.setCompanyCode(rs.getString(CMPCOD));
			routingInConsignmentVO.setConsignmentNumber(rs.getString(CSGDOCNUM));
			routingInConsignmentVO.setPaCode(rs.getString(MALBOXIDR));
			routingInConsignmentVO.setNoOfPieces(1);
			routingInConsignmentVO.setRoutingSerialNumber(1);
			routingInConsignmentVO.setPol(rs.getString(LEGONEPOL));
			routingInConsignmentVO.setPou(rs.getString("LEGONEPOU"));
			routingInConsignmentVO.setOnwardFlightNumber(rs.getString(LEGONEFLTNUM));
			if (isNotNullAndEmpty(rs.getString("LEGONEFLTDAT"))) {
				routingInConsignmentVO.setOnwardFlightDate(
						localDateUtil.getLocalDate(null, rs.getDate("LEGONEFLTDAT")));
			}
			routingInConsignmentVO.setOperationFlag(RoutingInConsignmentVO.OPERATION_FLAG_INSERT);
			consignmentDocumentVO.getRoutingInConsignmentVOs().add(routingInConsignmentVO);
		}
		if (isNotNullAndEmpty(rs.getString(LEGONEFLTNUM)) && isNotNullAndEmpty(rs.getString(LEGTWOFLTNUM))) {
			routingInConsignmentVO = new RoutingInConsignmentVO();
			routingInConsignmentVO.setCompanyCode(rs.getString(CMPCOD));
			routingInConsignmentVO.setConsignmentNumber(rs.getString(CSGDOCNUM));
			routingInConsignmentVO.setPaCode(rs.getString(MALBOXIDR));
			routingInConsignmentVO.setNoOfPieces(1);
			routingInConsignmentVO.setRoutingSerialNumber(2);
			routingInConsignmentVO.setPol(rs.getString(LEGTWOPOL));
			routingInConsignmentVO.setPou(rs.getString("LEGTWOPOU"));
			routingInConsignmentVO.setOnwardFlightNumber(rs.getString(LEGTWOFLTNUM));
			if (isNotNullAndEmpty(rs.getString("LEGTWOFLTDAT"))) {
				routingInConsignmentVO.setOnwardFlightDate(
						localDateUtil.getLocalDate(null, rs.getDate("LEGTWOFLTDAT")));
			}
			routingInConsignmentVO.setOperationFlag(RoutingInConsignmentVO.OPERATION_FLAG_INSERT);
			consignmentDocumentVO.getRoutingInConsignmentVOs().add(routingInConsignmentVO);
		}
		if (isNotNullAndEmpty(rs.getString(LEGONEFLTNUM)) && isNotNullAndEmpty(rs.getString(LEGTWOFLTNUM))
				&& isNotNullAndEmpty(rs.getString(LEGTHRFLTNUM))) {
			routingInConsignmentVO = new RoutingInConsignmentVO();
			routingInConsignmentVO.setCompanyCode(rs.getString(CMPCOD));
			routingInConsignmentVO.setConsignmentNumber(rs.getString(CSGDOCNUM));
			routingInConsignmentVO.setPaCode(rs.getString(MALBOXIDR));
			routingInConsignmentVO.setNoOfPieces(1);
			routingInConsignmentVO.setRoutingSerialNumber(3);
			routingInConsignmentVO.setPol(rs.getString("LEGTHRPOL"));
			routingInConsignmentVO.setPou(rs.getString("LEGTHRPOU"));
			routingInConsignmentVO.setOnwardFlightNumber(rs.getString(LEGTHRFLTNUM));
			if (isNotNullAndEmpty(rs.getString("LEGTHRFLTDAT"))) {
				routingInConsignmentVO.setOnwardFlightDate(
						localDateUtil.getLocalDate(null, rs.getDate("LEGTHRFLTDAT")));
			}
			routingInConsignmentVO.setOperationFlag(RoutingInConsignmentVO.OPERATION_FLAG_INSERT);
			consignmentDocumentVO.getRoutingInConsignmentVOs().add(routingInConsignmentVO);
			routingInConsignmentVO = new RoutingInConsignmentVO();
			routingInConsignmentVO.setCompanyCode(rs.getString(CMPCOD));
			routingInConsignmentVO.setConsignmentNumber(rs.getString(CSGDOCNUM));
			routingInConsignmentVO.setPaCode(rs.getString(MALBOXIDR));
			routingInConsignmentVO.setNoOfPieces(1);
			routingInConsignmentVO.setRoutingSerialNumber(4);
			routingInConsignmentVO.setPol(rs.getString("LEGFORPOL"));
			routingInConsignmentVO.setPou(rs.getString("LEGFORPOU"));
			routingInConsignmentVO.setOnwardFlightNumber(rs.getString("LEGFORFLTNUM"));
			if (isNotNullAndEmpty(rs.getString("LEGFORFLTDAT"))) {
				routingInConsignmentVO.setOnwardFlightDate(
						localDateUtil.getLocalDate(null, rs.getDate("LEGFORFLTDAT")));
			}
			routingInConsignmentVO.setOperationFlag(RoutingInConsignmentVO.OPERATION_FLAG_INSERT);
			consignmentDocumentVO.getRoutingInConsignmentVOs().add(routingInConsignmentVO);
		}
	}

	private void populateConsignmentScreeningDetails(ResultSet rs, ConsignmentDocumentVO consignmentDocumentVO)
			throws SQLException {
		if (isNotNullAndEmpty(rs.getString("SECRSNCOD"))) {
			if (consignmentDocumentVO.getConsignementScreeningVOs() == null) {
				consignmentDocumentVO.setConsignementScreeningVOs(new ArrayList<ConsignmentScreeningVO>());
			}
			ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
			consignmentScreeningVO.setCompanyCode(rs.getString(CMPCOD));
			consignmentScreeningVO.setConsignmentNumber(rs.getString(CSGDOCNUM));
			consignmentScreeningVO.setPaCode(rs.getString(MALBOXIDR));
			consignmentScreeningVO.setSecurityReasonCode("CS");
			consignmentScreeningVO.setScreeningMethodCode(rs.getString("SECRSNCOD"));
			consignmentScreeningVO.setScreeningAuthority(rs.getString("SECSTAPTY"));
			consignmentScreeningVO.setScreeningLocation(rs.getString(LEGONEPOL));
			consignmentScreeningVO.setOperationFlag(ConsignmentScreeningVO.OPERATION_FLAG_INSERT);
			consignmentScreeningVO.setSource(FILE_UPLOAD);
			consignmentDocumentVO.getConsignementScreeningVOs().add(consignmentScreeningVO);
		}
		if (isNotNullAndEmpty(rs.getString("SECSCRMTHCODONE"))) {
			if (consignmentDocumentVO.getConsignementScreeningVOs() == null) {
				consignmentDocumentVO.setConsignementScreeningVOs(new ArrayList<ConsignmentScreeningVO>());
			}
			ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
			consignmentScreeningVO.setCompanyCode(rs.getString(CMPCOD));
			consignmentScreeningVO.setConsignmentNumber(rs.getString(CSGDOCNUM));
			consignmentScreeningVO.setPaCode(rs.getString(MALBOXIDR));
			consignmentScreeningVO.setSecurityReasonCode("SM");
			consignmentScreeningVO.setScreeningMethodCode(rs.getString("SECSCRMTHCODONE"));
			consignmentScreeningVO.setScreeningAuthority(rs.getString("SCRAPLAUTONE"));
			consignmentScreeningVO.setScreeningRegulation(rs.getString("SCRAPLREGONE"));
			consignmentScreeningVO.setScreeningLocation(rs.getString(LEGONEPOL));
			consignmentScreeningVO.setOperationFlag(ConsignmentScreeningVO.OPERATION_FLAG_INSERT);
			consignmentScreeningVO.setSource(FILE_UPLOAD);
			consignmentDocumentVO.getConsignementScreeningVOs().add(consignmentScreeningVO);
		}
		if (isNotNullAndEmpty(rs.getString("SECSCRMTHCODTWO"))) {
			if (consignmentDocumentVO.getConsignementScreeningVOs() == null) {
				consignmentDocumentVO.setConsignementScreeningVOs(new ArrayList<ConsignmentScreeningVO>());
			}
			ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
			consignmentScreeningVO.setCompanyCode(rs.getString(CMPCOD));
			consignmentScreeningVO.setConsignmentNumber(rs.getString(CSGDOCNUM));
			consignmentScreeningVO.setPaCode(rs.getString(MALBOXIDR));
			consignmentScreeningVO.setSecurityReasonCode("SE");
			consignmentScreeningVO.setScreeningMethodCode(rs.getString("SECSCRMTHCODTWO"));
			consignmentScreeningVO.setScreeningAuthority(rs.getString("SCRAPLAUTTWO"));
			consignmentScreeningVO.setScreeningRegulation(rs.getString("SCRAPLREGTWO"));
			consignmentScreeningVO.setScreeningLocation(rs.getString(LEGONEPOL));
			consignmentScreeningVO.setOperationFlag(ConsignmentScreeningVO.OPERATION_FLAG_INSERT);
			consignmentScreeningVO.setSource(FILE_UPLOAD);
			consignmentDocumentVO.getConsignementScreeningVOs().add(consignmentScreeningVO);
		}
	}

	private String getKey(ResultSet rs) throws SQLException {
		String key = null;
		key = new StringBuilder().append(rs.getString(CMPCOD)).append(rs.getString(CSGDOCNUM))
				.append(rs.getString(MALBOXIDR)).append(rs.getString(CSGDAT)).append(rs.getString("CSGTYP")).toString();
		return key;
	}

	private static boolean isNotNullAndEmpty(String s) {
		return s != null && !"".equals(s.trim());
	}
}
