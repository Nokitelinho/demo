/*
 * CarditDetailsMapper.java Created on Sep 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Sep 11, 2006			a-1739		Created
 */
public class CarditDetailsMapper implements Mapper<CarditVO> {

	/**
	 * maps the resultset row to a VO
	 * Sep 11, 2006, a-1739
	 * @param rs
	 * @return carditVO
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public CarditVO map(ResultSet rs) throws SQLException {
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId(rs.getString("SDRIDR"));
		carditVO.setRecipientId(rs.getString("RCTIDR"));
		carditVO.setTstIndicator(rs.getInt("TSTIND"));
		carditVO.setCarditKey(rs.getString("CDTKEY"));
		return carditVO;
	}

}
