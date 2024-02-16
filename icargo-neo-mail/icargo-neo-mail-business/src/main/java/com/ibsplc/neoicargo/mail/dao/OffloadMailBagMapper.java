package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * @author A-1936
 */
public class OffloadMailBagMapper implements Mapper<MailbagVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public MailbagVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		mailbagVO.setUldNumber(rs.getString("ULDNUM"));
		mailbagVO.setPou(rs.getString("POU"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailbagVO.setScannedPort(rs.getString("ASGPRT"));
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailbagVO.setContainerType(rs.getString("CONTYP"));
		mailbagVO.setAcceptanceFlag(rs.getString("ACPFLG"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")));
		mailbagVO.setWeight(wgt);
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setFinalDestination(rs.getString("DSTCOD"));
		mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		mailbagVO.setPaCode(rs.getString("POACOD"));
		mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
		String airport = rs.getString("ASGPRT");
		if (rs.getDate("FLTDAT") != null) {
			mailbagVO.setFlightDate(localDateUtil.getLocalDate(airport, rs.getDate("FLTDAT")));
		}
		mailbagVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		mailbagVO.setOperationalStatus(rs.getString("OPRSTA"));
		return mailbagVO;
	}
}
