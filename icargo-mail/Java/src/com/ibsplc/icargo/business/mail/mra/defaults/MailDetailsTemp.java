/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.MailDetailsTemp.java
 *
 *	Created by	:	A-7531
 *	Created on	:	30-Nov-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.MailDetailsTemp.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	30-Nov-2018	:	Draft
 */
@Table(name = "MALMRAINT")
@Entity
@Staleable
public class MailDetailsTemp {

	
	private static final String MODULE_NAME = "mail.mra.defaults";

    private static final String CLASS_NAME = "MailDetailsTemp";

    private static final Log log = LogFactory.getLogger("MALMRA Intermediate table");
    
    private MailDetailsTempPK mailDetailsTempPK;
	
	
    private Calendar lastUpdateTime;
	private String lastUpdateUser;
	private String flightCarrierCod;
	private int flightCarrierIdr;
	private String flightNum;
	private long flightSeqNum;
	private long malseqnum;
	private String processStatus;
	private Calendar scanDat;
	private String scanPort;
	private int segSerNum;
	private String taxUpdationFlag;
	private String uldNum;
	private String mailSource;
	
	
	
	
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "seqNum", column = @Column(name = "SEQNUM"))})
		
	/**
	 * 	Getter for mailDetailsTempPK 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public MailDetailsTempPK getMailDetailsTempPK() {
		return mailDetailsTempPK;
	}
	/**
	 *  @param mailDetailsTempPK the mailDetailsTempPK to set
	 * 	Setter for mailDetailsTempPK 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setMailDetailsTempPK(MailDetailsTempPK mailDetailsTempPK) {
		this.mailDetailsTempPK = mailDetailsTempPK;
	}
	
	
	
	public MailDetailsTemp() {

	  }
	  public MailDetailsTemp(RateAuditDetailsVO rateAuditVO) throws SystemException {
    	  populatePk(rateAuditVO);
          populateAttributes(rateAuditVO);
          try {
              PersistenceController.getEntityManager().persist(this);
          } catch(CreateException createException) {
              throw new SystemException(createException.getMessage(),
                      createException);
          }
    }
	/**
	 * 	Getter for lastUpdateTime 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "LSTUPDTIM")
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 *  @param lastUpdateTime the lastUpdateTime to set
	 * 	Setter for lastUpdateTime 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * 	Getter for lastUpdateUser 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 *  @param lastUpdateUser the lastUpdateUser to set
	 * 	Setter for lastUpdateUser 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * 	Getter for flightCarrierCod 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "FLTCARCOD")
	public String getFlightCarrierCod() {
		return flightCarrierCod;
	}
	/**
	 *  @param flightCarrierCod the flightCarrierCod to set
	 * 	Setter for flightCarrierCod 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setFlightCarrierCod(String flightCarrierCod) {
		this.flightCarrierCod = flightCarrierCod;
	}
	/**
	 * 	Getter for flightCarrierIdr 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "FLTCARIDR")
	public int getFlightCarrierIdr() {
		return flightCarrierIdr;
	}
	/**
	 *  @param flightCarrierIdr the flightCarrierIdr to set
	 * 	Setter for flightCarrierIdr 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setFlightCarrierIdr(int flightCarrierIdr) {
		this.flightCarrierIdr = flightCarrierIdr;
	}
	/**
	 * 	Getter for flightNum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "FLTNUM")
	public String getFlightNum() {
		return flightNum;
	}
	/**
	 *  @param flightNum the flightNum to set
	 * 	Setter for flightNum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	/**
	 * 	Getter for flightSeqNum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "FLTSEQNUM")
	public long getFlightSeqNum() {
		return flightSeqNum;
	}
	/**
	 *  @param flightSeqNum the flightSeqNum to set
	 * 	Setter for flightSeqNum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setFlightSeqNum(long flightSeqNum) {
		this.flightSeqNum = flightSeqNum;
	}
	/**
	 * 	Getter for malseqnum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "MALSEQNUM")
	public long getMalseqnum() {
		return malseqnum;
	}
	/**
	 *  @param malseqnum the malseqnum to set
	 * 	Setter for malseqnum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setMalseqnum(long malseqnum) {
		this.malseqnum = malseqnum;
	}
	/**
	 * 	Getter for processStatus 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "PROSTA")
	public String getProcessStatus() {
		return processStatus;
	}
	/**
	 *  @param processStatus the processStatus to set
	 * 	Setter for processStatus 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	/**
	 * 	Getter for scanDat 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "SCNDAT")
	public Calendar getScanDat() {
		return scanDat;
	}
	/**
	 *  @param scanDat the scanDat to set
	 * 	Setter for scanDat 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setScanDat(Calendar scanDat) {
		this.scanDat = scanDat;
	}
	/**
	 * 	Getter for scanPort 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "SCNPRT")
	public String getScanPort() {
		return scanPort;
	}
	/**
	 *  @param scanPort the scanPort to set
	 * 	Setter for scanPort 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setScanPort(String scanPort) {
		this.scanPort = scanPort;
	}
	/**
	 * 	Getter for segSerNum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "SEGSERNUM")
	public int getSegSerNum() {
		return segSerNum;
	}
	/**
	 *  @param segSerNum the segSerNum to set
	 * 	Setter for segSerNum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setSegSerNum(int segSerNum) {
		this.segSerNum = segSerNum;
	}
	/**
	 * 	Getter for taxUpdationFlag 
	 *	Added by : A-10383 on 17-Feb-2023
	 * 	Used for :
	 */
	@Column(name = "TAXUPD")
	public String getTaxUpdationFlag() {
		return taxUpdationFlag;
	}
	/**
	 *  @param taxUpdationFlag the taxUpdationFlag to set
	 * 	Setter for taxUpdationFlag 
	 *	Added by : A-10383 on 17-Feb-2023
	 * 	Used for :
	 */
	public void setTaxUpdationFlag(String taxUpdationFlag) {
		this.taxUpdationFlag = taxUpdationFlag;
	}
	/**
	 * 	Getter for uldNum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Column(name = "ULDNUM")
	public String getUldNum() {
		return uldNum;
	}
	/**
	 *  @param uldNum the uldNum to set
	 * 	Setter for uldNum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setUldNum(String uldNum) {
		this.uldNum = uldNum;
	}
	@Column(name = "MALSRC")
	public String getMailSource() {
		return mailSource;
	}
	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}
    /**
     * 
     * 	Method		:	MailDetailsTemp.find
     *	Added by 	:	A-7531 on 03-Dec-2018
     * 	Used for 	:
     *	Parameters	:	@param mailDetailsTempPK
     *	Parameters	:	@return
     *	Parameters	:	@throws SystemException
     *	Parameters	:	@throws FinderException 
     *	Return type	: 	MailDetailsTemp
     */
	public static Collection<MRABillingDetailsVO> findMaildetails(String companyCode) throws SystemException {

		try {
			Log log = LogFactory.getLogger("--Maildetails---");
			log.entering("MailDetailsTemp", "entity");
			return constructDAO().findBillingEntriesAtMailbagLevel(companyCode);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	  
	
	/*public void update(MailDetailsTemp mailDetailsTemp) throws SystemException {
			Log log = LogFactory.getLogger("MRA DEFAULTS");
			log.entering("MRA Defaults", "update");
			this.setTaxUpdationFlag(mailDetailsTemp.getTaxUpdationFlag());
			log.exiting("MRA Defaults", "update");
		}*/
	  
	  private static MRADefaultsDAO constructDAO() throws PersistenceException,
		SystemException {
			Log log = LogFactory.getLogger("MRA BILLING");
			log.entering("MailDetailsTemp", "constructDAO");
			EntityManager entityManager = PersistenceController.getEntityManager();
			return MRADefaultsDAO.class
			.cast(entityManager.getQueryDAO(MODULE_NAME));
		}
	  
	  
	  
	  public static MailDetailsTemp find(MRABillingDetailsVO mRABillingDetailsVO)
				throws SystemException , FinderException{

		  MailDetailsTempPK mailDetailsTempPK = new MailDetailsTempPK();
					
					mailDetailsTempPK.setCompanyCode(mRABillingDetailsVO.getCompanyCode());
					mailDetailsTempPK.setSeqNum(mRABillingDetailsVO.getSeqNumInt());
					return PersistenceController.getEntityManager().find(MailDetailsTemp.class, mailDetailsTempPK);
				}

	  private void populatePk(RateAuditDetailsVO rateAuditVO) throws SystemException {
		  mailDetailsTempPK = new MailDetailsTempPK();
		  mailDetailsTempPK.setCompanyCode(rateAuditVO.getCompanyCode());
	    }
	  private void populateAttributes(RateAuditDetailsVO rateAuditVO) {
		  setMalseqnum(rateAuditVO.getMailSequenceNumber());
		  setFlightCarrierIdr(rateAuditVO.getCarrierid());
		  setFlightCarrierCod(rateAuditVO.getCarrierCode());
		  setFlightNum(rateAuditVO.getFlightno());
		  setFlightSeqNum(rateAuditVO.getFlightseqno());
		  setSegSerNum(rateAuditVO.getSegSerNo());
		  setLastUpdateTime(rateAuditVO.getLastUpdateTime().toCalendar());
		  setLastUpdateUser(rateAuditVO.getLastUpdateUser());
		  setProcessStatus(rateAuditVO.getProcessStatus());
		  setMailSource(rateAuditVO.getSource());
	  }
    
}
