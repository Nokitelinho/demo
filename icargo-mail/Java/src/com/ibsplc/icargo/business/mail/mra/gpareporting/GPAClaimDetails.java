/**
 * GPAClaimMaster.java Created on March 15, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpareporting;

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

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8527
 * 
 */
@Entity
@Table(name = "MALMRAGPACLMDTL")
public class GPAClaimDetails {

	private Log log = LogFactory
			.getLogger("MRA GPAREPORTING GPAClaimDetails");

	private GPAClaimDetailsPK gpaClaimDetailsPK;


	private String mailbagId;
	private Calendar recivedDate;
	private String claimType;
	private double claimAmount;
	private String currencyCode;
	private Calendar claimgsubDate;
	private String claimRefNumber;
	private String invoicRefNumber;
	private int versionNumber;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	private String claimStatus;

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "poaCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "sernum", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "seqNumber", column = @Column(name = "SEQNUM")),
			@AttributeOverride(name = "mailSeqNumber", column = @Column(name = "MALSEQNUM"))})
	public GPAClaimDetailsPK gpaClaimDetailsPK() {
		return gpaClaimDetailsPK;
	}

	public void setGPAClaimDetailsPK(GPAClaimDetailsPK gpaClaimDetailsPK) {
		this.gpaClaimDetailsPK = gpaClaimDetailsPK;
	}

	@Column(name = "MALIDR")
	public String getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	@Column(name = "RCVDAT")
	public Calendar getRecivedDate() {
		return recivedDate;
	}

	public void setRecivedDate(Calendar recivedDate) {
		this.recivedDate = recivedDate;
	}
	@Column(name = "CLMTYP")
	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	@Column(name = "CLMSTA")
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	@Column(name = "CLMAMT")
	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}
	@Column(name = "CLMSUBDAT")
	public Calendar getClaimgsubDate() {
		return claimgsubDate;
	}
	public void setClaimgsubDate(Calendar claimgsubDate) {
		this.claimgsubDate = claimgsubDate;
	}
	@Column(name = "CLMREFNUM")
	public String getClaimRefNumber() {
		return claimRefNumber;
	}
	public void setClaimRefNumber(String claimRefNumber) {
		this.claimRefNumber = claimRefNumber;
	}
	@Column(name = "INVREFNUM")
	public String getInvoicRefNumber() {
		return invoicRefNumber;
	}
	public void setInvoicRefNumber(String invoicRefNumber) {
		this.invoicRefNumber = invoicRefNumber;
	}
	@Column(name = "VERNUM")
	public int getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	@Column(name = "CURCOD")
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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

	public GPAClaimDetails() {

	}

	public GPAClaimDetails(ClaimDetailsVO claimDetailsVO) throws SystemException {
		log.entering("GPAClaimDetails", "GPAClaimDetails");

		populatePK(claimDetailsVO);
		populateAttributes(claimDetailsVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		log.exiting("GPAClaimDetails", "GPAClaimDetails");
	}

	private void populatePK(ClaimDetailsVO claimDetailsVO)
			throws SystemException {
		log.entering("GPAClaimDetails", "populatePK");
		this.setGPAClaimDetailsPK(new GPAClaimDetailsPK(claimDetailsVO
				.getCmpcod(), claimDetailsVO.getGpaCode(), claimDetailsVO
				.getSernum(),claimDetailsVO.getSeqNumber(),claimDetailsVO.getMailSeqNumber()));

		log.exiting("GPAClaimDetails", "populatePK");
	}

	public void update(ClaimDetailsVO claimDetailsVO) throws SystemException {
		log.entering("GPAClaimDetails", "update");
		populateAttributes(claimDetailsVO);
		log.exiting("GPAClaimDetails", "update");
	}

	private void populateAttributes(ClaimDetailsVO claimDetailsVO) {

		log.entering("GPAClaimDetails", "--populateAttributes---");
		this.setMailbagId(claimDetailsVO.getMailBagId());
		this.setRecivedDate(claimDetailsVO.getReceivedDate().toCalendar());
		this.setClaimType(claimDetailsVO.getClaimType());
		this.setCurrencyCode(claimDetailsVO.getCurrency());
		this.setClaimAmount(claimDetailsVO.getClaimAmount());
		this.setClaimStatus(claimDetailsVO.getClaimStatus());
		this.setClaimgsubDate(claimDetailsVO.getClaimSubDate().toCalendar());
		this.setClaimRefNumber(claimDetailsVO.getClaimRefNumber());
		this.setInvoicRefNumber(claimDetailsVO.getInvoicRefNumber());
		this.setVersionNumber(claimDetailsVO.getVersnNumber());
		this.setLastUpdatedUser(claimDetailsVO.getLastUpdatedUser());
		this.setLastUpdatedTime(claimDetailsVO.getLastUpdatedTime().toCalendar());

		this.log.exiting("GPAClaimDetails", "populateAttributes");
	}

	public static GPAClaimDetails find(GPAClaimDetailsPK gpaClaimDetailsPK)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(
				GPAClaimDetails.class, gpaClaimDetailsPK);
	}

	private static MRAGPAReportingDAO constructDAO() throws SystemException {
		try {
			return MRAGPAReportingDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO("mail.mra.gpareporting"));
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	public static Page<ClaimDetailsVO> listClaimDetails(
			InvoicFilterVO invoicFilterVO, int pageNumber)
			throws SystemException {
		try {
			return constructDAO().listClaimDetails(invoicFilterVO, pageNumber);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	
	public static Collection<ClaimDetailsVO> listClaimDetails(
			InvoicFilterVO invoicFilterVO)
			throws SystemException {
		try {
			return constructDAO().listClaimDetails(invoicFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

}
