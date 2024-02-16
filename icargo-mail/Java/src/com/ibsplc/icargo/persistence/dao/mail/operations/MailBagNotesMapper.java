package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

public class MailBagNotesMapper  implements Mapper<MailHistoryRemarksVO>{

	@Override
	public MailHistoryRemarksVO map(ResultSet rs) throws SQLException {
		
		MailHistoryRemarksVO mailHistoryRemarksVO = new MailHistoryRemarksVO();
		mailHistoryRemarksVO.setRemarkSerialNumber(rs.getLong("RMKSERNUM"));
		mailHistoryRemarksVO.setRemark(rs.getString("RMK"));
		mailHistoryRemarksVO.setUserName(rs.getString("USRNAM"));
		mailHistoryRemarksVO.setRemarkDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("RMKDAT")));
		return mailHistoryRemarksVO;
	}

	

}
