package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * The Class ReturnStockForm.
 *
 * @author A-1952
 */
public class ReturnStockForm extends ScreenModel {

	/** The stock holder. */
	private String stockHolder;

	/** The doc type. */
	private String docType;

	/** The sub type. */
	private String subType;

	/** The reference. */
	private String reference;

	/** The is manual. */
	private boolean isManual;

	/** The checkall. */
	private String[] checkall;

	/** The checksall. */
	private String[] checksall;

	/** The total available stock. */
	private String totalAvailableStock;

	/** The total no of docs. */
	private String totalNoOfDocs;

	/** The remarks. */
	private String remarks;
	
	/** The warning ok. */
	private String warningOk;

	/** The range from. */
	private String[] rangeFrom;

	/** The range to. */
	private String[] rangeTo;

	/** The noof docs. */
	private String[] noofDocs;

	/** The check. */
	private String[] check;

	/** The range fromav. */
	private String[] rangeFromav;

	/** The range toav. */
	private String[] rangeToav;

	/** The noof docsav. */
	private String[] noofDocsav;

	/** The mode. */
	private String mode = "N";
	
	/** The manual check flag. */
	private String manualCheckFlag;

	// The key attribute specified in struts_config.xml file.
	/** The Constant BUNDLE. */
	private static final String BUNDLE = "returnstockresources";

	/** The bundle. */
	private String bundle;

	/** The report generate mode. */
	private String reportGenerateMode; // Added as part of ICRD-46860 to decide whether report should be generated or not
	
	
	/** The document range. */
	private String documentRange;	// Added as part of Bug ICRD-73509 for finding maxlength of each documentType
	//Added as a part of ICRD-187375
	private String returnMode="numberOfDocuments";
	private String modeRangeFrom;
	private String modeNumberOfDocuments;
	private String lastPageNumber = "0";
	
	private String displayPage = "1";
	
	private int totalDocCount = 0;
	
	/**
	 * @return the totalDocCount
	 */
	public int getTotalDocCount() {
		return totalDocCount;
	}

	/**
	 * @param totalDocCount the totalDocCount to set
	 */
	public void setTotalDocCount(int totalDocCount) {
		this.totalDocCount = totalDocCount;
	}

	/**
	 * @return the lastPageNumber
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * @param lastPageNumber the lastPageNumber to set
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * @return the displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
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
	 * Gets the report generate mode.
	 *
	 * @return the report generate mode
	 */
	public String getReportGenerateMode() {
		return reportGenerateMode;
	}
	
	/**
	 * Sets the report generate mode.
	 *
	 * @param reportGenerateMode the new report generate mode
	 */
	public void setReportGenerateMode(String reportGenerateMode) {
		this.reportGenerateMode = reportGenerateMode;
	}
	
	/**
	 * Gets the bundle.
	 *
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	
	/**
	 * Sets the bundle.
	 *
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * Gets the stock holder.
	 *
	 * @return stockHolder
	 */
	public String getStockHolder() {
		return stockHolder;
	}

	/**
	 * Gets the range from.
	 *
	 * @return rangeFrom
	 */
	public String[] getRangeFrom() {
		return rangeFrom;
	}

	/**
	 * Gets the range to.
	 *
	 * @return rangeTo
	 */
	public String[] getRangeTo() {
		return rangeTo;
	}

	/**
	 * Gets the noof docs.
	 *
	 * @return noofDocs
	 */
	public String[] getNoofDocs() {
		return noofDocs;
	}

	/**
	 * Gets the check.
	 *
	 * @return check
	 */
	public String[] getCheck() {
		return check;
	}

	/**
	 * Gets the range fromav.
	 *
	 * @return rangeFromav
	 */
	public String[] getRangeFromav() {
		return rangeFromav;
	}

	/**
	 * Gets the range toav.
	 *
	 * @return rangeToav
	 */
	public String[] getRangeToav() {
		return rangeToav;
	}

	/**
	 * Gets the noof docsav.
	 *
	 * @return noofDocsav
	 */
	public String[] getNoofDocsav() {
		return noofDocsav;
	}

	/**
	 * Gets the mode.
	 *
	 * @return mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Gets the total no of docs.
	 *
	 * @return totalNoOfDocs
	 */
	public String getTotalNoOfDocs() {
		return totalNoOfDocs;
	}

	/**
	 * Gets the remarks.
	 *
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Gets the checksall.
	 *
	 * @return checksall
	 */
	public String[] getChecksall() {
		return checksall;
	}

	/**
	 * Gets the doc type.
	 *
	 * @return docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * Gets the sub type.
	 *
	 * @return subType
	 */

	public String getSubType() {
		return subType;
	}

	/**
	 * Gets the reference.
	 *
	 * @return reference
	 */

	public String getReference() {
		return reference;
	}

	/**
	 * Checks if is manual.
	 *
	 * @return isManual
	 */
	public boolean isManual() {
		return isManual;
	}

	/**
	 * Gets the total available stock.
	 *
	 * @return totalAvailableStock
	 */

	public String getTotalAvailableStock() {
		return totalAvailableStock;
	}

	/**
	 * Sets the range from.
	 *
	 * @param rangeFrom the new range from
	 */
	public void setRangeFrom(String[] rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	/**
	 * Sets the range to.
	 *
	 * @param rangeTo the new range to
	 */
	public void setRangeTo(String[] rangeTo) {
		this.rangeTo = rangeTo;
	}

	/**
	 * Sets the noof docs.
	 *
	 * @param noofDocs the new noof docs
	 */
	public void setNoofDocs(String[] noofDocs) {
		this.noofDocs = noofDocs;
	}

	/**
	 * Sets the check.
	 *
	 * @param check the new check
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * Sets the range fromav.
	 *
	 * @param rangeFromav the new range fromav
	 */
	public void setRangeFromav(String[] rangeFromav) {
		this.rangeFromav = rangeFromav;
	}

	/**
	 * Sets the range toav.
	 *
	 * @param rangeToav the new range toav
	 */
	public void setRangeToav(String[] rangeToav) {
		this.rangeToav = rangeToav;
	}

	/**
	 * Sets the noof docsav.
	 *
	 * @param noofDocsav the new noof docsav
	 */
	public void setNoofDocsav(String[] noofDocsav) {
		this.noofDocsav = noofDocsav;
	}

	/**
	 * Gets the checkall.
	 *
	 * @return checkall
	 */
	public String[] getCheckall() {
		return checkall;
	}

	/**
	 * Sets the total no of docs.
	 *
	 * @param totalNoOfDocs the new total no of docs
	 */
	public void setTotalNoOfDocs(String totalNoOfDocs) {
		this.totalNoOfDocs = totalNoOfDocs;
	}

	/**
	 * Sets the remarks.
	 *
	 * @param remarks the new remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Sets the checksall.
	 *
	 * @param checksall the new checksall
	 */
	public void setChecksall(String[] checksall) {
		this.checksall = checksall;
	}

	/**
	 * Sets the stock holder.
	 *
	 * @param stockHolder the new stock holder
	 */
	public void setStockHolder(String stockHolder) {
		this.stockHolder = stockHolder;
	}

	/**
	 * Sets the checkall.
	 *
	 * @param checkall the new checkall
	 */
	public void setCheckall(String[] checkall) {
		this.checkall = checkall;
	}

	/**
	 * Sets the doc type.
	 *
	 * @param docType the new doc type
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * Sets the sub type.
	 *
	 * @param subType the new sub type
	 */

	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * Sets the reference.
	 *
	 * @param reference the new reference
	 */

	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * Sets the manual.
	 *
	 * @param isManual the new manual
	 */
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}

	/**
	 * Sets the total available stock.
	 *
	 * @param totalAvailableStock the new total available stock
	 */
	public void setTotalAvailableStock(String totalAvailableStock) {
		this.totalAvailableStock = totalAvailableStock;
	}

	/**
	 * Gets the screen id.
	 *
	 * @return ScreenId
	 */
	public String getScreenId() {
		return "stockcontrol.defaults.returnstockrange";
	}

	/**
	 * Gets the product.
	 *
	 * @return Product
	 */
	public String getProduct() {
		return "stockcontrol";
	}

	/**
	 * Gets the sub product.
	 *
	 * @return SubProduct
	 */
	public String getSubProduct() {
		return "defaults";
	}
	
	/**
	 * Gets the warning ok.
	 *
	 * @return the warning ok
	 */
	public String getWarningOk() {
		return warningOk;
	}
	
	/**
	 * Sets the warning ok.
	 *
	 * @param warningOk the new warning ok
	 */
	public void setWarningOk(String warningOk) {
		this.warningOk = warningOk;
	}
	
	/**
	 * Gets the manual check flag.
	 *
	 * @return the manual check flag
	 */
	public String getManualCheckFlag() {
		return manualCheckFlag;
	}
	
	/**
	 * Sets the manual check flag.
	 *
	 * @param manualCheckFlag the new manual check flag
	 */
	public void setManualCheckFlag(String manualCheckFlag) {
		this.manualCheckFlag = manualCheckFlag;
	}
	
	/**
	 * 
	 * @return the range from
	 */
	public String getModeRangeFrom() {
		return modeRangeFrom;
	}
	/**
	 * 
	 * @param modeRangeFrom
	 */
	public void setModeRangeFrom(String modeRangeFrom) {
		this.modeRangeFrom = modeRangeFrom;
	}
	/**
	 * 
	 * @return the no of doc
	 */
	public String getModeNumberOfDocuments() {
		return modeNumberOfDocuments;
	}
	/**
	 * 
	 * @param modeNumberOfDocuments
	 */
	public void setModeNumberOfDocuments(String modeNumberOfDocuments) {
		this.modeNumberOfDocuments = modeNumberOfDocuments;
	}
	/**
	 * 
	 * @return the mode
	 */
	public String getReturnMode() {
		return returnMode;
	}
	/**
	 * 
	 * @param returnMode
	 */
	public void setReturnMode(String returnMode) {
		this.returnMode = returnMode;
	}

}