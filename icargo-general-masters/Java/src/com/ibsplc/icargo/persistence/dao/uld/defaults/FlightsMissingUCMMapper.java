/*
 * FlightsMissingUCMMapper.java Created on Jun 5, 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3791
 *
 */
public class FlightsMissingUCMMapper implements Mapper<FlightDetailsVO>{
	private Log log = LogFactory.getLogger("ULD_DEFAULTS");
    /**
     * Method for getting the map
     * @param rs
     * @return ULDDamageChecklistVO
     * @throws SQLException
     */
    public FlightDetailsVO map(ResultSet rs) throws SQLException{
    	log.entering("ListULDDamagechecklistMasterMapper","map");
    	FlightDetailsVO flightDetailsVO = new FlightDetailsVO();
    	flightDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
    	flightDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
    	flightDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
    	if(rs.getTimestamp("FLTDAT")!=null){
    		flightDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("FLTDAT")));
    	}
    	if(rs.getTimestamp("ATD")!=null){
    		flightDetailsVO.setAta(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("ATD")));
    	}
    	flightDetailsVO.setCurrentAirport(rs.getString("ARPCOD"));
    	flightDetailsVO.setFlightRoute(rs.getString("FLTROU"));
    	
    	return flightDetailsVO;
    }

}
