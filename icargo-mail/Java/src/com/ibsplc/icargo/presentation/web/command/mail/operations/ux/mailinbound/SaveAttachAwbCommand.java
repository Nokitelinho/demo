package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
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
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.AttachAwbDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.SaveAttachAwbCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	31-Oct-2018		:	Draft
 */
public class SaveAttachAwbCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ListAttachAwbCommand");
	private static final String STOCK_REQ_PARAMETER = "mailtracking.defaults.stockcheckrequired";
	
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
		Collection<ErrorVO> errors = null;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
	    		new MailTrackingDefaultsDelegate();
		ContainerDetailsVO containerDetailsVO=null;
		ArrayList<DSNDetails> dsnDetailsCollection=
				containerDetails.getDsnDetailsCollection();
		ArrayList<MailBagDetails> mailBagDetailsCollection=
				containerDetails.getMailBagDetailsCollection();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		Collection<DSNVO> selectedDsnVos=
				 new ArrayList<DSNVO>();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		Collection<MailbagVO> selectedMailBagVos=
				 new ArrayList<MailbagVO>();
		
		MailArrivalVO mailArrivalVO =null;
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		OperationalFlightVO operationalFlightVO=null;
		operationalFlightVO=
				MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails, logonAttributes);
		try {
			mailArrivalVO = 
					new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(operationalFlightVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		if(null!=mailArrivalVO){
			for(ContainerDetailsVO containerDetailsVOIterate:mailArrivalVO.getContainerDetails()){
				if(containerDetails.getContainerno().equals(containerDetailsVOIterate.getContainerNumber())){
					containerDetailsVO=containerDetailsVOIterate;
					break;
							
				}
			}
			
		}
		
		if(mailArrivalVO.getContainerDetails()!=null){
			for(ContainerDetailsVO containerDetailsVOIterate:mailArrivalVO.getContainerDetails()){
				if(containerDetailsVOIterate.getContainerNumber().equals(containerDetails.getContainerno())){
					containerDetailsVO=containerDetailsVOIterate;
					break;
				}
			}
			dsnDetailsMap.clear();
			if(null!=dsnDetailsCollection&&dsnDetailsCollection.size()>0){
				for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
					String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
							.append(dsnvo.getDestinationExchangeOffice())
								.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
									.append(dsnvo.getYear()).toString();
					dsnDetailsMap.put(dsnKey, dsnvo);	
				}
				selectedDsnVos.clear();
				for(DSNDetails dsnDetails:containerDetails.getDsnDetailsCollection()){
					String dsnKey=new StringBuilder(dsnDetails.getDsn()).append(dsnDetails.getOriginExchangeOffice())
							.append(dsnDetails.getDestinationExchangeOffice())
								.append(dsnDetails.getMailCategoryCode()).append(dsnDetails.getMailSubclass())
									.append(dsnDetails.getYear()).toString();
					if(dsnDetailsMap.containsKey(dsnKey)){
						selectedDsnVos.add(dsnDetailsMap.get(dsnKey));
					}
				}
				selectedMailBagVos.clear();
				for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
				
					StringBuilder dsnKey = new StringBuilder();
					dsnKey.append(dsnvo.getOriginExchangeOffice())
							.append(dsnvo.getDestinationExchangeOffice())
							.append(dsnvo.getMailCategoryCode())
							.append(dsnvo.getMailSubclass()).append(dsnvo.getYear())
							.append(dsnvo.getDsn());
					for(MailbagVO mailBagDetails:containerDetailsVO.getMailDetails()){
						StringBuilder mailKey = new StringBuilder();
						mailKey.append(mailBagDetails.getOoe())
						.append(mailBagDetails.getDoe())
						.append(mailBagDetails.getMailCategoryCode())
						.append(mailBagDetails.getMailSubclass()).append(mailBagDetails.getYear())
						.append(mailBagDetails.getDespatchSerialNumber());
						if(dsnKey.toString().equals(mailKey.toString())){
							selectedMailBagVos.add(mailBagDetails);
						
						}
					}
				}
				containerDetailsVO.setDsnVOs(selectedDsnVos);
				containerDetailsVO.setMailDetails(selectedMailBagVos);
			}
			
			if(null!=mailBagDetailsCollection&&mailBagDetailsCollection.size()>0){
				mailDetailsMap.clear();
				for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
					String mailKey=mailbagVO.getMailbagId();
					mailDetailsMap.put(mailKey, mailbagVO);	
				}
				selectedMailBagVos.clear();
				for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
					String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
							.append(dsnvo.getDestinationExchangeOffice())
								.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
									.append(dsnvo.getYear()).toString();
					dsnDetailsMap.put(dsnKey, dsnvo);	
				}
				selectedDsnVos.clear();
				
				for(MailBagDetails mailBagDetails:containerDetails.getMailBagDetailsCollection()){
					String mailBagKey=new StringBuilder(mailBagDetails.getDSN()).append(mailBagDetails.getOoe())
							.append(mailBagDetails.getDoe()).append(mailBagDetails.getCategory()).append(mailBagDetails.getSubClass())
							.append(mailBagDetails.getYear()).toString();
					if(mailDetailsMap.containsKey(mailBagDetails.getMailBagId())){
						selectedMailBagVos.add(mailDetailsMap.get(mailBagDetails.getMailBagId()));
					}
					if(dsnDetailsMap.containsKey(mailBagKey)){
						selectedDsnVos.add(dsnDetailsMap.get(mailBagKey));  
					}
				}
				
				Collection<DSNVO> dsnvos= new LinkedHashSet <DSNVO>();//To remove dulplicates from selectedDsnVos
				dsnvos.addAll(selectedDsnVos);
				selectedDsnVos.clear();
				selectedDsnVos.addAll(dsnvos);
				containerDetailsVO.setDsnVOs(selectedDsnVos); 
				containerDetailsVO.setMailDetails(selectedMailBagVos);
			}
			
		
			
			
		}
		
		
		errors = validateAttachAwbDetails(attachAwbDetails);
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
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
			return;
		}
		 double statedVolume;
		awbDetailVO.setShipmentDescription(attachAwbDetails.getShipmentDescription());
			if(((attachAwbDetails.getStatedPieces()==attachAwbDetails.getStandardPieces())&&
	                attachAwbDetails.getStatedWeightMeasure().getDisplayValue()==(attachAwbDetails.getStandardWeight())) ||(attachAwbDetails.getStatedPieces()!=null && attachAwbDetails.getStatedPieces().equals("0"))||(attachAwbDetails.getStatedPieces()==null))
		{
		awbDetailVO.setStatedPieces(Integer.parseInt(attachAwbDetails.getStandardPieces()));
		awbDetailVO.setStatedWeight(attachAwbDetails.getStdWeightMeasure());
		statedVolume = attachAwbDetails.getStandardWeight()/Double.parseDouble(attachAwbDetails.getDensity());
		}
		else {
			awbDetailVO.setStatedPieces(Integer.parseInt(attachAwbDetails.getStatedPieces()));
			awbDetailVO.setStatedWeight(attachAwbDetails.getStatedWeightMeasure());
			statedVolume = attachAwbDetails.getStatedWeightMeasure().getDisplayValue()/Double.parseDouble(attachAwbDetails.getDensity());
		}
		awbDetailVO.setStatedWeightCode(attachAwbDetails.getWeightStandard());
		
		 
	        					
		    if(MailConstantsVO.NO_VOLUME != statedVolume) {
			   if(MailConstantsVO.MINIMUM_VOLUME > statedVolume) {
				   statedVolume = MailConstantsVO.MINIMUM_VOLUME;
			   }else {
				   statedVolume = Double.parseDouble(TextFormatter.formatDouble(statedVolume , 2));
			   }
		    }
		    
		    awbDetailVO.setStatedVolume(new Measure(UnitConstants.VOLUME, statedVolume));  
		    awbDetailVO.setAgentCode(agentCode);
			log.log(Log.INFO, "AwbDetailVO----->>", awbDetailVO);
			DocumentFilterVO documentFilterVO = handleFilterVO(awbDetailVO,logonAttributes,agentCode);
			
			if ("".equals(attachAwbDetails.getDocumentNumber())
					&& AWBDetailVO.OPERATION_FLAG_INSERT.equals(awbDetailVO.getOperationFlag())) {

				log.log(Log.INFO,"Going to find the next Document Number -------->>");
				
			}
			
			else if (!"".equals(attachAwbDetails.getDocumentNumber())
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
						return;
					}
					log.log(Log.INFO, "DocumentValidationVO-------->>",documentValidationVO);
					if (documentValidationVO == null) {
						ErrorVO error =
							new ErrorVO("mailtracking.defaults.attachawb.msg.err.noagentstock");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(error);
						return;
					}
				}
			}
			
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
					actionContext.addAllError((List<ErrorVO>) errors);
	    			return;
				}
			}
			
    		containerDetailsVO.setActiveTab(mailinboundModel.getActiveTab());
			
			try {
	    		// ATTACH THE SELECTED DSNS TO LISTED AWB
	    		log.log(Log.INFO,"Going to attch AWB -------->>");
	    		awbDetailVO.setDocumentSubType(documentFilterVO.getDocumentSubType());
	    		log.log(Log.INFO, "Going to attch AWB -------->>", awbDetailVO);
				mailTrackingDefaultsDelegate.attachAWBDetails(awbDetailVO,containerDetailsVO);

			}catch (BusinessDelegateException businessDelegateException) {
	    		errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}
			
			else {
				if((!"".equals(attachAwbDetails.getDocumentNumber())
						&& AWBDetailVO.OPERATION_FLAG_INSERT.equals(awbDetailVO.getOperationFlag())
						&& AWBDetailVO.FLAG_YES.equals(isValidationRequired))
					|| ("".equals(attachAwbDetails.getDocumentNumber())
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
						actionContext.addAllError((List<ErrorVO>) errors);
						return;
					}
					log.log(Log.INFO,"Going to delete document from stock 6-------->>");
				}
			}
			
			ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
			result.add(mailinboundModel);
			responseVO.setResults(result);
			responseVO.setStatus("success");
			ErrorVO error = new ErrorVO("mail.operations.succ.attachawbsuccess");      
			error.setErrorDisplayType(ErrorDisplayType.INFO);
	        actionContext.addError(error); 
			actionContext.setResponseVO(responseVO);
	    	log.exiting("ScreenLoadAttachAwbCommand","execute");

				
			
		
	
		
	}
	
	
	
	private DocumentFilterVO handleFilterVO(AWBDetailVO awbDetailVO, LogonAttributes logonAttributes,
			String agentCode) {
		
		log.entering("SaveAttachAwbCommand","handleFilterVO");

    	DocumentFilterVO documentFilterVO = new DocumentFilterVO();

    	documentFilterVO.setAirlineIdentifier(awbDetailVO.getOwnerId());
    	documentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	documentFilterVO.setDocumentNumber(awbDetailVO.getMasterDocumentNumber());
    	documentFilterVO.setDocumentSubType(MailConstantsVO.MAIL_DOCUMENT_SUBTYPE);
    	documentFilterVO.setDocumentType(MailConstantsVO.DOCUMENT_TYPE);
    	documentFilterVO.setStockOwner(agentCode);

    	log.log(Log.INFO, "DocumentFilterVO----->>", documentFilterVO);
		return documentFilterVO;
	}

	
	
	private Collection<ErrorVO> validateAttachAwbDetails(AttachAwbDetails attachAwbDetails) {
		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();

		if (null==attachAwbDetails.getStandardPieces()||
				"".equals(attachAwbDetails.getStandardPieces())) {
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

	
}
