/*
 * ConsignmentDetailsMapper.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-2037
 *
 */
public class ConsignmentDetailsMapper implements Mapper<MailInConsignmentVO>{
	 /**
	  * @author A-2037
	  * @param rs
	  * @return
	  * @throws SQLException
	  */
	public MailInConsignmentVO map(ResultSet rs) throws SQLException{
		MailInConsignmentVO mailInConsignmentVO=
			new MailInConsignmentVO();
		
		mailInConsignmentVO.setCompanyCode(rs.getString("CMPCOD"));
		mailInConsignmentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailInConsignmentVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		mailInConsignmentVO.setPaCode(rs.getString("POACOD"));
		mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
		mailInConsignmentVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		return mailInConsignmentVO;
	}

}
