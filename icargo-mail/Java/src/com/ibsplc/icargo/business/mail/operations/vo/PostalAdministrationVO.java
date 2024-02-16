/*
 * PostalAdministrationVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 *
 */
public class PostalAdministrationVO extends AbstractVO {
    private String companyCode;
    private String paCode;
    private String paName;
    private String address;
    private String countryCode;
   // private Collection<MailEventVO> mailEvents;
    private String operationFlag;
    private boolean partialResdit;
    private boolean msgEventLocationNeeded;
    //Added for MRA
    private String settlementCurrencyCode;
    private String baseType;
    private String billingSource;
    private String billingFrequency;

    private LocalDate lastUpdateTime;
    private String lastUpdateUser;

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
	private String debInvCode;
	private String status;
	private String accNum;
	private String cusCode;
	private String vatNumber;
	private String autoEmailReqd;
	private int dueInDays;
	//Added by A-6991 for the ICRD-211662 
	private String proformaInvoiceRequired;
	//Added by A-5200 for the ICRD-78230 starts
	private String gibCustomerFlag; 
	//added by A-7371 for ICRD-212135
		private int resditTriggerPeriod;
		 private String settlementLevel;
		 private double tolerancePercent;
		 private double toleranceValue;
		 private double maxValue;
		//added by A-8353 for ICRD-230449
		private int dupMailbagPeriod ;
		//Added as part of ICRD-235779 starts
		private String secondaryEmail1;
		private String secondaryEmail2;
		private boolean isPASSPA;
	
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
	////Added as part of ICRD-235779 starts
	/**
	 * @return the gibCustomerFlag
	 */
	public String getGibCustomerFlag() {
		return gibCustomerFlag;
	}
	/**
	 * @param gibCustomerFlag the gibCustomerFlag to set
	 */
	public void setGibCustomerFlag(String gibCustomerFlag) {
		this.gibCustomerFlag = gibCustomerFlag;
	}
	//Added by A-5200 for the ICRD-78230 ends
	private HashMap<String,Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs;
	private Collection<PostalAdministrationDetailsVO> paDetails;

    public static final String BILLING_SOURCE_BILLING = "B";

    public static final String BILLING_SOURCE_REPORTING = "R";

    /**
     * Is MessagingEnabled For PA
     */
    private String messagingEnabled;

    //Added by A-1945
    private String basisType;

    /**
     * CRQ-AirNZ985
     */
    private String residtversion;

    
    //Added by a-7540
    private String latValLevel;

    public String getLatValLevel() {
		return latValLevel;
	}
	public void setLatValLevel(String latValLevel) {
		this.latValLevel = latValLevel;
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
     * @return operationFlag
     */
    public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
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
     * @return Returns the mailEvents.
     */
//    public Collection<MailEventVO> getMailEvents() {
//        return mailEvents;
//    }
//    /**
//     * @param mailEvents The mailEvents to set.
//     */
//    public void setMailEvents(Collection<MailEventVO> mailEvents) {
//        this.mailEvents = mailEvents;
//    }
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
	 * @return Returns the isPartialResdit.
	 *//*
	public boolean isPartialResdit() {
		return isPartialResdit;
	}
	*//**
	 * @param isPartialResdit The isPartialResdit to set.
	 *//*
	public void setPartialResdit(boolean isPartialResdit) {
		this.isPartialResdit = isPartialResdit;
	}*/
	/**
	 * @return Returns the isMsgEventLocationNeeded.
	 *//*
	public boolean isMsgEventLocationNeeded() {
		return isMsgEventLocationNeeded;
	}
	*//**
	 * @param isMsgEventLocationNeeded The isMsgEventLocationNeeded to set.
	 *//*
	public void setMsgEventLocationNeeded(boolean isMsgEventLocationNeeded) {
		this.isMsgEventLocationNeeded = isMsgEventLocationNeeded;
	}*/
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
	/**
	 * @return Returns the billingFrequency.
	 */
	public String getBillingFrequency() {
		return billingFrequency;
	}
	/**
	 * @param billingFrequency The billingFrequency to set.
	 */
	public void setBillingFrequency(String billingFrequency) {
		this.billingFrequency = billingFrequency;
	}
	/**
	 * @return Returns the billingSource.
	 */
	public String getBillingSource() {
		return billingSource;
	}
	/**
	 * @param billingSource The billingSource to set.
	 */
	public void setBillingSource(String billingSource) {
		this.billingSource = billingSource;
	}
	/**
	 * @return Returns the settlementCurrencyCode.
	 */
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}
	/**
	 * @param settlementCurrencyCode The settlementCurrencyCode to set.
	 */
	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

    /**
     *
     * @return basisType
     */
    public String getBasisType() {
        return basisType;
    }

    /**
     *
     * @param basisType
     */
    public void setBasisType(String basisType) {
        this.basisType = basisType;
    }
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
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
	public String getCusCode() {
		return cusCode;
	}
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
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
	public String getAccNum() {
		return accNum;
	}
	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}
	public HashMap<String, Collection<PostalAdministrationDetailsVO>> getPostalAdministrationDetailsVOs() {
		return postalAdministrationDetailsVOs;
	}
	public void setPostalAdministrationDetailsVOs(
			HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs) {
		this.postalAdministrationDetailsVOs = postalAdministrationDetailsVOs;
	}
	public Collection<PostalAdministrationDetailsVO> getPaDetails() {
		return paDetails;
	}
	public void setPaDetails(Collection<PostalAdministrationDetailsVO> paDetails) {
		this.paDetails = paDetails;
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
	 * @return the autoEmailReqd
	 */
	public String getAutoEmailReqd() {
		return autoEmailReqd;
	}
	/**
	 * @param autoEmailReqd the autoEmailReqd to set
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
	public void setDueInDays(int dueInDays) {
		this.dueInDays = dueInDays;
	}
	/**
	 * 	Getter for dueInDays 
	 *	Added by : A-5219 on 08-Apr-2014
	 * 	Used for :
	 */
	public int getDueInDays() {
		return dueInDays;
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
	public double getTolerancePercent() {
		return tolerancePercent;
	}
	public void setTolerancePercent(double tolerancePercent) {
		this.tolerancePercent = tolerancePercent;
	}
	public double getToleranceValue() {
		return toleranceValue;
	}
	public void setToleranceValue(double toleranceValue) {
		this.toleranceValue = toleranceValue;
	}
	public double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	
	/**
	 *  @param 
	 * 	Getter for dupMailbagPeriod 
	 *	Added by : A-8353 on 05-Dec-2018
	 * 	Used for : ICRD-230449
	 */
	public int getDupMailbagPeriod() {
		return dupMailbagPeriod;
	}
	/**
	 *  @param dupMailbagPeriod the dupMailbagPeriod to set
	 * 	Setter for dupMailbagPeriod 
	 *	Added by : A-8353 on 05-Dec-2018
	 * 	Used for : ICRD-230449
	 */
	public void setDupMailbagPeriod(int dupMailbagPeriod) {
		this.dupMailbagPeriod = dupMailbagPeriod;
	}
	/**
	 * 	Getter for isPASSPA 
	 *	Added by : A-8061 on 10-Jun-2021
	 * 	Used for :
	 */
	public boolean isPASSPA() {
		return isPASSPA;
	}
	/**
	 *  @param isPASSPA the isPASSPA to set
	 * 	Setter for isPASSPA 
	 *	Added by : A-8061 on 10-Jun-2021
	 * 	Used for :
	 */
	public void setPASSPA(boolean isPASSPA) {
		this.isPASSPA = isPASSPA;
	}



}
