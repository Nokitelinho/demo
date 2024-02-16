package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfigurationBuilder;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.component.proxy.*;
import com.ibsplc.neoicargo.mail.exception.*;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;

/** 
 * @author a-1883
 */
@Slf4j
@Component
public class MailTransfer {
	private static final String INVALID_FLIGHT_NO = "-1";
	private static final String MAIL_CONTROLLER_BEAN = "mAilcontroller";

	@Autowired
    MailOperationsMapper mailOperationsMapper;

	/** 
	* @param containerVOs
	* @param operationalFlightVO
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public Map<String, Object> transferContainers(Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO, String printFlag) throws InvalidFlightSegmentException,
			ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException {
		MailMRAProxy mailOperationsMRAProxy = ContextUtil.getInstance().getBean(MailMRAProxy.class);
		log.debug("MailTransfer" + " : " + "transferContainers" + " Entering");
		Map<MailbagPK, MailbagVO> mailMap = new HashMap<MailbagPK, MailbagVO>();
		Collection<ULDForSegmentVO> uLDForSegmentVOs = saveArrivalDetailsForTransfer(containerVOs, operationalFlightVO,
				mailMap);
		Map<String, Object> mapForReturn = new HashMap<String, Object>();
		int toFlightSegSerialNum = -1;
		if (operationalFlightVO.getFlightSequenceNumber() > 0) {
			new MailController().calculateContentID(containerVOs, operationalFlightVO);
			toFlightSegSerialNum = saveOutboundDetailsForTransfer(containerVOs, operationalFlightVO, uLDForSegmentVOs);
			for (ULDForSegmentVO uldForSegmentVO : uLDForSegmentVOs) {
				ULDForSegmentVO uldForSegVO = mailOperationsMapper.ULDForSegmentVO(uldForSegmentVO);
				uldForSegVO.setPou(operationalFlightVO.getPou());
				uldForSegVO.setCarrierId(operationalFlightVO.getCarrierId());
				uldForSegVO.setFlightNumber(operationalFlightVO.getFlightNumber());
				uldForSegVO.setFlightDate(operationalFlightVO.getFlightDate());
				uldForSegVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				new ReassignController().updateBookingForFlight(uldForSegVO, operationalFlightVO,
						"UPDATE_BOOKING_TO_FLIGHT");
			}
			for (ContainerVO containerVO : containerVOs) {
				if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType()) || containerVO.isUldTobarrow()) {
					if (isULDIntegrationEnabled() && ContainerVO.OPERATION_FLAG_INSERT.equals(containerVO.getOperationFlag())) {
						updateULDForOperations(containerVOs, operationalFlightVO, false);
					}
				}
			}
		} else {
			saveDestAssignednDetailsForTransfer(containerVOs, uLDForSegmentVOs, operationalFlightVO);
		}
		Collection<ContainerVO> transferContainers = updateTransferredContainers(containerVOs, operationalFlightVO,
				toFlightSegSerialNum);
		Collection<MailbagVO> transferredMails = updateMailBagInULD(mailMap, operationalFlightVO, toFlightSegSerialNum,
				containerVOs);
		flagResditsForContainerTransfer(transferredMails, containerVOs, operationalFlightVO);
		String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
		if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			mailController.flagMLDForContainerTransfer(transferredMails, containerVOs, operationalFlightVO);
		}
		boolean provisionalRateImport = false;
		Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(operationalFlightVO,
				((ArrayList<ContainerVO>) containerVOs).get(0), transferredMails,
				MailConstantsVO.MAIL_STATUS_TRANSFER_MAIL, provisionalRateImport);
		log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
		if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
			String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
			if (importEnabled != null && (importEnabled.contains("A") || importEnabled.contains("D"))) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		String provisionalRateimportEnabled = findSystemParameterValue(
				MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
			Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(operationalFlightVO,
					((ArrayList<ContainerVO>) containerVOs).get(0), transferredMails,
					MailConstantsVO.MAIL_STATUS_TRANSFERRED, provisionalRateImport);
			if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		if (transferContainers != null && transferContainers.size() > 0) {
			mapForReturn.put(MailConstantsVO.CONST_CONTAINER, transferContainers);
		}
		if (uLDForSegmentVOs != null && uLDForSegmentVOs.size() > 0) {
			mapForReturn.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
					createContainerDtlsForTransfermanifest(uLDForSegmentVOs, transferredMails));
		}
		log.debug("MailTransfer" + " : " + "transferContainers" + " Exiting");
		return mapForReturn;
	}

	/** 
	* @author A-3227
	* @param doe
	* @param companyCode
	* @param deliveredPort
	* @param cityCache
	* @return
	* @throws SystemException
	*/
	private boolean isValidDeliveryAirport(String doe, String companyCode, String deliveredPort,
			Map<String, String> cityCache) {
		log.debug("MailTransfer" + " : " + "isValidDeliveryAirport" + " Entering");
		Collection<String> officeOfExchanges = new ArrayList<String>();
		if (doe != null && doe.length() > 0) {
			officeOfExchanges.add(doe);
		}
		String deliveryCityCode = null;
		String nearestAirport = null;
		String nearestAirportToCity = null;
		log.debug("" + "----officeOfExchanges---" + " " + officeOfExchanges);
		Collection<ArrayList<String>> groupedOECityArpCodes = ContextUtil.getInstance().getBean(MailController.class)
				.findCityAndAirportForOE(companyCode,
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
		if (nearestAirport != null && nearestAirport.equals(deliveredPort)) {
			log.debug("" + "----nearestAirport---" + " " + nearestAirport);
			log.debug("" + "----deliveredPort---" + " " + deliveredPort);
			return true;
		}
		return false;
	}

	/** 
	* TODO Purpose Feb 1, 2007, A-1739
	* @param mailbagVOs
	* @param airportCode
	* @throws SystemException
	*/
	private void removeTerminatingMailbags(Collection<MailbagVO> mailbagVOs, String airportCode) {
		Collection<MailbagVO> mailbagsToRem = new ArrayList<MailbagVO>();
		Map<String, String> cityCache = new HashMap<String, String>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			if (isValidDeliveryAirport(mailbagVO.getDoe(), mailbagVO.getCompanyCode(), airportCode, cityCache)) {
				mailbagsToRem.add(mailbagVO);
			}
		}
		mailbagVOs.removeAll(mailbagsToRem);
	}

	/** 
	* @author a-1936 Added By Karthick V as the part of the NCA Mail TrackingCR .. Reset the Flight Pks to Carrier Needed for the Reassign ..
	* @param mailBags
	* @throws SystemException
	*/
	private Collection<MailbagVO> checkArrivedMailbagsInTransfer(Collection<MailbagVO> mailBags) {
		Collection<MailbagVO> arrivedMails = new ArrayList<MailbagVO>();
		for (MailbagVO mailBag : mailBags) {
			if (MailbagVO.FLAG_YES.equals(mailBag.getArrivedFlag()) || "ARR".equals(mailBag.getLatestStatus())) {
				MailbagVO arrivedMailbagVO = mailOperationsMapper.copyMailbagVO(mailBag);
				arrivedMailbagVO.setFinalDestination(null);
				arrivedMailbagVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
				arrivedMailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				arrivedMailbagVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
				arrivedMailbagVO.setPou(null);
				arrivedMailbagVO.setUldNumber(MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR)
						.concat(mailBag.getCarrierCode()));
				arrivedMailbagVO.setContainerNumber(arrivedMailbagVO.getUldNumber());
				arrivedMailbagVO.setContainerType(mailBag.getContainerType());
				arrivedMails.add(arrivedMailbagVO);
			}
		}
		return arrivedMails;
	}

	/** 
	* @param mailbagVO
	* @param airportCode
	* @return
	* @throws SystemException
	*/
	private AssignedFlightPK createMailbagInboundFlightPK(MailbagVO mailbagVO, String airportCode) {
		FlightOperationsProxy flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		log.debug("MailTransfer" + " : " + "createMailbagInboundFlightPK" + " Entering");
		AssignedFlightPK inboundFlightPK = new AssignedFlightPK();
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
		flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
		Collection<FlightValidationVO> flightValidationVOs = flightOperationsProxy
				.validateFlightForAirport(flightFilterVO);
		FlightValidationVO validVO = null;
		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				if (flightValidationVO.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber()) {
					validVO = flightValidationVO;
					break;
				}
			}
		}
		if (validVO != null && mailbagVO.getLegSerialNumber() == 0) {
			inboundFlightPK.setLegSerialNumber(validVO.getLegSerialNumber());
		} else {
			inboundFlightPK.setLegSerialNumber(mailbagVO.getLegSerialNumber());
		}
		inboundFlightPK.setCompanyCode(mailbagVO.getCompanyCode());
		inboundFlightPK.setCarrierId(mailbagVO.getCarrierId());
		inboundFlightPK.setFlightNumber(mailbagVO.getFlightNumber());
		inboundFlightPK.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		inboundFlightPK.setAirportCode(airportCode);
		log.debug("MailTransfer" + " : " + "createMailbagInboundFlightPK" + " Exiting");
		return inboundFlightPK;
	}

	/** 
	* returns InboundFlight and mailbags map
	* @param mailbagVOs
	* @param airportCode
	* @return
	* @throws SystemException
	*/
	private Map<AssignedFlightPK, Collection<MailbagVO>> constructFlightMailbagMap(Collection<MailbagVO> mailbagVOs,
			String airportCode) {
		log.debug("MailTransfer" + " : " + "constructFlightMailbagMap" + " Entering");
		Map<AssignedFlightPK, Collection<MailbagVO>> flightMailbagMap = new HashMap<AssignedFlightPK, Collection<MailbagVO>>();
		Collection<MailbagVO> newMailbagVos = null;
		for (MailbagVO mailbagVO : mailbagVOs) {
			AssignedFlightPK inboundFlightPK = createMailbagInboundFlightPK(mailbagVO, airportCode);
			newMailbagVos = flightMailbagMap.get(inboundFlightPK);
			if (newMailbagVos == null) {
				newMailbagVos = new ArrayList<MailbagVO>();
				flightMailbagMap.put(inboundFlightPK, newMailbagVos);
			}
			newMailbagVos.add(mailbagVO);
		}
		log.debug("MailTransfer" + " : " + "constructFlightMailbagMap" + " Exiting");
		return flightMailbagMap;
	}

	/** 
	* Inbound mailbags
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	*/
	public void saveMailbagsInboundDtlsForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		log.debug("MailTransfer" + " : " + "saveMailbagsInboundDtlsForTransfer" + " Entering");
		Map<AssignedFlightPK, Collection<MailbagVO>> flightMailbagVOsMap = constructFlightMailbagMap(mailbagVOs,
				containerVO.getAssignedPort());
		for (AssignedFlightPK inboundFlightPK : flightMailbagVOsMap.keySet()) {
			if (inboundFlightPK.getFlightNumber() != null
					&& !INVALID_FLIGHT_NO.equals(inboundFlightPK.getFlightNumber())) {
				AssignedFlight inboundFlight = findOrCreateInboundFlight(inboundFlightPK);
				inboundFlight.saveMailArrivalDetailsForTransfer(flightMailbagVOsMap.get(inboundFlightPK), containerVO);
				if (containerVO.isFromDeviationList()) {
					inboundFlight.releaseContainer(containerVO);
				}
			}
		}
		log.debug("MailTransfer" + " : " + "saveMailbagsInboundDtlsForTransfer" + " Exiting");
	}

	/** 
	* @param containerVO
	* @return
	*/
	private AssignedFlightPK constructAssignedFlightPKForMail(ContainerVO containerVO) {
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(containerVO.getAssignedPort());
		assignedFlightPk.setCompanyCode(containerVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(containerVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(containerVO.getLegSerialNumber());
		assignedFlightPk.setCarrierId(containerVO.getCarrierId());
		return assignedFlightPk;
	}

	/** 
	* This method is used to create the assignedFlight
	* @throws SystemException
	*/
	private AssignedFlight createAssignedFlightForMail(ContainerVO containerVO) {
		log.debug("MailTransfer" + " : " + "createAssignedFlightForMail" + " Entering");
		AssignedFlightVO assignedFlightVO = new AssignedFlightVO();
		assignedFlightVO.setAirportCode(containerVO.getAssignedPort());
		assignedFlightVO.setCarrierCode(containerVO.getCarrierCode());
		assignedFlightVO.setCarrierId(containerVO.getCarrierId());
		assignedFlightVO.setCompanyCode(containerVO.getCompanyCode());
		assignedFlightVO.setFlightDate(containerVO.getFlightDate());
		assignedFlightVO.setFlightNumber(containerVO.getFlightNumber());
		assignedFlightVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		assignedFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
		assignedFlightVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		AssignedFlight assignedFlight = new AssignedFlight(assignedFlightVO);
		log.debug("MailTransfer" + " : " + "createAssignedFlightForMail" + " Exiting");
		return assignedFlight;
	}

	/** 
	* @param containerVO
	* @throws SystemException
	*/
	private void validateAndCreateAsgdFltForMailbag(ContainerVO containerVO) {
		log.debug("MailTransfer" + " : " + "validateAndCreateAsgdFltForMailbag" + " Entering");
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPk = constructAssignedFlightPKForMail(containerVO);
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.info("FINDER EXCEPTION IS THROWN");
			assignedFlight = createAssignedFlightForMail(containerVO);
		}
		log.debug("MailTransfer" + " : " + "validateAndCreateAsgdFltForMailbag" + " Exiting");
	}

	/** 
	* @param containerVO
	* @param segmentSerialNumber
	* @return
	*/
	private AssignedFlightSegmentVO constructAssignedFltSegmentVOForMail(ContainerVO containerVO,
			int segmentSerialNumber) {
		AssignedFlightSegmentVO assignedFlightSegmentVO = new AssignedFlightSegmentVO();
		assignedFlightSegmentVO.setCarrierId(containerVO.getCarrierId());
		assignedFlightSegmentVO.setCompanyCode(containerVO.getCompanyCode());
		assignedFlightSegmentVO.setFlightNumber(containerVO.getFlightNumber());
		assignedFlightSegmentVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		assignedFlightSegmentVO.setSegmentSerialNumber(segmentSerialNumber);
		assignedFlightSegmentVO.setPol(containerVO.getAssignedPort());
		assignedFlightSegmentVO.setPou(containerVO.getPou());
		return assignedFlightSegmentVO;
	}

	/** 
	* @param containerVO
	* @return
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	*/
	private AssignedFlightSegment findOrCreateAsgdFltSegmentForMail(ContainerVO containerVO)
			throws InvalidFlightSegmentException {
		log.debug("MailTransfer" + " : " + "findOrCreateAsgdFltSegmentForMail" + " Entering");
		AssignedFlightSegment assignedFlightSegment = null;
		int segmentSerialNumber = findFlightSegment(containerVO.getCompanyCode(), containerVO.getCarrierId(),
				containerVO.getFlightNumber(), containerVO.getFlightSequenceNumber(), containerVO.getAssignedPort(),
				containerVO.getPou());
		AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
		assignedFlightSegmentPK.setCarrierId(containerVO.getCarrierId());
		assignedFlightSegmentPK.setCompanyCode(containerVO.getCompanyCode());
		assignedFlightSegmentPK.setFlightNumber(containerVO.getFlightNumber());
		assignedFlightSegmentPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		assignedFlightSegmentPK.setSegmentSerialNumber(segmentSerialNumber);
		try {
			assignedFlightSegment = AssignedFlightSegment.find(assignedFlightSegmentPK);
		} catch (FinderException finderException) {
			log.error("Catching FinderException ");
			AssignedFlightSegmentVO assignedFlightSegmentVO = constructAssignedFltSegmentVOForMail(containerVO,
					segmentSerialNumber);
			assignedFlightSegment = new AssignedFlightSegment(assignedFlightSegmentVO);
		}
		log.debug("MailTransfer" + " : " + "findOrCreateAsgdFltSegmentForMail" + " Exiting");
		return assignedFlightSegment;
	}

	/** 
	* @author A-3227 RENO K ABRAHAM,  Added on 24/09/08
	* @param mailbagVOs
	* @param despatchDetailsVOs
	* @param containerVO
	* @throws SystemException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	private void updateBookingForToFlight(Collection<MailbagVO> mailbagVOs,
			Collection<DespatchDetailsVO> despatchDetailsVOs, ContainerVO containerVO)
			throws CapacityBookingProxyException, MailBookingException {
		log.debug("MailTransfer" + " : " + "updateBookingForFlight" + " Entering");
		Collection<MailbagVO> flightAssignedMailbags = null;
		Collection<DespatchDetailsVO> flightAssignedDespatches = null;
		if (mailbagVOs != null && mailbagVOs.size() > 0 && containerVO != null) {
			flightAssignedMailbags = new ArrayList<MailbagVO>();
			for (MailbagVO mailbagVO : mailbagVOs) {
				MailbagVO mailVO = mailOperationsMapper.copyMailbagVO(mailbagVO);
				mailVO.setCompanyCode(containerVO.getCompanyCode());
				mailVO.setCarrierId(containerVO.getCarrierId());
				mailVO.setFlightNumber(containerVO.getFlightNumber());
				mailVO.setFlightDate(containerVO.getFlightDate());
				mailVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				mailVO.setLegSerialNumber(containerVO.getLegSerialNumber());
				mailVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
				mailVO.setPou(containerVO.getPou());
				mailVO.setPol(containerVO.getAssignedPort());
				mailVO.setContainerNumber(containerVO.getContainerNumber());
				mailVO.setUldNumber(containerVO.getContainerNumber());
				mailVO.setContainerType(containerVO.getType());
				flightAssignedMailbags.add(mailVO);
			}
		}
		if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0 && containerVO != null) {
			flightAssignedDespatches = new ArrayList<DespatchDetailsVO>();
			for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
				DespatchDetailsVO despatchVO = mailOperationsMapper.copyDespatchDetailsVO(despatchDetailsVO);
				despatchVO.setCompanyCode(containerVO.getCompanyCode());
				despatchVO.setCarrierId(containerVO.getCarrierId());
				despatchVO.setFlightNumber(containerVO.getFlightNumber());
				despatchVO.setFlightDate(containerVO.getFlightDate());
				despatchVO.setLegSerialNumber(containerVO.getLegSerialNumber());
				despatchVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				despatchVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
				despatchVO.setPou(containerVO.getPou());
				despatchVO.setAirportCode(containerVO.getAssignedPort());
				despatchVO.setContainerNumber(containerVO.getContainerNumber());
				despatchVO.setUldNumber(containerVO.getContainerNumber());
				despatchVO.setContainerType(containerVO.getType());
				flightAssignedDespatches.add(despatchVO);
			}
		}
		log.debug("MailTransfer" + " : " + "updateBookingForFlight" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	private void saveOutboundMailsFlightForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO)
			throws InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException {
		log.debug("MailTransfer" + " : " + "saveOutboundMailsFlightForTransfer" + " Entering");
		validateAndCreateAsgdFltForMailbag(containerVO);
		AssignedFlightSegment assignedFlightSegment = findOrCreateAsgdFltSegmentForMail(containerVO);
		assignedFlightSegment.saveOutboundMailsFlightForTransfer(mailbagVOs, containerVO);
		updateBookingForToFlight(mailbagVOs, null, containerVO);
		log.debug("MailTransfer" + " : " + "saveOutboundMailsFlightForTransfer" + " Exiting");
	}

	private String constructBulkULDNumber(String bulkPou) {
		return new StringBuilder().append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR).append(bulkPou)
				.toString();
	}

	/** 
	* @param containerVO
	* @return
	*/
	private ULDAtAirportVO constructULDAtAirportVO(ContainerVO containerVO) {
		ULDAtAirportVO uLDAtAirportVO = new ULDAtAirportVO();
		uLDAtAirportVO.setCompanyCode(containerVO.getCompanyCode());
		uLDAtAirportVO.setUldNumber(containerVO.getContainerNumber());
		uLDAtAirportVO.setAirportCode(containerVO.getAssignedPort());
		uLDAtAirportVO.setCarrierId(containerVO.getCarrierId());
		uLDAtAirportVO.setCarrierCode(containerVO.getCarrierCode());
		uLDAtAirportVO.setLastUpdateUser(containerVO.getLastUpdateUser());
		uLDAtAirportVO.setLastUpdateTime(containerVO.getLastUpdateTime());
		uLDAtAirportVO.setFinalDestination(containerVO.getFinalDestination());
		if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
			uLDAtAirportVO.setUldNumber(constructBulkULDNumber(containerVO.getFinalDestination()));
		} else {
			uLDAtAirportVO.setRemarks(containerVO.getRemarks());
			uLDAtAirportVO.setLocationCode(containerVO.getLocationCode());
			uLDAtAirportVO.setWarehouseCode(containerVO.getWarehouseCode());
		}
		return uLDAtAirportVO;
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	*/
	private void saveOutboundMailsDestnForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		log.debug("MailTransfer" + " : " + "saveMailsDestAssignDtlsForTransfer" + " Entering");
		ULDAtAirport uLDAtAirport = null;
		ULDAtAirportVO uLDAtAirportVO = constructULDAtAirportVO(containerVO);
		ULDAtAirportPK uLDAtAirportPK = constructULDAtAirportPK(uLDAtAirportVO);
		try {
			uLDAtAirport = ULDAtAirport.find(uLDAtAirportPK);
		} catch (FinderException finderException) {
			uLDAtAirport = new ULDAtAirport(uLDAtAirportVO);
		}
		uLDAtAirport.saveDestAssignedMailsForTransfer(mailbagVOs, containerVO);
		log.debug("MailTransfer" + " : " + "saveMailsDestAssignDtlsForTransfer" + " Exiting");
	}

	/** 
	* TODO Purpose Oct 11, 2006, a-1739
	* @param mailbagVOs
	* @param containerVO
	* @return
	* @throws SystemException
	*/
	private Collection<MailbagVO> updateMailbagsForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		log.debug("MailTransfer" + " : " + "updateMailbagDetailsForTransfer" + " Entering");
		Collection<MailbagVO> transferredMails = new Mailbag().updateMailbagsForTransfer(mailbagVOs, containerVO);
		log.debug("MailTransfer" + " : " + "updateMailbagDetailsForTransfer" + " Exiting");
		return transferredMails;
	}

	/** 
	* TODO Purpose Oct 11, 2006, a-1739
	* @param transferredMails
	* @throws SystemException
	*/
	private void flagResditsForMailbagTransfer(Collection<MailbagVO> transferredMails, ContainerVO containerVO) {
		log.debug("MailTransfer" + " : " + "flagResditsForTransfer" + " Entering");
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagResditsForMailbagTransfer(transferredMails, containerVO);
		log.debug("MailTransfer" + " : " + "flagResditsForTransfer" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public void transferMailbags(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO)
			throws InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException {
		NeoMastersServiceUtils neoMastersServiceUtils = ContextUtil.getInstance()
				.getBean(NeoMastersServiceUtils.class);
		MailMRAProxy mailOperationsMRAProxy = ContextUtil.getInstance().getBean(MailMRAProxy.class);
		ReassignController reassignController=ContextUtil.getInstance().getBean(ReassignController.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("MailTransfer" + " : " + "transferMailbags" + " Entering");
		Collection<String> systemParamterCodes = null;
		Map<String, String> systemParamterMap = null;
		Collection<MailbagVO> mailbagsForAcceptance = new ArrayList<>();
		Collection<MailbagVO> mailbagsForTransfer = new ArrayList<>();
		removeTerminatingMailbags(mailbagVOs, containerVO.getAssignedPort());
		log.debug("" + "THE MAILBAGS AFTER REMOVING TERMINATING " + " " + mailbagVOs);
		Collection<MailbagVO> mailbagsToRem = checkArrivedMailbagsInTransfer(mailbagVOs);
		if (mailbagsToRem != null && mailbagsToRem.size() > 0) {
			reassignController.reassignMailFromDestination(mailbagsToRem);
		}
		if (!containerVO.isFoundTransfer() && !containerVO.isFromDeviationList() && !containerVO.isFromCarditList()
				&& !"EXPFLTFIN_ACPMAL".equals(containerVO.getMailSource())) {
			saveMailbagsInboundDtlsForTransfer(mailbagVOs, containerVO);
		}
		if (containerVO.getContainerNumber() != null) {
			if (containerVO.getFlightSequenceNumber() > -1) {
				saveOutboundMailsFlightForTransfer(mailbagVOs, containerVO);
			} else {
				saveOutboundMailsDestnForTransfer(mailbagVOs, containerVO);
			}
			String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
			if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
				for (MailbagVO mailbagVO : mailbagVOs) {
					if (mailbagVO.getTransferFromCarrier() != null) {
						mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
						if (containerVO.getPou() != null && mailbagVO.getPou() == null) {
							mailbagVO.setPou(containerVO.getPou());
						}
						if (mailbagVO.getContainerNumber() == null) {
							mailbagVO.setContainerNumber(containerVO.getContainerNumber());
						}
						mailbagsForAcceptance.add(mailbagVO);
					} else {
						mailbagsForTransfer.add(mailbagVO);
					}
				}
				if (!mailbagsForAcceptance.isEmpty()) {
					MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
					mailAcceptanceVO.setFlightNumber(containerVO.getFlightNumber());
					mailAcceptanceVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
					mailAcceptanceVO.setFlightDate(containerVO.getFlightDate());
					mailAcceptanceVO.setFlightStatus(containerVO.getFlightStatus());
					Collection<ContainerDetailsVO> containerdetailsVOs = new ArrayList<>();
					ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
					containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
					containerDetailsVO.setContainerType(containerVO.getType());
					containerdetailsVOs.add(containerDetailsVO);
					mailAcceptanceVO.setContainerDetails(containerdetailsVOs);
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagsForAcceptance);
				}
				if (!mailbagsForTransfer.isEmpty()) {
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.flagMLDForMailbagTransfer(mailbagsForTransfer, containerVO, null);
				}
			}
		} else {
			String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
			if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				mailController.flagMLDForMailbagTransfer(mailbagVOs, containerVO, null);
			}
		}
		Collection<MailbagVO> transferredMails = updateMailbagsForTransfer(mailbagVOs, containerVO);
		LoginProfile logonVO = contextUtil.callerLoginProfile();
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				if (mailbagVO.getScannedUser() == null) {
					mailbagVO.setScannedUser(logonVO.getUserId());
				}
			}
		}
		MailOperationsMapper mapper = ContextUtil.getInstance().getBean(MailOperationsMapper.class);
		mailController.flagHistoryForTransfer(mapper.copyMailbagVOS(mailbagVOs), mapper.copyContainerVO(containerVO), mailController.getTriggerPoint());
		mailController.flagAuditForTransfer(mailbagVOs, containerVO);
		if (containerVO.isFromDeviationList()) {
			flagArrivalHistoryForDeviationMailbagsTransfer(mailbagVOs, containerVO);
		}
		boolean isFromTruck = false;
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				if ("AA".equals(mailbagVO.getCompanyCode()) && mailbagVO.getIsFromTruck() != null
						&& MailConstantsVO.FLAG_YES.equals(mailbagVO.getIsFromTruck())) {
					isFromTruck = true;
				}
			}
		}
		if (!isFromTruck) {
			flagResditsForMailbagTransfer(mailbagVOs, containerVO);
		}
		boolean provisionalRateImport = false;
		Collection<RateAuditVO> rateAuditVOs = mailController.createRateAuditVOs(containerVO, mailbagVOs,
				MailConstantsVO.MAIL_STATUS_TRANSFER_MAIL, provisionalRateImport);
		log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
		if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
			String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
			if (importEnabled != null && (importEnabled.contains("M") || importEnabled.contains("D"))) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		String provisionalRateimportEnabled = findSystemParameterValue(
				MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
			provisionalRateImport = true;
			Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(containerVO, mailbagVOs,
					MailConstantsVO.MAIL_STATUS_TRANSFERRED, provisionalRateImport);
			if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		if (!(containerVO.getCarrierCode().equals(containerVO.getOwnAirlineCode()))
				&& !validateCarrierCodeFromPartner(containerVO.getCompanyCode(), containerVO.getOwnAirlineCode(),
						containerVO.getAssignedPort(), containerVO.getCarrierCode())) {
			systemParamterCodes = new ArrayList<String>();
			systemParamterCodes.add(MailConstantsVO.NCA_RESDIT_PROC);
			try {
				systemParamterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParamterCodes);
			} catch (BusinessException e) {
				log.error(e.getMessage());
			}
			if (systemParamterMap != null && systemParamterMap.size() > 0
					&& systemParamterMap.get(MailConstantsVO.NCA_RESDIT_PROC).equals(MailConstantsVO.FLAG_YES)) {
				flagCarditsForTransferCarrier(mailbagVOs, containerVO);
			}
		}
		log.debug("MailTransfer" + " : " + "transferMailbags" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	*/
	private void flagArrivalHistoryForDeviationMailbagsTransfer(Collection<MailbagVO> mailbagVOs,
			ContainerVO containerVO) {
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
		containerDetailsVO.setContentId(containerVO.getContentId());
		containerDetailsVO.setContainerType(containerVO.getType());
		containerDetailsVO.setCarrierCode(containerVO.getCarrierCode());
		containerDetailsVO.setCarrierId(containerVO.getCarrierId());
		containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerDetailsVO.setFlightDate(containerVO.getFlightDate());
		containerDetailsVO.setAssignedPort(containerVO.getAssignedPort());
		containerDetailsVO.setPol(containerVO.getPol());
		containerDetailsVO.setPou(containerVO.getPou());
		containerDetailsVO.setAcceptedFlag(containerVO.getAcceptanceFlag());
		containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
		containerDetails.add(containerDetailsVO);
		mailArrivalVO.setContainerDetails(containerDetails);
		mailArrivalVO.setCompanyCode(containerVO.getCompanyCode());
		Collection<MailbagVO> mailbagVosForArrival = new ArrayList<>();
		MailbagVO mailbagVOForArrival = null;
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailbagVOForArrival = mailOperationsMapper.copyMailbagVO(mailbagVO);
			mailbagVOForArrival.setArrivedFlag(MailConstantsVO.FLAG_YES);
			mailbagVOForArrival.setAutoArriveMail(MailConstantsVO.FLAG_YES);
			mailbagVOForArrival.setScannedPort(containerVO.getPou());
			mailbagVosForArrival.add(mailbagVOForArrival);
		}
		containerDetailsVO.setMailDetails(mailbagVosForArrival);
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagMailbagAuditForArrival(mailArrivalVO);
		mailController.flagMailbagHistoryForArrival(mailOperationsMapper.copymailArrivalVO(mailArrivalVO), mailController.getTriggerPoint());
	}

	/** 
	* @author A-1936 added as the Part of NCACR This method is used toconstruct the Key for the HashMap which will be of the Form (CMPCOD-CDTSEQNUM-STNCOD)
	* @param mailBagVo
	* @return
	*/
	private String constructCarditKey(MailbagVO mailBagVo) {
		return new StringBuilder(mailBagVo.getCompanyCode()).append("-").append(mailBagVo.getCarditSequenceNumber())
				.append("-").append(mailBagVo.getStationCode()).toString();
	}

	/** 
	* @author A-1936 Added as the Part of NCA-CR This method is use dto groupthe MailBags Based on the Key (CMPCOD-CDTSEQNUM-STNCOD)
	* @param mailBagVos
	* @return
	*/
	private Map<String, Collection<MailbagVO>> groupMailBagsForCardit(Collection<MailbagVO> mailBagVos) {
		Map<String, Collection<MailbagVO>> carditMap = new HashMap<String, Collection<MailbagVO>>();
		Collection<MailbagVO> newMailbagVos = null;
		String carditMapKey = null;
		for (MailbagVO mailBag : mailBagVos) {
			carditMapKey = constructCarditKey(mailBag);
			newMailbagVos = carditMap.get(carditMapKey);
			if (newMailbagVos == null) {
				newMailbagVos = new ArrayList<MailbagVO>();
				carditMap.put(carditMapKey, newMailbagVos);
			}
			newMailbagVos.add(mailBag);
		}
		return carditMap;
	}

	/** 
	* @author A-1936 ADDED AS THE PART OF THE NCA-CR This method is used toedit the Receipient Message From the Message Standards .. The Recepient Id is overridden to be Transferred Carrier in the Message From the MSGMSGMST as we are sending the Cardit to the Transferred Carrier ..
	* @param originalMessage
	* @return
	*/
	private String createMessageForCardit(String originalMessage, String transferCarrier) {
		int upIndex = originalMessage.indexOf("UP+");
		int endIndex = originalMessage.indexOf(":", upIndex);
		String recepientID = originalMessage.substring(upIndex + 3, endIndex);
		String newMessage = originalMessage.replaceFirst(recepientID, transferCarrier);
		log.debug("" + "THE RECEPIENT ID IS " + " " + recepientID);
		log.debug("" + "THE TRANSFER CARRIER IS " + " " + transferCarrier);
		log.debug("" + "THE  ORIGINAL MESASGE   IS " + " " + originalMessage);
		log.debug("" + "THE  NEW  MESASGE   IS " + " " + newMessage);
		return newMessage;
	}

	/** 
	* @author A-1936 This method is used to Flag the Cardits for the TransferCarrier whenever the Transfer of MailBags takes Place .. Note: Flagging of The Cardits can happen Only if the System Parameter for the Same(SHRSYSPARCOD-'mailtracking.defaults.resdit.ncaspecificcheckneeded') is found to be 'Y'.
	* @param transferredMails
	* @throws SystemException
	*/
	private void flagCarditsForTransferCarrier(Collection<MailbagVO> transferredMails, ContainerVO containerVO) {
		MsgBrokerMessageProxy msgBrokerMessageProxy = ContextUtil.getInstance().getBean(MsgBrokerMessageProxy.class);
		log.debug("MailTranfer" + " : " + "flagCarditsForTransferCarrier" + " Entering");
		Collection<MailbagVO> carditMailBags = null;
		Collection<MessageDespatchDetailsVO> despatchDetailsVos = null;
		Collection<String> pous = null;
		String toStationCode = null;
		MessageDespatchDetailsVO despatchDetailVo = null;
		String[] splitKey = null;
		String companyCode = null;
		int carditSequenceNumber = 0;
		String stationCode = null;
		Map<String, Collection<MailbagVO>> carditMap = null;
		MessageVO messageVo = null;
		MailbagVO carditMailBag = null;
		if (transferredMails != null && transferredMails.size() > 0) {
			carditMailBags = new ArrayList<MailbagVO>();
			for (MailbagVO mailBag : transferredMails) {
				carditMailBag = Cardit.findCarditDetailsForAllMailBags(mailBag.getCompanyCode(),
						Mailbag.findMailSequenceNumber(mailBag.getMailbagId(), mailBag.getCompanyCode()));
				log.debug("" + "THE CARDIT MAIL BAG VO" + " " + carditMailBag);
				if (carditMailBag != null) {
					carditMailBags.add(carditMailBag);
				}
			}
			if (carditMailBags != null && carditMailBags.size() > 0) {
				carditMap = groupMailBagsForCardit(carditMailBags);
				if (carditMap != null && carditMap.size() > 0) {
					MsgBrokerMessageProxy messageBrokerProxy = msgBrokerMessageProxy;
					for (String key : carditMap.keySet()) {
						splitKey = key.split("-");
						companyCode = splitKey[0];
						carditSequenceNumber = Integer.parseInt(splitKey[1]);
						stationCode = splitKey[2];
						messageVo = messageBrokerProxy.findMessageDetails(companyCode, stationCode,
								MailConstantsVO.MESSAGETYPE_CARDIT, carditSequenceNumber);
						if (messageVo != null) {
							messageVo.setRawMessage(
									createMessageForCardit(messageVo.getRawMessage(), containerVO.getCarrierCode()));
							despatchDetailsVos = new ArrayList<MessageDespatchDetailsVO>();
							despatchDetailVo = new MessageDespatchDetailsVO();
							despatchDetailVo.setPartyType(MailConstantsVO.MSG_PARTYTYPE_ARL);
							despatchDetailVo.setParty(containerVO.getCarrierCode());
							despatchDetailsVos.add(despatchDetailVo);
							messageVo.setDespatchDetails(despatchDetailsVos);
							if (containerVO.getFlightSequenceNumber() > 0) {
								toStationCode = containerVO.getPou();
							} else {
								toStationCode = containerVO.getFinalDestination();
							}
							log.debug("" + "<<<<<<THE  TOSTATIONCODE IS>>>> " + " " + toStationCode);
							if (toStationCode != null) {
								if (messageVo.getPous() != null && messageVo.getPous().size() > 0) {
									messageVo.getPous().add(toStationCode);
								} else {
									pous = new ArrayList<String>();
									pous.add(toStationCode);
									messageVo.setPous(pous);
								}
								log.debug("" + "The MessageVo is " + " " + messageVo);
								log.debug("" + "The MessageVo is " + " " + messageVo.getPous().size());
							}
							messageBrokerProxy.sendMessageText(messageVo);
						}
					}
				}
			}
		}
	}

	/** 
	* @author A-1936 This methos is used to validate whether the CarrierCode ofthe Accepted Mail is same as any of the Partners. If same ,return true else false.
	* @param companyCode
	* @param ownCarrierCode
	* @param airportCode
	* @param carrierCode
	* @return
	* @throws SystemException
	*/
	private boolean validateCarrierCodeFromPartner(String companyCode, String ownCarrierCode, String airportCode,
			String carrierCode) {
		log.debug("MailAcceptance" + " : " + "ValidateCarrierCodeFromPartner" + " Entering");
		boolean isValid = false;

		Collection<PartnerCarrierVO> partnerCarrierVos = ContextUtil.getInstance().getBean(MailController.class)
				.findAllPartnerCarriers(companyCode,
				ownCarrierCode, airportCode);

		if (partnerCarrierVos != null && partnerCarrierVos.size() > 0) {
			for (PartnerCarrierVO partnerCarrierVO : partnerCarrierVos) {
				log.debug("" + "The Carrier Code is " + " " + carrierCode);
				log.debug("" + "The  Partner Carrier Code is " + " " + partnerCarrierVO.getPartnerCarrierCode());
				if (carrierCode.equals(partnerCarrierVO.getPartnerCarrierCode())) {
					isValid = true;
					break;
				}
			}
		}
		log.debug("" + "<<<<Can HandedOverResditFlagged>>>>" + " " + isValid);
		return isValid;
	}

	/** 
	* @author a-1936 This method is used to construct the Container DetailsContaining the DSNVs that is actually Required for the Generation of the Transfer Manifest Report ...
	* @param uldForSegmentVos
	* @return
	* @throws SystemException 
	*/
	private Collection<ContainerDetailsVO> createContainerDtlsForTransfermanifest(
			Collection<ULDForSegmentVO> uldForSegmentVos, Collection<MailbagVO> mailbagVOs) {
		log.debug("MailTransfer" + " : " + "createContainerDtlsForTransfermanifest" + " Entering");
		Collection<ContainerDetailsVO> containerDetailsColl = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetails = null;
		if (uldForSegmentVos != null & uldForSegmentVos.size() > 0) {
			for (ULDForSegmentVO uldForSegmentVO : uldForSegmentVos) {
				Collection<DSNVO> dsnVOs = new ArrayList<>();
				DSNVO dsnVo = null;
				containerDetails = new ContainerDetailsVO();
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for (MailbagVO mailbagVO : mailbagVOs) {
						if (uldForSegmentVO.getUldNumber().equals(mailbagVO.getUldNumber())) {
							dsnVo = new DSNVO();
							dsnVo.setCompanyCode(mailbagVO.getCompanyCode());
							dsnVo.setOriginExchangeOffice(mailbagVO.getOoe());
							dsnVo.setDestinationExchangeOffice(mailbagVO.getDoe());
							dsnVo.setMailCategoryCode(mailbagVO.getMailCategoryCode());
							dsnVo.setBags(1);
							dsnVo.setWeight(mailbagVO.getWeight());
							dsnVo.setYear(mailbagVO.getYear());
							dsnVo.setDsn(mailbagVO.getDespatchSerialNumber());
							dsnVo.setMailSubclass(mailbagVO.getMailSubclass());
							dsnVo.setMailClass(mailbagVO.getMailClass());
							dsnVo.setContainerNumber(mailbagVO.getUldNumber());
							dsnVo.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
							dsnVo.setMailbagId(mailbagVO.getMailbagId());
							dsnVo.setScannedUser(mailbagVO.getScannedUser());
							dsnVOs.add(dsnVo);
						}
					}
				}
				containerDetails.setDsnVOs(dsnVOs);
				containerDetails.setCompanyCode(uldForSegmentVO.getCompanyCode());
				containerDetails.setContainerNumber(uldForSegmentVO.getUldNumber());
				containerDetailsColl.add(containerDetails);
			}
		}
		log.debug("" + "THE CONTAINER DETAILS CONSTRUCTED" + " " + containerDetails);
		return containerDetailsColl;
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
			return sysparValue;
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/** 
	* TODO Purpose Feb 6, 2007, A-1739
	* @param transferredMails
	* @param containerVOs
	* @param operationalFlightVO
	* @throws SystemException
	*/
	private void flagResditsForContainerTransfer(Collection<MailbagVO> transferredMails,
			Collection<ContainerVO> containerVOs, OperationalFlightVO operationalFlightVO) {
		log.debug("MailTransfer" + " : " + "flagResditsForContainerTransfer" + " Entering");
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagResditsForContainerTransfer(transferredMails, containerVOs, operationalFlightVO);
		log.debug("MailTransfer" + " : " + "flagResditsForContainerTransfer" + " Exiting");
	}

	/** 
	* @param containerVO
	* @return
	* @throws SystemException
	*/
	private Container findContainer(ContainerVO containerVO) throws FinderException {
		log.debug("MailTransfer" + " : " + "findContainer" + " Entering");
		Container container = null;
		ContainerPK containerPK = new ContainerPK();
		containerPK.setAssignmentPort(containerVO.getAssignedPort());
		containerPK.setCarrierId(containerVO.getCarrierId());
		containerPK.setCompanyCode(containerVO.getCompanyCode());
		containerPK.setContainerNumber(containerVO.getContainerNumber());
		containerPK.setFlightNumber(containerVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(containerVO.getLegSerialNumber());
		container = Container.find(containerPK);
		log.debug("MailTransfer" + " : " + "findContainer" + " Exiting");
		return container;
	}

	/** 
	* @param container
	* @param containerAuditVO
	* @param operationalFlightVO
	* @throws SystemException 
	*/
	//TODO: Neo to correct audit
	/*
	private void collectContainerAuditDetails(Container container, ContainerAuditVO containerAuditVO,
			OperationalFlightVO operationalFlightVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("MailTransfer" + " : " + "collectContainerAuditDetails" + " Entering");
		String triggeringPoint = ContextUtil.getInstance().getTxContext(ContextUtil.TRIGGER_POINT);
		StringBuffer additionalInfo = new StringBuffer();
		containerAuditVO.setCompanyCode(container.getContainerPK().getCompanyCode());
		containerAuditVO.setContainerNumber(container.getContainerPK().getContainerNumber());
		containerAuditVO.setAssignedPort(container.getContainerPK().getAssignmentPort());
		containerAuditVO.setCarrierId(container.getContainerPK().getCarrierId());
		containerAuditVO.setFlightNumber(container.getContainerPK().getFlightNumber());
		containerAuditVO.setFlightSequenceNumber(container.getContainerPK().getFlightSequenceNumber());
		containerAuditVO.setLegSerialNumber(container.getContainerPK().getLegSerialNumber());
		containerAuditVO.setActionCode(MailConstantsVO.CONTAINER_TRANSFER);
		containerAuditVO.setAuditRemarks(MailConstantsVO.MAIL_ULD_TRANSF);
		containerAuditVO.setUserId(container.getLastUpdateUser());
		additionalInfo.append("Transferred to ");
		if (!"-1".equals(operationalFlightVO.getFlightNumber())) {
			additionalInfo.append(operationalFlightVO.getCarrierCode()).append(" ")
					.append(operationalFlightVO.getFlightNumber()).append(", ");
		} else {
			additionalInfo.append(operationalFlightVO.getCarrierCode()).append(", ");
		}
		additionalInfo.append(localDateUtil.getLocalDate(operationalFlightVO.getPol(), true)
				.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))).append(", ");
		if (!"-1".equals(operationalFlightVO.getFlightNumber())) {
			additionalInfo.append(operationalFlightVO.getPol()).append(" - ").append(operationalFlightVO.getPou())
					.append(" ");
		}
		additionalInfo.append("in ").append(operationalFlightVO.getPol());
		containerAuditVO.setAdditionalInformation(additionalInfo.toString());
		containerAuditVO.setTriggerPnt(triggeringPoint);
		log.debug("MailTransfer" + " : " + "collectContainerAuditDetails" + " Exiting");
	}
*/
	/** 
	* TODO Purpose Oct 10, 2006, a-1739
	* @param containerVO
	* @param operationalFlightVO
	* @param toFlightSegSerialNum
	*/
	private void modifyContainerVO(ContainerVO containerVO, OperationalFlightVO operationalFlightVO,
			int toFlightSegSerialNum) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		LoginProfile logon = null;
		;
		try {
			logon = contextUtil.callerLoginProfile();
		} finally {
		}
		containerVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		containerVO.setFlightDate(operationalFlightVO.getFlightDate());
		containerVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		containerVO.setCarrierId(operationalFlightVO.getCarrierId());
		containerVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		containerVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		containerVO.setSegmentSerialNumber(toFlightSegSerialNum);
		containerVO.setAssignedPort(operationalFlightVO.getPol());
		containerVO.setAssignedDate(localDateUtil.getLocalDate(operationalFlightVO.getPol(), true));
		containerVO.setPou(operationalFlightVO.getPou());
		containerVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
		containerVO.setOffloadFlag(MailConstantsVO.FLAG_NO);
		containerVO.setArrivedStatus(MailConstantsVO.FLAG_NO);
		containerVO.setAssignedUser(operationalFlightVO.getOperator());
		containerVO.setMailbagPresent(true);
		if (logon.getOwnAirlineIdentifier() != containerVO.getCarrierId()
				&& MailConstantsVO.DESTN_FLT_STR.equals(containerVO.getFlightNumber())) {
			containerVO.setTransitFlag(MailConstantsVO.FLAG_NO);
		}
	}

	/** 
	* TODO Purpose Oct 10, 2006, a-1739
	* @param containerVOs
	* @param operationalFlightVO
	* @param toFlightSegSerialNum
	* @throws SystemException
	*/
	private Collection<ContainerVO> updateTransferredContainers(Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO, int toFlightSegSerialNum) throws ULDDefaultsProxyException {
		log.debug("MailTransfer" + " : " + "updateTransferredContainers" + " Entering");
		var operationsFltHandlingProxy = ContextUtil.getInstance().getBean(OperationsFltHandlingProxy.class);
		var uLDDefaultsProxy = ContextUtil.getInstance().getBean(ULDDefaultsProxy.class);
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		Collection<ContainerVO> transferredCons = new ArrayList<ContainerVO>();
		ULDInFlightVO uldInFlightVO = null;
		Collection<ULDInFlightVO> uldInFlightVOs = null;
		FlightDetailsVO flightDetailsVO = null;
		boolean isUld = false;
		boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
		if (isUldIntegrationEnbled) {
			uldInFlightVOs = new ArrayList<ULDInFlightVO>();
			flightDetailsVO = new FlightDetailsVO();
			flightDetailsVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		}
		boolean isOprUldEnabled = MailConstantsVO.FLAG_YES
				.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD));
		Collection<UldInFlightVO> operationalULDs = new ArrayList<UldInFlightVO>();
		for (ContainerVO containerVO : containerVOs) {
			if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
				isUld = true;
			}
			Container container = null;
			try {
				container = findContainer(containerVO);
			} catch (FinderException exception) {
				container = new Container(containerVO);
				if (!"B".equals(containerVO.getType())) {
					mailController.flagMLDForMailOperationsInULD(containerVO, MailConstantsVO.MLD_STG);
				}
			}
			container.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
			container.setArrivedStatus(MailConstantsVO.FLAG_YES);
			container.setTransferFlag(MailConstantsVO.FLAG_YES);
			container.setFinalDestination(containerVO.getFinalDestination());

			ContainerVO toContainerVO = new ContainerVO();
			toContainerVO = mailOperationsMapper.copyContainerVO(containerVO);
			modifyContainerVO(toContainerVO, operationalFlightVO, toFlightSegSerialNum);
			transferredCons.add(toContainerVO);
			Container toContainer = null;
			try {
				toContainer = findContainer(toContainerVO);
			} catch (FinderException exception) {
				new Container(toContainerVO);
				if (!"B".equals(toContainerVO.getType())) {
					mailController.flagMLDForMailOperationsInULD(toContainerVO, MailConstantsVO.MLD_STG);
				}
			}
			if (toContainer != null) {
				toContainer.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
				if (MailConstantsVO.FLAG_NO.equals(toContainerVO.getTransitFlag())) {
					toContainer.setTransitFlag(toContainerVO.getTransitFlag());
				}
			}

			auditContainerUpdates(
					containerVO,
					MailConstantsVO.OPERATION_FLAG_UPDATE,
					toContainerVO.getTransactionCode(),
					"Container update",
					toContainerVO.getTriggerPoint()
			);

			if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
				if (isUldIntegrationEnbled && ContainerVO.OPERATION_FLAG_INSERT.equals(containerVO.getOperationFlag())) {
					uldInFlightVO = new ULDInFlightVO();
					uldInFlightVO.setUldNumber(containerVO.getContainerNumber());
					uldInFlightVO.setPointOfLading(containerVO.getAssignedPort());
					uldInFlightVO.setPointOfUnLading(containerVO.getPou());
					uldInFlightVO.setRemark(MailConstantsVO.MAIL_ULD_TRANSF);
					uldInFlightVOs.add(uldInFlightVO);
					flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
					flightDetailsVO.setFlightCarrierIdentifier(operationalFlightVO.getCarrierId());
					flightDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
					flightDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
					flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(operationalFlightVO.getFlightDate()));
					flightDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
					flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);
					flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
					flightDetailsVO.setRemark(MailConstantsVO.MAIL_ULD_TRANSF);
					uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
				}
			}
			if (isUldIntegrationEnbled) {
				if (uldInFlightVOs.size() > 0) {
				}
			}
		}
		if (isOprUldEnabled && isUld && operationalULDs != null && operationalULDs.size() > 0) {
			operationsFltHandlingProxy.saveOperationalULDsInFlight(operationalULDs);
		}
		log.debug("MailTransfer" + " : " + "updateTransferredContainers" + " Exiting");
		return transferredCons;
	}

	public void auditContainerUpdates(ContainerVO containerVO, String action, String transaction, String additionalInfo, String triggerPoint) {
		var auditUtils = ContextUtil.getInstance().getBean(AuditUtils.class);
		var auditConfigurationBuilder = new AuditConfigurationBuilder();
		auditUtils.performAudit(auditConfigurationBuilder
				.withBusinessObject(containerVO)
				.withTriggerPoint(triggerPoint)
				.withActionCode(action)
				.withEventName("containerUpdate")
				.withAdditionalInfo(additionalInfo)
				.withtransaction(transaction).build());
	}

	/** 
	* @param uldForSegmentVO
	* @param containerVO
	* @param operationalFlightVO
	* @return
	*/
	private ULDAtAirportVO constructULDAtAirportVO(ULDForSegmentVO uldForSegmentVO, ContainerVO containerVO,
			OperationalFlightVO operationalFlightVO) {
		ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
		uldAtAirportVO.setCompanyCode(uldForSegmentVO.getCompanyCode());
		uldAtAirportVO.setUldNumber(uldForSegmentVO.getUldNumber());
		uldAtAirportVO.setAirportCode(operationalFlightVO.getPol());
		uldAtAirportVO.setCarrierId(operationalFlightVO.getCarrierId());
		uldAtAirportVO.setNumberOfBags(uldForSegmentVO.getNoOfBags());
		uldAtAirportVO.setFinalDestination(containerVO.getFinalDestination());
		uldAtAirportVO.setTotalWeight(uldForSegmentVO.getTotalWeight());
		uldAtAirportVO.setRemarks(containerVO.getRemarks());
		uldAtAirportVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		uldAtAirportVO.setTransferFromCarrier(uldForSegmentVO.getTransferFromCarrier());
		return uldAtAirportVO;
	}

	/** 
	* @param uLDAtAirportVO
	* @return
	*/
	private ULDAtAirportPK constructULDAtAirportPK(ULDAtAirportVO uLDAtAirportVO) {
		ULDAtAirportPK uLDAtAirportPK = new ULDAtAirportPK();
		uLDAtAirportPK.setAirportCode(uLDAtAirportVO.getAirportCode());
		uLDAtAirportPK.setCarrierId(uLDAtAirportVO.getCarrierId());
		uLDAtAirportPK.setCompanyCode(uLDAtAirportVO.getCompanyCode());
		uLDAtAirportPK.setUldNumber(uLDAtAirportVO.getUldNumber());
		return uLDAtAirportPK;
	}

	/** 
	* @param containerVOs
	* @param uLDForSegmentVOs
	* @param operationalFlightVO
	* @throws SystemException
	*/
	private void saveDestAssignednDetailsForTransfer(Collection<ContainerVO> containerVOs,
			Collection<ULDForSegmentVO> uLDForSegmentVOs, OperationalFlightVO operationalFlightVO) {
		log.debug("MailTransfer" + " : " + "saveDestAssignednDetailsForTransfer" + " Entering");
		ULDAtAirport uLDAtAirport = null;
		for (ULDForSegmentVO uLDForSegmentVO : uLDForSegmentVOs) {
			for (ContainerVO containerVO : containerVOs) {
				if (uLDForSegmentVO.getUldNumber().equals(containerVO.getContainerNumber())) {
					ULDAtAirportVO uLDAtAirportVO = constructULDAtAirportVO(uLDForSegmentVO, containerVO,
							operationalFlightVO);
					ULDAtAirportPK uLDAtAirportPK = constructULDAtAirportPK(uLDAtAirportVO);
					try {
						uLDAtAirport = ULDAtAirport.find(uLDAtAirportPK);
						uLDAtAirport.setTransferFromCarrier(containerVO.getCarrierCode());
					} catch (FinderException finderException) {
						uLDAtAirport = new ULDAtAirport(uLDAtAirportVO);
					}
					uLDAtAirport.saveDestAssignedDetailsForTransfer(uLDAtAirportVO, uLDForSegmentVO.getMailbagInULDForSegmentVOs());
				}
			}
		}

		log.debug("MailTransfer" + " : " + "saveDestAssignednDetailsForTransfer" + " Exiting");
	}

	/**
	* @param operationalFlightVO
	* @param toFlightSegSerNum TODO
	* @return
	* @throws SystemException
	*/
	private Collection<MailbagVO> updateMailBagInULD(Map<MailbagPK, MailbagVO> mailMap,
			OperationalFlightVO operationalFlightVO, int toFlightSegSerNum, Collection<ContainerVO> containerVOs) {
		Collection<MailbagVO> transferredMails = new ArrayList<MailbagVO>();
		try {
			for (MailbagPK mailbagPK : mailMap.keySet()) {
				Mailbag mailbag = Mailbag.find(mailbagPK);
				if ("M".equals(mailbag.getMailType())
						&& !(MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbag.getLatestStatus()))) {
					MailbagVO mailbagVO = updateMailbagsForTransfer(operationalFlightVO, toFlightSegSerNum,
							containerVOs, mailbag);
					if (mailbagVO != null) {
						transferredMails.add(mailbagVO);
					}
				}
				if (containerVOs.size() > 0) {
					for (ContainerVO containerVo : containerVOs) {
						if (containerVo.isUldTobarrow()) {
							if (containerVo.getContainerNumber().equals(mailbag.getUldNumber())) {
								mailbag.setContainerType("B");
							}
						}
					}
				}
			}
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode());
		}
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		MailOperationsMapper mapper = ContextUtil.getInstance().getBean(MailOperationsMapper.class);
		mailController.flagAuditForContainerTransfer(operationalFlightVO, toFlightSegSerNum, transferredMails);
		mailController.flagHistoryForContainerTransfer(operationalFlightVO, toFlightSegSerNum, mapper.copyMailbagVOS(transferredMails), mailController.getTriggerPoint());
		return transferredMails;
	}

	/** 
	* Inbound containers
	* @param containerVOs
	* @param operationalFlightVO
	* @return Collection<ULDForSegmentVO>
	* @throws SystemException
	* @throws ULDDefaultsProxyException
	*/
	private Collection<ULDForSegmentVO> saveArrivalDetailsForTransfer(Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO, Map<MailbagPK, MailbagVO> mailMap)
			throws ULDDefaultsProxyException {
		log.debug("MailTransfer" + " : " + "saveArrivalDetailsForTransfer" + " Entering");
		Collection<ULDForSegmentVO> allULDForSegmentVOs = new ArrayList<ULDForSegmentVO>();
		Map<AssignedFlightPK, Collection<ContainerVO>> flightContainersMap = constructFlightContainerMap(containerVOs,
				operationalFlightVO.getPol());
		boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
		for (AssignedFlightPK inboundFlightPK : flightContainersMap.keySet()) {
			AssignedFlight inboundFlight = findOrCreateInboundFlight(inboundFlightPK);
			Collection<ULDForSegmentVO> uLDForSegmentVOs = inboundFlight.saveArrivalDetailsForTransfer(
					flightContainersMap.get(inboundFlightPK), operationalFlightVO, mailMap);
			if (isUldIntegrationEnbled) {
				updateULDForOperations(flightContainersMap.get(inboundFlightPK), operationalFlightVO, true);
			}
			allULDForSegmentVOs.addAll(uLDForSegmentVOs);
		}
		log.debug("MailTransfer" + " : " + "saveArrivalDetailsForTransfer" + " Exiting");
		return allULDForSegmentVOs;
	}

	/** 
	* @param containerVOs
	* @param operationalFlightVO
	* @param isImport
	* @throws SystemException
	* @throws ULDDefaultsProxyException
	*/
	private void updateULDForOperations(Collection<ContainerVO> containerVOs, OperationalFlightVO operationalFlightVO,
			boolean isImport) throws ULDDefaultsProxyException {
		ULDDefaultsProxy uLDDefaultsProxy = ContextUtil.getInstance().getBean(ULDDefaultsProxy.class);
		log.debug("MailTransfer" + " : " + "updateULDForOperations" + " Entering");
		if (containerVOs != null && containerVOs.size() > 0) {
			ULDInFlightVO uldInFlightVO = null;
			Collection<ULDInFlightVO> uldInFlightVOs = null;
			FlightDetailsVO flightDetailsVO = null;
			uldInFlightVOs = new ArrayList<ULDInFlightVO>();
			flightDetailsVO = new FlightDetailsVO();
			flightDetailsVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			flightDetailsVO.setFlightCarrierIdentifier(operationalFlightVO.getCarrierId());
			flightDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
			flightDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			if (operationalFlightVO.getFlightDate() != null) {

				flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(operationalFlightVO.getFlightDate()));
			} else {

				flightDetailsVO.setFlightDate(new com.ibsplc.icargo.framework.util.time.LocalDate("***"
						, Location.NONE, true));
			}
			flightDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
			flightDetailsVO.setDirection(isImport ? MailConstantsVO.IMPORT : MailConstantsVO.EXPORT);
			for (ContainerVO containerVO : containerVOs) {
				uldInFlightVO = new ULDInFlightVO();
				uldInFlightVO.setUldNumber(containerVO.getContainerNumber());
				uldInFlightVO.setPointOfLading(containerVO.getAssignedPort());
				uldInFlightVO.setPointOfUnLading(containerVO.getPou());
				uldInFlightVO
						.setRemark(isImport ? MailConstantsVO.MAIL_ULD_ARRIVED : MailConstantsVO.MAIL_ULD_ASSIGNED);
				uldInFlightVOs.add(uldInFlightVO);
			}
			flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
			flightDetailsVO.setRemark(isImport ? MailConstantsVO.MAIL_ULD_ARRIVED : MailConstantsVO.MAIL_ULD_ASSIGNED);
			flightDetailsVO.setAction(isImport ? FlightDetailsVO.ARRIVAL : FlightDetailsVO.ACCEPTANCE);
			uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
		}
		log.debug("MailTransfer" + " : " + "updateULDForOperations" + " Exiting");
	}

	/** 
	* @param inboundFlightPK
	* @return
	* @throws SystemException
	*/
	private AssignedFlight findOrCreateInboundFlight(AssignedFlightPK inboundFlightPK) {
		log.debug("MailTransfer" + " : " + "findOrCreateInboundFlight" + " Entering");
		AssignedFlight inboundFlight = null;
		try {
			inboundFlight = AssignedFlight.find(inboundFlightPK);
		} catch (FinderException exception) {
			AssignedFlightVO inboundFlightVO = validateOperationalFlight(inboundFlightPK);
			if(Objects.nonNull(inboundFlightVO)) {
				inboundFlight = new AssignedFlight(inboundFlightVO);
			}
		}
		log.debug("MailTransfer" + " : " + "findOrCreateInboundFlight" + " Exiting");
		return inboundFlight;
	}

	/** 
	* This a FlightProductProxyCall .This method is used to validate the FlightForAirport
	* @param flightFilterVO
	* @return
	* @throws SystemException
	*/
	public Collection<FlightValidationVO> validateFlight(FlightFilterVO flightFilterVO) {
		FlightOperationsProxy flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		log.debug("MailTransfer" + " : " + "validateFlight" + " Entering");
		return flightOperationsProxy.validateFlightForAirport(flightFilterVO);
	}

	/** 
	* @param inboundFlightPK
	* @return
	* @throws SystemException
	*/
	private AssignedFlightVO validateOperationalFlight(AssignedFlightPK inboundFlightPK) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("MailTransfer" + " : " + "validateOperationalFlight" + " Entering");
		AssignedFlightVO inboundFlightVO = null;
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(inboundFlightPK.getCompanyCode());
		flightFilterVO.setFlightCarrierId(inboundFlightPK.getCarrierId());
		flightFilterVO.setFlightNumber(inboundFlightPK.getFlightNumber());
		Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
		FlightValidationVO validVO = null;
		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				if (flightValidationVO.getFlightSequenceNumber() == inboundFlightPK.getFlightSequenceNumber()) {
					validVO = flightValidationVO;
					break;
				}
			}
		}
		if (validVO != null) {
			inboundFlightVO = new AssignedFlightVO();
			inboundFlightVO.setCompanyCode(inboundFlightPK.getCompanyCode());
			inboundFlightVO.setCarrierId(inboundFlightPK.getCarrierId());
			inboundFlightVO.setFlightNumber(inboundFlightPK.getFlightNumber());
			inboundFlightVO.setFlightSequenceNumber(inboundFlightPK.getFlightSequenceNumber());
			inboundFlightVO.setLegSerialNumber(inboundFlightPK.getLegSerialNumber());
			inboundFlightVO.setAirportCode(inboundFlightPK.getAirportCode());
			inboundFlightVO.setCarrierCode(validVO.getCarrierCode());
			inboundFlightVO
					.setFlightDate(LocalDateMapper.toZonedDateTime(validVO.getApplicableDateAtRequestedAirport()));
			inboundFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
			inboundFlightVO.setImportFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
			LoginProfile logonVO = contextUtil.callerLoginProfile();
			inboundFlightVO.setLastUpdateTime(localDateUtil.getLocalDate(logonVO.getAirportCode(), true));
			inboundFlightVO.setLastUpdateUser(logonVO.getUserId());
		}
		log.debug("MailTransfer" + " : " + "validateOperationalFlight" + " Exiting");
		return inboundFlightVO;
	}

	/** 
	* This method checks whether ULD integration Enabled or not
	* @return
	* @throws SystemException
	*/
	private boolean isULDIntegrationEnabled() {
		NeoMastersServiceUtils neoMastersServiceUtils = ContextUtil.getInstance()
				.getBean(NeoMastersServiceUtils.class);
		boolean isULDIntegrationEnabled = false;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.ULD_INTEGRATION_ENABLED);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			return isULDIntegrationEnabled;
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null
				&& ContainerVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.ULD_INTEGRATION_ENABLED))) {
			isULDIntegrationEnabled = true;
		}
		log.debug("" + " isULDIntegrationEnabled :" + " " + isULDIntegrationEnabled);
		return isULDIntegrationEnabled;
	}

	/** 
	* A-1883
	* @param containerVO
	* @param airportCode
	* @return InboundFlightPK
	*/
	private AssignedFlightPK createInboundFlightPK(ContainerVO containerVO, String airportCode) {
		AssignedFlightPK assignedFlightPK = new AssignedFlightPK();
		assignedFlightPK.setCompanyCode(containerVO.getCompanyCode());
		assignedFlightPK.setCarrierId(containerVO.getCarrierId());
		assignedFlightPK.setFlightNumber(containerVO.getFlightNumber());
		assignedFlightPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		assignedFlightPK.setLegSerialNumber(containerVO.getLegSerialNumber());
		assignedFlightPK.setAirportCode(airportCode);
		return assignedFlightPK;
	}

	/** 
	* This method groups containers based on the flight
	* @param containerVOs
	* @param airportCode
	* @return Map<InboundFlightPK,Collection<ContainerVO>>
	* @throws SystemException
	*/
	private Map<AssignedFlightPK, Collection<ContainerVO>> constructFlightContainerMap(
			Collection<ContainerVO> containerVOs, String airportCode) {
		log.debug("MailTransfer" + " : " + "constructFlightContainerMap" + " Entering");
		Map<AssignedFlightPK, Collection<ContainerVO>> flightContainersMap = new HashMap<AssignedFlightPK, Collection<ContainerVO>>();
		Collection<ContainerVO> containers = null;
		for (ContainerVO containerVO : containerVOs) {
			AssignedFlightPK assignedFlightPK = createInboundFlightPK(containerVO, airportCode);
			containers = flightContainersMap.get(assignedFlightPK);
			if (containers == null) {
				containers = new ArrayList<ContainerVO>();
				flightContainersMap.put(assignedFlightPK, containers);
			}
			containers.add(containerVO);
		}
		log.debug("MailTransfer" + " : " + "constructFlightContainerMap" + " Exiting");
		return flightContainersMap;
	}

	/** 
	* TODO Purpose Oct 10, 2006, a-1739
	* @param operationalFlightVO
	* @param toFlightSegSerNum TODO
	* @return
	* @throws SystemException
	*/
	public MailbagVO updateMailbagsForTransfer(OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
			Collection<ContainerVO> containerVOs, Mailbag mailbag) {
		log.debug("DSN" + " : " + "updateMailbagsForTransfer" + " Entering");
		MailbagVO mailbagVOToRet = null;
		for (ContainerVO containerVO : containerVOs) {
			if (containerVO != null && containerVO.getContainerNumber() != null
					&& containerVO.getContainerNumber().equals(mailbag.getUldNumber())) {
				mailbag.updateULDTransferDetails(operationalFlightVO, toFlightSegSerNum);
				if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbag.getLatestStatus())
						|| MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbag.getLatestStatus())) {
					mailbagVOToRet = mailbag.retrieveVO();
					if (mailbagVOToRet.getUldNumber() == null) {
						mailbagVOToRet.setUldNumber(containerVO.getContainerNumber());
					}
				}
				if (mailbagVOToRet != null) {
					mailbagVOToRet.setCarrierCode(operationalFlightVO.getCarrierCode());
					if (containerVO.getMailSource() != null && mailbagVOToRet.getMailSource() == null) {
						mailbagVOToRet.setMailSource(containerVO.getMailSource());
					}
					if (MailConstantsVO.FLAG_YES.equals(containerVO.getPaBuiltFlag())) {
						mailbagVOToRet.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
					}
				}
			}
		}
		log.debug("DSN" + " : " + "updateMailbagsForTransfer" + " Exiting");
		return mailbagVOToRet;
	}

	/** 
	* For Outbound containers
	* @param containerVOs
	* @param operationalFlightVO
	* @param uLDForSegmentVOs
	* @return
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	* @throws CapacityBookingProxyException
	*/
	private int saveOutboundDetailsForTransfer(Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO, Collection<ULDForSegmentVO> uLDForSegmentVOs)
			throws InvalidFlightSegmentException, CapacityBookingProxyException {
		log.debug("MailTransfer" + " : " + "saveOutboundFlightForTransfer" + " Entering");
		validateAndCreateAssignedFlight(operationalFlightVO);
		AssignedFlightSegment assignedFlightSegment = findOrCreateAssignedFlightSegment(operationalFlightVO);
		ZonedDateTime GHTtime = new ULDForSegment().findGHTForMailbags(operationalFlightVO);
		containerVOs.forEach(containerVO -> containerVO.setGHTtime(GHTtime));
		assignedFlightSegment.saveOutboundDetailsForTransfer(containerVOs, uLDForSegmentVOs);
		log.debug("MailTransfer" + " : " + "saveOutboundFlightForTransfer" + " Exiting");
		return assignedFlightSegment.getSegmentSerialNumber();
	}

	/** 
	* @author a-1936 This method is used to validateflight for closed statusand if not exists create
	* @param operationalFlightVO
	* @throws SystemException
	* @return AssignedFlight
	* @throws ContainerAssignmentException
	*/
	private AssignedFlight validateAndCreateAssignedFlight(OperationalFlightVO operationalFlightVO) {
		log.debug("MailTransfer" + " : " + "validateAndCreateAssignedFlight" + " Entering");
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPk = constructAssignedFlightPK(operationalFlightVO);
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.info("FINDER EXCEPTION IS THROWN");
			assignedFlight = createAssignedFlight(operationalFlightVO);
		}
		log.debug("MailTransfer" + " : " + "validateAndCreateAssignedFlight" + " Exiting");
		return assignedFlight;
	}

	/** 
	* find the segment serial number of the segment to which this container is assigned A-1739
	* @param companyCode
	* @param carrierId
	* @param flightNumber
	* @param flightSequenceNumber
	* @param pol
	* @param pou
	* @return the segmentserialnumber for the pol-pou of the flight
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	*/
	private int findFlightSegment(String companyCode, int carrierId, String flightNumber, long flightSequenceNumber,
			String pol, String pou) throws InvalidFlightSegmentException {
		FlightOperationsProxy flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		log.debug("MailTransfer" + " : " + "findFlightSegment" + " Entering");
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		flightSegments = flightOperationsProxy.findFlightSegments(companyCode, carrierId, flightNumber,
				flightSequenceNumber);
		String operationFlightSegment = new StringBuilder().append(pol).append(pou).toString();
		String flightSegment = null;
		int segSerialNum = 0;
		for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
			flightSegment = new StringBuilder().append(segmentSummaryVO.getSegmentOrigin())
					.append(segmentSummaryVO.getSegmentDestination()).toString();
			log.debug("" + "from proxy -- >" + " " + flightSegment);
			log.debug("" + "from container  -- >" + " " + operationFlightSegment);
			if (flightSegment.equals(operationFlightSegment)) {
				segSerialNum = segmentSummaryVO.getSegmentSerialNumber();
			}
		}
		if (segSerialNum == 0) {
			throw new InvalidFlightSegmentException(new Object[] { operationFlightSegment });
		}
		log.debug("MailTransferd" + " : " + "findFlightSegment" + " Exiting");
		return segSerialNum;
	}

	/** 
	* @author a-1883
	* @param operationalFlightVO
	* @return AssignedFlightSegment
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	*/
	private AssignedFlightSegment findOrCreateAssignedFlightSegment(OperationalFlightVO operationalFlightVO)
			throws InvalidFlightSegmentException {
		log.debug("MailTransfer" + " : " + "findOrCreateAssignedFlightSegment" + " Entering");
		AssignedFlightSegment assignedFlightSegment = null;
		int segmentSerialNumber = findFlightSegment(operationalFlightVO.getCompanyCode(),
				operationalFlightVO.getCarrierId(), operationalFlightVO.getFlightNumber(),
				operationalFlightVO.getFlightSequenceNumber(), operationalFlightVO.getPol(),
				operationalFlightVO.getPou());
		AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
		assignedFlightSegmentPK.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightSegmentPK.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightSegmentPK.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightSegmentPK.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightSegmentPK.setSegmentSerialNumber(segmentSerialNumber);
		try {
			assignedFlightSegment = AssignedFlightSegment.find(assignedFlightSegmentPK);
		} catch (FinderException finderException) {
			log.error("Catching FinderException ");
			AssignedFlightSegmentVO assignedFlightSegmentVO = constructAssignedFlightSegmentVO(operationalFlightVO,
					segmentSerialNumber);
			assignedFlightSegment = new AssignedFlightSegment(assignedFlightSegmentVO);
		}
		log.debug("MailTransfer" + " : " + "findOrCreateAssignedFlightSegment" + " Exiting");
		return assignedFlightSegment;
	}

	/** 
	* @author a-1883
	* @param operationalFlightVO
	* @return AssignedFlightPK
	*/
	private AssignedFlightPK constructAssignedFlightPK(OperationalFlightVO operationalFlightVO) {
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
		assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		return assignedFlightPk;
	}

	/** 
	* @param operationalFlightVO
	* @param segmentSerialNumber
	* @return
	*/
	private AssignedFlightSegmentVO constructAssignedFlightSegmentVO(OperationalFlightVO operationalFlightVO,
			int segmentSerialNumber) {
		AssignedFlightSegmentVO assignedFlightSegmentVO = new AssignedFlightSegmentVO();
		assignedFlightSegmentVO.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightSegmentVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightSegmentVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightSegmentVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightSegmentVO.setSegmentSerialNumber(segmentSerialNumber);
		assignedFlightSegmentVO.setPol(operationalFlightVO.getPol());
		assignedFlightSegmentVO.setPou(operationalFlightVO.getPou());
		return assignedFlightSegmentVO;
	}

	/** 
	* @author a-1936 This method is used to create the assignedFlight
	* @param operationalFlightVO
	* @return AssignedFlight
	* @throws SystemException
	*/
	private AssignedFlight createAssignedFlight(OperationalFlightVO operationalFlightVO) {
		log.debug("MailTransfer" + " : " + "createAssignedFlight" + " Entering");
		AssignedFlightVO assignedFlightVO = new AssignedFlightVO();
		assignedFlightVO.setAirportCode(operationalFlightVO.getPol());
		assignedFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		assignedFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
		assignedFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
		assignedFlightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		AssignedFlight assignedFlight = new AssignedFlight(assignedFlightVO);
		log.debug("MailTransfer" + " : " + "createAssignedFlight" + " Exiting");
		return assignedFlight;
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	* @throws FlightClosedException 
	*/
	public void transferMailbagsAtExport(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO)
			throws InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException,
			FlightClosedException {
		NeoMastersServiceUtils neoMastersServiceUtils = ContextUtil.getInstance()
				.getBean(NeoMastersServiceUtils.class);
		MailMRAProxy mailOperationsMRAProxy = ContextUtil.getInstance().getBean(MailMRAProxy.class);
		ReassignController reassignController=ContextUtil.getInstance().getBean(ReassignController.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("MailTransfer" + " : " + "transferMailbags" + " Entering");
		Collection<String> systemParamterCodes = null;
		Map<String, String> systemParamterMap = null;
		Collection<MailbagVO> flightAssignedMailbags = new ArrayList<MailbagVO>();
		Collection<MailbagVO> destAssignedMailbags = new ArrayList<MailbagVO>();
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			if (reassignController.isReassignableMailbags(mailbagVOs, flightAssignedMailbags,
					destAssignedMailbags)) {
				if (!flightAssignedMailbags.isEmpty()) {
					reassignController.reassignMailFromFlight(flightAssignedMailbags);
				}
				if (!destAssignedMailbags.isEmpty()) {
					reassignController.reassignMailFromDestination(destAssignedMailbags);
				}
			}
		}
		if (containerVO.getContainerNumber() != null) {
			if (containerVO.getFlightSequenceNumber() > -1) {
				saveOutboundMailsFlightForTransfer(mailbagVOs, containerVO);
			} else {
				saveOutboundMailsDestnForTransfer(mailbagVOs, containerVO);
			}
			String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
			if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				mailController.flagMLDForMailbagTransfer(mailbagVOs, containerVO, null);
			}
		}
		Collection<MailbagVO> transferredMails = updateMailbagsForTransfer(mailbagVOs, containerVO);
		containerVO.setExportTransfer(true);
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		if (mailbagVOs != null) {
			LoginProfile logonVO = contextUtil.callerLoginProfile();
			for (MailbagVO mailbagvo : mailbagVOs) {
				if (mailbagvo.getScannedUser() == null) {
					mailbagvo.setScannedUser(logonVO.getUserId());
				}
			}
		}
		mailController.flagHistoryForMailTransferAtExport(mailbagVOs, containerVO,ContextUtil.getInstance().getTxContext(ContextUtil.TRIGGER_POINT) );
		mailController.flagAuditForMailTransferAtExport(mailbagVOs, containerVO);
		flagResditsForMailbagTransfer(mailbagVOs, containerVO);
		boolean provisionalRateImport = false;
		Collection<RateAuditVO> rateAuditVOs = mailController.createRateAuditVOs(containerVO, mailbagVOs,
				MailConstantsVO.MAIL_STATUS_TRANSFER_MAIL, provisionalRateImport);
		if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
			String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
			if (importEnabled != null && (importEnabled.contains("D") || importEnabled.contains("M"))) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		String provisionalRateimportEnabled = findSystemParameterValue(
				MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
			provisionalRateImport = true;
			Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(containerVO, mailbagVOs,
					MailConstantsVO.MAIL_STATUS_TRANSFERRED, provisionalRateImport);
			if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		if (!(containerVO.getCarrierCode().equals(containerVO.getOwnAirlineCode()))
				&& !validateCarrierCodeFromPartner(containerVO.getCompanyCode(), containerVO.getOwnAirlineCode(),
						containerVO.getAssignedPort(), containerVO.getCarrierCode())) {
			systemParamterCodes = new ArrayList<String>();
			systemParamterCodes.add(MailConstantsVO.NCA_RESDIT_PROC);
			try {
				systemParamterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParamterCodes);
			} catch (BusinessException e) {
				log.error(e.getMessage());
			}
			if (systemParamterMap != null && systemParamterMap.size() > 0
					&& systemParamterMap.get(MailConstantsVO.NCA_RESDIT_PROC).equals(MailConstantsVO.FLAG_YES)) {
				flagCarditsForTransferCarrier(mailbagVOs, containerVO);
			}
		}
		log.debug("MailTransfer" + " : " + "transferMailbags" + " Exiting");
	}

	public Map<String, Object> saveArrivalBeforeTransferOut(Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO) throws ULDDefaultsProxyException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		Map<MailbagPK, MailbagVO> mailMap = new HashMap<MailbagPK, MailbagVO>();
		operationalFlightVO.setTransferOutOperation(true);
		Collection<ULDForSegmentVO> uLDForSegmentVOs = new ArrayList<>();
		if (!operationalFlightVO.isTransferStatus()) {
			uLDForSegmentVOs = saveArrivalDetailsForTransfer(containerVOs, operationalFlightVO, mailMap);
		}
		Collection<MailbagVO> transferredMails = new ArrayList<>();
		Map<String, Object> mapForReturn = new HashMap<String, Object>();
		try {
			for (MailbagPK mailbagPK : mailMap.keySet()) {
				Mailbag mailbag = Mailbag.find(mailbagPK);
				MailbagVO mailbagVO = mailbag.retrieveVO();
				mailbagVO.setScannedPort(operationalFlightVO.getPol());
				mailbagVO.setScannedUser(operationalFlightVO.getOperator());
				mailbagVO.setUldNumber(mailbag.getUldNumber());
				if (operationalFlightVO.getOperationTime() != null) {
					mailbagVO.setScannedDate(localDateUtil.getLocalDateTime(
							operationalFlightVO.getOperationTime().toLocalDateTime(), operationalFlightVO.getPol()));
				} else {
					mailbagVO.setScannedDate(localDateUtil.getLocalDate(operationalFlightVO.getPol(), true));
				}
				if (containerVOs != null && !containerVOs.isEmpty()
						&& containerVOs.iterator().next().getMailSource() != null) {
					mailbagVO.setMailSource(containerVOs.iterator().next().getMailSource());
				}
				mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_YES);
				mailbag.updateArrivalDetails(mailbagVO);
				transferredMails.add(mailbagVO);
			}
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode());
		}
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		MailOperationsMapper mapper = ContextUtil.getInstance().getBean(MailOperationsMapper.class);
		mailController.flagHistoryForContainerTransfer(operationalFlightVO, -1, mapper.copyMailbagVOS(transferredMails), mailController.getTriggerPoint());
		if (!operationalFlightVO.isTransferStatus()) {
			flagResditsForContainerTransfer(transferredMails, containerVOs, operationalFlightVO);
		}
		if (uLDForSegmentVOs != null && !uLDForSegmentVOs.isEmpty()) {
			mapForReturn.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
					createContainerDtlsForTransfermanifest(uLDForSegmentVOs, transferredMails));
		}
		return mapForReturn;
	}
	/**
	 * @author a-1936This method is used to find the Transfer Manifest for the Different Transactions
	 * @param tranferManifestFilterVo
	 * @return
	 * @throws SystemException
	 */
	public Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo) {
		return TransferManifest.findTransferManifest(tranferManifestFilterVo);
	}

}
