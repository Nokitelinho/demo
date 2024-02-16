/*
 * GPAReportingInvoicDetailsMapper.java created on Nov 21, 2018
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;

import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-8464
 *
 */
public class GPAReportingInvoicMapper implements Mapper<InvoicVO>{
	
	
	private Log log = LogFactory.getLogger("MRA:GPAREPORTING");
	
	/**
	 * maps the resultset row to a VO
	 * @param rs
	 * @return invoicDetailsVO
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public InvoicVO map(ResultSet rs) throws SQLException {
		InvoicVO invoicVO = new InvoicVO();
		//From result of list query to the details needed for displaying in screen invoic Enquiry

		Double netAmtInCtr=	rs.getDouble("TOTINVAMT");
		invoicVO.setTotalamount(netAmtInCtr);
		invoicVO.setInvoicRefId(rs.getString("INVREFNUM"));
		invoicVO.setRemarks(rs.getString("RMK"));
		invoicVO.setPoaCode(rs.getString("POACOD"));
		invoicVO.setCompanyCode(rs.getString("CMPCOD"));
		invoicVO.setSeqNumber(rs.getLong("SERNUM"));
		invoicVO.setLastupdatedUser(rs.getString("LSTUPDUSR"));
		invoicVO.setPayType(rs.getString("PAYTYP"));
		invoicVO.setFileName(rs.getString("FILNAM"));
		if(rs.getDate("INVDAT") != null){
			invoicVO.setInvoiceDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("INVDAT")));
		}
		invoicVO.setInvoicStatusCode(rs.getString("INVSTA"));
		if(rs.getDate("RPTPRDFRM") != null){
			invoicVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("RPTPRDFRM")));
		}
		if(rs.getDate("RPTPRDTOO") != null){
			invoicVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("RPTPRDTOO")));
		}
		invoicVO.setSerNums(rs.getString("SERNUMS"));
		invoicVO.setNumOfMailbags(rs.getLong("NUMMALBAG"));
		return invoicVO;
	}

}
