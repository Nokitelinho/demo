/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempDetail.java
 *
 *	Created by	:	A-6287
 *	Created on	:	25-Feb-2020
 *
 *  Copyright 2020 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReceptacleInformationMessageVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempDetail.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6287	:	25-Feb-2020	:	Draft
 */

@Entity
@Table(name = "MALCDTMSGTMP")
public class CarditTempDetails {
	
	private static final String MODULE = "Mail MESSAGE";
	private Log log = LogFactory.getLogger(MODULE);
	
	private CarditTempDetailsPK carditTempDetailsPK;
	private String SyntaxID;
	private String SyntaxVersion;
	private String SenderID;
	private String ProcessingFailed;
	private String SenderPartnerIDQualifier;
	private String RecipientID;
	private String RecipientPartnerIDQualifier;
	private String PreparationDate;
	private String InterchangeControlReference;
	private String TestIndicator;
	private String InterchangeControlCount;
	private String TrailerInterchangeControlReference;
	private String ControllingAgency;
	private String MessageReferenceNumber;
	private String MessageTypeIdentifier;
	private String MessageVersionNumber;
	private String MessageReleaseNumber;
	private String AssociationAssignedCode;
	private long messageSeqNum;
	
	private String MessageFunction;
	private String CarditMessageVersionNumber;
	private String DateTimePeriodQualifier;
	private String DateTimeFormatQualifier;
	private String TrailerMessageReferenceNumber;
	private String NumberOfSegments;
	private String ErrorCardit;
	private String MailCategoryCodeIndicator;
	private String ConsignmentCompletionDate;
	private String ReqDeliveryTime;
	private String TransportStageQualifier;
	private String ConveyanceReference;
	private String TransportIdentification;
	private String CarrierID;
	private String DepartureLocationQualifier;
	private String DeparturePlace;
	private String ArrivalLocationQualifier;
	private String ArrivalPlace;
	private String TransportInfoDepartureDate;
	private String TransportInfoArrivalDate;
	private String MailCategoryCode;
	private String DomesticTransportStageQualifier;
	private String DomesticConveyanceReference;
	private String DomesticTransportIdentification;
	private String DomesticCarrierID;

	private String ModeOfTransport;
	private String CarrierName;
	private String CodeListQualifier;
	private String AgencyForCarrierCode;
	private String TransportLegRate;
	private String ContractReference;
	private String DomesticDepartureLocationQualifier;
	private String DomesticDeparturePlace;
	private String DomesticArrivalLocationQualifier;
	private String DomesticArrivalPlace;
	private String DeparturePlaceName;
	private String DepartureCodeListQualifier;
	private String DepartureCodeListAgency;
	private String ArrivalPlaceName;
	private String ArrivalCodeListQualifier;
	private String ArrivalCodeListAgency;
	private String TransportInfoDateTimePeriodQualifier;
	private String TransportInfoDateTimeFormatQualifier;
	private String DepartureDate;
	private String ArrivalDate;

	private String  MailClassCode;
	private String  ControlValue;
	private String TotalPiecesControlQualifier;
	private String NumberOfReceptacles;
	private String TotalPiecesMeasureUnitQualifier;
	private String TotalWeightControlQualifier;
	private String WeightOfReceptacles;
	private String TotalWeightMeasureUnitQualifier;

	private String ConsignmentContractReferenceNumber;
	private String TransportContractReferenceQualifier;


	private String RefInfoTransportStageQualifier;
	private String HandoverOriginLocationQualifier;
	private String HandoverOriginLocationIdentifier;
	private String HandoverOriginLocationName;
	private String HandoverOriginCodeListQualifier;
	private String HandoverOriginCodeListAgency;
	private String HandoverDestnLocationQualifier;
	private String HandoverDestnLocationIdentifier;
	private String HandoverDestnLocationName;
	private String HandoverDestnCodeListQualifier;
	private String HandoverDestnCodeListAgency;
	private String HandOverInfoDateTimePeriodQualifier;
	private String HandOverInfoDateTimeFormatQualifier;
	private String OriginCutOffPeriod;
	private String DestinationCutOffPeriod;


	private String EquipmentQualifier;
	private String ContainerNumber;
	private String ContainerInfoCodeListResponsibleAgency;
	private String ContainerType;
	private String ContainerInfoCodeListQualifier;
	private String TypeCodeListResponsibleAgency;
	private String ContainerJourneyIdentifier;
	private String MeasurementApplication;
	private String MeasurementDimension;
	private String ContainerInfoMeasureUnitQualifier;
	private String ContainerWeight;
	private String SealNumber;
	private String errorCodes;
	private String messageStatus;
	private String isProcessed;
	private Calendar lastUpdatedTime;
	private String postalAwbNumber;
	private String shipperUpuCode;
	private String consigneeUpuCode;
	private String consignmentOrigin;
	private String consignmentDestination;
	/**
	 * 	Getter for lastUpdatedTime 
	 *	Added by : A-6287 on 04-Mar-2020
	 * 	Used for :
	 */
	@Version
	@Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 *  @param lastUpdatedTime the lastUpdatedTime to set
	 * 	Setter for lastUpdatedTime 
	 *	Added by : A-6287 on 04-Mar-2020
	 * 	Used for :
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	private Set<CarditTempMailBagDetails> mailBagVOs;
	
	
	
	/**
	 * 	Getter for messageSeqNum 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	@Column(name="MSGSEQNUM")
	public long getMessageSeqNum() {
		return messageSeqNum;
	}

	/**
	 *  @param messageSeqNum the messageSeqNum to set
	 * 	Setter for messageSeqNum 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	public void setMessageSeqNum(long messageSeqNum) {
		this.messageSeqNum = messageSeqNum;
	}

	/**
	 * 	Getter for CarditTempDetailsPK 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	
	@EmbeddedId
	@AttributeOverrides({
	@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	@AttributeOverride(name="sequenceNumber",column=@Column(name="SEQNUM")),
	@AttributeOverride(name="ConsignmentIdentifier",column=@Column(name="CNSMNTIDR"))})
	public CarditTempDetailsPK getCarditTempDetailsPK() {
        return carditTempDetailsPK;
    }

    /**
     * @param CarditTempDetailsPK The CarditTempDetailsPK to set.
     */
    public void setCarditTempDetailsPK(CarditTempDetailsPK carditTempDetailsPK) {
        this.carditTempDetailsPK = carditTempDetailsPK;
    }

	/**
	 * 	Getter for syntaxID 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
    @Column(name="SYNIDR")
	public String getSyntaxID() {
		return SyntaxID;
	}

	/**
	 *  @param syntaxID the syntaxID to set
	 * 	Setter for syntaxID 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setSyntaxID(String syntaxID) {
		SyntaxID = syntaxID;
	}

	/**
	 * 	Getter for syntaxVersion 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="SYNVER")
	public String getSyntaxVersion() {
		return SyntaxVersion;
	}

	/**
	 *  @param syntaxVersion the syntaxVersion to set
	 * 	Setter for syntaxVersion 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setSyntaxVersion(String syntaxVersion) {
		SyntaxVersion = syntaxVersion;
	}

	/**
	 * 	Getter for senderID 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="SNDIDR")
	public String getSenderID() {
		return SenderID;
	}

	/**
	 *  @param senderID the senderID to set
	 * 	Setter for senderID 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setSenderID(String senderID) {
		SenderID = senderID;
	}

	/**
	 * 	Getter for processingFailed 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="PRCFLD")
	public String getProcessingFailed() {
		return ProcessingFailed;
	}

	/**
	 *  @param processingFailed the processingFailed to set
	 * 	Setter for processingFailed 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setProcessingFailed(String processingFailed) {
		ProcessingFailed = processingFailed;
	}

	/**
	 * 	Getter for senderPartnerIDQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="SNDPRTIDRQFR")
	public String getSenderPartnerIDQualifier() {
		return SenderPartnerIDQualifier;
	}

	/**
	 *  @param senderPartnerIDQualifier the senderPartnerIDQualifier to set
	 * 	Setter for senderPartnerIDQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setSenderPartnerIDQualifier(String senderPartnerIDQualifier) {
		SenderPartnerIDQualifier = senderPartnerIDQualifier;
	}

	/**
	 * 	Getter for recipientID 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="RECPNTIDR")
	public String getRecipientID() {
		return RecipientID;
	}

	/**
	 *  @param recipientID the recipientID to set
	 * 	Setter for recipientID 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setRecipientID(String recipientID) {
		RecipientID = recipientID;
	}

	/**
	 * 	Getter for recipientPartnerIDQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="RCPPRTIDRQFR")
	public String getRecipientPartnerIDQualifier() {
		return RecipientPartnerIDQualifier;
	}

	/**
	 *  @param recipientPartnerIDQualifier the recipientPartnerIDQualifier to set
	 * 	Setter for recipientPartnerIDQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setRecipientPartnerIDQualifier(String recipientPartnerIDQualifier) {
		RecipientPartnerIDQualifier = recipientPartnerIDQualifier;
	}

	/**
	 * 	Getter for preparationDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="PRPDAT")
	public String getPreparationDate() {
		return PreparationDate;
	}

	/**
	 *  @param preparationDate the preparationDate to set
	 * 	Setter for preparationDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setPreparationDate(String preparationDate) {
		PreparationDate = preparationDate;
	}

	/**
	 * 	Getter for interchangeControlReference 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="ICHCTLREF")
	public String getInterchangeControlReference() {
		return InterchangeControlReference;
	}

	/**
	 *  @param interchangeControlReference the interchangeControlReference to set
	 * 	Setter for interchangeControlReference 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setInterchangeControlReference(String interchangeControlReference) {
		InterchangeControlReference = interchangeControlReference;
	}

	/**
	 * 	Getter for testIndicator 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TSTIND")
	public String getTestIndicator() {
		return TestIndicator;
	}

	/**
	 *  @param testIndicator the testIndicator to set
	 * 	Setter for testIndicator 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTestIndicator(String testIndicator) {
		TestIndicator = testIndicator;
	}

	/**
	 * 	Getter for interchangeControlCount 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="INTCHGCNLCNT")
	public String getInterchangeControlCount() {
		return InterchangeControlCount;
	}

	/**
	 *  @param interchangeControlCount the interchangeControlCount to set
	 * 	Setter for interchangeControlCount 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setInterchangeControlCount(String interchangeControlCount) {
		InterchangeControlCount = interchangeControlCount;
	}

	/**
	 * 	Getter for trailerInterchangeControlReference 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TRLINTCHGCNLREF")
	public String getTrailerInterchangeControlReference() {
		return TrailerInterchangeControlReference;
	}

	/**
	 *  @param trailerInterchangeControlReference the trailerInterchangeControlReference to set
	 * 	Setter for trailerInterchangeControlReference 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTrailerInterchangeControlReference(String trailerInterchangeControlReference) {
		TrailerInterchangeControlReference = trailerInterchangeControlReference;
	}

	/**
	 * 	Getter for controllingAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CTLAGY")
	public String getControllingAgency() {
		return ControllingAgency;
	}

	/**
	 *  @param controllingAgency the controllingAgency to set
	 * 	Setter for controllingAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setControllingAgency(String controllingAgency) {
		ControllingAgency = controllingAgency;
	}

	/**
	 * 	Getter for messageReferenceNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MSGREFNUM")
	public String getMessageReferenceNumber() {
		return MessageReferenceNumber;
	}

	/**
	 *  @param messageReferenceNumber the messageReferenceNumber to set
	 * 	Setter for messageReferenceNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMessageReferenceNumber(String messageReferenceNumber) {
		MessageReferenceNumber = messageReferenceNumber;
	}

	/**
	 * 	Getter for messageTypeIdentifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MSGTYP")
	public String getMessageTypeIdentifier() {
		return MessageTypeIdentifier;
	}

	/**
	 *  @param messageTypeIdentifier the messageTypeIdentifier to set
	 * 	Setter for messageTypeIdentifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMessageTypeIdentifier(String messageTypeIdentifier) {
		MessageTypeIdentifier = messageTypeIdentifier;
	}

	/**
	 * 	Getter for messageVersionNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MSGVER")
	public String getMessageVersionNumber() {
		return MessageVersionNumber;
	}

	/**
	 *  @param messageVersionNumber the messageVersionNumber to set
	 * 	Setter for messageVersionNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMessageVersionNumber(String messageVersionNumber) {
		MessageVersionNumber = messageVersionNumber;
	}

	/**
	 * 	Getter for messageReleaseNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MSGRELNUM")
	public String getMessageReleaseNumber() {
		return MessageReleaseNumber;
	}

	/**
	 *  @param messageReleaseNumber the messageReleaseNumber to set
	 * 	Setter for messageReleaseNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMessageReleaseNumber(String messageReleaseNumber) {
		MessageReleaseNumber = messageReleaseNumber;
	}

	/**
	 * 	Getter for associationAssignedCode 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="ASNASGCOD")
	public String getAssociationAssignedCode() {
		return AssociationAssignedCode;
	}

	/**
	 *  @param associationAssignedCode the associationAssignedCode to set
	 * 	Setter for associationAssignedCode 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setAssociationAssignedCode(String associationAssignedCode) {
		AssociationAssignedCode = associationAssignedCode;
	}

	/**
	 * 	Getter for messageFunction 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MSGFUN")
	public String getMessageFunction() {
		return MessageFunction;
	}

	/**
	 *  @param messageFunction the messageFunction to set
	 * 	Setter for messageFunction 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMessageFunction(String messageFunction) {
		MessageFunction = messageFunction;
	}

	/**
	 * 	Getter for dateTimePeriodQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DTMPRDQLF")
	public String getDateTimePeriodQualifier() {
		return DateTimePeriodQualifier;
	}

	/**
	 *  @param dateTimePeriodQualifier the dateTimePeriodQualifier to set
	 * 	Setter for dateTimePeriodQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDateTimePeriodQualifier(String dateTimePeriodQualifier) {
		DateTimePeriodQualifier = dateTimePeriodQualifier;
	}

	/**
	 * 	Getter for dateTimeFormatQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DTMFMTQLF")
	public String getDateTimeFormatQualifier() {
		return DateTimeFormatQualifier;
	}

	/**
	 *  @param dateTimeFormatQualifier the dateTimeFormatQualifier to set
	 * 	Setter for dateTimeFormatQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDateTimeFormatQualifier(String dateTimeFormatQualifier) {
		DateTimeFormatQualifier = dateTimeFormatQualifier;
	}

	/**
	 * 	Getter for trailerMessageReferenceNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TRLMSGREFNUM")
	public String getTrailerMessageReferenceNumber() {
		return TrailerMessageReferenceNumber;
	}

	/**
	 *  @param trailerMessageReferenceNumber the trailerMessageReferenceNumber to set
	 * 	Setter for trailerMessageReferenceNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTrailerMessageReferenceNumber(String trailerMessageReferenceNumber) {
		TrailerMessageReferenceNumber = trailerMessageReferenceNumber;
	}

	/**
	 * 	Getter for numberOfSegments 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="NUMSEG")
	public String getNumberOfSegments() {
		return NumberOfSegments;
	}

	/**
	 *  @param numberOfSegments the numberOfSegments to set
	 * 	Setter for numberOfSegments 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setNumberOfSegments(String numberOfSegments) {
		NumberOfSegments = numberOfSegments;
	}

	/**
	 * 	Getter for errorCardit 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="ERRCAR")
	public String getErrorCardit() {
		return ErrorCardit;
	}

	/**
	 *  @param errorCardit the errorCardit to set
	 * 	Setter for errorCardit 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setErrorCardit(String errorCardit) {
		ErrorCardit = errorCardit;
	}

	/**
	 * 	Getter for mailCategoryCodeIndicator 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MALCATCODIDR")
	public String getMailCategoryCodeIndicator() {
		return MailCategoryCodeIndicator;
	}

	/**
	 *  @param mailCategoryCodeIndicator the mailCategoryCodeIndicator to set
	 * 	Setter for mailCategoryCodeIndicator 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMailCategoryCodeIndicator(String mailCategoryCodeIndicator) {
		MailCategoryCodeIndicator = mailCategoryCodeIndicator;
	}

	/**
	 * 	Getter for consignmentCompletionDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CNSCMPDAT")
	public String getConsignmentCompletionDate() {
		return ConsignmentCompletionDate;
	}

	/**
	 *  @param consignmentCompletionDate the consignmentCompletionDate to set
	 * 	Setter for consignmentCompletionDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setConsignmentCompletionDate(String consignmentCompletionDate) {
		ConsignmentCompletionDate = consignmentCompletionDate;
	}

	/**
	 * 	Getter for reqDeliveryTime 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="REQDLVTIM")
	public String getReqDeliveryTime() {
		return ReqDeliveryTime;
	}

	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setReqDeliveryTime(String reqDeliveryTime) {
		ReqDeliveryTime = reqDeliveryTime;
	}

	/**
	 * 	Getter for transportStageQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TRPSTGQFR")
	public String getTransportStageQualifier() {
		return TransportStageQualifier;
	}

	/**
	 *  @param transportStageQualifier the transportStageQualifier to set
	 * 	Setter for transportStageQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTransportStageQualifier(String transportStageQualifier) {
		TransportStageQualifier = transportStageQualifier;
	}

	/**
	 * 	Getter for conveyanceReference 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CNVREFNUM")
	public String getConveyanceReference() {
		return ConveyanceReference;
	}

	/**
	 *  @param conveyanceReference the conveyanceReference to set
	 * 	Setter for conveyanceReference 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setConveyanceReference(String conveyanceReference) {
		ConveyanceReference = conveyanceReference;
	}

	/**
	 * 	Getter for transportIdentification 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TRSIDR")
	public String getTransportIdentification() {
		return TransportIdentification;
	}

	/**
	 *  @param transportIdentification the transportIdentification to set
	 * 	Setter for transportIdentification 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTransportIdentification(String transportIdentification) {
		TransportIdentification = transportIdentification;
	}

	/**
	 * 	Getter for carrierID 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CARIDR")
	public String getCarrierID() {
		return CarrierID;
	}

	/**
	 *  @param carrierID the carrierID to set
	 * 	Setter for carrierID 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setCarrierID(String carrierID) {
		CarrierID = carrierID;
	}

	/**
	 * 	Getter for departureLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DEPLOCQFR")
	public String getDepartureLocationQualifier() {
		return DepartureLocationQualifier;
	}

	/**
	 *  @param departureLocationQualifier the departureLocationQualifier to set
	 * 	Setter for departureLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDepartureLocationQualifier(String departureLocationQualifier) {
		DepartureLocationQualifier = departureLocationQualifier;
	}

	/**
	 * 	Getter for departurePlace 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DEPPLC")
	public String getDeparturePlace() {
		return DeparturePlace;
	}

	/**
	 *  @param departurePlace the departurePlace to set
	 * 	Setter for departurePlace 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDeparturePlace(String departurePlace) {
		DeparturePlace = departurePlace;
	}

	/**
	 * 	Getter for arrivalLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="ARRLOCQFR")
	public String getArrivalLocationQualifier() {
		return ArrivalLocationQualifier;
	}

	/**
	 *  @param arrivalLocationQualifier the arrivalLocationQualifier to set
	 * 	Setter for arrivalLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setArrivalLocationQualifier(String arrivalLocationQualifier) {
		ArrivalLocationQualifier = arrivalLocationQualifier;
	}

	/**
	 * 	Getter for arrivalPlace 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="ARRPLC")
	public String getArrivalPlace() {
		return ArrivalPlace;
	}

	/**
	 *  @param arrivalPlace the arrivalPlace to set
	 * 	Setter for arrivalPlace 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setArrivalPlace(String arrivalPlace) {
		ArrivalPlace = arrivalPlace;
	}

	/**
	 * 	Getter for transportInfoDepartureDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TRPDEPDAT")
	public String getTransportInfoDepartureDate() {
		return TransportInfoDepartureDate;
	}

	/**
	 *  @param transportInfoDepartureDate the transportInfoDepartureDate to set
	 * 	Setter for transportInfoDepartureDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTransportInfoDepartureDate(String transportInfoDepartureDate) {
		TransportInfoDepartureDate = transportInfoDepartureDate;
	}

	/**
	 * 	Getter for transportInfoArrivalDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TRPARRDAT")
	public String getTransportInfoArrivalDate() {
		return TransportInfoArrivalDate;
	}

	/**
	 *  @param transportInfoArrivalDate the transportInfoArrivalDate to set
	 * 	Setter for transportInfoArrivalDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTransportInfoArrivalDate(String transportInfoArrivalDate) {
		TransportInfoArrivalDate = transportInfoArrivalDate;
	}

	/**
	 * 	Getter for mailCategoryCode 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MALCATCOD")
	public String getMailCategoryCode() {
		return MailCategoryCode;
	}

	/**
	 *  @param mailCategoryCode the mailCategoryCode to set
	 * 	Setter for mailCategoryCode 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		MailCategoryCode = mailCategoryCode;
	}

	/**
	 * 	Getter for domesticTransportStageQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DOMTRPSTGQFR")
	public String getDomesticTransportStageQualifier() {
		return DomesticTransportStageQualifier;
	}

	/**
	 *  @param domesticTransportStageQualifier the domesticTransportStageQualifier to set
	 * 	Setter for domesticTransportStageQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDomesticTransportStageQualifier(String domesticTransportStageQualifier) {
		DomesticTransportStageQualifier = domesticTransportStageQualifier;
	}

	/**
	 * 	Getter for domesticConveyanceReference 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DOMCNVREFNUM")
	public String getDomesticConveyanceReference() {
		return DomesticConveyanceReference;
	}

	/**
	 *  @param domesticConveyanceReference the domesticConveyanceReference to set
	 * 	Setter for domesticConveyanceReference 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDomesticConveyanceReference(String domesticConveyanceReference) {
		DomesticConveyanceReference = domesticConveyanceReference;
	}

	/**
	 * 	Getter for domesticTransportIdentification 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DOMTRSIDR")
	public String getDomesticTransportIdentification() {
		return DomesticTransportIdentification;
	}

	/**
	 *  @param domesticTransportIdentification the domesticTransportIdentification to set
	 * 	Setter for domesticTransportIdentification 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDomesticTransportIdentification(String domesticTransportIdentification) {
		DomesticTransportIdentification = domesticTransportIdentification;
	}

	/**
	 * 	Getter for domesticCarrierID 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DOMCARIDR")
	public String getDomesticCarrierID() {
		return DomesticCarrierID;
	}

	/**
	 *  @param domesticCarrierID the domesticCarrierID to set
	 * 	Setter for domesticCarrierID 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDomesticCarrierID(String domesticCarrierID) {
		DomesticCarrierID = domesticCarrierID;
	}

	/**
	 * 	Getter for modeOfTransport 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TRPMODTRNSPT")
	public String getModeOfTransport() {
		return ModeOfTransport;
	}

	/**
	 *  @param modeOfTransport the modeOfTransport to set
	 * 	Setter for modeOfTransport 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setModeOfTransport(String modeOfTransport) {
		ModeOfTransport = modeOfTransport;
	}

	/**
	 * 	Getter for carrierName 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CARNAM")
	public String getCarrierName() {
		return CarrierName;
	}

	/**
	 *  @param carrierName the carrierName to set
	 * 	Setter for carrierName 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setCarrierName(String carrierName) {
		CarrierName = carrierName;
	}

	/**
	 * 	Getter for codeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CODLSTQFR")
	public String getCodeListQualifier() {
		return CodeListQualifier;
	}

	/**
	 *  @param codeListQualifier the codeListQualifier to set
	 * 	Setter for codeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setCodeListQualifier(String codeListQualifier) {
		CodeListQualifier = codeListQualifier;
	}

	/**
	 * 	Getter for agencyForCarrierCode 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="AGYCARCOD")
	public String getAgencyForCarrierCode() {
		return AgencyForCarrierCode;
	}

	/**
	 *  @param agencyForCarrierCode the agencyForCarrierCode to set
	 * 	Setter for agencyForCarrierCode 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setAgencyForCarrierCode(String agencyForCarrierCode) {
		AgencyForCarrierCode = agencyForCarrierCode;
	}

	/**
	 * 	Getter for transportLegRate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TSRLEGRAT")
	public String getTransportLegRate() {
		return TransportLegRate;
	}

	/**
	 *  @param transportLegRate the transportLegRate to set
	 * 	Setter for transportLegRate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTransportLegRate(String transportLegRate) {
		TransportLegRate = transportLegRate;
	}

	/**
	 * 	Getter for contractReference 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CNTREF")
	public String getContractReference() {
		return ContractReference;
	}

	/**
	 *  @param contractReference the contractReference to set
	 * 	Setter for contractReference 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setContractReference(String contractReference) {
		ContractReference = contractReference;
	}

	/**
	 * 	Getter for domesticDepartureLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DOMDEPLOCQFR")
	public String getDomesticDepartureLocationQualifier() {
		return DomesticDepartureLocationQualifier;
	}

	/**
	 *  @param domesticDepartureLocationQualifier the domesticDepartureLocationQualifier to set
	 * 	Setter for domesticDepartureLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDomesticDepartureLocationQualifier(String domesticDepartureLocationQualifier) {
		DomesticDepartureLocationQualifier = domesticDepartureLocationQualifier;
	}

	/**
	 * 	Getter for domesticDeparturePlace 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DOMDEPPLC")
	public String getDomesticDeparturePlace() {
		return DomesticDeparturePlace;
	}

	/**
	 *  @param domesticDeparturePlace the domesticDeparturePlace to set
	 * 	Setter for domesticDeparturePlace 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDomesticDeparturePlace(String domesticDeparturePlace) {
		DomesticDeparturePlace = domesticDeparturePlace;
	}

	/**
	 * 	Getter for domesticArrivalLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DOMARRLOCQFR")
	public String getDomesticArrivalLocationQualifier() {
		return DomesticArrivalLocationQualifier;
	}

	/**
	 *  @param domesticArrivalLocationQualifier the domesticArrivalLocationQualifier to set
	 * 	Setter for domesticArrivalLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDomesticArrivalLocationQualifier(String domesticArrivalLocationQualifier) {
		DomesticArrivalLocationQualifier = domesticArrivalLocationQualifier;
	}

	/**
	 * 	Getter for domesticArrivalPlace 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DOMARRPLC")
	public String getDomesticArrivalPlace() {
		return DomesticArrivalPlace;
	}

	/**
	 *  @param domesticArrivalPlace the domesticArrivalPlace to set
	 * 	Setter for domesticArrivalPlace 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDomesticArrivalPlace(String domesticArrivalPlace) {
		DomesticArrivalPlace = domesticArrivalPlace;
	}

	/**
	 * 	Getter for departurePlaceName 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DEPPLCNAM")
	public String getDeparturePlaceName() {
		return DeparturePlaceName;
	}

	/**
	 *  @param departurePlaceName the departurePlaceName to set
	 * 	Setter for departurePlaceName 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDeparturePlaceName(String departurePlaceName) {
		DeparturePlaceName = departurePlaceName;
	}

	/**
	 * 	Getter for departureCodeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DEPCODLSTQFR")
	public String getDepartureCodeListQualifier() {
		return DepartureCodeListQualifier;
	}

	/**
	 *  @param departureCodeListQualifier the departureCodeListQualifier to set
	 * 	Setter for departureCodeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDepartureCodeListQualifier(String departureCodeListQualifier) {
		DepartureCodeListQualifier = departureCodeListQualifier;
	}

	/**
	 * 	Getter for departureCodeListAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DEPCODLSTAGY")
	public String getDepartureCodeListAgency() {
		return DepartureCodeListAgency;
	}

	/**
	 *  @param departureCodeListAgency the departureCodeListAgency to set
	 * 	Setter for departureCodeListAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDepartureCodeListAgency(String departureCodeListAgency) {
		DepartureCodeListAgency = departureCodeListAgency;
	}

	/**
	 * 	Getter for arrivalPlaceName 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="ARRPLCNAM")
	public String getArrivalPlaceName() {
		return ArrivalPlaceName;
	}

	/**
	 *  @param arrivalPlaceName the arrivalPlaceName to set
	 * 	Setter for arrivalPlaceName 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setArrivalPlaceName(String arrivalPlaceName) {
		ArrivalPlaceName = arrivalPlaceName;
	}

	/**
	 * 	Getter for arrivalCodeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="ARRCODLSTQFR")
	public String getArrivalCodeListQualifier() {
		return ArrivalCodeListQualifier;
	}

	/**
	 *  @param arrivalCodeListQualifier the arrivalCodeListQualifier to set
	 * 	Setter for arrivalCodeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setArrivalCodeListQualifier(String arrivalCodeListQualifier) {
		ArrivalCodeListQualifier = arrivalCodeListQualifier;
	}

	/**
	 * 	Getter for arrivalCodeListAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="ARRCODLSTAGY")
	public String getArrivalCodeListAgency() {
		return ArrivalCodeListAgency;
	}

	/**
	 *  @param arrivalCodeListAgency the arrivalCodeListAgency to set
	 * 	Setter for arrivalCodeListAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setArrivalCodeListAgency(String arrivalCodeListAgency) {
		ArrivalCodeListAgency = arrivalCodeListAgency;
	}

	/**
	 * 	Getter for transportInfoDateTimePeriodQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TRPDTMPRDQLF")
	public String getTransportInfoDateTimePeriodQualifier() {
		return TransportInfoDateTimePeriodQualifier;
	}

	/**
	 *  @param transportInfoDateTimePeriodQualifier the transportInfoDateTimePeriodQualifier to set
	 * 	Setter for transportInfoDateTimePeriodQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTransportInfoDateTimePeriodQualifier(String transportInfoDateTimePeriodQualifier) {
		TransportInfoDateTimePeriodQualifier = transportInfoDateTimePeriodQualifier;
	}

	/**
	 * 	Getter for transportInfoDateTimeFormatQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TRPDTMFMTQFR")
	public String getTransportInfoDateTimeFormatQualifier() {
		return TransportInfoDateTimeFormatQualifier;
	}

	/**
	 *  @param transportInfoDateTimeFormatQualifier the transportInfoDateTimeFormatQualifier to set
	 * 	Setter for transportInfoDateTimeFormatQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTransportInfoDateTimeFormatQualifier(String transportInfoDateTimeFormatQualifier) {
		TransportInfoDateTimeFormatQualifier = transportInfoDateTimeFormatQualifier;
	}

	/**
	 * 	Getter for departureDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DEPDAT")
	public String getDepartureDate() {
		return DepartureDate;
	}

	/**
	 *  @param departureDate the departureDate to set
	 * 	Setter for departureDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDepartureDate(String departureDate) {
		DepartureDate = departureDate;
	}

	/**
	 * 	Getter for arrivalDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="ARRDAT")
	public String getArrivalDate() {
		return ArrivalDate;
	}

	/**
	 *  @param arrivalDate the arrivalDate to set
	 * 	Setter for arrivalDate 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setArrivalDate(String arrivalDate) {
		ArrivalDate = arrivalDate;
	}

	/**
	 * 	Getter for mailClassCode 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MALCLSCOD")
	public String getMailClassCode() {
		return MailClassCode;
	}

	/**
	 *  @param mailClassCode the mailClassCode to set
	 * 	Setter for mailClassCode 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMailClassCode(String mailClassCode) {
		MailClassCode = mailClassCode;
	}

	/**
	 * 	Getter for controlValue 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CNLVAL")
	public String getControlValue() {
		return ControlValue;
	}

	/**
	 *  @param controlValue the controlValue to set
	 * 	Setter for controlValue 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setControlValue(String controlValue) {
		ControlValue = controlValue;
	}

	/**
	 * 	Getter for totalPiecesControlQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TOTPCSCNLQFR")
	public String getTotalPiecesControlQualifier() {
		return TotalPiecesControlQualifier;
	}

	/**
	 *  @param totalPiecesControlQualifier the totalPiecesControlQualifier to set
	 * 	Setter for totalPiecesControlQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTotalPiecesControlQualifier(String totalPiecesControlQualifier) {
		TotalPiecesControlQualifier = totalPiecesControlQualifier;
	}

	/**
	 * 	Getter for numberOfReceptacles 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="NUMRCP")
	public String getNumberOfReceptacles() {
		return NumberOfReceptacles;
	}

	/**
	 *  @param numberOfReceptacles the numberOfReceptacles to set
	 * 	Setter for numberOfReceptacles 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setNumberOfReceptacles(String numberOfReceptacles) {
		NumberOfReceptacles = numberOfReceptacles;
	}

	/**
	 * 	Getter for totalPiecesMeasureUnitQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TOTPCSMSRUNTQFR")
	public String getTotalPiecesMeasureUnitQualifier() {
		return TotalPiecesMeasureUnitQualifier;
	}

	/**
	 *  @param totalPiecesMeasureUnitQualifier the totalPiecesMeasureUnitQualifier to set
	 * 	Setter for totalPiecesMeasureUnitQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTotalPiecesMeasureUnitQualifier(String totalPiecesMeasureUnitQualifier) {
		TotalPiecesMeasureUnitQualifier = totalPiecesMeasureUnitQualifier;
	}

	/**
	 * 	Getter for totalWeightControlQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TOTWGTCNLQFR")
	public String getTotalWeightControlQualifier() {
		return TotalWeightControlQualifier;
	}

	/**
	 *  @param totalWeightControlQualifier the totalWeightControlQualifier to set
	 * 	Setter for totalWeightControlQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTotalWeightControlQualifier(String totalWeightControlQualifier) {
		TotalWeightControlQualifier = totalWeightControlQualifier;
	}

	/**
	 * 	Getter for weightOfReceptacles 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="WGTRCP")
	public String getWeightOfReceptacles() {
		return WeightOfReceptacles;
	}

	/**
	 *  @param weightOfReceptacles the weightOfReceptacles to set
	 * 	Setter for weightOfReceptacles 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setWeightOfReceptacles(String weightOfReceptacles) {
		WeightOfReceptacles = weightOfReceptacles;
	}

	/**
	 * 	Getter for totalWeightMeasureUnitQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TOTWGTMSRUNTQFR")
	public String getTotalWeightMeasureUnitQualifier() {
		return TotalWeightMeasureUnitQualifier;
	}

	/**
	 *  @param totalWeightMeasureUnitQualifier the totalWeightMeasureUnitQualifier to set
	 * 	Setter for totalWeightMeasureUnitQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTotalWeightMeasureUnitQualifier(String totalWeightMeasureUnitQualifier) {
		TotalWeightMeasureUnitQualifier = totalWeightMeasureUnitQualifier;
	}

	/**
	 * 	Getter for consignmentContractReferenceNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CNSCRTREFNUM")
	public String getConsignmentContractReferenceNumber() {
		return ConsignmentContractReferenceNumber;
	}

	/**
	 *  @param consignmentContractReferenceNumber the consignmentContractReferenceNumber to set
	 * 	Setter for consignmentContractReferenceNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setConsignmentContractReferenceNumber(String consignmentContractReferenceNumber) {
		ConsignmentContractReferenceNumber = consignmentContractReferenceNumber;
	}

	/**
	 * 	Getter for transportContractReferenceQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TRPCRTREFQFR")
	public String getTransportContractReferenceQualifier() {
		return TransportContractReferenceQualifier;
	}

	/**
	 *  @param transportContractReferenceQualifier the transportContractReferenceQualifier to set
	 * 	Setter for transportContractReferenceQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTransportContractReferenceQualifier(String transportContractReferenceQualifier) {
		TransportContractReferenceQualifier = transportContractReferenceQualifier;
	}

	/**
	 * 	Getter for refInfoTransportStageQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRTRPSTGQFR")
	public String getRefInfoTransportStageQualifier() {
		return RefInfoTransportStageQualifier;
	}

	/**
	 *  @param refInfoTransportStageQualifier the refInfoTransportStageQualifier to set
	 * 	Setter for refInfoTransportStageQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setRefInfoTransportStageQualifier(String refInfoTransportStageQualifier) {
		RefInfoTransportStageQualifier = refInfoTransportStageQualifier;
	}

	/**
	 * 	Getter for handoverOriginLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRORGLOCQFR")
	public String getHandoverOriginLocationQualifier() {
		return HandoverOriginLocationQualifier;
	}

	/**
	 *  @param handoverOriginLocationQualifier the handoverOriginLocationQualifier to set
	 * 	Setter for handoverOriginLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandoverOriginLocationQualifier(String handoverOriginLocationQualifier) {
		HandoverOriginLocationQualifier = handoverOriginLocationQualifier;
	}

	/**
	 * 	Getter for handoverOriginLocationIdentifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRORGLOCIDR")
	public String getHandoverOriginLocationIdentifier() {
		return HandoverOriginLocationIdentifier;
	}

	/**
	 *  @param handoverOriginLocationIdentifier the handoverOriginLocationIdentifier to set
	 * 	Setter for handoverOriginLocationIdentifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandoverOriginLocationIdentifier(String handoverOriginLocationIdentifier) {
		HandoverOriginLocationIdentifier = handoverOriginLocationIdentifier;
	}

	/**
	 * 	Getter for handoverOriginLocationName 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRORGNAM")
	public String getHandoverOriginLocationName() {
		return HandoverOriginLocationName;
	}

	/**
	 *  @param handoverOriginLocationName the handoverOriginLocationName to set
	 * 	Setter for handoverOriginLocationName 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandoverOriginLocationName(String handoverOriginLocationName) {
		HandoverOriginLocationName = handoverOriginLocationName;
	}

	/**
	 * 	Getter for handoverOriginCodeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRORGCODQFR")
	public String getHandoverOriginCodeListQualifier() {
		return HandoverOriginCodeListQualifier;
	}

	/**
	 *  @param handoverOriginCodeListQualifier the handoverOriginCodeListQualifier to set
	 * 	Setter for handoverOriginCodeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandoverOriginCodeListQualifier(String handoverOriginCodeListQualifier) {
		HandoverOriginCodeListQualifier = handoverOriginCodeListQualifier;
	}

	/**
	 * 	Getter for handoverOriginCodeListAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRORGCODAGY")
	public String getHandoverOriginCodeListAgency() {
		return HandoverOriginCodeListAgency;
	}

	/**
	 *  @param handoverOriginCodeListAgency the handoverOriginCodeListAgency to set
	 * 	Setter for handoverOriginCodeListAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandoverOriginCodeListAgency(String handoverOriginCodeListAgency) {
		HandoverOriginCodeListAgency = handoverOriginCodeListAgency;
	}

	/**
	 * 	Getter for handoverDestnLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRDSTLOCQFR")
	public String getHandoverDestnLocationQualifier() {
		return HandoverDestnLocationQualifier;
	}

	/**
	 *  @param handoverDestnLocationQualifier the handoverDestnLocationQualifier to set
	 * 	Setter for handoverDestnLocationQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandoverDestnLocationQualifier(String handoverDestnLocationQualifier) {
		HandoverDestnLocationQualifier = handoverDestnLocationQualifier;
	}

	/**
	 * 	Getter for handoverDestnLocationIdentifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRDSTLOCIDR")
	public String getHandoverDestnLocationIdentifier() {
		return HandoverDestnLocationIdentifier;
	}

	/**
	 *  @param handoverDestnLocationIdentifier the handoverDestnLocationIdentifier to set
	 * 	Setter for handoverDestnLocationIdentifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandoverDestnLocationIdentifier(String handoverDestnLocationIdentifier) {
		HandoverDestnLocationIdentifier = handoverDestnLocationIdentifier;
	}

	/**
	 * 	Getter for handoverDestnLocationName 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRDSTNAM")
	public String getHandoverDestnLocationName() {
		return HandoverDestnLocationName;
	}

	/**
	 *  @param handoverDestnLocationName the handoverDestnLocationName to set
	 * 	Setter for handoverDestnLocationName 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandoverDestnLocationName(String handoverDestnLocationName) {
		HandoverDestnLocationName = handoverDestnLocationName;
	}

	/**
	 * 	Getter for handoverDestnCodeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRDSTCODQFR")
	public String getHandoverDestnCodeListQualifier() {
		return HandoverDestnCodeListQualifier;
	}

	/**
	 *  @param handoverDestnCodeListQualifier the handoverDestnCodeListQualifier to set
	 * 	Setter for handoverDestnCodeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandoverDestnCodeListQualifier(String handoverDestnCodeListQualifier) {
		HandoverDestnCodeListQualifier = handoverDestnCodeListQualifier;
	}

	/**
	 * 	Getter for handoverDestnCodeListAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRDSTCODAGY")
	public String getHandoverDestnCodeListAgency() {
		return HandoverDestnCodeListAgency;
	}

	/**
	 *  @param handoverDestnCodeListAgency the handoverDestnCodeListAgency to set
	 * 	Setter for handoverDestnCodeListAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandoverDestnCodeListAgency(String handoverDestnCodeListAgency) {
		HandoverDestnCodeListAgency = handoverDestnCodeListAgency;
	}

	/**
	 * 	Getter for handOverInfoDateTimePeriodQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRDTMQFR")
	public String getHandOverInfoDateTimePeriodQualifier() {
		return HandOverInfoDateTimePeriodQualifier;
	}

	/**
	 *  @param handOverInfoDateTimePeriodQualifier the handOverInfoDateTimePeriodQualifier to set
	 * 	Setter for handOverInfoDateTimePeriodQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandOverInfoDateTimePeriodQualifier(String handOverInfoDateTimePeriodQualifier) {
		HandOverInfoDateTimePeriodQualifier = handOverInfoDateTimePeriodQualifier;
	}

	/**
	 * 	Getter for handOverInfoDateTimeFormatQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRDTMFMTQFR")
	public String getHandOverInfoDateTimeFormatQualifier() {
		return HandOverInfoDateTimeFormatQualifier;
	}

	/**
	 *  @param handOverInfoDateTimeFormatQualifier the handOverInfoDateTimeFormatQualifier to set
	 * 	Setter for handOverInfoDateTimeFormatQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setHandOverInfoDateTimeFormatQualifier(String handOverInfoDateTimeFormatQualifier) {
		HandOverInfoDateTimeFormatQualifier = handOverInfoDateTimeFormatQualifier;
	}

	/**
	 * 	Getter for originCutOffPeriod 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRORGCUTOFF")
	public String getOriginCutOffPeriod() {
		return OriginCutOffPeriod;
	}

	/**
	 *  @param originCutOffPeriod the originCutOffPeriod to set
	 * 	Setter for originCutOffPeriod 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setOriginCutOffPeriod(String originCutOffPeriod) {
		OriginCutOffPeriod = originCutOffPeriod;
	}

	/**
	 * 	Getter for destinationCutOffPeriod 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="HNDOVRDSTCUTOFF")
	public String getDestinationCutOffPeriod() {
		return DestinationCutOffPeriod;
	}

	/**
	 *  @param destinationCutOffPeriod the destinationCutOffPeriod to set
	 * 	Setter for destinationCutOffPeriod 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDestinationCutOffPeriod(String destinationCutOffPeriod) {
		DestinationCutOffPeriod = destinationCutOffPeriod;
	}

	/**
	 * 	Getter for equipmentQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="EQPQFR")
	public String getEquipmentQualifier() {
		return EquipmentQualifier;
	}

	/**
	 *  @param equipmentQualifier the equipmentQualifier to set
	 * 	Setter for equipmentQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setEquipmentQualifier(String equipmentQualifier) {
		EquipmentQualifier = equipmentQualifier;
	}

	/**
	 * 	Getter for containerNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CTRNUM")
	public String getContainerNumber() {
		return ContainerNumber;
	}

	/**
	 *  @param containerNumber the containerNumber to set
	 * 	Setter for containerNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setContainerNumber(String containerNumber) {
		ContainerNumber = containerNumber;
	}

	/**
	 * 	Getter for containerInfoCodeListResponsibleAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CODLSTRSPAGY")
	public String getContainerInfoCodeListResponsibleAgency() {
		return ContainerInfoCodeListResponsibleAgency;
	}

	/**
	 *  @param containerInfoCodeListResponsibleAgency the containerInfoCodeListResponsibleAgency to set
	 * 	Setter for containerInfoCodeListResponsibleAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setContainerInfoCodeListResponsibleAgency(String containerInfoCodeListResponsibleAgency) {
		ContainerInfoCodeListResponsibleAgency = containerInfoCodeListResponsibleAgency;
	}

	/**
	 * 	Getter for containerType 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CTRTYP")
	public String getContainerType() {
		return ContainerType;
	}

	/**
	 *  @param containerType the containerType to set
	 * 	Setter for containerType 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setContainerType(String containerType) {
		ContainerType = containerType;
	}

	/**
	 * 	Getter for containerInfoCodeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CTRCODLSTQFR")
	public String getContainerInfoCodeListQualifier() {
		return ContainerInfoCodeListQualifier;
	}

	/**
	 *  @param containerInfoCodeListQualifier the containerInfoCodeListQualifier to set
	 * 	Setter for containerInfoCodeListQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setContainerInfoCodeListQualifier(String containerInfoCodeListQualifier) {
		ContainerInfoCodeListQualifier = containerInfoCodeListQualifier;
	}

	/**
	 * 	Getter for typeCodeListResponsibleAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="TYPCODLSTRSPAGY")
	public String getTypeCodeListResponsibleAgency() {
		return TypeCodeListResponsibleAgency;
	}

	/**
	 *  @param typeCodeListResponsibleAgency the typeCodeListResponsibleAgency to set
	 * 	Setter for typeCodeListResponsibleAgency 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setTypeCodeListResponsibleAgency(String typeCodeListResponsibleAgency) {
		TypeCodeListResponsibleAgency = typeCodeListResponsibleAgency;
	}

	/**
	 * 	Getter for containerJourneyIdentifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CTRJNYIDR")
	public String getContainerJourneyIdentifier() {
		return ContainerJourneyIdentifier;
	}

	/**
	 *  @param containerJourneyIdentifier the containerJourneyIdentifier to set
	 * 	Setter for containerJourneyIdentifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setContainerJourneyIdentifier(String containerJourneyIdentifier) {
		ContainerJourneyIdentifier = containerJourneyIdentifier;
	}

	/**
	 * 	Getter for measurementApplication 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MSRAPP")
	public String getMeasurementApplication() {
		return MeasurementApplication;
	}

	/**
	 *  @param measurementApplication the measurementApplication to set
	 * 	Setter for measurementApplication 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMeasurementApplication(String measurementApplication) {
		MeasurementApplication = measurementApplication;
	}

	/**
	 * 	Getter for measurementDimension 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="MSRDIM")
	public String getMeasurementDimension() {
		return MeasurementDimension;
	}

	/**
	 *  @param measurementDimension the measurementDimension to set
	 * 	Setter for measurementDimension 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setMeasurementDimension(String measurementDimension) {
		MeasurementDimension = measurementDimension;
	}

	/**
	 * 	Getter for containerInfoMeasureUnitQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CTRMSRUNTQFR")
	public String getContainerInfoMeasureUnitQualifier() {
		return ContainerInfoMeasureUnitQualifier;
	}

	/**
	 *  @param containerInfoMeasureUnitQualifier the containerInfoMeasureUnitQualifier to set
	 * 	Setter for containerInfoMeasureUnitQualifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setContainerInfoMeasureUnitQualifier(String containerInfoMeasureUnitQualifier) {
		ContainerInfoMeasureUnitQualifier = containerInfoMeasureUnitQualifier;
	}

	/**
	 * 	Getter for containerWeight 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="CTRWGT")
	public String getContainerWeight() {
		return ContainerWeight;
	}

	/**
	 *  @param containerWeight the containerWeight to set
	 * 	Setter for containerWeight 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setContainerWeight(String containerWeight) {
		ContainerWeight = containerWeight;
	}

	/**
	 * 	Getter for sealNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="SELNUM")
	public String getSealNumber() {
		return SealNumber;
	}

	/**
	 *  @param sealNumber the sealNumber to set
	 * 	Setter for sealNumber 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setSealNumber(String sealNumber) {
		SealNumber = sealNumber;
	}
	/*
	 * Dummy Constructor
	 */
	public CarditTempDetails(){}
	
	/**
	 * 
	 *	Constructor	: 	@param carditTempMsgVOs
	 *	Constructor	: 	@throws CreateException
	 *	Constructor	: 	@throws SystemException
	 *	Created by	:	A-6287
	 *	Created on	:	26-Feb-2020
	 * @throws SystemException 
	 */
	public CarditTempDetails(Collection<CarditTempMsgVO> carditTempMsgVOs) throws SystemException{
		
		for(CarditTempMsgVO tmpVO : carditTempMsgVOs){
			populateAttributes(tmpVO);
			try {
				PersistenceController.getEntityManager().persist(this);
			} catch (CreateException | SystemException e) {
				throw new SystemException(e.getMessage());
			}
			populateChildren(tmpVO);
		}
		
	}

	/**
	 * 	Method		:	CarditTempDetails.populateChildren
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param tmpVO 
	 *	Return type	: 	void
	 * @throws SystemException 
	 */
	private void populateChildren(CarditTempMsgVO tmpVO) throws SystemException {
		carditTempDetailsPK = getCarditTempDetailsPK();
		CarditTempMailBagDetails  carditTempMailBagDetails =null;
		Set<String> mailBagList = new HashSet<>();
		if(tmpVO.getMailbagVOs()!=null)
		for(ReceptacleInformationMessageVO rcpInfoVO : tmpVO.getMailbagVOs()){
			if(!mailBagList.add(rcpInfoVO.getDRTagNo())){
				continue;
			}
			try {
				carditTempMailBagDetails = new CarditTempMailBagDetails(carditTempDetailsPK, rcpInfoVO);
				if(this.mailBagVOs!=null){
					this.mailBagVOs.add(carditTempMailBagDetails);
				}else{
					this.mailBagVOs =  new HashSet<CarditTempMailBagDetails>();
					this.mailBagVOs.add(carditTempMailBagDetails);
				}
			} catch (SystemException e) {
				throw new SystemException(e.getMessage());
			}
		
		}
		
	}

	/**
	 * 	Method		:	CarditTempDetails.populateAttributes
	 *	Added by 	:	A-6287 on 26-Feb-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditTempMsgVOs 
	 *	Return type	: 	void
	 */
	private void populateAttributes(CarditTempMsgVO tmpVO) {
		log.entering("CarditTempDetails","populateAttributes");
		
		CarditTempDetailsPK carditTmpPK = new CarditTempDetailsPK();
		carditTmpPK.setCompanyCode(tmpVO.getCompanyCode());
		carditTmpPK.setConsignmentIdentifier(tmpVO.getConsignmentIdentifier());
		setCarditTempDetailsPK(carditTmpPK);
		
		setSyntaxID(tmpVO.getSyntaxID());
		setSyntaxVersion(tmpVO.getSyntaxVersion());
		setSenderID(tmpVO.getSenderID());
		setProcessingFailed(tmpVO.getProcessingFailed());
		setSenderPartnerIDQualifier(tmpVO.getSenderPartnerIDQualifier());
		setRecipientID(tmpVO.getRecipientID());
		setRecipientPartnerIDQualifier(tmpVO.getRecipientPartnerIDQualifier());
		setPreparationDate(tmpVO.getPreparationDate());
		setInterchangeControlReference(tmpVO.getInterchangeControlReference());
		setTestIndicator(tmpVO.getTestIndicator());
		setInterchangeControlCount(tmpVO.getInterchangeControlCount());
		setTrailerInterchangeControlReference(tmpVO.getTrailerInterchangeControlReference());
		setControllingAgency(tmpVO.getControllingAgency());
		setMessageReferenceNumber(tmpVO.getMessageReferenceNumber());
		setMessageTypeIdentifier(tmpVO.getMessageTypeIdentifier());
		setMessageVersionNumber(tmpVO.getMessageVersionNumber());
		setMessageReleaseNumber(tmpVO.getMessageReleaseNumber());
		setAssociationAssignedCode(tmpVO.getAssociationAssignedCode());
		setMessageFunction(tmpVO.getMessageFunction());
		//setCarditMessageVersionNumber(tmpVO.getCarditMessageVersionNumber());
		setDateTimePeriodQualifier(tmpVO.getDateTimePeriodQualifier());
		setDateTimeFormatQualifier(tmpVO.getDateTimeFormatQualifier());
		setTrailerMessageReferenceNumber(tmpVO.getTrailerMessageReferenceNumber());
		setNumberOfSegments(tmpVO.getNumberOfSegments());
		setErrorCardit(tmpVO.getErrorCardit());
		setMailCategoryCodeIndicator(tmpVO.getMailCategoryCodeIndicator());
		setConsignmentCompletionDate(tmpVO.getConsignmentCompletionDate());
		setReqDeliveryTime(tmpVO.getReqDeliveryTime());
		setTransportStageQualifier(tmpVO.getTransportStageQualifier());
		setConveyanceReference(tmpVO.getConveyanceReference());
		setTransportIdentification(tmpVO.getTransportIdentification());
		setCarrierID(tmpVO.getCarrierID());
		setDepartureLocationQualifier(tmpVO.getDepartureLocationQualifier());
		setDeparturePlace(tmpVO.getDeparturePlace());
		setArrivalLocationQualifier(tmpVO.getArrivalLocationQualifier());
		setArrivalPlace(tmpVO.getArrivalPlace());
		setTransportInfoDepartureDate(tmpVO.getTransportInfoDepartureDate());
		setTransportInfoArrivalDate(tmpVO.getTransportInfoArrivalDate());
		setMailCategoryCode(tmpVO.getMailCategoryCode());
		setDomesticTransportStageQualifier(tmpVO.getDomesticTransportStageQualifier());
		setDomesticConveyanceReference(tmpVO.getDomesticConveyanceReference());
		setDomesticTransportIdentification(tmpVO.getDomesticTransportIdentification());
		setDomesticCarrierID(tmpVO.getDomesticCarrierID());
		setModeOfTransport(tmpVO.getModeOfTransport());
		setCarrierName(tmpVO.getCarrierName());
		setCodeListQualifier(tmpVO.getCodeListQualifier());
		setAgencyForCarrierCode(tmpVO.getAgencyForCarrierCode());
		setTransportLegRate(tmpVO.getTransportLegRate());
		setContractReference(tmpVO.getContractReference());
		setDomesticDepartureLocationQualifier(tmpVO.getDomesticDepartureLocationQualifier());
		setDomesticDeparturePlace(tmpVO.getDomesticDeparturePlace());
		setDomesticArrivalLocationQualifier(tmpVO.getDomesticArrivalLocationQualifier());
		setDomesticArrivalPlace(tmpVO.getDomesticArrivalPlace());
		setDeparturePlaceName(tmpVO.getDeparturePlaceName());
		setDepartureCodeListQualifier(tmpVO.getDepartureCodeListQualifier());
		setDepartureCodeListAgency(tmpVO.getDepartureCodeListAgency());
		setArrivalPlaceName(tmpVO.getArrivalPlaceName());
		setArrivalCodeListQualifier(tmpVO.getArrivalCodeListQualifier());
		setArrivalCodeListAgency(tmpVO.getArrivalCodeListAgency());
		setTransportInfoDateTimePeriodQualifier(tmpVO.getTransportInfoDateTimePeriodQualifier());
		setTransportInfoDateTimeFormatQualifier(tmpVO.getTransportInfoDateTimeFormatQualifier());
		setDepartureDate(tmpVO.getDepartureDate());
		setArrivalDate(tmpVO.getArrivalDate());
		setMailClassCode(tmpVO.getMailClassCode());
		setControlValue(tmpVO.getControlValue());
		setTotalPiecesControlQualifier(tmpVO.getTotalPiecesControlQualifier());
		setNumberOfReceptacles(tmpVO.getNumberOfReceptacles());
		setTotalPiecesMeasureUnitQualifier(tmpVO.getTotalPiecesMeasureUnitQualifier());
		setTotalWeightControlQualifier(tmpVO.getTotalWeightControlQualifier());
		setWeightOfReceptacles(tmpVO.getWeightOfReceptacles());
		setTotalWeightMeasureUnitQualifier(tmpVO.getTotalWeightMeasureUnitQualifier());
		setConsignmentContractReferenceNumber(tmpVO.getConsignmentContractReferenceNumber());
		setTransportContractReferenceQualifier(tmpVO.getTransportContractReferenceQualifier());
		setRefInfoTransportStageQualifier(tmpVO.getRefInfoTransportStageQualifier());
		setHandoverOriginLocationQualifier(tmpVO.getHandoverOriginLocationQualifier());
		setHandoverOriginLocationIdentifier(tmpVO.getHandoverOriginLocationIdentifier());
		setHandoverOriginLocationName(tmpVO.getHandoverOriginLocationName());
		setHandoverOriginCodeListQualifier(tmpVO.getHandoverOriginCodeListQualifier());
		setHandoverOriginCodeListAgency(tmpVO.getHandoverOriginCodeListAgency());
		setHandoverDestnLocationQualifier(tmpVO.getHandoverDestnLocationQualifier());
		setHandoverDestnLocationIdentifier(tmpVO.getHandoverDestnLocationIdentifier());
		setHandoverDestnLocationName(tmpVO.getHandoverDestnLocationName());
		setHandoverDestnCodeListQualifier(tmpVO.getHandoverDestnCodeListQualifier());
		setHandoverDestnCodeListAgency(tmpVO.getHandoverDestnCodeListAgency());
		setHandOverInfoDateTimePeriodQualifier(tmpVO.getHandOverInfoDateTimePeriodQualifier());
		setHandOverInfoDateTimeFormatQualifier(tmpVO.getHandOverInfoDateTimeFormatQualifier());
		setOriginCutOffPeriod(tmpVO.getOriginCutOffPeriod());
		setDestinationCutOffPeriod(tmpVO.getDestinationCutOffPeriod());
		setEquipmentQualifier(tmpVO.getEquipmentQualifier());
		setContainerNumber(tmpVO.getContainerNumber());
		setContainerInfoCodeListResponsibleAgency(tmpVO.getContainerInfoCodeListResponsibleAgency());
		setContainerType(tmpVO.getContainerType());
		setContainerInfoCodeListQualifier(tmpVO.getContainerInfoCodeListQualifier());
		setTypeCodeListResponsibleAgency(tmpVO.getTypeCodeListResponsibleAgency());
		setContainerJourneyIdentifier(tmpVO.getContainerJourneyIdentifier());
		setMeasurementApplication(tmpVO.getMeasurementApplication());
		setMeasurementDimension(tmpVO.getMeasurementDimension());
		setContainerInfoMeasureUnitQualifier(tmpVO.getContainerInfoMeasureUnitQualifier());
		setContainerWeight(tmpVO.getContainerWeight());
		setSealNumber(tmpVO.getSealNumber());
		setMessageSeqNum(tmpVO.getMessageSeqnum());
		setIsProcessed("N");
		setPostalAwbNumber(tmpVO.getPostalawbnumber());
		setShipperUpuCode(tmpVO.getShipperUpuCode());
		setConsigneeUpuCode(tmpVO.getConsigneeUpuCode());
		setConsignmentOrigin(tmpVO.getConsignmentOrigin());
		setConsignmentDestination(tmpVO.getConsignmentDestination());
		
		log.exiting("CarditTempDetails","populateAttributes");
		
	}

	/**
	 * 	Getter for mailBagVOs 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	@OneToMany
	@JoinColumns({
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
		@JoinColumn(name = "SEQNUM", referencedColumnName = "SEQNUM", insertable = false, updatable = false),
		@JoinColumn(name = "CNSMNTIDR", referencedColumnName = "CNSMNTIDR", insertable = false, updatable = false)
	})
	public Set<CarditTempMailBagDetails> getMailBagVOs() {
		return mailBagVOs;
	}

	/**
	 *  @param mailBagVOs the mailBagVOs to set
	 * 	Setter for mailBagVOs 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	public void setMailBagVOs(Set<CarditTempMailBagDetails> mailBagVOs) {
		this.mailBagVOs = mailBagVOs;
	}

	/**
	 * 	Method		:	CarditTempDetails.find
	 *	Added by 	:	A-6287 on 03-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditTempDetailsPK2
	 *	Parameters	:	@return 
	 *	Return type	: 	CarditTempDetails
	 * @throws SystemException 
	 * @throws FinderException 
	 */
	public static CarditTempDetails find(CarditTempDetailsPK pk) throws FinderException, SystemException {
		
		 return PersistenceController.getEntityManager().find(CarditTempDetails.class,pk);
	}

	/**
	 * 	Getter for errorCodes 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	@Column(name="ERRCOD")
	public String getErrorCodes() {
		return errorCodes;
	}

	/**
	 *  @param errorCodes the errorCodes to set
	 * 	Setter for errorCodes 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	public void setErrorCodes(String errorCodes) {
		this.errorCodes = errorCodes;
	}

	/**
	 * 	Getter for messageStatus 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	@Column(name="PRCSTA")
	public String getMessageStatus() {
		return messageStatus;
	}

	/**
	 *  @param messageStatus the messageStatus to set
	 * 	Setter for messageStatus 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	/**
	 * 	Getter for isProcessed 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	@Column(name="MSGPRC")
	public String getIsProcessed() {
		return isProcessed;
	}

	/**
	 *  @param isProcessed the isProcessed to set
	 * 	Setter for isProcessed 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	public void setIsProcessed(String isProcessed) {
		this.isProcessed = isProcessed;
	}
	@Column(name="POSAWBNUM")
	public String getPostalAwbNumber() {
		return postalAwbNumber;
	}

	public void setPostalAwbNumber(String postalAwbNumber) {
		this.postalAwbNumber = postalAwbNumber;
	}
	@Column(name="SHPUPUCOD")
	public String getShipperUpuCode() {
		return shipperUpuCode;
	}

	public void setShipperUpuCode(String shipperUpuCode) {
		this.shipperUpuCode = shipperUpuCode;
	}
	@Column(name="CNSUPUCOD")
	public String getConsigneeUpuCode() {
		return consigneeUpuCode;
	}

	public void setConsigneeUpuCode(String consigneeUpuCode) {
		this.consigneeUpuCode = consigneeUpuCode;
	}
	@Column(name="CSGORGEXGOFCCOD")
	public String getConsignmentOrigin() {
		return consignmentOrigin;
	}

	public void setConsignmentOrigin(String consignmentOrigin) {
		this.consignmentOrigin = consignmentOrigin;
	}
	@Column(name="CSGDSTEXGOFCCOD")
	public String getConsignmentDestination() {
		return consignmentDestination;
	}

	public void setConsignmentDestination(String consignmentDestination) {
		this.consignmentDestination = consignmentDestination;
	}
	
	
}
