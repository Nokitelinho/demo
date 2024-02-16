/**
 *GPABillingCN51InvoiceDetailsMapper.java Created on Mar 30, 2012
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
public class GPABillingCN51InvoiceDetailsMapper implements MultiMapper<GPASettlementVO>{

	private Log log = LogFactory.getLogger("MRA GPABILLING");
	/**
	 * @param resultset
	 * @throws SQLException
	 * 
	 */
	public List<GPASettlementVO> map(ResultSet resultset) throws SQLException {
		log.entering("GPABillingCN51InvoiceDetailsMapper","map");
		List<GPASettlementVO> gpaSettlementVOs = new ArrayList<GPASettlementVO>();
		GPASettlementVO gpaSettlementVO=null;
		String prevCN51Key = null;
		String currCN51Key = null;
		String currInvoiceKey= null;
		String prevInvoiceKey= null;

		while(resultset.next()){
			currCN51Key = new StringBuilder().append(resultset.getString("GPACOD"))			
			.append(resultset.getString("CMPCOD"))
			.toString();
			if(!currCN51Key.equals(prevCN51Key)){				
				gpaSettlementVO=new GPASettlementVO();
				gpaSettlementVO.setCompanyCode(resultset.getString("CMPCOD"));
				gpaSettlementVO.setSettlementDetailsVOs(new ArrayList<SettlementDetailsVO>());
				gpaSettlementVO.setInvoiceSettlementVOs(new ArrayList<InvoiceSettlementVO>());
				gpaSettlementVO.setSettlementCurrency(resultset.getString("STLCURCOD"));				
				gpaSettlementVO.setGpaCode(resultset.getString("GPACOD"));				
				if(resultset.getDate("STLDAT")!= null){
					gpaSettlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getDate("STLDAT") ));
				}
				gpaSettlementVO.setLastUpdatedUser(resultset.getString("LSTUPDUSR"));
				if(resultset.getTimestamp("LSTUPDTIM")!=null){
					gpaSettlementVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getTimestamp("LSTUPDTIM")));
				}
				prevCN51Key=currCN51Key;
				gpaSettlementVOs.add(gpaSettlementVO);
			}
			//Modified and added INVSERNUM for ICRD-211662
			currInvoiceKey = new StringBuilder().append(resultset.getString("GPACOD"))
			.append(resultset.getString("INVNUM"))
			.append(resultset.getInt("INVSERNUM"))
			.append(resultset.getString("CMPCOD")).toString();
			if(resultset.getString("INVNUM")!=null && resultset.getDouble("TOTAMTBLGCUR")>0){
				if(!currInvoiceKey.equals(prevInvoiceKey)){
					InvoiceSettlementVO invoiceSettlementVO = new InvoiceSettlementVO();
					populateInvoiceSettlementVO(resultset,invoiceSettlementVO);
					gpaSettlementVO.getInvoiceSettlementVOs().add(invoiceSettlementVO);
					prevInvoiceKey=currInvoiceKey;
				}
			}



		}



		log.exiting("GPABillingCN51InvoiceDetailsMapper","map");


		return gpaSettlementVOs;
	}
	/**
	 * 
	 * @param resultset
	 * @param invoiceSettlementVO
	 * @throws SQLException
	 */
	private void populateInvoiceSettlementVO(ResultSet resultset,
			InvoiceSettlementVO invoiceSettlementVO) throws SQLException {
		Double dueAmount;
		Money dueAmountInBillingCurr= null;
		invoiceSettlementVO.setCompanyCode(resultset.getString("CMPCOD"));
		invoiceSettlementVO.setGpaCode(resultset.getString("GPACOD"));	
//Added by A-5526 for bug ICRD-	165012 starts
		invoiceSettlementVO.setGpaName(resultset.getString("POANAM"));   
//Added by A-5526 for bug ICRD-	165012 ends    
		invoiceSettlementVO.setInvoiceNumber(resultset.getString("INVNUM"));
		//Added by A-7794 as part of MRA revamp
		invoiceSettlementVO.setInvSerialNumber(resultset.getInt("INVSERNUM"));
		invoiceSettlementVO.setSettlementStatus(resultset.getString("STLSTA"));	
		if("P".equals(invoiceSettlementVO.getSettlementStatus())){
			invoiceSettlementVO.setSettlementStatus("F");
		}
		invoiceSettlementVO.setBillingPeriod(resultset.getString("BLDPRD"));
		invoiceSettlementVO.setSettlementId(resultset.getString("STLREFNUM"));
		invoiceSettlementVO.setSettlementSequenceNumber(resultset.getInt("STLSEQNUM"));
		invoiceSettlementVO.setSerialNumber(resultset.getInt("SERNUM"));		
		try{
			if(resultset.getString("STLCURCOD")!=null && resultset.getString("STLCURCOD").trim().length()>0){
				Money amtInSet=CurrencyHelper.getMoney(resultset.getString("STLCURCOD"));
				Money setAmt=CurrencyHelper.getMoney(resultset.getString("STLCURCOD"));	
				Money duaAmt=CurrencyHelper.getMoney(resultset.getString("STLCURCOD"));
				amtInSet.setAmount(resultset.getDouble("TOTAMTBLGCUR"));
				invoiceSettlementVO.setAmountInSettlementCurrency(amtInSet);
				setAmt.setAmount(resultset.getDouble("STLAMT"));
				invoiceSettlementVO.setAmountAlreadySettled(setAmt);
				duaAmt.setAmount(resultset.getDouble("DUEAMT"));
				invoiceSettlementVO.setDueAmount(duaAmt);
			}

			if(resultset.getString("CRTCURCOD")!=null && resultset.getString("CRTCURCOD").trim().length()>0){
				Money amtInCon=CurrencyHelper.getMoney(resultset.getString("CRTCURCOD"));
				amtInCon.setAmount(resultset.getDouble("TOTAMTCRTCUR"));
				invoiceSettlementVO.setAmountInContractCurrency(amtInCon);
			}
		}
		catch(CurrencyException e){
			e.getErrorCode();
		}
		invoiceSettlementVO.setBillingCurrencyCode(resultset.getString("BLGCURCOD"));
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

			
		invoiceSettlementVO.setSettlementCurrencyCode(resultset.getString("STLCURCOD"));
		invoiceSettlementVO.setContractCurrencyCode(resultset.getString("CRTCURCOD"));
		if(resultset.getTimestamp("BLGPRDTOO")!=null){
			invoiceSettlementVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getDate("BLGPRDTOO")));
		}

		/**
		 * for optimistic locking
		 */
		invoiceSettlementVO.setLastUpdatedUser(resultset.getString("LSTUPDUSR"));
		if(resultset.getTimestamp("LSTUPDTIM")!=null){
			invoiceSettlementVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getTimestamp("LSTUPDTIM")));
		}

	}



}
