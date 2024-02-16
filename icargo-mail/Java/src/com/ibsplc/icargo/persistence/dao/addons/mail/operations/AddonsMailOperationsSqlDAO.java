package com.ibsplc.icargo.persistence.dao.addons.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.persistence.dao.addons.mail.operations.MailBookingFilterQuery;
import com.ibsplc.icargo.persistence.dao.addons.mail.operations.MailBookingMapper;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Query;

public class AddonsMailOperationsSqlDAO extends AbstractQueryDAO implements AddonsMailOperationsDAO {

	 private static final String FIND_MAILBOOKING_AWBS="addons.mail.operations.findmailbookingawbs";
	 private static final String ORDER_BY_PARAMETERS = " SHPPFX,MSTDOCNUM";
	 private static final String AS_RANK = ") AS RANK FROM(";
	private static final String FIND_BOOKING_DETAILSFORMAILBAG = "addons.mail.operations.fetchBookedFlightDetailsForMailbag";
	 private static final String FETCH_BOOKED_FLIGHTS_DETAILS = "addons.mail.operations.fetchBookedFlightDetails";
	private static final String FIND_ATTACHED_MAILBAGS_COUNT_FROM_MALMST = "addons.mail.operations.findAttachedMailBagsCount";
	private static final String FIND_LOAD_PLAN_BOOKINGS="addons.mail.operations.findloadplanbookings";
	private static final String FIND_MANIFEST_BOOKINGS = "addons.mail.operations.findmanifestbookings";
	private static final String ORDER_BY = "AWBNUM";
	private static final String FETCH_BOOKED_FLIGHTS_DETAILS_AWB="addons.mail.operations.findFlightDetailsforAWB";
	private static final String FIND_SPLIT_COUNT_QUERY="addons.mail.operations.findSplitCount";
	    
	@Override
	public Page<MailBookingDetailVO> findMailBookingAWBs(MailBookingFilterVO mailBookingFilterVO, int pageNumber)
			throws SystemException, PersistenceException {
		Page<MailBookingDetailVO> mailbookingDetailVO=null;
		  PageableNativeQuery<MailBookingDetailVO> pgqry =null;
		
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_DENSE_RANK_QUERY);
	    rankQuery.append(ORDER_BY_PARAMETERS);
	    rankQuery.append(AS_RANK);
		Query baseQry = getQueryManager().createNamedNativeQuery(FIND_MAILBOOKING_AWBS);
		rankQuery.append(baseQry);
			pgqry = new MailBookingFilterQuery(new MailBookingMapper(), mailBookingFilterVO, rankQuery.toString());
		mailbookingDetailVO=pgqry.getPage(pageNumber);
		 return mailbookingDetailVO;
	}

	@Override
	public Collection<MailBookingDetailVO> fetchBookedFlightDetailsForMailbag(long mailSequenceNumber)
			throws SystemException {
		Query query = getQueryManager().createNamedNativeQuery(FIND_BOOKING_DETAILSFORMAILBAG);
	
		int index = 0;
		query.setParameter(++index, mailSequenceNumber);
		return query.getResultList(new BookingDetailsMapper());

	}
	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws SystemException, PersistenceException {
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FETCH_BOOKED_FLIGHTS_DETAILS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, shipmentPrefix);
		query.setParameter(++index, masterDocumentNumber);
		return query.getResultList(new MailBookedFlightDetailsMapper());
	}
	public int findAttachedMailBags(String companyCode, String shipmentPrefix, String masterDocumentNumber,
			String flightNumber, int flightSequenceNumber, int flightCarrierid, int segementserialNumber)
			throws SystemException, PersistenceException {
		int index = 0;
		int attachedMailBags = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_ATTACHED_MAILBAGS_COUNT_FROM_MALMST);
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

	@Override
	public Page<MailBookingDetailVO> findLoadPlanBookings(MailBookingFilterVO mailBookingFilterVO, int pageNumber)
			throws SystemException {
		PageableNativeQuery<MailBookingDetailVO> pgqry =null;
		Page<MailBookingDetailVO> loadPlanDetailVO = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_DENSE_RANK_QUERY);
	    rankQuery.append(ORDER_BY);
	    rankQuery.append(AS_RANK);
		Query query = getQueryManager().createNamedNativeQuery(FIND_LOAD_PLAN_BOOKINGS);
		rankQuery.append(query);
		pgqry = new LoadPlanFilterQuery(new LoadPlanMapper(), mailBookingFilterVO, rankQuery.toString());
		loadPlanDetailVO= pgqry.getPage(pageNumber);
		return loadPlanDetailVO;
	}

	@Override
	public Collection<MailBookingDetailVO> findFlightDetailsforAWB(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws SystemException, PersistenceException {
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FETCH_BOOKED_FLIGHTS_DETAILS_AWB);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, shipmentPrefix);
		query.setParameter(++index, masterDocumentNumber);
		return query.getResultList(new MailBookedFlightDetailsMapper());
	}   
    @Override
	public Page<MailBookingDetailVO> findManifestBookings(MailBookingFilterVO mailBookingFilterVO, int pageNumber)
			throws SystemException {
		PageableNativeQuery<MailBookingDetailVO> pgqry = null;
		Page<MailBookingDetailVO> manifestDetailVO = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_DENSE_RANK_QUERY);
		rankQuery.append(ORDER_BY);
		rankQuery.append(AS_RANK);
		Query query = getQueryManager().createNamedNativeQuery(FIND_MANIFEST_BOOKINGS);
		rankQuery.append(query);
		pgqry = new ManifestFilterQuery(new ManifestMapper(), mailBookingFilterVO, rankQuery.toString());
		manifestDetailVO= pgqry.getPage(pageNumber);
		return manifestDetailVO;
	}
    
	public int findSplitCount(String mstDocunum, String docOwnerId, String companyCode, int seqNum, int dupNum)
			throws SystemException {
		int index = 0;
		int splCount = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_SPLIT_COUNT_QUERY);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, docOwnerId);
		query.setParameter(++index, mstDocunum);
		query.setParameter(++index, seqNum);
		query.setParameter(++index, dupNum);

		String splCountValue = query.getSingleResult(getStringMapper("SPLCNT"));
		if (splCountValue != null) {
			splCount = Integer.parseInt(splCountValue);
		}
		return splCount;
	}

}
