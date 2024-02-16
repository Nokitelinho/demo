package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OnwardRouting;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.AttachRoutingDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ListAttachRoutingCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	20-Nov-2018		:	Draft
 */
public class ListAttachRoutingCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String CONSGNMNT_TYPE="mailtracking.defaults.consignmentdocument.type";
	private static final String STATUS_NO_RESULTS = "mailtracking.defaults.consignment.status.noresultsfound";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ListAttachRoutingCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("ListAttachRoutingCommand", "execute");
		LogonAttributes logonAttributes =      
				(LogonAttributes) getLogonAttribute();  
		MailinboundModel mailinboundModel =  
				(MailinboundModel) actionContext.getScreenModel();
		AttachRoutingDetails attachRoutingDetails=
				(AttachRoutingDetails) mailinboundModel.getAttachRoutingDetails();
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs=null;
		Collection<ContainerDetails> containerDetailsCollection=
				mailinboundModel.getContainerDetailsCollection();
		MailArrivalVO mailArrivalVO =null;
		
		
		ContainerDetails containerDetail=
				mailinboundModel.getContainerDetail();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		Collection<DSNVO> selectedDsnVos=
				 new ArrayList<DSNVO>();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		Collection<MailbagVO> selectedMailBagVos=
				 new ArrayList<MailbagVO>();
		Collection<ContainerDetailsVO> containerDetailsVOs =null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		
		HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
				new HashMap<String,ContainerDetailsVO>();
		ArrayList<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
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
			containerDetailsVOs=mailArrivalVO.getContainerDetails();
		}
		/**
		 * For finding the selected containers
		 */
		for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs){
			containerDetailsVoMap.put(
					containerDetailsVO.getContainerNumber(), containerDetailsVO);
		}
		
		for(ContainerDetails containerDetailIterate:containerDetailsCollection){
			
			if(containerDetailsVoMap.containsKey(containerDetailIterate.getContainerno())){
				containerDetailsVosSelected.add(
						containerDetailsVoMap.get(containerDetailIterate.getContainerno()));
			}
		}
		containerDetailsVOs=containerDetailsVosSelected;
		
		if(containerDetailsVOs.size()>0){
			if(null!=containerDetail&&null!=containerDetail.getDsnDetailsCollection()){
				for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs){
					if(null!=containerDetail.getDsnDetailsCollection()&&containerDetail.getDsnDetailsCollection().size()>0){
						for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
							String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
									.append(dsnvo.getDestinationExchangeOffice())
										.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
											.append(dsnvo.getYear()).toString();
							dsnDetailsMap.put(dsnKey, dsnvo);	
						}
						selectedDsnVos.clear();
						for(DSNDetails dsnDetails:containerDetail.getDsnDetailsCollection()){
							String dsnKey=new StringBuilder(dsnDetails.getDsn()).append(dsnDetails.getOriginExchangeOffice())
									.append(dsnDetails.getDestinationExchangeOffice())
										.append(dsnDetails.getMailCategoryCode()).append(dsnDetails.getMailSubclass())
											.append(dsnDetails.getYear()).toString();
							
							
							if(dsnDetailsMap.containsKey(dsnKey)){
								selectedDsnVos.add(dsnDetailsMap.get(dsnKey));
								containerDetailsVO.setDsnVOs(selectedDsnVos);
							}
						}
						
					}
					else if(null!=containerDetail.getMailBagDetailsCollection()&&containerDetail.getMailBagDetailsCollection().size()>0){
						mailDetailsMap.clear();
						for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
							String mailKey=new StringBuilder(mailbagVO.getDespatchSerialNumber()).append(mailbagVO.getOoe())
									.append(mailbagVO.getDoe()).append(mailbagVO.getMailCategoryCode()).append(mailbagVO.getMailSubclass())
									.append(mailbagVO.getYear()).toString();
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
						
						for(MailBagDetails mailBagDetails:containerDetail.getMailBagDetailsCollection()){
							String mailBagKey=new StringBuilder(mailBagDetails.getDSN()).append(mailBagDetails.getOoe())
									.append(mailBagDetails.getDoe()).append(mailBagDetails.getCategory()).append(mailBagDetails.getSubClass())
									.append(mailBagDetails.getYear()).toString();
							if(mailDetailsMap.containsKey(mailBagKey)){
								selectedMailBagVos.add(mailDetailsMap.get(mailBagKey));
							}
							if(dsnDetailsMap.containsKey(mailBagKey)){
								selectedDsnVos.add(dsnDetailsMap.get(mailBagKey));
							}
						}
						containerDetailsVO.setDsnVOs(selectedDsnVos);
						containerDetailsVO.setMailDetails(selectedMailBagVos);
						
					}
				}
			}
		}
		else{
			actionContext.addError(new ErrorVO("mail.operations.err.containernotfound"));
			return;
		}
		
		mailInConsignmentVOs=createMailInConsignmentVOs(containerDetailsVOs);
		
		
		 Map<String, Collection<OneTimeVO>> oneTimes = 
				 findOneTimeDescription(logonAttributes.getCompanyCode());
		 
		 if(oneTimes!=null){
			   Collection<OneTimeVO> typeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.type");
		 }
		 
		 errors = new ArrayList<ErrorVO>();
		 
		 errors = validateForm(attachRoutingDetails,logonAttributes);
		 
		 if (errors != null && errors.size() > 0) {
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
		 }else{ 
			 
			 ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
				consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				consignmentFilterVO.setConsignmentNumber(
						attachRoutingDetails.getConsignemntNumber().toUpperCase());
				consignmentFilterVO.setPaCode(attachRoutingDetails.getPaCode().toUpperCase());
				consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_YES);
				
				
				try {
					consignmentDocumentVO = new MailTrackingDefaultsDelegate().
					        findConsignmentDocumentDetails(consignmentFilterVO);
					
							
				} catch (BusinessDelegateException businessDelegateException) {
					businessDelegateException.getMessageVO().getErrors();
					handleDelegateException(businessDelegateException);
				}
				
				if(consignmentDocumentVO == null) {
					consignmentDocumentVO = new ConsignmentDocumentVO();
					consignmentDocumentVO.setOperationFlag("I");
					actionContext.addError(new ErrorVO(STATUS_NO_RESULTS));
					log.log(Log.FINE, "consignmentDocumentVO IS NULL");
					return;
				}else {
					consignmentDocumentVO.setOperationFlag("U");
					log.log(Log.FINE, "consignmentDocumentVO IS not NULL",
							consignmentDocumentVO);
					int totalRecords = 0;
					if(consignmentDocumentVO.getMailInConsignmentVOs()!=null){
						if(mailInConsignmentVOs!=null && mailInConsignmentVOs.size()>0){
							consignmentDocumentVO.getMailInConsignmentcollVOs().addAll(mailInConsignmentVOs);
						}
					}else {
						consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
					}
				}
				
				if(null!=consignmentDocumentVO.getRoutingInConsignmentVOs()&&
						consignmentDocumentVO.getRoutingInConsignmentVOs().size()>0){
					attachRoutingDetails=
							populateAttachRoutingDetails(consignmentDocumentVO.getRoutingInConsignmentVOs());
					attachRoutingDetails.setConsignemntNumber(consignmentDocumentVO.getConsignmentNumber());
					attachRoutingDetails.setPaCode(consignmentDocumentVO.getPaCode());
					attachRoutingDetails.setConsignmentDate(consignmentDocumentVO.getConsignmentDate().toDisplayDateOnlyFormat());
					attachRoutingDetails.setConsignmentType(consignmentDocumentVO.getType());
				}
				consignmentDocumentVO.setCompanyCode(logonAttributes.getCompanyCode());
				consignmentDocumentVO.setConsignmentNumber(
						attachRoutingDetails.getConsignemntNumber().toUpperCase());
				consignmentDocumentVO.setPaCode(
						attachRoutingDetails.getPaCode().toUpperCase());
				  
		 }
		mailinboundModel.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) oneTimes);
		mailinboundModel.setAttachRoutingDetails(attachRoutingDetails); 
		ResponseVO responseVO = new ResponseVO();
		ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		mailinboundModel.setConsignmentDocumentVO(consignmentDocumentVO);
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		 	 
		
	}
	
	
	private AttachRoutingDetails populateAttachRoutingDetails(
			Collection<RoutingInConsignmentVO> routingInConsignmentVOs) {
		
		ArrayList<OnwardRouting> onwardRoutings=
				new ArrayList<OnwardRouting>();
		AttachRoutingDetails attachRoutingDetails=
				new AttachRoutingDetails();
		for(RoutingInConsignmentVO routingInConsignmentVO:routingInConsignmentVOs){
			OnwardRouting onwardRouting=new OnwardRouting();
			if(null!=routingInConsignmentVO.getOnwardCarrierCode()){
				onwardRouting.setCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
			}
			if(null!=routingInConsignmentVO.getOnwardFlightDate()){
				onwardRouting.setFlightDate(
						routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat());
			}
			if(null!=routingInConsignmentVO.getOnwardFlightNumber()){
				onwardRouting.setFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
			}
			if(null!=routingInConsignmentVO.getPol()){
				onwardRouting.setPol(routingInConsignmentVO.getPol());
			}
			if(null!=routingInConsignmentVO.getPou()){
				onwardRouting.setPou(routingInConsignmentVO.getPou());
			}
			onwardRouting.setRoutingSerialNumber(routingInConsignmentVO.getRoutingSerialNumber());	
			onwardRouting.setOperationalStatus(MailConstantsVO.MRA_STATUS_UPDATE);
			onwardRoutings.add(onwardRouting);
			
		}
		attachRoutingDetails.setOnwardRouting(onwardRoutings);
		return attachRoutingDetails;
	}


	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			
			fieldValues.add(CONSGNMNT_TYPE);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
	
	private Collection<ErrorVO> validateForm(
			AttachRoutingDetails attachRoutingDetails,LogonAttributes logonAttributes) {
		String conDocNo = attachRoutingDetails.getConsignemntNumber();
		String paCode = attachRoutingDetails.getPaCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(conDocNo == null || ("".equals(conDocNo.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.condocno.empty"));
		}
		if(paCode == null || ("".equals(paCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.empty"));
		}else{

	  	log.log(Log.FINE, "Going To validate PA code ...in command");
			try {
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
					postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
									logonAttributes.getCompanyCode(),paCode.toUpperCase());	  			
		   			if(postalAdministrationVO == null) {
		  				Object[] obj = {paCode.toUpperCase()};
		  				errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.invalid",obj));
		  			}
		  	
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		return errors;
	}
	
	public Collection<MailInConsignmentVO> createMailInConsignmentVOs(
			Collection<ContainerDetailsVO> containerDetailsVOs){
		log.entering("ScreenloadAttachRoutingCommand","createMailInConsignmentVOs");
		Collection<MailInConsignmentVO> mailVOs = new ArrayList<MailInConsignmentVO>();
		Collection<DSNVO> dsnVOs = new ArrayList<DSNVO>();
		if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
			for(ContainerDetailsVO containerDetailsVO : containerDetailsVOs){
				if(dsnVOs != null && dsnVOs.size() > 0){
					dsnVOs.addAll(containerDetailsVO.getDsnVOs());
				}else{
					dsnVOs = containerDetailsVO.getDsnVOs();
					
				}
			}
		}
		Collection<DSNVO> newDsnVOs = new ArrayList<DSNVO>();
		Collection<String> dsnKey = new ArrayList<String>();
		
		if (dsnVOs != null && dsnVOs.size() != 0) {
			for (DSNVO dsnVO : dsnVOs) {
				String dsnpk = dsnVO.getOriginExchangeOffice()
				+dsnVO.getDestinationExchangeOffice()
				+dsnVO.getMailCategoryCode()
				+dsnVO.getMailSubclass()
				+dsnVO.getDsn()
				+dsnVO.getYear();
				if(!dsnKey.contains(dsnpk)){
					dsnKey.add(dsnpk);
				}		
				if(!newDsnVOs.contains(dsnVO)){
					newDsnVOs.add(dsnVO);
				}
			}
		}
		
		try {
			containerDetailsVOs = new MailTrackingDefaultsDelegate().findMailbagsInContainerForImportManifest(containerDetailsVOs);
		}catch (BusinessDelegateException businessDelegateException) {
			log
					.log(
							Log.SEVERE,
							"BusinessDelegateException---findMailbagsInContainerForManifest",
							businessDelegateException.getMessage());
		}
		for(ContainerDetailsVO contVO : containerDetailsVOs){
			if(contVO.getDsnVOs() != null && contVO.getDsnVOs().size() > 0){
				for(DSNVO dsn : contVO.getDsnVOs()){
					String dsnPKKey  = dsn.getOriginExchangeOffice()
					+dsn.getDestinationExchangeOffice()
					+dsn.getMailCategoryCode()
					+dsn.getMailSubclass()
					+dsn.getDsn()
					+dsn.getYear();
					for(String key : dsnKey){						
						if(key.equals(dsnPKKey)){
							if(dsn.getMailbags() != null && dsn.getMailbags().size() > 0){
								for(MailbagVO bagVO : dsn.getMailbags()){
									mailVOs.add(createMailInConsignmentVO(bagVO));
								}		
							}
							if(contVO.getDesptachDetailsVOs() != null && 
									contVO.getDesptachDetailsVOs().size() > 0){
								for(DespatchDetailsVO despatchVO : contVO.getDesptachDetailsVOs()){
									
									String despatchKey  = despatchVO.getOriginOfficeOfExchange()
									+despatchVO.getDestinationOfficeOfExchange()
									+despatchVO.getMailCategoryCode()
									+despatchVO.getMailSubclass()
									+despatchVO.getDsn()
									+despatchVO.getYear();
									if(key.equals(despatchKey)){
										mailVOs.add(createMailInConsignmentVO(despatchVO));
									}
								}
							}
						}						
					}
				}
			}
		}
		log.exiting("ScreenloadAttachRoutingCommand","createMailInConsignmentVOs");
		return mailVOs;
	} 
	/**
	 * @param receptacleVO
	 * @return
	 */
	private MailInConsignmentVO createMailInConsignmentVO(MailbagVO bagVO) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		mailInConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailInConsignmentVO.setDsn(bagVO.getDespatchSerialNumber());
		mailInConsignmentVO.setOriginExchangeOffice(bagVO.getOoe());
		mailInConsignmentVO.setDestinationExchangeOffice(bagVO.getDoe());
		mailInConsignmentVO.setMailClass(bagVO.getMailSubclass().substring(0, 1));
		mailInConsignmentVO.setMailSubclass(bagVO.getMailSubclass());
		mailInConsignmentVO.setMailCategoryCode(bagVO.getMailCategoryCode());
		mailInConsignmentVO.setYear(bagVO.getYear());
		mailInConsignmentVO.setStatedBags(1);
		mailInConsignmentVO.setStatedWeight(bagVO.getWeight());
		// mailInConsignmentVO.setUldNumber(receptacleVO.getUldNumber());
		mailInConsignmentVO.setReceptacleSerialNumber(bagVO.getReceptacleSerialNumber());
		mailInConsignmentVO.setMailId(bagVO.getMailbagId());
		mailInConsignmentVO.setHighestNumberedReceptacle(bagVO.getHighestNumberedReceptacle());
		mailInConsignmentVO.setRegisteredOrInsuredIndicator(bagVO.getRegisteredOrInsuredIndicator());
		return mailInConsignmentVO;
	}   
	/**
	 * @param receptacleVO
	 * @return
	 */
	private MailInConsignmentVO createMailInConsignmentVO(DespatchDetailsVO despatchVO) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		mailInConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailInConsignmentVO.setConsignmentNumber(despatchVO.getConsignmentNumber());
		mailInConsignmentVO.setPaCode(despatchVO.getPaCode());
		mailInConsignmentVO.setDsn(despatchVO.getDsn());
		mailInConsignmentVO.setOriginExchangeOffice(despatchVO.getOriginOfficeOfExchange());
		mailInConsignmentVO.setDestinationExchangeOffice(despatchVO.getDestinationOfficeOfExchange());
		mailInConsignmentVO.setMailClass(despatchVO.getMailSubclass().substring(0, 1));
		mailInConsignmentVO.setMailSubclass(despatchVO.getMailSubclass());
		mailInConsignmentVO.setMailCategoryCode(despatchVO.getMailCategoryCode());
		mailInConsignmentVO.setYear(despatchVO.getYear());
		if(despatchVO.getStatedBags() > 0){
			//This is a manifested despatch
			mailInConsignmentVO.setStatedBags(despatchVO.getStatedBags());
			mailInConsignmentVO.setStatedWeight(despatchVO.getStatedWeight());
		}else{
			//This is a found despatch
			mailInConsignmentVO.setStatedBags(despatchVO.getReceivedBags());
			mailInConsignmentVO.setStatedWeight(despatchVO.getReceivedWeight());
		}
		// mailInConsignmentVO.setUldNumber(receptacleVO.getUldNumber());
		return mailInConsignmentVO;
	}   

}
