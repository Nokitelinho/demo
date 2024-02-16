
/*
 * ContainerChangeOkCommand.java Created on Jul 1 2016
 *
 * Copyright 2016 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.containerchange;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
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
public class ContainerChangeOkCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
   private static final String CON = "CON";
   private static final String NOT = "NOT";
   private static final String ERROR_TRANSFERRED_OR_DELIVERED = "mailtracking.defaults.err.mailAlreadyTransferedOrDelivered";
   private static final String CONTAINER_TRANSFERRED_OR_DELIVERED = "mailtracking.defaults.changeflight.containertransferredordelivered";




	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ContainerChangeOkCommand","execute");

    	MailArrivalForm mailArrivalForm =
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		FlightValidationVO flightValidationVO=mailArrivalSession.getChangeFlightValidationVO();
		Collection<MailArrivalVO> mailArrivalVOs=new ArrayList<MailArrivalVO>();
		ArrayList <ContainerDetailsVO> containerDetailsVOs = (ArrayList<ContainerDetailsVO>)mailArrivalVO.getContainerDetails();
		String[] selectedContainers =mailArrivalForm.getChildCont().split(",");

		int sizeOfContainers = 0;
		if(selectedContainers !=null){
			sizeOfContainers = selectedContainers.length;
		}


		if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
    			containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
    		}
    	}

		 if(selectedContainers!=null&&sizeOfContainers>0){
   		  for(int i=0;i<sizeOfContainers;i++){
   			int containerVal = Integer.parseInt(selectedContainers[i]);
				ContainerDetailsVO containerVO = new ContainerDetailsVO();
				containerVO=containerDetailsVOs.get(containerVal);
				containerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
				containerVO.setUndoArrivalFlag(CON);
				for(MailbagVO mailbagVO:containerVO.getMailDetails()){
					if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())){
						if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())){
							mailbagVO.setUndoArrivalFlag(MailConstantsVO.FLAG_YES);
							}
	  					}

				}
   		  	}
		 }
		mailArrivalVO.setContainerDetails(containerDetailsVOs);

		log.log(Log.FINE, "Going To Save ...in command...UndoArrival...",
				mailArrivalVO);
		mailArrivalVO.setChangeFlightFlag(MailConstantsVO.FLAG_NO);

		mailArrivalVOs.add(mailArrivalVO);


    	  //Save container details to new flight
     	 String scanDate= new StringBuilder().append(mailArrivalForm.getFlightScanDate()).append(" ").append(mailArrivalForm.getFlightScanTime()).append(":00").toString();
     	 LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
   	     scanDat.setDateAndTime(scanDate);
   	     boolean isContainerPresent=false;
   	     MailArrivalVO newMailArrivalVO= new MailArrivalVO();
		MailArrivalVO flightMailArrivalVO=mailArrivalSession.getFlightMailArrivalVO();
		ArrayList<ContainerDetailsVO> newContainerDetailsVOs=new ArrayList<ContainerDetailsVO>();
		newContainerDetailsVOs=(ArrayList<ContainerDetailsVO>)flightMailArrivalVO.getContainerDetails() ;
		Collection<DSNVO> newDSNVOs=new ArrayList<DSNVO>();
    	  if(selectedContainers!=null&&sizeOfContainers>0){
    		  for(int i=0;i<sizeOfContainers;i++){
  				int containerVal = Integer.parseInt(selectedContainers[i]);
  				ContainerDetailsVO containerVO = new ContainerDetailsVO();
  				containerVO=containerDetailsVOs.get(containerVal);
  				ContainerDetailsVO newContainerVO = new ContainerDetailsVO();
  				//If the container is already present in flight,set opflag as update
  				if(newContainerDetailsVOs!=null&&newContainerDetailsVOs.size()>0){
  	  				for(ContainerDetailsVO newContainerDetailsVO:newContainerDetailsVOs){
  	  					String CONT1=new StringBuilder(newContainerDetailsVO.getContainerNumber()).append(newContainerDetailsVO.getContainerType()).toString();
  						String CONT2=new StringBuilder(containerVO.getContainerNumber()).append(containerVO.getContainerType()).toString();
  						if(CONT1.equals(CONT2)){
  							int newContainerVal=newContainerDetailsVOs.indexOf(newContainerDetailsVO);
  							isContainerPresent=true;
  							try{
  			  					BeanHelper.copyProperties(newContainerVO, containerVO);
  			  					}catch(SystemException e){
  			  						 e.getMessage();
  			  					}
  			  					newContainerVO.setCompanyCode(logonAttributes.getCompanyCode());
  			  					newContainerVO.setFlightNumber(flightValidationVO.getFlightNumber());
  			  					newContainerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
  			  					newContainerVO.setFlightDate(flightValidationVO.getFlightDate());
  			  					newContainerVO.setFlightStatus(flightValidationVO.getFlightStatus());
  			  					newContainerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
  			  					newContainerVO.setCarrierCode(flightValidationVO.getCarrierCode());
  			  					newContainerVO.setScanDate(scanDat);
  								newContainerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
  	  							newContainerVO.setTotalBags(newContainerDetailsVO.getTotalBags());
  	  							newContainerVO.setTotalWeight(newContainerDetailsVO.getTotalWeight());
  								newContainerVO.setUndoArrivalFlag(NOT);
  								newContainerVO.setRemarks(mailArrivalForm.getRemarks());
  				  				ArrayList<DSNVO> oldDsnVos = (ArrayList<DSNVO>)containerVO.getDsnVOs();
  				  				ArrayList<DSNVO> newDsnVos = (ArrayList<DSNVO>)newContainerDetailsVO.getDsnVOs();
  	  				  			Collection<MailbagVO>oldMailbagVOs=containerVO.getMailDetails();
  	  			  				Collection<MailbagVO>newMailbagVOs=new ArrayList<MailbagVO>();
  	  			  				for(MailbagVO oldMailbagVO:oldMailbagVOs){
  	  			  				if(MailConstantsVO.FLAG_YES.equals(oldMailbagVO.getArrivedFlag())){
  	  			  					MailbagVO newMailbagVO=new MailbagVO();
  	  								try{
  	  									BeanHelper.copyProperties(newMailbagVO, oldMailbagVO);
  	  									}catch(SystemException e){
  	  										 e.getMessage();
  	  									}
  	  								newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
  	  								newMailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
  	  								newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_FLAG_INSERT);
  	  								newMailbagVO.setUndoArrivalFlag(MailConstantsVO.FLAG_NO);
  	  								newMailbagVO.setScannedDate(scanDat);
  	  								newMailbagVO.setCarrierCode(flightValidationVO.getCarrierCode());
  	  								newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
  	  						    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
  	  								newMailbagVO.setContainerNumber(newContainerDetailsVO.getContainerNumber());
  	  								newMailbagVO.setContainerType(newContainerDetailsVO.getContainerType());
  	  						    	newMailbagVO.setSegmentSerialNumber(newContainerDetailsVO.getSegmentSerialNumber());
  	  						    	newMailbagVO.setUldNumber(newContainerDetailsVO.getContainerNumber());
  	  								newMailbagVO.setFlightDate(flightValidationVO.getFlightDate());
  	  								newMailbagVO.setFlightNumber(flightValidationVO.getFlightNumber());
  	  								newMailbagVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
  	  								newMailbagVO.setScannedUser(logonAttributes.getUserId());
  	  								newMailbagVO.setPou(newContainerDetailsVO.getPou());
  	  								newMailbagVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
  	  							newMailbagVO.setMailbagDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
  	  								newMailbagVOs.add(newMailbagVO);

  				  				for(DSNVO oldDsnVO:oldDsnVos){
  				  				String selectedDSN=new StringBuilder(oldDsnVO.getOriginExchangeOffice()).append(oldDsnVO.getDestinationExchangeOffice()).append(oldDsnVO.getMailCategoryCode()).append(oldDsnVO.getMailSubclass()).append(oldDsnVO.getYear()).append(oldDsnVO.getDsn()).toString();
  								String selectedMail=new StringBuilder(oldMailbagVO.getOoe()).append(oldMailbagVO.getDoe()).append(oldMailbagVO.getMailCategoryCode()).append(oldMailbagVO.getMailSubclass()).append(oldMailbagVO.getYear()).append(oldMailbagVO.getDespatchSerialNumber()).toString();
  								if(selectedDSN.equals(selectedMail)){
  				  					DSNVO newDsnVO=new DSNVO();
  									try{
  										BeanHelper.copyProperties(newDsnVO, oldDsnVO);
  										}catch(SystemException e){
  											 e.getMessage();
  										}
  									if(newDsnVos!=null&&newDsnVos.size()>0){
  									for(DSNVO dsn:newDsnVos ){
  										String newDsnpk = dsn.getOriginExchangeOffice()
  											   +dsn.getDestinationExchangeOffice()
  											   +dsn.getMailCategoryCode()
  											   +dsn.getMailSubclass()
  									           +dsn.getDsn()+dsn.getYear();
  										String oldDsnpk = oldDsnVO.getOriginExchangeOffice()
   											   +oldDsnVO.getDestinationExchangeOffice()
   											   +oldDsnVO.getMailCategoryCode()
   											   +oldDsnVO.getMailSubclass()
   									           +oldDsnVO.getDsn()+dsn.getYear();

  										if(oldDsnpk.equals(newDsnpk)){
  											newDsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
  											//newDsnVO.setPrevReceivedWeight(0.0);
  											newDsnVO.setPrevReceivedWeight(new Measure(UnitConstants.MAIL_WGT, 0.0)); //Added by A-7550
  											newDsnVO.setPrevReceivedBags(0);
  										}else{
  											newDsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
  										}
  									}
  									}else{
  										newDsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
  									}
  				  					newDsnVO.setReceivedDate(scanDat);
  				  					newDsnVO.setFlightNumber(flightValidationVO.getFlightNumber());
  				  					newDsnVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
  				  					newDsnVO.setDestination(flightValidationVO.getLegDestination());
  				  					newDsnVO.setCarrierCode(flightValidationVO.getCarrierCode());
  				  					newDsnVO.setCarrierId(flightValidationVO.getFlightCarrierId());
  				  					newDsnVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
  				  					newDsnVO.setPol(flightValidationVO.getLegOrigin());
  				  					newDsnVO.setPou(flightValidationVO.getLegDestination());
  				  					newDSNVOs.add(newDsnVO);
  				  				}
  				  				}
  	  			  				}
  	  			  				}
  			  				newContainerVO.setDsnVOs(newDSNVOs);
  			  				newContainerVO.setMailDetails(newMailbagVOs);
  			  				newContainerDetailsVOs.set(newContainerVal, newContainerVO);
  							}
  	  					}
  	  				}
  				//If the container is not present in the flight,then opflag is I
  				if(!isContainerPresent||newContainerDetailsVOs.size()==0){
  					ArrayList<DSNVO> oldDsnVos = (ArrayList<DSNVO>)containerVO.getDsnVOs();
  					Collection<MailbagVO>oldMailbagVOs=containerVO.getMailDetails();
  	  				try{
		  					BeanHelper.copyProperties(newContainerVO, containerVO);
		  					}catch(SystemException e){
		  						 e.getMessage();
		  					}
		  					newContainerVO.setCompanyCode(logonAttributes.getCompanyCode());
		  					newContainerVO.setFlightNumber(flightValidationVO.getFlightNumber());
		  					newContainerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		  					newContainerVO.setFlightDate(flightValidationVO.getFlightDate());
		  					newContainerVO.setFlightStatus(flightValidationVO.getFlightStatus());
		  					newContainerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		  					newContainerVO.setCarrierCode(containerVO.getCarrierCode());
		  					newContainerVO.setScanDate(scanDat);
		  					newContainerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		  					newContainerVO.setUndoArrivalFlag("NOT");
	  						newContainerVO.setTotalBags(0);
	  						//newContainerVO.setTotalWeight(0.0);
	  						newContainerVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, 0.0)); //Added by A-7550
		  					newContainerVO.setRemarks(mailArrivalForm.getRemarks());

				Collection<MailbagVO>newMailbagVOs=new ArrayList<MailbagVO>();
  				for(MailbagVO oldMailbagVO:oldMailbagVOs){
  					if(MailConstantsVO.FLAG_YES.equals(oldMailbagVO.getArrivedFlag())){
  					MailbagVO newMailbagVO=new MailbagVO();
					try{
						BeanHelper.copyProperties(newMailbagVO, oldMailbagVO);
						}catch(SystemException e){
							 e.getMessage();
						}
						newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
						newMailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_FLAG_INSERT);
						newMailbagVO.setUndoArrivalFlag(MailConstantsVO.FLAG_NO);
						newMailbagVO.setScannedDate(scanDat);
				    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
				    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				    	newMailbagVO.setCarrierCode(flightValidationVO.getCarrierCode());
						newMailbagVO.setContainerNumber(newContainerVO.getContainerNumber());
						newMailbagVO.setContainerType(newContainerVO.getContainerType());
				    	newMailbagVO.setUldNumber(newContainerVO.getContainerNumber());
						newMailbagVO.setFlightDate(flightValidationVO.getFlightDate());
						newMailbagVO.setFlightNumber(flightValidationVO.getFlightNumber());
						newMailbagVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
						newMailbagVO.setScannedUser(logonAttributes.getUserId());
						newMailbagVO.setPou(newContainerVO.getPou());
						newMailbagVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
						newMailbagVO.setMailbagDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
					newMailbagVOs.add(newMailbagVO);

  				for(DSNVO oldDsnVO:oldDsnVos){
  					String selectedDSN=new StringBuilder(oldDsnVO.getOriginExchangeOffice()).append(oldDsnVO.getDestinationExchangeOffice()).append(oldDsnVO.getMailCategoryCode()).append(oldDsnVO.getMailSubclass()).append(oldDsnVO.getYear()).append(oldDsnVO.getDsn()).toString();
					String selectedMail=new StringBuilder(oldMailbagVO.getOoe()).append(oldMailbagVO.getDoe()).append(oldMailbagVO.getMailCategoryCode()).append(oldMailbagVO.getMailSubclass()).append(oldMailbagVO.getYear()).append(oldMailbagVO.getDespatchSerialNumber()).toString();
					if(selectedDSN.equals(selectedMail)){
  					DSNVO newDsnVO=new DSNVO();
					try{
						BeanHelper.copyProperties(newDsnVO, oldDsnVO);
						}catch(SystemException e){
							 e.getMessage();
						}
					newDsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
  					newDsnVO.setReceivedDate(scanDat);
  					newDsnVO.setFlightNumber(flightValidationVO.getFlightNumber());
  					newDsnVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
  					newDsnVO.setDestination(flightValidationVO.getLegDestination());
  					newDsnVO.setCarrierCode(flightValidationVO.getCarrierCode());
  					newDsnVO.setCarrierId(flightValidationVO.getFlightCarrierId());
  					newDsnVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
  					newDsnVO.setPol(flightValidationVO.getLegOrigin());
  					newDsnVO.setPou(flightValidationVO.getLegDestination());
  					newDSNVOs.add(newDsnVO);
  						}
  					}
  					}
  					}
  				newContainerVO.setDsnVOs(newDSNVOs);
  				newContainerVO.setMailDetails(newMailbagVOs);
  				newContainerDetailsVOs.add(newContainerVO);
  	  			}


    		  }
    	  }
    		  newContainerDetailsVOs = makeDSNVOs(newContainerDetailsVOs,logonAttributes);
    		  newMailArrivalVO.setContainerDetails(newContainerDetailsVOs);

        	  mailArrivalSession.setFlightMailArrivalVO(newMailArrivalVO);
        	//Construct Changed Flight details to MailArrivalVO
        	  newMailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
        	  newMailArrivalVO.setFlightCarrierCode(flightValidationVO.getCarrierCode());
        	  newMailArrivalVO.setFlightNumber(flightValidationVO.getFlightNumber());
        	  newMailArrivalVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
        	  newMailArrivalVO.setArrivalDate(flightValidationVO.getFlightDate());
        	  newMailArrivalVO.setCarrierId(flightValidationVO.getFlightCarrierId());
        	  newMailArrivalVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
        	  newMailArrivalVO.setArrivalDate(flightValidationVO.getApplicableDateAtRequestedAirport());
        	  newMailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
        	  newMailArrivalVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
        	  newMailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
        	  newMailArrivalVO.setCompanyCode(flightValidationVO.getCompanyCode());
        	  newMailArrivalVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
        	  newMailArrivalVO.setPol(flightValidationVO.getLegOrigin());
        	  newMailArrivalVO.setPou(flightValidationVO.getLegDestination());
        	  newMailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
        	  newMailArrivalVO.setScanDate(scanDat);
        	  newMailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
        	  newMailArrivalVO.setChangeFlightFlag(MailConstantsVO.FLAG_YES);
        	  newMailArrivalVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
        	  newMailArrivalVO.setMailDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
      		log.log(Log.FINE, "Going To Save ...in command", newMailArrivalVO);

      		 /*
      		 * Construct lock vos for implicit locking
      		 */
      		Collection<LockVO> locks = prepareLocksForSave(newMailArrivalVO);
      		log.log(Log.FINE, "LockVO for implicit check", locks);
      		if (locks == null || locks.size() == 0) {
      			locks = null;
      		}

      		mailArrivalVOs.add(newMailArrivalVO);

      		  try {
      		    new MailTrackingDefaultsDelegate().saveChangeFlightDetails(mailArrivalVOs,locks);
                }catch (BusinessDelegateException businessDelegateException) {
          			errors = handleDelegateException(businessDelegateException);
          	  }
      		  if (errors != null && errors.size() > 0) {
      				for(ErrorVO err : errors){
      					if(ERROR_TRANSFERRED_OR_DELIVERED.equalsIgnoreCase(err.getErrorCode())){
      						ErrorVO error = new ErrorVO(CONTAINER_TRANSFERRED_OR_DELIVERED);
      						error.setErrorDisplayType(ErrorDisplayType.ERROR);
      						invocationContext.addError(error);
      					}else{
      						invocationContext.addAllError(errors);
      					}
      				}
      				mailArrivalSession.setFlightMailArrivalVO(mailArrivalVO);
      	    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
      	    		invocationContext.target = TARGET;
      	    		return;
          	  }

          	MailArrivalVO mailArriveVO = new MailArrivalVO();
          	flightValidationVO = new FlightValidationVO();
          	mailArrivalSession.setFlightMailArrivalVO(mailArriveVO);
          	mailArrivalSession.setChangeFlightValidationVO(flightValidationVO);
          	mailArrivalForm.setPopupCloseFlag(FLAG_YES);
          	mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
          	mailArrivalForm.setArrivalPort(logonAttributes.getAirportCode());
      		invocationContext.target = TARGET;
          	log.exiting("ContainerChangeOkCommand","execute");

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

    /**
	 * Mehtod to update DSN Summary VOs
	 * @param newContainerDetailsVOs
	 * @param logonAttributes
	 * @return Collection<ContainerDetailsVO>
	 */
    public ArrayList<ContainerDetailsVO>  makeDSNVOs(ArrayList<ContainerDetailsVO> newContainerDetailsVOs,
    		LogonAttributes logonAttributes){

    	if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
			for(ContainerDetailsVO popupVO:newContainerDetailsVOs){

				HashMap<String,DSNVO> dsnMapDespatch = new HashMap<String,DSNVO>();
				HashMap<String,String> despatchMap = new HashMap<String,String>();
				Collection<DSNVO> mainDSNVOs = popupVO.getDsnVOs();
				if(mainDSNVOs != null && mainDSNVOs.size() > 0){
					for(DSNVO dsnVO:mainDSNVOs){
						if(!MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
							if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())){
							String dsnpk = dsnVO.getOriginExchangeOffice()
					           +dsnVO.getDestinationExchangeOffice()
					           +dsnVO.getMailCategoryCode()
					           +dsnVO.getMailSubclass()
					           +dsnVO.getDsn()
					           +dsnVO.getYear();
							dsnMapDespatch.put(dsnpk,dsnVO);
							}
						}
				    }
				}

				int rcvdBags = 0;
				double rcvdWgt = 0;
				int delvdBags = 0;
				double delvdWgt = 0;
				Collection<DespatchDetailsVO> despatchDetailsVOs = popupVO.getDesptachDetailsVOs();
				 if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
					for(DespatchDetailsVO despatchVO:despatchDetailsVOs){
						String outerpk = despatchVO.getOriginOfficeOfExchange()
						           +despatchVO.getDestinationOfficeOfExchange()
						           +despatchVO.getMailCategoryCode()
						           +despatchVO.getMailSubclass()
						           +despatchVO.getDsn()
						           +despatchVO.getYear();

						if(despatchMap.get(outerpk) == null){
						if(dsnMapDespatch.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(despatchVO.getDsn());
							dsnVO.setOriginExchangeOffice(despatchVO.getOriginOfficeOfExchange());
							dsnVO.setDestinationExchangeOffice(despatchVO.getDestinationOfficeOfExchange());
							dsnVO.setMailClass(despatchVO.getMailClass());
							dsnVO.setMailCategoryCode(despatchVO.getMailCategoryCode());
							dsnVO.setMailSubclass(despatchVO.getMailSubclass());
							dsnVO.setYear(despatchVO.getYear());
							dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_NO);
							dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						for(DespatchDetailsVO innerVO:despatchDetailsVOs){
							String innerpk = innerVO.getOriginOfficeOfExchange()
					           +innerVO.getDestinationOfficeOfExchange()
					           +innerVO.getMailCategoryCode()
					           +innerVO.getMailSubclass()
					           +innerVO.getDsn()
					           +innerVO.getYear();
							if(outerpk.equals(innerpk)){
								rcvdBags = rcvdBags + innerVO.getReceivedBags();
								//rcvdWgt = rcvdWgt + innerVO.getReceivedWeight();
								rcvdWgt = rcvdWgt + innerVO.getReceivedWeight().getRoundedSystemValue();//added by A-7550
								delvdBags = delvdBags + innerVO.getDeliveredBags();
								//delvdWgt = delvdWgt + innerVO.getDeliveredWeight();
								delvdWgt = delvdWgt + innerVO.getDeliveredWeight().getRoundedSystemValue();//added by A-7550
							}
						}
						dsnVO.setReceivedBags(rcvdBags);
						//dsnVO.setReceivedWeight(rcvdWgt);
						dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, rcvdWgt)); //added by A-7550
						dsnVO.setDeliveredBags(delvdBags);
						//dsnVO.setDeliveredWeight(delvdWgt);
						dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, delvdWgt)); //added by A-7550
						dsnMapDespatch.put(outerpk,dsnVO);

						}else{
							DSNVO dsnVO = dsnMapDespatch.get(outerpk);
							String dsnpk = dsnVO.getOriginExchangeOffice()
					           +dsnVO.getDestinationExchangeOffice()
					           +dsnVO.getMailCategoryCode()
					           +dsnVO.getMailSubclass()
					           +dsnVO.getDsn()
					           +dsnVO.getYear();
							if(despatchDetailsVOs.size() > 0){
								for(DespatchDetailsVO dsptchVO:despatchDetailsVOs){
								String despatchpk = dsptchVO.getOriginOfficeOfExchange()
								           +dsptchVO.getDestinationOfficeOfExchange()
								           +dsptchVO.getMailCategoryCode()
								           +dsptchVO.getMailSubclass()
								           +dsptchVO.getDsn()
								           +dsptchVO.getYear();
									    if(dsnpk.equals(despatchpk)){
									    	if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsptchVO.getOperationalFlag())){
									    		dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
									    	}
											rcvdBags = rcvdBags + despatchVO.getReceivedBags();
											//rcvdWgt = rcvdWgt + despatchVO.getReceivedWeight();
											rcvdWgt = rcvdWgt + despatchVO.getReceivedWeight().getRoundedSystemValue();//added by A-7550
											delvdBags = delvdBags + despatchVO.getDeliveredBags();
											//delvdWgt = delvdWgt + despatchVO.getDeliveredWeight();
											delvdWgt = delvdWgt + despatchVO.getDeliveredWeight().getRoundedSystemValue();//added by A-7550
										}
								}
								//if(dsnVO.getReceivedBags()!= rcvdBags
								//	|| dsnVO.getReceivedWeight()!= rcvdWgt
								//	|| dsnVO.getDeliveredBags()!= delvdBags
								//	|| dsnVO.getDeliveredWeight()!= delvdWgt){
								//Added by A-7550	
								if(dsnVO.getReceivedBags()!= rcvdBags
											|| dsnVO.getReceivedWeight().getRoundedSystemValue()!= rcvdWgt
									|| dsnVO.getDeliveredBags()!= delvdBags
											|| dsnVO.getDeliveredWeight().getRoundedSystemValue()!= delvdWgt){
									if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())){
										dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
									}
								}
								dsnVO.setReceivedBags(rcvdBags);
								//dsnVO.setReceivedWeight(rcvdWgt);
								dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, rcvdWgt)); //Added by A-7550
								dsnVO.setDeliveredBags(delvdBags);
								//dsnVO.setDeliveredWeight(delvdWgt);
								dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, delvdWgt)); //Added by A-7550 
								dsnMapDespatch.put(outerpk,dsnVO);
							}
						  }
						despatchMap.put(outerpk,outerpk);
						}
						rcvdBags = 0;
						rcvdWgt = 0;
						delvdBags = 0;
						delvdWgt = 0;
						}
					}


				 /**
				  * For Mail Bag Details
				  */

				 HashMap<String,String> mailMap = new HashMap<String,String>();
				 HashMap<String,DSNVO> dsnMapMailbag = new HashMap<String,DSNVO>();
					if(mainDSNVOs != null && mainDSNVOs.size() > 0){
						for(DSNVO dsnVO:mainDSNVOs){
							if(MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
								if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())){
								String dsnpk = dsnVO.getOriginExchangeOffice()
						           +dsnVO.getDestinationExchangeOffice()
						           //added by anitha
						           //+dsnVO.getMailClass()
						           +dsnVO.getMailCategoryCode()
						           +dsnVO.getMailSubclass()
						           +dsnVO.getDsn()
						           +dsnVO.getYear();
								dsnMapMailbag.put(dsnpk,dsnVO);
								}
							}
					    }
					}

				int numBags = 0;
				double bagWgt = 0;
				int dlvBags = 0;
				double dlvWgt = 0;
				 Collection<MailbagVO> mailbagVOs = popupVO.getMailDetails();
				 if(mailbagVOs != null && mailbagVOs.size() > 0){
					for(MailbagVO mailbagVO:mailbagVOs){

						String outerpk = mailbagVO.getOoe()+mailbagVO.getDoe()
								   + mailbagVO.getMailCategoryCode()
						           + mailbagVO.getMailSubclass()
						           +mailbagVO.getDespatchSerialNumber()+mailbagVO.getYear();
						if(mailMap.get(outerpk) == null){
						if(dsnMapMailbag.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
							dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
							dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
							dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
							dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
							dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
							dsnVO.setYear(mailbagVO.getYear());
							dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
							dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						for(MailbagVO innerVO:mailbagVOs){
							String innerpk = innerVO.getOoe()+innerVO.getDoe()
							+ innerVO.getMailCategoryCode()
					           +(innerVO.getMailSubclass())
					           +innerVO.getDespatchSerialNumber()+innerVO.getYear();
							if(outerpk.equals(innerpk)){
								if(MailConstantsVO.FLAG_YES.equals(innerVO.getArrivedFlag())){
									dsnVO.setReceivedBags(numBags + 1);
									//dsnVO.setReceivedWeight(bagWgt + innerVO.getWeight());
									try {
										dsnVO.setReceivedWeight(Measure.addMeasureValues(new Measure(UnitConstants.MAIL_WGT,bagWgt ), innerVO.getWeight()));
									} catch (UnitException e) {
										// TODO Auto-generated catch block
										log.log(Log.SEVERE, "UnitException",e.getMessage());
									}//added by A-7371
									
									numBags= dsnVO.getReceivedBags();
									//bagWgt = dsnVO.getReceivedWeight();
									bagWgt = dsnVO.getReceivedWeight().getRoundedSystemValue(); //Added by A-7550
								}
								if(MailConstantsVO.FLAG_YES.equals(innerVO.getDeliveredFlag())){
									dsnVO.setDeliveredBags(dlvBags + 1);
									//dsnVO.setDeliveredWeight(dlvWgt + innerVO.getWeight());
									try {
										dsnVO.setDeliveredWeight(Measure.addMeasureValues(new Measure(UnitConstants.MAIL_WGT,dlvWgt), innerVO.getWeight()));
									} catch (UnitException e) {
										// TODO Auto-generated catch block
										log.log(Log.SEVERE, "UnitException",e.getMessage());
									}//added by A-7371
									
									dlvBags = dsnVO.getDeliveredBags();
									//dlvWgt = dsnVO.getDeliveredWeight();
									dlvWgt = dsnVO.getDeliveredWeight().getRoundedSystemValue(); //Added by A-7550

								}
							}
						}
						dsnMapMailbag.put(outerpk,dsnVO);
						}else{
							DSNVO dsnVO = dsnMapMailbag.get(outerpk);
							String dsnpk = dsnVO.getOriginExchangeOffice()
					           +dsnVO.getDestinationExchangeOffice()
					           +dsnVO.getMailCategoryCode()
					           +dsnVO.getMailSubclass()
					           +dsnVO.getDsn()
					           +dsnVO.getYear();
							if(mailbagVOs.size() > 0){
								for(MailbagVO mbagVO:mailbagVOs){
								String mailpk = mbagVO.getOoe()+mbagVO.getDoe()
								   + mbagVO.getMailCategoryCode()
						           + mbagVO.getMailSubclass()
						           + mbagVO.getDespatchSerialNumber()+mbagVO.getYear();
									    if(dsnpk.equals(mailpk)){
									    	if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(mbagVO.getOperationalFlag())
									    			|| MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mbagVO.getOperationalFlag())){
									    		dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
									    	}
											if(MailConstantsVO.FLAG_YES.equals(mbagVO.getArrivedFlag()) ){
												numBags = numBags + 1;
												if(mbagVO.getWeight()!=null){//added byA-7550
												bagWgt = bagWgt + mbagVO.getWeight().getRoundedSystemValue();
												}
											}
											if(MailConstantsVO.FLAG_YES.equals(mbagVO.getDamageFlag())){
												dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
										    }
											if(MailConstantsVO.FLAG_YES.equals(mbagVO.getDeliveredFlag())){
												dlvBags = dlvBags + 1;
												if(mbagVO.getWeight()!=null){//added by A-7550
												dlvWgt = dlvWgt + mbagVO.getWeight().getRoundedSystemValue();
												}
											}
										}
								}
								//if(dsnVO.getReceivedBags()!= numBags
									//	|| dsnVO.getReceivedWeight()!= bagWgt){
								//Added by A-7550
								if(dsnVO.getReceivedBags()!= numBags
											|| dsnVO.getReceivedWeight().getRoundedSystemValue()!= bagWgt){
										dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								}
								dsnVO.setReceivedBags(numBags);
								//dsnVO.setReceivedWeight(bagWgt);
								dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, bagWgt)); //Added by A-7550
								//if(dsnVO.getDeliveredBags()!= dlvBags
								//		|| dsnVO.getDeliveredWeight()!= dlvWgt){
									//Added by A-7550
								if(dsnVO.getDeliveredBags()!= dlvBags
											|| dsnVO.getDeliveredWeight().getRoundedSystemValue()!= dlvWgt){
											dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
									}
								dsnVO.setDeliveredBags(dlvBags);
								//dsnVO.setDeliveredWeight(dlvWgt);
								dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, dlvWgt)); //Added by A-7550
								dsnMapMailbag.put(outerpk,dsnVO);
							}
						  }
						mailMap.put(outerpk,outerpk);
						}
						numBags = 0;
						bagWgt = 0;
						dlvBags = 0;
						dlvWgt = 0;
					}
				}

				Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
				for(String key:dsnMapDespatch.keySet()){
					DSNVO dsnVO = dsnMapDespatch.get(key);
					newDSNVOs.add(dsnVO);
				}
				for(String key:dsnMapMailbag.keySet()){
					DSNVO dsnVO = dsnMapMailbag.get(key);
					if(newDSNVOs.size() == 0){
					   newDSNVOs = new ArrayList<DSNVO>();
					}
					newDSNVOs.add(dsnVO);
				}

				Collection<DSNVO> oldDSNVOs = popupVO.getDsnVOs();
				int rcvedBags = 0;
				double rcvedWgt = 0;
				if(newDSNVOs.size() > 0){
					for(DSNVO dsnVO1:newDSNVOs){
						String outerpk = dsnVO1.getOriginExchangeOffice()
						   +dsnVO1.getDestinationExchangeOffice()
						   +dsnVO1.getMailCategoryCode()
						   +dsnVO1.getMailSubclass()
				           +dsnVO1.getDsn()+dsnVO1.getYear();
						int flag = 0;
						if(oldDSNVOs != null && oldDSNVOs.size() > 0){
							for(DSNVO dsnVO2:oldDSNVOs){
								String innerpk = dsnVO2.getOriginExchangeOffice()
								   +dsnVO2.getDestinationExchangeOffice()
								   +dsnVO2.getMailCategoryCode()
								   +dsnVO2.getMailSubclass()
						           +dsnVO2.getDsn()+dsnVO2.getYear();
								if(outerpk.equals(innerpk)){
									if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO2.getOperationFlag())){
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
						}
						if(flag == 1){
							flag = 0;
						}
						//accBags = accBags + dsnVO1.getBags();
						//accWgt = accWgt + dsnVO1.getWeight();
						rcvedBags = rcvedBags + dsnVO1.getReceivedBags();
						//rcvedWgt = rcvedWgt + dsnVO1.getReceivedWeight();
						rcvedWgt = rcvedWgt + dsnVO1.getReceivedWeight().getRoundedSystemValue(); //Added by A-7550
					}
				}
				//popupVO.setTotalBags(accBags);
				//popupVO.setTotalWeight(accWgt);
				popupVO.setReceivedBags(rcvedBags);
				//popupVO.setReceivedWeight(rcvedWgt);
				popupVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, rcvedWgt)); //Added by A-7550
				popupVO.setDsnVOs(newDSNVOs);
			}
    	 }

    	return newContainerDetailsVOs;
    }

}
