/*
 * InvoicKeyLovMapper.java Created on Aug 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.InvoicKeyLovVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2408
 *
 */
public class InvoicKeyLovMapper implements Mapper<InvoicKeyLovVO> {
	private static final String CLASS_NAME = "InvoicKeyLovMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");


	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
    public InvoicKeyLovVO map(ResultSet rs) throws SQLException {
    	log.entering(CLASS_NAME,"map");
    	InvoicKeyLovVO invoicKeyLovVO=new InvoicKeyLovVO();
    	invoicKeyLovVO.setCompanyCode(rs.getString("CMPCOD"));
    	invoicKeyLovVO.setInvoicKey(rs.getString("INVKEY"));
    	invoicKeyLovVO.setPoaCode(rs.getString("POACOD"));

        return invoicKeyLovVO;
    }

}