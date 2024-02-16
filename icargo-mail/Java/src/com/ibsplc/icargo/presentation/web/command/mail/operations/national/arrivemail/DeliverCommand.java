/* DeliverCommand.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.arrivemail;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ArriveDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ArriveDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-4810
 */
public class DeliverCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING");

	/**
	 * TARGET
	 */
	private static final String TARGET = "success";
	private static final String FAIL ="failure";

	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.mailarrival";	
	private static final String SAVE_SUCCESS = 
		"mailtracking.defaults.national.msg.info.savesuccess";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("Deliver","execute");

		ArriveDispatchForm arriveDispatchForm = 
			(ArriveDispatchForm)invocationContext.screenModel;
		ArriveDispatchSession mailArrivalSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ContainerDetailsVO containerDetailVO=mailArrivalSession.getContainerDetailsVO();
		log.log(Log.FINE, "<<<<<<<<---containerDetailVO----->",
				containerDetailVO);
		log.log(Log.FINE, "<<<<<<<<---mailArrivalVO----->", mailArrivalVO);
		String companyCode = logonAttributes.getCompanyCode();
		String airport = logonAttributes.getAirportCode();
		Integer errorFlag = 0;


		Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalVO.getContainerDetails();
		ArrayList<ContainerDetailsVO> collectionContainerDetailsVOs = (ArrayList<ContainerDetailsVO>) containerDetailsVOs;
		String selectedRows = arriveDispatchForm.getSelectedRow();
		String[] singleRows = selectedRows.split(",");
		 String dsnIndex = "";
         String contIndex = "";
		   for(String row : singleRows){
			   Collection<String> delvcoll = new ArrayList<String>();
			   String[] containerDsns = row.split("~");
			   contIndex = containerDsns[0];
			   dsnIndex = containerDsns[1];
			   if(collectionContainerDetailsVOs != null && collectionContainerDetailsVOs.size() > 0){
				   ContainerDetailsVO containerDtlsVO = collectionContainerDetailsVOs.get(Integer.parseInt(contIndex));
			  
				   containerDtlsVO.setOperationFlag("U");
					containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
					 ArrayList<DSNVO> dsnv = (ArrayList<DSNVO>)containerDtlsVO.getDsnVOs();
					 
					   int i =Integer.parseInt(dsnIndex);
					   int dlvdBags = 0;
						double dlvdWeight = 0;
						//int size = dsnv.size();
						DSNVO dsnv1 = dsnv.get(i);
						delvcoll.add(dsnv1.getDestinationExchangeOffice());
						errorFlag=validateDOEs(delvcoll,companyCode,airport);

						if(errorFlag == 1){
							log.log(Log.INFO,"<<----DOE of Mailbag/Dispatches is NOT SAME as that of the current Airport --->>");
							invocationContext.addError(new ErrorVO("mailtracking.defaults.national.msg.err.cannotdeliveratthisport"));

							arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
							invocationContext.target = FAIL;
							return;
						} 
						//modified IF loop by a-5133 as part of the CR ICRD-19090 
						//if((dsnv1.getReceivedBags()!= 0 && dsnv1.getReceivedWeight()!= 0) && 
						try {
							if((dsnv1.getReceivedBags()!= 0 && dsnv1.getReceivedWeight().getRoundedSystemValue()!= 0) && 
									(dsnv1.getBags() >= dsnv1.getReceivedBags()) && 
									//dsnv1.getWeight() >= dsnv1.getReceivedWeight())
									Measure.compareTo(dsnv1.getWeight(), dsnv1.getReceivedWeight())>=0)
							{
								if(dsnv1.getReceivedBags() >= dsnv1.getDeliveredBags())
								{
									dlvdBags = (dsnv1.getReceivedBags());
									//dlvdWeight = dsnv1.getReceivedWeight();
									dlvdWeight = dsnv1.getReceivedWeight().getRoundedSystemValue();
									dsnv1.setDeliveredBags(dlvdBags);
									//dsnv1.setDeliveredWeight(dlvdWeight);
									dsnv1.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,dlvdWeight));//added by A-7371
									dsnv1.setOperationFlag("U");
									dsnv1.setRemarks(dsnv1.getRemarks());


								}
								else
								{
									invocationContext.addError(new ErrorVO("mailtracking.defaults.national.piecesgreater"));

									arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
									invocationContext.target = FAIL;
									return;

								}



							}

							else
							{
								invocationContext.addError(new ErrorVO("mailtracking.defaults.national.piecesnotarrived"));

								arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
								invocationContext.target = FAIL;
								return;

							}
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							log.log(Log.SEVERE,"UnitException",e.getMessage());
						}
						
						mailArrivalVO.setContainerDetails(containerDetailsVOs);
						arriveDispatchForm.setStatus(null);
			   }
		   }
		  // The code is commented by a-4810 for icrd -20140
		   //The code is modified to accomodate delivery of despatches form more than one container
		/*if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){		
			for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
				containerDtlsVO.setOperationFlag("U");
				containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
				ArrayList<DSNVO> dsnv = (ArrayList<DSNVO>)containerDtlsVO.getDsnVOs();


				String selectedRows = arriveDispatchForm.getSelectedRow();

				String[] singleRows = selectedRows.split(",");
				for(int i = 0; i < singleRows.length; i++){

					DSNVO dsnv1 = dsnv.get(Integer.parseInt(singleRows[i]));


					delvcoll.add(dsnv1.getDestinationExchangeOffice());
					errorFlag=validateDOEs(delvcoll,companyCode,airport);

					if(errorFlag == 1){
						log.log(Log.INFO,"<<----DOE of Mailbag/Dispatches is NOT SAME as that of the current Airport --->>");
						invocationContext.addError(new ErrorVO("mailtracking.defaults.national.msg.err.cannotdeliveratthisport"));

						arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
						invocationContext.target = FAIL;
						return;
					} 



					int dlvdBags = 0;
					double dlvdWeight = 0;
					int size = dsnv.size();
					if(dsnv1.getReceivedBags()!= 0 && dsnv1.getReceivedWeight()!= 0)
					{
						if(dsnv1.getReceivedBags()>dsnv1.getDeliveredBags())
						{
							dlvdBags = (dsnv1.getReceivedBags());
							dlvdWeight = dsnv1.getReceivedWeight();
							dsnv1.setDeliveredBags(dlvdBags);
							dsnv1.setDeliveredWeight(dlvdWeight);
							dsnv1.setOperationFlag("U");
							dsnv1.setRemarks(dsnv1.getRemarks());


						}
						else
						{
							invocationContext.addError(new ErrorVO("mailtracking.defaults.national.piecesgreater"));

							arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
							invocationContext.target = FAIL;
							return;

						}



					}

					else
					{
						invocationContext.addError(new ErrorVO("mailtracking.defaults.national.piecesnotarrived"));

						arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
						invocationContext.target = FAIL;
						return;

					}
				}

				mailArrivalVO.setContainerDetails(containerDetailsVOs);

				arriveDispatchForm.setStatus(null);
			}
		}*/


		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		log.log(Log.FINE, "Going To Save ...in command", mailArrivalVO);
		/*
		 * Construct lock vos for implicit locking
		 */
		Collection<LockVO> locks = prepareLocksForSave(mailArrivalVO);
		log.log(Log.FINE, "LockVO for implicit check", locks);
		if (locks == null || locks.size() == 0) {
			locks = null;
		}
		populateDespatchDetails(mailArrivalVO);
		try {
			new MailTrackingDefaultsDelegate().saveArrivalDetails(mailArrivalVO,locks);
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			mailArrivalSession.setMailArrivalVO(mailArrivalVO);
			arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = FAIL;
			return;
		}

		MailArrivalVO mailArriveVO = new MailArrivalVO(); 
		mailArrivalSession.setMailArrivalVO(mailArriveVO);
		arriveDispatchForm.setOperationalStatus("");
		arriveDispatchForm.setInitialFocus(FLAG_YES);
		arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.addError(new ErrorVO(SAVE_SUCCESS));
		arriveDispatchForm.setArrivalPort(logonAttributes.getAirportCode());
		invocationContext.target = TARGET;
		log.exiting("DeliverCommand","execute");
	    	
	    }
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


	    
	    
	private Integer validateDOEs(Collection<String> delvcoll,String companyCode,String airport){
		Collection<ArrayList<String>> groupedOECityArpCodes = null;
		Integer errorFlag = 1;
		if(delvcoll != null && delvcoll.size()!=0){


			try{
				groupedOECityArpCodes=  new MailTrackingDefaultsDelegate().findCityAndAirportForOE(companyCode, delvcoll);

			}
			catch (BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> err = handleDelegateException(businessDelegateException);
				log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
			}

			if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0){
				for(String doe : delvcoll){
					for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
						if(cityAndArpForOE.size() == 3 && 
								doe.equals(cityAndArpForOE.get(0)) && 
								airport.equals(cityAndArpForOE.get(2))){
							errorFlag = 0;
							break;
						}			
					}
					if(errorFlag == 0) {
						break;
					}
				}
			}	

		}
		return errorFlag;
	}   

	 private void  populateDespatchDetails(MailArrivalVO mailArrivalVO){
		   if(mailArrivalVO != null){
			   LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			   Collection<DespatchDetailsVO> despatchDetailsVOs = null;
			   if(mailArrivalVO.getContainerDetails() != null && mailArrivalVO.getContainerDetails().size() >0){
				   for(ContainerDetailsVO containerDetailsVO : mailArrivalVO.getContainerDetails()){
					   if(containerDetailsVO.getDsnVOs() != null && containerDetailsVO.getDsnVOs().size() >0){
						   despatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
						   for(DSNVO dsnvo : containerDetailsVO.getDsnVOs()){
							   despatchDetailsVOs.add(constructDespatchDetailsFromDSNVO(dsnvo, logonAttributes,containerDetailsVO));
						   }
						   if(despatchDetailsVOs !=null && despatchDetailsVOs.size() >0){
							   containerDetailsVO.setDesptachDetailsVOs(despatchDetailsVOs);

						   }

					   }
				   }

			   }
		   }

	   }

	 /**
		 * 
		 * @param dsnvo
		 * @param logonAttributes
		 * @return
		 */
		private DespatchDetailsVO constructDespatchDetailsFromDSNVO(DSNVO dsnvo,LogonAttributes logonAttributes,ContainerDetailsVO containerDetailsVO){
			DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
			despatchDetailsVO.setAcceptedDate(dsnvo.getAcceptedDate());
			despatchDetailsVO.setAirportCode(logonAttributes.getAirportCode());

			despatchDetailsVO.setCarrierCode(dsnvo.getCarrierCode());
			despatchDetailsVO.setCarrierId(containerDetailsVO.getCarrierId());
			despatchDetailsVO.setCompanyCode(containerDetailsVO.getCompanyCode());

			despatchDetailsVO.setConsignmentDate(dsnvo.getConsignmentDate());
			despatchDetailsVO.setConsignmentNumber(dsnvo.getCsgDocNum());
			despatchDetailsVO.setConsignmentSequenceNumber(dsnvo.getCsgSeqNum());

			despatchDetailsVO.setContainerNumber("BULK");
			despatchDetailsVO.setContainerType("B");

			despatchDetailsVO.setDeliveredBags(dsnvo.getDeliveredBags());
			despatchDetailsVO.setDeliveredWeight(dsnvo.getDeliveredWeight());
			despatchDetailsVO.setDestinationOfficeOfExchange(dsnvo.getDestinationExchangeOffice());
			despatchDetailsVO.setDestination(dsnvo.getDestination());
			despatchDetailsVO.setDsn(dsnvo.getDsn());

			despatchDetailsVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			despatchDetailsVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
			despatchDetailsVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());

			despatchDetailsVO.setMailCategoryCode(dsnvo.getMailCategoryCode());
			despatchDetailsVO.setMailClass(dsnvo.getMailClass());
			despatchDetailsVO.setMailSubclass(dsnvo.getMailSubclass());

			despatchDetailsVO.setOperationalFlag(dsnvo.getOperationFlag());
			despatchDetailsVO.setOperationType("I");
			despatchDetailsVO.setOriginOfficeOfExchange(dsnvo.getOriginExchangeOffice());
			despatchDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());

			despatchDetailsVO.setPaCode(dsnvo.getPaCode());
			despatchDetailsVO.setPou(dsnvo.getPou());
			despatchDetailsVO.setPrevDeliveredBags(dsnvo.getPrevDeliveredBags());
			despatchDetailsVO.setPrevDeliveredWeight(dsnvo.getPrevDeliveredWeight());
			despatchDetailsVO.setPrevReceivedBags(dsnvo.getPrevReceivedBags());
			despatchDetailsVO.setPrevReceivedWeight(dsnvo.getPrevReceivedWeight());
			despatchDetailsVO.setPrevStatedBags(dsnvo.getPrevStatedBags());
			despatchDetailsVO.setPrevStatedWeight(dsnvo.getPrevStatedWeight());

			despatchDetailsVO.setReceivedBags(dsnvo.getReceivedBags());
			despatchDetailsVO.setReceivedWeight(dsnvo.getReceivedWeight());

			despatchDetailsVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
			despatchDetailsVO.setStatedBags(dsnvo.getStatedBags());
			despatchDetailsVO.setStatedWeight(dsnvo.getStatedWeight());
			despatchDetailsVO.setAcceptedDate(dsnvo.getAcceptedDate());
			despatchDetailsVO.setReceivedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
			despatchDetailsVO.setYear(dsnvo.getYear());
			//Added for bug icrd-18422 by a-4810
			despatchDetailsVO.setRemarks(dsnvo.getRemarks());
			return despatchDetailsVO;

		}


}

