/*
 * ListCustRegCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerListFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.customer.CustomerDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 * 
 */
public class ListCustRegCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";
	// Added by A-5290 for ICRD-138868
	private static final String MAINTAINCUST_SCREEN_ID = "customermanagement.defaults.maintainregcustomer";

	private static final String BLANK = "";
	// CODE ADDED BY A-5219 FOR ICRD-18283 START
	private static final String REGION = "R";
	private static final String COUNTRY = "C";
	private static final String STATION = "S";
	private static final String ISAGENT="AG";
	private static final String ISCCCOLLECTOR="CC";
	private static final String ISCUSTOMER="CU";
	private static final String CHECKSTATUS = "on";
	// CODE ADDED BY A-5219 END
	//Added by A-8130 for CR ICRD-257611
	private static final String CUSTOMERCODE_VIEW_AUTHORISATION = "customermanagement.defaults.error.viewauthorisation";
	private static final String TRANSACTION_CODE_LSTCUS = "LSTCUS";
	private static final String LEVEL_TYPE_CUSGRP = "CUSGRP";
	private static final String LEVEL_TYPE_CUS = "CUS";
	private static final String LEVEL_TYPE_COUNTRY = "CNT";
	private static final String LEVEL_TYPE_STATION = "STN";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		Log log = LogFactory.getLogger(" customerlisting");
		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		// Added by A-5290 for ICRD-138868
		MaintainCustomerRegistrationSession maintainSession = getScreenSession(MODULENAME,MAINTAINCUST_SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//Added by A-7534 as part of ICRD-227236 starts
		MessageInboxSession messageInboxSession = 
				(MessageInboxSession)getScreenSession("workflow.defaults", "workflow.defaults.messageinbox");
		if(messageInboxSession.getMessageDetails() != null){
			populateMessageInboxSessionDetails(messageInboxSession, form);
		}
		//Added by A-7534 as part of ICRD-227236 ends
		errors = validateForm(form);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		//Modified by A-5220 for ICRD-20902 starts
		HashMap<String, String> indexMap = getIndexMap(session.getIndexMap(), invocationContext); 
		//Modified by A-5220 for ICRD-20902 ends
		HashMap<String, String> finalMap = null;
		CustomerListFilterVO filterVO = new CustomerListFilterVO();
		//Added by A-8130 for CR ICRD-257611
		boolean isValidType = false;
		if (session.getIndexMap() != null) {
			indexMap = session.getIndexMap();
		} else {
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String strAbsoluteIndex = (String) indexMap.get(form
				.getDisplayPageNum());
		form.setAbsoluteIndex(strAbsoluteIndex);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
			filterVO.setAbsoluteIndex(nAbsoluteIndex);
		}
		
		/* Added by A-5173 for ICRD-3761 Starts */
		
		
		if ( ListCustomerForm.PAGINATION_MODE_FROM_LIST.equals(form.getNavigationMode() ) ||
				ListCustomerForm.PAGINATION_MODE_FROM_ACTIVATE.equals(form.getNavigationMode()) ||
				ListCustomerForm.PAGINATION_MODE_FROM_INACTIVATE.equals(form.getNavigationMode())		
				)
				{

			log.log(Log.INFO, "PAGINATION_MODE_FROM_NAVIGATION_LIST ");
			
			filterVO.setTotalRecords(-1);
			//Commented by A-5220 for ICRD-20902 starts
			//filterVO.setPageNumber(1);
			//Commented by A-5220 for ICRD-20902 ends

		}else if(ListCustomerForm.PAGINATION_MODE_FROM_NAVIGATION_LINK.equals(form.getNavigationMode()) ) {

			
			filterVO.setTotalRecords(session.getTotalRecords());
			filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
			
			log.log(Log.INFO, "PAGINATION_MODE_FROM_NAVIGATION_LINK : TotalRecordCount :" +session.getTotalRecords());

		}else{
			if(session.getTotalRecords() != null){
			filterVO.setTotalRecords(session.getTotalRecords());			
			}
			else{
			filterVO.setTotalRecords(0);			
			}
		}

		
		//CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		CustomerDelegate delegate = new CustomerDelegate();
		
		/* Added by A-5173 for ICRD-3761 Ends */
		
		Page<CustomerVO> customerVOs = null;
		StringBuilder multiSelect=new StringBuilder();

		filterVO.setCompanyCode(companyCode);
		//added the "WRKFLWMSGINB" condition check for CR ICRD-76222(for Customer Master,while clicking Process Task from Message Inbox screen, 
		//system should navigate to Customer Master and List all the Customer details which are about to expire with in the specified days in job argument.
		if ("Y".equals(form.getCloseStatus()) ||
				"WRKFLWMSGINB".equals(form.getNavigationMode())) {
			if(session.getListFilterVO() != null){
			filterVO = session.getListFilterVO();			
			}
			if(session.getTotalRecords() != null){
			filterVO.setTotalRecords(session.getTotalRecords());
			}
			if(form.getDisplayPage() !=null){
			filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
			}
			if(filterVO.getExpiringBefore() != null){
				form.setExpiringBefore(filterVO.getExpiringBefore().toDisplayDateOnlyFormat());
			}
			session.setListFilterVO(null);
			/*
			 *  Added by A-5290 for ICRD-138868
			 * 	clearing the session value for removing the caching problem after navigation
			 */
			maintainSession.setCustomerCodesFromListing(null);
		} else {
			if (form.getCustCode() != null && !BLANK.equals(form.getCustCode())) {
				filterVO.setCustomerCode(form.getCustCode().toUpperCase());
			}
			// Added by A-7621 for < ICRD-132149 > Starts
			
			if (form.getIataCode() != null
					&& form.getIataCode().trim().length() > 0) {
				filterVO.setIataCode(form.getIataCode()
						.toUpperCase());
			}
			// Added by A-7621 for < ICRD-132149 > Ends
			
			if (form.getLoyaltyName() != null
					&& form.getLoyaltyName().trim().length() > 0) {
				filterVO.setLoyaltyProgramme(form.getLoyaltyName()
						.toUpperCase());
			}
			if (!"ALL".equals(form.getStatus())) {
				filterVO.setActiveStatus(form.getStatus());
			}
			filterVO.setPageNumber(Integer.parseInt(form.getDisplayPageNum()));
			if (form.getCustStation() != null
					&& form.getCustStation().trim().length() > 0) {
				filterVO.setStationCode(form.getCustStation().toUpperCase());
			}
			//Modified by A-5291 for ICRD-34060 Starts
			// CODE ADDED BY A-5219 FOR ICRD-18283 START
			if ((form.getLocationType() != null) && (form.getLocationType().trim().length() > 0)) {
				filterVO.setLocationType(form.getLocationType());

				if ((form.getLocationValue() != null) && (form.getLocationValue().trim().length() > 0)) {
					filterVO.setLocationValue(form.getLocationValue());
				}
			} 
			//Added by A-7534 for ICRD-228903
			if(form.getInternalAccountHolder()!=null && form.getInternalAccountHolder().trim().length()>0){
				filterVO.setInternalAccountHolder(form.getInternalAccountHolder());
			}
			// added for ICRD-35687 starts
			String[] custType = null;
			if(form.getCustType()!= null && form.getCustType().contains(",")){ 
			   custType=form.getCustType().split(",");
			}else if(form.getCustType()!= null){
				custType = new String[]{form.getCustType()};
			}
			if(custType!=null && custType.length>0){
				for (int i=0 ; i<custType.length ; i++){
					if(custType[i] != null && custType[i].length()>0){
					   multiSelect.append("'".concat(custType[i]).concat("',"));
					}
				}
			}
			int len = multiSelect.length();
			if(len>0){
			multiSelect.replace(len - 1, len, "");
			}
			filterVO.setCustomerType(multiSelect.toString());
			if(form.getCassAgent()!=null && form.getCassAgent().equals(CHECKSTATUS)){
				filterVO.setCassAgent(true);
			}
			filterVO.setClearingAgentFlag(form.isClearingAgentFlag());// Added BY A-8374 For ICRD-247693
			// ICRD-35687 ends
			// commented as part of ICRD-35687
			/*if(form.getAgent()!=null && form.getAgent().equals(CHECKSTATUS)){
				multiSelect.append("'".concat(ISAGENT).concat("',"));
			}
			if(form.getCustomer()!=null && form.getCustomer().equals(CHECKSTATUS)){
				multiSelect.append("'".concat(ISCUSTOMER).concat("',"));
			}
			if(form.getCccollector()!=null && form.getCccollector().equals(CHECKSTATUS)){
				multiSelect.append("'".concat(ISCCCOLLECTOR).concat("',"));
			}
			// Managing comma in the customerType 
				int len = multiSelect.length();
				if(len>0){
				multiSelect.replace(len - 1, len, "");
				}
				filterVO.setCustomerType(multiSelect.toString());*/
			//Modified by A-5291 for ICRD-34060 Ends

			LocalDate localExprBfre= null;
			String exprBfre = form.getExpiringBefore();
			if (exprBfre != null && exprBfre.trim().length() > 0) {
				//if(form.getExpiringBefore() != null && form.getExpiringBefore().trim().length()>0){
					localExprBfre = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
				//}
				localExprBfre.setDate(form.getExpiringBefore());
				//localToDate = convertToLocalDate(listFlightForm.getToDate(), flightFilterVO.getStation());
			}
			filterVO.setExpiringBefore(localExprBfre);
			log.log(Log.FINE, "filter vo to server-------->" + filterVO);
		}
		// CODE ADDED BY A-5219 END
		session.setListFilterVO(filterVO);
		try {
			//Modified by A-8130 for the CR ICRD-257611
		    isValidType = hasTransactionPrivilege(filterVO);
			if(isValidType){
			/* Modified by A-5173 for ICRD-3761 Starts */
			
			//customerVOs = delegate.listCustomerDetails(filterVO);			
			customerVOs = delegate.listCustomers(filterVO);
			}
			/* Modified by A-5173 for ICRD-3761 Ends */
			
		} catch (BusinessDelegateException ex) {
//printStackTrrace()();
			handleDelegateException(ex);
		}
		if (customerVOs != null && customerVOs.size() > 0) {
			boolean isWorkFlowMessageProcessed = false;
			for (CustomerVO custVO : customerVOs) {
				custVO.setPointsToBeRedeemed(custVO.getPointsAccruded()
						- custVO.getPointsRedeemed());
				if(messageInboxSession.getParameterMap()!=null &&
						custVO.getCustomerCode().equalsIgnoreCase(messageInboxSession.getParameterMap().get("CUSCOD"))
						&& "A".equalsIgnoreCase(custVO.getStatus())){
					isWorkFlowMessageProcessed = true;
				}
			}
			session.setCustomerVOs(customerVOs);
			
			
			
			// Added by A-5173 for < ICRD-3761 > Starts
				
				//log.log(Log.INFO, " Total Record Count : "+customerVOs.getTotalRecordCount());
				session.setTotalRecords(customerVOs.getTotalRecordCount());
			
			// Added by A-5173 for < ICRD-3761 > Ends
						
			
			form.setCloseStatus(BLANK);
			finalMap = indexMap;
			if (session.getCustomerVOs() != null) {
				finalMap = buildIndexMap(indexMap, session.getCustomerVOs());
				//Modified by A-5220 for ICRD-20902 starts
				session.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext));
				//Modified by A-5220 for ICRD-20902 ends
			}
			invocationContext.target = LIST_SUCCESS;
			if(messageInboxSession.getMessageDetails() != null && isWorkFlowMessageProcessed){
				invocationContext.target = "changestatus_success";
			}
			return;
		}
		//Added by A-8130 for the CR ICRD-257611
		else if(!isValidType){
			form.setCustCode(BLANK);
			ErrorVO errorVO = new ErrorVO(CUSTOMERCODE_VIEW_AUTHORISATION);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_SUCCESS;
			return;
			
		}
		else {
			session.setCustomerVOs(null);
			ErrorVO errorVO = new ErrorVO(
					"customermanagement.defaults.custlisting.msg.err.norecords");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_SUCCESS;
			return;

		}

	}

	/***************************************************************************
	 * /
	 * 
	 * @param form
	 * @return
	 * 
	 * public Collection<ErrorVO> validateForm(ListCustomerForm form){ ErrorVO
	 * error = null; Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	 * if(BLANK.equals(form.getCustCode())){ error = new
	 * ErrorVO("customermanagement.defaults.custlisting.msg.err.custcodemandatory");
	 * errors.add(error); } return errors; }
	 */
	/**
	 * @param indexMap
	 * @param customerVOPage
	 * @return HashMap
	 */
	private HashMap buildIndexMap(HashMap indexMap,
			Page<CustomerVO> customerVOPage) {
		HashMap existingMap = indexMap;
		String indexPage = String.valueOf((customerVOPage.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = indexMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			existingMap.put(indexPage, String.valueOf(customerVOPage
					.getEndIndex()));  
		}

		return existingMap;
	}
	 /**
	 * @param customerForm
	 * @return Collection
	 */
	private Collection<ErrorVO> validateForm(ListCustomerForm customerForm)
	    {
	        Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	        if(customerForm.getLocationType() != null && customerForm.getLocationType().trim().length() > 0 && (customerForm.getLocationValue() == null || customerForm.getLocationValue().trim().length() == 0))
	        {
	            ErrorVO errorVO = new ErrorVO("customermanagement.defaults.listcustomer.enterlocationvalue");
	            errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	            errors.add(errorVO);
	        }
	        if(customerForm.getLocationValue() != null && customerForm.getLocationValue().trim().length() > 0 && (customerForm.getLocationType() == null || customerForm.getLocationType().trim().length() == 0))
	        {
	            ErrorVO errorVO = new ErrorVO("customermanagement.defaults.listcustomer.enterlocationtype");
	            errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	            errors.add(errorVO);
	        }
	        return errors;
	    }
	/**
	 * 
	 * 	Method		:	ListCustRegCommand.populateMessageInboxSessionDetails
	 *	Added by 	:	A-7534 on 23-Feb-2018
	 * 	Used for 	:
	 *	Parameters	:	@param messageInboxSession
	 *	Parameters	:	@param form 
	 *	Return type	: 	void
	 */
	private void populateMessageInboxSessionDetails(MessageInboxSession messageInboxSession,
			ListCustomerForm form){
		if(messageInboxSession.getParameterMap().get("CUSCOD")!=null){
			form.setCustCode(messageInboxSession.getParameterMap().get("CUSCOD"));
		}
		form.setScreenFlag("");//to enable the buttons on CMT002
	}
	/**
	 * 
	 * 	Method		:	ListCustRegCommand.hasTransactionPrivilage
	 *	Added by 	:	A-8130 on 31-05-2018
	 * 	Used for 	:   ICRD-257611
	 *	Parameters	:	@param filterVO
	 *	Return type	: 	boolean
	 */
	public boolean hasTransactionPrivilege(CustomerListFilterVO filterVO)  {
		Log log = LogFactory.getLogger(" hasTransactionPrivilege----->"+filterVO);
		boolean isValidType=false;
		String privilegedCountry="";
		Collection<TransactionPrivilegeNewVO> privilegesList = null ;
		CustomerDelegate delegate = new CustomerDelegate();
		CustomerVO customerVO = null;
		if(filterVO.getCustomerCode()!=null && ! ("".equals(filterVO.getCustomerCode()))){
		CustomerFilterVO custFilterVO = new  CustomerFilterVO();
		custFilterVO.setCompanyCode(filterVO.getCompanyCode());
		custFilterVO.setCustomerCode(filterVO.getCustomerCode());
		custFilterVO.setCustomerStatus(filterVO.getActiveStatus());
		try {
			customerVO = delegate.validateCustomer(custFilterVO);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		     }
	     }
		try {
			privilegesList =TransactionPrivilegeHelper.getAllowededPrivilegesForTransaction(TRANSACTION_CODE_LSTCUS);	 
		} catch (SystemException e) {
			log.log(Log.FINE,"System Exception occurred while finding transaction privilage for customer"+e.getMessage());
		}
		if(privilegesList==null || privilegesList.isEmpty()) {
			return true;
		}
		if(privilegesList!=null && !privilegesList.isEmpty()){
			for (TransactionPrivilegeNewVO transactionPrivilegeNewVO : privilegesList) {
				//Modified by A-8374 for ICRD-344771
				if(transactionPrivilegeNewVO != null && (LEVEL_TYPE_CUSGRP.equals(transactionPrivilegeNewVO.getLevelType()) ||
						LEVEL_TYPE_CUS.equals(transactionPrivilegeNewVO.getLevelType()))){
					if( transactionPrivilegeNewVO.getTypeValue() != null && transactionPrivilegeNewVO.getTypeValue().length() > 0 ){
						if(filterVO.getCustomerCode()!= null){
							if(customerVO.getCustomerCode() != null && customerVO != null){
								/*if( transactionPrivilegeNewVO.getTypeValue() != null 
										&& transactionPrivilegeNewVO.getTypeValue().length() > 0){*/
								String typeValue=transactionPrivilegeNewVO.getTypeValue();
								String[] typeArray = typeValue.split(","); 
								if(typeArray != null && typeArray.length > 0) {
									for (String type: typeArray) {  
										if(type.equalsIgnoreCase(filterVO.getCustomerCode())) {
											isValidType=true;
										}
									}
								}

								/*}else{
									isValidType=true;
								}*/
							}else{
								filterVO.setPrivilegeLevel(transactionPrivilegeNewVO.getLevelType());
								filterVO.setPrivilegeLevelValue(transactionPrivilegeNewVO.getTypeValue());
								isValidType=true;
							}
						}else{
							filterVO.setPrivilegeLevel(transactionPrivilegeNewVO.getLevelType());
							filterVO.setPrivilegeLevelValue(transactionPrivilegeNewVO.getTypeValue());
							isValidType=true;
						}
					}else {
						isValidType=true;
					}
				}else if(((LEVEL_TYPE_COUNTRY.equals(transactionPrivilegeNewVO.getLevelType())) && (transactionPrivilegeNewVO.getTypeValue() != null))){
					String[] countries = transactionPrivilegeNewVO.getTypeValue().split(",");
					for (String country : countries) {
						privilegedCountry = privilegedCountry.concat(country).concat(",");
					}
					filterVO.setCountryCodes(privilegedCountry);
					filterVO.setCountryPrivilege(transactionPrivilegeNewVO.getLevelType());
					isValidType=true;
				}else if (transactionPrivilegeNewVO != null && (LEVEL_TYPE_STATION.equals(transactionPrivilegeNewVO.getLevelType())) && (transactionPrivilegeNewVO.getTypeValue() != null)){
					filterVO.setStationForPrivilege(transactionPrivilegeNewVO.getTypeValue());
					isValidType=true;						
				}
				else {
					isValidType=true;
				}
			} /*else {
			  isValidType=true;
		     }*/
		
	    }return isValidType;
	}
}
