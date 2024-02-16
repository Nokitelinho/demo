/*
 * BaseMailTrackingDefaultsDAO.java Created on Sep 10, 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.xaddons.bs.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingFilterVO;

/**
 * @author a-7779
 *
 */
public interface BaseMailTrackingDefaultsDAO {



	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDAO.findMailBookingAWBs
	 *	Added by 	:	a-7779 on 24-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	Collection<MailBookingFlightDetailVO>
	 */
	public Page<MailBookingDetailVO> findMailBookingAWBs(
			MailBookingFilterVO mailBookingFilterVO,int pageNumber) throws SystemException, PersistenceException;
	/**
	 * 
	 * 	Method		:	BaseMailTrackingDefaultsDAO.fetchBookedFlightDetails
	 *	Added by 	:	a-7779 on 29-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param shipmentPrefix
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<MailBookingFlightDetailVO>
	 */
	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(
			String companyCode, String shipmentPrefix,
			String masterDocumentNumber)throws SystemException, PersistenceException;
/**
 * 
 * 	Method		:	BaseMailTrackingDefaultsDAO.findAwbAtachedMailbagDetails
 *	Added by 	:	a-7779 on 31-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param ownerId
 *	Parameters	:	@param masterDocumentNumber
 *	Parameters	:	@return 
 *	Return type	: 	ScannedMailDetailsVO
 */
	public ScannedMailDetailsVO findAwbAtachedMailbagDetails(
			String companyCode, int ownerId, String masterDocumentNumber)throws SystemException, PersistenceException;
/**
 * 	Method		:	BaseMailTrackingDefaultsDAO.fetchBookedFlightDetailsForMailbag
 *	Added by 	:	A-7531 on 25-Sep-2017
 * 	Used for 	:
 *	Parameters	:	@param mailSequenceNumber
 *	Parameters	:	@return 
 *	Return type	: 	Collection<MailBookingDetailVO>
 * @throws SystemException 
 */
public Collection<MailBookingDetailVO> fetchBookedFlightDetailsForMailbag(
		long mailSequenceNumber) throws SystemException;

/**
 * @author A-8061
 * @param mailBookingDetailVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public int getMailBookingFlightDetailSerialNumber(
		MailBookingDetailVO mailBookingDetailVO) throws SystemException,PersistenceException;


/**
 * 
 * 	Method		:	BaseMailTrackingDefaultsDAO.findAttachedMailBags
 *	Added by 	:	a-8061 on 20-Nov-2017
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param shipmentPrefix
 *	Parameters	:	@param masterDocumentNumber
 *	Parameters	:	@return 
 *	Return type	: 	Integer
 */
public int findAttachedMailBags(String companyCode, String shipmentPrefix,
		String masterDocumentNumber,String flightNumber,int flightSequenceNumber,int flightCarrierid,int segementserialNumber)throws SystemException,PersistenceException;



			}
