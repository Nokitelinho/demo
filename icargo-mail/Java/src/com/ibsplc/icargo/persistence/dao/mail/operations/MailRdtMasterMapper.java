/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.MailRdtMasterMapper.java
 *
 *	Created by	:	A-6991
 *	Created on	:	18-Jul-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;


import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.MailRdtMasterMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6991	:	18-Jul-2018	:	Draft
 */
public class MailRdtMasterMapper implements Mapper<MailRdtMasterVO>{

	private Log log = LogFactory.getLogger("MailRdtMasterMapper");
	
	public MailRdtMasterVO map(ResultSet rs) throws SQLException {
		   
		   MailRdtMasterVO mailRdtMasterVO=new MailRdtMasterVO();
		   mailRdtMasterVO.setCompanyCode(rs.getString("CMPCOD"));
		   mailRdtMasterVO.setGpaCode(rs.getString("GPACOD"));
		   mailRdtMasterVO.setAirportCodes(rs.getString("DSTARPCOD"));
		   mailRdtMasterVO.setOriginAirportCodes(rs.getString("ORGARPCOD"));
		   mailRdtMasterVO.setSeqnum(rs.getLong("SERNUM"));
		   mailRdtMasterVO.setMailClass(rs.getString("MALCLS"));
		   mailRdtMasterVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
		   mailRdtMasterVO.setMailType(rs.getString("MALTYP"));
		   mailRdtMasterVO.setRdtDay(rs.getInt("RDTDAY"));
		   mailRdtMasterVO.setRdtOffset(rs.getInt("RDTOFT"));
		   mailRdtMasterVO.setRdtRule(rs.getString("RDTRUL"));
		   mailRdtMasterVO.setRdtCfgType(rs.getString("RDTCFGTYP"));
		   
		   log.log(Log.FINE, "\n\n mailRdtMasterVO listed !! ----------> ", mailRdtMasterVO.getAirportCodes());
		   return mailRdtMasterVO;
		   
	   }
	
}
