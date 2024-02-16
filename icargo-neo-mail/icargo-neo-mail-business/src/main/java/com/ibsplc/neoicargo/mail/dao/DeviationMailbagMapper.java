package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * @author 215166
 */
@Slf4j
public class DeviationMailbagMapper implements MultiMapper<MailbagVO> {
	List<MailbagVO> mailbagVOs = new ArrayList<>();

	public List<MailbagVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.info("Entering the MailBag DeviationMailbagMapper");
		MailbagVO mailbagVO = null;
		Map<String, MailbagVO> mailBagMap = new HashMap<>();
		StringBuilder key;
		while (rs.next()) {
			key = new StringBuilder(rs.getString("MALIDR")).append(rs.getLong("MALSEQNUM"));
			if (mailBagMap.containsKey(key.toString())) {
				mailbagVO = mailBagMap.get(key.toString());
			}
			mailbagVO = new MailbagVO();
			String airport = rs.getString("SCNPRT");
			mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
			mailbagVO.setMailbagId(rs.getString("MALIDR"));
			mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
			mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
			mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
			mailbagVO.setMailClass(rs.getString("MALCLS"));
			mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
			mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
			mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
			mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
			mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
			mailbagVO.setUldNumber(rs.getString("CONNUM"));
			mailbagVO.setYear(rs.getInt("YER"));
			if (rs.getDate("CSGDAT") != null) {
				mailbagVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getTimestamp("CSGDAT")));
			}
			String mailStatus = rs.getString("MALSTA");
			mailbagVO.setLatestStatus(mailStatus);
			mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
			mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
			mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			if (rs.getString("FLTNUM") != null && !"0".equals(rs.getString("FLTNUM"))
					&& !"-1".equals(rs.getString("FLTNUM")) && airport != null) {
				if (rs.getDate("FLTDAT") != null) {
					mailbagVO.setFlightDate(localDateUtil.getLocalDate(airport, rs.getDate("FLTDAT")));
				}
			}
			mailbagVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
			mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
			if (!"RTN".equals(mailStatus)) {
				mailbagVO.setContainerNumber(rs.getString("CONNUM"));
			}
			mailbagVO.setScannedUser(rs.getString("SCNUSR"));
			mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			mailbagVO.setOperationalStatus(rs.getString("OPRSTA"));
			mailbagVO.setContainerType(rs.getString("CONTYP"));
			mailbagVO.setPou(rs.getString("POU"));
			mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
			mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
			mailbagVO.setPaCode(rs.getString("POACOD"));
			Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")));
			mailbagVO.setWeight(wgt);
			mailbagVO.setFinalDestination(rs.getString("POU"));
			mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
			mailbagVO.setTransferFlag(rs.getString("TRAFLG"));
			mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
			mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
			mailbagVO.setMailDestination(rs.getString("DSTCOD"));
			Quantity actualWeight = quantities.getQuantity(Quantities.MAIL_WGT,
					BigDecimal.valueOf(rs.getDouble("ACTWGT")));
			mailbagVO.setActualWeight(actualWeight);
			if (rs.getTimestamp("LSTUPDTIM") != null) {
				mailbagVO.setLastUpdateTime(localDateUtil.getLocalDate(null, rs.getTimestamp("LSTUPDTIM")));
			}
			mailbagVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
			mailbagVO.setFromCarrier(rs.getString("FRMCARCOD"));
			if (rs.getString("ARRSTA") != null) {
				mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
			} else {
				if ("ARR".equals(mailStatus)) {
					mailbagVO.setArrivedFlag("Y");
				}
			}
			mailbagVO.setDeliveredFlag(rs.getString("DLVSTA"));
			if (rs.getString("INVULDNUM") != null) {
				if ((rs.getString("INVULDNUM").equals(rs.getString("INVCONNUM")))
						&& !(rs.getString("INVULDNUM").startsWith(MailConstantsVO.CONST_BULK))) {
					mailbagVO.setInventoryContainerType(MailConstantsVO.ULD_TYPE);
				} else {
					mailbagVO.setInventoryContainerType(MailConstantsVO.BULK_TYPE);
				}
			}
			if (rs.getString("INVCONNUM") != null) {
				mailbagVO.setInventoryContainer(rs.getString("INVCONNUM"));
			} else {
				mailbagVO.setInventoryContainer(rs.getString("CONNUM"));
				mailbagVO.setInventoryContainerType(rs.getString("CONTYP"));
			}
			mailbagVO.setUbrNumber(rs.getString("UBRNUM"));
			Timestamp bookingUpdateTime = rs.getTimestamp("BKGLSTUPDTIM");
			Timestamp bookingFlightDetailUpdateTime = rs.getTimestamp("BKGFLTUPDTIM");
			ZonedDateTime bookingLastUpdateTime = localDateUtil.getLocalDate(null, true);
			if (bookingUpdateTime != null && bookingFlightDetailUpdateTime != null) {
				if (bookingUpdateTime.after(bookingFlightDetailUpdateTime)) {
					bookingLastUpdateTime = localDateUtil.getLocalDate(null, bookingUpdateTime);
				} else {
					bookingLastUpdateTime = localDateUtil.getLocalDate(null, bookingFlightDetailUpdateTime);
				}
				if (bookingLastUpdateTime != null) {
					mailbagVO.setBookingLastUpdateTime(bookingLastUpdateTime);
					mailbagVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
				}
			}
			if (rs.getTimestamp("REQDLVTIM") != null) {
				mailbagVO.setReqDeliveryTime(localDateUtil.getLocalDate(null, rs.getTimestamp("REQDLVTIM")));
			}
			mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
			mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
			mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));
			mailbagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
			mailbagVO.setSequenceNumber(rs.getInt("SEQNUM"));
			mailbagVO.setOrigin(rs.getString("ORGCOD"));
			mailbagVO.setDestination(rs.getString("DSTCOD"));
			mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
			mailbagVO.setMailDestination(rs.getString("DSTCOD"));
			mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
			mailbagVO.setOnTimeDelivery(rs.getString("ONNTIMDLVFLG"));
			mailbagVO.setPol(rs.getString("POL"));
			mailbagVO.setVol(rs.getDouble("VOL"));
			mailbagVO.setVolUnit(rs.getString("VOLUNT"));
			if ("Y".equals(rs.getString("ACP"))) {
				mailbagVO.setAccepted("Y");
			} else {
				mailbagVO.setAccepted("N");
			}
			if (rs.getString("ERRSCNDAT") != null && rs.getString("ERRSCNDAT").trim().length() > 0) {
				if (!"Y".equals(mailbagVO.getAccepted())) {
					mailbagVO.setScannedDate(
							localDateUtil.getLocalDate(rs.getString("ERRSCNPRT"), rs.getTimestamp("ERRSCNDAT")));
					mailbagVO.setScannedPort(rs.getString("ERRSCNPRT"));
				}
				if ("N".equals(mailbagVO.getAccepted())) {
					mailbagVO.setAccepted("E");
				}
			}
			if (mailbagVO.getErrorCode() == null) {
				mailbagVO.setErrorCode("");
			}
			StringBuilder error = new StringBuilder(mailbagVO.getErrorCode());
			if ("OBMISSED".equals(rs.getString("ERRORCOD"))) {
				error.append("OBMISSED");
			}
			if ("IBUNPLANED".equals(rs.getString("ERRORCOD"))) {
				error.append("IBUNPLANED");
			}
			mailbagVO.setErrorCode(error.toString());
			log.debug("" + "THE MAILBAG VO IS FOUND TO BE " + " " + mailbagVO);
			mailbagVOs.add(mailbagVO);
		}
		return mailbagVOs;
	}
}
