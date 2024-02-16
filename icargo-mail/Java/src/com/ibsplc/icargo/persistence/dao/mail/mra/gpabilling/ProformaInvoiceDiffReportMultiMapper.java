/*
 * ProformaInvoiceDiffReportMultiMapper.java Created on Aug 11, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.ProformaInvoiceDiffReportVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3271
 *
 */
public class ProformaInvoiceDiffReportMultiMapper implements MultiMapper<ProformaInvoiceDiffReportVO>  {
	
	private static final String CLASS_NAME = "Proforma-InvoiceDiffReportMultiMapper";
	
	private Log log = LogFactory.getLogger("MRA:GPABILLING");
	/**
	 * @param resultSet
	 * @return cn51SummaryVO
	 * @throws SQLException
	 * @throws SystemException
	 */
	public List<ProformaInvoiceDiffReportVO> map(ResultSet resultSet) throws SQLException{
		log.entering(CLASS_NAME,"map");
		try{
			List<ProformaInvoiceDiffReportVO> proformaInvoiceDiffReportVOs = new ArrayList<ProformaInvoiceDiffReportVO>();
			
			ProformaInvoiceDiffReportVO proformaInvoiceDiffReportVO = null;
			CN51SummaryVO cn51SummaryVO = null;
			CN66DetailsVO cn66DetailsVO = null;
			
			while (resultSet.next()) {
				
				proformaInvoiceDiffReportVO = new ProformaInvoiceDiffReportVO();
				cn51SummaryVO = new CN51SummaryVO();
				cn66DetailsVO = new CN66DetailsVO();
				
				String baseCurrency = resultSet.getString("BLGCURCOD");
				
				proformaInvoiceDiffReportVO.setInvoiceNumber(resultSet.getString("INVNUM"));
				proformaInvoiceDiffReportVO.setInvoiceStatus(resultSet.getString("INVSTA"));
				if(resultSet.getDate("BLGPRDFRM") != null ) {
					cn51SummaryVO.setFromDate(new LocalDate(LocalDate.NO_STATION,
							Location.NONE,resultSet.getDate("BLGPRDFRM")));
				}
				if(resultSet.getDate("BLGPRDTOO") != null ) {
					cn51SummaryVO.setToDate(new LocalDate(LocalDate.NO_STATION,
							Location.NONE,resultSet.getDate("BLGPRDTOO")));
				}
				
				Money amtInBillCurr = CurrencyHelper.getMoney(baseCurrency);
				amtInBillCurr.setAmount(resultSet.getDouble("TOTAMTBLGCUR"));
				cn51SummaryVO.setTotalAmountInBillingCurrency(amtInBillCurr);
				cn51SummaryVO.setCcaReference(resultSet.getString("CCAREFNUM"));
				cn51SummaryVO.setDueAirline(resultSet.getString("DUEARL"));
				
				Collection<CN51SummaryVO> cn51SummaryVOs = new ArrayList<CN51SummaryVO>();
				Collection<CN66DetailsVO> cn66DetailsVOs = new ArrayList<CN66DetailsVO>();
				
				cn66DetailsVO.setBillingStatus(resultSet.getString("BLGSTA"));
				cn66DetailsVO.setRemarks(resultSet.getString("RMK"));
				cn66DetailsVO.setTotalAmount(resultSet.getDouble("BLDAMT"));
				cn66DetailsVO.setTotalWeight(resultSet.getDouble("TOTWGT"));
				cn66DetailsVO.setDsn(resultSet.getString("DSN"));
				
				cn66DetailsVOs.add(cn66DetailsVO);
				cn51SummaryVO.setCn66details(cn66DetailsVOs);
				cn51SummaryVOs.add(cn51SummaryVO);
				proformaInvoiceDiffReportVO.setCn51SummaryVOs(cn51SummaryVOs);
				
				proformaInvoiceDiffReportVOs.add(proformaInvoiceDiffReportVO);
				log.log(Log.INFO,
						"proformaInvoiceDiffReportVO.getInvoiceNumber",
						proformaInvoiceDiffReportVO.getInvoiceNumber());
				log.log(Log.INFO,
						"proformaInvoiceDiffReportVO.getInvoiceStatus",
						proformaInvoiceDiffReportVO.getInvoiceStatus());
				log.log(Log.INFO, "cn51SummaryVO.getCn66details", cn51SummaryVO.getCn66details());
				log.exiting(CLASS_NAME,"map");
				
			}
			return proformaInvoiceDiffReportVOs;
		}
		catch(CurrencyException currencyException){
			throw new SQLException(currencyException.getErrorCode());
		}
	}
}
