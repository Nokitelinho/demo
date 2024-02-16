/*
 * FindULDNumberLovCommand.java  Created on Feb 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import  com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ULDNumberLovForm;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author 
 * Class to find the airLineLov
 */
public class FindULDNumberLovCommand extends BaseCommand {

	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD");

	private static final String ULDNUMBERLOV_SUCCESS="uldNumberLov_success";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";

	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ULDNumberLovForm uldNumberLovForm= (ULDNumberLovForm)invocationContext.screenModel;

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		String uldNumber = uldNumberLovForm.getCode().toUpperCase();
		String station = uldNumberLovForm.getTxnStation();
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		log.log(Log.FINE, "\n\n\n\n transactionVO---> ", transactionVO);
		ULDDefaultsDelegate uldDefaultsDelegate =  new ULDDefaultsDelegate();
		//Collection<String> uldNumbers = new 	ArrayList<String>();
		//Added by A-2412 for pagination
		int displayPage=Integer.parseInt(uldNumberLovForm.getDisplayPage());
		Page<String> uldNumbers=null;
		// ends
		AirlineValidationVO curOwnerVO = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		String fromPrtyCode ="";
		// Added by A-3268 for Bug 101907 starts
		if(transactionVO!=null){
			fromPrtyCode = transactionVO.getFromPartyCode().toUpperCase();
			if("R".equals(transactionVO.getTransactionType()) && !"A".equals(transactionVO.getPartyType())){
				fromPrtyCode = transactionVO.getToPartyCode().toUpperCase();
			}
		}
		// Added by A-3268 for Bug 101907 ends
		try{
			/**
    		 * to get  Airline identifier for Login station
    		 */

		if(transactionVO!=null){
				// Modified by A-3268 for Bug 101907
				curOwnerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),fromPrtyCode);
    	 
			log.log(Log.FINE, "curOwnerVO>>>>>>>>>>>>>>>", curOwnerVO);
			//added by a-3278 for bug 19133
			if(station != null){
				uldNumbers = uldDefaultsDelegate.findStationUlds(
						logonAttributes.getCompanyCode(),
						uldNumber,
						station.toUpperCase(),
						transactionVO.getTransactionType(),
						curOwnerVO.getAirlineIdentifier(),displayPage);
			}else{
				uldNumbers = uldDefaultsDelegate.findStationUlds(
						logonAttributes.getCompanyCode(),
						uldNumber,
						logonAttributes.getAirportCode(),
						transactionVO.getTransactionType(),
						curOwnerVO.getAirlineIdentifier(),displayPage);
			}
		//a-3278 ends
		}
		}catch(BusinessDelegateException ex){
			ex.getMessage();
			error = handleDelegateException(ex);
		}
		/*String[] uld = new String[uldNumbers.size()];
		int val = 0;
		if (uldNumbers != null && uldNumbers.size() > 0) {
		      for(String uldNo:uldNumbers) {
		    	  uld[val] = uldNo;
		    	  val++;
		      }
		}
		uldNumberLovForm.setUldNumbers(uld);*/
		uldNumberLovForm.setUldnumLovPage(uldNumbers);
		invocationContext.addAllError(error);
		invocationContext.target =ULDNUMBERLOV_SUCCESS;
	}
}
