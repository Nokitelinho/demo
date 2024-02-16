package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.MLDDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDMasterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-5526
 *
 */
@Entity
@Table(name = "MALMLDMST")
public class MLDMessageMaster {
	private static final String mail_operations = "mail.operations";
	private Log log = LogFactory.getLogger("mail.operations");
	private MLDMessageMasterPK mldMessageMasterPK;

	private Calendar transactionTimeUTC;
	private Calendar flightOperationDateOub;
	private Calendar flightOperationDateInb;
	private Calendar eventTimeOub;
	private Calendar eventTimeInb;
	private Calendar transactionTime;
	private double weight;
	private int carrierIdOub;
	private long flightSequenceNumberInb;
	private long flightSequenceNumberOub;
	private int carrierIdInb;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	private String messageVersion;
	private String eventMode;
	//Modified as part of bug ICRD-143950 by A-5526
	private String containerNumber;
	private String weightCode;
	private String mailIdr;
	private String senderAirport;
	private String receiverAirport;
	private String pouOub;
	private String polInb;
	private String destAirport;
	private String postalCodeOub;
	private String flightNumberOub;
	private String postalCodeInb;
	private String processStatus;
	private String flightNumberInb;
	private String statusCodeOub;
	private String statusCodeInb;
	private long mailSequenceNumber;
	
	private String  transactionLevel;
	private Calendar msgTimUTC;

	public MLDMessageMaster() {

	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")) })
	public MLDMessageMasterPK getMldMessageMasterPK() {
		return mldMessageMasterPK;
	}

	public void setMldMessageMasterPK(MLDMessageMasterPK mldMessageMasterPK) {
		this.mldMessageMasterPK = mldMessageMasterPK;
	}

	/**
	 * 
	 * @param mldMasterVO
	 * @throws SystemException
	 */
	public MLDMessageMaster(MLDMasterVO mldMasterVO) throws SystemException {
		log.entering("MLDMessageMaster", "init");
		populatePK(mldMasterVO);
		populateAttributes(mldMasterVO);
		try {
			PersistenceController.getEntityManager().persist(this);

		} catch (CreateException exception) {
			log.log(Log.FINE, "CreateException-MLDMessageMaster");
			throw new SystemException(exception.getMessage(), exception);
		}

		log.exiting("MLDMessageMaster", "init");
	}

	/**
	 * 
	 * @param mldMasterVO
	 * @throws SystemException
	 */
	private void populateAttributes(MLDMasterVO mldMasterVO)
			throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		MLDDetailVO mLDDetailVO = mldMasterVO.getMldDetailVO();
		if (mLDDetailVO != null) {
			setCarrierIdInb(mLDDetailVO.getCarrierIdInb());
			setCarrierIdOub(mLDDetailVO.getCarrierIdOub());
			setEventTimeInb(mLDDetailVO.getEventTimeInb());
			setEventTimeOub(mLDDetailVO.getEventTimeOub());
			setFlightNumberInb(mLDDetailVO.getFlightNumberInb());
			setFlightNumberOub(mLDDetailVO.getFlightNumberOub());
			setFlightOperationDateInb(mLDDetailVO.getFlightOperationDateInb());
			setFlightOperationDateOub(mLDDetailVO.getFlightOperationDateOub());
			setFlightSequenceNumberInb(mLDDetailVO.getFlightSequenceNumberInb());
			setFlightSequenceNumberOub(mLDDetailVO.getFlightSequenceNumberOub());
			setPolInb(mLDDetailVO.getPolInb());
			setPostalCodeInb(mLDDetailVO.getPostalCodeInb());
			setPostalCodeOub(mLDDetailVO.getPostalCodeOub());
			setPouOub(mLDDetailVO.getPouOub());
			//Commented as part of bug ICRD-144653 by A-5526
			//setTransactionTime(new LocalDate(logonAttributes.getAirportCode(),
					//Location.ARP, false));
			//Added as part of bug ICRD-144653 by A-5526 starts
			if(mLDDetailVO.getEventTimeOub()!=null){
				setTransactionTime(mLDDetailVO.getEventTimeOub());        
			}
			else{
				setTransactionTime(mLDDetailVO.getEventTimeInb());
			}
			//Added as part of bug ICRD-144653 by A-5526 ends
			setTransactionTimeUTC(new LocalDate(
					logonAttributes.getAirportCode(), Location.ARP, false)
					.toGMTDate());
			setStatusCodeInb(mLDDetailVO.getMailModeInb());
			setStatusCodeOub(mLDDetailVO.getMailModeOub());
		}
		setEventMode(mldMasterVO.getEventCOde());
		setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, false).toCalendar());
		setLastUpdateUser(logonAttributes.getUserId());
		setMailIdr(mldMasterVO.getBarcodeValue());
		setMessageVersion(mldMasterVO.getMessageVersion());
		setMailSequenceNumber(mldMasterVO.getMailSequenceNumber());
		setProcessStatus(mldMasterVO.getProcessStatus());
		setReceiverAirport(mldMasterVO.getReceiverAirport());
		setSenderAirport(mldMasterVO.getSenderAirport());
//Modified as part of bug ICRD-143950 by A-5526
		setContainerNumber(mldMasterVO.getUldNumber());

		if (mldMasterVO.getWeight() != null) {
			//double weight = Double.parseDouble(mldMasterVO.getWeight()) / 10;
			//setWeight(weight);
			setWeight(mldMasterVO.getWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		}

		setWeightCode(mldMasterVO.getWeightCode());
		setDestAirport(mldMasterVO.getDestAirport());
		if(mldMasterVO.getTransactionLevel()!=null) {
		setTransactionLevel(mldMasterVO.getTransactionLevel());
		}
	}

	@Column(name = "TXNTIMUTC")
	@Temporal(TemporalType.DATE)
	public Calendar getTransactionTimeUTC() {
		return transactionTimeUTC;
	}

	public void setTransactionTimeUTC(Calendar transactionTimeUTC) {
		this.transactionTimeUTC = transactionTimeUTC;
	}

	@Column(name = "OUBFLTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFlightOperationDateOub() {
		return flightOperationDateOub;
	}

	public void setFlightOperationDateOub(Calendar flightOperationDateOub) {
		this.flightOperationDateOub = flightOperationDateOub;
	}

	@Column(name = "INBFLTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFlightOperationDateInb() {
		return flightOperationDateInb;
	}

	public void setFlightOperationDateInb(Calendar flightOperationDateInb) {
		this.flightOperationDateInb = flightOperationDateInb;
	}

	@Column(name = "OUBEVTTIM")
	@Temporal(TemporalType.DATE)
	public Calendar getEventTimeOub() {
		return eventTimeOub;
	}

	public void setEventTimeOub(Calendar eventTimeOub) {
		this.eventTimeOub = eventTimeOub;
	}

	@Column(name = "INBEVTTIM")
	@Temporal(TemporalType.DATE)
	public Calendar getEventTimeInb() {
		return eventTimeInb;
	}

	public void setEventTimeInb(Calendar eventTimeInb) {
		this.eventTimeInb = eventTimeInb;
	}
//Modified as part of bug ICRD-144653 by A-5526 
	@Column(name = "TXNTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Calendar transactionTime) {
		this.transactionTime = transactionTime;
	}

	@Column(name = "OUBCARIDR")
	public int getCarrierIdOub() {
		return carrierIdOub;
	}

	public void setCarrierIdOub(int carrierIdOub) {
		this.carrierIdOub = carrierIdOub;
	}

	@Column(name = "INBFLTSEQNUM")
	public long getFlightSequenceNumberInb() {
		return flightSequenceNumberInb;
	}

	public void setFlightSequenceNumberInb(long flightSequenceNumberInb) {
		this.flightSequenceNumberInb = flightSequenceNumberInb;
	}

	@Column(name = "OUBFLTSEQNUM")
	public long getFlightSequenceNumberOub() {
		return flightSequenceNumberOub;
	}

	public void setFlightSequenceNumberOub(long flightSequenceNumberOub) {
		this.flightSequenceNumberOub = flightSequenceNumberOub;
	}

	@Column(name = "INBCARIDR")
	public int getCarrierIdInb() {
		return carrierIdInb;
	}

	public void setCarrierIdInb(int carrierIdInb) {
		this.carrierIdInb = carrierIdInb;
	}

	@Column(name = "EVTMOD")
	public String getEventMode() {
		return eventMode;
	}

	public void setEventMode(String eventMode) {
		this.eventMode = eventMode;
	}

	@Column(name = "MALIDR")
	public String getMailIdr() {
		return mailIdr;
	}

	public void setMailIdr(String mailIdr) {
		this.mailIdr = mailIdr;
	}

	@Column(name = "OUBPOU")
	public String getPouOub() {
		return pouOub;
	}

	public void setPouOub(String pouOub) {
		this.pouOub = pouOub;
	}

	@Column(name = "INBPOL")
	public String getPolInb() {
		return polInb;
	}

	public void setPolInb(String polInb) {
		this.polInb = polInb;
	}

	@Column(name = "OUBPOACOD")
	public String getPostalCodeOub() {
		return postalCodeOub;
	}

	public void setPostalCodeOub(String postalCodeOub) {
		this.postalCodeOub = postalCodeOub;
	}

	@Column(name = "OUBFLTNUM")
	public String getFlightNumberOub() {
		return flightNumberOub;
	}

	public void setFlightNumberOub(String flightNumberOub) {
		this.flightNumberOub = flightNumberOub;
	}

	@Column(name = "INBPOACOD")
	public String getPostalCodeInb() {
		return postalCodeInb;
	}

	public void setPostalCodeInb(String postalCodeInb) {
		this.postalCodeInb = postalCodeInb;
	}

	@Column(name = "INBFLTNUM")
	public String getFlightNumberInb() {
		return flightNumberInb;
	}

	public void setFlightNumberInb(String flightNumberInb) {
		this.flightNumberInb = flightNumberInb;
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

	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "WGTCOD")
	public String getWeightCode() {
		return weightCode;
	}

	public void setWeightCode(String weightCode) {
		this.weightCode = weightCode;
	}

	@Column(name = "WGT")
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Column(name = "MSGVER")
	public String getMessageVersion() {
		return messageVersion;
	}

	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}

	@Column(name = "SNDARPCOD")
	public String getSenderAirport() {
		return senderAirport;
	}

	public void setSenderAirport(String senderAirport) {
		this.senderAirport = senderAirport;
	}

	@Column(name = "RCVARPCOD")
	public String getReceiverAirport() {
		return receiverAirport;
	}

	public void setReceiverAirport(String receiverAirport) {
		this.receiverAirport = receiverAirport;
	}

	@Column(name = "DSTARPCOD")
	public String getDestAirport() {
		return destAirport;
	}

	public void setDestAirport(String destAirport) {
		this.destAirport = destAirport;
	}
	//Changed ULDNUM as CONNUM as part of bug ICRD-143950 by A-5526
	@Column(name = "CONNUM")
	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	@Column(name = "PROSTA")
	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	@Column(name = "OUBSTACOD")
	public String getStatusCodeOub() {
		return statusCodeOub;
	}

	public void setStatusCodeOub(String statusCodeOub) {
		this.statusCodeOub = statusCodeOub;
	}

	@Column(name = "INBSTACOD")
	public String getStatusCodeInb() {
		return statusCodeInb;
	}

	public void setStatusCodeInb(String statusCodeInb) {
		this.statusCodeInb = statusCodeInb;
	}
	@Column(name = "MALSEQNUM")
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	@Column(name = "TRALVL")
	public String getTransactionLevel() {
		return transactionLevel;
	}
	public void setTransactionLevel(String transactionLevel) {
		this.transactionLevel = transactionLevel;
	}
	@Column(name = "MSGTIMUTC")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getMsgTimUTC() {
		return msgTimUTC;
	}
	public void setMsgTimUTC(Calendar msgTimUTC) {
		this.msgTimUTC = msgTimUTC;
	}
/**
 * 
 * @param mldMasterVO
 */
	private void populatePK(MLDMasterVO mldMasterVO) {
		log.log(Log.FINE, "THE MLDMasterVO VO>>>>>>>>>>", mldMasterVO);
		mldMessageMasterPK = new MLDMessageMasterPK();
		mldMessageMasterPK.setCompanyCode(mldMasterVO.getCompanyCode());
		log.exiting("MLDMessageMaster", "populatepK");

	}

	/**
	 * 
	 * @param mLDMessageMasterPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */

	public static MLDMessageMaster find(MLDMessageMasterPK mLDMessageMasterPK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(
				MLDMessageMaster.class, mLDMessageMasterPK);

	}

	/**
	 * @return
	 * @throws SystemException
	 */
	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(mail_operations));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception);
		}
	}
/**
 * 
 * @param companyCode
 * @return
 * @throws SystemException
 */
	public Collection<MLDMasterVO> findMLDDetails(String companyCode,int recordCount)
			throws SystemException {

		try {
			return constructDAO().findMLDDetails(companyCode,recordCount);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
