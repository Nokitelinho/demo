package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.proxy.*;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.exception.*;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** 
 * Saves the arrival details of an Inbound Flight
 * @author A-1739
 */
@Slf4j
@Component
public class MailArrival {
	private static final String TRANSACTION_ARRIVAL = "ARRIVAL";
	private static final String MODULE = "mail.operations";
	private static final String ENTITY = "MailArrival";
	//TODO:Commented
	//private static final Logger errPgExceptionLogger = ExtendedLogManager.getLogger("MAILHHTERR");
	@Autowired
	MailOperationsMapper mailOperationsMapper;
	@Autowired
	private LocalDate localDateUtil;
	/** 
	* TODO Purpose Jan 29, 2007, A-1739
	* @param dsnVOs
	* @param mailbagVO
	* @param containerDetailsVO
	* @throws SystemException 
	*/
	private void updateMailbagDSNVO(Collection<DSNVO> dsnVOs, MailbagVO mailbagVO,
			ContainerDetailsVO containerDetailsVO) {
		for (DSNVO dsnVO : dsnVOs) {
			if (MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())) {
				if (dsnVO.getDsn().equals(mailbagVO.getDespatchSerialNumber())
						&& dsnVO.getOriginExchangeOffice().equals(mailbagVO.getOoe())
						&& dsnVO.getDestinationExchangeOffice().equals(mailbagVO.getDoe())
						&& dsnVO.getMailCategoryCode().equals(mailbagVO.getMailCategoryCode())
						&& dsnVO.getMailSubclass().equals(mailbagVO.getMailSubclass())
						&& dsnVO.getYear() == mailbagVO.getYear()) {
					if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getAcceptanceFlag())) {
						if (!MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())) {
							dsnVO.setReceivedBags(dsnVO.getReceivedBags() + 1);
							try {
								dsnVO.setReceivedWeight(dsnVO.getReceivedWeight().add(mailbagVO.getWeight()));
							} finally {
							}
							containerDetailsVO.setReceivedBags(containerDetailsVO.getReceivedBags() + 1);
							if (mailbagVO.getWeight() != null) {
								try {
									containerDetailsVO.setReceivedWeight(
											containerDetailsVO.getReceivedWeight().add(mailbagVO.getWeight()));
								} finally {
								}
							}
						}
						if (!MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
							dsnVO.setDeliveredBags(dsnVO.getDeliveredBags() + 1);
							try {
								if(dsnVO.getDeliveredWeight()!=null)
								dsnVO.setDeliveredWeight(dsnVO.getDeliveredWeight().add(mailbagVO.getWeight()));
							} finally {
							}
						}
						dsnVO.setOperationFlag(dsnVO.OPERATION_FLAG_UPDATE);
					} else {
						if (!MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
							dsnVO.setDeliveredBags(dsnVO.getDeliveredBags() + 1);
							try {
								if(dsnVO.getDeliveredWeight()!=null)
								dsnVO.setDeliveredWeight(dsnVO.getDeliveredWeight().add(mailbagVO.getWeight()));
							} finally {
							}
						}
						if (!DSNVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())) {
							dsnVO.setOperationFlag(DSNVO.OPERATION_FLAG_UPDATE);
						}
					}
				}
			}
		}
	}

	/** 
	* updates the dsnVO of a despatchVO Jan 29, 2007, A-1739
	* @param dsnVOs
	* @param despatchDetailsVO
	* @param containerDetailsVO
	* @throws SystemException 
	*/
	private void updateDespatchDSNVO(Collection<DSNVO> dsnVOs, DespatchDetailsVO despatchDetailsVO,
			ContainerDetailsVO containerDetailsVO) {
		for (DSNVO dsnVO : dsnVOs) {
			if (MailConstantsVO.FLAG_NO.equals(dsnVO.getPltEnableFlag())) {
				if (dsnVO.getDsn().equals(despatchDetailsVO.getDsn())
						&& dsnVO.getOriginExchangeOffice().equals(despatchDetailsVO.getOriginOfficeOfExchange())
						&& dsnVO.getDestinationExchangeOffice()
								.equals(despatchDetailsVO.getDestinationOfficeOfExchange())
						&& dsnVO.getMailCategoryCode().equals(despatchDetailsVO.getMailCategoryCode())
						&& dsnVO.getMailSubclass().equals(despatchDetailsVO.getMailSubclass())
						&& dsnVO.getYear() == despatchDetailsVO.getYear()) {
					if (despatchDetailsVO.getAcceptedBags() > 0) {
						dsnVO.setReceivedBags(dsnVO.getReceivedBags() + despatchDetailsVO.getAcceptedBags()
								- despatchDetailsVO.getReceivedBags());
						Quantity despatchRecWt;
						try {
							despatchRecWt = despatchDetailsVO.getAcceptedWeight()
									.subtract(despatchDetailsVO.getReceivedWeight());
							Quantity recievedWt;
							try {
								recievedWt = dsnVO.getReceivedWeight().add(despatchRecWt);
								dsnVO.setReceivedWeight(recievedWt);
							} finally {
							}
						} finally {
						}
						dsnVO.setDeliveredBags(dsnVO.getDeliveredBags() + despatchDetailsVO.getAcceptedBags()
								- despatchDetailsVO.getDeliveredBags());
						Quantity despatchAcpWt;
						try {
							despatchAcpWt = despatchDetailsVO.getAcceptedWeight()
									.subtract(despatchDetailsVO.getDeliveredWeight());
							Quantity deliveredWt;
							try {
								deliveredWt = dsnVO.getDeliveredWeight().add(despatchAcpWt);
								dsnVO.setDeliveredWeight(deliveredWt);
							} finally {
							}
						} finally {
						}
						containerDetailsVO.setReceivedBags(containerDetailsVO.getReceivedBags()
								+ (despatchDetailsVO.getAcceptedBags() - despatchDetailsVO.getReceivedBags()));
						Quantity despWt;
						try {
							despWt = despatchDetailsVO.getAcceptedWeight()
									.subtract(despatchDetailsVO.getReceivedWeight());
							try {
								containerDetailsVO
										.setReceivedWeight(containerDetailsVO.getReceivedWeight().add(despWt));
							} finally {
							}
						} finally {
						}
						log.debug("" + "CHECK FOR THE  RECEIVED BAGS " + " " + containerDetailsVO);
						dsnVO.setOperationFlag(DSNVO.OPERATION_FLAG_UPDATE);
					} else {
						dsnVO.setDeliveredBags(dsnVO.getDeliveredBags() + despatchDetailsVO.getReceivedBags()
								- despatchDetailsVO.getDeliveredBags());
						Quantity despatchDelWt;
						try {
							despatchDelWt = despatchDetailsVO.getReceivedWeight()
									.subtract(despatchDetailsVO.getDeliveredWeight());
							Quantity deliveredWt;
							try {
								deliveredWt = dsnVO.getDeliveredWeight().add(despatchDelWt);
								dsnVO.setDeliveredWeight(deliveredWt);
							} finally {
							}
						} finally {
						}
						if (!DSNVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())) {
							dsnVO.setOperationFlag(DSNVO.OPERATION_FLAG_UPDATE);
						}
					}
				}
			}
		}
	}

	/** 
	* TODO Purpose Jan 25, 2007, A-1739
	* @param mailArrivalVO
	* @throws SystemException
	*/
	private void markDeliveryForTerminatingMailVOs(MailArrivalVO mailArrivalVO) throws MailOperationsBusinessException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(ENTITY + " : " + "markDeliveryForTerminatingMailVOs" + " Entering");
		Collection<ContainerDetailsVO> containers = mailArrivalVO.getContainerDetails();
		String companyCode = mailArrivalVO.getCompanyCode();
		String airportCode = mailArrivalVO.getAirportCode();
		boolean isPartial = mailArrivalVO.isPartialDelivery();
		Map<String, String> cityCache = new HashMap<String, String>();
		for (ContainerDetailsVO containerDetailsVO : containers) {
			if (!isPartial || (isPartial && ContainerDetailsVO.OPERATION_FLAG_UPDATE.equals(containerDetailsVO.getOperationFlag()))) {
				Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
				Collection<DespatchDetailsVO> despatches = containerDetailsVO.getDesptachDetailsVOs();
				if (despatches != null && despatches.size() > 0) {
					for (DespatchDetailsVO despatchDetailsVO : despatches) {
						if (!isPartial || (isPartial
								&& ContainerDetailsVO.OPERATION_FLAG_UPDATE.equals(despatchDetailsVO.getOperationalFlag()))) {
							boolean isAlreadyDlv = false;
							if (despatchDetailsVO.getAcceptedBags() > 0) {
								if (despatchDetailsVO.getDeliveredBags() == despatchDetailsVO.getAcceptedBags()) {
									isAlreadyDlv = true;
								}
							} else {
								if (despatchDetailsVO.getDeliveredBags() == despatchDetailsVO.getReceivedBags()) {
									isAlreadyDlv = true;
								}
							}
							OfficeOfExchangeVO originOfficeOfExchangeVO;
							originOfficeOfExchangeVO = ContextUtil.getInstance().getBean(MailController.class).validateOfficeOfExchange(companyCode,
									despatchDetailsVO.getOriginOfficeOfExchange());
							String poaCode = originOfficeOfExchangeVO.getPoaCode();
							if (!isAlreadyDlv && airportCode.equals(despatchDetailsVO.getDestination()) ? true
									: isValidDeliveryAirport(despatchDetailsVO.getDestinationOfficeOfExchange(),
											companyCode, airportCode, cityCache, MailConstantsVO.RESDIT_DELIVERED,
											poaCode, despatchDetailsVO.getConsignmentDate())) {
								updateDespatchDSNVO(dsnVOs, despatchDetailsVO, containerDetailsVO);
								if (despatchDetailsVO.getAcceptedBags() > 0) {
									despatchDetailsVO.setReceivedBags(despatchDetailsVO.getAcceptedBags());
									despatchDetailsVO.setReceivedWeight(despatchDetailsVO.getAcceptedWeight());
									despatchDetailsVO.setDeliveredBags(despatchDetailsVO.getAcceptedBags());
									despatchDetailsVO.setDeliveredWeight(despatchDetailsVO.getAcceptedWeight());
								} else {
									log.debug("THE  PURE ACCEPTED AT ARRIVAL PORT");
									despatchDetailsVO.setReceivedBags(despatchDetailsVO.getReceivedBags());
									despatchDetailsVO.setReceivedWeight(despatchDetailsVO.getReceivedWeight());
									despatchDetailsVO.setDeliveredBags(despatchDetailsVO.getReceivedBags());
									despatchDetailsVO.setDeliveredWeight(despatchDetailsVO.getReceivedWeight());
								}
								despatchDetailsVO.setReceivedDate(localDateUtil.getLocalDate(airportCode, true));
								if (!DespatchDetailsVO.OPERATION_FLAG_INSERT.equals(despatchDetailsVO.getOperationalFlag())) {
									despatchDetailsVO.setOperationalFlag(DespatchDetailsVO.OPERATION_FLAG_UPDATE);
									containerDetailsVO.setOperationFlag(DespatchDetailsVO.OPERATION_FLAG_UPDATE);
								}
							}
						}
					}
				}
				Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
				if (mailbags != null && mailbags.size() > 0) {
					for (MailbagVO mailbagVO : mailbags) {
						if (!isPartial || (isPartial && MailbagVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag()))) {
							Mailbag mailbagToFindPA = null;
							String poaCode = null;
							MailbagPK mailbagPk = new MailbagPK();
							mailbagPk.setCompanyCode(companyCode);
							mailbagPk.setMailSequenceNumber(
									findMailSequenceNumber(mailbagVO.getMailbagId(), companyCode));
							try {
								mailbagToFindPA = Mailbag.find(mailbagPk);
							} catch (FinderException e) {
								e.getMessage();
							}
							if (mailbagToFindPA != null && mailbagToFindPA.getPaCode() != null) {
								poaCode = mailbagToFindPA.getPaCode();
							} else {
								OfficeOfExchangeVO originOfficeOfExchangeVO;
								originOfficeOfExchangeVO = ContextUtil.getInstance().getBean(MailController.class)
										.validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
								poaCode = originOfficeOfExchangeVO.getPoaCode();
							}
							if (!MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())
									&& airportCode.equals(mailbagVO.getDestination())
											? true
											: isValidDeliveryAirport(mailbagVO.getDoe(), companyCode, airportCode,
													cityCache, MailConstantsVO.RESDIT_DELIVERED, poaCode,
													mailbagVO.getConsignmentDate())) {
								updateMailbagDSNVO(dsnVOs, mailbagVO, containerDetailsVO);
								mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_YES);
								mailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_YES);
								mailbagVO.setScannedPort(airportCode);
								if (mailArrivalVO.getScanDate() != null) {
									mailbagVO.setScannedDate(mailArrivalVO.getScanDate());
								}
								if (mailbagVO.getScannedUser() == null) {
									mailbagVO.setScannedUser(mailArrivalVO.getArrivedUser());
								}
								mailbagVO.setFlightDate(mailArrivalVO.getArrivalDate());
								if (MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())) {
									containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
									if (!"N".equals(containerDetailsVO.getDeliverPABuiltContainer())) {
										containerDetailsVO.setDeliveredStatus(MailConstantsVO.FLAG_YES);
									}
								}
								if (!MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
									mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_UPDATE);
									containerDetailsVO.setOperationFlag(MailbagVO.OPERATION_FLAG_UPDATE);
								}
							}
						}
					}
				}
				if (dsnVOs == null || dsnVOs.size() == 0) {
					if (!isPartial
							|| (isPartial && MailbagVO.OPERATION_FLAG_UPDATE.equals(containerDetailsVO.getOperationFlag()))) {
						if (MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())) {
							containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
							containerDetailsVO.setDeliveredStatus(MailConstantsVO.FLAG_YES);
							containerDetailsVO.setOperationFlag(MailbagVO.OPERATION_FLAG_UPDATE);
						}
					}
				}
			}
		}
		log.debug(ENTITY + " : " + "markDeliveryForTerminatingMailVOs" + " Exiting");
	}

	/** 
	* This method does the group delivery for all mailbags that can be delivered Jan 25, 2007, A-1739
	* @param mailArrivalVO
	* @throws SystemException
	* @throws FlightClosedException
	* @throws InvalidFlightSegmentException
	* @throws MailbagIncorrectlyDeliveredException
	* @throws DuplicateMailBagsException
	* @throws ContainerAssignmentException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	* @throws MailOperationsBusinessException
	*/
	public void deliverMailbags(MailArrivalVO mailArrivalVO, Collection<MailbagVO> arrivedMailBagsForMonitorSLA,
			Collection<MailbagVO> deliveredMailBagsForMonitorSLA,
			Collection<DespatchDetailsVO> despatchDetailsForInventoryRemoval) throws MailOperationsBusinessException {
		log.debug(ENTITY + " : " + "deliverMailbags" + " Entering");
		markDeliveryForTerminatingMailVOs(mailArrivalVO);
		log.debug("" + "after marking delivery " + " " + mailArrivalVO);
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.saveArrivalDetails(mailArrivalVO);
		log.debug(ENTITY + " : " + "deliverMailbags" + " Exiting");
	}

	/** 
	* This method is used to save ArrivalDetails
	* @param mailArrivalVO
	* @return
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @throws DuplicateMailBagsException
	* @throws MailbagIncorrectlyDeliveredException
	* @throws InvalidFlightSegmentException
	* @throws FlightClosedException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public ScannedMailDetailsVO saveArrivalDetails(MailArrivalVO mailArrivalVO,
			Collection<MailbagVO> arrivedMailBagsForMonitorSLA, Collection<MailbagVO> deliveredMailBagsForMonitorSLA)
			throws ContainerAssignmentException, DuplicateMailBagsException, MailbagIncorrectlyDeliveredException,
			InvalidFlightSegmentException, FlightClosedException, ULDDefaultsProxyException,
			CapacityBookingProxyException, MailBookingException, MailOperationsBusinessException {
		MailMRAProxy mailOperationsMRAProxy = ContextUtil.getInstance().getBean(MailMRAProxy.class);
		log.debug(ENTITY + " : " + "saveArrivedMails" + " Entering");
		log.debug("" + "The Mail Arrival VO" + " " + mailArrivalVO);
		boolean fltClosureCheckNeeded = false;
		boolean isRDTRestrictReq = false;
		updateMailbagVOforResdit(mailArrivalVO);
		if (!mailArrivalVO.isMailVOUpdated()) {
			Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
			Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
			if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
				for (ContainerDetailsVO contVO : containerDetailsVOs) {
					if (contVO.getMailDetails() != null && !contVO.getMailDetails().isEmpty()) {
						Collection<MailbagVO> mailbagVOs = contVO.getMailDetails();
						if (!mailArrivalVO.isFlightChange()) {
							for (MailbagVO mailbagVO : mailbagVOs) {
								if (mailbagVO.getMailSequenceNumber() > 0 && mailbagVO.getOperationalFlag() != null) {
									mailVOs.add(mailbagVO);
									if (mailbagVO.getDocumentNumber() != null
											&& mailbagVO.getDocumentNumber().trim().length() > 0) {
										fltClosureCheckNeeded = true;
									}
								}
								if (MailConstantsVO.MAIL_SRC_RESDIT.equals(mailbagVO.getMailbagSource())) {
									isRDTRestrictReq = true;
								}
							}
						}
					}
				}
				MailController.updateMailbagVOs(mailVOs, mailArrivalVO.isFlightChange());
			}
		}
		Collection<DespatchDetailsVO> despatchDetailsForInventoryRemoval = new ArrayList<DespatchDetailsVO>();
		Map<String, Collection<DespatchDetailsVO>> despatchesMapForInventory = new HashMap<String, Collection<DespatchDetailsVO>>();
		Map<String, Collection<MailbagVO>> mailBagsMapForInventory = new HashMap<String, Collection<MailbagVO>>();
		Collection<MailbagVO> deliveredArrivedMails = new ArrayList<MailbagVO>();
		Collection<ContainerDetailsVO> deliveredContainers = new ArrayList<ContainerDetailsVO>();
		Collection<MailbagVO> newlyArrivedMailbags = new ArrayList<MailbagVO>();
		Collection<MailbagVO> expMails = new ArrayList<MailbagVO>();
		Collection<ContainerDetailsVO> paBuiltContainers = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> arrivedContainers = mailArrivalVO.getContainerDetails();
		updatebulkDetails(arrivedContainers);
		AssignedFlightPK inboundFlightPK = createInboundFlightPK(mailArrivalVO);
		AssignedFlightVO inboundFlightVO = constructInboundFlightVO(mailArrivalVO);
		findOrCreateInboundFlight(inboundFlightPK, inboundFlightVO);
		boolean isScanned = mailArrivalVO.isScanned();
		if (isScanned && !fltClosureCheckNeeded) {
			checkFlightClosureForArrival(mailArrivalVO);
		}
		ScannedMailDetailsVO scanDetailsVO = constructScannedMailDetailsForFlight(mailArrivalVO);
		Map<String, DSNVO> dsnMap = checkForIncorrectArrivalDetails(mailArrivalVO, despatchDetailsForInventoryRemoval,
				expMails, newlyArrivedMailbags, deliveredMailBagsForMonitorSLA, deliveredArrivedMails,
				deliveredContainers);
		if (arrivedMailBagsForMonitorSLA != null) {
			arrivedMailBagsForMonitorSLA.addAll(newlyArrivedMailbags);
		}
		Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> segmentContainerMap = groupContainersForSegmentForArrival(
				mailArrivalVO, paBuiltContainers);
		boolean isUpdated = false;
		boolean isSaveSuccessfull = false;
		for (Map.Entry<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> segmentContainer : segmentContainerMap
				.entrySet()) {
			AssignedFlightSegment flightSegment = null;
			AssignedFlightSegmentPK assignedFlightSegmentPK = segmentContainer.getKey();
			Collection<ContainerDetailsVO> segmentContainers = segmentContainer.getValue();
			try {
				flightSegment = AssignedFlightSegment.find(assignedFlightSegmentPK);
			} catch (FinderException exception) {
				flightSegment = new AssignedFlightSegment(
						constructAssignedFlightSegVOForContainer(assignedFlightSegmentPK, segmentContainers));
			}
			isSaveSuccessfull = flightSegment.saveArrivalDetails(segmentContainers, mailArrivalVO, expMails,
					mailBagsMapForInventory, despatchesMapForInventory);
			if (isSaveSuccessfull) {
				isUpdated = true;
			}
		}
		if (isUpdated) {
			saveDSNMstDetails(dsnMap, isScanned);
			Collection<MailbagVO> mailbagsForMLDForDelivery = new ArrayList<MailbagVO>();
			Collection<MailbagVO> mailbagsForMLDForArrival = new ArrayList<MailbagVO>();
			Collection<MailbagVO> mailbagsForResdit = new ArrayList<MailbagVO>();
			Collection<MailbagVO> mailbagsForHistory = new ArrayList<MailbagVO>();
			mailbagsForResdit.addAll(newlyArrivedMailbags);
			mailbagsForResdit.addAll(deliveredArrivedMails);
			mailbagsForHistory.addAll(newlyArrivedMailbags);
			mailbagsForHistory.addAll(deliveredArrivedMails);
			mailbagsForHistory.addAll(deliveredArrivedMails);
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			MailOperationsMapper mailOperationsMapper = ContextUtil.getInstance().getBean(MailOperationsMapper.class);
			if (!isRDTRestrictReq) {
				mailController.flagResditsForArrival(mailArrivalVO, mailbagsForResdit, paBuiltContainers);
			}
			mailController.flagMailbagAuditForArrival(mailArrivalVO);
			mailController.flagMailbagHistoryForArrival(mailOperationsMapper.copymailArrivalVO(mailArrivalVO), mailController.getTriggerPoint());
			if (newlyArrivedMailbags != null && newlyArrivedMailbags.size() > 0) {
				for (MailbagVO mailbagVO : newlyArrivedMailbags) {
					if ("Y".equals(mailbagVO.getArrivedFlag()) && "Y".equals(mailbagVO.getDeliveredFlag())) {
						mailbagsForMLDForDelivery.add(mailbagVO);
						mailbagsForMLDForArrival.add(mailbagVO);
					} else if ("Y".equals(mailbagVO.getArrivedFlag())) {
						mailbagsForMLDForArrival.add(mailbagVO);
					}
				}
			}
			if (deliveredArrivedMails != null && deliveredArrivedMails.size() > 0) {
				for (MailbagVO mailbagVO : deliveredArrivedMails) {
					if ("Y".equals(mailbagVO.getDeliveredFlag())) {
						mailbagsForMLDForDelivery.add(mailbagVO);
					}
				}
				String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
				boolean provisionalRateImport = false;
				if (importEnabled != null && importEnabled.contains("D")) {
					Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(
							mailArrivalVO.getContainerDetails(), MailConstantsVO.MAIL_STATUS_DELIVERED,
							provisionalRateImport);
					if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
						try {
							mailOperationsMRAProxy.importMRAData(rateAuditVOs);
						} catch (BusinessException e) {
							throw new SystemException(e.getMessage(), e.getMessage(), e);
						}
					}
				}
				String provisionalRateimportEnabled = findSystemParameterValue(
						MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
				if (provisionalRateimportEnabled != null
						&& MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
					provisionalRateImport = true;
					Collection<RateAuditVO> provisionalRateAuditVOs = new MailController().createRateAuditVOs(
							mailArrivalVO.getContainerDetails(), MailConstantsVO.MAIL_STATUS_DELIVERED,
							provisionalRateImport);
					if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
						try {
							mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
						} catch (BusinessException e) {
							throw new SystemException(e.getMessage(), e.getMessage(), e);
						}
					}
				}
			}
			if (mailbagsForMLDForArrival != null && mailbagsForMLDForArrival.size() > 0) {
				String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
				if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
					mailController.flagMLDForMailOperations(mailbagsForMLDForArrival, "HND");
				}
			}
			if (mailbagsForMLDForDelivery != null && mailbagsForMLDForDelivery.size() > 0) {
				String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
				if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
					mailController.flagMLDForMailOperations(mailbagsForMLDForDelivery, "DLV");
				}
			}
			String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
			if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
				mailController.flagMLDForMailOperations(mailbagsForMLDForArrival, "RCF");
			}
			if (UldInFlightVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD))) {
				updateOperationalULDs(mailArrivalVO);
			}
		}
		scanDetailsVO.setMailDetails(expMails);
		if (deliveredArrivedMails != null && deliveredArrivedMails.size() > 0) {
			removeDeliveredMailbagsFromInventory(deliveredArrivedMails);
		}
		if (deliveredContainers != null && deliveredContainers.size() > 0) {
			try {
				removeDeliveredContainersFromInventory(deliveredContainers);
			} catch (FinderException e) {
				e.getErrorCode();
			}
		}
		if (despatchDetailsForInventoryRemoval != null && despatchDetailsForInventoryRemoval.size() > 0) {
			removeDeliveredDespatchesFromInventory(despatchDetailsForInventoryRemoval);
		}
		try {
			saveInventoryDetailsForArrival(constructMailAcceptanceForInventory(mailArrivalVO, despatchesMapForInventory,
					mailBagsMapForInventory));
		} catch (InventoryForArrivalFailedException e) {
			e.getMessage();
		}
		log.debug(ENTITY + " : " + "saveArrivedMails" + " Exiting");
		return scanDetailsVO;
	}

	private MailbagInULDForSegmentPK constructMailbagInULDForSegmentPK(ContainerDetailsVO containerDtlsVO,
			MailbagVO mailbagvo) {
		MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
		mailbagInULDForSegmentPK.setCompanyCode(containerDtlsVO.getCompanyCode());
		mailbagInULDForSegmentPK.setCarrierId(containerDtlsVO.getCarrierId());
		mailbagInULDForSegmentPK.setFlightNumber(containerDtlsVO.getFlightNumber());
		mailbagInULDForSegmentPK.setFlightSequenceNumber(containerDtlsVO.getFlightSequenceNumber());
		mailbagInULDForSegmentPK.setSegmentSerialNumber(containerDtlsVO.getSegmentSerialNumber());
		if (MailConstantsVO.BULK_TYPE.equals(containerDtlsVO.getContainerType())) {
			mailbagInULDForSegmentPK
					.setUldNumber(MailConstantsVO.CONST_BULK + MailConstantsVO.SEPARATOR + containerDtlsVO.getPou());
		} else {
			mailbagInULDForSegmentPK.setUldNumber(containerDtlsVO.getContainerNumber());
		}
		mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		return mailbagInULDForSegmentPK;
	}

	private void updatebulkDetails(Collection<ContainerDetailsVO> containerDetailsVOs) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			updateMailVOsLegSerialNumber(containerDetailsVOs);
			for (ContainerDetailsVO containerDtlsVO : containerDetailsVOs) {
				if (MailConstantsVO.BULK_TYPE.equals(containerDtlsVO.getContainerType())) {
					log.debug("Inside UpDATEBULK>>>>");
					Collection<MailbagVO> mailBagVOinConatiners = null;
					mailBagVOinConatiners = containerDtlsVO.getMailDetails();
					if (mailBagVOinConatiners != null) {
						for (MailbagVO mailbagvo : mailBagVOinConatiners) {
							updateBarrowDetails(containerDtlsVO, mailbagvo);
						}
						break;
					}
				}
			}
		}
	}

	private void updateBarrowDetails(ContainerDetailsVO containerDtlsVO, MailbagVO mailbagvo) {
		if (mailbagvo.getOperationalFlag() != null
				&& (mailbagvo.getOperationalFlag().equals(MailConstantsVO.OPERATION_FLAG_UPDATE)
						|| mailbagvo.getOperationalFlag().equals(MailConstantsVO.OPERATION_FLAG_INSERT))) {
			MailbagInULDForSegment mailbagInULDForSegment = null;
			try {
				mailbagInULDForSegment = MailbagInULDForSegment
						.find(constructMailbagInULDForSegmentPK(containerDtlsVO, mailbagvo));
			} catch (FinderException e) {
				log.error("Finder Exception Caught");
			}
			if (mailbagInULDForSegment != null) {
				log.debug("mailbag not null>>>>");
				Container container = null;
				ContainerPK containerPK = new ContainerPK();
				containerPK.setCompanyCode(containerDtlsVO.getCompanyCode());
				containerPK.setCarrierId(containerDtlsVO.getCarrierId());
				containerPK.setContainerNumber(mailbagInULDForSegment.getContainerNumber());
				containerPK.setAssignmentPort(containerDtlsVO.getPol());
				containerPK.setFlightNumber(containerDtlsVO.getFlightNumber());
				containerPK.setFlightSequenceNumber(containerDtlsVO.getFlightSequenceNumber());
				if (mailbagvo.getLegSerialNumber() == 0) {
					FlightValidationVO flightValidationVO = new FlightValidationVO();
					try {
						flightValidationVO = validateFlightForBulk(containerDtlsVO);
					} finally {
					}
					if (flightValidationVO != null) {
						containerPK.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					}
				} else {
					containerPK.setLegSerialNumber(mailbagvo.getLegSerialNumber());
				}
				try {
					container = Container.find(containerPK);
				} catch (FinderException finderException) {
					log.error("FinderException Caught");
				}
				if (container != null && !MailConstantsVO.FLAG_YES.equals(container.getArrivedStatus())) {
					log.debug("Container not null>>>>");
					container.setArrivedStatus(MailConstantsVO.FLAG_YES);
				}
			}
		}
	}

	/** 
	* A-1739
	* @param mailArrivalVO
	* @return
	*/
	private AssignedFlightPK createInboundFlightPK(MailArrivalVO mailArrivalVO) {
		AssignedFlightPK inboundFlightPK = new AssignedFlightPK();
		inboundFlightPK.setCompanyCode(mailArrivalVO.getCompanyCode());
		inboundFlightPK.setCarrierId(mailArrivalVO.getCarrierId());
		inboundFlightPK.setFlightNumber(mailArrivalVO.getFlightNumber());
		inboundFlightPK.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
		inboundFlightPK.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
		inboundFlightPK.setAirportCode(mailArrivalVO.getAirportCode());
		return inboundFlightPK;
	}

	/** 
	* A-1739
	* @param mailArrivalVO
	* @return
	*/
	private AssignedFlightVO constructInboundFlightVO(MailArrivalVO mailArrivalVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		AssignedFlightVO inboundFlightVO = new AssignedFlightVO();
		inboundFlightVO.setCompanyCode(mailArrivalVO.getCompanyCode());
		inboundFlightVO.setCarrierId(mailArrivalVO.getCarrierId());
		inboundFlightVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
		inboundFlightVO.setFlightNumber(mailArrivalVO.getFlightNumber());
		inboundFlightVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
		inboundFlightVO.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
		inboundFlightVO.setAirportCode(mailArrivalVO.getAirportCode());
		inboundFlightVO.setFlightDate(mailArrivalVO.getArrivalDate());
		inboundFlightVO.setImportFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
		inboundFlightVO.setExportFlightStatus(MailConstantsVO.FLIGHT_STATUS_CLOSED);
		if (mailArrivalVO.getFlightStatus() != null) {
			inboundFlightVO.setFlightStatus(mailArrivalVO.getFlightStatus());
		} else {
			inboundFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_CLOSED);
		}
		LoginProfile logonVO = contextUtil.callerLoginProfile();
		inboundFlightVO.setLastUpdateTime(localDateUtil.getLocalDate(logonVO.getAirportCode(), true));
		inboundFlightVO.setLastUpdateUser(logonVO.getUserId());
		return inboundFlightVO;
	}

	private FlightValidationVO validateFlightForBulk(ContainerDetailsVO containerDetailsVO) {
		FlightOperationsProxy flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		Collection<FlightValidationVO> flightValidationVOs = null;
		flightValidationVOs = flightOperationsProxy
				.validateFlightForAirport(constructFlightFilterVOForContainer(containerDetailsVO));
		for (FlightValidationVO flightValidationVO : flightValidationVOs) {
			if (flightValidationVO.getFlightSequenceNumber() == containerDetailsVO.getFlightSequenceNumber()) {
				return flightValidationVO;
			}
		}
		return null;
	}

	private FlightFilterVO constructFlightFilterVOForContainer(ContainerDetailsVO containerDetailsVO) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(containerDetailsVO.getCarrierId());
		flightFilterVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(containerDetailsVO.getFlightDate()));
		flightFilterVO.setDirection(FlightFilterVO.INBOUND);
		flightFilterVO.setStation(containerDetailsVO.getPou());
		flightFilterVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		return flightFilterVO;
	}

	/** 
	* @author a-1936 Added By Karthick V as the part of the NCA Mail TrackingCR This method is used to Construct the Mail Acceptance VO against which Carrier the MailBags will be Saved For the Inventory...
	* @param mailArrivalVO
	* @return
	* @throws ContainerAssignmentException
	*/
	private MailAcceptanceVO constructMailAcceptanceForInventory(MailArrivalVO mailArrivalVO,
			Map<String, Collection<DespatchDetailsVO>> despatchesMap, Map<String, Collection<MailbagVO>> mailBagsMap) {
		log.debug(ENTITY + " : " + "constructMailAcceptanceForInventory" + " Entering");
		MailAcceptanceVO mailAcceptanceVo = new MailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		Collection<ContainerDetailsVO> emptyContainerDetailsVOs = null;
		mailAcceptanceVo.setCompanyCode(mailArrivalVO.getCompanyCode());
		mailAcceptanceVo.setCarrierId(mailArrivalVO.getCarrierId());
		mailAcceptanceVo.setPol(mailArrivalVO.getAirportCode());
		mailAcceptanceVo.setFlightCarrierCode(mailArrivalVO.getFlightCarrierCode());
		mailAcceptanceVo.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
		mailAcceptanceVo.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
		mailAcceptanceVo.setOwnAirlineCode(mailArrivalVO.getOwnAirlineCode());
		mailAcceptanceVo.setOwnAirlineId(mailArrivalVO.getOwnAirlineId());
		mailAcceptanceVo.setAcceptedUser(mailArrivalVO.getArrivedUser());
		mailAcceptanceVo.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
		mailAcceptanceVo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ARR);
		mailAcceptanceVo.setPreassignNeeded(false);
		mailAcceptanceVo.setScanned(true);
		mailAcceptanceVo.setInventory(true);
		mailAcceptanceVo.setInventoryForArrival(true);
		mailAcceptanceVo.setContainerDetails(new ArrayList<ContainerDetailsVO>());
		emptyContainerDetailsVOs = constructEmptyContainerForInventoryForArrival(mailArrivalVO, mailAcceptanceVo);
		log.debug("" + "emptyContainerDetailsVOs" + " " + emptyContainerDetailsVOs);
		if (emptyContainerDetailsVOs != null && emptyContainerDetailsVOs.size() > 0) {
			mailAcceptanceVo.getContainerDetails().addAll(emptyContainerDetailsVOs);
		}
		containerDetailsVOs = constructContainerForInventoryForArrival(mailArrivalVO, mailAcceptanceVo, mailBagsMap,
				despatchesMap);
		log.debug("" + "containerDetailsVOs" + " " + containerDetailsVOs);
		log.debug("" + "THE MAIL ACCEPTANCE VO FOR THE INVENTORY SAVE" + " " + mailAcceptanceVo);
		log.debug(ENTITY + " : " + "constructMailAcceptanceForInventory" + " Exiting");
		return mailAcceptanceVo;
	}

	/** 
	* @author A-2553
	* @param mailArrivalVO
	* @param mailAcceptanceVo
	* @return
	* @throws SystemException
	*/
	private Collection<ContainerDetailsVO> constructEmptyContainerForInventoryForArrival(MailArrivalVO mailArrivalVO,
			MailAcceptanceVO mailAcceptanceVo) {
		Collection<ContainerDetailsVO> containersForArrival = new ArrayList<ContainerDetailsVO>();
		if (mailArrivalVO.getContainerDetails() != null && mailArrivalVO.getContainerDetails().size() > 0) {
			for (ContainerDetailsVO cntdtlsVO : mailArrivalVO.getContainerDetails()) {
				if (((cntdtlsVO.getDsnVOs() == null || cntdtlsVO.getDsnVOs().size() == 0))
						&& cntdtlsVO.getOperationFlag() != null
						&& !MailConstantsVO.FLAG_YES.equals(cntdtlsVO.getDeliveredStatus())) {
					ContainerDetailsVO conntDtlsVO = new ContainerDetailsVO();
					conntDtlsVO.setContainerType(MailConstantsVO.BULK_TYPE);
					conntDtlsVO.setDestination(null);
					conntDtlsVO.setContainerNumber(MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR)
							.concat(cntdtlsVO.getCarrierCode()));
					updateContainerDetailsForInventory(conntDtlsVO, mailAcceptanceVo);
					containersForArrival.add(conntDtlsVO);
					break;
				}
			}
		}
		return containersForArrival;
	}

	/** 
	* @author a-1936 Added By Karthick V as the Part of the NCA Mail TrackingCR This method is used to reset the Pks of the Arrived Containers as Required For the Inventory Save..
	* @throws SystemException
	*/
	private void updateContainerDetailsForInventory(ContainerDetailsVO containerDetails,
			MailAcceptanceVO mailAcceptanceVo) {
		log.debug(ENTITY + " : " + "updateContainerDetailsForInventory" + " Entering");
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		containerDetails.setCompanyCode(mailAcceptanceVo.getCompanyCode());
		containerDetails.setCarrierCode(mailAcceptanceVo.getFlightCarrierCode());
		containerDetails.setCarrierId(mailAcceptanceVo.getCarrierId());
		containerDetails.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
		containerDetails.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
		containerDetails.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
		containerDetails.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
		containerDetails.setPol(mailAcceptanceVo.getPol());
		containerDetails.setPou(null);
		containerDetails.setAcceptedFlag(MailConstantsVO.FLAG_YES);
		containerDetails.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ARR);
		containerDetails.setArrivedStatus(MailConstantsVO.FLAG_NO);
		containerDetails.setAssignmentDate(localDateUtil.getLocalDate(mailAcceptanceVo.getPol(), true));
		//containerDetails.setAssignmentDate(
				//new com.ibsplc.icargo.framework.util.time.LocalDate(mailAcceptanceVo.getPol(), Location.ARP, true));
		containerDetails.setFlightDate(null);
		containerDetails.setOffloadFlag(MailConstantsVO.FLAG_NO);
		containerDetails.setAssignedUser(mailAcceptanceVo.getAcceptedUser());

		containerDetails.setAssignmentDate(localDateUtil.getLocalDate(containerDetails.getPol(), true));
		updateOperationalFlagForContainer(containerDetails);
		log.debug(ENTITY + " : " + "updateContainerDetailsForInventory" + " Exiting");
	}

	/** 
	* @author a-1936
	* @param containerDetails
	* @throws SystemException
	* @throws ContainerAssignmentException
	*/
	private void updateOperationalFlagForContainer(ContainerDetailsVO containerDetails) {
		log.debug(ENTITY + " : " + "validateContainersForTransfer" + " Entering");
		ContainerAssignmentVO containerAssignmentVO = Container.findContainerAssignmentForArrival(
				containerDetails.getCompanyCode(), containerDetails.getContainerNumber(), containerDetails.getPol());
		if (containerAssignmentVO != null) {
			if (containerAssignmentVO.getFlightSequenceNumber() > 0) {
				containerDetails.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
				containerDetails.setContainerOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
			} else {
				if (containerAssignmentVO.getCarrierId() == containerDetails.getCarrierId()) {
					containerDetails.setContainerOperationFlag(ContainerDetailsVO.OPERATION_FLAG_UPDATE);
					if (MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getAcceptanceFlag())) {
						containerDetails.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_UPDATE);
					} else {
						containerDetails.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
					}
				} else {
					containerDetails.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
					containerDetails.setContainerOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
				}
			}
		} else {
			containerDetails.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
			containerDetails.setContainerOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
		}
		log.debug(ENTITY + " : " + "validateContainersForTransfer" + " Exiting");
	}

	/** 
	* @author A-1936 Added By Karthick V as the part of the NCA Mail TrackingCR
	* @throws SystemException
	* @throws ContainerAssignmentException
	*/
	private Collection<ContainerDetailsVO> constructContainerForInventoryForArrival(MailArrivalVO mailArrivalVO,
			MailAcceptanceVO mailAcceptanceVo, Map<String, Collection<MailbagVO>> mailBagsForInventoryMap,
			Map<String, Collection<DespatchDetailsVO>> despacthesForInventoryMap) {
		Collection<String> containerKeys = new ArrayList<String>();
		Collection<MailbagVO> mailBags = null;
		Collection<DespatchDetailsVO> despacthes = null;
		ContainerDetailsVO containerDetailsVo = null;
		Collection<ContainerDetailsVO> actualArrivedContainers = mailArrivalVO.getContainerDetails();
		Collection<ContainerDetailsVO> containersForArrival = mailAcceptanceVo.getContainerDetails();
		if (containersForArrival == null) {
			containersForArrival = new ArrayList<ContainerDetailsVO>();
		}
		if (mailBagsForInventoryMap != null && mailBagsForInventoryMap.size() > 0) {
			for (String key : mailBagsForInventoryMap.keySet()) {
				if (!containerKeys.contains(key)) {
					containerKeys.add(key);
				}
			}
		}
		if (despacthesForInventoryMap != null && despacthesForInventoryMap.size() > 0) {
			for (String key : despacthesForInventoryMap.keySet()) {
				if (!containerKeys.contains(key)) {
					containerKeys.add(key);
				}
			}
		}
		if (containerKeys.size() > 0) {
			for (String containerKey : containerKeys) {
				containerDetailsVo = new ContainerDetailsVO();
				String[] splitStr = containerKey.split(MailConstantsVO.ARPULD_KEYSEP);
				containerDetailsVo.setContainerNumber(splitStr[0]);
				containerDetailsVo.setContainerType(splitStr[1]);
				if (splitStr.length > 2 && "Y".equals(splitStr[2])) {
					containerDetailsVo.setPaBuiltFlag(splitStr[2]);
				}
				if (actualArrivedContainers != null && actualArrivedContainers.size() > 0) {
					for (ContainerDetailsVO arrivedContDtlVO : actualArrivedContainers) {
						if ((arrivedContDtlVO.getContainerNumber() != null && arrivedContDtlVO.getContainerNumber()
								.equals(containerDetailsVo.getContainerNumber()))
								&& (arrivedContDtlVO.getContainerType() != null && arrivedContDtlVO.getContainerType()
										.equals(containerDetailsVo.getContainerType()))) {
							containerDetailsVo.setDestination(arrivedContDtlVO.getDestination());
							if (arrivedContDtlVO.getPaBuiltFlag() != null
									&& arrivedContDtlVO.getPaBuiltFlag().equals(containerDetailsVo.getPaBuiltFlag())) {
								containerDetailsVo.setPaCode(arrivedContDtlVO.getPaCode());
								containerDetailsVo.setContainerJnyId(arrivedContDtlVO.getContainerJnyId());
							}
						}
					}
				}
				updateContainerDetailsForInventory(containerDetailsVo, mailAcceptanceVo);
				containerDetailsVo.setMailDetails(new ArrayList<MailbagVO>());
				containerDetailsVo.setDesptachDetailsVOs(new ArrayList<DespatchDetailsVO>());
				containerDetailsVo.setDsnVOs(new ArrayList<DSNVO>());
				mailBags = mailBagsForInventoryMap.get(containerKey);
				if (mailBags != null && mailBags.size() > 0) {
					updateDSNsForMailBags(mailBags, containerDetailsVo);
				}
				despacthes = despacthesForInventoryMap.get(containerKey);
				if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVo.getContainerType())) {
					containerDetailsVo.setContainerType(MailConstantsVO.BULK_TYPE);
					containerDetailsVo.setDestination(null);
					containerDetailsVo.setContainerNumber(MailConstantsVO.CONST_BULK_ARR_ARP
							.concat(MailConstantsVO.SEPARATOR).concat(containerDetailsVo.getCarrierCode()));
					if (containersForArrival != null && containersForArrival.size() > 0) {
						boolean isExisting = false;
						for (ContainerDetailsVO cntDtlVO : containersForArrival) {
							if (cntDtlVO.getContainerNumber().equals(containerDetailsVo.getContainerNumber())) {
								if (containerDetailsVo.getMailDetails() != null
										&& containerDetailsVo.getMailDetails().size() > 0) {
									if (cntDtlVO.getMailDetails() == null) {
										cntDtlVO.setMailDetails(new ArrayList<MailbagVO>());
									}
									cntDtlVO.getMailDetails().addAll(containerDetailsVo.getMailDetails());
								}
								if (containerDetailsVo.getDesptachDetailsVOs() != null
										&& containerDetailsVo.getDesptachDetailsVOs().size() > 0) {
									if (cntDtlVO.getDesptachDetailsVOs() == null) {
										cntDtlVO.setDesptachDetailsVOs(new ArrayList<DespatchDetailsVO>());
									}
									cntDtlVO.getDesptachDetailsVOs().addAll(containerDetailsVo.getDesptachDetailsVOs());
								}
								if (containerDetailsVo.getDsnVOs() != null
										&& containerDetailsVo.getDsnVOs().size() > 0) {
									if (cntDtlVO.getDsnVOs() == null) {
										cntDtlVO.setDsnVOs(new ArrayList<DSNVO>());
									}
									cntDtlVO.getDsnVOs().addAll(containerDetailsVo.getDsnVOs());
								}
								isExisting = true;
								break;
							}
						}
						if (!isExisting) {
							updateOperationalFlagForContainer(containerDetailsVo);
							containersForArrival.add(containerDetailsVo);
						}
					} else {
						updateOperationalFlagForContainer(containerDetailsVo);
						containersForArrival.add(containerDetailsVo);
					}
				} else {
					if (containersForArrival != null && containersForArrival.size() > 0) {
						boolean isExisting = false;
						for (ContainerDetailsVO cntDtlVO : containersForArrival) {
							if (cntDtlVO.getContainerNumber().equals(containerDetailsVo.getContainerNumber())) {
								if (containerDetailsVo.getMailDetails() != null
										&& containerDetailsVo.getMailDetails().size() > 0) {
									if (cntDtlVO.getMailDetails() == null) {
										cntDtlVO.setMailDetails(new ArrayList<MailbagVO>());
									}
									cntDtlVO.getMailDetails().addAll(containerDetailsVo.getMailDetails());
								}
								if (containerDetailsVo.getDesptachDetailsVOs() != null
										&& containerDetailsVo.getDesptachDetailsVOs().size() > 0) {
									if (cntDtlVO.getDesptachDetailsVOs() == null) {
										cntDtlVO.setDesptachDetailsVOs(new ArrayList<DespatchDetailsVO>());
									}
									cntDtlVO.getDesptachDetailsVOs().addAll(containerDetailsVo.getDesptachDetailsVOs());
								}
								if (containerDetailsVo.getDsnVOs() != null
										&& containerDetailsVo.getDsnVOs().size() > 0) {
									if (cntDtlVO.getDsnVOs() == null) {
										cntDtlVO.setDsnVOs(new ArrayList<DSNVO>());
									}
									cntDtlVO.getDsnVOs().addAll(containerDetailsVo.getDsnVOs());
								}
								isExisting = true;
								break;
							}
						}
						if (!isExisting) {
							containersForArrival.add(containerDetailsVo);
						}
					} else {
						containersForArrival.add(containerDetailsVo);
					}
				}
			}
		}
		return containersForArrival;
	}

	/** 
	* a-1936 Added By Karthick V as the part of the NCA Mail Tracking Cr
	* @return
	* @throws SystemException 
	*/
	private void updateDSNsForMailBags(Collection<MailbagVO> mailBags, ContainerDetailsVO containerDetails) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		Map<String, Collection<MailbagVO>> dsnMailMap = new HashMap<String, Collection<MailbagVO>>();
		Map<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
		Collection<DSNVO> dsnVos = new ArrayList<DSNVO>();
		for (MailbagVO mailbagVO : mailBags) {
			String dsnPK = constructDSNPKFromMail(mailbagVO);
			mailbagVO.setCarrierCode(containerDetails.getCarrierCode());
			mailbagVO.setCarrierId(containerDetails.getCarrierId());
			mailbagVO.setFinalDestination(null);
			mailbagVO.setFlightNumber(containerDetails.getFlightNumber());
			mailbagVO.setFlightSequenceNumber(containerDetails.getFlightSequenceNumber());
			mailbagVO.setSegmentSerialNumber(containerDetails.getSegmentSerialNumber());
			mailbagVO.setPou(null);
			mailbagVO.setOperationalFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
			mailbagVO.setScannedDate(localDateUtil.getLocalDate(containerDetails.getPol(), true));
			mailbagVO.setScannedPort(containerDetails.getPol());
			String containerNumber = MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR)
					.concat(mailbagVO.getCarrierCode());
			mailbagVO.setContainerNumber(containerNumber);
			mailbagVO.setUldNumber(containerDetails.getContainerNumber());
			Collection<MailbagVO> dsnMails = dsnMailMap.get(dsnPK);
			DSNVO dsnVo = dsnMap.get(dsnPK);
			if (dsnMails == null) {
				dsnMails = new ArrayList<MailbagVO>();
				dsnMailMap.put(dsnPK, dsnMails);
				dsnVo = new DSNVO();
				dsnMap.put(dsnPK, dsnVo);
				dsnVos.add(dsnVo);
				updateDSNVOFromMailbag(dsnVo, mailbagVO, false);
			}
			if (ContainerDetailsVO.OPERATION_FLAG_INSERT.equals(containerDetails.getOperationFlag())) {
				dsnVo.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
			} else {
				dsnVo.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_UPDATE);
			}
			dsnMails.add(mailbagVO);
			updateDSNVOFromMailbag(dsnVo, mailbagVO, true);
		}
		containerDetails.getMailDetails().addAll(mailBags);
		containerDetails.getDsnVOs().addAll(dsnVos);
	}

	/** 
	* @author a-1936
	* @param dsnVO
	* @param mailBag
	* @param isUpdate
	* @return
	* @throws SystemException 
	*/
	private DSNVO updateDSNVOFromMailbag(DSNVO dsnVO, MailbagVO mailBag, boolean isUpdate) {
		if (!isUpdate) {
			dsnVO.setCompanyCode(mailBag.getCompanyCode());
			dsnVO.setDsn(mailBag.getDespatchSerialNumber());
			dsnVO.setOriginExchangeOffice(mailBag.getOoe());
			dsnVO.setDestinationExchangeOffice(mailBag.getDoe());
			dsnVO.setMailClass(mailBag.getMailClass());
			dsnVO.setMailSubclass(mailBag.getMailSubclass());
			dsnVO.setMailCategoryCode(mailBag.getMailCategoryCode());
			dsnVO.setYear(mailBag.getYear());
			dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
		} else {
			dsnVO.setBags(dsnVO.getBags() + 1);
			try {
				if (Objects.isNull(dsnVO.getWeight())) {
					dsnVO.setWeight(mailBag.getWeight());
				} else {
					dsnVO.setWeight(dsnVO.getWeight().add(mailBag.getWeight()));
				}
			} finally {
			}
		}
		return dsnVO;
	}

	/** 
	* Added By Karthick V as the part of the NCA Mail Tracking CR For all MailBags that has been Arrived But Not Transfered or Delivered at the Port... All those MailBags and the Despacthes are to be moved to the Inventory... For the Corresponding Carrier ... TODO First construct the Mail Acceptance VO from the Mail Arrival Vo Then get all the Containers and its Details ...
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	* @throws DuplicateMailBagsException 
	*/
	private void saveInventoryDetailsForArrival(MailAcceptanceVO mailAcceptanceVO)
			throws InventoryForArrivalFailedException, ULDDefaultsProxyException, CapacityBookingProxyException,
			MailBookingException, DuplicateMailBagsException {
		log.debug(ENTITY + " : " + "saveInventoryDetailsForArrival" + " Entering");
		log.debug("" + "mailaxp" + " " + mailAcceptanceVO);
		try {
			ContextUtil.getInstance().getBean(MailAcceptance.class).saveInventoryDetailsForArrival(mailAcceptanceVO);
		} catch (FlightClosedException ex) {
			throw new InventoryForArrivalFailedException(ex);
		} catch (ContainerAssignmentException ex) {
			throw new InventoryForArrivalFailedException(ex);
		} catch (InvalidFlightSegmentException ex) {
			throw new InventoryForArrivalFailedException(ex);
		} catch (MailDefaultStorageUnitException ex) {
			throw new InventoryForArrivalFailedException(ex);
		}
		log.debug(ENTITY + " : " + "saveInventoryDetailsForArrival" + " Exiting");
	}

	private String constructDSNPKFromMail(MailbagVO mailbagVO) {
		log.debug("MailAcceptance" + " : " + "constructDSNPK" + " Entering");
		StringBuilder dsnPK = new StringBuilder();
		dsnPK.append(mailbagVO.getCompanyCode());
		dsnPK.append(mailbagVO.getDespatchSerialNumber());
		dsnPK.append(mailbagVO.getMailSubclass());
		dsnPK.append(mailbagVO.getMailCategoryCode());
		dsnPK.append(mailbagVO.getOoe());
		dsnPK.append(mailbagVO.getDoe());
		dsnPK.append(mailbagVO.getYear());
		log.debug("MailAcceptance" + " : " + "constructDSNPK" + " Exiting");
		return dsnPK.toString();
	}

	/** 
	* @author a-1936Added By Karthick V to remove all  the despacthes that are being delivered to be removed from the Inventory ..
	* @param despatchDetailsVos
	* @throws SystemException
	*/
	private void removeDeliveredDespatchesFromInventory(Collection<DespatchDetailsVO> despatchDetailsVos) {
		Collection<DespatchDetailsVO> despatchDetailsVosForReassign = updateDespacthesForInventoryRemoval(
				despatchDetailsVos);
		new ReassignController().reassignDSNsFromDestination(despatchDetailsVosForReassign);
	}

	/** 
	* @author a-2553Added By Paulson to remove all  the empty containers that are being delivered to be removed from the Inventory ..
	* @param deliveredContainers
	* @throws SystemException
	*/
	private void removeDeliveredContainersFromInventory(Collection<ContainerDetailsVO> deliveredContainers)
			throws FinderException {
		Collection<ContainerVO> containersForReassign = updateContainersForInventoryRemoval(deliveredContainers);
		new ReassignController().reassignContainerFromDestToDest(containersForReassign, null);
	}

	private void removeDeliveredMailbagsFromInventory(Collection<MailbagVO> mailbagsAlreadyArrived) {
		log.debug(ENTITY + " : " + "removeDeliveredMailbagsFromInventory" + " Entering");
		Collection<MailbagVO> mailBagsForRemoval = updateMailBagForInventoryRemoval(mailbagsAlreadyArrived);
		new ReassignController().reassignMailFromDestination(mailBagsForRemoval);
		log.debug(ENTITY + " : " + "removeDeliveredMailbagsFromInventory" + " Exiting");
	}

	/** 
	* Utilty for finding syspar Mar 23, 2007, A-1739
	* @param syspar
	* @return
	* @throws SystemException
	*/
	private String findSystemParameterValue(String syspar) {
		NeoMastersServiceUtils neoMastersServiceUtils = ContextUtil.getInstance().
				getBean(NeoMastersServiceUtils.class);
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

	private void updateOperationalULDs(MailArrivalVO mailArrivalVO) {
		OperationsFltHandlingProxy operationsFltHandlingProxy = ContextUtil.getInstance()
				.getBean(OperationsFltHandlingProxy.class);
		log.debug(ENTITY + " : " + "updateOperationalULDs" + " Entering");
		Collection<ContainerDetailsVO> containerDetails = mailArrivalVO.getContainerDetails();
		Collection<UldInFlightVO> uldInFlightVOs = new ArrayList<UldInFlightVO>();
		if (containerDetails != null && containerDetails.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetails) {
				if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())
						&& MailConstantsVO.OPERATION_FLAG_UPDATE.equals(containerDetailsVO.getOperationFlag())) {
					uldInFlightVOs.add(constructUldInFlightVO(containerDetailsVO));
				}
			}
		}
		if (uldInFlightVOs != null && uldInFlightVOs.size() > 0) {
			operationsFltHandlingProxy.saveOperationalULDsInFlight(uldInFlightVOs);
		}
		log.debug(ENTITY + " : " + "updateOperationalULDs" + " Exiting");
	}

	private UldInFlightVO constructUldInFlightVO(ContainerDetailsVO containerDetailsVO) {
		UldInFlightVO uldInFlightVO = new UldInFlightVO();
		uldInFlightVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		uldInFlightVO.setUldNumber(containerDetailsVO.getContainerNumber());
		uldInFlightVO.setCarrierId(containerDetailsVO.getCarrierId());
		if (containerDetailsVO.getFlightSequenceNumber() > 0) {
			uldInFlightVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			uldInFlightVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
			uldInFlightVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		}
		uldInFlightVO.setPou(containerDetailsVO.getPou());
		uldInFlightVO.setAirportCode(containerDetailsVO.getPol());
		uldInFlightVO.setFlightDirection(MailConstantsVO.OPERATION_INBOUND);
		return uldInFlightVO;
	}

	/** 
	* @param assignedFlightSegmentPK
	* @param segmentContainers
	* @return
	*/
	private AssignedFlightSegmentVO constructAssignedFlightSegVOForContainer(
			AssignedFlightSegmentPK assignedFlightSegmentPK, Collection<ContainerDetailsVO> segmentContainers) {
		AssignedFlightSegmentVO asgFltSegVO = new AssignedFlightSegmentVO();
		asgFltSegVO.setCarrierId(assignedFlightSegmentPK.getCarrierId());
		asgFltSegVO.setCompanyCode(assignedFlightSegmentPK.getCompanyCode());
		asgFltSegVO.setFlightNumber(assignedFlightSegmentPK.getFlightNumber());
		asgFltSegVO.setFlightSequenceNumber(assignedFlightSegmentPK.getFlightSequenceNumber());
		asgFltSegVO.setSegmentSerialNumber(assignedFlightSegmentPK.getSegmentSerialNumber());
		String pou = null;
		String pol = null;
		for (ContainerDetailsVO containerDetailsVO : segmentContainers) {
			pou = containerDetailsVO.getPou();
			pol = containerDetailsVO.getPol();
			break;
		}
		asgFltSegVO.setPol(pol);
		asgFltSegVO.setPou(pou);
		return asgFltSegVO;
	}

	/** 
	* A-1739
	* @param mailArrivalVO
	* @param paBuiltContainers
	* @return
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	*/
	private Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> groupContainersForSegmentForArrival(
			MailArrivalVO mailArrivalVO, Collection<ContainerDetailsVO> paBuiltContainers)
			throws InvalidFlightSegmentException {
		Collection<ContainerDetailsVO> containerDetails = mailArrivalVO.getContainerDetails();
		Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> segConMap = new HashMap<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>>();
		if (containerDetails != null && containerDetails.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetails) {
				AssignedFlightSegmentPK assignedFlightSegPK = null;
				boolean isUpdated = false;
				assignedFlightSegPK = constructAssignedFlightSegPK(containerDetailsVO);
				if (assignedFlightSegPK.getSegmentSerialNumber() == 0) {
					assignedFlightSegPK = constructAssignedFlightSegmentPKForArrival(containerDetailsVO, mailArrivalVO);
					containerDetailsVO.setCompanyCode(assignedFlightSegPK.getCompanyCode());
					containerDetailsVO.setCarrierId(assignedFlightSegPK.getCarrierId());
					containerDetailsVO.setFlightNumber(assignedFlightSegPK.getFlightNumber());
					containerDetailsVO.setFlightSequenceNumber(assignedFlightSegPK.getFlightSequenceNumber());
					containerDetailsVO.setSegmentSerialNumber(assignedFlightSegPK.getSegmentSerialNumber());
					containerDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
					containerDetailsVO.setPou(mailArrivalVO.getAirportCode());
				}
				if (containerDetailsVO.getOperationFlag() != null) {
					isUpdated = true;
				}
				if (isUpdated) {
					Collection<ContainerDetailsVO> containersForSegment = segConMap.get(assignedFlightSegPK);
					if (containersForSegment == null) {
						containersForSegment = new ArrayList<ContainerDetailsVO>();
						segConMap.put(assignedFlightSegPK, containersForSegment);
					}
					containersForSegment.add(containerDetailsVO);
					if (MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())) {
						paBuiltContainers.add(containerDetailsVO);
					}
				}
			}
		}
		return segConMap;
	}

	/** 
	* @author A-1739
	* @param mailArrivalVO
	* @return
	* @throws SystemException
	* @throws MailbagIncorrectlyDeliveredException
	* @throws DuplicateMailBagsException
	*/
	private Map<String, DSNVO> checkForIncorrectArrivalDetails(MailArrivalVO mailArrivalVO,
			Collection<DespatchDetailsVO> despatchDetailsForInventoryRemoval, Collection<MailbagVO> expMailbags,
			Collection<MailbagVO> newArrivedMailbags, Collection<MailbagVO> deliveredMailBagsForMonitorSLA,
			Collection<MailbagVO> deliveredArrivedMails, Collection<ContainerDetailsVO> deliveredContainers)
			throws MailbagIncorrectlyDeliveredException, DuplicateMailBagsException, MailOperationsBusinessException {
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug(ENTITY + " : " + "checkForIncorrectlyDeliveredMailbags" + " Entering");
		Map<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
		Collection<ContainerDetailsVO> containersToRem = new ArrayList<ContainerDetailsVO>();
		String companyCode = mailArrivalVO.getCompanyCode();
		String deliveredPort = mailArrivalVO.getAirportCode();
		boolean isScanned = mailArrivalVO.isScanned();
		boolean deliveryChkNeeded = mailArrivalVO.isDeliveryCheckNeeded();
		Collection<ContainerDetailsVO> containers = mailArrivalVO.getContainerDetails();
		String POST_DATA_CAPTURE = "PDC";
		if (containers != null && containers.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containers) {
				if (containerDetailsVO.getOperationFlag() != null) {
					if (mailArrivalVO.isScanned()) {
						containerDetailsVO.setCompanyCode(mailArrivalVO.getCompanyCode());
						if (ContainerDetailsVO.OPERATION_FLAG_INSERT.equals(containerDetailsVO.getOperationFlag())) {
							try {
								checkContainerAssignmentAtPol(containerDetailsVO);
							} catch (ContainerAssignmentException ex) {
								if (containerDetailsVO.getMailDetails() != null) {
									updateMailbagVOsWithErr(containerDetailsVO.getMailDetails(),
											MailConstantsVO.ERR_MSG_NEW_ULD_ASG);
									expMailbags.addAll(containerDetailsVO.getMailDetails());
								}
								containersToRem.add(containerDetailsVO);
								continue;
							}
						}
					}
					Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
					if (dsnVOs == null || dsnVOs.size() == 0) {
						if ("U".equals(containerDetailsVO.getOperationFlag())
								&& "Y".equals(containerDetailsVO.getDeliveredStatus())) {
							deliveredContainers.add(containerDetailsVO);
						}
					}
					if (dsnVOs != null && dsnVOs.size() > 0) {
						for (DSNVO dsnVO : dsnVOs) {
							log.debug("" + "The DSN VO" + " " + dsnVO);
							if (dsnVO.getOperationFlag() != null) {
								String dsnPK = constructDSNPKFrmDSNVO(dsnVO);
								DSNVO dsnMstVO = dsnMap.get(dsnPK);
								if (dsnMstVO == null) {
									dsnMstVO = copyDSNVO(dsnVO);
									dsnMap.put(dsnPK, dsnMstVO);
									Collection<DSNAtAirportVO> dsnAtAirports = new ArrayList<DSNAtAirportVO>();
									dsnMstVO.setDsnAtAirports(dsnAtAirports);
									DSNAtAirportVO dsnAtAirportVO = new DSNAtAirportVO();
									dsnAtAirportVO.setAirportCode(mailArrivalVO.getAirportCode());
									dsnAtAirportVO.setMailClass(dsnVO.getMailClass());
									dsnAtAirports.add(dsnAtAirportVO);
									Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
									dsnMstVO.setMailbags(mailbagVOs);
								}
								updateDSNAtAirportVOForArrival(dsnMstVO, dsnVO);
								if (dsnVO.getReceivedBags() > dsnVO.getBags()) {
									dsnMstVO.setBags(dsnVO.getReceivedBags() - dsnVO.getBags());
									try {
										if (dsnVO.getReceivedWeight() != null)
											if (dsnVO.getWeight() != null) {
												dsnMstVO.setWeight(dsnVO.getReceivedWeight().subtract(dsnVO.getWeight()));
											} else {
												dsnMstVO.setWeight(dsnVO.getReceivedWeight());
											}
									} finally {
									}
								}
							}
						}
					}
					Collection<MailbagVO> duplicateMailbags = new ArrayList<MailbagVO>();
					Collection<MailbagVO> incorrectlyDeliveredMails = new ArrayList<MailbagVO>();
					Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
					if (mailbags != null && mailbags.size() > 0) {
						for (MailbagVO mailbagVO : mailbags) {
							if (mailbagVO.getOperationalFlag() != null || "PDC".equals(mailbagVO.getActionMode())) {
								Collection<MailbagHistoryVO> mailhistories = new ArrayList<MailbagHistoryVO>();
								mailhistories = (mailbagVO.getMailbagHistories() != null
										&& !mailbagVO.getMailbagHistories().isEmpty())
												? mailbagVO.getMailbagHistories()
												: (ArrayList<MailbagHistoryVO>) Mailbag
														.findMailbagHistoriesWithoutCarditDetails(
																mailbagVO.getCompanyCode(), mailbagVO.getMailbagId());
								if (mailhistories != null && mailhistories.size() > 0) {
									for (MailbagHistoryVO mailbaghistoryvo : mailhistories) {
										if (mailbagVO.getFlightNumber() != null
												&& mailbagVO.getFlightSequenceNumber() > 0) {
											if (MailConstantsVO.MAIL_STATUS_ARRIVED
													.equals(mailbaghistoryvo.getMailStatus())
													&& mailbaghistoryvo.getScannedPort()
															.equals(mailbagVO.getScannedPort())) {
												if (!(mailbaghistoryvo.getFlightNumber()
														.equals(mailbagVO.getFlightNumber()))
														|| mailbaghistoryvo.getFlightSequenceNumber() != mailbagVO
																.getFlightSequenceNumber()) {
													if (MailConstantsVO.OPERATION_FLAG_UPDATE
															.equals(mailbagVO.getOperationalFlag())
															|| POST_DATA_CAPTURE.equals(mailbagVO.getActionMode())
															|| (MailConstantsVO.OPERATION_FLAG_INSERT
																	.equals(mailbagVO.getOperationalFlag())
																	&& !mailArrivalVO.isFlightChange())) {
													}
												} else if ((mailbaghistoryvo.getFlightNumber() != null
														&& mailbaghistoryvo.getFlightNumber()
																.equals(mailbagVO.getFlightNumber()))
														&& mailbaghistoryvo.getFlightSequenceNumber() == mailbagVO
																.getFlightSequenceNumber()
														&& (mailbaghistoryvo.getContainerNumber() != null
																&& !mailbaghistoryvo.getContainerNumber()
																		.equals(mailbagVO.getContainerNumber()))) {
													if (MailConstantsVO.OPERATION_FLAG_UPDATE
															.equals(mailbagVO.getOperationalFlag())
															|| POST_DATA_CAPTURE.equals(mailbagVO.getActionMode())
															|| (MailConstantsVO.OPERATION_FLAG_INSERT
																	.equals(mailbagVO.getOperationalFlag())
																	&& !mailArrivalVO.isFlightChange())) {
														throw new DuplicateMailBagsException(
																DuplicateMailBagsException.MAILBAG_ALREADY_ARRIVAL_INSAMEFLIGHT_DIFF_CNTAINER_EXCEPTION,
																new Object[] { mailbagVO.getMailbagId(),
																		mailbaghistoryvo.getContainerNumber() });
													}
												}
											}
										}
									}
								}
							}
							if (mailbagVO.getOperationalFlag() != null) {
								boolean isNew = false;
								Mailbag mailbag = null;
								try {
									mailbag = Mailbag.find(constructMailbagPK(mailbagVO));
								} catch (FinderException exception) {
									isNew = true;
								}
								if (mailbag != null) {
									if (mailbagVO.getMailCompanyCode() != null) {
										if ((!mailbagVO.getMailCompanyCode().equals(mailbag.getMailCompanyCode()))) {
											//TODO:Audit to be implemented as part of neo coding
											/*
											MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,
													MailbagAuditVO.SUB_MOD_OPERATIONS, MailbagAuditVO.ENTITY_MAILBAG);
											mailbagAuditVO = (MailbagAuditVO) AuditUtils
													.populateAuditDetails(mailbagAuditVO, mailbag, false);*/
											mailbag.setMailCompanyCode(mailbagVO.getMailCompanyCode());
											/*mailbagAuditVO = (MailbagAuditVO) AuditUtils
													.populateAuditDetails(mailbagAuditVO, mailbag, false);
											mailbagAuditVO.setActionCode("UPDATEMAL");
											mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
											mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
											mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
											mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
											mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
											mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
											mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
											mailbagAuditVO.setYear(mailbagVO.getYear());
											LoginProfile logonAttributes = contextUtil.callerLoginProfile();
											mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
											StringBuffer additionalInfo = new StringBuffer();
											additionalInfo.append("Company code ").append("updated for mailbag	")
													.append(mailbagVO.getMailbagId());
											mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
											AuditUtils.performAudit(mailbagAuditVO);*/
										} else {
											mailbag.setMailCompanyCode(mailbagVO.getMailCompanyCode());
										}
									}
								}
								mailbagVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
								mailbagVO.setMailSource(mailArrivalVO.getMailSource());
								mailbagVO.setMailbagDataSource(mailArrivalVO.getMailDataSource());
								MailbagVO currentMailbagVO = checkForDuplicateArrival(mailbagVO, isScanned);
								String dsnPK = constructDSNPKForMailbag(mailbagVO);
								DSNVO dsnVO = dsnMap.get(dsnPK);
								if (dsnVO != null) {
									if (isScanned && mailbagVO.getErrorType() != null) {
										duplicateMailbags.add(mailbagVO);
										removeDuplicatebagsWeight(containerDetailsVO, dsnVO, mailbagVO);
										continue;
									}
									dsnVO.getMailbags().add(mailbagVO);
								}
								String poaCode = null;
								if (mailbag != null && mailbag.getPaCode() != null) {
									poaCode = mailbag.getPaCode();
								} else {
									OfficeOfExchangeVO originOfficeOfExchangeVO;
									MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
									originOfficeOfExchangeVO = mailController
											.validateOfficeOfExchange(companyCode, mailbagVO.getOoe());
									poaCode = originOfficeOfExchangeVO.getPoaCode();
								}
								if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())
										&& !MailConstantsVO.FLAG_YES.equals(mailbagVO.getTransferFlag())) {
									String latestStatus = null;
									if (currentMailbagVO != null) {
										latestStatus = currentMailbagVO.getLatestStatus();
									}
									if (currentMailbagVO == null
											|| !currentMailbagVO.getScannedPort().equals(mailbagVO.getScannedPort())
											|| !MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
										newArrivedMailbags.add(mailbagVO);
									}
									if (!MailConstantsVO.MAIL_STATUS_DELIVERED.equals(latestStatus)
											&& MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
										if (deliveredPort.equals(mailbagVO.getDestination()) ? true
												: isValidDeliveryAirport(mailbagVO.getDoe(), companyCode, deliveredPort,
														null, MailConstantsVO.RESDIT_DELIVERED, poaCode,
														mailbagVO.getConsignmentDate())) {
											if (deliveredMailBagsForMonitorSLA != null) {
												deliveredMailBagsForMonitorSLA.add(mailbagVO);
											}
											if (currentMailbagVO != null && currentMailbagVO.getScannedPort()
													.equals(mailbagVO.getScannedPort())) {
												deliveredArrivedMails.add(mailbagVO);
											}
										} else {
											if (deliveryChkNeeded) {
												mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
												mailbagVO.setErrorDescription(MailConstantsVO.INCORRECT_DLV);
												incorrectlyDeliveredMails.add(mailbagVO);
											}
										}
									}
								}
								if (("U").equals(mailbagVO.getOperationalFlag()) && mailArrivalVO.isDeliveryNeeded()) {
									if (!MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
										if (deliveredPort.equals(mailbagVO.getDestination()) ? true
												: isValidDeliveryAirport(mailbagVO.getDoe(), companyCode, deliveredPort,
														null, MailConstantsVO.RESDIT_DELIVERED, poaCode,
														mailbagVO.getConsignmentDate())) {
											if (deliveredMailBagsForMonitorSLA != null) {
												deliveredMailBagsForMonitorSLA.add(mailbagVO);
											}
											if (currentMailbagVO != null && currentMailbagVO.getScannedPort()
													.equals(mailbagVO.getScannedPort())) {
												deliveredArrivedMails.add(mailbagVO);
											}
										} else {
											incorrectlyDeliveredMails.add(mailbagVO);
										}
									}
								}
							}
						}
						if (duplicateMailbags.size() > 0) {
							mailbags.removeAll(duplicateMailbags);
							removeArrivalExceptionPieces(containerDetailsVO, duplicateMailbags);
							expMailbags.addAll(duplicateMailbags);
						}
						if (incorrectlyDeliveredMails.size() > 0) {
							if (isScanned) {
								expMailbags.addAll(incorrectlyDeliveredMails);
								mailbags.removeAll(incorrectlyDeliveredMails);
								removeArrivalExceptionPieces(containerDetailsVO, incorrectlyDeliveredMails);
							} else {
								throw new MailbagIncorrectlyDeliveredException(
										MailbagIncorrectlyDeliveredException.MAILBAG_INCORRECTLY_DELIVERED,
										new String[] { deliveredPort });
							}
						}
					}
					Collection<DespatchDetailsVO> incorrectlyDeliveredDespatches = new ArrayList<DespatchDetailsVO>();
					Collection<DespatchDetailsVO> despatches = containerDetailsVO.getDesptachDetailsVOs();
					if (despatches != null && despatches.size() > 0) {
						for (DespatchDetailsVO despatchDetailsVO : despatches) {
							if (despatchDetailsVO.getOperationalFlag() != null) {
								despatchDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
								if (despatchDetailsVO.getDeliveredBags() != despatchDetailsVO.getPrevDeliveredBags()
										|| (despatchDetailsVO.getDeliveredWeight() != despatchDetailsVO
												.getPrevDeliveredWeight())) {
									OfficeOfExchangeVO originOfficeOfExchangeVO;
									originOfficeOfExchangeVO = new MailController().validateOfficeOfExchange(
											companyCode, despatchDetailsVO.getOriginOfficeOfExchange());
									String poaCode = originOfficeOfExchangeVO.getPoaCode();
									if (deliveredPort.equals(despatchDetailsVO.getDestination()) ? false
											: !isValidDeliveryAirport(
													despatchDetailsVO.getDestinationOfficeOfExchange(), companyCode,
													deliveredPort, null, MailConstantsVO.RESDIT_DELIVERED, poaCode,
													despatchDetailsVO.getConsignmentDate())) {
										incorrectlyDeliveredDespatches.add(despatchDetailsVO);
									} else {
										log.debug("Valid Delivery Port");
										if (despatchDetailsForInventoryRemoval != null) {
											if (despatchDetailsVO.getPrevReceivedBags() > 0
													&& (despatchDetailsVO.getPrevReceivedBags() > despatchDetailsVO
															.getPrevDeliveredBags())) {
												log.debug("Valid Delivery Port and despatches collected ");
												despatchDetailsForInventoryRemoval.add(despatchDetailsVO);
											} else {
												log.debug("No ----------------despatches collected ");
											}
										}
									}
								}
							}
						}
						if (incorrectlyDeliveredDespatches.size() > 0) {
							throw new MailbagIncorrectlyDeliveredException(
									MailbagIncorrectlyDeliveredException.DESPATCH_INCORRECTLY_DELIVERED,
									new String[] { deliveredPort });
						}
					}
				}
			}
			containers.removeAll(containersToRem);
		}
		log.debug("" + "container  " + " " + containers);
		Collection<String> dsnsToRem = new ArrayList<String>();
		if (dsnMap.size() > 0) {
			for (Map.Entry<String, DSNVO> dsnEntry : dsnMap.entrySet()) {
				DSNVO dsnVO = dsnMap.get(dsnEntry.getKey());
				if (MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())) {
					if (dsnVO.getMailbags().size() == 0) {
						dsnsToRem.add(dsnEntry.getKey());
					}
				}
			}
			for (String dsnPK : dsnsToRem) {
				dsnMap.remove(dsnPK);
			}
		}
		log.debug(ENTITY + " : " + "checkForIncorrectlyDeliveredMailbags" + " Exiting");
		return dsnMap;
	}

	/** 
	* TODO Purpose Oct 9, 2006, a-1739
	* @param mailArrivalVO
	* @return
	*/
	private ScannedMailDetailsVO constructScannedMailDetailsForFlight(MailArrivalVO mailArrivalVO) {
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		scannedMailDetailsVO.setCompanyCode(mailArrivalVO.getCompanyCode());
		scannedMailDetailsVO.setCarrierId(mailArrivalVO.getCarrierId());
		scannedMailDetailsVO.setFlightNumber(mailArrivalVO.getFlightNumber());
		scannedMailDetailsVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
		scannedMailDetailsVO.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
		scannedMailDetailsVO.setFlightDate(mailArrivalVO.getArrivalDate());
		scannedMailDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
		return scannedMailDetailsVO;
	}

	/** 
	* TODO Purpose Dec 7, 2006, a-1739
	* @param mailArrivalVO
	* @throws SystemException
	* @throws FlightClosedException
	*/
	private void checkFlightClosureForArrival(MailArrivalVO mailArrivalVO) throws FlightClosedException {
		AssignedFlightPK inbFlightPK = constructInbFlightPK(mailArrivalVO);
		if (checkInboundFlightClosed(inbFlightPK)) {
			StringBuilder errorString = new StringBuilder();
			errorString.append("checkFlightClosureForArrival scanned info -");
			if (mailArrivalVO.getFlightNumber() != null) {
				errorString.append(mailArrivalVO.getFlightNumber());
				errorString.append(" ");
				errorString.append(" scanned info ends.Closed flight info begins ");
			}
			if (inbFlightPK != null) {
				if (inbFlightPK.getFlightNumber() != null) {
					errorString.append(inbFlightPK.getFlightNumber()).append("-");
				}
				errorString.append(inbFlightPK.getFlightSequenceNumber()).append("-");
				if (inbFlightPK.getAirportCode() != null) {
					errorString.append(inbFlightPK.getAirportCode()).append("-");
				}
			}
			errorString.append("- MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION");
			String finalerrorString = errorString.toString();
			//TODO:Logger
			//errPgExceptionLogger.info(finalerrorString);
			throw new FlightClosedException(FlightClosedException.FLIGHT_STATUS_CLOSED, new String[] {
					new StringBuilder().append(mailArrivalVO.getFlightCarrierCode()).append(" ")
							.append(mailArrivalVO.getFlightNumber()).toString(),
					mailArrivalVO.getArrivalDate() != null
							? mailArrivalVO.getArrivalDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))
							: "" });
		}
	}

	/** 
	* A-1739
	* @param inboundFlightVO
	* @return
	* @throws SystemException
	*/
	private AssignedFlight findOrCreateInboundFlight(AssignedFlightPK inboundFlightPK,
			AssignedFlightVO inboundFlightVO) {
		AssignedFlight inboundFlight = null;
		try {
			inboundFlight = AssignedFlight.find(inboundFlightPK);
		} catch (FinderException exception) {
			inboundFlight = new AssignedFlight(inboundFlightVO);
		}
		return inboundFlight;
	}

	/** 
	* @author a-1936Added By Karthick  V This method is used to Update the Flight  with the Destiantion Details that could be used for the Reassign Dsns From  Destination..
	* @param despatchDetailsVos
	* @return
	* @throws SystemException
	*/
	private Collection<DespatchDetailsVO> updateDespacthesForInventoryRemoval(
			Collection<DespatchDetailsVO> despatchDetailsVos) throws SystemException{
		DespatchDetailsVO despatch = null;
		Collection<DespatchDetailsVO> despatchesForRemoval = new ArrayList<DespatchDetailsVO>();
		for (DespatchDetailsVO desp : despatchDetailsVos) {
			//despatch = new DespatchDetailsVO();
			despatch = mailOperationsMapper.copyDespatchDetailsVO(desp);
			despatch.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
			despatch.setContainerNumber(despatch.getContainerForInventory());
			String containerType = despatch.getContainerType();
			despatch.setContainerType(despatch.getContainerTypeAtAirport());
			if (despatch.getContainerType() == null || despatch.getContainerType().trim().length() == 0) {
				despatch.setContainerType(containerType);
			}
			despatch.setFlightDate(null);
			despatch.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
			despatch.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
			despatch.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
			despatch.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
			despatch.setPou(null);
			despatch.setDestination(null);
			despatch.setAcceptedBags(despatch.getDeliveredBags() - despatch.getPrevDeliveredBags());
			try {
				despatch.setAcceptedWeight(despatch.getDeliveredWeight().subtract(despatch.getPrevDeliveredWeight()));
			} finally {
			}
			despatchesForRemoval.add(despatch);
		}
		log.debug("" + "THE DESPATCHES FOR REMOVAL " + " " + despatchesForRemoval);
		return despatchesForRemoval;
	}

	/** 
	* @author a-1936Added  By  Karthick V as the part of the NCA Mail Tracking CR .. Reset the Flight Pks to Carrier Needed for the Reassign ..
	* @param mailBags
	*/
	private Collection<MailbagVO> updateMailBagForInventoryRemoval(Collection<MailbagVO> mailBags) {
		log.debug("Mail Arrival" + " : " + "updateMailBagForInventoryRemoval" + " Entering");
		Collection<MailbagVO> mailBagsForRemoval = new ArrayList<MailbagVO>();
		MailbagVO mailBagVO = null;
		for (MailbagVO mailBag : mailBags) {
			mailBagVO = new MailbagVO();
			mailBagVO.setCarrierId(mailBag.getCarrierId());
			mailBagVO.setCarrierCode(mailBag.getCarrierCode());
			if (MailConstantsVO.ULD_TYPE.equals(mailBag.getContainerType())) {
				mailBagVO.setContainerNumber(mailBag.getUldNumber());
			} else {
				mailBagVO.setContainerNumber(mailBag.getInventoryContainer());
			}
			mailBagVO.setInventoryContainerType(mailBag.getInventoryContainerType());
			mailBagVO.setFinalDestination(null);
			mailBagVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
			mailBagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
			mailBagVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
			mailBagVO.setPou(null);
			mailBagVO.setCompanyCode(mailBag.getCompanyCode());
			mailBagVO.setMailbagId(mailBag.getMailbagId());
			mailBagVO.setOoe(mailBag.getOoe());
			mailBagVO.setDoe(mailBag.getDoe());
			mailBagVO.setMailCategoryCode(mailBag.getMailCategoryCode());
			mailBagVO.setMailClass(mailBag.getMailClass());
			mailBagVO.setMailSubclass(mailBag.getMailSubclass());
			mailBagVO.setYear(mailBag.getYear());
			mailBagVO.setDespatchSerialNumber(mailBag.getDespatchSerialNumber());
			mailBagVO.setReceptacleSerialNumber(mailBag.getReceptacleSerialNumber());
			mailBagVO.setHighestNumberedReceptacle(mailBag.getHighestNumberedReceptacle());
			mailBagVO.setRegisteredOrInsuredIndicator(mailBag.getRegisteredOrInsuredIndicator());
			mailBagVO.setWeight(mailBag.getWeight());
			mailBagVO.setDamageFlag(mailBag.getDamageFlag());
			mailBagVO.setArrivedFlag(mailBag.getArrivedFlag());
			mailBagVO.setDeliveredFlag(mailBag.getDeliveredFlag());
			mailBagVO.setTransferFlag(mailBag.getTransferFlag());
			mailBagVO.setScannedPort(mailBag.getPou());
			mailBagsForRemoval.add(mailBagVO);
		}
		log.debug("Mail Arrival" + " : " + "updateMailBagForInventoryRemoval" + " Exiting");
		return mailBagsForRemoval;
	}

	/** 
	* @author a-2553Added By Paulson This method is used to Update the Flight  with the Destiantion Details that could be used for the Reassign containers From  Destination..
	* @param deliveredContainers
	* @return
	* @throws SystemException
	*/
	private Collection<ContainerVO> updateContainersForInventoryRemoval(
			Collection<ContainerDetailsVO> deliveredContainers) {
		ContainerVO containerVO = null;
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		for (ContainerDetailsVO cntDetVO : deliveredContainers) {
			containerVO = new ContainerVO();
			containerVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
			containerVO.setFlightDate(null);
			containerVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
			containerVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
			containerVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
			containerVO.setPou(null);
			containerVO.setContainerNumber(cntDetVO.getContainerNumber());
			containerVO.setCompanyCode(cntDetVO.getCompanyCode());
			containerVO.setCarrierCode(cntDetVO.getCarrierCode());
			containerVO.setAssignedPort(cntDetVO.getPol());
			containerVO.setType(cntDetVO.getContainerType());
			containerVO.setFinalDestination(cntDetVO.getDestination());
			containerVO.setRemarks(cntDetVO.getRemarks());
			containerVO.setReassignFlag(cntDetVO.isReassignFlag());
			containerVO.setAssignedUser(cntDetVO.getAssignedUser());
			containerVO.setAssignedDate(cntDetVO.getAssignmentDate());
			containerVO.setPaBuiltFlag(cntDetVO.getPaBuiltFlag());
			containerVO.setArrivedStatus(cntDetVO.getArrivedStatus());
			containerVO.setPaBuiltOpenedFlag(cntDetVO.getArrivedStatus());
			containerVO.setAcceptanceFlag(cntDetVO.getAcceptedFlag());
			containerVO.setOwnAirlineCode(cntDetVO.getOwnAirlineCode());
			containerVOs.add(containerVO);
		}
		log.debug("" + "THE containerVOs FOR REMOVAL " + " " + containerVOs);
		return containerVOs;
	}

	/** 
	* A-1739
	* @param containerDetailsVO
	* @return
	*/
	private AssignedFlightSegmentPK constructAssignedFlightSegPK(ContainerDetailsVO containerDetailsVO) {
		AssignedFlightSegmentPK assignedFlightSegPK = new AssignedFlightSegmentPK();
		assignedFlightSegPK.setCompanyCode(containerDetailsVO.getCompanyCode());
		assignedFlightSegPK.setCarrierId(containerDetailsVO.getCarrierId());
		assignedFlightSegPK.setFlightNumber(containerDetailsVO.getFlightNumber());
		assignedFlightSegPK.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		assignedFlightSegPK.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		return assignedFlightSegPK;
	}

	/** 
	* @author A-1739
	* @param containerDetailsVO
	* @param mailArrivalVO
	* @return
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	*/
	private AssignedFlightSegmentPK constructAssignedFlightSegmentPKForArrival(ContainerDetailsVO containerDetailsVO,
			MailArrivalVO mailArrivalVO) throws InvalidFlightSegmentException {
		int segmentSerialNumber = findFlightSegment(mailArrivalVO.getCompanyCode(), mailArrivalVO.getCarrierId(),
				mailArrivalVO.getFlightNumber(), mailArrivalVO.getFlightSequenceNumber(), containerDetailsVO.getPol(),
				mailArrivalVO.getAirportCode());
		AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
		assignedFlightSegmentPK.setCompanyCode(mailArrivalVO.getCompanyCode());
		assignedFlightSegmentPK.setCarrierId(mailArrivalVO.getCarrierId());
		assignedFlightSegmentPK.setFlightNumber(mailArrivalVO.getFlightNumber());
		assignedFlightSegmentPK.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
		assignedFlightSegmentPK.setSegmentSerialNumber(segmentSerialNumber);
		try {
			AssignedFlightSegment.find(assignedFlightSegmentPK);
		} catch (FinderException ex) {
			AssignedFlightSegmentVO segmentVO = constructAssignedFlightSegmentVOForArrival(containerDetailsVO,
					mailArrivalVO, segmentSerialNumber);
			log.debug("" + "asgFLightSeg not found  creating--" + " " + segmentVO);
			new AssignedFlightSegment(segmentVO);
		}
		return assignedFlightSegmentPK;
	}

	/** 
	* @author A-1739 This method is used to find the FlightSegment
	* @param companyCode
	* @param carrierId
	* @param flightNumber
	* @param flightSequenceNumber
	* @param pol
	* @param pou
	* @return
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	*/
	private int findFlightSegment(String companyCode, int carrierId, String flightNumber, long flightSequenceNumber,
			String pol, String pou) throws InvalidFlightSegmentException {
		FlightOperationsProxy flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		int segmentSerialNum = 0;
		log.debug(ENTITY + " : " + "findFlightSegment" + " Entering");
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		flightSegments = flightOperationsProxy.findFlightSegments(companyCode, carrierId, flightNumber,
				flightSequenceNumber);
		String containerSegment = new StringBuilder().append(pol).append(pou).toString();
		String flightSegment = null;
		for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
			flightSegment = new StringBuilder().append(segmentSummaryVO.getSegmentOrigin())
					.append(segmentSummaryVO.getSegmentDestination()).toString();
			log.debug("" + "from proxy -- >" + " " + flightSegment);
			log.debug("" + "from container  -- >" + " " + containerSegment);
			if (flightSegment.equals(containerSegment)) {
				segmentSerialNum = segmentSummaryVO.getSegmentSerialNumber();
			}
		}
		if (segmentSerialNum == 0) {
			throw new InvalidFlightSegmentException(new String[] { containerSegment });
		}
		log.debug(ENTITY + " : " + "findFlightSegment" + " Exiting");
		return segmentSerialNum;
	}

	/** 
	* A-1739 This method checks if there is already a container assigned at the other port If it accepted If it is in a closed flight
	* @param containerDetailsVO
	* @throws SystemException
	* @throws ContainerAssignmentException
	*/
	private void checkContainerAssignmentAtPol(ContainerDetailsVO containerDetailsVO)
			throws ContainerAssignmentException {
		log.debug("AssignedFlightSegment" + " : " + "checkContainerAssignmentAtPol" + " Entering");
		ContainerAssignmentVO containerAsgVO = Container.findContainerAssignment(containerDetailsVO.getCompanyCode(),
				containerDetailsVO.getContainerNumber(), containerDetailsVO.getPol());
		if (containerAsgVO != null) {
			if (MailConstantsVO.FLAG_YES.equals(containerAsgVO.getAcceptanceFlag())) {
				if (containerAsgVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
					throw new ContainerAssignmentException(ContainerAssignmentException.DESTN_ASSIGNED,
							new Object[] { containerAsgVO.getContainerNumber(), containerDetailsVO.getPol() });
				} else if (!checkFlightClosedForOperations(containerAsgVO)) {
					throw new ContainerAssignmentException(ContainerAssignmentException.FLIGHT_STATUS_POL_OPEN,
							new Object[] { containerAsgVO.getContainerNumber(),
									new StringBuilder(containerAsgVO.getCarrierCode()).append(" ")
											.append(containerAsgVO.getFlightNumber()).append(" ")
											.append(containerAsgVO.getFlightDate()
													.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))),
									containerDetailsVO.getPol() });
				}
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "checkContainerAssignmentAtPol" + " Exiting");
	}

	/** 
	* A-1739
	* @param containerAsgVO
	* @return
	* @throws SystemException
	*/
	private boolean checkFlightClosedForOperations(ContainerAssignmentVO containerAsgVO) {
		AssignedFlightPK asgFlightPK = constructAssignedFlightPKForULD(containerAsgVO);
		return checkFlightClosed(asgFlightPK);
	}

	/** 
	* A-1739
	* @param containerAsgVO
	* @return
	*/
	private AssignedFlightPK constructAssignedFlightPKForULD(ContainerAssignmentVO containerAsgVO) {
		AssignedFlightPK assignedFlightPK = new AssignedFlightPK();
		assignedFlightPK.setCompanyCode(containerAsgVO.getCompanyCode());
		assignedFlightPK.setCarrierId(containerAsgVO.getCarrierId());
		assignedFlightPK.setFlightNumber(containerAsgVO.getFlightNumber());
		assignedFlightPK.setFlightSequenceNumber(containerAsgVO.getFlightSequenceNumber());
		assignedFlightPK.setAirportCode(containerAsgVO.getAirportCode());
		assignedFlightPK.setLegSerialNumber(containerAsgVO.getLegSerialNumber());
		return assignedFlightPK;
	}

	private boolean checkFlightClosed(AssignedFlightPK assignedFlightPK) {
		log.debug(ENTITY + " : " + "isFlightClosedForOperations" + " Entering");
		AssignedFlight assignedFlight = null;
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPK);
		} catch (FinderException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
		boolean isFlightClosed = false;
		if (MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(assignedFlight.getExportClosingFlag())) {
			isFlightClosed = true;
		}
		log.debug("" + "isFlightClosed " + " " + isFlightClosed);
		log.debug(ENTITY + " : " + "isFlightClosedForOperations" + " Exiting");
		return isFlightClosed;
	}

	private void updateMailbagVOsWithErr(Collection<MailbagVO> mailDetails, String errMsg) {
		log.debug(ENTITY + " : " + "updateMailbagVOsWithErr" + " Entering");
		if (mailDetails != null && mailDetails.size() > 0) {
			for (MailbagVO mailbagVO : mailDetails) {
				mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
				mailbagVO.setErrorDescription(errMsg);
			}
		}
		log.debug(ENTITY + " : " + "updateMailbagVOsWithErr" + " Exiting");
	}

	/** 
	* A-1739
	* @param dsnVO
	* @return
	*/
	private String constructDSNPKFrmDSNVO(DSNVO dsnVO) {
		StringBuilder dsnPK = new StringBuilder();
		dsnPK.append(dsnVO.getCompanyCode());
		dsnPK.append(dsnVO.getDsn());
		dsnPK.append(dsnVO.getOriginExchangeOffice());
		dsnPK.append(dsnVO.getDestinationExchangeOffice());
		dsnPK.append(dsnVO.getMailSubclass());
		dsnPK.append(dsnVO.getMailCategoryCode());
		dsnPK.append(dsnVO.getYear());
		return dsnPK.toString();
	}

	/** 
	* @author A-1739
	* @param oldDSNVO
	* @return
	* @throws SystemException
	*/
	private DSNVO copyDSNVO(DSNVO oldDSNVO) {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		MailOperationsMapper mailOperationsMapper = ContextUtil.getInstance().getBean(MailOperationsMapper.class);
		DSNVO dsnVO = new DSNVO();
		dsnVO= mailOperationsMapper.copyDSNVO( oldDSNVO);
		dsnVO.setBags(0);
		dsnVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0)));
		return dsnVO;
	}

	/** 
	* A-1739
	* @param dsnMstVO
	* @param dsnVO
	* @throws SystemException 
	*/
	private void updateDSNAtAirportVOForArrival(DSNVO dsnMstVO, DSNVO dsnVO) {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		Collection<DSNAtAirportVO> dsnAtAirports = dsnMstVO.getDsnAtAirports();
		for (DSNAtAirportVO dsnAtAirportVO : dsnAtAirports) {
			dsnAtAirportVO.setTotalBagsArrived(
					dsnAtAirportVO.getTotalBagsArrived() + dsnVO.getReceivedBags() - dsnVO.getPrevReceivedBags());
			Quantity dsnRecWt;
			try {
				if (dsnVO.getReceivedWeight() != null && dsnVO.getPrevReceivedWeight() != null
						&& !dsnVO.getPrevReceivedWeight().getDisplayUnit().getName()
						.equals(dsnVO.getReceivedWeight().getDisplayUnit().getName())) {
					Quantity convertedWeight = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0),
							BigDecimal.valueOf(dsnVO.getPrevReceivedWeight().getValue().doubleValue()),
							dsnVO.getReceivedWeight().getDisplayUnit().getName());
					dsnRecWt = dsnVO.getReceivedWeight().subtract(convertedWeight);
				} else if (dsnVO.getPrevReceivedWeight() != null) {
					dsnRecWt = dsnVO.getReceivedWeight().subtract(dsnVO.getPrevReceivedWeight());
				} else {
					dsnRecWt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0),
							BigDecimal.valueOf(dsnVO.getReceivedWeight().getValue().doubleValue()),
							dsnVO.getReceivedWeight().getDisplayUnit().getName());
				}
				try {
					if (dsnAtAirportVO.getTotalWeightArrived() == null) {
						Quantity totalWeightArrived = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0),
								BigDecimal.valueOf(dsnRecWt.getValue().doubleValue()),
								dsnRecWt.getDisplayUnit().getName());
						dsnAtAirportVO.setTotalWeightArrived(totalWeightArrived);
					} else {
						dsnAtAirportVO.setTotalWeightArrived(dsnAtAirportVO.getTotalWeightArrived().add(dsnRecWt));
					}

				} finally {
				}
			} finally {
			}
			dsnAtAirportVO.setTotalBagsDelivered(
					dsnAtAirportVO.getTotalBagsDelivered() + dsnVO.getDeliveredBags() - dsnVO.getPrevDeliveredBags());
			Quantity dsnDelWt;
			try {
				Quantity deliveredWeight = dsnVO.getDeliveredWeight();
				Quantity prevDeliveredWeight = dsnVO.getPrevDeliveredWeight();

				if (deliveredWeight != null && prevDeliveredWeight != null) {
					dsnDelWt = deliveredWeight.subtract(prevDeliveredWeight);

					if (dsnAtAirportVO != null) {
						Quantity totalWeightDelivered = dsnAtAirportVO.getTotalWeightDelivered();
						if (totalWeightDelivered != null) {
							dsnAtAirportVO.setTotalWeightDelivered(totalWeightDelivered.add(dsnDelWt));
						}
					}
				}
			} finally {
			}
		}
	}

	private MailbagPK constructMailbagPK(MailbagVO mailbagVO) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
				: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		return mailbagPK;
	}

	/** 
	* This method throws exception in non scan mode else it silently returns an errocode alon ein the vo Oct 9, 2006, a-1739
	* @param mailbagVO
	* @param isScanned
	* @return
	* @throws DuplicateMailBagsException
	* @throws SystemException
	*/
	private MailbagVO checkForDuplicateArrival(MailbagVO mailbagVO, boolean isScanned)
			throws DuplicateMailBagsException {
		boolean isNew = false;
		Mailbag mailbag = null;
		try {
			mailbag = Mailbag.find(constructMailbagPK(mailbagVO));
		} catch (FinderException exception) {
			isNew = true;
		}
		String latestStatus = null;
		if (mailbag != null) {
			latestStatus = mailbag.getLatestStatus();
		}
		if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
			if (!isNew && !(MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus()))
					&& (MailConstantsVO.OPERATION_INBOUND.equals(mailbag.getOperationalStatus())
							&& mailbag.getScannedPort().equals(mailbagVO.getScannedPort()))) {
				if (isScanned) {
					mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
					mailbagVO.setErrorDescription(MailConstantsVO.DUPLICATE_MAIL_ERR);
				} else {
				}
			}
		} else if (MailbagVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag())) {
			if (mailbag != null) {
				if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(latestStatus)
						&& MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getLatestStatus())) {
					if (isScanned) {
						mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
						mailbagVO.setErrorDescription(MailConstantsVO.DUPLICATE_ARRIVAL);
					}
				}
			}
		}
		if (mailbag != null) {
			return mailbag.retrieveVO();
		}
		return null;
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @return
	*/
	private String constructDSNPKForMailbag(MailbagVO mailbagVO) {
		StringBuilder dsnPK = new StringBuilder();
		dsnPK.append(mailbagVO.getCompanyCode());
		dsnPK.append(mailbagVO.getDespatchSerialNumber());
		dsnPK.append(mailbagVO.getMailSubclass());
		dsnPK.append(mailbagVO.getMailCategoryCode());
		dsnPK.append(mailbagVO.getOoe());
		dsnPK.append(mailbagVO.getDoe());
		dsnPK.append(mailbagVO.getYear());
		return dsnPK.toString();
	}

	/** 
	* TODO Purpose Oct 9, 2006, a-1739
	* @param containerDetailsVO
	* @param dsnVO
	* @param mailbagVO
	* @throws SystemException 
	*/
	private void removeDuplicatebagsWeight(ContainerDetailsVO containerDetailsVO, DSNVO dsnVO, MailbagVO mailbagVO) {
		dsnVO.setReceivedBags(dsnVO.getReceivedBags() - 1);
		try {
			dsnVO.setReceivedWeight(dsnVO.getReceivedWeight().subtract(mailbagVO.getWeight()));
		} finally {
		}
		Collection<DSNAtAirportVO> dsnAtArps = dsnVO.getDsnAtAirports();
		if (dsnAtArps != null && dsnAtArps.size() > 0) {
			for (DSNAtAirportVO dsnArpVO : dsnAtArps) {
				dsnArpVO.setTotalBagsArrived(dsnArpVO.getTotalBagsArrived() - 1);
				try {
					dsnArpVO.setTotalWeightArrived(dsnArpVO.getTotalWeightArrived().subtract(mailbagVO.getWeight()));
				} finally {
				}
			}
		}
	}

	/** 
	* @author A-1739
	* @author A-3227
	* @param doe
	* @param companyCode
	* @param deliveredPort
	* @param cityCache
	* @param eventCode
	* @param poaCode 
	* @return
	* @throws SystemException
	* @throws MailOperationsBusinessException
	*/
	public boolean isValidDeliveryAirport(String doe, String companyCode, String deliveredPort,
			Map<String, String> cityCache, String eventCode, String poaCode, ZonedDateTime dspDate)
			throws MailOperationsBusinessException {
		SharedAreaProxy sharedAreaProxy = ContextUtil.getInstance().getBean(SharedAreaProxy.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug(ENTITY + " : " + "isValidDeliveryAirport" + " Entering");
		Collection<String> officeOfExchanges = new ArrayList<String>();
		if (doe != null && doe.length() > 0) {
			officeOfExchanges.add(doe);
		}
		String deliveryCityCode = null;
		String nearestAirport = null;
		String nearestAirportToCity = null;
		log.debug("" + "----officeOfExchanges---" + " " + officeOfExchanges);
		Collection<ArrayList<String>> groupedOECityArpCodes = ContextUtil.getInstance().getBean(MailController.class).findCityAndAirportForOE(companyCode,
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
			if (cityCache != null && nearestAirport != null && nearestAirport.length() > 0 && deliveryCityCode != null
					&& deliveryCityCode.length() > 0) {
				cityCache.put(deliveryCityCode, nearestAirport);
			}
		}
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		AirportValidationVO airportValidationVO = sharedAreaProxy.validateAirportCode(logonAttributes.getCompanyCode(),
				deliveredPort);
		if (airportValidationVO != null) {
			log.debug("" + "airportValidationVO.getCityCode" + " " + airportValidationVO.getCityCode());
			log.debug("" + "deliveryCityCode" + " " + deliveryCityCode);
			if (airportValidationVO.getCityCode().equals(deliveryCityCode)) {
				log.debug("inside city validation returning true");
				return true;
			}
		}
		log.debug("" + "Mail Arrival Coterminus check begins: deliveryCode" + " " + eventCode);
		log.debug("" + "Mail Arrival Coterminus check begins: deliveredPort" + " " + deliveredPort);
		log.debug("" + "Mail Arrival Coterminus check begins: doe" + " " + doe);
		Page<OfficeOfExchangeVO> destinationAirport = ContextUtil.getInstance().getBean(MailController.class).findOfficeOfExchange(companyCode, doe, 1);
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		if (destinationAirport != null && !destinationAirport.isEmpty()) {
			officeOfExchangeVO = destinationAirport.iterator().next();
		}
		if (officeOfExchangeVO != null && officeOfExchangeVO.getAirportCode() != null
				&& !officeOfExchangeVO.getAirportCode().isEmpty()) {
			nearestAirport = officeOfExchangeVO.getAirportCode();
		}
		boolean coTerminusCheck = ContextUtil.getInstance().getBean(MailController.class).validateCoterminusairports(nearestAirport, deliveredPort,
				eventCode, poaCode, dspDate);
		log.debug("" + "Mail Arrival coTerminusCheck : " + " " + coTerminusCheck);
		if (coTerminusCheck)
			return true;
		else
			throw new MailOperationsBusinessException(MailOperationsBusinessException.INVALID_DELIVERY_AIRPORT);
	}

	/** 
	* TODO Purpose Oct 18, 2006, a-1739
	* @param containerDetailsVO
	* @param duplicateBags
	* @throws SystemException 
	*/
	private void removeArrivalExceptionPieces(ContainerDetailsVO containerDetailsVO,
			Collection<MailbagVO> duplicateBags) {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug(ENTITY + " : " + "updateRemovedPiecesCount" + " Entering");
		int bagCount = duplicateBags.size();
		double bagsWeight = calculateWeightofBags(duplicateBags);
		containerDetailsVO.setReceivedBags(containerDetailsVO.getReceivedBags() - bagCount);
		try {
			containerDetailsVO.setReceivedWeight(containerDetailsVO.getReceivedWeight().subtract((quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(bagsWeight)))));
		}//TODO: Exception to be checked in neo coding
		finally {
		}

		Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
		if (dsnVOs != null && dsnVOs.size() > 0) {
			Collection<DSNVO> dsnVOsToRem = new ArrayList<DSNVO>();
			for (DSNVO dsnVO : dsnVOs) {
				for (MailbagVO mailbagVO : duplicateBags) {
					if (dsnVO.getDsn().equals(mailbagVO.getDespatchSerialNumber())
							&& dsnVO.getOriginExchangeOffice().equals(mailbagVO.getOoe())
							&& dsnVO.getDestinationExchangeOffice().equals(mailbagVO.getDoe())
							&& dsnVO.getMailCategoryCode().equals(mailbagVO.getMailCategoryCode())
							&& dsnVO.getMailSubclass().equals(mailbagVO.getMailSubclass())
							&& dsnVO.getYear() == mailbagVO.getYear()) {
						dsnVO.setReceivedBags(dsnVO.getReceivedBags() - 1);
						try {
							dsnVO.setReceivedWeight(dsnVO.getReceivedWeight().subtract(mailbagVO.getWeight()));
						} finally {
						}
					}
				}
				if (dsnVO.getReceivedBags() == 0) {
					dsnVOsToRem.add(dsnVO);
				}
			}
			dsnVOs.removeAll(dsnVOsToRem);
		}
		log.debug(ENTITY + " : " + "updateRemovedPiecesCount" + " Exiting");
	}

	/** 
	* A-1739
	* @param mailbags
	* @return
	*/
	private double calculateWeightofBags(Collection<MailbagVO> mailbags) {
		double totalWeight = 0;
		for (MailbagVO mailbagVO : mailbags) {
			totalWeight += mailbagVO.getWeight().getValue().doubleValue();
		}
		return totalWeight;
	}

	/** 
	* TODO Purpose Dec 7, 2006, a-1739
	* @param mailArrivalVO
	* @return
	*/
	private AssignedFlightPK constructInbFlightPK(MailArrivalVO mailArrivalVO) {
		AssignedFlightPK inbFlightPK = new AssignedFlightPK();
		inbFlightPK.setCompanyCode(mailArrivalVO.getCompanyCode());
		inbFlightPK.setAirportCode(mailArrivalVO.getAirportCode());
		inbFlightPK.setCarrierId(mailArrivalVO.getCarrierId());
		inbFlightPK.setFlightNumber(mailArrivalVO.getFlightNumber());
		inbFlightPK.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
		inbFlightPK.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
		return inbFlightPK;
	}

	/** 
	* TODO Purpose Dec 7, 2006, a-1739
	* @param inbFlightPK
	* @return
	* @throws SystemException
	*/
	private boolean checkInboundFlightClosed(AssignedFlightPK inbFlightPK) {
		log.debug(ENTITY + " : " + "checkInboundFlightClosed" + " Entering");
		AssignedFlight inbFlight = null;
		try {
			inbFlight = AssignedFlight.find(inbFlightPK);
		} catch (FinderException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		return MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(inbFlight.getImportClosingFlag());
	}

	/** 
	* @author A-1739
	* @param containerDetailsVO
	* @param mailArrivalVO
	* @param segmentSerialNumber
	* @return
	*/
	private AssignedFlightSegmentVO constructAssignedFlightSegmentVOForArrival(ContainerDetailsVO containerDetailsVO,
			MailArrivalVO mailArrivalVO, int segmentSerialNumber) {
		AssignedFlightSegmentVO asgFlightSegVO = new AssignedFlightSegmentVO();
		asgFlightSegVO.setCompanyCode(mailArrivalVO.getCompanyCode());
		asgFlightSegVO.setCarrierId(mailArrivalVO.getCarrierId());
		asgFlightSegVO.setFlightNumber(mailArrivalVO.getFlightNumber());
		asgFlightSegVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
		asgFlightSegVO.setSegmentSerialNumber(segmentSerialNumber);
		asgFlightSegVO.setPol(containerDetailsVO.getPol());
		asgFlightSegVO.setPou(mailArrivalVO.getAirportCode());
		return asgFlightSegVO;
	}

	private Collection<MailbagVO> saveDSNMstDetails(Map<String, DSNVO> dsnMap, boolean isScanned)
			throws DuplicateMailBagsException {
		Collection<MailbagVO> errMailbags = new ArrayList<MailbagVO>();
		for (Map.Entry<String, DSNVO> dsnEntry : dsnMap.entrySet()) {
			DSNVO dsnVO = dsnEntry.getValue();
			boolean isUpdate = true;
			try {
				new Mailbag().saveArrivalDetails(dsnVO);
			} finally {
			}
		}
		return errMailbags;
	}

	/** 
	* @author A-5991
	* @param mailIdr
	* @param companyCode
	* @return
	*/
	private long findMailSequenceNumber(String mailIdr, String companyCode) {
		try {
			return Mailbag.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			//log.log(Log.FINE, "SystemException-findMailBagSequenceNumberFromMailIdr");
		}
		return 0;
	}

	/** 
	* @param mailArrivalVO
	*/
	private void updateMailbagVOforResdit(MailArrivalVO mailArrivalVO) {
		NeoMastersServiceUtils neoMastersServiceUtils =
				ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class);
		boolean resditReq = false;
		Collection<String> paramNames = new ArrayList<String>();
		paramNames.add(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		try {
			Map<String, String> paramValMap = neoMastersServiceUtils.findSystemParameterByCodes(paramNames);
			resditReq = MailConstantsVO.FLAG_YES.equals(paramValMap.get(MailConstantsVO.IS_RESDITMESSAGING_ENABLED));
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("Resdit Required Flag " + resditReq);
		if (resditReq) {
			Collection<ContainerDetailsVO> containerDetails = mailArrivalVO.getContainerDetails();
			if (containerDetails != null && !containerDetails.isEmpty()) {
				for (ContainerDetailsVO containerDetailsVO : containerDetails) {
					Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
					if (mailbags != null && !mailbags.isEmpty()) {
						for (MailbagVO mailbagVO : mailbags) {
							if (mailbagVO.getOperationalFlag() != null && !"N".equals(mailbagVO.getOperationalFlag())) {
								mailbagVO.setResditRequired(resditReq);
							}
						}
					}
				}
			}
		}
	}

	/** 
	* @param containerDetails
	*/
	private void updateMailVOsLegSerialNumber(Collection<ContainerDetailsVO> containerDetails) {
		for (ContainerDetailsVO containerDtlsVO : containerDetails) {
			if (MailConstantsVO.BULK_TYPE.equals(containerDtlsVO.getContainerType())) {
				FlightValidationVO flightValidationVO = null;
				try {
					flightValidationVO = validateFlightForBulk(containerDtlsVO);
				} finally {
				}
				if (flightValidationVO != null) {
					Collection<MailbagVO> mailVOs = containerDtlsVO.getMailDetails();
					if (mailVOs != null && !mailVOs.isEmpty()) {
						for (MailbagVO mailVO : mailVOs) {
							mailVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
						}
					}
				}
			}
		}
	}
	/**
	 * @author a-9529 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */
	private static MailOperationsDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author a-9529
	 * @param containers
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ContainerDetailsVO> findMailbagsInContainerFromInboundForReact(
			Collection<ContainerDetailsVO> containers) {
		try {
			return constructDAO().findMailbagsInContainerFromInboundForReact(containers);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}
}
