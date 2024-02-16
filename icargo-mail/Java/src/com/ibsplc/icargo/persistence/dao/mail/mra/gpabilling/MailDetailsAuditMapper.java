/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MailDetailsAuditMapper.java
 *
 *	Created by	:	a-4809
 *	Created on	:	Apr 2, 2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MailDetailsAuditMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-4809	:	Apr 2, 2014	:	Draft
 */
public class MailDetailsAuditMapper implements Mapper<MailDetailVO>{
	
	private Log log = LogFactory.getLogger("MRA GPABILLING");

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: a-4809 on Apr 2, 2014
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public MailDetailVO map(ResultSet rs) throws SQLException {
		log.entering("MailDetailsAuditMapper", "map");
		MailDetailVO mailDetailVO = new MailDetailVO();
		mailDetailVO.setCompanyCode(rs.getString("CMPCOD"));
		mailDetailVO.setDsn(rs.getString("DSN"));
		mailDetailVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		mailDetailVO.setDestinationOfficeOfExchange(rs.getString("DSTEXGOFC"));
		mailDetailVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailDetailVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailDetailVO.setMailId(rs.getString("BLGBAS"));
		mailDetailVO.setYear(rs.getInt("YER"));
		if(rs.getTimestamp("BLDDAT")!=null){
		mailDetailVO.setRcvDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("BLDDAT")));
		}
		log.exiting("MailDetailsAuditMapper", "map");
		return mailDetailVO;
	}

}
