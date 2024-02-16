/*
 * MailDOTRate.java Created on Aug 03, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKDOTRAT")
@Staleable
@Deprecated
public class MailDOTRate {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");
	
	private static final String MODULE_NAME = "mail.mra.defaults";
	
	private MailDOTRatePK mailDOTRatePK;
	
	private String rateDescription;
	
	private String regionCode; 
	
	private double lineHaulRate;
	
	private double terminalHandlingRate;
	
	private double dotRate;
	
	/**
	 * 
	 */
	public MailDOTRate(){
		
	}
	/**
	 * @param mailDOTRateVO
	 * @throws SystemException
	 */
	public MailDOTRate(MailDOTRateVO mailDOTRateVO)
	throws SystemException{
		log.entering("MRA Defaults","contructor");
		
		MailDOTRatePK ratePK = new MailDOTRatePK();
		ratePK.setCompanyCode(mailDOTRateVO.getCompanyCode());
		ratePK.setSectorOrigin(mailDOTRateVO.getOriginCode());
		ratePK.setSectorDestination(mailDOTRateVO.getDestinationCode());
		ratePK.setRateCode(mailDOTRateVO.getRateCode());
		ratePK.setCircleMiles(mailDOTRateVO.getCircleMiles());
		
		this.setMailDOTRatePK(ratePK);
		populateAttributes(mailDOTRateVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
		log.exiting("MRA Defaults","contructor");
		
	}
	/**
	 * @return Returns the dotRate.
	 */
	@Column(name="DOTRAT")
	public double getDotRate() {
		return dotRate;
	}
	/**
	 * @param dotRate The dotRate to set.
	 */
	public void setDotRate(double dotRate) {
		this.dotRate = dotRate;
	}
	/**
	 * @return Returns the lineHaulRate.
	 */
	@Column(name="LHLRAT")
	public double getLineHaulRate() {
		return lineHaulRate;
	}
	/**
	 * @param lineHaulRate The lineHaulRate to set.
	 */
	public void setLineHaulRate(double lineHaulRate) {
		this.lineHaulRate = lineHaulRate;
	}
	/**
	 * @return Returns the mailDOTRatePK.
	 */
	 @EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="sectorOrigin", column=@Column(name="SEGORGCOD")),
			@AttributeOverride(name="sectorDestination", column=@Column(name="SEGDSTCOD")),
			@AttributeOverride(name="circleMiles", column=@Column(name="GCM")),
			@AttributeOverride(name="rateCode", column=@Column(name="RATCOD"))}
		)
	public MailDOTRatePK getMailDOTRatePK() {
		return mailDOTRatePK;
	}
	/**
	 * @param mailDOTRatePK The mailDOTRatePK to set.
	 */
	public void setMailDOTRatePK(MailDOTRatePK mailDOTRatePK) {
		this.mailDOTRatePK = mailDOTRatePK;
	}
	/**
	 * @return Returns the rateDescription.
	 */
	@Column(name="RATDSC")
	public String getRateDescription() {
		return rateDescription;
	}
	/**
	 * @param rateDescription The rateDescription to set.
	 */
	public void setRateDescription(String rateDescription) {
		this.rateDescription = rateDescription;
	}
	/**
	 * @return Returns the regionCode.
	 */
	@Column(name="REGCOD")
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode The regionCode to set.
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * @return Returns the terminalHandlingRate.
	 */
	@Column(name="THGRAT")
	public double getTerminalHandlingRate() {
		return terminalHandlingRate;
	}
	/**
	 * @param terminalHandlingRate The terminalHandlingRate to set.
	 */
	public void setTerminalHandlingRate(double terminalHandlingRate) {
		this.terminalHandlingRate = terminalHandlingRate;
	}
	
	/**
	 * @param mailDOTRateVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static  MailDOTRate find(MailDOTRateVO mailDOTRateVO)
	throws SystemException{
		MailDOTRate mailDOTRate=null;
		MailDOTRatePK dotPK = new MailDOTRatePK();
		dotPK.setCompanyCode(mailDOTRateVO.getCompanyCode());
		dotPK.setCircleMiles(mailDOTRateVO.getCircleMiles());
		dotPK.setRateCode(mailDOTRateVO.getRateCode());
		dotPK.setSectorDestination(mailDOTRateVO.getDestinationCode());
		dotPK.setSectorOrigin(mailDOTRateVO.getOriginCode());
		try{
			mailDOTRate= PersistenceController.getEntityManager().find(
				MailDOTRate.class, dotPK);
		} catch (FinderException e) {		
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		return mailDOTRate;
	}
	
	/**
	 * @param mailDOTRateVO
	 * @throws SystemException
	 */
	public void update(MailDOTRateVO mailDOTRateVO)
	throws SystemException{
		log.entering("MRA Defaults","update");
		populateAttributes(mailDOTRateVO);
		log.exiting("MRA Defaults","update");
	}
	/**
	 * @throws SystemException
	 */
	public void remove() 
	throws SystemException {
		log.entering("MRA Defaults", "remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		}
		log.exiting("MRA Defaults", "remove");
	}
	/**
	 * @param mailDOTRateVO
	 */
	private void populateAttributes(MailDOTRateVO mailDOTRateVO){
		log.entering("MRA Defaults","populateAttributes");
		this.setDotRate(mailDOTRateVO.getDotRate());
		this.setLineHaulRate(mailDOTRateVO.getLineHaulRate());
		this.setRateDescription(mailDOTRateVO.getRateDescription());
		this.setRegionCode(mailDOTRateVO.getRegionCode());
		this.setTerminalHandlingRate(mailDOTRateVO.getTerminalHandlingRate());
		log.exiting("MRA Defaults","populateAttributes");
	}
/**
 * @param filterVO
 * @return
 * @throws SystemException
 */
public static Collection<MailDOTRateVO> findDOTRateDetails(MailDOTRateFilterVO filterVO)
throws SystemException{
	
	Log log = LogFactory.getLogger("MRA DEFAULTS");
	log.entering("MailDOTRate", "findDOTRateDetails");
	MRADefaultsDAO mraDefaultsDao = null;
	try {
		EntityManager em = PersistenceController.getEntityManager();
		mraDefaultsDao = MRADefaultsDAO.class.cast(em
				.getQueryDAO(MODULE_NAME));
		log.exiting("MailDOTRate", "findDOTRateDetails");
		return mraDefaultsDao.findDOTRateDetails(filterVO);
	} catch (PersistenceException persistenceException) {
		throw new SystemException(persistenceException.getErrorCode());
	}
	
}
}
