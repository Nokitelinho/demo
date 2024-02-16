/*
 * ReturnULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
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
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 * Modified by A-3278
 * For the QF CR 1015 on 30Jun08
 *
 */
public class ReturnULDTransactionCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");
    
	private static final String SCREENLOAD_FAILURE = "screenload_failure"; 
	private static final String SCREENLOAD_SUCCESS = "screenload_success"; 
    
	private static final String SCREENLOAD_SCM = "save_scmerrorlog";
    
	private static final String SAVE_ULDERRORLOG = "save_ulderrorlog";
   
	private static final String MODULE_NAME = "uld.defaults";
    
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";
    
    private static final String PAGE_URL_SCM_RECONCILE = "fromScmUldReconcile";
    
    private static final String PAGE_URL_UCM_FOR_FLIGHT="fromulderrorlogforflight";

    private static final String BLANK = "";
    
    private static final String SCREENID_SCMERRORLOG = "uld.defaults.scmulderrorlog";
    
    private static final String SCREENID_ULDERRORLOG = "uld.defaults.ulderrorlog";
    
    private static final String LOAN = "L";
    
    private static final String BORROW = "B";
    
    private static final String AGENT = "G";
    
    private static final String AIRLINE = "A";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ReturnULDTransactionCommand", "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		TransactionListVO transactionListVO = new TransactionListVO();
		if(PAGE_URL_SCM_RECONCILE.equals(listULDTransactionSession.getPageURL())){
			 transactionListVO = listULDTransactionSession.getReturnTransactionListVO();
			 
		}else if(PAGE_URL_UCM_FOR_FLIGHT.equals(listULDTransactionSession.getPageURL())){
			 transactionListVO = listULDTransactionSession.getReturnTransactionListVO();
		}else{
		
		 transactionListVO = listULDTransactionSession
				.getTransactionListVO();
		}
		log.log(Log.FINE, "transactionListVO ****** new***** ",
				transactionListVO);
		transactionListVO
				.setReturnStationCode(logonAttributes.getAirportCode());

		LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, true);
		String retDate = currentdate.toDisplayDateOnlyFormat().toString();
		String retTime = currentdate.toDisplayTimeOnlyFormat().toString();
		transactionListVO.setReturnDate(currentdate);
		transactionListVO.setStrRetDate(retDate);
		transactionListVO.setStrRetTime(retTime);

		Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
		Page<ULDTransactionDetailsVO> uldTransactionDetailsVOs = null;
		if (transactionListVO.getTransactionDetailsPage() != null) {
			uldTransactionDetailsVOs = transactionListVO
					.getTransactionDetailsPage();
		}
		
		int cnt = 0;
		
		log.log(Log.FINE, "uldTransactionDetailsVOs is : - ********* ",
				uldTransactionDetailsVOs);
		String[] primaryKey = listULDTransactionForm.getUldDetails();
		log.log(Log.FINE, "primaryKey is : - ********* ", primaryKey);
		/*log.log(Log.FINE, "primaryKeylength is : - ********* "
				+ primaryKey.length);*/
		if ((primaryKey != null && primaryKey.length > 0)) {
			log.log(Log.FINE, "entering in loop -- primaryKey");
			int index = 0;
			int primaryKeyLen = primaryKey.length;
			if (uldTransactionDetailsVOs != null
					&& uldTransactionDetailsVOs.size() != 0) {
				log.log(Log.FINE, "entering in uldTransactionDetailsVOs$$$$$$",
						uldTransactionDetailsVOs);
				for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
					index++;
					String primaryKeyFromVO = new StringBuilder(
							uldTransactionDetailsVO.getUldNumber()).append(
							uldTransactionDetailsVO.getTransactionRefNumber())
							.append(index).toString();
					if ((cnt < primaryKeyLen)
							&& (primaryKeyFromVO.trim())
									.equalsIgnoreCase(primaryKey[cnt].trim())) {
					
					

					String agentCrnNumber = uldTransactionDetailsVO
							.getControlReceiptNumber();
					if (agentCrnNumber != null && agentCrnNumber.length() > 4) {
						uldTransactionDetailsVO
								.setControlReceiptNumberPrefix(agentCrnNumber
										.substring(0, 4));
						uldTransactionDetailsVO.setCrnToDisplay(agentCrnNumber
								.substring(4, agentCrnNumber.length()));
					}

					uldTransactionDetailsVO.setReturnDate(transactionListVO
							.getReturnDate());
					log
							.log(
									Log.FINE,
									"entering in uldTransactionDetailsVOs--return date$$$$$$",
									uldTransactionDetailsVO.getReturnDate());
				uldTransactionDetailsVO
							.setReturnStationCode(transactionListVO
									.getReturnStationCode());
					
					log.log(Log.INFO, "uld number", uldTransactionDetailsVO.getUldNumber());
					uldTransactionDetailsVO.setUldType(uldTransactionDetailsVO
							.getUldNumber().substring(0, 3));		
					
						
					uldTransactionDetailsVO.setReturnedBy(uldTransactionDetailsVO.getToPartyCode());
					uldTransactionDetailsVO.setCurrOwnerCode(uldTransactionDetailsVO.getFromPartyIdentifier());
					uldTxnDetailsVOs.add(uldTransactionDetailsVO);
						cnt++;

					}
				}
			}
		}
		
		// Added by Preet on 1 Sept for bug ULD 708 starts
		String crn="";
		String crnNumber = null;
		
		for (ULDTransactionDetailsVO uldTxnVo : uldTxnDetailsVOs) {
			if (LOAN.equals(uldTxnVo.getTransactionType())) {
				crn = uldTxnVo.getControlReceiptNumber();
			}else if (BORROW.equals(uldTxnVo.getTransactionType())) {
				crn = crnNumber;				
			}
			AirlineValidationVO toOwnerVO = null;
			String toPrtyCode = uldTxnVo.getToPartyCode();
			String airlineID = null;
			// In case of Return Loan --> no auto generation of CRN num required 
			if (LOAN.equals(uldTxnVo.getTransactionType()) && AIRLINE.equals(uldTxnVo.getPartyType())) {
				if (toPrtyCode != null && !("".equals(toPrtyCode))) {
					try {
						toOwnerVO = new AirlineDelegate().validateAlphaCode(
								logonAttributes.getCompanyCode(), toPrtyCode
										.toUpperCase());
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
				}
				if (toOwnerVO != null) {
					airlineID = toOwnerVO.getNumericCode() + "-";
					uldTxnVo.setControlReceiptNumberPrefix(airlineID);
					
					if (crn != null && crn.length() > 4) {
						uldTxnVo.setCrnToDisplay(crn.substring(4, crn.length()));
					} else{
						uldTxnVo.setCrnToDisplay("");
					}
				}				
			//In case of Return Borrow --> CRN Number is auto generated 
			} /* Commented by A-3415 for ICRD-114538
			else if (BORROW.equals(uldTxnVo.getTransactionType()) && AIRLINE.equals(uldTxnVo.getPartyType() )) {
				String controlRecieptNumber = null;
				if (crn == null) {
					log.log(Log.ALL, "crn number is null");
					try {
						controlRecieptNumber = new ULDDefaultsDelegate()
								.findCRNForULDTransaction(logonAttributes
										.getCompanyCode(),logonAttributes.getOwnAirlineCode());
						if (controlRecieptNumber != null
								&& controlRecieptNumber.length() > 0) {

							String number = controlRecieptNumber.substring(
									controlRecieptNumber.length() - 7,
									controlRecieptNumber.length());
							airlineID = new StringBuilder(controlRecieptNumber.substring(0,4)).append("1").toString();
							log.log(Log.ALL, "airlineID issss", airlineID);
							uldTxnVo.setControlReceiptNumberPrefix(airlineID);
							uldTxnVo.setCrnToDisplay(number);
							crnNumber = new StringBuilder(airlineID).append(
									number).toString();
						}

					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
				} else {
					log.log(Log.FINE, "crn is---", crn);
					String crnNumberPart = crn.substring(4,crn.length());					
					int count = Integer.parseInt(crnNumberPart.substring(0, 1));
					String number = crnNumberPart.substring(1, crnNumberPart
							.length());
					
					log.log(Log.FINE, "count is---", count);
					if (count < 3) {
						count++;
						airlineID = new StringBuilder(crn.substring(0,4)).append(count)
						.toString();
					} else {
						try {
							controlRecieptNumber = new ULDDefaultsDelegate()
									.findCRNForULDTransaction(logonAttributes
											.getCompanyCode(),logonAttributes.getOwnAirlineCode());
							if (controlRecieptNumber != null && controlRecieptNumber.length() > 0) {								
								airlineID = new StringBuilder(controlRecieptNumber.substring(0,4)).append("1").toString();
								number = controlRecieptNumber.substring(controlRecieptNumber.length() - 7, controlRecieptNumber
										.length());								
							}
						} catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
					}					
					uldTxnVo.setControlReceiptNumberPrefix(airlineID);
					uldTxnVo.setCrnToDisplay(number);
					crnNumber = new StringBuilder(airlineID).append(number).toString();
					
				}
		    
			}*/else if (AGENT.equals(uldTxnVo.getPartyType())){
				if (crn != null && crn.trim().length() > 0) {
					uldTxnVo.setControlReceiptNumberPrefix(crn.substring(0, 4));
					uldTxnVo.setCrnToDisplay(crn.substring(4, crn.length()));
				} 
			}			
			else{
				if (crn != null && crn.trim().length() > 0) {
					uldTxnVo.setControlReceiptNumberPrefix(crn.substring(0, 4));
					uldTxnVo.setCrnToDisplay(crn.substring(4, crn.length()));
				} 
			}
			/*
			 * modified the code by a-3278 for 28897 on 06Jan09
			 * a new CRN is maintained to save the latest and the old CRN seperately
			 * when the return txn happens returnCRN need to b updated
			 */
			//uldTxnVo.setControlReceiptNumber(new StringBuilder(uldTxnVo.getControlReceiptNumberPrefix()).append(uldTxnVo.getCrnToDisplay()).toString());
			//Added if check as part of ICRD-305544
			if(uldTxnVo.getControlReceiptNumberPrefix()!=null && uldTxnVo.getCrnToDisplay()!=null) {
			uldTxnVo.setReturnCRN(new StringBuilder(uldTxnVo.getControlReceiptNumberPrefix()).append(uldTxnVo.getCrnToDisplay()).toString());
			}
			log.log(Log.FINE, "uldTxnVo.getReturnCRN()----------->", uldTxnVo.getReturnCRN());
			
		}	
		//	Added by Preet on 1 Sept for bug ULD 708 ends
		
		
		if(PAGE_URL_SCM_RECONCILE.equals(listULDTransactionSession.getPageURL()) ||
				PAGE_URL_UCM_FOR_FLIGHT.equals(listULDTransactionSession.getPageURL())){

			log.log(Log.FINE, "entering in loop -- primaryKey");
		
			if (uldTransactionDetailsVOs != null
					&& uldTransactionDetailsVOs.size() != 0) {
				log.log(Log.FINE, "entering in uldTransactionDetailsVOs$$$$$$",
						uldTransactionDetailsVOs);
				for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
	
					String agentCrnNumber = uldTransactionDetailsVO
							.getControlReceiptNumber();
					if (agentCrnNumber != null && agentCrnNumber.length() > 4) {
						uldTransactionDetailsVO
								.setControlReceiptNumberPrefix(agentCrnNumber
										.substring(0, 4));
						uldTransactionDetailsVO.setCrnToDisplay(agentCrnNumber
								.substring(4, agentCrnNumber.length()));
					}

					uldTransactionDetailsVO.setReturnDate(transactionListVO
							.getReturnDate());
					log
							.log(
									Log.FINE,
									"entering in uldTransactionDetailsVOs--return date$$$$$$",
									uldTransactionDetailsVO.getReturnDate());
				uldTransactionDetailsVO
							.setReturnStationCode(transactionListVO
									.getReturnStationCode());
					
					log.log(Log.INFO, "uld number", uldTransactionDetailsVO.getUldNumber());
					uldTransactionDetailsVO.setUldType(uldTransactionDetailsVO
							.getUldNumber().substring(0, 3));		
					
						
					uldTransactionDetailsVO.setReturnedBy(uldTransactionDetailsVO.getToPartyCode());
					uldTransactionDetailsVO.setCurrOwnerCode(uldTransactionDetailsVO.getFromPartyIdentifier());
					uldTxnDetailsVOs.add(uldTransactionDetailsVO);
						
				}
			}
		
		}
		
			/*
			 * Save the calculated charges and the reurn details
			 */
			Collection<ULDTransactionDetailsVO> uldTxnVos = new ArrayList<ULDTransactionDetailsVO>();
			log.log(Log.INFO, "uldTxnDetailsVOs*******", uldTxnDetailsVOs);
				for (ULDTransactionDetailsVO txnVO : uldTxnDetailsVOs) {
					txnVO
							.setTransactionStatus(ULDTransactionDetailsVO.TO_BE_INVOICED);
					txnVO
							.setTotal((txnVO.getDemurrageAmount()
									+ txnVO.getTaxes() + txnVO.getOtherCharges())
									- txnVO.getWaived());
					uldTxnVos.add(txnVO);
					transactionListVO.setTransactionType(txnVO.getTransactionType());
				}
			
			transactionListVO.setUldTransactionsDetails(uldTxnVos);
			/*
			 * Construct lock vos for implicit locking
			 */
			
			
			
			//added by a-3045 for CR QF1015 starts
			Collection<AccessoryTransactionVO> accDetailsVOs = new ArrayList<AccessoryTransactionVO>();
			// Author a-5125 for ICRD-22254 
			Collection<AccessoryTransactionVO> accDetailsTempVOs = new ArrayList<AccessoryTransactionVO>();
			Collection<AccessoryTransactionVO> accTransactionDetailsVOs = transactionListVO
					.getAccessoryTransactions();
			String[] quantityArray = listULDTransactionForm.getReturnQuantity();	
			cnt = 0;
			int index=0;
			// Author a-5125 for ICRD-22254 starts used for Quantity exceeds check
			boolean quntityExceeds=false;
			if (accTransactionDetailsVOs != null
					&& accTransactionDetailsVOs.size() != 0) {
				for (AccessoryTransactionVO accessoryTransactionVO : accTransactionDetailsVOs) {
					if (quantityArray != null && quantityArray[index] != null
							&& !("".equals(quantityArray[index]))) {
						// Author A-5125 added for ICRD-22254
						if (accessoryTransactionVO.getQuantity() < (Integer
								.parseInt(quantityArray[index]))) {
							if (BORROW.equalsIgnoreCase(accessoryTransactionVO
									.getTransactionType())) {
								quntityExceeds = true;
							}
						}
						accessoryTransactionVO.setReturnQuntity(quantityArray[index]);
						accDetailsTempVOs.add(accessoryTransactionVO);
					}
					index++;
				}
			}
			if(quntityExceeds){
				errors.add(new ErrorVO("uld.defaults.returndmorethan.available"));
			}
			if (errors.size() > 0 && quntityExceeds ) {
				transactionListVO.setAccessoryTransactions(accDetailsTempVOs);
				listULDTransactionSession.setTransactionListVO(transactionListVO);
				log
						.log(
								Log.INFO,
								"The return quantity cannot greater than the Available qunatity*******",
								uldTxnDetailsVOs);
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
			// Author a-5125 for ICRD-22254 ends
			if (accTransactionDetailsVOs != null
					&& accTransactionDetailsVOs.size() != 0) {
				for (AccessoryTransactionVO accessoryTransactionVO : accTransactionDetailsVOs) {
					//Null check Added as part of bug 107177 by A-3767 on 25Feb11
					if(quantityArray!=null && !("").equals(quantityArray[cnt]))
					{
						accessoryTransactionVO.setQuantity(Integer.parseInt(quantityArray[cnt]));
					}
					else{
						accessoryTransactionVO.setQuantity(0);
					}
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
					//changed by a-3045 for bug 20362 starts,
					//this is to set the transaction type if we are listing accessories alone,not ulds.
					if(transactionListVO.getTransactionType() == null){
						transactionListVO.setTransactionType(accessoryTransactionVO.getTransactionType());
					}
					//changed by a-3045 for bug 20362 ends
				}// end for loop
			}//end if 
			
			transactionListVO.setAccessoryTransactions(accDetailsVOs);		
			//added by a-3045 for CR QF1015 ends
			Collection<LockVO> locks = prepareLocksForSave(transactionListVO);
			log.log(Log.FINE, "LockVO for implicit check", locks);
			try {
				uldDefaultsDelegate.saveReturnTransaction(transactionListVO,
						locks);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
			}
			invocationContext.addError(new ErrorVO("uld.defaults.returnuld.returnedsuccessfully"));
	    	invocationContext.target = SCREENLOAD_SUCCESS;
	    	
		listULDTransactionSession.setTransactionListVO(transactionListVO);

		listULDTransactionForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		if(PAGE_URL_SCM_RECONCILE.equals(listULDTransactionSession.getPageURL())){
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
			
			listULDTransactionSession.removeAllAttributes();
			invocationContext.target = SCREENLOAD_SCM;
			return;
	    }else if(PAGE_URL_UCM_FOR_FLIGHT.equals(listULDTransactionSession.getPageURL())){
	    	ULDErrorLogSession uldErrorLogSession = (ULDErrorLogSession) getScreenSession(
					MODULE_NAME, SCREENID_ULDERRORLOG);
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
			uldErrorLogSession.setPageURL("fromreturnuld");
			listULDTransactionSession.removeAllAttributes();
			invocationContext.target = SAVE_ULDERRORLOG;
			
			return;
		
	    }
		invocationContext.target = SCREENLOAD_SUCCESS;

		log.exiting("ReturnULDTransactionCommand", "execute");

	}


	
	/**
	 * 
	 * @param transactionListVO		 
	 * @return Collection<LockVO>
	 */
	private Collection<LockVO> prepareLocksForSave(
			TransactionListVO transactionListVO) {
		log.log(Log.FINE, "\n prepareLocksForSave------->>", transactionListVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<LockVO> locks = null;
		Collection<ULDTransactionDetailsVO> uldDetailsVOs = transactionListVO
				.getUldTransactionsDetails();
		if (uldDetailsVOs != null && uldDetailsVOs.size() > 0) {
			locks = new ArrayList<LockVO>();
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
