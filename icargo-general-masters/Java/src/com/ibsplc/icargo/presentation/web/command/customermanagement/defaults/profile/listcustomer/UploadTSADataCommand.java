/*
 * UploadTSADataCommand.java Created on Jun 22, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.poi.util.RecordFormatException;
import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.lock.LockDelegate;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.UploadTSAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.excel.ExcelReader;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3045
 *
 */
public class UploadTSADataCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CUSTOMERMGMT.DEFAULTS");

	private static final String SAVE_SUCCESS = "save_success";
	
	private static final String UPLOAD_FILETYPE = "shared.customer.uploadfiletype";
	
	private static final String SCREENID = "customermanagement.defaults.customerlisting";
	
	private static final String IAC = "The following are Indirect Air Carriers with a TSA-Approved Security Program";
	private static final String APIACSSP = "Indirect Air Carriers Authorized to Tender CCSF Screened Cargo Under AP-IACSSP-08-001";
	private static final String CCSF = "The following is a list of TSA approved Certified Cargo Screening Facilities";
	private static final String CCSF_ADDS = "The following is a list of TSA approved Certified Cargo Screening Facilities that have been added since last reporting period.";
		
	private static final String IAC_FILE = "IAC Lists";
	private static final String APIACSSP_FILE = "AP-IACSSP List";
	private static final String CCSF_FILE = "TSA:CCSF List";
	private static final String CCSF_ADDS_FILE = "CCSF Adds";
	
	private static final String LOCK_ACTION = "MODIFYCUS";
	private static final String LOCK_REMARKS = "CUSTOMER LOCKING";
	private static final String LOCK_DESCRIPTION = "Customer Modifications";
	
	
	
	/**
	 * This method overrides the execute method of BaseComand class
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.log(Log.FINE, "INSIDE THE UPLOADTSADATACOMMAND");		
		UploadTSAForm uploadTSAForm = (UploadTSAForm) invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();				
		/** Validating form */
		errors = validateForm(uploadTSAForm);
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "INSIDE THE UPLOADDGRCOMMAND VALIDATION FAILED");
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_SUCCESS;
			return;
		}		
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		FormFile selectedFile = uploadTSAForm.getSelectedFile();
		Collection <ErrorVO> errorVO = null;		
		String fileType = uploadTSAForm.getFileType();
		String fileTypeDescription = null;
		Collection<OneTimeVO> fileTypes = oneTimeDescription();
		for(OneTimeVO vo: fileTypes){
			if(fileType.equals(vo.getFieldValue())){
				fileTypeDescription = vo.getFieldDescription();
			}
		}
		String clientFileName = null;
		if(IAC_FILE.equals(fileTypeDescription)){
			clientFileName = IAC;
		}else if(APIACSSP_FILE.equals(fileTypeDescription)){
			clientFileName = APIACSSP;
		}else if(CCSF_FILE.equals(fileTypeDescription)){
			clientFileName = CCSF;
		}else if(CCSF_ADDS_FILE.equals(fileTypeDescription)){
			clientFileName = CCSF_ADDS;
		}	
		errors = setLock();
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "ALREADY LOCKED!!!!!!!!!!*!!!!");
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_SUCCESS;
			return;
		}		
		try {
			log.log(Log.FINE,"INSIDE THE UPLOADDGRCOMMAND BEFORE DELEGATE CALL");
			byte[] tsaData = selectedFile.getFileData();
			//String fileType = uploadTSAForm.getFileType();
			errors = delegate.uploadTSAData(clientFileName, tsaData);
			
		} catch (FileNotFoundException fe) {
//printStackTrrace()();
		} catch (IOException ioe) {
//printStackTrrace()();
		} catch (BusinessDelegateException bde) {
			errors = handleDelegateException(bde);
		}
		if(errors != null && errors.size() > 0){
			/*ErrorVO err = errors.iterator().next();
			err.setErrorDisplayType(ErrorDisplayType.ERROR);*/
			invocationContext.addAllError(errors);
		}else{			
			ErrorVO err = new ErrorVO("customermanagement.defaults.upload.msg.uploadsuccess");
			err.setErrorDisplayType(ErrorDisplayType.STATUS);
			invocationContext.addError(err);	
		}
		invocationContext.target = SAVE_SUCCESS;
		
	}
	
	/**
	 * Method to validate Form
	 * 
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(UploadTSAForm tsaForm) {	
		Collection<ErrorVO> errors = null;
		ErrorVO error = null;
		if (tsaForm.getSelectedFile() == null
				|| tsaForm.getSelectedFile().getFileSize() == 0) {
			log.log(Log.FINE,
					"INSIDE THE UPLOADTSACOMMAND FILE NOT FOUND ERROR");
			error = new ErrorVO("customermanagement.defaults.upload.err.filenotfound");
			errors = new ArrayList<ErrorVO>();
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if (tsaForm.getSelectedFile() != null 
				&& tsaForm.getSelectedFile().getFileSize()> 0) {
			String fileName = tsaForm.getSelectedFile().getFileName();
			if (!fileName.contains("xls")) {
				log.log(Log.FINE,
						"INSIDE THE UPLOADTSACOMMAND WRONG FILE FORMAT");
				error = new ErrorVO("customermanagement.defaults.upload.err.incorrectfileformat");
				errors = new ArrayList<ErrorVO>();
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		if(errors != null && errors.size() > 0){
			return errors;
		}
		if (tsaForm.getSelectedFile() != null 
				&& tsaForm.getSelectedFile().getFileSize()> 0) {								
			String fileType = tsaForm.getFileType();
			String fileTypeDescription = null;
			Collection<OneTimeVO> fileTypes = oneTimeDescription();
			for(OneTimeVO vo: fileTypes){
				if(fileType.equals(vo.getFieldValue())){
					fileTypeDescription = vo.getFieldDescription();
				}
			}
			String clientFileName = null;
			if(IAC_FILE.equals(fileTypeDescription)){
				clientFileName = IAC;
			}else if(APIACSSP_FILE.equals(fileTypeDescription)){
				clientFileName = APIACSSP;
			}else if(CCSF_FILE.equals(fileTypeDescription)){
				clientFileName = CCSF;
			}else if(CCSF_ADDS_FILE.equals(fileTypeDescription)){
				clientFileName = CCSF_ADDS;
			}			
			boolean flag = false;
			ExcelReader excelReader;
			try {
				byte[] tsaData = tsaForm.getSelectedFile().getFileData();
				excelReader = new ExcelReader(new ByteArrayInputStream(tsaData),5);
				Object[][] data = excelReader.getData();	
				for( int i = 0; i < 5; i++ ) {	
					log.log(Log.FINE, "fileType", fileType);
					log.log(Log.FINE, "clientFileName", clientFileName);
					log.log(Log.FINE, "$$$$$$$$$$$data", data, i);
					if((String)data[0][i] != null){
						if(clientFileName.equals(((String)data[0][i]).trim())){
							flag = true;
							break;
						}
					}					
				}
				excelReader.closeFile();
			} catch (FileNotFoundException e1) {
				// To be reviewed Auto-generated catch block
//printStackTrrace()();
			} catch (IOException e1) {
				// To be reviewed Auto-generated catch block
//printStackTrrace()();
			} catch (RecordFormatException e1){
//				 To be reviewed Auto-generated catch block
				//added by a-3045 for bug 63846 in 18Sep09 
				error = new ErrorVO("customermanagement.defaults.upload.err.filterenabledinexcel");
				errors = new ArrayList<ErrorVO>();
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				flag = true;
			}
			if(!flag){
				error = new ErrorVO("customermanagement.defaults.upload.err.incorrectfile",new Object[] { fileTypeDescription });
				errors = new ArrayList<ErrorVO>();
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}			
		}		
		return errors;
	}
	
	/**
	 * Method to populate the collection of onetime parameters to be obtained
	 * 
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ScreenLoadCommand", "getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(UPLOAD_FILETYPE);
		log.exiting("ScreenLoadCommand", "getOneTimeParameterTypes");
		return parameterTypes;
	}
	
	/**
	 * Method to populate the collection of onetime parameters to be obtained
	 * 
	 * @return Collection<OneTimeVO>
	 */
	private Collection<OneTimeVO> oneTimeDescription(){		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;			
		try {
		oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
				logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
		handleDelegateException(e);
		}			
		
			
		return oneTimeValues.get(UPLOAD_FILETYPE);
	}	
	
	/**
	 * Method to set the Lock
	 * 
	 * @return Collection<OneTimeVO>
	 */
	private Collection<ErrorVO> setLock(){	
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		TransactionLockVO txnLockVO = new TransactionLockVO(LOCK_ACTION);
		LockVO vo = null;
		txnLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		txnLockVO.setAction(LOCK_ACTION);
		txnLockVO.setScreenId(SCREENID);
		txnLockVO.setStationCode(logonAttributes.getStationCode());
		txnLockVO.setDescription(LOCK_DESCRIPTION);
		txnLockVO.setRemarks(LOCK_REMARKS);
		LockDelegate lock = new LockDelegate();
		try{
			vo = lock.addLock(txnLockVO);
		}catch(BusinessDelegateException ex){
			errors = handleDelegateException(ex);
		}
		return errors;
	}
}
