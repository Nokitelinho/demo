package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.*;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailboxIdVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.*;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClient;
import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClients;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.component.proxy.MailOperationsMRAProxy;
import com.ibsplc.neoicargo.mail.component.proxy.*;
import com.ibsplc.neoicargo.mail.exception.ContainerAssignmentException;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.exception.SharedProxyException;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.mapper.ClassicVOConversionMapper;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.neoicargo.mail.vo.converter.MailOperationsVOConverter;
import com.ibsplc.neoicargo.mailmasters.MailTrackingDefaultsBI;
import com.ibsplc.neoicargo.mailmasters.model.MailEventModel;
import com.ibsplc.neoicargo.mailmasters.model.MailboxIdModel;
import com.ibsplc.neoicargo.masters.area.airport.AirportBusinessException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/** 
 * The controller class for ALL ResditEvents. All resdit flagging is through this class
 * @author A-1739
 */
@Component
@Slf4j
@RegisterJAXRSClients(value={@RegisterJAXRSClient(clazz = MailTrackingDefaultsBI.class, targetService = "neo-mailmasters-business") })
public class ResditController {

	@Autowired
	private SharedAirlineProxy sharedAirlineProxy;
	@Autowired
	private FlightOperationsProxy flightOperationsProxy;
	@Autowired
	private SharedDefaultsProxy sharedDefaultsProxy;
	@Autowired
	private SharedAreaProxy sharedAreaProxy;
	@Autowired
	private MsgBrokerMessageProxy msgBrokerMessageProxy;
	@Autowired
	private LocalDate localDateUtil;
	@Autowired
	private ContextUtil contextUtil;
	@Autowired
	private MailOperationsMapper mailOperationsMapper;
	@Autowired
	private ClassicVOConversionMapper classicVOConversionMapper_;
	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;
	@Autowired
	private MailController mailController;
	@Autowired
	private MailOperationsMRAProxy mailOperationsMRAProxy;
	@Autowired
	MailTrackingDefaultsBI mailMasterApi;
	private static final String ACP_ULDS = "ACPULDS";
	private static final String CLASS = "ResditController";
	private static final String PA_ULDS = "PAULDS";
	private static final String CARR_ULDS = "CARRULDS";
	private static final String PA_MAILS = "PAMAILS";
	private static final String CARR_MAILS = "CARRMAILS";
	private static final String HAS_DEP = "HASDEP";
	private static final String IS_HNDOVER = "ISHNDOVR";
	private static final String IS_FLTASG = "ISFLTASG";
	private static final String NEWARR_MAILS = "NEWMAILS";
	private static final String OLIN_MAILS = "ONLINEMAILS";
	private static final String DLVD_ULDS = "DLVD_ULDS";
	private static final String DLVD_MAILS = "DLVDMAILS";
	private static final String RETN_MAILS = "RETNMAILS";
	private static final String RESDIT_TO_POST_US = "US101";
	private static final String RESDIT_EXD_AIRLINE_LIST = "mailtracking.defaults.resditcopyexcludeairlines";
	private static final String RESDIT_TO_AIRLINE_LIST = "mailtracking.defaults.resdittoairlinelist";
	private static final String RESDIT_TO_AIRLINE_AA = "AA";
	private static final String RESDIT_TO_AIRLINE_JL = "JL";
	public static final String IS_COTERMINUS_CONFIGURED = "mailtracking.defaults.iscoterminusconfigured";
	private static final String AUTOARRIVALOFFSET = "mail.operations.autoarrivaloffset";
	private static final String AUTOARRIVALFUNCTIONPOINTS = "mail.operations.autoarrivalfunctionpoints";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";

	/** 
	* May 23, 2007, a-1739
	* @param mailbagVO
	* @param txnId
	* @throws SystemException
	*/
	public void updateResditEventTimes(MailbagVO mailbagVO, String txnId) {
		log.debug(CLASS + " : " + "updateResditEventTimes" + " Entering");
		if (checkForResditConfig()) {
			ResditTransactionDetailVO txnDetailVO = findResditConfigurationForTxn(mailbagVO.getCompanyCode(),
					mailbagVO.getCarrierId(), txnId);
			if (txnDetailVO != null) {
				if (MailConstantsVO.FLAG_YES.equals(txnDetailVO.getReceivedResditFlag())) {
					updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_RECEIVED);
				}
				if (MailConstantsVO.FLAG_YES.equals(txnDetailVO.getLoadedResditFlag())) {
					updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_LOADED);
				}
				if (MailConstantsVO.FLAG_YES.equals(txnDetailVO.getUpliftedResditFlag())) {
					updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_UPLIFTED);
				}
				if (MailConstantsVO.FLAG_YES.equals(txnDetailVO.getHandedOverResditFlag())) {
					updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_HANDOVER_ONLINE);
				}
				if (MailConstantsVO.FLAG_YES.equals(txnDetailVO.getAssignedResditFlag())) {
					updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_ASSIGNED);
				}
			}
		} else {
			if (MailConstantsVO.TXN_ACP.equals(txnId)) {
				updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_HANDOVER_RECEIVED);
				updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_RECEIVED);
				updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_ASSIGNED);
				updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_HANDOVER_OFFLINE);
				updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_UPLIFTED);
			} else if (MailConstantsVO.TXN_ARR.equals(txnId)) {
				updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_HANDOVER_RECEIVED);
				updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_RECEIVED);
				updateResditTimeForEvent(mailbagVO, MailConstantsVO.RESDIT_DELIVERED);
			}
		}
		log.debug(CLASS + " : " + "updateResditEventTimes" + " Exiting");
	}

	/** 
	* TODO Purpose May 23, 2007, a-1739
	* @param mailbagVO
	* @throws SystemException
	*/
	private void updateResditTimeForEvent(MailbagVO mailbagVO, String resditEvent) {
		List<MailResdit> mailResdits = MailResdit.findMailResditsForEvent(mailbagVO, resditEvent);
		if (mailResdits != null && mailResdits.size() > 0) {
			for (MailResdit mailResdit : mailResdits) {
				mailResdit.setEventDate(mailbagVO.getScannedDate().toLocalDateTime());
				mailResdit.setUtcEventDate(localDateUtil.toUTCTime(mailbagVO.getScannedDate()).toLocalDateTime());
			}
		}
	}

	/** 
	* TODO Purpose Feb 2, 2007, A-1739
	* @return
	* @throws SystemException
	*/
	private boolean checkForResditConfig() {
		log.debug(CLASS + " : " + "checkForResditConfig" + " Entering");
		Collection<String> paramNames = new ArrayList<String>();
		paramNames.add(MailConstantsVO.RESDIT_CONFIG_CHECK);
		Map<String, String> paramValMap = null;
		try {
			paramValMap = neoMastersServiceUtils.findSystemParameterByCodes(paramNames);
		} catch (BusinessException e) {
			return false;
		}
		log.debug(CLASS + " : " + "checkForResditConfig" + " Exiting");
		return MailConstantsVO.FLAG_YES.equals(paramValMap.get(MailConstantsVO.RESDIT_CONFIG_CHECK));
	}

	/** 
	* TODO Purpose Feb 5, 2007, A-1739
	* @param companyCode
	* @param carrierId
	* @param txnId
	* @return
	* @throws SystemException
	*/
	private ResditTransactionDetailVO findResditConfigurationForTxn(String companyCode, int carrierId, String txnId) {
		log.debug(CLASS + " : " + "findResditConfigurationForTxn" + " Entering");
		return ResditConfiguration.findResditConfigurationForTxn(companyCode, carrierId, txnId);
	}

	/** 
	* @author SAP15
	* @param mailResditVOs
	* @throws SystemException
	*/
	private void stampResdits(Collection<MailResditVO> mailResditVOs) {
		if (mailResditVOs == null || mailResditVOs.size() <= 0) {
			return;
		}
		String preCheckEnabled = findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED);
		for (MailResditVO mailResditVO : mailResditVOs) {
			if (MailConstantsVO.FLAG_YES.equals(preCheckEnabled) && !canStampResdits(mailResditVO)) {
				continue;
			}
			new MailResdit(mailResditVO);
		}
	}

	/**
	* @param eventCode
	* @return
	* @throws SystemException
	*/
	private Collection<MailResditVO> canFlagResditForEvents(String eventCode, Collection<MailResditVO> mailResditVOs,
			Collection<MailbagVO> mailbagVOs) {
		log.debug(CLASS + " : " + "can FlagResditForEvent" + " Entering");
		if (mailResditVOs == null || mailResditVOs.size() <= 0) {
			return null;
		}
		boolean canFlag = false;
		HashMap<String, MailbagVO> mailbagVOMap = new HashMap<String, MailbagVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailbagVOMap.put(mailbagVO.getMailbagId(), mailbagVO);
			for (MailResditVO resditVO : mailResditVOs) {
				if (resditVO.getMailId() != null && resditVO.getMailId().equals(mailbagVO.getMailbagId()))
					resditVO.setMailSequenceNumber(
							mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
									: Mailbag.findMailBagSequenceNumberFromMailIdr(resditVO.getMailId(),
											resditVO.getCompanyCode()));
			}
		}
		Collection<MailResditVO> mailResditVOsForReturn = new ArrayList<MailResditVO>();
		Collection<MailResditVO> mailResditVOsFirst = new ArrayList<MailResditVO>();
		Collection<MailResditVO> mailResditVOsSecond = new ArrayList<MailResditVO>();
		MailbagVO mailbagVO = null;
		for (MailResditVO mailResditVO : mailResditVOs) {
			mailResditVO.setEventCode(eventCode);
			if (MailConstantsVO.RESDIT_ASSIGNED.equals(mailResditVO.getEventCode())
					|| MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(mailResditVO.getEventCode())
					|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailResditVO.getEventCode())
					|| MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(mailResditVO.getEventCode())) {
				mailbagVO = mailbagVOMap.get(mailResditVO.getMailId());
				if (mailbagVO != null) {
					if (MailConstantsVO.OPERATION_INBOUND.equals(mailbagVO.getOperationalStatus())
							&& MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
						mailResditVOsForReturn.add(mailResditVO);
					} else {
						mailResditVOsFirst.add(mailResditVO);
					}
				}
			} else {
				mailResditVOsSecond.add(mailResditVO);
			}
		}
		Collection<MailResditVO> fetchedMailResditVOs = null;
		StringBuilder keyBuilder = null;
		if (mailResditVOsFirst != null && mailResditVOsFirst.size() > 0) {
			HashMap<String, Collection<MailResditVO>> eventResditsMap = MailResdit
					.findResditFlightDetailsForMailbagEvents(mailResditVOsFirst);
			for (MailResditVO malResditVO : mailResditVOsFirst) {
				keyBuilder = new StringBuilder(malResditVO.getMailId()).append(malResditVO.getEventAirport())
						.append(malResditVO.getEventCode());
				fetchedMailResditVOs = eventResditsMap.get(keyBuilder.toString());
				if (fetchedMailResditVOs == null || fetchedMailResditVOs.size() <= 0) {
					mailResditVOsForReturn.add(malResditVO);
				} else {
					canFlag = true;
					for (MailResditVO fetchedMailResditVO : fetchedMailResditVOs) {
						if (fetchedMailResditVO.getFlightNumber() != null
								&& fetchedMailResditVO.getFlightNumber().equals(malResditVO.getFlightNumber())
								&& fetchedMailResditVO.getCarrierId() == malResditVO.getCarrierId()
								&& fetchedMailResditVO.getFlightSequenceNumber() == malResditVO
										.getFlightSequenceNumber()
								&& fetchedMailResditVO.getSegmentSerialNumber() == malResditVO
										.getSegmentSerialNumber()) {
							if ((MailConstantsVO.RESDIT_UPLIFTED.equals(malResditVO.getEventCode())
									|| MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(malResditVO.getEventCode()))
									&& MailConstantsVO.FLAG_NO.equals(fetchedMailResditVO.getResditSentFlag())
									&& MailConstantsVO.FLAG_YES.equals(fetchedMailResditVO.getProcessedStatus())) {
								MailResditPK mailResditPK = getMailResditPK(fetchedMailResditVO);
								MailResdit mailResdit;
								try {
									mailResdit = MailResdit.find(mailResditPK);
									mailResdit.remove();
								} catch (FinderException e) {
									log.info("" + " error msg \n\n " + " " + e.getMessage());
								}
							} else {
								canFlag = false;
								break;
							}
						}
						if (MailConstantsVO.FLAG_NO.equals(fetchedMailResditVO.getResditSentFlag())
								&& !MailConstantsVO.FLAG_YES.equals(fetchedMailResditVO.getProcessedStatus())) {
							MailResditPK mailResditPK = getMailResditPK(fetchedMailResditVO);
							MailResdit mailResdit;
							try {
								mailResdit = MailResdit.find(mailResditPK);
								mailResdit.remove();
							} catch (FinderException e) {
								log.info("" + " error msg \n\n " + " " + e.getMessage());
							}
						}
					}
					if (canFlag) {
						mailResditVOsForReturn.add(malResditVO);
					}
				}
			}
		}
		if (mailResditVOsSecond != null && mailResditVOsSecond.size() > 0) {
			for (MailResditVO malResditVO : mailResditVOsSecond) {
				boolean isResditExisting = MailResdit.checkResditExists(malResditVO, false);
				if (MailConstantsVO.RESDIT_RECEIVED.equals(malResditVO.getEventCode())) {
					log.debug("THE STATUS IS RECEIVED");
					if (!isResditExisting) {
						mailResditVOsForReturn.add(malResditVO);
					}
				} else {
					if (MailConstantsVO.RESDIT_LOADED.equals(malResditVO.getEventCode())) {
						boolean isResditExistingFromReassign = MailResdit.checkResditExistsFromReassign(malResditVO,
								false);
						if (!isResditExistingFromReassign) {
							mailResditVOsForReturn.add(malResditVO);
						} else {
							mailResditVOsForReturn.add(malResditVO);
						}
					} else {
						if (!isResditExisting) {
							mailResditVOsForReturn.add(malResditVO);
						}
					}
				}
			}
		}
		log.debug(CLASS + " : " + "canFlag ResditForEvent" + canFlag + " Exiting");
		return mailResditVOsForReturn;
	}

	/** 
	* A-2521
	* @param mailResditVO
	* @throws SystemException
	*/
	private MailResditPK getMailResditPK(MailResditVO mailResditVO) {
		log.debug("MailResdit" + " : " + "populatePK" + " Entering");
		log.debug("THE MAILRESDIT VO>>>>>>>>>>" + mailResditVO);
		MailResditPK mailResditPK = new MailResditPK();
		mailResditPK.setCompanyCode(mailResditVO.getCompanyCode());
		mailResditPK
				.setMailSequenceNumber(mailResditVO.getMailSequenceNumber() > 0 ? mailResditVO.getMailSequenceNumber()
						: findMailSequenceNumber(mailResditVO.getMailId(), mailResditVO.getCompanyCode()));
		mailResditPK.setEventCode(mailResditVO.getEventCode());
		mailResditPK.setSequenceNumber(mailResditVO.getResditSequenceNum());
		log.debug("MailResdit" + " : " + "populatepK" + " Exiting");
		return mailResditPK;
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @param eventAirport
	* @param eventCode
	* @param isResditSent
	* @return
	* @throws SystemException
	*/
	private MailResditVO constructMailResditVO(MailbagVO mailbagVO, String eventAirport, String eventCode,
			boolean isResditSent) {
		log.debug(CLASS + " : " + "constructMail ResditVO" + " Entering");
		String partyIdentifier;
		Page<OfficeOfExchangeVO> exchangeOfficeDetails;
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailResditVO.setMailId(mailbagVO.getMailbagId());
		mailResditVO.setFromDeviationList(mailbagVO.isFromDeviationList());
		if (eventAirport != null) {
			mailResditVO.setEventAirport(eventAirport);
		}
		mailResditVO.setEventCode(eventCode);
		mailResditVO.setCarrierId(mailbagVO.getCarrierId());
		mailResditVO.setFlightNumber(mailbagVO.getFlightNumber());
		mailResditVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		mailResditVO.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
		if (mailResditVO.getSegmentSerialNumber() == 0 && MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)) {
			mailResditVO.setSegmentSerialNumber(1);
		}
		mailResditVO.setPaOrCarrierCode(mailbagVO.getCarrierCode());
		if (isResditSent) {
			mailResditVO.setResditSentFlag(MailConstantsVO.FLAG_YES);
		} else {
			mailResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
		}
		mailResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
		mailResditVO.setUldNumber(mailbagVO.getUldNumber());
		log.debug("Resdit event date construction..mailbagVO.getScannedDate()" + mailbagVO.getScannedDate());
		if (MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)
				|| MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(eventCode)) {
			if (MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)) {
				mailResditVO.setEventDate(findResditEvtDate(mailbagVO, eventAirport));
			} else {
				mailResditVO.setEventDate(mailbagVO.getResditEventDate() != null ? mailbagVO.getResditEventDate()
						: localDateUtil.getLocalDate(eventAirport, true));
			}
		} else if (MailConstantsVO.RESDIT_ARRIVED.equals(eventCode)
				|| MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(eventCode)) {
			mailResditVO.setEventDate(mailbagVO.getResditEventDate() != null ? mailbagVO.getResditEventDate()
					: mailbagVO.getScannedDate() != null ? mailbagVO.getScannedDate()
							: localDateUtil.getLocalDate(eventAirport, true));
		} else {
			mailResditVO.setEventDate(mailbagVO.getScannedDate() != null ? mailbagVO.getScannedDate()
					: localDateUtil.getLocalDate(eventAirport, true));
		}
		String mailboxIdFromConfig = MailMessageConfiguration.findMailboxIdFromConfig(mailbagVO);
		if (mailboxIdFromConfig != null && !mailboxIdFromConfig.isEmpty()) {
			mailResditVO.setMailboxID(mailboxIdFromConfig);
		}
		if (mailbagVO.getMailbagId() != null && mailbagVO.getMailbagId().length() == 29) {
			String paCode = "";
			exchangeOfficeDetails = mailController.findOfficeOfExchange(mailbagVO.getCompanyCode(),
					mailbagVO.getMailbagId().substring(0, 6), 1);
			if (exchangeOfficeDetails != null && !exchangeOfficeDetails.isEmpty()) {
				OfficeOfExchangeVO officeOfExchangeVO = exchangeOfficeDetails.iterator().next();
				Mailbag mailbagToFindPA = null;
				String poaCode = null;
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagPk
						.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
								: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
				try {
					mailbagToFindPA = Mailbag.find(mailbagPk);
				} catch (FinderException e) {
					e.getMessage();
				}
				if (mailbagToFindPA != null && mailbagToFindPA.getPaCode() != null) {
					poaCode = mailbagToFindPA.getPaCode();
				} else {
					poaCode = officeOfExchangeVO.getPoaCode();
				}
				if (officeOfExchangeVO.getAirportCode() != null
						&& (!officeOfExchangeVO.getAirportCode().equals(eventAirport))) {
					log.debug("OOE Airport Code: " + officeOfExchangeVO.getAirportCode());
					ZonedDateTime dspDate = localDateUtil.getLocalDate(eventAirport, true);
					if (mailbagToFindPA != null) {
						dspDate = localDateUtil.getLocalDateTime(mailbagToFindPA.getDespatchDate(), null);
					}
					boolean coTerminusCheck = mailController.validateCoterminusairports(eventAirport,
							officeOfExchangeVO.getAirportCode(), eventCode, poaCode, dspDate);
					log.debug("coTerminusCheck: " + coTerminusCheck);
					log.debug("eventAirport bfre coTerminus Check: " + eventAirport);
					if (coTerminusCheck
							&& mailController.checkReceivedFromTruckEnabled(eventAirport,
									officeOfExchangeVO.getAirportCode(), paCode, dspDate)
							&& MailConstantsVO.FLAG_YES.equals(mailbagVO.getIsFromTruck())) {
						eventAirport = officeOfExchangeVO.getAirportCode();
					}
					log.debug("eventAirport after coTerminus Check: " + eventAirport);
					mailResditVO.setEventAirport(eventAirport);
				}
			}
			if (eventCode == null || (MailConstantsVO.RESDIT_DELIVERED.equals(eventCode)
					|| MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(eventCode))) {
				paCode = mailController.findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),
						mailbagVO.getMailbagId().substring(6, 12));
			} else {
				paCode = mailController.findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),
						mailbagVO.getMailbagId().substring(0, 6));
			}
			if (paCode != null) {
				partyIdentifier = findPartyIdentifierForPA(mailbagVO.getCompanyCode(), paCode);
				mailResditVO.setPartyIdentifier(partyIdentifier);
			}
			mailResditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
					: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		}
		log.debug(CLASS + " : " + "construct MailResditVO" + " Exiting");
		return mailResditVO;
	}

	/** 
	* @author a-1936This method is used to flag the Delivered Resdits for the  Mail Bags
	* @param deliveredMailbags
	* @param airportCode
	* @throws SystemException
	*/
	public void flagDeliveredResditForMailbags(Collection<MailbagVO> deliveredMailbags, String airportCode) {
		log.debug(CLASS + " : " + "flagDeliveredResdit" + " Entering");
		Collection<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.SYSPAR_USPS_ENHMNT);
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		String companyCode = logonAttributes.getCompanyCode();
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		HashMap<String, String> mailPAmap = null;
		HashMap<String, String> exgOfcPAMap = new HashMap<String, String>();
		Collection<MailResditVO> r21MailResditVOs = new ArrayList<MailResditVO>();
		Collection<MailResditVO> r23MailResditVOs = new ArrayList<MailResditVO>();
		String eventCode = null;
		int mailbagSize = deliveredMailbags.size();
		int limit = 999;
		if (mailbagSize > limit) {
			int startIndex = 0;
			int endIndex = limit;
			mailPAmap = new HashMap<>();
			while (startIndex < endIndex) {
				ArrayList<MailbagVO> deliveredMailbags1 = (ArrayList<MailbagVO>) deliveredMailbags;
				mailPAmap.putAll(MailResdit.findPAForMailbags(deliveredMailbags1.subList(startIndex, endIndex)));
				startIndex = endIndex;
				if (endIndex + limit < mailbagSize) {
					endIndex = endIndex + limit;
				} else {
					endIndex = mailbagSize;
				}
			}
		} else {
			mailPAmap = MailResdit.findPAForMailbags(deliveredMailbags);
		}
		String paCode = null;
		String dstPaCode = null;
		MailResditVO mailResditVO = null;
		for (MailbagVO mailbagVO : deliveredMailbags) {
			paCode = exgOfcPAMap.get(mailbagVO.getOoe());
			dstPaCode = exgOfcPAMap.get(mailbagVO.getDoe());
			if (paCode == null || paCode.length() <= 0) {
				paCode = new MailController().findPAForOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
				exgOfcPAMap.put(mailbagVO.getOoe(), paCode);
			}
			if (dstPaCode == null || dstPaCode.length() <= 0) {
				dstPaCode = new MailController().findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),
						mailbagVO.getDoe());
				exgOfcPAMap.put(mailbagVO.getDoe(), dstPaCode);
			}
			mailResditVO = constructMailResditVO(mailbagVO, airportCode, eventCode, false);
			mailResditVO.setPaOrCarrierCode(paCode);
			if (systemParameterMap != null
					&& (MailConstantsVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT)))) {
				if (mailPAmap != null && mailPAmap.size() > 0
						&& mailPAmap.get(mailbagVO.getMailbagId()).equals(MailConstantsVO.USPOST)) {
					eventCode = MailConstantsVO.RESDIT_READYFOR_DELIVERY;
					mailResditVO.setEventCode(eventCode);
					r23MailResditVOs.add(mailResditVO);
				} else {
					eventCode = MailConstantsVO.RESDIT_DELIVERED;
					mailResditVO.setPaOrCarrierCode(dstPaCode);
					mailResditVO.setEventCode(eventCode);
					r21MailResditVOs.add(mailResditVO);
				}
			} else {
				eventCode = MailConstantsVO.RESDIT_DELIVERED;
				mailResditVO.setPaOrCarrierCode(dstPaCode);
				mailResditVO.setEventCode(eventCode);
				r21MailResditVOs.add(mailResditVO);
			}
		}
		if (r23MailResditVOs != null && r23MailResditVOs.size() > 0) {
			r23MailResditVOs = canFlagResditForEvents(eventCode, r23MailResditVOs, deliveredMailbags);
			stampResdits(r23MailResditVOs);
		}
		if (r21MailResditVOs != null && r21MailResditVOs.size() > 0) {
			r21MailResditVOs = canFlagResditForEvents(eventCode, r21MailResditVOs, deliveredMailbags);
			stampResdits(r21MailResditVOs);
		}
		log.debug(CLASS + " : " + "flagDeliveredResdit" + " Exiting");
	}

	private long findMailSequenceNumber(String mailIdr, String companyCode) {
		return Mailbag.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
	}

	/** 
	* Utilty for finding syspar Mar 23, 2007, A-1739
	* @param syspar
	* @return
	* @throws SystemException
	*/
	private String findSystemParameterValue(String syspar) {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/** 
	* Method to find Resdit Event date from flight info
	* @param mailbagVO
	* @param eventAirport
	* @return resditEvtDate
	* @throws SystemException
	*/
	public ZonedDateTime findResditEvtDate(MailbagVO mailbagVO, String eventAirport) {
		log.debug("ResditController===========>>>" + " : " + "findResditEvtDate " + " Entering");
		ZonedDateTime resditEvtDate = localDateUtil.getLocalDate(eventAirport, true);
		boolean isEvtDateSet = false;
		try {
			Collection<FlightSegmentSummaryVO> segmentSummaryVOs = flightOperationsProxy.findFlightSegments(
					mailbagVO.getCompanyCode(), mailbagVO.getCarrierId(), mailbagVO.getFlightNumber(),
					mailbagVO.getFlightSequenceNumber());
			FlightSegmentSummaryVO segmentSummaryVO = null;
			if (segmentSummaryVOs != null && segmentSummaryVOs.size() > 0) {
				for (FlightSegmentSummaryVO segmentSumaryVO : segmentSummaryVOs) {
					if (segmentSumaryVO.getSegmentSerialNumber() == mailbagVO.getSegmentSerialNumber()) {
						segmentSummaryVO = segmentSumaryVO;
					}
				}
			}
			if (segmentSummaryVO != null) {
				FlightSegmentFilterVO segmentFilter = new FlightSegmentFilterVO();
				segmentFilter.setCompanyCode(mailbagVO.getCompanyCode());
				segmentFilter.setFlightCarrierId(mailbagVO.getCarrierId());
				segmentFilter.setFlightNumber(mailbagVO.getFlightNumber());
				segmentFilter.setSequenceNumber(mailbagVO.getFlightSequenceNumber());
				segmentFilter.setOrigin(segmentSummaryVO.getSegmentOrigin());
				segmentFilter.setDestination(segmentSummaryVO.getSegmentDestination());
				FlightSegmentValidationVO segmentValidationVO = flightOperationsProxy
						.validateFlightSegment(segmentFilter);
				boolean isAtd = MailConstantsVO.FLAG_YES
						.equals(findSystemParameterValue(MailConstantsVO.RESDIT_ATD_REQD));
				if (isAtd) {
					if (segmentValidationVO.getActualTimeOfDeparture() != null) {
						resditEvtDate = segmentValidationVO.getActualTimeOfDeparture().toZonedDateTime();
					} else {
						resditEvtDate = segmentValidationVO.getScheduleTimeOfDeparture().toZonedDateTime();
					}
				} else {
					resditEvtDate = segmentValidationVO.getScheduleTimeOfDeparture().toZonedDateTime();
				}
				isEvtDateSet = true;
				if ("Y".equals(mailbagVO.getIsFromTruck()) && "STD".equals(mailbagVO.getStdOrStaTruckFlag())) {
					resditEvtDate = segmentValidationVO.getScheduleTimeOfDeparture().toZonedDateTime();
				}
				if ("Y".equals(mailbagVO.getIsFromTruck()) && "STA".equals(mailbagVO.getStdOrStaTruckFlag())) {
					resditEvtDate = segmentValidationVO.getScheduleTimeOfArrival().toZonedDateTime();
				}
			}
		} finally {
		}
		if (resditEvtDate == null || !isEvtDateSet) {
			resditEvtDate = mailbagVO.getResditEventDate() != null ? mailbagVO.getResditEventDate()
					: localDateUtil.getLocalDate(eventAirport, true);
		}
		log.debug("ResditController========>>>" + " : " + "findResditEvtDate " + " Exiting");
		return resditEvtDate;
	}

	/** 
	* @author A-8353
	* @param companyCode
	* @param paCode
	* @return
	* @throws SystemException
	*/
	public String findPartyIdentifierForPA(String companyCode, String paCode) {
		log.debug(CLASS + " : " + "findPartyIdentifierForPA" + " Entering");
		return mailMasterApi.findPartyIdentifierForPA(companyCode,paCode);
	}

	/** 
	* Method		:	ResditController.canStampResdits Added by 	:	A-6245 on 07-Jan-2021 Used for 	:	IASCB-87899 Parameters	:	@param mailResditVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	boolean
	*/
	public boolean canStampResdits(MailResditVO mailResditVO) {
		boolean enabledFlag = false;
		MailboxIdVO mailboxId = new MailboxIdVO();
		MailboxIdModel mailboxIdModel = new MailboxIdModel();
		mailboxIdModel.setCompanyCode(mailResditVO.getCompanyCode());
		mailboxIdModel.setMailboxID(mailResditVO.getMailboxID());
		mailboxId = mailOperationsMapper.mailboxIdModelToMailboxIdVO(mailMasterApi.findMailboxId(mailboxIdModel));

		if (Objects.nonNull(mailboxId)
				&& MailConstantsVO.MESSAGE_ENABLED_PARTIAL.equals(mailboxId.getMessagingEnabled())) {
			Collection<MailEventVO> mailEventVOs = findMailEvent(mailboxId);
			Mailbag mailbag = findMailbag(mailResditVO);
			if (Objects.nonNull(mailbag) && Objects.nonNull(mailEventVOs) && !mailEventVOs.isEmpty()) {
				enabledFlag = canStampResditForMailBag(mailResditVO, mailEventVOs, mailbag);
			}
		} else if (Objects.nonNull(mailboxId)
				&& MailConstantsVO.MESSAGE_ENABLED_EXCLUDE.equals(mailboxId.getMessagingEnabled())
				&& (!MailConstantsVO.RESDIT_HANDOVER_RECEIVED.equals(mailResditVO.getEventCode())
						&& !MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(mailResditVO.getEventCode()))) {
			enabledFlag = true;
		} else if (Objects.nonNull(mailboxId) && MailConstantsVO.FLAG_YES.equals(mailboxId.getMessagingEnabled())) {
			enabledFlag = true;
		}
		if (enabledFlag && mailResditVO.isFromDeviationList()
				&& (MailConstantsVO.RESDIT_RECEIVED.equals(mailResditVO.getEventCode())
						|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailResditVO.getEventCode()))) {
			Mailbag mailbag = findMailbag(mailResditVO);
			if (mailbag != null) {
				enabledFlag = validateDeviationRestriction(mailbag);
			}
		}
		if (!"I".equals(mailResditVO.getResditSentFlag())
				&& MailConstantsVO.RESDIT_UPLIFTED.equals(mailResditVO.getEventCode())) {
			enabledFlag = checkIfATDCaptured(mailResditVO);
		}
		return enabledFlag;
	}


	private Collection<MailEventVO> findMailEvent(MailboxIdVO mailboxId)  {
		Collection<MailEventModel> mailEventModels = new ArrayList<>();
		MailEventModel mailEventModel = new MailEventModel();
		mailEventModel.setCompanyCode(mailboxId.getCompanyCode());
		mailEventModel.setMailboxId(mailboxId.getMailboxID());
		mailEventModels= mailMasterApi.findMailEvent(mailEventModel);
		return mailOperationsMapper.mailEventModelToMailEventVOs(mailEventModels);
	}
	/**
	* @author U-1532
	* @param mailbag
	* @return
	* @throws SystemException
	*/
	private boolean validateDeviationRestriction(Mailbag mailbag) {
		boolean enabledFlag = true;
		String restrcitedPAsfordeviationresdits = findSystemParameterValue(
				MailConstantsVO.RESTRICTED_PAS_FOR_DEVIATION_RESDITS);
		if (restrcitedPAsfordeviationresdits != null && ("*".equals(restrcitedPAsfordeviationresdits)
				|| restrcitedPAsfordeviationresdits.contains(mailbag.getPaCode()))) {
			enabledFlag = false;
		}
		return enabledFlag;
	}

	/** 
	* Method		:	ResditController.findMailbag Added by 	:	A-6245 on 07-Jan-2021 Used for 	:	IASCB-87899 Parameters	:	@param mailResditVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	Mailbag
	*/
	private Mailbag findMailbag(MailResditVO mailResditVO) {
		Mailbag mailbag = null;
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(mailResditVO.getCompanyCode());
		mailbagPK.setMailSequenceNumber(mailResditVO.getMailSequenceNumber());
		try {
			mailbag = Mailbag.find(mailbagPK);
		} catch (FinderException e) {
			log.debug("" + e.getMessage() + " " + e);
		}
		return mailbag;
	}

	/** 
	* Method		:	ResditController.canStampResditForMailBag Added by 	:	A-6245 on 07-Jan-2021 Used for 	:	IASCB-87899 Parameters	:	@param mailResditVO Parameters	:	@param mailEventVOs Parameters	:	@param mailbag Parameters	:	@return  Return type	: 	boolean
	*/
	private boolean canStampResditForMailBag(MailResditVO mailResditVO, Collection<MailEventVO> mailEventVOs,
			Mailbag mailbag) {
		boolean enabledFlag = false;
		for (MailEventVO mailEventVO : mailEventVOs) {
			if ((mailbag.getMailCategory().equals(mailEventVO.getMailCategory())
					&& mailbag.getMailSubClass().equals(mailEventVO.getMailClass()))
					|| (MailConstantsVO.ALL.equals(mailEventVO.getMailCategory())
							&& MailConstantsVO.ALL.equals(mailEventVO.getMailClass()))
					|| (mailbag.getMailCategory().equals(mailEventVO.getMailCategory())
							&& MailConstantsVO.ALL.equals(mailEventVO.getMailClass()))
					|| (MailConstantsVO.ALL.equals(mailEventVO.getMailCategory())
							&& mailbag.getMailSubClass().equals(mailEventVO.getMailClass()))) {
				enabledFlag = canStampResditForMailEvent(mailResditVO, mailEventVO);
			}
		}
		return enabledFlag;
	}

	/** 
	* Method		:	ResditController.canStampResditForMailEvent Added by 	:	A-6245 on 07-Jan-2021 Used for 	:	IASCB-87899 Parameters	:	@param mailResditVO Parameters	:	@param mailEventVO Parameters	:	@return  Return type	: 	boolean
	*/
	private boolean canStampResditForMailEvent(MailResditVO mailResditVO, MailEventVO mailEventVO) {
		boolean enabledFlag = false;
		if (MailConstantsVO.RESDIT_RECEIVED.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isReceived();
		} else if (MailConstantsVO.RESDIT_RETURNED.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isReturned();
		} else if (MailConstantsVO.RESDIT_UPLIFTED.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isUplifted();
		} else if (MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isHandedOver();
		} else if (MailConstantsVO.RESDIT_HANDOVER_RECEIVED.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isHandedOverReceivedResditFlag();
		} else if (MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isOnlineHandedOverResditFlag();
		} else if (MailConstantsVO.RESDIT_DELIVERED.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isDelivered();
		} else if (MailConstantsVO.RESDIT_PENDING.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isPending();
		} else if (MailConstantsVO.RESDIT_ASSIGNED.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isAssigned();
		} else if (MailConstantsVO.RESDIT_LOADED.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isLoadedResditFlag();
		} else if (MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isReadyForDelivery();
		} else if (MailConstantsVO.RESDIT_ARRIVED.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isArrived();
		} else if (MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isTransportationCompleted();
		} else if (MailConstantsVO.RESDIT_LOST.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isLostFlag();
		} else if (MailConstantsVO.RESDIT_FOUND.equals(mailResditVO.getEventCode())) {
			enabledFlag = mailEventVO.isFoundFlag();
		}
		return enabledFlag;
	}

	/** 
	* @author U-1532
	* @param mailResditVO
	* @return
	* @throws SystemException
	*/
	public boolean checkIfATDCaptured(MailResditVO mailResditVO) {
		Collection<FlightSegmentSummaryVO> segmentSummaryVOs = flightOperationsProxy.findFlightSegments(
				mailResditVO.getCompanyCode(), mailResditVO.getCarrierId(), mailResditVO.getFlightNumber(),
				mailResditVO.getFlightSequenceNumber());
		FlightSegmentSummaryVO segmentSummaryVO = null;
		if (segmentSummaryVOs != null && segmentSummaryVOs.size() > 0) {
			for (FlightSegmentSummaryVO segmentSumaryVO : segmentSummaryVOs) {
				if (segmentSumaryVO.getSegmentSerialNumber() == mailResditVO.getSegmentSerialNumber()) {
					segmentSummaryVO = segmentSumaryVO;
				}
			}
		}
		if (segmentSummaryVO != null) {
			FlightSegmentFilterVO segmentFilter = new FlightSegmentFilterVO();
			segmentFilter.setCompanyCode(mailResditVO.getCompanyCode());
			segmentFilter.setFlightCarrierId(mailResditVO.getCarrierId());
			segmentFilter.setFlightNumber(mailResditVO.getFlightNumber());
			segmentFilter.setSequenceNumber(mailResditVO.getFlightSequenceNumber());
			segmentFilter.setOrigin(segmentSummaryVO.getSegmentOrigin());
			segmentFilter.setDestination(segmentSummaryVO.getSegmentDestination());
			FlightSegmentValidationVO segmentValidationVO = flightOperationsProxy.validateFlightSegment(segmentFilter);
			boolean isAtd = MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_ATD_REQD));
			if (isAtd) {
				if (segmentValidationVO.getActualTimeOfDeparture() != null) {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * If ULDResdit already sent then no need to flag again. but need to flag if not sent or in N status Jan 22, 2007, a-1876
	 * @param eventCode
	 * @param uldResditVO
	 * @throws SystemException
	 */
	private boolean canFlagResditForULDEvent(String eventCode, UldResditVO uldResditVO) {
		log.debug(CLASS + " : " + "canFlagResditFor Event" + " Entering");
		boolean canFlag = false;
		uldResditVO.setEventCode(eventCode);
		if (MailConstantsVO.RESDIT_ASSIGNED.equals(uldResditVO.getEventCode())
				|| MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(uldResditVO.getEventCode())
				|| MailConstantsVO.RESDIT_UPLIFTED.equals(uldResditVO.getEventCode())
				|| MailConstantsVO.RESDIT_LOADED.equals(uldResditVO.getEventCode())) {
			Collection<UldResditVO> uldResditVOs = UldResdit.findULDResditStatus(uldResditVO);
			if (uldResditVOs != null && uldResditVOs.size() > 0) {
				for (UldResditVO uldResditStatusVO : uldResditVOs) {
					if (MailConstantsVO.FLAG_NO.equals(uldResditStatusVO.getResditSentFlag())) {
						if (!MailConstantsVO.FLAG_YES.equals(uldResditStatusVO.getProcessedStatus())) {
							List<UldResdit> uldResdits = UldResdit.findUldResdit(uldResditVO, eventCode);
							if (uldResdits != null && uldResdits.size() > 0) {
								UldResdit uldResdit = uldResdits.get(0);
								uldResdit.remove();
								canFlag = true;
							}
						}
					}
				}
			} else {
				canFlag = true;
			}
		} else {
			canFlag = true;
		}
		return canFlag;
	}

	/**
	 * @author A-1936 This method is used to construct the UldResditVo from theContainerDetailsVo
	 * @param containerDetailsVO
	 * @param eventAirport
	 * @param eventCode
	 * @return
	 */
	private UldResditVO constructUldResditVO(ContainerDetailsVO containerDetailsVO, String eventAirport,
											 String eventCode) {
		log.debug(CLASS + " : " + "getMailResditVO" + " Entering");
		UldResditVO uldResditVO = new UldResditVO();
		uldResditVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		uldResditVO.setEventAirport(eventAirport);
		uldResditVO.setEventCode(eventCode);
		uldResditVO.setCarrierId(containerDetailsVO.getCarrierId());
		if (!MailConstantsVO.RESDIT_PENDING.equals(eventCode)) {
			uldResditVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			uldResditVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
			uldResditVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		}
		uldResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
		uldResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
		uldResditVO.setUldNumber(containerDetailsVO.getContainerNumber());
		if (MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)) {
			uldResditVO.setEventDate(findResditEvtDateForULD(containerDetailsVO, eventAirport));
		} else {
			uldResditVO.setEventDate(localDateUtil.getLocalDate(eventAirport, true));
		}
		uldResditVO.setContainerJourneyId(containerDetailsVO.getContainerJnyId());
		uldResditVO.setShipperBuiltCode(containerDetailsVO.getPaCode());
		log.debug(CLASS + " : " + "constructUldResditVO" + " Exiting");
		return uldResditVO;
	}
	/**
	 * @author A-3227
	 * @param doe
	 * @param companyCode
	 * @param deliveredPort
	 * @param cityCache
	 * @param poaCode
	 * @return
	 * @throws SystemException
	 */
	public boolean isValidDeliveryAirport(String doe, String companyCode, String deliveredPort,
										  Map<String, String> cityCache, String poaCode, ZonedDateTime dspDate) {
		log.debug("MailTransfer" + " : " + "isValidDeliveryAirport" + " Entering");
		Collection<String> officeOfExchanges = new ArrayList<String>();
		if (doe != null && doe.length() > 0) {
			officeOfExchanges.add(doe);
		}
		String deliveryCityCode = null;
		String nearestAirport = null;
		String nearestAirportToCity = null;
		log.debug("" + "----officeOfExchanges---" + " " + officeOfExchanges);
		Collection<ArrayList<String>> groupedOECityArpCodes = mailController.findCityAndAirportForOE(companyCode,
				officeOfExchanges);
		log.debug("" + "----groupedOECityArpCodes---" + " " + groupedOECityArpCodes);
		if (groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
			for (ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
				if (cityAndArpForOE.size() == 3) {
					if (doe != null && doe.length() > 0 && doe.equals(cityAndArpForOE.get(0))) {
						deliveryCityCode = cityAndArpForOE.get(1);
						nearestAirportToCity = cityAndArpForOE.get(2);
					}
				}
			}
		}
		if (cityCache != null) {
			nearestAirport = cityCache.get(deliveryCityCode);
		}
		if (nearestAirport == null && nearestAirportToCity != null) {
			nearestAirport = nearestAirportToCity;
			if (nearestAirport != null) {
				cityCache.put(deliveryCityCode, nearestAirport);
			}
		}
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		AirportValidationVO airportValidationVO = null;
		try {
			airportValidationVO = neoMastersServiceUtils.validateAirportCode(logonAttributes.getCompanyCode(),
					deliveredPort);
		} catch (AirportBusinessException e) {
			log.error(e.getMessage());
		}
		String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
		if (MailConstantsVO.FLAG_YES.equals(isCoterminusConfigured)) {
			Page<OfficeOfExchangeVO> destinationAirport = mailController.findOfficeOfExchange(companyCode, doe,
					1);
			OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
			if (destinationAirport != null && !destinationAirport.isEmpty()) {
				officeOfExchangeVO = destinationAirport.iterator().next();
			}
			if (officeOfExchangeVO != null && officeOfExchangeVO.getAirportCode() != null
					&& !officeOfExchangeVO.getAirportCode().isEmpty()) {
				nearestAirport = officeOfExchangeVO.getAirportCode();
			}
			boolean coTerminusCheck = mailController.validateCoterminusairports(nearestAirport, deliveredPort,
					MailConstantsVO.RESDIT_READYFOR_DELIVERY, poaCode, dspDate);
			if (coTerminusCheck) {
				return true;
			}
		}
		if (airportValidationVO != null && airportValidationVO.getCityCode() != null) {
			if (airportValidationVO.getCityCode().equals(deliveryCityCode)) {
				log.debug("inside city validation returning true");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void flagReadyForDeliveryResditForMailbags(Collection<MailbagVO> deliveredMailbags, String airportCode) {
		log.debug(CLASS + " : " + "flagReady ForDeliveryResditForMailbags" + " Entering");
		HashMap<String, String> exgOfcPAMap = new HashMap<String, String>();
		Collection<MailResditVO> r23MailResditVOs = new ArrayList<MailResditVO>();
		String eventCode = null;
		String paCode = null;
		MailResditVO mailResditVO = null;
		eventCode = MailConstantsVO.RESDIT_READYFOR_DELIVERY;
		for (MailbagVO mailbagVO : deliveredMailbags) {
			paCode = exgOfcPAMap.get(mailbagVO.getOoe());
			if (paCode == null || paCode.length() <= 0) {
				paCode = new MailController().findPAForOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
				exgOfcPAMap.put(mailbagVO.getOoe(), paCode);
			}
			mailResditVO = constructMailResditVO(mailbagVO, airportCode, eventCode, false);
			mailResditVO.setPaOrCarrierCode(paCode);
			r23MailResditVOs.add(mailResditVO);
		}
		if (r23MailResditVOs != null && r23MailResditVOs.size() > 0) {
			r23MailResditVOs = canFlagResditForEvents(eventCode, r23MailResditVOs, deliveredMailbags);
			stampResdits(r23MailResditVOs);
		}
		log.debug(CLASS + " : " + "flagReadyFor DeliveryResditForMailbags" + " Exiting");
	}

	private void flagReadyForDeliveryResditForULD(Collection<ContainerDetailsVO> deliveredContainers,
												  String eventPort) {
		Collection<UldResditVO> uldResditVOs = new ArrayList<UldResditVO>();
		if (deliveredContainers != null && deliveredContainers.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : deliveredContainers) {
				UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO, eventPort,
						MailConstantsVO.RESDIT_READYFOR_DELIVERY);
				uldResditVOs.add(uldResditVO);
			}
			if (uldResditVOs != null && uldResditVOs.size() > 0) {
				flagReadyForDeliveryResditForULDs(uldResditVOs, eventPort);
			}
		}
	}

	private void flagReadyForDeliveryResditForULDs(Collection<UldResditVO> uldResditVOs, String airportCode) {
		log.debug(CLASS + " : " + "flagReadyForDeliveryResditForULDs" + " Entering");
		String eventCode = MailConstantsVO.RESDIT_READYFOR_DELIVERY;
		if (uldResditVOs != null && uldResditVOs.size() > 0) {
			for (UldResditVO uldResditVO : uldResditVOs) {
				if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
					log.info("Flagging the RESDIT_READYFOR_DELIVERY");
					new UldResdit(uldResditVO);
				}
			}
		}
		log.debug(CLASS + " : " + "flagReady ForDeliveryResditForULDs" + " Exiting");
	}

	/**
	 * Feb 8, 2007, A-1739 Modified By Karthick V to incorporate  the Functionality Sending the Resdits for  the  PA Built  ULd as Well. Note:- Currently the Resdits are not being flagged for the Ulds in the System as a  event of which the following steps to be Done When Resdits to be Send for the ULD 1.Stamp  the Records in Mtkuldrdt  with the Prosta-Y and RDTSND--Y which will be removed when the Resdits Flagging for theb ULD will be Done ...
	 * @param carditEnquiryVO
	 * @throws SystemException
	 * @throws ContainerAssignmentException
	 */
	public void sendResditMessages(CarditEnquiryVO carditEnquiryVO) throws ContainerAssignmentException {
		log.debug(CLASS + " : " + "send ResditMessage" + " Entering");
		Collection<ContainerVO> containerVos = carditEnquiryVO.getContainerVos();
		if (containerVos != null && containerVos.size() > 0) {
			updateSegmentDetailsForContainers(containerVos);
			persistULDResdits(containerVos);
		}
		Collection<ResditMessageVO> resditMessageVOs = new Resdit().constructResditMessageVOs(carditEnquiryVO);
		log.debug("constructed messages " + resditMessageVOs);
		if (resditMessageVOs != null && resditMessageVOs.size() > 0) {
			for (ResditMessageVO resditMessageVO : resditMessageVOs) {
				buildResdit(constructResditEventVOForMsg(resditMessageVO, carditEnquiryVO));
			}
		}
		log.debug(CLASS + " : " + "sendResdit Message" + " Exiting");
	}

	private void sendResditMessage(ResditMessageVO resditMessageVO, Collection<ResditEventVO> resditEventVOs) {
		log.debug(CLASS + " : " + "send ResditMessage" + " Entering");
		log.debug("resditMessage" + resditMessageVO);
		Collection<String> systemParameters = new ArrayList<String>();
		Collection<MessageVO> messageVO = null;
		String msgTxt = null;
		systemParameters.add(MailConstantsVO.SYSPAR_USPS_ENHMNT);
		systemParameters.add(USPS_INTERNATIONAL_PA);
		systemParameters.add(USPS_DOMESTIC_PA);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.debug(e.getMessage());
		}
		boolean isSendCCResditMessage = false;
		log.debug(
				"System Parameter For USPS ENHMNT is-->" + systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT));
		if (systemParameterMap != null
				&& (MailConstantsVO.FLAG_ACTIVE.equals(systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT))
				|| MailConstantsVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT)))
				&& RESDIT_TO_POST_US.equals(resditMessageVO.getRecipientID())) {
			log.debug("Going to sendCCResditMessage");
			isSendCCResditMessage = sendCCResditMessage(resditMessageVO, resditEventVOs);
		} else {
			messageVO = msgBrokerMessageProxy.encodeAndSaveMessage(resditMessageVO);
		}
		if (messageVO != null && !messageVO.isEmpty()) {
			msgTxt = messageVO.iterator().next().getRawMessage();
		}
		if (resditEventVOs != null && msgTxt != null
				&& (resditMessageVO.getRecipientID().equals(systemParameterMap.get(USPS_INTERNATIONAL_PA))
				|| resditMessageVO.getRecipientID().equals(systemParameterMap.get(USPS_DOMESTIC_PA)))) {
			createMailResditMessage(resditEventVOs, messageVO, resditMessageVO);
		}
		for (ResditEventVO resditEventVO : resditEventVOs) {
			if (resditEventVO.getMessageSequenceNumber() > 0) {
				removeResditEvent(resditEventVO);
			}
		}
		log.debug(CLASS + " : " + "sendResdit Message" + " Exiting");
	}

	/**
	 * Method		:	ResditController.createMailResditMessage Added by 	:	A-4809 on Jun 6, 2019 Used for 	: Parameters	:	@param resditEventVO  Return type	: 	void
	 * @param resditMessageVO
	 */
	private void createMailResditMessage(Collection<ResditEventVO> resditEventVOs, Collection<MessageVO> messageVOs,
										 ResditMessageVO resditMessageVO) {
		MailResditMessage mailResditMessage = new MailResditMessage(
				MailOperationsVOConverter.populateMailResditMessageVO(resditEventVOs, messageVOs));
		if (mailResditMessage != null && resditEventVOs != null && resditEventVOs.size() > 0) {
			long messageIdentifier = mailResditMessage.getMessageIdentifier() > 0
					? mailResditMessage.getMessageIdentifier()
					: 0;
			updateMessageIdentifierOfResditMessage(messageIdentifier, resditEventVOs, resditMessageVO);
		}
	}

	/**
	 * TODO Purpose Sep 22, 2006, a-1739
	 * @param resditEventVO
	 * @return
	 */
	private ResditEventPK constructResditEventPK(ResditEventVO resditEventVO) {
		ResditEventPK resditEventPK = new ResditEventPK();
		resditEventPK.setCompanyCode(resditEventVO.getCompanyCode());
		resditEventPK.setConsignmentDocumentNumber(resditEventVO.getConsignmentNumber());
		if (resditEventVO.getActualResditEvent() != null) {
			resditEventPK.setEventCode(resditEventVO.getActualResditEvent());
		} else {
			resditEventPK.setEventCode(resditEventVO.getResditEventCode());
		}
		resditEventPK.setEventPort(resditEventVO.getEventPort());
		resditEventPK.setMessageSequenceNumber(resditEventVO.getMessageSequenceNumber());
		return resditEventPK;
	}

	/**
	 * TODO Purpose Sep 22, 2006, a-1739
	 * @param resditEventVO
	 * @throws SystemException
	 */
	private void removeResditEvent(ResditEventVO resditEventVO) {
		log.debug(CLASS + " : " + "removeResditEvent" + " Entering");
		try {
			ResditEvent resditEvent = ResditEvent.find(constructResditEventPK(resditEventVO));
			resditEvent.remove();
		} catch (FinderException ex) {
		}
		log.debug(CLASS + " : " + "removeResditEvent" + " Exiting");
	}

	private boolean sendCCResditMessage(ResditMessageVO resditMessageVO, Collection<ResditEventVO> resditEventVOs) {
		log.debug(CLASS + " : " + "sendCCResditMessage" + " Entering");
		Collection<ConsignmentInformationVO> consignmentInformationVOsForPA = new ArrayList<ConsignmentInformationVO>();
		Collection<ConsignmentInformationVO> consignmentInformationVOsForOtherAirline = new ArrayList<ConsignmentInformationVO>();
		Collection<ConsignmentInformationVO> consignmentInformationVOsForRestrictedAirlines = new ArrayList<ConsignmentInformationVO>();
		Collection<ConsignmentInformationVO> consignmentInformationVOsForXX = new ArrayList<ConsignmentInformationVO>();
		HashMap<String, String> xxResditRecepientMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(RESDIT_EXD_AIRLINE_LIST);
		String contractReference = null;
		String contractedAirline = null;
		if (resditMessageVO.getConsignmentInformationVOs() != null
				&& resditMessageVO.getConsignmentInformationVOs().size() > 0) {
			Map oneTimesMap = null;
			try {
				oneTimesMap = sharedDefaultsProxy.findOneTimeValues(resditMessageVO.getCompanyCode(), oneTimeList);
				log.debug("\n\n RESDIT_TO_AIRLINE hash map******************" + oneTimesMap);
			} catch (BusinessException proxyException) {
				throw new SystemException(proxyException.getMessage());
			}
			Collection<String> resditToExcludeAirlineList = new ArrayList<String>();
			if (oneTimesMap != null) {
				Collection<OneTimeVO> resditToAirlineConfigList = (Collection<OneTimeVO>) oneTimesMap
						.get(RESDIT_EXD_AIRLINE_LIST);
				for (OneTimeVO oneTimeVO : resditToAirlineConfigList) {
					resditToExcludeAirlineList.add(oneTimeVO.getFieldValue());
				}
			}
			for (ConsignmentInformationVO consignmentInformationVO : resditMessageVO.getConsignmentInformationVOs()) {
				if (consignmentInformationVO.getConsignmentID().startsWith(MailConstantsVO.RESDIT_XX)) {
					consignmentInformationVOsForXX.add(consignmentInformationVO);
				} else {
					Collection<CarditReferenceInformationVO> carditRefInfoVos = Resdit
							.findCCForSendResdit(consignmentInformationVO);
					if (carditRefInfoVos != null && carditRefInfoVos.size() > 0) {
						if ((MailConstantsVO.RESDIT_DELIVERED.equals(consignmentInformationVO.getConsignmentEvent())
								|| MailConstantsVO.RESDIT_READYFOR_DELIVERY
								.equals(consignmentInformationVO.getConsignmentEvent()))) {
							for (CarditReferenceInformationVO carditRefInfoVo : carditRefInfoVos) {
								if (consignmentInformationVO.getTransferLocation() != null && consignmentInformationVO
										.getTransferLocation().equals(carditRefInfoVo.getDestination())) {
									if (carditRefInfoVo.getContractRef() != null
											&& carditRefInfoVo.getContractRef().length() > 0) {
										contractReference = carditRefInfoVo.getContractRef();
									} else {
										contractReference = carditRefInfoVo.getRefNumber();
									}
									contractedAirline = contractReference == null ? null
											: contractReference.substring(contractReference.length() - 2,
											contractReference.length());
									log.debug(
											"Inside sendCCResditMessage-->contractedAirline is--" + contractedAirline);
									if (contractedAirline != null && contractedAirline.length() > 0) {
										if (RESDIT_TO_AIRLINE_AA.equals(contractedAirline)) {
											consignmentInformationVOsForOtherAirline.add(consignmentInformationVO);
										} else if (resditToExcludeAirlineList.contains(contractedAirline)) {
											consignmentInformationVOsForRestrictedAirlines
													.add(consignmentInformationVO);
										} else {
											consignmentInformationVOsForPA.add(consignmentInformationVO);
										}
									}
									break;
								}
							}
						} else {
							for (CarditReferenceInformationVO carditRefInfoVO : carditRefInfoVos) {
								if (consignmentInformationVO.getTransferLocation() != null && consignmentInformationVO
										.getTransferLocation().equals(carditRefInfoVO.getOrgin())) {
									if (carditRefInfoVO.getContractRef() != null
											&& carditRefInfoVO.getContractRef().length() > 0) {
										contractReference = carditRefInfoVO.getContractRef();
									} else {
										contractReference = carditRefInfoVO.getRefNumber();
									}
									contractReference = carditRefInfoVO.getRefNumber();
									contractedAirline = contractReference == null ? null
											: contractReference.substring(contractReference.length() - 2,
											contractReference.length());
									log.debug(
											"Inside sendCCResditMessage-->contractedAirline is--" + contractedAirline);
									if (contractedAirline != null && contractedAirline.length() > 0) {
										if (RESDIT_TO_AIRLINE_AA.equals(contractedAirline)) {
											consignmentInformationVOsForOtherAirline.add(consignmentInformationVO);
										} else if (resditToExcludeAirlineList.contains(contractedAirline)) {
											consignmentInformationVOsForRestrictedAirlines
													.add(consignmentInformationVO);
										} else {
											consignmentInformationVOsForPA.add(consignmentInformationVO);
										}
									}
									break;
								}
							}
						}
					} else {
						consignmentInformationVOsForPA.add(consignmentInformationVO);
					}
				}
			}
		}
		if (consignmentInformationVOsForXX != null && consignmentInformationVOsForXX.size() > 0) {
			xxResditRecepientMap = Resdit.findRecepientForXXResdits(consignmentInformationVOsForXX);
			String addrParty = null;
			for (ConsignmentInformationVO consignmentInformationVO : consignmentInformationVOsForXX) {
				addrParty = xxResditRecepientMap.get(consignmentInformationVO.getConsignmentID());
				if (addrParty != null && RESDIT_TO_AIRLINE_AA.equals(addrParty)) {
					consignmentInformationVOsForOtherAirline.add(consignmentInformationVO);
				} else {
					consignmentInformationVOsForPA.add(consignmentInformationVO);
				}
			}
		}
		if (consignmentInformationVOsForPA != null && consignmentInformationVOsForPA.size() > 0) {
			log.debug("Inside sendCCResditMessage-->consignmentInformationVOsForAirline is--"
					+ consignmentInformationVOsForPA);
			ResditMessageVO resditMessageCopyVO = new ResditMessageVO();
			resditMessageCopyVO = mailOperationsMapper.copyResditMessageVO(resditMessageVO);
			resditMessageCopyVO.setConsignmentInformationVOs(consignmentInformationVOsForPA);
			log.debug("Inside sendCCResditMessage-->resdit is to airline" + resditMessageVO.getResditToAirlineCode());
			msgBrokerMessageProxy.encodeAndSaveMessage(resditMessageCopyVO);
			saveResditFileLogs(resditMessageCopyVO);
			postResditSendProcess(resditMessageCopyVO, resditEventVOs);
		}
		if (consignmentInformationVOsForOtherAirline != null && consignmentInformationVOsForOtherAirline.size() > 0) {
			log.debug("Inside sendCCResditMessage-->consignmentInformationVOsForOtherAirline is--"
					+ consignmentInformationVOsForOtherAirline);
			ResditMessageVO resditMessageCopyVO = new ResditMessageVO();
			resditMessageCopyVO = mailOperationsMapper.copyResditMessageVO(resditMessageVO);
			resditMessageCopyVO.setRecipientID(RESDIT_TO_AIRLINE_AA);
			resditMessageCopyVO.setResditToAirlineCode(RESDIT_TO_AIRLINE_AA);
			resditMessageCopyVO.setConsignmentInformationVOs(consignmentInformationVOsForOtherAirline);
			log.debug("Inside sendCCResditMessage-->resdit is to PA" + resditMessageVO.getRecipientID());
			msgBrokerMessageProxy.encodeAndSaveMessage(resditMessageCopyVO);
			saveResditFileLogs(resditMessageCopyVO);
			postResditSendProcess(resditMessageCopyVO, resditEventVOs);
		}
		if (consignmentInformationVOsForRestrictedAirlines != null
				&& consignmentInformationVOsForRestrictedAirlines.size() > 0) {
			log.debug("Inside sendCCResditMessage-->consignmentInformationVOsForOtherAirline is--"
					+ consignmentInformationVOsForRestrictedAirlines);
			ResditMessageVO resditMessageCopyVO = new ResditMessageVO();
			resditMessageCopyVO = mailOperationsMapper.copyResditMessageVO(resditMessageVO);
			resditMessageCopyVO.setResditToAirlineCode(RESDIT_TO_AIRLINE_JL);
			resditMessageCopyVO.setRecipientID(RESDIT_TO_AIRLINE_JL);
			resditMessageCopyVO.setConsignmentInformationVOs(consignmentInformationVOsForRestrictedAirlines);
			log.debug("Inside sendCCResditMessage-->resdit is to PA" + resditMessageVO.getRecipientID());
			msgBrokerMessageProxy.encodeAndSaveMessage(resditMessageCopyVO);
			saveResditFileLogs(resditMessageCopyVO);
			postResditSendProcess(resditMessageCopyVO, resditEventVOs);
		}
		log.debug(CLASS + " : " + "sendCCResditMessage" + " Exiting");
		return true;
	}

	/**
	 * @param resditMessageVO
	 * @return
	 */
	private boolean canCreateHistoryForMailResdits(ResditMessageVO resditMessageVO) {
		boolean isValid = false;
		Collection<ConsignmentInformationVO> consignments = resditMessageVO.getConsignmentInformationVOs();
		if (consignments != null && consignments.size() > 0) {
			for (ConsignmentInformationVO consignmentInformationVo : consignments) {
				if (consignmentInformationVo.getReceptacleInformationVOs() != null
						&& consignmentInformationVo.getReceptacleInformationVOs().size() > 0) {
					isValid = true;
					break;
				}
			}
		}
		return isValid;
	}

	private MailResditPK constructMailResditPK(ResditEventVO resditEventVO, ReceptacleInformationVO receptacleVO) {
		MailResditPK mailResditPK = new MailResditPK();
		mailResditPK.setCompanyCode(resditEventVO.getCompanyCode());
		if (resditEventVO.getActualResditEvent() != null) {
			mailResditPK.setEventCode(resditEventVO.getActualResditEvent());
		} else {
			mailResditPK.setEventCode(resditEventVO.getResditEventCode());
		}
		if (receptacleVO.getMailSequenceNumber() > 0) {
			mailResditPK.setMailSequenceNumber(receptacleVO.getMailSequenceNumber());
		} else {
			mailResditPK.setMailSequenceNumber(
					findMailSequenceNumber(receptacleVO.getReceptacleID(), resditEventVO.getCompanyCode()));
		}
		mailResditPK.setSequenceNumber(receptacleVO.getEventSequenceNumber());
		return mailResditPK;
	}
	/**
	 * TODO Purpose Sep 22, 2006, a-1739
	 * @param resditEventVO
	 * @param resditMessageVO
	 * @throws SystemException
	 */
	private void updateResditStatus(ResditEventVO resditEventVO, ResditMessageVO resditMessageVO) {
		log.debug(CLASS + " : " + "updateResditStatus" + " Entering");
		Collection<ConsignmentInformationVO> consignments = resditMessageVO.getConsignmentInformationVOs();
		if (consignments != null && consignments.size() > 0) {
			for (ConsignmentInformationVO consignVO : consignments) {
				if (String.valueOf(consignVO.getConsignmentEvent()).equals(resditEventVO.getResditEventCode())) {
					Collection<ReceptacleInformationVO> receptacles = consignVO.getReceptacleInformationVOs();
					if (receptacles != null && receptacles.size() > 0) {
						for (ReceptacleInformationVO receptacleVO : receptacles) {
							try {
								MailResdit mailResdit = MailResdit
										.find(constructMailResditPK(resditEventVO, receptacleVO));
								mailResdit.setProcessedStatus(MailConstantsVO.FLAG_YES);
								mailResdit.setResditSent(MailConstantsVO.RESDIT_EVENT_GENERATED);
								mailResdit.setInterchangeControlReference(
										resditMessageVO.getInterchangeControlReference());
								ZonedDateTime sendDate = localDateUtil.getLocalDate(null, true);
								ZonedDateTime gmt = localDateUtil.toUTCTime(sendDate);
								mailResdit.setResditSenttime(gmt.toLocalDateTime());
								setSenderAndRecipientIdentifier(resditEventVO, mailResdit);
							} catch (FinderException ex) {
								log.debug(
										"Trying to update status for DERIVED Receptacle which came in SB ULD....just ignore exception!!!!"
												+ receptacleVO.getReceptacleID());
							}
						}
					}
					Collection<ContainerInformationVO> containers = consignVO.getContainerInformationVOs();
					if (containers != null && containers.size() > 0) {
						for (ContainerInformationVO containerVO : containers) {
							try {
								UldResdit uldResdit = UldResdit.find(constructULDResditPK(resditEventVO, containerVO));
								uldResdit.setProcessedStatus(MailConstantsVO.FLAG_YES);
								uldResdit.setResditSent(MailConstantsVO.RESDIT_EVENT_GENERATED);
								uldResdit.setInterchangeControlReference(
										resditMessageVO.getInterchangeControlReference());
							} catch (FinderException exception) {
								throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
							}
						}
					}
				}
			}
		}
		log.debug(CLASS + " : " + "updateResditStatus" + " Exiting");
	}

	public void setSenderAndRecipientIdentifier(ResditEventVO resditEventVO, MailResdit mailResdit) {
		mailResdit.setSenderIdentifier(resditEventVO.getSenderIdentifier());
		mailResdit.setRecipientIdentifier(resditEventVO.getRecipientIdentifier());
	}

	/**
	 * TODO Purpose Sep 22, 2006, a-1739
	 * @param resditEventVO
	 * @param containerVO
	 * @return
	 */
	private UldResditPK constructULDResditPK(ResditEventVO resditEventVO, ContainerInformationVO containerVO) {
		UldResditPK uldResditPK = new UldResditPK();
		uldResditPK.setCompanyCode(resditEventVO.getCompanyCode());
		uldResditPK.setEventCode(resditEventVO.getResditEventCode());
		uldResditPK.setUldNumber(containerVO.getContainerNumber());
		uldResditPK.setSequenceNumber(containerVO.getEventSequenceNumber());
		return uldResditPK;
	}

	/**
	 * @param resditMessageVO
	 * @param resditEventVOs
	 * @throws SystemException
	 */
	private void postResditSendProcess(ResditMessageVO resditMessageVO, Collection<ResditEventVO> resditEventVOs) {
		log.debug(CLASS + " : " + "postResditSendProcess" + " Exiting");
		boolean canFlagHisForMailResdit = canCreateHistoryForMailResdits(resditMessageVO);
		HashSet<String> consignKeySet = new HashSet<String>();
		for (ResditEventVO resditEventVO : resditEventVOs) {
			updateResditStatus(resditEventVO, resditMessageVO);
			if (canFlagHisForMailResdit) {
				createMailbagHistoryForResdit(resditEventVO, resditMessageVO, consignKeySet);
			}
		}
		log.debug(CLASS + " : " + "postResditSendProcess" + " Exiting");
	}

	/**
	 * Added by A-2135 for QF CR 1517 Oct 06, 2010, a-2135
	 * @return
	 */
	private MailResditFileLogPK constructMailResditFileLogPK(ResditMessageVO resditMessageVO) {
		MailResditFileLogPK mailResditFileLogPK = new MailResditFileLogPK();
		mailResditFileLogPK.setCompanyCode(resditMessageVO.getCompanyCode());
		mailResditFileLogPK.setInterchangeControlReference(resditMessageVO.getInterchangeControlReference());
		mailResditFileLogPK.setRecipientID(resditMessageVO.getRecipientID());
		return mailResditFileLogPK;
	}

	/**
	 * Added by A-2135 for QF CR 1517
	 * @param resditMessageVO
	 * @throws SystemException
	 */
	private void saveResditFileLogs(ResditMessageVO resditMessageVO) {
		log.debug(CLASS + " : " + "SaveResditFileLogs" + " Entering");
		MailResditFileLog mailResditFileLog = null;
		try {
			mailResditFileLog = MailResditFileLog.find(constructMailResditFileLogPK(resditMessageVO));
			if (mailResditFileLog != null) {
				if (mailResditFileLog.getCCList() != null) {
					StringBuilder ccList = new StringBuilder(mailResditFileLog.getCCList());
					mailResditFileLog
							.setCCList(ccList.append(',').append(resditMessageVO.getResditToAirlineCode()).toString());
				}
			}
		} catch (FinderException exception) {
			mailResditFileLog = new MailResditFileLog(resditMessageVO);
		}
		log.debug(CLASS + " : " + "SaveResditFileLogs" + " Exiting");
	}

	/**
	 * Method		:	ResditController.constructConsignmentKey Added by 	: Used for 	: Constructing ConsignmentKey Parameters	:	@param consignVO Return type	: 	String
	  * @param receptacleInformationVO
	 */
	private String constructConsignmentKey(ConsignmentInformationVO consignVO,
										   ReceptacleInformationVO receptacleInformationVO) {
		StringBuilder key = new StringBuilder();
		key.append(consignVO.getConsignmentEvent()).append(consignVO.getConsignmentID())
				.append(receptacleInformationVO.getReceptacleID());
		Collection<TransportInformationVO> transportInfoVOs = consignVO.getTransportInformationVOs();
		TransportInformationVO transportVO = null;
		if (transportInfoVOs != null && transportInfoVOs.size() > 0) {
			transportVO = ((ArrayList<TransportInformationVO>) transportInfoVOs).get(0);
			if (transportVO != null) {
				key.append(transportVO.getCarrierCode()).append(transportVO.getFlightNumber())
						.append(transportVO.getFlightSequenceNumber());
			}
		}
		return key.toString();
	}

	/**
	 * TODO Purpose Sep 26, 2006, a-1739
	 * @param resditEventVO
	 * @param resditMessageVO
	 * @throws SystemException
	 */
	private void createMailbagHistoryForResdit(ResditEventVO resditEventVO, ResditMessageVO resditMessageVO,
											   HashSet<String> consignKeySet) {
		log.info(CLASS + " : " + "createMailbagHistoryForResdit" + " Entering");
		Collection<ConsignmentInformationVO> consignments = resditMessageVO.getConsignmentInformationVOs();
		resditEventVO.setInterchangeControlReference(resditMessageVO.getInterchangeControlReference());
		log.info("Interchange Control Reference Number to be flagged in MTKMALHIS"
				+ resditEventVO.getInterchangeControlReference());
		String key = null;
		for (ConsignmentInformationVO consignVO : consignments) {
			if (consignVO.getConsignmentEvent() != null && consignVO.getConsignmentEvent().trim().length() > 0
					&& consignVO.getConsignmentID() != null && consignVO.getConsignmentID().trim().length() > 0
					&& String.valueOf(consignVO.getConsignmentEvent()).equals(resditEventVO.getResditEventCode())
					&& consignVO.getConsignmentID().equals(resditEventVO.getConsignmentNumber())) {
				for (ReceptacleInformationVO receptacleInformationVO : consignVO.getReceptacleInformationVOs()) {
					key = constructConsignmentKey(consignVO, receptacleInformationVO);
					if (!(consignKeySet.contains(key))) {
						consignKeySet.add(key);
						new Mailbag().insertMailbagHistoryForResdit(resditEventVO, consignVO, receptacleInformationVO);
						MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
						mailController.createMailbagAuditForResdit(resditEventVO, consignVO, receptacleInformationVO);
					}
				}
			}
		}
		log.debug(CLASS + " : " + "createMailbagHistoryForResdit" + " Exiting");
	}

	/**
	 * TODO Purpose Feb 12, 2007, A-1739
	 * @param resditMessageVO
	 * @param carditEnquiryVO
	 * @return
	 */
	private Collection<ResditEventVO> constructResditEventVOForMsg(ResditMessageVO resditMessageVO,
																   CarditEnquiryVO carditEnquiryVO) {
		Collection<ResditEventVO> resditEvents = new ArrayList<ResditEventVO>();
		Collection<ConsignmentInformationVO> consignInfoVOs = resditMessageVO.getConsignmentInformationVOs();
		for (ConsignmentInformationVO consignInfoVO : consignInfoVOs) {
			ResditEventVO resditEventVO = new ResditEventVO();
			resditEventVO.setCompanyCode(resditMessageVO.getCompanyCode());
			resditEventVO.setResditEventCode(consignInfoVO.getConsignmentEvent());
			resditEventVO.setEventPort(resditMessageVO.getStationCode());
			resditEventVO.setConsignmentNumber(consignInfoVO.getConsignmentID());
			resditEventVO.setMessageSequenceNumber(consignInfoVO.getMessageSequenceNumber());
			resditEventVO.setPaCode(resditMessageVO.getRecipientID());
			resditEventVO.setCarditExist(resditMessageVO.getCarditExist());
			//TODO: neo to correct
//			resditEventVO.setEventDate(
//					localDateUtil.convertFromUTCTime(consignInfoVO.getEventDate(), resditEventVO.getEventPort()));
			String resditVersion = "";
			//TODO: Mailbox service to be corrected
//			try {
//				MailBoxId mailBoxId = MailBoxId.find(carditEnquiryVO.getCompanyCode(), resditMessageVO.getMailboxId());
//				resditVersion = mailBoxId.getResditversion();
//			} catch (FinderException exception) {
//				log.info("" + " error msg \n\n " + " " + exception.getMessage());
//			}
			resditEventVO.setResditVersion(resditVersion);
			if (MailConstantsVO.M49_1_1.equals(resditVersion)) {
				resditEventVO.setM49Resdit(true);
				resditEventVO.setResditVersion("1.1");
			}
			resditEvents.add(resditEventVO);
		}
		return resditEvents;
	}

	/**
	 * @author A-1936This method is used to Persist the  Resdit Events for  the ULDs
	 * @throws SystemException
	 */
	private void persistULDResdits(Collection<ContainerVO> containerVos) {
		log.debug("Resdit Controller" + " : " + "persistULDResdits" + " Entering");
		UldResdit uldResdit = null;
		for (ContainerVO containerVo : containerVos) {
			UldResditVO uldResditVO = new UldResditVO();
			uldResditVO.setCompanyCode(containerVo.getCompanyCode());
			uldResditVO.setEventAirport(containerVo.getAssignedPort());
			uldResditVO.setEventCode(containerVo.getEventCode());
			uldResditVO.setCarrierId(containerVo.getCarrierId());
			uldResditVO.setFlightNumber(containerVo.getFlightNumber());
			uldResditVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
			uldResditVO.setSegmentSerialNumber(containerVo.getSegmentSerialNumber());
			uldResditVO.setResditSentFlag(MailConstantsVO.FLAG_YES);
			uldResditVO.setProcessedStatus(MailConstantsVO.FLAG_YES);
			uldResditVO.setUldNumber(containerVo.getContainerNumber());
			uldResditVO.setEventDate(containerVo.getEventTime());
			uldResditVO.setCarditKey(containerVo.getCarditKey());
			if (!MailConstantsVO.RESDIT_UPLIFTED.equals(containerVo.getEventCode())) {
				uldResditVO.setPaOrCarrierCode(containerVo.getPaCode());
			}
			uldResdit = new UldResdit(uldResditVO);
			containerVo.setEventSequenceNumber(uldResdit.getSequenceNumber());
			log.debug(CLASS + " : " + "constructUldResditVO" + " Exiting");
		}
	}

	/**
	 * @author a-1936Added By Karthick V as the part of the NCA Mail Tracking CR This method is used to Update the Segment Details For the Containers
	 * @throws ContainerAssignmentException
	 */
	private void updateSegmentDetailsForContainers(Collection<ContainerVO> containerVos)
			throws ContainerAssignmentException {
		log.debug("Resdit Controller" + " : " + "updateSegmentDetailsForContainers" + " Entering");
		ContainerVO containerVo = new ArrayList<ContainerVO>(containerVos).get(0);
		if (containerVo.getSegmentSerialNumber() == 0 && containerVo.getFlightSequenceNumber() > 0) {
			new MailController().validateFlightForSegment(constructOprFltForULDResdits(containerVo), containerVos);
		}
		log.debug("Resdit Controller" + " : " + "updateSegmentDetailsForContainers" + " Exiting");
	}

	private OperationalFlightVO constructOprFltForULDResdits(ContainerVO containerVo) {
		log.debug("Resdit Controller" + " : " + "constructOprFltForULDResdits" + " Entering");
		OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
		operationalFlightVo.setCompanyCode(containerVo.getCompanyCode());
		operationalFlightVo.setFlightNumber(containerVo.getFlightNumber());
		operationalFlightVo.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		operationalFlightVo.setCarrierId(containerVo.getCarrierId());
		log.debug("Resdit Controller" + " : " + "constructOprFltForULDResdits" + " Exiting");
		return operationalFlightVo;
	}

	/**
	 * Invokes the EVT_RCR proc A-1739
	 * @param companyCode
	 * @throws SystemException
	 */
	public void invokeResditReceiver(String companyCode) {
		log.debug(CLASS + " : " + "invokeResditReceiver" + " Entering");
		new Resdit().invokeResditReceiver(companyCode);
		log.debug(CLASS + " : " + "invokeResditReceiver" + " Exiting");
	}

	/**
	 * Starts the resditBuilding Sep 8, 2006, a-1739
	 * @param companyCode
	 * @throws SystemException
	 * @return Collection<ResditEventVO>
	 */
	public Collection<ResditEventVO> checkForResditEvents(String companyCode) {
		log.debug(CLASS + " : " + "checkForResditEvents" + " Entering");
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.debug("" + "Resdit Enabled " + " " + resditEnabled);
			Collection<ResditEventVO> resditEvents = new Resdit().findResditEvents(companyCode);
			if (resditEvents != null && resditEvents.size() > 0) {
				updateResditReadTimes(resditEvents);
			}
			return resditEvents;
		} else {
			return null;
		}
	}
	private void updateResditReadTimes(Collection<ResditEventVO> resditEvents) {
		log.debug(CLASS + " : " + "updateResditReadTimes" + " Entering");
		try {
			for (ResditEventVO resditEventVO : resditEvents) {
				ResditEvent resditEvent = ResditEvent.find(constructResditEventPK(resditEventVO));
				String uniqueId = new StringBuilder().append(System.currentTimeMillis())
						.append(resditEventVO.getResditEventCode()).toString();
				resditEvent.setUniqueIdForResdit(uniqueId);
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
		log.debug(CLASS + " : " + "updateResditReadTimes" + " Exiting");
	}

	/**
	 * Added by A-2135 for QF CR 1517 updateResditSendStatus
	 * @throws SystemException
	 */
	public void updateResditSendStatus(ResditMessageVO resditMessageVO) {
		log.debug(CLASS + " : " + "updateResditSendStatus" + " Entering");
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		String companyCode = logonAttributes.getCompanyCode();
		String mailBagid = "";
		String controlReferenceNumber = resditMessageVO.getInterchangeControlReference();
		String fileName = resditMessageVO.getResditFileName();
		ZonedDateTime sendDate = localDateUtil.getLocalDate(null, true);
		resditMessageVO.setSendDate(LocalDateMapper.toLocalDate(sendDate));
		MailResditFileLogVO mailResditFileLogVO = new MailResditFileLogVO();
		mailResditFileLogVO.setCompanyCode(companyCode);
		mailResditFileLogVO.setInterchangeControlReference(controlReferenceNumber);
		mailResditFileLogVO.setFileName(fileName);
		ArrayList<MailResditFileLog> mailResditFileLog = (ArrayList<MailResditFileLog>) MailResditFileLog
				.findMailResditFileLog(mailResditFileLogVO);
		if (mailResditFileLog != null && !mailResditFileLog.isEmpty()) {
			for (MailResditFileLog mailResditFilelog : mailResditFileLog) {
				mailResditFilelog.setSendDate(sendDate);
			}
		}
		saveResditFileLogsAndUpdateMailbagHistory(resditMessageVO);
		log.debug(CLASS + " : " + "updateResditSendStatus" + " Exiting");
	}

	/**
	 * @author A-5526
	 * @param resditMessageVO
	 * @throws SystemException Added as part of bug ICRD-138578 by A-5526
	 */
	private void saveResditFileLogsAndUpdateMailbagHistory(ResditMessageVO resditMessageVO) {
		log.debug(
				"ResditController===========>>>" + " : " + "saveResditFileLogsAndUpdateMailbagHistory " + " Entering");
		Collection<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.SYSPAR_USPS_ENHMNT);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		saveResditFileLogs(resditMessageVO);
		Collection<ResditEventVO> mailResditEventVOs =
				classicVOConversionMapper_.copyResditEventVO_classic(resditMessageVO
				.getResditEventVOs());
		boolean canFlagHisForMailResdit = canCreateHistoryForMailResdits(resditMessageVO);
		Map oneTimesMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		Collection<String> resditToAirlineList = new ArrayList<String>();
		Collection<String> resditToExcludeAirlineList = new ArrayList<String>();
		Collection<ConsignmentInformationVO> consignmentInformationVOsToInclude = new ArrayList<ConsignmentInformationVO>();
		ResditMessageVO airlineResditMessageVO = null;
		String contractReference = null;
		String contractedAirline = null;
		String excludeAirline = "JL";
		oneTimeList.add(RESDIT_TO_AIRLINE_LIST);
		oneTimeList.add(RESDIT_EXD_AIRLINE_LIST);
		try {
			oneTimesMap = sharedDefaultsProxy.findOneTimeValues(resditMessageVO.getCompanyCode(), oneTimeList);
			log.debug("\n\n RESDIT_TO_AIRLINE hash map******************" + oneTimesMap);
		} catch (BusinessException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		if (oneTimesMap != null) {
			Collection<OneTimeVO> resditToAirlineConfigList = (Collection<OneTimeVO>) oneTimesMap
					.get(RESDIT_TO_AIRLINE_LIST);
			if (resditToAirlineConfigList != null) {
				for (OneTimeVO oneTimeVO : resditToAirlineConfigList) {
					resditToAirlineList.add(oneTimeVO.getFieldValue());
				}
			}
			resditToAirlineConfigList = (Collection<OneTimeVO>) oneTimesMap.get(RESDIT_EXD_AIRLINE_LIST);
			if (resditToAirlineConfigList != null) {
				for (OneTimeVO oneTimeVO : resditToAirlineConfigList) {
					resditToExcludeAirlineList.add(oneTimeVO.getFieldValue());
				}
			}
		}
		if (resditToAirlineList != null && resditToAirlineList.size() > 0) {
			log.debug("\n\n RESDIT_TO_AIRLINE PROCESS STARTS******************" + oneTimesMap);
			for (String ccAirline : resditToAirlineList) {
				if (RESDIT_TO_AIRLINE_AA.equals(ccAirline)) {
					if (systemParameterMap != null
							&& (MailConstantsVO.FLAG_ACTIVE
							.equals(systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT))
							|| MailConstantsVO.FLAG_YES
							.equals(systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT)))
							&& RESDIT_TO_POST_US.equals(resditMessageVO.getRecipientID())) {
						ResditMessageVO resditMessageCopyVO = new ResditMessageVO();
						resditMessageCopyVO = mailOperationsMapper.copyResditMessageVO(resditMessageVO);
						resditMessageCopyVO.setResditToAirlineCode(ccAirline);
						HashMap<String, String> carditDetailsMap = Resdit
								.findCarditDetailsForResdit(mailResditEventVOs);
						if (carditDetailsMap != null) {
							Collection<ConsignmentInformationVO> consignmentInformationVOs = resditMessageVO
									.getConsignmentInformationVOs();
							consignmentInformationVOsToInclude = new ArrayList<ConsignmentInformationVO>();
							if (consignmentInformationVOs != null && consignmentInformationVOs.size() > 0) {
								for (ConsignmentInformationVO consignmentInformationVO : consignmentInformationVOs) {
									contractReference = carditDetailsMap
											.get(consignmentInformationVO.getConsignmentID());
									contractedAirline = contractReference == null ? null
											: contractReference.substring(contractReference.length() - 2,
											contractReference.length());
									if (contractedAirline != null
											&& resditToExcludeAirlineList.contains(contractedAirline)) {
										continue;
									}
									consignmentInformationVOsToInclude.add(consignmentInformationVO);
								}
							}
						}
						if (consignmentInformationVOsToInclude.size() > 0) {
							resditMessageCopyVO.setConsignmentInformationVOs(consignmentInformationVOsToInclude);
							msgBrokerMessageProxy.encodeAndSaveMessage(resditMessageCopyVO);
							saveResditFileLogs(resditMessageVO);
						}
					}
				} else {
				}
			}
		}
		HashSet<String> consignKeySet = new HashSet<String>();
		for (ResditEventVO resditEventVO : mailResditEventVOs) {
			updateResditStatus(resditEventVO, resditMessageVO);
			if (canFlagHisForMailResdit) {
				createMailbagHistoryForResdit(resditEventVO, resditMessageVO, consignKeySet);
			}
		}
		log.debug("ResditController===========>>>" + " : " + "saveResditFileLogsAndUpdateMailbagHistory " + " Exiting");
	}

	public void buildResdit(Collection<ResditEventVO> resditEvents) {
		log.debug(CLASS + " : " + "buildResdit" + " Entering");

		buildResditProxy(resditEvents);
		log.debug(CLASS + " : " + "buildResdit" + " Exiting");
	}
	public ZonedDateTime findResditEvtDateForULD(ContainerDetailsVO containervo, String eventAirport) {
		log.debug("ResditController===========>>>" + " : " + "findResditEvtDateForULD " + " Entering");
		ZonedDateTime resditEvtDate = localDateUtil.getLocalDate(eventAirport, true);
		boolean isEvtDateSet = false;
		try {
			Collection<FlightSegmentSummaryVO> segmentSummaryVOs = flightOperationsProxy.findFlightSegments(
					containervo.getCompanyCode(), containervo.getCarrierId(), containervo.getFlightNumber(),
					containervo.getFlightSequenceNumber());
			FlightSegmentSummaryVO segmentSummaryVO = null;
			if (segmentSummaryVOs != null && segmentSummaryVOs.size() > 0) {
				for (FlightSegmentSummaryVO segmentSumaryVO : segmentSummaryVOs) {
					if (segmentSumaryVO.getSegmentSerialNumber() == containervo.getSegmentSerialNumber()) {
						segmentSummaryVO = segmentSumaryVO;
					}
				}
			}
			if (segmentSummaryVO != null) {
				FlightSegmentFilterVO segmentFilter = new FlightSegmentFilterVO();
				segmentFilter.setCompanyCode(containervo.getCompanyCode());
				segmentFilter.setFlightCarrierId(containervo.getCarrierId());
				segmentFilter.setFlightNumber(containervo.getFlightNumber());
				segmentFilter.setSequenceNumber(containervo.getFlightSequenceNumber());
				segmentFilter.setOrigin(segmentSummaryVO.getSegmentOrigin());
				segmentFilter.setDestination(segmentSummaryVO.getSegmentDestination());
				FlightSegmentValidationVO segmentValidationVO = flightOperationsProxy
						.validateFlightSegment(segmentFilter);
				log.debug("segmentdata from flt " + segmentValidationVO);
				boolean isAtd = MailConstantsVO.FLAG_YES
						.equals(findSystemParameterValue(MailConstantsVO.RESDIT_ATD_REQD));
				if (isAtd) {
					if (segmentValidationVO.getActualTimeOfDeparture() != null) {
						resditEvtDate = segmentValidationVO.getActualTimeOfDeparture().toZonedDateTime();
					} else {
						resditEvtDate = segmentValidationVO.getScheduleTimeOfDeparture().toZonedDateTime();
					}
				} else {
					resditEvtDate = segmentValidationVO.getScheduleTimeOfDeparture().toZonedDateTime();
				}
				isEvtDateSet = true;
			}
		} finally {
		}
		if (resditEvtDate == null || !isEvtDateSet) {
			resditEvtDate = localDateUtil.getLocalDate(eventAirport, true);
		}
		log.debug("ResditController========>>>" + " : " + "findResditEvtDateforUld " + " Exiting");
		return resditEvtDate;
	}

	/**
	 * @author A-5526Added for CRQ ICRD-233864
	 * @param mailArrivalVO
	 * @param mailbagVOs
	 * @param containerDetailsVOs
	 * @throws SystemException
	 */
	public void triggerReadyfordeliveryResdit(MailArrivalVO mailArrivalVO, Collection<MailbagVO> mailbagVOs,
											  Collection<ContainerDetailsVO> containerDetailsVOs) {
		Map<String, String> cityCache = new HashMap<String, String>();
		Collection<MailbagVO> readyForDeliveryMailbags = new ArrayList<MailbagVO>();
		Collection<ContainerDetailsVO> readyForDeliveryContainers = new ArrayList<ContainerDetailsVO>();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if (mailbagVOs != null && !mailbagVOs.isEmpty())
			for (MailbagVO mailbagVO : mailbagVOs) {
				Mailbag mailbagToFindPA = null;
				String poaCode = null;
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagPk
						.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
								: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
				try {
					mailbagToFindPA = Mailbag.find(mailbagPk);
				} catch (FinderException e) {
					e.getMessage();
				}
				if (mailbagToFindPA != null && mailbagToFindPA.getPaCode() != null) {
					poaCode = mailbagToFindPA.getPaCode();
				} else {
					OfficeOfExchangeVO originOfficeOfExchangeVO;
					originOfficeOfExchangeVO = new MailController().validateOfficeOfExchange(mailbagVO.getCompanyCode(),
							mailbagVO.getOoe());
					poaCode = originOfficeOfExchangeVO.getPoaCode();
				}
				if (mailbagVO.getMailbagId() != null && mailbagVO.getMailbagId().length() == 29) {
					AirlineValidationVO airlineValidationVO = null;
					if (mailbagVO.getCarrierCode() == null) {
						try {
							airlineValidationVO = sharedAirlineProxy.findAirline(mailbagVO.getCompanyCode(),
									mailbagVO.getCarrierId());
						} catch (SharedProxyException sharedProxyException) {
							sharedProxyException.getMessage();
						}
						mailbagVO.setCarrierCode(airlineValidationVO.getAlphaCode());
					}
					mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
				}
				if (isValidDeliveryAirport(mailbagVO.getDoe(), mailbagVO.getCompanyCode(),
						logonAttributes.getAirportCode(), cityCache, poaCode, mailbagVO.getConsignmentDate())) {
					if (isValidDeliveryAirport(mailbagVO.getDoe(), mailbagVO.getCompanyCode(),
							logonAttributes.getAirportCode(), cityCache, poaCode, mailbagVO.getConsignmentDate())) {
						readyForDeliveryMailbags.add(mailbagVO);
					} else {
					}
				}
			}
		for (ContainerDetailsVO arrivedContainer : containerDetailsVOs) {
			if (ContainerDetailsVO.FLAG_YES.equals(arrivedContainer.getPaBuiltFlag())
					&& MailConstantsVO.ULD_TYPE.equals(arrivedContainer.getContainerType())) {
				if (logonAttributes.getAirportCode().equalsIgnoreCase(arrivedContainer.getDestination())) {
					readyForDeliveryContainers.add(arrivedContainer);
				}
			}
		}
		flagReadyForDeliveryResditForMailbags(readyForDeliveryMailbags, mailArrivalVO.getAirportCode());
		flagReadyForDeliveryResditForULD(readyForDeliveryContainers, mailArrivalVO.getAirportCode());
	}

	/**
	 * @for IASCB-37022
	 * @param resditEvents
	 * @throws SystemException
	 */
	public void buildResditProxy(Collection<ResditEventVO> resditEvents) {
		log.debug("buildResditProxy" + " : " + "buildResditProxy" + " Entering");
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.debug("" + "Resdit Enabled " + " " + resditEnabled);
			if (resditEvents != null && resditEvents.size() > 0) {
				ResditMessageVO resditMessageVO = new Resdit().buildResditMessageVO(resditEvents);
				resditMessageVO.setResditEventVOs(
						classicVOConversionMapper_.copyResditEvent_classic(resditEvents));
				if (resditMessageVO.getConsignmentInformationVOs() != null
						&& (resditMessageVO.getConsignmentInformationVOs().size() > 0)) {
					sendResditMessage(resditMessageVO, resditEvents);
				} else {
					log.error("ConsignmentInformationVOs null in resdit.");
				}
			}
		}
		log.debug("buildResditProxy" + " : " + "buildResditProxy" + " Exiting");
	}

	public void populateAndSaveMailResditVO(MailUploadVO mailbagVO, long mailseqnum, long messageIdentifier,
											MailbagVO mailVO) {
		log.debug(CLASS + " : " + "construct MailResditVO" + " Entering");
		String partyIdentifier;
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
		Page<OfficeOfExchangeVO> exchangeOfficeDetails;
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailResditVO.setMailId(mailbagVO.getMailTag());
		String mailboxIdFromConfig = MailMessageConfiguration.findMailboxIdFromConfig(mailVO);
		if (mailboxIdFromConfig != null && !mailboxIdFromConfig.isEmpty()) {
			mailResditVO.setMailboxID(mailboxIdFromConfig);
		}
		mailResditVO.setMailSequenceNumber(mailseqnum);
		mailResditVO.setMessageIdentifier(messageIdentifier);
		if (mailbagVO.getScannedPort() != null) {
			mailResditVO.setEventAirport(mailbagVO.getScannedPort());
		}
		String scanType = null;
		if (mailbagVO.getScanType() != null) {
			if ("ACP".equals(mailbagVO.getScanType())) {
				scanType = "74";
			} else if ("ASG".equals(mailbagVO.getScanType())) {
				scanType = "24";
			} else if ("DLV".equals(mailbagVO.getScanType())) {
				scanType = "21";
			}
		}
		mailResditVO.setEventCode(scanType);
		if (!(MailConstantsVO.RESDIT_PENDING.equals(scanType) || MailConstantsVO.RESDIT_PENDING_M49.equals(scanType))) {
			mailResditVO.setCarrierId(mailbagVO.getCarrierId());
			mailResditVO.setFlightNumber(mailbagVO.getFlightNumber());
			mailResditVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			mailResditVO.setPaOrCarrierCode(mailbagVO.getCarrierCode());
			AirlineValidationVO airlineValidationVO = null;
			if (mailbagVO.getCompanyCode() != null && mailbagVO.getCarrierCode() != null) {
				try {
					airlineValidationVO = sharedAirlineProxy.validateAlphaCode(mailbagVO.getCompanyCode(),
							mailbagVO.getCarrierCode());
				} catch (SharedProxyException e) {
					airlineValidationVO = null;
				}
				mailResditVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
			}
		}
		mailResditVO.setResditSentFlag("I");
		mailResditVO.setProcessedStatus(MailConstantsVO.FLAG_YES);
		mailResditVO.setUldNumber(mailbagVO.getContainerNumber());
		mailResditVO.setEventDate(mailbagVO.getScannedDate() != null ? mailbagVO.getScannedDate()
				: localDateUtil.getLocalDate(mailbagVO.getScannedPort(), true));
		if (mailbagVO.getMailTag() != null && mailbagVO.getMailTag().length() == 29) {
			String paCode = "";
			Mailbag mailbagToFindPA = null;
			String poaCode = null;
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPk.setMailSequenceNumber(findMailSequenceNumber(mailbagVO.getMailTag(), mailbagVO.getCompanyCode()));
			try {
				mailbagToFindPA = Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				e.getMessage();
			}
			if (mailbagToFindPA != null && mailbagToFindPA.getPaCode() != null) {
				poaCode = mailbagToFindPA.getPaCode();
			} else {
				poaCode = mailbagVO.getPaCode();
			}
			mailResditVO.setEventAirport(mailbagVO.getScannedPort());
			if (mailResditVO.getFlightNumber() == null || mailResditVO.getFlightNumber().isEmpty()) {
				mailResditVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
			}
			if (scanType == null || (MailConstantsVO.RESDIT_DELIVERED.equals(scanType)
					|| MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(scanType))) {
				poaCode = new MailController().findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),
						mailbagVO.getMailTag().substring(6, 12));
			} else {
				paCode = new MailController().findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),
						mailbagVO.getMailTag().substring(0, 6));
			}
			if (paCode != null) {
				partyIdentifier = findPartyIdentifierForPA(mailbagVO.getCompanyCode(), paCode);
				mailResditVO.setPartyIdentifier(partyIdentifier);
			}
		}
		mailResditVO.setSenderIdentifier(mailbagVO.getMessageSenderIdentifier());
		mailResditVO.setRecipientIdentifier(mailbagVO.getMessageRecipientIdentifier());
		if (mailResditVO != null) {
			mailResditVOs.add(mailResditVO);
			stampResdits(mailResditVOs);
		}
	}

	public void updateMessageIdentifierOfResditMessage(long messageIdentifier, Collection<ResditEventVO> resditEventVOs,
													   ResditMessageVO resditMessageVO) {
		for (ResditEventVO resditEventVO : resditEventVOs) {
			Collection<ConsignmentInformationVO> consignments = resditMessageVO.getConsignmentInformationVOs();
			if (consignments != null && consignments.size() > 0) {
				for (ConsignmentInformationVO consignVO : consignments) {
					if (String.valueOf(consignVO.getConsignmentEvent()).equals(resditEventVO.getResditEventCode())) {
						Collection<ReceptacleInformationVO> receptacles = consignVO.getReceptacleInformationVOs();
						if (receptacles != null && receptacles.size() > 0) {
							for (ReceptacleInformationVO receptacleVO : receptacles) {
								try {
									MailResdit mailResdit = MailResdit
											.find(constructMailResditPK(resditEventVO, receptacleVO));
									mailResdit.setMessageIdentifier(messageIdentifier);
								} catch (FinderException ex) {
									log.debug("Trying to update Message Identifier....just ignore exception!!!!"
											+ receptacleVO.getReceptacleID());
								}
							}
						}
					}
				}
			}
		}
	}
	public void flagResditsForAcceptance(MailAcceptanceVO mailAcceptanceVO,
										 boolean hasFlightDeparted, Collection<MailbagVO> acceptedMailbags,
										 Collection<ContainerDetailsVO> acceptedUlds) throws SystemException {
		log.debug(CLASS, "flagResditsForAcceptance");
		String companyCode = mailAcceptanceVO.getCompanyCode();
		String ownAirlineCode = mailAcceptanceVO.getOwnAirlineCode();
		String pol = mailAcceptanceVO.getPol();
		String carrierCode = mailAcceptanceVO.getFlightCarrierCode();
		boolean isHandoverOnline = false;
		boolean isHandoverOffline = false;
		boolean isFlightAssign = false;
		boolean isFromAssign=false;
		String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);//Added by a-7871 for ICRD-240184
		String paCode = null;
		boolean isCoterminus;
		String orgAirport=null;
		OfficeOfExchangeVO originOfficeOfExchangeVO;
		Collection<MailbagVO> uspsMailbags = new ArrayList<MailbagVO>();
		for(MailbagVO mailbagVO:acceptedMailbags){
			if("Y".equals(mailAcceptanceVO.getIsFromTruck())){
				mailbagVO.setIsFromTruck("Y");
			}
			mailbagVO.setFromDeviationList(mailAcceptanceVO.isFromDeviationList());
			if(!mailAcceptanceVO.isAssignedToFlight()){
				mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
				mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				mailbagVO.setFlightDate(null);
				mailbagVO.setSegmentSerialNumber(MailConstantsVO.ZERO);
				mailAcceptanceVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				mailAcceptanceVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
				hasFlightDeparted=false;
			}
			else if(mailAcceptanceVO.isAssignedToFlight()){
				isFromAssign=true;
			}
		}
		if (mailAcceptanceVO.getFlightSequenceNumber() !=
				MailConstantsVO.DESTN_FLT) {
			isFlightAssign = true;
		}
		Collection<MailbagVO> paMailbags = new ArrayList<MailbagVO>();
		Collection<MailbagVO> handoverRcvdMailbags = new ArrayList<MailbagVO>();
		Collection<MailbagVO> handoverOnMailbags = new ArrayList<MailbagVO>();
		Collection<MailbagVO> handoverOffMailbags = new ArrayList<MailbagVO>();
		if(acceptedMailbags != null && acceptedMailbags.size() > 0) {
			groupPACarrMailbags(acceptedMailbags,paMailbags,handoverRcvdMailbags,handoverOnMailbags,
					handoverOffMailbags, mailAcceptanceVO.getOwnAirlineCode(), mailAcceptanceVO.getFlightCarrierCode());
		}
		Collection<ContainerDetailsVO> paContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> handoverRcvdContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> handoverOnContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> handoverOffContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		if(acceptedUlds != null && acceptedUlds.size() > 0) {
			groupPACarrULDs(acceptedUlds,paContainerDetailsVOs,
					handoverRcvdContainerDetailsVOs,handoverOnContainerDetailsVOs,
					handoverOffContainerDetailsVOs, mailAcceptanceVO.getOwnAirlineCode(), mailAcceptanceVO.getFlightCarrierCode());
		}
		if((handoverOnMailbags != null && handoverOnMailbags.size() > 0)
				||(handoverOnContainerDetailsVOs != null && handoverOnContainerDetailsVOs.size() > 0)) {
			isHandoverOnline = true;
		}
		if((handoverOffMailbags != null && handoverOffMailbags.size() > 0)
				||(handoverOffContainerDetailsVOs != null && handoverOffContainerDetailsVOs.size() > 0)) {
			isHandoverOffline = true;
		}
		if(checkForResditConfig()) {
			Map<String, Object> valuesMap = new HashMap<String, Object>();
			valuesMap.put(ACP_ULDS, acceptedUlds);
			valuesMap.put(PA_ULDS, paContainerDetailsVOs);
			valuesMap.put(CARR_ULDS, handoverRcvdMailbags);
			valuesMap.put(PA_MAILS, paMailbags);
			valuesMap.put(CARR_MAILS, handoverRcvdContainerDetailsVOs);
			valuesMap.put(IS_FLTASG, isFlightAssign);

			valuesMap.put(HAS_DEP, hasFlightDeparted);

			flagConfiguredResdits(companyCode, mailAcceptanceVO.getCarrierId(),
					MailConstantsVO.TXN_ACP, mailAcceptanceVO.getPol(),
					acceptedMailbags, valuesMap);
			if(isHandoverOffline) {
				valuesMap.put(IS_HNDOVER, isHandoverOffline);
				flagConfiguredHandoverResdit(companyCode,
						mailAcceptanceVO.getOwnAirlineId(),
						MailConstantsVO.TXN_ACP, mailAcceptanceVO.getPol(),
						acceptedMailbags, valuesMap);
			}
		} else {
			if(!isFromAssign){
				flagHandedoverReceivedForMailbags(handoverRcvdMailbags, pol);
				if(!paMailbags.isEmpty()){
					paCode = mailController.findPAForOfficeOfExchange(paMailbags.iterator().next().getCompanyCode(),  paMailbags.iterator().next().getOoe());
					originOfficeOfExchangeVO=mailController.validateOfficeOfExchange(paMailbags.iterator().next().getCompanyCode(), paMailbags.iterator().next().getOoe());
					String orginAirport=null;
					if(paMailbags.iterator().next().getPol()!=null && !paMailbags.iterator().next().getPol().isEmpty()){
						orginAirport=paMailbags.iterator().next().getPol();
					}else if(paMailbags.iterator().next().getOrigin()!=null && !paMailbags.iterator().next().getOrigin().isEmpty()){
						orginAirport=paMailbags.iterator().next().getOrigin();
					}else if(mailAcceptanceVO!=null){
						orginAirport=mailAcceptanceVO.getPol();
					}
					LoginProfile logonAttributes = contextUtil.callerLoginProfile();
					if(MailConstantsVO.MLD.equals(mailAcceptanceVO.getMailSource()) || MailConstantsVO.ONLOAD_MESSAGE.equals(mailAcceptanceVO.getMailSource())){
						orgAirport=	mailAcceptanceVO.getPol();
					}else{
						orgAirport =  logonAttributes.getStationCode();
					}
					Mailbag mailbagToFindPA = null;
					MailbagPK mailbagPk = new MailbagPK();
					mailbagPk.setCompanyCode(paMailbags.iterator().next().getCompanyCode());
					mailbagPk.setMailSequenceNumber(paMailbags.iterator().next().getMailSequenceNumber()>0?
							paMailbags.iterator().next().getMailSequenceNumber():findMailSequenceNumber(paMailbags.iterator().next().getMailbagId(), paMailbags.iterator().next().getCompanyCode())  );
					try {
						mailbagToFindPA = Mailbag.find(mailbagPk);
					} catch (FinderException e) {
						e.getMessage();
					}
					String orginAirportOfMailbag=null;
					if(mailbagToFindPA.getOrigin()!=null){
						orginAirportOfMailbag=mailbagToFindPA.getOrigin();
					}else{
						if (originOfficeOfExchangeVO != null && originOfficeOfExchangeVO.getAirportCode() == null) {
							String originOfficeOfExchange = originOfficeOfExchangeVO.getCode();
							orginAirportOfMailbag = mailController.findNearestAirportOfCity(paMailbags.iterator().next().getCompanyCode(), originOfficeOfExchange);
						} else {
							orginAirportOfMailbag = originOfficeOfExchangeVO != null ? originOfficeOfExchangeVO.getAirportCode() : null;
						}
					}
					if(mailbagToFindPA!=null && mailbagToFindPA.getPaCode()!=null ){
						paCode=mailbagToFindPA.getPaCode();
					}
					ZonedDateTime dspDate = localDateUtil.getLocalDate(orgAirport, true);
					if (mailbagToFindPA != null) {
						dspDate= localDateUtil.getLocalDateTime(mailbagToFindPA.getDespatchDate(), null);
					}
					isCoterminus=	mailController.validateCoterminusairports(orginAirportOfMailbag,orgAirport,MailConstantsVO.RESDIT_RECEIVED,paCode,dspDate) ||
							mailController.validateCoterminusairports(orginAirportOfMailbag,orgAirport,MailConstantsVO.RESDIT_UPLIFTED,paCode,dspDate);
					if(isCoterminusConfigured!=null && "Y".equals(isCoterminusConfigured))
					{

						if(orgAirport.equalsIgnoreCase(orginAirportOfMailbag) || mailController.checkReceivedFromTruckEnabled(orginAirport,orginAirportOfMailbag,paCode,dspDate)||isCoterminus){// modified by A-8353 for ICRD-336294
							flagReceivedResditForMailbags(paMailbags, pol);
						}}else{
						flagReceivedResditForMailbags(paMailbags, pol);
					}

				}
				flagReceivedResditForUlds(acceptedUlds, pol);
				flagHandedoverReceivedForULDs(handoverRcvdContainerDetailsVOs, pol);
			}
			// flagAssignedResdit
			if (isFlightAssign) {
				flagAssignedResditForMailbags(acceptedMailbags, pol);
				flagAssignedResditForUlds(acceptedUlds, pol);
				flagLoadedResditForMailbags(acceptedMailbags, pol);
				flagLoadedResditForUlds(acceptedUlds, pol);
			}
			// flagHandedOverOfflineResdit
			if (isHandoverOffline) {
				flagHandedOverResditForMailbags(handoverOffMailbags, pol);
				flagHandedOverResditForUlds(handoverOffContainerDetailsVOs, pol);
			}
			//flagHandedOveronlineResdit
			if (isHandoverOnline) {
				flagOnlineHandedoverResditForMailbags(handoverOnMailbags, pol);
				flagOnlineHandedoverResditForUlds(handoverOnContainerDetailsVOs, pol);
			}

			//flagUpliftedResdit only if own carrier
			if (hasFlightDeparted) {
				flagUpliftedResditForMailbags(acceptedMailbags, pol);
				flagUpliftedResditForUlds(acceptedUlds, pol);
			}
		}
		log.debug(CLASS, "flagResditsForAcceptance");
	}
	private void groupPACarrMailbags(Collection<MailbagVO> acceptedMailbags,
									 Collection<MailbagVO> paMailbags, Collection<MailbagVO> handoverRcvdMailbags, Collection<MailbagVO> handoverOnMailbags,
									 Collection<MailbagVO> handoverOffMailbags, String ownAirlineCOde, String flightCarrierCOde) {
		for(MailbagVO mailbagVO : acceptedMailbags) {
			// Added last 2 conditions since if transfer carrier code is own airline then no need to consider as transfer.
			//While accepting to other airline flight we may have to specify ownairline as transfer carrier.
			if(mailbagVO.getTransferFromCarrier() != null &&
					mailbagVO.getTransferFromCarrier().trim().length() > 0 &&
					ownAirlineCOde != null &&
					flightCarrierCOde != null ){
				if(ownAirlineCOde.equals(flightCarrierCOde) && flightCarrierCOde.equals(mailbagVO.getTransferFromCarrier())) {
					paMailbags.add(mailbagVO);
				}else if(ownAirlineCOde.equals(flightCarrierCOde) && !flightCarrierCOde.equals(mailbagVO.getTransferFromCarrier())) {
					handoverRcvdMailbags.add(mailbagVO);
				}else if(!ownAirlineCOde.equals(flightCarrierCOde) && ownAirlineCOde.equals(mailbagVO.getTransferFromCarrier())) {
					handoverOffMailbags.add(mailbagVO);
					paMailbags.add(mailbagVO);
				}else if(!ownAirlineCOde.equals(flightCarrierCOde) && !ownAirlineCOde.equals(mailbagVO.getTransferFromCarrier())) {
//					Operation is between both OAL ;  74 to be sent.
					handoverRcvdMailbags.add(mailbagVO);
					paMailbags.add(mailbagVO);
				}
			} else {
				paMailbags.add(mailbagVO);
			}
		}
	}
	private void groupPACarrULDs(Collection<ContainerDetailsVO> acceptedUlds,
								 Collection<ContainerDetailsVO> paContainerDetailsVOs,
								 Collection<ContainerDetailsVO> handoverRcvdContainerDetailsVOs, Collection<ContainerDetailsVO> handoverOnContainerDetailsVOs,
								 Collection<ContainerDetailsVO> handoverOffContainerDetailsVOs, String ownAirlineCOde, String flightCarrierCOde) {
		for(ContainerDetailsVO containerDetailsVO : acceptedUlds) {
			// Added last 2 conditions since if transfer carrier code is own airline then no need to consider as transfer.
			//While accepting to other airline flight we may have to specify ownairline as transfer carrier.
			if(containerDetailsVO.getTransferFromCarrier() != null &&
					containerDetailsVO.getTransferFromCarrier().trim().length() > 0 &&
					ownAirlineCOde != null &&
					flightCarrierCOde != null ){
				if(ownAirlineCOde.equals(flightCarrierCOde) && flightCarrierCOde.equals(containerDetailsVO.getTransferFromCarrier())) {
					handoverOnContainerDetailsVOs.add(containerDetailsVO);
					paContainerDetailsVOs.add(containerDetailsVO);
				}else if(ownAirlineCOde.equals(flightCarrierCOde) && !flightCarrierCOde.equals(containerDetailsVO.getTransferFromCarrier())) {
					handoverRcvdContainerDetailsVOs.add(containerDetailsVO);
				}else if(!ownAirlineCOde.equals(flightCarrierCOde) && ownAirlineCOde.equals(containerDetailsVO.getTransferFromCarrier())) {
					handoverOffContainerDetailsVOs.add(containerDetailsVO);
					paContainerDetailsVOs.add(containerDetailsVO);
				}else if(!ownAirlineCOde.equals(flightCarrierCOde) && !ownAirlineCOde.equals(containerDetailsVO.getTransferFromCarrier())) {
					//Operation is between both OAL ; no resdit sent.
				}
			} else {
				paContainerDetailsVOs.add(containerDetailsVO);
			}
		}
	}
	private void flagConfiguredResdits(String companyCode, int carrierId,
									   String txnId, String eventPort, Collection<MailbagVO> mailbagVOs,
									   Map<String, Object> valuesMap)
			throws SystemException {
		log.debug(CLASS, "flagConfiguredResdits");
		ResditTransactionDetailVO txnDetailVO =
				findResditConfigurationForTxn(companyCode, carrierId, txnId);
		if(txnDetailVO != null) {
			flagResditsForTransaction(companyCode, txnDetailVO,
					eventPort, mailbagVOs, valuesMap);
		} else {
			log.debug("Transactiondetails not configured");
		}
		log.debug(CLASS, "flagConfiguredResdits");
	}
	private void flagResditsForTransaction(
			String companyCode, ResditTransactionDetailVO txnDetailVO,
			String eventPort, Collection<MailbagVO> mailbagVOs,
			Map<String, Object> valuesMap)
			throws SystemException {
		log.debug(CLASS, "flagResditsForTransaction");
		log.debug("map --->> " + valuesMap);
		log.debug("txnDtl -->> " + txnDetailVO);
		boolean isSpecificResdit = false;
		log.debug("isSpecificResdit -->> " + isSpecificResdit);
		Map<String, Collection<CarditTransportationVO>> carditCache =
				new HashMap<String, Collection<CarditTransportationVO>>();
		boolean isLookupCarditDone = false;
		Collection<ContainerDetailsVO> containerDetails =
				(Collection<ContainerDetailsVO>)valuesMap.get(ACP_ULDS);
		if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getReceivedResditFlag())) {
			Collection<MailbagVO> paMailbags =
					(Collection<MailbagVO>)valuesMap.get(PA_MAILS);
			if(paMailbags != null && paMailbags.size() > 0) {
				log.debug(" flagging recevd");
				flagReceivedResditForMailbags(paMailbags, eventPort);
			}
			Collection<MailbagVO> newArrvMails =
					(Collection<MailbagVO>)valuesMap.get(NEWARR_MAILS);
			if(newArrvMails != null && newArrvMails.size() > 0) {
				if(isSpecificResdit) {
					if(!isLookupCarditDone) {
						updateCarditDetailsForMailbags(mailbagVOs);
						isLookupCarditDone = true;
					}
					log.debug(" flagging recevd for arrivalmails cardit");
					flagReceivedResditForCarditDetails(newArrvMails,  carditCache,
							eventPort);
				} else {
					log.debug(" flagging recevd for arrivalmails  normal");
					flagReceivedResditForMailbags(newArrvMails, eventPort);
				}
			}
			/**
			 * Added for Sending received resdits for ULD
			 */
			Collection<ContainerDetailsVO> paContainerDetails =
					(Collection<ContainerDetailsVO>)valuesMap.get(PA_ULDS);
			if(paContainerDetails != null && paContainerDetails.size() > 0) {
				flagReceivedResditForUlds(paContainerDetails, eventPort);
			}

		}
		if(MailConstantsVO.FLAG_YES.equals(
				txnDetailVO.getHandedOverReceivedResditFlag())) {
			Collection<MailbagVO> carrMailbags =
					(Collection<MailbagVO>)valuesMap.get(CARR_MAILS);
			log.debug(" flagging handover recevd");
			if(carrMailbags != null && carrMailbags.size() > 0) {
				flagHandedoverReceivedForMailbags(carrMailbags, eventPort);
			}
			Collection<ContainerDetailsVO> carrContainerDetails =
					(Collection<ContainerDetailsVO>)valuesMap.get(CARR_ULDS);
			if(carrContainerDetails != null && carrContainerDetails.size() > 0) {
				flagHandedoverReceivedForULDs(carrContainerDetails, eventPort);
			}
		}
		if(MailConstantsVO.FLAG_YES.equals(
				txnDetailVO.getAssignedResditFlag())) {
			Boolean asgFlg = (Boolean)valuesMap.get(IS_FLTASG);
			boolean isFlightAsgn = false;
			if(asgFlg != null) {
				isFlightAsgn = asgFlg.booleanValue();
			}
			if(isFlightAsgn) {
				if(mailbagVOs != null && mailbagVOs.size() > 0) {
					log.debug(" flagging assigned ");
					flagAssignedResditForMailbags(mailbagVOs, eventPort);
				}
				if(containerDetails != null && containerDetails.size() > 0) {
					flagAssignedResditForUlds(containerDetails, eventPort);
				}
			}
		}
		Boolean depFlg = (Boolean)valuesMap.get(HAS_DEP);
		boolean hasDept = false;
		if(depFlg != null) {
			hasDept = depFlg.booleanValue();
		}
		if(hasDept && MailConstantsVO.FLAG_YES.equals(
				txnDetailVO.getUpliftedResditFlag())) {
			if(mailbagVOs != null && mailbagVOs.size() > 0) {
				if(isSpecificResdit) {
					if(!isLookupCarditDone) {
						updateCarditDetailsForMailbags(mailbagVOs);
						isLookupCarditDone = true;
					}
					log.debug(" flagging uplifed w/ cardit ");
					flagResditOnDepartForCarditDetails(
							mailbagVOs, carditCache, eventPort,
							MailConstantsVO.RESDIT_UPLIFTED);
				} else {
					log.debug(" flagging uplifed ");
					flagUpliftedResditForMailbags(mailbagVOs, eventPort);
				}
			}
			if(containerDetails != null && containerDetails.size() > 0) {
				flagUpliftedResditForUlds(containerDetails, eventPort);
			}
		}
		if(MailConstantsVO.FLAG_YES.equals(
				txnDetailVO.getLoadedResditFlag())) {
			if(mailbagVOs != null && mailbagVOs.size() > 0) {
				if(isSpecificResdit) {
					if(!isLookupCarditDone) {
						updateCarditDetailsForMailbags(mailbagVOs);
						isLookupCarditDone = true;
					}
					log.debug(" flagging loaded w/ cardit ");
					flagResditOnDepartForCarditDetails(
							mailbagVOs, carditCache, eventPort,
							MailConstantsVO.RESDIT_LOADED);
				} else {
					log.debug(" flagging loaded ");
					flagLoadedResditForMailbags(mailbagVOs, eventPort);
				}
			}
			if(containerDetails != null && containerDetails.size() > 0) {
				flagLoadedResditForUlds(containerDetails, eventPort);
			}
		}
		/**
		 *	Currently based on ownairlines' config
		 *
		 */
		if(MailConstantsVO.FLAG_YES.equals(
				txnDetailVO.getHandedOverResditFlag())) {
			Collection<MailbagVO> onlineTransferMails =
					(Collection<MailbagVO>)valuesMap.get(OLIN_MAILS);
			if(onlineTransferMails != null && onlineTransferMails.size() > 0) {
				log.debug(" flagging online h/ov ");
				flagOnlineHandedoverResditForMailbags(onlineTransferMails, eventPort);
			}

			/**
			 * Added  for sending Handover_Received Resdits for ULDs
			 */
			if(containerDetails != null && containerDetails.size() > 0) {
				flagOnlineHandedoverResditForUlds(containerDetails, eventPort);
			}
		}
		/*
		 * Flagging DELIVERED RESDIT,based on the DELIVERED RESDIT MASTER Configuration
		 */
		if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getDeliveredResditFlag())){
			Collection<MailbagVO> deliveredMails =
					(Collection<MailbagVO>)valuesMap.get(DLVD_MAILS);
			Collection<ContainerDetailsVO> deliveredContainers =
					(Collection<ContainerDetailsVO>)valuesMap.get(DLVD_ULDS);
			if(deliveredMails != null && deliveredMails.size() >0){
				log.debug(" flagging delivered");
				flagDeliveredResditForMailbags(deliveredMails, eventPort);
			}
			if(deliveredContainers!=null && deliveredContainers.size()>0){
				flagDlvdResditsForUldFromArrival(deliveredContainers, eventPort);
			}
		}
		/*
		 * Flagging RETURNED RESDIT,based on the RETURNED RESDIT MASTER Configuration
		 */
		if(MailConstantsVO.FLAG_YES.equals(txnDetailVO.getReturnedResditFlag())){
			Collection<MailbagVO> mailbags =
					(Collection<MailbagVO>)valuesMap.get(RETN_MAILS);
			if(mailbags != null && mailbags.size() >0){
				log.debug(" flagging returned");
				flagReturnedResditForMailbags(mailbags);
			}
		}
		log.debug(CLASS, "flagResditsForTransaction");
	}
	private void updateCarditDetailsForMailbags(
			Collection<MailbagVO> mailbagVOs) throws SystemException {
		for(MailbagVO mailbagVO : mailbagVOs) {
			mailbagVO.setCarditKey(
					Cardit.findCarditForMailbag(mailbagVO.getCompanyCode(),
							mailbagVO.getMailbagId()));
		}
	}
	private void flagReceivedResditForCarditDetails(
			Collection<MailbagVO> newArrvMails,
			Map<String, Collection<CarditTransportationVO>> carditCache,
			String eventPort) throws SystemException {
		log.debug(CLASS, "flagReceivedResditForCardit");
		Collection<MailbagVO> noCarditMails = new ArrayList<MailbagVO>();
		boolean hasCardit = false;
		for(MailbagVO mailbagVO : newArrvMails) {
			hasCardit = false;
			if(mailbagVO.getCarditKey() != null) {
				Collection<CarditTransportationVO> transportationVOs =
						carditCache.get(mailbagVO.getCarditKey());
				if(transportationVOs == null) {
					transportationVOs =
							Cardit.findCarditTransportationDetails(mailbagVO.getCompanyCode(),
									mailbagVO.getCarditKey());
				}
				if(transportationVOs != null) {
					int idx = 0;
					for(CarditTransportationVO transportationVO : transportationVOs) {
						if(mailbagVO.getCarrierId() == transportationVO.getCarrierID()) {
							hasCardit = true;
							//if our carrier is the first itinery the we have to send 74
							if(idx == 0) {
								flagReceivedResditForCarditMail(mailbagVO, transportationVO);
								break;
								//else got to send 43
							} else {
								flagHandedoverReceivedForCarditMail(
										mailbagVO, transportationVO);
								break;
							}
						}
						idx++;
					}
				}
			}
			if(!hasCardit) {
				noCarditMails.add(mailbagVO);
			}
		}
		if(noCarditMails.size() > 0) {
			flagReceivedResditForMailbags(noCarditMails, eventPort);
		}
		log.debug(CLASS, "flagReceivedResditForCardit");
	}
	private void flagReceivedResditForCarditMail(
			MailbagVO mailbagVO, CarditTransportationVO transportationVO)
			throws SystemException {
		log.debug(CLASS, "flagReceivedResditForCarditMail");
		MailResditVO mailResditVO =
				constructTRTResditVO(mailbagVO, transportationVO,
						MailConstantsVO.RESDIT_RECEIVED);
		if(canFlagResditForEvent(MailConstantsVO.RESDIT_RECEIVED,
				mailResditVO, mailbagVO)) {
			Collection<String> paramNames = new ArrayList<String>();
			paramNames.add(MailConstantsVO.RESDIT_RCVD_CONFTIM);
			Map<String, String> paramValMap =
					null;
			try {
				paramValMap = neoMastersServiceUtils.findSystemParameterByCodes(
						paramNames);
			} catch (BusinessException e) {
				log.error(e.getMessage());
			}
			int minutes = Integer.parseInt(
					paramValMap.get(MailConstantsVO.RESDIT_RCVD_CONFTIM));
			mailResditVO.setEventDate(
					transportationVO.getDepartureTime().minusMinutes(minutes));
			mailResditVO.setEventCode(MailConstantsVO.RESDIT_RECEIVED);
			mailResditVO.setPaOrCarrierCode(
					mailController.findPAForOfficeOfExchange(
							mailbagVO.getCompanyCode(), mailbagVO.getOoe()));
			if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED))
					&& !canStampResdits(mailResditVO)) {
				return;
			}
			new MailResdit(mailResditVO);
		}
		log.debug(CLASS, "flagReceivedResditForCarditMail");
	}
	private boolean canFlagResditForEvent(String eventCode,MailResditVO mailResditVO,
										  MailbagVO mailbagVO) throws SystemException {
		log.debug(CLASS, "canFlagResditForEvent");
		boolean canFlag = false;
		/*
		 * Sets the Event-Code to the Mail-Resdit Vo..
		 */
		mailResditVO.setEventCode(eventCode);
		mailResditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		/*
		 * This method does whether RESDIT exists or not
		 */
		if(MailConstantsVO.RESDIT_ASSIGNED .equals(mailResditVO.getEventCode())
				|| MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(mailResditVO.getEventCode())
				|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailResditVO.getEventCode())){
			//Modified  for found mailbag
			if(MailConstantsVO.OPERATION_INBOUND.equals(mailbagVO.getOperationalStatus())) {
				if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
					canFlag= true;
				}
			} else {
				Collection<MailResditVO> mailResditVOs =
						MailResdit.findResditFlightDetailsForMailbag(mailResditVO);
				if(mailResditVOs == null ||  mailResditVOs.size() == 0){
					canFlag= true;
				} else {
					boolean hasMatch = false;
					for(MailResditVO flightMalResditVO : mailResditVOs) {
						if(mailResditVO.getEventAirport().equals(flightMalResditVO.getEventAirport())) {
							hasMatch = true;
							if(flightMalResditVO.getFlightNumber().equals(mailResditVO.getFlightNumber())&&
									flightMalResditVO.getCarrierId()==mailResditVO.getCarrierId() &&
									flightMalResditVO.getFlightSequenceNumber()==mailResditVO.getFlightSequenceNumber() &&
									flightMalResditVO.getSegmentSerialNumber()== mailResditVO.getSegmentSerialNumber()){
								canFlag=false;
							}else{
								/*
								 * if already present for another flight then
								 * if that RESDIT already sent then send this resdit again
								 * or else if that RESDIT not sent, then delete that event
								 * and send again.
								 */
								canFlag = true;
								if(MailConstantsVO.FLAG_NO.equals(flightMalResditVO.getResditSentFlag())){
									flightMalResditVO.setCompanyCode(mailResditVO.getCompanyCode());
									flightMalResditVO.setMailId(mailResditVO.getMailId());
									flightMalResditVO.setEventCode(mailResditVO.getEventCode());
									List<MailResdit> mailResditsForFlight  = MailResdit.findMailResdits(flightMalResditVO);
									if(mailResditsForFlight != null && mailResditsForFlight.size() > 0) {
										MailResdit mailResditForFlight = mailResditsForFlight.get(0);
										if(!MailConstantsVO.FLAG_YES.equals(
												mailResditForFlight.getProcessedStatus())) {
											//otherwise this might be in timer table
											mailResditForFlight.remove();
										}
									}
								} else {
									//for nca even if different flight if it is already sent
									//then no need to sent again. That different flight may be the
									//copied cardit flight
									//canFlag = false;
								}
							}
						}
					}
					if(!hasMatch) {
						canFlag = true;
					}
				}
			}
		} else {
			boolean isResditExisting = MailResdit.checkResditExists(mailResditVO, false);
			if(MailConstantsVO.RESDIT_RECEIVED.equals(mailResditVO.getEventCode())){
				log.debug("THE STATUS IS RECEIVED");
				// 74 need to be sent only from orgin port.
				//  74 will be stamped , rest all validation to be done on procedure.
				if(isResditExisting) {

				} else {
					canFlag = true;
				}
			} else {
				/*
				 * All the Checks Required for the Returned ,Delivered,Pending ,HandedOverOffline
				 * are Done here
				 */
				if (!isResditExisting) {
					canFlag=true;
				}
			}
		}
		log.debug(CLASS, "canFlagResditForEvent"+ canFlag);
		return canFlag;
	}
	void flagResditOnDepartForCarditDetails(
			Collection<MailbagVO> mailbagVOs,
			Map<String, Collection<CarditTransportationVO>> carditCache,
			String eventPort, String eventCode)
			throws SystemException {
		log.debug(CLASS, "flagDepartedResditForCarditDetails");
		Collection<MailbagVO> noCarditMails =
				new ArrayList<MailbagVO>();
		for(MailbagVO mailbagVO : mailbagVOs) {
			if(mailbagVO.getCarditKey() != null) {
				Collection<CarditTransportationVO> transportationVOs =
						carditCache.get(mailbagVO.getCarditKey());
				if(transportationVOs == null) {
					transportationVOs =
							Cardit.findCarditTransportationDetails(mailbagVO.getCompanyCode(),
									mailbagVO.getCarditKey());
				}
				if(transportationVOs != null) {
					CarditTransportationVO transportationVOToFlag = null;
					for(CarditTransportationVO transportationVO : transportationVOs) {
						if(mailbagVO.getCarrierId() == transportationVO.getCarrierID()) {
							//trt may be specified for both inb and outbound
							//so need to find correct segment
							if(MailConstantsVO.OPERATION_INBOUND.equals(
									mailbagVO.getOperationalStatus()) &&
									mailbagVO.getScannedPort().equals(
											transportationVO.getArrivalPort())) {
								//if our carrier is found then send apprp details in RESDIT
								transportationVOToFlag = transportationVO;
								break;
							} else if(MailConstantsVO.OPERATION_OUTBOUND.equals(
									mailbagVO.getOperationalStatus()) &&
									mailbagVO.getScannedPort().equals(
											transportationVO.getDeparturePort())) {
								//if our carrier is found then send apprp details in RESDIT
								transportationVOToFlag = transportationVO;
								break;
							}
						}
					}
					if(transportationVOToFlag != null) {
						if(MailConstantsVO.RESDIT_LOADED.equals(eventCode)) {
							flagLoadedResditForCarditMail(
									mailbagVO, transportationVOToFlag);
						} else {
							flagUpliftedResditForCarditMail(
									mailbagVO, transportationVOToFlag);
						}
					} else {
						//no matching transportData
						if(MailConstantsVO.OPERATION_OUTBOUND.equals(
								mailbagVO.getOperationalStatus())) {
							noCarditMails.add(mailbagVO);
						}
					}
				}
			} else {
				//no matching cardit
				if(MailConstantsVO.OPERATION_OUTBOUND.equals(
						mailbagVO.getOperationalStatus())) {
					noCarditMails.add(mailbagVO);
				}
			}
		}
//		no cardit found
		if(noCarditMails.size() > 0) {
			if(MailConstantsVO.RESDIT_LOADED.equals(eventCode)) {
				flagLoadedResditForMailbags(noCarditMails, eventPort);
			} else if(MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)) {
				flagUpliftedResditForMailbags(noCarditMails, eventPort);
			}
		}
		log.debug(CLASS, "flagDepartedResditForCarditDetails");
	}
	private void flagLoadedResditForCarditMail(MailbagVO mailbagVO,
											   CarditTransportationVO transportationVO) throws SystemException {
		log.debug(CLASS, "flagDepartedResditForCarditMail");
		MailResditVO mailResditVO =
				constructTRTResditVO(mailbagVO, transportationVO,
						MailConstantsVO.RESDIT_LOADED);
		mailResditVO.setEventDate(
				transportationVO.getDepartureTime());
		if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED))
				&& !canStampResdits(mailResditVO)) {
			return;
		}
		new MailResdit(mailResditVO);
		log.debug(CLASS, "flagDepartedResditForCarditMail");
	}
	private void flagUpliftedResditForCarditMail(MailbagVO mailbagVO,
												 CarditTransportationVO transportationVO) throws SystemException {
		log.debug(CLASS, "flagUpliftedResditForCarditMail");
		MailResditVO mailResditVO =
				constructTRTResditVO(mailbagVO, transportationVO,
						MailConstantsVO.RESDIT_UPLIFTED);
		mailResditVO.setEventDate(
				transportationVO.getDepartureTime());
		if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED))
				&& !canStampResdits(mailResditVO)) {
			return;
		}
		new MailResdit(mailResditVO);
		log.debug(CLASS, "flagUpliftedResditForCarditMail");
	}
	private MailResditVO constructTRTResditVO(MailbagVO mailbagVO,
											  CarditTransportationVO transportationVO, String eventCode) throws SystemException {
		String partyIdentifier;
		Page<OfficeOfExchangeVO> exchangeOfficeDetails;
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailResditVO.setMailId(mailbagVO.getMailbagId());
		mailResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
		mailResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
		mailResditVO.setUldNumber(mailbagVO.getUldNumber());
		mailResditVO.setFromDeviationList(mailbagVO.isFromDeviationList());
		mailResditVO.setEventAirport(transportationVO.getDeparturePort());
		mailResditVO.setCarrierId(transportationVO.getCarrierID());
		mailResditVO.setFlightNumber(transportationVO.getFlightNumber());
		mailResditVO.setFlightSequenceNumber(
				transportationVO.getFlightSequenceNumber());
		mailResditVO.setPaOrCarrierCode(transportationVO.getCarrierCode());

		mailResditVO.setEventCode(eventCode);
		mailResditVO.setSegmentSerialNumber(
				transportationVO.getSegmentSerialNum());
		if(mailResditVO.getSegmentSerialNumber()==0 && MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)){
			mailResditVO.setSegmentSerialNumber(1);
		}
		if(mailbagVO.getMailbagId()!=null && mailbagVO.getMailbagId().length()==29){
			String paCode="";
			exchangeOfficeDetails = mailController.findOfficeOfExchange(
					mailbagVO.getCompanyCode(), mailbagVO.getMailbagId().substring(0, 6),1);
			if(exchangeOfficeDetails!=null && !exchangeOfficeDetails.isEmpty()){
				OfficeOfExchangeVO officeOfExchangeVO = exchangeOfficeDetails.iterator().next();
				mailResditVO.setMailboxID(officeOfExchangeVO.getMailboxId());
			}
			if(eventCode==null || (MailConstantsVO.RESDIT_DELIVERED.equals(eventCode) ||MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(eventCode) ) )
			{
				paCode =mailController.findPAForOfficeOfExchange(
						mailbagVO.getCompanyCode(),mailbagVO.getMailbagId().substring(6, 12));
			}else{
				paCode = mailController.findPAForOfficeOfExchange(
						mailbagVO.getCompanyCode(),mailbagVO.getMailbagId().substring(0, 6));
			}
			if(paCode!=null){
				partyIdentifier=findPartyIdentifierForPA(mailbagVO.getCompanyCode(),paCode);
				mailResditVO.setPartyIdentifier(partyIdentifier);
			}
		}
		return mailResditVO;
	}
	private void flagDlvdResditsForUldFromArrival(Collection<ContainerDetailsVO> deliveredContainers ,String eventPort)
			throws SystemException{
		Collection<UldResditVO> uldResditVOs = new ArrayList<UldResditVO>();
		if (deliveredContainers != null && deliveredContainers.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : deliveredContainers) {
				UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
						eventPort, MailConstantsVO.RESDIT_DELIVERED);
				uldResditVOs.add(uldResditVO);
			}
			if (uldResditVOs != null && uldResditVOs.size() > 0) {
				flagDeliveredResditForULDs(uldResditVOs,
						eventPort);
			}
		}
	}
	private void flagDeliveredResditForULDs(
			Collection<UldResditVO> uldResditVOs, String airportCode)
			throws SystemException {
		log.debug(CLASS, "flagDeliveredResditForULDs");
		String eventCode = MailConstantsVO.RESDIT_DELIVERED;
		Collection<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.SYSPAR_USPS_ENHMNT);

		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		HashMap<String, String> uldPAmap = null;
		HashMap<String, String> uldPACarditmap = null;
		String paCode = null;

		/**
		 * If system parametere MailConstantsVO.SYSPAR_USPS_ENHMNT value is Y only thee enhancement should be
		 * considered otherwise no change is required.
		 */
		if (systemParameterMap != null
				&& (MailConstantsVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.SYSPAR_USPS_ENHMNT))
		)
		) {
			// Fetching business time configuration for Arrival Airport
//				resditConfigurationVO = MailResdit.findDeliveryResditTimeConfigForPort (airportCode);
			// Finding PA for Mailbags . If cardit exists then sender of cardit else PA of OOE
			uldPACarditmap = UldResdit.findPAForShipperbuiltULDs (uldResditVOs, true);
			uldPAmap = UldResdit.findPAForShipperbuiltULDs (uldResditVOs, false);
			if (uldResditVOs != null && uldResditVOs.size() > 0) {
				for (UldResditVO uldResditVO : uldResditVOs) {
					paCode = uldPACarditmap.get(uldResditVO.getUldNumber());
					paCode = paCode == null ? uldPAmap.get(uldResditVO.getUldNumber()): paCode;
					if (MailConstantsVO.USPOST.equals(paCode)){
						eventCode = MailConstantsVO.RESDIT_READYFOR_DELIVERY ;
					}else{
						eventCode = MailConstantsVO.RESDIT_DELIVERED ;
					}
					uldResditVO.setEventCode(eventCode);
					uldResditVO.setShipperBuiltCode(paCode);
					if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
						log.debug("Flagging the RESDIT_DELIVERED");
						new UldResdit(uldResditVO);
					}
				}
			}
		}else{
			if (uldResditVOs != null && uldResditVOs.size() > 0) {
				for (UldResditVO uldResditVO : uldResditVOs) {
					if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
						log.debug("Flagging the RESDIT_DELIVERED");
						new UldResdit(uldResditVO);
					}
				}
			}
		}
		log.debug(CLASS, "flagDeliveredResditForULDs");
	}
	private void flagReturnedResditForMailbags(
			Collection<MailbagVO> mailbagVOs)
			throws SystemException {
		log.debug(CLASS, "flagReturnedResditForMailbags");
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			MailResditVO mailResditVO = constructMailResditVO(mailbagVO,
					mailbagVO.getScannedPort(),
					MailConstantsVO.RESDIT_RETURNED, false);
			//for return damage details is mandatory
			Collection<DamagedMailbagVO> mailbagDamages =
					mailbagVO.getDamagedMailbags();
			String returnPACode = null;
			for(DamagedMailbagVO damagedMailbagVO : mailbagDamages) {
				returnPACode = damagedMailbagVO.getPaCode();
			}
			mailResditVO.setPaOrCarrierCode(returnPACode);
			mailResditVOs.add(mailResditVO);
		}
		mailResditVOs = canFlagResditForEvents(MailConstantsVO.RESDIT_RETURNED, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
		log.debug(CLASS, "flagReturnedResditForMailbags");
	}
	private void flagConfiguredHandoverResdit(String companyCode,
											  int ownAirlineId, String txnId, String eventPort,
											  Collection<MailbagVO> mailbagVOs, Map<String, Object> valuesMap)
			throws SystemException {
		log.debug(CLASS, "flagConfiguredHandoverResdit");
		ResditTransactionDetailVO resditTxnVO =
				findResditConfigurationForTxn(companyCode, ownAirlineId, txnId);
		if(resditTxnVO != null) {
			if(MailConstantsVO.FLAG_YES.equals(
					resditTxnVO.getHandedOverResditFlag())) {
				flagHandedOverResditForMailbags(mailbagVOs, eventPort);
				if(valuesMap.containsKey(ACP_ULDS)){
					Collection<ContainerDetailsVO> containerDetails =
							(Collection<ContainerDetailsVO>)valuesMap.get(ACP_ULDS);
					if(containerDetails != null && containerDetails.size() > 0) {
						flagHandedOverResditForUlds(containerDetails, eventPort);
					}
				}
			}
		}
		log.debug(CLASS, "flagConfiguredHandoverResdit");
	}
	public void flagHandedOverResditForMailbags(
			Collection<MailbagVO> mailbags, String pol) throws SystemException {
		log.debug(CLASS, "flagHandedOverResdit");
		log.debug( "The Pol is " + pol);
		Collection<MailbagVO> uspsMailbags = new ArrayList<MailbagVO>();
		if (mailbags != null && mailbags.size() > 0) {
			Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
			for (MailbagVO mailbagVO : mailbags) {
				MailResditVO resditVO = constructMailResditVO(mailbagVO, pol,
						MailConstantsVO.RESDIT_HANDOVER_OFFLINE, false);
				resditVO.setPaOrCarrierCode(mailbagVO.getCarrierCode());
				mailResditVOs.add(resditVO);

			}
			mailResditVOs = canFlagResditForEvents(MailConstantsVO.RESDIT_HANDOVER_OFFLINE, mailResditVOs, mailbags);
			stampResdits(mailResditVOs);
		}
		for(MailbagVO mailbagVO: mailbags) {
			if(new MailController().isUSPSMailbag(mailbagVO)){
				uspsMailbags.add(mailbagVO);
			}
		}
		if(uspsMailbags.size()>0) {
			Collection<RateAuditDetailsVO> rateAuditVOs =mailController.createRateAuditVOsFromMailbag(uspsMailbags);
				mailOperationsMRAProxy.recalculateDisincentiveData(rateAuditVOs);
		}
		log.debug(CLASS, "flagHandedOverResdit");
	}
	private void flagHandedOverResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)
			throws SystemException {
		log.debug("ResditControler", "flagUpliftedResditForUlds");
		if (acceptedUlds != null && acceptedUlds.size() > 0) {
			for (ContainerDetailsVO containerDetailVo : acceptedUlds) {
				UldResditVO resditVO = constructUldResditVO(containerDetailVo, pol,
						MailConstantsVO.RESDIT_HANDOVER_OFFLINE);
				resditVO.setPaOrCarrierCode(containerDetailVo.getCarrierCode());
				// For MTK558
				if(canFlagResditForULDEvent(MailConstantsVO.RESDIT_HANDOVER_OFFLINE, resditVO)){
					new UldResdit(resditVO);
				}

			}
		}
		log.debug("ResditControler", "flagUpliftedResditForUlds");
	}
	public void flagReceivedResditForMailbags(
			Collection<MailbagVO> mailbagVOs,String eventPort)
			throws SystemException {
		log.debug("MailResdit", "flagReceivedResditForMailbags");
		String eventCode = MailConstantsVO.RESDIT_RECEIVED;
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			MailResditVO mailResditVO=null;
			if(! mailbagVO.isBlockReceivedResdit()){
				mailResditVO = constructMailResditVO(mailbagVO,
						eventPort, eventCode, false);
				mailResditVO.setPaOrCarrierCode(
						mailController.findPAForOfficeOfExchange(
								mailbagVO.getCompanyCode(), mailbagVO.getOoe()));
				mailResditVO.setDependantEventCode(MailConstantsVO.RESDIT_HANDOVER_RECEIVED);

				if(!MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagVO.getLatestStatus()))
				{
					mailResditVOs.add(mailResditVO);
				}

			}else{
				eventCode =MailConstantsVO.RESDIT_HANDOVER_RECEIVED;
				mailResditVO = constructMailResditVO(mailbagVO,
						eventPort, eventCode, false);
				mailResditVO.setPaOrCarrierCode(
						mailController.findPAForOfficeOfExchange(
								mailbagVO.getCompanyCode(), mailbagVO.getOoe()));
				if(!MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagVO.getLatestStatus())){
					mailResditVOs.add(mailResditVO);
				}
			}
			if("Y".equals(mailbagVO.getIsFromTruck())){
				if(mailbagVO.getFlightNumber()!=null && mailbagVO.getFlightNumber().trim().length()>0 ){
					mailbagVO.setStdOrStaTruckFlag("STD");
					mailResditVO.setEventDate(findResditEvtDate(mailbagVO,mailbagVO.getScannedPort()));
				}
			}
		}
		mailResditVOs = canFlagResditForEvents(eventCode, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
		log.debug("MailResdit", "flagReceivedResditForMailbags");
	}
	public void flagHandedoverReceivedForMailbags(
			Collection<MailbagVO> mailbagVOs, String eventPort)
			throws SystemException {
		log.debug(CLASS, "flagHandedoverReceivedForMailbags");
		Collection<MailbagVO> uspsMailbags = new ArrayList<MailbagVO>();
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			MailResditVO mailResditVO = constructMailResditVO(mailbagVO, eventPort,
					MailConstantsVO.RESDIT_HANDOVER_RECEIVED, false);
			mailResditVO.setPaOrCarrierCode(mailbagVO.getTransferFromCarrier());
			log.debug("THE PA OR CARRIER CODE"+mailResditVO.getPaOrCarrierCode());

			mailResditVO.setDependantEventCode(MailConstantsVO.RESDIT_RECEIVED);
			mailResditVOs.add(mailResditVO);
		}
		mailResditVOs = canFlagResditForEvents(MailConstantsVO.RESDIT_HANDOVER_RECEIVED, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
		log.debug(CLASS, "flagHandedoverReceivedForMailbags");
	}
	private void flagHandedoverReceivedForULDs(
			Collection<ContainerDetailsVO> carrContainerDetailsVOs, String eventPort)
			throws SystemException {
		log.debug(CLASS, "flagHandedoverReceivedForULDs");
		for (ContainerDetailsVO containerDetailsVO : carrContainerDetailsVOs) {
			UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO, eventPort,
					MailConstantsVO.RESDIT_HANDOVER_RECEIVED);
			if("I".equals(containerDetailsVO.getOperationFlag())){
				if(canFlagResditForULDEvent(MailConstantsVO.RESDIT_HANDOVER_RECEIVED, uldResditVO)){
					new UldResdit(uldResditVO);
				}
			}
		}
		log.debug(CLASS, "flagHandedoverReceivedForULDs");
	}
	private void flagAssignedResditForMailbags(
			Collection<MailbagVO> mailbagVOs, String eventAirport)
			throws SystemException {
		log.debug(CLASS, "flagAssignedResdit");
		String eventCode = MailConstantsVO.RESDIT_ASSIGNED;
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
			for (MailbagVO mailbagVO : mailbagVOs) {
				mailbagVO.setScannedPort(eventAirport);
				MailResditVO mailResditVO = constructMailResditVO(mailbagVO,
						eventAirport, eventCode, false);
				log.debug( " mailResditVO " + mailResditVO);
				/*If Assigned immediately after Offloading, no need to send PENDING RESDIT
				 * Remove those Unsent Pending Resdits.
				 */
				//Commenting this out since this not critical and for usps
				//anyway the assigned resdit is not needed
			/*removeFlaggedUnsentResdit(mailbagVO, eventAirport,
					MailConstantsVO.RESDIT_PENDING);*/
				mailResditVOs.add(mailResditVO);
			}
			mailResditVOs = canFlagResditForEvents(eventCode, mailResditVOs, mailbagVOs);
			stampResdits(mailResditVOs);
		}
		log.debug(CLASS, "flagAssignedResdit");
	}
	private void flagAssignedResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)
			throws SystemException {
		String eventCode = MailConstantsVO.RESDIT_ASSIGNED;
		if (acceptedUlds != null && acceptedUlds.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : acceptedUlds) {
				UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
						pol, eventCode);
				log.debug( " uldResditVO " + uldResditVO);
				if(canFlagResditForULDEvent(eventCode, uldResditVO)){
					new UldResdit(uldResditVO);
				}
			}
		}
	}

	private void flagLoadedResditForMailbags(Collection<MailbagVO> mailbagVOs,
											 String eventPort) throws SystemException {
		log.debug(CLASS, "flagDepartedResditForMailbags");
		MailResditVO mailResditVO= null;
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailResditVO=constructMailResditVO(mailbagVO, eventPort,
					MailConstantsVO.RESDIT_LOADED, false);
			mailResditVOs.add(mailResditVO);
		}
		mailResditVOs = canFlagResditForEvents(MailConstantsVO.RESDIT_LOADED, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
		log.debug(CLASS, "flagDepartedResditForMailbags");
	}
	private void flagLoadedResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)
			throws SystemException {
		log.debug("ResditControler", "flagLoadedResditForUlds");
		String eventCode = MailConstantsVO.RESDIT_LOADED;
		if (acceptedUlds != null && acceptedUlds.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : acceptedUlds) {
				UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
						pol, eventCode);
				if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
					log.debug(
							"Flagging the LOADED Resdits---uldResditVO--",
							uldResditVO);
					log.debug("Flagging the LOADED Resdits");
					new UldResdit(uldResditVO);
				}

			}
		}
		log.debug("ResditControler", "flagLoadedResditForUlds");
	}
	public void flagOnlineHandedoverResditForMailbags(
			Collection<MailbagVO> mailbagVOs, String eventPort)
			throws SystemException {
		MailResditVO mailResditVO=null;
		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			MailbagVO mailbagVo=null;
			try {
				mailbagVo = mailController.constructOriginDestinationDetails(mailbagVO);
			} catch (SystemException e) {
				e.getMessage();
			}
			if(mailbagVo!=null){
				mailbagVO.setOrigin(mailbagVo.getOrigin());
				mailbagVO.setDestination(mailbagVo.getDestination());
			}
			mailResditVO=constructMailResditVO(mailbagVO, eventPort,
					MailConstantsVO.RESDIT_HANDOVER_ONLINE, false);
			mailResditVO.setPaOrCarrierCode(mailbagVO.getCarrierCode());
			mailResditVOs.add(mailResditVO);
		}
		mailResditVOs = canFlagResditForEvents(MailConstantsVO.RESDIT_HANDOVER_ONLINE, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
	}
	private void flagOnlineHandedoverResditForUlds(
			Collection<ContainerDetailsVO> carrContainerDetailsVOs, String eventPort)
			throws SystemException {
		MailResditVO mailResditVO=null;
		for (ContainerDetailsVO containerDetailsVO : carrContainerDetailsVOs) {
			UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO, eventPort,
					MailConstantsVO.RESDIT_HANDOVER_ONLINE);

			if(canFlagResditForULDEvent(MailConstantsVO.RESDIT_HANDOVER_ONLINE, uldResditVO)){
				new UldResdit(uldResditVO);
			}

		}
	}
	public void flagUpliftedResditForMailbags(Collection<MailbagVO> mailbags,
											  String pol) throws SystemException {
		log.debug(CLASS, "flagUpliftedResdit");
		Collection<MailbagVO> uspsMailbags = new ArrayList<MailbagVO>();
		if (mailbags != null && mailbags.size() > 0) {
			Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
			for (MailbagVO mailbagVO : mailbags) {
				MailResditVO mailResditVO = constructMailResditVO(mailbagVO, pol,
						MailConstantsVO.RESDIT_UPLIFTED, false);
				mailResditVOs.add(mailResditVO);

			}
			mailResditVOs = canFlagResditForEvents(
					MailConstantsVO.RESDIT_UPLIFTED, mailResditVOs, mailbags);
			stampResdits(mailResditVOs);
			LoginProfile logonAttributes = contextUtil.callerLoginProfile();
			for (MailbagVO mailbagVO : mailbags) {
				if(mailbagVO.getLastUpdateUser()==null) {
					mailbagVO.setLastUpdateUser(logonAttributes.getUserId());
				}
				Collection<FlightValidationVO> flightVOs=null;
				FlightFilterVO	flightFilterVO=new FlightFilterVO();
				flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
				flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
				flightFilterVO.setPageNumber(1);
				flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
				flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
				flightFilterVO.setStation(pol);
				flightVOs=mailController.validateFlight(flightFilterVO);
				if(flightVOs!=null && !flightVOs.isEmpty() &&  mailbagVO!=null){
					//history not done
					mailController.flagHistoryforFlightDeparture(mailbagVO,flightVOs, mailController.getTriggerPoint());
					mailController.flagAuditforFlightDeparture(mailbagVO,flightVOs);
				}
			}
			for(MailbagVO mailbagVO: mailbags) {
				if(mailController.isUSPSMailbag(mailbagVO)){
					uspsMailbags.add(mailbagVO);
				}
			}
			if(uspsMailbags.size()>0) {
				Collection<RateAuditDetailsVO> rateAuditVOs =mailController.createRateAuditVOsFromMailbag(uspsMailbags);
					mailOperationsMRAProxy.recalculateDisincentiveData(rateAuditVOs);
			}
		}
		log.debug(CLASS, "flagUpliftedResdit");
	}
	private void flagUpliftedResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)
			throws SystemException {
		log.debug("ResditControler", "flagUpliftedResditForUlds");
		if (acceptedUlds != null && acceptedUlds.size() > 0) {
			for (ContainerDetailsVO containerDetailVo : acceptedUlds) {
				UldResditVO resditVO = constructUldResditVO(containerDetailVo, pol,
						MailConstantsVO.RESDIT_UPLIFTED);
				resditVO.setPaOrCarrierCode(containerDetailVo.getCarrierCode());
				if(canFlagResditForULDEvent(MailConstantsVO.RESDIT_UPLIFTED, resditVO)){
					new UldResdit(resditVO);
				}
			}
		}
		log.debug("ResditControler", "flagUpliftedResditForUlds");
	}
	private void flagReceivedResditForUlds(
			Collection<ContainerDetailsVO> acceptedUlds, String pol)
			throws SystemException {
		log.debug("ResditControler", "flagReceivedResditForUlds");
		String eventCode = MailConstantsVO.RESDIT_RECEIVED;
		if (acceptedUlds != null && acceptedUlds.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : acceptedUlds) {
				UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
						pol, eventCode);
				if("I".equals(containerDetailsVO.getOperationFlag())){
					if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
						log.debug("Flagging the Received Resdits---uldResditVO--"+uldResditVO);
						log.debug("Flagging the Received Resdits");
						new UldResdit(uldResditVO);
					}
				}
			}
		}
	}
	private void flagHandedoverReceivedForCarditMail(
			MailbagVO mailbagVO, CarditTransportationVO transportationVO)
			throws SystemException {
		log.debug(CLASS, "flagHandedoverReceivedForCarditMail");
		MailResditVO mailResditVO =
				constructTRTResditVO(mailbagVO, transportationVO,
						MailConstantsVO.RESDIT_HANDOVER_RECEIVED);
		Collection<String> paramNames = new ArrayList<String>();
		paramNames.add(MailConstantsVO.RESDIT_RCVD_CONFTIM);
		Map<String, String> paramValMap = null;
		try {
			paramValMap = neoMastersServiceUtils.findSystemParameterByCodes(paramNames);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		int minutes = Integer.parseInt(
				paramValMap.get(MailConstantsVO.RESDIT_RCVD_CONFTIM));
		mailResditVO.setEventDate(
				transportationVO.getDepartureTime().minusMinutes(minutes));
		mailResditVO.setEventCode(
				MailConstantsVO.RESDIT_HANDOVER_RECEIVED);
		mailResditVO.setPaOrCarrierCode(mailbagVO.getTransferFromCarrier());
		if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED))
				&& !canStampResdits(mailResditVO)) {
			return;
		}
		new MailResdit(mailResditVO);
		log.debug(CLASS, "flagHandedoverReceivedForCarditMail");
	}

	public void flagResditsForFlightDeparture(String companyCode, int carrierId,
											  Collection<MailbagVO> mailbagVOs,Collection < ContainerDetailsVO> containerDetailsVOs,
											  String eventPort) throws SystemException {
		log.debug(CLASS, "flagResditsForFlightDeparture");
		if(checkForResditConfig()) {
			Map<String, Object> valuesMap = new HashMap<String, Object>();
			valuesMap.put(HAS_DEP, true);
			valuesMap.put(ACP_ULDS, containerDetailsVOs);
			flagConfiguredResdits(companyCode, carrierId, MailConstantsVO.TXN_DEP,
					eventPort, mailbagVOs, valuesMap);
		} else {
			flagUpliftedResditForMailbags(mailbagVOs, eventPort);
			flagUpliftedResditForUlds(containerDetailsVOs, eventPort);
		}
		log.debug(CLASS, "flagResditsForFlightDeparture");
	}
	public void flagResditForMailbags(Collection<MailbagVO> mailbagVOs,String eventPort,String eventCode
									  ) throws SystemException {
		log.debug(CLASS, "flagResditForMailbags");
		if (MailConstantsVO.RESDIT_RECEIVED.equals(eventCode)) {
			flagReceivedResditForMailbags(mailbagVOs, eventPort);
		} else if (MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)) {
			flagUpliftedResditForMailbags(mailbagVOs, eventPort);
		} else if (MailConstantsVO.RESDIT_ASSIGNED.equals(eventCode)) {
			flagAssignedResditForMailbags(mailbagVOs, eventPort);
		} else if (MailConstantsVO.RESDIT_PENDING.equals(eventCode)) {
			flagPendingResditForMailbags(mailbagVOs, eventPort);
		} else if (MailConstantsVO.RESDIT_RETURNED.equals(eventCode)) {
			/*
			 * This code will do the configuration check for Return Flagging,done in two scenarios
			 *
			 * 1. For Latest status = (ACP or ASG or TRA )  and Arrived flag is "N"
			 * 	  then "Returned Resdit" should be sent at "Accept Mail Milestone"
			 *
			 * 2. For Latest status = (OFL)
			 * 	  then "Returned Resdit" should be sent at "Accept Mail Milestone"
			 *
			 * 3. For Latest status = (ACP) and Arrived flag is "N"
			 * 	  then "Returned Resdit" should be sent at "Arrive Mail Milestone" .
			 */
			MailbagVO mailbagVO = new ArrayList<MailbagVO>(mailbagVOs).get(0);
			String transactionId = null;
			String arrivedFlag=null;
			if(mailbagVO.getArrivedFlag()==null || mailbagVO.getArrivedFlag().equals(MailConstantsVO.FLAG_NO)){
				arrivedFlag=MailConstantsVO.FLAG_NO;
			}else{
				arrivedFlag=MailConstantsVO.FLAG_YES;
			}
			if (checkForResditConfig()) {
				Map<String, Object> valuesMap = new HashMap<String, Object>();
				valuesMap.put(RETN_MAILS, mailbagVOs);
				//Return Mail Milestone
				transactionId = MailConstantsVO.TXN_RET;
				//Flagging the RESDIT for Diff. Milestones
				flagConfiguredResdits(mailbagVO.getCompanyCode(), mailbagVO
								.getCarrierId(), transactionId, eventPort, mailbagVOs,
						valuesMap);
			} else {
				/*
				 * If Configuration Check is not needed then
				 * "Returned Resdit" need to be send.
				 */
				flagReturnedResditForMailbags(mailbagVOs);
			}
		}

		log.debug(CLASS, "flagResditForMailbags");
	}
	private void flagPendingResditForMailbags(Collection<MailbagVO> mailbagVOs,
											  String eventPort) throws SystemException {
		log.debug(CLASS, "flagPendingResditForMailbags");

		Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();

		String eventCode=null;
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailbagVO = mailController.constructOriginDestinationDetails(mailbagVO);//added by A-8353 for ICRD-346641
			String mailboxIdFromConfig = null;
			MailboxIdVO mailBoxId =null;
			String companyCode=mailbagVO.getCompanyCode();
			String officeOfExchange=mailbagVO.getOoe();

				mailboxIdFromConfig= MailMessageConfiguration.findMailboxIdFromConfig(mailbagVO);//IASCB-27875
				if(mailboxIdFromConfig!=null) {
					mailBoxId = findMailbox(companyCode, mailboxIdFromConfig);
				}
				// remove already flagged unsent assigned
				eventCode = (mailBoxId!=null && !MailConstantsVO.M49_1_1.equals(mailBoxId.getResditversion()))?
						MailConstantsVO.RESDIT_PENDING:MailConstantsVO.RESDIT_PENDING_M49;


			MailResditVO mailResditVO = constructMailResditVO(mailbagVO,
					eventPort, eventCode, false);
			mailResditVO.setCarrierId(mailbagVO.getCarrierId());
			mailResditVOs.add(mailResditVO);

		}
		mailResditVOs = canFlagResditForEvents(eventCode, mailResditVOs, mailbagVOs);
		stampResdits(mailResditVOs);
		log.debug(CLASS, "flagPendingResditForMailbags");

	}
	public MailboxIdVO findMailbox(String companyCode,String mailboxId){
		MailboxIdVO mailboxIdVO = new MailboxIdVO();
		MailboxIdModel mailboxIdModel = new MailboxIdModel();
		mailboxIdModel.setCompanyCode(companyCode);
		mailboxIdModel.setMailboxID(mailboxId);
		mailboxIdVO = mailOperationsMapper.mailboxIdModelToMailboxIdVO(mailMasterApi.findMailboxId(mailboxIdModel));
		return mailboxIdVO;
	}
	public void flagResditsForMailbagTransfer(
			Collection<MailbagVO> transferredMails, ContainerVO containerVO)
			throws SystemException {
		log.debug(CLASS, "flagResditsForTransfer");
		String carrierCode = containerVO.getCarrierCode();
		String ownAirlineCode = containerVO.getOwnAirlineCode();
		String eventPort = containerVO.getAssignedPort();
		Collection<MailbagVO> newOnlineTransferredMails = null;
		Collection<MailbagVO> newTransferredMails = null;
		boolean isFlightAssigned = false;
		if(containerVO.getFlightSequenceNumber() > 0) {
			isFlightAssigned = true;
		}
		boolean isDeparted = false;
		if(containerVO.getFlightStatus()==null){
			isDeparted=  mailController.checkForDepartedFlight_Atd( containerVO);
		}else{
			isDeparted=	 MailConstantsVO.FLIGHT_STATUS_DEPARTED.equals(containerVO.getFlightStatus());
		}
		//Collection<MailbagVO> onlineTransferMails = null;
		/*
		 * If the toFlight is a carrier other than the owncarrier or if it is not
		 * a partner of the ownairline then this an OFFLINE Transfer.
		 * If not then online transferred mails are to be checked which means
		 * whether mailbags are being move between different flights of this
		 * airline. Some mailbags may be transferred to the same flight..for these
		 * no onlinetransferRESDIT need to be flagged. For those moved to different
		 * flight we've to flagonlineRESDIT
		 */

		Collection<MailbagVO> handoverRcvdMailbags = new ArrayList<MailbagVO>();
		Collection<MailbagVO> handoverOnMailbags = new ArrayList<MailbagVO>();
		Collection<MailbagVO> handoverOffMailbags = new ArrayList<MailbagVO>();

		identifyHandoverResditsForMailbags(transferredMails, handoverRcvdMailbags,
				handoverOnMailbags,  handoverOffMailbags,  ownAirlineCode, carrierCode, containerVO);


		/*
		 * Added By Karthick V as the part of the NCA Mail Tracking Bug Fix ...
		 */
		if(transferredMails!=null  && transferredMails.size()>0){
			newTransferredMails = new ArrayList<MailbagVO>();
			for(MailbagVO mailBag : transferredMails){
				newTransferredMails.add(constructMailBagVoForResdits(mailBag,containerVO));
			}
		}

		if(checkForResditConfig()) {
			Map<String, Object> valuesMap = new HashMap<String, Object>();
			valuesMap.put(IS_FLTASG, isFlightAssigned);
			if(isDeparted){
				valuesMap.put(HAS_DEP, isDeparted);
			}

			if(handoverOnMailbags != null && handoverOnMailbags.size() > 0) {
				valuesMap.put(OLIN_MAILS, handoverOnMailbags);
			}
			flagConfiguredResdits(containerVO.getCompanyCode(),
					containerVO.getOwnAirlineId(), MailConstantsVO.TXN_TSFR,
					eventPort, newTransferredMails, valuesMap);

			if(handoverOffMailbags != null && handoverOffMailbags.size() > 0) {
				flagConfiguredHandoverResdit(
						containerVO.getCompanyCode(),
						containerVO.getOwnAirlineId(),
						MailConstantsVO.TXN_TSFR, eventPort, handoverOffMailbags, valuesMap);
			}
		} else {


			// Changes for CR QF1410
			// Resdit 43
			if(handoverRcvdMailbags != null && handoverRcvdMailbags.size() > 0) {
				flagHandedoverReceivedForMailbags(handoverRcvdMailbags,eventPort);
			}
			//Resdit 41
			if(handoverOnMailbags != null && handoverOnMailbags.size() > 0 && !containerVO.isExportTransfer()) {
				flagOnlineHandedoverResditForMailbags(handoverOnMailbags, eventPort);
			}
			//Resdit 42
			if(handoverOffMailbags != null && handoverOffMailbags.size() > 0) {
				flagHandedOverResditForMailbags(handoverOffMailbags,eventPort);
			}

			if(isFlightAssigned &&!containerVO.isHandoverReceived()) {
				flagAssignedResditForMailbags(newTransferredMails,eventPort);
				//added for ICRD-82147
				flagLoadedResditForMailbags(newTransferredMails, eventPort);
			}

			if(isDeparted){
				flagUpliftedResditForMailbags(newTransferredMails, eventPort);
			}

		}
		log.debug(CLASS, "flagResditsForTransfer");
	}
	private void identifyHandoverResditsForMailbags(Collection<MailbagVO> transferMailbags, Collection<MailbagVO> handoverRcvdMailbags,
													Collection<MailbagVO> handoverOnMailbags, Collection<MailbagVO> handoverOffMailbags, String ownAirlineCode,
													String transferCarrierCode, ContainerVO containerVO) throws SystemException {
		boolean checkflgForPartnerCarrier=false;
		boolean isPartnerCarrier=false;

		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if(transferMailbags != null && transferMailbags.size() > 0){
			for(MailbagVO mailbagVO : transferMailbags) {

				if(mailbagVO.getCarrierCode() != null
						&& mailbagVO.getCarrierCode().trim().length() > 0
						&& ownAirlineCode != null && transferCarrierCode != null ){
					if (mailbagVO.getTransferFromCarrier() != null
							&& mailbagVO.getTransferFromCarrier().trim().length()>0
							&& !transferCarrierCode.equals(mailbagVO.getTransferFromCarrier())) {
						transferCarrierCode = mailbagVO.getTransferFromCarrier();
					}
					checkflgForPartnerCarrier=validateCarrierCodeFromPartner(mailbagVO.getCompanyCode(),ownAirlineCode,logonAttributes.getAirportCode(),transferCarrierCode);

					if (!ownAirlineCode.equals(mailbagVO.getCarrierCode())) {
						String carrierCode = mailbagVO.getCarrierCode();
						isPartnerCarrier = validateCarrierCodeFromPartner(mailbagVO.getCompanyCode(), ownAirlineCode,
								logonAttributes.getAirportCode(), carrierCode);
					}

					if(ownAirlineCode.equals(mailbagVO.getCarrierCode()) && transferCarrierCode.equals(mailbagVO.getCarrierCode())) {

						handoverOnMailbags.add(constructMailBagVoForResdits(mailbagVO,containerVO));
					}else if(ownAirlineCode.equals(transferCarrierCode)
							&& !transferCarrierCode.equals(mailbagVO.getCarrierCode()) ) {
						String fromCarrierCode = mailbagVO.getCarrierCode();
						mailbagVO = constructMailBagVoForResdits(mailbagVO,containerVO);
						mailbagVO.setTransferFromCarrier(fromCarrierCode);
						handoverRcvdMailbags.add(mailbagVO);


					}else if(!ownAirlineCode.equals(transferCarrierCode) &&  (ownAirlineCode.equals(mailbagVO.getCarrierCode()) || isPartnerCarrier)) {
						if(checkflgForPartnerCarrier)
						{
							handoverOnMailbags.add(constructMailBagVoForResdits(mailbagVO,containerVO));
						}
						else if(!ownAirlineCode.equals(transferCarrierCode) && ownAirlineCode.equals(mailbagVO.getCarrierCode())
								&& mailbagVO.getTransferFromCarrier() != null &&mailbagVO.getTransferFromCarrier().trim().length()>0){
							String fromCarrierCode = mailbagVO.getTransferFromCarrier();
							mailbagVO = constructMailBagVoForResdits(mailbagVO,containerVO);
							mailbagVO.setTransferFromCarrier(fromCarrierCode);
							handoverRcvdMailbags.add(mailbagVO);
						}
						else
						{
							handoverOffMailbags.add(constructMailBagVoForResdits(mailbagVO,containerVO));
						}
					}else if(!ownAirlineCode.equals(transferCarrierCode) && !ownAirlineCode.equals(mailbagVO.getTransferFromCarrier())) {
					}
				}
			}
		}
	}
	private MailbagVO constructMailBagVoForResdits(MailbagVO mailbagVO,
												   ContainerVO toContainerVo) throws SystemException {
		log.debug("Resdit Controoler", "constructMailBagVoForResdits");

		MailbagVO mailBagCopyVO = new MailbagVO();
		mailBagCopyVO=mailOperationsMapper.copyMailbagVO(mailbagVO);

		mailBagCopyVO.setScannedPort(toContainerVo.getAssignedPort());
		mailBagCopyVO.setCarrierCode(toContainerVo.getCarrierCode());
		mailBagCopyVO.setCarrierId(toContainerVo.getCarrierId());
		mailBagCopyVO.setFlightNumber(toContainerVo.getFlightNumber());
		mailBagCopyVO.setFlightSequenceNumber(toContainerVo.getFlightSequenceNumber());
		mailBagCopyVO.setSegmentSerialNumber(toContainerVo.getSegmentSerialNumber());
		mailBagCopyVO.setUldNumber(toContainerVo.getContainerNumber());
		if(toContainerVo.getOperationTime()!=null){
			mailBagCopyVO.setScannedDate(toContainerVo.getOperationTime());
		}
		if (mailBagCopyVO.getOrigin() == null || mailBagCopyVO.getDestination() == null) {
			MailbagVO mailBagVO = new MailbagVO();
			try {
				mailBagVO = mailController.constructOriginDestinationDetails(mailBagCopyVO);
			} catch (SystemException e) {
				e.getMessage();
			}
			if (mailBagCopyVO.getOrigin() == null) {
				mailBagCopyVO.setOrigin(mailBagVO.getOrigin());
			}
			if (mailBagCopyVO.getDestination() == null) {
				mailBagCopyVO.setDestination(mailBagVO.getDestination());
			}
		}

		log.debug(CLASS, "getMailResditVO");
		return mailBagCopyVO;
	}
	private boolean validateCarrierCodeFromPartner(String companyCode,
												   String ownCarrierCode, String airportCode, String carrierCode)
			throws SystemException {
		log.debug("MailAcceptance", "ValidateCarrierCodeFromPartner");
		boolean isValid = false;
		Collection<PartnerCarrierVO> partnerCarrierVos =
				mailOperationsMapper.partnerCarrierModelstoPartnerCarrierVos(
						mailMasterApi.findAllPartnerCarriers(companyCode, ownCarrierCode,
				airportCode));

		if (partnerCarrierVos != null && partnerCarrierVos.size() > 0) {
			for (PartnerCarrierVO partnerCarrierVO : partnerCarrierVos) {
				if (carrierCode
						.equals(partnerCarrierVO.getPartnerCarrierCode())) {
					isValid = true;
					break;
				}
			}
		}
		return isValid;
	}

	public void flagResditsForMailbagReassign(Collection<MailbagVO> mailbags,
											  ContainerVO toContainerVO) throws SystemException {
		log.debug(CLASS, "flagResditsForMailbagReassign");

		String eventPort = toContainerVO.getAssignedPort();
		boolean isDeparted = false;
		if (toContainerVO.getFlightStatus() == null) {
			isDeparted = mailController
					.checkForDepartedFlight_Atd(toContainerVO);
		} else {
			isDeparted = MailConstantsVO.FLIGHT_STATUS_DEPARTED
					.equals(toContainerVO.getFlightStatus());
		}
		boolean isFlightAssgn = false;
		if(toContainerVO.getFlightSequenceNumber() > 0) {
			isFlightAssgn = true;
		}
		boolean isHandover = false;
		if (toContainerVO.getOwnAirlineCode()!=null && !(toContainerVO.getCarrierCode().equals(
				toContainerVO.getOwnAirlineCode())) &&
				!validateCarrierCodeFromPartner(
						toContainerVO.getCompanyCode(),
						toContainerVO.getOwnAirlineCode(),
						eventPort, toContainerVO.getCarrierCode())) {
			isHandover = true;
		}
		if(checkForResditConfig()) {
			Map<String, Object> valuesMap = new HashMap<String, Object>();
			Collection<MailbagVO> paMailbags = new ArrayList<MailbagVO>();
			Collection<MailbagVO> carrMailbags = new ArrayList<MailbagVO>();

			if(isFlightAssgn) {
				valuesMap.put(IS_FLTASG, isFlightAssgn);
			}
			/*
			 *
			 * Added to flag the Uplifted Resdits For all the
			 * Mail Bags that has been  reassigned  to a flight which has been marked as a DEPARTED
			 *
			 */
			if(isDeparted){
				log.debug("THE DEPARED STATUS "+isDeparted);
				valuesMap.put(HAS_DEP, isDeparted);
			}


			if(!isHandover) {
			}

			// inventory save resdits NCA-CR
			/*
			 * Added By Karthick V to group the mailBags whether if that
			 * Mailbag is received from the PA or if that MailBag is received from the
			 * Carrier ...
			 * Group the MailBags on the same ...
			 *
			 */
			if( mailbags!=null && mailbags.size()>0){
				groupPACarrMailbags(mailbags, paMailbags, null, null, null, null, null);
				valuesMap.put(PA_MAILS, paMailbags);
				valuesMap.put(CARR_MAILS, carrMailbags);
			}

			flagConfiguredResdits(toContainerVO.getCompanyCode(),
					toContainerVO.getCarrierId(), MailConstantsVO.TXN_ASG,
					eventPort, mailbags, valuesMap);


		}else {
			if(isFlightAssgn) {
				// flagReceivedResditForMailbags(mailbags, eventPort);
				flagAssignedResditForMailbags(mailbags, eventPort);
				flagLoadedResditForMailbags(mailbags, eventPort);
			}
			if (isHandover) {
				flagHandedOverResditForMailbags(mailbags, eventPort);
			}

			if(isDeparted){
				flagUpliftedResditForMailbags(mailbags, eventPort);
			}

		}

		log.debug(CLASS, "flagResditsForMailbagReassign");
	}
	public void flagResditsForContainerTransfer(
			Collection<MailbagVO> transferredMails,
			Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO) throws SystemException {
		log.debug(CLASS, "flagResditsForContainerTransfer");
		String carrierCode = operationalFlightVO.getCarrierCode();
		String ownAirlineCode = operationalFlightVO.getOwnAirlineCode();
		String eventPort = operationalFlightVO.getPol();
		String companyCode = operationalFlightVO.getCompanyCode();
		boolean isDeparted = MailConstantsVO.FLIGHT_STATUS_DEPARTED.equals(operationalFlightVO.getFlightStatus())||operationalFlightVO.isAtdCaptured();
		boolean isOfflineHandover = false;
		Collection<MailbagVO> onlineTransferMails = null;
		if (!(carrierCode.equals(ownAirlineCode)) &&
				!validateCarrierCodeFromPartner(
						companyCode,
						ownAirlineCode, eventPort, carrierCode)) {
			isOfflineHandover = true;
		} else {
			onlineTransferMails = groupOnlineMailbagsInULD(
					transferredMails, containerVOs, operationalFlightVO);
		}

		Collection<ContainerDetailsVO> transferUlds = new ArrayList<ContainerDetailsVO>();
		if (containerVOs != null && containerVOs.size() > 0) {
			for (ContainerVO containerVO : containerVOs) {
				ContainerDetailsVO containerDetailsVO =
						constructContainerDetailsVO(containerVO);
				if (ContainerDetailsVO.FLAG_YES.equals(containerDetailsVO
						.getPaBuiltFlag())
						&& MailConstantsVO.ULD_TYPE.equals(containerDetailsVO
						.getContainerType())) {
					transferUlds.add(containerDetailsVO);
				}
			}

		}

		boolean isFlightAssigned = false;
		if(operationalFlightVO.getFlightSequenceNumber() > 0) {
			isFlightAssigned = true;
		}
		if(checkForResditConfig()) {
			Map<String, Object> valuesMap = new HashMap<String, Object>();
			valuesMap.put(ACP_ULDS, transferUlds);
			valuesMap.put(IS_FLTASG, isFlightAssigned);
			/*
			 * Added  to add the Uplifted Resdits to be falgged ..
			 *
			 */
			if(isDeparted){
				log.debug("THE DEPARTED STATUS"+isDeparted);
				valuesMap.put(HAS_DEP,isDeparted);
			}

			if(onlineTransferMails != null && onlineTransferMails.size() > 0) {
				valuesMap.put(OLIN_MAILS, onlineTransferMails);
			}

			flagConfiguredResdits(companyCode,
					operationalFlightVO.getCarrierId(), MailConstantsVO.TXN_TSFR,
					eventPort, transferredMails, valuesMap);

			if(isOfflineHandover) {
				flagConfiguredHandoverResdit(companyCode,
						operationalFlightVO.getOwnAirlineId(),
						MailConstantsVO.TXN_TSFR, eventPort, transferredMails, valuesMap);
			}

		} else {
			Collection<MailbagVO> updatedMailbagVOs=new ArrayList<MailbagVO>();

			try {
				for(MailbagVO mailbagVO : transferredMails){
					MailbagVO copyMailbagVO = mailOperationsMapper.copyMailbagVO(mailbagVO);
					MailbagVO updMailbagVO=new MailbagVO();
					updMailbagVO = createMailbagVOsforArrivedResdit(copyMailbagVO);
					updatedMailbagVOs.add(updMailbagVO);
				}
			}
			catch (SystemException e) {
				log.debug( "System Exception Caught");
				e.getMessage();

			}

			flagArrivedResditForMailbags(updatedMailbagVOs, eventPort);
			flagArrivedResditResditForULD(transferUlds, eventPort);

			if(!operationalFlightVO.isTransferOutOperation()){
				if(isFlightAssigned) {
					flagAssignedResditForMailbags(transferredMails, eventPort);
					flagAssignedResditForUlds(transferUlds, eventPort);
					flagLoadedResditForMailbags(transferredMails, eventPort);
				}

				if (isOfflineHandover) {
					flagHandedOverResditForMailbags(transferredMails, eventPort);
					flagHandedOverResditForUlds(transferUlds, eventPort);
				}else{
					if(onlineTransferMails != null && onlineTransferMails.size() > 0) {
						log.debug( " flagging online h/ov ");
						flagOnlineHandedoverResditForMailbags(onlineTransferMails, eventPort);
					}

					/**
					 * Added  for sending Handover_Received Resdits for ULDs
					 */
					if(transferUlds != null && transferUlds.size() > 0) {
						flagOnlineHandedoverResditForUlds(transferUlds, eventPort);
					}
				}


				if(isDeparted){
					flagUpliftedResditForMailbags(transferredMails, eventPort);
					flagUpliftedResditForUlds(transferUlds, eventPort);
				}
			}
		}

		log.debug(CLASS, "flagResditsForContainerTransfer");
	}

	private Collection<MailbagVO> groupOnlineMailbagsInULD(
			Collection<MailbagVO> transferredMails,
			Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS, "groupOnlineTransferMailbags");
		Collection<MailbagVO> mailbagsForOnline =
				new ArrayList<MailbagVO>();
		for(MailbagVO mailbagVO : transferredMails) {
			for(ContainerVO containerVO : containerVOs) {
				if(containerVO.getContainerNumber().equals(
						mailbagVO.getUldNumber())) {

					mailbagsForOnline.add(mailbagVO);
					break;
				}
			}
		}
		log.debug(CLASS, "groupOnlineTransferMailbags");
		return mailbagsForOnline;
	}
	private MailbagVO createMailbagVOsforArrivedResdit(MailbagVO mailbagVO) throws SystemException{
		Mailbag mailbag = null;
		try {
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() >0 ?
					mailbagVO.getMailSequenceNumber():findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));

			mailbag = Mailbag.find(mailbagPK);
		} catch (FinderException e) {
			e.getMessage();
		}
		catch (SystemException e1) {
			log.debug( "SystemException caught");
		}

		if (mailbag != null) {

			Collection<MailbagHistoryVO> existingMailbagHistories = Mailbag.findMailbagHistories(mailbag.getCompanyCode(),"", mailbag.getMailSequenceNumber());


			String airport = mailbag.getScannedPort();
			if (mailbagVO != null) {
				airport = mailbagVO.getScannedPort();
			}
			if (existingMailbagHistories != null
					&& existingMailbagHistories.size() > 0) {
				for (MailbagHistoryVO mailbagHistory : existingMailbagHistories) {
					if((MailConstantsVO.MAIL_STATUS_ASSIGNED
							.equals(mailbagHistory.getMailStatus()) || MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagHistory.getMailStatus()) || MailConstantsVO.MAIL_STATUS_DAMAGED
							.equals(mailbagHistory.getMailStatus()))){
						mailbagVO.setCarrierCode(mailbagHistory
								.getCarrierCode());
						mailbagVO.setFlightSequenceNumber(mailbagHistory.getFlightSequenceNumber());
						mailbagVO.setSegmentSerialNumber(mailbagHistory.getSegmentSerialNumber());
						mailbagVO.setFlightNumber(mailbagHistory
								.getFlightNumber());
						if(mailbagHistory.getFlightSequenceNumber() > 0){
							ZonedDateTime ldate = localDateUtil.getLocalDate(airport, true);
							ldate = localDateUtil.getLocalDateTime(mailbagHistory.getFlightDate(), null);

							mailbagVO.setFlightDate(ldate);
						}
						mailbagVO
								.setContainerNumber(mailbagHistory
										.getContainerNumber());
						mailbagVO
								.setPou(mailbagHistory.getPou());
					}
				}
			}

		}
		return mailbagVO;
	}
	private ContainerDetailsVO constructContainerDetailsVO(ContainerVO containerVO) {
		log.debug(CLASS, "constructContainerDetailsVO");

		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();

		containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
		containerDetailsVO.setCarrierId(containerVO.getCarrierId());
		containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
		containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
		containerDetailsVO.setPaBuiltFlag(containerVO.getPaBuiltFlag());
		containerDetailsVO.setContainerType(containerVO.getType());
		containerDetailsVO.setContainerJnyId(containerVO.getContainerJnyID());
		containerDetailsVO.setPaCode(containerVO.getShipperBuiltCode());

		log.debug(CLASS, "constructContainerDetailsVO");
		return containerDetailsVO;
	}
	public void flagResditForContainerReassign(Collection<MailbagVO> mailbagVOs,
											   Collection<ContainerDetailsVO> ulds, OperationalFlightVO toFlightVO,
											   boolean hasFlightDeparted) throws SystemException {

		log.debug(CLASS, "flagResditForContainerReassign");
		String eventPort = toFlightVO.getPol();

		boolean isFlightAsgn = false;
		if(toFlightVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT) {
			isFlightAsgn = true;
		}
		boolean isHandover = false;
		if(!toFlightVO.getCarrierCode().equals(toFlightVO.getOwnAirlineCode()) &&
				!validateCarrierCodeFromPartner(toFlightVO.getCompanyCode(),
						toFlightVO.getOwnAirlineCode(), eventPort,
						toFlightVO.getCarrierCode())) {
			isHandover = true;
		}
		if(checkForResditConfig()) {
			Map<String, Object> valuesMap = new HashMap<String, Object>();

			Collection<MailbagVO> paMailbags = new ArrayList<MailbagVO>();
			Collection<MailbagVO> carrMailbags = new ArrayList<MailbagVO>();

			valuesMap.put(ACP_ULDS, ulds);
			if(!isHandover) {
				valuesMap.put(HAS_DEP, hasFlightDeparted);
			}
			valuesMap.put(IS_FLTASG, isFlightAsgn);
			/*
			 * Added to group the mailBags whether if that
			 * Mailbag is received from the PA or if that MailBag is received from the
			 * Carrier ...
			 * Group the MailBags on the same ...
			 *
			 */
			if( mailbagVOs!=null && mailbagVOs.size()>0){
				groupPACarrMailbags(mailbagVOs, paMailbags, null, null, null, null, null);
				valuesMap.put(PA_MAILS, paMailbags);
				valuesMap.put(CARR_MAILS, carrMailbags);
			}

			flagConfiguredResdits(toFlightVO.getCompanyCode(),
					toFlightVO.getCarrierId(), MailConstantsVO.TXN_ASG,
					eventPort, mailbagVOs, valuesMap);

			if(isHandover) {
			}
		} else {
			if(isFlightAsgn) {

				flagLoadedResditForMailbags(mailbagVOs, eventPort);
				flagAssignedResditForUlds(ulds, eventPort);
				flagAssignedResditForMailbags(mailbagVOs, eventPort);
			}

			if(isHandover) {
				flagHandedOverResditForUlds(ulds, eventPort);
				flagHandedOverResditForMailbags(mailbagVOs, eventPort);
			} else {
				//send fligh
				if(hasFlightDeparted) {
					flagUpliftedResditForUlds(ulds, eventPort);
					if (toFlightVO.getActualTimeOfDeparture() != null) {
						for (MailbagVO mailbagVO : mailbagVOs) {
							mailbagVO.setResditEventDate(toFlightVO.getActualTimeOfDeparture());
						}
					}
					flagUpliftedResditForMailbags(mailbagVOs, eventPort);
				}
			}
		}
		log.debug(CLASS, "flagResditForContainerReassign");
	}
	public void flagResditsForArrival(MailArrivalVO mailArrivalVO,
									  Collection<MailbagVO> arrivedMailbags,
									  Collection<ContainerDetailsVO> arrivedContainers)
			throws SystemException {
		log.debug(CLASS, "flagResditsForArrival");
		log.debug(CLASS, "flagResditsForArrivedMailbags");
		Collection<MailbagVO> newMailbags = new ArrayList<MailbagVO>();
		Collection<MailbagVO> deliveredMailbags = new ArrayList<MailbagVO>();
		Collection<MailbagVO> readyForDeliveryMailbags = new ArrayList<MailbagVO>();
		Collection<ContainerDetailsVO> arrivedPABuiltContainers =new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> newContainers =
				new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> deliveredContainers =
				new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> readyForDeliveryContainers =
				new ArrayList<ContainerDetailsVO>();
		Map<String, String> cityCache = new HashMap<String, String>();
		LoginProfile logonAttributes = ContextUtil.getInstance().callerLoginProfile();
		for (MailbagVO mailbagVO : arrivedMailbags) {
			log.debug("flagResditsForArrivedMailbags"+mailbagVO);
			if (MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
				//Commented for bug 85199. 74 need not be send for newly added mail bags.
				//newMailbags.add(mailbagVO);
			}

			Mailbag mailbagToFindPA = null;//Added by A-8164 for ICRD-342541 starts
			String poaCode=null;
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() >0 ?
					mailbagVO.getMailSequenceNumber():findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode())  );
			try {
				mailbagToFindPA = Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				e.getMessage();
			}
			if(mailbagToFindPA!=null && mailbagToFindPA.getPaCode()!=null ){
				poaCode=mailbagToFindPA.getPaCode();
			}
			else{
				OfficeOfExchangeVO originOfficeOfExchangeVO;
				originOfficeOfExchangeVO=mailController.validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
				poaCode=originOfficeOfExchangeVO.getPoaCode();
			}

			if (isValidDeliveryAirport(mailbagVO.getDoe(), mailbagVO
					.getCompanyCode(), StringUtils.equals(mailArrivalVO.getMailSource(), MailConstantsVO.MRD)?mailArrivalVO.getAirportCode():
					logonAttributes.getAirportCode(), cityCache,poaCode,mailbagVO.getConsignmentDate())) {
				readyForDeliveryMailbags.add(mailbagVO);
			} else
			{
				//nothing to do
			}
			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
				deliveredMailbags.add(mailbagVO);
			}
		}
		for (ContainerDetailsVO arrivedContainer : arrivedContainers) {
			/**
			 * added  for SB ULD - sending resdit
			 */
			if (ContainerDetailsVO.FLAG_YES.equals(arrivedContainer.getPaBuiltFlag()) &&
					MailConstantsVO.ULD_TYPE.equals(arrivedContainer.getContainerType())) {
				arrivedPABuiltContainers.add(arrivedContainer);
				if (MailConstantsVO.OPERATION_FLAG_INSERT.equals(arrivedContainer.getOperationFlag())) {
					newContainers.add(arrivedContainer);
				}

				if( logonAttributes.getAirportCode().equalsIgnoreCase(arrivedContainer.getDestination())){
					readyForDeliveryContainers.add(arrivedContainer);
				}

				if(arrivedContainer.getPou() != null){
					if(MailConstantsVO.FLAG_YES.equals(arrivedContainer.getDeliveredStatus())) {
						deliveredContainers.add(arrivedContainer);
					}
				}
			}
		}

		if(checkForResditConfig()) {
			Map<String, Object> valuesMap = new HashMap<String, Object>();
			if(newMailbags!=null && newMailbags.size()>0){
				valuesMap.put(NEWARR_MAILS, newMailbags);
			}
			if(deliveredMailbags!=null && deliveredMailbags.size()>0){
				valuesMap.put(DLVD_MAILS,deliveredMailbags);
			}
			valuesMap.put(IS_FLTASG, true);
			//since arrival was done, departure must have happened
			valuesMap.put(HAS_DEP, true);
			if(newContainers!=null && newContainers.size()>0){
				valuesMap.put(PA_ULDS, newContainers);
			}
			if(deliveredContainers!=null && deliveredContainers.size()>0){
				valuesMap.put(DLVD_ULDS, deliveredContainers);
			}
			flagConfiguredResdits(mailArrivalVO.getCompanyCode(),
					mailArrivalVO.getCarrierId(), MailConstantsVO.TXN_ARR,
					mailArrivalVO.getAirportCode(), arrivedMailbags, valuesMap);
		}else {
			/*
			 * Added AS A PART OF AirNZ CR-504
			 * Earlier, " flagDeliveredResditForMailbags " was called
			 * without any priority to the Configuration.
			 */

			if( !"AA".equals(logonAttributes.getCompanyCode())){
				flagReadyForDeliveryResditForMailbags(readyForDeliveryMailbags,mailArrivalVO
						.getAirportCode());
				flagReadyForDeliveryResditForULD(readyForDeliveryContainers,mailArrivalVO
						.getAirportCode());
			}
			flagReceivedResditForMailbags(newMailbags, mailArrivalVO
					.getAirportCode());
			flagArrivedResditForMailbags(arrivedMailbags,mailArrivalVO
					.getAirportCode());

			flagDeliveredResditForMailbags(deliveredMailbags,
					mailArrivalVO.getAirportCode());
			flagReceivedResditForUlds(newContainers,mailArrivalVO
					.getAirportCode());
			flagArrivedResditResditForULD(arrivedPABuiltContainers,mailArrivalVO
					.getAirportCode());
			flagDlvdResditsForUldFromArrival(deliveredContainers,mailArrivalVO
					.getAirportCode());
		}

		log.debug(CLASS, "flagResditsForArrival");
	}
	public void flagArrivedResditForMailbags(
			Collection<MailbagVO> arrivedMailbags,
			String airportCode) throws SystemException {
		log.debug(CLASS, "flagArrivedResditForMailbags");
		HashMap<String, String> exgOfcPAMap = new HashMap<String, String> ();
		Collection<MailResditVO> r40MailResditVOs = new ArrayList<MailResditVO>();
		String eventCode = null;
		String paCode = null;
		MailResditVO mailResditVO = null;
		eventCode = MailConstantsVO.RESDIT_ARRIVED ;
		for (MailbagVO mailbagVO : arrivedMailbags) {
			paCode = exgOfcPAMap.get(mailbagVO.getOoe());
			if (paCode == null || paCode.length() <= 0){
				paCode = mailController.findPAForOfficeOfExchange(
						mailbagVO.getCompanyCode(), mailbagVO.getOoe());
				exgOfcPAMap.put(mailbagVO.getOoe(), paCode);
			}
			ZonedDateTime scanDate=mailbagVO.getScannedDate();
			updateArrivalEventTimeForAA(mailbagVO);
			MailbagVO mailbagVo=null;
			try {
				mailbagVo = mailController.constructOriginDestinationDetails(mailbagVO);
			} catch (SystemException e) {
				e.getMessage();
			}
			if(mailbagVo!=null){
				mailbagVO.setOrigin(mailbagVo.getOrigin());
				mailbagVO.setDestination(mailbagVo.getDestination());
			}
			mailResditVO =constructMailResditVO(mailbagVO, airportCode,eventCode, false);
			if(scanDate!=null){
				mailbagVO.setScannedDate(scanDate);
			}
			mailResditVO.setPaOrCarrierCode(paCode);
			r40MailResditVOs.add(mailResditVO);
		}
		if(r40MailResditVOs.size() >0){
			r40MailResditVOs = canFlagResditForEvents(eventCode, r40MailResditVOs, arrivedMailbags);
			stampResdits(r40MailResditVOs);
		}
		log.debug(CLASS, "flagArrivedResditForMailbags");
	}
	private void flagArrivedResditResditForULD(Collection<ContainerDetailsVO> arrivedContainers ,String eventPort)
			throws SystemException{
		Collection<UldResditVO> uldResditVOs = new ArrayList<UldResditVO>();
		if (arrivedContainers != null && arrivedContainers.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : arrivedContainers) {
				UldResditVO uldResditVO = constructUldResditVO(containerDetailsVO,
						eventPort, MailConstantsVO.RESDIT_ARRIVED);
				uldResditVOs.add(uldResditVO);
			}
			if (uldResditVOs.size() > 0) {
				flagArrivedResditForULDs(uldResditVOs,
						eventPort);
			}
		}
	}
	private void updateArrivalEventTimeForAA(MailbagVO mailbagVO) {

		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
		systemParameters.add(AUTOARRIVALOFFSET);
		String sysparfunpnts = null;
		String sysparoffset = null;
		Map<String, String> systemParameterMap;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
			if (systemParameterMap != null) {
				sysparfunpnts= systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
				sysparoffset=systemParameterMap.get(AUTOARRIVALOFFSET);
			}


		} catch (BusinessException e) {
			log.debug( "System Exception Caught");
			e.getMessage();
		}

		if(sysparfunpnts!=null)
		{
			if("0".equals(sysparoffset)  && MailConstantsVO.FLAG_YES.equals(mailbagVO.getAutoArriveMail()) )
			{
				FlightValidationVO validVO =null;
				try {
					validVO = mailController.validateFlightForBulk(mailbagVO);
				} catch (SystemException e) {
					validVO=null;
				}
				if(validVO!=null){
					mailbagVO.setResditEventDate(LocalDateMapper.toZonedDateTime(validVO.getAta()));
					mailbagVO.setScannedDate(LocalDateMapper.toZonedDateTime(validVO.getAta()));
				}


			}
		}



	}
	private void flagArrivedResditForULDs(
			Collection<UldResditVO> uldResditVOs, String airportCode)
			throws SystemException {
		log.debug(CLASS, "flagReadyForDeliveryResditFor ULDs");
		String eventCode = MailConstantsVO.RESDIT_ARRIVED;
		if (uldResditVOs != null && uldResditVOs.size() > 0) {
			for (UldResditVO uldResditVO : uldResditVOs) {
				if (canFlagResditForULDEvent(eventCode, uldResditVO)) {
					log.debug("Flagging the RESDIT_ARRIVED");
					new UldResdit(uldResditVO);
				}
			}
		}
		log.debug(CLASS, "flag ReadyForDeliveryResditForULDs");
	}
	public void flagResditsForTransportCompleted(String companyCode, int carrierId,
												 Collection<MailbagVO> mailbagVOs,Collection < ContainerDetailsVO> containerDetailsVOs,
												 String eventPort,String flightArrivedPort) throws SystemException {
		log.debug("ResditController===========>>>", "flagResditsForTransportCompleted ");
		Map<String, String> cityCache = new HashMap<String, String>();
		Collection<ContainerDetailsVO> readyForDeliveryContainers = new ArrayList<ContainerDetailsVO>();
		if(checkForResditConfig()) {
			Map<String, Object> valuesMap = new HashMap<String, Object>();

			valuesMap.put(HAS_DEP, true);
			valuesMap.put(ACP_ULDS, containerDetailsVOs);
			flagConfiguredResdits(companyCode, carrierId, MailConstantsVO.TXN_DEP,
					eventPort, mailbagVOs, valuesMap);
		} else{
			Collection<MailbagVO> readyForDeliveryMailbags = new ArrayList<MailbagVO>();
			if(mailbagVOs != null && mailbagVOs.size() > 0){
				for(MailbagVO mailbagVO : mailbagVOs){

					String destinationOfficeOfExchange = mailbagVO.getDoe();
					Mailbag mailbagToFindPA = null;
					String poaCode=null;
					MailbagPK mailbagPk = new MailbagPK();
					mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
					mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() >0 ?
							mailbagVO.getMailSequenceNumber(): findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()) );
					try {
						mailbagToFindPA = Mailbag.find(mailbagPk);
					} catch (FinderException e) {
						e.getMessage();
					}
					if(mailbagToFindPA!=null && mailbagToFindPA.getPaCode()!=null ){
						poaCode=mailbagToFindPA.getPaCode();
					}
					else{
						OfficeOfExchangeVO originOfficeOfExchangeVO;
						originOfficeOfExchangeVO=new MailController().validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
						poaCode=originOfficeOfExchangeVO.getPoaCode();
					}
					if (isValidDeliveryAirport(destinationOfficeOfExchange, mailbagVO.getCompanyCode(), flightArrivedPort, cityCache, poaCode,mailbagVO.getConsignmentDate())) {
						readyForDeliveryMailbags.add(mailbagVO);
					}
				}
			}
			if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
				for(ContainerDetailsVO containerDetailsVO : containerDetailsVOs){
					if( flightArrivedPort.equalsIgnoreCase(containerDetailsVO.getDestination())){
						readyForDeliveryContainers.add(containerDetailsVO);
					}
				}
			}
			if(readyForDeliveryMailbags.size()>0){
				flagTransportCompletedResditForMailbags(readyForDeliveryMailbags, eventPort);
			}
			if(readyForDeliveryContainers.size()>0){
				flagUpliftedResditForUlds(readyForDeliveryContainers, eventPort);
			}
		}
		log.debug("ResditController ===========>>>", "flagResditsForTransportCompleted");
	}
	public void flagTransportCompletedResditForMailbags(Collection<MailbagVO> mailbags,
														String pol) throws SystemException {
		log.debug(CLASS, "flagTransportCompletedResditForMailbags");
		if (mailbags != null && mailbags.size() > 0) {
			Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
			for (MailbagVO mailbagVO : mailbags) {
				MailResditVO mailResditVO = constructMailResditVO(mailbagVO, pol,
						MailConstantsVO.RESDIT_TRANSPORT_COMPLETED, false);
				mailResditVOs.add(mailResditVO);
			}
			mailResditVOs = canFlagResditForEvents(
					MailConstantsVO.RESDIT_TRANSPORT_COMPLETED, mailResditVOs, mailbags);
			stampResdits(mailResditVOs);
			LoginProfile logonAttributes = contextUtil.callerLoginProfile();

			for (MailbagVO mailbagVO : mailbags) {
				if(mailbagVO.getLastUpdateUser()==null) {
					mailbagVO.setLastUpdateUser(logonAttributes.getUserId());
				}
				Collection<FlightValidationVO> flightVOs=null;
				FlightFilterVO	flightFilterVO=new FlightFilterVO();
				flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
				flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
				flightFilterVO.setPageNumber(1);
				flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
				flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
				flightFilterVO.setStation(pol);
				flightVOs=mailController.validateFlight(flightFilterVO);
				if(flightVOs!=null && !flightVOs.isEmpty() &&mailbagVO!=null){
					mailController.flagHistoryforFlightArrival(mailbagVO,flightVOs, mailController.getTriggerPoint());
					mailController.flagAuditforFlightArrival(mailbagVO,flightVOs);
				}
			}

		}
		log.debug(CLASS, "flagTransportCompletedResditForMailbags");
	}

	public void flagResditsForFoundArrival(MailArrivalVO mailArrivalVO){
		Collection<MailbagVO> foundMailbags = new ArrayList<>();
		Collection<MailResditVO> mailResditVOs = new ArrayList<>();
		if (!mailArrivalVO.isFoundResditSent()) {
			foundMailbags = mailController.getFoundArrivalMailBags(mailArrivalVO);
		}
		if (Objects.nonNull(foundMailbags) && !foundMailbags.isEmpty()) {
			mailResditVOs = foundMailbags.stream().map(mailbagVO -> constructMailResditVOFromMailbag(mailbagVO, MailConstantsVO.RESDIT_FOUND))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		if(!mailResditVOs.isEmpty()){
			try {
				mailController.stampResdits(mailResditVOs);
			} catch (MailOperationsBusinessException e) {
				log.debug(e.getMessage());
			}
		}
	}
	private MailResditVO constructMailResditVOFromMailbag(MailbagVO mailbagVO,String eventCode){
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCompanyCode(mailbagVO.getCompanyCode());
		mailResditVO.setMailId(mailbagVO.getMailbagId());
		mailResditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		mailResditVO.setEventAirport(mailbagVO.getScannedPort());
		mailResditVO.setEventCode(eventCode);
		mailResditVO.setCarrierId(mailbagVO.getCarrierId());
		mailResditVO.setFlightNumber(mailbagVO.getFlightNumber());
		mailResditVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		mailResditVO.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
		mailResditVO.setPaOrCarrierCode(mailbagVO.getCarrierCode());
		mailResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
		mailResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
		mailResditVO.setUldNumber(mailbagVO.getContainerNumber());
		mailResditVO.setEventDate(getEventDate(mailbagVO));
		String mailboxId =null;
		mailboxId = MailMessageConfiguration.findMailboxIdFromConfig(mailbagVO);
		mailResditVO.setMailboxID(mailboxId);
		return mailResditVO;
	}
	private ZonedDateTime getEventDate(MailbagVO mailbagVO) {
		ZonedDateTime eventDate = localDateUtil.getLocalDate(null, true);
		if(Objects.nonNull(mailbagVO.getResditEventDate())){
			eventDate = mailbagVO.getResditEventDate();
		}else if(Objects.nonNull(mailbagVO.getScannedDate())){
			eventDate = mailbagVO.getScannedDate();
		}else if (mailbagVO.getScannedPort()!=null && !mailbagVO.getScannedPort().isEmpty()){
			eventDate = localDateUtil.getLocalDate(mailbagVO.getScannedPort(),true);
		}
		return eventDate;
	}
	public void flagLostResditsForMailbags(OperationalFlightVO operationalFlightVO){
		Collection<ContainerDetailsVO> containerDetailsVOs =null;
		containerDetailsVOs = mailController.findArrivalDetailsForReleasingMails(operationalFlightVO);
		Collection<MailbagVO> lostMailbags =null;
		if (Objects.nonNull(containerDetailsVOs) && !containerDetailsVOs.isEmpty()) {
			lostMailbags = containerDetailsVOs
					.stream()
					.flatMap(containerDetailsVO -> containerDetailsVO.getMailDetails().stream())
					.filter(mailbagVO -> MailConstantsVO.FLAG_NO.equals(mailbagVO.getArrivedFlag()))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		if(lostMailbags!=null) {

			Collection<MailResditVO> mailResditVOs = new ArrayList<MailResditVO>();
			for (MailbagVO lostMailbag : lostMailbags) {
				MailResditVO resditVO = new MailResditVO();
				resditVO = constructMailResditVOFromMailbag(lostMailbag, MailConstantsVO.RESDIT_LOST);
				mailResditVOs.add(resditVO);
			}
			try {
				mailController.stampResdits(mailResditVOs);
			} catch (MailOperationsBusinessException e) {
				log.debug(e.getMessage());
			}
		}

	}
}

