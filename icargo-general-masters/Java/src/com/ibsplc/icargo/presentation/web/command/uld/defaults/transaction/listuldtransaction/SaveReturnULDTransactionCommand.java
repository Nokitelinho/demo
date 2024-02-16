/*
 * SaveReturnULDTransactionCommand.java Created on Feb 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ReturnULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class SaveReturnULDTransactionCommand extends BaseCommand {

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

	private static final String PAGE_URL = "fromScmUldReconcile";

	/**
	 * Target if success
	 */
	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";
	
	private static final String SAVE_ULDERRORLOG = "save_ulderrorlog";

	private static final String SCREENID_ULDERRORLOG = "uld.defaults.ulderrorlog";

	private static final String SCREENID_SCMERRORLOG = "uld.defaults.scmulderrorlog";

	// added for scm reconcile

	private static final String SAVE_SCMERRORLOG = "save_scmerrorlog";
	
	private static final String PRINT_BORROW_UCR = "print_borrow_ucr";
	

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SaveReturnULDTransactionCommand", "execute");
		ReturnULDTransactionForm returnULDTransactionForm = (ReturnULDTransactionForm) invocationContext.screenModel;

		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ULDErrorLogSession uldErrorLogSession = (ULDErrorLogSession) getScreenSession(
				MODULE_NAME, SCREENID_ULDERRORLOG);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		String uldDmg = "";
		int flag = 0;
		TransactionListVO transactionListVO = listULDTransactionSession
				.getReturnTransactionListVO();

		String retDate = returnULDTransactionForm.getReturnDate();
		String retTime=returnULDTransactionForm.getReturnTime();
		log.log(Log.FINE, "\n\n\n\n retDate", retDate);
		log.log(Log.FINE, "\n\n\n\n retTime", retTime);
		if(!retTime.contains(":")){
			retTime=retTime.concat(":00");
		}
		log.log(Log.FINE, "\n\n\n\n retTime", retTime);
		LocalDate ldte = new LocalDate(
				transactionListVO.getReturnStationCode(), Location.ARP, true);

		if (retDate != null && !"".equals(retDate)) {

			StringBuilder returnDateAndTime=new StringBuilder();
			returnDateAndTime.append(retDate).append(" ").append(retTime).append(":00");
			log.log(Log.FINE, "\n\n\n\n returnDateAndTime", returnDateAndTime);
			transactionListVO.setReturnDate(ldte.setDateAndTime(returnDateAndTime.toString()));

			transactionListVO.setStrRetDate(retDate);
			transactionListVO.setStrRetTime(retTime.substring(0,5));
		} 
		//Commented by Deepthi as a part of 11595
		/*else {
			transactionListVO.setStrRetDate("");
		}*/

		// transactionListVO.setReturnStationCode(returnULDTransactionForm.getReturnStation());
		// log.log(Log.FINE,
		// "****returnULDTransactionForm.getReturnStation()******"+returnULDTransactionForm.getReturnStation());

		String[] waivedArray = returnULDTransactionForm.getWaived();
		String[] taxesArray = returnULDTransactionForm.getTaxes();
		String[] otherChargesArray = returnULDTransactionForm.getOtherCharges();
		String[] damagedArray = returnULDTransactionForm.getDamaged();
		String[] returnByArray = returnULDTransactionForm.getReturnBy();
		String[] remarksArray = returnULDTransactionForm.getReturnRemarks();
		String[] quantityArray = returnULDTransactionForm.getQuantity();		
		String txnType=returnULDTransactionForm.getTxnType();		
		
		//Added by Preet on 5 th Jan for newly added column CRN in Return Loan/Borrow screen --starts	
		String[] controlReceiptNumberPrefix = returnULDTransactionForm.getControlReceiptNumberPrefix();
		String[] crnToDisplay = returnULDTransactionForm.getCrnToDisplay();
		
		log.log(Log.FINE, "returnULDTransactionForm.getReturnDate()---",
				returnULDTransactionForm.getReturnDate());
		log.log(Log.FINE, "returnULDTransactionForm.getReturnDate()---",
				transactionListVO.getStrRetDate());
		log.log(Log.FINE,
				"listULDTransactionSession.getReturnTransactionListVO()---",
				listULDTransactionSession
						.getReturnTransactionListVO());
		// For CRN mandatory check
		if(crnToDisplay!=null && crnToDisplay.length>0){
			for(int i=0;i<crnToDisplay.length;i++){
				if (crnToDisplay[i] == null
						|| crnToDisplay[i].length() == 0) {
					ErrorVO errorVO = new ErrorVO(
					"uld.defaults.transaction.modify.crn.mandatory");
					errorVO.setErrorDisplayType(ERROR);
					invocationContext.target = SAVE_FAILURE;
					invocationContext.addError(errorVO);
					return;
				}
			}
		}			
		// isCrnChanged flag is set true if there is change in CRN from the return Loan/Borrow screen 
		boolean isCrnChanged=false;
		String crnToSet=null;
		String crnPrefix=null;
		TransactionVO transactionVO=new TransactionVO();
		Collection<ULDTransactionDetailsVO> txnDetailsVOs=new ArrayList<ULDTransactionDetailsVO>();
		List<ULDTransactionDetailsVO> uldTxnVOs = new ArrayList<ULDTransactionDetailsVO>();
		int i=0;
		if(transactionListVO.getUldTransactionsDetails() !=null && transactionListVO.getUldTransactionsDetails().size()>0){
		for (ULDTransactionDetailsVO uldTransactionDetailsVO :  transactionListVO.getUldTransactionsDetails()) {
			log.log(Log.FINE, " \n insidddddee itr", transactionListVO.getUldTransactionsDetails());
			ULDTransactionDetailsVO vo=new ULDTransactionDetailsVO();
			if(!("").equalsIgnoreCase(crnToDisplay[i]) && crnToDisplay[i] != null){	
				 log.log(Log.FINE,
						" \n beforechecking  controlReceiptNumberPrefix",
						controlReceiptNumberPrefix);
				crnPrefix=controlReceiptNumberPrefix[i];
				  log.log(Log.FINE, " \n afterchecking  controlReceiptNumberPrefix");
				  String crn=crnToDisplay[i];
				  crnToSet=new StringBuffer(crnPrefix).append(crn).toString();
				  if(crnToSet.equals(uldTransactionDetailsVO.getControlReceiptNumber())){
					  //isCrnChanged=false;	
					  vo.setControlReceiptNumber(crnToSet);						  
				}else{
					  isCrnChanged=true;
					  vo.setControlReceiptNumber(crnToSet);	
					  uldTransactionDetailsVO.setControlReceiptNumberPrefix(crnPrefix);
					  uldTransactionDetailsVO.setCrnToDisplay(crn);
					  txnDetailsVOs.add(vo);
				  }				  	  
			  }		     
			  uldTxnVOs.add(vo);
			i++;
		}
		log.log(Log.FINE, "isCrnChanged--->", isCrnChanged);
		// Check for duplicate CRN only if there is change in CRN 
		if(isCrnChanged){
			boolean dupCrnFlag=false;
			// Check for Duplicate CRN from the screen 
			    if (uldTxnVOs != null && uldTxnVOs.size() > 0) {
					int len = uldTxnVOs.size();
					log.log(Log.FINE, "len is", len);
					for (int index = 0; index < len; index++) {
						String firstCRN = uldTxnVOs.get(index)
								.getControlReceiptNumber();
						for (int j = index + 1; j < len; j++) {
							String secondCRN = uldTxnVOs.get(j)
									.getControlReceiptNumber();
							if (firstCRN.equals(secondCRN)) {
								log.log(Log.FINE,"firstCRN and secondCRN entered are equal");
								dupCrnFlag = true;
								break;
							}
						}

					}

					log.log(Log.FINE, "dupCrnFlag -----------", dupCrnFlag);
					if (dupCrnFlag) {
						ErrorVO error = new ErrorVO(
								"uld.defaults.loanBorrowULD.msg.err.duplicateCRN");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						return;
					}
				}
			 Collection<String> crnNums=null;
			 transactionVO.setUldTransactionDetailsVOs(txnDetailsVOs);
			 
			 // Check for 0,1,2 prefix in the CRN number			 
			 int temp=0;
			 boolean prefixFlag=false;
			 for (ULDTransactionDetailsVO transactionDetailsVO : uldTxnVOs) {
				 String crnNum=transactionDetailsVO.getControlReceiptNumber();
				 String numPart=crnNum.substring(4,5);
				 log.log(Log.FINE, "numPart -----------", numPart);
				log.log(Log.FINE, "temp -----------", temp);
				if(Integer.parseInt(numPart)==(temp)){
					 log.log(Log.FINE, "EQUAL---");
				 }else{
					 log.log(Log.FINE, "NOT EQUAL---");
					 prefixFlag=true;
					 break;
				 }
				 temp++;
				 if(temp>2){
					 temp=0;
				 }
			 }
			
			 if(prefixFlag){
				 ErrorVO error = new ErrorVO(
				 	"uld.defaults.loanBorrowULD.msg.err.incorrectCRNprefix");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;
					return;
			 }
			 
			
			 
			 
			 
			// Check for similar UCR numbers 
			boolean similarCrnFlag = true;	
			HashSet<String> uniqueCRN=new HashSet<String>();
			double size=uldTxnVOs.size();
			double count=Math.ceil(size/3);
			for (ULDTransactionDetailsVO transactionDetailsVO : uldTxnVOs) {
				String crnNum=transactionDetailsVO.getControlReceiptNumber().substring(5,transactionDetailsVO.getControlReceiptNumber().length());
				uniqueCRN.add(crnNum);
			}
			double crnLen=uniqueCRN.size();
			log.log(Log.FINE, "uniqueCRN -----------", uniqueCRN);
			log.log(Log.FINE, "crnLen -----------", crnLen);
			log.log(Log.FINE, "count -----------", count);
			if(crnLen==count){
				similarCrnFlag=false;
			}
			log.log(Log.FINE, "similarCrnFlag -----------", similarCrnFlag);
			if(similarCrnFlag){
				ErrorVO error = new ErrorVO(
				"uld.defaults.loanBorrowULD.msg.err.similarCRN");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
			
			
			
			 //Check for Duplicate CRN from ULDTXNMST table
				try {
					String companyCode=logonAttributes.getCompanyCode();
					crnNums=uldDefaultsDelegate.checkForDuplicateCRN(companyCode,transactionVO);
					log.log(Log.FINE, "crnNums returned ------------", crnNums);
				} catch (BusinessDelegateException ex) {
					errors=handleDelegateException(ex);
					ex.getMessage();
				}
				
				if (crnNums != null && crnNums.size() > 0 ) {
					StringBuffer duplicates = new StringBuffer("");
					for (String duplicate : crnNums) {
						if (("").equals(duplicates.toString())) {
							duplicates.append(duplicate);
						} else {
							duplicates.append(" , ");
							duplicates.append(duplicate);
						}
					}
					ErrorVO error = new ErrorVO(
							"uld.defaults.loanBorrowULD.msg.err.duplicateCRNexists",
							new Object[] { duplicates.toString() });
					errors.add(error);
					invocationContext.addAllError(errors);										
					invocationContext.target = SAVE_FAILURE;
					return;
				}
		 }
		}
		//Added by Preet on 5 th Jan for newly added column CRN in Return Loan/Borrow screen --starts
		
		Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionListVO
				.getUldTransactionsDetails();
		int cnt = 0;
		if (uldTransactionDetailsVOs != null
				&& uldTransactionDetailsVOs.size() != 0) {
			for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
				uldTransactionDetailsVO.setReturnDate(transactionListVO
						.getReturnDate());
				uldTransactionDetailsVO.setReturnStationCode(transactionListVO
						.getReturnStationCode());
				log.log(Log.FINE,
						"****transactionListVO.getReturnStationCode()******",
						transactionListVO.getReturnStationCode());
				uldTransactionDetailsVO.setWaived(Double
						.parseDouble(waivedArray[cnt]));
				uldTransactionDetailsVO.setTaxes(Double
						.parseDouble(taxesArray[cnt]));
				uldTransactionDetailsVO.setOtherCharges(Double
						.parseDouble(otherChargesArray[cnt]));
				uldTransactionDetailsVO
						.setTransactionStatus(ULDTransactionDetailsVO.TO_BE_INVOICED);
				uldTransactionDetailsVO
						.setTotal((uldTransactionDetailsVO.getDemurrageAmount()
								+ uldTransactionDetailsVO.getTaxes() + uldTransactionDetailsVO
								.getOtherCharges())
								- uldTransactionDetailsVO.getWaived());
				if ("true".equalsIgnoreCase(damagedArray[cnt])) {
					uldTransactionDetailsVO.setDamageStatus("Y");
				} else {
					uldTransactionDetailsVO.setDamageStatus("N");
				}

				if (returnByArray[cnt] != null
						&& !("").equals(returnByArray[cnt])) {
					uldTransactionDetailsVO.setReturnedBy(returnByArray[cnt]);
				} else {
					uldTransactionDetailsVO.setReturnedBy(null);
				}

				/**
				 * to get Airline identifier for Login station
				 */
				AirlineValidationVO curOwnerVO = null;

				if (uldTransactionDetailsVO.getReturnedBy() != null
						&& !("").equals(uldTransactionDetailsVO.getReturnedBy())) {
					try {
						curOwnerVO = new AirlineDelegate().validateAlphaCode(
								logonAttributes.getCompanyCode(),
								uldTransactionDetailsVO.getReturnedBy());
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					if (errors != null && errors.size() > 0) {

						invocationContext.addAllError(errors);
						listULDTransactionSession
								.setReturnTransactionListVO(transactionListVO);
						returnULDTransactionForm
								.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
						/* added by Preet on 12 th Dec for Client UCR printing starts*/ 
						if(("L").equals(txnType)){
						invocationContext.target = SAVE_SUCCESS;
						}else if(("B").equals(txnType)){
							if(returnULDTransactionForm.getPrintUCR() !=null && "Y".equals(returnULDTransactionForm.getPrintUCR())){
								invocationContext.target = PRINT_BORROW_UCR;
							}else{
								invocationContext.target = SAVE_SUCCESS;
							}
						}
						/* added by Preet on 12 th Dec for Client UCR printing ends*/ 
						//invocationContext.target = SAVE_SUCCESS;		
						return;
					}
				}
				int curCode = 0;

				if (curOwnerVO != null) {
					curCode = curOwnerVO.getAirlineIdentifier();
				}
				log
						.log(
								Log.FINE,
								"\n\n\n\n FOR each uldTransactionDetailsVO.getReturnedBy() ",
								uldTransactionDetailsVO.getReturnedBy());
				log.log(Log.FINE, "\n\n\n\n FOR each id", curCode);
				uldTransactionDetailsVO.setReturnRemark(remarksArray[cnt]);
				if ("L".equalsIgnoreCase(uldTransactionDetailsVO
						.getTransactionType())) {
					uldTransactionDetailsVO.setCurrOwnerCode(curCode);
				}
				if ("B".equalsIgnoreCase(uldTransactionDetailsVO
						.getTransactionType())) {
					uldTransactionDetailsVO.setCurrOwnerCode(curCode);
					// uldTransactionDetailsVO.setOperationalAirlineIdentifier(curCode);

				}
				if (("D").equals(uldTransactionDetailsVO.getDamageStatus())) {
					if (flag == 0) {
						uldDmg = uldTransactionDetailsVO.getUldNumber();
						flag = 1;
					} else {
						uldDmg = new StringBuilder(uldDmg).append(",").append(
								uldTransactionDetailsVO.getUldNumber())
								.toString();
					}
				}
				LocalDate ldate = new LocalDate(logonAttributes
						.getAirportCode(), Location.ARP, true);
				//Added by Preet on Nov 28th for bug fix starts
				uldTransactionDetailsVO.setLastUpdateTime(uldTransactionDetailsVO.getLastUpdateTime());
				//Added by Preet on Nov 28th for bug fix ends
				uldTransactionDetailsVO.setLastUpdateUser(logonAttributes
						.getUserId());
				//Added by Preet on 5 th Jan for newly added column CRN in Return Loan/Borrow screen --starts
				String prefix=controlReceiptNumberPrefix[cnt];
				String crn=crnToDisplay[cnt];
				uldTransactionDetailsVO.setControlReceiptNumber(new StringBuffer(prefix).append(crn).toString());
				//Added by Preet on 5 th Jan for newly added column CRN in Return Loan/Borrow screen --ends
				uldTxnDetailsVOs.add(uldTransactionDetailsVO);
				cnt++;
			}
		}
		/**
		 * to get Airline identifier for Login station
		 */
		AirlineValidationVO curOwnerVO = null;
		try {
			curOwnerVO = new AirlineDelegate().validateAlphaCode(
					logonAttributes.getCompanyCode(), logonAttributes
							.getOwnAirlineCode());
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			listULDTransactionSession
					.setReturnTransactionListVO(transactionListVO);
			returnULDTransactionForm
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			//invocationContext.target = SAVE_SUCCESS;
			/* added by Preet on 12 th Dec for Client UCR printing starts*/ 
			if(("L").equals(txnType)){
			invocationContext.target = SAVE_SUCCESS;
			}else if(("B").equals(txnType)){
				if(returnULDTransactionForm.getPrintUCR() !=null && "Y".equals(returnULDTransactionForm.getPrintUCR())){
					invocationContext.target = PRINT_BORROW_UCR;
				}else{
					invocationContext.target = SAVE_SUCCESS;
				}
			}
			/* added by Preet on 12 th Dec for Client UCR printing ends*/ 
			return;
		}
		int curCode = 0;

		if (curOwnerVO != null) {
			curCode = curOwnerVO.getAirlineIdentifier();
		}
		Collection<AccessoryTransactionVO> accDetailsVOs = new ArrayList<AccessoryTransactionVO>();
		Collection<AccessoryTransactionVO> accTransactionDetailsVOs = transactionListVO
				.getAccessoryTransactions();
		cnt = 0;
		if (accTransactionDetailsVOs != null
				&& accTransactionDetailsVOs.size() != 0) {
			for (AccessoryTransactionVO accessoryTransactionVO : accTransactionDetailsVOs) {
				accessoryTransactionVO.setQuantity(Integer
						.parseInt(quantityArray[cnt]));
				
				AirlineValidationVO currentOwnerVO = null;
				try {
					currentOwnerVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(),accessoryTransactionVO.getFromPartyCode() );
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				
				accessoryTransactionVO.setCurrOwnerCode(currentOwnerVO.getAirlineIdentifier());
				LocalDate ldate = new LocalDate(logonAttributes
						.getAirportCode(), Location.ARP, false);
				accessoryTransactionVO.setLastUpdateTime(ldate);
				accessoryTransactionVO.setLastUpdateUser(logonAttributes
						.getUserId());
				accDetailsVOs.add(accessoryTransactionVO);
				cnt++;
			}
		}

		returnULDTransactionForm.setDamageULD(uldDmg);
		transactionListVO.setUldTransactionsDetails(uldTxnDetailsVOs);
		transactionListVO.setAccessoryTransactions(accDetailsVOs);

		log.log(Log.FINE, "\n\n\n\n transactionListVO  BEFORE SAVE RETURN",
				transactionListVO);
		/*
		 * Construct lock vos for implicit locking
		 */
		Collection<LockVO> locks = prepareLocksForSave(transactionListVO);
		log.log(Log.FINE, "LockVO for implicit check", locks);
		/* Added by A-2412 on 25 oct for UCR printing */
		if( returnULDTransactionForm.getPrintUCR() !=null && ("Y").equals(returnULDTransactionForm.getPrintUCR())){
			transactionListVO.setToBePrinted(true);
		}
		else{
			transactionListVO.setToBePrinted(false);
		}
		/* Addition  by A-2412 on 25 oct for UCR printing ends*/
		
		try {
			uldDefaultsDelegate.saveReturnTransaction(transactionListVO,locks);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			listULDTransactionSession
					.setReturnTransactionListVO(transactionListVO);
		} else {

			// commented cause it calls damage report screen

			/*
			 * if(! "".equals(uldDmg)) {
			 *
			 * ErrorVO errorVO = new
			 * ErrorVO("uld.defaults.transaction.damageuldsfound");
			 * errorVO.setErrorDisplayType(WARNING); errors.add(errorVO);
			 * invocationContext.addAllError(errors);
			 * listULDTransactionSession.setReturnTransactionListVO(transactionListVO);
			 * }else
			 */

			returnULDTransactionForm.setCloseFlag(FLAG_YES);
			listULDTransactionSession.setReturnTransactionListVO(null);

		}

		if (returnULDTransactionForm.getPageurl() != null
				&& ("fromulderrorlog").equals(returnULDTransactionForm.getPageurl())) {
			ULDFlightMessageReconcileDetailsVO toReconcile = listULDTransactionSession
					.getULDFlightMessageReconcileDetailsVO();
			if (("B").equals(transactionListVO.getTransactionType())) {
				toReconcile.setToBeAvoidedFromValidationCheck(true);
			}
			log.log(Log.FINE, "\n \n toReconcile", toReconcile);
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				log.log(Log.FINE, "\n reconcile  delegate ");
				new ULDDefaultsDelegate().reconcileUCMULDError(toReconcile);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
			}
			returnULDTransactionForm.setPageurl("close");
			uldErrorLogSession.setPageURL("fromreturnuld");
			listULDTransactionSession.removeAllAttributes();
//			invocationContext.target = SAVE_ULDERRORLOG;
			/* added by Preet on 12 th Dec for Client UCR printing starts*/ 
			if(("L").equals(txnType)){
			invocationContext.target = SAVE_ULDERRORLOG;
			}else if(("B").equals(txnType)){
				if(returnULDTransactionForm.getPrintUCR() !=null && "Y".equals(returnULDTransactionForm.getPrintUCR())){
					invocationContext.target = PRINT_BORROW_UCR;
				}else{
					invocationContext.target = SAVE_ULDERRORLOG;
				}
			}
			/* added by Preet on 12 th Dec for Client UCR printing ends*/ 
			return;
		} else if (PAGE_URL.equals(returnULDTransactionForm.getPageurl())) {

			log.log(Log.FINE,"Inside scm page url");
			SCMULDErrorLogSession scmSession = getScreenSession(MODULE_NAME,
					SCREENID_SCMERRORLOG);
			ULDSCMReconcileDetailsVO reconcileDetailsVO = listULDTransactionSession
					.getULDSCMReconcileDetailsVO();
			Collection<ULDSCMReconcileDetailsVO> scmUldDetails = new ArrayList<ULDSCMReconcileDetailsVO>();
			scmUldDetails.add(reconcileDetailsVO);
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				new ULDDefaultsDelegate()
						.removeErrorCodeForULDsInSCM(scmUldDetails);
			} catch (BusinessDelegateException exception) {
				exception.getMessage();
				error = handleDelegateException(exception);
			}
			scmSession.setPageUrl("fromreturnuld");
			returnULDTransactionForm.setPageurl("scm_close");
			listULDTransactionSession.removeAllAttributes();
			//invocationContext.target = SAVE_SCMERRORLOG;
			/* added by Preet on 12 th Dec for Client UCR printing starts*/ 
			if(("L").equals(txnType)){
			invocationContext.target = SAVE_SCMERRORLOG;
			}else if(("B").equals(txnType)){
				if(returnULDTransactionForm.getPrintUCR() !=null && "Y".equals(returnULDTransactionForm.getPrintUCR())){
					invocationContext.target = PRINT_BORROW_UCR;
				}else{
					invocationContext.target = SAVE_SCMERRORLOG;
				}
			}
			/* added by Preet on 12 th Dec for Client UCR printing ends*/ 

			return;

		}

		returnULDTransactionForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
//		invocationContext.target = SAVE_SUCCESS;
		/* added by Preet on 12 th Dec for Client UCR printing starts*/ 
		if(("L").equals(txnType)){
		invocationContext.target = SAVE_SUCCESS;
		}else if(("B").equals(txnType)){
			if(returnULDTransactionForm.getPrintUCR() !=null && "Y".equals(returnULDTransactionForm.getPrintUCR())){
				invocationContext.target = PRINT_BORROW_UCR;
			}else{
				invocationContext.target = SAVE_SUCCESS;
			}
		}
		log
				.log(
						Log.FINE,
						"SaveReturnULDTransactionCommand--listULDTransactionSession.getUldNumbersSelected()---",
						listULDTransactionSession.getUldNumbersSelected());
		log.exiting("SaveReturnULDTransactionCommand", "execute");

	}
	/*
	 * Added by Ayswarya
	 */
	private Collection<LockVO> prepareLocksForSave(
			TransactionListVO transactionListVO) {
		log.log(Log.FINE, "\n prepareLocksForSave------->>", transactionListVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<LockVO> locks =null; 
		Collection<ULDTransactionDetailsVO> uldDetailsVOs = transactionListVO
				.getUldTransactionsDetails();
		if (uldDetailsVOs != null && uldDetailsVOs.size() > 0) {
			locks=new ArrayList<LockVO>();
			for (ULDTransactionDetailsVO uldVO : uldDetailsVOs) {
				ULDLockVO lock = new ULDLockVO();
				lock.setAction(LockConstants.ACTION_RETURNULDTRANSACTION);
				lock.setClientType(ClientType.WEB);
				lock.setCompanyCode(logonAttributes.getCompanyCode());
				lock.setScreenId(SCREEN_ID);
				lock.setStationCode(logonAttributes.getStationCode());
				lock.setUldNumber(uldVO.getUldNumber());
				lock.setDescription(uldVO.getUldNumber());
				lock.setRemarks(uldVO.getUldNumber());
				log.log(Log.FINE, "\n lock------->>", lock);
				locks.add(lock);
			}
		}
		return locks;
	}


}
