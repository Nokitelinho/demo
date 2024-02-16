package com.ibsplc.icargo.presentation.web.command.mail.operations.errorhandling;
import com.ibsplc.icargo.business.admin.monitoring.vo.AdminTransactionConfigVO;
import com.ibsplc.icargo.business.admin.monitoring.vo.ErrorHandlingFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.admin.monitoring.ErrorHandlingSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailTrackingErrorHandlingForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCommand extends BaseCommand {

/*
 * ListCommand.java 
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */




/**
 * @author A-5991
 * 
 */


	/**
	 * Logger
	 */
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME_ADMIN = "admin.monitoring";

	/**
	 * The ScreenID
	 */
	private static final String SCREEN_ID_ADMINMONITORING = "admin.monitoring.errorhandling";
	/**
	 * Target mappings for succes
	 */
	private static final String LIST_SUCCESS = "list_success";

	/**
	 * This method is used to execute the list requested construct rates details
	 * command
	 * 
	 * @param invocationContext
	 *            - InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering("ListCommand", "execute");

		ErrorHandlingSession errorHandlingSession = (ErrorHandlingSession) getScreenSession(MODULE_NAME_ADMIN, SCREEN_ID_ADMINMONITORING);
		MailTrackingErrorHandlingForm errorHandlingForm = (MailTrackingErrorHandlingForm) invocationContext.screenModel;
		       
		ErrorHandlingFilterVO errorHandlingFilterVO = new ErrorHandlingFilterVO();
		AdminTransactionConfigVO adminTransactionVO= new AdminTransactionConfigVO();
		populateFilterVO(errorHandlingForm, errorHandlingFilterVO);

		errorHandlingSession.setErrorHandlingFilterVO(errorHandlingFilterVO);
		adminTransactionVO.setReferenceOne("Mail Bag");
		adminTransactionVO.setReferenceTwo("Function");
		adminTransactionVO.setReferenceThree("Container");
		adminTransactionVO.setReferenceFour("Carrier Code");
		adminTransactionVO.setReferenceFive("Flight Number");
		adminTransactionVO.setReferenceSix("Flight Date");
		adminTransactionVO.setReferenceSeven("Remarks");
		adminTransactionVO.setReferenceEight("AirPort Code");
		adminTransactionVO.setReferenceNine("Scanned Date And Time");
		adminTransactionVO.setReferenceTen("Last Updated Time");
		
		
		errorHandlingSession.setAdminTransactionConfigVO(adminTransactionVO);      
		log.log(Log.FINE, "***list_success** *****");
		invocationContext.target = LIST_SUCCESS;

	}

	private void populateFilterVO(MailTrackingErrorHandlingForm errorHandlingForm, ErrorHandlingFilterVO errorHandlingFilterVO) {
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		errorHandlingFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

		//populating the reference fields.
		errorHandlingFilterVO.setReferenceOne(errorHandlingForm.getMailbag());
		
		if(errorHandlingForm.getContainer()!=null){
			errorHandlingFilterVO.setReferenceThree(errorHandlingForm.getContainer().toUpperCase());
		}
		errorHandlingFilterVO.setReferenceTwo(errorHandlingForm.getFunction());
		errorHandlingFilterVO.setReferenceSix(errorHandlingForm.getFlightdate());
		
		if(errorHandlingForm.getAirportcode()!=null){
			errorHandlingFilterVO.setReferenceEight(errorHandlingForm.getAirportcode().toUpperCase());	
		}
		errorHandlingFilterVO.setReferenceFive(errorHandlingForm.getFlightNumber());
		errorHandlingFilterVO.setReferenceFour(errorHandlingForm.getFlightCarrierCode());
		errorHandlingFilterVO.setErrorDescription(errorHandlingForm.getErrorCode()); 
		if("validateMailBagDetails".equals(errorHandlingForm.getTransactionName())){//IASCB-38721
			errorHandlingFilterVO.setTransactionType("validateMailBagDetails");
		}
		
	}

}