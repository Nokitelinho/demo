

package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1556
 *
 */
public class GPABillingEntriesForm extends ScreenModel {

	//added by A-5175 for QF CR ICRD-21098 starts
	public static final String PAGINATION_MODE_FROM_FILTER="FILTER";
	
	public static final String PAGINATION_MODE_FROM_NAVIGATION="NAVIGATION";
	//ends
	private static final String BUNDLE ="gpabillingentries";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";

	private String fromDate;
	private String selectedRow;
	private String toDate;

	private String gpaCodeFilter;
	private String mailbagId;

	private String contractRate; // A-8164 ICRD-256023
	private String UPURate;
	private boolean reRated;
	private String hasPrivilege; //A-7929 as part if icrd-132548

	private String originOfficeOfExchange;
	private String isUSPSPerformed; //Added by A-7871 for ICRD-232381
	
	/**
	 * @return the isUSPSPerformed
	 */
	public String getIsUSPSPerformed() {
		return isUSPSPerformed;
	}

	/**
	 * @param isUSPSPerformed the isUSPSPerformed to set
	 */
	public void setIsUSPSPerformed(String isUSPSPerformed) {
		this.isUSPSPerformed = isUSPSPerformed;
	}
	
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String getContractRate() {
		return this.contractRate;
	}

	public void setContractRate(String contractRate) {
		this.contractRate = contractRate;
	}

	public String getUPURate() {
		return this.UPURate;
	}

	public void setUPURate(String uPURate) {
		this.UPURate = uPURate;
	}

	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	/**
	 * @param originOfficeOfExchange the originOfficeOfExchange to set
	 */
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	/**
	 * @return the destinationOfficeOfExchange
	 */
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	/**
	 * @param destinationOfficeOfExchange the destinationOfficeOfExchange to set
	 */
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	/**
	 * @return the mailCategoryCode
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode the mailCategoryCode to set
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return the mailSubclass
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass the mailSubclass to set
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the recepatableSerialNumber
	 */
	public String getRecepatableSerialNumber() {
		return recepatableSerialNumber;
	}

	/**
	 * @param recepatableSerialNumber the recepatableSerialNumber to set
	 */
	public void setRecepatableSerialNumber(String recepatableSerialNumber) {
		this.recepatableSerialNumber = recepatableSerialNumber;
	}

	/**
	 * @return the highestNumberIndicator
	 */
	public String getHighestNumberIndicator() {
		return highestNumberIndicator;
	}

	/**
	 * @param highestNumberIndicator the highestNumberIndicator to set
	 */
	public void setHighestNumberIndicator(String highestNumberIndicator) {
		this.highestNumberIndicator = highestNumberIndicator;
	}

	/**
	 * @return the registeredIndicator
	 */
	public String getRegisteredIndicator() {
		return registeredIndicator;
	}

	/**
	 * @param registeredIndicator the registeredIndicator to set
	 */
	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}

	private String destinationOfficeOfExchange;

	private String mailCategoryCode;

	private String mailSubclass;

	private String year;

	private String dsn;
	private String recepatableSerialNumber;
	

	

	private String highestNumberIndicator;
	private String registeredIndicator;
	private String[] flightNumber;

	private String[] flightDate;

	private String[] piecesReceived;

	private String[] weightReceived;

	private String[] billingStatus;

	private String popupDespatchNumber;

	private String popupBillingStatus;

	private String popupRemarks;

	private String select;

	private String[] check;

	private String despatchNumbers;

	private String[] despatchNumber;

	private String screenStatus;

	private String[] saveBillingStatus;

	private String[] rowId;

	private String allCheck;

	private String isBillable;

	private String[] ccaReferenceNumber;
	//added for cr ICRD-7370
	private String [] serviceTax;
	private String [] tds;
	private String [] netAmount;
	
	// Added By A-3434 for pagination
	private String displayPage = "1";
	private String lastPageNum= "0";
	
	private String navigationMode;
	  /**
     * parameterValue
     */
    private String parameterValue;

	private String gpaName;

	private String status;

	private String consignmentNumber;

	private String[] gpaCode;

	private String[] receivedDate;

	private String country;

	private String restrictionFlag;

	private String specificFlag;

	private String overrideRounding;
	private String origin;
	private String destination;
	
	private String roundingValue;// Added by A-7929 for ICRD-260618

	private String currentDialogOption;
	
	private String currentDialogId;
	
	/**
	 * @return the roundingValue
	 */
	public String getRoundingValue() {
		return roundingValue;
	}
	/**
	 * @param roundingValue the roundingValue to set
	 */
	public void setRoundingValue(String roundingValue) {
		this.roundingValue = roundingValue;
	}

	/**
	 * @return the parameterValue
	 */
	public String getParameterValue() {
		return parameterValue;
	}

	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	/**
	 * @return the mailbagId
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 * @param mailbagId the mailbagId to set
	 */

	/**
	 * 	Getter for navigationMode 
	 *	Added by : A-5175 on 15-Oct-2012
	 * 	Used for : CR ICRD-21098
	 */
	public String getNavigationMode() {
		return navigationMode;
	}

	/**
	 *  @param navigationMode the navigationMode to set
	 * 	Setter for navigationMode 
	 *	Added by : A-5175 on 15-Oct-2012
	 * 	Used for : CR ICRD-21098
	 */
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
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
	 * @return Returns the isBillable.
	 */
	public String getIsBillable() {
		return isBillable;
	}

	/**
	 * @param isBillable The isBillable to set.
	 */
	public void setIsBillable(String isBillable) {
		this.isBillable = isBillable;
	}

	/**
	 * @return Returns the allCheck.
	 */
	public String getAllCheck() {
		return allCheck;
	}

	/**
	 * @param allCheck The allCheck to set.
	 */
	public void setAllCheck(String allCheck) {
		this.allCheck = allCheck;
	}

	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return Returns the saveBillingStatus.
	 */
	public String[] getSaveBillingStatus() {
		return saveBillingStatus;
	}

	/**
	 * @param saveBillingStatus The saveBillingStatus to set.
	 */
	public void setSaveBillingStatus(String[] saveBillingStatus) {
		this.saveBillingStatus = saveBillingStatus;
	}

	/**
	 * @return Returns the screenStatus.
	 */
	public String getScreenStatus() {
		return screenStatus;
	}

	/**
	 * @param screenStatus The screenStatus to set.
	 */
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}

	/**
	 * @return Returns the despatchNumber.
	 */
	public String[] getDespatchNumber() {
		return despatchNumber;
	}

	/**
	 * @param despatchNumber The despatchNumber to set.
	 */
	public void setDespatchNumber(String[] despatchNumber) {
		this.despatchNumber = despatchNumber;
	}

	/**
	 * @return Returns the despatchNumbers.
	 */
	public String getDespatchNumbers() {
		return despatchNumbers;
	}

	/**
	 * @param despatchNumbers The despatchNumbers to set.
	 */
	public void setDespatchNumbers(String despatchNumbers) {
		this.despatchNumbers = despatchNumbers;
	}

	/**
	 * @return Returns the check.
	 */
	public String[] getCheck() {
		return check;
	}

	/**
	 * @param check The check to set.
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * @return Returns the select.
	 */
	public String getSelect() {
		return select;
	}

	/**
	 * @param select The select to set.
	 */
	public void setSelect(String select) {
		this.select = select;
	}

	/**
	 * @return Returns the popupBillingStatus.
	 */
	public String getPopupBillingStatus() {
		return popupBillingStatus;
	}

	/**
	 * @param popupBillingStatus The popupBillingStatus to set.
	 */
	public void setPopupBillingStatus(String popupBillingStatus) {
		this.popupBillingStatus = popupBillingStatus;
	}

	/**
	 * @return Returns the popupDespatchNumber.
	 */
	public String getPopupDespatchNumber() {
		return popupDespatchNumber;
	}

	/**
	 * @param popupDespatchNumber The popupDespatchNumber to set.
	 */
	public void setPopupDespatchNumber(String popupDespatchNumber) {
		this.popupDespatchNumber = popupDespatchNumber;
	}

	/**
	 * @return Returns the popupRemarks.
	 */
	public String getPopupRemarks() {
		return popupRemarks;
	}

	/**
	 * @param popupRemarks The popupRemarks to set.
	 */
	public void setPopupRemarks(String popupRemarks) {
		this.popupRemarks = popupRemarks;
	}
	/**
	 * @return Returns the product.
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
	 * @return Returns screenId.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the product.
	 */
    public String getProduct() {
        return PRODUCT;
    }

    /**
	 * @return Returns the subproduct.
	 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }



	/**
	 * @return Returns the billingStatus.
	 */
	public String[] getBillingStatus() {
		return billingStatus;
	}

	/**
	 * @param billingStatus The billingStatus to set.
	 */
	public void setBillingStatus(String[] billingStatus) {
		this.billingStatus = billingStatus;
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
		//this.bundle = bundle;
	}



	/**
	 * @param category The category to set.
	 */

	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}


	/**
	 * @return Returns the flightDate.
	 */
	public String[] getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String[] flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String[] getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="ListBillingEntriesDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
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
	 * @return Returns the gpaCodeFilter.
	 */
	public String getGpaCodeFilter() {
		return gpaCodeFilter;
	}

	/**
	 * @param gpaCodeFilter The gpaCodeFilter to set.
	 */
	public void setGpaCodeFilter(String gpaCodeFilter) {
		this.gpaCodeFilter = gpaCodeFilter;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
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
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="ListBillingEntriesDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
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
	 * @return the consignmentNumber
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return the dsn
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	

	/**
	 * @return Returns the gpaCode.
	 */
	public String[] getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String[] gpaCode) {
		this.gpaCode = gpaCode;
	}

	

	
	

	/**
	 * @return Returns the piecesReceived.
	 */
	public String[] getPiecesReceived() {
		return piecesReceived;
	}

	/**
	 * @param piecesReceived The piecesReceived to set.
	 */
	public void setPiecesReceived(String[] piecesReceived) {
		this.piecesReceived = piecesReceived;
	}

	/**
	 * @return Returns the receivedDate.
	 */
	public String[] getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate The receivedDate to set.
	 */
	public void setReceivedDate(String[] receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return Returns the weightReceived.
	 */
	public String[] getWeightReceived() {
		return weightReceived;
	}

	/**
	 * @param weightReceived The weightReceived to set.
	 */
	public void setWeightReceived(String[] weightReceived) {
		this.weightReceived = weightReceived;
	}

	/**
	 * @return Returns the ccaReferenceNumber.
	 */
	public String[] getCcaReferenceNumber() {
		return ccaReferenceNumber;
	}

	/**
	 * @param ccaReferenceNumber The ccaReferenceNumber to set.
	 */
	public void setCcaReferenceNumber(String[] ccaReferenceNumber) {
		this.ccaReferenceNumber = ccaReferenceNumber;
	}

	/**
	 * @return the serviceTax
	 */
	public String[] getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(String[] serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * @return the tds
	 */
	public String[] getTds() {
		return tds;
	}

	/**
	 * @param tds the tds to set
	 */
	public void setTds(String[] tds) {
		this.tds = tds;
	}

	/**
	 * @return the netAmount
	 */
	public String[] getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(String[] netAmount) {
		this.netAmount = netAmount;
	}
	/**
	 * @param restrictionFlag the restrictionFlag to set
	 */
	public void setRestrictionFlag(String restrictionFlag) {
		this.restrictionFlag = restrictionFlag;
	}
	/**
	 * @return the restrictionFlag
	 */
	public String getRestrictionFlag() {
		return restrictionFlag;
	}

	/**
	 * 	Getter for specificFlag 
	 *	Added by : A-5219 on 06-Apr-2014
	 * 	Used for :
	 */
	public String getSpecificFlag() {
		return specificFlag;
	}

	/**
	 *  @param specificFlag the specificFlag to set
	 * 	Setter for specificFlag 
	 *	Added by : A-5219 on 06-Apr-2014
	 * 	Used for :
	 */
	public void setSpecificFlag(String specificFlag) {
		this.specificFlag = specificFlag;
	}

	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return the reRated
	 */
	  	public boolean isReRated() {
		return reRated;
	}

	/**
	 * @param reRated the reRated to set
	 */
	public void setReRated(boolean reRated) {
		this.reRated = reRated;
	}

	
	public String getOverrideRounding() {
		return overrideRounding;
	}

	/**
	 * @param overrideRounding the overrideRounding to set
	 */
	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}
	/**
	 * 	Getter for origin 
	 *	Added by : A-4809 on May 8, 2018
	 * 	Used for :
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 *  @param origin the origin to set
	 * 	Setter for origin 
	 *	Added by : A-4809 on May 8, 2018
	 * 	Used for :
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * 	Getter for destination 
	 *	Added by : A-4809 on May 8, 2018
	 * 	Used for : ICRD-258393
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 *  @param destination the destination to set
	 * 	Setter for destination 
	 *	Added by : A-4809 on May 8, 2018
	 * 	Used for : ICRD-258393
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getHasPrivilege() {
		return hasPrivilege;
	}

	public void setHasPrivilege(String hasPrivilege) {
		this.hasPrivilege = hasPrivilege;
	}

	
	/**
	 * Sets the current dialog option.
	 *
	 * @param currentDialogOption the currentDialogOption to set
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	/**
	 * Gets the current dialog option.
	 *
	 * @return the currentDialogOption
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * Sets the current dialog id.
	 *
	 * @param currentDialogId the currentDialogId to set
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * Gets the current dialog id.
	 *
	 * @return the currentDialogId
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}
	

}
