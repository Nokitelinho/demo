
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile;

import java.util.Collection;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 *
 * @author A-2046
 *
 */
public class ListCustomerForm extends ScreenModel {

	private static final String BUNDLE = "listcustomerform";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.customerlisting";
// CODE ADDED BY A-5219 FOR ICRD-18283 START
	private String locationType;
	private String locationValue;
	private String customerType;
	private String agent;
	private String cassAgent;
	private String customer;
	private String cccollector;
	private String chkFlag;
	private String expiringBefore;
// CODE ADDED BY A-5219 END
	private String bundle;
	private String custCode;
	private String custName;
	private String custStation;
	private String status;
	private String custTelephone;
	private String custMobile;
	private String custFax;
	private String customerCodeChild;
	private String customerCodePointRdmd;
	private String customerNamePointRdmd;
	private int index;
	private String[] loyaltyProgramme;
	private String loyaltyName;
	private String loyaltyProgrammePtAcc;
	

	private String duplicateAWBStatus;

	private String lastPageNumber = "0";
	private String displayPageNum = "1";
	private String absoluteIndex;
	private String[] check;
	private String[] customerCodes;

	private String[] programFromDate;
	private String[] programToDate;
	private String[] attachFromDate;
	private String[] attachToDate;
	private String[] groupFlag;

	private String[] selectedLoyalties;

	private String dispPage = "1";
	private String lastPage = "0";

	private String[] servicePointRedemption;
	private String[] pointsRedemption;
	private String[] redeemedTo;
	private String[] selectedRows;

	private String rows;
	private String customerCodeLov;

	private String companyCode;
	private String formNumber;
	private String textfiledObj;
	private String textfiledDesc;
	private String rowCount;
	private String displayPage="1";
	private String lastPageNum="0";
	private Collection<CustomerContactVO> collCustomerContactLovVO=null;
	private String flagPointRedemption;
	private String  pointsRedmdTo;
	private String  pointsAccruded;
	private String  pointsRedeemed;
	//for checking customer status;
	private String flag;
	
	private String canEnableShowPoints;
	
	private String[] operationFlag;
	
	private String closeStatus;
	
	private String saveLoyaltyFlag;
	private String screenFlag;
	
	/**
     * ownerId
     */
    private String ownerId;

    /**
     * Document number of the shipment,If AWB , then AWB Number
     */
    private String masterDocumentNumber;

    /**
     * Id corresponding to the duplicate document number
     */
    private String duplicateNumber;

    /**
     * Sequence number for the house airway bill
     */
    private String sequenceNumber;
    /**
	 * @return the custType
	 */
    private String internalAccountHolder;//Added by A-7534 for ICRD-228903
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
	/**
     * House awb number of the shipment,If AWB , then AWB Number
     */
    private String documentNumber;

    private String canRedeem;
    
    private String custType;
    
    // Added by A-7621 for < ICRD-132149 > 
    
    private String iataCode;
	
	// Added by A-5173 for < ICRD-3761 > Starts
	
	public static final String PAGINATION_MODE_FROM_LIST = "LIST";
	public static final String PAGINATION_MODE_FROM_ACTIVATE = "ACTIVATE";
	public static final String PAGINATION_MODE_FROM_INACTIVATE = "INACTIVATE";
	public static final String PAGINATION_MODE_FROM_NAVIGATION_LINK = "NAVIGATION";
	private String navigationMode;
	
	public String getNavigationMode() {
		return navigationMode;
	}

	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
		
	//Added by A-8374 for ICRD-247693
    private boolean clearingAgentFlag;
    public boolean isClearingAgentFlag() {
		return clearingAgentFlag;
	}
	public void setClearingAgentFlag(boolean clearingAgentFlag) {
		this.clearingAgentFlag = clearingAgentFlag;
	}
	// Added by A-5173 for < ICRD-3761 > Ends
	
/**
 * 
 * @return
 */
	public String getCanRedeem() {
		return canRedeem;
	}
/**
 * 
 * @param canRedeem
 */
	public void setCanRedeem(String canRedeem) {
		this.canRedeem = canRedeem;
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
	
	public String getCustCode() {
		return custCode;
	}
/**
 * 
 * @param custCode
 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
/**
 * 
 * @return
 */
	public String getCustFax() {
		return custFax;
	}
/**
 * 
 * @param custFax
 */
	public void setCustFax(String custFax) {
		this.custFax = custFax;
	}
/**
 * 
 * @return
 */
	public String getCustMobile() {
		return custMobile;
	}
/**
 * 
 * @param custMobile
 */
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
/**
 * 
 * @return
 */
	public String getCustName() {
		return custName;
	}
/**
 * 
 * @param custName
 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
/**
 * 
 * @return
 */
	public String getCustomerCodeChild() {
		return customerCodeChild;
	}
/**
 * 
 * @param customerCodeChild
 */
	public void setCustomerCodeChild(String customerCodeChild) {
		this.customerCodeChild = customerCodeChild;
	}
/**
 * 
 * @return
 */
	public String getCustomerCodePointRdmd() {
		return customerCodePointRdmd;
	}
/**
 * 
 * @param customerCodePointRdmd
 */
	public void setCustomerCodePointRdmd(String customerCodePointRdmd) {
		this.customerCodePointRdmd = customerCodePointRdmd;
	}
/**
 * 
 * @return
 */
	public String getCustomerNamePointRdmd() {
		return customerNamePointRdmd;
	}
/**
 * 
 * @param customerNamePointRdmd
 */
	public void setCustomerNamePointRdmd(String customerNamePointRdmd) {
		this.customerNamePointRdmd = customerNamePointRdmd;
	}
/**
 * 
 * @return
 */
	public String getCustStation() {
		return custStation;
	}
/**
 * 
 * @param custStation
 */
	public void setCustStation(String custStation) {
		this.custStation = custStation;
	}
/**
 * 
 * @return
 */
	public String getCustTelephone() {
		return custTelephone;
	}
/**
 * 
 * @param custTelephone
 */
	public void setCustTelephone(String custTelephone) {
		this.custTelephone = custTelephone;
	}
/**
 * 
 * @return
 */
	public String getDocumentNumber() {
		return documentNumber;
	}
/**
 * 
 * @param documentNumber
 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
/**
 * 
 * @return
 */
	public String getDuplicateAWBStatus() {
		return duplicateAWBStatus;
	}
/**
 * 
 * @param duplicateAWBStatus
 */
	public void setDuplicateAWBStatus(String duplicateAWBStatus) {
		this.duplicateAWBStatus = duplicateAWBStatus;
	}
/**
 * 
 * @return
 */
	public String getDuplicateNumber() {
		return duplicateNumber;
	}
/**
 * 
 * @param duplicateNumber
 */
	public void setDuplicateNumber(String duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
/**
 * 
 * @return
 */
	public String getLoyaltyName() {
		return loyaltyName;
	}
/**
 * 
 * @param loyaltyName
 */
	public void setLoyaltyName(String loyaltyName) {
		this.loyaltyName = loyaltyName;
	}
/**
 * 
 * @return
 */
	public String[] getLoyaltyProgramme() {
		return loyaltyProgramme;
	}
/**
 * 
 * @param loyaltyProgramme
 */
	public void setLoyaltyProgramme(String[] loyaltyProgramme) {
		this.loyaltyProgramme = loyaltyProgramme;
	}
/**
 * 
 * @return
 */
	public String getLoyaltyProgrammePtAcc() {
		return loyaltyProgrammePtAcc;
	}
/**
 * 
 * @param loyaltyProgrammePtAcc
 */
	public void setLoyaltyProgrammePtAcc(String loyaltyProgrammePtAcc) {
		this.loyaltyProgrammePtAcc = loyaltyProgrammePtAcc;
	}
/**
 * 
 * @return
 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
/**
 * 
 * @param masterDocumentNumber
 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
/**
 * 
 * @return
 */
	public String getOwnerId() {
		return ownerId;
	}
/**
 * 
 * @param ownerId
 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
/**
 * 
 * @return
 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
/**
 * 
 * @param sequenceNumber
 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
/**
 * 
 * @return
 */
	public String getStatus() {
		return status;
	}
/**
 * 
 * @param status
 */
	public void setStatus(String status) {
		this.status = status;
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
	public String getDisplayPageNum() {
		return displayPageNum;
	}
/***
 * 
 * @param displayPageNum
 */
	public void setDisplayPageNum(String displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
/**
 * 
 * @return
 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}
/**
 * 
 * @param lastPageNumber
 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
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
/***
 * 
 * @return
 */
	public String[] getCustomerCodes() {
		return customerCodes;
	}
/**
 * 
 * @param customerCodes
 */
	public void setCustomerCodes(String[] customerCodes) {
		this.customerCodes = customerCodes;
	}
/**
 * 
 * @return
 */
	public String[] getAttachFromDate() {
		return attachFromDate;
	}
/**
 * 
 * @param attachFromDate
 */
	public void setAttachFromDate(String[] attachFromDate) {
		this.attachFromDate = attachFromDate;
	}
/**
 * 
 * @return
 */
	public String[] getAttachToDate() {
		return attachToDate;
	}
/**
 * 
 * @param attachToDate
 */
	public void setAttachToDate(String[] attachToDate) {
		this.attachToDate = attachToDate;
	}
/**
 * 
 * @return
 */
	public String[] getProgramFromDate() {
		return programFromDate;
	}
/**
 * 
 * @param programFromDate
 */
	public void setProgramFromDate(String[] programFromDate) {
		this.programFromDate = programFromDate;
	}
/**
 * 
 * @return
 */
	public String[] getProgramToDate() {
		return programToDate;
	}
/**
 * 
 * @param programToDate
 */
	public void setProgramToDate(String[] programToDate) {
		this.programToDate = programToDate;
	}
/**
 * 
 * @return
 */
	public String[] getGroupFlag() {
		return groupFlag;
	}
/**
 * 
 * @param groupFlag
 */
	public void setGroupFlag(String[] groupFlag) {
		this.groupFlag = groupFlag;
	}
/**
 * 
 * @return
 */
	public String[] getSelectedLoyalties() {
		return selectedLoyalties;
	}
/**
 * 
 * @param selectedLoyalties
 */
	public void setSelectedLoyalties(String[] selectedLoyalties) {
		this.selectedLoyalties = selectedLoyalties;
	}

	/**
	 * @return Returns the dispPage.
	 */
	public String getDispPage() {
		return dispPage;
	}

	/**
	 * @param dispPage The dispPage to set.
	 */
	public void setDispPage(String dispPage) {
		this.dispPage = dispPage;
	}

	/**
	 * @return Returns the lastPage.
	 */
	public String getLastPage() {
		return lastPage;
	}

	/**
	 * @param lastPage The lastPage to set.
	 */
	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * @return Returns the pointsRedemption.
	 */
	public String[] getPointsRedemption() {
		return pointsRedemption;
	}

	/**
	 * @param pointsRedemption The pointsRedemption to set.
	 */
	public void setPointsRedemption(String[] pointsRedemption) {
		this.pointsRedemption = pointsRedemption;
	}

	/**
	 * @return Returns the redeemedTo.
	 */
	public String[] getRedeemedTo() {
		return redeemedTo;
	}

	/**
	 * @param redeemedTo The redeemedTo to set.
	 */
	public void setRedeemedTo(String[] redeemedTo) {
		this.redeemedTo = redeemedTo;
	}

	/**
	 * @return Returns the servicePointRedemption.
	 */
	public String[] getServicePointRedemption() {
		return servicePointRedemption;
	}

	/**
	 * @param servicePointRedemption The servicePointRedemption to set.
	 */
	public void setServicePointRedemption(String[] servicePointRedemption) {
		this.servicePointRedemption = servicePointRedemption;
	}

	/**
	 * @return Returns the selectedRows.
	 */
	public String[] getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows The selectedRows to set.
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	/**
	 * @return Returns the rows.
	 */
	public String getRows() {
		return rows;
	}

	/**
	 * @param rows The rows to set.
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}

	/**
	 * @return Returns the customerCodeLov.
	 */
	public String getCustomerCodeLov() {
		return customerCodeLov;
	}

	/**
	 * @param customerCodeLov The customerCodeLov to set.
	 */
	public void setCustomerCodeLov(String customerCodeLov) {
		this.customerCodeLov = customerCodeLov;
	}

	/**
	 * @return Returns the collCustomerContactLovVO.
	 */
	public Collection<CustomerContactVO> getCollCustomerContactLovVO() {
		return collCustomerContactLovVO;
	}

	/**
	 * @param collCustomerContactLovVO The collCustomerContactLovVO to set.
	 */
	public void setCollCustomerContactLovVO(
			Collection<CustomerContactVO> collCustomerContactLovVO) {
		this.collCustomerContactLovVO = collCustomerContactLovVO;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	 * @return Returns the formNumber.
	 */
	public String getFormNumber() {
		return formNumber;
	}

	/**
	 * @param formNumber The formNumber to set.
	 */
	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
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
	 * @return Returns the rowCount.
	 */
	public String getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount The rowCount to set.
	 */
	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return Returns the textfiledDesc.
	 */
	public String getTextfiledDesc() {
		return textfiledDesc;
	}

	/**
	 * @param textfiledDesc The textfiledDesc to set.
	 */
	public void setTextfiledDesc(String textfiledDesc) {
		this.textfiledDesc = textfiledDesc;
	}

	/**
	 * @return Returns the textfiledObj.
	 */
	public String getTextfiledObj() {
		return textfiledObj;
	}

	/**
	 * @param textfiledObj The textfiledObj to set.
	 */
	public void setTextfiledObj(String textfiledObj) {
		this.textfiledObj = textfiledObj;
	}

	/**
	 * @return Returns the flagPointRedemption.
	 */
	public String getFlagPointRedemption() {
		return flagPointRedemption;
	}

	/**
	 * @param flagPointRedemption The flagPointRedemption to set.
	 */
	public void setFlagPointRedemption(String flagPointRedemption) {
		this.flagPointRedemption = flagPointRedemption;
	}

	/**
	 * @return Returns the pointsRedmdTo.
	 */
	public String getPointsRedmdTo() {
		return pointsRedmdTo;
	}

	/**
	 * @param pointsRedmdTo The pointsRedmdTo to set.
	 */
	public void setPointsRedmdTo(String pointsRedmdTo) {
		this.pointsRedmdTo = pointsRedmdTo;
	}

	/**
	 * @return Returns the pointsAccruded.
	 */
	public String getPointsAccruded() {
		return pointsAccruded;
	}

	/**
	 * @param pointsAccruded The pointsAccruded to set.
	 */
	public void setPointsAccruded(String pointsAccruded) {
		this.pointsAccruded = pointsAccruded;
	}

	/**
	 * @return Returns the pointsRedeemed.
	 */
	public String getPointsRedeemed() {
		return pointsRedeemed;
	}

	/**
	 * @param pointsRedeemed The pointsRedeemed to set.
	 */
	public void setPointsRedeemed(String pointsRedeemed) {
		this.pointsRedeemed = pointsRedeemed;
	}
/**
 * 
 * @return
 */
	public String getFlag() {
		return flag;
	}
/**
 * 
 * @param flag
 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
/**
 * 
 * @return
 */
	public String[] getOperationFlag() {
		return operationFlag;
	}
/**
 * 
 * @param operationFlag
 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}
/**
 * 
 * @return
 */
	public String getCanEnableShowPoints() {
		return canEnableShowPoints;
	}
/**
 * 
 * @param canEnableShowPoints
 */
	public void setCanEnableShowPoints(String canEnableShowPoints) {
		this.canEnableShowPoints = canEnableShowPoints;
	}
/**
 * 
 * @return
 */
	public String getCloseStatus() {
		return closeStatus;
	}
/**
 * 
 * @param closeStatus
 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}
/**
 * 
 * @return
 */
	public String getAbsoluteIndex() {
		return absoluteIndex;
	}
/**
 * 
 * @param absoluteIndex
 */
	public void setAbsoluteIndex(String absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
/**
 * 
 * @return
 */
	public int getIndex() {
		return index;
	}
/**
 * 
 * @param index
 */
	public void setIndex(int index) {
		this.index = index;
	}
/**
 * 
 * @return
 */	
public String getSaveLoyaltyFlag() {
	return saveLoyaltyFlag;
}
/**
 * 
 * @param saveLoyaltyFlag
 */
public void setSaveLoyaltyFlag(String saveLoyaltyFlag) {
	this.saveLoyaltyFlag = saveLoyaltyFlag;
}

// CODE ADDED BY A-5219 FOR ICRD-18283 START
/**
 * @param locationType the locationType to set
 */
public void setLocationType(String locationType) {
	this.locationType = locationType;
}
/**
 * @return the locationType
 */
public String getLocationType() {
	return locationType;
}
/**
 * @param locationValue the locationValue to set
 */
public void setLocationValue(String locationValue) {
	this.locationValue = locationValue;
}
/**
 * @return the locationValue
 */
public String getLocationValue() {
	return locationValue;
}
public void setAgent(String agent) {
	this.agent = agent;
}
/**
 * @return the agent 
 */
public String getAgent() {              
	return agent;
}
/**
 * @param cccollector the cccollector to set
 */
public void setCccollector(String cccollector) { 
	this.cccollector = cccollector;
}
/**
 * @return the cccollector
 */
public String getCccollector() {
	return cccollector;
}
/**
 * @param customer the customer to set
 */
public void setCustomer(String customer) {
	this.customer = customer;
}
/**
 * @return the customer
 */
public String getCustomer() {
	return customer;
}
/**
 * @param cassAgent the cassAgent to set
 */
public void setCassAgent(String cassAgent) {
	this.cassAgent = cassAgent;
}
/**
 * @return the cassAgent
 */
public String getCassAgent() {
	return cassAgent;
}
/**
 * @param customerType the customerType to set
 */
public void setCustomerType(String customerType) {
	this.customerType = customerType;
}
/**
 * @return the customerType
 */
public String getCustomerType() {
	return customerType;
}
/**
 * @param localExprBfre the localExprBfre to set
 */
public void setExpiringBefore(String expiringBefore) {
	this.expiringBefore = expiringBefore;
}
/**
 * @return the localExprBfre
 */
public String getExpiringBefore() {
	return expiringBefore;
}
/**
 * @param chkFlag the chkFlag to set
 */
public void setChkFlag(String chkFlag) {
	this.chkFlag = chkFlag;
}
/**
 * @return the chkFlag
 */
public String getChkFlag() {
	return chkFlag;
}

public String getIataCode() {
	return iataCode;
}

public void setIataCode(String iataCode) {
	this.iataCode = iataCode;
}

public String getScreenFlag() {
	return screenFlag;
}

public void setScreenFlag(String screenFlag) {
	this.screenFlag = screenFlag;
}
/**
 * 
 * 	Method		:	ListCustomerForm.getInternalAccountHolder
 *	Added by 	:	A-7534 on 13-Feb-2018
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
public String getInternalAccountHolder() {
	return internalAccountHolder;
}
/**
 * 
 * 	Method		:	ListCustomerForm.setInternalAccountHolder
 *	Added by 	:	A-7534 on 13-Feb-2018
 * 	Used for 	:
 *	Parameters	:	@param internalAccountHolder 
 *	Return type	: 	void
 */
public void setInternalAccountHolder(String internalAccountHolder) {
	this.internalAccountHolder = internalAccountHolder;
}


//CODE ADDED BY A-5219 END


}
