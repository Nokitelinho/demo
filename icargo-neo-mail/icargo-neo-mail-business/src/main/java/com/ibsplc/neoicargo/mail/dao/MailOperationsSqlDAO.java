package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.FlightListingFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportFlightOperationsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightULDVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.feature.searchcontainerfilterquery.SearchContainerFilterQueryFeature;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.*;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.SqlType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.ibsplc.neoicargo.mail.dao.constants.MailOperationsSqlDAOConstants.*;
import static com.ibsplc.neoicargo.mail.vo.MailConstantsVO.MAILS_FOR_TRANSHIPMENT_FLIGHT;
import static com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.MAILSTATUS_ACCEPTED;
import static com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.MAILSTATUS_ARRIVED;

/** 
 * @author a-A5991
 */
@Slf4j
public class MailOperationsSqlDAO extends AbstractQueryDAO implements MailOperationsDAO {

	private static final String CLASS_NAME = "MailOperationsSqlDAO";

	/** 
	* @author a-2518
	*/
	private static class SLAMapper implements Mapper<MailActivityDetailVO> {
		private static final String CLASS_NAME = "SLAMapper";

		@Override
		public MailActivityDetailVO map(ResultSet resultSet) throws SQLException {
			return null;
		}
	}

	/** 
	* Method : findMLDCongfigurations Added by : A-5526 on Dec 17, 2015 for CRQ-93584 Used for : Parameters : @param mLDConfigurationFilterVO Parameters : @return Collection<MLDConfigurationVO> Parameters : @throws SystemException Parameters : @throws PersistenceException
	*/
	public Collection<MLDConfigurationVO> findMLDCongfigurations(MLDConfigurationFilterVO mLDConfigurationFilterVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findScannedMailDetails" + " Entering");
		int index = 0;
		Collection<MLDConfigurationVO> mLDConfigurationVOs = null;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MLD_CONFIGURATIONS);
		qry.setParameter(++index, mLDConfigurationFilterVO.getCompanyCode());
		if (mLDConfigurationFilterVO.getAirportCode() != null
				&& !("").equals(mLDConfigurationFilterVO.getAirportCode())) {
			qry.append(" AND CFG.ARPCOD = ?");
			qry.setParameter(++index, mLDConfigurationFilterVO.getAirportCode());
		}
		if (mLDConfigurationFilterVO.getCarrierIdentifier() > 0) {
			qry.append(" AND CFG.CARIDR = ?");
			qry.setParameter(++index, mLDConfigurationFilterVO.getCarrierIdentifier());
		}
		if (mLDConfigurationFilterVO.getMldversion() != null
				&& !("").equals(mLDConfigurationFilterVO.getMldversion())) {
			qry.append(" AND CFG.MLDVER = ?");
			qry.setParameter(++index, mLDConfigurationFilterVO.getMldversion());
		}
		return qry.getResultList(new MLDConfigurationMapper());
	}

	/** 
	* @author A-5991method finds mailsequence number from MailBag table using mailId and companycode
	*/
	public long findMailSequenceNumber(String mailId, String companyCode) {
		log.debug(CLASS_NAME + " : " + "findMailSequenceNumber" + " Entering");
		long mailSeqNumber = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILSEQUENCE_NUMBER_FROM_MAILIDR);
		int indx = 0;
		query.setParameter(++indx, companyCode);
		query.setParameter(++indx, mailId);
		String seqNum = query.getSingleResult(getStringMapper("MALSEQNUM"));
		if (seqNum != null) {
			mailSeqNumber = Long.parseLong(seqNum);
		}
		log.debug(CLASS_NAME + " : " + "findMailSequenceNumber" + " Exiting");
		return mailSeqNumber;
	}

	/** 
	* @author A-10504method checks Screening Details from MALCSGCSDDTL table using mailId and companyCode
	*/
	public int findScreeningDetails(String mailBagId, String companyCode) {
		String result = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_SELECT_COUNT_FROM_MALCSGCSDDTL);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailBagId);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailBagId);
		result = query.getSingleResult(getStringMapper("CMYCOD"));
		if (result != null) {
			return 1;
		} else {
			return 0;
		}
	}

	/** 
	* @author a-1936 This method is used to find out the Mail Bags in theContainers
	* @param containers
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<ContainerDetailsVO> findMailbagsInContainer(Collection<ContainerDetailsVO> containers)
			throws PersistenceException {
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + FIND_MAILBAGS_IN_CONTAINER + " Entering");
		Collection<ContainerDetailsVO> containerForReturn = new ArrayList<ContainerDetailsVO>();
		Query qry = null;
		for (ContainerDetailsVO cont : containers) {
			int idx = 0;
			if (cont.getFlightSequenceNumber() >= 0) {
				if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
					qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FLIGHT_ULD);
				} else {
					qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FLIGHT_BULK);
				}
			} else {
				if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
					qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_CARRIER_ULD);
				} else {
					qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_CARRIER_BULK);
				}
			}
			qry.setParameter(++idx, cont.getCompanyCode());
			qry.setParameter(++idx, cont.getCarrierId());
			qry.setParameter(++idx, cont.getFlightNumber());
			qry.setParameter(++idx, cont.getFlightSequenceNumber());
			qry.setParameter(++idx, cont.getLegSerialNumber());
			qry.setParameter(++idx, cont.getPol());
			qry.setParameter(++idx, cont.getContainerNumber());
			qry.setSensitivity(true);
			List<ContainerDetailsVO> list = qry
					.getResultList(cont.getFlightSequenceNumber() > 0 ? new AcceptedDsnsInFlightMultiMapper()
							: new AcceptedDsnsInCarrierMultiMapper());
			if (list != null && list.size() > 0) {
				containerForReturn.add(list.get(0));
				containerForReturn.stream().forEach(containerDetails -> {
					if (cont.getPou() != null) {
						containerDetails.setTransistPort(cont.getPou());
					}
				});
			}
		}
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + FIND_MAILBAGS_IN_CONTAINER + " Exiting");
		return containerForReturn;
	}

	public Collection<ContainerDetailsVO> findMailbagsInContainerWithoutAcceptance(
			Collection<ContainerDetailsVO> containers) throws PersistenceException {
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + FIND_MAILBAGS_IN_CONTAINER + " Entering");
		Collection<ContainerDetailsVO> containerForReturn = new ArrayList<>();
		Query qry = null;
		for (ContainerDetailsVO cont : containers) {
			int idx = 0;
			if (cont.getFlightSequenceNumber() >= 0) {
				if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
					qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FLIGHT_ULD_WITHOUTACCEPTANCE);
				} else {
					if (MailConstantsVO.MAILOUTBOUND_SCREEN.equals(cont.getFromScreen())) {
						qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FLIGHT_BULK);
					} else {
						qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FLIGHT_BULK_WITHOUTACCEPTANCE);
					}
				}
			} else {
				if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
					qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_CARRIER_ULD);
				} else {
					qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_CARRIER_BULK);
				}
			}
			qry.setParameter(++idx, cont.getCompanyCode());
			qry.setParameter(++idx, cont.getCarrierId());
			qry.setParameter(++idx, cont.getFlightNumber());
			qry.setParameter(++idx, cont.getFlightSequenceNumber());
			qry.setParameter(++idx, cont.getLegSerialNumber());
			if ((!MailConstantsVO.MAILOUTBOUND_SCREEN.equals(cont.getContainerType()))
					&& (!MailConstantsVO.ACCEPTANCE_FLAG.equals(cont.getAcceptedFlag()))
					&& (!MailConstantsVO.ULD_TYPE.equals(cont.getContainerType()))) {
				qry.setParameter(++idx, cont.getPou());
			} else {
				qry.setParameter(++idx, cont.getPol());
			}
			qry.setParameter(++idx, cont.getContainerNumber());
			List<ContainerDetailsVO> list = qry
					.getResultList(cont.getFlightSequenceNumber() > 0 ? new AcceptedDsnsInFlightMultiMapper()
							: new AcceptedDsnsInCarrierMultiMapper());
			if (list != null && list.size() > 0) {
				containerForReturn.add(list.get(0));
			}
		}
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + FIND_MAILBAGS_IN_CONTAINER + " Exiting");
		return containerForReturn;
	}

	/** 
	* @author A-2521For fetching already Stamped flight details for Collection of Mail Events
	*/
	public HashMap<String, Collection<MailResditVO>> findResditFlightDetailsForMailbagEvents(
			Collection<MailResditVO> mailResditVOs) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findResditFlightDetailsForMailbagEvents" + " Entering");
		if (mailResditVOs == null || mailResditVOs.size() <= 0) {
			return null;
		}
		Query query = getQueryManager().createNamedNativeQuery(FIND_MALRDTEVTS_FLIGHTDETAILS);
		MailResditVO mailResditVO = ((ArrayList<MailResditVO>) mailResditVOs).get(0);
		int idx = 0;
		query.setParameter(++idx, mailResditVO.getCompanyCode());
		query.setParameter(++idx, mailResditVO.getEventCode());
		boolean first = true;
		query.append(" AND (MALIDR = ? ");
		for (MailResditVO mailEventVO : mailResditVOs) {
			if (first) {
				first = false;
				query.setParameter(++idx, mailEventVO.getMailId());
			} else {
				query.append("OR MALIDR = ? ");
				query.setParameter(++idx, mailEventVO.getMailId());
			}
		}
		query.append(" ) ");
		query.append(" order by MALIDR,EVTPRT,EVTCOD");
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findResditFlightDetailsForMailbagEvents" + " Exiting");
		return query.getResultList(new MailResditDetailsMultiMapper()).get(0);
	}

	/** 
	* @author A-1739 This method is used to findFlaggedResditSeqNum Modified ByKarthick V .. This method returns the ResditSequence Number though it is just required to check wether that Resdit Exists for that Particular Event since it can serve as a Dual Purpose Check Resdit and also Return the Max Sequence Number if any .. Note::- As if now the Dependant code takes single event code but Multiple Codes can also be specified if required so that the Method handles that too,But it has to be comma Separated ...
	* @param mailResditVO
	* @param isSentCheckNeeded
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public boolean checkResditExists(MailResditVO mailResditVO, boolean isSentCheckNeeded) throws PersistenceException {
		log.debug("MailTrackkingDefaultsSQLDAO" + " : " + "checkResditExists" + " Entering");
		int idx = 0;
		StringBuilder qryBuilder = null;
		Query query = getQueryManager().createNamedNativeQuery(CHECK_RESDIT_EXISTS);
		query.setParameter(++idx, mailResditVO.getCompanyCode());
		query.setParameter(++idx, mailResditVO.getMailSequenceNumber());
		if (!MailConstantsVO.RESDIT_RECEIVED.equals(mailResditVO.getEventCode())) {
			query.append(" AND  MMR.EVTPRT = ?");
			query.setParameter(++idx, mailResditVO.getEventAirport());
		}
		if (mailResditVO.getDependantEventCode() != null && mailResditVO.getDependantEventCode().trim().length() > 0) {
			log.info("THE DEPENDANT EVENT CODES ARE PRESENT");
			query.append(" AND  MMR.EVTCOD IN  ( ?,");
			query.setParameter(++idx, mailResditVO.getEventCode());
			String[] dependantEventCodes = mailResditVO.getDependantEventCode().split(MailConstantsVO.MALCLS_SEP);
			qryBuilder = new StringBuilder();
			for (String dependantCode : dependantEventCodes) {
				qryBuilder.append("?,");
				query.setParameter(++idx, dependantCode);
			}
			qryBuilder.deleteCharAt(qryBuilder.length() - 1);
			qryBuilder.append(" ) ");
			query.append(qryBuilder.toString());
		} else {
			log.info("NO DEPENDANT CODES PRESENT");
			query.append(" AND  MMR.EVTCOD = ? ");
			query.setParameter(++idx, mailResditVO.getEventCode());
		}
		if (isSentCheckNeeded) {
			query.append(" AND MMR.RDTSND = ? ");
			query.setParameter(++idx, mailResditVO.getResditSentFlag());
		}
		log.debug("MailTrackkingDefaultsSQLDAO" + " : " + "checkResditExists" + " Exiting");
		return (query.getSingleResult(getLongMapper("SEQNUM")) > 0 ? true : false);
	}

	/** 
	* @author A-5991
	* @param mailResditVO
	* @param isSentCheckNeeded
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public boolean checkResditExistsFromReassign(MailResditVO mailResditVO, boolean isSentCheckNeeded)
			throws PersistenceException {
		log.debug("MailTrackkingDefaultsSQLDAO" + " : " + "checkResditExistsFromReassign" + " Entering");
		int idx = 0;
		StringBuilder qryBuilder = null;
		Query query = getQueryManager().createNamedNativeQuery(CHECK_RESDIT_EXISTSFROMREAASIGN);
		query.setParameter(++idx, mailResditVO.getCompanyCode());
		query.setParameter(++idx, mailResditVO.getMailSequenceNumber());
		query.setParameter(++idx, mailResditVO.getEventAirport());
		query.setParameter(++idx, mailResditVO.getFlightNumber());
		query.setParameter(++idx, mailResditVO.getFlightSequenceNumber());
		if (mailResditVO.getDependantEventCode() != null && mailResditVO.getDependantEventCode().trim().length() > 0) {
			log.info("THE DEPENDANT EVENT CODES ARE PRESENT");
			query.append(" AND  MMR.EVTCOD IN  ( ?,");
			query.setParameter(++idx, mailResditVO.getEventCode());
			String[] dependantEventCodes = mailResditVO.getDependantEventCode().split(MailConstantsVO.MALCLS_SEP);
			qryBuilder = new StringBuilder();
			for (String dependantCode : dependantEventCodes) {
				qryBuilder.append("?,");
				query.setParameter(++idx, dependantCode);
			}
			qryBuilder.deleteCharAt(qryBuilder.length() - 1);
			qryBuilder.append(" ) ");
			query.append(qryBuilder.toString());
		} else {
			log.info("NO DEPENDANT CODES PRESENT");
			query.append(" AND  MMR.EVTCOD = ? ");
			query.setParameter(++idx, mailResditVO.getEventCode());
		}
		if (isSentCheckNeeded) {
			query.append(" AND MMR.RDTSND = ? ");
			query.setParameter(++idx, mailResditVO.getResditSentFlag());
		}
		log.debug("MailTrackkingDefaultsSQLDAO" + " : " + "checkResditExists" + " Exiting");
		return (query.getSingleResult(getLongMapper("SEQNUM")) > 0 ? true : false);
	}

	/** 
	* @author A-4072For Mail 4 Fetching business time configuration for Arrival Airport //Added for ICRD-63167 moving Cardit Resdit from QF to Base
	*/
	public HashMap<String, String> findPAForMailbags(Collection<MailbagVO> mailbagVOs) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPAForMailbags" + " Entering");
		if (mailbagVOs == null || mailbagVOs.size() <= 0) {
			return null;
		}
		HashMap<String, String> paDetailMap = new HashMap<String, String>();
		Query query = getQueryManager().createNamedNativeQuery(FIND_PA_FORMAILBAGS);
		query.setSensitivity(true);
		int idx = 0;
		boolean first = true;
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			query.append("AND MALIDR IN ( ?");
			for (MailbagVO mailbagVO : mailbagVOs) {
				if (first) {
					first = false;
					query.setParameter(++idx, mailbagVO.getCompanyCode());
					query.setParameter(++idx, mailbagVO.getMailbagId());
				} else {
					query.append(",? ");
					query.setParameter(++idx, mailbagVO.getMailbagId());
				}
			}
			query.append(" ) ");
		}
		paDetailMap = (HashMap<String, String>) query.getResultList(new PADetailsMultiMapper()).get(0);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPAForMailbags" + " Exiting");
		return paDetailMap;
	}

	/** 
	* @param companyCode
	* @param mailbagId
	* @return
	* @throws SystemException
	* @throws PersistenceException*/
	public String findCarditForMailbag(String companyCode, String mailbagId) throws PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(FIND_IS_CARDIT_PRESENT_FOR_MAIL);
		query.setParameter(1, companyCode);
		query.setParameter(2, mailbagId);
		return query.getSingleResult(getStringMapper("CDTKEY"));
	}

	/** 
	* TODO Purpose Feb 5, 2007, A-1739
	* @param companyCode
	* @return
	* @throws SystemException
	* @throws PersistenceException*/
	public Collection<CarditTransportationVO> findCarditTransportationDetails(String companyCode, String carditKey)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCarditTransportForMailbag" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CARDIT_TRTDETAILS);
		int idx = 0;
		qry.setParameter(++idx, companyCode);
		qry.setParameter(++idx, carditKey);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCarditTransportForMailbag" + " Exiting");
		return qry.getResultList(new CarditTransportationMapper());
	}

	/** 
	* @author A-1936 ADDED AS THE PART OF NCA-CR This method is used to findthe CarditDetails for the MailBags..
	* @param companyCode
	* @param mailID
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public MailbagVO findCarditDetailsForAllMailBags(String companyCode, long mailID) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCarditDetailsForAllMailBags" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CARDITDETAILS_FORALLMAILS);
		int idx = 0;
		qry.setParameter(++idx, companyCode);
		qry.setParameter(++idx, mailID);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCarditDetailsForAllMailBags" + " Exiting");
		return qry.getSingleResult(new CarditDetailsForAllMailBagsMapper());
	}

	/** 
	* @author A-5991
	* @param mailbagVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public MailbagVO findTransferFromInfoFromCarditForMailbags(MailbagVO mailbagVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findTransferFromInfoFromCarditForMailbags" + " Entering");
		MailbagVO updatedMailbagVO = new MailbagVO();
		Query query = getQueryManager().createNamedNativeQuery(FIND_TRANSFERFROMINFO_FROM_CARDIT_FORMAILBAGS);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getMailbagId());
		updatedMailbagVO = query.getSingleResult(new FindTransferFromInfoFromCarditMapper());
		if (updatedMailbagVO != null) {
			mailbagVO.setTransferFromCarrier(updatedMailbagVO.getTransferFromCarrier());
			mailbagVO.setFromFlightDate(updatedMailbagVO.getFromFlightDate());
			mailbagVO.setFromFightNumber(updatedMailbagVO.getFromFightNumber());
			mailbagVO.setPou(updatedMailbagVO.getPou());
		}
		return mailbagVO;
	}

	/** 
	* TODO Purpose Feb 5, 2007, A-1739
	* @param companyCode
	* @param carrierId
	* @param txnId
	* @return
	* @throws SystemException
	* @throws PersistenceException*/
	public ResditTransactionDetailVO findResditConfurationForTxn(String companyCode, int carrierId, String txnId)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findResditConfurationForTxn" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_RESDITCONFIG_TXN);
		int idx = 0;
		qry.setParameter(++idx, companyCode);
		qry.setParameter(++idx, carrierId);
		qry.setParameter(++idx, txnId);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findResditConfurationForTxn" + " Exiting");
		return qry.getSingleResult(new ResditConfigurationMapper());
	}

	/** 
	* @author A-2553
	* @param companyCode
	* @param mailbagId
	* @return
	* @throws SystemException
	*/
	public MailbagVO findExistingMailbags(String companyCode, long mailbagId) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findExistingMailbags" + " Entering");
		MailbagVO mailbagVO = null;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_EXISTING_MAILBAGS);
		int index = 0;
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, mailbagId);
		List<MailbagVO> mailbagVOs = qry.getResultList(new ExistingMailbagsMapper());
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			mailbagVO = mailbagVOs.get(0);
		}
		return mailbagVO;
	}

	/** 
	* @author a-2518
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public String findPostalAuthorityCode(String companyCode, String officeOfExchange) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPostalAuthorityCode" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_POA_CODE);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, officeOfExchange);
		Mapper<String> stringMapper = getStringMapper("POACOD");
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPostalAuthorityCode" + " Exiting");
		return query.getSingleResult(stringMapper);
	}

	/** 
	* @author a-2518
	* @param companyCode
	* @param gpaCode
	* @param origin
	* @param destination
	* @param mailCategory
	* @param activity
	* @param scanDate
	* @return MailActivityDetailVO
	* @throws SystemException
	* @throws PersistenceException
	*/
	public MailActivityDetailVO findServiceTimeAndSLAId(String companyCode, String gpaCode, String origin,
			String destination, String mailCategory, String activity, ZonedDateTime scanDate)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findServiceTimeAndSLAId" + " Entering");
		Query query = null;
		MailActivityDetailVO mailActivityDetailVo = new MailActivityDetailVO();
		if (MAILSTATUS_ACCEPTED.equals(activity)) {
			query = getQueryManager().createNamedNativeQuery(FIND_SLAIDR_ACCPTANCE_TO_DEPARTURE);
		} else if (MAILSTATUS_ARRIVED.equals(activity)) {
			query = getQueryManager().createNamedNativeQuery(FIND_SLAIDR_ARRIVAL_TO_DELIVERY);
		}
		int index = 0;
		query.setParameter(++index, scanDate);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, gpaCode);
		query.setParameter(++index, origin);
		query.setParameter(++index, destination);
		query.setParameter(++index, mailCategory);
		Collection<MailActivityDetailVO> mailActivityDetailVos = query.getResultList(new SLAMapper());
		for (MailActivityDetailVO slaDetails : mailActivityDetailVos) {
			mailActivityDetailVo.setServiceTime(slaDetails.getServiceTime());
			mailActivityDetailVo.setSlaIdentifier(slaDetails.getSlaIdentifier());
			break;
		}
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findServiceTimeAndSLAId" + " Exiting");
		return mailActivityDetailVo;
	}

	/** 
	* @author A-3227findCityForOfficeOfExchange
	* @param companyCode
	* @param officeOfExchanges
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public HashMap<String, String> findCityForOfficeOfExchange(String companyCode, Collection<String> officeOfExchanges)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCityForOfficeOfExchange" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_CITY_FOR_EXCHANGE_OFFICES);
		int idx = 0;
		boolean first = true;
		HashMap<String, String> cityForOE = new HashMap<String, String>();
		query.setParameter(++idx, companyCode);
		if (officeOfExchanges != null && officeOfExchanges.size() > 0) {
			query.append(" AND EXGOFCCOD IN ( ?");
			for (String officeOfExchange : officeOfExchanges) {
				if (officeOfExchange != null) {
					if (first) {
						query.setParameter(++idx, officeOfExchange);
						first = false;
					} else {
						query.append(",? ");
						query.setParameter(++idx, officeOfExchange);
					}
				}
			}
			query.append(" ) ");
		}
		return (HashMap<String, String>) query.getResultList(new CityForOEMapper()).get(0);
	}

	/** 
	* CityForOEMapper
	* @author A-3227
	*/
	private static class CityForOEMapper implements MultiMapper<HashMap<String, String>> {
		private static final String CLASS_NAME = "CityForOEMapper";
		private List<HashMap<String, String>> cityForOEMap = new ArrayList<HashMap<String, String>>();

		@Override
		public List<HashMap<String, String>> map(ResultSet resultSet) throws SQLException {
			return null;
		}
	}

	/** 
	* @author A-3227This method fetches the latest Container Assignment irrespective of the PORT to which it is assigned. This to know the current assignment of the Container.
	* @param containerNumber
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public ContainerAssignmentVO findLatestContainerAssignment(String companyCode, String containerNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findLatestContainerAssignment" + " Entering");
		ContainerAssignmentVO containerAssignMentVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_LATEST_CONTAINER_ASSIGNMENT);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setSensitivity(true);
		containerAssignMentVO = qry.getSingleResult(new ContainerAssignmentMapper());
		log.debug(CLASS_NAME + " : " + "findLatestContainerAssignment" + " Exiting");
		return containerAssignMentVO;
	}

	/** 
	* @author a-1936 This method Checks whether the container is alreadyassigned to a flight/destn from the current airport
	* @param companyCode
	* @param containerNumber
	* @param pol
	* @throws SystemException
	* @throws PersistenceException
	*/
	public ContainerAssignmentVO findContainerAssignment(String companyCode, String containerNumber, String pol)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findContainerAssignment" + " Entering");
		ContainerAssignmentVO containerAssignMentVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CONTAINER_ASSIGNMENT);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, pol);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, pol);
		qry.setSensitivity(true);
		containerAssignMentVO = qry.getSingleResult(new ContainerAssignmentMapper());
		return containerAssignMentVO;
	}

	/** 
	* @author a-1876 This method is used to list the PartnerCarriers.
	* @param companyCode
	* @param ownCarrierCode
	* @param airportCode
	* @return Collection<PartnerCarrierVO>
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<PartnerCarrierVO> findAllPartnerCarriers(String companyCode, String ownCarrierCode,
			String airportCode) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findAllPartnerCarriers" + " Entering");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_PARTNERCARRIERS);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, ownCarrierCode);
		qry.setParameter(++index, airportCode);
		return qry.getResultList(new PartnerCarrierMapper());
	}


	public Collection<MailRdtMasterVO> findRdtMasterDetails(RdtMasterFilterVO filterVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findRdtMasterDetails" + " Entering");
		int index = 0;
		String[] airportCodes = null;
		Query qry = getQueryManager().createNamedNativeQuery(LIST_RDTDETAILS);
		qry.setParameter(++index, filterVO.getCompanyCode());
		log.debug("" + "airportCodes " + " " + airportCodes);
		log.debug("" + "Co terminus query " + " " + qry);
		if ((filterVO.getAirportCodes() != null && !(("").equals(filterVO.getAirportCodes())))
				|| (filterVO.getOriginAirportCode() != null && !(("").equals(filterVO.getOriginAirportCode())))) {
			qry = qry.append("AND");
		}
		if (filterVO.getAirportCodes() != null && !(("").equals(filterVO.getAirportCodes()))) {
			qry = qry.append("(DSTARPCOD=?");
			qry.setParameter(++index, filterVO.getAirportCodes());
		}
		if (filterVO.getOriginAirportCode() != null && !(("").equals(filterVO.getOriginAirportCode()))) {
			qry = qry.append("OR ORGARPCOD=?");
			qry.setParameter(++index, filterVO.getOriginAirportCode());
		}
		if ((filterVO.getAirportCodes() != null && !(("").equals(filterVO.getAirportCodes())))
				|| (filterVO.getOriginAirportCode() != null && !(("").equals(filterVO.getOriginAirportCode())))) {
			qry = qry.append(")");
		}
		if (filterVO.getMailType() != null && filterVO.getMailType().trim().length() > 0) {
			qry = qry.append("AND MALTYP=?");
			qry.setParameter(++index, filterVO.getMailType());
		}
		if (filterVO.getGpaCode() != null && filterVO.getGpaCode().trim().length() > 0) {
			qry = qry.append("AND GPACOD=?");
			qry.setParameter(++index, filterVO.getGpaCode());
		}
		if (filterVO.getMailClass() != null && filterVO.getMailClass().trim().length() > 0) {
			qry = qry.append("AND MALCLS=?");
			qry.setParameter(++index, filterVO.getMailClass());
		}
		log.debug("" + "Co terminus query " + " " + qry);
		Collection<MailRdtMasterVO> mailRdtMasterVOs = qry.getResultList(new MailRdtMasterMapper());
		return mailRdtMasterVOs;
	}

	/** 
	* @author A-2037 This method is used to find the History of a Mailbag
	* @param companyCode
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<MailbagHistoryVO> findMailbagHistories(String companyCode, String mailBagId,
			long mailSequenceNumber, String mldMsgGenerateFlag) {
		log.debug(CLASS_NAME + " : " + "findMailbagHistories" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGHISTORIES);
		int index = 0;
		if (mldMsgGenerateFlag != null && "Y".equalsIgnoreCase(mldMsgGenerateFlag)) {
			generateQueryToFetchMailbagHistoryWithMLDetails(query, companyCode, mailBagId, mailSequenceNumber, index);
		} else {
			query.setParameter(++index, companyCode);
			if (mailSequenceNumber != 0l) {
				query.append(" AND MST.MALSEQNUM = ? ORDER BY HIS.UTCSCNDAT ");
				query.setParameter(++index, mailSequenceNumber);
			} else {
				if (mailBagId != null && !mailBagId.isEmpty()) {
					query.append(" AND MST.MALIDR = ? ORDER BY HIS.UTCSCNDAT ");
					query.setParameter(++index, mailBagId);
				}
			}
		}
		return query.getResultList(new MailbagHistoryMapper());
	}

	public void generateQueryToFetchMailbagHistoryWithMLDetails(Query query, String companyCode, String mailBagId,
			long mailSequenceNumber, int index) {
		String malseqnum = "AND MST.MALSEQNUM = ?";
		String malidr = "AND MST.MALIDR = ?";
		Query queryMLD = getQueryManager().createNamedNativeQuery(FIND_MAILBAG_MLDDETAILS);
		boolean malseqExist = false;
		boolean mailBagIdExist = false;
		if (mailSequenceNumber != 0l) {
			malseqExist = true;
		}
		if (mailBagId != null && !mailBagId.isEmpty()) {
			mailBagIdExist = true;
		}
		if (malseqExist) {
			query.append(malseqnum);
			queryMLD.append(malseqnum);
		}
		if (mailBagIdExist) {
			query.append(malidr);
			queryMLD.append(malidr);
		}
		query.append(" UNION ALL ( ");
		queryMLD.append(" ) order by UTCSCNDAT ");
		query.combine(queryMLD);
		query.setParameter(++index, companyCode);
		if (malseqExist) {
			query.setParameter(++index, mailSequenceNumber);
		}
		if (mailBagIdExist) {
			query.setParameter(++index, mailBagId);
		}
		query.setParameter(++index, companyCode);
		if (malseqExist) {
			query.setParameter(++index, mailSequenceNumber);
		}
		if (mailBagIdExist) {
			query.setParameter(++index, mailBagId);
		}
	}

	/** 
	* TODO Purpose Oct 6, 2006, a-1739
	* @param mailbagVO
	* @return
	* @throws SystemException
	* @throws PersistenceException*/
	public MailbagVO findMailbagDetailsForUpload(MailbagVO mailbagVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagDetailsForUpload" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILDETAILS_FOR_UPLOAD);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getMailbagId());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagDetailsForUpload" + " Exiting");
		if (mailbagVO.getMailSource() != null && mailbagVO.getMailSource().startsWith(MailConstantsVO.SCAN)) {
			query.append(" ORDER BY LEGSERNUM DESC ");
		}
		return query.getSingleResult(new MailbagPresentMapper());
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findAirportCityForMLD(java.lang.String, java.lang.String) Added by 			: A-4803 on 14-Nov-2014 Used for 	:	finding airport city for MLD Parameters	:	@param companyCode Parameters	:	@param destination Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	public String findAirportCityForMLD(String companyCode, String destination) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAirportCityForMLD" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_AIRPORTCITYFORMLD);
		query.setParameter(++index, destination);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, destination);
		query.setParameter(++index, companyCode);
		String airportCode = query.getSingleResult(getStringMapper("ARPCOD"));
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAirportCityForMLD" + " Entering");
		return airportCode;
	}

	/** 
	* @author A-5991
	* @param flightAssignedContainerVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public int findNumberOfBarrowsPresentinFlightorCarrier(ContainerVO flightAssignedContainerVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "numberOfBarrowsPresent" + " Entering");
		int index = 0;
		int numberOfBarrows = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_NUMBER_OF_BARROWS_PRESENT_IN_FLIGHT_OR_CARRIER);
		qry.setParameter(++index, flightAssignedContainerVO.getCarrierId());
		qry.setParameter(++index, flightAssignedContainerVO.getFlightNumber());
		qry.setParameter(++index, flightAssignedContainerVO.getFlightSequenceNumber());
		qry.setParameter(++index, flightAssignedContainerVO.getAssignedPort());
		if (flightAssignedContainerVO.getPou() != null) {
			qry.setParameter(++index, flightAssignedContainerVO.getPou());
		} else {
			qry.setParameter(++index, flightAssignedContainerVO.getFinalDestination());
		}
		return qry.getSingleResult(getIntMapper("COUNT"));
	}

	/** 
	* Finds the arrival details of a ULD if any Sep 12, 2007, a-1739
	* @param containerVO
	* @return
	* @throws SystemException
	* @throws PersistenceException*/
	public ContainerAssignmentVO findArrivalDetailsForULD(ContainerVO containerVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findArrivalDetailsForULD" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_ULD_ARRIVAL_DTLS);
		int idx = 0;
		query.setParameter(++idx, containerVO.getCompanyCode());
		query.setParameter(++idx, containerVO.getContainerNumber());
		query.setParameter(++idx, containerVO.getAssignedPort());
		ContainerAssignmentVO contAsgVO = query.getSingleResult(new ContainerAssignmentMapper());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findArrivalDetailsForULD" + " Exiting");
		return contAsgVO;
	}

	/** 
	* Sep 12, 2007, a-1739
	* @param mailbagVO
	* @return
	* @throws SystemException*/
	public MailbagVO findArrivalDetailsForMailbag(MailbagVO mailbagVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findArrivalDetailsForMailbag" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAG_ARRIVAL_DTLS);
		int idx = 0;
		qry.setParameter(++idx, mailbagVO.getCompanyCode());
		qry.setParameter(++idx, mailbagVO.getMailbagId());
		qry.setParameter(++idx, mailbagVO.getScannedPort());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findArrivalDetailsForMailbag" + " Exiting");
		return qry.getSingleResult(new MailbagMapper());
	}

	/** 
	* @author A-2553
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public String findMailType(MailbagVO mailbagVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findPltModeOfDSN" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAIL_TYPE);
		int index = 0;
		if (mailbagVO != null) {
			qry.setParameter(++index, mailbagVO.getCompanyCode());
			qry.setParameter(++index, mailbagVO.getMailSequenceNumber());
		}
		return qry.getSingleResult(getStringMapper("MALTYP"));
	}

	/** 
	* @author a-1883
	* @param consignmentDocumentVO
	* @return ConsignmentDocumentVO
	* @throws SystemException
	* @throws PersistenceException
	*/
	public ConsignmentDocumentVO checkConsignmentDocumentExists(ConsignmentDocumentVO consignmentDocumentVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "checkConsignmentDocumentExists" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(CHECK_CONSIGNMENT_DOCUMENT_EXISTS);
		query.setParameter(1, consignmentDocumentVO.getCompanyCode());
		query.setParameter(2, consignmentDocumentVO.getConsignmentNumber());
		query.setParameter(3, consignmentDocumentVO.getPaCode());
		query.setParameter(4, consignmentDocumentVO.getConsignmentDate().toLocalDateTime());
		return query.getSingleResult(new ConsignmentDocumentExistsMapper());
	}

	/** 
	* This method finds mail sequence number
	* @param mailInConsignmentVO
	* @return int
	* @throws SystemException
	* @throws PersistenceException
	*/
	public int findMailSequenceNumber(MailInConsignmentVO mailInConsignmentVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailSequenceNumber" + " Entering");
		int mailSeqNumber = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAIL_SEQUENCE_NUMBER);
		int indx = 0;
		query.setParameter(++indx, mailInConsignmentVO.getCompanyCode());
		query.setParameter(++indx, mailInConsignmentVO.getConsignmentNumber());
		query.setParameter(++indx, mailInConsignmentVO.getPaCode());
		query.setParameter(++indx, mailInConsignmentVO.getConsignmentSequenceNumber());
		String seqNum = query.getSingleResult(getStringMapper("MALSEQNUM"));
		if (seqNum != null) {
			mailSeqNumber = Integer.parseInt(seqNum);
		}
		log.debug(CLASS_NAME + " : " + "findMailSequenceNumber" + " Exiting");
		return mailSeqNumber;
	}

	@Override
	public Collection<MailSubClassVO> findMailSubClassCodes(String companyCode, String subclassCode)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailSubClassCodes" + " Entering");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILSUBCLASSCODES);
		qry.setParameter(++index, companyCode);
		if (subclassCode != null && subclassCode.trim().length() > 0) {
			subclassCode = subclassCode.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE)
					+ MailConstantsVO.PERCENTAGE;
			qry.append("AND MTK.SUBCLSCOD LIKE  ? ");
			qry.setParameter(++index, subclassCode);
		}
		return qry.getResultList(new MailSubClassCodeMapper());
	}

	/** 
	* @param mailbagVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public boolean isMailbagAlreadyArrived(MailbagVO mailbagVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "isMailbagAlreadyArrived" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(IS_MAILBAG_ALREADY_ARRIVED);
		qry.setParameter(1, mailbagVO.getCompanyCode());
		qry.setParameter(2, mailbagVO.getMailbagId());
		qry.setParameter(3, mailbagVO.getScannedPort());
		qry.setParameter(4, mailbagVO.getCompanyCode());
		qry.append(" AND MALMST.MALSTA IN ('ARR','ASG') ");
		Mapper<String> stringMapper = getStringMapper("FLG");
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "isMailbagAlreadyArrived" + " Exiting");
		if ((qry.getSingleResult(stringMapper)) != null) {
			return true;
		}
		return false;
	}

	/** 
	* @author A-2037 This method is used to get the consignment details formailbag
	* @param companyCode
	* @param mailId
	* @param airportCode
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public MailInConsignmentVO findConsignmentDetailsForMailbag(String companyCode, String mailId, String airportCode)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findConsignmentDetailsForMailbag" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_DETAILS_FOR_MAILBAG);
		query.setSensitivity(true);
		query.setParameter(++index, companyCode);
		query.append(" AND MALMST.MALIDR        = ? ");
		query.setParameter(++index, mailId);
		return query.getSingleResult(new ConsignmentDetailsMapper());
	}

	/** 
	* @author A-2037 This method is used to find Local PAs
	* @param companyCode
	* @param countryCode
	* @return Collection<PostalAdministrationVO>
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<PostalAdministrationVO> findLocalPAs(String companyCode, String countryCode)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findLocalPAs" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_LOCALPAS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, countryCode);
		return query.getResultList(new LocalPAMapper());
	}

	/** 
	* @author A-5991
	*/
	public long findResditSequenceNumber(MailResditVO mailResditVO) throws PersistenceException {
		log.debug("MailTrackkingDefaultsSQLDAO" + " : " + "findResditSequenceNumber" + " Entering");
		int idx = 0;
		long sequenceNumber = 0;
		StringBuilder qryBuilder = null;
		Query query = getQueryManager().createNamedNativeQuery(CHECK_RESDIT_EXISTS);
		query.setParameter(++idx, mailResditVO.getCompanyCode());
		query.setParameter(++idx, mailResditVO.getMailSequenceNumber());
		if (mailResditVO.getEventCode() != null) {
			query.append(" AND  MMR.EVTCOD = ? ");
			query.setParameter(++idx, mailResditVO.getEventCode());
		}
		sequenceNumber = query.getSingleResult(getLongMapper("SEQNUM"));
		log.debug("MailTrackkingDefaultsSQLDAO" + " : " + "findResditSequenceNumber" + " Exiting");
		return sequenceNumber;
	}

	/** 
	* @author A-5160
	*/
	
	/** 
	* Method		:	MailTrackingDefaultsSqlDAO.getWhereClause Added by 	:	A-4803 on 12-May-2015 Used for 	: Parameters	:	@param sccCodes Parameters	:	@return Return type	: 	String
	*/
	private String getWhereClause(String[] sccCodes) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("('");
		for (String code : sccCodes) {
			buffer.append(code).append("','");
		}
		int len = buffer.length();
		return buffer.toString().substring(0, len - 3).trim() + "'";
	}

	private String getEnhancedWhereClause(String[] sccCodes, int offset, String column) {
		StringBuilder buffer = new StringBuilder();
		int startIndex = 0;
		int limit = offset;
		buffer.append(" AND ( ").append(column).append(" IN ('");
		for (int i = startIndex; i < limit; i++) {
			if (i == limit - 1) {
				buffer = new StringBuilder(buffer.substring(0, buffer.length() - 3).trim()).append("'");
				buffer.append(") OR ").append(column).append(" IN ('");
				startIndex = limit - 1;
				limit = offset + limit;
				if (limit > sccCodes.length) {
					limit = sccCodes.length;
				}
			}
			buffer.append(sccCodes[i]).append("','");
		}
		return buffer.toString().substring(0, buffer.length() - 3).trim() + "')";
	}

	public Collection<MailbagVO> findMailBagForDespatch(MailbagVO mailbagVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAWBDetailsForFlight" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FORDESPATCH);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getScannedPort());
		query.setParameter(++idx, mailbagVO.getDespatchId());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAWBDetailsForFlight" + " Exiting");
		return query.getResultList(new MailbagForInventoryMapper());
	}

	/** 
	* Method	    :	findUpuCodeNameForPA Added by 	:   A-5526 on Jun 24, 2016 for CRQ-103713 Used for 	:   findUpuCodeNameForPA Parameters	:	@param companyCode,paCode Parameters	:	@return String Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	public String findUpuCodeNameForPA(String companyCode, String paCode) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findUpuCodeName" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_UPUCODE_NAME_FOR_PA);
		query.setParameter(1, companyCode);
		query.setParameter(2, paCode);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findUpuCodeName" + " Exiting");
		return query.getSingleResult(getStringMapper("UPUCODNAME"));
	}

	/** 
	*/
	public String findCityForOfficeOfExchange(String companyCode, String ExchangeOfficeCode)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCityForOfficeOfExchange" + " Entering");
		String cityCode = "";
		Query query = getQueryManager().createNamedNativeQuery(FIND_CITY_CODE_FOR_OFFICEOFEXCHANGE);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, ExchangeOfficeCode);
		cityCode = query.getSingleResult(getStringMapper("CTYCOD"));
		log.debug("cityCode::" + cityCode);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCityForOfficeOfExchange" + " Exiting");
		return cityCode;
	}

	public HashMap<Long, Collection<MailbagHistoryVO>> findMailbagHistoriesMap(String companyCode, long[] malseqnum)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailbagHistories;" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGHISTORIES);
		query.setParameter(++index, companyCode);
		StringBuilder mailbagId = new StringBuilder("");
		for (long seqNum : malseqnum) {
			mailbagId.append(seqNum).append(",");
		}
		String[] mailbags = mailbagId.toString().split(",");
		String joinQuery = null;
		if (mailbags.length < 999) {
			joinQuery = getWhereClause(mailbags);
			query.append(" AND MST.MALSEQNUM IN ");
		} else {
			joinQuery = getEnhancedWhereClause(mailbags, 999, "MST.MALSEQNUM");
		}
		query.append(joinQuery).append(") ORDER BY HIS.UTCSCNDAT");
		return query.getResultList(new MailbagHistoryMultiMapper()).get(0);
	}

	/** 
	* @author A-7371for ICRD-264253
	*/
	private static class AWBAttachedReassignMailDetailsMapper implements MultiMapper<MailbagVO> {
		@Override
		public List<MailbagVO> map(ResultSet resultSet) throws SQLException {
			return null;
		}
	}

	/** 
	*/
	public String findMailboxId(MailResditVO resditVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailboxId" + " Entering");
		String mailbox = "";
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBOX);
		int indx = 0;
		query.setParameter(++indx, resditVO.getCompanyCode());
		query.setParameter(++indx, resditVO.getMailId());
		mailbox = query.getSingleResult(getStringMapper("MALBOX"));
		return mailbox;
	}

	/** 
	* @author A-6986
	* @param mailServiceLevelVO
	* @return
	*/
	public String findMailServiceLevelForIntPA(MailServiceLevelVO mailServiceLevelVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailServiceLevelForInternational" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAIL_SERVICE_LEVEL);
		query.setParameter(++index, mailServiceLevelVO.getCompanyCode());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.append(" UNION ALL ");
		query.append("SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCTG = ? "
				+ "AND (MALCLS = ? AND MALSUBCLS = '-') "
				+ "AND NOT EXISTS (SELECT 1 FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCTG = ? AND MALSUBCLS = ? AND MALCLS = ?)");
		query.append(" UNION ALL ");
		query.append(
				"SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD  = ? AND MALCTG    = '-' AND MALCLS  = ? AND MALSUBCLS = ? "
						+ "AND NOT EXISTS (SELECT 1 FROM MALSRVLVLMAP WHERE POACOD  = ? AND MALCTG    = ? "
						+ "AND ((MALCLS = ? and MALSUBCLS = ?) OR (MALCLS  = ? AND MALSUBCLS = '-')))");
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		return query.getSingleResult(getStringMapper("MALSRVLVL"));
	}

	/** 
	* @author A-6986
	* @param mailServiceLevelVO
	* @return
	*/
	public String findMailServiceLevelForDomPA(MailServiceLevelVO mailServiceLevelVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailServiceLevelForDomestic" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAIL_SERVICE_LEVEL);
		query.setParameter(++index, mailServiceLevelVO.getCompanyCode());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.append(" UNION ALL ");
		query.append("SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCLS = ? "
				+ "AND ((MALCTG    = ? AND MALSUBCLS = '-') OR (MALCTG    = '-' AND MALSUBCLS = ?))"
				+ "AND NOT EXISTS (SELECT 1 FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCLS = ? AND MALSUBCLS = ? AND MALCTG    = ?)");
		query.append(" UNION ALL ");
		query.append("SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCLS = ? "
				+ "AND MALCTG    = '-' AND MALSUBCLS = '-' "
				+ "AND NOT EXISTS (SELECT 1 FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCLS = ? AND ((MALCTG    = ? AND MALSUBCLS = ?) "
				+ "OR (MALCTG    = ? AND MALSUBCLS = '-') OR (MALCTG    = '-' AND MALSUBCLS = ?)) )");
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		return query.getSingleResult(getStringMapper("MALSRVLVL"));
	}

	/** 
	* @author A-6986
	* @return
	*/
	public Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO, int pageNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailHandoverDetails" + " Entering");
		StringBuffer masterQuery = new StringBuffer();
		int pagesize = mailHandoverFilterVO.getDefaultPageSize();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_MAIL_HANDOVER_DETAILS);
		masterQuery.append(queryString);
		PageableNativeQuery<MailHandoverVO> pgNativeQuery = new PageableNativeQuery<MailHandoverVO>(pagesize, 0,
				masterQuery.toString(), new MailHandoverMapper(),PersistenceController.getEntityManager().currentSession());
		int index = 0;
		pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getCompanyCode());
		if (mailHandoverFilterVO.getGpaCode() != null && !("").equals(mailHandoverFilterVO.getGpaCode())) {
			pgNativeQuery.append(" AND GPACOD=?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getGpaCode());
		}
		if (mailHandoverFilterVO.getAirportCode() != null && !("").equals(mailHandoverFilterVO.getAirportCode())) {
			pgNativeQuery.append(" AND ARPCOD = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getAirportCode());
		}
		if (mailHandoverFilterVO.getMailClass() != null && !("").equals(mailHandoverFilterVO.getMailClass())) {
			pgNativeQuery.append(" AND MALCLS = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getMailClass());
		}
		if (mailHandoverFilterVO.getMailSubClass() != null && !("").equals(mailHandoverFilterVO.getMailSubClass())) {
			pgNativeQuery.append(" AND MALSUBCLS = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getMailSubClass());
		}
		if (mailHandoverFilterVO.getExchangeOffice() != null
				&& !("").equals(mailHandoverFilterVO.getExchangeOffice())) {
			pgNativeQuery.append(" AND EXGOFC = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getExchangeOffice());
		}
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(pageNumber);
	}

	/** 
	* @author A-7371
	*/
	public Timestamp fetchSegmentSTA(MailbagVO mailbagVO) throws PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(FIND_FLIGHT_STA);
		int indx = 0;
		query.setParameter(++indx, mailbagVO.getCompanyCode());
		query.setParameter(++indx, mailbagVO.getFlightNumber());
		query.setParameter(++indx, mailbagVO.getCarrierId());
		query.setParameter(++indx, mailbagVO.getFlightSequenceNumber());
		query.setParameter(++indx, mailbagVO.getLegSerialNumber());
		Timestamp staDate = query.getSingleResult(getTimestampMapper("STA"));
		return staDate;
	}

	/** 
	* @author A-7371
	*/
	public String fetchRDTOffset(MailbagVO mailbagVO, String paCodDom) throws PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(FIND_RDTOFFSET);
		int indx = 0;
		query.setParameter(++indx, mailbagVO.getCompanyCode());
		query.setParameter(++indx, mailbagVO.getScannedPort());
		if (paCodDom.equals(mailbagVO.getPaCode())) {
			query.setParameter(++indx, MailConstantsVO.MALTYP_DOMESTIC);
			if (mailbagVO.getMailServiceLevel() != null && mailbagVO.getMailServiceLevel().trim().length() > 0) {
				query.append(" AND MALSRVLVL=? ");
				query.setParameter(++indx, mailbagVO.getMailServiceLevel());
			}
		} else {
			query.setParameter(++indx, MailConstantsVO.MALTYP_INTERNATIONAL);
			query.append(" AND GPACOD  =? ");
			query.setParameter(++indx, mailbagVO.getPaCode());
		}
		if (mailbagVO.getConsignmentDate() != null) {
			query.append(QUERY_VALIDITY_CHECK);
			query.setParameter(++indx, mailbagVO.getConsignmentDate().toLocalDateTime());
		}
		String rdtOffset = query.getSingleResult(new MailRDTOffsetMapper());
		return rdtOffset;
	}

	private static class MailRDTOffsetMapper implements Mapper<String> {
		@Override
		public String map(ResultSet resultSet) throws SQLException {
			return null;
		}
	}

	/** 
	* @author A-7871Used for ICRD-240184
	* @param currentAirport
	* @param paCode
	* @return receiveFromTruckEnabled
	* @throws SystemException
	*/
	public String checkReceivedFromTruckEnabled(String currentAirport, String orginAirport, String paCode,
			ZonedDateTime dspDate) {
		String receiveFromTruckEnabled;
		log.debug(CLASS_NAME + " : " + "checkReceivedFromTruckEnabled" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(CHECK_RECIEVEFRMTRUCK_ENABLED);
		query.setParameter(++index, currentAirport);
		query.setParameter(++index, orginAirport);
		query.setParameter(++index, paCode);
		if (dspDate != null) {
			query.append(" AND ?  BETWEEN MTK.VLDFRMDAT AND MTK.VLDTOODAT ");
			query.setParameter(++index, dspDate.toLocalDateTime());
		}
		receiveFromTruckEnabled = query.getSingleResult(getStringMapper("ARPCOD"));
		log.debug(CLASS_NAME + " : " + "checkReceivedFromTruckEnabled" + " Exiting");
		return receiveFromTruckEnabled;
	}

	/** 
	* @author A-8061
	* @param shipmentSummaryVO
	* @param mailFlightSummaryVO
	* @return
	* @throws SystemException
	*/
	public ScannedMailDetailsVO findAWBAttachedMailbags(ShipmentSummaryVO shipmentSummaryVO,
			MailFlightSummaryVO mailFlightSummaryVO) {
		int index = 0;
		Query query = null;
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		query = getQueryManager().createNamedNativeQuery(FIND_AWBATTACHED_MAILS);
		query.setParameter(++index, mailFlightSummaryVO.getCompanyCode());
		query.setParameter(++index, shipmentSummaryVO.getOwnerId());
		query.setParameter(++index, shipmentSummaryVO.getMasterDocumentNumber());
		query.setParameter(++index, shipmentSummaryVO.getSequenceNumber());
		query.setParameter(++index, shipmentSummaryVO.getDuplicateNumber());
		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs = query
				.getResultList(new AWBAttachedMailbagDetailsMapper());
		if (scannedMailDetailsVOs != null && !scannedMailDetailsVOs.isEmpty()) {
			scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>) scannedMailDetailsVOs).get(0);
		}
		return scannedMailDetailsVO;
	}

	/** 
	* @author A-8464
	* @return serviceStandard
	* @throws SystemException
	* @throws PersistenceException
	*/
	public int findServiceStandard(MailbagVO mailbagVo) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findServiceStandard" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_SERVICE_STANDARD);
		int index = 0;
		if (mailbagVo.getCompanyCode() != null && mailbagVo.getCompanyCode().trim().length() > 0) {
			qry.setParameter(++index, mailbagVo.getCompanyCode());
		}
		if (mailbagVo.getPaCode() != null && mailbagVo.getPaCode().trim().length() > 0) {
			qry.setParameter(++index, mailbagVo.getPaCode());
		}
		if (mailbagVo.getOrigin() != null && mailbagVo.getOrigin().trim().length() > 0) {
			qry.setParameter(++index, mailbagVo.getOrigin());
		}
		if (mailbagVo.getDestination() != null && mailbagVo.getDestination().trim().length() > 0) {
			qry.setParameter(++index, mailbagVo.getDestination());
		}
		if (mailbagVo.getMailServiceLevel() != null && mailbagVo.getMailServiceLevel().trim().length() > 0) {
			qry.setParameter(++index, mailbagVo.getMailServiceLevel());
		}
		if (mailbagVo.getConsignmentDate() != null) {
			qry.append("AND  ?  BETWEEN SRVSTDCFG.VLDFRMDAT AND SRVSTDCFG.VLDTOODAT ");
			qry.setParameter(++index, mailbagVo.getConsignmentDate().toLocalDateTime());
		}
		int serviceStandard = 0;
		String serviceStdString = qry.getSingleResult(getStringMapper("SRVSTD"));
		if (serviceStdString != null && serviceStdString.trim().length() > 0) {
			serviceStandard = Integer.parseInt(serviceStdString);
		}
		log.debug(CLASS_NAME + " : " + "findServiceStandard" + " Exiting");
		return serviceStandard;
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findRotingIndex(java.lang.String, java.lang.String) Added by 			: A-7531 on 30-Oct-2018 Used for 	: Parameters	:	@param routeIndex Parameters	:	@param companycode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	@Override
	public Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO) throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		int index = 0;
		Collection<RoutingIndexVO> routingIndexVOs = null;
		String qry = getQueryManager().getNamedNativeQueryString(FIND_ROUTING_INDEX);
		String modifiedStr1 = null;
		String modifiedStr2 = null;
		if (isOracleDataSource()) {
			modifiedStr1 = "AND syspar.parval = arlmst.arlidr";
		} else {
			modifiedStr1 = "AND arlmst.arlidr :: varchar = syspar.parval";
		}
		if (isOracleDataSource()) {
			modifiedStr2 = "and ? BETWEEN idx.pldeffdat-1 and idx.plddisdat";
		} else {
			modifiedStr2 = "and ? BETWEEN date(idx.pldeffdat)-1 and date(idx.plddisdat)";
		}
		qry = String.format(qry, modifiedStr1, modifiedStr2);
		Query qery = getQueryManager().createNativeQuery(qry);
		qery.setParameter(++index, routingIndexVO.getRoutingIndex());
		qery.setParameter(++index, routingIndexVO.getCompanyCode());
		if (routingIndexVO.getScannedDate() == null) {
			ZonedDateTime currentDate = localDateUtil.getLocalDate(null, false);
			routingIndexVO.setScannedDate(currentDate);
		}
		if (isOracleDataSource()) {
			qery.setParameter(++index, routingIndexVO.getScannedDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		} else {
			qery.setParameter(++index,Timestamp.valueOf(routingIndexVO.getScannedDate().toLocalDateTime()));
		}
		routingIndexVOs = qery.getResultList(new RoutingIndexDetailsMapper());
		return routingIndexVOs;
	}

	/** 
	* @author A-8514Added as part of ICRD-229584 starts
	*/
	public MailbagInULDForSegmentVO getManifestInfo(ScannedMailDetailsVO scannedMailDetailsVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "getManifestInfo" + " Entering");
		int index = 0;
		Collection<MailbagVO> mailBagVO;
		mailBagVO = scannedMailDetailsVO.getMailDetails();
		Query query = null;
		MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
		if (mailBagVO != null && mailBagVO.size() > 0) {
			for (MailbagVO mailbag : mailBagVO) {
				if (scannedMailDetailsVO.getFlightNumber() == null || scannedMailDetailsVO.getFlightNumber().equals(""))
					scannedMailDetailsVO.setFlightNumber(mailbag.getFlightNumber());
				if (scannedMailDetailsVO.getFlightSequenceNumber() == 0)
					scannedMailDetailsVO.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
				if (scannedMailDetailsVO.getSegmentSerialNumber() == 0)
					scannedMailDetailsVO.setSegmentSerialNumber(mailbag.getSegmentSerialNumber());
				if (scannedMailDetailsVO.getCarrierId() == 0)
					scannedMailDetailsVO.setCarrierId(mailbag.getCarrierId());
				if (mailbag.getMailSequenceNumber() == 0) {
					Long seqnum = findMailSequenceNumber(mailbag.getMailbagId(), mailbag.getCompanyCode());
					mailbag.setMailSequenceNumber(seqnum);
				}
				query = getQueryManager().createNamedNativeQuery(GET_MAIL_MANIFESTINFO);
				query.setParameter(++index, scannedMailDetailsVO.getCompanyCode());
				query.setParameter(++index, mailbag.getMailSequenceNumber());
				query.setParameter(++index, scannedMailDetailsVO.getAirportCode());
				query.setSensitivity(true);
				mailbagInULDForSegmentVO = query.getSingleResult(new InboundFlightDetailsForAutoArrivalMapper());
				return mailbagInULDForSegmentVO;
			}
		}
		return null;
	}

	/** 
	* @author A-7794
	*/
	@Override
	public String checkScanningWavedDest(MailbagVO mailbagVO) {
		String scanWavedAirport = null;
		Query query = getQueryManager().createNamedNativeQuery(CHECK_SCAN_WAVED_AIRPORT);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getPaCode());
		if (mailbagVO.getDestination() != null) {
			query.setParameter(++idx, mailbagVO.getDestination());
		} else {
			query.setParameter(++idx, mailbagVO.getFinalDestination());
		}
		if (mailbagVO.getConsignmentDate() != null) {
			query.append(" AND ?  BETWEEN VLDFRMDAT AND  VLDTOODAT  ");
			query.setParameter(++idx, mailbagVO.getConsignmentDate().toLocalDateTime());
		}
		scanWavedAirport = query.getSingleResult(getStringMapper("SCNWVDFLG"));
		return scanWavedAirport;
	}

	public static class DeviationMailBagGroupMapper implements Mapper<MailbagVO> {
		@Override
		public MailbagVO map(ResultSet rs) throws SQLException {
			//log.log(Log.INFO, "Entering the DeviationMailBagGroupMapper");
			Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setDestCityDesc(rs.getString("DSTCOD"));
			mailbagVO.setCount(rs.getInt("COUNT"));
			mailbagVO.setAcceptedBags(rs.getInt("SCACNT"));
			try {
				if(Objects.nonNull (mailbagVO.getWeight())) {
					mailbagVO.setWeight(mailbagVO.getWeight().add(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("TOTWGT")))));
				}else{
					mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("TOTWGT"))));
				}
				if(Objects.nonNull (mailbagVO.getAcceptedWeight())) {
					mailbagVO.setAcceptedWeight(mailbagVO.getAcceptedWeight().add(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("SCAWGT")))));
				}else {
					mailbagVO.setAcceptedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("SCAWGT"))));
				}
			}finally{

			}
			mailbagVO.setErrorCode(rs.getString("ERRORCOD"));
			return mailbagVO;
		}
	}

	private static class InvoicPeriodDetailsMapper implements Mapper<USPSPostalCalendarVO> {
		@Override
		public USPSPostalCalendarVO map(ResultSet resultSet) throws SQLException {
			return null;
		}
	}

	/** 
	* Added as part of IASCB-91419
	* @author 215166
	*/
	/** 
	* @author A-8061
	*/
	public String findServiceResponsiveIndicator(MailbagVO mailbagVO) {
		String serviceResponsiveIndicator = null;
		int idx = 0;
		String qery = getQueryManager().getNamedNativeQueryString(FIND_SERVICERESPONSIVEINDICATOR);
		String dynamicQry = null;
		if (isOracleDataSource()) {
			dynamicQry = "PKG_FRMWRK.FUN_CHECK_STRING_COMMON(DAYOFFWEK,1+TRUNC(?)-TRUNC(?, 'IW'),'') > 0";
		} else {
			dynamicQry = " strpos(DAYOFFWEK, to_char(extract(isodow from (to_date (?,'yyyy-mm-dd')) ))) > 0";
		}
		qery = String.format(qery, dynamicQry);
		Query qry = getQueryManager().createNativeQuery(qery);
		qry.setParameter(++idx, mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx, mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx, mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx, mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx, mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx, mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx, mailbagVO.getCompanyCode());
		qry.setParameter(++idx, mailbagVO.getPaCode());
		qry.setParameter(++idx, mailbagVO.getOrigin());
		qry.setParameter(++idx, mailbagVO.getDestination());
		qry.setParameter(++idx, mailbagVO.getConsignmentDate());
		qry.setParameter(++idx, mailbagVO.getConsignmentDate());
		if (isOracleDataSource()) {
			qry.setParameter(++idx, mailbagVO.getConsignmentDate());
		}
		serviceResponsiveIndicator = qry.getSingleResult(getStringMapper("SRVIND"));
		serviceResponsiveIndicator = serviceResponsiveIndicator == null ? "N" : serviceResponsiveIndicator;
		return serviceResponsiveIndicator;
	}

	/** 
	* Added as a part of ICRD-318999. This method is to pick up mailbox id according to the parameters given.
	*/
	public String findMailboxIdFromConfig(MailbagVO mailbagVO) {
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBOXID_FROM_CONFIG);
		query.setParameter(++index, mailbagVO.getOoe());
		query.setParameter(++index, mailbagVO.getDoe());
		query.setParameter(++index, mailbagVO.getOrigin());
		query.setParameter(++index, mailbagVO.getDestination());
		query.setParameter(++index, mailbagVO.getMailCategoryCode());
		query.setParameter(++index, mailbagVO.getMailSubclass());
		String xxResdit = "Y";
		if (null != mailbagVO.getConsignmentNumber()) {
			xxResdit = "N";
		}
		query.setParameter(++index, xxResdit);
		if (mailbagVO.getCarrierCode() == null) {
			query.setParameter(++index, mailbagVO.getCompanyCode());
		} else {
			query.setParameter(++index, mailbagVO.getCarrierCode());
		}
		String mailboxId = query.getSingleResult(getStringMapper("MALBOXID"));
		return mailboxId;
	}

	public String findRoutingDetailsForConsignment(MailbagVO mailbagVO) {
		String routingDetails = null;
		Query query;
		try {
			query = getQueryManager().createNamedNativeQuery(FIND_ROUTING_DETAILS_FOR_CONSIGNMENT);
			int index = 0;
			query.setParameter(++index, mailbagVO.getCompanyCode());
			query.setParameter(++index, mailbagVO.getPaCode());
			query.setParameter(++index, mailbagVO.getConsignmentNumber());
			query.setParameter(++index, mailbagVO.getDestination());
			routingDetails = query.getSingleResult(new RoutingForConsignmentMapper());
		} finally {
		}
		return routingDetails;
	}

	private static class RoutingForConsignmentMapper implements Mapper<String> {
		String routingDetail;

		@Override
		public String map(ResultSet resultSet) throws SQLException {
			return null;
		}
	}

	/** 
	* @author A-5526Added as part of CRQ IASCB-44518
	* @param containerNumber
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public Collection<MailbagVO> findMailbagsFromOALinResditProcessing(String containerNumber, String companyCode)
			throws PersistenceException {
		log.debug(CLASS_NAME + LOG_DELIMITER + " : " + "findMailbagsFromOALinResditProcessing" + " Entering");
		Collection<MailbagVO> mailBagVos = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FROMOAL_INRESDITPROCESING);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		if (isOracleDataSource()) {
			qry.append(
					" AND SCNDAT >= SYSDATE-(COALESCE((SELECT to_number(PARVAL) FROM SHRSYSPAR WHERE PARCOD='mail.operations.maxresditdaysforuldmailbagmapping' AND CMPCOD=?),0)) ");
			qry.setParameter(++index, companyCode);
		} else {
			qry.append(
					" AND SCNDAT >= current_date - (COALESCE( (SELECT (PARVAL)::integer FROM SHRSYSPAR WHERE PARCOD='mail.operations.maxresditdaysforuldmailbagmapping' AND CMPCOD=? ),0)) ");
			qry.setParameter(++index, companyCode);
		}
		mailBagVos = qry.getResultList(new MailBagsForUpliftedResditMapper());
		log.debug(CLASS_NAME + LOG_DELIMITER + " : " + "findMailbagsFromOALinResditProcessing" + " Exiting");
		return mailBagVos;
	}

	/** 
	* @author U-1439Added as part of CRQ IASCB-48353
	* @param containerNumber
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public Collection<MailbagVO> findMailbagsForPABuiltContainerSave(String containerNumber, String companyCode,
			ZonedDateTime fromDate, ZonedDateTime toDate) throws PersistenceException {
		log.debug(CLASS_NAME + LOG_DELIMITER + " : " + "findMailbagsForPABuiltContainerSave" + " Entering");
		Collection<MailbagVO> mailBagVos = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(MAILBAGS_FORPABUILT_CONTAINERSAVE);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		if (isOracleDataSource()) {
			qry.append(" AND CSGMST.CSGDAT BETWEEN TRUNC(?) AND TRUNC(?)  ");
			qry.setParameter(++index, fromDate);
			qry.setParameter(++index, toDate);
		} else {
			qry.append(
					"AND to_Date(TO_CHAR(CSGDAT,'yyyy-MM-dd'),'yyyy-MM-dd')  BETWEEN to_date(?,'yyyy-MM-dd') AND to_date(?,'yyyy-MM-dd')");
			//TODO: Neo to verify below code-refer classics
			qry.setParameter(++index, String.valueOf(fromDate));
			qry.setParameter(++index, String.valueOf(toDate));
		}
		mailBagVos = qry.getResultList(new MailBagsForPABuiltContainerMapper());
		log.debug(CLASS_NAME + LOG_DELIMITER + " : " + "findMailbagsForPABuiltContainerSave" + " Exiting");
		return mailBagVos;
	}

	private static class ContainerForDeviatedMailbagMapper implements Mapper<String> {
		String containerInfo;
//TODO: Neo to implement
		@Override
		public String map(ResultSet resultSet) throws SQLException {
			return null;
		}
	}
	/**
	 * @author A-8672
	 * @return handovertime
	 */
	public String findMailHandoverDetails(MailHandoverVO mailHandoverVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailHandoverDetails" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAIL_HANDOVER_DETAILS);
		int index = 0;
		qry.setParameter(++index, mailHandoverVO.getCompanyCode());
		if (mailHandoverVO.getGpaCode() != null && !("").equals(mailHandoverVO.getGpaCode())) {
			qry.append(" AND GPACOD=?");
			qry.setParameter(++index, mailHandoverVO.getGpaCode());
		}
		if (mailHandoverVO.getHoAirportCodes() != null && !("").equals(mailHandoverVO.getHoAirportCodes())) {
			qry.append(" AND ARPCOD = ?");
			qry.setParameter(++index, mailHandoverVO.getHoAirportCodes());
		}
		if (mailHandoverVO.getMailClass() != null && !("").equals(mailHandoverVO.getMailClass())) {
			qry.append(" AND MALCLS = ?");
			qry.setParameter(++index, mailHandoverVO.getMailClass());
		}
		if (mailHandoverVO.getMailSubClass() != null && !("").equals(mailHandoverVO.getMailSubClass())) {
			qry.append(" AND MALSUBCLS = ?");
			qry.setParameter(++index, mailHandoverVO.getMailSubClass());
		}
		if (mailHandoverVO.getExchangeOffice() != null && !("").equals(mailHandoverVO.getExchangeOffice())) {
			qry.append(" AND EXGOFC = ?");
			qry.setParameter(++index, mailHandoverVO.getExchangeOffice());
		}
		log.debug(CLASS_NAME + " : " + "findMailHandoverDetails" + " Exiting");
		return qry.getSingleResult(getStringMapper("HNDTIM"));
	}
	public MailbagVO findNotupliftedMailsInCarrierforDeviationlist(MailbagVO mailbagVO) throws PersistenceException {
		log.debug(CLASS_NAME + LOG_DELIMITER + " : " + "findNotupliftedMailsInCarrierforDeviationlist" + " Entering");
		MailbagVO mailBagVo = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_NOT_UPLIFTED_MAILBAGS_FOR_DEVIATION);
		qry.setParameter(++index, mailbagVO.getCompanyCode());
		qry.setParameter(++index, mailbagVO.getPol());
		qry.setParameter(++index, mailbagVO.getMailSequenceNumber());
		mailBagVo = qry.getSingleResult(new NotupliftedMailsInCarrierforDeviationMapper());
		log.debug(CLASS_NAME + LOG_DELIMITER + " : " + "findNotupliftedMailsInCarrierforDeviationlist" + " Exiting");
		return mailBagVo;
	}

	private static class NotupliftedMailsInCarrierforDeviationMapper implements Mapper<MailbagVO> {
		@Override
		public MailbagVO map(ResultSet resultSet) throws SQLException {
			return null;
		}
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findMailboxIdForPA(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO) Added by 			: A-8061 on 20-Jul-2020 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	public String findMailboxIdForPA(MailbagVO mailbagVO) throws PersistenceException {
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBOX_FORPA);
		int index = 0;
		qry.setParameter(++index, mailbagVO.getCompanyCode());
		qry.setParameter(++index, mailbagVO.getPaCode());
		return qry.getSingleResult(getStringMapper("MALBOXIDR"));
	}

	/** 
	* @author A-8353Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	public MailbagInULDForSegmentVO getManifestInfoForNextSeg(MailbagVO mailBagVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "getManifestInfoForNextSeg" + " Entering");
		MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
		int index = 0;
		if (mailBagVO.getMailSequenceNumber() == 0) {
			Long seqnum = findMailSequenceNumber(mailBagVO.getMailbagId(), mailBagVO.getCompanyCode());
			mailBagVO.setMailSequenceNumber(seqnum);
		}
		Query query = getQueryManager().createNamedNativeQuery(GET_MAIL_MANIFESTINFO_NXT_SEG);
		query.setParameter(++index, mailBagVO.getCompanyCode());
		query.setParameter(++index, mailBagVO.getMailSequenceNumber());
		query.setParameter(++index, mailBagVO.getPou());
		mailbagInULDForSegmentVO = query.getSingleResult(new InboundFlightDetailsForAutoArrivalMapper());
		return mailbagInULDForSegmentVO;
	}

	/** 
	* @author A-8353
	*/
	public String findTransferManifestId(String companyCode, long malSeqNum) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findTransferManifestId" + " Entering");
		String transferManifestId = null;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_TRANSFER_MANIFEST_ID_DETAILS);
		int index = 0;
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, malSeqNum);
		transferManifestId = qry.getSingleResult(getStringMapper("TRFMFTIDR"));
		return transferManifestId;
	}

	@Override
	public String findAgentCodeFromUpuCode(String cmpCode, String upuCode) {
		log.debug(CLASS_NAME + " : " + "findAgentCodeFromUpuCode" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_AGENT_CODE_FROM_UPUCODE);
		int index = 0;
		query.setParameter(++index, upuCode);
		query.setParameter(++index, cmpCode);
		String agentCode = query.getSingleResult(getStringMapper("AGTCOD"));
		log.debug(CLASS_NAME + " : " + "findAgentCodeFromUpuCode" + " Exiting");
		return agentCode;
	}

	@Override
	public Collection<MailbagVO> findAWBAttachedMailbags(MailbagVO mailbag, String consignmentNumber) {
		log.debug(CLASS_NAME + " : " + "findAWBAttachedMailbags" + " Entering");
		Collection<MailbagVO> mailBags = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_AWB_ATTACHED_MAIL_DETAILS);
		int index = 0;
		query.setParameter(++index, mailbag.getCompanyCode());
		query.setParameter(++index, mailbag.getDuplicateNumber());
		query.setParameter(++index, mailbag.getSequenceNumber());
		query.setParameter(++index, mailbag.getDocumentNumber());
		query.setParameter(++index, consignmentNumber);
		query.setSensitivity(true);
		mailBags = query.getResultList(new CarditPawbShipmentDetailsMapper());
		return mailBags;
	}

	public MailbagVO findMailbagDetails(String mailId, String companyCode) {
		log.debug(CLASS_NAME + " : " + "findMailbagDetails" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILSEQUENCE_NUMBER_FROM_MAILIDR);
		int indx = 0;
		query.setParameter(++indx, companyCode);
		query.setParameter(++indx, mailId);
		MailbagVO mailbagvo = query.getSingleResult(new MailbagDetailsForOrgAndDstMapper());
		log.debug(CLASS_NAME + " : " + "findMailbagDetails" + " Exiting");
		return mailbagvo;
	}

	/** 
	* @author U-1532
	*/
	public ContainerAssignmentVO findLatestContainerAssignmentForUldDelivery(ScannedMailDetailsVO scannedMailDetailsVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findLatestContainerAssignmentForUldDelivery" + " Entering");
		ContainerAssignmentVO containerAssignMentVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_LATEST_CONTAINER_ASSIGNMENT_FOR_ULDDELIVERY);
		qry.setParameter(++index, scannedMailDetailsVO.getCompanyCode());
		qry.setParameter(++index, scannedMailDetailsVO.getContainerNumber());
		qry.setParameter(++index, scannedMailDetailsVO.getAirportCode());
		if (scannedMailDetailsVO.getFlightNumber() != null && !scannedMailDetailsVO.getFlightNumber().isEmpty()) {
			qry.append(" AND MST.FLTNUM = ?");
			qry.setParameter(++index, scannedMailDetailsVO.getFlightNumber());
			if (scannedMailDetailsVO.getFlightDate() != null) {
				qry.append(
						" AND TO_NUMBER(TO_CHAR(TRUNC(FLT.FLTDAT),'YYYYMMDD')) = TO_NUMBER(TO_CHAR(TO_DATE(?), 'YYYYMMDD')) ");
				qry.setParameter(++index, scannedMailDetailsVO.getFlightDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
			}
			if (scannedMailDetailsVO.getCarrierCode() != null) {
				qry.append(" AND MST.FLTCARCOD = ? ");
				qry.setParameter(++index, scannedMailDetailsVO.getCarrierCode());
			}
		} else {
			qry.append(
					" AND MST.ASGDATUTC =(SELECT MAX (ASGDATUTC) FROM MALFLTCON WHERE CMPCOD = ? AND CONNUM = ?  AND POU = ? ) ");
			qry.setParameter(++index, scannedMailDetailsVO.getCompanyCode());
			qry.setParameter(++index, scannedMailDetailsVO.getContainerNumber());
			qry.setParameter(++index, scannedMailDetailsVO.getAirportCode());
		}
		qry.setSensitivity(true);
		containerAssignMentVO = qry.getSingleResult(new ContainerAssignmentMapper());
		log.debug(CLASS_NAME + " : " + "findLatestContainerAssignmentForUldDelivery" + " Exiting");
		return containerAssignMentVO;
	}

	/** 
	* @author A-8353
	*/
	@Override
	public long findLatestRegAgentIssuing(ConsignmentScreeningVO consignmentScreeningVO) {
		log.debug(CLASS_NAME + " : " + MAIL_OPERATIONS_FIND_LATEST_REGULATED_AGENT_ISSUING + " Entering");
		long result;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_LATEST_REGULATED_AGENT_ISSUING);
		int index = 0;
		query.setParameter(++index, consignmentScreeningVO.getCompanyCode());
		query.setParameter(++index, consignmentScreeningVO.getScreeningLocation());
		if (MailConstantsVO.SCREEN_LEVEL_VALUE.equals(consignmentScreeningVO.getScreenLevelValue())) {
			query.append("AND DTL.MALSEQNUM    = ?");
			query.setParameter(++index, consignmentScreeningVO.getMalseqnum());
		} else {
			query.setParameter(++index, consignmentScreeningVO.getConsignmentNumber());
			query.append("AND DTL.CSGDOCNUM    = ?");
		}
		query.setSensitivity(true);
		result = query.getSingleResult(getLongMapper("SERNUM"));
		return result;
	}

	/** 
	* @author A-8353
	* @throws SystemException
	*/
	@Override
	public Collection<ConsignmentScreeningVO> findScreeningMethodsForStampingRegAgentIssueMapping(
			ConsignmentScreeningVO consignmentScreeningVO) {
		log.debug(CLASS_NAME + " : " + MAIL_OPERATIONS_FIND_SCREENING_METHOD_WITHOUT_AGENT_SERNUM + " Entering");
		List<MailbagVO> mailbagVOs = null;
		MailbagVO mailbagVO = null;
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = null;
		Query query = getQueryManager()
				.createNamedNativeQuery(MAIL_OPERATIONS_FIND_SCREENING_METHOD_WITHOUT_AGENT_SERNUM);
		int index = 0;
		query.setParameter(++index, consignmentScreeningVO.getCompanyCode());
		query.setParameter(++index, consignmentScreeningVO.getScreeningLocation());
		if (MailConstantsVO.SCREEN_LEVEL_VALUE.equals(consignmentScreeningVO.getScreenLevelValue())) {
			query.append("AND DTL.MALSEQNUM    = ?");
			query.setParameter(++index, consignmentScreeningVO.getMalseqnum());
		} else {
			query.setParameter(++index, consignmentScreeningVO.getConsignmentNumber());
			query.append("AND DTL.CSGDOCNUM    = ?");
		}
		mailbagVOs = query.getResultList(new MailbagSecurityDetailsMapper());
		mailbagVO = mailbagVOs.get(0);
		if (mailbagVO != null && !mailbagVO.getConsignmentScreeningVO().isEmpty()) {
			consignmentScreeningVOs = mailbagVO.getConsignmentScreeningVO();
		}
		return consignmentScreeningVOs;
	}

	/** 
	* @throws SystemException 
	*/
	public String findApplicableRegFlagForMailbag(String companyCode, long sequencenum) {
		log.debug(CLASS_NAME + " : " + MAIL_OPERATIONS_FIND_APPLICABLE_REGULATION_FLAG_FOR_MAIL + " Entering");
		String result;
		Query query = getQueryManager()
				.createNamedNativeQuery(MAIL_OPERATIONS_FIND_APPLICABLE_REGULATION_FLAG_FOR_MAIL);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, sequencenum);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, sequencenum);
		result = query.getSingleResult(getStringMapper("APLREGFLG"));
		return result;
	}

	@Override
	public Collection<CarditPawbDetailsVO> findMailbagsForPAWBCreation(int noOfDays) {
		log.debug(CLASS_NAME + " : " + "findMailbagsForPAWBCreation" + " Entering");
		Collection<CarditPawbDetailsVO> carditPawbDetailsVOs = null;
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FOR_PAWB_CREATION);
		query.setParameter(++index, noOfDays);
		query.setParameter(++index, noOfDays);
		carditPawbDetailsVOs = query.getResultList(new MailBagsForPAWBCreationMapper());
		log.debug(CLASS_NAME + " : " + "findMailbagsForPAWBCreation" + " Exiting");
		return carditPawbDetailsVOs;
	}

	/** 
	* @author a-9998
	* @param carditVO
	* @return AWBDetailVO
	* @throws SystemException
	* @throws PersistenceException
	*/
	public AWBDetailVO findMstDocNumForAWBDetails(CarditVO carditVO) {
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "findMstDocNumForAWBDetails" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MSTDOCNUM_FOR_AWB_DETAILS);
		query.setParameter(1, carditVO.getCompanyCode());
		query.setParameter(2, carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().getConsignmentNumber());
		query.setParameter(3,
				carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().getConsignmentSequenceNumber());
		query.setParameter(4, carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().getPaCode());
		query.setParameter(5, carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().getMasterDocumentNumber());
		return query.getSingleResult(new AWBDetailsMapper());
	}

	/**
	 * @param mailMasterDataFilterVO
	 * @return MailbagDetailsVo
	 * @throws com.ibsplc.xibase.server.framework.exceptions.SystemException
	 * @throws PersistenceException
	 * @author 204082
	 * Added for IASCB-159267 on 20-Oct-2022
	 */
	@Override
	public Collection<MailbagDetailsVO> getMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO)
			throws SystemException, PersistenceException {

		String qryString = getQueryManager().getNamedNativeQueryString(
				MAIL_OPERATIONS_GET_MAILBAG_DETAILS);
		int index = 0;
		if (isOracleDataSource()) {
			qryString = getQueryManager().getNamedNativeQueryString(
					MAIL_OPERATIONS_GET_MAILBAG_DETAILS_FOR_ORACLE);
		}
		Query query = getQueryManager().createNativeQuery(qryString);
		com.ibsplc.icargo.framework.util.time.LocalDate utcCurrentTimestamp = new com.ibsplc.icargo.framework.util.time.LocalDate(com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION, Location.NONE, true);
		int lastScanTime = mailMasterDataFilterVO.getLastScanTime() / 24;
		query.setParameter(++index, mailMasterDataFilterVO.getNoOfDaysToConsider());
		query.setParameter(++index, utcCurrentTimestamp.toGMTDate().addDays(-lastScanTime));

		return query.getResultList(new MailbagDetailsForInterfaceMapper());
	}

	/**
	 * This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ConsignmentDocumentVO findConsignmentDocumentDetails(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException,
			PersistenceException {
		//log.entering(MODULE, "findConsignmentDocumentDetails");
		ConsignmentDocumentVO consignmentDocumentVO = null;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CONSIGNMENT_ROUTING_INFOS);
		query.setParameter(1, consignmentFilterVO.getCompanyCode());
		query.setParameter(2, consignmentFilterVO.getConsignmentNumber());
		query.setParameter(3, consignmentFilterVO.getPaCode());
		List<ConsignmentDocumentVO> consignmentDocumentVOs = query
				.getResultList(new ConsignmentDetailsMultimapper());
		if (consignmentDocumentVOs != null && consignmentDocumentVOs.size() > 0) {
			consignmentDocumentVO = consignmentDocumentVOs.get(0);
		}
		/*
		 * Mail details not needed for Online HHT.
		 */
		if(!MailConstantsVO.FLAG_YES.equalsIgnoreCase(consignmentFilterVO.getScannedOnline())) {
			String qryString = getQueryManager().getNamedNativeQueryString(
					FIND_CONSIGNMENT_DOCUMENT_DETAILS);
			if(consignmentFilterVO.getPageSize()==0){
				consignmentFilterVO.setPageSize(10);
			}
			PageableNativeQuery<MailInConsignmentVO> pgyquery =
					new PageableNativeQuery<MailInConsignmentVO>(consignmentFilterVO.getPageSize(),consignmentFilterVO.getTotalRecords(),
							qryString , new MaintainConsignmentMapper(), PersistenceController.getEntityManager().currentSession());

			pgyquery.setParameter(1, consignmentFilterVO.getCompanyCode());
			pgyquery.setParameter(2, consignmentFilterVO.getConsignmentNumber());
			pgyquery.setParameter(3, consignmentFilterVO.getPaCode());
			Page<MailInConsignmentVO> mailInConsignmentVOs=null;
			if(consignmentFilterVO.getPageNumber()>0)
				mailInConsignmentVOs=pgyquery.getPage(consignmentFilterVO.getPageNumber());
			else{
				//fix starts
				List<MailInConsignmentVO> fullmailInConsignmentVOs=pgyquery.getResultList(new MaintainConsignmentMapper());
				mailInConsignmentVOs= new Page<MailInConsignmentVO>(
						fullmailInConsignmentVOs, 0, 0, 0, 0, 0, false);

				//fix ends
			}
			if(consignmentDocumentVO!=null){
				consignmentDocumentVO.setMailInConsignmentVOs(mailInConsignmentVOs);
			}
			//log.log(Log.INFO, " ###### Query for execution ", qryString);
		}
		//log.log(Log.FINE, " <<=== ConsignmentDocumentVO ===>>",
		//consignmentDocumentVO);
		return consignmentDocumentVO;
	}
	/**
	 * @author A-2037 This method is used to find the Mailbags in CARDITChanged the query by A-8164 for ICRD-342608
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<PreAdviceDetailsVO> findMailbagsInCARDIT(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGINCARDIT);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry.setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVO.getPol());
		qry.append(" GROUP BY MALMST.DSTEXGOFC,MALMST.ORGEXGOFC,MALMST.MALCTG,CSGDTL.ULDNUM");
		qry.append(" ORDER BY MALMST.MALCTG,MALMST.ORGEXGOFC,MALMST.DSTEXGOFC,CSGDTL.ULDNUM");
		Collection<PreAdviceDetailsVO> coll = qry.getResultList(new MailbagsInCARDITMapper());
		for (PreAdviceDetailsVO preAdviceDetailsVO : coll) {
			preAdviceDetailsVO.setOriginExchangeOffice(preAdviceDetailsVO.getOriginExchangeOffice());
			preAdviceDetailsVO.setDestinationExchangeOffice(preAdviceDetailsVO.getDestinationExchangeOffice());
		}
		return coll;
	}

	/**
	 * Finds the cardit details of this resdit Sep 11, 2006, a-1739
	 * @param companyCode
	 * @param consignmentId
	 * @return the cardit details of this resdit
	 * @throws SystemException
	 * @throws PersistenceException */
	public CarditVO findCarditDetailsForResdit(String companyCode, String consignmentId) throws PersistenceException {
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "findCarditDetailsForResdit" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_CARDIT_FOR_RESDIT);
		query.setParameter(1, companyCode);
		query.setParameter(2, consignmentId);
		return query.getSingleResult(new CarditDetailsMapper());
	}
	/**
	 * For converting ULDResdit object qries Jun 3, 2008, a-1739
	 * @param uldResditVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<UldResditVO> findULDResditStatus(UldResditVO uldResditVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDResditStatus" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_ULDRESDIT_STATUS);
		int idx = 0;
		qry.setParameter(++idx, uldResditVO.getCompanyCode());
		qry.setParameter(++idx, uldResditVO.getUldNumber());
		qry.setParameter(++idx, uldResditVO.getEventCode());
		qry.setParameter(++idx, uldResditVO.getEventAirport());
		qry.setParameter(++idx, uldResditVO.getCarrierId());
		qry.setParameter(++idx, uldResditVO.getFlightNumber());
		qry.setParameter(++idx, uldResditVO.getFlightSequenceNumber());
		qry.setParameter(++idx, uldResditVO.getSegmentSerialNumber());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDResditStatus" + " Exiting");
		return qry.getResultList(new ULDResditMapper());
	}

	/**
	 * Finds the PA corresponding to an exchangeoffice Sep 13, 2006, a-1739
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException */
	public String findPAForOfficeOfExchange(String companyCode, String officeOfExchange) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPAForOfficeOfExchange" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_PA_FOR_EXCHANGEOFFICE);
		query.setParameter(1, companyCode);
		query.setParameter(2, officeOfExchange);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPAForOfficeOfExchange" + " Exiting");
		return query.getSingleResult(getStringMapper("POACOD"));
	}
	/**
	 * @author a-1883 NCA CR
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailDiscrepancyVO> findMailDiscrepancies(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug("MailTrackingSqlDAO" + " : " + "findMailDiscrepancies" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAIL_DISCREPANCIES);
		query.setParameter(1, operationalFlightVO.getCarrierId());
		query.setParameter(2, operationalFlightVO.getFlightNumber());
		query.setParameter(3, operationalFlightVO.getFlightSequenceNumber());
		query.setParameter(4, operationalFlightVO.getCompanyCode());
		query.setParameter(5, operationalFlightVO.getPou());
		Collection<MailDiscrepancyVO> discrepancies = query.getResultList(new MailDiscrepancyMapper());
		log.debug("MailTrackingSqlDAO" + " : " + "findMailDiscrepancies" + " Exiting");
		log.debug("" + " Mail Discrepancies " + " " + discrepancies);
		return discrepancies;
	}

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findULDsInInboundFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDsInInboundFlight" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_ULDS_IN_INBOUND_FLIGHT);
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPou());
		qry.setSensitivity(true);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDsInInboundFlight" + " Exiting");
		return qry.getResultList(new ContainerMapper());
	}
	/**
	 * @author A-1936 This method is used to find the Uplift
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findMailBagsForUpliftedResdit(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findAllPartnerCarriers" + " Entering");
		Collection<MailbagVO> mailBagVos = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FORUPLIFTEDRESDIT);
		qry.setSensitivity(true);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry.setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		if (operationalFlightVO.getPol() != null) {
			qry.append(" AND FLTSEG.POL       = ?");
			qry.setParameter(++index, operationalFlightVO.getPol());
		} else {
			if (operationalFlightVO.getPou() != null) {
				qry.append(" AND FLTSEG.POU       = ?");
				qry.setParameter(++index, operationalFlightVO.getPou());
			}
		}
		qry.append(
				" ) SELECT malidr,malseqnum,segsernum,scndat,uldnum,scnprt, contyp, malsta,pou,orgexgofc,dstexgofc,malctg,malsubcls,poacod,pol,orgcod,dstcod FROM  mal ");
		mailBagVos = qry.getResultList(new MailBagsForUpliftedResditMapper());
		if (mailBagVos != null && mailBagVos.size() > 0) {
			for (MailbagVO mailbag : mailBagVos) {
				mailbag.setCompanyCode(operationalFlightVO.getCompanyCode());
				if (mailbag.getCarrierCode() == null) {
					mailbag.setCarrierCode(operationalFlightVO.getCarrierCode());
				}
				mailbag.setCarrierId(operationalFlightVO.getCarrierId());
				mailbag.setFlightDate(operationalFlightVO.getFlightDate());
				mailbag.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				mailbag.setFlightNumber(operationalFlightVO.getFlightNumber());
				mailbag.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			}
		}
		return mailBagVos;
	}

	/**
	 * This method finds Cardit Details of Maiibag
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailbagHistoryVO findCarditDetailsOfMailbag(String companyCode, String mailBagId, long mailSequenceNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findCarditDetailsOfMailbag" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_CARDIT_DETAILS_OF_MAILBAG);
		query.setParameter(1, companyCode);
		if (mailSequenceNumber != 0l) {
			query.append(" AND MALMST.MALSEQNUM = ? ORDER BY CDTRCP.RCPSRLNUM DESC");
			query.setParameter(2, mailSequenceNumber);
		} else if (mailBagId != null || !mailBagId.isEmpty()) {
			query.append(" AND MALMST.MALIDR = ? ORDER BY CDTRCP.RCPSRLNUM ");
			query.setParameter(2, mailBagId);
		}
		return query.getSingleResult(new CarditDetailsOfMailbagMapper());
	}
	public Collection<MailHistoryRemarksVO> findMailbagNotes(String mailBagId) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailbagNotes" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGNOTES);
		query.setParameter(++index, mailBagId);
		return query.getResultList(new MailBagNotesMapper());
	}

	/**
	 * findMailbagHistories
	 */
	public Collection<MailbagHistoryVO> findMailStatusDetails(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(CLASS_NAME + " : " + "findMailbagHistories" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGSTATUS);
		query.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
		query.setParameter(++index, mailbagEnquiryFilterVO.getOrigin());
		query.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
		ZonedDateTime fromDate = localDateUtil.getLocalDate(null, false);
		fromDate = LocalDate.withDate(fromDate, mailbagEnquiryFilterVO.getFromDate());
		query.setParameter(++index,
				Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYYMMDD))));
		ZonedDateTime toDate = localDateUtil.getLocalDate(null, false);
		toDate = LocalDate.withDate(toDate, mailbagEnquiryFilterVO.getToDate());
		query.setParameter(++index, Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYYMMDD))));
		if (mailbagEnquiryFilterVO.getDespatchSerialNumber() != null
				&& mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length() > 0) {
			query.append(" AND MST.DSN = ? ");
			query.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
		}
		if (mailbagEnquiryFilterVO.getAwbNumber() != null
				&& mailbagEnquiryFilterVO.getAwbNumber().trim().length() > 0) {
			String awbNumber = mailbagEnquiryFilterVO.getAwbNumber();
			String[] awbNumbers = awbNumber.split("-");
			String mstdocnum = awbNumbers[1];
			query.append(" AND MST.MSTDOCNUM = ? ");
			query.setParameter(++index, mstdocnum);
		}
		if (mailbagEnquiryFilterVO.getMailbagId() != null
				&& mailbagEnquiryFilterVO.getMailbagId().trim().length() > 0) {
			query.append(" AND MST.MALIDR = ? ");
			query.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
		}
		query.append("  ORDER BY MST.MALIDR,BKGFLTDTL.SERNUM ");
		return query.getResultList(new MailbagHistoryMapper());
	}
	/**
	 * @author A-1885
	 */
	public Collection<MailUploadVO> findMailbagAndContainer(MailUploadVO mailUploadVo) throws PersistenceException {
		Collection<MailUploadVO> mailvos = null;
		Query qry = null;
		if (!mailUploadVo.isDeliverd()) {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILANDCONTAINERDETAILS);
			int index = 0;
			qry.setParameter(++index, mailUploadVo.getCompanyCode());
			qry.setParameter(++index, mailUploadVo.getCarrierId());
			qry.setParameter(++index, mailUploadVo.getFlightNumber());
			qry.setParameter(++index, mailUploadVo.getFlightSequenceNumber());
			if (mailUploadVo.getMailTag() != null && mailUploadVo.getMailTag().trim().length() > 0) {
				qry.append(" AND MALMST.MALIDR = ? ");
				qry.setParameter(++index, mailUploadVo.getMailTag());
			}
			if (mailUploadVo.getContainerNumber() != null && mailUploadVo.getContainerNumber().trim().length() > 0) {
				if (mailUploadVo.getContainerNumber().startsWith("BULK-")) {
					qry.append(" AND SEG.ULDNUM = ? ");
					qry.setParameter(++index, mailUploadVo.getContainerNumber());
				} else {
					qry.append(" AND SEG.CONNUM = ? ");
					qry.setParameter(++index, mailUploadVo.getContainerNumber());
				}
			}
		} else {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILDETAILSFORDELIVERYFORGHA);
			int index = 0;
			qry.setParameter(++index, mailUploadVo.getCompanyCode());
			qry.setParameter(++index, mailUploadVo.getContainerNumber());
			qry.setParameter(++index, mailUploadVo.getToPOU());
		}
		return qry.getResultList(new MailbagAndContainerMapper());
	}
	/**
	 * Overriding Method	:	@see cargo.business.mail.operations.vo.MLDMasterVO Added by 			: A-4803 on 28-Oct-2014 Used for 	:	To find whether a container is already presnet for the mail bag Parameters	:	@param mldMasterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public String findAlreadyAssignedTrolleyNumberForMLD(MLDMasterVO mldMasterVO) throws PersistenceException {
		MLDDetailVO mLDDetailVO = mldMasterVO.getMldDetailVO();
		Query query = null;
		int index = 0;
		if ("-1".equals(mLDDetailVO.getFlightNumberOub())) {
			query = getQueryManager().createNamedNativeQuery(FIND_ASSIGNED_TROLLEY_NUMBER_FOR_MLD_CARRIER);
			query.setParameter(++index, mLDDetailVO.getFlightNumberOub());
			query.setParameter(++index, mldMasterVO.getSenderAirport());
			query.setParameter(++index, mLDDetailVO.getPouOub());
			query.setParameter(++index, mLDDetailVO.getCarrierCodeOub());
			query.setParameter(++index, mLDDetailVO.getCompanyCode());
			return query.getSingleResult(getStringMapper(MailConstantsVO.CONTAINER_NUMBER));
		} else {
			String flightQuery = null;
			String dynamicquery = null;
			String finalFlightQuery = null;
			flightQuery = getQueryManager().getNamedNativeQueryString(FIND_ASSIGNED_TROLLEY_NUMBER_FOR_MLD_FLIGHT);
			if (isOracleDataSource()) {
				dynamicquery = "AND ASGFLT.FLTDAT = ?";
			} else {
				dynamicquery = "AND ASGFLT.FLTDAT = cast(? as timestamp)";
			}
			finalFlightQuery = String.format(flightQuery, dynamicquery);
			Query qry = getQueryManager().createNativeQuery(finalFlightQuery);
			qry.setParameter(++index, mLDDetailVO.getCarrierCodeOub());
			qry.setParameter(++index, mLDDetailVO.getFlightNumberOub());
			qry.setParameter(++index, mLDDetailVO.getFlightOperationDateOub().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
			qry.setParameter(++index, mldMasterVO.getSenderAirport());
			qry.setParameter(++index, mLDDetailVO.getPouOub());
			qry.setParameter(++index, mLDDetailVO.getCompanyCode());
			qry.append("AND CONMST.CONNUM LIKE ")
					.append(new StringBuilder().append("'%").append(mLDDetailVO.getFlight()).append("%'").toString());
			return qry.getSingleResult(getStringMapper(MailConstantsVO.CONTAINER_NUMBER));
		}
	}

	/**
	 * @author A-1936 This method is used to find the Uplift
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findUldsForUpliftedResdit(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findULDsForUpliftedResdit" + " Entering");
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_ULDS_FORUPLIFTEDRESDIT);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry.setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVO.getLegSerialNumber());
		qry.setParameter(++index, operationalFlightVO.getPol());
		qry.setParameter(++index, MailConstantsVO.ULD_TYPE);
		containerDetailsVOs = qry.getResultList(new UldsForUpliftedResditMapper());
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
		}
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				containerDetailsVO.setCompanyCode(operationalFlightVO.getCompanyCode());
				containerDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
				containerDetailsVO.setCarrierId(operationalFlightVO.getCarrierId());
				containerDetailsVO.setFlightDate(operationalFlightVO.getFlightDate());
				containerDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				containerDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			}
		}
		return containerDetailsVOs;
	}

	@Override
	public OfficeOfExchangeVO validateOfficeOfExchange(String companyCode, String officeOfExchange) throws PersistenceException {
		return null;
	}
	/**
	 * @author A-3227
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public DespatchDetailsVO findConsignmentDetailsForDespatch(String companyCode, DespatchDetailsVO despatchDetailsVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findConsignmentDetailsForDespatch" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_DETAILS_FOR_DESPATCH);
		int idx = 0;
		int consignmentSeqNumber = 1;
		log.debug("" + "--companyCode --- : " + " " + companyCode);
		log.debug("" + "--consignmentSeqNumber --- : " + " " + consignmentSeqNumber);
		log.debug("" + "--despatchDetailsVO.getConsignmentNumber() --- : " + " "
				+ despatchDetailsVO.getConsignmentNumber());
		log.debug("" + "--despatchDetailsVO.getPaCode() --- : " + " " + despatchDetailsVO.getPaCode());
		log.debug("" + "--despatchDetailsVO.getDsn() --- : " + " " + despatchDetailsVO.getDsn());
		log.debug("" + "--despatchDetailsVO.getOriginOfficeOfExchange() --- : " + " "
				+ despatchDetailsVO.getOriginOfficeOfExchange());
		log.debug("" + "--despatchDetailsVO.getDestinationOfficeOfExchange() --- : " + " "
				+ despatchDetailsVO.getDestinationOfficeOfExchange());
		log.debug("" + "--despatchDetailsVO.getMailCategoryCode() --- : " + " "
				+ despatchDetailsVO.getMailCategoryCode());
		log.debug("" + "--despatchDetailsVO.getMailSubclass() --- : " + " " + despatchDetailsVO.getMailSubclass());
		log.debug("" + "--despatchDetailsVO.getYear() --- : " + " " + despatchDetailsVO.getYear());
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, despatchDetailsVO.getConsignmentNumber());
		query.setParameter(++idx, consignmentSeqNumber);
		query.setParameter(++idx, despatchDetailsVO.getPaCode());
		query.setParameter(++idx, despatchDetailsVO.getDsn());
		query.setParameter(++idx, despatchDetailsVO.getOriginOfficeOfExchange());
		query.setParameter(++idx, despatchDetailsVO.getDestinationOfficeOfExchange());
		query.setParameter(++idx, despatchDetailsVO.getMailCategoryCode());
		query.setParameter(++idx, despatchDetailsVO.getMailSubclass());
		query.setParameter(++idx, despatchDetailsVO.getYear());
		return query.getSingleResult(new DespatchDetailsMapper());
	}

	/**
	 * Method	    :	findMLDDetails Added by 	:   A-5526 on Dec 20, 2015 for CRQ-93584 Used for 	: Parameters	:	@param companyCode Parameters	:	@return Collection<MLDMasterVO> Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public Collection<MLDMasterVO> findMLDDetails(String companyCode, int recordCount) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findScannedMailDetails" + " Entering");
		int index = 0;
		Collection<MLDMasterVO> mLDMasterVOs = null;
		String query = getQueryManager().getNamedNativeQueryString(FIND_MLD_DETAILS);
		String dynamicquery = null;
		if (isOracleDataSource()) {
			dynamicquery = "AND ROWNUM <= ? ORDER BY TXNTIMUTC ASC	";
		} else {
			dynamicquery = "ORDER BY TXNTIMUTC ASC LIMIT ? ";
		}
		query = String.format(query, dynamicquery);
		Query qry = getQueryManager().createNativeQuery(query);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, recordCount);
		return qry.getResultList(new MLDMessageDetailsMapper());
	}

	/**
	 * @author a-2553
	 * @param carditEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findCarditMails" + " Entering");
		Page<MailbagVO> mailbagVos = null;
		String baseQry = getQueryManager().getNamedNativeQueryString(FIND_CARDIT_MAILS);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_DENSE_RANK_QUERY);
		rankQuery.append("RESULT_TABLE.CMPCOD,RESULT_TABLE.MALIDR) RANK FROM ( ");
		rankQuery.append(baseQry);
		PageableNativeQuery<MailbagVO> qry = null;
		if (carditEnquiryFilterVO.getPageSize() == 0)
			qry = new CarditMailsFilterQuery(new CarditMailsMapper(), carditEnquiryFilterVO, rankQuery.toString());
		else
			qry = new CarditMailsFilterQuery(new CarditMailsMapper(), carditEnquiryFilterVO, rankQuery.toString(),
					"MTK056");
		log.info("" + "Query: " + " " + qry);
		if (MailConstantsVO.FLAG_YES.equals(carditEnquiryFilterVO.getConsignmentLevelAWbAttachRequired())) {
			List<MailbagVO> mails = qry.getResultList(new CarditMailsMapper());
			return new Page<MailbagVO>(mails, 0, 0, 0, 0, 0, false);
		} else {
			return qry.getPage(pageNumber);
		}
	}

	/**
	 * @author A-2107
	 * @param consignmentFilterVO
	 * @throws SystemException
	 */
	public Collection<MailbagVO> findCartIdsMailbags(ConsignmentFilterVO consignmentFilterVO) {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCartIdsMailbags" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(GET_CARDIT_MAILBAGDTLS);
		int idx = 0;
		qry.setParameter(++idx, consignmentFilterVO.getCompanyCode());
		qry.setParameter(++idx, consignmentFilterVO.getConsignmentFromDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		qry.setParameter(++idx, consignmentFilterVO.getConsignmentToDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		if (Objects.nonNull(consignmentFilterVO.getContainerJourneyId())
				&& !consignmentFilterVO.getContainerJourneyId().isEmpty()) {
			qry.append(" AND CSGDTL.MALJNRIDR = ?");
			qry.setParameter(++idx, consignmentFilterVO.getContainerJourneyId());
		}
		if (Objects.nonNull(consignmentFilterVO.getBellyCartId()) && !consignmentFilterVO.getBellyCartId().isEmpty()) {
			qry.append(" AND CSGDTL.ULDNUM = ?");
			qry.setParameter(++idx, consignmentFilterVO.getBellyCartId());
		}
		if (Objects.nonNull(consignmentFilterVO.getContainerNumber())
				&& !consignmentFilterVO.getContainerNumber().isEmpty()) {
			qry.append(" AND CSGDTL.ULDNUM = ?");
			qry.setParameter(++idx, consignmentFilterVO.getContainerNumber());
		}
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCartIdsMailbags" + " Exiting");
		return qry.getResultList(new CarditMailbagDetailsMapper());
	}

	/**
	 * @author A-5991
	 * @param companyCode
	 * @param mailbagID
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public CarditReceptacleVO findDuplicateMailbagsInCardit(String companyCode, String mailbagID)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findDuplicateMailbagsInCardit" + " Entering");
		CarditReceptacleVO carditReceptacleVO = new CarditReceptacleVO();
		Query query = getQueryManager().createNamedNativeQuery(FIND_DUPLICATE_MAILBAGS_IN_CARDIT);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, mailbagID);
		String oldCarditKey = "";
		carditReceptacleVO = query.getSingleResult((new CarditReceptacleMapper()));
		log.debug("oldCarditKey::" + oldCarditKey);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findDuplicateMailbagsInCardit" + " Exiting");
		return carditReceptacleVO;
	}
	/**
	 * @author A-5991
	 */
	public ContainerAssignmentVO findContainerDetailsForMRD(OperationalFlightVO opFltVo, String mailBag)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findContainerAssignment" + " Entering");
		ContainerAssignmentVO containerAssignMentVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery("mail.operations.findContainerDetailsForMRD");
		qry.setParameter(++index, opFltVo.getCompanyCode());
		qry.setParameter(++index, opFltVo.getFlightNumber());
		qry.setParameter(++index, opFltVo.getCarrierCode());
		qry.setParameter(++index, opFltVo.getFlightSequenceNumber());
		qry.setParameter(++index, opFltVo.getPou());
		qry.setParameter(++index, mailBag);
		containerAssignMentVO = qry.getSingleResult(new ContainerAssignmentMapper());
		return containerAssignMentVO;
	}

	public Collection<String> validateMailBagsForUPL(FlightValidationVO flightValidationVO)
			throws PersistenceException {
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(VALIDATE_MAILBAG_UPL);
		query.setParameter(++index, flightValidationVO.getCompanyCode());
		query.setParameter(++index, flightValidationVO.getFlightCarrierId());
		query.setParameter(++index, flightValidationVO.getFlightSequenceNumber());
		query.setParameter(++index, flightValidationVO.getFlightNumber());
		return query.getResultList(getStringMapper("MALIDR"));
	}

	private String appendMilitaryClasses(Query qry, int index, boolean isMil) {
		StringBuilder milQry = new StringBuilder();
		if (isMil) {
			milQry.append(" AND MALMST.MALCLS IN ( ");
		} else {
			milQry.append(" AND MALMST.MALCLS NOT IN ( ");
		}
		for (String mailClz : MailConstantsVO.MILITARY_CLASS) {
			milQry.append(" ?,");
			qry.setParameter(++index, mailClz);
		}
		milQry.deleteCharAt(milQry.length() - 1);
		milQry.append(" ) ");
		return milQry.toString();
	}

	/**
	 * @param containerVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException	 */
	public int findFlightLegSerialNumber(ContainerVO containerVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findFlightLegSerialNumber" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_FLIGHT_LEGSERNUM_FOR_CONTAINER);
		int idx = 0;
		query.setParameter(++idx, containerVO.getCompanyCode());
		query.setParameter(++idx, containerVO.getCarrierId());
		query.setParameter(++idx, containerVO.getFlightNumber());
		query.setParameter(++idx, containerVO.getFlightSequenceNumber());
		query.setParameter(++idx, containerVO.getAssignedPort());
		query.setParameter(++idx, containerVO.getContainerNumber());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findFlightLegSerialNumber" + " Exiting");
		return query.getSingleResult(getIntMapper("LEGSERNUM"));
	}
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#validateMailFlight Added by 			: A-5160 on 26-Nov-2014 Used for 	:	validating flight for mail Parameters	:	@param flightFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public Collection<FlightValidationVO> validateMailFlight(FlightFilterVO flightFilterVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAirportCityForMLD" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(VALIDATE_MAILFLIGHT);
		query.setParameter(++index, flightFilterVO.getCompanyCode());
		query.setParameter(++index, flightFilterVO.getFlightCarrierId());
		query.setParameter(++index, flightFilterVO.getFlightNumber());
		query.setParameter(++index, flightFilterVO.getFlightDate());
		query.setParameter(++index, flightFilterVO.getStation());
		Collection<FlightValidationVO> flightValidationVOs = query.getResultList(new MailFlightMapper());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAirportCityForMLD" + " Entering");
		return flightValidationVOs;
	}

	/**
	 * @author A-3227 - AUG 12, 2008
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkRoutingsForMails(OperationalFlightVO operationalFlightVO, DSNVO dSNVO, String type)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "checkRoutingsForMails" + " Entering");
		boolean routingAvailable = false;
		Query baseQry = getQueryManager().createNamedNativeQuery(FIND_ROUTINGS_FOR_MAILBAG);
		int index = 0;
		if (operationalFlightVO.getCompanyCode() != null) {
			baseQry.setParameter(++index, operationalFlightVO.getCompanyCode());
		} else {
			baseQry.setParameter(++index, "");
		}
		baseQry.setParameter(++index, operationalFlightVO.getCarrierId());
		if (operationalFlightVO.getFlightNumber() != null) {
			baseQry.setParameter(++index, operationalFlightVO.getFlightNumber());
		} else {
			baseQry.setParameter(++index, "");
		}
		baseQry.setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		if (dSNVO.getContainerNumber() != null) {
			baseQry.setParameter(++index, dSNVO.getContainerNumber());
		} else {
			baseQry.setParameter(++index, "");
		}
		if (dSNVO.getDsn() != null) {
			baseQry.setParameter(++index, dSNVO.getDsn());
		} else {
			baseQry.setParameter(++index, "");
		}
		if (dSNVO.getOriginExchangeOffice() != null) {
			baseQry.setParameter(++index, dSNVO.getOriginExchangeOffice());
		} else {
			baseQry.setParameter(++index, "");
		}
		if (dSNVO.getDestinationExchangeOffice() != null) {
			baseQry.setParameter(++index, dSNVO.getDestinationExchangeOffice());
		} else {
			baseQry.setParameter(++index, "");
		}
		baseQry.setParameter(++index, dSNVO.getYear());
		if (dSNVO.getMailCategoryCode() != null) {
			baseQry.setParameter(++index, dSNVO.getMailCategoryCode());
		} else {
			baseQry.setParameter(++index, "");
		}
		if (dSNVO.getMailSubclass() != null) {
			baseQry.setParameter(++index, dSNVO.getMailSubclass());
		} else {
			baseQry.setParameter(++index, "");
		}
		if (baseQry.getSingleResult(getIntMapper("ROUTE")) > 0) {
			routingAvailable = true;
		}
		log.info("" + "ROUTE PRESENT---> " + " " + routingAvailable);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "checkRoutingsForMails" + " Exiting");
		return routingAvailable;
	}
	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findULDsInAssignedFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDsInAssignedFlight" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_ULDS_IN_ASSIGNED_FLIGHT);
		boolean isOracleDataSource = isOracleDataSource();
		if (isOracleDataSource) {
			qry.append("AND substr(ULD.ULDNUM, 0, 4) <> 'BULK'");
		} else {
			qry.append("AND substr(ULD.ULDNUM, 0, 5) <> 'BULK'");
		}
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPol());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDsInAssignedFlight" + " Exiting");
		return qry.getResultList(new ContainerMapper());
	}

	/**
	 * @author a-1936
	 * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailManifestVO findContainersInFlightForManifest(OperationalFlightVO operationalFlightVo)
			throws PersistenceException {
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "findContainersInFlight" + " Entering");
		MailManifestVO mailManifestVo = new MailManifestVO();
		Query query = getQueryManager().createNamedNativeQuery(FIND_ULDS_INFLIGHT_FOR_MANIFEST);
		int idx = 0;
		query.setParameter(++idx, operationalFlightVo.getCompanyCode());
		query.setParameter(++idx, operationalFlightVo.getCarrierId());
		query.setParameter(++idx, operationalFlightVo.getFlightNumber());
		query.setParameter(++idx, operationalFlightVo.getFlightSequenceNumber());
		query.setParameter(++idx, operationalFlightVo.getCompanyCode());
		query.setParameter(++idx, operationalFlightVo.getCarrierId());
		query.setParameter(++idx, operationalFlightVo.getFlightNumber());
		query.setParameter(++idx, operationalFlightVo.getFlightSequenceNumber());
		query.setParameter(++idx, operationalFlightVo.getPol());
		mailManifestVo.setContainerDetails(query.getResultList(new ContainersForManifestMultiMapper()));
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDsForManifest" + " Exiting");
		return mailManifestVo;
	}

	/**
	 * findContainersInAssignedFlight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findContainersInAssignedFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findContainersInAssignedFlight" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CONTAINERS_IN_ASSIGNED_FLIGHT);
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPol());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findContainersInAssignedFlight" + " Exiting");
		return qry.getResultList(new ContainerMapper());
	}

	/**
	 * @author a-1936 This method is used to find out all the MailBags that hasbeen Manifested to the Closed Flight .. Required For Monitoring the Service Level Activity For the MailBags..
	 * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> findMailBagsInClosedFlight(OperationalFlightVO operationalFlightVo)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailBagsInClosedFlight" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_CLOSEDFLIGHT_MONIITORSLA);
		int index = 0;
		query.setParameter(++index, operationalFlightVo.getCompanyCode());
		query.setParameter(++index, operationalFlightVo.getCarrierId());
		query.setParameter(++index, operationalFlightVo.getFlightNumber());
		query.setParameter(++index, operationalFlightVo.getFlightSequenceNumber());
		query.setParameter(++index, operationalFlightVo.getLegSerialNumber());
		query.setParameter(++index, operationalFlightVo.getPol());
		Mapper<String> stringMapper = getStringMapper("MALIDR");
		Collection<String> mailIds = query.getResultList(stringMapper);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailBagsInClosedFlight" + " Exiting");
		return mailIds;
	}

	/**
	 * @author a-1936 This method is used to find the offload details
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findAcceptedContainersForOffload(OffloadFilterVO offloadFilterVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "FindAcceptedContainersForOffload" + " Entering");
		String baseQuery = getQueryManager().getNamedNativeQueryString(FIND_OFFLOAD_DETAILS);
		Query qry = new OffloadContainerFilterQuery(offloadFilterVO, baseQuery);
		return qry.getResultList(new OffloadContainerMultiMapper());
	}

	/**
	 * This method is used to find all the DSNS,that can be Offloaded for a ParticularFlight
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<DespatchDetailsVO> findAcceptedDespatchesForOffload(OffloadFilterVO offloadFilterVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findAcceptedDespatchesForOffload" + " Entering");
		if (offloadFilterVO.getDefaultPageSize() == 0) {
			String baseQuery = getQueryManager().getNamedNativeQueryString(FIND_OFFLOAD_DETAILS);
			Query qry = null;
			PageableQuery<DespatchDetailsVO> pageQuery = null;
			qry = new OffloadDespatchesFilterQuery(offloadFilterVO, baseQuery);
			pageQuery = new PageableQuery<DespatchDetailsVO>(qry, new OffloadDSNMapper());
			Page<DespatchDetailsVO> despatchDetailsPageVOs = pageQuery.getPage(offloadFilterVO.getPageNumber());
			if (despatchDetailsPageVOs != null && despatchDetailsPageVOs.size() > 0) {
				for (DespatchDetailsVO despatchDetailsVO : despatchDetailsPageVOs) {
					despatchDetailsVO.setFlightDate(offloadFilterVO.getFlightDate());
					despatchDetailsVO.setLegSerialNumber(offloadFilterVO.getLegSerialNumber());
				}
			}
			return despatchDetailsPageVOs;
		} else {
			int pageSize = offloadFilterVO.getDefaultPageSize();
			int totalRecords = offloadFilterVO.getTotalRecords();
			StringBuilder rankQuery = new StringBuilder().append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
			Query offloadDSNquery = getQueryManager().createNamedNativeQuery(FIND_DSN_OFFLOAD_DETAILS);
			rankQuery.append(offloadDSNquery);
			PageableNativeQuery<DespatchDetailsVO> query = new OffloadDespatchesUXFilterQuery(pageSize, totalRecords,
					new OffloadDSNMapper(), rankQuery.toString(), offloadFilterVO);
			query.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
			return query.getPage(offloadFilterVO.getPageNumber());
		}
	}

	/**
	 * This method is used to find all the MailBags that can be Offloaded for a ParticularFlight
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<MailbagVO> findAcceptedMailBagsForOffload(OffloadFilterVO offloadFilterVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findAcceptedMailBagsForOffload" + " Entering");
		String baseQuery = getQueryManager().getNamedNativeQueryString(OFFLOAD_MAILBAGS);
		if (offloadFilterVO.getDefaultPageSize() == 0) {
			Query qry = null;
			PageableQuery<MailbagVO> pageQuery = null;
			qry = new OffloadMailBagsFilterQuery(offloadFilterVO, baseQuery);
			pageQuery = new PageableQuery<MailbagVO>(qry, new OffloadMailBagMapper());
			return pageQuery.getPage(offloadFilterVO.getPageNumber());
		} else {
			int pageSize = offloadFilterVO.getDefaultPageSize();
			int totalRecords = offloadFilterVO.getTotalRecords();
			StringBuilder rankQuery = new StringBuilder().append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
			Query offloadMailquery = getQueryManager().createNamedNativeQuery(OFFLOAD_MAILBAGS);
			rankQuery.append(offloadMailquery);
			PageableNativeQuery<MailbagVO> query = new OffloadMailBagsUXFilterQuery(pageSize, totalRecords,
					new OffloadMailBagMapper(), rankQuery.toString(), offloadFilterVO);
			query.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
			return query.getPage(offloadFilterVO.getPageNumber());
		}
	}

	/**
	 * @author a-1936This  method is used to find the Transfer Manifest Details
	 * @param tranferManifestFilterVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findTransferManifest" + " Entering");
		Page<TransferManifestVO> transferManifestVos = null;
		String baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAIL_TRANSFERMANIFEST);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		rankQuery.append(baseQry);
		PageableNativeQuery<TransferManifestVO> pgqry = new TransferManifestListFilterQuery(
				new TransferManifestListMapper(), tranferManifestFilterVo, rankQuery.toString());
		return pgqry.getPage(tranferManifestFilterVo.getPageNumber());
	}

	/**
	 * changed by A-5216 to enable last link and total record count for Jira Id: ICRD-21098 and ScreenId MTK009
	 */
	public Page<MailbagVO> findMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailbags" + " Entering");
		String baseQuery = null;
		boolean acceptedmailbagFilterQuery = false;
		boolean allmailbagfilterQuery = false;
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		Query qry = null;
		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		int index = 0;
		if ("".equals(mailbagEnquiryFilterVO.getCurrentStatus()) || mailbagEnquiryFilterVO.getCurrentStatus() == null) {
			rankQuery.append(
					"result_table.cmpcod,result_table.malidr,result_table.fltcarcod,result_table.fltcaridr,result_table.fltnum,result_table.fltseqnum ) AS RANK FROM (");
			baseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAILDETAILS);
			allmailbagfilterQuery = true;
			log.info("The Status is <<ALL>> ");
			rankQuery.append(baseQuery);
			if (mailbagEnquiryFilterVO.getPageSize() == 0) {
				pageableNativeQuery = new MailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,
						rankQuery.toString());
			} else {
				pageableNativeQuery = new MailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,
						rankQuery.toString(), "MTK057");
			}
		} else if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
			rankQuery.append("result_table.cmpcod,result_table.malidr) AS RANK FROM (");
			baseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS_FORRETURN);
			rankQuery.append(baseQuery);
			if (mailbagEnquiryFilterVO.getPageSize() > 0) {
				pageableNativeQuery = new ReturnMailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,
						rankQuery.toString(), mailbagEnquiryFilterVO.getPageSize());
			} else {
				pageableNativeQuery = new ReturnMailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,
						rankQuery.toString());
			}
		} else {
			rankQuery.append(
					"result_table.cmpcod,result_table.malidr,result_table.fltcarcod,result_table.fltcaridr,result_table.fltnum,result_table.fltseqnum ) AS RANK FROM (");
			baseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS);
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagEnquiryFilterVO.getCurrentStatus())
					|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagEnquiryFilterVO.getCurrentStatus())
					|| MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
				log.info("The Status is <<ACCEPTED>><<TRANSFERRED>><<ASSIGNED>> ");
				if (MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getFromExportList())) {
					log.debug("Current Status:- ACP from mailExportList");
					acceptedmailbagFilterQuery = true;
					if (mailbagEnquiryFilterVO.getPageSize() > 0) {
						pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
								mailbagEnquiryFilterVO, baseQuery, mailbagEnquiryFilterVO.getPageSize());
					} else {
						pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
								mailbagEnquiryFilterVO, baseQuery);
					}
					log.debug("Base query passed,suffix query not required");
				} else {
					if ((mailbagEnquiryFilterVO.getCarrierId() == 0)
							&& (mailbagEnquiryFilterVO.getFlightNumber() == null
							|| mailbagEnquiryFilterVO.getFlightNumber().trim().length() == 0)) {
						acceptedmailbagFilterQuery = true;
						if (mailbagEnquiryFilterVO.getFromScreen() != null
								&& "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen())
								&& mailbagEnquiryFilterVO.getFlightNumber() == null) {
							rankQuery.append(baseQuery);
							if (mailbagEnquiryFilterVO.getPageSize() > 0) {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
										mailbagEnquiryFilterVO, rankQuery.toString(),
										mailbagEnquiryFilterVO.getPageSize());
							} else {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
										mailbagEnquiryFilterVO, rankQuery.toString());
							}
						} else {
							if (mailbagEnquiryFilterVO.getPageSize() > 0) {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
										mailbagEnquiryFilterVO, baseQuery, mailbagEnquiryFilterVO.getPageSize());
							} else {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
										mailbagEnquiryFilterVO, baseQuery);
							}
						}
						log.debug("Base query passed,suffix query not required");
					} else {
						if (mailbagEnquiryFilterVO.getCarrierId() > 0 && mailbagEnquiryFilterVO.getFromScreen() != null
								&& "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen())
								&& mailbagEnquiryFilterVO.getFlightNumber() == null) {
							log.debug("Carrier level and from assign continer");
							acceptedmailbagFilterQuery = true;
							rankQuery.append(baseQuery);
							if (mailbagEnquiryFilterVO.getPageSize() > 0) {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
										mailbagEnquiryFilterVO, rankQuery.toString(),
										mailbagEnquiryFilterVO.getPageSize());
							} else {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
										mailbagEnquiryFilterVO, rankQuery.toString());
							}
						} else if (mailbagEnquiryFilterVO.getCarrierId() > 0
								&& ("MAILEXPORTLIST".equals(mailbagEnquiryFilterVO.getFromScreen()))) {
							acceptedmailbagFilterQuery = true;
							baseQuery = baseQuery.concat(", ROWNUM AS RANK");
							if (mailbagEnquiryFilterVO.getPageSize() > 0) {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
										mailbagEnquiryFilterVO, baseQuery, mailbagEnquiryFilterVO.getPageSize());
							} else {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
										mailbagEnquiryFilterVO, baseQuery);
							}
						} else {
							log.debug("not from mailExportList,rank query used,suffix query to be added");
							rankQuery.append(baseQuery);
							if (mailbagEnquiryFilterVO.getPageSize() > 0) {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
										mailbagEnquiryFilterVO, rankQuery.toString(),
										mailbagEnquiryFilterVO.getPageSize());
							} else {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(new MailbagMapper(),
										mailbagEnquiryFilterVO, rankQuery.toString());
							}
						}
					}
				}
			} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagEnquiryFilterVO.getCurrentStatus())
					|| MailConstantsVO.MAIL_STATUS_NOTUPLIFTED.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
				log.info("" + "THE STATUS IS " + " " + mailbagEnquiryFilterVO.getCurrentStatus());
				rankQuery.append(baseQuery);
				if (mailbagEnquiryFilterVO.getPageSize() > 0) {
					pageableNativeQuery = new OffloadedMailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,
							rankQuery.toString(), mailbagEnquiryFilterVO.getPageSize());
				} else {
					pageableNativeQuery = new OffloadedMailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,
							rankQuery.toString());
				}
			} else if (MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
				int carrierId = mailbagEnquiryFilterVO.getCarrierId();
				ZonedDateTime flightDate = mailbagEnquiryFilterVO.getFlightDate();
				ZonedDateTime flightSqlDate = null;
				//TODO: Tobe corrected as part of NEO coding
				if (flightDate != null) {
					flightSqlDate = flightDate;
				}
//				String flightDateString = String.valueOf(flightSqlDate);
				baseQuery = getQueryManager().getNamedNativeQueryString(FIND_CAP_NOT_ACCPETED_MAILBAGS);
				rankQuery.append(baseQuery);
				if (mailbagEnquiryFilterVO.getPageSize() > 0) {
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getPageSize(), -1,
							rankQuery.toString(), new MailbagMapper(),PersistenceController.getEntityManager().currentSession());
				} else {
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getTotalRecords(),
							rankQuery.toString(), new MailbagMapper(),PersistenceController.getEntityManager().currentSession());
				}
				if (carrierId > 0) {
					pageableNativeQuery.append(" AND FLTCARIDR = ? ");
					pageableNativeQuery.setParameter(++index, carrierId);
				}
				if (mailbagEnquiryFilterVO.getCarrierCode() != null
						&& mailbagEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND FLTCARCOD  = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCarrierCode());
				}
				if (mailbagEnquiryFilterVO.getFlightNumber() != null
						&& mailbagEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND FLTNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
				}
				if (flightSqlDate != null) {
					pageableNativeQuery.append(" AND TO_NUMBER(TO_CHAR(?,'YYYYMMDD')) = ? ");
					pageableNativeQuery.setParameter(++index,
							Integer.parseInt(flightSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
				}
				pageableNativeQuery.append(" ) WHERE ");
				if (mailbagEnquiryFilterVO.getCompanyCode() != null
						&& mailbagEnquiryFilterVO.getCompanyCode().trim().length() > 0) {
					pageableNativeQuery.append(" CSGMAL.CMPCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
				}
				if (mailbagEnquiryFilterVO.getContainerNumber() != null
						&& mailbagEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND CSGMAL.ULDNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getContainerNumber());
				}
				ZonedDateTime scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
				ZonedDateTime scanToDate = mailbagEnquiryFilterVO.getScanToDate();
				if (scanFromDate != null) {
					pageableNativeQuery.append(" AND TO_NUMBER(TO_CHAR(CSGMST.CSGDAT,'YYYYMMDD')) >= ? ");
					pageableNativeQuery.setParameter(++index,
							Integer.parseInt(scanFromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
				}
				if (scanToDate != null) {
					pageableNativeQuery.append("  AND TO_NUMBER(TO_CHAR(CSGMST.CSGDAT,'YYYYMMDD')) <= ? ");
					pageableNativeQuery.setParameter(++index,
							Integer.parseInt(scanToDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
				}
				if (mailbagEnquiryFilterVO.getConsigmentNumber() != null
						&& mailbagEnquiryFilterVO.getConsigmentNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND CSGMAL.CSGDOCNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getConsigmentNumber());
				}
				if (mailbagEnquiryFilterVO.getUpuCode() != null
						&& mailbagEnquiryFilterVO.getUpuCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND POADTL.PARVAL = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getUpuCode());
				}
				if (mailbagEnquiryFilterVO.getReqDeliveryTime() != null) {
					String rqdDlvTime = mailbagEnquiryFilterVO.getReqDeliveryTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
					if (rqdDlvTime != null) {
						if (rqdDlvTime.contains("0000")) {
							pageableNativeQuery.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
						} else {
							pageableNativeQuery.append(" AND MALMST.REQDLVTIM = ?");
						}
						pageableNativeQuery.setParameter(++index,Timestamp.valueOf(
								(mailbagEnquiryFilterVO.getReqDeliveryTime().toLocalDateTime())));
					}
				}
				if (mailbagEnquiryFilterVO.getTransportServWindow() != null) {
					String transportServWindow = mailbagEnquiryFilterVO.getTransportServWindow().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
					if (transportServWindow != null) {
						if (transportServWindow.contains("0000")) {
							pageableNativeQuery.append(" AND TRUNC(MALMST.TRPSRVENDTIM) = ?");
						} else {
							pageableNativeQuery.append(" AND MALMST.TRPSRVENDTIM = ?");
						}
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getTransportServWindow());
					}
				}
				if (mailbagEnquiryFilterVO.getDespatchSerialNumber() != null
						&& mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.DSN = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
				}
				if (mailbagEnquiryFilterVO.getOoe() != null && !mailbagEnquiryFilterVO.getOoe().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.ORGEXGOFC = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
				}
				if (mailbagEnquiryFilterVO.getDoe() != null && !mailbagEnquiryFilterVO.getDoe().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.DSTEXGOFC = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
				}
				if (mailbagEnquiryFilterVO.getOriginAirportCode() != null
						&& !mailbagEnquiryFilterVO.getOriginAirportCode().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.ORGCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOriginAirportCode());
				}
				if (mailbagEnquiryFilterVO.getDestinationAirportCode() != null
						&& !mailbagEnquiryFilterVO.getDestinationAirportCode().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.DSTCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDestinationAirportCode());
				}
				if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
						&& mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALCTG = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
				}
				if (mailbagEnquiryFilterVO.getMailSubclass() != null
						&& mailbagEnquiryFilterVO.getMailSubclass().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALSUBCLS = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
				}
				if (mailbagEnquiryFilterVO.getYear() != null && mailbagEnquiryFilterVO.getYear().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.YER = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getYear());
				}
				if (mailbagEnquiryFilterVO.getReceptacleSerialNumber() != null
						&& mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.RSN = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReceptacleSerialNumber());
				}
				if (mailbagEnquiryFilterVO.getMailbagId() != null
						&& mailbagEnquiryFilterVO.getMailbagId().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALIDR = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
				}
				if (mailbagEnquiryFilterVO.getMailBagsToList() != null
						&& mailbagEnquiryFilterVO.getMailBagsToList().size() > 0) {
					String mailbagAppend = "AND MALMST.MALIDR IN (";
					for (int i = 1; i < mailbagEnquiryFilterVO.getMailBagsToList().size(); i++) {
						mailbagAppend = new StringBuffer().append(mailbagAppend).append("?,").toString();
					}
					mailbagAppend = new StringBuffer().append("?)").toString();
					pageableNativeQuery.append(mailbagAppend);
					for (String mailbag : mailbagEnquiryFilterVO.getMailBagsToList()) {
						pageableNativeQuery.setParameter(++index, mailbag);
					}
				}
				if (mailbagEnquiryFilterVO.getServiceLevel() != null
						&& mailbagEnquiryFilterVO.getServiceLevel().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALSRVLVL = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getServiceLevel());
				}
				if (mailbagEnquiryFilterVO.getOnTimeDelivery() != null
						&& mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.ONNTIMDLVFLG = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
				}
				if (mailbagEnquiryFilterVO.getCarditPresent() != null
						&& MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getCarditPresent())) {
					pageableNativeQuery.append(" AND EXISTS (SELECT 1 FROM MALCDTRCP RCP ")
							.append(" WHERE RCP.CMPCOD = CSGMAL.CMPCOD ").append(" AND RCP.RCPIDR     = MALMST.MALIDR ")
							.append(" ) ");
				}
			} else if (MailConstantsVO.MAIL_STATUS_NEW.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
				int carrierId = mailbagEnquiryFilterVO.getCarrierId();
				ZonedDateTime flightDate = mailbagEnquiryFilterVO.getFlightDate();
				ZonedDateTime flightSqlDate = null;
				//TODO:Tobe corrected as part of neo coding
			if (flightDate != null) {
				flightSqlDate=flightDate;
				}
//				String flightDateString = String.valueOf(flightSqlDate);
				baseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAILDETAILSFORNEW);
				rankQuery.append(baseQuery);
				if (mailbagEnquiryFilterVO.getPageSize() > 0) {
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getPageSize(), -1,
							rankQuery.toString(), new MailbagMapper(),PersistenceController.getEntityManager().currentSession());
				} else {
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getTotalRecords(),
							rankQuery.toString(), new MailbagMapper(),PersistenceController.getEntityManager().currentSession());
				}
				if (carrierId > 0) {
					pageableNativeQuery.append(" AND FLTCARIDR = ? ");
					pageableNativeQuery.setParameter(++index, carrierId);
				}
				if (mailbagEnquiryFilterVO.getCarrierCode() != null
						&& mailbagEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND FLTCARCOD  = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCarrierCode());
				}
				if (mailbagEnquiryFilterVO.getFlightNumber() != null
						&& mailbagEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND FLTNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
				}
				if (flightSqlDate != null) {
					pageableNativeQuery.append(" AND TO_NUMBER(TO_CHAR(?,'YYYYMMDD')) = ? ");
					pageableNativeQuery.setParameter(++index,
							Integer.parseInt(flightSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
				}
				pageableNativeQuery.append(" ) WHERE ");
				if (mailbagEnquiryFilterVO.getCompanyCode() != null
						&& mailbagEnquiryFilterVO.getCompanyCode().trim().length() > 0) {
					pageableNativeQuery.append(" CSGMAL.CMPCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
				}
				if (mailbagEnquiryFilterVO.getContainerNumber() != null
						&& mailbagEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND CSGMAL.ULDNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getContainerNumber());
				}
				ZonedDateTime scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
				ZonedDateTime scanToDate = mailbagEnquiryFilterVO.getScanToDate();
				if (scanFromDate != null && scanToDate != null) {
					pageableNativeQuery.append(" AND  CSGMST.CSGDAT BETWEEN  ?  ");
					pageableNativeQuery.setParameter(++index, scanFromDate);
					pageableNativeQuery.append(" AND  ?  ");
					pageableNativeQuery.setParameter(++index, scanToDate);
				}
				if (mailbagEnquiryFilterVO.getConsigmentNumber() != null
						&& mailbagEnquiryFilterVO.getConsigmentNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND CSGMAL.CSGDOCNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getConsigmentNumber());
				}
				if (mailbagEnquiryFilterVO.getUpuCode() != null
						&& mailbagEnquiryFilterVO.getUpuCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND POADTL.PARVAL = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getUpuCode());
				}
//				ZonedDateTime reqDeliveryTime = mailbagEnquiryFilterVO.getReqDeliveryTime();
				if (mailbagEnquiryFilterVO.getReqDeliveryTime() != null) {
					String rqdDlvTime = mailbagEnquiryFilterVO.getReqDeliveryTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
					if (rqdDlvTime != null) {
						if (rqdDlvTime.contains("0000")) {
							pageableNativeQuery.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
						} else {
							pageableNativeQuery.append(" AND MALMST.REQDLVTIM = ?");
						}
						pageableNativeQuery.setParameter(++index,Timestamp.valueOf(
								(mailbagEnquiryFilterVO.getReqDeliveryTime().toLocalDateTime())));
					}
				}
				if (mailbagEnquiryFilterVO.getTransportServWindow() != null) {
					String transportServWindow = mailbagEnquiryFilterVO.getTransportServWindow().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
					if (transportServWindow != null) {
						if (transportServWindow.contains("0000")) {
							pageableNativeQuery.append(" AND TRUNC(MALMST.TRPSRVENDTIM) = ?");
						} else {
							pageableNativeQuery.append(" AND MALMST.TRPSRVENDTIM = ?");
						}
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getTransportServWindow());
					}
				}
				if (mailbagEnquiryFilterVO.getDespatchSerialNumber() != null
						&& mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.DSN = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
				}
				if (mailbagEnquiryFilterVO.getOoe() != null && !mailbagEnquiryFilterVO.getOoe().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.ORGEXGOFC = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
				}
				if (mailbagEnquiryFilterVO.getDoe() != null && !mailbagEnquiryFilterVO.getDoe().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.DSTEXGOFC = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
				}
				if (mailbagEnquiryFilterVO.getOriginAirportCode() != null
						&& !mailbagEnquiryFilterVO.getOriginAirportCode().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.ORGCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOriginAirportCode());
				}
				if (mailbagEnquiryFilterVO.getDestinationAirportCode() != null
						&& !mailbagEnquiryFilterVO.getDestinationAirportCode().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.DSTCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDestinationAirportCode());
				}
				if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
						&& mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALCTG = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
				}
				if (mailbagEnquiryFilterVO.getMailSubclass() != null
						&& mailbagEnquiryFilterVO.getMailSubclass().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALSUBCLS = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
				}
				if (mailbagEnquiryFilterVO.getYear() != null && mailbagEnquiryFilterVO.getYear().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.YER = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getYear());
				}
				if (mailbagEnquiryFilterVO.getReceptacleSerialNumber() != null
						&& mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.RSN = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReceptacleSerialNumber());
				}
				if (mailbagEnquiryFilterVO.getMailbagId() != null
						&& mailbagEnquiryFilterVO.getMailbagId().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALIDR = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
				}
				if (mailbagEnquiryFilterVO.getMailBagsToList() != null
						&& mailbagEnquiryFilterVO.getMailBagsToList().size() > 0) {
					String mailbagAppend = "AND MALMST.MALIDR IN (";
					for (int i = 1; i < mailbagEnquiryFilterVO.getMailBagsToList().size(); i++) {
						mailbagAppend = new StringBuffer().append(mailbagAppend).append("?,").toString();
					}
					mailbagAppend = new StringBuffer().append("?)").toString();
					pageableNativeQuery.append(mailbagAppend);
					for (String mailbag : mailbagEnquiryFilterVO.getMailBagsToList()) {
						pageableNativeQuery.setParameter(++index, mailbag);
					}
				}
				if (mailbagEnquiryFilterVO.getServiceLevel() != null
						&& mailbagEnquiryFilterVO.getServiceLevel().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALSRVLVL = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getServiceLevel());
				}
				if (mailbagEnquiryFilterVO.getOnTimeDelivery() != null
						&& mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.ONNTIMDLVFLG = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
				}
				if (mailbagEnquiryFilterVO.getCarditPresent() != null
						&& MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getCarditPresent())) {
					pageableNativeQuery.append(" AND EXISTS (SELECT 1 FROM MALCDTRCP RCP ")
							.append(" WHERE RCP.CMPCOD = CSGMAL.CMPCOD ").append(" AND RCP.RCPIDR     = MALMST.MALIDR ")
							.append(" ) ");
				}
			} else {
				log.info("" + "THE STATUS IS--- " + " " + mailbagEnquiryFilterVO.getCurrentStatus());
				rankQuery.append(baseQuery);
				if (mailbagEnquiryFilterVO.getPageSize() > 0) {
					pageableNativeQuery = new ArrivalMailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,
							rankQuery.toString(), mailbagEnquiryFilterVO.getPageSize());
				} else {
					pageableNativeQuery = new ArrivalMailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,
							rankQuery.toString());
				}
			}
		}
		log.info("" + "Query is" + " " + pageableNativeQuery.toString());
		AcceptedMailBagFilterQuery.setRank(false);
		if (!acceptedmailbagFilterQuery && !allmailbagfilterQuery) {
			pageableNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		if (allmailbagfilterQuery) {
			pageableNativeQuery.append(")MST  where maxutcscndat =  utcscndat");
			pageableNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		return pageableNativeQuery.getPage(pageNumber);
	}

	/**
	 * @author A-1936 Note:- This method will be called to append the QueryDynamically in Places where a MailIn InventoryList From the Inventory List contains the Flight Number .. Which means its showing the Flight From where it got arrived ..
	 * @return
	 */
	private String appendArrivedFlightToInventory() {
		return new StringBuilder(" INNER JOIN MALHIS MALHIS ON  ").append("  MALMST.CMPCOD =MALHIS.CMPCOD  AND ")
				.append("  MALMST.MALSEQNUM =MALHIS.MALSEQNUM  AND ").append("  MALMST.SCNPRT = MALHIS.SCNPRT   ")
				.toString();
	}

	/**
	 * @author a-1936 This method is used to return all the containers which arealready assigned to a particular Flight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findFlightAssignedContainers(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findFlightAssignedContainers" + " Entering");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CONTAINERS_FORFLIGHT);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry.setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVO.getPol());
		Collection<ContainerVO> containerVOs = qry.getResultList(new ContainerMapperForFlight());
		if (CollectionUtils.isNotEmpty(containerVOs)) {
			var bookingBaseQuery = getQueryManager().getNamedNativeQueryString(FIND_BOOKING_TIME_FOR_DSNS_IN_CONTAINER);
			var bookingQry = new BookingDSNInContainerFilterQuery(containerVOs, bookingBaseQuery);
			Collection<ContainerVO> bookedContainerVOs = bookingQry.getResultList(new ContainerBookingMultiMapper());
			if (CollectionUtils.isNotEmpty(bookedContainerVOs)) {
				for (ContainerVO pageContainerVO : containerVOs) {
					for (ContainerVO bookedContainerVO : bookedContainerVOs) {
						if (pageContainerVO.getContainerNumber().equals(bookedContainerVO.getContainerNumber())) {
							pageContainerVO.setBookingTimeVOs(bookedContainerVO.getBookingTimeVOs());
							break;
						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(containerVOs)) {
			for (ContainerVO containerVo : containerVOs) {
				if (MailConstantsVO.BULK_TYPE.equals(containerVo.getType())) {
					log.info("THE BARROWS PRESENT CALCULATE WEIGHT");
					ContainerVO containerForSum = null;
					int indexForSum = 0;
					qry = getQueryManager().createNamedNativeQuery(FIND_BAGCOUNT_FORFLIGHT);
					qry.setParameter(++indexForSum, containerVo.getCompanyCode());
					StringBuilder containerNumber = new StringBuilder("BULK-").append(containerVo.getPou());
					qry.setParameter(++indexForSum, containerVo.getContainerNumber());
					qry.setParameter(++indexForSum, containerVo.getCarrierId());
					qry.setParameter(++indexForSum, containerVo.getFlightNumber());
					qry.setParameter(++indexForSum, containerVo.getFlightSequenceNumber());
					qry.setParameter(++indexForSum, containerVo.getSegmentSerialNumber());
					containerForSum = qry.getSingleResult(new BagCountMapper());
					if (containerForSum != null) {
						containerVo.setBags(containerForSum.getBags());
						containerVo.setWeight(containerForSum.getWeight());
						containerVo.setWarehouseCode(containerForSum.getWarehouseCode());
						containerVo.setLocationCode(containerForSum.getLocationCode());
						log.debug("" + "Bags >>>>>>>>>>>>>>" + " " + containerVo.getBags());
						log.debug("" + "Weight>>>>>>>>>>>>>>>>>" + " " + containerVo.getWeight());
					}
				}
			}
		}
		return containerVOs;
	}

	/**
	 * @author A-2037 This method is used to find the Damaged Mailbag Details
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<DamagedMailbagVO> findMailbagDamages(String companyCode, String mailbagId)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailbagDamages" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAG_DAMAGES);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailbagId);
		return query.getResultList(new DamagedMailBagMapper());
	}

	/**
	 * @author a-1936 This method is used to find the ContainerDetails
	 * @param searchContainerFilterVO
	 * @param pageNumber
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<ContainerVO> findContainers(SearchContainerFilterVO searchContainerFilterVO, int pageNumber)
			throws PersistenceException {
		PageableNativeQuery<ContainerVO> pgqry = null;
		Page<ContainerVO> containerVos = null;
		String nonSQ = "OTHERS";
		String outerQry = getQueryManager().getNamedNativeQueryString(FIND_CONTAINERS_OUTERQUERY);
		String baseQry = getQueryManager().getNamedNativeQueryString(FIND_CONTAINERS);
		String baseQry1 = null;
		if ("I".equals(searchContainerFilterVO.getOperationType())) {
			baseQry1 = new StringBuilder().append(baseQry).append(",ASGSEG.POL POL ").toString();
		} else {
			if (SEARCHMODE_DEST.equals(searchContainerFilterVO.getSearchMode())) {
				baseQry1 = new StringBuilder().append(baseQry).append(",NULL POL ").toString();
			} else {
				baseQry1 = new StringBuilder().append(baseQry).append(",ASG.ARPCOD POL ").toString();
			}
		}
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append(
				"RESULT_TABLE.CMPCOD , RESULT_TABLE.CONNUM,RESULT_TABLE.ASGPRT,RESULT_TABLE.FLTCARIDR,RESULT_TABLE.FLTNUM,RESULT_TABLE.FLTSEQNUM,RESULT_TABLE.LEGSERNUM) RANK FROM (");
		if (searchContainerFilterVO.getSubclassGroup() != null
				&& !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)) {
			rankQuery.append(outerQry);
			rankQuery.append(baseQry1);
		} else {
			rankQuery.append(baseQry1);
		}
		log.debug(CLASS_NAME + " : " + "findContainers" + " Entering");
		boolean isOracleDataSource = isOracleDataSource();
		searchContainerFilterVO.setBaseQuery(rankQuery.toString());
		searchContainerFilterVO.setOracleDataSource(isOracleDataSource);
		SearchContainerFilterQueryFeature searchContainerFilterQueryFeature=ContextUtil.getInstance().getBean(SearchContainerFilterQueryFeature.class);
		SearchContainerFilterVO searchContainerFilterVOs =searchContainerFilterQueryFeature.execute(searchContainerFilterVO) ;
			pgqry =searchContainerFilterVOs.getPgqry() ;

		containerVos = pgqry.getPage(pageNumber);
		if (containerVos != null && containerVos.size() > 0) {
			for (ContainerVO containerVO : containerVos) {
				findOffloadedInfoForCOntainer(containerVO);
				Query queryForSum = null;
				int index = 0;
				ContainerVO containerForSum = null;
				if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
					log.info("THE BARROWS PRESENT CALCULATE WEIGHT");
					if (containerVO.getFlightSequenceNumber() > 0) {
						queryForSum = getQueryManager().createNamedNativeQuery(FIND_BAGCOUNT_FORFLIGHT);
						queryForSum.setParameter(++index, containerVO.getCompanyCode());
						queryForSum.setParameter(++index, containerVO.getContainerNumber());
						queryForSum.setParameter(++index, containerVO.getCarrierId());
						queryForSum.setParameter(++index, containerVO.getFlightNumber());
						queryForSum.setParameter(++index, containerVO.getFlightSequenceNumber());
						queryForSum.setParameter(++index, containerVO.getSegmentSerialNumber());
						containerForSum = queryForSum.getSingleResult(new BagCountMapper());
					}
				}
				if (containerVO.getFlightSequenceNumber() <= 0) {
					queryForSum = getQueryManager().createNamedNativeQuery(FIND_BAGCOUNT_FORDESTINATION);
					queryForSum.setParameter(++index, containerVO.getCompanyCode());
					queryForSum.setParameter(++index, containerVO.getAssignedPort());
					queryForSum.setParameter(++index, containerVO.getCarrierId());
					queryForSum.setParameter(++index, containerVO.getContainerNumber());
					containerForSum = queryForSum.getSingleResult(new BagCountMapper());
				}
				if (containerForSum != null) {
					if (containerVO.getFlightSequenceNumber() > 0
							&& MailConstantsVO.FLAG_YES.equals(containerVO.getArrivedStatus())) {
						containerVO.setBags(containerForSum.getReceivedBags());
						containerVO.setWeight(containerForSum.getReceivedWeight());
					} else if (containerVO.getFlightSequenceNumber() < 0) {
						containerVO.setBags(containerForSum.getBags());
						containerVO.setWeight(containerForSum.getWeight());
					}
					containerVO.setWarehouseCode(containerForSum.getWarehouseCode());
					containerVO.setLocationCode(containerForSum.getLocationCode());
					log.debug("" + "Bags >>>>>>>>>>>>>>" + " " + containerVO.getBags());
					log.debug("" + "Weight>>>>>>>>>>>>>>>>>" + " " + containerVO.getWeight());
				}
			}
		}
		log.debug("" + "containerVos>>>>>>>>>>>>>>>>>" + " " + containerVos);
		return containerVos;
	}

	/**
	 * @author A-3429 This method is used to find theoffload information for the container
	 * @return PostalAdministrationVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void findOffloadedInfoForCOntainer(ContainerVO containerVO) throws PersistenceException {
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug(CLASS_NAME + " : " + "findOffloadedInfoForCOntainer" + " Entering");
		List<ContainerVO> containerForOffloadDts = null;
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_OFFLOADEDINFO_FORCONTAINER);
		qry.setParameter(++index, containerVO.getCompanyCode());
		qry.setParameter(++index, containerVO.getContainerNumber());
		qry.setParameter(++index, containerVO.getAssignedPort());
		containerForOffloadDts = qry.getResultList(new OffloadedFlightDetailsMapper());
		Collection<String> offloadDtls = null;
		int offloadCount = 0;
		if (containerForOffloadDts != null) {
			offloadDtls = new ArrayList<String>();
			for (ContainerVO container : containerForOffloadDts) {
				offloadDtls.add(container.getOffloadedDescription());
				offloadCount = offloadCount + container.getOffloadCount();
			}
			containerVO.setOffloadedInfo(offloadDtls);
			containerVO.setOffloadCount(offloadCount);
		}
	}

	/**
	 * @author a-1936 This method is used to return all the containers which arealready assigned to a particular destination
	 * @param destinationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findDestinationAssignedContainers(DestinationFilterVO destinationFilterVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findDestinationAssignedContainers" + " Entering");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CONTAINERS_FORDESTINATION);
		qry.setParameter(++index, destinationFilterVO.getCompanyCode());
		qry.setParameter(++index, destinationFilterVO.getCarrierId());
		qry.setParameter(++index, destinationFilterVO.getAirportCode());
		if (destinationFilterVO.getDestination() != null && destinationFilterVO.getDestination().trim().length() > 0) {
			qry.append("  AND  MST.DSTCOD = ? ");
			qry.setParameter(++index, destinationFilterVO.getDestination());
		} else {
			qry.append("  AND MST.DSTCOD IS NULL ");
		}
		Collection<ContainerVO> coll = qry.getResultList(new ContainerMapper());
		if (coll != null && coll.size() > 0) {
			for (ContainerVO containerVo : coll) {
				log.info("THE BARROWS PRESENT CALCULATE WEIGHT");
				ContainerVO containerForSum = null;
				int indexForSum = 0;
				qry = getQueryManager().createNamedNativeQuery(FIND_BAGCOUNT_FORDESTINATION);
				log.debug("" + "THE BARROWS PRESENT CALCULATE WEIGHT" + " " + qry);
				qry.setParameter(++indexForSum, containerVo.getCompanyCode());
				qry.setParameter(++indexForSum, containerVo.getAssignedPort());
				qry.setParameter(++indexForSum, containerVo.getCarrierId());
				qry.setParameter(++indexForSum, containerVo.getContainerNumber());
				containerForSum = qry.getSingleResult(new BagCountMapper());
				log.info("THE BARROWS PRESENT CALCULATE WEIGHT");
				if (containerForSum != null) {
					containerVo.setBags(containerForSum.getBags());
					containerVo.setWeight(containerForSum.getWeight());
					containerVo.setWarehouseCode(containerForSum.getWarehouseCode());
					containerVo.setLocationCode(containerForSum.getLocationCode());
					log.debug("" + "Bags >>>>>>>>>>>>>>" + " " + containerVo.getBags());
					log.debug("" + "Weight>>>>>>>>>>>>>>>>>" + " " + containerVo.getWeight());
				}
			}
		}
		return coll;
	}

	/**
	 * Finds the details for mailbag manifest
	 * @param opFlightVO
	 */
	public MailManifestVO findMailbagManifestDetails(OperationalFlightVO opFlightVO) throws PersistenceException {
		log.debug(this.getClass().getSimpleName() + " : " + "findMailbagManifestDetails" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAG_MANIFEST);
		int idx = 0;
		query.setParameter(++idx, opFlightVO.getCompanyCode());
		query.setParameter(++idx, opFlightVO.getCarrierId());
		query.setParameter(++idx, opFlightVO.getFlightNumber());
		query.setParameter(++idx, opFlightVO.getFlightSequenceNumber());
		query.setParameter(++idx, opFlightVO.getPol());
		Collection<MailManifestVO> manifests = query.getResultList(new MailbagManifestMultiMapper());
		log.debug(this.getClass().getSimpleName() + " : " + "findMailbagManifestDetails" + " Exiting");
		return manifests.iterator().next();
	}

	/**
	 * TODO Purpose Jan 18, 2007, A-1739
	 * @param opFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailManifestVO findMailAWBManifestDetails(OperationalFlightVO opFlightVO) throws PersistenceException {
		log.debug(this.getClass().getSimpleName() + " : " + "findMailbagManifestDetails" + " Entering");
		log.debug("" + "THE OPERATIONAL FLIGHT VO FROM OPERATIONS" + " " + opFlightVO);
		Query query = getQueryManager().createNamedNativeQuery(FIND_AWB_MANIFEST);
		int idx = 0;
		query.setParameter(++idx, opFlightVO.getCompanyCode());
		query.setParameter(++idx, opFlightVO.getCarrierId());
		query.setParameter(++idx, opFlightVO.getFlightNumber());
		query.setParameter(++idx, opFlightVO.getFlightSequenceNumber());
		query.setParameter(++idx, opFlightVO.getPol());
		Collection<MailManifestVO> manifests = query.getResultList(new MailAWBManifestMultiMapper());
		log.debug(this.getClass().getSimpleName() + " : " + "findMailbagManifestDetails" + " Exiting");
		return manifests.iterator().next();
	}

	public MailManifestVO findDSNMailbagManifest(OperationalFlightVO opFlightVO) throws PersistenceException {
		log.debug(this.getClass().getSimpleName()+" : "+ "findDSNMailbagManifest");
		log.debug(""+ "THE OPERATIONAL FLIGHT VO FROM OPERATIONS"+" "+ opFlightVO);
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_DSN_MAILBAG_MANIFEST);
		int idx = 0;

		query.setParameter(++idx, opFlightVO.getCompanyCode());
		query.setParameter(++idx, opFlightVO.getCarrierId());
		query.setParameter(++idx, opFlightVO.getFlightNumber());
		query.setParameter(++idx, opFlightVO.getFlightSequenceNumber());
		query.setParameter(++idx, opFlightVO.getPol());
		Collection<MailManifestVO> manifests = query.getResultList(new DSNMailbagManifestMultiMapper());
		log.debug(this.getClass().getSimpleName()+" : "+"findDSNMailbagManifest");
		return manifests.iterator().next();
	}
	public MailManifestVO findManifestbyDestination(
			OperationalFlightVO opFlightVO) throws SystemException,
			PersistenceException {
		log.debug(this.getClass().getSimpleName(),
				"findMailbagManifestDetails");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MANIFEST_DESTNCTG);
		int idx = 0;
		query.setParameter(++idx, opFlightVO.getCompanyCode());
		query.setParameter(++idx, opFlightVO.getCarrierId());
		query.setParameter(++idx, opFlightVO.getFlightNumber());
		query.setParameter(++idx, opFlightVO.getFlightSequenceNumber());
		query.setParameter(++idx, opFlightVO.getPol());
		Collection<MailManifestVO> manifests = query
				.getResultList(new DestinationManifestMultiMapper());
		log.debug(this.getClass().getSimpleName(),
				"findMailbagManifestDetails");
		return manifests.iterator().next();
	}
	/**
	 * TODO Purpose Jan 31, 2007, A-1739
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * */
	public Collection<AWBDetailVO> findAWBDetailsForFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAWBDetailsForFlight" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_AWB_FORFLIGHT);
		int idx = 0;
		query.setParameter(++idx, operationalFlightVO.getCompanyCode());
		query.setParameter(++idx, operationalFlightVO.getCarrierId());
		query.setParameter(++idx, operationalFlightVO.getFlightNumber());
		query.setParameter(++idx, operationalFlightVO.getFlightSequenceNumber());
		query.setParameter(++idx, operationalFlightVO.getPol());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAWBDetailsForFlight" + " Exiting");
		return query.getResultList(new AWBDetailsMapper());
	}


	/**
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	  */
	public List<ContainerDetailsVO> findArrivedContainers(MailArrivalFilterVO mailArrivalFilterVO)
			throws PersistenceException {
		String baseQuery = getQueryManager().getNamedNativeQueryString(FIND_ULDS_FOR_ARRIVAL);
		Query query = new MailArrivalFilterQuery(mailArrivalFilterVO, baseQuery);
		return query.getResultList(new MailArrivalMultiMapper());
	}

	/**
	 * @author a-1936 ADDED AS THE PART OF NCA-CR findMailDetail This method isused to find out the MailDetais For all MailBags for which Resdits are not sent and having the Search Mode as Despatch..
	 * @param despatchDetailVos
	 * @param unsentResditEvent
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findMailDetailsForDespatches(Collection<DespatchDetailsVO> despatchDetailVos,
															  String unsentResditEvent) throws PersistenceException {
		Collection<MailbagVO> mailBagVos = null;
		Collection<MailbagVO> mailBagsForUnsentResdits = null;
		Query qry = null;
		for (DespatchDetailsVO despatchVo : despatchDetailVos) {
			int index = 0;
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILDETAILS_FORUNSENTRESDITS);
			if (despatchVo.getCompanyCode() != null && despatchVo.getCompanyCode().trim().length() > 0) {
				qry.append("  AND CDTMST.CMPCOD = ? ");
				qry.setParameter(++index, despatchVo.getCompanyCode());
			}
			if (despatchVo.getPaCode() != null && despatchVo.getPaCode().trim().length() > 0) {
				qry.append("  AND CDTMST.SDRIDR= ? ");
				qry.setParameter(++index, despatchVo.getPaCode());
			}
			if (despatchVo.getConsignmentNumber() != null && despatchVo.getConsignmentNumber().trim().length() > 0) {
				qry.append("  AND CDTMST.CSGDOCNUM=? ");
				qry.setParameter(++index, despatchVo.getConsignmentNumber());
			}
			if (despatchVo.getConsignmentDate() != null) {
				qry.append("  AND TRUNC(CDTMST.CSGCMPDAT) = ? ");
				qry.setParameter(++index, despatchVo.getConsignmentDate().toLocalDateTime());
			}
			if (despatchVo.getOriginOfficeOfExchange() != null
					&& despatchVo.getOriginOfficeOfExchange().trim().length() > 0) {
				qry.append("  AND  MALMST.ORGEXGOFC =? ");
				qry.setParameter(++index, despatchVo.getOriginOfficeOfExchange());
			}
			if (despatchVo.getDestinationOfficeOfExchange() != null
					&& despatchVo.getDestinationOfficeOfExchange().trim().length() > 0) {
				qry.append("  AND  MALMST.DSTEXGOFC=?  ");
				qry.setParameter(++index, despatchVo.getDestinationOfficeOfExchange());
			}
			if (despatchVo.getMailCategoryCode() != null && despatchVo.getMailCategoryCode().trim().length() > 0) {
				qry.append("  AND MALMST.MALCTG=?  ");
				qry.setParameter(++index, despatchVo.getMailCategoryCode());
			}
			if (despatchVo.getDsn() != null && despatchVo.getDsn().trim().length() > 0) {
				qry.append("  AND MALMST.DSN=? ");
				qry.setParameter(++index, despatchVo.getDsn());
			}
			if (despatchVo.getYear() > 0) {
				qry.append("  AND MALMST.YER=? ");
				qry.setParameter(++index, despatchVo.getYear());
			}
			qry.append("  AND MALRDT.EVTCOD = ? ");
			qry.setParameter(++index, unsentResditEvent);
			qry.append("  AND MALRDT.RDTSND <> 'S' ");
			mailBagsForUnsentResdits = qry.getResultList(new MailbagsForUnsentResditMapper());
			if (mailBagsForUnsentResdits != null && mailBagsForUnsentResdits.size() > 0) {
				for (MailbagVO mailRdt : mailBagsForUnsentResdits) {
					mailRdt.setPaCode(despatchVo.getPaCode());
				}
				if (mailBagVos == null) {
					mailBagVos = new ArrayList<MailbagVO>();
				}
				mailBagVos.addAll(mailBagsForUnsentResdits);
			}
		}
		return mailBagVos;
	}

	/**
	 * @author a-1936 ADDED AS THE PART OF NCA-CR This method is used to findout the MailDetais For all MailBags for which Resdits are not sent and having the Search Mode as Document..
	 * @param consignmentDocumentVos
	 * @param unsentResditEvent
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findMailDetailsForDocument(Collection<ConsignmentDocumentVO> consignmentDocumentVos,
															String unsentResditEvent) throws PersistenceException {
		Collection<MailbagVO> finalMailBagVos = null;
		Collection<MailbagVO> mailBagsForUnsentResdits = null;
		Query qry = null;
		for (ConsignmentDocumentVO documentVo : consignmentDocumentVos) {
			int index = 0;
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILDETAILS_FORUNSENTRESDITS);
			if (documentVo.getCompanyCode() != null && documentVo.getCompanyCode().trim().length() > 0) {
				qry.append("  AND CSGMST.CMPCOD = ?  ");
				qry.setParameter(++index, documentVo.getCompanyCode());
			}
			if (documentVo.getPaCode() != null && documentVo.getPaCode().trim().length() > 0) {
				qry.append("  AND CSGMST.POACOD = ? ");
				qry.setParameter(++index, documentVo.getPaCode());
			}
			if (documentVo.getConsignmentNumber() != null && documentVo.getConsignmentNumber().trim().length() > 0) {
				qry.append("  AND CSGMST.CSGDOCNUM=? ");
				qry.setParameter(++index, documentVo.getConsignmentNumber());
			}
			if (documentVo.getConsignmentDate() != null) {
				qry.append("  AND TRUNC(CSGMST.CSGDAT) = ? ");
				qry.setParameter(++index, documentVo.getConsignmentDate().toLocalDateTime());
			}
			mailBagsForUnsentResdits = qry.getResultList(new MailbagsForUnsentResditMapper());
			if (mailBagsForUnsentResdits != null && mailBagsForUnsentResdits.size() > 0) {
				if (finalMailBagVos == null) {
					finalMailBagVos = new ArrayList<MailbagVO>();
				}
				finalMailBagVos.addAll(mailBagsForUnsentResdits);
			}
		}
		return finalMailBagVos;
	}

	/**
	 * @param partyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findPartyName(String companyCode, String partyCode) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPartyName" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_PARTY_NAME);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, partyCode);
		query.setParameter(++idx, partyCode);
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, partyCode);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPartyName" + " Exiting");
		String partyName = "";
		return query.getSingleResult(getStringMapper("PTYNAM"));
	}

	/**
	 * TODO Purpose Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param receptacleID
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 * */
	public String findOffloadReasonForMailbag(String companyCode, String receptacleID) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findOffloadReasonForMailbag" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_OFFLOADREASON_FOR_MAILBAG);
		query.setParameter(1, companyCode);
		query.setParameter(2, receptacleID);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findOffloadReasonForMailbag" + " Exiting");
		return query.getSingleResult(getStringMapper("OFLRSNCOD"));
	}

	/**
	 * TODO Purpose Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param containerNumber
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 * */
	public String findOffloadReasonForULD(String companyCode, String containerNumber) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findOffloadReasonForULD" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_OFFLOADREASON_FOR_CONTAINER);
		query.setParameter(1, companyCode);
		query.setParameter(2, containerNumber);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findOffloadReasonForULD" + " Exiting");
		return query.getSingleResult(getStringMapper("OFLRSNCOD"));
	}

	/**
	 * Find the damage reason for mailbag Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param receptacleID
	 * @param airportCode
	 * @return the damage reason
	 * @throws SystemException
	 * @throws PersistenceException
	 * */
	public String findDamageReason(String companyCode, String receptacleID, String airportCode)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findDamageReason" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAG_DAMAGE_REASON);
		query.setParameter(1, companyCode);
		query.setParameter(2, receptacleID);
		query.setParameter(3, airportCode);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findDamageReason" + " Exiting");
		return query.getSingleResult(getStringMapper("DMGCOD"));
	}

	/**
	 * Added by A-4072
	 */
	public Collection<CarditReferenceInformationVO> findCCForSendResdit(ConsignmentInformationVO consgmntInfo)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCCForSendResdit" + " Entering");
		Collection<CarditReferenceInformationVO> carditRefInfoVos = null;
		if (consgmntInfo == null) {
			return null;
		}
		Query query = getQueryManager().createNamedNativeQuery(FIND_CC_SENDRESDIT);
		query.setSensitivity(true);
		int index = 0;
		query.append(" And MST.CSGDOCNUM=?");
		query.setParameter(++index, consgmntInfo.getConsignmentID());
		carditRefInfoVos = query.getResultList(new SendResditMultiMapper());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCCForSendResdit" + " Exiting");
		return carditRefInfoVos;
	}

	/**
	 */
	public HashMap<String, String> findRecepientForXXResdits(
			Collection<ConsignmentInformationVO> consignmentInformationVOsForXX) throws PersistenceException {
		ContextUtil contextUtil = ContextUtil.getInstance();
		HashMap<String, String> xxResditRecepientMap = new HashMap<String, String>();
		if (consignmentInformationVOsForXX == null || consignmentInformationVOsForXX.size() <= 0) {
			return xxResditRecepientMap;
		}
		int idx1 = 0;
		int idx2 = 0;
		boolean first = true;
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		Query queryMail = null;
		Query queryUld = null;
		queryMail = getQueryManager().createNamedNativeQuery(FIND_RPT_FOR_XXMAILBAGS);
		queryMail.setParameter(++idx1, logonAttributes.getCompanyCode());
		queryUld = getQueryManager().createNamedNativeQuery(FIND_RPT_FOR_XXULDS);
		queryUld.setParameter(++idx2, logonAttributes.getCompanyCode());
		StringBuilder consignments = new StringBuilder();
		for (ConsignmentInformationVO consignmentInformationVO : consignmentInformationVOsForXX) {
			if (first) {
				first = false;
				consignments.append("AND	rdt.cdtkey IN ( ?");
				queryMail.setParameter(++idx1, consignmentInformationVO.getConsignmentID());
				queryUld.setParameter(++idx2, consignmentInformationVO.getConsignmentID());
			} else {
				consignments.append(",? ");
				queryMail.setParameter(++idx1, consignmentInformationVO.getConsignmentID());
				queryUld.setParameter(++idx2, consignmentInformationVO.getConsignmentID());
			}
		}
		consignments.append(" ) ");
		queryMail.append(consignments.toString());
		queryMail.append("UNION");
		queryUld.append(consignments.toString());
		queryMail.combine(queryUld);
		return ((ArrayList<HashMap<String, String>>) queryMail.getResultList(new XXResditRecepientMultiMapper()))
				.get(0);
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findOnlineFlightsAndConatiners(java.lang.String) Added by 			: A-4809 on Sep 29, 2015 Used for 	: Parameters	:	@param companyCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	@Override
	public List<MailArrivalVO> findOnlineFlightsAndConatiners(String companyCode) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findOnlineFlightsAndConatiners" + " Entering");
		List<MailArrivalVO> mailArrivalVOs = null;
		int index = 0;
		String query = getQueryManager().getNamedNativeQueryString(FIND_ONLINEFLIGHTS_CONTAINERS);
		String dynamicquery = null;
		if (isOracleDataSource()) {
			dynamicquery = " AND ( ( TO_NUMBER(TO_CHAR(LEG.ATA,'YYYYMMDDHH24MISS')) <= (SELECT TO_NUMBER(TO_CHAR(SYSDATE -(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS')) FROM SHRUSEARPPAR ARPPAR "
					+ "WHERE ARPPAR.CMPCOD = LEG.CMPCOD " + "AND ARPPAR.PARCOD ='mailtracking.defaults.arrivalOffset' "
					+ "AND ARPPAR.ARPCOD = ASGFLTSEG.POU " + "AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) >0 )) "
					+ "OR ( TO_NUMBER(TO_CHAR(LEG.STA,'YYYYMMDDHH24MISS'))  <= (SELECT TO_NUMBER(TO_CHAR(SYSDATE -(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))  FROM SHRUSEARPPAR ARPPAR ";
		} else {
			dynamicquery = " AND ( ( TO_NUMBER(TO_CHAR(LEG.ATA,'YYYYMMDDHH24MISS')) <= (SELECT TO_NUMBER(TO_CHAR(now() -  interval '1 day' *(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS')) FROM SHRUSEARPPAR ARPPAR "
					+ "WHERE ARPPAR.CMPCOD = LEG.CMPCOD " + "AND ARPPAR.PARCOD ='mailtracking.defaults.arrivalOffset' "
					+ "AND ARPPAR.ARPCOD = ASGFLTSEG.POU " + "AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) >0 )) "
					+ "OR ( TO_NUMBER(TO_CHAR(LEG.STA,'YYYYMMDDHH24MISS'))  <= (SELECT TO_NUMBER(TO_CHAR(now() -  interval '1 day' *(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))  FROM SHRUSEARPPAR ARPPAR ";
		}
		query = String.format(query, dynamicquery);
		Query qry = getQueryManager().createNativeQuery(query);
		qry.setParameter(++index, companyCode);
		return qry.getResultList(new AcquitContainerFromFlightMultiMapper());
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findFlightsForArrival(java.lang.String) Added by 			: A-4809 on Sep 30, 2015 Used for 	: Parameters	:	@param companyCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	@Override
	public Collection<OperationalFlightVO> findFlightsForArrival(String companyCode) throws PersistenceException {
		Collection<OperationalFlightVO> flightVOs = null;
		int index = 0;
		String query = getQueryManager().getNamedNativeQueryString(FIND_FLIGHTS_ARRIVAL);
		String dynamicquery = null;
		if (isOracleDataSource()) {
			dynamicquery = "AND ( ( TO_NUMBER(TO_CHAR(LEG.ATA,'YYYYMMDDHH24MISS')) <="
					+ " (SELECT TO_NUMBER(TO_CHAR(SYSDATE -(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))"
					+ " FROM SHRUSEARPPAR ARPPAR" + " WHERE ARPPAR.CMPCOD = LEG.CMPCOD"
					+ " AND ARPPAR.PARCOD ='mailtracking.defaults.arrivalOffset'" + " AND ARPPAR.ARPCOD= ASGFLTSEG.POU"
					+ " AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) > 0))"
					+ " OR ( TO_NUMBER(TO_CHAR(LEG.STA,'YYYYMMDDHH24MISS')) <="
					+ " (SELECT TO_NUMBER(TO_CHAR(SYSDATE -(TO_NUMBER(COALESCE(ARPPAR1.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))";
		} else {
			dynamicquery = "AND ( ( TO_NUMBER(TO_CHAR(LEG.ATA,'YYYYMMDDHH24MISS')) <="
					+ " (SELECT TO_NUMBER(TO_CHAR(now() -  interval '1 day' *(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))"
					+ " FROM SHRUSEARPPAR ARPPAR" + " WHERE ARPPAR.CMPCOD = LEG.CMPCOD"
					+ " AND ARPPAR.PARCOD ='mailtracking.defaults.arrivalOffset'" + " AND ARPPAR.ARPCOD= ASGFLTSEG.POU"
					+ " AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) > 0))"
					+ " OR ( TO_NUMBER(TO_CHAR(LEG.STA,'YYYYMMDDHH24MISS')) <="
					+ " (SELECT TO_NUMBER(TO_CHAR(now() -  interval '1 day' *(TO_NUMBER(COALESCE(ARPPAR1.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))";
		}
		query = String.format(query, dynamicquery);
		Query qry = getQueryManager().createNativeQuery(query);
		qry.setParameter(++index, companyCode);
		return qry.getResultList(new FlightArrivalMapper());
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findArrivalDetailsForReleasingMails(com.ibsplc.icargo.business.mailtracking.defaults.vo.OperationalFlightVO) Added by 			: A-4809 on Sep 30, 2015 Used for 	: Parameters	:	@param flightVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	@Override
	public List<ContainerDetailsVO> findArrivalDetailsForReleasingMails(OperationalFlightVO flightVO)
			throws PersistenceException {
		List<ContainerDetailsVO> containerDetailsVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_ARRIVAL);
		qry.setParameter(++index, flightVO.getCompanyCode());
		qry.setParameter(++index, flightVO.getCarrierId());
		qry.setParameter(++index, flightVO.getFlightNumber());
		qry.setParameter(++index, flightVO.getFlightSequenceNumber());
		qry.setParameter(++index, flightVO.getPou());
		return qry.getResultList(new MailbagForArrivalMultiMapper());
	}

	/**
	 * @author A-1885
	 */
	public Collection<OperationalFlightVO> findFlightForMailOperationClosure(String companyCode, int time,
																			 String airportCode) throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailOnHandDetails" + " Entering");
		int index = 0;
		ZonedDateTime date = null;
		LoginProfile logon = contextUtil.callerLoginProfile();
		date = localDateUtil.getLocalDate(logon.getAirportCode(), true);
		ZonedDateTime gmtCovTime = localDateUtil.toUTCTime(date);
		Collection<OperationalFlightVO> flights = null;
		String qryString = getQueryManager().getNamedNativeQueryString(FIND_FLIGHTSFOR_MAILCLOSURE);
		String modifiedStr1 = null;
		if (isOracleDataSource()) {
			modifiedStr1 = "( (LEG.ATDUTC IS NOT NULL AND ? >= (LEG.ATDUTC + to_number(COALESCE(ARPPAR.PARVAL,'0'))/24)) OR LEGSTA = 'CAN')";
		} else {
			modifiedStr1 = "( (LEG.ATDUTC IS NOT NULL AND ? >= (LEG.ATDUTC +(COALESCE(ARPPAR.PARVAL::interval,'0')))) OR LEGSTA = 'CAN')";
		}
		qryString = String.format(qryString, modifiedStr1);
		Query qry = getQueryManager().createNativeQuery(qryString);
		qry.setParameter(++index, gmtCovTime);
		qry.setParameter(++index, companyCode);
		if (time > 0 && isOracleDataSource()) {
			qry.append(
					" AND TO_NUMBER(TO_CHAR(MTK.FLTDAT,'YYYYMMDD')) > TO_NUMBER(TO_CHAR(current_date,'YYYYMMDD')) - ? ");
			qry.setParameter(++index, time);
		}
		if (time > 0 && !isOracleDataSource()) {
			qry.append(
					" AND TO_NUMBER(TO_CHAR(MTK.FLTDAT,'YYYYMMDD::text')) > TO_NUMBER(TO_CHAR(current_date,'YYYYMMDD::text')) - ? ");
			qry.setParameter(++index, time);
		}
		return qry.getResultList(new FlightsForClosureMapper());
	}

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findAnyContainerInAssignedFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAnyContainerInAssignedFlight" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_ANYCONTAINER_IN_ASSIGNED_FLIGHT);
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPol());
		qry.setParameter(6, operationalFlightVO.getLegSerialNumber());
		if (isOracleDataSource()) {
			qry.append(" AND ROWNUM=1");
		} else {
			qry.append(" LIMIT 1");
		}
		Mapper<String> stringMapper = getStringMapper("CON");
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAnyContainerInAssignedFlight" + " Exiting");
		return qry.getSingleResult(stringMapper);
	}

	/**
	 * @author A-5166
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<OperationalFlightVO> findImportFlghtsForArrival(String companyCode) throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug(CLASS_NAME + " : " + "findImportFlghtsForArrival" + " Entering");
		String qryString = getQueryManager()
				.getNamedNativeQueryString(MAILTRACKING_DEFAULTS_FINDIMPORTFLIGHTSFORARRIVAL);
		String modifiedStr1 = null;
		if (isOracleDataSource()) {
			modifiedStr1 = "AND LEG.STAUTC BETWEEN ( ? + 1 - TO_NUMBER(COALESCE(PERIOD1.PARVAL,'0')) - TO_NUMBER(COALESCE(OFFSET1.PARVAL,'0'))) AND (? + 1 - TO_NUMBER(COALESCE(OFFSET1.PARVAL,'0')))";
		} else {
			modifiedStr1 = "AND LEG.STAUTC BETWEEN ((cast(? as timestamp)  + INTERVAL '1 day') + make_interval(days =>1) - make_interval(days => (COALESCE(PERIOD1.PARVAL::INT, '0'))) - make_interval(days => to_number(COALESCE(OFFSET1.PARVAL, '0')::INT))) AND ((cast(? as timestamp)  + INTERVAL '1 day') + make_interval(days =>1) - make_interval(days => (COALESCE(OFFSET1.PARVAL::INT, '0'))))";
		}
		qryString = String.format(qryString, modifiedStr1);
		Query query = getQueryManager().createNativeQuery(qryString);
		int index = 0;
		ZonedDateTime date = null;
		LoginProfile logon = contextUtil.callerLoginProfile();
		date = localDateUtil.getLocalDate(logon.getAirportCode(), true);
		ZonedDateTime gmtCovTime = localDateUtil.toUTCTime(date);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, gmtCovTime);
		query.setParameter(++index, gmtCovTime);
		return query.getResultList(new OperationalFlightMapper());
	}

	/**
	 */
	public Page<DespatchDetailsVO> findDSNs(DSNEnquiryFilterVO dSNEnquiryFilterVO, int pageNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findCapturedNotAcceptedDSNs" + " Entering");
		PageableNativeQuery<DespatchDetailsVO> pgqry = null;
		Page<DespatchDetailsVO> despatchDetailsVOs = null;
		String baseQuery = null;
		StringBuilder rankQuery = null;
		int index = 0;
		baseQuery = getQueryManager().getNamedNativeQueryString(FIND_DESPATCHES);
		rankQuery = new StringBuilder(MAILTRACKING_DEFAULTS_ROWNUM_QUERY).append(baseQuery);
		pgqry = new PageableNativeQuery<DespatchDetailsVO>(dSNEnquiryFilterVO.getTotalRecords(), rankQuery.toString(),
				new AssignedDSNMapper(),PersistenceController.getEntityManager().currentSession());
		if (dSNEnquiryFilterVO.getCompanyCode() != null && dSNEnquiryFilterVO.getCompanyCode().trim().length() > 0) {
			pgqry.append("  MALMST.CMPCOD = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getCompanyCode().trim());
		}
		if ("true".equals(dSNEnquiryFilterVO.getPltEnabledFlag())) {
			pgqry.append(" AND MALMST.MALTYP = ? ");
			if (FLAG_FALSE.equalsIgnoreCase(dSNEnquiryFilterVO.getPltEnabledFlag())) {
				pgqry.setParameter(++index, "D");
			} else {
				log.debug("inside plt$$$$ ");
				pgqry.setParameter(++index, "M");
			}
		}
		if (dSNEnquiryFilterVO.getConsignmentNumber() != null
				&& dSNEnquiryFilterVO.getConsignmentNumber().trim().length() > 0) {
			pgqry.append(" AND  MALMST.CSGDOCNUM = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getConsignmentNumber().trim());
		}
		if (dSNEnquiryFilterVO.getPaCode() != null && dSNEnquiryFilterVO.getPaCode().trim().length() > 0) {
			pgqry.append(" AND  MALMST.POACOD = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getPaCode().trim());
		}
		if (dSNEnquiryFilterVO.getMailCategoryCode() != null
				&& dSNEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
			pgqry.append(" AND MALMST.MALCTG = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getMailCategoryCode().trim());
		}
		if (dSNEnquiryFilterVO.getMailSubClass() != null && dSNEnquiryFilterVO.getMailSubClass().trim().length() > 0) {
			pgqry.append(" AND MALMST.MALSUBCLS = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getMailSubClass().trim());
		}
		if (dSNEnquiryFilterVO.getOriginCity() != null && dSNEnquiryFilterVO.getOriginCity().trim().length() > 0) {
			pgqry.append(" AND ORGCTY.CTYCOD = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getOriginCity().trim());
		}
		if (dSNEnquiryFilterVO.getDestinationCity() != null
				&& dSNEnquiryFilterVO.getDestinationCity().trim().length() > 0) {
			pgqry.append(" AND DSTCTY.CTYCOD = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getDestinationCity().trim());
		}
		if (dSNEnquiryFilterVO.getDsn() != null && dSNEnquiryFilterVO.getDsn().trim().length() > 0) {
			pgqry.append(" AND MALMST.DSN = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getDsn().trim());
		}
		if (!"ALL".equalsIgnoreCase(dSNEnquiryFilterVO.getContainerType())
				&& dSNEnquiryFilterVO.getContainerType() != null
				&& dSNEnquiryFilterVO.getContainerType().trim().length() > 0) {
			pgqry.append(" AND CONMST.CONTYP = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getContainerType().trim());
		}
		if (dSNEnquiryFilterVO.getTransitFlag() != null && dSNEnquiryFilterVO.getTransitFlag().trim().length() > 0) {
			pgqry.append(" AND CONMST.TRAFLG = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getTransitFlag().trim());
		}
		if ("TRUE".equalsIgnoreCase(dSNEnquiryFilterVO.getCapNotAcpEnabledFlag())) {
			pgqry.append(" AND ULDSEG.ULDNUM IS NULL ");
		}
		if (MailConstantsVO.OPERATION_OUTBOUND.equals(dSNEnquiryFilterVO.getOperationType())) {
			pgqry.append(" AND FLT.EXPCLSFLG IN ('C','O') ");
		} else {
			pgqry.append(" AND FLT.IMPCLSFLG IN ('C','O') ");
		}
		if (dSNEnquiryFilterVO.getFlightNumber() != null && dSNEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
			pgqry.append(" AND ULDSEG.FLTNUM = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getFlightNumber());
		}
		if (dSNEnquiryFilterVO.getCarrierId() > 0) {
			pgqry.append(" AND ULDSEG.FLTCARIDR = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getCarrierId());
		}
		if (dSNEnquiryFilterVO.getPaCode() != null && dSNEnquiryFilterVO.getPaCode().trim().length() > 0) {
			pgqry.append(" AND ULDSEG.POACOD = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getPaCode());
		}
		if (dSNEnquiryFilterVO.getAirportCode() != null && dSNEnquiryFilterVO.getAirportCode().trim().length() > 0) {
			pgqry.append(" AND FLT.ARPCOD = ?");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getAirportCode());
		}
		String mailClass = dSNEnquiryFilterVO.getMailClass();
		if (dSNEnquiryFilterVO.getMailClass() != null && dSNEnquiryFilterVO.getMailClass().trim().length() > 0) {
			log.debug("" + "inside mailClass Check..class is$$$$ " + " " + mailClass);
			if (mailClass.contains(MailConstantsVO.MALCLS_SEP)) {
				pgqry.append(" AND MALMST.MALCLS IN ( '1','2','3','4','5','6') ");
			} else {
				pgqry.append(" AND MALMST.MALCLS = ? ");
				pgqry.setParameter(++index, dSNEnquiryFilterVO.getMailClass().trim());
			}
		}
		if ("TRUE".equalsIgnoreCase(dSNEnquiryFilterVO.getCapNotAcpEnabledFlag())) {
			ZonedDateTime fromDate = dSNEnquiryFilterVO.getFromDate();
			ZonedDateTime toDate = dSNEnquiryFilterVO.getToDate();
			if (fromDate != null && toDate != null) {
				pgqry.append(" AND CSGMST.CSGDAT BETWEEN ");
				pgqry.append(" ? AND ? ");
				pgqry.setParameter(++index, fromDate);
				pgqry.setParameter(++index, toDate);
			}
		} else {
			if (dSNEnquiryFilterVO.getFromDate() != null && dSNEnquiryFilterVO.getToDate() != null) {
				pgqry.append(" AND TRUNC(ULDSEG.ACPDAT) BETWEEN ? AND ? ");
				pgqry.setParameter(++index, dSNEnquiryFilterVO.getFromDate());
				pgqry.setParameter(++index, dSNEnquiryFilterVO.getToDate());
			}
		}
		if (dSNEnquiryFilterVO.getFlightDate() != null) {
			pgqry.append(" AND TO_NUMBER(TO_CHAR(flt.FLTDAT,'YYYYMMDD')) = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getFlightDate().toString().replace("-", ""));
		}
		pgqry.append("GROUP BY MALMST.DSNIDR");
		log.debug("" + "BASEQUERY==&&&>" + " " + baseQuery);
		pgqry.append(") RESULT_TABLE");
		despatchDetailsVOs = pgqry.getPage(pageNumber);
		log.debug(CLASS_NAME + " : " + "findCapturedNotAcceptedDespatches--" + " Exiting");
		return despatchDetailsVOs;
	}
	/**
	 * Find the PA corresponding to a Mailbox ID MAY 23, 2016, a-5526
	 * @param companyCode
	 * @param mailboxId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findPAForMailboxID(String companyCode, String mailboxId, String originOE)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPAForMailboxID" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_PA_FOR_MAILBOXID);
		query.setParameter(1, companyCode);
		query.setParameter(2, mailboxId);
		query.setParameter(3, originOE);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPAForMailboxID" + " Exiting");
		return query.getSingleResult(getStringMapper("POACOD"));
	}

	/**
	 * Invokes the EVT_RCR, EVT_TMR procedure
	 * @param companyCode
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void invokeResditReceiver(String companyCode) throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "invokeResditReceiver" + " Entering");
		ZonedDateTime todaysDate = localDateUtil.getLocalDate(null, true);
		Procedure procedure = getQueryManager().createNamedNativeProcedure(PROC_RDT_EVT_RCR);
		String date = localDateUtil.toUTCTime(todaysDate).format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss"));
		procedure.setParameter(1, companyCode);
		procedure.setParameter(2, date);
		procedure.setOutParameter(3, SqlType.STRING);
		procedure.execute();
		log.debug("" + "resdit receiver out paramter from EVT_RCR is == " + " " + procedure.getParameter(2));
		callResditTimerManager(companyCode);
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "invokeResditReceiver" + " Exiting");
	}

	private void callResditTimerManager(String companyCode) {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "callResditTimerManager" + " Entering");
		Procedure procedure = getQueryManager().createNamedNativeProcedure(PROC_RDT_EVT_TMR);
		procedure.setParameter(1, companyCode);
		procedure.setParameter(2, new GMTDate(true).toDisplayFormat("yyMMddHHmm"));
		procedure.setOutParameter(3, SqlType.STRING);
		procedure.execute();
		log.debug("" + "resdit receiver out paramter from TMR_MGR is == " + " " + procedure.getParameter(3));
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "callResditTimerManager" + " Exiting");
	}

	/**
	 * Checks for any flagged resdit events and returns them
	 * @param companyCode
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ResditEventVO> findResditEvents(String companyCode) throws PersistenceException {
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "checkForResditEvents" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_RESDIT_EVENTS);
		query.setParameter(1, companyCode);
		Collection<ResditEventVO> events = query.getResultList(new ResditEventMapper());
		if (events != null && events.size() > 0) {
			for (ResditEventVO resditEventVO : events) {
				resditEventVO.setCompanyCode(companyCode);
			}
		}
		return events;
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findMailBagsForTransportCompletedResdit(com.ibsplc.icargo.business.mailtracking.defaults.vo.OperationalFlightVO) Added by 			: Used for 	: Parameters	:	@param operationalFlightVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public Collection<MailbagVO> findMailBagsForTransportCompletedResdit(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + LOG_DELIMITER + " : " + "flagTransportCompletedResditForMailbags" + " Entering");
		Collection<MailbagVO> mailBagVos = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FOR_TRANSPORTCOMPLETED_RESDIT);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry.setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVO.getPou());
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry.setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVO.getPou());
		mailBagVos = qry.getResultList(new MailBagsForUpliftedResditMapper());
		if (mailBagVos != null && mailBagVos.size() > 0) {
			for (MailbagVO mailbag : mailBagVos) {
				mailbag.setCompanyCode(operationalFlightVO.getCompanyCode());
				if (operationalFlightVO.getCarrierCode() != null) {
					mailbag.setCarrierCode(operationalFlightVO.getCarrierCode());
				}
				mailbag.setCarrierId(operationalFlightVO.getCarrierId());
				mailbag.setFlightDate(operationalFlightVO.getFlightDate());
				mailbag.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				mailbag.setFlightNumber(operationalFlightVO.getFlightNumber());
				mailbag.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			}
		}
		log.debug(CLASS_NAME + LOG_DELIMITER + " : " + "flagTransportCompletedResditForMailbags" + " Exiting");
		return mailBagVos;
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findUldsForTransportCompletedResdit(com.ibsplc.icargo.business.mailtracking.defaults.vo.OperationalFlightVO) Added by 			: Used for 	: Parameters	:	@param operationalFlightVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findUldsForTransportCompletedResdit(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + "=============>>>" + " : " + "findUldsForTransportCompletedResdit" + " Entering");
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_ULDS_FOR_TRANSPORT_COMPLETED_RESDIT);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry.setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		containerDetailsVOs = qry.getResultList(new UldsForUpliftedResditMapper());
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				containerDetailsVO.setCompanyCode(operationalFlightVO.getCompanyCode());
				containerDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
				containerDetailsVO.setCarrierId(operationalFlightVO.getCarrierId());
				containerDetailsVO.setFlightDate(operationalFlightVO.getFlightDate());
				containerDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				containerDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			}
		}
		log.debug(CLASS_NAME + "=============>>>" + " : " + "findUldsForTransportCompletedResdit" + " Exiting");
		return containerDetailsVOs;
	}

	/**
	 */
	public Collection<OperationalFlightVO> findOperationalFlightForMRD(HandoverVO handoverVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findInboundFlightForMailOperation" + " Entering");
		int index = 0;
		boolean legDstExist = false;
		Collection<OperationalFlightVO> flights = null;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_OPERATIONFLIGHTS_MRD);
		if (handoverVO.getFlightDate() != null) {
			qry.append(" AND TRUNC(FLTMST.FLTDAT) = ? ");
			Timestamp timestamp = Timestamp.valueOf(handoverVO.getFlightDate().toLocalDateTime());
			qry.setParameter(++index, timestamp);
		} else {
			qry.append(" AND FLTLEG.ATA IS NOT NULL AND FLTLEG.ATA = (SELECT MAX(ATA) FROM FLTOPRLEG WHERE ");
			qry.append(" CMPCOD = FLTMST.CMPCOD AND ");
			qry.append(" ATA <= ? ");
			Timestamp timestamp = Timestamp.valueOf(handoverVO.getHandOverdate_time().toLocalDateTime());
			qry.setParameter(++index, timestamp);
			qry.append(" AND LEGDST = ? ");
			if (handoverVO.getFlightNumber() != null && handoverVO.getFlightNumber().trim().length() > 0
					&& !"0000".equals(handoverVO.getFlightNumber()) && handoverVO.getCarrierCode() != null
					&& handoverVO.getCarrierCode().trim().length() > 0) {
				legDstExist = true;
				qry.append(" AND FLTNUM = FLTMST.FLTNUM AND FLTCARIDR = FLTMST.FLTCARIDR ");
				if (handoverVO.getDestination() != null && handoverVO.getDestination().trim().length() > 0) {
					qry.setParameter(++index, handoverVO.getDestination());
				} else
					qry.setParameter(++index, handoverVO.getDestAirport());
				if (handoverVO.getFltSeqNum() > 0) {
					qry.append(" AND FLTSEQNUM = ? ");
					qry.setParameter(++index, handoverVO.getFltSeqNum());
				}
			} else {
				qry.setParameter(++index, handoverVO.getDestAirport());
			}
			qry.append(" ) ");
		}
		qry.append(" WHERE FLTMST.CMPCOD = ? AND FLTMST.FLTSTA = 'ACT' AND FLTLEG.LEGDST = ? ");
		qry.setParameter(++index, handoverVO.getCompanyCode());
		if (handoverVO.getDestination() != null && handoverVO.getDestination().trim().length() > 0) {
			qry.setParameter(++index, handoverVO.getDestination());
		} else
			qry.setParameter(++index, handoverVO.getDestAirport());
		if (handoverVO.getFlightNumber() != null && handoverVO.getFlightNumber().trim().length() > 0
				&& !"0000".equals(handoverVO.getFlightNumber()) && handoverVO.getCarrierCode() != null
				&& handoverVO.getCarrierCode().trim().length() > 0) {
			qry.append(" AND FLTMST.FLTNUM  = ?  AND FLTMST.FLTCARIDR="
					+ " (SELECT ARLIDR  FROM SHRARLMST WHERE CMPCOD  = FLTMST.CMPCOD AND TWOAPHCOD = ? )");
			qry.setParameter(++index, handoverVO.getFlightNumber());
			qry.setParameter(++index, handoverVO.getCarrierCode());
			if (handoverVO.getFltSeqNum() > 0) {
				qry.append(" AND FLTMST.FLTSEQNUM = ? ");
				qry.setParameter(++index, handoverVO.getFltSeqNum());
			}
		}
		return qry.getResultList(new FlightsForClosureMapper());
	}

	/**
	 */
	public Collection<CarditVO> findCarditDetailsForResdit(Collection<ResditEventVO> resditEvents)
			throws PersistenceException {
		if (resditEvents == null || resditEvents.size() == 0) {
			return null;
		}
		ArrayList<ResditEventVO> eventsArrayList = (ArrayList<ResditEventVO>) resditEvents;
		Query query = null;
		int idx = 0;
		query = getQueryManager().createNamedNativeQuery(FIND_CDT_DTL);
		query.setParameter(++idx, eventsArrayList.get(0).getCompanyCode());
		boolean isFirst = true;
		String usConsignment = "US";
		query.append(" AND	MST.CSGDOCNUM IN ( ?");
		for (ResditEventVO resditEventVO : eventsArrayList) {
			if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(resditEventVO.getCarditExist())
					&& resditEventVO.getConsignmentNumber().startsWith(usConsignment)) {
				if (isFirst) {
					isFirst = false;
					query.setParameter(++idx, resditEventVO.getConsignmentNumber());
				} else {
					query.append(",? ");
					query.setParameter(++idx, resditEventVO.getConsignmentNumber());
				}
			}
		}
		query.append(" ) ");
		if (!isFirst) {
			return query.getResultList(new CarditDetailsMapper());
		} else
			return null;
	}

	/**
	 */
	public Collection<ConsignmentRoutingVO> findConsignmentRoutingDetails(CarditEnquiryFilterVO carditEnqFilterVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findConsignmentRoutingDetails" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_ROUTING_DETAILS);
		int idx = 0;
		log.debug("" + "--companyCode --- : " + " " + carditEnqFilterVO.getCompanyCode());
		log.debug("" + "--carditEnqFilterVO.getConsignmentDocument() --- : " + " "
				+ carditEnqFilterVO.getConsignmentDocument());
		log.debug("" + "--carditEnqFilterVO.getPaoCode() --- : " + " " + carditEnqFilterVO.getPaoCode());
		query.setParameter(++idx, carditEnqFilterVO.getCompanyCode());
		query.setParameter(++idx, carditEnqFilterVO.getConsignmentDocument());
		query.setParameter(++idx, carditEnqFilterVO.getPaoCode());
		query.setParameter(++idx, carditEnqFilterVO.getCompanyCode());
		query.setParameter(++idx, carditEnqFilterVO.getConsignmentDocument());
		query.setParameter(++idx, carditEnqFilterVO.getPaoCode());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findConsignmentRoutingDetails" + " Exiting");
		return query.getResultList(new ConsignmentRoutingMapper());
	}
	/**
	 */
	public Collection<TransportInformationVO> findRoutingDetailsFromCardit(CarditEnquiryFilterVO carditEnqFilterVO,
																		   String airport) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findRoutingDetailsFromCardit" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_ROUTING_DETAILS);
		int idx = 0;
		int consignmentSeqNumber = 1;
		log.debug("" + "--companyCode --- : " + " " + carditEnqFilterVO.getCompanyCode());
		log.debug("" + "--consignmentSeqNumber --- : " + " " + consignmentSeqNumber);
		log.debug("" + "--carditEnqFilterVO.getConsignmentDocument() --- : " + " "
				+ carditEnqFilterVO.getConsignmentDocument());
		log.debug("" + "--carditEnqFilterVO.getPaoCode() --- : " + " " + carditEnqFilterVO.getPaoCode());
		query.setParameter(++idx, carditEnqFilterVO.getCompanyCode());
		query.setParameter(++idx, carditEnqFilterVO.getConsignmentDocument());
		query.setParameter(++idx, carditEnqFilterVO.getPaoCode());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findRoutingDetailsFromCardit" + " Exiting");
		return query.getResultList(new CarditRoutingMapper());
	}

	/**
	 */
	public Collection<ReceptacleInformationVO> findMailbagDetailsForResdit(ResditEventVO resditEventVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagDetailsForResdit" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAG_FOR_RESDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getConsignmentNumber());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());
		query.setParameter(++idx, resditEventVO.getResditEventCode());
		query.setParameter(++idx, MailConstantsVO.FLAG_YES);
		query.setParameter(++idx, resditEventVO.getEventPort());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagDetailsForResdit" + " Exiting");
		return query.getResultList(new ReceptanceInformationMapper());
	}
	/**
	 */
	public Collection<ReceptacleInformationVO> findMailbagDetailsForXXResdit(ResditEventVO resditEventVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagDetailsForXXResdit" + " Entering");
		log.debug("" + "Check----->>resditEventVO---->>>" + " " + resditEventVO);
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAG_FOR_XX_RESDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());
		if (resditEventVO.getActualResditEvent() != null
				&& MailConstantsVO.RESDIT_XX.equals(resditEventVO.getActualResditEvent())) {
			query.setParameter(++idx, MailConstantsVO.RESDIT_XX);
		} else {
			query.setParameter(++idx, resditEventVO.getResditEventCode());
		}
		query.setParameter(++idx, MailConstantsVO.FLAG_YES);
		query.setParameter(++idx, resditEventVO.getEventPort());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagDetailsForXXResdit" + " Exiting");
		return query.getResultList(new ReceptanceInformationMapper());
	}

	/**
	 */
	public Collection<TransportInformationVO> findTransportDetailsForULD(ResditEventVO resditEventVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findTransportDetailsForULD" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_TRANSPORT_FOR_ULDRESDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());
		query.setParameter(++idx, resditEventVO.getResditEventCode());
		query.setParameter(++idx, resditEventVO.getEventPort());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findTransportDetailsForULD" + " Exiting");
		return query.getResultList(new TransportInformationMapper());
	}

	/**
	 */
	public Collection<ContainerInformationVO> findULDDetailsForResdit(ResditEventVO resditEventVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDDetailsForResdit" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_ULD_DETAILS_FOR_RESDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getConsignmentNumber());
		query.setParameter(++idx, resditEventVO.getResditEventCode());
		query.setParameter(++idx, MailConstantsVO.FLAG_YES);
		query.setParameter(++idx, resditEventVO.getEventPort());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDDetailsForResdit" + " Exiting");
		return query.getResultList(new ContainerInformationMapper());
	}

	/**
	 */
	public Collection<ContainerInformationVO> findULDDetailsForResditWithoutCardit(ResditEventVO resditEventVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDDetailsForResditWithoutCardit" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_ULD_DETAILS_FOR_RESDIT_WITHOUT_CARDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getResditEventCode());
		query.setParameter(++idx, MailConstantsVO.FLAG_YES);
		query.setParameter(++idx, resditEventVO.getEventPort());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());
		if (isOracleDataSource()) {
			query.append(" AND ROWNUM=1");
		} else {
			query.append(" LIMIT 1");
		}
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findULDDetailsForResditWithoutCardit" + " Exiting");
		return query.getResultList(new ContainerInformationMapper());
	}

	public HashMap<Long, MailInConsignmentVO> findAllConsignmentDetailsForMailbag(String companyCode, long[] malseqnum)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findConsignmentDetailsForMailbag" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_DETAILS_FOR_MAILBAG);
		query.setSensitivity(true);
		query.setParameter(++index, companyCode);
		StringBuilder mailbagId = new StringBuilder("");
		for (long seqNum : malseqnum) {
			mailbagId.append(seqNum).append(",");
		}
		String[] mailbags = mailbagId.toString().split(",");
		String joinQuery = null;
		if (mailbags.length < 999) {
			joinQuery = getWhereClause(mailbags);
			query.append(" AND MALMST.MALSEQNUM IN ");
		} else {
			joinQuery = getEnhancedWhereClause(mailbags, 999, "MALMST.MALSEQNUM");
		}
		query.append(joinQuery).append(")");
		return query.getResultList(new ConsignmentsOfMailbagsMultiMapper()).get(0);
	}

	/**
	 * @author a-1936This  method is used to find the mailbags in the Container for the Manifest
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(
			Collection<ContainerDetailsVO> containers) throws PersistenceException {
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + FIND_MAILBAGS_IN_CONTAINER + " Entering");
		Collection<ContainerDetailsVO> containerForReturn = new ArrayList<ContainerDetailsVO>();
		Query qry = null;
		for (ContainerDetailsVO cont : containers) {
			int idx = 0;
			if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
				qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_IMPORT_MANIFEST_ULD);
			} else {
				qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_IMPORT_MANIFEST_BULK);
			}
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ACCEPTED);
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_TRANSFERRED);
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ASSIGNED);
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ARRIVED);
			qry.setParameter(++idx, cont.getPol());
			qry.setParameter(++idx, cont.getCompanyCode());
			qry.setParameter(++idx, cont.getCarrierId());
			qry.setParameter(++idx, cont.getFlightNumber());
			qry.setParameter(++idx, cont.getFlightSequenceNumber());
			qry.setParameter(++idx, cont.getSegmentSerialNumber());
			qry.setParameter(++idx, cont.getPol());
			qry.setParameter(++idx, cont.getContainerNumber());
			List<ContainerDetailsVO> list = qry.getResultList(new AcceptedDSNsForManifestMultiMapper());
			if (list != null && list.size() > 0) {
				containerForReturn.add(list.get(0));
			}
		}
		return containerForReturn;
	}

	/**
	 * @author A-6371For fetching Mailonhand list details
	 */
	public Page<MailOnHandDetailsVO> findMailOnHandDetails(SearchContainerFilterVO searchContainerFilterVO,
														   int pageNumber) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailOnHandDetails" + " Entering");
		PageableNativeQuery<MailOnHandDetailsVO> pgqry = null;
		Page<MailOnHandDetailsVO> MailOnHandDetailsVOs = null;
		String baseQry = "";
		String assignedUser = searchContainerFilterVO.getSearchMode();
		StringBuilder carrierBuilder = new StringBuilder();
		String carrierValues = "";
		if (searchContainerFilterVO.getPartnerCarriers() != null) {
			ArrayList<String> carriervalue = searchContainerFilterVO.getPartnerCarriers();
			for (String s : carriervalue) {
				carrierBuilder.append("'");
				carrierBuilder.append(s);
				carrierBuilder.append("'");
				carrierBuilder.append(",");
			}
			int i = carrierBuilder.lastIndexOf(",");
			carrierBuilder.deleteCharAt(i);
			carrierValues = carrierBuilder.toString();
			log.debug("" + " systemParameterMap " + " " + carrierValues);
		}
		if (SEARCHMODE_FLT.equals(searchContainerFilterVO.getSearchMode())) {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAILONHANDLISTFLIGHT);
			if (searchContainerFilterVO.getPartnerCarriers() != null) {
				StringBuilder queryBuilder = new StringBuilder(baseQry);
				queryBuilder.append("AND MST. FLTCARCOD IN (");
				queryBuilder.append(carrierValues);
				queryBuilder.append(")");
				baseQry = queryBuilder.toString();
			}
		} else if (SEARCHMODE_DEST.equals(assignedUser)) {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAILONHANDLISTCARRIER);
			if (searchContainerFilterVO.getPartnerCarriers() != null) {
				StringBuilder queryBuilder = new StringBuilder(baseQry);
				queryBuilder.append("AND MST. FLTCARCOD IN (");
				queryBuilder.append(carrierValues);
				queryBuilder.append(")");
				baseQry = queryBuilder.toString();
			}
		} else {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAILONHANDLIST);
			StringBuilder queryBuilder = new StringBuilder(baseQry);
			if (searchContainerFilterVO.getPartnerCarriers() != null) {
				queryBuilder.append("AND MST. FLTCARCOD IN (");
				queryBuilder.append(carrierValues);
				queryBuilder.append(")");
				baseQry = queryBuilder.toString();
			}
			queryBuilder.append(")UNION ALL (");
			String subQuery = getQueryManager().getNamedNativeQueryString(FIND_MAILONHANDLIST_PARTTWO);
			queryBuilder.append(subQuery);
			baseQry = queryBuilder.toString();
			if (searchContainerFilterVO.getPartnerCarriers() != null) {
				queryBuilder.append("AND MST. FLTCARCOD IN (");
				queryBuilder.append(carrierValues);
				queryBuilder.append(")");
				baseQry = queryBuilder.toString();
			}
		}
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append("RESULT_TABLE.ASGPRT, RESULT_TABLE.DSTCOD,RESULT_TABLE.SUBCLSGRP) RANK FROM ((");
		rankQuery.append(baseQry);
		rankQuery.append("))GROUP BY CMPCOD,ASGPRT ,DSTCOD ,SUBCLSGRP, SUBSTR(CONNUM, 0,3), CONTYP  )RESULT_TABLE)");
		log.info("THE SEARCH MODE IS DESTINATION");
		pgqry = new MailOnHandListFilterQuery(rankQuery.toString(), searchContainerFilterVO,
				new MailOnHandListMultiMapper());
		MailOnHandDetailsVOs = pgqry.getPage(pageNumber);
		log.info("THE DESTINATION IS PRESENT");
		return MailOnHandDetailsVOs;
	}

	/**
	 * @author A-7871for ICRD-257316 Used to get mailbag count
	 */
	public int findMailbagcountInContainer(ContainerVO containerVO) {
		log.debug(CLASS_NAME + " : " + "findMailbagcountInContainer" + " Entering");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGCOUNTINCONTAINER);
		qry.setParameter(++index, containerVO.getCompanyCode());
		qry.setParameter(++index, containerVO.getCarrierId());
		qry.setParameter(++index, containerVO.getFlightNumber());
		qry.setParameter(++index, containerVO.getFlightSequenceNumber());
		qry.setParameter(++index, containerVO.getSegmentSerialNumber());
		qry.setParameter(++index, containerVO.getContainerNumber());
		return qry.getSingleResult(getIntMapper("MAILBAGCOUNT"));
	}

	/**
	 * Added as part of CRQ ICRD-204806
	 * @author A-5526
	 * @param fileUploadFilterVO
	 * @return
	 */
	public String processMailOperationFromFile(FileUploadFilterVO fileUploadFilterVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "processMailOperationFromFile" + " Entering");
		String processStatus = null;
		if (fileUploadFilterVO != null) {
			int index = 0;
			Procedure procedure = getQueryManager().createNamedNativeProcedure(PROCESS_MAIL_OPERATIONS_FROM_FILE);
			index++;
			procedure.setParameter(index, fileUploadFilterVO.getCompanyCode());
			index++;
			procedure.setParameter(index, fileUploadFilterVO.getFileType());
			index++;
			procedure.setParameter(index, "F");
			index++;
			procedure.setParameter(index, fileUploadFilterVO.getProcessIdentifier());
			index++;
			procedure.setParameter(index, MailConstantsVO.FLAG_NO);
			index++;
			procedure.setOutParameter(index, SqlType.STRING);
			index++;
			procedure.setOutParameter(index, SqlType.STRING);
			procedure.execute();
			processStatus = (String) procedure.getParameter(index);
		}
		//log.error(new Object[] { "ProcessStatus-->", processStatus });
		return processStatus;
	}

	/**
	 * Added as part of CRQ ICRD-204806 fetchDataForOfflineUpload
	 * @param companyCode
	 * @param fileType
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailUploadVO> fetchDataForOfflineUpload(String companyCode, String fileType)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "fetchDataForOfflineUpload" + " Entering");
		Collection<MailUploadVO> mailUploadVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(FETCH_DATA_FOR_OFFLOAD_UPLOAD);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, fileType);
		mailUploadVOs = query.getResultList(new OfflineMailUploadMapper());
		log.info("" + "MailUploadVO is from dao*****" + " " + mailUploadVOs);
		return mailUploadVOs;
	}

	/**
	 * Added as part of CRQ ICRD-204806 removeDataFromTempTable
	 * @param fileUploadFilterVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "removeDataFromTempTable" + " Entering");
		String processStatus = null;
		if (fileUploadFilterVO != null) {
			int index = 0;
			Procedure procedure = getQueryManager().createNamedNativeProcedure(REMOVE_DATA_FROM_TEMPTABLE);
			index++;
			procedure.setParameter(index, fileUploadFilterVO.getCompanyCode());
			index++;
			procedure.setParameter(index, fileUploadFilterVO.getFileType());
			index++;
			procedure.setParameter(index, "F");
			index++;
			procedure.setParameter(index, fileUploadFilterVO.getProcessIdentifier());
			index++;
			procedure.setParameter(index, MailConstantsVO.FLAG_YES);
			index++;
			procedure.setOutParameter(index, SqlType.STRING);
			index++;
			procedure.setOutParameter(index, SqlType.STRING);
			procedure.execute();
			processStatus = (String) procedure.getParameter(index);
		}
		//log.error(new Object[] { "ProcessStatus-->", processStatus });
	}

	/**
	 * @author a-7794 This method is used to find ContainerAuditDetails
	 * @param mailAuditFilterVO
	 * @return Collection<PartnerCarrierVO>
	 * @throws SystemException
	 * @throws PersistenceException ICRD-229934
	 */
	public Collection<AuditDetailsVO> findCONAuditDetails(MailAuditFilterVO mailAuditFilterVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findCONAuditDetails" + " Entering");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CONAUDIT);
		qry.setParameter(++index, mailAuditFilterVO.getCompanyCode());
		qry.setParameter(++index, mailAuditFilterVO.getTxnFromDate());
		qry.setParameter(++index, mailAuditFilterVO.getTxnToDate());
		if (mailAuditFilterVO.getCarrierId() != 0) {
			qry.append("AND FLTCARIDR = ?");
			qry.setParameter(++index, mailAuditFilterVO.getCarrierId());
		}
		if (mailAuditFilterVO.getFlightNumber() != null && mailAuditFilterVO.getFlightNumber().trim().length() > 0) {
			qry.append("AND FLTNUM = ?");
			qry.setParameter(++index, mailAuditFilterVO.getFlightNumber());
			qry.append("AND FLTSEQNUM = ?");
			qry.setParameter(++index, mailAuditFilterVO.getFlightSequenceNumber());
			qry.append("AND LEGSERNUM = ?");
			qry.setParameter(++index, mailAuditFilterVO.getLegSerialNumber());
		}
		if (mailAuditFilterVO.getAssignPort() != null && mailAuditFilterVO.getAssignPort().trim().length() > 0) {
			qry.append("AND ASGPRT = ?");
			qry.setParameter(++index, mailAuditFilterVO.getAssignPort());
		}
		if (mailAuditFilterVO.getContainerNo() != null && mailAuditFilterVO.getContainerNo().trim().length() > 0) {
			qry.append("AND CONNUM = ?");
			qry.setParameter(++index, mailAuditFilterVO.getContainerNo());
		}
		return qry.getResultList(new MailAuditMapper());
	}

	/**
	 * Method	:	findMailbagIdForMailTag(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO) Added by 	: a-6245 on 22-Jun-2017 Used for 	:ICRD-205027 Parameters	:	@param mailbagVO Parameters	:	@return
	 */
	public MailbagVO findMailbagIdForMailTag(MailbagVO mailbagVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailbagIdForMailTag" + " Entering");
		int idx = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGID_FOR_MAIL_TAG);
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getOoe());
		query.setParameter(++idx, mailbagVO.getDoe());
		query.setParameter(++idx, mailbagVO.getMailCategoryCode());
		query.setParameter(++idx, mailbagVO.getMailSubclass());
		query.setParameter(++idx, mailbagVO.getYear());
		query.setParameter(++idx, mailbagVO.getDespatchSerialNumber());
		query.setParameter(++idx, mailbagVO.getReceptacleSerialNumber());
		query.setParameter(++idx, mailbagVO.getHighestNumberedReceptacle());
		query.setParameter(++idx, mailbagVO.getRegisteredOrInsuredIndicator());
		query.setParameter(++idx, mailbagVO.getWeight().getDisplayValue());
		return query.getSingleResult(new MailTagIDMapper());
	}

	/**
	 * Overriding Method	:@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO #findAgentCodeForPA(java.lang.String, java.lang.String) Added by 			: U-1267 on Nov 1, 2017 Used for 	:	ICRD-211205 Parameters	:	@param companyCode Parameters	:	@param officeOfExchange Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public String findAgentCodeForPA(String companyCode, String officeOfExchange) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAgentCodeForPA" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_AGENT_FOR_PA);
		query.setParameter(1, companyCode);
		query.setParameter(2, officeOfExchange);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAgentCodeForPA" + " Exiting");
		return query.getSingleResult(getStringMapper("PARVAL"));
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO #findMailbagVOsForDsnVOs(java.util.Collection) Added by 			: U-1267 on 08-Nov-2017 Used for 	:	ICRD-211205 Parameters	:	@param dsnVOs Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public Collection<MailbagVO> findMailbagVOsForDsnVOs(ContainerDetailsVO containerDetailsVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagVOsForDsnVOs" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FOR_DSN);
		if (containerDetailsVO.getFromScreen() != null && (containerDetailsVO.getFromScreen().equals("Outbound")
				|| containerDetailsVO.getFromScreen().equals("Inbound"))) {
			query.append("AND ULDSEG.CONNUM = ?");
		} else {
			query.append("AND ULDSEG.ULDNUM = ?");
		}
		int index = 0;
		if (!MailConstantsVO.FROMDETACHAWB.equals(containerDetailsVO.getFromDetachAWB())) {
			Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
			boolean first = true;
			if (dsnVOs != null && dsnVOs.size() > 0) {
				query.append("AND MST.DSNIDR IN ( ?");
				for (DSNVO dsnVO : dsnVOs) {
					StringBuilder sb = new StringBuilder();
					if (first) {
						first = false;
						query.setParameter(++index, containerDetailsVO.getCompanyCode());
						query.setParameter(++index, containerDetailsVO.getFlightNumber());
						query.setParameter(++index, containerDetailsVO.getFlightSequenceNumber());
						query.setParameter(++index, containerDetailsVO.getCarrierId());
						query.setParameter(++index, containerDetailsVO.getContainerNumber());
						sb.append(dsnVO.getOriginExchangeOffice()).append(dsnVO.getDestinationExchangeOffice())
								.append(dsnVO.getMailCategoryCode()).append(dsnVO.getMailSubclass())
								.append(dsnVO.getYear()).append(dsnVO.getDsn());
						query.setParameter(++index, sb.toString());
					} else {
						query.append(",? ");
						sb.append(dsnVO.getOriginExchangeOffice()).append(dsnVO.getDestinationExchangeOffice())
								.append(dsnVO.getMailCategoryCode()).append(dsnVO.getMailSubclass())
								.append(dsnVO.getYear()).append(dsnVO.getDsn());
						query.setParameter(++index, sb.toString());
					}
				}
				query.append(" ) ");
			}
		} else {
			Collection<MailbagVO> mailDetails = containerDetailsVO.getMailDetails();
			boolean first = true;
			if (mailDetails != null && mailDetails.size() > 0) {
				query.append("AND MST.MALSEQNUM IN ( ?");
				for (MailbagVO mailDetail : mailDetails) {
					StringBuilder sb = new StringBuilder();
					if (first) {
						first = false;
						query.setParameter(++index, containerDetailsVO.getCompanyCode());
						query.setParameter(++index, containerDetailsVO.getFlightNumber());
						query.setParameter(++index, containerDetailsVO.getFlightSequenceNumber());
						query.setParameter(++index, containerDetailsVO.getCarrierId());
						query.setParameter(++index, containerDetailsVO.getContainerNumber());
						query.setParameter(++index, mailDetail.getMailSequenceNumber());
					} else {
						query.append(",? ");
						sb.append(mailDetail.getOoe()).append(mailDetail.getDoe())
								.append(mailDetail.getMailCategoryCode()).append(mailDetail.getMailSubclass())
								.append(mailDetail.getYear()).append(mailDetail.getMailSequenceNumber());
						query.setParameter(++index, mailDetail.getMailSequenceNumber());
					}
				}
				query.append(" ) ");
			}
		}
		return query.getResultList(new MailbagMapper());
	}

	/**
	 * @author A-8061
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagHistoryVO> findMailbagResditEvents(String companyCode, String mailbagId)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailbagResditEvents" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGEVENTS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailbagId);
		return query.getResultList(new MailbagResditEventMapper());
	}

	/**
	 * @author A-8061
	 * @param carditEnquiryFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String[] findGrandTotals(CarditEnquiryFilterVO carditEnquiryFilterVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findGrandTotals" + " Entering");
		String grandTotal = "";
		String[] grandTotals = { "0", "0" };
		String baseQuery = getQueryManager().getNamedNativeQueryString(FIND_GRAND_TOTAL);
		Query query = new GrandTotalFilterQuery(carditEnquiryFilterVO, baseQuery);
		grandTotal = query.getSingleResult(getStringMapper("GRDTOT"));
		if (grandTotal != null) {
			if (grandTotal.split("-")[0] != null) {
				grandTotals[0] = grandTotal.split("-")[0];
			}
			if ((grandTotal.split("-").length > 1) && (grandTotal.split("-")[1] != null)) {
				grandTotals[1] = grandTotal.split("-")[1];
			}
		}
		return grandTotals;
	}

	public Page<ContainerDetailsVO> findContainerDetails(OperationalFlightVO operationalFlightVO, int pageNumber)
			throws PersistenceException {
		int index = 0;
		Page<ContainerDetailsVO> coll = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		PageableNativeQuery<ContainerDetailsVO> pageQuery = null;
		Query qry = null;
		boolean isCarrierList = false;
		if (operationalFlightVO.getFlightNumber() != null && operationalFlightVO.getFlightSequenceNumber() > 0) {
			String acceptedContainersInUld = getQueryManager()
					.getNamedNativeQueryString(FIND__OUTBOUND_FLIGHT_CONTAINERDETAILS_ULD);
			String acceptedContainersInBulk = getQueryManager()
					.getNamedNativeQueryString(FIND_OUTBOUND_FLIGHT_CONTAINERDETAILS_BULK);
			rankQuery.append(acceptedContainersInUld);
			pageQuery = new FindOutboundFlightDetailsFilterQuery(operationalFlightVO, rankQuery,
					acceptedContainersInBulk, new FindContainersinFlightMapper());
			pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		} else {
			isCarrierList = true;
			String acceptedUlds = getQueryManager()
					.getNamedNativeQueryString(FIND__OUTBOUND_CARRIER_CONTAINERDETAILS_ULD);
			String acceptedContainers = getQueryManager()
					.getNamedNativeQueryString(FIND_OUTBOUND_CARRIER_CONTAINERDETAILS_BULK);
			rankQuery.append(acceptedUlds);
			pageQuery = new FindOutboundCarrierDetailsFilterQuery(operationalFlightVO, rankQuery, acceptedContainers,
					new FindContainersinCarrierMapper());
			pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
			log.info("" + "FINAL query " + " " + pageQuery.toString());
		}
		coll = pageQuery.getPage(pageNumber);
		if (isCarrierList && coll != null) {
			for (ContainerDetailsVO container : coll) {
				if (container.getAssignedPort() != null && container.getContainerNumber() != null) {
					ContainerVO containerVO = new ContainerVO();
					containerVO.setCompanyCode(container.getCompanyCode());
					containerVO.setContainerNumber(container.getContainerNumber());
					containerVO.setAssignedPort(container.getAssignedPort());
					findOffloadedInfoForCOntainer(containerVO);
					if (containerVO != null) {
						container.setOffloadCount(containerVO.getOffloadCount());
						container.setOffloadedInfo(containerVO.getOffloadedInfo());
					}
				}
			}
		}
		return coll;
	}

	public Page<MailAcceptanceVO> findOutboundFlightsDetails(OperationalFlightVO operationalFlightVO, int pageNumber)
			throws PersistenceException {
		Page<MailAcceptanceVO> flightvos = null;
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findFlightDetailsforOutbound" + " Entering");
		String baseQry1 = getQueryManager().getNamedNativeQueryString(OUTBOUND_FIND_FLIGHT_FOR_PREADVICE);
		String baseQry2 = getQueryManager().getNamedNativeQueryString(OUTBOUND_FIND_FLIGHTDETAILS);
		String modifiedStr1 = null;
		if (isOracleDataSource()) {
			modifiedStr1 = "LISTAGG(CONNAM,',') within group (order by connam) CONNAM , LISTAGG(CONCNT,',') within group (order by connam) CONCNT , LISTAGG(MALCNT,',') within group (order by connam) MALCNT , LISTAGG(wgt,',') within group (order by connam) wgt, MAX(DSPWGTUNT) DSPWGTUNT";
		} else {
			modifiedStr1 = "STRING_AGG ( CONNAM,',' ORDER BY CONNAM) CONNAM , STRING_AGG(CAST (CONCNT AS CHARACTER VARYING),',' ORDER BY CONNAM) CONCNT , STRING_AGG(CAST (MALCNT AS CHARACTER VARYING),',' ORDER BY CONNAM) MALCNT , STRING_AGG(CAST (WGT AS CHARACTER VARYING), ',' ORDER BY CONNAM) WGT, MAX(DSPWGTUNT) DSPWGTUNT";
		}
		baseQry2 = String.format(baseQry2, modifiedStr1);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		rankQuery.append(baseQry1);
		int recordsPerPage = 25;
		if (operationalFlightVO.getRecordsPerPage() != 0) {
			recordsPerPage = operationalFlightVO.getRecordsPerPage();
		}
		boolean isOracleDataSource = isOracleDataSource();
		PageableNativeQuery<MailAcceptanceVO> pageQuery = new OutboundFlightFilterQuery(recordsPerPage,
				operationalFlightVO, rankQuery, baseQry2, isOracleDataSource, new OutboundFlightMapper());
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		pageQuery.setCacheable(false);
		log.info("" + "FINAL query " + " " + pageQuery.toString());
		flightvos = pageQuery.getPage(pageNumber);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "flightvos" + " Entering");
		return flightvos;
	}

	public Page<MailbagVO> findMailbagsinContainer(ContainerDetailsVO containervo, int pageNumber)
			throws PersistenceException {
		Page<MailbagVO> mailbagvos = null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		int index = 0;
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagsinContainer" + " Entering");
		String baseQryforuld = null;
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_IN_FLIGHT_ULD);
		} else {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_INSIDE_FLIGHT_BULK);
		}
		rankQuery.append(qry);
		PageableNativeQuery<MailbagVO> pageQuery = null;
		if (containervo.getFlightNumber() != null && containervo.getFlightSequenceNumber() > 0) {
			pageQuery = new PageableNativeQuery<MailbagVO>(containervo.getTotalRecordSize(), -1, rankQuery.toString(),
					new OutboundMailbagMapper(),PersistenceController.getEntityManager().currentSession());
		}
		pageQuery.setParameter(++index, containervo.getCompanyCode());
		pageQuery.setParameter(++index, containervo.getCarrierId());
		pageQuery.setParameter(++index, containervo.getFlightNumber());
		pageQuery.setParameter(++index, containervo.getFlightSequenceNumber());
		pageQuery.setParameter(++index, containervo.getPol());
		pageQuery.setParameter(++index, containervo.getContainerNumber());
		if (containervo.getAdditionalFilters() != null) {
			index = populateAdditionalFiltersForMailbags(pageQuery, index, containervo);
		}
		pageQuery.append(
				" ORDER BY CON.CONNUM, CON.SEGSERNUM, MALMST.DSN, MALMST.ORGEXGOFC, MALMST.DSTEXGOFC, MALMST.MALSUBCLS, MALMST.MALCTG,MALMST.YER ");
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		mailbagvos = pageQuery.getPage(pageNumber);
		return mailbagvos;
	}

	private int populateAdditionalFiltersForMailbags(PageableNativeQuery<MailbagVO> pageQuery, int index,
													 ContainerDetailsVO containervo) {
		if (containervo.getAdditionalFilters() != null) {
			MailbagEnquiryFilterVO mailbagFilters = containervo.getAdditionalFilters();
			if (mailbagFilters.getMailbagId() != null && mailbagFilters.getMailbagId().trim().length() > 0) {
				pageQuery.append(" AND MALMST.MALIDR = ?");
				pageQuery.setParameter(++index, mailbagFilters.getMailbagId());
			}
			if (mailbagFilters.getOriginAirportCode() != null
					&& mailbagFilters.getOriginAirportCode().trim().length() > 0) {
				pageQuery.append(" AND MALMST.ORGCOD = ?");
				pageQuery.setParameter(++index, mailbagFilters.getOriginAirportCode());
			}
			if (mailbagFilters.getDestinationAirportCode() != null
					&& mailbagFilters.getDestinationAirportCode().trim().length() > 0) {
				pageQuery.append(" AND MALMST.DSTCOD = ? ");
				pageQuery.setParameter(++index, mailbagFilters.getDestinationAirportCode());
			}
			if (mailbagFilters.getMailCategoryCode() != null
					&& mailbagFilters.getMailCategoryCode().trim().length() > 0) {
				pageQuery.append(" AND MALMST.MALCTG = ?");
				pageQuery.setParameter(++index, mailbagFilters.getMailCategoryCode());
			}
			if (mailbagFilters.getMailSubclass() != null && mailbagFilters.getMailSubclass().trim().length() > 0) {
				pageQuery.append(" AND MALMST.MALSUBCLS = ?");
				pageQuery.setParameter(++index, mailbagFilters.getMailSubclass());
			}
			if (mailbagFilters.getDespatchSerialNumber() != null
					&& mailbagFilters.getDespatchSerialNumber().trim().length() > 0) {
				pageQuery.append(" AND MALMST.DSN = ?");
				pageQuery.setParameter(++index, mailbagFilters.getDespatchSerialNumber());
			}
			if (mailbagFilters.getReceptacleSerialNumber() != null
					&& mailbagFilters.getReceptacleSerialNumber().trim().length() > 0) {
				pageQuery.append(" AND MALMST.RSN = ?");
				pageQuery.setParameter(++index, mailbagFilters.getReceptacleSerialNumber());
			}
			if (mailbagFilters.getPacode() != null && mailbagFilters.getPacode().trim().length() > 0) {
				pageQuery.append(" AND MALMST.POACOD = ?");
				pageQuery.setParameter(++index, mailbagFilters.getPacode());
			}
			if (mailbagFilters.getConsigmentNumber() != null
					&& mailbagFilters.getConsigmentNumber().trim().length() > 0) {
				pageQuery.append(" AND MALMST.CSGDOCNUM = ?");
				pageQuery.setParameter(++index, mailbagFilters.getConsigmentNumber());
			}
			if (mailbagFilters.getConsignmentDate() != null) {
				pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALMST.DSPDAT),'YYYYMMDD')) = ? ");
				pageQuery.setParameter(++index,Integer.parseInt(
						mailbagFilters.getConsignmentDate()
								.format(MailConstantsVO.DATE_TIME_FORMATTER_YYYY_MM_DD).replace("-", "")));
			}
			if (mailbagFilters.getReqDeliveryTime() != null) {
				pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALMST.REQDLVTIM),'YYYYMMDD')) = ? ");
				pageQuery.setParameter(++index,
						Integer.parseInt(mailbagFilters.getReqDeliveryTime()
								.format(MailConstantsVO.DATE_TIME_FORMATTER_YYYY_MM_DD).replace("-", "")));
			}
			if (mailbagFilters.getMasterDocumentNumber() != null
					&& mailbagFilters.getMasterDocumentNumber().trim().length() > 0) {
				pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
				pageQuery.setParameter(++index, mailbagFilters.getMasterDocumentNumber());
			}
			if (mailbagFilters.getShipmentPrefix() != null && mailbagFilters.getShipmentPrefix().trim().length() > 0) {
				pageQuery.append(" AND MALMST.SHPPFX = ?");
				pageQuery.setParameter(++index, mailbagFilters.getShipmentPrefix());
			}
			if (mailbagFilters.getTransferFromCarrier() != null
					&& mailbagFilters.getTransferFromCarrier().trim().length() > 0) {
				pageQuery.append(" AND MAL.FRMCARCOD = ?");
				pageQuery.setParameter(++index, mailbagFilters.getTransferFromCarrier());
			}
			if (mailbagFilters.getCurrentStatus() != null && mailbagFilters.getCurrentStatus().trim().length() > 0) {
				pageQuery.append(" AND ");
				pageQuery.append(" MALMST.MALSTA = ?");
				pageQuery.setParameter(++index, mailbagFilters.getCurrentStatus());
			}
			if ("Y".equals(mailbagFilters.getCarditPresent())) {
				pageQuery.append(" AND CSGDTL.MALSEQNUM IS NOT NULL ");
			}
			if ("Y".equals(mailbagFilters.getDamageFlag())) {
				pageQuery.append(" AND MALMST.DMGFLG = 'Y' ");
			}
		}
		return index;
	}

	public Page<DSNVO> findMailbagsinContainerdsnview(ContainerDetailsVO containervo, int pageNumber)
			throws PersistenceException {
		Page<DSNVO> dsnVos = null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		int index = 0;
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagsinContainer" + " Entering");
		String baseQryforuld = null;
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FLIGHT_ULD_DSNVIEW);
		} else {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FLIGHT_BULK_DSNVIEW);
		}
		rankQuery.append(qry);
		PageableNativeQuery<DSNVO> pageQuery = null;
		pageQuery = new PageableNativeQuery<DSNVO>(containervo.getTotalRecordSize(), -1, rankQuery.toString(),
				new MailbagDSNMapper(),PersistenceController.getEntityManager().currentSession());
		pageQuery.setParameter(++index, containervo.getCompanyCode());
		pageQuery.setParameter(++index, containervo.getCarrierId());
		pageQuery.setParameter(++index, containervo.getFlightNumber());
		pageQuery.setParameter(++index, containervo.getFlightSequenceNumber());
		pageQuery.setParameter(++index, containervo.getPol());
		pageQuery.setParameter(++index, containervo.getContainerNumber());
		if (containervo.getAdditionalFilters() != null) {
			index = populateAdditionalMailbagDSNFilterForFlight(pageQuery, index, containervo);
		}
		pageQuery.append(
				" ORDER BY CON.CONNUM, CON.SEGSERNUM, MALMST.DSN, MALMST.ORGEXGOFC, MALMST.DSTEXGOFC, MALMST.MALSUBCLS, MALMST.MALCTG, MALMST.YER ) MST\r\n"
						+ "GROUP BY CMPCOD,DSN,ORGEXGOFC,DSTEXGOFC,MALSUBCLS,MALCTGCOD,YER,PLTENBFLG ");
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		dsnVos = pageQuery.getPage(pageNumber);
		return dsnVos;
	}

	private int populateAdditionalMailbagDSNFilterForFlight(PageableNativeQuery<DSNVO> pageQuery, int index,
															ContainerDetailsVO containervo) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = containervo.getAdditionalFilters();
		if (mailbagEnquiryFilterVO.getDespatchSerialNumber() != null
				&& mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.DSN = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
		}
		if (mailbagEnquiryFilterVO.getOoe() != null && mailbagEnquiryFilterVO.getOoe().trim().length() > 0) {
			pageQuery.append(" AND MALMST.ORGEXGOFC = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
		}
		if (mailbagEnquiryFilterVO.getDoe() != null && mailbagEnquiryFilterVO.getDoe().trim().length() > 0) {
			pageQuery.append(" AND MALMST.DSTEXGOFC = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
		}
		if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
				&& mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MALCTG = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
		}
		if (mailbagEnquiryFilterVO.getMailSubclass() != null
				&& mailbagEnquiryFilterVO.getMailSubclass().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MALSUBCLS = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
		}
		if (mailbagEnquiryFilterVO.getMasterDocumentNumber() != null
				&& mailbagEnquiryFilterVO.getMasterDocumentNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMasterDocumentNumber());
		}
		if (mailbagEnquiryFilterVO.getShipmentPrefix() != null
				&& mailbagEnquiryFilterVO.getShipmentPrefix().trim().length() > 0) {
			pageQuery.append(" AND MALMST.SHPPFX = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getShipmentPrefix());
		}
		if (mailbagEnquiryFilterVO.getPacode() != null && mailbagEnquiryFilterVO.getPacode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.POACOD = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getPacode());
		}
		return index;
	}

	public MailbagVO findCarditSummaryView(CarditEnquiryFilterVO carditEnquiryFilterVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCarditSummaryView" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CARDIT_SUMMARY_DETAIL);
		String companyCode = carditEnquiryFilterVO.getCompanyCode();
		String ooe = carditEnquiryFilterVO.getOoe();
		String doe = carditEnquiryFilterVO.getDoe();
		String mailCategoryCode = carditEnquiryFilterVO.getMailCategoryCode();
		String mailClass = carditEnquiryFilterVO.getMailClass();
		String mailSubclass = carditEnquiryFilterVO.getMailSubclass();
		String year = carditEnquiryFilterVO.getYear();
		String despatchSerialNumber = carditEnquiryFilterVO.getDespatchSerialNumber();
		String receptacleSerialNumber = carditEnquiryFilterVO.getReceptacleSerialNumber();
		String consignmentDocument = carditEnquiryFilterVO.getConsignmentDocument();
		String paoCode = carditEnquiryFilterVO.getPaoCode();
		ZonedDateTime fromDate = carditEnquiryFilterVO.getFromDate();
		ZonedDateTime toDate = carditEnquiryFilterVO.getToDate();
		String carrierCode = carditEnquiryFilterVO.getCarrierCode();
		String flightNumber = carditEnquiryFilterVO.getFlightNumber();
		ZonedDateTime flightDate = carditEnquiryFilterVO.getFlightDate();
		String pol = carditEnquiryFilterVO.getPol();
		String uldNumber = carditEnquiryFilterVO.getUldNumber();
		String mailStatus = carditEnquiryFilterVO.getMailStatus();
		String mailbagId = carditEnquiryFilterVO.getMailbagId();
		ZonedDateTime reqDeliveryTime = carditEnquiryFilterVO.getReqDeliveryTime();
		String shipmentPrefix = carditEnquiryFilterVO.getShipmentPrefix();
		int index = 0;
		if (MailConstantsVO.FLIGHT_TYP_OPR.equals(carditEnquiryFilterVO.getFlightType())) {
			qry.append(" INNER JOIN MALFLT ASGFLT ");
			qry.append(" ON ASGFLT.CMPCOD  = MALMST.CMPCOD ");
			qry.append(" AND ASGFLT.FLTCARIDR = MALMST.FLTCARIDR ");
			qry.append(" AND ASGFLT.FLTNUM    = MALMST.FLTNUM ");
			qry.append(" AND ASGFLT.FLTSEQNUM = MALMST.FLTSEQNUM ");
		}
		if (carditEnquiryFilterVO.isPendingResditChecked()) {
			qry.append(" INNER JOIN MALRDT MALRDT ");
			qry.append(" ON MALRDT.CMPCOD  = CSGDTL.CMPCOD ");
			qry.append(" AND MALRDT.MALSEQNUM = CSGDTL.MALSEQNUM ");
			qry.append("  AND MALRDT.PROSTA='Y' ");
			qry.append(" AND MALRDT.EVTCOD IN ('74','42','48','6','82','57','24','14','40','23','43','41','21') ");
			qry.append(" AND (MALRDT.RDTSND='Y' OR MALRDT.RDTSND='S') ");
		}
		if (companyCode != null && companyCode.trim().length() > 0) {
			qry.append("WHERE CSGMST.CMPCOD = ?");
			qry.setParameter(++index, companyCode);
		}
		if (carditEnquiryFilterVO.getConsignmentDate() != null) {
			qry.append(" AND TRUNC(CSGMST.CSGDAT) = TO_DATE(?, 'yyyy-MM-dd') ");
			qry.setParameter(++index, carditEnquiryFilterVO.getConsignmentDate().toString());
		}
		if (ooe != null && ooe.trim().length() > 0) {
			qry.append("AND MALMST.ORGEXGOFC = ? ");
			qry.setParameter(++index, ooe);
		}
		if (doe != null && doe.trim().length() > 0) {
			qry.append("AND MALMST.DSTEXGOFC = ?");
			qry.setParameter(++index, doe);
		}
		if (mailCategoryCode != null && mailCategoryCode.trim().length() > 0) {
			qry.append("AND MALMST.MALCTG = ?");
			qry.setParameter(++index, mailCategoryCode);
		}
		if (year != null && year.trim().length() > 0) {
			qry.append("AND MALMST.YER = ?");
			qry.setParameter(++index, year);
		}
		if (despatchSerialNumber != null && despatchSerialNumber.trim().length() > 0) {
			qry.append("AND  MALMST.DSN = ?");
			qry.setParameter(++index, despatchSerialNumber);
		}
		if (receptacleSerialNumber != null && receptacleSerialNumber.trim().length() > 0) {
			qry.append("AND MALMST.RSN = ?");
			qry.setParameter(++index, receptacleSerialNumber);
		}
		if (consignmentDocument != null && consignmentDocument.trim().length() > 0) {
			qry.append("AND CSGMST.CSGDOCNUM = ?");
			qry.setParameter(++index, consignmentDocument);
		}
		if (mailSubclass != null && mailSubclass.trim().length() > 0) {
			qry.append(" AND MALMST.MALSUBCLS = ? ");
			qry.setParameter(++index, mailSubclass);
		}
		if (fromDate != null) {
			qry.append(" AND TRUNC(CSGMST.CSGDAT) >= TO_DATE(?, 'yyyy-MM-dd') ");
			qry.setParameter(++index, fromDate.toString());
		}
		if (toDate != null) {
			qry.append(" AND TRUNC(CSGMST.CSGDAT) <= TO_DATE(?, 'yyyy-MM-dd') ");
			qry.setParameter(++index, toDate.toString());
		}
		if (MailConstantsVO.FLIGHT_TYP_OPR.equals(carditEnquiryFilterVO.getFlightType())) {
			if (carrierCode != null && carrierCode.trim().length() > 0) {
				qry.append("AND ASGFLT.FLTCARCOD = ?");
				qry.setParameter(++index, carrierCode);
			}
			if (flightNumber != null && flightNumber.trim().length() > 0) {
				qry.append("AND ASGFLT.FLTNUM = ?");
				qry.setParameter(++index, flightNumber);
			}
			if (flightDate != null) {
				qry.append("AND TRUNC(ASGFLT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				qry.setParameter(++index, flightDate.toString());
			}
		} else {
			if (carrierCode != null && carrierCode.trim().length() > 0) {
				qry.append("AND TRT.FLTCARCOD = ?");
				qry.setParameter(++index, carrierCode);
			}
			if (flightNumber != null && flightNumber.trim().length() > 0) {
				qry.append("AND TRT.FLTNUM = ?");
				qry.setParameter(++index, flightNumber);
			}
			if (flightDate != null) {
				qry.append("AND TRUNC(TRT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				qry.setParameter(++index, flightDate.toString());
			}
		}
		if (pol != null && pol.trim().length() > 0) {
			qry.append("AND TRT.POL = ?");
			qry.setParameter(++index, pol);
		}
		if (uldNumber != null && uldNumber.trim().length() > 0) {
			qry.append("AND CSGDTL.ULDNUM = ?");
			qry.setParameter(++index, uldNumber);
		}
		if (mailbagId != null && mailbagId.trim().length() > 0) {
			qry.append(" AND MALMST.MALIDR = ? ");
			qry.setParameter(++index, mailbagId);
		}
		if (reqDeliveryTime != null) {
			String rqdDlvTime = reqDeliveryTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) ;
			if (rqdDlvTime != null) {
				if (rqdDlvTime.contains("0000")) {
					qry.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				} else {
					qry.append(" AND MALMST.REQDLVTIM = ?");
				}
				qry.setParameter(++index, reqDeliveryTime);
			}
		}
		if (shipmentPrefix != null && shipmentPrefix.trim().length() > 0) {
			qry.append("AND MALMST.SHPPFX = ?");
			qry.setParameter(++index, shipmentPrefix);
		}
		if (carditEnquiryFilterVO.getDocumentNumber() != null
				&& carditEnquiryFilterVO.getDocumentNumber().trim().length() > 0) {
			qry.append("AND MALMST.MSTDOCNUM = ?");
			qry.setParameter(++index, carditEnquiryFilterVO.getDocumentNumber());
		}
		if (mailStatus != null && mailStatus.trim().length() > 0) {
			if ("ACP".equals(mailStatus)) {
				qry.append(" AND MALMST.MALSTA  NOT IN('NEW','BKD','CAN')");
			} else if ("CAP".equals(mailStatus)) {
				qry.append(" AND MALMST.MALSTA IN('NEW','BKD','CAN')");
			}
		}
		if (carditEnquiryFilterVO.getMailOrigin() != null
				&& carditEnquiryFilterVO.getMailOrigin().trim().length() > 0) {
			qry.append("AND MALMST.ORGCOD= ?");
			qry.setParameter(++index, carditEnquiryFilterVO.getMailOrigin());
		}
		if (carditEnquiryFilterVO.getMaildestination() != null
				&& carditEnquiryFilterVO.getMaildestination().trim().length() > 0) {
			qry.append("AND MALMST.DSTCOD= ?");
			qry.setParameter(++index, carditEnquiryFilterVO.getMaildestination());
		}
		qry.append(" )group by cmpcod");
		log.info("" + "Query: " + " " + qry);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findCarditDetailsForAllMailBags" + " Exiting");
		return qry.getSingleResult(new OutboundCarditSummaryMapper());
	}

	public Page<MailbagVO> findGroupedCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findGroupedCarditMails" + " Entering");
		Page<MailbagVO> mailbagVos = null;
		String baseQry1 = getQueryManager().getNamedNativeQueryString(FIND_CARDIT_GROUP_VIEW_ACCEPTED);
		String baseQry2 = getQueryManager().getNamedNativeQueryString(FIND_CARDIT_GROUP_VIEW_COUNT);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		rankQuery.append(baseQry1);
		PageableNativeQuery<MailbagVO> qry = null;
		if (carditEnquiryFilterVO.getPageSize() > 0) {
			qry = new OutboundCarditGroupFilterQuery(new OutboundCarditGroupMapper(), carditEnquiryFilterVO,
					rankQuery.toString(), baseQry2.toString(), carditEnquiryFilterVO.getPageSize());
		} else {
			qry = new OutboundCarditGroupFilterQuery(new OutboundCarditGroupMapper(), carditEnquiryFilterVO,
					rankQuery.toString(), baseQry2.toString());
		}
		log.info("" + "Query: " + " " + qry);
		mailbagVos = qry.getPage(pageNumber);
		return mailbagVos;
	}

	public MailbagVO findLyinglistSummaryView(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailbags" + " Entering");
		if ("DEVIATION_LIST".equals(mailbagEnquiryFilterVO.getFromScreen())) {
			return findDeviationMailBagSummaryView(mailbagEnquiryFilterVO);
		}
		String outerQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*) COUNT ,sum(WGT) TOTWGT from (";
		String baseQuery = null;
		boolean acceptedmailbagFilterQuery = false;
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append("RNK ) AS RANK FROM (");
		Query qry = null;
		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		int index = 0;
		if (mailbagEnquiryFilterVO.isInventory()) {
			log.info("FOR INVENTORY MAIL BAGS");
			baseQuery = getQueryManager().getNamedNativeQueryString(FIND_INVENTORY_MAILBAGS);
			if (mailbagEnquiryFilterVO.getFromFlightNumber() != null
					&& mailbagEnquiryFilterVO.getFromFlightNumber().trim().length() > 0) {
				qry.append(appendArrivedFlightToInventory());
			}
			qry.append("  AND MALMST.CMPCOD = ? ");
			qry.append(" AND MALMST.SCNPRT= ?   ");
			qry.append(" AND ARPULD.FLTCARIDR= ?  ");
			qry.append(" AND ARPULD.DSTCOD  IS  NULL ");
			qry.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
			qry.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
			qry.setParameter(++index, mailbagEnquiryFilterVO.getCarrierId());
			if (mailbagEnquiryFilterVO.getFromFlightNumber() != null
					&& mailbagEnquiryFilterVO.getFromFlightNumber().trim().length() > 0) {
				qry.append(" AND MALHIS.MALSTA = ?  ");
				qry.append(" AND MALHIS.FLTNUM = ?  ");
				qry.setParameter(++index, MailConstantsVO.MAIL_STATUS_ARRIVED);
				qry.setParameter(++index, mailbagEnquiryFilterVO.getFromFlightNumber());
			}
			if (mailbagEnquiryFilterVO.getContainerNumber() != null
					&& mailbagEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
				qry.append(" AND MALMST.CONNUM= ?  ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getContainerNumber());
			}
			if (mailbagEnquiryFilterVO.getDestinationCity() != null
					&& mailbagEnquiryFilterVO.getDestinationCity().trim().length() > 0) {
				qry.append(" AND SUBSTR( MALMST.DSTEXGOFC,3,3) IN");
				qry.append(new StringBuilder("(").append(mailbagEnquiryFilterVO.getDestinationCity()).append(")")
						.toString());
			}
			log.debug("" + "THE MAIL BAG ENQUIRY FILTER VO" + " " + mailbagEnquiryFilterVO);
			if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
					&& mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
				if (MailConstantsVO.MIL_MAL_CAT.equals(mailbagEnquiryFilterVO.getMailCategoryCode())) {
					qry.append(appendMilitaryClasses(qry, index, true));
				} else {
					qry.append(" AND MALMST.MALCTG IN ");
					qry.append(new StringBuilder("(").append(mailbagEnquiryFilterVO.getMailCategoryCode()).append(")")
							.toString());
					qry.append(appendMilitaryClasses(qry, index, false));
				}
			}
		} else {
			if ("".equals(mailbagEnquiryFilterVO.getCurrentStatus())
					|| mailbagEnquiryFilterVO.getCurrentStatus() == null) {
				baseQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*)COUNT, sum(WGT)TOTWGT from	(";
				baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS));
				log.info("The Status is <<ALL>> ");
				if (!"ACP".equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
					baseQuery = baseQuery.concat(", ROW_NUMBER() OVER(ORDER BY 1) AS RNK ");
				}
				rankQuery.append(baseQuery);
				pageableNativeQuery = new OutboundLyinglistFilterQuery(new OutboundCarditSummaryMapper(),
						mailbagEnquiryFilterVO, rankQuery.toString(), true);
			} else if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
				baseQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*) COUNT, sum(WGT)TOTWGT from	(";
				baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS_FORRETURN));
				rankQuery.append(baseQuery);
				pageableNativeQuery = new OutboundLyingListReturnFilterQuery(new OutboundCarditSummaryMapper(),
						mailbagEnquiryFilterVO, rankQuery.toString(), true);
			} else {
				if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagEnquiryFilterVO.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagEnquiryFilterVO.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
					baseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS);
					outerQuery = outerQuery.concat(baseQuery);
					log.info("The Status is <<ACCEPTED>><<TRANSFERRED>><<ASSIGNED>> ");
					if (MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getFromExportList())) {
						log.debug("Current Status:- ACP from mailExportList");
						acceptedmailbagFilterQuery = true;
						pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
								new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO, baseQuery, outerQuery, true);
						log.debug("Base query passed,suffix query not required");
					} else {
						if ((mailbagEnquiryFilterVO.getCarrierId() == 0)
								&& (mailbagEnquiryFilterVO.getFlightNumber() == null
								|| mailbagEnquiryFilterVO.getFlightNumber().trim().length() == 0)) {
							acceptedmailbagFilterQuery = true;
							if (mailbagEnquiryFilterVO.getFromScreen() != null
									&& "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen())
									&& mailbagEnquiryFilterVO.getFlightNumber() == null) {
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery, outerQuery,
										false);
							} else {
								if ("MAILEXPORTLIST".equals(mailbagEnquiryFilterVO.getFromScreen())) {
									outerQuery = outerQuery.concat(", ROW_NUMBER() OVER(ORDER BY 1) AS RANK");
								}
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery, outerQuery,
										true);
							}
							log.debug("Base query passed,suffix query not required");
						} else {
							if (mailbagEnquiryFilterVO.getCarrierId() > 0
									&& mailbagEnquiryFilterVO.getFromScreen() != null
									&& "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen())
									&& mailbagEnquiryFilterVO.getFlightNumber() == null) {
								log.debug("Carrier level and from assign continer");
								acceptedmailbagFilterQuery = true;
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO, baseQuery,
										outerQuery, true);
							} else if (mailbagEnquiryFilterVO.getCarrierId() > 0
									&& ("MAILEXPORTLIST".equals(mailbagEnquiryFilterVO.getFromScreen()))) {
								acceptedmailbagFilterQuery = true;
								outerQuery = outerQuery.concat(", ROW_NUMBER() OVER(ORDER BY 1) AS RANK");
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO, baseQuery,
										outerQuery, true);
							} else {
								log.debug("not from mailExportList,rank query used,suffix query to be added");
								acceptedmailbagFilterQuery = true;
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO, baseQuery,
										outerQuery.toString(), true);
							}
						}
					}
				} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagEnquiryFilterVO.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_NOTUPLIFTED.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
					log.info("" + "THE STATUS IS " + " " + mailbagEnquiryFilterVO.getCurrentStatus());
					baseQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*) COUNT ,sum(WGT) TOTWGT from (";
					baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS));
					rankQuery.append(baseQuery);
					pageableNativeQuery = new OutboundlyinglistOffloadedFilterQuery(new OutboundCarditSummaryMapper(),
							mailbagEnquiryFilterVO, rankQuery.toString(), true);
				} else if (MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED
						.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
					baseQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*) COUNT ,sum(WGT) TOTWGT from (";
					int carrierId = mailbagEnquiryFilterVO.getCarrierId();
					ZonedDateTime flightDate = mailbagEnquiryFilterVO.getFlightDate();
					ZonedDateTime flightSqlDate = null;

					if (flightDate != null) {
						flightSqlDate = flightDate;
					}
					String flightDateString =flightSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)) ;
					baseQuery = baseQuery
							.concat(getQueryManager().getNamedNativeQueryString(FIND_CAP_NOT_ACCPETED_MAILBAGS));
					rankQuery.append(baseQuery);
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getTotalRecords(),
							rankQuery.toString(), new OutboundCarditSummaryMapper(),PersistenceController.getEntityManager().currentSession());
					if (carrierId > 0) {
						pageableNativeQuery.append(" AND FLTCARIDR = ? ");
						pageableNativeQuery.setParameter(++index, carrierId);
					}
					if (mailbagEnquiryFilterVO.getCarrierCode() != null
							&& mailbagEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
						pageableNativeQuery.append(" AND FLTCARCOD  = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCarrierCode());
					}
					if (mailbagEnquiryFilterVO.getFlightNumber() != null
							&& mailbagEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND FLTNUM = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
					}
					if (flightSqlDate != null) {
						pageableNativeQuery.append(" AND  TRUNC(FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
						pageableNativeQuery.setParameter(++index, flightDateString);
					}
					pageableNativeQuery.append(" ) WHERE ");
					if (mailbagEnquiryFilterVO.getCompanyCode() != null
							&& mailbagEnquiryFilterVO.getCompanyCode().trim().length() > 0) {
						pageableNativeQuery.append(" CSGMAL.CMPCOD = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
					}
					if (mailbagEnquiryFilterVO.getContainerNumber() != null
							&& mailbagEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND CSGMAL.ULDNUM = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getContainerNumber());
					}
					ZonedDateTime scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
					ZonedDateTime scanToDate = mailbagEnquiryFilterVO.getScanToDate();
					if (scanFromDate != null && scanToDate != null) {
						pageableNativeQuery.append(" AND  CSGMST.CSGDAT BETWEEN  ?  ");
						pageableNativeQuery.setParameter(++index, scanFromDate);
						pageableNativeQuery.append(" AND  ?  ");
						pageableNativeQuery.setParameter(++index, scanToDate);
					}
					if (mailbagEnquiryFilterVO.getConsigmentNumber() != null
							&& mailbagEnquiryFilterVO.getConsigmentNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND CSGMAL.CSGDOCNUM = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getConsigmentNumber());
					}
					if (mailbagEnquiryFilterVO.getUpuCode() != null
							&& mailbagEnquiryFilterVO.getUpuCode().trim().length() > 0) {
						pageableNativeQuery.append(" AND POADTL.PARVAL = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getUpuCode());
					}
					if (mailbagEnquiryFilterVO.getReqDeliveryTime() != null) {
						String rqdDlvTime = mailbagEnquiryFilterVO.getReqDeliveryTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
						if (rqdDlvTime != null) {
							if (rqdDlvTime.contains("0000")) {
								pageableNativeQuery.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
							} else {
								pageableNativeQuery.append(" AND MALMST.REQDLVTIM = ?");
							}
							pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReqDeliveryTime());
						}
					}
					if (mailbagEnquiryFilterVO.getDespatchSerialNumber() != null
							&& mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.DSN = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
					}
					if (mailbagEnquiryFilterVO.getOoe() != null && !mailbagEnquiryFilterVO.getOoe().isEmpty()) {
						pageableNativeQuery.append(" AND MALMST.ORGEXGOFC = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
					}
					if (mailbagEnquiryFilterVO.getDoe() != null && !mailbagEnquiryFilterVO.getDoe().isEmpty()) {
						pageableNativeQuery.append(" AND MALMST.DSTEXGOFC = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
					}
					if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
							&& mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.MALCTG = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
					}
					if (mailbagEnquiryFilterVO.getMailSubclass() != null
							&& mailbagEnquiryFilterVO.getMailSubclass().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.MALSUBCLS = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
					}
					if (mailbagEnquiryFilterVO.getYear() != null
							&& mailbagEnquiryFilterVO.getYear().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.YER = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getYear());
					}
					if (mailbagEnquiryFilterVO.getReceptacleSerialNumber() != null
							&& mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.RSN = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReceptacleSerialNumber());
					}
					if (mailbagEnquiryFilterVO.getMailbagId() != null
							&& mailbagEnquiryFilterVO.getMailbagId().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.MALIDR = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
					}
					if (mailbagEnquiryFilterVO.getServiceLevel() != null
							&& mailbagEnquiryFilterVO.getServiceLevel().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.MALSRVLVL = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getServiceLevel());
					}
					if (mailbagEnquiryFilterVO.getOnTimeDelivery() != null
							&& mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.ONNTIMDLVFLG = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
					}
					if (mailbagEnquiryFilterVO.getCarditPresent() != null
							&& MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getCarditPresent())) {
						pageableNativeQuery.append(" AND EXISTS (SELECT 1 FROM MALCDTRCP RCP ")
								.append(" WHERE RCP.CMPCOD = CSGMAL.CMPCOD ")
								.append(" AND RCP.RCPIDR     = MALMST.MALIDR ").append(" ) ");
					}
					pageableNativeQuery.append(")");
				} else {
					log.info("" + "THE STATUS IS--- " + " " + mailbagEnquiryFilterVO.getCurrentStatus());
					baseQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*) COUNT, sum(WGT) TOTWGT from (";
					baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS));
					rankQuery.append(baseQuery);
					pageableNativeQuery = new OutboundLyinglistArrivalFilterQuery(new OutboundCarditGroupMapper(),
							mailbagEnquiryFilterVO, rankQuery.toString(), true);
				}
			}
		}
		log.info("" + "Query is" + " " + pageableNativeQuery.toString());
		AcceptedMailBagFilterQuery.setRank(false);
		if (!acceptedmailbagFilterQuery) {
			pageableNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		return pageableNativeQuery.getSingleResult(new OutboundCarditSummaryMapper());
	}

	private MailbagVO findDeviationMailBagSummaryView(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) {
		StringBuilder baseQuery = null;
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append(" RNK ) AS RANK FROM ( ");
		rankQuery.append(" SELECT ROW_NUMBER() OVER(ORDER BY NULL) AS RNK,");
		rankQuery.append(" COUNT(*) COUNT, SUM(WGT) TOTWGT FROM ( ");
		rankQuery.append("  SELECT  MAX(WGT) WGT, CMPCOD, MALIDR, MALSEQNUM FROM ( ");
		Query masterQuery = null;
		masterQuery = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_MAIN);
		baseQuery = new StringBuilder(rankQuery).append(" ").append(masterQuery);
		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		Query query1 = null;
		query1 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_1);
		Query query2 = null;
		query2 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_2);
		pageableNativeQuery = new DeviationMailbagFilter(new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO,
				baseQuery, query1, query2);
		pageableNativeQuery.append("  )reqdata GROUP BY CMPCOD, MALIDR, MALSEQNUM )maldata ");
		pageableNativeQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		pageableNativeQuery.append(" ");
		return pageableNativeQuery.getSingleResult(new OutboundCarditSummaryMapper());
	}

	public Page<MailbagVO> findGroupedLyingList(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailbags" + " Entering");
		if ("DEVIATION_LIST".equals(mailbagEnquiryFilterVO.getFromScreen())) {
			return findDeviationMailBagGroup(mailbagEnquiryFilterVO, pageNumber);
		}
		String baseQuery = null;
		String outerQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, DSTCOD, count(*) COUNT , sum(case acpflg when 'Y' then 1 else 0 end) ACCPCNT,sum(case acpflg when 'Y' then WGT else 0 end) ACCPWGT,sum(WGT) TOTWGT from (";
		boolean acceptedmailbagFilterQuery = false;
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append("RNK ) AS RANK FROM (");
		Query qry = null;
		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		int index = 0;
		if (mailbagEnquiryFilterVO.isInventory()) {
			log.info("FOR INVENTORY MAIL BAGS");
			baseQuery = getQueryManager().getNamedNativeQueryString(FIND_INVENTORY_MAILBAGS);
			if (mailbagEnquiryFilterVO.getFromFlightNumber() != null
					&& mailbagEnquiryFilterVO.getFromFlightNumber().trim().length() > 0) {
				qry.append(appendArrivedFlightToInventory());
			}
			qry.append("  AND MALMST.CMPCOD = ? ");
			qry.append(" AND MALMST.SCNPRT= ?   ");
			qry.append(" AND ARPULD.FLTCARIDR= ?  ");
			qry.append(" AND ARPULD.DSTCOD  IS  NULL ");
			qry.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
			qry.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
			qry.setParameter(++index, mailbagEnquiryFilterVO.getCarrierId());
			if (mailbagEnquiryFilterVO.getFromFlightNumber() != null
					&& mailbagEnquiryFilterVO.getFromFlightNumber().trim().length() > 0) {
				qry.append(" AND MALHIS.MALSTA = ?  ");
				qry.append(" AND MALHIS.FLTNUM = ?  ");
				qry.setParameter(++index, MailConstantsVO.MAIL_STATUS_ARRIVED);
				qry.setParameter(++index, mailbagEnquiryFilterVO.getFromFlightNumber());
			}
			if (mailbagEnquiryFilterVO.getContainerNumber() != null
					&& mailbagEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
				qry.append(" AND MALMST.CONNUM= ?  ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getContainerNumber());
			}
			if (mailbagEnquiryFilterVO.getDestinationCity() != null
					&& mailbagEnquiryFilterVO.getDestinationCity().trim().length() > 0) {
				qry.append(" AND SUBSTR( MALMST.DSTEXGOFC,3,3) IN");
				qry.append(new StringBuilder("(").append(mailbagEnquiryFilterVO.getDestinationCity()).append(")")
						.toString());
			}
			log.debug("" + "THE MAIL BAG ENQUIRY FILTER VO" + " " + mailbagEnquiryFilterVO);
			if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
					&& mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
				if (MailConstantsVO.MIL_MAL_CAT.equals(mailbagEnquiryFilterVO.getMailCategoryCode())) {
					qry.append(appendMilitaryClasses(qry, index, true));
				} else {
					qry.append(" AND MALMST.MALCTG IN ");
					qry.append(new StringBuilder("(").append(mailbagEnquiryFilterVO.getMailCategoryCode()).append(")")
							.toString());
					qry.append(appendMilitaryClasses(qry, index, false));
				}
			}
		} else {
			if ("".equals(mailbagEnquiryFilterVO.getCurrentStatus())
					|| mailbagEnquiryFilterVO.getCurrentStatus() == null) {
				baseQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK,DSTCOD, count(*) COUNT , sum(case acpflg when 'Y' then 1 else 0 end) ACCPCNT,sum(case acpflg when 'Y' then WGT else 0 end) ACCPWGT,sum(WGT) TOTWGT from (";
				baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS));
				log.info("The Status is <<ALL>> ");
				rankQuery.append(baseQuery);
				if (mailbagEnquiryFilterVO.getPageSize() > 0) {
					pageableNativeQuery = new OutboundLyinglistFilterQuery(new OutboundCarditGroupMapper(),
							mailbagEnquiryFilterVO, rankQuery.toString(), false, mailbagEnquiryFilterVO.getPageSize());
				} else {
					pageableNativeQuery = new OutboundLyinglistFilterQuery(new OutboundCarditGroupMapper(),
							mailbagEnquiryFilterVO, rankQuery.toString(), false);
				}
			} else if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
				baseQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, DSTCOD, count(*) COUNT, sum(case acpflg when 'Y' then 1 else 0 end) ACPCOUNT,sum(case acpflg when 'Y' then WGT else 0 end) ACPWGT,sum(WGT) TOTWGT from (";
				baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS_FORRETURN));
				rankQuery.append(baseQuery);
				pageableNativeQuery = new OutboundLyingListReturnFilterQuery(new OutboundCarditGroupMapper(),
						mailbagEnquiryFilterVO, rankQuery.toString(), false);
			} else {
				if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagEnquiryFilterVO.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagEnquiryFilterVO.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
					outerQuery = "SELECT ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, DSTCOD, COUNT(*) COUNT , COUNT(*) ACCPCNT,SUM(WGT) ACCPWGT,SUM(WGT) TOTWGT FROM (";
					baseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS);
					outerQuery = outerQuery.concat(baseQuery);
					rankQuery.append(outerQuery);
					log.info("The Status is <<ACCEPTED>><<TRANSFERRED>><<ASSIGNED>> ");
					if (MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getFromExportList())) {
						log.debug("Current Status:- ACP from mailExportList");
						rankQuery.append(baseQuery);
						acceptedmailbagFilterQuery = true;
						pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(new OutboundCarditGroupMapper(),
								mailbagEnquiryFilterVO, baseQuery, rankQuery.toString(), false);
						log.debug("Base query passed,suffix query not required");
					} else {
						if ((mailbagEnquiryFilterVO.getCarrierId() == 0)
								&& (mailbagEnquiryFilterVO.getFlightNumber() == null
								|| mailbagEnquiryFilterVO.getFlightNumber().trim().length() == 0)) {
							acceptedmailbagFilterQuery = true;
							if (mailbagEnquiryFilterVO.getFromScreen() != null
									&& "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen())
									&& mailbagEnquiryFilterVO.getFlightNumber() == null) {
								rankQuery.append(outerQuery);
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery,
										rankQuery.toString(), false);
							} else {
								if ("MAILEXPORTLIST".equals(mailbagEnquiryFilterVO.getFromScreen())) {
									outerQuery = outerQuery.concat(", ROW_NUMBER() OVER(ORDER BY 1) AS RANK");
								}
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery,
										rankQuery.toString(), false);
							}
							log.debug("Base query passed,suffix query not required");
						} else {
							if (mailbagEnquiryFilterVO.getCarrierId() > 0
									&& mailbagEnquiryFilterVO.getFromScreen() != null
									&& "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen())
									&& mailbagEnquiryFilterVO.getFlightNumber() == null) {
								log.debug("Carrier level and from assign continer");
								acceptedmailbagFilterQuery = true;
								rankQuery.append(outerQuery);
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery,
										rankQuery.toString(), false);
							} else if (mailbagEnquiryFilterVO.getCarrierId() > 0
									&& ("MAILEXPORTLIST".equals(mailbagEnquiryFilterVO.getFromScreen()))) {
								acceptedmailbagFilterQuery = true;
								outerQuery = outerQuery.concat(", ROW_NUMBER() OVER(ORDER BY 1) AS RANK");
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery, outerQuery,
										false);
							} else {
								log.debug("not from mailExportList,rank query used,suffix query to be added");
								rankQuery.append(outerQuery);
								acceptedmailbagFilterQuery = true;
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery,
										rankQuery.toString(), false);
							}
						}
					}
				} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagEnquiryFilterVO.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_NOTUPLIFTED.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
					log.info("" + "THE STATUS IS " + " " + mailbagEnquiryFilterVO.getCurrentStatus());
					baseQuery = "select DSTCOD, count(*) COUNT, sum(case acpflg when 'Y' then 1 else 0 end) ACPCOUNT,sum(case acpflg when 'Y' then WGT else 0 end) ACPWGT,sum(WGT) TOTWGT from (";
					baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS));
					pageableNativeQuery = new OutboundlyinglistOffloadedFilterQuery(new OutboundCarditGroupMapper(),
							mailbagEnquiryFilterVO, baseQuery.toString(), false);
				} else if (MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED
						.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
					baseQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, DSTCOD, count(*) COUNT ,0 ACPCOUNT,0 ACPWGT,sum(WGT) TOTWGT from (";
					int carrierId = mailbagEnquiryFilterVO.getCarrierId();
					ZonedDateTime flightDate = mailbagEnquiryFilterVO.getFlightDate();
					ZonedDateTime flightSqlDate = null;
					if (flightDate != null) {
						flightSqlDate = flightDate;
					}
					String flightDateString = flightSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD));
					baseQuery = baseQuery
							.concat(getQueryManager().getNamedNativeQueryString(FIND_CAP_NOT_ACCPETED_MAILBAGS));
					rankQuery.append(baseQuery);
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getTotalRecords(),
							rankQuery.toString(), new OutboundCarditGroupMapper(),PersistenceController.getEntityManager().currentSession());
					if (carrierId > 0) {
						pageableNativeQuery.append(" AND FLTCARIDR = ? ");
						pageableNativeQuery.setParameter(++index, carrierId);
					}
					if (mailbagEnquiryFilterVO.getCarrierCode() != null
							&& mailbagEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
						pageableNativeQuery.append(" AND FLTCARCOD  = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCarrierCode());
					}
					if (mailbagEnquiryFilterVO.getFlightNumber() != null
							&& mailbagEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND FLTNUM = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
					}
					if (flightSqlDate != null) {
						pageableNativeQuery.append(" AND  TRUNC(FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
						pageableNativeQuery.setParameter(++index, flightDateString);
					}
					pageableNativeQuery.append(" ) WHERE ");
					if (mailbagEnquiryFilterVO.getCompanyCode() != null
							&& mailbagEnquiryFilterVO.getCompanyCode().trim().length() > 0) {
						pageableNativeQuery.append(" CSGMAL.CMPCOD = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
					}
					if (mailbagEnquiryFilterVO.getContainerNumber() != null
							&& mailbagEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND CSGMAL.ULDNUM = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getContainerNumber());
					}
					ZonedDateTime scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
					ZonedDateTime scanToDate = mailbagEnquiryFilterVO.getScanToDate();
					if (scanFromDate != null && scanToDate != null) {
						pageableNativeQuery.append(" AND  CSGMST.CSGDAT BETWEEN  ?  ");
						pageableNativeQuery.setParameter(++index, scanFromDate);
						pageableNativeQuery.append(" AND  ?  ");
						pageableNativeQuery.setParameter(++index, scanToDate);
					}
					if (mailbagEnquiryFilterVO.getConsigmentNumber() != null
							&& mailbagEnquiryFilterVO.getConsigmentNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND CSGMAL.CSGDOCNUM = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getConsigmentNumber());
					}
					if (mailbagEnquiryFilterVO.getUpuCode() != null
							&& mailbagEnquiryFilterVO.getUpuCode().trim().length() > 0) {
						pageableNativeQuery.append(" AND POADTL.PARVAL = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getUpuCode());
					}
					if (mailbagEnquiryFilterVO.getReqDeliveryTime() != null) {
						String rqdDlvTime = mailbagEnquiryFilterVO.getReqDeliveryTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm") );
						if (rqdDlvTime != null) {
							if (rqdDlvTime.contains("0000")) {
								pageableNativeQuery.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
							} else {
								pageableNativeQuery.append(" AND MALMST.REQDLVTIM = ?");
							}
							pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReqDeliveryTime());
						}
					}
					if (mailbagEnquiryFilterVO.getDespatchSerialNumber() != null
							&& mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.DSN = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
					}
					if (mailbagEnquiryFilterVO.getOoe() != null && !mailbagEnquiryFilterVO.getOoe().isEmpty()) {
						pageableNativeQuery.append(" AND MALMST.ORGEXGOFC = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
					}
					if (mailbagEnquiryFilterVO.getDoe() != null && !mailbagEnquiryFilterVO.getDoe().isEmpty()) {
						pageableNativeQuery.append(" AND MALMST.DSTEXGOFC = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
					}
					if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
							&& mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.MALCTG = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
					}
					if (mailbagEnquiryFilterVO.getMailSubclass() != null
							&& mailbagEnquiryFilterVO.getMailSubclass().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.MALSUBCLS = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
					}
					if (mailbagEnquiryFilterVO.getYear() != null
							&& mailbagEnquiryFilterVO.getYear().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.YER = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getYear());
					}
					if (mailbagEnquiryFilterVO.getReceptacleSerialNumber() != null
							&& mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.RSN = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReceptacleSerialNumber());
					}
					if (mailbagEnquiryFilterVO.getMailbagId() != null
							&& mailbagEnquiryFilterVO.getMailbagId().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.MALIDR = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
					}
					if (mailbagEnquiryFilterVO.getServiceLevel() != null
							&& mailbagEnquiryFilterVO.getServiceLevel().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.MALSRVLVL = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getServiceLevel());
					}
					if (mailbagEnquiryFilterVO.getOnTimeDelivery() != null
							&& mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length() > 0) {
						pageableNativeQuery.append(" AND MALMST.ONNTIMDLVFLG = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
					}
					if (mailbagEnquiryFilterVO.getCarditPresent() != null
							&& MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getCarditPresent())) {
						pageableNativeQuery.append(" AND EXISTS (SELECT 1 FROM MALCDTRCP RCP ")
								.append(" WHERE RCP.CMPCOD = CSGMAL.CMPCOD ")
								.append(" AND RCP.RCPIDR     = MALMST.MALIDR ").append(" ) ");
					}
					pageableNativeQuery.append(")MST group by DSTCOD");
				} else {
					log.info("" + "THE STATUS IS--- " + " " + mailbagEnquiryFilterVO.getCurrentStatus());
					baseQuery = "select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, DSTCOD, count(*) COUNT, sum(case acpflg when 'Y' then 1 else 0 end) ACPCOUNT,sum(case acpflg when 'Y' then WGT else 0 end) ACPWGT,sum(WGT) TOTWGT from (";
					baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS));
					rankQuery.append(baseQuery);
					pageableNativeQuery = new OutboundLyinglistArrivalFilterQuery(new OutboundCarditGroupMapper(),
							mailbagEnquiryFilterVO, rankQuery.toString(), false);
				}
			}
		}
		log.info("" + "Query is" + " " + pageableNativeQuery.toString());
		AcceptedMailBagFilterQuery.setRank(false);
		if (!acceptedmailbagFilterQuery) {
			pageableNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		return pageableNativeQuery.getPage(pageNumber);
	}

	private Page<MailbagVO> findDeviationMailBagGroup(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws PersistenceException{
		log.debug("findDeviationGroupedMails");
		StringBuilder baseQuery = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		rankQuery.append(" SELECT  COUNT(*) COUNT,  SUM(WGT) TOTWGT, DSTCOD,  ERRORCOD FROM ( ");
		Query masterQuery = null;
		masterQuery = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_MAIN);
		baseQuery = new StringBuilder(rankQuery).append(" ").append(masterQuery);
		Page<MailbagVO> mailbagVos = null;

		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		Query query1 = null;
		query1 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_1);
		Query query2 = null;
		query2 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_2);
		pageableNativeQuery = new DeviationMailbagFilter(new DeviationMailBagGroupMapper(), mailbagEnquiryFilterVO,
				baseQuery, query1, query2);
		pageableNativeQuery.append("  ) MST GROUP BY ERRORCOD, DSTCOD ");
		pageableNativeQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		pageableNativeQuery.append(" ");
		mailbagVos = pageableNativeQuery.getPage(pageNumber);
		return mailbagVos;
	}

	public Page<MailAcceptanceVO> findOutboundCarrierDetails(OperationalFlightVO operationalFlightVO, int pageNumber)
			throws PersistenceException {
		Page<MailAcceptanceVO> flightvos = null;
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findFlightDetailsforOutbound" + " Entering");
		String baseQryforuld = null;
		String baseQry1 = getQueryManager().getNamedNativeQueryString(OUTBOUND_FIND_CARRIER_FOR_ULD);
		String modifiedStr1 = null;
		if (isOracleDataSource()) {
			modifiedStr1 = "LISTAGG(CONNAM,',') within GROUP (ORDER BY connam) CONNAM , LISTAGG(CONCNT,',') within GROUP (ORDER BY connam) CONCNT , LISTAGG(MALCNT,',') within GROUP (ORDER BY connam) MALCNT , LISTAGG(WGT,',') within GROUP (ORDER BY connam) WGT";
		} else {
			modifiedStr1 = "string_agg ( connam,',' ORDER BY  connam) CONNAM, string_agg(CAST (CONCNT as character varying),',' ORDER BY connam)  CONCNT , string_agg(CAST (MALCNT as character varying),',' ORDER BY connam) MALCNT , string_agg(CAST (wgt as character varying), ',' ORDER BY connam) WGT";
		}
		baseQry1 = String.format(baseQry1, modifiedStr1);
		String baseQry2 = getQueryManager().getNamedNativeQueryString(OUTBOUND_FIND_CARRIER_FOR_BULK);
		String baseQry3 = getQueryManager().getNamedNativeQueryString(OUTBOUND_FIND_CARRIER_FOR_EMPTY);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		rankQuery.append(baseQry1);
		int recordsPerPage = 25;
		if (operationalFlightVO.getRecordsPerPage() != 0) {
			recordsPerPage = operationalFlightVO.getRecordsPerPage();
		}
		PageableNativeQuery<MailAcceptanceVO> pageQuery = new OutboundCarrierFilterQuery(recordsPerPage,
				operationalFlightVO, rankQuery, baseQry2, baseQry3, new OutboundCarrierMapper());
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		log.info("" + "FINAL query " + " " + pageQuery.toString());
		log.info("" + "Query: " + " " + pageQuery);
		flightvos = pageQuery.getPage(pageNumber);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "flightvos" + " Entering");
		return flightvos;
	}

	public Page<MailbagVO> getMailbagsinCarrierContainer(ContainerDetailsVO containervo, int pageNumber)
			throws PersistenceException {
		Page<MailbagVO> mailbagvos = null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		int index = 0;
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "getMailbagsinCarrierContainer" + " Entering");
		String baseQryforuld = null;
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_IN_CARRIER_ULD);
		} else {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_IN_CARRIER_BULK);
		}
		rankQuery.append(qry);
		PageableNativeQuery<MailbagVO> pageQuery = null;
		pageQuery = new PageableNativeQuery<MailbagVO>(containervo.getTotalRecordSize(), -1, rankQuery.toString(),
				new OutboundMailbagMapper(),PersistenceController.getEntityManager().currentSession());
		pageQuery.setParameter(++index, containervo.getCompanyCode());
		pageQuery.setParameter(++index, containervo.getCarrierId());
		pageQuery.setParameter(++index, containervo.getPol());
		pageQuery.setParameter(++index, containervo.getContainerNumber());
		if (containervo.getDestination() != null && containervo.getDestination().trim().length() > 0) {
			pageQuery.append("AND MST.DSTCOD = ?");
			pageQuery.setParameter(++index, containervo.getDestination());
		}
		index = populateMailagAdditionalFilter(pageQuery, index, containervo);
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			pageQuery.append(
					" ORDER BY MST.CONNUM,MST.SEGSERNUM,MALMST.DSN,MALMST.ORGEXGOFC,MALMST.DSTEXGOFC,MALMST.MALSUBCLS,MALMST.MALCTG,MALMST.YER");
		} else {
			pageQuery.append(
					" ORDER BY MST.CONNUM,MST.SEGSERNUM,DSNMST.DSN,DSNMST.ORGEXGOFC,DSNMST.DSTEXGOFC,DSNMST.MALSUBCLS,DSNMST.MALCTG,DSNMST.YER");
		}
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		mailbagvos = pageQuery.getPage(pageNumber);
		return mailbagvos;
	}

	private int populateMailagAdditionalFilter(PageableNativeQuery<MailbagVO> pageQuery, int index,
											   ContainerDetailsVO containervo) {
		if (containervo.getAdditionalFilters() != null) {
			MailbagEnquiryFilterVO mailbagFilter = containervo.getAdditionalFilters();
			if (mailbagFilter.getMailbagId() != null && mailbagFilter.getMailbagId().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.MALIDR = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailbagId());
				} else {
					pageQuery.append(" AND DSNMST.MALIDR = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailbagId());
				}
			}
			if (mailbagFilter.getOriginAirportCode() != null
					&& mailbagFilter.getOriginAirportCode().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.ORGCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getOriginAirportCode());
				} else {
					pageQuery.append(" AND DSNMST.ORGCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getOriginAirportCode());
				}
			}
			if (mailbagFilter.getDestinationAirportCode() != null
					&& mailbagFilter.getDestinationAirportCode().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.DSTCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getDestinationAirportCode());
				} else {
					pageQuery.append(" AND DSNMST.DSTCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getDestinationAirportCode());
				}
			}
			if (mailbagFilter.getMailCategoryCode() != null
					&& mailbagFilter.getMailCategoryCode().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.MALCTG = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailCategoryCode());
				} else {
					pageQuery.append(" AND DSNMST.MALCTG = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailCategoryCode());
				}
			}
			if (mailbagFilter.getMailSubclass() != null && mailbagFilter.getMailSubclass().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.MALSUBCLS = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailSubclass());
				} else {
					pageQuery.append(" AND DSNMST.MALSUBCLS = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailSubclass());
				}
			}
			if (mailbagFilter.getDespatchSerialNumber() != null
					&& mailbagFilter.getDespatchSerialNumber().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.DSN = ?");
					pageQuery.setParameter(++index, mailbagFilter.getDespatchSerialNumber());
				} else {
					pageQuery.append(" AND DSNMST.DSN = ?");
					pageQuery.setParameter(++index, mailbagFilter.getDespatchSerialNumber());
				}
			}
			if (mailbagFilter.getReceptacleSerialNumber() != null
					&& mailbagFilter.getReceptacleSerialNumber().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.RSN = ?");
					pageQuery.setParameter(++index, mailbagFilter.getReceptacleSerialNumber());
				} else {
					pageQuery.append(" AND DSNMST.RSN = ?");
					pageQuery.setParameter(++index, mailbagFilter.getReceptacleSerialNumber());
				}
			}
			if (mailbagFilter.getPacode() != null && mailbagFilter.getPacode().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.POACOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getPacode());
				} else {
					pageQuery.append(" AND DSNMST.POACOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getPacode());
				}
			}
			if (mailbagFilter.getConsigmentNumber() != null
					&& mailbagFilter.getConsigmentNumber().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.CSGDOCNUM = ?");
					pageQuery.setParameter(++index, mailbagFilter.getConsigmentNumber());
				} else {
					pageQuery.append(" AND DSNMST.CSGDOCNUM = ?");
					pageQuery.setParameter(++index, mailbagFilter.getConsigmentNumber());
				}
			}
			if (mailbagFilter.getConsignmentDate() != null) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALMST.DSPDAT),'YYYYMMDD')) = ? ");
					pageQuery.setParameter(++index,
							mailbagFilter.getConsignmentDate().toString().replace("-", ""));
				} else {
					pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(DSNMST.DSPDAT),'YYYYMMDD')) = ? ");
					pageQuery.setParameter(++index,
							mailbagFilter.getConsignmentDate().toString().replace("-", ""));
				}
			}
			if (mailbagFilter.getReqDeliveryTime() != null) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALMST.REQDLVTIM),'YYYYMMDD')) = ? ");
					pageQuery.setParameter(++index,
							mailbagFilter.getReqDeliveryTime().toString().replace("-", ""));
				} else {
					pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(DSNMST.REQDLVTIM),'YYYYMMDD')) = ? ");
					pageQuery.setParameter(++index,
							mailbagFilter.getReqDeliveryTime().toString().replace("-", ""));
				}
			}
			if (mailbagFilter.getMasterDocumentNumber() != null
					&& mailbagFilter.getMasterDocumentNumber().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMasterDocumentNumber());
				} else {
					pageQuery.append(" AND DSNMST.MSTDOCNUM = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMasterDocumentNumber());
				}
			}
			if (mailbagFilter.getShipmentPrefix() != null && mailbagFilter.getShipmentPrefix().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.SHPPFX = ?");
					pageQuery.setParameter(++index, mailbagFilter.getShipmentPrefix());
				} else {
					pageQuery.append(" AND DSNMST.SHPPFX = ?");
					pageQuery.setParameter(++index, mailbagFilter.getShipmentPrefix());
				}
			}
			if (mailbagFilter.getTransferFromCarrier() != null
					&& mailbagFilter.getTransferFromCarrier().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND ARPULD.FRMCARCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getTransferFromCarrier());
				} else {
					pageQuery.append(" AND CONARP.FRMCARCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getTransferFromCarrier());
				}
			}
			if (mailbagFilter.getCurrentStatus() != null && mailbagFilter.getCurrentStatus().trim().length() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.MALSTA = ?");
					pageQuery.setParameter(++index, mailbagFilter.getCurrentStatus());
				} else {
					pageQuery.append(" AND DSNMST.MALSTA = ?");
					pageQuery.setParameter(++index, mailbagFilter.getCurrentStatus());
				}
			}
			if ("Y".equals(mailbagFilter.getCarditPresent())) {
				pageQuery.append(" AND CSGDTL.MALSEQNUM IS NOT NULL ");
			}
			if ("Y".equals(mailbagFilter.getDamageFlag())) {
				if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.DMGFLG = 'Y' ");
				} else {
					pageQuery.append(" AND DSNMST.DMGFLG = 'Y' ");
				}
			}
		}
		return index;
	}

	public Page<DSNVO> getMailbagsinCarrierContainerdsnview(ContainerDetailsVO containervo, int pageNumber)
			throws PersistenceException {
		Page<DSNVO> dsnvos = null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		int index = 0;
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagsinContainer" + " Entering");
		String baseQryforuld = null;
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_CARRIER_ULD_DSNVIEW);
		} else {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_CARRIER_BULK_DSNVIEW);
		}
		rankQuery.append(qry);
		PageableNativeQuery<DSNVO> pageQuery = null;
		pageQuery = new PageableNativeQuery<DSNVO>(containervo.getTotalRecordSize(), -1, rankQuery.toString(),
				new MailbagDSNMapper(),PersistenceController.getEntityManager().currentSession());
		pageQuery.setParameter(++index, containervo.getCompanyCode());
		pageQuery.setParameter(++index, containervo.getCarrierId());
		pageQuery.setParameter(++index, containervo.getPol());
		pageQuery.setParameter(++index, containervo.getContainerNumber());
		if (containervo.getDestination() != null && containervo.getDestination().trim().length() > 0) {
			pageQuery.append("AND MST.DSTCOD = ?");
			pageQuery.setParameter(++index, containervo.getDestination());
		}
		if (containervo.getAdditionalFilters() != null) {
			index = populateAdditionalMailbagDSNFilter(pageQuery, index, containervo);
		}
		pageQuery.append(
				" ORDER BY MST.CONNUM,MST.SEGSERNUM,MALMST.DSN,MALMST.ORGEXGOFC,MALMST.DSTEXGOFC,MALMST.MALSUBCLS,MALMST.MALCTG,MALMST.YER) MST GROUP BY CMPCOD,DSN,ORGEXGOFC,DSTEXGOFC,MALSUBCLS,MALCTGCOD,YER");
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		dsnvos = pageQuery.getPage(pageNumber);
		return dsnvos;
	}

	private int populateAdditionalMailbagDSNFilter(PageableNativeQuery<DSNVO> pageQuery, int index,
												   ContainerDetailsVO containervo) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = containervo.getAdditionalFilters();
		if (mailbagEnquiryFilterVO.getDespatchSerialNumber() != null
				&& mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.DSN = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
		}
		if (mailbagEnquiryFilterVO.getOoe() != null && mailbagEnquiryFilterVO.getOoe().trim().length() > 0) {
			pageQuery.append(" AND MALMST.ORGEXGOFC = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
		}
		if (mailbagEnquiryFilterVO.getDoe() != null && mailbagEnquiryFilterVO.getDoe().trim().length() > 0) {
			pageQuery.append(" AND MALMST.DSTEXGOFC = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
		}
		if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
				&& mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MALCTG = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
		}
		if (mailbagEnquiryFilterVO.getMailSubclass() != null
				&& mailbagEnquiryFilterVO.getMailSubclass().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MALSUBCLS = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
		}
		if (mailbagEnquiryFilterVO.getMasterDocumentNumber() != null
				&& mailbagEnquiryFilterVO.getMasterDocumentNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMasterDocumentNumber());
		}
		if (mailbagEnquiryFilterVO.getShipmentPrefix() != null
				&& mailbagEnquiryFilterVO.getShipmentPrefix().trim().length() > 0) {
			pageQuery.append(" AND MALMST.SHPPFX = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getShipmentPrefix());
		}
		if (mailbagEnquiryFilterVO.getPacode() != null && mailbagEnquiryFilterVO.getPacode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.POACOD = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getPacode());
		}
		return index;
	}

	public Collection<DSNVO> getDSNsForContainer(ContainerDetailsVO containervo) throws PersistenceException {
		List<DSNVO> dsnVos = null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		int index = 0;
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagsinContainer" + " Entering");
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FLIGHT_ULD_DSNVIEW);
			qry.setParameter(++index, containervo.getCompanyCode());
			qry.setParameter(++index, containervo.getCarrierId());
			qry.setParameter(++index, containervo.getFlightNumber());
			qry.setParameter(++index, containervo.getFlightSequenceNumber());
			qry.setParameter(++index, containervo.getPol());
			qry.setParameter(++index, containervo.getContainerNumber());
		} else {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FLIGHT_BULK_DSNVIEW);
			qry.setParameter(++index, containervo.getCompanyCode());
			qry.setParameter(++index, containervo.getCarrierId());
			qry.setParameter(++index, containervo.getFlightNumber());
			qry.setParameter(++index, containervo.getFlightSequenceNumber());
			qry.setParameter(++index, containervo.getPol());
			qry.setParameter(++index, containervo.getContainerNumber());
		}
		qry.append(
				" ORDER BY CON.CONNUM, CON.SEGSERNUM, MALMST.DSN, MALMST.ORGEXGOFC, MALMST.DSTEXGOFC, MALMST.MALSUBCLS, MALMST.MALCTG, MALMST.YER ) MST\r\n"
						+ "GROUP BY CMPCOD,DSN,ORGEXGOFC,DSTEXGOFC,MALSUBCLS,MALCTGCOD,YER,PLTENBFLG ");
		dsnVos = qry.getResultList(new MailbagDSNMapper());
		return dsnVos;
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#listFlightDetails(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO) Added by 			: A-8164 on 25-Sep-2018 Used for 	: Parameters	:	@param mailArrivalVO Parameters	:	@return Parameters	:	@throws SystemException
	 */
	public Page<MailArrivalVO> listFlightDetails(MailArrivalVO mailArrivalVO) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "listFlightDetails" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_FLIGHTS_FOR_INBOUND1);
		Query query2 = getQueryManager().createNamedNativeQuery(FIND_FLIGHTS_FOR_INBOUND2);
		Query query3 = getQueryManager().createNamedNativeQuery(FIND_FLIGHTS_FOR_INBOUND3);
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		masterQuery.append(query);
		if (mailArrivalVO.getDefaultPageSize() == 0) {
			mailArrivalVO.setDefaultPageSize(10);
		}
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		PageableNativeQuery<MailArrivalVO> pgNativeQuery = new PageableNativeQuery<MailArrivalVO>(
				mailArrivalVO.getDefaultPageSize(), -1, masterQuery.toString(), new ListFlightDetailsMultiMapper(),
				PersistenceController.getEntityManager().currentSession());
		int index = 0;
		if (null != mailArrivalVO.getCompanyCode()) {
			pgNativeQuery.append("AND FLTMST.CMPCOD = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getCompanyCode());
		}
		if (null != mailArrivalVO.getAirportCode()) {
			pgNativeQuery.append("AND FLTSEG.SEGDST =  ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getAirportCode());
			pgNativeQuery.append("AND FLTLEG.LEGDST =  ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getAirportCode());
		}
		if (null != mailArrivalVO.getFlightNumber()) {
			pgNativeQuery.append("AND FLTMST.FLTNUM    = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightNumber());
		}
		if (null != mailArrivalVO.getFlightCarrierCode()) {
			pgNativeQuery.append("AND ARLMST.TWOAPHCOD    = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightCarrierCode());
		}
		if (null != mailArrivalVO.getFlightDate()) {
			pgNativeQuery.append("AND TO_NUMBER (to_char (fltleg.sta, 'YYYYMMDD')) = ?");
			pgNativeQuery.setParameter(++index, Integer
					.parseInt(mailArrivalVO.getFlightDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		}
		if (null != mailArrivalVO.getFlightStatus()) {
			if ("O".equals(mailArrivalVO.getFlightStatus())) {
				pgNativeQuery.append("AND (ULDSEG.ULDNUM IS NOT NULL AND COALESCE(MALFLT.IMPCLSFLG,'O') ='O')");
			} else if ("N".equals(mailArrivalVO.getFlightStatus())) {
				pgNativeQuery.append("AND ULDSEG.ULDNUM IS  NULL");
			} else {
				pgNativeQuery.append("AND MALFLT.IMPCLSFLG = ?");
				pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightStatus());
			}
		}
		if (null != mailArrivalVO.getArrivalPA()) {
			pgNativeQuery.append("AND MALMST.POACOD = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getArrivalPA());
		}
		if (null != mailArrivalVO.getTransferCarrier()) {
			pgNativeQuery.append("AND SEGDTL.TRFCARCOD = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getTransferCarrier());
		}
		if (null != mailArrivalVO.getPol() && mailArrivalVO.getPol().trim().length() > 0) {
			pgNativeQuery.append(" AND  FLTSEG.SEGORG = ? ");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getPol());
		}
		if (null != mailArrivalVO.getFromDate() && null != mailArrivalVO.getToDate()
				&& !isFlightFilterPresent(mailArrivalVO)) {
			pgNativeQuery.append(" AND TO_NUMBER(TO_CHAR(FLTLEG.STA, 'YYYYMMDD')) >= ? ");
			pgNativeQuery.setParameter(++index, Integer
					.parseInt(mailArrivalVO.getFromDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
			pgNativeQuery.append(" AND TO_NUMBER(TO_CHAR(FLTLEG.STA, 'YYYYMMDD')) <= ? ");
			pgNativeQuery.setParameter(++index, Integer
					.parseInt(mailArrivalVO.getToDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		} else {
			if (null != mailArrivalVO.getFromDate() && !isFlightFilterPresent(mailArrivalVO)) {
				pgNativeQuery.append(" AND TO_NUMBER(TO_CHAR(FLTLEG.STA, 'YYYYMMDD')) >= ? ");
				pgNativeQuery.setParameter(++index, Integer
						.parseInt(mailArrivalVO.getFromDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
			}
			if (null != mailArrivalVO.getToDate() && !isFlightFilterPresent(mailArrivalVO)) {
				pgNativeQuery.append(" AND TO_NUMBER(TO_CHAR(FLTLEG.STA, 'YYYYMMDD')) <= ? ");
				pgNativeQuery.setParameter(++index, Integer
						.parseInt(mailArrivalVO.getToDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
			}
		}
		String operatingReference = mailArrivalVO.getOperatingReference();
		if (operatingReference != null && operatingReference.trim().length() != 0) {
			pgNativeQuery.append(" AND coalesce(FLTREF.REFCARCOD,'" + logonAttributes.getOwnAirlineCode() + "') IN ( ");
			String[] operatingReferences = operatingReference.split(",");
			int flightRefIndex = 0;
			for (String fltRef : operatingReferences) {
				pgNativeQuery.append("?");
				if (++flightRefIndex < operatingReferences.length) {
					pgNativeQuery.append(",");
				}
				pgNativeQuery.setParameter(++index, fltRef.trim());
			}
			pgNativeQuery.append(")");
		}
		pgNativeQuery
				.append(") mst GROUP BY CMPCOD,FLTNUM,FLTDAT,FLTCARIDR,FLTSEQNUM,FLTCARCOD,LEGDST,LEGORG,FLTSTA,FLTROU,"
						+ "STA,ETA,ATA,ACRTYP,ARVGTE,LEGSERNUM,ARRTIM,IMPCLSFLG");
		if (mailArrivalVO.isMailFlightChecked()) {
			pgNativeQuery.append(" HAVING SUM(TOTACPBAG)+SUM(TOTRCVBAG) >= 1 ");
		}
		pgNativeQuery.append(" ORDER BY  fltdat ");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		log.info("" + "FINAL query " + " " + pgNativeQuery.toString());
		return pgNativeQuery.getPage(mailArrivalVO.getPageNumber());
	}
	public Collection<MailArrivalVO> listManifestDetails(MailArrivalVO mailArrivalVO) {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "listManifestDetails" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MANIFEST_DETAILS);
		boolean isFlightFilterPresent = false;
		if (mailArrivalVO.getFlightNumber() != null && mailArrivalVO.getFlightNumber().trim().length() > 0
				&& mailArrivalVO.getFlightDate() != null) {
			isFlightFilterPresent = true;
		}
		int index = 0;
		if (null != mailArrivalVO.getFlightCarrierCode()) {
			query.append("AND FLTCON.CMPCOD = ?");
			query.setParameter(++index, mailArrivalVO.getFlightCarrierCode());
		}
		if (null != mailArrivalVO.getAirportCode()) {
			query.append("AND SEG.POU =  ?");
			query.setParameter(++index, mailArrivalVO.getAirportCode());
		}
		if (null != mailArrivalVO.getFlightNumber()) {
			query.append("AND MALFLT.FLTNUM    = ?");
			query.setParameter(++index, mailArrivalVO.getFlightNumber());
		}
		if (null != mailArrivalVO.getFlightDate()) {
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) = ?");
			query.setParameter(++index, Integer.parseInt(
					mailArrivalVO.getFlightDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		}
		if (!isFlightFilterPresent && null != mailArrivalVO.getFromDate()) {
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) >= ?");
			query.setParameter(++index, Integer
					.parseInt(mailArrivalVO.getFromDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		}
		if (!isFlightFilterPresent && null != mailArrivalVO.getToDate()) {
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) <= ? ");
			query.setParameter(++index, Integer
					.parseInt(mailArrivalVO.getToDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		}
		Query query2 = getQueryManager().createNamedNativeQuery(FIND_MANIFEST_DETAILS2);
		query.combine(query2);
		if (null != mailArrivalVO.getFlightCarrierCode()) {
			query.append("AND FLTCON.CMPCOD = ?");
			query.setParameter(++index, mailArrivalVO.getFlightCarrierCode());
		}
		if (null != mailArrivalVO.getAirportCode()) {
			query.append("AND SEG.POU =  ?");
			query.setParameter(++index, mailArrivalVO.getAirportCode());
		}
		if (null != mailArrivalVO.getFlightNumber()) {
			query.append("AND MALFLT.FLTNUM    = ?");
			query.setParameter(++index, mailArrivalVO.getFlightNumber());
		}
		if (null != mailArrivalVO.getFlightDate()) {
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) = ?");
			query.setParameter(++index, Integer.parseInt(
					mailArrivalVO.getFlightDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		}
		if (!isFlightFilterPresent && null != mailArrivalVO.getFromDate()) {
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) >= ?");
			query.setParameter(++index, Integer
					.parseInt(mailArrivalVO.getFromDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		}
		if (!isFlightFilterPresent && null != mailArrivalVO.getToDate()) {
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) <= ? ");
			query.setParameter(++index, Integer
					.parseInt(mailArrivalVO.getToDate().format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		}
		query.append(") mst GROUP BY CMPCOD,FLTCARIDR,FLTNUM,FLTSEQNUM,SEGSERNUM,CONNUM");
		return query.getResultList(new ListManifestDetailsMultiMapper());
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findArrivedContainersForInbound(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO) Added by 			: A-8164 on 29-Dec-2018 Used for 	: Parameters	:	@param mailArrivalFilterVO Parameters	:	@return Parameters	:	@throws SystemException
	 */
	public Page<ContainerDetailsVO> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO) {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findArrivedContainersForInbound" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_INBOUND_CONTAINER_DETAILS);
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		masterQuery.append(query);
		if (mailArrivalFilterVO.getDefaultPageSize() == 0) {
			mailArrivalFilterVO.setDefaultPageSize(10);
		}
		PageableNativeQuery<ContainerDetailsVO> pgNativeQuery = new PageableNativeQuery<ContainerDetailsVO>(
				mailArrivalFilterVO.getDefaultPageSize(), -1, masterQuery.toString(),
				new InboundListContainerDetailsMultiMapper(),
				PersistenceController.getEntityManager().currentSession());
		int index = 0;
		for (int i = 0; i < 1; i++) {
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCompanyCode());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCarrierId());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getFlightNumber());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getFlightSequenceNumber());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPou());
		}
		if (mailArrivalFilterVO.getContainerNumber() != null
				&& mailArrivalFilterVO.getContainerNumber().trim().length() > 0) {
			pgNativeQuery.append(" AND ULD.ULDNUM =?  ");
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getContainerNumber());
		}
		if (mailArrivalFilterVO.getPol() != null && mailArrivalFilterVO.getPol().trim().length() > 0) {
			pgNativeQuery.append(" AND CON.ASGPRT =?  ");
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPol());
		}
		if (mailArrivalFilterVO.getDestination() != null && mailArrivalFilterVO.getDestination().trim().length() > 0) {
			pgNativeQuery.append(" AND CON.DSTCOD =?  ");
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getDestination());
		}
		pgNativeQuery.append(
				" ) ) MST GROUP BY CMPCOD, FLTCARIDR,FLTNUM,CONNUM,FLTSEQNUM,SEGSERNUM,CONNUM,ULDNUM,POU,ASGPRT,DSTCOD,CONTYP,CONJRNIDR,POACOD,ACTULDWGT,LOCCOD,ULDFRMCARCOD,LEGSERNUM ) ");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		log.info("" + "FINAL query " + " " + pgNativeQuery.toString());
		return pgNativeQuery.getPage(mailArrivalFilterVO.getPageNumber());
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findArrivedMailbagsForInbound(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO) Added by 			: A-8164 on 29-Dec-2018 Used for 	: Parameters	:	@param mailArrivalFilterVO Parameters	:	@return Parameters	:	@throws SystemException
	 */
	public Page<MailbagVO> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO) {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findArrivedMailbagsForInbound" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_INBOUND_MAILBAG_DETAILS);
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		masterQuery.append(query);
		if (mailArrivalFilterVO.getDefaultPageSize() == 0) {
			mailArrivalFilterVO.setDefaultPageSize(10);
		}
		PageableNativeQuery<MailbagVO> pgNativeQuery = new PageableNativeQuery<MailbagVO>(
				mailArrivalFilterVO.getDefaultPageSize(), -1, masterQuery.toString(),
				new InboundListMailbagDetailsMultiMapper(), PersistenceController.getEntityManager().currentSession());
		int index = 0;
		for (int i = 0; i < 1; i++) {
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCompanyCode());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCarrierId());
			pgNativeQuery.setParameter(++index, String.valueOf(mailArrivalFilterVO.getFlightNumber()));
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getFlightSequenceNumber());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPou());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getContainerNumber());
		}
		if (mailArrivalFilterVO.getPol() != null && mailArrivalFilterVO.getPol().trim().length() > 0) {
			pgNativeQuery.append(" AND FLT.POL =  ? ");
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPol());
		}
		if (mailArrivalFilterVO.getAdditionalFilter() != null) {
			index = populateAdditionalFilter(pgNativeQuery, index, mailArrivalFilterVO.getAdditionalFilter());
		}
		pgNativeQuery.append(" ORDER BY CONNUM, DSN,ORGEXGOFC,DSTEXGOFC,MALCLS,MALSUBCLS,MALCTGCOD,YER,MALSTA ) ");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(mailArrivalFilterVO.getPageNumber());
	}

	private int populateAdditionalFilter(PageableNativeQuery<MailbagVO> pageQuery, int index,
										 MailbagEnquiryFilterVO mailbagFilters) {
		if (mailbagFilters.getMailbagId() != null && mailbagFilters.getMailbagId().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MALIDR = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMailbagId());
		}
		if (mailbagFilters.getOriginAirportCode() != null
				&& mailbagFilters.getOriginAirportCode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.ORGCOD = ?");
			pageQuery.setParameter(++index, mailbagFilters.getOriginAirportCode());
		}
		if (mailbagFilters.getDestinationAirportCode() != null
				&& mailbagFilters.getDestinationAirportCode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.DSTCOD = ? ");
			pageQuery.setParameter(++index, mailbagFilters.getDestinationAirportCode());
		}
		if (mailbagFilters.getMailCategoryCode() != null && mailbagFilters.getMailCategoryCode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MALCTG = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMailCategoryCode());
		}
		if (mailbagFilters.getMailSubclass() != null && mailbagFilters.getMailSubclass().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MALSUBCLS = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMailSubclass());
		}
		if (mailbagFilters.getDespatchSerialNumber() != null
				&& mailbagFilters.getDespatchSerialNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.DSN = ?");
			pageQuery.setParameter(++index, mailbagFilters.getDespatchSerialNumber());
		}
		if (mailbagFilters.getReceptacleSerialNumber() != null
				&& mailbagFilters.getReceptacleSerialNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.RSN = ?");
			pageQuery.setParameter(++index, mailbagFilters.getReceptacleSerialNumber());
		}
		if (mailbagFilters.getPacode() != null && mailbagFilters.getPacode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.POACOD = ?");
			pageQuery.setParameter(++index, mailbagFilters.getPacode());
		}
		if (mailbagFilters.getConsigmentNumber() != null && mailbagFilters.getConsigmentNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.CSGDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagFilters.getConsigmentNumber());
		}
		if (mailbagFilters.getReqDeliveryTime() != null) {
			pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALMST.REQDLVTIM),'YYYYMMDD')) = ? ");
			pageQuery.setParameter(++index,
					mailbagFilters.getReqDeliveryTime().toString().replace("-", ""));
		}
		if (mailbagFilters.getMasterDocumentNumber() != null
				&& mailbagFilters.getMasterDocumentNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMasterDocumentNumber());
		}
		if (mailbagFilters.getShipmentPrefix() != null && mailbagFilters.getShipmentPrefix().trim().length() > 0) {
			pageQuery.append(" AND MALMST.SHPPFX = ?");
			pageQuery.setParameter(++index, mailbagFilters.getShipmentPrefix());
		}
		if (mailbagFilters.getCurrentStatus() != null && mailbagFilters.getCurrentStatus().trim().length() > 0) {
			if (MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagFilters.getCurrentStatus())) {
				pageQuery.append("AND (MALMST.MALSTA = ? AND (MAL.ARRSTA='N' OR MAL.ARRSTA IS NULL)) ");
				pageQuery.setParameter(++index, mailbagFilters.getCurrentStatus());
			} else if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagFilters.getCurrentStatus())) {
				pageQuery.append("AND MAL.ARRSTA='Y' ");
			} else {
				pageQuery.append(" AND MALMST.MALSTA = ?");
				pageQuery.setParameter(++index, mailbagFilters.getCurrentStatus());
			}
		}
		if ("Y".equals(mailbagFilters.getCarditPresent())) {
			pageQuery.append(" AND CSGMST.CSGDOCNUM IS NOT NULL ");
		}
		return index;
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findArrivedDsnsForInbound(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO) Added by 			: A-8164 on 29-Dec-2018 Used for 	: Parameters	:	@param mailArrivalFilterVO Parameters	:	@return
	 */
	public Page<DSNVO> findArrivedDsnsForInbound(MailArrivalFilterVO mailArrivalFilterVO) {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findArrivedDsnsForInbound" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_INBOUND_DSN_DETAILS);
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		masterQuery.append(query);
		if (mailArrivalFilterVO.getDefaultPageSize() == 0) {
			mailArrivalFilterVO.setDefaultPageSize(25);
		}
		PageableNativeQuery<DSNVO> pgNativeQuery = new PageableNativeQuery<DSNVO>(
				mailArrivalFilterVO.getDefaultPageSize(), -1, masterQuery.toString(),
				new InboundListDsnDetailsMultiMapper(),PersistenceController.getEntityManager().currentSession());
		int index = 0;
		for (int i = 0; i < 1; i++) {
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCompanyCode());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCarrierId());
			pgNativeQuery.setParameter(++index, String.valueOf(mailArrivalFilterVO.getFlightNumber()));
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getFlightSequenceNumber());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPou());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getContainerNumber());
		}
		if (mailArrivalFilterVO.getPol() != null && mailArrivalFilterVO.getPol().trim().length() > 0) {
			pgNativeQuery.append(" AND FLT.POL =  ? ");
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPol());
		}
		if (mailArrivalFilterVO.getAdditionalFilter() != null) {
			index = populateMailbagDSNAdditionalFilters(pgNativeQuery, mailArrivalFilterVO.getAdditionalFilter(),
					index);
		}
		pgNativeQuery.append(" )MST GROUP BY CMPCOD,DSN,ORGEXGOFC, DSTEXGOFC,MALSUBCLS,MALCTGCOD,YER,RMK,CSGDOCNUM )");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(mailArrivalFilterVO.getPageNumber());
	}

	private int populateMailbagDSNAdditionalFilters(PageableNativeQuery<DSNVO> pageQuery,
													MailbagEnquiryFilterVO mailbagFilters, int index) {
		if (mailbagFilters.getOoe() != null && mailbagFilters.getOoe().trim().length() > 0) {
			pageQuery.append(" AND MALMST.ORGEXGOFC = ?");
			pageQuery.setParameter(++index, mailbagFilters.getOoe());
		}
		if (mailbagFilters.getDestinationAirportCode() != null
				&& mailbagFilters.getDestinationAirportCode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.DSTEXGOFC = ? ");
			pageQuery.setParameter(++index, mailbagFilters.getDestinationAirportCode());
		}
		if (mailbagFilters.getMailCategoryCode() != null && mailbagFilters.getMailCategoryCode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MALCTG = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMailCategoryCode());
		}
		if (mailbagFilters.getMailSubclass() != null && mailbagFilters.getMailSubclass().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MALSUBCLS = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMailSubclass());
		}
		if (mailbagFilters.getDespatchSerialNumber() != null
				&& mailbagFilters.getDespatchSerialNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.DSN = ?");
			pageQuery.setParameter(++index, mailbagFilters.getDespatchSerialNumber());
		}
		if (mailbagFilters.getPacode() != null && mailbagFilters.getPacode().trim().length() > 0) {
			pageQuery.append(" AND MALMST.POACOD = ?");
			pageQuery.setParameter(++index, mailbagFilters.getPacode());
		}
		if (mailbagFilters.getConsigmentNumber() != null && mailbagFilters.getConsigmentNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.CSGDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagFilters.getConsigmentNumber());
		}
		if (mailbagFilters.getMasterDocumentNumber() != null
				&& mailbagFilters.getMasterDocumentNumber().trim().length() > 0) {
			pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMasterDocumentNumber());
		}
		if (mailbagFilters.getShipmentPrefix() != null && mailbagFilters.getShipmentPrefix().trim().length() > 0) {
			pageQuery.append(" AND MALMST.SHPPFX = ?");
			pageQuery.setParameter(++index, mailbagFilters.getShipmentPrefix());
		}
		return index;
	}

	/**
	 * @author A-7929
	 */
	public Page<ContainerVO> findAcceptedContainersForOffLoad(OffloadFilterVO offloadFilterVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findAcceptedContainersForOffLoad" + " Entering");
		int pageSize = offloadFilterVO.getDefaultPageSize();
		int totalRecords = offloadFilterVO.getTotalRecords();
		StringBuilder rankQuery = new StringBuilder().append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		Query offloadContainerquery = getQueryManager().createNamedNativeQuery(FIND_OFFLOAD_DETAILS);
		rankQuery.append(offloadContainerquery);
		PageableNativeQuery<ContainerVO> query = new OffloadContainerUXFilterQuery(pageSize, totalRecords,
				new OffloadContainerMultiMapper(), rankQuery.toString(), offloadFilterVO, offloadContainerquery);
		query.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		Page<ContainerVO> containerVOs = query.getPage(offloadFilterVO.getPageNumber());
		if (containerVOs != null && containerVOs.size() > 0) {
			for (ContainerVO containerVO : containerVOs) {
				containerVO.setFlightDate(offloadFilterVO.getFlightDate());
			}
		}
		return containerVOs;
	}

	/**
	 * @author A-8061
	 */
	public Boolean isMailAsAwb(MailbagVO mailbagVO) {
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAIL_BOOKING_COUNT);
		query.setParameter(++index, mailbagVO.getCompanyCode());
		query.setParameter(++index, mailbagVO.getMailSequenceNumber());
		return (query.getSingleResult(getLongMapper("MALBKG")) > 0 ? true : false);
	}

	@Override
	public MailbagVO findLatestFlightDetailsOfMailbag(MailbagVO mailbagVO) {
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_LATEST_FLIGHT_DETAILS_OF_MAILBAG);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getMailSequenceNumber());
		query.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ACCEPTED);
		query.setParameter(++idx, MailConstantsVO.MAIL_STATUS_TRANSFERRED);
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		return query.getSingleResult(new MailbagMapper());
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations. MailTrackingDefaultsDAO#listCarditDsnDetails(com.ibsplc. icargo.business.mail.operations.vo.DSNEnquiryFilterVO) Added by 			: 	A-8164 on 04-Sep-2019 Used for 			:	List Cardit DSN Details Parameters			:	@param dsnEnquiryFilterVO Parameters			:	@return
	 * @throws SystemException
	 */
	public Page<DSNVO> listCarditDsnDetails(DSNEnquiryFilterVO dsnEnquiryFilterVO) {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "listCarditDsnDetails" + " Entering");
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_CARDIT_DSN_DETAILS);
		masterQuery.append(queryString);
		PageableNativeQuery<DSNVO> pgNativeQuery = new PageableNativeQuery<DSNVO>(dsnEnquiryFilterVO.getPageSize(), -1,
				masterQuery.toString(), new CarditDsnEnquiryMapper(),PersistenceController.getEntityManager().currentSession());
		int index = 0;
		if (dsnEnquiryFilterVO.getFlightType() != null && dsnEnquiryFilterVO.getFlightType().trim().length() > 0) {
			if (MailConstantsVO.FLIGHT_TYP_OPR.equals(dsnEnquiryFilterVO.getFlightType()))
				pgNativeQuery.append("      INNER JOIN MALFLT ASGFLT " + "ON ASGFLT.CMPCOD  = MALMST.CMPCOD "
						+ "AND ASGFLT.FLTCARIDR = MALMST.FLTCARIDR " + "AND ASGFLT.FLTNUM    = MALMST.FLTNUM "
						+ "AND ASGFLT.FLTSEQNUM = MALMST.FLTSEQNUM");
		}
		pgNativeQuery.append(" WHERE CSGMST.CMPCOD  = ?");
		pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getCompanyCode());
		if (dsnEnquiryFilterVO.getConsignmentNumber() != null
				&& dsnEnquiryFilterVO.getConsignmentNumber().trim().length() > 0) {
			pgNativeQuery.append("AND CSGMST.CSGDOCNUM = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getConsignmentNumber());
		}
		if (dsnEnquiryFilterVO.getConsignmentDate() != null) {
			pgNativeQuery.append("AND TRUNC(CSGMST.CSGDAT) = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getConsignmentDate());
		}
		if (dsnEnquiryFilterVO.getOoe() != null && dsnEnquiryFilterVO.getOoe().trim().length() > 0) {
			pgNativeQuery.append("AND MALMST.ORGEXGOFC  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getOoe());
		}
		if (dsnEnquiryFilterVO.getDoe() != null && dsnEnquiryFilterVO.getDoe().trim().length() > 0) {
			pgNativeQuery.append("AND MALMST.DSTEXGOFC  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getDoe());
		}
		if (dsnEnquiryFilterVO.getMailCategoryCode() != null
				&& dsnEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
			pgNativeQuery.append("AND MALMST.MALCTG  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getMailCategoryCode());
		}
		if (dsnEnquiryFilterVO.getYear() != 0) {
			pgNativeQuery.append("AND MALMST.YER  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getYear());
		}
		if (dsnEnquiryFilterVO.getDsn() != null && dsnEnquiryFilterVO.getDsn().trim().length() > 0) {
			pgNativeQuery.append("AND MALMST.DSN  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getDsn());
		}
		if (dsnEnquiryFilterVO.getMailSubClass() != null && dsnEnquiryFilterVO.getMailSubClass().trim().length() > 0) {
			pgNativeQuery.append("AND MALMST.MALSUBCLS  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getMailSubClass());
		}
		if (dsnEnquiryFilterVO.getMailSubClass() != null && dsnEnquiryFilterVO.getMailSubClass().trim().length() > 0) {
			pgNativeQuery.append("AND MALMST.MALSUBCLS  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getMailSubClass());
		}
		if (dsnEnquiryFilterVO.getFromDate() != null && dsnEnquiryFilterVO.getToDate() != null) {
			pgNativeQuery.append("AND TO_NUMBER(TO_CHAR(trunc(CSGMST.CSGDAT),'YYYYMMDD')) BETWEEN ? AND ?");
			pgNativeQuery.setParameter(++index,
					dsnEnquiryFilterVO.getFromDate().toString().replace("-", ""));
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getToDate().toString().replace("-", ""));
		}
		if (dsnEnquiryFilterVO.getAwbAttached() != null && dsnEnquiryFilterVO.getAwbAttached().trim().length() > 0) {
			if (MailConstantsVO.FLAG_YES.equals(dsnEnquiryFilterVO.getAwbAttached())) {
				pgNativeQuery.append("AND MALMST.MSTDOCNUM  IS NOT NULL");
			} else {
				pgNativeQuery.append("AND MALMST.MSTDOCNUM  IS NULL");
			}
		}
		if (MailConstantsVO.FLIGHT_TYP_OPR.equals(dsnEnquiryFilterVO.getFlightType())) {
			if (dsnEnquiryFilterVO.getCarrierCode() != null
					&& dsnEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
				pgNativeQuery.append("AND ASGFLT.FLTCARCOD = ?");
				pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getCarrierCode());
			}
			if (dsnEnquiryFilterVO.getFlightNumber() != null
					&& dsnEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
				pgNativeQuery.append("AND ASGFLT.FLTNUM = ?");
				pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getFlightNumber());
			}
			if (dsnEnquiryFilterVO.getFlightDate() != null) {
				pgNativeQuery.append("AND TRUNC(ASGFLT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getFlightDate().toString());
			}
		} else {
			if (dsnEnquiryFilterVO.getCarrierCode() != null
					&& dsnEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
				pgNativeQuery.append("AND TRT.FLTCARCOD = ?");
				pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getCarrierCode());
			}
			if (dsnEnquiryFilterVO.getFlightNumber() != null
					&& dsnEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
				pgNativeQuery.append("AND TRT.FLTNUM = ?");
				pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getFlightNumber());
			}
			if (dsnEnquiryFilterVO.getFlightDate() != null) {
				pgNativeQuery.append("AND TRUNC(TRT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getFlightDate().toString());
			}
		}
		if (dsnEnquiryFilterVO.getAirportCode() != null && dsnEnquiryFilterVO.getAirportCode().trim().length() > 0) {
			pgNativeQuery.append("AND TRT.POL  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getAirportCode());
		}
		if (dsnEnquiryFilterVO.getContainerNumber() != null
				&& dsnEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
			pgNativeQuery.append("AND CSGDTL.ULDNUM  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getContainerNumber());
		}
		if (dsnEnquiryFilterVO.getShipmentPrefix() != null
				&& dsnEnquiryFilterVO.getShipmentPrefix().trim().length() > 0) {
			pgNativeQuery.append("AND MALMST.SHPPFX  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getShipmentPrefix());
		}
		if (dsnEnquiryFilterVO.getPaCode() != null && dsnEnquiryFilterVO.getPaCode().trim().length() > 0) {
			pgNativeQuery.append("AND MALMST.POACOD  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getPaCode());
		}
		if (dsnEnquiryFilterVO.getDocumentNumber() != null
				&& dsnEnquiryFilterVO.getDocumentNumber().trim().length() > 0) {
			pgNativeQuery.append("AND MALMST.MSTDOCNUM  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getDocumentNumber());
		}
		if (dsnEnquiryFilterVO.getRdt() != null) {
			String rqdDlvTime = dsnEnquiryFilterVO.getRdt().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

			if (rqdDlvTime != null) {
				if (rqdDlvTime.contains("0000")) {
					pgNativeQuery.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				} else {
					pgNativeQuery.append(" AND MALMST.REQDLVTIM = ?");
				}
			}
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getRdt());
		}
		if (dsnEnquiryFilterVO.getStatus() != null && dsnEnquiryFilterVO.getStatus().trim().length() > 0) {
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(dsnEnquiryFilterVO.getStatus())) {
				pgNativeQuery.append(" AND MALMST.MALSTA  NOT IN('NEW','BKD','CAN')");
			} else if (MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED.equals(dsnEnquiryFilterVO.getStatus())) {
				pgNativeQuery.append(" AND MALMST.MALSTA IN('NEW','BKD','CAN')");
			}
		}
		pgNativeQuery
				.append(" )GROUP BY FLTNUM , FLTDAT, CARCOD , ORGEXGOFF, DSTEXGOFF, DSPYER , DSPSRLNUM , MALCTGCOD ,"
						+ "MALSUBCLS , CMPCOD , SHPPFX , MSTDOCNUM , CSGDOCNUM , CSGDAT , POACOD , CONNUM , REQDLVTIM , ACPSTA ");
		pgNativeQuery.append(" ORDER BY CSGDOCNUM, CSGDAT, CONNUM, DSPSRLNUM");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(dsnEnquiryFilterVO.getPageNumber());
	}

	/**
	 * @author A-5526
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<RunnerFlightVO> findRunnerFlights(RunnerFlightFilterVO runnerFlightFilterVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findOutboundRunnerFlights" + " Entering");
		String qry = null;
		String additionalQuery = null;
		PageableNativeQuery<RunnerFlightVO> pgqry = null;
		String listType = null;
		if (RunnerFlightVO.RUN_DIRECTION_OUTBOUND.equals(runnerFlightFilterVO.getRunDirection())) {
			qry = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_FIND_OUTBOUNDRUNNERFLIGHTS);
			listType = RunnerFlightVO.RUN_DIRECTION_OUTBOUND;
		} else if (RunnerFlightVO.RUN_DIRECTION_INBOUND.equals(runnerFlightFilterVO.getRunDirection())) {
			if (RunnerFlightVO.LISTTYPE_INBOUND.equals(runnerFlightFilterVO.getInboundListType())) {
				qry = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_FIND_INBOUNDRUNNERFLIGHTS);
				listType = RunnerFlightVO.LISTTYPE_INBOUND;
			} else if (RunnerFlightVO.LISTTYPE_REFUSAL.equals(runnerFlightFilterVO.getInboundListType())) {
				qry = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_FIND_REFUSALRUNNERFLIGHTS);
				additionalQuery = getQueryManager()
						.getNamedNativeQueryString(MAIL_OPERATIONS_FIND_REFUSALRUNNERFLIGHTS_PART2);
				listType = RunnerFlightVO.LISTTYPE_REFUSAL;
			}
		}
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(
				"SELECT RESULT_TABLE.* , dense_rank() over( order by FLTDAT,FLTCARIDR,FLTNUM,FLTSEQNUM,FLTSEQNUM,CMPCOD desc) AS RANK FROM ( ");
		rankQuery.append(qry);
		pgqry = new ListRunnerFlightFilterQuery(runnerFlightFilterVO.getPageSize(),
				runnerFlightFilterVO.getTotalRecordCount(), isOracleDataSource(), runnerFlightFilterVO,
				rankQuery.toString(), additionalQuery, new RunnerFlightMultiMapper(listType));
		log.debug(CLASS_NAME + " : " + "findOutboundRunnerFlights" + " Exiting");
		return pgqry.getPage(runnerFlightFilterVO.getPageNumber());
	}

	/**
	 * @author A-5526
	 * @return ContainerVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ContainerVO findContainerDetails(RunnerFlightVO runnerFlightVO, RunnerFlightULDVO runnerFlightULDVO)
			throws PersistenceException {
		Query qry = null;
		int indexForSum = 0;
		ContainerVO containerVO = null;
		if (runnerFlightVO.getFlightSequenceNumber() > 0) {
			qry = getQueryManager().createNamedNativeQuery(FIND_BAGCOUNT_FORFLIGHT);
			qry.setParameter(++indexForSum, runnerFlightVO.getCompanyCode());
			qry.setParameter(++indexForSum, runnerFlightULDVO.getUldNumber());
			qry.setParameter(++indexForSum, runnerFlightVO.getCarrierId());
			qry.setParameter(++indexForSum, runnerFlightVO.getFlightNumber());
			qry.setParameter(++indexForSum, runnerFlightVO.getFlightSequenceNumber());
			qry.setParameter(++indexForSum,
					runnerFlightVO.getLegSerialNumber() != null && !runnerFlightVO.getLegSerialNumber().isEmpty()
							? Integer.parseInt(runnerFlightVO.getLegSerialNumber())
							: 0);
		} else {
			qry = getQueryManager().createNamedNativeQuery(FIND_BAGCOUNT_FORDESTINATION);
			qry.setParameter(++indexForSum, runnerFlightVO.getCompanyCode());
			qry.setParameter(++indexForSum, runnerFlightVO.getAirportCode());
			qry.setParameter(++indexForSum, runnerFlightVO.getCarrierId());
			qry.setParameter(++indexForSum, runnerFlightULDVO.getUldNumber());
		}
		containerVO = qry.getSingleResult(new BagCountMapper());
		return containerVO;
	}
	/**
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureApplicableMails(ForceMajeureRequestFilterVO filterVO,
																	   int pageNumber) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "listForceMajeureApplicableMails" + " Entering");
		PageableNativeQuery<ForceMajeureRequestVO> pgqry = null;
		int pageSize = filterVO.getDefaultPageSize();
		int totalRecords = filterVO.getTotalRecords();
		boolean isFltExist = false;
		String sortField = "";
		StringBuilder rankQuery = new StringBuilder(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		Query qry = null;
		int index = 0;
		if (MailConstantsVO.FLIGHT_TYP_OPR.equals(filterVO.getSource())) {
			qry = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST_MAILS_FOR_OPS);
			rankQuery.append(qry);
			pgqry = new PageableNativeQuery<ForceMajeureRequestVO>(pageSize, totalRecords, rankQuery.toString(),
					new ForceMajeureRequestMapper(),PersistenceController.getEntityManager().currentSession());
			pgqry.setParameter(++index, filterVO.getCompanyCode());
			if (filterVO.getPoaCode() != null && filterVO.getPoaCode().trim().length() > 0) {
				pgqry.append(" AND MST.POACOD = ? ");
				pgqry.setParameter(++index, filterVO.getPoaCode());
			}
			if (filterVO.getOrginAirport() != null && filterVO.getOrginAirport().trim().length() > 0) {
				pgqry.append(" AND MST.ORGCOD = ? ");
				pgqry.setParameter(++index, filterVO.getOrginAirport());
			}
			if (filterVO.getDestinationAirport() != null && filterVO.getDestinationAirport().trim().length() > 0) {
				pgqry.append(" AND MST.DSTCOD = ? ");
				pgqry.setParameter(++index, filterVO.getDestinationAirport());
			}
			if (filterVO.getCarrierID() > 0) {
				pgqry.append(" AND HIS.FLTCARIDR = ? ");
				pgqry.setParameter(++index, filterVO.getCarrierID());
			}
			if (filterVO.getFlightNumber() != null && filterVO.getFlightDate() != null
					&& filterVO.getFlightNumber().trim().length() > 0) {
				isFltExist = true;
				pgqry.append(" AND HIS.FLTNUM = ? ");
				String fltnumber = filterVO.getFlightNumber().substring(3, filterVO.getFlightNumber().length());
				pgqry.setParameter(++index, fltnumber);
				pgqry.append(" AND HIS.FLTDAT = ? ");
				pgqry.setParameter(++index, filterVO.getFlightDate());
			}
			if (filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0) {
				pgqry.append(" AND ( HIS.SCNPRT IN (");
				String[] airports = filterVO.getAffectedAirport().split(",");
				StringBuilder builder = new StringBuilder("'");
				for (String airport : airports) {
					builder.append(airport).append("','");
				}
				pgqry.append(builder.toString().substring(0, builder.length() - 2).trim()).append(")) ");
				if (!isFltExist) {
					pgqry.append(
							" AND Trunc(HIS.SCNDAT) BETWEEN to_date(?, 'dd-MON-yyyy') AND to_date(?, 'dd-MON-yyyy') ");
					pgqry.setParameter(++index, filterVO.getFromDate());
					pgqry.setParameter(++index, filterVO.getToDate());
				}
				pgqry.append(" AND HIS.MALHISIDR = (SELECT MAX(MALHISIDR) FROM MALHIS ");
				pgqry.append(" WHERE CMPCOD = MST.CMPCOD AND MALSEQNUM = MST.MALSEQNUM AND SCNPRT = HIS.SCNPRT ) ");
				if (isFltExist) {
					pgqry.append(" ) ) ");
				} else {
					pgqry.append(
							" AND TRUNC(his.SCNDAT) BETWEEN to_date(?, 'dd-MON-yyyy') AND to_date(?, 'dd-MON-yyyy')  ");
					pgqry.setParameter(++index, filterVO.getFromDate());
					pgqry.setParameter(++index, filterVO.getToDate());
				}
			}
			if (filterVO.getViaPoint() != null && filterVO.getViaPoint().trim().length() > 0) {
				pgqry.append(" AND ( ( HIS.SCNPRT = ? ");
				pgqry.setParameter(++index, filterVO.getViaPoint());
				if (!isFltExist) {
					pgqry.append(
							" AND TRUNC(his.SCNDAT) BETWEEN to_date(?, 'dd-MON-yyyy') AND to_date(?, 'dd-MON-yyyy') ");
					pgqry.setParameter(++index, filterVO.getFromDate());
					pgqry.setParameter(++index, filterVO.getToDate());
				}
				pgqry.append(" AND HIS.MALHISIDR = ( SELECT MAX(MALHISIDR) FROM MALHIS ");
				pgqry.append(" WHERE CMPCOD = MST.CMPCOD AND MALSEQNUM = MST.MALSEQNUM AND SCNPRT = HIS.SCNPRT ");
				if (!isFltExist) {
					pgqry.append(
							" AND TRUNC(HIS.SCNDAT) BETWEEN  to_date(?, 'dd-MON-yyyy') AND  to_date(?, 'dd-MON-yyyy') ");
					pgqry.setParameter(++index, filterVO.getFromDate());
					pgqry.setParameter(++index, filterVO.getToDate());
				} else {
					pgqry.append(" ) ");
				}
				pgqry.append(" AND MST.ORGCOD  <>  ?  AND  MST.DSTCOD <> ? ) ");
				pgqry.append(
						" OR ( MST.SCNPRT = ? AND  MST.ORGCOD  <>  ?  AND  MST.DSTCOD <> ? AND MST.MALSTA IN( 'ARR','OFL') ");
				pgqry.append(
						" AND HIS.MALHISIDR = (SELECT MAX(MALHISIDR)  FROM MALHIS   WHERE CMPCOD  = MST.CMPCOD   AND MALSEQNUM = MST.MALSEQNUM ");
				pgqry.append("	AND SCNPRT = MST.SCNPRT  AND MALSTA = MST.MALSTA  ))");
				pgqry.append(
						" OR ( INSTR(SUBSTR(FLTMST.FLTROU,INSTR(FLTMST.FLTROU,MST.ORGCOD,1)+length(MST.ORGCOD)-length(FLTMST.FLTROU)),? ) > 0 ");
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.append(" AND HIS.MALHISIDR = ( SELECT MAX(MALHISIDR) FROM MALHIS ");
				pgqry.append(" WHERE CMPCOD = MST.CMPCOD AND MALSEQNUM = MST.MALSEQNUM ) AND MST.DSTCOD  <>  ?   ) ");
				pgqry.append(
						" AND  TRUNC(FLTLEG.STA) BETWEEN to_date(?, 'dd-MON-yyyy') AND to_date(?, 'dd-MON-yyyy') ");
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getFromDate());
				pgqry.setParameter(++index, filterVO.getToDate());
			}
		} else {
			qry = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST_MAILS_FOR_CARDIT);
			rankQuery.append(qry);
			pgqry = new PageableNativeQuery<ForceMajeureRequestVO>(pageSize, totalRecords, rankQuery.toString(),
					new ForceMajeureRequestMapper(),PersistenceController.getEntityManager().currentSession());
			pgqry.setParameter(++index, filterVO.getCompanyCode());
			if (filterVO.getFlightNumber() != null && filterVO.getFlightNumber().trim().length() > 0) {
				String fltnumber = "";
				if (filterVO.getFlightNumber() != null && filterVO.getFlightNumber().length() > 0) {
					fltnumber = filterVO.getFlightNumber().substring(3, filterVO.getFlightNumber().length());
				}
				pgqry.append(" AND CSGRTG.FLTNUM = ? ");
				pgqry.setParameter(++index, fltnumber);
			} else {
				if (filterVO.getFromDate() != null) {
					pgqry.append(" AND ( TO_CHAR(TRUNC(CSGRTG.FLTDAT),'YYYYMMDDHHMM') >= ?  ");
					index++;
					pgqry.setParameter(index, filterVO.getFromDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
					pgqry.append(" OR  TO_CHAR(TRUNC(CSGRTG.LEGSTA),'YYYYMMDDHHMM') >= ?  )");
					index++;
					pgqry.setParameter(index, filterVO.getFromDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				}
				if (filterVO.getToDate() != null) {
					pgqry.append(" AND ( TO_CHAR(TRUNC(CSGRTG.FLTDAT),'YYYYMMDDHHMM') <= ?  ");
					index++;
					pgqry.setParameter(index, filterVO.getToDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
					pgqry.append(" OR  TO_CHAR(TRUNC(CSGRTG.LEGSTA),'YYYYMMDDHHMM') <= ?  )");
					index++;
					pgqry.setParameter(index, filterVO.getToDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				}
			}
			if (filterVO.getCarrierID() > 0) {
				pgqry.append(" AND CSGRTG.FLTCARIDR = ? ");
				pgqry.setParameter(++index, filterVO.getCarrierID());
			}
			if (filterVO.getFlightDate() != null) {
				pgqry.append("AND TO_NUMBER(TO_CHAR(TRUNC(CSGRTG.FLTDAT),'YYYYMMDD')) = ? ");
				pgqry.setParameter(++index,
						Integer.parseInt(filterVO.getFlightDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
			}
			if (filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0) {
				pgqry.append(" AND ( CSGRTG.POL IN (");
				String[] airports = filterVO.getAffectedAirport().split(",");
				StringBuilder builder = new StringBuilder("'");
				for (String airport : airports) {
					builder.append(airport).append("','");
				}
				pgqry.append(builder.toString().substring(0, builder.length() - 2).trim()).append(") ");
				pgqry.append(" OR CSGRTG.POU IN (")
						.append(builder.toString().substring(0, builder.length() - 2).trim());
				pgqry.append(" ) )");
			}
			if (filterVO.getOrginAirport() != null && filterVO.getOrginAirport().trim().length() > 0) {
				pgqry.append(" AND MST.ORGCOD = ? ");
				pgqry.setParameter(++index, filterVO.getOrginAirport());
			}
			if (filterVO.getDestinationAirport() != null && filterVO.getDestinationAirport().trim().length() > 0) {
				pgqry.append(" AND MST.DSTCOD = ? ");
				pgqry.setParameter(++index, filterVO.getDestinationAirport());
			}
			if (filterVO.getViaPoint() != null && filterVO.getViaPoint().trim().length() > 0) {
				pgqry.append(" AND ( MST.ORGCOD  <>  ?  AND  MST.DSTCOD <> ? AND CSGRTG.POU = ? ) ");
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
			}
			if (filterVO.getPoaCode() != null && filterVO.getPoaCode().trim().length() > 0) {
				pgqry.append(" AND MST.POACOD = ? ");
				pgqry.setParameter(++index, filterVO.getPoaCode());
			}
			if ((filterVO.getScanType() != null) && (filterVO.getScanType().trim().length() > 0)
					&& (!filterVO.getScanType().contains("ALL"))) {
				if (filterVO.getScanType().contains("RCV") && (!filterVO.getScanType().contains("DLV"))
						&& (!filterVO.getScanType().contains("LAT"))) {
					pgqry.append(" AND CSGRTG.POL =MST.ORGCOD ");
					if (filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0) {
						pgqry.append(" AND  MST.ORGCOD IN (");
						String[] airports = filterVO.getAffectedAirport().split(",");
						StringBuilder builder = new StringBuilder("'");
						for (String airport : airports) {
							builder.append(airport).append("','");
						}
						pgqry.append(builder.toString().substring(0, builder.length() - 2).trim()).append(") ");
					}
				} else if (filterVO.getScanType().contains("LOD") && (!filterVO.getScanType().contains("DLV"))
						&& (!filterVO.getScanType().contains("LAT"))) {
					pgqry.append(" AND CSGRTG.POL <>MST.DSTCOD ");
					if (filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0) {
						pgqry.append(" AND  CSGRTG.POL IN (");
						String[] airports = filterVO.getAffectedAirport().split(",");
						StringBuilder builder = new StringBuilder("'");
						for (String airport : airports) {
							builder.append(airport).append("','");
						}
						pgqry.append(builder.toString().substring(0, builder.length() - 2).trim()).append(") ");
					}
				} else if (((filterVO.getScanType().contains("DLV")) || (filterVO.getScanType().contains("LAT")))
						&& (!filterVO.getScanType().contains("LOD")) && (!filterVO.getScanType().contains("RCV"))) {
					pgqry.append(" AND CSGRTG.POU =MST.DSTCOD ");
					if (filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0) {
						pgqry.append(" AND  MST.DSTCOD IN (");
						String[] airports = filterVO.getAffectedAirport().split(",");
						StringBuilder builder = new StringBuilder("'");
						for (String airport : airports) {
							builder.append(airport).append("','");
						}
						pgqry.append(builder.toString().substring(0, builder.length() - 2).trim()).append(") ");
					}
				} else {
					pgqry.append(" AND CSGRTG.POL <> MST.ORGCOD ");
					pgqry.append(" AND CSGRTG.POU <> MST.DSTCOD ");
					if (filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0) {
						pgqry.append(" AND  MST.DSTCOD NOT IN (");
						String[] airports = filterVO.getAffectedAirport().split(",");
						StringBuilder builder = new StringBuilder("'");
						for (String airport : airports) {
							builder.append(airport).append("','");
						}
						pgqry.append(builder.toString().substring(0, builder.length() - 2).trim()).append(") ");
					}
					if (filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0) {
						pgqry.append(" AND  MST.ORGCOD NOT IN (");
						String[] airports = filterVO.getAffectedAirport().split(",");
						StringBuilder builder = new StringBuilder("'");
						for (String airport : airports) {
							builder.append(airport).append("','");
						}
						pgqry.append(builder.toString().substring(0, builder.length() - 2).trim()).append(") ");
					}
				}
			}
			pgqry.append(
					" group by MST.CMPCOD,MST.MALIDR,MST.MALSEQNUM,MST.SCNPRT,ARL.TWOAPHCOD,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.OPRSTA,MST.WGT,MST.ORGCOD,MST.DSTCOD,MST.CSGDOCNUM,MST.SCNUSR");
		}
		if (((filterVO.getSortingField() == null) || ("".equals(filterVO.getSortingField())))
				&& ((filterVO.getSortOrder() == null) || ("".equals(filterVO.getSortOrder())))) {
			pgqry.append(" ORDER BY MALIDR ASC ");
			pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		} else {
			if ("mailID".equals(filterVO.getSortingField()))
				sortField = "MALIDR";
			else if ("airportCode".equals(filterVO.getSortingField()))
				sortField = "SCNPRT";
			else if ("flightNumber".equals(filterVO.getSortingField()))
				sortField = "FLTNUM";
			else if ("flightDate".equals(filterVO.getSortingField()))
				sortField = "FLTDAT";
			else if ("type".equals(filterVO.getSortingField()))
				sortField = "OPRSTA";
			else if ("weight".equals(filterVO.getSortingField()))
				sortField = "WGT";
			else if ("originAirport".equals(filterVO.getSortingField()))
				sortField = "ORGARPCOD";
			else if ("destinationAirport".equals(filterVO.getSortingField()))
				sortField = "DSTARPCOD";
			else if ("consignmentDocNumber".equals(filterVO.getSortingField()))
				sortField = "CSGDOCNUM";
			else if ("lastUpdatedUser".equals(filterVO.getSortingField())) {
				sortField = "LSTUPDUSR";
			}
			if ("ASC".equals(filterVO.getSortOrder())) {
				pgqry.append("ORDER BY  " + sortField + " ASC ");
			} else if ("DESC".equals(filterVO.getSortOrder())) {
				pgqry.append("ORDER BY  " + sortField + " DESC ");
			}
			pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		return pgqry.getPage(pageNumber);
	}

	/**
	 */
	public String saveForceMajeureRequest(ForceMajeureRequestFilterVO filterVO) throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(CLASS_NAME + " : " + "saveForceMajeureRequest" + " Entering");
		String outPar = "";
		//ZonedDateTime dummyDate = localDateUtil.getLocalDate(null, false).setDate(MailConstantsVO.DUMMY_DATE_FOR_FMR);
		ZonedDateTime dummyDate = localDateUtil.getLocalDateTime(MailConstantsVO.DUMMY_DATE_FOR_FMR, null);
		Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(MAIL_OPERATIONS_SAVE_FORCE_MAJUERE);
		int index = 0;
		burstProcedure.setParameter(++index, filterVO.getCompanyCode());
		burstProcedure.setParameter(++index, filterVO.getOrginAirport());
		burstProcedure.setParameter(++index, filterVO.getDestinationAirport());
		burstProcedure.setParameter(++index, filterVO.getViaPoint());
		burstProcedure.setParameter(++index, filterVO.getAffectedAirport());
		burstProcedure.setParameter(++index, filterVO.getPoaCode());
		burstProcedure.setParameter(++index, filterVO.getCarrierID());
		String fltnumber = "";
		if (filterVO.getFlightNumber() != null && filterVO.getFlightNumber().length() > 0) {
			fltnumber = filterVO.getFlightNumber().substring(3, filterVO.getFlightNumber().length());
		}
		burstProcedure.setParameter(++index, fltnumber);
		if (filterVO.getFlightDate() != null)
			burstProcedure.setParameter(++index, filterVO.getFlightDate());
		else
			burstProcedure.setParameter(++index, dummyDate);
		if (filterVO.getFromDate() != null)
			burstProcedure.setParameter(++index, filterVO.getFromDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
		else
			burstProcedure.setParameter(++index, dummyDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
		if (filterVO.getToDate() != null)
			burstProcedure.setParameter(++index, filterVO.getToDate().format(DateTimeFormatter.ofPattern ("yyyyMMddHHmm")));
		else
			burstProcedure.setParameter(++index, dummyDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
		burstProcedure.setParameter(++index, filterVO.getFilterParameters());
		burstProcedure.setParameter(++index, filterVO.getReqRemarks());
		burstProcedure.setParameter(++index, filterVO.getSource());
		burstProcedure.setParameter(++index, filterVO.getLastUpdatedUser());
		burstProcedure.setParameter(++index, filterVO.getCurrentAirport());
		burstProcedure.setParameter(++index, filterVO.getScanType());
		burstProcedure.setOutParameter(++index, SqlType.STRING);
		burstProcedure.execute();
		outPar = (String) burstProcedure.getParameter(index);
		log.debug("" + "outParameter is " + " " + outPar);
		log.debug(CLASS_NAME + " : " + "saveForceMajeureRequest" + " Exiting");
		return outPar;
	}

	/**
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureDetails(ForceMajeureRequestFilterVO filterVO, int pageNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "listForceMajeureDetails" + " Entering");
		PageableNativeQuery<ForceMajeureRequestVO> pgqry = null;
		int pageSize = filterVO.getDefaultPageSize();
		int totalRecords = filterVO.getTotalRecords();
		StringBuilder rankQuery = new StringBuilder(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		String sortField = "";
		Query qry = null;
		int index = 0;
		qry = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST);
		rankQuery.append(qry);
		pgqry = new PageableNativeQuery<ForceMajeureRequestVO>(pageSize, totalRecords, rankQuery.toString(),
				new ForceMajeureRequestMapper(),PersistenceController.getEntityManager().currentSession());
		pgqry.setParameter(++index, filterVO.getCompanyCode());
		pgqry.setParameter(++index, filterVO.getForceMajeureID());
		if (filterVO.getAirportCode() != null && filterVO.getAirportCode().trim().length() > 0) {
			pgqry.append(" AND FORC.ARPCOD= ? ");
			pgqry.setParameter(++index, filterVO.getAirportCode());
		}
		if (filterVO.getFlightNumber() != null && filterVO.getFlightNumber().trim().length() > 0) {
			pgqry.append(" AND FORC.FLTNUM= ? ");
			pgqry.setParameter(++index, filterVO.getFlightNumber());
		}
		if (filterVO.getCarrierCode() != null && filterVO.getCarrierCode().trim().length() > 0) {
			pgqry.append(" AND FORC.FLTCARCOD= ? ");
			pgqry.setParameter(++index, filterVO.getCarrierCode());
		}
		if (filterVO.getFlightDate() != null) {
			pgqry.append(" AND TO_NUMBER(TO_CHAR(TRUNC(FORC.FLTDAT),'YYYYMMDD')) = ? ");
			pgqry.setParameter(++index, filterVO.getFlightDate().toString().replace("-", ""));
		}
		if (filterVO.getConsignmentNo() != null && filterVO.getConsignmentNo().trim().length() > 0) {
			pgqry.append(" AND FORC.CSGDOCNUM = ? ");
			pgqry.setParameter(++index, filterVO.getConsignmentNo());
		}
		if (filterVO.getMailbagId() != null && filterVO.getMailbagId().trim().length() > 0) {
			pgqry.append(" AND FORC.MALIDR = ? ");
			pgqry.setParameter(++index, filterVO.getMailbagId());
		}
		if (((filterVO.getSortingField() == null) || ("".equals(filterVO.getSortingField())))
				&& ((filterVO.getSortOrder() == null) || ("".equals(filterVO.getSortOrder())))) {
			pgqry.append("ORDER BY MALIDR ASC ");
			pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		} else {
			if ("mailID".equals(filterVO.getSortingField()))
				sortField = "MALIDR";
			else if ("airportCode".equals(filterVO.getSortingField()))
				sortField = "SCNPRT";
			else if ("flightNumber".equals(filterVO.getSortingField()))
				sortField = "FLTNUM";
			else if ("flightDate".equals(filterVO.getSortingField()))
				sortField = "FLTDAT";
			else if ("type".equals(filterVO.getSortingField()))
				sortField = "OPRSTA";
			else if ("weight".equals(filterVO.getSortingField()))
				sortField = "WGT";
			else if ("originAirport".equals(filterVO.getSortingField()))
				sortField = "ORGARPCOD";
			else if ("destinationAirport".equals(filterVO.getSortingField()))
				sortField = "DSTARPCOD";
			else if ("consignmentDocNumber".equals(filterVO.getSortingField()))
				sortField = "CSGDOCNUM";
			else if ("Forceid".equals(filterVO.getSortingField()))
				sortField = "FORMJRIDR";
			else if ("status".equals(filterVO.getSortingField()))
				sortField = "FORMJRSTA";
			else if ("lastUpdatedUser".equals(filterVO.getSortingField())) {
				sortField = "LSTUPDUSR";
			}
			if ("ASC".equals(filterVO.getSortOrder())) {
				pgqry.append("ORDER BY " + sortField + " ASC ");
			} else if ("DESC".equals(filterVO.getSortOrder())) {
				pgqry.append("ORDER BY  " + sortField + " DESC ");
			}
			pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		return pgqry.getPage(pageNumber);
	}

	/**
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureRequestIds(ForceMajeureRequestFilterVO filterVO, int pageNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "listForceMajeureRequestIds" + " Entering");
		PageableNativeQuery<ForceMajeureRequestVO> pgqry = null;
		int pageSize = filterVO.getDefaultPageSize();
		int totalRecords = filterVO.getTotalRecords();
		StringBuilder rankQuery = new StringBuilder(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		Query qry = null;
		int index = 0;
		qry = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_LIST_FORCE_MAJEURE_LOV);
		rankQuery.append(qry);
		pgqry = new PageableNativeQuery<ForceMajeureRequestVO>(pageSize, totalRecords, rankQuery.toString(),
				new ForceMajeureRequestMapper(),PersistenceController.getEntityManager().currentSession());
		pgqry.setParameter(++index, filterVO.getCompanyCode());
		if (filterVO.getForceMajeureID() != null && filterVO.getForceMajeureID().trim().length() > 0) {
			pgqry.append(" AND FORMJRIDR LIKE ?");
			pgqry.setParameter(++index, filterVO.getForceMajeureID());
		}
		if (filterVO.getFromDate() != null) {
			pgqry.append(" AND TO_NUMBER(TO_CHAR(REQDAT,'YYYYMMDD')) >= ? ");
			pgqry.setParameter(++index,
					Integer.parseInt(filterVO.getFromDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
		}
		if (filterVO.getToDate() != null) {
			pgqry.append(" AND TO_NUMBER(TO_CHAR(REQDAT,'YYYYMMDD')) <= ? ");
			pgqry.setParameter(++index,
					Integer.parseInt(filterVO.getToDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
		}
		pgqry.append("ORDER BY REQDAT DESC");
		pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgqry.getPage(pageNumber);
	}

	/**
	 */
	public String updateForceMajeureRequest(ForceMajeureRequestFilterVO requestVO) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "updateForceMajeureRequest" + " Entering");
		String outPar = "";
		Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(MAIL_OPERATIONS_UPDATE_FORCE_MAJUERE);
		int index = 0;
		burstProcedure.setParameter(++index, requestVO.getCompanyCode());
		burstProcedure.setParameter(++index, requestVO.getForceMajeureID());
		burstProcedure.setParameter(++index, requestVO.getStatus());
		burstProcedure.setParameter(++index, requestVO.getApprRemarks());
		burstProcedure.setParameter(++index, requestVO.getLastUpdatedUser());
		burstProcedure.setOutParameter(++index, SqlType.STRING);
		burstProcedure.execute();
		outPar = (String) burstProcedure.getParameter(index);
		log.debug("" + "outParameter is " + " " + outPar);
		log.debug(CLASS_NAME + " : " + "updateForceMajeureRequest" + " Exiting");
		return outPar;
	}

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findAllContainersInAssignedFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAllContainersInAssignedFlight" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_ALL_CONTAINERS_IN_ASSIGNED_FLIGHT);
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPol());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAllContainersInAssignedFlight" + " Exiting");
		return qry.getResultList(new ContainerMapper());
	}

	/**
	 * @author A-7794
	 * @param companyCode
	 * @param fileType
	 * @return
	 * @throws SystemException
	 */
	@Override
	public Collection<ConsignmentDocumentVO> fetchMailDataForOfflineUpload(String companyCode, String fileType) {
		log.debug(CLASS_NAME + " : " + "fetchMailDataForOfflineUpload" + " Entering");
		Collection<ConsignmentDocumentVO> documentVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(FETCH_MAILDATA_FOR_OFFLINE_UPLOAD);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, fileType);
		documentVOs = query.getResultList(new FileUploadConsignmentMultiMapper());
		log.info("" + "ConsignmentDocumentVO is from dao*****" + " " + documentVOs);
		return documentVOs;
	}

	@Override
	public List<MailMonitorSummaryVO> getServiceFailureDetails(MailMonitorFilterVO filterVO) throws PersistenceException {
		return null;
	}

	@Override
	public List<MailMonitorSummaryVO> getOnTimePerformanceDetails(MailMonitorFilterVO filterVO) throws PersistenceException {
		return null;
	}

	/**
	 * @author A-7794
	 * @param fileUploadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	@Override
	public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "processMailDataFromExcel" + " Entering");
		String processStatus = null;
		int index = 0;
		Procedure procedure = getQueryManager().createNamedNativeProcedure(PROCESS_MAIL_OPERATIONS_FROM_FILE);
		procedure.setParameter(++index, fileUploadFilterVO.getCompanyCode());
		procedure.setParameter(++index, fileUploadFilterVO.getFileType());
		procedure.setParameter(++index, "F");
		procedure.setParameter(++index, fileUploadFilterVO.getProcessIdentifier());
		procedure.setParameter(++index, MailConstantsVO.FLAG_NO);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		processStatus = (String) procedure.getParameter(index);
		return processStatus;
	}
	public List<MailMonitorSummaryVO> getForceMajeureCountDetails(MailMonitorFilterVO filterVO)
			throws PersistenceException {
		String baseQry = null;
		String baseQueryCount = null;
		StringBuilder rankQuery = new StringBuilder();
		baseQry = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_FORCEMEJURE);
		baseQueryCount = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_FORCEMEJURE_COUNT);
		rankQuery.append(baseQueryCount);
		rankQuery.append(baseQry);
		List<MailMonitorSummaryVO> summaryVOs = new ArrayList<MailMonitorSummaryVO>();
		Query query = new MailPerformanceDetailsFilterQuery(filterVO.getPageSize(), new MailbagMapper(), filterVO,
				"FORCE_MEJURE", rankQuery);
		summaryVOs = query.getResultList(new ForceMajeureCountMapper());
		return summaryVOs;
	}

	public Page<MailbagVO> getPerformanceMonitorMailbags(MailMonitorFilterVO filterVO, String type, int pageNumber)
			throws PersistenceException {
		Page<MailbagVO> mailbagVos = null;
		String baseQry = null;
		String baseQuerySelect = null;
		StringBuilder rankQuery = new StringBuilder();
		baseQry = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_FORCEMEJURE);
		baseQuerySelect = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_SELECT);
		rankQuery.append("SELECT RESULT_TABLE.* , ROW_NUMBER() OVER(ORDER BY NULL) AS RANK FROM ( ");
		rankQuery.append(baseQuerySelect);
		rankQuery.append(baseQry);
		PageableNativeQuery<MailbagVO> qry = new MailPerformanceMonitorFilterQuery(filterVO.getPageSize(),
				new MailbagMapper(), filterVO, type, rankQuery);
		qry.append(" )RESULT_TABLE");
		mailbagVos = qry.getPage(pageNumber);
		return mailbagVos;
	}

	/**
	 * Added by 	: A-8464 on 26-Mar-2018 Used for 	:	ICRD-273761 Parameters	:	@param mailbagEnquiryFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public MailbagVO findMailbagDetailsForMailbagEnquiryHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagDetailsForMailbagEnquiryHHT" + " Entering");
		int idx = 0;
		String qryString = getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS_MAILBAGENQUIRY);
		String modifiedStr1 = null;
		if (isOracleDataSource()) {
			modifiedStr1 = "LISTAGG(RTGCARCOD || ' ' || RTGFLTNUM || ' ' || RTGFLTDAT,',') within GROUP (ORDER BY RTGFLTDAT) ROUTEINFO";
		} else {
			modifiedStr1 = "string_agg ( RTGCARCOD || ' '|| RTGFLTNUM|| ' '|| RTGFLTDAT, ','  ORDER BY RTGFLTDAT) ROUTEINFO";
		}
		qryString = String.format(qryString, modifiedStr1);
		Query qry = getQueryManager().createNativeQuery(qryString);
		qry.setParameter(++idx, mailbagEnquiryFilterVO.getCompanyCode());
		qry.setParameter(++idx, mailbagEnquiryFilterVO.getMailbagId());
		qry.setParameter(++idx, mailbagEnquiryFilterVO.getCompanyCode());
		qry.setParameter(++idx, mailbagEnquiryFilterVO.getMailbagId());
		MailbagVO result = qry.getSingleResult(new MailbagEnquiryMapper());
		return result;
	}
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findDuplicateMailbag(java.lang.String, java.lang.String) Added by 			: A-7531 on 16-May-2019 Used for 	: Parameters	:	@param companyCode Parameters	:	@param mailBagId Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	@Override
	public ArrayList<MailbagVO> findDuplicateMailbag(String companyCode, String mailBagId) {
		Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
		ArrayList<MailbagVO> newmailVOs = new ArrayList<MailbagVO>();
		int idx = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_DUPLICATEMAILBAG_SEQNUM);
		qry.setParameter(++idx, companyCode);
		qry.setParameter(++idx, mailBagId);
		mailVOs = qry.getResultList(new DuplicateMailBagMapper());
		newmailVOs = new ArrayList<>(mailVOs);
		return newmailVOs;
	}

	@Override
	public Page<MailbagVO> findDeviationMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) throws PersistenceException {
		log.debug("findDeviationMailbags");
		StringBuilder baseQuery = null;
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append(" MALIDR, MALSEQNUM ) AS RANK FROM ( ");
		Query masterQuery = null;
		masterQuery = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_MAIN);
		baseQuery = new StringBuilder(rankQuery).append(" ").append(masterQuery);
		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		Query query1 = null;
		query1 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_1);
		Query query2 = null;
		query2 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_2);
		pageableNativeQuery = new DeviationMailbagFilter(new DeviationMailbagMapper(), mailbagEnquiryFilterVO,
				baseQuery, query1, query2);
		pageableNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		pageableNativeQuery.append(" ");
			return pageableNativeQuery.getPage(pageNumber);
	}

	private boolean isFlightFilterPresent(MailArrivalVO mailArrivalVO) {
		if (mailArrivalVO.getFlightNumber() != null && mailArrivalVO.getFlightNumber().trim().length() > 0
				&& mailArrivalVO.getFlightDate() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#getTempCarditMessages(java.lang.String) Added by 			: A-6287 on 01-Mar-2020 Used for 	: Parameters	:	@param companyCode Parameters	:	@return
	 * @throws SystemException
	 */
	@Override
	public Collection<CarditTempMsgVO> getTempCarditMessages(String companyCode, String includeMailBoxIdr,
															 String excludeMailBoxIdr, String includedOrigins, String excludedOrigins, int pageSize, int noOfDays) {
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "getTempCarditMessages" + " Entering");
		Collection<CarditTempMsgVO> carditTempMsgVOs = null;
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_TMPCARDITMSGS);
		query.setParameter(++index, companyCode);
		if (includeMailBoxIdr != null && !includeMailBoxIdr.trim().isEmpty()) {
			query.append(" and POSITION(tmp.sndidr in ?) >0 ");
			query.setParameter(++index, includeMailBoxIdr);
		}
		if (excludeMailBoxIdr != null && !excludeMailBoxIdr.trim().isEmpty()) {
			query.append(" AND  pkg_frmwrk.fun_check_string_contains(?,TMP.SNDIDR,?)=0 ");
			query.setParameter(++index, excludeMailBoxIdr);
			query.setParameter(++index, ",");
		}
		if (includedOrigins != null && !includedOrigins.trim().isEmpty()) {
			query.append(" AND  pkg_frmwrk.fun_check_string_contains(?,substr(TMP.DEPPLC,1,3),?)>0 ");
			query.setParameter(++index, includedOrigins);
			query.setParameter(++index, ";");
		}
		if (excludedOrigins != null && !excludedOrigins.trim().isEmpty()) {
			query.append(" AND  pkg_frmwrk.fun_check_string_contains(?,substr(TMP.DEPPLC,1,3),?)=0 ");
			query.setParameter(++index, excludedOrigins);
			query.setParameter(++index, ";");
		}
		if (noOfDays > 0) {
			query.append(" and TMP.lstupdtim> current_date- ? ");
			query.setParameter(++index, noOfDays);
		}
		if (pageSize > 0) {
			query.append(" ORDER BY TMP.SNDIDR,TMP.ICHCTLREF,TMP.MSGSEQNUM) MAL WHERE RNK < ? ");
			query.setParameter(++index, pageSize);
		} else {
			query.append(" ORDER BY TMP.SNDIDR,TMP.ICHCTLREF,TMP.MSGSEQNUM) MAL WHERE RNK <100 ");
		}
		carditTempMsgVOs = query.getResultList(new TempCarditMsgMapper());
		return carditTempMsgVOs;
	}

	public int findbulkcountInFlight(ContainerDetailsVO containerDetailsVO) {
		log.debug(CLASS_NAME + " : " + "findbulkcountInFlight" + " Entering");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_BULKCOUNT_FLIGHT);
		qry.setParameter(++index, containerDetailsVO.getCompanyCode());
		qry.setParameter(++index, containerDetailsVO.getCarrierId());
		qry.setParameter(++index, containerDetailsVO.getFlightNumber());
		qry.setParameter(++index, containerDetailsVO.getFlightSequenceNumber());
		qry.setParameter(++index, containerDetailsVO.getDestination());
		qry.setParameter(++index, containerDetailsVO.getAssignedPort());
		return qry.getSingleResult(getIntMapper("BULKCOUNT"));
	}

	public Collection<DSNVO> getDSNsForCarrier(ContainerDetailsVO containervo) throws PersistenceException {
		List<DSNVO> dsnVos = null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		int index = 0;
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findMailbagsinContainer" + " Entering");
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_CARRIER_ULD_DSNVIEW);
		} else {
			qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_CARRIER_BULK_DSNVIEW);
		}
		qry.setParameter(++index, containervo.getCompanyCode());
		qry.setParameter(++index, containervo.getCarrierId());
		qry.setParameter(++index, containervo.getPol());
		qry.setParameter(++index, containervo.getContainerNumber());
		qry.append(") MST GROUP BY CMPCOD,DSN,ORGEXGOFC,DSTEXGOFC,MALSUBCLS,MALCTGCOD,YER");
		dsnVos = qry.getResultList(new MailbagDSNMapper());
		return dsnVos;
	}

	/**
	 * @author a-9529 This method is used to find out the Mail Bags in theContainers
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findMailbagsInContainerFromInboundForReact(
			Collection<ContainerDetailsVO> containers) throws PersistenceException {
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "findMailbagsInContainerFromInboundForReact" + " Entering");
		Collection<ContainerDetailsVO> containerForReturn = new ArrayList<>();
		Query qry = null;
		for (ContainerDetailsVO cont : containers) {
			int idx = 0;
			if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
				qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FLIGHT_ULD_INBOUND);
				qry.setParameter(++idx, cont.getCompanyCode());
				qry.setParameter(++idx, cont.getCarrierId());
				qry.setParameter(++idx, cont.getFlightNumber());
				qry.setParameter(++idx, cont.getFlightSequenceNumber());
				qry.setParameter(++idx, cont.getLegSerialNumber());
				qry.setParameter(++idx, cont.getPol());
				qry.setParameter(++idx, cont.getContainerNumber());
			} else {
				qry = getQueryManager().createNamedNativeQuery(FIND_INBOUND_MAILBAG_DETAILS);
				qry.setParameter(++idx, cont.getCompanyCode());
				qry.setParameter(++idx, cont.getCarrierId());
				qry.setParameter(++idx, cont.getFlightNumber());
				qry.setParameter(++idx, cont.getFlightSequenceNumber());
				qry.setParameter(++idx, cont.getPou());
				qry.setParameter(++idx, cont.getContainerNumber());
				qry.append(" AND FLT.POL =  ?  ");
				qry.setParameter(++idx, cont.getPol());
				qry.append(" )ORDER BY CONNUM, DSN,ORGEXGOFC,DSTEXGOFC,MALCLS,MALSUBCLS,MALCTGCOD,YER,MALSTA  ");
			}
			List<ContainerDetailsVO> list = qry
					.getResultList(cont.getFlightSequenceNumber() > 0 ? new AcceptedDsnsInFlightMultiMapper()
							: new AcceptedDsnsInCarrierMultiMapper());
			if (list != null && !list.isEmpty()) {
				containerForReturn.add(list.get(0));
			}
		}
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "findMailbagsInContainerFromInboundForReact" + " Exiting");
		return containerForReturn;
	}
	public String findContainerInfoForDeviatedMailbag(ContainerDetailsVO containerDetailsVO, long mailSequenceNumber) {
		String containerInfo = null;
		Query query;
		try {
			query = getQueryManager().createNamedNativeQuery(CONTAINER_INFO_DEVIATED_MAILBAG);
			int index = 0;
			query.setParameter(++index, containerDetailsVO.getCompanyCode());
			query.setParameter(++index, mailSequenceNumber);
			query.setParameter(++index, containerDetailsVO.getPou());
			containerInfo = query.getSingleResult(new ContainerForDeviatedMailbagMapper());
		} finally {
		}
		return containerInfo;
	}

	/**
	 * @author A-5526 This method is used to find the details of approved Force Meajure info of a Mailbag
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode, String mailBagId,
																			 long mailSequenceNumber) throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findApprovedForceMajeureDetails" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_APPROVED_FORCE_MAJEURE_DETAILS_OF_MAILBAG);
		query.setParameter(++index, companyCode);
		if (mailSequenceNumber != 0l) {
			query.append(" AND REQMST.MALSEQNUM = ?  ");
			query.setParameter(++index, mailSequenceNumber);
		} else if (mailBagId != null && !mailBagId.isEmpty()) {
			query.append(" AND REQMST.MALIDR = ? ");
			query.setParameter(++index, mailBagId);
		}
		return query.getResultList(new ForceMajeureRequestMapper());
	}

	/**
	 * @author A-8353Parameters	:	@param String Parameters	:	@throws String Parameters	:	@throws String Parameters	:	@return Parameters	:	@throws SystemException
	 */
	public String checkMailInULDExistForNextSeg(String containerNumber, String airpotCode, String companyCode) {
		log.debug("MailTrackingDefaultsSqlDao" + " : " + "checkMailInULDExistForNextSeg" + " Entering");
		String mailExistInNextSeg = null;
		Query query = getQueryManager().createNamedNativeQuery(MAILBAG_IN_CONTAINER_USED_IN_NEXT_SEG);
		int idx = 0;
		query.setParameter(++idx, airpotCode);
		query.setParameter(++idx, airpotCode);
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, containerNumber);
		mailExistInNextSeg = query.getSingleResult(getStringMapper("MALEXT"));
		log.debug("MailTrackingDefaultsSqlDao" + " : " + "checkMailInULDExistForNextSeg" + " Exiting");
		return mailExistInNextSeg;
	}

	/**
	 * @author A-9084Parameters	:	@param String Parameters	:	@throws String Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public ConsignmentDocumentVO findConsignmentScreeningDetails(String consignmentNumber, String companyCode,
																 String poaCode) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDao" + " : " + "findConsignmentScreeningDetails" + " Entering");
		List<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
		ConsignmentDocumentVO consignmentVO = new ConsignmentDocumentVO();
		Query query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_SCREENING_DETAILS);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, consignmentNumber);
		query.setParameter(++index, poaCode);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, consignmentNumber);
		if (poaCode != null && !poaCode.isEmpty()) {
			query.append(" AND MST.POACOD = ?");
			query.setParameter(++index, poaCode);
		}
		consignmentDocumentVOs = query.getResultList(new ListConsignmentScreeningMapper());
		if (!consignmentDocumentVOs.isEmpty()) {
			consignmentVO = consignmentDocumentVOs.get(0);
			return consignmentVO;
		}

		return null;
	}
	@Override
	public MailbagVO findMailbagBillingStatus(MailbagVO mailbagVO) {
		log.debug(CLASS_NAME + " : " + "findMailbagBillingStatus" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAIL_BILLING_STATUS);
		int index = 0;
		qry.setParameter(++index, mailbagVO.getCompanyCode());
		qry.setParameter(++index, mailbagVO.getMailSequenceNumber());
		return qry.getSingleResult(new MailbagBillingStatusMapper());
	}
	@Override
	public String saveUploadedForceMajeureData(FileUploadFilterVO fileUploadFilterVO) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug(CLASS_NAME + " : " + "saveUploadedForceMajeureData" + " Entering");
		String processStatus = "OK";
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		int index = 0;
		Procedure procedure = getQueryManager()
				.createNamedNativeProcedure(MAIL_OPERATIONS_SAVE_FORCE_MAJEURE_REQUEST_FOR_UPLOAD);
		procedure.setParameter(++index, fileUploadFilterVO.getCompanyCode());
		procedure.setParameter(++index, fileUploadFilterVO.getProcessIdentifier());
		procedure.setParameter(++index, logonAttributes.getUserId());
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		processStatus = (String) procedure.getParameter(index);
		log.debug("" + "ProcessStatus-->" + " " + processStatus);
		log.debug(CLASS_NAME + " : " + "saveUploadedForceMajeureData" + " Exiting");
		return processStatus;
	}

	public Collection<ImportFlightOperationsVO> findInboundFlightOperationsDetails(ImportOperationsFilterVO filterVO,
																				   Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException {
		Collection<ImportFlightOperationsVO> importFlightOperationsVOs = null;
		String baseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAILINBOUND_FLIGHT_OPERATIONS_DETAILS);
		String baseQueryOne = getQueryManager()
				.getNamedNativeQueryString(FIND_MAILINBOUND_FLIGHT_OPERATIONS_DETAILS_ONE);
		ImportFlightOperationsFilterQuery importFlightOperationsFilterQuery = new ImportFlightOperationsFilterQuery(
				filterVO, manifestFilterVOs, baseQuery, baseQueryOne);
		Query qry = importFlightOperationsFilterQuery;
		importFlightOperationsVOs = qry.getResultList(new ImportFlightOperationsMultiMapper());
		return importFlightOperationsVOs;
	}

	public Collection<OffloadULDDetailsVO> findOffloadULDDetailsAtAirport(
			com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO filterVO) throws PersistenceException {
		log.debug(OPR_FLTHANDLING_FIND_OFFLOAD_ULD_DETAILS_AT_AIRPORT + " : " + "findOffloadULDDetailsAtAirport"
				+ " Entering");
		String baseQuery = getQueryManager()
				.getNamedNativeQueryString(OPR_FLTHANDLING_FIND_OFFLOAD_ULD_DETAILS_AT_AIRPORT);
		Query query = new ULDReceiptOffloadFilterQuery(baseQuery, filterVO);
		return query.getResultList(new OffloadULDDetailsMultiMapper());
	}

	public Collection<ManifestVO> findExportFlightOperationsDetails(ImportOperationsFilterVO filterVO,
																	Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException {
		log.debug(FIND_EXPORTFLIGHT_OPERATIONS_DETAILS + " : " + "findExportFlightOperationsDetails" + " Entering");
		Collection<ManifestVO> exportFlightOperationsVOs = null;
		String baseQry = getQueryManager().getNamedNativeQueryString(FIND_EXPORTFLIGHT_OPERATIONS_DETAILS);
		Query qry = new ExportFlightOperationsFilterQuery(filterVO, manifestFilterVOs, baseQry);
		exportFlightOperationsVOs = qry.getResultList(new ExportFlightOperationsMultiMapper());
		return exportFlightOperationsVOs;
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#fetchConsignmentDetailsForUpload(com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO) Added by 			: A-6245 on 22-Dec-2020 Used for 	:	IASCB-81526 Parameters	:	@param fileUploadFilterVO Parameters	:	@return Parameters	:	@throws SystemException
	 */
	@Override
	public Collection<ConsignmentDocumentVO> fetchConsignmentDetailsForUpload(FileUploadFilterVO fileUploadFilterVO) {
		log.debug(CLASS_NAME + " : " + "fetchConsignmentDetailsForUpload" + " Entering");
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FETCH_CONSIGNMENT_DETAILS_FOR_UPLOAD);
		int index = 0;
		query.setParameter(++index, fileUploadFilterVO.getCompanyCode());
		query.setParameter(++index, fileUploadFilterVO.getFileType());
		consignmentDocumentVOs = query.getResultList(new ConsignmentDetailsForUploadMultiMapper());
		log.debug(CLASS_NAME + " : " + "fetchConsignmentDetailsForUpload" + " Exiting");
		return consignmentDocumentVOs;
	}

	@Override
	public Collection<ContainerDetailsVO> findContainerJourneyID(ConsignmentFilterVO consignmentFilterVO) {
		log.debug(CLASS_NAME + " : " + "findContainerJourneyID" + " Entering");
		Collection<ContainerDetailsVO> containerJourneyIdDetails = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_CONTAINER_JOURNEY_ID);
		int index = 0;
		query.setParameter(++index, consignmentFilterVO.getCompanyCode());
		query.setParameter(++index, consignmentFilterVO.getContainerNumber());
		query.setParameter(++index, consignmentFilterVO.getConsignmentFromDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
		query.setParameter(++index,  consignmentFilterVO.getConsignmentToDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
		containerJourneyIdDetails = query.getResultList(new ContainerJourneyIDMapper());
		log.debug(CLASS_NAME + " : " + "findContainerJourneyID" + " Exiting");
		return containerJourneyIdDetails;
	}

	@Override
	public Collection<MailbagVO> getFoundArrivalMailBags(MailArrivalVO mailArrivalVO) throws PersistenceException {
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(GET_MAILBAGS_ARRIVAL);
		qry.setParameter(++index, mailArrivalVO.getCompanyCode());
		qry.setParameter(++index, mailArrivalVO.getCarrierId());
		qry.setParameter(++index, mailArrivalVO.getFlightNumber());
		qry.setParameter(++index, mailArrivalVO.getFlightSequenceNumber());
		StringBuilder mailbagId = new StringBuilder("");
		String joinQuery = null;
		Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				joinQuery = constructMailbagIdQuery(mailbagId, joinQuery, containerDetailsVO);
			}
		}
		qry.append(" AND MST.MALSEQNUM IN ");
		qry.append(joinQuery);
		qry.append(")");
		return qry.getResultList(new MailbagMapper());
	}

	private String constructMailbagIdQuery(StringBuilder mailbagId, String joinQuery,
										   ContainerDetailsVO containerDetailsVO) {
		if (containerDetailsVO.getMailDetails() != null && !containerDetailsVO.getMailDetails().isEmpty()) {
			Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
			if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
				for (MailbagVO mailbagVO : mailbagVOs) {
					mailbagId.append(mailbagVO.getMailSequenceNumber()).append(",");
				}
				String[] mailbags = mailbagId.toString().split(",");
				joinQuery = getWhereClause(mailbags);
			}
		}
		return joinQuery;
	}

	@Override
	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterVO mailAuditFilterVO) {
		log.debug(CLASS_NAME + " : " + "findAssignFlightAuditDetails" + " Entering");
		Collection<AuditDetailsVO> auditDetailsVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_FLIGHT_AUDIT_DETAILS);
		int index = 0;
		query.setParameter(++index, mailAuditFilterVO.getFlightNumber());
		query.setParameter(++index, mailAuditFilterVO.getFlightDate());
		if (mailAuditFilterVO.getAssignPort() != null) {
			query.append("AND FLTAUD.ARPCOD = ?");
			query.setParameter(++index, mailAuditFilterVO.getAssignPort());
		}
		auditDetailsVOs = query.getResultList(new AssignFlightAuditMapper());
		log.debug(CLASS_NAME + " : " + "findAssignFlightAuditDetails" + " Exiting");
		return auditDetailsVOs;
	}

	@Override
	public Collection<MailAcceptanceVO> findContainerVOs(MailAcceptanceVO mailAcceptanceVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findContainerVOs" + " Entering");
		Collection<MailAcceptanceVO> mailAcceptanceVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_FLIGHT_PURE_CONTAINER_DETAILS);
		int index = 0;
		query.setParameter(++index, mailAcceptanceVO.getCompanyCode());
		query.setParameter(++index, mailAcceptanceVO.getCarrierId());
		query.setParameter(++index, mailAcceptanceVO.getFlightNumber());
		query.setParameter(++index, mailAcceptanceVO.getFlightSequenceNumber());
		query.setParameter(++index, mailAcceptanceVO.getFlightDestination());
		mailAcceptanceVOs = query.getResultList(new PureTransferContainerMapper());
		return mailAcceptanceVOs;
	}

	@Override
	public MailbagVO listmailbagSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo) {
		log.debug(CLASS_NAME + " : " + LIST_MAILBAG_SECURITY_DETAILS + " Entering");
		List<MailbagVO> mailbagVOs = null;
		MailbagVO mailbagVO = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_LIST_MAILBAG_SECURITY_DETAILS);
		int index = 0;
		query.setParameter(++index, mailScreeningFilterVo.getCompanyCode());
		query.setParameter(++index, mailScreeningFilterVo.getMailBagId());
		query.setParameter(++index, mailScreeningFilterVo.getCompanyCode());
		query.setParameter(++index, mailScreeningFilterVo.getMailBagId());
		query.setSensitivity(true);
		mailbagVOs = query.getResultList(new MailbagSecurityDetailsMapper());
		mailbagVO = mailbagVOs.get(0);
		if (mailbagVO != null && !mailbagVO.getConsignmentScreeningVO().isEmpty()) {
			mailbagVO.setSecurityDetailsPresent(true);
			return mailbagVO;
		} else {
			Query query2 = getQueryManager()
					.createNamedNativeQuery(MAIL_OPERATIONS_LIST_MAILBAG_SECURITY_DETAILS_MAIL_SEQUENCE_NUMBER);
			int indexs = 0;
			query2.setParameter(++indexs, mailScreeningFilterVo.getCompanyCode());
			query2.setParameter(++indexs, mailScreeningFilterVo.getMailBagId());
			mailbagVOs = query2.getResultList(new MailbagSecurityDetailsMapper());
			mailbagVO = mailbagVOs.get(0);
			return mailbagVO;
		}
	}
	public Collection<MailInConsignmentVO> findMailInConsignment(ConsignmentFilterVO consignmentFilterVO) {
		Collection<MailInConsignmentVO> mailInConsignmentVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_DOCUMENT_DETAILS);
		int index = 0;
		query.setParameter(++index, consignmentFilterVO.getCompanyCode());
		query.setParameter(++index, consignmentFilterVO.getConsignmentNumber());
		query.setParameter(++index, consignmentFilterVO.getPaCode());
		mailInConsignmentVOs = query.getResultList(new MaintainConsignmentMapper());
		log.info("" + " ###### Query for execution " + " " + query.toString());
		return mailInConsignmentVOs;
	}

	/**
	 * @author A-8353
	 * @param cmpcod
	 * @param malSeqNum
	 * @return
	 * @throws SystemException
	 */
	@Override
	public ConsignmentScreeningVO findRegulatedCarrierForMailbag(String cmpcod, long malSeqNum) {
		log.debug(CLASS_NAME + " : " + MAIL_OPERATIONS_FIND_REGULATED_CARRIER_FOR_MAILBAG + " Entering");
		List<MailbagVO> mailbagVOs = null;
		MailbagVO mailbagVO = null;
		ConsignmentScreeningVO consignmentScreeningVO = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_REGULATED_CARRIER_FOR_MAILBAG);
		int index = 0;
		query.setParameter(++index, cmpcod);
		query.setParameter(++index, malSeqNum);
		mailbagVOs = query.getResultList(new MailbagSecurityDetailsMapper());
		mailbagVO = mailbagVOs.get(0);
		if (mailbagVO != null && !mailbagVO.getConsignmentScreeningVO().isEmpty()) {
			consignmentScreeningVO = mailbagVO.getConsignmentScreeningVO().iterator().next();
		}
		return consignmentScreeningVO;
	}
	/**
	 * @author A-10647
	 * @return FlightLoanPlanContainerVOs
	 * @throws SystemException
	 */
	@Override
	public Collection<FlightLoadPlanContainerVO> findPreviousLoadPlanVersionsForContainer(
			FlightLoadPlanContainerVO loadPlanVO) {
		log.debug(CLASS_NAME + " : " + MAIL_OPERATIONS_FIND_LOADPLAN_VERSIONS_FORCONTAINER + " Entering");
		List<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_LOADPLAN_VERSIONS_FORCONTAINER);
		int index = 0;
		query.setParameter(++index, loadPlanVO.getCompanyCode());
		query.setParameter(++index, loadPlanVO.getContainerNumber());
		query.setParameter(++index, loadPlanVO.getUldReferenceNo());
		flightLoadPlanContainerVOs = query.getResultList(new LoadPlanDetailsForContainerMapper());
		return flightLoadPlanContainerVOs;
	}

	/**
	 * @author A-9477
	 * @return FlightLoanPlanContainerVO
	 * @throws SystemException
	 */
	@Override
	public Collection<FlightLoadPlanContainerVO> findLoadPlandetails(SearchContainerFilterVO searchContainerFilterVO) {
		log.debug(CLASS_NAME + " : " + "findLoadPlandetails" + " Entering");
		List<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_LOADPLAN_DETAILS_FOR_FLIGHT);
		int index = 0;
		query.setParameter(++index, searchContainerFilterVO.getCompanyCode());
		query.setParameter(++index, searchContainerFilterVO.getCarrierId());
		query.setParameter(++index, searchContainerFilterVO.getFlightNumber());
		query.setParameter(++index, Long.parseLong(searchContainerFilterVO.getFlightSeqNumber()));
		if (searchContainerFilterVO.getSegOrigin() != null) {
			query.append(" AND LODPLNCON.SEGORG    = ? ");
			query.setParameter(++index, searchContainerFilterVO.getSegOrigin());
		}
		if (searchContainerFilterVO.getSegDestination() != null) {
			query.append(" AND LODPLNCON.SEGDST    = ? ");
			query.setParameter(++index, searchContainerFilterVO.getSegDestination());
		}
		query.append(" AND LODPLNVER =(SELECT MAX(LODPLNVER) FROM MALLODPLNFLTCON LODPLN ");
		query.append(" WHERE LODPLN.CMPCOD = LODPLNCON.CMPCOD ");
		query.append(" AND LODPLN.FLTCARIDR = LODPLNCON.FLTCARIDR ");
		query.append(" AND LODPLN.FLTNUM = LODPLNCON.FLTNUM ");
		query.append(" AND LODPLN.FLTSEQNUM = LODPLNCON.FLTSEQNUM ");
		if (searchContainerFilterVO.getSegOrigin() != null) {
			query.append(" AND LODPLN.SEGORG = LODPLNCON.SEGORG ");
		}
		if (searchContainerFilterVO.getSegDestination() != null) {
			query.append(" AND LODPLN.SEGDST = LODPLNCON.SEGDST ");
		}
		query.append(" ) ");
		flightLoadPlanContainerVOs = query.getResultList(new LoadPlanDetailsForContainerMapper());
		return flightLoadPlanContainerVOs;
	}
	@Override
	public Collection<ConsignmentScreeningVO> findRAacceptingForMailbag(String companyCode, long mailSequenceNumber) {
		log.debug(CLASS_NAME + " : " + MAIL_OPERATIONS_FIND_RA_ACCEPTING_FOR_MAILBAG + " Entering");
		List<MailbagVO> mailbagVOs = null;
		MailbagVO mailbagVO = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_RA_ACCEPTING_FOR_MAILBAG);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailSequenceNumber);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailSequenceNumber);
		query.setSensitivity(true);
		mailbagVOs = query.getResultList(new MailbagSecurityDetailsMapper());
		mailbagVO = mailbagVOs.get(0);
		return mailbagVO.getConsignmentScreeningVO();
	}

	@Override
	public String findRoutingDetailsForMailbag(String companyCode, long malseqnum, String airportCode) {
		log.debug(CLASS_NAME + " : " + "findRoutingDetailsForMailbag" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_ROUTING_DETAILS_FOR_MAILBAG);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, malseqnum);
		query.setParameter(++index, airportCode);
		return query.getSingleResult(getStringMapper("POL"));
	}
	@Override
	public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> fetchMailIndicatorForProgress(
			Collection<FlightListingFilterVO> flightListingFilterVOs) {
		log.debug(CLASS_NAME + " : " + FETCH_MAIL_INDICATOR + " Entering");
		Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> operationalFlightVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(FETCH_MAIL_INDICATOR);
		int index = 0;
		int first = 0;
		String companyCode = getCompanyCode(flightListingFilterVOs);
		String airportCode = getAirportCode(flightListingFilterVOs);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, airportCode);
		if (Objects.nonNull(flightListingFilterVOs)) {
			query.append("AND (FLTSEG.FLTCARIDR,FLTSEG.FLTNUM,FLTSEG.FLTSEQNUM ) IN ( ");
			for (FlightListingFilterVO flightListingFilterVO : flightListingFilterVOs) {
				if (first == 0) {
					first = 1;
				} else {
					query.append(" , ");
				}
				query.append(" ( ? , ? , ? ) ");
				query.setParameter(++index, flightListingFilterVO.getCarrierId());
				query.setParameter(++index, flightListingFilterVO.getFlightNumber());
				query.setParameter(++index, flightListingFilterVO.getFlightSequenceNumber());
			}
			query.append(")");
		}
		operationalFlightVOs = query.getResultList(new FetchMailindicatorMapper());
		return operationalFlightVOs;
	}

	private String getAirportCode(Collection<FlightListingFilterVO> flightListingFilterVOs) {
		String getAirportCode = null;
		if (flightListingFilterVOs != null && !flightListingFilterVOs.isEmpty()) {
			for (FlightListingFilterVO flightListingFilterVO : flightListingFilterVOs) {
				if (flightListingFilterVO.getAirportCode() != null) {
					return flightListingFilterVO.getAirportCode();
				}
			}
		}
		return getAirportCode;
	}

	private String getCompanyCode(Collection<FlightListingFilterVO> flightListingFilterVOs) {
		String companyCode = null;
		if (flightListingFilterVOs != null && !flightListingFilterVOs.isEmpty()) {
			for (FlightListingFilterVO flightListingFilterVO : flightListingFilterVOs) {
				if (flightListingFilterVO.getCompanyCode() != null) {
					return flightListingFilterVO.getCompanyCode();
				}
			}
		}
		return companyCode;
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findCN46TransferManifestDetails(com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO) Added by 			: A-10647 on 27-Oct-2022 Used for 	: Parameters	:	@param transferManifestVO Parameters	:	@return Parameters	:	@throws SystemException
	 */
	public Collection<ConsignmentDocumentVO> findCN46TransferManifestDetails(TransferManifestVO transferManifestVO) {
		log.debug(CLASS_NAME + " : " + "findCN46TransferManifestDetails" + " Entering");
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CN46_TRANSFER_MANIFEST_TDTL);
		qry.setParameter(++index, transferManifestVO.getCompanyCode());
		qry.setParameter(++index, transferManifestVO.getTransferManifestId());
		consignmentDocumentVOs = qry.getResultList(new TransferManifestConsignmentDetail());
		log.debug(CLASS_NAME + " : " + "findCN46TransferManifestDetails" + " Exiting");
		return consignmentDocumentVOs;
	}
	public Collection<MailAcceptanceVO> fetchFlightPreAdviceDetails(Collection<FlightFilterVO> flightFilterVOs)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "fetchFlightPreAdviceDetails" + " Entering");
		Collection<MailAcceptanceVO> preAdviceFlightVOs = new ArrayList<>();
		int index = 0;
		for (FlightFilterVO flightFilterVO : flightFilterVOs) {
			Query qry = getQueryManager().createNamedNativeQuery(OUTBOUND_FLIGHT_PREADVICE_DETAILS_NEW);
			qry.setParameter(++index, flightFilterVO.getCompanyCode());
			qry.setParameter(++index, flightFilterVO.getFlightCarrierId());
			qry.setParameter(++index, flightFilterVO.getFlightNumber());
			qry.setParameter(++index, flightFilterVO.getFlightSequenceNumber());
			MailAcceptanceVO preAdviceFlightVO = qry.getSingleResult(new OutboundFlightPreAdviceMapper());
			if (preAdviceFlightVO != null) {
				preAdviceFlightVOs.add(preAdviceFlightVO);
			}
			index = 0;
		}
		return preAdviceFlightVOs;
	}

	@Override
	public Collection<OperationalFlightVO> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO) {
		int index = 0;
		String query = getQueryManager().getNamedNativeQueryString(FIND_FLIGHTS_FOR_MAIL_INBOUND_AUTO_ATTACH_AWB);
		StringBuilder sb = new StringBuilder();
		if (Objects.nonNull(mailInboundAutoAttachAWBJobScheduleVO.getCarrierCodes())
				&& !mailInboundAutoAttachAWBJobScheduleVO.getCarrierCodes().trim().isEmpty()) {
			sb.append(" AND EXISTS (SELECT 1 FROM SHRARLMST ARL WHERE ARL.CMPCOD = ASGFLTSEG.CMPCOD");
			sb.append(" AND ARL.ARLIDR=ASGFLTSEG.FLTCARIDR AND ARL.TWOAPHCOD IN ('");
			sb.append(mailInboundAutoAttachAWBJobScheduleVO.getCarrierCodes().replace(",", "','")).append("'))");
		}
		if (Objects.nonNull(mailInboundAutoAttachAWBJobScheduleVO.getPointOfLadingCountries())
				&& !mailInboundAutoAttachAWBJobScheduleVO.getPointOfLadingCountries().trim().isEmpty()) {
			sb.append(" AND EXISTS (SELECT 1 FROM SHRARPMST ARP WHERE ARP.CMPCOD = ASGFLTSEG.CMPCOD");
			sb.append(" AND ARP.ARPCOD=ASGFLTSEG.POL AND ARP.CNTCOD IN ('");
			sb.append(mailInboundAutoAttachAWBJobScheduleVO.getPointOfLadingCountries().replace(",", "','"))
					.append("'))");
		}
		if (Objects.nonNull(mailInboundAutoAttachAWBJobScheduleVO.getPointOfUnladingCountries())
				&& !mailInboundAutoAttachAWBJobScheduleVO.getPointOfUnladingCountries().trim().isEmpty()) {
			sb.append(" AND EXISTS (SELECT 1 FROM SHRARPMST ARP WHERE ARP.CMPCOD = ASGFLTSEG.CMPCOD");
			sb.append(" AND ARP.ARPCOD=ASGFLTSEG.POU AND ARP.CNTCOD IN ('");
			sb.append(mailInboundAutoAttachAWBJobScheduleVO.getPointOfUnladingCountries().replace(",", "','"))
					.append("'))");
		}
		if (isOracleDataSource()) {
			sb.append(" AND ( TO_NUMBER(TO_CHAR(SYS_EXTRACT_UTC(SYSTIMESTAMP), 'YYYYMMDDHH24MISS')) >=")
					.append(" TO_NUMBER(TO_CHAR(LEG.STAUTC -(SELECT TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/1440")
					.append(" FROM SHRUSEARPPAR ARPPAR WHERE ARPPAR.CMPCOD= LEG.CMPCOD")
					.append(" AND ARPPAR.PARCOD='mail.operations.inboundautoattachawboffset' AND ARPPAR.ARPCOD= ASGFLTSEG.POU")
					.append(" AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) > 0),'YYYYMMDDHH24MISS')))").toString();
		} else {
			sb.append(" AND ( TO_NUMBER(TO_CHAR(now() at time zone ('utc'), 'YYYYMMDDHH24MISS')) >=").append(
							" TO_NUMBER(TO_CHAR(LEG.STAUTC - interval '1 day' *(SELECT TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/1440")
					.append(" FROM SHRUSEARPPAR ARPPAR WHERE ARPPAR.CMPCOD= LEG.CMPCOD")
					.append(" AND ARPPAR.PARCOD='mail.operations.inboundautoattachawboffset' AND ARPPAR.ARPCOD= ASGFLTSEG.POU")
					.append(" AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) > 0),'YYYYMMDDHH24MISS')))").toString();
		}
		sb.append(" AND EXISTS (SELECT 1 FROM MALMST MAL INNER JOIN MALULDSEGDTL SEGDTL ON MAL.CMPCOD = SEGDTL.CMPCOD");
		sb.append(" AND MAL.MALSEQNUM = SEGDTL.MALSEQNUM ");
		sb.append(" WHERE SEGDTL.CMPCOD = ASGFLTSEG.CMPCOD AND SEGDTL.FLTNUM = ASGFLTSEG.FLTNUM");
		sb.append(" AND SEGDTL.FLTCARIDR = ASGFLTSEG.FLTCARIDR AND SEGDTL.FLTSEQNUM = ASGFLTSEG.FLTSEQNUM");
		sb.append(" AND SEGDTL.SEGSERNUM = ASGFLTSEG.SEGSERNUM AND MAL.MSTDOCNUM IS NULL)");
		query = new StringBuilder(query).append(sb).toString();
		Query qry = getQueryManager().createNativeQuery(query);
		qry.setParameter(++index, mailInboundAutoAttachAWBJobScheduleVO.getCompanyCode());
		return qry.getResultList(new MailInboundAutoAttachAWBFlightsMapper());
	}

	@Override
	public Page<MailTransitVO> findMailTransit(MailTransitFilterVO mailTransitFilterVO, int pageNumber)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailtransit" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILTRANSIT);
		PageableNativeQuery<MailTransitVO> pgqry = null;
		Page<MailTransitVO> mailTransitVOs = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_DENSE_RANK_QUERY);
		rankQuery.append("RESULT_TABLE.CMPCOD,RESULT_TABLE.MALDSTCOD) RANK FROM (");
		rankQuery.append(query);
		pgqry = new MailTransitFilterQuery(new MailTransitMapper(), mailTransitFilterVO, rankQuery.toString());
		mailTransitVOs = pgqry.getPage(pageNumber);
		log.debug(CLASS_NAME + " : " + "" + " Exiting");
		return mailTransitVOs;
	}

	/**
	 * Added by 	: A-8464 on 26-Mar-2018 Used for 	:	ICRD-273761 Parameters	:	@param mailbagEnquiryFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public MailbagVO findMailbagDetailsForMailInboundHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws PersistenceException {
		log.debug(CLASS_NAME + " : " + "findMailbagDetailsForMailbagEnquiryHHT" + " Entering");
		int idx = 0;
		String qryString = getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS_MAILINBOUNDHHT);
		String modifiedStr1 = null;
		if (isOracleDataSource()) {
			modifiedStr1 = "LISTAGG(RTGCARCOD || ' ' || RTGFLTNUM || ' ' || RTGFLTDAT,',') within GROUP (ORDER BY RTGFLTDAT) ROUTEINFO";
		} else {
			modifiedStr1 = "string_agg ( RTGCARCOD || ' '|| RTGFLTNUM|| ' '|| RTGFLTDAT, ','  ORDER BY RTGFLTDAT) ROUTEINFO";
		}
		qryString = String.format(qryString, modifiedStr1);
		Query qry = getQueryManager().createNativeQuery(qryString);
		qry.setParameter(++idx, mailbagEnquiryFilterVO.getCompanyCode());
		qry.setParameter(++idx, mailbagEnquiryFilterVO.getMailbagId());
		qry.setParameter(++idx, mailbagEnquiryFilterVO.getScanPort());
		qry.setParameter(++idx, mailbagEnquiryFilterVO.getCompanyCode());
		qry.setParameter(++idx, mailbagEnquiryFilterVO.getMailbagId());
		return qry.getSingleResult(new MailbagEnquiryMapper());
	}

	@Override
	public MailbagVO findMailConsumed(MailTransitFilterVO filterVo) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(CLASS_NAME + " : " + "findMailConsumed" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAIL_CONSUMED_DATA);
		ZonedDateTime date = localDateUtil.getLocalDate(filterVo.getAirportCode(), false);
		int index = 0;
		qry.setParameter(++index, Integer.parseInt(
				(filterVo.getFromDate().format(MailConstantsVO.YYYY_MM_DD).substring(0, 8))));
		qry.setParameter(++index, Integer.parseInt(
				(filterVo.getToDate().format(MailConstantsVO.YYYY_MM_DD).substring(0, 8))));
		qry.setParameter(++index, filterVo.getAirportCode());
		qry.setParameter(++index, filterVo.getSegmentDestination());
		qry.setParameter(++index, filterVo.getCarrierCode());
		MailbagVO mailbagVO = qry.getSingleResult(new MailConsumedMapper());
		log.debug(CLASS_NAME + " : " + "findMailConsumed" + " Exiting");
		return mailbagVO;
	}
	public ConsignmentDocumentVO generateConsignmentSummaryReport(ConsignmentFilterVO consignmentFilterVO)
			throws SystemException {
		log.debug(CLASS_NAME, "generateConsignmentSummaryReport");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Query query = getQueryManager().createNamedNativeQuery(
				GENERATE_CONSIGNMENT_DETAILS_SUMMARY_REPORT);
		int index = 0;
		query.setParameter(++index, consignmentFilterVO.getCompanyCode());
		query.setParameter(++index, consignmentFilterVO.getConsignmentNumber());
		query.setParameter(++index, consignmentFilterVO.getPaCode());
		query.setParameter(++index, consignmentFilterVO.getCompanyCode());
		query.setParameter(++index, consignmentFilterVO.getConsignmentNumber());
		query.setParameter(++index, consignmentFilterVO.getPaCode());
		List<ConsignmentDocumentVO> consignmentDocumentVOs =  query.getResultList(new ConsignmentSummaryReportsMultiMapper());
		if (consignmentDocumentVOs!= null && !consignmentDocumentVOs.isEmpty()) {
			 consignmentDocumentVO = consignmentDocumentVOs.get(0);
		}
		return consignmentDocumentVO;
	}

	public 	TransferManifestVO  generateTransferManifestReport(String companyCode,String transferManifestId)
			throws SystemException,PersistenceException{
		log.debug(CLASS_NAME + " : " + "generateTransferManifestReport" + " Entering");
		TransferManifestVO transferManifestVO = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_TRANSFER_MANIFEST_DSN_DETAILS);
		int index=0;
		qry.setParameter(++index,companyCode);
		qry.setParameter(++index,transferManifestId);
		List<TransferManifestVO> transferManifestVOs =qry.getResultList(new ListTransferManifestDSNMultiMapper());
		if(transferManifestVOs!=null && transferManifestVOs.size()>0){
			transferManifestVO=transferManifestVOs.get(0);
		}
		return transferManifestVO;
	}

    public ConsignmentDocumentVO generateConsignmentSecurityReportDtls(ConsignmentFilterVO filterVO) {
        log.debug(CLASS_NAME, "generateConsignmentSecurityReportDtls");
        var query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_SCREENING_DETAILS);

        int index = 0;
        query.setParameter(++index, filterVO.getCompanyCode());
        query.setParameter(++index, filterVO.getConsignmentNumber());
        query.setParameter(++index, filterVO.getPaCode());
        query.setParameter(++index, filterVO.getCompanyCode());
        query.setParameter(++index, filterVO.getConsignmentNumber());
        query.append(" AND DTL.ARPCOD =  '"+filterVO.getAirportCode()+"'");
        var consignmentDocumentVOs = query.getResultList(new ListConsignmentScreeningMapper());
        if (CollectionUtils.isNotEmpty(consignmentDocumentVOs)) {
            return consignmentDocumentVOs.get(0);
        }

        return null;
    }
	public Collection<MailbagVO> findMailTagDetails(
			Collection<MailbagVO> mailbagVOs) throws SystemException,
			PersistenceException {
		log.debug(CLASS_NAME, "findMailTagDetails");
		Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailbagVO.setMailbagId(mailbagVO.getMailbagId());
			ZonedDateTime currentDate = localDateUtil.getLocalDate(null, false);
			mailbagVO.setCurrentDateStr(currentDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")));
			Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILTAG_DETAILS);
			int idx = 0;
			MailbagVO mailVO = new MailbagVO();
			qry.setParameter(++idx, mailbagVO.getCompanyCode());
			qry.setParameter(++idx, mailbagVO.getMailbagId());
			mailVO = qry.getSingleResult(new MailTagDetailsMapper());
			if(mailVO !=null)
			{
				mailVOs.add(mailVO);
			}
			else
			{
				mailVOs.add(mailbagVO);
			}
		}
		log.debug(CLASS_NAME, "findMailTagDetails");
		return mailVOs;
	}
	public ConsignmentDocumentVO generateConsignmentReport(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException
	{
		log.debug(CLASS_NAME, "findConsignmentDocumentDetails");
		ConsignmentDocumentVO consignmentDocumentVO = null;
		List<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
		int idx = 0;
		if(MailConstantsVO.CONSIGNMENT_TYPE_AV7.equals(consignmentFilterVO.getConType())){
			consignmentDocumentVO = new ConsignmentDocumentVO();
			Query query = getQueryManager().createNamedNativeQuery(GENERATE_CONSIGNMENT_DETAILS_REPORT_FOR_AV7);
			query.setParameter(++idx, consignmentFilterVO.getCompanyCode());
			query.setParameter(++idx, consignmentFilterVO.getConsignmentNumber());
			query.setParameter(++idx, consignmentFilterVO.getPaCode());
			consignmentDocumentVOs = query.getResultList(new ConsignmentDetailsReportMapper());

		}else{
			Query query = getQueryManager().createNamedNativeQuery(GENERATE_CONSIGNMENT_DOCUMENT_DETAILS_REPORT);
			query.setParameter(++idx, consignmentFilterVO.getCompanyCode());
			query.setParameter(++idx, consignmentFilterVO.getConsignmentNumber());
			query.setParameter(++idx, consignmentFilterVO.getPaCode());
			consignmentDocumentVOs = query
					.getResultList(new ConsignmentReportDtlsMultimapper());

		}
		if (consignmentDocumentVOs != null && consignmentDocumentVOs.size() > 0) {
			consignmentDocumentVO = consignmentDocumentVOs.get(0);
		}
		return consignmentDocumentVO;
	}
	
	 public String findRoutingDetails(String companyCode,long malseqnum) throws SystemException {
		log.debug("MailTrackingDefaultsSqlDAO" + "findRoutingDetails"); 
		String pouValues = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_ROUTING_DETAILS);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, malseqnum);
		List<String> pouVal = query.getResultList(new Mapper<String>() {
			public String map(ResultSet rs) throws SQLException {
				log.debug("Mapper", "map");
				return rs.getString("POU");
			}
		});
		StringBuilder sb = new StringBuilder();
		if( pouVal!=null && !pouVal.isEmpty()){
			Iterator<String> pouValue = pouVal.iterator();
			while (pouValue.hasNext()) {
				sb.append(pouValue.next() + ",");
			}
			pouValues = sb.toString();
			pouValues = pouValues.substring(0, pouValues.length() - 1);
		}
		return pouValues;

	}
	public Collection<ConsignmentDocumentVO> findTransferManifestConsignmentDetails(TransferManifestVO transferManifestVO)
			throws SystemException {
		log.debug(CLASS_NAME+ LOG_DELIMITER, "findTransferManifestConsignmentDetails");
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_TRANSFER_MANIFEST_CONSIGNMENTDTL);
		qry.setParameter(++index, transferManifestVO.getCompanyCode());
		qry.setParameter(++index, transferManifestVO.getTransferManifestId());
		consignmentDocumentVOs = qry.getResultList(new TransferManifestConsignmentDetail());
		log.debug(CLASS_NAME+ LOG_DELIMITER, "findTransferManifestConsignmentDetails");
		return consignmentDocumentVOs;
	}
	public Collection<ConsignmentDocumentVO> generateCN46ConsignmentReport(ConsignmentFilterVO consignmentFilterVO)
			throws SystemException {
		log.debug(CLASS_NAME, "generateCN46ConsignmentReport");
		ConsignmentDocumentVO consignmentDocumentVO = null;
		List<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(
				GENERATE_CN46_CONSIGNMENT_DOCUMENT_DETAILS_REPORT);
		query.setParameter(1, consignmentFilterVO.getCompanyCode());
		query.setParameter(2, consignmentFilterVO.getTransferManifestId());
		consignmentDocumentVOs = query.getResultList(new ManifestCN46ReportMultiMapper());
		log.debug(CLASS_NAME,LOG_DELIMITER, consignmentDocumentVO);
		return consignmentDocumentVOs;
	}
	public Collection<ConsignmentDocumentVO> generateCN46ConsignmentSummaryReport(ConsignmentFilterVO consignmentFilterVO)
			throws SystemException {
		log.debug(CLASS_NAME, "generateCN46ConsignmentSummaryReport");
		ConsignmentDocumentVO consignmentDocumentVO = null;
		List<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(GENERATE_CN46_CONSIGNMENT_DETAILS_SUMMARY_REPORT);
		query.setParameter(1, consignmentFilterVO.getCompanyCode());
		query.setParameter(2, consignmentFilterVO.getTransferManifestId());
		consignmentDocumentVOs = query
				.getResultList(new ManifestCN46SummaryReportMultiMapper());

		return consignmentDocumentVOs;
	}
	public HashMap<String, String> findPAForShipperbuiltULDs(
			Collection<UldResditVO> uldResditVOs, boolean isFromCardit)
			throws SystemException,PersistenceException{
		log .debug( "MailTrackingDefaultsSqlDAO" , "findPAForShipperbuiltULDs" );
		HashMap<String,String> paMap = new HashMap<String,String>();
		if (uldResditVOs == null || uldResditVOs.size() <= 0)
		{
			return paMap;
		}
		int idx = 0;
		boolean first = true;
		UldResditVO firstULDResditVO = ((ArrayList<UldResditVO>)uldResditVOs).get(0);
		Query query = null;
		if (isFromCardit){
			query = getQueryManager().createNamedNativeQuery(FIND_PA_FOR_SHIPPERBUILT_ULD_FRMCDT );
			query.setParameter(++idx, firstULDResditVO.getCompanyCode());
			query.append(" AND	CON.CONNUM IN ( ?");
		}else{
			query = getQueryManager().createNamedNativeQuery(FIND_PA_FOR_SHIPPERBUILT_ULD );
			query.setParameter(++idx, firstULDResditVO.getCompanyCode());
			query.setParameter(++idx, firstULDResditVO.getCarrierId());
			query.setParameter(++idx, firstULDResditVO.getFlightNumber());
			query.setParameter(++idx, firstULDResditVO.getFlightSequenceNumber());
			query.setParameter(++idx, firstULDResditVO.getSegmentSerialNumber());
			query.append(" AND	MST.CONNUM IN ( ?");
		}
		query.setSensitivity(true);
		for(UldResditVO uldResditVO: uldResditVOs){
			if(first) {
				first = false;
				query.setParameter(++idx, uldResditVO.getUldNumber());
			}else {
				query.append(",? ");
				query.setParameter(++idx, uldResditVO.getUldNumber());
			}
		}
		query.append(" ) ");
		paMap = (HashMap<String,String>)query.getResultList(new PADetailsMultiMapper()).get(0);
		log .debug ( "MailTrackingDefaultsSqlDAO" , "findPAForShipperbuiltULDs" );
		return paMap;
	}
	public Collection<MailResditVO> findResditFlightDetailsForMailbag(
			MailResditVO mailResditVO)
			throws SystemException, PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO", "findResditFlightDetailsForMailbag");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILRESDIT_FLIGHTDETAILS);
		int idx = 0;
		qry.setParameter(++idx, mailResditVO.getCompanyCode());
		qry.setParameter(++idx, mailResditVO.getMailId());
		qry.setParameter(++idx, mailResditVO.getEventCode());
		log.debug("MailTrackingDefaultsSqlDAO", "findResditFlightDetailsForMailbag");
		return qry.getResultList(new MailResditMapper());
	}

	@Override
	public Page<ContainerVO> findContainerDetailsForReassignment(SearchContainerFilterVO searchContainerFilterVO, int pageNumber) {
		StringBuilder queryStringBuilder = new StringBuilder(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
        String queryString = null;
		if(SEARCHMODE_DEST.equals(searchContainerFilterVO.getSearchMode())){
			queryString = getQueryManager().getNamedNativeQueryString(FIND_CONTAINER_DETAILS_FOR_REASSIGN_CARRIER_LEVEL);

		} else{
			queryString = getQueryManager().getNamedNativeQueryString(FIND_CONTAINER_DETAILS_FOR_REASSIGN_FLIGHT_LEVEL);

		}
		queryStringBuilder.append(queryString);
		if(searchContainerFilterVO.getPartnerCarriers()!=null && !searchContainerFilterVO.getPartnerCarriers().isEmpty()){
			ArrayList<String> carriervalue =searchContainerFilterVO.getPartnerCarriers();
			queryStringBuilder.append("AND MST. FLTCARCOD IN (");
			for(String s: carriervalue){
				queryStringBuilder.append("'");
				queryStringBuilder.append(s);
				queryStringBuilder.append("'");
				queryStringBuilder.append(",");
			}
			int i =	 queryStringBuilder.lastIndexOf(",");
			queryStringBuilder.deleteCharAt(i);

			queryStringBuilder.append(")");
		}
		queryStringBuilder.append(getQueryManager().getNamedNativeQueryString(FIND_CONTAINER_DETAILS_FOR_REASSIGN_GROUPBY));
		PageableNativeQuery pgNativeQuery = new PageableNativeQuery<ContainerVO>(searchContainerFilterVO.getPageSize(),-1,
				queryStringBuilder.toString(), new ContainerDetailsMapper(),PersistenceController.getEntityManager().currentSession());
		int index = 0;
		pgNativeQuery.setParameter(++index, searchContainerFilterVO.getCompanyCode());
		pgNativeQuery.setParameter(++index,searchContainerFilterVO.getContainerNumber());
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(1);



	}@Override
	public Collection<SecurityScreeningValidationVO> checkForSecurityScreeningValidation(SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO) {
		String query = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_CHECK_FOR_SECURITYSCREENING_VALIDATIONS);
		Query qry = null;
		qry = new SecurityScreeningValidationFilterQuery(query, securityScreeningValidationFilterVO);
		return qry.getResultList(new SecurityScreeningValidationsMapper());
	}
	public List<TransferManifestVO> findTransferManifestDetailsForTransfer(
			String companyCode, String tranferManifestId) throws SystemException, PersistenceException {
		log.debug(CLASS_NAME, "findTransferManifestDetails");
		TransferManifestVO transferManifestVO = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_TRANSFER_MANIFEST_DSN_DETAILS);
		int index = 0;
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, tranferManifestId);
		List<TransferManifestVO> transferManifestVOs = qry.getResultList(new ListTransferManifestDSNMultiMapper());

		return transferManifestVOs;
	}	

public Collection<MailStatusVO> generateMailStatusReport(MailStatusFilterVO mailStatusFilterVO)
			throws SystemException,PersistenceException {
		log.debug(CLASS_NAME, "generateMailStatusReport");
		Query qry = null;
		String baseQry = null;
		List<MailStatusVO> mailStatusVOs = new ArrayList<MailStatusVO>();

		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.EXPECTED_MAIL)) {

			log.debug(CLASS_NAME,"generateMailStatusReport-->EXPECTED_MAIL_CARDIT");
			qry = new ExpectedMailCarditFilterQuery(mailStatusFilterVO);

			List<MailStatusVO> mailStatusCarditVOs = new ArrayList<MailStatusVO>();
			mailStatusCarditVOs = qry.getResultList(new MailStatusReportMapper());

			if (mailStatusCarditVOs != null && mailStatusCarditVOs.size() > 0) {
				mailStatusVOs.addAll(mailStatusCarditVOs);
			}


		}

		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.EXPECTED_MAIL_CARDIT)) {
			log.debug(CLASS_NAME,"generateMailStatusReport-->EXPECTED_MAIL_CARDIT");
			qry = new ExpectedMailCarditFilterQuery(mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}


		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.EXPECTED_MAIL_TRANSHIPS)) {

			baseQry = getQueryManager().getNamedNativeQueryString(MAILS_FOR_TRANSHIPMENT_FLIGHT);
			qry = new ExpectedMailTranshipFilterQuery(mailStatusFilterVO,
					baseQry);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}


		if (MailConstantsVO.MAIL_WITHOUT_CARDIT.equals(mailStatusFilterVO.getCurrentStatus())) {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAIL_WITHOUT_CARDITS);
			qry = new MailsWithoutCarditFilterQuery(baseQry, mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		if (mailStatusFilterVO.getCurrentStatus().equals( MailConstantsVO.MAIL_ACCEPTED_NOT_UPLIFTED)) {

			if (mailStatusFilterVO.getCarrierid() > 0
					&& mailStatusFilterVO.getFlightCarrierid() == 0) {
				baseQry = getQueryManager().getNamedNativeQueryString(MAILS_ACCEPTED_NOT_UPLIFTED_CARRIER);
			} else {
				baseQry = getQueryManager().getNamedNativeQueryString(MAILS_ACCEPTED_NOT_UPLIFTED_FLIGHT);
			}
			qry = new MailsAcceptedButNotUpliftedFilterQuery(mailStatusFilterVO, baseQry);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}


		if (mailStatusFilterVO.getCurrentStatus().equals( MailConstantsVO.MAIL_NOT_UPLIFTED)) {

			if (mailStatusFilterVO.getCarrierid() > 0
					&& mailStatusFilterVO.getFlightCarrierid() == 0) {
				baseQry = getQueryManager().getNamedNativeQueryString(MAILS_NOTUPLIFTED_CARRIER);
			} else {
				baseQry = getQueryManager().getNamedNativeQueryString(MAILS_IN_FLIGHT);
			}
			qry = new MailsNotUpliftedFilterQuery(mailStatusFilterVO, baseQry);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.MAIL_UPLIFTED_WITHOUT_CARDIT)
				|| mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.MAIL_UPLIFTED_NOT_DELIVERED)) {
			baseQry = getQueryManager().getNamedNativeQueryString(MAILS_IN_FLIGHT);
			qry = new MailsUpliftedtFilterQuery(mailStatusFilterVO, baseQry);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.MAIL_CARDIT_NOT_POSSESSED)) {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAILSTATUS_CARDIT_NOT_ACCEPTED);
			qry = new CarditNotAcceptedFilterQuery(baseQry, mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		if (MailConstantsVO.MAIL_ARRIVED_NOT_DELIVERED.equals(mailStatusFilterVO.getCurrentStatus())) {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAIL_ARRIVED_NOT_DELIVERED);
			qry = new MailsArrivedNotDeliveredFilterQuery(baseQry,
					mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.MAIL_DELIVERED)) {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAILSTATUS_MAIL_DELIVERED);
			qry = new MailsDeliveredFilterQuery(baseQry, mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}
		
		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.MAIL_DELIVERED_WITHOUT_CARDIT)) {
			baseQry = getQueryManager().getNamedNativeQueryString( FIND_MAILSTATUS_MAIL_DELIVERED_WITHOUT_CARDIT);
			qry = new MailsDeliveredFilterQuery(baseQry, mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		log.debug(CLASS_NAME, "####^^^^##### Returning mailStatusVOs : in SQLDAO",
				mailStatusVOs);

		return mailStatusVOs;

	}
	
	public Collection<DailyMailStationReportVO> generateDailyMailStationReport(DailyMailStationFilterVO filterVO)
			throws SystemException {
		String baseQry = null;
		Query qry=null;
		baseQry = getQueryManager().getNamedNativeQueryString(MAILTRACKING_DEFAULTS_GENERATE_DAILYMAILSTATION_REPORT);
		qry = new DailyMailStationFilterQuery(baseQry,filterVO);
		Collection<DailyMailStationReportVO> reportVOS=null;
		reportVOS= qry.getResultList(new DailyMailStationMapper());
		return reportVOS;
	}
	public Collection<MailHandedOverVO> generateMailHandedOverReport(MailHandedOverFilterVO mailHandedOverFilterVO) throws SystemException, PersistenceException{
		log.debug( "generateMailHandedOverReport");
		String flightBaseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAIL_HANDED_OVER_TOFLIGHT);
		String carrierBaseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAIL_HANDED_OVER_TOCARRIER);
		Query query=new MailHandedOverFilterQuery(mailHandedOverFilterVO,flightBaseQuery,carrierBaseQuery);
		return query.getResultList(new MailHandedOverReportMapper());
	}
public Collection<DamagedMailbagVO> findDamageMailReport(DamageMailFilterVO damageMailFilterVO) throws SystemException, PersistenceException {
		log.debug(CLASS_NAME, "findDamageMailReport");
		String baseQuery = getQueryManager().getNamedNativeQueryString(FIND_DAMAGE_MAILBAG_REPORT);
		Query query = new DamageMailReportFilterQuery(damageMailFilterVO,baseQuery);
		return query.getResultList(new DamageMailReportMapper());
	}

	public MailManifestVO findImportManifestDetails(OperationalFlightVO operationalFlightVo) throws SystemException,PersistenceException{
		MailManifestVO mailManifestV0 = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_IMPORT_MANIFEST_DETAILS);
		int index=0;
		qry.setParameter(++index, operationalFlightVo.getCompanyCode());
		qry.setParameter(++index, operationalFlightVo.getCarrierId());
		qry.setParameter(++index, operationalFlightVo.getFlightNumber());
		qry.setParameter(++index, operationalFlightVo.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVo.getPou());
		List<MailManifestVO> mailManifestVOs =qry.getResultList(new ImportManifestReportMultiMapper());
		if(mailManifestVOs!=null && mailManifestVOs.size()>0){
			mailManifestV0=mailManifestVOs.get(0);
		}
		return mailManifestV0;
	}

}
