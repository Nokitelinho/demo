/*
 * ListCommand.java Created on Apr 03, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermailmanifest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 * @author A-2553
 *
 */
public class ListCommand extends BaseCommand {
	 private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * declaring
	    * TARGET , MODULE & SCREENID
	    */
	   private static final String TARGET_SUCCESS= "list_success";
	   private static final String TARGET_FAILURE = "list_failure";
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.transfermailmanifest";
	   public static final String MAIL_OPERATIONS_TRANSFER_TRANSACTION="mail.operation.transferoutinonetransaction";
	   /**
	    * @param invocationContext
	    * @throws CommandInvocationException
	    */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("ListCommand","execute");
		
		TransferMailManifestForm transferMailManifestForm = (TransferMailManifestForm)invocationContext.screenModel;
    	TransferMailManifestSession transferMailManifestSession = getScreenSession(MODULE_NAME,SCREEN_ID); 
    	Boolean transfer = false;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
    	
    	//Added by A-5201 for ICRD-20902 starts
    	String displayPageBeforeReset = transferMailManifestForm.getDisplayPage();
		if(!TransferMailManifestForm.NAV_MOD_LIST.equalsIgnoreCase(transferMailManifestForm.getNavigationMode())){
			TransferManifestFilterVO transferManifestFilterVO = transferMailManifestSession.getTransferManifestFilterVO();
            if(transferManifestFilterVO != null){
                    populateForm(transferManifestFilterVO,transferMailManifestForm);
                    transferMailManifestForm.setDisplayPage(displayPageBeforeReset);                    
            }
	    }
		
		Map<String, String> systemParameters = findSystemParameterValues(sharedDefaultsDelegate);
		if(systemParameters!=null && systemParameters.get(MAIL_OPERATIONS_TRANSFER_TRANSACTION)!=null &&
				 !systemParameters.get(MAIL_OPERATIONS_TRANSFER_TRANSACTION).isEmpty()){
				if("Y".equals(systemParameters.get(MAIL_OPERATIONS_TRANSFER_TRANSACTION))){
					transfer=true;
				}
				else{
					transfer=false;		 		
  		 }
  		 }
		transferMailManifestForm.setTransferParameter(transfer);
		//Added by A-5201 for ICRD-20902 end
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		TransferManifestFilterVO transferManifestFilterVO = new TransferManifestFilterVO();
		
		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		
//		 Added by Sever for handling PageAwareMultiMapper implementation
		//HashMap<String, String> indexMap = null;
		HashMap<String, String> indexMap = getIndexMap(transferMailManifestSession.getIndexMap(), invocationContext);//Added by A-5201 for ICRD-20902
		HashMap<String, String> finalMap = null;
		if (transferMailManifestSession.getIndexMap() != null) {
			indexMap = transferMailManifestSession.getIndexMap();
		}

		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
		
		int nAbsoluteIndex = 0;
		String displayPage = transferMailManifestForm.getDisplayPage();
		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		transferManifestFilterVO.setPageNumber(Integer.parseInt(displayPage));
		// Server ends
		
		
		String fromDate = transferMailManifestForm.getFromDate();
		String toDate = transferMailManifestForm.getToDate();
		if ("".equals(fromDate)) {
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.transfermailmanifest.msg.err.noFromDate");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}
		if ("".equals(toDate)) {
			ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.transfermailmanifest.msg.err.noToDate");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}
		if(formErrors== null || formErrors.size()== 0){
			if (!fromDate.equals(toDate)) {
				if (!DateUtilities.isLessThan(fromDate, toDate,"dd-MMM-yyyy")) {
			    	log.log(Log.FINE, "FROM DATE GRTR THAN TO DATE=========>");
			    	ErrorVO errorVO = new ErrorVO(
			    	"mailtracking.defaults.transfermailmanifest.msg.err.fromgrtrtodat");
			    	formErrors.add(errorVO);
			    }
			}				
		}
		if (formErrors != null && formErrors.size() > 0) {
			invocationContext.addAllError(formErrors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		transferManifestFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(transferMailManifestForm.getReferenceNumber()!=null && transferMailManifestForm.getReferenceNumber().length()>0){
			transferManifestFilterVO.setReferenceNumber(transferMailManifestForm.getReferenceNumber());
		}
		if(transferMailManifestForm.getFromDate()!=null && transferMailManifestForm.getFromDate().length()>0){
			LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			transferManifestFilterVO.setFromDate(date.setDate(transferMailManifestForm.getFromDate()));
		}
		if(transferMailManifestForm.getToDate()!=null && transferMailManifestForm.getToDate().length()>0){
			LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			transferManifestFilterVO.setToDate(date.setDate(transferMailManifestForm.getToDate()));
		}
		if(transferMailManifestForm.getInCarrierCode()!=null && transferMailManifestForm.getInCarrierCode().length()>0){
			transferManifestFilterVO.setInCarrierCode(transferMailManifestForm.getInCarrierCode());
		}
		if(transferMailManifestForm.getInFlightNumber()!=null && transferMailManifestForm.getInFlightNumber().length()>0){
			transferManifestFilterVO.setInFlightNumber(transferMailManifestForm.getInFlightNumber());
		}
		if(transferMailManifestForm.getInFlightDate()!=null && transferMailManifestForm.getInFlightDate().length()>0){
			LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			transferManifestFilterVO.setInFlightDate(date.setDate(transferMailManifestForm.getInFlightDate()));
		}
		if(transferMailManifestForm.getOutCarrierCode()!=null && transferMailManifestForm.getOutCarrierCode().length()>0){
			transferManifestFilterVO.setOutCarrierCode(transferMailManifestForm.getOutCarrierCode());
		}
		if(transferMailManifestForm.getOutFlightNumber()!=null && transferMailManifestForm.getOutFlightNumber().length()>0){
			transferManifestFilterVO.setOutFlightNumber(transferMailManifestForm.getOutFlightNumber());
		}
		if(transferMailManifestForm.getOutFlightDate()!=null && transferMailManifestForm.getOutFlightDate().length()>0){
			LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			transferManifestFilterVO.setOutFlightDate(date.setDate(transferMailManifestForm.getOutFlightDate()));
		}
		if(transferMailManifestForm.getAirportCode()!=null && transferMailManifestForm.getAirportCode().length()>0){
			transferManifestFilterVO.setAirportCode(transferMailManifestForm.getAirportCode());
		}
		if(transferMailManifestForm.getTransferStatus()!=null && transferMailManifestForm.getTransferStatus().length()>0){
			transferManifestFilterVO.setTransferStatus(transferMailManifestForm.getTransferStatus());
		}
		
		log.log(Log.INFO, "navigationMode: ", transferMailManifestForm.getNavigationMode());
		log.log(Log.INFO, "lastPageNumber: ", transferMailManifestForm.getLastPageNum());
		if(TransferMailManifestForm.NAV_MOD_LIST.equalsIgnoreCase(transferMailManifestForm.getNavigationMode()) || transferMailManifestForm.getNavigationMode() == null ){
			log.log(Log.INFO, "list action");
			transferManifestFilterVO.setTotalRecordsCount(-1);
			//transferManifestFilterVO.setPageNumber(1);
		}else if(TransferMailManifestForm.NAV_MOD_PAGINATION.equalsIgnoreCase(transferMailManifestForm.getNavigationMode())){
			log.log(Log.INFO, "navigation action");
			if( transferMailManifestSession !=null ){
				transferManifestFilterVO.setTotalRecordsCount(transferMailManifestSession.getTotalRecordCount());
			}
			if(transferMailManifestForm.getDisplayPage() != null && !("").equals (transferMailManifestForm.getDisplayPage())){
				transferManifestFilterVO.setPageNumber(Integer.parseInt(transferMailManifestForm.getDisplayPage()));
			}
		}
		log.log(Log.INFO, "pageNumber: ", transferManifestFilterVO.getPageNumber());
		Page<TransferManifestVO> transferManifestVOs =
			findTransferManifest(transferManifestFilterVO);
		if (transferManifestVOs == null
				|| (transferManifestVOs != null &&
						transferManifestVOs.size() == 0)) {
			transferMailManifestSession.setTransferManifestVOs(null);
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.noresultsfound");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
			invocationContext.addAllError(formErrors);
			transferMailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			log.log(Log.FINE, "transferManifestVOs IS NULL");
		} else {
			log.log(Log.FINE, "transferManifestVOs IS not NULL",
					transferManifestVOs);
			transferMailManifestForm.setDisableBtn("Y");
			transferMailManifestSession.setTransferManifestVOs(transferManifestVOs);
			transferMailManifestSession.setTransferManifestFilterVO(transferManifestFilterVO);
			transferMailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);

			//Added by A-5220 for ICRD-21098 starts
			if(transferMailManifestSession != null){
				transferMailManifestSession.setTotalRecordCount(transferManifestVOs.getTotalRecordCount());
			}
			//Added by A-5220 for ICRD-21098 ends

		}
		
		//		 Added by Server on handling PageAwareMultiMapper
		finalMap = indexMap;
		if (transferMailManifestSession.getTransferManifestVOs()!= null) {
			finalMap = buildIndexMap(indexMap, transferMailManifestSession.getTransferManifestVOs());
		}
		//transferMailManifestSession.setIndexMap(finalMap);
		transferMailManifestSession.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext));//Added by A-5201 for ICRD-20902
		log.log(Log.INFO, "GOING TO PUT FINAL MAP IN SESSION---------*****",
				finalMap);
		// Server ends
		invocationContext.target = TARGET_SUCCESS;
       	
    	log.exiting("ListCommand","execute");		
		
	}

	private Map<String, String> findSystemParameterValues(SharedDefaultsDelegate sharedDefaultsDelegate) {
		Map<String, String> systemParameters = null;
		Collection<String> parameterTypes = new ArrayList<>();
		parameterTypes.add(MAIL_OPERATIONS_TRANSFER_TRANSACTION);
		try {
    	     systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}
		return systemParameters;
	}
	/**
	 * Method to find TransferManifest matching the specified criteria.
	 *
	 * @param transferManifestFilterVO
	 * @return Page<TransferManifestVO>
	 */
	private Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO transferManifestFilterVO) {
		Page<TransferManifestVO> transferManifestVOs = null;
		
		try {
			log.log(Log.FINE, "transferManifestFilterVO.getAbsoluteIndex()=",
					transferManifestFilterVO.getPageNumber());
			transferManifestVOs = new MailTrackingDefaultsDelegate().findTransferManifest(transferManifestFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return transferManifestVOs;
	}
	
//	 Added by Server on handling PageAwareMultiMapper
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
	//Added by A-5201 for ICRD-20902 starts
	private void populateForm(TransferManifestFilterVO FilterVO,TransferMailManifestForm transferMailManifestForm){
		transferMailManifestForm.setReferenceNumber(FilterVO.getReferenceNumber());
		if(FilterVO.getFromDate() != null){
			transferMailManifestForm.setFromDate(FilterVO.getFromDate().toDisplayDateOnlyFormat());
		}
		if(FilterVO.getToDate() != null){
			transferMailManifestForm.setToDate(FilterVO.getToDate().toDisplayDateOnlyFormat()); 
		}
		transferMailManifestForm.setInCarrierCode(FilterVO.getInCarrierCode());
		transferMailManifestForm.setInFlightNumber(FilterVO.getInFlightNumber());
		if(FilterVO.getInFlightDate() != null){
			transferMailManifestForm.setInFlightDate(FilterVO.getInFlightDate().toDisplayDateOnlyFormat());
		}
		transferMailManifestForm.setInFlightNumber(FilterVO.getInFlightNumber());
		transferMailManifestForm.setOutCarrierCode(FilterVO.getOutCarrierCode());
		transferMailManifestForm.setOutFlightNumber(FilterVO.getOutFlightNumber());
		if(FilterVO.getOutFlightDate() != null){
			transferMailManifestForm.setOutFlightDate(FilterVO.getOutFlightDate().toDisplayDateOnlyFormat());
		}
		transferMailManifestForm.setOutFlightNumber(FilterVO.getOutFlightNumber());
	}
	//Added by A-5201 for ICRD-20902 end
}
