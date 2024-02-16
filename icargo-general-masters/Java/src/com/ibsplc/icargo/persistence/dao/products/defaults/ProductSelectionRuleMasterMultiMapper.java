/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.products.defaults.ProductSelectionRuleMasterMultiMapper.java
 *
 *	Created by	:	Prashant Behera
 *	Created on	:	Jun 29, 2022
 *
 *  Copyright 2022 Copyright  IBS Software  (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright  IBS Software  (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.products.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.products.defaults.ProductSelectionRuleMasterMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Prashant Behera	:	Jun 29, 2022	:	Draft
 */
public class ProductSelectionRuleMasterMultiMapper implements MultiMapper<ProductSelectionRuleMasterVO>{
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 *	Added by 			: 	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param rs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException
	 */
	@Override
	public List<ProductSelectionRuleMasterVO> map(ResultSet rs) throws SQLException {
		List<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs = new ArrayList<>();
		while(rs.next()) {
			ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
			productSelectionRuleMasterVO.setSerialNumber(rs.getInt("SERNUM"));
			productSelectionRuleMasterVO.setCompanyCode(rs.getString("CMPCOD"));
			productSelectionRuleMasterVO.setCommodityCode(rs.getString("COMCOD"));
			productSelectionRuleMasterVO.setSourceCode(rs.getString("SRCCOD"));
			productSelectionRuleMasterVO.setSccCode(rs.getString("SCCCOD"));
			productSelectionRuleMasterVO.setSccGroupCode(rs.getString("SCCGRPCOD"));
			productSelectionRuleMasterVO.setAgentCode(rs.getString("AGTCOD"));
			productSelectionRuleMasterVO.setAgentGroupCode(rs.getString("AGTGRPCOD"));
			productSelectionRuleMasterVO.setInternationalDomesticFlag(rs.getString("INTDOMFLG"));
			productSelectionRuleMasterVO.setOriginCountryCode(rs.getString("ORGCNTCOD"));
			productSelectionRuleMasterVO.setDestinationCountryCode(rs.getString("DSTCNTCOD"));
			productSelectionRuleMasterVO.setProductCode(rs.getString("PRDCOD"));
			productSelectionRuleMasterVOs.add(productSelectionRuleMasterVO);
		}
		return productSelectionRuleMasterVOs;
	}

}
