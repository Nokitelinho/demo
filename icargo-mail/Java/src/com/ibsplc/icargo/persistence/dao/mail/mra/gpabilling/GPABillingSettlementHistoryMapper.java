/*
 * GPABillingSettlementHistoryMapper.java created on Mar 27, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class GPABillingSettlementHistoryMapper implements Mapper<InvoiceSettlementHistoryVO> {

	private Log log = LogFactory.getLogger("MRA GPABILLING");
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	

	/* (non-Javadoc)
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public InvoiceSettlementHistoryVO map(ResultSet rs) throws SQLException {
		log.entering("GPABillingSettlementHistoryMapper","map");
		InvoiceSettlementHistoryVO invoiceSettlementHistoryVO=new InvoiceSettlementHistoryVO();
		invoiceSettlementHistoryVO.setCompanyCode(rs.getString("CMPCOD"));
		invoiceSettlementHistoryVO.setGpaCode(rs.getString("GPACOD"));
		invoiceSettlementHistoryVO.setInvoiceNumber(rs.getString("INVNUM"));
		invoiceSettlementHistoryVO.setSettlementReferenceNumber(rs.getString("STLREFNUM"));
		if(!"0000".equals(rs.getString("STLMODNUM"))){
		invoiceSettlementHistoryVO.setChequeNumber(rs.getString("STLMODNUM"));
		}
		invoiceSettlementHistoryVO.setChequeCurrency(rs.getString("STLCUR"));
		if(rs.getDate("PAYDAT")!=null){
			invoiceSettlementHistoryVO.setChequeDate((new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("PAYDAT"))));
		}
		invoiceSettlementHistoryVO.setChequeBank(rs.getString("BNKNAM"));
		invoiceSettlementHistoryVO.setChequeBranch(rs.getString("BRNNAM"));
		try{
		if(rs.getString("STLCUR")!=null && rs.getString("STLCUR").trim().length()>0){
			Money amt= CurrencyHelper.getMoney(rs.getString("STLCUR"));
			amt.setAmount(rs.getDouble("STLAMT"));
		invoiceSettlementHistoryVO.setChequeAmount(amt);
		}
		}
		catch(CurrencyException e){
			e.getErrorCode();
		}
		if(rs.getDate("PAYDAT")!=null){
		invoiceSettlementHistoryVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("PAYDAT")));
		}
		if("N".equals(rs.getString("CHQDELFLG"))){
		invoiceSettlementHistoryVO.setIsDeleted(false);
		}
		else if("Y".equals(rs.getString("CHQDELFLG"))){
			invoiceSettlementHistoryVO.setIsDeleted(true);
		}
		invoiceSettlementHistoryVO.setRemarks(rs.getString("RMK"));
		invoiceSettlementHistoryVO.setMcaRefNum(rs.getString("MCAREFNUM"));
		
		log.exiting("GPABillingSettlementHistoryMapper","map");
		return invoiceSettlementHistoryVO;
	}

}
