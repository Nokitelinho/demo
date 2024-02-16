/*
 * MRASisSupportingDocument created on October 29, 2018 
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import java.sql.Blob;
import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.util.PersistenceUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Entity class for SIS-Supporting documents.
 * The class handles the setter and getter for table CRASISSUPDOCDTL and its columns.
 * 
 * @author a-8061
 *
 */

/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  October 29, 2018				A-8061		 Created
 */

@Table(name = "MRASISSUPDOCDTL")
@Entity
public class MRASisSupportingDocument {
	
	private static final String CLASS_NAME = "SisSupportingDocument";
	private Log log = LogFactory.getLogger("CRA SISBILLING");
	
	private String memNumber;

	private String carierOrigin;
	private String carierDestination;
	private String lastUpdatedUser;

	private int dupNumber;
	private int sequenceNumber;
	
	private Blob fileData;
	private Calendar lastUpdatedTime;
	private MRASisSupportingDocumentPK sisSupportingDocumentPK;
	
	public MRASisSupportingDocument(){}
		
	
	
	public MRASisSupportingDocument(
			SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO)
			throws SystemException {
		populatePK(sisSupportingDocumentDetailsVO);
		populateAttributes(sisSupportingDocumentDetailsVO);
	}
	
	/**
	 * @return the extInterfacelogPK
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD") ),			
			@AttributeOverride(name = "billedAirline", column = @Column(name = "BLDARL") ),			
			@AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD") ),
			@AttributeOverride(name = "invoiceNumber", column = @Column(name = "INVNUM") ),
			@AttributeOverride(name = "invoiceSerialNumber", column = @Column(name = "INVSERNUM") ),
			@AttributeOverride(name = "billingType", column = @Column(name = "BLGTYP") ),
			@AttributeOverride(name = "docSerNumber", column = @Column(name = "DOCSERNUM") ),
			@AttributeOverride(name = "fileName", column = @Column(name = "FILNAM") ),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM") )})
	public MRASisSupportingDocumentPK getSisSupportingDocumentPK() {
		return sisSupportingDocumentPK;
	}
	
	public void setSisSupportingDocumentPK(MRASisSupportingDocumentPK sisSupportingDocumentPK){
		this.sisSupportingDocumentPK = sisSupportingDocumentPK;
	}
	
	 
	/**
	 * @return the memNumber
	 */
	@Column(name = "MEMNUM")
	public String getMemNumber() {
		return memNumber;
	}
	/**
	 * @param memNumber the memNumber to set
	 */
	public void setMemNumber(String memNumber) {
		this.memNumber = memNumber;
	}

	/**
	 * @return the carierOrigin
	 */
	@Column(name = "CARORG")
	public String getCarierOrigin() {
		return carierOrigin;
	}
	/**
	 * @param carierOrigin the carierOrigin to set
	 */
	public void setCarierOrigin(String carierOrigin) {
		this.carierOrigin = carierOrigin;
	}
	/**
	 * @return the carierDestination
	 */
	@Column(name = "CARDST")
	public String getCarierDestination() {
		return carierDestination;
	}
	/**
	 * @param carierDestination the carierDestination to set
	 */
	public void setCarierDestination(String carierDestination) {
		this.carierDestination = carierDestination;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the dupNumber
	 */
	@Column(name = "DUPNUM")
	public int getDupNumber() {
		return dupNumber;
	}
	/**
	 * @param dupNumber the dupNumber to set
	 */
	public void setDupNumber(int dupNumber) {
		this.dupNumber = dupNumber;
	}
	/**
	 * @return the sequenceNumber
	 */
	@Column(name = "SEQNUM")
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return the fileData
	 */
	@Column(name = "FILDAT")
	public Blob getFileData() {
		return fileData;
	}
	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(Blob fileData) {
		this.fileData = fileData;
	}
	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name = "LSTUPDTIM")
	@Version
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	
	



	/**
	 * Method to populate the PK attributes of the entity.
	 * 
	 * @param sisSupportingDocumentDetailsVO
	 */
	private void populatePK(
			SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO) {

		this.sisSupportingDocumentPK = new MRASisSupportingDocumentPK(
				sisSupportingDocumentDetailsVO.getCompanyCode(),				
				sisSupportingDocumentDetailsVO.getBilledAirline(),
				sisSupportingDocumentDetailsVO.getClearancePeriod(),
				sisSupportingDocumentDetailsVO.getInvoiceNumber(),
				sisSupportingDocumentDetailsVO.getInvoiceSerialNumber(),
				sisSupportingDocumentDetailsVO.getBillingType(),
				sisSupportingDocumentDetailsVO.getDocumentSerialNumber(),
				sisSupportingDocumentDetailsVO.getFilename(),
				sisSupportingDocumentDetailsVO.getMailSequenceNumber()
				);	
	}

	/**
	 * Method to populate the attributes of the entity.
	 * 
	 * @param sisSupportingDocumentDetailsVO
	 * @throws SystemException
	 */
	private void populateAttributes(
			SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO)
			throws SystemException {

		this.setMemNumber(sisSupportingDocumentDetailsVO.getMemoNumber());
		this.setCarierOrigin(sisSupportingDocumentDetailsVO.getCarierOrigin());
		this.setCarierDestination(sisSupportingDocumentDetailsVO
				.getCarierDestination());
		this.setDupNumber(sisSupportingDocumentDetailsVO.getDuplicateNumber());
		this.setSequenceNumber(sisSupportingDocumentDetailsVO
				.getSequenceNumber());

		
		Blob blob = PersistenceUtils.createBlob(sisSupportingDocumentDetailsVO
				.getFileData());
		
		this.setFileData(blob);
		this.setLastUpdatedUser(sisSupportingDocumentDetailsVO
				.getLastUpdatedUser());
		this.setLastUpdatedTime(sisSupportingDocumentDetailsVO
				.getLastUpdatedTime());

	}
	
	/**
	 * Method to save the entity.
	 * 
	 * @throws SystemException
	 */
	public void save() throws SystemException {
		log.entering(CLASS_NAME, "save");
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode());
		}
		log.exiting(CLASS_NAME, "save");
	}

	/**
	 * Method to remove the entity.
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering(CLASS_NAME, "remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		}
		log.exiting(CLASS_NAME, "remove");
	}
	
	/**
	 * Method to find the entity.
	 * 
	 * @param sisSupportingDocumentDetailsVO
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public MRASisSupportingDocument find(
			SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO)
			throws FinderException, SystemException {
		populatePK(sisSupportingDocumentDetailsVO);
		return PersistenceController.getEntityManager().find(
				MRASisSupportingDocument.class, this.sisSupportingDocumentPK);
	}
	
	
	
	

}
