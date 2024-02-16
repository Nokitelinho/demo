package com.ibsplc.icargo.business.mail.operations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationCache;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeature;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.config.vo.PrintConfigVO;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.util.ReportUtilInstance;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class DocumentControllerTest extends AbstractFeatureTest {

	private static final String MAILBAG_ID = "DEFRAAUSDFWAACA01200120001200";
	private static final String COMPANY_CODE = "IBS";
	private DocumentController spy;
	SaveConsignmentUploadFeature saveConsignmentUploadFeature;
	private MailController mailControllerBean;
	private PostalAdministrationCache postalAdministrationCache;
	private static final String PAWPARCODE="PAWBASSCONENAB";
	private static final String INVINFO="INVINFO";	  
	private MailTrackingDefaultsDAO dao;
	private static final String MAIL_OPERATIONS = "mail.operations";
	private DocumentController documentControllerMock;
	private SharedAirlineProxy sharedAirlineProxy;
	private MailOperationsProxy mailOperationsProxy;


	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		spy = spy(new DocumentController());
		saveConsignmentUploadFeature = mockBean(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FEATURE,
				SaveConsignmentUploadFeature.class);
		mailControllerBean = mockBean("mAilcontroller", MailController.class);
		 postalAdministrationCache = spy(PostalAdministrationCache.class);
	    doReturn(postalAdministrationCache).when(CacheFactory.getInstance()).getCache(PostalAdministrationCache.ENTITY_NAME);
		dao = mock(MailTrackingDefaultsDAO.class);
		documentControllerMock = mock(DocumentController.class);
		sharedAirlineProxy=mockProxy(SharedAirlineProxy.class);
		mailOperationsProxy = mockProxy(MailOperationsProxy.class);
	}

	private ConsignmentDocumentVO setUpConsignmentDocumentVO() {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode(COMPANY_CODE);
		consignmentDocumentVO.setConsignmentNumber("DEUS123456");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setType("CN38");
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setOperation("OUTBOUND");
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		mailInConsignmentVO.setMailId(MAILBAG_ID);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(new Page<MailInConsignmentVO>(
				(ArrayList<MailInConsignmentVO>) mailInConsignmentVOs, 0, 0, 0, 0, 0, false));
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setOnwardFlightNumber("0123");
		routingInConsignmentVO.setOnwardCarrierCode(COMPANY_CODE);
		routingInConsignmentVO.setPol("FRA");
		routingInConsignmentVO.setPou("DFW");
		routingInConsignmentVO.setCompanyCode(COMPANY_CODE);
		routingInConsignmentVO.setConsignmentNumber("DEUS123456");
		routingInConsignmentVOs.add(routingInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);
		return consignmentDocumentVO;
	}

	@Test
	public void verifySaveConsignmentUploadFeature_IsInvoked() throws SystemException, BusinessException {
		ConsignmentDocumentVO consignmentDocumentVO = setUpConsignmentDocumentVO();
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		spy.saveUploadedConsignmentData(consignmentDocumentVOs);
		verify(saveConsignmentUploadFeature, times(1)).execute(any(ConsignmentDocumentVO.class));
	}

	@Test(expected = MailTrackingBusinessException.class)
	public void shouldThrowBusinessExceptionSaveConsignmentUploadFeature_Fails()
			throws SystemException, BusinessException {
		ConsignmentDocumentVO consignmentDocumentVO = setUpConsignmentDocumentVO();
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		BusinessException be = new BusinessException() {
		};
		doThrow(be).when(saveConsignmentUploadFeature).execute(any());
		spy.saveUploadedConsignmentData(consignmentDocumentVOs);
	}
	@Test
	public void saveConsignmentDocument() throws SystemException, MailbagAlreadyAcceptedException, InvalidMailTagFormatException, DuplicateDSNException, DuplicateMailBagsException {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doReturn("Y").when(mailControllerBean).findSystemParameterValue(any(String.class));
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		PostalAdministrationDetailsVO paDet = new PostalAdministrationDetailsVO();
		Collection<PostalAdministrationDetailsVO> paDetails = new ArrayList<>();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs = new HashMap<>();
		paDet.setParCode(PAWPARCODE);
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put(INVINFO, paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		postalAdministrationVO.setPaName("TEST");
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode("IBS",
				"ER");
		spy.saveConsignmentDocument(consignmentDocumentVO);

	}
	@Test
	public void saveConsignmentDocument_ThrowSystemException() throws SystemException, MailbagAlreadyAcceptedException, InvalidMailTagFormatException, DuplicateDSNException, DuplicateMailBagsException {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doThrow(SystemException.class).when(mailControllerBean).findSystemParameterValue(any(String.class));
		spy.saveConsignmentDocument(consignmentDocumentVO);

	}
	@Test
	public void saveConsignmentDocument_ThrowException() throws SystemException, MailbagAlreadyAcceptedException, InvalidMailTagFormatException, DuplicateDSNException, DuplicateMailBagsException {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentSequenceNumber(1234);
		consignmentDocumentVO.setConsignmentNumber("CDG4367");
		consignmentDocumentVO.setPaCode("DE101");
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		doThrow(Exception.class).when(mailControllerBean).findSystemParameterValue(any(String.class));
		spy.saveConsignmentDocument(consignmentDocumentVO);
	}
	@Test
	public void generateCN46ConsignmentReport()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(true);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setCompanyCode("IBS");
		routingInConsignmentVO.setOnwardFlightNumber("IBS2343");
		routingInConsignmentVO.setOnwardCarrierCode("IBS");
		routingInConsignmentVO.setOnwardCarrierId(1134);
		routingInConsignmentVO.setPou("DXB");
		routingInConsignmentVO.setPol("CDG");
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingConsignmentVOs.add(routingInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingConsignmentVOs);
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setDsn("2345");
		mailInConsignmentVO.setOriginExchangeOffice("DEFRAA");
		mailInConsignmentVO.setDestinationExchangeOffice("SGSINA");
		mailInConsignmentVO.setMailClass("");
		mailInConsignmentVO.setYear(2);
		mailInConsignmentVO.setMailSequenceNumber(1520);
		mailInConsignmentVO.setMailId("DEFRABAEDXBAA1A21241312011351");
		mailInConsignmentVO.setMailCategoryCode("A");
		mailInConsignmentVO.setMailSubclass("EMS");
		mailInConsignmentVO.setHighestNumberedReceptacle("1");
		mailInConsignmentVO.setReceptacleSerialNumber("234");
		mailInConsignmentVO.setStatedWeight(new Measure(null, 4));
		mailInConsignmentVO.setUldNumber("AKE34512AV");
		mailInConsignmentVO.setStatedBags(2);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setAirlineCode("AV");
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		byte[] reportData = new byte[] { (byte) 0xe0, 0x4f, (byte) 0xd0, 0x20, (byte) 0xea, 0x3a, 0x69, 0x10,
				(byte) 0xa2, (byte) 0xd8, 0x08, 0x00, 0x2b, 0x30, 0x30, (byte) 0x9d };
		List<Map<String, Object>> reportMapList = new LinkedList<>();
		Map<String, Object> reportMap = new HashMap<>();
		reportMap.put("print-mode", "N");
		reportMap.put("PRINT_CONFIG", new PrintConfigVO());
		reportMap.put("DATA", reportData);
		reportMapList.add(reportMap);
		ReportUtilInstance reportUtilInstance = ReportUtilInstance.getIstance();
		doReturn(reportMapList).when(reportUtilInstance).exportMultipleReports(any());
		doReturn(reportData).when(reportUtilInstance).mergePDFbyteArray(any());
		spy.generateCN46ConsignmentReport(reportSpec);
		
	}
	@Test
	public void generateCN46ConsignmentReportThrowException()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<ConsignmentFilterVO>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(false);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setCompanyCode("IBS");
		routingInConsignmentVO.setOnwardFlightNumber("IBS2343");
		routingInConsignmentVO.setOnwardCarrierCode("IBS");
		routingInConsignmentVO.setOnwardCarrierId(1134);
		routingInConsignmentVO.setPou("DXB");
		routingInConsignmentVO.setPol("CDG");
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingConsignmentVOs.add(routingInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingConsignmentVOs);
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setDsn("2345");
		mailInConsignmentVO.setOriginExchangeOffice("DEFRAA");
		mailInConsignmentVO.setDestinationExchangeOffice("SGSINA");
		mailInConsignmentVO.setMailClass("");
		mailInConsignmentVO.setYear(2);
		mailInConsignmentVO.setMailSequenceNumber(1520);
		mailInConsignmentVO.setMailId("DEFRABAEDXBAA1A21241312011351");
		mailInConsignmentVO.setMailCategoryCode("A");
		mailInConsignmentVO.setMailSubclass("EMS");
		mailInConsignmentVO.setHighestNumberedReceptacle("1");
		mailInConsignmentVO.setReceptacleSerialNumber("");
		mailInConsignmentVO.setUldNumber("AKE34512AV");
		mailInConsignmentVO.setStatedBags(2);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setAirlineCode("AV");
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		doThrow(SharedProxyException.class).when(sharedAirlineProxy).validateAlphaCode(any(String.class),
				any(String.class));
		byte[] reportData = new byte[] { (byte) 0xe0, 0x4f, (byte) 0xd0, 0x20, (byte) 0xea, 0x3a, 0x69, 0x10,
				(byte) 0xa2, (byte) 0xd8, 0x08, 0x00, 0x2b, 0x30, 0x30, (byte) 0x9d };
		List<Map<String, Object>> reportMapList = new LinkedList<>();
		Map<String, Object> reportMap = new HashMap<>();
		reportMap.put("print-mode", "N");
		reportMap.put("PRINT_CONFIG", new PrintConfigVO());
		reportMap.put("DATA", reportData);
		reportMapList.add(reportMap);
		ReportUtilInstance reportUtilInstance = ReportUtilInstance.getIstance();
		doReturn(reportMapList).when(reportUtilInstance).exportMultipleReports(any());
		doReturn(reportData).when(reportUtilInstance).mergePDFbyteArray(any());
		spy.generateCN46ConsignmentReport(reportSpec);
	}
	@Test
	public void generateCN46ConsignmentReportPresistanceThrowException()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<ConsignmentFilterVO>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(false);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		PersistenceException persistenceException = new PersistenceException("");
		doThrow(persistenceException).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setCompanyCode("IBS");
		routingInConsignmentVO.setOnwardFlightNumber("IBS2343");
		routingInConsignmentVO.setOnwardCarrierCode("IBS");
		routingInConsignmentVO.setOnwardCarrierId(1134);
		routingInConsignmentVO.setPou("DXB");
		routingInConsignmentVO.setPol("CDG");
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingConsignmentVOs.add(routingInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingConsignmentVOs);
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setDsn("2345");
		mailInConsignmentVO.setOriginExchangeOffice("DEFRAA");
		mailInConsignmentVO.setDestinationExchangeOffice("SGSINA");
		mailInConsignmentVO.setMailClass("");
		mailInConsignmentVO.setYear(2);
		mailInConsignmentVO.setMailSequenceNumber(1520);
		mailInConsignmentVO.setMailId("DEFRABAEDXBAA1A21241312011351");
		mailInConsignmentVO.setMailCategoryCode("A");
		mailInConsignmentVO.setMailSubclass("EMS");
		mailInConsignmentVO.setHighestNumberedReceptacle("1");
		mailInConsignmentVO.setReceptacleSerialNumber("234");
		mailInConsignmentVO.setUldNumber("AKE34512AV");
		mailInConsignmentVO.setStatedBags(2);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setAirlineCode("AV");
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		doThrow(SharedProxyException.class).when(sharedAirlineProxy).validateAlphaCode(any(String.class),
				any(String.class));
		byte[] reportData = new byte[] { (byte) 0xe0, 0x4f, (byte) 0xd0, 0x20, (byte) 0xea, 0x3a, 0x69, 0x10,
				(byte) 0xa2, (byte) 0xd8, 0x08, 0x00, 0x2b, 0x30, 0x30, (byte) 0x9d };
		List<Map<String, Object>> reportMapList = new LinkedList<>();
		Map<String, Object> reportMap = new HashMap<>();
		reportMap.put("print-mode", "N");
		reportMap.put("PRINT_CONFIG", new PrintConfigVO());
		reportMap.put("DATA", reportData);
		reportMapList.add(reportMap);
		ReportUtilInstance reportUtilInstance = ReportUtilInstance.getIstance();
		doReturn(reportMapList).when(reportUtilInstance).exportMultipleReports(any());
		doReturn(reportData).when(reportUtilInstance).mergePDFbyteArray(any());
		spy.generateCN46ConsignmentReport(reportSpec);
	}
	@Test
	public void generateCN46ConsignmentReport_WithEmptyRoutingVO()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(false);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingConsignmentVOs = new ArrayList<>();
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingConsignmentVOs);
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setAirlineCode("AV");
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		spy.generateCN46ConsignmentReport(reportSpec);
	}
	@Test
	public void generateCN46ConsignmentReport_WithNoRoutingVO()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(false);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setRoutingInConsignmentVOs(null);
		consignmentDocumentVO.setMailInConsignmentcollVOs(null);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		spy.generateCN46ConsignmentReport(reportSpec);
	}
	@Test
	public void generateCN46ConsignmentSummaryReport()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(true);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setCompanyCode("IBS");
		routingInConsignmentVO.setOnwardFlightNumber("IBS2343");
		routingInConsignmentVO.setOnwardCarrierCode("IBS");
		routingInConsignmentVO.setOnwardCarrierId(1134);
		routingInConsignmentVO.setPou("SIN");
		routingInConsignmentVO.setPol("CDG");
		routingInConsignmentVO.setPolAirportName("France Airport");
		routingInConsignmentVO.setPouAirportName("Singapore Airport");
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingConsignmentVOs.add(routingInConsignmentVO);
		RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
		routingInConsignmentVO1.setCompanyCode("IBS");
		routingInConsignmentVO1.setOnwardFlightNumber("IBS2345");
		routingInConsignmentVO1.setOnwardCarrierCode("IBS");
		routingInConsignmentVO1.setOnwardCarrierId(1134);
		routingInConsignmentVO1.setPou("DXB");
		routingInConsignmentVO1.setPol("SIN");
		routingInConsignmentVO1.setPolAirportName("Singapore Airport");
		routingInConsignmentVO1.setPouAirportName("Dubai");
		routingInConsignmentVO1.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingConsignmentVOs.add(routingInConsignmentVO1);
		RoutingInConsignmentVO routingInConsignmentVO2 = new RoutingInConsignmentVO();
		routingInConsignmentVO2.setCompanyCode("IBS");
		routingInConsignmentVO2.setOnwardFlightNumber("IBS2346");
		routingInConsignmentVO2.setOnwardCarrierCode("IBS");
		routingInConsignmentVO2.setOnwardCarrierId(1134);
		routingInConsignmentVO2.setPou("FRA");
		routingInConsignmentVO2.setPol("DXB");
		routingInConsignmentVO2.setPolAirportName("Dubai");
		routingInConsignmentVO2.setPouAirportName("Frankfurt");
		routingInConsignmentVO2.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingConsignmentVOs.add(routingInConsignmentVO2);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingConsignmentVOs);
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setDsn("2345");
		mailInConsignmentVO.setOriginExchangeOffice("DEFRAA");
		mailInConsignmentVO.setDestinationExchangeOffice("SGSINA");
		mailInConsignmentVO.setMailClass("");
		mailInConsignmentVO.setYear(2);
		mailInConsignmentVO.setMailSequenceNumber(1520);
		mailInConsignmentVO.setMailId("DEFRABAEDXBAA1A21241312011351");
		mailInConsignmentVO.setMailCategoryCode("A");
		mailInConsignmentVO.setMailSubClassGroup("EMS");
		mailInConsignmentVO.setHighestNumberedReceptacle("1");
		mailInConsignmentVO.setReceptacleSerialNumber("234");
		mailInConsignmentVO.setUldNumber("AKE34512AV");
		mailInConsignmentVO.setStatedBags(2);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		MailInConsignmentVO mailInConsignmentVO1 = new MailInConsignmentVO();
		mailInConsignmentVO1.setDsn("2345");
		mailInConsignmentVO1.setOriginExchangeOffice("DEFRAA");
		mailInConsignmentVO1.setDestinationExchangeOffice("SGSINA");
		mailInConsignmentVO1.setMailClass("");
		mailInConsignmentVO1.setYear(2);
		mailInConsignmentVO1.setMailSequenceNumber(1520);
		mailInConsignmentVO1.setMailId("DEFRABAEDXBAA1A21241312011351");
		mailInConsignmentVO1.setMailCategoryCode("A");
		mailInConsignmentVO1.setMailSubClassGroup("LC");
		mailInConsignmentVO1.setHighestNumberedReceptacle("1");
		mailInConsignmentVO1.setReceptacleSerialNumber("234");
		mailInConsignmentVO1.setUldNumber("AKE34512AV");
		mailInConsignmentVO1.setStatedBags(2);
		mailInConsignmentVOs.add(mailInConsignmentVO1);
		MailInConsignmentVO mailInConsignmentVO2 = new MailInConsignmentVO();
		mailInConsignmentVO2.setDsn("2345");
		mailInConsignmentVO2.setOriginExchangeOffice("DEFRAA");
		mailInConsignmentVO2.setDestinationExchangeOffice("SGSINA");
		mailInConsignmentVO2.setMailClass("");
		mailInConsignmentVO2.setYear(2);
		mailInConsignmentVO2.setMailSequenceNumber(1520);
		mailInConsignmentVO2.setMailId("DEFRABAEDXBAA1A21241312011351");
		mailInConsignmentVO2.setMailCategoryCode("A");
		mailInConsignmentVO2.setMailSubClassGroup("CP");
		mailInConsignmentVO2.setHighestNumberedReceptacle("1");
		mailInConsignmentVO2.setReceptacleSerialNumber("234");
		mailInConsignmentVO2.setUldNumber("AKE34512AV");
		mailInConsignmentVO2.setStatedBags(2);
		mailInConsignmentVOs.add(mailInConsignmentVO2);
		consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setAirlineCode("AV");
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentSummaryReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		byte[] reportData = new byte[] { (byte) 0xe0, 0x4f, (byte) 0xd0, 0x20, (byte) 0xea, 0x3a, 0x69, 0x10,
				(byte) 0xa2, (byte) 0xd8, 0x08, 0x00, 0x2b, 0x30, 0x30, (byte) 0x9d };
		List<Map<String, Object>> reportMapList = new LinkedList<>();
		Map<String, Object> reportMap = new HashMap<>();
		reportMap.put("print-mode", "N");
		reportMap.put("PRINT_CONFIG", new PrintConfigVO());
		reportMap.put("DATA", reportData);
		reportMapList.add(reportMap);
		ReportUtilInstance reportUtilInstance = ReportUtilInstance.getIstance();
		doReturn(reportMapList).when(reportUtilInstance).exportMultipleReports(any());
		doReturn(reportData).when(reportUtilInstance).mergePDFbyteArray(any());
		spy.generateCN46ConsignmentSummaryReport(reportSpec);
	}
	@Test
	public void generateCN46ConsignmentSummaryReport_SVSubclass()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(false);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setCompanyCode("IBS");
		routingInConsignmentVO.setOnwardFlightNumber("IBS2343");
		routingInConsignmentVO.setOnwardCarrierCode("IBS");
		routingInConsignmentVO.setOnwardCarrierId(1134);
		routingInConsignmentVO.setPou("SIN");
		routingInConsignmentVO.setPol("CDG");
		routingInConsignmentVO.setPolAirportName("France Airport");
		routingInConsignmentVO.setPouAirportName("Singapore Airport");
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingConsignmentVOs.add(routingInConsignmentVO);
		RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
		routingInConsignmentVO1.setCompanyCode("IBS");
		routingInConsignmentVO1.setOnwardFlightNumber("IBS2345");
		routingInConsignmentVO1.setOnwardCarrierCode("IBS");
		routingInConsignmentVO1.setOnwardCarrierId(1134);
		routingInConsignmentVO1.setPou("DXB");
		routingInConsignmentVO1.setPol("SIN");
		routingInConsignmentVO1.setPolAirportName("Singapore Airport");
		routingInConsignmentVO1.setPouAirportName("Dubai");
		routingInConsignmentVO1
				.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false).addDays(1));
		routingConsignmentVOs.add(routingInConsignmentVO1);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingConsignmentVOs);
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setDsn("2345");
		mailInConsignmentVO.setOriginExchangeOffice("DEFRAA");
		mailInConsignmentVO.setDestinationExchangeOffice("SGSINA");
		mailInConsignmentVO.setMailClass("");
		mailInConsignmentVO.setYear(2);
		mailInConsignmentVO.setMailSequenceNumber(1520);
		mailInConsignmentVO.setMailId("DEFRABAEDXBAA1A21241312011351");
		mailInConsignmentVO.setMailCategoryCode("A");
		mailInConsignmentVO.setMailSubClassGroup("SV");
		mailInConsignmentVO.setHighestNumberedReceptacle("1");
		mailInConsignmentVO.setReceptacleSerialNumber("234");
		mailInConsignmentVO.setUldNumber("AKE34512AV");
		mailInConsignmentVO.setStatedBags(2);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setAirlineCode("AV");
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentSummaryReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		spy.generateCN46ConsignmentSummaryReport(reportSpec);
	}
	@Test
	public void generateCN46ConsignmentSummaryReport_EmptyRoutingVO()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(false);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingConsignmentVOs);
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setAirlineCode("AV");
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentSummaryReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		spy.generateCN46ConsignmentSummaryReport(reportSpec);
	}
	@Test
	public void generateCN46ConsignmentSummaryReport_WithNoRoutingVO()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(false);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setRoutingInConsignmentVOs(null);
		consignmentDocumentVO.setMailInConsignmentcollVOs(null);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setAirlineCode("AV");
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentSummaryReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		spy.generateCN46ConsignmentSummaryReport(reportSpec);
	}
	@Test
	public void generateCN46ConsignmentSummaryReport_WithSameFlight()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(false);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setCompanyCode("IBS");
		routingInConsignmentVO.setOnwardFlightNumber("IBS2343");
		routingInConsignmentVO.setOnwardCarrierCode("IBS");
		routingInConsignmentVO.setOnwardCarrierId(1134);
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingConsignmentVOs.add(routingInConsignmentVO);
		RoutingInConsignmentVO routingInConsignmentVO1 = new RoutingInConsignmentVO();
		routingInConsignmentVO1.setCompanyCode("IBS");
		routingInConsignmentVO1.setOnwardFlightNumber("IBS2343");
		routingInConsignmentVO1.setOnwardCarrierCode("IBS");
		routingInConsignmentVO1.setOnwardCarrierId(1134);
		routingInConsignmentVO1.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingConsignmentVOs.add(routingInConsignmentVO1);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingConsignmentVOs);
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setDsn("2345");
		mailInConsignmentVO.setOriginExchangeOffice("DEFRAA");
		mailInConsignmentVO.setDestinationExchangeOffice("SGSINA");
		mailInConsignmentVO.setMailClass("");
		mailInConsignmentVO.setYear(2);
		mailInConsignmentVO.setMailSequenceNumber(1520);
		mailInConsignmentVO.setMailId("DEFRABAEDXBAA1A21241312011351");
		mailInConsignmentVO.setMailCategoryCode("A");
		mailInConsignmentVO.setMailSubClassGroup("SV");
		mailInConsignmentVO.setHighestNumberedReceptacle("1");
		mailInConsignmentVO.setReceptacleSerialNumber("234");
		mailInConsignmentVO.setUldNumber("AKE34512AV");
		mailInConsignmentVO.setStatedBags(2);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setAirlineCode("AV");
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentSummaryReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		spy.generateCN46ConsignmentSummaryReport(reportSpec);
	}
	@Test
	public void generateCN46ConsignmentSummaryReport_WithNoPou()
			throws PersistenceException, SystemException, ReportGenerationException, SharedProxyException, ProxyException {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setAction("generateConsignmentSummaryReports");
		reportSpec.setReportId("RPRMTK304");
		reportSpec.setPreview(false);
		reportSpec.setProductCode("mail");
		reportSpec.setSubProductCode("operations");
		reportSpec.setResourceBundle("consignmentResources");
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode("IBS");
		consignmentFilterVO.setTransferManifestId("AVAA25");
		consignmentFilterVO.setConsignmentNumber(null);
		consignmentFilterVO.setPaCode("DE101");
		consignmentFilterVO.setBulkDownload(false);
		consignmentFilterVOs.add(consignmentFilterVO);
		reportSpec.addFilterValue(consignmentFilterVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setCompanyCode("IBS");
		routingInConsignmentVO.setOnwardFlightNumber("IBS2343");
		routingInConsignmentVO.setOnwardCarrierCode("IBS");
		routingInConsignmentVO.setOnwardCarrierId(1134);
		routingInConsignmentVO.setPol("CDG");
		routingInConsignmentVO.setPou("SIN");
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingConsignmentVOs.add(routingInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingConsignmentVOs);
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setDsn("2345");
		mailInConsignmentVO.setOriginExchangeOffice("DEFRAA");
		mailInConsignmentVO.setDestinationExchangeOffice("SGSINA");
		mailInConsignmentVO.setMailClass("");
		mailInConsignmentVO.setYear(2);
		mailInConsignmentVO.setMailSequenceNumber(1520);
		mailInConsignmentVO.setMailId("DEFRABAEDXBAA1A21241312011351");
		mailInConsignmentVO.setMailCategoryCode("A");
		mailInConsignmentVO.setMailSubClassGroup("SV");
		mailInConsignmentVO.setHighestNumberedReceptacle("1");
		mailInConsignmentVO.setReceptacleSerialNumber("234");
		mailInConsignmentVO.setUldNumber("AKE34512AV");
		mailInConsignmentVO.setStatedBags(2);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentcollVOs(mailInConsignmentVOs);
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setAirlineCode("AV");
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList();
		consignmentDocumentVOs.add(consignmentDocumentVO);
		doReturn(consignmentDocumentVOs).when(mailOperationsProxy).generateCN46ConsignmentSummaryReportDtls(consignmentFilterVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineName("American Airlines");
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		spy.generateCN46ConsignmentSummaryReport(reportSpec);
     }
	
	
}
