/*
 * PostalAdministrationForm.java Created on June 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2047
 *
 */
public class PostalAdministrationForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.masters.postaladministration";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "postalAdminResources";

	private String bundle;
	private String paCode;
	private String paName;
	private String countryCode;
	private String address;
	private String[] recieved;
	private String recievedArray;
	private String[] uplifted;
	private String upliftedArray;
	private String[] handoverReceived;
	private String handoverRcvdArray;
	private String[] loaded;
	private String loadedArray;
	private String[] onlineHandover;
	private String onlineHandoverArray;
	private String[] assigned;
	private String assignedArray;
	private String[] returned;
	private String returnedArray;
	private String[] handedOver;
	private String handedOverArray;
	private String[] pending;
	private String pendingArray;
	//Added by A-5201 for ICRD-85233,ICRD-79018,ICRD-80366 starts
	private String[] readyForDelivery;
	private String readyForDeliveryArray;
	private String[] transportationCompleted;
	private String transportationCompletedArray;
	private String[] arrived;
	private String arrivedArray;	
	//Added by A-5201 for ICRD-85233,ICRD-79018,ICRD-80366 end
	private String[] delivered;
	private String deliveredArray;
	private String[] rowId;
	private String[] operationFlag;
	private String opFlag;
	private String[] mailCategory;
	private String[] mailClass;
	//Added for MRA
	private String[] settlementCurrencyCode;
	private String baseType;
	private String[] billingSource;
	private String[] billingFrequency;
	//added for ICRD-7298
	private String[] parameterValue;
	private String[] HandoverMailTypValue;
	private String[] partyIdentifier;
	
	private String[] detailedRemarks;
	private String[] parCode;
	private String parameterType;	
	

	//	Added for cr 159 by Indu
	private String accNum;
	private String status;
	private String debInvCode;
	//Commented by Gopinath M (A-3217) for the Bug Id: MTK708 @ TRV on 31-Aug-2009
	//private String cusCode;

	private String[] validFrom;
	private String[] validTo;
	private String[] profInv;
	private String[] validSetFrom;
	private String[] validSetTo;
	private String[] validInvFrom;
	private String[] validInvTo;

	private String[] bilSerNum;
	private String[] seSerNum;
	private String[] invSerNum;

	private String[] bilOpFlag;
	private String[] billCheck;
	private String[] stlOpFlag;
	private String[] setCheck;
	private String[] invOpFlag;
	private String[] invCheck;

	private String conPerson;
	private String state;
	private String country;
	private String mobile;
	private String postCod;
	private String phone1;
	private String phone2;
	private String fax;
	private String email;
	private String city;
	private String remarks;

	private String statusActiveOrInactive;

	private String residtversion;
	
	private String vatNumber;
	private String autoEmailReqd;

	//Added by A-7540
	private String latValLevel;
	//Added as part of ICRD-235779 starts
			private String secondaryEmail1;
			private String secondaryEmail2;
			
		
			public String getSecondaryEmail1() {
				return secondaryEmail1;
			}
			public void setSecondaryEmail1(String secondaryEmail1) {
				this.secondaryEmail1 = secondaryEmail1;
			}
			public String getSecondaryEmail2() {
				return secondaryEmail2;
			}
			public void setSecondaryEmail2(String secondaryEmail2) {
				this.secondaryEmail2 = secondaryEmail2;
			}
	////Added as part of ICRD-235779 ends
	public String getLatValLevel() {
		return latValLevel;
	}
	public void setLatValLevel(String latValLevel) {
		this.latValLevel = latValLevel;
	}

	 /**
     * Is MessagingEnabled For PA
     */
    private String messagingEnabled;

    private boolean partialResdit;
    private boolean msgEventLocationNeeded;
    private String dueInDays;
	/**
	 * 	Getter for tolerancePercent 
	 *	Added by : A-7531 on 25-May-2018
	 * 	Used for :
	 */
	public double getTolerancePercent() {
		return tolerancePercent;
	}
	/**
	 *  @param tolerancePercent the tolerancePercent to set
	 * 	Setter for tolerancePercent 
	 *	Added by : A-7531 on 25-May-2018
	 * 	Used for :
	 */
	public void setTolerancePercent(double tolerancePercent) {
		this.tolerancePercent = tolerancePercent;
	}
	/**
	 * 	Getter for toleranceValue 
	 *	Added by : A-7531 on 25-May-2018
	 * 	Used for :
	 */
	public double getToleranceValue() {
		return toleranceValue;
	}
	/**
	 *  @param toleranceValue the toleranceValue to set
	 * 	Setter for toleranceValue 
	 *	Added by : A-7531 on 25-May-2018
	 * 	Used for :
	 */
	public void setToleranceValue(double toleranceValue) {
		this.toleranceValue = toleranceValue;
	}
	/**
	 * 	Getter for maxValue 
	 *	Added by : A-7531 on 25-May-2018
	 * 	Used for :
	 */
	public double getMaxValue() {
		return maxValue;
	}
	/**
	 *  @param maxValue the maxValue to set
	 * 	Setter for maxValue 
	 *	Added by : A-7531 on 25-May-2018
	 * 	Used for :
	 */
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	//Added by A-6991 for the ICRD-211662 
	private String proformaInvoiceRequired;

  //Added by A-5200 for the ICRD-78230 starts
	private String gibCustomerFlag; 
	private String msgSelected;
	//added by A-7371 for ICRD-212135
	private int resditTriggerPeriod;
	//added by A-7531 for icrd-235799
	 private String settlementLevel;
	 private double tolerancePercent;
	 private double toleranceValue;
	 private double maxValue;
	 //Added by A-8353 for icrd-230449
	 private int dupMailbagPeriod;
	 
	public String getGibCustomerFlag() {
		return gibCustomerFlag;
	}
	public void setGibCustomerFlag(String gibCustomerFlag) {
		this.gibCustomerFlag = gibCustomerFlag;
	}
	//Added by A-5200 for the ICRD-78230 ends
	/**
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
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
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the paName.
	 */
	public String getPaName() {
		return paName;
	}

	/**
	 * @param paName The paName to set.
	 */
	public void setPaName(String paName) {
		this.paName = paName;
	}

	/**
	 * @return Returns the assigned.
	 */
	public String[] getAssigned() {
		return assigned;
	}

	/**
	 * @param assigned The assigned to set.
	 */
	public void setAssigned(String[] assigned) {
		this.assigned = assigned;
	}

	/**
	 * @return Returns the assignedArray.
	 */
	public String getAssignedArray() {
		return assignedArray;
	}

	/**
	 * @param assignedArray The assignedArray to set.
	 */
	public void setAssignedArray(String assignedArray) {
		this.assignedArray = assignedArray;
	}

	/**
	 * @return Returns the handedOver.
	 */
	public String[] getHandedOver() {
		return handedOver;
	}

	/**
	 * @param handedOver The handedOver to set.
	 */
	public void setHandedOver(String[] handedOver) {
		this.handedOver = handedOver;
	}

	/**
	 * @return Returns the handedOverArray.
	 */
	public String getHandedOverArray() {
		return handedOverArray;
	}

	/**
	 * @param handedOverArray The handedOverArray to set.
	 */
	public void setHandedOverArray(String handedOverArray) {
		this.handedOverArray = handedOverArray;
	}

	/**
	 * @return Returns the pending.
	 */
	public String[] getPending() {
		return pending;
	}

	/**
	 * @param pending The pending to set.
	 */
	public void setPending(String[] pending) {
		this.pending = pending;
	}

	/**
	 * @return Returns the pendingArray.
	 */
	public String getPendingArray() {
		return pendingArray;
	}

	/**
	 * @param pendingArray The pendingArray to set.
	 */
	public void setPendingArray(String pendingArray) {
		this.pendingArray = pendingArray;
	}

	/**
	 * @return Returns the recieved.
	 */
	public String[] getRecieved() {
		return recieved;
	}

	/**
	 * @param recieved The recieved to set.
	 */
	public void setRecieved(String[] recieved) {
		this.recieved = recieved;
	}

	/**
	 * @return Returns the recievedArray.
	 */
	public String getRecievedArray() {
		return recievedArray;
	}

	/**
	 * @param recievedArray The recievedArray to set.
	 */
	public void setRecievedArray(String recievedArray) {
		this.recievedArray = recievedArray;
	}

	/**
	 * @return Returns the returned.
	 */
	public String[] getReturned() {
		return returned;
	}

	/**
	 * @param returned The returned to set.
	 */
	public void setReturned(String[] returned) {
		this.returned = returned;
	}

	/**
	 * @return Returns the returnedArray.
	 */
	public String getReturnedArray() {
		return returnedArray;
	}

	/**
	 * @param returnedArray The returnedArray to set.
	 */
	public void setReturnedArray(String returnedArray) {
		this.returnedArray = returnedArray;
	}

	/**
	 * @return Returns the uplifted.
	 */
	public String[] getUplifted() {
		return uplifted;
	}

	/**
	 * @param uplifted The uplifted to set.
	 */
	public void setUplifted(String[] uplifted) {
		this.uplifted = uplifted;
	}

	/**
	 * @return Returns the upliftedArray.
	 */
	public String getUpliftedArray() {
		return upliftedArray;
	}

	/**
	 * @param upliftedArray The upliftedArray to set.
	 */
	public void setUpliftedArray(String upliftedArray) {
		this.upliftedArray = upliftedArray;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
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
	 * @return Returns the opFlag.
	 */
	public String getOpFlag() {
		return opFlag;
	}

	/**
	 * @param opFlag The opFlag to set.
	 */
	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}

	/**
	 * @return Returns the mailCategory.
	 */
	public String[] getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory The mailCategory to set.
	 */
	public void setMailCategory(String[] mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return Returns the mailClass.
	 */
	public String[] getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String[] mailClass) {
		this.mailClass = mailClass;
	}

	/**
	 * @return Returns the messagingEnabled.
	 */
	public String getMessagingEnabled() {
		return messagingEnabled;
	}

	/**
	 * @param messagingEnabled The messagingEnabled to set.
	 */
	public void setMessagingEnabled(String messagingEnabled) {
		this.messagingEnabled = messagingEnabled;
	}

	/**
	 * @return Returns the delivered.
	 */
	public String[] getDelivered() {
		return delivered;
	}

	/**
	 * @param delivered The delivered to set.
	 */
	public void setDelivered(String[] delivered) {
		this.delivered = delivered;
	}

	/**
	 * @return Returns the deliveredArray.
	 */
	public String getDeliveredArray() {
		return deliveredArray;
	}

	/**
	 * @param deliveredArray The deliveredArray to set.
	 */
	public void setDeliveredArray(String deliveredArray) {
		this.deliveredArray = deliveredArray;
	}

	/**
	 * @return Returns the handoverRcvdArray.
	 */
	public String getHandoverRcvdArray() {
		return this.handoverRcvdArray;
	}

	/**
	 * @param handoverRcvdArray The handoverRcvdArray to set.
	 */
	public void setHandoverRcvdArray(String handoverRcvdArray) {
		this.handoverRcvdArray = handoverRcvdArray;
	}

	/**
	 * @return Returns the handoverReceived.
	 */
	public String[] getHandoverReceived() {
		return this.handoverReceived;
	}

	/**
	 * @param handoverReceived The handoverReceived to set.
	 */
	public void setHandoverReceived(String[] handoverReceived) {
		this.handoverReceived = handoverReceived;
	}

	/**
	 * @return Returns the loaded.
	 */
	public String[] getLoaded() {
		return this.loaded;
	}

	/**
	 * @param loaded The loaded to set.
	 */
	public void setLoaded(String[] loaded) {
		this.loaded = loaded;
	}

	/**
	 * @return Returns the loadedArray.
	 */
	public String getLoadedArray() {
		return this.loadedArray;
	}

	/**
	 * @param loadedArray The loadedArray to set.
	 */
	public void setLoadedArray(String loadedArray) {
		this.loadedArray = loadedArray;
	}

	/**
	 * @return Returns the onlineHandover.
	 */
	public String[] getOnlineHandover() {
		return this.onlineHandover;
	}

	/**
	 * @param onlineHandover The onlineHandover to set.
	 */
	public void setOnlineHandover(String[] onlineHandover) {
		this.onlineHandover = onlineHandover;
	}

	/**
	 * @return Returns the onlineHandoverArray.
	 */
	public String getOnlineHandoverArray() {
		return this.onlineHandoverArray;
	}

	/**
	 * @param onlineHandoverArray The onlineHandoverArray to set.
	 */
	public void setOnlineHandoverArray(String onlineHandoverArray) {
		this.onlineHandoverArray = onlineHandoverArray;
	}

	/**
	 * @return Returns the msgEventLocationNeeded.
	 */
	public boolean isMsgEventLocationNeeded() {
		return msgEventLocationNeeded;
	}

	/**
	 * @param msgEventLocationNeeded The msgEventLocationNeeded to set.
	 */
	public void setMsgEventLocationNeeded(boolean msgEventLocationNeeded) {
		this.msgEventLocationNeeded = msgEventLocationNeeded;
	}

	/**
	 * @return Returns the partialResdit.
	 */
	public boolean isPartialResdit() {
		return partialResdit;
	}

	/**
	 * @param partialResdit The partialResdit to set.
	 */
	public void setPartialResdit(boolean partialResdit) {
		this.partialResdit = partialResdit;
	}

	/**
	 * @return Returns the baseType.
	 */
	public String getBaseType() {
		return baseType;
	}

	/**
	 * @param baseType The baseType to set.
	 */
	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}




	public String[] getBillingFrequency() {
		return billingFrequency;
	}

	public void setBillingFrequency(String[] billingFrequency) {
		this.billingFrequency = billingFrequency;
	}

	public String[] getBillingSource() {
		return billingSource;
	}

	public void setBillingSource(String[] billingSource) {
		this.billingSource = billingSource;
	}

	public String[] getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String[] settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public String getAccNum() {
		return accNum;
	}

	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getConPerson() {
		return conPerson;
	}

	public void setConPerson(String conPerson) {
		this.conPerson = conPerson;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDebInvCode() {
		return debInvCode;
	}

	public void setDebInvCode(String debInvCode) {
		this.debInvCode = debInvCode;
	}
//	Commented by Gopinath M (A-3217) for the Bug Id: MTK708 @ TRV on 31-Aug-2009
//	public String getCusCode() {
//		return cusCode;
//	}
//
//	public void setCusCode(String cusCode) {
//		this.cusCode = cusCode;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPostCod() {
		return postCod;
	}

	public void setPostCod(String postCod) {
		this.postCod = postCod;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String[] validFrom) {
		this.validFrom = validFrom;
	}

	public String[] getValidSetFrom() {
		return validSetFrom;
	}

	public void setValidSetFrom(String[] validSetFrom) {
		this.validSetFrom = validSetFrom;
	}

	public String[] getValidSetTo() {
		return validSetTo;
	}

	public void setValidSetTo(String[] validSetTo) {
		this.validSetTo = validSetTo;
	}

	public String[] getValidTo() {
		return validTo;
	}

	public void setValidTo(String[] validTo) {
		this.validTo = validTo;
	}

	public String[] getProfInv() {
		return profInv;
	}

	public void setProfInv(String[] profInv) {
		this.profInv = profInv;
	}

	public String[] getBilOpFlag() {
		return bilOpFlag;
	}

	public void setBilOpFlag(String[] bilOpFlag) {
		this.bilOpFlag = bilOpFlag;
	}

	public String[] getBillCheck() {
		return billCheck;
	}

	public void setBillCheck(String[] billCheck) {
		this.billCheck = billCheck;
	}

	public String[] getSetCheck() {
		return setCheck;
	}

	public void setSetCheck(String[] setCheck) {
		this.setCheck = setCheck;
	}

	public String[] getStlOpFlag() {
		return stlOpFlag;
	}

	public void setStlOpFlag(String[] stlOpFlag) {
		this.stlOpFlag = stlOpFlag;
	}

	public String getStatusActiveOrInactive() {
		return statusActiveOrInactive;
	}

	public void setStatusActiveOrInactive(String statusActiveOrInactive) {
		this.statusActiveOrInactive = statusActiveOrInactive;
	}

	public String[] getBilSerNum() {
		return bilSerNum;
	}

	public void setBilSerNum(String[] bilSerNum) {
		this.bilSerNum = bilSerNum;
	}

	public String[] getSeSerNum() {
		return seSerNum;
	}

	public void setSeSerNum(String[] seSerNum) {
		this.seSerNum = seSerNum;
	}

	public String getResidtversion() {
		return residtversion;
	}

	public void setResidtversion(String residtversion) {
		this.residtversion = residtversion;
	}

	/**
	 * @return the vatNumber
	 */
	public String getVatNumber() {
		return vatNumber;
	}

	/**
	 * @param vatNumber the vatNumber to set
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	
	/**
	 * @return the invOpFlag
	 */
	public String[] getInvOpFlag() {
		return invOpFlag;
	}

	/**
	 * @param invOpFlag the invOpFlag to set
	 */
	public void setInvOpFlag(String[] invOpFlag) {
		this.invOpFlag = invOpFlag;
	}

	/**
	 * @return the invCheck
	 */
	public String[] getInvCheck() {
		return invCheck;
	}

	/**
	 * @param invCheck the invCheck to set
	 */
	public void setInvCheck(String[] invCheck) {
		this.invCheck = invCheck;
	}

	

	/**
	 * @return the invSerNum
	 */
	public String[] getInvSerNum() {
		return invSerNum;
	}

	/**
	 * @param invSerNum the invSerNum to set
	 */
	public void setInvSerNum(String[] invSerNum) {
		this.invSerNum = invSerNum;
	}

	

	/**
	 * @return the validInvFrom
	 */
	public String[] getValidInvFrom() {
		return validInvFrom;
	}

	/**
	 * @param validInvFrom the validInvFrom to set
	 */
	public void setValidInvFrom(String[] validInvFrom) {
		this.validInvFrom = validInvFrom;
	}

	/**
	 * @return the validInvTo
	 */
	public String[] getValidInvTo() {
		return validInvTo;
	}

	/**
	 * @param validInvTo the validInvTo to set
	 */
	public void setValidInvTo(String[] validInvTo) {
		this.validInvTo = validInvTo;
	}

	/**
	 * @return the parameterValue
	 */
	public String[] getParameterValue() {
		return parameterValue;
	}

	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String[] parameterValue) {
		this.parameterValue = parameterValue;
	}

	/**
	 * @return the detailedRemarks
	 */
	public String[] getDetailedRemarks() {
		return detailedRemarks;
	}

	/**
	 * @param detailedRemarks the detailedRemarks to set
	 */
	public void setDetailedRemarks(String[] detailedRemarks) {
		this.detailedRemarks = detailedRemarks;
	}
	/**
	 * @return the parameterType
	 */
	public String getParameterType() {
		return parameterType;
	}

	/**
	 * @param parameterType the parameterType to set
	 */
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	/**
	 * @return the parCode
	 */
	public String[] getParCode() {
		return parCode;
	}

	/**
	 * @param parCode the parCode to set
	 */
	public void setParCode(String[] parCode) {
		this.parCode = parCode;
	}
	/**
	 * 	Getter for emailRequiredFlag 
	 *	Added by : A-4809 on 02-Jan-2014
	 * 	Used for :ICRD-42160
	 */
	public String getAutoEmailReqd() {
		return autoEmailReqd;
	}
	/**
	 *  @param emailRequiredFlag the emailRequiredFlag to set
	 * 	Setter for emailRequiredFlag 
	 *	Added by : A-4809 on 02-Jan-2014
	 * 	Used for :ICRD-42160
	 */
	public void setAutoEmailReqd(String autoEmailReqd) {
		this.autoEmailReqd = autoEmailReqd;
	}

	/**
	 *  @param dueInDays the dueInDays to set
	 * 	Setter for dueInDays 
	 *	Added by : A-5219 on 08-Apr-2014
	 * 	Used for :
	 */
	public void setDueInDays(String dueInDays) {
		this.dueInDays = dueInDays;
	}

	/**
	 * 	Getter for dueInDays 
	 *	Added by : A-5219 on 08-Apr-2014
	 * 	Used for :
	 */
	public String getDueInDays() {
		return dueInDays;
	}

	/**
	 * 	Getter for readyForDelivery 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public String[] getReadyForDelivery() {
		return readyForDelivery;
	}
	/**
	 *  @param readyForDelivery the readyForDelivery to set
	 * 	Setter for readyForDelivery 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setReadyForDelivery(String[] readyForDelivery) {
		this.readyForDelivery = readyForDelivery;
	}
	/**
	 * 	Getter for readyForDeliveryArray 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public String getReadyForDeliveryArray() {
		return readyForDeliveryArray;
	}
	/**
	 *  @param readyForDeliveryArray the readyForDeliveryArray to set
	 * 	Setter for readyForDeliveryArray 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setReadyForDeliveryArray(String readyForDeliveryArray) {
		this.readyForDeliveryArray = readyForDeliveryArray;
	}
	/**
	 * 	Getter for transportationCompleted 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public String[] getTransportationCompleted() {
		return transportationCompleted;
	}
	/**
	 *  @param transportationCompleted the transportationCompleted to set
	 * 	Setter for transportationCompleted 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setTransportationCompleted(String[] transportationCompleted) {
		this.transportationCompleted = transportationCompleted;
	}
	/**
	 * 	Getter for transportationCompletedArray 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public String getTransportationCompletedArray() {
		return transportationCompletedArray;
	}
	/**
	 *  @param transportationCompletedArray the transportationCompletedArray to set
	 * 	Setter for transportationCompletedArray 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setTransportationCompletedArray(String transportationCompletedArray) {
		this.transportationCompletedArray = transportationCompletedArray;
	}
	/**
	 * 	Getter for arrived 
	 *	Added by : A-5201 on 14-Oct-2014
	 * 	Used for :
	 */
	public String[] getArrived() {
		return arrived;
	}
	/**
	 *  @param arrived the arrived to set
	 * 	Setter for arrived 
	 *	Added by : A-5201 on 14-Oct-2014
	 * 	Used for :
	 */
	public void setArrived(String[] arrived) {
		this.arrived = arrived;
	}
	/**
	 * 	Getter for arrivedArray 
	 *	Added by : A-5201 on 14-Oct-2014
	 * 	Used for :
	 */
	public String getArrivedArray() {
		return arrivedArray;
	}
	/**
	 *  @param arrivedArray the arrivedArray to set
	 * 	Setter for arrivedArray 
	 *	Added by : A-5201 on 14-Oct-2014
	 * 	Used for :
	 */
	public void setArrivedArray(String arrivedArray) {
		this.arrivedArray = arrivedArray;
	}
	/**
	 * @param msgSelected the msgSelected to set
	 */
	public void setMsgSelected(String msgSelected) {
		this.msgSelected = msgSelected;
	}
	/**
	 * @return the msgSelected
	 */
	public String getMsgSelected() {
		return msgSelected;
	}

	public String[] getPartyIdentifier() {
		return partyIdentifier;
	}
	public void setPartyIdentifier(String[] partyIdentifier) {
		this.partyIdentifier = partyIdentifier;
	}
	/**
	 * 	Getter for profomaInvoiceRequired 
	 *	Added by : A-6991 on 28-Aug-2017
	 * 	Used for : ICRD-211662
	 */
	public String getProformaInvoiceRequired() {
		return proformaInvoiceRequired;
	}
	/**
	 *  @param profomaInvoiceRequired the profomaInvoiceRequired to set
	 * 	Setter for profomaInvoiceRequired 
	 *	Added by : A-6991 on 28-Aug-2017
	 * 	Used for : ICRD-211662
	 */
	public void setProformaInvoiceRequired(String proformaInvoiceRequired) {
		this.proformaInvoiceRequired = proformaInvoiceRequired;
	}  
/**
	 * 
	 * @return resditTriggerPeriod
	 */
	public int getResditTriggerPeriod() {
		return resditTriggerPeriod;
	}
	  /**
	   * 
	   * @param resditTriggerPeriod
	   */
	public void setResditTriggerPeriod(int resditTriggerPeriod) {
		this.resditTriggerPeriod = resditTriggerPeriod;
	}
	public String getSettlementLevel() {
		return settlementLevel;
	}
	public void setSettlementLevel(String settlementLevel) {
		this.settlementLevel = settlementLevel;
	}
	/**
	 * 	Getter for  DupMailbagPeriod 
	 *	Added by : A-8353 on 05-Dec-2018
	 * 	Used for : ICRD-230449
	 */
	
	public int getDupMailbagPeriod() {
		return dupMailbagPeriod;
	}
	/**
	 *  @param DupMailbagPeriod to set
	 * 	Setter for DupMailbagPeriod
	 *	Added by : A-8353 on 05-Dec-2018
	 * 	Used for : ICRD-230449
	 */
	
	public void setDupMailbagPeriod(int dupMailbagPeriod) {
		this.dupMailbagPeriod = dupMailbagPeriod;
	}
	public String[] getHandoverMailTypValue() {
		return HandoverMailTypValue;
	}
	public void setHandoverMailTypValue(String[] handoverMailTypValue) {
		HandoverMailTypValue = handoverMailTypValue;
	}
	
}
