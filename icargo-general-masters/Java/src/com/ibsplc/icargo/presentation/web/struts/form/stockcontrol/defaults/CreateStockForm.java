/**
 *
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1952
 *
 */
public class CreateStockForm extends ScreenModel {

	private boolean isManual;

	private String stockHolder;

	private String[] checkall;

	private String[] check;

	private String totalNoOfDocs;

	private String docType;

	private String subType;

	private String[] rangeFrom;

	private String[] rangeTo;

	private String[] noOfDocs;
	
	private String[] hiddenOpFlag;

	private String buttonStatusFlag;
	
	/*
	 * Added by A-2589 for #102543
	 */
	private boolean partnerAirline;
	private String awbPrefix;
	private String airlineName;
	
	/** The document range. */
	private String documentRange;	// Added as part of Bug ICRD-73509 for finding maxlength of each documentType
	
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
	/*
	 * End - #102543
	 */
	
	/**
	 * @return the airlineName
	 */
	public String getAirlineName() {
		return airlineName;
	}
	/**
	 * @param airlineName the airlineName to set
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	/**
	 * @return the isPartnerAirline
	 */
	public boolean isPartnerAirline() {
		return partnerAirline;
	}
	/**
	 * @param isPartnerAirline the isPartnerAirline to set
	 */
	public void setPartnerAirline(boolean partnerAirline) {
		this.partnerAirline = partnerAirline;
	}
	/**
	 * @return the awbPrefix
	 */
	public String getAwbPrefix() {
		return awbPrefix;
	}
	/**
	 * @param awbPrefix the awbPrefix to set
	 */
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "createstockresources";
	
	private String bundle;


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
	 * @return Returns the checkall.
	 */
	public String[] getCheckall() {
		return checkall;
	}

	/**
	 * @param checkall
	 *            The checkall to set.
	 */
	public void setCheckall(String[] checkall) {
		this.checkall = checkall;
	}

	/**
	 * @return Returns the check.
	 */
	public String[] getCheck() {
		return check;
	}

	/**
	 * @param check
	 *            The check to set.
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * @param isManual
	 *            The check to set.
	 */
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}

	/**
	 * @return Returns the isManual.
	 */
	public boolean isManual() {

		return isManual;
	}

	/**
	 * @return Returns the totalNoOfDocs.
	 */
	public String getTotalNoOfDocs() {
		return totalNoOfDocs;
	}

	/**
	 * @param totalNoOfDocs
	 *            The reqRefNo to set.
	 */
	public void setTotalNoOfDocs(String totalNoOfDocs) {
		this.totalNoOfDocs = totalNoOfDocs;
	}

	/**
	 * @return Returns the stockHolder.
	 */
	public String getStockHolder() {
		return stockHolder;
	}

	/**
	 * @param stockHolder
	 *            The stockHolder to set.
	 */
	public void setStockHolder(String stockHolder) {
		this.stockHolder = stockHolder;
	}

	/**
	 * @return Returns the rangeFrom.
	 */
	public String[] getRangeFrom() {
		return rangeFrom;
	}

	/**
	 * @param rangeFrom
	 *            The rangeFrom to set.
	 */
	public void setRangeFrom(String[] rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	/**
	 * @return rangeTo Returns the rangeTo.
	 */
	public String[] getRangeTo() {
		return rangeTo;
	}

	/**
	 * @param rangeTo
	 *            The rangeTo to set.
	 */
	public void setRangeTo(String[] rangeTo) {
		this.rangeTo = rangeTo;
	}

	/**
	 * @return noOfDocs Returns the noOfDocs.
	 */
	public String[] getNoOfDocs() {
		return noOfDocs;
	}

	/**
	 * @param noOfDocs
	 *            The noOfDocs to set.
	 */
	public void setNoOfDocs(String[] noOfDocs) {
		this.noOfDocs = noOfDocs;
	}

	/**
	 * @return subType Returns the subType.
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * @param subType
	 *            The subType to set.
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * @return docType Returns the docType.
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType
	 *            The docType to set.
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @return
	 */
	public String getScreenId() {
		return "stockcontrol.defaults.createstock";
	}

	/**
	 * @return
	 */
	public String getProduct() {
		return "stockcontrol";
	}

	/**
	 * @return
	 */
	public String getSubProduct() {
		return "defaults";
	}
	/**
	 * @return Returns the buttonStatusFlag.
	 */
	public String getButtonStatusFlag() {
		return buttonStatusFlag;
	}
	/**
	 * @param buttonStatusFlag The buttonStatusFlag to set.
	 */
	public void setButtonStatusFlag(String buttonStatusFlag) {
		this.buttonStatusFlag = buttonStatusFlag;
	}
	/**
	 * @return Returns the hiddenOpFlag.
	 */
	public String[] getHiddenOpFlag() {
		return hiddenOpFlag;
	}
	/**
	 * @param hiddenOpFlag The hiddenOpFlag to set.
	 */
	public void setHiddenOpFlag(String[] hiddenOpFlag) {
		this.hiddenOpFlag = hiddenOpFlag;
	}

}
