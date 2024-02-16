package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.AWBFilterVO;
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
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.AttachAwbDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ListAttachAwbCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	31-Oct-2018		:	Draft
 */
public class ListAttachAwbCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ListAttachAwbCommand");
	
	private static final int DIV_VALUE = 10;
	private static final int MOD_VALUE = 10;
	private static final int AWB_LENGTH = 8;
	private static final String AWB_NOT_IN_STOCK = "mailtracking.defaults.attachawb.msg.err.noagentstock";
	private static final String LIST_ATTACH_AWB_COMMAND = "ListAttachAwbCommand";
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ArrayList<ContainerDetails> containerDetailsCollection= 
				 mailinboundModel.getContainerDetailsCollection();
		ContainerDetails containerDetails =null;
		containerDetails =
					containerDetailsCollection.get(0);
		AttachAwbDetails attachAwbDetails=
				mailinboundModel.getAttachAwbDetails();
		ResponseVO responseVO = new ResponseVO();
		
		AWBFilterVO awbFilterVO = new AWBFilterVO();
		Collection<ErrorVO> errors = null;
		
		errors = validateAttachAwbDetails(attachAwbDetails);
		
		if (errors != null && errors.size() > 0) {			
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		String shipmntPrefix = attachAwbDetails.getShipmentPrefix();
		AirlineValidationVO airlineValidationVO = null;
		
		if(attachAwbDetails.getShipmentPrefix()!=null && 
				attachAwbDetails.getShipmentPrefix().trim().length() > 0) {
			airlineValidationVO = 
					validateShipmentPrefix(shipmntPrefix,actionContext,logonAttributes);
			if(airlineValidationVO != null) {
    			boolean isAWBValid = false;
    			if(attachAwbDetails.getDocumentNumber()!=null && 
    					attachAwbDetails.getDocumentNumber().trim().length()>0){
    				isAWBValid=validateCheckDigit(
    						attachAwbDetails.getDocumentNumber(),airlineValidationVO.getAwbCheckDigit());
    				if(!isAWBValid) {
    	    			errors = new ArrayList<ErrorVO>();
    					ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.invalidAWB"); 
    					error.setErrorDisplayType(ErrorDisplayType.ERROR);
    					errors.add(error);	
    					actionContext.addAllError((List<ErrorVO>) errors);
    	    			return;  
        			}
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
						actionContext.addAllError((List<ErrorVO>) errors);
						return;
    			   }
    			}
					
    			}else {
    				if(shipmntPrefix.equals(logonAttributes.getOwnAirlineNumericCode())) {
					DocumentFilterVO documentFilterVO = handleFilterVO(
							airlineValidationVO,
							attachAwbDetails.getAgentCode(),attachAwbDetails.getDestination(),attachAwbDetails.getOrigin());
					populateAWBDetails(documentFilterVO, airlineValidationVO,
							attachAwbDetails,actionContext);
    				}else {
					
					errors = new ArrayList<>();
					ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.invalidAWB"); 
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);	
					actionContext.addAllError((List<ErrorVO>) errors);
	    			return;  
    				}
    				}
			}
			
			else {
    			errors = new ArrayList<ErrorVO>();
				ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.invalidshipmentprefix");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);	
				actionContext.addAllError((List<ErrorVO>) errors);
    			return;
			}
		}
		
		awbFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		awbFilterVO.setMasterDocumentNumber(attachAwbDetails
				.getDocumentNumber());
		
		awbFilterVO.setUldNumber(containerDetails.getContainerno());
		awbFilterVO.setOrigin(attachAwbDetails.getOrigin());
		awbFilterVO.setDestination(attachAwbDetails.getDestination());
		awbFilterVO.setDocumentOwnerIdentifier(airlineValidationVO
				.getAirlineIdentifier());
		awbFilterVO.setAgentCode(attachAwbDetails.getAgentCode());
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		AWBDetailVO awbDetailVO = null;
		log.log(Log.FINE, "awbFilterVO ---> ", awbFilterVO);
		try {
			awbDetailVO = mailTrackingDefaultsDelegate.findAWBDetails(awbFilterVO);

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		else {
			if (awbDetailVO != null) {
				if(!String.valueOf(awbDetailVO.getStatedPieces()).equals(attachAwbDetails.getStandardPieces())||attachAwbDetails.getStandardWeight()!=awbDetailVO.getStatedWeight().getDisplayValue()) {
					errors = new ArrayList<>();
					ErrorVO error = new ErrorVO("mailtracking.defaults.attachawb.msg.err.invalidshipmentpieceorweight");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);	
					actionContext.addAllError((List<ErrorVO>)errors);
					
					}
				awbDetailVO.setOperationFlag(AWBDetailVO.OPERATION_FLAG_UPDATE);
				awbDetailVO.setShipmentPrefix(attachAwbDetails.getShipmentPrefix());
				attachAwbDetails.setAwbDetailVO(awbDetailVO);
			    int pieces=awbDetailVO.getStatedPieces();
			    attachAwbDetails.setStatedPieces(Integer.toString(pieces));  
                attachAwbDetails.setStatedWeightMeasure(awbDetailVO.getStatedWeight());
                attachAwbDetails.setStatedWeight(attachAwbDetails.getStatedWeightMeasure().getDisplayValue());
				attachAwbDetails.setShipmentDescription(awbDetailVO.getShipmentDescription());
				attachAwbDetails.setDocumentNumber(awbDetailVO.getMasterDocumentNumber());
				attachAwbDetails.setMasterDocumentNumber(awbDetailVO.getMasterDocumentNumber());
			}else{
				awbDetailVO = new AWBDetailVO();
				awbDetailVO.setOperationFlag(AWBDetailVO.OPERATION_FLAG_INSERT);
				awbDetailVO.setOwnerId(airlineValidationVO.getAirlineIdentifier());
				awbDetailVO.setOwnerCode(airlineValidationVO.getAlphaCode());
				awbDetailVO.setCompanyCode(airlineValidationVO.getCompanyCode());
				awbDetailVO.setDestination(attachAwbDetails.getDestination()
						.toUpperCase());
				awbDetailVO.setMasterDocumentNumber(attachAwbDetails
						.getDocumentNumber());
				awbDetailVO.setShipmentPrefix(attachAwbDetails.getShipmentPrefix());
				awbDetailVO.setOrigin(attachAwbDetails.getOrigin().toUpperCase());
				attachAwbDetails.setDocumentNumber(attachAwbDetails
						.getDocumentNumber());
				attachAwbDetails.setMasterDocumentNumber(attachAwbDetails
						.getMasterDocumentNumber());
				attachAwbDetails.setAwbDetailVO(awbDetailVO);
			}
		}
		
		ArrayList<ContainerDetails> containerDetailsCollectionToSave=
				new ArrayList<ContainerDetails>();
		containerDetailsCollectionToSave.add(containerDetails);
		mailinboundModel.setContainerDetailsCollection(containerDetailsCollectionToSave);
		mailinboundModel.setAttachAwbDetails(attachAwbDetails);
		ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
    	log.exiting(LIST_ATTACH_AWB_COMMAND,"execute");
		
	}
	
	
	
	private void populateAWBDetails(DocumentFilterVO documentFilterVO, AirlineValidationVO airlineValidationVO,
			AttachAwbDetails attachAwbDetails, ActionContext actionContext) {
		
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
			return;
		}

		if (documentValidationVO == null) {
			ErrorVO error = new ErrorVO(
					"mailtracking.defaults.attachawb.msg.err.noDocumentPresent");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			actionContext.addError(error);
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
			attachAwbDetails.setDocumentNumber(documentNumber);
			attachAwbDetails.setMasterDocumentNumber(documentNumber);
			attachAwbDetails.setAwbDetailVO(awbDetailVO);
		}
		
	}



	private DocumentFilterVO handleFilterVO(AirlineValidationVO airlineValidationVO, String agentCode,
			String destination, String origin) {
		
		log.entering(LIST_ATTACH_AWB_COMMAND, "handleFilterVO");

		DocumentFilterVO documentFilterVO = new DocumentFilterVO();

		documentFilterVO.setCompanyCode(airlineValidationVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(airlineValidationVO
				.getAirlineIdentifier());
		/*documentFilterVO
				.setDocumentSubType(MailConstantsVO.MAIL_CONST.toUpperCase()); */ 
		documentFilterVO.setDocumentType(MailConstantsVO.DOCUMENT_TYPE);
		documentFilterVO.setStockOwner(agentCode);
		documentFilterVO.setAwbDestination(destination);
		documentFilterVO.setAwbOrigin(origin);
		//added by a-7779 starts
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.MAIL_AWB_PRODUCT);
		Map<String, String> systemParameterMap=null;
		try {
			systemParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters);
		} catch (BusinessDelegateException businessDelegateException) {
			Collection<ErrorVO> errorVOs = handleDelegateException(businessDelegateException);
			log.log(Log.INFO,"systemParameterMap fetch failed");
		}
		String documentSubType="";
		try {
			documentSubType = new MailTrackingDefaultsDelegate()
					.findAutoPopulateSubtype(documentFilterVO);
		} catch (BusinessDelegateException e) {
			log.log(Log.INFO,"documentSubType validation failed");
		}
		if(documentSubType ==null || documentSubType.trim().length() == 0){
		String product= null;
		if(systemParameterMap!=null && systemParameterMap.size()>0){
			product=systemParameterMap.get(MailConstantsVO.MAIL_AWB_PRODUCT);
		}
			
        if(product!=null){
        	try {
				documentSubType=new MailTrackingDefaultsDelegate().findProductsByName(airlineValidationVO.getCompanyCode(),product);
			} catch (BusinessDelegateException e) {
				log.log(Log.INFO,"documentSubType validation failed");
				}
			}
        }
        if(documentSubType!=null && documentSubType.trim().length()>0){
        	documentFilterVO.setDocumentSubType(documentSubType);
        }
      //added by a-7779 ends
		log.log(Log.INFO, "DocumentFilterVO----->>", documentFilterVO);
		return documentFilterVO;
	}



	private DocumentValidationVO getDocumentValidationVO(AttachAwbDetails attachAwbDetails,
			AirlineValidationVO airlineValidationVO, LogonAttributes logonAttributes) {
		
		DocumentValidationVO documentValidationVO = null;
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
		documentFilterVO.setDocumentNumber(attachAwbDetails
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



	private boolean validateCheckDigit(String documentNumber, int awbCheckDigit) {
		log.entering("ListAddAWBCommand", "validateCheckDigit");

    	int awbNum = 0;
    	int remainder = 0;

        if (documentNumber.length() == AWB_LENGTH){
        	if(awbCheckDigit != 0) {
        		try {
					awbNum = Integer.parseInt(documentNumber)/DIV_VALUE;
					remainder = Integer.parseInt(documentNumber)%MOD_VALUE;
        		}
        		catch(NumberFormatException numberFormatException){
        			return false;
        		}
	            if ((awbNum % awbCheckDigit )  == remainder) {
	                return true;
	            }
        	}
        }
        log.exiting("ListAddAWBCommand", "validateCheckDigit");
        return false;
	}



	private AirlineValidationVO validateShipmentPrefix(String shipmntPrefix, ActionContext actionContext,
			LogonAttributes logonAttributes) {
		
		log.entering(LIST_ATTACH_AWB_COMMAND, "validateShipmentPrefix");

		Collection<ErrorVO> errors = null;
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		try {
			airlineValidationVO = airlineDelegate.validateNumericCode(logonAttributes.getCompanyCode(), shipmntPrefix);
		} catch (BusinessDelegateException businessDelegateException) {

			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "AirlineValidationVO ---> ", airlineValidationVO);
		log.exiting(LIST_ATTACH_AWB_COMMAND, "validateShipmentPrefix");

		return airlineValidationVO;
	}

	
	
	private Collection<ErrorVO> validateAttachAwbDetails(
			AttachAwbDetails attachAwbDetails) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		if (("").equals(attachAwbDetails.getShipmentPrefix())) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.attachawb.msg.err.noAwb");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}

		return errors;
	} 

}
