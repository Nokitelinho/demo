/*
 * MoveManifestbagOkCommand.java Created on Jul 1 2016
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class MoveManifestbagOkCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "manifestbagok_success";
   private static final String TARGET_FAILURE = "manifestbagok_failure";
   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
   private static final String ULD_TYPE = "U";
   private static final String BULK_TYPE = "B";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("MoveManifestbagOkCommand","execute");

    	MailManifestForm mailManifestForm =
    		(MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	int samefltFlag=0;
    	 String containerType = mailManifestForm.getContainerType();
    	 OperationalFlightVO operationalFlightVO= mailManifestSession.getOperationalFlightVO();
    	 operationalFlightVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
    	 MailManifestVO initMailManifestVO = mailManifestSession.getMailManifestVO();
    	 String selectedContainer = mailManifestForm.getParentContainer();
    	 Collection<ContainerDetailsVO> initialContainerDetailsVOs = initMailManifestVO.getContainerDetails();
    	
    	 int flag = 0;
    	 Collection<ContainerDetailsVO> filterContDetailsVOs = new ArrayList<ContainerDetailsVO>();
        	   for (ContainerDetailsVO conDetVO : initialContainerDetailsVOs){ 
        		   if(conDetVO.getDesptachDetailsVOs() == null){	   
		         	 Collection<DSNVO> dsnVOs = conDetVO.getDsnVOs();
			         	 if(dsnVOs != null && dsnVOs.size() > 0){
			 	        	for(DSNVO dsnVO:dsnVOs){
			 	        		if(dsnVO.getMailbags() != null && dsnVO.getMailbags().size()>0){
			 	        			flag = 1;
			 	        		}
			 	        	}
			         	 }
    		        }
        		    if(flag == 0){
        		    	 filterContDetailsVOs.add(conDetVO);
        		    	 
  	         	    }else{
  	         	    	flag = 0; 
  	         	    }
        	   }
        	   log.log(Log.INFO, "filterContDetailsVOs....", filterContDetailsVOs);
			if(filterContDetailsVOs != null && filterContDetailsVOs.size() > 0){
 	        	   try {
 	        		  filterContDetailsVOs = new MailTrackingDefaultsDelegate().findMailbagsInContainerForManifest(filterContDetailsVOs);
 	               }catch (BusinessDelegateException businessDelegateException) {
 	     			errors = handleDelegateException(businessDelegateException);
 	     	       }
        	   }
        	   Collection<ContainerDetailsVO> newContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
        	   int selflag = 0;
        	   log.log(Log.INFO, "filterContDetailsVOs..after ..",
					filterContDetailsVOs);
				if(filterContDetailsVOs != null && filterContDetailsVOs.size() > 0){
    	        	for (ContainerDetailsVO initialContainerDetails : initialContainerDetailsVOs){ 
	    	        	for(ContainerDetailsVO filtercontDtls:filterContDetailsVOs){
	    	        		if(initialContainerDetails.getContainerNumber().equals(filtercontDtls.getContainerNumber())){
	    	        			initialContainerDetails.setDesptachDetailsVOs(filtercontDtls.getDesptachDetailsVOs());
	    	        			Collection<DSNVO> serverdsnVOs = filtercontDtls.getDsnVOs();
	    	        			Collection<DSNVO> dsnVOs = initialContainerDetails.getDsnVOs();
	    	        			 if(serverdsnVOs != null && serverdsnVOs.size() > 0){
	    	    		        	for(DSNVO dsn:dsnVOs){
	    	    		        		for(DSNVO serverdsn:serverdsnVOs){
	    	    		        			if(dsn.getOriginExchangeOffice().equals(serverdsn.getOriginExchangeOffice())
	    			   			       			&& dsn.getDestinationExchangeOffice().equals(serverdsn.getDestinationExchangeOffice())
	    			   			       			&& dsn.getMailCategoryCode().equals(serverdsn.getMailCategoryCode())
	    			   			       			&& dsn.getMailSubclass().equals(serverdsn.getMailSubclass())
	    			   			       			&& dsn.getDsn().equals(serverdsn.getDsn())
	    			   			       			&& dsn.getYear() == serverdsn.getYear()){
	    	    		        				dsn.setMailbags(serverdsn.getMailbags());
	    	    		        			}
	    	    		        		}
	    	    		        	}
	    	    		        }
	    	        			 initialContainerDetails.setDsnVOs(dsnVOs);
	    	        			newContainerDetailsVOs.add(initialContainerDetails);
	    	        			selflag = 1;
	    	        		}
	    	        	}
    	        		
	    	        	if(selflag == 0){
	    	        		 newContainerDetailsVOs.add(initialContainerDetails);
	  	         	    }else{
	  	         	    	selflag = 0; 
	  	         	    }	
    	        	}
    	        }
    	        log.log(Log.INFO, "newContainerDetailsVOs.. ..",
						newContainerDetailsVOs);
				initMailManifestVO.setContainerDetails(newContainerDetailsVOs); 
    	        mailManifestSession.setMailManifestVO(initMailManifestVO);
           MailManifestVO mailManifestVO = mailManifestSession.getMailManifestVO();
     	   Collection<ContainerDetailsVO> containerDetailsVOs = mailManifestVO.getContainerDetails();
     	  
    	 ContainerDetailsVO containerDetailsVO = (ContainerDetailsVO) new ArrayList<ContainerDetailsVO>(containerDetailsVOs).get(Integer.parseInt(selectedContainer));
    	 Collection<DSNVO> dsnVOs = new ArrayList<DSNVO>();
    	 ContainerVO containerVO = new ContainerVO();
    	 
    	 if(containerType.equals(containerDetailsVO.getContainerType()) &&
    	 	containerDetailsVO.getContainerNumber().equals(mailManifestForm.getUld())){
    		 invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.msg.err.sameUld"));
 	   		 invocationContext.target = TARGET_FAILURE;
 	   		 return;
    	 }
    	 
    	 if(containerType.equals(ULD_TYPE)){
	    	 if(containerDetailsVOs !=null){
	    		 for (ContainerDetailsVO conDetVO : containerDetailsVOs){	    			
	    				 if(conDetVO.getContainerNumber().equals(mailManifestForm.getUld())){
	    					 containerVO.setOperationFlag("U");
	    					 samefltFlag = 1;
	    				 }	    			
	    		 }
			 }
    	 }
    	 if(containerType.equals(BULK_TYPE)){
    		 if(mailManifestForm.getType().equals(BULK_TYPE)){
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.msg.err.alreadyInBulk"));
 	   			invocationContext.target = TARGET_FAILURE;
 	   			return;
    		 }
    		 if(containerDetailsVOs !=null){
	    		 for (ContainerDetailsVO conDetVO : containerDetailsVOs){
	    			 if(! conDetVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())){
	    				 if(conDetVO.getContainerNumber().equals(mailManifestForm.getUld())){
	    					 invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.msg.err.uldAlreadyInFlight"));
	    		 	   		 invocationContext.target = TARGET_FAILURE;
	    		 	   		 return;
	    				 }
	    			 }
	    		 }
    		 }
             if(("").equals(mailManifestForm.getUld())){
	   			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.msg.err.noBulkUldNumber"));
	   			invocationContext.target = TARGET_FAILURE;
	   			return;
     		 }
             else if((mailManifestForm.getUld()).equals(mailManifestForm.getContainerNo()) && ("M").equals(mailManifestForm.getSelectMod())){
            	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.msg.err.alreadyManifestedUld"));
 	   			invocationContext.target = TARGET_FAILURE;
 	   			return;
     		}
         }
    	 if(containerType.equals(ULD_TYPE)){
    		 if(("").equals(mailManifestForm.getUld())){
 	   			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.msg.err.noUldNumber"));
 	   			invocationContext.target = TARGET_FAILURE;
 	   			return;
      		 }
    		 if(("M").equals(mailManifestForm.getSelectMod())){
    			 String selectedDSNs = mailManifestForm.getSelctDSN();

    			 String[] primaryKey = selectedDSNs.split(",");
    		     dsnVOs =containerDetailsVO.getDsnVOs();
    		     for(int j=0;j<primaryKey.length;j++){
    		    	 DSNVO dVO=(DSNVO) new ArrayList<DSNVO>(containerDetailsVO.getDsnVOs()).get(Integer.parseInt(primaryKey[j]));
    		    	 if(dsnVOs !=null){
	    		         for (DSNVO dsnVO : dsnVOs){
	    		        	 if(BULK_TYPE.equals(dsnVO.getContainerType())){
		    		        	 if(!(dsnVO.getCompanyCode().equals(dVO.getCompanyCode())&&
		    		        			 dsnVO.getDsn().equals(dVO.getDsn())&&
		    		        			 dsnVO.getOriginExchangeOffice().equals(dVO.getOriginExchangeOffice())&&
		    		        			 dsnVO.getDestinationExchangeOffice().equals(dVO.getDestinationExchangeOffice())&&
		    		        			 dsnVO.getMailCategoryCode().equals(dVO.getMailCategoryCode())&&
		    		        			 dsnVO.getMailSubclass().equals(dVO.getMailSubclass())&&
		    		        			 dsnVO.getYear()==dVO.getYear())){
		    		        		 Collection<String> dsnContainers = dsnVO.getDsnContainers();
		    		        		 if(dsnContainers != null){
			    		        		 for(String dsnContainer : dsnContainers){
			    		        			 if(dsnContainer.equals(mailManifestForm.getUld())){
			    		        				 invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.msg.err.uldManifestedWithBulk"));
			    		        	 	   		 invocationContext.target = TARGET_FAILURE;
			    		        	 	   		 return;
			    		        			 }
			    		        		 }
		    		        		 }

		    		        	 }
	    		        	 }
	    		         }
    		    	 }
    		     }


    		     int contNum = containerDetailsVOs.size();
    		     for (int a=0;a<contNum;a++){
    		    	 if(a != Integer.parseInt(selectedContainer)){
    		    		 ContainerDetailsVO contDtlsVO = (ContainerDetailsVO) new ArrayList<ContainerDetailsVO>(containerDetailsVOs).get(a);
    		    		 Collection<DSNVO> dsnVOss =contDtlsVO.getDsnVOs();
    		    		 if(dsnVOss !=null){
    	    		        for (DSNVO dsnVO : dsnVOss){
    	    		        	if(BULK_TYPE.equals(dsnVO.getContainerType())){
	    	    		        	 Collection<String> dsnContainers = dsnVO.getDsnContainers();
	    	    		        	 if(dsnContainers != null){
		    	    		        	 for(String dsnContainer : dsnContainers){
		    	    		        		 if(dsnContainer.equals(mailManifestForm.getUld())){
		    	    		        			 invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.msg.err.uldManifestedWithBulk"));
		        		        	 	   		 invocationContext.target = TARGET_FAILURE;
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

    	 Collection<MailbagVO> mailbagVOs=new ArrayList<MailbagVO>();
    	 Collection<DespatchDetailsVO> despatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
         String selectedDSNs = mailManifestForm.getSelctDSN();

		 String[] primaryKey = selectedDSNs.split(",");
	     if(primaryKey!=null && primaryKey.length>0){
	    	 if(("M").equals(mailManifestForm.getSelectMod())&& containerType.equals(BULK_TYPE)){
		    	 if(containerDetailsVOs !=null){
		    		 for (ContainerDetailsVO conDetVO : containerDetailsVOs){	
		    			 Collection<DSNVO> dsnvos = conDetVO.getDsnVOs();
		    			 for (DSNVO dsnvo : dsnvos){		    				 
		    				 String inpk="";
		    	    			inpk = dsnvo.getOriginExchangeOffice()
		    					+dsnvo.getDestinationExchangeOffice()
		    					+dsnvo.getMailCategoryCode()
		    					+dsnvo.getMailSubclass()
		    					+dsnvo.getDsn()
		    					+dsnvo.getYear();
		    		    		if(("Y").equalsIgnoreCase(dsnvo.getPltEnableFlag())){
		    		    			for(MailbagVO mailVO : dsnvo.getMailbags()){
		    		    			   String outpk = mailVO.getOoe()
		    		    			   +mailVO.getDoe()
		    				           +mailVO.getMailCategoryCode()
		    				           +mailVO.getMailSubclass()
		    				           +mailVO.getDespatchSerialNumber()
		    				           +mailVO.getYear();

		    		    			   if(inpk.equals(outpk)){	    		    			   
		    		    				   if(mailManifestForm.getUld().equals(mailVO.getContainerNumber())){
		    			    					 containerVO.setOperationFlag("U");
		    			    					 samefltFlag = 1;
		    			    				 }	    
		    		    			   }
		    		    			}
		    		    		}
		    		    		else{
		    		    			for(DespatchDetailsVO despatchDtlsVO : containerDetailsVO.getDesptachDetailsVOs()){
		    		    				String despatchOutpk = despatchDtlsVO.getOriginOfficeOfExchange()
		    		    				+despatchDtlsVO.getDestinationOfficeOfExchange()
		    		 		            +despatchDtlsVO.getMailCategoryCode()
		    		 		            +despatchDtlsVO.getMailSubclass()
		    		 		            +despatchDtlsVO.getDsn()
		    		 		            +despatchDtlsVO.getYear();
		    		     			    if(inpk.equals(despatchOutpk)){
		    		     			    	if(mailManifestForm.getUld().equals(despatchDtlsVO.getContainerNumber())){
		    			    					 containerVO.setOperationFlag("U");
		    			    					 samefltFlag = 1;
		    			    				 }	   
		    		     			    }
		    		    			}
		    		    		}		    		    		
		    			 }	    			 
		    				   			
		    		 }
				 }
	    	 }
			for (int i=0;i<primaryKey.length;i++){

	    		DSNVO dsnVO=(DSNVO) new ArrayList<DSNVO>(containerDetailsVO.getDsnVOs()).get(Integer.parseInt(primaryKey[i]));
	    		String innerpk="";
    			innerpk = dsnVO.getOriginExchangeOffice()
				+dsnVO.getDestinationExchangeOffice()
				+dsnVO.getMailCategoryCode()
				+dsnVO.getMailSubclass()
				+dsnVO.getDsn()
				+dsnVO.getYear();

	    		if(("Y").equalsIgnoreCase(dsnVO.getPltEnableFlag())){
    				log.log(Log.FINE, "@@@@@dsnVO.getMailbags()", dsnVO.getMailbags());
					for(MailbagVO mailbagVO : dsnVO.getMailbags()){

	    			   String outerpk = mailbagVO.getOoe()
	    			   +mailbagVO.getDoe()
			           +mailbagVO.getMailCategoryCode()
			           +mailbagVO.getMailSubclass()
			           +mailbagVO.getDespatchSerialNumber()
			           +mailbagVO.getYear();

	    			   if(innerpk.equals(outerpk)){

	    				   mailbagVO.setCarrierCode(operationalFlightVO.getCarrierCode());
	    				   mailbagVO.setCarrierId(mailManifestVO.getCarrierId());
	    				   mailbagVO.setCompanyCode(mailManifestVO.getCompanyCode());
	    				   mailbagVO.setFlightDate(operationalFlightVO.getFlightDate());
	    				   mailbagVO.setFlightNumber(operationalFlightVO.getFlightNumber());
	    				   mailbagVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
	    				   mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
	    				   mailbagVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
	    				   mailbagVO.setPol(operationalFlightVO.getPol());
	    				   mailbagVO.setScannedPort(logonAttributes.getAirportCode());
	    				   mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	    				   mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
	    				   mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
	    				   mailbagVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());

	    				   mailbagVOs.add(mailbagVO);
	    			   }
	    			}
	    		}
	    		else{
	    			for(DespatchDetailsVO despatchDetailsVO : containerDetailsVO.getDesptachDetailsVOs()){
	    				String outpk = despatchDetailsVO.getOriginOfficeOfExchange()
	    				+despatchDetailsVO.getDestinationOfficeOfExchange()
	 		            +despatchDetailsVO.getMailCategoryCode()
	 		            +despatchDetailsVO.getMailSubclass()
	 		            +despatchDetailsVO.getDsn()
	 		            +despatchDetailsVO.getYear();
	     			    if(innerpk.equals(outpk)){
	     			    	despatchDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
	     			    	despatchDetailsVO.setCarrierId(mailManifestVO.getCarrierId());
	     			    	despatchDetailsVO.setCompanyCode(mailManifestVO.getCompanyCode());
	     			    	despatchDetailsVO.setFlightDate(operationalFlightVO.getFlightDate());
	     			    	despatchDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
	     			    	despatchDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
	     			    	despatchDetailsVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
	     			    	despatchDetailsVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
	     			    	despatchDetailsVO.setContainerType(containerDetailsVO.getContainerType());
	     			    	despatchDetailsVO.setUldNumber(containerDetailsVO.getContainerNumber());
	     			    	despatchDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	     			    	despatchDetailsVOs.add(despatchDetailsVO);
	     			    }
	    			}
	    		}
	    		dsnVOs.add(dsnVO);
	    	}
	     }

    	String containerNum = mailManifestForm.getUld().trim().toUpperCase();



    	ULDDelegate uldDelegate = new ULDDelegate();

    	if(ULD_TYPE.equals(containerType)){
			try {

				uldDelegate.validateULD(logonAttributes.getCompanyCode(),containerNum);
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

			if (errors != null && errors.size() > 0) {

				invocationContext.addError(new ErrorVO("mailtracking.defaults.mailmanifest.invaliduldnumber",
		   				new Object[]{containerNum}));
		  		invocationContext.target = TARGET;

		  		return;
			}
    	}
		
		if(!"Y".equals(mailManifestForm.getOverrideContainerFlag())){

			containerVO.setAcceptanceFlag(containerDetailsVO.getAcceptedFlag());
			containerVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
			containerVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
			containerVO.setCompanyCode(operationalFlightVO.getCompanyCode());
	    	containerVO.setAssignedPort(operationalFlightVO.getPol());
	    	containerVO.setContainerNumber(containerNum);
	    	containerVO.setFlightNumber(operationalFlightVO.getFlightNumber());
	    	containerVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
	    	containerVO.setCarrierId(operationalFlightVO.getCarrierId());
	    	containerVO.setCarrierCode(operationalFlightVO.getCarrierCode());
	    	containerVO.setFinalDestination(containerDetailsVO.getDestination());
	    	containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	    	containerVO.setType(containerType);
	    	containerVO.setFlightDate(operationalFlightVO.getFlightDate());
	    	containerVO.setPou(containerDetailsVO.getPou());
	    	containerVO.setOnwardRoute(containerDetailsVO.getRoute());
	    	containerVO.setAssignmentFlag("F");
	    	if(samefltFlag==0){
	    		containerVO.setOperationFlag("I");
	    	}
	    	containerVO.setAssignedUser(logonAttributes.getUserId());//TODO
	    	LocalDate assignDate=null;
	    	assignDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);	    	
	    	containerVO.setAssignedDate(assignDate);

	    	boolean isSameFltError = false;
	    	if(samefltFlag==0){
		    	try {
		    		containerVO = new MailTrackingDefaultsDelegate().validateContainer(logonAttributes.getAirportCode(), containerVO);
				}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
					if (errors != null && errors.size() > 0) {
						for(ErrorVO vo : errors) {
							log.log(Log.FINE,
									"vo.getErrorCode() ------------> ", vo.getErrorCode());
							if (("mailtracking.defaults.openedflight").equals(vo.getErrorCode())) {
								Object[] obj = vo.getErrorData();
								ContainerAssignmentVO containerAssignmentVO = (ContainerAssignmentVO)obj[2];
								log
										.log(
												Log.FINE,
												"ContainerAssignmentVO (Flight)------------> ",
												containerAssignmentVO);
								containerVO.setReassignFlag(true);
								containerVO.setCarrierId(containerAssignmentVO.getCarrierId());
								containerVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
								containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
								containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
								containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
								containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
								containerVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
								containerVO.setFlightDate(containerAssignmentVO.getFlightDate());
								containerVO.setAcceptanceFlag(containerAssignmentVO.getAcceptanceFlag());
								invocationContext.addError(vo);
								break;
							}
							else if (("mailtracking.defaults.canreassigned").equals(vo.getErrorCode())) {
								Object[] obj = vo.getErrorData();
								ContainerAssignmentVO containerAssignmentVO = (ContainerAssignmentVO)obj[2];
								log
										.log(
												Log.FINE,
												"ContainerAssignmentVO (Destn)------------> ",
												containerAssignmentVO);
								containerVO.setReassignFlag(true);
								containerVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
								containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
								containerVO.setCarrierId(containerAssignmentVO.getCarrierId());
								containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
								containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
								containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
								containerVO.setAcceptanceFlag(containerAssignmentVO.getAcceptanceFlag());
								invocationContext.addError(vo);
								break;
							}
							else {
								containerVO.setOperationFlag("I");
								containerVO.setCarrierId(operationalFlightVO.getCarrierId());
								containerVO.setFlightNumber(operationalFlightVO.getFlightNumber());
								containerVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
								containerVO.setCarrierCode(operationalFlightVO.getCarrierCode());
								containerVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
								containerVO.setFlightDate(operationalFlightVO.getFlightDate());
								//ignore this error
								if("mailtracking.defaults.sameflight".equals(vo.getErrorCode())) {
									isSameFltError = true;
								} else {
									invocationContext.addError(vo);
								}
								break;
							}
						}
					}
					mailManifestSession.setContainerVO(containerVO);
					if(!isSameFltError) {
						invocationContext.target = TARGET;
						return;
					}
				}
				if(!isSameFltError && containerVO.getSegmentSerialNumber()!=0){
					if(BULK_TYPE.equals(containerType)){
						containerVO.setOperationFlag("I");
					}
				}
	    	}
    	} else{
			containerVO = mailManifestSession.getContainerVO();
		}
		/*
		 * Construct lock vos for implicit locking
		 */
		Collection<LockVO> locks = prepareLocksForSave(containerVO);
		log.log(Log.FINE, "LockVO for implicit check", locks);
		try {
			new MailTrackingDefaultsDelegate().moveMailbagsInManifest(mailbagVOs, containerVO ,operationalFlightVO,locks);
		}
		catch(BusinessDelegateException businessDelegateException){

			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			return;

		}
		/*try{

			new MailTrackingDefaultsDelegate().reassignDSNs(despatchDetailsVOs,containerVO);
		}
		catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			return;
		}
		try{
			log.log(Log.FINE, "*******mailbagVOs***"+mailbagVOs);
			log.log(Log.FINE, "*******containerVO***"+containerVO);
			new MailTrackingDefaultsDelegate().reassignMailbags(mailbagVOs,containerVO);
		}
		catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			return;
		}*/

		mailManifestForm.setMoveMailFlag("CLOSEPOPUP");
		invocationContext.target = TARGET;
       	log.exiting("MoveManifestbagOkCommand","execute");

    }
    /*
     * Added by Indu
     */
    private Collection<LockVO> prepareLocksForSave(
    		ContainerVO containerVO) {
    	log.log(Log.FINE, "\n prepareLocksForSave------->>", containerVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<LockVO> locks = new ArrayList<LockVO>();

    	if (containerVO != null) {
    		//for (ContainerVO conVO : containerVOs) {
    			ULDLockVO lock = new ULDLockVO();
    			lock.setAction(LockConstants.ACTION_MALMANFST);
    			lock.setClientType(ClientType.WEB);
    			lock.setCompanyCode(logonAttributes.getCompanyCode());
    			lock.setScreenId(SCREEN_ID);
    			lock.setStationCode(logonAttributes.getStationCode());
    			lock.setUldNumber(containerVO.getContainerNumber());
    			lock.setDescription(containerVO.getContainerNumber());
    			lock.setRemarks(containerVO.getContainerNumber());
    			log.log(Log.FINE, "\n lock------->>", lock);
				locks.add(lock);
    	//	}
    	}
    	return locks;
    }
}





