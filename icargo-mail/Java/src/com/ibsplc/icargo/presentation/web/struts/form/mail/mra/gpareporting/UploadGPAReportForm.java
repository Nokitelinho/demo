/*
 * UploadGPAReportForm.java Created on Feb 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 *  
 * @author A-2257
 * 
 /*
 * 
 * Revision History
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1         Feb 13, 2007   Meera Vijayan			Initial draft
 *  
 *  
 *  
 */
public class UploadGPAReportForm extends ScreenModel {
   
    private static final String BUNDLE = "uploadgpareport";

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.gpareporting.uploadgpareport";
	
	/*form fields*/
    private FormFile theFile;
    private String source="";
    
    /** (non-Javadoc)
     * @return SCREENID  String
     */
    public String getScreenId() {
        return SCREENID;
    }

    /** (non-Javadoc)
     * @return PRODUCT  String
     */
    public String getProduct() {
        return PRODUCT;
    }

    /** (non-Javadoc)
     * @return SUBPRODUCT  String
     */
    public String getSubProduct() {
        return SUBPRODUCT;
    }

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the source.
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source The source to set.
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return Returns the theFile.
	 */
	public FormFile getTheFile() {
		return theFile;
	}

	/**
	 * @param theFile The theFile to set.
	 */
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}



}