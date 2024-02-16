/*
 * InvoiceLOVMapper.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;import java.sql.SQLException;import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceLovVO;import com.ibsplc.icargo.framework.util.time.LocalDate;import com.ibsplc.icargo.framework.util.time.Location;import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;import com.ibsplc.xibase.util.log.Log;import com.ibsplc.xibase.util.log.factory.LogFactory;
/** *@author A-2407 * 
 * Mapper for getting  Invoices */
public class InvoiceLOVMapper implements Mapper<AirlineInvoiceLovVO> {
	private Log log = LogFactory.getLogger("AirlineExceptionsMapper");		
	/**	 * @param rs	 * @throws SQLException	 */
	public AirlineInvoiceLovVO map(ResultSet rs) throws SQLException {		
		log.entering("InvoiceLOVMapper---------", "Map Method");				AirlineInvoiceLovVO invoiceLovVO 	= new AirlineInvoiceLovVO();				invoiceLovVO.setInvoiceNumber( rs.getString("INVNUM" ));		invoiceLovVO.setInterlineBillingType( rs.getString("INTBLGTYP" ));		invoiceLovVO.setClearancePeriod( rs.getString("CLRPRD" ));		invoiceLovVO.setAirlineCode( rs.getString("ARLCOD" ));		invoiceLovVO.setInvoiceDate((new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("BLDDAT"))).toDisplayDateOnlyFormat());								log.exiting("InvoiceLOVMapper", "Map Method");				return invoiceLovVO;
	}
}
