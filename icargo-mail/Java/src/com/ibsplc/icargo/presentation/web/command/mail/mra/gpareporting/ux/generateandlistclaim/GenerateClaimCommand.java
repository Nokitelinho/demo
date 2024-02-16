/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.generateandlistclaim.GenerateClaimCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	May 16, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.generateandlistclaim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.GenerateandListClaimSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.GenerateandListClaimForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.generateandlistclaim.GenerateClaimCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	May 16, 2019	:	Draft
 */
public class GenerateClaimCommand extends BaseCommand{

	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.generateandlistclaim";
	private Log log = LogFactory.getLogger("Mail MRA of Generate&ListClaim");
	private static final String TARGET_SUCESS = "generate_success";
	private static final String TARGET_FAILURE = "generate_failure";
	private static final String STATUS_CLAIMGENERATION="Claim generation initiated";
	private static final String CLAIM_GENERATION_TYPE="CG";
	private static final String MESSAGE_RUN_FAILED = "mail.mra.gpareporting.generateclaimcommand.processRunFailed";
	private static final String MESSAGE_RUN_SUCCESS = "mail.mra.gpareporting.generateclaimcommand.processRunSuccess";
	private static final String RESDIT="resdit";
	private static final String ERROR_INVALID_DATE_RANGE = "mail.mra.gpareporting.ux.generateandlistclaim.msg.err.genClaimInvalidDateRange";
	private static final String ERROR_INVALID_PACODE = "mail.mra.gpareporting.ux.generateandlistclaim.msg.err.genClaimInvalidPacode";
	private static final String ERROR_CLAIM_ALREADY_GENERATED = "mail.mra.gpareporting.ux.generateandlistclaim.msg.err.claimAlreadyGenerated";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-4809 on May 16, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("GenerateClaimCommand", "execute");
		GenerateandListClaimForm generateandListClaimForm = (GenerateandListClaimForm) invocationContext.screenModel;
		GenerateandListClaimSession listclaimsession = getScreenSession(
				MODULE_NAME, SCREENID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO displayMessageErrorVO = null;
		InvoicFilterVO invoicFilterVO = null;
		
		if (listclaimsession.getFilterParamValues() != null) {
			invoicFilterVO = listclaimsession.getFilterParamValues();
		} else {
			invoicFilterVO = new InvoicFilterVO();
			invoicFilterVO.setCmpcod(companyCode);
			if (generateandListClaimForm.getFromDate() != null
					&& generateandListClaimForm.getFromDate().length() > 0) {
				invoicFilterVO.setFromDate(generateandListClaimForm.getFromDate());
			}
			if (generateandListClaimForm.getToDate() != null
					&& generateandListClaimForm.getToDate().length() > 0) {
				invoicFilterVO.setToDate(generateandListClaimForm.getToDate());
			}
			if (generateandListClaimForm.getPaCode() != null
					&& generateandListClaimForm.getPaCode().trim().length() > 0) {
				invoicFilterVO.setGpaCode(generateandListClaimForm.getPaCode());
			}
			
		}
			if(RESDIT.equals(generateandListClaimForm.getFromButton())){
				invoicFilterVO.setResditRequired(InvoicFilterVO.FLAG_YES);
			}else{
				invoicFilterVO.setResditRequired(InvoicFilterVO.FLAG_NO);
		}
		if(invoicFilterVO!=null) {
			invoicFilterVO.setTriggerPoint("SCREEN");
		}
		
		
		
		
		errors = validateuspsPacode(invoicFilterVO, errors);
		
		if(errors==null || errors.isEmpty()){
		errors = validatefrmtoDateRange(invoicFilterVO, errors);
		}
		
		errors = validateClaimGeneration(invoicFilterVO,errors);
		
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_SUCESS;
			return;
		}
		
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		//Change to MRA Constants VO
		invoiceTransactionLogVO.setInvoiceType(CLAIM_GENERATION_TYPE);
		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.OPERATION_FLAG_INSERT);
		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setRemarks(STATUS_CLAIMGENERATION);
		invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
		invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
		invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
		invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
		invoiceTransactionLogVO.getSerialNumber();
		invoiceTransactionLogVO.getTransactionCode();
		
		 try{
		   		invoiceTransactionLogVO =  new MailTrackingMRADelegate().initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO );
			       }catch(BusinessDelegateException ex){
				errors = this.handleDelegateException(ex);
			    }
		 String txnlogInfo = new StringBuilder(invoiceTransactionLogVO.getTransactionCode()).append("-").append(invoiceTransactionLogVO.getSerialNumber()).toString();
			try {
				new MailTrackingMRADelegate().generateClaimAndResdits(invoicFilterVO,txnlogInfo);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "*****in the exception");
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && !errors.isEmpty()) {
				log.log(Log.SEVERE, " @@@@@@@@ Errors are there @@@@@@@@ ");
				displayMessageErrorVO = new ErrorVO(MESSAGE_RUN_FAILED);
				displayMessageErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				displayMessageErrorVO
						.setErrorData(new String[] {"ClaimGeneration" });
				invocationContext.addError(displayMessageErrorVO);
				invocationContext.target = TARGET_FAILURE;
			}else if(errors.isEmpty()){
				log.log(Log.FINE, " @@@@@@@@ process Run Success @@@@@@@@ ");
				displayMessageErrorVO = new ErrorVO(MESSAGE_RUN_SUCCESS);
				displayMessageErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
				displayMessageErrorVO
						.setErrorData(new String[] {"ClaimGeneration" });
				invocationContext.addError(displayMessageErrorVO);
			}

			invocationContext.target = TARGET_SUCESS;
			log.exiting("GenerateClaimCommand", "execute");
	}
	
	/**
	 * 
	 * 	Method		:	GenerateClaimCommand.validatefrmtoDateRange
	 *	Added by 	:	A-8061 on 20-Jun-2019
	 * 	Used for 	:
	 *	Parameters	:	@param invoicFilterVO
	 *	Parameters	:	@param errors
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validatefrmtoDateRange(
			InvoicFilterVO invoicFilterVO, Collection<ErrorVO> errors) {
		Collection<USPSPostalCalendarVO> validdateRange = new ArrayList<USPSPostalCalendarVO>();
		try {
			validdateRange = new MailTrackingMRADelegate()
					.validateFrmToDateRange(invoicFilterVO);
			if (validdateRange != null && validdateRange.size() > 0) {
				log.log(Log.INFO,
						"\n\n ******* ValidatefrmtoDateRange is True********** \n\n");
			} else {
				ErrorVO error = new ErrorVO(ERROR_INVALID_DATE_RANGE);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return errors;

	}
/**
 * 
 * 	Method		:	GenerateClaimCommand.validateuspsPacode
 *	Added by 	:	A-8061 on 20-Jun-2019
 * 	Used for 	:
 *	Parameters	:	@param invoicFilterVO
 *	Parameters	:	@param errors
 *	Parameters	:	@return 
 *	Return type	: 	Collection<ErrorVO>
 */
	private Collection<ErrorVO> validateuspsPacode(InvoicFilterVO invoicFilterVO, Collection<ErrorVO> errors){
		String paCode_int = "";
		String paCode_dom = "";
		Collection<String> systemParameters = new ArrayList<String>();
		Map<String, String> systemParameterMap=new HashMap<String, String>();
		systemParameters.add(USPS_INTERNATIONAL_PA);
		systemParameters.add(USPS_DOMESTIC_PA);
		try {
		systemParameterMap= new MailTrackingMRADelegate().validateuspsPacode(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			paCode_int = systemParameterMap.get(USPS_INTERNATIONAL_PA);
			paCode_dom= systemParameterMap.get(USPS_DOMESTIC_PA);
		}
		
		if (paCode_int.equals(invoicFilterVO.getGpaCode())||paCode_dom.equals(invoicFilterVO.getGpaCode())) {
			log.log(Log.INFO,
					"\n\n ******* Valid International PACODE ********** \n\n");
		} else {
			ErrorVO error = new ErrorVO(ERROR_INVALID_PACODE);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return errors;
	}
	
private Collection<ErrorVO> validateClaimGeneration(InvoicFilterVO invoicFilterVO, Collection<ErrorVO> errors)	{
	boolean isClaimGenerated;
	try {
		isClaimGenerated = new MailTrackingMRADelegate()
				.isClaimGenerated(invoicFilterVO);
		if(isClaimGenerated){
			ErrorVO error = new ErrorVO(ERROR_CLAIM_ALREADY_GENERATED);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

	} catch (BusinessDelegateException businessDelegateException) {
		businessDelegateException.getMessageVO().getErrors();
		handleDelegateException(businessDelegateException);
	}
	return errors;
	
}




}
