/*
 * DownloadAttachmentCommand created on October 29, 2018 
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.rejectionmemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.download.FileDownloadCommand;

import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;

import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.RejectionMemoForm;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SupportingDocumentFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;




/**
 * @author A-8061
 *
 */
public class DownloadAttachmentCommand extends FileDownloadCommand{
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD REJECTION MEMO");
	

	private static final String STR_ERROR="getStreamInfoError";
	private static final String DOWNLOAD="getStreamInfo";
	private static final String ACTION_FAILURE="download_failure";
	private static final String ERROR="cra.sisbilling.inward.issuerejectionmemo.streaminfoerror";
	 private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	 private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";
	
	
	 private static final String ERR_EXCEPTION="downloadAttachment exception caught";
	 private static final String FIL_TYP="cra.sisbilling.supportingdoc.filetyp"; 
	 
	
	
	@Override
	protected StreamInfo[] getStreamInfo(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(MODULE_NAME,DOWNLOAD);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		RejectionMemoSession session = (RejectionMemoSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		RejectionMemoForm rejectionMemoForm = (RejectionMemoForm) invocationContext.screenModel;
		RejectionMemoVO rejectionMemoVO  = session.getRejectionMemoVO();
		int row=Integer.parseInt(rejectionMemoForm.getSelectIndex());
		Collection<OneTimeVO> oneTimeVOs=null;
		Map<String, Collection<OneTimeVO>> hashMap=fetchOneTimeDetails(logonAttributes.getCompanyCode());
		 oneTimeVOs=hashMap.get(FIL_TYP);
		Collection<ErrorVO> errorVOs = null;
		ArrayList<SisSupportingDocumentDetailsVO> supportingDocumentDetailsVOs =
			(ArrayList<SisSupportingDocumentDetailsVO>)rejectionMemoVO.getSisSupportingDocumentDetailsVOs();
		
		SisSupportingDocumentDetailsVO supportingDocumentDetailsVO =supportingDocumentDetailsVOs.get(row);
		
		SisSupportingDocumentDetailsVO documentDetailsVO= new SisSupportingDocumentDetailsVO();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		
		SupportingDocumentFilterVO documentFilterVO=new SupportingDocumentFilterVO();

		documentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		documentFilterVO.setBilledAirline(supportingDocumentDetailsVO.getBilledAirline());
		documentFilterVO.setClearancePeriod(supportingDocumentDetailsVO.getClearancePeriod());
		documentFilterVO.setInvoiceNumber(supportingDocumentDetailsVO.getInvoiceNumber());
		documentFilterVO.setInvoiceSerialNumber(supportingDocumentDetailsVO.getInvoiceSerialNumber());
		documentFilterVO.setBillingType(supportingDocumentDetailsVO.getBillingType());
		documentFilterVO.setDocumentSerialNumber(supportingDocumentDetailsVO.getDocumentSerialNumber());
		documentFilterVO.setFileName(supportingDocumentDetailsVO.getFilename());
		try {
			documentDetailsVO = delegate.downloadAttachment(documentFilterVO);
		
		} catch(BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"inside  businessDelegateException");
			errorVOs = handleDelegateException(businessDelegateException);
	    	invocationContext.addAllError(errorVOs);
	    	invocationContext.target =ACTION_FAILURE;
	    	log.exiting(MODULE_NAME,ERR_EXCEPTION);
	   	}
		
		String contentType =null;
		String fileName=documentDetailsVO.getFilename();
		int index= fileName.lastIndexOf(".");
		String ext=fileName.substring(index+1,fileName.length());  

		for(OneTimeVO oneTimeVO: oneTimeVOs){
			if(ext.equals (oneTimeVO.getFieldValue())){
				contentType=oneTimeVO.getFieldDescription();
				break;
			}
		}
		
		if(contentType==null){
			contentType = "application/octet-stream";
		}
		
		
		byte[] fileData=documentDetailsVO.getFileData();
		FileDownloadCommand.StreamInfo[] streamInfo   = new FileDownloadCommand.StreamInfo[1];
		streamInfo[0]=new FileStreamInfo(contentType,fileData,"attachment",fileName);
		log.exiting(MODULE_NAME, DOWNLOAD);
		return streamInfo;
		
	
	}
	
	@Override
	protected void getStreamInfoError(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(MODULE_NAME, STR_ERROR);
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();           
		ErrorVO errorVO = null;     
		errorVO = new ErrorVO(ERROR);            
		errorVOs.add(errorVO);    
		invocationContext.addAllError(errorVOs);   
		invocationContext.target = ACTION_FAILURE;
		log.exiting(MODULE_NAME, STR_ERROR);
		
	}
	
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(
			String companyCode) {
		log.entering("AttachmentAttachCommand", "fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(FIL_TYP);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		log.exiting("AttachmentAttachCommand", "fetchOneTimeDetails");
		return hashMap;
	}
	
	
}
