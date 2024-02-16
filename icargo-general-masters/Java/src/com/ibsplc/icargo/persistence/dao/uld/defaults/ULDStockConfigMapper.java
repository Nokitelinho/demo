/*
 * ULDStockConfigMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1496
 *
 */
public class ULDStockConfigMapper implements Mapper<ULDStockConfigVO>{
	private Log log = LogFactory.getLogger("ULD_DEFAULTS");
    /**
     * Method for getting the map
     * @param rs
     * @return ULDStockConfigVO
     * @throws SQLException 
     */
    public ULDStockConfigVO map(ResultSet rs) throws SQLException{
    	
    	log.entering("ULDStockConfigMapper","List");
    	
    	ULDStockConfigVO uldStockConfigVO = new ULDStockConfigVO();    	
    	uldStockConfigVO.setCompanyCode(rs.getString("CMPCOD"));
    	uldStockConfigVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
    	uldStockConfigVO.setStationCode(rs.getString("ARPCOD"));
    	uldStockConfigVO.setUldTypeCode(rs.getString("ULDTYPCOD"));
    	uldStockConfigVO.setMaxQty(rs.getInt("MAXQTY"));
    	uldStockConfigVO.setMinQty(rs.getInt("MINQTY"));
    	uldStockConfigVO.setAirlineCode(rs.getString("ARLCOD"));
    	uldStockConfigVO.setRemarks(rs.getString("STKRMK"));
    	uldStockConfigVO.setUldNature(rs.getString("ULDNAT"));
    	uldStockConfigVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
    	log.log(Log.ALL, "ULDStockConfigMapper before settong time");
    	if( rs.getTimestamp("LSTUPDTIM") !=null ){
	    	uldStockConfigVO.setLastUpdatedTime(new LocalDate(
	    			LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("LSTUPDTIM")));
	    	log.log(Log.ALL, "ULDStockConfigMapper after settong time");
    	}
    	uldStockConfigVO.setUldGroupCode(rs.getString("ULDGRPCOD"));
    	uldStockConfigVO.setDwellTime(rs.getInt("DWLTIM"));
    	log.log(Log.INFO, "!!!!!uldStockConfigVO", uldStockConfigVO);
		return uldStockConfigVO;
    }
}
