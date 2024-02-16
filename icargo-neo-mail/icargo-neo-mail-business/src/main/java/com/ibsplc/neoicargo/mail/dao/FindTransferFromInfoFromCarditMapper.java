package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-3251
 */
public class FindTransferFromInfoFromCarditMapper implements Mapper<MailbagVO> {
	public MailbagVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagVO mailbagVO = new MailbagVO();
		if (rs.getDate("RCVDAT") != null) {
			mailbagVO.setFromFlightDate((localDateUtil.getLocalDate(null, rs.getDate("RCVDAT"))));
		}
		mailbagVO.setTransferFromCarrier(rs.getString("RCVCARCOD"));
		mailbagVO.setFromFightNumber(rs.getString("FLTNUM"));
		mailbagVO.setPou(rs.getString("POU"));
		return mailbagVO;
	}
}
