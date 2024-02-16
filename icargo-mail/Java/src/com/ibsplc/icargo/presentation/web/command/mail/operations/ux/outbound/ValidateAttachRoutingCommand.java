package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.AttachRoutingDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ValidateAttachRoutingCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
	log.entering("validateAttachRoutingCommand","execute");
	OutboundModel outboundModel = 
			(OutboundModel) actionContext.getScreenModel();
	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    List<ErrorVO> errors = new ArrayList<ErrorVO>();
	Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
	Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
	ArrayList<MailbagVO>selectedMailbagsVOs=null;
	Collection<ContainerDetails> containerDetailsCollection= 
			outboundModel.getContainerDetailsCollection();
	MailManifestVO newMailManifestVO = new MailManifestVO();
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
	HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
			new HashMap<String,ContainerDetailsVO>();
	operationalFlightVO =MailOutboundModelConverter.constructOperationalFlightVO(outboundModel.getMailAcceptance(), logonAttributes);
	ArrayList<ContainerDetailsVO> containerDetailsVosSelected=
			new ArrayList<ContainerDetailsVO>();
	/*for(ContainerDetails containerDetails: containerDetailsCollection) {
         ContainerDetailsVO containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(containerDetails,logonAttributes);
         containers.add(containerDetailsVO);
	}
	 for(ContainerDetailsVO containerDetsVO :containers) {
		 if(containerDetsVO.getTotalBags()>0) {
	try{
		containerDetailsVOs=new MailTrackingDefaultsDelegate().findMailbagsInContainer(containers);
	}catch(BusinessDelegateException businessDelegateException){
		//errorVOs = handleDelegateException(businessDelegateException);
		//actionContext.addAllError(errorVOs);
	}
	
		 }
	 }
	
if(containerDetailsVOs!=null) {
	ArrayList<MailbagVO> mailbagVOs =null;
	Collection<DSNVO> dsnVos=null;
	 for(ContainerDetailsVO containerDetsVO :containerDetailsVOs) {
		 mailbagVOs=  (ArrayList)containerDetsVO.getMailDetails();
		 dsnVos=(ArrayList)new MailTrackingDefaultsDelegate().getDSNsForContainer(containerDetsVO);
		 containerDetsVO.setMailDetails(mailbagVOs);
		 dsnVos=new MailTrackingDefaultsDelegate().getRoutingInfoforDSN(dsnVos,containerDetsVO);
		 containerDetsVO.setDsnVOs(dsnVos);
		
	 }
}	  */
	
	try {
	    	newMailManifestVO = new MailTrackingDefaultsDelegate().findContainersInFlightForManifest(operationalFlightVO);
      }catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
	  }
	if(null!=newMailManifestVO){
		containerDetailsVOs=newMailManifestVO.getContainerDetails();
	}
	/**
	 * For finding the selected containers
	 */
	for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs){
		containerDetailsVoMap.put(
				containerDetailsVO.getContainerNumber(), containerDetailsVO);
	}
	for(ContainerDetails containerDetailIterate:containerDetailsCollection){
		if(containerDetailsVoMap.containsKey(containerDetailIterate.getContainerNumber())){
			containerDetailsVosSelected.add(
					containerDetailsVoMap.get(containerDetailIterate.getContainerNumber()));
		}
	}
	containerDetailsVOs=containerDetailsVosSelected;
	/*routing available check and consignment number diff check starts*/
	 AttachRoutingDetails attachRoutingDetails = new AttachRoutingDetails();
	int routingAvl = 0;
	int csgDocNumDiff = 0;
	int paCodeDiff = 0;
	String csgDocNum = "";
	String paCode = "";
	attachRoutingDetails.setConsignemntNumber("");
	attachRoutingDetails.setPaCode("");
	if(newMailManifestVO.getContainerDetails()!=null && newMailManifestVO.getContainerDetails().size()>0){
		for(ContainerDetailsVO  selContVos :containerDetailsVOs){
			if(selContVos.getDsnVOs()!=null && selContVos.getDsnVOs().size()>0){
				for(DSNVO dsnVO:selContVos.getDsnVOs()){
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
	else{
		actionContext.addError(new ErrorVO("mail.operations.err.containernotfound"));
		return;
	}
	/*
	 * Selected dsns if any
	 */
/*	if (csgDocNumDiff == 0 && routingAvl == 0 && paCodeDiff == 0) {
		if (mailManifestForm.getSelectChild()!=null && mailManifestForm.getSelectChild().length()>0){
			String[] childDSN = mailManifestForm.getSelectChild().split(",");
			int childSize=childDSN.length;
			ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
			Collection<DSNVO> dsnVos = new ArrayList<DSNVO>();
			for(int i=0;i<childSize;i++){
				if(!("").equals(childDSN[i]) &&  !("").equals(childDSN[i].split("-")[0])){				
					containerDtlsVO = ((ArrayList<ContainerDetailsVO>)mailManifestVO.getContainerDetails())
					.get(Integer.parseInt(childDSN[i].split("-")[0]));	
					if(containerDtlsVO != null &&
							(!containerDtlsVO.getContainerNumber().equals(containerDtlsDummyVO.getContainerNumber()))) {
						containerDtlsDummyVO = new ContainerDetailsVO();
						try {
							BeanHelper.copyProperties(containerDtlsDummyVO,containerDtlsVO);
						} catch (SystemException systemException) {
							log.log(Log.FINE, "BeanHelper.copyProperties FAILED !!!! ");
						}    	    	
						dsnVos = new ArrayList<DSNVO>();
						containerDtlsDummyVO.setDsnVOs(dsnVos);
						containerDetailsVOs.add(containerDtlsDummyVO);
					}
					 * "containerDtlsVO" is the Object Of "mailManifestVO.getContainerDetails" itself 
					 * so this will throw false if "containerDetailsVOs" does not contains this Object.
					 * And new "containerDtlsDummyVO" can be added, since this is for adding the 
					 * DSNVOs to the collection "dsnVos" indirectly. 
					if(!containerDetailsVOs.contains(containerDtlsVO)){
						if((childDSN[i].split("-")[1]).trim().length() > 0 && containerDtlsVO != null) {
							DSNVO dsn = ((ArrayList<DSNVO>)containerDtlsVO.getDsnVOs()).get(
									Integer.parseInt(childDSN[i].split("-")[1]));
							if(dsn.getRoutingAvl()!=null && dsn.getRoutingAvl().length()>0){
								if("Y".equals(dsn.getRoutingAvl())){
									routingAvl ++;
									break;
								}
							}
							if(dsn.getCsgDocNum()!=null && 
									dsn.getCsgDocNum().length() > 0 &&
									dsn.getPaCode() != null &&
									dsn.getPaCode().trim().length() > 0){
								if("".equals(csgDocNum)){
									csgDocNum = dsn.getCsgDocNum();
								}else{
									if(!csgDocNum.equals(dsn.getCsgDocNum())){
										csgDocNumDiff ++;
										break;
									}
								}
								if(paCode != null && paCode.trim().length() == 0 ){
									paCode = dsn.getPaCode();
								}else{
									if(!paCode.equals(dsn.getPaCode())){
										paCodeDiff ++;
										break;
									}
								}
							}
							dsn.setCompanyCode(containerDtlsVO.getCompanyCode());
							dsn.setContainerNumber(containerDtlsVO.getContainerNumber());
							dsn.setSegmentSerialNumber(containerDtlsVO.getSegmentSerialNumber());	
							dsnVos.add(dsn);
						}
					}
				}
			}
		}
	}*/
	if (csgDocNumDiff == 0 && routingAvl == 0 && paCodeDiff == 0){
		if(csgDocNum != null && csgDocNum.length() > 0 && 
				paCode != null && paCode.length() > 0){
			attachRoutingDetails.setConsignemntNumber(csgDocNum);
			attachRoutingDetails.setPaCode(paCode);
		}
	}
	if(routingAvl > 0){
		ErrorVO error=new ErrorVO("mailtracking.defaults.attachrouting.msg.err.routingavailable");
		errors.add(error);
		actionContext.addAllError(errors);
		return;
	}
	if(csgDocNumDiff > 0){
		ErrorVO error=new ErrorVO("mailtracking.defaults.attachrouting.msg.err.csgdocnumdiff");
		errors.add(error);
		actionContext.addAllError(errors);
		return;
	}
	if(paCodeDiff > 0){
		ErrorVO error=new ErrorVO("mailtracking.defaults.attachrouting.msg.err.csgpacodediff");
		errors.add(error);
		actionContext.addAllError(errors);
		return;
	}
	//Collection<MailInConsignment> mailsInConsignments=createMailInConsignments(containerList);
	//outboundModel.setMailsInConsignment(mailsInConsignments);
	 SharedDefaultsDelegate sharedDefaultsDelegate = 
   	      new SharedDefaultsDelegate();
	 Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if(oneTimes!=null){
			Collection<OneTimeVO> typeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.type");
			log.log(Log.FINE, "******Getting OneTimeVOs***typeVOs***", typeVOs.size());
			 outboundModel.setOneTimeValues(MailOutboundModelConverter.constructOneTimeValues(oneTimes));
		}
		outboundModel.setCreateMailInConsignmentVOs(createMailInConsignmentVOs(containerDetailsVOs));
		outboundModel.setAttachRoutingDetails(attachRoutingDetails);
		ResponseVO responseVO = new ResponseVO();
		ArrayList<OutboundModel> result = new ArrayList<OutboundModel>();
		result.add(outboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
    	log.exiting("validateAttachRoutingCommand","execute");
}
/**
 * createMailInConsignmentVOs
 * @param containerDetailsVOs
 * @return createMailInConsignmentVOs
 */
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
	private MailInConsignmentVO createMailInConsignmentVO(MailbagVO bagVO) {
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
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
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
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
	Collection<ErrorVO> errors = null;
	try{
		Collection<String> fieldValues = new ArrayList<String>();
		fieldValues.add("mailtracking.defaults.consignmentdocument.type");
		oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
	}catch(BusinessDelegateException businessDelegateException){
		errors = handleDelegateException(businessDelegateException);
	}
	return oneTimes;
}


}
