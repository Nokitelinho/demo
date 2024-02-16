/*
 * ULDDiscrepancyMapperForHHT.java Created on Mar 17, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2667
 *
 */
public class ULDDiscrepancyMapperForHHT implements Mapper<ULDDiscrepancyVO>{
	private Log log = LogFactory.getLogger("ULD_DEFAULTS");
	
    /**
     * Method for getting the map
     * @param rs
     * @return ULDStockConfigVO
     * @throws SQLException
     * 
     */
    public ULDDiscrepancyVO map(ResultSet rs) throws SQLException{
    	log.entering("ULDDiscrepancyMapperForHHT","map");
    	ULDDiscrepancyVO discrepancyVO = new ULDDiscrepancyVO();
    	discrepancyVO.setOwnerStation(rs.getString("CURARP"));
    	discrepancyVO.setDiscrepencyCode(rs.getString("DISCOD"));
    	discrepancyVO.setUldNumber(rs.getString("ULDNUM"));
    	discrepancyVO.setTransactionStatus(rs.getString("TXNSTA"));
    	discrepancyVO.setLocation(rs.getString("LOC"));
    	discrepancyVO.setFacilityType(rs.getString("FACTYP"));
    	discrepancyVO.setAgentCode(rs.getString("RELTOO"));
    	return discrepancyVO;
    }
}
