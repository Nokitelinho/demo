/*
 * MailScanDetail.java Created on Jun 27, 2016
 *
 * Copyright 2015 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

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


import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * To save the scanned data informations from HHT device
 *
 * @author A-5991
 *
 */

@Entity
@Table(name = "MALSCNDTL")

public class MailScanDetail {
	
	private static final String MAIL_OPERATIONS = "mail.operations";
	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");
	
	private MailScanDetailPK mailScanDetailPK;
	
	
	private String airport;
	

	private String scanData;
	
	private String scannedUser;
	
	private String deviceId;
	
	private String deviceIpAddress;
	
	private int fileSequence;
	
	private int mailSequenceNumber;
	
	private String uploadStatus;
	
	private String lastUpdateUser;
	
 	private Calendar lastUpdateTime;
 	//Added for BUG ICRD-145494 by A-5526 starts
 	private Calendar scanDate;
 	private String nodeName;
	//Added for BUG ICRD-145494 by A-5526 ends
 	private Calendar creationTime;//Added for ICRD-156218
 	private String funtionPoint;
 	private String scanType;
 	private String flightCarrierCode;
 	private String flightNumber;
 	private Calendar flightDate;
 	private String containerNumber;
 	private String fromCarrierCode;
 	private String fromFlightNumber;
 	private Calendar fromFlightDate;
 	private String deliveryFlag;
 	private String offloadReason;
 	private String returnFlag;
 	private String damageReason;
 	private String containerDestination;
 	private String containerPOU;
 	private String containerType;
 	private String malComapnyCode;
 	private String screeningUser;
 	private String securityScreeningMethod;
 	private String stgUnit;
 	public MailScanDetail() {

    }
 	
 	/**
	 * @return Returns the mailResditPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "mailBagId", column = @Column(name = "MALIDR")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))
			 })
	public MailScanDetailPK getMailScanDetailPK() {
		return mailScanDetailPK;
	}

	/**
	 * @param mailResditPK
	 *            The mailResditPK to set.
	 */
	public void setMailScanDetailPK(MailScanDetailPK mailScanDetailPK) {
		this.mailScanDetailPK = mailScanDetailPK;
	}
 	
 	
	
	@Column(name = "SCNPRT")
	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}
	
	/**
	 * Get the value of scanned string
	 * @return
	 */
	@Column(name = "SCNSTR")
	public String getScanData() {
		return scanData;
	}
	/**
	 * Set the value of scanned string
	 * @param scannedString
	 */
	public void setScanData(String scanData) {
		this.scanData = scanData;
	}
	/**
	 * Get the value of Scanned user
	 * @return
	 */
	@Column(name = "SCNUSR")
	public String getScannedUser() {
		return scannedUser;
	}
	/**
	 * Set the value of Scanned user
	 * @param scannedUser
	 */
	public void setScannedUser(String scannedUser) {
		this.scannedUser = scannedUser;
	}
	/**
	 * Get the value of Scanned Device Identifier
	 * @return
	 */
	@Column(name = "DEVNAM")
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * Set the value of Scanned Device Identifier
	 * @param deviceId
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * Get the value of File sequence number
	 * @return
	 */
	@Column(name = "FLESEQNUM")
	public int getFileSequence() {
		return fileSequence;
	}
	/**
	 * Set the value of File sequence number
	 * @param fileSequence
	 */
	public void setFileSequence(int fileSequence) {
		this.fileSequence = fileSequence;
	}
	/**
	 * Get the value of Operation status
	 * @return
	 */
	@Column(name = "UPLSTA")
	public String getUploadStatus() {
		return uploadStatus;
	}
	/**
	 * Set the value of Operation status
	 * @param status
	 */
	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	/**
	 * Get the value of Last Updated User
	 * @return
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * Set the value of Last Updated User
	 * @param lastUpdateUser
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * Get the value of Last Updated Time
	 * @return
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * Set the value of Last Updated User
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	@Column(name = "SEQNUM")
	public int getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	public void setMailSequenceNumber(int mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	@Column(name = "DEVADR")
	public String getDeviceIpAddress() {
		return deviceIpAddress;
	}

	public void setDeviceIpAddress(String deviceIpAddress) {
		this.deviceIpAddress = deviceIpAddress;
	}
	
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
	@Column(name = "NODNAM")
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**
	 * @return the creationTime
	 */
	@Column(name="CRTTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Calendar creationTime) {
		this.creationTime = creationTime;
	}

	@Column(name="FUNPNT")
	public String getFuntionPoint() {
		return funtionPoint;
	}

	public void setFuntionPoint(String funtionPoint) {
		this.funtionPoint = funtionPoint;
	}
	@Column(name="SCNTYP")
	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	@Column(name="FLTCARCOD")
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	@Column(name="FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	@Column(name="FLTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}
	@Column(name="CONNUM")
	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	@Column(name="FRMCARCOD")
	public String getFromCarrierCode() {
		return fromCarrierCode;
	}

	public void setFromCarrierCode(String fromCarrierCode) {
		this.fromCarrierCode = fromCarrierCode;
	}
	@Column(name="FRMFLTNUM")
	public String getFromFlightNumber() {
		return fromFlightNumber;
	}

	public void setFromFlightNumber(String fromFlightNumber) {
		this.fromFlightNumber = fromFlightNumber;
	}
	@Column(name="FRMFLTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFromFlightDate() {
		return fromFlightDate;
	}

	public void setFromFlightDate(Calendar fromFlightDate) {
		this.fromFlightDate = fromFlightDate;
	}
	@Column(name="DLVFLG")
	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}
	@Column(name="OFLRSN")
	public String getOffloadReason() {
		return offloadReason;
	}

	public void setOffloadReason(String offloadReason) {
		this.offloadReason = offloadReason;
	}
	@Column(name="RTNFLG")
	public String getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}
	@Column(name="DMGRSN")
	public String getDamageReason() {
		return damageReason;
	}

	public void setDamageReason(String damageReason) {
		this.damageReason = damageReason;
	}
	@Column(name="CONDST")
	public String getContainerDestination() {
		return containerDestination;
	}

	public void setContainerDestination(String containerDestination) {
		this.containerDestination = containerDestination;
	}
	@Column(name="CONPOU")
	public String getContainerPOU() {
		return containerPOU;
	}

	public void setContainerPOU(String containerPOU) {
		this.containerPOU = containerPOU;
	}

	@Column(name="CONTYP")
	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	@Column(name="MALCMPCOD")
	public String getMalComapnyCode() {
		return malComapnyCode;
	}

	public void setMalComapnyCode(String malComapnyCode) {
		this.malComapnyCode = malComapnyCode;
	}
	@Column(name="SCRUSR")
	public String getScreeningUser() {
		return screeningUser;
	}

	public void setScreeningUser(String screeningUser) {
		this.screeningUser = screeningUser;
	}
	@Column(name="SECSCRMTHCOD")
	public String getSecurityScreeningMethod() {
		return securityScreeningMethod;
	}

	public void setSecurityScreeningMethod(String securityScreeningMethod) {
		this.securityScreeningMethod = securityScreeningMethod;
	}
	@Column(name="STGUNT")
	public String getStgUnit() {
		return stgUnit;
	}

	public void setStgUnit(String stgUnit) {
		this.stgUnit = stgUnit;
	}

	/**
	 * @author A-5991
	 * @param mailScanDetailVO
	 */
	private void populatePK(MailScanDetailVO mailScanDetailVO) {
		log.entering("mailScanDetail", "populatePK");
		log.log(Log.FINE, "THE mailScanDetailVO VO>>>>>>>>>>", mailScanDetailVO);
		mailScanDetailPK = new MailScanDetailPK();
		mailScanDetailPK.setCompanyCode(   mailScanDetailVO.getCompanyCode());
		mailScanDetailPK.setMailBagId( mailScanDetailVO.getMailBagId());      
		
		log.exiting("MailResdit", "populatepK");
	}
/**
 * @author A-5991
 * @param mailScanDetailVO
 */
	private void populateAttributes(MailScanDetailVO mailScanDetailVO) {
      
        setDeviceId(mailScanDetailVO.getDeviceId());
        setFileSequence(mailScanDetailVO.getFileSequence());
        setLastUpdateTime(mailScanDetailVO.getLastUpdateTime());
        setLastUpdateUser(mailScanDetailVO.getLastUpdateUser());
        setScanData(mailScanDetailVO.getScanData());
        setScannedUser(mailScanDetailVO.getScannedUser());
        setUploadStatus(mailScanDetailVO.getUploadStatus());
        setAirport(mailScanDetailVO.getAirport());
        setMailSequenceNumber(mailScanDetailVO.getMailSequenceNumber());
        setDeviceIpAddress(mailScanDetailVO.getDeviceIpAddress());
      //Added for BUG ICRD-145494 by A-5526 starts
        setScanDate(mailScanDetailVO.getScanDate().toCalendar());
        setNodeName(mailScanDetailVO.getNodeName());
        //Added for BUG ICRD-145494 by A-5526 ends
        setCreationTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));//Added for ICRD-156218
        setFuntionPoint(mailScanDetailVO.getFuntionPoint());
        setScanType(mailScanDetailVO.getScanType());
        setFlightCarrierCode(mailScanDetailVO.getFlightCarrierCode());
        setFlightNumber(mailScanDetailVO.getFlightNumber());
        if(mailScanDetailVO.getFlightDate()!= null){
        setFlightDate(mailScanDetailVO.getFlightDate().toCalendar());
        }
        setContainerNumber(mailScanDetailVO.getContainerNumber());
        setFromCarrierCode(mailScanDetailVO.getFromCarrierCode());
        setFromFlightNumber(mailScanDetailVO.getFromFlightNumber());
        if(mailScanDetailVO.getFromFlightDate()!= null){
        setFromFlightDate(mailScanDetailVO.getFromFlightDate().toCalendar());
        }
        setDeliveryFlag(mailScanDetailVO.getDeliveryFlag());
        setOffloadReason(mailScanDetailVO.getOffloadReason());
        setReturnFlag(mailScanDetailVO.getReturnFlag());
        setDamageReason(mailScanDetailVO.getDamageReason());
        setContainerDestination(mailScanDetailVO.getContainerDestination());
        setContainerPOU(mailScanDetailVO.getContainerPOU());
        setContainerType(mailScanDetailVO.getContainerType());
        setMalComapnyCode(mailScanDetailVO.getMalComapnyCode());
        setScreeningUser(mailScanDetailVO.getScreeningUser());
        setSecurityScreeningMethod(mailScanDetailVO.getSecurityScreeningMethod());
        setStgUnit(mailScanDetailVO.getStgUnit());
        
            }

	/**
	 * @author A-5991
	 * @param mailScanDetailPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */

	public static MailScanDetail find(MailScanDetailPK mailScanDetailPK)
	throws SystemException, FinderException {
return PersistenceController.getEntityManager().find(MailScanDetail.class,
		mailScanDetailPK);

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
     * @author A-5991
     * @param mailScanDetailVO
     * @throws SystemException
     */
    public MailScanDetail(MailScanDetailVO mailScanDetailVO) throws SystemException {
		log.entering("MailScanDetail", "init");
		populatePK(mailScanDetailVO);
		populateAttributes(mailScanDetailVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("MailResdit", "init");
	}
 	
    /**
     * Method to fetch MailScanDetails with UPLSTA=N
     * @param companyCode
     * @return
     * @throws SystemException
     */
    	public static Collection<MailScanDetailVO> findScannedMailDetails(
    			String companyCode,int uploadCount) throws SystemException{
    		try{
    			return constructDAO().findScannedMailDetails(companyCode,uploadCount);
    		}catch(PersistenceException persistenceException){
    			throw new SystemException(persistenceException.getErrorCode());
    		}
    	}
/**
 * processMailOperationFromFile
 * @param fileUploadFilterVO
 * @return
 * @throws SystemException
 */
	public static String processMailOperationFromFile(
			FileUploadFilterVO fileUploadFilterVO) throws SystemException {
		String processStatus = null;
	    try {
	      processStatus = constructDAO().processMailOperationFromFile(fileUploadFilterVO);
	    } catch (PersistenceException per) {
	      throw new SystemException(per.getMessage(), per);
	    }

	    return processStatus;
	}
/**
 * fetchDataForOfflineUpload
 * @param companyCode
 * @param fileType
 * @return
 * @throws SystemException
 */
	public static Collection<MailUploadVO> fetchDataForOfflineUpload(String companyCode,
			String fileType) throws SystemException {
		try{
		return constructDAO().fetchDataForOfflineUpload(companyCode,fileType);
    		}catch(PersistenceException persistenceException){
    			throw new SystemException(persistenceException.getErrorCode());
    		}
    	}
    /**
     * removeDataFromTempTable
     * @param fileUploadFilterVO
     * @throws SystemException
     */
	public static void removeDataFromTempTable(
			FileUploadFilterVO fileUploadFilterVO) throws SystemException{
		try {
			constructDAO().removeDataFromTempTable(fileUploadFilterVO);
		} catch (PersistenceException e) {
			 throw new SystemException(e.getMessage(), e);
		} 
		
	}
    
}
