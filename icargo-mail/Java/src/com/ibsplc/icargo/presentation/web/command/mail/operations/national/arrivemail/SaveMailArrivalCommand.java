/* SaveMailArrivalCommand.java Created on Feb 2, 2012
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

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
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
public class SaveMailArrivalCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "save_success";
	   private static final String FAIL = "failure";
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

		   log.entering("SaveMailAcceptanceCommand","execute");
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
		Collection<String> delvcoll = new ArrayList<String>();
		   Integer errorFlag = 0;
		   String companyCode = logonAttributes.getCompanyCode();
		   String airport = logonAttributes.getAirportCode();
		   Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalVO.getContainerDetails();
		   ArrayList<ContainerDetailsVO> collectionContainerDetailsVOs = (ArrayList<ContainerDetailsVO>) containerDetailsVOs;
		   DespatchDetailsVO despatchDetailsVO = null;
		   //
		   String selectedRows = arriveDispatchForm.getSelectedRow();
		   String dsnRowIndex = "";
           String contIndex = "";
           String dsnIndex = "";
           String rowIndex ="";
		   String[] singleRows = selectedRows.split(",");
		   for(String row : singleRows){
			   //int i=row.indexOf("~");
			   String[] containerDsns = row.split("~");
			   contIndex = containerDsns[0];
			   dsnRowIndex = containerDsns[1];
			   String[] dsnSplitInd =dsnRowIndex.split("-");
			   dsnIndex = dsnSplitInd[0];
			   rowIndex= dsnSplitInd[1];
			   
			   
			   
			   //contIndex = row.substring(i+1);
			   //dsnIndex = row.substring(0, i-1);
			   if(collectionContainerDetailsVOs != null && collectionContainerDetailsVOs.size() > 0){
				   ContainerDetailsVO containerDtlsVO = collectionContainerDetailsVOs.get(Integer.parseInt(contIndex));
				   containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
				   containerDtlsVO.setOperationFlag("U");
				   ArrayList<DSNVO> dsnv = (ArrayList<DSNVO>)containerDtlsVO.getDsnVOs();
				   int i =Integer.parseInt(dsnIndex);
				   int j =Integer.parseInt(rowIndex);
				   DSNVO dsnv1 = dsnv.get(i);
				   ArrayList<DespatchDetailsVO> despatchDetailsVOs = (ArrayList<DespatchDetailsVO>)containerDtlsVO.getDesptachDetailsVOs();
			   
				   
				 //  if((Integer.parseInt(arriveDispatchForm.getReceivedBags()[j])) <= (dsnv1.getBags())&&((Double.parseDouble(arriveDispatchForm.getReceivedWt()[j])) <= (dsnv1.getWeight())) )
				   try {
					if((Integer.parseInt(arriveDispatchForm.getReceivedBags()[j])) <= (dsnv1.getBags())&&
							   Measure.compareTo(arriveDispatchForm.getReceivedWtMeasure()[j], dsnv1.getWeight())<=0)
					   {		
						   dsnv1.setReceivedBags(Integer.parseInt(arriveDispatchForm.getReceivedBags()[j]));
						 //  dsnv1.setReceivedWeight(Double.parseDouble(arriveDispatchForm.getReceivedWt()[j]));
						   dsnv1.setReceivedWeight(arriveDispatchForm.getReceivedWtMeasure()[j]);
					   }
					   else
					   {
						   dsnv1.setReceivedBags(Integer.parseInt(arriveDispatchForm.getReceivedBags()[j]));
						   //dsnv1.setReceivedWeight(Double.parseDouble(arriveDispatchForm.getReceivedWt()[j]));
						   dsnv1.setReceivedWeight(arriveDispatchForm.getReceivedWtMeasure()[j]);
						   //errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.recievedpiecesgreater"));
					   }
				} catch (NumberFormatException | UnitException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "UnitException",e.getMessage());
				}
				  
				   
				//added by a-5133 as part of the CR ICRD-19090 starts
					  if(dsnv1.getTransferredPieces() > (Integer.parseInt(arriveDispatchForm.getReceivedBags()[j])))
					   {
							   errors.add(new ErrorVO("mailtracking.defaults.national.msg.err.CannotUpdateTransferDetails")); 
					   }
					   
					   if(dsnv1.getDeliveredBags() > (Integer.parseInt(arriveDispatchForm.getReceivedBags()[j])))
					   {   
						   errors.add(new ErrorVO("mailtracking.defaults.national.msg.err.CannotUpdateDeliveryDetails"));
					   }
					   //Added by Deepthi as a part of BUG ICRD-25019 
					   try {
						if(Integer.parseInt(arriveDispatchForm.getDeliveredBags()[j]) > (dsnv1.getBags())|| 
								  // (Double.parseDouble(arriveDispatchForm.getDeliveredWt()[j])) > dsnv1.getWeight())
								   Measure.compareTo(arriveDispatchForm.getDeliveredWtMeasure()[j], dsnv1.getWeight())>0){
							   errors.add(new ErrorVO("mailtracking.defaults.national.msg.err.deliveredpiecesgreater"));
						   }
					} catch (NumberFormatException | UnitException e) {
						// TODO Auto-generated catch block
						log.log(Log.SEVERE, "UnitException",e.getMessage());
					}
					   
				 
					  //added by a-5133 as part of the CR ICRD-19090 ends
				   
				   //if((Integer.parseInt(arriveDispatchForm.getDeliveredBags()[j])) <= (dsnv1.getReceivedBags())&&((Double.parseDouble(arriveDispatchForm.getDeliveredWt()[j])) <= (dsnv1.getReceivedWeight())) )
					   try {
						if((Integer.parseInt(arriveDispatchForm.getDeliveredBags()[j])) <= (dsnv1.getReceivedBags())&&
								   Measure.compareTo(arriveDispatchForm.getDeliveredWtMeasure()[j], dsnv1.getReceivedWeight())<=0 )
 {
						if ((Integer.parseInt(arriveDispatchForm
								.getDeliveredBags()[j]) != 0)
								&& (Double.parseDouble(arriveDispatchForm
										.getDeliveredWt()[j]) != 0)) {
							delvcoll.add(dsnv1.getDestinationExchangeOffice());
							errorFlag = validateDOEs(delvcoll, companyCode,
									airport);
							if (errorFlag == 1) {
								errors.add(new ErrorVO(
										"mailtracking.defaults.national.msg.err.cannotdeliveratthisport"));

							}
						}

						dsnv1.setDeliveredBags(Integer
								.parseInt(arriveDispatchForm.getDeliveredBags()[j]));
						// dsnv1.setDeliveredWeight(Double.parseDouble(arriveDispatchForm.getDeliveredWt()[j]));
						dsnv1.setDeliveredWeight(arriveDispatchForm
								.getDeliveredWtMeasure()[j]);// added by A-7371
					} else {
						errors.add(new ErrorVO(
								"mailtracking.defaults.national.reassign.error.deliveredpiecesgreater"));
					}
					} catch (NumberFormatException | UnitException e) {
						// TODO Auto-generated catch block
						log.log(Log.SEVERE, "UnitException",e.getMessage());
					}
				   
				   despatchDetailsVO = despatchDetailsVOs.get(i);
				   dsnv1.setRemarks(arriveDispatchForm.getRemarks()[j]);
				   dsnv1.setOperationFlag("U");
				   //counter = counter+1;

				   if(errors != null && errors.size() >0){
					   invocationContext.addAllError(errors);
					   invocationContext.target = FAIL;
					   return;
				   }
				   mailArrivalVO.setContainerDetails(containerDetailsVOs);	
			   }
			  
			   
			   
		   }
		   // The code is commented by a-4810 for icrd -20140
		   //The code is modified to accomodate delivery/arrival of despatches form more than one container
		   //
		   
		/*   if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
			   for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
				   containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
				   containerDtlsVO.setOperationFlag("U");


				   ArrayList<DSNVO> dsnv = (ArrayList<DSNVO>)containerDtlsVO.getDsnVOs();
				   ArrayList<DespatchDetailsVO> despatchDetailsVOs = (ArrayList<DespatchDetailsVO>)containerDtlsVO.getDesptachDetailsVOs();
				   
				   String selectedRows = arriveDispatchForm.getSelectedRow();

				   String[] singleRows = selectedRows.split(",");
				   int counter = 0;
				   int i =0;
				//   for(int j = 0; i < arriveDispatchForm.getSelectContainer().length; i++){
				   //Modified as part of bug-fix icrd-12687 by A-4810
				   for(String row : singleRows){
					   i = Integer.parseInt(row);
					   DSNVO dsnv1 = dsnv.get(i);



					   if((Integer.parseInt(arriveDispatchForm.getReceivedBags()[i])) <= (dsnv1.getBags())&&((Double.parseDouble(arriveDispatchForm.getReceivedWt()[i])) <= (dsnv1.getWeight())) )
					   {		
						   dsnv1.setReceivedBags(Integer.parseInt(arriveDispatchForm.getReceivedBags()[i]));
						   dsnv1.setReceivedWeight(Double.parseDouble(arriveDispatchForm.getReceivedWt()[i]));
					   }
					   else
					   {
						   errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.recievedpiecesgreater"));
					   }
					   if((Integer.parseInt(arriveDispatchForm.getDeliveredBags()[i])) <= (dsnv1.getReceivedBags())&&((Double.parseDouble(arriveDispatchForm.getDeliveredWt()[i])) <= (dsnv1.getReceivedWeight())) )
					   {	if((Integer.parseInt(arriveDispatchForm.getDeliveredBags()[i])!= 0) && (Double.parseDouble(arriveDispatchForm.getDeliveredWt()[i]) != 0))
					   {  
						   delvcoll.add(dsnv1.getDestinationExchangeOffice());
						   errorFlag=validateDOEs(delvcoll,companyCode,airport);
						   if(errorFlag == 1){
							   errors.add(new ErrorVO("mailtracking.defaults.national.msg.err.cannotdeliveratthisport"));

						   } 
					   }

					   dsnv1.setDeliveredBags(Integer.parseInt(arriveDispatchForm.getDeliveredBags()[i]));
					   dsnv1.setDeliveredWeight(Double.parseDouble(arriveDispatchForm.getDeliveredWt()[i]));
					   }
					   else
					   {
						   errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.deliveredpiecesgreater"));
					   }
					   
					   despatchDetailsVO = despatchDetailsVOs.get(i);
					   dsnv1.setRemarks(arriveDispatchForm.getRemarks()[i]);
					   dsnv1.setOperationFlag("U");
					   counter = counter+1;

					   if(errors != null && errors.size() >0){
						   invocationContext.addAllError(errors);
						   invocationContext.target = FAIL;
						   return;
					   }

				   }
			  // }

				   mailArrivalVO.setContainerDetails(containerDetailsVOs);	


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
		   updateContainerDetailsForArrival(mailArrivalVO);
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
		   FlightValidationVO flightValidationVO = new FlightValidationVO();
		   arriveDispatchForm.setInitialFocus(FLAG_YES);
		   arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		   invocationContext.addError(new ErrorVO(SAVE_SUCCESS));
		   arriveDispatchForm.setArrivalPort(logonAttributes.getAirportCode());
		   invocationContext.target = TARGET;
		   log.exiting("SaveMailAcceptanceCommand","execute");

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
	    
	   /**
	    * 
	    * @param mailArrivalVO
	    */
	   private void updateContainerDetailsForArrival(MailArrivalVO mailArrivalVO){
		   if(mailArrivalVO != null){
			   if(mailArrivalVO.getContainerDetails() != null && mailArrivalVO.getContainerDetails().size() >0){
				   for(ContainerDetailsVO containerDetailsVO : mailArrivalVO.getContainerDetails()){
					   int receivedPieces = 0;
					   double receivedWeight = 0;
					   if(containerDetailsVO.getDsnVOs()!= null && containerDetailsVO.getDsnVOs().size() >0){
						   for(DSNVO dsnvo : containerDetailsVO.getDsnVOs()){
							   receivedPieces = receivedPieces + dsnvo.getReceivedBags();
							   //receivedWeight = receivedWeight+ dsnvo.getReceivedWeight();
							   receivedWeight = receivedWeight+ dsnvo.getReceivedWeight().getRoundedSystemValue();//added by A-7371
						   }
						   containerDetailsVO.setReceivedBags(receivedPieces);
						  // containerDetailsVO.setReceivedWeight(receivedWeight);
						   containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,receivedWeight));//added by A-7371

					   }

				   }
			   }

		   }

	   }
	   /**
	    * 
	    * @param mailArrivalVO
	    */
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
	    	despatchDetailsVO.setRemarks(dsnvo.getRemarks());
	    	return despatchDetailsVO;
	    	
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


}


