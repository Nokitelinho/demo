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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ValidateMailChangeCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	26-Nov-2018		:	Draft
 */
public class ValidateMailChangeCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String MAIL_NOT_ARRIVED = "mailtracking.defaults.changeflight.mailnotarrived";
	private static final String MAILBAS_IMPORTED_TO_MRA = "mailtracking.defaults.changeflight.mailbagsimportedtoMRA";
	private static final String MAILBAGS_TRANSFERRED_OR_DELIVERED = "mailtracking.defaults.changeflight.containertransferredordelivered";
	private static final String CONTAINERS_PARTIALLY_ARRIVED = "mailtracking.defaults.mailchange.partiallyarrived";
	private static final String IMPORTED_TO_MRA = "I";
	private static final String DSN = "DSN";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ValidateMailChangeCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailArrivalVO mailArrivalVO =null;
		Collection<ContainerDetailsVO> containerDetailsVOs=null;
		ContainerDetails containerDetail=
				mailinboundModel.getContainerDetail();
		Collection<MailbagVO> selectedMailBagVos=
				 new ArrayList<MailbagVO>();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		
	
		Collection<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		Collection<DSNVO> selectedDsnVos=
				 new ArrayList<DSNVO>();
		ContainerDetailsVO containerDetailsVO =null;
		
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
		
		for (ContainerDetailsVO containerDetailsVOIterate : containerDetailsVOs) {
			if(containerDetailsVOIterate.getContainerNumber()
					.equals(containerDetail.getContainerno())){
				containerDetailsVO=containerDetailsVOIterate;
				break;
			}
		}
		
		if (null!=containerDetailsVO) {
			dsnDetailsMap.clear();
			for (DSNVO dsnvo : containerDetailsVO.getDsnVOs()) {
				String dsnKey = new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
						.append(dsnvo.getDestinationExchangeOffice())
						.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass()).append(dsnvo.getYear())
						.toString();
				dsnDetailsMap.put(dsnKey, dsnvo);
			}
			selectedDsnVos.clear();
			if(null!=containerDetail.getDsnDetailsCollection()&&containerDetail.getDsnDetailsCollection().size()>0){
				for (DSNDetails dsnDetails : containerDetail.getDsnDetailsCollection()) {
					String dsnKey = new StringBuilder(dsnDetails.getDsn()).append(dsnDetails.getOriginExchangeOffice())
							.append(dsnDetails.getDestinationExchangeOffice()) 
							.append(dsnDetails.getMailCategoryCode()).append(dsnDetails.getMailSubclass())
							.append(dsnDetails.getYear()).toString();
					if (dsnDetailsMap.containsKey(dsnKey)) {
						selectedDsnVos.add(dsnDetailsMap.get(dsnKey));
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
				containerDetailsVO.setMailDetails(selectedMailBagVos);
			}
			containerDetailsVO.setDsnVOs(selectedDsnVos);
			containerDetailsVosSelected.add(containerDetailsVO);

		}
		
		else{
			actionContext.addError(new ErrorVO("mail.operations.err.containernotfound"));
			return;
		}
		
		
		errors=validateForm(containerDetailsVosSelected);
		
		if(errors!=null&& errors.size() > 0){
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		ResponseVO responseVO = new ResponseVO();	
    	ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		
	}

	private Collection<ErrorVO> validateForm(Collection<ContainerDetailsVO> containerDetailsVOs) {
		
		boolean isPartiallyArrived=false;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs){
			int count=0;
		
			for(DSNVO dsnVO:containerDetailsVO.getDsnVOs()){
				
				if(FLAG_YES.equals(dsnVO.getPltEnableFlag())){
					
					Collection<MailbagVO> mailbagVOs= containerDetailsVO.getMailDetails();
					for(MailbagVO mail: mailbagVOs){
						String selectedDSN=new StringBuilder(dsnVO.getOriginExchangeOffice()).append(dsnVO.getDestinationExchangeOffice()).append(dsnVO.getMailCategoryCode()).append(dsnVO.getMailSubclass()).append(dsnVO.getYear()).toString();
						String selectedMail=new StringBuilder(mail.getOoe()).append(mail.getDoe()).append(mail.getMailCategoryCode()).append(mail.getMailSubclass()).append(mail.getYear()).toString();
						if(selectedDSN.equals(selectedMail)){
							if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){
								if(dsnVO.getReceivedBags()!=0&&(dsnVO.getReceivedBags()<dsnVO.getBags())){  
									isPartiallyArrived=true;
								}
								if(dsnVO.getBags()!=0&&dsnVO.getReceivedBags()==0){
									errors.add(new ErrorVO(MAIL_NOT_ARRIVED));
									return errors;
								}  
							
							} 
							
							if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag())){
								if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){

									if(dsnVO.getReceivedBags()==0){
										errors.add(new ErrorVO(MAIL_NOT_ARRIVED));
										return errors;

									}
								
									else if(mail.getFromFlightSequenceNumber()!= mail.getFlightSequenceNumber()||
											!(mail.getFromFightNumber().equals(mail.getFlightNumber()))||                            
											MailConstantsVO.FLAG_YES.equals(mail.getDeliveredFlag())|| 
											MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())
											||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus())
											 ){
										if(selectedDSN.equals(selectedMail)){ 
											count++;
											isPartiallyArrived=true;
										}

									}else 	if(IMPORTED_TO_MRA.equals(mail.getMraStatus())){
										errors.add(new ErrorVO(MAILBAS_IMPORTED_TO_MRA));
										return errors;	
									}
									else{
										if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag()) 
												&& !MailConstantsVO.OPERATION_FLAG_INSERT.equals(mail.getOperationalFlag())){
											mail.setUndoArrivalFlag(MailConstantsVO.FLAG_YES);
											containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
											containerDetailsVO.setUndoArrivalFlag(DSN); 
										}
									}
									if(count==dsnVO.getReceivedBags()){
										errors.add(new ErrorVO(MAILBAGS_TRANSFERRED_OR_DELIVERED));            
										return errors;
									}
								}
							}
						}
					}
				}
					
				
			} 
		}
		
		if(isPartiallyArrived){   
			//errors.add(new ErrorVO(CONTAINERS_PARTIALLY_ARRIVED)); 
		}
		return errors;
	}

}
