/*
 * MailbagDetailsForReportMapper.java Created on Feb 8, 2007	
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailDetailVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1883
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		   Feb 8, 2007			  	 A-1883		Created
 */
public class MailbagDetailsForReportMapper implements Mapper<MailDetailVO>{

	private Log log = LogFactory.getLogger("MAIL TRACKING");
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailDetailVO map(ResultSet rs) throws SQLException {
		log.entering("MailbagDetailsForReportMapper","Map");
		MailDetailVO mailDetailVO = new MailDetailVO();
		mailDetailVO.setControlDocumentNumber(rs.getString("CNTDOCNUM"));
		mailDetailVO.setMailId(rs.getString("MALIDR"));
		mailDetailVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailDetailVO.setMailClass(rs.getString("MALCLS"));
		mailDetailVO.setMailSubclass(rs.getString("MALSUBCLS"));
		//mailDetailVO.setWeight(rs.getDouble("WGT"));
		mailDetailVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
		mailDetailVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		mailDetailVO.setDestinationOfficeOfExchange(rs.getString("DSTEXGOFC"));
		mailDetailVO.setOriginCity(rs.getString("ORGCTY"));
		mailDetailVO.setDestinationCity(rs.getString("DSTCTY"));
		mailDetailVO.setOriginAirport(rs.getString("ORGARPCOD"));
		mailDetailVO.setDestinationAirport(rs.getString("DSTARPCOD"));
		mailDetailVO.setPou(rs.getString("POU"));
		mailDetailVO.setUldNumber(rs.getString("ULDNUM"));
		mailDetailVO.setDestnAirportName(rs.getString("DSTARPNAM")); 
		/*
		 * Added BY Karthick V as the part of the NCA Mail Tracking CR 
		 * 
		 */
		mailDetailVO.setDsn(rs.getString("DSN"));
		mailDetailVO.setCompanyCode(rs.getString("CMPCOD"));
		return mailDetailVO;
	}


}
