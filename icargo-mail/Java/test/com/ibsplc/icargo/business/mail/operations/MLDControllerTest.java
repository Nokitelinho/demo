/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MLDControllerTest.java
 *
 *	Created by	:	A-10647
 *	Created on	:	28-Feb-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import org.junit.Test;
import static org.mockito.Matchers.eq;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeCache;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDMessageVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.CriterionProvider;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;

/**
 * Java file : com.ibsplc.icargo.business.mail.operations.MLDControllerTest.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-10647 :
 * 28-Feb-2022 : Draft
 */
public class MLDControllerTest extends AbstractFeatureTest {
	private static final String MAILTRACKING_DEFAULTS = "mail.operations";
	private MLDController MLDControllerSpy;
	private MailTrackingDefaultsSqlDAO dao;
	Collection<MLDConfigurationVO> mldConfiguration;
	private SharedAirlineProxy sharedAirlineProxy;
	private FlightOperationsProxy flightOperationsProxy;
	private MLDController mldControllerMock;
	private AirlineValidationVO airlineValidationVOAlphaCode;
	Collection<FlightValidationVO> flightValidationVOs;
	private MsgBrokerMessageProxy msgBrokerMessageProxy;
	private PartnerCarrier partnerCarrier;
	Money money;
	private static final Log LOG = LogFactory.getLogger("MRA ReceivableManagement");
	private static final String MLD_REC_HND ="REC_HND";
	private CriterionProvider criterionProvider;
	private KeyUtilInstance keyUtils;
	private KeyUtils keyUtil;

	/**
	 * Overriding Method : @see
	 * com.ibsplc.icargo.framework.feature.AbstractFeatureTest#setup() Added by :
	 * A-10647 on 28-Feb-2022 Used for : Parameters : @throws Exception
	 */
	@Override
	public void setup() throws Exception {
		MLDControllerSpy = spy(new MLDController());
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		money = mock(Money.class);
		mldConfiguration = new ArrayList<MLDConfigurationVO>();
		sharedAirlineProxy = mockProxy(SharedAirlineProxy.class);
		airlineValidationVOAlphaCode = new AirlineValidationVO();
		airlineValidationVOAlphaCode.setAirlineIdentifier(1134);
		airlineValidationVOAlphaCode.setAlphaCode("JL");
		mldControllerMock = mock(MLDController.class);
		flightOperationsProxy = mockProxy(FlightOperationsProxy.class);
		flightValidationVOs = new ArrayList();
		KeyUtilInstanceMock.mockKeyUtilInstance();
		keyUtils = KeyUtilInstance.getInstance();
		msgBrokerMessageProxy = mockProxy(MsgBrokerMessageProxy.class);
		partnerCarrier = mockBean("PartnerCarrier", PartnerCarrier.class);  
	}

	@Test
	public void createMLDVOsFromMailbagVOsVersion2()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_REC;
		String mldEventMode = mode;

		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();

		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setAllocatedRequired("Y");
		mldConfiguration.add(configurationVO);

		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

	@Test
	public void createMLDVOsFromMailbagVOsVersion2_withMode1()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_HND;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setReceivedRequired("Y");
		mldConfiguration.add(configurationVO);

		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

	@Test
	public void createMLDVOsFromMailbagVOsVersion1()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("FRA");
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(1234);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_REC;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("FRA");
		segmentVO.setSegmentOrigin("FRA");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		configurationVO.setUpliftedRequired("Y");
		mldConfiguration.add(configurationVO);

		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

	@Test
	public void createMLDVOsFromMailbagVOs_with_No_MailBag() throws SystemException {
		Collection<MailbagVO> mailbagVOs = null;
		MailbagVO bagVO = null;
		ContainerVO toContainerVO = new ContainerVO();

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();

		String mode = MailConstantsVO.MLD_REC;
		String mldEventMode = mode;

		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionNOPOl()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setFlightSequenceNumber(1234);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_DLV;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setDeliveredRequired("Y");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(), any());
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionNST()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setFlightSequenceNumber(1234);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);
		toContainerVO.setFlightSequenceNumber(123);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_NST;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setNestedRequired("Y");
		mldConfiguration.add(configurationVO);

		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionSTG()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setFlightSequenceNumber(1234);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_STG;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setStagedRequired("Y");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));

		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionUPL()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("FRA");
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(1234);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_UPL;
		String mldEventMode = mode;

		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;
		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("FRA");
		segmentVO.setSegmentOrigin("FRA");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		configurationVO.setUpliftedRequired("Y");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionHND()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(3);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_HND;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(3);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(1134);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(1134);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(3);
		flightValidationVOs.add(validationVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;
		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
		FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
		flightSegmentSummaryVO.setSegmentOrigin("FRA");
		flightSegmentSummaryVO.setSegmentDestination("FRA");
		flightSegmentSummaryVO.setSegmentSerialNumber(3);
		flightSegments.add(flightSegmentSummaryVO);
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		configurationVO.sethNDRequired("Y");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any());
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
				any(String.class), any(Long.class));
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionRCT()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setFlightSequenceNumber(1234);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_RCT;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setReceivedFromFightRequired("Y");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionRCTNull()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setFlightSequenceNumber(1234);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = new MLDMasterVO() ;
		MLDDetailVO mldDetailVO = new MLDDetailVO();
		mldMasterVO.setMldDetailVO(mldDetailVO);
		
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_RCT;
		String mldEventMode = mode;

		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		doReturn(mldMasterVO).when(MLDControllerSpy).findParametersForVersion2(any(MLDMasterVO.class),
				any(String.class), any(MailbagVO.class), any(LogonAttributes.class), any(ContainerVO.class));
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionRET()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setFlightSequenceNumber(1234);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);
		Collection<DamagedMailbagVO> damagedMailBagVOs = new ArrayList();
		DamagedMailbagVO damagedMailBagVO = new DamagedMailbagVO();
		damagedMailBagVO.setPaCode("DE101");
		damagedMailBagVOs.add(damagedMailBagVO);
		bagVO.setDamagedMailbags(damagedMailBagVOs);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);
		toContainerVO.setFlightSequenceNumber(123);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_RET;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setReturnedRequired("Y");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

	@Test
	public void createMLDVOsFromMailbagVOsVersion1_withMode2()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("FRA");
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(1234);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_STG;
		String mldEventMode = mode;

		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("FRA");
		segmentVO.setSegmentOrigin("FRA");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		configurationVO.setReceivedFromOALRequired("Y");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));

		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionREC_HND()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("FRA");
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(0);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = "REC_HND";
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("FRA");
		segmentVO.setSegmentOrigin("FRA");	
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		configurationVO.setTransferredFromOALRequired("Y");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

	@Test
	public void createMLDVOsFromMailbagVOsNoVersion()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("DXB");
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(0);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("DXB");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = "REC_HND";
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("DXB");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("DXB");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("FRA");
		segmentVO.setSegmentOrigin("FRA");

		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("3");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);


	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionALL()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(3);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);
		toContainerVO.setFlightSequenceNumber(123);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_ALL;
		String mldEventMode = mode;

		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(3);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(1134);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(1134);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(3);
		flightValidationVOs.add(validationVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;
		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
		FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
		flightSegmentSummaryVO.setSegmentOrigin("FRA");
		flightSegmentSummaryVO.setSegmentDestination("FRA");
		flightSegmentSummaryVO.setSegmentSerialNumber(1);
		flightSegments.add(flightSegmentSummaryVO);
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		mldConfiguration.add(configurationVO);
	
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any());
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
				any(String.class), any(Long.class));
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionFRESHALL()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(3);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = null;

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_FRESH_ALL;
		String mldEventMode = mode;

		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(3);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(1134);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(1134);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(3);
		flightValidationVOs.add(validationVO);

		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;
		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
		FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
		flightSegmentSummaryVO.setSegmentOrigin("FRA");
		flightSegmentSummaryVO.setSegmentDestination("FRA");
		flightSegmentSummaryVO.setSegmentSerialNumber(1);
		flightSegments.add(flightSegmentSummaryVO);
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any());
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
				any(String.class), any(Long.class));
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionALLWithNoContainer()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(3);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = null;

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_ALL;
		String mldEventMode = mode;

		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(3);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(1134);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(1134);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(3);
		flightValidationVOs.add(validationVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
		FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
		flightSegmentSummaryVO.setSegmentOrigin("FRA");
		flightSegmentSummaryVO.setSegmentDestination("FRA");
		flightSegmentSummaryVO.setSegmentSerialNumber(1);
		flightSegments.add(flightSegmentSummaryVO);
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		mldConfiguration.add(configurationVO);
	
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any());
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
				any(String.class), any(Long.class));
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionALLNoScannedDate()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(3);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);
		toContainerVO.setFlightSequenceNumber(0);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_ALL;
		String mldEventMode = mode;

		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(3);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(1134);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(1134);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(3);
		flightValidationVOs.add(validationVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;
		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
		FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
		flightSegmentSummaryVO.setSegmentOrigin("FRA");
		flightSegmentSummaryVO.setSegmentDestination("FRA");
		flightSegmentSummaryVO.setSegmentSerialNumber(1);
		flightSegments.add(flightSegmentSummaryVO);
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any());
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
				any(String.class), any(Long.class));
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
	}

	@Test
	public void createMLDVOsFromMailbagVOsVersionFinderException()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(3);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_HND;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();

		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(3);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(1134);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setFlightCarrierId(1134);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(3);
		flightValidationVOs.add(validationVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;
		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
		FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
		flightSegmentSummaryVO.setSegmentOrigin("FRA");
		flightSegmentSummaryVO.setSegmentDestination("FRA");
		flightSegmentSummaryVO.setSegmentSerialNumber(3);
		flightSegments.add(flightSegmentSummaryVO);
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any());
		SystemException systemException = new SystemException("Error");
		doThrow(systemException).when(flightOperationsProxy).findFlightSegments(any(String.class),
				any(Integer.class), any(String.class), any(Long.class));
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
	}
	@Test
	public void createMLDVOsFromMailbagVOsVersionRCT_WithNOTransferCarrier()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setFlightSequenceNumber(1234);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = new MLDMasterVO();
		MLDDetailVO mldDetailVO = new MLDDetailVO() ;
		mldMasterVO.setMldDetailVO(mldDetailVO);
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_RCT;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);


	}
	@Test
	public void createMLDVOsFromMailbagVOsVersion1DLV()
			throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setContainerNumber("AKE90345AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setFlightSequenceNumber(3);
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("FRA");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
		bagVO.setMailSequenceNumber(1620);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_DLV;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV4567");
		containerAssignmentVO.setFlightSequenceNumber(3);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("FRA");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("FRA");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(1134);
		flightFilterVO.setFlightNumber("AV4567");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(1134);
		validationVO.setFlightNumber("AV4567");
		validationVO.setFlightSequenceNumber(3);
		flightValidationVOs.add(validationVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		airlineValidationVO.setAlphaCode("JL");
		int carrierCode = 11;
		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
		FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
		flightSegmentSummaryVO.setSegmentOrigin("FRA");
		flightSegmentSummaryVO.setSegmentDestination("FRA");
		flightSegmentSummaryVO.setSegmentSerialNumber(3);
		flightSegments.add(flightSegmentSummaryVO);
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		mldConfiguration.add(configurationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any());
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
				any(String.class), any(Long.class));
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
	}
	@Test
	public void createMLDVOsFromMailbagVOsVersion2_UPL()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_UPL;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		mldConfiguration.add(configurationVO);

		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}
	@Test
	public void createMLDVOsFromMailbagVOsVersion2_FRESHALL()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_FRESH_ALL;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("1");
		mldConfiguration.add(configurationVO);

		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}
	@Test
	public void createMLDVOsFromMailbagVOsVersion2_RECHND()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(11);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(11);

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MLD_REC_HND;
		String mldEventMode = mode;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setFlightNumber("AV9001");
		containerAssignmentVO.setFlightSequenceNumber(1234);
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setAirportCode("SIN");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode("IBS");
		flightFilterVO.setStation("SIN");
		flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		flightFilterVO.setFlightCarrierId(11);
		flightFilterVO.setFlightNumber("AV9001");
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
		FlightValidationVO validationVO = new FlightValidationVO();
		validationVO.setCompanyCode("IBS");
		validationVO.setFlightCarrierId(11);
		validationVO.setFlightNumber("AV9001");
		validationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(validationVO);

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;

		FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
		segmentVO.setSegmentDestination("SIN");
		segmentVO.setSegmentOrigin("SIN");
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		mldConfiguration.add(configurationVO);

		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
		doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
		MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

	}

@Test
public void findAirportFromFlightSegmentNotEmpty() {
	Collection<FlightSegmentSummaryVO> flightSegments =new ArrayList();
	FlightSegmentSummaryVO segmentVo = new FlightSegmentSummaryVO();
	segmentVo.setSegmentDestination("FRA");
	flightSegments.add(segmentVo);
	MailbagVO bagVO  = new MailbagVO();
	bagVO.setScannedPort("FRA");
	MLDControllerSpy.findAirportFromFlightSegment(flightSegments,bagVO);
}
@Test
public void findAirportFromFlightSegmentEmpty() {
	Collection<FlightSegmentSummaryVO> flightSegments =new ArrayList();
	MailbagVO bagVO  = new MailbagVO();
	MLDControllerSpy.findAirportFromFlightSegment(flightSegments,bagVO);
}
@Test
public void findDetailVOForRETNoDamagedBag() throws SystemException {
	LogonAttributes logon = null;

	logon =  ContextUtils.getSecurityContext().getLogonAttributesVO();
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	MailbagVO mailbagVO = new MailbagVO();
	MLDControllerSpy.findDetailVOForRET(mldDetailVO, mailbagVO, logon);
}
@Test
public void findDetailVOForRETEmptyDamagedBag() throws SystemException {
	LogonAttributes logon = null;

	logon =  ContextUtils.getSecurityContext().getLogonAttributesVO();
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	MailbagVO mailbagVO = new MailbagVO() ;
	Collection<DamagedMailbagVO> damagedMailBagVOs = new ArrayList();
	DamagedMailbagVO damagedMailBagVO = new DamagedMailbagVO();
	damagedMailBagVO.setPaCode("DE101");
	damagedMailBagVOs.add(damagedMailBagVO);
	mailbagVO.setDamagedMailbags(damagedMailBagVOs);
	MLDControllerSpy.findDetailVOForRET(mldDetailVO, mailbagVO, logon);
}
@Test
public void createMLDVOsFromMailbagVOsVersion1NoMailbagId()
		throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setContainerNumber("AKE90345AV");
	bagVO.setFlightNumber("AV4567");
	bagVO.setFlightSequenceNumber(3);
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("FRA");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
//	bagVO.setMailbagId("");
	bagVO.setMailSequenceNumber(1620);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);

	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = null;
	MLDDetailVO mldDetailVO = null;
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_DLV;
	String mldEventMode = mode;
	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
	containerAssignmentVO.setFlightNumber("AV4567");
	containerAssignmentVO.setFlightSequenceNumber(3);
	containerAssignmentVO.setCompanyCode("IBS");
	containerAssignmentVO.setAirportCode("FRA");

	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("FRA");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(1134);
	flightFilterVO.setFlightNumber("AV4567");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	FlightValidationVO validationVO = new FlightValidationVO();
	validationVO.setCompanyCode("IBS");
	validationVO.setFlightCarrierId(1134);
	validationVO.setFlightNumber("AV4567");
	validationVO.setFlightSequenceNumber(3);
	flightValidationVOs.add(validationVO);
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(1134);
	airlineValidationVO.setAlphaCode("JL");
	int carrierCode = 11;
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
	flightSegmentSummaryVO.setSegmentOrigin("FRA");
	flightSegmentSummaryVO.setSegmentDestination("FRA");
	flightSegmentSummaryVO.setSegmentSerialNumber(3);
	flightSegments.add(flightSegmentSummaryVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("SIN");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("IBS");
	configurationVO.setMldversion("1");
	mldConfiguration.add(configurationVO);
	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any());
	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
			any(String.class), any(Long.class));
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
}
@Test
public void createMLDVOsFromMailbagVOsVersionHND_EmptyTransferCarrier()
		throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setContainerNumber("AKE90345AV");
	bagVO.setFlightNumber("AV4567");
	bagVO.setFlightSequenceNumber(3);
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("FRA");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("");
	bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
	bagVO.setMailSequenceNumber(1620);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);

	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = null;
	MLDDetailVO mldDetailVO = null;
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MLD_REC_HND;
	String mldEventMode = mode;
	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
	containerAssignmentVO.setFlightNumber("AV4567");
	containerAssignmentVO.setFlightSequenceNumber(3);
	containerAssignmentVO.setCompanyCode("IBS");
	containerAssignmentVO.setAirportCode("FRA");

	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("FRA");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(1134);
	flightFilterVO.setFlightNumber("AV4567");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	FlightValidationVO validationVO = new FlightValidationVO();
	validationVO.setCompanyCode("IBS");
	validationVO.setFlightCarrierId(1134);
	validationVO.setFlightNumber("AV4567");
	validationVO.setFlightSequenceNumber(3);
	flightValidationVOs.add(validationVO);
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(1134);
	airlineValidationVO.setAlphaCode("JL");
	int carrierCode = 11;
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
	flightSegmentSummaryVO.setSegmentOrigin("FRA");
	flightSegmentSummaryVO.setSegmentDestination("FRA");
	flightSegmentSummaryVO.setSegmentSerialNumber(3);
	flightSegments.add(flightSegmentSummaryVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("SIN");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("IBS");
	configurationVO.setMldversion("1");
	mldConfiguration.add(configurationVO);
	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any());
	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
			any(String.class), any(Long.class));
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
}
@Test
public void createMLDVOsFromMailbagVOsVersionHND_NoTransferCarrier()
		throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setContainerNumber("AKE90345AV");
	bagVO.setFlightNumber("AV4567");
	bagVO.setFlightSequenceNumber(3);
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("FRA");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
//	bagVO.setTransferFromCarrier("");
	bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
	bagVO.setMailSequenceNumber(1620);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);

	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = null;
	MLDDetailVO mldDetailVO = null;
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MLD_REC_HND;
	String mldEventMode = mode;
	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
	containerAssignmentVO.setFlightNumber("AV4567");
	containerAssignmentVO.setFlightSequenceNumber(3);
	containerAssignmentVO.setCompanyCode("IBS");
	containerAssignmentVO.setAirportCode("FRA");

	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("FRA");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(1134);
	flightFilterVO.setFlightNumber("AV4567");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	FlightValidationVO validationVO = new FlightValidationVO();
	validationVO.setCompanyCode("IBS");
	validationVO.setFlightCarrierId(1134);
	validationVO.setFlightNumber("AV4567");
	validationVO.setFlightSequenceNumber(3);
	flightValidationVOs.add(validationVO);
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(1134);
	airlineValidationVO.setAlphaCode("JL");
	int carrierCode = 11;
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
	flightSegmentSummaryVO.setSegmentOrigin("FRA");
	flightSegmentSummaryVO.setSegmentDestination("FRA");
	flightSegmentSummaryVO.setSegmentSerialNumber(3);
	flightSegments.add(flightSegmentSummaryVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("SIN");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("IBS");
	configurationVO.setMldversion("1");
	mldConfiguration.add(configurationVO);
	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any());
	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
			any(String.class), any(Long.class));
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
}
@Test
public void createMLDVOsFromMailbagVOsVersionRCT_WithEmptyTransferCarrier()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setFlightSequenceNumber(1234);
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(11);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);

	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = null;
	MLDDetailVO mldDetailVO = null;
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_RCT;
	String mldEventMode = mode;
	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
	containerAssignmentVO.setFlightNumber("AV9001");
	containerAssignmentVO.setFlightSequenceNumber(1234);
	containerAssignmentVO.setCompanyCode("IBS");
	containerAssignmentVO.setAirportCode("SIN");

	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("SIN");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(11);
	flightFilterVO.setFlightNumber("AV9001");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	FlightValidationVO validationVO = new FlightValidationVO();
	validationVO.setCompanyCode("IBS");
	validationVO.setFlightCarrierId(11);
	validationVO.setFlightNumber("AV9001");
	validationVO.setFlightSequenceNumber(1234);
	flightValidationVOs.add(validationVO);

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;

	FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
	segmentVO.setSegmentDestination("SIN");
	segmentVO.setSegmentOrigin("SIN");
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("SIN");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("IBS");
	configurationVO.setMldversion("2");
	mldConfiguration.add(configurationVO);
	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

}
@Test
public void createMLDVOsFromMailbagVOsVersionRCT_WithNoTransferCarrier()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setFlightSequenceNumber(1234);
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(11);
	bagVO.setCarrierCode("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);

	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = null;
	MLDDetailVO mldDetailVO = null;
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_RCT;
	String mldEventMode = mode;
	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
	containerAssignmentVO.setFlightNumber("AV9001");
	containerAssignmentVO.setFlightSequenceNumber(1234);
	containerAssignmentVO.setCompanyCode("IBS");
	containerAssignmentVO.setAirportCode("SIN");

	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("SIN");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(11);
	flightFilterVO.setFlightNumber("AV9001");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	FlightValidationVO validationVO = new FlightValidationVO();
	validationVO.setCompanyCode("IBS");
	validationVO.setFlightCarrierId(11);
	validationVO.setFlightNumber("AV9001");
	validationVO.setFlightSequenceNumber(1234);
	flightValidationVOs.add(validationVO);

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;

	FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
	segmentVO.setSegmentDestination("SIN");
	segmentVO.setSegmentOrigin("SIN");
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("SIN");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("IBS");
	configurationVO.setMldversion("2");
	mldConfiguration.add(configurationVO);
	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

}
@Test
public void createMLDVOsFromMailbagVOsVersionSTG_withoutContainer()
		throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setFlightSequenceNumber(1234);
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(11);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = null;


	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = null;
	MLDDetailVO mldDetailVO = null;
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_STG;
	String mldEventMode = mode;
	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
	containerAssignmentVO.setFlightNumber("AV9001");
	containerAssignmentVO.setFlightSequenceNumber(1234);
	containerAssignmentVO.setCompanyCode("IBS");
	containerAssignmentVO.setAirportCode("SIN");

	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("SIN");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(11);
	flightFilterVO.setFlightNumber("AV9001");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	FlightValidationVO validationVO = new FlightValidationVO();
	validationVO.setCompanyCode("IBS");
	validationVO.setFlightCarrierId(11);
	validationVO.setFlightNumber("AV9001");
	validationVO.setFlightSequenceNumber(1234);
	flightValidationVOs.add(validationVO);

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;

	FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
	segmentVO.setSegmentDestination("SIN");
	segmentVO.setSegmentOrigin("SIN");
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("SIN");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("IBS");
	configurationVO.setMldversion("2");
	mldConfiguration.add(configurationVO);
	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));

	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
}
@Test
public void createMLDVOsFromMailbagVOsVersionNST_WithoutContainer()
		throws SystemException, SharedProxyException, PersistenceException, FinderException, ProxyException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setFlightSequenceNumber(1234);
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(11);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = null;
	

	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = null;
	MLDDetailVO mldDetailVO = null;
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_NST;
	String mldEventMode = mode;
	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
	containerAssignmentVO.setFlightNumber("AV9001");
	containerAssignmentVO.setFlightSequenceNumber(1234);
	containerAssignmentVO.setCompanyCode("IBS");
	containerAssignmentVO.setAirportCode("SIN");

	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("SIN");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(11);
	flightFilterVO.setFlightNumber("AV9001");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	FlightValidationVO validationVO = new FlightValidationVO();
	validationVO.setCompanyCode("IBS");
	validationVO.setFlightCarrierId(11);
	validationVO.setFlightNumber("AV9001");
	validationVO.setFlightSequenceNumber(1234);
	flightValidationVOs.add(validationVO);

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;

	FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
	segmentVO.setSegmentDestination("SIN");
	segmentVO.setSegmentOrigin("SIN");
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("SIN");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("IBS");
	configurationVO.setMldversion("2");
	mldConfiguration.add(configurationVO);

	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

}
@Test
public void findAirportForEmptyContainer_Empty() throws ProxyException, SystemException {
	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	MailbagVO mailbagVO = new MailbagVO();
	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("SIN");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(11);
	flightFilterVO.setFlightNumber("AV9001");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
	MLDControllerSpy.findAirportForEmptyContainer(mailbagVO);


}
@Test
public void createMLDVOsFromMailbagVOsVersion2RCF()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(11);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);

	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = null;
	MLDDetailVO mldDetailVO = null;
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_RCF;
	String mldEventMode = mode;

	ContainerAssignmentVO containerAssignmentVO = null;
	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("SIN");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(11);
	flightFilterVO.setFlightNumber("AV9001");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	FlightValidationVO validationVO = new FlightValidationVO();
	validationVO.setCompanyCode("IBS");
	validationVO.setFlightCarrierId(11);
	validationVO.setFlightNumber("AV9001");
	validationVO.setFlightSequenceNumber(1234);
	flightValidationVOs.add(validationVO);

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;

	FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
	segmentVO.setSegmentDestination("SIN");
	segmentVO.setSegmentOrigin("SIN");
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("SIN");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("IBS");
	configurationVO.setMldversion("2");
	mldConfiguration.add(configurationVO);

	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

}

@Test
public void flagMLDForMailbagTransferVersion1STG()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("FRA");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV4567");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("FRA");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(1134);

	
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_STG;
	String mldEventMode = mode;

	

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();

	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	MLDControllerSpy.flagMLDForMailbagTransfer(mailbagVOs,toContainerVO,operationalFlightVO,mode);

}
@Test
public void flagMLDForMailbagTransferVersion2HND()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(1134);

	
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_HND;
	String mldEventMode = mode;

	

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();


	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	MLDControllerSpy.flagMLDForMailbagTransfer(mailbagVOs,toContainerVO,operationalFlightVO,mode);

}
@Test
public void flagMLDForMailbagTransferVersion1TFD()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("FRA");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV4567");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("FRA");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(1134);

	
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_TFD;
	String mldEventMode = mode;

	

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();

	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	MLDControllerSpy.flagMLDForMailbagTransfer(mailbagVOs,toContainerVO,operationalFlightVO,mode);

}
@Test
public void flagMLDForMailbagTransferVersion2ALL()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(1134);

	
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_ALL;
	String mldEventMode = mode;

	

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);

	MLDControllerSpy.flagMLDForMailbagTransfer(mailbagVOs,toContainerVO,operationalFlightVO,mode);

}
@Test
	public void flagMLDForContainerTransferVersion2HND()
			throws SharedProxyException, SystemException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	Collection<ContainerVO>ContainerVOs = new ArrayList();
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(1134);
	ContainerVOs.add(toContainerVO);

	
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_ALL;
	String mldEventMode = mode;

	

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
;

	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	MLDControllerSpy.flagMLDForContainerTransfer(mailbagVOs,ContainerVOs,operationalFlightVO);
}

@Test
	public void flagMLDForContainerTransferVersion1HND()
			throws SharedProxyException, SystemException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	Collection<ContainerVO>ContainerVOs = new ArrayList();
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(1134);
	ContainerVOs.add(toContainerVO);

	
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_ALL;
	String mldEventMode = mode;

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	MLDControllerSpy.flagMLDForContainerTransfer(mailbagVOs,ContainerVOs,operationalFlightVO);
}
@Test
	public void generateCounter() throws SystemException{
		String companyCode="IBS";
		keyUtils = KeyUtilInstance.getInstance();
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		MLDControllerSpy.generateCounter(companyCode);
	}
	@Test
	public void generateCounterForKeyGreaterThanFive() throws SystemException{
		String companyCode="IBS";
		keyUtils = KeyUtilInstance.getInstance();
		doReturn("100000").when(keyUtils).getKey(any(Criterion.class));
		MLDControllerSpy.generateCounter(companyCode);
	}
	@Test
	public void constructFileName(){
		MLDMessageVO mLDMessageVO=new MLDMessageVO();
		MLDMasterVO mLDMasterVO = new MLDMasterVO();
		mLDMasterVO.setEventCOde(MailConstantsVO.MLD_DLV);
		mLDMasterVO.setReceiverAirport("FRA");
		mLDMasterVO.setSenderAirport("FRA");
		mLDMessageVO.setSenderID("LH");
		mLDMessageVO.setStationCode("FRA");
		String count="00001";
		MLDControllerSpy.constructFileName(mLDMessageVO,count,mLDMasterVO);
	}
	@Test
	public void constructFileNameForHND(){
		MLDMessageVO mLDMessageVO=new MLDMessageVO();
		MLDMasterVO mLDMasterVO = new MLDMasterVO();
		mLDMasterVO.setEventCOde(MailConstantsVO.MLD_HND);
		mLDMasterVO.setReceiverAirport(null);
		mLDMasterVO.setSenderAirport("FRA");
		mLDMessageVO.setSenderID("LH");
		mLDMessageVO.setStationCode("FRA");
		String count="00001";
		MLDControllerSpy.constructFileName(mLDMessageVO,count,mLDMasterVO);
	}
	@Test
	public void constructFileNameForRCF(){
		MLDMessageVO mLDMessageVO=new MLDMessageVO();
		MLDMasterVO mLDMasterVO = new MLDMasterVO();
		mLDMasterVO.setEventCOde(MailConstantsVO.MLD_RCF);
		mLDMasterVO.setReceiverAirport("FRA");
		mLDMasterVO.setSenderAirport("FRA");
		mLDMessageVO.setSenderID("LH");
		mLDMessageVO.setStationCode("FRA");
		String count="00001";
		MLDControllerSpy.constructFileName(mLDMessageVO,count,mLDMasterVO);
	}
	@Test
	public void constructFileNameForOtherMLD(){
		MLDMessageVO mLDMessageVO=new MLDMessageVO();
		MLDMasterVO mLDMasterVO = new MLDMasterVO();
		mLDMasterVO.setEventCOde(MailConstantsVO.MLD_REC);
		mLDMasterVO.setReceiverAirport("FRA");
		mLDMasterVO.setSenderAirport("FRA");
		mLDMessageVO.setSenderID("LH");
		mLDMessageVO.setStationCode("FRA");
		String count="00001";
		MLDControllerSpy.constructFileName(mLDMessageVO,count,mLDMasterVO);
	}
	@Test
public void findAirportFromFlightSegmentwithNoPol()
throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
Collection<MailbagVO> mailbagVOs = new ArrayList();
MailbagVO bagVO = new MailbagVO();
bagVO.setScannedPort("SIN");
Collection<FlightSegmentSummaryVO>flightSegments = new ArrayList();
FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
segmentVO.setSegmentDestination("SIN");
segmentVO.setSegmentOrigin("SIN");
flightSegments.add(segmentVO);
MLDControllerSpy.findAirportFromFlightSegment(flightSegments,bagVO);
}
@Test
public void findAirportFromFlightSegmentwithPol()
throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
Collection<MailbagVO> mailbagVOs = new ArrayList();
MailbagVO bagVO = new MailbagVO();
bagVO.setPol("SIN");
bagVO.setScannedPort("SIN");
Collection<FlightSegmentSummaryVO>flightSegments = new ArrayList();
FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
segmentVO.setSegmentDestination("SIN");
segmentVO.setSegmentOrigin("SIN");
flightSegments.add(segmentVO);
MLDControllerSpy.findAirportFromFlightSegment(flightSegments,bagVO);
}
@Test
public void createMLDVOsFromMailbagVOs_withEmptyMailBag()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	ContainerVO toContainerVO = null;
	String mode = MailConstantsVO.MLD_REC;
	String mldEventMode = mode;
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
}
@Test
public void flagMLDForContainerTransfer_withNoContainerVO() throws SystemException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setScannedPort("SIN");
	mailbagVOs.add(bagVO);
	Collection<ContainerVO>containerVOs	= new ArrayList();
	ContainerVO toContainerVO = null;
	containerVOs.add(toContainerVO);
	String mode = MailConstantsVO.MLD_REC;
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
	MLDControllerSpy.flagMLDForContainerTransfer(mailbagVOs, containerVOs, operationalFlightVO);
}
@Test
public void constructMLdVOs_withNoConfigVO()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("DXB");
	bagVO.setContainerNumber("AKE90345AV");
	bagVO.setFlightNumber("AV4567");
	bagVO.setFlightSequenceNumber(0);
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("DXB");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("DEFRAASGSINAACA21201111110200");
	bagVO.setMailSequenceNumber(1620);
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = "REC_HND";
	String mldEventMode = mode;
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(1134);
	airlineValidationVO.setAlphaCode("JL");
	int carrierCode = 11;
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = null;
	mldConfiguration.add(configurationVO);
	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
	doReturn(new ArrayList()).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);
}
@Test
public void flagMLDForMailOperations_All()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = "REC_HND";
	String mldEventMode = mode;
	int carrierCode = 11;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	mldMasterVO.setAllocationRequired(true);
	mldMasterVos.add(mldMasterVO);

		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_AllWithNoAllocation()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = "REC_HND";
	String mldEventMode = mode;
	int carrierCode = 11;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	mldMasterVO.setAllocationRequired(false);
	mldMasterVos.add(mldMasterVO);

		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_REC()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = "REC";
	String mldEventMode = mode;
	int carrierCode = 11;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("REC");
	mldMasterVO.setRecRequired(true);
	mldMasterVO.setAllocationRequired(false);
	mldMasterVos.add(mldMasterVO);

		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_RECWithNORecRequired()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = "REC";
	String mldEventMode = mode;
	int carrierCode = 11;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("REC");
	mldMasterVO.setRecRequired(false);
	mldMasterVos.add(mldMasterVO);

		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_UPL()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = "REC";
	String mldEventMode = mode;
	int carrierCode = 11;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("UPL");
	mldMasterVO.setUpliftedRequired(true);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_UPLWithNoUplifit()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = "REC";
	String mldEventMode = mode;
	int carrierCode = 11;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("UPL");
	mldMasterVO.setUpliftedRequired(false);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_HND()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "HND";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("HND");
	mldMasterVO.sethNdRequired(true);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_UPLWithNoHandOver()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "HND";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("HND");
	mldMasterVO.sethNdRequired(false);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_DLV()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "DLV";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("DLV");
	mldMasterVO.setdLVRequired(true);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_DLVWithNoDeliverRequired()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "DLV";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("DLV");
	mldMasterVO.setdLVRequired(false);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_STG()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "STG";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("STG");
	mldMasterVO.setsTGRequired(true);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_STGWithNoStageRequired()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "STG";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("STG");
	mldMasterVO.setsTGRequired(false);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_NST()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "NST";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("NST");
	mldMasterVO.setnSTRequired(true);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_NSTWithNoNstREquired()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "NST";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("NST");
	mldMasterVO.setnSTRequired(false);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_RCF()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "RCF";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RCF");
	mldMasterVO.setrCFRequired(true);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_RCFWithNoRcfRequired()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "RCF";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RCF");
	mldMasterVO.setrCFRequired(false);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_TFD()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "TFD";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("TFD");
	mldMasterVO.settFDRequired(true);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_TFDWithNoTfdRequired()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "TFD";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("TFD");
	mldMasterVO.settFDRequired(false);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_RCT()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "RCT";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RCT");
	mldMasterVO.setrCTRequired(true);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_RCTWithNoRctRequired()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "RCT";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RCT");
	mldMasterVO.setrCTRequired(false);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_RET()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "RET";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RET");
	mldMasterVO.setrETRequired(true);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}
@Test
public void flagMLDForMailOperations_RETWithNoRetRequired()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "RET";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RET");
	mldMasterVO.setrETRequired(false);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);

	}
@Test
public void sendMLdMessages_withRECMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("REC");
	mldMasterVO.setRecRequired(true);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierIdOub(34);
	mldDetailVO.setCarrierCodeOub("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("REC");
	mldMasterVO1.setRecRequired(true);
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeInb("1");
	mldDetailVO1.setFlightNumberOub("4567");
	mldDetailVO1.setCarrierCodeOub("123");
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	MLDMasterVO mldMasterVO2 = new MLDMasterVO();
	mldMasterVO2.setEventCOde("REC");
	mldMasterVO2.setRecRequired(true);
	MLDDetailVO mldDetailVO2 = new MLDDetailVO();
	mldDetailVO2.setMailModeOub("H");
	mldDetailVO2.setMailModeInb("H");
	mldDetailVO2.setCarrierIdOub(34);
	mldMasterVO2.setMldDetailVO(mldDetailVO2);
	mldMasterVO2.setSenderAirport("CDG");
	mldMasterVO2.setReceiverAirport("DXB");
	mldMasterVO2.setUldNumber("AV45671");
	mldMasterVO2.setAddrCarrier("345");
	mldMasterVO2.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO2);
	String companyCode="IBS";
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLdMessages_WithHNDMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("HND");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO2= new MLDMasterVO();
	mldMasterVO2.setEventCOde("HND");
	MLDDetailVO mldDetailVO2 = new MLDDetailVO();
	mldDetailVO2.setMailModeOub("H");
	mldDetailVO2.setMailModeInb("H");
	mldDetailVO2.setCarrierCodeInb("123");
	mldMasterVO2.setMldDetailVO(mldDetailVO);
	mldMasterVO2.setSenderAirport("CDG");
	mldMasterVO2.setReceiverAirport("DXB");
	mldMasterVO2.setUldNumber("AV45671");
	mldMasterVO2.setAddrCarrier("345");
	mldMasterVO2.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO2);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("HND");
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("1");
	mldDetailVO1.setMailModeInb("H");
	mldDetailVO1.setFlightNumberOub("156");
	mldDetailVO1.setCarrierIdInb(1);
	mldDetailVO1.setFlightSequenceNumberOub(123);
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	MLDMasterVO mldMasterVO3 = new MLDMasterVO();
	mldMasterVO3.setEventCOde("HND");
	MLDDetailVO mldDetailVO3 = new MLDDetailVO();
	mldDetailVO3.setMailModeOub("1");
	mldDetailVO3.setMailModeInb("1");
	mldDetailVO3.setFlightNumberInb("4567");
	mldDetailVO3.setFlightNumberOub("156");
	mldDetailVO3.setFlightSequenceNumberInb(123);
	mldMasterVO3.setMldDetailVO(mldDetailVO3);
	mldMasterVO3.setSenderAirport("CDG");
	mldMasterVO3.setReceiverAirport("DXB");
	mldMasterVO3.setUldNumber("AV45671");
	mldMasterVO3.setAddrCarrier("345");
	mldMasterVO3.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO3);
	MLDMasterVO mldMasterVO4 = new MLDMasterVO();
	mldMasterVO4.setEventCOde("HND");
	mldMasterVO4.setMldDetailVO(mldDetailVO1);
	mldMasterVO4.setSenderAirport("CDG");
	mldMasterVO4.setReceiverAirport("DXB");
	mldMasterVO4.setUldNumber("AV45671");
	mldMasterVO4.setAddrCarrier("345");
	mldMasterVO4.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO4);
	String companyCode="IBS";
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLDMessages_withEmptyMasterVos() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLDMessages_withNoMasterVos() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = null;
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLDMessages_withNoMldDetail() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	MLDDetailVO mldDetailVO = null;
	mldMasterVO.setMldDetailVO(null);
	mldMasterVOs.add(mldMasterVO);
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLdMessages_withNSTMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("NST");
	mldMasterVO.setRecRequired(true);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierIdOub(34);
	mldDetailVO.setCarrierCodeOub("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("NST");
	mldMasterVO1.setRecRequired(true);
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("1");
	mldDetailVO1.setMailModeInb("1");
	mldDetailVO1.setFlightNumberOub("4567");
	mldDetailVO1.setCarrierIdOub(0);
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	String companyCode="IBS";
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMldMessages_WithRCTmode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RCT");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierIdOub(34);
	mldDetailVO.setCarrierCodeOub("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("RCT");
	mldMasterVO1.setRecRequired(true);
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("1");
	mldDetailVO1.setMailModeInb("1");
	mldDetailVO1.setFlightNumberOub("4567");
	mldDetailVO1.setCarrierCodeOub("123");
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	String companyCode="IBS";
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLdMessages_withALLMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	mldMasterVO.setRecRequired(true);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierIdOub(34);
	mldDetailVO.setCarrierCodeOub("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("ALL");
	mldMasterVO1.setRecRequired(true);
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("1");
	mldDetailVO1.setMailModeInb("1");
	mldDetailVO1.setFlightNumberOub("4567");
	mldDetailVO1.setCarrierCodeOub("123");
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	String companyCode="IBS";
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLdMessages_withUPLMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("UPL");
	mldMasterVO.setRecRequired(true);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierIdOub(34);
	mldDetailVO.setCarrierCodeOub("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("UPL");
	mldMasterVO1.setRecRequired(true);
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("1");
	mldDetailVO1.setMailModeInb("1");
	mldDetailVO1.setCarrierCodeOub("123");
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	String companyCode="IBS";
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLdMessages_withSTGMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("STG");
	mldMasterVO.setRecRequired(true);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierIdOub(34);
	mldDetailVO.setCarrierCodeOub("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("STG");
	mldMasterVO1.setRecRequired(true);
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("1");
	mldDetailVO1.setMailModeInb("1");
	mldDetailVO1.setFlightNumberOub("4567");
	mldDetailVO1.setCarrierCodeOub("123");
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	MLDMasterVO mldMasterVO2 = new MLDMasterVO();
	mldMasterVO2.setEventCOde("STG");
	MLDDetailVO mldDetailVO2 = new MLDDetailVO();
	mldDetailVO2.setMailModeOub("1");
	mldDetailVO2.setMailModeInb("1");
	mldDetailVO2.setFlightNumberOub("4567");
	mldDetailVO2.setCarrierCodeOub("123");
	mldMasterVO2.setMldDetailVO(mldDetailVO2);
	mldMasterVO2.setSenderAirport("CDG");
	mldMasterVO2.setReceiverAirport("DXB");
	mldMasterVO2.setUldNumber("AV45671");
	mldMasterVO2.setAddrCarrier("345");
	mldMasterVO2.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO2);
	String companyCode="IBS";
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLdMessages_withRETMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RET");
	mldMasterVO.setRecRequired(true);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierIdOub(34);
	mldDetailVO.setCarrierCodeOub("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("RET");
	mldMasterVO1.setRecRequired(true);
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setFlightNumberOub("4567");
	mldDetailVO1.setCarrierCodeOub("123");
	mldDetailVO1.setCarrierIdOub(0);
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	MLDMasterVO mldMasterVO2 = new MLDMasterVO();
	mldMasterVO2.setEventCOde("RET");
	mldMasterVO2.setRecRequired(true);
	MLDDetailVO mldDetailVO2 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("H");
	mldDetailVO2.setFlightNumberOub("4567");
	mldDetailVO2.setCarrierCodeOub("123");
	mldDetailVO2.setCarrierIdOub(0);
	mldMasterVO2.setMldDetailVO(mldDetailVO2);
	mldMasterVO2.setSenderAirport("CDG");
	mldMasterVO2.setReceiverAirport("DXB");
	mldMasterVO2.setUldNumber("AV45671");
	mldMasterVO2.setAddrCarrier("345");
	mldMasterVO2.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO2);
	String companyCode="IBS";
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLdMessages_withNOCarrierID() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RET");
	mldMasterVO.setRecRequired(true);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setCarrierIdOub(0);
	mldDetailVO.setCarrierCodeOub("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLdMessages_WithRCFMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RCF");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierCodeInb("123");
	mldDetailVO.setCarrierIdOub(0);
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("RCF");
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("1");
	mldDetailVO1.setMailModeInb("1");
	mldDetailVO1.setFlightNumberInb("4567");
	mldDetailVO1.setFlightNumberOub("156");
	mldDetailVO1.setFlightSequenceNumberInb(123);
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	String companyCode="IBS";
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLdMessages_WithTFDMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("TFD");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierIdInb(123);
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("TFD");
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("1");
	mldDetailVO1.setMailModeInb("1");
	mldDetailVO1.setFlightNumberOub("156");
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	mldMasterVOs.add(mldMasterVO1);
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLDMessages_withDLVMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("DLV");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("4567");
	mldDetailVO.setFlightSequenceNumberInb(1);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("DLV");
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setFlightNumberInb("4567");
	mldDetailVO1.setFlightSequenceNumberInb(1);
	mldDetailVO1.setCarrierCodeInb("123");
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	MLDMasterVO mldMasterVO2 = new MLDMasterVO();
	mldMasterVO2.setEventCOde("DLV");
	MLDDetailVO mldDetailVO2 = new MLDDetailVO();
	mldDetailVO2.setFlightSequenceNumberInb(0);
	mldDetailVO2.setCarrierCodeInb("123");
	mldMasterVO2.setMldDetailVO(mldDetailVO2);
	mldMasterVO2.setSenderAirport("CDG");
	mldMasterVO2.setReceiverAirport("DXB");
	mldMasterVO2.setUldNumber("AV45671");
	mldMasterVO2.setAddrCarrier("345");
	mldMasterVO2.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO2);
	MLDMasterVO mldMasterVO3 = new MLDMasterVO();
	mldMasterVO3.setEventCOde("DLV");
	MLDDetailVO mldDetailVO3 = new MLDDetailVO();
	mldDetailVO3.setFlightNumberInb("4567");
	mldDetailVO3.setFlightSequenceNumberInb(0);
	mldDetailVO3.setCarrierCodeInb("123");
	mldMasterVO3.setMldDetailVO(mldDetailVO3);
	mldMasterVO3.setSenderAirport("CDG");
	mldMasterVO3.setReceiverAirport("DXB");
	mldMasterVO3.setUldNumber("AV45671");
	mldMasterVO3.setAddrCarrier("345");
	mldMasterVO3.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO3);
	String companyCode="IBS";
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMldMessages_withNOMailMode() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("RET");
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub(null);
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void sendMLdMessage_withHndWithNoFlightSequenceNumber() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("HND");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}
@Test
public void createMLDVOsFromULDs_withEmptyContainer()
		throws SystemException {
	ContainerVO toContainerVO = null;
	String mode = MailConstantsVO.MLD_STG;
	String mldEventMode = mode;
	MLDControllerSpy.flagMLDForMailOperationsInULD(toContainerVO, mldEventMode);
}

@Test
public void constructMLDVOsFromULDwithNoConfigVO()
		throws SystemException, SharedProxyException, PersistenceException {
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("AV");
	toContainerVO.setCarrierId(11);
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("AV");
	String mode = MailConstantsVO.MLD_RCT;
	String mldEventMode = mode;
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(1134);
	airlineValidationVO.setAlphaCode("JL");
	int carrierCode = 11;
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = null;
	mldConfiguration.add(configurationVO);
	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode("IBS", "AV");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("AV", "AV");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
				any(Integer.class));
	doReturn(new ArrayList()).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(toContainerVO, mldEventMode);
}

@Test
public void flagMLDForMailOperationsInULDModeAll() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="ALL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInModeULDMLDFRESHALL() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="FRESHALL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDModeMLDRECHND() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="REC_HND";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDModeMLDUPL() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="UPL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDModeMLDHND() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="HND";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  


@Test
public void flagMLDForMailOperationsInULDModeMLDRCF() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="RCF";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDModeMLDREC() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="REC";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDModeMLDNST() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="NST";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  


@Test
public void flagMLDForMailOperationsInULDModeMLDRCTwithconfig() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="RCT";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
} 


@Test
public void flagMLDForMailOperationsInULDModeMLDRET() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="RET";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDModeSTG() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="STG";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde(MailConstantsVO.MLD_STG);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setsTGRequired(true);
	mldMasterVO.setReceiverAirport("CDG");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDModeSTGInFlight() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	containerVo.setFlightSequenceNumber(1);
	String mode="STG";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde(MailConstantsVO.MLD_STG);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setsTGRequired(true);
	mldMasterVO.setReceiverAirport("CDG");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  



@Test
public void flagMLDForMailOperationsInULDAllmldv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="ALL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
	flightSegmentSummaryVO.setSegmentOrigin("FRA");
	flightSegmentSummaryVO.setSegmentDestination("FRA");
	flightSegmentSummaryVO.setSegmentSerialNumber(3);
	flightSegments.add(flightSegmentSummaryVO);
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
			any(String.class), any(Long.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  


@Test
public void flagMLDForMailOperationsInULDMLDFRESHALLmldv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="FRESHALL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDMLDRECHNDmldv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="REC_HND";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDMLDUPLmldv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="UPL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDMLDDLVmldv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="DLV";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDMLDHNDmldv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="HND";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  


@Test
public void flagMLDForMailOperationsInULDMLDRCFmldv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="RCF";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDMLDRECmldv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="REC";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDMLDNSTmldv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="NST";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDMLDRETmldversion1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="RET";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDSTGmldversion1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="STG";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde(MailConstantsVO.MLD_STG);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setsTGRequired(true);
	mldMasterVO.setReceiverAirport("CDG");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDALLInFlightmldversion1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	containerVo.setFlightSequenceNumber(1);
	String mode="ALL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde(MailConstantsVO.MLD_STG);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setsTGRequired(true);
	mldMasterVO.setReceiverAirport("CDG");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
	flightSegmentSummaryVO.setSegmentOrigin("FRA");
	flightSegmentSummaryVO.setSegmentDestination("FRA");
	flightSegmentSummaryVO.setSegmentSerialNumber(3);
	flightSegments.add(flightSegmentSummaryVO);
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
			any(String.class), any(Long.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  


@Test
public void flagMLDForMailOperationsInULDALLInFlightEmptySegmentsmldversion1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	containerVo.setFlightSequenceNumber(1);
	String mode="ALL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde(MailConstantsVO.MLD_STG);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setsTGRequired(true);
	mldMasterVO.setReceiverAirport("CDG");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
			any(String.class), any(Long.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDALLInFlightMoreThan1Segmentsv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	containerVo.setFlightSequenceNumber(1);
	String mode="ALL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde(MailConstantsVO.MLD_STG);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setsTGRequired(true);
	mldMasterVO.setReceiverAirport("CDG");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
	FlightSegmentSummaryVO flightSegmentSummaryVO1 = new FlightSegmentSummaryVO();
	flightSegmentSummaryVO1.setSegmentOrigin("FRA");
	flightSegmentSummaryVO1.setSegmentDestination("FRA");
	flightSegmentSummaryVO1.setSegmentSerialNumber(3);
	FlightSegmentSummaryVO flightSegmentSummaryVO2 = new FlightSegmentSummaryVO();
	flightSegmentSummaryVO2.setSegmentOrigin("FRA");
	flightSegmentSummaryVO2.setSegmentDestination("FRA");
	flightSegmentSummaryVO2.setSegmentSerialNumber(3);
	flightSegments.add(flightSegmentSummaryVO1);
	flightSegments.add(flightSegmentSummaryVO2);
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
			any(String.class), any(Long.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDALLInFlight1segmentsamePOLv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	containerVo.setPol("FRA");
	containerVo.setFlightSequenceNumber(1);
	String mode="ALL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde(MailConstantsVO.MLD_STG);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setsTGRequired(true);
	mldMasterVO.setReceiverAirport("CDG");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("FRA");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
	FlightSegmentSummaryVO flightSegmentSummaryVO1 = new FlightSegmentSummaryVO();
	flightSegmentSummaryVO1.setSegmentOrigin("FRA");
	flightSegmentSummaryVO1.setSegmentDestination("FRA");
	flightSegmentSummaryVO1.setSegmentSerialNumber(3);
	flightSegments.add(flightSegmentSummaryVO1);
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
			any(String.class), any(Long.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDALLInFlight1SegmentnotsamePOLv1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	containerVo.setPol("CDG");
	containerVo.setFlightSequenceNumber(1);
	String mode="ALL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde(MailConstantsVO.MLD_STG);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setsTGRequired(true);
	mldMasterVO.setReceiverAirport("CDG");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("FRA");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
	FlightSegmentSummaryVO flightSegmentSummaryVO1 = new FlightSegmentSummaryVO();
	flightSegmentSummaryVO1.setSegmentOrigin("FRA");
	flightSegmentSummaryVO1.setSegmentDestination("FRA");
	flightSegmentSummaryVO1.setSegmentSerialNumber(3);
	flightSegments.add(flightSegmentSummaryVO1);
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
			any(String.class), any(Long.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
}  

@Test
public void flagMLDForMailOperationsInULDALLInFlightInvalidSegmentmldversion1() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	containerVo.setPol("CDG");
	containerVo.setFlightSequenceNumber(1);
	String mode="ALL";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde(MailConstantsVO.MLD_STG);
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setsTGRequired(true);
	mldMasterVO.setReceiverAirport("CDG");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("1");
	configurationVO.setStagedRequired("Y");
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(null).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class),
			any(String.class), any(Long.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
} 



@Test
public void flagMLDForMailAcceptanceNull() throws SystemException, PersistenceException{
	MailAcceptanceVO mailAcceptanceVO =null;
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("AV");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("AV");
	bagVO.setTransferFromCarrier("AV");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	MLDControllerSpy.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);
	
} 

@Test
public void flagMLDForMailAcceptanceForValidMailAcceptance() throws SystemException, PersistenceException{
	MailAcceptanceVO mailAcceptanceVO =new MailAcceptanceVO();
	Collection<ContainerDetailsVO> containerDetails= new ArrayList<>();
	ContainerDetailsVO containerDetailsVO = new  ContainerDetailsVO();
	containerDetailsVO.setContainerType("B");
	containerDetails.add(containerDetailsVO);
	mailAcceptanceVO.setContainerDetails(containerDetails);
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("AV");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("AV");
	bagVO.setTransferFromCarrier("AV");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	MLDControllerSpy.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);
	
} 

@Test
public void flagMLDForMailAcceptanceForValidMailAcceptanceuld() throws SystemException, PersistenceException{
	MailAcceptanceVO mailAcceptanceVO =new MailAcceptanceVO();
	Collection<ContainerDetailsVO> containerDetails= new ArrayList<>();
	ContainerDetailsVO containerDetailsVO = new  ContainerDetailsVO();
	containerDetailsVO.setContainerType("U");
	containerDetails.add(containerDetailsVO);
	mailAcceptanceVO.setContainerDetails(containerDetails);
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("AV");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("AV");
	bagVO.setTransferFromCarrier("AV");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);

	mailbagVOs.add(bagVO);
	MLDControllerSpy.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);
	
} 

@Test
public void flagMLDForContainerReassignValidMailAcceptanceBULK() throws SystemException, PersistenceException{
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();

	Collection<ContainerDetailsVO> containerWithMailbagInfos = new ArrayList<>();
	ContainerDetailsVO containerDetailsVOwithmailbag = new ContainerDetailsVO();
	Collection<MailbagVO> totalMailBags = new ArrayList<>();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("AV");
	bagVO.setScannedPort("SIN");
	totalMailBags.add(bagVO);
	containerDetailsVOwithmailbag.setMailDetails(totalMailBags);
	containerWithMailbagInfos.add(containerDetailsVOwithmailbag);
	containerAssignmentVO.setFlightNumber("AV9001");
	containerAssignmentVO.setFlightSequenceNumber(1234);
	containerAssignmentVO.setCompanyCode("AV");
	containerAssignmentVO.setAirportCode("SIN");
	containerAssignmentVO.setContainerType("B");
	Collection<ContainerVO> containerVOs = new ArrayList<>();
	ContainerVO containerDetailsVO = new  ContainerVO();
	containerVOs.add(containerDetailsVO);
	doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(), any());
	doReturn(containerWithMailbagInfos).when(dao).findMailbagsInContainer(any());
	MLDControllerSpy.flagMLDForContainerReassign(containerVOs, operationalFlightVO);
	
} 

@Test
public void flagMLDForContainerReassignValidMailAcceptanceULD() throws SystemException, PersistenceException{
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();

	Collection<ContainerDetailsVO> containerWithMailbagInfos = new ArrayList<>();
	ContainerDetailsVO containerDetailsVOwithmailbag = new ContainerDetailsVO();
	Collection<MailbagVO> totalMailBags = new ArrayList<>();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("AV");
	bagVO.setScannedPort("SIN");
	totalMailBags.add(bagVO);
	containerDetailsVOwithmailbag.setMailDetails(totalMailBags);
	containerWithMailbagInfos.add(containerDetailsVOwithmailbag);
	containerAssignmentVO.setFlightNumber("AV9001");
	containerAssignmentVO.setFlightSequenceNumber(1234);
	containerAssignmentVO.setCompanyCode("AV");
	containerAssignmentVO.setAirportCode("SIN");
	containerAssignmentVO.setContainerType("U");
	Collection<ContainerVO> containerVOs = new ArrayList<>();
	ContainerVO containerDetailsVO = new  ContainerVO();
	containerVOs.add(containerDetailsVO);
	doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(), any());
	doReturn(containerWithMailbagInfos).when(dao).findMailbagsInContainer(any());
	MLDControllerSpy.flagMLDForContainerReassign(containerVOs, operationalFlightVO);
	
} 

@Test
public void createMLDVOsForVersion2_ActualWeightNotNull() throws SystemException, PersistenceException{
	ContainerVO containerVo = new ContainerVO();
	containerVo.setCarrierCode("AV"); 
	containerVo.setCarrierId(1134);
	String mode="RCF";
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("ALL");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setFlightNumberInb("2345");
	mldDetailVO.setFlightSequenceNumberInb(0);
	mldMasterVO.setnSTRequired(false);
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVOs.add(mldMasterVO);
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("AV");
	mLDConfigurationFilterVO.setCompanyCode("AV");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("CDG");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("AV");
	configurationVO.setMldversion("2");
	configurationVO.setStagedRequired("Y");
	containerVo.setActualWeight(new Measure(null,80.0));
	mldConfiguration.add(configurationVO);
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	MLDControllerSpy.flagMLDForMailOperationsInULD(containerVo, mode);
	
	}  

@Test
public void sendMLdMessages_WithRCFMode_WithTransactionLevel() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RCF");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierCodeInb("123");
	mldDetailVO.setCarrierIdOub(0);
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setTransactionLevel("U");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("RCF");
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("1");
	mldDetailVO1.setMailModeInb("1");
	mldDetailVO1.setFlightNumberInb("4567");
	mldDetailVO1.setFlightNumberOub("156");
	mldDetailVO1.setFlightSequenceNumberInb(123);
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}

@Test
public void sendMLdMessages_WithRCFMode_WithTransactionLevel_KeyNull() throws SystemException {
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RCF");
	MLDDetailVO mldDetailVO = new MLDDetailVO();
	mldDetailVO.setMailModeOub("H");
	mldDetailVO.setMailModeInb("H");
	mldDetailVO.setCarrierCodeInb("123");
	mldMasterVO.setMldDetailVO(mldDetailVO);
	mldMasterVO.setSenderAirport("CDG");
	mldMasterVO.setReceiverAirport("DXB");
	mldMasterVO.setUldNumber("AV45671");
	mldMasterVO.setAddrCarrier("345");
	mldMasterVO.setTransactionLevel("U");
	mldMasterVO.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO);
	MLDMasterVO mldMasterVO2= new MLDMasterVO();
	mldMasterVO2.setEventCOde("RCF");
	MLDDetailVO mldDetailVO2 = new MLDDetailVO();
	mldDetailVO2.setMailModeOub("H");
	mldDetailVO2.setMailModeInb("H");
	mldDetailVO2.setCarrierCodeInb("123");
	mldMasterVO2.setMldDetailVO(mldDetailVO);
	mldMasterVO2.setSenderAirport("CDG");
	mldMasterVO2.setReceiverAirport("DXB");
	mldMasterVO2.setUldNumber("AV45671");
	mldMasterVO2.setAddrCarrier("345");
	mldMasterVO2.setTransactionLevel("U");
	mldMasterVO2.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO2);
	MLDMasterVO mldMasterVO1 = new MLDMasterVO();
	mldMasterVO1.setEventCOde("RCF");
	MLDDetailVO mldDetailVO1 = new MLDDetailVO();
	mldDetailVO1.setMailModeOub("1");
	mldDetailVO1.setMailModeInb("H");
	mldDetailVO1.setFlightNumberOub("156");
	mldDetailVO1.setCarrierIdInb(1);
	mldDetailVO1.setFlightSequenceNumberOub(123);
	mldMasterVO1.setMldDetailVO(mldDetailVO1);
	mldMasterVO1.setSenderAirport("CDG");
	mldMasterVO1.setReceiverAirport("DXB");
	mldMasterVO1.setUldNumber("AV45671");
	mldMasterVO1.setAddrCarrier("345");
	mldMasterVO1.setTransactionLevel("U");
	mldMasterVO1.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO1);
	MLDMasterVO mldMasterVO3 = new MLDMasterVO();
	mldMasterVO3.setEventCOde("RCF");
	MLDDetailVO mldDetailVO3 = new MLDDetailVO();
	mldDetailVO3.setMailModeOub("1");
	mldDetailVO3.setMailModeInb("1");
	mldDetailVO3.setFlightNumberInb("4567");
	mldDetailVO3.setFlightNumberOub("156");
	mldDetailVO3.setFlightSequenceNumberInb(123);
	mldMasterVO3.setMldDetailVO(mldDetailVO3);
	mldMasterVO3.setSenderAirport("CDG");
	mldMasterVO3.setReceiverAirport("DXB");
	mldMasterVO3.setUldNumber("AV45671");
	mldMasterVO3.setAddrCarrier("345");
	mldMasterVO3.setTransactionLevel("U");
	mldMasterVO3.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO3);
	MLDMasterVO mldMasterVO4 = new MLDMasterVO();
	mldMasterVO4.setEventCOde("RCF");
	mldMasterVO4.setMldDetailVO(mldDetailVO1);
	mldMasterVO4.setSenderAirport("CDG");
	mldMasterVO4.setReceiverAirport("DXB");
	mldMasterVO4.setUldNumber("AV45671");
	mldMasterVO4.setAddrCarrier("345");
	mldMasterVO.setTransactionLevel("U");
	mldMasterVO4.setWeight(new Measure(UnitConstants.WEIGHT, 0.0));
	mldMasterVOs.add(mldMasterVO4);
	keyUtils = KeyUtilInstance.getInstance();
	doReturn("1").when(keyUtils).getKey(any(Criterion.class));
	MLDControllerSpy.sendMLDMessages(mldMasterVOs);
}


@Test
public void createMLDVOsFromMailbagVOsVersion2_withTransactionULDLevel()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(11);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);
	bagVO.setTransactionLevel("U");
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);

	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = null;
	MLDDetailVO mldDetailVO = null;
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_RCF;
	String mldEventMode = mode;

	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();

	containerAssignmentVO.setFlightNumber("AV9001");
	containerAssignmentVO.setFlightSequenceNumber(1234);
	containerAssignmentVO.setCompanyCode("IBS");
	containerAssignmentVO.setAirportCode("SIN");

	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("SIN");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(11);
	flightFilterVO.setFlightNumber("AV9001");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	FlightValidationVO validationVO = new FlightValidationVO();
	validationVO.setCompanyCode("IBS");
	validationVO.setFlightCarrierId(11);
	validationVO.setFlightNumber("AV9001");
	validationVO.setFlightSequenceNumber(1234);
	flightValidationVOs.add(validationVO);

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;

	FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
	segmentVO.setSegmentDestination("SIN");
	segmentVO.setSegmentOrigin("SIN");
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("SIN");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("IBS");
	configurationVO.setMldversion("2");
	configurationVO.setAllocatedRequired("Y");
	mldConfiguration.add(configurationVO);

	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
			any(Integer.class));
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

}

@Test
public void createMLDVOsFromMailbagVOsVersion2_withTransactionMailbagLevel()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(11);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);
	bagVO.setTransactionLevel("M");
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(11);

	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = null;
	MLDDetailVO mldDetailVO = null;
	LogonAttributes logon = new LogonAttributes();
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_RCF;
	String mldEventMode = mode;

	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();

	containerAssignmentVO.setFlightNumber("AV9001");
	containerAssignmentVO.setFlightSequenceNumber(1234);
	containerAssignmentVO.setCompanyCode("IBS");
	containerAssignmentVO.setAirportCode("SIN");

	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode("IBS");
	flightFilterVO.setStation("SIN");
	flightFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	flightFilterVO.setFlightCarrierId(11);
	flightFilterVO.setFlightNumber("AV9001");
	flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);

	Collection<FlightValidationVO> flightValidationVOs = new ArrayList();
	FlightValidationVO validationVO = new FlightValidationVO();
	validationVO.setCompanyCode("IBS");
	validationVO.setFlightCarrierId(11);
	validationVO.setFlightNumber("AV9001");
	validationVO.setFlightSequenceNumber(1234);
	flightValidationVOs.add(validationVO);

	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;

	FlightSegmentSummaryVO segmentVO = new FlightSegmentSummaryVO();
	segmentVO.setSegmentDestination("SIN");
	segmentVO.setSegmentOrigin("SIN");
	MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
	mLDConfigurationFilterVO.setAirportCode("SIN");
	mLDConfigurationFilterVO.setCarrierCode("IBS");
	mLDConfigurationFilterVO.setCompanyCode("IBS");
	MLDConfigurationVO configurationVO = new MLDConfigurationVO();
	configurationVO.setAirportCode("SIN");
	configurationVO.setCarrierIdentifier(1134);
	configurationVO.setCompanyCode("IBS");
	configurationVO.setMldversion("2");
	configurationVO.setAllocatedRequired("Y");
	mldConfiguration.add(configurationVO);

	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).findAirline(any(String.class),
			any(Integer.class));
	doReturn(mldConfiguration).when(dao).findMLDCongfigurations(any(MLDConfigurationFilterVO.class));
	doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlight(flightFilterVO);
	MLDControllerSpy.createMLDVOsFromMailbagVOs(mailbagVOs, toContainerVO, mldEventMode);

}

@Test
public void flagMLDForMailOperations_RCF_withTransactionLevel()
		throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	mailbagVOs.add(bagVO);
	ContainerVO toContainerVO = new ContainerVO();
	String mode = "RCF";
	String mldEventMode = mode;
	Collection<MLDMasterVO>mldMasterVos = new ArrayList<MLDMasterVO>();
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	mldMasterVO.setEventCOde("RCF");
	mldMasterVO.setTransactionLevel("U");
	mldMasterVO.setrCFRequired(true);
	mldMasterVos.add(mldMasterVO);
		doReturn(mldMasterVos).when(MLDControllerSpy).createMLDVOsFromMailbagVOs(any(Collection.class),
				any(ContainerVO.class), any());
	MLDControllerSpy.flagMLDForMailOperations(mailbagVOs, mldEventMode);
}


@Test
	public void flagRCFmessageForMailbags_With_TransactionLevel()
			throws SharedProxyException, SystemException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);
	mailbagVOs.add(bagVO);
	Collection<ContainerVO>ContainerVOs = new ArrayList();
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(1134);
	toContainerVO.setTransactionLevel("U");
	ContainerVOs.add(toContainerVO);
	LogonAttributes logon = new LogonAttributes(); 
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_ALL;
	String mldEventMode = mode; 
	MLDConfiguration mldConfigurationEntity = new MLDConfiguration();
	mldConfigurationEntity.setMldversion("2");
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	doReturn(mldConfigurationEntity).when(PersistenceController.getEntityManager()).find(eq(MLDConfiguration.class), any(MLDConfigurationPK.class) );
	MLDControllerSpy.flagMLDForContainerTransfer(mailbagVOs,ContainerVOs,operationalFlightVO);
}


@Test
	public void flagRCFmessageForMailbags_Without_TransactionLevel()
			throws SharedProxyException, SystemException, PersistenceException, FinderException {
	Collection<MailbagVO> mailbagVOs = new ArrayList();
	MailbagVO bagVO = new MailbagVO();
	bagVO.setPol("SIN");
	bagVO.setContainerNumber("AKE11225AV");
	bagVO.setFlightNumber("AV9001");
	bagVO.setCompanyCode("IBS");
	bagVO.setScannedPort("SIN");
	bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	bagVO.setCarrierId(1134);
	bagVO.setCarrierCode("IBS");
	bagVO.setTransferFromCarrier("IBS");
	bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
	bagVO.setMailSequenceNumber(1601);
	mailbagVOs.add(bagVO);
	Collection<ContainerVO>ContainerVOs = new ArrayList();
	ContainerVO toContainerVO = new ContainerVO();
	toContainerVO.setCarrierCode("IBS");
	toContainerVO.setCarrierId(1134);
	ContainerVOs.add(toContainerVO);
	LogonAttributes logon = new LogonAttributes(); 
	logon.setCompanyCode("IBS");
	String mode = MailConstantsVO.MLD_ALL;
	String mldEventMode = mode; 
	MLDConfiguration mldConfigurationEntity = new MLDConfiguration();
	mldConfigurationEntity.setMldversion("2");
	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	airlineValidationVO.setAirlineIdentifier(11);
	int carrierCode = 11;
	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
	doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
	doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	doReturn(mldConfigurationEntity).when(PersistenceController.getEntityManager()).find(eq(MLDConfiguration.class), any(MLDConfigurationPK.class) );
	MLDControllerSpy.flagMLDForContainerTransfer(mailbagVOs,ContainerVOs,operationalFlightVO);
}

@Test
public void updateMLDMessageSentTime()
		throws SystemException, PersistenceException, FinderException {
	MLDMessageVO mldMessageVO=new MLDMessageVO();
	mldMessageVO.setCompanyCode("QF");
	mldMessageVO.setSerialNumber(1105);
	MLDMessageMaster mldMessageMaster = new MLDMessageMaster();
	mldMessageMaster.setContainerNumber("sdsd");
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	doReturn(mldMessageMaster).when(PersistenceController.getEntityManager()).find(eq(MLDMessageMaster.class), any(MLDMessageMasterPK.class) );
	MLDControllerSpy.updateMLDMsgSentTime(mldMessageVO);
}

@Test
public void updateMLDMessageSentTimeReturnsException()
		throws SystemException, PersistenceException, FinderException {
	MLDMessageVO mldMessageVO=new MLDMessageVO();
	mldMessageVO.setCompanyCode("QF");
	mldMessageVO.setSerialNumber(0);
	MLDMessageMasterPK mLDMessageMasterPK = new MLDMessageMasterPK();
	mLDMessageMasterPK.setCompanyCode(mldMessageVO.getCompanyCode());
	mLDMessageMasterPK.setSerialNumber(mldMessageVO.getSerialNumber());
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
	doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MLDMessageMaster.class), any(MLDMessageMasterPK.class) );
	MLDControllerSpy.updateMLDMsgSentTime(mldMessageVO);
}

	@Test
	public void flagMLDForMailAcceptanceForValidMailAcceptance_TFD() throws SystemException, PersistenceException {
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setContainerType("B");
		containerDetails.add(containerDetailsVO);
		mailAcceptanceVO.setContainerDetails(containerDetails);
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("AV");
		bagVO.setTransferFromCarrier("AV");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);
		bagVO.setOperationalFlag("I");
		bagVO.setFlightSequenceNumber(1);
		mailbagVOs.add(bagVO);
		Collection<PartnerCarrierVO> partnerCarierVos = new ArrayList<>();
		PartnerCarrierVO partner = new PartnerCarrierVO();
		partner.setPartnerCarrierCode("AV");
		partner.setMldTfdReq("A");
		partnerCarierVos.add(partner);
		Collection<MailbagVO> transferFromPartnerCarriermailbagVOs = new ArrayList<>();
		transferFromPartnerCarriermailbagVOs.add(bagVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(partnerCarierVos).when(dao).findAllPartnerCarriers("IBS", "AV", "SIN");
		MLDControllerSpy.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);

	}

	@Test
	public void flagMLDForMailAcceptance_TFD_TransferCarrier_isEmpty() throws SystemException, PersistenceException {
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setContainerType("B");
		containerDetails.add(containerDetailsVO);
		mailAcceptanceVO.setContainerDetails(containerDetails);
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("AV");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);
		bagVO.setTransferFromCarrier("");
		bagVO.setOperationalFlag("I");
		mailbagVOs.add(bagVO);
		Collection<MailbagVO> transferFromPartnerCarriermailbagVOs = new ArrayList<>();
		transferFromPartnerCarriermailbagVOs.add(bagVO);
		MLDControllerSpy.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);

	}

	@Test
	public void flagMLDForMailAcceptance_TFD_TransferCarrier_isNULL() throws SystemException, PersistenceException {
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setContainerType("B");
		containerDetails.add(containerDetailsVO);
		mailAcceptanceVO.setContainerDetails(containerDetails);
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("AV");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);
		bagVO.setOperationalFlag("I");
		mailbagVOs.add(bagVO);
		MLDControllerSpy.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);

	}

	@Test
	public void flagMLDForMailAcceptance_TFD_parntnerCarriers_Null() throws SystemException, PersistenceException {
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setContainerType("B");
		containerDetails.add(containerDetailsVO);
		mailAcceptanceVO.setContainerDetails(containerDetails);
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("AV");
		bagVO.setTransferFromCarrier("AV");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);
		mailbagVOs.add(bagVO);
		Collection<PartnerCarrierVO> partnerCarierVos = null;
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(partnerCarierVos).when(dao).findAllPartnerCarriers("IBS", "AV", "SIN");
		MLDControllerSpy.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);

	}

	@Test
	public void flagMLDForMailAcceptance_TFD_invalid_PartnerCode() throws SystemException, PersistenceException {
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setContainerType("B");
		containerDetails.add(containerDetailsVO);
		mailAcceptanceVO.setContainerDetails(containerDetails);
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("AV");
		bagVO.setTransferFromCarrier("QF");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);
		bagVO.setOperationalFlag("I");
		mailbagVOs.add(bagVO);
		Collection<PartnerCarrierVO> partnerCarierVos = new ArrayList<>();
		PartnerCarrierVO partner = new PartnerCarrierVO();
		partner.setPartnerCarrierCode("AV");
		partner.setMldTfdReq("T");
		partnerCarierVos.add(partner);
		Collection<MailbagVO> transferFromPartnerCarriermailbagVOs = new ArrayList<>();
		transferFromPartnerCarriermailbagVOs.add(bagVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(partnerCarierVos).when(dao).findAllPartnerCarriers("IBS", "AV", "SIN");
		MLDControllerSpy.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);

	}

	@Test
	public void flagMLDForMailAcceptance_TFD_invalid_Tfdreqid() throws SystemException, PersistenceException {
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setContainerType("B");
		containerDetails.add(containerDetailsVO);
		mailAcceptanceVO.setContainerDetails(containerDetails);
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("SIN");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV9001");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("AV");
		bagVO.setTransferFromCarrier("AV");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);
		bagVO.setOperationalFlag("I");
		bagVO.setFlightSequenceNumber(0);
		mailAcceptanceVO.setFlightSequenceNumber(1);
		mailbagVOs.add(bagVO);
		Collection<PartnerCarrierVO> partnerCarierVos = new ArrayList<>();
		PartnerCarrierVO partner = new PartnerCarrierVO();
		partner.setPartnerCarrierCode("AV");
		partner.setMldTfdReq("T");
		partnerCarierVos.add(partner);
		Collection<MailbagVO> transferFromPartnerCarriermailbagVOs = new ArrayList<>();
		transferFromPartnerCarriermailbagVOs.add(bagVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(partnerCarierVos).when(dao).findAllPartnerCarriers("IBS", "AV", "SIN");
		MLDControllerSpy.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);

	}
	
	@Test
	public void flagMLDForMailAcceptance_TFD_mailbags_isEmpty() throws SystemException, PersistenceException {
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setContainerType("B");
		containerDetails.add(containerDetailsVO);
		mailAcceptanceVO.setContainerDetails(containerDetails);
		Collection<MailbagVO> mailbagVOs =null;
		MLDControllerSpy.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);

	}

	@Test
	public void flagMLDForMailbagTransferVersion2_TFD()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("FRA");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(1134);

		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_TFD;
		String mldEventMode = mode;

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setAllocatedRequired("Y");
		mldConfiguration.add(configurationVO);
		Collection<PartnerCarrierVO> partnerCarierVos = new ArrayList<>();
		PartnerCarrierVO partner = new PartnerCarrierVO();
		partner.setPartnerCarrierCode("IBS");
		partner.setMldTfdReq("A");
		partnerCarierVos.add(partner);
		MLDConfiguration mldConfigurationEntity = new MLDConfiguration();
		mldConfigurationEntity.setMldversion("2");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(partnerCarierVos).when(dao).findAllPartnerCarriers("IBS", "AV", "SIN");
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(mldConfigurationEntity).when(PersistenceController.getEntityManager()).find(eq(MLDConfiguration.class),
				any(MLDConfigurationPK.class));
		MLDControllerSpy.flagMLDForMailbagTransfer(mailbagVOs, toContainerVO, operationalFlightVO, mode);

	}

	@Test
	public void flagMLDForMailbagTransferVersion2_TFD_partnerCarrierisEmpty()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("FRA");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("AV");
		toContainerVO.setCarrierId(1134);

		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_TFD;
		String mldEventMode = mode;

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setAllocatedRequired("Y");
		mldConfiguration.add(configurationVO);
		Collection<PartnerCarrierVO> partnerCarierVos = new ArrayList<>();
		MLDConfiguration mldConfigurationEntity = new MLDConfiguration();
		mldConfigurationEntity.setMldversion("2");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(partnerCarierVos).when(dao).findAllPartnerCarriers("IBS", "AV", "SIN");
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(mldConfigurationEntity).when(PersistenceController.getEntityManager()).find(eq(MLDConfiguration.class),
				any(MLDConfigurationPK.class));
		MLDControllerSpy.flagMLDForMailbagTransfer(mailbagVOs, toContainerVO, operationalFlightVO, mode);

	}

	@Test
	public void flagMLDForMailbagTransferVersion2_TFD_With_Mldtfdreqid()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("FRA");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(1134);

		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_TFD;
		String mldEventMode = mode;

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setAllocatedRequired("Y");
		mldConfiguration.add(configurationVO);
		Collection<PartnerCarrierVO> partnerCarierVos = new ArrayList<>();
		PartnerCarrierVO partner = new PartnerCarrierVO();
		partner.setPartnerCarrierCode("IBS");
		partner.setMldTfdReq("T");
		partnerCarierVos.add(partner);
		MLDConfiguration mldConfigurationEntity = new MLDConfiguration();
		mldConfigurationEntity.setMldversion("2");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(partnerCarierVos).when(dao).findAllPartnerCarriers("IBS", "AV", "SIN");
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(mldConfigurationEntity).when(PersistenceController.getEntityManager()).find(eq(MLDConfiguration.class),
				any(MLDConfigurationPK.class));
		MLDControllerSpy.flagMLDForMailbagTransfer(mailbagVOs, toContainerVO, operationalFlightVO, mode);

	}

	@Test
	public void flagMLDForMailbagTransferVersion2_TFD_InvalidParnter()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("FRA");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("AV");
		toContainerVO.setCarrierId(1134);

		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_TFD;
		String mldEventMode = mode;

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setAllocatedRequired("Y");
		mldConfiguration.add(configurationVO);
		Collection<PartnerCarrierVO> partnerCarierVos = new ArrayList<>();
		PartnerCarrierVO partner = new PartnerCarrierVO();
		partner.setPartnerCarrierCode("QF");
		partner.setMldTfdReq("A");
		partnerCarierVos.add(partner);
		MLDConfiguration mldConfigurationEntity = new MLDConfiguration();
		mldConfigurationEntity.setMldversion("2");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(partnerCarierVos).when(dao).findAllPartnerCarriers("IBS", "AV", "SIN");
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(mldConfigurationEntity).when(PersistenceController.getEntityManager()).find(eq(MLDConfiguration.class),
				any(MLDConfigurationPK.class));
		MLDControllerSpy.flagMLDForMailbagTransfer(mailbagVOs, toContainerVO, operationalFlightVO, mode);

	}
	@Test
	public void flagMLDForMailbagTransferVersion2_TFD_With_Invalid_Mldtfdreqid()
			throws SystemException, ProxyException, SharedProxyException, PersistenceException, FinderException {
		Collection<MailbagVO> mailbagVOs = new ArrayList();
		MailbagVO bagVO = new MailbagVO();
		bagVO.setPol("FRA");
		bagVO.setContainerNumber("AKE11225AV");
		bagVO.setFlightNumber("AV4567");
		bagVO.setCompanyCode("IBS");
		bagVO.setScannedPort("SIN");
		bagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		bagVO.setCarrierId(1134);
		bagVO.setCarrierCode("IBS");
		bagVO.setTransferFromCarrier("IBS");
		bagVO.setMailbagId("INBLRASGSINAACA21200121110100");
		bagVO.setMailSequenceNumber(1601);

		mailbagVOs.add(bagVO);
		ContainerVO toContainerVO = new ContainerVO();
		toContainerVO.setCarrierCode("IBS");
		toContainerVO.setCarrierId(1134);

		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		String mode = MailConstantsVO.MLD_TFD;
		String mldEventMode = mode;

		Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(11);
		int carrierCode = 11;
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		mLDConfigurationFilterVO.setAirportCode("SIN");
		mLDConfigurationFilterVO.setCarrierCode("IBS");
		mLDConfigurationFilterVO.setCompanyCode("IBS");
		MLDConfigurationVO configurationVO = new MLDConfigurationVO();
		configurationVO.setAirportCode("SIN");
		configurationVO.setCarrierIdentifier(1134);
		configurationVO.setCompanyCode("IBS");
		configurationVO.setMldversion("2");
		configurationVO.setAllocatedRequired("Y");
		mldConfiguration.add(configurationVO);
		Collection<PartnerCarrierVO> partnerCarierVos = new ArrayList<>();
		PartnerCarrierVO partner = new PartnerCarrierVO();
		partner.setPartnerCarrierCode("IBS");
		partner.setMldTfdReq("B");
		partnerCarierVos.add(partner);
		MLDConfiguration mldConfigurationEntity = new MLDConfiguration();
		mldConfigurationEntity.setMldversion("2");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAILTRACKING_DEFAULTS);
		doReturn(partnerCarierVos).when(dao).findAllPartnerCarriers("IBS", "AV", "SIN");
		doReturn(airlineValidationVOAlphaCode).when(sharedAirlineProxy).validateAlphaCode("IBS", "IBS");
		doReturn(carrierCode).when(mldControllerMock).findCarrierIdentifier("IBS", "IBS");
		doReturn(mldConfigurationEntity).when(PersistenceController.getEntityManager()).find(eq(MLDConfiguration.class),
				any(MLDConfigurationPK.class));
		MLDControllerSpy.flagMLDForMailbagTransfer(mailbagVOs, toContainerVO, operationalFlightVO, mode);
	}

}
