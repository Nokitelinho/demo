/*
 * CarditDetailsForAllMailBagsMapper.java Created on Feb  06, 2006
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1936 
 * This class is used to map the Resultset into MailBagvo
 */
public class CarditDetailsForAllMailBagsMapper implements Mapper<MailbagVO> {

	private Log log=LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	/**
	 * @author A-1936
	 * @param rs
	 * @throws SQLException
	 */
	 public MailbagVO map(ResultSet rs) throws SQLException { 
		log.entering("CarditDetailsForAllMailBagsMapper","Map Method");
		  MailbagVO mailBagVo = new MailbagVO();
		  mailBagVo.setCompanyCode(rs.getString("CMPCOD"));
		  mailBagVo.setCarditKey(rs.getString("CDTKEY"));
		  mailBagVo.setCarditSequenceNumber(rs.getInt("CDTSEQNUM"));
		  mailBagVo.setStationCode(rs.getString("STNCOD"));
		  mailBagVo.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		  return mailBagVo;
	 }

}
