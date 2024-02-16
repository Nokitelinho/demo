/*
 * TransactionDetailsCommand.java Created on May 10,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
/**
 * This command class is for listing transaction details of ULDs
 * 
 * @author a-3093
 *
 */
public class TransactionDetailsCommand extends BaseCommand {

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
	private static final String SCREEN_ID_MVT = "uld.defaults.loanborrowdetailsenquiry";

	/**
	 * Target if success
	 */
	private static final String LIST_SUCCESS = "list_success";
	
	private static final String LIST_FAILURE = "list_failure";
	
	private static final String ALL = "ALL";
	
	private static final String TXNSTATUS_ONETIME = "uld.defaults.transactionStatus";

	
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("TransactiondetailsCommand", "execute");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO(); 
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		ListULDMovementForm listULDTransactionForm = (ListULDMovementForm) invocationContext.screenModel;
		
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID_MVT);
		
		String companyCode = logonAttributes.getCompanyCode();
		
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues(companyCode);
		
		Collection<OneTimeVO> txnStatus = oneTimeValues.get(TXNSTATUS_ONETIME);
		log.log(Log.FINE, "****txnStatus  OneTime******", txnStatus);
		listULDTransactionSession.setTxnStatus(txnStatus);
		
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		
		listULDTransactionSession.setTransactionFilterVO(transactionFilterVO);
		
		//errors = validateForm(listULDTransactionForm, listULDTransactionSession, companyCode);		
		
		transactionFilterVO = makeFilter(listULDTransactionForm,
				listULDTransactionSession, companyCode);

		TransactionListVO transactionListVO = new TransactionListVO();
		try {
			transactionListVO = uldDefaultsDelegate
					.findTransactionHistory(transactionFilterVO);
			log.log(Log.FINE, "TransactionListVO ~~~~~~~~~~~~~~~~~",
					transactionListVO);
			
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		if(transactionListVO == null )
			  
		  {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.uldmovement.transaction.noenquiriesfound");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			
		  }
		
		if(transactionListVO.getTransactionDetailsPage()!=null && transactionListVO.getTransactionDetailsPage().size()>0){
			listULDTransactionForm.setListStatus("N");
		}
		if ((transactionListVO.getTransactionDetailsPage()== null || transactionListVO
				.getTransactionDetailsPage().size() == 0))
				 {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.uldmovement.transaction.noenquiriesfound");
			errors.add(errorVO);
			invocationContext.addAllError(errors);			
			invocationContext.target = LIST_FAILURE  ;
		}
		listULDTransactionSession.setTransactionListVO(transactionListVO);
		listULDTransactionSession.setTransactionFilterVO(transactionFilterVO);
		  
		listULDTransactionForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		
		invocationContext.target = LIST_SUCCESS;

		log.exiting("TransactiondetailsCommand", "execute");

	}
		
	private TransactionFilterVO makeFilter(
			ListULDMovementForm listULDTransactionForm,
			ListULDTransactionSession listULDTransactionSession,
			String companyCode) {
		log.entering("TransactiondetailsCommand", "makeFilter");

		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();
		transactionFilterVO.setCompanyCode(companyCode);
		
		String uldNumber = listULDTransactionForm.getUldNumber().toUpperCase();
		if (uldNumber != null && uldNumber.trim().length() > 0) {
			
			transactionFilterVO.setUldNumber(uldNumber.trim());
			transactionFilterVO.setUldTypeCode(uldNumber.substring(0,3));
		} else {
			transactionFilterVO.setUldNumber("");
			transactionFilterVO.setUldTypeCode("");
		}
		if (listULDTransactionForm.getFromDate() != null
				&& listULDTransactionForm.getFromDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(listULDTransactionForm.getFromDate(),"dd-MMM-yyyy")) {
				LocalDate frmDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				frmDate.setDate(listULDTransactionForm.getFromDate().trim());
				transactionFilterVO.setTxnFromDate(frmDate);
			}

		} else {
			transactionFilterVO.setTxnFromDate(null);
		}
			
		if (listULDTransactionForm.getToDate() != null && listULDTransactionForm.getToDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(listULDTransactionForm.getToDate(), "dd-MMM-yyyy")) {
				LocalDate toDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				toDate.setDate(listULDTransactionForm.getToDate().trim());
				transactionFilterVO.setTxnToDate(toDate);
			}

		} else {
			transactionFilterVO.setTxnToDate(null);
		}
		
		String toDisplayPage = listULDTransactionForm.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);	
		transactionFilterVO.setPageNumber(displayPage);
		transactionFilterVO.setTransactionType(ALL);
		log.log(Log.INFO, "FILTER VO IS --------------->>>",
				transactionFilterVO);
		log.exiting("TransactiondetailsCommand", "makeFilter");
		return transactionFilterVO;
	}
	
	
	private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();    	
    	
    	parameterTypes.add(TXNSTATUS_ONETIME);
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
	
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(String companyCode){
		log.entering("ScreenLoadCommand","getOneTimeValues");
		
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					companyCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}


}
