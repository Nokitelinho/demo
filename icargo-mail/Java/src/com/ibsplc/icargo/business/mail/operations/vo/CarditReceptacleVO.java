/*
 * CarditReceptacleVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *
 * @author A-5991
 *
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 30,  2016 A-5991 First Draft
 */
public class CarditReceptacleVO extends AbstractVO {

	private String receptacleId;

	/**
	 * The origin office of exchange
	 */
	private String originExchangeOffice;

	/**
	 * The destination office of exchange
	 */
	private String destinationExchangeOffice;

	/**
	 * The category of mail in despatch
	 */
	private String mailCategoryCode;

	/**
	 * The last digit of the year
	 */
	private int lastDigitOfYear;

	/**
	 * The weight of the receptacle
	 *
	 */
	//private double receptacleWeight;
	private Measure receptacleWeight;//added by A-7371

	/**
	 * String indicating whether this is the highest numbered receptacle in the
	 * container
	 */
	private String highestNumberReceptacleIndicator;

	/**
	 * String indicating whether receptacle contains registered or insured items
	 */
	private String regdOrInsuredIndicator;

	/**
	 * The mail sub class of the despatch
	 */
	private String mailSubClassCode;

	/**
	 * The serial number of the despatch
	 */
	private String despatchNumber;

	/**
	 * The receptacle serial number in the despatch
	 */
	private String receptacleSerialNumber;

	/**
	 * Receptacle handling class
	 */
	private String handlingClass;

	/**
	 * Code list responsible agency, coded
	 */
	private String codeListResponsibleAgency;

	/**
	 * Receptacle dangerous goods indicator
	 */
	private String dangerousGoodsIndicator;

	/**
	 * Package identification
	 */
	private String referenceQualifier;

	/**
	 * Type pf receptacle
	 */
	private String receptacleType;

	/**
	 * The despatch identification information
	 */
	private String despatchIdentification;

	/**
	 * Measurement value
	 */
	private String measurementApplicationQualifier;

	/**
	 * Measure unit qualifier -Kilogramme
	 */
	private String measureUnitQualifier;

	/**
	 * Measurement dimension, coded
	 */
	private String receptacleWeightType;

	/**
	 * Document name coded
	 */
	private String documentOrMessageNameCode;

	/**
	 * Last udpate user
	 */
	private String lastUpdateUser;

	/**
	 * The CDTTYP : CarditType (Message Function)
	 */
	private String carditType;
	private String pkgType;
	private String mailBagId;
	/**
	 * The RCPSTA : ReceptacleStatus (Message Function)
	 */
	private String receptacleStatus;

	/**
	 * The UPDTIM : UpdatedTime (Message Function)
	 */
	private LocalDate updatedTime;


	private String carditKey;
	private long mailSeqNum;
	private LocalDate reqDeliveryTime;//Added as part of ICRD-214795
	private String mailOrigin;
	private String mailDestination;

	//Added by A-8672 for IASCB-56964
    private LocalDate handoverTime;
    private String sealNumber;
    //Added by A-9951 for IASCB-139851
    private String masterDocumentNumber;
    private int ownerId;
    private int duplicateNumber;
    private int sequenceNumber;
	/**
	 * @return Returns the mailBagId.
	 */
	public String getMailBagId() {
		return mailBagId;
	}

	/**
	 * @param mailBagId The mailBagId to set.
	 */
	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}

	/**
	 * @return Returns the pkgType.
	 */
	public String getPkgType() {
		return pkgType;
	}

	/**
	 * @param pkgType The pkgType to set.
	 */
	public void setPkgType(String pkgType) {
		this.pkgType = pkgType;
	}

	/**
	 * @return Returns the codeListResponsibleAgency.
	 */
	public String getCodeListResponsibleAgency() {
		return codeListResponsibleAgency;
	}

	/**
	 * @param codeListResponsibleAgency
	 *            The codeListResponsibleAgency to set.
	 */
	public void setCodeListResponsibleAgency(String codeListResponsibleAgency) {
		this.codeListResponsibleAgency = codeListResponsibleAgency;
	}

	/**
	 * @return Returns the dangerousGoodsIndicator.
	 */
	public String getDangerousGoodsIndicator() {
		return dangerousGoodsIndicator;
	}

	/**
	 * @param dangerousGoodsIndicator
	 *            The dangerousGoodsIndicator to set.
	 */
	public void setDangerousGoodsIndicator(String dangerousGoodsIndicator) {
		this.dangerousGoodsIndicator = dangerousGoodsIndicator;
	}

	/**
	 * @return Returns the despatchIdentification.
	 */
	public String getDespatchIdentification() {
		return despatchIdentification;
	}

	/**
	 * @param despatchIdentification
	 *            The despatchIdentification to set.
	 */
	public void setDespatchIdentification(String despatchIdentification) {
		this.despatchIdentification = despatchIdentification;
	}

	/**
	 * @return Returns the despatchNumber.
	 */
	public String getDespatchNumber() {
		return despatchNumber;
	}

	/**
	 * @param despatchNumber
	 *            The despatchNumber to set.
	 */
	public void setDespatchNumber(String despatchNumber) {
		this.despatchNumber = despatchNumber;
	}

	/**
	 * @return Returns the destinationExchangeOffice.
	 */
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}

	/**
	 * @param destinationExchangeOffice
	 *            The destinationExchangeOffice to set.
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}

	/**
	 * @return Returns the documentOrMessageNameCode.
	 */
	public String getDocumentOrMessageNameCode() {
		return documentOrMessageNameCode;
	}

	/**
	 * @param documentOrMessageNameCode
	 *            The documentOrMessageNameCode to set.
	 */
	public void setDocumentOrMessageNameCode(String documentOrMessageNameCode) {
		this.documentOrMessageNameCode = documentOrMessageNameCode;
	}

	/**
	 * @return Returns the handlingClass.
	 */
	public String getHandlingClass() {
		return handlingClass;
	}

	/**
	 * @param handlingClass
	 *            The handlingClass to set.
	 */
	public void setHandlingClass(String handlingClass) {
		this.handlingClass = handlingClass;
	}

	/**
	 * @return Returns the highestNumberReceptacleIndicator.
	 */
	public String getHighestNumberReceptacleIndicator() {
		return highestNumberReceptacleIndicator;
	}

	/**
	 * @param highestNumberReceptacleIndicator
	 *            The highestNumberReceptacleIndicator to set.
	 */
	public void setHighestNumberReceptacleIndicator(
			String highestNumberReceptacleIndicator) {
		this.highestNumberReceptacleIndicator = highestNumberReceptacleIndicator;
	}

	/**
	 * @return Returns the lastDigitOfYear.
	 */
	public int getLastDigitOfYear() {
		return lastDigitOfYear;
	}

	/**
	 * @param lastDigitOfYear
	 *            The lastDigitOfYear to set.
	 */
	public void setLastDigitOfYear(int lastDigitOfYear) {
		this.lastDigitOfYear = lastDigitOfYear;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubClassCode.
	 */
	public String getMailSubClassCode() {
		return mailSubClassCode;
	}

	/**
	 * @param mailSubClassCode
	 *            The mailSubClassCode to set.
	 */
	public void setMailSubClassCode(String mailSubClassCode) {
		this.mailSubClassCode = mailSubClassCode;
	}

	/**
	 * @return Returns the measurementApplicationQualifier.
	 */
	public String getMeasurementApplicationQualifier() {
		return measurementApplicationQualifier;
	}

	/**
	 * @param measurementApplicationQualifier
	 *            The measurementApplicationQualifier to set.
	 */
	public void setMeasurementApplicationQualifier(
			String measurementApplicationQualifier) {
		this.measurementApplicationQualifier = measurementApplicationQualifier;
	}

	/**
	 * @return Returns the measureUnitQualifier.
	 */
	public String getMeasureUnitQualifier() {
		return measureUnitQualifier;
	}

	/**
	 * @param measureUnitQualifier
	 *            The measureUnitQualifier to set.
	 */
	public void setMeasureUnitQualifier(String measureUnitQualifier) {
		this.measureUnitQualifier = measureUnitQualifier;
	}

	/**
	 * @return Returns the originExchangeOffice.
	 */
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}

	/**
	 * @param originExchangeOffice
	 *            The originExchangeOffice to set.
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}

	/**
	 * @return Returns the receptacleId.
	 */
	public String getReceptacleId() {
		return receptacleId;
	}

	/**
	 * @param receptacleId
	 *            The receptacleId to set.
	 */
	public void setReceptacleId(String receptacleId) {
		this.receptacleId = receptacleId;
	}

	/**
	 * @return Returns the receptacleNumber.
	 */
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	/**
	 * @param receptacleNumber
	 *            The receptacleNumber to set.
	 */
	public void setReceptacleSerialNumber(String receptacleNumber) {
		this.receptacleSerialNumber = receptacleNumber;
	}

	/**
	 * @return Returns the receptacleType.
	 */
	public String getReceptacleType() {
		return receptacleType;
	}

	/**
	 * @param receptacleType
	 *            The receptacleType to set.
	 */
	public void setReceptacleType(String receptacleType) {
		this.receptacleType = receptacleType;
	}
	//added by A-7371
/**
 *
 * @return receptacleWeight
 */
	public Measure getReceptacleWeight() {
		return receptacleWeight;
	}
/**
 *
 * @param receptacleWeight
 */
	public void setReceptacleWeight(Measure receptacleWeight) {
		this.receptacleWeight = receptacleWeight;
	}

	/**
	 * @return Returns the receptacleWeight.
	 */
	/*public double getReceptacleWeight() {
		return receptacleWeight;
	}

	*//**
	 * @param receptacleWeight
	 *            The receptacleWeight to set.
	 *//*
	public void setReceptacleWeight(double receptacleWeight) {
		this.receptacleWeight = receptacleWeight;
	}
*/
	/**
	 * @return Returns the receptacleWeightType.
	 */
	public String getReceptacleWeightType() {
		return receptacleWeightType;
	}

	/**
	 * @param receptacleWeightType
	 *            The receptacleWeightType to set.
	 */
	public void setReceptacleWeightType(String receptacleWeightType) {
		this.receptacleWeightType = receptacleWeightType;
	}

	/**
	 * @return Returns the referenceQualifier.
	 */
	public String getReferenceQualifier() {
		return referenceQualifier;
	}

	/**
	 * @param referenceQualifier
	 *            The referenceQualifier to set.
	 */
	public void setReferenceQualifier(String referenceQualifier) {
		this.referenceQualifier = referenceQualifier;
	}

	/**
	 * @return Returns the regdOrInsuredIndicator.
	 */
	public String getRegdOrInsuredIndicator() {
		return regdOrInsuredIndicator;
	}

	/**
	 * @param regdOrInsuredIndicator
	 *            The regdOrInsuredIndicator to set.
	 */
	public void setRegdOrInsuredIndicator(String regdOrInsuredIndicator) {
		this.regdOrInsuredIndicator = regdOrInsuredIndicator;
	}

	/**
	 * @return the carditType
	 */
	public String getCarditType() {
		return carditType;
	}

	/**
	 * @param carditType the carditType to set
	 */
	public void setCarditType(String carditType) {
		this.carditType = carditType;
	}

	/**
	 * @return the receptacleStatus
	 */
	public String getReceptacleStatus() {
		return receptacleStatus;
	}

	/**
	 * @param receptacleStatus the receptacleStatus to set
	 */
	public void setReceptacleStatus(String receptacleStatus) {
		this.receptacleStatus = receptacleStatus;
	}

	/**
	 * @return the updatedTime
	 */
	public LocalDate getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * @param updatedTime the updatedTime to set
	 */
	public void setUpdatedTime(LocalDate updatedTime) {
		this.updatedTime = updatedTime;
	}
	/**
	 * @return the carditKey
	 */
	public String getCarditKey() {
		return carditKey;
	}
	/**
	 * @param carditKey the carditKey to set
	 */
	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}
	public long getMailSeqNum() {
		return mailSeqNum;
	}
	public void setMailSeqNum(long mailSeqNum) {
		this.mailSeqNum = mailSeqNum;
	}

	/**
	 * 	Getter for reqDeliveryTime
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public LocalDate getReqDeliveryTime() {
		return reqDeliveryTime;
	}

	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}

	/**
	 * @return the mailOrigin
	 */
	public String getMailOrigin() {
		return mailOrigin;
	}

	/**
	 * @param mailOrigin the MailOrigin to set
	 */
	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}

	/**
	 * @return the mailDestination
	 */
	public String getMailDestination() {
		return mailDestination;
	}

	/**
	 * @param mailDestination the MailDestination to set
	 */
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	/**
	 * @return the handoverTime
	 */
	public LocalDate getHandoverTime() {
		return handoverTime;
	}
	/**
	 * @param handoverTime the handoverTime to set
	 */
	public void setHandoverTime(LocalDate handoverTime) {
		this.handoverTime = handoverTime;
	}
	/**
	 * 	Getter for sealNumber
	 *	Added by : A-5219 on 28-Nov-2020
	 * 	Used for :
	 */
	public String getSealNumber() {
		return sealNumber;
	}
	/**
	 *  @param sealNumber the sealNumber to set
	 * 	Setter for sealNumber
	 *	Added by : A-5219 on 28-Nov-2020
	 * 	Used for :
	 */
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public int getDuplicateNumber() {
		return duplicateNumber;
	}
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
}
