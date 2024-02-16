/**
 * MCALovMapper.java created on May 25, 2012
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author a-4823
 *
 */
public class MCALovMapper implements Mapper<CCAdetailsVO> {

	/* (non-Javadoc)
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	@Override
	public CCAdetailsVO map(ResultSet rs) throws SQLException {
		CCAdetailsVO ccAdetailsVO=new CCAdetailsVO();
		ccAdetailsVO.setCcaRefNumber(rs.getString("CCANUM"));
		ccAdetailsVO.setDsnNo(rs.getString("DSN"));
		ccAdetailsVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
		return ccAdetailsVO;
	}

}
