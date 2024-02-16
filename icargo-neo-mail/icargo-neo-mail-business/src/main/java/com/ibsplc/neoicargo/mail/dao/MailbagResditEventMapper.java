package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagHistoryVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-8061 
 */
public class MailbagResditEventMapper implements Mapper<MailbagHistoryVO> {
	public static final String CONST_RESDIT_STATUS_SEND = "Sent";
	public static final String CONST_RESDIT_STATUS_UNSEND = "Unsent";
	public static final String CONST_RESDIT_STATUS_UNPROCESSED = "UP";
	public static final String CONST_RESDIT_STATUS_NOT_INITIATED = "Not Initiated";
	public static final String CONST_RESDIT_STATUS_NOT_REQUIRED = "Sending Not Required";
	public static final String CONST_RESDIT_STATUS_SENT_FAILED = "Sending Failed";

	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public MailbagHistoryVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagHistoryVO.setMailbagId(rs.getString("MALIDR"));
		if (rs.getTimestamp("EVTDAT") != null) {
			mailbagHistoryVO.setMessageTime(localDateUtil.getLocalDate(null, rs.getTimestamp("EVTDAT")));
		}
		mailbagHistoryVO.setEventCode(rs.getString("EVTCOD"));
		if (rs.getString("PROSTA") != null && MailConstantsVO.FLAG_YES.equals(rs.getString("PROSTA"))
				&& rs.getString("RDTSND") != null
				&& MailConstantsVO.RESDIT_EVENT_GENERATED.equals(rs.getString("RDTSND"))) {
			mailbagHistoryVO.setProcessedStatus(CONST_RESDIT_STATUS_SEND);
		} else {
			if (MailConstantsVO.FLAG_YES.equals(rs.getString("PROSTA")) && rs.getString("RDTSND") != null
					&& !MailConstantsVO.RESDIT_EVENT_GENERATED.equals(rs.getString("RDTSND"))
					&& rs.getString("RDTFALRSNCOD") == null) {
				mailbagHistoryVO.setProcessedStatus(CONST_RESDIT_STATUS_SENT_FAILED);
			} else if (rs.getString("PROSTA") != null && MailConstantsVO.FLAG_YES.equals(rs.getString("PROSTA"))
					&& rs.getString("RDTSND") != null && "N".equals(rs.getString("RDTSND"))
					&& rs.getString("RDTFALRSNCOD") != null) {
				mailbagHistoryVO.setProcessedStatus(CONST_RESDIT_STATUS_NOT_REQUIRED);
			} else {
				mailbagHistoryVO.setProcessedStatus(CONST_RESDIT_STATUS_NOT_INITIATED);
			}
		}
		if (rs.getString("PROSTA") != null && MailConstantsVO.FLAG_NO.equals(rs.getString("PROSTA"))
				&& rs.getString("RDTSND") != null && MailConstantsVO.FLAG_NO.equals(rs.getString("RDTSND"))) {
			mailbagHistoryVO.setMailStatus(CONST_RESDIT_STATUS_UNPROCESSED);
		}
		if (rs.getString("PROSTA") != null && MailConstantsVO.FLAG_YES.equals(rs.getString("PROSTA"))
				&& rs.getString("RDTSND") != null && MailConstantsVO.FLAG_NO.equals(rs.getString("RDTSND"))) {
			mailbagHistoryVO.setMailStatus(CONST_RESDIT_STATUS_UNPROCESSED);
		}
		if (rs.getString("EVTPRT") != null) {
			mailbagHistoryVO.setAirportCode(rs.getString("EVTPRT"));
		}
		return mailbagHistoryVO;
	}
}
