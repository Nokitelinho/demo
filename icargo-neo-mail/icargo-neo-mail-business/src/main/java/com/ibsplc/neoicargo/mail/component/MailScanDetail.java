/*
 * MailScanDetail.java Created on Jun 27, 2016
 *
 * Copyright 2015 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.MailScanDetailVO;
import com.ibsplc.neoicargo.mail.vo.MailUploadVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
/**
 * To save the scanned data informations from HHT device
 *
 * @author A-5991
 *
 */
@Setter
@Getter
@Entity
@IdClass(MailScanDetailPK.class)
@Table(name = "MALSCNDTL")
@SequenceGenerator(name = "MALSCNDTLSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALSCNDTL_SEQ")
public class MailScanDetail extends BaseEntity implements Serializable {
	
	private static final String MAIL_OPERATIONS = "mail.operations";
	//private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	@Autowired
	private LocalDate localDateUtil;

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "MALIDR")
	private String mailBagId;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALSCNDTLSEQ")
	@Column(name = "SERNUM")
	private int serialNumber;

	@Column(name = "SCNPRT")
	private String airport;


	@Column(name = "SCNSTR")
	private String scanData;

	@Column(name = "SCNUSR")
	private String scannedUser;

	@Column(name = "DEVNAM")
	private String deviceId;

	@Column(name = "DEVADR")
	private String deviceIpAddress;

	@Column(name = "FLESEQNUM")
	private int fileSequence;

	@Column(name = "SEQNUM")
	private int mailSequenceNumber;

	@Column(name = "UPLSTA")
	private String uploadStatus;

	@Column(name = "SCNDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	//Added for BUG ICRD-145494 by A-5526 starts
 	private LocalDateTime scanDate;
	@Column(name = "NODNAM")
	private String nodeName;
	@Column(name="CRTTIM")
	//@Temporal(TemporalType.TIMESTAMP)
	//Added for BUG ICRD-145494 by A-5526 ends
 	private LocalDateTime creationTime;//Added for ICRD-156218
 	public MailScanDetail() {

    }


	/**
	 * @author A-5991
	 * @param mailScanDetailVO
	 */
	private void populatePK(MailScanDetailVO mailScanDetailVO) {
//		log.entering("mailScanDetail", "populatePK");
//		log.log(Log.FINE, "THE mailScanDetailVO VO>>>>>>>>>>", mailScanDetailVO);
		this.setCompanyCode(   mailScanDetailVO.getCompanyCode());
		this.setMailBagId( mailScanDetailVO.getMailBagId());
		
	//	log.exiting("MailResdit", "populatepK");
	}
/**
 * @author A-5991
 * @param mailScanDetailVO
 */
	private void populateAttributes(MailScanDetailVO mailScanDetailVO) {
      
        setDeviceId(mailScanDetailVO.getDeviceId());
        setFileSequence(mailScanDetailVO.getFileSequence());
        setLastUpdatedUser(mailScanDetailVO.getLastUpdateUser());
        setScanData(mailScanDetailVO.getScanData());
        setScannedUser(mailScanDetailVO.getScannedUser());
        setUploadStatus(mailScanDetailVO.getUploadStatus());
        setAirport(mailScanDetailVO.getAirport());
        setMailSequenceNumber(mailScanDetailVO.getMailSequenceNumber());
        setDeviceIpAddress(mailScanDetailVO.getDeviceIpAddress());
      //Added for BUG ICRD-145494 by A-5526 starts
        setScanDate(mailScanDetailVO.getScanDate().toLocalDateTime());
        setNodeName(mailScanDetailVO.getNodeName());
        //Added for BUG ICRD-145494 by A-5526 ends
        setCreationTime(localDateUtil.getLocalDate(null, false).toLocalDateTime());//Added for ICRD-156218
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
    public static MailOperationsDAO constructDAO() throws SystemException {
        try {
            return MailOperationsDAO.class.cast(PersistenceController
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
	//	log.entering("MailScanDetail", "init");
		populatePK(mailScanDetailVO);
		populateAttributes(mailScanDetailVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		//vlog.exiting("MailResdit", "init");
	}
 	
    /**
     * Method to fetch MailScanDetails with UPLSTA=N
     * @param companyCode
     * @return
     * @throws SystemException
     */
    	public static Collection<MailScanDetailVO> findScannedMailDetails(
    			String companyCode,int uploadCount) throws SystemException{

			//TODO:Neo to correct
//    		try{
//    			return constructDAO().findScannedMailDetails(companyCode,uploadCount);
//
//    		}catch(PersistenceException persistenceException){
//    			throw new SystemException(persistenceException.getErrorCode());
//    		}
			return null;
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
		//TODO:Neo to correct
//	    try {
//	      processStatus = constructDAO().processMailOperationFromFile(fileUploadFilterVO);
//	    } catch (PersistenceException per) {
//	      throw new SystemException(per.getMessage(), per);
//	    }

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
		//TODO:Neo to correct
//		try{
//		return constructDAO().fetchDataForOfflineUpload(companyCode,fileType);
//    		}catch(PersistenceException persistenceException){
//    			throw new SystemException(persistenceException.getErrorCode());
//    		}
		return null;
    	}
    /**
     * removeDataFromTempTable
     * @param fileUploadFilterVO
     * @throws SystemException
     */
	public static void removeDataFromTempTable(
			FileUploadFilterVO fileUploadFilterVO) throws SystemException{
		//TODO:Neo to correct
//		try {
//			constructDAO().removeDataFromTempTable(fileUploadFilterVO);
//		} catch (PersistenceException e) {
//			 throw new SystemException(e.getMessage(), e);
//		}
		
	}
    
}
