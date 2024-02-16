/*
 * CarditContainerVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * 
 * @author A-5991
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 30, 2016 A-5991 First Draft
 */
public class CarditContainerVO extends AbstractVO {

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
	//private String containerWeight;
	private Measure containerWeight;//added by A-7371

	/**
	 * Container seal number
	 */
	private String sealNumber;

	private String containerNumber;

	/**
	 * Container Journey Identifier
	 */
	private String containerJourneyIdentifier;
	
	/**
	 * The CDTTYP : CarditType (Message Function) 
	 */
	private String carditType;

	/**
	 * last update user
	 */
	private String lastUpdateUser;

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
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber
	 *            The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the containerType.
	 */
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
	/*public String getContainerWeight() {
		return containerWeight;
	}

	*//**
	 * @param containerWeight
	 *            The containerWeight to set.
	 *//*
	public void setContainerWeight(String containerWeight) {
		this.containerWeight = containerWeight;
	}*/

	/**
	 * @return Returns the equipmentQualifier.
	 */
	public String getEquipmentQualifier() {
		return equipmentQualifier;
	}
	//added by A-7371
/**
 * 
 * @return containerWeight
 */
	public Measure getContainerWeight() {
		return containerWeight;
	}
/**
 * 
 * @param containerWeight
 */
	public void setContainerWeight(Measure containerWeight) {
		this.containerWeight = containerWeight;
	}

	/**
	 * @param equipmentQualifier
	 *            The equipmentQualifier to set.
	 */
	public void setEquipmentQualifier(String equipmentQualifier) {
		this.equipmentQualifier = equipmentQualifier;
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
	 * @return Returns the measurementDimension.
	 */
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
	 * @return the containerJourneyIdentifier
	 */
	public String getContainerJourneyIdentifier() {
		return containerJourneyIdentifier;
	}

	/**
	 * @param containerJourneyIdentifier the containerJourneyIdentifier to set
	 */
	public void setContainerJourneyIdentifier(String containerJourneyIdentifier) {
		this.containerJourneyIdentifier = containerJourneyIdentifier;
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

}
