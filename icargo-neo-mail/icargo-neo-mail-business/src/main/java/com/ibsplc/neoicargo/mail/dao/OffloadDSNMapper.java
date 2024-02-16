package com.ibsplc.neoicargo.mail.dao;

import java.sql.Date;
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
 * @author A-1936
 */
public class OffloadDSNMapper implements Mapper<DespatchDetailsVO> {
	/** 
	* This method is used to map the ResultSet in to the DespatchDetailsVOs
	* @param rs
	* @return
	* @throws SQLException
	*/
	public DespatchDetailsVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
		String airport = rs.getString("ASGPRT");
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs.getString("DSTEXGOFC"));
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setAcceptedBags(rs.getInt("BAGS"));
		despatchDetailsVO.setAcceptedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
		despatchDetailsVO.setContainerType(rs.getString("CONTYP"));
		despatchDetailsVO.setAcceptanceFlag(rs.getString("ACPFLG"));
		despatchDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		despatchDetailsVO.setUldNumber(rs.getString("ULDNUM"));
		despatchDetailsVO.setDestination(rs.getString("DSTCOD"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setPou(rs.getString("POU"));
		despatchDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setAirportCode(airport);
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		despatchDetailsVO.setStatedBags(rs.getInt("STDBAG"));
		despatchDetailsVO.setStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDWGT"))));
		despatchDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		if (rs.getDate("ACPDAT") != null) {
			despatchDetailsVO.setAcceptedDate(localDateUtil.getLocalDate(airport, rs.getDate("ACPDAT")));
		}
		if (rs.getDate("CSGDAT") != null) {
			despatchDetailsVO.setConsignmentDate(localDateUtil.getLocalDate(airport, rs.getDate("CSGDAT")));
		}
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));
		if (rs.getString("SDRIDR") != null) {
			despatchDetailsVO.setPaCode(rs.getString("SDRIDR"));
		}
		Date csgCmpDat = rs.getDate("CSGCMPDAT");
		if (csgCmpDat != null) {
			despatchDetailsVO.setConsignmentDate(localDateUtil.getLocalDate(null, csgCmpDat));
		}
		if (despatchDetailsVO.getMailCategoryCode() == null && rs.getString("MALCTG") != null) {
			despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTG"));
		}
		return despatchDetailsVO;
	}
}
