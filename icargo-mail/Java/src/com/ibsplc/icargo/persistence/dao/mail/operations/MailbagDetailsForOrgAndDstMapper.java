package com.ibsplc.icargo.persistence.dao.mail.operations;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

public class MailbagDetailsForOrgAndDstMapper implements Mapper<MailbagVO>{


 
	/**
	 * @author A-10555
	 * @param rs
	 * @return
	 * @throws SQLException
	 */

	public MailbagVO map(ResultSet rs)
	 throws SQLException{
		MailbagVO  mailbagvo =  new MailbagVO();
		mailbagvo.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagvo.setOrigin(rs.getString("ORGCOD"));
		mailbagvo.setDestination(rs.getString("DSTCOD"));
		return mailbagvo;
	}




}