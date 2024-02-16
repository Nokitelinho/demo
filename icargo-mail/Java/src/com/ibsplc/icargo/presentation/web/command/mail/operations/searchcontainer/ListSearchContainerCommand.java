/*
 * ListSearchContainerCommand.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchcontainer;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.SEARCH_MODE_ALL;
import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.SEARCH_MODE_DESTN;
import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.SEARCH_MODE_FLIGHT;
import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;


/**
 * @author A-1876
 *
 */
public class ListSearchContainerCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String LIST_SUCCESS = "list_success";
   private static final String LIST_FAILURE = "list_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String PARENT_SCREEN = "mailonhandlist";
   private static final String CONST_SEARCH_ALL = "ALL";
   private static final String CONST_SEARCH_DEST = "DESTN";
   private static final String CONST_SEARCH_FLIGHT = "FLT";
   private static final String FLTTYPE_ONETIME = "mailtracking.defaults.operationtype";
   private static final String FLTTYPE_ONETIME_SEARCHMODE = "mailtracking.defaults.containersearchmode";
   private static final String FLTTYPE_ONETIME_SUBCLASSGROUP = "mailtracking.defaults.subclassgroup";
   private static final String FROM_SCREEN_LISTMAIL = "LSTMALBAG";
   /**
    * CONST_ON
    */
   private static final String CONST_ON = "on";
  
    /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListContainersCommand","execute");
    	  
    	SearchContainerForm searchContainerForm = 
    		(SearchContainerForm)invocationContext.screenModel;
    	SearchContainerSession searchContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	searchContainerForm.setSelectContainer(null);
    	String companyCode = logonAttributes.getCompanyCode();

		if (searchContainerSession.getListContainerVOs() != null) {
			searchContainerSession.setListContainerVOs(null);
		}
		SearchContainerFilterVO searchContainerFilterVO =
			new SearchContainerFilterVO();

		searchContainerFilterVO.setCompanyCode(companyCode);
		if(searchContainerSession.getSearchContainerFilterVO()!=null&&searchContainerForm.getFromScreen()!=null)
		{
			if(PARENT_SCREEN.equalsIgnoreCase(searchContainerForm.getFromScreen().trim())
					|| FROM_SCREEN_LISTMAIL.equals(searchContainerForm.getFromScreen()))
			{
				searchContainerFilterVO=searchContainerSession.getSearchContainerFilterVO();
				//Added as part of bug ICRD-136025 by A-5526 starts
				if(searchContainerFilterVO.getContainerNumber()==null){
					searchContainerForm.setContainerNo("");      
				}
				//Added as part of bug ICRD-136025 by A-5526 ends
				HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
				searchContainerSession.setOneTimeValues(oneTimeValues);
				searchContainerForm.setDisplayPage("1");
				searchContainerForm.setLastPageNum("0");
				if(PARENT_SCREEN.equalsIgnoreCase(searchContainerForm.getFromScreen().trim())){
					
					searchContainerSession.setParentScreen(PARENT_SCREEN);
					
				}  
				
				
			}
		}

		// Added by Sever for handling PageAwareMultiMapper implementation
		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		if (searchContainerSession.getIndexMap() != null) {
			indexMap = searchContainerSession.getIndexMap();
		}

		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}

		int nAbsoluteIndex = 0;
		String displayPage = searchContainerForm.getDisplayPage();
		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		searchContainerFilterVO.setAbsoluteIndex(nAbsoluteIndex);
		// String displayPage = listFltSchForm.getDisplayPage();
		// Server ends
		
		//Added by A-5201 as part from the ICRD-20507 starts
		
					
	if(!"YES".equals(searchContainerForm.getCountTotalFlag()) 
			&&searchContainerSession.getTotalRecords()!= null
			&& searchContainerSession.getTotalRecords().intValue() != 0){
			searchContainerFilterVO.setTotalRecords(searchContainerSession.getTotalRecords().intValue());
	    }else{
	    	searchContainerFilterVO.setTotalRecords(-1);
	    }
	    	
		//Added by A-5201 as part from the ICRD-20507 end
	if(!FROM_SCREEN_LISTMAIL.equals(searchContainerForm.getFromScreen())
			||PARENT_SCREEN.equalsIgnoreCase(searchContainerForm.getFromScreen())){
		errors = updateFilterVO(searchContainerForm,searchContainerFilterVO,logonAttributes);
	}
		//Added by A-5945 for ICRD-117048 starts
	 if(PARENT_SCREEN.equalsIgnoreCase(searchContainerForm.getFromScreen()))   	{
		 
		 searchContainerFilterVO.setShowEmptyContainer(MailConstantsVO.FLAG_NO);
		 searchContainerFilterVO.setNotClosedFlag(MailConstantsVO.FLAG_YES);
		 searchContainerFilterVO.setMailAcceptedFlag(MailConstantsVO.FLAG_NO);
	 }
	//Added by A-5945 for ICRD-117048 ends
		searchContainerSession.setSearchContainerFilterVO(searchContainerFilterVO);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			searchContainerForm.setAssignedto(CONST_FLIGHT);			
			invocationContext.target = LIST_FAILURE;
		}else{ 
			log.log(Log.FINE, "searchContainerFilterVO built...",
					searchContainerFilterVO);
			log.log(Log.FINE, "displayPage...", displayPage);
		Page<ContainerVO> containerVOs =
			findContainers(searchContainerFilterVO, displayPage);
		
		searchContainerForm.setFromScreen("");
		if (containerVOs == null
				|| (containerVOs != null &&
						containerVOs.size() == 0)) {
			searchContainerSession
			.setListContainerVOs(null);
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.noresultsfound");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			searchContainerForm.setAssignedto(CONST_FLIGHT);
			log.log(Log.FINE, "containerVOs IS NULL");
		} else {
			log.log(Log.FINE, "containerVOs IS not NULL", containerVOs);
			searchContainerSession.setListContainerVOs(containerVOs);
			searchContainerSession.setTotalRecords(containerVOs.getTotalRecordCount()); //Added by A-5201 as part for the ICRD-21098
			searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		}
		
		// Added by Server on handling PageAwareMultiMapper
		finalMap = indexMap;
		if (searchContainerSession.getListContainerVOs() != null) {
			finalMap = buildIndexMap(indexMap, searchContainerSession.getListContainerVOs());
		}
		searchContainerSession.setIndexMap(finalMap);
		log.log(Log.INFO, "GOING TO PUT FINAL MAP IN SESSION---------*****",
				finalMap);
		invocationContext.target = LIST_SUCCESS;
			
		}
		log.exiting("ListContainersCommand","execute");
	}

    
    
    
    
    private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ScreenLoadCommand","getOneTimeValues");
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
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
    
    
    
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(FLTTYPE_ONETIME);
    	parameterTypes.add(FLTTYPE_ONETIME_SEARCHMODE); 
    	parameterTypes.add(FLTTYPE_ONETIME_SUBCLASSGROUP);
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
	/**
	 * Method to find schedules matching the specified criteria.
	 *
	 * @param searchContainerFilterVO
	 * @param displayPage
	 * @return Page<ContainerVO>
	 */
	private Page<ContainerVO> findContainers(
			SearchContainerFilterVO searchContainerFilterVO, String displayPage) {
		Page<ContainerVO> containerVOs = null;
		int pageNumber = Integer.parseInt(displayPage);
		
		try {
			log.log(Log.FINE, "searchContainerFilterVO.getAbsoluteIndex()=",
					searchContainerFilterVO.getAbsoluteIndex());
			containerVOs = new MailTrackingDefaultsDelegate().findContainers(searchContainerFilterVO, 
					pageNumber);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return containerVOs;
	}

	
	/**
	 * Method to update FilterVO
	 * @param searchContainerForm
	 * @param searchContainerFilterVO
	 * @param logonAttributes
	 * @return Collection<ErrorVO>
	 */
	
	private Collection<ErrorVO> updateFilterVO(
			SearchContainerForm searchContainerForm,
			SearchContainerFilterVO searchContainerFilterVO,
			LogonAttributes logonAttributes){
		
	String assignedTo = searchContainerForm.getAssignedTo();
	log.log(Log.FINE, "assigned to got here is ====>>", assignedTo);
	if (CONST_SEARCH_ALL.equals(assignedTo)) {
		  searchContainerFilterVO.setSearchMode(SEARCH_MODE_ALL);		  
		  String oprType = searchContainerForm.getOperationTypeAll();
		  if (oprType != null 
					&& oprType.trim().length() > 0) {
				searchContainerFilterVO.setOperationType(oprType);
			}
	  }else if (CONST_SEARCH_DEST.equals(assignedTo)) {
		  searchContainerFilterVO.setSearchMode(SEARCH_MODE_DESTN);
	  }else if (CONST_SEARCH_FLIGHT.equals(assignedTo)) {
		  searchContainerFilterVO.setSearchMode(SEARCH_MODE_FLIGHT);		  
		  String oprType = searchContainerForm.getOperationType();
		  if (oprType != null 
					&& oprType.trim().length() > 0) {
				searchContainerFilterVO.setOperationType(oprType);
			}
	  }  
	  
	  LocalDate afd = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
	  LocalDate atd = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
	  LocalDate fd = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
		
		Collection<ErrorVO> errorsMail = new ArrayList<ErrorVO>();
		
		String containerNo = searchContainerForm.getContainerNo();
		if (containerNo != null && containerNo.trim().length() > 0) {
		  searchContainerFilterVO.setContainerNumber(containerNo.toUpperCase());
		}
		
		String fromDate = searchContainerForm.getFromDate();
		if (fromDate != null && fromDate.trim().length() > 0) {
			searchContainerFilterVO.setAssignedFromDate(afd.setDate(fromDate));
			searchContainerFilterVO.setStrFromDate(fromDate);
		}
		
		String toDate = searchContainerForm.getToDate();
		if (toDate != null && toDate.trim().length() > 0) {
			searchContainerFilterVO.setAssignedToDate(atd.setDate(toDate));
			searchContainerFilterVO.setStrToDate(toDate);
		}
		
		if(searchContainerFilterVO.getAssignedToDate() != null 
				&&	searchContainerFilterVO.getAssignedFromDate() != null){
			if (!fromDate.equals(toDate)) {
				if (!DateUtilities.isLessThan(fromDate, toDate,"dd-MMM-yyyy")) {
 		    	    errorsMail.add(new ErrorVO("mailtracking.defaults.fromdategreatertodate"));
				}
		    }
		}
		
		
		
		
		String depPort = searchContainerForm.getDeparturePort();
		if (depPort != null && depPort.trim().length() > 0) {
			searchContainerFilterVO.setDeparturePort(depPort.toUpperCase());
			AirportValidationVO airportValidationVO = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    		try {
    			airportValidationVO = new AreaDelegate().validateAirportCode(
					logonAttributes.getCompanyCode(),depPort.toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
   			}
			if (errors != null && errors.size() > 0) {
			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidairport",
		   				new Object[]{depPort.toUpperCase()}));
			}
		}
		
		String assignedBy = searchContainerForm.getAssignedBy();
		if (assignedBy != null && assignedBy.trim().length() > 0) {
			searchContainerFilterVO.setAssignedUser(assignedBy.toUpperCase());
		}
		
		
		if (searchContainerForm.getTransferable() != null) {
			String transferable = searchContainerForm.getTransferable().trim();
			searchContainerFilterVO.setCurrentAirport(logonAttributes.getAirportCode());
			if (MailConstantsVO.FLAG_YES.equals(transferable)
					|| CONST_ON.equals(transferable)) {
				searchContainerFilterVO.setTransferStatus(MailConstantsVO.FLAG_YES);
			} else {
				searchContainerFilterVO.setTransferStatus(MailConstantsVO.FLAG_NO);
			}
		} else {
			searchContainerFilterVO.setTransferStatus(MailConstantsVO.FLAG_NO);
		}
		
		
		//Added by A-3429 for ICRD-83340
				
		if (searchContainerForm.getNotClosedFlag() != null) {
			String closeFlag = searchContainerForm.getNotClosedFlag().trim();
			if (MailConstantsVO.FLAG_YES.equals(closeFlag)
					|| CONST_ON.equals(closeFlag)) {
				searchContainerFilterVO.setNotClosedFlag(MailConstantsVO.FLAG_YES);
			} else {
				searchContainerFilterVO.setNotClosedFlag(MailConstantsVO.FLAG_NO);
			}
		} else {
			searchContainerFilterVO.setNotClosedFlag(MailConstantsVO.FLAG_NO);
		}
		
		
		if (searchContainerForm.getMailAcceptedFlag() != null) {
			String mailAcceptedFlag = searchContainerForm.getMailAcceptedFlag().trim();
			if (MailConstantsVO.FLAG_YES.equals(mailAcceptedFlag)
					|| CONST_ON.equals(mailAcceptedFlag)) {
				searchContainerFilterVO.setMailAcceptedFlag(MailConstantsVO.FLAG_YES);
			} else {
				searchContainerFilterVO.setMailAcceptedFlag(MailConstantsVO.FLAG_NO);
			}
		} else {
			searchContainerFilterVO.setMailAcceptedFlag(MailConstantsVO.FLAG_NO);
		}
		//Added by A-5945 for ICRD-96261
		
		if (searchContainerForm.getShowEmptyContainer() != null) {
			String showEmptyContainer = searchContainerForm.getShowEmptyContainer().trim();
			if (MailConstantsVO.FLAG_YES.equals(showEmptyContainer)
					|| CONST_ON.equals(showEmptyContainer)) {
				searchContainerFilterVO.setShowEmptyContainer(MailConstantsVO.FLAG_YES);
			} else {
				searchContainerFilterVO.setShowEmptyContainer(MailConstantsVO.FLAG_NO);
			}
		} else {
			searchContainerFilterVO.setShowEmptyContainer(MailConstantsVO.FLAG_NO);
		}
		
		
		String subclassGroup = searchContainerForm.getSubclassGroup();
		if (subclassGroup != null && subclassGroup.trim().length() > 0) {
			searchContainerFilterVO.setSubclassGroup(subclassGroup);
		}
		//Added by A-3429 for ICRD-83340 ENDS
		String carrier = "";
		String carrierDest = "";
		/*if(CONST_FLIGHT.equals(searchContainerForm.getAssignedto())){
		String oprType = searchContainerForm.getOperationType();
		if (oprType != null 
				&& oprType.trim().length() > 0) {
			searchContainerFilterVO.setOperationType(oprType);
			
		}*/
	if (CONST_SEARCH_FLIGHT.equals(assignedTo)) {
		String flightCarrierCode = searchContainerForm.getFlightCarrierCode();
		if (flightCarrierCode != null 
				&& flightCarrierCode.trim().length() > 0) {
			searchContainerFilterVO.setFlightCarrierCode(flightCarrierCode.trim().toUpperCase());
			carrier = flightCarrierCode.trim().toUpperCase();
		}
		String flightNumber = searchContainerForm.getFlightNumber();
		if (flightNumber != null && flightNumber.trim().length() > 0) {
			searchContainerFilterVO.setFlightNumber((flightNumber).toUpperCase());
		}
		String flightDate = searchContainerForm.getFlightDate();
		if (flightDate != null && flightDate.trim().length() > 0) {
			searchContainerFilterVO.setFlightDate(fd.setDate(flightDate));
			searchContainerFilterVO.setStrFlightDate(flightDate);
		}
		
		}else if(CONST_SEARCH_DEST.equals(assignedTo)){
			
			String carrierCode = searchContainerForm.getCarrier();
			if (carrierCode != null && carrierCode.trim().length() > 0) {
				searchContainerFilterVO.setCarrierCode(carrierCode.trim().toUpperCase());
				carrierDest = carrierCode.trim().toUpperCase();
			}
		}
		//Added by A-3429 for ICRD-83340
			String destination = searchContainerForm.getDestination();
			if (destination != null && destination.trim().length() > 0) {
				searchContainerFilterVO.setFinalDestination(destination.toUpperCase());
				AirportValidationVO airportValidationVO = null;
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    		try {
	    			airportValidationVO = new AreaDelegate().validateAirportCode(
						logonAttributes.getCompanyCode(),destination.toUpperCase());
				}
				catch(BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
	   			}
				if (errors != null && errors.size() > 0) {
				errorsMail.add(new ErrorVO("mailtracking.defaults.invalidairport",
			   				new Object[]{destination.toUpperCase()}));
				}
				
				
			}
		//Added by A-3429 for ICRD-83340
		if (!"".equals(carrier)) {
			/**
    		 * to get  Airline identifier for Login station
    		 */
    		AirlineValidationVO curOwnerVO = null;
    		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    		try {
    			curOwnerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),carrier);
			}
			catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
   			}
			if(curOwnerVO != null) {
				searchContainerFilterVO.setCarrierId(curOwnerVO.getAirlineIdentifier());
			}
			if (errors != null && errors.size() > 0) {
			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidcarrier",
		   				new Object[]{carrier}));
			}
		}
		
		if (!"".equals(carrierDest)) {
			/**
    		 * to get  Airline identifier for Login station
    		 */
    		AirlineValidationVO curOwnerVO = null;
    		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    		try {
    			curOwnerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),carrierDest);
			}
			catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
   			}
			if(curOwnerVO != null) {
				searchContainerFilterVO.setDestionationCarrierId(curOwnerVO.getAirlineIdentifier());
			}
			if (errors != null && errors.size() > 0) {
			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidcarrier",
		   				new Object[]{carrierDest}));
			}
		}

		
		return errorsMail;
				
	}

	// Added by Server on handling PageAwareMultiMapper
	/**
	 * method to bulid the hashmap to maintain absoluteindex
	 *
	 * @param existingMap HashMap<String, String>
	 * @param page Page
	 * @return HashMap<String, String>
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
	 * This method is to format flightNumber
	 * Not using - CRQ-AirNZ989-12
	 * @param flightNumber 
	 * @return String
	 */
	/*private String formatFlightNumber(String flightNumber){
		
		int numLength = flightNumber.length();
		String newFlightNumber = "" ;
	    
		if(numLength == 1) { 
	    	newFlightNumber = new  StringBuilder("000").append(flightNumber).toString();
	    }else if(numLength == 2) {
	    	newFlightNumber = new  StringBuilder("00").append(flightNumber).toString();
	    }else if(numLength == 3) { 
	    	newFlightNumber = new  StringBuilder("0").append(flightNumber).toString();
	    }else {
	    	newFlightNumber = flightNumber ;
	    }
		return newFlightNumber;
		
	}*/
	
}
