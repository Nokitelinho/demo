/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MailFromCarditMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Nov 30, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MailFromCarditMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Nov 30, 2018	:	Draft
 */
public class MailFromCarditMapper implements Mapper<DocumentBillingDetailsVO>{
	
	private static final String CLASS_NAME = "MailFromCarditMapper";
	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	@Override
	public DocumentBillingDetailsVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME,"Mapper");
		DocumentBillingDetailsVO billingDetailsVO = new DocumentBillingDetailsVO();
		billingDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		billingDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		billingDetailsVO.setConsignmentSeqNumber(rs.getString("CSGSEQNUM"));
		billingDetailsVO.setPoaCode(rs.getString("POACOD"));
		billingDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		
		return billingDetailsVO;
	}

}
