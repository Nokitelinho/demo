/*
 * DeleteLoanBorrowDetailsCommand.java  Created on Feb 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
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
public class DeleteLoanBorrowDetailsCommand  extends BaseCommand {
    
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
    
    //added by a-3045 for CR QF1016 starts
    private static final String BLANK = "";
    //added by a-3045 for CR QF1016 ends
   	
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	//Commented by a-3045 for bug ULD539 ofr code review changes
    	/*ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();*/		
    	MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
    	LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		//added by a-3045 for CR QF1016 starts
		/*transactionVO.setCompanyCode(loanBorrowULDSession.getCompanyCode());
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
    		
    		log.log(Log.FINE, "\n\n\n\nDATE AND TIME" + txndat);
    		if (txndat != null && !"".equals(txndat)){
    			transactionVO.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
    			transactionVO.setStrTransactionDate(strTxnDate);
    			transactionVO.setTransactionTime(txnTime);
    			}else {
    				transactionVO.setStrTransactionDate("");
    				transactionVO.setTransactionTime("");
    			}
    		
    		transactionVO.setTransactionRemark(maintainULDTransactionForm.getTransactionRemarks());
    		transactionVO.setPartyType(maintainULDTransactionForm.getPartyType());
    		transactionVO.setFromPartyCode(maintainULDTransactionForm.getFromPartyCode().toUpperCase());
    		transactionVO.setToPartyCode(maintainULDTransactionForm.getToPartyCode().toUpperCase());
    		transactionVO.setFromPartyName(maintainULDTransactionForm.getFromPartyName());
    		transactionVO.setToPartyName(maintainULDTransactionForm.getToPartyName());*/   		
		updateSession(transactionVO, maintainULDTransactionForm);
    	//Commented by a-3045 for bug ULD539 ofr code review changes	  
		//Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
		ArrayList<ULDTransactionDetailsVO>  uldTransactionDetailsVOs  
													= new ArrayList<ULDTransactionDetailsVO>(transactionVO.getUldTransactionDetailsVOs());
    	
    	Collection<ULDTransactionDetailsVO> removeVOs = new ArrayList<ULDTransactionDetailsVO>();
		String[] primaryKey = maintainULDTransactionForm.getUldDetails();
		if (primaryKey != null && primaryKey.length > 0) {
			log.log(Log.ALL, "primaryKey.length", primaryKey.length);
			for (int i = 0; i < primaryKey.length; i++) {
				int index = Integer.parseInt(primaryKey[i]);
				ULDTransactionDetailsVO removeVO = uldTransactionDetailsVOs.get(index);
				log.log(Log.ALL, "primaryKey.length1", primaryKey.length);
				removeVOs.add(removeVO);
				log.log(Log.ALL, "primaryKey.length1", removeVOs);
			}			
			/*int cnt=0;
			int primaryKeyLen = primaryKey.length;
			if (uldTransactionDetailsVOs != null && uldTransactionDetailsVOs.size() != 0) {
				for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
					String primaryKeyFromVO = uldTransactionDetailsVO.getUldNumber();
						if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
							equalsIgnoreCase(primaryKey[cnt].trim())) {
						       if(("I").equalsIgnoreCase(uldTransactionDetailsVO.getOperationalFlag())) {
						    	
        		    	      }else {
        		    	    	     uldTransactionDetailsVO.setOperationalFlag("D");
        		    		         uldTxnDetailsVOs.add(uldTransactionDetailsVO);
        		    	     }
							cnt ++;
					}
					else {
						 uldTxnDetailsVOs.add(uldTransactionDetailsVO);
					}
				}
			}*/
		}
		uldTransactionDetailsVOs.removeAll(removeVOs);
		log.log(Log.ALL, "uldTransactionDetailsVOs", uldTransactionDetailsVOs);
		// Added by A-2412 for Loan/Borrow CR
	/*	ArrayList<ULDTransactionDetailsVO> uldDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
   if(uldTxnDetailsVOs !=null && uldTxnDetailsVOs.size()>0){
		log.log(Log.ALL, "delete cmd uldTxnDetailsVOs ---> "+uldTxnDetailsVOs);
		
		ULDTransactionDetailsVO uldDetailsToCopy;

		// to remove the reference
			for (ULDTransactionDetailsVO vo : uldTxnDetailsVOs ) {
				uldDetailsToCopy = new ULDTransactionDetailsVO();
				try {
					BeanHelper.copyProperties(uldDetailsToCopy,vo );
				} catch (SystemException e) {
//printStackTrrace()();
				}
				uldDetailsVOs. add(uldDetailsToCopy);
				}*/
		//added by a-3045 for CR QF1016 ends
/******** commented for bug 102914 starts*************************/
		/*
		 if(uldTransactionDetailsVOs !=null && uldTransactionDetailsVOs.size()>0){
			 log.log(Log.ALL, "uldTransactionDetailsVOs"+uldTransactionDetailsVOs);	
		int i=0;
		int size=uldTransactionDetailsVOs.size();
		log.log(Log.ALL, " size"+size);		
		String firstVal=uldTransactionDetailsVOs.get(0).getControlReceiptNumber();
		//String iden=firstVal.substring(firstVal.length()-7,firstVal.length());
		//String airlineID=firstVal.substring(0,firstVal.length()-8);		
		String airlineID=firstVal.substring(0,4);
		String iden=firstVal.substring(5,firstVal.length());		
		String loanTxnNumPrefix=airlineID+"0";
		String crnToSet=loanTxnNumPrefix+iden;		
		int count=0;
		for(ULDTransactionDetailsVO vo:uldTransactionDetailsVOs){
				String crn=uldTransactionDetailsVOs.get(count).getControlReceiptNumber();
				vo.setControlReceiptNumber(crnToSet);
				vo.setControlReceiptNumberPrefix(loanTxnNumPrefix);
				vo.setCrnToDisplay(iden);
				String id=crn.substring(5,crn.length());
				airlineID=crn.substring(0,4);
				crn=crnToSet;
				i=Integer.parseInt(crn.substring(4,5));
				if(i==2){
					i=0;
					if(count+1 < uldTransactionDetailsVOs.size()){
						String str=uldTransactionDetailsVOs.get(count+1).getControlReceiptNumber();
						id=str.substring(5,str.length());
					}
				}else{
					i++;
				}
				loanTxnNumPrefix=airlineID+i;
				crnToSet=loanTxnNumPrefix+id;
				log.log(Log.ALL, " crnToSet="+crnToSet);
				count++;		
			}		
		log.log(Log.ALL, "delete cmd after sorting ---> "+uldTransactionDetailsVOs);
		
		//loanBorrowULDSession.setCtrlRcptNo(uldDetailsVOs.get(uldDetailsVOs.size()-1).getControlReceiptNumber());
		}
		 */
		 /******** commented for bug 102914 ends*************************/
		 transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
		if( uldTransactionDetailsVOs==null ||uldTransactionDetailsVOs.size()==0){
			loanBorrowULDSession.setCtrlRcptNo(null);
			loanBorrowULDSession.setCtrlRcptNoPrefix(null);
		}else{
			loanBorrowULDSession.setCtrlRcptNo(uldTransactionDetailsVOs.get(uldTransactionDetailsVOs.size()-1).getControlReceiptNumber());			
		}			
		// Added by A-2412 ends 
		loanBorrowULDSession.setTransactionVO(transactionVO);
    	maintainULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	//maintainULDForm.setStatusFlag("screenload");
    	invocationContext.target =DELETE_SUCCESS;
        
    }
    
    //added by a-3045 for CR QF1016 starts   
    /**
	 * @param transactionVO
	 * @param form
	 * @return
	 */
    private void updateSession(TransactionVO transactionVO,
			MaintainULDTransactionForm form) {		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		int len = 0;		
		if (form.getCrn() != null ) {			
			len = form.getCrn().length;
		}
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
		transactionVO.setCompanyCode(logonAttributes.getCompanyCode());
	    transactionVO.setTransactionType(form.getTransactionType());
		transactionVO.setTransactionNature(form.getTransactionNature());
		transactionVO.setTransactionStation(form.getTransactionStation());
		String txnDate = form.getTransactionDate();
		String strTxnDate = form.getTransactionDate();
		String txnTime = form.getTransactionTime();
		LocalDate ldte = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		if(!txnTime.contains(":")){
			txnTime=txnTime.concat(":00");
		}
		StringBuilder txndat = new StringBuilder();
		txndat.append(txnDate).append(" ").append(txnTime).append(":00");
		//txnDate = txnDate.concat(" "+txnTime+(":00"));		
		if (txndat.length()>0){
			transactionVO.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
			transactionVO.setStrTransactionDate(strTxnDate);
			transactionVO.setTransactionTime(txnTime);
			}else {
				transactionVO.setStrTransactionDate(BLANK);
				transactionVO.setTransactionTime(BLANK);
			}		
		transactionVO.setTransactionRemark(form.getTransactionRemarks());
		if(form.getPartyType() != null && 
				form.getPartyType().trim().length() > 0){
			transactionVO.setPartyType(form.getPartyType().trim());
		}
		if(form.getFromPartyCode() != null && 
				form.getFromPartyCode().trim().length() > 0){
			transactionVO.setFromPartyCode(form.getFromPartyCode().trim().toUpperCase());
		}
		if(form.getToPartyCode() != null && 
				form.getToPartyCode().trim().length() > 0){
			transactionVO.setToPartyCode(form.getToPartyCode().trim().toUpperCase());
		}
		if(form.getFromPartyName() != null && 
				form.getFromPartyName().trim().length() > 0){
			transactionVO.setFromPartyName(form.getFromPartyName().trim());
		}
		if(form.getToPartyName() != null && 
				form.getToPartyName().trim().length() > 0){
			transactionVO.setToPartyName(form.getToPartyName().trim());
		}
		transactionVO.setAwbNumber(form.getAwbNumber());
		if("Y".equals(form.getLoaded())){
			transactionVO.setEmptyStatus("N");
		}else{
			transactionVO.setEmptyStatus("Y");
		}
		for (int i = 0; i < len; i++) {
			log.log(Log.FINE, "len", len);
			ULDTransactionDetailsVO vo = new ULDTransactionDetailsVO();
			vo.setControlReceiptNumberPrefix(form.getCrnPrefix()[i]);
			vo.setCrnToDisplay(form.getCrn()[i]);
			String crn = new StringBuilder(form.getCrnPrefix()[i]).append(
					form.getCrn()[i]).toString();
			vo.setControlReceiptNumber(crn);			
			vo.setFromPartyName(form.getFromPartyName());
			vo.setOperationalFlag(ULDTransactionDetailsVO.OPERATION_FLAG_INSERT);
			vo.setToPartyCode(form.getToPartyCode());
			vo.setToPartyName(form.getToPartyName());
			vo.setUldNature(form.getUldNature()[i]);			
			vo.setUldNumber(form.getUldNum()[i]);			
			vo.setCompanyCode(logonAttributes.getCompanyCode());					
			vo.setTransactionType(form.getTransactionType());
			vo.setPartyType(form.getPartyType());
			if (txndat.length()>0) {
				vo.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
			}
			//changed and added for bug ULD565 by a-3045 starts
			vo.setTransactionStationCode(form.getTransactionStation());
			if(form.getDestnAirport()[i] != null && !BLANK.equals(form.getDestnAirport()[i])){
				vo.setTxStationCode(form.getDestnAirport()[i]);
			}else{
				vo.setTxStationCode(form.getTransactionStation());
			}
			log.log(Log.FINE, "getDestnAirport");
			//log.log(Log.FINE, "getDamageCheck()[i]" + form.getDamageCheck()[i]);
			vo.setLastUpdateUser(logonAttributes.getUserId());
			vo.setTransactionStatus(TransactionVO.TO_BE_RETURNED);
			vo.setUldConditionCode(form.getUldCondition()[i]);
			if (("DAM").equals(form.getUldCondition()[i])) {
				vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_YES);
			} else {
				vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_NO);
			}
			//Added by A-4072 for CR ICRD-192300 starts
			/*
			 * As part of new UCR report few fields are added in ULD010
			 * they are only required to display in Report
			 * Below details are only expected from screen ULD010.
			 */
			if (form.getDamageRemark()[i] != null &&
					!form.getDamageRemark()[i].isEmpty()) {
				vo.setDamageRemark(form.getDamageRemark()[i]);
			}    
			if (form.getOdlnCode()[i] != null &&
					!form.getOdlnCode()[i].isEmpty()) {
				vo.setOdlnCode(form.getOdlnCode()[i].toUpperCase());   
			}
			boolean isDamagedSelected= false;
			if(form.getDamagedFlag()!=null){
				for( int dmgindx = 0; dmgindx < form.getDamagedFlag().length; dmgindx++)
				{					
					if(form.getDamagedFlag()[dmgindx]!=null &&
							i==Integer.parseInt(form.getDamagedFlag()[dmgindx])){
						isDamagedSelected = true;
						break;
					}					
				}
			}
			if (isDamagedSelected){
				vo.setDamageFlagFromScreen(ULDTransactionDetailsVO.FLAG_YES);
			}else{ 
				vo.setDamageFlagFromScreen(ULDTransactionDetailsVO.FLAG_NO);  
			}      
			vo.setOriginatorName(form.getOriginatorName().toUpperCase());     
			//Added by A-4072 for CR ICRD-192300 end
			/*if (form.getDamageCheck()[i] != null
					&& form.getDamageCheck()[i].trim().length() > 0
					&& ULDTransactionDetailsVO.FLAG_YES.equals(form
							.getDamageCheck()[i])) {				
				vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_YES);
			} else {			
				vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_NO);
			}*/
			uldTransactionDetailsVOs.add(vo);			
		}
		Collection<ULDTransactionDetailsVO> uldVos = transactionVO
				.getUldTransactionDetailsVOs();
		if (uldVos != null && uldVos.size() > 0) {
			transactionVO.getUldTransactionDetailsVOs().removeAll(uldVos);
			transactionVO.getUldTransactionDetailsVOs().addAll(
					uldTransactionDetailsVOs);
		}			
	}
    //added by a-3045 for CR QF1016 ends
}
