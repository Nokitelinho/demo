/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.PrintMailTagMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Aug 4, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.PrintMailTagMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Aug 4, 2016	:	Draft
 */


public class PrintMailTagMapper implements Mapper<MailbagVO>{
	
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: A-4809 on Aug 4, 2016
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public MailbagVO map(ResultSet rs) throws SQLException {
		log.entering("PrintMailTagMapper", "Resultset");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setCarrierCode(rs.getString("TWOAPHCOD"));
		mailbagVO.setOrgCityDesc(rs.getString("ORGCTYNAM"));
		mailbagVO.setDestCityDesc(rs.getString("DESTCTYNAM"));
		mailbagVO.setDestCountryDesc(rs.getString("CNTNAM"));
		mailbagVO.setSubClassDesc(rs.getString("DES"));
		return mailbagVO;
	}

}
