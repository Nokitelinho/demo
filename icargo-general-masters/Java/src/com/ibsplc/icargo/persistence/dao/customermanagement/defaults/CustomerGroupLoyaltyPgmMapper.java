/*
 * CustomerGroupLoyaltyPgmMapper.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2048
 *
 */
public class CustomerGroupLoyaltyPgmMapper implements
					Mapper<CustomerGroupLoyaltyProgrammeVO>{

	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return List<LoyaltyAttributeVO>
	 * @throws SQLException
	 */
	public CustomerGroupLoyaltyProgrammeVO map(ResultSet resultSet)
		throws SQLException {
		log.entering("CustomerGroupLoyaltyPgmMapper","Map");
		CustomerGroupLoyaltyProgrammeVO customerGroupLoyaltyProgrammeVO = new CustomerGroupLoyaltyProgrammeVO();

		customerGroupLoyaltyProgrammeVO.setCompanyCode(resultSet.getString("CMPCOD"));
		customerGroupLoyaltyProgrammeVO.setGroupCode(resultSet.getString("GRPCOD"));
		customerGroupLoyaltyProgrammeVO.setLoyaltyProgramCode(resultSet.getString("LTYPRGCOD"));
		customerGroupLoyaltyProgrammeVO.setSequenceNumber(resultSet.getInt("SEQNUM"));
		customerGroupLoyaltyProgrammeVO.setPointsAccruded(resultSet.getInt("PTSARD"));
		customerGroupLoyaltyProgrammeVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
		if(resultSet.getDate("FRMDAT") != null) {
			customerGroupLoyaltyProgrammeVO.setFromDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,
					resultSet.getDate("FRMDAT")));
		}
		if(resultSet.getDate("TOODAT") != null) {
			customerGroupLoyaltyProgrammeVO.setToDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,
					resultSet.getDate("TOODAT")));
		}

		if(resultSet.getDate("LTYFRMDAT") != null) {
			customerGroupLoyaltyProgrammeVO.setLoyaltyFromDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,
					resultSet.getDate("LTYFRMDAT")));
		}

		if(resultSet.getDate("LTYTOODAT") != null) {
			customerGroupLoyaltyProgrammeVO.setLoyaltyToDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,
					resultSet.getDate("LTYTOODAT")));
		}

		customerGroupLoyaltyProgrammeVO.setLastUpdatedUser(resultSet.getString("LSTUPDUSR"));

		log.exiting("CustomerGroupLoyaltyPgmMapper","Map");
		return customerGroupLoyaltyProgrammeVO;

	}
}
