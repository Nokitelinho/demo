/*
 * CarditReceptacle.java Created on Jun 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * This entity persists the information about a receptacle in the Cardit
 * 
 * @author A-1739
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 7, 2006 A-1739 First Draft
 */
@Entity
@Table(name = "MALCDTRCP")
@Staleable
public class CarditReceptacle {
	
	private static final String MAIL_OPERATIONS = "mail.operations";

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
	private double receptacleWeight;

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
	 * The CDTTYP : CarditType (Message Function) 
	 */
	private String carditType;

	private Calendar handoverTime;
	private CarditReceptaclePK carditReceptaclePK;

	public CarditReceptacle() {

	}

	public CarditReceptacle(CarditPK carditPK,
			CarditReceptacleVO carditReceptacleVO) throws SystemException {
		populatePK(carditPK, carditReceptacleVO);
		populateAttributes(carditReceptacleVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
	}

	 /**
	 * Populates the PK fields
	 * 
	 * @param carditPK
	 * @param carditReceptacleVO
	 */
	public CarditReceptaclePK populatePK(CarditPK carditPK,
			CarditReceptacleVO carditReceptacleVO) {

		carditReceptaclePK = new CarditReceptaclePK();
		carditReceptaclePK.setCompanyCode(   carditPK.getCompanyCode());
		carditReceptaclePK.setCarditKey(   carditPK.getCarditKey());
		carditReceptaclePK.setReceptacleId(   carditReceptacleVO.getReceptacleId());

		return carditReceptaclePK;
	}

	/**
	 * @param carditReceptacleVO
	 */
	private void populateAttributes(CarditReceptacleVO carditReceptacleVO) {
		setCodeListResponsibleAgency(carditReceptacleVO
				.getCodeListResponsibleAgency());
		setDangerousGoodsIndicator(carditReceptacleVO
				.getDangerousGoodsIndicator());
		setDespatchIdentification(carditReceptacleVO
				.getDespatchIdentification());
		setDestinationExchangeOffice(carditReceptacleVO
				.getDestinationExchangeOffice());
		setDocumentOrMessageNameCode(carditReceptacleVO
				.getDocumentOrMessageNameCode());
		setHandlingClass(carditReceptacleVO.getHandlingClass());
		setHighestNumberReceptacleIndicator(carditReceptacleVO
				.getHighestNumberReceptacleIndicator());
		setLastDigitOfYear(carditReceptacleVO.getLastDigitOfYear());
		setMailCategoryCode(carditReceptacleVO.getMailCategoryCode());
		setMailSubClassCode(carditReceptacleVO.getMailSubClassCode());
		setMeasurementApplicationQualifier(carditReceptacleVO
				.getMeasurementApplicationQualifier());
		setMeasureUnitQualifier(carditReceptacleVO.getMeasureUnitQualifier());
		setOriginExchangeOffice(carditReceptacleVO.getOriginExchangeOffice());
		setReceptacleSerialNumber(carditReceptacleVO
				.getReceptacleSerialNumber());
		setDespatchNumber(carditReceptacleVO.getDespatchNumber());
		setReceptacleType(carditReceptacleVO.getReceptacleType());
		//setReceptacleWeight(carditReceptacleVO.getReceptacleWeight());
		setReceptacleWeight(carditReceptacleVO.getReceptacleWeight().getRoundedDisplayValue());//added by A-7371
		setReceptacleWeightType(carditReceptacleVO.getReceptacleWeightType());
		setReferenceQualifier(carditReceptacleVO.getReferenceQualifier());
		setRegdOrInsuredIndicator(carditReceptacleVO
				.getRegdOrInsuredIndicator());
		/*
		 * FOR M39
		 */
		setCarditType(carditReceptacleVO.getCarditType());
		if(carditReceptacleVO.getHandoverTime()!=null){
			setHandoverTime(carditReceptacleVO.getHandoverTime().toCalendar());
		}		
	}

	/**
	 * @author A-3227
	 * @param carditRcpPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static CarditReceptacle find(CarditReceptaclePK carditRcpPK)
	throws FinderException, SystemException{
		return PersistenceController.getEntityManager().find(CarditReceptacle.class, carditRcpPK);
	}
	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(),
					removeException);
		}
	}
	/**
	 * @return Returns the carditReceptaclePK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "carditKey", column = @Column(name = "CDTKEY")),
		@AttributeOverride(name = "receptacleId", column = @Column(name = "RCPIDR"))})
	public CarditReceptaclePK getCarditReceptaclePK() {
		return carditReceptaclePK;
	}

	/**
	 * @param carditReceptaclePK
	 *            The carditReceptaclePK to set.
	 */
	public void setCarditReceptaclePK(CarditReceptaclePK carditReceptaclePK) {
		this.carditReceptaclePK = carditReceptaclePK;
	}

	/**
	 * @return Returns the codeListResponsibleAgency.
	 */
	@Column(name = "CODLSTAGY")
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
	@Column(name = "DGRGDSIND")
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
	@Column(name = "DSPIDR")
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
	@Column(name = "DSPSRLNUM")
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
	@Column(name = "DSTEXGOFF")
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}

	/**
	 * @param destinationExchangeOffice
	 *            The destinationExchangeOffice to set.
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice =  destinationExchangeOffice;
	}

	/**
	 * @return Returns the documentOrMessageNameCode.
	 */
	@Column(name = "DOCMSGNAM")
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
	@Column(name = "HNDCLS")
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
	@Column(name = "HSTRCPNUM")
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
	@Column(name = "DSPYER")
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
	 * @return Returns the mailCategoryCode.
	 */
	@Column(name = "MALCTG")
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
	@Column(name = "MALSUBCLS")
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
	@Column(name = "MMTAPNQLF")
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
	@Column(name = "MMTUNTQLF")
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
	@Column(name = "ORGEXGOFF")
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}

	/**
	 * @param originExchangeOffice
	 *            The originExchangeOffice to set.
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice =  originExchangeOffice;
	}

	/**
	 * @return Returns the receptacleNumber.
	 */
	@Column(name = "RCPSRLNUM")
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
	@Column(name = "RCPTYP")
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

	/**
	 * @return Returns the receptacleWeight.
	 */
	@Column(name = "RCPWGT")
	public double getReceptacleWeight() {
		return receptacleWeight;
	}

	/**
	 * @param receptacleWeight
	 *            The receptacleWeight to set.
	 */
	public void setReceptacleWeight(double receptacleWeight) {
		this.receptacleWeight = receptacleWeight;
	}

	/**
	 * @return Returns the receptacleWeightType.
	 */
	@Column(name = "WGTTYP")
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
	@Column(name = "REFQLF")
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
	@Column(name = "RCPRGDINS")
	public String getRegdOrInsuredIndicator() {
		return regdOrInsuredIndicator;
	}
	/**
	 * @return the carditType
	 */
	@Column(name="CDTTYP")
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
	 * @param regdOrInsuredIndicator
	 *            The regdOrInsuredIndicator to set.
	 */
	public void setRegdOrInsuredIndicator(String regdOrInsuredIndicator) {
		this.regdOrInsuredIndicator = regdOrInsuredIndicator;
	}
	/**
	 * @return the handoverTime
	 */
	@Column(name="MALHNDTIM")
	public Calendar getHandoverTime() {
		return handoverTime;
	}
	/**
	 * @param handoverTime the handoverTime to set
	 */
	public void setHandoverTime(Calendar handoverTime) {
		this.handoverTime = handoverTime;
	}
	
	/**
	 * @author A-2553
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(MAIL_OPERATIONS));
		} catch (PersistenceException exception) {
			throw new SystemException("Query DAO not found", exception);
		}
	}
	
	/**
	 * @author a-2553
	 * @param carditEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<MailbagVO> findCarditMails(
			CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws SystemException {
		try{
			return constructDAO().findCarditMails(
				carditEnquiryFilterVO, pageNumber);
		}catch(PersistenceException exception){
			throw new SystemException("findcarditmails");
		}

	}

	/**
	 * @author 
	 * @param mailbagID
	 * @return
	 * @throws SystemException
	 */
	public static CarditReceptacleVO findDuplicateMailbagsInCardit(
			String companyCode,String mailbagID)
			throws SystemException {
		try{
			return constructDAO().findDuplicateMailbagsInCardit(
					companyCode,mailbagID);
		}catch(PersistenceException exception){
			throw new SystemException("findDuplicateMailbagsInCardit");
		}
	}	
   /**
    * @author A-7929
    * @param mailbagEnquiryFilterVO
    * @param pageNumber
    * @return
    */
	public static Page<MailbagVO> findMailbagsForTruckFlight(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
			int pageNumber) throws SystemException {
		try{
			return constructDAO().findMailbagsForTruckFlight(
					mailbagEnquiryFilterVO, pageNumber);
		}catch(SystemException exception){
			throw new SystemException("findcarditmails");
		}
	}
}
	

