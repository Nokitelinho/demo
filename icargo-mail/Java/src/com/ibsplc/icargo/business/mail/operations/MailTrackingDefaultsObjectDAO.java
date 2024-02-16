/*
 * MailTrackingDefaultsObjectDAO.java Created on May 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Collection;
import java.util.List;

import javax.persistence.NoResultException;

import com.ibsplc.icargo.business.mail.operations.vo.MailResditFileLogVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.UldResditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.object.AbstractObjectQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.object.ObjectQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1739
 *
 */

/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		  May 23, 2007			a-1739		Created
 */
/**
 * @author a-1739
 */
public class MailTrackingDefaultsObjectDAO extends AbstractObjectQueryDAO implements MailtrackingDefaultsObjectInterface{

	private static final String FIND_MAILRESDIT_FOREVENT =
		"mail.operations.findmailresditforevent";
	/**
	 * For finding all instances of a mail resdit
	 */
	private static final String FIND_ALL_MAILRESDITS =
		"mail.operations.findallmailresdits";

	/**
	 * For finding all instances of a uld resdit
	 */
	private static final String FIND_ALL_ULDRESDITS =
		"mail.operations.findalluldresdits";
	/**
	 * For finding all instances of a uld resdit,where flight Details is not available
	 */
	private static final String FIND_PENDING_ULDRESDITS =
		"mail.operations.findpendinguldresdits";

	/**
	 * For finding all instances of a mail resdit
	 */
	private static final String FIND_ALL_MAILRESDITS_RESDITSTATUSUPDATE =
		"mail.operations.findmailresditsforresditstatusupdate";


	/**
	 * For finding all instances of a uld resdit
	 */
	private static final String FIND_ALL_ULDRESDITS_RESDITSTATUSUPDATE =
		"mail.operations.finduldresditsforresditstatusupdate";
	
	/**
	 * For finding all instances of mail resdit file log
	 */
	private static final String FIND_ALL_MAILRESDITFILELOG =
		"mail.operations.findallmailresditsforfilelog";
	/**
	 * For finding all instances of mailbag history
	 */
	private static final String FIND_ALL_MAILBAGHISTORY =
		"mail.operations.findallmailbaghistory";
	/**
	 * For finding all instances of mailbag resdits
	 */
	private static final String FIND_ALL_RESDITS =
		"mail.operations.findallresdits";
	
	private static final String FIND_SERVICE_LEVEL_DETAILS = 
			"mail.operations.findserviceleveldetails";//a-6986 added for ICRD-243469
	private Log log = LogFactory.getLogger("MailTracking_Defaults");

	/**
	 * TODO Purpose
	 * May 23, 2007, a-1739
	 * @param mailbagVO
	 * @param resditEvent
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public List<MailResdit> findMailResditForEvent(MailbagVO mailbagVO, String resditEvent)
	throws PersistenceException, SystemException {
		ObjectQuery query = getQueryManager().createNamedQuery(
				FIND_MAILRESDIT_FOREVENT);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getMailbagId());
		query.setParameter(++idx, resditEvent);
		query.setParameter(++idx, mailbagVO.getMailSequenceNumber());
		query.setParameter(++idx, mailbagVO.getScannedPort());
		query.setParameter(++idx, mailbagVO.getCarrierId());
		query.setParameter(++idx, mailbagVO.getFlightNumber());
		query.setParameter(++idx, mailbagVO.getFlightSequenceNumber());
		query.setParameter(++idx, mailbagVO.getSegmentSerialNumber());
		query.setParameter(++idx, MailbagVO.FLAG_NO);
		return query.getResultList();
	}
	/**
	 *
	 * @param mailResditVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public List<MailResdit> findMailResdits(MailResditVO mailResditVO)
		throws PersistenceException, SystemException {

		ObjectQuery query = null;
		if(mailResditVO.getFlightSequenceNumber() > 0) {
			query = getQueryManager().createNamedQuery(
					FIND_MAILRESDIT_FOREVENT);
		} else {
			query = getQueryManager().createNamedQuery(
					FIND_ALL_MAILRESDITS);
		}

		int idx = 0;
		query.setParameter(++idx, mailResditVO.getCompanyCode());
		query.setParameter(++idx, mailResditVO.getMailId());
		query.setParameter(++idx, mailResditVO.getEventCode());
		query.setParameter(++idx, mailResditVO.getEventAirport());
		if(mailResditVO.getFlightSequenceNumber() > 0) {
			query.setParameter(++idx, mailResditVO.getCarrierId());
			query.setParameter(++idx, mailResditVO.getFlightNumber());
			query.setParameter(++idx, mailResditVO.getFlightSequenceNumber());
			query.setParameter(++idx, mailResditVO.getSegmentSerialNumber());
			query.setParameter(++idx, mailResditVO.getResditSentFlag());
		}

		return query.getResultList();
	}


	/**
	 * @author a-1936
	 * A generic Method that can be Used to fetch the Mail Resdit Entity Corresponding to the
	 * Event Code  and the Event Airport..
	 * Added By Karthick V as  the part of the NCA Mail Tracking CR..
	 * @param mailResditVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public List<MailResdit> findMailResdit(MailResditVO mailResditVO,String eventCode)
		throws PersistenceException,SystemException {
		   log.entering("MailTrackingDefaultsObjectDAO","findMailResdit");
	       ObjectQuery query = null;
	 	   query = getQueryManager().createNamedQuery(
	 		  FIND_ALL_MAILRESDITS);
	 	   int idx = 0;
		   query.setParameter(++idx, mailResditVO.getCompanyCode());
		   query.setParameter(++idx, mailResditVO.getMailId());
		   query.setParameter(++idx, eventCode);
		   query.setParameter(++idx, mailResditVO.getEventAirport());
		   log.exiting("MailTrackingDefaultsObjectDAO","findMailResdit");
		   return query.getResultList();
	}

	/**
	 * @author a-1876
	 * A generic Method that can be Used to fetch the Uld Resdit Entity Corresponding to the
	 * Event Code  and the Event Airport..
	 * Added By Roopak V S
	 * @param uldResditVO
	 * @param eventCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public List<UldResdit> findUldResdit(UldResditVO uldResditVO,String eventCode)
		throws PersistenceException,SystemException {
		   log.entering("MailTrackingDefaultsObjectDAO","findUldResdit");
	       ObjectQuery query = null;
	 	   query = getQueryManager().createNamedQuery(
	 			  FIND_ALL_ULDRESDITS);
	 	   int idx = 0;
		   query.setParameter(++idx, uldResditVO.getCompanyCode());
		   query.setParameter(++idx, uldResditVO.getUldNumber());
		   query.setParameter(++idx, eventCode);
		   query.setParameter(++idx, uldResditVO.getEventAirport());
		   query.setParameter(++idx, uldResditVO.getCarrierId());
		   query.setParameter(++idx, uldResditVO.getFlightNumber());
		   query.setParameter(++idx, uldResditVO.getFlightSequenceNumber());
		   log.exiting("MailTrackingDefaultsObjectDAO","findUldResdit");
		   return query.getResultList();
	}
	
	public List<UldResdit> findPendingUldResdit(UldResditVO uldResditVO,String eventCode)
	throws PersistenceException,SystemException {
	    log.entering("MailTrackingDefaultsObjectDAO","findPendingUldResdit");
        ObjectQuery query = null;
		query =getQueryManager().createNamedQuery(
					FIND_PENDING_ULDRESDITS);		
 	    int idx = 0;
	    query.setParameter(++idx, uldResditVO.getCompanyCode());
	    query.setParameter(++idx, uldResditVO.getUldNumber());
	    query.setParameter(++idx, eventCode);
	    query.setParameter(++idx, uldResditVO.getEventAirport());
	    query.setParameter(++idx, uldResditVO.getCarrierId());
	    log.exiting("MailTrackingDefaultsObjectDAO","findPendingUldResdit");
	    return query.getResultList();
        
	}
	/**
	 *  @author a-2572
	 *  @param companyCode
	 *  @param controlReferenceNumber
	 *  @return
	 *  @throws SystemException
	 *  @throws PersistenceException
	 */
	public Collection<MailResdit> findMailResditForResditStatusUpd
	(String companyCode ,String controlReferenceNumber)	throws PersistenceException,SystemException{
		log.entering("MailTrackingDefaultsObjectDAO","findMailResditForResditStatus");
	       ObjectQuery query = null;
	 	   query = getQueryManager().createNamedQuery(
	 			  FIND_ALL_MAILRESDITS_RESDITSTATUSUPDATE);
	 	   int idx = 0;
		   query.setParameter(++idx, companyCode);
		   query.setParameter(++idx, controlReferenceNumber);
		   
		   log.exiting("MailTrackingDefaultsObjectDAO","findMailResditForResditStatus");
		   return query.getResultList();
	}

	/**
	 *  @author a-2572
	 *  @param companyCode
	 *  @param controlReferenceNumber
	 *  @return
	 *  @throws SystemException
	 *  @throws PersistenceException
	 */
	public Collection<UldResdit> findULDResditForResditStatusUpd
	(String companyCode ,String controlReferenceNumber)	throws PersistenceException,SystemException{
		log.entering("MailTrackingDefaultsObjectDAO","findMailResditForResditStatus");
	       ObjectQuery query = null;
	 	   query = getQueryManager().createNamedQuery(
	 			  FIND_ALL_ULDRESDITS_RESDITSTATUSUPDATE);
	 	   int idx = 0;
		   query.setParameter(++idx, companyCode);
		   query.setParameter(++idx, controlReferenceNumber);
		   
		   log.exiting("MailTrackingDefaultsObjectDAO","findMailResditForResditStatus");
		   return query.getResultList();
	}
   
	/**
	 * 
	 * @param mailbagHistoryVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author A-2572
	 */
	public Collection<MailbagHistory> findMailBagHistory(MailbagHistoryVO mailbagHistoryVO)
	throws PersistenceException,SystemException{
		
		ObjectQuery query = null;
		query = getQueryManager().createNamedQuery(FIND_ALL_MAILBAGHISTORY);
		
		int idx = 0;
		query.setParameter(++idx, mailbagHistoryVO.getCompanyCode());
		query.setParameter(++idx, mailbagHistoryVO.getInterchangeControlReference());
		query.setParameter(++idx, mailbagHistoryVO.getMailbagId());
		query.setParameter(++idx, mailbagHistoryVO.getOriginExchangeOffice());
		query.setParameter(++idx, mailbagHistoryVO.getDestinationExchangeOffice());
		query.setParameter(++idx, mailbagHistoryVO.getMailCategoryCode());
		query.setParameter(++idx, mailbagHistoryVO.getMailSubclass());
		query.setParameter(++idx, mailbagHistoryVO.getYear());
		query.setParameter(++idx, mailbagHistoryVO.getDsn());
			
		log.exiting("MailTrackingDefaultsObjectDAO", "findMailResditFileLog");
		return query.getResultList();
	}
	/**
	 * @author A-5526 for CRQ ICRD-118163
	 * @throws SystemException
	 */
	public Collection<MailResdit> findAllResditDetails(
			String companyCode, long mailbagId)
			throws SystemException {
		log.entering("MailTrackingDefaultsObjectDAO","findAllResditDetails");
		int index = 0;
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				FIND_ALL_RESDITS);
		ObjectQuery query;
		try {
			query = getQueryManager().createQuery(baseQuery);
			query.setParameter(++index, companyCode);
			query.setParameter(++index, mailbagId);
			if (query.getResultList() != null) {
				return (Collection<MailResdit>) query.getResultList();
			}
		} catch (PersistenceException e) {
			// To be reviewed Auto-generated catch block
			e.getErrorCode();
		} catch (NoResultException e) {
			log.log(Log.FINE,
					" NO Resdit FOUND FOR SPECIFIEDINPUT : ");
		}
		return null;
	}
	
	
	/**
	 *  @author a-2572
	 *  @param mailResditFileLogVO	  
	 *  @return
	 *  @throws SystemException
	 *  @throws PersistenceException
	 */
	public Collection<MailResditFileLog> findMailResditFileLog (MailResditFileLogVO mailResditFileLogVO)
	throws PersistenceException,SystemException{
		log.entering("MailTrackingDefaultsObjectDAO","findMailResditFileLog");
		 ObjectQuery query = null;
		query = getQueryManager().createNamedQuery(FIND_ALL_MAILRESDITFILELOG);
		int idx = 0;
		query.setParameter(++idx, mailResditFileLogVO.getCompanyCode());
		query.setParameter(++idx, mailResditFileLogVO
				.getInterchangeControlReference());
		query.setParameter(++idx, mailResditFileLogVO.getFileName());
		log.exiting("MailTrackingDefaultsObjectDAO", "findMailResditFileLog");
		return query.getResultList();
	}
	/**
	 *  @author a-6986
	 *  @param companyCode	  
	 *  @return
	 *  @throws SystemException
	 *  @throws PersistenceException
	 */
	public Collection<MailServiceLevel> findServiceLevelDtls(String companyCode)
			throws SystemException,PersistenceException{
		log.entering("MailTrackingDefaultsObjectDAO","findServiceLevelDtls");
		ObjectQuery query = null;
		query = getQueryManager().createNamedQuery(FIND_SERVICE_LEVEL_DETAILS);
		int index = 0;
		query.setParameter(++index, companyCode);
		log.exiting("MailTrackingDefaultsObjectDAO", "findServiceLevelDtls");
		return query.getResultList();
	}

}
