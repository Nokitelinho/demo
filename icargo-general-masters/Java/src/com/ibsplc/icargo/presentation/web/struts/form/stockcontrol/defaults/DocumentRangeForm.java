/**
 * DocumentRangeForm.java Created on June 13, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
 
/**
 * 
 * The Class DocumentRangeForm.
 * @author A-4777
 */
public class DocumentRangeForm extends ScreenModel {	
	
	/** The doc type. */
	private String docType;	
	
	/** The document range. */
	private String documentRange;	
	
	/**
	 * Gets the document range.
	 *
	 * @return the document range
	 */
	public String getDocumentRange() {
		return documentRange;
	}

	/**
	 * Sets the document range.
	 *
	 * @param documentRange the new document range
	 */
	public void setDocumentRange(String documentRange) {
		this.documentRange = documentRange;
	}
	/**
	 * Gets the doc type.
	 *
	 * @return the doc type
	 */
	public String getDocType() {
		return docType;
	}

	
	/**
	 * Sets the doc type.
	 *
	 * @param docType the new doc type
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 */
	public String getScreenId() {
		return "stockcontrol.defaults.documentrange";
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
