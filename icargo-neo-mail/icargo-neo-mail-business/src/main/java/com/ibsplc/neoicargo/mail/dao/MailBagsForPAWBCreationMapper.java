package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.CarditPawbDetailsVO;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.vo.RoutingInConsignmentVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * Java file : com.ibsplc.icargo.persistence.dao.mail.operations.MailBagsForPAWBCreationMapper.java ---------------------------------------------------  A-9998 :
 */
@Slf4j
public class MailBagsForPAWBCreationMapper implements MultiMapper<CarditPawbDetailsVO> {
	public static final String SOURCE_INDICATOR_ACCEPTED = "ACP";

	public List<CarditPawbDetailsVO> map(ResultSet rs) throws SQLException {
		log.debug("MailBagsInPAWBmapper" + " : " + "map" + " Entering");
		List<CarditPawbDetailsVO> carditPawbDetailsVOs = new ArrayList<>();
		Collection<RoutingInConsignmentVO> consignmentRoutingVOs = null;
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = null;
		Collection<MailInConsignmentVO> mailInConsignmentVOs = null;
		Collection<MailInConsignmentVO> mailInCondignmentVOss = null;
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
			currentKey = stringBuilder.append(rs.getString("CMPCOD")).append(rs.getString("CSGDOCNUM"))
					.append(rs.getString("POACOD")).append(rs.getInt("CSGSEQNUM")).toString();
			log.debug("" + "CurrentKey : " + " " + currentKey);
			log.debug("" + "PreviousKey : " + " " + previousKey);
			if (!currentKey.equals(previousKey)) {
				carditPawbDetailsVO = new CarditPawbDetailsVO();
				consignmentDocumentVO = new ConsignmentDocumentVO();
				mailInConsignmentVOs = new ArrayList<>();
				mailInCondignmentVOss = new ArrayList<>();
				consignmentRoutingVOs = new ArrayList<>();
				consignmentScreeningVOs = new ArrayList<>();
				collectConsignmentDocumentDetails(consignmentDocumentVO, rs);
				collectCarditPAWBDetails(carditPawbDetailsVO, rs);
				consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
				Page<MailInConsignmentVO> mailInConsignmentVOss = new Page<>(
						(ArrayList<MailInConsignmentVO>) mailInCondignmentVOss, 0, 0, 0, 0, 0, false);
				consignmentDocumentVO.setMailInConsignmentVOs(mailInConsignmentVOss);
				carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
				carditPawbDetailsVO.setConsignmentRoutingVOs(consignmentRoutingVOs);
				carditPawbDetailsVO.setConsignmentScreeningVOs(consignmentScreeningVOs);
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
			if (!routingCurrentKey.equals(routingPreviousKey)) {
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
			if (!screeningCurrentKey.equals(screeningPreviousKey)) {
				consignmentScreeningVO = new ConsignmentScreeningVO();
				setScreeningDetailsFromConsignmentVos(consignmentScreeningVO, consignmentDocumentVO);
				populateConsignmentScreeningDetails(consignmentScreeningVO, rs);
				consignmentScreeningSerialNumbers = setConsignmentScreeningSerialNumber(consignmentScreeningVOs,
						consignmentScreeningVO, consignmentScreeningSerialNumbers);
				consignmentScreeningVOs.add(consignmentScreeningVO);
				screeningPreviousKey = screeningCurrentKey;
			}
		}
		log.debug("MailBagsInPAWBmapper" + " : " + "map" + " Exiting");
		return carditPawbDetailsVOs;
	}

	private void setScreeningDetailsFromConsignmentVos(ConsignmentScreeningVO consignmentScreeningVO,
			ConsignmentDocumentVO consignmentDocumentVO) {
		consignmentScreeningVO.setCompanyCode(consignmentDocumentVO.getCompanyCode());
		consignmentScreeningVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
		consignmentScreeningVO.setPaCode(consignmentDocumentVO.getPaCode());
		consignmentScreeningVO.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
	}

	private void setRoutingDetailsFromConsignmentVos(RoutingInConsignmentVO consignmentRoutingVO,
			ConsignmentDocumentVO consignmentDocumentVO) {
		consignmentRoutingVO.setCompanyCode(consignmentDocumentVO.getCompanyCode());
		consignmentRoutingVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
		consignmentRoutingVO.setPaCode(consignmentDocumentVO.getPaCode());
		consignmentRoutingVO.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
	}

	private String setMailInConsignmentVosAndUpdateMailKey(ResultSet rs,
			Collection<MailInConsignmentVO> mailInConsignmentVOs, Collection<MailInConsignmentVO> mailInCondignmentVOss,
			ConsignmentDocumentVO consignmentDocumentVO, CarditPawbDetailsVO carditPawbDetailsVO, String mailCurrentKey,
			String mailPreviousKey) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		MailInConsignmentVO mailInConsignmentVO;
		if (!mailCurrentKey.equals(mailPreviousKey)) {
			mailInConsignmentVO = new MailInConsignmentVO();
			mailInConsignmentVO.setCompanyCode(consignmentDocumentVO.getCompanyCode());
			mailInConsignmentVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
			mailInConsignmentVO.setPaCode(consignmentDocumentVO.getPaCode());
			mailInConsignmentVO.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
			collectMailDetails(mailInConsignmentVO, rs);
			carditPawbDetailsVO.setTotalPieces(carditPawbDetailsVO.getTotalPieces() + 1);
			double weight = carditPawbDetailsVO.getTotalWeight().getDisplayValue().doubleValue();
			weight = weight + mailInConsignmentVO.getStatedWeight().getDisplayValue().doubleValue();
			carditPawbDetailsVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(weight),
					BigDecimal.valueOf(0.0),"K"));
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
		if (!routingSerialNumbers.contains(Integer.valueOf(consignmentRoutingVO.getRoutingSerialNumber()))) {
			routingSerialNumbers.add(Integer.valueOf(consignmentRoutingVO.getRoutingSerialNumber()));
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
		if (!consignmentScreeningSerialNumbers.contains(Long.valueOf(consignmentScreeningVO.getSerialNumber()))) {
			consignmentScreeningSerialNumbers.add(Long.valueOf(consignmentScreeningVO.getSerialNumber()));
			consignmentScreeningVOs.add(consignmentScreeningVO);
		}
		return consignmentScreeningSerialNumbers;
	}

	private void populateRoutingDetails(RoutingInConsignmentVO routingInConsignmentVO, ResultSet rs)
			throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		routingInConsignmentVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
		routingInConsignmentVO.setPol(rs.getString("POL"));
		routingInConsignmentVO.setPou(rs.getString("POU"));
		routingInConsignmentVO.setOnwardFlightNumber(rs.getString("FLTNUM"));
		routingInConsignmentVO.setOnwardCarrierCode(rs.getString("FLTCARCOD"));
		routingInConsignmentVO.setOnwardCarrierId(rs.getInt("FLTCARIDR"));
		if (rs.getDate("FLTDAT") != null) {
			routingInConsignmentVO
					.setOnwardFlightDate(localDateUtil.getLocalDate(rs.getString("POL"), rs.getDate("FLTDAT")));
		}
		routingInConsignmentVO.setOnwardCarrierSeqNum(rs.getLong("FLTSEQNUM"));
	}

	private void populateConsignmentScreeningDetails(ConsignmentScreeningVO consignmentScreeningVO, ResultSet rs)
			throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		consignmentScreeningVO.setScreenDetailType(rs.getString("SCRDTLTYP"));
		if (consignmentScreeningVO.getScreenDetailType() != null) {
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
			consignmentScreeningVO
					.setSecurityStatusDate(localDateUtil.getLocalDate(null, rs.getTimestamp("SECSTADAT")));
		}
		consignmentScreeningVO.setSecurityStatusParty(rs.getString("SECSTAPTY"));
		consignmentScreeningVO.setAdditionalSecurityInfo(rs.getString("ADLSECINF"));
	}

	private void collectConsignmentDocumentDetails(ConsignmentDocumentVO consignmentDocumentVO, ResultSet rs)
			throws SQLException {
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
		if (rs.getString("MSTDOCNUM") != null) {
			consignmentDocumentVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		}
		if (rs.getString("SHPPFX") != null) {
			consignmentDocumentVO.setShipmentPrefix(rs.getString("SHPPFX"));
		}
	}

	private void collectCarditPAWBDetails(CarditPawbDetailsVO carditPawbDetailsVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		carditPawbDetailsVO.setConsignmentDestination(rs.getString("CSGDSTEXGOFCCOD"));
		carditPawbDetailsVO.setConsignmentOrigin(rs.getString("CSGORGEXGOFCCOD"));
		carditPawbDetailsVO.setShipperCode(rs.getString("SHPUPUCOD"));
		carditPawbDetailsVO.setConsigneeCode(rs.getString("CNSUPUCOD"));
		carditPawbDetailsVO.setSourceIndicator(SOURCE_INDICATOR_ACCEPTED);
		carditPawbDetailsVO.setTotalPieces(0);
		carditPawbDetailsVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
				BigDecimal.valueOf(0.0), UnitConstants.WEIGHT_UNIT_KILOGRAM));
	}

	private void collectMailDetails(MailInConsignmentVO mailInConsignmentVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		mailInConsignmentVO.setDsn(rs.getString("DSN"));
		mailInConsignmentVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		mailInConsignmentVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		mailInConsignmentVO.setMailClass(rs.getString("MALCLS"));
		mailInConsignmentVO.setYear(rs.getInt("YER"));
		mailInConsignmentVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
		mailInConsignmentVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailInConsignmentVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailInConsignmentVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailInConsignmentVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailInConsignmentVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		mailInConsignmentVO.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
				BigDecimal.valueOf(rs.getDouble("WGT")), UnitConstants.WEIGHT_UNIT_KILOGRAM));
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
