/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.MRAGPABatchSettlement.java
 *
 *	Created by	:	A-5219
 *	Created on	:	10-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.receivablemanagement;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.MRAGPABatchSettlement.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	10-Nov-2021	:	Draft
 */

@Entity
@Staleable
@Table(name = "MALMRAGPABTHSTLDTL")
public class MRAGPABatchSettlementDetail {
	
	private static final  Log LOG = LogFactory.getLogger("MRA receivablemanagement MRAGPABatchSettlementDetail");
	
	private static final String CLASS_NAME="MRAGPABatchSettlementDetail";
	
	private MRAGPABatchSettlementDetailPK mRAGPABatchSettlementDetailPK;
	
	private Calendar settlementDate;
	
	private String mailId;
	
	private Calendar mailDate;
	
	private String consignmentDocumentNum;
	
	private String settlementCurrencyCode;
	
	private String accountNumber;
	
	private String invoiceNumber;
	
	private double totalBatchAmount;
	
	private double appliedAmount;
	
	private double unAppliedAmount;
	
	private Calendar payDate;
	
	private String rsn;
	
	private String remarks;
	
	/**
	 * 	Getter for mRAGPABatchSettlementDetailPK 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "batchId", column = @Column(name = "BTHSTLIDR")),
		@AttributeOverride(name = "batchSequenceNumber", column = @Column(name = "BTHSTLSEQNUM")),
		@AttributeOverride(name = "gpaCode", column = @Column(name = "POACOD")),
		@AttributeOverride(name = "mailSeqNum", column = @Column(name = "MALSEQNUM"))})
	public MRAGPABatchSettlementDetailPK getmRAGPABatchSettlementDetailPK() {
		return mRAGPABatchSettlementDetailPK;
	}

	/**
	 *  @param mRAGPABatchSettlementDetailPK the mRAGPABatchSettlementDetailPK to set
	 * 	Setter for mRAGPABatchSettlementDetailPK 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setmRAGPABatchSettlementDetailPK(MRAGPABatchSettlementDetailPK mRAGPABatchSettlementDetailPK) {
		this.mRAGPABatchSettlementDetailPK = mRAGPABatchSettlementDetailPK;
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
	 * 	Getter for mailId 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="MALIDR")
	public String getMailId() {
		return mailId;
	}

	/**
	 *  @param mailId the mailId to set
	 * 	Setter for mailId 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	/**
	 * 	Getter for mailDate 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="MALDAT")
	public Calendar getMailDate() {
		return mailDate;
	}

	/**
	 *  @param mailDate the mailDate to set
	 * 	Setter for mailDate 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setMailDate(Calendar mailDate) {
		this.mailDate = mailDate;
	}

	/**
	 * 	Getter for consignmentDocumentNum 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="CSGDOCNUM")
	public String getConsignmentDocumentNum() {
		return consignmentDocumentNum;
	}

	/**
	 *  @param consignmentDocumentNum the consignmentDocumentNum to set
	 * 	Setter for consignmentDocumentNum 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setConsignmentDocumentNum(String consignmentDocumentNum) {
		this.consignmentDocumentNum = consignmentDocumentNum;
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
	 * 	Getter for accountNumber 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="ACCNUM")
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 *  @param accountNumber the accountNumber to set
	 * 	Setter for accountNumber 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * 	Getter for invoiceNumber 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="INVNUM")
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 *  @param invoiceNumber the invoiceNumber to set
	 * 	Setter for invoiceNumber 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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
	 * 	Getter for payDate 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="PAYDAT")
	public Calendar getPayDate() {
		return payDate;
	}

	/**
	 *  @param payDate the payDate to set
	 * 	Setter for payDate 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setPayDate(Calendar payDate) {
		this.payDate = payDate;
	}

	/**
	 * 	Getter for rsn 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	@Column(name="RSN")
	public String getRsn() {
		return rsn;
	}

	/**
	 *  @param rsn the rsn to set
	 * 	Setter for rsn 
	 *	Added by : A-5219 on 10-Nov-2021
	 * 	Used for :
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
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
	 * 
	 *	Constructor	: 	@param batchSettlementDetailsVO
	 *	Constructor	: 	@throws SystemException
	 *	Created by	:	A-5219
	 *	Created on	:	15-Nov-2021
	 */
	public MRAGPABatchSettlementDetail(PaymentBatchSettlementDetailsVO batchSettlementDetailsVO)throws SystemException{
		populatePK(batchSettlementDetailsVO);
		populateAttributes(batchSettlementDetailsVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		LOG.log(Log.SEVERE, e);
	    	}
		}
	
	
	/**
	 * 
	 * 	Method		:	MRAGPABatchSettlementDetail.populatePK
	 *	Added by 	:	A-5219 on 15-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param batchSettlementDetailsVO 
	 *	Return type	: 	void
	 */
	private void populatePK(PaymentBatchSettlementDetailsVO batchSettlementDetailsVO){
		LOG.entering(CLASS_NAME, "populatePK");
		mRAGPABatchSettlementDetailPK= new MRAGPABatchSettlementDetailPK();
		mRAGPABatchSettlementDetailPK.setCompanyCode(batchSettlementDetailsVO.getCompanyCode());
		mRAGPABatchSettlementDetailPK.setGpaCode(batchSettlementDetailsVO.getPaCode());
		mRAGPABatchSettlementDetailPK.setBatchSequenceNumber(batchSettlementDetailsVO.getBatchSequenceNum());
		mRAGPABatchSettlementDetailPK.setMailSeqNum(batchSettlementDetailsVO.getMailSeqNum());
		mRAGPABatchSettlementDetailPK.setBatchId(batchSettlementDetailsVO.getBatchID());
		this.setmRAGPABatchSettlementDetailPK(mRAGPABatchSettlementDetailPK);


	}
	
	
	/**
	 * 
	 * 	Method		:	MRAGPABatchSettlementDetail.populateAttributes
	 *	Added by 	:	A-5219 on 15-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param batchSettlementDetailsVO 
	 *	Return type	: 	void
	 */
	private void populateAttributes(PaymentBatchSettlementDetailsVO batchSettlementDetailsVO){
		LOG.entering(CLASS_NAME, "populateAttributes");
		this.setMailId(batchSettlementDetailsVO.getMailbagId());
		this.setMailDate(batchSettlementDetailsVO.getMailbagDate());
		this.setConsignmentDocumentNum(batchSettlementDetailsVO.getConsignmentDocNum());
		this.setPayDate(batchSettlementDetailsVO.getPayDate());
		this.setSettlementCurrencyCode(batchSettlementDetailsVO.getCurrencyCode());
		this.setSettlementDate(batchSettlementDetailsVO.getSettlementDate());
		this.setTotalBatchAmount(batchSettlementDetailsVO.getPaidAmount().getAmount());
		this.setUnAppliedAmount(batchSettlementDetailsVO.getPaidAmount().getAmount());
		this.setAccountNumber(batchSettlementDetailsVO.getAccountNumber());
	}
	
	/**
	 *
	 * 	Method		:	MRAGPABatchSettlementDetail.find
	 *	Added by 	:	A-5219 on 10-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param pk
	 *	Parameters	:	@return
	 *	Parameters	:	@throws FinderException
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	MRAGPABatchSettlementDetail
	 */
	public static MRAGPABatchSettlementDetail find(MRAGPABatchSettlementDetailPK pk)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(MRAGPABatchSettlementDetail.class, pk);
	}


	public MRAGPABatchSettlementDetail(){

	}
	

}
