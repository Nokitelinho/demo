/**
 *
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;

/**
 * @author A-1883
 *
 */
public class ListStockRequestForm extends ScreenModel {

	private String reqRefNo="";
	private String[] stockholderCode;
	private String status="";
	private String docType = "";
	private String subType = "";
	private String fromDate="";
	private String toDate="";
	private String code="";
	private String level="";
	private String stockHolderType="";

	private boolean isManual;

	private String lastPageNumber = "0";
	private String displayPage = "1";

	private String[] checkbox;
	private String canCancel = "N";
    private String checkList;
    private String oneRow = "N";
    private String fromStockRequestList = "";
    
    private String awbPrefix;
    private String airlineName;
    private boolean partnerAirline;

	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "liststockrequestresources";

	private String bundle;
	
	/* added by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-20959 and ScreenId: STK016
	 */
	private String countTotalFlag = "";

	private String partnerPrefix;	
	public String getPartnerPrefix() {
		return partnerPrefix;
	}
	public void setPartnerPrefix(String partnerPrefix) {
		this.partnerPrefix = partnerPrefix;
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
		 * @return Returns the docType.
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
		 * @return Returns the docType.
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
		 * @return Returns the code.
		 */
		public String getCode() {
			return code;
		}
		/**
		 * @param code The code to set.
		 */
		public void setCode(String code) {
			this.code = code;
	}
	/**
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="ListStockRequestsDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
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
	 * @param checkbox The checkbox to set.
	 */
	public void setCheckbox(String[] checkbox) {
		this.checkbox = checkbox;
	}
	/**
	* @return Returns the checkbox.
	*/
	public String[] getCheckbox() {

	   return checkbox;
	}
	/**
	* @param  stockholderCode The stockholderCode to set.
	*/
	public void setStockholderCode(String[] stockholderCode) {
	  this.stockholderCode = stockholderCode;
	}
	/**
	* @return Returns the stockholderCode.
	*/
	public String[] getStockholderCode() {

	  return stockholderCode;
	}
	/**
	* @param isManual The isManual to set.
	*/
	public void setManual(boolean isManual) {

	   this.isManual = isManual;
	}
	/**
	 * @return Returns the reqRefNo.
	 */
	public String getReqRefNo() {
		return reqRefNo;
	}
	/**
	 * @param reqRefNo The reqRefNo to set.
	 */
	public void setReqRefNo(String reqRefNo) {
		this.reqRefNo = reqRefNo;
	}
	/**
	 * @return  status Returns the status.
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
	 * @return Returns the subType.
	 */
	public String getSubType() {
		return subType;
	}
	/**
	 * @param subType The subType to set.
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}
	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="ListStockRequestsDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
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
	 * @return Returns the canCancel.
	 */
	public String getCanCancel() {
		return canCancel;
	}

	/**
	 * @param canCancel
	 *            The canCancel to set.
	 */
	public void setCanCancel(String canCancel) {
		this.canCancel = canCancel;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage
	 *            The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNumber.
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * @param lastPageNumber
	 *            The lastPageNumber to set.
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
/**
 * @return
 */
	public String getScreenId() {
		return "stockcontrol.defaults.liststockrequest";
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
	 * @return Returns the checkList.
	 */
	public String getCheckList() {
		return checkList;
	}
	/**
	 * @param checkList The checkList to set.
	 */
	public void setCheckList(String checkList) {
		this.checkList = checkList;
	}
	/**
	 * @return Returns the oneRow.
	 */
	public String getOneRow() {
		return oneRow;
	}
	/**
	 * @param oneRow The oneRow to set.
	 */
	public void setOneRow(String oneRow) {
		this.oneRow = oneRow;
	}
	/**
	 * @return Returns the fromStockRequestList.
	 */
	public String getFromStockRequestList() {
		return fromStockRequestList;
	}
	/**
	 * @param fromStockRequestList The fromStockRequestList to set.
	 */
	public void setFromStockRequestList(String fromStockRequestList) {
		this.fromStockRequestList = fromStockRequestList;
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
	
	//added by A-5216
	/**
	 * 
	 * @param countTotalFlag
	 */
	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}
	
	/**
	 * 
	 * @return countTotalFlag
	 */
	public String getCountTotalFlag() {
		return countTotalFlag;
	}

}

