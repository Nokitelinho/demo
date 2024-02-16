package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.MailHistoryRemarksVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

public class MailBagNotesMapper implements Mapper<MailHistoryRemarksVO> {
	@Override
	public MailHistoryRemarksVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailHistoryRemarksVO mailHistoryRemarksVO = new MailHistoryRemarksVO();
		mailHistoryRemarksVO.setRemarkSerialNumber(rs.getLong("RMKSERNUM"));
		mailHistoryRemarksVO.setRemark(rs.getString("RMK"));
		mailHistoryRemarksVO.setUserName(rs.getString("USRNAM"));
		mailHistoryRemarksVO.setRemarkDate(localDateUtil.getLocalDate(null, rs.getTimestamp("RMKDAT")));
		return mailHistoryRemarksVO;
	}
}
