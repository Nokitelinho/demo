/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.GPASettlementMailbagMapper.java
 *
 *	Created by	:	A-7531
 *	Created on	:	26-Apr-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;


import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;


import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.GPASettlementMailbagMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	26-Apr-2018	:	Draft
 */
public class GPASettlementMailbagMapper implements
MultiMapper<InvoiceSettlementVO> {
	
	private static final String CMPCOD = "CMPCOD";
	private static final String MALSEQNUM = "MALSEQNUM";
	private static final String STLREFNUM = "STLREFNUM";
	private static final String STLCUR = "STLCUR";

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public List<InvoiceSettlementVO> map(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		List<InvoiceSettlementVO> invoiceSettlemnetVOs=new ArrayList<InvoiceSettlementVO>();
		Map<String, InvoiceSettlementVO> invoiceDetailsMap =  new HashMap<String, InvoiceSettlementVO>();
		String key="";
		while (rs.next()) {
			if(rs.getLong(MALSEQNUM)!=0 && rs.getString(CMPCOD)!=null ){
			key = new StringBuilder().append(rs.getLong(MALSEQNUM))
					.append(rs.getString(CMPCOD)).append(rs.getString(STLREFNUM)).toString();
			}
			if(!invoiceDetailsMap.containsKey(key)){
			InvoiceSettlementVO invoiceSettlementVo=new InvoiceSettlementVO() ;
		invoiceSettlementVo.setGpaCode(rs.getString("GPACOD"));
		invoiceSettlementVo.setCompanyCode(rs.getString(CMPCOD));
		invoiceSettlementVo.setInvoiceStatus(rs.getString("INVSTA"));	 
		if (rs.getString("BLGPRDTOO") != null) {
			invoiceSettlementVo.setBillingPeriod( rs.getString("BLGPRDTOO"));
		}
		else if (rs.getString("BLGPRD") != null) {
			invoiceSettlementVo.setBillingPeriod(rs.getString("BLGPRD"));
		}
		if(rs.getDate("STLDAT")!=null)
		{
			invoiceSettlementVo.setSettlementDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE, rs.getDate("STLDAT")));
		}
		invoiceSettlementVo.setInvoiceNumber(rs.getString("INVNUM"));
		invoiceSettlementVo.setInvSerialNumber(rs.getInt("INVSERNUM"));
		invoiceSettlementVo.setSettlementSequenceNumber(rs.getInt("INVSEQNUM"));
		invoiceSettlementVo.setMailbagID(rs.getString("MALIDR"));
		invoiceSettlementVo.setMailsequenceNum(rs.getLong(MALSEQNUM));
		invoiceSettlementVo.setSettlementCurrencyCode(rs.getString(STLCUR));
		invoiceSettlementVo.setRemarks(rs.getString("INVRMK"));
		invoiceSettlementVo.setSerialNumber(rs.getInt("INVSERNUM"));
		invoiceSettlementVo.setCaseClosed(rs.getString("CASCLSFLG"));
		invoiceSettlementVo.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
		invoiceSettlementVo.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
		if (rs.getDate("LSTUPDTIM") != null){
		invoiceSettlementVo.setLastUpdatedTime(new LocalDate(
				LocalDate.NO_STATION,Location.NONE, rs.getDate("LSTUPDTIM")));
		}
		invoiceSettlementVo.setContractCurrencyCode(rs.getString("CTRCURCOD"));
		Money dueAmount = null;
		Money settledamount = null;
		Money totamtblgcurrency = null;
		Money totamtctrcurrency = null;
		Money revnetamt = null;
		Money totblgamt=null;
		Money totsettleamt=null;
		Money amount=null;
		Money amountSettled=null;
		Money billedAmount=null;
		Money currSettAmt=null;//ICRD-349950

		
	    try {
	    	
			dueAmount = CurrencyHelper.getMoney(rs.getString(STLCUR));
			settledamount = CurrencyHelper.getMoney(rs.getString(STLCUR));
			totamtblgcurrency = CurrencyHelper.getMoney(rs.getString(STLCUR));
			totamtctrcurrency = CurrencyHelper.getMoney(rs.getString(STLCUR));
			revnetamt = CurrencyHelper.getMoney(rs.getString(STLCUR));
			totblgamt  = CurrencyHelper.getMoney(rs.getString(STLCUR));
			totsettleamt  = CurrencyHelper.getMoney(rs.getString(STLCUR));
			amount = CurrencyHelper.getMoney(rs.getString(STLCUR));
			amountSettled=CurrencyHelper.getMoney(rs.getString(STLCUR));
			billedAmount = CurrencyHelper.getMoney(rs.getString(STLCUR));
			currSettAmt=CurrencyHelper.getMoney(rs.getString(STLCUR));
			
			
		} catch (CurrencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	    dueAmount.setAmount(rs.getDouble("C66DUE_AMT"));	
	    settledamount.setAmount(rs.getDouble("C66STLAMT"));
	     amountSettled.setAmount(rs.getDouble("INVSTLAMT"));
	    invoiceSettlementVo.setSettlementStatus(rs.getString("C66STLSTA"));
	    invoiceSettlementVo.setInvoiceStatus(rs.getString("STLSTA"));
	    totamtblgcurrency.setAmount(rs.getDouble("NETAMTBLGCUR"));
	    totamtctrcurrency.setAmount(rs.getDouble("TOTAMTCTRCUR"));
	    totblgamt.setAmount(rs.getDouble("TOTAMTBLGCUR"));
	    totsettleamt.setAmount(rs.getDouble("STLAMT"));
	    amount.setAmount(rs.getDouble("AMT"));
	    if(rs.getDouble("REVNETAMT")!=0){
	    	billedAmount.setAmount(rs.getDouble("NETAMTBLGCUR"));
	    	invoiceSettlementVo.setAmountInSettlementCurrency(billedAmount);
	    	revnetamt.setAmount(rs.getDouble("REVNETAMT"));
	    	double actualBilled=totamtblgcurrency.getAmount()+revnetamt.getAmount();
	    	totamtblgcurrency.setAmount(actualBilled);
	    	invoiceSettlementVo.setActualBilled(totamtblgcurrency);
	    	/*double newDueamt=dueAmount.getAmount()+revnetamt.getAmount();
	    	dueAmount.setAmount(newDueamt);*/
	    	invoiceSettlementVo.setDueAmount(dueAmount);
	    }else{
	    	invoiceSettlementVo.setAmountInSettlementCurrency(totamtblgcurrency);
	    	 invoiceSettlementVo.setActualBilled(totamtblgcurrency);
	    	 invoiceSettlementVo.setDueAmount(dueAmount);
	    }
	    invoiceSettlementVo.setAmountSettled(amountSettled);
	    invoiceSettlementVo.setTotalBlgamt(totblgamt);
	    invoiceSettlementVo.setTotalSettledamt(totsettleamt);
	    invoiceSettlementVo.setMcaNumber(revnetamt);
	    
	    invoiceSettlementVo.setAmountAlreadySettled(settledamount);
	    
	    
	    invoiceSettlementVo.setAmountInContractCurrency(totamtctrcurrency);
	    if(rs.getString(STLREFNUM)!=null ){
	    invoiceSettlementVo.setCurrentSettlingAmount(settledamount.getAmount()<0?currSettAmt:settledamount);//modifed for ICRD-349950
	    }else if(rs.getString(STLREFNUM)==null&&dueAmount.getAmount()!=0){
	    	invoiceSettlementVo.setCurrentSettlingAmount(dueAmount);
	    }else{
	    invoiceSettlementVo.setCurrentSettlingAmount(dueAmount);
	    }
	    invoiceSettlementVo.setTolerancePercentage(rs.getDouble("STLTOLPER"));
	    invoiceSettlementVo.setSettlementValue(rs.getDouble("STLTOLVAL"));
	    invoiceSettlementVo.setSettlementMaxValue(rs.getDouble("STLTOLMAXVAL"));
	    invoiceSettlementVo.setGpaName(rs.getString("POANAM"));
	    invoiceSettlementVo.setSettlementId(rs.getString(STLREFNUM));
	    invoiceSettlementVo.setSettlementLevel(rs.getString("STLLVL"));
	    if(!"0000".equals(rs.getString("STLMODNUM"))){
	    invoiceSettlementVo.setChequeNo(rs.getString("STLMODNUM"));
	    }
	    invoiceSettlementVo.setSettlemetAmt(amount);
	    invoiceSettlementVo.setMcaRefnum(rs.getString("MCAREFNUM"));
	   
	   
		  
	    if(invoiceSettlementVo!=null){
			invoiceSettlemnetVOs.add(invoiceSettlementVo);
		}
	    invoiceDetailsMap.put(key, invoiceSettlementVo);
	  
		}
			else
			{
				Money amountSettld=null;
				try {
					amountSettld=CurrencyHelper.getMoney(rs.getString(STLCUR));
					amountSettld.setAmount(rs.getDouble("INVSTLAMT")+invoiceDetailsMap.get(key).getAmountSettled().getAmount());
					invoiceDetailsMap.get(key).setAmountSettled(amountSettld);
					
				} catch (CurrencyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
	
		}
		return invoiceSettlemnetVOs;
	}

	
}
