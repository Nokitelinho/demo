/*
 * ListULDBorrowDetailsCommand.java  Created on Jan 12,07
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */


public class ListULDBorrowDetailsCommand extends BaseCommand {

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
	private static final String LIST_SUCCESS = "list_success";
	
	private static final String LIST_FAILURE = "list_success";
	
	private static final String AIRLINE = "A";
	
	private static final String AGENT = "G";

	/**
	 * execute method
	 *
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
		log.log(Log.FINE, "\n\n\n\n transactionVO in list BEFORE MODIFY ---> ",
				transactionVO);
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionVO
		.getUldTransactionDetailsVOs();
		
		

		Collection<ErrorVO> errorss = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		/**
		 * to validate UldNumber Format
		 */
		String uldNum = maintainULDTransactionForm.getBorrowUldNum()
				.toUpperCase();
		if (uldNum != null && uldNum.trim().length() > 0) {
			try {
				new ULDDefaultsDelegate().validateULDFormat(
						loanBorrowULDSession.getCompanyCode(), uldNum);

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		
		if(loanBorrowULDSession.getSelectedULDColl()==null ||
				loanBorrowULDSession.getSelectedULDColl().size()==0){
		/**
		 * to get  Airline identifier for party code
		 */
		String fromPrtyCode = maintainULDTransactionForm.getFromPartyCode().toUpperCase();
		String toPrtyCode = maintainULDTransactionForm.getToPartyCode().toUpperCase();
		AirlineValidationVO fromOwnerVO = null;
		AirlineValidationVO toOwnerVO = null;
		AgentDelegate agentDelegate = new AgentDelegate();
		AgentVO agentVO = null;
		if(("L").equals(maintainULDTransactionForm.getTransactionType())){
    		if (fromPrtyCode != null && ! ("".equals(fromPrtyCode))) {
    		
    		try {
				fromOwnerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						maintainULDTransactionForm.getFromPartyCode().toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				
				errorss =  handleDelegateException(businessDelegateException);
   			}
    		}}
		if(("B").equals(maintainULDTransactionForm.getTransactionType())){
    		if (toPrtyCode != null && ! ("".equals(toPrtyCode))) {
    		
    		try {
				toOwnerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						maintainULDTransactionForm.getToPartyCode().toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				
				errorss =  handleDelegateException(businessDelegateException);
   			}
    		}}
		
		if (AIRLINE.equals(maintainULDTransactionForm.getPartyType())) {
			
			if(("L").equals(maintainULDTransactionForm.getTransactionType())){
	    		if (toPrtyCode != null && ! ("".equals(toPrtyCode))) {
	    		
	    		try {
					toOwnerVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(),
							maintainULDTransactionForm.getToPartyCode().toUpperCase());
				}
				catch(BusinessDelegateException businessDelegateException) {
					
					errorss =  handleDelegateException(businessDelegateException);
	   			}
	    		}}
			if(("B").equals(maintainULDTransactionForm.getTransactionType())){
	    		if (fromPrtyCode != null && ! ("".equals(fromPrtyCode))) {
	    		
	    		try {
					fromOwnerVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(),
							maintainULDTransactionForm.getFromPartyCode().toUpperCase());
				}
				catch(BusinessDelegateException businessDelegateException) {
					
					errorss =  handleDelegateException(businessDelegateException);
	   			}
	    		}}
			}
		
		if (AGENT.equals(maintainULDTransactionForm.getPartyType())) {
			if(("B").equals(maintainULDTransactionForm.getTransactionType())){
			if (fromPrtyCode != null && ! ("".equals(fromPrtyCode))) {
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				agentVO = agentDelegate.findAgentDetails(
						logonAttributes.getCompanyCode(),
						maintainULDTransactionForm.getFromPartyCode().toUpperCase());
			} catch (BusinessDelegateException exception) {
				log.log(Log.FINE,"*****in the exception");
				exception.getMessage();
				error = handleDelegateException(exception);
			}}}
			if(("L").equals(maintainULDTransactionForm.getTransactionType())){
			if (toPrtyCode != null && ! ("".equals(toPrtyCode))) {
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
				try {
					agentVO = agentDelegate.findAgentDetails(
							logonAttributes.getCompanyCode(),
							maintainULDTransactionForm.getToPartyCode().toUpperCase());
				} catch (BusinessDelegateException exception) {
					log.log(Log.FINE,"*****in the exception");
					exception.getMessage();
					error = handleDelegateException(exception);
				}}}
			if(agentVO == null){
				ErrorVO errorVO = new ErrorVO("uld.defaults.loanborrowULD.invalidagentcode");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorss.add(errorVO);
			}

		}

		if (errorss != null && errorss.size() > 0) {
			invocationContext.addAllError(errorss);
			maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);
			if(("MODIFY").equals(maintainULDTransactionForm.getModifyMode())){
				maintainULDTransactionForm.setBorrowPopupFlag("BORROWSCREENLOAD-MOD");
			} else {
				maintainULDTransactionForm.setBorrowPopupFlag("BORROWSCREENLOAD");
			}
			invocationContext.target = LIST_SUCCESS;
			return;
	   }

		

		int oprId=0;
		if ((errors == null || errors.size() == 0) && ("A").equals(maintainULDTransactionForm.getPartyType())) {
			try {
				Integer oprID=new ULDDefaultsDelegate().findOperationalAirlineForULD(logonAttributes.getCompanyCode(), uldNum);
				oprId = oprID.intValue();

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			log.log(Log.FINE, "\n\n\n\n oprId ---> ", oprId);
			log.log(Log.FINE, "\n\n\n\n ownerVO.getAirlineIdentifier() ---> ",
					toOwnerVO.getAirlineIdentifier());
		if(oprId==toOwnerVO.getAirlineIdentifier())
		{
			ErrorVO error = null;
			 error = new ErrorVO(
			 "uld.defaults.adduldloan.msg.err.cannotloan");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);

		}}}


		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			maintainULDTransactionForm.setBorrowPopupClose(FLAG_NO);
			if(("MODIFY").equals(maintainULDTransactionForm.getModifyMode())){
				maintainULDTransactionForm.setBorrowPopupFlag("BORROWSCREENLOAD-MOD");
			} else {
				maintainULDTransactionForm.setBorrowPopupFlag("BORROWSCREENLOAD");
			}
		} else {	
			if(("MODIFY").equals(maintainULDTransactionForm.getModifyMode())){
					for (ULDTransactionDetailsVO uldVO : loanBorrowULDSession.getSelectedULDColl()) {
						if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getBorrowUldNum().toUpperCase())) {
							maintainULDTransactionForm.setBorrowUldNum(uldVO.getUldNumber());
							maintainULDTransactionForm.setBorrowTxnNum(uldVO.getControlReceiptNumber());
							maintainULDTransactionForm.setBorrowTransactionStation(uldVO.getTxStationCode());
							if ("Y".equalsIgnoreCase(uldVO.getDamageStatus())) {
								maintainULDTransactionForm.setBorrowDamage("true");
							} else {
								maintainULDTransactionForm.setBorrowDamage("");
							}
							maintainULDTransactionForm.setBorrowULDNature(uldVO.getUldNature());
							// Added by Preet for ULD 344 on 18Apr08 --starts
							// This is to default ULD Condition Code to SER in case coming from SCM / UCM screen
							if(uldVO.getUldConditionCode()!=null && uldVO.getUldConditionCode().trim().length()>0){								
								maintainULDTransactionForm.setUldConditionCode(uldVO.getUldConditionCode());
							}else{								
								maintainULDTransactionForm.setUldConditionCode("SER");
							}
							// Added by Preet for ULD 344 on 18Apr08 --ends							
							
							/* Added by A-2408  generate Borrow CRN */
							if(uldVO.getControlReceiptNumber() !=null && uldVO.getControlReceiptNumber().length()>0){
							String crnPrefix=uldVO.getControlReceiptNumber().substring(0, 5);
							String crn=uldVO.getControlReceiptNumber().substring(5, uldVO.getControlReceiptNumber().length());
							loanBorrowULDSession.setCtrlRcptNo(crn);
							loanBorrowULDSession.setCtrlRcptNoPrefix(crnPrefix);
							maintainULDTransactionForm.setBorrowTxnNum(crn);
							maintainULDTransactionForm.setBorrowTxnNumPrefix(crnPrefix);
						}
						else{
							String fromPrtyCode = maintainULDTransactionForm.getFromPartyCode()
							.toUpperCase();
							AirlineValidationVO toOwnerVO = null;
							errors = new ArrayList<ErrorVO>();
							String airlineID=null;
							
							if (("B").equals(maintainULDTransactionForm.getTransactionType())) {
								if (fromPrtyCode != null && !("".equals(fromPrtyCode))) {

									try {
										toOwnerVO = new AirlineDelegate().validateAlphaCode(
												logonAttributes.getCompanyCode(),
												maintainULDTransactionForm.getFromPartyCode()
														.toUpperCase());
									} catch (BusinessDelegateException businessDelegateException) {

										errors = handleDelegateException(businessDelegateException);
									}
								}
							}
							
							if (errors != null && errors.size() > 0) {
								invocationContext.addAllError(errors);
								invocationContext.target = LIST_FAILURE;
								return;
							}
								
							if(toOwnerVO !=null ){
								airlineID=toOwnerVO.getNumericCode();	
								loanBorrowULDSession.setCtrlRcptNo(null);
								loanBorrowULDSession.setCtrlRcptNoPrefix(airlineID+"-0");
								maintainULDTransactionForm.setBorrowTxnNum(null);
								maintainULDTransactionForm.setBorrowTxnNumPrefix(airlineID+"-0");
							}
							
							
						}
						
						
						/* Added by A-2408 to generate Borrow CRN ends*/
						}
					}
					maintainULDTransactionForm.setBorrowPopupFlag("LIST-MOD");
			}
			else{
				boolean isNotDuplicate = true;
			if(uldTransactionDetailsVOs!=null && uldTransactionDetailsVOs.size()>0){
			for (ULDTransactionDetailsVO uldVO : uldTransactionDetailsVOs) {
				if (uldVO.getUldNumber().equals(
						maintainULDTransactionForm.getBorrowUldNum().toUpperCase())) {
					isNotDuplicate = false;
				}
			}}
			if (!isNotDuplicate) {
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.transaction.uldalreadyexist");
				errorVO.setErrorDisplayType(ERROR);
				
				invocationContext.addError(errorVO);
				maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);
				maintainULDTransactionForm.setBorrowPopupFlag("BORROWSCREENLOAD");
			}else
			{
				if(loanBorrowULDSession.getSelectedULDColl()!=null && loanBorrowULDSession.getSelectedULDColl().size()>0){
				for (ULDTransactionDetailsVO uldVO : loanBorrowULDSession.getSelectedULDColl()) {
					if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getBorrowUldNum().toUpperCase())) {
					
						maintainULDTransactionForm.setBorrowUldNum(uldVO.getUldNumber());
						maintainULDTransactionForm.setBorrowTxnNum(uldVO.getControlReceiptNumber());
						maintainULDTransactionForm.setBorrowTransactionStation(uldVO.getTxStationCode());
						if ("Y".equalsIgnoreCase(uldVO.getDamageStatus())) {
							maintainULDTransactionForm.setBorrowDamage("true");
						} else {
							maintainULDTransactionForm.setBorrowDamage("");
						}
						maintainULDTransactionForm.setBorrowULDNature(uldVO.getUldNature());
						
					}

				}}
				maintainULDTransactionForm.setBorrowPopupFlag("LIST");
			}
			}
		}
		log.log(Log.ALL, "crn number from form is:", maintainULDTransactionForm.getBorrowTxnNum());
		if(maintainULDTransactionForm.getModifyMode()!=null &&
				!("MODIFY").equals(maintainULDTransactionForm.getModifyMode())){
			if(maintainULDTransactionForm.getBorrowTxnNum() !=null && +maintainULDTransactionForm.getBorrowTxnNum().length()>0){
				loanBorrowULDSession.setCtrlRcptNo(maintainULDTransactionForm.getBorrowTxnNum());
			}
		}
		// Addition by A-2412 ends 
		maintainULDTransactionForm.setAddUldDisable("SAVE_ENABLE");
		invocationContext.target = LIST_SUCCESS;
	}
}
