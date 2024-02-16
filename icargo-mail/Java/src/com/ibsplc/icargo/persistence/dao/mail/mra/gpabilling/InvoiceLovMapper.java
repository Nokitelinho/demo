/*
 * InvoiceLovMapper.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceLovVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
//TODO Mapper<InvoiceLovVO>
public class InvoiceLovMapper implements Mapper<InvoiceLovVO> {
	private static final String CLASS_NAME = "InvoiceLovMapper";

	private Log log = LogFactory.getLogger("MRA:GPABILLING");


	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
    public InvoiceLovVO map(ResultSet rs) throws SQLException {

    	log.entering(CLASS_NAME,"map");
    	InvoiceLovVO invoiceLovVo=new InvoiceLovVO();

    	invoiceLovVo.setInvoiceNumber(rs.getString("INVNUM"));

    	invoiceLovVo.setGpaCode(rs.getString("GPACOD"));
    	invoiceLovVo.setBillingPeriod(rs.getString("BLDPRD"));
    	invoiceLovVo.setInvoiceStatus(rs.getString("INVSTA"));
        return invoiceLovVo;
    }

}