package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.ux.checkembargos.CheckEmbargoSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ValidateTransferMailCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	25-Nov-2018		:	Draft
 */
public class ValidateTransferMailCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	 private static final String AUTOARRIVALFUNCTIONPOINTS="mail.operations.autoarrivalfunctionpoints";
	 private static final String AUTOARRIVALENABLEDPAS="mail.operations.autoarrivalenabledPAs";
	 private static final String FUNPNTS_TRA  = "TRA";
	 private static final String EMBARGO_EXIST="mail.operations.err.embargoexists";
	 private static final String EMBARGO_EXISTS = "Embargo Exists";


	private Log log = LogFactory.getLogger("MAIL OPERATIONS ValidateTransferMailCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("ValidateTransferMailCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		MailArrivalVO mailArrivalVO=null;
		ContainerDetailsVO containerDetailsVO=
				new ContainerDetailsVO();
		HashMap<String,DSNVO> dsnDetailsMap=
				new HashMap<String,DSNVO>();
		Collection<DSNVO> selectedDsnVos=
				 new ArrayList<DSNVO>();
		HashMap<String,MailbagVO> mailDetailsMap=
				new HashMap<String,MailbagVO>();
		Collection<MailbagVO> selectedMailBagVos=
				 new ArrayList<MailbagVO>();
		
		
		ResponseVO responseVO = new ResponseVO();	
		ContainerDetails containerDetails=mailinboundModel.getContainerDetail();
		ArrayList<DSNDetails> dsnDetailsCollection=
				containerDetails.getDsnDetailsCollection();
		String airport = logonAttributes.getAirportCode();
		Collection<String> does=new ArrayList<String>();
		Collection<ArrayList<String>> groupedOECityArpCodes = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
		//systemParameters.add(AUTOARRIVALENABLEDPAS);

	Map<String, String> systemParameterMap=null;
		try {
			systemParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters);
		} catch (BusinessDelegateException businessDelegateException) {
			Collection<ErrorVO> errorVOs = handleDelegateException(businessDelegateException);
			log.log(Log.INFO,"");
		}	
		//Map<String, String> systemParameterMap1 = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters2);
		
		OperationalFlightVO operationalFlightVO=null;
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		
    	CheckEmbargoSession checkEmbargoSession = getScreenSession("reco.defaults", "reco.defaults.ux.checkembargo");
		MailArrivalVO mailArrivalDetailsVO = (MailArrivalVO)actionContext.getAttribute("mailArrivalDetails");
		List<ErrorVO> existingerrors = actionContext.getErrors();
		if(CollectionUtils.isNotEmpty(existingerrors) && CollectionUtils.isNotEmpty(mailArrivalDetailsVO.getEmbargoDetails()))
	    {
	    	  Iterator errorsList = existingerrors.iterator();
	    	  while (errorsList.hasNext()) {
	        	  ErrorVO errorVO = (ErrorVO)(errorsList).next();
	        	  if (EMBARGO_EXIST.equals(errorVO.getErrorCode()) && 
	        			  !MailConstantsVO.INFO.equals(mailArrivalDetailsVO.getEmbargoDetails().iterator().next().getEmbargoLevel()))
		          {
	        		 checkEmbargoSession.setEmbargos(mailArrivalDetailsVO.getEmbargoDetails());
			         if(MailConstantsVO.WARNING.equals(mailArrivalDetailsVO.getEmbargoDetails().iterator().next().getEmbargoLevel())){
			        	 checkEmbargoSession.setContinueAction("mail.operations.ux.mailinbound.arrivemail");
			        	}
		        	 responseVO.setStatus("embargo");
		        	 errorsList.remove(); 
			         actionContext.setResponseVO(responseVO);
			         return;
			      }else{
			    	  errorsList.remove();
			      }
	    	  } 
	   }
	    else if(CollectionUtils.isNotEmpty(existingerrors)) {
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
			for(ContainerDetailsVO containerDetailsVOIterate:mailArrivalVO.getContainerDetails()){
				if(containerDetails.getContainerno().equals(containerDetailsVOIterate.getContainerNumber())){
					containerDetailsVO=containerDetailsVOIterate;
					break;
							
				}
			}
			dsnDetailsMap.clear();
			if(null!=dsnDetailsCollection&&dsnDetailsCollection.size()>0){
				for(DSNVO dsnvo:containerDetailsVO.getDsnVOs()){
					String dsnKey=new StringBuilder(dsnvo.getDsn()).append(dsnvo.getOriginExchangeOffice())
							.append(dsnvo.getDestinationExchangeOffice())
								.append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass())
									.append(dsnvo.getYear()).toString();
					dsnDetailsMap.put(dsnKey, dsnvo);	
				}
				selectedDsnVos.clear();
				for(DSNDetails dsnDetails:containerDetails.getDsnDetailsCollection()){
					String dsnKey=new StringBuilder(dsnDetails.getDsn()).append(dsnDetails.getOriginExchangeOffice())
							.append(dsnDetails.getDestinationExchangeOffice())
								.append(dsnDetails.getMailCategoryCode()).append(dsnDetails.getMailSubclass())
									.append(dsnDetails.getYear()).toString();
					if(dsnDetailsMap.containsKey(dsnKey)){
						selectedDsnVos.add(dsnDetailsMap.get(dsnKey));
					}
				}
			
				containerDetailsVO.setDsnVOs(selectedDsnVos);
			
			}
			else if(null!=containerDetails.getMailBagDetailsCollection()&&containerDetails.getMailBagDetailsCollection().size()>0){
				mailDetailsMap.clear();
				for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
					String mailKey=new StringBuilder(mailbagVO.getDespatchSerialNumber()).append(mailbagVO.getOoe())
							.append(mailbagVO.getDoe()).append(mailbagVO.getMailCategoryCode()).append(mailbagVO.getMailSubclass())
							.append(mailbagVO.getYear()).toString();
					//Added by A-7540
					mailbagVO.setInventoryContainer(containerDetailsVO.getContainerNumber());
					mailbagVO.setInventoryContainerType(containerDetailsVO.getContainerType());
					mailDetailsMap.put(mailbagVO.getMailbagId(), mailbagVO);	
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
				ArrayList<String>addedKeyToDsnVO=new ArrayList<String>();
				for(MailBagDetails mailBagDetails:containerDetails.getMailBagDetailsCollection()){
					if(mailBagDetails != null){
					String mailBagKey=new StringBuilder(mailBagDetails.getDSN()).append(mailBagDetails.getOoe())
							.append(mailBagDetails.getDoe()).append(mailBagDetails.getCategory()).append(mailBagDetails.getSubClass())
							.append(mailBagDetails.getYear()).toString();
					if(mailDetailsMap.containsKey(mailBagDetails.getMailBagId())){
						selectedMailBagVos.add(mailDetailsMap.get(mailBagDetails.getMailBagId()));
					}
					if(dsnDetailsMap.containsKey(mailBagKey)){
						if(!addedKeyToDsnVO.contains(mailBagKey)){
						selectedDsnVos.add(dsnDetailsMap.get(mailBagKey));
						addedKeyToDsnVO.add(mailBagKey);
					   }
					}
				}
				}
				containerDetailsVO.setDsnVOs(selectedDsnVos);
				containerDetailsVO.setMailDetails(selectedMailBagVos);
				
			}
		
		}
		else{
			actionContext.addError(new ErrorVO("mail.operations.err.containernotfound"));
			return;
		}
		
		
		Collection<DSNVO> dsnvos=containerDetailsVO.getDsnVOs();
		
		for(DSNVO dsnVO:dsnvos){
			String outerpk = dsnVO.getOriginExchangeOffice()
	   				+dsnVO.getDestinationExchangeOffice()
	   				+dsnVO.getMailCategoryCode()
	   				+dsnVO.getMailSubclass()
	   				+dsnVO.getDsn()
	   				+dsnVO.getYear();
			if(DSNVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
				if(containerDetailsVO.getMailDetails()!=null){
					for(MailbagVO mailbagVO : containerDetailsVO.getMailDetails()){
						String scnPrtForVal=mailbagVO.getScannedPort();
						String innerpk = mailbagVO.getOoe()
			   	    			   +mailbagVO.getDoe()
			   			           +mailbagVO.getMailCategoryCode()
			   			           +mailbagVO.getMailSubclass()
			   			           +mailbagVO.getDespatchSerialNumber()
			   			           +mailbagVO.getYear();
						String sysparfunctionpoints = null;
						//String autoArrEnabledPAs= null;
						boolean enableAutoArrival = false;
						if (systemParameterMap != null) {
							sysparfunctionpoints = systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
							//autoArrEnabledPAs=systemParameterMap.get(AUTOARRIVALENABLEDPAS);
						}
						if(sysparfunctionpoints!=null &&
								sysparfunctionpoints.contains(FUNPNTS_TRA)
								){
							enableAutoArrival = true;						  
						}
						if(enableAutoArrival){
							mailbagVO.setAutoArriveMail(MailConstantsVO.FLAG_YES);
							try {
								updateMailbagDSNVO(dsnVO,
										 mailbagVO,containerDetailsVO) ;
							} catch (SystemException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							log.log(Log.FINE, "Going To Save ...in command...DeliverMail...",
									mailArrivalVO);
							log.log(Log.FINE, "Going To Save ...in command", mailArrivalVO);						
						}
						if(innerpk.equals(outerpk)){
							if(MailbagVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())||MailConstantsVO.MAIL_STATUS_DELIVERED
									.equals(mailbagVO.getMailStatus())){
								log.log(Log.FINE, "Mailbag already delivered-transfer need to be shown");
								actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydeliveredfortransfer"));
								return;
							} 
							
							else if ( (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagVO.getMailStatus()) && airport.equals(scnPrtForVal) )||
											!airport.equals(mailbagVO.getScannedPort())||
													(MailbagVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())&& 
															(MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagVO.getMailStatus())||
																	(mailbagVO.getFlightNumber() != null 
																			&& mailbagVO.getFromFightNumber()!=null &&
																				!mailbagVO.getFlightNumber().equals(mailbagVO.getFromFightNumber())) ||
																						( mailbagVO.getFlightSequenceNumber()!=mailbagVO.getFromFlightSequenceNumber())))) {                    
									log.log(Log.FINE, "Mailbag already transferred-transfer need to be shown");	   
									MailbagInULDForSegmentVO mailbagInULDForSegmentVO=null;
									mailbagInULDForSegmentVO=  new MailTrackingDefaultsDelegate().getManifestInfo(mailbagVO);
							if(!"Y".equals(mailbagVO.getArrivedFlag()) && !enableAutoArrival){
								actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.noreceivedbags"));					
								return;
							}
							else if ("N".equals(mailbagVO.getAcceptanceFlag()) && "I".equals(mailbagVO.getOperationalFlag())){
                                 actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagnotarrived")); 
                                 return;
							}
							
							else if(MailConstantsVO.MAIL_STATUS_RETURNED
									.equals(mailbagVO.getMailStatus())){
										actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadyreturned"));
										return;
							}
							else if(mailbagInULDForSegmentVO!=null||((!MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus()))&&airport.equals(scnPrtForVal)) ){  
								actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadytransfered"));
										return;
							}  
							
							}
						}
					}
				}
			}
			
			if(!does.contains(dsnVO.getDestinationExchangeOffice())){
 				does.add(dsnVO.getDestinationExchangeOffice());
 			}

			if(MailConstantsVO.ZERO==dsnVO.getReceivedBags()){
				actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.noreceivedbags"));					
				return;					
				
			}
			
		}
		
		if(does != null && does.size()!=0){
			try {
				/*
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
				log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
			}
			
			int errorFlag = 0;			
 			if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0){
 				for(String doe : does){
 					 String destinationPort=null;
 					Page<OfficeOfExchangeVO> exchDstAirport = null;
 					try {
 						exchDstAirport = new MailTrackingDefaultsDelegate()
 										.findOfficeOfExchange(logonAttributes.getCompanyCode(), doe, 1);
 					} catch (BusinessDelegateException businessDelegateException) {
 						errors = handleDelegateException(businessDelegateException);
 					}
 					OfficeOfExchangeVO destOfficeOfExchangeVO=new OfficeOfExchangeVO();
 					if(exchDstAirport!=null && !exchDstAirport.isEmpty()){
 						destOfficeOfExchangeVO = exchDstAirport.iterator().next();
 					}
 					for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
 						if (destOfficeOfExchangeVO != null && destOfficeOfExchangeVO.getAirportCode() != null) {
 							destinationPort=destOfficeOfExchangeVO.getAirportCode();
 						}
 						else if (cityAndArpForOE.size() == 3 && 
 								doe.equals(cityAndArpForOE.get(0))){
 							destinationPort=cityAndArpForOE.get(2);
 						}
 						if(cityAndArpForOE.size() == 3 && 
 								doe.equals(cityAndArpForOE.get(0)) && 
 								airport.equals(destinationPort)){
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
		   //Added as part of IASCB-34119 starts
				mailinboundModel.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
				Collection<PartnerCarrierVO> partnerCarriers = new ArrayList<PartnerCarrierVO>();
				ArrayList<String> partnerCarriersList = new ArrayList<String>();
				try {
					partnerCarriers = new MailTrackingDefaultsDelegate().findAllPartnerCarriers(
							logonAttributes.getCompanyCode(), logonAttributes.getOwnAirlineCode(),
							logonAttributes.getAirportCode());
				} catch (BusinessDelegateException businessDelegateException) {
					businessDelegateException.getMessage();
				}
				if (partnerCarriers != null && partnerCarriers.size() > 0) {
					for (PartnerCarrierVO partnerCarrierVO : partnerCarriers) {
						partnerCarriersList.add(partnerCarrierVO.getPartnerCarrierCode());
					}
				}
				mailinboundModel.setPartnerCarriers(partnerCarriersList);
		
				//Added as part of IASCB-34119 ends
		
    	ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("ValidateTransferMailCommand","execute");
		
		
	}
	
	private void updateMailbagDSNVO(DSNVO dsnVO,
			MailbagVO mailbagVO, ContainerDetailsVO containerDetailsVO) 
					throws SystemException {
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			if (MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())) {
				if (dsnVO.getDsn().equals(mailbagVO.getDespatchSerialNumber())
						&& dsnVO.getOriginExchangeOffice().equals(
								mailbagVO.getOoe())
						&& dsnVO.getDestinationExchangeOffice().equals(
								mailbagVO.getDoe())
						&& dsnVO.getMailCategoryCode().equals(
								mailbagVO.getMailCategoryCode())
						&& dsnVO.getMailSubclass().equals(
								mailbagVO.getMailSubclass())
						&& dsnVO.getYear() == mailbagVO.getYear()) {
					if (MailConstantsVO.FLAG_YES.equals(mailbagVO
							.getAcceptanceFlag())) {
						// manifested mailbag
						if (!MailConstantsVO.FLAG_YES.equals(mailbagVO
								.getArrivedFlag())) {
							dsnVO.setReceivedBags(dsnVO.getReceivedBags() + 1);
							/*if(mailbagVO.getWeight()!=null){
							dsnVO.setReceivedWeight(dsnVO.getReceivedWeight()
									+ mailbagVO.getWeight().getSystemValue());//added by a-7371
							}*/
							
								try {
									dsnVO.setReceivedWeight(Measure.addMeasureValues(dsnVO.getReceivedWeight(), mailbagVO.getWeight()));
								} catch (UnitException e) {
									// TODO Auto-generated catch block
									throw new SystemException(e.getErrorCode());
								}//added by A-7371
							

							// update containerVO
							containerDetailsVO
									.setReceivedBags(containerDetailsVO
											.getReceivedBags() + 1);
							if(!"I".equals(containerDetailsVO.getOperationFlag())){
								containerDetailsVO.setOperationFlag("U");
								dsnVO.setOperationFlag("U");
					    	}
							mailbagVO.setArrivedFlag("Y");
							mailbagVO.setScannedPort(logonAttributes.getAirportCode());
							mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
							mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
							mailbagVO.setUldNumber(mailbagVO.getContainerNumber());
							if(mailbagVO.getWeight()!=null){
						/*	containerDetailsVO
									.setReceivedWeight(containerDetailsVO
											.getReceivedWeight()
											+ mailbagVO.getWeight());*///added by A-7371
								try {
									containerDetailsVO
									.setReceivedWeight(Measure.addMeasureValues(containerDetailsVO
												.getReceivedWeight(), mailbagVO.getWeight()));
								} catch (UnitException e) {
									// TODO Auto-generated catch block
									throw new SystemException(e.getErrorCode());
								}//added by A-7371
							}
						}
				 }
			   }
		    }
	     }

}
