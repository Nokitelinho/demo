/*
 * LoyaltyPgmToCustomersMapper.java Created on jun 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;




import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2048
 *
 */
public class LoyaltyPgmToCustomersMapper implements 
					Mapper<AttachLoyaltyProgrammeVO>{
	
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return List<LoyaltyAttributeVO>
	 * @throws SQLException
	 */
	public AttachLoyaltyProgrammeVO map(ResultSet resultSet) 
		throws SQLException {
		log.entering("TempCustMapper","Map");
		AttachLoyaltyProgrammeVO attachLoyaltyProgrammeVO = new AttachLoyaltyProgrammeVO();
		attachLoyaltyProgrammeVO.setCompanyCode(resultSet.getString("CMPCOD"));
		attachLoyaltyProgrammeVO.setLoyaltyProgrammeCode(resultSet.getString("LTYPRGCOD"));
		attachLoyaltyProgrammeVO.setGroupFlag(resultSet.getString("GROUPFLAG"));
		attachLoyaltyProgrammeVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
		
		if(resultSet.getDate("FRMDAT") != null) {
			attachLoyaltyProgrammeVO.setFromDate(
					new LocalDate(LocalDate.NO_STATION,Location.NONE,
							resultSet.getDate("FRMDAT")));
		}
	   if(resultSet.getDate("TOODAT") != null) {
		   attachLoyaltyProgrammeVO.setToDate(
				   new LocalDate(LocalDate.NO_STATION,Location.NONE,
						   resultSet.getDate("TOODAT")));
		   
		}
		if(resultSet.getDate("LTYFRMDAT") != null) {
			attachLoyaltyProgrammeVO.setLoyaltyFromDate(
					new LocalDate(LocalDate.NO_STATION,Location.NONE,
							resultSet.getDate("LTYFRMDAT")));
		}
		if(resultSet.getDate("LTYTOODAT") != null) {
			attachLoyaltyProgrammeVO.setLoyaltyToDate(
					new LocalDate(LocalDate.NO_STATION,Location.NONE,
							resultSet.getDate("LTYTOODAT")));
		}
		attachLoyaltyProgrammeVO.setSequenceNumber(resultSet.getString("SEQNUM"));
		attachLoyaltyProgrammeVO.setCustomerCode(resultSet.getString("CUSCOD"));
		Timestamp time = resultSet.getTimestamp("LSTUPDTIM");
		if(time != null){
			attachLoyaltyProgrammeVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,time));  
		}
		
		return attachLoyaltyProgrammeVO;
		
	}
}
