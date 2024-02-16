/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempMailBagDetails.java
 *
 *	Created by	:	A-6287
 *	Created on	:	02-Mar-2020
 *
 *  Copyright 2020 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReceptacleInformationMessageVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempMailBagDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6287	:	02-Mar-2020	:	Draft
 */

@Entity
@Table(name = "MALCDTMSGRCPTMP")
@Staleable
public class CarditTempMailBagDetails {

	private String NumberOfPackages;
	private String ReceptacleType;
	private String PkgType;
	private String ReferenceQualifier;
	private String ReceptacleID;
	private String DangerousGoodsIndicator;
	private String InsuranceIndicator;
	private String HandlingClass;
	private String CodeListResponsibleAgency;
	private String MeasurementApplicationQualifier;
	private String ReceptacleWeightType;
	private String MeasureUnitQualifier;
	private String DocumentOrMessageNameCode;
	private String DespatchIdentification;
	private String OriginExchangeOffice;
	private String DestinationExchangeOffice;
	private String ReceptacleInfoMailCategoryCode;
	private String MailSubClassCode;
	private String LastDigitOfYear;
	private String DespatchNumber;
	private String ReceptacleSerialNumber;
	private String HighestNumberReceptacleIndicator;
	private String RegdOrInsuredIndicator;
	private String ReceptacleWeight;
	private CarditTempMailBagDetailsPK carditTempMailBagDetailsPK;
	private String sealNumber;

	/**
	 * 	Getter for carditTempMailBagDetailsPK
	 *	Added by : A-6287 on 02-Mar-2020
	 * 	Used for :
	 */
	@EmbeddedId
	@AttributeOverrides({
	@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	@AttributeOverride(name="sequenceNumber",column=@Column(name="SEQNUM")),
	@AttributeOverride(name="ConsignmentIdentifier",column=@Column(name="CNSMNTIDR")),
	@AttributeOverride(name="DRTagNo",column=@Column(name="DRTAGNUM"))})
	public CarditTempMailBagDetailsPK getCarditTempMailBagDetailsPK() {
		return carditTempMailBagDetailsPK;
	}

	/**
	 *  @param carditTempMailBagDetailsPK the carditTempMailBagDetailsPK to set
	 * 	Setter for carditTempMailBagDetailsPK
	 *	Added by : A-6287 on 02-Mar-2020
	 * 	Used for :
	 */
	public void setCarditTempMailBagDetailsPK(CarditTempMailBagDetailsPK carditTempMailBagDetailsPK) {
		this.carditTempMailBagDetailsPK = carditTempMailBagDetailsPK;
	}

	/**
	 * 	Getter for numberOfPackages
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="NUMPKG")
	public String getNumberOfPackages() {
		return NumberOfPackages;
	}

	/**
	 *  @param numberOfPackages the numberOfPackages to set
	 * 	Setter for numberOfPackages
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setNumberOfPackages(String numberOfPackages) {
		NumberOfPackages = numberOfPackages;
	}

	/**
	 * 	Getter for receptacleType
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="RCPTYP")
	public String getReceptacleType() {
		return ReceptacleType;
	}

	/**
	 *  @param receptacleType the receptacleType to set
	 * 	Setter for receptacleType
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setReceptacleType(String receptacleType) {
		ReceptacleType = receptacleType;
	}

	/**
	 * 	Getter for pkgType
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="PKGTYP")
	public String getPkgType() {
		return PkgType;
	}

	/**
	 *  @param pkgType the pkgType to set
	 * 	Setter for pkgType
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setPkgType(String pkgType) {
		PkgType = pkgType;
	}

	/**
	 * 	Getter for referenceQualifier
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="REFQFR")
	public String getReferenceQualifier() {
		return ReferenceQualifier;
	}

	/**
	 *  @param referenceQualifier the referenceQualifier to set
	 * 	Setter for referenceQualifier
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setReferenceQualifier(String referenceQualifier) {
		ReferenceQualifier = referenceQualifier;
	}

	/**
	 * 	Getter for receptacleID
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="RCPIDR")
	public String getReceptacleID() {
		return ReceptacleID;
	}

	/**
	 *  @param receptacleID the receptacleID to set
	 * 	Setter for receptacleID
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setReceptacleID(String receptacleID) {
		ReceptacleID = receptacleID;
	}

	/**
	 * 	Getter for dangerousGoodsIndicator
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DGRIND")
	public String getDangerousGoodsIndicator() {
		return DangerousGoodsIndicator;
	}

	/**
	 *  @param dangerousGoodsIndicator the dangerousGoodsIndicator to set
	 * 	Setter for dangerousGoodsIndicator
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDangerousGoodsIndicator(String dangerousGoodsIndicator) {
		DangerousGoodsIndicator = dangerousGoodsIndicator;
	}

	/**
	 * 	Getter for insuranceIndicator
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="INSIND")
	public String getInsuranceIndicator() {
		return InsuranceIndicator;
	}

	/**
	 *  @param insuranceIndicator the insuranceIndicator to set
	 * 	Setter for insuranceIndicator
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setInsuranceIndicator(String insuranceIndicator) {
		InsuranceIndicator = insuranceIndicator;
	}

	/**
	 * 	Getter for handlingClass
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDCLS")
	public String getHandlingClass() {
		return HandlingClass;
	}

	/**
	 *  @param handlingClass the handlingClass to set
	 * 	Setter for handlingClass
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandlingClass(String handlingClass) {
		HandlingClass = handlingClass;
	}

	/**
	 * 	Getter for codeListResponsibleAgency
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CODLSTAGY")
	public String getCodeListResponsibleAgency() {
		return CodeListResponsibleAgency;
	}

	/**
	 *  @param codeListResponsibleAgency the codeListResponsibleAgency to set
	 * 	Setter for codeListResponsibleAgency
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setCodeListResponsibleAgency(String codeListResponsibleAgency) {
		CodeListResponsibleAgency = codeListResponsibleAgency;
	}

	/**
	 * 	Getter for measurementApplicationQualifier
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MSRAPPQFR")
	public String getMeasurementApplicationQualifier() {
		return MeasurementApplicationQualifier;
	}

	/**
	 *  @param measurementApplicationQualifier the measurementApplicationQualifier to set
	 * 	Setter for measurementApplicationQualifier
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMeasurementApplicationQualifier(String measurementApplicationQualifier) {
		MeasurementApplicationQualifier = measurementApplicationQualifier;
	}

	/**
	 * 	Getter for receptacleWeightType
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="RCPWGTTYP")
	public String getReceptacleWeightType() {
		return ReceptacleWeightType;
	}

	/**
	 *  @param receptacleWeightType the receptacleWeightType to set
	 * 	Setter for receptacleWeightType
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setReceptacleWeightType(String receptacleWeightType) {
		ReceptacleWeightType = receptacleWeightType;
	}

	/**
	 * 	Getter for measureUnitQualifier
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MSRUNTQFR")
	public String getMeasureUnitQualifier() {
		return MeasureUnitQualifier;
	}

	/**
	 *  @param measureUnitQualifier the measureUnitQualifier to set
	 * 	Setter for measureUnitQualifier
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMeasureUnitQualifier(String measureUnitQualifier) {
		MeasureUnitQualifier = measureUnitQualifier;
	}

	/**
	 * 	Getter for documentOrMessageNameCode
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DOCMSGNAMCOD")
	public String getDocumentOrMessageNameCode() {
		return DocumentOrMessageNameCode;
	}

	/**
	 *  @param documentOrMessageNameCode the documentOrMessageNameCode to set
	 * 	Setter for documentOrMessageNameCode
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDocumentOrMessageNameCode(String documentOrMessageNameCode) {
		DocumentOrMessageNameCode = documentOrMessageNameCode;
	}

	/**
	 * 	Getter for despatchIdentification
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DSPIDN")
	public String getDespatchIdentification() {
		return DespatchIdentification;
	}

	/**
	 *  @param despatchIdentification the despatchIdentification to set
	 * 	Setter for despatchIdentification
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDespatchIdentification(String despatchIdentification) {
		DespatchIdentification = despatchIdentification;
	}

	/**
	 * 	Getter for originExchangeOffice
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="ORGEXEOFF")
	public String getOriginExchangeOffice() {
		return OriginExchangeOffice;
	}

	/**
	 *  @param originExchangeOffice the originExchangeOffice to set
	 * 	Setter for originExchangeOffice
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		OriginExchangeOffice = originExchangeOffice;
	}

	/**
	 * 	Getter for destinationExchangeOffice
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DSTEXEOFF")
	public String getDestinationExchangeOffice() {
		return DestinationExchangeOffice;
	}

	/**
	 *  @param destinationExchangeOffice the destinationExchangeOffice to set
	 * 	Setter for destinationExchangeOffice
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		DestinationExchangeOffice = destinationExchangeOffice;
	}

	/**
	 * 	Getter for receptacleInfoMailCategoryCode
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="RCPMALCATCOD")
	public String getReceptacleInfoMailCategoryCode() {
		return ReceptacleInfoMailCategoryCode;
	}

	/**
	 *  @param receptacleInfoMailCategoryCode the receptacleInfoMailCategoryCode to set
	 * 	Setter for receptacleInfoMailCategoryCode
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setReceptacleInfoMailCategoryCode(String receptacleInfoMailCategoryCode) {
		ReceptacleInfoMailCategoryCode = receptacleInfoMailCategoryCode;
	}

	/**
	 * 	Getter for mailSubClassCode
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MALSUBCLSCOD")
	public String getMailSubClassCode() {
		return MailSubClassCode;
	}

	/**
	 *  @param mailSubClassCode the mailSubClassCode to set
	 * 	Setter for mailSubClassCode
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMailSubClassCode(String mailSubClassCode) {
		MailSubClassCode = mailSubClassCode;
	}

	/**
	 * 	Getter for lastDigitOfYear
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="LSTDGTYAR")
	public String getLastDigitOfYear() {
		return LastDigitOfYear;
	}

	/**
	 *  @param lastDigitOfYear the lastDigitOfYear to set
	 * 	Setter for lastDigitOfYear
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setLastDigitOfYear(String lastDigitOfYear) {
		LastDigitOfYear = lastDigitOfYear;
	}

	/**
	 * 	Getter for despatchNumber
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DSPNUM")
	public String getDespatchNumber() {
		return DespatchNumber;
	}

	/**
	 *  @param despatchNumber the despatchNumber to set
	 * 	Setter for despatchNumber
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDespatchNumber(String despatchNumber) {
		DespatchNumber = despatchNumber;
	}

	/**
	 * 	Getter for receptacleSerialNumber
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="RCPSERNUM")
	public String getReceptacleSerialNumber() {
		return ReceptacleSerialNumber;
	}

	/**
	 *  @param receptacleSerialNumber the receptacleSerialNumber to set
	 * 	Setter for receptacleSerialNumber
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		ReceptacleSerialNumber = receptacleSerialNumber;
	}

	/**
	 * 	Getter for highestNumberReceptacleIndicator
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HGHNUMRCPIND")
	public String getHighestNumberReceptacleIndicator() {
		return HighestNumberReceptacleIndicator;
	}

	/**
	 *  @param highestNumberReceptacleIndicator the highestNumberReceptacleIndicator to set
	 * 	Setter for highestNumberReceptacleIndicator
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHighestNumberReceptacleIndicator(String highestNumberReceptacleIndicator) {
		HighestNumberReceptacleIndicator = highestNumberReceptacleIndicator;
	}

	/**
	 * 	Getter for regdOrInsuredIndicator
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="RGDINSIND")
	public String getRegdOrInsuredIndicator() {
		return RegdOrInsuredIndicator;
	}

	/**
	 *  @param regdOrInsuredIndicator the regdOrInsuredIndicator to set
	 * 	Setter for regdOrInsuredIndicator
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setRegdOrInsuredIndicator(String regdOrInsuredIndicator) {
		RegdOrInsuredIndicator = regdOrInsuredIndicator;
	}

	/**
	 * 	Getter for receptacleWeight
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="RCPWGT")
	public String getReceptacleWeight() {
		return ReceptacleWeight;
	}

	/**
	 *  @param receptacleWeight the receptacleWeight to set
	 * 	Setter for receptacleWeight
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setReceptacleWeight(String receptacleWeight) {
		ReceptacleWeight = receptacleWeight;
	}

	public CarditTempMailBagDetails(){}

	/**
	 *	Constructor	: 	@param carditTempDetailsPK
	 *	Constructor	: 	@param tmpVO
	 *	Created by	:	A-6287
	 *	Created on	:	02-Mar-2020
	 * @throws SystemException
	 * @throws CreateException
	 */
	public CarditTempMailBagDetails(CarditTempDetailsPK carditTempDetailsPK, ReceptacleInformationMessageVO rcpInfoVO) throws SystemException{
		populateAttributes(carditTempDetailsPK,rcpInfoVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException | SystemException e) {

			throw new SystemException(e.getMessage());
		}

	}

	/**
	 * 	Method		:	CarditTempMailBagDetails.populateAttributes
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param tmpVO
	 *	Return type	: 	void
	 * @throws SystemException
	 * @throws CreateException
	 */
	private void populateAttributes(CarditTempDetailsPK carditTempDetailsPK, ReceptacleInformationMessageVO rcpInfoVO){

		CarditTempMailBagDetailsPK mailBagPK = null;

		mailBagPK = new CarditTempMailBagDetailsPK();
		mailBagPK.setCompanyCode(carditTempDetailsPK.getCompanyCode());
		mailBagPK.setSequenceNumber(carditTempDetailsPK.getSequenceNumber());
		mailBagPK.setConsignmentIdentifier(carditTempDetailsPK.getConsignmentIdentifier());
		mailBagPK.setDRTagNo(rcpInfoVO.getDRTagNo());
		setCarditTempMailBagDetailsPK(mailBagPK);
		setSealNumber(rcpInfoVO.getSealNumber());
		setReferenceQualifier(rcpInfoVO.getReferenceQualifier());
		setReceptacleID(rcpInfoVO.getReceptacleID());
		setDangerousGoodsIndicator(rcpInfoVO.getDangerousGoodsIndicator());
		setInsuranceIndicator(rcpInfoVO.getInsuranceIndicator());
		setHandlingClass(rcpInfoVO.getHandlingClass());
		setCodeListResponsibleAgency(rcpInfoVO.getCodeListResponsibleAgency());
		setMeasurementApplicationQualifier(rcpInfoVO.getMeasurementApplicationQualifier());
		setReceptacleWeightType(rcpInfoVO.getReceptacleWeightType());
		setMeasureUnitQualifier(rcpInfoVO.getMeasureUnitQualifier());
		setDocumentOrMessageNameCode(rcpInfoVO.getDocumentOrMessageNameCode());
		setDespatchIdentification(rcpInfoVO.getDespatchIdentification());
		if (rcpInfoVO.getReceptacleVO() != null) {
			setOriginExchangeOffice(rcpInfoVO.getReceptacleVO().getOriginExchangeOffice());
			setDestinationExchangeOffice(rcpInfoVO.getReceptacleVO().getDestinationExchangeOffice());
			setReceptacleInfoMailCategoryCode(rcpInfoVO.getReceptacleVO().getMailCategoryCode());
			setMailSubClassCode(rcpInfoVO.getReceptacleVO().getMailSubClassCode());
			setLastDigitOfYear(Integer.toString(rcpInfoVO.getReceptacleVO().getLastDigitOfYear()));
			setDespatchNumber(rcpInfoVO.getReceptacleVO().getDespatchNumber());
			setReceptacleSerialNumber(rcpInfoVO.getReceptacleVO().getReceptacleSerialNumber());
			setHighestNumberReceptacleIndicator(rcpInfoVO.getReceptacleVO().getHighestNumberReceptacleIndicator());
			setRegdOrInsuredIndicator(rcpInfoVO.getReceptacleVO().getRegdOrInsuredIndicator());
			setReceptacleWeight(Double.toString(rcpInfoVO.getReceptacleVO().getReceptacleWeight()));
		}

	}
	/**
	 * 	Getter for sealNumber
	 *	Added by : A-5219 on 28-Nov-2020
	 * 	Used for :
	 */
	@Column(name="CONSELNUM")
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


}
