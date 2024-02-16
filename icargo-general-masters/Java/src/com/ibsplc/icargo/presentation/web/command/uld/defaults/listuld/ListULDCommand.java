/*
 * ListULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * This command class is used to list the details of the specified ULD  
 * @author A-1347
 */
public class ListULDCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ULD");
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID =
		"uld.defaults.listuld";
	private static final String LISTULD_SUCCESS = "list_success";
	 
	private static final String LISTSTATUS = "noListForm";
	private static final String LISTULD_FAILURE = "list_failure";
    
	private static final String OPERATSTATUS_ONETIME = "uld.defaults.operationalStatus";
	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
	private static final String ULDNATURE_ONETIME = "uld.defaults.uldnature";
	
	//Added By A-6841 for CRQ ICRD-155382
	private static final String OCCUPANCYSTATUS_ONETIME = "uld.defaults.occupiedstatus";
	private static final String INTRANSIT_ONETIME = "uld.defaults.transitstatus";
	// Added by Preet for bug ULD 74 
	private static final String CLEANSTATUS_ONETIME = "uld.defaults.cleanlinessStatus";

	private static final String LEVEL_TYPE="uld.defaults.leveltype";
	private static final String CONTENT_TYPE="uld.defaults.contentcodes";
	private static final String FACILITY_TYPE="uld.defaults.facilitytypes";
    
    private static final String YES = "Y";
  //added by A-4443 for icrd-3722 strats
    public static final String SCREEN_STATUS_INVALID = "errors_present";
  //added by A-4443 for icrd-3722 ends
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	invocationContext.target = LISTULD_SUCCESS;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ListULDForm listULDForm = (ListULDForm) invocationContext.screenModel;
		ListULDSession listULDSession = 
							(ListULDSession)getScreenSession(MODULE,SCREENID);
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		listULDSession.setOneTimeValues(oneTimeValues);
		/*
		 * Validate for client errors
		 */
		Collection<ErrorVO> errors = null;
		Boolean inValidAirline = false;
		
		ULDListFilterVO uldListFilterVO = null;
		log.log(Log.INFO, " listStatus---->", listULDSession.getListStatus());
		log.log(Log.INFO, "StatusFlag---->", listULDForm.getStatusFlag());
		if(listULDSession.getListStatus()!=null && ("monitorStock").equals(listULDSession.getListStatus())){
			listULDSession.setListStatus(LISTSTATUS);
			listULDForm.setScreenLoadStatus("monitorStock");
		}
		if(listULDSession.getListStatus()!=null && ("noListForm").equals(listULDSession.getListStatus())){
			//listULDForm.setUldNumber(null);
			//Commented above code by A-7359 for ICRD-296240
		}
		if("fromSCM".equals(listULDForm.getListStatus())){
			listULDSession.setListStatus(LISTSTATUS);
		}
		if(logonAttributes.isAirlineUser()){
    		listULDForm.setDisableStatus("airline");
    	}
    	else{
    		listULDForm.setDisableStatus("GHA");
    	}
		log.log(Log.INFO, " listStatus---->", listULDSession.getListStatus());
		if((!(LISTSTATUS.equals(listULDSession.getListStatus()))) &&
				(!("recorduld".equals(listULDForm.getStatusFlag())))) {
			uldListFilterVO = new ULDListFilterVO();
			log.log(Log.INFO,"is comung here");
			uldListFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			// Added by A-2412 for AgentFilter
			uldListFilterVO.setAgentCode(listULDForm.getAgentCode());
			errors = validateForm(listULDForm,
					logonAttributes.getCompanyCode());
			if(!loadUldFilterFromForm(listULDForm,uldListFilterVO)) {
				if((listULDForm.getAirlineCode() == null ||
						listULDForm.getAirlineCode().trim().length() == 0) &&
						(listULDForm.getLastMovementDate() == null ||
								listULDForm.getLastMovementDate().trim().length() == 0)) {
						ErrorVO error = new ErrorVO(
										"uld.defaults.atlestonefieldmandatory");
						errors.add(error);
				}
			}
			Collection<ErrorVO> errorsAirlineandDate = 
									validateAirlineandDate(listULDForm,
					logonAttributes.getCompanyCode(),uldListFilterVO);
			if(errorsAirlineandDate!=null && errorsAirlineandDate.size() > 0 ) {
				errors.addAll(errorsAirlineandDate);
				invocationContext.addAllError(errors);
				//added by A-4443 for icrd-3722 strats
				listULDSession.setListFilterVO(uldListFilterVO);
				listULDSession.setListDisplayPage(null);
				listULDForm.setDisableStatus(SCREEN_STATUS_INVALID);
		    	invocationContext.target = LISTULD_FAILURE;
				return;
				//added by A-4443 for icrd-3722 ends
			}
			
			listULDSession.setIsListed(true);
			listULDSession.setListFilterVO(uldListFilterVO);
		}
		else if(listULDSession.getIsListed()) {
			log.log(Log.INFO,"is comung here---->2");
			log.log(Log.FINE, "checked.length-------------------->"
					);
			uldListFilterVO = listULDSession.getListFilterVO();
			if(uldListFilterVO!=null){
				uldListFilterVO.setFromListULD(true);
				listULDForm.setDisplayPage("1");
				listULDForm.setLastPageNum("0");
				listULDSession.setListStatus("");
				listULDForm.setStatusFlag("");
				if(uldListFilterVO.getLevelType()!=null){
					listULDForm.setLevelType(uldListFilterVO.getLevelType());
				}
				//Added by A-7359 for ICRD-296240 starts here
				if(uldListFilterVO.getUldNumber()!=null){
					listULDForm.setUldNumber(uldListFilterVO.getUldNumber());
				}
				//Added by A-7359 for ICRD-296240 ends here
				//added by A-2883
				if(uldListFilterVO.isViewByNatureFlag()){
					listULDForm.setUldNature(uldListFilterVO.getUldNature());
		    		listULDForm.setUldTypeCode(uldListFilterVO.getUldTypeCode());
				}else{
					listULDForm.setUldGroupCode(uldListFilterVO.getUldGroupCode());
				}
				Collection<ErrorVO> errorsAirlineandDate = 
					validateAirline(uldListFilterVO);
				if(errorsAirlineandDate!=null && errorsAirlineandDate.size() > 0 ) {
					inValidAirline = true;
				}
			}
		}
		else {
			listULDSession.setListStatus("");
			listULDForm.setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = LISTULD_FAILURE;
			return;
		}
		
		if(listULDForm.getOwnerAirline() != null && listULDForm.getOwnerAirline().equals(logonAttributes.getOwnAirlineCode()) && "on".equalsIgnoreCase(listULDForm.getOalUldOnly()))
		{
			ErrorVO error = new ErrorVO("uld.defaults.listuld.correctthefilter");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			listULDForm.setDisableStatus(SCREEN_STATUS_INVALID);
	    	invocationContext.target = LISTULD_FAILURE;
			return;
		}
		
		if("YES".equals(listULDForm.getCountTotalFlag())&& listULDSession.getTotalRecords() != 0){
			uldListFilterVO.setTotalRecords(listULDSession.getTotalRecords());
		}else{
			uldListFilterVO.setTotalRecords(-1);
		}
		 //Added by A-7359 for ICRD-228547
		listULDForm.setFilterSummaryDetails(populateFilterDetails(uldListFilterVO));
		log.log(Log.FINE, "checked.length-------------------->",
				uldListFilterVO);
		Page<ULDListVO> uldListVOs = new Page<ULDListVO>(
							new ArrayList<ULDListVO>(), 0, 0, 0, 0, 0, false);
		
		try {
			uldListVOs = new ULDDefaultsDelegate().findULDList(
					uldListFilterVO,Integer.parseInt(listULDForm.getDisplayPage())) ;
		}
		catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if(errors != null &&
				errors.size() > 0 ) {
			 log.log(Log.FINE,"Inside errors in ListULDCommand");
				listULDSession.setListDisplayPage(null);
				invocationContext.addAllError(errors);
				invocationContext.target = LISTULD_FAILURE;
				return;
		}		
		if(listULDForm.getAirlineCode() != null) {
			uldListFilterVO.setAirlineCode(listULDForm.getAirlineCode());
		}
		if(inValidAirline && uldListVOs != null && uldListVOs.size()> 0){
			//added by A-4443 for icrd-3722 starts
			listULDSession.setListFilterVO(uldListFilterVO);
			listULDSession.setListDisplayPage(null);
			listULDForm.setDisableStatus(SCREEN_STATUS_INVALID);
			invocationContext.target = LISTULD_FAILURE;
			//added by A-4443 for icrd-3722 ends
			return;
		}
		else if(uldListVOs != null && uldListVOs.size()> 0) {
			log
					.log(Log.FINE, "ULDLIST VOS INSIDE LIST ULD COMMAND",
							uldListVOs);
			listULDSession.setListDisplayPage(uldListVOs);
			log.log(Log.FINE, "TotalRecords", uldListVOs.getTotalRecordCount());
			listULDSession.setTotalRecords(uldListVOs.getTotalRecordCount());
		}
		else {
			listULDForm.setFilterSummaryDetails("");//FilterSummaryDetails set as empty as part of ICRD-256470
			listULDSession.setListFilterVO(uldListFilterVO);
			listULDSession.setListDisplayPage(null);
			ErrorVO error = new ErrorVO("uld.defaults.listuld.norecordsfound");
	     	error.setErrorDisplayType(ErrorDisplayType.ERROR);
	     	errors = new ArrayList<ErrorVO>();
	     	errors.add(error);
	     	invocationContext.addAllError(errors);
	     	//added by A-4443 for icrd-3722 strats
	     	listULDForm.setDisableStatus(SCREEN_STATUS_INVALID);
	    	invocationContext.target = LISTULD_FAILURE;
			return;
			//added by A-4443 for icrd-3722 ends
		}
		listULDForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		
		invocationContext.target = LISTULD_SUCCESS;
		
    }


	private Collection<ErrorVO> validateForm(
								ListULDForm listULDForm,String companyCode) {
		log.log(Log.FINE,"*****************inside validate form**************");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(listULDForm.getUldNumber()!= null &&
				listULDForm.getUldNumber().trim().length() >0) {
			log.log(Log.FINE,"******************inside uldnum*****************");
			if((listULDForm.getUldRangeFrom()!= null &&
					listULDForm.getUldRangeFrom().trim().length() >0) ||
					(listULDForm.getUldRangeTo()!= null &&
							listULDForm.getUldRangeTo().trim().length() >0)) {
				log.log(Log.FINE,"**************inside uldrange****************");
				 error = new ErrorVO(
							 "uld.defaults.invalidfilterentry");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
			}
			
		}
		else if((listULDForm.getUldRangeFrom()!= null &&
				listULDForm.getUldRangeFrom().trim().length() >0) ||
				(listULDForm.getUldRangeTo()!= null &&
						listULDForm.getUldRangeTo().trim().length() >0)) {
			if((listULDForm.getUldRangeFrom() == null ||
					listULDForm.getUldRangeFrom().trim().length() ==0)) {
				 error = new ErrorVO(
							 "uld.defaults.uldRangeFromMandatory");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				
			}
			
			if((listULDForm.getUldRangeTo() == null ||
					listULDForm.getUldRangeTo().trim().length() ==0)) {
				 error = new ErrorVO(
								 "uld.defaults.uldRangeTOMandatory");
						 error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				
			}
			
			if((listULDForm.getUldTypeCode() == null ||
					listULDForm.getUldTypeCode().trim().length() ==0)) {
					 error = new ErrorVO(
							 "uld.defaults.uldTypeMandatory");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				
			}
			
			/* Commented by A-3415 for ICRD-114094
			 * if((listULDForm.getAirlineCode() == null ||
					listULDForm.getAirlineCode().trim().length() ==0)) {
				 error = new ErrorVO(
								 "uld.defaults.airlineMandatory");
						 error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				
			}*/
			
			if((listULDForm.getUldRangeFrom()!= null &&
					listULDForm.getUldRangeFrom().trim().length() >0) &&
					(listULDForm.getUldRangeTo()!= null &&
							listULDForm.getUldRangeTo().trim().length() >0)) {
				if(Integer.parseInt(listULDForm.getUldRangeFrom())
						> Integer.parseInt(listULDForm.getUldRangeTo())) {
					 error = new ErrorVO(
								 "uld.defaults.uldrangefromgreaterthanrangeto");
						 error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}
				
			}
		}
		else if (((!("").equals(listULDForm.getFromDate())) && listULDForm
				.getFromDate() != null)
				&& ((!("").equals(listULDForm.getToDate())) && listULDForm
						.getToDate() != null)) {
			if (!listULDForm.getFromDate().equals(
					listULDForm.getToDate())) {
				if (!DateUtilities.isLessThan(listULDForm
						.getFromDate(), listULDForm.getToDate(),
						"dd-MMM-yyyy")) {
					error= new ErrorVO(
							"uld.defaults.fromdateearliertodate");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
				}
			}
		}			
		return errors;
	}
	
	private Collection<ErrorVO> validateAirlineandDate(ListULDForm listULDForm,
			String companyCode,ULDListFilterVO uldListFilterVO) {
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		
		if(listULDForm.getLastMovementDate() != null && 
				listULDForm.getLastMovementDate().trim().length() > 0) {
			LocalDate lastMovementDate = null;
			if(DateUtilities.isValidDate(
							listULDForm.getLastMovementDate(),"dd-MMM-yyyy")) {
				lastMovementDate = new LocalDate(
				  getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
				lastMovementDate.setDate(listULDForm.getLastMovementDate());				
			}
			else {
				error = new ErrorVO("uld.defaults.invaliddate",
						new Object[]{listULDForm.getLastMovementDate()});
				errors.add(error);
			}
			uldListFilterVO.setLastMovementDate(lastMovementDate);
		}
		//added by a-3045 for CR AirNZ415 starts
		if(listULDForm.getFromDate() != null && 
				 listULDForm.getFromDate().trim().length() > 0) {
			LocalDate fromDate = null;
			if(DateUtilities.isValidDate(
							listULDForm.getFromDate(),"dd-MMM-yyyy")) {
				fromDate = new LocalDate(
				  getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
				fromDate.setDate(listULDForm.getFromDate());				
			}
			else {
				error = new ErrorVO("uld.defaults.invaliddate",
						new Object[]{listULDForm.getFromDate()});
				errors.add(error);
			}
			uldListFilterVO.setFromDate(fromDate);			
		}
		if(listULDForm.getToDate() != null && 
				listULDForm.getToDate().trim().length() > 0) {
			LocalDate toDate = null;
			if(DateUtilities.isValidDate(
							listULDForm.getToDate(),"dd-MMM-yyyy")) {
				toDate = new LocalDate(
				  getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);				 
				toDate.setDate(listULDForm.getToDate());					
			}
			else {
				error = new ErrorVO("uld.defaults.invaliddate",
						new Object[]{listULDForm.getToDate()});
				errors.add(error);
			}
			uldListFilterVO.setToDate(toDate);		
		}				 
		Map<String,AirlineValidationVO> airlineMap = new HashMap<String,AirlineValidationVO>();
		Collection<String> airlineCodes = new ArrayList<String>();
		if((listULDForm.getAirlineCode() != null 
				&& listULDForm.getAirlineCode().trim().length() > 0)
				||(listULDForm.getOwnerAirline() != null 
						&& listULDForm.getOwnerAirline().trim().length() > 0)){			
			String cmpcod =  getApplicationSession().getLogonVO().getCompanyCode();
			if((listULDForm.getAirlineCode() != null 
					&& listULDForm.getAirlineCode().trim().length() > 0) 
					&& !airlineCodes.contains(listULDForm.getAirlineCode())){
				airlineCodes.add(listULDForm.getAirlineCode().toUpperCase());
			}
			if((listULDForm.getOwnerAirline() != null 
					&& listULDForm.getOwnerAirline().trim().length() > 0) 
					&& !airlineCodes.contains(listULDForm.getOwnerAirline())){
				airlineCodes.add(listULDForm.getOwnerAirline().toUpperCase());	
			}			
			Collection<ErrorVO> errorsAirline = null;
			try {	
				airlineMap = new AirlineDelegate().validateAlphaCodes(cmpcod,airlineCodes);
			}
			catch(BusinessDelegateException businessDelegateException) {
				errorsAirline = 
				handleDelegateException(businessDelegateException);
	       	}
			if(errorsAirline != null &&
				errorsAirline.size() > 0) {
					errors.addAll(errorsAirline);
			}
			if("null".equalsIgnoreCase(listULDForm.getOalUldOnly())){
			uldListFilterVO.setAirlineCode(
							 listULDForm.getAirlineCode().toUpperCase());
			}
			uldListFilterVO.setOwnerAirline(
						     listULDForm.getOwnerAirline().toUpperCase());			
		}		
		if(listULDForm.getOwnerAirline() != null
				&& listULDForm.getOwnerAirline().trim().length() > 0) {			
			AirlineValidationVO airlineVO = airlineMap.get(
					listULDForm.getOwnerAirline().toUpperCase());	
			if(airlineVO != null){			
			uldListFilterVO.setOwnerAirlineidentifier(airlineVO.getAirlineIdentifier());
			}
		}		
		if(listULDForm.getAirlineCode() != null
				&& listULDForm.getAirlineCode().trim().length() > 0) {
			AirlineValidationVO airlineVO = airlineMap.get(
					listULDForm.getAirlineCode().toUpperCase());
			if(airlineVO != null){			
			uldListFilterVO.setAirlineidentifier(airlineVO.getAirlineIdentifier());
			}
		}		
        //added by a-3045 for CR AirNZ415 ends
		return errors;
	}
	private Collection<ErrorVO> validateAirline(ULDListFilterVO uldListFilterVO) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map<String,AirlineValidationVO> airlineMap = new HashMap<String,AirlineValidationVO>();
		Collection<String> airlineCodes = new ArrayList<String>();
		log.log(Log.FINE, "uldListFilterVO.getAirlineCode()=============>",
				uldListFilterVO.getAirlineCode());
		log.log(Log.FINE, "uldListFilterVO.getOwnerAirline()=============>",
				uldListFilterVO.getOwnerAirline());
		if((uldListFilterVO.getAirlineCode() != null 
				&& uldListFilterVO.getAirlineCode().trim().length() > 0)
				||(uldListFilterVO.getOwnerAirline() != null 
						&& uldListFilterVO.getOwnerAirline().trim().length() > 0)){			
			String cmpcod =  getApplicationSession().getLogonVO().getCompanyCode();
			if((uldListFilterVO.getAirlineCode() != null 
					&& uldListFilterVO.getAirlineCode().trim().length() > 0)) {
				if(!airlineCodes.contains(uldListFilterVO.getAirlineCode())){
					airlineCodes.add(uldListFilterVO.getAirlineCode().toUpperCase());
				}
			}
			if((uldListFilterVO.getOwnerAirline() != null 
					&& uldListFilterVO.getOwnerAirline().trim().length() > 0)) {
				if(!airlineCodes.contains(uldListFilterVO.getOwnerAirline())){
					airlineCodes.add(uldListFilterVO.getOwnerAirline().toUpperCase());	
				}
			}			
			Collection<ErrorVO> errorsAirline = null;
			try {	
				airlineMap = new AirlineDelegate().validateAlphaCodes(cmpcod,airlineCodes);
			}
			catch(BusinessDelegateException businessDelegateException) {
				errorsAirline = 
				handleDelegateException(businessDelegateException);
	       	}
			if(errorsAirline != null &&
				errorsAirline.size() > 0) {
				log.log(Log.FINE,
						"uldListFilterVO.getOwnerAirline()=============>",
						errorsAirline.size());
					errors.addAll(errorsAirline);
			}
	
		}		

		return errors;
	} 
	
    private boolean loadUldFilterFromForm(
    				ListULDForm listULDForm,ULDListFilterVO uldListFilterVO) {
    	boolean isValid = false;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	if((listULDForm.getUldNumber() != null &&
				listULDForm.getUldNumber().trim().length() > 0)) {
    		uldListFilterVO.setUldNumber(
    								listULDForm.getUldNumber().toUpperCase());
    		isValid = true;
    	}
    	
    	if((listULDForm.getUldGroupCode() != null &&
				listULDForm.getUldGroupCode().trim().length() > 0)) {
    		uldListFilterVO.setUldGroupCode(
    							  listULDForm.getUldGroupCode().toUpperCase());
    		isValid = true;
    	}
    	
    	if((listULDForm.getUldTypeCode() != null &&
				listULDForm.getUldTypeCode().trim().length() > 0)) {
    		uldListFilterVO.setUldTypeCode(
    								listULDForm.getUldTypeCode().toUpperCase());
    		isValid = true;
    	}
    	    	
    	if((listULDForm.getOverallStatus() != null &&
				listULDForm.getOverallStatus().trim().length() > 0)) {
    		uldListFilterVO.setOverallStatus(listULDForm.getOverallStatus());
    		isValid = true;
    	}
    	
    	if((listULDForm.getDamageStatus() != null &&
				listULDForm.getDamageStatus().trim().length() > 0)) {
    		uldListFilterVO.setDamageStatus(listULDForm.getDamageStatus());
    		isValid = true;
    	}
    	
    	if((listULDForm.getCleanlinessStatus() != null &&
				listULDForm.getCleanlinessStatus().trim().length() > 0)) {
    		uldListFilterVO.setCleanlinessStatus(
    										listULDForm.getCleanlinessStatus());
    		isValid = true;
    	}
    	
    	if((listULDForm.getManufacturer() != null &&
				listULDForm.getManufacturer().trim().length() > 0)) {
    		uldListFilterVO.setManufacturer(
    							listULDForm.getManufacturer());
    		isValid = true;
    	}
    	
    	if((listULDForm.getLocation() != null &&
				listULDForm.getLocation().trim().length() >0)) {
    		uldListFilterVO.setLocation(
    								listULDForm.getLocation().toUpperCase());
    		isValid = true;
    	}
    	
    	if((listULDForm.getOwnerStation() != null &&
				listULDForm.getOwnerStation().trim().length() > 0)) {
    		uldListFilterVO.setOwnerStation(
    							listULDForm.getOwnerStation().toUpperCase());
    		isValid = true;
    	}
    	
    	if((listULDForm.getUldNature() != null &&
				listULDForm.getUldNature().trim().length() > 0)) {
    		uldListFilterVO.setUldNature(
    							listULDForm.getUldNature().toUpperCase());
    		isValid = true;
    	}
    	
    	
    	if((listULDForm.getCurrentStation() != null &&
				listULDForm.getCurrentStation().trim().length() > 0)) {
    		uldListFilterVO.setCurrentStation(
    							listULDForm.getCurrentStation().toUpperCase());
    		isValid = true;
    	}
    	
    	
    	
    	if((listULDForm.getUldRangeFrom() != null &&
				listULDForm.getUldRangeFrom().trim().length() > 0)) {
    		uldListFilterVO.setUldRangeFrom(
    						Integer.parseInt(listULDForm.getUldRangeFrom()));
    		isValid = true;
    	}
    	else {
    		listULDForm.setUldRangeFrom("");
    		uldListFilterVO.setUldRangeFrom(-1);
    	}
    	if((listULDForm.getUldRangeTo() != null &&
				listULDForm.getUldRangeTo().trim().length() > 0)) {
    		uldListFilterVO.setUldRangeTo(
    							Integer.parseInt(listULDForm.getUldRangeTo()));
    		isValid = true;
    	}
    	else {
    		listULDForm.setUldRangeTo("");
    		uldListFilterVO.setUldRangeTo(-1);
    	}
    	
    	if(listULDForm.getLevelType()!=null && listULDForm.getLevelType().trim().length()>0){
    		uldListFilterVO.setLevelType(listULDForm.getLevelType());
    		isValid=true;
    	}
    	else{
    		listULDForm.setLevelType("");
    	}
    	
    	if(listULDForm.getLevelValue()!=null && listULDForm.getLevelValue().trim().length()>0){
    		uldListFilterVO.setLevelValue(listULDForm.getLevelValue());
    		isValid=true;
    	}
    	else{
    		listULDForm.setLevelValue("");
    	}
    	
    	if(listULDForm.getContent()!=null && listULDForm.getContent().trim().length()>0){
    		uldListFilterVO.setContent(listULDForm.getContent());
    		isValid=true;
    	}
    	else{
    		listULDForm.setContent("");
    	}
    	//added by a-3045 for bug 46783 on 19May09 starts
    	if(listULDForm.getOffairportFlag()!=null && listULDForm.getOffairportFlag().trim().length()>0){
    		uldListFilterVO.setOffairportFlag(listULDForm.getOffairportFlag());
    		isValid=true;
    	}
    	//Added By A-6841 for CRQ ICRD-155382	
    	if(listULDForm.getOccupiedULDFlag()!=null && listULDForm.getOccupiedULDFlag().trim().length()>0){
    		uldListFilterVO.setOccupiedULDFlag(listULDForm.getOccupiedULDFlag());
    		isValid=true;
    	}
    	if(listULDForm.getInTransitFlag()!=null && listULDForm.getInTransitFlag().trim().length()>0){ 
    		uldListFilterVO.setInTransitFlag(listULDForm.getInTransitFlag());
    		isValid=true;
    	}
    	//added by a-3045 for bug 46783 on 19May09 ends
    	//Added by A-3415 for ICRD-114094 Starts
    	if(listULDForm.getOwnerAirline()!=null && listULDForm.getOwnerAirline().trim().length()>0){
    		uldListFilterVO.setOwnerAirline(listULDForm.getOwnerAirline());
    		isValid=true;
    	}
    	if(listULDForm.getOalUldOnly()!=null && listULDForm.getOalUldOnly().trim().length()>0){
    		uldListFilterVO.setOalUldOnly(listULDForm.getOalUldOnly());
    	}
    	if("on".equalsIgnoreCase(listULDForm.getOalUldOnly())){
    	uldListFilterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
    	}
    	//Added by A-3415 for ICRD-114094 Ends
    	uldListFilterVO.setFromListULD(true);
   		return isValid;
	}
    /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ListULDCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.INFO, "oneTimeValuesExceptions ---> " );
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ListULDCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}

	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ListULDCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();


    	parameterTypes.add(OPERATSTATUS_ONETIME);
    	parameterTypes.add(DAMAGESTATUS_ONETIME);
    	parameterTypes.add(ULDNATURE_ONETIME);
    	//Added By A-6841 for CRQ ICRD-155382
    	parameterTypes.add(OCCUPANCYSTATUS_ONETIME);
    	parameterTypes.add(INTRANSIT_ONETIME);

    	//Added by Preet for bug ULD 74 starts
    	parameterTypes.add(CLEANSTATUS_ONETIME);
    	
    	parameterTypes.add(LEVEL_TYPE);
    	parameterTypes.add(CONTENT_TYPE);
    	parameterTypes.add(FACILITY_TYPE);
    	//Added by Preet for bug ULD 74 ends
    	log.exiting("ListULDCommand","getOneTimeParameterTypes");
    	return parameterTypes;
    }
	/**
	 * 	Method		:	ListULDCommand.populateFilterDetails
	 *	Added by 	:	A-7359 on 23-Jan-2018
	 * 	Used for 	:	ICRD-228547
	 *	Parameters	:	@param uldListFilterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	private String populateFilterDetails(ULDListFilterVO uldListFilterVO) {
		log.entering(" ListULDCommand", "populateFilterDetails");
		String finalFilter = "";
		if (uldListFilterVO.getUldNumber() != null
				&& uldListFilterVO.getUldNumber().trim().length() > 0) {
			finalFilter = "ULD No :" + uldListFilterVO.getUldNumber();
		}
		if (uldListFilterVO.getUldGroupCode() != null
				&& uldListFilterVO.getUldGroupCode().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "ULD Group :" + uldListFilterVO.getUldGroupCode();

		}
		if (uldListFilterVO.getUldTypeCode() != null
				&& uldListFilterVO.getUldTypeCode().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "ULD Type Code :" + uldListFilterVO.getUldTypeCode();

		}
		if (uldListFilterVO.getAirlineCode() != null
				&& uldListFilterVO.getAirlineCode().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Operating Airline :"
					+ uldListFilterVO.getAirlineCode();

		}
		if (uldListFilterVO.getOwnerAirline() != null
				&& uldListFilterVO.getOwnerAirline().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Owner Airline :"
					+ uldListFilterVO.getOwnerAirline();

		}
		if (uldListFilterVO.getManufacturer() != null
				&& uldListFilterVO.getManufacturer().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Manufacturer :" + uldListFilterVO.getManufacturer();

		}
		if (uldListFilterVO.getDamageStatus() != null
				&& uldListFilterVO.getDamageStatus().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Damage Status : "
					+ getDescofOneTime(uldListFilterVO.getDamageStatus(),
							DAMAGESTATUS_ONETIME);

		}
		if (uldListFilterVO.getOverallStatus() != null
				&& uldListFilterVO.getOverallStatus().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Overall Status : "
					+ getDescofOneTime(uldListFilterVO.getOverallStatus(),
							OPERATSTATUS_ONETIME);

		}
		if (uldListFilterVO.getCleanlinessStatus() != null
				&& uldListFilterVO.getCleanlinessStatus().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Cleanliness Status : "
					+ getDescofOneTime(uldListFilterVO.getCleanlinessStatus(),
							CLEANSTATUS_ONETIME);

		}
		if (uldListFilterVO.getAgentCode() != null
				&& uldListFilterVO.getAgentCode().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Agent Code :" + uldListFilterVO.getAgentCode();

		}
		if (uldListFilterVO.getCurrentStation() != null
				&& uldListFilterVO.getCurrentStation().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Current Airport :"
					+ uldListFilterVO.getCurrentStation();

		}
		if (uldListFilterVO.getOwnerStation() != null
				&& uldListFilterVO.getOwnerStation().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Owner Airport :"
					+ uldListFilterVO.getOwnerStation();

		}
		if (uldListFilterVO.getLastMovementDate() != null) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "ULD Not Moved Since :"
					+ uldListFilterVO.getLastMovementDate()
							.toDisplayDateOnlyFormat();

		}
		if (uldListFilterVO.getUldNature() != null
				&& uldListFilterVO.getUldNature().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "ULD Nature : "
					+ getDescofOneTime(uldListFilterVO.getUldNature(),
							ULDNATURE_ONETIME);

		}
		if (uldListFilterVO.getUldRangeFrom() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "ULD Range from : "
					+ uldListFilterVO.getUldRangeFrom();

		}
		if (uldListFilterVO.getUldRangeTo() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "ULD Range To : " + uldListFilterVO.getUldRangeTo();

		}
		if (uldListFilterVO.getFromDate() != null) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "From Date :"
					+ uldListFilterVO.getFromDate().toDisplayDateOnlyFormat();

		}
		if (uldListFilterVO.getToDate() != null) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "To Date :"
					+ uldListFilterVO.getToDate().toDisplayDateOnlyFormat();

		}
		if (uldListFilterVO.getLevelType() != null
				&& uldListFilterVO.getLevelType().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Level Type :" + uldListFilterVO.getLevelType();

		}
		if (uldListFilterVO.getLevelValue() != null
				&& uldListFilterVO.getLevelValue().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Level Value :" + uldListFilterVO.getLevelValue();

		}
		if (uldListFilterVO.getContent() != null
				&& uldListFilterVO.getContent().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Content : "
					+ getDescofOneTime(uldListFilterVO.getContent(),
							CONTENT_TYPE);

		}
		if (uldListFilterVO.getInTransitFlag() != null
				&& uldListFilterVO.getInTransitFlag().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "In-Transit Status : "
					+ getDescofOneTime(uldListFilterVO.getInTransitFlag(),
							INTRANSIT_ONETIME);

		}
		if (uldListFilterVO.getOffairportFlag() != null
				&& uldListFilterVO.getOffairportFlag().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			if ("Y".equals(uldListFilterVO.getOffairportFlag())) {
				finalFilter += "Off Airport Status : Off Airport";
			} else {
				finalFilter += "Off Airport Status: On Airport";
			}

		}
		if (uldListFilterVO.getOccupiedULDFlag() != null
				&& uldListFilterVO.getOccupiedULDFlag().trim().length() > 0) {
			if (!"".equals(finalFilter)) {
				finalFilter += (",");
			}
			finalFilter += "Occupied Status : "
					+ getDescofOneTime(uldListFilterVO.getOccupiedULDFlag(),
							OCCUPANCYSTATUS_ONETIME);

		}
		log.log(Log.INFO, "finalFilter ---> ", finalFilter);
		log.exiting("ListULDCommand", "populateFilterDetails");
		return finalFilter;
	}


	/**
	 * 	Method		:	ListULDCommand.getDescofOneTime
	 *	Added by 	:	A-7359 on 23-Jan-2018
	 * 	Used for 	:	ICRD-228547
	 *	Parameters	:	@param occupiedULDFlag
	 *	Parameters	:	@param occupancystatusOnetime
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	private String getDescofOneTime(String status, String keyForMap) {
		log.entering("ListULDCommand", "getDescofOneTime");
		String description = "";
		HashMap<String, Collection<OneTimeVO>> oneTimeValuesMap = getOneTimeValues();
		Collection<OneTimeVO> oneTimeList = oneTimeValuesMap.get(keyForMap);
		for (OneTimeVO oneTimeVO : oneTimeList) {
			if (oneTimeVO.getFieldValue().equals(status)) {
				description = oneTimeVO.getFieldDescription();
				break;
			}
		}
		log.log(Log.INFO, "description ---> ", description);
		log.exiting("ListULDCommand", "getDescofOneTime");
		return description;
	}
}
