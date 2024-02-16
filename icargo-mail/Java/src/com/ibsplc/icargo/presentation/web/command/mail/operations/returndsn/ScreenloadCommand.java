/*
 * ScreenloadCommand.java Created on July 24, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returndsn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnDsnSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnDsnForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.dispatcher.BatchedResponse;
import com.ibsplc.xibase.client.framework.dispatcher.DispatcherException;
import com.ibsplc.xibase.client.framework.dispatcher.RequestDispatcher;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class ScreenloadCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.returndsn";
   private static final String DSN_SCREEN_ID = "mailtracking.defaults.dsnEnquiry";
   
   private static final String DAMAGE_CODE = "mailtracking.defaults.return.reasoncode";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenLoadCommand","execute");
    	  
    	ReturnDsnForm returnDsnForm = 
    		(ReturnDsnForm)invocationContext.screenModel;
    	ReturnDsnSession returnDsnSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	
		//Added for Unit Component
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		returnDsnSession.setWeightRoundingVO(unitRoundingVO);			
		setUnitComponent(logonAttributes.getStationCode(),returnDsnSession);	
		
    	Collection<String> fieldTypes = new ArrayList<String>();		
		fieldTypes.add(DAMAGE_CODE);
				
		Map<String,Collection<OneTimeVO>> oneTimeData
			= new HashMap<String,Collection<OneTimeVO>>();	
		    	
    	/*
		 * Start the batch processing
		 */
		RequestDispatcher.startBatch();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		AreaDelegate areaDelegate = new AreaDelegate();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		
    	try { 		
    		
    		sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					fieldTypes);
    		  		  		    		
    	}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
    	
    	/*
		 * Obtain the responses after the batch fetch
		 */
		try {
			BatchedResponse response[] = RequestDispatcher.executeBatch();
			log.log(Log.INFO, "Response length:--", response.length);
			if(!response[0].hasError()) {
				oneTimeData = (HashMap<String,Collection<OneTimeVO>>)response[0].getReturnValue();
				log.log(Log.INFO, "oneTimeData:--", oneTimeData);
			}
							
		}catch (DispatcherException dispatcherException) {
			dispatcherException.getMessage();
		}
		
		if (oneTimeData != null) {			
			Collection<OneTimeVO> damagecodes = 
				oneTimeData.get(DAMAGE_CODE);	
						
			returnDsnSession.setOneTimeDamageCodes(damagecodes);			
		}
		
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		String countryCode = null;
				
		try {
			airportValidationVO = areaDelegate.validateAirportCode(
    				logonAttributes.getCompanyCode(),
    				logonAttributes.getStationCode()); 
			
			countryCode = airportValidationVO.getCountryCode();
			
			if (countryCode != null) {
				postalAdministrationVOs = mailTrackingDefaultsDelegate.findLocalPAs(
						logonAttributes.getCompanyCode(),
						countryCode);
			}				
			
		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		if (postalAdministrationVOs != null) {
			returnDsnSession.setPostalAdministrationVOs(postalAdministrationVOs);
		}
		
		try {
			handleDespatchDetailsVOs(
					returnDsnForm,
					returnDsnSession,
					logonAttributes,
					mailTrackingDefaultsDelegate);				
			
		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}		
	    		
		returnDsnForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);
		returnDsnForm.setCurrentPage(1);
		returnDsnForm.setDisplayPage(1);
		returnDsnForm.setLastPage(returnDsnSession.getDamagedDsnVOs().size());
        	
    	invocationContext.target = TARGET;
       	
    	log.exiting("ScreenLoadCommand","execute");
    	
    }
    /**
     * Method to get the selected DespatchDetailsVOs and set it to ReturnDsnSession
     * @param returnDsnForm
     * @param returnDsnSession
     * @param logonAttributes
     * @param mailTrackingDefaultsDelegate
     * @throws BusinessDelegateException
     */
    private void handleDespatchDetailsVOs(
    		ReturnDsnForm returnDsnForm,
    		ReturnDsnSession returnDsnSession,
    		LogonAttributes logonAttributes,
    		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate) 
    throws BusinessDelegateException {
    	
    	log.entering("ScreenLoadCommand","handleDespatchDetailsVOs");
    	
    	DsnEnquirySession dsnEnquirySession =
    		getScreenSession(MODULE_NAME,DSN_SCREEN_ID);
    	
    	String selecteddsns = returnDsnForm.getSelectedDsns();
		String[] selectedDsns = selecteddsns.split(",");
		ArrayList<String> dsns = new ArrayList<String>();
		for (String str : selectedDsns) {
			dsns.add(str);
		}
		log.log(Log.FINE, "Selected indexes------------> ", dsns);
		Collection<DespatchDetailsVO> despatchDetailsVOs = dsnEnquirySession.getDespatchDetailsVOs();
		//Collection<DespatchDetailsVO> selectedDespatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
		Collection<DamagedDSNVO> selectedvos = new ArrayList<DamagedDSNVO>();
		
		int index = 0;
		for (DespatchDetailsVO selectedvo : despatchDetailsVOs) {
			if (dsns.contains(String.valueOf(index))) {
				
				//selectedDespatchDetailsVOs.add(selectedvo);
				
				DamagedDSNVO damagedDsnVO = new DamagedDSNVO();
				damagedDsnVO.setAcceptedBags(selectedvo.getAcceptedBags());
				damagedDsnVO.setAcceptedWeight(selectedvo.getAcceptedWeight());
				damagedDsnVO.setAirportCode(selectedvo.getAirportCode());
				damagedDsnVO.setCarrierId(selectedvo.getCarrierId());
				damagedDsnVO.setCompanyCode(selectedvo.getCompanyCode());
				damagedDsnVO.setCarrierCode(selectedvo.getCarrierCode());
				damagedDsnVO.setConsignmentNumber(selectedvo.getConsignmentNumber());
				damagedDsnVO.setMailCategoryCode(selectedvo.getMailCategoryCode());
				damagedDsnVO.setConsignmentSequenceNumber(selectedvo.getConsignmentSequenceNumber());
				damagedDsnVO.setDestinationExchangeOffice(selectedvo.getDestinationOfficeOfExchange());
				damagedDsnVO.setDsn(selectedvo.getDsn());
				if (selectedvo.getFlightSequenceNumber() > 0) {
					damagedDsnVO.setFlightNumber(selectedvo.getFlightNumber());
					damagedDsnVO.setFlightSequenceNumber(selectedvo
							.getFlightSequenceNumber());
					damagedDsnVO.setSegmentSerialNumber(selectedvo
							.getSegmentSerialNumber());
					damagedDsnVO.setFlightDate(selectedvo.getFlightDate());
				} else {
					damagedDsnVO.setFlightNumber(String
							.valueOf(MailConstantsVO.DESTN_FLT));
					damagedDsnVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
					damagedDsnVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
				}
				
				damagedDsnVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				damagedDsnVO.setMailClass(selectedvo.getMailClass());
				damagedDsnVO.setMailSubclass(selectedvo.getMailSubclass());
				damagedDsnVO.setOriginExchangeOffice(selectedvo.getOriginOfficeOfExchange());
				damagedDsnVO.setYear(selectedvo.getYear());
				Collection<DamagedDSNDetailVO> damagedDsnDetailVOs = new ArrayList<DamagedDSNDetailVO>();
				damagedDsnVO.setDamagedDsnDetailVOs(damagedDsnDetailVOs);
				damagedDsnVO.setContainerType(selectedvo.getContainerType());
				damagedDsnVO.setContainerNumber(selectedvo.getContainerNumber());
				damagedDsnVO.setPou(selectedvo.getPou()!= null ?selectedvo.getPou():selectedvo.getDestination());
				damagedDsnVO.setAirportCode(logonAttributes.getAirportCode());
				damagedDsnVO.setPaCode(selectedvo.getPaCode());
				selectedvos.add(damagedDsnVO);
			}
			index++;
		}
		
		//selectedvos = mailTrackingDefaultsDelegate.findDSNDamages(selectedDespatchDetailsVOs);
		
		/*for (DamagedDSNVO vo : selectedvos) {
			damagedDsnDetailVOs = vo.getDamagedDsnDetailVOs();
			if (damagedDsnDetailVOs != null && damagedDsnDetailVOs.size() > 0) {
				for(DamagedDSNDetailVO dsnDetailVO : damagedDsnDetailVOs) {
					dsnDetailVO.setLatestDamagedBags(dsnDetailVO.getDamagedBags());
					dsnDetailVO.setLatestDamagedWeight(dsnDetailVO.getDamagedWeight());
					dsnDetailVO.setLatestReturnedBags(dsnDetailVO.getReturnedBags());
					dsnDetailVO.setLatestReturnedWeight(dsnDetailVO.getReturnedWeight());
				}
			}
			else {
				vo.setAirportCode(logonAttributes.getAirportCode());
			}
		}*/
		
		log.log(Log.FINE, "DamagedDsnVOs------------> ", selectedvos);
		returnDsnSession.setDamagedDsnVOs(selectedvos);
		
		// setting details of first vo to the form
		for (DamagedDSNVO vo : selectedvos) {
			returnDsnForm.setDsn(vo.getDsn());
			returnDsnForm.setOriginOE(vo.getOriginExchangeOffice());
			returnDsnForm.setDestnOE(vo.getDestinationExchangeOffice());
			returnDsnForm.setMailClass(vo.getMailClass());
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			String year = "200";
			String[] date = currentDate.toDisplayDateOnlyFormat().split("-");
			if(date[date.length - 1].length() == 4) {
				year = date[date.length - 1].substring(0,3);
			}
			log.log(Log.FINE, "---year---> ", year);
			returnDsnForm.setYear(year+String.valueOf(vo.getYear()));
			if(vo.getAcceptedWeight().getRoundedSystemValue()== 0){
				//returnDsnForm.setDmgWeight(vo.getReceivedWeight());
				returnDsnForm.setDmgWeight(vo.getReceivedWeight().getRoundedSystemValue());//added by A-7371
			}else{
			//returnDsnForm.setDmgWeight(vo.getAcceptedWeight());
				returnDsnForm.setDmgWeight(vo.getAcceptedWeight().getRoundedSystemValue());//added by A-7371
			}
			if(vo.getAcceptedBags()== 0){
				returnDsnForm.setDmgNOB(vo.getReceivedBags());
			}else{
				returnDsnForm.setDmgNOB(vo.getAcceptedBags());	
			}
			/*damagedDsnDetailVOs = vo.getDamagedDsnDetailVOs();
			String pacode = "";
			if (damagedDsnDetailVOs != null) {
				for(DamagedDSNDetailVO dsnDetailVO : damagedDsnDetailVOs) {
					pacode = dsnDetailVO.getReturnedPaCode();
					break;
				}
				returnDsnForm.setPostalAdmin(pacode);
			}else{
			 */
			returnDsnForm.setPostalAdmin(vo.getPaCode());
			
						
			break;
		}
		
		log.exiting("ScreenLoadCommand","handleDespatchDetailsVOs");
    }
    
    /**
	 * A-3251
	 * @param stationCode
	 * @param mailAcceptanceSession
	 * @return 
	 */
	private void setUnitComponent(String stationCode,
			ReturnDsnSession returnDsnSession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.WEIGHT);			
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			returnDsnSession.setWeightRoundingVO(unitRoundingVO);			
		   }catch(UnitException unitException) {
				unitException.getErrorCode();
		   }
		
	}
}
