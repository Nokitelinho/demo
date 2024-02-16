/*
 * ULDDiscrepancyMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1496
 *
 */
public class ULDDiscrepancyMapper implements Mapper<ULDDiscrepancyVO>{
	private Log log = LogFactory.getLogger("ULD_DEFAULTS");
    /**
     * Method for getting the map
     * @param rs
     * @return ULDStockConfigVO
     * @throws SQLException
     */
    public ULDDiscrepancyVO map(ResultSet rs) throws SQLException{
    	log.entering("ULDDiscrepancy","map");
    	ULDDiscrepancyVO discrepancyVO = new ULDDiscrepancyVO();
    	discrepancyVO.setCompanyCode(rs.getString("CMPCOD"));
    	discrepancyVO.setDiscrepencyCode(rs.getString("DISCOD"));
    	discrepancyVO.setUldNumber(rs.getString("ULDNUM"));
    	discrepancyVO.setReportingStation(rs.getString("RPTARP"));    	
    	discrepancyVO.setLastUpdatedTime(
    			new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
    	Date datTwo = rs.getDate("DISDAT");
    	if(datTwo != null){
    		discrepancyVO.setDiscrepencyDate(new LocalDate(discrepancyVO.getReportingStation(),Location.ARP,datTwo));
    	}
    	discrepancyVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
    	discrepancyVO.setFacilityType(rs.getString("FACTYP"));
    	discrepancyVO.setLocation(rs.getString("LOC"));
    	discrepancyVO.setSequenceNumber(rs.getString("SEQNUM"));
    	discrepancyVO.setRemarks(rs.getString("RMK"));
    	return discrepancyVO;
    }
}
