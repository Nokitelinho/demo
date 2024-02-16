/*
 * DamagedMailBagMapper.java Created on July 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

//import com.ibsplc.xibase.util.log.Log;
//import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2037
 * 
 */
public class DamagedMailBagMapper implements Mapper<DamagedMailbagVO> {

	// private Log log= LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public DamagedMailbagVO map(ResultSet rs) throws SQLException {
		String airportCode = rs.getString("ARPCOD");
		DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
		damagedMailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		damagedMailbagVO.setAirportCode(rs.getString("ARPCOD"));
		damagedMailbagVO.setDamageCode(rs.getString("DMGCOD"));
		if (rs.getTimestamp("DMGDAT") != null) {
			damagedMailbagVO.setDamageDate(new LocalDate(airportCode,
					Location.ARP, rs.getTimestamp("DMGDAT")));
		}
		damagedMailbagVO.setDamageDescription(rs.getString("DMGDES"));
		damagedMailbagVO
				.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		damagedMailbagVO.setDsn(rs.getString("DSN"));
		damagedMailbagVO.setMailbagId(rs.getString("MALIDR"));
		damagedMailbagVO.setMailClass(rs.getString("MALCLS"));
		damagedMailbagVO.setOperationType(rs.getString("OPRTYP"));
		damagedMailbagVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		damagedMailbagVO.setRemarks(rs.getString("RMK"));
		damagedMailbagVO.setUserCode(rs.getString("USRCOD"));
		damagedMailbagVO.setYear(rs.getInt("YER"));
		damagedMailbagVO.setReturnedFlag(rs.getString("RTNFLG"));
		damagedMailbagVO.setPaCode(rs.getString("POACOD"));
		return damagedMailbagVO;
	}

}
