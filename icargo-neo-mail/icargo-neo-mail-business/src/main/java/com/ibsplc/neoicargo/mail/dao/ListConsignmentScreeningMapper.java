package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import com.ibsplc.neoicargo.mail.vo.RoutingInConsignmentVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

public class ListConsignmentScreeningMapper implements MultiMapper<ConsignmentDocumentVO> {
	@Override
	public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ArrayList<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		Collection<RoutingInConsignmentVO> consignmentRoutingVOs = new ArrayList<>();
		String routingKey = "";
		Collection<Integer> entries = new ArrayList<>();
		Collection<String> routingKeys = new ArrayList<>();
		while (rs.next()) {
			ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
			ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
			RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
			routingKey = rs.getString("POU") + rs.getString("POL") + rs.getString("FLTCARCOD");
			consignmentDocumentVO.setCountryCode(rs.getString("CNTCOD"));
			consignmentDocumentVO.setMailCategory(rs.getString("MALCTG"));
			consignmentDocumentVO.setApplicableRegTransportDirection(rs.getString("APLREGTRPDIR"));
			consignmentDocumentVO.setApplicableRegBorderAgencyAuthority(rs.getString("APLREGBRDAGYAUT"));
			consignmentDocumentVO.setApplicableRegReferenceID(rs.getString("APLREGREFIDR"));
			consignmentDocumentVO.setApplicableRegFlag(rs.getString("APLREGFLG"));
			consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
			consignmentDocumentVO.setSecurityStatusCode(rs.getString("SECSTACOD"));
			consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
			consignmentDocumentVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
			consignmentDocumentVO.setConsignmentOrigin(rs.getString("CSGORG"));
			consignmentDocumentVO.setConsigmentDest(rs.getString("CSGDST"));
			consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
			consignmentDocumentVO.setStatedBags(rs.getInt("BAG"));
			Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")));
			consignmentDocumentVO.setStatedWeight(wgt);
			consignmentDocumentVO.setCsgIssuerName(rs.getString("CSGISRNAM"));
			consignmentDocumentVO.setMstAddionalSecurityInfo(rs.getString("MSTADLSECINF"));
			if (rs.getTimestamp("CSGDAT") != null) {
				consignmentDocumentVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getTimestamp("CSGDAT")));
			}
			ConsignmentScreeningVO consignmentScreening = populateScreeningDetails(consignmentScreeningVO, rs, entries);
			if (null != consignmentScreening) {
				consignmentScreeningVOs.add(consignmentScreening);
			}
			RoutingInConsignmentVO routingInConsignment = populateRoutingInformation(routingInConsignmentVO, rs,
					routingKey, routingKeys);
			if (null != routingInConsignment) {
				consignmentRoutingVOs.add(routingInConsignment);
			}
			consignmentDocumentVO.setConsignementScreeningVOs(consignmentScreeningVOs);
			consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
			consignmentDocumentVOs.add(consignmentDocumentVO);
		}
		return consignmentDocumentVOs;
	}

	private ConsignmentScreeningVO populateScreeningDetails(ConsignmentScreeningVO consignmentScreening, ResultSet rs,
			Collection<Integer> entries) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		int serialNumber = rs.getInt("SERNUM");
		if ((!entries.contains(serialNumber))) {
			consignmentScreening.setStatedBags(rs.getInt("STDBAG"));
			Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDWGT")));
			consignmentScreening.setStatedWeight(wgt);
			consignmentScreening.setSecurityStatusParty(rs.getString("SECSTAPTY"));
			if (rs.getTimestamp("SECSTADAT") != null) {
				consignmentScreening
						.setSecurityStatusDate(localDateUtil.getLocalDate(null, rs.getTimestamp("SECSTADAT")));
			}
			consignmentScreening.setCompanyCode(rs.getString("CMPCOD"));
			consignmentScreening.setPaCode(rs.getString("POACOD"));
			consignmentScreening.setSecurityStatusCode(rs.getString("SECSTACOD"));
			consignmentScreening.setConsignmentNumber(rs.getString("CSGDOCNUM"));
			consignmentScreening.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
			if (null == rs.getString("ADLSECINF")) {
				consignmentScreening.setAdditionalSecurityInfo(rs.getString("MSTADLSECINF"));
			} else {
				consignmentScreening.setAdditionalSecurityInfo(rs.getString("ADLSECINF"));
			}
			consignmentScreening.setRemarks(rs.getString("RMK"));
			consignmentScreening.setScreeningLocation(rs.getString("SCRLOC"));
			consignmentScreening.setScreeningMethodCode(rs.getString("SECSCRMTHCOD"));
			consignmentScreening.setScreeningAuthority(rs.getString("SCRAPLAUT"));
			consignmentScreening.setScreeningRegulation(rs.getString("SCRAPLREG"));
			consignmentScreening.setSecurityReasonCode(rs.getString("SCRDTLTYP"));
			consignmentScreening.setCountryCode(rs.getString("CNTCOD"));
			consignmentScreening.setSerialNumber(serialNumber);
			consignmentScreening.setResult(rs.getString("SCRRES"));
			consignmentScreening.setSource(rs.getString("CSGSRC"));
			consignmentScreening.setAgentType(rs.getString("AGTTYP"));
			consignmentScreening.setAgentID(rs.getString("AGTIDR"));
			consignmentScreening.setIsoCountryCode(rs.getString("ISOCNTCODE"));
			consignmentScreening.setExpiryDate(rs.getString("EXPDAT"));
			consignmentScreening.setApplicableRegTransportDirection(rs.getString("APLREGTRPDIR"));
			consignmentScreening.setApplicableRegBorderAgencyAuthority(rs.getString("APLREGBRDAGYAUT"));
			consignmentScreening.setApplicableRegReferenceID(rs.getString("APLREGREFIDR"));
			consignmentScreening.setApplicableRegFlag(rs.getString("APLREGFLG"));
			consignmentScreening.setSeScreeningAuthority(rs.getString("SCEAPLAUT"));
			consignmentScreening.setSeScreeningReasonCode(rs.getString("SCERSNCOD"));
			consignmentScreening.setSeScreeningRegulation(rs.getString("SCEAPLREG"));
			entries.add(serialNumber);
			return consignmentScreening;
		}
		return null;
	}

	private RoutingInConsignmentVO populateRoutingInformation(RoutingInConsignmentVO routingInConsignment, ResultSet rs,
			String routingKey, Collection<String> routingKeys) throws SQLException {
		if ((!routingKeys.contains(routingKey))) {
			routingInConsignment.setPou(rs.getString("POU"));
			routingInConsignment.setPol(rs.getString("POL"));
			routingInConsignment.setFlightCarrierCode(rs.getString("FLTCARCOD"));
			routingKeys.add(routingKey);
			return routingInConsignment;
		}
		return null;
	}
}
