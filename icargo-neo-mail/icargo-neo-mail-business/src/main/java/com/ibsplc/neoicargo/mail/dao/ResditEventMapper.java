package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.ResditEventVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-1739
 */
public class ResditEventMapper implements Mapper<ResditEventVO> {
	private static final String MSTDOCNUM = "MSTDOCNUM";
	private static final String SHPPFX = "SHPPFX";

	/** 
	* returns the VO
	* @param rs
	* @return ResditEventVO
	* @throws SQLException
	*/
	public ResditEventVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ResditEventVO resditEventVO = new ResditEventVO();
		if (MailConstantsVO.M49_1_1.equals(rs.getString("RDTVERNUM"))) {
			resditEventVO.setM49Resdit(true);
		}
		resditEventVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		String eventCode = rs.getString("EVTCOD");
		resditEventVO.setResditEventCode(eventCode);
		resditEventVO.setEventPort(rs.getString("EVTPRT"));
		resditEventVO.setMessageSequenceNumber(rs.getInt("MSGSEQNUM"));
		String uniqueId = System.currentTimeMillis() + eventCode;
		resditEventVO.setUniqueIdForFlag(uniqueId);
		resditEventVO.setPaCode(rs.getString("POACOD"));
		resditEventVO.setResditVersion(rs.getString("MSGVERNUM"));
		resditEventVO.setEventPortName(rs.getString("ARPNAM"));
		resditEventVO.setCarditExist(rs.getString("CDTEXT"));
		resditEventVO.setActualSenderId(rs.getString("ACTSDRIDR"));
		resditEventVO.setReciever(rs.getString("RCTIDR"));
		if (rs.getTimestamp("EVTDAT") != null && rs.getString("EVTPRT") != null
				&& rs.getString("EVTPRT").trim().length() > 0) {
			resditEventVO.setEventDate(localDateUtil.getLocalDate(rs.getString("EVTPRT"), rs.getTimestamp("EVTDAT")));
		}
		if (MailConstantsVO.FLAG_YES.equals(rs.getString("MSGEVTLOC"))) {
			resditEventVO.setMsgEventLocationEnabled(true);
		}
		if (MailConstantsVO.FLAG_YES.equals(rs.getString("PRTRDT"))) {
			resditEventVO.setPartialResdit(true);
		}
		resditEventVO.setPartyName(rs.getString("MALBOXDES"));
		resditEventVO.setCarrierCode(rs.getString("FLTCARCOD"));
		resditEventVO.setCarrierId(rs.getInt("FLTCARIDR"));
		resditEventVO.setFlightNumber(rs.getString("FLTNUM"));
		resditEventVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		resditEventVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		if (rs.getString(MSTDOCNUM) != null && !rs.getString(MSTDOCNUM).isEmpty()) {
			resditEventVO.setMasterDocumentNumber(rs.getString(MSTDOCNUM));
		}
		if (rs.getString(SHPPFX) != null && !rs.getString(SHPPFX).isEmpty()) {
			resditEventVO.setShipmentPrefix(rs.getString(SHPPFX));
		}
		return resditEventVO;
	}
}
