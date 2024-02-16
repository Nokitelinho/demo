/*
 * GPABillingCN51SettlementDetailsMapper.java created on Mar 26, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class GPABillingCN51SettlementDetailsMapper implements MultiMapper<InvoiceSettlementVO>{

	private Log log = LogFactory.getLogger("MRA GPABILLING");
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<InvoiceSettlementVO> map(ResultSet rs) throws SQLException {
		log.entering("GPABillingCN51SettlementDetailsMapper","map");
		List<InvoiceSettlementVO> invoiceVOs=new ArrayList<InvoiceSettlementVO>();
		
		String currInvoiceKey= null;
		Map<String, InvoiceSettlementVO> invoiceDetailsMap = new HashMap<String, InvoiceSettlementVO>();;

		while(rs.next()){
		InvoiceSettlementVO invoiceSettlementVO = new InvoiceSettlementVO();
		currInvoiceKey = new StringBuilder().append(rs.getString("STLGPACOD"))
				.append(rs.getString("STLREFNUM")) 
				//.append(rs.getInt("GPASEQNUM"))
				.append(rs.getString("INVNUM")) 
				//.append(rs.getInt("SERNUM"))
				.append(rs.getString("CMPCOD")).toString();
		if(!invoiceDetailsMap.containsKey(currInvoiceKey)){
		populateInvoiceSettlementVO(rs,invoiceSettlementVO);
		invoiceVOs.add(invoiceSettlementVO);
		invoiceDetailsMap.put(currInvoiceKey, invoiceSettlementVO);
		}
		}

	/*	List<GPASettlementVO> gpaSettlementVOs = new ArrayList<GPASettlementVO>();
		//		Collection<SettlementDetailsVO> settlementdetailsVOs= new ArrayList<SettlementDetailsVO>();
		String currSettlementKey = null;
		String prevSettlementKey = null;		
		//		Collection<InvoiceSettlementVO> invoiceSettlementVOs = new ArrayList<InvoiceSettlementVO>();
		String currInvoiceKey= null;
		String prevInvoiceKey= null;
		String currSettlementDetailsKey = null;
		String prevSettlementDetailsKey = null;
		GPASettlementVO gpaSettlementVO=null;
		String currChequeNo=null;		
		Integer prevSerialNo=null;
		Map<String, GPASettlementVO> settlementMap = new HashMap<String, GPASettlementVO>();
		Map<String, SettlementDetailsVO> settlementDetailsMap = null;
		Map<String, InvoiceSettlementVO> invoiceDetailsMap = null;

		while(hasNext()){

			currSettlementKey = new StringBuilder().append(rs.getString("GPACOD"))			
			.append(rs.getString("CMPCOD")).append(rs.getString("STLREFNUM")).append(rs.getInt("SEQNUM"))
			.toString();
			if(!currSettlementKey.equals(prevSettlementKey)){
				gpaSettlementVO = new GPASettlementVO();
				gpaSettlementVO.setCompanyCode(rs.getString("CMPCOD"));
				gpaSettlementVO.setSettlementDetailsVOs(new ArrayList<SettlementDetailsVO>());
				gpaSettlementVO.setInvoiceSettlementVOs(new ArrayList<InvoiceSettlementVO>());
				settlementDetailsMap = new HashMap<String, SettlementDetailsVO>();
				invoiceDetailsMap = new HashMap<String, InvoiceSettlementVO>();
				gpaSettlementVO.setSettlementId(rs.getString("STLREFNUM"));				
				gpaSettlementVO.setSettlementCurrency(rs.getString("POASTLCURCOD"));
				gpaSettlementVO.setSettlementSequenceNumber(rs.getInt("SEQNUM"));
				gpaSettlementVO.setGpaCode(rs.getString("GPACOD"));				
				if(rs.getDate("STLDAT")!= null){
					gpaSettlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("STLDAT") ));
				}
				*//**
				 * for optimistic locking
				 *//*
				gpaSettlementVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
				if(rs.getTimestamp("LSTUPDTIM")!=null){
					gpaSettlementVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
				}  
				gpaSettlementVOs.add(gpaSettlementVO);
				prevSettlementKey= currSettlementKey;
			}
			currSettlementDetailsKey = new StringBuilder().append(rs.getString("STLGPACOD"))
			.append(rs.getString("STLREFNUM"))
			.append(rs.getInt("SEQNUM")).append(rs.getInt("SERNUM"))
			.append(rs.getString("STLCMPCOD")).toString();
			if(rs.getString("STLREFNUM")!=null ){
				if(!settlementDetailsMap.containsKey(currSettlementDetailsKey)){
					currChequeNo=rs.getString("STLMODNUM");

					SettlementDetailsVO settlementDetailsVO = new SettlementDetailsVO();		
					populateSettlementDetailsVO(rs,settlementDetailsVO);
					gpaSettlementVO.getSettlementDetailsVOs().add(settlementDetailsVO);					
					settlementDetailsMap.put(currSettlementDetailsKey, settlementDetailsVO);


				}
			}

			//Modified and added INVSERNUM for ICRD-211662
			currInvoiceKey = new StringBuilder().append(rs.getString("INVGPACOD"))
			.append(rs.getString("STLREFNUM"))
			.append(rs.getInt("GPASEQNUM"))
			.append(rs.getString("INVNUM"))
			.append(rs.getInt("INVSERNUM"))
			.append(rs.getString("INVCMPCOD")).toString();
			if(rs.getString("INVNUM")!=null ||  rs.getInt("STLSEQNUM")!=0){
				if(!currInvoiceKey.equals(prevInvoiceKey)){
					InvoiceSettlementVO invoiceSettlementVO = new InvoiceSettlementVO();
					populateInvoiceSettlementVO(rs,invoiceSettlementVO);
					gpaSettlementVO.getInvoiceSettlementVOs().add(invoiceSettlementVO);
					prevInvoiceKey = currInvoiceKey;
				}
			}


		}

		// This part of the code is required to add the last parent
		// The last parent wont be added by the main loop
		if(gpaSettlementVO!=null){
			increment();
		}

		log.exiting("GPABillingCN51SettlementDetailsMapper","map");		
		return gpaSettlementVOs;*/
		return invoiceVOs;
	}
	/**
	 * 
	 * @param rs
	 * @param invoiceSettlementVO
	 * @throws SQLException
	 */
	private void populateInvoiceSettlementVO(ResultSet rs,
			InvoiceSettlementVO invoiceSettlementVO) throws SQLException {
		Double dueAmount;
		Money dueAmountInBillingCurr= null;
		invoiceSettlementVO.setCompanyCode(rs.getString("CMPCOD"));
		invoiceSettlementVO.setSerialNumber(rs.getInt("SERNUM"));
		invoiceSettlementVO.setSettlementSequenceNumber(rs.getInt("GPASEQNUM"));
		invoiceSettlementVO.setGpaCode(rs.getString("INVGPACOD"));
		invoiceSettlementVO.setGpaName(rs.getString("POANAM"));
		invoiceSettlementVO.setInvoiceNumber(rs.getString("INVNUM"));
		//Added by A-7794 as part of MRA revamp
		invoiceSettlementVO.setInvSerialNumber(rs.getInt("INVSERNUM"));
		if("P".equals(rs.getString("STLSTA"))){
			invoiceSettlementVO.setSettlementStatus("D");
		}
		else{
			invoiceSettlementVO.setSettlementStatus(rs.getString("STLSTA"));
		}		
		invoiceSettlementVO.setSettlementId(rs.getString("STLREFNUM"));
		invoiceSettlementVO.setBillingPeriod(rs.getString("BLDPRD"));
		if(rs.getTimestamp("BLGPRDTOO")!=null){
			invoiceSettlementVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("BLGPRDTOO")));
		}
		try{
			if(rs.getString("STLCUR")!=null && rs.getString("STLCUR").trim().length()>0){
				Money amtInSet=CurrencyHelper.getMoney(rs.getString("STLCUR"));
				Money setAmt=CurrencyHelper.getMoney(rs.getString("STLCUR"));
				Money duaAmt=CurrencyHelper.getMoney(rs.getString("STLCUR"));
				amtInSet.setAmount(rs.getDouble("TOTAMTBLGCUR"));
				invoiceSettlementVO.setAmountInSettlementCurrency(amtInSet);
				setAmt.setAmount(rs.getDouble("C51STLAMT"));
				invoiceSettlementVO.setAmountAlreadySettled(setAmt);
				duaAmt.setAmount(rs.getDouble("DUEAMT"));
				invoiceSettlementVO.setDueAmount(duaAmt);
			}

			if(rs.getString("CRTCURCOD")!=null && rs.getString("CTRCURCOD").trim().length()>0){
				Money amtInCon=CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
				amtInCon.setAmount(rs.getDouble("TOTAMTCTRCUR"));
				invoiceSettlementVO.setAmountInContractCurrency(amtInCon);
			}
		}
		catch(CurrencyException e){
			e.getErrorCode();
		}
		invoiceSettlementVO.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
		if(invoiceSettlementVO.getDueAmount()!=null){
			if(invoiceSettlementVO.getDueAmount().getAmount()>=0){
				if(invoiceSettlementVO.getAmountInSettlementCurrency()!=null && invoiceSettlementVO.getAmountAlreadySettled()!=null ){
					dueAmount=invoiceSettlementVO.getAmountInSettlementCurrency().getAmount()-invoiceSettlementVO.getAmountAlreadySettled().getAmount();
					try {
						if(invoiceSettlementVO.getBillingCurrencyCode()!= null){
							dueAmountInBillingCurr = CurrencyHelper.getMoney(invoiceSettlementVO.getBillingCurrencyCode());
							dueAmountInBillingCurr.setAmount(dueAmount);
							invoiceSettlementVO.setDueAmount(dueAmountInBillingCurr);
						}

					} catch (CurrencyException e) {
						// TODO Auto-generated catch block						
					}

				}
			}
		}


		
		invoiceSettlementVO.setSettlementCurrencyCode(rs.getString("POASTLCURCOD"));
		invoiceSettlementVO.setContractCurrencyCode(rs.getString("CTRCURCOD"));

		/**
		 * for optimistic locking
		 */
		invoiceSettlementVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
		if(rs.getTimestamp("LSTUPDTIM")!=null){
			invoiceSettlementVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
		}
	}
	/**
	 * 
	 * @param rs
	 * @param settlementDetailsVO
	 * @throws SQLException
	 */
	private void populateSettlementDetailsVO(ResultSet rs,
			SettlementDetailsVO settlementDetailsVO) throws SQLException{

		settlementDetailsVO.setCompanyCode(rs.getString("STLCMPCOD"));
		settlementDetailsVO.setGpaCode(rs.getString("STLGPACOD"));		
		settlementDetailsVO.setSettlementId(rs.getString("STLREFNUM"));
		settlementDetailsVO.setSerialNumber(rs.getInt("SERNUM"));
		settlementDetailsVO.setSettlementSequenceNumber(rs.getInt("SEQNUM"));
		settlementDetailsVO.setChequeBank(rs.getString("BNKNAM"));
		settlementDetailsVO.setChequeBranch(rs.getString("BRNNAM"));
		if(rs.getDate("PAYDAT")!= null){
			settlementDetailsVO.setChequeDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("PAYDAT") ));
		}
		settlementDetailsVO.setChequeNumber(rs.getString("STLMODNUM"));
		if(rs.getString("CHQDELFLG")!=null){
			settlementDetailsVO.setIsDeleted(rs.getString("CHQDELFLG"));	
		}

		settlementDetailsVO.setRemarks(rs.getString("RMK"));		
		settlementDetailsVO.setChequeCurrency(rs.getString("STLCUR"));
		try {
			if(rs.getString("STLCUR")!=null && rs.getString("STLCUR").trim().length()>0){
				Money chequeAmount = CurrencyHelper.getMoney(rs.getString("STLCUR"));
				chequeAmount.setAmount(rs.getDouble("STLAMT"));
				settlementDetailsVO.setChequeAmount(chequeAmount);				
			} 
		}
		catch (CurrencyException e) {			
			log.log(Log.FINE,  "CurrencyException");
		}
	}
}
