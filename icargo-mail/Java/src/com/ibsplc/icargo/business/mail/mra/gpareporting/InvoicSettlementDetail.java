/*
 * MailGPAInvoicDetail.java Created on Nov 20, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpareporting;

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

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailGPAInvoicDetailVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-8464
 *
 */
@Entity
@Table(name = "MALMRAGPAINCSTLDTL")
public class InvoicSettlementDetail {

	private  Log log = LogFactory.getLogger("MRA GPAREPORTING InvoicSettlementDetail");

	private InvoicSettlementDetailPK invoicSettlementDetailPK;

    private String mailIdr;
    private String invoicRefNum;
    private Calendar receivedDate;
    private String mailClass;
    private double weight;
    private double appliedRate;
    private double invoicAmount;
    //private double dueAmount;
    private double claimAmount;
    private String processStatus;
    private String invoicPaymentStatus;
    private String claimStatus;
    private String remark;
    private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	private long versionNumber;
	private double updClaimAmount;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")) })
	public InvoicSettlementDetailPK getInvoicSettlementDetailPK() {
		return invoicSettlementDetailPK;
	}
	public void setInvoicSettlementDetailPK(InvoicSettlementDetailPK invoicSettlementDetailPK) {
		this.invoicSettlementDetailPK = invoicSettlementDetailPK;
	}



	@Column(name = "MALIDR")
	public String getMailIdr() {
		return mailIdr;
	}
	public void setMailIdr(String mailIdr) {
		this.mailIdr = mailIdr;
	}

	@Column(name = "INVREFNUM")
	public String getInvoicRefNum() {
		return invoicRefNum;
	}
	public void setInvoicRefNum(String invoicRefNum) {
		this.invoicRefNum = invoicRefNum;
	}

	@Column(name = "RCVDAT")
	public Calendar getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Calendar receivedDate) {
		this.receivedDate = receivedDate;
	}

	@Column(name = "MALCLS")
	public String getMailClass() {
		return mailClass;
	}
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	@Column(name = "GRSWGT")
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Column(name = "APLRAT")
	public double getAppliedRate() {
		return appliedRate;
	}
	public void setAppliedRate(double appliedRate) {
		this.appliedRate = appliedRate;
	}

	@Column(name = "INVAMT")
	public double getInvoicAmount() {
		return invoicAmount;
	}
	public void setInvoicAmount(double invoicAmount) {
		this.invoicAmount = invoicAmount;
	}
	/*
	@Column(name = "DUEAMT")
	public double getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}
	*/
	@Column(name = "CLMAMT")
	public double getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	@Column(name = "PROSTA")
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	@Column(name = "INVPAYSTA")
	public String getInvoicPaymentStatus() {
		return invoicPaymentStatus;
	}
	public void setInvoicPaymentStatus(String invoicPaymentStatus) {
		this.invoicPaymentStatus = invoicPaymentStatus;
	}

	@Column(name = "CLMSTA")
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	@Column(name = "RMK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	 @Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}


	public InvoicSettlementDetail(){

	}

	public InvoicSettlementDetail(MailGPAInvoicDetailVO invoicDetailsVO)
			throws SystemException {
		log.entering("InvoicSettlementDetail", "InvoicSettlementDetail");

		populatePK(invoicDetailsVO);
		populateAttributes(invoicDetailsVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		log.exiting("InvoicSettlementDetail", "InvoicSettlementDetail");
	}


	private void populatePK(MailGPAInvoicDetailVO invoicDetailsVO)
			throws SystemException {
		log.entering("InvoicSettlementDetail", "populatePK");
		this.setInvoicSettlementDetailPK(new InvoicSettlementDetailPK(
				invoicDetailsVO.getCompanyCode(),
				invoicDetailsVO.getSerialNumber(),
				invoicDetailsVO.getMailSequenceNumber()));
		log.exiting("InvoicSettlementDetail", "populatePK");
	}

	public void update(MailGPAInvoicDetailVO invoicDetailsVO)
			throws SystemException {
		log.entering("InvoicSettlementDetail", "update");
		populateAttributes(invoicDetailsVO);
		log.exiting("InvoicSettlementDetail", "update");
	}

	private void populateAttributes(MailGPAInvoicDetailVO invoicDetailsVO) {

		log.entering("InvoicSettlementDetail", "--populateAttributes---");
		this.setAppliedRate(invoicDetailsVO.getAppliedRate());
		this.setClaimAmount(invoicDetailsVO.getClaimAmount());
		this.setClaimStatus(invoicDetailsVO.getClaimStatus());
		//this.setDueAmount(invoicDetailsVO.getDueAmount());
		this.setInvoicAmount(invoicDetailsVO.getInvoicAmount());
		this.setInvoicPaymentStatus(invoicDetailsVO.getInvoicPaymentStatus());
		this.setInvoicRefNum(invoicDetailsVO.getInvoicRefNum());
		this.setLastUpdatedTime(invoicDetailsVO.getLastUpdatedTime());
		this.setLastUpdatedUser(invoicDetailsVO.getLastUpdatedUser());
		this.setMailClass(invoicDetailsVO.getMailClass());
		this.setMailIdr(invoicDetailsVO.getMailIdr());
		this.setProcessStatus(invoicDetailsVO.getProcessStatus());
		this.setReceivedDate(invoicDetailsVO.getReceivedDate());
		this.setWeight(invoicDetailsVO.getWeight());
		this.setVersionNumber(invoicDetailsVO.getVersionNumber()+1);
		log.exiting("InvoicSettlementDetail", "populateAttributes");
	}
	/**
	 * 	Getter for versionNumber
	 *	Added by : A-5219 on 13-Feb-2020
	 * 	Used for :
	 */
	@Column(name = "VERNUM")
	public long getVersionNumber() {
		return versionNumber;
	}
	/**
	 *  @param versionNumber the versionNumber to set
	 * 	Setter for versionNumber
	 *	Added by : A-5219 on 13-Feb-2020
	 * 	Used for :
	 */
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}
	/**
	 *
	 * 	Method		:	InvoicSettlementDetail.find
	 *	Added by 	:	A-5219 on 13-Feb-2020
	 * 	Used for 	:
	 *	Parameters	:	@param invoicSettlementDetailPK
	 *	Parameters	:	@return
	 *	Parameters	:	@throws FinderException
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	InvoicSettlementDetail
	 */
	public static InvoicSettlementDetail find(InvoicSettlementDetailPK invoicSettlementDetailPK)
		      throws FinderException, SystemException {
		      return PersistenceController.getEntityManager().find(InvoicSettlementDetail.class, invoicSettlementDetailPK);
		  }
	/**
	 * 	Getter for updClaimAmount
	 *	Added by : A-5219 on 17-Feb-2020
	 * 	Used for :
	 */
	@Column(name = "UPDCLMAMT")
	public double getUpdClaimAmount() {
		return updClaimAmount;
	}
	/**
	 *  @param updClaimAmount the updClaimAmount to set
	 * 	Setter for updClaimAmount
	 *	Added by : A-5219 on 17-Feb-2020
	 * 	Used for :
	 */
	public void setUpdClaimAmount(double updClaimAmount) {
		this.updClaimAmount = updClaimAmount;
	}


	 }
