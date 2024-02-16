/*
 * MailDiscrepancyMapper.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailDiscrepancyVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1883
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		   Jan 19, 2007			  	 a-1883		Created
 */
public class MailDiscrepancyMapper implements Mapper<MailDiscrepancyVO>{

	private Log log = LogFactory.getLogger("MAIL TRACKING");
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailDiscrepancyVO map(ResultSet rs) throws SQLException {
		log.entering("MailDiscrepancyMapper","Map");
		MailDiscrepancyVO mailDiscrepancyVO = new MailDiscrepancyVO();
		mailDiscrepancyVO.setCompanyCode(rs.getString("CMPCOD"));
		mailDiscrepancyVO.setMailIdentifier(rs.getString("MALIDR"));
		mailDiscrepancyVO.setUldNumber(rs.getString("ULDNUM"));
		String acceptedStatus = rs.getString("ACPSTA");
		String arrivalStatus = rs.getString("ARRSTA");
		if(MailDiscrepancyVO.FLAG_YES.equals(acceptedStatus) && 
				MailDiscrepancyVO.FLAG_NO.equals(arrivalStatus)){
			mailDiscrepancyVO.setDiscrepancyType(MailConstantsVO.MAIL_DISCREPANCY_MISSING);
		}else if(MailDiscrepancyVO.FLAG_NO.equals(acceptedStatus) && 
				MailDiscrepancyVO.FLAG_YES.equals(arrivalStatus)){
			mailDiscrepancyVO.setDiscrepancyType(MailConstantsVO.MAIL_DISCREPANCY_FOUND);
		}
		return mailDiscrepancyVO;
	}

}
