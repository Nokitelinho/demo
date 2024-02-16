/*
 * CarditVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
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
 * Jun 30, 2016  A-5991 First Draft
 */
public class CarditVO extends AbstractVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companyCode;

	/**
	 * Cardit Key
	 */
	private String carditKey;

	/**
	 * Interchange reference
	 */
	private String interchangeCtrlReference;

	/**
	 * Syntax id of this interchange
	 */
	private String interchangeSyntaxId;

	/**
	 * Syntax version
	 */
	private int interchangeSyntaxVer;

	/**
	 * Recipient idr
	 */
	private String recipientId;

	/**
	 * Sender id
	 */
	private String senderId;
	
	/**
	 * Actual Sender Id
	 */
	private String actualSenderId;

	/**
	 * consignment completion date
	 */
	private LocalDate consignmentDate;

	/**
	 * Interchange preparation date
	 */
	private LocalDate preparationDate;

	/**
	 * Consignment doc num
	 */
	private String consignmentNumber;

	/**
	 * message name
	 */
	private String messageName;

	/**
	 * mail catergory code
	 */
	private String mailCategoryCode;

	/**
	 * Msg reference num
	 */
	private String messageRefNum;

	/**
	 * message version
	 */
	private String messageVersion;

	/**
	 * message segment number
	 */
	private int messageSegmentNum;

	/**
	 * message release number
	 */
	private String messageReleaseNum;

	/**
	 * Message type identifier
	 */
	private String messageTypeId;

	/**
	 * testIndicator
	 */
	private int tstIndicator;

	/**
	 * Controlling agency
	 */
	private String controlAgency;

	/**
	 * Interchange control count
	 */
	private int interchangeControlCnt;

	/**
	 * cardit
	 */
	private LocalDate carditReceivedDate;

	/**
	 * last update user
	 */
	private String lastUpdateUser;
	
	private long msgSeqNum;
	
	private String stationCode;	
	
	private String messageFunction;
	
	private Collection<CarditTransportationVO> transportInformation;

	private Collection<CarditReceptacleVO> receptacleInformation;

	private Collection<CarditContainerVO> containerInformation;

	private Collection<CarditTotalVO> totalsInformation;
	
	/* DONE FOR M39*/
	private String associationAssignedCode;	
	
	private String carditType;
	
	private boolean isSenderIdChanged;
	
    /**
     * Store the Handover InformationVO of this cardit
     */
    private Collection<CarditHandoverInformationVO> handoverInformation;
    
    /**
     * Store the Reference InformationVO of this cardit
     */
    private Collection<CarditReferenceInformationVO> referenceInformation;

    /**
     * COntract reference number
     */
    private String contractReferenceNumber;

	private Collection<ConsignmentScreeningVO> consignementScreeningVOs;
	private String securityStatusParty;
	private String securityStatusCode;
	private String additionalSecurityInfo;
	private LocalDate securityStatusDate;
	private String applicableRegTransportDirection;
	private String applicableRegBorderAgencyAuthority;
	private String applicableRegReferenceID;
	private String applicableRegFlag;
	private CarditPawbDetailsVO carditPawbDetailsVO;
	private String consignmentIssuerName;
	 
	public Collection<ConsignmentScreeningVO> getConsignementScreeningVOs() {
		return consignementScreeningVOs;
	}
	public void setConsignementScreeningVOs(Collection<ConsignmentScreeningVO> consignementScreeningVOs) {
		this.consignementScreeningVOs = consignementScreeningVOs;
	}
	public String getSecurityStatusParty() {
		return securityStatusParty;
	}
	public void setSecurityStatusParty(String securityStatusParty) {
		this.securityStatusParty = securityStatusParty;
	}
	public String getSecurityStatusCode() {
		return securityStatusCode;
	}
	public void setSecurityStatusCode(String securityStatusCode) {
		this.securityStatusCode = securityStatusCode;
	}
	public String getAdditionalSecurityInfo() {
		return additionalSecurityInfo;
	}
	public void setAdditionalSecurityInfo(String additionalSecurityInfo) {
		this.additionalSecurityInfo = additionalSecurityInfo;
	}
	public LocalDate getSecurityStatusDate() {
		return securityStatusDate;
	}
	public void setSecurityStatusDate(LocalDate securityStatusDate) {
		this.securityStatusDate = securityStatusDate;
	}
	public String getApplicableRegTransportDirection() {
		return applicableRegTransportDirection;
	}
	public void setApplicableRegTransportDirection(String applicableRegTransportDirection) {
		this.applicableRegTransportDirection = applicableRegTransportDirection;
	}
	public String getApplicableRegBorderAgencyAuthority() {
		return applicableRegBorderAgencyAuthority;
	}
	public void setApplicableRegBorderAgencyAuthority(String applicableRegBorderAgencyAuthority) {
		this.applicableRegBorderAgencyAuthority = applicableRegBorderAgencyAuthority;
	}
	public String getApplicableRegReferenceID() {
		return applicableRegReferenceID;
	}
	public void setApplicableRegReferenceID(String applicableRegReferenceID) {
		this.applicableRegReferenceID = applicableRegReferenceID;
	}
   public String getApplicableRegFlag() {
		return applicableRegFlag;
	}
	public void setApplicableRegFlag(String applicableRegFlag) {
		this.applicableRegFlag = applicableRegFlag;
	}
   /**
	 * @return the contractReferenceNumber
	 */
	public String getContractReferenceNumber() {
		return contractReferenceNumber;
	}

	/**
	 * @param contractReferenceNumber the contractReferenceNumber to set
	 */
	public void setContractReferenceNumber(String contractReferenceNumber) {
		this.contractReferenceNumber = contractReferenceNumber;
	}

	/**
	 * @return Returns the messageFunction.
	 */
	public String getMessageFunction() {
		return messageFunction;
	}

	/**
	 * @param messageFunction The messageFunction to set.
	 */
	public void setMessageFunction(String messageFunction) {
		this.messageFunction = messageFunction;
	}

	/**
	 * @return Returns the carditKey.
	 */
	public String getCarditKey() {
		return carditKey;
	}

	/**
	 * @param carditKey
	 *            The carditKey to set.
	 */
	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}

	/**
	 * @return Returns the carditReceivedDate.
	 */
	public LocalDate getCarditReceivedDate() {
		return carditReceivedDate;
	}

	/**
	 * @param carditReceivedDate
	 *            The carditReceivedDate to set.
	 */
	public void setCarditReceivedDate(LocalDate carditReceivedDate) {
		this.carditReceivedDate = carditReceivedDate;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the consignmentDate.
	 */
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}

	/**
	 * @param consignmentDate
	 *            The consignmentDate to set.
	 */
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	/**
	 * @return Returns the consignmentNumber.
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber
	 *            The consignmentNumber to set.
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return Returns the controlAgency.
	 */
	public String getControlAgency() {
		return controlAgency;
	}

	/**
	 * @param controlAgency
	 *            The controlAgency to set.
	 */
	public void setControlAgency(String controlAgency) {
		this.controlAgency = controlAgency;
	}

	/**
	 * @return Returns the interchangeControlCnt.
	 */
	public int getInterchangeControlCnt() {
		return interchangeControlCnt;
	}

	/**
	 * @param interchangeControlCnt
	 *            The interchangeControlCnt to set.
	 */
	public void setInterchangeControlCnt(int interchangeControlCnt) {
		this.interchangeControlCnt = interchangeControlCnt;
	}

	/**
	 * @return Returns the interchangeCtrlReference.
	 */
	public String getInterchangeCtrlReference() {
		return interchangeCtrlReference;
	}

	/**
	 * @param interchangeCtrlReference
	 *            The interchangeCtrlReference to set.
	 */
	public void setInterchangeCtrlReference(String interchangeCtrlReference) {
		this.interchangeCtrlReference = interchangeCtrlReference;
	}

	/**
	 * @return Returns the interchangeSyntaxId.
	 */
	public String getInterchangeSyntaxId() {
		return interchangeSyntaxId;
	}

	/**
	 * @param interchangeSyntaxId
	 *            The interchangeSyntaxId to set.
	 */
	public void setInterchangeSyntaxId(String interchangeSyntaxId) {
		this.interchangeSyntaxId = interchangeSyntaxId;
	}

	/**
	 * @return Returns the interchangeSyntaxVer.
	 */
	public int getInterchangeSyntaxVer() {
		return interchangeSyntaxVer;
	}

	/**
	 * @param interchangeSyntaxVer
	 *            The interchangeSyntaxVer to set.
	 */
	public void setInterchangeSyntaxVer(int interchangeSyntaxVer) {
		this.interchangeSyntaxVer = interchangeSyntaxVer;
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
	 * @return Returns the messageName.
	 */
	public String getMessageName() {
		return messageName;
	}

	/**
	 * @param messageName
	 *            The messageName to set.
	 */
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	/**
	 * @return Returns the messageRefNum.
	 */
	public String getMessageRefNum() {
		return messageRefNum;
	}

	/**
	 * @param messageRefNum
	 *            The messageRefNum to set.
	 */
	public void setMessageRefNum(String messageRefNum) {
		this.messageRefNum = messageRefNum;
	}

	/**
	 * @return Returns the messageReleaseNum.
	 */
	public String getMessageReleaseNum() {
		return messageReleaseNum;
	}

	/**
	 * @param messageReleaseNum
	 *            The messageReleaseNum to set.
	 */
	public void setMessageReleaseNum(String messageReleaseNum) {
		this.messageReleaseNum = messageReleaseNum;
	}

	/**
	 * @return Returns the messageSegmentNum.
	 */
	public int getMessageSegmentNum() {
		return messageSegmentNum;
	}

	/**
	 * @param messageSegmentNum
	 *            The messageSegmentNum to set.
	 */
	public void setMessageSegmentNum(int messageSegmentNum) {
		this.messageSegmentNum = messageSegmentNum;
	}

	/**
	 * @return Returns the messageTypeId.
	 */
	public String getMessageTypeId() {
		return messageTypeId;
	}

	/**
	 * @param messageTypeId
	 *            The messageTypeId to set.
	 */
	public void setMessageTypeId(String messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

	/**
	 * @return Returns the messageVersion.
	 */
	public String getMessageVersion() {
		return messageVersion;
	}

	/**
	 * @param messageVersion
	 *            The messageVersion to set.
	 */
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}

	/**
	 * @return Returns the preparationDate.
	 */
	public LocalDate getPreparationDate() {
		return preparationDate;
	}

	/**
	 * @param preparationDate
	 *            The preparationDate to set.
	 */
	public void setPreparationDate(LocalDate preparationDate) {
		this.preparationDate = preparationDate;
	}

	/**
	 * @return Returns the recipientId.
	 */
	public String getRecipientId() {
		return recipientId;
	}

	/**
	 * @param recipientId
	 *            The recipientId to set.
	 */
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	/**
	 * @return Returns the senderId.
	 */
	public String getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId
	 *            The senderId to set.
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return Returns the tstIndicator.
	 */
	public int getTstIndicator() {
		return tstIndicator;
	}

	/**
	 * @param tstIndicator
	 *            The tstIndicator to set.
	 */
	public void setTstIndicator(int tstIndicator) {
		this.tstIndicator = tstIndicator;
	}

	/**
	 * @return Returns the containerInformation.
	 */
	public Collection<CarditContainerVO> getContainerInformation() {
		return containerInformation;
	}

	/**
	 * @param containerInformation
	 *            The containerInformation to set.
	 */
	public void setContainerInformation(
			Collection<CarditContainerVO> containerInformation) {
		this.containerInformation = containerInformation;
	}

	/**
	 * @return Returns the receptacleInformation.
	 */
	public Collection<CarditReceptacleVO> getReceptacleInformation() {
		return receptacleInformation;
	}

	/**
	 * @param receptacleInformation
	 *            The receptacleInformation to set.
	 */
	public void setReceptacleInformation(
			Collection<CarditReceptacleVO> receptacleInformation) {
		this.receptacleInformation = receptacleInformation;
	}

	/**
	 * @return Returns the totalsInformation.
	 */
	public Collection<CarditTotalVO> getTotalsInformation() {
		return totalsInformation;
	}

	/**
	 * @param totalsInformation
	 *            The totalsInformation to set.
	 */
	public void setTotalsInformation(Collection<CarditTotalVO> totalsInformation) {
		this.totalsInformation = totalsInformation;
	}

	/**
	 * @return Returns the transportInformation.
	 */
	public Collection<CarditTransportationVO> getTransportInformation() {
		return transportInformation;
	}

	/**
	 * @param transportInformation
	 *            The transportInformation to set.
	 */
	public void setTransportInformation(
			Collection<CarditTransportationVO> transportInformation) {
		this.transportInformation = transportInformation;
	}

	/**
	 * @return Returns the msgSeqNum.
	 */
	public long getMsgSeqNum() {
		return msgSeqNum;
	}

	/**
	 * @param msgSeqNum The msgSeqNum to set.
	 */
	public void setMsgSeqNum(long msgSeqNum) {
		this.msgSeqNum = msgSeqNum;
	}

	/**
	 * @return Returns the stationCode.
	 */
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	/**
	 * @return the associationAssignedCode
	 */
	public String getAssociationAssignedCode() {
		return associationAssignedCode;
	}

	/**
	 * @param associationAssignedCode the associationAssignedCode to set
	 */
	public void setAssociationAssignedCode(String associationAssignedCode) {
		this.associationAssignedCode = associationAssignedCode;
	}

	/**
	 * @return the handoverInformation
	 */
	public Collection<CarditHandoverInformationVO> getHandoverInformation() {
		return handoverInformation;
	}

	/**
	 * @param handoverInformation the handoverInformation to set
	 */
	public void setHandoverInformation(
			Collection<CarditHandoverInformationVO> handoverInformation) {
		this.handoverInformation = handoverInformation;
	}

	/**
	 * @return the referenceInformation
	 */
	public Collection<CarditReferenceInformationVO> getReferenceInformation() {
		return referenceInformation;
	}

	/**
	 * @param referenceInformation the referenceInformation to set
	 */
	public void setReferenceInformation(
			Collection<CarditReferenceInformationVO> referenceInformation) {
		this.referenceInformation = referenceInformation;
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
	 * @return the actualSenderId
	 */
	public String getActualSenderId() {
		return actualSenderId;
	}

	/**
	 * @param actualSenderId the actualSenderId to set
	 */
	public void setActualSenderId(String actualSenderId) {
		this.actualSenderId = actualSenderId;
	}

	public boolean isSenderIdChanged() {
		return isSenderIdChanged;
	}

	public void setSenderIdChanged(boolean isSenderIdChanged) {
		this.isSenderIdChanged = isSenderIdChanged;
	}
	public CarditPawbDetailsVO getCarditPawbDetailsVO() {
		return carditPawbDetailsVO;
	}
	public void setCarditPawbDetailsVO(CarditPawbDetailsVO carditPawbDetailsVO) {
		this.carditPawbDetailsVO = carditPawbDetailsVO;
	}
	
	public String getConsignmentIssuerName() {
			return consignmentIssuerName;
		}
		public void setConsignmentIssuerName(String consignmentIssuerName) {
			this.consignmentIssuerName = consignmentIssuerName;
		}

}

