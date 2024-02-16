/*
 * MailbagInULDForSegment.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.MailbagInULDForSegmentPK;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author a-5991
 *
 */
@Entity
@Table(name = "MALULDSEGDTL")

public class MailbagInULDForSegment {
	private Log log =LogFactory.getLogger("mail.operations");
	private static final String MODULE = "mail.operations";

	private MailbagInULDForSegmentPK mailbagInULDForSegmentPK;

	/*
	 * ULDNumber for ULD and barrow number for Bulk
	 */
	private String containerNumber;

	private String damageFlag;

	private String scannedPort;

	private Calendar scannedDate;

	private double weight;

	private String acceptanceFlag;

	private String arrivalFlag;

	private String deliveredStatus;

	//will be Y if transferred
	private String transferFlag;


   private String mailClass;

   
   /**
    * Control Document Number;
    */
   private String controlDocumentNumber;

   private String transferFromCarrier;

   private String transferToCarrier;

   private String mraStatus;

   private Calendar  lastUpdateTime;

   private String  lastUpdateUser;

  // private String sealNumber;

   private String arrivalsealNumber;

	//private long documentNumber;
	//private String masterDocumentNumber;
	//private int duplicateNumber;
	//private int sequenceNumber;
	private String documentOwnerCode;
	private int recievedBags;
	private double recievedWeight;
	private int statedBags;
	private double statedWeight;
	private int deliveredBags;
	private double deliveredWeight;
	private int acceptedBags;
	private double acceptedWeight;
	private String acceptedUser;
	private Calendar acceptedDate;
	private Calendar receivedDate;
	private Calendar ghttim;
	private String serviceResponsive;//added by A-7371 as part of ICRD-240340

	/**
	 * @return Returns the containerNumber.
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
		this.containerNumber =  containerNumber;
	}

	/**
	 * @return Returns the deliveredStatus.
	 */
	@Column(name = "DLVSTA")
	public String getDeliveredStatus() {
		return deliveredStatus;
	}

	/**
	 * @param deliveredStatus
	 *            The deliveredStatus to set.
	 */
	public void setDeliveredStatus(String deliveredStatus) {
		this.deliveredStatus = deliveredStatus;
	}

	/**
	 * @return Returns the damageFlag.
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
	 * @return Returns the mailbagInULDForSegmentPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "segmentSerialNumber", column = @Column(name = "SEGSERNUM")),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")) })
	public MailbagInULDForSegmentPK getMailbagInULDForSegmentPK() {
		return mailbagInULDForSegmentPK;
	}

	/**
	 * @param mailbagInULDForSegmentPK
	 *            The mailbagInULDForSegmentPK to set.
	 */
	public void setMailbagInULDForSegmentPK(
			MailbagInULDForSegmentPK mailbagInULDForSegmentPK) {
		this.mailbagInULDForSegmentPK = mailbagInULDForSegmentPK;
	}

	/**
	 * @return Returns the scannedDate.
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
	 * @return Returns the scannedPort.
	 */
	@Column(name = "SCNPRT")
	public String getScannedPort() {
		return scannedPort;
	}

	/**
	 * @param scannedPort
	 *            The scannedPort to set.
	 */
	public void setScannedPort(String scannedPort) {
		this.scannedPort = scannedPort;
	}


	/**
	 * @return Returns the weight.
	 */
	@Column(name = "WGT")
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            The weight to set.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}



	/**
	 * @return Returns the acceptanceFlag.
	 */
	@Column(name = "ACPSTA")
	public String getAcceptanceFlag() {
		return acceptanceFlag;
	}

	/**
	 * @param acceptanceFlag
	 *            The acceptanceFlag to set.
	 */
	public void setAcceptanceFlag(String acceptanceFlag) {
		this.acceptanceFlag = acceptanceFlag;
	}

	/**
	 * @return Returns the arrivalFlag.
	 */
	@Column(name = "ARRSTA")
	public String getArrivalFlag() {
		return arrivalFlag;
	}

	/**
	 * @param arrivalFlag
	 *            The arrivalFlag to set.
	 */
	public void setArrivalFlag(String arrivalFlag) {
		this.arrivalFlag = arrivalFlag;
	}


	/**
	 * @return Returns the tranferFlag.
	 */
	@Column(name="TRAFLG")
	public String getTransferFlag() {
		return transferFlag;
	}

	/**
	 * @param tranferFlag The tranferFlag to set.
	 */
	public void setTransferFlag(String tranferFlag) {
		this.transferFlag = tranferFlag;
	}




	/**
	 * @return Returns the controlDocumentNumber.
	 */
	@Column(name="CNTDOCNUM")
	public String getControlDocumentNumber() {
		return controlDocumentNumber;
	}

	/**
	 * @param controlDocumentNumber The controlDocumentNumber to set.
	 */
	public void setControlDocumentNumber(String controlDocumentNumber) {
		this.controlDocumentNumber = controlDocumentNumber;
	}

	
	/**
	 * @return Returns the transferFromCarrier.
	 */
	@Column(name="FRMCARCOD")
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}

	/**
	 * @param transferFromCarrier The transferFromCarrier to set.
	 */
	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
	}

	/**
	 * @return Returns the transferToCarrier.
	 */
	@Column(name="TRFCARCOD")
	public String getTransferToCarrier() {
		return transferToCarrier;
	}

	/**
	 * @param transferToCarrier The transferToCarrier to set.
	 */
	public void setTransferToCarrier(String transferToCarrier) {
		this.transferToCarrier = transferToCarrier;
	}



	/**
	 * @return Returns the mraStatus.
	 */
	@Column(name="MRASTA")
	public String getMraStatus() {
		return mraStatus;
	}

	/**
	 * @param mraStatus The mraStatus to set.
	 */
	public void setMraStatus(String mraStatus) {
		this.mraStatus = mraStatus;
	}
 /*
  * Added By Karthick V as the part of the Optimsistic Locking Mechanism
  *
  *
  */

	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return the sealNumber
	 *//*
	@Column(name = "SELNUM")
	public String getSealNumber() {
		return sealNumber;
	}

	*//**
	 * @param sealNumber the sealNumber to set
	 *//*
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
*/
	/**
	 * @return the arrivalsealNumber
	 */
	@Column(name = "ARRSELNUM")
	public String getArrivalsealNumber() {
		return arrivalsealNumber;
	}

	/**
	 * @param arrivalsealNumber the arrivalsealNumber to set
	 */
	public void setArrivalsealNumber(String arrivalsealNumber) {
		this.arrivalsealNumber = arrivalsealNumber;
	}




	/**
	 * @return the documentNumber
	 *//*
	@Column(name = "DOCOWRIDR")
	public long getDocumentNumber() {
		return documentNumber;
	}

	*//**
	 * @param documentNumber the documentNumber to set
	 *//*
	public void setDocumentNumber(long documentNumber) {
		this.documentNumber = documentNumber;
	}

	*//**
	 * @return the masterDocumentNumber
	 *//*
	@Column(name = "MSTDOCNUM")
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}

	*//**
	 * @param masterDocumentNumber the masterDocumentNumber to set
	 *//*
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}

	*//**
	 * @return the duplicateNumber
	 *//*
	@Column(name = "DUPNUM")
	public int getDuplicateNumber() {
		return duplicateNumber;
	}

	*//**
	 * @param duplicateNumber the duplicateNumber to set
	 *//*
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}

	*//**
	 * @return the sequenceNumber
	 *//*
	@Column(name = "SEQNUM")
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	*//**
	 * @param sequenceNumber the sequenceNumber to set
	 *//*
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}*/

	/**


	/**
	 * @return the recievedBags
	 */
	@Column(name = "RCVBAG")
	public int getRecievedBags() {
		return recievedBags;
	}

	/**
	 * @param recievedBags the recievedBags to set
	 */
	public void setRecievedBags(int recievedBags) {
		this.recievedBags = recievedBags;
	}

	/**
	 * @return the recievedWeight
	 */
	@Column(name = "RCVWGT")
	public double getRecievedWeight() {
		return recievedWeight;
	}

	/**
	 * @param recievedWeight the recievedWeight to set
	 */
	public void setRecievedWeight(double recievedWeight) {
		this.recievedWeight = recievedWeight;
	}

	/**
	 * @return the statedBags
	 */
	@Column(name = "STDBAG")
	public int getStatedBags() {
		return statedBags;
	}

	/**
	 * @param statedBags the statedBags to set
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
	 * @param statedWeight the statedWeight to set
	 */
	public void setStatedWeight(double statedWeight) {
		this.statedWeight = statedWeight;
	}

	/**
	 * @return the deliveredBags
	 */
	@Column(name = "DLVBAG")
	public int getDeliveredBags() {
		return deliveredBags;
	}

	/**
	 * @param deliveredBags the deliveredBags to set
	 */
	public void setDeliveredBags(int deliveredBags) {
		this.deliveredBags = deliveredBags;
	}


	/**
	 * @return the deliveredWeight
	 */
	@Column(name = "DLVWGT")
	public double getDeliveredWeight() {
		return deliveredWeight;
	}

	/**
	 * @param deliveredWeight the deliveredWeight to set
	 */
	public void setDeliveredWeight(double deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}


	/**
	 * @return the acceptedBags
	 */
	@Column(name="ACPBAG")
	public int getAcceptedBags() {
		return acceptedBags;
	}

	/**
	 * @param acceptedBags the acceptedBags to set
	 */
	public void setAcceptedBags(int acceptedBags) {
		this.acceptedBags = acceptedBags;
	}
	/**
	 * @return the acceptedWeight
	 */
	@Column(name="ACPWGT")
	public double getAcceptedWeight() {
		return acceptedWeight;
	}

	/**
	 * @param acceptedWeight the acceptedWeight to set
	 */
	public void setAcceptedWeight(double acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}
	/**
	 * @author A-5991
	 */
	public MailbagInULDForSegment() {

	}
	public MailbagInULDForSegment(ULDForSegmentPK uldForSegmentPK,
			MailbagInULDForSegmentVO mailbagInULDForSegmenVO)
			throws SystemException {
		populatePK(uldForSegmentPK, mailbagInULDForSegmenVO);
		populateAttributes(mailbagInULDForSegmenVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}

	/**
	 *
	 * @param dsnInULDForSegPK
	 * @param mailbagInULDForSegmentVO
	 */
	private void populatePK(ULDForSegmentPK uldForSegmentPK,
			MailbagInULDForSegmentVO mailbagInULDForSegmentVO) {
		mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
		mailbagInULDForSegmentPK.setCompanyCode(   uldForSegmentPK.getCompanyCode());
		mailbagInULDForSegmentPK.setCarrierId(   uldForSegmentPK.getCarrierId());
		mailbagInULDForSegmentPK.setFlightNumber(   uldForSegmentPK.getFlightNumber());
		mailbagInULDForSegmentPK.setFlightSequenceNumber(   uldForSegmentPK.getFlightSequenceNumber());
		mailbagInULDForSegmentPK.setSegmentSerialNumber(   uldForSegmentPK.getSegmentSerialNumber());
		mailbagInULDForSegmentPK.setUldNumber(   uldForSegmentPK.getUldNumber());
		mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagInULDForSegmentVO.getMailSequenceNumber());

	}


	/**
	 *
	 * @param mailbagInULDForSegmentVO
	 */
	private void populateAttributes(
			MailbagInULDForSegmentVO mailbagInULDForSegmentVO) {
		containerNumber = mailbagInULDForSegmentVO.getContainerNumber();
		damageFlag = mailbagInULDForSegmentVO.getDamageFlag();
		scannedDate = mailbagInULDForSegmentVO.getScannedDate().toCalendar();
		scannedPort = mailbagInULDForSegmentVO.getScannedPort();
		//weight = mailbagInULDForSegmentVO.getWeight();
		weight = mailbagInULDForSegmentVO.getWeight().getSystemValue();//added by A-7371 //Changed by A-8164 for ICRD-336710
		if(mailbagInULDForSegmentVO.getAcceptanceFlag()!=null){
		setAcceptanceFlag(mailbagInULDForSegmentVO.getAcceptanceFlag());
		}else{
			setAcceptanceFlag(MailConstantsVO.FLAG_NO);
		}
		if(mailbagInULDForSegmentVO.getArrivalFlag()!=null){
		setArrivalFlag(mailbagInULDForSegmentVO.getArrivalFlag());
		}else{
			setArrivalFlag(MailConstantsVO.FLAG_NO);
		}
		if(mailbagInULDForSegmentVO.getDeliveredStatus()!=null){
		setDeliveredStatus(mailbagInULDForSegmentVO.getDeliveredStatus());
		}else{
			setDeliveredStatus(MailConstantsVO.FLAG_NO);
		}

		if("Y".equals(mailbagInULDForSegmentVO.getAcceptanceFlag())){
			setAcceptedBags(1);
			//setAcceptedWeight(mailbagInULDForSegmentVO.getWeight());
			setAcceptedWeight(mailbagInULDForSegmentVO.getWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		}
		if("Y".equals(mailbagInULDForSegmentVO.getArrivalFlag())){
			setRecievedBags(1);
			//setRecievedWeight(mailbagInULDForSegmentVO.getWeight());
			setRecievedWeight(mailbagInULDForSegmentVO.getWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added bY A-7371
		}
		if("Y".equalsIgnoreCase(mailbagInULDForSegmentVO.getDeliveredFlag())){
			setDeliveredBags(1);
			//setDeliveredWeight(mailbagInULDForSegmentVO.getWeight());
			setDeliveredWeight(mailbagInULDForSegmentVO.getWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		}
		if(mailbagInULDForSegmentVO.getTransferFlag()!=null){
		setTransferFlag(mailbagInULDForSegmentVO.getTransferFlag());
		}else{
			setTransferFlag(MailConstantsVO.FLAG_NO);
		}
		//setMailClass(mailbagInULDForSegmentVO.getMailClass());
		
		setControlDocumentNumber(mailbagInULDForSegmentVO.getControlDocumentNumber());
		setTransferFromCarrier(mailbagInULDForSegmentVO.getTransferFromCarrier());
		setTransferToCarrier(mailbagInULDForSegmentVO.getTransferToCarrier());
		/*if(mailbagInULDForSegmentVO.getSealNumber() != null &&
				mailbagInULDForSegmentVO.getSealNumber().trim().length() > 0) {
			setSealNumber(mailbagInULDForSegmentVO.getSealNumber());
		}*/
		if(mailbagInULDForSegmentVO.getArrivalSealNumber() != null &&
				mailbagInULDForSegmentVO.getArrivalSealNumber().trim().length() > 0) {
			setArrivalsealNumber(mailbagInULDForSegmentVO.getArrivalSealNumber());
		}
		 /* * Added By Karthick V to include the status  for importing the Data
         * from  the Flown Flights that could be used  be used for the
         * Mail Revenue Accounting ...
         * Intially it will be N
         * Once  MRA Has Imported  the Data the Status will Be 'I'
         * Note:Unlike the Despatches the Mailbag
         * cannot be Updated meaning there wil be no status 'U'
         */
		if(mailbagInULDForSegmentVO.getMraStatus()!=null){
        this.setMraStatus(mailbagInULDForSegmentVO.getMraStatus());
		}else{
			 this.setMraStatus(MailConstantsVO.MRA_STATUS_NEW);
		}
        /*
         * Added By Karthick V to include  the Optimistic Locking Mechanism ..
         *
         */
        this.lastUpdateUser=mailbagInULDForSegmentVO.getLastUpdateUser();
        setAcceptedUser(mailbagInULDForSegmentVO.getLastUpdateUser());
        setReceivedDate(mailbagInULDForSegmentVO.getScannedDate().toCalendar());
        setAcceptedDate(new LocalDate(mailbagInULDForSegmentVO.getScannedPort(),
				Location.ARP, mailbagInULDForSegmentVO.getScannedDate(), true).toCalendar());
        setServiceResponsive(MailConstantsVO.FLAG_NO);                                                                                                                          
        if(mailbagInULDForSegmentVO.getGhttim()!=null){
        	setGhttim(mailbagInULDForSegmentVO.getGhttim());
        }
        //added as part of IASCB-91750
        if(MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getIsFromTruck())) {
        	setAcceptanceFlag(MailConstantsVO.FLAG_YES);
        }
	}
/**
 * @author A-5991
 * @param mailbagPK
 * @return
 * @throws SystemException
 * @throws FinderException
 */
	public static MailbagInULDForSegment find(MailbagInULDForSegmentPK mailbagPK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(
				MailbagInULDForSegment.class, mailbagPK);
	}
	/**
	 * @author A-5991
	 * @return
	 * @throws SystemException
	 */
	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/**
	 * @author A-5991
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

	}

	/**
	 * @return
	 */
	public MailbagInULDForSegmentVO retrieveVO() {
		MailbagInULDForSegmentVO mailbagInULDForSegVO = new MailbagInULDForSegmentVO();
		MailbagInULDForSegmentPK mailbagPk = getMailbagInULDForSegmentPK();
		mailbagInULDForSegVO.setMailSequenceNumber(mailbagPk.getMailSequenceNumber());
		mailbagInULDForSegVO.setContainerNumber(getContainerNumber());
		mailbagInULDForSegVO.setDamageFlag(getDamageFlag());
		mailbagInULDForSegVO.setScannedDate(new LocalDate(getScannedPort(),
				Location.ARP, getScannedDate(), true));
		mailbagInULDForSegVO.setScannedPort(getScannedPort());
		//mailbagInULDForSegVO.setWeight(getWeight());
		mailbagInULDForSegVO.setWeight(new Measure(UnitConstants.MAIL_WGT,getWeight()));//added by A-7371
		mailbagInULDForSegVO.setAcceptanceFlag(getAcceptanceFlag());
		mailbagInULDForSegVO.setArrivalFlag(getArrivalFlag());
		mailbagInULDForSegVO.setTransferFlag(getTransferFlag());
	//	mailbagInULDForSegVO.setMailClass(getMailClass());
        mailbagInULDForSegVO.setDeliveredStatus(getDeliveredStatus());
        mailbagInULDForSegVO.setTransferFromCarrier(getTransferFromCarrier());
        mailbagInULDForSegVO.setTransferToCarrier(getTransferToCarrier());
        mailbagInULDForSegVO.setMraStatus(getMraStatus());
		return mailbagInULDForSegVO;
	}

	/**
     * Removes assignment of mailbags from a flight
     * Sep 14, 2006, a-1739
     * @param mailbags
     * @param isInbound TODO
     * @throws SystemException
     */
    public void reassignMailFromFlight(Collection<MailbagVO> mailbags, boolean isInbound)
        throws SystemException {
        log.entering("DSNInULDForSegment", "reassignMailFromFlight");
        Map<MailbagInULDForSegmentPK, MailbagInULDForSegmentVO> mailbagConMap =
            removeMailbags(mailbags);
        log.log(Log.FINE, "MAILBAGCONMAp", mailbagConMap);
		if(!isInbound) {
        	//updateDSNInContainers(mailbagConMap, mailbags);
        }
        log.exiting("DSNInULDForSegment", "reassignMailFromFlight");
    }



    /**
     * Removes assignment of mailbags from this DSN. Also returns a map
     * so the the DSNInCOntainer can be updated if mailbags are in bulk
     * @param mailbags
     * @return the map of DSNcontainerPK and VO having the count of mailbags
     * @throws SystemException
     */
    private Map<MailbagInULDForSegmentPK, MailbagInULDForSegmentVO> removeMailbags(Collection<MailbagVO> mailbags)
        throws SystemException {
        log.entering("DSNInULDForSegment", "removeMailbags");
        Map<MailbagInULDForSegmentPK, MailbagInULDForSegmentVO> mailbagConMap =
            new HashMap<MailbagInULDForSegmentPK, MailbagInULDForSegmentVO>();

        if(mailbags != null && mailbags.size() > 0) {
           // if(getMailbagInULDForSegments() != null) {
                for(MailbagVO mailbagVO : mailbags) {
                    MailbagInULDForSegmentPK mailbagInULDPK = constructMailbagPK(mailbagVO);
                    MailbagInULDForSegment mailbagInULDToRem = null;
                    MailbagInULDForSegment mailbagInULDForSegments= null;
                    try {
                    	mailbagInULDForSegments = mailbagInULDToRem.find(mailbagInULDPK);
    				} catch (FinderException ex) {
    					log.log(Log.SEVERE, "Finder Exception Caught");
    				}

                    if(mailbagInULDForSegments != null) {
                      //  mailbagInULDForSegments.remove(mailbagInULDToRem);
                        mailbagInULDForSegments.remove();
                    }
                }
           // }
        }
        log.exiting("DSNInULDForSegment", "removeMailbags");
        return mailbagConMap;
    }

    /**
     *	A-1739
     * @param mailbagVO
     * @return
     */
    private MailbagInULDForSegmentPK constructMailbagPK(MailbagVO mailbagVO) {
        MailbagInULDForSegmentPK mailbagPK = new MailbagInULDForSegmentPK();
       // dsnInULDForSegmentPk = getDsnInULDForSegmentPk();
        mailbagPK.setCompanyCode(   mailbagVO.getCompanyCode());
        mailbagPK.setCarrierId(   mailbagVO.getCarrierId());
        mailbagPK.setFlightNumber(   mailbagVO.getFlightNumber());
        mailbagPK.setFlightSequenceNumber(   mailbagVO.getFlightSequenceNumber());
        mailbagPK.setSegmentSerialNumber(   mailbagVO.getSegmentSerialNumber());
        mailbagPK.setUldNumber(   mailbagVO.getUldNumber());
        mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());  //Added for ICRD-204654
       // mailbagPK.setMailId(   mailbagVO.getMailbagId());
        log.log(Log.FINE, "MAILID MAILBAGVO", mailbagVO.getMailbagId());
		return mailbagPK;
    }

	/**
	 * @author A-1883
	 * @param operationalFlightVO
	 * @param consignmentKey TODO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MailDetailVO> findMailbagDetailsForReport(
			   OperationalFlightVO operationalFlightVO, String consignmentKey)
			   throws SystemException{
		Collection<MailDetailVO> mailDetailVOs = null;
		try {
			mailDetailVOs = constructDAO().findMailbagDetailsForReport(
					operationalFlightVO, consignmentKey);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
		return mailDetailVOs;
	}
	/**
	 * @param acceptedUser the acceptedUser to set
	 */
	public void setAcceptedUser(String acceptedUser) {
		this.acceptedUser = acceptedUser;
	}
	/**
	 * @return the acceptedUser
	 */
	@Column(name = "ACPUSR")
	public String getAcceptedUser() {
		return acceptedUser;
	}
	/**
	 * @param acceptedDate the acceptedDate to set
	 */
	public void setAcceptedDate(Calendar acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	/**
	 * @return the acceptedDate
	 */
	@Column(name = "ACPDAT")
	public Calendar getAcceptedDate() {
		return acceptedDate;
	}
	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(Calendar receivedDate) {
		this.receivedDate = receivedDate;
	}
	/**
	 * @return the receivedDate
	 */
	@Column(name = "RCVDAT")
	public Calendar getReceivedDate() {
		return receivedDate;
	}
	/**
	 * @author A-7371
	 * @return the serviceResponsive
	 */
	@Column(name = "SRVRSPLN")
	public String getServiceResponsive() {
		return serviceResponsive;
	}
    /**
     * @author A-7371
     * @param serviceResponsive the serviceResponsive to set
     */
	public void setServiceResponsive(String serviceResponsive) {
		this.serviceResponsive = serviceResponsive;
	}

	/**
	 * This method updateswith AWB details
	 * a-1883
	 * @param shipmentValidationVO
	 * @param shipmentPrefix
	 * @throws SystemException
	 */
	public void updateAWBDetails(ShipmentValidationVO  shipmentValidationVO,String shipmentPrefix )
    throws SystemException {
		log.entering("DSNInULDForSegment", "updateAWBDetails");
		//this.setDocumentNumber(shipmentValidationVO.getOwnerId());
		//this.setMasterDocumentNumber(shipmentValidationVO.getDocumentNumber());
		//this.setDuplicateNumber(shipmentValidationVO.getDuplicateNumber());
		//this.setSequenceNumber(shipmentValidationVO.getSequenceNumber());
		//this.documentOwnerCode(shipmentPrefix);
		log.exiting("DSNInULDForSegment", "updateAWBDetails");
	}
	/**
	 * @author a-1936
	 * This method is used to find out the other DSns for the Same AWb excluding the One 
	 * passed as the Filter 
	 * @param dsnVo
	 * @param containerDetailsVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<DespatchDetailsVO> findOtherDSNsForSameAWB(
			DSNVO dsnVo,ContainerDetailsVO containerDetailsVO) 
			throws SystemException {
		try {
			return constructDAO().findOtherDSNsForSameAWB(
					 dsnVo,containerDetailsVO);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}

	@Version
	@Column(name = "GHTTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getGhttim() {
		return ghttim;
	}

	public void setGhttim(Calendar ghttim) {
		this.ghttim = ghttim;
	}
	//a-7938
	public MailbagInULDForSegment(MailbagInULDForSegment mailbagInULDForSegment)
			throws SystemException {
		
		try {
			PersistenceController.getEntityManager().persist(mailbagInULDForSegment); 
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	/**
	 * @author A-8353
	 * @param scannedMailDetailsVO
	 * @return
	 * @throws SystemException 
	 */
	public MailbagInULDForSegmentVO getManifestInfo(
			ScannedMailDetailsVO scannedMailDetailsVO) throws SystemException,PersistenceException {
			return constructDAO().getManifestInfo(scannedMailDetailsVO);
	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @return
	 * @throws SystemException 
	 */
	public MailbagInULDForSegmentVO getManifestInfoForNextSeg(
			MailbagVO mailbagVO) throws SystemException,PersistenceException {
			return constructDAO().getManifestInfoForNextSeg(mailbagVO);
	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param containerVO
	 */
	public void updateHandoverReceivedCarrierForTransfer(MailbagVO mailbagVO,
			ContainerVO containerVO){
		if(MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())&&containerVO.getFinalDestination()!=null){
			mailbagVO.setUldNumber(new StringBuilder().append(MailConstantsVO.CONST_BULK)
					.append(MailConstantsVO.SEPARATOR).append(containerVO.getFinalDestination()).toString());
		}
		MailbagInULDForSegment mailbagInULDForSeg = null;
			try {
				mailbagInULDForSeg=find(constructMailbagPK(mailbagVO));
			} catch (FinderException | SystemException e) {
				e.getMessage();
			}
		if (mailbagInULDForSeg!=null){
			mailbagInULDForSeg.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
		}
	}
	
	/**
	 * @author A-8353
	 * @param mailbagVO
	 */
	public void updateTransferFlagForPreviousSegment(MailbagVO mailbagVO) {
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		scannedMailDetailsVO.setCompanyCode(mailbagVO.getCompanyCode());
		scannedMailDetailsVO.setAirportCode(mailbagVO.getScannedPort());
		if(mailbagVO.getMailSequenceNumber()==0){
			try {
				mailbagVO.setMailSequenceNumber(constructDAO().findMailSequenceNumber(mailbagVO.getMailbagId(),mailbagVO.getCompanyCode()));
			} catch (SystemException e) {
				log.log(Log.FINE, "SystemException", e);
			   e.getMessage();
			}
		}
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailbagVOs);
		MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
		try {
			mailbagInULDForSegmentVO = getManifestInfo(scannedMailDetailsVO);
		} catch (PersistenceException | SystemException e) {
			log.log(Log.FINE, "SystemException", e);
			e.getMessage();
		}
		if (mailbagInULDForSegmentVO != null) {
			MailbagInULDForSegment mailbagInULDForSeg = null;
			MailbagInULDForSegmentPK mailbagPK = new MailbagInULDForSegmentPK();
			mailbagPK.setCompanyCode(mailbagInULDForSegmentVO.getCompanyCode());
			mailbagPK.setCarrierId(mailbagInULDForSegmentVO.getCarrierId());
			mailbagPK.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
			mailbagPK.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
			mailbagPK.setSegmentSerialNumber(mailbagInULDForSegmentVO.getSegmentSerialNumber());
			mailbagPK.setUldNumber(mailbagInULDForSegmentVO.getContainerNumber());
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			try {
				mailbagInULDForSeg = find(mailbagPK);
			} catch (FinderException | SystemException e) {
				log.log(Log.FINE, "FinderException", e);
				e.getMessage();
			}
			if (mailbagInULDForSeg != null) {
				mailbagInULDForSeg.setTransferFlag(MailConstantsVO.FLAG_YES);
			}
		}

	}

	
}
