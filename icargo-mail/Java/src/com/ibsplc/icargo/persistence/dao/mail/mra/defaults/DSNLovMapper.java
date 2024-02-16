/**
 * DSNLovMapper.java created on May 25, 2012
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
public class DSNLovMapper  implements Mapper<CCAdetailsVO>{

	/**
	 * @param  rs
	 * @return ccAdetailsVO
	 */
	public CCAdetailsVO map(ResultSet rs) throws SQLException {
		CCAdetailsVO ccAdetailsVO=new CCAdetailsVO();
		ccAdetailsVO.setCcaRefNumber(rs.getString("CCAREFNUM"));
		ccAdetailsVO.setDsnNo(rs.getString("DSN"));
		ccAdetailsVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
		ccAdetailsVO.setBillingBasis(rs.getString("MALIDR"));//modified by A-7371 as part ICRD-249058
		ccAdetailsVO.setOrigin(rs.getString("ORGEXGOFC"));
		ccAdetailsVO.setDestination(rs.getString("DSTEXGOFC"));
		ccAdetailsVO.setCategory(rs.getString("MALCTGCOD"));
		ccAdetailsVO.setSubClass(rs.getString("MALSUBCLS"));
		ccAdetailsVO.setYear(rs.getString("YER"));
		ccAdetailsVO.setRsn(rs.getString("RSN"));
		ccAdetailsVO.setHni(rs.getString("HSN"));
		ccAdetailsVO.setRegind(rs.getString("REGIND"));
		return ccAdetailsVO;
	}

}
