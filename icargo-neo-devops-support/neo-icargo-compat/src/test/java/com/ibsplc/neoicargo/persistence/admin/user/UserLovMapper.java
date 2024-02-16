/*
 * UserLovMapper.java Created on Mar 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.persistence.admin.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.admin.user.vo.UserLovVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;



/**
 * @author A-1872
 *
 */
public class UserLovMapper implements Mapper<UserLovVO>{

	/**
	 * To map DB ResultSet to UserDetailsLovVO
	 *
	 * @param resultSet
	 * @throws SQLException
	 */
	public UserLovVO map(ResultSet resultSet) throws SQLException {
		UserLovVO userLovVO = new UserLovVO();
		userLovVO.setUserCode(resultSet.getString("USRCOD"));
		userLovVO.setUserFirstName(resultSet.getString("USRNAM"));
		userLovVO.setCompanyCode(resultSet.getString("CMPCOD"));
		userLovVO.setStation(resultSet.getString("DEFSTN"));
		return userLovVO;
	}

}
