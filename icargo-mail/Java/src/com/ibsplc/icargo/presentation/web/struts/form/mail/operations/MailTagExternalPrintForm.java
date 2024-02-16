package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailTagExternalPrintForm.java
 *	This class is used for
 */
public class MailTagExternalPrintForm extends ScreenModel{
	private static final String SCREEN_ID = "mailtracking.defaults.externalmailtagprint";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	@Override
	public String getProduct() {
		return PRODUCT_NAME;
	}

	public String getPrinterId() {
		return printerId;
	}

	public void setPrinterId(String printerId) {
		this.printerId = printerId;
	}

	public String getScreenMode() {
		return screenMode;
	}

	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String getMailTagLabelDetails() {
		return mailTagLabelDetails;
	}

	public void setMailTagLabelDetails(String mailTagLabelDetails) {
		this.mailTagLabelDetails = mailTagLabelDetails;
	}

	@Override
	public String getScreenId() {
		return SCREEN_ID;
	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}
	private String printerId;
	private String officeCode;
	private String screenMode;
	private String redirectURL;
	private String mailTagLabelDetails;
	private String[] mailbagId;
	private String selectedMailBagId;
	private String[] opFlag;
	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String[] getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String[] mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String[] getOpFlag() {
		return opFlag;
	}

	public void setOpFlag(String[] opFlag) {
		this.opFlag = opFlag;
	}

	public String getSelectedMailBagId() {
		return selectedMailBagId;
	}

	public void setSelectedMailBagId(String selectedMailBagId) {
		this.selectedMailBagId = selectedMailBagId;
	}

	

	

}
