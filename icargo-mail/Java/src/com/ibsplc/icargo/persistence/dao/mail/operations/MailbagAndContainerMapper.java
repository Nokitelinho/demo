/*
 * FlightsForClosureMapper.java Created on Aug 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;


import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * @author A-1885
 *
 */
public class MailbagAndContainerMapper implements Mapper<MailUploadVO> {
    
	/**
     * This class is used to map the ResultSet into the MailBagVo
     * @param rs
     * @return 
     * @throws SQLException
     */
	public MailUploadVO  map(ResultSet rs)
	 throws SQLException{
		MailUploadVO mailUploadVO = new MailUploadVO();
		
		mailUploadVO.setMailTag(rs.getString("MALIDR"));
		mailUploadVO.setContainerNumber(rs.getString("CONNUM"));
		mailUploadVO.setToContainer(rs.getString("ULDNUM"));
		mailUploadVO.setContainerType(rs.getString("CONTYP"));
		mailUploadVO.setContainerPol(rs.getString("ASGPRT"));		
		mailUploadVO.setContainerPOU(rs.getString("POU"));
		mailUploadVO.setDestination(rs.getString("DSTCOD"));
		if(MailUploadVO.FLAG_YES.equals(rs.getString("ARRSTA")))
			{
			mailUploadVO.setArrived(true);
			}
		if(MailUploadVO.FLAG_YES.equals(rs.getString("DLVFLG")))
			{
			mailUploadVO.setDeliverd(true);
			}
		if(MailUploadVO.FLAG_YES.equals(rs.getString("ACPFLG")))
			{
			mailUploadVO.setAccepted(true);
			}
		return mailUploadVO;
	}
	
	
}
