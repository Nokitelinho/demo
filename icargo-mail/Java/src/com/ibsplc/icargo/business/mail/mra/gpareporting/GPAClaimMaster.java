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
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
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
@Table(name = "MALMRAGPACLM")
public class GPAClaimMaster {

	private Log log = LogFactory
			.getLogger("MRA GPAREPORTING GPAClaimMaster");

	private GPAClaimMasterPK gpaClaimMasterPK;

	private Calendar fromDate;
	private Calendar toDate;
	private long totalclaimamt;
	private int totalmailcount;
	private String currencyCode;
	private String claimgenflag;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	private String resditSendFlag;
	private String claimGenFilname;

	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "gpaCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "claimReferenceNumber", column = @Column(name = "CLMREFNUM")) })
	public GPAClaimMasterPK getGPAClaimMasterPK() {
		return gpaClaimMasterPK;
	}

	public void setGPAClaimMasterPK(GPAClaimMasterPK gpaClaimMasterPK) {
		this.gpaClaimMasterPK = gpaClaimMasterPK;
	}

	@Column(name = "RPTPRDFRM")
	public Calendar getFromDate() {
		return fromDate;
	}

	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}

	@Column(name = "RPTPRDTOO")
	public Calendar getToDate() {
		return toDate;
	}

	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}

	@Column(name = "TOTCLMAMT")
	public long getTotalclaimamt() {
		return totalclaimamt;
	}

	public void setTotalclaimamt(long totalclaimamt) {
		this.totalclaimamt = totalclaimamt;
	}

	@Column(name = "TOTMALCNT")
	public int getTotalmailcount() {
		return totalmailcount;
	}

	public void setTotalmailcount(int totalmailcount) {
		this.totalmailcount = totalmailcount;
	}

	@Column(name = "CURCOD")
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "CLMSND")
	public String getClaimgenflag() {
		return claimgenflag;
	}

	public void setClaimgenflag(String claimgenflag) {
		this.claimgenflag = claimgenflag;
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

	public GPAClaimMaster() {

	}

	public GPAClaimMaster(ClaimDetailsVO claimDetailsVO) throws SystemException {
		log.entering("GPAClaimMaster", "GPAClaimMaster");

		populatePK(claimDetailsVO);
		populateAttributes(claimDetailsVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		log.exiting("GPAClaimMaster", "GPAClaimMaster");
	}

	private void populatePK(ClaimDetailsVO claimDetailsVO)
			throws SystemException {
		log.entering("GPAClaimMaster", "populatePK");
		this.setGPAClaimMasterPK(new GPAClaimMasterPK(claimDetailsVO
				.getCompanyCode(), claimDetailsVO.getGpaCode(), claimDetailsVO
				.getClaimRefNumber()));

		log.exiting("GPAClaimMaster", "populatePK");
	}

	public void update(ClaimDetailsVO claimDetailsVO) throws SystemException {
		log.entering("GPAClaimMaster", "update");
		populateAttributes(claimDetailsVO);
		log.exiting("GPAClaimMaster", "update");
	}

	private void populateAttributes(ClaimDetailsVO claimDetailsVO) {

		log.entering("GPAClaimMaster", "--populateAttributes---");
		this.setFromDate(claimDetailsVO.getFromDate().toCalendar());
		this.setToDate(claimDetailsVO.getToDate().toCalendar());
		//this.setClaimgenflag(claimDetailsVO.getClaimGenerateFlag());
		//this.setCurrencyCode(claimDetailsVO.getCurrency());
		//this.setTotalclaimamt(claimDetailsVO.getClaimAmount());
		//this.setTotalmailcount(claimDetailsVO.getNoOfMailbags());
		this.setLastUpdatedUser(claimDetailsVO.getLastUpdatedUser());
		//this.setLastUpdatedTime(claimDetailsVO.getLastUpdatedTime().toCalendar());
		this.setResditSendFlag(claimDetailsVO.getResditSendFlag());
		
		this.log.exiting("GPAClaimMaster", "populateAttributes");
	}

	public static GPAClaimMaster find(GPAClaimMasterPK gpaClaimMasterPK)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(
				GPAClaimMaster.class, gpaClaimMasterPK);
	}

	private static MRAGPAReportingDAO constructDAO() throws SystemException {
		try {
			return MRAGPAReportingDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO("mail.mra.gpareporting"));
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/*public static void saveClaimDetails(
			Collection<InvoicDetailsVO> invoicDetailsVOs)
			throws SystemException {
		MailGPAInvoicDetail mailGPAInvoicDetail = null;
		if (invoicDetailsVOs != null && !invoicDetailsVOs.isEmpty()) {
			for (InvoicDetailsVO invoicDetailsVO : invoicDetailsVOs) {
				try {
					mailGPAInvoicDetail = MailGPAInvoicDetail
							.find(constructGPAClaimMasterPK(invoicDetailsVO));
					mailGPAInvoicDetail.setClaimAmount(invoicDetailsVO
							.getClaimamount().getAmount());
				} catch (FinderException e) {
					e.getErrorCode();
				}
			}
		}

	}*/

	/*private static GPAClaimMasterPK constructGPAClaimMasterPK(
			InvoicDetailsVO invoicDetailsVO) throws SystemException {

		GPAClaimMasterPK gpaClaimMasterPK = new GPAClaimMasterPK();
		gpaClaimMasterPK.setCompanyCode(invoicDetailsVO.getCompanyCode());
		gpaClaimMasterPK.setSernum(invoicDetailsVO.getSerialNumber());
		gpaClaimMasterPK.setGpaCode(invoicDetailsVO.getPoaCode());
		return gpaClaimMasterPK;
	}*/

	public static Page<ClaimDetailsVO> listClaimDetails(
			InvoicFilterVO invoicFilterVO, int pageNumber)
			throws SystemException {
		try {
			return constructDAO().listClaimDetails(invoicFilterVO, pageNumber);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	public static Page<ClaimDetailsVO> listGenerateClaimDetails(
			InvoicFilterVO invoicFilterVO, int pageNumber)
			throws SystemException {
		try {
			return constructDAO().listGenerateClaimDetails(invoicFilterVO,
					pageNumber);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
/**
 * 	Method		:	GPAClaimMaster.saveClaimDetails
 *	Added by 	:	A-4809 on May 28, 2019
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
	public static String saveClaimDetails(InvoicFilterVO filterVO) throws SystemException{
		try{
			 return constructDAO().saveClaimDetails(filterVO);
		}catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	/**
	 * 	Method		:	GPAClaimMaster.findMailbagsForClaim
	 *	Added by 	:	A-4809 on Jun 3, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ClaimVO
	 */
	public static ClaimVO findMailbagsForClaim(InvoicFilterVO filterVO) throws SystemException{
		try{
			return constructDAO().findMailbagsForClaim(filterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
/**
 * 	Method		:	GPAClaimMaster.findGeneratedResditMessages
 *	Added by 	:	A-4809 on Jun 6, 2019
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Collection<ResditEventVO>
 */
	public static Collection<ResditEventVO> findGeneratedResditMessages(InvoicFilterVO filterVO) throws SystemException{
	try{
		return constructDAO().findGeneratedResditMessages(filterVO);
	} catch (PersistenceException e) {
		throw new SystemException(e.getErrorCode(), e);
	}
 }
/**
 * 
 * 	Method		:	GPAClaimMaster.isClaimGeneraetd
 *	Added by 	:	A-8061 on 20-Jun-2019
 * 	Used for 	:	ICRD-262451
 *	Parameters	:	@param invoicFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Boolean
 */
public static String findClaimMasterDetails(InvoicFilterVO invoicFilterVO) throws SystemException{
	try{
		return constructDAO().findClaimMasterDetails(invoicFilterVO);
	} catch (PersistenceException e) {
		throw new SystemException(e.getErrorCode(), e);
	}
}
/**
 * 
 * 	Method		:	GPAClaimMaster.findClaimReferenceNumber
 *	Added by 	:	A-8061 on 27-Jun-2019
 * 	Used for 	:
 *	Parameters	:	@param invoicFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	String
 */
public static String findClaimReferenceNumber(InvoicFilterVO invoicFilterVO) throws SystemException{
	try{
		return constructDAO().findClaimReferenceNumber(invoicFilterVO);
	} catch (PersistenceException e) {
		throw new SystemException(e.getErrorCode(), e);
	}
}

/**
 * 	Getter for resditSendFlag 
 *	Added by : A-8061 on 27-Jun-2019
 * 	Used for :
 */
@Column(name = "RDTSND")
public String getResditSendFlag() {
	return resditSendFlag;
}

/**
 *  @param resditSendFlag the resditSendFlag to set
 * 	Setter for resditSendFlag 
 *	Added by : A-8061 on 27-Jun-2019
 * 	Used for :
 */
public void setResditSendFlag(String resditSendFlag) {
	this.resditSendFlag = resditSendFlag;
}
@Column(name = "CLMFILNAM")
public String getClaimGenFilname() {
	return claimGenFilname;
}
public void setClaimGenFilname(String claimGenFilname) {
	this.claimGenFilname = claimGenFilname;
}
/**
 * 	Method		:	GPAClaimMaster.findMailbagsForClaim
 *	Added by 	:	A-8176 on Aug 7, 2020
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	ClaimVO
 */
public static Collection<ClaimVO> findMailbagsForClaimForInternational(InvoicFilterVO filterVO) throws SystemException{
	try{
		return constructDAO().findMailbagsForClaimForInternational(filterVO);
	} catch (PersistenceException e) {
		throw new SystemException(e.getErrorCode(), e);
	}
}
}
