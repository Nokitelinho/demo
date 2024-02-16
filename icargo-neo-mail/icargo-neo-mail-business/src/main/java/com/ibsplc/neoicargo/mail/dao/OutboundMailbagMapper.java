package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import java.util.Objects;

import com.ibsplc.neoicargo.framework.util.currency.Money;

@Slf4j
public class OutboundMailbagMapper implements Mapper<MailbagVO> {
	/** 
	* @author a-1936 This method is used to set the values from the resultsetinto Mapper
	* @param rs
	* @return
	* @throws SQLException
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.info("Entering the MailBag Mapper");
		MailbagVO mailbagVO = new MailbagVO();
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
		if (rs.getDate("SCNDAT") != null) {
			mailbagVO.setScannedDate(localDateUtil.getLocalDate(airport, rs.getTimestamp("SCNDAT")));
		}
		String mailStatus = rs.getString("MALSTA");
		mailbagVO.setLatestStatus(mailStatus);
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		if (rs.getString("FLTNUM") != null && !"0".equals(rs.getString("FLTNUM"))
				&& !"-1".equals(rs.getString("FLTNUM"))) {
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

		if(Objects.nonNull(rs.getDouble("DSPWGT")) && Objects.nonNull(rs.getString("DSPWGTUNT"))) {
			Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSPWGT")),
					BigDecimal.valueOf(rs.getDouble("WGT")), rs.getString("DSPWGTUNT"));

			mailbagVO.setWeight(wgt);
		} else {
			mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
					BigDecimal.valueOf(rs.getDouble("WGT"))));
		}

		mailbagVO.setDisplayUnit(rs.getString("DSPWGTUNT"));
		mailbagVO.setFinalDestination(rs.getString("POU"));
		mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		mailbagVO.setTransferFlag(rs.getString("TRAFLG"));
		mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
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
		mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));
		if (rs.getDouble("VOL") != 0) {
			mailbagVO.setVol(rs.getDouble("VOL"));
		}
		if (rs.getString("VOLUNT") != null) {
			mailbagVO.setVolUnit(rs.getString("VOLUNT"));
		}
		if (rs.getString("MRASTA") != null) {
			mailbagVO.setMraStatus(rs.getString("MRASTA"));
		}
		mailbagVO.setMailRemarks(rs.getString("MALRMK"));
		log.debug("" + "THE MAILBAG VO IS FOUND TO BE " + " " + mailbagVO);
		mailbagVO.setCarditPresent(rs.getString("CRDPRT"));
		if (rs.getString("FRMCARCOD") != null) {
			mailbagVO.setTransferFromCarrier(rs.getString("FRMCARCOD"));
		}
		if (rs.getTimestamp("TRPSRVENDTIM") != null) {
			mailbagVO.setTransWindowEndTime(localDateUtil.getLocalDate(null, rs.getTimestamp("TRPSRVENDTIM")));
		}
		if (rs.getString("MALSRVLVL") != null) {
			mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
		}
		mailbagVO.setAcceptancePostalContainerNumber(rs.getString("ACPPOACONNUM"));
		mailbagVO.setAcceptanceAirportCode(rs.getString("ACPARPCOD"));
		mailbagVO.setBellyCartId(rs.getString("BLYCRTIDR"));
		mailbagVO.setRoutingAvlFlag(rs.getString("RTGAVL"));
		return mailbagVO;
	}
}
