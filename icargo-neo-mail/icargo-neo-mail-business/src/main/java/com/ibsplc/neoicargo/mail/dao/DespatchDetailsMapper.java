package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.DespatchDetailsVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * @author A-2037 This class is used to Map the Resultset into DespatchDetailsVO
 */
public class DespatchDetailsMapper implements Mapper<DespatchDetailsVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public DespatchDetailsVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
		despatchDetailsVO.setAcceptedBags(rs.getInt("ACPBAG"));
		despatchDetailsVO.setPrevAcceptedBags(rs.getInt("ACPBAG"));
		if (rs.getTimestamp("ACPDAT") != null) {
			despatchDetailsVO.setAcceptedDate(localDateUtil.getLocalDate(null, rs.getTimestamp("ACPDAT")));
		}
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		despatchDetailsVO.setAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPWGT")),
						BigDecimal.valueOf(rs.getDouble("ACPWGT")), "K"));
		despatchDetailsVO.setPrevAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPWGT")),
						BigDecimal.valueOf(rs.getDouble("ACPWGT")), "K"));
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getTimestamp("CSGDAT") != null) {
			despatchDetailsVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getTimestamp("CSGDAT")));
		}
		despatchDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs.getString("DSTEXGOFC"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setPltEnabledFlag(rs.getString("PLTENBFLG"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setStatedBags(rs.getInt("STDBAG"));
		despatchDetailsVO.setStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDWGT")),
						BigDecimal.valueOf(rs.getDouble("STDWGT")), "K"));
		despatchDetailsVO.setPrevStatedBags(rs.getInt("STDBAG"));
		despatchDetailsVO.setPrevStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDWGT")),
						BigDecimal.valueOf(rs.getDouble("STDWGT")), "K"));
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
		despatchDetailsVO.setReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("RCVWGT")),
						BigDecimal.valueOf(rs.getDouble("RCVWGT")), "K"));
		despatchDetailsVO.setDeliveredBags(rs.getInt("DLVBAG"));
		despatchDetailsVO.setDeliveredWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DLVWGT")),
						BigDecimal.valueOf(rs.getDouble("DLVWGT")), "K"));
		despatchDetailsVO.setPrevReceivedBags(rs.getInt("RCVBAG"));
		despatchDetailsVO.setPrevReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("RCVWGT")),
						BigDecimal.valueOf(rs.getDouble("RCVWGT")), "K"));
		despatchDetailsVO.setPrevDeliveredBags(rs.getInt("DLVBAG"));
		despatchDetailsVO.setPrevDeliveredWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DLVWGT")),
						BigDecimal.valueOf(rs.getDouble("DLVWGT")), "K"));
		despatchDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		despatchDetailsVO.setTransferFlag(rs.getString("TRAFLG"));
		return despatchDetailsVO;
	}
}
