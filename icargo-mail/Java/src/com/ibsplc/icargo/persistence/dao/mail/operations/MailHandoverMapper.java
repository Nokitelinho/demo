/*
 * MailHandoverMapper.java Created on Jul 02, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailHandoverVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-6986
 * 
 */
public class MailHandoverMapper implements Mapper<MailHandoverVO>{
	private Log log = LogFactory.getLogger("MAIL");

	
	private static final String CLASS_NAME = "MailHandoverMapper";
	
	public MailHandoverVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		
		MailHandoverVO mailHandoverVO = new MailHandoverVO();
		
		mailHandoverVO.setCompanyCode(rs.getString("CMPCOD"));
		mailHandoverVO.setHoAirportCodes(rs.getString("ARPCOD"));
		mailHandoverVO.setGpaCode(rs.getString("GPACOD"));
		if(rs.getString("MALCLS")!=null){
			mailHandoverVO.setMailClass(rs.getString("MALCLS"));
		}else{
			mailHandoverVO.setMailClass("");
		}		
		mailHandoverVO.setExchangeOffice(rs.getString("EXGOFC"));
		mailHandoverVO.setHandoverTimes(rs.getString("HNDTIM"));
		mailHandoverVO.setMailSubClass(rs.getString("MALSUBCLS"));
		mailHandoverVO.setSerialNumber(rs.getInt("SERNUM"));
		
		return mailHandoverVO;
	}

}
