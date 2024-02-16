package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * @author a-2553
 */
@Slf4j
public class CarditMailsMapper implements Mapper<MailbagVO> {
	/** 
	* @author a-2553 This method is used to set the values from the resultsetinto Mapper
	* @param rs
	* @return
	* @throws SQLException 
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.info("Entering the CarditMailsMapper");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSPSRLNUM"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFF"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RCPSRLNUM"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFF"));
		mailbagVO.setMailClass((rs.getString("MALSUBCOD")).substring(0, 1));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCOD"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setYear(rs.getInt("DSPYER"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSTRCPNUM"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("RCPRGDINS"));
		mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("RCPWGT"))));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setPaCode(rs.getString("SDRIDR"));
		mailbagVO.setCarrierCode(rs.getString("CARCOD"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setUpliftAirport(rs.getString("POL"));
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
		mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));
		mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
		if (rs.getDate("DEPTIM") != null) {
			mailbagVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getTimestamp("DEPTIM")));
		}
		mailbagVO.setUldNumber(rs.getString("CONNUM"));
		if (rs.getDate("CSGCMPDAT") != null) {
			mailbagVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getDate("CSGCMPDAT")));
		}
		if (rs.getTimestamp("REQDLVTIM") != null) {
			mailbagVO.setReqDeliveryTime(localDateUtil.getLocalDate(null, rs.getTimestamp("REQDLVTIM")));
		}
		if (rs.getTimestamp("TRPSRVENDTIM") != null) {
			mailbagVO.setTransWindowEndTime(localDateUtil.getLocalDate(null, rs.getTimestamp("TRPSRVENDTIM")));
		}
		if ("Y".equals(rs.getString("ACP"))) {
			mailbagVO.setAccepted("Y");
		} else {
			mailbagVO.setAccepted("N");
		}
		if ("N".equals(mailbagVO.getAccepted())) {
			if (rs.getString("SCNDTL") != null && rs.getString("SCNDTL").trim().length() > 0) {
				String[] scanDetails = rs.getString("SCNDTL").split("~");
				if (scanDetails != null && scanDetails.length == 3) {
					String scannedDate = scanDetails[2];
					ZonedDateTime scanDate = localDateUtil.getLocalDate(scanDetails[0], true);
					scanDate = LocalDate.withDateAndTime(scanDate, scannedDate);
					mailbagVO.setScannedDate(localDateUtil.getLocalDate(scanDetails[0], true));
					mailbagVO.setScannedUser(scanDetails[1]);
				}
				mailbagVO.setAccepted("E");
			}
		}
		if (rs.getString("SCNDAT") != null && rs.getString("SCNDAT").trim().length() > 0) {
			mailbagVO.setScannedDate(localDateUtil.getLocalDate(rs.getString("POL"), rs.getTimestamp("SCNDAT")));
			if ("N".equals(mailbagVO.getAccepted())) {
				mailbagVO.setAccepted("E");
			}
		}
		mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
		mailbagVO.setSequenceNumber(rs.getInt("SEQNUM"));
		mailbagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		if (rs.getInt("FLTSEQNUM") != 0) {
			mailbagVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		}
		if (rs.getString("POU") != null) {
			mailbagVO.setPou(rs.getString("POU"));
		}
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		if (rs.getDouble("VOL") != 0) {
			mailbagVO.setVol(rs.getDouble("VOL"));
		}
		if (rs.getString("VOLUNT") != null) {
			mailbagVO.setVolUnit(rs.getString("VOLUNT"));
		}
		if (rs.getLong("MALSEQNUM") != 0) {
			mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		}
		if (rs.getString("MALSRVLVL") != null) {
			mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
		}
		mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		mailbagVO.setMailbagDataSource(rs.getString("MALSRC"));
		log.debug("" + "THE MAILBAG VO IS FOUND TO BE " + " " + mailbagVO);
		return mailbagVO;
	}
}
