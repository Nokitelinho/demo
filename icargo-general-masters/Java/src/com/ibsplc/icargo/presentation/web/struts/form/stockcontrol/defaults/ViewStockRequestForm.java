/*
 * ListStockRequestForm.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1366
 *
 */
public class ViewStockRequestForm extends ScreenModel {
	
	private String airline;
	private String documentType;
	private String docSubType;
	private String[] content;
	private String[] modeOfCommn;
	private static final String BUNDLE = "viewrequestresources"; 

	private String bundle;

    /**
	 * @return Returns the content.
	 */
	public String[] getContent() {
		return this.content;
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String[] content) {
		this.content = content;
	}

	/**
	 * @return Returns the modeOfCommn.
	 */
	public String[] getModeOfCommn() {
		return this.modeOfCommn;
	}

	/**
	 * @param modeOfCommn The modeOfCommn to set.
	 */
	public void setModeOfCommn(String[] modeOfCommn) {
		this.modeOfCommn = modeOfCommn;
	}

	/**
	 * @return Returns the airline.
	 */
	public String getAirline() {
		return this.airline;
	}

	/**
	 * @param airline The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
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
	 * @return Returns the docSubType.
	 */
	public String getDocSubType() {
		return this.docSubType;
	}

	/**
	 * @param docSubType The docSubType to set.
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}

	/**
	 * @return Returns the documentType.
	 */
	public String getDocumentType() {
		return this.documentType;
	}

	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	

	/* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
     */
    public String getScreenId() {
        return "stockcontrol.defaults.cto.viewstockrequest";
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
     */
    public String getProduct() {
        return "stockcontrol";
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
     */
    public String getSubProduct() {
        return "defaults";
    }

}
