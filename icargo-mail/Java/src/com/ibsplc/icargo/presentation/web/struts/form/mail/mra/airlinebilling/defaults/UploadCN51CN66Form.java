/*
 * UploadCN51CN66Form.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults;




import com.ibsplc.icargo.framework.model.ScreenModel;
import org.apache.struts.upload.FormFile;


/**
 * @author Rani Rose John
 * Form for UploadCN51CN66 screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 13, 2007   Rani Rose John    		Initial draft
 */

public class UploadCN51CN66Form extends ScreenModel {

    private static final String BUNDLE = "uploadcn51cn66";

	private static final String PRODUCT = "mra";
	private static final String SUBPRODUCT = "airlinebilling";
	private static final String SCREENID = "mra.defaults.uploadcn51cn66";
	
	//private String sourcePath;
	/*
     * For attaching reports
     */
    private FormFile cnAttached;

	
	/**
	 * @return Returns the SCREENID.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the PRODUCT.
	 */
    public String getProduct() {
        return PRODUCT;
    }
    /**
	 * @return Returns the SUBPRODUCT.
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
	 * @return Returns the cnAttached.
	 */
	public FormFile getCnAttached() {
		return cnAttached;
	}

	/**
	 * @param cnAttached The cnAttached to set.
	 */
	public void setCnAttached(FormFile cnAttached) {
		this.cnAttached = cnAttached;
	}

	

}
