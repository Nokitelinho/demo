/**
 * GPABillingSettlementInvoiceDetail.java Created on Mar 30, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.util.Calendar;
import java.util.Collection;



import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;

import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-4823
 *
 */
@Entity
@Table(name = "MALMRAGPASTLINVDTL")
@Staleable
public class GPABillingSettlementInvoiceDetail {
	 private static final String MODULE_NAME = "mail.mra.gpabilling";
	private GPABillingSettlementInvoiceDetailPK gpaBillingSettelmentInvoiceDetailsPK;
	private double settledAmount;
	private String remarks;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	private String settlementLevel;//modified by a-7871
	
	
	//default constructor

	public GPABillingSettlementInvoiceDetail(){
	}


	/**
	 * 
	 * @param invoiceSettlementVO
	 * @throws SystemException 
	 */
	public GPABillingSettlementInvoiceDetail(
			InvoiceSettlementVO invoiceSettlementVO) throws SystemException {
		populatePK(invoiceSettlementVO);
		populateAttributes(invoiceSettlementVO);
		try {	
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {			
			throw new SystemException(createException.getErrorCode());
		}
	}
	/**
	 * 
	 * @param invoiceSettlementVO
	 */
	private void populateAttributes(InvoiceSettlementVO invoiceSettlementVO) {
		if(invoiceSettlementVO.getCurrentSettlingAmount()!=null){
			this.settledAmount=invoiceSettlementVO.getCurrentSettlingAmount().getAmount();
		}
		this.remarks = invoiceSettlementVO.getRemarks();
		//Added by A-7794 as part of ICRD-194277
		this.lastUpdatedUser = invoiceSettlementVO.getLastUpdatedUser();
		if(invoiceSettlementVO.getLastUpdatedTime() != null){
		this.lastUpdatedTime = invoiceSettlementVO.getLastUpdatedTime().toCalendar();
		}
		//this.isDeleted = invoiceSettlementVO.isDeleted();
	}
	/**
	 * 
	 * @param invoiceSettlementVO2
	 */
	private void populatePK(
			InvoiceSettlementVO invoiceSettlementVO2) {
		GPABillingSettlementInvoiceDetailPK gpaBillingSettelmentInvoiceDetailsPK = new GPABillingSettlementInvoiceDetailPK();		
		gpaBillingSettelmentInvoiceDetailsPK.setCompanyCode(invoiceSettlementVO2.getCompanyCode());
		gpaBillingSettelmentInvoiceDetailsPK.setGpaCode(invoiceSettlementVO2.getGpaCode());
		gpaBillingSettelmentInvoiceDetailsPK.setInvoiceNumber(invoiceSettlementVO2.getInvoiceNumber());
		gpaBillingSettelmentInvoiceDetailsPK.setSettlementReferenceNumber(invoiceSettlementVO2.getSettlementId());		
		gpaBillingSettelmentInvoiceDetailsPK.setSettlementSequenceNumber(invoiceSettlementVO2.getSettlementSequenceNumber());
		gpaBillingSettelmentInvoiceDetailsPK.setSettlementSerialNumber(invoiceSettlementVO2.getSerialNumber());
		gpaBillingSettelmentInvoiceDetailsPK.setMailSeqNum(invoiceSettlementVO2.getMailsequenceNum());
		this.gpaBillingSettelmentInvoiceDetailsPK = gpaBillingSettelmentInvoiceDetailsPK;
	}
	@Column (name = "STLAMT")
	public double getSettledAmount() {
		return settledAmount;
	}
	/**
	 * @param settledAmount the settledAmount to set
	 */
	public void setSettledAmount(double settledAmount) {
		this.settledAmount = settledAmount;
	}
	@Column (name = "RMK")
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
	 * @return the lastUpdatedUser
	 */
	@Column (name = "LSTUPDUSR")
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
	 * @return the lastUpdatedTime
	 */
	@Column (name = "LSTUPDTIM")
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
	 * @return the settlementLevel
	 */
	@Column (name = "STLLVL")
	public String getSettlementLevel() {
		return settlementLevel;
	}


	/**
	 * @param settlementLevel the settlementLevel to set
	 */
	public void setSettlementLevel(String settlementLevel) {
		this.settlementLevel = settlementLevel;
	}
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "gpaCode", column = @Column(name = "GPACOD")),
		@AttributeOverride(name = "settlementReferenceNumber", column = @Column(name = "STLREFNUM")),
		@AttributeOverride(name = "settlementSequenceNumber", column = @Column(name = "SEQNUM")),
		@AttributeOverride(name = "invoiceNumber", column = @Column(name = "INVNUM")),
		@AttributeOverride(name = "settlementSerialNumber", column = @Column(name = "SERNUM")),
		@AttributeOverride(name = "mailSeqNum", column = @Column(name = "MALSEQNUM"))})

		public GPABillingSettlementInvoiceDetailPK getGpaBillingSettelmentInvoiceDetailsPK() {
		return gpaBillingSettelmentInvoiceDetailsPK;
	}
	/**
	 * @param gpaBillingSettelmentInvoiceDetailsPK the gpaBillingSettelmentInvoiceDetailsPK to set
	 */
	public void setGpaBillingSettelmentInvoiceDetailsPK(
			GPABillingSettlementInvoiceDetailPK gpaBillingSettelmentInvoiceDetailsPK) {
		this.gpaBillingSettelmentInvoiceDetailsPK = gpaBillingSettelmentInvoiceDetailsPK;
	}




	/**
	 * 
	 * @param companyCode
	 * @param gpaCode
	 * @param settlementId
	 * @param settlementSequenceNumber
	 * @param invoiceNumber
	 * @param serialNumber 
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static GPABillingSettlementInvoiceDetail find(String companyCode, String gpaCode,
			String settlementId, int settlementSequenceNumber,String invoiceNumber, int serialNumber,long mailSeqnum) throws SystemException, FinderException {

		GPABillingSettlementInvoiceDetailPK gpaBillingSettelmentInvoiceDetailsPK = new GPABillingSettlementInvoiceDetailPK();

		gpaBillingSettelmentInvoiceDetailsPK.setCompanyCode(companyCode);
		gpaBillingSettelmentInvoiceDetailsPK.setGpaCode(gpaCode);
		gpaBillingSettelmentInvoiceDetailsPK.setSettlementReferenceNumber(settlementId);
		gpaBillingSettelmentInvoiceDetailsPK.setSettlementSequenceNumber(settlementSequenceNumber);
		gpaBillingSettelmentInvoiceDetailsPK.setInvoiceNumber(invoiceNumber);
		gpaBillingSettelmentInvoiceDetailsPK.setSettlementSerialNumber(serialNumber);
		gpaBillingSettelmentInvoiceDetailsPK.setMailSeqNum(mailSeqnum);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(GPABillingSettlementInvoiceDetail.class, gpaBillingSettelmentInvoiceDetailsPK);
	}
	/**
	 * 
	 * @param invoiceSettlementVO
	 */
	public void update(InvoiceSettlementVO invoiceSettlementVO) {
		populateAttributes(invoiceSettlementVO);		

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
	 * 	Method		:	GPABillingSettlementInvoiceDetail.findSettlementInvoiceDetails
	 *	Added by 	:	A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	GPASettlementVO
	 * @throws SystemException 
	 */
	public static Collection<GPASettlementVO> findSettlementInvoiceDetails(
			InvoiceSettlementFilterVO filterVO) throws SystemException {
		try{
			return MRAGPABillingDAO.class.cast(
					PersistenceController.getEntityManager().
					getQueryDAO(MODULE_NAME)).findSettlementInvoiceDetails(filterVO);
	    }catch (PersistenceException persistenceException) {
			  persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}


	/**
	 * 	Method		:	GPABillingSettlementInvoiceDetail.findSettledMailbagDetails
	 *	Added by 	:	A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Page<InvoiceSettlementVO>
	 * @throws SystemException 
	 */
	public static Page<InvoiceSettlementVO> findSettledMailbagDetails(
			InvoiceSettlementFilterVO filterVO) throws SystemException {
	
			try{
				return MRAGPABillingDAO.class.cast(
						PersistenceController.getEntityManager().
						getQueryDAO(MODULE_NAME)).findSettledMailbagDetails(filterVO);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
	}


	/**
	 * 	Method		:	GPABillingSettlementInvoiceDetail.findUnsettledMailbagDetails
	 *	Added by 	:	A-7531 on 03-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Page<InvoiceSettlementVO>
	 * @throws SystemException 
	 */
	public static Page<InvoiceSettlementVO> findUnsettledMailbagDetails(
			InvoiceSettlementFilterVO filterVO) throws SystemException {
		try{
			return MRAGPABillingDAO.class.cast(
					PersistenceController.getEntityManager().
					getQueryDAO(MODULE_NAME)).findUnsettledMailbagDetails(filterVO);
	    }catch (PersistenceException persistenceException) {
			  persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}


	/**
	 * 	Method		:	GPABillingSettlementInvoiceDetail.findMailbagSettlementHistory
	 *	Added by 	:	A-7531 on 11-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceFiletrVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<GPASettlementVO>
	 */
	public static Collection<InvoiceSettlementHistoryVO> findMailbagSettlementHistory(
			InvoiceSettlementFilterVO invoiceFiletrVO) throws SystemException{
			try{
				return MRAGPABillingDAO.class.cast(
						PersistenceController.getEntityManager().
						getQueryDAO(MODULE_NAME)).findMailbagSettlementHistory(invoiceFiletrVO);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
	}


	/**
	 * 	Method		:	GPABillingSettlementInvoiceDetail.findUnsettledInvoiceDetails
	 *	Added by 	:	A-7531 on 26-Jun-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<GPASettlementVO>
	 */
	public static Collection<GPASettlementVO> findUnsettledInvoiceDetails(
			InvoiceSettlementFilterVO filterVO)throws SystemException  {
		try{
			return MRAGPABillingDAO.class.cast(
					PersistenceController.getEntityManager().
					getQueryDAO(MODULE_NAME)).findUnsettledInvoiceDetails(filterVO);
	    }catch (PersistenceException persistenceException) {
			  persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	



}
