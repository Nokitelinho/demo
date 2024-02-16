/*
 * ULDConfigMapper.java Created on May 06,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3093
 *
 */
public class ULDConfigMapper implements Mapper<AuditDetailsVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	 /**
     * Method for getting the map
     * @param rs
     * @return AuditDetailsVO
     * @throws SQLException
     */
	public AuditDetailsVO map(ResultSet rs) throws SQLException {
		log.entering("ULDAuditEnquiryMapper", "map");
		AuditDetailsVO auditDetailsVO = new AuditDetailsVO();
		auditDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		auditDetailsVO.setAction(rs.getString("TRANSACTION"));
		auditDetailsVO.setRemarks(rs.getString("RMK"));
		auditDetailsVO.setAdditionalInformation(rs.getString("RMK"));
		auditDetailsVO.setStationCode(rs.getString("ARPCOD"));
		auditDetailsVO.setLastUpdateUser(rs.getString("UPDUSR"));
		auditDetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE ,rs.getTimestamp("DAT")));
		log.log(Log.INFO, "!!!!auditDetailsVOs", rs.getDate("STKCHKDAT"));
		log.log(Log.INFO, "!!!!auditDetailsVOs", auditDetailsVO);
		log.exiting("ULDAuditEnquiryMapper", "map");


		return auditDetailsVO;
	}
}
