/*
 * ShowTotalReturnULDCommand.java Created on Feb 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ReturnULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ShowTotalReturnULDCommand  extends BaseCommand {
    /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");
	
	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";
	
	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";
		
	/**
	 *Target  if success
	 */
	private static final String TOTAL_SUCCESS = "total_success";
    
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ShowTotalReturnULDCommand","execute");
    	ReturnULDTransactionForm returnULDTransactionForm = (ReturnULDTransactionForm) invocationContext.screenModel;
    	
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		
		TransactionListVO   transactionListVO = listULDTransactionSession.getReturnTransactionListVO();
		
//		String retDate = returnULDTransactionForm.getReturnDate();
//		LocalDate ldte = new LocalDate(listULDTransactionSession.getStationCode(),false);
//		if (retDate != null && !"".equals(retDate)){
//			transactionListVO.setReturnDate(ldte.setDate(retDate ,CALENDAR_DATE_FORMAT));
//			transactionListVO.setStrRetDate(retDate);
//			}else {
//				transactionListVO.setStrRetDate("");
//			}
//		
//		transactionListVO.setReturnStationCode(returnULDTransactionForm.getReturnStation());
		
		String[] waivedArray = returnULDTransactionForm.getWaived(); 
		String[] taxesArray = returnULDTransactionForm.getTaxes(); 
		String[] otherChargesArray = returnULDTransactionForm.getOtherCharges(); 
		String[] damagedArray = returnULDTransactionForm.getDamaged(); 
		String[] returnByArray = returnULDTransactionForm.getReturnBy(); 
		String[] remarksArray = returnULDTransactionForm.getReturnRemarks(); 
		String[] quantityArray = returnULDTransactionForm.getQuantity(); 
		
    	Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
    	Collection<ULDTransactionDetailsVO>  uldTransactionDetailsVOs  
													= transactionListVO.getUldTransactionsDetails();
    	int cnt = 0;
    	if (uldTransactionDetailsVOs != null && uldTransactionDetailsVOs.size() != 0) {
			for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
				uldTransactionDetailsVO.setReturnDate(transactionListVO.getReturnDate());
				uldTransactionDetailsVO.setReturnStationCode(transactionListVO.getReturnStationCode());
				if(waivedArray[cnt] ==null || waivedArray[cnt].length()==0){
					waivedArray[cnt]="0.0";
				}
				uldTransactionDetailsVO.setWaived(Double.parseDouble(waivedArray[cnt]));
				
				if(taxesArray[cnt] ==null || taxesArray[cnt].length()==0){
					taxesArray[cnt]="0.0";
				}
				uldTransactionDetailsVO.setTaxes(Double.parseDouble(taxesArray[cnt]));
				
				if(otherChargesArray[cnt] ==null || otherChargesArray[cnt].length()==0){
					otherChargesArray[cnt]="0.0";
				}
				uldTransactionDetailsVO.setOtherCharges(Double.parseDouble(otherChargesArray[cnt]));
				
				
				double retTotal = (uldTransactionDetailsVO.getDemurrageAmount()
						+uldTransactionDetailsVO.getTaxes() + uldTransactionDetailsVO.getOtherCharges())
						- uldTransactionDetailsVO.getWaived();
				if(retTotal > 0){
					uldTransactionDetailsVO.setTotal(retTotal);
				}else{
					uldTransactionDetailsVO.setTotal(0);
				}
				if("true".equalsIgnoreCase(damagedArray[cnt])) {
				     uldTransactionDetailsVO.setDamageStatus("Y");
				}else {
					 uldTransactionDetailsVO.setDamageStatus("N");
				}
				uldTransactionDetailsVO.setReturnedBy(returnByArray[cnt]);
				uldTransactionDetailsVO.setReturnRemark(remarksArray[cnt]);
				uldTxnDetailsVOs.add(uldTransactionDetailsVO);
				cnt++;
			}
		}
		
		Collection<AccessoryTransactionVO> accDetailsVOs = new ArrayList<AccessoryTransactionVO>();
    	Collection<AccessoryTransactionVO> accTransactionDetailsVOs  
													= transactionListVO.getAccessoryTransactions();
    	cnt = 0;
    	if (accTransactionDetailsVOs != null && accTransactionDetailsVOs.size() != 0) {
			for (AccessoryTransactionVO accessoryTransactionVO : accTransactionDetailsVOs) {
				accessoryTransactionVO.setQuantity(Integer.parseInt(quantityArray[cnt]));
				accDetailsVOs.add(accessoryTransactionVO);
				cnt++;
			}
		}
    	
    	transactionListVO.setUldTransactionsDetails(uldTxnDetailsVOs);
    	transactionListVO.setAccessoryTransactions(accDetailsVOs);
				  
		listULDTransactionSession.setReturnTransactionListVO(transactionListVO);			
          
     	returnULDTransactionForm.setScreenStatusFlag(
		      ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
        invocationContext.target =TOTAL_SUCCESS;
             
         	log.exiting("ShowTotalReturnULDCommand","execute");
     
    }
   
}
