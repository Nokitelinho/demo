/*
 * UpdateTableCommand.java Created on Nov 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturerejectionmemo;


import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureRejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Ruby Abraham
 * Command class to update table data
 *
 * Revision History
 *
 * Version      Date             Author          		 Description
 *
 *  0.1        Nov 22, 2007    Ruby Abraham     		Initial draft
 */
public class UpdateTableCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	
	private static final String CLASS_NAME = "UpdateTableCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";
	
	//private static final String UPDATE_SUCCESS = "update_success";
	//private static final String UPDATE_FAILURE = "update_failure";
		
	/**
	 * Execute method
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureRejectionMemoSession  captureRejectionMemoSession = 
			(CaptureRejectionMemoSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		CaptureRejectionMemoForm captureRejectionMemoForm=
			(CaptureRejectionMemoForm)invocationContext.screenModel;
		
				
		Collection<ErrorVO> errors = null;
		
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		
		
		updateSession(captureRejectionMemoForm,captureRejectionMemoSession,companyCode);
		
		//	For checking whether free rows or columns exists in the table
		errors =validateTable(captureRejectionMemoForm,captureRejectionMemoSession);
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
			//invocationContext.target=UPDATE_FAILURE;
			//return;
		}
		
		//	For checking whether duplicate invoice no and duplicate clearance period exists in the table
		errors =checkDuplicate(captureRejectionMemoForm,captureRejectionMemoSession);
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
		}
		
		//	For validating the  clearance period exists in the table
		errors = validateClearancePeriods(captureRejectionMemoForm,captureRejectionMemoSession,companyCode);
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
		}
		
		
	}
		
	//	Method for updating the session
	/**
	 * @param captureRejectionMemoForm
	 * @param captureRejectionMemoSession
	 * @param companyCode
	 */
	public void updateSession(CaptureRejectionMemoForm captureRejectionMemoForm,CaptureRejectionMemoSession  captureRejectionMemoSession,String companyCode){
		log.entering("updatesession","execute");
		int index = 0;
		
		ArrayList<MemoInInvoiceVO> updatedMemoInInvoiceVOs 	= new ArrayList<MemoInInvoiceVO>();
		
		
		String[] rejectionMemoNo = captureRejectionMemoForm.getRejectionMemoNo();
		
		String[] invoiceNo  =  captureRejectionMemoForm.getInvoiceNos();
		
		String[] clearancePeriod = captureRejectionMemoForm.getClearancePeriods();
		
		String[] rejectionDate  	= captureRejectionMemoForm.getRejectionDate();
				
		double[] provisionalAmt	= captureRejectionMemoForm.getProvisionalAmount();
		
		double[] reportedAmt	= captureRejectionMemoForm.getReportedAmount();
		
		double[] rejectedAmt= captureRejectionMemoForm.getRejectedAmount();
		
		double[] previousDifferenceAmount = captureRejectionMemoForm.getPreviousDifferenceAmount();
		
		String[] operationalFlag = captureRejectionMemoForm.getOperationalFlag();
		
		String[] remarks = captureRejectionMemoForm.getRemarks();
	
		if(captureRejectionMemoSession.getMemoInInvoiceVOs() != null && 
				captureRejectionMemoSession.getMemoInInvoiceVOs().size() >0){
			
			MemoInInvoiceVO memoInInvoiceVO = null;
			int length = captureRejectionMemoSession.getMemoInInvoiceVOs().size();
			
			//for(MemoInInvoiceVO memoInInvoiceVO:captureRejectionMemoSession.getMemoInInvoiceVOs()){
			
			for(int i=0;i<length;i++){
				memoInInvoiceVO= new  MemoInInvoiceVO();
				
				memoInInvoiceVO.setCompanyCode(companyCode);
				
				if(!("").equals(rejectionMemoNo[index])){
					memoInInvoiceVO.setMemoCode(rejectionMemoNo[index]);
				}else{
					memoInInvoiceVO.setMemoCode("");
				}
				
				if(!("").equals(invoiceNo[index])){
					memoInInvoiceVO.setInvoiceNumber(invoiceNo[index]);
				}else{
					memoInInvoiceVO.setInvoiceNumber("");
				}
				
				
				if(!("").equals(clearancePeriod[index])){
					memoInInvoiceVO.setClearancePeriod(clearancePeriod[index]);
				}else{
					memoInInvoiceVO.setClearancePeriod("");
				}
				
	
				if(!("").equals(rejectionDate[index])){
					memoInInvoiceVO.setMemoDate(new LocalDate(NO_STATION,NONE,false).
							setDate(rejectionDate[index]));
				}else {
					memoInInvoiceVO.setMemoDate(null);
				}
								
				if(0.0 != provisionalAmt[index] ){
					memoInInvoiceVO.setProvisionalAmount(provisionalAmt[index]);
				}
				else {
					memoInInvoiceVO.setProvisionalAmount(0.0);
				}
				if(0.0 != reportedAmt[index] ){
					memoInInvoiceVO.setReportedAmount(reportedAmt[index]);
				}
				else {
					memoInInvoiceVO.setReportedAmount(0.0);
				}
				if(0.0 != rejectedAmt[index] ){
					memoInInvoiceVO.setDifferenceAmount(rejectedAmt[index]);
				}
				else {
					memoInInvoiceVO.setDifferenceAmount(0.0);
				}
				
				if(0.0 != previousDifferenceAmount[index] ){
					memoInInvoiceVO.setPreviousDifferenceAmount(previousDifferenceAmount[index]);
				}
				else {
					memoInInvoiceVO.setPreviousDifferenceAmount(0.0);
				}
				
				if(!("").equals(operationalFlag[index])){
					log.log(Log.FINE, "OperationFlag", operationalFlag, index);
					memoInInvoiceVO.setOperationalFlag(operationalFlag[index]);
				}
				else {
					memoInInvoiceVO.setOperationalFlag("");
				}
				if(!("").equals(remarks[index])){
					log.log(Log.FINE, "Remarks", remarks, index);
					memoInInvoiceVO.setRemarks(remarks[index]);
				}
				else {
					memoInInvoiceVO.setRemarks("");
				}
							
				updatedMemoInInvoiceVOs.add(memoInInvoiceVO );
				index++;				
			}
			}
			log.log(Log.FINE, "UPDATED MEMOININVOICEVOS",
					updatedMemoInInvoiceVOs);
			captureRejectionMemoSession.setMemoInInvoiceVOs(updatedMemoInInvoiceVOs);		
		log.exiting("updatesession","execute");
	}
	
	
	//For checking whether free rows or columns exists in the table
	/**
	 * @return Collection<ErrorVO>
	 * @param captureRejectionMemoForm
	 * @param captureRejectionMemoSession
	 */
	public Collection<ErrorVO> validateTable(CaptureRejectionMemoForm captureRejectionMemoForm,
							CaptureRejectionMemoSession  captureRejectionMemoSession){
		log.entering("validateTable","execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		
		
		String[] invoiceNo = captureRejectionMemoForm.getInvoiceNos();
		
		
		String[] clearancePeriod = captureRejectionMemoForm.getClearancePeriods();
		
		double[] differenceAmt  = captureRejectionMemoForm.getRejectedAmount();		
		
				
		String[] operationFlag = captureRejectionMemoForm.getOperationalFlag();
		
		
		
		
		
		if(differenceAmt!=null ) {			
			
			for(int i=0;i<differenceAmt.length;i++) {
				if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])){
					if(("").equals(invoiceNo[i])){
						ErrorVO errorVO = new ErrorVO(
						"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.noinvoiceno");
						errorVO.setErrorDisplayType(ERROR);
						errors.add(errorVO);
						break;
					}
				}
			}
			
			
			for(int i=0;i<differenceAmt.length;i++) {
				if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])){
					if(("").equals(clearancePeriod[i])){
						ErrorVO errorVO = new ErrorVO(
						"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.noclrprd");
						errorVO.setErrorDisplayType(ERROR);
						errors.add(errorVO);
						break;
					}
				}
			}
			
			
			
			for(int i=0;i<differenceAmt.length;i++) {
				if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])){
					if(differenceAmt[i] == 0){
						ErrorVO errorVO = new ErrorVO(
						"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.freedifferenceamtexists");
						errorVO.setErrorDisplayType(ERROR);
						errors.add(errorVO);
						break;
					}
				}
			}			 
		}
				
			
							
		log.exiting("validateTable","execute");
		return errors;
		
	}
	
	
//	For checking whether duplicate records exist
	/**
	 * @return Collection<ErrorVO>
	 * @param captureRejectionMemoForm
	 * @param captureRejectionMemoSession
	 */
	public Collection<ErrorVO> checkDuplicate(CaptureRejectionMemoForm captureRejectionMemoForm,CaptureRejectionMemoSession  captureRejectionMemoSession){
		log.entering("checkDuplicate","execute");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(captureRejectionMemoSession.getMemoInInvoiceVOs()!= null){
				
			String[] invoiceNumber 	= captureRejectionMemoForm.getInvoiceNos();
			
			String[] clearancePeriod = captureRejectionMemoForm.getClearancePeriods();			
			
			String[] operationFlag = captureRejectionMemoForm.getOperationalFlag();
			
			if(invoiceNumber!=null) {
				
				int invoiceClrPrdFlag = 0;
				
			    int clrPrdFlag = 0;		
				
				
				
				for(int i=0;i<invoiceNumber.length;i++) {
					for(int j=0;j<invoiceNumber.length;j++) {
						log.log(Log.INFO, "Going to compare First invoice No",
								invoiceNumber, i);
						log.log(Log.INFO, " and Second Invoice No ",
								invoiceNumber, j);
						log.log(Log.FINE,
								"Going to Compare First Clearance Period",
								clearancePeriod, i);
						log.log(Log.INFO, " and Second Clearance Period ",
								clearancePeriod, j);
						if(i!=j) {
							if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i]) &&
									!(OPERATION_FLAG_DELETE).equals(operationFlag[j])) {
								if(invoiceNumber[i].equals(invoiceNumber[j])) {
									if(clearancePeriod[i].equals(clearancePeriod[j])) {
										invoiceClrPrdFlag = 1;
									}
								}
							}
						}
					}
				}
				
				
				
				/*for(int i=0;i<clearancePeriod.length;i++) {
					for(int j=0;j<clearancePeriod.length;j++) {
						log.log(Log.INFO,"Going to compare "+clearancePeriod[i]);
						log.log(Log.INFO," and "+clearancePeriod[j]);
						if(i!=j) {
							if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i]) &&
									!(OPERATION_FLAG_DELETE).equals(operationFlag[j])) {
								if(clearancePeriod[i].equals(clearancePeriod[j])) {
									clrPrdFlag = 1;
								}
							}
						}
					}
				}*/
				
				
				if(invoiceClrPrdFlag ==1) {
					log.log(Log.INFO,
							"Dup invoiceNumber and Clearance Period >>>>>>",
							invoiceClrPrdFlag);
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.dupinvoicenumberclrprdexists");
					errorVO.setErrorDisplayType(ERROR);					
					errors.add(errorVO);
				}
				
				if(clrPrdFlag ==1) {
					log
							.log(Log.INFO, "dup Clearance Period >>>>>>",
									clrPrdFlag);
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.dupclearanceperiodexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
				}
			}	
		}
		log.exiting("checkDuplicate","execute");
		return errors;
	}
	
	
//	For validating the clearnce period exist
	/**
	 * @return Collection<ErrorVO>
	 * @param captureRejectionMemoForm
	 * @param captureRejectionMemoSession
	 * @param companyCode
	 */
	public Collection<ErrorVO> validateClearancePeriods(CaptureRejectionMemoForm captureRejectionMemoForm,
			CaptureRejectionMemoSession  captureRejectionMemoSession,String companyCode){
		
		log.entering("validateClearancePeriods","execute");
		String[] clearancePeriod = captureRejectionMemoForm.getClearancePeriods();	
		
		String[] operationFlag = captureRejectionMemoForm.getOperationalFlag();
	
		UPUCalendarVO upuCalendarVO = new UPUCalendarVO();
		
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();	
		
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	
		 
	if(clearancePeriod != null){		
		try{
			for(int i=0;i<clearancePeriod.length;i++) {
				if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i])){
					if(!("").equals(clearancePeriod[i])){
						log.log(Log.FINE, "Clearance Period", clearancePeriod,
								i);
							upuCalendarVO =  mailTrackingMRADelegate.validateIataClearancePeriod(companyCode,
									 													clearancePeriod[i]);
								
					}
				}
			}
		 }catch(BusinessDelegateException businessDelegateException){
		    	log.log(Log.FINE,"inside updateAirlinecaught busDelegateExc");
		    	businessDelegateException.getMessage();
		    	errors=handleDelegateException(businessDelegateException);
		 }	
		
		if(errors!= null && errors.size() >0){
			log.log(Log.FINE, "Errors Size", errors.size());
		}		
		else{
			log.log(Log.FINE, "UPUCalendarVO", upuCalendarVO);
		}
	}
	log.exiting("validateClearancePeriods","execute");
	return errors;
	 	 
	 
}
	
	
		

}
