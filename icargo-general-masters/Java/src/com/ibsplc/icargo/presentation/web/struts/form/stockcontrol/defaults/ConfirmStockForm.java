/**
 *  ConfirmStockForm.java Created on Feb 9, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-4443
 *
 */
public class ConfirmStockForm extends ScreenModel{
	private static final String BUNDLE = "confirmstockresources";
	private static final String PRODUCT = "stockcontrol";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "stockcontrol.defaults.confirmstock";
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
	
	private boolean partnerAirline;
	private String airlineName;
	private String awbPrefix;
	private String operation;
	private String startRange;
	private String endRange;
	private String btn;
	private String popUpStatus;
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
	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}
	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}
	/**
	 * @return the docSubType
	 */
	public String getDocSubType() {
		return docSubType;
	}
	/**
	 * @param docSubType the docSubType to set
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/** 
	 * @return the stockHolderType
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}
	/**
	 * @param stockHolderType the stockHolderType to set
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}
	/**
	 * @return the stockHolderCode
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	/**
	 * @param stockHolderCode the stockHolderCode to set
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the isManual
	 */
	public boolean isManual() {
		return isManual;
	}
	/**
	 * @param isManual the isManual to set
	 */
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}
	/**
	 * @return the stockControlFor
	 */
	public String getStockControlFor() {
		return stockControlFor;
	}
	/**
	 * @param stockControlFor the stockControlFor to set
	 */
	public void setStockControlFor(String stockControlFor) {
		this.stockControlFor = stockControlFor;
	}
	/**
	 * @return the pageStockRequestVo
	 */
	public Page<StockRequestVO> getPageStockRequestVo() {
		return pageStockRequestVo;
	}
	/**
	 * @param pageStockRequestVo the pageStockRequestVo to set
	 */
	public void setPageStockRequestVo(Page<StockRequestVO> pageStockRequestVo) {
		this.pageStockRequestVo = pageStockRequestVo;
	}
	/**
	 * @return the lastPageNum
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * @param lastPageNum the lastPageNum to set
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
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
	 * @return the approvedStock
	 */
	public String[] getApprovedStock() {
		return approvedStock;
	}
	/**
	 * @param approvedStock the approvedStock to set
	 */
	public void setApprovedStock(String[] approvedStock) {
		this.approvedStock = approvedStock;
	}
	/**
	 * @return the allocatedStock
	 */
	public String[] getAllocatedStock() {
		return allocatedStock;
	}
	/**
	 * @param allocatedStock the allocatedStock to set
	 */
	public void setAllocatedStock(String[] allocatedStock) {
		this.allocatedStock = allocatedStock;
	}
	/**
	 * @return the toBeAllocated
	 */
	public String[] getToBeAllocated() {
		return toBeAllocated;
	}
	/**
	 * @param toBeAllocated the toBeAllocated to set
	 */
	public void setToBeAllocated(String[] toBeAllocated) {
		this.toBeAllocated = toBeAllocated;
	}
	/**
	 * @return the refNoHidden
	 */
	public String getRefNoHidden() {
		return refNoHidden;
	}
	/**
	 * @param refNoHidden the refNoHidden to set
	 */
	public void setRefNoHidden(String refNoHidden) {
		this.refNoHidden = refNoHidden;
	}
	/**
	 * @return the appremarks
	 */
	public String getAppremarks() {
		return appremarks;
	}
	/**
	 * @param appremarks the appremarks to set
	 */
	public void setAppremarks(String appremarks) {
		this.appremarks = appremarks;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the buttonStatusFlag
	 */
	public String getButtonStatusFlag() {
		return buttonStatusFlag;
	}
	/**
	 * @param buttonStatusFlag the buttonStatusFlag to set
	 */
	public void setButtonStatusFlag(String buttonStatusFlag) {
		this.buttonStatusFlag = buttonStatusFlag;
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
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
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
	 * @param btn the btn to set
	 */
	public void setBtn(String btn) {
		this.btn = btn;
	}
	/**
	 * @return the btn
	 */
	public String getBtn() {
		return btn;
	}
	/**
	 * @return the popUpStatus
	 */
	public String getPopUpStatus() {
		return popUpStatus;
	}
	/**
	 * @param popUpStatus the popUpStatus to set
	 */
	public void setPopUpStatus(String popUpStatus) {
		this.popUpStatus = popUpStatus;
	}



}
