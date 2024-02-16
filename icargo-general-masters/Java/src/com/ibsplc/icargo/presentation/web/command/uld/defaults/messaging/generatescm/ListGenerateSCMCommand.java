/*
 * ListGenerateSCMCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.generatescm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * This command class is used to list the Generate SCM
 * 
 * @author A-1862
 */

public class ListGenerateSCMCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Generate SCM");

	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of Generate SCM
	 */
	private static final String SCREENID = "uld.defaults.generatescm";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";
	
	private static final String FACILITY_TYPE = "uld.defaults.facilitytypes";
	private static final String SCM_ULD_STATUS = "uld.defaults.scmuldstatus";
	private static final String SYS_PARAM_DISPLAYDISONSTOCKCHECK = "uld.defaults.displaydiscrepancyonstockcheck";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();

		GenerateSCMForm generateSCMForm = (GenerateSCMForm) invocationContext.screenModel;
		GenerateSCMSession generateSCMsession = (GenerateSCMSession) getScreenSession(
				MODULE, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errorsairline = new ArrayList<ErrorVO>();
		//ICRD-270001 add as part by A-4393
		 boolean includeDiscrepancyCheck=false;
			Collection<String> parameterTypes = new ArrayList<String>();
			parameterTypes.add(SYS_PARAM_DISPLAYDISONSTOCKCHECK);
			Map<String,String> sysParameterMap = null;
			 SharedDefaultsDelegate sharedDelegate = new SharedDefaultsDelegate();
			 try {
				 sysParameterMap = sharedDelegate.findSystemParameterByCodes(parameterTypes);
		        } catch (BusinessDelegateException ex) {
		          handleDelegateException(ex);
		        }
	 		 if(sysParameterMap!=null&&sysParameterMap.get(SYS_PARAM_DISPLAYDISONSTOCKCHECK)!=null){
				 includeDiscrepancyCheck=OneTimeVO.FLAG_YES.equals(sysParameterMap.get(SYS_PARAM_DISPLAYDISONSTOCKCHECK))?true:false;
			 }
			 //ICRD-270001 add as part by A-4393 end 	 
		//Added By T-1927 for ICRD-36528 start
		if(generateSCMForm.getScmStockCheckDate() != null
				&& generateSCMForm.getScmStockCheckDate().trim().length() > 0){
			LocalDate locDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(), Location.ARP, true);
	        LocalDate currDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(), Location.ARP, true);
	        LocalDate localDate = locDate.setDate(generateSCMForm.getScmStockCheckDate());
	        String currentDat = DateUtilities.getCurrentDate("dd-MMM-yyyy");
	        LocalDate currentDate = currDate.setDate(currentDat);
	        if(localDate.isGreaterThan(currentDate))
	        {
	            ErrorVO errorVO = new ErrorVO("uld.defaults.generatescm.msg.err.dateinvalid");
	            errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	            errors.add(errorVO);
	            invocationContext.addAllError(errors);
	            invocationContext.target = LIST_FAILURE;
	            return;
	        }
	}
		//added by a-6344 for ICRD-55460 starts	
		if(generateSCMForm.getListFromStock()==null || "N".equals(generateSCMForm.getListFromStock())){
			generateSCMForm.setUldStatus("S,M,F");
			generateSCMForm.setUldNumberStock("");
		}
		boolean isEmptyList=false;
		
		String uld=generateSCMForm.getUldNumberStock();
		String uldStatus=generateSCMForm.getUldStatus();
		String uldDynamicSearch=null;
		if(generateSCMForm.getDynamicQuery() != null && generateSCMForm.getDynamicQuery().trim().length() > 0){
			if (generateSCMForm.getDynamicQuery().contains("'='")) {
				generateSCMForm.setDynamicQuery(generateSCMForm
						.getDynamicQuery().replace("'=", "="));
			}
		    uldDynamicSearch=generateSCMForm.getDynamicQuery().toUpperCase();
		}
		
		if(generateSCMForm.getListFromStock()!=null && "Y".equals(generateSCMForm.getListFromStock()) && 
				 "".equals(uld) && "".equals(uldStatus)){
			isEmptyList=true;
		}
		//added by a-6344 for ICRD-55460  ends
		
		//Added By T-1927 for ICRD-36528 end
		log.log(Log.INFO, "Page URL........>>>>>>>>", generateSCMForm.getPageURL());
		if("LISTULD".equals(generateSCMForm.getPageURL())|| "LISTULDS".equals(generateSCMForm.getPageURL()) ){
			LocalDate stockCheck = null;
			if (generateSCMForm.getScmAirport() != null
					&& generateSCMForm.getScmAirport().trim().length() > 0) {
				if (validateAirportCodes(generateSCMForm.getScmAirport()
						.toUpperCase(), compCode) != null) {
					errors.add(new ErrorVO(
							"uld.defaults.generatescm.msg.err.airportcodeinvalid",
							null));
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}
			
			if(generateSCMForm.getScmAirport()!=null && generateSCMForm.getScmAirport().trim().length()>0){
				generateSCMForm.setScmAirport(generateSCMForm.getScmAirport().toUpperCase());
				stockCheck=new LocalDate(generateSCMForm.getScmAirport().toUpperCase(),Location.ARP,true);
			}else{
				stockCheck=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			}
			generateSCMForm.setScmStockCheckDate(stockCheck.toDisplayDateOnlyFormat());
		}else if(generateSCMForm.getPageURL()==null || generateSCMForm.getPageURL().trim().length() ==0 ){
			generateSCMsession.setChangedSystemStock(null);
			generateSCMsession.setSystemStock(null);		
		}
		if("LISTULDS".equals(generateSCMForm.getPageURL())){
			if (generateSCMForm.getScmAirport() != null
					&& generateSCMForm.getScmAirport().trim().length() > 0) {
				if (validateAirportCodes(generateSCMForm.getScmAirport()
						.toUpperCase(), compCode) != null) {
					errors.add(new ErrorVO(
							"uld.defaults.generatescm.msg.err.airportcodeinvalid",
							null));
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}
			SCMMessageFilterVO scmMessageFilterVO = new SCMMessageFilterVO();
			
			scmMessageFilterVO.setCompanyCode(compCode);
			log
					.log(
							Log.FINE,
							"<<<<<<<<<<------------Page number-------->>>>>>>>>>>>>>>>>",
							generateSCMForm.getDisplayPage());
			scmMessageFilterVO.setPageNumber(Integer.parseInt(generateSCMForm.getDisplayPage()));
			scmMessageFilterVO.setAirportCode(generateSCMForm.getScmAirport()
					.toUpperCase());
			LocalDate fltDate = new LocalDate(scmMessageFilterVO.getAirportCode(),Location.ARP, true);
			String stockCheckDate= generateSCMForm.getScmStockCheckDate();
			StringBuilder dateAndTime=new StringBuilder(stockCheckDate);
			if(generateSCMForm.getScmStockCheckTime()!=null && generateSCMForm.getScmStockCheckTime().trim().length()>0){
			String stockCheckTime= generateSCMForm.getScmStockCheckTime();
			if(!stockCheckTime.contains(":")){
				stockCheckTime=stockCheckTime.concat(":00");
			} 
			log.log(Log.FINE, "\n\n\n\n time from form", stockCheckTime);
			dateAndTime.append(" ").append(stockCheckTime).append(":00");
			}else
			{
				LocalDate stockCheck = new LocalDate(generateSCMForm.getScmAirport(),Location.ARP,true);
				String stockCheckTim=stockCheck.toDisplayFormat("HH:mm");
				log.log(Log.FINE, "\n\n\n\n current time", stockCheckTim);
				generateSCMForm.setScmStockCheckTime(stockCheckTim);				
				dateAndTime.append(" ").append(stockCheckTim).append(":00");
				
			}
			
			log.log(Log.FINE, "\n\n\n\nDATE AND TIME", dateAndTime);
			scmMessageFilterVO.setStockControlDate(fltDate.setDateAndTime(dateAndTime.toString()));
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			AirlineValidationVO airlineValidationVO = null;
			if (generateSCMForm.getScmAirline() != null
					&& generateSCMForm.getScmAirline().trim().length() > 0) {
				try {
					airlineValidationVO = airlineDelegate.validateAlphaCode(
							logonAttributes.getCompanyCode(), generateSCMForm
									.getScmAirline().toUpperCase());

				} catch (BusinessDelegateException businessDelegateException) {
					errorsairline = handleDelegateException(businessDelegateException);
				}
				if (errorsairline != null && errorsairline.size() > 0) {
					Object[] obj = { generateSCMForm.getScmAirline().toUpperCase() };
					ErrorVO errorVO = new ErrorVO(
							"uld.defaults.generatescm.msg.err.invalidcarrier", obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);

				}
				
				if(airlineValidationVO!=null){
				scmMessageFilterVO.setFlightCarrierIdentifier(airlineValidationVO
						.getAirlineIdentifier());
				}
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
			generateSCMsession.setMessageFilterVO(scmMessageFilterVO);
		}
		//ends
		
    	//String[] opFlags = generateSCMForm.getOperationFlag();
		//String[] rowIds = generateSCMForm.getRowId();
		
		/**
		 * Validate for client errors
		 */
		
		
		Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes.getCompanyCode());
			
		ArrayList<OneTimeVO> facilityTypes = (ArrayList<OneTimeVO>) oneTimeCollection.get(FACILITY_TYPE);
		generateSCMsession.setFacilityType(facilityTypes);
		ArrayList<OneTimeVO> uldStatusList = (ArrayList<OneTimeVO>) oneTimeCollection.get(SCM_ULD_STATUS);
		generateSCMsession.setUldStatusList(uldStatusList);

		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		if (generateSCMsession.getIndexMap() != null) {
			indexMap = generateSCMsession.getIndexMap();
		}

		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String displayPage = generateSCMForm.getDisplayPage();
		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		
		if(!"LISTULDS".equals(generateSCMForm.getPageURL())){
		//generateSCMsession.removeAllAttributes();
		errors = validateForm(generateSCMForm, logonAttributes.getCompanyCode());
		if (errors == null) {
			errors = new ArrayList<ErrorVO>();

		}
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVO = null;
		if (generateSCMForm.getScmAirline() != null
				&& generateSCMForm.getScmAirline().trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), generateSCMForm
								.getScmAirline().toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errorsairline = handleDelegateException(businessDelegateException);
			}
			if (errorsairline != null && errorsairline.size() > 0) {
				//commented by nisha for bugfix on 29Apr08
				//generateSCMForm.setScreenStatusFlag("SCREENLOAD");
				Object[] obj = { generateSCMForm.getScmAirline().toUpperCase() };
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.generatescm.msg.err.invalidcarrier", obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);

			}
		}

		if (generateSCMForm.getScmAirport() != null
				&& generateSCMForm.getScmAirport().trim().length() > 0) {
			if (validateAirportCodes(generateSCMForm.getScmAirport()
					.toUpperCase(), compCode) != null) {
				errors.add(new ErrorVO(
						"uld.defaults.generatescm.msg.err.airportcodeinvalid",
						null));
			}
		}

		if (errors != null && errors.size() > 0) {
//			commented by nisha for bugfix on 29Apr08
			//generateSCMForm.setScreenStatusFlag("SCREENLOAD");
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		SCMMessageFilterVO scmMessageFilterVO = new SCMMessageFilterVO();
		scmMessageFilterVO.setAbsoluteIndex(nAbsoluteIndex);
		scmMessageFilterVO.setCompanyCode(compCode);
		//added as part of ICRD-270001 by A-4393
		scmMessageFilterVO.setMissingDiscrepancyCaptured(includeDiscrepancyCheck);
		//added as part of ICRD-270001 by A-4393
		log.log(Log.FINE,
				"<<<<<<<<<<------------Page number-------->>>>>>>>>>>>>>>>>",
				generateSCMForm.getDisplayPage());
		scmMessageFilterVO.setPageNumber(Integer.parseInt(generateSCMForm.getDisplayPage()));
		scmMessageFilterVO.setAirportCode(generateSCMForm.getScmAirport()
				.toUpperCase());
//		Added by nisha for bugFix starts
		if(generateSCMForm.getFacilityFilter()!=null && generateSCMForm.getFacilityFilter().trim().length()>0){
			if(generateSCMsession.getFacilityType()!=null && generateSCMsession.getFacilityType().size()>0){
				for(OneTimeVO vo:generateSCMsession.getFacilityType()){
					if(vo.getFieldDescription().equalsIgnoreCase(generateSCMForm.getFacilityFilter())){
						scmMessageFilterVO.setFacility(vo.getFieldValue());
						break;
					}
				}
			}
			
		}
		if(generateSCMForm.getLocationFilter()!=null && generateSCMForm.getLocationFilter().trim().length()>0){
			scmMessageFilterVO.setLocation(generateSCMForm.getLocationFilter().trim());
		}
		//ends
		
		LocalDate fltDate = new LocalDate(scmMessageFilterVO.getAirportCode(),Location.ARP, true);
		String stockCheckDate= generateSCMForm.getScmStockCheckDate();
		StringBuilder dateAndTime=new StringBuilder(stockCheckDate);
		if(generateSCMForm.getScmStockCheckTime()!=null && generateSCMForm.getScmStockCheckTime().trim().length()>0){
		String stockCheckTime= generateSCMForm.getScmStockCheckTime();
		if(!stockCheckTime.contains(":")){
			stockCheckTime=stockCheckTime.concat(":00");
		} 
		log.log(Log.FINE, "\n\n\n\n time from form", stockCheckTime);
		dateAndTime.append(" ").append(stockCheckTime).append(":59");
		}else
		{
			LocalDate stockCheck = new LocalDate(generateSCMForm.getScmAirport(),Location.ARP,true);
			String stockCheckTim=stockCheck.toDisplayFormat("HH:mm");
			log.log(Log.FINE, "\n\n\n\n current time", stockCheckTim);
			generateSCMForm.setScmStockCheckTime(stockCheckTim);				
			dateAndTime.append(" ").append(stockCheckTim).append(":00");
			
		}
		
		log.log(Log.FINE, "\n\n\n\nDATE AND TIME", dateAndTime);
		scmMessageFilterVO.setStockControlDate(fltDate.setDateAndTime(dateAndTime.toString()));
		scmMessageFilterVO.setFlightCarrierIdentifier(airlineValidationVO
				.getAirlineIdentifier());

			
		//Added by Sreekumar S (Adding the current page modifications into session)
		ArrayList<ULDVO> extraULDs = new ArrayList<ULDVO>();
		String[] uldNumbers = generateSCMForm.getExtrauld();		
		String[] scmStatusFlags = generateSCMForm.getScmStatusFlags();
		String[] operationFlags = generateSCMForm.getOperationFlag();
		int index = 0;
		log.log(Log.FINE,
				"Size of uld before setting into session --------->>>",
				extraULDs.size());
		if(uldNumbers != null ){
			//int length = uldNumbers.length;
			
		for (String uldNumber : uldNumbers)
		{						
			if(!("").equals(uldNumber))
			{
					ULDVO uldVO = new ULDVO();
					uldVO.setCompanyCode(logonAttributes.getCompanyCode());
					uldVO.setScmStatusFlag(scmStatusFlags[index]);
					uldVO.setUldNumber(uldNumbers[index]);
					if(!"NOOP".equals(operationFlags[index]) || !"U".equals(operationFlags[index])){
							extraULDs.add(uldVO);							
					}	
				}
				index++;
			}
		}
		
		//Added by A-1853 to update the newly added uld to session, done as part of cr 9728 
		//cr developments starts
		ArrayList<ULDVO> newULDs = new ArrayList<ULDVO>();
		String[] newuldNumbers = generateSCMForm.getNewuld();	
		String[] newFacilityTypes = generateSCMForm.getNewFacilityType();
		String[] newLocation = generateSCMForm.getNewLocations();		
		String[] oprFlag = generateSCMForm.getNewOperationFlag();
		int newindex = 0;		
		if(newuldNumbers != null ){			
		for (String uldNumber : newuldNumbers)		{		
			
			if(!("").equals(uldNumber))
			{
					ULDVO uldVO = new ULDVO();
					uldVO.setCompanyCode(logonAttributes.getCompanyCode());
					uldVO.setScmStatusFlag(ULDVO.SCM_FOUND_STOCK);
					uldVO.setUldNumber(newuldNumbers[newindex]);
					uldVO.setFacilityType(newFacilityTypes[newindex]);
					uldVO.setLocation(newLocation[newindex]);
					uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);
					if(!"NOOP".equals(oprFlag[newindex])){
						newULDs.add(uldVO);							
					}	
				}
			newindex++;
			}
		}
		
		if(newULDs != null && newULDs.size() > 0){
			ArrayList<OneTimeVO> newFacTypes = (ArrayList<OneTimeVO>) oneTimeCollection.get(FACILITY_TYPE);			 
			for(ULDVO uldVO:newULDs){
				if (newFacTypes != null && newFacTypes.size() > 0) {					
					for (OneTimeVO oneTimeVO : newFacTypes) {
						if(uldVO.getFacilityType() != null && uldVO.getFacilityType().equals(oneTimeVO.getFieldValue())){
							uldVO.setFacilityType(oneTimeVO.getFieldValue());							
						}
					}
				}		
			}
		}
		log.log(Log.FINE,
				"Size of uld before setting into session --------->>>", newULDs.size());
		generateSCMsession.setNewSystemStock(newULDs);
		
		// cr developments ends
		if(generateSCMForm.getPageURL()!=null && generateSCMForm.getPageURL().trim().length()>0) {
		generateSCMsession.setChangedSystemStock(extraULDs);
		}
		log
				.log(
						Log.FINE,
						"---------generateSCMsession.setChangedSystemStock(extraULDs) --------->>>",
						extraULDs.size());
		log.log(Log.FINE,
				"Size of uld after setting into session --------->>>",
				extraULDs.size());
		Page<ULDVO> ULDpage = new Page<ULDVO>(extraULDs,0,0,0,0,0,false);
		
		generateSCMsession.setSystemStock(ULDpage);
		//ending---
		
		Page<ULDVO> uldVOs = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		log.log(Log.INFO, "SCM Filter VO", scmMessageFilterVO);
		//added by a-3045 for bug 24468 starts
		if("LISTULD".equals(generateSCMForm.getPageURL())){			
			ULDListFilterVO uldListFilterVO = generateSCMsession.getULDListFilterVO();
			uldListFilterVO.setFromListULD(false);
			uldListFilterVO.setPageNumber(Integer.parseInt(generateSCMForm.getDisplayPage()));			
			// added by A-5280 for bug ICRD-25890 starts
			uldListFilterVO
					.setCompanyCode(logonAttributes.getCompanyCode());
			uldListFilterVO.setAirlineidentifier(airlineValidationVO
					.getAirlineIdentifier());
			// added by A-5280 for bug ICRD-25890 ends
			try {
				uldVOs = new ULDDefaultsDelegate()
						.findULDListForSCM(uldListFilterVO,Integer.parseInt(generateSCMForm.getDisplayPage()));
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
			}
			log.log(Log.FINE, "ULDVOs ---> ", uldVOs);
			log.log(Log.FINE, "ULDVOs from LISTULD+++++++++++---> ", uldVOs);
		}else{
			
			scmMessageFilterVO.setUldNumber(uld);
			scmMessageFilterVO.setUldStatus(uldStatus);
			scmMessageFilterVO.setDynamicQueryString(uldDynamicSearch);
			if((generateSCMsession.getSystemStock()!= null &&
					generateSCMsession.getSystemStock().size()==0)
					||( generateSCMForm.getTotalRecords() ==-1) ){
				scmMessageFilterVO.setTotalRecords(-1);
				
			}else {
				scmMessageFilterVO.setTotalRecords(generateSCMsession.getTotalRecords().intValue());
			}
				try {
					if(!isEmptyList ){
						uldVOs = new ULDDefaultsDelegate()
						.findUldDetailsForSCM(scmMessageFilterVO);
					}
				} catch (BusinessDelegateException businessDelegateException) {
					businessDelegateException.getMessage();
					error = handleDelegateException(businessDelegateException);
				}
				log.log(Log.FINE, "ULDVOs ---> ", uldVOs);
		}
		if(generateSCMForm.getScmMessageSendingFlag()==null || "D".equals(generateSCMForm.getScmMessageSendingFlag()))
		{
			
			scmMessageFilterVO.setUldStatus("N");
			newULDs=new ArrayList<ULDVO>(); 
			try {
					Page<ULDVO> uldMissingVOs = new ULDDefaultsDelegate()
							.findUldDetailsForSCM(scmMessageFilterVO);
					if (uldMissingVOs != null) {
						for (ULDVO uldvo : uldMissingVOs) {
							uldvo.setOperationalFlag("I");
							newULDs.add(uldvo);
						}
					}
			} catch (BusinessDelegateException e) {
				//empty catch
			}
			generateSCMsession.setNewSystemStock(newULDs);
			
		}
		//added by a-3045 for bug 24468 ends		
		if ((uldVOs == null || uldVOs.size() == 0) &&  newULDs.size()==0) {
//			commented by nisha for bugfix on 29Apr08
			//generateSCMForm.setScreenStatusFlag("SCREENLOAD");
			invocationContext.addError(new ErrorVO(
					"uld.defaults.messaging.generatescm.noresults", null));
			invocationContext.addAllError(errors);
			generateSCMForm.setListStatus("Y");
			generateSCMsession.setMessageFilterVO(scmMessageFilterVO);
			generateSCMsession.setSystemStock(uldVOs);
			invocationContext.target = LIST_FAILURE;
			return;
		}else if(uldVOs!=null&&uldVOs.size()>0){
			
			ArrayList<OneTimeVO> facTypes = (ArrayList<OneTimeVO>) oneTimeCollection.get(FACILITY_TYPE);
			HashMap<String, ULDVO> updatedULDs=generateSCMsession.getUpdatedULDStocks(); 
			for(ULDVO uldVO:uldVOs){
				if (facTypes != null && facTypes.size() > 0) {
					
					for (OneTimeVO oneTimeVO : facTypes) {
						if(uldVO.getFacilityType() != null && uldVO.getFacilityType().equals(oneTimeVO.getFieldValue())){
							uldVO.setFacilityType(oneTimeVO.getFieldValue());
						}
					}
				}
				//added as part of ICRD-258261 by A-4393 for asiana 
				if(includeDiscrepancyCheck){
					if(uldVO.isMissingDiscrepancyCaptured()){
					uldVO.setScmStatusFlag(ULDVO.SCM_MISSING_STOCK);
					}
				}
				//added as part of ICRD-258261 by A-4393 for asiana ends  
				if(updatedULDs!=null && updatedULDs.containsKey(uldVO.getUldNumber())){
					uldVO.setScmStatusFlag(updatedULDs.get(uldVO.getUldNumber()).getScmStatusFlag());
				} //else {
					//uldVO.setScmStatusFlag(ULDVO.SCM_SYSTEM_STOCK);
			//}
			}
			
		}
		
		if(uldVOs != null && uldVOs.size()>0){
		generateSCMsession.setTotalRecords(new Integer(uldVOs.getTotalRecordCount()));
		log.log(Log.FINE, "uldVOs.getTotalRecordCount() ---> ", uldVOs.getTotalRecordCount());
		generateSCMForm.setScmMessageSendingFlag(uldVOs.get(0).getScmMessageSendingFlag());
		if(generateSCMForm.getDynamicQuery()!=null && generateSCMForm.getDynamicQuery().trim().length()>0){
			generateSCMForm.setListedDynamicQuery(generateSCMForm.getDynamicQuery());
			generateSCMsession.setKeyListedDynamicQuery(generateSCMForm.getDynamicQuery());
		}else{
			generateSCMForm.setListedDynamicQuery("");
			generateSCMsession.setKeyListedDynamicQuery("");
		}
		//added by A-5867 for bug ICRD-107865 
		generateSCMsession.setSystemStock(uldVOs);	
		}else{
			generateSCMForm.setScmMessageSendingFlag("");
		}
		
		
		
		
		generateSCMsession.setMessageFilterVO(scmMessageFilterVO);
		}
		finalMap = indexMap;
		if (generateSCMsession.getSystemStock() != null) {
			finalMap = buildIndexMap(indexMap, generateSCMsession.getSystemStock());
		}
		generateSCMsession.setIndexMap(finalMap);
		log.log(Log.INFO, "GOING TO PUT FINAL MAP IN SESSION---------*****",
				finalMap);
		// Server ends
		generateSCMsession.setIndexMap(finalMap);
		
		generateSCMForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		generateSCMForm.setListStatus("Y");
		
		invocationContext.target = LIST_SUCCESS;

	}

	/**
	 * @param generateSCMForm
	 * @param companyCode
	 * @return errors
	 */

	private Collection<ErrorVO> validateForm(GenerateSCMForm generateSCMForm,
			String companyCode) {
		log.entering("GenerateSCMCommand", "validateForm");

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (generateSCMForm.getScmAirline() == null
				|| generateSCMForm.getScmAirline().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.generatescm.airlinemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if (generateSCMForm.getScmAirport() == null
				|| generateSCMForm.getScmAirport().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.generatescm.airportmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if (generateSCMForm.getScmStockCheckDate() == null
				|| generateSCMForm.getScmStockCheckDate().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.generatescm.stockdatemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		/*if (generateSCMForm.getScmStockCheckTime() == null
				|| generateSCMForm.getScmStockCheckTime().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.generatescm.stocktimemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}*/
		log.exiting("GenerateSCMCommand", "validateForm");
		return errors;
	}
	
	/**
	 * @param station
	 * @param companyCode
	 * @return errors
	 */
	public Collection<ErrorVO> validateAirportCodes(String station,
			String companyCode) {
		log.entering("ListCommand", "validateAirportCodes");
		Collection<ErrorVO> errors = null;
		try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateAirportCode(companyCode, station);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		log.exiting("ListCommand", "validateAirportCodes");
		return errors;
	}
/**
 * 
 * @param existingMap
 * @param page
 * @return
 */
	private HashMap<String, String> buildIndexMap(HashMap<String,
			String> existingMap, Page page) {

		HashMap<String, String> finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
	}
	
	 /**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(FACILITY_TYPE);
		oneTimeList.add(SCM_ULD_STATUS);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		return hashMap;
	}
	
	

    

}
