package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ValidateTransferContainerCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	15-Nov-2018		:	Draft
 */
public class ValidateTransferContainerCommand extends AbstractCommand  {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";


private Log log = LogFactory.getLogger("MAIL OPERATIONS ValidateTransferContainerCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("ValidateTransferContainerCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel(); 
		ArrayList<ContainerDetails> containerDetailsCollection = 
				 mailinboundModel.getContainerDetailsCollection();
		MailArrivalVO mailArrivalVO = null;
		ArrayList<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
				new HashMap<String,ContainerDetailsVO>();
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		OperationalFlightVO operationalFlightVO=null;
		
		String embargoFlag=null;
		embargoFlag=(String) actionContext.getAttribute("embargoFlag");
		if(embargoFlag!=null&&embargoFlag.equals("true")){
			actionContext.addError(new ErrorVO("mail.operations.err.embargoexists"));
			return;
		}
		
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
		
		String airport = logonAttributes.getAirportCode();
		Collection<ContainerVO> containerVOs = 
				new ArrayList<ContainerVO>();
	 	
	 	for(ContainerDetailsVO containerDetailsVO:containerDetailsVos){
			containerDetailsVoMap.put(
					containerDetailsVO.getContainerNumber(), containerDetailsVO);
		}
		
		for(ContainerDetails containerDetail:containerDetailsCollection){
			
			if(containerDetailsVoMap.containsKey(containerDetail.getContainerno())){
				containerDetailsVosSelected.add(
						containerDetailsVoMap.get(containerDetail.getContainerno()));
			}
		}
		
		Collection<String> nearbyOEToCurrentAirport  = new ArrayList<String>();
		
		/**
		 * Populating the containerVos from container Details selected
		 */
		for(ContainerDetailsVO containerDetailsVO:containerDetailsVosSelected){
			
			Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
			Collection<MailbagVO> mailbagvo = containerDetailsVO.getMailDetails();
			Collection<ArrayList<String>> groupedOECityArpCodes = null;
			Collection<String> does=new ArrayList<String>();
			if(dsnVOs != null && dsnVOs.size()!=0){
				for(DSNVO dsnVO:dsnVOs){
					if(!does.contains(dsnVO.getDestinationExchangeOffice())){
		 				does.add(dsnVO.getDestinationExchangeOffice());
		 			}					
				}
				if(does != null && does.size()!=0){
					try {
         				
         			   /**
         			     * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
         			     * the inner collection contains the values in the order :
         			     * 0.OFFICE OF EXCHANGE
         			     * 1.CITY NEAR TO OE
         			     * 2.NEAREST AIRPORT TO CITY
         			    */
         			     
         				groupedOECityArpCodes = 
         					new MailTrackingDefaultsDelegate().findCityAndAirportForOE(
         							logonAttributes.getCompanyCode(), does);
         			}catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					
					int errorFlag = 0;				
         			if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0){
         				for(String doe : does){
         					for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
         						if(cityAndArpForOE.size() == 3 && 
         								doe.equals(cityAndArpForOE.get(0)) && 
         								airport.equals(cityAndArpForOE.get(2))){
         							errorFlag = 1;
         							break;
         						}			
         					}
         					if(errorFlag == 1) {
         						break;
         					}
         				}
         			}
         			if(errorFlag == 1){
        				log.log(Log.INFO,"<<----DOE of Mailbag/Despatch is Same as that of the current Airport --->>");
        				actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.cannottransferdsn"));
        				return;
        			}
				}
				
			}
			
			int errorFlag = 0;
			MailbagVO mailbagToDeliver = null;
			try {
				 nearbyOEToCurrentAirport  = 
					new MailTrackingDefaultsDelegate().findOfficeOfExchangesForAirport(
							logonAttributes.getCompanyCode(), airport);
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}		
			Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
			if(mailbagVOs != null && mailbagVOs.size() >0){
				for(MailbagVO  mailvo:mailbagVOs){
						if (!MailConstantsVO.FLAG_YES.equals(mailvo.getDeliveredFlag())) { 
							if(isTerminating(nearbyOEToCurrentAirport,mailvo)){	
							 mailbagToDeliver = mailvo;
								errorFlag = 1;
									break;
							}	
					}
				}
			}
			

			
			
		
		}
		
		ResponseVO responseVO = new ResponseVO();	
    	ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
    	log.exiting("ValidateTransferContainerCommand","execute"); 
	}
		
		private boolean isTerminating(Collection<String> nearbyOEToCurrentAirport,
				MailbagVO mailbagvo){
			boolean isTerminating = false;
			if(nearbyOEToCurrentAirport != null && nearbyOEToCurrentAirport.size() > 0){
				for(String officeOfExchange : nearbyOEToCurrentAirport){
					if(mailbagvo != null && !"Y".equals(mailbagvo.getDeliveredFlag())){
						isTerminating = officeOfExchange.equals(mailbagvo.getDoe()) ? true : false;
						if(isTerminating){
							break;	
						}
					}
				}
			}
			return isTerminating;
		}

}
