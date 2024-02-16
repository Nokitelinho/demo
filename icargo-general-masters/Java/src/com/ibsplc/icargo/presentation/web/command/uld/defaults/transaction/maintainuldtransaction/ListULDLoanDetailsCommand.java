/*
 * ListULDLoanDetailsCommand.java  Created on Jan 12,07
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
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
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


public class ListULDLoanDetailsCommand extends BaseCommand {

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

	private static final String AIRLINE = "A";

	private static final String AGENT = "G";

	private static final String ULD_IS_OCCUPIED_AT_WAREHOUSE =
		"uld.defaults.uldisoccupiedatwarehouse";

	private static final String ULD_EXISTS_AT_AIRPORT = "operations.flthandling.uldexistsatairport";

	private static final String ULD_EXISTS_IN_LOADPLAN = "operations.flthandling.uldexistsinloadplan";

	private static final String ULD_EXISTS_IN_MANIFEST = "operations.flthandling.uldexistsinmanifest";

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
		String compCode=logonAttributes.getCompanyCode();
		String airportCode=logonAttributes.getAirportCode();
		boolean isAirlineUser=logonAttributes.isAirlineUser();

		/**
		 * to validate UldNumber Format
		 */
		String uldNum = maintainULDTransactionForm.getLoanUldNum()
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
					/*Added by A-2412 for Agent Blocking */

					/*if(agentVO.isAgentBlocked()){
						ErrorVO errorVO = new ErrorVO(								
						"uld.defaults.loanborrowULD.blockedAgent");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
					}*/

				}

			if (errorss != null && errorss.size() > 0) {
				invocationContext.addAllError(errorss);
				maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);
				if(("MODIFY").equals(maintainULDTransactionForm.getModifyMode())){
					maintainULDTransactionForm.setLoanPopupFlag("LOANSCREENLOAD-MOD");
				} else {
					maintainULDTransactionForm.setLoanPopupFlag("LOANSCREENLOAD");
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
				log.log(Log.FINE,
						"\n\n\n\n ownerVO.getAirlineIdentifier() ---> ",
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
				maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);
			} else {
				ErrorVO err=null;
				err=validateULD(uldNum,compCode,airportCode,isAirlineUser);
				if(err!=null){
					invocationContext.addError(err);
					maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);
				}else{


			if(("MODIFY").equals(maintainULDTransactionForm.getModifyMode())){
					for (ULDTransactionDetailsVO uldVO : loanBorrowULDSession.getSelectedULDColl()) {
						if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getLoanUldNum().toUpperCase())) {
							maintainULDTransactionForm.setLoanUldNum(uldVO.getUldNumber());
							//maintainULDTransactionForm.setLoanTxnNum(uldVO.getControlReceiptNumber());
							maintainULDTransactionForm.setLoanTransactionStation(uldVO.getTxStationCode());
							if ("Y".equalsIgnoreCase(uldVO.getDamageStatus())) {
								maintainULDTransactionForm.setLoanDamage("true");
							} else {
								maintainULDTransactionForm.setLoanDamage("");
							}
							maintainULDTransactionForm.setLoanULDNature(uldVO.getUldNature());
							log.log(Log.INFO, "uldcondition code", uldVO.getUldConditionCode());
							//added by nisha starts on 07feb08
							if(uldVO.getUldConditionCode()!=null && uldVO.getUldConditionCode().trim().length()>0){
								log.log(Log.INFO, "uldcondiadsfsdftion code",
										uldVO.getUldConditionCode());
							maintainULDTransactionForm.setUldConditionCode(uldVO.getUldConditionCode());
							}else{
								log.log(Log.INFO, "uldconditionelse code",
										uldVO.getUldConditionCode());
								maintainULDTransactionForm.setUldConditionCode("SER");
							}
//							added by nisha ends on 07feb08
							
							//Added by nisha on 06 feb 08 starts
							if (uldVO.getControlReceiptNumber() == null
									|| uldVO.getControlReceiptNumber().length() == 0) {
								try {
									String crn = new ULDDefaultsDelegate()
											.findCRNForULDTransaction(
													logonAttributes
															.getCompanyCode(),
													maintainULDTransactionForm
															.getFromPartyCode()
															.toUpperCase());
									if (crn != null && crn.length() > 0) {
										String airlineID = crn.substring(0, crn
												.length() - 7);
										String number = crn.substring(crn
												.length() - 7, crn.length());
										String s3 = new StringBuilder(airlineID)
												.append("0").append(number)
												.toString();
										crn = s3;
										log.log(Log.INFO, "crn", crn);
										loanBorrowULDSession.setCtrlRcptNoPrefix(crn.substring(0, 5));
										maintainULDTransactionForm.setLoanTxnNum(crn.substring(5, crn.length()));
										loanBorrowULDSession.setCtrlRcptNo(crn.substring(5, crn.length()));

									}

								} catch (BusinessDelegateException businessDelegateException) {
									errors = handleDelegateException(businessDelegateException);
								}
							}else{
							String crnPrefix=uldVO.getControlReceiptNumber().substring(0, 5);
							String crn=uldVO.getControlReceiptNumber().substring(5, uldVO.getControlReceiptNumber().length());
							maintainULDTransactionForm.setLoanTxnNum(crn);
							loanBorrowULDSession.setCtrlRcptNoPrefix(crnPrefix);
							loanBorrowULDSession.setCtrlRcptNo(crn);
							}

//							Added by nisha on 06 feb 08 ends

							//loanBorrowULDSession.setCtrlRcptNoPrefix(crnPrefix);
						}
					}
					maintainULDTransactionForm.setLoanPopupFlag("LIST-MOD");
			}
			else{
				boolean isNotDuplicate = true;
			if(uldTransactionDetailsVOs!=null && uldTransactionDetailsVOs.size()>0){
			for (ULDTransactionDetailsVO uldVO : uldTransactionDetailsVOs) {
				if (uldVO.getUldNumber().equals(
						maintainULDTransactionForm.getLoanUldNum().toUpperCase())) {
					isNotDuplicate = false;
				}
			}}
			if (!isNotDuplicate) {
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.transaction.uldalreadyexist");
				errorVO.setErrorDisplayType(ERROR);

				invocationContext.addError(errorVO);
				maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);
				maintainULDTransactionForm.setLoanPopupFlag("LOANSCREENLOAD");
			}else
			{
				boolean isPresent=false;
				if(loanBorrowULDSession.getSelectedULDColl()!=null && loanBorrowULDSession.getSelectedULDColl().size()>0){
				for (ULDTransactionDetailsVO uldVO : loanBorrowULDSession.getSelectedULDColl()) {
					if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getLoanUldNum().toUpperCase())) {
						isPresent=true;
						maintainULDTransactionForm.setLoanUldNum(uldVO.getUldNumber());
						maintainULDTransactionForm.setLoanTxnNum(uldVO.getControlReceiptNumber());
						maintainULDTransactionForm.setLoanTransactionStation(uldVO.getTxStationCode());
						if ("Y".equalsIgnoreCase(uldVO.getDamageStatus())) {
							maintainULDTransactionForm.setLoanDamage("true");
						} else {
							maintainULDTransactionForm.setLoanDamage("");
						}

						maintainULDTransactionForm.setLoanULDNature(uldVO.getUldNature());

					}

				}}
				if(!isPresent){
				ULDListVO uldListVO=null;
				try{
					uldListVO=new ULDDefaultsDelegate().fetchULDDetailsForTransaction(logonAttributes.getCompanyCode(), uldNum);

					log.log(Log.ALL,
							"in the ListULDLoanDetailsCommand=====uldListVO",
							uldListVO);
					if(uldListVO !=null){
						loanBorrowULDSession.setUldLastUpdateTime(uldListVO.getLastUpdateTime());
						loanBorrowULDSession.setUldLastUpdateUser(uldListVO.getLastUpdateUser());
					}
					//added by A-2619 ends

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if(uldListVO==null )
			{
				ErrorVO errorVO = new ErrorVO(
				"uld.defaults.transaction.ulddoesnotexist");
		errorVO.setErrorDisplayType(ERROR);

		invocationContext.addError(errorVO);
		maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);
		maintainULDTransactionForm.setLoanPopupFlag("LOANSCREENLOAD");
		invocationContext.target = LIST_SUCCESS;
		return;
			}
			else{
			maintainULDTransactionForm.setLoanULDNature(uldListVO.getUldNature());
			log.log(Log.ALL, "uldListVO.getDamageStatus()------------>>>",
					uldListVO.getDamageStatus());
			if("D".equals(uldListVO.getDamageStatus())){
				maintainULDTransactionForm.setLoanDamage("true");
				//Added by Preet
				maintainULDTransactionForm.setUldConditionCode("DAM");
				
log.log(Log.ALL,
						"maintainULDTransactionForm.getUldConditionCode()",
						maintainULDTransactionForm.getUldConditionCode());
			}else{
				maintainULDTransactionForm.setLoanDamage("false");
				//Added by Preet
				maintainULDTransactionForm.setUldConditionCode("SER");
				//ends
			}
			}
			log.log(Log.INFO, "loanBorrowULDSession.getCrn",
					loanBorrowULDSession.getCtrlRcptNo());
			if(maintainULDTransactionForm.getLoanTxnNum()!=null && maintainULDTransactionForm.getLoanTxnNum().trim().length()>0){
				loanBorrowULDSession.setCtrlRcptNo(maintainULDTransactionForm.getLoanTxnNum().trim());
			}
			//added by nisha on 29JAN for bug fix
				}

				maintainULDTransactionForm.setLoanPopupFlag("LIST");
			}
			}}
		}
			maintainULDTransactionForm.setAddUldDisable("SAVE_ENABLE");
		invocationContext.target = LIST_SUCCESS;
	}

	private ErrorVO validateULD(String uldno,String compCode,String airportCode,boolean isAirlineUser) {
//    	find whether airport is a ULD handling GHA

    	AreaDelegate areaDelegate =new AreaDelegate();
    	AirportVO airportVO=null;
    	Collection<ErrorVO> excep = new ArrayList<ErrorVO>();
    	ErrorVO err=null;
    	try {
    		airportVO =	areaDelegate.findAirportDetails(compCode,airportCode);
    	} catch (BusinessDelegateException e) {
    	e.getMessage();
    	excep = handleDelegateException(e);
    	}
    	log.log(Log.FINE, "airportVO---------------->", airportVO);
		boolean isGHA=false;
    	if(isAirlineUser){
    		isGHA=false;
    	}else
    	{
    		isGHA=true;
    	}

    	boolean isAirlineGHA=false;
    	if(airportVO!=null)
    	{
    		if(airportVO.getUsedAirportVO() != null && airportVO.getUsedAirportVO().isUldGHAFlag()){
    		//if(airportVO.getUsedAirportVO().isUldGHAFlag()){
    			isAirlineGHA=true;
    		}else{
    			isAirlineGHA=false;
    		}
    	}
    	log.log(Log.FINE, "isGHA----------------->", isGHA);
		log.log(Log.FINE, "isAirlineGHA-------------------->", isAirlineGHA);
		if(isGHA || isAirlineGHA){
    		Collection<ULDValidationVO> validationvo = new ArrayList<ULDValidationVO>();
    		ULDValidationVO vo=new ULDValidationVO();
    		vo.setUldNumber(uldno.toUpperCase());
    		vo.setCompanyCode(compCode);
    		validationvo.add(vo);
//    	to check whether ULD is in warehouse
    		log.log(Log.FINE, "\n\n\n\n ****INSIDE WH VALIDATION FOR ULD******");
    		Collection<ErrorVO> errorsafterwh = new ArrayList<ErrorVO>();
    		try {
    			new ULDDefaultsDelegate().validateULDForWarehouseOccupancy(validationvo);
    		} catch (BusinessDelegateException businessDelegateException) {
    			businessDelegateException.getMessage();
    			log.log(Log.FINE, "\n\n\n\n ****caught******");
    			errorsafterwh = handleDelegateException(businessDelegateException);
    		}
    		if (errorsafterwh != null && errorsafterwh.size() > 0) {

    			for (ErrorVO error : errorsafterwh) {
    				if (ULD_IS_OCCUPIED_AT_WAREHOUSE.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_IS_OCCUPIED_AT_WAREHOUSE");


	    				return new ErrorVO("uld.defaults.uldisoccupiedatwarehouse", new Object[] { uldN });
    				}

    		}}


//    	to check whether ULD is operational anywhere

    		log.log(Log.FINE, "\n\n\n\n ****INSIDE opr VALIDATION FOR ULD******");
    		Collection<ErrorVO> errorsafteropr = new ArrayList<ErrorVO>();
    		try {
    			new ULDDefaultsDelegate().checkForULDInOperation(validationvo);
    		} catch (BusinessDelegateException businessDelegateException) {
    			businessDelegateException.getMessage();
    			log.log(Log.FINE, "\n\n\n\n ****caught******");
    			errorsafteropr = handleDelegateException(businessDelegateException);
    		}
    		if (errorsafteropr != null && errorsafteropr.size() > 0) {
    			log.log(Log.FINE, "\n\n\n\n ****ERROR PRESENT******");
    			for (ErrorVO error : errorsafteropr) {
    				if (ULD_EXISTS_AT_AIRPORT.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_EXISTS_AT_AIRPORT");

	    				return new ErrorVO("operations.flthandling.uldexistsatairport", new Object[] { uldN });
    				}
    				if (ULD_EXISTS_IN_LOADPLAN.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_EXISTS_IN_LOADPLAN");

	    				return new ErrorVO("operations.flthandling.uldexistsinloadplan", new Object[] { uldN });
    				}
    				if (ULD_EXISTS_IN_MANIFEST.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_EXISTS_IN_MANIFEST");

	    				return new ErrorVO("operations.flthandling.uldexistsinmanifest", new Object[] { uldN });
    				}

    		}}
    	}
    	return err;
    }


}
