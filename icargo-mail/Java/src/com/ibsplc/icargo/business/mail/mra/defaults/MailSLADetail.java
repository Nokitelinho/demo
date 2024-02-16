/*
 * MailSLADetail.java Created on Apr 2, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLADetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2524
 *
 */

@Entity
@Table(name = "MTKSLADTL")
@Staleable
@Deprecated
public class MailSLADetail {
	
	private MailSLADetailPK mailSLADetailPk;
	private String mailCategory;
	private int serviceTime;
	private int alertTime;
	private int chaserTime;
	private int chaserFrequency;
	private int maxNumberOfChasers;
	private double claimRate;
	

    /**
     * Default Constructor
     */
    public MailSLADetail() {        
    }
	
    
    /**
	 * 
	 * @return
	 */
	public static Log returnLogger() {
		return LogFactory.getLogger("MRA MAILSLADETAIL");
	}


	/**
	 * @return Returns the alertTime.
	 */
	@Column(name="ALRTIM")
	public int getAlertTime() {
		return alertTime;
	}


	/**
	 * @param alertTime The alertTime to set.
	 */
	public void setAlertTime(int alertTime) {
		this.alertTime = alertTime;
	}


	/**
	 * @return Returns the chaserFrequency.
	 */
	@Column(name="CHSFRQ")
	public int getChaserFrequency() {
		return chaserFrequency;
	}


	/**
	 * @param chaserFrequency The chaserFrequency to set.
	 */
	public void setChaserFrequency(int chaserFrequency) {
		this.chaserFrequency = chaserFrequency;
	}


	/**
	 * @return Returns the chaserTime.
	 */
	@Column(name="CHSTIM")
	public int getChaserTime() {
		return chaserTime;
	}


	/**
	 * @param chaserTime The chaserTime to set.
	 */
	public void setChaserTime(int chaserTime) {
		this.chaserTime = chaserTime;
	}


	/**
	 * @return Returns the claimRate.
	 */
	@Column(name="CLMRAT")
	public double getClaimRate() {
		return claimRate;
	}


	/**
	 * @param claimRate The claimRate to set.
	 */
	public void setClaimRate(double claimRate) {
		this.claimRate = claimRate;
	}


	/**
	 * @return Returns the mailCategory.
	 */
	@Column(name="MALCTGCOD")
	public String getMailCategory() {
		return mailCategory;
	}


	/**
	 * @param mailCategory The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}


	/**
	 * @return Returns the mailSLADetailPk.
	 */
	 @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="slaId", column=@Column(name="SLAIDR")),
		@AttributeOverride(name="serialNumber", column=@Column(name="SERNUM"))}
	)
	public MailSLADetailPK getMailSLADetailPk() {
		return mailSLADetailPk;
	}


	/**
	 * @param mailSLADetailPk The mailSLADetailPk to set.
	 */
	public void setMailSLADetailPk(MailSLADetailPK mailSLADetailPk) {
		this.mailSLADetailPk = mailSLADetailPk;
	}


	/**
	 * @return Returns the maxNumberOfChasers.
	 */
	@Column(name="MAXNUMCHS")
	public int getMaxNumberOfChasers() {
		return maxNumberOfChasers;
	}


	/**
	 * @param maxNumberOfChasers The maxNumberOfChasers to set.
	 */
	public void setMaxNumberOfChasers(int maxNumberOfChasers) {
		this.maxNumberOfChasers = maxNumberOfChasers;
	}


	/**
	 * @return Returns the serviceTime.
	 */
	@Column(name="SRVTIM")
	public int getServiceTime() {
		return serviceTime;
	}


	/**
	 * @param serviceTime The serviceTime to set.
	 */
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}
	
	
	
	/**
	 * 
	 * @param mailSLADetailsVo
	 * @throws SystemException
	 */
	public MailSLADetail(MailSLADetailsVO mailSLADetailsVo)throws SystemException{
		returnLogger().entering("MailSLADetail","MailSLADetail");
		populatePK(mailSLADetailsVo);
		populateAttributes(mailSLADetailsVo);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		
		returnLogger().exiting("MailSLADetail","MailSLADetail");
	}
	
	/**
	 * 
	 * @param mailSLADetailsVo
	 */
    private void populatePK(MailSLADetailsVO mailSLADetailsVo) {
    	returnLogger().entering("MailSLADetail","populatePK");
    	MailSLADetailPK mailSLADetailPK=new MailSLADetailPK(
    			mailSLADetailsVo.getCompanyCode(),
    			mailSLADetailsVo.getSlaId(),
    			mailSLADetailsVo.getSerialNumber());
		this.setMailSLADetailPk(mailSLADetailPK);
		returnLogger().exiting("MailSLADetail","populatePK");
	}


    /**
     * 
     * @param mailSLADetailsVO
     */
	private void populateAttributes(MailSLADetailsVO mailSLADetailsVO) {
		returnLogger().entering("MailSLADetail","populateAttributes");
		this.setMailCategory(mailSLADetailsVO.getMailCategory());
		this.setAlertTime(mailSLADetailsVO.getAlertTime());
		this.setChaserTime(mailSLADetailsVO.getChaserTime());
		this.setServiceTime(mailSLADetailsVO.getServiceTime());
		this.setChaserFrequency(mailSLADetailsVO.getChaserFrequency());
		this.setMaxNumberOfChasers(mailSLADetailsVO.getMaxNumberOfChasers());
		this.setClaimRate(mailSLADetailsVO.getClaimRate());
		returnLogger().exiting("MailSLADetail","populateAttributes");
		
	}
	
	
	/**
	 * 
	 * @param companyCode
	 * @param slaId
	 * @param serialNumber
	 * @return
	 * @throws SystemException
	 */
	
	public static MailSLADetail find(String companyCode,String slaId, int serialNumber)throws SystemException{
		returnLogger().entering("MailSLADetail","find");
		MailSLADetail mailSLADetail=null;
		MailSLADetailPK mailSLADetailPK=new MailSLADetailPK(companyCode,slaId,serialNumber);
		try {
			mailSLADetail=PersistenceController.getEntityManager().find(
					MailSLADetail.class,mailSLADetailPK);
		} catch (FinderException e) {		
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting("MailSLADetail","find");
		return mailSLADetail;
		
	}
	
	
	/**
	 * 
	 * @param mailSLADetailsVO
	 * @throws SystemException
	 */
	public void update(MailSLADetailsVO mailSLADetailsVO)
	throws SystemException{
		returnLogger().entering("MailSLADetail","update");
		populateAttributes(mailSLADetailsVO);		
		returnLogger().exiting("MailSLADetail","update");		
	}

	
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		returnLogger().entering("MailSLADetail","remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} catch (OptimisticConcurrencyException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting("MailSLADetail","remove");
	}
	
	
	
	
}
