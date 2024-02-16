
/*
 * SaveCommand.java Created on Sep 04, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchrouting;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNRoutingSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3229
 *
 */



public class SaveCommand extends BaseCommand {
	private  Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS "); 

	private static final String SAVE_SUCCESS = "save_success";	
	private static final String SAVE_FAILURE = "save_failure";
	private static final String DESPATCH_ROUTING = "DR";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.despatchrouting";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID_DSNPOPUP = "mailtracking.mra.defaults.dsnselectpopup";

	private static final String WRONG_AGREEMENTYPE = "mailtracking.mra.defaults.despatchrouting.msg.err.wrongagreement";
	private static final String INVALID_PA="mail.mra.defaults.modifyroute.error.invalidtransferpa";
	private static final String INVALID_AIRLINE="mail.mra.defaults.modifyroute.error.invalidtransferairline";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {


		log.entering("Save Despatch Routing ","execute");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		DespatchRoutingForm  despatchRoutingForm = (DespatchRoutingForm)invocationContext.screenModel;
		DSNRoutingSession dsnRoutingSession = getScreenSession(MODULE_NAME,SCREEN_ID); 
	    MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREENID_DSNPOPUP);
		Collection<DSNRoutingVO> dsnRoutingVOs=dsnRoutingSession.getDSNRoutingVOs();	    	
		log.log(Log.INFO, "dsnRoutingVOs@@@@@@@@@@@@@@@@@@@@", dsnRoutingVOs);
		updateDsnRoutingSession(dsnRoutingSession,despatchRoutingForm); 

		dsnRoutingVOs=dsnRoutingSession.getDSNRoutingVOs();
		log.log(Log.INFO, "dsnRoutingVOs after updating session@@@@@@",
				dsnRoutingVOs);
		Collection<ErrorVO> errors = null;

		double oalWgt=(Double.parseDouble(despatchRoutingForm.getOalwgt()));
		try {
			oalWgt=UnitFormatter.getRoundedValue(UnitConstants.WEIGHT,UnitConstants.WEIGHT_UNIT_KILOGRAM, oalWgt);
		} catch (UnitException unitException) {
			unitException.getErrorCode();
		}
		//validations
		//validating carrier code----->>>

		errors = new ArrayList<ErrorVO>();
		
		validateTransferPAAndAirline(despatchRoutingForm,logonAttributes,errors);
		
		if(errors!=null && !errors.isEmpty()){
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;	
		}
		

		Map<String, AirlineValidationVO> validealphaCodes = new HashMap<String, AirlineValidationVO>();
		AirlineDelegate airlineDelegate = new AirlineDelegate();

		String[] carrierCode=despatchRoutingForm.getFlightCarrierCode();		 	
		String[] hiddenopFlg = despatchRoutingForm.getHiddenOpFlag();

		try {

			Collection<String> carrierCodes=new ArrayList<String>();

			if(hiddenopFlg!=null && hiddenopFlg.length>0 ){
				int count=0;
				for(String opflag :hiddenopFlg){
					if(carrierCode!=null){						
						if(!"NOOP".equals(opflag)&&!"D".equals(opflag)){
							if(!(carrierCodes.contains(carrierCode[count]))){
								carrierCodes.add(carrierCode[count]);			
							}
						}							
					}
					count= count+1;	
				}					
			}

			validealphaCodes = airlineDelegate.validateAlphaCodes(
					logonAttributes.getCompanyCode(), carrierCodes);

			//setting valid carrier id to vos
			if(validealphaCodes!=null){
				for(DSNRoutingVO dsnRoutingVO : dsnRoutingVOs ) {
					if(!("D".equals(dsnRoutingVO.getOperationFlag()))){
						dsnRoutingVO.setFlightCarrierId(validealphaCodes.get(dsnRoutingVO.getFlightCarrierCode()).getAirlineIdentifier());  
					}						  						  
				}
			}

		} catch (BusinessDelegateException businessDelegateException) {
			//printStackTrrace()();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "airlineValidationVO********************",
				validealphaCodes);
		boolean isValidFlightNumber = validateFlightForOwnAirlineCode(logonAttributes.getOwnAirlineCode(),dsnRoutingVOs);
		if(!isValidFlightNumber){
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.dsnrouting.flightnumbermandatory");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		


		/**
		 * added flight validation for bug ICRD-2170 
		 * to stamp flight seqnum in MTKDSNRTG
		 */
		//Added by A-5945 for ICRD-104289 starts
		for(DSNRoutingVO dsnRoutingVO : dsnRoutingVOs){
		if(dsnRoutingVO.getFlightCarrierCode()!=null && !"YY".equals(dsnRoutingVO.getFlightCarrierCode()) &&
				dsnRoutingVO.getFlightNumber()!=null && dsnRoutingVO.getFlightNumber().trim().length()>0 &&
				!"0000".equals(dsnRoutingVO.getFlightNumber())&& !"D".equals(dsnRoutingVO.getOperationFlag())){
		Collection<FlightValidationVO> flightValidationVOs=validateFlightForAirport(despatchRoutingForm,dsnRoutingVO);
		if ((flightValidationVOs.isEmpty() && flightValidationVOs.size() <= 0 
				&& dsnRoutingVO.getFlightCarrierCode().equals(logonAttributes.getOwnAirlineCode())) || "CAN".equals(flightValidationVOs.iterator().next().getFlightStatus())) {//modified by A-7371 as part of ICRD-267391 
				//modified by A-7371 as part of ICRD-267391 
					log.log(Log.FINE, "flightValidationVOs is NULL");
					Object[] obj = {dsnRoutingVO.getFlightCarrierCode(),
							dsnRoutingVO.getFlightNumber(),
							dsnRoutingVO.getDepartureDate().toString().substring(0,11)};
					        errors.add(new ErrorVO("mailtracking.mra.defaults.despatchrouting.msg.err.noflightdetails",obj));
				}
		boolean activeFlight=false;
		if(flightValidationVOs.size() >1) {
		Iterator<FlightValidationVO> flightterator = flightValidationVOs.iterator();
		FlightValidationVO flightValidationVO = null;
		while(flightterator.hasNext()) {
			flightValidationVO = flightterator.next();
			int polIndex = 0;
			int pouIndex = 0;
			String[] fltRoute = flightValidationVO.getFlightRoute().split("-");
			boolean isRoundRobinFlt = false;
			if(fltRoute != null && fltRoute.length > 2){
				isRoundRobinFlt = fltRoute[0].equals(fltRoute[fltRoute.length-1]) ;
				if(isRoundRobinFlt && !flightValidationVO.getFlightRoute().contains(dsnRoutingVO.getPol()) ||
						!flightValidationVO.getFlightRoute().contains(dsnRoutingVO.getPou())){
					isRoundRobinFlt = false;
				}
			}
			for(int i=0; i<fltRoute.length ; i++){
				polIndex = fltRoute[i].equals(dsnRoutingVO.getPol()) ? i : polIndex;
				pouIndex = fltRoute[i].equals(dsnRoutingVO.getPou()) ? i : pouIndex;
			}
			if(!flightValidationVO.getFlightRoute().contains(dsnRoutingVO.getPol()) || !flightValidationVO.getFlightRoute().contains(dsnRoutingVO.getPou()) || (polIndex > pouIndex && !isRoundRobinFlt)){
				flightterator.remove();
			}
			else{
				if(flightValidationVO.getFlightStatus().equals("ACT") || flightValidationVO.getFlightStatus().equals("TBA")) {
					activeFlight=true;
				}
			}
		 }
		}
		//Added by A-8176 for ICRD-225429 starts
		for(FlightValidationVO flightvo:flightValidationVOs){
			if(flightvo.getFlightStatus().equals("ACT") || flightvo.getFlightStatus().equals("TBA") || !activeFlight) {
			dsnRoutingVO.setFlightType(flightvo.getFlightType());//A-8061 Added for ICRD-307354
			}
			//added by A-7371 for ICRD-343383 starts
			Collection<FlightSegmentSummaryVO> segmentSummaryVos = null;
			segmentSummaryVos=findFlightSegments(flightvo);
			if(segmentSummaryVos!=null && segmentSummaryVos.size()>0){
				for (FlightSegmentSummaryVO segmentVo : segmentSummaryVos) {
					if(segmentVo.getSegmentOrigin().equals(dsnRoutingVO.getPol())
							&& segmentVo.getSegmentDestination().equals(dsnRoutingVO.getPou())){ 
						dsnRoutingVO.setSegmentSerialNumber(segmentVo.getSegmentSerialNumber());
					}
				}
			}
			//added by A-7371 for ICRD-343383 ends	
			if(dsnRoutingVO.getAgreementType()!=null && dsnRoutingVO.getAgreementType().trim().length()>0 && ! "null".equals(dsnRoutingVO.getAgreementType().trim())) {
				if(flightvo.getAgreementType()!=null && flightvo.getAgreementType().trim().length()>0){
			     if(!dsnRoutingVO.getAgreementType().equals(flightvo.getAgreementType())) {
				       Object[] obj = {dsnRoutingVO.getFlightCarrierCode(),
				       dsnRoutingVO.getFlightNumber(),
					   dsnRoutingVO.getDepartureDate().toString().substring(0,11)};
				       errors.add(new ErrorVO(WRONG_AGREEMENTYPE,obj));
			     }
			   }
				else {
					Object[] obj = {dsnRoutingVO.getFlightCarrierCode(),
					dsnRoutingVO.getFlightNumber(),
					dsnRoutingVO.getDepartureDate().toString().substring(0,11)};
					errors.add(new ErrorVO(WRONG_AGREEMENTYPE,obj));
				}
		  }
		 else {
			 if(flightvo.getAgreementType()!=null && flightvo.getAgreementType().trim().length()>0 && "L".equals(flightvo.getAgreementType()) && (flightvo.getFlightStatus().equals("ACT")||flightvo.getFlightStatus().equals("TBA") || !activeFlight)) {
				 dsnRoutingVO.setAgreementType(flightvo.getAgreementType());
				}
			}
		if(flightvo.getLegOrigin() != null && flightvo.getLegDestination() != null && flightvo.getFlightRoute() != null
				&& dsnRoutingVO.getFlightCarrierCode().equals(logonAttributes.getOwnAirlineCode())
				&& flightvo.getFlightNumber().equals(dsnRoutingVO.getFlightNumber())){
				if(flightvo.getFlightRoute().contains(dsnRoutingVO.getPol()) && flightvo.getFlightRoute().contains(dsnRoutingVO.getPou())){
					String[] fltRoute= flightvo.getFlightRoute().split("-");
					int polIndex = 0;
					int pouIndex = 0;
					//Code added by Manish for IASCB-46075 start
					boolean isRoundRobinFlt = false;
					if(fltRoute != null && fltRoute.length > 2){
						isRoundRobinFlt = fltRoute[0].equals(fltRoute[fltRoute.length-1]);
						if(isRoundRobinFlt && !flightvo.getFlightRoute().contains(dsnRoutingVO.getPol()) ||
								!flightvo.getFlightRoute().contains(dsnRoutingVO.getPou())){
							isRoundRobinFlt = false;
						}
					}
					//Code added by Manish for IASCB-46075 end
					for(int i=0; i<fltRoute.length ; i++){
						polIndex = fltRoute[i].equals(dsnRoutingVO.getPol()) ? i : polIndex;
						pouIndex = fltRoute[i].equals(dsnRoutingVO.getPou()) ? i : pouIndex;
					}
					if(polIndex > pouIndex && !isRoundRobinFlt){//isRoundRobinFlt check added by Manish for IASCB-46075
						Object[] obj = {dsnRoutingVO.getFlightCarrierCode(),
							dsnRoutingVO.getFlightNumber(),
							dsnRoutingVO.getDepartureDate().toString().substring(0,11)};
							errors.add(new ErrorVO("mailtracking.mra.defaults.despatchrouting.msg.err.invalidRoute",obj));
					}
				}else{
					Object[] obj = {dsnRoutingVO.getFlightCarrierCode(),
							dsnRoutingVO.getFlightNumber(),
							dsnRoutingVO.getDepartureDate().toString().substring(0,11)};
							errors.add(new ErrorVO("mailtracking.mra.defaults.despatchrouting.msg.err.invalidRoute",obj));
				}
			}
		}	
		//Added by A-8176 for ICRD-225429 ends
		}
		}
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;    
					return;
		}
		//Added by A-5945 for ICRD-104289 ends      
		//validating pol pou --->>

		errors = new ArrayList<ErrorVO>();
		Map<String, AirportValidationVO> validCodes = new HashMap<String, AirportValidationVO>();
		AreaDelegate areaDelegate = new AreaDelegate();	

		hiddenopFlg = despatchRoutingForm.getHiddenOpFlag();
		String[] pol=despatchRoutingForm.getPol();
		String[] pou=despatchRoutingForm.getPou();	

		try {

			Collection<String> sectorCodes=new ArrayList<String>();

			if(hiddenopFlg!=null && hiddenopFlg.length>0 ){
				int cnt=0;
				for(String opflag :hiddenopFlg){
					if(pol!=null){						
						if(!"NOOP".equals(opflag)&&!"D".equals(opflag)){								
							sectorCodes.add(pol[cnt]);						
						}							
					}
					if(pou!=null){						
						if(!"NOOP".equals(opflag)&&!"D".equals(opflag)){								
							sectorCodes.add(pou[cnt]);						
						}							
					}							
					cnt= cnt+1;	
				}					
			}	

			validCodes = areaDelegate.validateAirportCodes(logonAttributes.getCompanyCode(), sectorCodes);			

		} catch (BusinessDelegateException businessDelegateException) {
			//printStackTrrace()();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "Sector********************", validCodes);
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;	
		}	    	




		/*
		 * Logic for business of split routes for other airline
		 * split routes and identical routes for own airline  
		 */
		ArrayList<DSNRoutingVO> routingVOs = new ArrayList<DSNRoutingVO>();

		for(DSNRoutingVO dsnRoutingVO : dsnRoutingVOs){
			if(!"D".equals(dsnRoutingVO.getOperationFlag())){
				routingVOs.add(dsnRoutingVO);
			}

		} 
		String secpol="";
		int secnop=0;
		double secwgt=0.0;
		if(routingVOs.size()>0){	       
			secpol = routingVOs.get(0).getPol();
			secnop =routingVOs.get(0).getNopieces();
			secwgt=routingVOs.get(0).getWeight();
		}
		int i =0;
		int erflg=0;
		int spltcnt=0;

		for(DSNRoutingVO dsnRoutingVO : routingVOs){
			if(dsnRoutingVO.getNopieces()>(Integer.parseInt(despatchRoutingForm.getOalPcs()))){
				erflg=2;
				break;
			}   
			/* All the weight related checks are commented bcz weight displaying is changed to Display weight instead of the one which is converted to system unit
			 * try {
				if(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT,UnitConstants.WEIGHT_UNIT_KILOGRAM,dsnRoutingVO.getWeight())>(oalWgt)){
					erflg=4;
					break;
				}
			} catch (UnitException e) {
			
				e.printStackTrace();
			}  */

			if(i>0){	        		
				if(secpol.equalsIgnoreCase(dsnRoutingVO.getPol())){
					// is an own airline identical route
					secnop = secnop + dsnRoutingVO.getNopieces();
					secwgt = secwgt + dsnRoutingVO.getWeight();
					spltcnt=spltcnt+1;
				}else{
					//not an own airline identical route
					if(spltcnt>0){
						//already identical routes exist so check for total 
						if(secnop!=(Integer.parseInt(despatchRoutingForm.getOalPcs()))){
							erflg=1;
							break;
						}
						/*All the weight related checks are commented bcz weight displaying is changed to Display weight instead of the one which is converted to system unit
						 * if(secwgt!=(oalWgt)){
							erflg=3;
							break;
						}*/	        				
					}else {
						//check if any less values are given for non identical routes
						if(secnop<(Integer.parseInt(despatchRoutingForm.getOalPcs()))){			        			
							erflg=5;
							break;
						}
						/* All the weight related checks are commented bcz weight displaying is changed to Display weight instead of the one which is converted to system unit
						 * if(secwgt<(oalWgt)){		        			
							erflg=6;
							break;
						} */
					}

					spltcnt=0;
					secnop= dsnRoutingVO.getNopieces();	
					secwgt=dsnRoutingVO.getWeight();
				}
				secpol=dsnRoutingVO.getPol();
			}	        	
			i=i+1;								
		}


		if(erflg==2){
			errors = new ArrayList<ErrorVO>();    			
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.noofpiecesinvalid");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;    			
			return;				
		}

		if(erflg==4){
			errors = new ArrayList<ErrorVO>();    			
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.weightinvalid");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;    			
			return;				
		}

		if(erflg==5){
			errors = new ArrayList<ErrorVO>();    			
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.nopiecesless");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;    			
			return;				
		}

		if(erflg==6){
			errors = new ArrayList<ErrorVO>();    			
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.weightless");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;    			
			return;				
		}

		//-->>check for last row
		if(spltcnt>0){
			if(secnop!=(Integer.parseInt(despatchRoutingForm.getOalPcs()))){
				erflg=1;				 
			}
			/*
			 * else if(secwgt!=(oalWgt)){
				erflg=3;      				 
			} */
		}else{
			if(secnop<(Integer.parseInt(despatchRoutingForm.getOalPcs()))){			        			
				errors = new ArrayList<ErrorVO>();    			
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.nopiecesless");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;    			
				return;		
			} 
			/* else
				try {
					if(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT,UnitConstants.WEIGHT_UNIT_KILOGRAM,secwgt)<(oalWgt)){		        			
						errors = new ArrayList<ErrorVO>();    			
						ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.weightless");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(errorVO);
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;    			
						return;			
					}
				} catch (UnitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	*/			  
		}
		//--------------->>

		if(erflg==1){
			errors = new ArrayList<ErrorVO>();    			
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.totalnoofpiecesinvalid");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;    			
			return;				
		}

		if(erflg==3){
			errors = new ArrayList<ErrorVO>();    			
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.totalweightinvalid");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;    			
			return;				
		} 


		errors = new ArrayList<ErrorVO>();


		try {
			mailTrackingMRADelegate.saveDSNRoutingDetails(dsnRoutingVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
		}
		if(errors != null && errors.size() > 0) {
			log.log(Log.SEVERE, "SAVE ERROR!!!!");
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		else{					

			try {
				//procedure call to prorate the despatch again with the new route
				mailTrackingMRADelegate.reProrateDSN(dsnRoutingVOs);
			} catch (BusinessDelegateException businessDelegateException) {					
				errors = handleDelegateException(businessDelegateException);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
			}
			if(errors != null && errors.size() > 0) {
				log.log(Log.SEVERE, "RE-PRORATEDSN --->>> ERROR!!!!");
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}else{

				errors = new ArrayList<ErrorVO>();
				errors.add(new ErrorVO("mailtracking.mra.defaults.despatchrouting.msg.info.datasavedsuccessfully"));
				invocationContext.addAllError(errors);
				dsnRoutingSession.removeDSNRoutingVOs();
				dsnRoutingSession.removeDSNRoutingFilterVO();
				despatchRoutingForm.setDsn("");
				despatchRoutingForm.setDsnDate("");
				despatchRoutingForm.setTransferAirline("");
				despatchRoutingForm.setTransferPA("");
				despatchRoutingForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = SAVE_SUCCESS;

				return;
			}
		}

	}
	/**
	 * 
	 * @param despatchRoutingForm
	 * @param dsnRoutingVOs 
	 */
	//changed by A-5945 for ICRD-104289
	private Collection<FlightValidationVO> validateFlightForAirport(DespatchRoutingForm despatchRoutingForm, DSNRoutingVO dsnRoutingVO) {
		Collection<FlightValidationVO> flightValidationVOs = null;
		FlightValidationVO  flightValidationVO = null;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		if(dsnRoutingVO!=null ){
		
				try{
					flightValidationVOs = new MailTrackingMRADelegate().validateFlightForAirport(constructFlightFilterVO(dsnRoutingVO, logonAttributes));
				}catch(BusinessDelegateException businessDelegateException){
					log.log(Log.FINE,  "BusinessDelegateException");
				}
				log.log(Log.FINE, "flightValidationVOs ------------> ", flightValidationVOs);
				log.log(Log.FINE, "flightValidationVOs is NULL");
				if(flightValidationVOs.size() >0){
					flightValidationVO  = flightValidationVOs.iterator().next();
					dsnRoutingVO.setFlightSeqnum(flightValidationVO.getFlightSequenceNumber());

				}
			}
return flightValidationVOs;


	}
	/**
	 * 
	 * @param dsnRoutingVO
	 * @param logonAttributes
	 * @return
	 */
	private FlightFilterVO constructFlightFilterVO(
			DSNRoutingVO dsnRoutingVO, LogonAttributes logonAttributes) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(dsnRoutingVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(dsnRoutingVO.getFlightCarrierId());
		flightFilterVO.setFlightDate(dsnRoutingVO.getDepartureDate());
		flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);			
		flightFilterVO.setFlightNumber(dsnRoutingVO.getFlightNumber());			
		flightFilterVO.setCarrierCode(dsnRoutingVO.getFlightCarrierCode());
		//flightFilterVO.setLoadPlanFlightDate(dsnRoutingVO.getDepartureDate());
		flightFilterVO.setStation(dsnRoutingVO.getPol());			
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setAirportCode(dsnRoutingVO.getPol());
		flightFilterVO.setActiveAlone(true);

		log.log(Log.FINE, "flightFilterVOs  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.------------> ", flightFilterVO);
		return flightFilterVO;
	}
	/*
	 * updateDsnRoutingSession A-3251
	 */
	private void updateDsnRoutingSession(DSNRoutingSession dSNRoutingSession , DespatchRoutingForm despatchRoutingForm){

		ArrayList<DSNRoutingVO> dsnRoutingVOs= (ArrayList<DSNRoutingVO>)dSNRoutingSession.getDSNRoutingVOs();
		ArrayList<DSNRoutingVO> toRemovedsnRoutingVOs = new ArrayList<DSNRoutingVO>();
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		//int serialNumber =1;
		//int csgseqnum=0;
		//String csgdocnum="";
		//String blgbas="";
		//String cmpcod="";
		//String poacod="";	
		long malseqnum=0;

		if(dsnRoutingVOs!=null){
			for(DSNRoutingVO proVO : dsnRoutingVOs ) {							
				malseqnum =   proVO.getMailSequenceNumber();			
			}
		}	    

		String[] hiddenOpFlag=despatchRoutingForm.getHiddenOpFlag();   	

		String[] carrierCode=despatchRoutingForm.getFlightCarrierCode();
		String[] flightNumber=despatchRoutingForm.getFlightNumber();
		String[] departDate =despatchRoutingForm.getDepartureDate();
		String[] pol=despatchRoutingForm.getPol();
		String[] pou=despatchRoutingForm.getPou();
		String[] agreementType = despatchRoutingForm.getAgreementType();
		String[] nopieces=despatchRoutingForm.getNopieces();
		String[] weight=despatchRoutingForm.getWeight();

		String[] blockSpaceType = despatchRoutingForm.getBlockSpaceType();

		/*for(DSNRoutingVO dSNRoutingVO :dsnRoutingVOs){
	    			if("I".equals(dSNRoutingVO.getOperationFlag())){
	    				toRemovedsnRoutingVOs.add(dSNRoutingVO);	
	    			}
	    		}


	    	  if(toRemovedsnRoutingVOs.size()>0){
	    		  dsnRoutingVOs.removeAll(toRemovedsnRoutingVOs);		        
		        }
		 */


		if(hiddenOpFlag!=null && hiddenOpFlag.length>0 ){
			int count=0;
			for(String opflag :hiddenOpFlag){
				if(!"NOOP".equals(opflag)){
					DSNRoutingVO dSNRoutingVO = null;
					if("U".equals(opflag)){
						dSNRoutingVO = dsnRoutingVOs.get(count);
						dSNRoutingVO.setOperationFlag("U");
					}else if("D".equals(opflag)){
						dSNRoutingVO = dsnRoutingVOs.get(count);
						dSNRoutingVO.setOperationFlag("D");
					}else if("I".equals(opflag)){
						dSNRoutingVO = dsnRoutingVOs.get(count);
						dSNRoutingVO.setOperationFlag("I");  
						/*dSNRoutingVO = new DSNRoutingVO();
				        	dSNRoutingVO.setOperationFlag("I");       

				        	//mandatory fields for save
				        	dSNRoutingVO.setCompanyCode(cmpcod);
				        	dSNRoutingVO.setCsgSequenceNumber(csgseqnum);
				        	dSNRoutingVO.setCsgDocumentNumber(csgdocnum);
				        	dSNRoutingVO.setBillingBasis(blgbas);							 
							dSNRoutingVO.setPoaCode(poacod);							
							dSNRoutingVO.setRoutingSerialNumber(serialNumber);				        	
							dSNRoutingVO.setOwnairlinecode(logonAttributes.getOwnAirlineCode());
							dSNRoutingVO.setAcctualnopieces(Integer.parseInt(despatchRoutingForm.getOalPcs()));
							dSNRoutingVO.setAcctualweight(Double.parseDouble(despatchRoutingForm.getOalwgt()));
							dSNRoutingVO.setLegsernum(1);
							serialNumber=serialNumber+1;*/	
					} 			        	
					dSNRoutingVO.setTriggerPoint(DESPATCH_ROUTING);
					dSNRoutingVO.setCompanyCode(logonAttributes.getCompanyCode());
					dSNRoutingVO.setLastUpdateUser(logonAttributes.getUserId());
					dSNRoutingVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
					dSNRoutingVO.setFlightCarrierCode(carrierCode[count]);
					dSNRoutingVO.setFlightNumber(flightNumber[count]);
					dSNRoutingVO.setDepartureDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(departDate[count]));
					dSNRoutingVO.setPol(pol[count]);
					dSNRoutingVO.setPou(pou[count]);
					dSNRoutingVO.setMailSequenceNumber(malseqnum);
					dSNRoutingVO.setTransferAirline(despatchRoutingForm.getTransferAirline());
					dSNRoutingVO.setTransferPA(despatchRoutingForm.getTransferPA());
					
					dSNRoutingVO.setAgreementType(agreementType[count]);
					
					dSNRoutingVO.setBlockSpaceType(blockSpaceType[count]);
					
					if(dSNRoutingVO.getBlockSpaceType()!=null && !"".equals(dSNRoutingVO.getBlockSpaceType()) && !logonAttributes.getOwnAirlineCode().equals(dSNRoutingVO.getFlightCarrierCode())){
						dSNRoutingVO.setBsaReference("TMPBSAREF");
					}

					dSNRoutingVO.setNopieces(Integer.parseInt(nopieces[count]));
				
						try {
							if((UnitFormatter.getRoundedValue(UnitConstants.WEIGHT,UnitConstants.WEIGHT_UNIT_KILOGRAM,dSNRoutingVO.getWeight()) != Double.parseDouble(despatchRoutingForm.getWeight()[count])))


                         dSNRoutingVO.setWeight(Double.parseDouble(weight[count]));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			//Code commented by Manish for IASCB-33315 start
			/*try {
				if((UnitFormatter.getRoundedValue(UnitConstants.WEIGHT,UnitConstants.WEIGHT_UNIT_KILOGRAM,dSNRoutingVO.getAcctualweight()) != Double.parseDouble(despatchRoutingForm.getWeight()[count])))
				{
						dSNRoutingVO.setAcctualweight(Double.parseDouble(weight[count]));
				}
			} catch (NumberFormatException | UnitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Code commented by Manish for IASCB-33315 end
			dSNRoutingVO.setAcctualnopieces(Integer.parseInt(nopieces[count]));
					/* if("I".equals(dSNRoutingVO.getOperationFlag())){
					    	dsnRoutingVOs.add(dSNRoutingVO);
					    	}*/
				}
				count++;	        		
			}
		}	        
		dSNRoutingSession.setDSNRoutingVOs(dsnRoutingVOs);			

	}
	
	/**
	 * Method used for validation from DSN routing screen if we enter Own carrier code in YY sector, flight number is mandatory
	 * @param ownCarrierCode
	 * @param dsnRoutingVOs
	 * @return
	 */
	private boolean validateFlightForOwnAirlineCode(String ownCarrierCode,Collection<DSNRoutingVO> dsnRoutingVOs){
		boolean isValid = false;
		StringBuilder stringData = new StringBuilder("");
		for(DSNRoutingVO dsnRoutingVO : dsnRoutingVOs){
			isValid = false;
			if(ownCarrierCode.equals(dsnRoutingVO.getFlightCarrierCode())){
				try{
					if(Integer.parseInt(dsnRoutingVO.getFlightNumber()) >0)
						{
						isValid = true;
						}
				}catch(NumberFormatException exception){
					isValid = true;
				}
				stringData.append(isValid);
				continue;
			}else
				{
				isValid = true;
				}
				stringData.append(isValid);
		}
		if(stringData.toString().contains("false"))
			{
			isValid = false;
			}
		return isValid;
	}
	/**
	 * @author A-7371
	 * @param flightvo
	 * @return
	 */
	private Collection<FlightSegmentSummaryVO> findFlightSegments(FlightValidationVO flightvo) {
		// TODO Auto-generated method stub
		
		
		Collection<FlightSegmentSummaryVO> segmentSummaryVos = null;
		int segmentSerialNumber = 0;
		log.log(Log.INFO, "Validate the Container For the Flight");
		try {
			segmentSummaryVos = new MailTrackingMRADelegate().findFlightSegments(
					flightvo.getCompanyCode(), flightvo
							.getFlightCarrierId(), flightvo.getFlightNumber(),
					 flightvo.getFlightSequenceNumber());
		} catch (BusinessDelegateException e) {
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		return segmentSummaryVos;
	}

	private void validateTransferPAAndAirline(DespatchRoutingForm  despatchRoutingForm,LogonAttributes logonAttributes, Collection<ErrorVO> errors) {
		
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		String transferPA=despatchRoutingForm.getTransferPA();
		String transferAirline=despatchRoutingForm.getTransferAirline();

		if(transferPA != null  && !transferPA.isEmpty()){
			try {
		  			PostalAdministrationVO postalAdministrationVO =null;
					postalAdministrationVO  = new MailTrackingMRADelegate().findPACode(
									logonAttributes.getCompanyCode(),transferPA);
					if(postalAdministrationVO == null) {
		  				errors.add(new ErrorVO(INVALID_PA));
		  			}
		  	
			}catch (BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> err =  handleDelegateException(businessDelegateException);
				if(err!=null && !err.isEmpty()){
					errors.addAll(err);
				}
			}
		}
		if(transferAirline != null  && !transferAirline.isEmpty()){
		try {
			airlineDelegate.validateAlphaCode(logonAttributes.getCompanyCode(), transferAirline);
		} catch (BusinessDelegateException businessDelegateException) {
				errors.add(new ErrorVO(INVALID_AIRLINE));
		}
		
		}

	}
	
}





