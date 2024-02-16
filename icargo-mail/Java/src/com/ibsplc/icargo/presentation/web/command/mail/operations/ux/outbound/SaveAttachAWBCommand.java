package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.AttachAwbDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SaveAttachAWBCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ListAttachAwbCommand");
	private static final String STOCK_REQ_PARAMETER = "mailtracking.defaults.stockcheckrequired";
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		OutboundModel outboundModel = 
				(OutboundModel) actionContext.getScreenModel();
	
		ArrayList<ContainerDetails> containerDetailsCollection= 
				outboundModel.getContainerDetailsCollection();
		ContainerDetails containerDetails =null;
		containerDetails =
					containerDetailsCollection.get(0);
		ContainerDetailsVO containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(containerDetails,logonAttributes);
		AttachAwbDetails attachAwbDetails=
				outboundModel.getAttachAwbDetails();
		ResponseVO responseVO = new ResponseVO();
		List<ErrorVO> errors = null;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
	    		new MailTrackingDefaultsDelegate();
		// VALIDATING FORM
		errors = validateForm(attachAwbDetails);
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			//invocationContext.target = TARGET_FAILURE;
			return;
		}

		AWBDetailVO awbDetailVO = 
				attachAwbDetails.getAwbDetailVO();
		String isValidationRequired =
				attachAwbDetails.getSystemParameters().get(STOCK_REQ_PARAMETER);
		String agentCode = attachAwbDetails.getAgentCode();
		
		if (agentCode == null || agentCode.trim().length()==0) {
			ErrorVO error =
				new ErrorVO("mailtracking.defaults.attachawb.msg.err.noAgentCode");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			actionContext.addError(error);
			//invocationContext.target = TARGET_FAILURE;
			return;
		}
		// UPDATE AWBDETAILVO
		/*
		 * Added By Karthick V as the part of the Air NewZealand CR
		 * 
		 * 
		 */
		awbDetailVO.setShipmentDescription(attachAwbDetails.getShipmentDescription());
		if(((attachAwbDetails.getStatedPieces()==attachAwbDetails.getStandardPieces())&& 
				attachAwbDetails.getStatedWeight().getDisplayValue()==(attachAwbDetails.getStandardWeight())) ||(attachAwbDetails.getStatedPieces()==0 ))
        {
		awbDetailVO.setStatedPieces(attachAwbDetails.getStandardPieces());
		//awbDetailVO.setStatedWeight(mailManifestForm.getStdWeight());
		awbDetailVO.setStatedWeight(attachAwbDetails.getStdWeightMeasure());//added by A-7371
			attachAwbDetails.setStandardWeight(attachAwbDetails.getStdWeightMeasure().getDisplayValue());
}
        else {
            awbDetailVO.setStatedPieces(attachAwbDetails.getStatedPieces());
            awbDetailVO.setStatedWeight(attachAwbDetails.getStatedWeight());
            attachAwbDetails.setStandardWeight(attachAwbDetails.getStatedWeight().getDisplayValue());
        }
		awbDetailVO.setStatedWeightCode(attachAwbDetails.getWeightStandard());
		/*
		 * Calculating the Volume
		 */
        double statedVolume = 0.0D;
        statedVolume = attachAwbDetails.getStandardWeight()/Double.parseDouble(attachAwbDetails.getDensity());							
	    if(MailConstantsVO.NO_VOLUME != statedVolume) {
		   if(MailConstantsVO.MINIMUM_VOLUME > statedVolume) {
			   statedVolume = MailConstantsVO.MINIMUM_VOLUME;
		   }else {
			   statedVolume = Double.parseDouble(TextFormatter.formatDouble(statedVolume , 2));
		   }
	    }
		//awbDetailVO.setStatedVolume(statedVolume);
	    awbDetailVO.setStatedVolume(new Measure(UnitConstants.VOLUME, statedVolume));  //Added by A-7550
		awbDetailVO.setAgentCode(agentCode);
		log.log(Log.INFO, "AwbDetailVO----->>", awbDetailVO);
		DocumentFilterVO documentFilterVO = handleFilterVO(
												awbDetailVO,
												logonAttributes,
												agentCode);
		//IF NO AWB LISTED
		if ("".equals(attachAwbDetails.getMasterDocumentNumber())
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
		else if (!"".equals(attachAwbDetails.getMasterDocumentNumber())
				&& AWBDetailVO.OPERATION_FLAG_INSERT.equals(awbDetailVO.getOperationFlag())) {
			log.log(Log.INFO, "isValidationRequired -------->>",
					isValidationRequired);
			if(AWBDetailVO.FLAG_YES.equals(isValidationRequired)){
				log.log(Log.INFO,"Going to get document from stock -------->>");
				DocumentValidationVO documentValidationVO = null;
				try {
					documentValidationVO = mailTrackingDefaultsDelegate.validateDocumentInStock(documentFilterVO);
				} catch(BusinessDelegateException exception) {
					actionContext.addAllError(
							handleDelegateException(exception));
					//invocationContext.target = TARGET_FAILURE;
					return;
				}
				log.log(Log.INFO, "DocumentValidationVO-------->>",
						documentValidationVO);
				if (documentValidationVO == null) {
					ErrorVO error =
						new ErrorVO("mailtracking.defaults.attachawb.msg.err.noagentstock");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(error);
					//invocationContext.target = TARGET_FAILURE;
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
			if (!awbDetailVO.getOrigin().equalsIgnoreCase(attachAwbDetails.getOrigin())
					|| !awbDetailVO.getDestination().equalsIgnoreCase(attachAwbDetails.getDestination())) {
				errors = new ArrayList<ErrorVO>();
				ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.originDestMismatch");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				actionContext.addAllError(errors);
    			//invocationContext.target = TARGET_FAILURE;
    			return;
			}
		}
    	try {
    		// ATTACH THE SELECTED DSNS TO LISTED AWB
    		log.log(Log.INFO,"Going to attch AWB -------->>");
    		awbDetailVO.setDocumentSubType(documentFilterVO.getDocumentSubType());
    		log.log(Log.INFO, "Going to attch AWB -------->>", awbDetailVO);
    		containerDetailsVO.setActiveTab(outboundModel.getActiveTab());
    		containerDetailsVO.setFromScreen("Outbound");
			mailTrackingDefaultsDelegate.attachAWBDetails(awbDetailVO,containerDetailsVO);
		}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			//invocationContext.target = TARGET_FAILURE;
			return;
		}
		else {
			if((!"".equals(attachAwbDetails.getMasterDocumentNumber())
					&& AWBDetailVO.OPERATION_FLAG_INSERT.equals(awbDetailVO.getOperationFlag())
					&& AWBDetailVO.FLAG_YES.equals(isValidationRequired))
				|| ("".equals(attachAwbDetails.getMasterDocumentNumber())
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
					actionContext.addAllError(errors);
				//	invocationContext.target = TARGET_FAILURE;
					return;
				}
				log.log(Log.INFO,"Going to delete document from stock 6-------->>");
			}
		}
		// IF SAVE SUCCESSFUL
		//mailManifestSession.setAWBDetailVO(null);
		//mailManifestSession.setContainerDetailsVO(null);
		//mailManifestForm.setScreenStatus("CLOSE");
		//invocationContext.target = TARGET_SUCCESS;
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
    	String documentSubType="";
		try {
		String product=	findSystemParameterValue(MailConstantsVO.MAIL_AWB_PRODUCT);
		documentSubType= new MailTrackingDefaultsDelegate().findProductsByName(logonAttributes.getCompanyCode(),product);
		} catch (BusinessDelegateException businessDelegateException) {
			// TODO Auto-generated catch block
			handleDelegateException(businessDelegateException);
			;
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Added by A-8527 for IASCB-28086 Ends
    	DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    	documentFilterVO.setAirlineIdentifier(awbDetailVO.getOwnerId());
    	documentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	documentFilterVO.setDocumentNumber(awbDetailVO.getMasterDocumentNumber());
    	/*
    	 * Included as per BUG 28143
    	 */
    	//Added by A-8527 for IASCB-28086 Starts
    	//documentFilterVO.setDocumentSubType(MailConstantsVO.MAIL_DOCUMENT_SUBTYPE);
    	documentFilterVO.setDocumentSubType(documentSubType);
    	//Added by A-8527 for IASCB-28086 Ends
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
	private List<ErrorVO> validateForm(
			AttachAwbDetails attachAwbDetails) {
		List<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		if (attachAwbDetails.getStandardPieces()== 0) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.attachawb.msg.err.noStdPieces");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}
		if (attachAwbDetails.getStandardWeight() == 0) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.attachawb.msg.err.noStdWeight");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}
		return formErrors;
	}
	//Added by A-8527 for IASCB-28086 Starts
	private String findSystemParameterValue(String syspar) throws SystemException, BusinessDelegateException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}
	//Added by A-8527 for IASCB-28086 Ends
}