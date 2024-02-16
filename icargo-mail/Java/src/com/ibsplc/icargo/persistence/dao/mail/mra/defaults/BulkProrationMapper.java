/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.defaults.BulkProrationMapper.java
 *
 *	Created by	:	A-7531
 *	Created on	:	09-Nov-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;


/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.defaults.BulkProrationMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	09-Nov-2017	:	Draft
 */
public class BulkProrationMapper implements Mapper<DocumentBillingDetailsVO> {


	
	
	
	public DocumentBillingDetailsVO map(ResultSet rs) throws SQLException {
			
		DocumentBillingDetailsVO documentDetailsVO =new DocumentBillingDetailsVO();
		documentDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		documentDetailsVO.setBillingBasis(rs.getString("BLGBAS"));
		documentDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		documentDetailsVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
		documentDetailsVO.setCsgSequenceNumber(rs.getInt("CSGSEQNUM"));
		documentDetailsVO.setPoaCode(rs.getString("POACOD"));
		documentDetailsVO.setProrateException(rs.getString("EXPCOD"));
		
		
		return documentDetailsVO;
	}

}
