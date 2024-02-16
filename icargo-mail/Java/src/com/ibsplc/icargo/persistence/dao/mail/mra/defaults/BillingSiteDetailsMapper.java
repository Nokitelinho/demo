/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.BillingSiteDetailsMapper.java
 *
 *	Created by	:	A-5219
 *	Created on	:	19-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteLOVVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 *
 * @author A-5219
 *
 */

public class BillingSiteDetailsMapper implements Mapper<BillingSiteLOVVO> {

    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
     */
	 public BillingSiteLOVVO map(ResultSet rs) throws SQLException {

	        Log log =LogFactory.getLogger("MAILTRACKING_MRA_BILLINGSITE");
	        BillingSiteLOVVO billingLOVVo = new BillingSiteLOVVO();

	        //billingSiteVo.setBillingsite(rs.getString("CMPCOD"));
	        billingLOVVo.setBillingSiteCode(rs.getString("BLGSITCOD"));
	        billingLOVVo.setBillingSiteDescription(rs.getString("BLGSITNAM"));


	        log.log(Log.INFO," BillingSiteDetailsMapper in BillingSiteMapper "+billingLOVVo);
	        log.exiting("BillingSiteDetailsMapper","MAP METHOD");
	        return billingLOVVo;
	}


}
