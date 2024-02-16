/*
 * DeliverMailCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class DeliverMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "save_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
   private static final String CONST_OP="U";
   private static final String SAVE_SUCCESS = 
		"mailtracking.defaults.mailsubclassmaster.msg.info.savesuccess";
   private static final String FLIGHT ="FLT";
   private static final String SCREEN_NUMERICAL_ID ="MTK007";

   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ArriveMailCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		String[] selectedContainer = mailArrivalForm.getSelectContainer();
		String[] childContainer = mailArrivalForm.getChildContainer();
		ContainerDetailsVO containerDetailsVO = null;
		ArrayList<ContainerDetailsVO> containerDtlsVOs = (ArrayList<ContainerDetailsVO>) mailArrivalVO.getContainerDetails();
		if(containerDtlsVOs != null && containerDtlsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDtlsVOs){
    			containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
    		}
    	}
		log.log(Log.FINE, "!!!!!mailArrivalVO.getScanDate()0000000",
				mailArrivalVO.getScanDate());
		//Added by A-4809 to throw error select either mailbag/ULD
		boolean isContainerSelected = false;
		boolean isDSNSelected = false;
		if(selectedContainer !=null && selectedContainer.length>0) {
			isContainerSelected = true;
		}
		if(childContainer !=null && childContainer.length>0){
			isDSNSelected = true;
		}
		if(isContainerSelected && isDSNSelected){
			log.log(Log.FINE, "Conatiner and Mailbag selected,either one to be select");
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.eithercontormail"));
			invocationContext.target = TARGET;
			return;
		}
		if(selectedContainer!= null || childContainer!=null ){
			if(selectedContainer!=null){
	    		
	    		for(int i=0;i<selectedContainer.length;i++){    			
	    			log.log(Log.FINE, "$$$$$$$$$$$$$containerPrimaryKey[i]$$",
							selectedContainer, i);
					containerDetailsVO =  containerDtlsVOs.get(Integer.parseInt(selectedContainer[i]));
					log
							.log(
									Log.FINE,
									"$$$$$$$$$$$$$concontainerDetailsVO.getOperationFlag()",
									containerDetailsVO);
					if("I".equals(containerDetailsVO.getOperationFlag())){
						log.log(Log.FINE, "$$$$$$$$$$$$$");
						invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.datanotyetsaved"));
						invocationContext.target = TARGET;
						return;
					}
					//Added by A-4809 for avoiding deliver twice Starts
					ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>)containerDetailsVO.getDsnVOs();
					if(dsnVos!=null && dsnVos.size()>0){ 
						for(DSNVO dsnVO:dsnVos){
							if(FLAG_YES.equals(dsnVO.getPltEnableFlag())){
								Collection<MailbagVO> mailbagVOs= containerDetailsVO.getMailDetails();	
								for(MailbagVO mail: mailbagVOs){
									String Flight1 = new StringBuilder(FLIGHT).append(mail.getCarrierId()).append(mail.getFlightSequenceNumber()).append(mail.getFlightNumber()).toString();
									String Flight2 = new StringBuilder(FLIGHT).append(mail.getFromCarrierId()).append(mail.getFromFlightSequenceNumber()).append(mail.getFromFightNumber()).toString();
									if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){
										if((FLAG_YES.equals(mail.getDeliveredFlag()))||(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus()))){
											invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydelivered"));
						invocationContext.target = TARGET;
						return;
										}else if(!Flight1.equals(Flight2)){
//Modified a part of ICRD-156956 by A-5526
											if((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus()) && logonAttributes.getAirportCode().equals(mail.getScannedPort()))||
													MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus())||
													MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())||
													MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mail.getMailStatus())){
												if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())){
													invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadytransfered"));
												}
												else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus()) &&
														logonAttributes.getAirportCode().equals(mail.getScannedPort())) {
													invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadyarrivedinanotherflight"));
												}else{
												invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydelivered"));
												}
												
												invocationContext.target = TARGET;
												return;									
												
											}
										}							
									}
								}
							}
						}
					}
					//Added by A-4809 for avoiding deliver twice Ends
					containerDetailsVO.setOperationFlag(CONST_OP);					
					Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
					if(mailbags !=null){
		      			for(MailbagVO mailbagVO :mailbags ){
		      				if("I".equals(mailbagVO.getOperationalFlag())){
								log.log(Log.FINE, "$$$$$$$$$$$$$");
								invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.datanotyetsaved"));
								invocationContext.target = TARGET;
								return;
							}
			    			log.log(Log.FINE, "$$$$$$$UPDATE MAIL");
		  					mailbagVO.setOperationalFlag(CONST_OP);
		  					mailbagVO.setScannedDate(mailArrivalVO.getScanDate());
		  					mailbagVO.setMailSource(SCREEN_NUMERICAL_ID);//Added for ICRD-156218
		       			}  
					}
					Collection<DespatchDetailsVO> despatches = containerDetailsVO.getDesptachDetailsVOs();
					if(despatches !=null){
		      			for(DespatchDetailsVO despatchVO :despatches ){
		      				log.log(Log.FINE, "$$$$$$$UPDATE DESPATCH");
		      				if("I".equals(despatchVO.getOperationalFlag())){
								log.log(Log.FINE, "$$$$$$$$$$$$$");
								invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.datanotyetsaved"));
								invocationContext.target = TARGET;
								return;
							}
		      				despatchVO.setOperationalFlag(CONST_OP);
		       			}  
					}						
				}
			}
			else{
	    		String selectedDSN[] = mailArrivalForm.getChildContainer();
	    		String selectMainContainer = mailArrivalForm.getSelectMainContainer();
	    		//Commented by indu for bug 50957 since selectedDSNs is only taking the first value for delivery.
	    		/*String selectedDSNs=selectedDSN[0];
		   	    String[] primaryKey = selectedDSNs.split(",");   */	
		   	    ContainerDetailsVO containerDtlsVO= containerDtlsVOs.get(Integer.parseInt(selectMainContainer));
		   	    if("I".equals(containerDtlsVO.getOperationFlag())){
					invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.datanotyetsaved"));
					invocationContext.target = TARGET;
					return;
				}
		   	    containerDtlsVO.setOperationFlag(CONST_OP);
		   	  if("M".equals(mailArrivalForm.getSelectMode())){
		   	    	containerDtlsVO.setDeliverPABuiltContainer("N");
		   	    }
	   			for (int i=0;i<selectedDSN.length;i++){
//	   			Added  by indu for bug 50957 starts
	   				int symIdx=selectedDSN[i].indexOf("~");
	   				int dsnLen=selectedDSN[i].length();
	   				String pKey = selectedDSN[i].substring(symIdx+1,dsnLen);
//		   			Added  by indu for bug 50957 ends
	   	    		DSNVO dsnVO=(DSNVO) new ArrayList<DSNVO>(containerDtlsVO.getDsnVOs()).get(Integer.parseInt(pKey));
	   	    		
	   	    		String innerpk = dsnVO.getOriginExchangeOffice()
	   				+dsnVO.getDestinationExchangeOffice()
	   				+dsnVO.getMailCategoryCode()
	   				+dsnVO.getMailSubclass()
	   				+dsnVO.getDsn()
	   				+dsnVO.getYear();
	   	    		log.log(Log.FINE, "@#$#$#%$#$dsnVO ::", dsnVO);
					if(("Y").equalsIgnoreCase(dsnVO.getPltEnableFlag())){
	   	    			if(containerDtlsVO.getMailDetails()!=null){
		   	    			for(MailbagVO mailbagVO : containerDtlsVO.getMailDetails()){
		   	    				
		   	    			   String outerpk = mailbagVO.getOoe()
		   	    			   +mailbagVO.getDoe()
		   			           +mailbagVO.getMailCategoryCode()
		   			           +mailbagVO.getMailSubclass()
		   			           +mailbagVO.getDespatchSerialNumber()
		   			           +mailbagVO.getYear();
		   	    			   if("I".equals(mailbagVO.getOperationalFlag())){
									log.log(Log.FINE, "$$$$$$$$$$$$$");
									invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.datanotyetsaved"));
									invocationContext.target = TARGET;
									return;
		   	    			   }
		   	    			   if(innerpk.equals(outerpk)){		   	    				
								String Flight1 = new StringBuilder(FLIGHT).append(mailbagVO.getCarrierId()).append(mailbagVO.getFlightSequenceNumber()).append(mailbagVO.getFlightNumber()).toString();
								String Flight2 = new StringBuilder(FLIGHT).append(mailbagVO.getFromCarrierId()).append(mailbagVO.getFromFlightSequenceNumber()).append(mailbagVO.getFromFightNumber()).toString();								
		   	    				//Added by A-4809 for avoiding deliver twice Starts
		   	    				   if((FLAG_YES.equals(mailbagVO.getDeliveredFlag()))||(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getMailStatus()))){
		   							log.log(Log.FINE, "Mailbag already delivered-this need to be shown");
		   							invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydelivered"));
		   							invocationContext.target = TARGET;
		   							return;
		   						}else if(!Flight1.equals(Flight2)){ 
		   							//Modified for ICRD-147823 starts
									if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getMailStatus())
											){
										invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydelivered"));
										invocationContext.target = TARGET;
										return;									
									}else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus()) && logonAttributes.getAirportCode().equals(mailbagVO.getScannedPort())){
										invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadyarrivedinanotherflight"));
										invocationContext.target = TARGET;
										return;
									}
									//Modified for ICRD-147823 ends	
									else if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagVO.getMailStatus())||
											MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagVO.getMailStatus())){
										invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadytransfered"));
										invocationContext.target = TARGET;
										return;									
									}
		   						}
		   	    				//Added by A-4809 for avoiding deliver twice Ends
			   	    				mailbagVO.setOperationalFlag(CONST_OP);
			   	    				mailbagVO.setScannedDate(mailArrivalVO.getScanDate());
			   	    				mailbagVO.setMailSource(SCREEN_NUMERICAL_ID);//Added for ICRD-156218
		   	    			   }
		   	    			}
	   	    			}
	   	    		}
	   	    		else{
	   	    			for(DespatchDetailsVO despatchDetailsVO : containerDtlsVO.getDesptachDetailsVOs()){
	   	    				String outpk = despatchDetailsVO.getOriginOfficeOfExchange()
	   	    				+despatchDetailsVO.getDestinationOfficeOfExchange()
	   	 		            +despatchDetailsVO.getMailCategoryCode()
	   	 		            +despatchDetailsVO.getMailSubclass()
	   	 		            +despatchDetailsVO.getDsn()
	   	 		            +despatchDetailsVO.getYear();
	   	    				if("I".equals(despatchDetailsVO.getOperationalFlag())){
								log.log(Log.FINE, "$$$$$$$$$$$$$");
								invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.datanotyetsaved"));
								invocationContext.target = TARGET;
								return;
	   	    			   }
	   	     			    if(innerpk.equals(outpk)){
	   	     			  //Added by A-4809 for avoiding deliver twice Starts
	   	     			   /**
	   	     			    * delivered bag count and weight count need to be compared with std or acp
	   	     			    */
/*	   	     			    	if(despatchDetailsVO.getDeliveredBags()!=0 && despatchDetailsVO.getDeliveredWeight()!=0.0){
		   							log.log(Log.FINE, "Despatch already delivered-this need to be shown");
		   							invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.despatchalreadydelivered"));
		   							invocationContext.target = TARGET;
		   							return;
	   	     			    	}*/
	   	     			  //Added by A-4809 for avoiding deliver twice Ends
	   	     			    	despatchDetailsVO.setOperationalFlag(CONST_OP);
	   	     			    }
	   	     			}
	   	    		}   	    			
	   	    	}	
			}
			mailArrivalVO.setPartialDelivery(true);
		}else{
			/**
			 * Added by A-4809
			 * Delivery of mailbag/despatch need to preveneted if any of the mailbag/despatch is already delivered.
			 * Individual delivery need to be performed in that case.Generic message will only be thrown
			 */
			if(containerDtlsVOs!=null && containerDtlsVOs.size()>0){
				for(ContainerDetailsVO contVO:containerDtlsVOs){
					ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>)contVO.getDsnVOs();
					if(dsnVos!=null && dsnVos.size()>0){
						for(DSNVO dsnVO:dsnVos){
							if(FLAG_YES.equals(dsnVO.getPltEnableFlag())){
								Collection<MailbagVO> mailbagVOs= contVO.getMailDetails();	
								for(MailbagVO mail: mailbagVOs){
									String Flight1 = new StringBuilder(FLIGHT).append(mail.getCarrierId()).append(mail.getFlightSequenceNumber()).append(mail.getFlightNumber()).toString();
									String Flight2 = new StringBuilder(FLIGHT).append(mail.getFromCarrierId()).append(mail.getFromFlightSequenceNumber()).append(mail.getFromFightNumber()).toString();
									if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){
										if((FLAG_YES.equals(mail.getDeliveredFlag()))||(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus()))){
											invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydelivered"));
											invocationContext.target = TARGET;
											return;
										}else if(!Flight1.equals(Flight2)){
											if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus())||
													MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus())
													){
												invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydelivered"));
												invocationContext.target = TARGET;
												return;									
											}else if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())||
													MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mail.getMailStatus())){     
												invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadytransfered"));
												invocationContext.target = TARGET;
												return;	
											}
																		
										}							
									}
									mail.setMailSource(SCREEN_NUMERICAL_ID);//Added for ICRD-156218
								}
							}
						}
					}
				}
			}
		}
		mailArrivalVO.setContainerDetails(containerDtlsVOs);
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		mailArrivalVO.setDeliveryNeeded(true);
		log.log(Log.FINE, "Going To Save ...in command...DeliverMail...",
				mailArrivalVO);
		try {
		    new MailTrackingDefaultsDelegate().deliverMailbags(mailArrivalVO);
        }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	}
    	if (errors != null && errors.size() > 0) {
    		Collection<ContainerDetailsVO> containerDtlsVOsCopy=mailArrivalVO.getContainerDetails();          
    		for(ContainerDetailsVO containerDetailsVOForIterate:containerDtlsVOsCopy)
    		{
    			containerDetailsVOForIterate.setOperationFlag("");
    			Collection<MailbagVO> mails=containerDetailsVOForIterate.getMailDetails();                 
    			for(MailbagVO mail:mails)
    			{  mail.setOperationalFlag("");  
    			
    			}
    		}
    		mailArrivalForm.setChildContainer(null);
    		mailArrivalForm.setSelectContainer(null);    
    		mailArrivalVO.setDeliveryCheckNeeded(false);            
    		mailArrivalVO.setDeliveryNeeded(false);
    		
    		invocationContext.addAllError(errors);
    		mailArrivalSession.setMailArrivalVO(mailArrivalVO);
    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = TARGET;
    		return;
    	}    	
    	MailArrivalVO mailArriveVO = new MailArrivalVO(); 
    	//Added for ICRD-134007 starts
/*    	LocalDate arrivalDate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
    	arrivalDate.set(mailArrivalVO.getArrivalDate());*/
    	mailArriveVO.setFlightCarrierCode(mailArrivalVO.getFlightCarrierCode());
    	mailArriveVO.setFlightNumber(mailArrivalVO.getFlightNumber());
    	mailArriveVO.setArrivalDate(mailArrivalVO.getArrivalDate());
    	//Added for ICRD-134007 ends
    	mailArrivalSession.setMailArrivalVO(mailArriveVO);
    	mailArrivalForm.setSaveSuccessFlag(FLAG_YES);//Added for ICRD-134007 
    	//mailArrivalForm.setListFlag("FAILURE");//Commented for ICRD-134007 
    	mailArrivalForm.setOperationalStatus("");
    	FlightValidationVO flightValidationVO = new FlightValidationVO();
    	mailArrivalSession.setFlightValidationVO(flightValidationVO);
    	
    	mailArrivalForm.setInitialFocus(FLAG_YES);
    	mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	mailArrivalForm.setArrivalPort(logonAttributes.getAirportCode());
		
    	//invocationContext.addError(new ErrorVO(SAVE_SUCCESS));//Commented for ICRD-134007 

    	invocationContext.target = TARGET;
       	
    	log.exiting("DeliverMailCommand","execute");
    	
    }
}
