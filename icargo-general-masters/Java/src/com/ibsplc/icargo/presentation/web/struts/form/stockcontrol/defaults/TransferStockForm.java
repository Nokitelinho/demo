package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

// TODO: Auto-generated Javadoc
/**
 * The Class TransferStockForm.
 *
 * @author A-1952
 */
public class TransferStockForm extends ScreenModel {
	
	/** The Constant DEFAULT_MODE. */
	private static final String DEFAULT_MODE = "numberOfDocuments";
	
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

	/** The total allocated stock. */
	private String totalAllocatedStock;

	/** The transfer from. */
	private String transferFrom;

	/** The transfer to. */
	private String transferTo;

	/** The total no of docs. */
	private String totalNoOfDocs;

	/** The remarks. */
	private String remarks;

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
	
	//for radio button
	/** The transfer mode. */
	private String transferMode = DEFAULT_MODE;

	//Added to transfer stock by specifying only the range from and number of documents
	/** The mode range from. */
	private String modeRangeFrom;
	
	/** The mode number of documents. */
	private String modeNumberOfDocuments;

	// The key attribute specified in struts_config.xml file.
	/** The Constant BUNDLE. */
	private static final String BUNDLE = "transferstockresources";

	/** The bundle. */
	private String bundle;
	
	/** The report generate mode. */
	private String reportGenerateMode; // Added as part of ICRD-46860 to decide whether report should be generated or not
	
	/** The document range. */
	private String documentRange;	// Added as part of Bug ICRD-73509 for finding maxlength of each documentType	
	
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
	 * Gets the stock holder.
	 *
	 * @return stockHolder
	 */
	public String getStockHolder() {
		return stockHolder;
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
	 * Gets the mode.
	 *
	 * @return mode
	 */
	public String getMode() {
		return mode;
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
	 * Gets the transfer from.
	 *
	 * @return transferFrom
	 */
	public String getTransferFrom() {
		return transferFrom;
	}

	/**
	 * Gets the transfer to.
	 *
	 * @return transferTo
	 */
	public String getTransferTo() {
		return transferTo;
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
	 * Gets the total allocated stock.
	 *
	 * @return totalAllocatedStock
	 */
	public String getTotalAllocatedStock() {
		return totalAllocatedStock;
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
	 * Sets the transfer from.
	 *
	 * @param transferFrom the new transfer from
	 */
	public void setTransferFrom(String transferFrom) {
		this.transferFrom = transferFrom;
	}

	/**
	 * Sets the transfer to.
	 *
	 * @param transferTo the new transfer to
	 */
	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
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
	 * Sets the total allocated stock.
	 *
	 * @param totalAllocatedStock the new total allocated stock
	 */
	public void setTotalAllocatedStock(String totalAllocatedStock) {
		this.totalAllocatedStock = totalAllocatedStock;
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
	 * Gets the screen id.
	 *
	 * @return ScreenId
	 */
	public String getScreenId() {
		return "stockcontrol.defaults.transferstockrange";
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
	 * Gets the transfer mode.
	 *
	 * @return Returns the transferMode.
	 */
	public String getTransferMode() {
		return transferMode;
	}
	
	/**
	 * Sets the transfer mode.
	 *
	 * @param transferMode The transferMode to set.
	 */
	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}
	
	/**
	 * Gets the mode number of documents.
	 *
	 * @return Returns the modeNumberOfDocuments.
	 */
	public String getModeNumberOfDocuments() {
		return modeNumberOfDocuments;
	}
	
	/**
	 * Sets the mode number of documents.
	 *
	 * @param modeNumberOfDocuments The modeNumberOfDocuments to set.
	 */
	public void setModeNumberOfDocuments(String modeNumberOfDocuments) {
		this.modeNumberOfDocuments = modeNumberOfDocuments;
	}
	
	/**
	 * Gets the mode range from.
	 *
	 * @return Returns the modeRangeFrom.
	 */
	public String getModeRangeFrom() {
		return modeRangeFrom;
	}
	
	/**
	 * Sets the mode range from.
	 *
	 * @param modeRangeFrom The modeRangeFrom to set.
	 */
	public void setModeRangeFrom(String modeRangeFrom) {
		this.modeRangeFrom = modeRangeFrom;
	}

}