package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
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
 * @author a-8464
 */
@Slf4j
public class MailbagEnquiryMapper implements Mapper<MailbagVO> {
	/** 
	* @author a-8464 
	* @param rs
	* @return  
	* @throws SQLException
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.info("Entering the MailbagEnquiryMapper");
		MailbagVO mailbagVO = new MailbagVO();
		String airport = rs.getString("SCNPRT");
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		if (rs.getDate("SCNDAT") != null && airport != null) {
			mailbagVO.setScannedDate(localDateUtil.getLocalDate(airport, rs.getTimestamp("SCNDAT")));
		}
		String mailStatus = rs.getString("MALSTA");
		mailbagVO.setLatestStatus(mailStatus);
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		if (rs.getString("FLTNUM") != null && !"0".equals(rs.getString("FLTNUM"))
				&& !"-1".equals(rs.getString("FLTNUM")) && airport != null) {
			if (rs.getDate("FLTDAT") != null) {
				mailbagVO.setFlightDate(localDateUtil.getLocalDate(airport, rs.getDate("FLTDAT")));
			}
		}
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		mailbagVO.setPou(rs.getString("POU"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")));
		mailbagVO.setWeight(wgt);
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
		if (rs.getString("ACTWGTDSPUNT") != null) {
			mailbagVO.setActualWeight(
					quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACTWGT"))));
		}
		mailbagVO.setLastUpdateUser(rs.getString("SCNUSR"));
		if (rs.getTimestamp("REQDLVTIM") != null) {
			mailbagVO.setReqDeliveryTime(localDateUtil.getLocalDate(null, rs.getTimestamp("REQDLVTIM")));
		}
		if (rs.getTimestamp("CSGDAT") != null && airport != null) {
			mailbagVO.setConsignmentDate(localDateUtil.getLocalDate(airport, rs.getTimestamp("CSGDAT")));
		}
		mailbagVO.setRoutingInfo(rs.getString("ROUTEINFO"));
		mailbagVO.setMailRemarks(rs.getString("MALRMK"));
		if (rs.getString("TWOAPHCOD") != null) {
			mailbagVO.setCarrierCode(rs.getString("TWOAPHCOD"));
		}
		if (rs.getString("FLTNUM") != null && rs.getString("FLTNUM") != "-1") {
			mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		}
		if (rs.getString("FLTCARIDR") != null) {
			mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		}
		if (rs.getInt("FLTSEQNUM") != 0) {
			mailbagVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		}
		log.debug("" + "THE MAILBAG VO IS FOUND TO BE " + " " + mailbagVO);
		return mailbagVO;
	}
}
