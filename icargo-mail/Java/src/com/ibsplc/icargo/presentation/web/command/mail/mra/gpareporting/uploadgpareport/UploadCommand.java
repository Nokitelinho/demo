/*
 * UploadCommand.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.uploadgpareport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.struts.upload.FormFile;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.excel.ExcelReader;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.UploadGPAReportForm;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportMessageVO;

/**
 * @author A-2245
 *         /*
 *         Revision History
 *         Version      Date         	  Author        Description
 *         0.1         Feb 21, 2007  	  A-2245		Initial draft
 *         0.2         Sep 14,2007        A-2270        modified for Capturing mailBag level Details
 */
public class UploadCommand extends BaseCommand {
    /**
     * Logger and the file name
     */
    private Log log = LogFactory.getLogger("Mailtracking MRA");

    private static final String CLASS_NAME = "UploadCommand";

    /*
    * Target mapping for success
    */
    private static final String UPLOAD_SUCCESS = "upload_success";
    /**
     * String constant END_OF_MESSAGE
     */
    public static final String END_OF_MESSAGE = "LAST";
    /*
     * String constant MAX_COLUMNS
     */
    private static final int MAX_COLUMNS = 50;
    /**
     * String constant CONSIGNMENT
     */
    public static final String CONSIGNMENT = "CONSIGNMENT";
    /**
     * String constant ETD
     */
    public static final String ETD = "ETD";
    /**
     * String constant ORIGIN
     */
    public static final String ORIGIN = "ORIGIN";
    /**
     * String constant COUNTRY
     */
    public static final String COUNTRY = "COUNTRY";
    /**
     * String constant OFFICE
     */
    public static final String OFFICE = "OFFICE";
    /**
     * String constant CATEGORY
     */
    public static final String CATEGORY = "CATEGORY";
    /**
     * String constant SUBCLASS
     */
    public static final String SUBCLASS = "SUBCLASS";
    /**
     * String constant DESPNB
     */
    public static final String DESPNB = "DESPNB";
    /**
     * String constant WGT
     */
    public static final String WGT = "WGT";
    /**
     * String constant BAGS
     */
    public static final String BAGS = "BAGS";
    /**
     * String constant RATE
     */
    public static final String RATE = "RATE";
    /**
     * String constant FLIGHT
     */
    public static final String FLIGHT = "FLIGHT";
    /**
     * String Constant HSN
     */
    public static final String HSN = "HSN";
    /**
     * String RSN
     */
    public static final String RSN = "RSN";
    /**
     * String REGIND
     */
    public static final String REGIND = "REGIND";
    /*
     * String Array COLUMNS
     */
    private static final String[] COLUMNS = {CONSIGNMENT, ETD, ORIGIN, COUNTRY,
            OFFICE, CATEGORY, SUBCLASS, DESPNB, WGT, BAGS, RATE,HSN,RSN,REGIND};
    /*
     * String constant MESSAGE_TYPE
     */
    private static final String MESSAGE_TYPE = "GPR";
    /*
     * String constant MESSAGE_VERSION
     */
    private static final String MESSAGE_VERSION = "1";
    /*
     * String constant NEW_LINE
     */
    private static final String NEW_LINE = "\r\n";

    /*
     * Error Codes
     */
    private static final String MAND_ENTERFILE = "mailtracking.mra.gpareporting.uploadfile.msg.err.nullvalue";
    private static final String ERR_INVALIDFILE = "mailtracking.mra.gpareporting.uploadfile.msg.err.invalidfile";
    private static final String ERR_INVALIDFILETYPE = "mailtracking.mra.gpareporting.uploadfile.msg.err.invalidfiletype";
    private static final String ERR_FILENOTFOUND = "mailtracking.mra.gpareporting.uploadfile.msg.err.filenotfound";
    private static final String ERR_UNABLETOREAD = "mailtracking.mra.gpareporting.uploadfile.msg.err.unabletoread";
    private static final String UPLOAD_SUCCESSFULLY = "mailtracking.mra.gpareporting.uploadfilesuccessfully";

    /**
     * The execute method
     * for UploadCommand
     * @param invocationContext
     * @throws CommandInvocationException
     */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        log.entering(CLASS_NAME, "execute");
        /*
         * Obtaining uploadGPAReportForm
         */
        UploadGPAReportForm uploadGPAReportForm =
                (UploadGPAReportForm) invocationContext.screenModel;
        invocationContext.target = UPLOAD_SUCCESS;
        String message = null;
    
        Collection<ErrorVO> errors = validateForm(uploadGPAReportForm);
        if(errors.size() > 0) {
            invocationContext.addAllError(errors);
            log.log(Log.INFO, "Setting Failure");
            invocationContext.target = UPLOAD_SUCCESS;
            return;
        }
        FormFile formFile = uploadGPAReportForm.getTheFile();
        log.log(Log.FINE, new StringBuilder().append("Got file ")
                .append(formFile.getFileName()).toString());
        try {
            message = constructMessageFromExcelFile(formFile.getInputStream());
        } catch(FileNotFoundException e) {
            ErrorVO error = new ErrorVO(ERR_FILENOTFOUND);
            log.log(Log.INFO, "Before Adding Error");
            errors.add(error);
        } catch(IOException e) {
            ErrorVO error = new ErrorVO(ERR_UNABLETOREAD);
            log.log(Log.INFO, "Before Adding Error");
            errors.add(error);
        }
        if(errors.size() > 0) {
        	
            invocationContext.addAllError(errors);
            log.log(Log.INFO, "Setting Failure");
            invocationContext.target = UPLOAD_SUCCESS;
            return;
        }

        /*
         *
         * Populating gpaReportMessageVO
         */
        LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
        GPAReportMessageVO gpaReportMessageVO = new GPAReportMessageVO();
        gpaReportMessageVO.setMessage(message);
        gpaReportMessageVO.setCompanyCode(logonAttributes.getCompanyCode());
        gpaReportMessageVO.setStationCode(logonAttributes.getStationCode());

        MailTrackingMRADelegate mailTrackingMRADelegate =
                new MailTrackingMRADelegate();
        /*
         * delegate call
         */
        try {
            mailTrackingMRADelegate.uploadGPAReport(gpaReportMessageVO);
        } catch(BusinessDelegateException businessDelegateException) {
        	
            errors = handleDelegateException(businessDelegateException);
           
        }
        if(errors != null && errors.size() > 0) {
        	invocationContext.target = UPLOAD_SUCCESS;
            invocationContext.addAllError(errors);
            return;
           
        }
        else{
        errors = new ArrayList<ErrorVO>(1);
    	ErrorVO error = new ErrorVO(UPLOAD_SUCCESSFULLY );
		errors.add(error);
		log.log(Log.INFO, "errors...", errors);
		invocationContext.addAllError(errors);
        }
        invocationContext.target = UPLOAD_SUCCESS;
        log.exiting(CLASS_NAME, "execute");
    }

    /**
     * Method to validate Form
     * @param uploadFileForm
     * @return errors
     */
    private Collection<ErrorVO> validateForm(
            UploadGPAReportForm uploadFileForm) {
        log.entering("UploadCommand", "validateForm");
        Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
        ErrorVO error = null;
        if(uploadFileForm.getTheFile().getFileName().length() == 0) {
            error = new ErrorVO(MAND_ENTERFILE);
            log.log(Log.INFO, "Before Adding Error");
            errors.add(error);
        } else if(uploadFileForm.getTheFile().getFileSize() == 0) {
            error = new ErrorVO(ERR_INVALIDFILE);
            log.log(Log.INFO, "Before Adding Error");
            errors.add(error);
        } else if(!uploadFileForm.getTheFile().getFileName().toUpperCase()
                .endsWith(".XLS")) {
            error = new ErrorVO(ERR_INVALIDFILETYPE);
            log.log(Log.INFO, "Before Adding Error");
            errors.add(error);
        }
        log.exiting("UploadCommand", "validateForm");
        return errors;
    }
    /**
     * Method to construct Message from Excel File
     * @param inputStream
     * @return String
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String constructMessageFromExcelFile(InputStream inputStream)
            throws FileNotFoundException, IOException {
//        int[] columnIndices = null;
        int headerRow = -1;
        ArrayList<Integer> flightRepeatedColumnIndices = null;
//        int[] flightIndices = null;
        String[][] fileContent = readDataFromExcel(inputStream, MAX_COLUMNS);
        if(fileContent != null) {
            headerRow = findHeaderRow(fileContent);
        }
        StringBuilder messageContent = new StringBuilder(50000);
        messageContent.append(MESSAGE_TYPE).append("/")
                .append(MESSAGE_VERSION)
                .append(NEW_LINE);
        if(headerRow > -1) {
        	int[] columnIndices = findColumnIndices(fileContent, headerRow);
            flightRepeatedColumnIndices = findColumnIndexByContentPrefix(
                    fileContent, headerRow, FLIGHT);
            int[] flightIndices = new int[flightRepeatedColumnIndices.size()];
            for(int k = flightIndices.length - 1; k >= 0; k--) {
                flightIndices[k] = flightRepeatedColumnIndices.get(k);
            }
            messageContent.append(findDate(fileContent[0][0])).append("/")
                    .append(findDate(fileContent[0][1])).append(NEW_LINE);
            for(int i = headerRow + 1; i < fileContent.length; i++) {
                String reportContent = constructReportContentInfo(fileContent,
                        headerRow, columnIndices, i);
                if(reportContent != null) {
                    messageContent.append(reportContent);
                } else {
                    continue;
                }
                messageContent.append(constructFlightInfo(fileContent,
                        flightIndices, i));
                messageContent.append(NEW_LINE);
            }
        }
        messageContent.append(END_OF_MESSAGE).append(NEW_LINE);
        return messageContent.toString();
    }

    private String constructFlightInfo(String[][] fileContent,
                                       int[] flightIndices, int currentRow) {
        StringBuilder flightInfo = new StringBuilder(30);
        for(int repeatedCol : flightIndices) {
            if(fileContent[currentRow][repeatedCol].trim().length() > 0) {
                flightInfo.append(fileContent[currentRow][repeatedCol]).append("/");
            }
        }
        return flightInfo.toString();
    }

    private String constructReportContentInfo(String[][] fileContent,
                                              int headerRow,
                                              int[] columnIndices,
                                              int currentRow) {
        StringBuilder rowContent = new StringBuilder(50);
        boolean isValuesPresent = false;
        for(int j = 0; j < columnIndices.length; j++) {
            String cellContent = null;
            if(columnIndices[j] != -1) {
                if(fileContent[currentRow][columnIndices[j]].trim().length() >
                        0) {
                    isValuesPresent = true;
                }
                if(ETD.equalsIgnoreCase(
                        fileContent[headerRow][columnIndices[j]].replace(" ",""))) {
                    cellContent = findDate(fileContent[currentRow][columnIndices[j]]);
                } else if(DESPNB.equalsIgnoreCase(fileContent[headerRow][columnIndices[j]].replace(" ", "")) 
                        || BAGS.equalsIgnoreCase(fileContent[headerRow][columnIndices[j]].replace(" ", ""))
                        || RSN.equalsIgnoreCase(fileContent[headerRow][columnIndices[j]].replace(" ", ""))
                        || HSN.equalsIgnoreCase(fileContent[headerRow][columnIndices[j]].replace(" ", ""))
                        || REGIND.equalsIgnoreCase(fileContent[headerRow][columnIndices[j]].replace(" ", ""))) {
                    if(fileContent[currentRow][columnIndices[j]].endsWith(".0")) {
                        cellContent = fileContent[currentRow][columnIndices[j]].substring(0,fileContent[currentRow][columnIndices[j]].length() - 2);
                    } else {
                        cellContent = fileContent[currentRow][columnIndices[j]];
                    }
                } else {
                    cellContent = fileContent[currentRow][columnIndices[j]];
                }

            } else {
                cellContent = "";
            }
            rowContent.append(cellContent).append("/");
        }
        return isValuesPresent ? rowContent.toString() : null;
    }

    /**
     *
     * @param dateString
     * @return The String representation of the number of days of the given
     * date after 01-JAN-1900
     *
     */
    private String findDate(String dateString) {
        String date = null;
        try {
            int numberOfDays = (int) Double.parseDouble(dateString);
            date = String.valueOf(numberOfDays);
        } catch(NumberFormatException e) {
            date = dateString;
        }
        return date;
    }

    private ArrayList<Integer> findColumnIndexByContentPrefix(
            String[][] fileContent, int row, String repeatedColumn) {
        ArrayList<Integer> repeatedColumnIndices = new ArrayList<Integer>(5);
        for(int k = 0; k < fileContent[row].length; k++) {
            if(fileContent[row][k].replace(" ", "").toUpperCase().startsWith(repeatedColumn)) {
                repeatedColumnIndices.add(k);
            }
        }
        return repeatedColumnIndices;
    }

    private int[] findColumnIndices(String[][] fileContent, int row) {
        int[] columnIndices = new int[COLUMNS.length];
        for(int i = 0; i < COLUMNS.length; i++) {
            columnIndices[i] = findColumnIndexByContent(fileContent, row, COLUMNS[i]);
        }
        return columnIndices;
    }

    /**
     * Method to read Data from Excel
     * @param inputStream
     * @param numberOfColumns
     * @return fileContent
     * @throws FileNotFoundException
     * @throws IOException
     */
    private String[][] readDataFromExcel(InputStream inputStream,
                                         int numberOfColumns)
            throws FileNotFoundException, IOException {
        String[][] fileContent = null;
        ExcelReader excelReader = new ExcelReader(inputStream, numberOfColumns);
        Object[][] data = excelReader.getData();
        excelReader.closeFile();
        if(data != null && data.length > 0) {
            fileContent = new String[data.length][data[0].length];
            for(int i = 0; i < data.length; i++) {
                for(int j = 0; j < data[i].length; j++) {
                    fileContent[i][j] = (data[i][j] != null) ? String.valueOf(data[i][j]).trim() : "";
                }
            }
        }
        return fileContent;
    }

    private int findHeaderRow(String[][] fileContent) {
        int rowIndex = -1;
        for(int k = 0; k < COLUMNS.length; k++) {
            for(int i = 0; i < fileContent.length; i++) {
                for(int j = 0; j < fileContent[i].length; j++) {
                    if(COLUMNS[k].equalsIgnoreCase(fileContent[i][j].replace(" ", ""))) {
                        rowIndex = i;
                        return rowIndex;
                    }
                }
            }
        }
        return rowIndex;
    }

    /**
     * Method to find Column Index by Content
     * @param fileContent
     * @param row
     * @param cellContent
     * @return columnIndex
     */
    private int findColumnIndexByContent(String[][] fileContent, int row,
                                         String cellContent) {
        int columnIndex = -1;
        for(int j = 0; j < fileContent[row].length; j++) {
            if(cellContent.equalsIgnoreCase(fileContent[row][j].replace(" ", ""))) {
                columnIndex = j;
                break;
            }
        }
        return columnIndex;
    }

}
