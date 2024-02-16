package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DeliverMailDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ChangeScanTimeCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	14-Nov-2018		:	Draft
 */
public class ChangeScanTimeCommand extends AbstractCommand  {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private  static final String SUCCESS_MESSAGE="mail.operations.err.changescantimesuccess";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ChangeScanTimeCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		MailArrivalVO mailArrivalVO =null;
		ContainerDetails containerDetails=
				mailinboundModel.getContainerDetail();
		Collection<ContainerDetailsVO> containerDetailsVos=
					new ArrayList<ContainerDetailsVO>() ;
		DeliverMailDetails deliverMailDetails=
				(DeliverMailDetails) mailinboundModel.getDeliverMailDetails();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		
		ArrayList<MailBagDetails> mailBagDetailsCollection=
				containerDetails.getMailBagDetailsCollection();
		
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

			for(ContainerDetailsVO containerDetailsVO:mailArrivalVO.getContainerDetails()){
				if(containerDetailsVO.getContainerNumber()
						.equals(containerDetails.getContainerno())){ 
					if(null!=mailBagDetailsCollection){   
						mailDetailsMap.clear();
						for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
							String mailKey=mailbagVO.getMailbagId();
							mailDetailsMap.put(mailKey, mailbagVO);	
						}
						Collection<MailbagVO> selectedMailBagVos=
								 new ArrayList<MailbagVO>();
						selectedMailBagVos.clear();
						for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
							String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
									.append(dsnvo.getDestinationExchangeOffice())
										.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
											.append(dsnvo.getYear()).toString();
							dsnDetailsMap.put(dsnKey, dsnvo);	
						}
						Collection<DSNVO> selectedDsnVos=
								 new ArrayList<DSNVO>();
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
						containerDetailsVO.setDsnVOs(selectedDsnVos);
						containerDetailsVO.setMailDetails(selectedMailBagVos);
						containerDetailsVos.add(containerDetailsVO);
						
					}
				}
				
			}
		}
		else{
			actionContext.addError(new ErrorVO("mail.operations.err.containernotfound"));
			return;
		}
		
		
		
		
		if(containerDetailsVos != null && containerDetailsVos.size() > 0){
			for(ContainerDetailsVO containerDetailsVO:containerDetailsVos){
				if(containerDetailsVO.getContainerNumber().equals(containerDetails.getContainerno())){
					containerDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
					containerDetailsVO.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_UPDATE);
					containerDetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
  							Location.NONE, true));
					containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
					for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
						if(null!=deliverMailDetails.getDate()
								&&null!=deliverMailDetails.getTime()){
							LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
							String scanDT = new StringBuilder(deliverMailDetails.getDate()).append(" ")
							        .append(deliverMailDetails.getTime()).append(":00").toString();
							mailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
							mailbagVO.setOperationalFlag(ContainerDetailsVO.OPERATION_FLAG_UPDATE);
							mailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
		  							Location.NONE, true));
							mailbagVO.setLastUpdateUser(logonAttributes.getUserId());
						}
					}
				}
			}
		}
		

		mailArrivalVO.setContainerDetails(containerDetailsVos);
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId()); 
		
		Collection<LockVO> locks = prepareLocksForSave(mailArrivalVO);
		
		if (locks == null || locks.size() == 0) {
			locks = null;
		}
		try {
			new MailTrackingDefaultsDelegate().saveArrivalDetails(mailArrivalVO, locks);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		ResponseVO responseVO = new ResponseVO();	
    	ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
    	mailinboundModel.setMailArrivalVO(mailArrivalVO);
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		ErrorVO error = new ErrorVO(SUCCESS_MESSAGE);      
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error); 
        actionContext.setResponseVO(responseVO);
		
		
	}
	
	
	private Collection<LockVO> prepareLocksForSave(
    		MailArrivalVO mailArrivalVO) {
    	log.log(Log.FINE, "\n prepareLocksForSave------->>", mailArrivalVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<LockVO> locks = new ArrayList<LockVO>();
    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalVO.getContainerDetails();
    	
    	if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
    		for (ContainerDetailsVO conVO : containerDetailsVOs) {
    			if(conVO.getOperationFlag()!=null && conVO.getOperationFlag().trim().length()>0){
    				ArrayList<MailbagVO> mailbagvos=new ArrayList<MailbagVO>(conVO.getMailDetails());
    				if(mailbagvos!=null && mailbagvos.size()>0){
    				for(MailbagVO bagvo:mailbagvos){
    					if(bagvo.getOperationalFlag()!=null && bagvo.getOperationalFlag().trim().length()>0){
    						ULDLockVO lock = new ULDLockVO();
    		    			lock.setAction(LockConstants.ACTION_MAILARRIVAL);
    		    			lock.setClientType(ClientType.WEB);
    		    			lock.setCompanyCode(logonAttributes.getCompanyCode());
    		    			lock.setScreenId(SCREEN_ID);
    		    			lock.setStationCode(logonAttributes.getStationCode());
    		    			if(bagvo.getContainerForInventory() != null){
    		    				lock.setUldNumber(bagvo.getContainerForInventory());
    		    				lock.setDescription(bagvo.getContainerForInventory());
    		    				lock.setRemarks(bagvo.getContainerForInventory());
    		    			log.log(Log.FINE, "\n lock------->>", lock);
							locks.add(lock);
    					    }
    					}
    					   
    					}
    			}
    			
    		}
    	}
    	
    }
    	return locks;
    }


}
