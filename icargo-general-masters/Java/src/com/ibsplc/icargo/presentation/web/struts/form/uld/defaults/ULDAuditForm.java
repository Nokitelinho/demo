package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;


public class ULDAuditForm extends ScreenModel {
	
	private static final String SCREEN_ID = "uld.defaults.uldaudit";
    private static final String PRODUCT_NAME = "uld";
    private static final String SUBPRODUCT_NAME = "defaults";
    private static final String BUNDLE = "uldaudit";
    
    private String uldNumber;
    private String txnFromDate;
    private String txnToDate;
    
	public String getProduct() {
		return PRODUCT_NAME;
	}

	public String getScreenId() {
		return SCREEN_ID;
	}

	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}
	public String getBundle(){
		return BUNDLE;
	}

	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	public String getTxnFromDate() {
		return txnFromDate;
	}

	public void setTxnFromDate(String txnFromDate) {
		this.txnFromDate = txnFromDate;
	}

	public String getTxnToDate() {
		return txnToDate;
	}

	public void setTxnToDate(String txnToDate) {
		this.txnToDate = txnToDate;
	}

}
