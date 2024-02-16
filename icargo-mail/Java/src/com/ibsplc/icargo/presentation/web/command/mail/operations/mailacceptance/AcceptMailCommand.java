/*
 * AcceptMailCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.server.framework.vo.LogonAttributesVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
/**
 * @author A-5991
 * 
 */
public class AcceptMailCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	/**
	 * TARGET
	 */
	private static final String TARGET = "success";
	private static final String FAILURE = "failure";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
	private static final String HYPHEN = "-";
	private static final String ERROR_DMG_REASON_MANDATORY = "mailtracking.defaults.err.reasonfordamagemandatory";
	private static final String WARN_COTERMINUS="mailtracking.defaults.war.coterminus";
	private static final String COTERMINUS_STATUS="coterminus";
	private static final String LATVALIDATION_STATUS="latvalidation";
	private static final String SCREEN_ID_VOID= "MTK002";
	private static final String VOID_MAILBAGS = "mailtracking.operations.mailacceptance.voidmailbags";
	/**
	 * This method overrides the executre method of BaseComand class
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("AcceptMailCommand","execute");

    	MailAcceptanceForm mailAcceptanceForm =
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

   	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();

		if("FLIGHT".equals(mailAcceptanceForm.getAssignToFlight())){
			if((mailAcceptanceForm.getPou() == null || 
						mailAcceptanceForm.getPou().trim().length() == 0)){ 
			invocationContext.addError(new ErrorVO("mailtracking.defaults.pou.empty"));
			invocationContext.target = TARGET;
		  		return;
			}
			if("U".equals(mailAcceptanceForm.getContainerType()) && (mailAcceptanceForm.getDestn()==null ||
					mailAcceptanceForm.getDestn().trim().length()==0)){
				invocationContext.addError(new ErrorVO("mailtracking.defaults.destn.empty"));
				invocationContext.target = TARGET;
		  		return;
			}
			if("B".equals(mailAcceptanceForm.getContainerType())){
				if(! (mailAcceptanceForm.getPou().equals(mailAcceptanceForm.getDestn()))){
					invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destnandpouisnotsame"));
					invocationContext.target = TARGET;
			  		return;
				}
			}
		}				
			
		ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();
    	Collection<MailbagVO> newMailbagVOs =  containerDetailsVO.getMailDetails();
    	Collection<MailbagVO> updatedMailbagVOs = new ArrayList<MailbagVO>(); 
    	
    	if(newMailbagVOs != null && newMailbagVOs.size() > 0) {
			  for(MailbagVO newMailbagVO:newMailbagVOs) {
                  //Added by A-5945 for ICRD-93738 starts
				  String wgt = String.valueOf(newMailbagVO.getStrWeight().getDisplayValue()); //modified by a-7371
				  log.log(Log.FINE, "wt ...in command", wgt);
				 	String stdwgt = wgt;
					if(wgt.length() == 3){
						stdwgt = new StringBuilder("0").append(wgt).toString();  
					}
					if(wgt.length() == 2){
						stdwgt = new StringBuilder("00").append(wgt).toString();
					}
					if(wgt.length() == 1){
						stdwgt = new StringBuilder("000").append(wgt).toString();
					}  
                 //Added by A-5945 for ICRD-93738 ends 
			    /*String mailId = new StringBuilder()
			            .append(newMailbagVO.getOoe())
			            .append(newMailbagVO.getDoe())
						.append(newMailbagVO.getMailCategoryCode())
						.append(newMailbagVO.getMailSubclass())
						.append(newMailbagVO.getYear())
						.append(newMailbagVO.getDespatchSerialNumber())
						.append(newMailbagVO.getReceptacleSerialNumber())
						.append(newMailbagVO.getHighestNumberedReceptacle())
						.append(newMailbagVO.getRegisteredOrInsuredIndicator())
						.append(stdwgt).toString();*/
			    String dsnId = new StringBuilder()
	            .append(newMailbagVO.getOoe())
	            .append(newMailbagVO.getDoe())
				.append(newMailbagVO.getMailCategoryCode())
				.append(newMailbagVO.getMailSubclass())
				.append(newMailbagVO.getYear())
				.append(newMailbagVO.getDespatchSerialNumber()).toString();			    
			    newMailbagVO.setMailbagId(newMailbagVO.getMailbagId());//Modified for ICRD-205027
			    newMailbagVO.setDespatchId(dsnId);
			     newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
			    	newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
			    	newMailbagVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
			    	newMailbagVO.setFlightDate(mailAcceptanceVO.getFlightDate());
			    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
			    	if(MailConstantsVO.FLAG_YES.equals(newMailbagVO.getArrivedFlag())){
			    		//No need to update  
			    	}else{
			    	newMailbagVO.setArrivedFlag("N");
			    	}
			    	newMailbagVO.setDeliveredFlag("N");
			    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
			    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			    	newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
			    	newMailbagVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());			    	
			    	newMailbagVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());       			    	
			    	newMailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
			    	newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
			    	newMailbagVO.setContainerType(containerDetailsVO.getContainerType());
			    	newMailbagVO.setPou(containerDetailsVO.getPou());
			  }
			}
    	//added for icrd-85496 by a-4810 begins
    	if(MailbagVO.FLAG_YES.equals(mailAcceptanceForm.getModify()) && containerDetailsVO.getDeletedMailDetails() != null &&
    			!containerDetailsVO.getDeletedMailDetails().isEmpty()){
    		
	    		for(MailbagVO mailVO : newMailbagVOs){
	    			if(!MailbagVO.OPERATION_FLAG_DELETE.equals(mailVO.getOperationalFlag())){
	    				updatedMailbagVOs.add(mailVO);
	    			}
	    		}
	    		for(MailbagVO delmailVO : containerDetailsVO.getDeletedMailDetails()){
	    			for(MailbagVO mailVO : newMailbagVOs){
	    			if(MailbagVO.OPERATION_FLAG_DELETE.equals(delmailVO.getOperationalFlag()) && MailbagVO.OPERATION_FLAG_INSERT.equals(mailVO.getOperationalFlag()) &&
	    					delmailVO.getMailbagId() != null && delmailVO.getMailbagId().equals(mailVO.getNewMailId())){
	    				delmailVO.setNewMailId(mailVO.getMailbagId());
	    				if(delmailVO.getContainerNumber() == null){
	    					delmailVO.setContainerNumber(mailAcceptanceForm.getContainerNo());
	    					delmailVO.setContainerType(mailAcceptanceForm.getContainerType());
	        			}
	    			}
	    		}
    		}
    		newMailbagVOs = updatedMailbagVOs;
    	}
    	if(newMailbagVOs != null && newMailbagVOs.size() > 0) {
    	  for(MailbagVO newMailbagVO:newMailbagVOs) {
	    	 if(newMailbagVO.getDamageFlag() != null && MailbagVO.FLAG_YES.equals(newMailbagVO.getDamageFlag())
 	    			 && newMailbagVO.getOperationalFlag()!=null && (MailbagVO.OPERATION_FLAG_INSERT.equals(newMailbagVO.getOperationalFlag())
 	    			 || MailbagVO.OPERATION_FLAG_UPDATE.equals(newMailbagVO.getOperationalFlag()))) {
				 if(newMailbagVO.getDamagedMailbags() == null ||  newMailbagVO.getDamagedMailbags().size() == 0){
							 invocationContext.addError(new ErrorVO(ERROR_DMG_REASON_MANDATORY)); 
							 break;
				 }
			 }
    	  }
    	}
    	//added for icrd-85496 by a-4810 ends
  	    containerDetailsVO.setMailDetails(newMailbagVOs);
    	mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);
      
    	if(invocationContext.getErrors() != null && invocationContext.getErrors().size() >0 ) {
	  		mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
    	}	

   	  //validate Location - Warehouse
    	String location = mailAcceptanceForm.getLocation();
    	if (location != null && location.trim().length() > 0) {
    	log.log(Log.FINE, "Going To validate Location - Warehouse ...in command");
    	LocationValidationVO locationValidationVO = new LocationValidationVO();
		  try {
			  locationValidationVO = new MailTrackingDefaultsDelegate().validateLocation(
					logonAttributes.getCompanyCode(),logonAttributes.getAirportCode(),
					mailAcceptanceForm.getWarehouse(),location.toUpperCase());
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (locationValidationVO == null) {
	  		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invalidlocation"));
	  		mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	  }
    	}
    	
//    	validate PA code
    	String invalidGeneralPACode = "";
    		try {
		  			String paCode = mailAcceptanceForm.getPaCode().toUpperCase();
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();

	  				postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
	  								logonAttributes.getCompanyCode(),paCode);

		  			if (postalAdministrationVO == null) {
		  				if("".equals(invalidGeneralPACode)){
		  					invalidGeneralPACode = paCode;
		  				}else{
		  					invalidGeneralPACode = new StringBuilder(invalidGeneralPACode).append(",").append(paCode).toString();
		  				}
		  			
		  		}
	  		}catch (BusinessDelegateException businessDelegateException) {
  				errors = handleDelegateException(businessDelegateException);
  			}
	  	
	  	if(!"".equals(invalidGeneralPACode)){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidpacode",
	   				new Object[]{invalidGeneralPACode}));
			mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
		}
    	
    	

//    	validate PA code
    	String invalidPACode = "";
    	Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
	  	log.log(Log.FINE, "Going To validate PA code ...in command");
	  	if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
	  		try {
		  		for(DespatchDetailsVO despatchVO:despatchDetailsVOs){
		  			String paCode = despatchVO.getPaCode().toUpperCase();
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();

	  				postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
	  								logonAttributes.getCompanyCode(),paCode);

		  			if (postalAdministrationVO == null) {
		  				if("".equals(invalidPACode)){
		  					invalidPACode = paCode;
		  				}else{
		  					invalidPACode = new StringBuilder(invalidPACode).append(",").append(paCode).toString();
		  				}
		  			}
		  		}
	  		}catch (BusinessDelegateException businessDelegateException) {
  				errors = handleDelegateException(businessDelegateException);
  			}
	  	}
	  	if(!"".equals(invalidPACode)){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidpacode",
	   				new Object[]{invalidPACode}));
			mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
		}
	  	
	  	/*
	  	 * Validating Consignment Date and Stated Weight for a Despatch 
	  	 */
	  	log.log(Log.FINE, "Going To validate Consignment Date and Stated Weight for a Despatch...in command");
	  	if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
	  		Collection<DespatchDetailsVO> despatchDetails = new ArrayList<DespatchDetailsVO>();
	  		Collection<DespatchDetailsVO> despatchesWithWrongCsgDtls = null;
	  		try { 
	  			for(DespatchDetailsVO despatchDtl : despatchDetailsVOs) {
	  				if(despatchDtl.getConsignmentNumber() != null && 
	  						despatchDtl.getConsignmentNumber().trim().length() > 0) {
	  					despatchDetails.add(despatchDtl);
	  				}
	  			}
	  			if(despatchDetails.size() > 0) {
	  				despatchesWithWrongCsgDtls = new MailTrackingDefaultsDelegate().validateConsignmentDetails(logonAttributes.getCompanyCode(),despatchDetails);
	  			}
	  			if(despatchesWithWrongCsgDtls != null && despatchesWithWrongCsgDtls.size() > 0) {
	  				Collection<ErrorVO> despatchCSGDetailErrors = new ArrayList<ErrorVO>();
	  				StringBuilder dsns = new StringBuilder();
	  				boolean first = true;
	  				for(DespatchDetailsVO wrongDespatchCsgDtl : despatchesWithWrongCsgDtls) {
	  					if(first) {
		  					dsns.append(wrongDespatchCsgDtl.getDsn());
	  						first = false;
	  					}else {
		  					dsns.append(",").append(wrongDespatchCsgDtl.getDsn());
	  					}
	  				}
	  				if(dsns.length() > 0) {
	  					despatchCSGDetailErrors.add(new ErrorVO("mailtracking.defaults.acceptmail.wrongconsignmentDetails",
  							new Object[] {dsns.toString()}));
	  				}
	  				if (despatchCSGDetailErrors != null && despatchCSGDetailErrors.size() > 0) {
	  			  		invocationContext.addAllError(despatchCSGDetailErrors);
	  			  		mailAcceptanceForm.setPopupCloseFlag("N");
	  			  		invocationContext.target = TARGET;
	  			  		return;
	  			  	}
	  			}
			} catch (BusinessDelegateException  businessDelegateException) {
  				errors = handleDelegateException(businessDelegateException);
  			}
	  	}
//    	validate class and subclass
    	String invalidSubClass = "";
    	Collection<DespatchDetailsVO> desDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
	  	log.log(Log.FINE, "Going To validate class and subclass ...in command");
	  	if(desDetailsVOs != null && desDetailsVOs.size() > 0){
		  		for(DespatchDetailsVO despatchVO:desDetailsVOs){
		  			if(despatchVO.getMailClass()!=null && despatchVO.getMailClass().trim().length()>0
		  					&& despatchVO.getMailSubclass()!=null && despatchVO.getMailSubclass().trim().length()>0){
		  			String mailclass = despatchVO.getMailClass().toUpperCase();
		  			String mailsubclass = despatchVO.getMailSubclass().toUpperCase();
		  			if(!mailsubclass.startsWith(mailclass)){
		  				invalidSubClass=mailsubclass;
		  			}}
		  		}
	  	}
	  	if(!"".equals(invalidSubClass)){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidsubclass"));
			mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
		}


    	//validate Mail bags
    	log.log(Log.FINE, "Going To validate Mail bags ...in command");

    	Collection<MailbagVO> mailbgVOs = containerDetailsVO.getMailDetails();
    	Collection<MailbagVO> newMailbgVOs = new ArrayList<MailbagVO>();
    	if(mailbgVOs != null && mailbgVOs.size() > 0){
	  		for(MailbagVO mailbagVO:mailbgVOs){
	  			if("I".equals(mailbagVO.getOperationalFlag())
	  				|| "U".equals(mailbagVO.getOperationalFlag())){
	  				newMailbgVOs.add(mailbagVO);
	  			}
	  		}
	  	}
		  try {
		    new MailTrackingDefaultsDelegate().validateMailBags(newMailbgVOs);
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (errors != null && errors.size() > 0) {
	  		invocationContext.addAllError(errors);
	  		mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	  }

	  	boolean isScanned=false;
	  	
	  	try {
	  		if(!mailAcceptanceForm.isCanDiscardLATValidation()){
	  			mailAcceptanceForm.setWarningStatus(LATVALIDATION_STATUS);
		    new MailTrackingDefaultsDelegate().doLATValidation(newMailbgVOs,isScanned);
	  		}
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	if (errors != null && errors.size() > 0) {
	  		invocationContext.addAllError(errors);
	  		mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	}
	  	//added by A-7371 as part of ICRD-273840 starts
	  	String isCoterminusConfigured=null;
	  	try {
			 isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
		} catch (BusinessDelegateException businessDelegateException) {
			// TODO Auto-generated catch block
			errors = handleDelegateException(businessDelegateException);
		}
	  	if(MailConstantsVO.FLAG_YES.equals(isCoterminusConfigured)&& !mailAcceptanceForm.isCanDiscardCoterminus()){
	  	Collection<MailbagVO> mailbagVOsForValidation = containerDetailsVO.getMailDetails();
	  	if(mailbagVOsForValidation != null && mailbagVOsForValidation.size() > 0){
	  		for(MailbagVO mailbagVO:mailbagVOsForValidation){

					if (MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
  				String ooe = mailbagVO.getOoe();
		    	//String doe = mailbagVO.getDoe();
  				 boolean isCoterminus=false;
						Page<OfficeOfExchangeVO> orginAirport = null;
				try {
					orginAirport = new MailTrackingDefaultsDelegate().findOfficeOfExchange(
							mailbagVO.getCompanyCode(),ooe,1);
				} catch (BusinessDelegateException businessDelegateException) {
					// TODO Auto-generated catch block
					errors = handleDelegateException(businessDelegateException);
				}
				OfficeOfExchangeVO officeOfExchangeVO=new OfficeOfExchangeVO();
				if(orginAirport!=null && !orginAirport.isEmpty()){
					officeOfExchangeVO = orginAirport.iterator().next();
				}
				//added by A-8353 for ICRD-325826 starts
				if(officeOfExchangeVO.getAirportCode()==null){
			     Collection<ArrayList<String>> oECityArpCodes = null;
				 Collection<String> gpaCode = new ArrayList<String>();
				 String airportCode = null;
				gpaCode.add(officeOfExchangeVO.getCode());
				try{     
	    			oECityArpCodes=  new MailTrackingDefaultsDelegate().findCityAndAirportForOE(logonAttributes.getCompanyCode(), gpaCode);

	    		}
	    		catch (BusinessDelegateException businessDelegateException) {
	    			Collection<ErrorVO> err = handleDelegateException(businessDelegateException);
	    			log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
	    		}
				if(oECityArpCodes != null && oECityArpCodes.size() > 0){
					for(ArrayList<String> cityAndArpForOE : oECityArpCodes) {
						airportCode=cityAndArpForOE.get(2);   
					}
				}
				officeOfExchangeVO.setAirportCode(airportCode);
				}//added by A-8353 for ICRD-325826 ends
						if (!logonAttributes.getAirportCode().equals(officeOfExchangeVO.getAirportCode())) {
		    	try {
		    		
			  		//if(!mailAcceptanceForm.isCanDiscardCoterminus()){
			  			mailAcceptanceForm.setWarningStatus(COTERMINUS_STATUS);
			  			LocalDate dspDate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
						   isCoterminus=  new MailTrackingDefaultsDelegate().validateCoterminusairports(officeOfExchangeVO.getAirportCode(),logonAttributes.getAirportCode(),MailConstantsVO.RESDIT_ASSIGNED,officeOfExchangeVO.getPoaCode(),dspDate);
								   if(isCoterminus){
				    		continue;
				    	}else{
				    		errors.add(new ErrorVO(WARN_COTERMINUS));
				    		break;
				    	}
			  		//}
		        }catch (BusinessDelegateException businessDelegateException) {
		  			errors = handleDelegateException(businessDelegateException);
			  	  }
						}
					}
			    	  }
			       }
	  	}
	  //added by A-7371 as part of ICRD-273840 ends   
	  	if (errors != null && errors.size() > 0) {
	  		invocationContext.addAllError(errors);
	  		mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	}
//			Check for same OOE and DOE for despatches
			int sameOE = 0;
			Collection<DespatchDetailsVO> despatchVOs = containerDetailsVO.getDesptachDetailsVOs();
			if(despatchVOs != null && despatchVOs.size() > 0){
		  		for(DespatchDetailsVO despatchVO:despatchVOs){
	  				String ooe = despatchVO.getOriginOfficeOfExchange();
			    	String doe = despatchVO.getDestinationOfficeOfExchange();
			    	if(ooe.trim().length() == 6){
				      if(doe.trim().length() == 6){
				        if (ooe.equals(doe)) {
			        	   sameOE = 1;
			        	   String pk = new StringBuilder(despatchVO.getOriginOfficeOfExchange())
	        							.append(despatchVO.getDestinationOfficeOfExchange())
	        							//.append(despatchVO.getMailClass())
	        							//added by anitha for change in pk
	        							.append(despatchVO.getMailCategoryCode())
	        							.append(despatchVO.getMailSubclass())
	        							.append(despatchVO.getYear())
	        							.append(despatchVO.getDsn()).toString();
			        			 invocationContext.addError(new ErrorVO("mailtracking.defaults.sameoe",new Object[]{pk}));
			        	}
				      }
				    }
		  		}
		  	}
			if(sameOE == 1){
				mailAcceptanceForm.setPopupCloseFlag("N");
				invocationContext.target = TARGET;
		  		return;
			}

//	  	Check for same OOE and DOE for mail bags
		sameOE = 0;
		Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
		if(mailbagVOs != null && mailbagVOs.size() > 0){
	  		for(MailbagVO mailbagVO:mailbagVOs){
  				String ooe = mailbagVO.getOoe();
		    	String doe = mailbagVO.getDoe();
		    	if(ooe.trim().length() == 6){
			       if(doe.trim().length() == 6){
			    	  if (ooe.equals(doe)) {
		        		 sameOE = 1;
		        		 invocationContext.addError(new ErrorVO("mailtracking.defaults.sameoe",new Object[]{mailbagVO.getMailbagId()}));
			    	  }
			       }
			    }
	  		}
	  	}
		if(sameOE == 1){
			mailAcceptanceForm.setPopupCloseFlag("N");
			invocationContext.target = TARGET;
	  		return;
		}


    //Duplicate check for Mail bags
	  	Collection<MailbagVO> firstMailbagVOs = containerDetailsVO.getMailDetails();
	  	Collection<MailbagVO> secMailbagVOs = containerDetailsVO.getMailDetails();
	  	int duplicate = 0;
	  	if(firstMailbagVOs != null && firstMailbagVOs.size() > 0){
	  		int count = 0;
	  		for(MailbagVO fstMailbagVO:firstMailbagVOs){
	  			for(MailbagVO secMailbagVO:secMailbagVOs){
	  				if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId()) &&
	  						!"D".equals(secMailbagVO.getOperationalFlag())){             
	  					count++;
	  				}
	  				if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId()) &&
	  						"D".equals(secMailbagVO.getOperationalFlag())&&
	  						"I".equals(fstMailbagVO.getOperationalFlag())){                
	  					
	  					fstMailbagVO.setOperationalFlag("U"); 
	  					secMailbagVO.setAcknowledge("D");          
	  					
	  				}
	  				
	  			}
	  			if(count > 1){
	  				duplicate = 1;
	  				
	  				break;
	  			}
	  			count = 0;
	  		}
	  	}
	  	if(duplicate == 1){
	  		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.duplicatemailbag"));
	  		mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	}

//		 checking duplicate mailbags across containers
	  	int duplicateAcross = 0;
	  	String container = "";
	  	String mailId = "";
	  	MailAcceptanceVO mailAcceptVO = mailAcceptanceSession.getMailAcceptanceVO();
	  	log.log(Log.FINE, "**********mailAcceptVO***********:::", mailAcceptVO);
	  	Collection<ContainerDetailsVO> allContainerDtlVOs = mailAcceptVO.getContainerDetails();
	  	log.log(Log.FINE, "**********mailAcceptVO -all containers***********:::", allContainerDtlVOs);
		//ADDED FOR BUG 87046 STARTS
    	Collection<ContainerDetailsVO> containerDtlVOs =  mailAcceptanceSession.getContainerDetailsVOs();
log.log(Log.FINE, "*********containerDtlVOs*********:::",
				containerDtlVOs);
if(allContainerDtlVOs!=null && allContainerDtlVOs.size()>0){
		if(containerDtlVOs != null && containerDtlVOs.size() > 0){
		for(ContainerDetailsVO cntVO :allContainerDtlVOs){
			Collection<MailbagVO> mailBagsinCont = cntVO.getMailDetails();
			if(mailBagsinCont != null && mailBagsinCont.size() > 0){
				for(MailbagVO fstMailbagVO:mailBagsinCont){
    				for(ContainerDetailsVO popupVO:containerDtlVOs){
    					if(!fstMailbagVO.getContainerNumber().equals(popupVO.getContainerNumber())){
    						Collection<MailbagVO> secMailVOs = popupVO.getMailDetails();
    						if(secMailVOs != null && secMailVOs.size() > 0){
    							for(MailbagVO secMailbagVO:secMailVOs){
    								if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId())){
    									duplicateAcross = 1;
										container = cntVO.getContainerNumber();
    									mailId = fstMailbagVO.getMailbagId();
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

// Added for bug ICRD-100112 by A-5526 starts
//checking duplicate mailbags within a session itself
	if(containerDtlVOs != null && containerDtlVOs.size() > 0){
	for(ContainerDetailsVO cntVO :containerDtlVOs){
		Collection<MailbagVO> mailBagsinCont = cntVO.getMailDetails();
		if(mailBagsinCont != null && mailBagsinCont.size() > 0){
			for(MailbagVO fstMailbagVO:mailBagsinCont){
				
					if(!fstMailbagVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())){
						Collection<MailbagVO> secMailbagVOsFromSession = containerDetailsVO.getMailDetails();
						if(secMailbagVOsFromSession != null && secMailbagVOsFromSession.size() > 0){
							for(MailbagVO secMailbagVO:secMailbagVOsFromSession){
								if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId())){
									duplicateAcross = 1;      
									container = cntVO.getContainerNumber();  
									mailId = fstMailbagVO.getMailbagId();
								}
								
								
							}
							
								}
							}
						
					}
				}
			}
		}
	// Added for bug ICRD-100112 by A-5526 ends

    	if(duplicateAcross == 1){
	  		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.duplicatemailbagacross",new Object[]{container,mailId}));
	  		mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	}
    	   
    	// Validate Transfer From Carrier
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
    	if(containerDetailsVO.getTransferFromCarrier() != null
    		&& !"".equals(containerDetailsVO.getTransferFromCarrier())){
	    	try {        			
	    		AirlineValidationVO airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(),containerDetailsVO.getTransferFromCarrier());
	
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {            			
				Object[] obj = {containerDetailsVO.getTransferFromCarrier()};
				invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
				invocationContext.target = TARGET;
				return;
			}
    	}
    	
    	// Validate Destination
    	AreaDelegate areaDelegate = new AreaDelegate();
    	AirportValidationVO airportValidationVO = null;
    	String destination = mailAcceptanceForm.getDestn();        	
    	if (destination != null && !"".equals(destination)) {        		
    		try {        			
    			airportValidationVO = areaDelegate.validateAirportCode(
    					logonAttributes.getCompanyCode(),destination.toUpperCase());
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {            			
    			Object[] obj = {destination.toUpperCase()};
				invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidairport",obj));
				invocationContext.target = TARGET;
				return;
    		}
    	}
    	
    	
//    	Validating Mailbag & Despatches for similar key
    	/*
    	 * This code will take any newly inserted Mailbag or Despatch and construct a key from that.
    	 * The key comprises {1.OOE 2.DOE 3.MAL CAT CODE 4.Subclass 5.DSN 6.YEAR}
    	 * and check of any similar key in any of the Mail or Despatch already present.
    	 * The case may have two ways.
    	 * 1.Containers already present with Mailbags or Despatches
    	 * 2.Containers created newly with Mailbags or Despatches.
    	 * In First case, the Container Details can be taken from ContainerDetailsVO
    	 * and other details(reg. Mailbag & Despatch) can be taken from DSNVO
    	 * In Second case, the container details and all other details can be taken from MailAcceptanceVO
    	 *  
    	 */
    	Collection<MailbagVO> mailVOs=containerDetailsVO.getMailDetails();
    	Collection<DespatchDetailsVO> despatchVOS=containerDetailsVO.getDesptachDetailsVOs();
    	Collection<ContainerDetailsVO> contDtlsVOs=mailAcceptanceVO.getContainerDetails();
    	log.log(Log.FINE, "*********contDtlsVOs********", contDtlsVOs);
		String key="";
    	String connum="";
    	String errFlg=MailConstantsVO.FLAG_NO;
    	Integer errVal=0;
    	/*
    	 * This Commented Code can by used in case, any performance issues
    	 * comes with the present code written below.
    	 * Before that, make sure all the changes are done in this code too as per the present code.
    	 */
//    	HashSet<String> hashKeySet=new HashSet<String>();
//    	if((mailVOs!=null && mailVOs.size()>0)   
//    			&&(despatchVOS!=null && despatchVOS.size()>0)){
//    		for(MailbagVO mailbagVO:mailVOs){
//    			hashKeySet.add(mailbagVO.getOoe()+HYPHEN
//    					+mailbagVO.getDoe()+HYPHEN
//    					+mailbagVO.getMailCategoryCode()+HYPHEN
//    					+mailbagVO.getMailSubclass()+HYPHEN
//    					+mailbagVO.getDespatchSerialNumber()+HYPHEN
//    					+mailbagVO.getYear());
//    		}
//    		if(!hashKeySet.isEmpty()){
//    			for(DespatchDetailsVO despatchVO:despatchVOS){
//    				if(hashKeySet.contains(despatchVO.getOriginOfficeOfExchange()+HYPHEN
//    						+despatchVO.getDestinationOfficeOfExchange()+HYPHEN
//    						+despatchVO.getMailCategoryCode()+HYPHEN
//    						+despatchVO.getMailSubclass()+HYPHEN
//    						+despatchVO.getDsn()+HYPHEN
//    						+despatchVO.getYear())){
//    					errFlg=MailConstantsVO.FLAG_YES;
//    					key=despatchVO.getOriginOfficeOfExchange()+HYPHEN
//    					+despatchVO.getDestinationOfficeOfExchange()+HYPHEN
//    					+despatchVO.getMailSubclass()+HYPHEN
//    					+despatchVO.getDsn()+HYPHEN
//    					+despatchVO.getYear();
//    					break;
//    				}
//    			}
//    		}
//    	}
    	if((mailVOs!=null && mailVOs.size()>0)){    	
    		for(MailbagVO mailbagVO:mailVOs){
    			if("I".equals(mailbagVO.getOperationalFlag())){
    				key=mailbagVO.getOoe()+HYPHEN
    				+mailbagVO.getDoe()+HYPHEN
    				+mailbagVO.getMailCategoryCode()+HYPHEN
    				+mailbagVO.getMailSubclass()+HYPHEN
    				+mailbagVO.getDespatchSerialNumber()+HYPHEN
    				+mailbagVO.getYear();    				
    				for(DespatchDetailsVO despatchVO:despatchVOS){    		
    					if(key.equals(despatchVO.getOriginOfficeOfExchange()+HYPHEN
    							+despatchVO.getDestinationOfficeOfExchange()+HYPHEN
    		    				+despatchVO.getMailCategoryCode()+HYPHEN
    							+despatchVO.getMailSubclass()+HYPHEN
    							+despatchVO.getDsn()+HYPHEN
    							+despatchVO.getYear())){
    						errFlg=MailConstantsVO.FLAG_YES;
    						errVal=1;
    						connum=despatchVO.getContainerNumber();
    						break;
    					}
    				}
    				if((MailConstantsVO.FLAG_NO.equals(errFlg))
    						&& (contDtlsVOs!=null && contDtlsVOs.size()>0)){
    					for(ContainerDetailsVO containerDetailVO : contDtlsVOs){
    						if(containerDetailVO.getDesptachDetailsVOs() != null 
    								&& containerDetailVO.getDesptachDetailsVOs().size()>0){
    							for(DespatchDetailsVO despatchVO:containerDetailVO.getDesptachDetailsVOs()){    		
    								if(key.equals(despatchVO.getOriginOfficeOfExchange()+HYPHEN
    										+despatchVO.getDestinationOfficeOfExchange()+HYPHEN
    					    				+despatchVO.getMailCategoryCode()+HYPHEN
    										+despatchVO.getMailSubclass()+HYPHEN
    										+despatchVO.getDsn()+HYPHEN
    										+despatchVO.getYear())){
    									errFlg=MailConstantsVO.FLAG_YES;
    									errVal=2;
    									connum=despatchVO.getContainerNumber();
    									break;
    								}
    							} 
    						}
    						if((MailConstantsVO.FLAG_NO.equals(errFlg))
    								&&(containerDetailVO.getDsnVOs() != null 
    								&& containerDetailVO.getDsnVOs().size()>0)){
    							for(DSNVO dsnVO:containerDetailVO.getDsnVOs()){    		
    								if(key.equals(dsnVO.getOriginExchangeOffice()+HYPHEN
    										+dsnVO.getDestinationExchangeOffice()+HYPHEN
    					    				+dsnVO.getMailCategoryCode()+HYPHEN
    										+dsnVO.getMailSubclass()+HYPHEN
    										+dsnVO.getDsn()+HYPHEN
    										+dsnVO.getYear())){
    									if(MailConstantsVO.FLAG_NO.equals(dsnVO.getPltEnableFlag())){
    										errFlg=MailConstantsVO.FLAG_YES;
    										errVal=2;
    										connum=containerDetailVO.getContainerNumber();
    										break;
    									}
    								}
    							}
    						}
    						if(MailConstantsVO.FLAG_YES.equals(errFlg)){
    							break;    					
    						}
    					}
    				}
    				if(MailConstantsVO.FLAG_YES.equals(errFlg)){
    					break;    					
    				}
    			}
    		}
    	}
    	if((despatchVOS!=null && despatchVOS.size()>0) 
    			&& (MailConstantsVO.FLAG_NO.equals(errFlg))){
    		for(DespatchDetailsVO despatchVO:despatchVOS){
    			if("I".equals(despatchVO.getOperationalFlag())){
    				key=despatchVO.getOriginOfficeOfExchange()+HYPHEN
    				+despatchVO.getDestinationOfficeOfExchange()+HYPHEN
    				+despatchVO.getMailCategoryCode()+HYPHEN
    				+despatchVO.getMailSubclass()+HYPHEN
    				+despatchVO.getDsn()+HYPHEN
    				+despatchVO.getYear();
    				for(MailbagVO mailbagVO:mailVOs){    		
    					if(key.equals(mailbagVO.getOoe()+HYPHEN
    							+mailbagVO.getDoe()+HYPHEN
    		    				+mailbagVO.getMailCategoryCode()+HYPHEN
    							+mailbagVO.getMailSubclass()+HYPHEN
    							+mailbagVO.getDespatchSerialNumber()+HYPHEN
    							+mailbagVO.getYear())){
    						errFlg=MailConstantsVO.FLAG_YES;
    						errVal=1;
    						connum=mailbagVO.getContainerNumber();
    						break;
    					}
    				}
    				if((MailConstantsVO.FLAG_NO.equals(errFlg))
    						&& (contDtlsVOs!=null && contDtlsVOs.size()>0)){
    					for(ContainerDetailsVO containerDetailVO : contDtlsVOs){
    						if(containerDetailVO.getMailDetails()!=null 
    								&& containerDetailVO.getMailDetails().size()>0){
    							for(MailbagVO mailbagVO:containerDetailVO.getMailDetails()){    		
    								if(key.equals(mailbagVO.getOoe()+HYPHEN
    										+mailbagVO.getDoe()+HYPHEN
    					    				+mailbagVO.getMailCategoryCode()+HYPHEN
    										+mailbagVO.getMailSubclass()+HYPHEN
    										+mailbagVO.getDespatchSerialNumber()+HYPHEN
    										+mailbagVO.getYear())){
    									errFlg=MailConstantsVO.FLAG_YES;
    									errVal=3;
    									connum=mailbagVO.getContainerNumber();
    									break;
    								}
    							}
    						}
    						if((MailConstantsVO.FLAG_NO.equals(errFlg)) 
    								&& (containerDetailVO.getDsnVOs() != null 
    								&& containerDetailVO.getDsnVOs().size()>0)){
    							for(DSNVO dsnVO:containerDetailVO.getDsnVOs()){    		
    								if(key.equals(dsnVO.getOriginExchangeOffice()+HYPHEN
    										+dsnVO.getDestinationExchangeOffice()+HYPHEN
    					    				+dsnVO.getMailCategoryCode()+HYPHEN
    										+dsnVO.getMailSubclass()+HYPHEN
    										+dsnVO.getDsn()+HYPHEN
    										+dsnVO.getYear())){
    									if(MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
    										errFlg=MailConstantsVO.FLAG_YES;
    										errVal=3;
    										connum=containerDetailVO.getContainerNumber();
    										break;
    									}
    								}
    							}
    						}
    						if(MailConstantsVO.FLAG_YES.equals(errFlg)){
    							break;    					
    						}
    					}
    				}
    				if(MailConstantsVO.FLAG_YES.equals(errFlg)){
    					break;    					
    				}
    			}
    		}
    	}
    	if(errFlg.equals(MailConstantsVO.FLAG_YES)){
    		if(errVal==1){
    			Object[] obj={key.toUpperCase()};
    			log.log(Log.FINE, "SIMILAR-MAILBAG-DSN-KEY...--->", key.toUpperCase());
				invocationContext.addError(new ErrorVO("mailtracking.defaults.acceptmail.similarmaildsnkey",obj));
    			invocationContext.target = TARGET;
    			return;
    		}else if(errVal==2){
    			Object[] obj={key.toUpperCase(),connum.toUpperCase()};
    			log.log(Log.FINE, "SIMILAR-DSN-KEY...--->", key.toUpperCase());
				invocationContext.addError(new ErrorVO("mailtracking.defaults.acceptmail.similardsnkey",obj));
    			invocationContext.target = TARGET;
    			return;    			
    		}else if(errVal==3){
    			Object[] obj={key.toUpperCase(),connum.toUpperCase()};
    			log.log(Log.FINE, "SIMILAR-MAILBAG-KEY...--->", key.toUpperCase());
				invocationContext.addError(new ErrorVO("mailtracking.defaults.acceptmail.similarmailkey",obj));
    			invocationContext.target = TARGET;
    			return;    		
    			
    		}
    	}
    	
    	//Added by A-8331 for validating void mailbags
    	/*Collection<MailbagVO> mailbgsVO = containerDetailsVO.getMailDetails();
    	DocumentBillingDetailsVO billingDetailvo = new DocumentBillingDetailsVO();
    	if(mailbgsVO != null && mailbgsVO.size() > 0){
    		int	 count=0;
    		int delete=0;
	  		for(MailbagVO mailbagVO:mailbgsVO){
	  			if(MailbagVO.OPERATION_FLAG_DELETE.equals(mailbagVO.getOperationalFlag()))
	  				{
	  				delete++;
	  				try {
	  					billingDetailvo= findMailbagBillingStatus(mailbagVO);
	  					
						if (MRAConstantsVO.BILLED.equals(billingDetailvo.getBillingStatus()))
						{
							count ++;
							mailbagVO.setOperationalFlag("NOOP");
							
						}
						
	  				}catch (BusinessDelegateException businessDelegateException) {
				  			errors = handleDelegateException(businessDelegateException);
					  	  }
	  				
	  		}}
	  		if (delete>0 && delete==count){
  				Object[] obj = new Object[1];
  				obj[0] = String.valueOf(delete);
			        ErrorVO	displayErrorVO = new ErrorVO(VOID_MAILBAGS,obj);
				displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(displayErrorVO);
				invocationContext.addAllError(errors);				
				invocationContext.target = FAILURE;
				return;
	  		}
	  		
	  		
	  	}*/
    	//Updating Session
    	
    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailAcceptanceSession.getContainerDetailsVOs();
    	log.log(Log.FINE, "containerDetailsVOs ...in command",
				containerDetailsVOs);
		Collection<ContainerDetailsVO> newContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
    	if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
					if(containerDtlsVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())){
						newContainerDetailsVOs.add(containerDetailsVO);
					}else{
						newContainerDetailsVOs.add(containerDtlsVO);
        			}
    		}
    	}
    	//mailAcceptanceSession.setContainerDetailsVOs(newContainerDetailsVOs);
    	log.log(Log.FINE, "newContainerDetailsVOs ...in command",
				newContainerDetailsVOs);
    	
    	
    	
		// making Summary DSN VOs
    	newContainerDetailsVOs = makeDSNVOs(newContainerDetailsVOs,logonAttributes);
    	//n
    	mailAcceptanceSession.setContainerDetailsVOs(newContainerDetailsVOs);

//    	validate DSN VOs
    	log.log(Log.FINE, "Going To validate DSN VOs ...in command");
		  try {
		    new MailTrackingDefaultsDelegate().validateDSNs(containerDetailsVO.getDsnVOs());
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (errors != null && errors.size() > 0) {
	  		invocationContext.addAllError(errors);
	  		mailAcceptanceForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	  }

    	//updating in main screen VOs
	  	  //Commented by A-4810 for icrd-90664
	  	  //This is now handled in embargovalidationcommand and closemailacceptancecommand for various scenarios
		/*Collection<ContainerDetailsVO> contDetailsVOs = mailAcceptanceVO.getContainerDetails();
		log.log(Log.FINE, "contDetailsVOs ...in mainscreen", contDetailsVOs);
		Collection<ContainerDetailsVO> newVOs = new ArrayList<ContainerDetailsVO>();
		int flag = 0;
		if(contDetailsVOs != null && contDetailsVOs.size() > 0){
		  for(ContainerDetailsVO mainscreenVO:contDetailsVOs){
			  if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
				for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
				  if(mainscreenVO.getContainerNumber().equals(popupVO.getContainerNumber())){
					  newVOs.add(popupVO);
					  flag = 1;
				  }
		        }
			  }
			  if(flag == 1){
				  flag = 0;
			  }else{
				  newVOs.add(mainscreenVO);
			  }
		  }
		}
		
		log.log(Log.FINE, "newVOs ...in first", newVOs);
		flag = 0;
		if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
		for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
		      if(contDetailsVOs != null && contDetailsVOs.size() > 0){
			  for(ContainerDetailsVO mainscreenVO:contDetailsVOs){
				   if(mainscreenVO.getContainerNumber().equals(popupVO.getContainerNumber())){
						  flag = 1;
				   }
			    }
			  }
			  if(flag == 0){
				  newVOs.add(popupVO);
			  }else{
				  flag = 0;
			  }
		  }
		}
		log.log(Log.FINE, "newVOs ...in second", newVOs);
		mailAcceptanceVO.setContainerDetails(newVOs);*/
		mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
		//mailAcceptanceSession.setContainerDetailsVOs(null);
		//mailAcceptanceSession.setContainerDetailsVO(null);
		//mailAcceptanceForm.setPopupCloseFlag("Y");
		mailAcceptanceForm.setSelectMail(null);
    	invocationContext.target = TARGET;
    	log.exiting("AcceptMailCommand","execute");    	
    }

	/**
	 * Mehtod to update DSN Summary VOs
	 * 
	 * @param newContainerDetailsVOs
	 * @param logonAttributes
	 * @return Collection<ContainerDetailsVO>
	 */
	public Collection<ContainerDetailsVO> makeDSNVOs(
			Collection<ContainerDetailsVO> newContainerDetailsVOs,
			LogonAttributes logonAttributes) {

		if (newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO popupVO : newContainerDetailsVOs) {
				HashMap<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
				Collection<DespatchDetailsVO> despatchDetailsVOs = popupVO
						.getDesptachDetailsVOs();
				if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
					for (DespatchDetailsVO despatchVO : despatchDetailsVOs) {
						int numBags = 0;
						double bagWgt = 0;
						double accptbagvol = 0;
						int stdNumBags = 0;
						double stdBagWgt = 0;
						double stdBagVol = 0;
						String outerpk = despatchVO.getOriginOfficeOfExchange()
								+ despatchVO.getDestinationOfficeOfExchange()
								// +despatchVO.getMailClass()
								// added by anitha for change in pk
								+ despatchVO.getMailCategoryCode()
								+ despatchVO.getMailSubclass()
								+ despatchVO.getDsn() + despatchVO.getYear();
						if (dsnMap.get(outerpk) == null) {
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes
									.getCompanyCode());
							dsnVO.setDsn(despatchVO.getDsn());
							dsnVO.setOriginExchangeOffice(despatchVO
									.getOriginOfficeOfExchange());
							dsnVO.setDestinationExchangeOffice(despatchVO
									.getDestinationOfficeOfExchange());
							dsnVO.setMailClass(despatchVO.getMailClass());
							// added by anitha for change in pk
							dsnVO.setMailCategoryCode(despatchVO
									.getMailCategoryCode());
							dsnVO.setMailSubclass(despatchVO.getMailSubclass());
							dsnVO.setYear(despatchVO.getYear());
							dsnVO.setPltEnableFlag("N");
							for (DespatchDetailsVO innerVO : despatchDetailsVOs) {
								String innerpk = innerVO
										.getOriginOfficeOfExchange()
										+ innerVO
												.getDestinationOfficeOfExchange()
										// +innerVO.getMailClass()
										// added by anitha for change in pk
										+ innerVO.getMailCategoryCode()
										+ innerVO.getMailSubclass()
										+ innerVO.getDsn() + innerVO.getYear();
								if (outerpk.equals(innerpk)) {
									numBags = numBags
											+ innerVO.getAcceptedBags();
									/*bagWgt = bagWgt
											+ innerVO.getAcceptedWeight();*/
									bagWgt = bagWgt
											+ innerVO.getAcceptedWeight().getRoundedSystemValue(); //added by A-7550
									//accptbagvol = accptbagvol
									//		+ innerVO.getAcceptedVolume();
									accptbagvol = accptbagvol
											+ innerVO.getAcceptedVolume().getRoundedSystemValue(); //Added by A-7550 
									stdNumBags = stdNumBags
											+ innerVO.getStatedBags();
									/*stdBagWgt = stdBagWgt
											+ innerVO.getStatedWeight();*/
									stdBagWgt = stdBagWgt
											+ innerVO.getStatedWeight().getRoundedSystemValue();//added by A-7550
									//stdBagVol = stdBagVol
										//	+ innerVO.getStatedVolume();
									stdBagVol = stdBagVol
												+ innerVO.getStatedVolume().getRoundedSystemValue(); //added by A-7550
								}
							}
							dsnVO.setBags(numBags);
							//dsnVO.setWeight(doubleFormatter(bagWgt, 2));
							dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT, doubleFormatter(bagWgt, 2))); //Added by A-7550
							dsnVO.setStatedBags(stdNumBags);
							//dsnVO
							//		.setStatedWeight(doubleFormatter(stdBagWgt,
							//				2));
							dsnVO
							.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,doubleFormatter(stdBagWgt,
									2)));        //Added by A-7550
							//dsnVO.setAcceptedVolume(doubleFormatter(
							//		accptbagvol, 2));
							dsnVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME,doubleFormatter(
									accptbagvol, 2)));     //Added by A-7550
							//dsnVO
								//	.setStatedVolume(doubleFormatter(stdBagVol,
								//			2));
							dsnVO
							.setStatedVolume(new Measure(UnitConstants.VOLUME,doubleFormatter(stdBagVol,
									2)));                          //Added by A-7550
							dsnMap.put(outerpk, dsnVO);
							numBags = 0;
							bagWgt = 0;
							accptbagvol = 0;
							stdNumBags = 0;
							stdBagWgt = 0;
							stdBagVol = 0;
						}
					}
				}

				Collection<MailbagVO> mailbagVOs = popupVO.getMailDetails();
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for (MailbagVO mailbagVO : mailbagVOs) {
						    int dsnCount=0;
						    int newDsnCount=0;
						    double deletedBagWgt = 0;
						    
						int numBags = 0;
						double bagWgt = 0;
						double bagVol = 0;
						String outerpk = mailbagVO.getOoe()
								+ mailbagVO.getDoe()
								+ (mailbagVO.getMailSubclass())
								+ mailbagVO.getMailCategoryCode()
								+ mailbagVO.getDespatchSerialNumber()
								+ mailbagVO.getYear();
						if (dsnMap.get(outerpk) == null) {
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes
									.getCompanyCode());
							dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
							dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
							dsnVO.setDestinationExchangeOffice(mailbagVO
									.getDoe());
							dsnVO.setMailClass(mailbagVO.getMailSubclass()
									.substring(0, 1));
							dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
							dsnVO.setMailCategoryCode(mailbagVO
									.getMailCategoryCode());
							dsnVO.setYear(mailbagVO.getYear());
							dsnVO.setPltEnableFlag("Y");
							if("D".equals(mailbagVO.getOperationalFlag())){
								dsnVO.setOperationFlag("D");    
															}      
							for (MailbagVO innerVO : mailbagVOs) {
								    if("D".equals(innerVO.getAcknowledge())){     
								    	break;    
								    }
								    
								    if("I".equals(innerVO.getOperationalFlag())){
								    	newDsnCount++; 
								    	
								    }
								String innerpk = innerVO.getOoe()
										+ innerVO.getDoe()
										+ (innerVO.getMailSubclass())
										+ innerVO.getMailCategoryCode()
										+ innerVO.getDespatchSerialNumber()
										+ innerVO.getYear();
								
								if (outerpk.equals(innerpk)) {
									if("D".equals(innerVO.getOperationalFlag())){
									
								    	dsnVO.setOperationFlag("D");
								    	deletedBagWgt=deletedBagWgt+innerVO.getWeight().getRoundedSystemValue();//modified by A-7550
								    	if( "B".equals(popupVO.getContainerType())){      
								    		innerVO.setAcknowledge("U");        
								    	}            
								    }
									dsnCount++;    
										if(!"D".equals(innerVO.getOperationalFlag())) {
									numBags = numBags + 1;
									if(innerVO.getWeight()!=null){
									bagWgt = bagWgt + innerVO.getWeight().getSystemValue();//modified by A-7550
									}
									//bagVol = bagVol + innerVO.getVolume();
									bagVol = bagVol + innerVO.getVolume().getRoundedSystemValue(); //Added by A-7550
									
								}
								}
								
								    
							}
							dsnVO.setStatedBags(dsnCount);  
							dsnVO.setShipmentCount(newDsnCount);
							//dsnVO.setShipmentWeight(deletedBagWgt);   
							dsnVO.setShipmentWeight(new Measure(UnitConstants.MAIL_WGT, deletedBagWgt));  //Added by A-7550							
							dsnVO.setBags(numBags);
							//dsnVO.setWeight(doubleFormatter(bagWgt, 2));
							/*
							 * A-5526 Added For ICRD-286407 Begin
							 * Now weight conversion from hg to kg is not happening.
							 * Remove the code block after releasing Measure changes CR ICRD-211400 . 
							  */
					/*		if(!"AA".equals(logonAttributes.getCompanyCode())){
								bagWgt=bagWgt/10; 
				}*/
							dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,doubleFormatter(bagWgt, 2))); //Added by A-7550
							//dsnVO.setAcceptedVolume(doubleFormatter(bagVol, 2));
							dsnVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME,doubleFormatter(bagVol, 2))); //Added by A-7550
							//dsnVO.setStatedVolume(0); // Since mailbag has only
														// one vol and that is
														// kept inside accepted
														// vol
							dsnVO.setStatedVolume(new Measure(UnitConstants.VOLUME,0));
							dsnMap.put(outerpk, dsnVO);
							numBags = 0;
							bagWgt = 0;
							bagVol = 0;
						}
					
					}
				}

				Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
				for (String key : dsnMap.keySet()) {
					DSNVO dsnVO = dsnMap.get(key);
					newDSNVOs.add(dsnVO);
				}
				Collection<DSNVO> oldDSNVOs = popupVO.getDsnVOs();
				int numBags = 0;
				double wgtBags = 0;
				if (newDSNVOs != null && newDSNVOs.size() > 0) {
					for (DSNVO dsnVO : newDSNVOs) {
						String outerpk = dsnVO.getOriginExchangeOffice()
								+ dsnVO.getDestinationExchangeOffice()
								// +dsnVO1.getMailClass()
								// added by anitha for change in pk
								+ dsnVO.getMailCategoryCode()
								+ dsnVO.getMailSubclass() + dsnVO.getDsn()
								+ dsnVO.getYear();
						int flag = 0;
						if (oldDSNVOs != null && oldDSNVOs.size() > 0) {
							for (DSNVO oldDsnVO : oldDSNVOs) {
								String innerpk = oldDsnVO
										.getOriginExchangeOffice()
										+ oldDsnVO.getDestinationExchangeOffice()
										// +oldDsnVO.getMailClass()
										// added by anitha for change in pk
										+ oldDsnVO.getMailCategoryCode()
										+ oldDsnVO.getMailSubclass()
										+ oldDsnVO.getDsn() + oldDsnVO.getYear();
								if (outerpk.equals(innerpk)) {
									if (!"I".equals(oldDsnVO.getOperationFlag())) {
										if( !"D".equals(dsnVO.getOperationFlag())){
										dsnVO.setOperationFlag("U");
										} 
										
										dsnVO.setPrevBagCount(oldDsnVO
												.getPrevBagCount());
										dsnVO.setPrevBagWeight(oldDsnVO
												.getPrevBagWeight());
										dsnVO.setPrevStatedBags(oldDsnVO
												.getPrevStatedBags());
										dsnVO.setPrevStatedWeight(oldDsnVO
												.getPrevStatedWeight());
										 
										/*
										 * Added For AirNZ CR : Mail Allocation
										 */
										dsnVO.setUbrNumber(oldDsnVO
												.getUbrNumber());
										dsnVO.setCurrencyCode(oldDsnVO
												.getCurrencyCode());
										dsnVO
												.setMailrate(oldDsnVO
														.getMailrate());
										dsnVO.setBookingLastUpdateTime(oldDsnVO
												.getBookingLastUpdateTime());
										dsnVO
												.setBookingFlightDetailLastUpdTime(oldDsnVO
														.getBookingFlightDetailLastUpdTime());
										// END CR : Mail Allocation

										// For ANZ BUG 37127
										dsnVO.setRoutingAvl(oldDsnVO
												.getRoutingAvl());
									}
									flag = 1;
								}
							}
						}
						if (flag == 1) {
							if (!"U".equals(dsnVO.getOperationFlag()) && !"D".equals(dsnVO.getOperationFlag())) {  
								dsnVO.setOperationFlag("I");
							}
							flag = 0;
						} else {
							dsnVO.setOperationFlag("I");
						}
						numBags = numBags + dsnVO.getBags();
						//wgtBags = wgtBags + dsnVO.getWeight();
						wgtBags = wgtBags + dsnVO.getWeight().getSystemValue(); //Added by A-7550
						if("D".equals(dsnVO.getOperationFlag()) &&
								dsnVO.getStatedBags()==1 && numBags>0){            
						numBags = numBags - dsnVO.getBags();                
						//wgtBags = wgtBags - dsnVO.getWeight();  
						wgtBags = wgtBags - dsnVO.getWeight().getRoundedSystemValue();   //Added by A-7550
						}    
						if("D".equals(dsnVO.getOperationFlag()) &&
								dsnVO.getStatedBags()>1&&
								dsnVO.getShipmentCount()>0){
							dsnVO.setOperationFlag("U"); 
							dsnVO.setBags(dsnVO.getStatedBags());                  
							//dsnVO.setWeight(dsnVO.getWeight()+dsnVO.getShipmentWeight());  
							try {
								dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(), dsnVO.getShipmentWeight()));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE,"UnitException",e.getMessage());
							}  
						}  
						dsnVO.setStatedBags(0);              
						dsnVO.setShipmentCount(0);
						//dsnVO.setShipmentWeight(0);      
						dsnVO.setShipmentWeight(new Measure(UnitConstants.MAIL_WGT, 0)); //Added by A-7550
					}
				}
				popupVO.setTotalBags(numBags);
				popupVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,doubleFormatter(wgtBags, 2)));
				popupVO.setDsnVOs(newDSNVOs);
			}
		}

		return newContainerDetailsVOs;
	}

	/**
	 * Rounding double value to a given precosion
	 * 
	 * @author A-3227 RENO K ABRAHAM
	 * @param amt
	 * @param precisionValue
	 * @return
	 */
	private static double doubleFormatter(double dataToRound, int precisionValue) {

		long formattedData = (long) (dataToRound * Math.pow(10,
				precisionValue + 1));
		int sign = (formattedData < 0) ? -1 : 1;
		int reminder = (int) (Math.abs(formattedData) % 10);
		formattedData = formattedData / 10;
		if (reminder >= 5) {
			formattedData++;
		}
		return (sign * formattedData / Math.pow(10, precisionValue));
	}
	/**
	 * @author A-7371
	 * @param syspar
	 * @return
	 * @throws BusinessDelegateException
	 */
	public String findSystemParameterValue(String syspar) throws BusinessDelegateException
			 {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}
	/**
	 * 	Method		:	AcceptMailCommand.findMailbagBillingStatus
	 *	Added by 	:	a-8331 on 25-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param mailvo
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	Collection<DocumentBillingDetailsVO>
	 */
	 public DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailvo) throws BusinessDelegateException {
	    	
	    	DocumentBillingDetailsVO billingStatusDetails = null;
	    	DocumentBillingDetailsVO documentBillingDetailsVO = new DocumentBillingDetailsVO();
	    	Collection<DocumentBillingDetailsVO>  documentBillingDetails = new ArrayList<DocumentBillingDetailsVO>();
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	        	MailbagVO mailbagVO = new MailbagVO() ;		
	        	mailbagVO.setCompanyCode(mailvo.getCompanyCode());
	        	LocalDate currentDate = new LocalDate("***", Location.NONE, true);
	     	    mailbagVO.setFlightNumber(mailvo.getFlightNumber());
	        	mailbagVO.setFlightSequenceNumber(mailvo.getFlightSequenceNumber());
	        	mailbagVO.setOrigin(null);
	        	mailbagVO.setDestination(null);
	        	mailbagVO.setMailbagId(mailvo.getMailbagId());
	        	mailbagVO.setCarrierId(mailvo.getCarrierId());
	        	mailbagVO.setMailSequenceNumber(mailvo.getMailSequenceNumber());
	        	documentBillingDetailsVO.setCompanyCode(mailvo.getCompanyCode());
	            documentBillingDetailsVO.setMailSequenceNumber(mailvo.getMailSequenceNumber());
	            documentBillingDetailsVO.setLastUpdatedUser(getApplicationSession().getLogonVO().getUserId());
	            documentBillingDetailsVO.setLastUpdatedTime(currentDate);
	            documentBillingDetailsVO.setScreenID(SCREEN_ID_VOID);
	            documentBillingDetailsVO.setBillingBasis(mailvo.getMailbagId());
	            documentBillingDetails.add(documentBillingDetailsVO);
	        	try {
					billingStatusDetails = new MailTrackingDefaultsDelegate().findMailbagBillingStatus(mailbagVO);
	        	}catch (BusinessDelegateException businessDelegateException) {
	      			errors = handleDelegateException(businessDelegateException);
	    	  	  }
				
	        		
	    			
	    		/*for (DocumentBillingDetailsVO 
	    		{docvo :billingStatusDetails)	*/
	    			
	    			
	    			if (MRAConstantsVO.BILLED.equals(billingStatusDetails.getBillingStatus())){
						
	    				return billingStatusDetails;
    					
					}
	       				
	       				else
	       				{
	       					if(MRAConstantsVO.BILLABLE.equals(billingStatusDetails.getBillingStatus()))
	       					{
	       						
	       						try {
	       						new  MailTrackingDefaultsDelegate().voidMailbags(documentBillingDetails);		
	       						}catch (BusinessDelegateException businessDelegateException) {
	       				  			errors = handleDelegateException(businessDelegateException);
	       					  	  }
	       						
	       						
	       					}
	       				}
	       			
	       			
	    		
	    		return billingStatusDetails;	
	       			
	    		}
	 }
	           

