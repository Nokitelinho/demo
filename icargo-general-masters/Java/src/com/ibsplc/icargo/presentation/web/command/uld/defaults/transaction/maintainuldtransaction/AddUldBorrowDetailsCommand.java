/*
 * AddUldBorrowDetailsCommand.java  Created on Feb 9, 2006
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


public class AddUldBorrowDetailsCommand extends BaseCommand {

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

		MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		/* Added by A-2412 for Laon/Borrow CR */
		log.log(Log.ALL, "inside AddUldBorrowDetailsCommand");
		if(maintainULDTransactionForm.getBorrowTxnNum() ==null || maintainULDTransactionForm.getBorrowTxnNum().length()==0){
			log.log(Log.ALL, "maintainULDTransactionForm.getBorrowTxnNum() null ");
			ErrorVO errorVO=new ErrorVO("uld.defaults.transaction.crn.mandatory");
			errorVO.setErrorDisplayType(ERROR);
			maintainULDTransactionForm.setBorrowPopupClose(FLAG_NO);	
			invocationContext.target = ADD_SUCCESS;
			invocationContext.addError(errorVO);
			return;
		}
		else{
			log.log(Log.ALL, "maintainULDTransactionForm.getBorrowTxnNum() not null ");
		}
		// Addition BY A-2412 ends 
		
//		Airport validation
		if (maintainULDTransactionForm.getBorrowTransactionStation() != null
				&& maintainULDTransactionForm.getBorrowTransactionStation()
						.trim().length() > 0) {
			
			
			Collection<ErrorVO> errorsOwnerStation = null;
			try {
				log.log(Log.FINE, "\n Airport delegate " );
				new AreaDelegate().validateAirportCode(getApplicationSession().getLogonVO().getCompanyCode(),
						maintainULDTransactionForm.getBorrowTransactionStation().toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				errorsOwnerStation = handleDelegateException(businessDelegateException);
   			}
			if (errorsOwnerStation != null && errorsOwnerStation.size() > 0) {
				ErrorVO errorVO = new ErrorVO(
				"uld.defaults.transaction.invalidstation");
				errorVO.setErrorDisplayType(ERROR);
				
				invocationContext.addError(errorVO);
				maintainULDTransactionForm.setBorrowPopupClose(FLAG_NO);	
				invocationContext.target = ADD_SUCCESS;
				return;
			}
			
		}
		
		
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionVO
				.getUldTransactionDetailsVOs();
		
		ArrayList<ULDTransactionDetailsVO> selectedULDs=loanBorrowULDSession.getSelectedULDColl();
		if(("LIST").equals(maintainULDTransactionForm.getBorrowPopupFlag())){
			ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
			uldTransactionDetailsVO.setUldNumber(maintainULDTransactionForm
					.getBorrowUldNum().toUpperCase());
			//String txnNum = maintainULDTransactionForm.getBorrowTxnNum();
			/* Added by A-2412 on 30 th Oct to generate Borrow CRN ends */
			String txnNumPrefix=maintainULDTransactionForm.getBorrowTxnNumPrefix();
			String txnNum = txnNumPrefix+maintainULDTransactionForm.getBorrowTxnNum();			
			/* Addition by A-2412 on 30 th Oct to generate Borrow CRN ends */
			
			if (!("").equalsIgnoreCase(txnNum)) {
				//uldTransactionDetailsVO.setCapturedRefNumber(txnNum);
				uldTransactionDetailsVO.setControlReceiptNumber(txnNum);
			}
			uldTransactionDetailsVO.setOperationalFlag("I");
			if ("true".equalsIgnoreCase(maintainULDTransactionForm
					.getBorrowDamage())||"on".equalsIgnoreCase(maintainULDTransactionForm
							.getBorrowDamage())) {
				uldTransactionDetailsVO.setDamageStatus("Y");
			} else {
				uldTransactionDetailsVO.setDamageStatus("N");
			}
			uldTransactionDetailsVO.setReturnPartyCode(transactionVO.getFromPartyCode().toUpperCase());
			uldTransactionDetailsVO.setUldNature(maintainULDTransactionForm.getBorrowULDNature());
			
			//Added by A-2412
			uldTransactionDetailsVO.setUldConditionCode(maintainULDTransactionForm.getUldConditionCode());
			//ends
			
			if (maintainULDTransactionForm.getBorrowTransactionStation() != null
					&& maintainULDTransactionForm.getBorrowTransactionStation()
							.trim().length() > 0) {
				uldTransactionDetailsVO.setTxStationCode(maintainULDTransactionForm
						.getBorrowTransactionStation().toUpperCase());
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
					if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getBorrowUldNum().toUpperCase())) {
						isPresent=true;
						uldVO.setUldNumber(maintainULDTransactionForm.getBorrowUldNum());
						
						//Added by A-2412 on 30 th Oct
						String txnNumsPrefix = maintainULDTransactionForm.getBorrowTxnNumPrefix();
						String txnNums = txnNumsPrefix+maintainULDTransactionForm.getBorrowTxnNum();						
						//ends
						if (!("").equalsIgnoreCase(txnNums)) {
				//uldVO.setCapturedRefNumber(txnNums);
				uldVO.setControlReceiptNumber(txnNums);
			}
			uldVO.setOperationalFlag("I");
			if ("true".equalsIgnoreCase(maintainULDTransactionForm
				.getBorrowDamage())||"on".equalsIgnoreCase(maintainULDTransactionForm
						.getBorrowDamage())) {
			uldVO.setDamageStatus("Y");
			} else {
				uldVO.setDamageStatus("N");
					}
					uldVO.setReturnPartyCode(transactionVO.getFromPartyCode().toUpperCase());
					uldVO.setUldNature(maintainULDTransactionForm.getBorrowULDNature());
					//Added by A-2412
					uldVO.setUldConditionCode(maintainULDTransactionForm.getUldConditionCode());
					//ends
										
					if (maintainULDTransactionForm.getBorrowTransactionStation() != null
							&& maintainULDTransactionForm.getBorrowTransactionStation()
									.trim().length() > 0) {
						uldVO.setTxStationCode(maintainULDTransactionForm
								.getBorrowTransactionStation().toUpperCase());
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
			log.log(Log.FINE,
					"\n\n\n\n selectedULDs in final ADD BORROW ---> ",
					selectedULDs);
			if (uldTransactionDetailsVOs != null
					&& uldTransactionDetailsVOs.size() > 0) {
				if(("MODIFY").equals(maintainULDTransactionForm.getModifyMode())){
					if(("LIST-MOD").equals(maintainULDTransactionForm.getBorrowPopupFlag())){
						for (ULDTransactionDetailsVO uldVO : uldTransactionDetailsVOs) {
							if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getBorrowUldNum().toUpperCase())) {
								uldVO.setUldNumber(maintainULDTransactionForm.getBorrowUldNum());
								
								//Added by A-2412 on 30 th Oct
								String txnNumsPrefix = maintainULDTransactionForm.getBorrowTxnNumPrefix();
								String txnNums =txnNumsPrefix+ maintainULDTransactionForm.getBorrowTxnNum();								
								//ends
								if (!("").equalsIgnoreCase(txnNums)) {
									//uldVO.setCapturedRefNumber(txnNum);
									uldVO.setControlReceiptNumber(txnNums);									
								}
								uldVO.setOperationalFlag("I");
								log
										.log(
												Log.FINE,
												"\n\n\n\n maintainULDTransactionForm.getBorrowDamage() ---> ",
												maintainULDTransactionForm
														.getBorrowDamage());
								if ("true".equalsIgnoreCase(maintainULDTransactionForm
										.getBorrowDamage())||"on".equalsIgnoreCase(maintainULDTransactionForm
												.getBorrowDamage())) {
									uldVO.setDamageStatus("Y");
								} else {
									uldVO.setDamageStatus("N");
								}
								uldVO.setReturnPartyCode(transactionVO.getFromPartyCode().toUpperCase());
								uldVO.setUldNature(maintainULDTransactionForm.getBorrowULDNature());
								//Added by A-2412
								uldVO.setUldConditionCode(maintainULDTransactionForm.getUldConditionCode());
								
								if (maintainULDTransactionForm.getBorrowTransactionStation() != null
										&& maintainULDTransactionForm.getBorrowTransactionStation()
												.trim().length() > 0) {
									uldVO.setTxStationCode(maintainULDTransactionForm
											.getBorrowTransactionStation().toUpperCase());
								} else {
									uldVO.setTxStationCode(getApplicationSession()
											.getLogonVO().getAirportCode());

								}	
								
							}

						}
						for (ULDTransactionDetailsVO uldVO : selectedULDs) {
							if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getBorrowUldNum().toUpperCase())) {
								uldVO.setUldNumber(maintainULDTransactionForm.getBorrowUldNum());
								
								//Added by A-2412 on 30 th Oct
								String txnNumPrefix = maintainULDTransactionForm.getBorrowTxnNumPrefix();
								String txnNum = txnNumPrefix+maintainULDTransactionForm.getBorrowTxnNum();								
								//ends
								if (!("").equalsIgnoreCase(txnNum)) {
									//uldVO.setCapturedRefNumber(txnNum);
									uldVO.setControlReceiptNumber(txnNum);
								}
								uldVO.setOperationalFlag("I");
								log
										.log(
												Log.FINE,
												"\n\n\n\n maintainULDTransactionForm.getBorrowDamage() ---> ",
												maintainULDTransactionForm
														.getBorrowDamage());
								if ("true".equalsIgnoreCase(maintainULDTransactionForm
										.getBorrowDamage())||"on".equalsIgnoreCase(maintainULDTransactionForm
												.getBorrowDamage())) {
									uldVO.setDamageStatus("Y");
								} else {
									uldVO.setDamageStatus("N");
								}
								uldVO.setReturnPartyCode(transactionVO.getFromPartyCode().toUpperCase());
								uldVO.setUldNature(maintainULDTransactionForm.getBorrowULDNature());
								// Added by A-2412
								uldVO.setUldConditionCode(maintainULDTransactionForm.getUldConditionCode());
								
								
								if (maintainULDTransactionForm.getBorrowTransactionStation() != null
										&& maintainULDTransactionForm.getBorrowTransactionStation()
												.trim().length() > 0) {
									uldVO.setTxStationCode(maintainULDTransactionForm
											.getBorrowTransactionStation().toUpperCase());
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
			maintainULDTransactionForm.setBorrowPopupClose(FLAG_YES);
			maintainULDTransactionForm.setBorrowPopupFlag("");
			maintainULDTransactionForm.setAddUldDisable("");
			maintainULDTransactionForm
			.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    invocationContext.target = ADD_SUCCESS;
	}
}
