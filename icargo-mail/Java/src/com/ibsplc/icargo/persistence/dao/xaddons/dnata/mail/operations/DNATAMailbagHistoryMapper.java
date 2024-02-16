package com.ibsplc.icargo.persistence.dao.xaddons.dnata.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailbagHistoryMapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

public class DNATAMailbagHistoryMapper extends MailbagHistoryMapper
			implements Mapper<MailbagHistoryVO>  
				  {


	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailbagHistoryVO map(ResultSet rs) throws SQLException {
		
		MailbagHistoryVO mailbagHistoryVO=super.map(rs);
		if(rs.getString("SCRUSR") != null){//A-9619-IASCB-44583 adding screening staff
			mailbagHistoryVO.setScreeningUser(rs.getString("SCRUSR"));
		}

		if(rs.getString("STGUNT") != null){//A-9619 for IASCB-44572
			mailbagHistoryVO.setStorageUnit(rs.getString("STGUNT"));
		}
		
		return mailbagHistoryVO;
	}
}
