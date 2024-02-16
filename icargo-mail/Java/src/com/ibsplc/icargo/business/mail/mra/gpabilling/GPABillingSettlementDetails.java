/**
 * GPABillingSettlementDetails.java Created on Mar 30, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
@Entity
@Table(name = "MALMRAGPASTLDTL")
@Staleable
public class GPABillingSettlementDetails {
	private GPABillingSettlementDetailsPK gpaBillingSettlementDetailsPK;

	
	private String bankName;
	private String branchName;
	private String isDeleted;
	private String remarks;
	private String accTxnIdr;
	private String accStatus;
//	private Set<GPABillingSettlementInvoiceDetail> gpaBillingInvoiceDetails;
	private double outStandingAmount;
	//Added by A-8176 for ICRD-214302
	private String settlementMode;
	private String settlementModeNumber;
	private Calendar paymentDate;
	private double settlementAmount;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	//default constructor
	public GPABillingSettlementDetails(){
		
	}
	/**
	 * 
	 * @param settlementDetailsVO
	 * @throws SystemException
	 */
	public GPABillingSettlementDetails(SettlementDetailsVO settlementDetailsVO) throws SystemException {
		populatePK(settlementDetailsVO);
		populateAttributes(settlementDetailsVO);
		try {	

			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {			
			throw new SystemException(createException.getErrorCode());
		}
		settlementDetailsVO.setSerialNumber(this.gpaBillingSettlementDetailsPK.getSerialNumber());
	}
	
	/**
	 * 
	 * @param settlementDetailsVO
	 */
	private void populateAttributes(SettlementDetailsVO settlementDetailsVO) {
		this.bankName= settlementDetailsVO.getChequeBank();
		this.branchName= settlementDetailsVO.getChequeBranch();
		if(settlementDetailsVO.getOutStandingChqAmt()!= null){
			this.outStandingAmount = settlementDetailsVO.getOutStandingChqAmt().getRoundedAmount();
		}
		//Added by A-7794 as part of ICRD-194277
		if(settlementDetailsVO.getChequeDate() != null){
			this.paymentDate = settlementDetailsVO.getChequeDate();
		}
		if(settlementDetailsVO.getChequeAmount() != null){
			this.settlementAmount = settlementDetailsVO.getChequeAmount().getAmount();
		}
		this.settlementModeNumber = settlementDetailsVO.getChequeNumber();
		this.isDeleted= settlementDetailsVO.getIsDeleted();
		this.remarks= settlementDetailsVO.getRemarks();
		this.accStatus = settlementDetailsVO.getIsAccounted();
	}
	/**
	 * 
	 * @param settlementDetailsVO
	 */
	private void populatePK(SettlementDetailsVO settlementDetailsVO) {
		GPABillingSettlementDetailsPK gpaBillingSettlementDetailsPK = new GPABillingSettlementDetailsPK();
		gpaBillingSettlementDetailsPK.setCompanyCode(settlementDetailsVO.getCompanyCode());
		gpaBillingSettlementDetailsPK.setGpaCode(settlementDetailsVO.getGpaCode());
		gpaBillingSettlementDetailsPK.setSettlementReferenceNumber(settlementDetailsVO.getSettlementId());	
		gpaBillingSettlementDetailsPK.setSettlementSequenceNumber(settlementDetailsVO.getSettlementSequenceNumber());
		//Added by A-7794 as part of ICRD-194277
		gpaBillingSettlementDetailsPK.setSerialNumber(settlementDetailsVO.getSerialNumber());
		this.gpaBillingSettlementDetailsPK= gpaBillingSettlementDetailsPK;

	}
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "gpaCode", column = @Column(name = "GPACOD")),
		@AttributeOverride(name = "settlementReferenceNumber", column = @Column(name = "STLREFNUM")),
		@AttributeOverride(name = "settlementSequenceNumber", column = @Column(name = "SEQNUM")),
		@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))})
		public GPABillingSettlementDetailsPK getGpaBillingSettlementDetailsPK() {
		return gpaBillingSettlementDetailsPK;
	}
	/**
	 * @param gpaBillingSettlementDetailsPK the gpaBillingSettlementDetailsPK to set
	 */
	public void setGpaBillingSettlementDetailsPK(
			GPABillingSettlementDetailsPK gpaBillingSettlementDetailsPK) {
		this.gpaBillingSettlementDetailsPK = gpaBillingSettlementDetailsPK;
	}
	
	//Added by A-7794 as part of MRA revamp
	/*@OneToMany
	@JoinColumns({
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
		@JoinColumn(name = "GPACOD", referencedColumnName = "GPACOD", insertable = false, updatable = false),
		@JoinColumn(name = "STLREFNUM", referencedColumnName = "STLREFNUM", insertable = false, updatable = false),
		@JoinColumn(name = "SEQNUM", referencedColumnName = "SEQNUM", insertable = false, updatable = false),
		@JoinColumn(name = "SERNUM", referencedColumnName = "SERNUM", insertable = false, updatable = false)
	})
	public Set<GPABillingSettlementInvoiceDetail> getGpaBillingInvoiceDetails() {
		return gpaBillingInvoiceDetails;
	}
	public void setGpaBillingInvoiceDetails(
			Set<GPABillingSettlementInvoiceDetail> gpaBillingInvoiceDetails) {
		this.gpaBillingInvoiceDetails = gpaBillingInvoiceDetails;
	}
	*/
	@Column(name="BNKNAM")
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Column(name="BRNNAM")
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	
	@Column(name="OUTSTLAMT")
	public double getOutStandingAmount() {
		return outStandingAmount;
	}
	/**
	 * @param outStandingAmount the outStandingAmount to set
	 */
	public void setOutStandingAmount(double outStandingAmount) {
		this.outStandingAmount = outStandingAmount;
	}	
	
	@Column(name="CHQDELFLG")
	public String getIsDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	@Column(name="RMK")
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the accTxnIdr
	 */
	@Column(name="ACCTXNIDR")
	public String getAccTxnIdr() {
		return accTxnIdr;
	}
	/**
	 * @param accTxnIdr the accTxnIdr to set
	 */
	public void setAccTxnIdr(String accTxnIdr) {
		this.accTxnIdr = accTxnIdr;
	}
	@Column(name="ACCSTA")
	public String getAccStatus() {
		return accStatus;
	}
	/**
	 * @param accStatus the accStatus to set
	 */
	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}
	
	@Column(name="STLMOD")
	public String getSettlementMode() {
		return settlementMode;
	}
	/**
	 * @param settlementMode the settlementMode to set
	 */
	public void setSettlementMode(String settlementMode) {
		this.settlementMode = settlementMode;
	}
	@Column(name="STLMODNUM")
	public String getSettlementModeNumber() {
		return settlementModeNumber;
	}
	/**
	 * @param settlementModeNumber the settlementModeNumber to set
	 */
	public void setSettlementModeNumber(String settlementModeNumber) {
		this.settlementModeNumber = settlementModeNumber;
	}
	@Column(name="PAYDAT")
	public Calendar getPaymentDate() {
		return paymentDate;
	}
	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Calendar paymentDate) {
		this.paymentDate = paymentDate;
	}
	@Column(name="STLAMT")
	public double getSettlementAmount() {
		return settlementAmount;
	}
	/**
	 * @param settlementAmount the settlementAmount to set
	 */
	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	@Column(name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	@Column(name="LSTUPDTIM")
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
	 * 
	 * @param companyCode
	 * @param gpaCode
	 * @param settlementId
	 * @param serialNumber
	 * @param settlementSequenceNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static GPABillingSettlementDetails find(String companyCode,
			String gpaCode, String settlementId, int serialNumber,
			int settlementSequenceNumber) throws SystemException,
			FinderException {
		GPABillingSettlementDetailsPK gpaBillingSettlementDetailsPK = new GPABillingSettlementDetailsPK();
		gpaBillingSettlementDetailsPK.setCompanyCode(  companyCode);
		gpaBillingSettlementDetailsPK.setGpaCode(gpaCode);
		gpaBillingSettlementDetailsPK.setSettlementReferenceNumber(settlementId);
		gpaBillingSettlementDetailsPK.setSerialNumber(serialNumber);
		gpaBillingSettlementDetailsPK.setSettlementSequenceNumber(settlementSequenceNumber);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(GPABillingSettlementDetails.class, gpaBillingSettlementDetailsPK);
	}
	/**
	 * 
	 * @throws SystemException
	 * @throws RemoveException
	 */
	public void remove() throws SystemException, RemoveException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw ex;
		}


	}
	/**
	 * 
	 * @param settlementDetailsVO
	 */
	public void update(SettlementDetailsVO settlementDetailsVO) {
		populateAttributes(settlementDetailsVO);

	}
	
	
}
