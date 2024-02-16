/*
 * PopulateLocationMapper.java Created on Jan 5, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;

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
 * This class implements a Mapper<<ULDVO>
 * @author A-2048
 *
 */
public class PopulateLocationMapper implements Mapper<ULDVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     */
	public ULDVO map(ResultSet rs) throws SQLException {
		log.entering("ULDValidationMapper","map");
		ULDVO uldVO = new ULDVO();
		uldVO.setLocation(rs.getString("FACCOD"));
		uldVO.setLocationType(rs.getString("FACTYP"));
		
		log.exiting("ULDValidationMapper","map");
		return uldVO;

	}

}
