/*
 * DSNInULDForSegmentVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 * 
 */
public class DSNInULDForSegmentVO extends AbstractVO {
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

	private String operationFlag;

	private String pltEnabledFlag;

	private int receivedBags;

	//private double receivedWeight;
	private Measure receivedWeight;//added by A-7371

	private int deliveredBags;

	//private double deliveredWeight;
	private Measure deliveredWeight;//added by A-7371

	/* AWB Details */
	private int documentOwnerIdentifier;

	private String masterDocumentNumber;

	private int duplicateNumber;

	private int sequenceNumber;

	private String documentOwnerCode;

	/**
	 * The mailSubclass
	 */
	private String mailSubclass;

	/**
	 * The mailCategoryCode
	 */
	private String mailCategoryCode;

	private Collection<DSNInContainerForSegmentVO> dsnInContainerForSegs;

	private Collection<MailbagInULDForSegmentVO> mailBags;

	private Collection<DSNInConsignmentForULDSegmentVO> dsnInConsignments; 
	
	
	private LocalDate lastUpdateTime;
	
	private String lastUpdateUser;
    
	private String ubrNumber;
    private double mailrate;
    private String currencyCode;
   
    private int transferredPieces;
	//private double transferredWeight;
    private Measure transferredWeight;//added by A-7371
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
 * 
 * @return receivedWeight
 */
	public Measure getReceivedWeight() {
		return receivedWeight;
	}
/**
 * 
 * @param receivedWeight
 */
	public void setReceivedWeight(Measure receivedWeight) {
		this.receivedWeight = receivedWeight;
	}
/**
 * 
 * @return deliveredWeight
 */
	public Measure getDeliveredWeight() {
		return deliveredWeight;
	}
/**
 * 
 * @param deliveredWeight
 */
	public void setDeliveredWeight(Measure deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}
/**
 * 
 * @return transferredWeight
 */
	public Measure getTransferredWeight() {
		return transferredWeight;
	}
/**
 * 
 * @param transferredWeight
 */
	public void setTransferredWeight(Measure transferredWeight) {
		this.transferredWeight = transferredWeight;
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
	}
*/
	/**
	 * @return Returns the destinationOfficeOfExchange.
	 */
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	/**
	 * @param destinationOfficeOfExchange
	 *            The destinationOfficeOfExchange to set.
	 */
	public void setDestinationOfficeOfExchange(
			String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	/**
	 * @return Returns the dsnInContainerForSegs.
	 */
	public Collection<DSNInContainerForSegmentVO> getDsnInContainerForSegs() {
		return dsnInContainerForSegs;
	}

	/**
	 * @param dsnInContainerForSegs
	 *            The dsnInContainerForSegs to set.
	 */
	public void setDsnInContainerForSegs(
			Collection<DSNInContainerForSegmentVO> dsnInContainerForSegs) {
		this.dsnInContainerForSegs = dsnInContainerForSegs;
	}

	/**
	 * @return Returns the mailBags.
	 */
	public Collection<MailbagInULDForSegmentVO> getMailBags() {
		return mailBags;
	}

	/**
	 * @param mailBags
	 *            The mailBags to set.
	 */
	public void setMailBags(Collection<MailbagInULDForSegmentVO> mailBags) {
		this.mailBags = mailBags;
	}

	/**
	 * @return Returns the originOfficeOfExchange.
	 */
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	/**
	 * @param originOfficeOfExchange
	 *            The originOfficeOfExchange to set.
	 */
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

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
	 * @return Returns the dsnInConsignments.
	 */
	public Collection<DSNInConsignmentForULDSegmentVO> getDsnInConsignments() {
		return dsnInConsignments;
	}

	/**
	 * @param dsnInConsignments
	 *            The dsnInConsignments to set.
	 */
	public void setDsnInConsignments(
			Collection<DSNInConsignmentForULDSegmentVO> dsnInConsignments) {
		this.dsnInConsignments = dsnInConsignments;
	}

	/**
	 * @return Returns the receivedBags.
	 */
	public int getReceivedBags() {
		return receivedBags;
	}

	/**
	 * @param receivedBags
	 *            The receivedBags to set.
	 */
	public void setReceivedBags(int receivedBags) {
		this.receivedBags = receivedBags;
	}

	/**
	 * @return Returns the receivedWeight.
	 */
	/*public double getReceivedWeight() {
		return receivedWeight;
	}

	*//**
	 * @param receivedWeight
	 *            The receivedWeight to set.
	 *//*
	public void setReceivedWeight(double receivedWeight) {
		this.receivedWeight = receivedWeight;
	}*/

	/**
	 * @return Returns the deliveredBags.
	 */
	public int getDeliveredBags() {
		return deliveredBags;
	}

	/**
	 * @param deliveredBags
	 *            The deliveredBags to set.
	 */
	public void setDeliveredBags(int deliveredBags) {
		this.deliveredBags = deliveredBags;
	}

	/**
	 * @return Returns the deliveredWeight.
	 */
	/*public double getDeliveredWeight() {
		return deliveredWeight;
	}

	*//**
	 * @param deliveredWeight
	 *            The deliveredWeight to set.
	 *//*
	public void setDeliveredWeight(double deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}*/

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
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass
	 *            The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
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

	/**
	 * @return the ubrNumber
	 */
	public String getUbrNumber() {
		return ubrNumber;
	}

	/**
	 * @param ubrNumber the ubrNumber to set
	 */
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the mailrate
	 */
	public double getMailrate() {
		return mailrate;
	}

	/**
	 * @param mailrate the mailrate to set
	 */
	public void setMailrate(double mailrate) {
		this.mailrate = mailrate;
	}
	public int getTransferredPieces() {
		return transferredPieces;
	}
	public void setTransferredPieces(int transferredPieces) {
		this.transferredPieces = transferredPieces;
	}
	/*public double getTransferredWeight() {
		return transferredWeight;
	}
	public void setTransferredWeight(double transferredWeight) {
		this.transferredWeight = transferredWeight;
	}*/
}
