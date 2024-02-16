package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.MailSubClassVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author This class is used to Map the Resultset into MailSubClassVO
 */
public class MailSubClassCodeMapper implements Mapper<MailSubClassVO> {
	/** 
	* @param rs
	* @return 
	* @throws SQLException
	*/
	public MailSubClassVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailSubClassVO mailSubClassVO = new MailSubClassVO();
		mailSubClassVO.setCompanyCode(rs.getString("CMPCOD"));
		mailSubClassVO.setCode(rs.getString("SUBCLSCOD"));
		mailSubClassVO.setDescription(rs.getString("DES"));
		mailSubClassVO.setSubClassGroup(rs.getString("SUBCLSGRP"));
		Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
		if (lstUpdTime != null) {
			mailSubClassVO.setLastUpdateTime(localDateUtil.getLocalDate(null, lstUpdTime));
		}
		mailSubClassVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		return mailSubClassVO;
	}
}
