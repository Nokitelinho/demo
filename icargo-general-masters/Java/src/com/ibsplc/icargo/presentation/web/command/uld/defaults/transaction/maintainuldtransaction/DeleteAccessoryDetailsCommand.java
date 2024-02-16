/*
 * DeleteAccessoryDetailsCommand.java  Created on Feb 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class DeleteAccessoryDetailsCommand  extends BaseCommand {
    
    /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD");
	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";
	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";
	/**
	 * target String if success
	 */
	private static final String DELETE_SUCCESS = "delete_success";
   	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
    	LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		
		transactionVO.setCompanyCode(loanBorrowULDSession.getCompanyCode());
    	//	transactionVO = updateTransaction(maintainULDTransactionForm,transactionVO);
    		transactionVO.setTransactionType(maintainULDTransactionForm.getTransactionType());
    		transactionVO.setTransactionNature(maintainULDTransactionForm.getTransactionNature());
    		transactionVO.setTransactionStation(maintainULDTransactionForm.getTransactionStation());
    		String txnDate = maintainULDTransactionForm.getTransactionDate();
    		String strTxnDate = maintainULDTransactionForm.getTransactionDate();
    		String txnTime = maintainULDTransactionForm.getTransactionTime();
    		LocalDate ldte = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
    		if(!txnTime.contains(":")){
    			txnTime=txnTime.concat(":00");
    		}
    		StringBuilder txndat = new StringBuilder();
    		txndat.append(txnDate).append(" ").append(txnTime).append(":00");
    		//txnDate = txnDate.concat(" "+txnTime+(":00"));
    		
    		log.log(Log.FINE, "\n\n\n\nDATE AND TIME", txndat);
			if (txndat.length()>0){
    			transactionVO.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
    			transactionVO.setStrTransactionDate(strTxnDate);
    			transactionVO.setTransactionTime(txnTime);
    		}else {
    			transactionVO.setStrTransactionDate("");
    			transactionVO.setTransactionTime("");
    		}
    		
    		transactionVO.setTransactionRemark(maintainULDTransactionForm.getTransactionRemarks());
    		transactionVO.setPartyType(maintainULDTransactionForm.getPartyType());    	
    		if(maintainULDTransactionForm.getFromPartyCode() != null && 
    				maintainULDTransactionForm.getFromPartyCode().trim().length() > 0){
    			transactionVO.setFromPartyCode(maintainULDTransactionForm.getFromPartyCode().trim().toUpperCase());
    		}
    		if(maintainULDTransactionForm.getToPartyCode() != null && 
    				maintainULDTransactionForm.getToPartyCode().trim().length() > 0){
    			transactionVO.setToPartyCode(maintainULDTransactionForm.getToPartyCode().trim().toUpperCase());
    		}
    		if(maintainULDTransactionForm.getFromPartyName() != null && 
    				maintainULDTransactionForm.getFromPartyName().trim().length() > 0){
    			transactionVO.setFromPartyName(maintainULDTransactionForm.getFromPartyName().trim());
    		}
    		if(maintainULDTransactionForm.getToPartyName() != null && 
    				maintainULDTransactionForm.getToPartyName().trim().length() > 0){
    			transactionVO.setToPartyName(maintainULDTransactionForm.getToPartyName().trim());
    		}
    		
    		transactionVO.setAwbNumber(maintainULDTransactionForm.getAwbNumber());
    		
    		if("Y".equals(maintainULDTransactionForm.getLoaded())){
    			transactionVO.setEmptyStatus("N");
    		}else{
    			transactionVO.setEmptyStatus("Y");
    		}
		
		Collection<AccessoryTransactionVO> accessTransactionVOs  
												= transactionVO.getAccessoryTransactionVOs();
		Collection<AccessoryTransactionVO> accessoryTransactionVOs = new 	ArrayList<AccessoryTransactionVO>();
          String[] primaryKey = maintainULDTransactionForm.getAccessoryDetails();
          if (primaryKey != null && primaryKey.length > 0) {
	         int cnt=0;
	         int primaryKeyLen = primaryKey.length;
	          if (accessTransactionVOs != null && accessTransactionVOs.size() != 0) {
	        	    for (AccessoryTransactionVO accessoryTransactionVO : accessTransactionVOs) {
	        		String primaryKeyFromVO = accessoryTransactionVO.getAccessoryCode();
	        		      if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
	        				          equalsIgnoreCase(primaryKey[cnt].trim())) {
	        		    	  if(!("I").equalsIgnoreCase(accessoryTransactionVO.getOperationalFlag())) {
	        		    	    accessoryTransactionVO.setOperationalFlag("D");
	        		    	    accessoryTransactionVOs.add(accessoryTransactionVO);
	        		    	  }
	        		    	  cnt ++;
	        		      }
	        		      else {
	        		    	  accessoryTransactionVOs.add(accessoryTransactionVO);
	        		      }
	        	    }
	          }
	     }
	    transactionVO.setAccessoryTransactionVOs(accessoryTransactionVOs);
	      
		loanBorrowULDSession.setTransactionVO(transactionVO);
    	maintainULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	//maintainULDForm.setStatusFlag("screenload");
    	invocationContext.target = DELETE_SUCCESS;
        
    }

}
