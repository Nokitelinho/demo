/*
 * MailTagIDMapper.java Created on June 22, 2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * @author 
 * This method is used to Map the ResultSet into PreAdviceDetailsVO
 * 
 */
public class MailTagIDMapper implements Mapper<MailbagVO>{
	 /*
	  *  (non-Javadoc)
	  * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	  */
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailbagVO map(ResultSet rs) throws SQLException{
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));		
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"));//added by a-7371
		mailbagVO.setWeight(wgt);
		return mailbagVO;
	}

}
