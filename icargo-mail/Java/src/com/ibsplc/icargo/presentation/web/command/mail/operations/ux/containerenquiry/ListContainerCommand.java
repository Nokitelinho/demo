/*
 * ListContainerCommand.java Created on Sep 26, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.ListContainerCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	26-Sep-2018	:	Draft
 */

public class ListContainerCommand extends AbstractCommand {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.listcontainer";
	
	private Log log = LogFactory.getLogger("Mail Operations Mailbag Enquiry ");

	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("ListContainerCommand", "execute");
		
		ArrayList results = new ArrayList();
		List<ErrorVO> erros= null;
		String displayPage = ""; 
		Page<ContainerVO>  containerVOPage = null;		
 
		  
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();		
		ContainerFilter filterContainerModel = (ContainerFilter)listContainerModel.getContainerFilter();
		ResponseVO responseVO = new ResponseVO();
		SearchContainerFilterVO searchContainerFilterVO = new  SearchContainerFilterVO();

		
		erros=(List<ErrorVO>)updateFilterVO(filterContainerModel, searchContainerFilterVO,logonAttributes);
        if (erros!=null &&erros.size()>0){        	
        	actionContext.addAllError(erros);
        	return; 
        }	

		containerVOPage =findContainers(searchContainerFilterVO,
					filterContainerModel.getDisplayPage());
		if(containerVOPage==null || containerVOPage.size()==0){			
			actionContext.addError(new ErrorVO("No data found"));		
			return;
		}else{	
			ArrayList<ContainerDetails> containers = new ArrayList<ContainerDetails>();		
			String actualWeightUnit=null;
			try {
				actualWeightUnit=findSystemParameterValue("mail.operations.defaultcaptureunit");
			} catch (SystemException e2) {
				e2.printStackTrace();
			}
			if(containerVOPage!=null && containerVOPage.size()>0){
				for( ContainerVO containerVO : containerVOPage){	
					if(actualWeightUnit!=null){
						containerVO.setActualWeightUnit(actualWeightUnit);
					}
				}			
			}
		ArrayList containerList = MailOperationsModelConverter.constructContainer(containerVOPage,logonAttributes); //modified by A-7929 as part of ICRD-269984
		PageResult pageList = new PageResult(containerVOPage, containerList);		
		listContainerModel.setContainerDetails(pageList);
		 boolean hasPrivilegeToViewEstimatedCharge = false;
 		try {
 			hasPrivilegeToViewEstimatedCharge = SecurityAgent.getInstance()
 					.checkBusinessPrivilege("mail.operations.privilegerequiredforestimatedcharge");
 			
 		} catch (SystemException systemException) {
 			systemException.getMessage();
 			log.log(Log.INFO,systemException);
 		}
 		listContainerModel.setEstimatedChargePrivilage(hasPrivilegeToViewEstimatedCharge);
		results.add(listContainerModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);}
		log.exiting("ListMailbagsCommand","execute");

	}


    private Page<ContainerVO> findContainers(SearchContainerFilterVO searchContainerFilterVO, String displayPage)
    {
      Page containerVOs = null;
      System.out.println("Inside findContainers-->"+searchContainerFilterVO.toString());
      //System.out.println("Inside findContainers-->"+searchContainerFilterVO.toDisplayString());
      

      int pageNumber = Integer.parseInt(displayPage);
      try
      {
        this.log.log(3, new Object[] { "searchContainerFilterVO.getAbsoluteIndex()=", 
          Integer.valueOf(searchContainerFilterVO.getAbsoluteIndex()) });
        containerVOs = new MailTrackingDefaultsDelegate().findContainers(searchContainerFilterVO, 
          pageNumber);
      } catch (BusinessDelegateException businessDelegateException) {
        businessDelegateException.getMessageVO().getErrors();
        handleDelegateException(businessDelegateException);
      }
      return containerVOs;
    }
    
    
    private Collection<ErrorVO> updateFilterVO(ContainerFilter filterContainerModel, SearchContainerFilterVO searchContainerFilterVO, LogonAttributes logonAttributes)
    {
      String oprType;
      AirlineValidationVO curOwnerVO;
      Collection errors;
      System.out.println("Inside container list"+filterContainerModel);
      System.out.println("Inside container list"+filterContainerModel.getAssignedTo());
      searchContainerFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
      if ("ALL".equals(filterContainerModel.getAssignedTo())) {
        searchContainerFilterVO.setSearchMode("ALL");
        searchContainerFilterVO.setOperationType(filterContainerModel.getOperationType());
      }
      else if ("DESTN".equals(filterContainerModel.getAssignedTo())) {
        searchContainerFilterVO.setSearchMode("DESTN");
      } else if ("FLT".equals(filterContainerModel.getAssignedTo())) {
        searchContainerFilterVO.setSearchMode("FLT");
        oprType = filterContainerModel.getOperationType();
        if ((oprType != null) && 
          (oprType.trim().length() > 0))
          searchContainerFilterVO.setOperationType(oprType);

      }else{
    	  searchContainerFilterVO.setSearchMode("ALL");
      }

      LocalDate afd = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, false);
      LocalDate atd = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, false);
      LocalDate fd = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, false);

      Collection errorsMail = new ArrayList();

      String containerNo = filterContainerModel.getContainerNo();
      if ((containerNo != null) && (containerNo.trim().length() > 0)) {
        searchContainerFilterVO.setContainerNumber(containerNo.toUpperCase());
      }
      
      if(filterContainerModel.getContainersToList()!=null && //Added by A-8164 for mailinbound
    		  filterContainerModel.getContainersToList().size()>0){
    	  searchContainerFilterVO.setContainersToList(filterContainerModel.getContainersToList()); 
    	  for(String container:filterContainerModel.getContainersToList()){ 
    		  if(container.contains("BULK-")){
    			  searchContainerFilterVO.setBulkPresent(true);   
    			  searchContainerFilterVO.setFlightNumberFromInbound(
    					  filterContainerModel.getFlightNumberFromInbound());
    			  searchContainerFilterVO.setFlightCarrierCodeFromInbound(
    					  filterContainerModel.getFlightCarrierCodeFromInbound());
    			  searchContainerFilterVO.setLegSerialNumber(
    					  filterContainerModel.getLegSerialNumber());
    			  searchContainerFilterVO.setFlightSeqNumber(
    					  filterContainerModel.getFlightSeqNumber());
    		  }
    	  }
      }

      String fromDate = filterContainerModel.getFromDate();
      if ((fromDate != null) && (fromDate.trim().length() > 0)) {
        searchContainerFilterVO.setAssignedFromDate(afd.setDate(fromDate));
        searchContainerFilterVO.setStrFromDate(fromDate);
      }

      String toDate = filterContainerModel.getToDate();
      if ((toDate != null) && (toDate.trim().length() > 0)) {
        searchContainerFilterVO.setAssignedToDate(atd.setDate(toDate));
        searchContainerFilterVO.setStrToDate(toDate);
      }

      if ((searchContainerFilterVO.getAssignedToDate() != null) && 
        (searchContainerFilterVO.getAssignedFromDate() != null) && 
        (!(fromDate.equals(toDate))) && 
        (!(DateUtilities.isLessThan(fromDate, toDate, "dd-MMM-yyyy")))) {
        errorsMail.add(new ErrorVO("mailtracking.defaults.fromdategreatertodate"));
      }

      String depPort = filterContainerModel.getAirport();
      if ((depPort != null) && (depPort.trim().length() > 0)) {
        searchContainerFilterVO.setDeparturePort(depPort.toUpperCase());

        errors = new ArrayList();
        try {
          new AreaDelegate().validateAirportCode(
            logonAttributes.getCompanyCode(), depPort.toUpperCase());
        }
        catch (BusinessDelegateException businessDelegateException) {
          errors = handleDelegateException(businessDelegateException);
        }
        if ((errors != null) && (errors.size() > 0))
          errorsMail.add(
            new ErrorVO("mailtracking.defaults.invalidairport", 
            new Object[] { depPort.toUpperCase() }));

      }

      String assignedBy = filterContainerModel.getAssignedBy();
      if ((assignedBy != null) && (assignedBy.trim().length() > 0)) {
        searchContainerFilterVO.setAssignedUser(assignedBy.toUpperCase());
      }

      if (filterContainerModel.getToBeTransfered()!= null) {
        String transferable = filterContainerModel.getToBeTransfered().trim();
        searchContainerFilterVO.setCurrentAirport(logonAttributes.getAirportCode());
        if (("Y".equals(transferable)) || 
          ("on".equals(transferable)) || 
          ("true".equals(transferable)))
          searchContainerFilterVO.setTransferStatus("Y");
        else
          searchContainerFilterVO.setTransferStatus("N");
      }
      else {
        searchContainerFilterVO.setTransferStatus("N");
      }

      if (filterContainerModel.getNotClosedFlag() != null) {
        String closeFlag = filterContainerModel.getNotClosedFlag().trim();
        if (("Y".equals(closeFlag)) || 
          ("on".equals(closeFlag))|| 
          ("true".equals(closeFlag)))
          searchContainerFilterVO.setNotClosedFlag("Y");
        else
          searchContainerFilterVO.setNotClosedFlag("N");
      }
      else {
        searchContainerFilterVO.setNotClosedFlag("N");
      }

      if (filterContainerModel.getMailAcceptedFlag() != null) {
        String mailAcceptedFlag = filterContainerModel.getMailAcceptedFlag().trim();
        if (("Y".equals(mailAcceptedFlag)) || 
          ("on".equals(mailAcceptedFlag))|| 
          ("true".equals(mailAcceptedFlag)))
          searchContainerFilterVO.setMailAcceptedFlag("Y");
        else
          searchContainerFilterVO.setMailAcceptedFlag("N");
      }
      else {
        searchContainerFilterVO.setMailAcceptedFlag("N");
      }

      if (filterContainerModel.getShowEmpty() != null) {
        String showEmptyContainer = filterContainerModel.getShowEmpty().trim();
        if (("Y".equals(showEmptyContainer)) || 
          ("on".equals(showEmptyContainer))|| 
          ("true".equals(showEmptyContainer)))
          searchContainerFilterVO.setShowEmptyContainer("Y");
        else
          searchContainerFilterVO.setShowEmptyContainer("N");
      }
      else {
        searchContainerFilterVO.setShowEmptyContainer("N");
      }
      
      if (filterContainerModel.getShowUnreleased() != null) {
          String showUnreleasedContainer = filterContainerModel.getShowUnreleased().trim();
          if (("Y".equals(showUnreleasedContainer)) || 
            ("on".equals(showUnreleasedContainer))|| 
            ("true".equals(showUnreleasedContainer)))
            searchContainerFilterVO.setShowUnreleasedContainer("Y");
          else
            searchContainerFilterVO.setShowUnreleasedContainer("N");
        }
        else {
          searchContainerFilterVO.setShowUnreleasedContainer("N");
        }
     
      if (filterContainerModel.getSubclassGroup() != null) {
          String subclassGroup = filterContainerModel.getSubclassGroup().trim();
          if (("EMS".equals(subclassGroup)) || 
            ("on".equals(subclassGroup))|| 
            ("true".equals(subclassGroup)))
            searchContainerFilterVO.setSubclassGroup("EMS");
          else if("OTHERS".equals(subclassGroup)){
        	  searchContainerFilterVO.setSubclassGroup("OTHERS");
          }
          else
            searchContainerFilterVO.setSubclassGroup("NONEMS");
          
        }
        else {
          searchContainerFilterVO.setSubclassGroup("NONEMS");
        }
      if(null!=filterContainerModel.getUnplannedContainers()
    		  && "Y".equals(filterContainerModel.getUnplannedContainers())) {
    	  searchContainerFilterVO.setUnplannedContainers(true);
      }
      if(null!=filterContainerModel.getHbaMarkedFlag()) {
    	  searchContainerFilterVO.setHbaMarked(filterContainerModel.getHbaMarkedFlag());
      }
      searchContainerFilterVO.setNavigation(filterContainerModel.isNavigation());
      String carrier = "";
      String carrierDest = "";

      if ("FLT".equals(filterContainerModel.getAssignedTo())) {
        String flightCarrierCode = filterContainerModel.getCarrierCode();
        if ((flightCarrierCode != null) && 
          (flightCarrierCode.trim().length() > 0)) {
          searchContainerFilterVO.setFlightCarrierCode(flightCarrierCode.trim().toUpperCase());
          carrier = flightCarrierCode.trim().toUpperCase();
        }
        String flightNumber = filterContainerModel.getFlightNumber();
        if ((flightNumber != null) && (flightNumber.trim().length() > 0))
          searchContainerFilterVO.setFlightNumber(flightNumber.toUpperCase());

        String flightDate = filterContainerModel.getFlightDate();
        if ((flightDate != null) && (flightDate.trim().length() > 0)) {
          searchContainerFilterVO.setFlightDate(fd.setDate(flightDate));
          searchContainerFilterVO.setStrFlightDate(flightDate);
        }
      }
      else if ("DESTN".equals(filterContainerModel.getAssignedTo()))
      {
        String carrierCode = filterContainerModel.getCarrierCode();
        if ((carrierCode != null) && (carrierCode.trim().length() > 0)) {
          searchContainerFilterVO.setCarrierCode(carrierCode.trim().toUpperCase());
          carrierDest = carrierCode.trim().toUpperCase();
        }
      }

      String destination = filterContainerModel.getDestination();
      if ((destination != null) && (destination.trim().length() > 0) && !destination.equals("null")) {
        searchContainerFilterVO.setFinalDestination(destination.toUpperCase());

         errors = new ArrayList();
        try {
          new AreaDelegate().validateAirportCode(
            logonAttributes.getCompanyCode(), destination.toUpperCase());
        }
        catch (BusinessDelegateException businessDelegateException) {
          errors = handleDelegateException(businessDelegateException);
        }
        if ((errors != null) && (errors.size() > 0)) {
          errorsMail.add(
            new ErrorVO("mail.operations.ux.invalidairport", 
            new Object[] { destination.toUpperCase() }));
        }

      }
      if(filterContainerModel.getPageSize()!=null)
      searchContainerFilterVO.setPageSize(Integer.parseInt(filterContainerModel.getPageSize())); //Added by A-7929 as part of ICRD-320470
      if (!("".equals(carrier)))
      {
        curOwnerVO = null;
        errors = new ArrayList();
        try {
          curOwnerVO = new AirlineDelegate().validateAlphaCode(
            logonAttributes.getCompanyCode(), carrier);
        }
        catch (BusinessDelegateException businessDelegateException) {
          errors = handleDelegateException(businessDelegateException);
        }
        if (curOwnerVO != null)
          searchContainerFilterVO.setCarrierId(curOwnerVO.getAirlineIdentifier());

        if ((errors != null) && (errors.size() > 0))
          errorsMail.add(
            new ErrorVO("mailtracking.defaults.invalidcarrier", 
            new Object[] { carrier }));

      }

      if (!("".equals(carrierDest)))
      {
        curOwnerVO = null;
        errors = new ArrayList();
        try {
          curOwnerVO = new AirlineDelegate().validateAlphaCode(
            logonAttributes.getCompanyCode(), carrierDest);
        }
        catch (BusinessDelegateException businessDelegateException) {
          errors = handleDelegateException(businessDelegateException);
        }
        if (curOwnerVO != null)
          searchContainerFilterVO.setDestionationCarrierId(curOwnerVO.getAirlineIdentifier());

        if ((errors != null) && (errors.size() > 0)) {
          errorsMail.add(
            new ErrorVO("mailtracking.defaults.invalidcarrier", 
            new Object[] { carrierDest }));
        }

      }

      return errorsMail;
    }
    
    /**
     * 
     * 	Method		:	ListContainerCommand.findSystemParameterValue
     *	Added by 	:	A-8164 on 02-Aug-2019
     * 	Used for 	:
     *	Parameters	:	@param syspar
     *	Parameters	:	@return
     *	Parameters	:	@throws SystemException
     *	Parameters	:	@throws BusinessDelegateException 
     *	Return type	: 	String
     */
    private static String findSystemParameterValue(String syspar) throws SystemException, BusinessDelegateException {
    	String sysparValue = null;
    	ArrayList<String> systemParameters = new ArrayList<String>();
    	systemParameters.add(syspar);
    	Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
    			.findSystemParameterByCodes(systemParameters);
    	if (systemParameterMap != null) {
    		sysparValue = systemParameterMap.get(syspar);
    	}
    	return sysparValue;
    }

}
