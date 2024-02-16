/*
 * OZMailTrackingDefaultsSqlDAO.java Created on Sep  10, 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.AWBAttachedMailbagDetailsMapper;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author a-A7779
 *
 */

public class OZMailTrackingDefaultsSqlDAO extends AbstractQueryDAO implements
		OZMailTrackingDefaultsDAO {

	private Log log = LogFactory.getLogger("OZMAIL_OPERATIONS");
	private static final String MODULE = "OZMailTrackingDefaultsSqlDAO";
	
    private static final String FIND_MAILBOOKING_AWBS="xaddons.oz.mail.operations.findmailbookingawbs";
    private static final String FETCH_BOOKED_FLIGHTS_DETAILS = "xaddons.oz.mail.operations.fetchBookedFlightDetails";
    private static final String FIND_AWB_ATTACHED_MAIL_BAG_DETAILS = "mail.operations.findAwbAtachedMailbagDetails";
    private static final String FIND_BOOKING_DETAILSFORMAILBAG = "xaddons.oz.mail.operations.fetchBookedFlightDetailsForMailbag";
    private static final String ORDER_BY_PARAMETERS = " SHPPFX,MSTDOCNUM";
    private static final String AS_RANK = ") AS RANK FROM(";//added by A-7371 for ICRD-228233
    //a-8061
    private static final String FIND_MAXSERIAL_NUMBER_FROM_MALBKGFLTDTL = "xaddons.oz.mail.operations.findMaxSerialNumber";
    //A-8061 added for ICRD-232728
    private static final String FIND_ATTACHED_MAILBAGS_COUNT_FROM_MALMST = "xaddons.oz.mail.operations.findAttachedMailBagsCount";
    
 

  
	/**
	   * 
	   *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findMailBookingAWBs(com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingFlightFilterVO)
	   *	Added by 			: a-7779 on 24-Aug-2017
	   * 	Used for 	:
	   *	Parameters	:	@param mailBookingFilterVO
	   *	Parameters	:	@return
	   *	Parameters	:	@throws SystemException
	   *	Parameters	:	@throws PersistenceException
	   */
	public Page<MailBookingDetailVO> findMailBookingAWBs(
			MailBookingFilterVO mailBookingFilterVO,int pageNumber) throws SystemException, PersistenceException{
		log.entering(MODULE, "findMailBookingAWBs");
		Page<MailBookingDetailVO> mailbookingDetailVO=null;
		//String  baseQry = getQueryManager().getNamedNativeQueryString(
		//		FIND_MAILBOOKING_AWBS);
		Query baseQry = getQueryManager().createNamedNativeQuery(
				FIND_MAILBOOKING_AWBS);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_DENSE_RANK_QUERY);
	    rankQuery.append(ORDER_BY_PARAMETERS);
	    rankQuery.append(AS_RANK);
		rankQuery.append(baseQry);
		//PageableNativeQuery<MailBookingDetailVO> pgqry = new MailBookingFilterQuery(mailBookingFilterVO.getPageSize(),mailBookingFilterVO.getRowCount(),rankQuery.toString(),
			//	new MailBookingMapper(), mailBookingFilterVO);
		PageableNativeQuery<MailBookingDetailVO> pgqry = new MailBookingFilterQuery(new MailBookingMapper(), mailBookingFilterVO, rankQuery.toString());
		 mailbookingDetailVO=pgqry.getPage(pageNumber);
			log.log(Log.INFO, "Query: ", pgqry);
		 return mailbookingDetailVO;
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#fetchBookedFlightDetails(java.lang.String, java.lang.String, java.lang.String)
	 *	Added by 			: a-7779 on 31-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param shipmentPrefix
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(
			String companyCode, String shipmentPrefix,
			String masterDocumentNumber)throws SystemException, PersistenceException{
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FETCH_BOOKED_FLIGHTS_DETAILS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, shipmentPrefix);
		query.setParameter(++index, masterDocumentNumber);
		Collection<MailBookingDetailVO> mailbookingdetails=query.getResultList(new MailBookedFlightDetailsMapper());
		return mailbookingdetails;
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findAwbAtachedMailbagDetails(java.lang.String, int, java.lang.String)
	 *	Added by 			: a-7779 on 31-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param ownerId
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@return
	 * @throws SystemException 
	 */
	public ScannedMailDetailsVO findAwbAtachedMailbagDetails(
			String companyCode, int ownerId, String masterDocumentNumber) throws SystemException{
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_AWB_ATTACHED_MAIL_BAG_DETAILS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, ownerId);
		query.setParameter(++index, masterDocumentNumber);
		return query.getResultList(new AWBAttachedMailbagDetailsMapper()).get(0);
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations.OZMailTrackingDefaultsDAO#fetchBookedFlightDetailsForMailbag(long)
	 *	Added by 			: A-7531 on 25-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailSequenceNumber
	 *	Parameters	:	@return 
	 * @throws SystemException 
	 */
	
	public Collection<MailBookingDetailVO> fetchBookedFlightDetailsForMailbag(
			long mailSequenceNumber) throws SystemException {
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_BOOKING_DETAILSFORMAILBAG);
		
		int index = 0;
		query.setParameter(++index,mailSequenceNumber);
		Collection<MailBookingDetailVO> mailbookingdetail = query
				.getResultList(new BookingDetailsMapper());
		return mailbookingdetail;
	}

	/**
	 * @author A-8061
	 * @param mailBookingDetailVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public int getMailBookingFlightDetailSerialNumber(
			MailBookingDetailVO mailBookingDetailVO) throws SystemException{
		
		log.entering(MODULE, "getMailBookingFlightDetailSerialNumber");
		int serialNumber = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAXSERIAL_NUMBER_FROM_MALBKGFLTDTL);
		int indx = 0;
		
		query.setParameter(++indx,mailBookingDetailVO.getCompanyCode() );
		query.setParameter(++indx, mailBookingDetailVO.getBookingFlightNumber());
		query.setParameter(++indx,mailBookingDetailVO.getBookingFlightSequenceNumber() );
		query.setParameter(++indx, mailBookingDetailVO.getBookingFlightCarrierid());
		query.setParameter(++indx, mailBookingDetailVO.getSegementserialNumber());

		String serNum = query.getSingleResult(getStringMapper("SERNUM"));
		if (serNum != null) {
			serialNumber = Integer.parseInt(serNum);
		}
		log.exiting(MODULE, "getMailBookingFlightDetailSerialNumber");
		return serialNumber;
		
		
	}
	
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDAO.findAttachedMailBags
	 *	Added by 	:	a-8061 on 20-Nov-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param shipmentPrefix
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@return 
	 *	Return type	: 	Integer
	 */
	public int findAttachedMailBags(
			String companyCode, String shipmentPrefix,
			String masterDocumentNumber,String flightNumber,int flightSequenceNumber,int flightCarrierid,int segementserialNumber)throws SystemException, PersistenceException{
		int index = 0;
		int attachedMailBags = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_ATTACHED_MAILBAGS_COUNT_FROM_MALMST);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, shipmentPrefix);
		query.setParameter(++index, masterDocumentNumber);
		query.setParameter(++index, flightNumber);
		query.setParameter(++index, flightSequenceNumber);
		query.setParameter(++index, flightCarrierid);
		query.setParameter(++index, segementserialNumber);
		
		String attMalCount = query.getSingleResult(getStringMapper("MALCOUNT"));
		if (attMalCount != null) {
			attachedMailBags = Integer.parseInt(attMalCount);
		}
		
		return attachedMailBags;
	}
	
	

	
}