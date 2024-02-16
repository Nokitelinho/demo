/*
 * MailServiceLevel.java Created on Apr 09 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.vo.MailServiceLevelVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-6986
 *
 */
@Table(name="MALSRVLVLMAP")
@Entity
@Staleable
public class MailServiceLevel {

	private static final String MODULE_NAME = "mail.operations";
	
	private static final Log log = LogFactory.getLogger("MAIL_OPERATIONS"); 
	
	private String	mailServiceLevel;
	
	private Calendar lastUpdatedTime;
	
	private String	lastUpdatedUser;
	
	private MailServiceLevelPK mailServiceLevelPK;
	/**
	 * @return the mailServiceLevel
	 */
	@Column(name = "MALSRVLVL")
	public String getMailServiceLevel() {
		return mailServiceLevel;
	}

	/**
	 * @param mailServiceLevel the mailServiceLevel to set
	 */
	public void setMailServiceLevel(String mailServiceLevel) {
		this.mailServiceLevel = mailServiceLevel;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	
	@Column(name = "LSTUPDTIM")
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
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
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
	 * @return the mailServiceLevelPK
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "poaCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "mailCategory", column = @Column(name = "MALCTG")),
			@AttributeOverride(name = "mailClass", column = @Column(name = "MALCLS")),
			@AttributeOverride(name = "mailSubClass", column = @Column(name = "MALSUBCLS"))})
	public MailServiceLevelPK getMailServiceLevelPK() {
		return mailServiceLevelPK;
	}

	/**
	 * @param mailServiceLevelPK the mailServiceLevelPK to set
	 */
	
	public void setMailServiceLevelPK(MailServiceLevelPK mailServiceLevelPK) {
		this.mailServiceLevelPK = mailServiceLevelPK;
	}

	public MailServiceLevel() {
		
	}

	/**
	 * @param mailServiceLevelVO
	 * @throws SystemException 
	 */
	public MailServiceLevel(MailServiceLevelVO mailServiceLevelVO) throws SystemException, CreateException {
		try{
			populatePK(mailServiceLevelVO);
			populateAttributes(mailServiceLevelVO);
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			   throw new SystemException(createException.getErrorCode());
		}
	}
	
	private void populatePK(MailServiceLevelVO mailServiceLevelVO){
		MailServiceLevelPK mailServiceLevelPK = new MailServiceLevelPK();
		mailServiceLevelPK.setCompanyCode(mailServiceLevelVO.getCompanyCode());
		mailServiceLevelPK.setPoaCode(mailServiceLevelVO.getPoaCode());
		mailServiceLevelPK.setMailCategory(mailServiceLevelVO.getMailCategory());
		mailServiceLevelPK.setMailClass(mailServiceLevelVO.getMailClass());
		mailServiceLevelPK.setMailSubClass(mailServiceLevelVO.getMailSubClass());
		
		this.mailServiceLevelPK = mailServiceLevelPK;
		
	}
	
	private void populateAttributes(MailServiceLevelVO mailServiceLevelVO){
		
		this.setMailServiceLevel(mailServiceLevelVO.getMailServiceLevel());
		this.setLastUpdatedTime(mailServiceLevelVO.getLastUpdatedTime());
		this.setLastUpdatedUser(mailServiceLevelVO.getLastUpdatedUser());
		
	}
	
	public static MailServiceLevel find(MailServiceLevelVO mailServiceLevelVO)throws SystemException,FinderException{
		log.entering("MailServiceLevel", " find "); 
		
		MailServiceLevel mailServiceLevel = null;
		MailServiceLevelPK mailServiceLevelPK = new MailServiceLevelPK();
		mailServiceLevelPK.setCompanyCode(mailServiceLevelVO.getCompanyCode());
		mailServiceLevelPK.setMailCategory(mailServiceLevelVO.getMailCategory());
		mailServiceLevelPK.setMailClass(mailServiceLevelVO.getMailClass());
		mailServiceLevelPK.setMailSubClass(mailServiceLevelVO.getMailSubClass());
		mailServiceLevelPK.setPoaCode(mailServiceLevelVO.getPoaCode());
		
		mailServiceLevel = PersistenceController.getEntityManager()
								.find(MailServiceLevel.class,mailServiceLevelPK);
		
		return mailServiceLevel;
		
	}
	
	public void remove()throws RemoveException, SystemException{
		log.entering("MailServiceLevel", " remove "); 
		try
	    {
	      PersistenceController.getEntityManager().remove(this);
	    } catch (RemoveException removeException) {
	      throw new SystemException(removeException.getErrorCode());
	    }
		log.exiting("MailServiceLevel", " remove ");
	
	}
}
