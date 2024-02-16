package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;

/** 
 * @author A-2037
 */
public class ConsignmentDetailsMapper implements Mapper<MailInConsignmentVO> {
	/** 
	* @author A-2037
	* @param rs
	* @return
	* @throws SQLException
	*/
	public MailInConsignmentVO map(ResultSet rs) throws SQLException {
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode(rs.getString("CMPCOD"));
		mailInConsignmentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailInConsignmentVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		mailInConsignmentVO.setPaCode(rs.getString("POACOD"));
		mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
		mailInConsignmentVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		return mailInConsignmentVO;
	}
}
