/*
 * DownloadCommand.java Created on Sep 17, 2010
 * 
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailexceptionreports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.download.FileDownloadCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MailExceptionsReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 * @author A-2414
 * 
 * Data for the report is written to a file, which is downloaded.
 *
 */

public class DownloadCommand extends FileDownloadCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "DownloadCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREENID = "mailtracking.mra.defaults.mailexceptionreports";

	private static final String DOWNLOAD_FAILURE = "download_failure";
	
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
		
		MailExceptionsSession mailExceptionsSession = getScreenSession(
				MODULE_NAME, SCREENID);

		MailExceptionsReportForm mailExceptionsReportForm = (MailExceptionsReportForm) invocationContext.screenModel;
		
		String fileData = mailExceptionsSession.getData();
				
		String tempDownLoadFolder = "";
		
		
			
		tempDownLoadFolder =getApplicationSession().getTempDir();
		
		String dir = createTempFolder(tempDownLoadFolder);
				
		/* 
		 * Writing in a file.
		 * File Name - <Report Type>_<Current Date>.csv
		 */
		
        String contentType = "application/CSV";
        StringBuilder directoryName = new StringBuilder(dir);
        StringBuilder fileName = new StringBuilder(mailExceptionsReportForm
				.getReportType());
        fileName.append("_");
        fileName.append(DateUtilities.getCurrentDate("yyyyMMdd"));

        StringBuilder zipFileName = new StringBuilder(fileName.toString());

        fileName.append(".csv");
        zipFileName.append(".zip");
        
	    setZipFile(true);
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
}