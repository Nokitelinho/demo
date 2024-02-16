/*
 * TransferMailCommand.java Created on Jun 30 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class TransferMailCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	/**
	 * TARGET
	 */
	private static final String TARGET_SUCCESS= "transfer_success";
	private static final String TARGET_FAIL= "transfer_fail";
	private static final String TARGET = "save_success";
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	 private static final String AUTOARRIVALFUNCTIONPOINTS="mail.operations.autoarrivalfunctionpoints";
	 private static final String AUTOARRIVALENABLEDPAS="mail.operations.autoarrivalenabledPAs";
	 private static final String FUNPNTS_TRA  = "TRA";

	/**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("TransferContainerCommand","execute");
		
		MailArrivalForm mailArrivalForm = (MailArrivalForm)invocationContext.screenModel;
		MailArrivalSession mailArrivalSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailArrivalVO newMailArrivalVO=mailArrivalSession.getMailArrivalVO();
		ArrayList<ContainerDetailsVO> containerDetails = (ArrayList<ContainerDetailsVO>)newMailArrivalVO.getContainerDetails();
		String airport = logonAttributes.getAirportCode();
		String[] dsns=mailArrivalForm.getContainer().split(",");
		Collection<String> does=new ArrayList<String>();
		Collection<ArrayList<String>> groupedOECityArpCodes = null;
		int size=dsns.length;
		for(int i=0;i<size;i++){
			ContainerDetailsVO contVO1 = ((ArrayList<ContainerDetailsVO>)(containerDetails)).get(Integer.parseInt(dsns[i].split("~")[0]));
			DSNVO dsnVO=((ArrayList<DSNVO>)contVO1.getDsnVOs()).get(Integer.parseInt(dsns[i].split("~")[1]));
			ArrayList<String> systemParameters = new ArrayList<String>();
			systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
			systemParameters.add(AUTOARRIVALENABLEDPAS);

		Map<String, String> systemParameterMap=null;
			try {
				systemParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters);
			} catch (BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
				log.log(Log.INFO,"");
			}	
			//Map<String, String> systemParameterMap1 = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters2);
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
			String outerpk = dsnVO.getOriginExchangeOffice()
	   				+dsnVO.getDestinationExchangeOffice()
	   				+dsnVO.getMailCategoryCode()
	   				+dsnVO.getMailSubclass()
	   				+dsnVO.getDsn()
	   				+dsnVO.getYear();
			if(DSNVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
				//MailbagVO mailbagVO = ((ArrayList<MailbagVO>)contVO1.getMailDetails()).get(Integer.parseInt(dsns[i].split("~")[1]));
				if(contVO1.getMailDetails()!=null){
				for(MailbagVO mailbagVO : contVO1.getMailDetails()){
					String innerpk = mailbagVO.getOoe()
		   	    			   +mailbagVO.getDoe()
		   			           +mailbagVO.getMailCategoryCode()
		   			           +mailbagVO.getMailSubclass()
		   			           +mailbagVO.getDespatchSerialNumber()
		   			           +mailbagVO.getYear();
					
					if(enableAutoArrival){
						mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
						mailbagVO.setInventoryContainer(mailbagVO.getContainerNumber());
						mailbagVO.setFlightDate(newMailArrivalVO.getArrivalDate());
						mailbagVO.setAutoArriveMail(MailConstantsVO.FLAG_YES);
						newMailArrivalVO.setContainerDetails(containerDetails);
						newMailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
						newMailArrivalVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);//Added for ICRD-156218
						newMailArrivalVO.setMailDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
						
						log.log(Log.FINE, "Going To Save ...in command...DeliverMail...",
								newMailArrivalVO);
						log.log(Log.FINE, "Going To Save ...in command", newMailArrivalVO);						
						//Collection<DSNVO> dsnVOs = contVO1.getDsnVOs();
								
										// udpate dsnvo

										try {
											updateMailbagDSNVO(dsnVO, mailbagVO,
													contVO1);
										} catch (SystemException e) {
											e.getMessage();
										}
						
					
										
											Collection<LockVO> locks = new ArrayList<LockVO>();
											Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
											if (locks == null || locks.size() == 0) {
												locks = null;
											}
											  try {
											    new MailTrackingDefaultsDelegate().saveArrivalDetails(newMailArrivalVO,locks);
									          }catch (BusinessDelegateException businessDelegateException) {
									        	  errors = handleDelegateException(businessDelegateException);
									    	  }
									    	  if (errors != null && errors.size() > 0) {
									    		invocationContext.addAllError(errors);
									    		mailArrivalSession.setMailArrivalVO(newMailArrivalVO);
									    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
									    		invocationContext.target = TARGET;
									    		return;
									    	  }
											
		
					}
					if(innerpk.equals(outerpk)){
						if(MailbagVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())){
							log.log(Log.FINE, "Mailbag already delivered-transfer need to be shown");
							invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadydeliveredfortransfer"));
							invocationContext.target = TARGET_FAIL;
							return;
							} 
						
						//Modified as part of bug ICRD-152490 by A-5526
						else if ((MailConstantsVO.MAIL_STATUS_TRANSFERRED
									.equals(mailbagVO.getMailStatus())
									|| !airport.equals(mailbagVO
											.getScannedPort())
									||

									(MailbagVO.FLAG_YES.equals(mailbagVO
											.getArrivedFlag())
											&& (MailConstantsVO.MAIL_STATUS_ASSIGNED
													.equals(mailbagVO
															.getMailStatus()) || (mailbagVO
											.getFlightNumber() != null && mailbagVO.getFromFightNumber()!=null && !mailbagVO
											.getFlightNumber()
											.equals(mailbagVO
													.getFromFightNumber())) || (  mailbagVO.getFlightSequenceNumber()!=mailbagVO.getFromFlightSequenceNumber())))))
						             {                  
							log.log(Log.FINE, "Mailbag already transferred-transfer need to be shown");	   
		                     //Added for ICRD-126577
							
							if(!"Y".equals(mailbagVO.getArrivedFlag()) && !enableAutoArrival){
								invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.noreceivedbags"));					
							}
							//Added by A-7540 for ICRD-233028	

							else if ("N".equals(mailbagVO.getAcceptanceFlag()) && "I".equals(mailbagVO.getOperationalFlag()))
							{
                                 invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagnotarrived")); 
							}
							
							else if(MailConstantsVO.MAIL_STATUS_TRANSFERRED
									.equals(mailbagVO.getMailStatus())){
								invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagalreadytransfered"));
							}  
								
							
							if(invocationContext.getErrors() != null){
							invocationContext.target = TARGET_FAIL;
							return;
							}
						}
	
					}
				}				
			  }
			}else {
				/**
				 * No of deliver bags and deliver weight total need to be considered
				 * this need to be compared.
				 */
/*				if(dsnVO.getDeliveredBags()!=0 && dsnVO.getDeliveredWeight()!=0.0){
					log.log(Log.FINE, "Despatch already delivered-transfer need to be shown");
					invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.desptachalreadydeliveredfortransfer"));
					invocationContext.target = TARGET_FAIL;
					return;
				}*/
			}
 			if(!does.contains(dsnVO.getDestinationExchangeOffice())){
 				does.add(dsnVO.getDestinationExchangeOffice());
 			}

			if(MailConstantsVO.ZERO==dsnVO.getReceivedBags()){
				mailArrivalForm.setChkFlag("");
				invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.noreceivedbags"));					
				invocationContext.target = TARGET_FAIL;
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
				Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
				log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
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
				invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.cannottransferdsn"));
				mailArrivalForm.setChkFlag("");
				invocationContext.target = TARGET_FAIL;
				return;
			}			
		}
		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		mailArrivalForm.setChkFlag("showTransferMailScreen");		
		invocationContext.target = TARGET_SUCCESS;		
		log.exiting("TransferContainerCommand","execute");
		
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
