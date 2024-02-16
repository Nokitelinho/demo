/*
 * StockListForm.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1619
 *
 */
public class StockListForm extends ScreenModel {
	
	private String airline ="";
	private String documentType;
	private String docSubType;
//	FOR PAGINATION
	private String lastPageNum = "0";
	private String displayPage = "1";	
	private String isButtonClicked="N" ;
	private String[] rowId;
	private String[] airlineValue;
	private String[] documentValue;
	private String[] docTypeValue;
	private String[] totalStock;
	private String[] requestedFlag;
	private String[] requestedTime;
	private String[] reOrderLevel;	
	private static final String BUNDLE = "stocklistresources"; 
	private String afterReload = "N";
	private String forMessage;
	private String fromStockList = "N";
	private String bundle;
	private String preview;
	private String countTotalFlag = "";//Added by A-5221 as part from the ICRD-23107

    /**
	 * @return Returns the preview.
	 */
	public String getPreview() {
		return this.preview;
	}

	/**
	 * @param preview The preview to set.
	 */
	public void setPreview(String preview) {
		this.preview = preview;
	}

	/**
	 * @return Returns the fromStockList.
	 */
	public String getFromStockList() {
		return this.fromStockList;
	}

	/**
	 * @param fromStockList The fromStockList to set.
	 */
	public void setFromStockList(String fromStockList) {
		this.fromStockList = fromStockList;
	}

	/**
	 * @return Returns the forMessage.
	 */
	public String getForMessage() {
		return forMessage;
	}

	/**
	 * @param forMessage The forMessage to set.
	 */
	public void setForMessage(String forMessage) {
		this.forMessage = forMessage;
	}

	/**
	 * @return Returns the afterReload.
	 */
	public String getAfterReload() {
		return this.afterReload;
	}

	/**
	 * @param afterReload The afterReload to set.
	 */
	public void setAfterReload(String afterReload) {
		this.afterReload = afterReload;
	}

	/**
	 * @return Returns the airlineValue.
	 */
	public String[] getAirlineValue() {
		return this.airlineValue;
	}

	/**
	 * @param airlineValue The airlineValue to set.
	 */
	public void setAirlineValue(String[] airlineValue) {
		this.airlineValue = airlineValue;
	}

	/**
	 * @return Returns the docTypeValue.
	 */
	public String[] getDocTypeValue() {
		return this.docTypeValue;
	}

	/**
	 * @param docTypeValue The docTypeValue to set.
	 */
	public void setDocTypeValue(String[] docTypeValue) {
		this.docTypeValue = docTypeValue;
	}

	/**
	 * @return Returns the documentValue.
	 */
	public String[] getDocumentValue() {
		return this.documentValue;
	}

	/**
	 * @param documentValue The documentValue to set.
	 */
	public void setDocumentValue(String[] documentValue) {
		this.documentValue = documentValue;
	}

	/**
	 * @return Returns the reOrderLevel.
	 */
	public String[] getReOrderLevel() {
		return this.reOrderLevel;
	}

	/**
	 * @param reOrderLevel The reOrderLevel to set.
	 */
	public void setReOrderLevel(String[] reOrderLevel) {
		this.reOrderLevel = reOrderLevel;
	}

	/**
	 * @return Returns the requestedFlag.
	 */
	public String[] getRequestedFlag() {
		return this.requestedFlag;
	}

	/**
	 * @param requestedFlag The requestedFlag to set.
	 */
	public void setRequestedFlag(String[] requestedFlag) {
		this.requestedFlag = requestedFlag;
	}

	/**
	 * @return Returns the requestedTime.
	 */
	public String[] getRequestedTime() {
		return this.requestedTime;
	}

	/**
	 * @param requestedTime The requestedTime to set.
	 */
	public void setRequestedTime(String[] requestedTime) {
		this.requestedTime = requestedTime;
	}

	/**
	 * @return Returns the totalStock.
	 */
	public String[] getTotalStock() {
		return this.totalStock;
	}

	/**
	 * @param totalStock The totalStock to set.
	 */
	public void setTotalStock(String[] totalStock) {
		this.totalStock = totalStock;
	}

	/* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
     */
	/**
     * @return String
     * @param 
     * */
    public String getScreenId() {
        return "stockcontrol.defaults.cto.stocklist";
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
     */
    /**
     * @return String
     * @param 
     * */
    public String getProduct() {
        return "stockcontrol";
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
     */
    /**
     * @return String
     * @param 
     * */
    public String getSubProduct() {
        return "defaults";
    }

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getBundle()
	 */
    /**
     * @return String
     * @param 
     * */
	public String getBundle() {
		// To be reviewed Auto-generated method stub
		return BUNDLE;
	}

	/**
	 * @return Returns the airline.
	 */
	public String getAirline() {
		return this.airline;
	}

	/**
	 * @param airline The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	

	/**
	 * @return Returns the docSubType.
	 */
	public String getDocSubType() {
		return this.docSubType;
	}

	/**
	 * @param docSubType The docSubType to set.
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}

	/**
	 * @return Returns the documentType.
	 */
	public String getDocumentType() {
		return this.documentType;
	}

	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return this.displayPage;
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
		return this.lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the isButtonClicked.
	 */
	public String getIsButtonClicked() {
		return this.isButtonClicked;
	}

	/**
	 * @param isButtonClicked The isButtonClicked to set.
	 */
	public void setIsButtonClicked(String isButtonClicked) {
		this.isButtonClicked = isButtonClicked;
	}

	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return this.rowId;
	}

	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
	//Added by A-5221 as part from the ICRD-23107 starts

	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}

	public String getCountTotalFlag() {
		return countTotalFlag;
	}
	//Added by A-5221 as part from the ICRD-23107 ends

}
