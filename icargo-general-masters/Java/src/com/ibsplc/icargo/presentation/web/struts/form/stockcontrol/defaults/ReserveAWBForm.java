/**
 * 
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1747
 *
 */
public class ReserveAWBForm extends ScreenModel {
	
	private String airline;
	private String awbType;
	private String custCode;
	private String expiryDate="";
	private String remarks;
	private String general;
	private String specific;
	private String totAwb;	
	private static final String BUNDLE = "requeststockresources"; 
	private String bundle;
	private String[] awbPrefix;
	private String[] awbNumber;
	private String[] rowId;
	private String isGeneral;
	private String isSpecific;
	private String afterReserve;
	private String afterCloseList;
	private String docnumForPrint;
	private String awbprefixforprint;
	private String[] reservationOperationFlag;
	private String shpPrefix;
	
	
	
	
	
	

	/**
	 * @return Returns the reservationOperationFlag.
	 */
	public String[] getReservationOperationFlag() {
		return reservationOperationFlag;
	}

	/**
	 * @param reservationOperationFlag The reservationOperationFlag to set.
	 */
	public void setReservationOperationFlag(String[] reservationOperationFlag) {
		this.reservationOperationFlag = reservationOperationFlag;
	}

	/**
	 * @return Returns the awbprefixforprint.
	 */
	public String getAwbprefixforprint() {
		return this.awbprefixforprint;
	}

	/**
	 * @param awbprefixforprint The awbprefixforprint to set.
	 */
	public void setAwbprefixforprint(String awbprefixforprint) {
		this.awbprefixforprint = awbprefixforprint;
	}

	/**
	 * @return Returns the docnumForPrint.
	 */
	public String getDocnumForPrint() {
		return this.docnumForPrint;
	}

	/**
	 * @param docnumForPrint The docnumForPrint to set.
	 */
	public void setDocnumForPrint(String docnumForPrint) {
		this.docnumForPrint = docnumForPrint;
	}

	/**
	 * @return Returns the afterCloseList.
	 */
	public String getAfterCloseList() {
		return this.afterCloseList;
	}

	/**
	 * @param afterCloseList The afterCloseList to set.
	 */
	public void setAfterCloseList(String afterCloseList) {
		this.afterCloseList = afterCloseList;
	}

	/**
	 * @return Returns the afterReserve.
	 */
	public String getAfterReserve() {
		return this.afterReserve;
	}

	/**
	 * @param afterReserve The afterReserve to set.
	 */
	public void setAfterReserve(String afterReserve) {
		this.afterReserve = afterReserve;
	}

	/**
	 * @return Returns the isGeneral.
	 */
	public String getIsGeneral() {
		return this.isGeneral;
	}

	/**
	 * @param isGeneral The isGeneral to set.
	 */
	public void setIsGeneral(String isGeneral) {
		this.isGeneral = isGeneral;
	}

	/**
	 * @return Returns the isSpecific.
	 */
	public String getIsSpecific() {
		return this.isSpecific;
	}

	/**
	 * @param isSpecific The isSpecific to set.
	 */
	public void setIsSpecific(String isSpecific) {
		this.isSpecific = isSpecific;
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

	/**
	 * @return Returns the awbNumber.
	 */
	public String[] getAwbNumber() {
		return this.awbNumber;
	}

	/**
	 * @param awbNumber The awbNumber to set.
	 */
	public void setAwbNumber(String[] awbNumber) {
		this.awbNumber = awbNumber;
	}

	/**
	 * @return Returns the awbPrefix.
	 */
	public String[] getAwbPrefix() {
		return this.awbPrefix;
	}

	/**
	 * @param awbPrefix The awbPrefix to set.
	 */
	public void setAwbPrefix(String[] awbPrefix) {
		this.awbPrefix = awbPrefix;
	}

	/* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
     */
	/**
     * @return String
     * @param 
     * */
    public String getScreenId() {
        return "stockcontrol.defaults.cto.reservestock";
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
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
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
	 * @return Returns the awbType.
	 */
	public String getAwbType() {
		return this.awbType;
	}

	/**
	 * @param awbType The awbType to set.
	 */
	public void setAwbType(String awbType) {
		this.awbType = awbType;
	}

	/**
	 * @return Returns the custCode.
	 */
	public String getCustCode() {
		return this.custCode;
	}

	/**
	 * @param custCode The custCode to set.
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	/**
	 * @return Returns the expiryDate.
	 */
	public String getExpiryDate() {
		return this.expiryDate;
	}

	/**
	 * @param expiryDate The expiryDate to set.
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return Returns the general.
	 */
	public String getGeneral() {
		return this.general;
	}

	/**
	 * @param general The general to set.
	 */
	public void setGeneral(String general) {
		this.general = general;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the specific.
	 */
	public String getSpecific() {
		return this.specific;
	}

	/**
	 * @param specific The specific to set.
	 */
	public void setSpecific(String specific) {
		this.specific = specific;
	}

	/**
	 * @return Returns the totAwb.
	 */
	public String getTotAwb() {
		return this.totAwb;
	}

	/**
	 * @param totAwb The totAwb to set.
	 */
	public void setTotAwb(String totAwb) {
		this.totAwb = totAwb;
	}

	/**
	 * @return Returns the shpPrefix.
	 */
	public String getShpPrefix() {
		return shpPrefix;
	}

	/**
	 * @param shpPrefix The shpPrefix to set.
	 */
	public void setShpPrefix(String shpPrefix) {
		this.shpPrefix = shpPrefix;
	}

}
