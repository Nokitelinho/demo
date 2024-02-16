/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.attachfile.UploadExcelCommand.java
 *
 *	Created by	:	A-5219
 *	Created on	:	NOV 08, 2021
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.attachfile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadVO;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.FilterFromAdvpay;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.AttachFileModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.excel.ExcelReader;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class UploadExcelCommand extends AbstractCommand {

	private static final Log LOG = LogFactory.getLogger("MRA DEFAUTS");
	
	private static final String CLASS_NAME = "UploadExcelCommand";
	
	private static final String MAX_UPLOAD_FILE_SIZE = "system.defaults.maxuploadfilesize";
	
	private static final String EMPTY_STRING = "";
	
	private static final String TRANSFER_COMPLETED =  "TC";
	
	private static final String CSV_DELIMITER = ",";
	
	private static final String LINESEP = "\r\n";
	
	private static final String MRABTHSTL = "MRABTHSTL";
	
	
	private static final String UNAPPLIED_SETTLEMENT =  "MRA_US";
	private static final String SUBSYTEM_MRA = "M";
	
	private static final String SUCCESS_MESSAGE = "mail.mra.defaults.attachfile.exceluploadinfo";
	
	private static final String FILE_UPLOAD_MAP= "fileUploadMap";
	private static final String UPLOAD_FILE_KEY = "UPLOAD_FILE_KEY_undefined";
	private static final String ZERO = "0";
	private static final String XLS = "XLS";
	private static final String SELECT_FILE="mail.mra.defaults.attachfile.selectfile";
	private static final String MAX_FILE_LIMIT_REACHED="mail.mra.defaults.attachfile.filesizelimit";
	private static final String SUCCESS="SUCCESS";
	private static final String FILE_ALREADY_PROCESSED="mail.mra.defaults.attachfile.alreadyprocessed";
	private static final String DISABLE_FILE_LOCK="shared.defaults.diablelockonfileprocess";
	private static final String IN_PROGRESS="MRA Batch detail processing in progress on ";
	
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 *	Added by 			: A-5219 on 09-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param actionContext
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws CommandInvocationException
	 */
	@Override
	public void execute(ActionContext actionContext)
			throws BusinessDelegateException, CommandInvocationException {
		LOG.entering(CLASS_NAME, "execute");
		AttachFileModel attachFileModel = (AttachFileModel)actionContext.getScreenModel();
		ScreenSession screenSession = getScreenSession();
		HashMap<String,UploadFileModel> fileUploadMap = screenSession
				.getFromScreenSessionMap(FILE_UPLOAD_MAP);
		UploadFileModel uploadFileModel = null;
		int fileSize = 0;
		int fileCount = 0;
		InputStream testInputStream = null;
		String fileName = null;
		if(fileUploadMap != null && fileUploadMap.get(UPLOAD_FILE_KEY)!= null){
			uploadFileModel = fileUploadMap.get(UPLOAD_FILE_KEY);
			fileSize = uploadFileModel.getFileSize();


			byte[] testbytearray = uploadFileModel.getData();
			testInputStream = new ByteArrayInputStream(testbytearray);
			fileName = uploadFileModel.getFileName();
		}
		LogonAttributes logonAttributes = getLogonAttribute();
		String companyCode = logonAttributes.getCompanyCode();
		String user = logonAttributes.getUserName();
		Map<String, String> systemParameters = getFileSizeFromSystemParameter();
		boolean isValidFileType = false;
		String paramValue = null != systemParameters ? systemParameters
				.get(MAX_UPLOAD_FILE_SIZE) : ZERO;
		int maxupoadFileSize = 0;
		if (paramValue != null && !paramValue.isEmpty()) {
			maxupoadFileSize = Integer.parseInt(paramValue);
		}
		
		if(fileName != null && fileName.trim().length() > 0){
			String[] type = fileName.split("\\.");
			if(type != null && type.length >0 &&
					type[type.length-1] != null && type[type.length-1].toUpperCase().contains(XLS)){
					isValidFileType = true;
			}
		}
		List<ErrorVO> errors = new ArrayList<>();
		ErrorVO error = null;
		if (fileName == null || fileName.trim().length() == 0 || fileSize == 0 || !isValidFileType) {
			error = new ErrorVO(SELECT_FILE);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		} else if ((maxupoadFileSize > 0) && (fileSize > maxupoadFileSize)) {
			Object[] obj = { maxupoadFileSize };
			error = new ErrorVO(MAX_FILE_LIMIT_REACHED,
					obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if (errors != null && !errors.isEmpty()) {
			List<UploadFileModel> results = new ArrayList<>();
			results.add(uploadFileModel);
			
			ResponseVO responseVO = new ResponseVO();
			 responseVO.setStatus(SUCCESS);
			 responseVO.setResults(results);
			 actionContext.addAllError(errors);
			 actionContext.setResponseVO(responseVO);
			return;
		}
		Collection<FileUploadVO> fileUploadVOs = null;
		SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
		try {
			fileUploadVOs = defaultsDelegate.findFileTypes(logonAttributes
					.getCompanyCode());
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		int maxColCount = 0;
		if (fileUploadVOs != null && !fileUploadVOs.isEmpty()) {
			for (FileUploadVO fileUploadVO : fileUploadVOs) {
				if (MRABTHSTL.equals(fileUploadVO.getFileType())) {
					maxColCount = fileUploadVO.getMaxColumnCount();
					break;
				}
			}
		}

		String fileType = MRABTHSTL;
		LocalDate uploadTime = new LocalDate(logonAttributes.getStationCode(),
				Location.STN, true);
		String dateFormat = uploadTime.toDisplayDateOnlyFormat().replaceAll(
				"-", EMPTY_STRING);
		String timeFormat = uploadTime.toDisplayTimeOnlyFormat().replaceAll(
				":", EMPTY_STRING);
		String processIdr = new StringBuffer().append(fileType).append("_")
				.append(user).append("_").append(dateFormat).append("_")
				.append(timeFormat).toString();
		FileUploadVO uploadVO = new FileUploadVO();
		uploadVO.setCompanyCode(companyCode);
		uploadVO.setProcessIdentifier(processIdr);
		uploadVO.setFileName(fileName);
		uploadVO.setUploadTime(uploadTime);
		uploadVO.setFileType(fileType);
		uploadVO.setUser(user);
		uploadVO.setStatusFieldValue(TRANSFER_COMPLETED);
		LOG.entering(CLASS_NAME, "Started Converting to CSV Data");
		
		PaymentBatchFilterVO batchFilterVO = populatefilterVO(
				attachFileModel.getFilterFromAdvpay(),
				logonAttributes);
		batchFilterVO.setFileName(fileName);
		batchFilterVO.setProcessIdentifier(processIdr);		
		MailTrackingMRADelegate mraDelegate = new MailTrackingMRADelegate();
		try{
			fileCount = mraDelegate.findGPASettlementBatchUploadFileCount(batchFilterVO);
			if(fileCount > 0){
				error = new ErrorVO(FILE_ALREADY_PROCESSED);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				actionContext.addAllError(errors);
				return;
			}
		}catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			actionContext.addAllError(errors);
			return;
		}
		
		String csvData = null;
		try {
			csvData = excelToCSVConvertor(testInputStream, maxColCount);
			if (csvData != null) {
				uploadVO.setRawData(csvData);
			}
		} catch (IOException e) {
			LOG.log(Log.SEVERE, e);
		} 
		LOG.entering(CLASS_NAME, "Finished Converting to CSV Data");
		String uploadStatus = null;
		try {
			uploadStatus = defaultsDelegate.uploadFile(uploadVO);

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			actionContext.addAllError(errors);

		}
		LOG.log(Log.INFO, "uploadStatus is :"+uploadStatus);
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType(UNAPPLIED_SETTLEMENT);
		invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.INITIATED);
		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		invoiceTransactionLogVO.setTransactionDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
		invoiceTransactionLogVO.setTransactionTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		invoiceTransactionLogVO.setTransactionTimeUTC(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		invoiceTransactionLogVO.setSubSystem(SUBSYTEM_MRA);
		StringBuilder remarks = new StringBuilder(IN_PROGRESS);
		remarks.append(invoiceTransactionLogVO.getTransactionDate().toDisplayDateOnlyFormat());
		remarks.append(" at ");
		remarks.append(invoiceTransactionLogVO.getTransactionTime().toDisplayTimeOnlyFormat());
		remarks.append(" for file: ");
		remarks.append(fileName);
		invoiceTransactionLogVO.setRemarks(remarks.toString());
		
		if(errors!=null && !errors.isEmpty()){
			actionContext.addAllError(errors);
			return;
		}
		try {
			invoiceTransactionLogVO = mraDelegate
					.initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);

			mraDelegate.uploadPaymentBatchDetail(batchFilterVO,invoiceTransactionLogVO);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			actionContext.addAllError(errors);
			return;
		}

		ErrorVO errorInfo = new ErrorVO(SUCCESS_MESSAGE);   
		errorInfo.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(errorInfo);
        LOG.exiting(CLASS_NAME, "execute");
	}

	
	/**
	 * 
	 * 	Method		:	UploadExcelCommand.getFileSizeFromSystemParameter
	 *	Added by 	:	A-5219 on 09-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,String>
	 */
	private Map<String, String> getFileSizeFromSystemParameter() {
		LOG.entering(CLASS_NAME, "getSystemParameter");
		Collection<String> parameterCodes = new ArrayList<>(2);
		Map<String, String> systemParameters = null;
		parameterCodes.add(MAX_UPLOAD_FILE_SIZE);
		parameterCodes.add(DISABLE_FILE_LOCK);
		try {
			systemParameters = new SharedDefaultsDelegate()
					.findSystemParameterByCodes(parameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			LOG.log(Log.FINE,
					"------SYSTEM PARAMETER FETCH EXCEPTION------------");
		}
		
		return systemParameters;
	}

	/**
	 * 
	 * 	Method		:	UploadExcelCommand.excelToCSVConvertor
	 *	Added by 	:	A-5219 on 09-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param inputStream
	 *	Parameters	:	@param maxColCount
	 *	Parameters	:	@return
	 *	Parameters	:	@throws FileNotFoundException
	 *	Parameters	:	@throws IOException 
	 *	Return type	: 	String
	 */
	private String excelToCSVConvertor(InputStream inputStream, int maxColCount) throws IOException {
		LOG.entering(CLASS_NAME,"excelToCSVConvertor");
		StringBuilder csvData = new StringBuilder();
		int colCount = maxColCount;
		ExcelReader excelReader = new ExcelReader(inputStream, colCount);
		Object[][] fileContent = excelReader.getData();
		
		int rowCount = fileContent.length;
		if(fileContent != null && rowCount > 0){
			for(int i = 0; i < fileContent.length; i++) {

				boolean isContentPresent = false;
				for(int temp = 1; temp < fileContent[i].length; temp++) {
					if(fileContent[i][temp] != null && fileContent[i][temp].toString().trim().length() > 0) {
						isContentPresent = true;
						break;
					}
				}
				if(!isContentPresent) {
					continue;
				}
				for(int j = 0; j < colCount; j++){
					if(fileContent[i][j] != null){
						csvData.append(fileContent[i][j]);
					}else{
						csvData.append("");
					}
					csvData.append(CSV_DELIMITER);
				}
				csvData.append(LINESEP);
			}
			if(csvData != null && !csvData.toString().isEmpty()){
				return csvData.toString();
			}
		}
		LOG.exiting(CLASS_NAME,"excelToCSVConvertor");
		return null;
	}

	/**
	 * 
	 * 	Method		:	UploadExcelCommand.populatefilterVO
	 *	Added by 	:	A-5219 on 09-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param selectedBatchId
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	PaymentBatchFilterVO
	 */
	private PaymentBatchFilterVO populatefilterVO(FilterFromAdvpay filter, LogonAttributes logonAttributes) {
		PaymentBatchFilterVO batchFilterVO = new PaymentBatchFilterVO();
		batchFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		batchFilterVO.setFileType(MRABTHSTL);
		batchFilterVO.setPageNumber(1);
		batchFilterVO.setDisplayPage(1);
		batchFilterVO.setDefaultPageSize(1);
		batchFilterVO.setBatchId(filter.getBatchID());
		batchFilterVO.setPaCode(filter.getPaCode());
		batchFilterVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(filter.getBatchDate()));
		return batchFilterVO;
	}
}
