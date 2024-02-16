package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentValidationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.*;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.Criterion;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.CriterionBuilder;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyCondition;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyUtils;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** 
 * Behavioural entity for building Resdit messages
 * @author A-1739
 */
@Slf4j
public class Resdit {
	private static final String MAILTRACKING_DEFAULTS = "mail.operations";
	private static final String PREP_DATE_FORMAT = "yyMMdd";
	private static final String PREP_TIME_FORMAT = "HHmm";
	private static final String KEY_RESDIT_INTERCHANGE = "RESDIT_INTERCHANGE";
	private static final String KEY_MESSAGE_REFERENCE = "RESDIT_MESSAGEREF";
	/** 
	* Indication that the total consignment has been accepted
	*/
	private static final int CONSIGNMENT_TOTALLY_ACCEPTED_USPOST = 3;
	private static final int CONSIGNMENT_TOTALLY_ACCEPTED = 1;
	/** 
	* Indication that consignment has been accepted only partially
	*/
	private static final int CONSIGNMENT_PARTIALLY_ACCEPTED = 3;
	private static final int DOCUMENT_NOT_RECIEVED = 11;
	private static final String FLIGHTID_DELIM = "#";
	private static final String PARTY_TYPE_AIRLINE = "AR";
	private static final String DOMESTIC_PACODE = "mailtracking.domesticmra.usps";
	private static final String PAWBASSCONENAB = "PAWBASSCONENAB";
	private static final String PAWBPARMVALYES = "YES";
	private static final String INVINFO = "INVINFO";

	/** 
	* This methods builds the ResditMessageVO for manually triggering the RESDIT Sending Feb 12, 2007, A-1739
	* @param carditEnquiryVO
	* @return
	* @throws SystemException
	*/
	public Collection<ResditMessageVO> constructResditMessageVOs(CarditEnquiryVO carditEnquiryVO) {
		log.debug("Resdit" + " : " + "constructResditMessageVOs" + " Entering");
		log.debug(" mailbags for grouping " + carditEnquiryVO.getMailbagVos());
		Collection<ResditMessageVO> resditMessageVos = new ArrayList<ResditMessageVO>();
		Collection<ResditMessageVO> resditMessageVOsForMail = new ArrayList<ResditMessageVO>();
		Collection<ResditMessageVO> resditMessageVOsForContainer = new ArrayList<ResditMessageVO>();
		String resditEvent = carditEnquiryVO.getUnsendResditEvent();
		Map<String, Collection<MailbagVO>> resditMailbagMap = null;
		Map<String, Collection<ContainerVO>> resditContainerMap = null;
		boolean isCarditFlight = MailConstantsVO.FLIGHT_TYP_CARDIT.equals(carditEnquiryVO.getFlightType());
		if (isCarditFlight) {
			if (!MailConstantsVO.RESDIT_RECEIVED.equals(resditEvent)
					&& !MailConstantsVO.RESDIT_PENDING.equals(resditEvent)
					&& !MailConstantsVO.RESDIT_HANDOVER_RECEIVED.equals(resditEvent)) {
				if (carditEnquiryVO.getMailbagVos() != null && carditEnquiryVO.getMailbagVos().size() > 0) {
					resditMailbagMap = groupMailbagResdits(carditEnquiryVO);
				}
				if (carditEnquiryVO.getContainerVos() != null && carditEnquiryVO.getContainerVos().size() > 0) {
					resditContainerMap = groupResditsForULD(carditEnquiryVO);
				}
			}
		} else if (carditEnquiryVO.getMailbagVos() != null && carditEnquiryVO.getMailbagVos().size() > 0) {
			resditMailbagMap = groupMailbagsOfCardit(carditEnquiryVO);
		}
		if (carditEnquiryVO.getMailbagVos() != null && carditEnquiryVO.getMailbagVos().size() > 0) {
			if (resditMailbagMap != null) {
				for (Collection<MailbagVO> mailbags : resditMailbagMap.values()) {
					constructResditMessagesForMail(mailbags, resditMessageVOsForMail, carditEnquiryVO);
				}
			} else {
				constructResditMessagesForMail(carditEnquiryVO.getMailbagVos(), resditMessageVOsForMail,
						carditEnquiryVO);
			}
		}
		if (carditEnquiryVO.getContainerVos() != null && carditEnquiryVO.getContainerVos().size() > 0) {
			if (resditContainerMap != null && resditContainerMap.size() > 0) {
				for (Collection<ContainerVO> containerVos : resditContainerMap.values()) {
					constructResditMessagesForULD(containerVos, resditMessageVOsForContainer, carditEnquiryVO);
				}
			} else {
				constructResditMessagesForULD(carditEnquiryVO.getContainerVos(), resditMessageVOsForContainer,
						carditEnquiryVO);
			}
		}
		if (resditMessageVOsForContainer.size() > 0) {
			resditMessageVos.addAll(resditMessageVOsForContainer);
		}
		if (resditMessageVOsForMail != null && resditMessageVOsForMail.size() > 0) {
			resditMessageVos.addAll(resditMessageVOsForMail);
		}
		log.debug("Resdit" + " : " + "constructResditMessageVOs" + " Exiting");
		return resditMessageVos;
	}

	private void constructResditMessagesForULD(Collection<ContainerVO> containerVos,
			Collection<ResditMessageVO> resditMessageVos, CarditEnquiryVO carditEnquiryVo) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("Resdit" + " : " + "groupResditMessages" + " Entering");
		if (containerVos != null && containerVos.size() > 0) {
			ResditMessageVO resditMessageVO = new ResditMessageVO();
			resditMessageVO.setCompanyCode(carditEnquiryVo.getCompanyCode());
			resditMessageVO.setMessageType(ResditMessageVO.MSG_TYP);
			resditMessageVO.setMessageStandard(ResditMessageVO.EDIFACT);
			resditMessageVO.setLocNeeded(true);
			ZonedDateTime preparationDate = localDateUtil.getLocalDate(null, true);
			resditMessageVO.setPreparationDate(preparationDate.format(DateTimeFormatter.ofPattern(PREP_DATE_FORMAT)));
			resditMessageVO.setPreparationTime(preparationDate.format(DateTimeFormatter.ofPattern(PREP_TIME_FORMAT)));
			String resditEvent = carditEnquiryVo.getUnsendResditEvent();
			boolean hasTransportInfo = false;
			if (!MailConstantsVO.RESDIT_RECEIVED.equals(resditEvent)
					&& !MailConstantsVO.RESDIT_RETURNED.equals(resditEvent)
					&& !MailConstantsVO.RESDIT_PENDING.equals(resditEvent)) {
				hasTransportInfo = true;
			}
			boolean hasHandedoverInfo = false;
			if (!resditEvent.equals(MailConstantsVO.RESDIT_UPLIFTED)
					&& !resditEvent.equals(MailConstantsVO.RESDIT_PENDING)
					&& !resditEvent.equals(MailConstantsVO.RESDIT_ASSIGNED)) {
				hasHandedoverInfo = true;
			}
			boolean hasReasonCode = false;
			if (resditEvent.equals(MailConstantsVO.RESDIT_PENDING)
					|| resditEvent.equals(MailConstantsVO.RESDIT_RETURNED)) {
				hasReasonCode = true;
			}
			Map<String, ConsignmentInformationVO> consignmentMap = new HashMap<String, ConsignmentInformationVO>();
			for (ContainerVO containerVo : containerVos) {
				containerVo.setCompanyCode(carditEnquiryVo.getCompanyCode());
				String consignmentKey = containerVo.getConsignmentDocumentNumber();
				ConsignmentInformationVO consignInfoVO = consignmentMap.get(consignmentKey);
				if (consignInfoVO == null) {
					consignInfoVO = constructConsignInfoVOForULD(containerVo, resditMessageVO, carditEnquiryVo);
					consignmentMap.put(consignmentKey, consignInfoVO);
				}
				updateConsignInfoVOForULD(resditMessageVO, consignInfoVO, containerVo, hasTransportInfo,
						hasHandedoverInfo, hasReasonCode);
			}
			resditMessageVO
					.setConsignmentInformationVOs(new ArrayList<ConsignmentInformationVO>(consignmentMap.values()));
			generateSequencesForResdit(resditMessageVO);
			resditMessageVos.add(resditMessageVO);
		}
		log.debug("Resdit" + " : " + "groupResditMessages" + " Exiting");
	}

	/** 
	* This method generates INTREF and MSGREF sequences for RESDIT Sep 11, 2006, a-1739
	* @param resditMessageVO
	* @throws SystemException
	*/
	private void generateSequencesForResdit(ResditMessageVO resditMessageVO) {
		log.debug("Resdit" + " : " + "generateSequencesForResdit" + " Entering");

		KeyUtils keyUtils=ContextUtil.getInstance().getBean(KeyUtils.class);
		KeyCondition keyCondition =  new KeyCondition();
		keyCondition.setKey(KEY_RESDIT_INTERCHANGE);
		keyCondition.setValue(resditMessageVO.getRecipientID());
		Criterion interchangeCriterion = new CriterionBuilder()
				.withSequence(KEY_RESDIT_INTERCHANGE)
				.withKeyCondition(keyCondition)
				.withPrefix("").build();
		resditMessageVO.setInterchangeControlReference(
				keyUtils.getKey(interchangeCriterion));
		KeyCondition mesrefKeyCondition =  new KeyCondition();
		mesrefKeyCondition.setKey(KEY_MESSAGE_REFERENCE);
		mesrefKeyCondition.setValue(resditMessageVO.getRecipientID());
		Criterion mesrefCriterion = new CriterionBuilder()
				.withSequence(KEY_MESSAGE_REFERENCE)
				.withKeyCondition(mesrefKeyCondition)
				.withPrefix("").build();
		resditMessageVO.setMessageReferenceNumber(
				keyUtils.getKey(mesrefCriterion));
		log.debug("Resdit" + " : " + "generateSequencesForResdit" + " Exiting");
	}

	/** 
	* @author a-1936This method is used to construct the Container Information Vo
	* @param containerVo
	* @return
	*/
	private ContainerInformationVO constructContainerInformationVo(ContainerVO containerVo) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("Resdit" + " : " + "constructContainerInformationVo" + " Entering");
		ContainerInformationVO containerInformationVo = new ContainerInformationVO();
		containerInformationVo.setCarrierCode(containerVo.getCarrierCode());
		containerInformationVo.setCodeListResponsibleAgency(containerVo.getCodeListResponsibleAgency());
		containerInformationVo.setContainerNumber(containerVo.getContainerNumber());
		containerInformationVo
				.setContainerWeight(String.valueOf(containerVo.getContainerWeight().getValue().doubleValue()));
		containerInformationVo.setEquipmentQualifier(containerVo.getEquipmentQualifier());
		containerInformationVo.setMeasurementDimension(containerVo.getMeasurementDimension());
		containerInformationVo.setSealNumber(containerVo.getContainerSealNumber());
		containerInformationVo.setTypeCodeListResponsibleAgency(containerVo.getTypeCodeListResponsibleAgency());
		//containerInformationVo.setEventDate(containerVo.getEventTime().);
		containerInformationVo.setEventDate(LocalDateMapper.toGMTDate(containerVo.getEventTime()));
		containerInformationVo.setContainerType(containerVo.getTypeCode());
		containerInformationVo.setEventSequenceNumber(containerVo.getEventSequenceNumber());
		containerInformationVo.setPartnerId(containerVo.getPaCode());
		log.debug("Resdit" + " : " + "constructContainerInformationVo" + " Exiting");
		return containerInformationVo;
	}

	/** 
	* Utilty for finding syspar Mar 23, 2007, A-1739
	* @param syspar
	* @return
	* @throws SystemException
	*/
	private String findSystemParameterValue(String syspar) {
		NeoMastersServiceUtils neoMastersServiceUtils =
				ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class);
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug(" systemParameterMap " + systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/** 
	* TODO Purpose Sep 28, 2006, a-1739
	* @param companyCode
	* @param carrierId
	* @param flightNumber
	* @param flightSequenceNumber
	* @param transportInformationVO
	* @param segmentSerialNumber
	* @throws SystemException
	*/
	private void populateSegmentDetails(String companyCode, int carrierId, String flightNumber,
			long flightSequenceNumber, TransportInformationVO transportInformationVO, int segmentSerialNumber) {
		FlightOperationsProxy flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("Resdit" + " : " + "populateSegmentDetails" + " Entering");
		Collection<FlightSegmentSummaryVO> segmentSummaryVOs = flightOperationsProxy.findFlightSegments(companyCode,
				carrierId, flightNumber, flightSequenceNumber);
		String segOrigin = "";
		String segDestination = "";
		FlightSegmentSummaryVO segmentSummaryVO = null;
		if (segmentSummaryVOs != null && segmentSummaryVOs.size() > 0) {
			for (FlightSegmentSummaryVO segmentSumaryVO : segmentSummaryVOs) {
				segOrigin = segmentSumaryVO.getSegmentOrigin();
				segDestination = segmentSumaryVO.getSegmentDestination();
				if (segmentSumaryVO.getSegmentSerialNumber() == segmentSerialNumber) {
					segmentSummaryVO = segmentSumaryVO;
				}
			}
		}
		if (segmentSummaryVO != null) {
			FlightSegmentFilterVO segmentFilter = new FlightSegmentFilterVO();
			segmentFilter.setCompanyCode(companyCode);
			segmentFilter.setFlightCarrierId(carrierId);
			segmentFilter.setFlightNumber(flightNumber);
			segmentFilter.setSequenceNumber(flightSequenceNumber);
			segmentFilter.setOrigin(segmentSummaryVO.getSegmentOrigin());
			segmentFilter.setDestination(segmentSummaryVO.getSegmentDestination());
			FlightSegmentValidationVO segmentValidationVO = flightOperationsProxy.validateFlightSegment(segmentFilter);
			log.debug("segmentdata from flt " + segmentValidationVO);
			transportInformationVO.setArrivalPlace(segmentSummaryVO.getSegmentDestination());
			transportInformationVO.setDeparturePlace(segmentSummaryVO.getSegmentOrigin());
			boolean isAtd = MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.RESDIT_ATD_REQD));
			if (isAtd) {
				if (segmentValidationVO.getActualTimeOfDeparture() != null) {
					transportInformationVO.setDepartureTime(segmentValidationVO.getActualTimeOfDeparture());
				} else {
					transportInformationVO.setDepartureTime(segmentValidationVO.getScheduleTimeOfDeparture());
				}
			} else {
				transportInformationVO.setDepartureTime(segmentValidationVO.getScheduleTimeOfDeparture());
			}
			transportInformationVO.setArrivalTime(segmentValidationVO.getScheduleTimeOfArrival());
			ZonedDateTime departureDate = localDateUtil.getLocalDate(transportInformationVO.getDeparturePlace(), false);
			departureDate = LocalDate.withDate(departureDate,
					transportInformationVO.getDepartureTime().toDisplayDateOnlyFormat());
			transportInformationVO.setDepartureDate(LocalDateMapper.toLocalDate(departureDate));
		} else {
			AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
			assignedFlightSegmentPK.setCompanyCode(companyCode);
			assignedFlightSegmentPK.setCarrierId(carrierId);
			assignedFlightSegmentPK.setFlightNumber(flightNumber);
			assignedFlightSegmentPK.setFlightSequenceNumber(flightSequenceNumber);
			assignedFlightSegmentPK.setSegmentSerialNumber(segmentSerialNumber);
			AssignedFlightSegment asgFltSeg = null;
			try {
				asgFltSeg = AssignedFlightSegment.find(assignedFlightSegmentPK);
			} catch (FinderException finderException) {
				log.info("" + " error msg \n\n " + " " + finderException.getMessage());
			}
			if (asgFltSeg != null) {
				transportInformationVO.setArrivalPlace(asgFltSeg.getPou());
				transportInformationVO.setDeparturePlace(asgFltSeg.getPol());
			}
			if (segOrigin != null && segDestination != null && segOrigin.trim().length() > 0
					&& segDestination.trim().length() > 0) {
				FlightSegmentFilterVO segmentFilter = new FlightSegmentFilterVO();
				segmentFilter.setCompanyCode(companyCode);
				segmentFilter.setFlightCarrierId(carrierId);
				segmentFilter.setFlightNumber(flightNumber);
				segmentFilter.setSequenceNumber(flightSequenceNumber);
				segmentFilter.setOrigin(segOrigin);
				segmentFilter.setDestination(segDestination);
				FlightSegmentValidationVO segmentValidationVO = flightOperationsProxy
						.validateFlightSegment(segmentFilter);
				log.debug("segmentdata from flt " + segmentValidationVO);
				boolean isAtd = MailConstantsVO.FLAG_YES
						.equals(findSystemParameterValue(MailConstantsVO.RESDIT_ATD_REQD));
				if (isAtd) {
					if (segmentValidationVO.getActualTimeOfDeparture() != null) {
						transportInformationVO.setDepartureTime(segmentValidationVO.getActualTimeOfDeparture());
					} else {
						transportInformationVO.setDepartureTime(segmentValidationVO.getScheduleTimeOfDeparture());
					}
				} else {
					transportInformationVO.setDepartureTime(segmentValidationVO.getScheduleTimeOfDeparture());
				}
			}
		}
		log.debug("Resdit" + " : " + "populateSegmentDetails" + " Exiting");
	}

	/** 
	* The string in conveyance reference in split into different parts Sep 13, 2006, a-1739
	* @param companyCode
	* @param transportInformationVO
	* @throws SystemException
	*/
	private void updateTransportInformationDetails(String companyCode, TransportInformationVO transportInformationVO) {
		log.debug("Resdit" + " : " + "updateTransportInformationDetails" + " Entering");
		String conveyanceRef = transportInformationVO.getConveyanceReference();
		String[] flightDetails = conveyanceRef.split(FLIGHTID_DELIM);
		String carrierCode = flightDetails[0];
		int carrierId = Integer.parseInt(flightDetails[1]);
		String flightNumber = flightDetails[2];
		long flightSequenceNumber = Long.parseLong(flightDetails[3]);
		int segmentSerialNumber = Integer.parseInt(flightDetails[4]);
		transportInformationVO.setCarrierCode(carrierCode);
		transportInformationVO.setCarrierID(carrierId);
		transportInformationVO.setFlightNumber(flightNumber);
		transportInformationVO.setFlightSequenceNumber(flightSequenceNumber);
		transportInformationVO.setSegmentSerialNumber(segmentSerialNumber);
		transportInformationVO.setConveyanceReference(carrierCode.concat(flightNumber));
		populateSegmentDetails(companyCode, carrierId, flightNumber, flightSequenceNumber, transportInformationVO,
				segmentSerialNumber);
		transportInformationVO.setModeOfTransport(ResditMessageVO.AIR_TRANSPORT);
		transportInformationVO.setTransportStageQualifier(ResditMessageVO.MAIN_CARRIAGE_TRT);
		log.debug("Resdit" + " : " + "updateTransportInformationDetails" + " Exiting");
	}

	/** 
	* @param companyCode
	* @param consignInfoVO
	* @param containerVo
	* @throws SystemException
	*/
	private void populateContainerTransportInformation(String companyCode, ConsignmentInformationVO consignInfoVO,
			ContainerVO containerVo) {
		log.debug("Resdit" + " : " + "populateContainerTransportInformation" + " Entering");
		Collection<TransportInformationVO> transportInfoVOs = consignInfoVO.getTransportInformationVOs();
		if (transportInfoVOs == null) {
			transportInfoVOs = new ArrayList<TransportInformationVO>();
			consignInfoVO.setTransportInformationVOs(transportInfoVOs);
		}
		TransportInformationVO transportInfoVO = new TransportInformationVO();
		transportInfoVO.setDeparturePlace(containerVo.getResditEventPort());
		transportInfoVO.setDepartureLocationQualifier(ResditMessageVO.PLACE_OF_DEPARTURE);
		String conveyRefNo = new StringBuilder().append(containerVo.getCarrierCode()).append(FLIGHTID_DELIM)
				.append(containerVo.getCarrierId()).append(FLIGHTID_DELIM).append(containerVo.getFlightNumber())
				.append(FLIGHTID_DELIM).append(containerVo.getFlightSequenceNumber()).append(FLIGHTID_DELIM)
				.append(containerVo.getSegmentSerialNumber()).toString();
		log.debug("THE CONVEYANCE REFERENCE NUMBER" + conveyRefNo);
		transportInfoVO.setConveyanceReference(conveyRefNo);
		updateTransportInformationDetails(companyCode, transportInfoVO);
		if (MailConstantsVO.RESDIT_UPLIFTED.equals(consignInfoVO.getConsignmentEvent())) {
			consignInfoVO.setEventDate(transportInfoVO.getDepartureDate().toGMTDate());
		}
		transportInfoVOs.add(transportInfoVO);
		log.debug("Resdit" + " : " + "populateContainerTransportInformation" + " Exiting");
	}

	private static MailOperationsDAO constructDAO() {
		try {
			return MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(MAILTRACKING_DEFAULTS));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception.getMessage(), exception);
		}
	}

	/** 
	* TODO Purpose
	* @throws SystemException
	*/
	private String findPartyName(String companyCode, String partyCode) {
		String partyName = "";
		try {
			partyName = constructDAO().findPartyName(companyCode, partyCode);
		} catch (PersistenceException e) {
			log.info("" + " error msg \n\n " + " " + e.getMessage());
		}
		return partyName;
	}

	/** 
	* Populates the HandedOver Information for the RESDIT Sep 13, 2006, a-1739
	* @param consignInfoVO
	* @param resditMessageVO
	* @throws SystemException
	*/
	private void populateHandedOverInformation(ConsignmentInformationVO consignInfoVO,
			ResditMessageVO resditMessageVO) {
		log.debug("Resdit" + " : " + "populateHandedOverInformation" + " Entering");
		String eventCode = consignInfoVO.getConsignmentEvent();
		String partyName = "";
		Collection<ReceptacleInformationVO> receptacleInfoVOs = consignInfoVO.getReceptacleInformationVOs();
		if (receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
			ReceptacleInformationVO receptacleInfoVO = null;
			for (ReceptacleInformationVO receptacleInfo : receptacleInfoVOs) {
				receptacleInfoVO = receptacleInfo;
				break;
			}
			consignInfoVO.setPartnerID(receptacleInfoVO.getPartnerId());
			consignInfoVO.setOriginExgOffice(receptacleInfoVO.getOriginExgOffice());
			if (consignInfoVO.getPartnerID() != null) {
				partyName = findPartyName(resditMessageVO.getCompanyCode(), consignInfoVO.getPartnerID());
				if (partyName != null) {
					consignInfoVO.setPartyName(partyName);
				}
			}
		} else {
			Collection<ContainerInformationVO> containerInfoVOs = consignInfoVO.getContainerInformationVOs();
			if (containerInfoVOs != null && containerInfoVOs.size() > 0) {
				ContainerInformationVO containerInfoVO = null;
				for (ContainerInformationVO containerInfo : containerInfoVOs) {
					containerInfoVO = containerInfo;
					break;
				}
				consignInfoVO.setPartnerID(containerInfoVO.getPartnerId());
				if (consignInfoVO.getPartnerID() != null) {
					partyName = findPartyName(resditMessageVO.getCompanyCode(), consignInfoVO.getPartnerID());
					if (partyName != null) {
						consignInfoVO.setPartyName(partyName);
					}
				}
			}
		}
		populateResponsibleAgencyCodes(eventCode, consignInfoVO, resditMessageVO);
		populatePartyQualifier(eventCode, consignInfoVO, resditMessageVO);
		log.debug("Resdit" + " : " + "populateHandedOverInformation" + " Exiting");
	}

	/** 
	* @param eventCode
	* @param consignInfoVO
	* @param resditMessageVO
	* @return
	* @throws SystemException
	*/
	private String getPartyQualifier(String eventCode, ConsignmentInformationVO consignInfoVO,
			ResditMessageVO resditMessageVO) {
		log.debug("Resdit" + " : " + "getPartyQualifier" + " Entering");
		if (eventCode.equals(MailConstantsVO.RESDIT_DELIVERED)
				|| eventCode.equals(MailConstantsVO.RESDIT_READYFOR_DELIVERY)) {
			return ResditMessageVO.CONSIGNMENT_DESTINATION;
		} else if (eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_OFFLINE)
				|| eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_RECEIVED)
				|| eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_ONLINE)
				|| eventCode.equals(MailConstantsVO.RESDIT_UPLIFTED)) {
			return ResditMessageVO.CONNECTING_CARRIER;
		} else if (eventCode.equals(MailConstantsVO.RESDIT_RECEIVED)) {
			return ResditMessageVO.CONSIGNMENT_ORIGIN;
		} else if (eventCode.equals(MailConstantsVO.RESDIT_RETURNED)) {
			if (resditMessageVO.getMessageType() != null
					&& ResditMessageVO.MSG_TYPB.equalsIgnoreCase(resditMessageVO.getMessageType())) {
				return ResditMessageVO.CONSIGNMENT_ORIGIN;
			} else {
				log.debug("partnerId --" + consignInfoVO.getPartnerID());
				if (consignInfoVO.getPartnerID().equals(resditMessageVO.getRecipientID())) {
					return ResditMessageVO.CONSIGNMENT_ORIGIN;
				} else if (consignInfoVO.getPartnerID().equals(new MailController().findPAForOfficeOfExchange(
						resditMessageVO.getCompanyCode(), constructDOEForMail(consignInfoVO)))) {
					return ResditMessageVO.CONSIGNMENT_DESTINATION;
				}
			}
		}
		return ResditMessageVO.AGENT;
	}

	/** 
	* TODO Purpose Sep 19, 2006, a-1739
	* @param consignInfoVO
	* @return
	*/
	private String constructDOEForMail(ConsignmentInformationVO consignInfoVO) {
		Collection<ReceptacleInformationVO> receptacleInfos = consignInfoVO.getReceptacleInformationVOs();
		for (ReceptacleInformationVO receptacleVO : receptacleInfos) {
			return receptacleVO.getReceptacleID().substring(5, 10);
		}
		return null;
	}

	/** 
	* TODO Purpose Sep 14, 2006, a-1739
	* @param eventCode
	* @param consignInfoVO
	* @param resditMessageVO
	* @throws SystemException
	*/
	private void populatePartyQualifier(String eventCode, ConsignmentInformationVO consignInfoVO,
			ResditMessageVO resditMessageVO) {
		log.debug("Resdit" + " : " + "populatePartyQualifier" + " Entering");
		consignInfoVO.setPartyQualifier(getPartyQualifier(eventCode, consignInfoVO, resditMessageVO));
		consignInfoVO.setMutuallyDefined(false);
		log.debug("Resdit" + " : " + "populatePartyQualifier" + " Exiting");
	}

	/** 
	* TODO Purpose
	* @param eventCode
	* @param consignInfoVO
	* @param resditMessageVO
	* @throws SystemException
	*/
	private void populateResponsibleAgencyCodes(String eventCode, ConsignmentInformationVO consignInfoVO,
			ResditMessageVO resditMessageVO) {
		log.debug("Resdit" + " : " + "populateResponsibleAgencyCodes" + " Entering");
		if (eventCode.equals(MailConstantsVO.RESDIT_RECEIVED) || eventCode.equals(MailConstantsVO.RESDIT_RETURNED)
				|| eventCode.equals(MailConstantsVO.RESDIT_DELIVERED)
				|| eventCode.equals(MailConstantsVO.RESDIT_READYFOR_DELIVERY)
				|| eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_ONLINE)
				|| eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_OFFLINE)
				|| eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_RECEIVED)
				|| eventCode.equals(MailConstantsVO.RESDIT_UPLIFTED)) {
			consignInfoVO.setResponsibleAgencyCodes(ResditMessageVO.AGY_UPU);
			consignInfoVO.setResponsibleAgencyCodesForLoc(ResditMessageVO.AGY_UPU);
		} else if (eventCode.equals(MailConstantsVO.RESDIT_LOADED)) {
			consignInfoVO.setResponsibleAgencyCodes(ResditMessageVO.AGY_IATA);
			consignInfoVO.setResponsibleAgencyCodesForLoc(ResditMessageVO.AGY_IATA);
		}
		log.debug("Resdit" + " : " + "populateResponsibleAgencyCodes" + " Exiting");
	}

	/** 
	* @param resditMessageVO
	* @param consignInfoVO
	* @param containerVo
	* @param hasTransportInfo
	* @param hasHandedoverInfo
	* @param hasReasonCode
	* @throws SystemException
	*/
	private void updateConsignInfoVOForULD(ResditMessageVO resditMessageVO, ConsignmentInformationVO consignInfoVO,
			ContainerVO containerVo, boolean hasTransportInfo, boolean hasHandedoverInfo, boolean hasReasonCode) {
		log.debug("Resdit" + " : " + "updateConsignInfoVOForULD" + " Entering");
		Collection<ContainerInformationVO> containerCollection = consignInfoVO.getContainerInformationVOs();
		if (containerCollection == null) {
			containerCollection = new ArrayList<ContainerInformationVO>();
			consignInfoVO.setContainerInformationVOs(containerCollection);
		}
		containerCollection.add(constructContainerInformationVo(containerVo));
		log.debug("THE TRANSPORT INFO" + hasTransportInfo);
		log.debug("THE HANDED OVER  INFO" + hasHandedoverInfo);
		log.debug(" HAS REASON CODE " + hasReasonCode);
		if ((consignInfoVO.getTransportInformationVOs() == null
				|| consignInfoVO.getTransportInformationVOs().size() == 0) && hasTransportInfo) {
			populateContainerTransportInformation(containerVo.getCompanyCode(), consignInfoVO, containerVo);
		}
		if (hasHandedoverInfo) {
			populateHandedOverInformation(consignInfoVO, resditMessageVO);
		}
		if (consignInfoVO.getEventReason() == null && hasReasonCode) {
			populateReasonCodes(consignInfoVO, resditMessageVO.getCompanyCode(), resditMessageVO.getStationCode());
		}
		log.debug("THE RESDIT MESSAGE VO IS " + resditMessageVO);
		computeResditEventDate(consignInfoVO);
		log.debug("Resdit" + " : " + "updateConsignInfoVOForULD" + " Exiting");
	}

	/** 
	* TODO Purpose Sep 14, 2006, a-1739
	* @param receptacleInfoVOs
	* @return
	*/
	private ZonedDateTime getLatestReceptacleEventDate(Collection<ReceptacleInformationVO> receptacleInfoVOs) {
		log.debug("Resdit" + " : " + "getLatestReceptacleEventDate" + " Entering");
		ZonedDateTime latestEventDate = null;
		for (ReceptacleInformationVO receptacleInfoVO : receptacleInfoVOs) {
			ZonedDateTime eventDate = receptacleInfoVO.getEventDate().toZonedDateTime();
			if (latestEventDate == null) {
				latestEventDate = eventDate;
			} else if (latestEventDate.isBefore(eventDate)) {
				latestEventDate = eventDate;
			}
		}
		log.debug("Resdit" + " : " + "getLatestReceptacleEventDate" + " Exiting");
		return latestEventDate;
	}

	/** 
	* Finds the RESDIT event date from ULD & RCP event dates Sep 14, 2006, a-1739
	* @param consignInfoVO
	*/
	private void computeResditEventDate(ConsignmentInformationVO consignInfoVO) {
		log.debug("Resdit" + " : " + "computeResditEventDate" + " Entering");
		ZonedDateTime resditEventDate = null;
		Collection<ReceptacleInformationVO> receptacleInfoVOs = consignInfoVO.getReceptacleInformationVOs();
		ZonedDateTime receptacleEventDate = null;
		if (receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
			receptacleEventDate = getLatestReceptacleEventDate(receptacleInfoVOs);
		}
		Collection<ContainerInformationVO> containerInfoVOs = consignInfoVO.getContainerInformationVOs();
		ZonedDateTime containerEventDate = null;
		if (containerInfoVOs != null && containerInfoVOs.size() > 0) {
			containerEventDate = getLatestContainerEventDate(containerInfoVOs);
		}
		if (receptacleEventDate == null) {
			resditEventDate = containerEventDate;
		} else if (containerEventDate == null) {
			resditEventDate = receptacleEventDate;
		} else if (receptacleEventDate.isAfter(containerEventDate)) {
			resditEventDate = receptacleEventDate;
		} else {
			resditEventDate = containerEventDate;
		}
		consignInfoVO.setEventDate(LocalDateMapper.toGMTDate(resditEventDate));
		log.debug("Resdit" + " : " + "computeResditEventDate" + " Exiting");
	}

	/** 
	* Finds the latest event date of ULD Sep 15, 2006, a-1739
	* @param containerInfoVOs
	* @return
	*/
	private ZonedDateTime getLatestContainerEventDate(Collection<ContainerInformationVO> containerInfoVOs) {
		log.debug("Resdit" + " : " + "getLatestContainerEventDate" + " Entering");
		ZonedDateTime latestEventDate = null;
		for (ContainerInformationVO containerInfoVO : containerInfoVOs) {
			ZonedDateTime eventDate = containerInfoVO.getEventDate().toZonedDateTime();
			if (latestEventDate == null) {
				latestEventDate = eventDate;
			} else if (latestEventDate.isBefore(eventDate)) {
				latestEventDate = eventDate;
			}
		}
		log.debug("Resdit" + " : " + "getLatestContainerEventDate" + " Exiting");
		return latestEventDate;
	}

	/** 
	* Finds the offload reason code Sep 14, 2006, a-1739
	* @param consignInfoVO
	* @param companyCode
	* @param eventPort
	* @throws SystemException
	*/
	private void populateReasonCodes(ConsignmentInformationVO consignInfoVO, String companyCode, String eventPort) {
		log.debug("Resdit" + " : " + "populateOffloadReasonCode" + " Entering");
		Collection<ReceptacleInformationVO> receptacleInfoVOs = consignInfoVO.getReceptacleInformationVOs();
		String eventCode = consignInfoVO.getConsignmentEvent();
		if (receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
			ReceptacleInformationVO receptacleVO = null;
			for (ReceptacleInformationVO receptacleInfoVO : receptacleInfoVOs) {
				receptacleVO = receptacleInfoVO;
				break;
			}
			if (eventCode.equals(MailConstantsVO.RESDIT_PENDING)) {
				consignInfoVO.setEventReason(
						MailOffloadDetail.findOffloadReasonForMailbag(companyCode, receptacleVO.getReceptacleID()));
			} else {
				consignInfoVO.setEventReason(
						DamagedMailbag.findDamageReason(companyCode, receptacleVO.getReceptacleID(), eventPort));
			}
		} else {
			Collection<ContainerInformationVO> containerInfoVOs = consignInfoVO.getContainerInformationVOs();
			if (containerInfoVOs == null) {
				log.debug(
						"populate reason codes issue Check This consignment --->>>" + consignInfoVO.getConsignmentID());
			}
			ContainerInformationVO containerInformationVO = null;
			for (ContainerInformationVO containerInfoVO : containerInfoVOs) {
				containerInformationVO = containerInfoVO;
				break;
			}
			consignInfoVO.setEventReason(MailOffloadDetail.findOffloadReasonForULD(companyCode,
					containerInformationVO.getContainerNumber()));
		}
		log.debug("Resdit" + " : " + "populateOffloadReasonCode" + " Exiting");
	}

	/** 
	* @author a-1739
	* @param containerVo
	* @param resditMessageVO
	* @param carditEnquiryVO
	* @return
	*/
	private ConsignmentInformationVO constructConsignInfoVOForULD(ContainerVO containerVo,
			ResditMessageVO resditMessageVO, CarditEnquiryVO carditEnquiryVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		if (resditMessageVO.getStationCode() == null) {
			resditMessageVO.setStationCode(carditEnquiryVO.getResditEventPort());
			resditMessageVO.setSenderID(containerVo.getCarditRecipientId());
			resditMessageVO.setRecipientID(containerVo.getPaCode());
		}
		ConsignmentInformationVO consignInfoVO = new ConsignmentInformationVO();
		consignInfoVO.setConsignmentID(containerVo.getConsignmentDocumentNumber());
		consignInfoVO.setMessageSequenceNumber(containerVo.getResditEventSeqNum());
		consignInfoVO.setConsignmentEvent(carditEnquiryVO.getResditEventCode());
		consignInfoVO.setDateFormatQualifier(201);
		consignInfoVO.setDateQualifier(7);
		consignInfoVO.setTransferLocation(carditEnquiryVO.getResditEventPort());
		consignInfoVO.setEventDate(LocalDateMapper.toGMTDate(containerVo.getEventTime()));
		consignInfoVO.setConsignmentResponse(CONSIGNMENT_PARTIALLY_ACCEPTED);
		log.debug("THE PARTNER ID " + containerVo.getPaCode());
		consignInfoVO.setPartnerID(containerVo.getPaCode());
		return consignInfoVO;
	}

	/** 
	* @param mailbagVOs
	* @param resditMessageVOs
	* @param carditEnquiryVO
	* @throws SystemException
	*/
	private void constructResditMessagesForMail(Collection<MailbagVO> mailbagVOs,
			Collection<ResditMessageVO> resditMessageVOs, CarditEnquiryVO carditEnquiryVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("Resdit" + " : " + "groupResditMessages" + " Entering");
		ResditMessageVO resditMessageVO = new ResditMessageVO();
		resditMessageVO.setCompanyCode(carditEnquiryVO.getCompanyCode());
		resditMessageVO.setMessageType(ResditMessageVO.MSG_TYP);
		resditMessageVO.setMessageStandard(ResditMessageVO.EDIFACT);
		resditMessageVO.setLocNeeded(true);
		ZonedDateTime preparationDate = localDateUtil.getLocalDate(null, true);
		//TODO: neo to verify the format
		resditMessageVO.setPreparationDate(preparationDate.format(DateTimeFormatter.ofPattern(PREP_DATE_FORMAT)));
		resditMessageVO.setPreparationTime(preparationDate.format(DateTimeFormatter.ofPattern(PREP_TIME_FORMAT)));
		String resditEvent = carditEnquiryVO.getUnsendResditEvent();
		boolean hasTransportInfo = false;
		if (!MailConstantsVO.RESDIT_RECEIVED.equals(resditEvent) && !MailConstantsVO.RESDIT_RETURNED.equals(resditEvent)
				&& !MailConstantsVO.RESDIT_PENDING.equals(resditEvent)) {
			hasTransportInfo = true;
		}
		boolean hasHandedoverInfo = false;
		if (!resditEvent.equals(MailConstantsVO.RESDIT_UPLIFTED) && !resditEvent.equals(MailConstantsVO.RESDIT_PENDING)
				&& !resditEvent.equals(MailConstantsVO.RESDIT_ASSIGNED)) {
			hasHandedoverInfo = true;
		}
		boolean hasReasonCode = false;
		if (resditEvent.equals(MailConstantsVO.RESDIT_PENDING) || resditEvent.equals(MailConstantsVO.RESDIT_RETURNED)) {
			hasReasonCode = true;
		}
		Map<String, ConsignmentInformationVO> consignmentMap = new HashMap<String, ConsignmentInformationVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailbagVO.setCompanyCode(carditEnquiryVO.getCompanyCode());
			String consignmentKey = mailbagVO.getConsignmentNumber();
			ConsignmentInformationVO consignInfoVO = consignmentMap.get(consignmentKey);
			if (consignInfoVO == null) {
				consignInfoVO = constructConsignInfoVOForMail(mailbagVO, resditMessageVO, carditEnquiryVO);
				consignmentMap.put(consignmentKey, consignInfoVO);
			}
			updateConsignInfoVOForMail(resditMessageVO, consignInfoVO, mailbagVO, hasTransportInfo, hasHandedoverInfo,
					hasReasonCode);
			if (mailbagVO.getCarditKey() != null && mailbagVO.getCarditKey().trim().length() > 0) {
				resditMessageVO.setCarditExist(MailConstantsVO.FLAG_YES);
			} else {
				resditMessageVO.setCarditExist(MailConstantsVO.FLAG_NO);
			}
		}
		resditMessageVO.setConsignmentInformationVOs(new ArrayList<ConsignmentInformationVO>(consignmentMap.values()));
		generateSequencesForResdit(resditMessageVO);
		resditMessageVOs.add(resditMessageVO);
		log.debug("Resdit" + " : " + "groupResditMessages" + " Exiting");
	}

	/** 
	* TODO Purpose Feb 12, 2007, A-1739
	* @param resditMessageVO
	* @param consignInfoVO
	* @param mailbagVO
	* @param hasTransportInfo
	* @param hasHandedoverInfo
	* @param hasReasonCode
	* @throws SystemException
	*/
	private void updateConsignInfoVOForMail(ResditMessageVO resditMessageVO, ConsignmentInformationVO consignInfoVO,
			MailbagVO mailbagVO, boolean hasTransportInfo, boolean hasHandedoverInfo, boolean hasReasonCode) {
		Collection<ReceptacleInformationVO> receptacleColln = consignInfoVO.getReceptacleInformationVOs();
		if (receptacleColln == null) {
			receptacleColln = new ArrayList<ReceptacleInformationVO>();
			consignInfoVO.setReceptacleInformationVOs(receptacleColln);
		}
		receptacleColln.add(constructReceptVO(mailbagVO));
		if ((consignInfoVO.getTransportInformationVOs() == null
				|| consignInfoVO.getTransportInformationVOs().size() == 0) && hasTransportInfo) {
			populateMailbagTransportInformation(resditMessageVO.getCompanyCode(), consignInfoVO, mailbagVO);
		}
		if (consignInfoVO.getPartnerID() == null && hasHandedoverInfo) {
			populateHandedOverInformation(consignInfoVO, resditMessageVO);
		}
		if (consignInfoVO.getEventReason() == null && hasReasonCode) {
			populateReasonCodes(consignInfoVO, mailbagVO.getCompanyCode(), mailbagVO.getResditEventPort());
		}
		computeResditEventDate(consignInfoVO);
	}

	/** 
	* TODO Purpose Feb 28, 2007, a-1739
	* @param companyCode
	* @param consignInfoVO
	* @param mailbagVO
	* @throws SystemException
	*/
	private void populateMailbagTransportInformation(String companyCode, ConsignmentInformationVO consignInfoVO,
			MailbagVO mailbagVO) {
		Collection<TransportInformationVO> transportInfoVOs = consignInfoVO.getTransportInformationVOs();
		if (transportInfoVOs == null) {
			transportInfoVOs = new ArrayList<TransportInformationVO>();
			consignInfoVO.setTransportInformationVOs(transportInfoVOs);
		}
		TransportInformationVO transportInfoVO = new TransportInformationVO();
		transportInfoVO.setDeparturePlace(mailbagVO.getResditEventPort());
		transportInfoVO.setDepartureLocationQualifier(ResditMessageVO.PLACE_OF_DEPARTURE);
		String conveyRefNo = new StringBuilder().append(mailbagVO.getCarrierCode()).append(FLIGHTID_DELIM)
				.append(mailbagVO.getCarrierId()).append(FLIGHTID_DELIM).append(mailbagVO.getFlightNumber())
				.append(FLIGHTID_DELIM).append(mailbagVO.getFlightSequenceNumber()).append(FLIGHTID_DELIM)
				.append(mailbagVO.getSegmentSerialNumber()).toString();
		transportInfoVO.setConveyanceReference(conveyRefNo);
		updateTransportInformationDetails(companyCode, transportInfoVO);
		if (MailConstantsVO.RESDIT_UPLIFTED.equals(consignInfoVO.getConsignmentEvent())) {
			consignInfoVO.setEventDate(transportInfoVO.getDepartureDate().toGMTDate());
		}
		transportInfoVOs.add(transportInfoVO);
	}

	/** 
	* TODO Purpose Feb 9, 2007, A-1739
	* @param resditMailbagVO
	* @return
	* @throws SystemException 
	*/
	private ReceptacleInformationVO constructReceptVO(MailbagVO resditMailbagVO) {
		ReceptacleInformationVO receptVO = new ReceptacleInformationVO();
		if (findSystemParameterValue(DOMESTIC_PACODE).equals(resditMailbagVO.getPaCode())) {
			receptVO.setReceptacleID(resditMailbagVO.getMailbagId().substring(0, 10));
		} else {
			receptVO.setReceptacleID(resditMailbagVO.getMailbagId());
		}
		receptVO.setPartnerId(resditMailbagVO.getHandoverPartner());
		receptVO.setCarrierCode(resditMailbagVO.getCarrierCode());
		receptVO.setEventDate(LocalDateMapper.toGMTDate(resditMailbagVO.getResditEventUTCDate()));
		receptVO.setEventSequenceNumber(resditMailbagVO.getResditEventSeqNum());
		return receptVO;
	}

	/** 
	* TODO Purpose Feb 12, 2007, A-1739
	* @param mailbagVO
	* @param resditMessageVO
	* @param carditEnquiryVO
	* @return
	*/
	private ConsignmentInformationVO constructConsignInfoVOForMail(MailbagVO mailbagVO, ResditMessageVO resditMessageVO,
			CarditEnquiryVO carditEnquiryVO) {
		if (resditMessageVO.getStationCode() == null) {
			resditMessageVO.setStationCode(mailbagVO.getResditEventPort());
			resditMessageVO.setSenderID(mailbagVO.getCarditRecipientId());
			resditMessageVO.setRecipientID(mailbagVO.getPaCode());
		}
		ConsignmentInformationVO consignInfoVO = new ConsignmentInformationVO();
		consignInfoVO.setConsignmentID(mailbagVO.getConsignmentNumber());
		consignInfoVO.setMessageSequenceNumber(mailbagVO.getResditEventSeqNum());
		consignInfoVO.setConsignmentEvent(carditEnquiryVO.getUnsendResditEvent());
		consignInfoVO.setDateFormatQualifier(201);
		consignInfoVO.setDateQualifier(7);
		consignInfoVO.setTransferLocation(mailbagVO.getResditEventPort());
		consignInfoVO.setConsignmentResponse(CONSIGNMENT_PARTIALLY_ACCEPTED);
		return consignInfoVO;
	}

	/** 
	* TODO Purpose Mar 6, 2007, a-1739
	* @param mailbagResditMap
	* @param mailbagVOs
	*/
	private void groupHandedoverMailbags(Map<String, Collection<MailbagVO>> mailbagResditMap,
			Collection<MailbagVO> mailbagVOs) {
		String carditHndovrPartner = null;
		for (MailbagVO mailbagVO : mailbagVOs) {
			carditHndovrPartner = mailbagVO.getHandoverPartner().concat(mailbagVO.getConsignmentNumber());
			Collection<MailbagVO> resditMailbags = mailbagResditMap.get(carditHndovrPartner);
			if (resditMailbags == null) {
				resditMailbags = new ArrayList<MailbagVO>();
				mailbagResditMap.put(carditHndovrPartner, resditMailbags);
			}
			resditMailbags.add(mailbagVO);
		}
	}

	/** 
	* TODO Purpose Mar 2, 2007, a-1739
	* @param carditEnquiryVO
	* @return
	* @throws SystemException
	*/
	private Map<String, Collection<MailbagVO>> groupMailbagsOfCardit(CarditEnquiryVO carditEnquiryVO) {
		log.debug("Resdit" + " : " + "groupMailbagsOfCardit" + " Entering");
		Map<String, Collection<MailbagVO>> resditMailbagMap = new HashMap<String, Collection<MailbagVO>>();
		String resditEvent = carditEnquiryVO.getUnsendResditEvent();
		Collection<MailbagVO> mailbagVOs = carditEnquiryVO.getMailbagVos();
		if (MailConstantsVO.RESDIT_RETURNED.equals(resditEvent)) {
			groupReturnedMailbags(resditMailbagMap, mailbagVOs, carditEnquiryVO);
		} else if (MailConstantsVO.RESDIT_DELIVERED.equals(resditEvent)
				|| MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(resditEvent)) {
			groupHandedoverMailbags(resditMailbagMap, mailbagVOs);
		} else {
			String carditKey = null;
			for (MailbagVO mailbagVO : mailbagVOs) {
				carditKey = mailbagVO.getConsignmentNumber();
				Collection<MailbagVO> carditMails = resditMailbagMap.get(carditKey);
				if (carditMails == null) {
					carditMails = new ArrayList<MailbagVO>();
					resditMailbagMap.put(carditKey, carditMails);
				}
				carditMails.add(mailbagVO);
			}
		}
		log.debug("Resdit" + " : " + "groupMailbagsOfCardit" + " Exiting");
		return resditMailbagMap;
	}

	/** 
	* TODO Purpose Mar 6, 2007, a-1739
	* @param mailbagResditMap
	* @param mailbagVOs
	* @param carditEnquiryVO
	* @throws SystemException
	*/
	private void groupReturnedMailbags(Map<String, Collection<MailbagVO>> mailbagResditMap,
			Collection<MailbagVO> mailbagVOs, CarditEnquiryVO carditEnquiryVO) {
		String cdtdmgReason = null;
		for (MailbagVO mailbagVO : mailbagVOs) {
			Collection<DamagedMailbagVO> damagedMails = Mailbag.findMailbagDamages(carditEnquiryVO.getCompanyCode(),
					mailbagVO.getMailbagId());
			cdtdmgReason = damagedMails.iterator().next().getDamageCode().concat(mailbagVO.getConsignmentNumber());
			Collection<MailbagVO> resditMailbags = mailbagResditMap.get(cdtdmgReason);
			if (resditMailbags == null) {
				resditMailbags = new ArrayList<MailbagVO>();
				mailbagResditMap.put(cdtdmgReason, resditMailbags);
			}
			resditMailbags.add(mailbagVO);
		}
	}

	/** 
	* @author a-1936This method is used to group the Resdits for the Shipper Built ULDs
	* @param carditEnquiryVO
	* @return
	* @throws SystemException
	*/
	private Map<String, Collection<ContainerVO>> groupResditsForULD(CarditEnquiryVO carditEnquiryVO) {
		log.debug("Resdit" + " : " + "groupResditsForULD" + " Entering");
		Collection<ContainerVO> containerVos = carditEnquiryVO.getContainerVos();
		String resditEvent = carditEnquiryVO.getUnsendResditEvent();
		Map<String, Collection<ContainerVO>> resditContainerMap = new HashMap<String, Collection<ContainerVO>>();
		if (containerVos != null && containerVos.size() > 0) {
			if (MailConstantsVO.RESDIT_ASSIGNED.equals(resditEvent)
					|| MailConstantsVO.RESDIT_UPLIFTED.equals(resditEvent)
					|| MailConstantsVO.RESDIT_LOADED.equals(resditEvent)
					|| MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(resditEvent)) {
				String flightId = null;
				for (ContainerVO containerVO : containerVos) {
					flightId = new StringBuilder().append(containerVO.getCarrierId())
							.append(containerVO.getFlightSequenceNumber()).append(containerVO.getFlightNumber())
							.append(containerVO.getConsignmentDocumentNumber()).toString();
					Collection<ContainerVO> resditContainers = resditContainerMap.get(flightId);
					if (resditContainers == null) {
						resditContainers = new ArrayList<ContainerVO>();
						resditContainerMap.put(flightId, resditContainers);
					}
					resditContainers.add(containerVO);
				}
			} else if (MailConstantsVO.RESDIT_RETURNED.equals(resditEvent)) {
				groupReturnedResditsForULD(resditContainerMap, containerVos, carditEnquiryVO);
			} else if (MailConstantsVO.RESDIT_DELIVERED.equals(resditEvent)
					|| MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(resditEvent)) {
				groupHandedoverResditsForULD(resditContainerMap, containerVos);
			}
		}
		log.debug("Resdit" + " : " + "groupResditsForULD" + " Exiting");
		return resditContainerMap;
	}

	/** 
	* @author a-1936This method is used to group the Handed Over Resdits For ULD..
	* @param resditContainerMap
	* @param containerVos
	*/
	private void groupHandedoverResditsForULD(Map<String, Collection<ContainerVO>> resditContainerMap,
			Collection<ContainerVO> containerVos) {
		log.debug("RESDIT" + " : " + "groupHandedoverResditsForULD" + " Entering");
		String carditHndovrPartner = null;
		for (ContainerVO contianerVO : containerVos) {
			carditHndovrPartner = contianerVO.getHandedOverPartner().concat(contianerVO.getConsignmentDocumentNumber());
			Collection<ContainerVO> resditContainers = resditContainerMap.get(carditHndovrPartner);
			if (resditContainers == null) {
				resditContainers = new ArrayList<ContainerVO>();
				resditContainerMap.put(carditHndovrPartner, resditContainers);
			}
			resditContainers.add(contianerVO);
		}
		log.debug("RESDIT" + " : " + "groupHandedoverResditsForULD" + " Exiting");
	}

	/** 
	* @author a-1936This method is used to group the Returned Resdits for the ULD.
	* @param resditContainerMap
	* @param containerVos
	* @param carditEnquiryVo
	*/
	private void groupReturnedResditsForULD(Map<String, Collection<ContainerVO>> resditContainerMap,
			Collection<ContainerVO> containerVos, CarditEnquiryVO carditEnquiryVo) {
	}

	/** 
	* Groups mailbags to diff messages according to each message copy con of spr_rdt_msg_controller db proc Feb 26, 2007, a-1739
	* @param carditEnquiryVO
	* @return
	* @throws SystemException
	*/
	private Map<String, Collection<MailbagVO>> groupMailbagResdits(CarditEnquiryVO carditEnquiryVO) {
		log.debug("Resdit" + " : " + "groupMailbagResdits" + " Entering");
		Collection<MailbagVO> mailbagVOs = carditEnquiryVO.getMailbagVos();
		String resditEvent = carditEnquiryVO.getUnsendResditEvent();
		Map<String, Collection<MailbagVO>> mailbagResditMap = new HashMap<String, Collection<MailbagVO>>();
		if (MailConstantsVO.RESDIT_ASSIGNED.equals(resditEvent) || MailConstantsVO.RESDIT_UPLIFTED.equals(resditEvent)
				|| MailConstantsVO.RESDIT_LOADED.equals(resditEvent)
				|| MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(resditEvent)) {
			String flightId = null;
			for (MailbagVO mailbagVO : mailbagVOs) {
				flightId = new StringBuilder().append(mailbagVO.getCarrierId())
						.append(mailbagVO.getFlightSequenceNumber()).append(mailbagVO.getFlightNumber())
						.append(mailbagVO.getConsignmentNumber()).toString();
				Collection<MailbagVO> resditMailbags = mailbagResditMap.get(flightId);
				if (resditMailbags == null) {
					resditMailbags = new ArrayList<MailbagVO>();
					mailbagResditMap.put(flightId, resditMailbags);
				}
				resditMailbags.add(mailbagVO);
			}
		} else if (MailConstantsVO.RESDIT_RETURNED.equals(resditEvent)) {
			groupReturnedMailbags(mailbagResditMap, mailbagVOs, carditEnquiryVO);
		} else if (MailConstantsVO.RESDIT_DELIVERED.equals(resditEvent)
				|| MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(resditEvent)) {
			groupHandedoverMailbags(mailbagResditMap, mailbagVOs);
		}
		log.debug("Resdit" + " : " + "groupMailbagResdits" + " Exiting");
		return mailbagResditMap;
	}

	/** 
	* Added for Mail 4
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public static Collection<CarditReferenceInformationVO> findCCForSendResdit(ConsignmentInformationVO consgmntInfo) {
		Collection<CarditReferenceInformationVO> carditRefInfoVo = null;
		try {
			carditRefInfoVo = new Resdit().constructDAO().findCCForSendResdit(consgmntInfo);
		} catch (PersistenceException e) {
		}
		return carditRefInfoVo;
	}

	/** 
	* Method to retrieve the recepient party for XX resdit . Depending on the contract with AA or USPS direct business recepient will be AA or USPS.
	* @param consignmentInformationVOsForXX
	* @return
	* @throws SystemException
	*/
	public static HashMap<String, String> findRecepientForXXResdits(
			Collection<ConsignmentInformationVO> consignmentInformationVOsForXX) {
		try {
			return constructDAO().findRecepientForXXResdits(consignmentInformationVOsForXX);
		} catch (PersistenceException e) {
			return null;
		}
	}

	/** 
	* Invokes the EVT_RCR proc A-1739
	* @param companyCode
	* @throws SystemException
	*/
	public void invokeResditReceiver(String companyCode) {
		log.debug("Resdit" + " : " + "invokeResditReceiver" + " Entering");
		try {
			constructDAO().invokeResditReceiver(companyCode);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		log.debug("Resdit" + " : " + "invokeResditReceiver" + " Exiting");
	}

	/** 
	* Starts the resditbuilding Sep 8, 2006, a-1739
	* @param companyCode
	* @throws SystemException
	*/
	public Collection<ResditEventVO> findResditEvents(String companyCode) {
		log.debug("Resdit" + " : " + "findResditEvents" + " Entering");
		try {
			return constructDAO().findResditEvents(companyCode);
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception.getMessage(), exception);
		}
	}

	/**
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public static HashMap<String, String> findCarditDetailsForResdit(Collection<ResditEventVO> resditEvents) {
		HashMap<String, String> carditDetailsMap = new HashMap<String, String>();
		try {
			Collection<CarditVO> carditVOs = new Resdit().constructDAO().findCarditDetailsForResdit(resditEvents);
			if (carditVOs != null && carditVOs.size() > 0) {
				for (CarditVO carditVO : carditVOs) {
					carditDetailsMap.put(carditVO.getConsignmentNumber(), carditVO.getContractReferenceNumber());
				}
			}
		} catch (PersistenceException e) {
		}
		return carditDetailsMap;
	}

	/** 
	* @param resditEvents
	* @return
	* @throws SystemException
	*/
	public ResditMessageVO buildResditMessageVO(Collection<ResditEventVO> resditEvents) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("Resdit" + " : " + "buildResditMessageVO" + " Entering");
		String resditVersion = "";
		ResditEventVO firstResditEvent = resditEvents.iterator().next();
		log.debug("------ResditEventVO--------" + resditEvents);
		resditVersion = firstResditEvent.getResditVersion();
		ResditMessageVO resditMessageVO = new ResditMessageVO();
		resditMessageVO.setCompanyCode(firstResditEvent.getCompanyCode());
		resditMessageVO.setStationCode(firstResditEvent.getEventPort());
		if (("1.0").equals(resditVersion)) {
			resditMessageVO.setMessageType(ResditMessageVO.MSG_TYP);
		} else if (("1.1").equals(resditVersion)) {
			resditMessageVO.setMessageType(ResditMessageVO.MSG_TYPB);
		} else if ((ResditMessageVO.MSG_VER_DOM).equals(resditVersion)) {
			resditMessageVO.setMessageType(ResditMessageVO.MSG_TYPDOM);
		}
		resditMessageVO.setMessageStandard(ResditMessageVO.EDIFACT);
		populateHeaderDetails(firstResditEvent, resditMessageVO);
		String resditFileName = "";
		ZonedDateTime date = localDateUtil.getLocalDate(null, true);
		resditFileName = new StringBuilder().append("RESDIT_").append(date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")))
				.append(firstResditEvent.getPaCode()).append(firstResditEvent.getCarditExist())
				.append(firstResditEvent.getEventPort()).toString();
		log.debug("\n\nDefault Resdit file name format selected file name generated : " + resditFileName);
		resditMessageVO.setResditFileName(resditFileName);
		log.debug("*******resditMessageVO--------" + resditMessageVO);
		boolean isPartialResditEnabled = false;
		resditMessageVO.setLocNeeded(firstResditEvent.isMsgEventLocationEnabled());
		isPartialResditEnabled = firstResditEvent.isPartialResdit();
		boolean tempPartFlag = isPartialResditEnabled;
		for (ResditEventVO resditEventVO : resditEvents) {
			if (MailConstantsVO.RESDIT_XX.equals(resditEventVO.getResditEventCode())) {
				resditEventVO.setActualResditEvent(MailConstantsVO.RESDIT_XX);
				resditEventVO.setResditEventCode(MailConstantsVO.RESDIT_LOADED);
				tempPartFlag = true;
			} else {
				tempPartFlag = isPartialResditEnabled;
			}
			log.debug(
					"\n\n\n Going to Construct Consignment information and all relevant infos for constructing resdit message");
			log.debug("\n\n\n For Event Code ::" + resditEventVO.getResditEventCode());
			log.debug("\n\n\n Consignment id ::" + resditEventVO.getConsignmentNumber());
			populateConsignmentInformation(resditEventVO, resditMessageVO, tempPartFlag, resditVersion);
		}
		log.debug("\n\n\n\n ----------------final resditMessageVO from builder--------" + resditMessageVO);
		log.debug("Resdit" + " : " + "buildResditMessageVO" + " Exiting");
		return resditMessageVO;
	}

	/** 
	* @param resditEventVO
	* @param resditMessageVO
	* @throws SystemException
	*/
	private void populateHeaderDetails(ResditEventVO resditEventVO, ResditMessageVO resditMessageVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("Resdit" + " : " + "buildHeaderDetails" + " Entering");
		if (!MailConstantsVO.RESDIT_XX.equals(resditEventVO.getResditEventCode())) {
			CarditVO carditVO = Cardit.findCarditDetailsForResdit(resditEventVO.getCompanyCode(),
					resditEventVO.getConsignmentNumber());
			if (carditVO != null) {
				resditMessageVO.setTestIndicator(String.valueOf(carditVO.getTstIndicator()));
			}
		}
		resditMessageVO.setRecipientID(resditEventVO.getPaCode());
		resditMessageVO.setEnvelopeAddress(resditEventVO.getReciever());
		ZonedDateTime preparationDate = localDateUtil.getLocalDate(null, true);
		resditMessageVO.setPreparationDate(preparationDate.format(DateTimeFormatter.ofPattern(PREP_DATE_FORMAT)));
		resditMessageVO.setPreparationTime(preparationDate.format(DateTimeFormatter.ofPattern(PREP_TIME_FORMAT)));
		generateSequencesForResdit(resditMessageVO);
		log.debug("Resdit" + " : " + "buildHeaderDetails" + " Exiting");
	}

	/** 
	* @param resditEventVO
	* @param resditMessageVO
	* @param isPartialResditEnabled
	* @param resditVersion
	* @throws SystemException
	*/
	private void populateConsignmentInformation(ResditEventVO resditEventVO, ResditMessageVO resditMessageVO,
			boolean isPartialResditEnabled, String resditVersion) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		log.debug("Resdit" + " : " + "buildConsignmentInformation" + " Entering");
		log.debug("---------------resditEventVO Coming for populateConsignmentInformation----------" + resditEventVO);
		ConsignmentInformationVO consignInfoVO = new ConsignmentInformationVO();
		CarditEnquiryFilterVO carditEnqFilterVO = new CarditEnquiryFilterVO();
		Collection<ConsignmentRoutingVO> consignmentRoutingVOs = null;
		Map<String, String> connectionARLsMap = null;
		ArrayList<String> conArlList = null;
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		PostalAdministrationVO postalAdministrationVO = null;
		StringBuilder pAWBNo = new StringBuilder();
		if (resditEventVO != null) {
			String eventCode = resditEventVO.getResditEventCode();
			String consignmentNumber = resditEventVO.getConsignmentNumber();
			if (resditEventVO.getShipmentPrefix() != null && resditEventVO.getMasterDocumentNumber() != null) {
				String shipmentPrefix = resditEventVO.getShipmentPrefix();
				String masterDocumentNumber = resditEventVO.getMasterDocumentNumber();
				pAWBNo.append(shipmentPrefix).append("-").append(masterDocumentNumber);
			}
			carditEnqFilterVO.setCompanyCode(resditEventVO.getCompanyCode());
			carditEnqFilterVO.setPaoCode(resditEventVO.getPaCode());
			carditEnqFilterVO.setConsignmentDocument(consignmentNumber);
			String companyCode = resditEventVO.getCompanyCode();
			try {
				consignmentRoutingVOs = constructDAO().findConsignmentRoutingDetails(carditEnqFilterVO);
			} catch (PersistenceException exp) {
				log.debug("---------------PersistenceException----------Caught-----!!!!!!!!");
				log.debug("---------------carditEnqFilterVO----------" + carditEnqFilterVO);
			}
			if (consignmentRoutingVOs != null && consignmentRoutingVOs.size() > 0) {
				for (ConsignmentRoutingVO csgRoutingVO : consignmentRoutingVOs) {
					if (connectionARLsMap == null) {
						connectionARLsMap = new HashMap<String, String>();
						if (logonAttributes.getOwnAirlineIdentifier() != csgRoutingVO.getFlightCarrierId()) {
							connectionARLsMap.put(csgRoutingVO.getFlightCarrierCode(),
									csgRoutingVO.getFlightCarrierCode());
						}
					} else {
						if ((!(connectionARLsMap.containsKey(csgRoutingVO.getFlightCarrierCode())))
								&& (logonAttributes.getOwnAirlineIdentifier() != csgRoutingVO.getFlightCarrierId())) {
							connectionARLsMap.put(csgRoutingVO.getFlightCarrierCode(),
									csgRoutingVO.getFlightCarrierCode());
						}
					}
				}
			}
			consignInfoVO.setConnectionAirlines(new ArrayList<String>());
			if (connectionARLsMap != null && connectionARLsMap.size() > 0) {
				conArlList = new ArrayList<String>();
				for (String ConnectionArl : connectionARLsMap.values()) {
					conArlList.add(ConnectionArl);
				}
				consignInfoVO.setConnectionAirlines(conArlList);
			}
			log.debug("---------------consignInfoVO.getConnectionAirlines----------"
					+ consignInfoVO.getConnectionAirlines());
			consignInfoVO.setListMailbagsChecked(isPartialResditEnabled);
			log.debug("---------------isPartialResditEnabled-----!!!!!!!!" + isPartialResditEnabled);
			log.debug("---------------consignInfoVO.isListMailbagsChecked-----!!!!!!!!"
					+ consignInfoVO.isListMailbagsChecked());
			consignInfoVO.setConsignmentID(consignmentNumber);
			if (resditEventVO.isM49Resdit() && MailConstantsVO.RESDIT_PENDING.equals(eventCode)) {
				eventCode = MailConstantsVO.RESDIT_PENDING_M49;
			}
			consignInfoVO.setConsignmentEvent(eventCode);
			consignInfoVO.setDateFormatQualifier(201);
			consignInfoVO.setDateQualifier(7);
			consignInfoVO.setTransferLocation(resditEventVO.getEventPort());
			consignInfoVO.setLocation(resditEventVO.getEventPortName());
			boolean flag = false;
			String senderID = resditEventVO.getPaCode();
			if (pAWBNo.length() > 0) {
				postalAdministrationVO =mailController.findPACode(resditEventVO.getCompanyCode(), senderID);
				if (!postalAdministrationVO.getPostalAdministrationDetailsVOs().isEmpty()) {
					Collection<PostalAdministrationDetailsVO> paDetails = postalAdministrationVO
							.getPostalAdministrationDetailsVOs().get(INVINFO);
					if (!paDetails.isEmpty()) {
						for (PostalAdministrationDetailsVO paDetail : paDetails) {
							if (paDetail.getParCode().equals(PAWBASSCONENAB)
									&& paDetail.getParameterValue().equalsIgnoreCase(PAWBPARMVALYES)) {
								flag = true;
								break;
							}
						}
					}
				}
				if (flag) {
					consignInfoVO.setConsignmentPAWBNo(pAWBNo.toString());
				}
			}
			boolean isTransportInfoNeeded = false;
			if (!MailConstantsVO.RESDIT_RETURNED.equals(eventCode) && !MailConstantsVO.RESDIT_PENDING.equals(eventCode)
					&& !MailConstantsVO.RESDIT_HANDOVER_RECEIVED.equals(eventCode)) {
				isTransportInfoNeeded = true;
			}
			populateContainerInformation(resditEventVO, consignInfoVO, isTransportInfoNeeded);
			populateMailbagInformation(resditEventVO, consignInfoVO, isTransportInfoNeeded);
			if (consignInfoVO.getReceptacleInformationVOs() == null
					&& consignInfoVO.getContainerInformationVOs() == null) {
				log.error(
						"\n\n\n\n\t\t\t CHECK----->>>>Consignment with no ReceptacleInformation and ContainerInformation !!!!!!!! So removing from resdit list ");
				log.error("\n\n\n\n\t\t\t    EVENT CODE :: " + consignInfoVO.getConsignmentEvent());
				log.error("\n\n\n\n\t\t\t ConsignmentID :: " + consignInfoVO.getConsignmentID());
				log.error("\n\n\n\n\t\t\t Message Seqnum :: " + consignInfoVO.getMessageSequenceNumber());
				log.error("\n\n\n\n consignInfoVO skiped :: \n" + consignInfoVO);
				if (consignInfoVO != null) {
					logError(consignInfoVO);
				}
			} else {
				resditMessageVO.setMailboxId(consignInfoVO.getMailboxID());
				computeResditEventDate(consignInfoVO);
				if ("N".equals(resditEventVO.getCarditExist())) {
					consignInfoVO.setConsignmentResponse(DOCUMENT_NOT_RECIEVED);
				} else {
					consignInfoVO.setConsignmentResponse(CONSIGNMENT_PARTIALLY_ACCEPTED);
				}
				populateLocationQualifier(companyCode, consignInfoVO);
				if (!eventCode.equals(MailConstantsVO.RESDIT_PENDING)
						&& !eventCode.equals(MailConstantsVO.RESDIT_ASSIGNED)) {
					populateHandedOverInformation(consignInfoVO, resditMessageVO);
				}
				if (eventCode.equals(MailConstantsVO.RESDIT_PENDING)
						|| eventCode.equals(MailConstantsVO.RESDIT_RETURNED)) {
					populateReasonCodes(consignInfoVO, resditEventVO.getCompanyCode(), resditEventVO.getEventPort());
				}
				Collection<ConsignmentInformationVO> consignmentVOs = resditMessageVO.getConsignmentInformationVOs();
				if (consignmentVOs == null) {
					consignmentVOs = new ArrayList<ConsignmentInformationVO>();
					resditMessageVO.setConsignmentInformationVOs(consignmentVOs);
				}
				log.debug("\n\n\n\n consignInfoVO constructed and included for resdit :: \n " + consignInfoVO);
				consignmentVOs.add(consignInfoVO);
			}
		}
		log.debug("Resdit" + " : " + "buildConsignmentInformation" + " Exiting");
	}

	/** 
	* @param consignInfoVO
	*/
	private void logError(ConsignmentInformationVO consignInfoVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		try {
			String fileName = System.getProperty("mailtracking.error.file", "/data1/logs/icoprddomain/app")
					+ File.separator + "ErrorLog.txt";
			File file = new File(fileName);
			ZonedDateTime lc = localDateUtil.getLocalDate(null, true);
			Writer output = new BufferedWriter(new FileWriter(file, true));
			try {
				output.write("@ " + lc);
				output.write("\n ErrorVO :> " + consignInfoVO.toString());
			} catch (Exception e) {
			} finally {
				output.close();
			}
		} catch (Exception e) {
		}
	}

	/** 
	* @param companyCode
	* @param consignInfoVO
	*/
	private void populateLocationQualifier(String companyCode, ConsignmentInformationVO consignInfoVO) {
		String destinationOE = "";
		String destinationCity = "";
		String ooe = "";
		if (consignInfoVO.getReceptacleInformationVOs() != null
				&& consignInfoVO.getReceptacleInformationVOs().size() > 0) {
			destinationOE = ((ArrayList<ReceptacleInformationVO>) consignInfoVO.getReceptacleInformationVOs()).get(0)
					.getReceptacleID();
			ooe = ((ArrayList<ReceptacleInformationVO>) consignInfoVO.getReceptacleInformationVOs()).get(0)
					.getOriginExgOffice();
			consignInfoVO.setOriginExgOffice(ooe);
		}
	}

	/** 
	* @param resditEventVO
	* @param consignInfoVO
	* @param isTransportInfoNeeded
	* @throws SystemException
	*/
	private void populateMailbagInformation(ResditEventVO resditEventVO, ConsignmentInformationVO consignInfoVO,
			boolean isTransportInfoNeeded) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("Resdit" + " : " + "populateMailbagInformation" + " Entering");
		Collection<ReceptacleInformationVO> receptacleInfoVOs = null;
		Collection<GoodsItemDetailsVO> groupReceptaclesUnderGid = null;
		log.debug("CHECK ----resditEventVO.getCarditExist()=======>>>" + resditEventVO.getCarditExist());
		if (MailConstantsVO.RESDIT_XX.equals(resditEventVO.getActualResditEvent())) {
			receptacleInfoVOs = MailResdit.findMailbagDetailsForXXResdit(resditEventVO);
		} else {
			if ("N".equals(resditEventVO.getCarditExist())) {
				receptacleInfoVOs = MailResdit.findMailbagDetailsForXXResdit(resditEventVO);
			} else if ("Y".equals(resditEventVO.getCarditExist())) {
				receptacleInfoVOs = MailResdit.findMailbagDetailsForResdit(resditEventVO);
				Integer i = 0;
				Map<Integer, String> bellyCartIDs = null;
				ArrayList<ReceptacleInformationVO> pciReceptaclesUnderTE = new ArrayList<ReceptacleInformationVO>();
				ArrayList<ReceptacleInformationVO> pciReceptaclesToRemove = new ArrayList<ReceptacleInformationVO>();
				ZonedDateTime receptacleEventDate = null;
				if (resditEventVO.getResditEventCode() != null && "74".equals(resditEventVO.getResditEventCode())) {
					if (receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
						receptacleEventDate = getLatestReceptacleEventDate(receptacleInfoVOs);
						for (ReceptacleInformationVO receptacleInfo : receptacleInfoVOs) {
							if (receptacleInfo.getEquipmentQualifier() != null
									&& "TE".equals(receptacleInfo.getEquipmentQualifier())) {
								pciReceptaclesToRemove.add(receptacleInfo);
								if (bellyCartIDs == null) {
									bellyCartIDs = new HashMap<Integer, String>();
									bellyCartIDs.put(i, receptacleInfo.getJourneyID());
									i = 1 + 1;
								} else {
									if (!(bellyCartIDs.containsValue(receptacleInfo.getJourneyID()))) {
										bellyCartIDs.put(i, receptacleInfo.getJourneyID());
										i = 1 + 1;
									}
								}
							}
						}
					}
					ReceptacleInformationVO receptacleUnderTE = null;
					if (bellyCartIDs != null && bellyCartIDs.size() > 0) {
						int siz = bellyCartIDs.size();
						for (int j = 0; j < siz; j++) {
							receptacleUnderTE = new ReceptacleInformationVO();
							receptacleUnderTE.setEventDate(LocalDateMapper.toGMTDate(receptacleEventDate));
							receptacleUnderTE.setReceptacleID(bellyCartIDs.get(j));
							pciReceptaclesUnderTE.add(receptacleUnderTE);
						}
					}
					if (pciReceptaclesToRemove != null && pciReceptaclesToRemove.size() > 0) {
						receptacleInfoVOs.removeAll(pciReceptaclesToRemove);
					}
					if (pciReceptaclesUnderTE != null && pciReceptaclesUnderTE.size() > 0) {
						receptacleInfoVOs.addAll(pciReceptaclesUnderTE);
					}
				} else {
					if (receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
						for (ReceptacleInformationVO receptacleInfo : receptacleInfoVOs) {
							if (receptacleInfo.getEquipmentQualifier() != null
									&& "TE".equals(receptacleInfo.getEquipmentQualifier())) {
								receptacleInfo.setJourneyID(null);
							}
						}
					}
				}
			}
		}
		if (receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
			for (ReceptacleInformationVO receptacleInfo : receptacleInfoVOs) {
				consignInfoVO.setPartyIdentifier(receptacleInfo.getPartyIdentifier());
				consignInfoVO.setMailboxID(receptacleInfo.getMailboxID());
				consignInfoVO.setMailBagDestination(receptacleInfo.getMailBagDestination());
			}
		}
		if (consignInfoVO.getReceptacleInformationVOs() != null
				&& consignInfoVO.getReceptacleInformationVOs().size() > 0) {
			if (receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
				receptacleInfoVOs.addAll(consignInfoVO.getReceptacleInformationVOs());
			} else {
				receptacleInfoVOs = new ArrayList<ReceptacleInformationVO>();
				receptacleInfoVOs.addAll(consignInfoVO.getReceptacleInformationVOs());
			}
		}
		log.debug("\n\n Check !!! ReceptacleInfoVOs before grouping=======>>>" + receptacleInfoVOs);
		log.debug("\n\n Check !!! GidVos before grouping under GID=======>>>" + consignInfoVO.getGIDVOs());
		if (receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
			groupReceptaclesUnderGid = groupReceptaclesUnderGid((ArrayList<ReceptacleInformationVO>) receptacleInfoVOs);
			if (groupReceptaclesUnderGid != null && groupReceptaclesUnderGid.size() > 0) {
				consignInfoVO.setGIDVOs(groupReceptaclesUnderGid);
			}
			consignInfoVO.setReceptacleInformationVOs(receptacleInfoVOs);
			log.debug("\n\n Check !!! ReceptacleInfoVOs After grouping=======>>>"
					+ consignInfoVO.getReceptacleInformationVOs());
			log.debug("\n\n Check !!! GidVos After grouping under GID=======>>>" + consignInfoVO.getGIDVOs());
			if (isTransportInfoNeeded) {
				Collection<TransportInformationVO> consignTransportInfoVOs = consignInfoVO.getTransportInformationVOs();
				if (consignTransportInfoVOs == null) {
					consignTransportInfoVOs = new ArrayList<TransportInformationVO>();
					consignInfoVO.setTransportInformationVOs(consignTransportInfoVOs);
				}
				if (consignTransportInfoVOs.size() == 0) {
					Collection<TransportInformationVO> transportInfoVOs = populateTransportInformationFromMailbag(
							resditEventVO);
					if (transportInfoVOs != null) {
						for (TransportInformationVO transportVO : transportInfoVOs) {
							if (MailConstantsVO.RESDIT_UPLIFTED.equals(consignInfoVO.getConsignmentEvent())) {
								if (transportVO.getDepartureTime() != null) {
									consignInfoVO.setEventDate(transportVO.getDepartureTime().toGMTDate());
								} else {
									consignInfoVO.setEventDate(LocalDateMapper.toGMTDate(resditEventVO.getEventDate()));
									transportVO.setDepartureTime(LocalDateMapper.toLocalDate(resditEventVO.getEventDate()));
									transportVO.setArrivalTime(LocalDateMapper.toLocalDate(resditEventVO.getEventDate()));
								}
							}
						}
					}
					consignTransportInfoVOs.addAll(transportInfoVOs);
				}
			}
		}
		log.debug("Resdit" + " : " + "populateMailbagInformation" + " Exiting");
	}

	/** 
	* @param resditEventVO
	* @return
	* @throws SystemException
	*/
	private Collection<TransportInformationVO> populateTransportInformationFromMailbag(ResditEventVO resditEventVO) {
		log.debug("Resdit" + " : " + "populateTransportInformationFromMailbag" + " Entering");
		Collection<TransportInformationVO> transportInfoVOs = new ArrayList<TransportInformationVO>();
		Collection<TransportInformationVO> transportInfoVOsFromCardit = null;
		CarditEnquiryFilterVO carditEnqFilterVO = new CarditEnquiryFilterVO();
		transportInfoVOs = constructTransportDetailsForMailbag(resditEventVO, transportInfoVOs);
		if (transportInfoVOs != null && transportInfoVOs.size() > 0) {
			if (resditEventVO.getFlightNumber() != null && resditEventVO.getFlightNumber().trim().length() > 0) {
				updateTransportInformationDetails(resditEventVO.getCompanyCode(), transportInfoVOs.iterator().next());
			}
		}
		if (MailConstantsVO.RESDIT_UPLIFTED.equals(resditEventVO.getResditEventCode())) {
			if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.SYSPAR_USPS_ENHMNT))
					&& MailConstantsVO.USPOST.equals(resditEventVO.getPaCode())) {
				carditEnqFilterVO.setCompanyCode(resditEventVO.getCompanyCode());
				carditEnqFilterVO.setPaoCode(resditEventVO.getPaCode());
				carditEnqFilterVO.setConsignmentDocument(resditEventVO.getConsignmentNumber());
				String airport = resditEventVO.getEventPort();
				try {
					transportInfoVOsFromCardit = constructDAO().findRoutingDetailsFromCardit(carditEnqFilterVO,
							airport);
				} catch (PersistenceException exp) {
					log.debug("---------------PersistenceException----------Caught-----!!!!!!!!");
					log.debug("---------------carditEnqFilterVO----------" + carditEnqFilterVO);
				}
				if (transportInfoVOsFromCardit != null && transportInfoVOsFromCardit.size() > 0) {
					for (TransportInformationVO tranVOCardit : transportInfoVOsFromCardit) {
						if (!resditEventVO.getEventPort().equals(tranVOCardit.getDeparturePlace())) {
							transportInfoVOs.add(tranVOCardit);
						}
					}
				}
			}
		}
		log.debug("Resdit" + " : " + "populateTransportInformationFromMailbag" + " Exiting");
		return transportInfoVOs;
	}

	/** 
	* @param receptacleInfoVOs
	* @return
	*/
	private Collection<GoodsItemDetailsVO> groupReceptaclesUnderGid(
			ArrayList<ReceptacleInformationVO> receptacleInfoVOs) {
		log.debug("Resdit" + " : " + "groupReceptaclesUnderGid" + " Entering");
		Collection<GoodsItemDetailsVO> groupReceptaclesUnderGids = new ArrayList<GoodsItemDetailsVO>();
		log.debug("ResditMessageEncoder" + " : " + "groupReceptaclesForPCI" + " Entering");
		GoodsItemDetailsVO goodsItemDetailsVO = new GoodsItemDetailsVO();
		ArrayList<ArrayList<ReceptacleInformationVO>> pciReceptacles = new ArrayList<ArrayList<ReceptacleInformationVO>>();
		goodsItemDetailsVO.setPciReceptacles(pciReceptacles);
		ArrayList<ReceptacleInformationVO> tenReceptacles = new ArrayList<ReceptacleInformationVO>();
		int idx = 0;
		boolean isDerived = false;
		for (ReceptacleInformationVO receptacleInfo : receptacleInfoVOs) {
			if (receptacleInfo.getJourneyID() != null) {
				isDerived = true;
			}
			tenReceptacles.add(receptacleInfo);
			if (tenReceptacles.size() == 10) {
				pciReceptacles.add(tenReceptacles);
				goodsItemDetailsVO.setPciReceptacles(pciReceptacles);
				tenReceptacles = new ArrayList<ReceptacleInformationVO>();
			}
			if (pciReceptacles.size() == 99) {
				goodsItemDetailsVO.setPciReceptacles(pciReceptacles);
				if (isDerived) {
					goodsItemDetailsVO.setRecepOrContainerScanned(0);
				} else {
					goodsItemDetailsVO.setRecepOrContainerScanned(1);
				}
				isDerived = false;
				groupReceptaclesUnderGids.add(goodsItemDetailsVO);
				pciReceptacles = new ArrayList<ArrayList<ReceptacleInformationVO>>();
				goodsItemDetailsVO = new GoodsItemDetailsVO();
			}
			idx++;
		}
		if ((tenReceptacles.size() > 0 && tenReceptacles.size() < 10)) {
			pciReceptacles.add(tenReceptacles);
			goodsItemDetailsVO.setPciReceptacles(pciReceptacles);
			if (isDerived) {
				goodsItemDetailsVO.setRecepOrContainerScanned(0);
			} else {
				goodsItemDetailsVO.setRecepOrContainerScanned(1);
			}
		}
		groupReceptaclesUnderGids.add(goodsItemDetailsVO);
		log.debug("Resdit" + " : " + "groupReceptaclesUnderGid" + " Exiting");
		return groupReceptaclesUnderGids;
	}

	/** 
	* @param resditEventVO
	* @param consignInfoVO
	* @param isTransportInfoNeeded
	* @throws SystemException
	*/
	private void populateContainerInformation(ResditEventVO resditEventVO, ConsignmentInformationVO consignInfoVO,
			boolean isTransportInfoNeeded) {
		log.debug("Resdit" + " : " + "populateContainerInformation" + " Entering");
		Collection<ContainerInformationVO> containerInformationVOs = null;
		if ("N".equals(resditEventVO.getCarditExist())) {
			containerInformationVOs = UldResdit.findULDDetailsForResditWithoutCardit(resditEventVO);
			ReceptacleInformationVO receptacleInformationVO = null;
			Collection<ContainerInformationVO> containerInformationToRemove = new ArrayList<ContainerInformationVO>();
			Collection<ReceptacleInformationVO> newReceptacleInformationVOs = new ArrayList<ReceptacleInformationVO>();
			if (containerInformationVOs != null && containerInformationVOs.size() > 0) {
				ZonedDateTime containerEventDate = null;
				containerEventDate = getLatestContainerEventDate(containerInformationVOs);
				for (ContainerInformationVO containerInformationVO : containerInformationVOs) {
					if (containerInformationVO.getJourneyID() != null) {
						containerInformationToRemove.add(containerInformationVO);
						receptacleInformationVO = new ReceptacleInformationVO();
						receptacleInformationVO.setEventDate(LocalDateMapper.toGMTDate(containerEventDate));
						receptacleInformationVO.setPartnerId(containerInformationVO.getPartnerId());
						receptacleInformationVO.setReceptacleID(containerInformationVO.getJourneyID());
						newReceptacleInformationVOs.add(receptacleInformationVO);
					}
				}
				if (containerInformationToRemove != null && containerInformationToRemove.size() > 0) {
					containerInformationVOs.removeAll(containerInformationToRemove);
				}
				if (newReceptacleInformationVOs != null && newReceptacleInformationVOs.size() > 0) {
					if (consignInfoVO.getReceptacleInformationVOs() != null
							&& consignInfoVO.getReceptacleInformationVOs().size() > 0) {
						consignInfoVO.getReceptacleInformationVOs().addAll(newReceptacleInformationVOs);
					} else {
						consignInfoVO.setReceptacleInformationVOs(newReceptacleInformationVOs);
					}
				}
			}
		} else {
			containerInformationVOs = UldResdit.findULDDetailsForResdit(resditEventVO);
		}
		if (containerInformationVOs != null && containerInformationVOs.size() > 0) {
			consignInfoVO.setContainerInformationVOs(containerInformationVOs);
			if (isTransportInfoNeeded) {
				Collection<TransportInformationVO> consignTransportInfoVOs = consignInfoVO.getTransportInformationVOs();
				if (consignTransportInfoVOs == null) {
					consignTransportInfoVOs = new ArrayList<TransportInformationVO>();
					consignInfoVO.setTransportInformationVOs(consignTransportInfoVOs);
				}
				if (consignTransportInfoVOs.size() == 0) {
					Collection<TransportInformationVO> transportInfoVOs = populateTransportInformationFromULDs(
							resditEventVO);
					if (transportInfoVOs != null) {
						for (TransportInformationVO transportVO : transportInfoVOs) {
							if (MailConstantsVO.RESDIT_UPLIFTED.equals(consignInfoVO.getConsignmentEvent())) {
								consignInfoVO.setEventDate(transportVO.getDepartureTime().toGMTDate());
							}
						}
					}
					consignTransportInfoVOs.addAll(transportInfoVOs);
				}
			}
		}
		log.debug("Resdit" + " : " + "populateContainerInformation" + " Exiting");
	}

	/** 
	* @param resditEventVO
	* @return
	* @throws SystemException
	*/
	private Collection<TransportInformationVO> populateTransportInformationFromULDs(ResditEventVO resditEventVO) {
		log.debug("Resdit" + " : " + "populateTransportInformationFromULDs" + " Entering");
		Collection<TransportInformationVO> containerTransports = UldResdit.findTransportDetailsForULD(resditEventVO);
		if (containerTransports != null && containerTransports.size() > 0) {
			for (TransportInformationVO transportInformationVO : containerTransports) {
				if (resditEventVO.getEventPort() != null) {
					transportInformationVO.setDepartureTime(new com.ibsplc.icargo.framework.util.time.LocalDate(
							resditEventVO.getEventPort(), Location.ARP, true));
				}
				updateTransportInformationDetails(resditEventVO.getCompanyCode(), transportInformationVO);
			}
		}
		log.debug("Resdit" + " : " + "populateTransportInformationFromULDs" + " Exiting");
		return containerTransports;
	}

	private Collection<TransportInformationVO> constructTransportDetailsForMailbag(ResditEventVO resditEventVO,
			Collection<TransportInformationVO> transportInfoVOs) {
		log.debug("Resdit" + " : " + "constructTransportDetailsForMailbag" + " Entering");
		TransportInformationVO transportInfoVO = new TransportInformationVO();
		transportInfoVO.setDeparturePlace(resditEventVO.getEventPort());
		transportInfoVO.setDepartureLocationQualifier(ResditMessageVO.PLACE_OF_DEPARTURE);
		String conveyRefNo = new StringBuilder()
				.append(resditEventVO.getCarrierCode() != null ? resditEventVO.getCarrierCode() : "")
				.append(FLIGHTID_DELIM).append(resditEventVO.getCarrierId()).append(FLIGHTID_DELIM)
				.append(resditEventVO.getFlightNumber() != null ? resditEventVO.getFlightNumber() : "")
				.append(FLIGHTID_DELIM).append(resditEventVO.getFlightSequenceNumber()).append(FLIGHTID_DELIM)
				.append(resditEventVO.getSegmentSerialNumber()).toString();
		transportInfoVO.setConveyanceReference(conveyRefNo);
		transportInfoVO.setCarrierName(resditEventVO.getPartyName());
		transportInfoVOs.add(transportInfoVO);
		log.debug("Resdit" + " : " + "constructTransportDetailsForMailbag" + " Exiting");
		return transportInfoVOs;
	}
}
