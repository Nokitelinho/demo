package com.ibsplc.neoicargo.stock.dao.impl;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockFilterVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestFilterVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.context.RequestContext;
import com.ibsplc.neoicargo.framework.core.security.spring.LoginProfileExtractor;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditContext;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.tx.AuditTxManager;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.dao.mybatis.MonitorStockQueryMapper;
import com.ibsplc.neoicargo.stock.dao.mybatis.RangeQueryMapper;
import com.ibsplc.neoicargo.stock.dao.mybatis.StockQueryMapper;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.dao.repository.StockRepository;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.mapper.StockAgentMapper;
import com.ibsplc.neoicargo.stock.mapper.StockFilterMapper;
import com.ibsplc.neoicargo.stock.mapper.StockHolderMapper;
import com.ibsplc.neoicargo.stock.mapper.StockMapper;
import com.ibsplc.neoicargo.stock.mapper.StockRequestMapper;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockRequestFilterModel;
import com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestFilterVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockDetailsFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderFilterVO;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
class StockDaoImplTest {

  @Autowired private StockRepository stockRepository;
  @Autowired private StockQueryMapper stockQueryMapper;
  @Autowired private RangeQueryMapper rangeQueryMapper;
  @Autowired private StockHolderRepository stockHolderRepository;
  @Autowired private MonitorStockQueryMapper monitorStockQueryMapper;
  @Autowired private StockRequestMapper stockRequestMapper;
  @Autowired private StockFilterMapper stockFilterMapper;
  @Autowired private StockAgentMapper stockAgentMapper;
  @Autowired private StockAgentRepository stockAgentRepository;
  private StockDao stockDao;
  private StockRequestFilterModel filterModel;
  private StockFilterModel stockFilterModel;
  private StockHolderFilterVO stockHolderFilterVO;
  @Mock private ApplicationContext applicationContext;
  @Mock private ObjectProvider<LoginProfileExtractor> loginProfileExtractor;
  @Mock private ContextUtil contextUtil;

  @BeforeEach
  void setUp() {
    var stockMapper = Mappers.getMapper(StockMapper.class);
    var rangeMapper = Mappers.getMapper(RangeMapper.class);
    var stockHolderMapper = Mappers.getMapper(StockHolderMapper.class);
    ReflectionTestUtils.setField(stockMapper, "rangeMapper", rangeMapper);
    ReflectionTestUtils.setField(stockHolderMapper, "stockMapper", stockMapper);
    stockDao =
        new StockDaoImpl(
            stockHolderMapper,
            stockHolderRepository,
            stockRepository,
            stockQueryMapper,
            rangeQueryMapper,
            rangeMapper,
            stockMapper,
            stockRequestMapper,
            monitorStockQueryMapper,
            stockAgentMapper,
            stockAgentRepository);
    filterModel = new StockRequestFilterModel();
    stockFilterModel = new StockFilterModel();
    stockHolderFilterVO = new StockHolderFilterVO();
    contextUtil = new ContextUtil(applicationContext, loginProfileExtractor);
    RequestContext requestContext = new RequestContext();
    AuditContext auditContext = new AuditContext();
    auditContext.setAuditObject(new AuditVO());
    var txMap = new HashMap<String, Object>();
    txMap.put(AuditTxManager.AUDIT_TASK_BUSINESS, auditContext);
    contextUtil.setTxContext(txMap);
    contextUtil.setContext(requestContext);
    when(applicationContext.getBean(ContextUtil.class)).thenReturn(contextUtil);
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findStockHolderDetails tests")
  class FindStockHolderDetails {

    @Test
    @Transactional
    void shouldFindStockHolderDetails() {
      // When
      var stockHolderVO = stockDao.findStockHolderDetails("IBS", "HQ");

      // Then
      assertEquals("IBS", stockHolderVO.getCompanyCode());
      assertEquals(2, stockHolderVO.getStock().size());
    }

    @Test
    void shouldReturnEmptyResponse() {
      // When
      var stockHolderVO = stockDao.findStockHolderDetails("IBS", "-");

      // Then
      assertNull(stockHolderVO);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findStockWithRanges tests")
  class FindStockWithRanges {

    @Test
    void shouldFindStockWithRanges() {
      // Given
      var stockAllocationVO =
          StockAllocationVO.builder()
              .companyCode("IBS")
              .stockHolderCode("HQ")
              .airlineIdentifier(1172)
              .documentType("AWB")
              .documentSubType("S")
              .build();

      // When
      var result =
          stockDao.findStockWithRanges(
              stockAllocationVO.getCompanyCode(),
              stockAllocationVO.getStockHolderCode(),
              stockAllocationVO.getAirlineIdentifier(),
              stockAllocationVO.getDocumentType(),
              stockAllocationVO.getDocumentSubType());

      // Then
      assertNotNull(result);
      assertEquals("IBS", result.getCompanyCode());
      assertEquals(13, result.getRanges().size());
    }

    @Test
    void shouldReturnEmptyResponse() {
      // Given
      var stockAllocationVO =
          StockAllocationVO.builder()
              .companyCode("IBS")
              .stockHolderCode("wrong")
              .airlineIdentifier(1172)
              .documentType("AWB")
              .documentSubType("S")
              .build();

      // When
      var result =
          stockDao.findStockWithRanges(
              stockAllocationVO.getCompanyCode(),
              stockAllocationVO.getStockHolderCode(),
              stockAllocationVO.getAirlineIdentifier(),
              stockAllocationVO.getDocumentType(),
              stockAllocationVO.getDocumentSubType());

      // Then
      assertNull(result);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findApproverCode tests")
  class FindApproverCode {

    @Test
    void shouldFindApproverCode() {
      // When
      var stockApproverCode = stockDao.findApproverCode("IBS", "APAC", "AWB", "S");

      // Then
      assertNotNull(stockApproverCode);
      assertEquals("HQ", stockApproverCode);
    }

    @Test
    void shouldReturnEmptyResponse() {
      // When
      var stockHolderVO = stockDao.findApproverCode("IBS", "HQ", "AWB", "-");

      // Then
      assertNull(stockHolderVO);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findMonitorStockHolderDetails tests")
  class FindMonitorStockHolderDetailsTest {

    @Test
    void shouldReturnMonitorStockVO() {
      // Given
      var stockFilterVO = getMockStockFilterVO();

      // When
      var monitorStockVO = stockDao.findMonitoringStockHolderDetails(stockFilterVO);

      // Then
      assertNotNull(monitorStockVO.getStockHolderCode());
      assertNotNull(monitorStockVO.getDocumentType());
      assertNotNull(monitorStockVO.getDocumentSubType());
    }

    @Test
    void shouldReturnMonitorStockVOifManualFlagIsYes() {
      // Given
      var stockFilterVO = getMockStockFilterVO();
      stockFilterVO.setManualFlag("Y");

      // When
      var monitorStockVO = stockDao.findMonitoringStockHolderDetails(stockFilterVO);

      // Then
      assertNotNull(monitorStockVO.getStockHolderCode());
      assertNotNull(monitorStockVO.getDocumentType());
      assertNotNull(monitorStockVO.getDocumentSubType());
    }

    @Test
    void shouldReturnMonitorStockVOifManualFlagIsNo() {
      // Given
      var stockFilterVO = getMockStockFilterVO();
      stockFilterVO.setManualFlag("Y");

      // When
      var monitorStockVO = stockDao.findMonitoringStockHolderDetails(stockFilterVO);

      // Then
      assertNotNull(monitorStockVO.getStockHolderCode());
      assertNotNull(monitorStockVO.getDocumentType());
      assertNotNull(monitorStockVO.getDocumentSubType());
    }

    @Test
    void shouldReturnMonitorStockVO_WhenPrivilegeLevelValueIsEmpty() {
      // Given
      var stockFilterVO = getMockStockFilterVO();
      stockFilterVO.setPrivilegeLevelValue(" ");

      // When
      var monitorStockVO = stockDao.findMonitoringStockHolderDetails(stockFilterVO);

      // Then
      assertNotNull(monitorStockVO.getStockHolderCode());
      assertNotNull(monitorStockVO.getDocumentType());
      assertNotNull(monitorStockVO.getDocumentSubType());
    }

    @Test
    void shouldReturnNullPrivilegeLevelValueIsPresent() {
      // Given
      var stockFilterVO = getMockStockFilterVO();
      stockFilterVO.setPrivilegeLevelValue("1,2,,3");

      // When
      var monitorStockVO = stockDao.findMonitoringStockHolderDetails(stockFilterVO);

      // Then
      assertNull(monitorStockVO);
    }

    @Test
    @Transactional
    void shouldReturnEmptyResponse() {
      // When
      var stockHolderVO = stockDao.findStockHolderDetails("IBS", "-");

      // Then
      assertNull(stockHolderVO);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findStockForStockHolder tests")
  class FindStockForStockHolder {

    @Test
    void shouldFindStockForStockHolder() {
      // Given
      var stockAllocationVO =
          StockAllocationVO.builder()
              .companyCode("IBS")
              .stockControlFor("HQ")
              .airlineIdentifier(1172)
              .documentType("AWB")
              .documentSubType("S")
              .build();

      // When
      var result = stockDao.findStockForStockHolder(stockAllocationVO);

      // Then
      assertNotNull(result);
      assertEquals(140414.0, result.getTotalStock());
    }

    @Test
    void shouldReturnEmptyResponse() {
      // Given
      var stockAllocationVO =
          StockAllocationVO.builder()
              .companyCode("IBS")
              .stockControlFor("WRONG")
              .airlineIdentifier(1172)
              .documentType("AWB")
              .documentSubType("S")
              .build();

      // When
      var result = stockDao.findStockForStockHolder(stockAllocationVO);

      // Then
      assertNull(result);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: addRanges tests")
  class AddRanges {

    @Test
    @Transactional
    void shouldAddRanges() {
      // Given
      var stockAllocationVO =
          StockAllocationVO.builder()
              .companyCode("IBS")
              .stockHolderCode("APAC")
              .airlineIdentifier(1172)
              .documentType("AWB")
              .documentSubType("S")
              .build();

      var stockVO = StockVO.builder().stockSerialNumber(3L).build();

      var rangeVO =
          RangeVO.builder()
              .companyCode("IBS")
              .stockHolderCode("APAC")
              .airlineIdentifier(1172)
              .documentType("AWB")
              .documentSubType("S")
              .stockAcceptanceDate(ZonedDateTime.now())
              .lastUpdateTime(ZonedDateTime.now())
              .startRange("101")
              .endRange("111")
              .build();

      // When
      var initialStock =
          stockDao.findStockWithRanges(
              stockAllocationVO.getCompanyCode(),
              stockAllocationVO.getStockHolderCode(),
              stockAllocationVO.getAirlineIdentifier(),
              stockAllocationVO.getDocumentType(),
              stockAllocationVO.getDocumentSubType());
      stockDao.addRanges(stockVO, List.of(rangeVO));
      var updatedStock =
          stockDao.findStockWithRanges(
              stockAllocationVO.getCompanyCode(),
              stockAllocationVO.getStockHolderCode(),
              stockAllocationVO.getAirlineIdentifier(),
              stockAllocationVO.getDocumentType(),
              stockAllocationVO.getDocumentSubType());

      // Then
      assertEquals(0, initialStock.getRanges().size());
      assertEquals(1, updatedStock.getRanges().size());
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findStockHolderTypes tests")
  class FindStockHolderTypes {

    @Test
    void shouldFindStockHolderTypes() {
      // When
      var stockHolderPriorityVOs = stockDao.findStockHolderTypes("DNSG");

      // Then
      assertNotNull(stockHolderPriorityVOs);
      assertThat(stockHolderPriorityVOs).hasSize(2);
    }

    @Test
    void shouldReturnResponseWithEmptyList() {
      // When
      var stockHolderPriorityVOs = stockDao.findStockHolderTypes("NONAME");

      // Then
      assertThat(stockHolderPriorityVOs).isEmpty();
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findPriorities tests")
  class FindPriorities {

    @Test
    void shouldReturnPriorities() {
      // When
      var stockHolderPriorityVOs = stockDao.findPriorities("AV", List.of("HQ", "HQ1", "VB"));

      // Then
      assertNotNull(stockHolderPriorityVOs);
      assertThat(stockHolderPriorityVOs).hasSize(2);
    }

    @Test
    void shouldReturnResponseWithEmptyList() {
      // When
      var stockHolderPriorityVOs = stockDao.findPriorities("NONAME", List.of("HQ", "HQ1"));

      // Then
      assertNotNull(stockHolderPriorityVOs);
      assertThat(stockHolderPriorityVOs).isEmpty();
    }

    @Test
    void shouldReturnResponseWithSinglePriority() {
      // When
      var stockHolderPriorityVOs = stockDao.findPriorities("AV", List.of("HQ", "HQ1"));

      // Then
      assertNotNull(stockHolderPriorityVOs);
      assertThat(stockHolderPriorityVOs).hasSize(1);
      assertThat(stockHolderPriorityVOs.get(0).getPriority()).isEqualTo(1);
      assertThat(stockHolderPriorityVOs.get(0).getStockHolderCode()).isEqualTo("HQ");
    }

    @Test
    void shouldFindStockHolderTypeCode() {
      // Given
      var stockRequestVO = new StockRequestVO();
      stockRequestVO.setCompanyCode("IBS");
      stockRequestVO.setStockHolderCode("HQ");
      stockRequestVO.setStockHolderType("H");

      // When
      var stkhdlNames = stockDao.findStockHolderTypeCode(stockRequestVO);

      // Then
      assertThat(stkhdlNames).isNotEmpty();
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findMonitorStock tests")
  class FindMonitorStockTest {

    private static final String PRIVILEGE_RULE = "STK_HLDR_CODE";
    private static final String PRIVILEGE_LEVEL_TYPE = "STKHLD";

    private StockFilterVO populateStockFilterVO() {
      var vo = new StockFilterVO();
      vo.setCompanyCode("AV");
      vo.setAirlineIdentifier(1191);
      vo.setStockHolderCode("HQ");
      vo.setDocumentType("AWB");
      vo.setDocumentSubType("S");
      vo.setPageNumber(1);
      vo.setAbsoluteIndex(1);
      vo.setPrivilegeLevelType("TEST-level-type");
      vo.setPrivilegeLevelValue(null);
      vo.setPrivilegeRule("TEST-rule");
      vo.setManualFlag("Y");

      return vo;
    }

    @Test
    void shouldReturnMonitorStockListViewVOWithResults() {
      // Given
      var stockFilterVO = populateStockFilterVO();

      // When
      var monitorStockVOs = stockDao.findMonitorStock(stockFilterVO);

      // Then
      assertEquals(2, monitorStockVOs.size());
    }

    @Test
    void shouldReturnAvailableStockAndAllocatedStockWithZeroValue() {
      // Given
      var stockFilterVO = populateStockFilterVO();
      stockFilterVO.setManualFlag("Y");

      // When
      var monitorStockVOs = stockDao.findMonitorStock(stockFilterVO);

      // Then
      var monitorStockVOList =
          monitorStockVOs.stream()
              .filter(e -> Objects.equals(e.getAllocatedStock(), 0))
              .filter(e -> Objects.equals(e.getAvailableStock(), 0))
              .collect(Collectors.toList());

      assertEquals(0, monitorStockVOList.size());
      assertEquals(2, monitorStockVOs.size());
    }

    @Test
    void shouldReturnNull_IF_PrivilegeLevelValueNotFound() {
      // Given
      var stockFilterVO = populateStockFilterVO();
      stockFilterVO.setPrivilegeLevelValue("1,2,,3");
      stockFilterVO.setPrivilegeRule(PRIVILEGE_RULE);
      stockFilterVO.setPrivilegeLevelType(PRIVILEGE_LEVEL_TYPE);

      // When
      var monitorStockVOs = stockDao.findMonitorStock(stockFilterVO);

      // Then
      assertThat(monitorStockVOs).isEmpty();
    }

    @Test
    void shouldReturnResults_WhenPrivilegeLevelValueIsEmpty() {
      // Given
      var stockFilterVO = populateStockFilterVO();
      stockFilterVO.setPrivilegeLevelValue(" ");
      stockFilterVO.setPrivilegeRule(PRIVILEGE_RULE);
      stockFilterVO.setPrivilegeLevelType(PRIVILEGE_LEVEL_TYPE);

      // When
      var monitorStockVOs = stockDao.findMonitorStock(stockFilterVO);

      // Then
      assertEquals(2, monitorStockVOs.size());
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findStockRequests tests")
  class FindStockRequests {

    private final PageRequest pageable = PageRequest.of(0, 2);
    private static final String PRIVILEGE_RULE = "STK_HLDR_CODE";
    private static final String PRIVILEGE_LEVEL_TYPE = "STKHLD";

    @Test
    @DisplayName("Filter: companyCode")
    void shouldFindStockRequestsFilter1() {
      // Given
      filterModel.setCompanyCode("AV");
      filterModel.setAllocateCall(true);
      var filterVO = stockRequestMapper.mapModelToVo(filterModel);

      // When
      var actual = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("Filter: requestRefNumber")
    void shouldFindStockRequestsFilter2() {
      // Given
      filterModel.setRequestRefNumber("ALLOCATE1005");
      var filterVO = stockRequestMapper.mapModelToVo(filterModel);

      // When
      var actual = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(1);
      assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("Filter: status")
    void shouldFindStockRequestsFilter3() {
      StockRequestFilterVO filterVO;
      // Case 1
      filterModel.setStatus("ALL");
      filterModel.setAllocateCall(true);
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result1 = stockDao.findStockRequests(filterVO, pageable);

      // Case 2
      filterModel.setAllocateCall(false);
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result2 = stockDao.findStockRequests(filterVO, pageable);

      // Case 3
      filterModel.setStatus("Y");
      filterModel.setAllocateCall(true);
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result3 = stockDao.findStockRequests(filterVO, pageable);

      // Case 4
      filterModel.setAllocateCall(false);
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result4 = stockDao.findStockRequests(filterVO, pageable);

      // Case 5
      filterModel.setStatus("INVALID");
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result5 = stockDao.findStockRequests(filterVO, pageable);

      // Case 6
      filterModel.setStatus(" ");
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result6 = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(result1).isNotNull().isNotEmpty();
      assertThat(result1.get(0)).isNotNull();
      assertThat(result1).hasSize(1);

      assertThat(result2).isNotNull().isNotEmpty();
      assertThat(result2.get(0)).isNotNull();
      assertThat(result2).hasSize(2);

      assertThat(result3).isNotNull().isNotEmpty();
      assertThat(result3.get(0)).isNotNull();
      assertThat(result3).hasSize(1);

      assertThat(result4).isNotNull().isNotEmpty();
      assertThat(result4.get(0)).isNotNull();
      assertThat(result4).hasSize(1);

      assertThat(result5).isEmpty();

      assertThat(result6).isNotNull().isNotEmpty();
      assertThat(result6.get(0)).isNotNull();
      assertThat(result6).hasSize(2);
    }

    @Test
    @DisplayName("Filter: stockHolderCode")
    void shouldFindStockRequestsFilter4() {
      // Given
      filterModel.setManual(false);
      filterModel.setStockHolderCode("ALLOCATE4");
      var filterVO = stockRequestMapper.mapModelToVo(filterModel);

      // When
      var actual = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("Filter: stockHolderType")
    void shouldFindStockRequestsFilter5() {
      // Given
      filterModel.setStockHolderType("A");
      var filterVO = stockRequestMapper.mapModelToVo(filterModel);

      // When
      var actual = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Filter: documentType")
    void shouldFindStockRequestsFilter6() {
      // Given
      filterModel.setDocumentType("AWB");
      var filterVO = stockRequestMapper.mapModelToVo(filterModel);

      // When
      var actual = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("Filter: documentSubType")
    void shouldFindStockRequestsFilter7() {
      // Given
      filterModel.setDocumentSubType("S");
      var filterVO = stockRequestMapper.mapModelToVo(filterModel);

      // When
      var actual = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("Filter: approver")
    void shouldFindStockRequestsFilter8() {
      // Given
      filterModel.setApprover("BO");
      var filterVO = stockRequestMapper.mapModelToVo(filterModel);

      // When
      var actual = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Filter: airlineIdentifier")
    void shouldFindStockRequestsFilter9() {
      // Given
      filterModel.setAirlineIdentifier("1172");
      var filterVO = stockRequestMapper.mapModelToVo(filterModel);

      // When
      var actual = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("Filter: privilegeLevelType & privilegeLevelValue & privilegeRule")
    void shouldFindStockRequestsFilter10() {
      // Given
      StockRequestFilterVO filterVO;

      // When
      filterModel.setPrivilegeLevelValue("INVALID");
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result1 = stockDao.findStockRequests(filterVO, pageable);

      filterModel.setPrivilegeLevelValue("11111109");
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result2 = stockDao.findStockRequests(filterVO, pageable);

      filterModel.setPrivilegeLevelValue("11111109");
      filterModel.setPrivilegeRule("INVALID");
      filterModel.setPrivilegeLevelType("INVALID");
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result3 = stockDao.findStockRequests(filterVO, pageable);

      filterModel.setPrivilegeLevelValue("11111109");
      filterModel.setPrivilegeRule(PRIVILEGE_RULE);
      filterModel.setPrivilegeLevelType("INVALID");
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result4 = stockDao.findStockRequests(filterVO, pageable);

      filterModel.setPrivilegeLevelValue("11111109");
      filterModel.setPrivilegeRule("INVALID");
      filterModel.setPrivilegeLevelType(PRIVILEGE_LEVEL_TYPE);
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result5 = stockDao.findStockRequests(filterVO, pageable);

      filterModel.setPrivilegeLevelValue("INVALID");
      filterModel.setPrivilegeRule(PRIVILEGE_RULE);
      filterModel.setPrivilegeLevelType(PRIVILEGE_LEVEL_TYPE);
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result6 = stockDao.findStockRequests(filterVO, pageable);

      // found by STKHLDCOD
      filterModel.setPrivilegeLevelValue("11111109");
      filterModel.setPrivilegeRule(PRIVILEGE_RULE);
      filterModel.setPrivilegeLevelType(PRIVILEGE_LEVEL_TYPE);
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result7 = stockDao.findStockRequests(filterVO, pageable);

      // found by STKAPRCOD
      filterModel.setPrivilegeLevelValue("SIN");
      filterModel.setPrivilegeRule(PRIVILEGE_RULE);
      filterModel.setPrivilegeLevelType(PRIVILEGE_LEVEL_TYPE);
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result8 = stockDao.findStockRequests(filterVO, pageable);

      filterModel.setPrivilegeLevelValue("");
      filterModel.setPrivilegeRule(PRIVILEGE_RULE);
      filterModel.setPrivilegeLevelType(PRIVILEGE_LEVEL_TYPE);
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result9 = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(result1).isNotNull().isNotEmpty().hasSize(2);
      assertThat(result2).isNotNull().isNotEmpty().hasSize(2);
      assertThat(result3).isNotNull().isNotEmpty().hasSize(2);
      assertThat(result4).isNotNull().isNotEmpty().hasSize(2);
      assertThat(result5).isNotNull().isNotEmpty().hasSize(2);
      assertThat(result6).isNotNull().isEmpty();
      assertThat(result7).isNotNull().isEmpty();
      assertThat(result8).isNotNull().isEmpty();
      assertThat(result9).isNotNull().isNotEmpty().hasSize(2);
    }

    @Test
    @DisplayName("Filter: requestCreatedBy")
    void shouldFindStockRequestsFilter11() {
      // Given
      StockRequestFilterVO filterVO;

      // When
      filterModel.setRequestCreatedBy(List.of("ICOADMIN"));
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result1 = stockDao.findStockRequests(filterVO, pageable);

      filterModel.setRequestCreatedBy(List.of("ICOADMIN", "INVALID"));
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result2 = stockDao.findStockRequests(filterVO, pageable);

      filterModel.setRequestCreatedBy(List.of("INVALID"));
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result3 = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(result1).isNotNull().isNotEmpty();
      assertThat(result1.get(0)).isNotNull();
      assertThat(result1.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(result1).hasSize(2);

      assertThat(result2).isNotNull().isNotEmpty();
      assertThat(result2.get(0)).isNotNull();
      assertThat(result2.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(result2).hasSize(2);

      assertThat(result3).isEmpty();
    }

    @Test
    @DisplayName("Filter: isManual")
    void shouldFindStockRequestsFilter12() {
      // Given
      StockRequestFilterVO filterVO;

      // When
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result1 = stockDao.findStockRequests(filterVO, pageable);

      filterModel.setManual(true);
      filterVO = stockRequestMapper.mapModelToVo(filterModel);
      var result2 = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(result1).isNotNull().isNotEmpty();
      assertThat(result1.get(0)).isNotNull();
      assertThat(result1.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(result1).hasSize(2);

      assertThat(result2).isNotNull().isNotEmpty();
      assertThat(result2.get(0)).isNotNull();
      assertThat(result2.get(0).getTotalRecordCount()).isEqualTo(8);
      assertThat(result2).hasSize(2);
    }

    @Test
    @DisplayName("Filter: fromDate")
    void shouldFindStockRequestsFilter13() {
      // Given
      filterModel.setFromDate(LocalDateMapper.toLocalDate(ZonedDateTime.now().minusMonths(2)));
      var filterVO = stockRequestMapper.mapModelToVo(filterModel);

      // When
      var actual = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Filter: toDate")
    void shouldFindStockRequestsFilter14() {
      // Given
      filterModel.setToDate(LocalDateMapper.toLocalDate(ZonedDateTime.now().plusYears(1)));
      var filterVO = stockRequestMapper.mapModelToVo(filterModel);

      // When
      var actual = stockDao.findStockRequests(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(actual).hasSize(2);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: FindStockHolderLov tests")
  class FindStockHolderLov {

    @Test
    void shouldReturnResults() {
      // Given
      var filterVO = MockModelGenerator.getMockStockHolderLovFilterVO();

      // Then
      var result = stockDao.findStockHolderLov(filterVO);

      assertThat(result).hasSize(1);
      assertThat(result.get(0).getStockHolderCode()).isEqualTo("ALLOCATE5");
      assertThat(result.get(0).getStockHolderType()).isEqualTo("H");
      assertThat(result.get(0).getStockHolderName()).isEqualTo("HEAD QUARTER 2");
      assertThat(result.get(0).getDescription()).isEqualTo("TEST");
    }

    @Test
    void shouldNotReturnResults() {
      // Given
      var filterVO = MockModelGenerator.getMockStockHolderLovFilterVO();
      filterVO.setCompanyCode("AV-TEST");

      // Then
      var result = stockDao.findStockHolderLov(filterVO);

      assertThat(result).isEmpty();
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: FindAvailableRanges tests")
  class FindAvailableRanges {

    @Test
    void shouldReturnResults() {
      // Given
      var filterVO = getMockStockFilterVO("IBS", "HQ", "H", 1172, "N", "AWB", "S");
      final var pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

      // Then
      var result = stockDao.findAvailableRanges(filterVO, pageable);

      assertThat(result).hasSize(13);
    }

    @Test
    void shouldNotReturnResults() {
      // Given
      var filterVO = getMockStockFilterVO();
      final var pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);
      filterVO.setCompanyCode("AV-TEST");

      // Then
      var result = stockDao.findAvailableRanges(filterVO, pageable);

      assertThat(result).isEmpty();
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: FindAutoProcessingQuantityAvailable tests")
  class FindAutoProcessingQuantityAvailable {

    @Test
    void shouldReturnResults() {
      // Then
      var result = stockDao.findAutoProcessingQuantityAvailable("AV", "CC", "AWB", "S");

      assertNotNull(result);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: checkStock tests")
  class CheckStock {

    @Test
    void shouldPassWhenStockExists() {
      var actual = stockDao.checkStock("AV", "HQ", "AWB", "S");

      assertThat(actual).isTrue();
    }

    @Test
    void shouldNotPassWhenStockDoesntExists() {
      var actual = stockDao.checkStock("WRONG", "HQ", "AWB", "S");

      assertThat(actual).isFalse();
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findStockRequestDetails tests")
  class FindStockRequestDetailsFeature {

    private StockRequestFilterVO stockRequestFilterVO;

    @BeforeEach
    public void setUp() {
      stockRequestFilterVO = getMockStockRequestFilterVO();
    }

    @Test
    void shouldFindStockRequestDetails() {
      stockRequestFilterVO.setRequestRefNumber("ALLOCATE1000");
      var actual = stockDao.findStockRequestDetails(stockRequestFilterVO);

      assertThat(actual).isNotNull();
    }

    @Test
    void shouldNotFindStockRequestDetails() {
      stockRequestFilterVO.setRequestRefNumber("INVALID");
      var actual = stockDao.findStockRequestDetails(stockRequestFilterVO);

      assertThat(actual).isNotNull();
      assertThat(actual.getCompanyCode()).isNull();
      assertThat(actual.getDocumentType()).isNull();
      assertThat(actual.getAirlineIdentifier()).isNull();
    }

    @Test
    void shouldFindStockRequestDetails_WithoutPrivilegeLevelValue() {
      // Given
      stockRequestFilterVO.setRequestRefNumber("ALLOCATE1000");
      stockRequestFilterVO.setPrivilegeLevelValue(" ");

      // When
      var actual = stockDao.findStockRequestDetails(stockRequestFilterVO);

      // Then
      assertThat(actual).isNotNull();
    }

    @Test
    void shouldNotFindStockRequestDetails_WithInvalidPrivilegeLevelProperties() {
      // Given
      stockRequestFilterVO.setRequestRefNumber("ALLOCATE1000");
      stockRequestFilterVO.setPrivilegeRule("STK_HLDR_CODE");
      stockRequestFilterVO.setPrivilegeLevelType("STKHLD");
      stockRequestFilterVO.setPrivilegeLevelValue("1,2,,3");
      stockRequestFilterVO.setLevelValues(List.of("1", "2", "3"));

      // When
      var actual = stockDao.findStockRequestDetails(stockRequestFilterVO);

      // Then
      assertThat(actual).isNotNull();
      assertThat(actual.getCompanyCode()).isNull();
      assertThat(actual.getDocumentType()).isNull();
      assertThat(actual.getAirlineIdentifier()).isNull();
    }

    @Test
    void shouldFindStockRequestDetails_WithPrivilegeLevelProperties() {
      // Given
      stockRequestFilterVO.setRequestRefNumber("ALLOCATE1000");
      stockRequestFilterVO.setPrivilegeRule("STK_HLDR_CODE");
      stockRequestFilterVO.setPrivilegeLevelType("STKHLD");
      stockRequestFilterVO.setPrivilegeLevelValue("HQ, ,INVALID");
      stockRequestFilterVO.setLevelValues(List.of("HQ", "INVALID"));

      // When
      var actual = stockDao.findStockRequestDetails(stockRequestFilterVO);

      // Then
      assertThat(actual).isNotNull();
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findDocumentDetailsFeature tests")
  class FindDocumentDetailsFeature {

    @Test
    void shouldFindDocumentDetails() {
      var companyCode = "XX";
      var airlineIdentifier = 1003;
      var documentNumber = "2000200";
      var actual =
          stockDao.findDocumentDetails(companyCode, airlineIdentifier, toLong(documentNumber));

      assertThat(actual).isNotNull();
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findBlacklistedStock tests")
  class FindBlacklistedStock {

    private final PageRequest pageable = PageRequest.of(0, 25);

    @Test
    @DisplayName("Filter: companyCode")
    void shouldFindBlacklistedStock1() {
      // Given
      stockFilterModel.setCompanyCode("DNUK");
      var filterVO = stockFilterMapper.mapModelToVo(stockFilterModel);

      // When
      var actual = stockDao.findBlacklistedStock(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(1);
      assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("Filter: documentType")
    void shouldFindBlacklistedStock2() {
      // Given
      stockFilterModel.setDocumentType("AWB1");
      var filterVO = stockFilterMapper.mapModelToVo(stockFilterModel);

      // When
      var actual = stockDao.findBlacklistedStock(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("Filter: documentSubType")
    void shouldFindBlacklistedStock3() {
      // Given
      stockFilterModel.setDocumentSubType("S1");
      var filterVO = stockFilterMapper.mapModelToVo(stockFilterModel);

      // When
      var actual = stockDao.findBlacklistedStock(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(3);
      assertThat(actual).hasSize(3);
    }

    @Test
    @DisplayName("Filter: airlineIdentifier")
    void shouldFindBlacklistedStock4() {
      // Given
      stockFilterModel.setAirlineIdentifier(1000);
      var filterVO = stockFilterMapper.mapModelToVo(stockFilterModel);

      // When
      var actual = stockDao.findBlacklistedStock(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("Filter: rangeFrom")
    void shouldFindBlacklistedStock5() {
      // Given
      stockFilterModel.setRangeFrom("7777824");
      var filterVO = stockFilterMapper.mapModelToVo(stockFilterModel);

      // When
      var actual = stockDao.findBlacklistedStock(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(1);
      assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("Filter: rangeTo")
    void shouldFindBlacklistedStock6() {
      // Given
      stockFilterModel.setRangeTo("8888876");
      var filterVO = stockFilterMapper.mapModelToVo(stockFilterModel);

      // When
      var actual = stockDao.findBlacklistedStock(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(2);
      assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("Filter: fromDate")
    void shouldFindBlacklistedStock7() {
      // Given
      filterModel.setFromDate(LocalDateMapper.toLocalDate(ZonedDateTime.now().minusMonths(2)));
      var filterVO = stockFilterMapper.mapModelToVo(stockFilterModel);

      // When
      var actual = stockDao.findBlacklistedStock(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(14);
      assertThat(actual).hasSize(14);
    }

    @Test
    @DisplayName("Filter: toDate")
    void shouldFindBlacklistedStock8() {
      // Given
      filterModel.setFromDate(LocalDateMapper.toLocalDate(ZonedDateTime.now().minusYears(5)));
      var filterVO = stockFilterMapper.mapModelToVo(stockFilterModel);

      // When
      var actual = stockDao.findBlacklistedStock(filterVO, pageable);

      // Then
      assertThat(actual).isNotNull().isNotEmpty();
      assertThat(actual.get(0)).isNotNull();
      assertThat(actual.get(0).getTotalRecordCount()).isEqualTo(14);
      assertThat(actual).hasSize(14);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: checkForBlacklistedDocument tests")
  class CheckForBlacklistedDocumentFeature {

    @Test
    void shouldCheckForBlacklistedDocument1() {
      Boolean actual = stockDao.checkForBlacklistedDocument("AV", "AWB", "2011734", 1134L);
      assertThat(actual).isTrue();
    }

    @Test
    void shouldCheckForBlacklistedDocument2() {
      Boolean actual = stockDao.checkForBlacklistedDocument("INVALID", "AWB", "2011735", 1777L);
      assertThat(actual).isFalse();
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findBlacklistRanges tests")
  class FindBlacklistRanges {

    @Test
    @Transactional
    void shouldFindBlacklistRanges() {
      // Given
      var vo = new BlacklistStockVO();
      vo.setCompanyCode("IBS");
      vo.setAirlineIdentifier(1172);
      vo.setDocumentType("AWB");
      vo.setDocumentSubType("S");
      vo.setRangeFrom("6767676");
      vo.setRangeTo("6767676");

      // When
      var stocks = stockDao.findBlacklistRanges(vo);

      // Then
      assertFalse(stocks.isEmpty());
      assertEquals(1, stocks.size());
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findStockAgentMappings tests")
  class FindStockAgentMappings {

    @Test
    @Transactional
    void shouldFindStockAgentMappings() {
      // Given
      var vo = new StockAgentFilterVO();
      vo.setCompanyCode("AV");
      vo.setAgentCode("T1001");
      vo.setStockHolderCode("1234567");
      vo.setPageNumber(1);

      // When
      var agents = stockDao.findStockAgentMappings(vo);

      // Then
      assertFalse(agents.isEmpty());
      assertEquals(1, agents.size());
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findStockDetails tests")
  class FindStockDetails {

    @Test
    @Transactional
    void shouldFindStockDetails() {
      // Given
      var filterVO = new DocumentFilterVO();
      filterVO.setCompanyCode("IBS");
      filterVO.setAirlineIdentifier(1137);
      filterVO.setDocumentNumber("1000");
      filterVO.setDocumentType("AWB");
      filterVO.setDocumentSubType("S");

      // When
      var result = stockDao.isStockDetailsExists(filterVO);

      // Then
      assertThat(result).isTrue();
    }

    @Test
    @Transactional
    void shouldNotFindStockDetails() {
      // Given
      var filterVO = new DocumentFilterVO();
      filterVO.setCompanyCode("AV");
      filterVO.setAirlineIdentifier(1137);
      filterVO.setMstdocnum("1000");
      filterVO.setDocumentType("AWB");
      filterVO.setDocumentSubType("S");

      // When
      var result = stockDao.isStockDetailsExists(filterVO);

      // Then
      assertThat(result).isFalse();
    }
  }

  @Test
  void shouldFindStockAgent() {
    // When
    var value = stockDao.findStockAgent("AV", "D1001");
    // Then
    assertEquals("AV", value.getCompanyCode());
    assertEquals("D1001", value.getAgentCode());
  }

  @Test
  void shouldFindStockAgentIsNull() {
    // When
    var value = stockDao.findStockAgent("AV", "-");
    // Then
    assertNull(value);
  }

  @Test
  void shouldFindAutoPopulateSubtype() {
    var documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setStockOwner("AGT345");
    documentFilterVO.setStockHolderCode("STKHLD345");
    documentFilterVO.setDocumentType("AWB");
    documentFilterVO.setDocumentSubType("S");
    var actual = stockDao.findAutoPopulateSubtype(documentFilterVO);
    assertEquals("S", actual);
  }

  @Test
  void shouldFindAutoPopulateSubtypeWithNull() {
    // When
    var documentFilterVO = new DocumentFilterVO();
    var actual = stockDao.findAutoPopulateSubtype(documentFilterVO);

    // Then
    assertNull(actual);
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: findStockHolders tests")
  class FindStockHoldersFeature {

    @Test
    void shouldFindStockHolders1() {
      stockHolderFilterVO.setPageNumber(1);
      stockHolderFilterVO.setCompanyCode("IBS");
      stockHolderFilterVO.setStockHolderType("S");

      var list = stockDao.findStockHolders(stockHolderFilterVO);
      assertThat(list).hasSize(4);
    }

    @Test
    void shouldFindStockHolders2() {
      stockHolderFilterVO.setPageNumber(1);
      var list = stockDao.findStockHolders(stockHolderFilterVO);
      assertThat(list).isEmpty();
    }
  }

  @Test
  void shouldFindCustomerStockDetails() {
    // Given
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setStockHolderCode("ROCKS");
    // When
    var result = stockDao.findCustomerStockDetails(stockDetailsFilterVO);
    // Then
    assertEquals("HQ", result.getApproverCode());
  }

  @Test
  void shouldFindCustomerStockDetailsNull() {
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    // When
    var result = stockDao.findCustomerStockDetails(stockDetailsFilterVO);
    // Then
    assertNull(result);
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("method: findAWBStockDetailsForPrint tests")
  class FindAWBStockDetailsForPrintFeature {

    @Test
    void shouldFindAWBStockDetailsForPrint() {

      StockFilterVO stockFilterVO = new StockFilterVO();
      List<RangeVO> list = stockDao.findAWBStockDetailsForPrint(stockFilterVO);
      assertNotNull(list);
    }

    @Test
    void shouldFindAWBStockDetailsForPrintEmpty() {
      StockFilterVO stockFilterVO = new StockFilterVO();
      stockFilterVO.setCompanyCode("2345");
      stockFilterVO.setStockHolderCode("12345");
      List<RangeVO> list = stockDao.findAWBStockDetailsForPrint(stockFilterVO);
      assertThat(list).isEmpty();
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: FindStockRangeDetails tests")
  class FindStockRangeDetails {

    @Test
    void shouldFindStockRangeDetails() {
      var rangeVO = stockDao.findStockRangeDetails("XX", 1003, "AWB", 2000001);
      assertThat(rangeVO.getStockHolderCode()).isEqualTo("HQ");
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: FindAgentsForStockHolder tests")
  class FindAgentsForStockHolder {

    @Test
    void shouldFindAgentsForStockHolder() {
      var list = stockDao.findAgentsForStockHolder("AV", "VALIDATE");
      assertThat(list).hasSize(2);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: CheckApprover tests")
  class CheckApprover {

    @Test
    void shouldCheckApprover() {
      var result = stockDao.checkApprover("AV", "HQ");
      assertThat(result).isEqualTo(4);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: RemoveStockHolder tests")
  class RemoveStockHolder {

    @Test
    @Transactional
    void shouldRemoveStockHolder() {
      var holderVO1 = stockDao.findStockHolderDetails("IBS", "AGT67");
      assertEquals(42, holderVO1.getStockHolderSerialNumber());
      stockDao.remove(holderVO1);
      var holderVO2 = stockDao.findStockHolderDetails("IBS", "AGT67");
      assertNull(holderVO2);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: GetStockAgentMappings tests")
  class GetStockAgentMappings {

    @Test
    void shouldGetStockAgentMappings1() {
      StockAgentFilterVO filterVO = new StockAgentFilterVO();
      filterVO.setCompanyCode("AV");
      filterVO.setStockHolderCode("VALIDATE");
      var list = stockDao.getStockAgentMappings(filterVO);
      assertThat(list).hasSize(2);
    }

    @Test
    void shouldGetStockAgentMappings2() {
      StockAgentFilterVO filterVO = new StockAgentFilterVO();
      filterVO.setCompanyCode("AV");
      filterVO.setStockHolderCode("VALIDATE1");
      var list = stockDao.getStockAgentMappings(filterVO);
      assertThat(list).isEmpty();
    }

    @Test
    void shouldGetStockAgentMappings3() {
      StockAgentFilterVO filterVO = new StockAgentFilterVO();
      filterVO.setCompanyCode("AV");
      filterVO.setStockHolderCode("VALIDATE");
      filterVO.setAgentCode("DHLCDG");
      var list = stockDao.getStockAgentMappings(filterVO);
      assertThat(list).hasSize(1);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: FindRangeDelete tests")
  class FindRangeDelete {

    @Test
    void shouldFindRangeDelete() {
      var rangeVO = stockDao.findRangeDelete("XX", "AWB", "S", 2000000L);
      assertThat(rangeVO.getStockHolderCode()).isEqualTo("HQ");
    }
  }
}
