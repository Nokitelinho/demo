/*
 * SurchargeProrationForm.java Created on Jul 7, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-5255 
 * @version	0.1, Jul 7, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 7, 2015	     A-5255		First draft
 */

public class SurchargeProrationForm  extends ScreenModel{
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.defaults.viewproration";
	private static final String BUNDLE = "mailproration";
	private String sector;
	private String totalSurchgInUsd;
	private String totalSurchgInBas;
	private String totalSurchgInSdr;
	private String totalSurchgInCur;
	private String action;
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
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}
	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}
	/**
	 * @return the totalSurchgInUsd
	 */
	public String getTotalSurchgInUsd() {
		return totalSurchgInUsd;
	}
	/**
	 * @param totalSurchgInUsd the totalSurchgInUsd to set
	 */
	public void setTotalSurchgInUsd(String totalSurchgInUsd) {
		this.totalSurchgInUsd = totalSurchgInUsd;
	}
	/**
	 * @return the totalSurchgInBas
	 */
	public String getTotalSurchgInBas() {
		return totalSurchgInBas;
	}
	/**
	 * @param totalSurchgInBas the totalSurchgInBas to set
	 */
	public void setTotalSurchgInBas(String totalSurchgInBas) {
		this.totalSurchgInBas = totalSurchgInBas;
	}
	/**
	 * @return the totalSurchgInSdr
	 */
	public String getTotalSurchgInSdr() {
		return totalSurchgInSdr;
	}
	/**
	 * @param totalSurchgInSdr the totalSurchgInSdr to set
	 */
	public void setTotalSurchgInSdr(String totalSurchgInSdr) {
		this.totalSurchgInSdr = totalSurchgInSdr;
	}
	/**
	 * @return the totalSurchgInCur
	 */
	public String getTotalSurchgInCur() {
		return totalSurchgInCur;
	}
	/**
	 * @param totalSurchgInCur the totalSurchgInCur to set
	 */
	public void setTotalSurchgInCur(String totalSurchgInCur) {
		this.totalSurchgInCur = totalSurchgInCur;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	

}
