/*
 * DSNInULDAtAirportVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 * 
 * @author A-5991
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * JUN 30, 2016 A-5991 First Draft
 */
public class DSNInULDAtAirportVO extends AbstractVO {

	private String dsn;

	private String originOfficeOfExchange;

	private String destinationOfficeOfExchange;

	private String mailClass;

	private int year;

	private int statedBags;

	//private double statedWeight;
	private Measure statedWeight;//added by A-7371

	private int acceptedBags;

	//private double acceptedWeight;
	private Measure acceptedWeight;//added by A-7371

	private String pltEnabledFlag;

	private String operationFlag;

	/* AWB Details */
	private int documentOwnerIdentifier;

	private String masterDocumentNumber;

	private int duplicateNumber;

	private int sequenceNumber;

	private String documentOwnerCode;
	
	private String mailCategoryCode;
	
	private String mailSubclass;

	private Collection<DSNInContainerAtAirportVO> dsnInContainerAtAirports;

	private Collection<MailbagInULDAtAirportVO> mailbagInULDVOs;

	private Collection<DSNInConsignmentForULDAtAirportVO> dsnInConsignments;
/**
 * 
 * @return statedWeight
 */
	public Measure getStatedWeight() {
		return statedWeight;
	}
/**
 * 
 * @param statedWeight
 */
	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
	}
/**
 * 
 * @return acceptedWeight
 */
	public Measure getAcceptedWeight() {
		return acceptedWeight;
	}
/**
 * 
 * @param acceptedWeight
 */
	public void setAcceptedWeight(Measure acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}

	/**
	 * @return Returns the acceptedBags.
	 */
	public int getAcceptedBags() {
		return acceptedBags;
	}

	/**
	 * @param acceptedBags
	 *            The acceptedBags to set.
	 */
	public void setAcceptedBags(int acceptedBags) {
		this.acceptedBags = acceptedBags;
	}

	/**
	 * @return Returns the acceptedWeight.
	 */
	/*public double getAcceptedWeight() {
		return acceptedWeight;
	}

	*//**
	 * @param acceptedWeight
	 *            The acceptedWeight to set.
	 *//*
	public void setAcceptedWeight(double acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}*/

	/**
	 * @return Returns the destinationExchangeOffice.
	 */
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	/**
	 * @param destinationExchangeOffice
	 *            The destinationExchangeOffice to set.
	 */
	public void setDestinationOfficeOfExchange(String destinationExchangeOffice) {
		this.destinationOfficeOfExchange = destinationExchangeOffice;
	}

	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn
	 *            The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the dsnInContainerAtAirports.
	 */
	public Collection<DSNInContainerAtAirportVO> getDsnInContainerAtAirports() {
		return dsnInContainerAtAirports;
	}

	/**
	 * @param dsnInContainerAtAirports
	 *            The dsnInContainerAtAirports to set.
	 */
	public void setDsnInContainerAtAirports(
			Collection<DSNInContainerAtAirportVO> dsnInContainerAtAirports) {
		this.dsnInContainerAtAirports = dsnInContainerAtAirports;
	}

	/**
	 * @return Returns the mailClass.
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass
	 *            The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	/**
	 * @return Returns the originExchangeOffice.
	 */
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	/**
	 * @param originExchangeOffice
	 *            The originExchangeOffice to set.
	 */
	public void setOriginOfficeOfExchange(String originExchangeOffice) {
		this.originOfficeOfExchange = originExchangeOffice;
	}

	/**
	 * @return Returns the pltEnabledFlag.
	 */
	public String getPltEnabledFlag() {
		return pltEnabledFlag;
	}

	/**
	 * @param pltEnabledFlag
	 *            The pltEnabledFlag to set.
	 */
	public void setPltEnabledFlag(String pltEnabledFlag) {
		this.pltEnabledFlag = pltEnabledFlag;
	}

	/**
	 * @return Returns the statedBags.
	 */
	public int getStatedBags() {
		return statedBags;
	}

	/**
	 * @param statedBags
	 *            The statedBags to set.
	 */
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}

	/**
	 * @return Returns the statedWeight.
	 */
	/*public double getStatedWeight() {
		return statedWeight;
	}

	*//**
	 * @param statedWeight
	 *            The statedWeight to set.
	 *//*
	public void setStatedWeight(double statedWeight) {
		this.statedWeight = statedWeight;
	}*/

	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return Returns the mailbagInULDVOs.
	 */
	public Collection<MailbagInULDAtAirportVO> getMailbagInULDVOs() {
		return mailbagInULDVOs;
	}

	/**
	 * @param mailbagInULDVOs
	 *            The mailbagInULDVOs to set.
	 */
	public void setMailbagInULDVOs(
			Collection<MailbagInULDAtAirportVO> mailbagInULDVOs) {
		this.mailbagInULDVOs = mailbagInULDVOs;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the dsnInConsignments.
	 */
	public Collection<DSNInConsignmentForULDAtAirportVO> getDsnInConsignments() {
		return dsnInConsignments;
	}

	/**
	 * @param dsnInConsignments
	 *            The dsnInConsignments to set.
	 */
	public void setDsnInConsignments(
			Collection<DSNInConsignmentForULDAtAirportVO> dsnInConsignments) {
		this.dsnInConsignments = dsnInConsignments;
	}

	/**
	 * @return Returns the documentOwnerCode.
	 */
	public String getDocumentOwnerCode() {
		return documentOwnerCode;
	}

	/**
	 * @param documentOwnerCode
	 *            The documentOwnerCode to set.
	 */
	public void setDocumentOwnerCode(String documentOwnerCode) {
		this.documentOwnerCode = documentOwnerCode;
	}

	/**
	 * @return Returns the documentOwnerIdentifier.
	 */
	public int getDocumentOwnerIdentifier() {
		return documentOwnerIdentifier;
	}

	/**
	 * @param documentOwnerIdentifier
	 *            The documentOwnerIdentifier to set.
	 */
	public void setDocumentOwnerIdentifier(int documentOwnerIdentifier) {
		this.documentOwnerIdentifier = documentOwnerIdentifier;
	}

	/**
	 * @return Returns the duplicateNumber.
	 */
	public int getDuplicateNumber() {
		return duplicateNumber;
	}

	/**
	 * @param duplicateNumber
	 *            The duplicateNumber to set.
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}

	/**
	 * @return Returns the masterDocumentNumber.
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}

	/**
	 * @param masterDocumentNumber
	 *            The masterDocumentNumber to set.
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

}
