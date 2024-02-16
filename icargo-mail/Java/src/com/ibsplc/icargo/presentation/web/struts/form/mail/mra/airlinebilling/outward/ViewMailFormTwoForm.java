/*
 * ViewMailFormTwoForm.java Created on June 20, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward;
import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-3456
 * 
 */
public class ViewMailFormTwoForm extends ScreenModel{

	 private static final String BUNDLE = "viewForm2";
	 private static final String PRODUCT = "mail";
	 private static final String SUBPRODUCT = "mra.airlinebilling";
	 private static final String SCREENID 
			= "mailtracking.mra.airlinebilling.outward.viewform2";
	 private String clearancePeriod;
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
		public String getClearancePeriod() {
			return clearancePeriod;
		}

		public void setClearancePeriod(String clearancePeriod) {
			this.clearancePeriod = clearancePeriod;
		}

}
