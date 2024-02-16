package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.DSNVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OnwardRouteForSegmentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
public class FindContainersinCarrierMapper implements MultiMapper<ContainerDetailsVO> {
	private static final String CLASS_NAME = "FindContainersinCarrierMapper";

	/** 
	* The Map method that can be used to construct the List containing the  ContainerDetailsVo
	* @author a-1936
	* @param rs
	* @return
	* @throws SQLException
	*/
	@Override
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
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
		DSNVO dsnVO = null;
		Collection<DSNVO> dsnVOs = null;
		while (rs.next()) {
			currULDKey = rs.getString("CONNUM");
			//this.log.log(3, new Object[] { "The NEW ParentID is Found to be ", currULDKey });
			if (!currULDKey.startsWith("TRASH")) {
				if (!currULDKey.equals(prevULDKey)) {
					containerDetailsVO = new ContainerDetailsVO();
					populateContainerDetails(containerDetailsVO, rs);
					containerDetailsVOsList.add(containerDetailsVO);
					prevULDKey = currULDKey;
					onwardRoutingVos = new ArrayList();
					dsnVOs = new ArrayList();
					prevDSNKey = null;
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
		containerDetailsVO.setAssignedPort(assignedPort);
		containerDetailsVO.setPol(assignedPort);
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		containerDetailsVO.setLocation(rs.getString("LOCCOD"));
		containerDetailsVO.setWareHouse(rs.getString("WHSCOD"));
		containerDetailsVO.setRemarks(rs.getString("RMK"));
		containerDetailsVO.setTotalBags(rs.getInt("MALCNT"));
		containerDetailsVO
				.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
		containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
		containerDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
		containerDetailsVO.setContainerType(rs.getString("CONTYP"));
		containerDetailsVO.setTransferFromCarrier(rs.getString("FRMCARCOD"));
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
		containerDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		if (rs.getTimestamp("REQDLVTIM") != null) {
			containerDetailsVO.setMinReqDelveryTime(localDateUtil.getLocalDate(null, rs.getTimestamp("REQDLVTIM")));
		}
		Money amount = Money.of(rs.getString("BASCURCOD"));
			if(Objects.nonNull(rs.getBigDecimal("PROCHG"))) {
				amount.setAmount(rs.getBigDecimal("PROCHG"));
			}
			else{
				amount.setAmount(BigDecimal.ZERO);
			}
			containerDetailsVO.setProvosionalCharge(amount);

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
