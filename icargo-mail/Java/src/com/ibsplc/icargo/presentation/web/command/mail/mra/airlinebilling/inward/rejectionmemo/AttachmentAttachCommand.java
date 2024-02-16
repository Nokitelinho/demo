/*
 * AttachmentScreanloadCommand.java Created on Oct 23, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.rejectionmemo;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.upload.FormFile;




import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.MRASisSupportingDocumentDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-8061.
 *To attach the supporting documents to Rejection memo,charge memo and list outward billing screens
 */
public class AttachmentAttachCommand extends BaseCommand {

	 private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD REJECTION MEMO");
	 private static final String ATTACH_SUCCESS= "attach_success";
	 private static final String ATTACH_FAILURE= "attach_failure";
	 private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	 private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";
	 private static final String INV_NUMBER_03 = "DUMMY03";
	 private static final String CLR_PERIOD = "DUMMY";
	 private static final String ERR_EXCEPTION = "exception";
	 private static final String BLANK = "";
	 private static final String STATUS_BILLED = "D";
	 private static final String INT_BLGTYPE = "O";
	 private static final String FILE_ALREADY_EXISTS = "mra.airlinebilling.rejectionmemo.filealreadyattached";
	 private static final String FIL_TYP="cra.sisbilling.supportingdoc.filetyp";
	 private static final String INV_FILETYPE="mra.airlinebilling.rejectionmemo.invalidfiletype";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		    log.entering("AttachmentAttachCommand", "execute");
		    
		    MRASisSupportingDocumentDetailsForm supportingDocForm =
				(MRASisSupportingDocumentDetailsForm)invocationContext.screenModel;
		    ApplicationSessionImpl applicationSession = getApplicationSession();
		    Collection<ErrorVO> errorVOs = null;
			LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
			 Collection<SisSupportingDocumentDetailsVO> supportingDocVos = null;
			 Collection<SisSupportingDocumentDetailsVO> supportingDocVosToSave = new ArrayList<SisSupportingDocumentDetailsVO>();
		    SisSupportingDocumentDetailsVO supportingDocVO = new SisSupportingDocumentDetailsVO();
			RejectionMemoSession session = (RejectionMemoSession) getScreenSession(
					MODULE_NAME, SCREEN_ID);
			Collection<OneTimeVO> oneTimeVOs=null;
			MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
			String fileName="";
			byte[] fileData =null;
		    
		    if(supportingDocForm.getFileData()!=null){
				FormFile selectedFile = supportingDocForm.getFileData();
				try {
					log.log(Log.FINE, " --inside file try --");
					fileData = selectedFile.getFileData();
					fileName= selectedFile.getFileName(); 
				} catch (FileNotFoundException fe) {	
					log.log(Log.FINE,"inside  file not found");
				} catch (IOException ioe) {
					log.log(Log.FINE,"inside IO exception");
				} 
		    }
		  

			RejectionMemoVO rejectionMemoVO  = session.getRejectionMemoVO();
			Map<String, Collection<OneTimeVO>> hashMap=fetchOneTimeDetails(logonAttributesVO.getCompanyCode());
			 oneTimeVOs=hashMap.get(FIL_TYP);
		    if(rejectionMemoVO!=null){
		    	supportingDocVos=rejectionMemoVO.getSisSupportingDocumentDetailsVOs();
		    	errorVOs = validateFileName(supportingDocVos,fileName,oneTimeVOs); 
		    	
		    	if(errorVOs != null && errorVOs.size() > 0){    
					invocationContext.addAllError(errorVOs);        
					invocationContext.target = ATTACH_FAILURE;   
					return;      
				}  
		    	
							supportingDocVO.setBilledAirline(rejectionMemoVO.getAirlineIdentifier());
						    supportingDocVO.setBillingType(SisSupportingDocumentDetailsVO.BILLING_TYPE_REJECTION_MEMO);
						    if(rejectionMemoVO.getOutwardInvoiceNumber()!=null &&
						    		!BLANK.equals(rejectionMemoVO.getOutwardInvoiceNumber())){
						    	supportingDocVO.setInvoiceNumber(rejectionMemoVO.getOutwardInvoiceNumber());
						    }
						    else{
						    	supportingDocVO.setInvoiceNumber(INV_NUMBER_03);
						    }

						    if(STATUS_BILLED.equals(rejectionMemoVO.getMemoStatus())) {
						    if(rejectionMemoVO.getClearanceperiod()!=null &&
						    		!BLANK.equals(rejectionMemoVO.getClearanceperiod())){
						    	supportingDocVO.setClearancePeriod(rejectionMemoVO.getClearanceperiod());
						    }
						    else{
						    	supportingDocVO.setClearancePeriod(CLR_PERIOD);
						    }
						    } else {
				
						    	if(rejectionMemoVO.getOutwardClearancePeriod()!=null &&
							    		!BLANK.equals(rejectionMemoVO.getOutwardClearancePeriod())){
							    	supportingDocVO.setClearancePeriod(rejectionMemoVO.getOutwardClearancePeriod());
							    }
							    else{
							    	supportingDocVO.setClearancePeriod(CLR_PERIOD);
							    }
						    }

				    supportingDocVO.setCarierDestination(rejectionMemoVO.getDestination());
				    supportingDocVO.setCarierOrigin(rejectionMemoVO.getOrigin());
				    
				   /* if(rejectionMemoVO.getSerialNumber()!=0){
				    	supportingDocVO.setInvoiceSerialNumber(memovo.getOutwardInvoiceSerialNumber());
				    }
				    else{*/
				    	  supportingDocVO.setInvoiceSerialNumber(1);
				   /* }*/
				    /**
				     * Setting the values to SisSupportingDocumentDetailsVO for RM and CM
				     */
				    supportingDocVO.setCompanyCode(logonAttributesVO.getCompanyCode());
				    
				   /* supportingDocVO.setDocOwnerId(awbInMemoVO.getDocumentOwnerIdentifier());
				    supportingDocVO.setDuplicateNumber(awbInMemoVO.getDuplicateNumber());
				    supportingDocVO.setMasterDocumentNumber(awbInMemoVO.getMasterDocumentNumber());
				    supportingDocVO.setSequenceNumber(awbInMemoVO.getSequenceNumber());
				    */
				    
				    supportingDocVO.setLastUpdatedUser(logonAttributesVO.getUserId());
				    LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
				    supportingDocVO.setLastUpdatedTime(date);
				    supportingDocVO.setMemoNumber(rejectionMemoVO.getMemoCode());
				    supportingDocVO.setFileData(fileData);
				    supportingDocVO.setFilename(fileName);
				    supportingDocVO.setBillingMode(rejectionMemoVO.getBillingBasis());
				   /* if(supportingDocVO.getBillingMode() == null){
				    	 supportingDocVO.setBillingMode(memovo.getAwbBillingMode());
				    }*/
				    supportingDocVO.setAttachmentIndicator(SisSupportingDocumentDetailsVO.FLAG_YES);
				    supportingDocVO.setAttachmentStatus(SisSupportingDocumentDetailsVO.FLAG_YES);
				    supportingDocVO.setInterlineBillingType(INT_BLGTYPE);
				    supportingDocVO.setFromAttachment(true);
				    supportingDocVO.setStatus(rejectionMemoVO.getMemoStatus());
				    supportingDocVO.setOperationFlag(SisSupportingDocumentDetailsVO.OPERATION_FLAG_INSERT);
				    
				  //  supportingDocVO.setAwbSerialNumber(awbInMemoVO.getAwbSerialNumber());//added for ICRD-24968

				if(supportingDocVos==null){
		    	supportingDocVos= new ArrayList<SisSupportingDocumentDetailsVO>();
				}
				supportingDocVos.add(supportingDocVO);
				rejectionMemoVO.setSisSupportingDocumentDetailsVOs(supportingDocVos);
				try {
						//if(rejectionMemoVO.getInvoiceNumber()!=null){
						supportingDocVosToSave.add(supportingDocVO);
						supportingDocVosToSave = delegate.saveSupportingDocumentDetails(supportingDocVosToSave);
					//}
				
				} catch(BusinessDelegateException businessDelegateException) {
					log.log(Log.FINE,"inside  businessDelegateException");
					supportingDocVos.removeAll(supportingDocVosToSave); 
					errorVOs = handleDelegateException(businessDelegateException);
			    	invocationContext.addAllError(errorVOs);
			    	invocationContext.target =ATTACH_FAILURE;
			    	log.exiting(MODULE_NAME,ERR_EXCEPTION);
			   	} finally{ 
			   		rejectionMemoVO.setSisSupportingDocumentDetailsVOs(supportingDocVos); 
			   	}
				 }

		    supportingDocForm.setActionStatus("Y");
			invocationContext.target = ATTACH_SUCCESS;
	        log.exiting("AttachmentAttachCommand", "execute");
	

}
	
	private Collection<ErrorVO> validateFileName(Collection<SisSupportingDocumentDetailsVO> documentVOs,String fileName,
			Collection<OneTimeVO> oneTimeVOs){
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();         
		ErrorVO errorVO = null;
		boolean isValidFileType = false;
		int index= fileName.lastIndexOf(".");
		String ext=fileName.substring(index+1,fileName.length());

		for(OneTimeVO oneTimeVO: oneTimeVOs){
			if(ext.equalsIgnoreCase(oneTimeVO.getFieldValue())){
				isValidFileType = true;
				break;
			}
		}
		if(!isValidFileType){
			errorVO = new ErrorVO(INV_FILETYPE);
			errorVOs.add(errorVO);
		}
		if (documentVOs != null) { 
			for (SisSupportingDocumentDetailsVO documetVO : documentVOs) {
				if(fileName.equals(documetVO.getFilename())){
					errorVO = new ErrorVO(FILE_ALREADY_EXISTS);          
					errorVOs.add(errorVO);
					break;
				}
			}
		}
		log.exiting("AttachmentAttachCommand", "validateFiles");	                      	
		return errorVOs; 
		
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
