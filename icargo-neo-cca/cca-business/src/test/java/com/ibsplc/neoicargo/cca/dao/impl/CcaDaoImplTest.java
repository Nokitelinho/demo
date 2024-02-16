package com.ibsplc.neoicargo.cca.dao.impl;

import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.dao.mybatis.CcaQueryMapper;
import com.ibsplc.neoicargo.cca.dao.repository.CcaMasterRepository;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.modal.AttachmentsData;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneeData;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import com.ibsplc.neoicargo.cca.modal.viewfilter.DateRangeFilterData;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.framework.core.context.RequestContext;
import com.ibsplc.neoicargo.framework.core.context.tenant.TenantContext;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.security.spring.LoginProfileExtractor;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditContext;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.tx.AuditTxManager;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import com.ibsplc.neoicargo.testframework.dao.PostgresDaoTest;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.COMPANY_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getBasicMockCcaMaster;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaDataFilter;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaMaster;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.REJECTED_AS_PART_OF_VOIDING_CCA_REASON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "LH")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
class CcaDaoImplTest extends PostgresDaoTest {

    @Autowired
    private TenantContext tenantContext;

    @Mock
    private CcaQueryMapper ccaQueryMapper;

    @Mock
    private ContextUtil contextUtil;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private ObjectProvider<LoginProfileExtractor> loginProfileExtractor;

    @InjectMocks
    private CcaDaoImpl ccaDao;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ccaDao.ccaMasterRepository = tenantContext.getBean(CcaMasterRepository.class);
        ccaDao.ccaQueryMapper = tenantContext.getBean(CcaQueryMapper.class);

        doReturn(new LoginProfile()).when(contextUtil).callerLoginProfile();
        contextUtil = new ContextUtil(applicationContext, loginProfileExtractor);
        var requestContext = new RequestContext();
        var auditContext = new AuditContext();
        auditContext.setAuditObject(new AuditVO());
        var txMap = new HashMap<String, Object>();
        txMap.put(AuditTxManager.AUDIT_TASK_BUSINESS, auditContext);
        contextUtil.setTxContext(txMap);
        contextUtil.setContext(requestContext);
        when(applicationContext.getBean(ContextUtil.class)).thenReturn(contextUtil);
    }

    @Test
    void shouldGetCCADetails() {
        // Given
        final var ccaDataFilter = getCcaDataFilter(SHIPMENT_PREFIX, "12312311", null, COMPANY_CODE);

        // When
        doReturn(new CCAMasterVO()).when(ccaQueryMapper).getCCADetails(any(CcaDataFilter.class));

        // Then
        assertDoesNotThrow(() -> ccaDao.getCCADetails(ccaDataFilter));
    }

    @Test
    void shouldSaveCCA() {
        // Then
        assertDoesNotThrow(() -> ccaDao.saveCCA(getFullMockCcaMaster()));
    }

    @Test
    void shouldSaveAllCCAs() {
        // Then
        assertDoesNotThrow(() -> ccaDao.saveAllCCAs(List.of(getFullMockCcaMaster())));
    }

    @Test
    void shouldGetRelatedCCA() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("44440000", "CCA000001", LocalDate.of(2020, 12, 3));
        final var ccaDataFilter = getCcaDataFilter(SHIPMENT_PREFIX, "44440000", null, COMPANY_CODE);

        // When
        doReturn(List.of(ccaMasterVO)).when(ccaQueryMapper).getRelatedCCA(ccaDataFilter);

        // Then
        assertNotNull(ccaDao.getRelatedCCA(ccaDataFilter));
    }

    @Test
    void shouldFindCCA() {
        // Given
        final var ccaFilterVO = new CCAFilterVO(SHIPMENT_PREFIX, "14563214", "CCA000001");
        final var ccaMaster = getBasicMockCcaMaster("CCA000001", "14563214");

        // Then
        assertDoesNotThrow(() -> ccaDao.saveCCA(ccaMaster));
        assertNotNull(ccaDao.findCCAMaster(ccaFilterVO));
    }

    @Test
    void shouldFindCcaMastersByCcaSerialNumbers() {
        // Given
        var savedCcaMasters = Stream.of(
                getBasicMockCcaMaster("CCA000005", "14563215"),
                getBasicMockCcaMaster("CCA000006", "14563216")
        )
                .map(ccaDao.ccaMasterRepository::save)
                .collect(Collectors.toList());

        // Then
        var loadedCcaMasters = ccaDao.findCcaMastersByCcaSerialNumbers(
                savedCcaMasters.stream()
                        .map(CcaMaster::getCcaSerialNumber)
                        .collect(Collectors.toList())
        );
        assertEquals(2, loadedCcaMasters.size());
        assertEquals(
                Set.of("CCA000005", "CCA000006"),
                Set.copyOf(
                        loadedCcaMasters.stream()
                                .map(CcaMaster::getCcaReferenceNumber)
                                .collect(Collectors.toList())
                )
        );
    }

    @Test
    void shouldReturnNullIfCCANotFound() {
        // Then
        assertTrue(ccaDao.findCCAMaster(new CCAFilterVO(SHIPMENT_PREFIX, "14563642", "CCN000001")).isEmpty());
    }

    @Test
    void shouldGetCCAList() {
        // Given
        final var ccaMasterVO1 = getCCAMasterVO("44440000", "CCAA000001", LocalDate.of(2020, 12, 3));
        final var ccaMasterVO2 = getCCAMasterVO("44440001", "CCAA000001", LocalDate.of(2020, 12, 3));
        final var ccaDataFilter = getCcaDataFilter(null, null, "CCAA", null);

        // When
        doReturn(List.of(ccaMasterVO1, ccaMasterVO2)).when(ccaQueryMapper).getRelatedCCA(ccaDataFilter);

        // Then
        assertDoesNotThrow(() -> ccaDao.getCCAList(List.of(ccaDataFilter)));
    }

    @Test
    void shouldGetCCAListWithAwbNumber() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("14563214", "CCAA000001", LocalDate.of(2020, 12, 3));
        final var ccaDataFilter = getCcaDataFilter(SHIPMENT_PREFIX, "14563214", "CCAA", null);

        // When
        doReturn(List.of(ccaMasterVO)).when(ccaQueryMapper).getRelatedCCA(ccaDataFilter);

        // Then
        assertDoesNotThrow(() -> ccaDao.getCCAList(List.of(ccaDataFilter)));
    }

    @Test
    void shouldReturnSortedRelatedCCAInDscOrderByIssueDate() {
        // Given
        final var masterDocumentNumber = "79089371";
        final var ccaMasterFirst = getBasicMockCcaMaster("CCA000001", masterDocumentNumber);
        ccaMasterFirst.setCcaIssueDate(LocalDate.now().plusDays(2));
        ccaMasterFirst.setCcaIssueDateTimeInUTC(LocalDateTime.now().plusDays(2));

        final var ccaMasterSecond = getBasicMockCcaMaster("CCA000002", masterDocumentNumber);
        ccaMasterSecond.setCcaIssueDate(LocalDate.now().minusDays(1));
        ccaMasterSecond.setCcaIssueDateTimeInUTC(LocalDateTime.now().minusDays(1));

        final var ccaMasterThird = getBasicMockCcaMaster("CCA000003", masterDocumentNumber);
        ccaMasterThird.setCcaIssueDate(LocalDate.now());
        ccaMasterThird.setCcaIssueDateTimeInUTC(LocalDateTime.now());

        // When
        ccaDao.ccaMasterRepository.saveAll(List.of(ccaMasterFirst, ccaMasterSecond, ccaMasterThird));
        final var ccaDataFilter = getCcaDataFilter(SHIPMENT_PREFIX, masterDocumentNumber, null, COMPANY_CODE);
        //should be sorted by issue date asc
        final var relatedCCAList = ccaDao.getRelatedCCA(ccaDataFilter);

        // Then
        assertEquals(3, relatedCCAList.size());
        assertEquals("CCA000001", relatedCCAList.get(0).getCcaReferenceNumber());
        assertEquals("CCA000003", relatedCCAList.get(1).getCcaReferenceNumber());
        assertEquals("CCA000002", relatedCCAList.get(2).getCcaReferenceNumber());
    }

    @Test
    void shouldReturnSortedRelatedCCAInDscOrderByCCANumber() {
        // Given
        //We display the records in the descending order of CCA Issue Date.
        //Within the same date we sort records on the basis of CCA Number
        final var masterDocumentNumber = "79089372";
        final var ccaMasterFirst = getBasicMockCcaMaster("CCA000003", masterDocumentNumber);
        final var ccaMasterSecond = getBasicMockCcaMaster("CCA000002", masterDocumentNumber);
        final var ccaMasterThird = getBasicMockCcaMaster("CCA000001", masterDocumentNumber);
        LocalDateTime time = LocalDateTime.now();
        ccaMasterFirst.setCcaIssueDateTimeInUTC(time);
        ccaMasterSecond.setCcaIssueDateTimeInUTC(time);
        ccaMasterThird.setCcaIssueDateTimeInUTC(time);

        // When
        ccaDao.ccaMasterRepository.saveAll(List.of(ccaMasterFirst, ccaMasterSecond, ccaMasterThird));

        final var ccaDataFilter = getCcaDataFilter(SHIPMENT_PREFIX, masterDocumentNumber, null, COMPANY_CODE);
        //should be sorted by CCA Number dsc, because issue date is the same for all of them
        final var relatedCCAList = ccaDao.getRelatedCCA(ccaDataFilter);

        // Then
        assertEquals(3, relatedCCAList.size());
        assertEquals("CCA000003", relatedCCAList.get(0).getCcaReferenceNumber());
        assertEquals("CCA000002", relatedCCAList.get(1).getCcaReferenceNumber());
        assertEquals("CCA000001", relatedCCAList.get(2).getCcaReferenceNumber());
    }

    @Transactional
    @Test
    void shouldDeleteCCA() {
        // Given
        final var fullMockCcaMaster = getFullMockCcaMaster();
        fullMockCcaMaster.setCcaStatus(CcaStatus.N);
        fullMockCcaMaster.setCcaReferenceNumber("CCA00001");
        fullMockCcaMaster.setMasterDocumentNumber("77803840");

        final var fullMockCcaMaster1 = getFullMockCcaMaster();
        fullMockCcaMaster1.setCcaStatus(CcaStatus.N);
        fullMockCcaMaster1.setCcaReferenceNumber("CCA00002");
        fullMockCcaMaster.setMasterDocumentNumber("77803841");

        var ccaDataFilters = List.of(
                getCcaDataFilter(SHIPMENT_PREFIX, "77803840", "CCA00001", COMPANY_CODE),
                getCcaDataFilter(SHIPMENT_PREFIX, "77803841", "CCA00002", COMPANY_CODE)
        );

        // When
        var savedCcaMasters = ccaDao.ccaMasterRepository.saveAll(List.of(fullMockCcaMaster, fullMockCcaMaster1));

        final var actualResult = ccaDao.deleteCCA(
                        savedCcaMasters.stream()
                                .map(CcaMaster::getCcaSerialNumber)
                                .collect(Collectors.toList())
        );

        // Then
        assertEquals(2, actualResult.size());

        ccaDataFilters.forEach(ccaDataFilter ->
                assertTrue(
                        ccaDao.ccaMasterRepository.findByShipmentPrefixAndMasterDocumentNumberAndCcaReferenceNumber(
                                ccaDataFilter.getShipmentPrefix(),
                                ccaDataFilter.getMasterDocumentNumber(),
                                ccaDataFilter.getCcaReferenceNumber()
                        )
                                .isEmpty()
                )
        );
    }

    @Test
    void shouldGetCcaNumbers() {
        // Given
        var ccaRefNumber = "CCA000004";

        final var ccaNumbersFilter = new CcaSelectFilter();
        ccaNumbersFilter.setLoadFromFilter(true);
        ccaNumbersFilter.setFilter(List.of(ccaRefNumber.toLowerCase()));

        final var ccaMaster = getBasicMockCcaMaster(ccaRefNumber, "14563214");

        // Then
        assertDoesNotThrow(() -> ccaDao.saveCCA(ccaMaster));
        var ccaNumbers = ccaDao.getCCANumbers(ccaNumbersFilter, COMPANY_CODE);
        assertEquals(1, ccaNumbers.size());
        assertEquals(ccaRefNumber, ccaNumbers.get(0).getCcaNumber());
    }

    @Test
    @Transactional
    void shouldUpdateExportAndImportBillingStatus() {
        // Given
        ccaDao.ccaMasterRepository.deleteAll();
        final var masterDocNum = "27803840";
        final var ccaRefNum = "CCA000097";
        final var ccaMaster = ccaDao.ccaMasterRepository.saveAndFlush(getBasicMockCcaMaster(ccaRefNum, masterDocNum));
        ccaMaster.setExportBillingStatus(null);
        ccaMaster.setImportBillingStatus(null);

        final var ccaFilterVO = new CCAFilterVO(SHIPMENT_PREFIX, masterDocNum, ccaRefNum);
        final var newExportStatus = "E";
        final var newImportStatus = "I";
        ccaFilterVO.setExportBillingStatus(newExportStatus);
        ccaFilterVO.setImportBillingStatus(newImportStatus);

        // When
        ccaDao.updateExportAndImportBillingStatus(ccaFilterVO);

        // Then
        final var ccaMasters = ccaDao.ccaMasterRepository.findAll();

        assertEquals(1, ccaMasters.size());
        assertEquals("E", ccaMasters.get(0).getExportBillingStatus());
        assertEquals("I", ccaMasters.get(0).getImportBillingStatus());
    }


    @Test
    @Transactional
    void shouldFindCcaListViewVO() {
        // Given
        final var ccaMasterFirst = getFullMockCcaMaster();
        final var ccaMasterSecond = getFullMockCcaMaster();
        final var ccaMasterThird = getFullMockCcaMaster();
        final var ccaMasterFourth = getFullMockCcaMaster();

        ccaMasterFourth.setCcaStatus(CcaStatus.D);

        final var ccaListViewFilterVO = new CCAListViewFilterVO();
        ccaListViewFilterVO.setCompanyCode(COMPANY_CODE);
        final var pageable = PageRequest.of(1, 25);
        final var updatedPageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
        final var maxResult = updatedPageable.getPageSize();
        final var firstResult = updatedPageable.getPageNumber() * pageable.getPageSize();

        // When
        ccaDao.ccaMasterRepository.deleteAll();
        ccaDao.ccaMasterRepository.saveAll(List.of(ccaMasterFirst, ccaMasterSecond, ccaMasterThird, ccaMasterFourth));

        // Then

        assertDoesNotThrow(() -> ccaDao.findCcaListViewVO(ccaListViewFilterVO, pageable, maxResult, firstResult));

        // And
        final var dateRangeFilterData = new DateRangeFilterData();
        dateRangeFilterData.setFrom("2022-06-06");
        dateRangeFilterData.setTo("2022-06-23");
        ccaListViewFilterVO.setCcaIssueDate(dateRangeFilterData);

        // Then
        assertDoesNotThrow(() -> ccaDao.findCcaListViewVO(ccaListViewFilterVO, pageable, maxResult, firstResult));

        // And
        dateRangeFilterData.setFrom(null);
        dateRangeFilterData.setTo(null);
        ccaListViewFilterVO.setCcaIssueDate(dateRangeFilterData);

        // Then
        assertDoesNotThrow(() -> ccaDao.findCcaListViewVO(ccaListViewFilterVO, pageable, maxResult, firstResult));
    }

    @Test
    @Transactional
    void shouldUpdateCcaMasterAttachments() {
        // Given
        ccaDao.ccaMasterRepository.deleteAll();
        final var ccaMasterFirst = getFullMockCcaMaster();
        final var ccaAttachments = "    \"cca_attachments\": [\n" +
                "        {\n" +
                "            \"name\": \"print_button\",\n" +
                "            \"format\": \"pdf\",\n" +
                "            \"location\": \"icargoneoits3/CCA/CCA000004/e79a948f-4aa9-402c-9d4d-21cd0e2d39eb.png\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"location\": \"icargoneoits3/CCA/CCA000003/e79a948f-4aa9-402c-9d4d-21cd0e2d39eb.png\",\n" +
                "            \"name\": \"document\",\n" +
                "            \"format\": \"png\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"location\": \"icargoneoits3/CCA/CCA000004/e79a948f-4aa9-402c-9d4d-21cd0e2d39eb.png\",\n" +
                "            \"name\": \"print_button\",\n" +
                "            \"format\": \"pdf\"\n" +
                "        }\n" +
                "    ]";
        ccaMasterFirst.setCcaAttachments(ccaAttachments);

        // Then
        final var savedEntity = ccaDao.ccaMasterRepository.save(ccaMasterFirst);

        // And
        final var attachmentsData = new AttachmentsData();
        attachmentsData.setCompanyCode(COMPANY_CODE);
        attachmentsData.setCcaReferenceNumber(savedEntity.getCcaReferenceNumber());
        attachmentsData.setMasterDocumentNumber(savedEntity.getMasterDocumentNumber());
        attachmentsData.setShipmentPrefix(SHIPMENT_PREFIX);
        attachmentsData.setCcaAttachments("    \"cca_attachments\": [\n" +
                "        {\n" +
                "            \"name\": \"print_button\",\n" +
                "            \"format\": \"pdf\",\n" +
                "            \"location\": \"icargoneoits3/CCA/CCA000004/e79a948f-4aa9-402c-9d4d-21cd0e2d39eb.png\"\n" +
                "        }\n" +
                "    ]");

        // Then
        assertDoesNotThrow(() -> ccaDao.updateCcaMasterAttachments(attachmentsData));

        // And
        attachmentsData.setCcaAttachments(ccaAttachments);

        // Then
        assertDoesNotThrow(() -> ccaDao.updateCcaMasterAttachments(attachmentsData));

        // And
        attachmentsData.setCcaAttachments(null);

        // Then
        assertDoesNotThrow(() -> ccaDao.updateCcaMasterAttachments(attachmentsData));
    }

    @Test
    @Transactional
    void shouldUpdateCcaMasterAssigneeIfExistOrThrowException() throws CcaBusinessException {
        // Given
        ccaDao.ccaMasterRepository.deleteAll();
        final var ccaMaster = getFullMockCcaMaster();

        var ccaAssigneeData = new CcaAssigneeData();
        ccaAssigneeData.setShipmentPrefix("134");
        ccaAssigneeData.setMasterDocumentNumber("77803840");
        ccaAssigneeData.setCcaReferenceNumber("CCA00001");
        ccaAssigneeData.setAssignee("Mr.Freeze");

        // When
        var savedCca = ccaDao.ccaMasterRepository.save(ccaMaster);
        ccaDao.updateCcaMasterAssignee(ccaAssigneeData, "AV");

        // Then
        final var updatedCca = ccaDao.ccaMasterRepository.findById(savedCca.getCcaSerialNumber());
        assertTrue(updatedCca.isPresent());
        assertEquals("Mr.Freeze",updatedCca.get().getCcaAssignee());

        // And
        var wrongCcaAssigneeData = new CcaAssigneeData();
        wrongCcaAssigneeData.setShipmentPrefix("000");
        wrongCcaAssigneeData.setMasterDocumentNumber("00000000");
        wrongCcaAssigneeData.setCcaReferenceNumber("CCA000000");
        wrongCcaAssigneeData.setAssignee("Mr.Freeze");

        // Then
        assertThrows(CcaBusinessException.class, () -> ccaDao.updateCcaMasterAssignee(wrongCcaAssigneeData, "AV"));
    }

    @Test
    @Transactional
    void shouldUpdateExistingCCAForAwbVoidedEvent() {
        // Given
        final var awbVoidedEvent = new AwbVoidedEvent();
        awbVoidedEvent.setMasterDocumentNumber("77803840");
        awbVoidedEvent.setShipmentPrefix(SHIPMENT_PREFIX);

        ccaDao.ccaMasterRepository.deleteAll();
        final var ccaMasterFirst = getFullMockCcaMaster();
        ccaMasterFirst.setCcaStatus(CcaStatus.N);

        final var ccaMasterSecond = getFullMockCcaMaster();
        ccaMasterSecond.setCcaStatus(CcaStatus.C);

        final var ccuMasterThird = getFullMockCcaMaster();
        ccuMasterThird.setCcaStatus(CcaStatus.I);

        final var ccaMasterFourth = getFullMockCcaMaster();
        ccaMasterFourth.setCcaStatus(CcaStatus.A);

        // When
        ccaDao.ccaMasterRepository.saveAll(List.of(ccaMasterFirst, ccaMasterSecond, ccuMasterThird, ccaMasterFourth));
        ccaDao.updateExistingCCAForAwbVoidedEvent(awbVoidedEvent);

        // Then
        final var ccaMasters = ccaDao.ccaMasterRepository.findAll().stream()
                .filter(master -> CcaStatus.S.equals(master.getCcaStatus()))
                .filter(master -> REJECTED_AS_PART_OF_VOIDING_CCA_REASON.equals(master.getCcaReason()))
                .count();
        assertEquals(3, ccaMasters);
    }
}