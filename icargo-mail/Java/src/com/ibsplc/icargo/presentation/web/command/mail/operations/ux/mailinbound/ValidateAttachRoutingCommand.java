package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ValidateAttachRoutingCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	17-Nov-2018		:	Draft
 */
public class ValidateAttachRoutingCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String CONSGNMNT_TYPE="mailtracking.defaults.consignmentdocument.type";
	
private Log log = LogFactory.getLogger("MAIL OPERATIONS ValidateAttachRoutingCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("ValidateAttachRoutingCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		Collection<ContainerDetails> containerDetailsCollection=
				mailinboundModel.getContainerDetailsCollection();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailArrivalVO mailArrivalVO =null;
		Collection<ContainerDetailsVO> containerDetailsVOs =null;
		AttachRoutingDetails attachRoutingDetails=
				new AttachRoutingDetails();
		ResponseVO responseVO = new ResponseVO();
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
		
		int routingAvl = 0;
		int csgDocNumDiff = 0;
		int paCodeDiff = 0;
		String csgDocNum = "";
		String paCode = "";
		attachRoutingDetails.setConsignemntNumber("");
		attachRoutingDetails.setPaCode("");
		
		if(containerDetailsVOs!=null && containerDetailsVOs.size()>0){
			for(ContainerDetailsVO  selectedContainerDetailsVO :containerDetailsVOs){
				if(selectedContainerDetailsVO.getDsnVOs()!=null 
						&& selectedContainerDetailsVO.getDsnVOs().size()>0){
					for(DSNVO dsnVO:selectedContainerDetailsVO.getDsnVOs()){
						if(dsnVO.getCsgDocNum()!=null && 
								dsnVO.getCsgDocNum().length() > 0 &&
								dsnVO.getPaCode() != null &&
								dsnVO.getPaCode().trim().length() > 0){
							if(csgDocNum != null && csgDocNum.trim().length() == 0 ){
								csgDocNum = dsnVO.getCsgDocNum();
							}else{
								if(!csgDocNum.equals(dsnVO.getCsgDocNum())){
									csgDocNumDiff ++;
									break;
								}
							}
							if(paCode != null && paCode.trim().length() == 0 ){
								paCode = dsnVO.getPaCode();
							}else{
								if(!paCode.equals(dsnVO.getPaCode())){
									paCodeDiff ++;
									break;
								}
							}
						}
						
						if(dsnVO.getRoutingAvl()!=null && dsnVO.getRoutingAvl().length()>0){
							if("Y".equals(dsnVO.getRoutingAvl())){
								routingAvl ++;
								break;
							}
						}
					}
					if(csgDocNumDiff > 0 || routingAvl > 0 || paCodeDiff > 0){
						break;
					}
				}
					
			}
		}
		
		if (csgDocNumDiff == 0 && routingAvl == 0 && paCodeDiff == 0){
			attachRoutingDetails.setConsignemntNumber(csgDocNum);
			attachRoutingDetails.setPaCode(paCode);
		}
		
		if(routingAvl > 0){
			ErrorVO error=new ErrorVO("mailtracking.defaults.attachrouting.msg.err.routingavailable");
			errors.add(error);
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		if(csgDocNumDiff > 0){
			ErrorVO error=new ErrorVO("mailtracking.defaults.attachrouting.msg.err.csgdocnumdiff");
			errors.add(error);
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		if(paCodeDiff > 0){
			ErrorVO error=new ErrorVO("mailtracking.defaults.attachrouting.msg.err.csgpacodediff");
			errors.add(error);
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		 Map<String, Collection<OneTimeVO>> oneTimes = 
				 findOneTimeDescription(logonAttributes.getCompanyCode());
		 
		
		mailinboundModel.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) oneTimes);
		mailinboundModel.setCreateMailInConsignmentVOs(createMailInConsignmentVOs(containerDetailsVOs));
		ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		mailinboundModel.setAttachRoutingDetails(attachRoutingDetails);
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
    	log.exiting("ScreenLoadAttachAwbCommand","execute");
	
		
		
		
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
}
