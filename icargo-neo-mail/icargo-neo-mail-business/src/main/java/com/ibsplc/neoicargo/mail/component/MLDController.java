package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDFlownDetailMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDMessageDetailVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDStatusMessageVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClient;
import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClients;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.MsgBrokerMessageProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedAirlineProxy;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.ForceAcceptanceException;
import com.ibsplc.neoicargo.mail.exception.MailMLDBusniessException;
import com.ibsplc.neoicargo.mail.exception.SharedProxyException;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.neoicargo.mailmasters.MailTrackingDefaultsBI;
import com.ibsplc.neoicargo.mailmasters.model.MLDConfigurationFilterModel;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.Criterion;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.CriterionBuilder;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyCondition;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyUtils;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

/** 
 * @author A-5526
 */
@Component
@Slf4j
@RegisterJAXRSClients(value={@RegisterJAXRSClient(clazz = MailTrackingDefaultsBI.class, targetService = "neo-mailmasters-business") })
public class MLDController {

	@Autowired
	private MailOperationsMapper mailOperationsMapper;
	@Autowired
	private Quantities quantities;
	@Autowired
	private FlightOperationsProxy flightOperationsProxy;
	@Autowired
	private SharedAirlineProxy sharedAirlineProxy;
	@Autowired
	private MsgBrokerMessageProxy msgBrokerMessageProxy;
	@Autowired
	private LocalDate localDateUtil;
	@Autowired
	private ContextUtil contextUtil;
	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;
	@Autowired
	MailTrackingDefaultsBI mailMasterApi;
	private static final String CLASS = "MLDController";
	private static final String DUPLICATE_MLD_CONFIGURATION = "mailtracking.defaults.duplictae.mld.configurations";
	private static final String MLD_REC_HND = "REC_HND";

	/** 
	* Method : MLDController.findMLDCongfigurations Added by : A-5526 on Dec 17, 2015 Used for : Find MLD Configurations Parameters : @param mLDConfigurationFilterVO Parameters : @throws SystemException Return type : Collection<MLDConfigurationVO>
	* @throws SystemException
	*/
	public Collection<MLDConfigurationVO> findMLDCongfigurations(MLDConfigurationFilterVO mLDConfigurationFilterVO) {
		log.debug(CLASS + " : " + "findMLDCongfigurations" + " Entering");
		if (mLDConfigurationFilterVO.getCarrierCode() != null && !mLDConfigurationFilterVO.getCarrierCode().isEmpty()) {
			mLDConfigurationFilterVO.setCarrierIdentifier(findCarrierIdentifier(
					mLDConfigurationFilterVO.getCompanyCode(), mLDConfigurationFilterVO.getCarrierCode()));
		}

		MLDConfigurationFilterModel mLDConfigurationFilterModel = mailOperationsMapper
                .mLDConfigurationFilterVOtoMLDConfigurationFilterModel(mLDConfigurationFilterVO);
		Collection<MLDConfigurationVO> mLDConfigurationVOs = mailOperationsMapper.mLDConfigurationModelstoMLDConfigurationVOs
				( mailMasterApi.findMLDCongfigurations(mLDConfigurationFilterModel));

		for (MLDConfigurationVO mLDConfigurationVO : mLDConfigurationVOs) {
			mLDConfigurationVO.setCarrierCode(
					findAirline(mLDConfigurationVO.getCompanyCode(), mLDConfigurationVO.getCarrierIdentifier()));
		}
		log.debug(CLASS + " : " + "findMLDCongfigurations" + " Exiting");
		return mLDConfigurationVOs;
	}

	public void flagMLDForMailOperations(Collection<MailbagVO> mailbagVOs, String mode) {
		log.debug("MLDController" + " : " + "flagMLDForMailOperations" + " Entering");
		if ((mailbagVOs == null) || (mailbagVOs.size() <= 0)) {
			return;
		}
		Collection<MLDMasterVO> mldMasterVOs = createMLDVOsFromMailbagVOs(mailbagVOs, null, mode);
		for (MLDMasterVO mldMasterVO : mldMasterVOs) {
			if ((MailConstantsVO.MLD_ALL.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isAllocationRequired())
					|| (MailConstantsVO.MLD_REC.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isRecRequired())
					|| (MailConstantsVO.MLD_UPL.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isUpliftedRequired())
					|| (MailConstantsVO.MLD_HND.equals(mldMasterVO.getEventCOde()) && mldMasterVO.ishNdRequired())
					|| (MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isdLVRequired())
					|| (MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde()) && mldMasterVO.issTGRequired())
					|| (MailConstantsVO.MLD_NST.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isnSTRequired())
					|| (MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isrCFRequired())
					|| (MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde()) && mldMasterVO.istFDRequired())
					|| (MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isrCTRequired())
					|| (MailConstantsVO.MLD_RET.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isrETRequired())) {
				new MLDMessageMaster(mldMasterVO);
			}
		}
		log.debug(CLASS + " : " + "flagMLDForMailOperations" + " Exiting");
	}

	/** 
	* Method : MLDController.createMLDVOsFromMailbagVOs Added by : A-5526 on Dec 17, 2015 Used for : to createMLDVOsFromMailbagVOs Parameters : @param toContainerVO,mode Parameters : @throws SystemException Return type : Collection<MLDMasterVO>
	* @throws SystemException
	*/
	Collection<MLDMasterVO> createMLDVOsFromMailbagVOs(Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO,
			String mode) {
		log.debug(CLASS + " : " + "createMLDVOsFromMailbaagVOs" + " Entering");
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		if ((mailbagVOs == null) || (mailbagVOs.isEmpty())) {
			return mldMasterVOs;
		}
		LoginProfile logon = null;
		logon = contextUtil.callerLoginProfile();
		for (MailbagVO mailbagVO : mailbagVOs) {
			String airport = "";
			String carrier = "";
			airport = findAirportForMLDMode(mailbagVO, mode);
			carrier = findCarrierCodeForMLDMode(mailbagVO, mode, toContainerVO, logon);
			int carrierIdentifier = findCarrierIdentifier(logon.getCompanyCode(), carrier);
			MLDConfigurationFilterVO filterVO = new MLDConfigurationFilterVO();
			filterVO.setCompanyCode(logon.getCompanyCode());
			filterVO.setAirportCode(
					mailbagVO.getScannedPort() != null ? mailbagVO.getScannedPort() : logon.getAirportCode());
			filterVO.setCarrierIdentifier(carrierIdentifier);
			filterVO.setCarrierCode(carrier);
			filterVO.setAirportCode(airport);
			Collection<MLDConfigurationVO> configVOs = findMLDCongfigurations(filterVO);
			MLDConfigurationVO configVO = null;
			String mldVersion = "";
			if (!configVOs.isEmpty()) {
				configVO = configVOs.iterator().next();
				mldVersion = configVO.getMldversion();
			}
			MLDMasterVO masterVO = constructMLDMasterVO(configVO);
			if (mldVersion.equals(MailConstantsVO.MLD_VERSION2)) {
				masterVO = createMLDVOsForVersion2(mailbagVO, toContainerVO, mldVersion, mode, airport, logon,
						masterVO);
				if (masterVO != null) {
					mldMasterVOs.add(masterVO);
				}
			} else if (mldVersion.contentEquals(MailConstantsVO.MLD_VERSION1)) {
				masterVO = createMLDVOsForVersion1(mailbagVO, toContainerVO, mldVersion, mode, airport, logon,
						masterVO);
				if (masterVO != null) {
					mldMasterVOs.add(masterVO);
				}
			} else {
				break;
			}
		}
		log.debug(CLASS + " : " + "createMLDVOsFromMailbaagVOs" + " Exiting");
		return mldMasterVOs;
	}

	/** 
	* Method : MLDController.findCarrierIdentifier Added by : A-5526 on Dec 17, 2015 Used for : to findCarrierIdentifier Parameters : @param companyCode,carrierCode Parameters : @throws SystemException
	* @throws SystemException
	*/
	public int findCarrierIdentifier(String companyCode, String carrierCode) {
		log.debug(CLASS + " : " + "findCarrierIdentifier" + " Entering");
		AirlineValidationVO airlineValidationVO = null;
		int carrierId = 0;
		try {
			airlineValidationVO = neoMastersServiceUtils.validateAlphaCode(companyCode, carrierCode);
		} catch (BusinessException ex) {
			throw new SystemException(ex.getMessage());
		}
		if (airlineValidationVO != null) {
			carrierId = airlineValidationVO.getAirlineIdentifier();
		}
		log.debug(CLASS + " : " + "findCarrierIdentifier" + " Exiting");
		return carrierId;
	}

	/** 
	* Method : MLDController.findAirline Added by : A-5526 on Dec 17, 2015 Used for : to findAirline Parameters : @param String,int Return : String
	* @throws SystemException
	*/
	private String findAirline(String companyCode, int carrierId) {
		log.debug(CLASS + " : " + "findAirline" + " Entering");
		AirlineValidationVO airlineValidationVO = null;
		String carrierCode = "";
		if (carrierId > 0) {
			try {
				airlineValidationVO = sharedAirlineProxy.findAirline(companyCode, carrierId);
			} catch (SharedProxyException sharedProxyException) {
				throw new SystemException(sharedProxyException.getMessage());
			}
			if (airlineValidationVO != null) {
				carrierCode = airlineValidationVO.getAlphaCode();
			}
		}
		log.debug(CLASS + " : " + "findAirline" + " Exiting");
		return carrierCode;
	}

	/** 
	* Added as part of bug ICRD-144653 by A-5526 This method is to set flight date with time as STD / ATA
	* @author A-5526
	* @param operationMode
	* @param mldDetailVO
	* @throws SystemException
	*/
	private void updateFlightDateWithScheduledTime(String operationMode, MLDDetailVO mldDetailVO, String airportCode) {
		Collection<FlightValidationVO> flightVOs = null;
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mldDetailVO.getCompanyCode());
		flightFilterVO.setDirection(operationMode);
		flightFilterVO.setPageNumber(1);
		if (MailConstantsVO.OPERATION_INBOUND.equals(operationMode)) {
			flightFilterVO.setFlightNumber(mldDetailVO.getFlightNumberInb());
			flightFilterVO.setFlightSequenceNumber(mldDetailVO.getFlightSequenceNumberInb());
			flightFilterVO.setFlightCarrierId(mldDetailVO.getCarrierIdInb());
		} else {
			flightFilterVO.setFlightNumber(mldDetailVO.getFlightNumberOub());
			flightFilterVO.setFlightSequenceNumber(mldDetailVO.getFlightSequenceNumberOub());
			flightFilterVO.setFlightCarrierId(mldDetailVO.getCarrierIdOub());
			if (airportCode != null) {
				flightFilterVO.setAirportCode(airportCode);
				flightFilterVO.setStation(airportCode);
			}
		}
		FlightValidationVO flightDetailVO = null;
		flightVOs = new MailController().validateFlight(flightFilterVO);
		if (flightVOs != null && flightVOs.size() > 0) {
			flightDetailVO = flightVOs.iterator().next();
			if (flightDetailVO != null) {
				//TODO: NEO to correct date
				/*
				if (MailConstantsVO.OPERATION_INBOUND.equals(operationMode)) {
					mldDetailVO.setFlightOperationDateInb(flightDetailVO.getSta());
				} else {
					mldDetailVO.setFlightOperationDateOub(flightDetailVO.getStd());
					if (mldDetailVO.getPouOub() == null) {
						mldDetailVO.setPouOub(flightDetailVO.getLegDestination());
					}
				}
				*/

			}
		}
	}

	/** 
	* Added as part of bug ICRD-152670 by A-5526 This method is to find flight segment Origin station 
	* @author A-5526
	* @param toContainerVO 
	* @param eventCode
	* @throws SystemException
	*/
	private void findSenderOrReceiverAirportCodes(MailbagVO mailbagVO, ContainerVO toContainerVO,
			MLDMasterVO mldMasterVO, String eventCode) {
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		Collection<FlightSegmentSummaryVO> flightSegmentsTemp = new ArrayList<FlightSegmentSummaryVO>();
		if (MailConstantsVO.MLD_ALL.equals(eventCode) && toContainerVO != null) {
			flightSegments = flightOperationsProxy.findFlightSegments(mailbagVO.getCompanyCode(),
					toContainerVO.getCarrierId(), toContainerVO.getFlightNumber(),
					toContainerVO.getFlightSequenceNumber());
		} else {
			flightSegments = flightOperationsProxy.findFlightSegments(mailbagVO.getCompanyCode(),
					mailbagVO.getCarrierId(), mailbagVO.getFlightNumber(), mailbagVO.getFlightSequenceNumber());
		}
		//TODO: Neo correction
		//BeanHelper.copyProperties(flightSegmentsTemp, flightSegments);
		Collection<String> routes = new ArrayList<String>();
		ArrayList<FlightSegmentSummaryVO> segmentsTemp = (ArrayList<FlightSegmentSummaryVO>) flightSegments;
		int segmentsTempLength = segmentsTemp.size();
		if (segmentsTempLength > 0) {
			routes.add(segmentsTemp.get(0).getSegmentOrigin());
			routes.add(segmentsTemp.get(0).getSegmentDestination());
		}
		if (segmentsTempLength > 1) {
			routes.add(segmentsTemp.get(1).getSegmentDestination());
		}
		if (flightSegments != null) {
			for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
				if (MailConstantsVO.MLD_HND.equals(eventCode)
						&& segmentSummaryVO.getSegmentDestination().equals(mailbagVO.getScannedPort())) {
					for (String route : routes) {
						if (route.equalsIgnoreCase(mailbagVO.getScannedPort())) {
							mldMasterVO.setSenderAirport(segmentSummaryVO.getSegmentOrigin());
						}
					}
				}
				if (MailConstantsVO.MLD_ALL.equals(eventCode)
						&& segmentSummaryVO.getSegmentOrigin().equals(mailbagVO.getScannedPort())) {
					for (String route : routes) {
						if (route.equalsIgnoreCase(mailbagVO.getScannedPort())) {
							mldMasterVO.setReceiverAirport(segmentSummaryVO.getSegmentDestination());
						}
					}
				}
			}
		}
	}

	/** 
	* Method		:	MLDController.createMLDVOsForVersion2 Added by 	:	A-10647 on 24-Feb-2022 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@param toContainerVO Parameters	:	@param mldVersion Parameters	:	@param mode Parameters	:	@param airport Parameters	:	@param carrier Parameters	:	@param carrierID Parameters	:	@param logon Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MLDMasterVO
	*/
	MLDMasterVO createMLDVOsForVersion2(MailbagVO mailbagVO, ContainerVO toContainerVO, String mldVersion, String mode,
			String airport, LoginProfile logon, MLDMasterVO mldMasterVO) {
		MLDDetailVO mldDetailVO = null;
		String mldEventMode = mode;
		if (mode.equalsIgnoreCase(MailConstantsVO.MLD_FRESH_ALL) || mode.equalsIgnoreCase(MLD_REC_HND)
				|| mode.equalsIgnoreCase(MailConstantsVO.MLD_UPL) || mode.equalsIgnoreCase(MailConstantsVO.MLD_HND)
				|| mode.equalsIgnoreCase(MailConstantsVO.MLD_ALL)) {
			return null;
		}
		mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
		mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
		mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		mldMasterVO.setEventCOde(mldEventMode);
		mldMasterVO.setScanTime(mailbagVO.getScannedDate());
		mldMasterVO.setSenderAirport(airport);
		if (mldMasterVO.getReceiverAirport() == null || mldMasterVO.getReceiverAirport().isEmpty()) {
			mldMasterVO.setReceiverAirport(mailbagVO.getPou());
		}
		String destination = "";
		if (mailbagVO.getMailbagId() != null && mailbagVO.getMailbagId().trim().length() == 29) {
			destination = new MailUploadController().findAirportCityForMLD(mailbagVO.getCompanyCode(),
					mailbagVO.getMailbagId().substring(8, 11));
			mldMasterVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
					BigDecimal.valueOf(Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29)))));
		}
		if (mldMasterVO.getReceiverAirport() == null || mldMasterVO.getReceiverAirport().isEmpty()) {
			mldMasterVO.setReceiverAirport(destination);
		}
		mldMasterVO.setDestAirport(destination);
		mldMasterVO.setLastUpdatedUser(logon.getUserId());
		mldMasterVO.setProcessStatus("NEW");
		mldMasterVO.setMessageVersion(mldVersion);
		mldDetailVO = new MLDDetailVO();
		mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
		mldDetailVO.setMailIdr(mailbagVO.getMailbagId());
		mldDetailVO.setMessageSequence(1);
		if ((MailConstantsVO.MLD_REC.equals(mode)) || (MailConstantsVO.MLD_NST.equals(mode))
				|| (MailConstantsVO.MLD_STG.equals(mode)) || (MailConstantsVO.MLD_RCT.equals(mode))
				|| (MailConstantsVO.MLD_RET.equals(mode))) {
			mldMasterVO.setMldDetailVO(mldDetailVO);
			mldMasterVO = findParametersForVersion2(mldMasterVO, mode, mailbagVO, logon, toContainerVO);
			if (mode.equalsIgnoreCase(MailConstantsVO.MLD_RCT) && mldMasterVO == null) {
				return null;
			}
			mldDetailVO = mldMasterVO.getMldDetailVO();
		} else {
			if (MailConstantsVO.MLD_DLV.equals(mode) || (MailConstantsVO.MLD_RCF.equals(mode))) {
				mldMasterVO.setSenderAirport(airport);
				mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
				mldMasterVO.setDestAirport(destination);
				mldMasterVO.setMessagingMode("O");
				mldDetailVO.setCarrierCodeInb(mailbagVO.getCarrierCode());
				mldDetailVO.setFlightNumberInb(mailbagVO.getFlightNumber());
				mldDetailVO.setCarrierIdInb(mailbagVO.getCarrierId());
				mldDetailVO.setFlightSequenceNumberInb(mailbagVO.getFlightSequenceNumber());
				updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND, mldDetailVO);
				mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
				mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
				mldDetailVO.setMailModeInb("F");
				mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
			}
		}
		if ((MailConstantsVO.MLD_RCF.equals(mode) || MailConstantsVO.MLD_DLV.equals(mode))) {
			if (mailbagVO.getTransactionLevel() != null
					&& MailConstantsVO.ULD_LEVEL_TRANSACTION.equals(mailbagVO.getTransactionLevel())) {
				mldMasterVO.setTransactionLevel(MailConstantsVO.ULD_LEVEL_TRANSACTION);
				mldMasterVO.setBarcodeValue(mailbagVO.getContainerNumber());
			} else {
				mldMasterVO.setTransactionLevel(MailConstantsVO.MAILBAG_LEVEL_TRANSACTION);
			}
		}
		if ((MailConstantsVO.MLD_RCF.equals(mode) || MailConstantsVO.MLD_DLV.equals(mode))
				&& mldMasterVO.getTransactionLevel() != null
				&& MailConstantsVO.MAILBAG_LEVEL_TRANSACTION.equals(mldMasterVO.getTransactionLevel())) {
			mldMasterVO.setUldNumber(null);
		} else if ((MailConstantsVO.MLD_STG.equals(mode) || MailConstantsVO.MLD_NST.equals(mode))
				&& toContainerVO != null) {
			mldMasterVO.setUldNumber(toContainerVO.getContainerNumber());
		} else {
			mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
		}
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	}

	/** 
	* Method		:	MLDController.createMLDVOsForVersion1 Added by 	:	A-10647 on 24-Feb-2022 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@param toContainerVO Parameters	:	@param mldVersion Parameters	:	@param mode Parameters	:	@param airport Parameters	:	@param carrier Parameters	:	@param carrierID Parameters	:	@param logon Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MLDMasterVO
	*/
	MLDMasterVO createMLDVOsForVersion1(MailbagVO mailbagVO, ContainerVO toContainerVO, String mldVersion, String mode,
			String airport, LoginProfile logon, MLDMasterVO mldMasterVO) {
		String mldEventMode = mode;
		if (mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_FRESH_ALL)
				|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_ALL)
				|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_REC)
				|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_HND)
				|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_UPL)
				|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_DLV)
				|| mldEventMode.equalsIgnoreCase(MLD_REC_HND)) {
			mldMasterVO = findParametersForVersion1(mailbagVO, toContainerVO, mldVersion, mode, airport, logon,
					mldMasterVO);
			return mldMasterVO;
		} else {
			return null;
		}
	}

	/** 
	* Method		:	MLDController.findAirportForMLDMode Added by 	:	A-10647 on 04-Mar-2022 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@param mode Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	String
	*/
	public String findAirportForMLDMode(MailbagVO mailbagVO, String mode) {
		String airport = "";
		if (MailConstantsVO.MLD_DLV.equals(mode) || (MailConstantsVO.MLD_HND.equals(mode))
				|| (MailConstantsVO.MLD_RCF.equals(mode))) {
			if (mailbagVO.getPol() != null) {
				airport = mailbagVO.getPol();
			} else {
				ContainerAssignmentVO containerAssignmentVO = null;
				containerAssignmentVO = new MailController()
						.findLatestContainerAssignment(mailbagVO.getContainerNumber());
				if (containerAssignmentVO != null
						&& (containerAssignmentVO.getFlightNumber() != null
								&& containerAssignmentVO.getFlightNumber().equals(mailbagVO.getFlightNumber()))
						&& containerAssignmentVO.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber()) {
					airport = containerAssignmentVO.getAirportCode();
				} else {
					airport = findAirportForEmptyContainer(mailbagVO);
				}
			}
		} else {
			airport = mailbagVO.getScannedPort();
		}
		return airport;
	}

	/** 
	* Method		:	MLDController.findCarrierCodeForMLDMode Added by 	:	A-10647 on 04-Mar-2022 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@param mode Parameters	:	@param toContainerVO Parameters	:	@param logon Parameters	:	@return  Return type	: 	String
	*/
	public String findCarrierCodeForMLDMode(MailbagVO mailbagVO, String mode, ContainerVO toContainerVO,
			LoginProfile logon) {
		String carrier = "";
		if ((MailConstantsVO.MLD_ALL.equals(mode) && toContainerVO != null)
				|| ((MailConstantsVO.MLD_STG.equals(mode) && toContainerVO != null)
						|| (MailConstantsVO.MLD_NST.equals(mode) && toContainerVO != null))) {
			carrier = toContainerVO.getCarrierCode();
		} else if (MailConstantsVO.MLD_UPL.equals(mode)) {
			carrier = logon.getCompanyCode();
		} else {
			carrier = mailbagVO.getCarrierCode();
		}
		return carrier;
	}

	/** 
	* Method		:	MLDController.findAirportForEmptyContainer Added by 	:	A-10647 on 04-Mar-2022 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	String
	*/
	public String findAirportForEmptyContainer(MailbagVO mailbagVO) {
		String airport = "";
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		flightFilterVO.setStation(mailbagVO.getScannedPort());
		//TODO: Neo to correct date
		//flightFilterVO.setFlightDate(mailbagVO.getFlightDate());
		flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
		flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		Collection<FlightValidationVO> flightValidationVOs = new MailController().validateFlight(flightFilterVO);
		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				Collection<FlightSegmentSummaryVO> flightSegments = null;
				try {
					flightSegments = flightOperationsProxy.findFlightSegments(flightValidationVO.getCompanyCode(),
							flightValidationVO.getFlightCarrierId(), flightValidationVO.getFlightNumber(),
							flightValidationVO.getFlightSequenceNumber());
				} finally {
				}
				airport = findAirportFromFlightSegment(flightSegments, mailbagVO);
			}
		}
		return airport;
	}

	/** 
	* Method		:	MLDController.findAirportFromFlightSegment Added by 	:	A-10647 on 04-Mar-2022 Used for 	: Parameters	:	@param flightSegments Parameters	:	@param mailbagVO Parameters	:	@return  Return type	: 	String
	*/
	public String findAirportFromFlightSegment(Collection<FlightSegmentSummaryVO> flightSegments, MailbagVO mailbagVO) {
		String airport = "";
		if (flightSegments != null && !flightSegments.isEmpty()) {
			for (FlightSegmentSummaryVO segmentVo : flightSegments) {
				if (segmentVo.getSegmentDestination().equals(mailbagVO.getScannedPort())) {
					airport = segmentVo.getSegmentOrigin();
					if (mailbagVO.getPol() == null) {
						mailbagVO.setPol(segmentVo.getSegmentOrigin());
					}
				}
			}
		}
		return airport;
	}

	/** 
	* Method		:	MLDController.findParametersForVersion2 Added by 	:	A-10647 on 04-Mar-2022 Used for 	: Parameters	:	@param mldMasterVO Parameters	:	@param mode Parameters	:	@param mailbagVO Parameters	:	@param logon Parameters	:	@param toContainerVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MLDMasterVO
	*/
	public MLDMasterVO findParametersForVersion2(MLDMasterVO mldMasterVO, String mode, MailbagVO mailbagVO,
			LoginProfile logon, ContainerVO toContainerVO) {
		MLDDetailVO mldDetailVO = mldMasterVO.getMldDetailVO();
		if (MailConstantsVO.MLD_RCT.equals(mode)) {
			if (MailConstantsVO.MLD_RCT.equals(mode) && mailbagVO.getTransferFromCarrier() != null
					&& mailbagVO.getTransferFromCarrier().trim().length() > 0) {
				mldDetailVO.setCarrierCodeInb(mailbagVO.getTransferFromCarrier());
				mldDetailVO.setCarrierIdInb(
						findCarrierIdentifier(logon.getCompanyCode(), mailbagVO.getTransferFromCarrier()));
			} else {
				return null;
			}
		}
		if (((MailConstantsVO.MLD_STG.equals(mode) && toContainerVO != null)
				|| (MailConstantsVO.MLD_NST.equals(mode) && toContainerVO != null))) {
			mldDetailVO = findDetailVOForSTGAndNST(mldDetailVO, mailbagVO, logon, toContainerVO);
			mldMasterVO.setUldNumber(toContainerVO.getContainerNumber());
		} else {
			mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
			mldDetailVO.setCarrierIdOub(findCarrierIdentifier(logon.getCompanyCode(), mailbagVO.getCarrierCode()));
			mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberOub(mailbagVO.getFlightSequenceNumber());
			mldDetailVO.setCarrierIdOub(mailbagVO.getCarrierId());
			mldDetailVO.setEventTimeOub(localDateUtil.getLocalDate(logon.getAirportCode(), true));
			mldDetailVO.setPouOub(mailbagVO.getPou());
			updateFlightDateWithScheduledTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO,
					mldMasterVO.getSenderAirport());
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeOub("F");
			} else {
				mldDetailVO.setMailModeOub("H");
			}
		}
		mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());
		if (MailConstantsVO.MLD_RET.equals(mode)) {
			findDetailVOForRET(mldDetailVO, mailbagVO, logon);
			mldDetailVO.setEventTimeOub(localDateUtil.getLocalDate(logon.getAirportCode(), true));
		}
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	}

	/** 
	* Method		:	MLDController.findDetailVOForSTGAndNST Added by 	:	A-10647 on 04-Mar-2022 Used for 	: Parameters	:	@param mldDetailVO Parameters	:	@param mailbagVO Parameters	:	@param logon Parameters	:	@param toContainerVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MLDDetailVO
	*/
	public MLDDetailVO findDetailVOForSTGAndNST(MLDDetailVO mldDetailVO, MailbagVO mailbagVO, LoginProfile logon,
			ContainerVO toContainerVO) {
		mldDetailVO.setCarrierCodeOub(toContainerVO.getCarrierCode());
		mldDetailVO.setFlightNumberOub(toContainerVO.getFlightNumber());
		mldDetailVO.setFlightSequenceNumberOub(toContainerVO.getFlightSequenceNumber());
		mldDetailVO.setCarrierIdOub(toContainerVO.getCarrierId());
		updateFlightDateWithScheduledTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO, mailbagVO.getScannedPort());
		mldDetailVO.setPouOub(toContainerVO.getPou());
		if (mailbagVO.getScannedDate() != null) {
			mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
		} else {
			mldDetailVO.setEventTimeOub(localDateUtil.getLocalDate(logon.getAirportCode(), true));
		}
		mldDetailVO.setEventTimeOub(localDateUtil.getLocalDate(logon.getAirportCode(), true));
		if (toContainerVO.getFlightSequenceNumber() > 0) {
			mldDetailVO.setMailModeOub("F");
		} else {
			mldDetailVO.setMailModeOub("H");
		}
		return mldDetailVO;
	}

	/** 
	* Method		:	MLDController.findDetailVOForRET Added by 	:	A-10647 on 04-Mar-2022 Used for 	: Parameters	:	@param mldDetailVO Parameters	:	@param mailbagVO Parameters	:	@param logon Parameters	:	@param toContainerVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MLDDetailVO
	*/
	public MLDDetailVO findDetailVOForRET(MLDDetailVO mldDetailVO, MailbagVO mailbagVO, LoginProfile logon) {
		mldDetailVO.setEventTimeOub(localDateUtil.getLocalDate(logon.getAirportCode(), true));
		if (mailbagVO.getDamagedMailbags() != null && !mailbagVO.getDamagedMailbags().isEmpty()) {
			Collection<DamagedMailbagVO> damagedMailBags = mailbagVO.getDamagedMailbags();
			DamagedMailbagVO damagedMailbagVO = damagedMailBags.iterator().next();
			mldDetailVO.setPostalCodeOub(damagedMailbagVO.getPaCode());
		}
		return mldDetailVO;
	}

	/** 
	* Method		:	MLDController.findMasterVOForVersion1 Added by 	:	A-10647 on 04-Mar-2022 Used for 	: Parameters	:	@param mldMasterVO Parameters	:	@param mode Parameters	:	@param toContainerVO Parameters	:	@param mailbagVO Parameters	:	@param logon Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MLDMasterVO
	*/
	public MLDMasterVO findMasterVOForVersion1(MLDMasterVO mldMasterVO, String mode, ContainerVO toContainerVO,
			MailbagVO mailbagVO, LoginProfile logon) {
		MLDDetailVO mldDetailVO = mldMasterVO.getMldDetailVO();
		if (MailConstantsVO.MLD_ALL.equals(mode) && toContainerVO != null) {
			mldMasterVO.setUldNumber(toContainerVO.getContainerNumber());
			mldDetailVO.setCarrierCodeOub(toContainerVO.getCarrierCode());
			mldDetailVO.setFlightNumberOub(toContainerVO.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberOub(toContainerVO.getFlightSequenceNumber());
			updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO);
			mldDetailVO.setCarrierIdOub(toContainerVO.getCarrierId());
			mldDetailVO.setPouOub(toContainerVO.getPou());
			if (mailbagVO.getScannedDate() != null) {
				mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
			} else {
				mldDetailVO.setEventTimeOub(localDateUtil.getLocalDate(logon.getAirportCode(), true));
			}
			mldDetailVO.setEventTimeOub(localDateUtil.getLocalDate(logon.getAirportCode(), true));
			if (toContainerVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeOub("F");
			} else {
				mldDetailVO.setMailModeOub("H");
			}
		} else {
			mldDetailVO = findMLDDetailVOForModeOtherThanALL(mldDetailVO, mode, logon, mailbagVO);
		}
		mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());
		if (MLD_REC_HND.equals(mldMasterVO.getEventCOde()) && mailbagVO.getTransferFromCarrier() != null
				&& mailbagVO.getTransferFromCarrier().trim().length() > 0) {
			mldDetailVO.setMailModeInb("H");
			mldMasterVO.setEventCOde(MailConstantsVO.MLD_HND);
			mldDetailVO.setCarrierCodeInb(mailbagVO.getTransferFromCarrier());
			mldDetailVO
					.setCarrierIdInb(findCarrierIdentifier(logon.getCompanyCode(), mailbagVO.getTransferFromCarrier()));
		}
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	}

	/** 
	* Method		:	MLDController.findMLDDetailVOForModeOtherThanALL Added by 	:	A-10647 on 04-Mar-2022 Used for 	: Parameters	:	@param mldDetailVO Parameters	:	@param mode Parameters	:	@param logon Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MLDDetailVO
	*/
	public MLDDetailVO findMLDDetailVOForModeOtherThanALL(MLDDetailVO mldDetailVO, String mode, LoginProfile logon,
			MailbagVO mailbagVO) {
		if (MailConstantsVO.MLD_UPL.equals(mode)) {
			mldDetailVO.setCarrierCodeOub(logon.getCompanyCode());
			mldDetailVO.setCarrierIdOub(findCarrierIdentifier(logon.getCompanyCode(), logon.getOwnAirlineCode()));
		} else {
			mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
			mldDetailVO.setCarrierIdOub(findCarrierIdentifier(logon.getCompanyCode(), mailbagVO.getCarrierCode()));
		}
		mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
		mldDetailVO.setFlightSequenceNumberOub(mailbagVO.getFlightSequenceNumber());
		updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO);
		mldDetailVO.setCarrierIdOub(mailbagVO.getCarrierId());
		mldDetailVO.setEventTimeOub(localDateUtil.getLocalDate(logon.getAirportCode(), true));
		updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO);
		mldDetailVO.setPouOub(mailbagVO.getPou());
		if (mailbagVO.getFlightSequenceNumber() > 0) {
			mldDetailVO.setMailModeOub("F");
		} else {
			mldDetailVO.setMailModeOub("H");
		}
		return mldDetailVO;
	}

	/** 
	* Method		:	MLDController.findParametersForVersion1 Added by 	:	A-10647 on 04-Mar-2022 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@param toContainerVO Parameters	:	@param mldVersion Parameters	:	@param mode Parameters	:	@param airport Parameters	:	@param logon Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MLDMasterVO
	*/
	public MLDMasterVO findParametersForVersion1(MailbagVO mailbagVO, ContainerVO toContainerVO, String mldVersion,
			String mode, String airport, LoginProfile logon, MLDMasterVO mldMasterVO) {
		String mldEventMode = mode;
		mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
		mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
		mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		mldMasterVO.setEventCOde(mldEventMode);
		if (MailConstantsVO.MLD_FRESH_ALL.equals(mldEventMode)) {
			mldMasterVO.setEventCOde(MailConstantsVO.MLD_ALL);
			mode = MailConstantsVO.MLD_REC;
		}
		if (MLD_REC_HND.equals(mldEventMode)) {
			mldMasterVO.setEventCOde(MLD_REC_HND);
			mode = MailConstantsVO.MLD_REC;
		}
		mldMasterVO.setScanTime(mailbagVO.getScannedDate());
		mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
		if (MailConstantsVO.MLD_ALL.equals(mode)) {
			if (toContainerVO != null) {
				findSenderOrReceiverAirportCodes(mailbagVO, toContainerVO, mldMasterVO, mode);
			} else {
				findSenderOrReceiverAirportCodes(mailbagVO, null, mldMasterVO, mode);
			}
		}
		if (mldMasterVO.getReceiverAirport() == null || mldMasterVO.getReceiverAirport().isEmpty()) {
			mldMasterVO.setReceiverAirport(mailbagVO.getPou());
		}
		String destination = "";
		if (mailbagVO.getMailbagId() != null && mailbagVO.getMailbagId().trim().length() == 29) {
			destination = new MailUploadController().findAirportCityForMLD(mailbagVO.getCompanyCode(),
					mailbagVO.getMailbagId().substring(8, 11));
			mldMasterVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
					BigDecimal.valueOf(Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29)))));
		}
		mldMasterVO.setDestAirport(destination);
		mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
		mldMasterVO.setLastUpdatedUser(logon.getUserId());
		mldMasterVO.setProcessStatus("NEW");
		mldMasterVO.setWeightCode("KG");
		mldMasterVO.setMessageVersion(mldVersion);
		MLDDetailVO mldDetailVO = new MLDDetailVO();
		mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
		mldDetailVO.setMailIdr(mailbagVO.getMailbagId());
		mldDetailVO.setMessageSequence(1);
		if ((MailConstantsVO.MLD_ALL.equals(mode)) || (MailConstantsVO.MLD_UPL.equals(mode))
				|| (MailConstantsVO.MLD_REC.equals(mode))) {
			mldMasterVO.setMldDetailVO(mldDetailVO);
			mldMasterVO = findMasterVOForVersion1(mldMasterVO, mode, toContainerVO, mailbagVO, logon);
			mldDetailVO = mldMasterVO.getMldDetailVO();
		} else {
			mldMasterVO.setSenderAirport(mailbagVO.getPol());
			mldMasterVO.setReceiverAirport(mailbagVO.getPou());
			mldMasterVO.setSenderAirport(airport);
			mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
			mldMasterVO.setDestAirport(destination);
			mldMasterVO.setMessagingMode("O");
			mldDetailVO.setCarrierCodeInb(mailbagVO.getCarrierCode());
			mldDetailVO.setFlightNumberInb(mailbagVO.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberInb(mailbagVO.getFlightSequenceNumber());
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND, mldDetailVO);
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND, mldDetailVO);
			mldDetailVO.setCarrierIdInb(mailbagVO.getCarrierId());
			mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
			mldDetailVO.setPolInb(mailbagVO.getPol());
			mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
			mldDetailVO.setMailModeInb("F");
			mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
		}
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	}

	private MLDMasterVO constructMLDMasterVO(MLDConfigurationVO configVO) {
		MLDMasterVO masterVO = null;
		if (configVO != null) {
			masterVO = new MLDMasterVO();
			if (MailConstantsVO.FLAG_YES.equals(configVO.getAllocatedRequired())) {
				masterVO.setAllocationRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getReceivedRequired())) {
				masterVO.setRecRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getUpliftedRequired())) {
				masterVO.setUpliftedRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.gethNDRequired())) {
				masterVO.sethNdRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getDeliveredRequired())) {
				masterVO.setdLVRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getStagedRequired())) {
				masterVO.setsTGRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getNestedRequired())) {
				masterVO.setnSTRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getReceivedFromFightRequired())) {
				masterVO.setrCFRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getTransferredFromOALRequired())) {
				masterVO.settFDRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getReceivedFromOALRequired())) {
				masterVO.setrCTRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getReturnedRequired())) {
				masterVO.setrETRequired(true);
			}
		}
		return masterVO;
	}

	public MLDMasterVO createMLDVOsForVersion1(ContainerVO containerVo, String mldVersion, String mode, String airport,
			LoginProfile logon, MLDMasterVO mldMasterVO) {
		String mldEventMode = mode;
		if (mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_FRESH_ALL)
				|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_ALL)
				|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_REC)
				|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_HND)
				|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_UPL)
				|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_DLV)
				|| mldEventMode.equalsIgnoreCase(MLD_REC_HND)) {
			mldMasterVO = findParametersForVersion1(containerVo, mldVersion, mode, airport, logon, mldMasterVO);
			return mldMasterVO;
		} else {
			return null;
		}
	}

	public MLDMasterVO findParametersForVersion1(ContainerVO containerVo, String mldVersion, String mode,
			String airport, LoginProfile logon, MLDMasterVO mldMasterVO) {
		String mldEventMode = mode;
		mldMasterVO.setCompanyCode(containerVo.getCompanyCode());
		mldMasterVO.setBarcodeValue(containerVo.getContainerNumber());
		mldMasterVO.setEventCOde(mldEventMode);
		if (MailConstantsVO.MLD_FRESH_ALL.equals(mldEventMode)) {
			mldMasterVO.setEventCOde(MailConstantsVO.MLD_ALL);
			mode = MailConstantsVO.MLD_REC;
		}
		mldMasterVO.setScanTime(containerVo.getScannedDate());
		mldMasterVO.setSenderAirport(containerVo.getPol());
		if (MailConstantsVO.MLD_ALL.equals(mode)) {
			findSenderOrReceiverAirportCodes(containerVo, mldMasterVO);
		}
		mldMasterVO.setReceiverAirport(containerVo.getPou());
		String destination = "";
		mldMasterVO.setDestAirport(destination);
		mldMasterVO.setUldNumber(containerVo.getContainerNumber());
		mldMasterVO.setLastUpdatedUser(logon.getUserId());
		mldMasterVO.setProcessStatus("NEW");
		mldMasterVO.setWeightCode("KG");
		mldMasterVO.setMessageVersion(mldVersion);
		MLDDetailVO mldDetailVO = new MLDDetailVO();
		mldDetailVO.setCompanyCode(containerVo.getCompanyCode());
		mldDetailVO.setMailIdr(null);
		mldDetailVO.setMessageSequence(1);
		if ((MailConstantsVO.MLD_ALL.equals(mode)) || (MailConstantsVO.MLD_UPL.equals(mode))
				|| (MailConstantsVO.MLD_REC.equals(mode))) {
			mldMasterVO.setMldDetailVO(mldDetailVO);
			mldMasterVO = findMasterVOForVersion1(mldMasterVO, containerVo, logon);
			mldDetailVO = mldMasterVO.getMldDetailVO();
		} else {
			mldMasterVO.setSenderAirport(containerVo.getPol());
			mldMasterVO.setReceiverAirport(containerVo.getPou());
			mldMasterVO.setSenderAirport(airport);
			mldMasterVO.setReceiverAirport(containerVo.getPou());
			mldMasterVO.setDestAirport(destination);
			mldMasterVO.setMessagingMode("O");
			mldDetailVO.setCarrierCodeInb(containerVo.getCarrierCode());
			mldDetailVO.setFlightNumberInb(containerVo.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberInb(containerVo.getFlightSequenceNumber());
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND, mldDetailVO);
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND, mldDetailVO);
			mldDetailVO.setCarrierIdInb(containerVo.getCarrierId());
			mldDetailVO.setEventTimeInb(containerVo.getScannedDate());
			mldDetailVO.setPolInb(containerVo.getPol());
			mldDetailVO.setPostalCodeInb(containerVo.getPaCode());
			mldDetailVO.setMailModeInb("F");
			mldDetailVO.setEventTimeInb(containerVo.getScannedDate());
		}
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	}

	public MLDMasterVO findMasterVOForVersion1(MLDMasterVO mldMasterVO, ContainerVO containerVo, LoginProfile logon) {
		MLDDetailVO mldDetailVO = mldMasterVO.getMldDetailVO();
		mldDetailVO = findMLDDetailVOForModeOtherThanALL(mldDetailVO, logon, containerVo);
		mldDetailVO.setPostalCodeOub(containerVo.getPaCode());
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	}

	public MLDDetailVO findMLDDetailVOForModeOtherThanALL(MLDDetailVO mldDetailVO, LoginProfile logon,
			ContainerVO containerVo) {
		mldDetailVO.setCarrierCodeOub(containerVo.getCarrierCode());
		mldDetailVO.setCarrierIdOub(findCarrierIdentifier(logon.getCompanyCode(), containerVo.getCarrierCode()));
		mldDetailVO.setFlightNumberOub(containerVo.getFlightNumber());
		mldDetailVO.setFlightSequenceNumberOub(containerVo.getFlightSequenceNumber());
		updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO);
		mldDetailVO.setCarrierIdOub(containerVo.getCarrierId());
		mldDetailVO.setEventTimeOub(localDateUtil.getLocalDate(logon.getAirportCode(), true));
		updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO);
		mldDetailVO.setPouOub(containerVo.getPou());
		if (containerVo.getFlightSequenceNumber() > 0) {
			mldDetailVO.setMailModeOub("F");
		} else {
			mldDetailVO.setMailModeOub("H");
		}
		return mldDetailVO;
	}

	public void findSenderOrReceiverAirportCodes(ContainerVO containerVo, MLDMasterVO mldMasterVO) {
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		flightSegments = flightOperationsProxy.findFlightSegments(containerVo.getCompanyCode(),
				containerVo.getCarrierId(), containerVo.getFlightNumber(), containerVo.getFlightSequenceNumber());
		Collection<String> routes = new ArrayList<>();
		if (flightSegments != null) {
			ArrayList<FlightSegmentSummaryVO> segmentsTemp = (ArrayList<FlightSegmentSummaryVO>) flightSegments;
			int segmentsTempLength = segmentsTemp.size();
			if (segmentsTempLength > 0) {
				routes.add(segmentsTemp.get(0).getSegmentOrigin());
				routes.add(segmentsTemp.get(0).getSegmentDestination());
			}
			if (segmentsTempLength > 1) {
				routes.add(segmentsTemp.get(1).getSegmentDestination());
			}
			recevierAirport(containerVo, mldMasterVO, flightSegments, routes);
		}
	}

	public void recevierAirport(ContainerVO containerVo, MLDMasterVO mldMasterVO,
			Collection<FlightSegmentSummaryVO> flightSegments, Collection<String> routes) {
		for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
			if (segmentSummaryVO.getSegmentOrigin().equals(containerVo.getPol())) {
				routeMthd(containerVo, mldMasterVO, routes, segmentSummaryVO);
			}
		}
	}

	public void routeMthd(ContainerVO containerVo, MLDMasterVO mldMasterVO, Collection<String> routes,
			FlightSegmentSummaryVO segmentSummaryVO) {
		for (String route : routes) {
			if (route.equalsIgnoreCase(containerVo.getPol())) {
				mldMasterVO.setReceiverAirport(segmentSummaryVO.getSegmentDestination());
			}
		}
	}

	public MLDMasterVO createMLDVOsForVersion2(ContainerVO containerVo, String mldVersion, String mode, String airport,
			LoginProfile logon, MLDMasterVO mldMasterVO) {
		MLDDetailVO mldDetailVO = null;
		String mldEventMode = mode;
		if (mode.equalsIgnoreCase(MailConstantsVO.MLD_FRESH_ALL) || mode.equalsIgnoreCase(MLD_REC_HND)
				|| mode.equalsIgnoreCase(MailConstantsVO.MLD_UPL) || mode.equalsIgnoreCase(MailConstantsVO.MLD_HND)
				|| mode.equalsIgnoreCase(MailConstantsVO.MLD_ALL)) {
			return null;
		}
		mldMasterVO.setCompanyCode(containerVo.getCompanyCode());
		mldMasterVO.setBarcodeValue(containerVo.getContainerNumber());
		mldMasterVO.setDestAirport(containerVo.getPou());
		mldMasterVO.setEventCOde(mldEventMode);
		mldMasterVO.setScanTime(containerVo.getScannedDate());
		mldMasterVO.setSenderAirport(airport);
		mldMasterVO.setReceiverAirport(containerVo.getPou());
		mldMasterVO.setTransactionLevel(MailConstantsVO.ULD_LEVEL_TRANSACTION);
		if ((MailConstantsVO.MLD_RCF.equals(mode))) {
			mldMasterVO.setUldNumber(null);
		} else {
			mldMasterVO.setUldNumber(containerVo.getContainerNumber());
		}
		mldMasterVO.setLastUpdatedUser(logon.getUserId());
		mldMasterVO.setProcessStatus("NEW");
		mldMasterVO.setWeight(containerVo.getActualWeight());
		if (containerVo.getActualWeight() != null) {
			mldMasterVO.setWeightCode(containerVo.getActualWeight().getDisplayUnit().getName());
		}
		mldMasterVO.setMessageVersion(mldVersion);
		mldDetailVO = new MLDDetailVO();
		mldDetailVO.setCompanyCode(containerVo.getCompanyCode());
		mldDetailVO.setMailIdr(null);
		mldDetailVO.setMessageSequence(1);
		if ((MailConstantsVO.MLD_REC.equals(mode)) || (MailConstantsVO.MLD_NST.equals(mode))
				|| (MailConstantsVO.MLD_STG.equals(mode)) || (MailConstantsVO.MLD_RCT.equals(mode))
				|| (MailConstantsVO.MLD_RET.equals(mode))) {
			mldMasterVO.setMldDetailVO(mldDetailVO);
			mldMasterVO = findParametersForVersion2(mldMasterVO, containerVo, logon);
			mldDetailVO = mldMasterVO.getMldDetailVO();
		}
		mldMasterVO.setTransactionLevel("U");
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	}

	public MLDMasterVO findParametersForVersion2(MLDMasterVO mldMasterVO, ContainerVO containerVo, LoginProfile logon) {
		MLDDetailVO mldDetailVO = mldMasterVO.getMldDetailVO();
		mldDetailVO.setCarrierCodeOub(containerVo.getCarrierCode());
		mldDetailVO.setCarrierIdOub(findCarrierIdentifier(logon.getCompanyCode(), containerVo.getCarrierCode()));
		mldDetailVO.setFlightNumberOub(containerVo.getFlightNumber());
		mldDetailVO.setFlightSequenceNumberOub(containerVo.getFlightSequenceNumber());
		updateFlightDateWithScheduledTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO,
				mldMasterVO.getSenderAirport());
		mldDetailVO.setCarrierIdOub(containerVo.getCarrierId());
		mldDetailVO.setEventTimeOub(localDateUtil.getLocalDate(logon.getAirportCode(), true));
		updateFlightDateWithScheduledTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO,
				mldMasterVO.getSenderAirport());
		mldDetailVO.setPouOub(containerVo.getPou());
		if (containerVo.getFlightSequenceNumber() > 0) {
			mldDetailVO.setMailModeOub("F");
		} else {
			mldDetailVO.setMailModeOub("H");
		}
		mldDetailVO.setPostalCodeOub(containerVo.getPaCode());
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	}

	/** 
	* @author A-8353
	* @param mldDetailVO
	* @throws SystemException 
	*/
	private void updateFlightDateWithTime(String operationMode, MLDDetailVO mldDetailVO) {
		String airportCode = null;
		updateFlightDateWithScheduledTime(operationMode, mldDetailVO, airportCode);
	}
	
	public void flagMLDForUpliftedMailbagsForATDCapture(Collection<OperationalFlightVO> operationalFlightVOs) {

		log.debug(CLASS, "flagMLDForUpliftedMailbags");

		if (operationalFlightVOs != null) {
			for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {

				AssignedFlight assignedFlight = null;
				AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
				assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
				assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
				assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
				assignedFlightPk.setFlightSequenceNumber(operationalFlightVO
						.getFlightSequenceNumber());
				assignedFlightPk.setLegSerialNumber(operationalFlightVO
						.getLegSerialNumber());
				assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
				try {
					assignedFlight = AssignedFlight.find(assignedFlightPk);
				} catch (FinderException ex) {
					assignedFlight=null;
				}
				if (assignedFlight != null && MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(assignedFlight.getExportClosingFlag())) {
					Collection<MailbagVO> totalMailBags = new ArrayList<MailbagVO>();
					totalMailBags = MailAcceptance
							.findMailBagsForUpliftedResdit(operationalFlightVO);

					flagMLDForMailOperations(totalMailBags, MailConstantsVO.MLD_UPL);

				}

			}

		}
		log.debug(CLASS, "flagMLDForUpliftedMailbags");

	}
	/**
	 * Method : MLDController.triggerMLDMessages Added by : A-5526 on Dec 17, 2015 Used for : to triggerMLDMessages Parameters : @param String
	 * @throws SystemException
	 */
	public void triggerMLDMessages(String companyCode, int recordCount) {
		log.debug(CLASS + " : " + "triggerMLDMessages" + " Entering");
		Collection<MLDMasterVO> mldMasterVOs = new MLDMessageMaster().findMLDDetails(companyCode, recordCount);
		sendMLDMessages(mldMasterVOs);
		log.debug(CLASS + " : " + "triggerMLDMessages" + " Exiting");
	}

	/**
	 * Method : MLDController.sendMLDMessages Added by : A-5526 on Dec 17, 2015 Used for : to sendMLDMessages Parameters : @param Collection<MLDMasterVO>
	 * @throws SystemException
	 */
	void sendMLDMessages(Collection<MLDMasterVO> mldMasterVOs) {
		log.debug(CLASS + " : " + "sendMLDMessages" + " Entering");
		if ((mldMasterVOs == null) || (mldMasterVOs.size() <= 0)) {
			return;
		}
		Map<String, Collection<MLDMasterVO>> eventModeCollections = new HashMap<>();
		for (MLDMasterVO mldMasterVO : mldMasterVOs) {
			MLDDetailVO mldDetailVO = mldMasterVO.getMldDetailVO();
			if ((MailConstantsVO.MLD_REC.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_ALL.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_UPL.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_NST.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_RET.equals(mldMasterVO.getEventCOde()))
					&& ((mldDetailVO.getFlightNumberOub() != null) || (mldDetailVO.getMailModeOub() != null
					&& MailConstantsVO.MLD_MODE_CARRIER.equals(mldDetailVO.getMailModeOub())
					&& (mldDetailVO.getCarrierCodeOub() != null && mldDetailVO.getCarrierIdOub() != 0)))) {
				String flightNumber = mldDetailVO.getFlightNumberOub() != null ? mldDetailVO.getFlightNumberOub()
						: "FFFF";
				String key = mldMasterVO.getEventCOde() + mldMasterVO.getSenderAirport()
						+ mldMasterVO.getReceiverAirport() + mldMasterVO.getUldNumber() + mldDetailVO.getCarrierIdOub()
						+ flightNumber + mldDetailVO.getFlightSequenceNumberOub();
				eventModeCollections = constructMLDMasterCollections(eventModeCollections, mldMasterVO, key);
			}
			if (MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde())
					&& (mldDetailVO.getFlightNumberInb() != null && mldDetailVO.getFlightSequenceNumberInb() != 0)) {
				String key = mldMasterVO.getEventCOde() + mldMasterVO.getSenderAirport()
						+ mldMasterVO.getReceiverAirport() + mldMasterVO.getUldNumber() + mldDetailVO.getCarrierIdInb()
						+ mldDetailVO.getFlightNumberInb() + mldDetailVO.getFlightSequenceNumberInb();
				if ("U".equals(mldMasterVO.getTransactionLevel())) {
					eventModeCollections = constructMLDMasterCollectionsForRCF(eventModeCollections, mldMasterVO, key);
				} else {
					eventModeCollections = constructMLDMasterCollections(eventModeCollections, mldMasterVO, key);
				}
			}
			if (((MailConstantsVO.MLD_HND.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde()))
					&& ((mldDetailVO.getFlightNumberInb() != null && mldDetailVO.getFlightSequenceNumberInb() != 0)
					|| (MailConstantsVO.MLD_MODE_CARRIER.equals(mldDetailVO.getMailModeInb())
					&& mldDetailVO.getCarrierCodeInb() != null)))) {
				String flightNumber = mldDetailVO.getFlightNumberInb() != null ? mldDetailVO.getFlightNumberInb()
						: "FFFF";
				String key = mldMasterVO.getEventCOde() + mldMasterVO.getSenderAirport()
						+ mldMasterVO.getReceiverAirport() + mldMasterVO.getUldNumber() + mldDetailVO.getCarrierIdInb()
						+ flightNumber + mldDetailVO.getFlightSequenceNumberInb();
				eventModeCollections = constructMLDMasterCollections(eventModeCollections, mldMasterVO, key);
			}
			if (MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde())) {
				String flightNumber = mldDetailVO.getFlightNumberInb() != null ? mldDetailVO.getFlightNumberInb()
						: "FFFF";
				String key = mldMasterVO.getEventCOde() + mldMasterVO.getSenderAirport()
						+ mldMasterVO.getReceiverAirport() + mldMasterVO.getUldNumber() + mldDetailVO.getCarrierIdInb()
						+ flightNumber + mldDetailVO.getFlightSequenceNumberInb();
				if ("U".equals(mldMasterVO.getTransactionLevel())) {
					eventModeCollections = constructMLDMasterCollectionsForRCF(eventModeCollections, mldMasterVO, key);
				} else {
					eventModeCollections = constructMLDMasterCollections(eventModeCollections, mldMasterVO, key);
				}
			}
			if (MailConstantsVO.MLD_HND.equals(mldMasterVO.getEventCOde())
					&& MailConstantsVO.MLD_MODE_CARRIER.equals(mldDetailVO.getMailModeInb())
					&& (mldDetailVO.getFlightNumberOub() != null)) {
				String key = mldMasterVO.getEventCOde() + mldMasterVO.getSenderAirport()
						+ mldMasterVO.getReceiverAirport() + mldMasterVO.getUldNumber() + mldDetailVO.getCarrierIdInb()
						+ mldDetailVO.getFlightNumberOub() + mldDetailVO.getFlightSequenceNumberOub();
				eventModeCollections = constructMLDMasterCollections(eventModeCollections, mldMasterVO, key);
			}
		}
		for (Entry<String, Collection<MLDMasterVO>> modeVOs : eventModeCollections.entrySet()) {
			Collection<MLDMasterVO> mLDMasterVOs = modeVOs.getValue();
			MLDMessageVO mLDMessageVO = constructMLDMessageVO(mLDMasterVOs);
			mLDMessageVO.setMessageSubType(mLDMasterVOs.iterator().next().getMessageVersion());
			
			msgBrokerMessageProxy.encodeAndSaveMessage(mLDMessageVO);
		}
		log.debug(CLASS + " : " + "sendMLDMessages" + " Exiting");
	}

	/**
	 * Method : MLDController.updateMLDMessageSendStatus Added by : A-5526 on Dec 17, 2015 Used for : to updateMLDMessageSendStatus Parameters : @param MLDMasterVO
	 * @throws SystemException
	 */
	private void updateMLDMessageSendStatus(MLDMasterVO mLDMasterVO) {
		log.debug(CLASS + " : " + "updateMLDMessageSendStatus" + " Entering");
		MLDMessageMaster mLDMessageMaster = null;
		MLDMessageMasterPK mLDMessageMasterPK = new MLDMessageMasterPK();
		mLDMessageMasterPK.setCompanyCode(mLDMasterVO.getCompanyCode());
		mLDMessageMasterPK.setSerialNumber(mLDMasterVO.getSerialNumber());
		try {
			mLDMessageMaster = MLDMessageMaster.find(mLDMessageMasterPK);
		} catch (FinderException e) {
			e.printStackTrace();
		}
		if (mLDMessageMaster != null) {
			mLDMessageMaster.setProcessStatus("SND");
		}
		log.debug(CLASS + " : " + "updateMLDMessageSendStatus" + " Exiting");
	}

	/**
	 * Method : MLDController.constructMLDMessageVO Added by : A-5526 on Dec 17, 2015 Used for : to constructMLDMessageVO Parameters : @param Collection<MLDMasterVO> Return : MLDMessageVO
	 * @throws SystemException
	 */
	private MLDMessageVO constructMLDMessageVO(Collection<MLDMasterVO> mLDMasterVOs) {
		log.debug(CLASS + " : " + "constructMLDMessageVO" + " Entering");
		MLDMessageVO mLDMessageVO = new MLDMessageVO();
		MLDMasterVO mLDMasterVO = null;
		MLDMessageDetailVO mLDMessageDetailVOToTriger = null;
		if (mLDMasterVOs != null && mLDMasterVOs.size() > 0) {
			mLDMasterVO = mLDMasterVOs.iterator().next();
			mLDMessageVO.setCompanyCOde(mLDMasterVO.getCompanyCode());
			mLDMessageVO.setCompanyCode(mLDMasterVO.getCompanyCode());
			mLDMessageVO.setMessageVersion(mLDMasterVO.getMessageVersion());
			mLDMessageVO.setMessageSequence(mLDMasterVO.getMessageSequence());
			mLDMessageVO.setMessageType(MailConstantsVO.MLD);
			mLDMessageVO.setStationCode(mLDMasterVO.getSenderAirport());
			mLDMessageVO.setMessageStandard(MailConstantsVO.IMP);
			mLDMessageVO.setSenderID(
					findAirline(mLDMasterVO.getCompanyCode(), Integer.parseInt(mLDMasterVO.getAddrCarrier())));
			mLDMessageVO.setMldMessageDetailVOs(new ArrayList<MLDMessageDetailVO>());
			mLDMessageVO.setSerialNumber(mLDMasterVO.getSerialNumber());
			for (MLDMasterVO mLDMasterVOForTrigger : mLDMasterVOs) {
				MLDMessageDetailVO mldMessageDetailVO = new MLDMessageDetailVO();
				mLDMessageDetailVOToTriger = createMldMessageDetailVO(mLDMasterVOForTrigger, mldMessageDetailVO);
				if (mLDMessageDetailVOToTriger != null) {
					mLDMessageVO.getMldMessageDetailVOs().add(mLDMessageDetailVOToTriger);
					String count = generateCounter(mLDMasterVO.getCompanyCode());
					constructFileName(mLDMessageVO, count, mLDMasterVO);
				}
			}
		}
		log.debug(CLASS + " : " + "constructMLDMessageVO" + " Exiting");
		return mLDMessageVO;
	}

	/**
	 * Method : MLDController.createMldMessageDetailVO Added by : A-5526 on Dec 17, 2015 Used for : to createMldMessageDetailVO Parameters : @param MLDMasterVO,MLDMessageDetailVO Return : MLDMessageDetailVO
	 * @throws SystemException
	 */
	private MLDMessageDetailVO createMldMessageDetailVO(MLDMasterVO mldMasterVO,
														MLDMessageDetailVO mldMessageDetailVO) {
		log.debug(CLASS + " : " + "createMldMessageDetailVO" + " Entering");
		MLDDetailVO mldDetailVO = null;
		MLDStatusMessageVO mldStatusMessageVO = new MLDStatusMessageVO();
		mldStatusMessageVO.setDestAirport(mldMasterVO.getDestAirport());
		mldStatusMessageVO.setEventCOde(mldMasterVO.getEventCOde());
		ContainerAssignmentVO containerAssignmentVO = null;
		if (mldMasterVO.getUldNumber() != null && mldMasterVO.getUldNumber().trim().length() > 0) {
			containerAssignmentVO = new MailController().findLatestContainerAssignment(mldMasterVO.getUldNumber());
		}
		if (containerAssignmentVO != null
				&& MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())) {
			mldMessageDetailVO.setBarcodeType(MailConstantsVO.ULD_TYPE);
			mldStatusMessageVO.setExpectedInd(MailConstantsVO.FLAG_YES);
		} else {
			mldStatusMessageVO.setExpectedInd(MailConstantsVO.FLAG_NO);
			mldMessageDetailVO.setBarcodeType(MailConstantsVO.MLD_BARCODETYPE_FOR_MAIL_LEVEL);
		}
		mldStatusMessageVO.setReasonCode("");
		mldStatusMessageVO.setReceiverAirport(mldMasterVO.getReceiverAirport());

		mldStatusMessageVO.setScanTime(LocalDateMapper.toLocalDate(mldMasterVO.getScanTime()));
		mldStatusMessageVO.setSenderAirport(mldMasterVO.getSenderAirport());
		if ((mldMasterVO.getUldNumber() != null)
				&& (!mldMasterVO.getUldNumber().startsWith(MailConstantsVO.CONST_BULK))) {
			mldStatusMessageVO.setUldNumber(mldMasterVO.getUldNumber());
		}
		if ((mldMasterVO.getUldNumber() != null) && MailConstantsVO.MLD_NST.equals(mldMasterVO.getEventCOde())) {
			mldStatusMessageVO.setUldNumber(mldMasterVO.getUldNumber());
		}
		mldStatusMessageVO.setWeight(String.valueOf(mldMasterVO.getWeight().getRoundedValue().doubleValue()));
		mldStatusMessageVO.setWeightCode(mldMasterVO.getWeightCode());
		if ((MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde()))
				&& MailConstantsVO.ULD_TYPE.equalsIgnoreCase(mldMasterVO.getTransactionLevel())
				&& mldMasterVO.getUldNumber() != null
				&& mldMasterVO.getUldNumber().equals(mldMasterVO.getBarcodeValue())) {
			mldMessageDetailVO.setBarcodeValue(mldMasterVO.getUldNumber());
			mldMessageDetailVO.setBarcodeType(MailConstantsVO.ULD_TYPE);
		} else {
			mldMessageDetailVO.setBarcodeType(MailConstantsVO.MLD_BARCODETYPE_FOR_MAIL_LEVEL);
			mldMessageDetailVO.setBarcodeValue(mldMasterVO.getBarcodeValue());
		}
		mldMessageDetailVO.setMldStatusMessageVO(mldStatusMessageVO);
		if (MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde()) && mldMasterVO.getBarcodeValue() != null
				&& mldMasterVO.getBarcodeValue().trim().length() == 9) {
			mldMessageDetailVO.setBarcodeType(MailConstantsVO.ULD_TYPE);
		}
		mldDetailVO = mldMasterVO.getMldDetailVO();
		String temp = null;
		if (mldDetailVO != null) {
			MLDFlownDetailMessageVO mldFlownDetailMessageVO = new MLDFlownDetailMessageVO();
			mldFlownDetailMessageVO
					.setCarrierCode(findAirline(mldMasterVO.getCompanyCode(), mldDetailVO.getCarrierIdInb()));
			mldFlownDetailMessageVO.setEventTime(LocalDateMapper.toLocalDate(mldDetailVO.getEventTimeInb()));
			mldFlownDetailMessageVO.setFlightNumber(mldDetailVO.getFlightNumberInb());
			mldFlownDetailMessageVO.setFlightOperationDate(LocalDateMapper.toLocalDate(mldDetailVO.getFlightOperationDateInb()));
			if ((mldDetailVO.getFlightNumberInb() != null) && (!"".equals(mldDetailVO.getFlightNumberInb()))) {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_FLIGHT);
				temp = findAirline(mldMasterVO.getCompanyCode(), mldDetailVO.getCarrierIdInb())
						+ mldDetailVO.getFlightNumberInb();
			} else if (MailConstantsVO.MLD_REC.equals(mldMasterVO.getEventCOde())) {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_POSTAL_AUTHORITY);
				if (mldMasterVO.getBarcodeValue() != null && mldMasterVO.getBarcodeValue().trim().length() == 29) {
					temp = mldMasterVO.getBarcodeValue().substring(0, 6);
				}
			} else if (MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_NST.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde())) {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_POSTAL_AUTHORITY);
				if (mldMasterVO.getBarcodeValue() != null && mldMasterVO.getBarcodeValue().trim().length() == 29) {
					temp = mldMasterVO.getBarcodeValue().substring(0, 6);
				}
			}
			if (MailConstantsVO.MLD_HND.equals(mldMasterVO.getEventCOde())
					&& MailConstantsVO.MLD_MODE_CARRIER.equals(mldDetailVO.getMailModeInb())) {
				mldFlownDetailMessageVO.setMailMode(mldDetailVO.getMailModeInb());
				temp = findAirline(mldMasterVO.getCompanyCode(), mldDetailVO.getCarrierIdInb());
			}
			if ((MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde()))
					&& MailConstantsVO.MLD_MODE_CARRIER.equals(mldDetailVO.getMailModeInb())) {
				mldFlownDetailMessageVO.setMailMode(mldDetailVO.getMailModeInb());
				temp = findAirline(mldMasterVO.getCompanyCode(), mldDetailVO.getCarrierIdInb());
			}
			if (MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde())) {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_CARRIER);
				temp = mldFlownDetailMessageVO.getCarrierCode();
			}
			if (mldMasterVO.getMessageVersion() != null && "2".equals(mldMasterVO.getMessageVersion())) {
				if (MailConstantsVO.MLD_REC.equals(mldMasterVO.getEventCOde())) {
					String ooe = "";
					if (mldMasterVO.getBarcodeValue() != null && mldMasterVO.getBarcodeValue().trim().length() == 29) {
						ooe = mldMasterVO.getBarcodeValue().substring(0, 6);
					}
					temp = findPostalOperatorCode(mldMasterVO.getCompanyCode(), ooe);
				}
			}
			mldFlownDetailMessageVO.setModeDescription(temp);
			mldFlownDetailMessageVO.setOperationMode(MailConstantsVO.MLD_OPERATION_INBOUND_MODE);
			mldFlownDetailMessageVO.setPolOrPou(mldDetailVO.getPolInb());
			mldFlownDetailMessageVO.setPostalCode(mldDetailVO.getPostalCodeInb());
			if ((mldDetailVO.getFlightNumberInb() == null) || ("".equals(mldDetailVO.getFlightNumberInb()))) {
				mldFlownDetailMessageVO.setPolOrPou(mldMasterVO.getSenderAirport());
			}
			if ((mldDetailVO.getFlightNumberInb() != null) && (!"".equals(mldDetailVO.getFlightNumberInb()))) {
				mldMessageDetailVO.setInbMLDFlownDetailMessageVO(mldFlownDetailMessageVO);
			} else if (MailConstantsVO.MLD_REC.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_HND.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde())) {
				mldMessageDetailVO.setInbMLDFlownDetailMessageVO(mldFlownDetailMessageVO);
			}
			mldFlownDetailMessageVO = new MLDFlownDetailMessageVO();
			mldFlownDetailMessageVO
					.setCarrierCode(findAirline(mldMasterVO.getCompanyCode(), mldDetailVO.getCarrierIdOub()));
			mldFlownDetailMessageVO.setEventTime(LocalDateMapper.toLocalDate(mldDetailVO.getEventTimeOub()));
			mldFlownDetailMessageVO.setFlightNumber(mldDetailVO.getFlightNumberOub());
			mldFlownDetailMessageVO.setFlightOperationDate(LocalDateMapper.toLocalDate(mldDetailVO.getFlightOperationDateOub()));
			if ((mldDetailVO.getFlightNumberOub() != null) && (!"".equals(mldDetailVO.getFlightNumberOub()))
					&& mldDetailVO.getFlightSequenceNumberOub() > 0) {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_FLIGHT);
				temp = findAirline(mldMasterVO.getCompanyCode(), mldDetailVO.getCarrierIdOub())
						+ mldDetailVO.getFlightNumberOub();
			} else {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_CARRIER);
				temp = findAirline(mldMasterVO.getCompanyCode(), mldDetailVO.getCarrierIdOub());
			}
			mldFlownDetailMessageVO.setModeDescription(temp);
			mldFlownDetailMessageVO.setOperationMode(MailConstantsVO.MLD_OPERATION_OUTBOUND_MODE);
			if (MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde())) {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_POSTAL_AUTHORITY);
				if (mldMasterVO.getBarcodeValue() != null && mldMasterVO.getBarcodeValue().trim().length() == 29) {
					temp = mldMasterVO.getBarcodeValue().substring(6, 12);
				}
				mldFlownDetailMessageVO.setModeDescription(temp);
			}
			if (MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde())) {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_CARRIER);
				temp = mldFlownDetailMessageVO.getCarrierCode();
				mldFlownDetailMessageVO.setModeDescription(temp);
			}
			if (mldMasterVO.getMessageVersion() != null && "2".equals(mldMasterVO.getMessageVersion())) {
				if (MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde())
						|| MailConstantsVO.MLD_RET.equals(mldMasterVO.getEventCOde())) {
					String ooe = "";
					if (mldMasterVO.getBarcodeValue() != null && mldMasterVO.getBarcodeValue().trim().length() == 29) {
						if (MailConstantsVO.MLD_RET.equals(mldMasterVO.getEventCOde())) {
							ooe = mldMasterVO.getBarcodeValue().substring(0, 6);
						} else {
							ooe = mldMasterVO.getBarcodeValue().substring(6, 12);
						}
					}
					temp = findPostalOperatorCode(mldMasterVO.getCompanyCode(), ooe);
					mldFlownDetailMessageVO.setModeDescription(temp);
					mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_POSTAL_AUTHORITY);
				}
			}
			mldFlownDetailMessageVO.setPolOrPou(mldDetailVO.getPouOub());
			mldFlownDetailMessageVO.setPostalCode(mldDetailVO.getPostalCodeOub());
			if ((mldDetailVO.getFlightNumberOub() != null) && (!"".equals(mldDetailVO.getFlightNumberOub()))) {
				mldMessageDetailVO.setOubMLDFlownDetailMessageVO(mldFlownDetailMessageVO);
			}
			if (MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde())) {
				mldMessageDetailVO.setOubMLDFlownDetailMessageVO(mldFlownDetailMessageVO);
			}
			if (((MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde()))
					|| (MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde()))
					|| (MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde()))
					|| (MailConstantsVO.MLD_RET.equals(mldMasterVO.getEventCOde())))
					&& (mldDetailVO.getFlightNumberOub() != null || mldDetailVO.getCarrierIdOub() > 0)) {
				mldMessageDetailVO.setOubMLDFlownDetailMessageVO(mldFlownDetailMessageVO);
			}
			if (MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde()) && containerAssignmentVO != null
					&& MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())) {
				mldMasterVO.setBarcodeType(MailConstantsVO.ULD_TYPE);
				mldMasterVO.setBarcodeValue(mldMasterVO.getUldNumber());
			}
		}
		log.debug(CLASS + " : " + "createMldMessageDetailVO" + " Exiting");
		return mldMessageDetailVO;
	}

	/**
	 * @author A-8061
	 * @param companyCode
	 * @param ooe
	 * @return
	 */
	private String findPostalOperatorCode(String companyCode, String ooe) {
		String paCode = "";
		String partyIdentifier = "";
		try {
			paCode = new MailController().findPAForOfficeOfExchange(companyCode, ooe);
			if (paCode != null && !"".equals(paCode)) {
				PostalAdministrationVO postalAdministrationVO = new MailController().findPACode(companyCode, paCode);
				if (Objects.nonNull(postalAdministrationVO)
						&& Objects.nonNull(postalAdministrationVO.getPostalAdministrationDetailsVOs())
						&& !postalAdministrationVO.getPostalAdministrationDetailsVOs().isEmpty()) {
					partyIdentifier = getPartyIdentifierForPA(postalAdministrationVO);
				}
			}
		} finally {
		}
		return partyIdentifier;
	}

	private String getPartyIdentifierForPA(PostalAdministrationVO postalAdministrationVO) {
		Collection<PostalAdministrationDetailsVO> postalAdministrationDetailsVOs;
		String partyIdentifier = "";
		postalAdministrationDetailsVOs = postalAdministrationVO.getPostalAdministrationDetailsVOs().get("INVINFO");
		if (postalAdministrationDetailsVOs != null && !postalAdministrationDetailsVOs.isEmpty()) {
			for (PostalAdministrationDetailsVO postalAdministrationDetailsVO : postalAdministrationDetailsVOs) {
				if ("UPUCOD".equals(postalAdministrationDetailsVO.getParCode())
						&& postalAdministrationDetailsVO.getPartyIdentifier() != null) {
					partyIdentifier = postalAdministrationDetailsVO.getPartyIdentifier();
				}
			}
		}
		return partyIdentifier;
	}
	/**
	 * @author A-8353
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public String generateCounter(String companyCode) {
		String key = null;
		KeyCondition keyCondition =  new KeyCondition();
		keyCondition.setKey("MLD_KEY_TYP");
		keyCondition.setValue("MLD_KEY_TYP");
		Criterion criterion = new CriterionBuilder()
				.withSequence("MLD_KEY_SEQ")
				.withKeyCondition(keyCondition)
				.withPrefix("").build();
		KeyUtils keyUtils = ContextUtil.getInstance().getBean(KeyUtils.class);
		key =  (keyUtils.getKey(criterion));
		return key;
	}

	/**
	 * @author A-8353
	 * @param key
	 * @param maxLength
	 * @return
	 */
	private String checkLength(String key, int maxLength) {
		String modifiedKey = null;
		StringBuilder buildKey = new StringBuilder();
		modifiedKey = new StringBuilder().append(key).toString();
		int keyLength = modifiedKey.length();
		if (modifiedKey.length() < maxLength) {
			int diff = maxLength - keyLength;
			String val = null;
			for (int i = 0; i < diff; i++) {
				val = buildKey.append("0").toString();
			}
			modifiedKey = new StringBuilder().append(val).append(key).toString();
		}
		return modifiedKey;
	}

	/**
	 * @author A-8353
	 * @param mLDMessageVO
	 * @param count
	 */
	public void constructFileName(MLDMessageVO mLDMessageVO, String count, MLDMasterVO mLDMasterVO) {
		String fileName = null;
		ZonedDateTime localdate = null;
		String airportCode = null;
		if (MailConstantsVO.MLD_DLV.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_HND.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_RCF.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			airportCode = mLDMasterVO.getReceiverAirport();
		} else {
			airportCode = mLDMasterVO.getSenderAirport();
		}
		if (airportCode != null) {
			localdate = localDateUtil.getLocalDate(airportCode, false);
		} else {
			localdate = localDateUtil.getLocalDate(null, false);
		}
		String appendingDate = localDateUtil.toUTCTime(localdate).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		fileName = new StringBuilder().append(mLDMessageVO.getSenderID()).append("_").append(airportCode).append("_")
				.append("iCargo").append("_").append(appendingDate).append("_").append(count).toString();
		mLDMessageVO.setMldFileName(fileName);
	}

	/**
	 * Method:MLDController.constructMLDMasterCollections
	 * @param eventModeCollections
	 * @param mldMasterVO
	 * @param key
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Collection<MLDMasterVO>> constructMLDMasterCollections(
			Map<String, Collection<MLDMasterVO>> eventModeCollections, MLDMasterVO mldMasterVO, String key) {
		if (eventModeCollections.get(key) == null) {
			Collection<MLDMasterVO> modeMasterVOs = new ArrayList<>();
			mldMasterVO.setProcessStatus(MailConstantsVO.FLAG_YES);
			updateMLDMessageSendStatus(mldMasterVO);
			modeMasterVOs.add(mldMasterVO);
			eventModeCollections.put(key, modeMasterVOs);
		} else {
			Collection<MLDMasterVO> modeMasterVOs = eventModeCollections.get(key);
			mldMasterVO.setProcessStatus(MailConstantsVO.FLAG_YES);
			updateMLDMessageSendStatus(mldMasterVO);
			modeMasterVOs.add(mldMasterVO);
			eventModeCollections.put(key, modeMasterVOs);
		}
		return eventModeCollections;
	}
	public Map<String, Collection<MLDMasterVO>> constructMLDMasterCollectionsForRCF(
			Map<String, Collection<MLDMasterVO>> eventModeCollections, MLDMasterVO mldMasterVO, String key) {
		if (eventModeCollections.get(key) == null) {
			Collection<MLDMasterVO> modeMasterVOs = new ArrayList<>();
			mldMasterVO.setProcessStatus(MailConstantsVO.FLAG_YES);
			updateMLDMessageSendStatus(mldMasterVO);
			modeMasterVOs.add(mldMasterVO);
			eventModeCollections.put(key, modeMasterVOs);
		} else {
			mldMasterVO.setProcessStatus(MailConstantsVO.FLAG_YES);
			updateMLDMessageSendStatus(mldMasterVO);
		}
		return eventModeCollections;
	}	 
	public void flagMLDForMailAcceptance(MailAcceptanceVO mailAcceptanceVO,
										 Collection<MailbagVO>mailbags) throws SystemException {
		log.debug(CLASS, "flagMLDForMailAcceptance");

		if ((mailAcceptanceVO == null) || (mailbags == null)) {
			return;
		}
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		Collection<MailbagVO> transferFromCarriermailbagVOs = new ArrayList<>();
		Collection<MailbagVO> transferFromPartnerCarriermailbagVOs = new ArrayList<>();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		constructMailbagVos(mailAcceptanceVO, mailbags, mailbagVOs, transferFromCarriermailbagVOs,
				transferFromPartnerCarriermailbagVOs, logonAttributes);

		flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_REC);

		if(mailAcceptanceVO.getFlightNumber()!=null && !mailAcceptanceVO.getFlightNumber().equals("-1")){
			flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_FRESH_ALL);
		}

		flagMLDForMailOperations(transferFromCarriermailbagVOs, MLD_REC_HND);

		flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_NST);
		Collection<ContainerDetailsVO> containerdetailsVOs =mailAcceptanceVO.getContainerDetails();
		if(containerdetailsVOs!=null && !containerdetailsVOs.isEmpty()){
			for(ContainerDetailsVO containerdetailsVO:containerdetailsVOs){
				if("B".equals(containerdetailsVO.getContainerType()))
				{
					flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_STG);
				}
			}
		}
		flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_RCT);
		flagMLDForMailOperations(transferFromPartnerCarriermailbagVOs, MailConstantsVO.MLD_TFD);

		log.debug(CLASS, "flagMLDForMailAcceptance");
	}
	private void constructMailbagVos(MailAcceptanceVO mailAcceptanceVO, Collection<MailbagVO> mailbags,
									 Collection<MailbagVO> mailbagVOs, Collection<MailbagVO> transferFromCarriermailbagVOs,
									 Collection<MailbagVO> transferFromPartnerCarriermailbagVOs, LoginProfile logonAttributes)
			throws SystemException {
		if ((mailbags != null) && !mailbags.isEmpty() ) {
			for (MailbagVO mailbagVO : mailbags) {
				transferFromCarriermailbagVOs(mailAcceptanceVO, mailbagVOs, transferFromCarriermailbagVOs, mailbagVO);
				transferFromPartnerCarriermailbagVOs(transferFromPartnerCarriermailbagVOs, logonAttributes, mailbagVO);	 

	   }
		}
	}
	private void transferFromPartnerCarriermailbagVOs(Collection<MailbagVO> transferFromPartnerCarriermailbagVOs,
													  LoginProfile logonAttributes, MailbagVO mailbagVO) throws SystemException {
		if (mailbagVO.getTransferFromCarrier() != null && !mailbagVO.getTransferFromCarrier().isEmpty()) {
			String companyCode = mailbagVO.getCompanyCode();
			String ownCarrierCode = logonAttributes.getOwnAirlineCode();
			String airportCode = logonAttributes.getAirportCode();
			Collection<PartnerCarrierVO> partnerCarierVos =
					mailOperationsMapper.partnerCarrierModelstoPartnerCarrierVos(
							mailMasterApi.findAllPartnerCarriers(companyCode, ownCarrierCode,
									airportCode));
			String carrierCode = mailbagVO.getTransferFromCarrier();
			if (partnerCarierVos != null) {
				for (PartnerCarrierVO partner : partnerCarierVos) {
					String partnerCarrier = partner.getPartnerCarrierCode();
					if (partnerCarrier.equals(carrierCode) && ( partner.getMldTfdReq()!=null &&"A".equals(partner.getMldTfdReq()))) {
						transferFromPartnerCarriermailbagVOs.add(mailbagVO);
					}
				}
			}
		}
	}
	private void transferFromCarriermailbagVOs(MailAcceptanceVO mailAcceptanceVO, Collection<MailbagVO> mailbagVOs,
											   Collection<MailbagVO> transferFromCarriermailbagVOs, MailbagVO mailbagVO) {
		if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
			if(mailbagVO.getFlightSequenceNumber()==0 && mailAcceptanceVO.getFlightSequenceNumber()!=0) {
				mailbagVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
			}
			mailbagVOs.add(mailbagVO);
			if(mailbagVO.getTransferFromCarrier()!=null && !mailbagVO.getTransferFromCarrier().isEmpty()){
				transferFromCarriermailbagVOs.add(mailbagVO);
			}
		}
	}
	public void flagMLDForMailbagReturn(
			Collection<MailbagVO>mailbags) throws SystemException {
		log.debug(CLASS, "flagMLDForMailbagReturn");
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		if ((mailbags != null) && (mailbags.size() > 0)) {
			for (MailbagVO mailbagVO : mailbags) {
				mailbagVOs.add(mailbagVO);
			}
		}
		flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_RET);
		log.debug(CLASS, "flagMLDForMailbagReturn");
	}
	public void flagMLDForMailOperationsInULD(ContainerVO containerVo,
											  String mode) throws SystemException{
		log.debug(CLASS, "flagMLDForMailOperationsInULD");
		if ((containerVo == null)) {
			return;
		}
		Collection<MLDMasterVO> mldMasterVOs = createMLDVOsFromUldVOs(
				containerVo, mode);
		for (MLDMasterVO mldMasterVO : mldMasterVOs) {
			if ((MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde())&& mldMasterVO.issTGRequired() )) {
				new MLDMessageMaster(mldMasterVO);
			}
		}
		log.debug(CLASS, "flagMLDForMailOperationsInULD");
	}
	public Collection<MLDMasterVO> createMLDVOsFromUldVOs(ContainerVO containerVo, String mode) throws SystemException {
		log.debug(CLASS, "createMLDVOsFromUldVOs");
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
		LoginProfile logon = null;
		logon = contextUtil.callerLoginProfile();
		String airport = "";
		airport = containerVo.getAssignedPort();
		int carrierIdentifier =containerVo.getCarrierId();
		MLDConfigurationFilterVO filterVO = new MLDConfigurationFilterVO();
		filterVO.setCompanyCode(logon.getCompanyCode());
		filterVO.setCarrierCode(containerVo.getCarrierCode());
		filterVO.setAirportCode(containerVo.getAssignedPort());
		filterVO.setCarrierIdentifier(carrierIdentifier);
		Collection<MLDConfigurationVO> configVOs = findMLDCongfigurations(filterVO);
		MLDConfigurationVO	configVO = null;
		String mldVersion = "";
		if(!configVOs.isEmpty()) {
			configVO = configVOs.iterator().next();
			mldVersion = configVO.getMldversion();
		}
		MLDMasterVO masterVO = constructMLDMasterVO(configVO);
		if (mldVersion.equals(MailConstantsVO.MLD_VERSION2)) {
			masterVO = createMLDVOsForVersion2(containerVo, mldVersion, mode, airport, logon,masterVO);
			if (masterVO != null) {
				mldMasterVOs.add(masterVO);
			}
		}else {
			masterVO = createMLDVOsForVersion1(containerVo, mldVersion, mode, airport,logon,masterVO);
			if (masterVO != null) {
				mldMasterVOs.add(masterVO);
			}
		}
		log.debug(CLASS, "createMLDVOsFromUldVOs");
		return mldMasterVOs;
	}
	public void flagMLDForMailbagTransfer(Collection<MailbagVO> mailbagVOs,
										  ContainerVO containerVO, OperationalFlightVO operationalFlightVO,String mode)
			throws SystemException {
		log.debug(CLASS, "flagMLDForMailbagTransfer");
		if (mailbagVOs == null) {
			return;
		}
		LoginProfile logonAttributes =contextUtil.callerLoginProfile();
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		String airport = "";
		String carrier = "";
		ArrayList<String> partnerCarriers = new ArrayList<>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			airport = mailbagVO.getScannedPort();
			carrier = mailbagVO.getCarrierCode();
			String companyCode = logonAttributes.getCompanyCode();
			int carrierIdentifier = findCarrierIdentifier(companyCode, carrier);
			String mldVersion = mailMasterApi.getMLDVersion(carrierIdentifier, companyCode, airport);
			if ((mldVersion.equalsIgnoreCase(MailConstantsVO.MLD_VERSION1) && (mode.equalsIgnoreCase(MailConstantsVO.MLD_HND) ||
					mode.equalsIgnoreCase(MailConstantsVO.MLD_ALL))) || (mldVersion.equalsIgnoreCase(MailConstantsVO.MLD_VERSION2) &&
					(mode.equalsIgnoreCase(MailConstantsVO.MLD_STG) ||
							mode.equalsIgnoreCase(MailConstantsVO.MLD_NST) ||
							mode.equalsIgnoreCase(MailConstantsVO.MLD_TFD)))) {
				if (MailConstantsVO.MLD_TFD.equals(mode)) {
					boolean isPartnerCarrier = false;
					isPartnerCarrier = partnerCarrierCheck(logonAttributes, carrier, partnerCarriers, mailbagVO,
							isPartnerCarrier);
					if (!isPartnerCarrier && logonAttributes.getOwnAirlineCode().equals(containerVO.getCarrierCode())) {
						break;
					}
				}
				if (operationalFlightVO == null) {
					mldMasterVO = new MLDMasterVO();
					mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
					mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
					mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
					mldMasterVO.setEventCOde(mode);
					mldMasterVO.setScanTime(mailbagVO.getScannedDate());
					mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
					findSenderOrReceiverAirportCodes(mailbagVO, null, mldMasterVO, MailConstantsVO.MLD_HND);
					mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
					String destinationAirport = "";
					if (mailbagVO.getMailbagId() != null
							&& mailbagVO.getMailbagId().trim().length() == 29) {
						mldMasterVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
								BigDecimal.valueOf(Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29)))));
						destinationAirport = new MailUploadController()
								.findAirportCityForMLD(mailbagVO.getCompanyCode(),
										mailbagVO.getMailbagId().substring(8, 11));
					}
					mldMasterVO.setDestAirport(destinationAirport);
					mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
					mldMasterVO.setLastUpdatedUser(containerVO.getLastUpdateUser());
					mldMasterVO.setProcessStatus("NEW");
					mldMasterVO.setMessagingMode("O");
					mldMasterVO.setMessageSequence(1);
					mldMasterVO.setMessageVersion(mldVersion);
					mldMasterVO.setUldWeightCode("KG");
					mldMasterVO.setWeightCode("HG");
					mldDetailVO = new MLDDetailVO();
					if (mailbagVO.getFlightSequenceNumber() > 0) {
						mldDetailVO.setMailModeInb("F");
					}
					mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
					mldDetailVO.setMailIdr(mailbagVO.getMailbagId());
					mldDetailVO.setMessageSequence(1);
					mldDetailVO.setCarrierCodeOub(containerVO.getCarrierCode());
					mldDetailVO.setCarrierIdOub(containerVO.getCarrierId());
					mldDetailVO.setFlightSequenceNumberOub(containerVO
							.getFlightSequenceNumber());
					mldDetailVO.setFlightNumberOub(containerVO.getFlightNumber());
					if (mldDetailVO.getFlightNumberOub() != null)
					{
						updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO);
					}
					mldDetailVO.setPouOub(containerVO.getPou());
					if (containerVO.getFlightSequenceNumber() > 0) {
						mldDetailVO.setMailModeOub("F");
						mldDetailVO.setModeDescriptionOub("Flight Operation");
					} else {
						mldDetailVO.setMailModeOub("H");
						mldDetailVO.setModeDescriptionOub("Carrier Operation");
					}
					mldDetailVO.setPostalCodeOub(containerVO.getPaCode());
					mldDetailVO.setCarrierCodeInb(mailbagVO.getCarrierCode());
					mldDetailVO.setFlightNumberInb(mailbagVO.getFlightNumber());
					mldDetailVO.setFlightSequenceNumberInb(mailbagVO
							.getFlightSequenceNumber());
					if (mldDetailVO.getFlightNumberInb() != null)
					{
						updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND, mldDetailVO);
					}
					mldDetailVO.setCarrierIdInb(mailbagVO.getCarrierId());
					mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
					if (mailbagVO.getFlightSequenceNumber() > 0) {
						mldDetailVO.setMailModeInb("F");
					} else {
						mldDetailVO.setMailModeOub("H");
					}
					mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
					mldDetailVO.setPolInb(mailbagVO.getPol());
					mldMasterVO.setMldDetailVO(mldDetailVO);
					mldMasterVOs.add(mldMasterVO);
				}
				if (MailConstantsVO.MLD_HND.equals(mode)) {
					mldMasterVO = new MLDMasterVO();
					mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
					mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
					mldMasterVO.setEventCOde(MailConstantsVO.MLD_ALL);
					mldMasterVO.setScanTime(mailbagVO.getScannedDate());
					if (mailbagVO.getPou() == null) {
						mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
					} else {
						mldMasterVO.setSenderAirport(mailbagVO.getPou());
					}
					mldMasterVO.setReceiverAirport(mailbagVO.getPou());
					String destination = "";
					if (mailbagVO.getMailbagId() != null
							&& mailbagVO.getMailbagId().trim().length() == 29) {
						mldMasterVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
								BigDecimal.valueOf(Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29)))));
						destination = new MailUploadController().findAirportCityForMLD(
								mailbagVO.getCompanyCode(), mailbagVO.getMailbagId()
										.substring(8, 11));
					}
					mldMasterVO.setDestAirport(destination);
					if (mldMasterVO.getSenderAirport() != null && mldMasterVO.getSenderAirport().equals(mldMasterVO.getReceiverAirport()) && containerVO != null) {
						mldMasterVO.setReceiverAirport(containerVO.getPou());
					}
					if (mailbagVO.getFlightSequenceNumber() > 0) {
						findSenderOrReceiverAirportCodes(mailbagVO, null, mldMasterVO, MailConstantsVO.MLD_ALL);
					}
					if (mldMasterVO.getReceiverAirport() == null || mldMasterVO.getReceiverAirport().isEmpty()) {
						mldMasterVO.setReceiverAirport(mldMasterVO.getDestAirport());
					}
					if (mailbagVO.getContainerNumber() != null) {
						mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
					} else if (containerVO != null) {
						mldMasterVO.setUldNumber(containerVO.getContainerNumber());
					}
					mldMasterVO.setLastUpdatedUser(mailbagVO.getScannedUser());
					mldMasterVO.setProcessStatus("NEW");
					mldMasterVO.setMessagingMode("O");
					mldMasterVO.setMessageSequence(1);
					mldMasterVO.setMessageVersion("1");
					mldMasterVO.setUldWeightCode("KG");
					mldMasterVO.setWeightCode("HG");
					mldDetailVO = new MLDDetailVO();
					mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
					mldDetailVO.setMailIdr(mailbagVO.getMailbagId());
					mldDetailVO.setMessageSequence(1);
					mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
					mldDetailVO.setCarrierIdOub(mailbagVO.getCarrierId());
					mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
					mldDetailVO.setFlightSequenceNumberOub(mailbagVO
							.getFlightSequenceNumber());
					if (operationalFlightVO != null) {
						updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO);
					} else {
						mldDetailVO.setFlightNumberOub(containerVO.getFlightNumber());
						mldDetailVO.setFlightSequenceNumberOub(containerVO
								.getFlightSequenceNumber());
						updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO);
					}
					mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
					mldDetailVO.setPouOub(mailbagVO.getPou());
					mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());
					if (containerVO.getFlightSequenceNumber() > 0) {
						mldDetailVO.setMailModeOub("F");
						mldDetailVO.setModeDescriptionOub("Flight Operation");
					} else {
						mldDetailVO.setMailModeOub("H");
						mldDetailVO.setModeDescriptionOub("Carrier Operation");
					}
					mldMasterVO.setMldDetailVO(mldDetailVO);
					mldMasterVOs.add(mldMasterVO);
				}
			}
		}
		for (MLDMasterVO mldMasterVOLoop : mldMasterVOs) {
			new MLDMessageMaster(mldMasterVOLoop);
		}
		log.debug(CLASS, "flagMLDForMailbagTransfer");
	}
	private boolean partnerCarrierCheck(LoginProfile logonAttributes, String carrier,
										ArrayList<String> partnerCarriers, MailbagVO mailbagVO, boolean isPartnerCarrier) throws SystemException {
		String companyCode=mailbagVO.getCompanyCode();
		String ownCarrierCode=logonAttributes.getOwnAirlineCode();
		String airportCode=mailbagVO.getScannedPort();
		Collection<PartnerCarrierVO> partnerCarierVos =
				mailOperationsMapper.partnerCarrierModelstoPartnerCarrierVos(
						mailMasterApi.findAllPartnerCarriers(companyCode, ownCarrierCode,
								airportCode));
		if (!partnerCarierVos.isEmpty()) {
			for (PartnerCarrierVO partner : partnerCarierVos) {
				String partnerCarrier = partner.getPartnerCarrierCode();
				partnerCarriers.add(partnerCarrier);
				if (partnerCarriers.contains(carrier)
						&& (partner.getMldTfdReq()!=null && ("T".equals(partner.getMldTfdReq()) || "A".equals(partner.getMldTfdReq())))) {
					isPartnerCarrier = true;
				}
			}
		}
		return isPartnerCarrier;
	}
	 public void flagMLDForMailReassignOperations(
			Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO,
			String mode) throws SystemException {
		log.debug(CLASS, "flagMLDForMailReassignOperations");
		if ((mailbagVOs == null) || (mailbagVOs.size() <= 0)) {
			return;
		}
		Collection<MLDMasterVO> mldMasterVOs = createMLDVOsFromMailbagVOs(
				mailbagVOs, toContainerVO, mode);

		for (MLDMasterVO mldMasterVO : mldMasterVOs) {
			new MLDMessageMaster(mldMasterVO);
		}

		log.debug(CLASS, "flagMLDForMailReassignOperations");
	}
	public void flagMLDForContainerTransfer(Collection<MailbagVO> mailbagVOs,
											Collection<ContainerVO> containerVOs,
											OperationalFlightVO operationalFlightVO) throws SystemException {
		log.debug(CLASS, "flagMLDForContainerTransfer");
		if (mailbagVOs == null) {
			return;
		}
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		ContainerVO containerVO = null;
		LoginProfile logonAttributes =contextUtil.callerLoginProfile();
		String airport = "";
		String carrier = "";
		for (MailbagVO mailbagVO : mailbagVOs) {
			if (containerVOs != null && containerVOs.size() > 0) {
				containerVO = containerVOs.iterator().next();
			}
			if (containerVO != null) {
				airport = mailbagVO.getScannedPort();
				AirlineValidationVO airlineValidationVO = null;
				if(containerVO.getCarrierCode()==null){
					try{
						airlineValidationVO= new SharedAirlineProxy()
								.findAirline(mailbagVO.getCompanyCode(), mailbagVO.getCarrierId());
					}catch (SharedProxyException sharedProxyException) {
						log.debug(String.valueOf(sharedProxyException));
					} catch (SystemException ex) {
						log.debug(String.valueOf(ex));
					}
				}
				carrier = airlineValidationVO!=null ? airlineValidationVO.getAlphaCode():"";
				int carrierIdentifier =findCarrierIdentifier(
						logonAttributes.getCompanyCode(), carrier);
				String mldVersion=mailMasterApi.getMLDVersion(carrierIdentifier,logonAttributes.getCompanyCode(),airport);
				if(!mldVersion.equalsIgnoreCase(MailConstantsVO.MLD_VERSION1)){
					continue;
				}
				mailbagVO.setPol(containerVO.getAssignedPort());
				mldMasterVO = new MLDMasterVO();
				mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
				mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
				mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				mldMasterVO.setEventCOde(MailConstantsVO.MLD_HND);
				mldMasterVO.setScanTime(mailbagVO.getScannedDate());
				mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
				mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
				String destinationAirport = "";
				if (mailbagVO.getMailbagId() != null
						&& mailbagVO.getMailbagId().trim().length() == 29) {
					mldMasterVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
							BigDecimal.valueOf(Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29)))));
					destinationAirport = new MailUploadController()
							.findAirportCityForMLD(mailbagVO.getCompanyCode(),
									mailbagVO.getMailbagId().substring(8, 11));
				}
				mldMasterVO.setDestAirport(destinationAirport);
				if(mailbagVO.getContainerNumber()!=null){
					mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
				}else if(containerVO!=null){
					mldMasterVO.setUldNumber(containerVO.getContainerNumber());
				}
				mldMasterVO.setLastUpdatedUser(containerVO.getLastUpdateUser());
				mldMasterVO.setProcessStatus("NEW");
				mldMasterVO.setMessagingMode("O");
				mldMasterVO.setMessageSequence(1);
				mldMasterVO.setMessageVersion("1");
				mldMasterVO.setUldWeightCode("KG");
				mldMasterVO.setWeightCode("HG");
				mldDetailVO = new MLDDetailVO();
				if (mailbagVO.getFlightSequenceNumber() > 0) {
					mldDetailVO.setMailModeInb("F");
				}
				mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
				mldDetailVO.setMailIdr(mailbagVO.getMailbagId());
				mldDetailVO.setMessageSequence(1);
				mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
				mldDetailVO.setCarrierIdOub(operationalFlightVO.getCarrierId());
				mldDetailVO.setFlightSequenceNumberOub(operationalFlightVO
						.getFlightSequenceNumber());
				mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
				mldDetailVO.setPouOub(mailbagVO.getPou());
				updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
				mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
				if (mailbagVO.getFlightSequenceNumber() > 0) {
					mldDetailVO.setMailModeOub("F");
				} else {
					mldDetailVO.setMailModeOub("H");
				}
				mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());
				mldDetailVO.setCarrierCodeInb(containerVO.getCarrierCode());
				mldDetailVO.setFlightNumberInb(containerVO.getFlightNumber());
				mldDetailVO.setFlightSequenceNumberInb(containerVO
						.getFlightSequenceNumber());
				//Added as part of bug ICRD-144653 by A-5526
				updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
				//Commented as part of bug ICRD-144653 by A-5526
				//mldDetailVO.setFlightOperationDateInb(containerVO.getFlightDate());
				mldDetailVO.setCarrierIdInb(containerVO.getCarrierId());
				mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
				if (containerVO.getFlightSequenceNumber() > 0) {
					mldDetailVO.setMailModeInb("F");
				} else {
					mldDetailVO.setMailModeOub("H");
				}
				mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
				mldMasterVO.setMldDetailVO(mldDetailVO);
				mldMasterVOs.add(mldMasterVO);
			}
		}
		for (MLDMasterVO mldMasterVOLoop : mldMasterVOs) {
			new MLDMessageMaster(mldMasterVOLoop);
		}
		ContainerVO containerVo = containerVOs.iterator().next();
		flagMLDForMailbagTransfer(mailbagVOs, containerVo, operationalFlightVO,MailConstantsVO.MLD_HND);
		flagRCFmessageForMailbags(mailbagVOs, containerVo, operationalFlightVO);
		flagMLDmessageForMailbags(mailbagVOs, containerVo, operationalFlightVO,MailConstantsVO.MLD_TFD);
		log.debug(CLASS, "flagMLDForContainerTransfer");
	}
	public void flagMLDmessageForMailbags(Collection<MailbagVO> mailbagVOs,
										  ContainerVO containerVO, OperationalFlightVO operationalFlightVO,String mode)
			throws SystemException {
		log.debug(CLASS, "flagMLDmessageForMailbags");
		if (mailbagVOs == null) {
			return;
		}
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		if(MailConstantsVO.MLD_TFD.equals(mode)){
			if(logonAttributes.getOwnAirlineCode().equals(operationalFlightVO.getCarrierCode())){
				return ;
			}
		}
		String airport = "";
		String carrier = "";
		for (MailbagVO mailbagVO : mailbagVOs) {
			mldMasterVO = new MLDMasterVO();
			airport = mailbagVO.getScannedPort();
			carrier = mailbagVO.getCarrierCode();
			int carrierIdentifier =findCarrierIdentifier(
					logonAttributes.getCompanyCode(), carrier);
			String mldVersion= mailMasterApi.getMLDVersion(carrierIdentifier,logonAttributes.getCompanyCode(),airport);
			if(!mldVersion.equalsIgnoreCase(MailConstantsVO.MLD_VERSION2)){
				continue;
			}
			mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
			mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
			mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mldMasterVO.setEventCOde(mode);
			mldMasterVO.setScanTime(mailbagVO.getScannedDate());
			mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
			mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
			String destinationAirport = "";
			if (mailbagVO.getMailbagId() != null
					&& mailbagVO.getMailbagId().trim().length() == 29) {
				mldMasterVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
						BigDecimal.valueOf(Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29)))));
				destinationAirport = new MailUploadController()
						.findAirportCityForMLD(mailbagVO.getCompanyCode(),
								mailbagVO.getMailbagId().substring(8, 11));
			}
			mldMasterVO.setDestAirport(destinationAirport);
			if(mailbagVO.getContainerNumber()!=null){
				mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
			}else if(containerVO!=null){
				mldMasterVO.setUldNumber(containerVO.getContainerNumber());
			}
			mldMasterVO.setLastUpdatedUser(containerVO.getLastUpdateUser());
			mldMasterVO.setProcessStatus("NEW");
			mldMasterVO.setMessagingMode("O");
			mldMasterVO.setMessageSequence(1);
			mldMasterVO.setMessageVersion("2");
			mldMasterVO.setUldWeightCode("KG");
			mldMasterVO.setWeightCode("HG");
			mldDetailVO = new MLDDetailVO();
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeInb("F");
			}
			mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
			mldDetailVO.setMailIdr(mailbagVO.getMailbagId());
			mldDetailVO.setMessageSequence(1);
			mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
			mldDetailVO.setCarrierIdOub(operationalFlightVO.getCarrierId());
			mldDetailVO.setFlightSequenceNumberOub(operationalFlightVO
					.getFlightSequenceNumber());
			mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
			mldDetailVO.setPouOub(mailbagVO.getPou());
			updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
			mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeOub("F");
			} else {
				mldDetailVO.setMailModeOub("H");
			}
			mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());
			mldDetailVO.setCarrierCodeInb(containerVO.getCarrierCode());
			mldDetailVO.setFlightNumberInb(containerVO.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberInb(containerVO
					.getFlightSequenceNumber());
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
			mldDetailVO.setCarrierIdInb(containerVO.getCarrierId());
			mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
			if (containerVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeInb("F");
			} else {
				mldDetailVO.setMailModeOub("H");
			}
			mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
			mldDetailVO.setPolInb(mailbagVO.getPol());
			mldMasterVO.setMldDetailVO(mldDetailVO);
			mldMasterVOs.add(mldMasterVO);
		}
		for (MLDMasterVO mldMasterVOLoop : mldMasterVOs) {
			new MLDMessageMaster(mldMasterVOLoop);
		}
		log.debug(CLASS, "flagMLDmessageForMailbags");
	}
	public void flagRCFmessageForMailbags(Collection<MailbagVO> mailbagVOs,
										  ContainerVO containerVO, OperationalFlightVO operationalFlightVO)
			throws SystemException {
		log.debug(CLASS, "flagRCFmessageForMailbags");
		if (mailbagVOs == null) {
			return;
		}
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		String airport ="";
		String carrier ="";
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		for (MailbagVO mailbagVO : mailbagVOs) {
			mldMasterVO = new MLDMasterVO();
			airport = mailbagVO.getScannedPort();
			carrier = mailbagVO.getCarrierCode();
			int carrierIdentifier =findCarrierIdentifier(
					logonAttributes.getCompanyCode(), carrier);
			String mldVersion= mailMasterApi.getMLDVersion(carrierIdentifier,logonAttributes.getCompanyCode(),airport);
			if(mldVersion.equalsIgnoreCase(MailConstantsVO.MLD_VERSION2)){
				mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
				mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
				mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				mldMasterVO.setEventCOde(MailConstantsVO.MLD_RCF);
				mldMasterVO.setScanTime(mailbagVO.getScannedDate());
				if(containerVO.getTransactionLevel()!=null) {
					mldMasterVO.setTransactionLevel(containerVO.getTransactionLevel());
				}
				mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
				mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
				String destinationAirport = "";
				if (mailbagVO.getMailbagId() != null
						&& mailbagVO.getMailbagId().trim().length() == 29) {
					mldMasterVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
							BigDecimal.valueOf(Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29)))));
					destinationAirport = new MailUploadController()
							.findAirportCityForMLD(mailbagVO.getCompanyCode(),
									mailbagVO.getMailbagId().substring(8, 11));
				}
				mldMasterVO.setDestAirport(destinationAirport);
				if(mailbagVO.getContainerNumber()!=null){
					mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
				}else if(containerVO!=null){
					mldMasterVO.setUldNumber(containerVO.getContainerNumber());
				}
				mldMasterVO.setLastUpdatedUser(containerVO.getLastUpdateUser());
				mldMasterVO.setProcessStatus("NEW");
				mldMasterVO.setMessagingMode("O");
				mldMasterVO.setMessageSequence(1);
				mldMasterVO.setMessageVersion("2");
				mldMasterVO.setUldWeightCode("KG");
				mldMasterVO.setWeightCode("HG");
				mldDetailVO = new MLDDetailVO();
				if (mailbagVO.getFlightSequenceNumber() > 0) {
					mldDetailVO.setMailModeInb("F");
				}
				mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
				mldMasterVO.setBarcodeValue(mldMasterVO.getUldNumber());
				mldDetailVO.setMailIdr(mldMasterVO.getUldNumber());
				mldDetailVO.setMessageSequence(1);
				mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
				mldDetailVO.setCarrierIdOub(operationalFlightVO.getCarrierId());
				mldDetailVO.setFlightSequenceNumberOub(operationalFlightVO
						.getFlightSequenceNumber());
				mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
				mldDetailVO.setPouOub(mailbagVO.getPou());
				updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
				mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
				if (mailbagVO.getFlightSequenceNumber() > 0) {
					mldDetailVO.setMailModeOub("F");
				} else {
					mldDetailVO.setMailModeOub("H");
				}
				mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());
				mldDetailVO.setCarrierCodeInb(containerVO.getCarrierCode());
				mldDetailVO.setFlightNumberInb(containerVO.getFlightNumber());
				mldDetailVO.setFlightSequenceNumberInb(containerVO
						.getFlightSequenceNumber());
				mldDetailVO.setCarrierIdInb(containerVO.getCarrierId());
				updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
				mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
				if (containerVO.getFlightSequenceNumber() > 0) {
					mldDetailVO.setMailModeInb("F");
				} else {
					mldDetailVO.setMailModeOub("H");
				}
				mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
				mldMasterVO.setMldDetailVO(mldDetailVO);
				mldMasterVOs.add(mldMasterVO);
				break;
			}
		}
		for (MLDMasterVO mldMasterVOLoop : mldMasterVOs) {
			new MLDMessageMaster(mldMasterVOLoop);
		}
		log.debug(CLASS, "flagRCFmessageForMailbags");
	}
	  public void flagMLDForContainerReassign(
			Collection<ContainerVO> containerVOs, OperationalFlightVO toFlightVO)
			throws SystemException {
		log.debug(CLASS, "flagMLDForContainerReassign");
		LoginProfile logon = contextUtil.callerLoginProfile();
		ContainerAssignmentVO containerAssignmentVO = null;
		for(ContainerVO containerVO:containerVOs){

			Collection<MailbagVO> mailBagsToReassign = new ArrayList<MailbagVO>();
			containerAssignmentVO = Container.findLatestContainerAssignment(logon.getCompanyCode(),containerVO.getContainerNumber());
			Collection<ContainerDetailsVO> containerDetailsVOs=findContainerDetailsVO(containerVO,toFlightVO,containerAssignmentVO);

			Collection<ContainerDetailsVO> containerWithMailbagInfos = MailAcceptance
					.findMailbagsInContainer(containerDetailsVOs);
			for(ContainerDetailsVO containerDetailsVO:containerWithMailbagInfos){
				ContainerVO toContainerVO = createToContainerVO(containerDetailsVO, toFlightVO);

				Collection<MailbagVO> totalMailBags=containerDetailsVO.getMailDetails();
				if (totalMailBags != null && totalMailBags.size() > 0) {
					for (MailbagVO mailbagVO : totalMailBags) {
						if (containerDetailsVO.getContainerNumber() != null
								&& (containerDetailsVO.getContainerNumber().equals(mailbagVO
								.getContainerNumber()))) {
							mailbagVO.setCarrierCode(toContainerVO.getCarrierCode());
							mailBagsToReassign.add(mailbagVO);
						}
					}
					flagMLDForMailReassignOperations(mailBagsToReassign, toContainerVO,
							MailConstantsVO.MLD_ALL);

					if("B".equals(containerAssignmentVO.getContainerType())) {
						flagMLDForMailReassignOperations(mailBagsToReassign, toContainerVO,
								MailConstantsVO.MLD_STG);
					}
				}
			}
		}

		log.debug(CLASS, "flagMLDForContainerReassign");  

}
	private Collection<ContainerDetailsVO> findContainerDetailsVO(ContainerVO containerVO,OperationalFlightVO toFlightVO,ContainerAssignmentVO containerAssignmentVO) {
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<ContainerDetailsVO>();

		ContainerDetailsVO condetVO = new ContainerDetailsVO();
		condetVO.setCompanyCode(containerVO.getCompanyCode());
		condetVO.setContainerNumber(containerVO.getContainerNumber());
		condetVO.setContainerType(containerVO.getType());
		condetVO.setCarrierId(containerVO.getCarrierId());
		condetVO.setPol(containerVO.getAssignedPort());
		condetVO.setCarrierCode(containerVO.getCarrierCode());
		if(containerAssignmentVO.getFlightNumber().equals(containerVO.getFlightNumber()) &&
				containerAssignmentVO.getFlightSequenceNumber()==containerVO.getFlightSequenceNumber()){

			condetVO.setFlightNumber(containerVO.getFlightNumber());
			condetVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			condetVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
			condetVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		}
		else{
			condetVO.setFlightNumber(toFlightVO.getFlightNumber());
			condetVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
			condetVO.setSegmentSerialNumber(toFlightVO.getSegSerNum());
			condetVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
		}
		containerDetailsVOs.add(condetVO);
		return containerDetailsVOs;
	}
private ContainerVO createToContainerVO(ContainerDetailsVO containerDetailsVO,
											OperationalFlightVO toFlightVO) {
		log.debug(CLASS, "createToContainerVO");
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCarrierCode(toFlightVO.getCarrierCode());
		containerVO.setCarrierId(toFlightVO.getCarrierId());
		containerVO.setFlightNumber(toFlightVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(toFlightVO
				.getFlightSequenceNumber());
		containerVO.setFlightDate(toFlightVO.getFlightDate());
		containerVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
		containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerVO.setPou(toFlightVO.getPou());
		log.debug(CLASS, "createToContainerVO");
		return containerVO;

	}
	public void flagMLDForUpliftedMailbags(
			Collection<OperationalFlightVO> operationalFlightVOs)
			throws SystemException {
		log.debug(CLASS, "flagMLDForUpliftedMailbags");
		if (operationalFlightVOs != null) {
			for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
				Collection<MailbagVO> totalMailBags = new ArrayList<MailbagVO>();
				totalMailBags = MailAcceptance
						.findMailBagsForUpliftedResdit(operationalFlightVO);
				flagMLDForMailOperations(totalMailBags, MailConstantsVO.MLD_UPL);
			}
		}
		log.debug(CLASS, "flagMLDForUpliftedMailbags");
	}
	}


