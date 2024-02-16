/*
 * MailInConsignment.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
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
 * @author a-5991
 */
@Entity
@Table(name = "MALCSGDTL")
@Staleable	
public class MailInConsignment {
	
	private static final String MODULE = "mail.operations";	
	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");
	private MailInConsignmentPK mailInConsignmentPK;
	private int statedBags;
	private double statedWeight;
	private double declaredValue;
	private String sealNumber;
	/**
	 * @return the declaredValue
	 */
	@Column(name="DCLVAL")
	public double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the currencyCode
	 */
	@Column(name="CURCOD")
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	private String uldNumber;
	private String currencyCode;
	private String mailClass;
	private String mailbagJnrIdr;
	

	

	
	/**
	 * @return Returns the mailInConsignmentPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="consignmentNumber", column=@Column(name="CSGDOCNUM")),
		@AttributeOverride(name="paCode", column=@Column(name="POACOD")),
		@AttributeOverride(name="consignmentSequenceNumber", column=@Column(name="CSGSEQNUM")),
		@AttributeOverride(name="mailSequenceNumber", column=@Column(name="MALSEQNUM"))
	})
	public MailInConsignmentPK getMailInConsignmentPK() {
		return mailInConsignmentPK;
	}
	/**
	 * @param mailInConsignmentPK The mailInConsignmentPK to set.
	 */
	public void setMailInConsignmentPK(MailInConsignmentPK mailInConsignmentPK) {
		this.mailInConsignmentPK = mailInConsignmentPK;
	}
	

	/**
	 * @return Returns the statedBags.
	 */
	@Column(name="BAGCNT")
	public int getStatedBags() {
		return statedBags;
	}
	/**
	 * @param statedBags The statedBags to set.
	 */
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	@Column(name="ULDNUM")
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber =  uldNumber;
	}
	
	/**
	 * 
	 * @return mailbagJnrIdr
	 */
	@Column(name="MALJNRIDR")
	public String getMailbagJnrIdr() {
		return mailbagJnrIdr;
	}
	/**
	 * 
	 * @param mailbagJnrIdr
	 */
	public void setMailbagJnrIdr(String mailbagJnrIdr) {
		this.mailbagJnrIdr = mailbagJnrIdr;
	}
	/**
	 * Default Constructor
	 */
	public MailInConsignment(){
	}
	
	/**
	 * @param mailInConsignmentVO
	 * @throws SystemException
	 */
	public MailInConsignment(MailInConsignmentVO mailInConsignmentVO)
	throws SystemException {
		log.entering("MailInConsignment","MailInConsignment");
		populatePk(mailInConsignmentVO);
		populateAttributes(mailInConsignmentVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch(CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("MailInConsignment","MailInConsignment");
	}
	
	/**
	 * @param mailInConsignmentVO
	 */
	private void populatePk(MailInConsignmentVO
			mailInConsignmentVO){
		log.entering("MailInConsignment","populatePk");
		MailInConsignmentPK mailInConsignmentPk 
		= new MailInConsignmentPK();
		mailInConsignmentPk.setCompanyCode(   mailInConsignmentVO.getCompanyCode());
		mailInConsignmentPk.setConsignmentNumber(  
			mailInConsignmentVO.getConsignmentNumber());
		mailInConsignmentPk.setConsignmentSequenceNumber(   
			mailInConsignmentVO.getConsignmentSequenceNumber());
		mailInConsignmentPk.setPaCode(   mailInConsignmentVO.getPaCode());
		mailInConsignmentPk.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
		this.setMailInConsignmentPK(mailInConsignmentPk);
		log.exiting("MailInConsignment","populatePk");
		
	}
	
	/**
	 * @param mailInConsignmentVO
	 */
	private void populateAttributes(MailInConsignmentVO
			mailInConsignmentVO){
		log.entering("MailInConsignment","populateAttributes");
		
		this.setStatedBags(mailInConsignmentVO.getStatedBags());
		this.setUldNumber(mailInConsignmentVO.getUldNumber());
		this.setDeclaredValue(mailInConsignmentVO.getDeclaredValue());
		this.setCurrencyCode(mailInConsignmentVO.getCurrencyCode());
		this.setMailbagJnrIdr(mailInConsignmentVO.getMailbagJrnIdr());
		this.setSealNumber(mailInConsignmentVO.getSealNumber());
		log.exiting("MailInConsignment","populateAttributes");
	}
	
	/**
	 * @param mailInConsignmentVO
	 */
	public void update(MailInConsignmentVO
			mailInConsignmentVO){
		log.entering("MailInConsignment","populateAttributes");
		populateAttributes(mailInConsignmentVO);
		log.exiting("MailInConsignment","populateAttributes");
	}
	
	/**
	 *  This method removes entity
	 *  @throws SystemException
	 */
	public void remove()throws SystemException{
		log.entering("MailInConsignment","remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch(RemoveException removeException) {
			removeException.getErrorCode();
			throw new SystemException(removeException.getMessage(), removeException);
		}
		log.exiting("MailInConsignment","remove");
	}
	
	/**
	 * @param mailInConsignmentVO
	 * @return MailInConsignment
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailInConsignment find(MailInConsignmentVO
			mailInConsignmentVO)throws SystemException,FinderException{
		Log findLog = LogFactory.getLogger("MAIL_OPERATIONS");
		findLog.entering("MailInConsignment","find");
		MailInConsignment mailInConsignment = null;
		MailInConsignmentPK mailInConsignmentPk 
		= new MailInConsignmentPK();
		mailInConsignmentPk.setCompanyCode(   mailInConsignmentVO.getCompanyCode());
		mailInConsignmentPk.setConsignmentNumber(  
			mailInConsignmentVO.getConsignmentNumber());
		mailInConsignmentPk.setConsignmentSequenceNumber(   
			mailInConsignmentVO.getConsignmentSequenceNumber());
		mailInConsignmentPk.setPaCode(   mailInConsignmentVO.getPaCode());
		mailInConsignmentPk.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
		return PersistenceController.getEntityManager().find(MailInConsignment.class, mailInConsignmentPk);
		
	}
	
	/**
	 * 
	 * @return MailTrackingDefaultsDAO
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
	throws SystemException {
		MailTrackingDefaultsDAO mailTrackingDefaultsDAO = null;
		try{
			EntityManager em = PersistenceController.getEntityManager();
			mailTrackingDefaultsDAO = MailTrackingDefaultsDAO.class.cast(
					em.getQueryDAO(MODULE));
		}
		catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		return mailTrackingDefaultsDAO;
	}
	
	/**
	 * This method finds mail sequence number
	 * @param mailInConsignmentVO
	 * @return int
	 * @throws SystemException
	 */
	public static int findMailSequenceNumber(MailInConsignmentVO mailInConsignmentVO)
	throws 	SystemException {
		Log findLog = LogFactory.getLogger("mail.operations");
		findLog.entering("MailInConsignment","findMailSequenceNumber");
		try{
			return constructDAO().findMailSequenceNumber(mailInConsignmentVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	/**
	 * @author A-2037
	 * @param companyCode
	 * @param mailId
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	public static MailInConsignmentVO findConsignmentDetailsForMailbag(String 
			companyCode,String mailId,String airportCode) throws SystemException{
		Log findLog = LogFactory.getLogger("mail.operations");
		findLog.entering("MailInConsignment",
		"findConsignmentDetailsForMailbag");
		
		
		try{
			return constructDAO().findConsignmentDetailsForMailbag(companyCode,
					mailId,airportCode);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
			
		}
	}
	/**
	 * 	Getter for sealNumber 
	 *	Added by : A-5219 on 26-Nov-2020
	 * 	Used for :
	 */
	@Column(name="CONSELNUM")
	public String getSealNumber() {
		return sealNumber;
	}
	/**
	 *  @param sealNumber the sealNumber to set
	 * 	Setter for sealNumber 
	 *	Added by : A-5219 on 26-Nov-2020
	 * 	Used for :
	 */
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
}
