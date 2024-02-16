/*
 * SCMULDListMapper.java Created on Nov 23, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class SCMULDListMapper implements Mapper<ULDVO> {
	
	private Log log = LogFactory.getLogger("ULD_DEFAULTS");
	
	   /**
	    * @param rs
	    * @return ULDListVO
	    * @throws SQLException
	    */
		public ULDVO map(ResultSet rs) throws SQLException{
			log.entering("INSIDE THE MAPPER","map(ResultSet rs)");
						
			ULDVO uldVO =new ULDVO();
			uldVO.setCompanyCode(rs.getString("CMPCOD")); 
			uldVO.setUldNumber(rs.getString("ULDNUM"));
			uldVO.setLocation(rs.getString("LOC"));
			uldVO.setOwnerStation(rs.getString("OWNARP"));
			uldVO.setCurrentStation(rs.getString("CURARP"));
			uldVO.setFacilityType(rs.getString("FACTYP"));
			uldVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
			uldVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION , 
					Location.NONE , rs.getTimestamp("LSTUPDTIM")));
			uldVO.setOperationalAirlineCode(rs.getString("OPRARLCOD"));
			log.log(Log.INFO, "THe ULDVO details are", uldVO);
			log.exiting("SCMULDListMapper","SCMULDListMapper");
			return uldVO;
			
		}
}
