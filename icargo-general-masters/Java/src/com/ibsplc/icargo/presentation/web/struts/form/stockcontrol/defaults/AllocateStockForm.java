/*
 * AllocateStockForm.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1366
 *
 */
public class AllocateStockForm extends ScreenModel {

// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "allocatestockresources";

	private String bundle;

	private String docType="";
    private String docSubType="";
    private String fromDate;
    private String toDate;
	private String stockHolderType;
	private String stockHolderCode="";
	private String status="";
	private String level;
	private boolean isManual;
	private String stockControlFor="";
	private Page<StockRequestVO> pageStockRequestVo;
	private String lastPageNum="0";
	private String displayPage="1";
	private String[] checkBox;
	private String[] approvedStock;
	private String[] allocatedStock;
	private String[] toBeAllocated;
	private String refNoHidden;
	private String appremarks;
	private String remarks;
	private String buttonStatusFlag = "";
	
	private String closeStatus; //Added by A-5201 for ICRD-24963
	private boolean partnerAirline;
	private String airlineName;
	private String awbPrefix;

	private String startRange;
	
	private String reportGenerateMode; // Added as part of ICRD-46860 to decide whether report should be generated or not	
	
	/** The document range. */
	private String documentRange;	// Added as part of Bug ICRD-73509 for finding maxlength of each documentType
	
	private String partnerPrefix;	
	public String getPartnerPrefix() {
		return partnerPrefix;
	}
	public void setPartnerPrefix(String partnerPrefix) {
		this.partnerPrefix = partnerPrefix;
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
	public String getReportGenerateMode() {
		return reportGenerateMode;
	}
	public void setReportGenerateMode(String reportGenerateMode) {
		this.reportGenerateMode = reportGenerateMode;
	}
	/**
	 * @return Returns the startRange.
	 */
	public String getStartRange() {
		return startRange;
	}
	/**
	 * @param startRange The startRange to set.
	 */
	public void setStartRange(String startRange) {
		this.startRange = startRange;
	}
	/**
	 * @return Returns the checkBox.
	 */
	public String[] getCheckBox() {
		return checkBox;
	}
	/**
	 * @param checkBox The checkBox to set.
	 */
	public void setCheckBox(String[] checkBox) {
		this.checkBox = checkBox;
	}
	/**
	 * @return Returns the stockControlFor.
	 */
	public String getStockControlFor() {
		return stockControlFor;
	}
	/**
	 * @param stockControlFor The stockControlFor to set.
	 */
	public void setStockControlFor(String stockControlFor) {
		this.stockControlFor = stockControlFor;
	}
	/**
	 * @return Returns the docSubType.
	 */
	public String getDocSubType() {
		return docSubType;
	}
	/**
	 * @param docSubType The docSubType to set.
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}
	/**
	 * @return Returns the docType.
	 */
	public String getDocType() {
		return docType;
	}
	/**
	 * @param docType The docType to set.
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}
	/**
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="AllocateStockDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the isManual.
	 */
	public boolean isManual() {
		return isManual;
	}
	/**
	 * @param isManual The isManual to set.
	 */
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	/**
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	/**
	 * @return Returns the stockHolderType.
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}
	/**
	 * @param stockHolderType The stockHolderType to set.
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}
	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="AllocateStockDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return String
	 */
	public String getScreenId() {
		return "stockcontrol.defaults.allocatestock";
	}
	/**
	 * @return String
	 */
	public String getProduct() {
		return "stockcontrol";
	}
	/**
	 * @return String
	 */
	public String getSubProduct() {
		return "defaults";
	}
	/**
	 * @return Returns the level.
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level The level to set.
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
	 * @return Returns the pageStockRequestVo.
	 */
	public Page<StockRequestVO> getPageStockRequestVo() {
		return pageStockRequestVo;
	}
	/**
	 * @param pageStockRequestVo The pageStockRequestVo to set.
	 */
	public void setPageStockRequestVo(Page<StockRequestVO> pageStockRequestVo) {
		this.pageStockRequestVo = pageStockRequestVo;
	}
	/**
	 * @return Returns the approvedStock.
	 */
	public String[] getApprovedStock() {
		return approvedStock;
	}
	/**
	 * @param approvedStock The approvedStock to set.
	 */
	public void setApprovedStock(String[] approvedStock) {
		this.approvedStock = approvedStock;
	}
	/**
	 * @return Returns the allocatedStock.
	 */
	public String[] getAllocatedStock() {
		return allocatedStock;
	}
	/**
	 * @param allocatedStock The allocatedStock to set.
	 */
	public void setAllocatedStock(String[] allocatedStock) {
		this.allocatedStock = allocatedStock;
	}
	/**
	 * @return Returns the refNoHidden.
	 */
	public String getRefNoHidden() {
		return refNoHidden;
	}
	/**
	 * @param refNoHidden The refNoHidden to set.
	 */
	public void setRefNoHidden(String refNoHidden) {
		this.refNoHidden = refNoHidden;
	}
	/**
	 * @return Returns the appremarks.
	 */
	public String getAppremarks() {
		return appremarks;
	}
	/**
	 * @param appremarks The appremarks to set.
	 */
	public void setAppremarks(String appremarks) {
		this.appremarks = appremarks;
	}
	/**
	 * @return Returns the toBeAllocated.
	 */
	public String[] getToBeAllocated() {
		return toBeAllocated;
	}
	/**
	 * @param toBeAllocated The toBeAllocated to set.
	 */
	public void setToBeAllocated(String[] toBeAllocated) {
		this.toBeAllocated = toBeAllocated;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	/**
	 * @return the partnerAirline
	 */
	public boolean isPartnerAirline() {
		return partnerAirline;
	}
	/**
	 * @param partnerAirline the partnerAirline to set
	 */
	public void setPartnerAirline(boolean partnerAirline) {
		this.partnerAirline = partnerAirline;
	}

	//Added by A-5201 for ICRD-24963  starts
	public String getCloseStatus()
    {
        return closeStatus;
    }
    public void setCloseStatus(String closeStatus)
    {
        this.closeStatus = closeStatus;
    }
    //Added by A-5201 for ICRD-24963 end
}
