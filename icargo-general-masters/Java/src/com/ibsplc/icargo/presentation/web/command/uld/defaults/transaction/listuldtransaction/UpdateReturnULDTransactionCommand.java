/*
 * UpdateReturnULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class UpdateReturnULDTransactionCommand  extends BaseCommand {
    
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
	private static final String UPDATE_SUCCESS = "update_success";
    
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("UpdateListULDTransactionCommand","execute");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
    	
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		
		TransactionListVO txnListVO = new TransactionListVO();
		TransactionListVO   transactionListVO = listULDTransactionSession.getTransactionListVO();
		
		
		log.log(Log.FINE, "\n\n\n\n transactionListVO  ", transactionListVO);
		Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
    	Page<ULDTransactionDetailsVO>  uldTransactionDetailsVOs  
													= transactionListVO.getTransactionDetailsPage();
		String[] primaryKey = listULDTransactionForm.getUldDetails();
		
		if (primaryKey != null && primaryKey.length > 0) {
			int cnt=0;
			int index = 0;
			int primaryKeyLen = primaryKey.length;
			if (uldTransactionDetailsVOs != null && uldTransactionDetailsVOs.size() != 0) {
				for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
					index++;
					String primaryKeyFromVO =new StringBuilder(uldTransactionDetailsVO.getUldNumber())
																		.append(uldTransactionDetailsVO.getTransactionRefNumber())
																		.append(index).toString();
					// Added By Sreekumar S
					uldTransactionDetailsVO.setWaived(0);
					uldTransactionDetailsVO.setDemurrageAmount(0);
					uldTransactionDetailsVO.setTransationPeriod(null);
					uldTransactionDetailsVO.setOtherCharges(0);
					uldTransactionDetailsVO.setTotal(0);
					uldTransactionDetailsVO.setTaxes(0);
					log.log(Log.FINE, "\n\n\n\n primaryKeyFromVO  ",
							primaryKeyFromVO);
						//log.log(Log.FINE, "\n\n\n\n primaryKey[cnt]  "+primaryKey[cnt]);
						if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
							equalsIgnoreCase(primaryKey[cnt].trim())) {
						             uldTxnDetailsVOs.add(uldTransactionDetailsVO);
        		    	          	 cnt ++;
					   }
				  }
			}
		}
		
		
		Collection<AccessoryTransactionVO> accDetailsVOs = new ArrayList<AccessoryTransactionVO>();
    	Collection<AccessoryTransactionVO> accTransactionDetailsVOs  
													= transactionListVO.getAccessoryTransactions();
		String[] primaryKeyAcc = listULDTransactionForm.getAccessoryDetails();
		
		if (primaryKeyAcc != null && primaryKeyAcc.length > 0) {
			int cnt=0;
			int index = 0;
			int primaryKeyLen = primaryKeyAcc.length;
			if (accTransactionDetailsVOs != null && accTransactionDetailsVOs.size() != 0) {
				for (AccessoryTransactionVO accessoryTransactionVO : accTransactionDetailsVOs) {
					index++;
					String primaryKeyFromVO =new StringBuilder(accessoryTransactionVO.getAccessoryCode())
																		.append(accessoryTransactionVO.getTransactionRefNumber())
																		.append(index).toString();
					 
						if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
							equalsIgnoreCase(primaryKeyAcc[cnt].trim())) {
						            accDetailsVOs.add(accessoryTransactionVO);
        		    	          	 cnt ++;
					   }
				  }
			}
		}
		
		String txnType = "";
		if (uldTxnDetailsVOs != null && uldTxnDetailsVOs.size() != 0) {
			for (ULDTransactionDetailsVO uldTxnVO : uldTxnDetailsVOs) {
				txnType =	uldTxnVO.getTransactionType();
				break;
			}
		}
		
		if (accDetailsVOs != null && accDetailsVOs.size() != 0) {
			for (AccessoryTransactionVO accVO : accDetailsVOs) {
				txnType =	accVO.getTransactionType();
				break;
			}
		}
		
		listULDTransactionSession.setStationCode(logonAttributes.getAirportCode());
	    txnListVO.setReturnStationCode(logonAttributes.getAirportCode());
		txnListVO.setUldTransactionsDetails(uldTxnDetailsVOs);
		txnListVO.setAccessoryTransactions(accDetailsVOs);
		txnListVO.setTransactionType(txnType);
		LocalDate ldte = new LocalDate(txnListVO.getReturnStationCode(),Location.ARP,true);
		txnListVO.setStrRetDate(ldte.toDisplayFormat(CALENDAR_DATE_FORMAT));
		log.log(Log.FINE, "\n\n\nCurrent Time is------------------>", ldte.toDisplayTimeOnlyFormat());
		txnListVO.setStrRetTime(ldte.toDisplayTimeOnlyFormat().substring(0,5));
		log.log(Log.FINE, "\n\n\n\n txnListVO  ", txnListVO);
		listULDTransactionSession.setReturnTransactionListVO(txnListVO);			
     
     	listULDTransactionForm.setMode("R");
     
		listULDTransactionForm.setScreenStatusFlag(
		      ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
        invocationContext.target =UPDATE_SUCCESS;
             
         	log.exiting("UpdateListULDTransactionCommand","execute");
     
    }
   
}
