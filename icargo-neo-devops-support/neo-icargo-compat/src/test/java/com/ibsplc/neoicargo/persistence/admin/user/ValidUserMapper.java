/*
 * ValidUserMapper.java Created on Mar 21, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.persistence.admin.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.admin.user.vo.ValidUsersVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1872
 *
 */
public class ValidUserMapper implements Mapper<ValidUsersVO> {

	public ValidUsersVO map( ResultSet resultSet ) throws SQLException {
		ValidUsersVO validUsersVO = new ValidUsersVO();
		validUsersVO.setUserCode( resultSet.getString("USRCOD"));
		validUsersVO.setUserName( resultSet.getString("USRNAM"));
		validUsersVO.setCompanyCode( resultSet.getString("CMPCOD"));
		return validUsersVO;
	}
}
