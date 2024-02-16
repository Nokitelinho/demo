package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mailmasters.vo.MailBoxIdLovVO;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.MailBoxIdLovMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Aug 5, 2016	:	Draft
 */
public class MailBoxIdLovMapper implements Mapper<MailBoxIdLovVO> {
	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet) Added by 			: A-4809 on Aug 5, 2016 Used for 	: Parameters	:	@param arg0 Parameters	:	@return Parameters	:	@throws SQLException 
	*/
	@Override
	public MailBoxIdLovVO map(ResultSet rs) throws SQLException {
		MailBoxIdLovVO mailBoxIdLovVO = new MailBoxIdLovVO();
		mailBoxIdLovVO.setCompanyCode(rs.getString("CMPCOD"));
		mailBoxIdLovVO.setMailboxCode(rs.getString("MALBOXIDR"));
		mailBoxIdLovVO.setMailboxDescription(rs.getString("MALBOXDES"));
		return mailBoxIdLovVO;
	}
}
