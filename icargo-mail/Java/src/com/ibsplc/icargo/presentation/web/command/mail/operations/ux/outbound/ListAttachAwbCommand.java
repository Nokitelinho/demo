package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.AWBFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.AttachAwbDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListAttachAwbCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ListAttachAwbCommand");
	
	private static final int DIV_VALUE = 10;
	private static final int MOD_VALUE = 10;
	private static final int AWB_LENGTH = 8;
	private static final String AWB_NOT_IN_STOCK = "mailtracking.defaults.attachawb.msg.err.noagentstock";
	
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
		List<ErrorVO> errors= null;
		AttachAwbDetails attachAwbDetails=
				outboundModel.getAttachAwbDetails();
		AWBFilterVO awbFilterVO = new AWBFilterVO();
		ResponseVO responseVO = new ResponseVO();
		String shipmntPrefix = attachAwbDetails.getShipmentPrefix();
		AirlineValidationVO airlineValidationVO = null;
    	
		/*
		 * Modified By KarthicK V as the part of the NCA Mail Tracking Bug Fix ..
		 * 
		 * 
		 */
		if(attachAwbDetails.getShipmentPrefix()!=null && attachAwbDetails.getShipmentPrefix().trim().length() > 0) {	
    		/*
    		 * Validate Prefix
   		     */   		
    		airlineValidationVO = validateShipmentPrefix(shipmntPrefix,logonAttributes);
    		if(airlineValidationVO != null) {
    			boolean isAWBValid = false;
    			if(attachAwbDetails.getMasterDocumentNumber()!=null && 
    					attachAwbDetails.getMasterDocumentNumber().trim().length()>0){
    				isAWBValid=validateCheckDigit(attachAwbDetails.getMasterDocumentNumber(),airlineValidationVO.getAwbCheckDigit());
    			if(!isAWBValid) {
	    			errors = new ArrayList<ErrorVO>();
					ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.invalidAWB"); //Modified by A-8164 for ICRD-259683
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);	
					actionContext.addAllError(errors);
	    			//invocationContext.target = TARGET_FAILURE;
	    			return;  
    				}
					// Validate AWB is in Stock
					// Added for ICRD-211205 starts
					DocumentValidationVO documentValidationVO = null;
					if(shipmntPrefix.equals(logonAttributes.getOwnAirlineNumericCode())) {
					documentValidationVO = getDocumentValidationVO(
							attachAwbDetails, airlineValidationVO,
							logonAttributes);
					if (documentValidationVO == null) {
						errors = new ArrayList<ErrorVO>();
						ErrorVO error = new ErrorVO(AWB_NOT_IN_STOCK);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						actionContext.addAllError(errors);
						//invocationContext.target = TARGET_FAILURE;
						return;
    			   }
					}
				} else {
					if(shipmntPrefix.equals(logonAttributes.getOwnAirlineNumericCode())) {
					DocumentFilterVO documentFilterVO = handleFilterVO(
							airlineValidationVO,
							attachAwbDetails.getAgentCode(),attachAwbDetails.getDestination(),attachAwbDetails.getOrigin());//modified by a-7871 for ICRD-262511);
					populateAWBDetails(documentFilterVO, airlineValidationVO,
							attachAwbDetails,outboundModel,
							actionContext);
					//invocationContext.target = TARGET_SUCCESS;
					//mailManifestForm.setScreenStatus("LISTED");
					//return;
					} 
					else{
						errors = new ArrayList<>();
						ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.invalidAWB"); //Modified by A-8164 for ICRD-259683
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);	
						actionContext.addAllError(errors);
		    			return;  
						}
    			 }
    			// Added for ICRD-211205 ends
    		 }
    		else {
	    			errors = new ArrayList<ErrorVO>();
					ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.invalidshipmentprefix");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);	
					actionContext.addAllError(errors);
	    			//invocationContext.target = TARGET_FAILURE;
	    			return;
    		}
    	}
			awbFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			awbFilterVO.setMasterDocumentNumber(attachAwbDetails
					.getMasterDocumentNumber());
			awbFilterVO.setUldNumber(containerDetailsVO.getContainerNumber());
			awbFilterVO.setOrigin(attachAwbDetails.getOrigin());
			awbFilterVO.setDestination(attachAwbDetails.getDestination());
			awbFilterVO.setDocumentOwnerIdentifier(airlineValidationVO
					.getAirlineIdentifier());
			awbFilterVO.setAgentCode(attachAwbDetails.getAgentCode());
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
			actionContext.addAllError(errors);
			//invocationContext.target = TARGET_FAILURE;
			return;
		}
		else {
			if (awbDetailVO != null) { 
				/*IASCB-169213**/
				if((awbDetailVO.getStatedPieces())!=(attachAwbDetails.getStandardPieces())||attachAwbDetails.getStandardWeight()!=awbDetailVO.getStatedWeight().getDisplayValue()) {
					errors = new ArrayList<>();
					ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.invalidshipmentpieceorweight");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);	
					actionContext.addAllError((List<ErrorVO>)errors);
					}
				/*IASCB-169213**/
				awbDetailVO.setOperationFlag(AWBDetailVO.OPERATION_FLAG_UPDATE);
				awbDetailVO.setShipmentPrefix(attachAwbDetails.getShipmentPrefix());
				//mailManifestSession.setAWBDetailVO(awbDetailVO);
				
				
				/*mailManifestForm.setStdWeight(
						mailManifestForm.getStdWeight() + 
						awbDetailVO.getStatedWeight());*/
			
			
					attachAwbDetails.setStatedPieces(awbDetailVO.getStatedPieces());
					attachAwbDetails.setStatedWeight(awbDetailVO.getStatedWeight());
				attachAwbDetails.setAwbDetailVO(awbDetailVO);
				attachAwbDetails.setShipmentDescription(awbDetailVO.getShipmentDescription());
				attachAwbDetails.setMasterDocumentNumber(awbDetailVO.getMasterDocumentNumber());
			}else{//Added for ICRD-259708
				awbDetailVO = new AWBDetailVO();
				awbDetailVO.setOperationFlag(AWBDetailVO.OPERATION_FLAG_INSERT);
				awbDetailVO.setOwnerId(airlineValidationVO.getAirlineIdentifier());
				awbDetailVO.setOwnerCode(airlineValidationVO.getAlphaCode());
				awbDetailVO.setCompanyCode(airlineValidationVO.getCompanyCode());
				awbDetailVO.setDestination(attachAwbDetails.getDestination()!=null? 
						attachAwbDetails.getDestination().toUpperCase():null);//for ICRD-341853
				awbDetailVO.setMasterDocumentNumber(attachAwbDetails
						.getMasterDocumentNumber());
				awbDetailVO.setShipmentPrefix(attachAwbDetails.getShipmentPrefix());
				awbDetailVO.setOrigin(attachAwbDetails.getOrigin()!=null
						?attachAwbDetails.getOrigin().toUpperCase():null);//for ICRD-341853 
				attachAwbDetails.setMasterDocumentNumber(attachAwbDetails
						.getMasterDocumentNumber());
				attachAwbDetails.setAwbDetailVO(awbDetailVO);
				//mailManifestSession.setAWBDetailVO(awbDetailVO);
			}
		}
		//mailManifestForm.setScreenStatus("LISTED");
		ArrayList<ContainerDetails> containerDetailsCollectionToSave=
				new ArrayList<ContainerDetails>();
		containerDetailsCollectionToSave.add(containerDetails);
		outboundModel.setContainerDetailsCollection(containerDetailsCollectionToSave);
	    outboundModel.setAttachAwbDetails(attachAwbDetails);
		ArrayList<OutboundModel> result = new ArrayList<OutboundModel>();
		result.add(outboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
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
			AttachAwbDetails attachAwbDetails, AirlineValidationVO airlineValidationVO,LogonAttributes logonAttributes) {
		DocumentValidationVO documentValidationVO = null;
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
		documentFilterVO.setDocumentNumber(attachAwbDetails.getMasterDocumentNumber());
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
			AttachAwbDetails attachAwbDetails,
			OutboundModel outboundModel,
			ActionContext actionContext) {
		String documentNumber = null;
		DocumentValidationVO documentValidationVO = null;
		AWBDetailVO awbDetailVO = null;
		try {
			documentValidationVO = new MailTrackingDefaultsDelegate()
					.findNextDocumentNumber(documentFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			actionContext
					.addAllError(handleDelegateException(businessDelegateException));
			//invocationContext.target = TARGET_FAILURE;
			return;
		}
		if (documentValidationVO == null) {
			ErrorVO error = new ErrorVO(
					"mailtracking.defaults.attachawb.msg.err.noDocumentPresent");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			actionContext.addError(error);
			//invocationContext.target = TARGET_FAILURE;
			return;
		} else {
			documentNumber = documentValidationVO.getDocumentNumber();
			documentFilterVO.setDocumentNumber(documentNumber);
			awbDetailVO = new AWBDetailVO();
			awbDetailVO.setOperationFlag(AWBDetailVO.OPERATION_FLAG_INSERT);
			awbDetailVO.setOwnerId(airlineValidationVO.getAirlineIdentifier());
			awbDetailVO.setOwnerCode(airlineValidationVO.getAlphaCode());
			awbDetailVO.setCompanyCode(airlineValidationVO.getCompanyCode());
			awbDetailVO.setDestination(attachAwbDetails.getDestination()
					.toUpperCase());
			awbDetailVO.setMasterDocumentNumber(documentNumber);
			awbDetailVO.setShipmentPrefix(attachAwbDetails.getShipmentPrefix());
			awbDetailVO.setOrigin(attachAwbDetails.getOrigin().toUpperCase());
			attachAwbDetails.setMasterDocumentNumber(documentNumber);
			attachAwbDetails.setAwbDetailVO(awbDetailVO);
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
		//Added by A-8527 for IASCB-28086 Starts
		String documentSubType="";
		
		//Added by A-8527 for IASCB-28086 Ends
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(airlineValidationVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(airlineValidationVO
				.getAirlineIdentifier());
		//documentFilterVO.setDocumentSubType(MailConstantsVO.MAIL_DOCUMENT_SUBTYPE);
		documentFilterVO.setDocumentType(MailConstantsVO.DOCUMENT_TYPE);
		documentFilterVO.setStockOwner(agentCode);
		documentFilterVO.setAwbDestination(dest);//added by a-7871 for ICRD-262511
		documentFilterVO.setAwbOrigin(origin);
		//documentFilterVO.setDocumentSubType(MailConstantsVO.MAIL_CONST.toUpperCase());//commented by A-8527 for IASCB-28086 
		try {
			documentSubType = new MailTrackingDefaultsDelegate()
					.findAutoPopulateSubtype(documentFilterVO);
			if(documentSubType ==null || documentSubType.trim().length() == 0){
				String product=	findSystemParameterValue(MailConstantsVO.MAIL_AWB_PRODUCT);
				documentSubType= new MailTrackingDefaultsDelegate().findProductsByName(airlineValidationVO.getCompanyCode(),product);
			}
		} catch (BusinessDelegateException businessDelegateException) {
			// TODO Auto-generated catch block
			handleDelegateException(businessDelegateException);
			;
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		documentFilterVO.setDocumentSubType(documentSubType);//Added by A-8527 for IASCB-28086 
		log.log(Log.INFO, "DocumentFilterVO----->>", documentFilterVO);
		return documentFilterVO;
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
