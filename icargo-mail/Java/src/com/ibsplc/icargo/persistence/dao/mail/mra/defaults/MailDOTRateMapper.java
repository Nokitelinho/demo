/*
 * MailDOTRateMapper.java created on Aug 03, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-2408
 * 
 */
public class MailDOTRateMapper implements Mapper<MailDOTRateVO> {

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailDOTRateVO map(ResultSet rs) throws SQLException {
		MailDOTRateVO mailDOTRateVO = new MailDOTRateVO();
		mailDOTRateVO.setCompanyCode(rs.getString("CMPCOD"));
		mailDOTRateVO.setCircleMiles(rs.getDouble("GCM"));
		mailDOTRateVO.setDestinationCode(rs.getString("SEGDSTCOD"));
		mailDOTRateVO.setOriginCode(rs.getString("SEGORGCOD"));
		mailDOTRateVO.setDotRate(rs.getDouble("DOTRAT"));
		StringBuilder lineHaulRate = new StringBuilder("0");
		if (rs.getString("LHLRAT").indexOf(".") == 0) {
			lineHaulRate.append(rs.getString("LHLRAT"));
		}
		if (rs.getString("LHLRAT").length() == 8) {
			lineHaulRate.append("0");
		}
		mailDOTRateVO.setLineHaulRateString(lineHaulRate.toString());
		mailDOTRateVO.setLineHaulRate(rs.getDouble("LHLRAT"));
		mailDOTRateVO.setRateCode(rs.getString("RATCOD"));
		mailDOTRateVO.setRateDescription(rs.getString("RATDSC"));
		mailDOTRateVO.setRegionCode(rs.getString("REGCOD"));
		mailDOTRateVO.setTerminalHandlingRate(rs.getDouble("THGRAT"));

		return mailDOTRateVO;
	}

}
