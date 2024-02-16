/*
 * ListArriveMailCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ListArriveMailCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
   private static final String SYSPAR_DEFUNIT_WEIGHT= "mail.operations.defaultcaptureunit";
   private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ListArriveMailCommand","execute");

    	MailArrivalForm mailArrivalForm =
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		String containerNum = mailArrivalForm.getContainerNo();
		String containerType = mailArrivalForm.getContainerType();
		mailArrivalForm.setIsContainerValidFlag("");
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		ContainerDetailsVO contDtlVO = mailArrivalSession.getContainerDetailsVO();
		ContainerAssignmentVO containerAssignmentVO = null;
		MailTrackingDefaultsDelegate defaultsDelegate = new MailTrackingDefaultsDelegate();
		if (containerNum != null && containerType!=null) {

				try {
					containerAssignmentVO = defaultsDelegate.findLatestContainerAssignment(containerNum);
				} catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}



		}
		Collection<ContainerDetailsVO> cntdlsVOs = mailArrivalVO.getContainerDetails();
		if(cntdlsVOs != null && !cntdlsVOs.isEmpty()){
			for(ContainerDetailsVO dtlVO : cntdlsVOs){
				if(!"B".equals(mailArrivalForm.getContainerType()) && dtlVO.getContainerNumber() != null && dtlVO.getContainerNumber().trim().length() > 0 &&
						dtlVO.getPol() != null && dtlVO.getPol().trim().length() > 0){
					if(dtlVO.getContainerNumber().equals(mailArrivalForm.getContainerNo()) &&
							!(dtlVO.getPol().equals(mailArrivalForm.getPol()))){
						invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.containerexistalready"));
						invocationContext.target = TARGET;
						return;
					}
				}
			}
		}
		
		//added by A-7540 for ICRD-274933 starts
    	Map systemParameters = null;  
        SharedDefaultsDelegate sharedDelegate =new SharedDefaultsDelegate();
        try {
        	systemParameters=sharedDelegate.findSystemParameterByCodes(getSystemParameterCodes());
        } catch (BusinessDelegateException e) {
               e.getMessage();
        }//added by A-7540 for ICRD-274933 ends
        AreaDelegate areaDelegate = new AreaDelegate();
		Map stationParameters = null; 
	    	String stationCode = logonAttributes.getStationCode();
    	String companyCode=logonAttributes.getCompanyCode();
    	try {
			stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
		} catch (BusinessDelegateException e1) {			
			e1.getMessage();
		}
    	if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
    		mailArrivalForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));
    		}
    		else{
    			mailArrivalForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
    		}
    	//added by A-7540 for ICRD-274933
		//Modified by A-7794 as part of ICRD-197439
		if(Objects.equals(mailArrivalForm.getPol(), mailArrivalForm.getArrivalPort())){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.err.invalidflightsegment"));
			invocationContext.target = TARGET;
			return;
		}
		
		 FlightValidationVO flightValidationVO = mailArrivalSession.getFlightValidationVO();
		 String route = flightValidationVO.getFlightRoute();
		if((mailArrivalForm.getPol() == null || mailArrivalForm.getPol().trim().length()==0) && contDtlVO!=null && contDtlVO.getPol()!=null )
			{
			mailArrivalForm.setPol(contDtlVO.getPol());
			}
		 
		else if(mailArrivalForm.getPol() == null && mailArrivalForm.getPol().trim().length()==0){
			
			if(flightValidationVO!=null && flightValidationVO.getFlightRoute()!=null && flightValidationVO.getFlightRoute().trim().length()>0){
				
				 String[] routeArr = route.split("-");
				 String pol = "";
				 for(int i=routeArr.length-1;i>=0;i--){
					//Modified by A-7794 as part of ICRD-197439
					 if( Objects.equals(routeArr[i], mailArrivalForm.getArrivalPort())){
						 pol = routeArr[i-1];
						 break;
					 }
				 }
				mailArrivalForm.setPol(pol);
			}
		}
		if("B".equals(containerType)){
			containerNum = new StringBuilder("BULK-").append(mailArrivalVO.getAirportCode()).toString();
			mailArrivalForm.setContainerNo(containerNum);
		}

		if(containerNum == null || ("".equals(containerNum.trim()))){
		    invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.containernum.empty"));
		    mailArrivalForm.setIsContainerValidFlag(FlightDetailsVO.FLAG_NO);
	    }
	//Added as part of bug ICRD-114395 by A-5526 starts
		else if(containerAssignmentVO!=null &&
				containerType.equals(containerAssignmentVO.getContainerType()) &&
				MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())&&
				containerAssignmentVO.getFlightDate()!=null){
			//Modified by A-7794 as part of ICRD-197439
			if(Objects.equals(containerAssignmentVO.getAirportCode(), mailArrivalForm.getArrivalPort())
					|| (MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getArrivalFlag()) && !(mailArrivalForm.getArrivalPort().equals(containerAssignmentVO.getAirportCode()))))
			{


			Object[] obj = {new StringBuilder(containerAssignmentVO.getCarrierCode())
			 .append("").append(containerAssignmentVO.getFlightNumber()).append(" ").toString(),
			 containerAssignmentVO.getFlightDate().toDisplayDateOnlyFormat()};
	         invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.containernum.isalreadyinanoutboundflight",obj));
		     mailArrivalForm.setIsContainerValidFlag("E");     
		      
			}

		}
		//Added as part of bug ICRD-114395 by A-5526 ends
			//Removed the code to fix ICRD-147838

		else{
    	// Putting selected ContainerNo Details VO into session...
		int flag = 0;
    	containerNum = mailArrivalForm.getContainerNo().trim().toUpperCase();

    	if("U".equals(containerType)){

    		String error="ERROR";
	    	ULDDelegate uldDelegate = new ULDDelegate();
	    	//MailTrackingDefaultsDelegate defaultsDelegate = new MailTrackingDefaultsDelegate();
	    	FlightDetailsVO flightDetails = null;
			Collection<ULDInFlightVO> uldInFlightVos = new ArrayList<ULDInFlightVO>();
			ULDInFlightVO uldInFlightVo = new ULDInFlightVO();
			flightDetails = new FlightDetailsVO();
			flightDetails.setCompanyCode(logonAttributes.getCompanyCode());
			//Modified by A-7794 as part of ICRD-197439
			flightDetails.setCurrentAirport(mailArrivalForm.getArrivalPort());
			flightDetails.setDirection(MailConstantsVO.IMPORT);
			flightDetails.setFlightNumber(mailArrivalVO.getFlightNumber());
			flightDetails.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
			flightDetails.setFlightCarrierIdentifier(mailArrivalVO.getCarrierId());
			flightDetails.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
			flightDetails.setFlightRoute(route); //Added by A-6991 for ICRD-243040
			flightDetails.setSubSystem("M"); //Added by A-7794 for ICRD-297323
			

			uldInFlightVo.setUldNumber(containerNum);
			uldInFlightVo.setPointOfLading(mailArrivalForm.getPol());
			//Modified by A-7794 as part of ICRD-197439
			uldInFlightVo.setPointOfUnLading(mailArrivalForm.getArrivalPort());
			uldInFlightVo.setRemark(MailConstantsVO.MAIL_ULD_ARRIVED);
			uldInFlightVos.add(uldInFlightVo);
					flightDetails.setUldInFlightVOs(uldInFlightVos);
					flightDetails.setAction(FlightDetailsVO.ARRIVAL);
			try {
				uldDelegate.validateULD(logonAttributes.getCompanyCode(),containerNum);

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

			if (errors != null && errors.size() > 0) {
				invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.invaliduldnumber",
		   				new Object[]{containerNum}));
				mailArrivalForm.setIsContainerValidFlag(FlightDetailsVO.FLAG_NO);
		  		invocationContext.target = TARGET;
		  		ContainerDetailsVO containerDtlsVO = mailArrivalSession.getContainerDetailsVO();
		  		containerDtlsVO.setContainerType(mailArrivalForm.getContainerType());
		  		mailArrivalSession.setContainerDetailsVO(containerDtlsVO);
		  		return;
			}
			Map<String, String> results = null;
			Collection<String> codes = new ArrayList<String>();
	      	codes.add(MailConstantsVO.ULD_INTEGRATION_ENABLED);
			boolean isUldIntEnable=false;
	      	try {
	      		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
	      	} catch(BusinessDelegateException businessDelegateException) {
	      		handleDelegateException(businessDelegateException);
	      	}
	      	if(results != null && results.size() > 0) {
	      		if(results.containsKey(MailConstantsVO.ULD_INTEGRATION_ENABLED)){
	      			if("Y".equals(results.get(MailConstantsVO.ULD_INTEGRATION_ENABLED)))
	      				{
	      				isUldIntEnable=true;
	      		}
	      	}
	      	}
	      	if(isUldIntEnable)
			{
			try {
				defaultsDelegate.validateULDsForOperation(flightDetails);
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				}
			}
			if (errors != null && errors.size() > 0) {
									
				invocationContext.addAllError(errors);
		  		invocationContext.target = TARGET;
		  	//Changed as part of Bug ICRD-94272 by A-5526 starts
		  		for (ErrorVO errorVO : errors) {

		  			if(errorVO.getErrorDisplayType()!=null && error.equals(errorVO.getErrorDisplayType().toString())){

		  				mailArrivalForm.setIsContainerValidFlag("E");  // If 'uld.defaults.errortype.notinstock' =E then need to throw the error message
		  			}else
		  			{
		  				mailArrivalForm.setIsContainerValidFlag("W");    // If 'uld.defaults.errortype.notinstock' =E then need to throw the warning message

		  			}
		  		}
		  	//Changed as part of Bug ICRD-94272 by A-5526 ends
		  		ContainerDetailsVO containerDtlsVO = mailArrivalSession.getContainerDetailsVO();
		  		containerDtlsVO.setContainerType(mailArrivalForm.getContainerType());
		  		mailArrivalSession.setContainerDetailsVO(containerDtlsVO);
		  		return;
			}
    	}

    	ContainerDetailsVO selContainerDtlsVO = new ContainerDetailsVO();

    	//STEP 1
    	Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalSession.getContainerDetailsVOs();
    	Collection<ContainerDetailsVO> mainContDtlsVOs = mailArrivalVO.getContainerDetails();
    	log.log(Log.FINE, "*containerDetailsVOs**", containerDetailsVOs);
		try{
        	if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
        		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
        			log.log(Log.FINE, "****containerNum***", containerNum);
					log.log(Log.FINE,
							"***containerDtlsVO.getContainerNumber()*",
							containerDtlsVO.getContainerNumber());
					log.log(Log.FINE, "**mailArrivalForm.getPol()**",
							mailArrivalForm.getPol());
					log.log(Log.FINE, "**containerDtlsVO.getPol()**",
							containerDtlsVO.getPol());
					if(containerNum.equals(containerDtlsVO.getContainerNumber())
        					&& mailArrivalForm.getPol().equals(containerDtlsVO.getPol())){
        					flag = 1;
            				BeanHelper.copyProperties(selContainerDtlsVO,containerDtlsVO);
            				mailArrivalForm.setContainerNo(selContainerDtlsVO.getContainerNumber());
        					mailArrivalForm.setDisableFlag("Y");
            		   		log.log(Log.FINE, "*******STEP 1***");
        			}
        		}
        	}

        	if(flag == 0){
        		log.log(Log.FINE, "**mainContDtlsVOs**", mainContDtlsVOs);
				if(mainContDtlsVOs != null && mainContDtlsVOs.size() > 0){
            		for(ContainerDetailsVO containerDtlsVO:mainContDtlsVOs){
            			if(containerNum.equals(containerDtlsVO.getContainerNumber())
            					&& mailArrivalForm.getPol().equals(containerDtlsVO.getPol())){
            				flag = 1;
                    		BeanHelper.copyProperties(selContainerDtlsVO,containerDtlsVO);
                    		selContainerDtlsVO.setOperationFlag("U");
            			}
            			//Added by A-4809 for BUG ICMN-3528 --->Starts
            			Collection<MailbagVO> mailbagVOs = containerDtlsVO.getMailDetails();
            			Collection<DespatchDetailsVO> despatchDetailsVOs = containerDtlsVO.getDesptachDetailsVOs();
            			for(MailbagVO mailbagVO:mailbagVOs){
            				if(mailbagVO.getOperationalFlag()== null
            						||(MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag()))){
            					mailbagVO.setDisplayLabel("Y");
            					log.log(Log.FINE, "**DisplayLabel**", mailbagVO.getDisplayLabel());
            				}
            			}
            			for(DespatchDetailsVO despatchDetailsVO:despatchDetailsVOs){
            				if(despatchDetailsVO.getOperationalFlag()== null
            						||(DespatchDetailsVO.OPERATION_FLAG_INSERT.equals(despatchDetailsVO.getOperationalFlag()))){
            					despatchDetailsVO.setDisplayLabel("Y");
            				}
            			}
            			//Added by A-4809 for BUG ICMN-3528 --->Ends
            		}
            	}
        		if(flag == 1){

        			if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
        				mailArrivalForm.setContainerNo(selContainerDtlsVO.getContainerNumber());
        	       		mailArrivalForm.setDisableFlag("Y");
        	       		if(!"I".equals(selContainerDtlsVO.getOperationFlag())){
        	       			selContainerDtlsVO.setOperationFlag("U");
        	       		}

        				Collection<MailbagVO> mailbagVOs = selContainerDtlsVO.getMailDetails();
       	       			if (mailbagVOs != null && mailbagVOs.size() != 0) {
       	       				for (MailbagVO mailbagVO: mailbagVOs) {
       	       					String mailId = mailbagVO.getMailbagId();
       	       					if(mailId.length()==29){
       	       					double displayWt=Double.parseDouble(mailId.substring(mailId.length()-4,mailId.length()));
   	       						Measure strWt=new Measure(UnitConstants.MAIL_WGT,displayWt/10);
   	       					mailbagVO.setStrWeight(strWt);//added by A-7371
       	       					}
       	       				}
       	       			}

        				containerDetailsVOs.add(selContainerDtlsVO);
        			}else{
        				//added by anitha
        				mailArrivalForm.setDisableFlag("Y");
        				if(selContainerDtlsVO.getMailDetails()!=null){
        				Collection<MailbagVO> mailbagVOs = selContainerDtlsVO.getMailDetails();
       	       			if (mailbagVOs != null && mailbagVOs.size() != 0) {
       	       				for (MailbagVO mailbagVO: mailbagVOs) {
       	       					String mailId = mailbagVO.getMailbagId();
       	       					if(mailId.length()==29){
       	       						double displayWt=Double.parseDouble(mailId.substring(mailId.length()-4,mailId.length()));
       	       						Measure strWt=new Measure(UnitConstants.MAIL_WGT,displayWt/10);
       	       					mailbagVO.setStrWeight(strWt);//added by A-7371
       	       					}
       	       				}
       	       			}
        			}}
        		}
        	}
        }catch (SystemException e) {
        	log.log(Log.FINE,  "Sys.Excptn ");
    	}

        if(flag == 0){
        	log.log(Log.FINE, "*******WARNING***");
        	//Changed as part of Bug ICRD-94272 by A-5526 starts

            if(MailConstantsVO.BULK_TYPE.equals(containerType)){//If bulk no need to show the ULD present or not warning message
        		mailArrivalForm.setDisableFlag("N");
        	}else if("W".equals(mailArrivalForm.getIsContainerValidFlag())){  //Else if 'uld.defaults.errortype.notinstock'=W then need to show the warning for ULDs
        	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.invaliduld"));
        	} else{
        	mailArrivalForm.setDisableFlag("N");    //else if ULD is present in the system(It is listing when listed in ULD013 screen) no need to show the warning .allow to save mailbags
        	}
        	//Changed as part of Bug ICRD-94272 by A-5526 ends
        	selContainerDtlsVO.setOperationFlag("I");
        	selContainerDtlsVO.setPol(mailArrivalForm.getPol());
        	selContainerDtlsVO.setPaBuiltFlag(mailArrivalForm.getPaBuilt());
    		String remarks = mailArrivalForm.getRemarks();
    		if (remarks != null && remarks.trim().length() > 0) {
    			selContainerDtlsVO.setRemarks(mailArrivalForm.getRemarks());
    		}
        }
        selContainerDtlsVO.setContainerType(mailArrivalForm.getContainerType());
        mailArrivalSession.setContainerDetailsVO(selContainerDtlsVO);
        mailArrivalSession.setContainerDetailsVOs(containerDetailsVOs);
		}
		log.log(Log.FINE, "*******WARNING***", mailArrivalSession.getContainerDetailsVO());
		if("".equals(mailArrivalForm.getIsContainerValidFlag())){
			mailArrivalForm.setIsContainerValidFlag(FlightDetailsVO.FLAG_YES);
			mailArrivalForm.setDisableFlag(FlightDetailsVO.FLAG_YES);
		}

		invocationContext.target = TARGET;
    	log.exiting("ListArriveMailCommand","execute");

    }

    
    /**
	 * 
	 * @return
	 */
    private Collection<String> getSystemParameterCodes(){
             Collection<String> systemParameterCodes = new ArrayList<String>();
               systemParameterCodes.add(SYSPAR_DEFUNIT_WEIGHT);
               return systemParameterCodes;
      }
    private Collection<String> getStationParameterCodes(){
        Collection<String> systemParameterCodes = new ArrayList<String>();
          systemParameterCodes.add(STNPAR_DEFUNIT_WEIGHT);
          
          return systemParameterCodes;
 }
}
