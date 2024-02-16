package com.ibsplc.icargo.persistence.dao.addons.mail.operations;



import java.util.Collection;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;

public interface AddonsMailOperationsDAO extends QueryDAO {
	
	public Page<MailBookingDetailVO> findMailBookingAWBs(
			MailBookingFilterVO mailBookingFilterVO,int pageNumber) throws SystemException, PersistenceException;


	public Collection<MailBookingDetailVO> fetchBookedFlightDetailsForMailbag(long mailSequenceNumber)
			throws SystemException;

	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws SystemException, PersistenceException;

	public int findAttachedMailBags(String companyCode, String shipmentPrefix, String masterDocumentNumber,
			String flightNumber, int flightSequenceNumber, int flightCarrierid, int segementserialNumber)
			throws SystemException, PersistenceException;


	public Page<MailBookingDetailVO> findLoadPlanBookings(MailBookingFilterVO mailBookingFilterVO, int pageNumber) throws SystemException;
	
	public Collection<MailBookingDetailVO> findFlightDetailsforAWB(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws SystemException, PersistenceException;   
   
    public Page<MailBookingDetailVO> findManifestBookings(MailBookingFilterVO mailBookingFilterVO, int pageNumber) throws SystemException;

	public int findSplitCount(String mstDocunum, String docOwnerId, String companyCode, int seqNum, int dupNum)
			throws SystemException;
}
