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
 * @author a-1303
 */
@Slf4j
public class MailbagForInventoryMapper implements Mapper<MailbagVO> {
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
		mailbagVO.setDespatchId(rs.getString("DSNIDR"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		log.debug("" + "The Mail Sub Class  " + " " + mailbagVO.getMailSubclass());
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		log.debug("" + "The Mail Category Code " + " " + mailbagVO.getMailCategoryCode());
		mailbagVO.setYear(rs.getInt("YER"));
		if (rs.getDate("SCNDAT") != null) {
			mailbagVO.setScannedDate(localDateUtil.getLocalDate(airport, rs.getDate("SCNDAT")));
		}
		log.debug("" + "THE MAIL STATUS IS " + " " + rs.getString("MALSTA"));
		String mailStatus = rs.getString("MALSTA");
		log.debug("" + "THE MAIL STATUS IS " + " " + mailStatus);
		mailbagVO.setLatestStatus(mailStatus);
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		if (rs.getLong("FLTSEQNUM") > 0) {
			if (rs.getDate("FLTDAT") != null) {
				mailbagVO.setFlightDate(localDateUtil.getLocalDate(airport, rs.getDate("FLTDAT")));
			}
		}
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
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
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailStatus)) {
			mailbagVO.setArrivedFlag(MailbagVO.FLAG_YES);
		}
		if (rs.getString("INBFLTNUM") != null) {
			log.debug("THE INBOUND DETAILS ARE COLLECTED");
			mailbagVO.setFromContainer(rs.getString("INBCONNUM"));
			mailbagVO.setFromContainerType(rs.getString("INBCONTYP"));
			mailbagVO.setFromFightNumber(rs.getString("INBFLTNUM"));
			mailbagVO.setFromFlightDate(localDateUtil.getLocalDate(airport, rs.getDate("INBFLTDAT")));
			mailbagVO.setFromFlightSequenceNumber(rs.getLong("INBFLTSEQNUM"));
			mailbagVO.setFromSegmentSerialNumber(rs.getInt("INBSEGSERNUM"));
		}
		log.debug("" + "THE MAILBAG VO IS FOUND TO BE " + " " + mailbagVO);
		return mailbagVO;
	}
}
