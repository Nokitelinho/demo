/*
 * ViewAwbStockForm.java Created on Jan 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3184
 *
 */
public class ViewAwbStockForm extends ScreenModel{


	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "viewawbstockresources";
	
	private String bundle;

	private String awb ="";
	private String awp ="";

/**
	 * @return Returns the awb.
	 */
	public String getAwb() {
		return awb;
		
	}
	/**
	 * @param awb The awb to set.
	 */
	public void setAwb(String awb) {
		this.awb = awb;
	}
	
	/**
	 * @return Returns the awp.
	 */
	public String getAwp() {
		return awp;
	}
	/**
	 * @param awp The awp to set.
	 */
	public void setAwp(String awp) {
		this.awp = awp;
	}
/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
/**
 * @return ScreenId
 */
    public String getScreenId()
    {
        return "stockcontrol.defaults.viewawbstock";
    }
/**
 * @return Product
 */
    public String getProduct()
    {
        return "stockcontrol";
    }
/**
 * @return SubProduct
 */
    public String getSubProduct()
    {
        return "defaults";
    }

}