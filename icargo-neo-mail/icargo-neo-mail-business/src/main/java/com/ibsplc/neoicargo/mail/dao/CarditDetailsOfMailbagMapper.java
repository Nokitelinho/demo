package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagHistoryVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * @author A-1883Revision History ------------------------------------------------------------------------- Revision 		Date 					Author 		Description -------------------------------------------------------------------------  0.1     		   Feb 27, 2007			  	 A-1883		Created
 */
public class CarditDetailsOfMailbagMapper implements Mapper<MailbagHistoryVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public MailbagHistoryVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String stationCode = rs.getString("STNCOD");
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagHistoryVO.setMailbagId(rs.getString("MALIDR"));
		if (rs.getTimestamp("CDTRCVDAT") != null) {
			mailbagHistoryVO.setScanDate(localDateUtil.getLocalDate(stationCode, rs.getTimestamp("CDTRCVDAT")));
			mailbagHistoryVO.setMessageTime(localDateUtil.getLocalDate(stationCode, rs.getTimestamp("CDTRCVDAT")));
		}
		mailbagHistoryVO.setFlightNumber(rs.getString("FLTNUM"));
		if (rs.getTimestamp("FLTDAT") != null) {
			mailbagHistoryVO.setFlightDate(localDateUtil.getLocalDate(stationCode, rs.getTimestamp("FLTDAT")));
		}
		mailbagHistoryVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		mailbagHistoryVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		mailbagHistoryVO.setDsn(rs.getString("DSN"));
		mailbagHistoryVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagHistoryVO.setYear(rs.getInt("YER"));
		mailbagHistoryVO.setMailRemarks(rs.getString("MALRMK"));
		mailbagHistoryVO.setMailClass(rs.getString("MALSUBCLS").substring(0, 1));
		mailbagHistoryVO.setMailCategoryCode(rs.getString("MALCTG"));
		mailbagHistoryVO.setRsn(rs.getString("RSN"));
		mailbagHistoryVO
				.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
		if (rs.getTimestamp("REQDLVTIM") != null) {
			mailbagHistoryVO.setReqDeliveryTime(localDateUtil.getLocalDate(null, rs.getTimestamp("REQDLVTIM")));
		}
		mailbagHistoryVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagHistoryVO.setCarrierId(rs.getInt("CARIDR"));
		mailbagHistoryVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailbagHistoryVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailbagHistoryVO.setMailStatus(MailConstantsVO.CARDIT_EVENT);
		mailbagHistoryVO.setScannedPort(stationCode);
		mailbagHistoryVO.setPou(rs.getString("POU"));
		mailbagHistoryVO.setScanUser(rs.getString("SCNUSR"));
		mailbagHistoryVO.setActualWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACTWGT"))));
		if (rs.getString("MALSRVLVL") != null) {
			mailbagHistoryVO.setMailSerLvl(rs.getString("MALSRVLVL"));
		}
		if (rs.getString("ORGCOD") != null) {
			mailbagHistoryVO.setOrigin(rs.getString("ORGCOD"));
		}
		if (rs.getString("DSTCOD") != null) {
			mailbagHistoryVO.setDestination(rs.getString("DSTCOD"));
		}
		if (rs.getTimestamp("CSGDAT") != null) {
			mailbagHistoryVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getTimestamp("CSGDAT")));
		}
		if (rs.getString("CSGDOCNUM") != null) {
			mailbagHistoryVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		}
		if (rs.getTimestamp("TRPSRVENDTIM") != null) {
			mailbagHistoryVO.setTransportSrvWindow(localDateUtil.getLocalDate(null, rs.getTimestamp("TRPSRVENDTIM")));
		}
		return mailbagHistoryVO;
	}
}
