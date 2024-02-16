/*
 * ArriveMailCommand.java Created on Jun 30 2016
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
import java.util.HashMap;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.checkembargos.CheckEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
/**
 * @author A-5991
 *
 */
public class ArriveMailCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "save_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
   private static final String SAVE_SUCCESS =
		"mailtracking.defaults.mailsubclassmaster.msg.info.savesuccess";
   private static final String FLIGHT ="FLT";
   private static final String SCREEN_NUMERICAL_ID ="MTK007";
	private static final String EMBARGO_EXISTS = "embargo_exists";
	/** The Constant MODULE_NAME_EMG. */
	private static final String MODULE_NAME_EMG = "reco.defaults";

	/** The Constant SCREEN_ID_EMG. */
	private static final String SCREEN_ID_EMG = "reco.defaults.checkembargo";
	private static final String EMBARGO_LEVEL_ERROR = "E";

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

    	//A-8061 added for ICRD-263340 Begin
    	HashMap<String,String> embargoMailMap = new HashMap<String,String>();
    	if(mailArrivalForm.getEmbargoFlag()!=null && EMBARGO_EXISTS.equals(mailArrivalForm.getEmbargoFlag())){
    	Collection<EmbargoDetailsVO> embargoDetailVos= new ArrayList<EmbargoDetailsVO>();
    	CheckEmbargoSession embargoSession =  getScreenSession(MODULE_NAME_EMG, SCREEN_ID_EMG);
    	if(embargoSession!=null && embargoSession.getEmbargos()!=null && embargoSession.getEmbargos().size() > 0){
    			embargoDetailVos =embargoSession.getEmbargos();
    	}
    	if(embargoDetailVos.size() > 0){
    			for(EmbargoDetailsVO embargoDetailsVO : embargoDetailVos){
    				if(EMBARGO_LEVEL_ERROR.equals(embargoDetailsVO.getEmbargoLevel())){
    					embargoMailMap.put(embargoDetailsVO.getShipmentID(), embargoDetailsVO.getShipmentID());
    				}
    			}
    		}
    	}
    	//A-8061 added for ICRD-263340 END
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();

		ArrayList<ContainerDetailsVO> containerDetailsVOs = (ArrayList<ContainerDetailsVO>) mailArrivalVO.getContainerDetails();
		String[] selectedContainers = mailArrivalForm.getSelectContainer();
		String[] selectedChildContainers = mailArrivalForm.getChildContainer();
		int sizeOfContainers = 0;
		boolean isContainerSelected = false;
		boolean isDSNSelected = false;

		int sizeOfChildContainers = 0;
		if(selectedContainers !=null) {
			sizeOfContainers = selectedContainers.length;
			isContainerSelected = true;
		} else {
			sizeOfContainers = containerDetailsVOs.size();
		}

		if(selectedChildContainers !=null)
		{
			sizeOfChildContainers = selectedChildContainers.length;
			//dsns of only 1 container can be selected
			sizeOfContainers = 1;
			isDSNSelected = true;
		}
		/**
		 * User shud either select mailbag/despatch or Container for arrival
		 * Selecting mailbag/despatch and container together to be prevented
		 * As discussed with Ravi
		 */
		if(isContainerSelected && isDSNSelected){
			log.log(Log.FINE, "Conatiner and Mailbag selected,either one to be select");
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.eithercontormail"));
			invocationContext.target = TARGET;
			return;
		}
		/**
		 * If user select a mailbag which is already arrived,a error message need to be thrown
		 * At container level, this is not implemented.At conatiner level all mailbags in container needs to be
		 * checked.As per discussion with Ravi currently this need not be implemented
		 */
		if(selectedChildContainers!=null){

    				for(int i=0;i<selectedChildContainers.length;i++){
	    				int val=Integer.parseInt(selectedChildContainers[i].split("~")[1]);
	    				int containerVal = Integer.parseInt(selectedChildContainers[i].split("~")[0]);
	    				ContainerDetailsVO containerVO = containerDetailsVOs.get(containerVal);
				ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>)containerVO.getDsnVOs();
				DSNVO dsnVO = dsnVos.get(val);
				if(FLAG_YES.equals(dsnVO.getPltEnableFlag())){
					log.log(Log.FINE, "mailbag is selected");
					Collection<MailbagVO> mailbagVOs= containerVO.getMailDetails();
					for(MailbagVO mail: mailbagVOs){
						if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){
							String Flight1 = new StringBuilder(FLIGHT).append(mail.getCarrierId()).append(mail.getFlightSequenceNumber()).append(mail.getFlightNumber()).toString();
							String Flight2 = new StringBuilder(FLIGHT).append(mail.getFromCarrierId()).append(mail.getFromFlightSequenceNumber()).append(mail.getFromFightNumber()).toString();
							//Added as part of BUG ICRD-112270 by A-5526
							String selectedDSN=new StringBuilder(dsnVO.getOriginExchangeOffice()).append(dsnVO.getDestinationExchangeOffice()).append(dsnVO.getMailCategoryCode()).append(dsnVO.getMailSubclass()).append(dsnVO.getYear()).toString();
							String selectedMail=new StringBuilder(mail.getOoe()).append(mail.getDoe()).append(mail.getMailCategoryCode()).append(mail.getMailSubclass()).append(mail.getYear()).toString();
							//Modified for bug ICRD-156956 by A-5526
							if((FLAG_YES.equals(mail.getArrivedFlag()))||(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus())) &&  logonAttributes.getAirportCode().equals(mail.getScannedPort())){
								if(selectedDSN.equals(selectedMail)){ //Added as part of BUG ICRD-112270 by A-5526
								invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailarrivedalready"));
								invocationContext.target = TARGET;
								return;
								}
							}else if(!Flight1.equals(Flight2)){
								//Modified for bug ICRD-149216 by A-5526
								if((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus())||
										MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus())||
										MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mail.getMailStatus())) && logonAttributes.getAirportCode().equals(mail.getScannedPort())){
									invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailarrivedalready"));
									invocationContext.target = TARGET;
									return;

								}
							}
						}
					}
				}else{
					log.log(Log.FINE, "despatch is selected");
					//if(dsnVO.getReceivedBags()>0 && dsnVO.getReceivedWeight()>0.0){
					//Added by A-7550
					if(dsnVO.getReceivedBags()>0 && dsnVO.getReceivedWeight().getRoundedSystemValue()>0.0){
						if((dsnVO.getPrevReceivedBags()==dsnVO.getReceivedBags())&&(dsnVO.getPrevReceivedWeight()==dsnVO.getReceivedWeight())){
						invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.despatchalreadydelivered"));
						invocationContext.target = TARGET;
						return;
						}
					}
				}
			}
		}else if(selectedContainers!=null){

    				for(int i=0;i<selectedContainers.length;i++){
    				ContainerDetailsVO containerDetailsVO = containerDetailsVOs.get(Integer.parseInt(selectedContainers[i].split("~")[0]));
				ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>)containerDetailsVO.getDsnVOs();
				for(DSNVO dsnVO:dsnVos){
					if(FLAG_YES.equals(dsnVO.getPltEnableFlag())){
						log.log(Log.FINE, "mailbag is selected");
						Collection<MailbagVO> mailbagVOs= containerDetailsVO.getMailDetails();
						for(MailbagVO mail: mailbagVOs){
							if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){
								String Flight1 = new StringBuilder(FLIGHT).append(mail.getCarrierId()).append(mail.getFlightSequenceNumber()).append(mail.getFlightNumber()).toString();
								String Flight2 = new StringBuilder(FLIGHT).append(mail.getFromCarrierId()).append(mail.getFromFlightSequenceNumber()).append(mail.getFromFightNumber()).toString();
//Modified for bug ICRD-156956 by A-5526
								if((FLAG_YES.equals(mail.getArrivedFlag()))||(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus())) && logonAttributes.getAirportCode().equals(mail.getScannedPort())){
									invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailarrivedalready"));
									invocationContext.target = TARGET;
									return;
								}else if(!Flight1.equals(Flight2)){
									if((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus())||
											MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus())||
											MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mail.getMailStatus())) && logonAttributes.getAirportCode().equals(mail.getScannedPort())){
										invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailarrivedalready"));
										invocationContext.target = TARGET;
										return;
									}
								}
							}
						}
					}
				}
			}
		}else{
			/**
			 * Added by A-4809
			 * Arrival of mailbag/despatch need to preveneted if any of the mailbag/despatch is already arrived.
			 * Individual arrival need to be performed in that case.Generic message will only be thrown
			 */
			if(containerDetailsVOs!=null && containerDetailsVOs.size()>0){

				for(ContainerDetailsVO contVO:containerDetailsVOs){
						ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>)contVO.getDsnVOs();
						if(dsnVos!=null && dsnVos.size()>0){
							for(DSNVO dsnVO:dsnVos){
								if(FLAG_YES.equals(dsnVO.getPltEnableFlag())){
									Collection<MailbagVO> mailbagVOs= contVO.getMailDetails();
									for(MailbagVO mail: mailbagVOs){
										if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){
											String Flight1 = new StringBuilder(FLIGHT).append(mail.getCarrierId()).append(mail.getFlightSequenceNumber()).append(mail.getFlightNumber()).toString();
											String Flight2 = new StringBuilder(FLIGHT).append(mail.getFromCarrierId()).append(mail.getFromFlightSequenceNumber()).append(mail.getFromFightNumber()).toString();
											   //Modified for bug ICRD-156956 by A-5526
											if((FLAG_YES.equals(mail.getArrivedFlag()))||(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus())) && logonAttributes.getAirportCode().equals(mail.getScannedPort())){
												invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailarrivedalready"));
												invocationContext.target = TARGET;
												return;
											}else if(!Flight1.equals(Flight2)){
												if((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mail.getMailStatus())||
														MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus())||
														MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mail.getMailStatus())) && logonAttributes.getAirportCode().equals(mail.getScannedPort())){
													invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailarrivedalready"));
													invocationContext.target = TARGET;
													return;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		// Error thowing for already arrived case..Ends
/*
		if(isContainerSelected){
			ContainerDetailsVO containerDetailsVO = containerDetailsVOs.get(Integer.parseInt(selectedContainers[0].split("~")[0]));
    				ArrayList<MailbagVO> mailDetails =(ArrayList<MailbagVO>)containerDetailsVO.getMailDetails();
    			if(isDSNSelected){
    				for(int i=0;i<selectedContainers.length;i++){
	    				int val=Integer.parseInt(selectedChildContainers[i].split("~")[1]);
    				MailbagVO mailvo = mailDetails.get(val);
    				if(mailvo.getArrivedFlag()!=null && !FLAG_YES.equals(mailvo.getArrivedFlag()) && !"ARR".equals(mailvo.getMailStatus()))
    						mailCount++;
    			}
    			}
    			else{
    				for(MailbagVO mailbagVO : mailDetails){
    				if(mailbagVO.getArrivedFlag()!=null && !FLAG_YES.equals(mailbagVO.getArrivedFlag())&& !"ARR".equals(mailbagVO.getMailStatus()))
    					mailCount++;
    			}
    		}
    	}else{
    		if(selectedChildContainers!=null){
	    		ContainerDetailsVO containerDetailsVO = containerDetailsVOs.get(Integer.parseInt(selectedChildContainers[0].split("~")[0]));
	    		for(int i=0;i<selectedChildContainers.length;i++){
		    		int val=Integer.parseInt(selectedChildContainers[i].split("~")[1]);
				ArrayList<MailbagVO> mails =(ArrayList<MailbagVO>)containerDetailsVO.getMailDetails();
				MailbagVO mailvo = mails.get(val);
				if(mailvo.getArrivedFlag()!=null && !FLAG_YES.equals(mailvo.getArrivedFlag())&& !"ARR".equals(mailvo.getMailStatus()))
						mailCount++;
		    	}
	    	}
    		else
    			for(ContainerDetailsVO containerDetailsVO : containerDetailsVOs){
    				ArrayList<MailbagVO> mails =(ArrayList<MailbagVO>)containerDetailsVO.getMailDetails();
    				for(MailbagVO mail : mails){
    					if(mail.getArrivedFlag()!=null && !FLAG_YES.equals(mail.getArrivedFlag())&& !"ARR".equals(mail.getMailStatus()))
    						mailCount++;
    				}
    			}
    	}*/
/*		if(mailCount==0){
			ErrorVO errorVO=new ErrorVO("mailtracking.defaults.mailarrival.mailarrivedalready");
			errorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
   			errors.add(errorVO);
   			invocationContext.addAllError(errors);
   			invocationContext.target=TARGET;
			return;
		}*/
		int conIdx = 0;
		//if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
		if(sizeOfContainers > 0){
			for(int iter=0;iter<sizeOfContainers;iter++)
			{
				/*for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs)*/
				if(isContainerSelected) {
					//if container selced take tht
					conIdx = Integer.parseInt(selectedContainers[iter]);
				} else {
					//if dsn selcted take idx of tht
					if(isDSNSelected) {
						conIdx =  Integer.parseInt(selectedChildContainers[0].split("~")[0]);
					} else {
						//else take all
						conIdx = iter;
					}
				}
				log.log(Log.FINEST, "selectec container idx ", conIdx);
				ContainerDetailsVO containerDetailsVO=containerDetailsVOs.get(conIdx);//Added by Sreekumar S inorder to select the checked Containers only


				 HashMap<String,DSNVO> dsnMapMailbag = new HashMap<String,DSNVO>();
				 ArrayList<DSNVO> mainDSNVOs = (ArrayList<DSNVO>)containerDetailsVO.getDsnVOs();
				 if(containerDetailsVO.getDsnVOs()==null || containerDetailsVO.getDsnVOs().size()==0){
						containerDetailsVO.setOperationFlag("U");
					}
				 if(!isDSNSelected) { //!= null already done outside
					 sizeOfChildContainers =  containerDetailsVO.getDsnVOs().size();
				 }
				 log.log(Log.FINEST, "dsn sel", isDSNSelected);
							//for selective arrival
				 			int dsnIdx = 0;
							for(int dsnIter = 0; dsnIter < sizeOfChildContainers ; dsnIter++) {
							 	if(isDSNSelected) {
							 		dsnIdx = Integer.parseInt(selectedChildContainers[dsnIter].split("~")[1]);
							 	} else {
							 		dsnIdx = dsnIter;
							 	}
							 	log.log(Log.FINEST, "dsn selected first",
										dsnIdx);
								DSNVO dsnVO = mainDSNVOs.get(dsnIdx);
								 String dsnpk = dsnVO.getOriginExchangeOffice()
						           +dsnVO.getDestinationExchangeOffice()
						           +dsnVO.getMailCategoryCode()
						           +dsnVO.getMailSubclass()
						           +dsnVO.getDsn()
						           +dsnVO.getYear();
								 dsnMapMailbag.put(dsnpk,dsnVO);
						}

//					}

					Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
					 if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
						for(DespatchDetailsVO despatchVO:despatchDetailsVOs){

							String despatchpk = despatchVO.getOriginOfficeOfExchange()
							           + despatchVO.getDestinationOfficeOfExchange()
									   + despatchVO.getMailCategoryCode()
							           + despatchVO.getMailSubclass()
							           + despatchVO.getDsn()
							           + despatchVO.getYear();

								DSNVO dsnVO = dsnMapMailbag.get(despatchpk);
//								String dsnpk = dsnVO.getOriginExchangeOffice()
//						           		+ dsnVO.getDestinationExchangeOffice()
//						           		+ dsnVO.getMailCategoryCode()
//						           		+ dsnVO.getMailSubclass()
//						           		+ dsnVO.getDsn()
//						           		+ dsnVO.getYear();

//								    if(dsnpk.equals(despatchpk)){

									if(dsnVO != null) {
										if(despatchVO.getReceivedBags() == 0){
											if(!"I".equals(despatchVO.getOperationalFlag())){
												despatchVO.setOperationalFlag("U");
									    	}
											if(!"I".equals(dsnVO.getOperationFlag())){
									    		dsnVO.setOperationFlag("U");
									    	}
											if(!"I".equals(containerDetailsVO.getOperationFlag())){
												containerDetailsVO.setOperationFlag("U");
									    	}
											dsnVO.setReceivedBags(dsnVO.getReceivedBags()
													 + (despatchVO.getAcceptedBags() - despatchVO.getReceivedBags()));
											/*dsnVO.setReceivedWeight(dsnVO.getReceivedWeight()
													 + (despatchVO.getAcceptedWeight() - despatchVO.getReceivedWeight()));*/
											Measure despWt;
											try {
												despWt = Measure.subtractMeasureValues(despatchVO.getAcceptedWeight(),  despatchVO.getReceivedWeight());
												try {
													dsnVO.setReceivedWeight(Measure.addMeasureValues(dsnVO.getReceivedWeight(), despWt));
												} catch (UnitException e) {
													// TODO Auto-generated catch block
													log.log(Log.SEVERE,"UnitException", e.getMessage());
												}//added by A-7371
											} catch (UnitException e) {
												// TODO Auto-generated catch block
												log.log(Log.SEVERE,"UnitException", e.getMessage());
											}
											
											despatchVO.setReceivedBags(despatchVO.getAcceptedBags());
											despatchVO.setReceivedWeight(despatchVO.getAcceptedWeight());
											despatchVO.setReceivedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
										}
										dsnMapMailbag.put(despatchpk,dsnVO);
									}
								}
						}
				 Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
				 if(mailbagVOs != null && mailbagVOs.size() > 0){
					for(MailbagVO mailbagVO:mailbagVOs){

						String mailpk = mailbagVO.getOoe()
						           + mailbagVO.getDoe()
								   + mailbagVO.getMailCategoryCode()
						           + mailbagVO.getMailSubclass()
						           + mailbagVO.getDespatchSerialNumber()
						           + mailbagVO.getYear();

							DSNVO dsnVO = dsnMapMailbag.get(mailpk);
//							String dsnpk = dsnVO.getOriginExchangeOffice()
//					           		+ dsnVO.getDestinationExchangeOffice()
//					           		+ dsnVO.getMailCategoryCode()
//					           		+ dsnVO.getMailSubclass()
//					           		+ dsnVO.getDsn()
//					           		+ dsnVO.getYear();
//
//							    if(dsnpk.equals(mailpk)){
								if(dsnVO != null) {

									if(!"Y".equals(mailbagVO.getArrivedFlag())){
										if(!"I".equals(mailbagVO.getOperationalFlag())){
											mailbagVO.setOperationalFlag("U");
								    	}
										if(!"I".equals(dsnVO.getOperationFlag())){
								    		dsnVO.setOperationFlag("U");
								    	}
										if(!"I".equals(containerDetailsVO.getOperationFlag())){
											containerDetailsVO.setOperationFlag("U");
								    	}
										//A-8061 added for ICRD-263340 
										if(embargoMailMap.size() > 0 && embargoMailMap.containsKey(mailbagVO.getMailbagId())){
											mailbagVO.setArrivedFlag("N");
										}
										else{
										mailbagVO.setArrivedFlag("Y");
										}

										mailbagVO.setScannedPort(logonAttributes.getAirportCode());
										mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
										mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
										mailbagVO.setFlightDate(mailArrivalVO.getArrivalDate());
										mailbagVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);//Added for ICRD-156218
										mailbagVO.setMailbagDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
										dsnVO.setReceivedBags(dsnVO.getReceivedBags() + 1);
										//added by A-7371
										try {
											dsnVO.setReceivedWeight(Measure.addMeasureValues(dsnVO.getReceivedWeight(), mailbagVO.getWeight()));
										} catch (UnitException e) {
											// TODO Auto-generated catch block
											log.log(Log.SEVERE,"UnitException", e.getMessage());
										}
										
									}
									dsnMapMailbag.put(mailpk,dsnVO);
								}
							}
					}


				Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
				for(String key:dsnMapMailbag.keySet()){
					DSNVO dsnVO = dsnMapMailbag.get(key);
					newDSNVOs.add(dsnVO);
				}

				ArrayList<DSNVO> oldDSNVOs = (ArrayList<DSNVO>) containerDetailsVO.getDsnVOs();
				int accBags = 0;
				double accWgt = 0;
				int rcvedBags = 0;
				double rcvedWgt = 0;
				if(newDSNVOs != null && newDSNVOs.size() > 0){
					for(DSNVO dsnVO1:newDSNVOs){
						String outerpk = dsnVO1.getOriginExchangeOffice()
						   +dsnVO1.getDestinationExchangeOffice()
						   +dsnVO1.getMailCategoryCode()
						   +dsnVO1.getMailSubclass()
				           +dsnVO1.getDsn()+dsnVO1.getYear();
						int flag = 0;

//						if(oldDSNVOs != null && oldDSNVOs.size() > 0){

						int dsnIdx2 = 0;
							for(int dsnIter=0;dsnIter<sizeOfChildContainers;dsnIter++)
							/*for(DSNVO dsnVO2:oldDSNVOs)*/{

								if(isDSNSelected) {
									dsnIdx2 = Integer.parseInt(selectedChildContainers[dsnIter].substring(2));
								} else {
									dsnIdx2 = dsnIter;
								}

								log.log(Log.FINEST, "dsn selected 2nd time",
										dsnIdx2);
								DSNVO dsnVO2 = oldDSNVOs.get(dsnIdx2); // Added by Sreekumar inorder to choose the checked DSNs only
								String innerpk = dsnVO2.getOriginExchangeOffice()
								   +dsnVO2.getDestinationExchangeOffice()
								   +dsnVO2.getMailCategoryCode()
								   +dsnVO2.getMailSubclass()
						           +dsnVO2.getDsn()+dsnVO2.getYear();
								if(outerpk.equals(innerpk)){
									if(!"I".equals(dsnVO2.getOperationFlag())){
										dsnVO1.setPrevBagCount(dsnVO2.getPrevBagCount());
										dsnVO1.setPrevBagWeight(dsnVO2.getPrevBagWeight());
                                        dsnVO1.setPrevReceivedBags(dsnVO2.getPrevReceivedBags());
                                        dsnVO1.setPrevReceivedWeight(dsnVO2.getPrevReceivedWeight());
                                        dsnVO1.setPrevDeliveredBags(dsnVO2.getPrevDeliveredBags());
                                        dsnVO1.setPrevDeliveredWeight(dsnVO2.getPrevDeliveredWeight());
									}
									flag = 1;
								}
							}
//						}
						if(flag == 1){
							flag = 0;
						}
//						accBags = accBags + dsnVO1.getBags();
//						accWgt = accWgt + dsnVO1.getWeight();

						//Commented for  AirNZ BUG : 43845
//						rcvedBags = rcvedBags + dsnVO1.getReceivedBags();
//						rcvedWgt = rcvedWgt + dsnVO1.getReceivedWeight();
					}
				}
				/*
				 * Solution For AirNZ BUG : 43845
				 * Start
				 */
				if(oldDSNVOs != null && oldDSNVOs.size() > 0){
					for(DSNVO dsnVO : oldDSNVOs){
						if(dsnVO.getReceivedBags() > 0) {
							rcvedBags = rcvedBags + dsnVO.getReceivedBags();
							//rcvedWgt = rcvedWgt + dsnVO.getReceivedWeight();
							rcvedWgt = rcvedWgt + dsnVO.getReceivedWeight().getRoundedSystemValue();
						}
					}
				}
				//END AirNZ BUG : 43845

//				containerDetailsVO.setTotalBags(accBags);
//				containerDetailsVO.setTotalWeight(accWgt);
				containerDetailsVO.setReceivedBags(rcvedBags);
				//containerDetailsVO.setReceivedWeight(rcvedWgt);
				Measure wgt=new Measure(UnitConstants.MAIL_WGT, rcvedWgt);//Added by A-7550
				containerDetailsVO.setReceivedWeight(wgt);
//				containerDetailsVO.setDsnVOs(newDSNVOs);
			}
    	 }

		if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
    			containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
    		}
    	}

		mailArrivalVO.setContainerDetails(containerDetailsVOs);
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		mailArrivalVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);//Added for ICRD-156218
		mailArrivalVO.setMailDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
		log.log(Log.FINE, "Going To Save ...in command...DeliverMail...",
				mailArrivalVO);
		log.log(Log.FINE, "Going To Save ...in command", mailArrivalVO);


		/*
		 * Construct lock vos for implicit locking
		 */
		Collection<LockVO> locks = prepareLocksForSave(mailArrivalVO);

		if (locks == null || locks.size() == 0) {
			locks = null;
		}
		  try {
		    new MailTrackingDefaultsDelegate().saveArrivalDetails(mailArrivalVO,locks);
          }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	  }
    	  if (errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
    		mailArrivalSession.setMailArrivalVO(mailArrivalVO);
    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = TARGET;
    		return;
    	  }

    	MailArrivalVO mailArriveVO = new MailArrivalVO();
    	//Added for ICRD-134007 starts
    	LocalDate arrivalDate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
    	arrivalDate.setDate(mailArrivalForm.getArrivalDate());
    	mailArriveVO.setFlightCarrierCode(mailArrivalForm.getFlightCarrierCode());
    	mailArriveVO.setFlightNumber(mailArrivalForm.getFlightNumber());
    	mailArriveVO.setArrivalDate(arrivalDate);
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

    	log.exiting("ArriveMailCommand","execute");

    }
    /*
     * Added by Indu
     */
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
