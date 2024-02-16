package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * 
 * @author a-1496
 *
 */
public class ListCustomerPointsForm extends ScreenModel {
	
	private static final String BUNDLE = "listcustomerpointsform";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.listcustomerpoints";
	
	//added by A-5175 for QF CR icrd-22065 starts
	public static final String PAGINATION_MODE_FROM_FILTER="FILTER";
	
	public static final String PAGINATION_MODE_FROM_NAVIGATION="NAVIGATION";
	//ends
	
	private String bundle;
	private String customerCode;
	private String total;
	private String fromDate;
	private String toDate;
	private String awbPrefix="";
	private String documentNumber="";
	private String hawbNumber="";
	
	private String displayPage = "1";
	private String lastPageNum =  "0";
	private String totalRecords = "0";
	private String currentPageNum = "1";
	//added for integration with customer lis
	private String[] check;
	private String fromListing;
	
	private String duplicateAWBStatus;
	
	private String dispPageNum = "1";
	private String lastPagNumber = "0";
	
	private String navigationMode;
	
	/**
	 * 	Getter for navigationMode 
	 *	Added by : A-5175 on 24-Oct-2012
	 * 	Used for : icrd-22065
	 */
	public String getNavigationMode() {
		return navigationMode;
	}
	/**
	 *  @param navigationMode the navigationMode to set
	 * 	Setter for navigationMode 
	 *	Added by : A-5175 on 24-Oct-2012
	 * 	Used for : icrd-22065
	 */
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
	/**
	 * 
	 * @return
	 */
	public String getDispPageNum() {
		return dispPageNum;
	}
	/**
	 * 
	 * @param dispPageNum
	 */
	public void setDispPageNum(String dispPageNum) {
		this.dispPageNum = dispPageNum;
	}
	/**
	 * 
	 * @return
	 */
	public String getLastPagNumber() {
		return lastPagNumber;
	}
	/**
	 * 
	 * @param lastPagNumber
	 */
	public void setLastPagNumber(String lastPagNumber) {
		this.lastPagNumber = lastPagNumber;
	}
	/**
	 * @return Returns the duplicateAWBStatus.
	 */
	public String getDuplicateAWBStatus() {
		return duplicateAWBStatus;
	}
	/**
	 * @param duplicateAWBStatus The duplicateAWBStatus to set.
	 */
	public void setDuplicateAWBStatus(String duplicateAWBStatus) {
		this.duplicateAWBStatus = duplicateAWBStatus;
	}
	/**
	 * 
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * 
	 * @param bundle
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	/**
	 * 
	 * @return
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * 
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

/**
 * 
 */
    public String getScreenId() {
        return SCREENID;
    }

  /**
   * 
   */
    public String getProduct() {
        return PRODUCT;
    }

/**
 * 
 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
    /**
     * 
     * @return
     */
	@ DateFieldId(id="ListCustomerPointsDateRange",fieldType="from")/*Added By A-5131 for ICRD-9704*/
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * 
	 * @param fromDate
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * 
	 * @return
	 */
	@ DateFieldId(id="ListCustomerPointsDateRange",fieldType="to")/*Added By A-5131 for ICRD-9704*/
	public String getToDate() {
		return toDate;
	}
	/**
	 * 
	 * @param toDate
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * 
	 * @param total
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return Returns the awbPrefix.
	 */
	public String getAwbPrefix() {
		return awbPrefix;
	}
	/**
	 * @param awbPrefix The awbPrefix to set.
	 */
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
	/**
	 * @return Returns the documentNumber.
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}
	/**
	 * @param documentNumber The documentNumber to set.
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	/**
	 * @return Returns the hawbNumber.
	 */
	public String getHawbNumber() {
		return hawbNumber;
	}
	/**
	 * @param hawbNumber The hawbNumber to set.
	 */
	public void setHawbNumber(String hawbNumber) {
		this.hawbNumber = hawbNumber;
	}
	/**
	 * @return Returns the currentPageNum.
	 */
	public String getCurrentPageNum() {
		return currentPageNum;
	}
	/**
	 * @param currentPageNum The currentPageNum to set.
	 */
	public void setCurrentPageNum(String currentPageNum) {
		this.currentPageNum = currentPageNum;
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
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getCheck() {
		return check;
	}
	/**
	 * 
	 * @param check
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}
	/**
	 * 
	 * @return
	 */
	public String getFromListing() {
		return fromListing;
	}
	/**
	 * 
	 * @param fromListing
	 */
	public void setFromListing(String fromListing) {
		this.fromListing = fromListing;
	}

}
