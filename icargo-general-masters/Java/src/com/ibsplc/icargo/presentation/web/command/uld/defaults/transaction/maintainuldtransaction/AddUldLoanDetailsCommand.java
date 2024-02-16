/*
 * AddUldLoanDetailsCommand.java  Created on Feb 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class AddUldLoanDetailsCommand extends BaseCommand {

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
	private static final String ADD_SUCCESS = "add_success";
	


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
		log.log(Log.FINE, "\n\n\n\n transactionVO BEFORE MODIFY ---> ",
				transactionVO);
		log.log(Log.FINE,
				"\n\n\n\n maintainULDTransactionForm.getPartyType() ---> ",
				maintainULDTransactionForm.getPartyType());
		log.log(Log.ALL, "AddUldLoanDetailsCommand condition code is ",
				maintainULDTransactionForm.getUldConditionCode());
		//		Airport validation
		if (maintainULDTransactionForm.getLoanTransactionStation() != null
				&& maintainULDTransactionForm.getLoanTransactionStation()
						.trim().length() > 0) {
			
			
			Collection<ErrorVO> errorsOwnerStation = null;
			try {
				log.log(Log.FINE, "\n Airport delegate " );
				new AreaDelegate().validateAirportCode(getApplicationSession().getLogonVO().getCompanyCode(),
						maintainULDTransactionForm.getLoanTransactionStation().toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				errorsOwnerStation = handleDelegateException(businessDelegateException);
   			}
			if (errorsOwnerStation != null && errorsOwnerStation.size() > 0) {
				ErrorVO errorVO = new ErrorVO(
				"uld.defaults.transaction.invalidstation");
				errorVO.setErrorDisplayType(ERROR);
				
				invocationContext.addError(errorVO);
				maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);	
				invocationContext.target = ADD_SUCCESS;
				return;
			}
			
		}
		
		
	
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionVO
				.getUldTransactionDetailsVOs();
		
		ArrayList<ULDTransactionDetailsVO> selectedULDs=loanBorrowULDSession.getSelectedULDColl();
		if(("LIST").equals(maintainULDTransactionForm.getLoanPopupFlag())){
			ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
			uldTransactionDetailsVO.setUldNumber(maintainULDTransactionForm
					.getLoanUldNum().toUpperCase());
			String txnNum = maintainULDTransactionForm.getLoanTxnNum();
			// Added by A-2412 on 18th OCT for CRN Editable CR
			String txnNumPrefix=maintainULDTransactionForm.getLoanTxnNumPrefix();
			// Addition  by A-2412 on 18th OCT for CRN Editable CR ends
			
			if (!("").equalsIgnoreCase(txnNum)) {
				//uldTransactionDetailsVO.setCapturedRefNumber(txnNum);
				//uldTransactionDetailsVO.setControlReceiptNumber(txnNum);
				uldTransactionDetailsVO.setControlReceiptNumber(txnNumPrefix+txnNum);
			}
			uldTransactionDetailsVO.setOperationalFlag("I");
			if ("true".equalsIgnoreCase(maintainULDTransactionForm
					.getLoanDamage())||"on".equalsIgnoreCase(maintainULDTransactionForm
							.getLoanDamage())) {
				uldTransactionDetailsVO.setDamageStatus("Y");
			} else {
				uldTransactionDetailsVO.setDamageStatus("N");
			}
			uldTransactionDetailsVO.setReturnPartyCode(transactionVO.getFromPartyCode().toUpperCase());
			uldTransactionDetailsVO.setUldNature(maintainULDTransactionForm.getLoanULDNature());
			
			// Added by A-2412
			uldTransactionDetailsVO.setUldConditionCode(maintainULDTransactionForm.getUldConditionCode());
			
			log.log(Log.ALL,
					"uldTransactionDetailsVO.getUldConditionCode()-----",
					uldTransactionDetailsVO.getUldConditionCode());
			if (maintainULDTransactionForm.getLoanTransactionStation() != null
					&& maintainULDTransactionForm.getLoanTransactionStation()
							.trim().length() > 0) {
				uldTransactionDetailsVO.setTxStationCode(maintainULDTransactionForm
						.getLoanTransactionStation().toUpperCase());
			} else {
				uldTransactionDetailsVO.setTxStationCode(getApplicationSession()
						.getLogonVO().getAirportCode());
			}
			
			
			if(selectedULDs==null){
				selectedULDs=new ArrayList<ULDTransactionDetailsVO>();	
				selectedULDs.add(uldTransactionDetailsVO);
			}else{
				boolean isPresent=false;
				for(ULDTransactionDetailsVO uldVO:selectedULDs){
					if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getLoanUldNum().toUpperCase())) {
						isPresent=true;
						uldVO.setUldNumber(maintainULDTransactionForm.getLoanUldNum());
						String txnNums = maintainULDTransactionForm.getLoanTxnNum();
						String txnNumsPrefix = maintainULDTransactionForm.getLoanTxnNumPrefix();
						if (!("").equalsIgnoreCase(txnNums)) {
				//uldVO.setCapturedRefNumber(txnNums);
				uldVO.setControlReceiptNumber(txnNumsPrefix+txnNums);
			}
			uldVO.setOperationalFlag("I");
			if ("true".equalsIgnoreCase(maintainULDTransactionForm
				.getLoanDamage())||"on".equalsIgnoreCase(maintainULDTransactionForm
						.getLoanDamage())) {
			uldVO.setDamageStatus("Y");
			} else {
				uldVO.setDamageStatus("N");
					}
					uldVO.setReturnPartyCode(transactionVO.getFromPartyCode().toUpperCase());
					uldVO.setUldNature(maintainULDTransactionForm.getLoanULDNature());
					
					// Added by A-2412
					uldVO.setUldConditionCode(maintainULDTransactionForm.getUldConditionCode());
					log.log(Log.ALL, "uldVO.getUldConditionCode()-----", uldVO.getUldConditionCode());
					if (maintainULDTransactionForm.getLoanTransactionStation() != null
							&& maintainULDTransactionForm.getLoanTransactionStation()
									.trim().length() > 0) {
						uldVO.setTxStationCode(maintainULDTransactionForm
								.getLoanTransactionStation().toUpperCase());
					} else {
						uldVO.setTxStationCode(getApplicationSession()
								.getLogonVO().getAirportCode());
			
					}								
				}
				
			}
			if(!isPresent){
				selectedULDs.add(uldTransactionDetailsVO);				
			}}
            loanBorrowULDSession.setSelectedULDColl(selectedULDs);	  
			}
			log.log(Log.FINE, "\n\n\n\n selectedULDs in final ADD LOAN ---> ",
					selectedULDs);
			if (uldTransactionDetailsVOs != null
					&& uldTransactionDetailsVOs.size() > 0) {
				if(("MODIFY").equals(maintainULDTransactionForm.getModifyMode())){
					if(("LIST-MOD").equals(maintainULDTransactionForm.getLoanPopupFlag())){
						for (ULDTransactionDetailsVO uldVO : uldTransactionDetailsVOs) {
							if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getLoanUldNum().toUpperCase())) {
								uldVO.setUldNumber(maintainULDTransactionForm.getLoanUldNum());
								String txnNum = maintainULDTransactionForm.getLoanTxnNum();
								String txnNumPrefix = maintainULDTransactionForm.getLoanTxnNumPrefix();
								if (!("").equalsIgnoreCase(txnNum)) {
									//uldVO.setCapturedRefNumber(txnNum);
									uldVO.setControlReceiptNumber(txnNumPrefix+txnNum);
								}
								uldVO.setOperationalFlag("I");
								log
										.log(
												Log.FINE,
												"\n\n\n\n maintainULDTransactionForm.getLoanDamage() ---> ",
												maintainULDTransactionForm
														.getLoanDamage());
								if ("true".equalsIgnoreCase(maintainULDTransactionForm
										.getLoanDamage())||"on".equalsIgnoreCase(maintainULDTransactionForm
												.getLoanDamage())) {
									uldVO.setDamageStatus("Y");
								} else {
									uldVO.setDamageStatus("N");
								}
								uldVO.setReturnPartyCode(transactionVO.getFromPartyCode().toUpperCase());
								uldVO.setUldNature(maintainULDTransactionForm.getLoanULDNature());
								
								//Added by A-2412
								uldVO.setUldConditionCode(maintainULDTransactionForm.getUldConditionCode());
								//Addition ends 
								
								if (maintainULDTransactionForm.getLoanTransactionStation() != null
										&& maintainULDTransactionForm.getLoanTransactionStation()
												.trim().length() > 0) {
									uldVO.setTxStationCode(maintainULDTransactionForm
											.getLoanTransactionStation().toUpperCase());
								} else {
									uldVO.setTxStationCode(getApplicationSession()
											.getLogonVO().getAirportCode());

								}	
								
								
							}

						}
						for (ULDTransactionDetailsVO uldVO : selectedULDs) {
							if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getLoanUldNum().toUpperCase())) {
								uldVO.setUldNumber(maintainULDTransactionForm.getLoanUldNum());
								String txnNum = maintainULDTransactionForm.getLoanTxnNum();
								String txnNumPrefix = maintainULDTransactionForm.getLoanTxnNumPrefix();
								if (!("").equalsIgnoreCase(txnNum)) {
									//uldVO.setCapturedRefNumber(txnNum);
									uldVO.setControlReceiptNumber(txnNumPrefix+txnNum);
								}
								uldVO.setOperationalFlag("I");
								log
										.log(
												Log.FINE,
												"\n\n\n\n maintainULDTransactionForm.getLoanDamage() ---> ",
												maintainULDTransactionForm
														.getLoanDamage());
								if ("true".equalsIgnoreCase(maintainULDTransactionForm
										.getLoanDamage())||"on".equalsIgnoreCase(maintainULDTransactionForm
												.getLoanDamage())) {
									uldVO.setDamageStatus("Y");
								} else {
									uldVO.setDamageStatus("N");
								}
								uldVO.setReturnPartyCode(transactionVO.getFromPartyCode().toUpperCase());
								uldVO.setUldNature(maintainULDTransactionForm.getLoanULDNature());
								
								//Added by A-2412
								uldVO.setUldConditionCode(maintainULDTransactionForm.getUldConditionCode());
								//Addition ends
								
								if (maintainULDTransactionForm.getLoanTransactionStation() != null
										&& maintainULDTransactionForm.getLoanTransactionStation()
												.trim().length() > 0) {
									uldVO.setTxStationCode(maintainULDTransactionForm
											.getLoanTransactionStation().toUpperCase());
								} else {
									uldVO.setTxStationCode(getApplicationSession()
											.getLogonVO().getAirportCode());

								}	
								
							}

						}
						transactionVO
								.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
						log.log(Log.FINE,
								"\n\n\n\n transactionVO AFTER MODIFY ---> ",
								transactionVO);
						log.log(Log.FINE,
								"\n\n\n\n selectedULDs AFTER MODIFY ---> ",
								selectedULDs);
						loanBorrowULDSession.setTransactionVO(transactionVO);
						loanBorrowULDSession.setSelectedULDColl(selectedULDs);
					}
						
					} 				
				else{	
					if(selectedULDs!=null && selectedULDs.size()>0){
				    uldTransactionDetailsVOs.addAll(selectedULDs);
					transactionVO
							.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);		
					loanBorrowULDSession.setTransactionVO(transactionVO);
					}
		     }		
			} else {
				Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
				if(selectedULDs!=null && selectedULDs.size()>0){
				uldTxnDetailsVOs.addAll(selectedULDs);
				transactionVO.setUldTransactionDetailsVOs(uldTxnDetailsVOs);
				loanBorrowULDSession.setTransactionVO(transactionVO);
				}
				
			}
			maintainULDTransactionForm.setLoanPopupClose(FLAG_YES);
			maintainULDTransactionForm.setLoanPopupFlag("");
			maintainULDTransactionForm.setAddUldDisable("");
			maintainULDTransactionForm
			.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    invocationContext.target = ADD_SUCCESS;
	}
}
