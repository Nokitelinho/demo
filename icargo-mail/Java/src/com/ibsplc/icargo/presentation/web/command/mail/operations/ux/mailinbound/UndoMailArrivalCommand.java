package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

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
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.UndoMailArrivalCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	01-Nov-2018		:	Draft
 */
public class UndoMailArrivalCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String CONTAINER_LEVEL="container";
	private static final String MAILBAG_LEVEL="mailbag";
	private static final String DSN_LEVEL="dsn";
	private  static final String SUCCESS_MESSAGE="mail.operations.err.undoarrivalsuccess";
	private static final String SYS_PARA_TRIGGER_FOR_MRAIMPORT="mailtracking.mra.triggerforimport";
	private Log log = LogFactory.getLogger("MAIL OPERATIONS UndoMailArrivalCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("UndoMailArrivalCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ArrayList<ContainerDetails> containerDetailsCollection = 
				 mailinboundModel.getContainerDetailsCollection();
		MailArrivalVO mailArrivalVO =null;
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		Collection<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
		HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
				new HashMap<String,ContainerDetailsVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		Collection<DSNVO> selectedDsnVos=
				 new ArrayList<DSNVO>();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		 Collection<MailbagVO> selectedMailBagVos=
				 new ArrayList<MailbagVO>();
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		boolean mailbagLevel=false;
		//Added as part of IASCB-84085 starts
		Map<String, String> sysResults = null;
		Collection<String> codes = new ArrayList<String>();
		codes.add(SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		try {
			sysResults = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);  
		} 
		String triggerForMRAImport = (sysResults.get(SYS_PARA_TRIGGER_FOR_MRAIMPORT));
		//Added as part of IASCB-84085 ends
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
		
			
			for(ContainerDetailsVO containerDetailsVO:containerDetailsVos){
				containerDetailsVoMap.put(containerDetailsVO.getContainerNumber(), containerDetailsVO);
			}
			
			for(ContainerDetails containerDetails:containerDetailsCollection){     
				if(containerDetailsVoMap.containsKey(containerDetails.getContainerno())){
					ContainerDetailsVO containerDetailsVO=
							containerDetailsVoMap.get(containerDetails.getContainerno());
					dsnDetailsMap.clear();
					for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
						String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
								.append(dsnvo.getDestinationExchangeOffice())
									.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
										.append(dsnvo.getYear()).toString();
						dsnDetailsMap.put(dsnKey, dsnvo);	
					}
					mailDetailsMap.clear();
					for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
						String mailKey=mailbagVO.getMailbagId();
						mailDetailsMap.put(mailKey, mailbagVO);	
					}
					if(null!=containerDetails.getDsnDetailsCollection()&&containerDetails.getDsnDetailsCollection().size()>0){
						selectedDsnVos.clear();
						for(DSNDetails dsnDetails:containerDetails.getDsnDetailsCollection()){
							String dsnKey=new StringBuilder(dsnDetails.getDsn()).append(dsnDetails.getOriginExchangeOffice())
									.append(dsnDetails.getDestinationExchangeOffice())
										.append(dsnDetails.getMailCategoryCode()).append(dsnDetails.getMailSubclass())
											.append(dsnDetails.getYear()).toString();
							if(dsnDetailsMap.containsKey(dsnKey)){
								selectedDsnVos.add(dsnDetailsMap.get(dsnKey));
							}
							selectedMailBagVos.clear();               
							for(MailbagVO mailBagDetails:containerDetailsVO.getMailDetails()){
								if(mailBagDetails.getDespatchSerialNumber()!=null && mailBagDetails.getDespatchSerialNumber().equals(dsnDetails.getDsn())){
									selectedMailBagVos.add(mailDetailsMap.get(mailBagDetails.getMailbagId()));
									
								}
							}
						}
						
						Collection<DSNVO> dsnvos= new LinkedHashSet<>();
						dsnvos.addAll(selectedDsnVos);
						selectedDsnVos.clear();
						selectedDsnVos.addAll(dsnvos);
						containerDetailsVoMap.get(containerDetails.getContainerno()).setDsnVOs(selectedDsnVos);
						containerDetailsVoMap.get(containerDetails.getContainerno()).setMailDetails(selectedMailBagVos);
						
					}
					else if(null!=containerDetails.getMailBagDetailsCollection()&&containerDetails.getMailBagDetailsCollection().size()>0){
						mailbagLevel=true;
						mailDetailsMap.clear();
						for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
							String mailKey=mailbagVO.getMailbagId();
							mailDetailsMap.put(mailKey, mailbagVO);	
						}
						selectedMailBagVos.clear();
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
						containerDetailsVoMap.get(containerDetails.getContainerno()).setDsnVOs(selectedDsnVos);
						containerDetailsVoMap.get(containerDetails.getContainerno()).setMailDetails(selectedMailBagVos);
						
					}
					else{      
						selectedDsnVos=containerDetailsVoMap.get(containerDetails.getContainerno()).getDsnVOs();
						selectedMailBagVos=containerDetailsVoMap.get(containerDetails.getContainerno()).getMailDetails();
					}
					containerDetailsVosSelected.add(containerDetailsVoMap.get(containerDetails.getContainerno()));

				}
			}
			
			for(ContainerDetailsVO containerDetailsVO:containerDetailsVosSelected){
				for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
					if(FLAG_YES.equals(dsnvo.getPltEnableFlag())){
						Collection<MailbagVO> mailbagVOs= containerDetailsVO.getMailDetails();
						for(MailbagVO mail: mailbagVOs){
								String selectedDSN=new StringBuilder(dsnvo.getOriginExchangeOffice())
										.append(dsnvo.getDestinationExchangeOffice()).append(dsnvo.getMailCategoryCode())
												.append(dsnvo.getMailSubclass()).append(dsnvo.getYear()).toString();
								String selectedMail=new StringBuilder(mail.getOoe()).append(mail.getDoe())
										.append(mail.getMailCategoryCode()).append(mail.getMailSubclass())
												.append(mail.getYear()).toString();
							if(selectedDSN.equals(selectedMail)){
							if(dsnvo.getDsn().equals(mail.getDespatchSerialNumber())){
								if(dsnvo.getReceivedBags()==0){  
									actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailnotarrived"));
									return;
								}  
							
							} 
							
							if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag())){
								if(dsnvo.getDsn().equals(mail.getDespatchSerialNumber())){
									
									if(dsnvo.getReceivedBags()==0){
										actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailnotarrived"));
										return;
									}
									else if(mail.getFromFlightSequenceNumber()!= mail.getFlightSequenceNumber()||
											!(mail.getFromFightNumber().equals(mail.getFlightNumber()))||                            
											MailConstantsVO.FLAG_YES.equals(mail.getDeliveredFlag())|| 
											MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())
											||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus()) ){
										if(selectedDSN.equals(selectedMail)){ 
											actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailtransferredordelivered"));            
										return;
										}
									}else if(triggerForMRAImport!=null && !"N".equals(triggerForMRAImport) && "I".equals(mail.getMraStatus())){
										actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagsimportedtoMRA"));
										return;	
									}
									else{
										if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag()) && !"I".equals(mail.getOperationalFlag())){
										mail.setUndoArrivalFlag(MailConstantsVO.FLAG_YES);
										containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
										containerDetailsVO.setUndoArrivalFlag("DSN");    
										}
									}
								}
							}
							
							}
						}
					}
					
					
					boolean containerArrived = false;
					boolean importedToMRA = false;
					for(MailbagVO mailbag : containerDetailsVO.getMailDetails()){
						if(MailConstantsVO.FLAG_YES.equals(mailbag.getArrivedFlag())){
							containerArrived = true;
						} 
						if(triggerForMRAImport!=null && !"N".equals(triggerForMRAImport) &&"I".equals(mailbag.getMraStatus())){
							importedToMRA=true;
						}
						
					}
					
					if(!containerArrived){
						error.add(new ErrorVO("mail.operations.mailarrival.containernotarrived"));
						actionContext.addAllError((List<ErrorVO>) error);
						return;	 	
					}   
					
					if(FLAG_YES.equals(dsnvo.getPltEnableFlag())){
						
						Collection<MailbagVO> mailbagVOs= containerDetailsVO.getMailDetails();	
						for(MailbagVO mail: mailbagVOs){                   
							if(dsnvo.getDsn().equals(mail.getDespatchSerialNumber())){            
								if(MailConstantsVO.FLAG_YES.equals(mail.getTransferFlag()) || 
										MailConstantsVO.FLAG_YES.equals(mail.getDeliveredFlag())||
											mail.getFromFlightSequenceNumber()!= mail.getFlightSequenceNumber()||
												!(mail.getFromFightNumber().equals(mail.getFlightNumber()))||
													MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())
														||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus()) ){
									if(!"I".equals(mail.getOperationalFlag())){
									error.add(new ErrorVO("mailtracking.defaults.mailarrival.containertransferredordelivered"));
									actionContext.addAllError((List<ErrorVO>) error);
									return;         
									}
								}else if(importedToMRA){
									error.add(new ErrorVO("mailtracking.defaults.mailarrival.mailbagsimportedtoMRA"));
									actionContext.addAllError((List<ErrorVO>) error);
									return;	
								}else{
									if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag())){
										if(!"I".equals(mail.getOperationalFlag())){
										//Undo arrive flag need to be set only for those arrived mailbags
											mail.setUndoArrivalFlag(MailConstantsVO.FLAG_YES);
										}
									}
								}
							}
						}
					}
								
				}
				
				if( error.size()==0){
					if(!mailbagLevel) 
					containerDetailsVO.setUndoArrivalFlag("CON");  
					containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE); 
				}
			}
			
			if(containerDetailsVosSelected != null && containerDetailsVosSelected.size() > 0){
				for(ContainerDetailsVO containerDetailsVO:containerDetailsVosSelected){
					containerDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
				}
			}
			
			mailArrivalVO.setContainerDetails(containerDetailsVosSelected);
			
			log.log(Log.FINE, "Going To Save ...in command...UndoArrival...",
					mailArrivalVO);
			
			try {
			    new MailTrackingDefaultsDelegate().saveUndoArrivalDetails(mailArrivalVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    			for(ErrorVO err : errors){
						if("mailtracking.defaults.err.mailAlreadyTransferedOrDelivered".equalsIgnoreCase(err.getErrorCode())){
							ErrorVO error1 = new ErrorVO("mail.operations.mailarrival.containertransferredordelivered");
							error1.setErrorDisplayType(ErrorDisplayType.ERROR);
							actionContext.addError(error1);
						}else if ("mailtracking.defaults.err.mailNotYetArrived".equalsIgnoreCase(err.getErrorCode())){
							ErrorVO error1 = new ErrorVO("mail.operations.err.mailNotYetArrived");
							error1.setErrorDisplayType(ErrorDisplayType.ERROR);
							actionContext.addError(error1);
						}
						else{
							actionContext.addAllError((List<ErrorVO>) errors);
						}
					}
	    			return;
	    	  }
			
	    	  ResponseVO responseVO = new ResponseVO();	
	      	ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
	      	mailinboundModel.setMailArrivalVO(mailArrivalVO);
	  		result.add(mailinboundModel);
	  		responseVO.setResults(result);
	  		responseVO.setStatus("success");
	  		ErrorVO error1 = new ErrorVO(SUCCESS_MESSAGE);      
	 		error1.setErrorDisplayType(ErrorDisplayType.INFO);
	        actionContext.addError(error1);
	        actionContext.setResponseVO(responseVO);
			
			
		
		
	}

}
