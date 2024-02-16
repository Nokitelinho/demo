/*
 * ListContainerCommand.java Created on Sep 26, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file : com.ibsplc.icargo.presentation.web.command.mail.operations.ux.
 * forcemajeurerequest.ListCommand.java Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : a-8527 :
 * 22-Nov-2018 : Draft
 */

public class ListCommand extends BaseCommand {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";
	private static final String TARGET = "list_success";
	private Log log = LogFactory.getLogger("Mail Operations force majeure request");
	private static final String NO_RESULT = "mailtracking.defaults.forcemajeure.msg.err.noresultsfound";
	private static final String SCREENLOAD_FAILURE="screen_failure";
	private static final String ONETIMEBNDTYPE="mailtracking.defaults.operationtype";
	/**
	 * 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE,
				"\n\n in the list command of New tab force Majeure Request----------> \n\n");
		ForceMajeureRequestForm forceMajeureRequestForm = (ForceMajeureRequestForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		ForceMajeureRequestSession forceMajeureRequestSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		  MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
			      new MailTrackingDefaultsDelegate();
		  AirlineDelegate airlineDelegate = new AirlineDelegate();

		Page<ForceMajeureRequestVO> forceMajeureRequestVOs = null;

		ForceMajeureRequestFilterVO forceMajeureRequestFilterVO = null;
		
		if(forceMajeureRequestSession.getFilterParamValues() != null){
			forceMajeureRequestFilterVO = forceMajeureRequestSession.getFilterParamValues();
		}else{
			forceMajeureRequestFilterVO = new ForceMajeureRequestFilterVO();
		}
		
		forceMajeureRequestFilterVO.setCompanyCode(companyCode);
		forceMajeureRequestFilterVO.setCurrentAirport(logonAttributes.getAirportCode());
		forceMajeureRequestForm.setUserId("");    
	
		if (forceMajeureRequestForm.getOrigin_airport() != null
				&& forceMajeureRequestForm.getOrigin_airport().trim().length() > 0) {
			errors=validateAirport(forceMajeureRequestForm.getOrigin_airport(),companyCode);
	
			forceMajeureRequestFilterVO.setOrginAirport(forceMajeureRequestForm
					.getOrigin_airport());
		}else{
			forceMajeureRequestFilterVO.setOrginAirport("");
		}
		if (forceMajeureRequestForm.getDestination() != null
				&& forceMajeureRequestForm.getDestination().trim().length() > 0) {
			
			errors=validateAirport(forceMajeureRequestForm.getDestination(),companyCode);
			forceMajeureRequestFilterVO
					.setDestinationAirport(forceMajeureRequestForm
							.getDestination());
		}else{
			forceMajeureRequestFilterVO.setDestinationAirport("");
		}
		if (forceMajeureRequestForm.getPacode() != null
				&& forceMajeureRequestForm.getPacode().trim().length() > 0) {
			forceMajeureRequestFilterVO.setPoaCode(forceMajeureRequestForm
					.getPacode());
		}else{
			forceMajeureRequestFilterVO.setPoaCode("");
		}
		if (forceMajeureRequestForm.getViaPoint() != null
				&& forceMajeureRequestForm.getViaPoint().trim().length() > 0) {
			errors=validateAirport(forceMajeureRequestForm.getViaPoint(),companyCode);
			forceMajeureRequestFilterVO.setViaPoint(forceMajeureRequestForm
					.getViaPoint());
		}else{
			forceMajeureRequestFilterVO.setViaPoint("");
		}
		if (forceMajeureRequestForm.getAffectedAirport() != null
				&& forceMajeureRequestForm.getAffectedAirport().trim().length() > 0) {
			String [] aff_airport= forceMajeureRequestForm.getAffectedAirport().split(",");
			for(String affairport:aff_airport){
			errors= validateAirport(affairport,companyCode);
			}
			forceMajeureRequestFilterVO.setAffectedAirport(forceMajeureRequestForm.getAffectedAirport());
		}else{
			forceMajeureRequestFilterVO.setAffectedAirport("");
		}
		if (errors != null && errors.size() > 0) {     
			invocationContext.addAllError(errors);
            invocationContext.target = SCREENLOAD_FAILURE;
            return;
		}
		 AirlineValidationVO airlineValidationVO = null;
		if(forceMajeureRequestForm.getCarrierCode()!=null && forceMajeureRequestForm.getCarrierCode().trim().length()>0){
			
			 try {  
				 
				 
					 airlineValidationVO = airlineDelegate.validateAlphaCode(
	 					logonAttributes.getCompanyCode(),
	 					forceMajeureRequestForm.getCarrierCode().trim().toUpperCase());
					 forceMajeureRequestFilterVO.setCarrierID(airlineValidationVO.getAirlineIdentifier());
				 }
	 		catch (BusinessDelegateException businessDelegateException) {
	 			errors = handleDelegateException(businessDelegateException);
	 		}
			 if (errors != null && errors.size() > 0) {            			
	 			Object[] obj = {forceMajeureRequestForm.getCarrierCode().trim().toUpperCase()};
	 			ErrorVO error = new ErrorVO("mailtracking.defaults.forcemajeure.invalidcarrier",obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
	            invocationContext.target = SCREENLOAD_FAILURE;
	            return;
	 		}
			}
		if(forceMajeureRequestForm.getFlightNumber()!=null &&forceMajeureRequestForm.getFlightNumber().trim().length()>0){
			FlightFilterVO flightFilterVO = handleFlightFilterVO(forceMajeureRequestForm,logonAttributes);
			flightFilterVO.setCarrierCode(forceMajeureRequestForm.getCarrierCode().trim().toUpperCase());
		    flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		    flightFilterVO.setFlightNumber(forceMajeureRequestForm.getFlightNumber());
			forceMajeureRequestFilterVO.setFlightNumber(forceMajeureRequestForm.getCarrierCode().trim().toUpperCase()+" "+forceMajeureRequestForm
					.getFlightNumber());	
			try {
				 Collection flightValidationVOs = null;
			
			      this.log.log(3, new Object[] { "FlightFilterVO ------------> ", flightFilterVO });
			      flightValidationVOs = 
			        mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
			    }
			    catch (BusinessDelegateException businessDelegateException) {
			      errors = handleDelegateException(businessDelegateException);
			    }
			    if ((errors != null) && (errors.size() > 0)) {
			    	Object[] obj = {forceMajeureRequestForm.getFlightNumber().trim()};
			    	ErrorVO error = new ErrorVO("mailtracking.defaults.forcemajeure.invalidFlightNo",obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					invocationContext.addAllError(errors);
			      invocationContext.target = SCREENLOAD_FAILURE;
			      return;
			    }
		}else{
			forceMajeureRequestFilterVO.setFlightNumber("");
		}
		

		if(forceMajeureRequestForm.getActionFlag()!=null && forceMajeureRequestForm.getActionFlag().trim().length()>0 ){
			forceMajeureRequestForm.setActionFlag(forceMajeureRequestForm.getActionFlag());
			}
		LocalDate fromdate = null;
		if(forceMajeureRequestForm.getFrmDate() != null && forceMajeureRequestForm.getFrmDate().trim().length() >0){
			int fromTimeInhours = 0;
			int fromTimeInMinutes = 0;
			if (forceMajeureRequestForm.getFrmTime() != null && forceMajeureRequestForm.getFrmTime().contains(":")) {
				
				if (forceMajeureRequestForm.getFrmTime().length() == 5) {
					if (forceMajeureRequestForm.getFrmTime().substring(0, 2) != null) {       
						fromTimeInhours = Integer
								.parseInt(forceMajeureRequestForm.getFrmTime().substring(0, 2));
					}
					if (forceMajeureRequestForm.getFrmTime().substring(3, 5) != null) {
						fromTimeInMinutes = Integer
								.parseInt(forceMajeureRequestForm.getFrmTime().substring(3, 5));
					}

				}      
			}else{
				fromTimeInhours=0;
				fromTimeInMinutes=0;
			}
			
		fromdate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				true).setDate(forceMajeureRequestForm.getFrmDate());
		fromdate.addHours(fromTimeInhours);
		fromdate.addMinutes(fromTimeInMinutes);
		}
		LocalDate todate = null;
		if(forceMajeureRequestForm.getToDate() != null && forceMajeureRequestForm.getToDate().trim().length() >0){
			int toTimeInhours = 0;
			int toTimeInMinutes = 0;
			if (forceMajeureRequestForm.getToTime() != null && forceMajeureRequestForm.getToTime().contains(":")) {
				
				if (forceMajeureRequestForm.getToTime().length() == 5) {
					if (forceMajeureRequestForm.getToTime().substring(0, 2) != null) {
						toTimeInhours = Integer
								.parseInt(forceMajeureRequestForm.getToTime().substring(0, 2));
					}
					if (forceMajeureRequestForm.getToTime().substring(3, 5) != null) {
						toTimeInMinutes = Integer
								.parseInt(forceMajeureRequestForm.getToTime().substring(3, 5));
					}

				}
			}else{
				toTimeInhours=23;       
				toTimeInMinutes=59;
			}      
		 todate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				true).setDate(forceMajeureRequestForm.getToDate());
		 todate.addHours(toTimeInhours);
		 todate.addMinutes(toTimeInMinutes);
		}
		LocalDate fltdate = null;
		if(forceMajeureRequestForm.getFlightDateStr() != null && forceMajeureRequestForm.getFlightDateStr().trim().length() > 0)
			 fltdate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false).setDate(forceMajeureRequestForm.getFlightDateStr());
		forceMajeureRequestFilterVO.setFlightDate(fltdate);
		forceMajeureRequestFilterVO.setFromDate(fromdate);
		forceMajeureRequestFilterVO.setToDate(todate);
		forceMajeureRequestFilterVO.setSource(forceMajeureRequestForm
				.getSource());
		forceMajeureRequestFilterVO.setSortingField(forceMajeureRequestForm.getSortingField());
		forceMajeureRequestFilterVO.setSortOrder(forceMajeureRequestForm.getSortOrder());
		if(forceMajeureRequestForm.getNewTabRemarks()!=null&& forceMajeureRequestForm.getNewTabRemarks().trim().length()>0){
		forceMajeureRequestFilterVO.setReqRemarks(forceMajeureRequestForm.getNewTabRemarks());
		}
		else{
			forceMajeureRequestFilterVO.setReqRemarks("");	
		}
		StringBuffer filterParameters = new StringBuffer();
		filterParameters.append("ORG:"
				+ forceMajeureRequestForm.getOrigin_airport().trim() + ";");
		filterParameters.append("DST:"
				+ forceMajeureRequestForm.getDestination().trim() + ";");
		filterParameters.append("POA:"
				+ forceMajeureRequestForm.getPacode().trim() + ";");
		filterParameters.append("VPT:"
				+ forceMajeureRequestForm.getViaPoint().trim() + ";");
		filterParameters.append("AFAPT:"
				+ forceMajeureRequestForm.getAffectedAirport().trim() + ";");
		filterParameters.append("FLNO:"
				+ forceMajeureRequestForm.getFlightNumber().trim() + ";");
		filterParameters.append("FLDAT:"
				+ forceMajeureRequestForm.getFlightDateStr().trim() + ";");
		filterParameters.append("FRDAT:"
				+ forceMajeureRequestForm.getFrmDate().trim() + ";");
		filterParameters.append("TODAT:"
				+ forceMajeureRequestForm.getToDate().trim() + ";");
		filterParameters.append("SRC:"
				+ forceMajeureRequestForm.getSource().trim());
		forceMajeureRequestFilterVO.setFilterParameters(filterParameters
				.toString());
		forceMajeureRequestFilterVO.setScanType(forceMajeureRequestForm.getScanType());
		//Scan type codes in details starts
		if(forceMajeureRequestForm.getScanType()!=null && !forceMajeureRequestForm.getScanType().isEmpty()){
			String[] scanTypes = forceMajeureRequestForm.getScanType()
					.split(",");
			ArrayList<OneTimeVO> onteTimeValues=forceMajeureRequestSession.getScanType();
			StringBuilder builder = new StringBuilder(); 
			int count=0;
			for (String type : scanTypes) {
		        count=count+1;
				if(count>1){
					builder.append(",");
				}
				for (OneTimeVO oneTime:onteTimeValues){        
					if(type.equals(oneTime.getFieldValue())){
						builder.append(oneTime.getFieldDescription());
					}
				}
			}
			if(builder!=null)
			forceMajeureRequestFilterVO.setScanTypeDetail(builder.toString());
		}
		
		
		
		
		//Scan type codes in details end
		forceMajeureRequestSession.setFilterParamValues(forceMajeureRequestFilterVO);
		int displaypage=1;
		if(forceMajeureRequestFilterVO.getTotalRecords() == 0){
			forceMajeureRequestFilterVO.setTotalRecords(-1);
		}
		if(forceMajeureRequestForm.getDefaultPageSize()!=null && forceMajeureRequestForm.getDefaultPageSize().trim().length()>0){
			forceMajeureRequestFilterVO.setDefaultPageSize(Integer.parseInt(forceMajeureRequestForm.getDefaultPageSize()));
		}
		if(forceMajeureRequestForm.getDisplayPage()!=null&& forceMajeureRequestForm.getDisplayPage().trim().length()>0){
			displaypage=Integer.parseInt(forceMajeureRequestForm.getDisplayPage());
		}
		
		forceMajeureRequestVOs = listForceMajeureApplicableMails(
							forceMajeureRequestFilterVO,displaypage );
			if (forceMajeureRequestVOs == null
					|| forceMajeureRequestVOs.size() == 0) {
				log.log(Log.SEVERE,
						"\n\n *******no record found********** \n\n");
				ErrorVO error = new ErrorVO(NO_RESULT);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);

				invocationContext.target = TARGET;
				return;
			}
			Map<String,String> map = new HashMap<String,String>();
			Map<String, Collection<OneTimeVO>> oneTimeValues = new HashMap<String, Collection<OneTimeVO>>();
		    Collection <String> oneTimeList = new ArrayList<String>();
		    oneTimeList.add(ONETIMEBNDTYPE); 
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			try {
				oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				handleDelegateException(businessDelegateException);
			}
			if (forceMajeureRequestVOs != null
					&& forceMajeureRequestVOs.size() > 0) {
				for(OneTimeVO onetime : oneTimeValues.get(ONETIMEBNDTYPE)){
					map.put(onetime.getFieldValue(), onetime.getFieldDescription());
				}
				for(ForceMajeureRequestVO reqVO : forceMajeureRequestVOs){
					if(reqVO.getType() != null){
						reqVO.setType(map.get(reqVO.getType()));
					}
				}
				forceMajeureRequestForm.setDisplaytype("SHOWNEW");
				forceMajeureRequestForm.setNewTabRemarks("");
				forceMajeureRequestSession
						.setTotalRecords(forceMajeureRequestVOs.size());
				forceMajeureRequestSession
						.setListforcemajeurevos(forceMajeureRequestVOs);
				
			}
		
		log.exiting("ListCommand", "execute");

	}

	private Page<ForceMajeureRequestVO> listForceMajeureApplicableMails(
			ForceMajeureRequestFilterVO forceMajeureRequestFilterVO,
			int pageNumber) {
		Page<ForceMajeureRequestVO> listForceMajeureVOs = null;
		log.log(Log.INFO,
				"\n\n ******* Inside listForceMajeureApplicableMails********** \n\n");
		try {

			listForceMajeureVOs = new MailTrackingDefaultsDelegate()
					.listForceMajeureApplicableMails(
							forceMajeureRequestFilterVO, pageNumber);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return listForceMajeureVOs;
	}
	
	private FlightFilterVO handleFlightFilterVO(ForceMajeureRequestForm forceMajeureRequestForm, LogonAttributes logonAttributes)
	  {
	    FlightFilterVO flightFilterVO = new FlightFilterVO();

	    flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	    flightFilterVO.setStringFlightDate(forceMajeureRequestForm.getFlightDateStr());
	    LocalDate date = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);;
	    flightFilterVO.setFlightDate(date.setDate(forceMajeureRequestForm.getFlightDateStr()));
	    return flightFilterVO;
	  }
	private Collection<ErrorVO> validateAirport(String airportcode,String companycode){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AirportValidationVO airportValidationVO = null;
		 AreaDelegate areaDelegate = new AreaDelegate();
		try {        			
 			airportValidationVO = areaDelegate.validateAirportCode(companycode,
 					airportcode.toUpperCase());
 		}catch (BusinessDelegateException businessDelegateException) {
 			errors = handleDelegateException(businessDelegateException);
 		}
 		/*if (errors != null && errors.size() > 0) { 
 			Object[] obj = {airportcode};
 			ErrorVO error = new ErrorVO("mailtracking.defaults.invalidairport",
 			   		new Object[]{airportcode.toUpperCase()});
 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
 			errors.add(error);
 			
 		}*/
 		return errors;
	}
	
}
