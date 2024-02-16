package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.InboundListMailbagDetailsMultiMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-8164	:	28-Dec-2018		:	Draft
 */
@Slf4j
public class InboundListMailbagDetailsMultiMapper implements MultiMapper<MailbagVO> {
	private static final String CLASS_NAME = "InboundListMailbagDetailsMultiMapper";

	public List<MailbagVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		List<MailbagVO> mailbagVOsList = new ArrayList<MailbagVO>();
		while (rs.next()) {
			MailbagVO mailbagVO = new MailbagVO();
			if (rs.getString("MALIDR") != null) {
				mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
				String mailbagId = rs.getString("MALIDR");
				mailbagVO.setMailbagId(mailbagId);
				mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
				mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
				mailbagVO.setMailClass(rs.getString("MALCLS"));
				mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
				mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
				mailbagVO.setYear(rs.getInt("YER"));
				mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
				mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
				mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
				mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
				mailbagVO.setSealNumber(rs.getString("SELNUM"));
				mailbagVO.setOrigin(rs.getString("ORGCOD"));
				mailbagVO.setDestination(rs.getString("DSTCOD"));
				if (0 != rs.getDouble("WGT"))
					mailbagVO.setWeight(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSPWGT")),
									BigDecimal.valueOf(rs.getDouble("WGT")), rs.getString("DSPWGTUNT")));
				if (0 != rs.getDouble("WGT"))
					mailbagVO.setStrWeight(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
				mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
				mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
				mailbagVO.setDeliveredFlag(rs.getString("DLVSTA"));
				mailbagVO.setTransferFlag(rs.getString("MALTRAFLG"));
				mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));
				if (rs.getDouble("VOL") != 0) {
					mailbagVO.setVol(rs.getDouble("VOL"));
				}
				if (rs.getString("VOLUNT") != null) {
					mailbagVO.setVolUnit(rs.getString("VOLUNT"));
				}
				if (rs.getTimestamp("SCNDAT") != null) {
					mailbagVO
							.setScannedDate(localDateUtil.getLocalDate(rs.getString("POU"), rs.getTimestamp("SCNDAT")));
				}
				mailbagVO.setMailStatus(rs.getString("MALSTA"));
				mailbagVO.setTransferFromCarrier(rs.getString("TRFCARCOD"));
				mailbagVO.setFromFightNumber(rs.getString("FLTNUM"));
				mailbagVO.setFromCarrierId(rs.getInt("FLTCARIDR"));
				mailbagVO.setFromFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
				mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
				mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
				mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));
				mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
				mailbagVO.setPaCode(rs.getString("MALPOA"));
				mailbagVO.setMailRemarks(rs.getString("MALRMK"));
				mailbagVO.setRoutingAvlFlag(rs.getString("RTGAVL"));
				mailbagVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
				mailbagVO.setTransitFlag(rs.getString("TRNFLG"));
				mailbagVO.setOnTimeDelivery(rs.getString("ONNTIMDLVFLG"));
				mailbagVO.setScanningWavedFlag(rs.getString("SCNWVDFLG"));
				if (rs.getTimestamp("TRPSRVENDTIM") != null) {
					mailbagVO.setTransWindowEndTime(localDateUtil.getLocalDate(null, rs.getTimestamp("TRPSRVENDTIM")));
				}
				if (rs.getString("MALSRVLVL") != null) {
					mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
				}
				if (rs.getTimestamp("REQDLVTIM") != null) {
					mailbagVO.setReqDeliveryTime(localDateUtil.getLocalDate(null, rs.getTimestamp("REQDLVTIM")));
				}
				mailbagVOsList.add(mailbagVO);
			}
		}
		return mailbagVOsList;
	}
}
