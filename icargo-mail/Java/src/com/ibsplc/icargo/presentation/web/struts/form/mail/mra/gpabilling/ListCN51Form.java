/*
 * ListCN51Form.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.currency.Money;

/**
 * @author A-1556
 *
 */
public class ListCN51Form extends ScreenModel {

	private static final String BUNDLE = "listcn51resources";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID ="mailtracking.mra.gpabilling.listcn51s";

	private String listcn51frmdat;

	private String listcn51todat;

	private String gpacode;

	private String gpaname;

	private String airlinecode;

	private String[] rowCount;

	private String viewFlag = "N";

	private String fromScreen;

	private Money[] totalAmountInContractCurrency;

	private String accEntryFlag;

	private String selIdx;
	private String lastPageNumber;
	private String displayPage = "1";
	/**
	 * 	Getter for lastPageNumber 
	 *	Added by : A-5219 on 07-May-2014
	 * 	Used for :
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}
	/**
	 *  @param lastPageNumber the lastPageNumber to set
	 * 	Setter for lastPageNumber 
	 *	Added by : A-5219 on 07-May-2014
	 * 	Used for :
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
	/**
	 * 	Getter for displayPage 
	 *	Added by : A-5219 on 07-May-2014
	 * 	Used for :
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 *  @param displayPage the displayPage to set
	 * 	Setter for displayPage 
	 *	Added by : A-5219 on 07-May-2014
	 * 	Used for :
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * @return
	 */
	public String getBundle() {
		return BUNDLE;
	}


	/**
	 * @return Returns the pRODUCT.
	 */
	public String getPRODUCT() {
		return PRODUCT;
	}

	/**
	 * @return Returns the SUBPRODUCT.
	 */
	public String getSUBPRODUCT() {
		return SUBPRODUCT;
	}

	/**
	 *
	 * @return SCREENID
	 */
	public String getScreenId() {

		return SCREENID;
	}

	/**
	 *
	 * @return PRODUCT
	 */
	public String getProduct() {

		return PRODUCT;
	}

	/**
	 *
	 * @return SUBPRODUCT
	 */
	public String getSubProduct() {

		return SUBPRODUCT;
	}


	/**
	 * @return Returns the airlinecode.
	 */
	public String getAirlinecode() {
		return airlinecode;
	}


	/**
	 * @param airlinecode The airlinecode to set.
	 */
	public void setAirlinecode(String airlinecode) {
		this.airlinecode = airlinecode;
	}


	/**
	 * @return Returns the gpacode.
	 */
	public String getGpacode() {
		return gpacode;
	}


	/**
	 * @param gpacode The gpacode to set.
	 */
	public void setGpacode(String gpacode) {
		this.gpacode = gpacode;
	}


	/**
	 * @return Returns the gpaname.
	 */
	public String getGpaname() {
		return gpaname;
	}


	/**
	 * @param gpaname The gpaname to set.
	 */
	public void setGpaname(String gpaname) {
		this.gpaname = gpaname;
	}


	/**
	 * @return Returns the listcn51frmdat.
	 */
	@DateFieldId(id="ListCN51DateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getListcn51frmdat() {
		return listcn51frmdat;
	}


	/**
	 * @param listcn51frmdat The listcn51frmdat to set.
	 */
	public void setListcn51frmdat(String listcn51frmdat) {
		this.listcn51frmdat = listcn51frmdat;
	}


	/**
	 * @return Returns the listcn51todat.
	 */
	@DateFieldId(id="ListCN51DateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getListcn51todat() {
		return listcn51todat;
	}


	/**
	 * @param listcn51todat The listcn51todat to set.
	 */
	public void setListcn51todat(String listcn51todat) {
		this.listcn51todat = listcn51todat;
	}

	/**
	 * @return Returns the rowCount.
	 */
	public String[] getRowCount() {
		return rowCount;
	}


	/**
	 * @param rowCount The rowCount to set.
	 */
	public void setRowCount(String[] rowCount) {
		this.rowCount = rowCount;
	}


	/**
	 * @return Returns the viewFlag.
	 */
	public String getViewFlag() {
		return viewFlag;
	}


	/**
	 * @param viewFlag The viewFlag to set.
	 */
	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}


	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}


	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}


	/**
	 * @return Returns the totalAmountInContractCurrency.
	 */
	public Money[] getTotalAmountInContractCurrency() {
		return totalAmountInContractCurrency;
	}


	/**
	 * @param totalAmountInContractCurrency The totalAmountInContractCurrency to set.
	 */
	public void setTotalAmountInContractCurrency(
			Money[] totalAmountInContractCurrency) {
		this.totalAmountInContractCurrency = totalAmountInContractCurrency;
	}


	public String getAccEntryFlag() {
		return accEntryFlag;
	}


	public void setAccEntryFlag(String accEntryFlag) {
		this.accEntryFlag = accEntryFlag;
	}



/**
	 * @return the selIdx
	 */
	public String getSelIdx() {
		return selIdx;
	}


	/**
	 * @param selIdx the selIdx to set
	 */
	public void setSelIdx(String selIdx) {
		this.selIdx = selIdx;
	}









}
