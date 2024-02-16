/*
 * ValidatePoaDetailsMapper.java 
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-3251
 *
 */
public class ValidatePoaDetailsMapper implements   Mapper<PostalAdministrationDetailsVO> {

	public PostalAdministrationDetailsVO map(ResultSet rs) throws SQLException {
		PostalAdministrationDetailsVO postalAdministrationDetailsVO = new PostalAdministrationDetailsVO(); 		
		postalAdministrationDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		postalAdministrationDetailsVO.setPoaCode(rs.getString("POACOD"));
		postalAdministrationDetailsVO.setParCode(rs.getString("PARCOD"));
		postalAdministrationDetailsVO.setSernum(rs.getString("SERNUM"));
		postalAdministrationDetailsVO.setBillingSource(rs.getString("BLGSRC"));
		postalAdministrationDetailsVO.setBillingFrequency(rs.getString("BLGFRQ"));
		return postalAdministrationDetailsVO;
	}

}
