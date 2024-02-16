/*
 * ListULDDamagechecklistMasterMapper.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;


import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class ListULDDamagechecklistMasterMapper implements Mapper<ULDDamageChecklistVO>{
	private Log log = LogFactory.getLogger("ULD_DEFAULTS");
    /**
     * Method for getting the map
     * @param rs
     * @return ULDDamageChecklistVO
     * @throws SQLException
     */
    public ULDDamageChecklistVO map(ResultSet rs) throws SQLException{
    	log.entering("ListULDDamagechecklistMasterMapper","map");
    	ULDDamageChecklistVO uldDamageChecklistVO = new ULDDamageChecklistVO();
    	uldDamageChecklistVO.setCompanyCode(rs.getString("CMPCOD"));
    	uldDamageChecklistVO.setNoOfPoints(rs.getInt("PNT"));
    	uldDamageChecklistVO.setDescription(rs.getString("DESCRPTIN"));
    	uldDamageChecklistVO.setSection(rs.getString("SEC"));
    	uldDamageChecklistVO.setLastUpdatedTime(
    			new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
    	
    	uldDamageChecklistVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
    	
    	uldDamageChecklistVO.setSequenceNumber(rs.getString("SEQNUM"));
    	
    	return uldDamageChecklistVO;
    }

}
