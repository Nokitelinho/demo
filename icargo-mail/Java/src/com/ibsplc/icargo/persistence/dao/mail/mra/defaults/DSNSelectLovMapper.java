/*
 * DSNSelectLovMapper.java Created on JUl 10, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */


public class DSNSelectLovMapper implements Mapper<DSNPopUpVO> {
	private static final String CLASS_NAME = "DSNSelectLovMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 * @param rs
	 * @return DespatchLovVO
	 * @throws SQLException
	 */

	public DSNPopUpVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME,"map");
		DSNPopUpVO despatchLovVO=new DSNPopUpVO();
		despatchLovVO.setCompanyCode(rs.getString("CMPCOD"));
		despatchLovVO.setBlgBasis(rs.getString("MALIDR"));
		despatchLovVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));//added by A-7371 for ICRD-234334
		despatchLovVO.setCsgdocnum(rs.getString("CSGDOCNUM"));
		despatchLovVO.setCsgseqnum(rs.getInt("CSGSEQNUM"));
		//Changed for ICRD-36416 by Ramachandran S
    	despatchLovVO.setGpaCode(rs.getString("POACOD"));
		despatchLovVO.setDsn(rs.getString("DSN"));
		if(rs.getDate("RCVDAT")!= null){
			LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,
					Location.NONE,rs.getDate("RCVDAT"));
			String date=fromDate.toDisplayDateOnlyFormat();
			despatchLovVO.setDsnDate(date);
		}

		despatchLovVO.setBillingDetailsCount(rs.getInt("DTLCOUNT"));
		//Added for Bug ICRD-21653
		despatchLovVO.setPoaCode(rs.getString("POACOD"));
		//Added for ICRD-101113
		despatchLovVO.setReceptacleSerialNo(rs.getString("RSN"));
		despatchLovVO.setHni(rs.getString("HSN"));
		despatchLovVO.setRegInd(rs.getString("REGIND"));
		//aDDED BY a-8331
		despatchLovVO.setBillingStatus(rs.getString("MRASTA"));
		//Added by A_7794 as part of ICRD-232299
		despatchLovVO.setMailSource(rs.getString("MALSRC"));
		despatchLovVO.setTransferAirline(rs.getString("TRFCARCOD"));
		despatchLovVO.setTransferPA(rs.getString("TRFPOACOD"));
		
		
		
		return despatchLovVO;
	}

}
