package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.AcquitUldCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	24-Oct-2018		:	Draft
 */
public class AcquitUldCommand extends AbstractCommand {
	
private Log log = LogFactory.getLogger("MAIL OPERATIONS AcquitUldCommand");
private  static final String SUCCESS_MESSAGE="mail.operations.err.acquituldsuccess";
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("AcquitUldCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		MailArrivalVO mailArrivalVO=null;
		Collection<ContainerDetails> containerDetailsCollection=
				mailinboundModel.getContainerDetailsCollection();
		Collection<ContainerDetailsVO> containerDetailsVOs=null;
		
		HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
				new HashMap<String,ContainerDetailsVO>();
		ArrayList<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
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
			containerDetailsVO.setAquitULDFlag(true);
			containerDetailsVoMap.put(
					containerDetailsVO.getContainerNumber(), containerDetailsVO);
		}
		
		for(ContainerDetails containerDetail:containerDetailsCollection){
			
			if(containerDetailsVoMap.containsKey(containerDetail.getContainerno())){
				containerDetailsVosSelected.add(
						containerDetailsVoMap.get(containerDetail.getContainerno()));
			}
		}
		
		if(containerDetailsVosSelected.size()!=0){
			try {
				new MailTrackingDefaultsDelegate().autoAcquitContainers(containerDetailsVosSelected);
			} catch (BusinessDelegateException e) {
			
				e.getMessage();
			}
		}
		LocalDate arrivalDate=
				new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		mailArrivalVO.setArrivalDate(arrivalDate);
		mailinboundModel.setMailArrivalVO(mailArrivalVO);
		
		ArrayList<MailinboundModel> mailinboundModels=
				new ArrayList<MailinboundModel>();
		mailinboundModels.add(mailinboundModel);
		ResponseVO responseVO = new ResponseVO();	  
	    responseVO.setStatus("success");
	    responseVO.setResults(mailinboundModels);
	    ErrorVO error = new ErrorVO(SUCCESS_MESSAGE); 
	    error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error); 
	    actionContext.setResponseVO(responseVO);  
		log.exiting("AcquitUldCommand","execute");
		
		
		
	}

}
