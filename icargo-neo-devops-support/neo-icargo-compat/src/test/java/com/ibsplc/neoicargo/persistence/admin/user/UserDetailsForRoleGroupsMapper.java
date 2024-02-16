
/*
 * UserDetailsForRoleGroupsMapper Created on Jul 30, 2014
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
 * @author A-5497
 *
 */
public class UserDetailsForRoleGroupsMapper implements Mapper<ValidUsersVO> {

	public ValidUsersVO map( ResultSet resultSet ) throws SQLException {
		ValidUsersVO validUsersVO = new ValidUsersVO();
		validUsersVO.setUserCode( resultSet.getString("USRCOD"));
		validUsersVO.setUserName( resultSet.getString("USRNAM"));
		validUsersVO.setCompanyCode( resultSet.getString("CMPCOD"));
		return validUsersVO;
	}
}
