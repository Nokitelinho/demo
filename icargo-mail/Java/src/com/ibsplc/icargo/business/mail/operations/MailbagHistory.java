/*
 * MailbagHistory.java Created on Jun 27, 2016
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
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.InheritanceType;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 * 
 *         Maintains history for a mailbag. This entity stores all details
 *         regarding the mailbag transactions. *
 */
@Entity
@Table(name = "MALHIS_VW_BASE")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Staleable
public class MailbagHistory {

	private static final String MAIL_OPERATIONS = "mail.operations";
	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	private MailbagHistoryPK mailbagHistoryPK;
	private Calendar scanDate;
	private String flightNumber;
	private int carrierId;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String scannedPort;
	private String containerNumber;
	private String containerType;
	private String scanUser;
	private Calendar flightDate;
	private String carrierCode;
	private String pou;	

	private Calendar utcScandate;


    private Calendar messageTime;
	private Calendar messageTimeUTC;


	// Added by Paulson For SB uld for mail history
	private String paBuiltUldFlag;

	private Calendar creationTime;

	private String mailSource;

	/**
	 * The mailStatus
	 */
	private String mailStatus;

	/**
	 * Added by A-2135 for QFCR 1517
	 */
	private String interchangeControlReference;

	private String additionalInfo;//Added by a-7871 for ICRD-240184

	private Calendar  lastUpdateTime;
	
	/**
	 * @return the additionalInfo
	 */
	@Column(name = "ADDINF")
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * @param additionalInfo the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	/**
	 * @return Utc scan date
	 */
	@Column(name = "UTCSCNDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUtcScanDate() {
		return utcScandate;
	}

	/**
	 * @param utcScandate
	 */
	public void setUtcScanDate(Calendar utcScandate) {
		this.utcScandate = utcScandate;
	}

	/**
	 * @return pou
	 */
	@Column(name = "POU")
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the carrierId.
	 * 
	 */
	@Column(name = "FLTCARIDR")
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return Returns the flightNumber.
	 * 
	 */
	@Column(name = "FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightSequenceNumber.
	 * 
	 */
	@Column(name = "FLTSEQNUM")
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the scanUser.
	 */
	@Column(name = "SCNUSR")
	public String getScanUser() {
		return scanUser;
	}

	/**
	 * @param scanUser
	 *            The scanUser to set.
	 */
	public void setScanUser(String scanUser) {
		this.scanUser = scanUser;
	}

	// Added by Saravanan S

	/**
	 * @return Returns the mailbagHistoryPK.
	 * 
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")),
			@AttributeOverride(name = "historySequenceNumber", column = @Column(name = "MALHISIDR")) })
	public MailbagHistoryPK getMailbagHistoryPK() {
		return mailbagHistoryPK;
	}

	/**
	 * @param mailbagHistoryPK
	 *            The mailbagHistoryPK to set.
	 */
	public void setMailbagHistoryPK(MailbagHistoryPK mailbagHistoryPK) {
		this.mailbagHistoryPK = mailbagHistoryPK;
	}

	/**
	 * @return Returns the scanDate.
	 * 
	 */
	@Column(name = "SCNDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getScanDate() {
		return scanDate;
	}

	/**
	 * @param scanDate
	 *            The scanDate to set.
	 */
	public void setScanDate(Calendar scanDate) {
		this.scanDate = scanDate;
	}

	/**
	 * @return Returns the scannedPort.
	 * 
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
	 * @return Returns the segmentSerialNumber.
	 * 
	 */
	@Column(name = "SEGSERNUM")
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

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
	 * @return Returns the containerType.
	 * 
	 */
	@Column(name = "CONTYP")
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
	 * @return Returns the carrierCode.
	 */
	@Column(name = "FLTCARCOD")
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the flightDate.
	 */

	@Column(name = "FLTDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the interchangeControlReference
	 */
	@Column(name = "CNTREFNUM")
	public String getInterchangeControlReference() {
		return interchangeControlReference;
	}

	/**
	 * @param interchangeControlReference
	 *            the interchangeControlReference to set
	 */
	public void setInterchangeControlReference(
			String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}


	/*
	 * @Column(name="POAULDFLG") public String getPaBuiltUldFlag() { return
	 * paBuiltUldFlag; }
	 * 
	 * public void setPaBuiltUldFlag(String paBuiltUldFlag) {
	 * this.paBuiltUldFlag = paBuiltUldFlag; }
	 */


	/**
	 * @return the creationTime
	 */
	@Version
	@Column(name = "CRTTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Calendar creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the source
	 */
	@Column(name = "MALSRC")
	public String getMailSource() {
		return mailSource;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}

	/**
	 * @return the mailStatus
	 */
	@Column(name = "MALSTA")
	public String getMailStatus() {
		return mailStatus;
	}

	/**
	 * @param mailStatus
	 *            the mailStatus to set
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}
	
	/**
	 * @return Returns the messageTime.
	 */
    @Column(name="MSGTIM")
    @Temporal(TemporalType.TIMESTAMP)
	public Calendar getMessageTime() {
		return messageTime;
	}

	/**
	 * @param messageTime The messageTime to set.
	 */
	public void setMessageTime(Calendar messageTime) {
		this.messageTime = messageTime;
	}
	
	@Column(name="MSGTIMUTC")
	@Temporal(TemporalType.TIMESTAMP)			
	public Calendar getMessageTimeUTC() {
		return messageTimeUTC;
	}

	/**
	 *  @param messageTimeUTC the messageTimeUTC to set
	 * 	Setter for messageTimeUTC 
	 *	Added by : A-4803 on 03-Dec-2014
	 * 	Used for : storing utc message time
	 */
	public void setMessageTimeUTC(Calendar messageTimeUTC) {
		this.messageTimeUTC = messageTimeUTC;
	}
	
	//Added by A-7929 as part of IASCB-35577
	private String lastUpdateUser;
	@Column(name = "LSTUPDUSR")
		public String getLastUpdateUser() {
			return lastUpdateUser;
		}
		public void setLastUpdateUser(String lastUpdateUser) {
			this.lastUpdateUser = lastUpdateUser;
		}

	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
		
	public MailbagHistory() {

	}

	/**
	 * @author A-5991
	 * @param mailbagPK
	 * @param mailbagHistoryVO
	 * @throws SystemException
	 */
	public MailbagHistory(MailbagPK mailbagPK, MailbagHistoryVO mailbagHistoryVO)
			throws SystemException {
		setMailbagHistoryAttributes(mailbagPK, mailbagHistoryVO);
		try {
			PersistenceController.getEntityManager().persist(this);

		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}

	}
	
	/**
	 * @author A-9619 for resolving sonarlint issue Constructors should only call non-overridable methods (squid:S1699)
	 * @param mailbagPK
	 * @param mailbagHistoryVO
	 * @throws SystemException
	 */
	private void setMailbagHistoryAttributes(MailbagPK mailbagPK, MailbagHistoryVO mailbagHistoryVO) throws SystemException {
		populatePK(mailbagPK, mailbagHistoryVO);
		populateAttributes(mailbagHistoryVO);
	}
	
	public MailbagHistory persistMailbagHistory(MailbagPK mailbagPK, MailbagHistoryVO mailbagHistoryVO)
			throws SystemException {
		
		populatePK(mailbagPK, mailbagHistoryVO);
		populateAttributes(mailbagHistoryVO);
		try {
			PersistenceController.getEntityManager().persist(this);

		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
		return this;
	}

	/**
	 * @author A-5991
	 * @param mailbagPK
	 * @param mailbagHistoryVO
	 * @throws SystemException
	 */
	private void populatePK(MailbagPK mailbagPK,
			MailbagHistoryVO mailbagHistoryVO) throws SystemException {
		mailbagHistoryPK = new MailbagHistoryPK();
		if(mailbagHistoryVO.getCompanyCode()!=null && mailbagHistoryVO.getCompanyCode().trim().length()>0) {
		  mailbagHistoryPK.setCompanyCode(mailbagHistoryVO.getCompanyCode());
		} else {
			 mailbagHistoryPK.setCompanyCode(mailbagPK.getCompanyCode());
		}
		if(mailbagHistoryVO.getMailSequenceNumber()!=0) {
			mailbagHistoryPK.setMailSequenceNumber(mailbagHistoryVO
					.getMailSequenceNumber());
		} else {
		 mailbagHistoryPK.setMailSequenceNumber(mailbagPK
				.getMailSequenceNumber());
		}

	}

	/**
	 * A-5991
	 * 
	 * @param mailbagHistoryVO
	 */
	public void populateAttributes(MailbagHistoryVO mailbagHistoryVO) {
		setCarrierId(mailbagHistoryVO.getCarrierId());
		setContainerNumber(mailbagHistoryVO.getContainerNumber());
		setContainerType(mailbagHistoryVO.getContainerType());
		setFlightNumber(mailbagHistoryVO.getFlightNumber());
		setFlightSequenceNumber(mailbagHistoryVO.getFlightSequenceNumber());
		setScanDate(mailbagHistoryVO.getScanDate().toCalendar());
		//UTC scan should be populated from scan time, review by Shambu 
		if(mailbagHistoryVO.getScanDate()!=null){//added by A-8353 for IASCB-52564
	     LocalDate utcDate = mailbagHistoryVO.getScanDate();
	     GMTDate gmt =  utcDate.toGMTDate();
	     setUtcScanDate(gmt.toCalendar());  
			
		}
		else{
		LocalDate utcDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		GMTDate gmt =  utcDate.toGMTDate();
		setUtcScanDate(gmt.toCalendar());
		}

		setScannedPort(mailbagHistoryVO.getScannedPort());
		setSegmentSerialNumber(mailbagHistoryVO.getSegmentSerialNumber());
		setMailStatus(mailbagHistoryVO.getMailStatus());
		setScanUser(mailbagHistoryVO.getScanUser());
			
		

		setCarrierCode(mailbagHistoryVO.getCarrierCode());
		setPou(mailbagHistoryVO.getPou());
		// setPaBuiltUldFlag(mailbagHistoryVO.getPaBuiltFlag());
		if (mailbagHistoryVO.getFlightDate() != null) {
			setFlightDate(mailbagHistoryVO.getFlightDate().toCalendar());
		}

		if (mailbagHistoryVO.getMessageTime() != null) {
		setMessageTime(mailbagHistoryVO.getMessageTime());
        setMessageTimeUTC(mailbagHistoryVO.getMessageTime().toGMTDate().toCalendar());
		}
		setInterchangeControlReference(mailbagHistoryVO
				.getInterchangeControlReference());
		setCreationTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));// Added
																					// for
																					// ICRD-156218
		if(mailbagHistoryVO.getMailSource()!=null){	
		setMailSource(mailbagHistoryVO.getMailSource());// Added for ICRD-156218
		}else{
			setMailSource(MailConstantsVO.FRM_JOB);
		}
		
		setAdditionalInfo(mailbagHistoryVO.getAdditionalInfo());//Added by A-7871 for ICRD-240184
		LogonAttributes logonAttributes=null;
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			log.log(Log.FINE,"SystemException at logonAttributes-ContextUtils.getSecurityContext()");
		} 
		setLastUpdateUser(logonAttributes.getUserId()); //Added by A-7929 as part of IASCB-35577
		setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));//IASCB-46569  
	} 
	
	

	/**
	 * @author A-5991
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}

	/**
	 * @author A-5991
	 * @param mailbaghistorypk
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static MailbagHistory findMailbagHistory(
			MailbagHistoryPK mailbaghistorypk) throws FinderException,
			SystemException {
		 return PersistenceController.getEntityManager().find(MailbagHistory.getInstance().getClass(), mailbaghistorypk);
	}

	/**
	 * Find the arrival history of a mailbag Sep 12, 2007, a-1739
	 * 
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 */
	public static MailbagVO findArrivalDetailsForMailbag(MailbagVO mailbagVO)
			throws SystemException {
		try {
			return constructDAO().findArrivalDetailsForMailbag(mailbagVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	/**
	 * @return
	 * @throws SystemException
	 */
	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(MAIL_OPERATIONS));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception);
		}
	}

	/**
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 */
	public static MailbagVO findLatestFlightDetailsOfMailbag(MailbagVO mailbagVO)
			throws SystemException {
		try {
			return constructDAO().findLatestFlightDetailsOfMailbag(mailbagVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	/**
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 */
	public static boolean isMailbagAlreadyArrived(MailbagVO mailbagVO)
			throws SystemException {
		try {
			return constructDAO().isMailbagAlreadyArrived(mailbagVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}
	/**
	 * 
	 * 	Method		:	MailbagHistory.constructMailbagHistoryPK
	 *	Added by 	:	A-8061 on 07-Apr-2020
	 * 	Used for 	:
	 *	Parameters	:	@param mailhistoryvo
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MailbagHistoryPK
	 */
	 public static  MailbagHistoryPK constructMailbagHistoryPK(MailbagHistoryVO mailhistoryvo) throws SystemException {
			MailbagHistoryPK mailbagHistoryPK = new MailbagHistoryPK();
			mailbagHistoryPK.setCompanyCode(mailhistoryvo.getCompanyCode());
		    mailbagHistoryPK.setHistorySequenceNumber(mailhistoryvo.getHistorySequenceNumber());
			mailbagHistoryPK.setMailSequenceNumber(mailhistoryvo.getMailSequenceNumber()> 0 ?
			mailhistoryvo.getMailSequenceNumber(): Mailbag.findMailSequenceNumber(mailhistoryvo.getMailbagId(), mailhistoryvo.getCompanyCode()) );
			return  mailbagHistoryPK;
	 }
	 
	 /**
	 * @author A-9619
	 * @return company specific mail controller
	 * @throws SystemException
	 * IASCB-55196
	 */
	public static MailbagHistory getInstance() throws SystemException {
		return (MailbagHistory) SpringAdapter.getInstance().getBean("MailbagHistoryEntity");

	}
}