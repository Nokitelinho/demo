/*
 * ULDValidationMapper.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author A-1347
 *
 */

/**
 * This class implements a Mapper<<ULDVO>
 * @author A-1347
 *
 */
public class ULDValidationMapper implements Mapper<ULDValidationVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     */
	public ULDValidationVO map(ResultSet rs) throws SQLException {
		log.entering("ULDValidationMapper","map");
		ULDValidationVO uldValidationVO = new ULDValidationVO();
		uldValidationVO.setCompanyCode(rs.getString("CMPCOD"));
		uldValidationVO.setOwnerAirlineCode(rs.getString("OWNCOD"));
		uldValidationVO.setOwnerAirlineIdentifier(rs.getInt("OWNARLIDR"));
		uldValidationVO.setOwnerStation(rs.getString("OWNARP"));
		uldValidationVO.setCurrentStation(rs.getString("CURARP"));
		uldValidationVO.setOverallStatus(rs.getString("OVLSTA"));
		uldValidationVO.setDamageStatus(rs.getString("DMGSTA"));
		uldValidationVO.setUldNature(rs.getString("ULDNAT"));
		uldValidationVO.setLocation(rs.getString("LOC"));
		log.exiting("ULDValidationMapper","map");
		return uldValidationVO;

	}

}
