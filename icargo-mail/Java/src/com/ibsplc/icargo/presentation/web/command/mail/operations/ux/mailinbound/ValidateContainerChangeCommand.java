package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ValidateContainerChangeCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	16-Nov-2018		:	Draft
 */
public class ValidateContainerChangeCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String IMPORTED_TO_MRA = "I";
	private static final String CONTAINER_NOT_ARRIVED = "mailtracking.defaults.changeflight.containernotarrived";
	private static final String MAILBAS_IMPORTED_TO_MRA = "mailtracking.defaults.changeflight.mailbagsimportedtoMRA";
	private static final String CONTAINER_TRANSFERRED_OR_DELIVERED = "mailtracking.defaults.changeflight.containertransferredordelivered";
	
	
private Log log = LogFactory.getLogger("MAIL OPERATIONS ValidateContainerChangeCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		

		LogonAttributes logonAttributes =   
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();    
		ArrayList<ContainerDetails> containerDetailsCollection = 
				 mailinboundModel.getContainerDetailsCollection(); 
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		Collection<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
				new HashMap<String,ContainerDetailsVO>();
		
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
			containerDetailsVos=mailArrivalVO.getContainerDetails();
		}
			
		mailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		mailArrivalVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
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

		errors=validateForm(containerDetailsVosSelected);
		if(errors!=null&& errors.size() > 0){
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		mailinboundModel.setContainerDetailsCollection(containerDetailsCollection);
		ResponseVO responseVO = new ResponseVO();	
    	ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);  
		responseVO.setResults(result);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		
		
	}

	private Collection<ErrorVO> validateForm(Collection<ContainerDetailsVO> containerDetailsVosSelected) {
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		for(ContainerDetailsVO containerDetailsVO:containerDetailsVosSelected){
			boolean containerArrived = false;
			boolean importedToMRA = false;
			
			if(!(containerDetailsVO.getMailDetails().size()==0)){
				for(MailbagVO mailbag : containerDetailsVO.getMailDetails()){
					if(MailConstantsVO.FLAG_YES.equals(mailbag.getArrivedFlag())){
						containerArrived = true;
					}
					if(IMPORTED_TO_MRA.equals(mailbag.getMraStatus())){
						importedToMRA=true;
				
					}
					
				}
			}
			if(!containerArrived){
				errors.add(new ErrorVO(CONTAINER_NOT_ARRIVED));
				return errors;
			}   
			
			ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>)containerDetailsVO.getDsnVOs(); 
			
			if(dsnVos!=null && dsnVos.size()>0){
				for(DSNVO dsnVO:dsnVos){
					if(FLAG_YES.equals(dsnVO.getPltEnableFlag())){                               
						Collection<MailbagVO> mailbagVOs= containerDetailsVO.getMailDetails();	
						for(MailbagVO mail: mailbagVOs){ 
							if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag())){                     
								if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){            
									if(MailConstantsVO.FLAG_YES.equals(mail.getTransferFlag()) || 
											MailConstantsVO.FLAG_YES.equals(mail.getDeliveredFlag())||
												mail.getFromFlightSequenceNumber()!= mail.getFlightSequenceNumber()||
													!(mail.getFromFightNumber().equals(mail.getFlightNumber()))||
														MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())
															||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus()) ){
										if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(mail.getOperationalFlag())){
											errors.add(new ErrorVO(CONTAINER_TRANSFERRED_OR_DELIVERED));
											return errors;
										}
									}else if(importedToMRA){
										errors.add(new ErrorVO(MAILBAS_IMPORTED_TO_MRA));
										return errors;
									
									}
								}
							}
						}
					}
				}
			}
		}
		return errors;
	}

}
