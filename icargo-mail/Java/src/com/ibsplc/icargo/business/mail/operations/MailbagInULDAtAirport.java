/*
 * MailbagInULDAtAirport.java Created on Jun 27, 2016
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.MailbagInULDAtAirportPK;

import com.ibsplc.icargo.business.mail.operations.MailbagInULDAtAirport;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author a-5991
 * 
 */
@Entity
@Table(name = "MALARPULDDTL")
@Staleable
public class MailbagInULDAtAirport {

	private MailbagInULDAtAirportPK mailbagInULDAtAirportPK;

	/*
	 * ULDNumber for ULD and barrow number for Bulk
	 */
	private String containerNumber;

	private String damageFlag;

	private Calendar scannedDate;

	private double weight;

	private String mailClass;

	private String transferFromCarrier;

	private String sealNumber;

	private long documentNumber;
	private String masterDocumentNumber;
	private int duplicateNumber;
	private int sequenceNumber;
	private String documentOwnerCode;
	private int acceptedBags;
	private double acceptedWeight;
	private int statedBags;
	private double statedWeight;

	/**
	 * @return Returns the containerNumber.
	 * 
	 */
	@Column(name = "CONNUM")
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
	 * @return Returns the damageFlag.
	 * 
	 */
	@Column(name = "DMGFLG")
	public String getDamageFlag() {
		return damageFlag;
	}

	/**
	 * @param damageFlag
	 *            The damageFlag to set.
	 */
	public void setDamageFlag(String damageFlag) {
		this.damageFlag = damageFlag;
	}

	/**
	 * @return Returns the mailbagInULDAtAirportPK.
	 * 
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),

			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM")),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")) })
	public MailbagInULDAtAirportPK getMailbagInULDAtAirportPK() {
		return mailbagInULDAtAirportPK;
	}

	/**
	 * @param mailbagInULDAtAirportPK
	 *            The mailbagInULDAtAirportPK to set.
	 */
	public void setMailbagInULDAtAirportPK(
			MailbagInULDAtAirportPK mailbagInULDAtAirportPK) {
		this.mailbagInULDAtAirportPK = mailbagInULDAtAirportPK;
	}

	/**
	 * @return Returns the scannedDate.
	 * 
	 */
	@Column(name = "SCNDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getScannedDate() {
		return scannedDate;
	}

	/**
	 * @param scannedDate
	 *            The scannedDate to set.
	 */
	public void setScannedDate(Calendar scannedDate) {
		this.scannedDate = scannedDate;
	}



	/**
	 * @return Returns the fromTransferCarrier.
	 */
	@Column(name = "FRMCARCOD")
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}

	/**
	 * @param fromTransferCarrier
	 *            The fromTransferCarrier to set.
	 */
	public void setTransferFromCarrier(String fromTransferCarrier) {
		this.transferFromCarrier = fromTransferCarrier;
	}

	/**
	 * @return the documentNumber
	 * 
	 */
	@Column(name = "DOCOWRIDR")
	public long getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber
	 *            the documentNumber to set
	 */
	public void setDocumentNumber(long documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**
	 * @return the masterDocumentNumber
	 */
	@Column(name = "MSTDOCNUM")
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}

	/**
	 * @param masterDocumentNumber
	 *            the masterDocumentNumber to set
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}

	/**
	 * @return the duplicateNumber
	 */
	@Column(name = "DUPNUM")
	public int getDuplicateNumber() {
		return duplicateNumber;
	}

	/**
	 * @param duplicateNumber
	 *            the duplicateNumber to set
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}

	/**
	 * @return the sequenceNumber
	 */
	@Column(name = "SEQNUM")
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            the sequenceNumber to set
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the documentOwnerCode
	 */
	@Column(name = "DOCOWRCOD")
	public String getDocumentOwnerCode() {
		return documentOwnerCode;
	}

	/**
	 * @param documentOwnerCode
	 *            the documentOwnerCode to set
	 */
	public void setDocumentOwnerCode(String documentOwnerCode) {
		this.documentOwnerCode = documentOwnerCode;
	}

	/**
	 * @return the acceptedBags
	 */
	@Column(name = "ACPBAG")
	public int getAcceptedBags() {
		return acceptedBags;
	}

	/**
	 * @param acceptedBags
	 *            the acceptedBags to set
	 */
	public void setAcceptedBags(int acceptedBags) {
		this.acceptedBags = acceptedBags;
	}

	/**
	 * @return the acceptedWeight
	 */
	@Column(name = "ACPWGT")
	public double getAcceptedWeight() {
		return acceptedWeight;
	}

	/**
	 * @param acceptedWeight
	 *            the acceptedWeight to set
	 */
	public void setAcceptedWeight(double acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}

	/**
	 * @return the statedBags
	 */
	@Column(name = "STDBAG")
	public int getStatedBags() {
		return statedBags;
	}

	/**
	 * @param statedBags
	 *            the statedBags to set
	 */
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}

	/**
	 * @return the statedWeight
	 */
	@Column(name = "STDWGT")
	public double getStatedWeight() {
		return statedWeight;
	}

	/**
	 * @param statedWeight
	 *            the statedWeight to set
	 */
	public void setStatedWeight(double statedWeight) {
		this.statedWeight = statedWeight;
	}

	/**
	 * A-5991
	 * 
	 * @param mailbagInAirportPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailbagInULDAtAirport find(
			MailbagInULDAtAirportPK mailbagInAirportPK) throws FinderException,
			SystemException {
		return PersistenceController.getEntityManager().find(
				MailbagInULDAtAirport.class, mailbagInAirportPK);
	}

	public MailbagInULDAtAirport() {

	}

	/**
	 * @author A-5991
	 * @param dsnInULDAtAirportPk
	 * @param mailbagInULDVO
	 * @throws SystemException
	 */
	public MailbagInULDAtAirport(ULDAtAirportPK uldAtAirportPK,
			MailbagInULDAtAirportVO mailbagInULDVO) throws SystemException {
		populatePK(uldAtAirportPK, mailbagInULDVO);
		populateAttributes(mailbagInULDVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}

	}

	/**
	 * A-5991
	 * 
	 * @param dsnInULDAtAirportPk
	 * @param mailbagInULDVO
	 */
	private void populatePK(ULDAtAirportPK uldAtAirportPK,
			MailbagInULDAtAirportVO mailbagInULDVO) {
		mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		if (uldAtAirportPK!=null && uldAtAirportPK.getCompanyCode() != null) {
			mailbagInULDAtAirportPK.setCompanyCode(uldAtAirportPK
					.getCompanyCode());
			mailbagInULDAtAirportPK.setCarrierId(uldAtAirportPK.getCarrierId());
			mailbagInULDAtAirportPK.setAirportCode(uldAtAirportPK
					.getAirportCode());
			mailbagInULDAtAirportPK.setUldNumber(uldAtAirportPK.getUldNumber());
		} else {
			mailbagInULDAtAirportPK.setCompanyCode(mailbagInULDVO
					.getComapnyCode());
		mailbagInULDAtAirportPK.setCarrierId(mailbagInULDVO.getCarrierId());
			mailbagInULDAtAirportPK.setAirportCode(mailbagInULDVO
					.getAirportCode());
		mailbagInULDAtAirportPK.setUldNumber(mailbagInULDVO.getUldNumber());
		}
		mailbagInULDAtAirportPK.setMailSequenceNumber(mailbagInULDVO
				.getMailSequenceNumber());

	}

	/**
	 * @author A-5991
	 * @param mailbagInULDVO
	 */
	private void populateAttributes(MailbagInULDAtAirportVO mailbagInULDVO) {
		setContainerNumber(mailbagInULDVO.getContainerNumber());
		if(mailbagInULDVO.getDamageFlag()!=null){
		setDamageFlag(mailbagInULDVO.getDamageFlag());
		}
		else{
			setDamageFlag(MailbagInULDAtAirportVO.FLAG_NO);
		}
		setScannedDate(mailbagInULDVO.getScannedDate().toCalendar());
		//setWeight(mailbagInULDVO.getWeight());
		mailbagInULDVO.setAcceptedBags(1);
		mailbagInULDVO.setAcceptedWgt(mailbagInULDVO.getWeight());
		//setMailClass(mailbagInULDVO.getMailClass());
		setTransferFromCarrier(mailbagInULDVO.getTransferFromCarrier());
		//setSealNumber(mailbagInULDVO.getSealNumber());
		setAcceptedBags(mailbagInULDVO.getAcceptedBags());
		//setAcceptedWeight(mailbagInULDVO.getAcceptedWgt());
		if(mailbagInULDVO.getAcceptedWgt()!=null){
		setAcceptedWeight(mailbagInULDVO.getAcceptedWgt().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		}
		setStatedBags(mailbagInULDVO.getStatedBags());
		//setStatedWeight(mailbagInULDVO.getStatedWgt());
		if(mailbagInULDVO.getStatedWgt()!=null){
		setStatedWeight(mailbagInULDVO.getStatedWgt().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		}
	}

	/**
	 * @author A-5991
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
	 * A-1739
	 * 
	 * @return
	 */
	public MailbagInULDAtAirportVO retrieveVO() {
		MailbagInULDAtAirportVO mailbagInULDVO = new MailbagInULDAtAirportVO();
		MailbagInULDAtAirportPK mailbagInULDPK = getMailbagInULDAtAirportPK();
		mailbagInULDVO.setMailSequenceNumber(mailbagInULDPK
				.getMailSequenceNumber());
		mailbagInULDVO.setContainerNumber(getContainerNumber());
		mailbagInULDVO.setDamageFlag(getDamageFlag());
		mailbagInULDVO.setScannedDate(new LocalDate(mailbagInULDPK
				.getAirportCode(), Location.ARP, getScannedDate(), true));
		//mailbagInULDVO.setWeight(getAcceptedWeight());
		mailbagInULDVO.setWeight(new Measure(UnitConstants.MAIL_WGT,getAcceptedWeight()));//added by A-7371
	//	mailbagInULDVO.setMailClass(getMailClass());
		mailbagInULDVO.setTransferFromCarrier(getTransferFromCarrier());
		return mailbagInULDVO;
	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param containerVO
	 * @param airportCode
	 */
	public void updateHandoverReceivedCarrierForTransfer(MailbagVO mailbagVO,
			ContainerVO containerVO,String  airportCode){
		MailbagInULDAtAirportPK mailbagInULDAirportPK=new MailbagInULDAtAirportPK();
		mailbagInULDAirportPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagInULDAirportPK.setCarrierId(mailbagVO.getCarrierId());
		mailbagInULDAirportPK.setAirportCode(airportCode);
		mailbagInULDAirportPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		if(MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())&&containerVO.getFinalDestination()!=null){
			mailbagInULDAirportPK.setUldNumber(MailConstantsVO.CONST_BULK+MailConstantsVO.SEPARATOR+containerVO.getFinalDestination());	
		}else{
			mailbagInULDAirportPK.setUldNumber(mailbagVO.getContainerNumber());
		}
		MailbagInULDAtAirport mailbagInULDAtArp = null;
			try {
				mailbagInULDAtArp=find(mailbagInULDAirportPK);
			} catch (FinderException | SystemException e) {
				e.getMessage();
			}
		if (mailbagInULDAtArp!=null){
			mailbagInULDAtArp.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
		}
	}

}
