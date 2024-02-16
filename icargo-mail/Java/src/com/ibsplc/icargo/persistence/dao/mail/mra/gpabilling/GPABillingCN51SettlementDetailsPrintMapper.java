/**
 * GPABillingCN51SettlementDetailsPrintMapper.java Created on Mar 30, 2012
 *
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
import java.util.List;

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
 * @author a-4823
 *
 */
public class GPABillingCN51SettlementDetailsPrintMapper implements MultiMapper<GPASettlementVO>{
	private Log log = LogFactory.getLogger("MRA GPABILLING");
	@Override
	public List<GPASettlementVO> map(ResultSet rs) throws SQLException {
		log.entering("GPABillingCN51InvoiceDetailsMapper","map");
		List<GPASettlementVO> gpaSettlementVOs = new ArrayList<GPASettlementVO>();		
		String currSettlementKey = null;
		String prevSettlementKey = null;				
		String currInvoiceKey= null;
		String prevInvoiceKey= null;
		String currSettlementDetailsKey = null;
		String prevSettlementDetailsKey = null;
		GPASettlementVO gpaSettlementVO=null;
		String currChequeNo=null;		
		String prevChequeNo=null;

		while(rs.next()){

			currSettlementKey = new StringBuilder().append(rs.getString("GPACOD"))
			.append(rs.getString("STLREFNUM"))
			.append(rs.getInt("SEQNUM"))
			.append(rs.getString("CMPCOD"))
			.toString();
			if(!currSettlementKey.equals(prevSettlementKey)){
				gpaSettlementVO = new GPASettlementVO();
				gpaSettlementVO.setCompanyCode(rs.getString("CMPCOD"));
				gpaSettlementVO.setSettlementDetailsVOs(new ArrayList<SettlementDetailsVO>());
				gpaSettlementVO.setInvoiceSettlementVOs(new ArrayList<InvoiceSettlementVO>());
				gpaSettlementVO.setSettlementId(rs.getString("STLREFNUM"));				
				gpaSettlementVO.setSettlementCurrency(rs.getString("POASTLCURCOD"));
				gpaSettlementVO.setSettlementSequenceNumber(rs.getInt("SEQNUM"));
				gpaSettlementVO.setGpaCode(rs.getString("GPACOD"));				
				if(rs.getDate("STLDAT")!= null){
					gpaSettlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("STLDAT") ));
				}
				/**
				 * for optimistic locking
				 */
				gpaSettlementVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
				if(rs.getTimestamp("LSTUPDTIM")!=null){
					gpaSettlementVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
				}
				prevSettlementKey=currSettlementKey;
				gpaSettlementVOs.add(gpaSettlementVO);
			}

			currSettlementDetailsKey = new StringBuilder().append(rs.getString("STLGPACOD"))
			.append(rs.getString("STLREFNUM"))
			.append(rs.getInt("SEQNUM")).append(rs.getInt("SERNUM"))
			.append(rs.getString("STLCMPCOD")).toString();
			if(rs.getString("STLREFNUM")!=null ){
				if(!currSettlementDetailsKey.equals(prevSettlementDetailsKey)){
					currChequeNo=rs.getString("STLMODNUM");				

					SettlementDetailsVO settlementDetailsVO = new SettlementDetailsVO();				
					populateSettlementDetailsVO(rs,settlementDetailsVO);
					gpaSettlementVO.getSettlementDetailsVOs().add(settlementDetailsVO);					
					prevSettlementDetailsKey = currSettlementDetailsKey;
					prevChequeNo=currChequeNo;

				}
			}
			currInvoiceKey = new StringBuilder().append(rs.getString("INVGPACOD"))
			.append(rs.getString("STLREFNUM"))
			.append(rs.getInt("SEQNUM"))
			.append(rs.getInt("SERNUM"))
			
			.append(rs.getString("INVNUM"))
			.append(rs.getString("INVCMPCOD")).toString();
			if(rs.getString("INVNUM")!=null ||rs.getInt("GPASEQNUM")!=0){
				if(!currInvoiceKey.equals(prevInvoiceKey)){
					InvoiceSettlementVO invoiceSettlementVO = new InvoiceSettlementVO();


					populateInvoiceSettlementVO(rs,invoiceSettlementVO);

					gpaSettlementVO.getInvoiceSettlementVOs().add(invoiceSettlementVO);
					prevInvoiceKey = currInvoiceKey;
				}
			}



		}
		return gpaSettlementVOs;
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
		invoiceSettlementVO.setSettlementStatus(rs.getString("STLSTA"));
		invoiceSettlementVO.setSettlementId(rs.getString("STLREFNUM"));
		invoiceSettlementVO.setBillingPeriod(rs.getString("BLDPRD"));
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

			if(rs.getString("CTRCURCOD")!=null && rs.getString("CTRCURCOD").trim().length()>0){
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
			SettlementDetailsVO settlementDetailsVO) throws SQLException {
		settlementDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		settlementDetailsVO.setGpaCode(rs.getString("GPACOD"));		
		settlementDetailsVO.setSettlementId(rs.getString("STLREFNUM"));
		settlementDetailsVO.setSerialNumber(rs.getInt("SERNUM"));
		settlementDetailsVO.setSettlementSequenceNumber(rs.getInt("SEQNUM"));
		settlementDetailsVO.setChequeBank(rs.getString("BNKNAM"));
		settlementDetailsVO.setChequeBranch(rs.getString("BRNNAM"));
		if(rs.getDate("PAYDAT")!= null){
			settlementDetailsVO.setChequeDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("PAYDAT") ));
		}
		settlementDetailsVO.setChequeNumber(rs.getString("STLMODNUM"));
		settlementDetailsVO.setIsDeleted(rs.getString("CHQDELFLG"));	
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
