package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.modifyroute;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.FlightDetail;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.ModifyRouteFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.ModifyRouteModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

public class ExecuteCommand extends AbstractCommand{

	private Log log = LogFactory.getLogger("Modify Route");
	private static final String MODULE_NAME = "mail.mra";
	private static final String CLASS_NAME="ExecuteCommand";
	private static final String SUCCESS="success";
	private static final String WRONG_AGREEMENTYPE = "mail.mra.defaults.modifyroute.error.wrongagreement";
	private static final String INVALID_BSA = "mail.mra.defaults.modifyroute.error.bsanotexist";
	private static final String INVALID_BSA_OWN = "mail.mra.defaults.modifyroute.error.bsanotexistforowncarrier";
	private static final String VALID_BSA="validbsa";
	private static final String CARCOD_YY="YY";
	private static final String FLNUM_ZERO="0000";
	private static final String INVALID_FLIGHT="mail.mra.defaults.modifyroute.error.noflightdetails";
	private static final String INVALID_FLIGHT_ROUTE="mail.mra.defaults.modifyroute.error.invalidRoute";
	private static final String EXECUTED_SUCCESSFULLY="mail.mra.defaults.modifyroute.info.executesuccess"; 
	private static final String LSTPROEXP="LSTPROEXP";
	private static final String MADATORY_FILTER = "mail.mra.defaults.modifyroute.error.mandatoryfilter";
	private static final String ROUTE_SIZE_EXCEEDED = "mail.mra.defaults.modifyroute.error.routesize";
	private static final String DATERANGE_EXCEED = "mail.mra.defaults.modifyroute.error.daterangeexceed";
	private static final String SYSPAR_DATERANGEFORBULKUPDATE="mailtracking.mra.rangeforbulkrouteupdate";
	private static final String INVALID_PA="mail.mra.defaults.modifyroute.error.invalidtransferpa";
	private static final String INVALID_AIRLINE="mail.mra.defaults.modifyroute.error.invalidtransferairline";
	
	
	

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		this.log.entering(MODULE_NAME, CLASS_NAME);
		LogonAttributes logonAttributes = getLogonAttribute();
		ModifyRouteModel modifyRouteModel = (ModifyRouteModel)actionContext.getScreenModel();
		ArrayList<ErrorVO> errors= new ArrayList<>();
		GPABillingEntriesFilterVO gPABillingEntriesFilterVO=new GPABillingEntriesFilterVO();
		
		validateTransferPAAndAirline(modifyRouteModel,logonAttributes,errors);
		
		if (errors.isEmpty()) {
		validateFilterConditions(modifyRouteModel.getModifyRouteFilter(),errors);
		}
		
		if (errors.isEmpty()) {
		validateCarrierAndAirport(modifyRouteModel.getFlightDetails(),logonAttributes,errors);
		}
		
		if (errors.isEmpty()) {
		validateRouteDetails(modifyRouteModel.getFlightDetails(),logonAttributes,errors);
		}
		
		if (!errors.isEmpty()) {
			actionContext.addAllError(errors);
			return;
		}

		updateFilterDetails(modifyRouteModel.getFlightDetails(),modifyRouteModel.getModifyRouteFilter(),modifyRouteModel,logonAttributes,gPABillingEntriesFilterVO);
		
		
		if(gPABillingEntriesFilterVO.getFlightDetails().length()>4000){
		errors.add(new ErrorVO(ROUTE_SIZE_EXCEEDED));
		}
		
		try{
		 new MailTrackingMRADelegate().updateRouteAndReprorate(gPABillingEntriesFilterVO);
		}catch(BusinessDelegateException businessDelegateException){
			errors=(ArrayList)handleDelegateException(businessDelegateException);
			actionContext.addAllError(errors);
			return;
		}
		
		
		 ErrorVO error = new ErrorVO(EXECUTED_SUCCESSFULLY);
		 error.setErrorDisplayType(ErrorDisplayType.INFO);
		 errors.add(error);
		 actionContext.addAllError(errors);	
		
	     List<ModifyRouteModel> results = new ArrayList<>();
	     results.add(modifyRouteModel);
		 ResponseVO responseVO = new ResponseVO();
		 responseVO.setStatus(SUCCESS);
		 responseVO.setResults(results); 
		 actionContext.setResponseVO(responseVO);
		this.log.exiting(MODULE_NAME, CLASS_NAME);
		
	}
	
	private void validateTransferPAAndAirline(ModifyRouteModel modifyRouteModel,LogonAttributes logonAttributes, ArrayList<ErrorVO> errors) {
	
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		String transferPA=modifyRouteModel.getTransferPA();
		String transferAirline=modifyRouteModel.getTransferAirline();

		if(transferPA != null  && !transferPA.isEmpty()){
			try {
		  			PostalAdministrationVO postalAdministrationVO =null;
					postalAdministrationVO  = new MailTrackingMRADelegate().findPACode(
									logonAttributes.getCompanyCode(),transferPA);
					if(postalAdministrationVO == null) {
		  				errors.add(new ErrorVO(INVALID_PA));
		  			}
		  	
			}catch (BusinessDelegateException businessDelegateException) {
				List<ErrorVO> err =  handleDelegateException(businessDelegateException);
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

	private void validateFilterConditions(ModifyRouteFilter modifyRouteFilter, ArrayList<ErrorVO> errors) {
		
		Map<String, String> systemParameterValues = null;
		ArrayList<String> systemparameterTypes = new ArrayList<>();
    	systemparameterTypes.add(SYSPAR_DATERANGEFORBULKUPDATE);
    	int daterangeConfigValue=0;
    	
		if (LSTPROEXP.equals(modifyRouteFilter.getFromScreen())) {

			if ((modifyRouteFilter.getFromDate() == null || modifyRouteFilter.getFromDate().isEmpty()
					|| modifyRouteFilter.getToDate() == null || modifyRouteFilter.getToDate().isEmpty())
					&& (modifyRouteFilter.getCarrierCode() == null || modifyRouteFilter.getCarrierCode().isEmpty()
							|| modifyRouteFilter.getFlightNumber() == null
							|| modifyRouteFilter.getFlightNumber().isEmpty()
							|| modifyRouteFilter.getFlightDate() == null
							|| modifyRouteFilter.getFlightDate().isEmpty())) {
				Object[] obj = { "Flight details or Date range is " };
				errors.add(new ErrorVO(MADATORY_FILTER, obj));
			} 
				try {
					systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(systemparameterTypes);
				} catch (BusinessDelegateException e) {
					this.log.log(Log.INFO, e);
				}
				daterangeConfigValue =	systemParameterValues!=null ? Integer.valueOf(systemParameterValues.get(SYSPAR_DATERANGEFORBULKUPDATE)):0;
				if (modifyRouteFilter.getFromDate() != null && !modifyRouteFilter.getFromDate().isEmpty()
						&& modifyRouteFilter.getToDate() != null && !modifyRouteFilter.getToDate().isEmpty()
						&& DateUtilities.getDifferenceInDays(modifyRouteFilter.getFromDate(),
								modifyRouteFilter.getToDate()) > daterangeConfigValue) {

					Object[] obj = { daterangeConfigValue };
					errors.add(new ErrorVO(DATERANGE_EXCEED, obj));
				}
			
		}
	}

	private GPABillingEntriesFilterVO updateFilterDetails(Collection<FlightDetail> flightDetails,
			ModifyRouteFilter modifyRouteFilter,ModifyRouteModel modifyRouteModel,LogonAttributes logonAttributes,GPABillingEntriesFilterVO gPABillingEntriesFilterVO) {
		
		
		gPABillingEntriesFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		gPABillingEntriesFilterVO.setCarrierCode(modifyRouteFilter.getCarrierCode());
		gPABillingEntriesFilterVO.setFlightNumber(modifyRouteFilter.getFlightNumber());
		gPABillingEntriesFilterVO.setOrigin(modifyRouteFilter.getOrigin());
		gPABillingEntriesFilterVO.setDestination(modifyRouteFilter.getDestination());
		gPABillingEntriesFilterVO.setMailbagId(modifyRouteFilter.getMailbagID());
		gPABillingEntriesFilterVO.setOriginOfficeOfExchange(modifyRouteFilter.getOriginExchangeOffice());
		gPABillingEntriesFilterVO.setDestinationOfficeOfExchange(modifyRouteFilter.getDestinationExchangeOffice());
		gPABillingEntriesFilterVO.setMailCategoryCode(modifyRouteFilter.getCategory());
		gPABillingEntriesFilterVO.setMailSubclass(modifyRouteFilter.getSubClass());
		gPABillingEntriesFilterVO.setYear(modifyRouteFilter.getYear());
		gPABillingEntriesFilterVO.setDsnNumber(modifyRouteFilter.getDsn());
		gPABillingEntriesFilterVO.setRsn(modifyRouteFilter.getRsn());
		gPABillingEntriesFilterVO.setHni(modifyRouteFilter.getHni());
		gPABillingEntriesFilterVO.setRegInd(modifyRouteFilter.getRi());
		gPABillingEntriesFilterVO.setAssignedStatus(modifyRouteFilter.getAssignedStatus());
		gPABillingEntriesFilterVO.setAssignee(modifyRouteFilter.getAssignee());
		gPABillingEntriesFilterVO.setExceptionCode(modifyRouteFilter.getExceptionCode());
		gPABillingEntriesFilterVO.setFromScreen(modifyRouteFilter.getFromScreen());
		
		if(modifyRouteFilter.getFlightDate()!=null && !modifyRouteFilter.getFlightDate().isEmpty()){
		gPABillingEntriesFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false).setDate( modifyRouteFilter.getFlightDate()));
		}
		
		if(modifyRouteFilter.getFromDate()!=null && !modifyRouteFilter.getFromDate().isEmpty()){
			gPABillingEntriesFilterVO.setFromDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate( modifyRouteFilter.getFromDate()));
		}
		if(modifyRouteFilter.getToDate()!=null && !modifyRouteFilter.getToDate().isEmpty()){
			gPABillingEntriesFilterVO.setToDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate( modifyRouteFilter.getToDate()));
		}
		

		setRoutingDetails(flightDetails,gPABillingEntriesFilterVO);
		gPABillingEntriesFilterVO.setTransferPA(modifyRouteModel.getTransferPA());
		gPABillingEntriesFilterVO.setTransferAirline(modifyRouteModel.getTransferAirline());
		if(modifyRouteFilter.getSelectedMalSeqNum()!=null && modifyRouteFilter.getSelectedMalSeqNum().trim().length()>0){
			gPABillingEntriesFilterVO.setMailSeqNumbers(modifyRouteFilter.getSelectedMalSeqNum().substring(1, modifyRouteFilter.getSelectedMalSeqNum().length()));
		}else{
			gPABillingEntriesFilterVO.setMailSeqNumbers("");
		}

		return gPABillingEntriesFilterVO;
	}

	private void setRoutingDetails(Collection<FlightDetail> flightDetails,
			GPABillingEntriesFilterVO gPABillingEntriesFilterVO) {
		
		final String COLUMN_SEPERATOR="#";
		final String ROW_SEPERATOR="@";
		StringBuilder fligData=new StringBuilder();
		
		flightDetails.forEach(flightDetail->{
			fligData.
			append(flightDetail.getCarrierId()).append(COLUMN_SEPERATOR).
			append(flightDetail.getCarrierCode()).append(COLUMN_SEPERATOR).
			append(flightDetail.getFlightNumber()).append(COLUMN_SEPERATOR).
			append(flightDetail.getFlightSeqNum()).append(COLUMN_SEPERATOR).
			append(flightDetail.getSegmentSerNum()).append(COLUMN_SEPERATOR).
			append(flightDetail.getFlightDate()).append(COLUMN_SEPERATOR).
			append(flightDetail.getPol()).append(COLUMN_SEPERATOR).
			append(flightDetail.getPou()).append(COLUMN_SEPERATOR).
			append(flightDetail.getAgreementType()).append(COLUMN_SEPERATOR).
			append(flightDetail.getBlockSpace()).append(COLUMN_SEPERATOR).
			append(flightDetail.getFlightType()).
			append(ROW_SEPERATOR);
		});
		
		gPABillingEntriesFilterVO.setFlightDetails(fligData.toString());

		
	}

	private List<ErrorVO> validateBSA(FlightDetail flightDetail,LogonAttributes logonAttributes,ArrayList<ErrorVO> errors)  {
		
		DSNRoutingVO dSNRoutingVO =new DSNRoutingVO();
		String bsaValidationStus=null;
		dSNRoutingVO.setCompanyCode(logonAttributes.getCompanyCode());
    	dSNRoutingVO.setFlightNumber(flightDetail.getFlightNumber());
    	dSNRoutingVO.setPol(flightDetail.getPol().toUpperCase());
    	dSNRoutingVO.setPou(flightDetail.getPou().toUpperCase());
    	dSNRoutingVO.setDepartureDate(new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false).setDate( flightDetail.getFlightDate()));
    	dSNRoutingVO.setBlockSpaceType(flightDetail.getBlockSpace());
    	dSNRoutingVO.setFlightCarrierCode(flightDetail.getCarrierCode());
    	dSNRoutingVO.setFlightCarrierId(flightDetail.getCarrierId());
 
    	if(logonAttributes.getOwnAirlineCode().equals(dSNRoutingVO.getFlightCarrierCode())){
    		 Object[] obj = {flightDetail.getCarrierCode(),
		    		   flightDetail.getFlightNumber(),
		    		   flightDetail.getFlightDate()};
		     errors.add(new ErrorVO(INVALID_BSA_OWN,obj));
		     return errors;
    	}

    	try {
			 bsaValidationStus = new MailTrackingMRADelegate().validateBSA(dSNRoutingVO);
		} catch (BusinessDelegateException e) {
			log.log(Log.INFO, "bsaValidationStus", bsaValidationStus);  
		}
    	
    	if(!VALID_BSA.equals(bsaValidationStus)){
    		 Object[] obj = {flightDetail.getCarrierCode(),
		    		   flightDetail.getFlightNumber(),
		    		   flightDetail.getFlightDate()};
		     errors.add(new ErrorVO(INVALID_BSA,obj));
    	}

		return errors;
		
	}
	
	/**
	 * 
	 * 	Method		:	ExecuteCommand.validateCarrierCodeAndSectors
	 *	Added by 	:	A-8061 on 15-Dec-2020
	 * 	Used for 	:
	 *	Parameters	:	@param flightDetails
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@param errors
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	List<ErrorVO>
	 */
	private List<ErrorVO> validateCarrierAndAirport(Collection<FlightDetail> flightDetails,
			LogonAttributes logonAttributes, ArrayList<ErrorVO> errors) throws BusinessDelegateException {
		Set<String> carrierCodes = new HashSet<>();
		Map<String, AirlineValidationVO> validealphaCodes = new HashMap<>();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		Set<String> sectorCodes = new HashSet<>();
		AreaDelegate areaDelegate = new AreaDelegate();

		if (flightDetails != null && !flightDetails.isEmpty()) {

			flightDetails.forEach(flightDetail -> {
				carrierCodes.add(flightDetail.getCarrierCode());
				sectorCodes.add(flightDetail.getPol());
				sectorCodes.add(flightDetail.getPou());
			});

			try {
				validealphaCodes = airlineDelegate.validateAlphaCodes(logonAttributes.getCompanyCode(), carrierCodes);
				areaDelegate.validateAirportCodes(logonAttributes.getCompanyCode(), sectorCodes);

			} catch (BusinessDelegateException businessDelegateException) {
				List<ErrorVO> err =  handleDelegateException(businessDelegateException);
				if(err!=null && !err.isEmpty()){
					errors.addAll(err);
				}
			}

			if (validealphaCodes != null && !validealphaCodes.isEmpty()) {
				for (FlightDetail flightDetail : flightDetails) {
					flightDetail
							.setCarrierId(validealphaCodes.get(flightDetail.getCarrierCode()).getAirlineIdentifier());
				}
			}
		}
		return errors;
	}	
	
	
	
	private boolean isRoundRobinFlt(FlightValidationVO flightValidationVO,String[] fltRoute ,FlightDetail flightDetail){
		
		boolean isRoundRobinFlt = false;

		if (fltRoute != null && fltRoute.length > 2) {
			isRoundRobinFlt = fltRoute[0].equals(fltRoute[fltRoute.length - 1]);
			if (isRoundRobinFlt && !flightValidationVO.getFlightRoute().contains(flightDetail.getPol())
					|| !flightValidationVO.getFlightRoute().contains(flightDetail.getPou())) {
				isRoundRobinFlt = false;
			}
		}
		
		
		return isRoundRobinFlt;
		
	}
	
	private boolean isActiveOrRemove(FlightValidationVO flightValidationVO, FlightDetail flightDetail,
			Iterator<FlightValidationVO> flightterator, boolean isRoundRobinFlt, int polIndex, int pouIndex) {
		boolean activeFlight = false;

		if (!flightValidationVO.getFlightRoute().contains(flightDetail.getPol())
				|| !flightValidationVO.getFlightRoute().contains(flightDetail.getPou())
				|| (polIndex > pouIndex && !isRoundRobinFlt)) {
			flightterator.remove();
		} else {
			if (flightValidationVO.getFlightStatus().equals("ACT")
					|| flightValidationVO.getFlightStatus().equals("TBA")) {
				activeFlight = true;
			}
		}
		return activeFlight;
	}

	private boolean isActiveFlight(Collection<FlightValidationVO> flightValidationVOs, FlightDetail flightDetail) {

		boolean activeFlight = false;
		Iterator<FlightValidationVO> flightterator = flightValidationVOs.iterator();
		FlightValidationVO flightValidationVO = null;
		while (flightterator.hasNext()) {
			flightValidationVO = flightterator.next();
			int polIndex = 0;
			int pouIndex = 0;
			String[] fltRoute = flightValidationVO.getFlightRoute().split("-");

			boolean isRoundRobinFlt = isRoundRobinFlt(flightValidationVO, fltRoute, flightDetail);

			if (fltRoute != null) {
				for (int i = 0; i < fltRoute.length; i++) {
					polIndex = fltRoute[i].equals(flightDetail.getPol()) ? i : polIndex;
					pouIndex = fltRoute[i].equals(flightDetail.getPou()) ? i : pouIndex;
				}
			}
			activeFlight = isActiveOrRemove(flightValidationVO, flightDetail, flightterator, isRoundRobinFlt, polIndex,
					pouIndex);

		}
		return activeFlight;

	}
	
	private void validateEmptyFlight(Collection<FlightValidationVO> flightValidationVOs,FlightDetail flightDetail,ArrayList<ErrorVO> errors,LogonAttributes logonAttributes){
		
		if ((flightValidationVOs==null ||flightValidationVOs.isEmpty())&& flightDetail.getCarrierCode().equals(logonAttributes.getOwnAirlineCode())) {
			Object[] obj = {flightDetail.getCarrierCode(),
					flightDetail.getFlightNumber(),
					flightDetail.getFlightDate()};
			        errors.add(new ErrorVO(INVALID_FLIGHT,obj));
}
	}
	
	
	
	private void validateAgreementAndRoute(Collection<FlightValidationVO> flightValidationVOs,
			FlightDetail flightDetail, LogonAttributes logonAttributes, ArrayList<ErrorVO> errors,
			boolean activeFlight) {

		for (FlightValidationVO flightvo : flightValidationVOs) {
			validateAgreementType(flightvo, flightDetail, activeFlight, errors);
			if (!errors.isEmpty()) {
				return;
			}
			validateFlightRoute(flightvo, flightDetail, logonAttributes, errors);
			if (!errors.isEmpty()) {
				return;
			}


		}
	}
	
	
	private List<ErrorVO> validateRouteDetails(Collection<FlightDetail> flightDetails,LogonAttributes logonAttributes,ArrayList<ErrorVO> errors)  {

			for(FlightDetail flightDetail: flightDetails){
				boolean activeFlight=false;
				
				if (flightDetail.getFlightType() == null || "null".equals(flightDetail.getFlightType())) {
					flightDetail.setFlightType("");
				}
				if(!CARCOD_YY.equals(flightDetail.getCarrierCode()) &&
						flightDetail.getFlightNumber()!=null && flightDetail.getFlightNumber().trim().length()>0 &&
						!FLNUM_ZERO.equals(flightDetail.getFlightNumber())){
					
				Collection<FlightValidationVO> flightValidationVOs=validateFlightForAirport(flightDetail);

				validateEmptyFlight(flightValidationVOs,flightDetail,errors,logonAttributes);
				if(!errors.isEmpty()){
					return errors;
				}

				if(flightValidationVOs !=null && flightValidationVOs.size() >1) {
				activeFlight = isActiveFlight(flightValidationVOs,flightDetail);
				}
				
				validateAgreementAndRoute(flightValidationVOs,flightDetail,logonAttributes,errors,activeFlight);
				}
				
				if(flightDetail.getBlockSpace()!=null && !flightDetail.getBlockSpace().isEmpty()){
					validateBSA(flightDetail, logonAttributes, errors);
				}
	
			}
			
			return errors;
		}
		
	private void validateRoute(FlightValidationVO flightvo,FlightDetail flightDetail,ArrayList<ErrorVO> errors){
		
		String[] fltRoute= flightvo.getFlightRoute().split("-");
		int polIndex = 0;
		int pouIndex = 0;
		
		boolean isRoundRobinFlt = isRoundRobinFlt(flightvo, fltRoute, flightDetail);

		if(fltRoute!=null){
		for(int i=0; i<fltRoute.length ; i++){
			polIndex = fltRoute[i].equals(flightDetail.getPol()) ? i : polIndex;
			pouIndex = fltRoute[i].equals(flightDetail.getPou()) ? i : pouIndex;
		}
		}
		if(polIndex > pouIndex && !isRoundRobinFlt){
			Object[] obj = {flightDetail.getCarrierCode(),
					flightDetail.getFlightNumber(),
				flightDetail.getFlightDate()};
				errors.add(new ErrorVO(INVALID_FLIGHT_ROUTE,obj));
		}
		
	}
	private ArrayList<ErrorVO> validateFlightRoute(FlightValidationVO flightvo,FlightDetail flightDetail,LogonAttributes logonAttributes,ArrayList<ErrorVO> errors) {
		
		if(flightvo.getLegOrigin() != null && flightvo.getLegDestination() != null && flightvo.getFlightRoute() != null
				&& flightDetail.getCarrierCode().equals(logonAttributes.getOwnAirlineCode())
				&& flightvo.getFlightNumber().equals(flightDetail.getFlightNumber())){
			
				if(flightvo.getFlightRoute().contains(flightDetail.getPol()) && flightvo.getFlightRoute().contains(flightDetail.getPou())){
					validateRoute(flightvo, flightDetail, errors);
				}
				else{
					Object[] obj = {flightDetail.getCarrierCode(),
							flightDetail.getFlightNumber(),
							flightDetail.getFlightDate()};
							errors.add(new ErrorVO(INVALID_FLIGHT_ROUTE,obj));
				}
			}
		
		return errors;
	}

	private void validateAgreementType(FlightValidationVO flightvo,FlightDetail flightDetail,boolean activeFlight,ArrayList<ErrorVO> errors) {
		
		if (flightvo.getFlightStatus().equals("ACT") || flightvo.getFlightStatus().equals("TBA") || !activeFlight) {
			flightDetail.setFlightType(flightvo.getFlightType());
		}
		if (flightDetail.getAgreementType() != null && flightDetail.getAgreementType().trim().length() > 0) {
			if (flightvo.getAgreementType() != null && flightvo.getAgreementType().trim().length() > 0) {
				if (!flightDetail.getAgreementType().equals(flightvo.getAgreementType())) {
					Object[] obj = { flightDetail.getCarrierCode(), flightDetail.getFlightNumber(),
							flightDetail.getFlightDate() };
					errors.add(new ErrorVO(WRONG_AGREEMENTYPE, obj));
				}
			} else {
				Object[] obj = { flightDetail.getCarrierCode(), flightDetail.getFlightNumber(),
						flightDetail.getFlightDate() };
				errors.add(new ErrorVO(WRONG_AGREEMENTYPE, obj));
			}
		} else if ("L".equals(flightvo.getAgreementType()) && (flightvo.getFlightStatus().equals("ACT")
							|| flightvo.getFlightStatus().equals("TBA") || !activeFlight)) {
				flightDetail.setAgreementType(flightvo.getAgreementType());
		}

	}

	private Collection<FlightValidationVO> validateFlightForAirport(FlightDetail flightDetails) {
		Collection<FlightValidationVO> flightValidationVOs = null;
		FlightValidationVO  flightValidationVO = null;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			try{
				flightValidationVOs = new MailTrackingMRADelegate().validateFlightForAirport(constructFlightFilterVO(flightDetails, logonAttributes));
			}catch(BusinessDelegateException businessDelegateException){
					log.log(Log.FINE,  "BusinessDelegateException");
			}
			if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
					flightValidationVO  = flightValidationVOs.iterator().next();
					flightDetails.setFlightSeqNum(flightValidationVO.getFlightSequenceNumber());
			}
			return flightValidationVOs;
	}
	
	
	private FlightFilterVO constructFlightFilterVO(FlightDetail flightDetail, LogonAttributes logonAttributes) {
		
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightCarrierId(flightDetail.getCarrierId());
		flightFilterVO.setFlightDate(
				new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(flightDetail.getFlightDate()));
		flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
		flightFilterVO.setFlightNumber(flightDetail.getFlightNumber());
		flightFilterVO.setCarrierCode(flightDetail.getCarrierCode());
		flightFilterVO.setStation(flightDetail.getPol());
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setAirportCode(flightDetail.getPol());
		
		return flightFilterVO;
	}

	
}
