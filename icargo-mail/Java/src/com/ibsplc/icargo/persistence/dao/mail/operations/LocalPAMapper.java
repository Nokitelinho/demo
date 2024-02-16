/*
 * LocalPAMapper.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-2037
 * 
 */
public class LocalPAMapper implements Mapper<PostalAdministrationVO> {
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public PostalAdministrationVO map(ResultSet rs) throws SQLException {
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		postalAdministrationVO.setAddress(rs.getString("POAADR"));
		postalAdministrationVO.setCompanyCode(rs.getString("CMPCOD"));
		postalAdministrationVO.setCountryCode(rs.getString("CNTCOD"));
		postalAdministrationVO.setPaCode(rs.getString("POACOD"));
		postalAdministrationVO.setPaName(rs.getString("POANAM"));
		return postalAdministrationVO;
	}

}
