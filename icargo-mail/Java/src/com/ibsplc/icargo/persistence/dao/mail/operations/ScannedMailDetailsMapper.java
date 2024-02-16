
/*
 * FlightsForClosureMapper.java Created on Aug 19, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * @author A-5526
 *
 */
public class ScannedMailDetailsMapper implements Mapper<MailScanDetailVO> {
    
	/**
     * This class is used to map the ResultSet into the MailScanDetailVO
     * @param rs
     * @return 
     * @throws SQLException
     */
	public MailScanDetailVO  map(ResultSet rs)
	 throws SQLException{
		MailScanDetailVO mailScanDetailVO = new MailScanDetailVO();
		
		mailScanDetailVO.setCompanyCode(rs.getString("CMPCOD"));
		mailScanDetailVO.setAirport(rs.getString("SCNPRT"));         
		mailScanDetailVO.setScanData(rs.getString("SCNSTR"));
		mailScanDetailVO.setMailBagId(rs.getString("MALIDR"));
		mailScanDetailVO.setSerialNumber(rs.getInt("SERNUM"));
		return mailScanDetailVO;
	}
	
	
}
