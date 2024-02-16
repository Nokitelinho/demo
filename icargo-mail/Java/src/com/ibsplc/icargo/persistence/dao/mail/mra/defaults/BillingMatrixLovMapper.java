/*
 * BillingMatrixLovMapper.java Created on Mar 6, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixLovVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */


public class BillingMatrixLovMapper implements Mapper<BillingMatrixLovVO> {
	private static final String CLASS_NAME = "BillingMatrixLovMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
     * @author A-2398
     * @param rs
     * @return BillingMatrixLovVO
     * @throws SQLException
     */
    
    public BillingMatrixLovVO map(ResultSet rs) throws SQLException {
    	log.entering(CLASS_NAME,"map");
    	BillingMatrixLovVO billingMatrixLovVO=new BillingMatrixLovVO();
    	billingMatrixLovVO.setCompanyCode(rs.getString("CMPCOD"));
    	billingMatrixLovVO.setBillingMatrixCode(rs.getString("BLGMTXCOD"));
    	billingMatrixLovVO.setBillingMatrixDescription(rs.getString("BLGMTXDES"));

        return billingMatrixLovVO;
    }

}
