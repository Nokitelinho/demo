/*
 * SaveAttachAwbCommand.java Created on Jul 1 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
/**
 * @author A-5991
 *
 */
public class SaveAttachAwbCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "save_success";
   private static final String TARGET_FAILURE = "save_failure";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";

   private static final String STOCK_REQ_PARAMETER = "mailtracking.defaults.stockcheckrequired";
   public static final String MAIL_AWB_PRODUCT = "mail.operations.productIndicator";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("SaveAttachAwbCommand","execute");

    	MailManifestForm mailManifestForm =
    		(MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;

    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();

    	// VALIDATING FORM
		errors = validateForm(mailManifestForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		ContainerDetailsVO containerDetailsVO = mailManifestSession.getContainerDetailsVO();
		AWBDetailVO awbDetailVO = mailManifestSession.getAWBDetailVO();
		// obtain the system parameter for stock control check
		String isValidationRequired =
			mailManifestSession.getSystemParameters().get(STOCK_REQ_PARAMETER);
		// obtain the system parameter for stock holder
		String agentCode = mailManifestSession.getAgentCode();

		if (agentCode == null || agentCode.trim().length()==0) {
			ErrorVO error =
				new ErrorVO("mailtracking.defaults.attachawb.msg.err.noAgentCode");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		// UPDATE AWBDETAILVO
		/*
		 * Added By Karthick V as the part of the Air NewZealand CR
		 * 
		 * 
		 */
		awbDetailVO.setShipmentDescription(mailManifestForm.getShipmentDesc());
		awbDetailVO.setStatedPieces(mailManifestForm.getStdPieces());
		//awbDetailVO.setStatedWeight(mailManifestForm.getStdWeight());
		awbDetailVO.setStatedWeight(mailManifestForm.getStdWeightMeasure());//added by A-7371
		awbDetailVO.setStatedWeightCode(mailManifestForm.getWeightStandard());
		awbDetailVO.setShipmentDescription(mailManifestForm.getShipmentDesc());
		
		/*
		 * Calculating the Volume
		 */
        double statedVolume = 0.0D;
        statedVolume = mailManifestForm.getStdWeight()/Double.parseDouble(mailManifestForm.getDensity());							
	    if(MailConstantsVO.NO_VOLUME != statedVolume) {
		   if(MailConstantsVO.MINIMUM_VOLUME > statedVolume) {
			   statedVolume = MailConstantsVO.MINIMUM_VOLUME;
		   }else {
			   statedVolume = Double.parseDouble(TextFormatter.formatDouble(statedVolume , 2));
		   }
	    }
		//awbDetailVO.setStatedVolume(statedVolume);
	    awbDetailVO.setStatedVolume(new Measure(UnitConstants.VOLUME, statedVolume));  //Added by A-7550
		log.log(Log.INFO, "AwbDetailVO----->>", awbDetailVO);
		DocumentFilterVO documentFilterVO = handleFilterVO(
												awbDetailVO,
												logonAttributes,
												agentCode);

		//IF NO AWB LISTED
		if ("".equals(mailManifestForm.getDocumentNumber())
				&& AWBDetailVO.OPERATION_FLAG_INSERT.equals(awbDetailVO.getOperationFlag())) {

			log.log(Log.INFO,"Going to find the next Document Number -------->>");
			/*String documentNumber = null;
			DocumentValidationVO documentValidationVO = null;
			try {

				documentValidationVO = mailTrackingDefaultsDelegate.findNextDocumentNumber(documentFilterVO);

			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				invocationContext.addAllError(handleDelegateException(businessDelegateException));
				invocationContext.target = TARGET_FAILURE;
				return;
			}

			if (documentValidationVO == null) {
				ErrorVO error =
					new ErrorVO("mailtracking.defaults.attachawb.msg.err.noDocumentPresent");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(error);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			else {
				documentNumber = documentValidationVO.getDocumentNumber();
				documentFilterVO.setDocumentNumber(documentNumber);
			}

			log.log(Log.INFO, "DocumentNumber----->>", documentNumber);
			awbDetailVO.setMasterDocumentNumber(documentNumber);*/

		}
		// IF THE AWB IS A NEW ONE
		else if (!"".equals(mailManifestForm.getDocumentNumber())
				&& AWBDetailVO.OPERATION_FLAG_INSERT.equals(awbDetailVO.getOperationFlag())) {

			log.log(Log.INFO, "isValidationRequired -------->>",
					isValidationRequired);
			if(AWBDetailVO.FLAG_YES.equals(isValidationRequired)){
				log.log(Log.INFO,"Going to get document from stock -------->>");
				DocumentValidationVO documentValidationVO = null;
				try {

					documentValidationVO = mailTrackingDefaultsDelegate.validateDocumentInStock(documentFilterVO);

				} catch(BusinessDelegateException exception) {
					invocationContext.addAllError(
							handleDelegateException(exception));
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				log.log(Log.INFO, "DocumentValidationVO-------->>",
						documentValidationVO);
				if (documentValidationVO == null) {
					ErrorVO error =
						new ErrorVO("mailtracking.defaults.attachawb.msg.err.noagentstock");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(error);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
			}
		}
		// IF AWB IS ALREADY PRESENT
		else if (AWBDetailVO.OPERATION_FLAG_UPDATE.equals(awbDetailVO.getOperationFlag())) {
			log.log(Log.INFO,"Going to check origin and destn -------->>");
			/*
			 * Validate whether AWBDetailVO 's origin - destination
			 * and selected DSNs origin destn match.
			 * It must be the same else error
			 */
			if (!awbDetailVO.getOrigin().equalsIgnoreCase(mailManifestForm.getOrigin())
					|| !awbDetailVO.getDestination().equalsIgnoreCase(mailManifestForm.getDestination())) {
				errors = new ArrayList<ErrorVO>();
				ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.originDestMismatch");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
			}
		}

    	try {
    		// ATTACH THE SELECTED DSNS TO LISTED AWB
    		log.log(Log.INFO,"Going to attch AWB -------->>");
    		log.log(Log.INFO, "Going to attch AWB -------->>", awbDetailVO);
			mailTrackingDefaultsDelegate.attachAWBDetails(awbDetailVO,containerDetailsVO);

		}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		else {
			if((!"".equals(mailManifestForm.getDocumentNumber())
					&& AWBDetailVO.OPERATION_FLAG_INSERT.equals(awbDetailVO.getOperationFlag())
					&& AWBDetailVO.FLAG_YES.equals(isValidationRequired))
				|| ("".equals(mailManifestForm.getDocumentNumber())
					&& AWBDetailVO.OPERATION_FLAG_INSERT.equals(awbDetailVO.getOperationFlag())) ){
				/*
				 * If the document number is obtained from stock
				 * then delete the stock document number.
				 */
				//for stock patch
				documentFilterVO.setStockOwner(null);
				log.log(Log.INFO,"Going to delete document from stock 1-------->>");
				try {
					log.log(Log.INFO,"Going to delete document from stock 2-------->>");
					mailTrackingDefaultsDelegate.deleteDocumentFromStock(documentFilterVO);
					log.log(Log.INFO,"Going to delete document from stock 3-------->>");
				} catch (BusinessDelegateException businessDelegateException) {					
					errors = handleDelegateException(businessDelegateException);
					invocationContext.addAllError(errors);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				log.log(Log.INFO,"Going to delete document from stock 6-------->>");
			}
		}

		// IF SAVE SUCCESSFUL
		mailManifestSession.setAWBDetailVO(null);
		mailManifestSession.setContainerDetailsVO(null);
		mailManifestForm.setScreenStatus("CLOSE");

		invocationContext.target = TARGET_SUCCESS;

    	log.exiting("SaveAttachAwbCommand","execute");

    }
    /**
     * Method is used to create filter vo
     * @param awbDetailVO
     * @param logonAttributes
     * @param agentCode
     * @return
     */
    private DocumentFilterVO handleFilterVO(
    		AWBDetailVO awbDetailVO,
    		LogonAttributes logonAttributes,
    		String agentCode) {

    	log.entering("SaveAttachAwbCommand","handleFilterVO");

    	DocumentFilterVO documentFilterVO = new DocumentFilterVO();
        String companyCode = logonAttributes.getCompanyCode();
    	documentFilterVO.setAirlineIdentifier(awbDetailVO.getOwnerId());
    	documentFilterVO.setCompanyCode(companyCode);
    	documentFilterVO.setDocumentNumber(awbDetailVO.getMasterDocumentNumber());
    	documentFilterVO.setDocumentType(MailConstantsVO.DOCUMENT_TYPE);
    	documentFilterVO.setStockOwner(agentCode);

    	log.log(Log.INFO, "DocumentFilterVO----->>", documentFilterVO);
		return documentFilterVO;
    }

    /**
	 * This method is used for validating the form for the particular action
	 * @param mailManifestForm - MailManifestForm
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			MailManifestForm mailManifestForm) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();

		if (mailManifestForm.getStdPieces() == 0) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.attachawb.msg.err.noStdPieces");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}
		if (mailManifestForm.getStdWeight() == 0) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.attachawb.msg.err.noStdWeight");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}

		return formErrors;
	}
}
