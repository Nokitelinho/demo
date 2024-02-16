package com.ibsplc.icargo.business.mail.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedCommodityProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryAttachmentVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryFilterVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeCache;
import com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationCache;
import com.ibsplc.icargo.business.mail.operations.cache.SecurityScreeningValidationCache;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.proxy.DocumentRepositoryProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsMRAProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedGeneralMasterGroupingProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedULDProxy;
import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.HandoverVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAttachmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailMRDVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailWebserviceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDForSegmentVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportFlightOperationsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.AreaBusinessException;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.template.TemplateEncoderUtil;
import com.ibsplc.icargo.framework.util.template.TemplateEncoderUtilInstance;
import com.ibsplc.icargo.framework.util.template.TemplateEncoderUtilInstanceMock;
import com.ibsplc.icargo.framework.util.template.TemplateRenderingException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mockito.Mockito;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.business.shared.defaults.SharedDefaultsBI;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;
import com.ibsplc.xibase.server.framework.persistence.CreateException;

public class MailUploadControllerTest extends AbstractFeatureTest {

	private MailUploadController mailUploadControllerSpy;
	private MailTrackingDefaultsDAO dao;
	private ImportOperationsFilterVO filterVO;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private FlightOperationsProxy flightOperationsProxy;
	private SharedAreaProxy sharedAreaProxy;
	private SharedAirlineProxy sharedAirlineProxy;
	DocumentRepositoryProxy documentRepositoryProxy;
	public static final String MAILBAG_ID="USDFWADEFRAAACA96545256010025";
	private OfficeOfExchangeCache officeOfExchangeCache;
	private PostalAdministrationCache postalAdministrationCache;
	private static final String PERIODFOR_PABUILTMAILS="mail.operations.noofdaysforpabuiltmailbags";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private SharedULDProxy sharedULDProxy;
	private MailController mailControllerBean; 
	private AssignedFlightSegment assignedFlightSegment;
	private Container containerEntity;
	private Mailbag mailBag;
	private AssignedFlight assignedFlight;
	private ULDForSegment uldForSegment;
	private Mailbag mailbag;
	private MailController mailController;
	private Mailbag mailbagBean;
	private SharedCommodityProxy sharedCommodityProxy;
	private MailUploadController mailUploadControllerBean;
	private TemplateEncoderUtil templateEncoderUtil;
	private TemplateEncoderUtilInstance templateEncoderUtilInstance;
	private Container containerSpy;
	private KeyUtilInstance keyUtils;
	private KeyUtilInstance keyUtilsInstance;
	private SecurityScreeningValidationCache securityScreeningValidationCache;
	private SharedGeneralMasterGroupingProxy sharedGeneralMasterGroupingProxy;
	private MailbagPK mailbagPK;
	private MailAcceptance mailAcceptance;
	private MailOperationsMRAProxy mailOperationsMRAProxy;
	private OfficeOfExchange officeOfExchange;
	private ResditController resditController;

	@Override
	public void setup() throws Exception {
		mailUploadControllerSpy = spy(new MailUploadController());
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsDAO.class);
		filterVO = new ImportOperationsFilterVO();
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		sharedAreaProxy =mockProxy(SharedAreaProxy.class);
		sharedAirlineProxy =mockProxy(SharedAirlineProxy.class);
		flightOperationsProxy =mockProxy(FlightOperationsProxy.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		documentRepositoryProxy=mockProxy(DocumentRepositoryProxy.class);	
		officeOfExchangeCache = spy(OfficeOfExchangeCache.class);
		postalAdministrationCache = spy(PostalAdministrationCache.class);
    	doReturn(officeOfExchangeCache).when(CacheFactory.getInstance()).getCache(OfficeOfExchangeCache.ENTITY_NAME);
    	doReturn(postalAdministrationCache).when(CacheFactory.getInstance()).getCache(PostalAdministrationCache.ENTITY_NAME);
        sharedULDProxy = mockProxy(SharedULDProxy.class);
        mailControllerBean = mockBean("mAilcontroller", MailController.class);
        AssignedFlightSegmentVO assignedFlightSegmentVO = new AssignedFlightSegmentVO();
        assignedFlightSegmentVO.setCompanyCode(getCompanyCode());
		assignedFlightSegment = new AssignedFlightSegment(assignedFlightSegmentVO);
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVO.setReassignFlag(true);
		containerVO.setAssignedPort("CDG");
		
        assignedFlight = new AssignedFlight();
        ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
        uldForSegmentVO.setCompanyCode(getCompanyCode());
        uldForSegment = new ULDForSegment(uldForSegmentVO);
        mailController = mock(MailController.class);
        mailbagBean = mockBean("MailbagEntity", Mailbag.class);
        sharedCommodityProxy = mockProxy(SharedCommodityProxy.class);
        mailUploadControllerBean =  mockBean("mailUploadcontroller", MailUploadController.class);
        TemplateEncoderUtilInstanceMock.mockTemplateEncoderUtilInstance();
        templateEncoderUtilInstance = TemplateEncoderUtilInstance.getInstance();
        containerSpy = spy(new Container());
		KeyUtilInstanceMock.mockKeyUtilInstance();
		keyUtils = KeyUtilInstance.getInstance();
		keyUtilsInstance =  KeyUtilInstance.getInstance();
		securityScreeningValidationCache = spy(SecurityScreeningValidationCache.class);
    	doReturn(securityScreeningValidationCache).when(CacheFactory.getInstance()).getCache(SecurityScreeningValidationCache.ENTITY_NAME);
    	sharedGeneralMasterGroupingProxy =mockProxy(SharedGeneralMasterGroupingProxy.class);
    	 mailAcceptance = mock(MailAcceptance.class);
 		mailOperationsMRAProxy = mockProxy(MailOperationsMRAProxy.class);
		officeOfExchange = mock(OfficeOfExchange.class);
		resditController = mock(ResditController.class);

	}
	@Test
	public void findInboundFlightOperationsDetailsreturnflightoperationdetails() throws SystemException,PersistenceException {
		ImportFlightOperationsVO importFlightOperationsVO = new ImportFlightOperationsVO();
		Collection<ManifestFilterVO> manifestfilterVOlist = new ArrayList<>(); 
		ManifestFilterVO manifestFilterVO= new ManifestFilterVO();
		manifestFilterVO.setAgentCode("AA");
		importFlightOperationsVO.setCarrierCode("EK");
		manifestfilterVOlist.add(manifestFilterVO);
		Collection<ImportFlightOperationsVO> inputflightoprnVOlist= new ArrayList<>();
		inputflightoprnVOlist.add(importFlightOperationsVO);
		Collection<ImportFlightOperationsVO> inputflightoprnVOlist1= new ArrayList<>();
		doReturn(inputflightoprnVOlist).when(dao).findInboundFlightOperationsDetails(any(ImportOperationsFilterVO.class),anyCollectionOf(ManifestFilterVO.class));
		inputflightoprnVOlist1= mailUploadControllerSpy.findInboundFlightOperationsDetails(filterVO,manifestfilterVOlist); 
		assertNotNull(inputflightoprnVOlist1);	
		
	}
	@Test
	public void findOffloadULDDetailsAtAirportreturnflightoperationdetails() throws SystemException,PersistenceException {
		
		OffloadFilterVO offloadFilterVO = new OffloadFilterVO();
		OffloadULDDetailsVO offloadULDDetailsVO =new OffloadULDDetailsVO();
		Collection<OffloadULDDetailsVO> offloadULDDetailsVOList = new ArrayList<>(); 
		Collection<OffloadULDDetailsVO> offloadULDDetailsVOList1 = new ArrayList<>();
		offloadULDDetailsVO.setCompanyCode("DNAE");
		offloadULDDetailsVOList.add(offloadULDDetailsVO);
		doReturn(offloadULDDetailsVOList).when(dao).findOffloadULDDetailsAtAirport(any(OffloadFilterVO.class));
		offloadULDDetailsVOList1= mailUploadControllerSpy.findOffloadULDDetailsAtAirport(offloadFilterVO); 
		assertNotNull(offloadULDDetailsVOList1);	
	}
	@Test(expected = SystemException.class)
	public void findOffloadULDDetailsAtAirporthrowsexception() throws SystemException,PersistenceException {
		
		OffloadFilterVO offloadFilterVO = new OffloadFilterVO(); 
		Collection<OffloadULDDetailsVO> offloadULDDetailsVOList1 = new ArrayList<>();
      	doThrow(PersistenceException.class).when(dao).findOffloadULDDetailsAtAirport(any(OffloadFilterVO.class));
		offloadULDDetailsVOList1= mailUploadControllerSpy.findOffloadULDDetailsAtAirport(offloadFilterVO); 
		
	}
	
	@Test
	public void generateTrolleyNumberForMRDDetailsWhenNoFlightDate() throws SystemException, ProxyException, TemplateRenderingException {
		HandoverVO mailHandover = new HandoverVO();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		mailHandover.setFlightDate(currentDate);
		 String serialNumber = null;
		 Map<String, String> dummyTrolleyNumberMap=null;
		 serialNumber=mailUploadControllerSpy.generateTrolleyNumberForMRD(mailHandover,dummyTrolleyNumberMap); 
		 assertNotNull(serialNumber);
		
	}
	
	@Test
	public void generateTrolleyNumberForMRDDetailsWhenFlightNumber() throws SystemException, ProxyException, TemplateRenderingException {
		HandoverVO mailHandover = new HandoverVO();
		mailHandover.setFlightNumber("1234");
		 String serialNumber = null;
		 Map<String, String> dummyTrolleyNumberMap=null;
		 serialNumber=mailUploadControllerSpy.generateTrolleyNumberForMRD(mailHandover,dummyTrolleyNumberMap); 
		 assertNotNull(serialNumber);
	}
		 
	@Test
	public void generateTrolleyNumberForMRDDetailsWhenFlightNumberLength() throws SystemException, ProxyException, TemplateRenderingException {
		HandoverVO mailHandover = new HandoverVO();
		mailHandover.setFlightNumber("001234");
		String serialNumber = null;
		Map<String, String> dummyTrolleyNumberMap=null;
		serialNumber=mailUploadControllerSpy.generateTrolleyNumberForMRD(mailHandover,dummyTrolleyNumberMap); 
		assertNotNull(serialNumber);
	}
	
	
	@Test
	public void generateTrolleyNumberForMRDDetailsWithFlightInfoTag() throws SystemException, ProxyException, TemplateRenderingException {
		HandoverVO mailHandover = new HandoverVO();
		mailHandover.setFlightNumber("0000");
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		mailHandover.setFlightDate(currentDate);
		String serialNumber = null;
		Map<String, String> dummyTrolleyNumberMap = null;
        MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mLDDetailVO.setCompanyCode("DNAE");
		Map<String, String> mailIDFormatMap= null;
		doReturn(mailIDFormatMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		String keyFormat=null;
		Map<String, MLDDetailVO> templateObject = new HashMap<>();
		templateObject.put("mail", mLDDetailVO);
		doReturn("KeyCondition1").when(TemplateEncoderUtilInstance.getInstance()).encode(keyFormat, "vo", templateObject, false);
		serialNumber=mailUploadControllerSpy.generateTrolleyNumberForMRD(mailHandover,dummyTrolleyNumberMap); 
		assertNotNull(serialNumber);
	}
	
	@Test
	public void generateTrolleyNumberForMRDDetailsWithFlightNumberTagAndDateNull() throws SystemException, ProxyException, TemplateRenderingException {
		HandoverVO mailHandover = new HandoverVO();
		mailHandover.setFlightNumber("0000");
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		mailHandover.setFlightDate(currentDate);
		String serialNumber = null;
		Map<String, String> dummyTrolleyNumberMap = null;
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mLDDetailVO.setCompanyCode("DNAE");
		Map<String, String> mailIDFormatMap= null;
		doReturn(mailIDFormatMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		String keyFormat=null;
		Map<String, MLDDetailVO> templateObject = new HashMap<>();
		templateObject.put("mail", mLDDetailVO);
		doReturn("KeyCondition2").when(TemplateEncoderUtilInstance.getInstance()).encode(keyFormat, "vo", templateObject, false);
		serialNumber=mailUploadControllerSpy.generateTrolleyNumberForMRD(mailHandover,dummyTrolleyNumberMap); 
		assertNotNull(serialNumber);
	}
	
	@Test
	public void generateTrolleyNumberForMRDDetailsWithFlightNewKey() throws SystemException, ProxyException, TemplateRenderingException {
		HandoverVO mailHandover = new HandoverVO();
		mailHandover.setFlightNumber("001234");
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		mailHandover.setFlightDate(currentDate);
		String serialNumber = null;
		Map<String, String> dummyTrolleyNumberMap = new HashMap<>();
		String dummyTrolleyNumberMapKey=null;
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mLDDetailVO.setCompanyCode("DNAE");
		Map<String, String> mailIDFormatMap= null;
		doReturn(mailIDFormatMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		String keyFormat="{flight}";
		Map<String, MLDDetailVO> templateObject = new HashMap<>();
		templateObject.put("mail", mLDDetailVO);
		doReturn("KeyCondition3").when(TemplateEncoderUtilInstance.getInstance()).encode(keyFormat, "vo", templateObject, false);
		dummyTrolleyNumberMap.put(dummyTrolleyNumberMapKey, serialNumber);
		serialNumber=mailUploadControllerSpy.generateTrolleyNumberForMRD(mailHandover,dummyTrolleyNumberMap); 
		assertNotNull(serialNumber);
	}
	
	@Test
	public void generateTrolleyNumberForMRDDetailsWithFlightExistingKey() throws SystemException, ProxyException, TemplateRenderingException {
		HandoverVO mailHandover = new HandoverVO();
		mailHandover.setFlightNumber("1234");
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		mailHandover.setFlightDate(currentDate);
		mailHandover.setCarrierCode("EK");
		mailHandover.setCompanyCode("DNAE");
		String serialNumber = "1234";
		Map<String, String> dummyTrolleyNumberMap = new HashMap<>();
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mLDDetailVO.setCompanyCode("DNAE");
		mLDDetailVO.setCarrier("EK");
		mLDDetailVO.setFlight(mailHandover.getFlightNumber().substring(0, 4));
		String day = mailHandover.getFlightDate().toString().substring(0, 2);
		mLDDetailVO.setFlightDay(day);
		String key=mLDDetailVO.getCompanyCode()+mLDDetailVO.getCarrier()+mailHandover.getFlightNumber().substring(0, 4)+mailHandover.getFlightDate().toString().substring(0, 2);
		Map<String, String> mailIDFormatMap= new HashMap();
		mailIDFormatMap.put("mailtracking.defaults.trolleynumberidformat","${mail.carrier}${mail.flight}${mail.flightDay}");
		doReturn(mailIDFormatMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		String keyFormat="${mail.carrier}${mail.flight}${mail.flightDay}";
		Map<String, MLDDetailVO> templateObject = new HashMap<>();
		templateObject.put("mail", mLDDetailVO);
		doReturn("KeyCondition4").when(TemplateEncoderUtilInstance.getInstance()).encode(keyFormat, "mail", templateObject, false);
		dummyTrolleyNumberMap.put(key, serialNumber);
		serialNumber=mailUploadControllerSpy.generateTrolleyNumberForMRD(mailHandover,dummyTrolleyNumberMap); 
		assertNotNull(serialNumber);
	}
	


	@Test
	public void shouldInsertNewDamageDetailsWithDocumentOnDamageCapture() throws ProxyException, SystemException {
		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs = new ArrayList<DocumentRepositoryMasterVO>();

		when(documentRepositoryProxy.getDocumentsfromRepository(any(DocumentRepositoryFilterVO.class)))
				.thenReturn(documentRepositoryMasterVOs);

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag(MAILBAG_ID);
		mailUploadVO.setDamageCode("14");
		MailAttachmentVO attachmentVO = new MailAttachmentVO();
		attachmentVO.setAttachmentOpFlag("I");
		attachmentVO.setFileName("test.png");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		attachments.add(attachmentVO);
		mailUploadVO.setAttachments(attachments);

		mailUploadControllerSpy.uploadDocumentsToRepository(mailUploadVO);

	}

	@Test
	public void doThrowProxyExceptionWhileInvokingDocumentRepository() throws SystemException, ProxyException {

		when(documentRepositoryProxy.getDocumentsfromRepository(any(DocumentRepositoryFilterVO.class)))
				.thenThrow(ProxyException.class);

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag(MAILBAG_ID);
		mailUploadVO.setDamageCode("14");
		MailAttachmentVO attachmentVO = new MailAttachmentVO();
		attachmentVO.setAttachmentOpFlag("I");
		attachmentVO.setFileName("test.png");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		attachments.add(attachmentVO);
		mailUploadVO.setAttachments(attachments);

		mailUploadControllerSpy.uploadDocumentsToRepository(mailUploadVO);

	}

	@Test
	public void shouldNotInsertWhenDamageDetailsisEmpty() throws ProxyException, SystemException {
		DocumentRepositoryFilterVO documentRepositoryFilterVO = new DocumentRepositoryFilterVO();
		documentRepositoryFilterVO.setCompanyCode(null);
		documentRepositoryFilterVO.setDocumentType("MAL");
		documentRepositoryFilterVO.setPurpose("DMG");
		documentRepositoryFilterVO.setReference1("MALIDR");
		documentRepositoryFilterVO.setTransactionDataRef1(MAILBAG_ID);
		documentRepositoryFilterVO.setReference2("DMGCOD");
		documentRepositoryFilterVO.setTransactionDataRef2("14");
		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs = new ArrayList<DocumentRepositoryMasterVO>();

		when(documentRepositoryProxy.getDocumentsfromRepository(any(DocumentRepositoryFilterVO.class)))
				.thenReturn(documentRepositoryMasterVOs);

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag(MAILBAG_ID);
		mailUploadVO.setDamageCode("14");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		mailUploadVO.setAttachments(attachments);

		mailUploadControllerSpy.uploadDocumentsToRepository(mailUploadVO);

	}

	@Test
	public void shouldNotInsertWhenDamageDetailsIsNull() throws ProxyException, SystemException {
		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs = new ArrayList<DocumentRepositoryMasterVO>();

		when(documentRepositoryProxy.getDocumentsfromRepository(any(DocumentRepositoryFilterVO.class)))
				.thenReturn(documentRepositoryMasterVOs);

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag(MAILBAG_ID);
		mailUploadVO.setDamageCode("14");
		mailUploadVO.setAttachments(null);

		mailUploadControllerSpy.uploadDocumentsToRepository(mailUploadVO);

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

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag("USDFWAUSLAXAACA87488561004123");
		mailUploadVO.setDamageCode("4");
		MailAttachmentVO attachmentVO = new MailAttachmentVO();
		attachmentVO.setAttachmentOpFlag("I");
		attachmentVO.setFileName("test.png");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		attachments.add(attachmentVO);
		mailUploadVO.setAttachments(attachments);

		mailUploadControllerSpy.uploadDocumentsToRepository(mailUploadVO);

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

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag("USDFWAUSLAXAACA87488561004123");
		mailUploadVO.setDamageCode("4");
		MailAttachmentVO attachmentVO = new MailAttachmentVO();
		attachmentVO.setAttachmentOpFlag("");
		attachmentVO.setFileName("test.png");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		attachments.add(attachmentVO);
		mailUploadVO.setAttachments(attachments);

		mailUploadControllerSpy.uploadDocumentsToRepository(mailUploadVO);

	}

	@Test
	public void shouldNotUpdateDamageDetailsWhenAttachmentsAreNull() throws ProxyException, SystemException {

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

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag("USDFWAUSLAXAACA87488561004123");
		mailUploadVO.setDamageCode("4");
		mailUploadVO.setAttachments(null);

		mailUploadControllerSpy.uploadDocumentsToRepository(mailUploadVO);

	}

	@Test
	public void shouldNotUpdateDamageDetailsWhenAttachmentsAreEmpty() throws ProxyException, SystemException {

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

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag("USDFWAUSLAXAACA87488561004123");
		mailUploadVO.setDamageCode("4");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		mailUploadVO.setAttachments(attachments);

		mailUploadControllerSpy.uploadDocumentsToRepository(mailUploadVO);

	}

	@Test
	public void doThrowWhenUpdateDamageDetailsWhenAttachmentsAreAvaiable() throws ProxyException, SystemException {

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
		when(documentRepositoryProxy.uploadMultipleDocumentsToRepository(any(Collection.class)))
				.thenThrow(ProxyException.class);

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag("USDFWAUSLAXAACA87488561004123");
		mailUploadVO.setDamageCode("4");
		List<MailAttachmentVO> attachments = new ArrayList<>();
		mailUploadVO.setAttachments(attachments);

		mailUploadControllerSpy.uploadDocumentsToRepository(mailUploadVO);

	}
	
	@Test
	public void handleInvalidErrorsWhenInvalidDOE() throws SystemException{
		boolean isValidDoe=false;
		boolean isValidFlightCarrier =true;
		boolean isValidHandoverCarrier = true;
		boolean isValidFlight=true;
		boolean isValidFlightSegment =true;
		boolean isValidFlighOrigin=true;
		HandoverVO handoverVO = new HandoverVO();
		handoverVO.setCompanyCode("DNAE");
		handoverVO.setOrigin("CDG");
		handoverVO.setDestination("DXB");
		Collection<ErrorVO> errorVOs=mailUploadControllerSpy.handleInvalidErrors(isValidDoe,isValidFlightCarrier,isValidHandoverCarrier,isValidFlight,isValidFlightSegment,handoverVO,isValidFlighOrigin);
		assertNotNull(errorVOs);
	}
	@Test
	public void handleInvalidErrorsWhenInvalidAirport() throws SystemException{
		boolean isValidDoe=true;
		boolean isValidFlightCarrier =true;
		boolean isValidHandoverCarrier = true;
		boolean isValidFlight=true;
		boolean isValidFlightSegment =true;
		boolean isValidFlighOrigin=true;
		HandoverVO handoverVO = new HandoverVO();
		handoverVO.setCompanyCode("DNAE");
		handoverVO.setOrigin("CDG");
		handoverVO.setDestination("DXB");
		Collection<ErrorVO> errorVOs=mailUploadControllerSpy.handleInvalidErrors(isValidDoe,isValidFlightCarrier,isValidHandoverCarrier,isValidFlight,isValidFlightSegment,handoverVO,isValidFlighOrigin);
		assertNotNull(errorVOs);
	}
	@Test
	public void handleInvalidErrorsWhenInvalidFlightCarrier() throws SystemException{
		boolean isValidDoe=true;
		boolean isValidFlightCarrier =false;
		boolean isValidHandoverCarrier = true;
		boolean isValidFlight=true;
		boolean isValidFlightSegment =true;
		boolean isValidFlighOrigin=true;
		HandoverVO handoverVO = new HandoverVO();
		handoverVO.setCompanyCode("DNAE");
		handoverVO.setOrigin("CDG");
		handoverVO.setDestination("DXB");
		Collection<ErrorVO> errorVOs=mailUploadControllerSpy.handleInvalidErrors(isValidDoe,isValidFlightCarrier,isValidHandoverCarrier,isValidFlight,isValidFlightSegment,handoverVO,isValidFlighOrigin);
		assertNotNull(errorVOs);
	}
	@Test
	public void handleInvalidErrorsWhenInvalidHandoverCarrier() throws SystemException{
		boolean isValidDoe=true;
		boolean isValidFlightCarrier =true;
		boolean isValidHandoverCarrier = false;
		boolean isValidFlight=true;
		boolean isValidFlightSegment =true;
		boolean isValidFlighOrigin=true;
		HandoverVO handoverVO = new HandoverVO();
		handoverVO.setCompanyCode("DNAE");
		handoverVO.setOrigin("CDG");
		handoverVO.setDestination("DXB");
		Collection<ErrorVO> errorVOs=mailUploadControllerSpy.handleInvalidErrors(isValidDoe,isValidFlightCarrier,isValidHandoverCarrier,isValidFlight,isValidFlightSegment,handoverVO,isValidFlighOrigin);
		assertNotNull(errorVOs);
	}
	@Test
	public void handleInvalidErrorsWhenInvalidFlight() throws SystemException{
		boolean isValidDoe=true;
		boolean isValidFlightCarrier =true;
		boolean isValidHandoverCarrier = true;
		boolean isValidFlight=false;
		boolean isValidFlightSegment =true;
		boolean isValidFlighOrigin=true;
		HandoverVO handoverVO = new HandoverVO();
		handoverVO.setCompanyCode("DNAE");
		handoverVO.setOrigin("CDG");
		handoverVO.setDestination("DXB");
		Collection<ErrorVO> errorVOs=mailUploadControllerSpy.handleInvalidErrors(isValidDoe,isValidFlightCarrier,isValidHandoverCarrier,isValidFlight,isValidFlightSegment,handoverVO,isValidFlighOrigin);
		assertNotNull(errorVOs);
	}
	@Test
	public void handleInvalidErrorsWhenInvalidSegment() throws SystemException{
		boolean isValidDoe=true;
		boolean isValidFlightCarrier =true;
		boolean isValidHandoverCarrier = true;
		boolean isValidFlight=true;
		boolean isValidFlightSegment =false;
		boolean isValidFlighOrigin=true;
		HandoverVO handoverVO = new HandoverVO();
		handoverVO.setCompanyCode("DNAE");
		handoverVO.setOrigin("CDG");
		handoverVO.setDestination("DXB");
		Collection<ErrorVO> errorVOs=mailUploadControllerSpy.handleInvalidErrors(isValidDoe,isValidFlightCarrier,isValidHandoverCarrier,isValidFlight,isValidFlightSegment,handoverVO,isValidFlighOrigin);
		assertNotNull(errorVOs);
	}
	@Test
	public void handleInvalidErrorsWhenInvalidDestibation() throws SystemException{
		boolean isValidDoe=true;
		boolean isValidFlightCarrier =true;
		boolean isValidHandoverCarrier = true;
		boolean isValidFlight=true;
		boolean isValidFlightSegment =true;
		boolean isValidFlighOrigin=true;
		HandoverVO handoverVO = new HandoverVO();
		handoverVO.setCompanyCode("DNAE");
		handoverVO.setOrigin("CDG");
		handoverVO.setDestination("K");
		AirportValidationVO airportValidationVO = null;
		AirportValidationVO airportValidationVO1 = new AirportValidationVO();
		airportValidationVO1.setAirportCode("DXB");
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode("DNAE","XXX");
		doReturn(airportValidationVO1).when(sharedAreaProxy).validateAirportCode("DNAE","XXX");
		Collection<ErrorVO> errorVOs=mailUploadControllerSpy.handleInvalidErrors(isValidDoe,isValidFlightCarrier,isValidHandoverCarrier,isValidFlight,isValidFlightSegment,handoverVO,isValidFlighOrigin);
		assertNotNull(errorVOs);
	}

	@Test
	public void handleInvalidHandoverWhenNoInvalidErrors() throws SystemException {
		HashMap<String,Collection<HandoverVO> >handovers = new HashMap<>();
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setHandovers(handovers);
		mailUploadControllerSpy.handleInvalidHandover(mailMRDMessageVO);
	}
	@Test
	public void handleInvalidHandoverWhenInvalidOOE() throws SystemException, PersistenceException {
		Collection<HandoverVO> handoverList = new ArrayList<>();
		boolean validateAirport=true;
		HandoverVO handover = new HandoverVO();
		OfficeOfExchangeVO officeOfExchange = null;
		handover.setHandOverType("POC");
		handoverList.add(handover);
		HashMap<String,Collection<HandoverVO> >handovers = new HashMap<>();
		handovers.put("invalidhandovers", handoverList);
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setHandovers(handovers);
		mailMRDMessageVO.setCompanyCode("DNAE");
		doReturn(officeOfExchange).when(dao).validateOfficeOfExchange(any(String.class),any(String.class));
		when(mailUploadControllerSpy.validateAirport(any(String.class),any(String.class))).thenReturn(validateAirport);
		mailUploadControllerSpy.handleInvalidHandover(mailMRDMessageVO);
	}
	@Test
	public void handleInvalidHandoverWhenInvalidOOEFORPOD() throws SystemException, PersistenceException {
		Collection<HandoverVO> handoverList = new ArrayList<>();
		boolean validateAirport=true;
		HandoverVO handover = new HandoverVO();
		OfficeOfExchangeVO officeOfExchange = null;
		handover.setHandOverType("POD");
		handoverList.add(handover);
		HashMap<String,Collection<HandoverVO> >handovers = new HashMap<>();
		handovers.put("invalidhandovers", handoverList);
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setHandovers(handovers);
		mailMRDMessageVO.setCompanyCode("DNAE");
		doReturn(officeOfExchange).when(dao).validateOfficeOfExchange(any(String.class),any(String.class));
		when(mailUploadControllerSpy.validateAirport(any(String.class),any(String.class))).thenReturn(validateAirport);
		mailUploadControllerSpy.handleInvalidHandover(mailMRDMessageVO);
	}
	@Test
	public void handleInvalidHandoverWhenInvalidAirport() throws SystemException, PersistenceException {
		Collection<HandoverVO> handoverList = new ArrayList<>();
		HandoverVO handover = new HandoverVO();
		OfficeOfExchangeVO officeOfExchange = new OfficeOfExchangeVO();
		handover.setHandOverType("POC");
		handoverList.add(handover);
		HashMap<String,Collection<HandoverVO> >handovers = new HashMap<>();
		handovers.put("invalidhandovers", handoverList);
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setHandovers(handovers);
		mailMRDMessageVO.setCompanyCode("DNAE");
		doReturn(officeOfExchange).when(dao).validateOfficeOfExchange(any(String.class),any(String.class));
		when(mailUploadControllerSpy.validateAirport(any(String.class),any(String.class))).thenReturn(false);
		mailUploadControllerSpy.handleInvalidHandover(mailMRDMessageVO);
	}
	@Test
	public void handleInvalidHandoverWhenInvalidHandoversEmpty() throws SystemException, PersistenceException {
		Collection<HandoverVO> handoverList = new ArrayList<>();
		HandoverVO handover = new HandoverVO();
		OfficeOfExchangeVO officeOfExchange = new OfficeOfExchangeVO();
		handover.setHandOverType("POC");
		HashMap<String,Collection<HandoverVO> >handovers = new HashMap<>();
		handovers.put("invalidhandovers", handoverList);
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setHandovers(handovers);
		mailMRDMessageVO.setCompanyCode("DNAE");
		doReturn(officeOfExchange).when(dao).validateOfficeOfExchange(any(String.class),any(String.class));
		when(mailUploadControllerSpy.validateAirport(any(String.class),any(String.class))).thenReturn(false);
		mailUploadControllerSpy.handleInvalidHandover(mailMRDMessageVO);
	}
	@Test
	public void handleInvalidHandoverWhenHandoverTimeIsNull() throws SystemException, PersistenceException {
		Collection<HandoverVO> handoverList = new ArrayList<>();
		HandoverVO handover = new HandoverVO();
		OfficeOfExchangeVO officeOfExchange = new OfficeOfExchangeVO();
		handover.setHandOverType("POC");
		LocalDate date=null;
		handover.setHandOverdate_time(date);
		handoverList.add(handover);
		HashMap<String,Collection<HandoverVO> >handovers = new HashMap<>();
		handovers.put("invalidhandovers", handoverList);
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setHandovers(handovers);
		mailMRDMessageVO.setCompanyCode("DNAE");
		doReturn(officeOfExchange).when(dao).validateOfficeOfExchange(any(String.class),any(String.class));
		when(mailUploadControllerSpy.validateAirport(any(String.class),any(String.class))).thenReturn(true);
		mailUploadControllerSpy.handleInvalidHandover(mailMRDMessageVO);
	}
	@Test
	public void handleInvalidHandoverWhenHandoverTimeIsNotNull() throws SystemException, PersistenceException {
		Collection<HandoverVO> handoverList = new ArrayList<>();
		HandoverVO handover = new HandoverVO();
		OfficeOfExchangeVO officeOfExchange = new OfficeOfExchangeVO();
		handover.setHandOverType("POC");
		LocalDate date =new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		handover.setHandOverdate_time(date);
		handoverList.add(handover);
		HashMap<String,Collection<HandoverVO> >handovers = new HashMap<>();
		handovers.put("invalidhandovers", handoverList);
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setHandovers(handovers);
		mailMRDMessageVO.setCompanyCode("DNAE");
		doReturn(officeOfExchange).when(dao).validateOfficeOfExchange(any(String.class),any(String.class));
		when(mailUploadControllerSpy.validateAirport(any(String.class),any(String.class))).thenReturn(true);
		mailUploadControllerSpy.handleInvalidHandover(mailMRDMessageVO);
	}
	@Test
	public void handleInvalidMailbagErrorsWhenNoOnetimes() throws ProxyException, SystemException{
		HashMap<String, Collection<OneTimeVO>> oneTimes= null;
		String mailbag="CDGOMAUSDFWAACA95484950AAA027";
		HandoverVO handover = new HandoverVO();
		handover.setCompanyCode("DNAE");
		Object[] obj = new Object[2];
		obj[0]=mailbag;
		obj[1]="1234";
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),any(ArrayList.class))).thenReturn(null);
		mailUploadControllerSpy.handleInvalidMailbagErrors(mailbag,handover,obj);
	}
	@Test
	public void handleInvalidMailbagAsValidR1Value() throws ProxyException, SystemException{
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		String mailbag="CDGOMAUSDFWAACA95484950AAA027";
		HandoverVO handover = new HandoverVO();
		handover.setCompanyCode("DNAE");
		Object[] obj = new Object[2];
		obj[0]=mailbag;
		obj[1]="1234";
		OneTimeVO RIoneTime= new OneTimeVO();
		RIoneTime.setFieldValue("A");
		OneTimeVO HNIoneTime= new OneTimeVO();
		HNIoneTime.setFieldValue("A1");
		OneTimeVO categoryOneTime= new OneTimeVO();
		categoryOneTime.setFieldValue("A1");
		Collection<OneTimeVO> RIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> HNIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> categoryFieldValues= new ArrayList<>();
		RIFieldValues.add(RIoneTime);
		HNIFieldValues.add(HNIoneTime);
		categoryFieldValues.add(categoryOneTime);
		oneTimes.put("mailtracking.defaults.registeredorinsuredcode",RIFieldValues);
		oneTimes.put("mailtracking.defaults.highestnumbermail",HNIFieldValues);
		oneTimes.put("mailtracking.defaults.mailcategory",categoryFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),any(ArrayList.class))).thenReturn(oneTimes);
		mailUploadControllerSpy.handleInvalidMailbagErrors(mailbag,handover,obj);
	}
	@Test
	public void handleInvalidMailbagAsInValidR1Value() throws ProxyException, SystemException{
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		String mailbag="CDGOMAUSDFWAACA95484950BBB027";
		HandoverVO handover = new HandoverVO();
		handover.setCompanyCode("DNAE");
		Object[] obj = new Object[2];
		obj[0]=mailbag;
		obj[1]="1234";
		OneTimeVO RIoneTime= new OneTimeVO();
		RIoneTime.setFieldValue("A");
		OneTimeVO HNIoneTime= new OneTimeVO();
		HNIoneTime.setFieldValue("9");
		OneTimeVO categoryOneTime= new OneTimeVO();
		categoryOneTime.setFieldValue("A");
		Collection<OneTimeVO> RIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> HNIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> categoryFieldValues= new ArrayList<>();
		RIFieldValues.add(RIoneTime);
		HNIFieldValues.add(HNIoneTime);
		categoryFieldValues.add(categoryOneTime);
		oneTimes.put("mailtracking.defaults.registeredorinsuredcode",RIFieldValues);
		oneTimes.put("mailtracking.defaults.highestnumbermail",HNIFieldValues);
		oneTimes.put("mailtracking.defaults.mailcategory",categoryFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),any(ArrayList.class))).thenReturn(oneTimes);
		mailUploadControllerSpy.handleInvalidMailbagErrors(mailbag,handover,obj);
	}
	@Test
	public void handleInvalidMailbagAsValidHNIValue() throws ProxyException, SystemException{
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		String mailbag="CDGOMAUSDFWAACA95484950999027";
		HandoverVO handover = new HandoverVO();
		handover.setCompanyCode("DNAE");
		Object[] obj = new Object[2];
		obj[0]=mailbag;
		obj[1]="1234";
		OneTimeVO RIoneTime= new OneTimeVO();
		RIoneTime.setFieldValue("A");
		OneTimeVO HNIoneTime= new OneTimeVO();
		HNIoneTime.setFieldValue("9");
		OneTimeVO categoryOneTime= new OneTimeVO();
		categoryOneTime.setFieldValue("A");
		Collection<OneTimeVO> RIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> HNIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> categoryFieldValues= new ArrayList<>();
		RIFieldValues.add(RIoneTime);
		HNIFieldValues.add(HNIoneTime);
		categoryFieldValues.add(categoryOneTime);
		oneTimes.put("mailtracking.defaults.registeredorinsuredcode",RIFieldValues);
		oneTimes.put("mailtracking.defaults.highestnumbermail",HNIFieldValues);
		oneTimes.put("mailtracking.defaults.mailcategory",categoryFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),any(ArrayList.class))).thenReturn(oneTimes);
		mailUploadControllerSpy.handleInvalidMailbagErrors(mailbag,handover,obj);
	}
	@Test
	public void handleInvalidMailbagAsInValidHNIValue() throws ProxyException, SystemException{
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		String mailbag="CDGOMAUSDFWAACA95484950BAAA17";
		HandoverVO handover = new HandoverVO();
		handover.setCompanyCode("DNAE");
		Object[] obj = new Object[2];
		obj[0]=mailbag;
		obj[1]="1234";
		OneTimeVO RIoneTime= new OneTimeVO();
		RIoneTime.setFieldValue("A");
		OneTimeVO HNIoneTime= new OneTimeVO();
		HNIoneTime.setFieldValue("9");
		OneTimeVO categoryOneTime= new OneTimeVO();
		categoryOneTime.setFieldValue("A");
		Collection<OneTimeVO> RIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> HNIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> categoryFieldValues= new ArrayList<>();
		RIFieldValues.add(RIoneTime);
		HNIFieldValues.add(HNIoneTime);
		categoryFieldValues.add(categoryOneTime);
		oneTimes.put("mailtracking.defaults.registeredorinsuredcode",RIFieldValues);
		oneTimes.put("mailtracking.defaults.highestnumbermail",HNIFieldValues);
		oneTimes.put("mailtracking.defaults.mailcategory",categoryFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),any(ArrayList.class))).thenReturn(oneTimes);
		mailUploadControllerSpy.handleInvalidMailbagErrors(mailbag,handover,obj);
	}
	@Test
	public void handleInvalidMailbagAsValidCategoryValue() throws ProxyException, SystemException{
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		String mailbag="CDGOMAUSDFWAAAA15484950999027";
		HandoverVO handover = new HandoverVO();
		handover.setCompanyCode("DNAE");
		Object[] obj = new Object[2];
		obj[0]=mailbag;
		obj[1]="1234";
		OneTimeVO RIoneTime= new OneTimeVO();
		RIoneTime.setFieldValue("A");
		OneTimeVO HNIoneTime= new OneTimeVO();
		HNIoneTime.setFieldValue("9");
		OneTimeVO categoryOneTime= new OneTimeVO();
		categoryOneTime.setFieldValue("A");
		Collection<OneTimeVO> RIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> HNIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> categoryFieldValues= new ArrayList<>();
		RIFieldValues.add(RIoneTime);
		HNIFieldValues.add(HNIoneTime);
		categoryFieldValues.add(categoryOneTime);
		oneTimes.put("mailtracking.defaults.registeredorinsuredcode",RIFieldValues);
		oneTimes.put("mailtracking.defaults.highestnumbermail",HNIFieldValues);
		oneTimes.put("mailtracking.defaults.mailcategory",categoryFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),any(ArrayList.class))).thenReturn(oneTimes);
		mailUploadControllerSpy.handleInvalidMailbagErrors(mailbag,handover,obj);
	}
	@Test
	public void handleInvalidMailbagAsInValidCategoryValue() throws ProxyException, SystemException{
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		String mailbag="CDGOMAUSDFBBBBB95484950999027";
		HandoverVO handover = new HandoverVO();
		handover.setCompanyCode("DNAE");
		Object[] obj = new Object[2];
		obj[0]=mailbag;
		obj[1]="1234";
		OneTimeVO RIoneTime= new OneTimeVO();
		RIoneTime.setFieldValue("A");
		OneTimeVO HNIoneTime= new OneTimeVO();
		HNIoneTime.setFieldValue("9");
		OneTimeVO categoryOneTime= new OneTimeVO();
		categoryOneTime.setFieldValue("A");
		Collection<OneTimeVO> RIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> HNIFieldValues= new ArrayList<>();
		Collection<OneTimeVO> categoryFieldValues= new ArrayList<>();
		RIFieldValues.add(RIoneTime);
		HNIFieldValues.add(HNIoneTime);
		categoryFieldValues.add(categoryOneTime);
		oneTimes.put("mailtracking.defaults.registeredorinsuredcode",RIFieldValues);
		oneTimes.put("mailtracking.defaults.highestnumbermail",HNIFieldValues);
		oneTimes.put("mailtracking.defaults.mailcategory",categoryFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),any(ArrayList.class))).thenReturn(oneTimes);
		mailUploadControllerSpy.handleInvalidMailbagErrors(mailbag,handover,obj);
	}  
    @Test
	public void findExportFlightOperationsDetailsreturnflightoperationdetails() throws SystemException,PersistenceException {
		ManifestVO mailManifestVO = new ManifestVO();
		Collection<ManifestFilterVO> manifestfilterVOlist = new ArrayList<>(); 
		ManifestFilterVO manifestFilterVO= new ManifestFilterVO();
		manifestfilterVOlist.add(manifestFilterVO);
		Collection<ManifestVO> inputflightoprnVOlist= new ArrayList<>();
		inputflightoprnVOlist.add(mailManifestVO);
		Collection<ManifestVO> inputflightoprnVOlist1= new ArrayList<>();
		doReturn(inputflightoprnVOlist).when(dao).findExportFlightOperationsDetails(any(ImportOperationsFilterVO.class),anyCollectionOf(ManifestFilterVO.class));
		inputflightoprnVOlist1= mailUploadControllerSpy.findExportFlightOperationsDetails(filterVO,manifestfilterVOlist); 
		assertNotNull(inputflightoprnVOlist1);	
		
	}

	
	 @Test
	 public void setContainerDetailsTest() throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, SystemException, FinderException{
		 ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		MailUploadVO mailUploadVo=new MailUploadVO();
		
		mailUploadVo.setSecurityMethods("ABC");
		mailUploadVo.setDateTime("05-May-2022 09:00:13");
		mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		LogonAttributes logonAttributes= new LogonAttributes();
		String scanningPort="CDG";
		HashMap<String, String> map=new HashMap<>();
		map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
		map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
		mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
		mailUploadVo.setDsn("1170");
		mailUploadVo.setRegisteredIndicator("9");
		mailUploadVo.setHighestnumberIndicator("9");
		mailUploadVo.setCompanyCode(getCompanyCode());
		mailUploadVo.setTransactionLevel("M");
		mailUploadVo.setContainerNumber("AKE01268AV");
		mailUploadVo.setPaCode("Y");
		mailUploadVo.setToContainer("AKE01268AV");
		scannedMailDetailsVO.setProcessPoint("TRA");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
	
		mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
		 
	 }
	
	 @Test
	 public void setContainerDetailsTestWithNullCheck() throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, SystemException, FinderException{
		 ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		MailUploadVO mailUploadVo=new MailUploadVO();
		mailUploadVo.setSecurityMethods("ABC");
		mailUploadVo.setDateTime("05-May-2022 09:00:13");
	
		mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		LogonAttributes logonAttributes= new LogonAttributes();
		String scanningPort="CDG";
		HashMap<String, String> map=new HashMap<>();
		map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
		map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
		mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
		mailUploadVo.setDsn("1170");
		mailUploadVo.setRegisteredIndicator("9");
		mailUploadVo.setHighestnumberIndicator("9");
		mailUploadVo.setCompanyCode(getCompanyCode());
		
		mailUploadVo.setContainerNumber(null);
		mailUploadVo.setPaCode(null);
		mailUploadVo.setToContainer("AKE01268AV");
		scannedMailDetailsVO.setProcessPoint("RSGM");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
	
		mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
		 
	 }
	 @Test
	 public void setContainerDetailsTestEmptyCheck() throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, SystemException, FinderException{
		 ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		MailUploadVO mailUploadVo=new MailUploadVO();
		mailUploadVo.setSecurityMethods("ABC");
		mailUploadVo.setDateTime("05-May-2022 09:00:13");
	
		mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		LogonAttributes logonAttributes= new LogonAttributes();
		String scanningPort="CDG";
		HashMap<String, String> map=new HashMap<>();
		map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
		map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
		mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
		mailUploadVo.setDsn("1170");
		mailUploadVo.setRegisteredIndicator("9");
		mailUploadVo.setHighestnumberIndicator("9");
		mailUploadVo.setCompanyCode(getCompanyCode());
		
		mailUploadVo.setContainerNumber("");
		mailUploadVo.setPaCode("");
		mailUploadVo.setToContainer("AKE01268AV");
		scannedMailDetailsVO.setProcessPoint("TEST");
		
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
	
		mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
		 
	 }
	 @Test
	 public void setContainerDetailsTestToContainerCheck() throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, SystemException, FinderException{
		 ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		MailUploadVO mailUploadVo=new MailUploadVO();
		mailUploadVo.setSecurityMethods("ABC");
		mailUploadVo.setDateTime("05-May-2022 09:00:13");
	
		mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		LogonAttributes logonAttributes= new LogonAttributes();
		String scanningPort="CDG";
		HashMap<String, String> map=new HashMap<>();
		map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
		map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
		mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
		mailUploadVo.setDsn("1170");
		mailUploadVo.setRegisteredIndicator("9");
		mailUploadVo.setHighestnumberIndicator("9");
		mailUploadVo.setCompanyCode(getCompanyCode());
		
		mailUploadVo.setContainerNumber("");
		mailUploadVo.setPaCode("");
		mailUploadVo.setToContainer(null);
		scannedMailDetailsVO.setProcessPoint("TEST");
		
		
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
	
		mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
		 
	 }
	 

		@Test
	    public void  validateFromFileInbound() throws ProxyException, SystemException{
	    	FileUploadFilterVO fileUploadFilterVO=new FileUploadFilterVO();
	    	fileUploadFilterVO.setCompanyCode(getCompanyCode());
	  
	    	MailUploadVO mailUploadVO = new MailUploadVO();
	    	mailUploadVO.setContainerNumber("AKE01268AV");
				
	    	mailUploadVO.setMailTag("FRCDGADEFRAAAUA01170604990888");
	    	mailUploadVO.setContainerPol("CDG");
	    	mailUploadVO.setContainerPOU("FRA");
	    	mailUploadVO.setContainerType("U");
	    	mailUploadVO.setPaCode("X");
	    	mailUploadVO.setFlightNumber("0607");
	    	LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
	    	mailUploadVO.setFlightDate(flightDate);
	    	
	    	MailUploadVO mailUploadVO1 = new MailUploadVO();
	    	mailUploadVO1.setContainerNumber("AKE01268AV");
	    	mailUploadVO1.setPaCode("Y");
	    	Collection<MailUploadVO> value= new ArrayList<>();
	    	value.add(mailUploadVO);
	    	value.add(mailUploadVO1);
	    	
			when(MailScanDetail
			.fetchDataForOfflineUpload(fileUploadFilterVO.getCompanyCode(), fileUploadFilterVO.getFileType())).thenReturn(value);
	    	mailUploadControllerSpy.validateFromFile(fileUploadFilterVO);
	    	
	    	
	    }
	
		@Test
	    public void  validateFromFileInboundNullCheck() throws ProxyException, SystemException{
	    	FileUploadFilterVO fileUploadFilterVO=new FileUploadFilterVO();
	    	fileUploadFilterVO.setCompanyCode(getCompanyCode());
	  
	    
	    	Collection<MailUploadVO> value= new ArrayList<>();
	    	value.add(null);
	    
	    	
			when(MailScanDetail
			.fetchDataForOfflineUpload(fileUploadFilterVO.getCompanyCode(), fileUploadFilterVO.getFileType())).thenReturn(value);
	    	mailUploadControllerSpy.validateFromFile(fileUploadFilterVO);
	    	
	    	
	    }
		@Test
	    public void  validateFromFileInboundValidatePA() throws ProxyException, SystemException{
	    	FileUploadFilterVO fileUploadFilterVO=new FileUploadFilterVO();
	    	fileUploadFilterVO.setCompanyCode(getCompanyCode());
	  
	    	MailUploadVO mailUploadVO = new MailUploadVO();
	    	mailUploadVO.setContainerNumber("AKE01268AV");
				
	    	mailUploadVO.setMailTag("FRCDGADEFRAAAUA01170604990888");
	    	mailUploadVO.setContainerPol("CDG");
	    	mailUploadVO.setContainerPOU("FRA");
	    	mailUploadVO.setContainerType("U");
	    	mailUploadVO.setPaCode("Y");
	    	mailUploadVO.setFlightNumber("0607");
	    	LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
	    	mailUploadVO.setFlightDate(flightDate);
	    	
	    	MailUploadVO mailUploadVO1 = new MailUploadVO();
	    	mailUploadVO1.setContainerNumber("AKE01268AV");
	    	mailUploadVO1.setPaCode("Y");
	    	Collection<MailUploadVO> value= new ArrayList<>();
	    	value.add(mailUploadVO);
	    	value.add(mailUploadVO1);
	    	
			when(MailScanDetail
			.fetchDataForOfflineUpload(fileUploadFilterVO.getCompanyCode(), fileUploadFilterVO.getFileType())).thenReturn(value);
	    	mailUploadControllerSpy.validateFromFile(fileUploadFilterVO);
	    	
	    	
	    }

    
    @Test
    public void constructVOAndSaveDetails_ContainerJourneyIdNotNull() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("U");
		containerVo.setFromDeviationList(true);
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerEntity = new Container(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		containerEntity.setAcceptanceFlag("Y");
		containerEntity.setTransitFlag("Y");
		
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	
    	
    	uldValidationVO.setUldNumber("AKE12345AV");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVO.setCompanyCode(getCompanyCode());
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ACP");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("MLD");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		BusinessException be = new BusinessException() {};
		MailHHTBusniessException ex = new MailHHTBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO(MailMLDBusniessException.INVALID_ULD_FORMAT);
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_ContainerJourneyIdNull() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	officeOfExchangeVO.setPoaCode("DE101");
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	
    	ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("U");
		containerVo.setFromDeviationList(true);
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerEntity = new Container(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		containerEntity.setAcceptanceFlag("Y");
		containerEntity.setTransitFlag("Y");
		
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	
    	uldValidationVO.setUldNumber("AKE12345AV");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVO.setCompanyCode(getCompanyCode());
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	mailUploadVO.setScanType("ACP");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId(null);
    	mailUploadVO.setContaineDescription("CONTAINER_DELIVERY");
    	mailUploadVO.setMailSource("WS");
    	mailUploadVO.setExpRsn(true);
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("B");
    	mailBagVOs.add(mailUploadVO);
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(uldValidationVO).when(sharedULDProxy).validateULD(getCompanyCode(),uldValidationVO.getUldNumber());
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_SettingFlightValidationsForMailSource() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	Collection<FlightValidationVO> flightValidationVOS = new ArrayList<>();
    	FlightValidationVO flightValidationVO = new FlightValidationVO();
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	
    	ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("U");
		containerVo.setFromDeviationList(true);
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerEntity = new Container(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		containerEntity.setAcceptanceFlag("Y");
		containerEntity.setTransitFlag("Y");
		
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	
    	uldValidationVO.setUldNumber("AKE12345AV");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVOs.add(flightFilterVO);
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	flightValidationVO.setLegOrigin("CDG");
    	flightValidationVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightValidationVO.setFlightRoute("FRA-DFW");
    	flightValidationVO.setCompanyCode(getCompanyCode());
    	flightValidationVOS.add(flightValidationVO);
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	mailUploadVO.setScanType("ACP");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setMailSource("WS");
    	mailUploadVO.setExpRsn(false);
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setFlightValidationVOS(flightValidationVOS);
    	mailUploadVO.setContainerType("U");
    	mailBagVOs.add(mailUploadVO);
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		BusinessException be = new BusinessException() {};
		MailHHTBusniessException ex = new MailHHTBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO(MailMLDBusniessException.INVALID_ULD_FORMAT);
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_ForAcceptedCaseOfMLD() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	
    	ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("U");
		containerVo.setFromDeviationList(true);
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerEntity = new Container(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		containerEntity.setAcceptanceFlag("Y");
		containerEntity.setTransitFlag("Y");
		
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	
    	uldValidationVO.setUldNumber("AKE12345AV");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVO.setCompanyCode(getCompanyCode());
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ACP");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("MRD");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		BusinessException be = new BusinessException() {};
		MailHHTBusniessException ex = new MailHHTBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO(MailMLDBusniessException.INVALID_ULD_FORMAT);
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_ScanTypeArrival() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException, PersistenceException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	
    	ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("U");
		containerVo.setFromDeviationList(true);
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerEntity = new Container(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		containerEntity.setAcceptanceFlag("Y");
		containerEntity.setTransitFlag("Y");
		
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	
    	uldValidationVO.setUldNumber("AKE12345AV");
    	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
    	flightSegmentSummaryVO.setSegmentOrigin("CDG");
    	flightSegmentSummaryVO.setSegmentDestination("CDG");
    	flightSegmentSummaryVO.setSegmentSerialNumber(1);
    	flightSegments.add(flightSegmentSummaryVO);
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setLegDestination("CDG");
    	flightFilterVO.setFlightSequenceNumber(12);
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	containerAssignmentVO.setAirportCode("CDG");
    	containerAssignmentVO.setFlightNumber("AKE12345AV");
    	containerAssignmentVO.setFlightSequenceNumber(12);
    	containerAssignmentVO.setSegmentSerialNumber(1);
    	containerAssignmentVO.setUldReferenceNo(1);
    	officeOfExchangeVO.setPoaCode("FR001");
    	officeOfExchangeVO.setAirportCode("CDG");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ARR");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("EXCELUPL");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailUploadVO.setContainerPOU("CDG");
    	mailUploadVO.setFlightSequenceNumber(12);
    	mailUploadVO.setFromPol("CDG");
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Long.class));
		doReturn(assignedFlight).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(uldForSegment).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerAssignmentVO).when(mailController).findLatestContainerAssignment(any(String.class));
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_ScanTypeArrivalWithNoContainer() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException, PersistenceException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	uldValidationVO.setUldNumber("AKE12345AV");
    	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
    	flightSegmentSummaryVO.setSegmentOrigin("CDG");
    	flightSegmentSummaryVO.setSegmentDestination("CDG");
    	flightSegmentSummaryVO.setSegmentSerialNumber(1);
    	flightSegments.add(flightSegmentSummaryVO);
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setLegDestination("CDG");
    	flightFilterVO.setFlightSequenceNumber(12);
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	containerAssignmentVO.setAirportCode("CDG");
    	containerAssignmentVO.setFlightNumber("AKE12345AV");
    	containerAssignmentVO.setFlightSequenceNumber(12);
    	containerAssignmentVO.setSegmentSerialNumber(1);
    	officeOfExchangeVO.setPoaCode("FR001");
    	officeOfExchangeVO.setAirportCode("CDG");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ARR");
    	mailUploadVO.setContainerNumber(null);
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("EXCELUPL");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType(null);
    	mailUploadVO.setContainerPOU("CDG");
    	mailUploadVO.setFlightSequenceNumber(12);
    	mailUploadVO.setFromPol("CDG");
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Long.class));
		doReturn(assignedFlight).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(uldForSegment).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerAssignmentVO).when(mailController).findLatestContainerAssignment(any(String.class));
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_ScanTypeArrivalWithBulk() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException, PersistenceException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("U");
		containerVo.setFromDeviationList(true);
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerEntity = new Container(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		containerEntity.setAcceptanceFlag("Y");
		containerEntity.setTransitFlag("Y");
		
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	uldValidationVO.setUldNumber("AKE12345AV");
    	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
    	flightSegmentSummaryVO.setSegmentOrigin("CDG");
    	flightSegmentSummaryVO.setSegmentDestination("CDG");
    	flightSegmentSummaryVO.setSegmentSerialNumber(1);
    	flightSegments.add(flightSegmentSummaryVO);
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setLegDestination("CDG");
    	flightFilterVO.setFlightSequenceNumber(12);
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	containerAssignmentVO.setAirportCode("CDG");
    	containerAssignmentVO.setFlightNumber("AKE12345AV");
    	containerAssignmentVO.setFlightSequenceNumber(12);
    	containerAssignmentVO.setSegmentSerialNumber(1);
    	officeOfExchangeVO.setPoaCode("FR001");
    	officeOfExchangeVO.setAirportCode("CDG");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ARR");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("EXCELUPL");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("B");
    	mailUploadVO.setContainerPOU("CDG");
    	mailUploadVO.setFlightSequenceNumber(12);
    	mailUploadVO.setFromPol("CDG");
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Long.class));
		doReturn(assignedFlight).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(uldForSegment).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerAssignmentVO).when(mailController).findLatestContainerAssignment(any(String.class));
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_ScanTypeArrivalWithPOLs() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException, PersistenceException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	
    	ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("U");
		containerVo.setFromDeviationList(true);
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerEntity = new Container(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		containerEntity.setAcceptanceFlag("Y");
		containerEntity.setTransitFlag("Y");
		
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	uldValidationVO.setUldNumber("AKE12345AV");
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
    	flightSegmentSummaryVO.setSegmentOrigin("CDG");
    	flightSegmentSummaryVO.setSegmentDestination("CDG");
    	flightSegmentSummaryVO.setSegmentSerialNumber(1);
    	flightSegments.add(flightSegmentSummaryVO);
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setLegDestination("CDG");
    	flightFilterVO.setFlightSequenceNumber(12);
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setPoaCode("FR001");
    	officeOfExchangeVO.setAirportCode("CDG");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ARR");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("EXCELUPL");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailUploadVO.setContainerPOU("CDG");
    	mailUploadVO.setFlightSequenceNumber(12);
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Long.class));
		doReturn(assignedFlight).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(uldForSegment).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_MailSourceEXCELUPLAndScanTypeNotArrival() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	uldValidationVO.setUldNumber("AKE12345AV");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("DLV");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("EXCELUPL");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_MailSourceMTKMALUPLJOB() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	uldValidationVO.setUldNumber("AKE12345AV");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("DLV");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("MTKMALUPLJOB");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_MailSourceMTKMALUPLJOBAndScannedPortNull() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	uldValidationVO.setUldNumber("AKE12345AV");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("DLV");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("MTKMALUPLJOB");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("B");
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,null);
    }
    
    @Test
    public void constructVOAndSaveDetails_ProcessPointExport() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException, PersistenceException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	
    	ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("U");
		containerVo.setFromDeviationList(true);
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerEntity = new Container(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		containerEntity.setAcceptanceFlag("Y");
		containerEntity.setTransitFlag("Y");
		
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	uldValidationVO.setUldNumber("AKE12345AV");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVO.setCompanyCode(getCompanyCode());
    	flightFilterVOs.add(flightFilterVO);
    	containerAssignmentVO.setAirportCode("CDG");
    	containerAssignmentVO.setFlightNumber("AKE12345AV");
    	containerAssignmentVO.setFlightSequenceNumber(12);
    	containerAssignmentVO.setSegmentSerialNumber(1);
    	containerAssignmentVO.setContainerType("U");
    	containerAssignmentVO.setUldReferenceNo(1);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("EXP");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("MLD");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		BusinessException be = new BusinessException() {};
		MailHHTBusniessException ex = new MailHHTBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO(MailMLDBusniessException.INVALID_ULD_FORMAT);
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_OffloadProcessPoint() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	officeOfExchangeVO.setPoaCode("DE101");
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	uldValidationVO.setUldNumber("AKE12345AV");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVO.setCompanyCode(getCompanyCode());
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	mailUploadVO.setScanType("OFL");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId(null);
    	mailUploadVO.setContaineDescription("CONTAINER_DELIVERY");
    	mailUploadVO.setMailSource("WS");
    	mailUploadVO.setExpRsn(true);
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("B");
    	mailBagVOs.add(mailUploadVO);
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(uldValidationVO).when(sharedULDProxy).validateULD(getCompanyCode(),uldValidationVO.getUldNumber());
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_ContainerProcessPointReassign() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException, PersistenceException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	Collection<PartnerCarrierVO> partners = new ArrayList<>();
    	PartnerCarrierVO partnerCarrierVO = new PartnerCarrierVO();
    	partnerCarrierVO.setPartnerCarrierCode("IBS");
    	partners.add(partnerCarrierVO);
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	uldValidationVO.setUldNumber("AKE12345AV");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVO.setCompanyCode(getCompanyCode());
    	flightFilterVOs.add(flightFilterVO);
    	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
    	flightSegmentSummaryVO.setSegmentOrigin("CDG");
    	flightSegmentSummaryVO.setSegmentDestination("DFW");
    	flightSegmentSummaryVO.setSegmentSerialNumber(1);
    	flightSegments.add(flightSegmentSummaryVO);
    	containerAssignmentVO.setCompanyCode(getCompanyCode());
    	containerAssignmentVO.setAirportCode("CDG");
    	containerAssignmentVO.setFlightNumber("AKE12345AV");
    	containerAssignmentVO.setFlightSequenceNumber(-1);
    	containerAssignmentVO.setSegmentSerialNumber(1);
    	containerAssignmentVO.setContainerType("U");
    	containerAssignmentVO.setFlightNumber("AV1234");
    	containerAssignmentVO.setContainerNumber("AKE12345AV");
    	containerAssignmentVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
    	containerAssignmentVO.setAcceptanceFlag("N");
    	containerAssignmentVO.setCarrierId(1);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ACP");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("WS");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailUploadVO.setExpRsn(true);
    	mailBagVOs.add(mailUploadVO);  
    	
    	
    	ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVO.setReassignFlag(true);
		containerVO.setAssignedPort("CDG");
		containerVO.setUldReferenceNo(1);
        containerEntity = new Container(containerVO);
    	
    	containerEntity.setAssignedOn(Calendar.getInstance());
    	containerEntity.setContainerType("U");
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		doReturn(containerAssignmentVO).when(mailController).findLatestContainerAssignment(any(String.class));
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Long.class));
		BusinessException be = new BusinessException() {};
		MailHHTBusniessException ex = new MailHHTBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO(MailMLDBusniessException.INVALID_ULD_FORMAT);
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_ContainerProcessPointReassignHavingPOUForContainerAssignmentVO() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException, PersistenceException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	Collection<PartnerCarrierVO> partners = new ArrayList<>();
    	PartnerCarrierVO partnerCarrierVO = new PartnerCarrierVO();
    	partnerCarrierVO.setPartnerCarrierCode("IBS");
    	partners.add(partnerCarrierVO);
    	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
    	flightSegmentSummaryVO.setSegmentOrigin("CDG");
    	flightSegmentSummaryVO.setSegmentDestination("DFW");
    	flightSegmentSummaryVO.setSegmentSerialNumber(1);
    	flightSegments.add(flightSegmentSummaryVO);
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	uldValidationVO.setUldNumber("AKE12345AV");
    	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
    	containerAssignmentVO.setCompanyCode(getCompanyCode());
    	containerAssignmentVO.setArrivalFlag("N");
    	containerAssignmentVO.setContainerType("U");
    	containerAssignmentVO.setAirportCode("CDG");
    	containerAssignmentVO.setFlightNumber("AV1234");
    	containerAssignmentVO.setFlightSequenceNumber(-1);
    	containerAssignmentVO.setContainerNumber("AKE12345AV");
    	containerAssignmentVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
    	containerAssignmentVO.setAcceptanceFlag("N");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVO.setCompanyCode(getCompanyCode());
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ACP");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("WS");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailBagVOs.add(mailUploadVO);  
    	
    	ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVO.setReassignFlag(true);
		containerVO.setAssignedPort("CDG");
		containerVO.setUldReferenceNo(1);
        containerEntity = new Container(containerVO);
    	containerEntity.setAssignedOn(Calendar.getInstance());
    	containerEntity.setContainerType("U");
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Long.class));
		BusinessException be = new BusinessException() {};
		MailHHTBusniessException ex = new MailHHTBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO(MailMLDBusniessException.INVALID_ULD_FORMAT);
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void constructVOAndSaveDetails_ContainerProcessPointReassignHavingPOUForScannedMaildetailsVO() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException, PersistenceException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	Collection<PartnerCarrierVO> partners = new ArrayList<>();
    	PartnerCarrierVO partnerCarrierVO = new PartnerCarrierVO();
    	partnerCarrierVO.setPartnerCarrierCode("IBS");
    	partners.add(partnerCarrierVO);
    	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
    	flightSegmentSummaryVO.setSegmentOrigin("CDG");
    	flightSegmentSummaryVO.setSegmentDestination("DFW");
    	flightSegmentSummaryVO.setSegmentSerialNumber(1);
    	flightSegments.add(flightSegmentSummaryVO);
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	uldValidationVO.setUldNumber("AKE12345AV");
    	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
    	containerAssignmentVO.setCompanyCode(getCompanyCode());
    	containerAssignmentVO.setArrivalFlag("N");
    	containerAssignmentVO.setContainerType("U");
    	containerAssignmentVO.setAirportCode("CDG");
    	containerAssignmentVO.setFlightNumber("AV1234");
    	containerAssignmentVO.setFlightSequenceNumber(-1);
    	containerAssignmentVO.setContainerNumber("AKE12345AV");
    	containerAssignmentVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
    	containerAssignmentVO.setAcceptanceFlag("N");
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVO.setCompanyCode(getCompanyCode());
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("FRA");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ACP");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("WS");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailUploadVO.setContainerPOU("DFW");
    	mailBagVOs.add(mailUploadVO);  
    	
    	
    	ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVO.setReassignFlag(true);
		containerVO.setAssignedPort("CDG");
		containerVO.setUldReferenceNo(1);
        containerEntity = new Container(containerVO);
    	containerEntity.setAssignedOn(Calendar.getInstance());
    	containerEntity.setContainerType("U");
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Long.class));
		BusinessException be = new BusinessException() {};
		MailHHTBusniessException ex = new MailHHTBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO(MailMLDBusniessException.INVALID_ULD_FORMAT);
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetails(mailBagVOs,"CDG");
    }
    
    @Test
    public void saveMailUploadDetailsFromMLD_WithJourneyId() throws PersistenceException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException{
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		MLDMasterVO mldMasterVo = new MLDMasterVO();
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mldMasterVo.setBarcodeType("J");
		mldMasterVo.setBarcodeValue("J");
		mldMasterVo.setMessageVersion("2");
		mldMasterVo.setEventCOde("NST");
		mldMasterVo.setSenderAirport("CDG");
		mldMasterVo.setUldNumber("AKE12345AV");
		mldMasterVo.setContainerType("U");
		mLDDetailVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
		mldMasterVo.setMldDetailVO(mLDDetailVO);
		mldMasterVo.setScanTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mldMasterVOs.add(mldMasterVo);
    	containerAssignmentVO.setCompanyCode(getCompanyCode());
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		mailUploadControllerSpy.saveMailUploadDetailsFromMLD(mldMasterVOs);
    }
    
    @Test
    public void saveMailUploadDetailsFromMLD_WithoutJourneyIdToSatisfyAccetanceCondition() throws PersistenceException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException{
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		MLDMasterVO mldMasterVo = new MLDMasterVO();
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mldMasterVo.setBarcodeType("J");
		mldMasterVo.setBarcodeValue("J");
		mldMasterVo.setMessageVersion("2");
		mldMasterVo.setEventCOde("NST");
		mldMasterVo.setSenderAirport("CDG");
		mldMasterVo.setUldNumber("AKE12345AV");
		mldMasterVo.setContainerType("U");
		mldMasterVo.setMldDetailVO(mLDDetailVO);
		mldMasterVo.setScanTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mldMasterVOs.add(mldMasterVo);
    	containerAssignmentVO.setCompanyCode(getCompanyCode());
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		mailUploadControllerSpy.saveMailUploadDetailsFromMLD(mldMasterVOs);
    }
    
    @Test
    public void saveMailUploadDetailsFromMLD_WithBarcodeTypeNotEqualToJ() throws PersistenceException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException{
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		MLDMasterVO mldMasterVo = new MLDMasterVO();
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mldMasterVo.setBarcodeType("R");
		mldMasterVo.setBarcodeValue("J");
		mldMasterVo.setMessageVersion("2");
		mldMasterVo.setEventCOde("NST");
		mldMasterVo.setSenderAirport("CDG");
		mldMasterVo.setUldNumber("AKE12345AV");
		mldMasterVo.setContainerType("U");
		mLDDetailVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
		mldMasterVo.setMldDetailVO(mLDDetailVO);
		mldMasterVo.setScanTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mldMasterVOs.add(mldMasterVo);
    	containerAssignmentVO.setCompanyCode(getCompanyCode());
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		mailUploadControllerSpy.saveMailUploadDetailsFromMLD(mldMasterVOs);
    }
    
    @Test
    public void saveMailUploadDetailsFromMLD_WithcontainerAssignmentVOAsNullAndBarcodeTypeJ() throws PersistenceException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException{
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
		MLDMasterVO mldMasterVo = new MLDMasterVO();
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mldMasterVo.setBarcodeType("J");
		mldMasterVo.setBarcodeValue("J");
		mldMasterVo.setMessageVersion("2");
		mldMasterVo.setEventCOde("NST");
		mldMasterVo.setSenderAirport("CDG");
		mldMasterVo.setUldNumber("AKE12345AV");
		mldMasterVo.setContainerType("U");
		mLDDetailVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
		mldMasterVo.setMldDetailVO(mLDDetailVO);
		mldMasterVo.setScanTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mldMasterVOs.add(mldMasterVo);
		doReturn(null).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		mailUploadControllerSpy.saveMailUploadDetailsFromMLD(mldMasterVOs);
    }
    
    @Test
    public void saveMailUploadDetailsFromMLD_WithcontainerAssignmentVOAsNullAndBarcodeTypeU() throws PersistenceException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException{
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
		MLDMasterVO mldMasterVo = new MLDMasterVO();
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mldMasterVo.setBarcodeType("U");
		mldMasterVo.setBarcodeValue("J");
		mldMasterVo.setMessageVersion("2");
		mldMasterVo.setEventCOde("NST");
		mldMasterVo.setSenderAirport("CDG");
		mldMasterVo.setUldNumber("AKE12345AV");
		mldMasterVo.setContainerType("U");
		mLDDetailVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
		mldMasterVo.setMldDetailVO(mLDDetailVO);
		mldMasterVo.setScanTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mldMasterVOs.add(mldMasterVo);
		doReturn(null).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		mailUploadControllerSpy.saveMailUploadDetailsFromMLD(mldMasterVOs);
    }
    
    @Test
    public void saveMailUploadDetailsFromMLD_WithcontainerAssignmentVOAsNullAndBarcodeTypeR() throws PersistenceException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException{
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
		MLDMasterVO mldMasterVo = new MLDMasterVO();
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mldMasterVo.setBarcodeType("R");
		mldMasterVo.setBarcodeValue("J");
		mldMasterVo.setMessageVersion("2");
		mldMasterVo.setEventCOde("NST");
		mldMasterVo.setSenderAirport("CDG");
		mldMasterVo.setUldNumber("AKE12345AV");
		mldMasterVo.setContainerType("U");
		mLDDetailVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
		mldMasterVo.setMldDetailVO(mLDDetailVO);
		mldMasterVo.setScanTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mldMasterVOs.add(mldMasterVo);
		doReturn(null).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		mailUploadControllerSpy.saveMailUploadDetailsFromMLD(mldMasterVOs);
    }
    
    @Test
    public void saveMailUploadDetailsFromMLD_WithcontainerAssignmentVOAsNullAndBarcodeTypenull() throws PersistenceException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException{
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
		MLDMasterVO mldMasterVo = new MLDMasterVO();
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		mldMasterVo.setBarcodeType(null);
		mldMasterVo.setBarcodeValue("J");
		mldMasterVo.setMessageVersion("2");
		mldMasterVo.setEventCOde("NST");
		mldMasterVo.setSenderAirport("CDG");
		mldMasterVo.setUldNumber("AKE12345AV");
		mldMasterVo.setContainerType("U");
		mLDDetailVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
		mldMasterVo.setMldDetailVO(mLDDetailVO);
		mldMasterVo.setScanTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mldMasterVOs.add(mldMasterVo);
		doReturn(null).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		mailUploadControllerSpy.saveMailUploadDetailsFromMLD(mldMasterVOs); 
    }
    @Test()
      public void performWhileErrorStampingForFoundMailWebServicesForMailBag() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
       	MailUploadVO mailBagVO = new MailUploadVO();
       	mailBagVO.setCompanyCode("AV");
       	mailBagVO.setProcessPoint("DLV");
       	mailBagVO.setMailTag("FRCDGAUSDFWAAAA15484950999027");
       	String scannedPort="SIN";
       	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
       	doReturn(scannedMailDetailsVO).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class), any(String.class));
       	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(mailBagVO,scannedPort);
   	} 
    @Test(expected= SystemException.class)
	public void shouldThrowHHTExceptionWhileErrorStampingForFoundMailWebServicesForEmptyMailBag() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
       	BusinessException be = new BusinessException() {
		};
		MailHHTBusniessException ex = new MailHHTBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO(MailMLDBusniessException.MALBAG_DLV_IMPFLT_MISSING);
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
       	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));     	
    }
    @Test(expected= SystemException.class)
	public void shouldThrowMLDExceptionWhileErrorStampingForFoundMailWebServicesForEmptyMailBag() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
       	BusinessException be = new BusinessException() {
		};
		MailMLDBusniessException exMld = new MailMLDBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO(MailMLDBusniessException.MALBAG_DLV_IMPFLT_MISSING);
		errorVOs.add(errVo);
		exMld.addErrors(errorVOs);
       	doThrow(exMld).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
       	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));    	
    }
    @Test(expected= SystemException.class)
	public void shouldThroMailTrackingExceptionWhileErrorStampingForFoundMailWebServicesForEmptyMailBag() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
       	BusinessException be = new BusinessException() {
		};
		MailTrackingBusinessException ex = new MailTrackingBusinessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO("Mailbag delivered. Import flight details are mandatory for mailbag arrival");
		errVo.setConsoleMessage("Mailbag delivered. Import flight details are mandatory for mailbag arrival");
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
       	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));  	
    }
    @Test(expected= SystemException.class)
   	public void shouldThrowWhenErrorMappingNotExistWhenErrorMappingNotExistForFoundMailWebServicesForEmptyMailBag() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
          	BusinessException be = new BusinessException() {
   		};
   		MailTrackingBusinessException ex = new MailTrackingBusinessException(be);
   		Collection<ErrorVO> errorVOs = new ArrayList<>();
   		ErrorVO errVo=new ErrorVO("No Mapping");
   		errorVOs.add(errVo);
   		ex.addErrors(errorVOs);
          	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
          	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));  	
       }
    @Test(expected= SystemException.class)
   	public void shouldThrowRemoteExceptionWhileErrorStampingForFoundMailWebServicesForEmptyMailBag() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
   		   RemoteException ex = new RemoteException();
          doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
          	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));    	
       }
    @Test(expected= SystemException.class)
   	public void shouldThrowPersistenceExceptionWhileErrorStampingForFoundMailWebServicesForEmptyMailBag() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
          	PersistenceException ex = new PersistenceException();
            doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
          	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));     	
       }
    @Test(expected= SystemException.class) 
    public void shouldThrowExceptionWhenEmptyErrorsWhileErrorStampingForFoundMailWebServicesForEmptyMailBag() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
       	BusinessException be = new BusinessException() {
		};
		MailMLDBusniessException ex = new MailMLDBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
       	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));
       	
   	}
    @Test(expected= SystemException.class) 
    public void shouldThrowExceptionWhenEmptyErrorCodeWhileErrorStampingForFoundMailWebServicesForEmptyMailBag() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
       	BusinessException be = new BusinessException() {
		};
		MailMLDBusniessException ex = new MailMLDBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO("");
		errVo.setConsoleMessage("");
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
       	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));
       	
   	}
    @Test(expected= SystemException.class) 
    public void shouldThrowExceptionWhenNullErrorCodeWhileErrorStampingForFoundMailWebServicesForEmptyMailBag() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
       	BusinessException be = new BusinessException() {
		};
		MailMLDBusniessException ex = new MailMLDBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO(null);
		errVo.setConsoleMessage(null);
		errorVOs.add(errVo);
		ex.addErrors(errorVOs);
       	doThrow(ex).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
       	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));
       	
   	}
    @Test(expected= SystemException.class)
	public void shouldThrowExceptionWhileErrorStampingForFoundTransferredMailWebService() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
       	BusinessException be = new BusinessException() {
		};
		MailMLDBusniessException exMld = new MailMLDBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO("Mailbag Transfered. Import flight details are mandatory for mailbag arrival");
		errVo.setConsoleMessage("Mailbag Transfered. Import flight details are mandatory for mailbag arrival");
		errorVOs.add(errVo);
		exMld.addErrors(errorVOs);
       	doThrow(exMld).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
       	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));    	
    }
    @Test(expected= SystemException.class)
	public void shouldThrowExceptionWhileErrorStampingForFoundReturnedMailWebService() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
       	BusinessException be = new BusinessException() {
		};
		MailMLDBusniessException exMld = new MailMLDBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errVo=new ErrorVO("Mailbag returned. Import flight details are mandatory for mailbag arrival");
		errVo.setConsoleMessage("Mailbag returned. Import flight details are mandatory for mailbag arrival");
		errorVOs.add(errVo);
		exMld.addErrors(errorVOs);
       	doThrow(exMld).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
       	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));    	
    }
    @Test(expected= SystemException.class)
	public void shouldThrowExceptionWhileErrorStampingForClosedFlightFrmMailWebService() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
       	BusinessException be = new BusinessException() {
		};
		MailMLDBusniessException exMld = new MailMLDBusniessException(be);
		Collection<ErrorVO> errorVOs = new ArrayList<>();
       	errorVOs = new ArrayList<>();
       	ErrorVO errVo=new ErrorVO("Mailbag is currently in a closed flight");
       	errVo.setConsoleMessage("Mailbag is currently in a closed flight");
		errorVOs.add(errVo);
		exMld.addErrors(errorVOs);
		doThrow(exMld).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
       	mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));    	
    }
    @Test(expected= SystemException.class)
   	public void shouldThrowExceptionWhileErrorStampingForMailPresentFrmMailWebService() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
          	BusinessException be = new BusinessException() {
   		};
   		MailMLDBusniessException exMld = new MailMLDBusniessException(be);
   		Collection<ErrorVO> errorVOs = new ArrayList<>();
   		ErrorVO errVo=new ErrorVO("Mailbag is currently at port");
   		errVo.setConsoleMessage("Mailbag is currently at port");
   		errorVOs.add(errVo);
   		exMld.addErrors(errorVOs);
   	    doThrow(exMld).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
        mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));
       }
    @Test(expected= SystemException.class)
   	public void shouldThrowExceptionWhileErrorStampingForContAlrdyPresentFrmMailWebService() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
          	BusinessException be = new BusinessException() {
   		};
   		MailMLDBusniessException exMld = new MailMLDBusniessException(be);
   		Collection<ErrorVO> errorVOs = new ArrayList<>();
   		ErrorVO errVo=new ErrorVO("This BULK/ULD is present in another flight");
   		errVo.setConsoleMessage("This BULK/ULD is present in another flight");
   		errorVOs.add(errVo);
   		exMld.addErrors(errorVOs);
        doThrow(exMld).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
        mailUploadControllerSpy.performErrorStampingForFoundMailWebServices(any(MailUploadVO.class), any(String.class));    	
       }
    @Test()
   	public void doSaveMailUploadDetailsForGHA() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	scannedMailDetailsVO.setArrivalException(true);
    	scannedMailDetailsVO.setProcessPoint("DLV");
    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
    	MailUploadVO mailUploadVO =new MailUploadVO();
    	mailscanVos.add(mailUploadVO);
    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
    	mailWebserviceVOs.add(mailWebserviceVO);
    	String scannedPort="SIN";
    	doReturn(mailscanVos).when(mailUploadControllerSpy).createMailScanVOS((Collection<MailWebserviceVO>) any(Collections.class),any(String.class),any(StringBuilder.class),any(String.class));
    	doReturn(scannedMailDetailsVO).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
        mailUploadControllerSpy.performMailOperationForGHA(mailWebserviceVOs, scannedPort);    	
       }
    @Test()
   	public void doSaveMailUploadDetailsForGHAForArrivalExceptionFalse() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	scannedMailDetailsVO.setArrivalException(false);
    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
    	MailUploadVO mailUploadVO =new MailUploadVO();
    	mailscanVos.add(mailUploadVO);
    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
    	mailWebserviceVOs.add(mailWebserviceVO);
    	String scannedPort="SIN";
    	doReturn(mailscanVos).when(mailUploadControllerSpy).createMailScanVOS((Collection<MailWebserviceVO>) any(Collections.class),any(String.class),any(StringBuilder.class),any(String.class));
    	doReturn(scannedMailDetailsVO).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
        mailUploadControllerSpy.performMailOperationForGHA(mailWebserviceVOs, scannedPort);    	
       }
    @Test()
   	public void doSaveMailUploadDetailsForGHAForProcessPointNotDLV() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	scannedMailDetailsVO.setArrivalException(true);
    	scannedMailDetailsVO.setProcessPoint("ACP");
    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
    	MailUploadVO mailUploadVO =new MailUploadVO();
    	mailscanVos.add(mailUploadVO);
    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
    	mailWebserviceVOs.add(mailWebserviceVO);
    	String scannedPort="SIN";
    	doReturn(mailscanVos).when(mailUploadControllerSpy).createMailScanVOS((Collection<MailWebserviceVO>) any(Collections.class),any(String.class),any(StringBuilder.class),any(String.class));
    	doReturn(scannedMailDetailsVO).when(mailUploadControllerSpy).saveMailUploadDetailsForAndroid(any(MailUploadVO.class),any(String.class));
        mailUploadControllerSpy.performMailOperationForGHA(mailWebserviceVOs, scannedPort);    	
       }
    @Test()
   	public void doPopulatePABuildFlaYesgContainerForGHA() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
    	MailUploadVO mailUploadVO =new MailUploadVO();
    	mailscanVos.add(mailUploadVO);
    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
    	mailWebserviceVO.setCompanyCode(getCompanyCode());
        mailWebserviceVO.setCarrierCode(getCompanyCode());
    	mailWebserviceVO.setPAbuilt(true);
    	mailWebserviceVO.setScanType("EXP");
    	mailWebserviceVO.setScanDateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailWebserviceVOs.add(mailWebserviceVO);
    	String scannedPort="SIN";
    	StringBuilder errorString=new StringBuilder();
    	String errorFromMapping=null;
    	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
    	mailUploadControllerSpy.createMailScanVOS(mailWebserviceVOs,scannedPort,errorString,errorFromMapping);    	
       }
    @Test()
   	public void doPopulatePABuildFlagNoContainerForGHA() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
    	MailUploadVO mailUploadVO =new MailUploadVO();
    	mailscanVos.add(mailUploadVO);
    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
    	mailWebserviceVO.setCompanyCode(getCompanyCode());
        mailWebserviceVO.setCarrierCode(getCompanyCode());
    	mailWebserviceVO.setPAbuilt(false);
    	mailWebserviceVO.setScanType("EXP");
    	mailWebserviceVO.setScanDateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailWebserviceVOs.add(mailWebserviceVO);
    	String scannedPort="SIN";
    	StringBuilder errorString=new StringBuilder();
    	String errorFromMapping=null;
    	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
    	mailUploadControllerSpy.createMailScanVOS(mailWebserviceVOs,scannedPort,errorString,errorFromMapping);    	
       }

	 @Test()
	   	public void setScanInformationForAndroidForRemark() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException, ForceAcceptanceException {
	    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
	    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
	    	MailUploadVO mailUploadVO =new MailUploadVO();
	    	mailUploadVO.setMailSource("WS");
	    	mailscanVos.add(mailUploadVO);
	    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
	    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
	    	mailWebserviceVO.setCompanyCode(getCompanyCode());
	        mailWebserviceVO.setCarrierCode(getCompanyCode());
	    	mailWebserviceVO.setPAbuilt(false);
	    	mailWebserviceVO.setScanType("EXP");
	    	mailWebserviceVO.setScanDateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    	mailWebserviceVOs.add(mailWebserviceVO);
	    	String scannedPort="SIN";
	    	MailbagVO mailbagVO= new MailbagVO();
	    	Collection <MailbagVO>mailbagVos=new ArrayList<> () ;
	    	mailbagVos.add(mailbagVO);
	    	scannedMailDetailsVO.setMailDetails(mailbagVos);
	    	mailUploadControllerSpy.setScanInformationForAndroid(mailUploadVO,scannedMailDetailsVO,scannedPort);    	
	       }
	 
	 @Test()
	   	public void setScanInformationForAndroidForRemarkNotFromWS() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException, ForceAcceptanceException {
	    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
	    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
	    	MailUploadVO mailUploadVO =new MailUploadVO();
	    	mailscanVos.add(mailUploadVO);
	    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
	    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
	    	mailWebserviceVO.setCompanyCode(getCompanyCode());
	        mailWebserviceVO.setCarrierCode(getCompanyCode());
	    	mailWebserviceVO.setPAbuilt(false);
	    	mailWebserviceVO.setScanType("EXP");
	    	mailWebserviceVO.setScanDateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    	mailWebserviceVOs.add(mailWebserviceVO);
	    	String scannedPort="SIN";
	    	MailbagVO mailbagVO= new MailbagVO();
	    	Collection <MailbagVO>mailbagVos=new ArrayList<> () ;
	    	mailbagVos.add(mailbagVO);
	    	scannedMailDetailsVO.setMailDetails(mailbagVos);
	    	mailUploadControllerSpy.setScanInformationForAndroid(mailUploadVO,scannedMailDetailsVO,scannedPort);    	
	       }
	@Test()
   	public void doMakeArrivalVOToScannedUser() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException, ForceAcceptanceException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	ContainerDetailsVO containerDetailVO =new ContainerDetailsVO();
    	MailbagVO mailbagVo= new MailbagVO();
    	Collection<MailbagVO> mailbagVos = new ArrayList<>();
    	mailbagVos.add(mailbagVo);
    	mailbagVo.setMailClass("UN");
    	mailbagVo.setMailSubclass("UN");
    	mailbagVo.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0));
    	mailbagVo.setActionMode("DELIVERY");
    	containerDetailVO.setMailDetails(mailbagVos);
    	scannedMailDetailsVO.setValidatedContainer(containerDetailVO);
    	scannedMailDetailsVO.setMailDetails(mailbagVos);
    	scannedMailDetailsVO.setFlightSequenceNumber(10);
    	scannedMailDetailsVO.setTransactionLevel("M");
    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	Map<String, String> systemParameterMap = new HashMap<>();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
    	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Integer.class));
    	mailUploadControllerSpy.makeMailArrivalVO(scannedMailDetailsVO,
				logonAttributes,false);  	
       }
    
    @Test()
   	public void doMakeArrivalVOToScannedUserNull() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException, ForceAcceptanceException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	ContainerDetailsVO containerDetailVO =new ContainerDetailsVO();
    	MailbagVO mailbagVo= new MailbagVO();
    	Collection<MailbagVO> mailbagVos = new ArrayList<>();
    	mailbagVos.add(mailbagVo);
    	mailbagVo.setMailClass("UN");
    	mailbagVo.setMailSubclass("UN");
    	mailbagVo.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0));
    	mailbagVo.setActionMode("DELIVERY");
    	mailbagVo.setScannedUser("SYSADMIN");
    	containerDetailVO.setMailDetails(mailbagVos);
    	scannedMailDetailsVO.setValidatedContainer(containerDetailVO);
    	scannedMailDetailsVO.setMailDetails(mailbagVos);
    	scannedMailDetailsVO.setFlightSequenceNumber(10);
    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	Map<String, String> systemParameterMap = new HashMap<>();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
    	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Integer.class));
    	mailUploadControllerSpy.makeMailArrivalVO(scannedMailDetailsVO,
				logonAttributes,false);  	
       }
    @Test()
   	public void doMakeArrivalVOToScannedUserEmpty() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException, ForceAcceptanceException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	ContainerDetailsVO containerDetailVO =new ContainerDetailsVO();
    	MailbagVO mailbagVo= new MailbagVO();
    	Collection<MailbagVO> mailbagVos = new ArrayList<>();
    	mailbagVos.add(mailbagVo);
    	mailbagVo.setMailClass("UN");
    	mailbagVo.setMailSubclass("UN");
    	mailbagVo.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0));
    	mailbagVo.setActionMode("DELIVERY");
    	mailbagVo.setScannedUser("");
    	containerDetailVO.setMailDetails(mailbagVos);
    	scannedMailDetailsVO.setValidatedContainer(containerDetailVO);
    	scannedMailDetailsVO.setMailDetails(mailbagVos);
    	scannedMailDetailsVO.setFlightSequenceNumber(10);
    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	Map<String, String> systemParameterMap = new HashMap<>();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
    	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Integer.class));
    	mailUploadControllerSpy.makeMailArrivalVO(scannedMailDetailsVO,
				logonAttributes,false);  	
       }
    @Test()
   	public void doPopulateDefaultStorageUnitGHA() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException, TemplateRenderingException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
    	MailUploadVO mailUploadVO =new MailUploadVO();
    	mailscanVos.add(mailUploadVO);
    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
    	mailWebserviceVO.setCompanyCode(getCompanyCode());
        mailWebserviceVO.setCarrierCode(getCompanyCode());
    	mailWebserviceVO.setPAbuilt(false);
    	mailWebserviceVO.setScanType("ACP");
    	mailWebserviceVO.setScanDateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailWebserviceVOs.add(mailWebserviceVO);
    	String scannedPort="SIN";
    	StringBuilder errorString=new StringBuilder();
    	String errorFromMapping=null;
    	HashMap<String, String> systemParameterMap = new HashMap<>();
       	systemParameterMap.put("mailtracking.defaults.defaultstorageunitformail", "FRASTR$");
       	templateEncoderUtilInstance = TemplateEncoderUtilInstance.getInstance();
		doReturn("1").when(templateEncoderUtilInstance).encode(any(String.class), any(String.class), any(HashMap.class), any(Boolean.class));
       	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
    	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
    	mailUploadControllerSpy.createMailScanVOS(mailWebserviceVOs,scannedPort,errorString,errorFromMapping);    	
       }
    @Test(expected= SystemException.class)
   	public void doPopulateEmptyDefaultStorageUnitGHA() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException, TemplateRenderingException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
    	MailUploadVO mailUploadVO =new MailUploadVO();
    	mailscanVos.add(mailUploadVO);
    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
    	mailWebserviceVO.setCompanyCode(getCompanyCode());
        mailWebserviceVO.setCarrierCode(getCompanyCode());
    	mailWebserviceVO.setPAbuilt(false);
    	mailWebserviceVO.setScanType("ACP");
    	mailWebserviceVO.setToCarrierCod("LH");
    	mailWebserviceVO.setScanDateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailWebserviceVOs.add(mailWebserviceVO);
    	String scannedPort="SIN";
    	StringBuilder errorString=new StringBuilder();
    	String errorFromMapping=null;
    	HashMap<String, String> systemParameterMap = new HashMap<>();
       	systemParameterMap.put("mailtracking.defaults.defaultstorageunitformail", "FRASTR");
       	templateEncoderUtilInstance = TemplateEncoderUtilInstance.getInstance();
		doReturn("1").when(templateEncoderUtilInstance).encode(any(String.class), any(String.class), any(HashMap.class), any(Boolean.class));
       	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
    	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
    	mailUploadControllerSpy.createMailScanVOS(mailWebserviceVOs,scannedPort,errorString,errorFromMapping);    	
       }
    @Test()
   	public void doPopulateDefaultStorageUnitGHAEmptyToCarier() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException, TemplateRenderingException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
    	MailUploadVO mailUploadVO =new MailUploadVO();
    	mailscanVos.add(mailUploadVO);
    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
    	mailWebserviceVO.setCompanyCode(getCompanyCode());
        mailWebserviceVO.setCarrierCode(getCompanyCode());
    	mailWebserviceVO.setPAbuilt(false);
    	mailWebserviceVO.setScanType("ACP");
    	mailWebserviceVO.setScanDateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailWebserviceVOs.add(mailWebserviceVO);
    	mailWebserviceVO.setToCarrierCod("");
    	String scannedPort="SIN";
    	StringBuilder errorString=new StringBuilder();
    	String errorFromMapping=null;
    	HashMap<String, String> systemParameterMap = new HashMap<>();
       	systemParameterMap.put("mailtracking.defaults.defaultstorageunitformail", "FRASTR$");
       	templateEncoderUtilInstance = TemplateEncoderUtilInstance.getInstance();
       	doThrow(new TemplateRenderingException()).when(TemplateEncoderUtilInstance.getInstance()).encode(any(String.class), any(String.class), any(Map.class),any(Boolean.class));
       	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
    	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
    	mailUploadControllerSpy.createMailScanVOS(mailWebserviceVOs,scannedPort,errorString,errorFromMapping);    	
       }
    @Test()
   	public void doPopulateDefaultStorageUnitGHA2EmptySysPar() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException, TemplateRenderingException {
    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
    	MailUploadVO mailUploadVO =new MailUploadVO();
    	mailscanVos.add(mailUploadVO);
    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
    	mailWebserviceVO.setCompanyCode(getCompanyCode());
        mailWebserviceVO.setCarrierCode(getCompanyCode());
    	mailWebserviceVO.setPAbuilt(false);
    	mailWebserviceVO.setScanType("ACP");
    	mailWebserviceVO.setScanDateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailWebserviceVOs.add(mailWebserviceVO);
    	mailWebserviceVO.setToCarrierCod("");
    	String scannedPort="SIN";
    	StringBuilder errorString=new StringBuilder();
    	String errorFromMapping=null;
    	HashMap<String, String> systemParameterMap = new HashMap<>();
       	systemParameterMap.put("mailtracking.defaults.defaultstorageunitformail", "");
       //	templateEncoderUtilInstance = TemplateEncoderUtil;
		doReturn("FRALH").when(TemplateEncoderUtilInstance.getInstance()).encode(any(String.class), any(String.class), any(Map.class), any(Boolean.class));
       	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
    	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
    	mailUploadControllerSpy.createMailScanVOS(mailWebserviceVOs,scannedPort,errorString,errorFromMapping);    	
       }
   
    @Test
    public void shouldCallSetScreeningDetailsFromConstructScannedMailDetailVOWithSecurityMethodsEmpty()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
    	MailUploadVO mailUploadVo=new MailUploadVO();
    	ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
    	mailUploadVo.setSecurityMethods("");
		mailUploadVo.setDateTime("05-May-2022 09:00:13");
		mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		LogonAttributes logonAttributes= new LogonAttributes();
		String scanningPort="CDG";
		HashMap<String, String> map=new HashMap<>();
		map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
		map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
		mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
		mailUploadVo.setDsn("1170");
		mailUploadVo.setRegisteredIndicator("9");
		mailUploadVo.setHighestnumberIndicator("9");
		mailUploadVo.setCompanyCode(getCompanyCode());
		mailUploadVo.setContainerNumber("AKE01268AV");
		mailUploadVo.setPaCode("Y");
		mailUploadVo.setToContainer("AKE01268AV");
		scannedMailDetailsVO.setProcessPoint("TRA");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
    }
    @Test
    public void shouldCallSetScreeningDetailsFromConstructScannedMailDetailVOWithoutSecurityMethods()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
    	MailUploadVO mailUploadVo=new MailUploadVO();
    	ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
    	Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
    	scannedMailDetailsVO.setMailDetails(mailBagVOs);
    	mailUploadVo.setSecurityMethods("");
		mailUploadVo.setDateTime("05-May-2022 09:00:13");
		mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		LogonAttributes logonAttributes= new LogonAttributes();
		String scanningPort="CDG";
		HashMap<String, String> map=new HashMap<>();
		map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
		map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
		mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
		mailUploadVo.setDsn("1170");
		mailUploadVo.setRegisteredIndicator("9");
		mailUploadVo.setHighestnumberIndicator("9");
		mailUploadVo.setCompanyCode(getCompanyCode());
		mailUploadVo.setContainerNumber("AKE01268AV");
		mailUploadVo.setPaCode("Y");
		mailUploadVo.setToContainer("AKE01268AV");
		scannedMailDetailsVO.setProcessPoint("TRA");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
    }
    @Test
    public void shouldCallSetScreeningDetailsFromConstructScannedMailDetailVOWithEmptyMailDetails()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
    	MailUploadVO mailUploadVo=new MailUploadVO();
    	ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO(); 	
    	scannedMailDetailsVO.setMailDetails(new ArrayList<MailbagVO>());
    	mailUploadVo.setSecurityMethods("ABC");
		mailUploadVo.setDateTime("05-May-2022 09:00:13");
		mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
		LogonAttributes logonAttributes= new LogonAttributes();
		String scanningPort="CDG";
		HashMap<String, String> map=new HashMap<>();
		map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
		map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
		mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
		mailUploadVo.setDsn("1170");
		mailUploadVo.setRegisteredIndicator("9");
		mailUploadVo.setHighestnumberIndicator("9");
		mailUploadVo.setCompanyCode(getCompanyCode());
		mailUploadVo.setContainerNumber("AKE01268AV");
		mailUploadVo.setPaCode("Y");
		mailUploadVo.setToContainer("AKE01268AV");
		scannedMailDetailsVO.setProcessPoint("TRA");
		MailbagVO mailbagVO=new MailbagVO();
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
    }
    @Test
    public void shouldCallSetScreeningDetailsFromConstructScannedMailDetailVOWithMailDetailsasNull()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
    	MailUploadVO mailUploadVo=new MailUploadVO();
    	ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
    	mailUploadVo.setSecurityMethods("ABC");
		mailUploadVo.setDateTime("05-May-2022 09:00:13");
		mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
		LogonAttributes logonAttributes= new LogonAttributes();
		String scanningPort="CDG";
		HashMap<String, String> map=new HashMap<>();
		map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
		map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
		mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
		mailUploadVo.setDsn("1170");
		mailUploadVo.setRegisteredIndicator("9");
		mailUploadVo.setHighestnumberIndicator("9");
		mailUploadVo.setCompanyCode(getCompanyCode());
		mailUploadVo.setContainerNumber("AKE01268AV");
		mailUploadVo.setPaCode("Y");
		mailUploadVo.setToContainer("AKE01268AV");
		scannedMailDetailsVO.setProcessPoint("TRA");
		scannedMailDetailsVO.setMailDetails(null);
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		MailbagVO mailbagVO=new MailbagVO();
		mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
    }
	@Test
    public void shouldCallSetScreeningDetailsFromConstructScannedMailDetailVOWithMailDetails()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
    	MailUploadVO mailUploadVo=new MailUploadVO();
    	ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
    	MailbagVO mailBagVO = new MailbagVO();
    	Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
    	mailBagVOs.add(mailBagVO);
    	scannedMailDetailsVO.setMailDetails(mailBagVOs);
    	mailUploadVo.setSecurityMethods("ABC");
		mailUploadVo.setDateTime("05-May-2022 09:00:13");
		mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		LogonAttributes logonAttributes= new LogonAttributes();
		String scanningPort="CDG";
		HashMap<String, String> map=new HashMap<>();
		map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
		map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
		mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
		mailUploadVo.setDsn("1170");
		mailUploadVo.setRegisteredIndicator("9");
		mailUploadVo.setHighestnumberIndicator("9");
		mailUploadVo.setCompanyCode(getCompanyCode());
		mailUploadVo.setContainerNumber("AKE01268AV");
		mailUploadVo.setPaCode("Y");
		mailUploadVo.setToContainer("AKE01268AV");
		scannedMailDetailsVO.setProcessPoint("TRA");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
    }
	
	@Test
    public void constructVOAndSaveDetails_ScanTypeArrivalTest1() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException, PersistenceException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		//OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		//operationalFlightVO.setFlightSequenceNumber(-1);
		//HashMap<String, String> systemParameterMap = null;
		//Collection<OnwardRoutingVO> onwardRoutings = new ArrayList<>();
		//OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
		/*onwardRoutingVO.setCompanyCode(getCompanyCode());
		onwardRoutingVO.setOnwardFlightDate(new LocalDate("CDG", Location.ARP,true));
		onwardRoutings.add(onwardRoutingVO);
		containerVo.setOnwardRoutings(onwardRoutings);*/
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("U");
		containerVo.setFromDeviationList(true);
		containerVo.setUldReferenceNo(0);
		containerVos.add(containerVo);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerEntity = new Container(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		containerEntity.setAcceptanceFlag("Y");
		containerEntity.setTransitFlag("Y");
		
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	uldValidationVO.setUldNumber("AKE12345AV");
    	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
    	flightSegmentSummaryVO.setSegmentOrigin("CDG");
    	flightSegmentSummaryVO.setSegmentDestination("CDG");
    	flightSegmentSummaryVO.setSegmentSerialNumber(1);
    	flightSegments.add(flightSegmentSummaryVO);
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setLegDestination("CDG");
    	flightFilterVO.setFlightSequenceNumber(12);
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	containerAssignmentVO.setAirportCode("CDG");
    	containerAssignmentVO.setFlightNumber("AKE12345AV");
    	containerAssignmentVO.setFlightSequenceNumber(12);
    	containerAssignmentVO.setSegmentSerialNumber(1);
    	containerAssignmentVO.setUldReferenceNo(0);
    	officeOfExchangeVO.setPoaCode("FR001");
    	officeOfExchangeVO.setAirportCode("CDG");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ARR");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("EXCELUPL");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailUploadVO.setContainerPOU("CDG");
    	mailUploadVO.setFlightSequenceNumber(12);
    	mailUploadVO.setFromPol("CDG");
    	mailBagVOs.add(mailUploadVO);  
    	//doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		//doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Long.class));
		doReturn(assignedFlight).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(uldForSegment).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerAssignmentVO).when(mailController).findLatestContainerAssignment(any(String.class));
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		containerSpy.update(containerVo);  
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,"CDG");
    	//containerSpy.update(containerVo);   
    	
    }
	
	@Test
    public void constructVOAndSaveDetails_ScanTypeArrivalTest2() throws MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException, SystemException, ProxyException, SharedProxyException, AreaBusinessException, RemoteException, ServiceNotAccessibleException, FinderException, PersistenceException{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
    	HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
    	Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
    	MailUploadVO mailUploadVO = new MailUploadVO();
    	mailUploadVO.setCompanyCode(getCompanyCode());
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	HashMap<String, String> systemParameterMap = new HashMap<>();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	AirportValidationVO airportValidationVO = new AirportValidationVO();
    	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
    	FlightValidationVO flightFilterVO = new FlightValidationVO();
    	ULDValidationVO uldValidationVO = new ULDValidationVO();
    	ContainerVO containerVo = new ContainerVO();
		ContainerPK ContainerPK = new ContainerPK();
		Collection<ContainerVO> containerVos = new ArrayList<>();
		containerVo.setAssignedPort("CDG");
		containerVo.setReassignFlag(false);
		containerVo.setContainerNumber("AKE12345AV");
		containerVo.setOperationFlag("U");
		containerVo.setFromDeviationList(true);
		containerVo.setUldReferenceNo(1);
		containerVos.add(containerVo);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerEntity = new Container(containerVo);
		containerEntity.setContainerPK(ContainerPK);
		containerEntity.setAcceptanceFlag("Y");
		containerEntity.setTransitFlag("Y");
		
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
    	uldValidationVO.setUldNumber("AKE12345AV");
    	ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
    	FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
    	flightSegmentSummaryVO.setSegmentOrigin("CDG");
    	flightSegmentSummaryVO.setSegmentDestination("CDG");
    	flightSegmentSummaryVO.setSegmentSerialNumber(1);
    	flightSegments.add(flightSegmentSummaryVO);
    	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
    	flightFilterVO.setLegOrigin("CDG");
    	flightFilterVO.setLegDestination("CDG");
    	flightFilterVO.setFlightSequenceNumber(12);
    	flightFilterVO.setFlightRoute("FRA-DFW");
    	flightFilterVOs.add(flightFilterVO);
    	airportValidationVO.setCompanyCode(getCompanyCode());
    	airportValidationVO.setAirportCode("DFW");
    	systemParameterMap.put(PERIODFOR_PABUILTMAILS, "2");
    	containerAssignmentVO.setAirportCode("CDG");
    	containerAssignmentVO.setFlightNumber("AKE12345AV");
    	containerAssignmentVO.setFlightSequenceNumber(12);
    	containerAssignmentVO.setSegmentSerialNumber(1);
    	containerAssignmentVO.setUldReferenceNo(1);
    	officeOfExchangeVO.setPoaCode("FR001");
    	officeOfExchangeVO.setAirportCode("CDG");
    	officeOfExhange.add(officeOfExchangeVO);
    	mailUploadVO.setScanType("ARR");
    	mailUploadVO.setContainerNumber("AKE12345AV");
    	mailUploadVO.setCarrierCode(getCompanyCode());
    	mailUploadVO.setFlightNumber("AV1234");
    	mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    	mailUploadVO.setDestination("DFW");
    	mailUploadVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
    	mailUploadVO.setContaineDescription("CONTAINER_ARRIVAL");
    	mailUploadVO.setMailSource("EXCELUPL");
    	mailUploadVO.setOrginOE("DEFRAA");
    	mailUploadVO.setContainerType("U");
    	mailUploadVO.setContainerPOU("CDG");
    	mailUploadVO.setFlightSequenceNumber(12);
    	mailUploadVO.setFromPol("CDG");
    	mailBagVOs.add(mailUploadVO);  
    	doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange(getCompanyCode(), mailUploadVO.getOrginOE(), 1);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(airportValidationVO).when(sharedAreaProxy).validateAirportCode(any(String.class),any(String.class));
		doReturn(assignedFlightSegment).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Long.class));
		doReturn(assignedFlight).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(uldForSegment).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerAssignmentVO).when(mailController).findLatestContainerAssignment(any(String.class));
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
		containerSpy.update(containerVo);  
    	mailUploadControllerSpy.saveMailUploadDetails(mailBagVOs,"CDG");
    	
    }
	
	
	 @Test
	 public void setContainerDetailsTest_ActualWeightTrue() throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, SystemException, FinderException{
		 ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		MailUploadVO mailUploadVo=new MailUploadVO();
		
		mailUploadVo.setSecurityMethods("ABC");
		mailUploadVo.setDateTime("05-May-2022 09:00:13");
		mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		LogonAttributes logonAttributes= new LogonAttributes();
		String scanningPort="CDG";
		HashMap<String, String> map=new HashMap<>();
		map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
		map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
		mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
		mailUploadVo.setDsn("1170");
		mailUploadVo.setRegisteredIndicator("9");
		mailUploadVo.setHighestnumberIndicator("9");
		mailUploadVo.setCompanyCode(getCompanyCode());
		
		mailUploadVo.setContainerNumber("AKE01268AV");
		mailUploadVo.setPaCode("Y");
		mailUploadVo.setToContainer("AKE01268AV");
		mailUploadVo.setUldActualWeight(new Measure(null,80.0));
		scannedMailDetailsVO.setProcessPoint("TRA");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
	
		mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
		 
	 }
	    @Test
	    public void updateContainerPOLPOUForMLDGetWeightTrue() throws PersistenceException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException{
			Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
			ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
			MLDMasterVO mldMasterVo = new MLDMasterVO();
			MLDDetailVO mLDDetailVO = new MLDDetailVO();
			mldMasterVo.setBarcodeType("R");
			mldMasterVo.setBarcodeValue("J");
			mldMasterVo.setMessageVersion("2");
			mldMasterVo.setEventCOde("NST");
			mldMasterVo.setSenderAirport("CDG");
			mldMasterVo.setUldNumber("AKE12345AV");
			mldMasterVo.setContainerType("U");
			mldMasterVo.setWeight(new Measure(null,80.0));
			mLDDetailVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
			mldMasterVo.setMldDetailVO(mLDDetailVO);
			mldMasterVo.setScanTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			mldMasterVOs.add(mldMasterVo);
	    	containerAssignmentVO.setCompanyCode(getCompanyCode());
			doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
			mailUploadControllerSpy.saveMailUploadDetailsFromMLD(mldMasterVOs);
	    }
	    @Test
		 public void setContainerDetailsTest_WithTransactionLevel() throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, SystemException, FinderException{
			 ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
			MailUploadVO mailUploadVo=new MailUploadVO();
			mailUploadVo.setSecurityMethods("ABC");
			mailUploadVo.setDateTime("05-May-2022 09:00:13");
			mailUploadVo.setContaineDescription("CONTAINER_ARRIVAL");
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			LogonAttributes logonAttributes= new LogonAttributes();
			String scanningPort="CDG";
			HashMap<String, String> map=new HashMap<>();
			map.put("mail.operation.destinationforcarditmissingdomesticmailbag","value");
			map.put("mailtracking.defaults.noofcharsallowedformailtag", "29");
			mailUploadVo.setMailTag("FRCDGADEFRAAAUA01170604990888");
			mailUploadVo.setDsn("1170");
			mailUploadVo.setRegisteredIndicator("9");
			mailUploadVo.setHighestnumberIndicator("9");
			mailUploadVo.setCompanyCode(getCompanyCode());
			mailUploadVo.setTransactionLevel("U");
			mailUploadVo.setContainerNumber("AKE01268AV");
			mailUploadVo.setPaCode("Y");
			mailUploadVo.setToContainer("AKE01268AV");
			scannedMailDetailsVO.setProcessPoint("TRA");
			doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
			mailUploadControllerSpy.constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVo, mailbagVO, logonAttributes, scanningPort);
		 }
	    @Test()
	   	public void doMakeArrivalVOToScannedUser_TransactionLevel_NotNUll() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException, ForceAcceptanceException {
	    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
	    	ContainerDetailsVO containerDetailVO =new ContainerDetailsVO();
	    	MailbagVO mailbagVo= new MailbagVO();
	    	Collection<MailbagVO> mailbagVos = new ArrayList<>();
	    	mailbagVos.add(mailbagVo);
	    	mailbagVo.setMailClass("UN");
	    	mailbagVo.setMailSubclass("UN");
	    	mailbagVo.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0));
	    	mailbagVo.setActionMode("DELIVERY");
	    	containerDetailVO.setMailDetails(mailbagVos);
	    	scannedMailDetailsVO.setValidatedContainer(containerDetailVO);
	    	scannedMailDetailsVO.setMailDetails(mailbagVos);
	    	scannedMailDetailsVO.setFlightSequenceNumber(10);
	    	scannedMailDetailsVO.setTransactionLevel("U");
	    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
	    	Map<String, String> systemParameterMap = new HashMap<>();
	    	Collection<FlightSegmentSummaryVO> flightSegments = new ArrayList<>();
	    	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
	    	doReturn(flightSegments).when(flightOperationsProxy).findFlightSegments(any(String.class),any(Integer.class),any(String.class),any(Integer.class));
	    	mailUploadControllerSpy.makeMailArrivalVO(scannedMailDetailsVO,
					logonAttributes,false);  	
	       }
   @Test
    public void doSecurityScreeningValidationsTest()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		FlightValidationVO flightValidationVO=new FlightValidationVO();
		flightDetailsVOs.add(flightValidationVO);
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		mailbagVO.setOrigin("FRA");
		mailbagVO.setDestination("DFW");
		mailbagVO.setMailSequenceNumber(2839);
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setAirportCode("LAX");
		scannedMailDetailsVO.setTransferFromCarrier("QF");
		scannedMailDetailsVO.setPou("LIS");
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		securityScreeningValidationVO.setErrorType("W");
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		AirportValidationVO airportValidationVO= new AirportValidationVO();
		airportValidationVO.setCountryCode("DE");
		AirportValidationVO airportValidationVO1= new AirportValidationVO();
		airportValidationVO1.setCountryCode("US");
		AirportValidationVO airportValidationVO2= new AirportValidationVO();
		airportValidationVO2.setCountryCode("US");
		AirportValidationVO airportValidationVO3= new AirportValidationVO();
		airportValidationVO3.setCountryCode("PT");
		countryCodeMap.put("FRA",airportValidationVO);
		countryCodeMap.put("DFW",airportValidationVO1);
		countryCodeMap.put("LAX",airportValidationVO2);
		countryCodeMap.put("LIS",airportValidationVO3);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		String appRegFlg="1";
		doReturn(appRegFlg).when(dao).findApplicableRegFlagForMailbag(any(String.class),any(Long.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithGroupNameNull()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		Collection<FlightValidationVO> flightDetailsVOs=null;
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		mailbagVO.setOrigin("FRA");
		mailbagVO.setDestination("DFW");
		mailbagVO.setMailSequenceNumber(2839);
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setAirportCode("LAX");
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		securityScreeningValidationVO.setErrorType("W");
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		AirportValidationVO airportValidationVO= new AirportValidationVO();
		airportValidationVO.setCountryCode("DE");
		AirportValidationVO airportValidationVO1= new AirportValidationVO();
		airportValidationVO1.setCountryCode("US");
		AirportValidationVO airportValidationVO2= new AirportValidationVO();
		airportValidationVO2.setCountryCode("DE");
		countryCodeMap.put("FRA",airportValidationVO);
		countryCodeMap.put("DFW",airportValidationVO1);
		countryCodeMap.put("LAX",airportValidationVO2);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		SystemException systemException = new SystemException("system exception");
		doThrow(systemException).when(dao).findApplicableRegFlagForMailbag(any(String.class),any(Long.class));
		doReturn(null).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		doReturn(null).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithCountryNameNull()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		Collection<FlightValidationVO> flightDetailsVOs=null;
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		mailbagVO.setOrigin("FRA");
		mailbagVO.setDestination("DFW");
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setAirportCode("LAX");
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		securityScreeningValidationVO.setErrorType("W");
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		AirportValidationVO airportValidationVO= new AirportValidationVO();
		airportValidationVO.setCountryCode("DE");
		AirportValidationVO airportValidationVO1= new AirportValidationVO();
		airportValidationVO1.setCountryCode("US");
		AirportValidationVO airportValidationVO2= new AirportValidationVO();
		airportValidationVO2.setCountryCode("DE");
		countryCodeMap.put("FRA",airportValidationVO);
		countryCodeMap.put("DFW",airportValidationVO1);
		countryCodeMap.put("LAX",airportValidationVO2);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		doReturn(null).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		doReturn(null).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(null).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithProxyException()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		Collection<FlightValidationVO> flightDetailsVOs=null;
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		mailbagVO.setOrigin("FRA");
		mailbagVO.setDestination("DFW");
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setAirportCode("LAX");
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		securityScreeningValidationVO.setErrorType("W");
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		AirportValidationVO airportValidationVO= new AirportValidationVO();
		airportValidationVO.setCountryCode("DE");
		AirportValidationVO airportValidationVO1= new AirportValidationVO();
		airportValidationVO1.setCountryCode("US");
		AirportValidationVO airportValidationVO2= new AirportValidationVO();
		airportValidationVO2.setCountryCode("DE");
		countryCodeMap.put("FRA",airportValidationVO);
		countryCodeMap.put("DFW",airportValidationVO1);
		countryCodeMap.put("LAX",airportValidationVO2);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		ProxyException proxyException = new ProxyException("proxy exception", null);
		doReturn(null).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		doThrow(proxyException).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		SystemException systemException = new SystemException("system exception");
		doThrow(systemException).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithFlightValidationEmpty()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();;
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		mailbagVO.setOrigin("FRA");
		mailbagVO.setDestination("DFW");
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setAirportCode("LAX");
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		securityScreeningValidationVO.setErrorType("W");
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		AirportValidationVO airportValidationVO= new AirportValidationVO();
		airportValidationVO.setCountryCode("DE");
		AirportValidationVO airportValidationVO1= new AirportValidationVO();
		airportValidationVO1.setCountryCode("US");
		AirportValidationVO airportValidationVO2= new AirportValidationVO();
		airportValidationVO2.setCountryCode("DE");
		countryCodeMap.put("FRA",airportValidationVO);
		countryCodeMap.put("DFW",airportValidationVO1);
		countryCodeMap.put("LAX",airportValidationVO2);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		doReturn(null).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestForContainer()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, PersistenceException, ProxyException, FinderException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<>();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		scannedMailDetailsVO.setContainerNumber("AKE94847LH");
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerType("U");
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO=new MailbagVO();
		mailBagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailBagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		Collection<String> groupNames = new ArrayList<>();
		groupNames.add("TXNARPGRP");
		MailbagVO mailbagVo = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		mailbagVo.setCompanyCode("IBS");
		mailbagVo.setMailSequenceNumber(456);
		mailbagVo.setSecurityStatusCode("SPX");
		mailbagVo.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVo.setWeight(new Measure(UnitConstants.MAIL_WGT,10.0));
		mailbagVo.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVo.setPaCode("DE101");
		Map<String, String> map= new HashMap<>();
		map.put("mailtracking.defaults.uspsinternationalpa","US101");
		map.put("mailtracking.domesticmra.usps","US001");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailbag = new Mailbag(mailbagVo);
		mailBagPK.setCompanyCode("IBS"); 
		mailBagPK.setMailSequenceNumber(456);
		mailbag.setMailbagPK(mailBagPK);
		mailbag.setSecurityStatusCode("SPX");
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
		long sernum=10;
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag("Y");
		containerAssignmentVO.setArrivalFlag("N");
		containerAssignmentVO.setAirportCode("FRA");
		containerAssignmentVO.setContainerType("U");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test()
    public void doSecurityScreeningValidationsTestForContainerTransfer()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, PersistenceException, ProxyException, FinderException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<>();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		FlightValidationVO flightValidationVO=new FlightValidationVO();
		flightValidationVO.setFlightNumber("3434");
		flightDetailsVOs.add(flightValidationVO);
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		scannedMailDetailsVO.setContainerNumber("AKE94847LH");
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerType("U");
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO=new MailbagVO();
		mailBagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailBagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		Collection<String> groupNames = new ArrayList<>();
		groupNames.add("TXNARPGRP");
		MailbagVO mailbagVo = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVo.setCompanyCode("IBS");
		mailbagVo.setMailSequenceNumber(456);
		mailbagVo.setSecurityStatusCode("SPX");
		mailBagPK.setCompanyCode("IBS"); 
		mailBagPK.setMailSequenceNumber(456);
		mailBag.setMailbagPK(mailBagPK);
		mailBag.setSecurityStatusCode("SPX");
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
    	
		long sernum=0;
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(null).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag("Y");
		containerAssignmentVO.setArrivalFlag("N");
		containerAssignmentVO.setPou("FRA");
		containerAssignmentVO.setContainerType("U");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test()
    public void doSecurityScreeningValidationsTestForContainerTransferWithEmptyValidationVO()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, PersistenceException, ProxyException, FinderException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<>();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		FlightValidationVO flightValidationVO=new FlightValidationVO();
		flightValidationVO.setFlightNumber("3434");
		flightDetailsVOs.add(flightValidationVO);
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		scannedMailDetailsVO.setContainerNumber("AKE94847LH");
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerType("U");
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO=new MailbagVO();
		mailBagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailBagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		Collection<String> groupNames = new ArrayList<>();
		groupNames.add("TXNARPGRP");
		MailbagVO mailbagVo = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVo.setCompanyCode("IBS");
		mailbagVo.setMailSequenceNumber(456);
		mailbagVo.setSecurityStatusCode("SPX");
		mailBagPK.setCompanyCode("IBS"); 
		mailBagPK.setMailSequenceNumber(456);
		mailBag.setMailbagPK(mailBagPK);
		mailBag.setSecurityStatusCode("SPX");
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
    	
		long sernum=0;
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag("Y");
		containerAssignmentVO.setArrivalFlag("N");
		containerAssignmentVO.setPou("FRA");
		containerAssignmentVO.setContainerType("U");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}

	@Test(expected=MailHHTBusniessException.class)
    public void doSecurityScreeningValidationsTestForContainerArrival()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, PersistenceException, ProxyException, FinderException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<>();
		Collection<FlightValidationVO> flightDetailsVOs=null;
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		scannedMailDetailsVO.setContainerNumber("AKE94847LH");
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerType("U");
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		securityScreeningValidationVO.setErrorType(MailConstantsVO.ERROR);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO=new MailbagVO();
		mailBagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailBagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		Collection<String> groupNames = new ArrayList<>();
		groupNames.add("TXNARPGRP");
		MailbagVO mailbagVo = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVo.setCompanyCode("IBS");
		mailbagVo.setMailSequenceNumber(456);
		mailbagVo.setSecurityStatusCode("SPX");
		mailBagPK.setCompanyCode("IBS"); 
		mailBagPK.setMailSequenceNumber(456);
		mailBag.setMailbagPK(mailBagPK);
		mailBag.setSecurityStatusCode("SPX");
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
    	
		long sernum=0;
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag("Y");
		containerAssignmentVO.setArrivalFlag("N");
		containerAssignmentVO.setPou("FRA");
		containerAssignmentVO.setContainerType("U");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestForContainerArrivalMailbagNull()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, PersistenceException, ProxyException, FinderException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<>();
		Collection<FlightValidationVO> flightDetailsVOs=null;
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		scannedMailDetailsVO.setContainerNumber("AKE94847LH");
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerType("U");
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		//securityScreeningValidationVO.setErrorType(MailConstantsVO.ERROR);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO=new MailbagVO();
		mailBagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(null);
		containerDetailsVOs.add(containerDetailsVO);
		Collection<String> groupNames = new ArrayList<>();
		groupNames.add("TXNARPGRP");
		MailbagVO mailbagVo = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVo.setCompanyCode("IBS");
		mailbagVo.setMailSequenceNumber(456);
		mailbagVo.setSecurityStatusCode("SPX");
		mailBagPK.setCompanyCode("IBS"); 
		mailBagPK.setMailSequenceNumber(456);
		mailBag.setMailbagPK(mailBagPK);
		mailBag.setSecurityStatusCode("SPX");
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
		long sernum=0;
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag("Y");
		containerAssignmentVO.setArrivalFlag("N");
		containerAssignmentVO.setPou("FRA");
		containerAssignmentVO.setContainerType("U");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestForContainerArrivalWithoutMailbags()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, PersistenceException, ProxyException, FinderException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<>();
		Collection<FlightValidationVO> flightDetailsVOs=null;
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		scannedMailDetailsVO.setContainerNumber("AKE94847LH");
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerType("U");
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		//securityScreeningValidationVO.setErrorType(MailConstantsVO.ERROR);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setMailDetails(mailBagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		Collection<String> groupNames = new ArrayList<>();
		groupNames.add("TXNARPGRP");
		MailbagVO mailbagVo = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVo.setCompanyCode("IBS");
		mailbagVo.setMailSequenceNumber(456);
		mailbagVo.setSecurityStatusCode("SPX");
		mailBagPK.setCompanyCode("IBS"); 
		mailBagPK.setMailSequenceNumber(456);
		mailBag.setMailbagPK(mailBagPK);
		mailBag.setSecurityStatusCode("SPX");
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
		long sernum=0;
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag("Y");
		containerAssignmentVO.setArrivalFlag("N");
		containerAssignmentVO.setPou("FRA");
		containerAssignmentVO.setContainerType("U");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestForContainerArrivalWithSystemException()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, PersistenceException, ProxyException, FinderException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<>();
		Collection<FlightValidationVO> flightDetailsVOs=null;
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		scannedMailDetailsVO.setContainerNumber("AKE94847LH");
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerType("U");
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		//securityScreeningValidationVO.setErrorType(MailConstantsVO.ERROR);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO=new MailbagVO();
		mailBagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailBagVOs);
		containerDetailsVO.setMailDetails(mailBagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		Collection<String> groupNames = new ArrayList<>();
		groupNames.add("TXNARPGRP");
		MailbagVO mailbagVo = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVo.setCompanyCode("IBS");
		mailbagVo.setMailSequenceNumber(456);
		mailbagVo.setSecurityStatusCode("SPX");
		mailBagPK.setCompanyCode("IBS"); 
		mailBagPK.setMailSequenceNumber(456);
		mailBag.setMailbagPK(mailBagPK);
		mailBag.setSecurityStatusCode("SPX");
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
		long sernum=0;
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		SystemException systemException = new SystemException("system exception");
		doThrow(systemException).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag("Y");
		containerAssignmentVO.setArrivalFlag("N");
		containerAssignmentVO.setPou("FRA");
		containerAssignmentVO.setContainerType("U");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestForContainerArrivalWithProxyException()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, PersistenceException, ProxyException, FinderException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<>();
		Collection<FlightValidationVO> flightDetailsVOs=null;
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		scannedMailDetailsVO.setContainerNumber("AKE94847LH");
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerType("U");
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		//securityScreeningValidationVO.setErrorType(MailConstantsVO.ERROR);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO=new MailbagVO();
		mailBagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailBagVOs);
		containerDetailsVO.setMailDetails(mailBagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		Collection<String> groupNames = new ArrayList<>();
		groupNames.add("TXNARPGRP");
		MailbagVO mailbagVo = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVo.setCompanyCode("IBS");
		mailbagVo.setMailSequenceNumber(456);
		mailbagVo.setSecurityStatusCode("SPX");
		mailBagPK.setCompanyCode("IBS"); 
		mailBagPK.setMailSequenceNumber(456);
		mailBag.setMailbagPK(mailBagPK);
		mailBag.setSecurityStatusCode("SPX");
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
		long sernum=0;
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		ProxyException proxyException = new ProxyException("proxy exception", null);
		doThrow(proxyException).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(containerDetailsVOs).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag("Y");
		containerAssignmentVO.setArrivalFlag("N");
		containerAssignmentVO.setPou("FRA");
		containerAssignmentVO.setContainerType("U");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestForContainerArrivalWithoutContainer()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, PersistenceException, ProxyException, FinderException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<>();
		Collection<FlightValidationVO> flightDetailsVOs=null;
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		scannedMailDetailsVO.setContainerNumber("AKE94847LH");
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerType("U");
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		//securityScreeningValidationVO.setErrorType(MailConstantsVO.ERROR);
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setMailDetails(mailBagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		Collection<String> groupNames = new ArrayList<>();
		groupNames.add("TXNARPGRP");
		MailbagVO mailbagVo = new MailbagVO();
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = new Mailbag();
		mailbagVo.setCompanyCode("IBS");
		mailbagVo.setMailSequenceNumber(456);
		mailbagVo.setSecurityStatusCode("SPX");
		mailBagPK.setCompanyCode("IBS"); 
		mailBagPK.setMailSequenceNumber(456);
		mailBag.setMailbagPK(mailBagPK);
		mailBag.setSecurityStatusCode("SPX");
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
		long sernum=0;
		doReturn(sernum).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(null).when(dao).findMailbagsInContainer(anyCollectionOf(ContainerDetailsVO.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag("Y");
		containerAssignmentVO.setArrivalFlag("N");
		containerAssignmentVO.setPou("FRA");
		containerAssignmentVO.setContainerType("U");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test(expected=MailHHTBusniessException.class)
    public void doSecurityScreeningValidationsTestWithWarning()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		mailbagVO.setSecurityStatusCode("SPX");
		scannedMailDetailsVO.setProcessPoint("ARR");
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		securityScreeningValidationVO.setErrorType("E");
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		doReturn(null).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithEmptyMailDetails()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithNullMailDetails()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		scannedMailDetailsVO.setContainerNumber("");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithTransitFlagN()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_NO);
		scannedMailDetailsVO.setContainerNumber("AKE81769LH");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithArrivalFlagY()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_YES);
		containerAssignmentVO.setArrivalFlag(MailConstantsVO.FLAG_YES);
		scannedMailDetailsVO.setContainerNumber("AKE81769LH");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithContainerTypeNull()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		scannedMailDetailsVO.setAirportCode("FRA");
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_YES);
		containerAssignmentVO.setArrivalFlag(MailConstantsVO.FLAG_NO);
		containerAssignmentVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerNumber("AKE81769LH");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithContainerTypeNotEqual()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		scannedMailDetailsVO.setContainerType("U");
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_YES);
		containerAssignmentVO.setArrivalFlag(MailConstantsVO.FLAG_NO);
		containerAssignmentVO.setContainerType("B");
		containerAssignmentVO.setAirportCode("FRA");
		scannedMailDetailsVO.setContainerNumber("AKE81769LH");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithPouNotEqual()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setAirportCode("DFW");
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		scannedMailDetailsVO.setContainerType("U");
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_YES);
		containerAssignmentVO.setArrivalFlag(MailConstantsVO.FLAG_NO);
		containerAssignmentVO.setContainerType("U");
		containerAssignmentVO.setAirportCode("FRA");
		containerAssignmentVO.setPou("FRA");
		scannedMailDetailsVO.setContainerNumber("AKE81769LH");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithPouNotEqualAndBulk()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setAirportCode("DFW");
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		scannedMailDetailsVO.setContainerType("B");
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_YES);
		containerAssignmentVO.setArrivalFlag(MailConstantsVO.FLAG_NO);
		containerAssignmentVO.setContainerType("B");
		containerAssignmentVO.setAirportCode("FRA");
		containerAssignmentVO.setPou("DFW");
		scannedMailDetailsVO.setContainerNumber("AKE81769LH");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);
	}
	@Test
    public void doSecurityScreeningValidationsTestWithTransitFlagNoAtArrival()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setAirportCode("DFW");
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		scannedMailDetailsVO.setContainerType("B");
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_NO);
		containerAssignmentVO.setPou("DFW");
		scannedMailDetailsVO.setContainerNumber("AKE81769LH");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);
	}
	@Test
    public void doSecurityScreeningValidationsTestWithArrivalFlagYesAtArrival()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		scannedMailDetailsVO.setContainerType("B");
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_YES);
		containerAssignmentVO.setArrivalFlag(MailConstantsVO.FLAG_YES);
		containerAssignmentVO.setPou("DFW");
		scannedMailDetailsVO.setContainerNumber("AKE81769LH");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);
	}
	@Test
    public void doSecurityScreeningValidationsTestWithPouDiffAtArrival()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setAirportCode("FRA");
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		scannedMailDetailsVO.setContainerType("B");
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_YES);
		containerAssignmentVO.setArrivalFlag(MailConstantsVO.FLAG_NO);
		containerAssignmentVO.setPou("DFW");
		scannedMailDetailsVO.setContainerNumber("AKE81769LH");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();	
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);
	}
	@Test
    public void doSecurityScreeningValidationsTestContainerWithProcessPointNull()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setProcessPoint(null);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setContainerNumber("");
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithScreening()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		mailbagVO.setSecurityStatusCode("SPX");
		scannedMailDetailsVO.setProcessPoint("TRA");
		scannedMailDetailsVO.setScreeningPresent(true);
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithoutContainerAssgnmentVO()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		ContainerAssignmentVO containerAssignmentVO = null;
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithoutContainerAssgnmentVOFromWS()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailSource(MailConstantsVO.WS);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestFromWS()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		mailbagVO.setSecurityStatusCode("SPX");
		scannedMailDetailsVO.setProcessPoint("TRA");
		scannedMailDetailsVO.setMailSource(MailConstantsVO.WS);
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestFromNotSecValReqForDel()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		mailbagVO.setSecurityStatusCode("SPX");
		scannedMailDetailsVO.setProcessPoint("TRA");
		scannedMailDetailsVO.setNotReqSecurityValAtDel(true);
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void doSecurityScreeningValidationsTestWithOtherProcessPoint()
    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
		mailbagVO.setSecurityStatusCode("SPX");
		scannedMailDetailsVO.setProcessPoint("TRA");
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
		SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<>();
		securityScreeningValidationVOs.add(securityScreeningValidationVO);
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		doReturn(null).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
		doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
		mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

	}
	@Test
    public void checkSecValRequiredForDeliveryTestForNew() {
		MailUploadVO uploadedMaibagVO=new MailUploadVO();
		uploadedMaibagVO.setDeliverd(true);
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		scannedMailDetailsVO.setAirportCode("FRA");
		Mailbag mailbag = new Mailbag();
		mailbag.setLatestStatus(MailConstantsVO.MAIL_STATUS_NEW);
		mailbag.setScannedPort("FRA");
		mailUploadControllerSpy.checkForSecValRequiredAtDeliveryAndAppReqFlag(uploadedMaibagVO,scannedMailDetailsVO,mailbag);
	}
	@Test
    public void checkSecValRequiredForDeliveryTest() {
		MailUploadVO uploadedMaibagVO=new MailUploadVO();
		uploadedMaibagVO.setDeliverd(true);
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		scannedMailDetailsVO.setAirportCode("FRA");
		Mailbag mailbag = new Mailbag();
		mailbag.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
		mailbag.setScannedPort("FRA");
		mailUploadControllerSpy.checkForSecValRequiredAtDeliveryAndAppReqFlag(uploadedMaibagVO,scannedMailDetailsVO,mailbag);

	}
	@Test
    public void checkSecValRequiredForDeliveryTestWithDeliveryFlagFalse() {
		MailUploadVO uploadedMaibagVO=new MailUploadVO();
		uploadedMaibagVO.setDeliverd(false);
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		scannedMailDetailsVO.setAirportCode("FRA");
		Mailbag mailbag = new Mailbag();
		mailbag.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
		mailbag.setScannedPort("FRA");
		mailUploadControllerSpy.checkForSecValRequiredAtDeliveryAndAppReqFlag(uploadedMaibagVO,scannedMailDetailsVO,mailbag);

	}
	@Test
    public void checkSecValRequiredForDeliveryTestWithAirportCodeNull() {
		MailUploadVO uploadedMaibagVO=new MailUploadVO();
		uploadedMaibagVO.setDeliverd(true);
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		Mailbag mailbag = new Mailbag();
		mailbag.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
		mailbag.setScannedPort("FRA");
		mailUploadControllerSpy.checkForSecValRequiredAtDeliveryAndAppReqFlag(uploadedMaibagVO,scannedMailDetailsVO,mailbag);

	}
	@Test
    public void checkSecValRequiredForDeliveryTestWithAirportAndScannedPortNotEqual() {
		MailUploadVO uploadedMaibagVO=new MailUploadVO();
		uploadedMaibagVO.setDeliverd(true);
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		scannedMailDetailsVO.setAirportCode("DFW");
		Mailbag mailbag = new Mailbag();
		mailbag.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
		mailbag.setScannedPort("FRA");
		mailUploadControllerSpy.checkForSecValRequiredAtDeliveryAndAppReqFlag(uploadedMaibagVO,scannedMailDetailsVO,mailbag);

	}
	@Test
    public void checkSecValRequiredForDeliveryTestWithLatestStatusAcp() {
		MailUploadVO uploadedMaibagVO=new MailUploadVO();
		uploadedMaibagVO.setDeliverd(true);
		ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		scannedMailDetailsVO.setAirportCode("FRA");
		Mailbag mailbag = new Mailbag();
		mailbag.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		mailbag.setScannedPort("FRA");
		mailUploadControllerSpy.checkForSecValRequiredAtDeliveryAndAppReqFlag(uploadedMaibagVO,scannedMailDetailsVO,mailbag);

	}
	
	
		@Test(expected=MailHHTBusniessException.class)
	public void checkForClosedFlight_Test() throws MailMLDBusniessException,
			MailHHTBusniessException, SystemException, FlightClosedException, PersistenceException, FinderException, ForceAcceptanceException {

			ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setContainerNumber("AKE43532AV");
		mailUploadVO.setScanType("FUL");
		String scanningPort = "CDG";
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setCarrierCode("AV");
		containerAssignmentVO.setCarrierId(1134);
		containerAssignmentVO.setFlightNumber("1123");
		containerAssignmentVO.setFlightSequenceNumber(1);
		containerAssignmentVO.setAirportCode("CDG");
		containerAssignmentVO.setFlightDate(currentDate);
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		mailAcceptanceVO.setPol("CDG");
		mailAcceptanceVO.setCompanyCode("AV");
		mailAcceptanceVO.setFlightNumber("1111");
		mailAcceptanceVO.setFlightCarrierCode("AV");
		mailAcceptanceVO.setFlightDate(currentDate);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();

		assignedFlightPk.setCompanyCode("AV");
		assignedFlightPk.setFlightNumber("1111");
		assignedFlightPk.setFlightSequenceNumber(1);
		assignedFlightPk.setLegSerialNumber(1);
		assignedFlightPk.setCarrierId(1134);
		assignedFlightPk.setAirportCode("CDG");
		AssignedFlight assignedFlight = new AssignedFlight();
		assignedFlight.setCarrierCode("AV");
		assignedFlight.setFlightDate(currentDate);
		assignedFlight.setExportClosingFlag("C");

		doReturn(containerAssignmentVO).when(mailControllerBean).findLatestContainerAssignment(any());
		doReturn(assignedFlight).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class),
				any(AssignedFlightPK.class));
		doThrow(FlightClosedException.class).when(mailAcceptance).checkForClosedFlight(mailAcceptanceVO);

		mailUploadControllerSpy.checkForClosedFlight(scannedMailDetailsVO, containerAssignmentVO);
	}
		
		
		@Test(expected=MailHHTBusniessException.class)
	public void saveMailUploadDetailsForULDFULIndicator_ClosedFlight_Test() throws MailMLDBusniessException,
			MailHHTBusniessException, SystemException, FlightClosedException, PersistenceException, FinderException {

		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setContainerNumber("AKE43532AV");
		mailUploadVO.setScanType("FUL");
		String scanningPort = "CDG";
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setCarrierCode("AV");
		containerAssignmentVO.setCarrierId(1134);
		containerAssignmentVO.setFlightNumber("1123");
		containerAssignmentVO.setFlightSequenceNumber(1);
		containerAssignmentVO.setAirportCode("CDG");
		containerAssignmentVO.setFlightDate(currentDate);
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		mailAcceptanceVO.setPol("CDG");
		mailAcceptanceVO.setCompanyCode("AV");
		mailAcceptanceVO.setFlightNumber("1111");
		mailAcceptanceVO.setFlightCarrierCode("AV");
		mailAcceptanceVO.setFlightDate(currentDate);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();

		assignedFlightPk.setCompanyCode("AV");
		assignedFlightPk.setFlightNumber("1111");
		assignedFlightPk.setFlightSequenceNumber(1);
		assignedFlightPk.setLegSerialNumber(1);
		assignedFlightPk.setCarrierId(1134);
		assignedFlightPk.setAirportCode("CDG");
		AssignedFlight assignedFlight = new AssignedFlight();
		assignedFlight.setCarrierCode("AV");
		assignedFlight.setFlightDate(currentDate);
		assignedFlight.setExportClosingFlag("C");

		doReturn(containerAssignmentVO).when(mailControllerBean).findLatestContainerAssignment(any());
		doReturn(assignedFlight).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class),
				any(AssignedFlightPK.class));
		doThrow(FlightClosedException.class).when(mailAcceptance).checkForClosedFlight(mailAcceptanceVO);

		mailUploadControllerSpy.saveMailUploadDetailsForULDFULIndicator(mailUploadVO, scanningPort);
	}
		
			@Test
	public void saveMailUploadDetailsForULDFULIndicator_Ful_Condition_Test() throws MailMLDBusniessException,
			MailHHTBusniessException, SystemException, FlightClosedException, PersistenceException, FinderException {

		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setContainerNumber("AKE43532AV");
		mailUploadVO.setScanType("");
		String scanningPort = "CDG";
		ContainerAssignmentVO containerAssignmentVO = null;

		mailUploadControllerSpy.saveMailUploadDetailsForULDFULIndicator(mailUploadVO, scanningPort);

		}
		
	
		@Test
	public void saveMailUploadDetailsForULDFULIndicator_ClosedFlight_ExportFlight_Test() throws MailMLDBusniessException,
			MailHHTBusniessException, SystemException, FlightClosedException, PersistenceException, FinderException {

		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setContainerNumber("AKE43532AV");
		mailUploadVO.setScanType("FUL");
		String scanningPort = "CDG";
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setCompanyCode("IBS");
		containerAssignmentVO.setCarrierCode("AV");
		containerAssignmentVO.setCarrierId(1134);
		containerAssignmentVO.setFlightNumber("1123");
		containerAssignmentVO.setFlightSequenceNumber(1);
		containerAssignmentVO.setAirportCode("CDG");
		containerAssignmentVO.setFlightDate(currentDate);
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		mailAcceptanceVO.setPol("CDG");
		mailAcceptanceVO.setCompanyCode("AV");
		mailAcceptanceVO.setFlightNumber("1111");
		mailAcceptanceVO.setFlightCarrierCode("AV");
		mailAcceptanceVO.setFlightDate(currentDate);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();

		assignedFlightPk.setCompanyCode("AV");
		assignedFlightPk.setFlightNumber("1111");
		assignedFlightPk.setFlightSequenceNumber(1);
		assignedFlightPk.setLegSerialNumber(1);
		assignedFlightPk.setCarrierId(1134);
		assignedFlightPk.setAirportCode("CDG");
		AssignedFlight assignedFlight = new AssignedFlight();
		assignedFlight.setCarrierCode("AV");
		assignedFlight.setFlightDate(currentDate);
		assignedFlight.setExportClosingFlag("I");

		doReturn(containerAssignmentVO).when(mailControllerBean).findLatestContainerAssignment(any());
		doReturn(assignedFlight).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class),
				any(AssignedFlightPK.class));
		doThrow(FlightClosedException.class).when(mailAcceptance).checkForClosedFlight(mailAcceptanceVO);

		mailUploadControllerSpy.saveMailUploadDetailsForULDFULIndicator(mailUploadVO, scanningPort);
	}
@Test		
public void importMailProvisionalRateData() throws SystemException, ProxyException {
	HashMap<String, String> systemParameterMap = new HashMap();
	Collection<RateAuditVO> rateAuditVOs = new ArrayList();
	Collection<MailbagVO> mailbagVOs = new ArrayList<>();
	MailbagVO mailbagVO = new MailbagVO();
	mailbagVO.setOperationalFlag("I");
	mailbagVO.setPaCode("US101");
	mailbagVOs.add(mailbagVO);
	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
	scannedMailDetailsVO.setMailDetails(mailbagVOs);
	Collection<RateAuditDetailsVO> rateAuditDetailsVOs = new ArrayList();
	RateAuditVO auditVO = new RateAuditVO();
	auditVO.setCompanyCode("IBS");
	auditVO.setMailSequenceNumber(234567);
	RateAuditDetailsVO auditDetail = new RateAuditDetailsVO();
	auditDetail.setSource("TRA");
	rateAuditDetailsVOs.add(auditDetail);
	auditVO.setRateAuditDetails(rateAuditDetailsVOs);
	rateAuditVOs.add(auditVO);
	systemParameterMap.put("mailtracking.defaults.importsmailstomra", "Y");
	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
	doReturn(rateAuditVOs).when(mailControllerBean).createRateAuditVOs(any(OperationalFlightVO.class), any(ContainerVO.class), anyCollectionOf(MailbagVO.class), any(String.class), any(Boolean.class));
	doNothing().when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
	mailUploadControllerSpy.importMailProvisionalRateData(scannedMailDetailsVO);	
}
@Test		
public void importMailProvisionalRateData_WithImportNotEnabled() throws SystemException, ProxyException {
	HashMap<String, String> systemParameterMap = new HashMap();
	Collection<RateAuditVO> rateAuditVOs = new ArrayList();
	Collection<MailbagVO> mailbagVOs = new ArrayList<>();
	MailbagVO mailbagVO = new MailbagVO();
	mailbagVO.setOperationalFlag("I");
	mailbagVO.setPaCode("US101");
	mailbagVOs.add(mailbagVO);
	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
	scannedMailDetailsVO.setMailDetails(mailbagVOs);
	Collection<RateAuditDetailsVO> rateAuditDetailsVOs = new ArrayList();
	RateAuditVO auditVO = new RateAuditVO();
	auditVO.setCompanyCode("IBS");
	auditVO.setMailSequenceNumber(234567);
	RateAuditDetailsVO auditDetail = new RateAuditDetailsVO();
	auditDetail.setSource("TRA");
	rateAuditDetailsVOs.add(auditDetail);
	auditVO.setRateAuditDetails(rateAuditDetailsVOs);
	rateAuditVOs.add(auditVO);
	systemParameterMap.put("mailtracking.defaults.importsmailstomra", "N");
	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
	doReturn(rateAuditVOs).when(mailControllerBean).createRateAuditVOs(any(OperationalFlightVO.class), any(ContainerVO.class), anyCollectionOf(MailbagVO.class), any(String.class), any(Boolean.class));
	doNothing().when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
	mailUploadControllerSpy.importMailProvisionalRateData(scannedMailDetailsVO);	
}

@Test		
public void importMailProvisionalRateData_WithoutImportToMra() throws SystemException, ProxyException {
	HashMap<String, String> systemParameterMap = new HashMap();
	Collection<RateAuditVO> rateAuditVOs = new ArrayList();
	Collection<MailbagVO> mailbagVOs = new ArrayList<>();
	MailbagVO mailbagVO = new MailbagVO();
	mailbagVO.setOperationalFlag("I");
	mailbagVO.setPaCode("US101");
	mailbagVOs.add(mailbagVO);
	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
	scannedMailDetailsVO.setMailDetails(mailbagVOs);
	Collection<RateAuditDetailsVO> rateAuditDetailsVOs = new ArrayList();
	systemParameterMap.put("mailtracking.defaults.importsmailstomra", null);
	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
	doReturn(rateAuditVOs).when(mailControllerBean).createRateAuditVOs(any(OperationalFlightVO.class), any(ContainerVO.class), anyCollectionOf(MailbagVO.class), any(String.class), any(Boolean.class));
	doNothing().when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
	mailUploadControllerSpy.importMailProvisionalRateData(scannedMailDetailsVO);	
}
@Test(expected=SystemException.class)	
public void importMailProvisionalRateData_throwsException() throws SystemException, ProxyException {
	HashMap<String, String> systemParameterMap = new HashMap();
	Collection<RateAuditVO> rateAuditVOs = new ArrayList();
	Collection<MailbagVO> mailbagVOs = new ArrayList<>();
	MailbagVO mailbagVO = new MailbagVO();
	mailbagVO.setOperationalFlag("I");
	mailbagVO.setPaCode("US101");
	mailbagVOs.add(mailbagVO);
	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
	scannedMailDetailsVO.setMailDetails(mailbagVOs);
	Collection<RateAuditDetailsVO> rateAuditDetailsVOs = new ArrayList();
	RateAuditVO auditVO = new RateAuditVO();
	auditVO.setCompanyCode("IBS");
	auditVO.setMailSequenceNumber(234567);
	RateAuditDetailsVO auditDetail = new RateAuditDetailsVO();
	auditDetail.setSource("TRA");
	rateAuditDetailsVOs.add(auditDetail);
	auditVO.setRateAuditDetails(rateAuditDetailsVOs);
	rateAuditVOs.add(auditVO);
	systemParameterMap.put("mailtracking.defaults.importsmailstomra", "Y");
	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
	doReturn(rateAuditVOs).when(mailControllerBean).createRateAuditVOs(any(OperationalFlightVO.class), any(ContainerVO.class), anyCollectionOf(MailbagVO.class), any(String.class), any(Boolean.class));
	doThrow(ProxyException.class).when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
	mailUploadControllerSpy.importMailProvisionalRateData(scannedMailDetailsVO);	
}
@Test
	    public void doSecurityScreeningValidationsWithAppRegFlgwithProxyException()
	    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
			ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
			Collection<FlightValidationVO> flightDetailsVOs=null;
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			mailbagVO.setOrigin("FRA");
			mailbagVO.setDestination("DFW");
			mailBagVOs.add(mailbagVO);
			scannedMailDetailsVO.setMailDetails(mailBagVOs);
			scannedMailDetailsVO.setAirportCode("LAX");
			scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
			securityScreeningValidationVO.setErrorType("W");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
			AirportValidationVO airportValidationVO= new AirportValidationVO();
			airportValidationVO.setCountryCode("DE");
			AirportValidationVO airportValidationVO1= new AirportValidationVO();
			airportValidationVO1.setCountryCode("US");
			AirportValidationVO airportValidationVO2= new AirportValidationVO();
			airportValidationVO2.setCountryCode("DE");
			countryCodeMap.put("FRA",airportValidationVO);
			countryCodeMap.put("DFW",airportValidationVO1);
			countryCodeMap.put("LAX",airportValidationVO2);
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
			securityScreeningValidationVOs.add(securityScreeningValidationVO);
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
			ProxyException proxyException = new ProxyException("proxy exception", null);
			doReturn(null).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
			doThrow(proxyException).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
			SystemException systemException = new SystemException("system exception");
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
			ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
			doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
			mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

		}
		@Test
	    public void doSecurityScreeningValidationsWithAppRegFlg()
	    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
			ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
			Collection<FlightValidationVO> flightDetailsVOs=null;
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			mailbagVO.setOrigin("FRA");
			mailbagVO.setDestination("DFW");
			mailBagVOs.add(mailbagVO);
			scannedMailDetailsVO.setMailDetails(mailBagVOs);
			scannedMailDetailsVO.setAirportCode("LAX");
			scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
			securityScreeningValidationVO.setErrorType("E");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
			AirportValidationVO airportValidationVO= new AirportValidationVO();
			airportValidationVO.setCountryCode("DE");
			AirportValidationVO airportValidationVO1= new AirportValidationVO();
			airportValidationVO1.setCountryCode("US");
			AirportValidationVO airportValidationVO2= new AirportValidationVO();
			airportValidationVO2.setCountryCode("DE");
			countryCodeMap.put("FRA",airportValidationVO);
			countryCodeMap.put("DFW",airportValidationVO1);
			countryCodeMap.put("LAX",airportValidationVO2);
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
			securityScreeningValidationVOs.add(securityScreeningValidationVO);
			doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
			Collection<String> groupNames = new ArrayList<>();
			groupNames.add("DSTARPCNTGRP");
			doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
			doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
			ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
			doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
			mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

		}
		@Test
	    public void doSecurityScreeningValidationsWithAppRegFlgOrginNull()
	    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
			ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
			Collection<FlightValidationVO> flightDetailsVOs=null;
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			mailbagVO.setDestination("DFW");
			mailBagVOs.add(mailbagVO);
			scannedMailDetailsVO.setMailDetails(mailBagVOs);
			scannedMailDetailsVO.setAirportCode("LAX");
			scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
			securityScreeningValidationVO.setErrorType("E");
			securityScreeningValidationVO.setValidationType("AR");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
			AirportValidationVO airportValidationVO= new AirportValidationVO();
			airportValidationVO.setCountryCode("DE");
			AirportValidationVO airportValidationVO1= new AirportValidationVO();
			airportValidationVO1.setCountryCode("US");
			AirportValidationVO airportValidationVO2= new AirportValidationVO();
			airportValidationVO2.setCountryCode("DE");
			countryCodeMap.put("DFW",airportValidationVO1);
			countryCodeMap.put("LAX",airportValidationVO2);
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
			securityScreeningValidationVOs.add(securityScreeningValidationVO);
			doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
			Collection<String> groupNames = new ArrayList<>();
			groupNames.add("DSTARPCNTGRP");
			doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
			doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
			ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
			doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
			mailUploadControllerSpy.doSecurityScreeningValidations(scannedMailDetailsVO,flightDetailsVOs);

		}
		@Test()
	    public void checkAppRegFlagValidationForPABuildContainerTest()
	    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
			ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
			Collection<FlightValidationVO> flightDetailsVOs=null;
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			mailbagVO.setDestination("DFW");
			mailBagVOs.add(mailbagVO);
			scannedMailDetailsVO.setMailDetails(mailBagVOs);
			scannedMailDetailsVO.setAirportCode("LAX");
			scannedMailDetailsVO.setErrorMailDetails(mailBagVOs);
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
			securityScreeningValidationVO.setErrorType("X");
			securityScreeningValidationVO.setValidationType("AR");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
			AirportValidationVO airportValidationVO= new AirportValidationVO();
			airportValidationVO.setCountryCode("DE");
			AirportValidationVO airportValidationVO1= new AirportValidationVO();
			airportValidationVO1.setCountryCode("US");
			AirportValidationVO airportValidationVO2= new AirportValidationVO();
			airportValidationVO2.setCountryCode("DE");
			countryCodeMap.put("DFW",airportValidationVO1);
			countryCodeMap.put("LAX",airportValidationVO2);
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
			securityScreeningValidationVOs.add(securityScreeningValidationVO);
			doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
			Collection<String> groupNames = new ArrayList<>();
			groupNames.add("DSTARPCNTGRP");
			doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
			ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
			doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
			mailUploadControllerSpy.checkAppRegFlagValidationForPABuildContainer(mailBagVOs,scannedMailDetailsVO);

		}
		@Test()
	    public void checkAppRegFlagValidationForPABuildContainerTestWithError()
	    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
			ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
			Collection<FlightValidationVO> flightDetailsVOs=null;
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			mailbagVO.setDestination("DFW");
			mailBagVOs.add(mailbagVO);
			//scannedMailDetailsVO.setMailDetails(mailBagVOs);
			scannedMailDetailsVO.setAirportCode("LAX");
		    scannedMailDetailsVO.setErrorMailDetails( new ArrayList<MailbagVO>());
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
			securityScreeningValidationVO.setErrorType("W");
			securityScreeningValidationVO.setValidationType("AR");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
			AirportValidationVO airportValidationVO= new AirportValidationVO();
			airportValidationVO.setCountryCode("DE");
			AirportValidationVO airportValidationVO1= new AirportValidationVO();
			airportValidationVO1.setCountryCode("US");
			AirportValidationVO airportValidationVO2= new AirportValidationVO();
			airportValidationVO2.setCountryCode("DE");
			countryCodeMap.put("DFW",airportValidationVO1);
			countryCodeMap.put("LAX",airportValidationVO2);
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
			securityScreeningValidationVOs.add(securityScreeningValidationVO);
			doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
			Collection<String> groupNames = new ArrayList<>();
			groupNames.add("DSTARPCNTGRP");
			doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
			doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
			ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
			doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
			mailUploadControllerSpy.checkAppRegFlagValidationForPABuildContainer(mailBagVOs,scannedMailDetailsVO);

		}
		@Test()
	    public void checkAppRegFlagValidationForPABuildContainerTestWithPou()
	    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
			ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
			Collection<FlightValidationVO> flightDetailsVOs=null;
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			mailbagVO.setDestination("DFW");
			mailBagVOs.add(mailbagVO);
			//scannedMailDetailsVO.setMailDetails(mailBagVOs);
			scannedMailDetailsVO.setAirportCode("LAX");
			scannedMailDetailsVO.setPou("DFW");
		    scannedMailDetailsVO.setErrorMailDetails( new ArrayList<MailbagVO>());
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
			securityScreeningValidationVO.setErrorType("W");
			securityScreeningValidationVO.setValidationType("AR");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
			AirportValidationVO airportValidationVO= new AirportValidationVO();
			airportValidationVO.setCountryCode("DE");
			AirportValidationVO airportValidationVO1= new AirportValidationVO();
			airportValidationVO1.setCountryCode("US");
			AirportValidationVO airportValidationVO2= new AirportValidationVO();
			airportValidationVO2.setCountryCode("DE");
			countryCodeMap.put("DFW",airportValidationVO1);
			countryCodeMap.put("LAX",airportValidationVO2);
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
			securityScreeningValidationVOs.add(securityScreeningValidationVO);
			doReturn(null).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
			Collection<String> groupNames = new ArrayList<>();
			doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
			doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
			ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
			doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
			mailUploadControllerSpy.checkAppRegFlagValidationForPABuildContainer(mailBagVOs,scannedMailDetailsVO);
		}
		@Test()
	    public void checkAppRegFlagValidationForPABuildContainerTestWithGroupPresent()
	    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
			ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
			Collection<FlightValidationVO> flightDetailsVOs=null;
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			mailbagVO.setDestination("DFW");
			mailBagVOs.add(mailbagVO);
			//scannedMailDetailsVO.setMailDetails(mailBagVOs);
			//scannedMailDetailsVO.setAirportCode("LAX");
			scannedMailDetailsVO.setPou("DFW");
		    scannedMailDetailsVO.setErrorMailDetails( new ArrayList<MailbagVO>());
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
			securityScreeningValidationVO.setErrorType("W");
			securityScreeningValidationVO.setValidationType("AR");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
			AirportValidationVO airportValidationVO= new AirportValidationVO();
			airportValidationVO.setCountryCode("DE");
			AirportValidationVO airportValidationVO1= new AirportValidationVO();
			airportValidationVO1.setCountryCode("US");
			AirportValidationVO airportValidationVO2= new AirportValidationVO();
			airportValidationVO2.setCountryCode("DE");
			countryCodeMap.put("DFW",airportValidationVO1);
			//countryCodeMap.put("LAX",airportValidationVO2);
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
			securityScreeningValidationVOs.add(securityScreeningValidationVO);
			doReturn(null).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
			Collection<String> groupNames = new ArrayList<>();
			groupNames.add("DSTARPCNTGRP");
			doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
			doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
			ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
			doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
			mailUploadControllerSpy.checkAppRegFlagValidationForPABuildContainer(mailBagVOs,scannedMailDetailsVO);

		}
		@Test()
	    public void checkAppRegFlagValidationForPABuildContainerTestWithSameCountries()
	    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
			ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
			Collection<FlightValidationVO> flightDetailsVOs=new ArrayList<>();
			FlightValidationVO flightValidationVO=new FlightValidationVO();
			flightDetailsVOs.add(flightValidationVO);
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			mailbagVO.setDestination("DFW");
			mailBagVOs.add(mailbagVO);
			//scannedMailDetailsVO.setMailDetails(mailBagVOs);
			scannedMailDetailsVO.setAirportCode("LAX");
			scannedMailDetailsVO.setPou("DFW");
		    scannedMailDetailsVO.setErrorMailDetails( new ArrayList<MailbagVO>());
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
			securityScreeningValidationVO.setErrorType("W");
			securityScreeningValidationVO.setValidationType("AR");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
			AirportValidationVO airportValidationVO= new AirportValidationVO();
			airportValidationVO.setCountryCode("DE");
			AirportValidationVO airportValidationVO1= new AirportValidationVO();
			airportValidationVO1.setCountryCode("DE");
			AirportValidationVO airportValidationVO2= new AirportValidationVO();
			airportValidationVO2.setCountryCode("DE");
			countryCodeMap.put("DFW",airportValidationVO1);
			countryCodeMap.put("LAX",airportValidationVO2);
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
			securityScreeningValidationVOs.add(securityScreeningValidationVO);
			doReturn(null).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
			Collection<String> groupNames = new ArrayList<>();
			groupNames.add("DSTARPCNTGRP");
			doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
			doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
			ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
			doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
			mailUploadControllerSpy.checkAppRegFlagValidationForPABuildContainer(mailBagVOs,scannedMailDetailsVO);

		}
		@Test()
	    public void checkAppRegFlagValidationForPABuildContainerTestWithPouEmpty()
	    		throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, ProxyException {
			ScannedMailDetailsVO scannedMailDetailsVO=new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
			Collection<FlightValidationVO> flightDetailsVOs=null;
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			mailbagVO.setDestination("DFW");
			mailbagVO.setConsignmentNumber("TEST");
			mailbagVO.setMailSubclass("1A");
			mailBagVOs.add(mailbagVO);
			MailbagVO mailbagVO2=new MailbagVO();
			mailbagVO2.setMailbagId("FRCDGADEFRAAAUA01170604990888");
			mailbagVO2.setDestination("DFW");
			mailbagVO2.setConsignmentNumber("TEST");
			mailbagVO2.setMailSubclass("1A");
			mailBagVOs.add(mailbagVO2);
			//scannedMailDetailsVO.setMailDetails(mailBagVOs);
			scannedMailDetailsVO.setAirportCode("LAX");
			scannedMailDetailsVO.setPou("");
		    scannedMailDetailsVO.setErrorMailDetails( new ArrayList<MailbagVO>());
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			SecurityScreeningValidationVO securityScreeningValidationVO= new SecurityScreeningValidationVO();
			securityScreeningValidationVO.setErrorType("W");
			securityScreeningValidationVO.setValidationType("AR");
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
			AirportValidationVO airportValidationVO= new AirportValidationVO();
			airportValidationVO.setCountryCode("DE");
			AirportValidationVO airportValidationVO1= new AirportValidationVO();
			airportValidationVO1.setCountryCode("US");
			AirportValidationVO airportValidationVO2= new AirportValidationVO();
			airportValidationVO2.setCountryCode("DE");
			countryCodeMap.put("DFW",airportValidationVO1);
			countryCodeMap.put("LAX",airportValidationVO2);
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = new ArrayList<SecurityScreeningValidationVO>();
			//securityScreeningValidationVOs.add(securityScreeningValidationVO);
			doReturn(securityScreeningValidationVOs).when(securityScreeningValidationCache).checkForSecurityScreeningValidation(any(SecurityScreeningValidationFilterVO.class));
			Collection<String> groupNames = new ArrayList<>();
			groupNames.add("DSTARPCNTGRP");
			doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
			doReturn(groupNames).when(sharedGeneralMasterGroupingProxy).findGroupNamesOfEntity(any(GeneralMasterGroupFilterVO.class));
			doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),anyCollectionOf(String.class));
			ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
			doReturn(containerAssignmentVO).when(mailUploadControllerSpy).getContainerAssignmentVOFromContext();
			mailUploadControllerSpy.checkAppRegFlagValidationForPABuildContainer(mailBagVOs,scannedMailDetailsVO);
		}
		@Test
		public void setPouOubForMLD0WithNullFlightValidationVOs() throws SystemException, ProxyException, TemplateRenderingException {
			MLDMasterVO mldMasterVO = new MLDMasterVO();
			MLDDetailVO mLDDetailVO = new MLDDetailVO();
			mldMasterVO.setMldDetailVO(mLDDetailVO);
			mLDDetailVO.setMessageVersion("0");
			doReturn(null).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
			mailUploadControllerSpy.setPouOubForMLD0(mldMasterVO, mLDDetailVO);
			
}
		@Test
		public void setPouOubForMLD0WithEmptyFlightValidationVOs() throws SystemException, ProxyException, TemplateRenderingException {
			MLDMasterVO mldMasterVO = new MLDMasterVO();
			MLDDetailVO mLDDetailVO = new MLDDetailVO();
			mldMasterVO.setMldDetailVO(mLDDetailVO);
			mLDDetailVO.setMessageVersion("0");
			doReturn(Collections.EMPTY_LIST).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
			mailUploadControllerSpy.setPouOubForMLD0(mldMasterVO, mLDDetailVO);
			
		
}	
@Test
public void setPouOubForMLD0WithFlightValidationVOs() throws SystemException, ProxyException, TemplateRenderingException {
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	MLDDetailVO mLDDetailVO = new MLDDetailVO();
	mLDDetailVO.setCarrierCodeOub("CC");
	mLDDetailVO.setFlightNumberOub("1234");
	mLDDetailVO.setFlightOperationDateOub(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, true));
	mldMasterVO.setMldDetailVO(mLDDetailVO);
	mLDDetailVO.setMessageVersion("0");
	Collection<FlightValidationVO> flights =  new ArrayList<>();
	FlightValidationVO flight = new FlightValidationVO();
	flight.setLegDestination("DST");
	flight.setCarrierCode("CC");
	flight.setFlightNumber("1234");
	flight.setFlightDate(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, true));
	flights.add(flight);
	doReturn(flights).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
	mailUploadControllerSpy.setPouOubForMLD0(mldMasterVO, mLDDetailVO);
	

	}
@Test
public void setPouOubForMLD0WithFlightValidationVOsWithDiffCarrier() throws SystemException, ProxyException, TemplateRenderingException {
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	MLDDetailVO mLDDetailVO = new MLDDetailVO();
	mLDDetailVO.setCarrierCodeOub("CC");
	mLDDetailVO.setFlightNumberOub("1234");
	mLDDetailVO.setFlightOperationDateOub(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, true));
	mldMasterVO.setMldDetailVO(mLDDetailVO);
	mLDDetailVO.setMessageVersion("0");
	Collection<FlightValidationVO> flights =  new ArrayList<>();
	FlightValidationVO flight = new FlightValidationVO();
	flight.setLegDestination("DST");
	flight.setCarrierCode("CB");
	flight.setFlightNumber("1234");
	flight.setFlightDate(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, true));
	flights.add(flight);
	doReturn(flights).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
	doReturn("connum").when(mailController).findAlreadyAssignedTrolleyNumberForMLD(any(MLDMasterVO.class));
	mailUploadControllerSpy.setPouOubForMLD0(mldMasterVO, mLDDetailVO);
	
	}
@Test
public void setPouOubForMLD0WithFlightValidationVOsWithDiffFltNum() throws SystemException, ProxyException, TemplateRenderingException {
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	MLDDetailVO mLDDetailVO = new MLDDetailVO();
	mLDDetailVO.setCarrierCodeOub("CC");
	mLDDetailVO.setFlightNumberOub("1234");
	mLDDetailVO.setFlightOperationDateOub(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, true));
	mldMasterVO.setMldDetailVO(mLDDetailVO);
	mLDDetailVO.setMessageVersion("0");
	Collection<FlightValidationVO> flights =  new ArrayList<>();
	FlightValidationVO flight = new FlightValidationVO();
	flight.setLegDestination("DST");
	flight.setCarrierCode("CC");
	flight.setFlightNumber("1235");
	flight.setFlightDate(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, true));
	flights.add(flight);
	doReturn(flights).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
	mailUploadControllerSpy.setPouOubForMLD0(mldMasterVO, mLDDetailVO);
	
}
@Test
public void generateTrolleyNumberForMLDVersion1() throws SystemException, ProxyException, TemplateRenderingException {
	MLDMasterVO mldMasterVO = new MLDMasterVO();
	MLDDetailVO mLDDetailVO = new MLDDetailVO();
	mLDDetailVO.setCarrierCodeOub("CC");
	mLDDetailVO.setFlightNumberOub("1234");
	mLDDetailVO.setFlightOperationDateOub(new LocalDate(LocalDate.NO_STATION,
			Location.NONE, true));
	mldMasterVO.setMldDetailVO(mLDDetailVO);
	mLDDetailVO.setMessageVersion("1");
	mailUploadControllerSpy.generateDummyTrolleyNumberForMLD(mldMasterVO, "Key");
	

	}
@Test
public void saveMailUploadDetailsFromMLD_WithcontainerAssignmentVOAsNullAndBarcodeTypeRVersion1() throws PersistenceException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException{
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
	MLDMasterVO mldMasterVo = new MLDMasterVO();
	MLDDetailVO mLDDetailVO = new MLDDetailVO();
	mldMasterVo.setBarcodeType("R");
	mldMasterVo.setBarcodeValue("J");
	mLDDetailVO.setMessageVersion("1");
	mldMasterVo.setEventCOde("NST");
	mldMasterVo.setSenderAirport("CDG");
	mldMasterVo.setUldNumber("AKE12345AV");
	mldMasterVo.setContainerType("U");
	mLDDetailVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
	mldMasterVo.setMldDetailVO(mLDDetailVO);
	mldMasterVo.setScanTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	mldMasterVOs.add(mldMasterVo);
	doReturn(null).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
	mailUploadControllerSpy.saveMailUploadDetailsFromMLD(mldMasterVOs);
}
@Test
public void saveMailUploadDetailsFromMLD_WithcontainerAssignmentVOAsNullAndBarcodeTypeRVersion0() throws PersistenceException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ForceAcceptanceException{
	Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
	MLDMasterVO mldMasterVo = new MLDMasterVO();
	MLDDetailVO mLDDetailVO = new MLDDetailVO();
	mldMasterVo.setBarcodeType("R");
	mldMasterVo.setBarcodeValue("J");
	mLDDetailVO.setMessageVersion("0");
	mldMasterVo.setEventCOde("NST");
	mldMasterVo.setSenderAirport("CDG");
	mldMasterVo.setUldNumber("AKE12345AV");
	mldMasterVo.setContainerType("U");
	mLDDetailVO.setContainerJourneyId("J43GBAXLSMIA051152EH");
	mldMasterVo.setMldDetailVO(mLDDetailVO);
	mldMasterVo.setScanTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	mldMasterVOs.add(mldMasterVo);
	doReturn(null).when(dao).findLatestContainerAssignment(any(String.class),any(String.class));
	mailUploadControllerSpy.saveMailUploadDetailsFromMLD(mldMasterVOs);
		}
		@Test
		public void saveRemarksForMailTag_Test() throws MailMLDBusniessException, MailHHTBusniessException, SystemException, FinderException {
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
			String scanningPort = "CDG";
			long mailSequenceNumber = 12;
			when(mailbagBean.findMailBagSequenceNumberFromMailIdr("DEFRAAUSORDAAUN17878501000133", any(String.class))).thenReturn(mailSequenceNumber);
			MailUploadVO mailUploadVO = new MailUploadVO();
			mailUploadVO.setCarrierCode("LH");
			mailUploadVO.setMailSequenceNumber(12);
			mailUploadVO.setRemarks("Container Clearance Waiting");
			mailUploadVO.setScannedDate(currentDate);
			mailUploadVO.setSerialNumber(1);
			mailUploadVO.setScanUser("ACF");
			mailUploadVO.setMailTag("DEFRAAUSORDAAUN17878501000133");
			MailHistoryRemarksVO mailHistoryRemarksVO = new MailHistoryRemarksVO();
			mailHistoryRemarksVO.setCompanyCode("LH");
			mailHistoryRemarksVO.setMailSequenceNumber(12);
			mailHistoryRemarksVO.setRemark("Container Clearance Waiting");
			mailHistoryRemarksVO.setRemarkDate(currentDate);
			mailHistoryRemarksVO.setRemarkSerialNumber(1);
			mailHistoryRemarksVO.setUserName("ACF"); 
			MailHistoryRemarks mailHistoryRemarks = new MailHistoryRemarks();
			mailHistoryRemarks.setRemarks("Container Clearance Waiting");
			mailHistoryRemarks.setUserName("ACF");
			MailHistoryRemarksPK mailHistoryRemarksPK = new MailHistoryRemarksPK();
			mailHistoryRemarksPK.setCompanyCode("LH");
			mailHistoryRemarksPK.setSerialNumber(1);
			doReturn(mailHistoryRemarks).when(PersistenceController.getEntityManager()).find(eq(MailHistoryRemarks.class),any(MailHistoryRemarksPK.class));
			mailUploadControllerSpy.saveRemarksForMailTag(mailUploadVO);
		}
		@Test
		public void saveRemarksForMailTag_TestForZeroSeq() throws MailMLDBusniessException, MailHHTBusniessException, SystemException, FinderException {
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
			String scanningPort = "CDG";
			long mailSequenceNumber = 0;
			when(mailbagBean.findMailBagSequenceNumberFromMailIdr("DEFRAAUSORDAAUN17878501000133", "IBS")).thenReturn(mailSequenceNumber);
			MailUploadVO mailUploadVO = new MailUploadVO();
			mailUploadVO.setCarrierCode("LH");
			mailUploadVO.setMailSequenceNumber(mailSequenceNumber);
			mailUploadVO.setRemarks("Container Clearance Waiting");
			mailUploadVO.setScannedDate(currentDate);
			mailUploadVO.setSerialNumber(1);
			mailUploadVO.setScanUser("ACF");
			mailUploadVO.setMailTag("DEFRAAUSORDAAUN17878501000133");
			mailUploadVO.setCompanyCode("IBS");
			MailHistoryRemarksVO mailHistoryRemarksVO = new MailHistoryRemarksVO();
			MailHistoryRemarks mailHistoryRemarks = new MailHistoryRemarks();
			mailHistoryRemarks.setRemarks("Container Clearance Waiting");
			mailHistoryRemarks.setUserName("ACF");
			MailHistoryRemarksPK mailHistoryRemarksPK = new MailHistoryRemarksPK();
			mailHistoryRemarksPK.setCompanyCode("LH");
			mailHistoryRemarksPK.setSerialNumber(1);
			doReturn(mailHistoryRemarks).when(PersistenceController.getEntityManager()).find(eq(MailHistoryRemarks.class),any(MailHistoryRemarksPK.class));
			mailUploadControllerSpy.saveRemarksForMailTag(mailUploadVO);
				
		}
		 @Test()
		   	public void doSaveMailUploadDetailsForGHA_RemarksTest() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException {
		    	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
		    	scannedMailDetailsVO.setArrivalException(true);
		    	scannedMailDetailsVO.setProcessPoint("DLV");
		    	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
		    	MailUploadVO mailUploadVO =new MailUploadVO();
		    	mailUploadVO.setScanType("RMK");
		    	mailscanVos.add(mailUploadVO);
		    	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
		    	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
		    	mailWebserviceVO.setScanType("RMK");
		    	mailWebserviceVOs.add(mailWebserviceVO);
		    	String scannedPort="SIN";
		    	doReturn(mailscanVos).when(mailUploadControllerSpy).createMailScanVOS((Collection<MailWebserviceVO>) any(Collections.class),any(String.class),any(StringBuilder.class),any(String.class));
		    	doReturn(scannedMailDetailsVO).when(mailUploadControllerSpy).saveRemarksForMailTag(any(MailUploadVO.class));
		        mailUploadControllerSpy.performMailOperationForGHA(mailWebserviceVOs, scannedPort);    	
		       }
		 	 @Test()
		    	public void saveRemarksForMailTag_TestForRMK() throws SystemException, MailMLDBusniessException, MailHHTBusniessException, MailTrackingBusinessException, PersistenceException, RemoteException, SharedProxyException {
		     	ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
		     	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		     	ArrayList<MailUploadVO> mailscanVos= new ArrayList<>();
		     	MailUploadVO mailUploadVO =new MailUploadVO();
		     	mailscanVos.add(mailUploadVO);
		     	Collection<MailWebserviceVO>mailWebserviceVOs =new ArrayList<>();
		     	MailWebserviceVO mailWebserviceVO=new MailWebserviceVO();
		     	mailWebserviceVO.setCompanyCode(getCompanyCode());
		         mailWebserviceVO.setCarrierCode(getCompanyCode());
		     	mailWebserviceVO.setPAbuilt(true);
		     	mailWebserviceVO.setScanType("RMK");
		     	mailWebserviceVO.setScanDateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		     	mailWebserviceVOs.add(mailWebserviceVO);
		     	String scannedPort="SIN";
		     	StringBuilder errorString=new StringBuilder();
		     	String errorFromMapping=null;
		     	doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(getCompanyCode(),getCompanyCode());
		     	mailUploadControllerSpy.createMailScanVOS(mailWebserviceVOs,scannedPort,errorString,errorFromMapping);    	
		        }
		 	 
		
	@Test
	public void shouldCalculateMailbagVolume() throws Exception {
		LogonAttributes logonAttributes  = ContextUtils.getSecurityContext().getLogonAttributesVO();
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		scannedMailDetailsVO.setCompanyCode(getCompanyCode());
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailSubclass("CA");
		scannedMailDetailsVO.setMailDetails(Stream.of(mailbagVO).collect(Collectors.toList()));
		CommodityValidationVO commodityValidationVO = new CommodityValidationVO();
		commodityValidationVO.setCommodityCode("MAL");
		commodityValidationVO.setCompanyCode(getCompanyCode());
		commodityValidationVO.setDensityFactor(120);
		Map<String, CommodityValidationVO> commodityMap = new HashMap<>();
		commodityMap.put("MAL",commodityValidationVO);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(any(),any());
		mailUploadControllerSpy.makeMailAcceptanceVO(scannedMailDetailsVO,logonAttributes);
		assertNotNull(scannedMailDetailsVO.getCompanyCode());
	}
	@Test
	public void shouldCalculateMailbagVolumeForUpdateVOTransfer() throws Exception {
		LogonAttributes logonAttributes  = ContextUtils.getSecurityContext().getLogonAttributesVO();
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		scannedMailDetailsVO.setCompanyCode(getCompanyCode());
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailSubclass("CA");
		scannedMailDetailsVO.setMailDetails(Stream.of(mailbagVO).collect(Collectors.toList()));
		CommodityValidationVO commodityValidationVO = new CommodityValidationVO();
		commodityValidationVO.setCommodityCode("MAL");
		commodityValidationVO.setCompanyCode(getCompanyCode());
		commodityValidationVO.setDensityFactor(120);
		Map<String, CommodityValidationVO> commodityMap = new HashMap<>();
		commodityMap.put("MAL",commodityValidationVO);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(any(),any());
		mailUploadControllerSpy.updateVOForTransfer(scannedMailDetailsVO,logonAttributes);
		assertNotNull(scannedMailDetailsVO.getCompanyCode());
	}
	@Test
	public void shouldCalculateMailbagVolumeForRESDITProcessing() throws Exception {
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag("USDFWADEFRAAACA31200120001200");
		mailUploadVO.setScannedPort("DFW");
		mailUploadVO.setSubClass("CA");
		mailUploadVO.setDateTime("01-JAN-2023 00:00:00");
		OfficeOfExchangePK officeOfExchangePK = new OfficeOfExchangePK();
		officeOfExchangePK.setCompanyCode(getCompanyCode());
		officeOfExchangePK.setOfficeOfExchange("USDFWA");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePK);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class), any(OfficeOfExchangePK.class));
		Map<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(MailConstantsVO.USPS_INTERNATIONAL_PA, "US101");
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		postalAdministrationVO.setCompanyCode(getCompanyCode());
		postalAdministrationVO.setPaCode("US101");
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(any(), any());
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		mailUploadControllerSpy.processResditMailsForAllEvents(Stream.of(mailUploadVO).collect(Collectors.toList()));
		assertNotNull(mailUploadVO.getCompanyCode());
	}
	
	@Test
	public void shouldSaveMailScanDetails() throws ProxyException, SystemException {
		

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag(MAILBAG_ID);
		mailUploadVO.setDamageCode("14");
		mailUploadVO.setScanUser("ICOADMIN");
		mailUploadVO.setScannedPort("DFW");
		mailUploadVO.setFunctionPoint("validate");
		mailUploadVO.setContainerJourneyId("14");
		mailUploadVO.setScanType("ACP");
		mailUploadVO.setCarrierCode("AA");
		mailUploadVO.setFlightNumber("1236");
		mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailUploadVO.setContainerNumber("AKE88675AA");
		mailUploadVO.setFromCarrierCode("AA");
		mailUploadVO.setTransferFrmFlightNum("1133");
		mailUploadVO.setTransferFrmFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailUploadVO.setDeliverFlag("N");
		mailUploadVO.setOffloadReason("2");
		mailUploadVO.setReturnCode("N");
		mailUploadVO.setDamageCode("N");
		mailUploadVO.setDestination("LAX");
		mailUploadVO.setContainerPOU("LAX");
		mailUploadVO.setContainerType("U");
		mailUploadVO.setCompanyCode("IBS");
		mailUploadVO.setScreeningUser("N");
		mailUploadVO.setSecurityMethods("N");
		mailUploadVO.setStorageUnit("N");
		mailUploadVO.setMailTag("USDFWADEFRAAACA31200120001200");
		mailUploadControllerSpy.saveMailScanDetails(mailUploadVO);

	}
	
	@Test
	public void shouldSaveMailScanDetailsWhenFlightNull() throws ProxyException, SystemException {
		

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setMailTag(MAILBAG_ID);
		mailUploadVO.setDamageCode("14");
		mailUploadVO.setScanUser("ICOADMIN");
		mailUploadVO.setScannedPort("DFW");
		mailUploadVO.setFunctionPoint("validate");
		mailUploadVO.setContainerJourneyId("14");
		mailUploadVO.setScanType("ACP");
		mailUploadVO.setCarrierCode("AA");
		mailUploadVO.setFlightNumber("1236");
		mailUploadVO.setFlightDate(null);
		mailUploadVO.setContainerNumber("AKE88675AA");
		mailUploadVO.setFromCarrierCode("AA");
		mailUploadVO.setTransferFrmFlightNum("1133");
		mailUploadVO.setTransferFrmFlightDate(null);
		mailUploadVO.setDeliverFlag("N");
		mailUploadVO.setOffloadReason("2");
		mailUploadVO.setReturnCode("N");
		mailUploadVO.setDamageCode("N");
		mailUploadVO.setDestination("LAX");
		mailUploadVO.setContainerPOU("LAX");
		mailUploadVO.setContainerType("U");
		mailUploadVO.setCompanyCode("IBS");
		mailUploadVO.setScreeningUser("N");
		mailUploadVO.setSecurityMethods("N");
		mailUploadVO.setStorageUnit("N");
		mailUploadVO.setMailTag("USDFWADEFRAAACA31200120001200");
		mailUploadControllerSpy.saveMailScanDetails(mailUploadVO);

	}
	@Test
	public void testProcessMRDPODWithoutFlightDetailsSuccess() throws SystemException {
		MailUploadVO mailUploadVO = new MailUploadVO();
		String mailTag ="NZAKLAAUSYDAAUX31600160910100";
		mailUploadVO.setMailTag(mailTag);
		HandoverVO handover = new HandoverVO();
		handover.setHandOverdate_time(new LocalDate("SYD", ARP, true));
		mailUploadVO.setScannedDate(new LocalDate("SYD", ARP, true));
		Map<String, String> systemParameterMap= new HashMap();
		systemParameterMap.put("mailtracking.domesticmra.usps","PACODE");
		systemParameterMap.put("mailtracking.defaults.uspsinternationalpa","lpa");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn("mailservice").when(mailController).findMailServiceLevel(any(MailbagVO.class));
		doNothing().when(mailController).flagMailbagHistoryForDelivery(anyCollectionOf(MailbagVO.class));
		doNothing().when(resditController).flagDeliveredResditForMailbags(anyCollectionOf(MailbagVO.class), anyString());
		doNothing().when(mailController).flagMLDForMailOperations(anyCollectionOf(MailbagVO.class), anyString());
		mailUploadControllerSpy.processMRDPODWithoutFlightDetails(mailUploadVO,"NZAKLAAUSYDAAUX31600160910100",handover);
	}
	@Test
	public void testProcessMRDPODWithoutFlightDetailsMailSeqnumberIs0() throws SystemException {
		MailUploadVO mailUploadVO = new MailUploadVO();
		String mailTag ="NZAKLAAUSYDAAUX31600160910100";
		mailUploadVO.setMailTag(mailTag);
		HandoverVO handover = new HandoverVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(0);
		handover.setHandOverdate_time(new LocalDate("SYD", ARP, true));
		mailUploadVO.setScannedDate(new LocalDate("SYD", ARP, true));
		Map<String, String> systemParameterMap= new HashMap();
		systemParameterMap.put("mailtracking.domesticmra.usps","PACODE");
		systemParameterMap.put("mailtracking.defaults.uspsinternationalpa","lpa");
		doReturn(mailbagVO).when(dao).findMailbagDetails(anyString(), anyString());
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn("mailservice").when(mailController).findMailServiceLevel(any(MailbagVO.class));
		doNothing().when(mailController).flagMailbagHistoryForDelivery(anyCollectionOf(MailbagVO.class));
		doNothing().when(resditController).flagDeliveredResditForMailbags(anyCollectionOf(MailbagVO.class), anyString());
		doNothing().when(mailController).flagMLDForMailOperations(anyCollectionOf(MailbagVO.class), anyString());
		mailUploadControllerSpy.processMRDPODWithoutFlightDetails(mailUploadVO,"NZAKLAAUSYDAAUX31600160910100",handover);
	}
	@Test
	public void testProcessMRDPODWithoutFlightDetailsWithExistingMailbag() throws SystemException, FinderException, PersistenceException {
		MailUploadVO mailUploadVO = new MailUploadVO();
		String mailTag ="NZAKLAAUSYDAAUX31600160910100";
		mailUploadVO.setMailTag(mailTag);
		HandoverVO handover = new HandoverVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(12344);
		 mailBag = new Mailbag();
		handover.setHandOverdate_time(new LocalDate("SYD", ARP, true));
		mailUploadVO.setScannedDate(new LocalDate("SYD", ARP, true));
		Map<String, String> systemParameterMap= new HashMap();
		systemParameterMap.put("mailtracking.domesticmra.usps","PACODE");
		systemParameterMap.put("mailtracking.defaults.uspsinternationalpa","lpa");
		systemParameterMap.put("mailtracking.defaults.enableuspsresditenhancements", "Y");
		doReturn(mailbagVO).when(dao).findMailbagDetails(anyString(), anyString());
		Mailbag mailbag = new Mailbag();
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(12344);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn("mailservice").when(mailController).findMailServiceLevel(any(MailbagVO.class));
		doNothing().when(mailController).flagMailbagHistoryForDelivery(anyCollectionOf(MailbagVO.class));
		doNothing().when(resditController).flagDeliveredResditForMailbags(anyCollectionOf(MailbagVO.class), anyString());
		doNothing().when(mailController).flagMLDForMailOperations(anyCollectionOf(MailbagVO.class), anyString());	
		mailUploadControllerSpy.processMRDPODWithoutFlightDetails(mailUploadVO,"NZAKLAAUSYDAAUX31600160910100",handover);
	}
	@Test
	public void testProcessMRDPODWithoutFlightDetailsAlreadyDelivered() throws SystemException, FinderException, PersistenceException {
		MailUploadVO mailUploadVO = new MailUploadVO();
		String mailTag ="NZAKLAAUSYDAAUX31600160910100";
		mailUploadVO.setMailTag(mailTag);
		HandoverVO handover = new HandoverVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(12344);
		 mailBag = new Mailbag();
		handover.setHandOverdate_time(new LocalDate("SYD", ARP, true));
		mailUploadVO.setScannedDate(new LocalDate("SYD", ARP, true));
		Map<String, String> systemParameterMap= new HashMap();
		systemParameterMap.put("mailtracking.domesticmra.usps","PACODE");
		systemParameterMap.put("mailtracking.defaults.uspsinternationalpa","lpa");
		systemParameterMap.put("mailtracking.defaults.enableuspsresditenhancements", "Y");
		doReturn(mailbagVO).when(dao).findMailbagDetails(anyString(), anyString());
		Mailbag mailbag = new Mailbag();
		mailbag.setLatestStatus("DLV");
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(12344);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn("mailservice").when(mailController).findMailServiceLevel(any(MailbagVO.class));
		doNothing().when(mailController).flagMailbagHistoryForDelivery(anyCollectionOf(MailbagVO.class));
		doNothing().when(resditController).flagDeliveredResditForMailbags(anyCollectionOf(MailbagVO.class), anyString());
		doNothing().when(mailController).flagMLDForMailOperations(anyCollectionOf(MailbagVO.class), anyString());	
		mailUploadControllerSpy.processMRDPODWithoutFlightDetails(mailUploadVO,"NZAKLAAUSYDAAUX31600160910100",handover);
	}
	@Test
	public void testProcessMRDPODWithoutFlightDetailsThrowsFinderException() throws SystemException, FinderException, PersistenceException {
		MailUploadVO mailUploadVO = new MailUploadVO();
		String mailTag ="NZAKLAAUSYDAAUX31600160910100";
		mailUploadVO.setMailTag(mailTag);
		HandoverVO handover = new HandoverVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(12344);
		 mailBag = new Mailbag();
		handover.setHandOverdate_time(new LocalDate("SYD", ARP, true));
		mailUploadVO.setScannedDate(new LocalDate("SYD", ARP, true));
		Map<String, String> systemParameterMap= new HashMap();
		systemParameterMap.put("mailtracking.domesticmra.usps","PACODE");
		systemParameterMap.put("mailtracking.defaults.uspsinternationalpa","lpa");
		systemParameterMap.put("mailtracking.defaults.enableuspsresditenhancements", "Y");
		doReturn(mailbagVO).when(dao).findMailbagDetails(anyString(), anyString());
		Mailbag mailbag = new Mailbag();
		mailbag.setLatestStatus("DLV");
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(12344);
		mailbag.setMailbagPK(mailbagPK);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn("mailservice").when(mailController).findMailServiceLevel(any(MailbagVO.class));
		doNothing().when(mailController).flagMailbagHistoryForDelivery(anyCollectionOf(MailbagVO.class));
		doNothing().when(resditController).flagDeliveredResditForMailbags(anyCollectionOf(MailbagVO.class), anyString());
		doNothing().when(mailController).flagMLDForMailOperations(anyCollectionOf(MailbagVO.class), anyString());	
		mailUploadControllerSpy.processMRDPODWithoutFlightDetails(mailUploadVO,"NZAKLAAUSYDAAUX31600160910100",handover);
	}
	@Test
	public void testProcessMRDPODWithoutFlightDetailsThrowsMLDException() throws SystemException, FinderException, PersistenceException {
		MailUploadVO mailUploadVO = new MailUploadVO();
		String mailTag ="NZAKLAAUSYDAAUX31600160910100";
		mailUploadVO.setMailTag(mailTag);
		HandoverVO handover = new HandoverVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(12344);
		 mailBag = new Mailbag();
		handover.setHandOverdate_time(new LocalDate("SYD", ARP, true));
		mailUploadVO.setScannedDate(new LocalDate("SYD", ARP, true));
		mailUploadVO.setMailSequenceNumber(12344);
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setOrginOE("567");
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(12344);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		mailUploadControllerSpy.processMRDPODWithoutFlightDetails(mailUploadVO,"NZAKLAAUSYDAAUX31600160910100",handover);
	}
	@Test
	public void testConstructMailUploadVOWithoutFlightInHandover(){
		mailUploadControllerSpy.constructMailUploadVOWithoutFlightInHandover(new HandoverVO(), new MailUploadVO());
	}
	@Test
	public void testIsMailbagNotPresentAtInbound() throws RemoteException, SystemException, PersistenceException{
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doReturn(new MailbagVO()).when(dao).findMailbagDetailsForMailInboundHHT(any(MailbagEnquiryFilterVO.class));
		assertFalse(mailUploadControllerSpy.isMailbagNotPresentAtInbound("NZAKLAAUSYDAAUX31600160910100","IBS","SYD"));
	}
	@Test
	public void testIsMailbagNotPresentAtInboundThrowsException() throws RemoteException, SystemException, PersistenceException {
			doThrow(RemoteException.class).when(dao).findMailbagDetailsForMailInboundHHT(any(MailbagEnquiryFilterVO.class));
			assertTrue(mailUploadControllerSpy.isMailbagNotPresentAtInbound("NZAKLAAUSYDAAUX31600160910100","IBS","SYD"));
		}
	@Test
	public void testHandleMessageForMRDPODWithoutFlightSuccess() throws PersistenceException, SystemException, MailTrackingBusinessException, ForceAcceptanceException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setCompanyCode("IBS");
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		List<String> mailIds =  new ArrayList<>();
		mailIds.add("NZAKLAAUSYDAAUX31600160910100");
		handover.setMailId(mailIds);
		doReturn(null).when(dao).findMailbagDetailsForMailInboundHHT(any(MailbagEnquiryFilterVO.class));
		mailUploadControllerSpy.handleMessageForMRDPOD(mailMRDMessageVO, fltVos,handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	@Test
	public void testHandleMessageForMRDPODWithoutFlightMailAvailableAtInbound() throws PersistenceException, SystemException, MailTrackingBusinessException, ForceAcceptanceException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setCompanyCode("IBS");
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		List<String> mailIds =  new ArrayList<>();
		mailIds.add("NZAKLAAUSYDAAUX31600160910100");
		handover.setMailId(mailIds);
		doReturn(new MailbagVO()).when(dao).findMailbagDetailsForMailInboundHHT(any(MailbagEnquiryFilterVO.class));
		mailUploadControllerSpy.handleMessageForMRDPOD(mailMRDMessageVO, fltVos,handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	
	@Test
	public void testHandleMessageForMRDPODWithoutFlight() throws PersistenceException, SystemException, MailTrackingBusinessException, ForceAcceptanceException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setCompanyCode("IBS");
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		handover.setFlightNumber("0000");
		List<String> mailIds =  new ArrayList<>();
		mailIds.add("NZAKLAAUSYDAAUX31600160910100");
		handover.setMailId(mailIds);
		doReturn(new MailbagVO()).when(dao).findMailbagDetailsForMailInboundHHT(any(MailbagEnquiryFilterVO.class));
		mailUploadControllerSpy.handleMessageForMRDPOD(mailMRDMessageVO, fltVos,handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	
	@Test
	public void testConstructAndValidateMailBags() throws SystemException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setCompanyCode("IBS");
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		handover.setHandOverCarrierCode("12");
		handover.setDestAirport("SYD");
		handover.setDstExgOffice("AUSYDA");
		doReturn(new AirportValidationVO()).when(sharedAreaProxy).validateAirportCode(anyString(),anyString());
		mailUploadControllerSpy.constructAndValidateMailBags(mailMRDMessageVO, handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	
	@Test
	public void testConstructAndValidateMailBagsInvalidAirport() throws SystemException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setCompanyCode("IBS");
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		handover.setHandOverCarrierCode("12");
		doReturn(new AirportValidationVO()).when(sharedAreaProxy).validateAirportCode(anyString(),anyString());
		mailUploadControllerSpy.constructAndValidateMailBags(mailMRDMessageVO, handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	
	@Test
	public void testConstructAndValidateMailBagsInvalidAirportWithFlightNumber() throws SystemException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setCompanyCode("IBS");
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		handover.setHandOverCarrierCode("12");
		handover.setFlightNumber("0000");
		doReturn(new AirportValidationVO()).when(sharedAreaProxy).validateAirportCode(anyString(),anyString());
		mailUploadControllerSpy.constructAndValidateMailBags(mailMRDMessageVO, handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	
	@Test
	public void testHandleMessageForMRDPODWithErrors() throws PersistenceException, SystemException, MailTrackingBusinessException, ForceAcceptanceException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setHandoverErrors(new ArrayList<>());
		mailMRDMessageVO.setCompanyCode("IBS");
		Collection<ErrorVO> errorVOS = new ArrayList<>();
		ErrorVO error = new ErrorVO("error");
		errorVOS.add(error);
		mailMRDMessageVO.setHandoverErrors(errorVOS);
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		List<String> mailIds =  new ArrayList<>();
		mailIds.add("NZAKLAAUSYDAAUX31600160910100");
		handover.setMailId(mailIds);
		doReturn(null).when(dao).findMailbagDetailsForMailInboundHHT(any(MailbagEnquiryFilterVO.class));
		mailUploadControllerSpy.handleMessageForMRDPOD(mailMRDMessageVO, fltVos,handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	
	@Test
	public void testHandleMessageForMRDPODWithInvalidMailbag() throws PersistenceException, SystemException, MailTrackingBusinessException, ForceAcceptanceException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setHandoverErrors(new ArrayList<>());
		mailMRDMessageVO.setCompanyCode("IBS");
		HashMap<String,String> mailbags = new HashMap<>();
		mailbags.put("valid_mailbag","valid_mailbag");
		mailMRDMessageVO.setMailBags(mailbags);
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		List<String> mailIds =  new ArrayList<>();
		mailIds.add("NZAKLAAUSYDAAUX31600160910100");
		handover.setMailId(mailIds);
		doReturn(null).when(dao).findMailbagDetailsForMailInboundHHT(any(MailbagEnquiryFilterVO.class));
		mailUploadControllerSpy.handleMessageForMRDPOD(mailMRDMessageVO, fltVos,handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	
	@Test
	public void testConstructAndValidateMailBagsInvalidAirportWithFlightNumberNotZero() throws SystemException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setCompanyCode("IBS");
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		handover.setHandOverCarrierCode("12");
		handover.setCarrierCode("QF");
		handover.setFlightNumber("2456");
		handover.setDestination("SYD");
		handover.setOrigin("AKL");
		doReturn(new AirportValidationVO()).when(sharedAreaProxy).validateAirportCode(anyString(),anyString());
		mailUploadControllerSpy.constructAndValidateMailBags(mailMRDMessageVO, handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	
	@Test
	public void testConstructAndValidateValidDoe() throws SystemException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setCompanyCode("IBS");
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		handover.setHandOverCarrierCode("12");
		handover.setDestAirport("SYD");
		handover.setDstExgOffice("AUSYDA");
		handover.setHandOverType("POD");
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("SYD");
    	officeOfExchangeVO.setActive(true);
    	officeOfExhange.add(officeOfExchangeVO);
		doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange("IBS","AUSYDA", 1);
		doReturn(new AirportValidationVO()).when(sharedAreaProxy).validateAirportCode(anyString(),anyString());
		mailUploadControllerSpy.constructAndValidateMailBags(mailMRDMessageVO, handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	
	@Test
	public void testConstructAndValidateValidCarrierCode() throws SystemException, SharedProxyException{
		MailMRDVO mailMRDMessageVO = new MailMRDVO();
		mailMRDMessageVO.setCompanyCode("IBS");
		Collection<OperationalFlightVO> fltVos =  null;
		HandoverVO handover = new HandoverVO();
		handover.setHandOverCarrierCode("IBS");
		handover.setDestAirport("SYD");
		handover.setDstExgOffice("AUSYDA");
		handover.setHandOverType("POD");
    	Page<OfficeOfExchangeVO> officeOfExhange = new Page<>();
    	OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    	officeOfExchangeVO.setPoaCode("DE101");
    	officeOfExchangeVO.setAirportCode("SYD");
    	officeOfExchangeVO.setActive(true);
    	officeOfExhange.add(officeOfExchangeVO);
		doReturn(officeOfExhange).when(officeOfExchangeCache).findOfficeOfExchange("IBS","AUSYDA", 1);
		doReturn(new AirlineValidationVO()).when(sharedAirlineProxy).validateAlphaCode(anyString(),anyString());
		mailUploadControllerSpy.constructAndValidateMailBags(mailMRDMessageVO, handover,"NZAKLAAUSYDAAUX31600160910100");
	}
	@Test
	public void checkTbaFlightsRequiredTest() throws SystemException{
    	Map<String, String> mailIDFormatMap= null;
		doReturn(mailIDFormatMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailUploadControllerSpy.checkTbaFlightsRequired();
	}
	@Test
	public void checkTbaFlightsRequiredTestWithException() throws SystemException{
    	Map<String, String> mailIDFormatMap= null;
    	SystemException systemException = new SystemException("system exception");
		doThrow(systemException).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailUploadControllerSpy.checkTbaFlightsRequired();
	}
	@Test
	public void createFlightFilterVOForMRDTest() throws SystemException{
    	MailMRDVO mailMRDMessageVO=new MailMRDVO();
    	HandoverVO handoverVO=new HandoverVO();
    	int carierId=183; 
    	LocalDate handoverDate=new LocalDate(LocalDate.NO_STATION,
 				Location.NONE, true);
    	handoverVO.setHandOverdate_time(handoverDate);
    	Map<String, String> systemParameterMap= new HashMap();
		systemParameterMap.put("mail.operations.noofdaysformrdflightstoconsider", "5");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailUploadControllerSpy.createFlightFilterVOForMRD(mailMRDMessageVO,handoverVO,carierId);
	}
	@Test
	public void createFlightFilterVOForMRDTestWithSysParNull() throws SystemException{
    	MailMRDVO mailMRDMessageVO=new MailMRDVO();
    	HandoverVO handoverVO=new HandoverVO();
    	int carierId=183; 
    	LocalDate handoverDate=new LocalDate(LocalDate.NO_STATION,
 				Location.NONE, true);
    	handoverVO.setHandOverdate_time(handoverDate);
		doReturn(null).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailUploadControllerSpy.createFlightFilterVOForMRD(mailMRDMessageVO,handoverVO,carierId);
	}
	@Test
	public void createFlightFilterVOForMRDTestWithHandoverTimeNull() throws SystemException{
    	MailMRDVO mailMRDMessageVO=new MailMRDVO();
    	HandoverVO handoverVO=new HandoverVO();
    	int carierId=183; 
    	Map<String, String> systemParameterMap= new HashMap();
		systemParameterMap.put("mail.operations.noofdaysformrdflightstoconsider", "5");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailUploadControllerSpy.createFlightFilterVOForMRD(mailMRDMessageVO,handoverVO,carierId);
	}
	@Test
	public void createFlightFilterVOForMRDTestWithFlightDate() throws SystemException{
    	MailMRDVO mailMRDMessageVO=new MailMRDVO();
    	HandoverVO handoverVO=new HandoverVO();
    	int carierId=183; 
    	Map<String, String> systemParameterMap= new HashMap();
    	handoverVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
 				Location.NONE, true));
		systemParameterMap.put("mail.operations.noofdaysformrdflightstoconsider", "5");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any(ArrayList.class));
		mailUploadControllerSpy.createFlightFilterVOForMRD(mailMRDMessageVO,handoverVO,carierId);
	}
	@Test
	public void validateContainerMailWeightCapture() throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, SystemException, PersistenceException
	{
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		MailUploadVO mailUploadVO = new MailUploadVO();
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		mailUploadVO.setCompanyCode("QF");
		mailUploadVO.setContainerType("B");
		mailUploadVO.setContainerNumber("BULK123CHE");
		mailUploadVO.setScannedPort("SYD");
		containerAssignmentVO.setCompanyCode("QF");
		containerAssignmentVO.setCarrierCode("QF");
		containerAssignmentVO.setCarrierId(1081);
		containerAssignmentVO.setFlightNumber("1100");
		containerAssignmentVO.setFlightSequenceNumber(1);
		containerAssignmentVO.setAirportCode("SYD");
		containerAssignmentVO.setFlightDate(currentDate);
		containerAssignmentVO.setDestination("DXB");
		containerAssignmentVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,434));
		containerAssignmentVO.setNetWeight(new Measure(UnitConstants.MAIL_WGT,10.1));
		containerAssignmentVO.setContainerType(mailUploadVO.getContainerType());
		containerAssignmentVO.setUnit("K"); 
		containerAssignmentVO.setWeightStatus("FIN");
		ContainerVO containerVo = new ContainerVO();
		containerVo.setCompanyCode(mailUploadVO.getCompanyCode());
		containerVo.setContainerNumber(mailUploadVO
				.getContainerNumber());
		containerVo.setAssignedPort(mailUploadVO.getScannedPort());
		doReturn(containerAssignmentVO).when(mailController).findContainerWeightCapture(any(ContainerVO.class));
		doReturn(containerAssignmentVO).when(dao).findContainerWeightCapture(any(String.class), any(String.class));
		doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
		mailUploadControllerSpy.validateContainerMailWeightCapture(mailUploadVO);
	}
	@Test
	public void validateContainerMailWeightCapture_nodata() throws SharedProxyException, SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException
	{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode("QF");
		mailUploadVO.setContainerType("U");
		mailUploadVO.setContainerNumber("AKE12345QF");
		doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
		mailUploadControllerSpy.validateContainerMailWeightCapture(mailUploadVO);
	}
	
	@Test
	public void validateContainerMailWeightCapture_ifcase() throws SharedProxyException, SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException
	{
    	mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");

		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode("QF");
		mailUploadVO.setContainerType("U");
		mailUploadVO.setContainerNumber("AKE12r45QF");
		doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
		mailUploadControllerSpy.validateContainerMailWeightCapture(mailUploadVO);
	}
	@Test(expected=SystemException.class)
	public void validateContainerMailWeightCapture_conatiner() throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException, SystemException, PersistenceException
	{
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		MailUploadVO mailUploadVO = new MailUploadVO();
		ContainerAssignmentVO containerAssignmentVO =null;
		mailUploadVO.setCompanyCode("QF");
		mailUploadVO.setContainerType("U");
		mailUploadVO.setContainerNumber("");
		doReturn(containerAssignmentVO).when(dao).findContainerWeightCapture(any(String.class),any(String.class));
		doThrow(PersistenceException.class).when(dao).findContainerWeightCapture(any(String.class), any(String.class));
		doNothing().when(mailUploadControllerSpy).constructAndRaiseException(any(String.class), any(String.class), any(ScannedMailDetailsVO.class));
		mailUploadControllerSpy.validateContainerMailWeightCapture(mailUploadVO);
	}

}

