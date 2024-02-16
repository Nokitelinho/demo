/*
 * DownloadCommand.java Created on Oct Jul 1 2016
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagreconciliation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.download.FileDownloadCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailbagReconciliationSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
/**
 * 
 * @author A-5991
 *
 */
public class DownloadCommand extends FileDownloadCommand {

	private Log log = LogFactory.getLogger("MAIL MRA DEFAULTS");

	private static final String CLASS_NAME = "DownloadCommand";
	
	private static final String MODULE_NAME = "mail.operations";
	
	private static final String SCREENID = "mailtracking.defaults.MailbagReconciliation";

	private static final String DOWNLOAD_FAILURE = "download_failure";
	
	private static final String TEMP_DIR = "mailtracking.defaults.mailbagreconciliation.filegeneration.temporaryserverfolder";
	
	private static final String DEFAULT_TEMP = "temp";

	/**
	 * Method for downloading the file.
	 * 
	 * @param invocationContext
	 * @return void 
	 * @throws CommandInvocationException
	 */
	@Override
	protected FileDownloadCommand.StreamInfo[] getStreamInfo(InvocationContext invocationContext) throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "getStreamInfo");
		
		MailbagReconciliationSession mailbagReconciliationSession = getScreenSession(
				MODULE_NAME, SCREENID);

		//MailbagReconciliationForm mailbagReconciliationForm = (MailbagReconciliationForm) invocationContext.screenModel;
		Map<String,String> systemParameters = null;
		String tempDownLoadFolder = "";
		
		String fileData = mailbagReconciliationSession.getData();
		
		try {
			systemParameters = findSystemParameter();
			if(systemParameters!=null){
				tempDownLoadFolder = systemParameters.get(TEMP_DIR);
			}
		} catch (SystemException e) {
			e.getMessage();
		}
		if(tempDownLoadFolder == null){
		
			tempDownLoadFolder =getApplicationSession().getTempDir();
		}
			String dir = createTempFolder(tempDownLoadFolder);
		/* 
		 * Writing in a file.
		 * File Name - <Report Type>_<Current Date>.csv
		 */
		
        String contentType = "application/CSV";   
        StringBuilder directoryName = new StringBuilder(dir);
        StringBuilder fileName = new StringBuilder("MailReconcilitaionReport");
        fileName.append("_");
        fileName.append(DateUtilities.getCurrentDate("yyyyMMddHHMMSS"));

        StringBuilder zipFileName = new StringBuilder(fileName.toString());

        fileName.append(".csv");
       // zipFileName.append(".zip");
        
	    setZipFile(false);
	    setZipFileName(zipFileName.toString());
        
        StringBuilder filePath = new StringBuilder(directoryName).append(fileName); 
        File file = new File(filePath.toString());
        //setMaxSize(1024*1024); // file size limit in byte 
	    FileDownloadCommand.StreamInfo[] streamInfo = new FileDownloadCommand.StreamInfo[1];
        streamInfo[0] = new FileStreamInfo(contentType, file);
        
        try {
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			output.write(fileData);
			output.close();
		} catch (IOException e) {
			e.getMessage();
		}
        
        log.exiting(CLASS_NAME,"getStreamInfo");
        
        return streamInfo;

	}
	
	/**
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	protected  void getStreamInfoError(InvocationContext invocationContext)
    throws CommandInvocationException{
		
		/*
		 * Return in case of an error - file exceeded limit.
		 */
		invocationContext.target=DOWNLOAD_FAILURE;
		return;
	}
	/**
	 * This method finds the system parameter corresponding to
	 * the parameter code passed
	 * @param parameterCode
	 * @return string
	 * @throws SystemException
	 */
	private Map<String,String> findSystemParameter()
	throws SystemException {
		log.entering("ExportDataCommand","findSystemParameter");
		String parameterValue = null;
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(TEMP_DIR);   
		Map<String,String> systemParameters = null;
		try {
			systemParameters = new SharedDefaultsDelegate()
						.findSystemParameterByCodes(parameterCodes);
		} catch (BusinessDelegateException ex) {
			log.log(Log.SEVERE,"delegate Exception From Find System Parameter");
			throw new SystemException(ex.getMessage());
		}
		/*
		if(systemParameters!=null){
			parameterValue = systemParameters.get(parameterCode);
		}
		*/
		log.exiting("ExportDataCommand","findSystemParameter");
		return systemParameters;
	}
}


