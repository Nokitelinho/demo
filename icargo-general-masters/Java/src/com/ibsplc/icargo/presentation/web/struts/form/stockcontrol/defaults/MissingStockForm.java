package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

public class MissingStockForm extends ScreenModel {
	private static final String BUNDLE = "missingstock";
	private static final String PRODUCT = "stockcontrol";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "stockcontrol.defaults.missingstock";
	private String bundle;
	private String[] rangeFrom;
	private String[] rangeTo;
	private String stockHolderType;
	private String stockHolderCode="";
	private String[] checkall;
	private String[] check;
	private String startRange;
	private String endRange;
	//added by a-4443 for icrd-3024
	private String fromScreen;
	private String okSuccess;
	//Added by A-2881 for ICRD-8873
	private String[] remarks;
	private String[] hiddenOpFlag;
	
	 /**
	 * @return the bundle
	 */
	public String getBundle() {
		 return BUNDLE;
	}
	/**
	 * @param bundle the bundle to set
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	public String[] getRangeFrom() {
		return rangeFrom;
	}

	public void setRangeFrom(String[] rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public String[] getRangeTo() {
		return rangeTo;
	}

	public void setRangeTo(String[] rangeTo) {
		this.rangeTo = rangeTo;
	}

	
	public String getProduct() {
		// TODO Auto-generated method stub
		return "stockcontrol";
	}

	
	public String getScreenId() {
		// TODO Auto-generated method stub
		return "stockcontrol.defaults.missingstock";
	}

	
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return "defaults";
	}

	/**
	 * @param stockHolderType the stockHolderType to set
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}

	/**
	 * @return the stockHolderType
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}

	/**
	 * @param stockHolderCode the stockHolderCode to set
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * @return the stockHolderCode
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param checkall the checkall to set
	 */
	public void setCheckall(String[] checkall) {
		this.checkall = checkall;
	}

	/**
	 * @return the checkall
	 */
	public String[] getCheckall() {
		return checkall;
	}

	/**
	 * @param check the check to set
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * @return the check
	 */
	public String[] getCheck() {
		return check;
	}
	/**
	 * @return the screenid
	 */
	public static String getScreenid() {
		return SCREENID;
	}
	/**
	 * @return the subproduct
	 */
	public static String getSubproduct() {
		return SUBPRODUCT;
	}
	/**
	 * @param startRange the startRange to set
	 */
	public void setStartRange(String startRange) {
		this.startRange = startRange;
	}
	/**
	 * @return the startRange
	 */
	public String getStartRange() {
		return startRange;
	}
	/**
	 * @param endRange the endRange to set
	 */
	public void setEndRange(String endRange) {
		this.endRange = endRange;
	}
	/**
	 * @return the endRange
	 */
	public String getEndRange() {
		return endRange;
	}
	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}
	/**
	 * @param okSuccess the okSuccess to set
	 */
	public void setOkSuccess(String okSuccess) {
		this.okSuccess = okSuccess;
	}
	/**
	 * @return the okSuccess
	 */
	public String getOkSuccess() {
		return okSuccess;
	}
	/**
	 * @return the remarks
	 */
	public String[] getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	/**
	 * @param hiddenOpFlag the hiddenOpFlag to set
	 */
	public void setHiddenOpFlag(String[] hiddenOpFlag) {
		this.hiddenOpFlag = hiddenOpFlag;
	}
	/**
	 * @return the hiddenOpFlag
	 */
	public String[] getHiddenOpFlag() {
		return hiddenOpFlag;
	}

}
