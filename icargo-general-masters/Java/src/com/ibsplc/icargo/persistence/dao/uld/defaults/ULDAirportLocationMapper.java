/*
 * ULDAirportLocationMapper.java Created on Jul 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;


import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author A-2048
 *
 */

/**
 * This class implements a Mapper<<ULDAirportLocationVO>
 * @author A-2048
 *
 */
public class ULDAirportLocationMapper implements Mapper<ULDAirportLocationVO>{

    private Log log = LogFactory.getLogger("ULDAirportLocationMapper");
	/**
     *
     * @param rs
     * @return List<ULDDamageVO>
     * @throws SQLException
     */
	public ULDAirportLocationVO map(ResultSet rs) throws SQLException {

	   log.entering("ULDAirportLocationMapper","map");
	   ULDAirportLocationVO locationVO = new ULDAirportLocationVO();
	   locationVO.setCompanyCode(rs.getString("CMPCOD"));
	   locationVO.setAirportCode(rs.getString("ARPCOD"));
	   locationVO.setFacilityType(rs.getString("FACTYP"));
	   //added by a-3278 for QF1006 on 04Aug08 starts
	   locationVO.setContent(rs.getString("CNT"));
	   //a-3278 ends
	   locationVO.setSequenceNumber(rs.getString("SEQNUM"));
	   locationVO.setFacilityCode(rs.getString("FACCOD"));
	   locationVO.setDescription(rs.getString("FACDES"));
	   locationVO.setDefaultFlag(rs.getString("DEFFLG"));
	   locationVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
	   if(rs.getTimestamp("LSTUPDTIM") != null){
		   locationVO.setLastUpdatedTime(
				   new LocalDate(LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("LSTUPDTIM")));
	   }
	   return locationVO;
	 }
}
