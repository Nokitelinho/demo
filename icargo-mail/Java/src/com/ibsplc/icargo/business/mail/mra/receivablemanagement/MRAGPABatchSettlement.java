/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.MRAGPABatchSettlement.java
 *
 *	Created by	:	A-5219
 *	Created on	:	11-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
 
package com.ibsplc.icargo.business.mail.mra.receivablemanagement;


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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchUploadedVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO;
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
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.MRAGPABatchSettlement.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8331	:	10-Nov-2021	:	Draft
 */

@Entity
@Staleable
@Table(name = "MALMRAGPABTHSTL")
public class MRAGPABatchSettlement {

	private static final  Log LOG = LogFactory.getLogger("MRA receivablemanagement MRAGPABatchSettlement");

	private static final String MODULE="mail.mra.receivablemanagement";

	private MRAGPABatchSettlementPK mRAGPABatchSettlementPk;

	private Calendar settlementDate;
	private String settlementCurrencyCode;

	private String batchStatus;

	private double totalBatchAmount;

	private double appliedAmount;

	private double unAppliedAmount;

	private String remarks;

	private String processId;

	private String fileType;

	private String acccountTxnId;

	private String source;

	private String lastUpdateUser;

	private Calendar lastUpdatedTime;
	
	private int recordCount;

	private String fileName;
	
	private static final String CLASSNAME="MRAGPABatchSettlement";
	/**
	 * 	Getter for mRAGPABatchSettlementPk
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "batchId", column = @Column(name = "BTHSTLIDR")),
			@AttributeOverride(name = "poaCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "batchSequenceNumber", column = @Column(name = "BTHSTLSEQNUM")),
			})
	public MRAGPABatchSettlementPK getmRAGPABatchSettlementPk() {
		return mRAGPABatchSettlementPk;
	}

	/**
	 *  @param mRAGPABatchSettlementPk the mRAGPABatchSettlementPk to set
	 * 	Setter for mRAGPABatchSettlementPk
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setmRAGPABatchSettlementPk(MRAGPABatchSettlementPK mRAGPABatchSettlementPk) {
		this.mRAGPABatchSettlementPk = mRAGPABatchSettlementPk;
	}

	/**
	 * 	Getter for settlementDate
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="STLDAT")
	public Calendar getSettlementDate() {
		return settlementDate;
	}

	/**
	 *  @param settlementDate the settlementDate to set
	 * 	Setter for settlementDate
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setSettlementDate(Calendar settlementDate) {
		this.settlementDate = settlementDate;
	}

	/**
	 * 	Getter for settlementCurrencyCode
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="STLCURCOD")
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	/**
	 *  @param settlementCurrencyCode the settlementCurrencyCode to set
	 * 	Setter for settlementCurrencyCode
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	/**
	 * 	Getter for batchStatus
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="BTHSTA")
	public String getBatchStatus() {
		return batchStatus;
	}

	/**
	 *  @param batchStatus the batchStatus to set
	 * 	Setter for batchStatus
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	/**
	 * 	Getter for totalBatchAmount
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="BTHTOTAMT")
	public double getTotalBatchAmount() {
		return totalBatchAmount;
	}

	/**
	 *  @param totalBatchAmount the totalBatchAmount to set
	 * 	Setter for totalBatchAmount
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setTotalBatchAmount(double totalBatchAmount) {
		this.totalBatchAmount = totalBatchAmount;
	}

	/**
	 * 	Getter for appliedAmount
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="APLAMT")
	public double getAppliedAmount() {
		return appliedAmount;
	}

	/**
	 *  @param appliedAmount the appliedAmount to set
	 * 	Setter for appliedAmount
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setAppliedAmount(double appliedAmount) {
		this.appliedAmount = appliedAmount;
	}

	/**
	 * 	Getter for unAppliedAmount
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="UNNAPLAMT")
	public double getUnAppliedAmount() {
		return unAppliedAmount;
	}

	/**
	 *  @param unAppliedAmount the unAppliedAmount to set
	 * 	Setter for unAppliedAmount
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setUnAppliedAmount(double unAppliedAmount) {
		this.unAppliedAmount = unAppliedAmount;
	}

	/**
	 * 	Getter for remarks
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 *  @param remarks the remarks to set
	 * 	Setter for remarks
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 	Getter for processId
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="PCRIDR")
	public String getProcessId() {
		return processId;
	}

	/**
	 *  @param processId the processId to set
	 * 	Setter for processId
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * 	Getter for fileType
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="FILTYP")
	public String getFileType() {
		return fileType;
	}

	/**
	 *  @param fileType the fileType to set
	 * 	Setter for fileType
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * 	Getter for acccountTxnId
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="ACCTXNIDR")
	public String getAcccountTxnId() {
		return acccountTxnId;
	}

	/**
	 *  @param acccountTxnId the acccountTxnId to set
	 * 	Setter for acccountTxnId
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setAcccountTxnId(String acccountTxnId) {
		this.acccountTxnId = acccountTxnId;
	}

	/**
	 * 	Getter for source
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="SRC")
	public String getSource() {
		return source;
	}

	/**
	 *  @param source the source to set
	 * 	Setter for source
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * 	Getter for lastUpdateUser
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 *  @param lastUpdateUser the lastUpdateUser to set
	 * 	Setter for lastUpdateUser
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * 	Getter for lastUpdatedTime
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	 @Version
	 @Column(name="LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 *  @param lastUpdatedTime the lastUpdatedTime to set
	 * 	Setter for lastUpdatedTime
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}


	public MRAGPABatchSettlement(){

	}
	
	public MRAGPABatchSettlement(PaymentBatchDetailsVO advancePaymentVO)
			throws  SystemException {
		
		LOG.entering(CLASSNAME, CLASSNAME);
		populatePK(advancePaymentVO);
		populateAllAttributes(advancePaymentVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException) {
         throw new SystemException(createException.getMessage(),
                 createException);
		}
		LOG.exiting(CLASSNAME, CLASSNAME);
	}

	private void populatePK(PaymentBatchDetailsVO advancePaymentVO) {
		LOG.entering(CLASSNAME, "populatePK");

		this.setmRAGPABatchSettlementPk(new MRAGPABatchSettlementPK(advancePaymentVO.getCompanyCode(),advancePaymentVO.getBatchID(),
				advancePaymentVO.getPaCode(),advancePaymentVO.getBatchSequenceNum()) );
		LOG.exiting(CLASSNAME, "populatePK");
	}
	
	
	private void populateAllAttributes(PaymentBatchDetailsVO advancePaymentVO) {
		LOG.entering(CLASSNAME, "populateAllAttributes");
	double totBatamt =0;
	totBatamt= advancePaymentVO.getBatchAmount().getAmount();
	this.setSettlementCurrencyCode(advancePaymentVO.getBatchCurrency());
	this.setSettlementDate(advancePaymentVO.getBatchDate());
	this.setBatchStatus(advancePaymentVO.getBatchStatus());
	this.setProcessId(advancePaymentVO.getProcessID());
	this.setAcccountTxnId(null);
	this.setLastUpdateUser(advancePaymentVO.getLstUpdatedUser());
	this.setAppliedAmount(0);
	this.setFileType(null);
	this.setSource(advancePaymentVO.getSource());
	this.setTotalBatchAmount(totBatamt);
	this.setUnAppliedAmount(totBatamt);
	this.setLastUpdateUser(advancePaymentVO.getLstUpdatedUser());
	this.setLastUpdatedTime(advancePaymentVO.getLstUpdatedTime().toCalendar());
	this.setRemarks(advancePaymentVO.getRemarks());
	LOG.entering(CLASSNAME, "populateAllAttributes");
	}

	/**
	 *
	 * 	Method		:	MRAGPABatchSettlement.constructDAO
	 *	Added by 	:	A-5219 on 11-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	MRAReceivableManagementDAO
	 */
	public static MRAReceivableManagementDAO constructDAO() throws SystemException {
		try {
			return MRAReceivableManagementDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(
							MODULE));
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 *
	 * 	Method		:	MRAGPABatchSettlement.findBatchUploadDetails
	 *	Added by 	:	A-5219 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	Collection<MRABatchUploadedVO>
	 */
	public Collection<MRABatchUploadedVO> findBatchUploadDetails(PaymentBatchFilterVO filterVO)
			throws SystemException{
		Collection<MRABatchUploadedVO> vos = null;
		try{
			vos= constructDAO().findBatchUploadDetails(filterVO);
		}catch (SystemException e) {
			LOG.log(Log.SEVERE, e);
		}
		return vos;
	}

	/**
	 *
	 * 	Method		:	MRAGPABatchSettlement.validateFileName
	 *	Added by 	:	A-5219 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	int
	 */
	public int validateFileName(PaymentBatchFilterVO filterVO)
			throws SystemException{
		int fileCount = 0;
		try{
			fileCount = constructDAO().validateFileName(filterVO);
		}catch (SystemException e) {
			LOG.log(Log.SEVERE, e);
		}
		return fileCount;
	}
	
	
	public static MRAGPABatchSettlement find(MRAGPABatchSettlementPK pk)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(MRAGPABatchSettlement.class, pk);
	}

	@Column(name="RECCNT")
	public int getRecordCount() {
		return recordCount;
	}



	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}


	@Column(name="FILNAM")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


/**
 * @author A-8331
 * @param companyCode
 * @param paCode
 * @param batchID
 * @return
 * @throws FinderException
 * @throws SystemException
 */

 public static MRAGPABatchSettlement find(String companyCode, String paCode, String batchID) throws FinderException, SystemException {
		MRAGPABatchSettlement mraGPABatchSettlement= null;
		MRAGPABatchSettlementPK mraGPABatchSettlementPK = new MRAGPABatchSettlementPK();
		mraGPABatchSettlementPK.setCompanyCode(  companyCode);
		mraGPABatchSettlementPK.setPoaCode(paCode);
		mraGPABatchSettlementPK.setBatchId(batchID);
		mraGPABatchSettlementPK.setBatchSequenceNumber(findBatchSequenceNumber(batchID, companyCode));
		try {
		EntityManager em = PersistenceController.getEntityManager();
		mraGPABatchSettlement = em.find(MRAGPABatchSettlement.class, mraGPABatchSettlementPK);
		} catch (FinderException e) {
			
			LOG.log(Log.SEVERE, e);
			
			
		} 
		
		return mraGPABatchSettlement ;
		
		
		
	}
/**
 * @author A-8331	
 * @param batchID
 * @param companyCode
 * @return
 * @throws SystemException
 */
	private static long findBatchSequenceNumber(String batchID, String companyCode) throws SystemException {
		return constructDAO().findBatchSequenceNumber(batchID, companyCode);
		
	}

	
	
	
	
	
	
	
	
	
	
	
		
		
		
	



	public String loadMRASettlementBatchUploadDetails(FileUploadFilterVO filterVO)throws SystemException {
        LOG.entering(CLASSNAME, "loadMRASettlementBatchUploadDetails");
		String status = "";
        try
	    {
	        status = constructDAO().loadMRASettlementBatchUploadDetails(filterVO);
	      } catch (PersistenceException persistenceException) {
	    	  LOG.log(Log.SEVERE, persistenceException);
		}
		return status;
	}
/**
 * @author A-8331
 * @param advancePaymentVO
 * @throws FinderException
 * @throws SystemException 
 */
	public void updateBatchStatus(PaymentBatchDetailsVO advancePaymentVO) throws FinderException, SystemException {
		
		MRAGPABatchSettlement	mRAGPABatchSettlement= find(advancePaymentVO.getCompanyCode(),
				advancePaymentVO.getPaCode(), advancePaymentVO.getBatchID()
				);
		if(mRAGPABatchSettlement!=null)
		{
		mRAGPABatchSettlement.setBatchStatus(MRAConstantsVO.BATCH_STATUS_DELETE);
		advancePaymentVO.setAccTxnIdr(mRAGPABatchSettlement.getAcccountTxnId());
		advancePaymentVO.setAuditAmount(mRAGPABatchSettlement.getTotalBatchAmount());
		advancePaymentVO.setBatchCurrency(mRAGPABatchSettlement.getSettlementCurrencyCode());
		}
		
		
	} 
 
}

