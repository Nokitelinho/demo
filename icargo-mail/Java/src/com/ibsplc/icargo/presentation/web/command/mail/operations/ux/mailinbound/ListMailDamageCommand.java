package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ListMailDamageCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	22-Dec-2018		:	Draft
 */
public class ListMailDamageCommand extends AbstractCommand  {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String DAMAGE_CODES = "mailtracking.defaults.return.reasoncode";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ListMailDamageCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("ListMailDamageCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel(); 
		MailArrivalVO mailArrivalVO =null;
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ArrayList<MailinboundModel> results = new ArrayList<MailinboundModel>();
		ResponseVO responseVO = new ResponseVO();
		ContainerDetails containerDetails=mailinboundModel.getContainerDetail();
		ArrayList<MailBagDetails> mailBagDetailsCollection=
				containerDetails.getMailBagDetailsCollection();
		Map<String, Collection<OneTimeVO>> oneTimes = 
				new HashMap<String, Collection<OneTimeVO>>();
		
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
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
			containerDetailsVos=mailArrivalVO.getContainerDetails();
		}
		if(null!=containerDetailsVos&&containerDetailsVos.size()>0){
			for(ContainerDetailsVO containerDetailsVOIterate:containerDetailsVos){
				if(containerDetails.getContainerno().equals(containerDetailsVOIterate.getContainerNumber())){
					containerDetailsVO=containerDetailsVOIterate;
					break;
							
				}
			}
			mailDetailsMap.clear();
			for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
				String mailKey=mailbagVO.getMailbagId();
				mailDetailsMap.put(mailKey, mailbagVO);	
			}
			Collection<MailbagVO> selectedMailBagVos=
					 new ArrayList<MailbagVO>();
			selectedMailBagVos.clear();
			if(null!=mailBagDetailsCollection&&mailBagDetailsCollection.size()>0){
				for(MailBagDetails mailBagDetailsIterate:mailBagDetailsCollection){
					String mailbagKey=new StringBuilder(mailBagDetailsIterate.getDSN()).append(mailBagDetailsIterate.getOoe())
							.append(mailBagDetailsIterate.getDoe()).append(mailBagDetailsIterate.getCategory()).append(mailBagDetailsIterate.getSubClass())
							.append(mailBagDetailsIterate.getYear()).toString();
					if(mailDetailsMap.containsKey(mailBagDetailsIterate.getMailBagId())){
						selectedMailBagVos.add(mailDetailsMap.get(mailBagDetailsIterate.getMailBagId()));
					}
				}
				containerDetailsVO.setMailDetails(selectedMailBagVos);
			}
		}
		
		for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){ 
			if(!MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(mailbagVO.getMailStatus())) {
				if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagVO.getMailStatus())) {
					actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadytransfered"));
				}else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getMailStatus())) {
					actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydelivered"));
				}else if (!MailConstantsVO.MAIL_STATUS_DAMAGED.equalsIgnoreCase(mailbagVO.getMailStatus())) {
	    		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailarrival.mailbagnotarrived");	
	    		actionContext.addError(errorVO);
	    		return;
				}else{
					break;
				}
			}
		}
		
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(DAMAGE_CODES);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessageVO().getErrors();
			errors=handleDelegateException(businessDelegateException);
		}
		
		if(errors!=null&&errors.size()>0){	
    		actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		
		
		mailinboundModel.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) oneTimes);
		results.add(mailinboundModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("ListMailDamageCommand", "execute");
	
		
		
		
	}
	
	
}
