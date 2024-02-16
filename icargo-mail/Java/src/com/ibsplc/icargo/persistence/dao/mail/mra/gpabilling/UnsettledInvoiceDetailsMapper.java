/**
 * GPABillingCN51UnsettledInvoiceDetailsMapper.java Created on Mar 30, 2012
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

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
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
public class UnsettledInvoiceDetailsMapper implements MultiMapper<InvoiceSettlementVO>{
	private Log log = LogFactory.getLogger("MRA GPABILLING");

	/**
	 * @param resultset
	 * @throws SQLException
	 */
	public List<InvoiceSettlementVO> map(ResultSet resultset) throws SQLException {
		log.entering("UnsettledInvoiceDetailsMapper", "map");
		//List<GPASettlementVO> gpaSettlementVOs = new ArrayList<GPASettlementVO>();
		List<InvoiceSettlementVO> invoiceSettlementVOs = new ArrayList<InvoiceSettlementVO>();
		//GPASettlementVO gpaSettlementVO=new GPASettlementVO();
		String prevCN51Key = null;
		String currCN51Key = null;
		//Modified and added INVSERNUM for ICRD-211662
		while(resultset.next()){
			currCN51Key = new StringBuilder().append(resultset.getString("GPACOD"))			
			.append(resultset.getString("CMPCOD")).append(resultset.getInt("INVSERNUM")).append(resultset.getString("INVNUM"))
			.toString();
			//if(resultset.getDouble("TOTAMTBLGCUR")>0){
			if(!currCN51Key.equals(prevCN51Key)){
				//Commented by A-4809 for BUG ICRD-18449..Starts
				//New mapper to set value to GPASettlementVO
/*				gpaSettlementVO=new GPASettlementVO();
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
				}*/
				//Commented by A-4809 for BUG ICRD-18449..Ends

				InvoiceSettlementVO invoiceSettlementVO = new InvoiceSettlementVO();
				populateInvoiceSettlementVO(resultset,invoiceSettlementVO);
				invoiceSettlementVOs.add(invoiceSettlementVO);
			//	gpaSettlementVO.getInvoiceSettlementVOs().add(invoiceSettlementVO);

				prevCN51Key=currCN51Key;
			}

			//gpaSettlementVOs.add(gpaSettlementVO);
			//}
		}
		return invoiceSettlementVOs;
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
		invoiceSettlementVO.setInvoiceNumber(resultset.getString("INVNUM"));
		invoiceSettlementVO.setSettlementStatus(resultset.getString("STLSTA"));	
		if("P".equals(invoiceSettlementVO.getSettlementStatus())){
			invoiceSettlementVO.setSettlementStatus("F");
		}
		invoiceSettlementVO.setBillingPeriod(resultset.getString("BLDPRD"));
		if(resultset.getTimestamp("BLGPRDTOO")!=null){
			invoiceSettlementVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getDate("BLGPRDTOO")));
		}
		invoiceSettlementVO.setSettlementId(resultset.getString("STLREFNUM"));
		invoiceSettlementVO.setSettlementSequenceNumber(resultset.getInt("STLSEQNUM"));
		invoiceSettlementVO.setSerialNumber(resultset.getInt("SERNUM"));
		//Added by A-7794 as part of ICRD-257781
		invoiceSettlementVO.setInvSerialNumber(resultset.getInt("INVSERNUM"));
		try{
			if(resultset.getString("STLCURCOD")!=null && resultset.getString("STLCURCOD").trim().length()>0){
				Money amtInSet=CurrencyHelper.getMoney(resultset.getString("STLCURCOD"));
				Money setAmt=CurrencyHelper.getMoney(resultset.getString("STLCURCOD"));				
				amtInSet.setAmount(resultset.getDouble("TOTAMTBLGCUR"));
				invoiceSettlementVO.setAmountInSettlementCurrency(amtInSet);
				setAmt.setAmount(resultset.getDouble("STLAMT"));
				invoiceSettlementVO.setAmountAlreadySettled(setAmt);
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

				
		invoiceSettlementVO.setSettlementCurrencyCode(resultset.getString("STLCURCOD"));
		invoiceSettlementVO.setContractCurrencyCode(resultset.getString("CRTCURCOD"));

		/**
		 * for optimistic locking
		 */
		invoiceSettlementVO.setLastUpdatedUser(resultset.getString("LSTUPDUSR"));
		if(resultset.getTimestamp("LSTUPDTIM")!=null){
			invoiceSettlementVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getTimestamp("LSTUPDTIM")));
		}

	}

}
