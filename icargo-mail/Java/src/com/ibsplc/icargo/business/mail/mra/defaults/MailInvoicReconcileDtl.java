/*
 * MailInvoicReconcileDtl.java Created on Sep 17, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicDupRecepVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicReconcileDtlVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVRCLDTL")
@Staleable
@Deprecated
public class MailInvoicReconcileDtl {
	private MailInvoicReconcileDtlPK mailInvoicReconcileDtlPK;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	private String sectorFlight;
	
	private Calendar scanDate;
	
	private  String invoicKey;
	
	private Calendar claimDate;
	
	private String claimCode;
	
	private String claimStatus;
	
	private double claimAmount;
	
	private String claimKey;
	
	private String poaCode;
	
	/**
	 * 
	 */
	public MailInvoicReconcileDtl(){
		
	}
	/**
	 * @param vo
	 * @throws SystemException
	 */
	public MailInvoicReconcileDtl(MailInvoicReconcileDtlVO vo)
	throws SystemException{
		
		MailInvoicReconcileDtlPK reconcilePK = new MailInvoicReconcileDtlPK();
		reconcilePK.setCompanyCode(vo.getCompanyCode());
		reconcilePK.setRecepticleIdentifier(vo.getRecepticleIdentifier());
		reconcilePK.setSectorIdentifier(vo.getSectorIdentifier());
		this.setMailInvoicReconcileDtlPK(reconcilePK);
		this.setSectorOrigin(vo.getSectorOrigin());
		this.setSectorDestination(vo.getSectorDestination());
		this.setScanDate(vo.getScanDate());
		this.setInvoicKey(vo.getInvoicKey());
		this.setClaimDate(vo.getClaimDate());
		this.setClaimCode(vo.getClaimCode());
		this.setClaimStatus(vo.getClaimStatus());
		this.setClaimAmount(vo.getClaimAmount());
		this.setClaimKey(vo.getClaimKey());
		this.setPoaCode(vo.getPoaCode());
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
		
	}

	/**
	 * @return Returns the claimAmount.
	 */
	@Column(name = "CLMAMT")
	public double getClaimAmount() {
		return claimAmount;
	}

	/**
	 * @param claimAmount The claimAmount to set.
	 */
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	/**
	 * @return Returns the claimCode.
	 */
	@Column(name = "CLMCOD")
	public String getClaimCode() {
		return claimCode;
	}

	/**
	 * @param claimCode The claimCode to set.
	 */
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}

	/**
	 * @return Returns the claimDate.
	 */
	@Column(name = "CLMDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getClaimDate() {
		return claimDate;
	}

	/**
	 * @param claimDate The claimDate to set.
	 */
	public void setClaimDate(Calendar claimDate) {
		this.claimDate = claimDate;
	}

	/**
	 * @return Returns the claimKey.
	 */
	@Column(name = "CLMKEY")
	public String getClaimKey() {
		return claimKey;
	}

	/**
	 * @param claimKey The claimKey to set.
	 */
	public void setClaimKey(String claimKey) {
		this.claimKey = claimKey;
	}

	/**
	 * @return Returns the claimStatus.
	 */
	@Column(name = "CLMSTA")
	public String getClaimStatus() {
		return claimStatus;
	}

	/**
	 * @param claimStatus The claimStatus to set.
	 */
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	/**
	 * @return Returns the invoicKey.
	 */
	@Column(name = "INVKEY")
	public String getInvoicKey() {
		return invoicKey;
	}

	/**
	 * @param invoicKey The invoicKey to set.
	 */
	public void setInvoicKey(String invoicKey) {
		this.invoicKey = invoicKey;
	}

	/**
	 * @return Returns the mailInvoicReconcileDtlPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="recepticleIdentifier", column=@Column(name="RCPIDR")),
		@AttributeOverride(name="sectorIdentifier", column=@Column(name="SECIDR"))}
	)
	public MailInvoicReconcileDtlPK getMailInvoicReconcileDtlPK() {
		return mailInvoicReconcileDtlPK;
	}

	/**
	 * @param mailInvoicReconcileDtlPK The mailInvoicReconcileDtlPK to set.
	 */
	public void setMailInvoicReconcileDtlPK(
			MailInvoicReconcileDtlPK mailInvoicReconcileDtlPK) {
		this.mailInvoicReconcileDtlPK = mailInvoicReconcileDtlPK;
	}

	/**
	 * @return Returns the poaCode.
	 */
	@Column(name = "POACOD")
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the scanDate.
	 */
	@Column(name = "SCNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getScanDate() {
		return scanDate;
	}

	/**
	 * @param scanDate The scanDate to set.
	 */
	public void setScanDate(Calendar scanDate) {
		this.scanDate = scanDate;
	}

	/**
	 * @return Returns the sectorDestination.
	 */
	@Column(name = "SECDST")
	public String getSectorDestination() {
		return sectorDestination;
	}

	/**
	 * @param sectorDestination The sectorDestination to set.
	 */
	public void setSectorDestination(String sectorDestination) {
		this.sectorDestination = sectorDestination;
	}

	/**
	 * @return Returns the sectorFlight.
	 */
	@Column(name = "SECFLT")
	public String getSectorFlight() {
		return sectorFlight;
	}

	/**
	 * @param sectorFlight The sectorFlight to set.
	 */
	public void setSectorFlight(String sectorFlight) {
		this.sectorFlight = sectorFlight;
	}

	/**
	 * @return Returns the sectorOrigin.
	 */
	@Column(name = "SECORG")
	public String getSectorOrigin() {
		return sectorOrigin;
	}

	/**
	 * @param sectorOrigin The sectorOrigin to set.
	 */
	public void setSectorOrigin(String sectorOrigin) {
		this.sectorOrigin = sectorOrigin;
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailInvoicReconcileDtl find(MailInvoicReconcileDtlVO vo)throws 
	SystemException,FinderException{
		MailInvoicReconcileDtlPK reconcilePK = new MailInvoicReconcileDtlPK();
		reconcilePK.setCompanyCode(vo.getCompanyCode());
		reconcilePK.setRecepticleIdentifier(vo.getRecepticleIdentifier());
		reconcilePK.setSectorIdentifier(vo.getSectorIdentifier());
		return PersistenceController.getEntityManager().find(
				MailInvoicReconcileDtl.class, reconcilePK);
	}
	/**
	 * @param vo
	 */
	public void update(MailInvoicReconcileDtlVO vo){
		this.setSectorOrigin(vo.getSectorOrigin());
		this.setSectorDestination(vo.getSectorDestination());
		this.setScanDate(vo.getScanDate());
		this.setInvoicKey(vo.getInvoicKey());
		this.setClaimDate(vo.getClaimDate());
		this.setClaimCode(vo.getClaimCode());
		this.setClaimStatus(vo.getClaimStatus());
		this.setClaimAmount(vo.getClaimAmount());
		this.setClaimKey(vo.getClaimKey());
		this.setPoaCode(vo.getPoaCode());
	}
	/**
	 * @param vo
	 */
	public void updateClaimStatus(MailInvoicReconcileDtlVO vo){
		this.setClaimStatus(vo.getClaimStatus());
	}
	/**
	 * @param dupVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<String> findReconcileSectorIdrs(MailInvoicDupRecepVO dupVO)
	throws SystemException{
		
		try{
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().
					getQueryDAO( "mail.mra.defaults")).findReconcileSectorIdrs(dupVO);
	    }catch (PersistenceException persistenceException) {
			  persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	
}