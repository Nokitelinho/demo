/*
 * DeleteCommand.java Created on Nov 22, 2007
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
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
//import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureRejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class to delete a row in Capture Rejection Memo 
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1     Nov 22, 2007   Ruby Abraham		Initial draft
 */
public class DeleteCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	
	private static final String CLASS_NAME = "DeleteCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";
	
	private static final String DELETE_SUCCESS = "delete_success";
	
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
		
		String[] selectedRows=captureRejectionMemoForm.getSelect();
		
		log.log(Log.FINE, "selectedrows size", selectedRows.length);
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
				
		
		updateSession(captureRejectionMemoForm,captureRejectionMemoSession,companyCode);
		
		ArrayList<MemoInInvoiceVO> memoInInvoiceVOs = null;
		memoInInvoiceVOs =	captureRejectionMemoSession.getMemoInInvoiceVOs();
		
		
		int index = 0;
		if(memoInInvoiceVOs!=null && memoInInvoiceVOs.size() >0){	
			for(int i = selectedRows.length - 1; i >= 0; i--) {
				String str = selectedRows[i];			
				index = Integer.parseInt(str);
				log.log(Log.INFO, "\n \n \n The index-->", index);
				MemoInInvoiceVO  memoInInvoiceVO = ((ArrayList<MemoInInvoiceVO>)memoInInvoiceVOs).get(index);
							if((OPERATION_FLAG_INSERT).equals(memoInInvoiceVO.getOperationalFlag())){							
								memoInInvoiceVOs.remove(memoInInvoiceVO);
							}
							else{
								memoInInvoiceVO.setOperationalFlag(OPERATION_FLAG_DELETE);
							}
					//log.log(Log.FINE,"Index"+index);
			}
		}
		captureRejectionMemoSession.setMemoInInvoiceVOs(memoInInvoiceVOs);
		log.log(Log.FINE, "The MemoInInvoiceVOs", captureRejectionMemoSession.getMemoInInvoiceVOs());
		invocationContext.target = DELETE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
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
}
