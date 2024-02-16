/*
 * UldResdit.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.UldResditVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
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
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-3109
 * 
 */
@Entity
@Table(name = "MALULDRDT")
@Staleable
public class UldResdit {

	private static final String MAIL_OPERATIONS = "mail.operations";

	private static final String ULD_EVENT_KEY = "ULD_EVENT_KEY";

	private Log log = LogFactory.getLogger("MAIL_operations");

	private UldResditPK uldResditPK;

	/**
	 * totalWeight
	 */
	private String eventAirport;

	/**
	 * totalWeight
	 */
	private Calendar eventDate;

	/**
	 * totalWeight
	 */
	private int carrierId;

	/**
	 * totalWeight
	 */
	private String flightNumber;

	/**
	 * totalWeight
	 */
	private long flightSequenceNumber;

	/**
	 * totalWeight
	 */
	private int segmentSerialNumber;

	/**
	 * totalWeight
	 */
	private String resditSent;

	/**
	 * totalWeight
	 */
	private String processedStatus;

	/**
	 * totalWeight
	 */
	private long messageSequenceNumber;

	/**
	 * totalWeight
	 */
	private int uldEventSequenceNumber;

	/**
	 * totalWeight
	 */
	private String carditKey;

	/**
	 * totalWeight
	 */
	private String paOrCarrierCode;

	/**
	 * totalWeight
	 */
	private String containerJourneyId;

	/**
	 * shipperBuiltCode - Contains the Shipper Code(PA Code), who build the SB
	 * ULD.
	 */
	private String shipperBuiltCode;

	/**
	 *
	 */
	private Calendar utcEventDate;
	/**
	 * The interchangeControlReference
	 */
	private String interchangeControlReference;

	/**
	 * @return Returns the uldEventSequenceNumber.
	 */
	@Column(name = "ULDEVTSEQ")
	public int getUldEventSequenceNumber() {
		return uldEventSequenceNumber;
	}

	/**
	 * @param uldEventSequenceNumber
	 *            The uldEventSequenceNumber to set.
	 */
	public void setUldEventSequenceNumber(int uldEventSequenceNumber) {
		this.uldEventSequenceNumber = uldEventSequenceNumber;
	}

	/**
	 * @return Returns the carrierId.
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
	 * @return Returns the eventAirport.
	 */
	@Column(name = "EVTPRT")
	public String getEventAirport() {
		return eventAirport;
	}

	/**
	 * @param eventAirport
	 *            The eventAirport to set.
	 */
	public void setEventAirport(String eventAirport) {
		this.eventAirport = eventAirport;
	}

	/**
	 * @return Returns the eventDate.
	 */
	@Column(name = "EVTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate
	 *            The eventDate to set.
	 */
	public void setEventDate(Calendar eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return Returns the flightNumber.
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
	 * @return Returns the segmentSerialNumber.
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
	 * @return Returns the resditSent.
	 */
	@Column(name = "RDTSND")
	public String getResditSent() {
		return resditSent;
	}

	/**
	 * @param resditSent
	 *            The resditSent to set.
	 */
	public void setResditSent(String resditSent) {
		this.resditSent = resditSent;
	}

	/**
	 * @return Returns the messageSequenceNumber.
	 */
	@Column(name = "MSGSEQNUM ")
	public long getMessageSequenceNumber() {
		return messageSequenceNumber;
	}

	/**
	 * @param messageSequenceNumber
	 *            The messageSequenceNumber to set.
	 */
	public void setMessageSequenceNumber(long messageSequenceNumber) {
		this.messageSequenceNumber = messageSequenceNumber;
	}

	/**
	 * @return Returns the processedStatus.
	 */
	@Column(name = "PROSTA ")
	public String getProcessedStatus() {
		return processedStatus;
	}

	/**
	 * @param processedStatus
	 *            The processedStatus to set.
	 */
	public void setProcessedStatus(String processedStatus) {
		this.processedStatus = processedStatus;
	}

	/**
	 * @return Returns the carditKey.
	 */
	@Column(name = "CDTKEY")
	public String getCarditKey() {
		return this.carditKey;
	}

	/**
	 * @param carditKey
	 *            The carditKey to set.
	 */
	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}

	/**
	 * @return Returns the paOrCarrierCode.
	 */
	@Column(name = "POACARCOD")
	public String getPaOrCarrierCode() {
		return paOrCarrierCode;
	}

	/**
	 * @param paOrCarrierCode
	 *            The paOrCarrierCode to set.
	 */
	public void setPaOrCarrierCode(String paOrCarrierCode) {
		this.paOrCarrierCode = paOrCarrierCode;
	}

	/**
	 * 
	 * @return utcEventDate
	 */
	@Column(name = "EVTDATUTC")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUtcEventDate() {
		return utcEventDate;
	}

	/**
	 * 
	 * @param utcEventDate
	 */
	public void setUtcEventDate(Calendar utcEventDate) {
		this.utcEventDate = utcEventDate;
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

	/**
	 * @return the containerJourneyId
	 */
	@Column(name = "CONJRNIDR")
	public String getContainerJourneyId() {
		return containerJourneyId;
	}

	/**
	 * @param containerJourneyId
	 *            the containerJourneyId to set
	 */
	public void setContainerJourneyId(String containerJourneyId) {
		this.containerJourneyId = containerJourneyId;
	}

	/**
	 * @return the shipperBuiltCode
	 */
	@Column(name = "POACOD")
	public String getShipperBuiltCode() {
		return shipperBuiltCode;
	}

	/**
	 * @param shipperBuiltCode
	 *            the shipperBuiltCode to set
	 */
	public void setShipperBuiltCode(String shipperBuiltCode) {
		this.shipperBuiltCode = shipperBuiltCode;
	}

	/**
	 * @return Returns the uldResditPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "eventCode", column = @Column(name = "EVTCOD")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	public UldResditPK getUldResditPK() {
		return uldResditPK;
	}

	/**
	 * @param uldResditPK
	 *            The uldResditPK to set.
	 */
	public void setUldResditPK(UldResditPK uldResditPK) {
		this.uldResditPK = uldResditPK;
	}

	/**
	 * The Default Constructor Required For Hibernate
	 * 
	 */
	public UldResdit() {
	}

	public UldResdit(UldResditVO uldResditVO) throws SystemException {
		log.entering("UldResdit", "UldResdit");
		populatePK(uldResditVO);
		populateAttributes(uldResditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("UldResdit", "UldResdit");
	}

	/**
	 * @author A-3109
	 * @param uldResditVO
	 */
	private void populatePK(UldResditVO uldResditVO) {
		log.entering("UldResdit", "populatePK");
		log.log(Log.FINE, "THE UldResditVO >>>>>>>>>>", uldResditVO);
		uldResditPK = new UldResditPK();
		uldResditPK.setCompanyCode(uldResditVO.getCompanyCode());
		uldResditPK.setUldNumber(uldResditVO.getUldNumber());
		uldResditPK.setEventCode(uldResditVO.getEventCode());
		log.log(Log.INFO, "THE getCompanyCode is", uldResditPK.getCompanyCode());
		log.log(Log.INFO, "THE getEventCode is", uldResditPK.getEventCode());
		log.log(Log.INFO, "THE getSequenceNumber is",
				uldResditPK.getSequenceNumber());
		log.log(Log.INFO, "THE getUldNumber is", uldResditPK.getUldNumber());
		log.exiting("UldResdit", "populatepK");
	}

	/**
	 * @author A-3109
	 * @param uldResditVO
	 * @throws SystemException
	 */
	private void populateAttributes(UldResditVO uldResditVO)
			throws SystemException {
		log.entering("UldResdit", "populateAttributes");
		setCarrierId(uldResditVO.getCarrierId());
		setFlightNumber(uldResditVO.getFlightNumber());
		setFlightSequenceNumber(uldResditVO.getFlightSequenceNumber());
		setSegmentSerialNumber(uldResditVO.getSegmentSerialNumber());
		setEventAirport(uldResditVO.getEventAirport());
		setEventDate(uldResditVO.getEventDate().toCalendar());
		setUtcEventDate(uldResditVO.getEventDate().toGMTDate().toCalendar());
		setResditSent(uldResditVO.getResditSentFlag());
		setProcessedStatus(uldResditVO.getProcessedStatus());
		setMessageSequenceNumber(uldResditVO.getMessageSequenceNumber());
		setCarditKey(uldResditVO.getCarditKey());
		setPaOrCarrierCode(uldResditVO.getPaOrCarrierCode());
		setUldEventSequenceNumber(generateULDEventSeqNumber(
				uldResditVO.getCompanyCode(), uldResditVO.getUldNumber()));
		setContainerJourneyId(uldResditVO.getContainerJourneyId());
		setShipperBuiltCode(uldResditVO.getShipperBuiltCode());
		log.exiting("UldResdit", "populateAttributes");
	}

	/**
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 */
	private int generateULDEventSeqNumber(String companyCode, String uldNumber)
			throws SystemException {
		log.entering("UldResdit", "generateSerialNumber");
		Criterion criterion = KeyUtils.getCriterion(companyCode, ULD_EVENT_KEY,
				uldNumber);
		criterion.setStartAt("1");
		return Integer.parseInt(KeyUtils.getKey(criterion));
	}

	/**
	 * @author A-3109 This method is used to find the DAO's Instance
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
	 * This method is used to return the Instance of the Entity
	 * 
	 * @author A-3109
	 * @param uldResditPK
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static UldResdit find(UldResditPK uldResditPK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(UldResdit.class,
				uldResditPK);

	}

	/**
	 * @author A-3109 This method is used to remove the Entity
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
	 * For finding status of a ULDRESDit event
	 * Jun 3, 2008, a-1739
	 * @param uldResditVO
	 * @return
	 * @throws SystemException
	 */
    public static Collection<UldResditVO> findULDResditStatus (UldResditVO uldResditVO)
    	throws SystemException{
    	try {
    		return constructDAO().findULDResditStatus(uldResditVO);
    	} catch(PersistenceException exception) {
    		throw new SystemException(exception.getMessage(), exception);
    	}
    }

    
    /**
	 * @author a-1936
     * Added By Karthick V as the part of the NCA Mail Tracking CR..
     * This method is used to find the Mail Resdit Entity as such
     * @param mailResditVO
     * @return
     * @throws SystemException
     */
	public static List<UldResdit> findUldResdit(
			UldResditVO uldResditVO,String eventCode) throws SystemException {
		try {
			return constructObjectDAO().findUldResdit(
					uldResditVO,eventCode);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	
	
	/**
	 * @author A-3227 
     * Added By RENO K ABRAHAM 
     * This method is used to find the ULD Resdit Entity with Pending Event 
     * @param mailResditVO
     * @return
     * @throws SystemException 
     */
	public static List<UldResdit> findPendingUldResdit(
			UldResditVO uldResditVO,String eventCode) throws SystemException {
		try {
			return constructObjectDAO().findPendingUldResdit(
					uldResditVO,eventCode);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	
	
	/**
	 * 
	 * @param uldResditVOs
	 * @return
	 * @throws SystemException
	 */
	public static HashMap<String, String> findPAForShipperbuiltULDs(
			Collection<UldResditVO> uldResditVOs, boolean isFromCardit)	throws SystemException {
		try {
			return constructDAO().findPAForShipperbuiltULDs (uldResditVOs, isFromCardit);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	
	/**
	 *
	 * @return
	 * @throws SystemException
	 */
   private static MailtrackingDefaultsObjectInterface constructObjectDAO() throws SystemException {
   	try {
   		return PersistenceController.getEntityManager().getObjectQueryDAO(
   				MAIL_OPERATIONS);
   	} catch(PersistenceException ex) {
   		throw new SystemException (ex.getMessage(), ex);
   	}
   }
   /**
    * @author A-1936
    * This method is used to  find the LastAssignedResditForUld
    * @param containerDetailVo
    * @param eventCode
    * @return
    * @throws SystemException
    */
   public static UldResditVO  findLastAssignedResditForUld(ContainerDetailsVO containerDetailVo,
  		 String eventCode)
     throws SystemException{
  	 try{
  	 return constructDAO().findLastAssignedResditForUld(containerDetailVo,eventCode);
    }catch(PersistenceException ex){
  	  throw new SystemException(ex.getErrorCode(),ex);
    }
  }
	/**
	 * 
	 * @param companyCode
	 * @param controlReferenceNumber
	 * @return
	 * @throws SystemException
	 * @author A-2572
	 */
	public static Collection<UldResdit> findULDResditForResditStatusUpd(
			String companyCode, String controlReferenceNumber)
			throws SystemException {		
		try {
			return constructObjectDAO().findULDResditForResditStatusUpd(companyCode,controlReferenceNumber);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	} 
	 /**
	  * For converting uldResdit to uldResditVO
	  * @param uldResdit
	  * @return uldResditVO
	  */
	 public static UldResditVO covertToUldResditVo(
			 UldResdit uldResdit) {
		 UldResditVO uldResditVO = new UldResditVO();
		uldResditVO.setCompanyCode(
				uldResdit.getUldResditPK().getCompanyCode());
		uldResditVO.setUldNumber(
				uldResdit.getUldResditPK().getUldNumber());
		uldResditVO.setEventCode(
				uldResdit.getUldResditPK().getEventCode());
		uldResditVO.setEventAirport(uldResdit.getEventAirport());
		uldResditVO.setCarrierId(uldResdit.getCarrierId());
		uldResditVO.setFlightNumber(uldResdit.getFlightNumber());
		uldResditVO.setFlightSequenceNumber(uldResdit
				.getFlightSequenceNumber());
		uldResditVO.setSegmentSerialNumber(uldResdit
				.getSegmentSerialNumber());
		uldResditVO.setResditSentFlag(uldResdit.getResditSent());
		uldResditVO.setEventDate(
				new LocalDate(uldResdit.getEventAirport(),
						Location.ARP, uldResdit.getEventDate(), true));
		uldResditVO.setMessageSequenceNumber(uldResdit
				.getMessageSequenceNumber());
		uldResditVO.setProcessedStatus(uldResdit.getProcessedStatus());
		uldResditVO.setCarditKey(uldResdit.getCarditKey());
		uldResditVO.setPaOrCarrierCode(uldResdit.getPaOrCarrierCode());
		uldResditVO.setInterchangeControlReference(
				uldResdit.getInterchangeControlReference());
		uldResditVO.setContainerJourneyId(
				uldResdit.getContainerJourneyId());
		return uldResditVO;
  }
	 /**
	  * 
	  * @param resditEventVO
	  * @return
	  * @throws SystemException
	  */
	 public static Collection<TransportInformationVO> findTransportDetailsForULD(
				ResditEventVO resditEventVO) throws SystemException {
			try {
				return constructDAO().findTransportDetailsForULD(resditEventVO);
			} catch(PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getMessage(), persistenceException);
			}
		}
	 
	 /**
	  * 
	  * @param resditEventVO
	  * @return
	  * @throws SystemException
	  */
	 public static Collection<ReceptacleInformationVO> findMailbagDetailsForSBUldsFromCardit(
				ResditEventVO resditEventVO) throws SystemException {
			try {
				return constructDAO().findMailbagDetailsForSBUldsFromCardit(resditEventVO);
			} catch (PersistenceException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}
		}
	 /**
	  * 
	  * @param resditEventVO
	  * @return
	  * @throws SystemException
	  */
	 public static Collection<ContainerInformationVO> findULDDetailsForResdit(
				ResditEventVO resditEventVO) throws SystemException {
			try {
				return constructDAO().findULDDetailsForResdit(resditEventVO);
			} catch(PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getMessage(), persistenceException);
			}
		}
	 
	 /**
	  * 
	  * @param resditEventVO
	  * @return
	  * @throws SystemException
	  */
	 public static Collection<ContainerInformationVO> findULDDetailsForResditWithoutCardit(
				ResditEventVO resditEventVO) throws SystemException {
			try {
				return constructDAO().findULDDetailsForResditWithoutCardit(resditEventVO);
			} catch(PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getMessage(), persistenceException);
			}
		}
}
