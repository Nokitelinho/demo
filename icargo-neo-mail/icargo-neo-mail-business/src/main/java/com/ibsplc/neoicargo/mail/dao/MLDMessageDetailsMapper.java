package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MLDDetailVO;
import com.ibsplc.neoicargo.mail.vo.MLDMasterVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;

/** 
 * @author A-5526
 */
public class MLDMessageDetailsMapper implements Mapper<MLDMasterVO> {
	public MLDMasterVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MLDMasterVO mldMasterVO = new MLDMasterVO();
		MLDDetailVO mldDetailVO = new MLDDetailVO();
		mldMasterVO.setCompanyCode(rs.getString("CMPCOD"));
		mldMasterVO.setSerialNumber(rs.getInt("SERNUM"));
		mldMasterVO.setBarcodeValue(rs.getString("MALIDR"));
		mldMasterVO.setWeightCode(rs.getString("WGTCOD"));
		mldMasterVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
				BigDecimal.valueOf(Double.parseDouble(rs.getString("WGT")))));
		mldMasterVO.setEventCOde(rs.getString("EVTMOD"));
		if ("HND".equals(rs.getString("EVTMOD")) || "DLV".equals(rs.getString("EVTMOD"))
				|| "RCF".equals(rs.getString("EVTMOD")) || MailConstantsVO.MLD_TFD.equals(rs.getString("EVTMOD"))) {
			if ("HND".equals(rs.getString("EVTMOD")) && rs.getInt("OUBCARIDR") > 0) {
				mldMasterVO.setAddrCarrier(String.valueOf(rs.getInt("OUBCARIDR")));
			} else
				mldMasterVO.setAddrCarrier(String.valueOf(rs.getInt("INBCARIDR")));
		} else {
			mldMasterVO.setAddrCarrier(String.valueOf(rs.getInt("OUBCARIDR")));
		}
		mldMasterVO.setSenderAirport(rs.getString("SNDARPCOD"));
		mldMasterVO.setReceiverAirport(rs.getString("RCVARPCOD"));
		mldMasterVO.setDestAirport(rs.getString("DSTARPCOD"));
		if (mldMasterVO.getSenderAirport() != null && rs.getTimestamp("TXNTIM") != null) {
			ZonedDateTime scnDat = localDateUtil.getLocalDate(mldMasterVO.getSenderAirport(),
					rs.getTimestamp("TXNTIM"));
			mldMasterVO.setScanTime(scnDat);
		}
		mldMasterVO.setUldNumber(rs.getString("CONNUM"));
		mldDetailVO.setCompanyCode(rs.getString("CMPCOD"));
		mldDetailVO.setMailIdr(rs.getString("MALIDR"));
		mldDetailVO.setSerialNumber(rs.getInt("SERNUM"));
		mldDetailVO.setCarrierIdInb(rs.getInt("INBCARIDR"));
		mldDetailVO.setFlightNumberInb(rs.getString("INBFLTNUM"));
		mldDetailVO.setFlightSequenceNumberInb(rs.getInt("INBFLTSEQNUM"));
		mldDetailVO.setMailModeInb(rs.getString("INBSTACOD"));
		mldDetailVO.setPolInb(rs.getString("INBPOL"));
		if (mldMasterVO.getSenderAirport() != null && rs.getTimestamp("INBEVTTIM") != null) {
			ZonedDateTime scnDat = localDateUtil.getLocalDate(mldMasterVO.getSenderAirport(),
					rs.getTimestamp("INBEVTTIM"));
			mldDetailVO.setEventTimeInb(scnDat);
		}
		if (mldMasterVO.getSenderAirport() != null && rs.getTimestamp("INBFLTDAT") != null) {
			ZonedDateTime scnDat = localDateUtil.getLocalDate(mldMasterVO.getSenderAirport(),
					rs.getTimestamp("INBFLTDAT"));
			mldDetailVO.setFlightOperationDateInb(scnDat);
		}
		mldDetailVO.setPostalCodeOub(rs.getString("OUBPOACOD"));
		mldDetailVO.setCarrierIdOub(rs.getInt("OUBCARIDR"));
		mldDetailVO.setFlightNumberOub(rs.getString("OUBFLTNUM"));
		mldDetailVO.setFlightSequenceNumberOub(rs.getInt("OUBFLTSEQNUM"));
		mldDetailVO.setMailModeOub(rs.getString("OUBSTACOD"));
		mldDetailVO.setPouOub(rs.getString("OUBPOU"));
		if (mldMasterVO.getSenderAirport() != null && rs.getTimestamp("OUBEVTTIM") != null) {
			ZonedDateTime scnDat = localDateUtil.getLocalDate(mldMasterVO.getSenderAirport(),
					rs.getTimestamp("OUBEVTTIM"));
			mldDetailVO.setEventTimeOub(scnDat);
		}
		if (mldMasterVO.getSenderAirport() != null && rs.getTimestamp("OUBFLTDAT") != null) {
			ZonedDateTime scnDat = localDateUtil.getLocalDate(mldMasterVO.getSenderAirport(),
					rs.getTimestamp("OUBFLTDAT"));
			mldDetailVO.setFlightOperationDateOub(scnDat);
		}
		mldDetailVO.setPostalCodeOub(rs.getString("OUBPOACOD"));
		mldMasterVO.setMldDetailVO(mldDetailVO);
		mldMasterVO.setMessageVersion(rs.getString("MSGVER"));
		if (rs.getString("TRALVL") != null) {
			mldMasterVO.setTransactionLevel(rs.getString("TRALVL"));
		}
		return mldMasterVO;
	}
}
