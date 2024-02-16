/*
 * RateCardLovMapper.java Created on Feb 1, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardLovVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */

//TODO Mapper<RateCardLovVO>
public class RateCardLovMapper implements Mapper<RateCardLovVO> {
	private static final String CLASS_NAME = "RateCardLovMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");


	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
    public RateCardLovVO map(ResultSet rs) throws SQLException {
    	log.entering(CLASS_NAME,"map");
    	RateCardLovVO rateCardLovVO=new RateCardLovVO();
    	rateCardLovVO.setCompanyCode(rs.getString("CMPCOD"));
    	rateCardLovVO.setRateCardID(rs.getString("RATCRDCOD"));
    	rateCardLovVO.setDescription(rs.getString("RATCRDDES"));

        return rateCardLovVO;
    }

}
