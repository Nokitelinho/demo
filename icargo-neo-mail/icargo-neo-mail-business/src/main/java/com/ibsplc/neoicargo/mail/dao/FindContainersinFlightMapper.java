package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OnwardRouteForSegmentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
public class FindContainersinFlightMapper implements MultiMapper<ContainerDetailsVO> {
	private static final String CLASS_NAME = "FindFlightDetailsMapper";

	/**
	 * The Map method that can be used to construct the List containing the  ContainerDetailsVo
	 * @author a-1936
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetailsVO = null;
		String currULDKey = "";
		String prevULDKey = "";
		String currOnwardKey = "";
		String prevOnwardKey = "";
		String pou = null;
		String flightNumber = null;
		String carrierCode = null;
		StringBuilder onwardFlightBuilder = null;
		String currDSNKey = null;
		String prevDSNKey = null;
		Collection<OnwardRouteForSegmentVO> onwardRoutingVos = null;
		MailbagVO mailbagVO = null;
		Collection<MailbagVO> mailbagsVOs = null;
		while (rs.next()) {
			currULDKey = new StringBuilder(rs.getString("CONNUM")).toString();
			log.debug("" + "The NEW ParentID is Found to be " + " " + currULDKey);
			if (MailConstantsVO.FLAG_NO.equals(rs.getString("ACPFLG"))
					&& MailConstantsVO.FLAG_YES.equals(rs.getString("ARRSTA"))) {
				continue;
			}
			if (currULDKey.startsWith(MailConstantsVO.BULK_TRASH)) {
				continue;
			}
			if (!currULDKey.equals(prevULDKey)) {
				containerDetailsVO = new ContainerDetailsVO();
				populateContainerDetails(containerDetailsVO, rs);
				containerDetailsVOsList.add(containerDetailsVO);
				prevULDKey = currULDKey;
				onwardRoutingVos = new ArrayList<OnwardRouteForSegmentVO>();
				onwardFlightBuilder = new StringBuilder();
				mailbagsVOs = new ArrayList<MailbagVO>();
				containerDetailsVO.setOnwardRoutingForSegmentVOs(onwardRoutingVos);
				containerDetailsVO.setMailDetails(mailbagsVOs);
			}
			if (rs.getInt("RTGSERNUM") > 0) {
				currOnwardKey = new StringBuffer(currULDKey).append(rs.getString("RTGSERNUM")).toString();
				if (!currOnwardKey.equals(prevOnwardKey)) {
					flightNumber = rs.getString("ONWFLTNUM");
					pou = rs.getString("RTGPOU");
					carrierCode = rs.getString("ONWFLTCARCOD");
					OnwardRouteForSegmentVO onwardRouteForSegmentVO = new OnwardRouteForSegmentVO();
					onwardRouteForSegmentVO.setOnwardCarrierCode(carrierCode);
					onwardRouteForSegmentVO.setOnwardCarrierId(rs.getInt("ONWFLTCARIDR"));
					if (rs.getString("ONWFLTDAT") != null) {
						ZonedDateTime localdate = localDateUtil.getLocalDate(rs.getString("ASGPRT"), false);
						localdate = LocalDate.withDate(localdate, rs.getString("ONWFLTDAT"));
						onwardRouteForSegmentVO.setOnwardFlightDate(localdate);
					}
					onwardRouteForSegmentVO.setOnwardFlightNumber(flightNumber);
					onwardRouteForSegmentVO.setPou(pou);
					onwardRouteForSegmentVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
					onwardRoutingVos.add(onwardRouteForSegmentVO);
					if (rs.getString("ONWFLTDAT") != null && flightNumber != null && flightNumber.trim().length() > 0
							&& carrierCode != null && carrierCode.trim().length() > 0 && pou != null
							&& pou.trim().length() > 0) {
						if (onwardFlightBuilder.length() > 0) {
							onwardFlightBuilder.append(", ");
						}
						onwardFlightBuilder.append(carrierCode).append("-").append(flightNumber).append("-")
								.append(rs.getString("ONWFLTDAT")).append("-").append(pou);
						containerDetailsVO.setRoute(onwardFlightBuilder.toString());
					}
					prevOnwardKey = currOnwardKey;
				}
			}
			String dsn = rs.getString("DSN");
			if (dsn != null && (rs.getInt("DSNACPBAG") > 0)) {
				currDSNKey = new StringBuilder().append(currULDKey).append(rs.getString("DSN"))
						.append(rs.getString("ORGEXGOFC")).append(rs.getString("DSTEXGOFC"))
						.append(rs.getString("MALSUBCLS")).append(rs.getString("MALCTGCOD")).append(rs.getInt("YER"))
						.toString();
				log.debug("" + "currDSNKey " + " " + currDSNKey);
				if (!currDSNKey.equals(prevDSNKey)) {
					mailbagVO = new MailbagVO();
					populateMailBagDetails(mailbagVO, rs, containerDetailsVO);
					mailbagsVOs.add(mailbagVO);
					prevDSNKey = currDSNKey;
				} else {
				}
			}
		}
		return containerDetailsVOsList;
	}

	private void populateContainerDetails(ContainerDetailsVO containerDetailsVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String assignedPort = rs.getString("ASGPRT");
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setAssignedPort(assignedPort);
		containerDetailsVO.setPol(rs.getString("ASGPRT"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		if (rs.getDate("FLTDAT") != null) {
			containerDetailsVO.setFlightDate(localDateUtil.getLocalDate(assignedPort, rs.getDate("FLTDAT")));
		}
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		containerDetailsVO.setLocation(rs.getString("LOCCOD"));
		containerDetailsVO.setWareHouse(rs.getString("WHSCOD"));
		containerDetailsVO.setRemarks(rs.getString("RMK"));
		containerDetailsVO.setTotalBags(rs.getInt("MALCNT"));

		String dspwgtunt = rs.getString("DSPWGTUNT");
//		Quantity mailBagWeight = Objects.nonNull(dspwgtunt)?(quantities.getQuantity(Quantities.MAIL_WGT,
//				BigDecimal.ZERO,
//				BigDecimal.valueOf(rs.getDouble("WGT")),dspwgtunt)):
//				quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.ZERO);
		//TODO: checking weight with 2 par

//		Quantity mailBagWeight = quantities.getQuantity(Quantities.MAIL_WGT,
//				BigDecimal.ZERO,
//				BigDecimal.valueOf(rs.getDouble("WGT")),"K");
		Quantity mailBagWeight = quantities.getQuantity(Quantities.MAIL_WGT,
				BigDecimal.valueOf(rs.getDouble("WGT")));
		if (Objects.nonNull(containerDetailsVO.getTotalWeight())) {
			containerDetailsVO.setTotalWeight(containerDetailsVO.getMailbagwt().add(mailBagWeight));
		} else {
			containerDetailsVO.setTotalWeight(mailBagWeight);
		}

		containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
		containerDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
		containerDetailsVO.setContainerType(rs.getString("CONTYP"));
		containerDetailsVO.setArrivedStatus(rs.getString("ARRSTA"));
		containerDetailsVO.setTransferFromCarrier(rs.getString("ULDFRMCARCOD"));
		containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
		containerDetailsVO.setPaCode(rs.getString("POACOD"));
		containerDetailsVO.setTransitFlag(rs.getString("TRNFLG"));
		containerDetailsVO.setActualWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACTULDWGT"))));
		containerDetailsVO.setUldFulIndFlag(rs.getString("ULDFULIND"));
		containerDetailsVO.setTransactionCode(rs.getString("TXNCOD"));
		containerDetailsVO.setUldReferenceNo(rs.getLong("ULDREFNUM"));
		if (rs.getString("CNTIDR") != null) {
			containerDetailsVO.setContentId(rs.getString("CNTIDR"));
		}
		Timestamp lstUpdateTime = rs.getTimestamp("CONLSTUPDTIM");
		if (lstUpdateTime != null) {
			containerDetailsVO.setLastUpdateTime(localDateUtil.getLocalDate(null, lstUpdateTime));
		}
		Timestamp uldLastUpdateTime = rs.getTimestamp("ULDLSTUPDTIM");
		if (uldLastUpdateTime != null) {
			containerDetailsVO.setUldLastUpdateTime(localDateUtil.getLocalDate(null, uldLastUpdateTime));
		}
		if (rs.getTimestamp("ASGDATUTC") != null) {
			containerDetailsVO.setAssignedDate(localDateUtil.getLocalDate(assignedPort, rs.getTimestamp("ASGDATUTC")));
		}
		containerDetailsVO.setAssignedUser(rs.getString("USRCOD"));
		Timestamp uldLastUpdTim = rs.getTimestamp("ULDLSTUPDTIM");
		if (uldLastUpdTim != null) {
			containerDetailsVO.setUldLastUpdateTime(localDateUtil.getLocalDate(null, uldLastUpdTim));
		}
		if (rs.getTimestamp("REQDLVTIM") != null) {
			containerDetailsVO
					.setMinReqDelveryTime(localDateUtil.getLocalDate(assignedPort, rs.getTimestamp("REQDLVTIM")));
		}
		Money amount;
		try {
			amount = Money.of(rs.getString("BASCURCOD"));
			if(Objects.nonNull(rs.getBigDecimal("PROCHG"))) {
				amount.setAmount(rs.getBigDecimal("PROCHG"));
			}
			else{
				amount.setAmount(BigDecimal.ZERO);
			}
			containerDetailsVO.setProvosionalCharge(amount);
		} finally {
		}
		if (containerDetailsVO.getTotalBags() != rs.getInt("RATEDCOUNT")) {
			containerDetailsVO.setRateAvailforallMailbags("N");
		} else {
			containerDetailsVO.setRateAvailforallMailbags("Y");
		}
		containerDetailsVO.setBaseCurrency(rs.getString("BASCURCOD"));
	}

	private void populateMailBagDetails(MailbagVO mailVO, ResultSet rs, ContainerDetailsVO containerDetailsVo)
			throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		mailVO.setCompanyCode(rs.getString("CMPCOD"));
		mailVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailVO.setOoe(rs.getString("ORGEXGOFC"));
		mailVO.setDoe(rs.getString("DSTEXGOFC"));
		mailVO.setMailClass(rs.getString("MALCLS"));
		mailVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailVO.setYear(rs.getInt("YER"));
		mailVO.setMailbagId(rs.getString("MALIDR"));
		mailVO.setVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(rs.getDouble("VOL"))));
		mailVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailVO.setSealNumber(rs.getString("SELNUM"));
		mailVO.setDamageFlag(rs.getString("DMGFLG"));
		mailVO.setShipmentPrefix(rs.getString("SHPPFX"));
		mailVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
	}
}
