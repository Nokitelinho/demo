/*
 * SLALovMapper.java Created on Apr 02 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLADetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-2521
 *
 */
public class SLALovMapper implements Mapper<SLADetailsVO> {
	
	private static final String CLASS_NAME = "SLALovMapper";

	
	/**
     * @param rs
     * @return contractDetailsVO
     * @throws SQLException
     */
    public SLADetailsVO map(ResultSet rs) throws SQLException {
    	
    	SLADetailsVO slaDetailsVO=new SLADetailsVO();
    	
    	slaDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
    	slaDetailsVO.setSlaID(rs.getString("SLAIDR"));
    	slaDetailsVO.setDescription(rs.getString("SLADSC"));
    	slaDetailsVO.setCurrency(rs.getString("CURCOD"));
    	
        return slaDetailsVO;
    }

}
