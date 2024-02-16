/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.AutoMCACommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Mar 5, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GPABillingEnquiryModel;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.AutoMCACommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Mar 5, 2019	:	Draft
 */ 
public class AutoMCACommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("MAIL MRA GPABILLING");
	private static final String STATUS_SUCCESS = "success";
	private static final String SYS_PARAM_NO_RECORDS = "cra.defaults.recordsinMRA001forAutoMCA";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.defaults.maintaincca";
	private static final String SIZE_EXCEED = "mailtracking.mra.billingentries.msg.error.sizeexceeded";
	private static final String MIX_OF_BILLED_BILLABLE = "mailtracking.mra.billingentries.msg.error.mixofbilledandbillabe";
	private static final String ONHOLD = "mailtracking.mra.billingentries.msg.error.onhold";
	private static final String NOT_SAME_DSN_GPACODE_ORG_DEST_CAT = "mailtracking.mra.billingentries.msg.error.manydifferntfields";
	private static final String AUTOMCA_CANNOT_BE_ISSUED = "mailtracking.mra.billingentries.msg.error.notbillableorbilled";
	private static final String BILLINGSTATUS_BILLED="BILLED";
	private static final String BILLINGSTATUS_BILLABLE ="BILLABLE";
	private static final String BILLINGSTATUS_ONHOLD="ON HOLD";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("AutoMCACommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		GPABillingEnquiryModel gpaBillingEnquiryModel = (GPABillingEnquiryModel)actionContext.getScreenModel();
		 GPABillingEntryFilter gpaBillingEntryFilter = (GPABillingEntryFilter)gpaBillingEnquiryModel.getGpaBillingEntryFilter();
		// PageResult<GPABillingEntryDetails> billingDetails = gpaBillingEnquiryModel.getGpaBillingEntryDetails();
		 Collection<GPABillingEntryDetails> selectedBilling = gpaBillingEnquiryModel.getSelectedBillingDetails();
		 Collection<DocumentBillingDetailsVO> billingVos = constructDocumentBillingVOs(selectedBilling);
		 String records = gpaBillingEnquiryModel.getRecords();
		 GPABillingEntriesFilterVO filterVO = new GPABillingEntriesFilterVO();	
		 List<ErrorVO> errors=(List<ErrorVO>)updateFilterVO(gpaBillingEntryFilter, filterVO, logonAttributes);
		 MaintainCCASession maintainCCASession=getScreenSession(MODULE_NAME,SCREENID);//mra072
		 MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();
		 Collection<CCAdetailsVO> ccaDetailsVOs = null;
		 ResponseVO responseVO = new ResponseVO();
		 List<GPABillingEnquiryModel> results = new ArrayList<GPABillingEnquiryModel>();
		 
	        if (errors!=null && !errors.isEmpty()){        	 
	        	actionContext.addAllError(errors);
	        	return; 
	        }
	      errors=validateValues(records,billingVos);
	        if (errors!=null && !errors.isEmpty()){        	
	        	actionContext.addAllError(errors);
	        	return; 
	        }	
	        if(billingVos!=null && !billingVos.isEmpty()){
	        DocumentBillingDetailsVO documentBillingDetailsVO = billingVos.iterator().next();
	        maintainCCAFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	    	maintainCCAFilterVO.setConsignmentDocNum(documentBillingDetailsVO.getCsgDocumentNumber());
	    	if(documentBillingDetailsVO.getCsgSequenceNumber()!=null){
			maintainCCAFilterVO.setConsignmentSeqNum(documentBillingDetailsVO.getCsgSequenceNumber());
	    	}
			maintainCCAFilterVO.setDsnNumber(documentBillingDetailsVO.getDsn());
			maintainCCAFilterVO.setBillingBasis(documentBillingDetailsVO.getBillingBasis());
			maintainCCAFilterVO.setPOACode(documentBillingDetailsVO.getPoaCode());
	        }
	    	try{ 
	    		ccaDetailsVOs =new MailTrackingMRADelegate().findCCAdetails(maintainCCAFilterVO);;
			}catch (BusinessDelegateException businessDelegateException) {
				errors=handleDelegateException(businessDelegateException);

			}
	    	CCAdetailsVO cCAdetailsVO = new CCAdetailsVO();
	    	cCAdetailsVO = updateCCADetailsVO(ccaDetailsVOs);
	    	maintainCCASession.setDocumentBillingDetailsVOs(billingVos);
	    	maintainCCASession.setFromScreen("listbillingentriesux");
	    	maintainCCASession.setGPABillingEntriesFilterVO(filterVO);
	    	maintainCCASession.setCCAdetailsVOs(ccaDetailsVOs);
	    	maintainCCASession.setCCAdetailsVO(cCAdetailsVO);
	    	results.add(gpaBillingEnquiryModel);
			responseVO.setResults(results);
			responseVO.setStatus(STATUS_SUCCESS);
			actionContext.setResponseVO(responseVO);
	}
/**
 * 	Method		:	AutoMCACommand.validateValues
 *	Added by 	:	A-4809 on May 13, 2019
 * 	Used for 	:
 *	Parameters	:	@param records
 *	Parameters	:	@param billingVos
 *	Parameters	:	@return 
 *	Return type	: 	List<ErrorVO>
 */
private CCAdetailsVO updateCCADetailsVO(Collection<CCAdetailsVO> ccaDetailsVOs) {
	CCAdetailsVO cCAdetailsVO = new CCAdetailsVO();
	double revRate = 0;
	Money chgGrossWt=null;
	Money othChgGrsWt=null;
	String billingStatus = "";
	String gpaCode = "";
	String dsn = "";
	String category = "";
	String currency= "";
    int sernum=0;
    double netAmountValue = 0.0;
	Money netAmt = null;
    String revCurrency = "";
    String revGpaCode ="";
	for(CCAdetailsVO ccaVO:ccaDetailsVOs){
		revRate = ccaVO.getRevisedRate();
		currency = ccaVO.getContCurCode();
		revCurrency = ccaVO.getRevContCurCode();
		revGpaCode = ccaVO.getRevGpaCode();
		chgGrossWt=ccaVO.getMailChg();
		othChgGrsWt=ccaVO.getSurChg();
		billingStatus=ccaVO.getBillingStatus();
		sernum=ccaVO.getBlgDtlSeqNum();
		gpaCode=ccaVO.getGpaCode();
		try {
			netAmt = CurrencyHelper.getMoney(ccaVO.getContCurCode());
		    netAmountValue = (ccaVO.getNetAmount().getAmount()); 
		    netAmt.setAmount(netAmountValue);
		} catch (CurrencyException e) {
			log.log(Log.FINE,"Inside CurrencyException.. ");
		}
	}
	cCAdetailsVO.setBillingStatus(billingStatus);
	cCAdetailsVO.setGpaCode(gpaCode);
	cCAdetailsVO.setCategory(category);
	cCAdetailsVO.setDsnNo(dsn);
    cCAdetailsVO.setRate(revRate);
    cCAdetailsVO.setContCurCode(currency);
    cCAdetailsVO.setChgGrossWeight(chgGrossWt);
    cCAdetailsVO.setOtherChgGrossWgt(othChgGrsWt);
    cCAdetailsVO.setRevisedRate(revRate);
    cCAdetailsVO.setRevContCurCode(revCurrency);
    cCAdetailsVO.setRevGpaCode(revGpaCode);
    cCAdetailsVO.setBlgDtlSeqNum(sernum);
    cCAdetailsVO.setNetAmount(netAmt);
	return cCAdetailsVO;
	}
/**
 * 	Method		:	AutoMCACommand.validateValues
 *	Added by 	:	A-4809 on Mar 7, 2019
 * 	Used for 	:
 *	Parameters	:	@param records
 *	Parameters	:	@param billingVos
 *	Parameters	:	@return 
 *	Return type	: 	List<ErrorVO>
 */
	private List<ErrorVO> validateValues(String records, Collection<DocumentBillingDetailsVO> billingVos) {
		ArrayList<ErrorVO> errors = new ArrayList<>();
		String maxMailBagCount = null;
		Boolean isDifferentBillingStatus = false;
		Boolean isDifferentGPACode = false;
		Boolean isDifferentOrigin = false;
		Boolean isDifferentDestination = false;  
		Boolean isDifferentDSN = false;
		Boolean isDifferentCategory = false;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(SYS_PARAM_NO_RECORDS);
		Map<String, String> systemParameters = null;		
		try {
			systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException e) {
			log.log(Log.FINE,"Inside CurrencyException.. ");
		}
		if(systemParameters!=null &&systemParameters.size()>0 ){
			maxMailBagCount=systemParameters.get(SYS_PARAM_NO_RECORDS);
		}
		Object[] obj = { maxMailBagCount };
		try{
		int recordCount = Integer.parseInt(records);
		if (recordCount > Integer.parseInt(maxMailBagCount)) {
			 ErrorVO errorVO = new ErrorVO(SIZE_EXCEED,obj);		
			  errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			  //added by 8331
			  errors.add(errorVO); 
		} 
		}catch(NumberFormatException e){
			log.log(Log.FINE,"Inside NumberFormatException.. ");
		}
		String billingStatus = null;
		String gpaCode = null;
		String origin = null;
		String destination = null;
		String dsn = null;
		String category = null;
	    for (DocumentBillingDetailsVO documentBillingDetailsVO :billingVos) {
    		if(billingStatus == null) {
			    billingStatus = documentBillingDetailsVO.getBillingStatus();
    		}
    		else if(!billingStatus.equals(documentBillingDetailsVO.getBillingStatus())){
				 isDifferentBillingStatus = true;	
			}
    		
    		
			if(gpaCode == null){ 
				gpaCode = documentBillingDetailsVO.getGpaCode();
			}
			else if(!gpaCode.equals(documentBillingDetailsVO.getGpaCode())){
				isDifferentGPACode = true;
			}
			if(origin== null){
				origin = documentBillingDetailsVO.getOrigin();
			}
			else if(!origin.equals(documentBillingDetailsVO.getOrigin())){
				isDifferentOrigin = true;
			}
			if(destination== null){
				destination = documentBillingDetailsVO.getDestination();
			}
			else if(!destination.equals(documentBillingDetailsVO.getDestination())){
				isDifferentDestination = true;
			}
			if(dsn== null){
				dsn = documentBillingDetailsVO.getDsn();
			}
			else if(!dsn.equals(documentBillingDetailsVO.getDsn())){
				isDifferentDSN = true;
			}
			if(category== null){
				category = documentBillingDetailsVO.getCategory();
			}
			else if(!category.equals(documentBillingDetailsVO.getCategory())){
				isDifferentCategory = true;
			}	
			
    	}	
    	if(isDifferentBillingStatus  )
    	{
    		ErrorVO errorVO = new ErrorVO(MIX_OF_BILLED_BILLABLE);
			 errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 errors.add(errorVO); //added by 8331 starts
    	}
    	if(isDifferentGPACode || isDifferentOrigin || isDifferentDestination || isDifferentDSN || isDifferentCategory ){
    		ErrorVO errorVO = new ErrorVO(NOT_SAME_DSN_GPACODE_ORG_DEST_CAT);
			 errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 errors.add(errorVO); 
    	}
    	if(BILLINGSTATUS_ONHOLD.equals(billingStatus)){  
    		 ErrorVO errorVO = new ErrorVO(ONHOLD);
			 errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 errors.add(errorVO); 
    	}	
   	  if(!( BILLINGSTATUS_BILLABLE.equals(billingStatus)|| BILLINGSTATUS_BILLED.equals(billingStatus))){  
   		  ErrorVO errorVO = new ErrorVO(AUTOMCA_CANNOT_BE_ISSUED);
			 errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 errors.add(errorVO); 
   	}	
		return errors;
	}
/**
 * 	Method		:	AutoMCACommand.constructDocumentBillingVOs
 *	Added by 	:	A-4809 on Mar 7, 2019
 * 	Used for 	:
 *	Parameters	:	@param billingDetails
 *	Parameters	:	@return 
 *	Return type	: 	Collection<DocumentBillingDetailsVO>
 */
	private Collection<DocumentBillingDetailsVO> constructDocumentBillingVOs(
			Collection<GPABillingEntryDetails> billingDetails) {
		Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs = new ArrayList<DocumentBillingDetailsVO>() ;
		for(GPABillingEntryDetails dtl : billingDetails){
			DocumentBillingDetailsVO vo = new DocumentBillingDetailsVO();
			vo.setCompanyCode(dtl.getCompanyCode());
			vo.setBillingBasis(dtl.getMailbagID());
			vo.setGpaCode(dtl.getGpaCode());
			vo.setPoaCode(dtl.getGpaCode()); 
			vo.setWeight(dtl.getWeight());
			vo.setCurrency(dtl.getCurrency());
			vo.setApplicableRate(dtl.getApplicableRate());
			vo.setOrgOfficeOfExchange(dtl.getOrgOfficeOfExchange());
 			vo.setDestOfficeOfExchange(dtl.getDestOfficeOfExchange());
			vo.setCategory(dtl.getCategory());
			vo.setSubClass(dtl.getSubClass());
			vo.setDsn(dtl.getDsn());
			vo.setYear(dtl.getYear());
			vo.setRsn(dtl.getRsn());
			vo.setRegInd(dtl.getRegInd());
			vo.setHni(dtl.getHni());
			vo.setBillingStatus(dtl.getBillingStatus());  //added by 8331
			try{
				Money valCharges =  CurrencyHelper.getMoney(vo.getCurrency());
				Money netAmount =  CurrencyHelper.getMoney(vo.getCurrency());
				Money serviceTax =  CurrencyHelper.getMoney(vo.getCurrency());
				Money grossAmount =  CurrencyHelper.getMoney(vo.getCurrency());
				Money mailChg =  CurrencyHelper.getMoney(vo.getCurrency());
				Money surChg =  CurrencyHelper.getMoney(vo.getCurrency());
				valCharges.setAmount(dtl.getValCharges());
				netAmount.setAmount(dtl.getAmount());
				serviceTax.setAmount(dtl.getServiceTax());
				grossAmount.setAmount(dtl.getGrossAmount());
				mailChg.setAmount(dtl.getAmount());
				surChg.setAmount(dtl.getSurChg());
				vo.setValCharges(valCharges);
				vo.setNetAmount(netAmount); 
				vo.setServiceTax(serviceTax);
				vo.setGrossAmount(grossAmount);
				vo.setMailChg(mailChg);
				vo.setSurChg(surChg);
			}catch(CurrencyException currencyException){
				log.log(Log.FINE, currencyException.getErrorCode());
			}
			documentBillingDetailsVOs.add(vo);
		}
		return documentBillingDetailsVOs;
	}
/**
 * 	Method		:	AutoMCACommand.updateFilterVO
 *	Added by 	:	A-4809 on Mar 7, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntryFilter
 *	Parameters	:	@param filterVO
 *	Parameters	:	@param logonAttributes
 *	Parameters	:	@return 
 *	Return type	: 	List<ErrorVO>
 */
	private List<ErrorVO> updateFilterVO(GPABillingEntryFilter gpaBillingEntryFilter,GPABillingEntriesFilterVO filterVO, LogonAttributes logonAttributes) {
		ArrayList<ErrorVO> errors = new ArrayList<>();
		try{
			
			if(gpaBillingEntryFilter.getFromDate()!= null && gpaBillingEntryFilter.getFromDate().trim().length()!=0){
				LocalDate fromDateFormat = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				filterVO.setFromDate(fromDateFormat.setDate(gpaBillingEntryFilter.getFromDate()));
			}
			if(gpaBillingEntryFilter.getToDate()!= null && gpaBillingEntryFilter.getToDate().trim().length()!=0){
				LocalDate toDateFormat = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				filterVO.setToDate(toDateFormat.setDate(gpaBillingEntryFilter.getToDate()));
			}

			filterVO.setCompanyCode(logonAttributes.getCompanyCode());
			filterVO.setConDocNumber(gpaBillingEntryFilter.getConsignmentNumber());
			filterVO.setBillingStatus(gpaBillingEntryFilter.getBillingStatus());
			filterVO.setGpaCode(gpaBillingEntryFilter.getGpaCode());
			filterVO.setOriginOfficeOfExchange(gpaBillingEntryFilter.getOriginOE());
			filterVO.setOrigin(gpaBillingEntryFilter.getOrigin());
			filterVO.setDestinationOfficeOfExchange(gpaBillingEntryFilter.getDestinationOE());
			filterVO.setDestination(gpaBillingEntryFilter.getDestination());
			filterVO.setMailCategoryCode(gpaBillingEntryFilter.getCategory());
			filterVO.setMailSubclass(gpaBillingEntryFilter.getSubClass());
			filterVO.setYear(gpaBillingEntryFilter.getYear());
			filterVO.setDsnNumber(gpaBillingEntryFilter.getDsn());
			filterVO.setRsn(gpaBillingEntryFilter.getRsn());
			filterVO.setHni(gpaBillingEntryFilter.getHni());
			filterVO.setRegInd(gpaBillingEntryFilter.getRi());
			filterVO.setMailbagId(gpaBillingEntryFilter.getMailbag());
			filterVO.setIsUSPSPerformed(gpaBillingEntryFilter.getUspsMailPerformance());
			filterVO.setTotalRecordCount(gpaBillingEntryFilter.getTotalRecords());
			filterVO.setDefaultPageSize(gpaBillingEntryFilter.getDefaultPageSize());
			if(gpaBillingEntryFilter.getRateBasis()!=null && !gpaBillingEntryFilter.getRateBasis().isEmpty()){
				if(MRAConstantsVO.CONTRACT_RATE.equals(gpaBillingEntryFilter.getRateBasis())){
					filterVO.setContractRate(gpaBillingEntryFilter.getRateBasis());
				}else if(MRAConstantsVO.UPU_RATE.equals(gpaBillingEntryFilter.getRateBasis())){
					filterVO.setUPURate(gpaBillingEntryFilter.getRateBasis());
				}
			}
		}catch(Exception e){
			errors.add(new ErrorVO(e.getMessage()));
		}
		return  errors;
	}
}
