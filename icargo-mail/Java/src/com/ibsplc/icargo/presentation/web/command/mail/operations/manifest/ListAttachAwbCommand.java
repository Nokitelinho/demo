/*
 * ListAttachAwbCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.AWBFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ListAttachAwbCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "list_success";
   private static final String TARGET_FAILURE = "list_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";	
   
   private static final int DIV_VALUE = 10;
   private static final int MOD_VALUE = 10;
   private static final int AWB_LENGTH = 8;
   private static final String AWB_NOT_IN_STOCK = "mailtracking.defaults.attachawb.msg.err.noagentstock";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListAttachAwbCommand","execute");
    	  
    	MailManifestForm mailManifestForm = 
    		(MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	// VALIDATING FORM
		errors = validateForm(mailManifestForm);
		if (errors != null && errors.size() > 0) {			
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		mailManifestSession.setAWBDetailVO(null);
		
		ContainerDetailsVO containerDetailsVO = mailManifestSession.getContainerDetailsVO();
		
		if(containerDetailsVO==null){
			MailArrivalSession mailArrivalSession = getScreenSession(MODULE_NAME, //Added by A-8164 for ICRD-259682
				"mailtracking.defaults.mailarrival");
			containerDetailsVO = mailArrivalSession.getContainerDetailsVO();			
		}
		
		AWBFilterVO awbFilterVO = new AWBFilterVO();
		
		// validating shipment prefix of AWB
		String shipmntPrefix = mailManifestForm.getShipmentPrefix();
		AirlineValidationVO airlineValidationVO = null;
    	
		/*
		 * Modified By KarthicK V as the part of the NCA Mail Tracking Bug Fix ..
		 * 
		 * 
		 */
		if(mailManifestForm.getShipmentPrefix()!=null && mailManifestForm.getShipmentPrefix().trim().length() > 0) {	
    		/*
    		 * Validate Prefix
   		     */   		
    		airlineValidationVO = validateShipmentPrefix(shipmntPrefix,invocationContext,logonAttributes);
    		if(airlineValidationVO != null) {
    			boolean isAWBValid = false;
    			if(mailManifestForm.getDocumentNumber()!=null && 
    					mailManifestForm.getDocumentNumber().trim().length()>0){
    				isAWBValid=validateCheckDigit(mailManifestForm.getDocumentNumber(),airlineValidationVO.getAwbCheckDigit());
    			if(!isAWBValid) {
	    			errors = new ArrayList<ErrorVO>();
					ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.invalidAWB"); //Modified by A-8164 for ICRD-259683
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);	
					invocationContext.addAllError(errors);
	    			invocationContext.target = TARGET_FAILURE;
	    			return;  
    				}
					// Validate AWB is in Stock
					// Added for ICRD-211205 starts
					DocumentValidationVO documentValidationVO = null;
					documentValidationVO = getDocumentValidationVO(
							mailManifestForm, airlineValidationVO,
							logonAttributes);
					if (documentValidationVO == null) {
						errors = new ArrayList<ErrorVO>();
						ErrorVO error = new ErrorVO(AWB_NOT_IN_STOCK);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						invocationContext.addAllError(errors);
						invocationContext.target = TARGET_FAILURE;
						return;
    			   }
				} else {
					DocumentFilterVO documentFilterVO = handleFilterVO(
							airlineValidationVO,
							mailManifestSession.getAgentCode(),mailManifestForm.getDestination(),mailManifestForm.getOrigin());//modified by a-7871 for ICRD-262511);
					populateAWBDetails(documentFilterVO, airlineValidationVO,
							mailManifestForm, mailManifestSession,
							invocationContext);
					invocationContext.target = TARGET_SUCCESS;
					mailManifestForm.setScreenStatus("LISTED");
					return;

    			 }
    			// Added for ICRD-211205 ends
    		 }
    		else {
	    			errors = new ArrayList<ErrorVO>();
					ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.invalidshipmentprefix");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);	
					invocationContext.addAllError(errors);
	    			invocationContext.target = TARGET_FAILURE;
	    			return;
    		}

    	}
		
			awbFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			awbFilterVO.setMasterDocumentNumber(mailManifestForm
					.getDocumentNumber());
			
			awbFilterVO.setUldNumber(containerDetailsVO.getContainerNumber());
			awbFilterVO.setOrigin(mailManifestForm.getOrigin());
			awbFilterVO.setDestination(mailManifestForm.getDestination());
			awbFilterVO.setDocumentOwnerIdentifier(airlineValidationVO
					.getAirlineIdentifier());
			awbFilterVO.setAgentCode(mailManifestSession.getAgentCode());
    	
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();
    	AWBDetailVO awbDetailVO = null;
    	log.log(Log.FINE, "awbFilterVO ---> ", awbFilterVO);
		try {
    			awbDetailVO = mailTrackingDefaultsDelegate.findAWBDetails(awbFilterVO);

		}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		else {
			if (awbDetailVO != null) {
				awbDetailVO.setOperationFlag(AWBDetailVO.OPERATION_FLAG_UPDATE);
				awbDetailVO.setShipmentPrefix(mailManifestForm.getShipmentPrefix());
				mailManifestSession.setAWBDetailVO(awbDetailVO);
				
				mailManifestForm.setStdPieces(
						mailManifestForm.getStdPieces() +
						awbDetailVO.getStatedPieces());
				/*mailManifestForm.setStdWeight(
						mailManifestForm.getStdWeight() + 
						awbDetailVO.getStatedWeight());*/
				try {
					mailManifestForm.setStdWeightMeasure(Measure.addMeasureValues(mailManifestForm.getStdWeightMeasure(), awbDetailVO.getStatedWeight()));
					mailManifestForm.setStdWeight(mailManifestForm.getStdWeightMeasure().getDisplayValue());
				} catch (UnitException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE,"UnitException",e.getMessage());
				}
				mailManifestForm.setShipmentDesc(awbDetailVO.getShipmentDescription());
				mailManifestForm.setDocumentNumber(awbDetailVO.getMasterDocumentNumber());
			}else{//Added for ICRD-259708
				awbDetailVO = new AWBDetailVO();
				awbDetailVO.setOperationFlag(AWBDetailVO.OPERATION_FLAG_INSERT);
				awbDetailVO.setOwnerId(airlineValidationVO.getAirlineIdentifier());
				awbDetailVO.setOwnerCode(airlineValidationVO.getAlphaCode());
				awbDetailVO.setCompanyCode(airlineValidationVO.getCompanyCode());
				awbDetailVO.setDestination(mailManifestForm.getDestination()
						.toUpperCase());
				awbDetailVO.setMasterDocumentNumber(mailManifestForm
						.getDocumentNumber());
				awbDetailVO.setShipmentPrefix(mailManifestForm.getShipmentPrefix());
				awbDetailVO.setOrigin(mailManifestForm.getOrigin().toUpperCase());
				mailManifestForm.setDocumentNumber(mailManifestForm
						.getDocumentNumber());
				mailManifestSession.setAWBDetailVO(awbDetailVO);
			}
		}
		
		mailManifestForm.setScreenStatus("LISTED");
		
		log.log(Log.INFO, "AwbDetailVO----->>", awbDetailVO);
		invocationContext.target = TARGET_SUCCESS;
       	
    	log.exiting("ListAttachAwbCommand","execute");
    	
    }
    /**
     * @param airlinePrefix
     * @param invocationContext
     * @param logonAttributes
     * @return airlineValidationVO
     */
    private AirlineValidationVO validateShipmentPrefix(
    		String airlinePrefix,
    		InvocationContext invocationContext,
    		LogonAttributes logonAttributes) {
    	
    	log.entering("ListAttachAwbCommand", "validateShipmentPrefix");
    	
    	Collection<ErrorVO> errors = null;    	
    	AirlineValidationVO airlineValidationVO = null;
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
    	try {
    		airlineValidationVO = airlineDelegate.validateNumericCode(
					logonAttributes.getCompanyCode(), airlinePrefix);
		} catch (BusinessDelegateException businessDelegateException) {
			
			errors = handleDelegateException(businessDelegateException);
		}
    	log.log(Log.FINE, "AirlineValidationVO ---> ", airlineValidationVO);
		log.exiting("ListAttachAwbCommand", "validateShipmentPrefix");
    	
    	return airlineValidationVO;
    }
    /**
	 * This method is used for validating the form for the particular action
	 * @param mailManifestForm - MailManifestForm
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			MailManifestForm mailManifestForm) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();

		if (("").equals(mailManifestForm.getShipmentPrefix())) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.attachawb.msg.err.noAwb");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}

		return formErrors;
	}   
	
	private boolean validateCheckDigit(String awbNumber,int checkDigit) {
    	log.entering("ListAddAWBCommand", "validateCheckDigit");

    	int awbNum = 0;
    	int remainder = 0;

        if (awbNumber.length() == AWB_LENGTH){
        	if(checkDigit != 0) {
        		try {
					awbNum = Integer.parseInt(awbNumber)/DIV_VALUE;
					remainder = Integer.parseInt(awbNumber)%MOD_VALUE;
        		}
        		catch(NumberFormatException numberFormatException){
        			return false;
        		}
	            if ((awbNum % checkDigit )  == remainder) {
	                return true;
	            }
        	}
        }
        log.exiting("ListAddAWBCommand", "validateCheckDigit");
        return false;
    }
	/**
	 * 
	 * 	Method		:	ListAttachAwbCommand.getDocumentValidationVO
	 *	Added by 	:	U-1267 on 07-Nov-2017
	 * 	Used for 	:	ICRD-211205
	 *	Parameters	:	@param mailManifestForm
	 *	Parameters	:	@param airlineValidationVO
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	DocumentValidationVO
	 */
	private DocumentValidationVO getDocumentValidationVO(
			MailManifestForm mailManifestForm, AirlineValidationVO airlineValidationVO,LogonAttributes logonAttributes) {

		DocumentValidationVO documentValidationVO = null;
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
		documentFilterVO.setDocumentNumber(mailManifestForm
				.getDocumentNumber());
		documentFilterVO.setDocumentType(MailConstantsVO.DOCUMENT_TYPE);
		log.log(Log.FINE, "checking for document details in stock... ");
		log.log(Log.FINE, "documentFilterVO ---> ", documentFilterVO);
		try {
			documentValidationVO = new MailTrackingDefaultsDelegate().validateDocumentInStock(documentFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return documentValidationVO;
	}
	/**
	 * 
	 * 	Method		:	ListAttachAwbCommand.populateAWBDetails
	 *	Added by 	:	U-1267 on 09-Nov-2017
	 * 	Used for 	:	ICRD-211205
	 *	Parameters	:	@param documentFilterVO
	 *	Parameters	:	@param airlineValidationVO
	 *	Parameters	:	@param mailManifestForm
	 *	Parameters	:	@param mailManifestSession
	 *	Parameters	:	@param invocationContext 
	 *	Return type	: 	void
	 */
	private void populateAWBDetails(DocumentFilterVO documentFilterVO,
			AirlineValidationVO airlineValidationVO,
			MailManifestForm mailManifestForm,
			MailManifestSession mailManifestSession,
			InvocationContext invocationContext) {
		String documentNumber = null;
		DocumentValidationVO documentValidationVO = null;
		AWBDetailVO awbDetailVO = null;
		try {

			documentValidationVO = new MailTrackingDefaultsDelegate()
					.findNextDocumentNumber(documentFilterVO);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			invocationContext
					.addAllError(handleDelegateException(businessDelegateException));
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		if (documentValidationVO == null) {
			ErrorVO error = new ErrorVO(
					"mailtracking.defaults.attachawb.msg.err.noDocumentPresent");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = TARGET_FAILURE;
			return;
		} else {
			documentNumber = documentValidationVO.getDocumentNumber();
			documentFilterVO.setDocumentNumber(documentNumber);
			awbDetailVO = new AWBDetailVO();
			awbDetailVO.setOperationFlag(AWBDetailVO.OPERATION_FLAG_INSERT);
			awbDetailVO.setOwnerId(airlineValidationVO.getAirlineIdentifier());
			awbDetailVO.setOwnerCode(airlineValidationVO.getAlphaCode());
			awbDetailVO.setCompanyCode(airlineValidationVO.getCompanyCode());
			awbDetailVO.setDestination(mailManifestForm.getDestination()
					.toUpperCase());
			awbDetailVO.setMasterDocumentNumber(documentNumber);
			awbDetailVO.setShipmentPrefix(mailManifestForm.getShipmentPrefix());
			awbDetailVO.setOrigin(mailManifestForm.getOrigin().toUpperCase());
			mailManifestForm.setDocumentNumber(documentNumber);
			mailManifestSession.setAWBDetailVO(awbDetailVO);
		}
	}
	/**
	 * 
	 * 	Method		:	ListAttachAwbCommand.handleFilterVO
	 *	Added by 	:	U-1267 on 09-Nov-2017
	 * 	Used for 	:	ICRD-211205
	 *	Parameters	:	@param airlineValidationVO
	 *	Parameters	:	@param agentCode
	 *	Parameters	:	@return 
	 *	Return type	: 	DocumentFilterVO
	 */
	private DocumentFilterVO handleFilterVO(
			AirlineValidationVO airlineValidationVO, String agentCode,String dest,String origin) {//modified by a-7871 for ICRD-262511

		log.entering("ListAttachAwbCommand", "handleFilterVO");

		DocumentFilterVO documentFilterVO = new DocumentFilterVO();

		documentFilterVO.setCompanyCode(airlineValidationVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(airlineValidationVO
				.getAirlineIdentifier());
		documentFilterVO
				.setDocumentSubType(MailConstantsVO.MAIL_DOCUMENT_SUBTYPE);
		documentFilterVO.setDocumentType(MailConstantsVO.DOCUMENT_TYPE);
		documentFilterVO.setStockOwner(agentCode);
		documentFilterVO.setAwbDestination(dest);//added by a-7871 for ICRD-262511
		documentFilterVO.setAwbOrigin(origin);
		log.log(Log.INFO, "DocumentFilterVO----->>", documentFilterVO);
		return documentFilterVO;
	}

}
