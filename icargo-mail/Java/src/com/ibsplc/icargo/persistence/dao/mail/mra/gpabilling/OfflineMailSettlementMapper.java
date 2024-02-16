/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.OfflineMailSettlementMapper.java
 *
 *	Created by	:	A-7531
 *	Created on	:	14-Jan-2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.OfflineMailSettlementMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	14-Jan-2019	:	Draft
 */
public class OfflineMailSettlementMapper implements MultiMapper<InvoiceSettlementVO>{

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public List<InvoiceSettlementVO> map(ResultSet rs) throws SQLException {
		List<InvoiceSettlementVO> invoiceSettlemnetVOs=new ArrayList<InvoiceSettlementVO>();
		while (rs.next()) {
			InvoiceSettlementVO invoiceSettlementVO=new InvoiceSettlementVO();

			invoiceSettlementVO.setCompanyCode(rs.getString("CMPCOD"));
			invoiceSettlementVO.setContractCurrencyCode(rs.getString("CURCOD"));
			invoiceSettlementVO.setDestnCode(rs.getString("DSTCOD"));
			invoiceSettlementVO.setSettlementFileName(rs.getString("FILNAM"));
			invoiceSettlementVO.setSettlementFileType(rs.getString("FILTYP"));
			invoiceSettlementVO.setFlownSector(rs.getString("FLNSEC"));
			invoiceSettlementVO.setInvoiceNumber(rs.getString("INVNUM"));

			Money mailchg = null;
			Money netamount = null;
			Money stlamt = null;
			Money surchg=null;
			Money tax=null;
			Money settledamt=null;
			Money dueamt=null;
			Money revnetamt=null;
			Money actualbilled=null;
			if(rs.getString("STLMTCUR")!=null){
				try{
					mailchg = CurrencyHelper.getMoney(rs.getString("STLMTCUR"));
					netamount=CurrencyHelper.getMoney(rs.getString("STLMTCUR"));
					stlamt=CurrencyHelper.getMoney(rs.getString("STLMTCUR"));
					surchg=CurrencyHelper.getMoney(rs.getString("STLMTCUR"));
					tax=CurrencyHelper.getMoney(rs.getString("STLMTCUR"));
					settledamt=CurrencyHelper.getMoney(rs.getString("STLMTCUR"));
					dueamt=CurrencyHelper.getMoney(rs.getString("STLMTCUR"));
					revnetamt = CurrencyHelper.getMoney(rs.getString("STLMTCUR"));
					actualbilled=CurrencyHelper.getMoney(rs.getString("STLMTCUR"));
				mailchg.setAmount(rs.getDouble("MALCHG"));
				netamount.setAmount(rs.getDouble("NETAMT"));
				stlamt.setAmount(rs.getDouble("STLAMT"));
				surchg.setAmount(rs.getDouble("SURCHG"));
				tax.setAmount(rs.getDouble("TAX"));
				settledamt.setAmount(rs.getDouble("C66STLAMT"));
				dueamt.setAmount(rs.getDouble("C66DUEAMT"));
				revnetamt.setAmount(rs.getDouble("REVNETAMT"));
				} catch (CurrencyException e) {
					// TODO Auto-generated catch block
					e.getErrorCode();
				}
			

			}
			if(rs.getDouble("REVNETAMT")!=0){
				double amount=netamount.getAmount()+revnetamt.getAmount();
				actualbilled.setAmount(amount);
				invoiceSettlementVO.setActualBilled(actualbilled);
			}else{
				invoiceSettlementVO.setActualBilled(actualbilled);
			}

			invoiceSettlementVO.setMailCharge(mailchg);
			invoiceSettlementVO.setNetAmount(netamount);
			invoiceSettlementVO.setSettlemetAmt(stlamt);
			invoiceSettlementVO.setSurCharge(surchg);
			invoiceSettlementVO.setAmountAlreadySettled(settledamt);
			invoiceSettlementVO.setDueAmount(dueamt);
			invoiceSettlementVO.setMcaNumber(revnetamt);
			invoiceSettlementVO.setMailRate(rs.getDouble("MALRAT"));
			invoiceSettlementVO.setMailsequenceNum(rs.getLong("MALSEQNUM"));
			invoiceSettlementVO.setGpaCode(rs.getString("PACOD"));
			invoiceSettlementVO.setSettlementCurrencyCode(rs.getString("STLMTCUR"));
			invoiceSettlementVO.setRemarks(rs.getString("STLRMKS"));
			invoiceSettlementVO.setTax(tax);

			Measure strWt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"));
			invoiceSettlementVO.setWgt(strWt);


			invoiceSettlementVO.setMailbagID(rs.getString("MALIDR"));
			invoiceSettlementVO.setMailsequenceNum(rs.getLong("MALSEQNUM"));
			invoiceSettlementVO.setProcessIdentifier(rs.getString("PRCIDR"));
			invoiceSettlementVO.setSummaryGpa(rs.getString("C51GPA"));
			invoiceSettlementVO.setSummaryInvoiceNumber(rs.getString("C51INVNUM"));
			invoiceSettlementVO.setInvSerialNumber(rs.getInt("INVSERNUM"));
			invoiceSettlementVO.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
			invoiceSettlementVO.setTolerancePercentage(rs.getDouble("STLTOLPER"));
			invoiceSettlementVO.setSettlementValue(rs.getDouble("STLTOLVAL"));
			invoiceSettlementVO.setSettlementMaxValue(rs.getDouble("STLTOLMAXVAL"));
			invoiceSettlementVO.setSettlementLevel(rs.getString("STLLVL"));

			invoiceSettlemnetVOs.add(invoiceSettlementVO);
		}
		return invoiceSettlemnetVOs;
	}

}
