package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.icargo.business.admin.user.vo.UserParameterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryAttachmentVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryFilterVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVolumeDetailsVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.aa.AAMailController;
import com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeCache;
import com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationCache;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.AutoAttachAWBDetailsFeature;
import com.ibsplc.icargo.business.mail.operations.feature.publishmailoperationsdata.PublishRapidMailOperationsDataFeature;
import com.ibsplc.icargo.business.mail.operations.feature.saveloadplandetailsformail.SaveLoadPlanDetailsForMailFeature;
import com.ibsplc.icargo.business.mail.operations.feature.saveloadplandetailsformail.SaveLoadPlanDetailsForMailFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistory.SaveMailbagHistoryFeature;
import com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistory.SaveMailbagHistoryFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeature;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.savesecuritydetails.SaveSecurityDetailsFeature;
import com.ibsplc.icargo.business.mail.operations.feature.savesecuritydetails.SaveSecurityDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.savesendresditmessagedetails.SaveSendResditMessageDetailsFeature;
import com.ibsplc.icargo.business.mail.operations.feature.savesendresditmessagedetails.SaveSendResditMessageDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.*;
import com.ibsplc.icargo.business.mail.operations.vo.*;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.AutoForwardDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mailsecurityandscreening.SecurityAndScreeningMessageVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.FlightListingFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.OtherCustomsInformationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationMasterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralRuleConfigDetailsVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupDetailsVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.business.shared.uld.vo.AircraftCompatibilityVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.security.context.SecurityContext;
import com.ibsplc.xibase.server.framework.persistence.keygen.GenerationFailedException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import org.junit.Test;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class MailControllerTest extends AbstractFeatureTest {

	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String MAILBAG_ID = "USDFWADEFRAAACA01200120001200";
	private static final String COMPANY_CODE = "IBS";
	public static final String MAIL_OPERATIONS_TRANSFER_TRANSACTION="mail.operation.transferoutinonetransaction";
	private static final String ID_SEP = "~";
	private MailController spy;
	private MailTrackingDefaultsDAO dao;
	SaveMailbagHistoryFeature saveMailbagHistoryFeature;
    DocumentRepositoryProxy documentRepositoryProxy;
	private Container containerEntity;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private SharedAreaProxy sharedAreaProxy;
	private MailController mailControllerBean; 
	private AAMailController aamailControllerBean; 
	private MailController mailControllerMock;
	private ULDForSegment ulDForSegmentEntity;
	private ULDAtAirport uLDAtAirportEntity;
	private ULDDefaultsProxy uLDDefaultsProxy;
	private OperationsFltHandlingProxy operationsFltHandlingProxy;
	private MsgBrokerMessageProxy msgBrokerMessageProxy;
	private SecurityContext securityContext;
	private AssignedFlight assignedFlightEntity;
	SaveSecurityDetailsFeature saveSecurityDetailsFeature;
	SavePAWBDetailsFeature savePAWBDetailsFeature;
	private PostalAdministrationCache postalAdministrationCache;
	private static final String CARDITKEY="US101K4IRDPEFGIUA0";
	private static final String PAWPARCODE="PAWBASSCONENAB";
	private static final String INVINFO="INVINFO";	  
    private ConsignmentScreeningDetails ConsignmentScreeningDetailsEntity;
    private Mailbag MailbagEntity;
	private OfficeOfExchangeCache officeOfExchangeCache;
	private static final String DESTN = "FRPART";
	private static final String ORIGIN = "AEDXBT";
	private SharedGeneralMasterGroupingProxy sharedGeneralMasterGroupingProxy;
	private SharedCommodityProxy sharedCommodityProxy;
	private FlightOperationsProxy flightOperationsProxy; 
	SaveLoadPlanDetailsForMailFeature saveLoadPlanDetailsForMailFeature;
	private SharedAirlineProxy sharedAirlineProxy;
	private ConsignmentScreeningDetails consignmentScreeningDetailsEntity;
	private FlightFilterVO flightFilterVO;
	private String masterTypeForGPADetails;
	private String masterTypeForGPADetailsWithoutPACOD;
	private String masterTypeForMailbagDetails;
	private String masterTypeWithoutSubclassCode;
	private String companyCode;
	private String airportCode;	
    private OperationsShipmentProxy operationsShipmentProxy;
        private KeyUtilInstance keyUtils;
        private SharedCustomerProxy sharedCustomerProxy;
        private AutoAttachAWBDetailsFeature autoAttachAWBDetailsFeature;
    private ResditController resditController;
    private SaveSendResditMessageDetailsFeature saveSendResditMessageDetailsFeature;
    private ConsignmentScreeningVO consignmentScreeningVO;
    private Mailbag mailBag;
	private MailbagPK mailbagPK;
	//private Mailbag mailbagBean;
	private AdminUserProxy adminUserProxy;
	private  Collection<UserParameterVO> userParameterVOs;
	private MailController mailController;
	private SharedULDProxy sharedULDProxy;
	private MailOperationsProxy mailOperationsProxy;
	
	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		spy = spy(new MailController());
		dao = mock(MailTrackingDefaultsDAO.class);
		resditController = mock(ResditController.class);
		saveMailbagHistoryFeature= mockBean(SaveMailbagHistoryFeatureConstants.SAVE_MAILBAG_HISTORY_FEATURE,SaveMailbagHistoryFeature.class);
		saveSendResditMessageDetailsFeature= mockBean(SaveSendResditMessageDetailsFeatureConstants.SAVE_SEND_RESDIT_MESSAGE_DETAILS_FEATURE,SaveSendResditMessageDetailsFeature.class);
		containerEntity = new Container();
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		sharedCommodityProxy = mockProxy(SharedCommodityProxy.class);
		sharedGeneralMasterGroupingProxy = mockProxy(SharedGeneralMasterGroupingProxy.class);
		mailControllerBean = mockBean("mAilcontroller", MailController.class);
		aamailControllerBean = mockBean("aaMailController", AAMailController.class);
		mailControllerMock = mock(MailController.class);
		ULDForSegmentVO uLDForSegmentVO = new ULDForSegmentVO();
		uLDForSegmentVO.setCompanyCode(getCompanyCode());
		ulDForSegmentEntity = new ULDForSegment(uLDForSegmentVO);
		uLDAtAirportEntity = new ULDAtAirport();
		uLDDefaultsProxy = mockProxy(ULDDefaultsProxy.class);
		operationsFltHandlingProxy = mockProxy(OperationsFltHandlingProxy.class);
		securityContext = mock(SecurityContext.class);
        documentRepositoryProxy=mockProxy(DocumentRepositoryProxy.class);	
        msgBrokerMessageProxy=mockProxy(MsgBrokerMessageProxy.class);	
        assignedFlightEntity = new AssignedFlight();
        saveSecurityDetailsFeature= mockBean(SaveSecurityDetailsFeatureConstants.SAVE_SECURITY_DETAILS_FEATURE,SaveSecurityDetailsFeature.class);
        savePAWBDetailsFeature= mockBean(SavePAWBDetailsFeatureConstants.SAVE_PAWB_FEATURE,SavePAWBDetailsFeature.class);
        saveLoadPlanDetailsForMailFeature =mockBean(SaveLoadPlanDetailsForMailFeatureConstants.SAVE_LOADPLAN_FEATURE,SaveLoadPlanDetailsForMailFeature.class);
        postalAdministrationCache = spy(PostalAdministrationCache.class);
    	doReturn(postalAdministrationCache).when(CacheFactory.getInstance()).getCache(PostalAdministrationCache.ENTITY_NAME);
		ConsignmentScreeningDetailsEntity = spy(new ConsignmentScreeningDetails());
		
		MailbagEntity = mockBean("MailbagEntity", Mailbag.class);  
		officeOfExchangeCache = spy(OfficeOfExchangeCache.class);
		doReturn(officeOfExchangeCache).when(CacheFactory.getInstance()).getCache(OfficeOfExchangeCache.ENTITY_NAME);
		flightOperationsProxy = mockProxy(FlightOperationsProxy.class);
		sharedAreaProxy = mockProxy(SharedAreaProxy.class);
		sharedAirlineProxy=mockProxy(SharedAirlineProxy.class);
		consignmentScreeningDetailsEntity = spy(new ConsignmentScreeningDetails());
		flightFilterVO = mock(FlightFilterVO.class);
		masterTypeForGPADetails = "PACOD,SUBCLS,EXGOFC";
		masterTypeWithoutSubclassCode = "PACOD,EXGOFC";
		companyCode = "AV";
		masterTypeForGPADetailsWithoutPACOD = "SUBCLS,EXGOFC";
		masterTypeForMailbagDetails = "MALBAGINF";
		airportCode = "EWR"; 
        operationsShipmentProxy = mockProxy(OperationsShipmentProxy.class);
         KeyUtilInstanceMock.mockKeyUtilInstance();
	keyUtils = KeyUtilInstance.getInstance();
		sharedCustomerProxy = mockProxy(SharedCustomerProxy.class);
		autoAttachAWBDetailsFeature = mockBean("mail.operations.autoAttachAWBDetailsFeature",AutoAttachAWBDetailsFeature.class);
		mailBag = mock(Mailbag.class);
		//mailbagBean = mockBean("MailbagEntity", Mailbag.class);
		adminUserProxy = mockProxy(AdminUserProxy.class);
		mailController = mock(MailController.class);
		 sharedULDProxy = mockProxy(SharedULDProxy.class);
		 mailOperationsProxy = mockProxy(MailOperationsProxy.class);
}

	@Test
	public void shouldInvokeDao_When_ConsignmentDetailsForUpload_IsInvoked()
			throws SystemException, PersistenceException {
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		spy.fetchConsignmentDetailsForUpload(any(FileUploadFilterVO.class));
		verify(dao, times(1)).fetchConsignmentDetailsForUpload(any(FileUploadFilterVO.class));
	}

	@Test
	public void shouldInvokeDao_When_MailbagDetailsForValidation_IsInvoked()
			throws SystemException, PersistenceException {
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		spy.getMailbagDetailsForValidation(any(String.class), any(String.class));
		verify(dao, times(1)).getMailbagDetailsForValidation(any(String.class), any(String.class));
	}

	@Test
	public void shouldNotInsertMailbagHistory_When_MailbagHistoryVOsIsNull() throws Exception {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = null;
		spy.saveMailbagHistory(mailbagHistoryVOs);
		verify(saveMailbagHistoryFeature, times(0)).execute(any(MailbagHistoryVO.class));
	}
	@Test
	public void shouldNotInsertMailbagHistory_When_MailbagHistoryVOsIsEmpty() throws Exception {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<>();
		spy.saveMailbagHistory(mailbagHistoryVOs);
		verify(saveMailbagHistoryFeature, times(0)).execute(any(MailbagHistoryVO.class));
	}
	@Test
	public void verifySaveMailbagHistoryFeature_IsInvoked() throws SystemException, BusinessException {
		MailbagHistoryVO mailbagHistoryVO = setupMailbagHistoryVO();
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<>();
		mailbagHistoryVOs.add(mailbagHistoryVO);
		spy.saveMailbagHistory(mailbagHistoryVOs);
		verify(saveMailbagHistoryFeature, times(1)).execute(any(MailbagHistoryVO.class));
	}
	@Test(expected=SystemException.class)
	public void shouldThrowBusinessException_When_SaveMailbagHistoryFeature_IsInvoked() throws SystemException, BusinessException {
		MailbagHistoryVO mailbagHistoryVO = setupMailbagHistoryVO();
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<>();
		mailbagHistoryVOs.add(mailbagHistoryVO);
		BusinessException businessException = new BusinessException() {
		};
		doThrow(businessException).when(saveMailbagHistoryFeature).execute(any(MailbagHistoryVO.class));
		spy.saveMailbagHistory(mailbagHistoryVOs);	
	}

	private MailbagHistoryVO setupMailbagHistoryVO() {
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setCompanyCode(COMPANY_CODE);
		mailbagHistoryVO.setMailbagId(MAILBAG_ID);
		return mailbagHistoryVO;
	}
	
		@Test
		public void createContainer_Find() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
			ContainerVO containerVo = new ContainerVO();
			ContainerPK ContainerPK = new ContainerPK();
			Collection<ContainerVO> containerVos = new ArrayList<>();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			HashMap<String, String> systemParameterMap = null;
			containerVo.setAssignedPort("CDG");
			containerVo.setReassignFlag(false);
			containerVo.setContainerNumber("AKE12345AV");
			containerVo.setOperationFlag("I");
			containerVos.add(containerVo);
			containerEntity.setContainerPK(ContainerPK);
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			spy.saveContainers(operationalFlightVO, containerVos);
			assertThat(containerVo.isReassignFlag(), is(false));
		}
		
		@Test
		public void createContainer_FinderException() throws SystemException, FinderException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException{
			ContainerVO containerVo = new ContainerVO();
			Collection<ContainerVO> containerVos = new ArrayList<>();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			HashMap<String, String> systemParameterMap = null;
			containerVo.setAssignedPort("CDG");
			containerVo.setReassignFlag(false);
			containerVo.setContainerNumber("AKE12345AV");
			containerVo.setOperationFlag("I");
			containerVo.setUldReferenceNo(1);
			containerVos.add(containerVo);
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			spy.saveContainers(operationalFlightVO, containerVos);
		}
		
		@Test
		public void modifyContainer_ToSatisfyUpdateCondition() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
			ContainerVO containerVo = new ContainerVO();
			ContainerPK ContainerPK = new ContainerPK();
			Collection<ContainerVO> containerVos = new ArrayList<>();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			operationalFlightVO.setFlightSequenceNumber(-1);
			HashMap<String, String> systemParameterMap = null;
			Collection<OnwardRoutingVO> onwardRoutings = new ArrayList<>();
			OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
			onwardRoutingVO.setCompanyCode(getCompanyCode());
			onwardRoutingVO.setOnwardFlightDate(new LocalDate("CDG", Location.ARP,true));
			onwardRoutings.add(onwardRoutingVO);
			containerVo.setOnwardRoutings(onwardRoutings);
			containerVo.setAssignedPort("CDG");
			containerVo.setReassignFlag(false);
			containerVo.setContainerNumber("AKE12345AV");
			containerVo.setOperationFlag("U");
			containerVo.setFromDeviationList(true);
			containerVo.setUldReferenceNo(1);
			containerVos.add(containerVo);
			containerEntity.setContainerPK(ContainerPK);
			containerEntity.setAcceptanceFlag("Y");
			containerEntity.setTransitFlag("Y");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			spy.saveContainers(operationalFlightVO, containerVos);
			assertThat(containerVo.getOperationFlag(),is("U"));
		}
		
		@Test(expected=SystemException.class)
		public void modifyContainer_FinderException() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
			FinderException finderException = new FinderException();
			ContainerVO containerVo = new ContainerVO();
			ContainerPK ContainerPK = new ContainerPK();
			Collection<ContainerVO> containerVos = new ArrayList<>();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			operationalFlightVO.setFlightSequenceNumber(-1);
			HashMap<String, String> systemParameterMap = null;
			Collection<OnwardRoutingVO> onwardRoutings = new ArrayList<>();
			OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
			onwardRoutingVO.setCompanyCode(getCompanyCode());
			onwardRoutingVO.setOnwardFlightDate(new LocalDate("CDG", Location.ARP,true));
			onwardRoutings.add(onwardRoutingVO);
			containerVo.setOnwardRoutings(onwardRoutings);
			containerVo.setAssignedPort("CDG");
			containerVo.setReassignFlag(false);
			containerVo.setContainerNumber("AKE12345AV");
			containerVo.setOperationFlag("U");
			containerVo.setFromDeviationList(true);
			containerVo.setUldReferenceNo(1);
			containerVos.add(containerVo);
			containerEntity.setContainerPK(ContainerPK);
			containerEntity.setAcceptanceFlag("Y");
			containerEntity.setTransitFlag("Y");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			doThrow(finderException).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			spy.saveContainers(operationalFlightVO, containerVos);
		}
		
		@Test
		public void modifyContainer_OnwardRoutingsArrayListNull() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
			ContainerVO containerVo = new ContainerVO();
			ContainerPK ContainerPK = new ContainerPK();
			Collection<ContainerVO> containerVos = new ArrayList<>();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			operationalFlightVO.setFlightSequenceNumber(-1);
			HashMap<String, String> systemParameterMap = null;
			Collection<OnwardRoutingVO> onwardRoutings = new ArrayList<>();
			onwardRoutings =null;
			Set<OnwardRouting> onwardRoutes = new HashSet<OnwardRouting>();
			OnwardRouting onwardRouting = new OnwardRouting();
			onwardRouting.setOnwardCarrierCode(getCompanyCode());
			onwardRoutes.add(onwardRouting);
			containerVo.setOnwardRoutings(onwardRoutings);
			containerVo.setAssignedPort("CDG");
			containerVo.setReassignFlag(false);
			containerVo.setContainerNumber("AKE12345AV");
			containerVo.setOperationFlag("U");
			containerVo.setFromDeviationList(false);
			containerVo.setUldReferenceNo(1);
			containerVos.add(containerVo);
			containerEntity.setContainerPK(ContainerPK);
			containerEntity.setAcceptanceFlag("Y");
			containerEntity.setOnwardRoutes(onwardRoutes);
			containerEntity.setTransitFlag(null);
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			spy.saveContainers(operationalFlightVO, containerVos);
			assertNull(onwardRoutings);
		}
		
		@Test
		public void modifyContainer_OnwardRoutingsSizeNull() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
			ContainerVO containerVo = new ContainerVO();
			ContainerPK ContainerPK = new ContainerPK();
			Collection<ContainerVO> containerVos = new ArrayList<>();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			operationalFlightVO.setFlightSequenceNumber(-1);
			HashMap<String, String> systemParameterMap = null;
			Collection<OnwardRoutingVO> onwardRoutings = new ArrayList<>(0);
			containerVo.setOnwardRoutings(onwardRoutings);
			containerVo.setAssignedPort("CDG");
			containerVo.setReassignFlag(false);
			containerVo.setContainerNumber("AKE12345AV");
			containerVo.setOperationFlag("U");
			containerVo.setFromDeviationList(true);
			containerVo.setUldReferenceNo(1);
			containerVos.add(containerVo);
			containerEntity.setContainerPK(ContainerPK);
			containerEntity.setAcceptanceFlag("Y");
			containerEntity.setTransitFlag(null);
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			spy.saveContainers(operationalFlightVO, containerVos);
			assertThat(onwardRoutings.size(), is(0));
		}
		
		@Test
		public void modifyContainer_OnwardRouteArrayListNull() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
			ContainerVO containerVo = new ContainerVO();
			ContainerPK ContainerPK = new ContainerPK();
			Collection<ContainerVO> containerVos = new ArrayList<>();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			operationalFlightVO.setFlightSequenceNumber(-1);
			HashMap<String, String> systemParameterMap = null;
			Set<OnwardRouting> onwardRoutes = new HashSet<OnwardRouting>();
			onwardRoutes = null;
			containerVo.setAssignedPort("CDG");
			containerVo.setReassignFlag(false);
			containerVo.setContainerNumber("AKE12345AV");
			containerVo.setOperationFlag("U");
			containerVo.setFromDeviationList(false);
			containerVo.setUldReferenceNo(1);
			containerVos.add(containerVo);
			containerEntity.setContainerPK(ContainerPK);
			containerEntity.setAcceptanceFlag("Y");
			containerEntity.setOnwardRoutes(onwardRoutes);
			containerEntity.setTransitFlag("Y");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			spy.saveContainers(operationalFlightVO, containerVos);
			assertNull(onwardRoutes);
		}
		
		@Test
		public void modifyContainer_OnwardRouteSizeNull() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
			ContainerVO containerVo = new ContainerVO();
			ContainerPK ContainerPK = new ContainerPK();
			Collection<ContainerVO> containerVos = new ArrayList<>();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			operationalFlightVO.setFlightSequenceNumber(-1);
			HashMap<String, String> systemParameterMap = null;
			Set<OnwardRouting> onwardRoutes = new HashSet<OnwardRouting>(0);
			containerVo.setAssignedPort("CDG");
			containerVo.setReassignFlag(false);
			containerVo.setContainerNumber("AKE12345AV");
			containerVo.setOperationFlag("U");
			containerVo.setUldReferenceNo(1);
			containerVos.add(containerVo);
			containerEntity.setContainerPK(ContainerPK);
			containerEntity.setAcceptanceFlag("Y");
			containerEntity.setOnwardRoutes(onwardRoutes);
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			spy.saveContainers(operationalFlightVO, containerVos);
			assertThat(onwardRoutes.size(), is(0));
		}
		
		@Test
		public void modifyContainer_AccepatanceFlagNo() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
			ContainerVO containerVo = new ContainerVO();
			ContainerPK ContainerPK = new ContainerPK();
			Collection<ContainerVO> containerVos = new ArrayList<>();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			operationalFlightVO.setFlightSequenceNumber(-1);
			HashMap<String, String> systemParameterMap = null;
			containerVo.setAssignedPort("CDG");
			containerVo.setReassignFlag(false);
			containerVo.setContainerNumber("AKE12345AV");
			containerVo.setOperationFlag("U");
			containerVo.setUldReferenceNo(1);
			containerVos.add(containerVo);
			containerEntity.setContainerPK(ContainerPK);
			containerEntity.setAcceptanceFlag("N");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			spy.saveContainers(operationalFlightVO, containerVos);
			assertThat(containerEntity.getAcceptanceFlag(),is("N"));
		}
		
		
		@Test
		public void updateRetainFlagForContainer_Implementation() throws FinderException, SystemException, RemoteException {
			mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
			ContainerVO containerVO = new ContainerVO();
			doNothing().when(mailControllerMock).flagContainerAuditForRetaining(any(ContainerVO.class));
			spy.updateRetainFlagForContainer(containerVO);
			assertNotNull(containerVO);
		}
		
		@Test
		public void constructContainerVOFromDetails_ReassignFlagTrue() throws ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, SystemException{
			ContainerDetailsVO containerDetailsVO =new ContainerDetailsVO();
			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			containerDetailsVO.setReassignFlag(true);
			containerDetailsVOs.add(containerDetailsVO);
			mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
			spy.saveAcceptedContainers(mailAcceptanceVO);
			assertTrue(containerDetailsVO.isReassignFlag());
		}
		
		@Test
		public void constructContainerVOFromDetails_ReassignFlagFalse() throws ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, SystemException{
			ContainerDetailsVO containerDetailsVO =new ContainerDetailsVO();
			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			containerDetailsVO.setContainerOperationFlag("I");
			containerDetailsVO.setPaCode("US101");
			containerDetailsVO.setReassignFlag(false);
			containerDetailsVOs.add(containerDetailsVO);
			mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
			spy.saveAcceptedContainers(mailAcceptanceVO);
			assertFalse(containerDetailsVO.isReassignFlag());
		}
		
		@Test
		public void consrtuctAndRemoveContainer_OperationsFltHandlingProxy() throws FinderException, SystemException, PersistenceException{
			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			ContainerPK containerPK = new ContainerPK();
			containerPK.setAssignmentPort("CDG");
			containerEntity.setContainerPK(containerPK);
			containerDetailsVO.setFlightSequenceNumber(1);
			containerDetailsVO.setContainerType("U");
			containerDetailsVOs.add(containerDetailsVO);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn("Y").when(spy).findSystemParameterValue(any(String.class));
			doNothing().when(operationsFltHandlingProxy).saveOperationalULDsInFlight(anyCollectionOf(UldInFlightVO.class));
			spy.unassignEmptyULDs(containerDetailsVOs);
			assertThat(spy.findSystemParameterValue(any(String.class)),is("Y"));
		}
		
		@Test
		public void consrtuctAndRemoveContainer_FlightSeqNumberLessThanZero() throws FinderException, SystemException, PersistenceException{
			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			ContainerPK containerPK = new ContainerPK();
			containerPK.setAssignmentPort("CDG");
			containerEntity.setContainerPK(containerPK);
			containerDetailsVO.setFlightSequenceNumber(-1);
			containerDetailsVO.setContainerType("B");
			containerDetailsVOs.add(containerDetailsVO);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn("N").when(spy).findSystemParameterValue(any(String.class));
			spy.unassignEmptyULDs(containerDetailsVOs);
			assertThat(spy.findSystemParameterValue(any(String.class)),is("N"));
		}
		
		@Test
		public void consrtuctAndRemoveContainer_ContainerTypeBulk() throws FinderException, SystemException, PersistenceException{
			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			ContainerPK containerPK = new ContainerPK();
			containerPK.setAssignmentPort("CDG");
			containerEntity.setContainerPK(containerPK);
			containerDetailsVO.setFlightSequenceNumber(1);
			containerDetailsVO.setContainerType("B");
			containerDetailsVOs.add(containerDetailsVO);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn("Y").when(spy).findSystemParameterValue(any(String.class));
			spy.unassignEmptyULDs(containerDetailsVOs);
			assertThat(containerDetailsVO.getContainerType(), is("B"));
		}
		
		@Test(expected=SystemException.class)
		public void consrtuctAndRemoveContainer_FinderException() throws FinderException, SystemException, PersistenceException{
			FinderException finderException = new FinderException();
			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVOs.add(containerDetailsVO);
			ContainerPK containerPK = new ContainerPK();
			containerPK.setAssignmentPort("CDG");
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doThrow(finderException).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			spy.unassignEmptyULDs(containerDetailsVOs);
		}
		
		@Test
		public void deleteEmptyContainer_ContainerTypeUld() throws SystemException, FinderException, PersistenceException{
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("U");
			containerDetailsVO.setFlightNumber("1000");
			containerDetailsVO.setFlightSequenceNumber(1);
			containerDetailsVO.setAssignedPort("CDG");
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(1).when(dao).findbulkcountInFlight(any(ContainerDetailsVO.class));
			spy.deleteEmptyContainer(containerDetailsVO);
			assertThat(containerDetailsVO.getContainerType(),is("U"));
		}
		
		@Test
		public void deleteEmptyContainer_ConatinerTypeBulk() throws SystemException, FinderException, PersistenceException{
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("B");
			containerDetailsVO.setFlightNumber("-1");
			containerDetailsVO.setFlightSequenceNumber(-1);
			containerDetailsVO.setAssignedPort("CDG");
			doReturn(uLDAtAirportEntity).when(PersistenceController.getEntityManager()).find(eq(ULDAtAirport.class), any(ULDAtAirportPK.class));
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(1).when(dao).findbulkcountInFlight(any(ContainerDetailsVO.class));
			spy.deleteEmptyContainer(containerDetailsVO);
			assertThat(containerDetailsVO.getContainerType(),is("B"));
		}
		
		@Test
		public void deleteEmptyContainer_bulkCountZero() throws SystemException, FinderException, PersistenceException{
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("B");
			containerDetailsVO.setFlightNumber("1000");
			containerDetailsVO.setFlightSequenceNumber(-1);
			containerDetailsVO.setAssignedPort("CDG");
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			doReturn(0).when(dao).findbulkcountInFlight(any(ContainerDetailsVO.class));
			spy.deleteEmptyContainer(containerDetailsVO);
			assertThat(dao.findbulkcountInFlight(any(ContainerDetailsVO.class)), is(0));
		}
		
		@Test
		public void deleteEmptyContainer_bulkCountOne() throws SystemException, FinderException, PersistenceException{
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("U");
			containerDetailsVO.setFlightNumber("1000");
			containerDetailsVO.setFlightSequenceNumber(-1);
			containerDetailsVO.setAssignedPort("CDG");
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(uLDAtAirportEntity).when(PersistenceController.getEntityManager()).find(eq(ULDAtAirport.class), any(ULDAtAirportPK.class));
			doReturn(1).when(dao).findbulkcountInFlight(any(ContainerDetailsVO.class));
			spy.deleteEmptyContainer(containerDetailsVO);
			assertThat(dao.findbulkcountInFlight(any(ContainerDetailsVO.class)), is(1));
		}
		
		@Test
		public void deleteEmptyContainerbulknotfound() throws SystemException, FinderException, PersistenceException{
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("U");
			containerDetailsVO.setFlightNumber("-1");
			containerDetailsVO.setFlightSequenceNumber(-1);
			containerDetailsVO.setAssignedPort("CDG");
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(1).when(dao).findbulkcountInFlight(any(ContainerDetailsVO.class));
			doReturn(null).when(PersistenceController.getEntityManager()).find(eq(ULDAtAirport.class), any(ULDAtAirportPK.class));
			spy.deleteEmptyContainer(containerDetailsVO);
		}
		
		@Test
		public void autoAcquitContainers_ContainerTypeUld() throws SystemException, PersistenceException, FinderException{
			Collection<ContainerDetailsVO> conatinerstoAcquit= new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("U");
			containerDetailsVO.setAquitULDFlag(true);
			conatinerstoAcquit.add(containerDetailsVO);
			containerEntity.setRetainFlag("N");
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			doReturn(true).when(spy).isULDIntegrationEnabled();
			spy.autoAcquitContainers(conatinerstoAcquit);
			assertThat(containerDetailsVO.getContainerType(), is("U"));
		}
		
		@Test
		public void autoAcquitContainers_ContainerTypeBulk() throws SystemException, PersistenceException, FinderException{
			Collection<ContainerDetailsVO> conatinerstoAcquit= new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("B");
			conatinerstoAcquit.add(containerDetailsVO);
			spy.autoAcquitContainers(conatinerstoAcquit);
			assertThat(containerDetailsVO.getContainerType(), is("B"));
		}
		
		@Test
		public void autoAcquitContainers_isULDIntegrationEnabledTrue() throws SystemException, PersistenceException, FinderException{
			Collection<ContainerDetailsVO> conatinerstoAcquit= new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("U");
			containerDetailsVO.setAquitULDFlag(true);
			conatinerstoAcquit.add(containerDetailsVO);
			containerEntity.setRetainFlag("N");
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn(null).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			doReturn(true).when(spy).isULDIntegrationEnabled();
			spy.autoAcquitContainers(conatinerstoAcquit);
			assertTrue(spy.isULDIntegrationEnabled());
		}
		
		@Test
		public void autoAcquitContainers_FinderExceptionForContainer() throws SystemException, FinderException, PersistenceException{
			Collection<ContainerDetailsVO> conatinerstoAcquit= new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("U");
			containerDetailsVO.setAquitULDFlag(false);
			conatinerstoAcquit.add(containerDetailsVO);
			containerEntity.setRetainFlag("Y");
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn(true).when(spy).isULDIntegrationEnabled();
			spy.autoAcquitContainers(conatinerstoAcquit);
		}
		
		@Test
		public void autoAcquitContainers_FinderExceptionForUldForSegment() throws SystemException, FinderException, PersistenceException{
			Collection<ContainerDetailsVO> conatinerstoAcquit= new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("U");
			containerDetailsVO.setAquitULDFlag(true);
			conatinerstoAcquit.add(containerDetailsVO);
			containerEntity.setRetainFlag("Y");
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
			doReturn(true).when(spy).isULDIntegrationEnabled();
			spy.autoAcquitContainers(conatinerstoAcquit);
		}
		
		@Test
		public void autoAcquitContainers_ULDDefaultsProxyException() throws ULDDefaultsProxyException,ProxyException, SystemException{
			Collection<ContainerDetailsVO> conatinerstoAcquit= new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerType("B");
			conatinerstoAcquit.add(containerDetailsVO);
			doReturn(true).when(spy).isULDIntegrationEnabled();
			doThrow(ULDDefaultsProxyException.class).when(uLDDefaultsProxy).updateULDForOperations(any(FlightDetailsVO.class));
			spy.autoAcquitContainers(conatinerstoAcquit);
		}
		
		@Test
		public void autoAcquitContainers_ConatinerstoAcquitCollectionNull() throws SystemException{
			Collection<ContainerDetailsVO> conatinerstoAcquit= new ArrayList<>();
			conatinerstoAcquit = null;
			spy.autoAcquitContainers(conatinerstoAcquit);
			assertNull(conatinerstoAcquit);
		}
		
		@Test
		public void autoAcquitContainers_conatinerstoAcquitSizeNull() throws SystemException{
			Collection<ContainerDetailsVO> conatinerstoAcquit= new ArrayList<>(0);
			spy.autoAcquitContainers(conatinerstoAcquit);
			assertThat(conatinerstoAcquit.size(),is(0));
	}
	
	@Test
	public void shouldInsertNewDamageDetailsWithDocumentOnDamageCapture() throws ProxyException, SystemException {
		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs = new ArrayList<DocumentRepositoryMasterVO>();

		when(documentRepositoryProxy.getDocumentsfromRepository(any(DocumentRepositoryFilterVO.class)))
				.thenReturn(documentRepositoryMasterVOs);

		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setCompanyCode("AV");
		mailbagVO.setMailbagId(MAILBAG_ID);
		MailAttachmentVO attachmentVO = new MailAttachmentVO();
		attachmentVO.setAttachmentOpFlag("I");
		attachmentVO.setFileName("test.png");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		attachments.add(attachmentVO);
		mailbagVO.setAttachments(attachments);

		spy.uploadDocumentsToRepository(mailbagVO);

	}

	@Test
	public void doThrowProxyExceptionWhileInvokingDocumentRepository() throws SystemException, ProxyException {

		when(documentRepositoryProxy.getDocumentsfromRepository(any(DocumentRepositoryFilterVO.class)))
				.thenThrow(ProxyException.class);

		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setCompanyCode("AV");
		mailbagVO.setMailbagId(MAILBAG_ID);
		MailAttachmentVO attachmentVO = new MailAttachmentVO();
		attachmentVO.setAttachmentOpFlag("I");
		attachmentVO.setFileName("test.png");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		attachments.add(attachmentVO);
		mailbagVO.setAttachments(attachments);

		spy.uploadDocumentsToRepository(mailbagVO);

	}

	

	










	@Test
	public void shouldInsertDamageDetailsWhenDocumentRepositoryMasterVOsIsNotEmpty()
			throws ProxyException, SystemException {

		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs = new ArrayList<DocumentRepositoryMasterVO>();
		DocumentRepositoryAttachmentVO document = new DocumentRepositoryAttachmentVO();
		document.setFileName("TEST");
		List<DocumentRepositoryAttachmentVO> docList = new ArrayList<DocumentRepositoryAttachmentVO>();
		docList.add(document);
		DocumentRepositoryMasterVO docMaster = new DocumentRepositoryAttachmentVO();
		docMaster.setAttachments(docList);
		documentRepositoryMasterVOs.add(docMaster);
		when(documentRepositoryProxy.getDocumentsfromRepository(any(DocumentRepositoryFilterVO.class)))
				.thenReturn(documentRepositoryMasterVOs);

		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setCompanyCode("AV");
		mailbagVO.setMailbagId(MAILBAG_ID);
		MailAttachmentVO attachmentVO = new MailAttachmentVO();
		attachmentVO.setAttachmentOpFlag("I");
		attachmentVO.setFileName("test.png");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		attachments.add(attachmentVO);
		mailbagVO.setAttachments(attachments);

		spy.uploadDocumentsToRepository(mailbagVO);

	}

	@Test
	public void shouldNotUpdateDamageDetailsWhenOperationsFlagIsNotInsert() throws ProxyException, SystemException {

		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs = new ArrayList<DocumentRepositoryMasterVO>();
		DocumentRepositoryAttachmentVO document = new DocumentRepositoryAttachmentVO();
		document.setFileName("TEST");
		List<DocumentRepositoryAttachmentVO> docList = new ArrayList<DocumentRepositoryAttachmentVO>();
		docList.add(document);
		DocumentRepositoryMasterVO docMaster = new DocumentRepositoryAttachmentVO();
		docMaster.setAttachments(docList);
		documentRepositoryMasterVOs.add(docMaster);
		when(documentRepositoryProxy.getDocumentsfromRepository(any(DocumentRepositoryFilterVO.class)))
				.thenReturn(documentRepositoryMasterVOs);

		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setCompanyCode("AV");
		mailbagVO.setMailbagId(MAILBAG_ID);
		MailAttachmentVO attachmentVO = new MailAttachmentVO();
		attachmentVO.setAttachmentOpFlag("");
		attachmentVO.setFileName("test.png");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		attachments.add(attachmentVO);
		mailbagVO.setAttachments(attachments);

		spy.uploadDocumentsToRepository(mailbagVO);
		
		

	}
	
	@Test
	public void shouldInvokeDao_When_FindCartIds_IsInvoked() throws Exception{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		spy.findCartIds(any(ConsignmentFilterVO.class));
		verify(dao, times(1)).findCartIdsMailbags(any(ConsignmentFilterVO.class));
	}
	@Test
	public void shouldInvokeDao_When_FindContainerJourneyId_IsInvoked() throws Exception{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		spy.findContainerJourneyID(any(ConsignmentFilterVO.class));
		verify(dao, times(1)).findContainerJourneyID(any(ConsignmentFilterVO.class));
	}	

	@Test
	public void findAssignFlightAuditDetails_IsInvoked() throws BusinessDelegateException, SystemException, PersistenceException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		MailAuditFilterVO mailAuditFilterVO = new MailAuditFilterVO();
		spy.findAssignFlightAuditDetails(mailAuditFilterVO);
	}
	
	@Test
	public void closeInboundFlight_IsInvoked() throws ULDDefaultsProxyException, SystemException, FinderException, PersistenceException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setCarrierCode("AV");
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(null).when(spy).findArrivalDetailsForReleasingMails(operationalFlightVO);
		doReturn(false).when(spy).isULDIntegrationEnabled();
		spy.closeInboundFlight(operationalFlightVO);
	}
	
	@Test
	public void closeInboundFlight_when_containers_are_returned_for_audit() throws PersistenceException, SystemException, FinderException, ULDDefaultsProxyException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setCarrierCode("AV");
		Collection<ContainerDetailsVO> containerDetailsVO = new ArrayList<>();
		ContainerDetailsVO containerdetailsvo = new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVO = new ArrayList<>();
		MailbagVO mailbagvo = new MailbagVO();
		mailbagvo.setArrivedFlag("Y");
		mailbagvo.setMailbagId(MAILBAG_ID);
		mailbagVO.add(mailbagvo);
		containerdetailsvo.setMailDetails(mailbagVO);
		containerdetailsvo.setContainerNumber("AKE11678AV");
		containerDetailsVO.add(containerdetailsvo);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerDetailsVO).when(spy).findArrivalDetailsForReleasingMails(operationalFlightVO);
		doReturn(false).when(spy).isULDIntegrationEnabled();
		spy.closeInboundFlight(operationalFlightVO);
	}
	
	@Test
	public void closeInboundFlight_when_containers_are_returned_for_audit_without_mailbag_info() throws PersistenceException, SystemException, FinderException, ULDDefaultsProxyException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setCarrierCode("AV");
		Collection<ContainerDetailsVO> containerDetailsVO = new ArrayList<>();
		ContainerDetailsVO containerdetailsvo = new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVO = new ArrayList<>();
		MailbagVO mailbagvo = new MailbagVO();
		mailbagvo.setArrivedFlag("N");
		mailbagVO.add(mailbagvo);
		containerdetailsvo.setMailDetails(mailbagVO);
		containerdetailsvo.setContainerNumber("AKE11678AV");
		containerDetailsVO.add(containerdetailsvo);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerDetailsVO).when(spy).findArrivalDetailsForReleasingMails(operationalFlightVO);
		doReturn(false).when(spy).isULDIntegrationEnabled();
		spy.closeInboundFlight(operationalFlightVO);
	}
	
	@Test
	public void closeInboundFlight_when_containers_are_returned_for_audit_without_mailbagID() throws PersistenceException, SystemException, FinderException, ULDDefaultsProxyException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setCarrierCode("AV");
		Collection<ContainerDetailsVO> containerDetailsVO = new ArrayList<>();
		ContainerDetailsVO containerdetailsvo = new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVO = new ArrayList<>();
		MailbagVO mailbagvo = new MailbagVO();
		mailbagvo.setArrivedFlag("Y");
		mailbagvo.setMailbagId(null);
		mailbagVO.add(mailbagvo);
		containerdetailsvo.setMailDetails(mailbagVO);
		containerdetailsvo.setContainerNumber("AKE11678AV");
		containerDetailsVO.add(containerdetailsvo);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerDetailsVO).when(spy).findArrivalDetailsForReleasingMails(operationalFlightVO);
		doReturn(false).when(spy).isULDIntegrationEnabled();
		spy.closeInboundFlight(operationalFlightVO);
	}
	
	@Test
	public void reopenInboundFlights_IsInvoked() throws ULDDefaultsProxyException, SystemException, FinderException, PersistenceException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<OperationalFlightVO> operationalFlightVOs = new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVOs.add(operationalFlightVO);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(null).when(spy).findArrivalDetailsForReleasingMails(operationalFlightVO);
		spy.reopenInboundFlights(operationalFlightVOs);
	}
	
	@Test
	public void reopenInboundFlights_when_containers_are_returned_for_audit() throws PersistenceException, SystemException, FinderException, ULDDefaultsProxyException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<OperationalFlightVO> operationalFlightVOs = new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVOs.add(operationalFlightVO);
		Collection<ContainerDetailsVO> containerDetailsVO = new ArrayList<>();
		ContainerDetailsVO containerdetailsvo = new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVO = new ArrayList<>();
		MailbagVO mailbagvo = new MailbagVO();
		mailbagvo.setArrivedFlag("Y");
		mailbagvo.setMailbagId(MAILBAG_ID);
		mailbagVO.add(mailbagvo);
		containerdetailsvo.setMailDetails(mailbagVO);
		containerdetailsvo.setContainerNumber("AKE11678AV");
		containerDetailsVO.add(containerdetailsvo);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerDetailsVO).when(spy).findArrivalDetailsForReleasingMails(operationalFlightVO);
		spy.reopenInboundFlights(operationalFlightVOs);
	}
	
	@Test
	public void reopenInboundFlights_when_containers_are_returned_for_audit_without_mailbag_info() throws PersistenceException, SystemException, FinderException, ULDDefaultsProxyException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<OperationalFlightVO> operationalFlightVOs = new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVOs.add(operationalFlightVO);
		Collection<ContainerDetailsVO> containerDetailsVO = new ArrayList<>();
		ContainerDetailsVO containerdetailsvo = new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVO = new ArrayList<>();
		MailbagVO mailbagvo = new MailbagVO();
		mailbagvo.setArrivedFlag("N");
		mailbagVO.add(mailbagvo);
		containerdetailsvo.setMailDetails(mailbagVO);
		containerdetailsvo.setContainerNumber("AKE11678AV");
		containerDetailsVO.add(containerdetailsvo);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerDetailsVO).when(spy).findArrivalDetailsForReleasingMails(operationalFlightVO);
		spy.reopenInboundFlights(operationalFlightVOs);
	}
	
	@Test
	public void reopenInboundFlights_when_containers_are_returned_for_audit_without_mailbagID() throws PersistenceException, SystemException, FinderException, ULDDefaultsProxyException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<OperationalFlightVO> operationalFlightVOs = new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVOs.add(operationalFlightVO);
		Collection<ContainerDetailsVO> containerDetailsVO = new ArrayList<>();
		ContainerDetailsVO containerdetailsvo = new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVO = new ArrayList<>();
		MailbagVO mailbagvo = new MailbagVO();
		mailbagvo.setArrivedFlag("Y");
		mailbagvo.setMailbagId(null);
		mailbagVO.add(mailbagvo);
		containerdetailsvo.setMailDetails(mailbagVO);
		containerdetailsvo.setContainerNumber("AKE11678AV");
		containerDetailsVO.add(containerdetailsvo);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerDetailsVO).when(spy).findArrivalDetailsForReleasingMails(operationalFlightVO);
		spy.reopenInboundFlights(operationalFlightVOs);
	}
	
	@Test
	public void reopenFlight_isInvoked() throws SystemException, FinderException{
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("O");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		spy.reopenFlight(operationalFlightVO);
	}
	
	@Test
	public void reopenFlight_with_ExportClosingFlag() throws SystemException, FinderException, PersistenceException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("C");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(null).when(spy).findULDsInAssignedFlight(operationalFlightVO);
		spy.reopenFlight(operationalFlightVO);
	}
	
	@Test
	public void reopenFlight_with_ContainerDetails() throws SystemException, FinderException, PersistenceException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("C");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerVOs).when(spy).findULDsInAssignedFlight(operationalFlightVO);
		spy.reopenFlight(operationalFlightVO);
	}
	
	@Test
	public void triggerEmailForPureTransferContainers_Success_Test()
			throws SystemException, FinderException, PersistenceException {

		Collection<OperationalFlightVO> operationalFlightVOs = new ArrayList<>();
		Collection<ContainerDetailsVO> containterDetails = new ArrayList<>();
		Collection<MailAcceptanceVO> mailAcceptanceVOs = new ArrayList<>();

		OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
		operationalFlightVo.setCompanyCode("IBS");
		operationalFlightVo.setCarrierId(1134);
		operationalFlightVo.setFlightNumber("3101");
		operationalFlightVo.setPol("CDG");
		operationalFlightVo.setPou("SIN");

		operationalFlightVOs.add(operationalFlightVo);
	
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();

		mailAcceptanceVO.setCompanyCode("IBS");
		mailAcceptanceVO.setCarrierId(1134);
		mailAcceptanceVO.setFlightNumber("3101");
		mailAcceptanceVO.setFlightSequenceNumber(1);
		mailAcceptanceVO.setFlightDestination("CDG");

		ContainerDetailsVO containerDetailsVo = new ContainerDetailsVO();
		containerDetailsVo.setCompanyCode("IBS");
		containerDetailsVo.setFlightSequenceNumber(1);
		containerDetailsVo.setCarrierId(1134);
		containerDetailsVo.setContainerNumber("AKE12457AV");
		containerDetailsVo.setContainerType("PURE");

		ContainerDetailsVO containerDetailsVos = new ContainerDetailsVO();
		containerDetailsVos.setCompanyCode("IBS");
		containerDetailsVos.setFlightSequenceNumber(1);
		containerDetailsVos.setCarrierId(1134);
		containerDetailsVos.setContainerNumber("AKE12458AV");
		containerDetailsVos.setContainerType("PURE");

		containterDetails.add(containerDetailsVo);
		containterDetails.add(containerDetailsVos);
		mailAcceptanceVOs.add(mailAcceptanceVO);

		mailAcceptanceVO.setContainerDetails(containterDetails);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);

		doReturn(mailAcceptanceVOs).when(dao).findContainerVOs(any(MailAcceptanceVO.class));

		spy.triggerEmailForPureTransferContainers(operationalFlightVOs);
	}

	@Test(expected = SystemException.class)
	public void triggerEmailForPureTransferContainers_Exception_Test()
			throws SystemException, FinderException, PersistenceException {

		Collection<OperationalFlightVO> operationalFlightVOs = new ArrayList<>();
		Collection<ContainerDetailsVO> containterDetails = null;

		OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
		operationalFlightVo.setCompanyCode("IBS");
		operationalFlightVo.setCarrierId(1134);
		operationalFlightVo.setFlightNumber("3101");
		operationalFlightVo.setPol("CDG");
		operationalFlightVo.setPou("SIN");


		operationalFlightVOs.add(operationalFlightVo);
	
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();

		mailAcceptanceVO.setCompanyCode("IBS");
		mailAcceptanceVO.setCarrierId(1134);
		mailAcceptanceVO.setFlightNumber("3101");
		mailAcceptanceVO.setFlightSequenceNumber(1);
		mailAcceptanceVO.setFlightDestination("CDG");

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);

		doThrow(PersistenceException.class).when(dao).findContainerVOs(any(MailAcceptanceVO.class));

		spy.triggerEmailForPureTransferContainers(operationalFlightVOs);
	}

	

	@Test
	public void triggerEmailForPureTransferContainers_ContainerDetails_Empty_Test()
			throws SystemException, FinderException, PersistenceException {

		Collection<OperationalFlightVO> operationalFlightVOs = new ArrayList<>();
		Collection<ContainerDetailsVO> containterDetails = new ArrayList<>();
		Collection<MailAcceptanceVO> mailAcceptanceVOs = new ArrayList<>();

		OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
		operationalFlightVo.setCompanyCode("IBS");
		operationalFlightVo.setCarrierId(1134);
		operationalFlightVo.setFlightNumber("3101");
		operationalFlightVo.setPol("CDG");
		operationalFlightVo.setPou("SIN");

		operationalFlightVOs.add(operationalFlightVo);
		
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();

		mailAcceptanceVO.setCompanyCode("IBS");
		mailAcceptanceVO.setCarrierId(1134);
		mailAcceptanceVO.setFlightNumber("3101");
		mailAcceptanceVO.setFlightSequenceNumber(1);
		mailAcceptanceVO.setFlightDestination("CDG");

		mailAcceptanceVO.setContainerDetails(containterDetails);
		mailAcceptanceVOs.add(mailAcceptanceVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);

		doReturn(mailAcceptanceVOs).when(dao).findContainerVOs(any(MailAcceptanceVO.class));

		spy.triggerEmailForPureTransferContainers(operationalFlightVOs);
	}
	private ConsignmentScreeningVO setUpConsignmentScreeningVO() {
		ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setCompanyCode("AV");
		consignmentScreeningVO.setSerialNumber(11);
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setConsignmentNumber("CDG10782d");
		consignmentScreeningVO.setConsignmentSequenceNumber(2);
		consignmentScreeningVO.setSourceIndicator("IFSUM");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		return consignmentScreeningVO;
	}
	@Test
	public void saveSecurityDetails_IsInvoked() throws SystemException, BusinessException {
		ConsignmentScreeningVO consignmentScreeningVO = setUpConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		spy.saveSecurityDetails(consignmentScreeningVOs);
		verify(saveSecurityDetailsFeature, times(1)).execute(any(ConsignmentScreeningVO.class));
	}
	@Test
	public void saveSecurityDetails_WithEmptyConsignmentScreeningVO() throws SystemException, BusinessException {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		spy.saveSecurityDetails(consignmentScreeningVOs);
	}
	@Test
	public void saveSecurityDetails_WithNoConsignmentScreeningVO() throws SystemException, BusinessException {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = null;
		spy.saveSecurityDetails(consignmentScreeningVOs);
	}
	@Test
	public void saveSecurityDetails_ThrowException() throws SystemException, BusinessException {
		ConsignmentScreeningVO consignmentScreeningVO = setUpConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		BusinessException businessException = new BusinessException() {
		};
		doThrow(businessException).when(saveSecurityDetailsFeature).execute(consignmentScreeningVO);
		spy.saveSecurityDetails(consignmentScreeningVOs);
	}
//a-10383
	
	@Test
	public void mailOperationsTransferTransaction_success_test()throws SystemException
	{
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mail.operation.transferoutinonetransaction", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		String sysparfunctionpoints="Y";
		systemParameterMap.put("mail.operation.transferoutinonetransaction", "Y");

		spy.mailOperationsTransferTransaction();
	}
	@Test(expected = SystemException.class)
	public void mailOperationsTransferTransaction_catch_test()throws SystemException
	{
		HashMap<String, String> systemParameterMap = new HashMap<>();
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(MAIL_OPERATIONS_TRANSFER_TRANSACTION);
		systemParameterMap.put("mail.operation.transferoutinonetransaction", "Y");
		doThrow(new SystemException("error",
				"error")).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		spy.mailOperationsTransferTransaction();
		
	}
	
	@Test(expected = SystemException.class)
	public void mailOperationsTransferTransaction_systemParameterMap_null_test()throws SystemException
	{
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap=null;
		doThrow(new SystemException("error",
				"error")).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		systemParameterMap=null;
		
		spy.mailOperationsTransferTransaction();
	}
	
	@Test
	public void mailOperationsTransferTransaction_sysparfunpoint_test()throws SystemException
	{
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mail.operation.transferoutinonetransaction", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		String sysparfunctionpoints="Y";
		spy.mailOperationsTransferTransaction();
	}
	@Test
	public void mailOperationsTransferTransaction_sysparfunpoint_failure_test()throws SystemException
	{
		
		String sysparfunctionpoints=null;
		spy.mailOperationsTransferTransaction();
	}
	
	@Test
	public void mailOperationsTransferTransaction_sysparfunpoint_failure2_test()throws SystemException
	{
		
		String sysparfunctionpoints="N";
		spy.mailOperationsTransferTransaction();
	}
	
		
	@Test
	public void listmailbagSecurityDetails_Test() throws SystemException, FinderException, PersistenceException {

		MailScreeningFilterVO mailScreeningFilterVo = new MailScreeningFilterVO();
		MailbagVO mailbagVO = new MailbagVO();

		mailScreeningFilterVo.setMailBagId(MAILBAG_ID);
		mailScreeningFilterVo.setCompanyCode(COMPANY_CODE);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(mailScreeningFilterVo);

		spy.listmailbagSecurityDetails(mailScreeningFilterVo);

	}
		
	@Test
	public void listmailbagSecurityDetails_Exception_Test() throws SystemException, PersistenceException {

		MailScreeningFilterVO mailScreeningFilterVo = new MailScreeningFilterVO();
		mailScreeningFilterVo.setMailBagId(MAILBAG_ID);
		mailScreeningFilterVo.setCompanyCode(COMPANY_CODE);
		SystemException systemException = new SystemException("system exception");

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).listmailbagSecurityDetails(mailScreeningFilterVo);

		spy.listmailbagSecurityDetails(mailScreeningFilterVo);

	}
	@Test
	public void findAgentFromUpucodeSuccessCase()
			throws SystemException, PersistenceException {
		String agentCode="HQMAIL";
		String cmpCode="AV";
		String upuCode="USA";		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(agentCode).when(dao).findAgentCodeFromUpuCode(cmpCode,upuCode);
		spy.findAgentFromUpucode(cmpCode, upuCode);
	}  
	@Test
	public void findAgentFromUpucodeWhenUpucodeIsNull()
			throws SystemException, PersistenceException {
		String cmpCode="AV";
		String upuCode=null;		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(null).when(dao).findAgentCodeFromUpuCode(cmpCode,upuCode);
		spy.findAgentFromUpucode(cmpCode, upuCode);
	}
	@Test
	public void saveCarditMessageTest() throws SystemException, BusinessException, PersistenceException,
			IllegalAccessException, InvocationTargetException, FinderException {
		Collection<CarditVO> carditMessages=new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO=new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("AV");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached=MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		CarditVO cdtVO=new CarditVO();
		cdtVO.setSenderId("US101");
		cdtVO.setCarditKey(CARDITKEY);
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(cdtVO).when(dao).findCarditDetailsForResdit(ediInterchangeVO.getCompanyCode(),carditVO.getConsignmentNumber());
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		CarditPawbDetailsVO carditPawbDetailVO = new CarditPawbDetailsVO();
		carditPawbDetailVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailVO.setConsignmentDestinationAirport("DXB");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailVO);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DFW");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DFW", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("AV");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
		detailsVO1.setGroupedEntity("US");
		detailVOs.add(detailsVO1);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(true).when(mailControllerMock).findPawbCountryValidation(carditVO, null);
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void saveCarditMessageCarditPawbDetailsVOIsNullTest() throws SystemException, BusinessException,
			PersistenceException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages=new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		carditVO.setCarditPawbDetailsVO(null);
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO=new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("AV");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached=MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		CarditVO cdtVO=new CarditVO();
		cdtVO.setSenderId("US101");
		cdtVO.setCarditKey(CARDITKEY);
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(cdtVO).when(dao).findCarditDetailsForResdit(ediInterchangeVO.getCompanyCode(),carditVO.getConsignmentNumber());
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void saveCarditMessagePostalAdministartionCheckFalseTest() throws SystemException, BusinessException,
			PersistenceException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages=new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		carditMessages.add(carditVO);
		carditVO.setSenderId("FR102");
		EDIInterchangeVO ediInterchangeVO=new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("AV");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached=MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		CarditVO cdtVO=new CarditVO();
		cdtVO.setSenderId("FR102");
		cdtVO.setCarditKey("ABCCARGO03");
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(cdtVO).when(dao).findCarditDetailsForResdit(ediInterchangeVO.getCompanyCode(),carditVO.getConsignmentNumber());
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		postalAdministrationVO.setPostalAdministrationDetailsVOs(new HashMap<>());
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void saveCarditMessagePostalAdministartionCheckFalseNotPAWBParamTest() throws SystemException,
			BusinessException, PersistenceException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages=new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO=new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("AV");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached=MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		CarditVO cdtVO=new CarditVO();
		cdtVO.setSenderId("US101");
		cdtVO.setCarditKey(CARDITKEY);
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(cdtVO).when(dao).findCarditDetailsForResdit(ediInterchangeVO.getCompanyCode(),carditVO.getConsignmentNumber());
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB1");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void saveCarditMessagePostalAdministartionCheckFalsePAWBParamValNoTest() throws SystemException,
			BusinessException, PersistenceException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages=new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO=new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("AV");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached=MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		CarditVO cdtVO=new CarditVO();
		cdtVO.setSenderId("US101");
		cdtVO.setCarditKey(CARDITKEY);
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(cdtVO).when(dao).findCarditDetailsForResdit(ediInterchangeVO.getCompanyCode(),carditVO.getConsignmentNumber());
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("NO");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void saveCarditMessagePostalAdministartionCheckFalse1Test() throws SystemException, BusinessException,
			PersistenceException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages=new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO=new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("AV");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached=MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		CarditVO cdtVO=new CarditVO();
		cdtVO.setSenderId("US101");
		cdtVO.setCarditKey(CARDITKEY);
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(cdtVO).when(dao).findCarditDetailsForResdit(ediInterchangeVO.getCompanyCode(),carditVO.getConsignmentNumber());
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		spy.saveCarditMessages(ediInterchangeVO);
	}
	private CarditVO setCarditVO() {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber("12345678");
		carditPawbDetailsVO.setConsignmentDestination("FRPART");
		carditPawbDetailsVO.setConsignmentOrigin("AEDXBT");
		carditPawbDetailsVO.setShipperCode("JPA");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("8");
		carditVO.setCompanyCode("AV");
		carditVO.setConsignmentNumber("K4IRDPEFGIUA");
		carditVO.setSenderId("US101");
		carditVO.setStationCode("CDG");
		Collection<CarditTotalVO> totalsInformation=new ArrayList<>();
		CarditTotalVO carditTotalVO=new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		carditTotalVO.setWeightOfReceptacles(new Measure(null, 17.4));
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		return carditVO;
	}
	private ConsignmentDocumentVO setConsignmentDocumentVO(){
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> consignmentRoutingVOs = new ArrayList<>();
		RoutingInConsignmentVO routingvo = new RoutingInConsignmentVO();
		routingvo.setCompanyCode("AV");
		routingvo.setOnwardCarrierCode("AA");
		routingvo.setOnwardCarrierId(1154);
		routingvo.setPol("CDG");
		routingvo.setPou("DXB");
		routingvo.setPou("DXB");
		routingvo.setOnwardFlightNumber("7525");
		routingvo.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingvo.setTransportStageQualifier("20");
		consignmentRoutingVOs.add(routingvo);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsigmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		consignmentDocumentVO.setMailInConsignmentVOs(mailInConsigmentVOs);
		return consignmentDocumentVO;
	}  
@Test
	public void editscreeningDetails_test() throws SystemException, FinderException {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs= new ArrayList<>();
		ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
		ConsignmentScreeningDetailsPK pk = new ConsignmentScreeningDetailsPK();
		ConsignmentScreeningDetails screening = new ConsignmentScreeningDetails();
		
		consignmentScreeningVO.setCompanyCode("AV");
		consignmentScreeningVO.setSerialNumber(369);

		consignmentScreeningVO.setScreeningLocation("LOC");
		consignmentScreeningVO.setSecurityStatusParty("JOHN");
		consignmentScreeningVO.setScreeningMethodCode("EDS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("SM");
		consignmentScreeningVO.setResult("F");
		
		consignmentScreeningVO.setAgentID("1111");
		consignmentScreeningVO.setAgentType("RC");
		consignmentScreeningVO.setIsoCountryCode("AV");
		consignmentScreeningVO.setExpiryDate("2803");
		
		consignmentScreeningVO.setRemarks("remarks");
		consignmentScreeningVO.setScreeningRegulation("EXP-FR/RA/0");
		consignmentScreeningVO.setAdditionalSecurityInfo("SECURED");
		consignmentScreeningVO.setScreeningAuthority("EXP-AVS");
		consignmentScreeningVO.setSeScreeningAuthority("EXP-IN-DIP-2021");
		consignmentScreeningVO.setSeScreeningReasonCode("Mail");
		consignmentScreeningVO.setSeScreeningRegulation("IN2345");
		consignmentScreeningVO.setApplicableRegTransportDirection("1");
		consignmentScreeningVO.setApplicableRegBorderAgencyAuthority("CUS");
		consignmentScreeningVO.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		consignmentScreeningVO.setApplicableRegFlag("N");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		doReturn(ConsignmentScreeningDetailsEntity).when(PersistenceController.getEntityManager()).find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));
		spy.editscreeningDetails(consignmentScreeningVOs);
	}
	@Test
	public void editscreeningDetails_WithEmptyConsignmentScreeningVO() throws SystemException, BusinessException {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		spy.editscreeningDetails(consignmentScreeningVOs);
	}
	@Test
	public void editscreeningDetails_WithNoConsignmentScreeningVO() throws SystemException, BusinessException {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = null;
		spy.editscreeningDetails(consignmentScreeningVOs);
	}
	@Test
	public void editscreeningDetails_FinderException_test() throws SystemException, FinderException {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs= new ArrayList<>();
		ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
		ConsignmentScreeningDetailsPK pk = new ConsignmentScreeningDetailsPK();
		ConsignmentScreeningDetails screening = new ConsignmentScreeningDetails();
		
		consignmentScreeningVO.setCompanyCode("AV");
		consignmentScreeningVO.setSerialNumber(369);

		consignmentScreeningVO.setScreeningLocation("LOC");
		consignmentScreeningVO.setSecurityStatusParty("JOHN");
		consignmentScreeningVO.setScreeningMethodCode("EDS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("SM");
		consignmentScreeningVO.setResult("F");
		
		consignmentScreeningVO.setAgentID("1111");
		consignmentScreeningVO.setAgentType("RC");
		consignmentScreeningVO.setIsoCountryCode("AV");
		consignmentScreeningVO.setExpiryDate("2803");
		
		consignmentScreeningVO.setRemarks("remarks");
		consignmentScreeningVO.setScreeningRegulation("EXP-FR/RA/0");
		consignmentScreeningVO.setAdditionalSecurityInfo("SECURED");
		consignmentScreeningVO.setScreeningAuthority("EXP-AVS");
		consignmentScreeningVO.setSeScreeningAuthority("EXP-IN-DIP-2021");
		consignmentScreeningVO.setSeScreeningReasonCode("Mail");
		consignmentScreeningVO.setSeScreeningRegulation("IN2345");
		consignmentScreeningVO.setApplicableRegTransportDirection("1");
		consignmentScreeningVO.setApplicableRegBorderAgencyAuthority("CUS");
		consignmentScreeningVO.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		consignmentScreeningVO.setApplicableRegFlag("N");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		doReturn(ConsignmentScreeningDetailsEntity).when(PersistenceController.getEntityManager()).find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));
		spy.editscreeningDetails(consignmentScreeningVOs);
	}
	

	@Test
	public void deletescreeningDetails_test() throws SystemException, FinderException {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs= new ArrayList<>();
		ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
		ConsignmentScreeningDetailsPK pk = new ConsignmentScreeningDetailsPK();

		consignmentScreeningVO.setCompanyCode("AV");
		consignmentScreeningVO.setSerialNumber(369);
		consignmentScreeningVOs.add(consignmentScreeningVO);

		doReturn(ConsignmentScreeningDetailsEntity).when(PersistenceController.getEntityManager()).find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));
		spy.deletescreeningDetails(consignmentScreeningVOs);
	}
	@Test
	public void deletescreeningDetails_WithEmptyConsignmentScreeningVO() throws SystemException, BusinessException {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		spy.deletescreeningDetails(consignmentScreeningVOs);
	}
	@Test
	public void deletescreeningDetails_WithNoConsignmentScreeningVO() throws SystemException, BusinessException {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = null;
		spy.deletescreeningDetails(consignmentScreeningVOs);
	}
	
	@Test
	public void deletescreeningDetails_FinderException_test() throws SystemException, FinderException {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs= new ArrayList<>();
		ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
		ConsignmentScreeningDetailsPK pk = new ConsignmentScreeningDetailsPK();

		consignmentScreeningVO.setCompanyCode("AV");
		consignmentScreeningVO.setSerialNumber(369);
		consignmentScreeningVOs.add(consignmentScreeningVO);
		doReturn(ConsignmentScreeningDetailsEntity).when(PersistenceController.getEntityManager()).find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));
		spy.deletescreeningDetails(consignmentScreeningVOs);
	}
	@Test
	public void saveCarditMessageThrowsBusinessExceptionTest() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
	 Collection<CarditVO> carditMessages = new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("IFCSUM");
		receptacleVO.setMailSubClassCode("ert");
		receptacleVO.setReceptacleWeight(new Measure(UnitConstants.WEIGHT, 5));
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("47");
		carditVO.setMessageTypeId("IFCSUM");
		carditVO.getCarditPawbDetailsVO().setConsignmentOriginAirport("DFW");
		carditVO.getCarditPawbDetailsVO().setConsignmentDestinationAirport("DXB");
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
		Mailbag mailbag = new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		mailbag.setDespatchDate(Calendar.getInstance());
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
				carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		cardit.setCarditPK(carditPK);
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailSequenceNumber(2345);
		mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setPol("DFW");
		routingInConsignmentVO.setPou("FRA");
		routingVOs.add(routingInConsignmentVO);
		RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
		routingInConsignmentVO1.setPol("FRA");
		routingInConsignmentVO1.setPou("SIN");
		routingVOs.add(routingInConsignmentVO1);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
		consignmentDocumentVO.setAirportCode("CDG");
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
		CarditTotalVO carditTotalVO = new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DFW");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DFW");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setPoaCode("FR001");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		ArrayList<String> systemParameter1 = new ArrayList<>();
		systemParameter1.add("mail.operations.pawbcountryvalidation");
		HashMap<String, String> systemParameterMap1 = new HashMap<>();
		systemParameterMap1.put("mail.operations.pawbcountryvalidation", "EU");
		doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameter1);
		doReturn("EU").when(mailControllerMock).findSystemParameterValue("mail.operations.pawbcountryvalidation");
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
		Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
		CarditTransportationVO transportataion = new CarditTransportationVO();
		transportataion.setFlightNumber("FLT124");
		transportataion.setArrivalPort("FRA");
		transportataion.setDeparturePort("DFW");
		transportationVOs.add(transportataion);
		carditVO.setTransportInformation(transportationVOs);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DFW");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DFW", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("AV");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
		detailsVO1.setGroupedEntity("NL");
		detailVOs.add(detailsVO1);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(true).when(mailControllerMock).findPawbCountryValidation(carditVO, consignmentDocumentVO);
		BusinessException businessException = new BusinessException() {
		};
		 ErrorVO errorVO = new ErrorVO("mailtracking.defaults.err.invalidpawbstock");
		 errorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
		businessException.addError(errorVO);
		doThrow(businessException).when(savePAWBDetailsFeature).execute(carditVO);
		spy.saveCarditMessages(ediInterchangeVO);
	}
 @Test
	public void saveCarditMessageThrowsSystemExceptionTest() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages = new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("IFCSUM");
		receptacleVO.setMailSubClassCode("ert");
		receptacleVO.setReceptacleWeight(new Measure(UnitConstants.WEIGHT, 5));
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("47");
		carditVO.setMessageTypeId("IFCSUM");
		carditVO.getCarditPawbDetailsVO().setConsignmentOriginAirport("DFW");
		carditVO.getCarditPawbDetailsVO().setConsignmentDestinationAirport("DXB");
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
		Mailbag mailbag = new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		mailbag.setDespatchDate(Calendar.getInstance());
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
				carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		cardit.setCarditPK(carditPK);
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailSequenceNumber(2345);
		mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setPol("DFW");
		routingInConsignmentVO.setPou("FRA");
		routingVOs.add(routingInConsignmentVO);
		RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
		routingInConsignmentVO1.setPol("FRA");
		routingInConsignmentVO1.setPou("SIN");
		routingVOs.add(routingInConsignmentVO1);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
		consignmentDocumentVO.setAirportCode("CDG");
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
		CarditTotalVO carditTotalVO = new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DFW");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DFW");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setPoaCode("FR001");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		ArrayList<String> systemParameter1 = new ArrayList<>();
		systemParameter1.add("mail.operations.pawbcountryvalidation");
		HashMap<String, String> systemParameterMap1 = new HashMap<>();
		systemParameterMap1.put("mail.operations.pawbcountryvalidation", "EU");
		doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameter1);
		doReturn("EU").when(mailControllerMock).findSystemParameterValue("mail.operations.pawbcountryvalidation");
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
		Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
		CarditTransportationVO transportataion = new CarditTransportationVO();
		transportataion.setFlightNumber("FLT124");
		transportataion.setArrivalPort("FRA");
		transportataion.setDeparturePort("DFW");
		transportationVOs.add(transportataion);
		carditVO.setTransportInformation(transportationVOs);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DFW");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DFW", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("AV");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
		detailsVO1.setGroupedEntity("NL");
		detailVOs.add(detailsVO1);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(true).when(mailControllerMock).findPawbCountryValidation(carditVO, consignmentDocumentVO);
		 SystemException systemException = new SystemException("system exception");
		doThrow(systemException).when(savePAWBDetailsFeature).execute(carditVO);
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void saveCarditMessageThrowsBusinessExceptionWithEmptyErrorVOTest() throws BusinessException,
			PersistenceException, SystemException, IllegalAccessException, InvocationTargetException, FinderException {
		Collection<CarditVO> carditMessages=new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO=new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached=MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		CarditVO cdtVO=new CarditVO();
		cdtVO.setSenderId("US101");
		cdtVO.setCarditKey(CARDITKEY);
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(cdtVO).when(dao).findCarditDetailsForResdit(ediInterchangeVO.getCompanyCode(),carditVO.getConsignmentNumber());
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode( any(String.class), any(String.class));
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailSequenceNumber(2345);
		mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setPol("DFW");
		routingInConsignmentVO.setPou("FRA");
		routingVOs.add(routingInConsignmentVO);
		RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
		routingInConsignmentVO1.setPol("FRA");
		routingInConsignmentVO1.setPou("SIN");
		routingVOs.add(routingInConsignmentVO1);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
		consignmentDocumentVO.setAirportCode("CDG");
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DFW");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DFW", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("AV");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
		detailsVO1.setGroupedEntity("NL");
		detailVOs.add(detailsVO1);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		CarditPawbDetailsVO carditPawbDetailVO = new CarditPawbDetailsVO();
		carditPawbDetailVO.setConsignmentOriginAirport("DFW");
		carditPawbDetailVO.setConsignmentDestinationAirport("FRA");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailVO);
		doReturn(true).when(mailControllerMock).findPawbCountryValidation(carditVO, consignmentDocumentVO);
		BusinessException businessException = new BusinessException() {
		};
		businessException.addErrors(new ArrayList<>());
		doThrow(businessException).when(savePAWBDetailsFeature).execute(carditVO);
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void sendSecurityScreenDetailsTest() throws PersistenceException, SystemException, ProxyException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setLegOrigin("FRA");
		flightValidationVo.setLegDestination("DXB");
		flightValidationVo.setSta(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		flightValidationVOs.add(flightValidationVo);
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		containerDetailsVO.setPou("DXB");
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		MailbagVO mailVO = new MailbagVO();
		mailVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailVO.setSecurityDetailsPresent(true);
		mailVO.setSecurityStatusCode("SPX");
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		//consignmentScreeningVO.setScreenDetailType("SM");
		consignmentScreeningVO.setScreeningLocation("FRA");
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doThrow(ProxyException.class).when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);
	}
	@Test
	public void sendSecurityScreenDetailsWithSMTest() throws PersistenceException, SystemException, ProxyException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setLegOrigin("FRA");
		flightValidationVo.setLegDestination("DXB");
		flightValidationVo.setSta(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		flightValidationVOs.add(flightValidationVo);
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		containerDetailsVO.setPou("DXB");
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		MailbagVO mailVO = new MailbagVO();
		mailVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailVO.setSecurityDetailsPresent(true);
		mailVO.setSecurityStatusCode("SPX");
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenDetailType("SM");
		consignmentScreeningVO.setScreeningLocation("FRA");
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doThrow(ProxyException.class).when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);

	}
	@Test
	public void sendSecurityScreenDetailsWithSMTestForAllStation() throws PersistenceException, SystemException, ProxyException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setLegOrigin("FRA");
		flightValidationVo.setLegDestination("DXB");
		flightValidationVo.setSta(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		flightValidationVOs.add(flightValidationVo);
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		containerDetailsVO.setPou("DXB");
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		MailbagVO mailVO = new MailbagVO();
		mailVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailVO.setSecurityDetailsPresent(true);
		mailVO.setSecurityStatusCode("SPX");
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenDetailType("SM");
		consignmentScreeningVO.setScreeningLocation("FRA");
		ConsignmentScreeningVO consignmentScreeningVO1=new ConsignmentScreeningVO();
		consignmentScreeningVO1.setScreenDetailType("CS");
		consignmentScreeningVO1.setAgentType("RI");
		ConsignmentScreeningVO consignmentScreeningVO2=new ConsignmentScreeningVO();
		consignmentScreeningVO2.setScreenDetailType("CS");
		consignmentScreeningVO2.setAgentType("RA");
		ConsignmentScreeningVO consignmentScreeningVO3=new ConsignmentScreeningVO();
		consignmentScreeningVO3.setScreenDetailType("AR");
		consignmentScreeningVO3.setAgentType("RI");
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		consignmentScreeningVOs.add(consignmentScreeningVO1);
		consignmentScreeningVOs.add(consignmentScreeningVO2);
		consignmentScreeningVOs.add(consignmentScreeningVO3);
		mailVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		String sysParValue="mail.operations.sendallstationsscreeningstatus";
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(sysParValue, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doThrow(ProxyException.class).when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);
	}
	@Test
	public void sendSecurityScreenDetailsWithoutScreeningLocationTest() throws PersistenceException, SystemException, ProxyException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVO.setPou("DXB");
		containers.add(containerDetailsVO);
		MailbagVO mailVO = new MailbagVO();
		mailVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailVO.setSecurityDetailsPresent(true);
		mailVO.setSecurityStatusCode("SPX");
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doThrow(ProxyException.class).when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);

	}
	@Test
	public void sendSecurityScreenDetailsTestSecurityDetailsEmpty() throws PersistenceException, SystemException, ProxyException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		MailbagVO mailVO = new MailbagVO();
		mailVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doThrow(ProxyException.class).when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);

	}
	@Test
	public void sendSecurityScreenDetailsTestWithConsignmentScreeningDetails() throws PersistenceException, SystemException, ProxyException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setLegOrigin("FRA");
		flightValidationVo.setLegDestination("DXB");
		flightValidationVOs.add(flightValidationVo);
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVO.setPou("DFW");
		containers.add(containerDetailsVO);
		MailbagVO mailVO = new MailbagVO();
		mailVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailVO.setSecurityDetailsPresent(true);
		mailVO.setSecurityStatusCode("SPX");
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setScreenDetailType("CS");
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);

	}
	@Test
	public void sendSecurityScreenDetailsTestWithoutSecurityStatusCode() throws PersistenceException, SystemException, ProxyException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		MailbagVO mailVO = new MailbagVO();
		mailVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailVO.setSecurityDetailsPresent(true);
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);

	}
	@Test
	public void sendSecurityScreenDetailsWithoutConsignmentSecurityDetail() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		MailbagVO mailVO = new MailbagVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);

	}
	@Test
	public void sendSecurityScreenDetailsWithoutConsignmentSecurityDetailEmpty() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		MailbagVO mailVO = new MailbagVO();
		mailVO.setSecurityDetailsPresent(true);
		mailVO.setSecurityStatusCode("SPX");
		String sysParValue="mail.operations.sendallstationsscreeningstatus";
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(sysParValue, "Y");
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);
	}
	@Test
	public void sendSecurityScreenDetailsWithoutMailVO() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(null).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);

	}
	@Test()
	public void sendSecurityScreenDetailsWithEmptySecurityDetails() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		MailbagVO mailVO = new MailbagVO();
		mailVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailVO.setSecurityDetailsPresent(true);
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		mailVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);

	}
	@Test()
	public void sendSecurityScreenDetailsWithEmptyMailbagVO() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);

	}
	@Test()
	public void sendSecurityScreenDetailsWithNullMailbagVO() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		containerDetailsVO.setMailDetails(null);
		containers.add(containerDetailsVO);
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);

	}
	@Test()
	public void sendSecurityScreenDetailsWithEmptyContainerDetails() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.doSecurityAndScreeningValidations(operationalFlightVO,containerVOs,bulkContainers,flightValidationVOs);

	}
	/*@Test()
	public void sendSecurityScreenDetailsWithNullContainerDetails() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE0198LH");
		containerVOs.add(uldContainer);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(null).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.findContainerDetailsVOs(operationalFlightVO,containerVOs,bulkContainers);

	}*/
	@Test()
	public void sendSecurityScreenDetailsForEmptyContainers() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(null).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.findContainerDetailsVOs(operationalFlightVO,containerVOs,bulkContainers);

	}
	@Test()
	public void sendSecurityScreenDetailsForEmptyContainerDetailsVO() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);
	}
	@Test()
	public void sendSecurityScreenDetailsForNullContainerDetailsVO() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerDetailsVO> containers=null;
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		spy.sendSecurityScreeningDetails(containers,operationalFlightVO,flightValidationVOs);
	}
	@Test()
	public void sendSecurityScreenDetailsForNullContainers() throws PersistenceException, SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainers.add(bulkContainer);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(null).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		spy.findContainerDetailsVOs(operationalFlightVO,null,null);

	}
	@Test
	public void generateMailSecurityReport_Test()
			throws SystemException, FinderException, PersistenceException, ReportGenerationException , ProxyException{
		ReportSpec reportSpec = new ReportSpec();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailScreeningFilterVO mailScreeningFilterVo = new MailScreeningFilterVO();
		MailbagVO mailbagVO = new MailbagVO();		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		String pou = "DXB";
		reportSpec.addFilterValue(MAILBAG_ID);
		reportSpec.addFilterValue(COMPANY_CODE);
		mailScreeningFilterVo.setMailBagId(MAILBAG_ID);
		mailScreeningFilterVo.setCompanyCode(COMPANY_CODE);
		mailbagVO.setOrigin("CDG");
		mailbagVO.setDestination("FRA");
		mailbagVO.setMailbagId(MAILBAG_ID);
		mailbagVO.setMailSequenceNumber(834567);
		ConsignmentScreeningVO consignmentScreeningVo1 = new ConsignmentScreeningVO();
		consignmentScreeningVo1.setScreeningLocation("FRA");
		consignmentScreeningVo1.setScreeningMethodCode("EDS");
		consignmentScreeningVo1.setSecurityStatusParty("JESSI");
		ConsignmentScreeningVO consignmentScreeningVo2 = new ConsignmentScreeningVO();
		consignmentScreeningVo2.setScreeningLocation("FRA");
		consignmentScreeningVo2.setScreeningMethodCode("EDS");
		consignmentScreeningVo2.setSecurityStatusParty("JESSI");
		consignmentScreeningVOs.add(consignmentScreeningVo1);
		consignmentScreeningVOs.add(consignmentScreeningVo2);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(mailbagVO).when(mailOperationsProxy).fetchMailSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(pou).when(mailOperationsProxy).findRoutingDetails(any(String.class), any(long.class));
		mailbagVOs.add(mailbagVO);
		reportSpec.setData(mailbagVOs);
		spy.generateMailSecurityReport(reportSpec);

	}


		 @Test(expected = SystemException.class)
	public void generateMailSecurityReport_Exception()
			throws SystemException, FinderException, PersistenceException, ReportGenerationException , ProxyException{
		ReportSpec reportSpec = new ReportSpec();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailScreeningFilterVO mailScreeningFilterVo = new MailScreeningFilterVO();
		MailbagVO mailbagVO = new MailbagVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		reportSpec.addFilterValue(MAILBAG_ID);
		reportSpec.addFilterValue(COMPANY_CODE);
		mailScreeningFilterVo.setMailBagId(MAILBAG_ID);
		mailScreeningFilterVo.setCompanyCode(COMPANY_CODE);
		mailbagVO.setOrigin("CDG");
		mailbagVO.setDestination("FRA");
		mailbagVO.setMailbagId(MAILBAG_ID);
		mailbagVO.setMailSequenceNumber(834567);
		ConsignmentScreeningVO consignmentScreeningVo1 = new ConsignmentScreeningVO();
		consignmentScreeningVo1.setScreeningLocation("FRA");
		consignmentScreeningVo1.setScreeningMethodCode("EDS");
		consignmentScreeningVo1.setSecurityStatusParty("JESSI");
		ConsignmentScreeningVO consignmentScreeningVo2 = new ConsignmentScreeningVO();
		consignmentScreeningVo2.setScreeningLocation("FRA");
		consignmentScreeningVo2.setScreeningMethodCode("EDS");
		consignmentScreeningVo2.setSecurityStatusParty("JESSI");
		consignmentScreeningVOs.add(consignmentScreeningVo1);
		consignmentScreeningVOs.add(consignmentScreeningVo2);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doThrow(ProxyException.class).when(mailOperationsProxy).findRoutingDetails(any(String.class), any(long.class));
		doReturn(mailbagVO).when(mailOperationsProxy).fetchMailSecurityDetails(any(MailScreeningFilterVO.class));
		mailbagVOs.add(mailbagVO);
		reportSpec.setData(mailbagVOs);
		spy.generateMailSecurityReport(reportSpec);

	}
	@Test
	public void saveMailSecurityStatus_test() throws SystemException, FinderException {
		MailbagVO mailbagVO = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setMailSequenceNumber(456);
		mailbagVO.setSecurityStatusCode("SPX");
		  mailBagPK.setCompanyCode("IBS"); 
		  mailBagPK.setMailSequenceNumber(456);
		  mailBag.setMailbagPK(mailBagPK);
		doReturn(MailbagEntity).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
		spy.saveMailSecurityStatus(mailbagVO);
	}
	@Test
	public void saveMailSecurityStatusNull_test() throws SystemException, FinderException {
		MailbagVO mailbagVO = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setMailSequenceNumber(456);
		mailbagVO.setSecurityStatusCode("SPX");
		  mailBagPK.setCompanyCode("IBS"); 
		  mailBagPK.setMailSequenceNumber(456);
		  mailBag.setMailbagPK(mailBagPK);
		doReturn(null).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
		spy.saveMailSecurityStatus(mailbagVO);
	}
	@Test
	public void saveMailSecurityStatus_FinderException_test() throws SystemException, FinderException {
		MailbagVO mailbagVO = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setMailSequenceNumber(456);
		mailbagVO.setSecurityStatusCode("SPX");
		  mailBagPK.setCompanyCode("IBS"); 
		  mailBagPK.setMailSequenceNumber(456);
		  mailBag.setMailbagPK(mailBagPK);
		doReturn(null).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
		FinderException findException = new FinderException("finder exception");
		doThrow(findException).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
		spy.saveMailSecurityStatus(mailbagVO);
	}   
    @Test
	public void saveCarditMessage_WithREceptacleVO() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages=new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("IFSUM");
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("1");
		carditVO.setMessageTypeId("IFCSUM");
		Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
		CarditTransportationVO transportataion = new CarditTransportationVO();
		transportataion.setFlightNumber("FLT124");
		transportataion.setArrivalPort("FRA");
		transportataion.setDeparturePort("DFW");
		transportationVOs.add(transportataion);
		carditVO.setTransportInformation(transportationVOs);
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO=new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached=MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber =987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		Mailbag mailbag=new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setPol("DFW");
		routingInConsignmentVO.setPou("FRA");
		routingVOs.add(routingInConsignmentVO);
		RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
		routingInConsignmentVO1.setPol("FRA");
		routingInConsignmentVO1.setPou("SIN");
		routingVOs.add(routingInConsignmentVO1);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		spy.saveCarditMessages(ediInterchangeVO);

	}
	@Test
	public void findAWBAttachedMailbags_sucess() throws SystemException, PersistenceException {
		Collection<MailbagVO>mailbags = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setWeight( new Measure(UnitConstants.WEIGHT, 5));
		mailbags.add(mailbagVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		String consignmentDocNum ="CDG0245";
		spy.findAWBAttachedMailbags(mailbagVO, consignmentDocNum);
		
	}
	
	@Test
	public void saveCarditMessage_WithREceptacleVOForUpdate() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages = new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("IFCSUM");
		receptacleVO.setMailSubClassCode("ert");
		receptacleVO.setReceptacleWeight(new Measure(UnitConstants.WEIGHT, 5));
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("47");
		carditVO.setMessageTypeId("IFCSUM");
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
//		systemParameters.add("mailtracking.mra.triggerforimport");
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
		Mailbag mailbag = new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		mailbag.setDespatchDate(Calendar.getInstance());
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
				carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		cardit.setCarditPK(carditPK);
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailSequenceNumber(2345);
		mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
		Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
		CarditTransportationVO transportataion = new CarditTransportationVO();
		transportataion.setFlightNumber("FLT124");
		transportataion.setArrivalPort("FRA");
		transportataion.setDeparturePort("DFW");
		transportationVOs.add(transportataion);
		carditVO.setTransportInformation(transportationVOs);
		CarditTotalVO carditTotalVO = new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DFW");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setPoaCode("FR001");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void saveCarditMessage_WithREceptacleVOForCarditDom() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages = new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("CARDITDOM");
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("1");
		carditVO.setMessageTypeId("CARDITDOM");
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
		Mailbag mailbag = new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		CarditVO cdtVO = new CarditVO();
		cdtVO.setSenderId("US101");
		cdtVO.setCarditKey(CARDITKEY);
		doReturn(cdtVO).when(dao).findCarditDetailsForResdit(ediInterchangeVO.getCompanyCode(),
				carditVO.getConsignmentNumber());
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
				carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setPol("DFW");
		routingInConsignmentVO.setPou("FRA");
		routingVOs.add(routingInConsignmentVO);
		RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
		routingInConsignmentVO1.setPol("FRA");
		routingInConsignmentVO1.setPou("SIN");
		routingVOs.add(routingInConsignmentVO1);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
		CarditTransportationVO transportataion = new CarditTransportationVO();
		transportataion.setFlightNumber("FLT124");
		transportataion.setArrivalPort("FRA");
		transportataion.setDeparturePort("DFW");
		transportationVOs.add(transportataion);
		carditVO.setTransportInformation(transportationVOs);
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("AV");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
		detailsVO1.setGroupedEntity("NL");
		detailVOs.add(detailsVO1);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(true).when(mailControllerMock).findPawbCountryValidation(carditVO, consignmentDocumentVO);
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void saveCarditMessage_WithNoConsignmentDocumentVO() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages=new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("IFSUM");
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("1");
		carditVO.setMessageTypeId("IFCSUM");
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO=new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached=MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber =987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		Mailbag mailbag=new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		doReturn(null).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		spy.saveCarditMessages(ediInterchangeVO);

	}
	@Test
	public void saveCarditMessage_WithNoConsignmentDocumentOForCarditDom() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages = new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("CARDITDOM");
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("1");
		carditVO.setMessageTypeId("CARDITDOM");
		Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
		CarditTransportationVO transportataion = new CarditTransportationVO();
		transportataion.setFlightNumber("FLT124");
		transportataion.setArrivalPort("FRA");
		transportataion.setDeparturePort("DFW");
		transportationVOs.add(transportataion);
		carditVO.setTransportInformation(transportationVOs);
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
		Mailbag mailbag = new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		CarditVO cdtVO = new CarditVO();
		cdtVO.setSenderId("US101");
		cdtVO.setCarditKey(CARDITKEY);
		doReturn(cdtVO).when(dao).findCarditDetailsForResdit(ediInterchangeVO.getCompanyCode(),
				carditVO.getConsignmentNumber());
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
				carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		doReturn(null).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		spy.saveCarditMessages(ediInterchangeVO);
	}
	
	@Test
	public void saveCarditMessage_WithoutReceptacleVO() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages = new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("1");
		carditVO.setMessageTypeId("IFCSUM");
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
//		systemParameters.add("mailtracking.mra.triggerforimport");
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
		Mailbag mailbag = new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		mailbag.setDespatchDate(Calendar.getInstance());
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
				carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		cardit.setCarditPK(carditPK);
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailSequenceNumber(2345);
		mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setPol("DFW");
		routingInConsignmentVO.setPou("FRA");
		routingVOs.add(routingInConsignmentVO);
		RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
		routingInConsignmentVO1.setPol("FRA");
		routingInConsignmentVO1.setPou("SIN");
		routingVOs.add(routingInConsignmentVO1);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
		CarditTotalVO carditTotalVO = new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DFW");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setPoaCode("FR001");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void savePAWBDetailsFromCardit_throwFinderException() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages = new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("IFCSUM");
		receptacleVO.setMailSubClassCode("ert");
		receptacleVO.setReceptacleWeight(new Measure(UnitConstants.WEIGHT, 5));
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("47");
		carditVO.setMessageTypeId("IFCSUM");
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
		Mailbag mailbag = new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		mailbag.setDespatchDate(Calendar.getInstance());
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
				carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		cardit.setCarditPK(carditPK);
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailSequenceNumber(2345);
		mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
		CarditTotalVO carditTotalVO = new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DFW");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setPoaCode("FR001");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
		Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
		CarditTransportationVO transportataion = new CarditTransportationVO();
		transportataion.setFlightNumber("FLT124");
		transportataion.setArrivalPort("FRA");
		transportataion.setDeparturePort("DFW");
		transportationVOs.add(transportataion);
		carditVO.setTransportInformation(transportationVOs);
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void savePAWBDetailsFromCardit_WithoutOriginAndDestination() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages = new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("IFCSUM");
		receptacleVO.setMailSubClassCode("ert");
		receptacleVO.setReceptacleWeight(new Measure(UnitConstants.WEIGHT, 5));
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("47");
		carditVO.setMessageTypeId("IFCSUM");
		CarditPawbDetailsVO carditPawbDetailVO = new CarditPawbDetailsVO();
		carditVO.setCarditPawbDetailsVO(carditPawbDetailVO);
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
		Mailbag mailbag = new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		mailbag.setDespatchDate(Calendar.getInstance());
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
				carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		cardit.setCarditPK(carditPK);
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailSequenceNumber(2345);
		mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
		CarditTotalVO carditTotalVO = new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
		CarditTransportationVO transportataion = new CarditTransportationVO();
		transportataion.setFlightNumber("FLT124");
		transportataion.setArrivalPort("FRA");
		transportataion.setDeparturePort("DFW");
		transportationVOs.add(transportataion);
		carditVO.setTransportInformation(transportationVOs);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DFW");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setPoaCode("FR001");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void savePAWBDetailsFromCardit_WithoutOriginAndDestinationAirport() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages = new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("IFCSUM");
		receptacleVO.setMailSubClassCode("ert");
		receptacleVO.setReceptacleWeight(new Measure(UnitConstants.WEIGHT, 5));
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("47");
		carditVO.setMessageTypeId("IFCSUM");
		CarditPawbDetailsVO carditPawbDetailVO = new CarditPawbDetailsVO();
		carditPawbDetailVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailVO.setConsignmentDestination(DESTN);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailVO);
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
		Mailbag mailbag = new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		mailbag.setDespatchDate(Calendar.getInstance());
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
				carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		cardit.setCarditPK(carditPK);
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailSequenceNumber(2345);
		mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
		CarditTotalVO carditTotalVO = new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DFW");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setPoaCode("FR001");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
		Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
		CarditTransportationVO transportataion = new CarditTransportationVO();
		transportataion.setFlightNumber("FLT124");
		transportataion.setArrivalPort("FRA");
		transportataion.setDeparturePort("DFW");
		transportationVOs.add(transportataion);
		carditVO.setTransportInformation(transportationVOs);
		spy.saveCarditMessages(ediInterchangeVO);
	}
	@Test
	public void createContainer_Find_ULD() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
		ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		HashMap<String, String> systemParameterMap = null;
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("BULK");
		containerVo.setType("B");
		containerVo.setOperationFlag("I");
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(null).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		spy.saveContainers(operationalFlightVO, containerVos);
		assertThat(containerVo.isReassignFlag(), is(false));
	}
	@Test
	public void createContainer_Find_Bulk() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
		ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		HashMap<String, String> systemParameterMap = null;
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("I");
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(null).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		spy.saveContainers(operationalFlightVO, containerVos);
		assertThat(containerVo.isReassignFlag(), is(false));
	}
	
	@Test
	public void flagMLDForMailOperationsInULD_Test() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
		ContainerVO containerVo = new ContainerVO();
		spy.flagMLDForMailOperationsInULD(containerVo, "mode");
	}
	@Test
	public void saveCarditMessage_WithoutPawb() throws SystemException, BusinessException, PersistenceException,
			FinderException, IllegalAccessException, InvocationTargetException {
		Collection<CarditVO> carditMessages = new ArrayList<>();
		CarditVO carditVO = setCarditVO();
		Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
		receptacleVO.setCarditType("IFCSUM");
		receptacleVO.setMailSubClassCode("ert");
		receptacleVO.setReceptacleWeight(new Measure(UnitConstants.WEIGHT, 5));
		receptacleVOs.add(receptacleVO);
		carditVO.setReceptacleInformation(receptacleVOs);
		carditVO.setCompanyCode("IBS");
		carditVO.setCarditType("47");
		carditVO.setMessageTypeId("IFCSUM");
		carditVO.setCarditPawbDetailsVO(null);
		carditMessages.add(carditVO);
		EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
		ediInterchangeVO.setCompanyCode("IBS");
		ediInterchangeVO.setCarditMessages(carditMessages);
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(autoProcessEnabled, "Y");
		systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 987;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
		Mailbag mailbag = new Mailbag();
		mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
		mailbag.setMasterDocumentNumber("1335678");
		mailbag.setDupliacteNumber(1);
		mailbag.setDocumentOwnerId(134);
		mailbag.setSequenceNumber(987);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(987);
		mailbag.setMailbagPK(mailbagPK);
		mailbag.setDespatchDate(Calendar.getInstance());
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
				carditVO.getSenderId());
		Cardit cardit = new Cardit();
		CarditPK carditPK = new CarditPK();
		carditPK.setCompanyCode("IBS");
		carditPK.setCarditKey("34567");
		cardit.setCarditPK(carditPK);
		doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailSequenceNumber(2345);
		mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
		mailConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
		doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("IBS");
		consignmentDocumentPk.setConsignmentNumber("CDG2345");
		consignmentDocumentPk.setPaCode("DE101");
		consignmentDocumentPk.setConsignmentSequenceNumber(1234);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
		CarditTotalVO carditTotalVO = new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DFW");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setPoaCode("FR001");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
		spy.saveCarditMessages(ediInterchangeVO);
	}
	
	@Test
	public void findAirportFromMailbag_mailseqnum_present() throws SystemException, FinderException{
		MailbagPK mailbagPk = new MailbagPK();
		Mailbag mailbag= new Mailbag();
		MailbagVO mailbagvo=new MailbagVO();
		mailbagvo.setMailSequenceNumber(456);
		mailbagvo.setCompanyCode("IBS");  
		mailbagPk.setCompanyCode("IBS"); 
		mailbagPk.setMailSequenceNumber(456);
		mailbag.setMailbagPK(mailbagPk);
		doReturn(MailbagEntity).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPk);
		spy.findAirportFromMailbag(mailbagvo);
		
		
	}
	 
	@Test
	public void findAirportFromMailbag_existing_mailbag() throws SystemException, FinderException,PersistenceException{
		MailbagPK mailbagPk = new MailbagPK();
		Mailbag mailbag= new Mailbag();
		MailbagVO mailbagVOfromDB=new MailbagVO();
		MailbagVO mailbagvo=new MailbagVO();
		mailbagvo.setMailSequenceNumber(0);
		mailbagvo.setCompanyCode(COMPANY_CODE);
		mailbagvo.setMailbagId(MAILBAG_ID);
		mailbag.setMailbagPK(mailbagPk);
		mailbagVOfromDB.setOrigin("CDG");
		mailbagVOfromDB.setDestination("FRA");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVOfromDB).when(dao).findMailbagDetails(mailbagvo.getMailbagId(),mailbagvo.getCompanyCode());
		spy.findAirportFromMailbag(mailbagvo);
		 
		 
	}
	
	@Test
	public void findAirportFromMailbag_newmailbag_return_origin() throws SystemException, FinderException,PersistenceException{
		MailbagPK mailbagPk = new MailbagPK();
		Mailbag mailbag= new Mailbag();
		MailbagVO mailbagVOfromDB=new MailbagVO();
		MailbagVO mailbagvo=new MailbagVO();
		mailbagvo.setMailSequenceNumber(0);
		mailbagvo.setCompanyCode(COMPANY_CODE);
		mailbagvo.setMailbagId(MAILBAG_ID);
		mailbag.setMailbagPK(mailbagPk);
		mailbagVOfromDB.setOrigin("CDG");
		mailbagVOfromDB.setDestination("FRA");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(null).when(dao).findMailbagDetails(mailbagvo.getMailbagId(),mailbagvo.getCompanyCode());
		Page<OfficeOfExchangeVO> originOfficeDetails = new Page<>();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setAirportCode("FRA");
		originOfficeDetails.add(officeOfExchangeVO);
		doReturn(originOfficeDetails).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "USDFWA", 1);
		spy.findAirportFromMailbag(mailbagvo);
		
	} 
	@Test
	public void findAirportFromMailbag_newmailbag_return_destination() throws SystemException, FinderException,PersistenceException{
		MailbagPK mailbagPk = new MailbagPK();
		Mailbag mailbag= new Mailbag();
		MailbagVO mailbagVOfromDB=new MailbagVO();
		MailbagVO mailbagvo=new MailbagVO();
		mailbagvo.setMailSequenceNumber(0);
		mailbagvo.setCompanyCode(COMPANY_CODE);
		mailbagvo.setMailbagId(MAILBAG_ID);
		mailbag.setMailbagPK(mailbagPk);
		mailbagVOfromDB.setOrigin("CDG"); 
		mailbagVOfromDB.setDestination("FRA");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(null).when(dao).findMailbagDetails(mailbagvo.getMailbagId(),mailbagvo.getCompanyCode());
		Page<OfficeOfExchangeVO> originOfficeDetails = new Page<>();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		originOfficeDetails.add(officeOfExchangeVO);
		doReturn(originOfficeDetails).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "DEFRAA", 1);
		spy.findAirportFromMailbag(mailbagvo);
		
		
	}
	
					@Test
					public void findAirportFromMailbag_newmailbag_return_destination_airportcodenull() throws SystemException, FinderException,PersistenceException{
						MailbagPK mailbagPk = new MailbagPK();
						Mailbag mailbag= new Mailbag();
						MailbagVO mailbagVOfromDB=new MailbagVO();
						MailbagVO mailbagvo=new MailbagVO();
						mailbagvo.setMailSequenceNumber(0);
						mailbagvo.setCompanyCode(COMPANY_CODE);
						mailbagvo.setMailbagId(MAILBAG_ID);
						mailbag.setMailbagPK(mailbagPk);
						mailbagVOfromDB.setOrigin("CDG"); 
						mailbagVOfromDB.setDestination("FRA");
						doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
						doReturn(null).when(dao).findMailbagDetails(mailbagvo.getMailbagId(),mailbagvo.getCompanyCode());
						Page<OfficeOfExchangeVO> originOfficeDetails = new Page<>();
						OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
						officeOfExchangeVO.setAirportCode("DFW");
						originOfficeDetails.add(officeOfExchangeVO);
						doReturn(originOfficeDetails).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "DEFRAA", 1);
						spy.findAirportFromMailbag(mailbagvo);
						
						
					}

					@Test
					public void findAirportFromMailbag_newmailbag_return_origin_airportcodenull() throws SystemException, FinderException,PersistenceException{
						MailbagPK mailbagPk = new MailbagPK();
						Mailbag mailbag= new Mailbag();
						MailbagVO mailbagVOfromDB=new MailbagVO();
						MailbagVO mailbagvo=new MailbagVO();
						mailbagvo.setMailSequenceNumber(0);
						mailbagvo.setCompanyCode(COMPANY_CODE);
						mailbagvo.setMailbagId(MAILBAG_ID);
						mailbag.setMailbagPK(mailbagPk);
						mailbagVOfromDB.setOrigin("CDG");
						mailbagVOfromDB.setDestination("FRA");
						doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
						doReturn(null).when(dao).findMailbagDetails(mailbagvo.getMailbagId(),mailbagvo.getCompanyCode());
						Page<OfficeOfExchangeVO> originOfficeDetails = new Page<>();
						OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
						originOfficeDetails.add(officeOfExchangeVO);
						doReturn(originOfficeDetails).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "USDFWA", 1);
						spy.findAirportFromMailbag(mailbagvo);
						
					} 
					
					@Test
					public void findAirportFromMailbag_newmailbag_originofficenull() throws SystemException, FinderException,PersistenceException{
						MailbagPK mailbagPk = new MailbagPK();
						Mailbag mailbag= new Mailbag();
						MailbagVO mailbagVOfromDB=new MailbagVO();
						MailbagVO mailbagvo=new MailbagVO();
						mailbagvo.setMailSequenceNumber(0);
						mailbagvo.setCompanyCode(COMPANY_CODE);
						mailbagvo.setMailbagId(MAILBAG_ID);
						mailbag.setMailbagPK(mailbagPk);
						mailbagVOfromDB.setOrigin("CDG");
						mailbagVOfromDB.setDestination("FRA");
						doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
						doReturn(null).when(dao).findMailbagDetails(mailbagvo.getMailbagId(),mailbagvo.getCompanyCode());
						Page<OfficeOfExchangeVO> originOfficeDetails = new Page<>();
						OfficeOfExchangeVO officeOfExchangeVO = null;
						originOfficeDetails.add(officeOfExchangeVO);
						doReturn(originOfficeDetails).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "USDFWA", 1);
						spy.findAirportFromMailbag(mailbagvo);
						
					} 

					@Test
					public void findAirportFromMailbag_newmailbag_destofficenull() throws SystemException, FinderException,PersistenceException{
						MailbagPK mailbagPk = new MailbagPK();
						Mailbag mailbag= new Mailbag();
						MailbagVO mailbagVOfromDB=new MailbagVO();
						MailbagVO mailbagvo=new MailbagVO();
						mailbagvo.setMailSequenceNumber(0);
						mailbagvo.setCompanyCode(COMPANY_CODE);
						mailbagvo.setMailbagId(MAILBAG_ID);
						mailbag.setMailbagPK(mailbagPk);
						mailbagVOfromDB.setOrigin("CDG"); 
						mailbagVOfromDB.setDestination("FRA");
						doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
						doReturn(null).when(dao).findMailbagDetails(mailbagvo.getMailbagId(),mailbagvo.getCompanyCode());
						Page<OfficeOfExchangeVO> destinationOfficeDetails = new Page<>();
						OfficeOfExchangeVO officeOfExchangeVO = null;
						destinationOfficeDetails.add(officeOfExchangeVO);
						doReturn(destinationOfficeDetails).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "DEFRAA", 1);
						spy.findAirportFromMailbag(mailbagvo);
						
						
					}
	

		@Test(expected = SystemException.class)
	public void calculateAndUpdateLatestAcceptanceTime_Test()
			throws SystemException, BusinessException, MailHHTBusniessException {
		Collection<MailbagVO> newMailbgVOs = new ArrayList<>();
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		LogonAttributes logonAttributes = new LogonAttributes();
		logonAttributes.setCompanyCode(getCompanyCode());
		logonAttributes.setAirportCode("CDG");
		logonAttributes.setAirlineIdentifier(1076);

		String companyCode = COMPANY_CODE;

		postalAdministrationVO.setPaCode("US101");
		postalAdministrationVO.setLatValLevel("E");
		boolean isScanned = true;
		MailbagVO mailbagVo = new MailbagVO();
		mailbagVo.setOoe("USDFWA");
		mailbagVo.setPaCode("US101");
		mailbagVo.setScannedPort("DFW");
		mailbagVo.setFlightNumber("1234");
		mailbagVo.setCompanyCode(COMPANY_CODE);
		mailbagVo.setMailSource("MTK060");
		mailbagVo.setLatValidationNeeded("Y");

		String exchangeCode = "USDFWA";
		Collection<String> officeOfExchanges = new ArrayList<String>();
		officeOfExchanges.add(exchangeCode);
		HashMap<String, String> cityForOE = new HashMap<>();
		cityForOE.put("USDFWA", "DFW");
		Collection<String> cityCodes = new ArrayList<String>();
		cityCodes.add("CDG");
		cityCodes.add("TRV");
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setCityCode("DFW");
		cityVO.setCompanyCode(COMPANY_CODE);
		cityVO.setCountryCode("US");
		cityVO.setNearestAirport("DFW");

		cityMap.put("DFW", cityVO);
		newMailbgVOs.add(mailbagVo);
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);

		LocalDate std = new LocalDate("DFW", Location.ARP, true);

		Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
		flightFilterVO.setLegOrigin("CDG");
		flightFilterVO.setFlightRoute("FRA-DFW");
		flightFilterVO.setCompanyCode(getCompanyCode());
		flightFilterVO.setFlightDate(currentDate);
		flightFilterVO.setStd(std);

		flightFilterVOs.add(flightFilterVO);
		FlightFilterVO flightFilterVos = new FlightFilterVO();
		flightFilterVos.setFlightNumber("1234");

		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		GeneralConfigurationFilterVO generalTimeMappingFilterVO = new GeneralConfigurationFilterVO();
		generalTimeMappingFilterVO.setCompanyCode(COMPANY_CODE);
		generalTimeMappingFilterVO.setAirportCode("CDG");
		generalTimeMappingFilterVO.setConfigurationType("MLT");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("US");
		countryCodeMap.put("CDG", airportValidationVO);
		countryCodeMap.put("CDG", airportValidationVos);
		Collection<ArrayList<String>> groupedOECityArpCodes = new ArrayList<ArrayList<String>>();

		ArrayList<String> groupedOECityArp = new ArrayList<>();
		groupedOECityArp.add("USDFWA");
		groupedOECityArp.add("DFW");
		groupedOECityArp.add("DFW");

		Collection<String> airportCodes = new ArrayList<String>();
		airportCodes.add("DFW");

		Collection<GeneralConfigurationMasterVO> generalConfigurationMasterVOs = new ArrayList<>();
		GeneralConfigurationMasterVO GeneralConfigurationMasterVO = new GeneralConfigurationMasterVO();
		GeneralConfigurationMasterVO.setStartDate(currentDate);
		generalConfigurationMasterVOs.add(GeneralConfigurationMasterVO);

		Collection<GeneralRuleConfigDetailsVO> generalRuleConfigDetailsVos = new ArrayList<>();
		GeneralConfigurationMasterVO.setTimeDetails(generalRuleConfigDetailsVos);

		GeneralRuleConfigDetailsVO generalRuleConfigDetailsVO = new GeneralRuleConfigDetailsVO();
		generalRuleConfigDetailsVos.add(generalRuleConfigDetailsVO);
		generalRuleConfigDetailsVO.setParameterCode("Min");
		generalRuleConfigDetailsVO.setParameterValue("20");

		String syspar = "mailtracking.defaults.uspsinternationalpa";
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mailtracking.defaults.uspsinternationalpa", "US101");

		doReturn(logonAttributes).when(securityContext).getLogonAttributesVO();
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn(postalAdministrationCache).when(CacheFactory.getInstance())
				.getCache(PostalAdministrationCache.ENTITY_NAME);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(any(String.class),
				any(String.class));
		doReturn(officeOfExchangeCache).when(CacheFactory.getInstance()).getCache(OfficeOfExchangeCache.ENTITY_NAME);
		doReturn(cityForOE).when(officeOfExchangeCache).findCityForOfficeOfExchange(companyCode, officeOfExchanges);
		doReturn("US101").when(mailControllerBean).findSystemParameterValue(any(String.class));
		doReturn("US101").when(spy).findSystemParameterValue(any(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(generalConfigurationMasterVOs).when(sharedDefaultsProxy)
				.findGeneralConfigurationDetails(any(GeneralConfigurationFilterVO.class));

		spy.calculateAndUpdateLatestAcceptanceTime(mailbagVo);
	}
	
			@Test
	public void doLATValidation_Warning_Skip_Test()
			throws SystemException, BusinessException, MailHHTBusniessException {

		Collection<MailbagVO> newMailbgVOs = new ArrayList<>();
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		LogonAttributes logonAttributes = new LogonAttributes();
		logonAttributes.setCompanyCode(getCompanyCode());
		logonAttributes.setAirportCode("CDG");
		logonAttributes.setAirlineIdentifier(1076);

		String companyCode = COMPANY_CODE;

		postalAdministrationVO.setPaCode("US101");
		postalAdministrationVO.setLatValLevel("E");
		boolean isScanned = true;
		MailbagVO mailbagVo = new MailbagVO();
		mailbagVo.setOoe("USDFWA");
		mailbagVo.setPaCode("US101");
		mailbagVo.setScannedPort("DFW");
		mailbagVo.setFlightNumber("1234");
		mailbagVo.setCompanyCode(COMPANY_CODE);
		mailbagVo.setMailSource("MTK060");
		mailbagVo.setLatValidationNeeded("N");

		String exchangeCode = "USDFWA";
		Collection<String> officeOfExchanges = new ArrayList<String>();
		officeOfExchanges.add(exchangeCode);
		HashMap<String, String> cityForOE = new HashMap<>();
		cityForOE.put("USDFWA", "DFW");
		Collection<String> cityCodes = new ArrayList<String>();
		cityCodes.add("CDG");
		cityCodes.add("TRV");
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setCityCode("DFW");
		cityVO.setCompanyCode(COMPANY_CODE);
		cityVO.setCountryCode("US");
		cityVO.setNearestAirport("DFW");

		cityMap.put("DFW", cityVO);
		newMailbgVOs.add(mailbagVo);
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);

		LocalDate std = new LocalDate("DFW", Location.ARP, true);

		Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
		flightFilterVO.setLegOrigin("CDG");
		flightFilterVO.setFlightRoute("FRA-DFW");
		flightFilterVO.setCompanyCode(getCompanyCode());
		flightFilterVO.setFlightDate(currentDate);
		flightFilterVO.setStd(std);

		flightFilterVOs.add(flightFilterVO);
		FlightFilterVO flightFilterVos = new FlightFilterVO();
		flightFilterVos.setFlightNumber("1234");

		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		GeneralConfigurationFilterVO generalTimeMappingFilterVO = new GeneralConfigurationFilterVO();
		generalTimeMappingFilterVO.setCompanyCode(COMPANY_CODE);
		generalTimeMappingFilterVO.setAirportCode("CDG");
		generalTimeMappingFilterVO.setConfigurationType("MLT");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("US");
		countryCodeMap.put("CDG", airportValidationVO);
		countryCodeMap.put("CDG", airportValidationVos);
		Collection<ArrayList<String>> groupedOECityArpCodes = new ArrayList<ArrayList<String>>();

		ArrayList<String> groupedOECityArp = new ArrayList<>();
		groupedOECityArp.add("USDFWA");
		groupedOECityArp.add("DFW");
		groupedOECityArp.add("DFW");

		Collection<String> airportCodes = new ArrayList<String>();
		airportCodes.add("DFW");

		Collection<GeneralConfigurationMasterVO> generalConfigurationMasterVOs = new ArrayList<>();
		GeneralConfigurationMasterVO GeneralConfigurationMasterVO = new GeneralConfigurationMasterVO();
		GeneralConfigurationMasterVO.setStartDate(currentDate);
		generalConfigurationMasterVOs.add(GeneralConfigurationMasterVO);

		Collection<GeneralRuleConfigDetailsVO> generalRuleConfigDetailsVos = new ArrayList<>();
		GeneralConfigurationMasterVO.setTimeDetails(generalRuleConfigDetailsVos);

		GeneralRuleConfigDetailsVO generalRuleConfigDetailsVO = new GeneralRuleConfigDetailsVO();
		generalRuleConfigDetailsVos.add(generalRuleConfigDetailsVO);
		generalRuleConfigDetailsVO.setParameterCode("Min");
		generalRuleConfigDetailsVO.setParameterValue("20");

		doReturn(logonAttributes).when(securityContext).getLogonAttributesVO();
		doReturn(postalAdministrationCache).when(CacheFactory.getInstance())
				.getCache(PostalAdministrationCache.ENTITY_NAME);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(any(String.class),
				any(String.class));
		doReturn(officeOfExchangeCache).when(CacheFactory.getInstance()).getCache(OfficeOfExchangeCache.ENTITY_NAME);
		doReturn(cityForOE).when(officeOfExchangeCache).findCityForOfficeOfExchange(companyCode, officeOfExchanges);
		doReturn("US101").when(mailControllerBean).findSystemParameterValue(any(String.class));
		doReturn("US101").when(spy).findSystemParameterValue(any(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(generalConfigurationMasterVOs).when(sharedDefaultsProxy)
				.findGeneralConfigurationDetails(any(GeneralConfigurationFilterVO.class));

		spy.doLATValidation(newMailbgVOs, isScanned);
	}
			
					@Test
	public void doLATValidation_Warning_Skip_Without_Mailsource()
			throws SystemException, BusinessException, MailHHTBusniessException {

		Collection<MailbagVO> newMailbgVOs = new ArrayList<>();
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		LogonAttributes logonAttributes = new LogonAttributes();
		logonAttributes.setCompanyCode(getCompanyCode());
		logonAttributes.setAirportCode("CDG");
		logonAttributes.setAirlineIdentifier(1076);

		String companyCode = COMPANY_CODE;

		postalAdministrationVO.setPaCode("US101");
		postalAdministrationVO.setLatValLevel("E");
		boolean isScanned = true;
		MailbagVO mailbagVo = new MailbagVO();
		mailbagVo.setOoe("USDFWA");
		mailbagVo.setPaCode("US101");
		mailbagVo.setScannedPort("DFW");
		mailbagVo.setFlightNumber("1234");
		mailbagVo.setCompanyCode(COMPANY_CODE);
		mailbagVo.setMailSource("");
		mailbagVo.setLatValidationNeeded("N");

		String exchangeCode = "USDFWA";
		Collection<String> officeOfExchanges = new ArrayList<String>();
		officeOfExchanges.add(exchangeCode);
		HashMap<String, String> cityForOE = new HashMap<>();
		cityForOE.put("USDFWA", "DFW");
		Collection<String> cityCodes = new ArrayList<String>();
		cityCodes.add("CDG");
		cityCodes.add("TRV");
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setCityCode("DFW");
		cityVO.setCompanyCode(COMPANY_CODE);
		cityVO.setCountryCode("US");
		cityVO.setNearestAirport("DFW");

		cityMap.put("DFW", cityVO);
		newMailbgVOs.add(mailbagVo);
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);

		LocalDate std = new LocalDate("DFW", Location.ARP, true);

		Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
		flightFilterVO.setLegOrigin("CDG");
		flightFilterVO.setFlightRoute("FRA-DFW");
		flightFilterVO.setCompanyCode(getCompanyCode());
		flightFilterVO.setFlightDate(currentDate);
		flightFilterVO.setStd(std);

		flightFilterVOs.add(flightFilterVO);
		FlightFilterVO flightFilterVos = new FlightFilterVO();
		flightFilterVos.setFlightNumber("1234");

		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		GeneralConfigurationFilterVO generalTimeMappingFilterVO = new GeneralConfigurationFilterVO();
		generalTimeMappingFilterVO.setCompanyCode(COMPANY_CODE);
		generalTimeMappingFilterVO.setAirportCode("CDG");
		generalTimeMappingFilterVO.setConfigurationType("MLT");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("US");
		countryCodeMap.put("CDG", airportValidationVO);
		countryCodeMap.put("CDG", airportValidationVos);
		Collection<ArrayList<String>> groupedOECityArpCodes = new ArrayList<ArrayList<String>>();

		ArrayList<String> groupedOECityArp = new ArrayList<>();
		groupedOECityArp.add("USDFWA");
		groupedOECityArp.add("DFW");
		groupedOECityArp.add("DFW");

		Collection<String> airportCodes = new ArrayList<String>();
		airportCodes.add("DFW");

		Collection<GeneralConfigurationMasterVO> generalConfigurationMasterVOs = new ArrayList<>();
		GeneralConfigurationMasterVO GeneralConfigurationMasterVO = new GeneralConfigurationMasterVO();
		GeneralConfigurationMasterVO.setStartDate(currentDate);
		generalConfigurationMasterVOs.add(GeneralConfigurationMasterVO);

		Collection<GeneralRuleConfigDetailsVO> generalRuleConfigDetailsVos = new ArrayList<>();
		GeneralConfigurationMasterVO.setTimeDetails(generalRuleConfigDetailsVos);

		GeneralRuleConfigDetailsVO generalRuleConfigDetailsVO = new GeneralRuleConfigDetailsVO();
		generalRuleConfigDetailsVos.add(generalRuleConfigDetailsVO);
		generalRuleConfigDetailsVO.setParameterCode("Min");
		generalRuleConfigDetailsVO.setParameterValue("20");

		doReturn(logonAttributes).when(securityContext).getLogonAttributesVO();
		doReturn(postalAdministrationCache).when(CacheFactory.getInstance())
				.getCache(PostalAdministrationCache.ENTITY_NAME);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(any(String.class),
				any(String.class));
		doReturn(officeOfExchangeCache).when(CacheFactory.getInstance()).getCache(OfficeOfExchangeCache.ENTITY_NAME);
		doReturn(cityForOE).when(officeOfExchangeCache).findCityForOfficeOfExchange(companyCode, officeOfExchanges);
		doReturn("US101").when(mailControllerBean).findSystemParameterValue(any(String.class));
		doReturn("US101").when(spy).findSystemParameterValue(any(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(generalConfigurationMasterVOs).when(sharedDefaultsProxy)
				.findGeneralConfigurationDetails(any(GeneralConfigurationFilterVO.class));

		spy.doLATValidation(newMailbgVOs, isScanned);
	}   
  @Test
	public void saveFligthLoadPlanForMail_IsInvoked() throws SystemException, BusinessException {
		Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs = new ArrayList();
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVOs.add(flightLoadPlanContainerVO);
		spy.saveFligthLoadPlanForMail(flightLoadPlanContainerVOs);
		verify(saveLoadPlanDetailsForMailFeature, times(1)).execute(any(FlightLoadPlanContainerVO.class));
	}
	
 @Test
	public void findLoadPlandetails_Success_Test() throws SystemException, PersistenceException {
		Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs = new ArrayList<>();

		SearchContainerFilterVO searchContainerFilterVO = new SearchContainerFilterVO();
		searchContainerFilterVO.setCompanyCode(COMPANY_CODE);
		searchContainerFilterVO.setCarrierId(1234);
		searchContainerFilterVO.setFlightNumber("1234");
		searchContainerFilterVO.setFlightSeqNumber("1345");
		searchContainerFilterVO.setSegOrigin("CDG");
		searchContainerFilterVO.setDestination("FRA");
		FlightLoadPlanContainerVO flightLoadPlanContainerVO1 = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO1.setCompanyCode(COMPANY_CODE);
		flightLoadPlanContainerVO1.setCarrierId(1076);
		flightLoadPlanContainerVO1.setFlightNumber("1234");
		flightLoadPlanContainerVO1.setFlightSequenceNumber(1);
		flightLoadPlanContainerVO1.setLoadPlanVersion(2);
		flightLoadPlanContainerVO1.setSegOrigin("CDG");
		flightLoadPlanContainerVO1.setSegDestination("FRA");
		FlightLoadPlanContainerVO flightLoadPlanContainerVO2 = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO2.setCompanyCode(COMPANY_CODE);
		flightLoadPlanContainerVO2.setCarrierId(1076);
		flightLoadPlanContainerVO2.setFlightNumber("1234");
		flightLoadPlanContainerVO2.setFlightSequenceNumber(1);
		flightLoadPlanContainerVO2.setLoadPlanVersion(2);
		flightLoadPlanContainerVO2.setSegOrigin("CDG");
		flightLoadPlanContainerVO2.setSegDestination("FRA");
		flightLoadPlanContainerVOs.add(flightLoadPlanContainerVO1);
		flightLoadPlanContainerVOs.add(flightLoadPlanContainerVO2);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(flightLoadPlanContainerVOs).when(dao).findLoadPlandetails(any(SearchContainerFilterVO.class));
		spy.findLoadPlandetails(searchContainerFilterVO);
	}	
			@Test
	public void findLoadPlandetails_Exception_Test() throws SystemException, PersistenceException {
		Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs = new ArrayList<>();
		SearchContainerFilterVO searchContainerFilterVO = new SearchContainerFilterVO();
		searchContainerFilterVO.setCompanyCode(COMPANY_CODE);
		searchContainerFilterVO.setFlightCarrierCode("123");
		searchContainerFilterVO.setFlightNumber("1234");
		searchContainerFilterVO.setFlightSeqNumber("1345");
		searchContainerFilterVO.setSegOrigin("CDG");
		searchContainerFilterVO.setDestination("FRA");
       SystemException systemException = new SystemException("system exception");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).findLoadPlandetails(any(SearchContainerFilterVO.class));
		spy.findLoadPlandetails(searchContainerFilterVO);
	} 
	@Test
	public void saveConsignmentDetailsMaster_test() throws SystemException, FinderException {
		ConsignmentDocumentVO  consignmentDocumentVO= new ConsignmentDocumentVO();  
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("AV");
		consignmentDocumentPk.setConsignmentNumber("AVFRAA006627");
		consignmentDocumentPk.setPaCode("FR001");
		consignmentDocumentPk.setConsignmentSequenceNumber(1);
		consignmentDocumentVO.setCompanyCode("AV");
		consignmentDocumentVO.setConsignmentSequenceNumber(1);
		consignmentDocumentVO.setConsignmentNumber("AVFRAA006627");
		consignmentDocumentVO.setPaCode("FR001");
		consignmentDocumentVO.setSecurityReasonCode("SM");
		consignmentDocumentVO.setAdditionalSecurityInfo("SECURED");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		spy.saveConsignmentDetailsMaster(consignmentDocumentVO);
	}
	@Test
	public void saveConsignmentDetailsMaster_test_WithEmptyConsignmentDocumentVO() throws SystemException, BusinessException {
		ConsignmentDocumentVO  consignmentDocumentVO= null;
		spy.saveConsignmentDetailsMaster(consignmentDocumentVO);
	}
 
	@Test(expected=SystemException.class)
	public void saveConsignmentDetailsMaster_FinderException_test() throws SystemException, FinderException {
		ConsignmentDocumentVO  consignmentDocumentVO= new ConsignmentDocumentVO();  
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("AV");
		consignmentDocumentPk.setConsignmentNumber("AVFRAA006627");
		consignmentDocumentPk.setPaCode("FR001");
		consignmentDocumentPk.setConsignmentSequenceNumber(3);
		consignmentDocumentVO.setCompanyCode("AV");
		consignmentDocumentVO.setConsignmentSequenceNumber(3);
		consignmentDocumentVO.setConsignmentNumber("AVFRAA006627");
		consignmentDocumentVO.setPaCode("FR001");
		consignmentDocumentVO.setSecurityReasonCode("SM");
		consignmentDocumentVO.setAdditionalSecurityInfo("SECURED");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		spy.saveConsignmentDetailsMaster(consignmentDocumentVO);
	}
	@Test
	public void saveConsignmentDetailsMaster_else_test() throws SystemException, FinderException {
		ConsignmentDocumentVO  consignmentDocumentVO= new ConsignmentDocumentVO();  
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("AV");
		consignmentDocumentPk.setConsignmentNumber("AVFRAA006627");
		consignmentDocumentPk.setPaCode("FR001");
		consignmentDocumentPk.setConsignmentSequenceNumber(3);
		consignmentDocumentVO.setCompanyCode("AV");
		consignmentDocumentVO.setConsignmentSequenceNumber(3);
		consignmentDocumentVO.setConsignmentNumber("AVFRAA006627");
		consignmentDocumentVO.setPaCode("FR001");
		consignmentDocumentVO.setSecurityReasonCode("SC");
		consignmentDocumentVO.setAdditionalSecurityInfo("SECURED");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		spy.saveConsignmentDetailsMaster(consignmentDocumentVO);
}   
	@Test
	public void saveConsignmentDetailsMaster_elseif_test() throws SystemException, FinderException {
		ConsignmentDocumentVO  consignmentDocumentVO= new ConsignmentDocumentVO();  
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode("AV");
		consignmentDocumentPk.setConsignmentNumber("AVFRAA006627");
		consignmentDocumentPk.setPaCode("FR001");
		consignmentDocumentPk.setConsignmentSequenceNumber(3);
		consignmentDocumentVO.setCompanyCode("AV");
		consignmentDocumentVO.setConsignmentSequenceNumber(3);
		consignmentDocumentVO.setConsignmentNumber("AVFRAA006627");
		consignmentDocumentVO.setPaCode("FR001");
		consignmentDocumentVO.setSecurityReasonCode("SC");
		consignmentDocumentVO.setAdditionalSecurityInfo("SECURED");
		consignmentDocumentVO.setSecurityStatusCode("SPX");
		consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
		doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
				any(ConsignmentDocumentPK.class));
		spy.saveConsignmentDetailsMaster(consignmentDocumentVO);
}
		
	 @Test
					public void modifyContainer_ToSatisfyUpdateConditionLessTest() throws SystemException, PersistenceException, ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException, FinderException{
						ContainerVO containerVo = new ContainerVO();
						ContainerPK ContainerPK = new ContainerPK();
						Collection<ContainerVO> containerVos = new ArrayList<>();
						OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
						operationalFlightVO.setFlightSequenceNumber(-1);
						HashMap<String, String> systemParameterMap = null;
						Collection<OnwardRoutingVO> onwardRoutings = new ArrayList<>();
						OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
						onwardRoutingVO.setCompanyCode(getCompanyCode());
						onwardRoutingVO.setOnwardFlightDate(new LocalDate("CDG", Location.ARP,true));
						onwardRoutings.add(onwardRoutingVO);
						containerVo.setOnwardRoutings(onwardRoutings);
						containerVo.setAssignedPort("CDG");
						containerVo.setReassignFlag(false);
						containerVo.setContainerNumber("AKE89897AV");
						containerVo.setOperationFlag("U");
						containerVo.setFromDeviationList(true);
						containerVo.setUldReferenceNo(0);
						containerVos.add(containerVo);
						containerEntity.setContainerPK(ContainerPK);
						containerEntity.setAcceptanceFlag("Y");
						containerEntity.setTransitFlag("Y");
						containerEntity.setUldReferenceNo(1);
						doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
						doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
						doReturn(ulDForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
						spy.saveContainers(operationalFlightVO, containerVos);
						assertThat(containerVo.getOperationFlag(),is("U"));
					}
					
	@Test
	public void perforEUValidationForCargoFlight()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy)
				.findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos = new ArrayList<>();
		FlightValidationVO flightValidationVo = new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO = new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class), any(Integer.class));
		Map<String, String> airlineMap = new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforCargoonlyAircraft", "GEC");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class), any(Integer.class),
				anyCollectionOf(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		operationalFlightVO.setAirportCode("CDG");
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		ConsignmentScreeningVO consignmentScreeningVo = null;
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(consignmentScreeningVo).when(dao).findRegulatedCarrierForMailbag(any(String.class), any(Long.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
	@Test
	public void  perforEUValidationForPassengerFlight() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("CO");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforPassengerAircraft", "LH");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class),any(String.class));
		ConsignmentScreeningVO consignmentScreeningVo=new ConsignmentScreeningVO() ;
		consignmentScreeningVo.setAgentID("INDEL-LH");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(consignmentScreeningVo).when(dao).findRegulatedCarrierForMailbag(any(String.class),any(Long.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForPassengerFlightWithAlreadyExistRC() throws SystemException, SharedProxyException, ProxyException, FinderException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVO.setMailSequenceNumber(12345);
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setCompanyCode(COMPANY_CODE);
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
				LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
				LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
			Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
			Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			ConsignmentScreeningVO cs=new ConsignmentScreeningVO();
			cs.setAgentType("RA");
			cs.setScreeningLocation("CDG");				
			consignmentScreeningVos.add(cs);
			
			
			List<String> statusCodes = new ArrayList<>();
			statusCodes.add("SPX");
			statusCodes.add("SPH");
			statusCodes.add("SCO");
			
			List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
			ConsignmentScreeningVO ra=new ConsignmentScreeningVO();
			raAcceptVos.add(ra);
		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforPassengerAircraft", "LH");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class),any(String.class));
		ConsignmentScreeningVO consignmentScreeningVo=new ConsignmentScreeningVO() ;
		consignmentScreeningVo.setAgentID("INDEL-LH");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(consignmentScreeningVo).when(dao).findRegulatedCarrierForMailbag(any(String.class),any(Long.class));
		doReturn(consignmentScreeningDetailsEntity).when(PersistenceController.getEntityManager()).find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));

		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test(expected= SystemException.class)
	public void  perforEUValidationForPassengerFlightWithfindAirlineDetailsException() throws SystemException, SharedProxyException, ProxyException, FinderException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("CO");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforPassengerAircraft", "LH");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		doThrow(ProxyException.class).when(sharedAirlineProxy).validateAlphaCode(any(String.class),any(String.class));
		doThrow(ProxyException.class).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForPassengerFlightWithfindAirlineDetailsWithAirlineParameterEmpty() throws SystemException, SharedProxyException, ProxyException, FinderException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
			AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		
			Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RI");
		cs.setSecurityStatusCode("SPX");
		
		consignmentScreeningVos.add(cs);
			
			List<String> statusCodes = new ArrayList<>();
			statusCodes.add("SPX");
			statusCodes.add("SPH");
			statusCodes.add("SCO");
			
			List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
			List<ConsignmentScreeningVO> riIssueVos=new ArrayList<>();
		ConsignmentScreeningVO raIssues=new ConsignmentScreeningVO();
		raIssues.setAgentType("RI");
		raIssues.setSecurityStatusCode("SPX");
		
	riIssueVos.add(raIssues);
		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
	
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForPassengerFlightWithfindAirlineDetailsWithAirlineParameterNull() throws SystemException, SharedProxyException, ProxyException, FinderException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("CO");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		doReturn(null).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForCargoFlightWithfindAirlineDetailsWithAirlineParameterNull() throws SystemException, SharedProxyException, ProxyException, FinderException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		doReturn(null).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForCargoFlightWithfindAirlineDetailsWithAirlineParameterEmpty() throws SystemException, SharedProxyException, ProxyException, FinderException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setFltOwner("LH");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		FlightValidationVO flightValidationVO=new FlightValidationVO();
		flightValidationVO.setFlightCarrierId(183);
		flightValidationVOs.add(flightValidationVO);
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		Map<String, String> airlineMap=new HashMap<>();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class),any(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	
	@Test(expected= SystemException.class)
	public void  perforEUValidationForPassengerFlightWithfindAirlineParameterCodeException() throws SystemException, SharedProxyException, ProxyException, FinderException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("CO");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforPassengerAircraft", "LH");
		doThrow(ProxyException.class).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		doThrow(ProxyException.class).when(sharedAirlineProxy).validateAlphaCode(any(String.class),any(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test(expected= SystemException.class)
	public void  perforEUValidationForCargoFlightWithfindAirlineParameterCodeException() throws SystemException, SharedProxyException, ProxyException, FinderException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforPassengerAircraft", "LH");
		doThrow(ProxyException.class).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		doThrow(ProxyException.class).when(sharedAirlineProxy).validateAlphaCode(any(String.class),any(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	
	@Test
	public void  perforEUValidationForTruckFlightWithFlightOwner() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("T");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforCargoonlyAircraft", "GEC");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class),any(String.class));
		ConsignmentScreeningVO consignmentScreeningVo=null;
		doReturn(consignmentScreeningVo).when(dao).findRegulatedCarrierForMailbag(any(String.class),any(Long.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForTruckFlightWithFlightValidationNull() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("T");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		doReturn(null).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforCargoonlyAircraft", "GEC");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class),any(String.class));
		ConsignmentScreeningVO consignmentScreeningVo=null;
		doReturn(consignmentScreeningVo).when(dao).findRegulatedCarrierForMailbag(any(String.class),any(Long.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test(expected= SystemException.class)
	public void  perforEUValidationForTruckFlightWithFlightValidationWithValidateAlphaException() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("T");
		operationalFlightVO.setFltOwner("LH");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		doReturn(null).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforCargoonlyAircraft", "GEC");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		doThrow(SharedProxyException.class).when(sharedAirlineProxy).validateAlphaCode(any(String.class),any(String.class));
		ConsignmentScreeningVO consignmentScreeningVo=null;
		doReturn(consignmentScreeningVo).when(dao).findRegulatedCarrierForMailbag(any(String.class),any(Long.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test(expected= SystemException.class)
	public void  perforEUValidationForTruckFlightWithFlightValidationWithNonReferenceException() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("T");
		operationalFlightVO.setFltOwner("LH");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doThrow(ProxyException.class).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		doReturn(null).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforCargoonlyAircraft", "GEC");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test(expected= SystemException.class)
	public void  perforEUValidationForTruckFlightWithFlightValidationWithValidateFlightException() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("T");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap=new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight","N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs= new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		doThrow(ProxyException.class).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		Map<String, String> airlineMap=new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforCargoonlyAircraft", "GEC");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class),any(Integer.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void perforEUValidationForCargoFlightForGreenCountry()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("IN", countryVO);

		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
	@Test
	public void  perforEUValidationForCargoFlightForCountryNull() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		doReturn(null).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForCargoFlightForGreenCountryEmpty() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForCargoFlightRoutesEmpty() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL");
		operationalFlightVO.setFlightType("C");
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForCargoFlightRoutesNull() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO); 
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setCompanyCode("AV");
		operationalFlightVO.setFlightType("C");
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);
		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForCargoFlightPolNull() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForCargoFlightOperationFloghtVONull() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=null;
	
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForContainerDetailVONull() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =null;
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		OperationalFlightVO operationalFlightVO=null;
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO); 
	}
	@Test
	public void  perforEUValidationForMailDetailEmpty() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();;
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=null;
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO); 
	}
	@Test
	public void  perforEUValidationForMailDetailNull() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();;
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =null;
		containerDetailsVO.setMailDetails(mailbagVOs);
		OperationalFlightVO operationalFlightVO=null;
		containerDetailsVOs.add(containerDetailsVO);
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO); 
	}
	@Test
	public void  perforEUValidationForContainerDetailVOEmpty() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		OperationalFlightVO operationalFlightVO=null;
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO); 
	}
	
	@Test
	public void perforEUValidationForCargoFlightForDifferentPol()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("LAX");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		airportCodes.add("DEL");
		Set<String> cityCodes = new HashSet<>();
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");
		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		List<ConsignmentScreeningVO> riIssueVos = new ArrayList<>();
		String PreviousAirport = "DEL";

		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		doReturn(PreviousAirport).when(dao).findRoutingDetailsForMailbag(any(String.class), any(Long.class),
				any(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN"); 
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
	@Test
	public void  perforEUValidationForCargoFlightWithAirportCodeEmpty() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("LAX");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForCargoFlightWithAirportCodesNull() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("LAX");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes=null;
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  perforEUValidationForCargoFlightFoCityEmpty() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void perforEUValidationForCargoFlightFoCityNull()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = null;
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		operationalFlightVO.setAirportCode("CDG");
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);
		Collection<String> airportCodes = new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
	@Test
	public void perforEUValidationForCargoFlightFoNextPOUDifferent()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		;
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("N");
		cityMap.put("DEL", cityVO);
		cityMap.put("LAX", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		operationalFlightVO.setAirportCode("CDG");
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
	@Test
	public void perforEUValidationForCargoFlightFoNextPOUNotEU()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("E");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("N");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		operationalFlightVO.setAirportCode("CDG");
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);
		Collection<String> airportCodes = new ArrayList<>();

		Set<String> cityCodes = new HashSet<>();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
	@Test
	public void  perforEUValidationForCargoFlightForOriginEU() throws SystemException, SharedProxyException, ProxyException, PersistenceException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();;
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("N");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void  findCountryMemberGroupforAirportTestDifferentAirport() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   	
	}
	@Test
	public void  findCountryMemberGroupforAirportTestWithCountryCodeNull() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DEL");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode(null);
		validAirportCodes.put("DEL", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   	
	}
	@Test
	public void  findCountryMemberGroupforAirportCodesNull() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		Map<String, AirportValidationVO> validAirportCodes=null;
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   	
	}
	@Test
	public void  findCountryMemberGroupforAirportCodesEmpty() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap<>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   	
	}
	@Test(expected=SystemException.class)
	public void  perforEUValidationForCargoFlightFoNextPOUNotEUException() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();;
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("E");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("N");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doThrow(SharedProxyException.class).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test(expected=SystemException.class)
	public void  perforEUValidationForCargoFlightForGreenCountryException() throws SystemException, SharedProxyException, ProxyException{
		Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes =new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("");
		country.put("IN", countryVO);
		doThrow(SharedProxyException.class).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs,operationalFlightVO);   	
	}
	@Test
	public void perforEUValidationForTruckFlightWithFlightValidatioVOnNull()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("T");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy)
				.findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		doReturn(null).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos = new ArrayList<>();
		FlightValidationVO flightValidationVo = new FlightValidationVO();
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO = new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class), any(Integer.class));
		Map<String, String> airlineMap = new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforCargoonlyAircraft", "GEC");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class), any(Integer.class),
				anyCollectionOf(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		ConsignmentScreeningVO consignmentScreeningVo = null;
		doReturn(consignmentScreeningVo).when(dao).findRegulatedCarrierForMailbag(any(String.class), any(Long.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
	@Test
	public void perforEUValidationForTruckFlightWithFlightValidationVOsEmpty()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("T");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
			operationalFlightVO.setAirportCode("CDG");
			LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy)
				.findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos = new ArrayList<>();
		FlightValidationVO flightValidationVo = new FlightValidationVO();
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO = new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class), any(Integer.class));
		Map<String, String> airlineMap = new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforCargoonlyAircraft", "GEC");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class), any(Integer.class),
				anyCollectionOf(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		ConsignmentScreeningVO consignmentScreeningVo = null;
		doReturn(consignmentScreeningVo).when(dao).findRegulatedCarrierForMailbag(any(String.class), any(Long.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
		@Test
	public void saveRAIdentifierForMailbagGreenCountry_WithOut_Parameter()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("LAX");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		airportCodes.add("DEL");
		Set<String> cityCodes = new HashSet<>();
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");
		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("N");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);

		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RI");
		cs.setScreeningLocation("DEL");
		cs.setSecurityStatusDate(previousDate);
		cs.setSecurityStatusCode("SPX");
        consignmentScreeningVos.add(cs);
		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		List<ConsignmentScreeningVO> riIssueVos = new ArrayList<>();
		String PreviousAirport = "DEL";

		List<ConsignmentScreeningVO> riIssueVosPrevArp = new ArrayList<>();
		ConsignmentScreeningVO riIssuePrevArp = new ConsignmentScreeningVO();
		riIssuePrevArp.setSecurityStatusCode("SPX");
		riIssuePrevArp.setAgentType("RI");
		riIssuePrevArp.setScreeningLocation("DEL");
		riIssuePrevArp.setSecurityStatusDate(previousDate);
		riIssueVosPrevArp.add(riIssuePrevArp);

		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class)); 
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		doReturn(PreviousAirport).when(dao).findRoutingDetailsForMailbag(any(String.class), any(Long.class),
				any(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
		
		
			@Test
	public void saveRAIdentifierForMailbagGreenCountry_WithScc()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setSecurityStatusCode("SPX");
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("LAX");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setPol("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		airportCodes.add("DEL");
		Set<String> cityCodes = new HashSet<>();
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("N");
		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO(); 
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("N");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);

		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RI");
		cs.setScreeningLocation("DEL");
		cs.setSecurityStatusCode("SPX");
        consignmentScreeningVos.add(cs);
        
        	ConsignmentScreeningVO cs1 = new ConsignmentScreeningVO();
		cs.setAgentType("RI");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusCode("SPX");
        consignmentScreeningVos.add(cs1);
        
        
		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		List<ConsignmentScreeningVO> riIssueVos = new ArrayList<>(); 
		ConsignmentScreeningVO ri=new ConsignmentScreeningVO();
		ri.setAgentType("RI");
		ri.setScreeningLocation("CDG");
		ri.setSecurityStatusCode("SPX");
        consignmentScreeningVos.add(ri);
		
		
		String PreviousAirport = "DEL";

		List<ConsignmentScreeningVO> riIssueVosPrevArp = new ArrayList<>();
		ConsignmentScreeningVO riIssuePrevArp = new ConsignmentScreeningVO();
		riIssuePrevArp.setSecurityStatusCode("SPX");
		riIssuePrevArp.setAgentType("RI");
		riIssuePrevArp.setScreeningLocation("DEL");
		riIssueVosPrevArp.add(riIssuePrevArp);

		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class)); 
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		doReturn(PreviousAirport).when(dao).findRoutingDetailsForMailbag(any(String.class), any(Long.class),
				any(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}

	@Test
	public void saveRAIdentifierForMailbagNotGreenCountry_WithOut_SCC()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("LAX");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		airportCodes.add("DEL");
		Set<String> cityCodes = new HashSet<>();
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("N");
		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("N");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);

		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
	
		consignmentScreeningVos.add(cs);
		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		List<ConsignmentScreeningVO> riIssueVos = new ArrayList<>();
		String PreviousAirport = "DFW";
		List<ConsignmentScreeningVO> riIssueVosPrevArp = new ArrayList<>();
		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		doReturn(PreviousAirport).when(dao).findRoutingDetailsForMailbag(any(String.class), any(Long.class),
				any(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
	
	@Test
	public void saveRAIdentifierForMailbagNotGreenCountry_With_Parameter()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("LAX");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		airportCodes.add("DEL");
		Set<String> cityCodes = new HashSet<>();
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("N");
		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);

		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
	
		consignmentScreeningVos.add(cs);
		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		List<ConsignmentScreeningVO> riIssueVos = new ArrayList<>();
		String PreviousAirport = "DFW";
		List<ConsignmentScreeningVO> riIssueVosPrevArp = new ArrayList<>();
		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		doReturn(PreviousAirport).when(dao).findRoutingDetailsForMailbag(any(String.class), any(Long.class),
				any(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO); 
	}
		@Test
	public void saveRAIdentifierForMailbagNotGreenCountry_WithScc()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("LAX");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		airportCodes.add("DEL");
		Set<String> cityCodes = new HashSet<>();
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("N");
		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("N");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);

		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RI");
		cs.setScreeningLocation("DEL");
		cs.setSecurityStatusDate(previousDate);
		cs.setSecurityStatusCode("SPX");
        consignmentScreeningVos.add(cs);
		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		List<ConsignmentScreeningVO> riIssueVos = new ArrayList<>();
		String PreviousAirport = "DFW";

		List<ConsignmentScreeningVO> riIssueVosPrevArp = new ArrayList<>();
		ConsignmentScreeningVO riIssuePrevArp = new ConsignmentScreeningVO();
		riIssuePrevArp.setSecurityStatusCode("SPX");
		riIssuePrevArp.setAgentType("RI");
		riIssuePrevArp.setScreeningLocation("DEL");
		riIssuePrevArp.setSecurityStatusDate(previousDate);
		riIssueVosPrevArp.add(riIssuePrevArp);

		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class)); 
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		doReturn(PreviousAirport).when(dao).findRoutingDetailsForMailbag(any(String.class), any(Long.class),
				any(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
		
			@Test
	public void saveRAIdentifierForMailbag_With_RAacceptValues_Present_Test()
			throws SystemException, SharedProxyException, ProxyException, FinderException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVO.setMailSequenceNumber(12345);
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setCompanyCode(COMPANY_CODE);
		operationalFlightVO.setPol("CDG");
		
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
	
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		raAcceptVos.add(ra);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
			
		@Test(expected=SystemException.class)
	public void saveRAIdentifierForMailbag_AirlineParameter_Exception_Test()
			throws SystemException, SharedProxyException, ProxyException, FinderException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVO.setMailSequenceNumber(12345);
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setCompanyCode(COMPANY_CODE);
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		Set<String> cityCodes = new HashSet<>();
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RA");
		cs.setScreeningLocation("CDG");
		cs.setSecurityStatusDate(previousDate);
		consignmentScreeningVos.add(cs);

		List<String> statusCodes = new ArrayList<>();
		statusCodes.add("SPX");
		statusCodes.add("SPH");
		statusCodes.add("SCO");

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		ConsignmentScreeningVO ra = new ConsignmentScreeningVO();
		ra.setAgentType("RA");
		ra.setScreeningLocation("CDG");
		ra.setSecurityStatusDate(previousDate);
		raAcceptVos.add(ra);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("operations.flthandling.identifyacc3basedonmanifestedflight", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy)
				.findSystemParameterByCodes(anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		doReturn(flightValidationVOs).when(flightOperationsProxy).getNonReferenceFlights(any(FlightFilterVO.class));
		Collection<FlightValidationVO> flightValidationVos = new ArrayList<>();
		FlightValidationVO flightValidationVo = new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		AirlineVO airlineVO = new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class), any(Integer.class));
		Map<String, String> airlineMap = new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforPassengerAircraft", "LH");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class), any(Integer.class),
				anyCollectionOf(String.class));
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		ConsignmentScreeningVO consignmentScreeningVo = new ConsignmentScreeningVO();
		consignmentScreeningVo.setAgentID("INDEL-LH");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(consignmentScreeningVo).when(dao).findRegulatedCarrierForMailbag(any(String.class), any(Long.class));
		doReturn(consignmentScreeningDetailsEntity).when(PersistenceController.getEntityManager())
				.find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));

		doThrow(ProxyException.class).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
		
		
	@Test
	public void saveRAIdentifierForMailbag_RoutingDetails_Exception()
			throws SystemException, SharedProxyException, ProxyException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setPol("LAX");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("C");
		operationalFlightVO.setAirportCode("CDG");
		Map<String, AirportValidationVO> validAirportCodes = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap = new HashMap<>();
		CityVO cityVO = new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 = new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DEL", cityVO);
		cityMap.put("FRA", cityVO1);
		Collection<String> airportCodes = new ArrayList<>();
		airportCodes.add("DEL");
		Set<String> cityCodes = new HashSet<>();
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("Y");
		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		LocalDate currentDate = new LocalDate("CDG", Location.ARP, true);
		LocalDate previousDate = new LocalDate("DFW", Location.ARP, true);

		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
	
		consignmentScreeningVos.add(cs);
		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		List<ConsignmentScreeningVO> riIssueVos = new ArrayList<>();
		String PreviousAirport = "DFW";
		List<ConsignmentScreeningVO> riIssueVosPrevArp = new ArrayList<>();
		
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class), anyCollectionOf(String.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		SystemException systemException = new SystemException("system exception");
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));
		doThrow(systemException).when(dao).findRoutingDetailsForMailbag(any(String.class), any(Long.class),
				any(String.class));
	spy.performEUValidations(containerDetailsVOs, operationalFlightVO); 
	}
	
		
			@Test
	public void saveRAIdentifierForMailbag_ThirdParty_RA_Issue_Value_No_Test()
			throws SystemException, SharedProxyException, ProxyException, FinderException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVO.setMailSequenceNumber(12345);
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setCompanyCode(COMPANY_CODE);
		operationalFlightVO.setPol("CDG");
		
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("N");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RI");
		cs.setScreeningLocation("DEL");
		cs.setSecurityStatusCode("SPX");
	
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		List<ConsignmentScreeningVO> riIssueVos =new ArrayList<>();
		
		ConsignmentScreeningVO ri = new ConsignmentScreeningVO();
		ri.setAgentType("RI");
		ri.setScreeningLocation("DEL");
		ri.setSecurityStatusCode("SPX");

		String PreviousAirport="DEL";

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(PreviousAirport).when(dao).findRoutingDetailsForMailbag(any(String.class), any(Long.class),
				any(String.class));
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}
			
					@Test
	public void saveRAIdentifierForMailbag_ThirdParty_RA_Issue_Value_N_And_SCC_F_Test()
			throws SystemException, SharedProxyException, ProxyException, FinderException, PersistenceException {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVO.setMailSequenceNumber(12345);
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		operationalFlightVO.setCountryCode("IN");
		operationalFlightVO.setPol("DEL");
		operationalFlightVO.setFlightRoute("DEL-FRA");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setCompanyCode(COMPANY_CODE);
		operationalFlightVO.setPol("CDG");
		
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();

		AirlineAirportParameterVO parameterVo1 = new AirlineAirportParameterVO();
		parameterVo1.setParameterCode("mail.operations.thirdpartyraissuemail");
		parameterVo1.setParameterValue("N");

		AirlineAirportParameterVO parameterVo2 = new AirlineAirportParameterVO();
		parameterVo2.setParameterCode("mail.operations.regulatedagentacceptingmail");
		parameterVo2.setParameterValue("US/RA1234");

		AirlineAirportParameterVO parameterVo3 = new AirlineAirportParameterVO();
		parameterVo3.setParameterCode("mail.operations.raacceptancevalidationoverride");
		parameterVo3.setParameterValue("Y");
		airportParameterVO.add(parameterVo1);
		airportParameterVO.add(parameterVo2);
		airportParameterVO.add(parameterVo3);
		Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		ConsignmentScreeningVO cs = new ConsignmentScreeningVO();
		cs.setAgentType("RI");
		cs.setScreeningLocation("DEL");
		cs.setSecurityStatusCode("EDS");
	
		consignmentScreeningVos.add(cs);

		List<ConsignmentScreeningVO> raAcceptVos = new ArrayList<>();
		List<ConsignmentScreeningVO> riIssueVos =new ArrayList<>();
		
		ConsignmentScreeningVO ri = new ConsignmentScreeningVO();
		ri.setAgentType("RI");
		ri.setScreeningLocation("DEL");
		ri.setSecurityStatusCode("EDS");

		String PreviousAirport="DEL";

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(PreviousAirport).when(dao).findRoutingDetailsForMailbag(any(String.class), any(Long.class),
				any(String.class));
		doReturn(airportParameterVO).when(sharedAirlineProxy)
				.findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		doReturn(consignmentScreeningVos).when(dao).findRAacceptingForMailbag(any(String.class), any(Long.class));

		spy.performEUValidations(containerDetailsVOs, operationalFlightVO);
	}   
    @Test
	public void updateRegAgentForScreeningMethodsTest() throws SystemException, PersistenceException, FinderException{
		ConsignmentScreeningVO consignmentScreeningVO= new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setConsignmentNumber("TEST1");
		consignmentScreeningVO.setPaCode("DE101");
		consignmentScreeningVO.setSource("EXT");
		consignmentScreeningVO.setAirportCode("FRA");
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setSerialNumber(3);
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setAgentID("DE/1092/00");
		consignmentScreeningVO.setExpiryDate("0522");
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		long sernum=0L;
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(sernum).when(dao).findLatestRegAgentIssuing(any(ConsignmentScreeningVO.class));
		doReturn(consignmentScreeningVOs).when(dao).findScreeningMethodsForStampingRegAgentIssueMapping(any(ConsignmentScreeningVO.class));
		doReturn(ConsignmentScreeningDetailsEntity).when(PersistenceController.getEntityManager()).find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));
		spy.updateRegAgentForScreeningMethods(consignmentScreeningVOs);
		
	 } 
	@Test
	public void updateRegAgentForScreeningMethodWithoutScreeningMethodTest() throws SystemException, PersistenceException, FinderException{
		ConsignmentScreeningVO consignmentScreeningVO= new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setConsignmentNumber("TEST1");
		consignmentScreeningVO.setPaCode("DE101");
		consignmentScreeningVO.setSource("EXT");
		consignmentScreeningVO.setAirportCode("FRA");
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setSerialNumber(3);
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setAgentID("DE/1092/00");
		consignmentScreeningVO.setExpiryDate("0522");
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		long sernum=0L;
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(sernum).when(dao).findLatestRegAgentIssuing(any(ConsignmentScreeningVO.class));
		doReturn(null).when(dao).findScreeningMethodsForStampingRegAgentIssueMapping(any(ConsignmentScreeningVO.class));
		doReturn(ConsignmentScreeningDetailsEntity).when(PersistenceController.getEntityManager()).find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));
		spy.updateRegAgentForScreeningMethods(consignmentScreeningVOs);
		
	 }
	@Test
	public void updateRegAgentForScreeningMethodWithoutRITest() throws SystemException, PersistenceException, FinderException{
		ConsignmentScreeningVO consignmentScreeningVO= new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setConsignmentNumber("TEST1");
		consignmentScreeningVO.setPaCode("DE101");
		consignmentScreeningVO.setSource("EXT");
		consignmentScreeningVO.setAirportCode("FRA");
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setSerialNumber(3);
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setAgentID("DE/1092/00");
		consignmentScreeningVO.setExpiryDate("0522");
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		spy.updateRegAgentForScreeningMethods(consignmentScreeningVOs);
		
	 }
	@Test
	public void saveSecurityScreeningFromServiceTest() throws SystemException, PersistenceException{
		SecurityAndScreeningMessageVO  securityAndScreeningMessageVO=new SecurityAndScreeningMessageVO() ;
		securityAndScreeningMessageVO.setCompanyCode("LH");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailbagVO.setType("M");
		mailbagVO.setScannedPort("FRA");
		Collection<MailbagVO>mailbagVOs=new ArrayList<>();
		ConsignmentScreeningVO consignmentScreeningVO= new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setConsignmentNumber("TEST1");
		consignmentScreeningVO.setPaCode("DE101");
		consignmentScreeningVO.setSource("EXT");
		consignmentScreeningVO.setAirportCode("FRA");
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setSerialNumber(3);
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setAgentID("DE/1092/00");
		consignmentScreeningVO.setExpiryDate("0522");
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		mailbagVOs.add(mailbagVO);
		securityAndScreeningMessageVO.setMailbagVOs(mailbagVOs);
		long sernum=10;
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		spy.saveSecurityScreeningFromService(securityAndScreeningMessageVO);
		
	 }
	@Test
	public void saveSecurityScreeningFromServiceTestWithNoMailbagInSystem() throws SystemException, PersistenceException{
		SecurityAndScreeningMessageVO  securityAndScreeningMessageVO=new SecurityAndScreeningMessageVO() ;
		securityAndScreeningMessageVO.setCompanyCode("LH");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailbagVO.setType("M");
		mailbagVO.setScannedPort("FRA");
		Collection<MailbagVO>mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		securityAndScreeningMessageVO.setMailbagVOs(mailbagVOs);
		long sernum=0;
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		spy.saveSecurityScreeningFromService(securityAndScreeningMessageVO);
		
	 }
	@Test()
	public void saveSecurityScreeningFromServiceTestSystemExceptions() throws SystemException, PersistenceException{
		SecurityAndScreeningMessageVO  securityAndScreeningMessageVO=new SecurityAndScreeningMessageVO() ;
		securityAndScreeningMessageVO.setCompanyCode("LH");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailbagVO.setType("M");
		mailbagVO.setScannedPort("FRA");
		Collection<MailbagVO>mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		securityAndScreeningMessageVO.setMailbagVOs(mailbagVOs);
		long sernum=0;
		SystemException systemException = new SystemException("system exception");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		spy.saveSecurityScreeningFromService(securityAndScreeningMessageVO);
		
	 }
	@Test()
	public void saveSecurityScreeningFromServiceWithMailType() {
		SecurityAndScreeningMessageVO  securityAndScreeningMessageVO=new SecurityAndScreeningMessageVO() ;
		securityAndScreeningMessageVO.setCompanyCode("LH");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailbagVO.setType("C");
		mailbagVO.setScannedPort("FRA");
		Collection<MailbagVO>mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		securityAndScreeningMessageVO.setMailbagVOs(mailbagVOs);
		spy.saveSecurityScreeningFromService(securityAndScreeningMessageVO);
		
	 }
	@Test()
	public void saveSecurityScreeningFromServiceTestSystemException() throws SystemException, PersistenceException{
		SecurityAndScreeningMessageVO  securityAndScreeningMessageVO=new SecurityAndScreeningMessageVO() ;
		securityAndScreeningMessageVO.setCompanyCode("LH");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailbagVO.setType("M");
		mailbagVO.setScannedPort("FRA");
		Collection<MailbagVO>mailbagVOs=new ArrayList<>();
		ConsignmentScreeningVO consignmentScreeningVO= new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setConsignmentNumber("TEST1");
		consignmentScreeningVO.setPaCode("DE101");
		consignmentScreeningVO.setSource("EXT");
		consignmentScreeningVO.setAirportCode("FRA");
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setSerialNumber(3);
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setAgentID("DE/1092/00");
		consignmentScreeningVO.setExpiryDate("0522");
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		mailbagVOs.add(mailbagVO);
		securityAndScreeningMessageVO.setMailbagVOs(mailbagVOs);
		mailbagVOs.add(mailbagVO);
		securityAndScreeningMessageVO.setMailbagVOs(mailbagVOs);
		long sernum=10;
		SystemException systemException = new SystemException("system exception");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).findLatestRegAgentIssuing(any(ConsignmentScreeningVO.class));
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		spy.saveSecurityScreeningFromService(securityAndScreeningMessageVO);
		
	 }
	 @Test
	public void findAirportParameterCodeTest() throws SystemException{
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		String companyCode = "IBS";
		String airportCode = "CDG";
		flightFilterVO.setAirportCode(airportCode);
		flightFilterVO.setCompanyCode(companyCode);
		Collection<String> parCodes =new ArrayList<>();
		parCodes.add("operations.flthandling.isonlinehandledairport");
		Map<String,String> arpMap = new HashMap<>();
		arpMap.put("operations.flthandling.isonlinehandledairport", "N");
		doReturn(arpMap).when(sharedAreaProxy).findAirportParametersByCode(companyCode, airportCode,parCodes);
		spy.findAirportParameterCode(flightFilterVO, parCodes);
	 }
	 
	 @Test
	 public void fetchMailIndicatorForProgress() throws SystemException, PersistenceException{
		 Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> operationalFlightVOs =new ArrayList<>();
		 Collection<FlightListingFilterVO> flightListingFilterVOs = new ArrayList<>();
		 FlightListingFilterVO flightListingFilterVO = new FlightListingFilterVO();
		 flightListingFilterVO.setCompanyCode("AA");
		 flightListingFilterVO.setCarrierId(1001);
		 flightListingFilterVO.setFlightNumber("1234");
		 flightListingFilterVO.setAirportCode("DFW");
		 flightListingFilterVO.setFlightSequenceNumber(1);
		 flightListingFilterVOs.add(flightListingFilterVO);
		
		 com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO operationalFlightVO = new com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO();
		 operationalFlightVO.setCompanyCode("AA");
		 operationalFlightVO.setCarrierId(1001);
		 operationalFlightVO.setFlightNumber("1234");
		 operationalFlightVOs.add(operationalFlightVO);
		 doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		 doReturn(operationalFlightVOs).when(dao).fetchMailIndicatorForProgress(any());
		 spy.fetchMailIndicatorForProgress(flightListingFilterVOs);
	 }
	 
	 @Test
	 public void fetchMailIndicatorForProgressForSystemException() throws SystemException, PersistenceException{
		 Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> operationalFlightVOs =new ArrayList<>();
		 Collection<FlightListingFilterVO> flightListingFilterVOs = new ArrayList<>();
		 SystemException systemException = new SystemException("error");
		 doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		 doThrow(systemException).when(dao).fetchMailIndicatorForProgress(any());
		 
		 spy.fetchMailIndicatorForProgress(flightListingFilterVOs);
	 }


	 @Test
		public void saveCarditMessage_WithPawbCountryValidationOrigin() throws SystemException, BusinessException, PersistenceException,
				FinderException, IllegalAccessException, InvocationTargetException {
			Collection<CarditVO> carditMessages = new ArrayList<>();
			CarditVO carditVO = setCarditVO();
			Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
			CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
			receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
			receptacleVO.setCarditType("IFCSUM");
			receptacleVO.setMailSubClassCode("ert");
			receptacleVO.setReceptacleWeight(new Measure(UnitConstants.WEIGHT, 5));
			receptacleVOs.add(receptacleVO);
			carditVO.setReceptacleInformation(receptacleVOs);
			carditVO.setCompanyCode("IBS");
			carditVO.setCarditType("47");
			carditVO.setMessageTypeId("IFCSUM");
			carditVO.getCarditPawbDetailsVO().setConsignmentOriginAirport("FRA");
			carditVO.getCarditPawbDetailsVO().setConsignmentDestinationAirport("DXB");
			carditMessages.add(carditVO);
			EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
			ediInterchangeVO.setCompanyCode("IBS");
			ediInterchangeVO.setCarditMessages(carditMessages);
			String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
			String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
			ArrayList<String> systemParameters = new ArrayList<>();
			systemParameters.add(autoProcessEnabled);
			systemParameters.add(failAlreadyAttached);
			HashMap<String, String> systemParameterMap = new HashMap<>();
			systemParameterMap.put(autoProcessEnabled, "Y");
			systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			long mailSequencenumber = 987;
			doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
			Mailbag mailbag = new Mailbag();
			mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
			mailbag.setMasterDocumentNumber("1335678");
			mailbag.setDupliacteNumber(1);
			mailbag.setDocumentOwnerId(134);
			mailbag.setSequenceNumber(987);
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode("IBS");
			mailbagPK.setMailSequenceNumber(987);
			mailbag.setMailbagPK(mailbagPK);
			mailbag.setDespatchDate(Calendar.getInstance());
			doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
			HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
			Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
			PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
			paDet.setParCode(PAWPARCODE);
			paDet.setParameterValue("YES");
			paDetails.add(paDet);
			postalAdministrationDetailsVOs.put(INVINFO, paDetails);
			postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
			doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
					carditVO.getSenderId());
			Cardit cardit = new Cardit();
			CarditPK carditPK = new CarditPK();
			carditPK.setCompanyCode("IBS");
			carditPK.setCarditKey("34567");
			cardit.setCarditPK(carditPK);
			doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
			ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
			Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
			MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
			mailInConsignmentVO.setCompanyCode("IBS");
			mailInConsignmentVO.setMailSequenceNumber(2345);
			mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
			mailConsignmentVOs.add(mailInConsignmentVO);
			consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
			Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
			RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
			routingInConsignmentVO.setPol("DFW");
			routingInConsignmentVO.setPou("FRA");
			routingVOs.add(routingInConsignmentVO);
			RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
			routingInConsignmentVO1.setPol("FRA");
			routingInConsignmentVO1.setPou("SIN");
			routingVOs.add(routingInConsignmentVO1);
			consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
			consignmentDocumentVO.setAirportCode("CDG");
			doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
			ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
			consignmentDocumentPk.setCompanyCode("IBS");
			consignmentDocumentPk.setConsignmentNumber("CDG2345");
			consignmentDocumentPk.setPaCode("DE101");
			consignmentDocumentPk.setConsignmentSequenceNumber(1234);
			ConsignmentDocument consignmentDocument = new ConsignmentDocument();
			consignmentDocumentVO.setCompanyCode("IBS");
			consignmentDocumentVO.setConsignmentSequenceNumber(1234);
			consignmentDocumentVO.setConsignmentNumber("CDG4367");
			consignmentDocumentVO.setPaCode("DE101");
			consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
			doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
					any(ConsignmentDocumentPK.class));
			Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
			CarditTotalVO carditTotalVO = new CarditTotalVO();
			carditTotalVO.setNumberOfReceptacles("1");
			totalsInformation.add(carditTotalVO);
			carditVO.setTotalsInformation(totalsInformation);
			OfficeOfExchange officeOfExchange=new OfficeOfExchange();
			officeOfExchange.setAirportCode("FRA");
			OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
			officeOfExchangePk.setCompanyCode("IBS");
			officeOfExchangePk.setOfficeOfExchange("FRA");
			officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
			doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
					any(OfficeOfExchangePK.class));
			Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
			OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
			officeOfExchangeVO.setPoaCode("FR001");
			officeOfExchangeVO.setActive(true);
			officeOfExchangePage.add(officeOfExchangeVO);
			ArrayList<String> systemParameter1 = new ArrayList<>();
			systemParameter1.add("mail.operations.pawbcountryvalidation");
			HashMap<String, String> systemParameterMap1 = new HashMap<>();
			systemParameterMap1.put("mail.operations.pawbcountryvalidation", "EUR");
			doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameter1);
			doReturn("EUR").when(mailControllerMock).findSystemParameterValue("mail.operations.pawbcountryvalidation");
			doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
			mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
			doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
			Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
			CarditTransportationVO transportataion = new CarditTransportationVO();
			transportataion.setFlightNumber("FLT124");
			transportataion.setArrivalPort("FRA");
			transportataion.setDeparturePort("DFW");
			transportationVOs.add(transportataion);
			carditVO.setTransportInformation(transportationVOs);
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
			AirportValidationVO airportValidationVO = new AirportValidationVO();
			airportValidationVO.setCountryCode("US");
			airportValidationVO.setCityCode("DFW");
			AirportValidationVO airportValidationVos = new AirportValidationVO();
			airportValidationVos.setCountryCode("DE");
			airportValidationVos.setCityCode("FRA");
			countryCodeMap.put("DFW", airportValidationVO);
			countryCodeMap.put("FRA", airportValidationVos);
			GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
			groupVO.setCompanyCode("AV");
			GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
			detailsVO.setGroupedEntity("DE");
			ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
			detailVOs.add(detailsVO);
			GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
			detailsVO1.setGroupedEntity("US");
			detailVOs.add(detailsVO1);
			groupVO.setGroupDetailsVOs(detailVOs);
			doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
					anyCollectionOf(String.class));
			doReturn(true).when(mailControllerMock).findPawbCountryValidation(carditVO, consignmentDocumentVO);
			spy.saveCarditMessages(ediInterchangeVO);
		}
	 @Test
		public void saveCarditMessage_WithNoPawbCountryValidation() throws SystemException, BusinessException, PersistenceException,
				FinderException, IllegalAccessException, InvocationTargetException {
			Collection<CarditVO> carditMessages = new ArrayList<>();
			CarditVO carditVO = setCarditVO();
			Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
			CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
			receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
			receptacleVO.setCarditType("IFCSUM");
			receptacleVO.setMailSubClassCode("ert");
			receptacleVO.setReceptacleWeight(new Measure(UnitConstants.WEIGHT, 5));
			receptacleVOs.add(receptacleVO);
			carditVO.setReceptacleInformation(receptacleVOs);
			carditVO.setCompanyCode("IBS");
			carditVO.setCarditType("47");
			carditVO.setMessageTypeId("IFCSUM");
			carditVO.getCarditPawbDetailsVO().setConsignmentOriginAirport("DFW");
			carditVO.getCarditPawbDetailsVO().setConsignmentDestinationAirport("DXB");
			carditMessages.add(carditVO);
			EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
			ediInterchangeVO.setCompanyCode("IBS");
			ediInterchangeVO.setCarditMessages(carditMessages);
			String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
			String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
			ArrayList<String> systemParameters = new ArrayList<>();
			systemParameters.add(autoProcessEnabled);
			systemParameters.add(failAlreadyAttached);
			HashMap<String, String> systemParameterMap = new HashMap<>();
			systemParameterMap.put(autoProcessEnabled, "Y");
			systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			long mailSequencenumber = 987;
			doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
			Mailbag mailbag = new Mailbag();
			mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
			mailbag.setMasterDocumentNumber("1335678");
			mailbag.setDupliacteNumber(1);
			mailbag.setDocumentOwnerId(134);
			mailbag.setSequenceNumber(987);
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode("IBS");
			mailbagPK.setMailSequenceNumber(987);
			mailbag.setMailbagPK(mailbagPK);
			mailbag.setDespatchDate(Calendar.getInstance());
			doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
			HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
			Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
			PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
			paDet.setParCode(PAWPARCODE);
			paDet.setParameterValue("YES");
			paDetails.add(paDet);
			postalAdministrationDetailsVOs.put(INVINFO, paDetails);
			postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
			doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
					carditVO.getSenderId());
			Cardit cardit = new Cardit();
			CarditPK carditPK = new CarditPK();
			carditPK.setCompanyCode("IBS");
			carditPK.setCarditKey("34567");
			cardit.setCarditPK(carditPK);
			doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
			ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
			Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
			MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
			mailInConsignmentVO.setCompanyCode("IBS");
			mailInConsignmentVO.setMailSequenceNumber(2345);
			mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
			mailConsignmentVOs.add(mailInConsignmentVO);
			consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
			Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
			RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
			routingInConsignmentVO.setPol("DFW");
			routingInConsignmentVO.setPou("FRA");
			routingVOs.add(routingInConsignmentVO);
			RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
			routingInConsignmentVO1.setPol("FRA");
			routingInConsignmentVO1.setPou("SIN");
			routingVOs.add(routingInConsignmentVO1);
			consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
			consignmentDocumentVO.setAirportCode("CDG");
			doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
			ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
			consignmentDocumentPk.setCompanyCode("IBS");
			consignmentDocumentPk.setConsignmentNumber("CDG2345");
			consignmentDocumentPk.setPaCode("DE101");
			consignmentDocumentPk.setConsignmentSequenceNumber(1234);
			ConsignmentDocument consignmentDocument = new ConsignmentDocument();
			consignmentDocumentVO.setCompanyCode("IBS");
			consignmentDocumentVO.setConsignmentSequenceNumber(1234);
			consignmentDocumentVO.setConsignmentNumber("CDG4367");
			consignmentDocumentVO.setPaCode("DE101");
			consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
			doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
					any(ConsignmentDocumentPK.class));
			Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
			CarditTotalVO carditTotalVO = new CarditTotalVO();
			carditTotalVO.setNumberOfReceptacles("1");
			totalsInformation.add(carditTotalVO);
			carditVO.setTotalsInformation(totalsInformation);
			OfficeOfExchange officeOfExchange=new OfficeOfExchange();
			officeOfExchange.setAirportCode("DFW");
			OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
			officeOfExchangePk.setCompanyCode("IBS");
			officeOfExchangePk.setOfficeOfExchange("DFW");
			officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
			doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
					any(OfficeOfExchangePK.class));
			Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
			OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
			officeOfExchangeVO.setPoaCode("FR001");
			officeOfExchangeVO.setActive(true);
			officeOfExchangePage.add(officeOfExchangeVO);
			ArrayList<String> systemParameter1 = new ArrayList<>();
			systemParameter1.add("mail.operations.pawbcountryvalidation");
			HashMap<String, String> systemParameterMap1 = new HashMap<>();
			systemParameterMap1.put("mail.operations.pawbcountryvalidation", "EU");
			doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameter1);
			doReturn("EU").when(mailControllerMock).findSystemParameterValue("mail.operations.pawbcountryvalidation");
			doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
			mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
			doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
			Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
			CarditTransportationVO transportataion = new CarditTransportationVO();
			transportataion.setFlightNumber("FLT124");
			transportataion.setArrivalPort("FRA");
			transportataion.setDeparturePort("DFW");
			transportationVOs.add(transportataion);
			carditVO.setTransportInformation(transportationVOs);
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
			AirportValidationVO airportValidationVO = new AirportValidationVO();
			airportValidationVO.setCountryCode("US");
			airportValidationVO.setCityCode("DFW");
			AirportValidationVO airportValidationVos = new AirportValidationVO();
			airportValidationVos.setCountryCode("DE");
			airportValidationVos.setCountryCode("FRA");
			airportValidationVos.setCityCode("FRA");
			countryCodeMap.put("DFW", airportValidationVO);
			countryCodeMap.put("FRA", airportValidationVos);
			GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
			groupVO.setCompanyCode("AV");
			GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
			detailsVO.setGroupedEntity("DE");
			ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
			detailVOs.add(detailsVO);
			GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
			detailsVO1.setGroupedEntity("NL");
			detailVOs.add(detailsVO1);
			groupVO.setGroupDetailsVOs(detailVOs);
			doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
					anyCollectionOf(String.class));
			doReturn(false).when(mailControllerMock).findPawbCountryValidation(carditVO, consignmentDocumentVO);
			spy.saveCarditMessages(ediInterchangeVO);
		}
	 @Test
		public void pawbCountryValidation() throws SystemException, BusinessException, PersistenceException,
				FinderException, IllegalAccessException, InvocationTargetException {
		 ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		 Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
			RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
			routingInConsignmentVO.setPol("DFW");
			routingInConsignmentVO.setPou("SIN");
			routingVOs.add(routingInConsignmentVO);
			RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
			routingInConsignmentVO1.setPol("SIN");
			routingInConsignmentVO1.setPou("FRA");
			routingVOs.add(routingInConsignmentVO1);
			consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
			consignmentDocumentVO.setCompanyCode("IBS");
			CarditVO carditVO = new CarditVO();
			CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
			carditPawbDetailsVO.setConsignmentOriginAirport("DFW");
			carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
			carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
			ArrayList<String> systemParameter = new ArrayList<>();
			systemParameter.add("mail.operations.pawbcountryvalidation");
			HashMap<String, String> systemParameterMap = new HashMap<>();
			systemParameterMap.put("mail.operations.pawbcountryvalidation", "EU");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameter);
			doReturn("EU").when(mailControllerMock).findSystemParameterValue("mail.operations.pawbcountryvalidation");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
			AirportValidationVO airportValidationVO = new AirportValidationVO();
			airportValidationVO.setCountryCode("US");
			airportValidationVO.setCityCode("DFW");
			AirportValidationVO airportValidationVos = new AirportValidationVO();
			airportValidationVos.setCountryCode("DE");
			airportValidationVos.setCityCode("FRA");
			AirportValidationVO airportValidationVoTransit = new AirportValidationVO();
			airportValidationVoTransit.setCountryCode("SG");
			airportValidationVoTransit.setCityCode("SIN");
			countryCodeMap.put("DFW", airportValidationVO);
			countryCodeMap.put("FRA", airportValidationVos);
			countryCodeMap.put("SIN", airportValidationVoTransit);
			GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
			groupVO.setCompanyCode("AV");
			GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
			detailsVO.setGroupedEntity("DE");
			ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
			detailVOs.add(detailsVO);
			GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
			detailsVO1.setGroupedEntity("NL");
			detailVOs.add(detailsVO1);
			groupVO.setGroupDetailsVOs(detailVOs);
			doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
					anyCollectionOf(String.class));
			spy.findPawbCountryValidation(carditVO, consignmentDocumentVO);
	 }
	 @Test
		public void pawbCountryValidation_throwException() throws SystemException, BusinessException, PersistenceException,
				FinderException, IllegalAccessException, InvocationTargetException {
		 ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		 Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
			RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
			routingInConsignmentVO.setPol("DFW");
			routingInConsignmentVO.setPou("SIN");
			routingVOs.add(routingInConsignmentVO);
			RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
			routingInConsignmentVO1.setPol("SIN");
			routingInConsignmentVO1.setPou("FRA");
			routingVOs.add(routingInConsignmentVO1);
			consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
			consignmentDocumentVO.setCompanyCode("IBS");
			CarditVO carditVO = new CarditVO();
			CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
			carditPawbDetailsVO.setConsignmentOriginAirport("DFW");
			carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
			carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
			ArrayList<String> systemParameter = new ArrayList<>();
			systemParameter.add("mail.operations.pawbcountryvalidation");
			HashMap<String, String> systemParameterMap = new HashMap<>();
			systemParameterMap.put("mail.operations.pawbcountryvalidation", "EU");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameter);
			doReturn("EU").when(mailControllerMock).findSystemParameterValue("mail.operations.pawbcountryvalidation");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
			AirportValidationVO airportValidationVO = new AirportValidationVO();
			airportValidationVO.setCountryCode("US");
			airportValidationVO.setCityCode("DFW");
			AirportValidationVO airportValidationVos = new AirportValidationVO();
			airportValidationVos.setCountryCode("DE");
			airportValidationVos.setCityCode("FRA");
			AirportValidationVO airportValidationVoTransit = new AirportValidationVO();
			airportValidationVoTransit.setCountryCode("SG");
			airportValidationVoTransit.setCityCode("SIN");
			countryCodeMap.put("DFW", airportValidationVO);
			countryCodeMap.put("FRA", airportValidationVos);
			countryCodeMap.put("SIN", airportValidationVoTransit);
			GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
			groupVO.setCompanyCode("AV");
			GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
			detailsVO.setGroupedEntity("DE");
			ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
			detailVOs.add(detailsVO);
			GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
			detailsVO1.setGroupedEntity("NL");
			detailVOs.add(detailsVO1);
			groupVO.setGroupDetailsVOs(detailVOs);
			doThrow(ProxyException.class).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
			doReturn(new HashMap()).when(sharedAreaProxy).validateAirportCodes(any(String.class),
					anyCollectionOf(String.class));
			spy.findPawbCountryValidation(carditVO, consignmentDocumentVO);
	 }
	 @Test
	public void saveCarditMessage_PawbSucess() throws SystemException, PersistenceException, FinderException, ProxyException, MailTrackingBusinessException, DuplicateMailBagsException, IllegalAccessException, InvocationTargetException {
			Collection<CarditVO> carditMessages = new ArrayList<>();
			CarditVO carditVO = setCarditVO();
			Collection<CarditReceptacleVO> receptacleVOs = new ArrayList();
			CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
			receptacleVO.setReceptacleId("FRCDGAUSDFWAACA28013002800717");
			receptacleVO.setCarditType("IFCSUM");
			receptacleVO.setMailSubClassCode("ert");
			receptacleVO.setReceptacleWeight(new Measure(UnitConstants.WEIGHT, 5));
			receptacleVOs.add(receptacleVO);
			carditVO.setReceptacleInformation(receptacleVOs);
			carditVO.setCompanyCode("IBS");
			carditVO.setCarditType("47");
			carditVO.setMessageTypeId("IFCSUM");
			carditVO.getCarditPawbDetailsVO().setConsignmentOriginAirport("DFW");
			carditVO.getCarditPawbDetailsVO().setConsignmentDestinationAirport("DXB");
			carditMessages.add(carditVO);
			EDIInterchangeVO ediInterchangeVO = new EDIInterchangeVO();
			ediInterchangeVO.setCompanyCode("IBS");
			ediInterchangeVO.setCarditMessages(carditMessages);
			String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
			String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
			ArrayList<String> systemParameters = new ArrayList<>();
			systemParameters.add(autoProcessEnabled);
			systemParameters.add(failAlreadyAttached);
			HashMap<String, String> systemParameterMap = new HashMap<>();
			systemParameterMap.put(autoProcessEnabled, "Y");
			systemParameterMap.put("mailtracking.mra.triggerforimport", "Y");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			long mailSequencenumber = 987;
			doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class), any(String.class));
			Mailbag mailbag = new Mailbag();
			mailbag.setMailIdr("FRCDGAUSDFWAACA28013002800717");
			mailbag.setMasterDocumentNumber("1335678");
			mailbag.setDupliacteNumber(1);
			mailbag.setDocumentOwnerId(134);
			mailbag.setSequenceNumber(987);
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode("IBS");
			mailbagPK.setMailSequenceNumber(987);
			mailbag.setMailbagPK(mailbagPK);
			mailbag.setDespatchDate(Calendar.getInstance());
			doReturn(mailbag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailbagPK);
			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
			HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
			Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
			PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
			paDet.setParCode(PAWPARCODE);
			paDet.setParameterValue("YES");
			paDetails.add(paDet);
			postalAdministrationDetailsVOs.put(INVINFO, paDetails);
			postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
			doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(),
					carditVO.getSenderId());
			Cardit cardit = new Cardit();
			CarditPK carditPK = new CarditPK();
			carditPK.setCompanyCode("IBS");
			carditPK.setCarditKey("34567");
			cardit.setCarditPK(carditPK);
			doReturn(cardit).when(PersistenceController.getEntityManager()).find(eq(Cardit.class), any(CarditPK.class));
			ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
			Page<MailInConsignmentVO> mailConsignmentVOs = new Page();
			MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
			mailInConsignmentVO.setCompanyCode("IBS");
			mailInConsignmentVO.setMailSequenceNumber(2345);
			mailInConsignmentVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, 5)));
			mailConsignmentVOs.add(mailInConsignmentVO);
			consignmentDocumentVO.setMailInConsignmentVOs(mailConsignmentVOs);
			Collection<RoutingInConsignmentVO>routingVOs = new ArrayList<>();
			RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
			routingInConsignmentVO.setPol("DFW");
			routingInConsignmentVO.setPou("FRA");
			routingVOs.add(routingInConsignmentVO);
			RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
			routingInConsignmentVO1.setPol("FRA");
			routingInConsignmentVO1.setPou("SIN");
			routingVOs.add(routingInConsignmentVO1);
			consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
			consignmentDocumentVO.setAirportCode("CDG");
			doReturn(consignmentDocumentVO).when(dao).findConsignmentDocumentDetails(any(ConsignmentFilterVO.class));
			ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
			consignmentDocumentPk.setCompanyCode("IBS");
			consignmentDocumentPk.setConsignmentNumber("CDG2345");
			consignmentDocumentPk.setPaCode("DE101");
			consignmentDocumentPk.setConsignmentSequenceNumber(1234);
			ConsignmentDocument consignmentDocument = new ConsignmentDocument();
			consignmentDocumentVO.setCompanyCode("IBS");
			consignmentDocumentVO.setConsignmentSequenceNumber(1234);
			consignmentDocumentVO.setConsignmentNumber("CDG4367");
			consignmentDocumentVO.setPaCode("DE101");
			consignmentDocument.setConsignmentDocumentPK(consignmentDocumentPk);
			doReturn(consignmentDocument).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class),
					any(ConsignmentDocumentPK.class));
			Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
			CarditTotalVO carditTotalVO = new CarditTotalVO();
			carditTotalVO.setNumberOfReceptacles("1");
			totalsInformation.add(carditTotalVO);
			carditVO.setTotalsInformation(totalsInformation);
			OfficeOfExchange officeOfExchange=new OfficeOfExchange();
			officeOfExchange.setAirportCode("DFW");
			OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
			officeOfExchangePk.setCompanyCode("IBS");
			officeOfExchangePk.setOfficeOfExchange("DFW");
			officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
			doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
					any(OfficeOfExchangePK.class));
			Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
			OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
			officeOfExchangeVO.setPoaCode("FR001");
			officeOfExchangeVO.setActive(true);
			officeOfExchangePage.add(officeOfExchangeVO);
			ArrayList<String> systemParameter1 = new ArrayList<>();
			systemParameter1.add("mail.operations.pawbcountryvalidation");
			HashMap<String, String> systemParameterMap1 = new HashMap<>();
			systemParameterMap1.put("mail.operations.pawbcountryvalidation", "EU");
			doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameter1);
			doReturn("EU").when(mailControllerMock).findSystemParameterValue("mail.operations.pawbcountryvalidation");
			doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange("IBS", "CDF", 1);
			mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
			doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
			Collection<CarditTransportationVO> transportationVOs = new ArrayList<>();
			CarditTransportationVO transportataion = new CarditTransportationVO();
			transportataion.setFlightNumber("FLT124");
			transportataion.setArrivalPort("FRA");
			transportataion.setDeparturePort("DFW");
			transportationVOs.add(transportataion);
			carditVO.setTransportInformation(transportationVOs);
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
			AirportValidationVO airportValidationVO = new AirportValidationVO();
			airportValidationVO.setCountryCode("US");
			airportValidationVO.setCityCode("DFW");
			AirportValidationVO airportValidationVos = new AirportValidationVO();
			airportValidationVos.setCountryCode("DE");
			airportValidationVos.setCityCode("FRA");
			countryCodeMap.put("DFW", airportValidationVO);
			countryCodeMap.put("FRA", airportValidationVos);
			GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
			groupVO.setCompanyCode("AV");
			GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
			detailsVO.setGroupedEntity("DE");
			ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
			detailVOs.add(detailsVO);
			GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
			detailsVO1.setGroupedEntity("NL");
			detailVOs.add(detailsVO1);
			groupVO.setGroupDetailsVOs(detailVOs);
			doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
					anyCollectionOf(String.class));
			doReturn(true).when(mailControllerMock).findPawbCountryValidation(carditVO, consignmentDocumentVO);
			spy.saveCarditMessages(ediInterchangeVO);
		}

	@Test
	public void publishMasterDataForMailForGPADetails_Test() throws SystemException, RemoteException {
		MailMasterDataFilterVO mailMasterDataFilterVO = new MailMasterDataFilterVO();
		mailMasterDataFilterVO.setCompanyCode(companyCode);
		mailMasterDataFilterVO.setMasterType(masterTypeForGPADetails);

		doNothing().when(spy).publishGPADetails(companyCode);
		doNothing().when(spy).publishSubclassDetails(companyCode);
		doNothing().when(spy).publishExchangeOfficeDetails(companyCode);
		spy.publishMasterDataForMail(mailMasterDataFilterVO);
		verify(spy, times(1)).publishGPADetails(companyCode);
		verify(spy, times(1)).publishSubclassDetails(companyCode);
		verify(spy, times(1)).publishExchangeOfficeDetails(companyCode);
	}

	@Test
	public void publishMasterDataForMail_MasterTypeIsWithoutPACOD_Test() throws SystemException, RemoteException {
		MailMasterDataFilterVO mailMasterDataFilterVO = new MailMasterDataFilterVO();
		mailMasterDataFilterVO.setCompanyCode(companyCode);
		mailMasterDataFilterVO.setMasterType(masterTypeForGPADetailsWithoutPACOD);
		doNothing().when(spy).publishSubclassDetails(companyCode);
		doNothing().when(spy).publishExchangeOfficeDetails(companyCode);
		spy.publishMasterDataForMail(mailMasterDataFilterVO);

		verify(spy, times(0)).publishGPADetails(companyCode);
		verify(spy, times(1)).publishSubclassDetails(companyCode);
		verify(spy, times(1)).publishExchangeOfficeDetails(companyCode);
	}

	@Test
	public void publishMasterDataForMail_MasterTypeIsWithoutSubclass_Test() throws SystemException, RemoteException {
		MailMasterDataFilterVO mailMasterDataFilterVO = new MailMasterDataFilterVO();
		mailMasterDataFilterVO.setCompanyCode(companyCode);
		mailMasterDataFilterVO.setMasterType(masterTypeWithoutSubclassCode);
		doNothing().when(spy).publishGPADetails(companyCode);
		doNothing().when(spy).publishExchangeOfficeDetails(companyCode);
		spy.publishMasterDataForMail(mailMasterDataFilterVO);
		verify(spy, times(1)).publishGPADetails(companyCode);
		verify(spy, times(0)).publishSubclassDetails(companyCode);
	}

	@Test
	public void publishExchangeOfficeDetails_Test() throws SystemException, ProxyException {
		doReturn(new ArrayList<>()).when(spy).getOfficeOfExchangeDetails(companyCode);
		doNothing().when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.publishExchangeOfficeDetails(companyCode);
		verify(spy, times(1)).getOfficeOfExchangeDetails(companyCode);
		verify(msgBrokerMessageProxy, times(1)).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
	}
	@Test()
	public void publishExchangeOfficeDetailsThrowProxyException_Test() throws ProxyException, SystemException {
		doReturn(new ArrayList<>()).when(spy).getOfficeOfExchangeDetails(companyCode);
		doThrow(ProxyException.class).when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.publishExchangeOfficeDetails(companyCode);
	}
	@Test
	public void getOfficeOfExchangeDetails_Test() throws SystemException, PersistenceException {
		Collection<OfficeOfExchangeVO> officeOfExchangeDetails = new ArrayList<>();
		OfficeOfExchangeVO officeOfExchange = new OfficeOfExchangeVO();
		officeOfExchange.setCode("TWA");
		officeOfExchange.setCodeDescription("Taiwan Post");
		officeOfExchange.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		officeOfExchangeDetails.add(officeOfExchange);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(officeOfExchangeDetails).when(dao).getOfficeOfExchangeDetails(companyCode);
		Collection<OfficeOfExchangeVO> result = spy.getOfficeOfExchangeDetails(companyCode);
		verify(dao, times(1)).getOfficeOfExchangeDetails(companyCode);
		assertEquals(officeOfExchangeDetails, result);
	}

	@Test
	public void publishGPADetails_Test() throws SystemException, ProxyException {
		doReturn(new ArrayList<>()).when(spy).getPADetails(companyCode);
		doNothing().when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.publishGPADetails(companyCode);
		verify(spy, times(1)).getPADetails(companyCode);
		verify(msgBrokerMessageProxy, times(1)).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
	}

	@Test()
	public void publishGPADetailsThrowProxyException_Test() throws ProxyException, SystemException {
		doReturn(new ArrayList<>()).when(spy).getPADetails(companyCode);
		doThrow(ProxyException.class).when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.publishGPADetails(companyCode);
	}

	@Test
	public void getPADetails_Test() throws SystemException, PersistenceException {
		Collection<PostalAdministrationVO> paDetails = new ArrayList<>();
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		postalAdministrationVO.setPaCode("TWA");
		postalAdministrationVO.setPaName("Taiwan Post");
		postalAdministrationVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		paDetails.add(postalAdministrationVO);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(paDetails).when(dao).getPADetails(companyCode);
		Collection<PostalAdministrationVO> result = spy.getPADetails(companyCode);
		verify(dao, times(1)).getPADetails(companyCode);
		assertEquals(paDetails, result);
	}

	@Test
	public void publishMasterDataForMailbagDetails_Test() throws SystemException, RemoteException {
		MailMasterDataFilterVO mailMasterDataFilterVO = new MailMasterDataFilterVO();
		mailMasterDataFilterVO.setCompanyCode(companyCode);
		mailMasterDataFilterVO.setMasterType(masterTypeForMailbagDetails);
		mailMasterDataFilterVO.setNoOfDaysToConsider(60);
		mailMasterDataFilterVO.setLastScanTime(72);

		doNothing().when(spy).publishMailbagDetails(mailMasterDataFilterVO);
		spy.publishMasterDataForMail(mailMasterDataFilterVO);
		verify(spy, times(1)).publishMailbagDetails(mailMasterDataFilterVO);
	}

	@Test
	public void publishMasterDataForMail_MasterTypeIsWithoutMALBAGINF_Test() throws SystemException, RemoteException {
		MailMasterDataFilterVO mailMasterDataFilterVO = new MailMasterDataFilterVO();
		mailMasterDataFilterVO.setCompanyCode(companyCode);
		mailMasterDataFilterVO.setMasterType("");
		mailMasterDataFilterVO.setNoOfDaysToConsider(60);
		mailMasterDataFilterVO.setLastScanTime(72);
		spy.publishMasterDataForMail(mailMasterDataFilterVO);
		verify(spy, times(0)).publishMailbagDetails(mailMasterDataFilterVO);
	}

	@Test
	public void publishMailbagDetails_Test() throws SystemException, ProxyException {
		MailMasterDataFilterVO mailMasterDataFilterVO = new MailMasterDataFilterVO();
		mailMasterDataFilterVO.setCompanyCode(companyCode);
		mailMasterDataFilterVO.setMasterType(masterTypeForMailbagDetails);
		mailMasterDataFilterVO.setNoOfDaysToConsider(60);
		mailMasterDataFilterVO.setLastScanTime(72);

		doReturn(new ArrayList<>()).when(spy).getMailbagDetails(mailMasterDataFilterVO);
		doNothing().when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.publishMailbagDetails(mailMasterDataFilterVO);
		verify(spy, times(1)).getMailbagDetails(mailMasterDataFilterVO);
		verify(msgBrokerMessageProxy, times(1)).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		verify(spy, times(1)).updateInterfaceFlag(anyCollectionOf(MailbagVO.class), anyString());
	}

	@Test()
	public void publishMailbagDetailsThrowProxyException_Test() throws ProxyException, SystemException {
		MailMasterDataFilterVO mailMasterDataFilterVO = new MailMasterDataFilterVO();
		mailMasterDataFilterVO.setCompanyCode(companyCode);
		mailMasterDataFilterVO.setMasterType(masterTypeForMailbagDetails);
		mailMasterDataFilterVO.setNoOfDaysToConsider(60);
		mailMasterDataFilterVO.setLastScanTime(72);

		doReturn(new ArrayList<>()).when(spy).getMailbagDetails(mailMasterDataFilterVO);
		doThrow(ProxyException.class).when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.publishMailbagDetails(mailMasterDataFilterVO);
	}

	@Test
	public void getMailbagDetails_Test() throws SystemException, PersistenceException {
		MailMasterDataFilterVO mailMasterDataFilterVO = new MailMasterDataFilterVO();
		mailMasterDataFilterVO.setCompanyCode(companyCode);
		mailMasterDataFilterVO.setMasterType(masterTypeForMailbagDetails);
		mailMasterDataFilterVO.setNoOfDaysToConsider(60);
		mailMasterDataFilterVO.setLastScanTime(72);

		Collection<MailbagDetailsVO> mailbagDetailsVOCollection = new ArrayList<>();
		MailbagDetailsVO mailbagDetailsVO = new MailbagDetailsVO();
		mailbagDetailsVO.setMailSequenceNumber(100L);
		mailbagDetailsVO.setMailbagId("SGSINAAEDXBAAUN14208304000200");
		mailbagDetailsVO.setFlightCarrierCode("LH");
		mailbagDetailsVO.setSegmentOrigin("SIN");
		mailbagDetailsVOCollection.add(mailbagDetailsVO);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagDetailsVOCollection).when(dao).getMailbagDetails(mailMasterDataFilterVO);
		Collection<MailbagDetailsVO> result = spy.getMailbagDetails(mailMasterDataFilterVO);
		verify(dao, times(1)).getMailbagDetails(mailMasterDataFilterVO);
		assertEquals(mailbagDetailsVOCollection, result);
	}


	@Test
	public void publishSubclassDetails_Test() throws SystemException, ProxyException {
		doReturn(new ArrayList<>()).when(spy).getSubclassDetails(companyCode);
		doNothing().when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.publishSubclassDetails(companyCode);
		verify(spy, times(1)).getSubclassDetails(companyCode);
		verify(msgBrokerMessageProxy, times(1)).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
	}
	@Test()
	public void publishSubclassDetailsThrowProxyException_Test() throws ProxyException, SystemException {
		doReturn(new ArrayList<>()).when(spy).getSubclassDetails(companyCode);
		doThrow(ProxyException.class).when(msgBrokerMessageProxy).encodeAndSaveMessageAsync(any(BaseMessageVO.class));
		spy.publishSubclassDetails(companyCode);
	}
	@Test
	public void getSubclassDetails_Test() throws SystemException, PersistenceException {
		Collection<MailSubClassVO> subclassDetails = new ArrayList<>();
		MailSubClassVO mailSubClassVO = new MailSubClassVO();
		mailSubClassVO.setCode("TWA");
		mailSubClassVO.setDescription("Taiwan Post");
		mailSubClassVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		subclassDetails.add(mailSubClassVO);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(subclassDetails).when(dao).getSubclassDetails(companyCode);
		Collection<MailSubClassVO> result = spy.getSubclassDetails(companyCode);
		verify(dao, times(1)).getSubclassDetails(companyCode);
		assertEquals(subclassDetails, result);
	}

	 @Test
	    public void doSecurityScreeningValidationsTestForContainers()
	    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, PersistenceException, ProxyException, FinderException {
			MailbagVO mailbagVO = new MailbagVO();
			MailbagPK mailBagPK = new MailbagPK();
			Mailbag mailBag = new Mailbag();
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO=new SecurityScreeningValidationFilterVO();
			mailBag.setMailIdr("FRCDGADEFRAAAUA01170604990888");
			mailBag.setSecurityStatusCode("SPX");
			mailbagVO.setCompanyCode("IBS");
			mailbagVO.setMailSequenceNumber(456);
			mailbagVO.setSecurityStatusCode("SPX");
			  mailBagPK.setCompanyCode("IBS"); 
			  mailBagPK.setMailSequenceNumber(456);
			  mailBag.setMailbagPK(mailBagPK);
			doReturn(MailbagEntity).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
			spy.findMailbagForSecurityScreeningVal(securityScreeningValidationFilterVO,mailBagPK);

		}
	 @Test
	    public void doSecurityScreeningValidationsTestForMailbagWithSpxStatus()
	    		throws SystemException, FinderException {
			MailbagVO mailbagVO = new MailbagVO();
			MailbagPK mailBagPK = new MailbagPK();
			Mailbag mailBag = new Mailbag();
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO=new SecurityScreeningValidationFilterVO();
			mailBag.setMailIdr("FRCDGADEFRAAAUA01170604990888");
			mailBag.setSecurityStatusCode("SPX");
			mailbagVO.setCompanyCode("IBS");
			mailbagVO.setMailSequenceNumber(456);
			mailbagVO.setSecurityStatusCode("SPX");
			  mailBagPK.setCompanyCode("IBS"); 
			  mailBagPK.setMailSequenceNumber(456);
			  mailBag.setMailbagPK(mailBagPK);
			  MailbagEntity.setSecurityStatusCode("SPX");
			doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
			spy.findMailbagForSecurityScreeningVal(securityScreeningValidationFilterVO,mailBagPK);

		}
	 @Test
	    public void doSecurityScreeningValidationsTestForMailbagWithFinderException()
	    		throws SystemException, FinderException {
			MailbagVO mailbagVO = new MailbagVO();
			MailbagPK mailBagPK = new MailbagPK();
			Mailbag mailBag = new Mailbag();
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO=new SecurityScreeningValidationFilterVO();
			mailBag.setMailIdr("FRCDGADEFRAAAUA01170604990888");
			mailBag.setSecurityStatusCode("SPX");
			mailbagVO.setCompanyCode("IBS");
			mailbagVO.setMailSequenceNumber(456);
			mailbagVO.setSecurityStatusCode("SPX");
			  mailBagPK.setCompanyCode("IBS"); 
			  mailBagPK.setMailSequenceNumber(456);
			  mailBag.setMailbagPK(mailBagPK);
			  MailbagEntity.setSecurityStatusCode("SPX");
			  FinderException findException = new FinderException("finder exception");
				doThrow(findException).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
			spy.findMailbagForSecurityScreeningVal(securityScreeningValidationFilterVO,mailBagPK);

		}
	 @Test
	    public void doSecurityScreeningValidationsTestForMailbagWithOrgAndDest()
	    		throws SystemException, FinderException {
			MailbagVO mailbagVO = new MailbagVO();
			MailbagPK mailBagPK = new MailbagPK();
			Mailbag mailBag = new Mailbag();
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO=new SecurityScreeningValidationFilterVO();
			securityScreeningValidationFilterVO.setOriginAirport("FRA");
			securityScreeningValidationFilterVO.setDestinationAirport("DFW");
			mailBag.setMailIdr("FRCDGADEFRAAAUA01170604990888");
			mailBag.setSecurityStatusCode("SPX");
			mailbagVO.setCompanyCode("IBS");
			mailbagVO.setMailSequenceNumber(456);
			mailbagVO.setSecurityStatusCode("SPX");
			  mailBagPK.setCompanyCode("IBS"); 
			  mailBagPK.setMailSequenceNumber(456);
			  mailBag.setMailbagPK(mailBagPK);
			  MailbagEntity.setSecurityStatusCode("SPX");
			  doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
			spy.findMailbagForSecurityScreeningVal(securityScreeningValidationFilterVO,mailBagPK);

		}
	 @Test
	 public void testUpdateIntFlgAsNForMailBagsInConatiner() throws PersistenceException, SystemException, FinderException {
		 HbaMarkingVO hbaMarkingVO = new HbaMarkingVO();
			hbaMarkingVO.setCompanyCode("CC");
			hbaMarkingVO.setUldRefNo(1000);
			hbaMarkingVO.setHbaPosition("POS");
			hbaMarkingVO.setHbaType("TYP");
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		    Collection<ContainerDetailsVO> containers=new ArrayList<>();
			ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
			Collection<MailbagVO> mailbagVOs=new ArrayList<>();
			mailbagVOs.add(mailbagVO);
			containerDetailsVO.setMailDetails(mailbagVOs);
			containers.add(containerDetailsVO);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		 doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
			doReturn(new Mailbag()).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
		 spy.updateIntFlgAsNForMailBagsInConatiner(hbaMarkingVO);
	 }

	 @Test(expected=SystemException.class)
	 public void testUpdateIntFlgAsNForMailBagsInConatinerSystemException() throws PersistenceException, SystemException, FinderException {
		 HbaMarkingVO hbaMarkingVO = new HbaMarkingVO();
			hbaMarkingVO.setCompanyCode("CC");
			hbaMarkingVO.setUldRefNo(1000);
			hbaMarkingVO.setHbaPosition("POS");
			hbaMarkingVO.setHbaType("TYP");
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		    Collection<ContainerDetailsVO> containers=new ArrayList<>();
			ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
			Collection<MailbagVO> mailbagVOs=new ArrayList<>();
			mailbagVOs.add(mailbagVO);
			containerDetailsVO.setMailDetails(mailbagVOs);
			containers.add(containerDetailsVO);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		 doThrow(PersistenceException.class).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		 spy.updateIntFlgAsNForMailBagsInConatiner(hbaMarkingVO);
	 }

	 @Test
	 public void testUpdateIntFlgAsNForMailBagsInConatinerWithoutMailBags() throws PersistenceException, SystemException, FinderException {
		 HbaMarkingVO hbaMarkingVO = new HbaMarkingVO();
			hbaMarkingVO.setCompanyCode("CC");
			hbaMarkingVO.setUldRefNo(1000);
			hbaMarkingVO.setHbaPosition("POS");
			hbaMarkingVO.setHbaType("TYP");
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		    Collection<ContainerDetailsVO> containers=new ArrayList<>();
			ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
			Collection<MailbagVO> mailbagVOs=new ArrayList<>();
			mailbagVOs.add(mailbagVO);
			containerDetailsVO.setMailDetails(null);
			containers.add(containerDetailsVO);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		 doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		 spy.updateIntFlgAsNForMailBagsInConatiner(hbaMarkingVO);
	 }

	 @Test
	 public void testUpdateIntFlgAsNForMailBagsInConatinerWithEmptyMailBags() throws PersistenceException, SystemException, FinderException {
		 HbaMarkingVO hbaMarkingVO = new HbaMarkingVO();
			hbaMarkingVO.setCompanyCode("CC");
			hbaMarkingVO.setUldRefNo(1000);
			hbaMarkingVO.setHbaPosition("POS");
			hbaMarkingVO.setHbaType("TYP");
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		    Collection<ContainerDetailsVO> containers=new ArrayList<>();
			ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
			Collection<MailbagVO> mailbagVOs=new ArrayList<>();
			containerDetailsVO.setMailDetails(mailbagVOs);
			containers.add(containerDetailsVO);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		 doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		 spy.updateIntFlgAsNForMailBagsInConatiner(hbaMarkingVO);
	 }

	 @Test(expected=SystemException.class)
	 public void testUpdateInterfaceFlag() throws PersistenceException, SystemException, FinderException {
		 MailbagVO mailbagVO = new MailbagVO();
		 mailbagVO.setCompanyCode("LH");
		 mailbagVO.setMailSequenceNumber(123);
		 Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		 MailbagPK mailBagPK = new MailbagPK();
		 mailBagPK.setCompanyCode("LH");
		 mailBagPK.setMailSequenceNumber(123);
			mailbagVOs.add(mailbagVO);
			Mailbag mailBag = new Mailbag();
			mailBag.setMailbagPK(new MailbagPK());
			doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
		 spy.updateInterfaceFlag(mailbagVOs, "N");
	 }

	 @Test
	 public void testUpdateInterfaceFlagWithNull() throws PersistenceException, SystemException, FinderException {
		 MailbagVO mailbagVO = new MailbagVO();
		 mailbagVO.setCompanyCode("LH");
		 mailbagVO.setMailSequenceNumber(123);
		 Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		 MailbagPK mailBagPK = new MailbagPK();
		 mailBagPK.setCompanyCode("LH");
		 mailBagPK.setMailSequenceNumber(123);
			mailbagVOs.add(mailbagVO);
			Mailbag mailBag = new Mailbag();
			mailBag.setMailbagPK(new MailbagPK());
			doReturn(null).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
		 spy.updateInterfaceFlag(mailbagVOs, "N");
	 }

	 @Test
	 public void testUpdateInterfaceFlagSuccess() throws SystemException, FinderException {
		 MailbagVO mailbagVO = new MailbagVO();
		 mailbagVO.setCompanyCode("LH");
		 mailbagVO.setMailSequenceNumber(123);
		 Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		 MailbagPK mailBagPK = new MailbagPK();
		 mailBagPK.setCompanyCode("LH");
		 mailBagPK.setMailSequenceNumber(123);
			mailbagVOs.add(mailbagVO);
			Mailbag mailBag = new Mailbag();
			mailBag.setMailbagPK(new MailbagPK());
			mailBag.setIntFlg("N");
			mailBag.getIntFlg();
			doReturn(MailbagEntity).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(), mailBagPK);
		 spy.updateInterfaceFlag(mailbagVOs, "N");
	 }

	@Test
	public void shouldInvokeDao_When_PlannedRoutingIndexDetails_IsInvoked()
			throws SystemException, PersistenceException {
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		spy.getPlannedRoutingIndexDetails(any(String.class));
		verify(dao, times(1)).getPlannedRoutingIndexDetails(any(String.class));
	}

	@Test(expected=SystemException.class)
	public void shouldThrowException_When_PlannedRoutingIndexDetails_ThrowException()
			throws SystemException, PersistenceException {
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(SystemException.class).when(dao).getPlannedRoutingIndexDetails(any(String.class));
		spy.getPlannedRoutingIndexDetails(any(String.class));
		verify(dao, times(1)).getPlannedRoutingIndexDetails(any(String.class));
	}

	 

	@Test
	public void getExchangeOfficeDetails_Test() throws SystemException, PersistenceException {
		Collection<OfficeOfExchangeVO> officeOfExchangeDetails = new ArrayList<>();
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		officeOfExchangeVO.setCompanyCode("AV");
		officeOfExchangeVO.setCityCode("NYC");
		officeOfExchangeVO.setOfficeCode("D");
		officeOfExchangeVO.setPoaCode("DE101");
		officeOfExchangeVO.setAirportCode("EWR");
		officeOfExchangeDetails.add(officeOfExchangeVO);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(officeOfExchangeDetails).when(dao).getExchangeOfficeDetails(companyCode, airportCode);
		Collection<OfficeOfExchangeVO> result = spy.getExchangeOfficeDetails(companyCode, airportCode);
		verify(dao, times(1)).getExchangeOfficeDetails(companyCode, airportCode);
		assertEquals(officeOfExchangeDetails, result);
	}
	@Test
	public void updateOciInformationTest() throws PersistenceException, SystemException, SharedProxyException{
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DXB");
		
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE01125AV");
		containerVOs.add(uldContainer);
		
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("AEDXBADEFRAAACA24506182910911");
		mailbagVO.setAwbNumber("134-30000980");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DXB");
		mailbagVO.setUpliftAirport("DXB");
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("BE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		Collection<OtherCustomsInformationVO> otherCustomsInformationVOs = new ArrayList<>(); 
		OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
		otherCustomsInformationVO.setCountryCode("BE");
		otherCustomsInformationVO.setCustomsInfomation("421");
		otherCustomsInformationVO.setInfoIdentifier("ACP");
		otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
		otherCustomsInformationVOs.add(otherCustomsInformationVO);
		ShipmentDetailVO shipmentDelVO = new ShipmentDetailVO();
		shipmentDelVO.setOtherCustomsInformationVOs(otherCustomsInformationVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(shipmentDelVO).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		doNothing().when(operationsShipmentProxy).saveShipmentDetailsAsync(any(ShipmentDetailVO.class));
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("BE");
		ArrayList<String> countries;
		Map<String, CountryVO> country = new HashMap<String, CountryVO>();
		country.put("BE", countryVO);
		countries = new ArrayList<>();
		countries.add("BE");
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(), any());
		spy.doSecurityAndScreeningValidations(operationalFlightVO,containerVOs,bulkContainers,flightValidationVOs);
	}
	@Test
	public void updateOciInformationWhenMailbagsNullTest() throws PersistenceException, SystemException{
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DXB");
		
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE01125AV");
		containerVOs.add(uldContainer);
		
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("AEDXBADEFRAAACA24506182910911");
		mailbagVO.setAwbNumber("134-30000980");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DXB");
		mailbagVO.setUpliftAirport("DXB");
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(null);
		containers.add(containerDetailsVO);
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("BE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		Collection<OtherCustomsInformationVO> otherCustomsInformationVOs = new ArrayList<>(); 
		OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
		otherCustomsInformationVO.setCountryCode("BE");
		otherCustomsInformationVO.setCustomsInfomation("421");
		otherCustomsInformationVO.setInfoIdentifier("ACP");
		otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
		otherCustomsInformationVOs.add(otherCustomsInformationVO);
		ShipmentDetailVO shipmentDelVO = new ShipmentDetailVO();
		shipmentDelVO.setOtherCustomsInformationVOs(otherCustomsInformationVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(shipmentDelVO).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		doNothing().when(operationsShipmentProxy).saveShipmentDetailsAsync(any(ShipmentDetailVO.class));
		spy.updateOciInfo(containers,mailbagVOs ,operationalFlightVO);
	}
	
	@Test
	public void updateOciInformationWhenMailbagsEmptyTest() throws PersistenceException, SystemException{
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DXB");
		
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE01125AV");
		containerVOs.add(uldContainer);
		
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("AEDXBADEFRAAACA24506182910911");
		mailbagVO.setAwbNumber("134-30000980");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DXB");
		mailbagVO.setUpliftAirport("DXB");
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(new ArrayList<>());
		containers.add(containerDetailsVO);
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("BE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		Collection<OtherCustomsInformationVO> otherCustomsInformationVOs = new ArrayList<>(); 
		OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
		otherCustomsInformationVO.setCountryCode("BE");
		otherCustomsInformationVO.setCustomsInfomation("421");
		otherCustomsInformationVO.setInfoIdentifier("ACP");
		otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
		otherCustomsInformationVOs.add(otherCustomsInformationVO);
		ShipmentDetailVO shipmentDelVO = new ShipmentDetailVO();
		shipmentDelVO.setOtherCustomsInformationVOs(otherCustomsInformationVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(shipmentDelVO).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		doNothing().when(operationsShipmentProxy).saveShipmentDetailsAsync(any(ShipmentDetailVO.class));
		spy.updateOciInfo(containers,mailbagVOs ,operationalFlightVO);
	}
	
	@Test
	public void updateOciInformationWhenAWBNumberNullTest() throws PersistenceException, SystemException, SharedProxyException{
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DXB");
		
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE01125AV");
		containerVOs.add(uldContainer);
		
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("AEDXBADEFRAAACA24506182910911");
		mailbagVO.setAwbNumber(null);
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DXB");
		mailbagVO.setUpliftAirport("DXB");
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("BE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		Collection<OtherCustomsInformationVO> otherCustomsInformationVOs = new ArrayList<>(); 
		OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
		otherCustomsInformationVO.setCountryCode("BE");
		otherCustomsInformationVO.setCustomsInfomation("421");
		otherCustomsInformationVO.setInfoIdentifier("ACP");
		otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
		otherCustomsInformationVOs.add(otherCustomsInformationVO);
		ShipmentDetailVO shipmentDelVO = new ShipmentDetailVO();
		shipmentDelVO.setOtherCustomsInformationVOs(otherCustomsInformationVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(shipmentDelVO).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		doNothing().when(operationsShipmentProxy).saveShipmentDetailsAsync(any(ShipmentDetailVO.class));
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("BE");
		ArrayList<String> countries;
		Map<String, CountryVO> country = new HashMap<String, CountryVO>();
		country.put("BE", countryVO);
		countries = new ArrayList<>();
		countries.add("BE");
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(), any());
		spy.doSecurityAndScreeningValidations(operationalFlightVO,containerVOs,bulkContainers,flightValidationVOs);
	}
	
		@Test
	public void updateOciInformationWhenOtherCustomsInformationVosNullTest() throws PersistenceException, SystemException, SharedProxyException{
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DXB");
		
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE01125AV");
		containerVOs.add(uldContainer);
		
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("AEDXBADEFRAAACA24506182910911");
		mailbagVO.setAwbNumber("134-30000980");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DXB");
		mailbagVO.setUpliftAirport("DXB");
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("BE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		Collection<OtherCustomsInformationVO> otherCustomsInformationVOs = new ArrayList<>(); 
		OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
		otherCustomsInformationVO.setCountryCode("BE");
		otherCustomsInformationVO.setCustomsInfomation("421");
		otherCustomsInformationVO.setInfoIdentifier("ACP");
		otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
		otherCustomsInformationVOs.add(otherCustomsInformationVO);
		ShipmentDetailVO shipmentDelVO = new ShipmentDetailVO();
		shipmentDelVO.setOtherCustomsInformationVOs(null);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(shipmentDelVO).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		doNothing().when(operationsShipmentProxy).saveShipmentDetailsAsync(any(ShipmentDetailVO.class));

		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("BE");
		ArrayList<String> countries;
		Map<String, CountryVO> country = new HashMap<String, CountryVO>();
		country.put("BE", countryVO);
		countries = new ArrayList<>();
		countries.add("BE");
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(), any());
		spy.doSecurityAndScreeningValidations(operationalFlightVO,containerVOs,bulkContainers,flightValidationVOs);
	}
	@Test
	public void updateOciInformationWhenOtherCustomsInformationVosEmptyTest() throws PersistenceException, SystemException, SharedProxyException{
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DXB");
		
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE01125AV");
		containerVOs.add(uldContainer);
		
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("AEDXBADEFRAAACA24506182910911");
		mailbagVO.setAwbNumber("134-30000980");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DXB");
		mailbagVO.setUpliftAirport("DXB");
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("BE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		Collection<OtherCustomsInformationVO> otherCustomsInformationVOs = new ArrayList<>(); 
		OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
		otherCustomsInformationVO.setCountryCode("BE");
		otherCustomsInformationVO.setCustomsInfomation("421");
		otherCustomsInformationVO.setInfoIdentifier("ACP");
		otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
		otherCustomsInformationVOs.add(otherCustomsInformationVO);
		ShipmentDetailVO shipmentDelVO = new ShipmentDetailVO();
		shipmentDelVO.setOtherCustomsInformationVOs(new ArrayList<>());
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(shipmentDelVO).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		doNothing().when(operationsShipmentProxy).saveShipmentDetailsAsync(any(ShipmentDetailVO.class));
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("BE");
		ArrayList<String> countries;
		Map<String, CountryVO> country = new HashMap<String, CountryVO>();
		country.put("BE", countryVO);
		countries = new ArrayList<>();
		countries.add("BE");
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(), any());
		spy.doSecurityAndScreeningValidations(operationalFlightVO,containerVOs,bulkContainers,flightValidationVOs);
	}
	
	@Test
	public void updateOciInformationWhenIsoCountryCodeNullTest() throws PersistenceException, SystemException{
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DXB");
		
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE01125AV");
		containerVOs.add(uldContainer);
		
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("AEDXBADEFRAAACA24506182910911");
		mailbagVO.setAwbNumber("134-30000980");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DXB");
		mailbagVO.setUpliftAirport("DXB");
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode(null);
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		Collection<OtherCustomsInformationVO> otherCustomsInformationVOs = new ArrayList<>(); 
		OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
		otherCustomsInformationVO.setCountryCode("BE");
		otherCustomsInformationVO.setCustomsInfomation("421");
		otherCustomsInformationVO.setInfoIdentifier("ACP");
		otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
		otherCustomsInformationVOs.add(otherCustomsInformationVO);
		ShipmentDetailVO shipmentDelVO = new ShipmentDetailVO();
		shipmentDelVO.setOtherCustomsInformationVOs(otherCustomsInformationVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(shipmentDelVO).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		doNothing().when(operationsShipmentProxy).saveShipmentDetailsAsync(any(ShipmentDetailVO.class));
		spy.doSecurityAndScreeningValidations(operationalFlightVO,containerVOs,bulkContainers,flightValidationVOs);
	}
	@Test
	public void updateOciInformationWhenAgentIdNullTest() throws PersistenceException, SystemException{
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DXB");
		
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE01125AV");
		containerVOs.add(uldContainer);
		
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("AEDXBADEFRAAACA24506182910911");
		mailbagVO.setAwbNumber("134-30000980");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DXB");
		mailbagVO.setUpliftAirport("DXB");
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID(null);
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("BE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		Collection<OtherCustomsInformationVO> otherCustomsInformationVOs = new ArrayList<>(); 
		OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
		otherCustomsInformationVO.setCountryCode("BE");
		otherCustomsInformationVO.setCustomsInfomation("421");
		otherCustomsInformationVO.setInfoIdentifier("ACP");
		otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
		otherCustomsInformationVOs.add(otherCustomsInformationVO);
		ShipmentDetailVO shipmentDelVO = new ShipmentDetailVO();
		shipmentDelVO.setOtherCustomsInformationVOs(otherCustomsInformationVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(shipmentDelVO).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		doNothing().when(operationsShipmentProxy).saveShipmentDetailsAsync(any(ShipmentDetailVO.class));
		spy.doSecurityAndScreeningValidations(operationalFlightVO,containerVOs,bulkContainers,flightValidationVOs);
	}
	@Test
	public void updateOciInformationThrowsExceptionTest() throws PersistenceException, SystemException, SharedProxyException{
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DXB");
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE01125AV");
		containerVOs.add(uldContainer);
		
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("AEDXBADEFRAAACA24506182910911");
		mailbagVO.setAwbNumber("134-30000980");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DXB");
		mailbagVO.setUpliftAirport("DXB");
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("BE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		Collection<OtherCustomsInformationVO> otherCustomsInformationVOs = new ArrayList<>(); 
		OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
		otherCustomsInformationVO.setCountryCode("BE");
		otherCustomsInformationVO.setCustomsInfomation("421");
		otherCustomsInformationVO.setInfoIdentifier("ACP");
		otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
		otherCustomsInformationVOs.add(otherCustomsInformationVO);
		ShipmentDetailVO shipmentDelVO = new ShipmentDetailVO();
		shipmentDelVO.setOtherCustomsInformationVOs(otherCustomsInformationVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		SystemException systemException = new SystemException("system exception");
		doThrow(systemException).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		doNothing().when(operationsShipmentProxy).saveShipmentDetailsAsync(any(ShipmentDetailVO.class));
		CountryVO countryVO = new CountryVO();
        countryVO.setMemberGroupCode("BE");
        ArrayList<String> countries;
        Map<String, CountryVO> country = new HashMap<String, CountryVO>();
        country.put("BE", countryVO);
        countries = new ArrayList<>();
        countries.add("BE");
        doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(), any());
		spy.doSecurityAndScreeningValidations(operationalFlightVO,containerVOs,bulkContainers,flightValidationVOs);
	}
	@Test
	public void updateOciInformationWhenConsignemntScreeningVosEmptyTest() throws PersistenceException, SystemException{
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("DXB");
		
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		ContainerVO uldContainer=new ContainerVO();
		uldContainer.setContainerNumber("AKE01125AV");
		containerVOs.add(uldContainer);
		
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		containerVOs.add(uldContainer);
		bulkContainers.add(bulkContainer);
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("IBS");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("AEDXBADEFRAAACA24506182910911");
		mailbagVO.setAwbNumber("134-30000980");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DXB");
		mailbagVO.setUpliftAirport("DXB");
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList<>();
		FlightValidationVO flightValidationVo=new FlightValidationVO();
		flightValidationVOs.add(flightValidationVo);
		Collection<ContainerDetailsVO> containers=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containers.add(containerDetailsVO);
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		Collection<OtherCustomsInformationVO> otherCustomsInformationVOs = new ArrayList<>(); 
		OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
		otherCustomsInformationVO.setCountryCode("BE");
		otherCustomsInformationVO.setCustomsInfomation("421");
		otherCustomsInformationVO.setInfoIdentifier("ACP");
		otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
		otherCustomsInformationVOs.add(otherCustomsInformationVO);
		ShipmentDetailVO shipmentDelVO = new ShipmentDetailVO();
		shipmentDelVO.setOtherCustomsInformationVOs(otherCustomsInformationVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));
		doReturn(containers).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(shipmentDelVO).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		doNothing().when(operationsShipmentProxy).saveShipmentDetailsAsync(any(ShipmentDetailVO.class));
		spy.doSecurityAndScreeningValidations(operationalFlightVO,containerVOs,bulkContainers,flightValidationVOs);
	}

 	 
	 @Test
	 public void testGenerateAutomaticBarrowID() throws GenerationFailedException, SystemException {
		
		 doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		 spy.generateAutomaticBarrowId("CC");
	 }
	 
	 @Test
	 public void testGenerateAutomaticBarrowIDThrowsSystemException() throws GenerationFailedException, SystemException {
		 doThrow(new SystemException("Exception")).when(keyUtils).getKey(any(Criterion.class));
		 assertTrue(spy.generateAutomaticBarrowId("CC").length()>2);
	 }
	 
	 @Test
	 public void testGenerateAutomaticBarrowIDThrowsGenerationFailedException() throws GenerationFailedException, SystemException {
		 doThrow(new GenerationFailedException("Exception")).when(keyUtils).getKey(any(Criterion.class));
		 assertTrue(spy.generateAutomaticBarrowId("CC").length()>2);
	 }
	 
	 @Test
	 public void testGenerateAutomaticBarrowIDWithReset() throws GenerationFailedException, SystemException {
		 doReturn("9999999").when(keyUtils).getKey(any(Criterion.class));
		 spy.generateAutomaticBarrowId("CC");
		 verify(keyUtils, times(1)).resetKey(any(Criterion.class), anyString());
	 }
	 
	 @Test
	 public void testGenerateAutomaticBarrowIDResetThrowsSystemException() throws GenerationFailedException, SystemException {
		 doReturn("9999999").when(keyUtils).getKey(any(Criterion.class));
		 doThrow(new SystemException("Exception")).when(keyUtils).resetKey(any(Criterion.class), anyString());
		 spy.generateAutomaticBarrowId("CC");	 
	 }
	 
	 @Test
	 public void testGenerateAutomaticBarrowIDResetThrowsGenerationFailedException() throws GenerationFailedException, SystemException {
		 doReturn("9999999").when(keyUtils).getKey(any(Criterion.class));
		 doThrow(new GenerationFailedException("Exception")).when(keyUtils).resetKey(any(Criterion.class), anyString());
		 spy.generateAutomaticBarrowId("CC");
		 verify(keyUtils, times(1)).resetKey(any(Criterion.class), anyString());
	 }
	 @Test
	 public void findCN46TransferManifestDetails() throws PersistenceException, SystemException {
		 Collection<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
			ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
			TransferManifestVO transferManifestVO = new TransferManifestVO();
			consignmentDocumentVOs.add(consignmentDocumentVO);
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(consignmentDocumentVOs).when(dao).findCN46TransferManifestDetails(any(TransferManifestVO.class));
			spy.findCN46TransferManifestDetails(transferManifestVO);
	 }
	 
		 @Test
		public void fetchFlightPreAdviceDetailsTest() throws PersistenceException, SystemException{
		 

			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			Collection<FlightFilterVO>  flightFilterVOs= new ArrayList<>();
			flightFilterVO.setCompanyCode(getCompanyCode());
			flightFilterVO.setFlightCarrierId(183);
			flightFilterVO.setFlightNumber("0712");
			flightFilterVO.setFlightSequenceNumber(12);
			spy.fetchFlightPreAdviceDetails(flightFilterVOs);
			verify(dao, times(1)).fetchFlightPreAdviceDetails(flightFilterVOs);
		
	 }
		@Test(expected = SystemException.class)
		public void fetchFlightPreAdviceDetailsTest_ThrowsException() throws  SystemException,PersistenceException{
		 

			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			Collection<FlightFilterVO>  flightFilterVOs= new ArrayList<>();
			flightFilterVO.setCompanyCode(getCompanyCode());
			flightFilterVO.setFlightCarrierId(183);
			flightFilterVO.setFlightNumber(null);
			flightFilterVO.setFlightSequenceNumber(12);
			doThrow(PersistenceException.class).when(dao).fetchFlightPreAdviceDetails(flightFilterVOs);

			spy.fetchFlightPreAdviceDetails(flightFilterVOs);
		
	 }

	@Test
	public void shouldNotUpdateActualWeightForMailContainer_When_ContainerNumberIsNull() throws Exception {
		ContainerVO containerVO = new ContainerVO();
		ContainerVO updatedContainerVO = spy.updateActualWeightForMailContainer(containerVO);
		assertNull(updatedContainerVO.getContainerNumber());
	}

	@Test
	public void shouldNotUpdateActualWeightForMailContainer_When_ContainerNumberIsEmpty() throws Exception {
		ContainerVO containerVO = new ContainerVO();
		containerVO.setContainerNumber("");
		ContainerVO updatedContainerVO = spy.updateActualWeightForMailContainer(containerVO);
	}

	@Test
	public void shouldNotUpdateActualWeightForMailContainer_When_ActualWeightIsNull() throws Exception {
		ContainerVO containerVO = new ContainerVO();
		containerVO.setContainerNumber("AKE12032QF");
		ContainerVO updatedContainerVO = spy.updateActualWeightForMailContainer(containerVO);
		assertNull(updatedContainerVO.getActualWeight());
	}

	
	@Test
	public void shouldFindFlightsForMailInboundAutoAttachAWB() throws Exception {
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		spy.findFlightsForMailInboundAutoAttachAWB(any());
		verify(dao, times(1)).findFlightsForMailInboundAutoAttachAWB(any());
	}	
	@Test
	public void shouldNotPopulateShipmentCustomsInformationVOsForACS_When_SystemExceptionOccurs() throws Exception{
		ShipmentDetailVO shipmentDetailVO = new ShipmentDetailVO();
		shipmentDetailVO.setCompanyCode(getCompanyCode());
		SystemException se = new SystemException(SystemException.DATABASE_UNAVAILABLE);
		doThrow(se).when(sharedAreaProxy).validateAirportCodes(any(), any());
		spy.validateAndPopulateShipmentCustomsInformationVOsForACS(shipmentDetailVO);
		assertNull(shipmentDetailVO.getShipmentCustomsInformationVOs());
 }
	@Test
	public void shouldNotPopulateShipmentCustomsInformationVOsForACS_When_CountryCodeAbsentForOrigin() throws Exception{
		ShipmentDetailVO shipmentDetailVO = new ShipmentDetailVO();
		shipmentDetailVO.setCompanyCode(getCompanyCode());
		shipmentDetailVO.setOrigin("SYD");
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		countryCodeMap.put("SYD", null);
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(), any());
		spy.validateAndPopulateShipmentCustomsInformationVOsForACS(shipmentDetailVO);
		assertNull(shipmentDetailVO.getShipmentCustomsInformationVOs());
	}
	@Test
	public void shouldNotPopulateShipmentCustomsInformationVOsForACS_When_CountryCodeNotAUForOrigin() throws Exception{
		ShipmentDetailVO shipmentDetailVO = new ShipmentDetailVO();
		shipmentDetailVO.setCompanyCode(getCompanyCode());
		shipmentDetailVO.setOrigin("SYD");
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("IN");
		countryCodeMap.put("SYD", airportValidationVO);
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(), any());
		spy.validateAndPopulateShipmentCustomsInformationVOsForACS(shipmentDetailVO);
		assertNull(shipmentDetailVO.getShipmentCustomsInformationVOs());
	}
	@Test
	public void shouldNotPopulateShipmentCustomsInformationVOsForACS_When_CountryCodeAbsentForDestination() throws Exception{
		ShipmentDetailVO shipmentDetailVO = new ShipmentDetailVO();
		shipmentDetailVO.setCompanyCode(getCompanyCode());
		shipmentDetailVO.setOrigin("SYD");
		shipmentDetailVO.setDestination("MEL");
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("AU");
		countryCodeMap.put("SYD", airportValidationVO);
		countryCodeMap.put("MEL", null);
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(), any());
		spy.validateAndPopulateShipmentCustomsInformationVOsForACS(shipmentDetailVO);
		assertNull(shipmentDetailVO.getShipmentCustomsInformationVOs());
	}
	@Test
	public void shouldNotPopulateShipmentCustomsInformationVOsForACS_When_CountryCodeAUForDestination() throws Exception{
		ShipmentDetailVO shipmentDetailVO = new ShipmentDetailVO();
		shipmentDetailVO.setCompanyCode(getCompanyCode());
		shipmentDetailVO.setOrigin("SYD");
		shipmentDetailVO.setDestination("MEL");
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("AU");
		countryCodeMap.put("SYD", airportValidationVO);
		airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("AU");
		countryCodeMap.put("MEL", airportValidationVO);
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(), any());
		spy.validateAndPopulateShipmentCustomsInformationVOsForACS(shipmentDetailVO);
		assertNull(shipmentDetailVO.getShipmentCustomsInformationVOs());
	}
	@Test
	public void shouldPopulateShipmentCustomsInformationVOsForACS() throws Exception{
		ShipmentDetailVO shipmentDetailVO = new ShipmentDetailVO();
		shipmentDetailVO.setCompanyCode(getCompanyCode());
		shipmentDetailVO.setOrigin("SYD");
		shipmentDetailVO.setDestination("LAX");
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("AU");
		countryCodeMap.put("SYD", airportValidationVO);
		airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		countryCodeMap.put("LAX", airportValidationVO);
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(), any());
		spy.validateAndPopulateShipmentCustomsInformationVOsForACS(shipmentDetailVO);
		assertNotNull(shipmentDetailVO.getShipmentCustomsInformationVOs());
	}
	@Test(expected=SystemException.class)
	public void shouldNotPopulateShipmentCustomsInformationVOsForACSFromAttachAWBDetails() throws Exception{
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode(getCompanyCode());
		ContainerDetailsVO containerDetailsVOFromScreen = new ContainerDetailsVO();
		containerDetailsVOFromScreen.setCompanyCode(getCompanyCode());
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(getCompanyCode());
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		containerDetailsVO.setMailDetails(Stream.of(mailbagVO).collect(Collectors.toList()));
		MailManifestVO mailManifestVO = new MailManifestVO();
		mailManifestVO.setCompanyCode(getCompanyCode());
		mailManifestVO.setContainerDetails(new ArrayList<>());
		Mailbag mailbag = new Mailbag();
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(1);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(Stream.of(containerDetailsVO).collect(Collectors.toList())).when(dao).findMailbagsInContainerWithoutAcceptance(any());
		doReturn(null).when(sharedCustomerProxy).getAllCustomerDetails(any());
		doReturn(null).when(sharedCustomerProxy).findCustomers(any());
		doReturn(null).when(operationsShipmentProxy).saveShipmentDetails(any());
		doReturn(mailManifestVO).when(dao).findContainersInFlightForManifest(any());
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class),any(AssignedFlightPK.class));
		MailTrackingBusinessException mailTrackingBusinessException = new MailTrackingBusinessException(MailTrackingBusinessException.MAILTRACKING_CONTAINERID_MISSING);
		doThrow(mailTrackingBusinessException).when(autoAttachAWBDetailsFeature).execute(any());
		spy.attachAWBDetails(awbDetailVO, containerDetailsVOFromScreen);
		verify(mailControllerMock, times(0)).validateAndPopulateShipmentCustomsInformationVOsForACS(any());
	}		
	@Test(expected=SystemException.class)
	public void shouldPopulateShipmentCustomsInformationVOsForACSFromAttachAWBDetails() throws Exception{
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode(getCompanyCode());
		ContainerDetailsVO containerDetailsVOFromScreen = new ContainerDetailsVO();
		containerDetailsVOFromScreen.setCompanyCode(getCompanyCode());
		containerDetailsVOFromScreen.setFromScreen(MailConstantsVO.MAILOUTBOUND_SCREEN);
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(getCompanyCode());
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		containerDetailsVO.setMailDetails(Stream.of(mailbagVO).collect(Collectors.toList()));
		MailManifestVO mailManifestVO = new MailManifestVO();
		mailManifestVO.setCompanyCode(getCompanyCode());
		mailManifestVO.setContainerDetails(new ArrayList<>());
		Mailbag mailbag = new Mailbag();
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(1);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(Stream.of(containerDetailsVO).collect(Collectors.toList())).when(dao).findMailbagsInContainerWithoutAcceptance(any());
		doReturn(null).when(sharedCustomerProxy).getAllCustomerDetails(any());
		doReturn(null).when(sharedCustomerProxy).findCustomers(any());
		doReturn(null).when(operationsShipmentProxy).saveShipmentDetails(any());
		doReturn(mailManifestVO).when(dao).findContainersInFlightForManifest(any());
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class),any(AssignedFlightPK.class));
		MailTrackingBusinessException mailTrackingBusinessException = new MailTrackingBusinessException(MailTrackingBusinessException.MAILTRACKING_CONTAINERID_MISSING);
		doThrow(mailTrackingBusinessException).when(autoAttachAWBDetailsFeature).execute(any());
		spy.attachAWBDetails(awbDetailVO, containerDetailsVOFromScreen);
		verify(mailControllerMock, times(1)).validateAndPopulateShipmentCustomsInformationVOsForACS(any());
	}	
	
	@Test
	public void constructOperationalFlightVO_pol() {
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol("DFW");
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		spy.constructOperationalFlightVO(containerVO);
	
	}
	@Test
	public void constructOperationalFlightVO_pol_Null() {
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		spy.constructOperationalFlightVO(containerVO);
	
	}
	
	@Test
	public void constructContainerDetailsVO_contentID() {
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId("MAIL");
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("7856");
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		spy.constructContainerDetailsVO(containerVO,toFlightVO);
	
	}
	
	@Test
	public void constructContainerDetailsVO_contentID_null() {
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("7856");
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		spy.constructContainerDetailsVO(containerVO,toFlightVO);
	
	}
	
	@Test
	public void calculateContentID() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		containerDetailsVO.setMailDetails(null);
		containerDetailsVO.setCompanyCode("AA");
		containerDetailsVOs.add(containerDetailsVO);
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE45678AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("7856");
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		doReturn(containerDetailsVOs).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn("M").when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	@Test
	public void calculateContentID_containerDetailVONull() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode("AA");
		containerDetailsVOs.add(containerDetailsVO);
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE45678AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("7856");
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		doReturn(null).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn("M").when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	@Test
	public void calculateContentID_containerNumberNull() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setCompanyCode("AA");
		mailbagVos.add(mailbagVO);
		containerDetailsVO.setCompanyCode("AA");
		containerDetailsVO.setMailDetails(mailbagVos);
		containerDetailsVOs.add(containerDetailsVO);
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber(null);
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("7856");
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		doReturn(containerDetailsVOs).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn("M").when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	
	@Test
	public void calculateContentID_containerDetailNull() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		containerDetailsVO.setCompanyCode("AA");
		containerDetailsVO.setMailDetails(mailbagVos);
		containerDetailsVOs.add(containerDetailsVO);
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE78622AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("7856");
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		doReturn(containerDetailsVOs).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn(null).when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	@Test
	public void calculateContentID_ForLH() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		containerDetailsVO.setCompanyCode("LH");
		containerDetailsVO.setMailDetails(mailbagVos);
		containerDetailsVOs.add(containerDetailsVO);
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("LH");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE78622AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
	    OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("LH");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("7856");
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		doReturn(containerDetailsVOs).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn("M").when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	@Test
	public void calculateContentID_mailbagVosNull() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		containerDetailsVO.setCompanyCode("AA");
		containerDetailsVO.setMailDetails(null);
		containerDetailsVOs.add(containerDetailsVO);
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE78622AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("7856");
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		doReturn(null).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn(null).when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,null);
	
	}
	@Test
	public void calculateContentID_flightNumberNull() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		containerDetailsVO.setCompanyCode("AA");
		containerDetailsVO.setMailDetails(null);
		containerDetailsVOs.add(containerDetailsVO);
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE78622AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber(null);
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		doReturn(null).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn(null).when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	
	@Test
	public void calculateContentID_Carrier() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		containerDetailsVO.setCompanyCode("AA");
		containerDetailsVO.setMailDetails(null);
		containerDetailsVOs.add(containerDetailsVO);
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE78622AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("9809");
		toFlightVO.setFlightSequenceNumber(0);
		toFlightVO.setLegSerialNumber(1);
		doReturn(null).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn(null).when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(null,toFlightVO);
	
	}
	
	@Test
	public void calculateContentID_ContainerVONull() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		containerDetailsVO.setCompanyCode("AA");
		containerDetailsVO.setMailDetails(null);
		containerDetailsVOs.add(containerDetailsVO);
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE78622AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("9809");
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		doReturn(null).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn(null).when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(null,toFlightVO);
	
	}
	
	@Test
	public void calculateContentID_ContainerVOEmpty() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		containerDetailsVO.setCompanyCode("AA");
		containerDetailsVO.setMailDetails(null);
		containerDetailsVOs.add(containerDetailsVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber("9809");
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		doReturn(null).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn(null).when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	@Test
	public void calculateContentID_EmptyContainerDetailVO() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE78622AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber(null);
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		toFlightVO.setPou("LAX");
		doReturn(containerDetailsVOs).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn(null).when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	@Test
	public void calculateContentID_PouNull() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE78622AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber(null);
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		toFlightVO.setPou(null);
		doReturn(containerDetailsVOs).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn(null).when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	
	@Test
	public void calculateContentID_PouEmpty() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE78622AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber(null);
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		toFlightVO.setPou("");
		doReturn(containerDetailsVOs).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn(null).when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	@Test
	public void calculateContentID_EmptyContainerDetailVONull() throws Exception{
		Collection<ContainerVO> containerVOs=new ArrayList<>();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO=new ContainerDetailsVO();
		Collection<MailbagVO> mailbagVos=new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode("AA");
		containerVO.setPol(null);
		containerVO.setPou("LAX");
		containerVO.setCarrierId(1001);
		containerVO.setContainerNumber("AKE78622AA");
		containerVO.setFlightNumber("7851");
		containerVO.setFlightSequenceNumber(1);
		containerVO.setLegSerialNumber(1);
		containerVO.setContentId(null);
		containerVOs.add(containerVO);
		OperationalFlightVO toFlightVO=new OperationalFlightVO();
		toFlightVO.setCompanyCode("AA");
		toFlightVO.setPol("DFW");
		toFlightVO.setCarrierId(1001);
		toFlightVO.setFlightNumber(null);
		toFlightVO.setFlightSequenceNumber(1);
		toFlightVO.setLegSerialNumber(1);
		toFlightVO.setPou("LAX");
		doReturn(null).when(spy).findMailbagsInContainer(any());
		aamailControllerBean = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
		doReturn(null).when(aamailControllerBean).calculateULDContentId(any(),any());
		spy.calculateContentID(containerVOs,toFlightVO);
	
	}
	

	@Test
    public void findMailTransitTest() throws SystemException,PersistenceException {



        MailTransitFilterVO mailTransitFilterVO = new MailTransitFilterVO();
        Page<MailTransitVO> mailTransitVOs = new Page<>();
        MailTransitVO mailTransitVO = new MailTransitVO();
        int pageNumber = 1;
        mailTransitVOs.add(mailTransitVO);
        doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
        doReturn(mailTransitVOs).when(dao).findMailTransit(mailTransitFilterVO,pageNumber);
        spy.findMailTransit(mailTransitFilterVO,pageNumber);



    }
	
	@Test
    public void findMailbagNotesTest() throws SystemException,PersistenceException {
		MailHistoryRemarksVO mailHistoryRemarksVO = new MailHistoryRemarksVO();
		Collection<MailHistoryRemarksVO> mailHistoryRemarksVOs = new ArrayList<>();
		String mailBagId = "DEFRAAUSORDAAUN17878501000133";
        mailHistoryRemarksVOs.add(mailHistoryRemarksVO);
        doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
        doReturn(mailHistoryRemarksVOs).when(dao).findMailbagNotes(mailBagId);
        spy.findMailbagNotes(mailBagId);
    }
	@Test(expected = SystemException.class)
    public void findMailbagNotesTest_Exception() throws SystemException,PersistenceException {
		MailHistoryRemarksVO mailHistoryRemarksVO = new MailHistoryRemarksVO();
		Collection<MailHistoryRemarksVO> mailHistoryRemarksVOs = new ArrayList<>();
		String mailBagId = "DEFRAAUSORDAAUN17878501000133";
        mailHistoryRemarksVOs.add(mailHistoryRemarksVO);
        doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
        doThrow(PersistenceException.class).when(dao).findMailbagNotes(any(String.class));
        spy.findMailbagNotes(mailBagId);
    }
	
	@Test(expected=SystemException.class)
	public void findMailbagDetailsForMailInboundHHTExceptionTest() throws Exception{
		
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		
		mailbagEnquiryFilterVO.setCompanyCode("AA");
	    mailbagEnquiryFilterVO.setMailbagId("TESTMAILID");
	    mailbagEnquiryFilterVO.setScanPort("DFW");
	    
	    
	    doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
	    doThrow(PersistenceException.class).when(dao).findMailbagDetailsForMailInboundHHT(any());
	    spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
	
	}
	
	@Test
	public void findMailbagDetailsForMailInboundHHTTest() throws Exception{
		
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		
		mailbagEnquiryFilterVO.setCompanyCode("AA");
	    mailbagEnquiryFilterVO.setMailbagId("TESTMAILID");
	    mailbagEnquiryFilterVO.setScanPort("DFW");
	    
	    
	    MailbagVO mailbagVO = null;
	    
	    doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).findMailbagDetailsForMailInboundHHT(any());
		spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
		mailbagVO = new MailbagVO();
		mailbagVO.setLatestStatus("ARR");
		mailbagVO.setFlightNumber("0123");
		mailbagVO.setFlightDate(new LocalDate("CDG", Location.ARP,true)) ;
		mailbagVO.setFlightSequenceNumber(123);
		mailbagVO.setCarrierId(1001);
		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).findMailbagDetailsForMailInboundHHT(any());
		spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
		Collection<FlightValidationVO> flightValidationVos = new ArrayList<>();
		FlightValidationVO flightValidationVo = new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).findMailbagDetailsForMailInboundHHT(any());
		spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
	}
	
	@Test
	public void findMailbagDetailsForMailInboundHHTTestReturnValidationVOs() throws Exception{
		
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		
		mailbagEnquiryFilterVO.setCompanyCode("AA");
	    mailbagEnquiryFilterVO.setMailbagId("TESTMAILID");
	    mailbagEnquiryFilterVO.setScanPort("DFW");
	    
	    
	    MailbagVO mailbagVO = null;
	    
		
		mailbagVO = new MailbagVO();
		mailbagVO.setLatestStatus("ARR");
		mailbagVO.setFlightNumber("0123");
		mailbagVO.setFlightDate(new LocalDate("CDG", Location.ARP,true)) ;
		mailbagVO.setFlightSequenceNumber(123);
		mailbagVO.setCarrierId(1001);
		
		Collection<FlightValidationVO> flightValidationVos = new ArrayList<>();
		FlightValidationVO flightValidationVo = new FlightValidationVO();
		flightValidationVo.setFlightOwner("LH");
		flightValidationVos.add(flightValidationVo);
		doReturn(flightValidationVos).when(flightOperationsProxy).validateFlight(any(FlightFilterVO.class));
		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).findMailbagDetailsForMailInboundHHT(any());
		spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
	}
	
	@Test
	public void findMailbagDetailsForMailInboundHHTTestNegativeScenarios() throws Exception{
		
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		
		mailbagEnquiryFilterVO.setCompanyCode("AA");
	    mailbagEnquiryFilterVO.setMailbagId("TESTMAILID");
	    mailbagEnquiryFilterVO.setScanPort("DFW");
	    
	    
	    MailbagVO mailbagVO = null;
	    
		
		mailbagVO = new MailbagVO();
		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).findMailbagDetailsForMailInboundHHT(any());
		spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
		mailbagVO.setLatestStatus("RRR");
		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).findMailbagDetailsForMailInboundHHT(any());
		spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
		mailbagVO.setLatestStatus("ARR");
		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).findMailbagDetailsForMailInboundHHT(any());
		spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
		mailbagVO.setFlightNumber("0123");
		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).findMailbagDetailsForMailInboundHHT(any());
		spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
		mailbagVO.setFlightDate(new LocalDate("CDG", Location.ARP,true)) ;
		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).findMailbagDetailsForMailInboundHHT(any());
		spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
		mailbagVO.setFlightSequenceNumber(123);
		mailbagVO.setCarrierId(1001);
		
		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagVO).when(dao).findMailbagDetailsForMailInboundHHT(any());
		spy.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		
	}
	
	  @Test
	    public void findFlightListingsTest() throws SystemException,PersistenceException, ProxyException {
			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setFromDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
			flightFilterVO.setToDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
			flightFilterVO.setSegmentOrigin("MUC");
			flightFilterVO.setCarrierCode("LH");
			flightFilterVO.setSegmentDestination("LAX");
			flightFilterVO.setCompanyCode("LH"); 
			Collection<FlightSegmentCapacitySummaryVO> flights=new ArrayList<>();
			FlightSegmentCapacitySummaryVO flight=new FlightSegmentCapacitySummaryVO();
			flight.setSegmentDestination("LAX");
			flight.setSegmentOrigin("MUC");
			flights.add(flight);
			doReturn(flights).when(flightOperationsProxy).findFlightListings(any(FlightFilterVO.class));
	        spy.findFlightListings(flightFilterVO);
	    }
	
		@Test
	    public void findActiveAllotmentsTest() throws SystemException,PersistenceException, ProxyException {
			FlightSegmentCapacityFilterVO flightSegmentCapacityFilterVO = new FlightSegmentCapacityFilterVO();
			flightSegmentCapacityFilterVO.setFromDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
			flightSegmentCapacityFilterVO.setToDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
			flightSegmentCapacityFilterVO.setSegmentOrigin("MUC");
			flightSegmentCapacityFilterVO.setSegmentDestination("LAX");
			flightSegmentCapacityFilterVO.setCompanyCode("LH"); 
			flightSegmentCapacityFilterVO.setAllotmentSubType("M");
			flightSegmentCapacityFilterVO.setPageNumber(1);
			Page<FlightSegmentCapacitySummaryVO> mailConsumed = new Page();
			doReturn(mailConsumed).when(flightOperationsProxy).findActiveAllotments(flightSegmentCapacityFilterVO);
	        spy.findActiveAllotments(flightSegmentCapacityFilterVO);
	    }
		
		@Test
	    public void findMailConsumedTest() throws SystemException,PersistenceException {
	        MailTransitFilterVO mailTransitFilterVO = new MailTransitFilterVO();
	        mailTransitFilterVO.setFromDate("23-03-2023");
	        mailTransitFilterVO.setToDate("23-03-2023");
	        mailTransitFilterVO.setAirportCode("MUC");
	        mailTransitFilterVO.setSegmentDestination("LAX");
	        MailbagVO mailbagvO = new MailbagVO();
	        mailbagvO.setWeight(new Measure(UnitConstants.WEIGHT, 800));
	        doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
	        doReturn(mailbagvO).when(dao).findMailConsumed(mailTransitFilterVO);
	        spy.findMailConsumed(mailTransitFilterVO);

	    }
		
		@Test
		public void shouldNotCalculateAndSaveContentIdWhenMailAcceptanceVOIsNull() throws Exception{
			spy.calculateAndSaveContentId(null);
		}	
	
		@Test
		public void shouldNotCalculateAndSaveContentIdForCarrier() throws Exception {
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			mailAcceptanceVO.setFlightSequenceNumber(-1);
			spy.calculateAndSaveContentId(mailAcceptanceVO);
			assertNull(mailAcceptanceVO.getContainerDetails());
		}
	
		@Test
		public void shouldNotCalculateAndSaveContentIdWhenContainerDetailsIsNull() throws Exception {
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			mailAcceptanceVO.setFlightSequenceNumber(1);
			spy.calculateAndSaveContentId(mailAcceptanceVO);
			assertNull(mailAcceptanceVO.getContainerDetails());
		}
	
		@Test
		public void shouldNotCalculateAndSaveContentIdWhenContainerDetailsIsEmpty() throws Exception {
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			mailAcceptanceVO.setFlightSequenceNumber(1);
			mailAcceptanceVO.setContainerDetails(new ArrayList<>());
			spy.calculateAndSaveContentId(mailAcceptanceVO);
			assertTrue(mailAcceptanceVO.getContainerDetails().isEmpty());
		}
	
		@Test
		public void shouldCalculateAndSaveContentId() throws Exception {
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			mailAcceptanceVO.setFlightSequenceNumber(1);
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setCompanyCode(getCompanyCode());
			mailAcceptanceVO.setContainerDetails(Stream.of(containerDetailsVO).collect(Collectors.toList()));
			ContainerPK containerPK = new ContainerPK();
			containerPK.setCompanyCode(getCompanyCode());
			containerEntity.setContainerPK(containerPK);
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class),
					any(ContainerPK.class));
			spy.calculateAndSaveContentId(mailAcceptanceVO);
			assertFalse(mailAcceptanceVO.getContainerDetails().isEmpty());
		}
		@Test
		public void findMailbagHistoriesFromWebScreen() throws Exception {
			Collection<MailbagHistoryVO> MailbagHistoryVOs = new ArrayList<>();
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(MailbagHistoryVOs).when(dao).findMailbagHistories(COMPANY_CODE,MAILBAG_ID,1L,"A");
			spy.findMailbagHistoriesFromWebScreen(COMPANY_CODE,MAILBAG_ID,1);
		}
		@Test(expected=SystemException.class)
		public void findMailbagHistoriesFromWebScreen_throwsException() throws Exception {
			Collection<MailbagHistoryVO> MailbagHistoryVOs = new ArrayList<>();
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doThrow(PersistenceException.class).when(dao).findMailbagHistories(any(String.class),any(String.class),any(Long.class),any(String.class));
			
			spy.findMailbagHistoriesFromWebScreen(any(String.class),any(String.class),any(Long.class));
		}
		
		@Test
		public void testFlagAuditForMailOperartions() throws SystemException{
			spy.flagAuditForMailOperartions(new ArrayList<>(), "DELIVERED");
		}
		@Test
		public void whenSelectedResditsIsNotNullInSaveMalRdtMsgAddDtlTest() throws Exception{
			Collection<MessageDespatchDetailsVO> messageAddressDetails=new ArrayList();
			MessageDespatchDetailsVO messageDespatchDetailsVO=new MessageDespatchDetailsVO();
			messageDespatchDetailsVO.setInterfaceSystem("ICS");
			messageDespatchDetailsVO.setMode("L");
			messageDespatchDetailsVO.setAddress("Test");
			messageDespatchDetailsVO.setEnvelopeCode("ICOXML");
			messageDespatchDetailsVO.setEnvelopeAddress("ABCD~P");
			messageAddressDetails.add(messageDespatchDetailsVO);
			Collection<AutoForwardDetailsVO> participantDetails=new ArrayList();
			AutoForwardDetailsVO participantDetail=new AutoForwardDetailsVO();
			participantDetail.setInterfaceSystem("ICS");
			participantDetail.setParticipantName("HQMAIL");
			participantDetail.setParticipantType("AGENT");
			participantDetail.setAirportCode("FRA");
			participantDetail.setCountryCode("DE");
			participantDetails.add(participantDetail);
			MessageVO messageVo =new MessageVO();
			messageVo.setMessageType("IFTSTA");
			messageVo.setMessageVersion("1");
			String selectedResditVersion="1.1 M39";
			List<String>selectedResdits=new ArrayList();
			selectedResdits.add("6");
			Collection<MailbagVO> selectedMailbags=new ArrayList();
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode("AV");
			mailbagVO.setPaCode("FR001");
			mailbagVO.setSequenceNumber(14772);
			mailbagVO.setUpliftAirport("CDG");
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
			mailbagVO.setCarrierId(1134);
			mailbagVO.setFlightNumber("6688");
			mailbagVO.setFlightSequenceNumber(16);
			mailbagVO.setContainerNumber("AKE34334AV");
			mailbagVO.setMailboxId("FR001");
			mailbagVO.setLastUpdateUser("AV");
			selectedMailbags.add(mailbagVO);
			String mailBoxID="FR001";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
			Collection<MailbagHistoryVO> mailbagHistories = new ArrayList();
			MailbagHistoryVO mailbagHistoryVO= new MailbagHistoryVO();
			mailbagHistoryVO.setAdditionalInfo("true");
			mailbagHistoryVO.setEventCode("6");
			mailbagHistories.add(mailbagHistoryVO);
			mailbagVO.setMailbagHistories(mailbagHistories);
			String messageAddressSequenceNumber="123";
			resditController.flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber, mailbagHistoryVO.getEventCode(), mailbagVO);
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion, selectedResdits, selectedMailbags);
		}
		@Test
		public void whenSelectedResditsIsNullInSaveMalRdtMsgAddDtlTest() throws Exception{
			Collection<MessageDespatchDetailsVO> messageAddressDetails=new ArrayList();
			MessageDespatchDetailsVO messageDespatchDetailsVO=new MessageDespatchDetailsVO();
			messageDespatchDetailsVO.setInterfaceSystem("ICS");
			messageDespatchDetailsVO.setMode("L");
			messageDespatchDetailsVO.setAddress("Test");
			messageDespatchDetailsVO.setEnvelopeCode("ICOXML");
			messageDespatchDetailsVO.setEnvelopeAddress("ABCD~P");
			messageAddressDetails.add(messageDespatchDetailsVO);
			Collection<AutoForwardDetailsVO> participantDetails=new ArrayList();
			AutoForwardDetailsVO participantDetail=new AutoForwardDetailsVO();
			participantDetail.setInterfaceSystem("ICS");
			participantDetail.setParticipantName("HQMAIL");
			participantDetail.setParticipantType("AGENT");
			participantDetail.setAirportCode("FRA");
			participantDetail.setCountryCode("DE");
			participantDetails.add(participantDetail);
			MessageVO messageVo =new MessageVO();
			messageVo.setMessageType("IFTSTA");
			messageVo.setMessageVersion("1");
			String selectedResditVersion="1.1 M39";
			List<String>selectedResdits=new ArrayList<>();
			selectedResdits.add("74");
			Collection<MailbagVO> selectedMailbags=new ArrayList();
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode("AV");
			mailbagVO.setPaCode("FR001");
			mailbagVO.setSequenceNumber(14772);
			mailbagVO.setUpliftAirport("CDG");
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
			mailbagVO.setCarrierId(1134);
			mailbagVO.setFlightNumber("6688");
			mailbagVO.setFlightSequenceNumber(16);
			mailbagVO.setContainerNumber("AKE34334AV");
			mailbagVO.setMailboxId("FR001");
			mailbagVO.setLastUpdateUser("AV");
			selectedMailbags.add(mailbagVO);
			String mailBoxID="FR001";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
			Collection<MailbagHistoryVO> mailbagHistories = new ArrayList();
			MailbagHistoryVO mailbagHistoryVO= new MailbagHistoryVO();
			mailbagHistoryVO.setAdditionalInfo("true");
			mailbagHistoryVO.setEventCode("6");
			mailbagHistories.add(mailbagHistoryVO);
			mailbagVO.setMailbagHistories(mailbagHistories);
			String messageAddressSequenceNumber="123";
			resditController.flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber, mailbagHistoryVO.getEventCode(), mailbagVO);
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion, selectedResdits, selectedMailbags);
		}
		@Test
		public void whenSelectedResditsIsEmptyInSaveMalRdtMsgAddDtlTest() throws Exception{
			Collection<MessageDespatchDetailsVO> messageAddressDetails=new ArrayList();
			MessageDespatchDetailsVO messageDespatchDetailsVO=new MessageDespatchDetailsVO();
			messageDespatchDetailsVO.setInterfaceSystem("ICS");
			messageDespatchDetailsVO.setMode("L");
			messageDespatchDetailsVO.setAddress("Test");
			messageDespatchDetailsVO.setEnvelopeCode("ICOXML");
			messageDespatchDetailsVO.setEnvelopeAddress("ABCD~P");
			messageAddressDetails.add(messageDespatchDetailsVO);
			Collection<AutoForwardDetailsVO> participantDetails=new ArrayList();
			AutoForwardDetailsVO participantDetail=new AutoForwardDetailsVO();
			participantDetail.setInterfaceSystem("ICS");
			participantDetail.setParticipantName("HQMAIL");
			participantDetail.setParticipantType("AGENT");
			participantDetail.setAirportCode("FRA");
			participantDetail.setCountryCode("DE");
			participantDetails.add(participantDetail);
			MessageVO messageVo =new MessageVO();
			messageVo.setMessageType("IFTSTA");
			messageVo.setMessageVersion("1");
			String selectedResditVersion="1.1 M39";
			List<String>selectedResdits=new ArrayList();
			Collection<MailbagVO> selectedMailbags=new ArrayList();
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode("AV");
			mailbagVO.setPaCode("FR001");
			mailbagVO.setSequenceNumber(14772);
			mailbagVO.setUpliftAirport("CDG");
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
			mailbagVO.setCarrierId(1134);
			mailbagVO.setFlightNumber("6688");
			mailbagVO.setFlightSequenceNumber(16);
			mailbagVO.setContainerNumber("AKE34334AV");
			mailbagVO.setMailboxId("FR001");
			mailbagVO.setLastUpdateUser("AV");
			selectedMailbags.add(mailbagVO);
			String mailBoxID="FR001";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
			Collection<MailbagHistoryVO> mailbagHistories = new ArrayList();
			MailbagHistoryVO mailbagHistoryVO= new MailbagHistoryVO();
			mailbagHistoryVO.setAdditionalInfo("true");
			mailbagHistoryVO.setEventCode("6");
			mailbagHistories.add(mailbagHistoryVO);
			mailbagVO.setMailbagHistories(mailbagHistories);
			String messageAddressSequenceNumber="123";
			resditController.flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber, mailbagHistoryVO.getEventCode(), mailbagVO);
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion, selectedResdits, selectedMailbags);
		}
		@Test(expected=SystemException.class)
		public void throwPersistanceExceptionInSaveMalRdtMsgAddDtlTest() throws Exception{
			Collection<MessageDespatchDetailsVO> messageAddressDetails=new ArrayList();
			MessageDespatchDetailsVO messageDespatchDetailsVO=new MessageDespatchDetailsVO();
			messageDespatchDetailsVO.setInterfaceSystem("ICS");
			messageDespatchDetailsVO.setMode("L");
			messageDespatchDetailsVO.setAddress("Test");
			messageDespatchDetailsVO.setEnvelopeCode("ICOXML");
			messageDespatchDetailsVO.setEnvelopeAddress("ABCD~P");
			messageAddressDetails.add(messageDespatchDetailsVO);
			Collection<AutoForwardDetailsVO> participantDetails=new ArrayList();
			AutoForwardDetailsVO participantDetail=new AutoForwardDetailsVO();
			participantDetail.setInterfaceSystem("ICS");
			participantDetail.setParticipantName("HQMAIL");
			participantDetail.setParticipantType("AGENT");
			participantDetail.setAirportCode("FRA");
			participantDetail.setCountryCode("DE");
			participantDetails.add(participantDetail);
			MessageVO messageVo =new MessageVO();
			messageVo.setMessageType("IFTSTA");
			messageVo.setMessageVersion("1");
			String selectedResditVersion="1.1 M39";
			List<String>selectedResdits=new ArrayList();
			selectedResdits.add("6");
			Collection<MailbagVO> selectedMailbags=new ArrayList();
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode("AV");
			mailbagVO.setPaCode("FR001");
			mailbagVO.setSequenceNumber(14772);
			mailbagVO.setUpliftAirport("CDG");
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
			mailbagVO.setCarrierId(1134);
			mailbagVO.setFlightNumber("6688");
			mailbagVO.setFlightSequenceNumber(16);
			mailbagVO.setContainerNumber("AKE34334AV");
			mailbagVO.setMailboxId("FR001");
			mailbagVO.setLastUpdateUser("AV");
			selectedMailbags.add(mailbagVO);
			String mailBoxID="FR001";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
			doThrow(PersistenceException.class).when(dao).findMailboxIdForPA(any());
	        Collection<MailbagHistoryVO> mailbagHistories = new ArrayList();
			MailbagHistoryVO mailbagHistoryVO= new MailbagHistoryVO();
			mailbagHistoryVO.setAdditionalInfo("true");
			mailbagHistoryVO.setEventCode("6");
			mailbagHistories.add(mailbagHistoryVO);
			mailbagVO.setMailbagHistories(mailbagHistories);
			String messageAddressSequenceNumber="123";
			resditController.flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber, mailbagHistoryVO.getEventCode(), mailbagVO);
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion, selectedResdits, selectedMailbags);
		}
		@Test
		public void whenadditionalInfoOfMailbagHistoeiesIsNotEqualToTheStringInSaveMalRdtMsgAddDtlTest() throws Exception{
			Collection<MessageDespatchDetailsVO> messageAddressDetails=new ArrayList();
			MessageDespatchDetailsVO messageDespatchDetailsVO=new MessageDespatchDetailsVO();
			messageDespatchDetailsVO.setInterfaceSystem("ICS");
			messageDespatchDetailsVO.setMode("L");
			messageDespatchDetailsVO.setAddress("Test");
			messageDespatchDetailsVO.setEnvelopeCode("ICOXML");
			messageDespatchDetailsVO.setEnvelopeAddress("ABCD~P");
			messageAddressDetails.add(messageDespatchDetailsVO);
			Collection<AutoForwardDetailsVO> participantDetails=new ArrayList();
			AutoForwardDetailsVO participantDetail=new AutoForwardDetailsVO();
			participantDetail.setInterfaceSystem("ICS");
			participantDetail.setParticipantName("HQMAIL");
			participantDetail.setParticipantType("AGENT");
			participantDetail.setAirportCode("FRA");
			participantDetail.setCountryCode("DE");
			participantDetails.add(participantDetail);
			MessageVO messageVo =new MessageVO();
			messageVo.setMessageType("IFTSTA");
			messageVo.setMessageVersion("1");
			String selectedResditVersion="1.1 M39";
			List<String>selectedResdits=new ArrayList();
			Collection<MailbagVO> selectedMailbags=new ArrayList();
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode("AV");
			mailbagVO.setPaCode("FR001");
			mailbagVO.setSequenceNumber(14772);
			mailbagVO.setUpliftAirport("CDG");
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
			mailbagVO.setCarrierId(1134);
			mailbagVO.setFlightNumber("6688");
			mailbagVO.setFlightSequenceNumber(16);
			mailbagVO.setContainerNumber("AKE34334AV");
			mailbagVO.setMailboxId("FR001");
			mailbagVO.setLastUpdateUser("AV");
			selectedMailbags.add(mailbagVO);
			String mailBoxID="FR001";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
			Collection<MailbagHistoryVO> mailbagHistories = new ArrayList();
			MailbagHistoryVO mailbagHistoryVO= new MailbagHistoryVO();
			mailbagHistoryVO.setAdditionalInfo(null);
			mailbagHistoryVO.setEventCode("6");
			mailbagHistories.add(mailbagHistoryVO);
			mailbagVO.setMailbagHistories(mailbagHistories);
			String messageAddressSequenceNumber="123";
			resditController.flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber, mailbagHistoryVO.getEventCode(), mailbagVO);
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion, selectedResdits, selectedMailbags);
		}
		@Test
		public void whenEventCodeOfMailbagHistoriesIsNullInSaveMalRdtMsgAddDtlTest() throws Exception{
			Collection<MessageDespatchDetailsVO> messageAddressDetails=new ArrayList();
			MessageDespatchDetailsVO messageDespatchDetailsVO=new MessageDespatchDetailsVO();
			messageDespatchDetailsVO.setInterfaceSystem("ICS");
			messageDespatchDetailsVO.setMode("L");
			messageDespatchDetailsVO.setAddress("Test");
			messageDespatchDetailsVO.setEnvelopeCode("ICOXML");
			messageDespatchDetailsVO.setEnvelopeAddress("ABCD~P");
			messageAddressDetails.add(messageDespatchDetailsVO);
			Collection<AutoForwardDetailsVO> participantDetails=new ArrayList();
			AutoForwardDetailsVO participantDetail=new AutoForwardDetailsVO();
			participantDetail.setInterfaceSystem("ICS");
			participantDetail.setParticipantName("HQMAIL");
			participantDetail.setParticipantType("AGENT");
			participantDetail.setAirportCode("FRA");
			participantDetail.setCountryCode("DE");
			participantDetails.add(participantDetail);
			MessageVO messageVo =new MessageVO();
			messageVo.setMessageType("IFTSTA");
			messageVo.setMessageVersion("1");
			String selectedResditVersion="1.1 M39";
			List<String>selectedResdits=new ArrayList();
			Collection<MailbagVO> selectedMailbags=new ArrayList();
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode("AV");
			mailbagVO.setPaCode("FR001");
			mailbagVO.setSequenceNumber(14772);
			mailbagVO.setUpliftAirport("CDG");
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
			mailbagVO.setCarrierId(1134);
			mailbagVO.setFlightNumber("6688");
			mailbagVO.setFlightSequenceNumber(16);
			mailbagVO.setContainerNumber("AKE34334AV");
			mailbagVO.setMailboxId("FR001");
			mailbagVO.setLastUpdateUser("AV");
			selectedMailbags.add(mailbagVO);
			String mailBoxID="FR001";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
			Collection<MailbagHistoryVO> mailbagHistories = new ArrayList();
			MailbagHistoryVO mailbagHistoryVO= new MailbagHistoryVO();
			mailbagHistoryVO.setAdditionalInfo("true");
			mailbagHistoryVO.setEventCode(null);
			mailbagHistories.add(mailbagHistoryVO);
			mailbagVO.setMailbagHistories(mailbagHistories);
			String messageAddressSequenceNumber="123";
			resditController.flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber, mailbagHistoryVO.getEventCode(), mailbagVO);
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion, selectedResdits, selectedMailbags);
		}
		@Test
		public void whenMessageAddressDetailsIsNullInSaveMalRdtMsgAddDtlTest() throws Exception{
			Collection<MessageDespatchDetailsVO> messageAddressDetails=null;
			Collection<AutoForwardDetailsVO> participantDetails=new ArrayList();
			AutoForwardDetailsVO participantDetail=new AutoForwardDetailsVO();
			participantDetail.setInterfaceSystem("ICS");
			participantDetail.setParticipantName("HQMAIL");
			participantDetail.setParticipantType("AGENT");
			participantDetail.setAirportCode("FRA");
			participantDetail.setCountryCode("DE");
			participantDetails.add(participantDetail);
			MessageVO messageVo =new MessageVO();
			messageVo.setMessageType("IFTSTA");
			messageVo.setMessageVersion("1");
			String selectedResditVersion="1.1 M39";
			List<String>selectedResdits=new ArrayList();
			Collection<MailbagVO> selectedMailbags=new ArrayList();
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode("AV");
			mailbagVO.setPaCode("FR001");
			mailbagVO.setSequenceNumber(14772);
			mailbagVO.setUpliftAirport("CDG");
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
			mailbagVO.setCarrierId(1134);
			mailbagVO.setFlightNumber("6688");
			mailbagVO.setFlightSequenceNumber(16);
			mailbagVO.setContainerNumber("AKE34334AV");
			mailbagVO.setMailboxId("FR001");
			mailbagVO.setLastUpdateUser("AV");
			selectedMailbags.add(mailbagVO);
			String mailBoxID="FR001";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
			Collection<MailbagHistoryVO> mailbagHistories = new ArrayList();
			MailbagHistoryVO mailbagHistoryVO= new MailbagHistoryVO();
			mailbagHistoryVO.setAdditionalInfo("true");
			mailbagHistoryVO.setEventCode("6");
			mailbagHistories.add(mailbagHistoryVO);
			mailbagVO.setMailbagHistories(mailbagHistories);
			String messageAddressSequenceNumber="123";
			resditController.flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber, mailbagHistoryVO.getEventCode(), mailbagVO);
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion, selectedResdits, selectedMailbags);
		}
		@Test
		public void whenMessageAddressDetailsIsEmptyInSaveMalRdtMsgAddDtlTest() throws Exception{
			Collection<MessageDespatchDetailsVO> messageAddressDetails=new ArrayList();
			Collection<AutoForwardDetailsVO> participantDetails=new ArrayList();
			AutoForwardDetailsVO participantDetail=new AutoForwardDetailsVO();
			participantDetail.setInterfaceSystem("ICS");
			participantDetail.setParticipantName("HQMAIL");
			participantDetail.setParticipantType("AGENT");
			participantDetail.setAirportCode("FRA");
			participantDetail.setCountryCode("DE");
			participantDetails.add(participantDetail);
			MessageVO messageVo =new MessageVO();
			messageVo.setMessageType("IFTSTA");
			messageVo.setMessageVersion("1");
			String selectedResditVersion="1.1 M39";
		    List<String>selectedResdits=new ArrayList();
			Collection<MailbagVO> selectedMailbags=new ArrayList();
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode("AV");
			mailbagVO.setPaCode("FR001");
			mailbagVO.setSequenceNumber(14772);
			mailbagVO.setUpliftAirport("CDG");
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
			mailbagVO.setCarrierId(1134);
			mailbagVO.setFlightNumber("6688");
			mailbagVO.setFlightSequenceNumber(16);
			mailbagVO.setContainerNumber("AKE34334AV");
			mailbagVO.setMailboxId("FR001");
			mailbagVO.setLastUpdateUser("AV");
			selectedMailbags.add(mailbagVO);
			String mailBoxID="FR001";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
			Collection<MailbagHistoryVO> mailbagHistories = new ArrayList();
			MailbagHistoryVO mailbagHistoryVO= new MailbagHistoryVO();
			mailbagHistoryVO.setAdditionalInfo("true");
			mailbagHistoryVO.setEventCode("6");
			mailbagHistories.add(mailbagHistoryVO);
			mailbagVO.setMailbagHistories(mailbagHistories);
			String messageAddressSequenceNumber="123";
			resditController.flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber, mailbagHistoryVO.getEventCode(), mailbagVO);
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion, selectedResdits, selectedMailbags);
		}
		@Test
		public void whenParticipantDetailsIsNullInSaveMalRdtMsgAddDtlTest() throws Exception{
			Collection<MessageDespatchDetailsVO> messageAddressDetails=new ArrayList();
			MessageDespatchDetailsVO messageDespatchDetailsVO=new MessageDespatchDetailsVO();
			messageDespatchDetailsVO.setInterfaceSystem("ICS");
			messageDespatchDetailsVO.setMode("L");
			messageDespatchDetailsVO.setAddress("Test");
			messageDespatchDetailsVO.setEnvelopeCode("ICOXML");
			messageDespatchDetailsVO.setEnvelopeAddress("ABCD~P");
			messageAddressDetails.add(messageDespatchDetailsVO);
			Collection<AutoForwardDetailsVO> participantDetails=null;
			MessageVO messageVo =new MessageVO();
			messageVo.setMessageType("IFTSTA");
			messageVo.setMessageVersion("1");
			String selectedResditVersion="1.1 M39";
			List<String>selectedResdits=new ArrayList();
			Collection<MailbagVO> selectedMailbags=new ArrayList();
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode("AV");
			mailbagVO.setPaCode("FR001");
			mailbagVO.setSequenceNumber(14772);
			mailbagVO.setUpliftAirport("CDG");
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
			mailbagVO.setCarrierId(1134);
			mailbagVO.setFlightNumber("6688");
			mailbagVO.setFlightSequenceNumber(16);
			mailbagVO.setContainerNumber("AKE34334AV");
			mailbagVO.setMailboxId("FR001");
			mailbagVO.setLastUpdateUser("AV");
			selectedMailbags.add(mailbagVO);
			String mailBoxID="FR001";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
			Collection<MailbagHistoryVO> mailbagHistories = new ArrayList();
			MailbagHistoryVO mailbagHistoryVO= new MailbagHistoryVO();
			mailbagHistoryVO.setAdditionalInfo("true");
			mailbagHistoryVO.setEventCode(null);
			mailbagHistories.add(mailbagHistoryVO);
			mailbagVO.setMailbagHistories(mailbagHistories);
			String messageAddressSequenceNumber="123";
			resditController.flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber, mailbagHistoryVO.getEventCode(), mailbagVO);
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion, selectedResdits, selectedMailbags);
		}
		@Test
		public void whenParticipantDetailsIsEmptyInSaveMalRdtMsgAddDtlTest() throws Exception{
			Collection<MessageDespatchDetailsVO> messageAddressDetails=new ArrayList();
			MessageDespatchDetailsVO messageDespatchDetailsVO=new MessageDespatchDetailsVO();
			messageDespatchDetailsVO.setInterfaceSystem("ICS");
			messageDespatchDetailsVO.setMode("L");
			messageDespatchDetailsVO.setAddress("Test");
			messageDespatchDetailsVO.setEnvelopeCode("ICOXML");
			messageDespatchDetailsVO.setEnvelopeAddress("ABCD~P");
			messageAddressDetails.add(messageDespatchDetailsVO);
			Collection<AutoForwardDetailsVO> participantDetails=new ArrayList();
			MessageVO messageVo =new MessageVO();
			messageVo.setMessageType("IFTSTA");
			messageVo.setMessageVersion("1");
			String selectedResditVersion="1.1 M39";
			List<String>selectedResdits=new ArrayList();
			Collection<MailbagVO> selectedMailbags=new ArrayList();
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode("AV");
			mailbagVO.setPaCode("FR001");
			mailbagVO.setSequenceNumber(14772);
			mailbagVO.setUpliftAirport("CDG");
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
			mailbagVO.setCarrierId(1134);
			mailbagVO.setFlightNumber("6688");
			mailbagVO.setFlightSequenceNumber(16);
			mailbagVO.setContainerNumber("AKE34334AV");
			mailbagVO.setMailboxId("FR001");
			mailbagVO.setLastUpdateUser("AV");
			selectedMailbags.add(mailbagVO);
			String mailBoxID="FR001";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
			Collection<MailbagHistoryVO> mailbagHistories = new ArrayList();
			MailbagHistoryVO mailbagHistoryVO= new MailbagHistoryVO();
			mailbagHistoryVO.setAdditionalInfo("true");
			mailbagHistoryVO.setEventCode("6");
			mailbagHistories.add(mailbagHistoryVO);
			mailbagVO.setMailbagHistories(mailbagHistories);
			String messageAddressSequenceNumber="123";
			resditController.flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber, mailbagHistoryVO.getEventCode(), mailbagVO);
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion, selectedResdits, selectedMailbags);
		}
	@Test
	public void shouldThrowBusinessException_When_SaveMalRdtMsgAddDtlTest()
			throws SystemException, BusinessException, PersistenceException {
		Collection<MessageDespatchDetailsVO> messageAddressDetails = new ArrayList();
		MessageDespatchDetailsVO messageDespatchDetailsVO = new MessageDespatchDetailsVO();
		messageDespatchDetailsVO.setInterfaceSystem("ICS");
		messageDespatchDetailsVO.setMode("L");
		messageDespatchDetailsVO.setAddress("Test");
		messageDespatchDetailsVO.setEnvelopeCode("ICOXML");
		messageDespatchDetailsVO.setEnvelopeAddress("ABCD~P");
		messageAddressDetails.add(messageDespatchDetailsVO);
		Collection<AutoForwardDetailsVO> participantDetails = new ArrayList();
		MessageVO messageVo = new MessageVO();
		messageVo.setMessageType("IFTSTA");
		messageVo.setMessageVersion("1");
		String selectedResditVersion = "1.1 M39";
		List<String> selectedResdits = new ArrayList();
		Collection<MailbagVO> selectedMailbags = new ArrayList();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("AV");
		mailbagVO.setPaCode("FR001");
		mailbagVO.setSequenceNumber(14772);
		mailbagVO.setUpliftAirport("CDG");
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
		mailbagVO.setCarrierId(1134);
		mailbagVO.setFlightNumber("6688");
		mailbagVO.setFlightSequenceNumber(16);
		mailbagVO.setContainerNumber("AKE34334AV");
		mailbagVO.setMailboxId("FR001");
		mailbagVO.setLastUpdateUser("AV");
		selectedMailbags.add(mailbagVO);
		String mailBoxID = "FR001";
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailBoxID).when(dao).findMailboxIdForPA(any());
		verify(saveSendResditMessageDetailsFeature, times(0)).execute(any(MailResditMessageDetailsVO.class));
		BusinessException businessException = new BusinessException() {
		};
		doThrow(businessException).when(saveSendResditMessageDetailsFeature)
				.execute(any(MailResditMessageDetailsVO.class));
		spy.saveMalRdtMsgAddDtl(messageAddressDetails, participantDetails, messageVo, selectedResditVersion,
				selectedResdits, selectedMailbags);
		}
	@Test
	public void validateOperationalFlightTest() throws SystemException {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		operationalFlightVO.setRequireAllLegs(true);
		operationalFlightVO.setFlightSequenceNumber(1234);
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList();
		FlightValidationVO flightValidationVO=new FlightValidationVO();
		flightValidationVO.setLegOrigin("FRA");
		flightValidationVOs.add(flightValidationVO);
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		spy.validateOperationalFlight(operationalFlightVO,false);
	}
	@Test
	public void validateOperationalFlightWithRequireAlllegsFalseTest() throws  SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol("FRA");
		operationalFlightVO.setRequireAllLegs(false);
		operationalFlightVO.setFlightSequenceNumber(1234);
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList();
		FlightValidationVO flightValidationVO=new FlightValidationVO();
		flightValidationVO.setLegOrigin("FRA");
		flightValidationVO.setFlightSequenceNumber(1234);
		flightValidationVOs.add(flightValidationVO);
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		spy.validateOperationalFlight(operationalFlightVO,true);
	}
	@Test
	public void validateOperationalFlightWithPolNull() throws  SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol(null);
		operationalFlightVO.setRequireAllLegs(true);	
		operationalFlightVO.setFlightSequenceNumber(1234);
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList();
		FlightValidationVO flightValidationVO=new FlightValidationVO();
		flightValidationVO.setLegOrigin("FRA");
		flightValidationVOs.add(flightValidationVO);
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		spy.validateOperationalFlight(operationalFlightVO,false);
	}
	@Test
	public void validateOperationalFlightWithFlightDate() throws  SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol(null);
		operationalFlightVO.setRequireAllLegs(false);	
		//operationalFlightVO.setFlightSequenceNumber(1234);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		Collection<FlightValidationVO> flightValidationVOs=new ArrayList();
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		spy.validateOperationalFlight(operationalFlightVO,false);
	}
	@Test
	public void validateOperationalFlightWithFlightValidationVOsNull() throws  SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol(null);
		operationalFlightVO.setRequireAllLegs(true);	
		operationalFlightVO.setFlightSequenceNumber(1234);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		Collection<FlightValidationVO> flightValidationVOs=null;
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		spy.validateOperationalFlight(operationalFlightVO,false);
	}
	@Test
	public void validateOperationalFlightWithFlightDateAndSeqNumNull() throws SystemException  {
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setPol(null);
		operationalFlightVO.setRequireAllLegs(true);	
		Collection<FlightValidationVO> flightValidationVOs=null;
		doReturn(flightValidationVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		spy.validateOperationalFlight(operationalFlightVO,false);
	}
	@Test
	public void generateCN46ReportForFlightlevel_Test()
			throws SystemException, BusinessException, PersistenceException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		ReportSpec reportSpec = new ReportSpec();
		OperationalFlightVO flightVO = new OperationalFlightVO();
		reportSpec.addFilterValue(flightVO);
		MailManifestVO manifestVO = new MailManifestVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(manifestVO).when(dao).findCN46ManifestDetails(any());
		Collection<MailManifestVO> reportData = new ArrayList<>();
        reportData.add(manifestVO);
		reportSpec.setData(reportData);
		spy.generateCN46ReportForFlightlevel(reportSpec);
	}
	@Test(expected=SystemException.class)
	public void generateCN46ReportForFlightlevel_throwsPersistanceException()
			throws SystemException, BusinessException, PersistenceException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		ReportSpec reportSpec = new ReportSpec();
		OperationalFlightVO flightVO = new OperationalFlightVO();
		reportSpec.addFilterValue(flightVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
	    doThrow(PersistenceException.class).when(dao).findCN46ManifestDetails(any());
		spy.generateCN46ReportForFlightlevel(reportSpec);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndCountrytypeGreenTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndCountrytypeBlueTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("BLU");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "B";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndCountrytypeWhiteTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "W";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndCountrytypeRedTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("RED");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "R";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndCountrytypeOtherTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("KKK");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "Other";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndCountrytypeNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = null;
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(null).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "Other";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultFailTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("F");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndScreeningLoactionDifferentAndConsignemntRoutingVosNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("MEL");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ConsignmentRoutingVO> consignmentRoutingVos = null;
		doReturn(consignmentRoutingVos).when(dao).findConsignmentRoutingVosForMailbagScreening(any(String.class),any(Long.class));
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("QF");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndScreeningLoactionDifferentAndConsignemntRoutingVosNotNullAndDifferentPolTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("MEL");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ConsignmentRoutingVO> consignmentRoutingVos = new ArrayList<>();
		ConsignmentRoutingVO consignmentRoutingVO = new ConsignmentRoutingVO();
		consignmentRoutingVO.setPol("SYD");
		consignmentRoutingVos.add(consignmentRoutingVO);
		List<String> statusCodes = new ArrayList<>();
		statusCodes.add("SHR");
		doReturn(consignmentRoutingVos).when(dao).findConsignmentRoutingVosForMailbagScreening(any(String.class),any(Long.class));
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("RED");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "R";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndScreeningLoactionDifferentAndConsignemntRoutingVosNotNullAndDifferentPolTest1() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("MEL");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ConsignmentRoutingVO> consignmentRoutingVos = new ArrayList<>();
		ConsignmentRoutingVO consignmentRoutingVO = new ConsignmentRoutingVO();
		consignmentRoutingVO.setPol("SYD");
		consignmentRoutingVos.add(consignmentRoutingVO);
		List<String> statusCodes = new ArrayList<>();
		statusCodes.add("SPX");
		doReturn(consignmentRoutingVos).when(dao).findConsignmentRoutingVosForMailbagScreening(any(String.class),any(Long.class));
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndScreeningLoactionDifferentAndConsignemntRoutingVosNotNullAndDifferentPolTest2() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("MEL");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ConsignmentRoutingVO> consignmentRoutingVos = new ArrayList<>();
		ConsignmentRoutingVO consignmentRoutingVO = new ConsignmentRoutingVO();
		consignmentRoutingVO.setPol("SYD");
		ConsignmentRoutingVO consignmentRoutingVO1 = new ConsignmentRoutingVO();
		consignmentRoutingVO1.setPol("MEL");
		consignmentRoutingVos.add(consignmentRoutingVO);
		List<String> statusCodes = new ArrayList<>();
		statusCodes.add("SPX");
		doReturn(consignmentRoutingVos).when(dao).findConsignmentRoutingVosForMailbagScreening(any(String.class),any(Long.class));
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndScreeningLoactionDifferentAndConsignemntRoutingVosNotNullAndSamePolTest2() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("MEL");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ConsignmentRoutingVO> consignmentRoutingVos = new ArrayList<>();
		ConsignmentRoutingVO consignmentRoutingVO = new ConsignmentRoutingVO();
		consignmentRoutingVO.setPol("MEL");
		consignmentRoutingVos.add(consignmentRoutingVO);
		List<String> statusCodes = new ArrayList<>();
		statusCodes.add("SPX");
		doReturn(consignmentRoutingVos).when(dao).findConsignmentRoutingVosForMailbagScreening(any(String.class),any(Long.class));
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenBagNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr(null);
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagEmptyTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenMailBagEntityNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(null).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenRaCountNotZeroTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(1).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndScreeningLoactionDifferentAndConsignemntRoutingVosEmptyTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ConsignmentRoutingVO> consignmentRoutingVos = new ArrayList<>();
		List<String> statusCodes = new ArrayList<>();
		statusCodes.add("SHR");
		doReturn(consignmentRoutingVos).when(dao).findConsignmentRoutingVosForMailbagScreening(any(String.class),any(Long.class));
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("RED");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "R";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndScreeningLoactionDifferentAndConsignemntScreeningVosNotNullAndDifferentPolAndCountryBlueTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("MEL");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ConsignmentRoutingVO> consignmentRoutingVos = new ArrayList<>();
		ConsignmentRoutingVO consignmentRoutingVO = new ConsignmentRoutingVO();
		consignmentRoutingVO.setPol("SYD");
		consignmentRoutingVos.add(consignmentRoutingVO);
		List<String> statusCodes = new ArrayList<>();
		statusCodes.add("SPX");
		doReturn(consignmentRoutingVos).when(dao).findConsignmentRoutingVosForMailbagScreening(any(String.class),any(Long.class));
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("BLU");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "B";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndScreeningLoactionDifferentAndConsignemntScreeningVosNotNullAndDifferentPolAndCountryWhiteTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("MEL");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ConsignmentRoutingVO> consignmentRoutingVos = new ArrayList<>();
		ConsignmentRoutingVO consignmentRoutingVO = new ConsignmentRoutingVO();
		consignmentRoutingVO.setPol("SYD");
		consignmentRoutingVos.add(consignmentRoutingVO);
		List<String> statusCodes = new ArrayList<>();
		statusCodes.add("SPX");
		doReturn(consignmentRoutingVos).when(dao).findConsignmentRoutingVosForMailbagScreening(any(String.class),any(Long.class));
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "W";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenBagNotNullAndResultPassAndScreeningLoactionDifferentAndConsignemntScreeningVosNotNullAndDifferentPolAndCountryOtherTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("MEL");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ConsignmentRoutingVO> consignmentRoutingVos = new ArrayList<>();
		ConsignmentRoutingVO consignmentRoutingVO = new ConsignmentRoutingVO();
		consignmentRoutingVO.setPol("SYD");
		consignmentRoutingVos.add(consignmentRoutingVO);
		List<String> statusCodes = new ArrayList<>();
		statusCodes.add("SPX");
		doReturn(consignmentRoutingVos).when(dao).findConsignmentRoutingVosForMailbagScreening(any(String.class),any(Long.class));
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("Other");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "Other";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	//@Test
	public void shouldThrowSystemExceptionWhenSequenceNumberTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		SystemException systemException =  new SystemException("system exception");
		doThrow(systemException).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldThrowProxyExceptionTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 128;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		ProxyException proxyException = new ProxyException("proxy exception", null);
		doThrow(proxyException).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void fetchFlightVolumeDetailsTest() throws SystemException, PersistenceException, ProxyException {
		Collection<FlightFilterVO> flightFilterVOs = new ArrayList<>();
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setFlightCarrierId(183);
		flightFilterVO.setFlightNumber("2706");
		flightFilterVO.setFlightSequenceNumber(1);
		flightFilterVO.setCompanyCode("LH");
		flightFilterVOs.add(flightFilterVO);
		Collection<FlightSegmentVolumeDetailsVO> flights = new ArrayList<>();
		FlightSegmentVolumeDetailsVO flight = new FlightSegmentVolumeDetailsVO();
		flight.setSegmentDestination("CDG");
		flight.setSegmentOrigin("FRA");
		flights.add(flight);
		doReturn(flights).when(flightOperationsProxy).fetchFlightVolumeDetails(flightFilterVOs);
		spy.fetchFlightVolumeDetails(flightFilterVOs);
	}
	@Test
	public void findULDTypesTest() throws SystemException, PersistenceException, ProxyException, BusinessDelegateException {
		Collection<ULDTypeVO> uldTypes=new ArrayList<>();
		ULDTypeVO uldType=new ULDTypeVO();
		uldType.setCompanyCode("LH");
		uldType.setUldType("C");
		Collection<AircraftCompatibilityVO> aircrafts=new ArrayList<>();
		AircraftCompatibilityVO aircraft=new AircraftCompatibilityVO();
		aircraft.setPositionType("C");
		aircrafts.add(aircraft);
		uldType.setAircraftCompatibility(aircrafts);
		uldTypes.add(uldType);
		ULDTypeFilterVO uldTypeFilterVO=new ULDTypeFilterVO();
		uldTypeFilterVO.setCompanyCode("LH");
		uldTypeFilterVO.setUldTypeCode("AKE");
		doReturn(uldTypes).when(sharedULDProxy).findULDTypes(uldTypeFilterVO);
		spy.findULDTypes(uldTypeFilterVO);
	}
	@Test
	public void stampACC3IdentifierForPreviousLegMailBags_Test() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightNumber("7171");
		operationalFlightVO.setCarrierId(1176);
		operationalFlightVO.setLegSerialNumber(2);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    operationalFlightVOs.add(operationalFlightVO);		
	    
	    Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
				
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		Collection<FlightValidationVO> flightFilterVOs= new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightNumber("7171");
		flightFilterVOs.add(flightFilterVO);
		Collection<AirlineAirportParameterVO> parameterMapAirline = new ArrayList<>();
		AirlineAirportParameterVO airlineAirportParameterVO= new AirlineAirportParameterVO();
		airlineAirportParameterVO.setParameterValue("Y");
		parameterMapAirline.add(airlineAirportParameterVO);
		doReturn(parameterMapAirline).when(sharedAirlineProxy).findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("operations.flthandling.alternateairportcodeforacc3", "LTFM");
		doReturn(parameterMap).when(sharedAreaProxy).findAirportParametersByCode(any(String.class),any(String.class),anyCollectionOf(String.class));
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		Map<String, String> airlineMap = new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforPassengerAircraft", "LH");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class), any(Integer.class),
				anyCollectionOf(String.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setContainerNumber("AKE94835LH");
		containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		bulkContainers.add(bulkContainer);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("C");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerVOs).when(dao).findULDsInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(bulkContainers).when(dao).findContainersInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(any()); 
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void stampACC3IdentifierForWithExceptionForAlternateAirport_Test() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightNumber("7171");
		operationalFlightVO.setCarrierId(1176);
		operationalFlightVO.setLegSerialNumber(2);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    operationalFlightVOs.add(operationalFlightVO);		
	    Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		Collection<FlightValidationVO> flightFilterVOs= new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightNumber("7171");
		flightFilterVOs.add(flightFilterVO);
		Collection<AirlineAirportParameterVO> parameterMapAirline = new ArrayList<>();
		AirlineAirportParameterVO airlineAirportParameterVO= new AirlineAirportParameterVO();
		airlineAirportParameterVO.setParameterValue("Y");
		parameterMapAirline.add(airlineAirportParameterVO);
		doReturn(parameterMapAirline).when(sharedAirlineProxy).findAirlineAirportParameters(any(AirlineAirportParameterFilterVO.class));
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("operations.flthandling.alternateairportcodeforacc3", "LTFM");
		doThrow(new SystemException("error",
				"error")).when(sharedAreaProxy).findAirportParametersByCode(any(String.class),any(String.class),anyCollectionOf(String.class));
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		Map<String, String> airlineMap = new HashMap<>();
		airlineMap.put("shared.airline.ACC3CarrierCodeforPassengerAircraft", "LH");
		doReturn(airlineMap).when(sharedAirlineProxy).findAirlineParametersByCode(any(String.class), any(Integer.class),
				anyCollectionOf(String.class));
		AirlineVO airlineVO=new AirlineVO();
		doReturn(airlineVO).when(sharedAirlineProxy).findAirlineDetails(any(String.class),any(Integer.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setContainerNumber("AKE94835LH");
		containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		bulkContainers.add(bulkContainer);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("C");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerVOs).when(dao).findULDsInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(bulkContainers).when(dao).findContainersInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(any()); 
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test 
	public void stampACC3IdentifierOperationalFlightVOsIsNull()throws PersistenceException, SharedProxyException, SystemException,
	  ProxyException, FinderException {
	  Collection<OperationalFlightVO> operationalFlightVOs= null; 
	  OperationalFlightVO operationalFlightVO= new OperationalFlightVO(); 
	  spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);  
	  }
	@Test
	public void stampACC3IdentifierCheckForEuNonEuAirportIsFalse() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
	    operationalFlightVOs.add(operationalFlightVO);		
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
      }
	@Test
	public void stampACC3IdentifierFlightRoutesLessThan2() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
	    operationalFlightVOs.add(operationalFlightVO);		
	    List<String> routes =new ArrayList<>();
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void stampACC3IdentifieFltRouteOrgAndIntermediatePortIsEmpty() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute(" - -FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
	    operationalFlightVOs.add(operationalFlightVO);		
	 				
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void stampACC3IdentifierFlightValidationVOsIsEmpty() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightNumber("7171");
		operationalFlightVO.setCarrierId(1176);
		operationalFlightVO.setLegSerialNumber(2);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    operationalFlightVOs.add(operationalFlightVO);		
	 				
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		Collection<FlightValidationVO> flightFilterVOs= new ArrayList<>();
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
	
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void stampACC3IdentifierFlightValidationVOsIsNull() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightNumber("7171");
		operationalFlightVO.setCarrierId(1176);
		operationalFlightVO.setLegSerialNumber(2);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    operationalFlightVOs.add(operationalFlightVO);		
	 				
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		doReturn(null).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
	
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void stampACC3IdentifierContainerDetailsVOsIsEmpty() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightNumber("7171");
		operationalFlightVO.setCarrierId(1176);
		operationalFlightVO.setLegSerialNumber(2);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    operationalFlightVOs.add(operationalFlightVO);		
	    
	    Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
						
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		Collection<FlightValidationVO> flightFilterVOs= new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightNumber("7171");
		flightFilterVOs.add(flightFilterVO);
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setContainerNumber("AKE94835LH");
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		bulkContainers.add(bulkContainer);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("C");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerVOs).when(dao).findULDsInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(bulkContainers).when(dao).findContainersInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(any()); 
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void stampACC3IdentifierContainerDetailsVOsIsNull() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightNumber("7171");
		operationalFlightVO.setCarrierId(1176);
		operationalFlightVO.setLegSerialNumber(2);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    operationalFlightVOs.add(operationalFlightVO);		
	    						
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		Collection<FlightValidationVO> flightFilterVOs= new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightNumber("7171");
		flightFilterVOs.add(flightFilterVO);
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setContainerNumber("AKE94835LH");
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		bulkContainers.add(bulkContainer);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("C");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerVOs).when(dao).findULDsInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(bulkContainers).when(dao).findContainersInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(null).when(spy).findContainerDetailsVOs(any(), any(), any()); 
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void stampACC3IdentifierMailbagDetailsIsNull() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightNumber("7171");
		operationalFlightVO.setCarrierId(1176);
		operationalFlightVO.setLegSerialNumber(2);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    operationalFlightVOs.add(operationalFlightVO);		
	    
	    Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		containerDetailsVO.setMailDetails(null);
		containerDetailsVOs.add(containerDetailsVO);
				
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		Collection<FlightValidationVO> flightFilterVOs= new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightNumber("7171");
		flightFilterVOs.add(flightFilterVO);
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setContainerNumber("AKE94835LH");
		containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		bulkContainers.add(bulkContainer);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("C");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerVOs).when(dao).findULDsInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(bulkContainers).when(dao).findContainersInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(any()); 
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void stampACC3IdentifierMailbagDetailsIsEmpty() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightNumber("7171");
		operationalFlightVO.setCarrierId(1176);
		operationalFlightVO.setLegSerialNumber(2);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    operationalFlightVOs.add(operationalFlightVO);		
	    
	    Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("FRA");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
				
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		Collection<FlightValidationVO> flightFilterVOs= new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightNumber("7171");
		flightFilterVOs.add(flightFilterVO);
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setContainerNumber("AKE94835LH");
		containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		bulkContainers.add(bulkContainer);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("C");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerVOs).when(dao).findULDsInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(bulkContainers).when(dao).findContainersInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(any()); 
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void stampACC3IdentifierRoutesNotcontainsConDtlsVOPou() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightNumber("7171");
		operationalFlightVO.setCarrierId(1176);
		operationalFlightVO.setLegSerialNumber(2);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    operationalFlightVOs.add(operationalFlightVO);		
	    
	    Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("DEL");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
				
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		Collection<FlightValidationVO> flightFilterVOs= new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightNumber("7171");
		flightFilterVOs.add(flightFilterVO);
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setContainerNumber("AKE94835LH");
		containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		bulkContainers.add(bulkContainer);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("C");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerVOs).when(dao).findULDsInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(bulkContainers).when(dao).findContainersInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(any()); 
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void stampACC3IdentifierOptfltVOPolNotequalsConDtlsVOPou() throws PersistenceException, SharedProxyException, SystemException, ProxyException, FinderException {
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		Collection<OperationalFlightVO> operationalFlightVOs= new ArrayList<>();
		OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
		operationalFlightVO.setFlightRoute("DEL-DXB-FRA");
		operationalFlightVO.setPou("FRA");
		operationalFlightVO.setPol("DXB");
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setFlightType("CO");
		operationalFlightVO.setAirportCode("CDG");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightNumber("7171");
		operationalFlightVO.setCarrierId(1176);
		operationalFlightVO.setLegSerialNumber(2);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    operationalFlightVOs.add(operationalFlightVO);		
	    
	    Collection<ContainerDetailsVO> containerDetailsVOs =new ArrayList<>();
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		containerDetailsVO.setPou("DXB");
		MailbagVO mailbagVO= new MailbagVO();
		Collection<MailbagVO> mailbagVOs =new ArrayList<>();
		mailbagVO.setMailbagId("DEFRAADEMUCAA7A80089009095408");
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
				
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DXB");
		airportValidationVO.setCountryCode("IN");
		AirportValidationVO airportValidationVO1 =new AirportValidationVO();
		airportValidationVO1.setCityCode("FRA");
		validAirportCodes.put("DXB", airportValidationVO);
		validAirportCodes.put("FRA", airportValidationVO1);
		Map<String, CityVO> cityMap=new HashMap <>();
		CityVO cityVO =new CityVO();
		cityVO.setEuNonEuIndicator("N");
		CityVO cityVO1 =new CityVO();
		cityVO1.setEuNonEuIndicator("E");
		cityMap.put("DXB", cityVO);
		cityMap.put("FRA", cityVO1);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(cityMap).when(sharedAreaProxy).validateCityCodes(any(String.class),anyCollectionOf(String.class));
		
		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("IN", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		Collection<FlightValidationVO> flightFilterVOs= new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightNumber("7171");
		flightFilterVOs.add(flightFilterVO);
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO=new ContainerVO();
		containerVO.setContainerNumber("AKE94835LH");
		containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		Collection<ContainerVO> bulkContainers=new ArrayList<>();
		ContainerVO bulkContainer=new ContainerVO();
		bulkContainer.setContainerNumber("BULK1234");
		bulkContainers.add(bulkContainer);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		assignedFlightEntity.setExportClosingFlag("C");
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(containerVOs).when(dao).findULDsInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(bulkContainers).when(dao).findContainersInAssignedFlight(any(OperationalFlightVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(any()); 
		spy.stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroAndResultPassAndCountrytypeGreenTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		//officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroAndSourceNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource(null);
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroAndSourceDifferntTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHY");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroAndScannedPortNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation(null);
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroAndDestinationNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination(null);
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroAndDestinationNullTest1() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDA11111111111111111111111");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDA11111111111111111111111");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroAndOriginNullTest1() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDA11111111111111111111111");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailBag = new Mailbag();
		mailBag.setMailIdr("1111111111111111111111111111");
		mailBag.setSequenceNumber(128);
		//mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroTest2() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailbagVO.setOoe(null);
		mailbagVO.setDoe(null);
		mailBag = new Mailbag();
		mailBag.setMailIdr("");
		mailBag.setSequenceNumber(128);
		//mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroTest3() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		//consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		//officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailbagVO.setOoe(null);
		mailbagVO.setDoe(null);
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		//mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroTest4() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		//consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("FR001");
		officeOfExchangeVO.setActive(true);
		//officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailbagVO.setOoe(null);
		mailbagVO.setDoe(null);
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		//mailBag.setOrigin("SYD");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsForDomesticWhenMailSequenceNumberZeroAndResultPassAndCountrytypeGreenTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("28LXXIXQB089");
		consignmentScreeningVO.setCompanyCode("QF");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailbagVO.setCompanyCode("QF");
		mailBag = new Mailbag();
		mailBag.setMailIdr("28LXXIXQB089");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("QF");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("QF");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("QF");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		
		HashMap<String, String> systemParameterMap1 = new HashMap<>();
		systemParameterMap1.put("mailtracking.domesticmra.usps", "AU101");
		systemParameterMap1.put("mail.operation.destinationforcarditmissingdomesticmailbag", "XXX");
		doReturn(systemParameterMap1).doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsForDomesticWhenMailSequenceNumberZeroAndScreeningLocationNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("28LXXIXQB089");
		consignmentScreeningVO.setCompanyCode("QF");
		//consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailbagVO.setCompanyCode("QF");
		mailBag = new Mailbag();
		mailBag.setMailIdr("28LXXIXQB089");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("QF");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("QF");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("QF");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		
		HashMap<String, String> systemParameterMap1 = new HashMap<>();
		systemParameterMap1.put("mailtracking.domesticmra.usps", "AU101");
		systemParameterMap1.put("mail.operation.destinationforcarditmissingdomesticmailbag", "XXX");
		doReturn(systemParameterMap1).doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	//@Test
	public void shouldSaveScreeningDetailsForDomesticWhenDestinationNATest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("28LXXIXQB089");
		consignmentScreeningVO.setCompanyCode("QF");
		//consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailbagVO.setCompanyCode("QF");
		mailBag = new Mailbag();
		mailBag.setMailIdr("28LXXIXQB089");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("QF");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("QF");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("QF");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		
		HashMap<String, String> systemParameterMap1 = new HashMap<>();
		systemParameterMap1.put("mailtracking.domesticmra.usps", "AU101");
		systemParameterMap1.put("mail.operation.destinationforcarditmissingdomesticmailbag", "NA");
		doReturn(systemParameterMap1).doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		SystemException systemException = new SystemException("system exception");
		doThrow(systemException).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	//@Test
	public void shouldSaveScreeningDetailsForDomesticWhenDestinationNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("28LXXIXQB089");
		consignmentScreeningVO.setCompanyCode("QF");
		//consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailbagVO.setCompanyCode("QF");
		mailBag = new Mailbag();
		mailBag.setMailIdr("28LXXIXQB089");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("QF");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("QF");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("QF");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		
		HashMap<String, String> systemParameterMap1 = null;
		//systemParameterMap1.put("mailtracking.domesticmra.usps", "AU101");
		//systemParameterMap1.put("mail.operation.destinationforcarditmissingdomesticmailbag", "NA");
		doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroAndPACodeNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("FRA");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		//officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangeVO.setAirportCode("FRA");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsForDomesticWhenSourceNotFromHHTTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("28LXXIXQB089");
		consignmentScreeningVO.setCompanyCode("QF");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailbagVO.setCompanyCode("QF");
		mailBag = new Mailbag();
		mailBag.setMailIdr("28LXXIXQB089");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("QF");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("QF");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("QF");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("MTK073");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		
		HashMap<String, String> systemParameterMap1 = new HashMap<>();
		systemParameterMap1.put("mailtracking.domesticmra.usps", "AU101");
		systemParameterMap1.put("mail.operation.destinationforcarditmissingdomesticmailbag", "XXX");
		doReturn(systemParameterMap1).doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	@Test
	public void shouldSaveScreeningDetailsForDomesticWhenSourceNullTest() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("28LXXIXQB089");
		consignmentScreeningVO.setCompanyCode("QF");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("AU101");
		officeOfExchangeVO.setActive(true);
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailbagVO.setCompanyCode("QF");
		mailBag = new Mailbag();
		mailBag.setMailIdr("28LXXIXQB089");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("QF");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("QF");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("QF");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource(null);
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		
		HashMap<String, String> systemParameterMap1 = new HashMap<>();
		systemParameterMap1.put("mailtracking.domesticmra.usps", "AU101");
		systemParameterMap1.put("mail.operation.destinationforcarditmissingdomesticmailbag", "XXX");
		doReturn(systemParameterMap1).doReturn(systemParameterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenMailSequenceNumberZeroAndPostalAuthorityEmpty() throws PersistenceException, SystemException, FinderException, ProxyException, SharedProxyException, MailHHTBusniessException{
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setMailIdr("AUSYDADEFRAACCA23221221002145");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setScreeningLocation("SYD");
		consignmentScreeningVO.setResult("P");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		long mailSequencenumber = 0;
		doReturn(mailSequencenumber).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
		Page<OfficeOfExchangeVO> officeOfExchangePage = new Page();
		officeOfExchangeVO.setPoaCode("");
		officeOfExchangeVO.setActive(true);
		officeOfExchangeVO.setAirportCode("");
		officeOfExchangePage.add(officeOfExchangeVO);
		doReturn(officeOfExchangePage).when(officeOfExchangeCache).findOfficeOfExchange(any(String.class),any(String.class),any(int.class));
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(128);
		mailBag = new Mailbag();
		mailBag.setMailIdr("AUSYDADEFRAACCA23221221002145");
		mailBag.setSequenceNumber(128);
		mailBag.setOrigin("SYD");
		mailBag.setDestination("FRA");
		mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(128);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(MailbagEntity.getClass(),mailbagPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("IBS");
		operationalFlightVO.setPol("SYD");
		Map<String, CountryVO> country = new HashMap<>();
		CountryVO countryVO = new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("AU", countryVO);
		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("SYD");
		airportValidationVO.setCountryCode("AU");
		validAirportCodes.put("SYD", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class), anyCollectionOf(String.class));
		spy.findCountryMemberGroupforAirport(operationalFlightVO,operationalFlightVO.getPol());   
		String counttyType = "G";
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(128);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		spy.saveScreeningDetailsFromHHT(consignmentScreeningVOs);
	}
	public void fetchMailSecurityDetails_Test() throws SystemException, SharedProxyException, PersistenceException, ProxyException{
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		MailScreeningFilterVO mailScreeningFilterVo= new MailScreeningFilterVO();
		mailScreeningFilterVo.setCompanyCode("LH");
		mailScreeningFilterVo.setMailBagId("INDELCDEFRAACUN22200213702200");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("LH");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("INDELCDEFRAACUN22200213702200");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DEL");
		mailbagVO.setUpliftAirport("DEL");
		
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
	
		SystemException systemException = new SystemException("system exception");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).listmailbagSecurityDetails(mailScreeningFilterVo);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));

		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));

		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("DE", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		spy.listmailbagSecurityDetails(mailScreeningFilterVo);
		spy.fetchMailSecurityDetails(mailScreeningFilterVo);
	}
	@Test
	public void fetchMailSecurityDetailsScreenDetailTypeIsNonCS() throws SystemException, SharedProxyException, PersistenceException, ProxyException{
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		MailScreeningFilterVO mailScreeningFilterVo= new MailScreeningFilterVO();
		mailScreeningFilterVo.setCompanyCode("LH");
		mailScreeningFilterVo.setMailBagId("INDELCDEFRAACUN22200213702200");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("LH");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("INDELCDEFRAACUN22200213702200");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DEL");
		mailbagVO.setUpliftAirport("DEL");
		
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("R");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("C");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
				
		SystemException systemException = new SystemException("system exception");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).listmailbagSecurityDetails(mailScreeningFilterVo);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));

		spy.listmailbagSecurityDetails(mailScreeningFilterVo);
		spy.fetchMailSecurityDetails(mailScreeningFilterVo);
	}
	@Test
	public void fetchMailSecurityDetailsAgentTypeAsRI() throws SystemException, SharedProxyException, PersistenceException, ProxyException{
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		MailScreeningFilterVO mailScreeningFilterVo= new MailScreeningFilterVO();
		mailScreeningFilterVo.setCompanyCode("LH");
		mailScreeningFilterVo.setMailBagId("INDELCDEFRAACUN22200213702200");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("LH");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("INDELCDEFRAACUN22200213702200");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DEL");
		mailbagVO.setUpliftAirport("DEL");
		
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		consignmentScreeningVO.setExpiryDate("0923");
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
				
		SystemException systemException = new SystemException("system exception");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).listmailbagSecurityDetails(mailScreeningFilterVo);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));

		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));

		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("WHT");
		country.put("DE", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		spy.listmailbagSecurityDetails(mailScreeningFilterVo);
		spy.fetchMailSecurityDetails(mailScreeningFilterVo);
	}
	@Test
	public void fetchMailSecurityDetailsIsBlueCountry() throws SystemException, SharedProxyException, PersistenceException, ProxyException{
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		MailScreeningFilterVO mailScreeningFilterVo= new MailScreeningFilterVO();
		mailScreeningFilterVo.setCompanyCode("LH");
		mailScreeningFilterVo.setMailBagId("INDELCDEFRAACUN22200213702200");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("LH");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("INDELCDEFRAACUN22200213702200");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DEL");
		mailbagVO.setUpliftAirport("DEL");
		
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
	
		SystemException systemException = new SystemException("system exception");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).listmailbagSecurityDetails(mailScreeningFilterVo);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));

		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));

		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("BLU");
		country.put("DE", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		spy.listmailbagSecurityDetails(mailScreeningFilterVo);
		spy.fetchMailSecurityDetails(mailScreeningFilterVo);
	}
	@Test
	public void fetchMailSecurityDetailsForGreenCountry() throws SystemException, SharedProxyException, PersistenceException, ProxyException{
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		MailScreeningFilterVO mailScreeningFilterVo= new MailScreeningFilterVO();
		mailScreeningFilterVo.setCompanyCode("LH");
		mailScreeningFilterVo.setMailBagId("INDELCDEFRAACUN22200213702200");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("LH");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("INDELCDEFRAACUN22200213702200");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DEL");
		mailbagVO.setUpliftAirport("DEL");
		
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
		
		SystemException systemException = new SystemException("system exception");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).listmailbagSecurityDetails(mailScreeningFilterVo);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));

		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));

		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("GRN");
		country.put("DE", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		spy.listmailbagSecurityDetails(mailScreeningFilterVo);
		spy.fetchMailSecurityDetails(mailScreeningFilterVo);
	}
	@Test
	public void fetchMailSecurityDetailsIsEUCountry() throws SystemException, SharedProxyException, PersistenceException, ProxyException{
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		MailScreeningFilterVO mailScreeningFilterVo= new MailScreeningFilterVO();
		mailScreeningFilterVo.setCompanyCode("LH");
		mailScreeningFilterVo.setMailBagId("INDELCDEFRAACUN22200213702200");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("LH");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("INDELCDEFRAACUN22200213702200");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DEL");
		mailbagVO.setUpliftAirport("DEL");
		
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RI");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		consignmentScreeningVO.setExpiryDate("0923");
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
	
		SystemException systemException = new SystemException("system exception");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).listmailbagSecurityDetails(mailScreeningFilterVo);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));

		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));

		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("EU");
		country.put("DE", countryVO);
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		
		spy.listmailbagSecurityDetails(mailScreeningFilterVo);
		spy.fetchMailSecurityDetails(mailScreeningFilterVo);
	}
	@Test
	public void fetchMailSecurityDetailCatchBlock() throws SystemException, SharedProxyException, PersistenceException, ProxyException{
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		MailScreeningFilterVO mailScreeningFilterVo= new MailScreeningFilterVO();
		mailScreeningFilterVo.setCompanyCode("LH");
		mailScreeningFilterVo.setMailBagId("INDELCDEFRAACUN22200213702200");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("LH");
		mailbagVO.setDocumentOwnerIdr(1134);
		mailbagVO.setDocumentNumber("30000980");
		mailbagVO.setDuplicateNumber(1);
		mailbagVO.setSequenceNumber(1);
		mailbagVO.setShipmentPrefix("134");
		mailbagVO.setMailbagId("INDELCDEFRAACUN22200213702200");
		mailbagVO.setSecurityDetailsPresent(true);
		mailbagVO.setSecurityStatusCode("SPX");
		mailbagVO.setOrigin("DEL");
		mailbagVO.setUpliftAirport("DEL");
		
		ConsignmentScreeningVO consignmentScreeningVO=new ConsignmentScreeningVO();
		consignmentScreeningVO.setAgentID("421");
		consignmentScreeningVO.setAgentType("RA");
		consignmentScreeningVO.setConsignmentNumber("PAWCHECKTEST5");
		consignmentScreeningVO.setScreeningLocation("DXB");
		consignmentScreeningVO.setIsoCountryCode("DE");
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setScreenDetailType("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityStatusCode("SPX");
		consignmentScreeningVO.setSource("FLTCLS");
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs=new ArrayList<>();
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailbagVO.setConsignmentScreeningVO(consignmentScreeningVOs);
	
		SystemException systemException = new SystemException("system exception");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(systemException).when(dao).listmailbagSecurityDetails(mailScreeningFilterVo);
		doReturn(mailbagVO).when(dao).listmailbagSecurityDetails(any(MailScreeningFilterVO.class));

		Map<String, AirportValidationVO> validAirportCodes=new HashMap <>();
		AirportValidationVO airportValidationVO =new AirportValidationVO();
		airportValidationVO.setCityCode("DEL");
		airportValidationVO.setCountryCode("IN");
		validAirportCodes.put("DEL", airportValidationVO);
		doReturn(validAirportCodes).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));

		Map<String, CountryVO> country=new HashMap<>();
		CountryVO countryVO=new CountryVO();
		countryVO.setMemberGroupCode("BLUE");
		country.put("DE", countryVO);
		doThrow(SharedProxyException.class).when(sharedAreaProxy).validateCountryCodes(any(String.class),anyCollectionOf(String.class));
		doReturn(country).when(sharedAreaProxy).validateCountryCodes(any(), any());
		spy.listmailbagSecurityDetails(mailScreeningFilterVo);
		spy.fetchMailSecurityDetails(mailScreeningFilterVo);
	}
	
	@Test
	public void publishMailOperationDataForRapidSystemTest() throws SystemException, BusinessException, RemoteException{
		MailOperationDataFilterVO mailOperationDataFilterVO = new MailOperationDataFilterVO();
		PublishRapidMailOperationsDataFeature publishRapidMailOperationsDataFeature = mockBean("operations.flthandling.publishRapidMailOperationsDataFeature", PublishRapidMailOperationsDataFeature.class);
		spy.publishMailOperationDataForRapidSystem(mailOperationDataFilterVO);
		verify(publishRapidMailOperationsDataFeature,times(1)).execute(any(PublishRapidMailOperationsDataFeatureVO.class));
	}
}
