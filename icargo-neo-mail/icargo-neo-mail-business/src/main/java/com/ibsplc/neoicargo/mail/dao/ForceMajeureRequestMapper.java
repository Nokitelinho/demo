package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.ForceMajeureRequestVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * @author A-5219
 */
public class ForceMajeureRequestMapper implements Mapper<ForceMajeureRequestVO> {
	private static final String OPRSRC = "OPRSRC";

	/** 
	*/
	public ForceMajeureRequestVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ForceMajeureRequestVO requestVO = new ForceMajeureRequestVO();
		if (rs.getString("FILPAR") != null) {
			requestVO.setCompanyCode(rs.getString("CMPCOD"));
			requestVO.setForceMajuereID(rs.getString("FORMJRIDR"));
			if (rs.getDate("FRMDAT") != null) {
				requestVO.setFromDate(localDateUtil.getLocalDate(null, rs.getDate("FRMDAT")));
			}
			if (rs.getDate("TOODAT") != null) {
				requestVO.setToDate(localDateUtil.getLocalDate(null, rs.getDate("TOODAT")));
			}
			if (rs.getDate("REQDAT") != null) {
				requestVO.setRequestDate(localDateUtil.getLocalDate(null, rs.getDate("REQDAT")));
			}
			requestVO.setFilterParameters(rs.getString("FILPAR"));
			requestVO.setSequenceNumber(rs.getLong("SEQNUM"));
			requestVO.setStatus(rs.getString("FORMJRSTA"));
			requestVO.setApprovalRemarks(rs.getString("APRRMK"));
			requestVO.setRequestRemarks(rs.getString("REQRMK"));
		} else {
			requestVO.setCompanyCode(rs.getString("CMPCOD"));
			requestVO.setMailID(rs.getString("MALIDR"));
			requestVO.setMailSeqNumber(rs.getLong("MALSEQNUM"));
			requestVO.setForceMajuereID(rs.getString("FORMJRIDR"));
			requestVO.setSequenceNumber(rs.getLong("SEQNUM"));
			requestVO.setStatus(rs.getString("FORMJRSTA"));
			requestVO.setApprovalRemarks(rs.getString("APRRMK"));
			requestVO.setRequestRemarks(rs.getString("REQRMK"));
			requestVO.setAirportCode(rs.getString("SCNPRT"));
			if (!("-1".equals(rs.getString("FLTNUM")))) {
				requestVO.setFlightNumber(rs.getString("FLTNUM"));
			} else {
				requestVO.setFlightNumber("");
			}
			requestVO.setCarrierID(rs.getInt("FLTCARIDR"));
			requestVO.setFlightSeqNum(rs.getInt("FLTSEQNUM"));
			requestVO.setCarrierCode(rs.getString("FLTCARCOD"));
			if (rs.getDate("FLTDAT") != null) {
				requestVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getDate("FLTDAT")));
			}
			requestVO.setType(rs.getString("OPRTYP"));
			if (rs.getString(OPRSRC) != null) {
				requestVO.setSource(rs.getString(OPRSRC));
			}
			requestVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
			requestVO.setOriginAirport(rs.getString("ORGARPCOD"));
			requestVO.setDestinationAirport(rs.getString("DSTARPCOD"));
			requestVO.setConsignmentDocNumber(rs.getString("CSGDOCNUM"));
			requestVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
			requestVO.setRecieveScan(rs.getString("RCVSCNFRCMJRFLG"));
			requestVO.setLoadScan(rs.getString("LODSCNFRCMJRFLG"));
			requestVO.setDeliveryScan(rs.getString("DLVSCNFRCMJRFLG"));
			requestVO.setLateDeliveryScan(rs.getString("LATDLVFRCMJRFLG"));
			requestVO.setAllScan(rs.getString("ALLSCNFRCMJRFLG"));
		}
		return requestVO;
	}
}
