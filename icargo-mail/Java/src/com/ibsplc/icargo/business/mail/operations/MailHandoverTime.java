/*
 * MailHandoverTime.java Created on Jul 02, 2018
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
import javax.persistence.Version;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.operations.vo.MailHandoverFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHandoverVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
*
* @author A-6986
* 
*/
@Entity
@Table(name = "MALHNDTIM")
@Staleable
public class MailHandoverTime {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String PRODUCT_NAME = "mail.operations";
	private static final String CLASS_NAME = "MailHandoverTime";
	
	private String handoverTime;
	private String airport;
	private String exchangeOffice;
	private String mailClass;
	private String mailSubClass;
	private String paCode;
	private MailHandoverTimePK mailHandoverTimePK;
	
	/**
	 * @return the handoverTime
	 */
	@Column(name = "HNDTIM")
	public String getHandoverTime() {
		return handoverTime;
	}
	/**
	 * @param handoverTime
	 *            the handoverTime to set
	 */
	public void setHandoverTime(String handoverTime) {
		this.handoverTime = handoverTime;
	}
	
	//Added by A-7929 as part of IASCB-35577
	private Calendar lastUpdateTime;
	private String lastUpdateUser;

    @Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
		public Calendar getLastUpdateTime() {
			return lastUpdateTime;
		}
		public void setLastUpdateTime(Calendar lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
		}

	@Column(name = "LSTUPDUSR")
		public String getLastUpdateUser() {
			return lastUpdateUser;
		}
		public void setLastUpdateUser(String lastUpdateUser) {
			this.lastUpdateUser = lastUpdateUser;
		}
	/**
	 * @return the airport
	 */
	@Column(name = "ARPCOD")
	public String getAirport() {
		return airport;
	}
	/**
	 * @param airport
	 *            the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}
	/**
	 * @return the exchangeOffice
	 */
	@Column(name = "EXGOFC")
	public String getExchangeOffice() {
		return exchangeOffice;
	}
	/**
	 * @param exchangeOffice
	 *            the exchangeOffice to set
	 */
	public void setExchangeOffice(String exchangeOffice) {
		this.exchangeOffice = exchangeOffice;
	}
	/**
	 * @return the mailClass
	 */
	@Column(name = "MALCLS")
	public String getMailClass() {
		return mailClass;
	}
	/**
	 * @param mailClass
	 *            the mailClass to set
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * @return the mailSubClass
	 */
	@Column(name = "MALSUBCLS")
	public String getMailSubClass() {
		return mailSubClass;
	}
	/**
	 * @param mailSubClass
	 *            the mailSubClass to set
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
		/**
	 * @return the paCode
	 */
	@Column(name = "GPACOD")
	public String getPaCode() {
		return paCode;
	}
	/**
	 * @param paCode the paCode to set
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	/**
	 * @return the mailHandoverTimePK
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")) })
	public MailHandoverTimePK getMailHandoverTimePK() {
		return mailHandoverTimePK;
	}
	/**
	 * @param mailHandoverTimePK
	 *            the mailHandoverTimePK to set
	 */
	public void setMailHandoverTimePK(MailHandoverTimePK mailHandoverTimePK) {
		this.mailHandoverTimePK = mailHandoverTimePK;
	}

	public MailHandoverTime() {
		
	}
	
	public MailHandoverTime(MailHandoverVO mailHandoverVO) throws SystemException {
		try{
			populatePK(mailHandoverVO);
			populateAttributes(mailHandoverVO);
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			   throw new SystemException(createException.getMessage(), createException);
		}
	}
	private void populatePK(MailHandoverVO mailHandoverVO){
		MailHandoverTimePK mailHandoverTimePK = new MailHandoverTimePK();
		mailHandoverTimePK.setCompanyCode(mailHandoverVO.getCompanyCode());
		mailHandoverTimePK.setSerialNumber(mailHandoverVO.getSerialNumber());
		this.mailHandoverTimePK = mailHandoverTimePK;
	}
	private void populateAttributes(MailHandoverVO mailHandoverVO){
		
		this.handoverTime = mailHandoverVO.getHandoverTimes();
		this.airport = mailHandoverVO.getHoAirportCodes();
		this.exchangeOffice = mailHandoverVO.getExchangeOffice();
		this.mailClass = mailHandoverVO.getMailClass();
		this.mailSubClass = mailHandoverVO.getMailSubClass();
		this.paCode = mailHandoverVO.getGpaCode();
		this.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true)); // Added by A-7929 as part of IASCB-35577
	    this.setLastUpdateUser(mailHandoverVO.getLastUpdateUser());
	}

	public static MailHandoverTime find(String companyCode, int serialNumber) throws FinderException, SystemException {
		MailHandoverTimePK mailHandoverTimePK = new MailHandoverTimePK();
		mailHandoverTimePK.setCompanyCode(companyCode);
		mailHandoverTimePK.setSerialNumber(serialNumber);
		return PersistenceController.getEntityManager().find(MailHandoverTime.class, mailHandoverTimePK);
	}
	public void update(MailHandoverVO mailHandoverVO) throws SystemException{
		populateAttributes(mailHandoverVO);
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
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	/**
	 * Page This method is used to remove the DAOS Instance
	 * 
	 * @return
	 * @throws SystemException
	 */
	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(PRODUCT_NAME));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception);
		}
	}

	public static Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO,
			int pageNumber) throws SystemException {
		try {
			return constructDAO().findMailHandoverDetails(mailHandoverFilterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	public static MailHandoverTime findMailHandoverTime(MailHandoverVO mailHandoverVO) throws SystemException {
		MailHandoverTime mailHandoverTime = null;
		MailHandoverFilterVO filterVO = new MailHandoverFilterVO();
		int serialNumber = 0;
		filterVO.setCompanyCode(mailHandoverVO.getCompanyCode());
		filterVO.setAirportCode(mailHandoverVO.getHoAirportCodes());
		filterVO.setMailClass(mailHandoverVO.getMailClass());
		filterVO.setMailSubClass(mailHandoverVO.getMailSubClass());
		filterVO.setGpaCode(mailHandoverVO.getGpaCode());
		filterVO.setExchangeOffice(mailHandoverVO.getExchangeOffice());
		filterVO.setDefaultPageSize(10);
		Page<MailHandoverVO> mailHandoverVOs = findMailHandoverDetails(filterVO, 1);
		if(mailHandoverVOs!=null && mailHandoverVOs.size()>0)
			for(MailHandoverVO vo : mailHandoverVOs){
				serialNumber = vo.getSerialNumber();
			}
		try {
			if(mailHandoverVO.getCompanyCode()!=null)
			mailHandoverTime = find(mailHandoverVO.getCompanyCode(), serialNumber);
		} catch (FinderException e) {
		}
		return mailHandoverTime;
	}
	
}
