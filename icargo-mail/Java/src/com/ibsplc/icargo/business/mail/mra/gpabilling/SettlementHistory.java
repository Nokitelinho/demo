/*
 * SettlementHistory.java created on Mar 26, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
@Entity
@Table(name = "MTKGPASTLHIS")
@Staleable
@Deprecated
public class SettlementHistory {
	private  Log log = LogFactory.getLogger("MRA GPAREPORTING");
	private static final String MODULE_NAME = "mail.mra.gpabilling";
	
	private SettlementHistoryPK settlementHistoryPK;
	private Calendar settledDate;
	private double settledAmount;
	private String settlementRemarks;
	
	
	/**
	 * @return Returns the settledAmount.
	 */
	@Column(name="STLAMT")
	public double getSettledAmount() {
		return settledAmount;
	}


	/**
	 * @param settledAmount The settledAmount to set.
	 */
	public void setSettledAmount(double settledAmount) {
		this.settledAmount = settledAmount;
	}


	/**
	 * @return Returns the settledDate.
	 */
	@Column(name="STLDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getSettledDate() {
		return settledDate;
	}


	/**
	 * @param settledDate The settledDate to set.
	 */
	public void setSettledDate(Calendar settledDate) {
		this.settledDate = settledDate;
	}


	/**
	 * @return Returns the settlementHistoryPK.
	 */
	 @EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="invoiceNumber", column=@Column(name="INVNUM")),
			@AttributeOverride(name="gpaCode", column=@Column(name="GPACOD")),
			@AttributeOverride(name="serialNumber", column=@Column(name="SERNUM"))}
		)
	public SettlementHistoryPK getSettlementHistoryPK() {
		return settlementHistoryPK;
	}


	/**
	 * @param settlementHistoryPK The settlementHistoryPK to set.
	 */
	public void setSettlementHistoryPK(SettlementHistoryPK settlementHistoryPK) {
		this.settlementHistoryPK = settlementHistoryPK;
	}


	/**
	 * @return Returns the settlementRemarks.
	 */
	@Column(name="STLRMK")
	public String getSettlementRemarks() {
		return settlementRemarks;
	}


	/**
	 * @param settlementRemarks The settlementRemarks to set.
	 */
	public void setSettlementRemarks(String settlementRemarks) {
		this.settlementRemarks = settlementRemarks;
	}


	/**
	 * default constructor
	 *
	 */
	public SettlementHistory(){
		
	}
	/**
	 * @author A-2280
	 * @param invoiceSettlementHistoryVO
	 * @throws SystemException
	 */
	public SettlementHistory(InvoiceSettlementHistoryVO invoiceSettlementHistoryVO)throws SystemException{
		log.entering("SettlementHistory","SettlementHistory");
		populatePK(invoiceSettlementHistoryVO);
		populateAttributes(invoiceSettlementHistoryVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			
			createException.getErrorCode();
			throw new SystemException(createException.getMessage());
		} 
		log.exiting("SettlementHistory","SettlementHistory");
		
	}

	/**
	 * @author A-2280
	 * @param invoiceSettlementHistoryVO
	 */

	private void populateAttributes(InvoiceSettlementHistoryVO invoiceSettlementHistoryVO) {
		log.entering("SettlementHistory","populateAttributes");
		if(invoiceSettlementHistoryVO.getAmountInSettlementCurrency()!=null) {
			this.setSettledAmount(invoiceSettlementHistoryVO.getAmountInSettlementCurrency().getRoundedAmount());
		}
		this.setSettledDate(invoiceSettlementHistoryVO.getSettlementDate());
		//this.setSettlementRemarks(invoiceSettlementHistoryVO.get)
		log.exiting("SettlementHistory","populateAttributes");
		
	}


	/**
	 * @author A-2280
	 * @param invoiceSettlementHistoryVO
	 */
	private void populatePK(InvoiceSettlementHistoryVO invoiceSettlementHistoryVO) {
		log.entering("SettlementHistory","populatePK");
		SettlementHistoryPK settlementHisPk=new SettlementHistoryPK();
		settlementHisPk.setCompanyCode( invoiceSettlementHistoryVO.getCompanyCode());
		settlementHisPk.setGpaCode( invoiceSettlementHistoryVO.getGpaCode());
		settlementHisPk.setInvoiceNumber( invoiceSettlementHistoryVO.getInvoiceNumber());
		this.setSettlementHistoryPK(settlementHisPk);
		log.exiting("SettlementHistory","populatePK");
	}
	
	/**
	 * @author A-2280
	 * @param companyCode
	 * @param gpaCode
	 * @param invoiceNumber
	 * @param serialNumber
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static SettlementHistory find(String companyCode,String gpaCode,String invoiceNumber,int serialNumber) 
	            throws FinderException, SystemException{
		SettlementHistory settlementHistMatch=null;
		SettlementHistoryPK settlementHistPK=new SettlementHistoryPK();
		settlementHistPK.setCompanyCode( companyCode);
		settlementHistPK.setGpaCode( gpaCode);
		settlementHistPK.setInvoiceNumber( invoiceNumber);
		settlementHistPK.setSerialNumber( serialNumber);
		return PersistenceController.getEntityManager().find(SettlementHistory.class, settlementHistPK);
	}
	/**
	 * @author A-2280
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {			
			removeException.getErrorCode();
			throw new SystemException(removeException.getMessage());
		} 
	}
	
	/**
	 * @author A-2280
	 * @param invoiceSettlementHistoryVO
	 * @throws SystemException
	 */
	public void update(InvoiceSettlementHistoryVO invoiceSettlementHistoryVO)throws SystemException{
		log.entering("SettlementHistory","update");
		populateAttributes(invoiceSettlementHistoryVO);
		log.exiting("SettlementHistory","update");
		
	}
	
	/**
	 * method for calling up the DAO for the submodule
	 * @author A-2280
	 * @return queryDAO
	 * @throws SystemException
	 */
	private static MRAGPABillingDAO constructDAO()
	throws SystemException {
		MRAGPABillingDAO queryDAO =null;
		try {
			queryDAO = (MRAGPABillingDAO)PersistenceController
			.getEntityManager()
			.getQueryDAO(MODULE_NAME);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(),e);
		}
		
		return queryDAO;
	}
	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<InvoiceSettlementHistoryVO> findSettlementHistory(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO)throws SystemException{
		
		
		try {
			return constructDAO().findSettlementHistory(invoiceSettlementFilterVO);
		} catch (PersistenceException persistenceException) {			
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	
}
