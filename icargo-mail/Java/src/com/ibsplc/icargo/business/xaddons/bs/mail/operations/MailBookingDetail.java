package com.ibsplc.icargo.business.xaddons.bs.mail.operations;

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


import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

import com.ibsplc.icargo.persistence.dao.xaddons.bs.mail.operations.BaseMailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;

/**
 *	Java file	: 	.MailBookingDetail.java
 *
 *	Created by	:	a-7779
 *	Created on	:	22-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */

/**
 * Java file : .MailBookingDetail.java Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : a-7779 :
 * 22-Aug-2017 : Draft
 */

//@Table(name = "MALBKGFLTDTL")
//@Entity
@Staleable
public class MailBookingDetail {

	private static final String MODULENAME = "bsmail.operations";

	private String bookingStatus;
	private String lastUpdateUser;
	private Calendar lastUpdateTime;
	private MailBookingDetailPK mailBookingDetailPK;

	@Column(name = "BKGSTA")
	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightCarrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")),
			@AttributeOverride(name = "segementserialNumber", column = @Column(name = "SEGSERNUM")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")) })
	public MailBookingDetailPK getMailBookingDetailPK() {
		return mailBookingDetailPK;
	}

	public void setMailBookingDetailPK(
			MailBookingDetailPK mailBookingDetailPK) {
		this.mailBookingDetailPK = mailBookingDetailPK;
	}

	/**
	 * Default Constructor
	 * 
	 */
	public MailBookingDetail() {

	}

	/**
	 * 
	 * Constructor : @param mailBookingDetailVO Constructor : @throws
	 * SystemException Created by : a-7779 Created on : 22-Aug-2017
	 */
	public MailBookingDetail(MailBookingDetailVO mailBookingDetailVO)
			throws SystemException {
	
		try {
			EntityManager em = PersistenceController.getEntityManager();
			populatePk(mailBookingDetailVO);
			populateAttributes(mailBookingDetailVO); 
			em.persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage());
		}
	
	}
	/**
	 * 
	 * Method : MailBookingDetail.populateAttributes Added by : a-7779 on
	 * 22-Aug-2017 Used for : Parameters : @param mailBookingDetailVO Return
	 * type : void
	 * 
	 * @throws SystemException
	 */
	private void populateAttributes(
			MailBookingDetailVO mailBookingDetailVO)
			throws SystemException {

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		this.setBookingStatus(mailBookingDetailVO.getBookingStatus());
		this.setLastUpdateUser(logonAttributes.getUserId());
		this.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, true).toCalendar());
		

	}

	/**
	 * 
	 * Method : MailBookingDetail.populatePk Added by : a-7779 on 22-Aug-2017
	 * Used for : Parameters : @param mailBookingDetailVO Return type : void
	 * @throws SystemException 
	 */
	private void populatePk(MailBookingDetailVO mailBookingDetailVO) throws SystemException {
		
		
		mailBookingDetailPK = new MailBookingDetailPK();
		mailBookingDetailPK
				.setCompanyCode(mailBookingDetailVO.getCompanyCode());
		
		if(mailBookingDetailVO.getBookingFlightNumber()!=null && mailBookingDetailVO.getBookingFlightNumber().trim().length()>0)//modified by A-7371 as part of ICRD-266881
		{
		mailBookingDetailPK.setFlightNumber(mailBookingDetailVO
				.getBookingFlightNumber());
		}else{
			mailBookingDetailPK.setFlightNumber("-1");
		}
		
		mailBookingDetailPK.setFlightCarrierId(mailBookingDetailVO.getBookingFlightCarrierid());
		mailBookingDetailPK.setFlightSequenceNumber(mailBookingDetailVO
				.getBookingFlightSequenceNumber());
		mailBookingDetailPK.setSegementserialNumber(mailBookingDetailVO
				.getSegementserialNumber());
		mailBookingDetailPK.setMailSequenceNumber(mailBookingDetailVO.getMailSequenceNumber());
		//added by A-8061 as part of ICRD-229572 begin 
		
		/*int serialNumnber=0;//A-8061 commented for ICRD-305178 mail bag duplication
		if(mailBookingDetailVO.getBookingFlightNumber()!=null && mailBookingDetailVO.getBookingFlightNumber().trim().length()>0)//modified by A-7371 as part of ICRD-266881
		{
		 serialNumnber = getMailBookingFlightDetailSerialNumber(mailBookingDetailVO);
		}
		if(serialNumnber>0){
		mailBookingDetailPK.setSerialNumber(serialNumnber+1);
		}else{*/
			mailBookingDetailPK.setSerialNumber(1);
		//}
		//added by A-8061 as part of ICRD-229572 end
		//this.setMailBookingDetailPK(mailBookingDetailPK);

	}

	

	/**
	 * Added by 	:	a-8061 on 13-Nov-2017
	 * @param mailBookingDetailVO
	 * @return
	 * @throws SystemException
	 */
	private int getMailBookingFlightDetailSerialNumber(MailBookingDetailVO mailBookingDetailVO) throws SystemException{
		try {
			return constructDAO().getMailBookingFlightDetailSerialNumber(mailBookingDetailVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage());
		}
		
	}


	/**
	 * 
	 * Method : MailBookingFlightDetail.findMailBookingAWBs Added by : a-7779 on
	 * 28-Aug-2017 Used for : Parameters : @param mailBookingFilterVO Parameters
	 * : @return Return type : Collection<MailBookingFlightDetailVO>
	 * @throws SystemException 
	 * @throws PersistenceException 
	 */
	public static Page<MailBookingDetailVO> findMailBookingAWBs(
			MailBookingFilterVO mailBookingFilterVO,int pageNumber) throws SystemException {
		try {
			return constructDAO().findMailBookingAWBs(mailBookingFilterVO,pageNumber);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage());
		}
	}

	/**
	 * 
	 * Method : MailBookingFlightDetail.constructDAO Added by : a-7779 on
	 * 28-Aug-2017 Used for : Parameters : @return Parameters : @throws
	 * SystemException Return type : MailTrackingDefaultsDAO
	 */
	private static BaseMailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return BaseMailTrackingDefaultsDAO.class.cast(em
					.getQueryDAO(MODULENAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * 
	 * Method : MailBookingFlightDetail.find Added by : a-7779 on 28-Aug-2017
	 * Used for : Parameters : @param mailBookingDetailPK2 Parameters : @return
	 * Parameters : @throws FinderException Parameters : @throws SystemException
	 * Return type : MailBookingFlightDetail
	 */
	public static MailBookingDetail find(
			MailBookingDetailPK mailBookingDetailPK2)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(
				MailBookingDetail.class, mailBookingDetailPK2);
	}

	/**
	 * 
	 * 	Method		:	MailBookingFlightDetail.remove
	 *	Added by 	:	a-7779 on 28-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingDetailPK2
	 *	Parameters	:	@throws OptimisticConcurrencyException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void remove() throws OptimisticConcurrencyException, SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	/**
	 * 
	 * 	Method		:	MailBookingFlightDetail.fetchBookedFlightDetails
	 *	Added by 	:	a-7779 on 29-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param shipmentPrefix
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<MailBookingFlightDetailVO>
	 * @throws SystemException 
	 * @throws PersistenceException 
	 */
	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode,
			String shipmentPrefix, String masterDocumentNumber) throws SystemException, PersistenceException {
		Collection<MailBookingDetailVO> mailBookingDetailVOs = null;
		 mailBookingDetailVOs = constructDAO().fetchBookedFlightDetails(companyCode,
				shipmentPrefix, masterDocumentNumber);
		 
		 //a-8061 ICRD-232728 begin
		String flightNumber="";
		int flightSequenceNumber=0;
		int flightCarrierid=0;
		int segementserialNumber=0;
		
		if(mailBookingDetailVOs!=null && mailBookingDetailVOs.size() > 0){
		for(MailBookingDetailVO mailBookingDetailVO : mailBookingDetailVOs){
			flightNumber=mailBookingDetailVO.getBookingFlightNumber();
			flightSequenceNumber=mailBookingDetailVO.getBookingFlightSequenceNumber();
			flightCarrierid=mailBookingDetailVO.getBookingFlightCarrierid();
			segementserialNumber=mailBookingDetailVO.getSegementserialNumber();
			int attachedMailBagsCount=0;
			
			if(flightNumber==null || "".equals(flightNumber)){
				flightNumber="-1";
			}
			//if(flightNumber!=null && !flightNumber.isEmpty()){  
			 attachedMailBagsCount= constructDAO().findAttachedMailBags(companyCode,
					shipmentPrefix, masterDocumentNumber,flightNumber,flightSequenceNumber,flightCarrierid,segementserialNumber);
			//}
			
			
			mailBookingDetailVO.setAttachedMailBagCount(attachedMailBagsCount);
		}
		}
		//a-8061 ICRD-232728 end
		
		return mailBookingDetailVOs;
	}

	/**
	 * 	Method		:	MailBookingDetail.fetchBookedFlightDetailsForMailbag
	 *	Added by 	:	A-7531 on 25-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagId 
	 *	Return type	: 	void
	 * @return 
	 * @throws SystemException 
	 */
	public Collection<MailBookingDetailVO> fetchBookedFlightDetailsForMailbag(long mailSequenceNumber) throws SystemException {
		return constructDAO().fetchBookedFlightDetailsForMailbag(mailSequenceNumber);
		
	}
}
