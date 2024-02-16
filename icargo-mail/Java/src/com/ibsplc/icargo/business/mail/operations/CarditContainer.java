				 
/*
 * CarditContainer.java Created on Jun 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */	

package com.ibsplc.icargo.business.mail.operations;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.vo.CarditContainerVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This entity persists the information of each container in a cardit
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
 *  		  July 16, 2007			A-1739		EJB3 Final changes
 */
@Entity
@Table(name = "MALCDTCON")
@Staleable
public class CarditContainer {

	/**
	 * Equipment qualifier code
	 */
	private String equipmentQualifier;

	/**
	 * Code list responsible agency coded
	 */
	private String codeListResponsibleAgency;

	/**
	 * Equipment size and type, identification
	 */
	private String containerType;

	/**
	 * Code list responsible agency , coded
	 */
	private String typeCodeListResponsibleAgency;

	/**
	 * Measurement dimensions, coded-Unit net weight
	 */
	private String measurementDimension;

	/**
	 * container weight If value is null default value is set as -1
	 */
	private double containerWeight;

	/**
	 * Container seal number
	 */
	private String sealNumber;
	
	/**
	 * Completion Status
	 */
	private String completionStatus;
	
	/**
	 * The CDTTYP : CarditType (Message Function) 
	 */
	private String carditType;

	/**
	 * Container Journey Identifier
	 */
	private String containerJourneyIdentifier;
	
	
	private CarditContainerPK carditContainerPK;

	public CarditContainer() {

	}

	public CarditContainer(CarditPK carditPK,
			CarditContainerVO carditContainerVO) throws SystemException {
		populatePK(carditPK, carditContainerVO);
		populateAttributes(carditContainerVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}

	/**
	 * @param carditPK
	 * @param carditContainerVO
	 */
	private void populatePK(CarditPK carditPK,
			CarditContainerVO carditContainerVO) {
		carditContainerPK = new CarditContainerPK();
		carditContainerPK.setCompanyCode(   carditPK.getCompanyCode());
		carditContainerPK.setCarditKey(   carditPK.getCarditKey());
		carditContainerPK.setContainerNumber(   carditContainerVO
				.getContainerNumber());
	}

	/**
	 * @param carditContainerVO
	 */
	private void populateAttributes(CarditContainerVO carditContainerVO) {
		setCodeListResponsibleAgency(carditContainerVO
				.getCodeListResponsibleAgency());
		setContainerType(carditContainerVO.getContainerType());
		
if(carditContainerVO.getContainerWeight()!=null){//ICRD-313536
		setContainerWeight(Double.parseDouble(carditContainerVO.getContainerWeight().getFormattedSystemValue()));//added by A-7371
		}
		
		setEquipmentQualifier(carditContainerVO.getEquipmentQualifier());
		setMeasurementDimension(carditContainerVO.getMeasurementDimension());
		setSealNumber(carditContainerVO.getSealNumber());
		setTypeCodeListResponsibleAgency(carditContainerVO
				.getTypeCodeListResponsibleAgency());
		setCompletionStatus("N");
		/*
		 * FOR M39
		 */
		setCarditType(carditContainerVO.getCarditType());
		setContainerJourneyIdentifier(carditContainerVO.getContainerJourneyIdentifier());
	}
	/**
	 * @author A-3227
	 * @param carditContPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static CarditContainer find(CarditContainerPK carditContPK)
	throws FinderException, SystemException{
		return PersistenceController.getEntityManager().find(CarditContainer.class, carditContPK);
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
	 * @return Returns the codeListResponsibleAgency.
	 */
	@Column(name = "NUMCODLSTAGY")
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
	 * @return Returns the containerType.
	 */
	@Column(name = "TYPCOD")
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType
	 *            The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the containerWeight.
	 */
	@Column(name = "NETWGT")
	public double getContainerWeight() {
		return containerWeight;
	}

	/**
	 * @param containerWeight
	 *            The containerWeight to set.
	 */
	public void setContainerWeight(double containerWeight) {
		this.containerWeight = containerWeight;
	}

	/**
	 * @return Returns the equipmentQualifier.
	 */
	@Column(name = "EQPQLF")
	public String getEquipmentQualifier() {
		return equipmentQualifier;
	}

	/**
	 * @param equipmentQualifier
	 *            The equipmentQualifier to set.
	 */
	public void setEquipmentQualifier(String equipmentQualifier) {
		this.equipmentQualifier = equipmentQualifier;
	}

	/**
	 * @return Returns the measurementDimension.
	 */
	@Column(name = "MMTDMN")
	public String getMeasurementDimension() {
		return measurementDimension;
	}

	/**
	 * @param measurementDimension
	 *            The measurementDimension to set.
	 */
	public void setMeasurementDimension(String measurementDimension) {
		this.measurementDimension = measurementDimension;
	}

	/**
	 * @return Returns the sealNumber.
	 */
	@Column(name = "CONSELNUM")
	public String getSealNumber() {
		return sealNumber;
	}

	/**
	 * @param sealNumber
	 *            The sealNumber to set.
	 */
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}

	/**
	 * @return Returns the typeCodeListResponsibleAgency.
	 */
	@Column(name = "TYPCODLSTAGY")
	public String getTypeCodeListResponsibleAgency() {
		return typeCodeListResponsibleAgency;
	}

	/**
	 * @param typeCodeListResponsibleAgency
	 *            The typeCodeListResponsibleAgency to set.
	 */
	public void setTypeCodeListResponsibleAgency(
			String typeCodeListResponsibleAgency) {
		this.typeCodeListResponsibleAgency = typeCodeListResponsibleAgency;
	}
	
	/**
	 * @return Returns the completionStatus.
	 */
	@Column(name = "CMPSTA")
	public String getCompletionStatus() {
		return completionStatus;
	}

	/**
	 * @param completionStatus The completionStatus to set.
	 */
	public void setCompletionStatus(String completionStatus) {
		this.completionStatus = completionStatus;
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
	 * @return Returns the carditContainerPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "carditKey", column = @Column(name = "CDTKEY")),
		@AttributeOverride(name = "containerNumber", column = @Column(name = "CONNUM"))})
	public CarditContainerPK getCarditContainerPK() {
		return carditContainerPK;
	}

	/**
	 * @param carditContainerPK
	 *            The carditContainerPK to set.
	 */
	public void setCarditContainerPK(CarditContainerPK carditContainerPK) {
		this.carditContainerPK = carditContainerPK;
	}
	/**
	 * @author a-1936
	 * This method is used to find the Cardit Container //
	 * @param companyCode
	 * @param carditKey
	 * @param containerNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static CarditContainer find(String companyCode, String carditKey,
			String containerNumber) throws SystemException,
			FinderException {
		   Log  logger= LogFactory.getLogger("MAILTRACKING_DEFAULTS");
		   CarditContainerPK carditContPK = new CarditContainerPK();
		   carditContPK.setCompanyCode(   companyCode);
		   carditContPK.setCarditKey(carditKey);
		   carditContPK.setContainerNumber(containerNumber);
		   logger.log(Log.FINE,"The Company Code "+ carditContPK.getCompanyCode());
		   logger.log(Log.FINE,"The CarditKey "+ carditContPK.getCarditKey());
		   logger.log(Log.FINE,"The ContainerNumber "+ carditContPK.getContainerNumber());
		  EntityManager em = PersistenceController.getEntityManager();
		  return em.find(CarditContainer.class, carditContPK);
	}

	/**
	 * @return the containerJourneyIdentifier
	 */
	@Column(name="CONJRNIDR")
	public String getContainerJourneyIdentifier() {
		return containerJourneyIdentifier;
	}

	/**
	 * @param containerJourneyIdentifier the containerJourneyIdentifier to set
	 */
	public void setContainerJourneyIdentifier(String containerJourneyIdentifier) {
		this.containerJourneyIdentifier = containerJourneyIdentifier;
	}

	
	
}