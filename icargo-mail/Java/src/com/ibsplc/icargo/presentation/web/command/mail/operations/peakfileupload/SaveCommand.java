/*
 * SaveCommand.java Created on July 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.peakfileupload;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.business.mail.operations.vo.PeakMessageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PeakFileUploadForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2001
 */
public class SaveCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILTRACKING");

   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "save_success";
   private static final String TARGET_FAILURE = "save_failure";

   private static final String OPERATION_DEPARTURE = "D";
   private static final String OPERATION_ARRIVAL = "A";
   private static final String POU = "POU";
   private static final String POL = "POL";
   private static final String EMPTY = "";
   private static final char OPERATION_DEPARTURE_CHECKCHAR = 'D';
   private static final char OPERATION_ARRIVAL_CHECKCHAR = 'A';
   private static final int INDEX_3 = 3;

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("SaveCommand","execute");
    	PeakFileUploadForm peakFileUploadForm =
    		(PeakFileUploadForm)invocationContext.screenModel;
    	Collection<ErrorVO> errors = validateForm(peakFileUploadForm);
    	if(errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
    		invocationContext.target=TARGET_FAILURE;
    		return;
    	}
    	PeakMessageVO peakMessageVO = getPeakMessageVO(peakFileUploadForm);
    	try {
			new MailTrackingDefaultsDelegate().receivePeakMessage(peakMessageVO);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		if(errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target=TARGET_FAILURE;
			return;
    	}
		ErrorVO error = new ErrorVO(
			"mailtracking.defaults.peakfileupload.savedsuccessfully");
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
		clearForm(peakFileUploadForm);
    	peakFileUploadForm.setScreenStatusFlag(
    			ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET_SUCCESS;
    	log.exiting("ScreenloadCommand","execute");

    }

    /**
     *
     * @param peakFileUploadForm
     */
	private void clearForm(PeakFileUploadForm peakFileUploadForm) {
		log.entering("SaveCommand","clearForm");
		peakFileUploadForm.setOperation(EMPTY);
		peakFileUploadForm.setPou(EMPTY);
		peakFileUploadForm.setPol(EMPTY);
		log.exiting("SaveCommand","clearForm");
	}

	/**
     *
     * @param peakFileUploadForm
     * @return
     */
	private Collection<ErrorVO> validateForm(
			PeakFileUploadForm peakFileUploadForm) {
		log.entering("SaveCommand","validateForm");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(OPERATION_DEPARTURE.equals(peakFileUploadForm.getOperation())) {
			errors.addAll(validateAirport(peakFileUploadForm.getPou(), POU));
			errors.addAll(validateUploadFile(
					peakFileUploadForm.getUploadFile(),
					OPERATION_DEPARTURE_CHECKCHAR));
		}
		else if(OPERATION_ARRIVAL.equals(peakFileUploadForm.getOperation())) {
			errors.addAll(validateAirport(peakFileUploadForm.getPol(), POL));
			errors.addAll(validateUploadFile(
					peakFileUploadForm.getUploadFile(),
					OPERATION_ARRIVAL_CHECKCHAR));
		}
		log.exiting("ScreenloadCommand","validateForm");
		return errors;
	}

	/**
	 *
	 * @param uploadFile
	 * @param checkCharacter
	 * @return
	 */
	private Collection<ErrorVO> validateUploadFile(
			FormFile uploadFile, char checkCharacter) {
		log.entering("SaveCommand","validateUploadFile");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (uploadFile.getFileName() == null ||
				uploadFile.getFileName().trim().length() == 0) {
			log.log(Log.FINE,"ERROR OCCURED");
			error = new ErrorVO(
					"mailtracking.defaults.peakfileupload.selectfile");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else if (uploadFile.getFileSize() == 0) {
			log.log(Log.FINE,"ERROR OCCURED");
			error = new ErrorVO(
					"mailtracking.defaults.peakfileupload.invalidfile");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else {
			char opChar = uploadFile.getFileName().charAt(INDEX_3);
			log.log(Log.FINEST, "req char ", checkCharacter);
			log.log(Log.FINEST, "char in file ", opChar);
			if(opChar != checkCharacter) {
				error = new ErrorVO(
					"mailtracking.defaults.peakfileupload.msg.err.invaliduploaddetails");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		log.exiting("ScreenloadCommand","validateUploadFile");
		return errors;
	}

	/**
	 *
	 * @param airport
	 * @param displayString
	 * @return
	 */
	private Collection<ErrorVO> validateAirport(String airport,
			String displayString) {
		log.entering("SaveCommand","validateAirport");
		String companyCode =
				getApplicationSession().getLogonVO().getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(airport == null || airport.trim().length() == 0) {
			/*error = new ErrorVO(
				"mailtracking.defaults.peakfileupload.msg.err.mandatory",
				new Object[]{displayString});
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);*/
		}
		else {
			try {
				new AreaDelegate().validateAirportCode(
						companyCode, airport.toUpperCase());
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
			if(errors != null && errors.size() > 0) {
				errors = new ArrayList<ErrorVO>();
				error = new ErrorVO(
						"mailtracking.defaults.peakfileupload.msg.err.invalidairport",
						new Object[]{displayString});
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		log.exiting("ScreenloadCommand","validateAirport");
		return errors;
	}

	/**
     *
     * @param peakFileUploadForm
     * @return
     */
    private PeakMessageVO getPeakMessageVO(PeakFileUploadForm peakFileUploadForm) {
    	log.entering("ScreenloadCommand","getPeakMessageVO");
    	PeakMessageVO peakMessageVO = new PeakMessageVO();
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	LocalDate currenDate = new LocalDate(
    			LocalDate.NO_STATION,Location.NONE,false);
    	peakMessageVO.setCompanyCode(logonAttributes.getCompanyCode());
    	peakMessageVO.setStationCode(logonAttributes.getStationCode());
    	peakMessageVO.setUploadUser(logonAttributes.getUserId());
    	peakMessageVO.setReceiptOrSentDate(currenDate);
    	peakMessageVO.setOperationMode(peakFileUploadForm.getOperation());
    	peakMessageVO.setPol(peakFileUploadForm.getPol().toUpperCase());
    	peakMessageVO.setPou(peakFileUploadForm.getPou().toUpperCase());
    	uploadFile(peakFileUploadForm.getUploadFile(),peakMessageVO);
    	log.log(Log.FINE, "peakMessageVO  is--> ", peakMessageVO);
		log.exiting("ScreenloadCommand","getPeakMessageVO");
		return peakMessageVO;
	}

    /**
     *
     * @param uploadFile
     * @param peakMessageVO
     * @return
     */
	private PeakMessageVO uploadFile(FormFile uploadFile,
			PeakMessageVO peakMessageVO) {
		String uploadFileName = 
			uploadFile.getFileName().substring(0, 
					uploadFile.getFileName().indexOf(".")); 
    	log.log(Log.FINE, "FormFile  is--> ", uploadFile);
		log.log(Log.FINE, "File name is--> ", uploadFileName);
		log.log(Log.FINE, "File size is--> ", uploadFile.getFileSize());
		peakMessageVO.setFileName(uploadFileName);
		String stringData = "";
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] test = new byte[8192];
			InputStream is = uploadFile.getInputStream();
			int totalByteSize = 0;
			while((totalByteSize=is.read(test,0,8192))!= -1){
				baos.write(test,0,totalByteSize);
			}
			stringData = new String(baos.toByteArray());
			peakMessageVO.setOriginalMessage(stringData);
			peakMessageVO.setRawMessage(stringData);
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		return peakMessageVO;
	}

}
