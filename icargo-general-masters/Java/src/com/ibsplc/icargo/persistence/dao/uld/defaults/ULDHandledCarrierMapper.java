/*
 * ULDHandledCarrierMapper.java Created on Dec 05, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHandledCarrierVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-2883
 *
 */


public class ULDHandledCarrierMapper   implements Mapper<ULDHandledCarrierVO>{
	
	public ULDHandledCarrierVO map(ResultSet rs) throws SQLException {
		ULDHandledCarrierVO uldHandledCarrierVO =new ULDHandledCarrierVO();
		
		uldHandledCarrierVO.setCompanyCode(rs.getString("CMPCOD"));
		uldHandledCarrierVO.setAirlineCode(rs.getString("ARLCOD"));
		uldHandledCarrierVO.setStationCode(rs.getString("STNCOD"));
		uldHandledCarrierVO.setAirlineName(rs.getString("ARLNAM"));
	
		return uldHandledCarrierVO;
	}

}
