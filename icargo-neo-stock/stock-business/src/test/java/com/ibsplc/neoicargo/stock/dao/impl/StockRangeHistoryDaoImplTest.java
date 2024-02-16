package com.ibsplc.neoicargo.stock.dao.impl;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockStockRangeHistory;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockRangeVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.stock.dao.StockRangeHistoryDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockRangeHistory;
import com.ibsplc.neoicargo.stock.dao.mybatis.StockQueryMapper;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.dao.repository.StockRangeHistoryRepository;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeHistoryFilterVO;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
class StockRangeHistoryDaoImplTest {

  @Autowired private RangeMapper rangeMapper;
  @Autowired private StockRangeHistoryRepository stockRangeHistoryRepository;
  @Autowired private StockAgentRepository stockAgentRepository;
  @Autowired private StockQueryMapper stockQueryMapper;

  private StockRangeHistoryDao stockRangeHistoryDao;
  private StockRangeHistory stockRangeHistory;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    stockRangeHistoryDao =
        new StockRangeHistoryDaoImpl(
            rangeMapper, stockQueryMapper, stockAgentRepository, stockRangeHistoryRepository);
    stockRangeHistory = stockRangeHistoryRepository.save(getMockStockRangeHistory());
  }

  @AfterEach
  void destroy() {
    stockRangeHistoryRepository.delete(stockRangeHistory);
  }

  @Test
  void shouldReturnRangesForMerge() {
    // Given
    RangeVO vo = getMockRangeVO("IBS", "AWB", "S", 0L, 99999L);
    List<RangeVO> expected = List.of(vo);

    // When
    List<RangeVO> actual = stockRangeHistoryDao.findUsedRangesForMerge(vo, MODE_USED);

    // Then
    assertThat(actual).isNotNull().isNotEmpty();
    assertThat(actual.get(0).getAirlineIdentifier())
        .isEqualTo(expected.get(0).getAirlineIdentifier());
    assertThat(actual.get(0).getDocumentSubType()).isEqualTo(expected.get(0).getDocumentSubType());
    assertThat(actual.get(0).getDocumentType()).isEqualTo(expected.get(0).getDocumentType());
    assertThat(actual.get(0).getStockHolderCode()).isEqualTo(expected.get(0).getStockHolderCode());
  }

  @Test
  void shouldReturnStockAgentByCompanyCodeAndAgentCode() {
    // When
    var entity = stockRangeHistoryDao.findByCompanyCodeAndAgentCode("AV", "Y1001");

    // Then
    assertThat(entity.getCompanyCode()).isEqualTo("AV");
    assertThat(entity.getStockHolderCode()).isEqualTo("STKHLD1");
  }

  @Test
  void shouldReturnNullWhenStockAgentNotExists() {
    // When
    var entity = stockRangeHistoryDao.findByCompanyCodeAndAgentCode("-", "Y1001");

    // Then
    assertThat(entity).isNull();
  }

  @Test
  void shouldReturnStockRangeHistoryIdList() {
    // Given
    var filterVO =
        StockRangeHistoryFilterVO.builder()
            .companyCode("IBS")
            .airlineId(1171)
            .fromStockHolderCode("HQ")
            .docType("AWB")
            .docSubType("S")
            .build();

    // When
    var result = stockRangeHistoryDao.findStockRangeHistoryList(List.of(filterVO));

    // Then
    assertEquals(1, result.size());
    assertEquals(123, result.get(0));
  }

  @Test
  void shouldReturnNullWhenStockRangeHistoryNotExists() {
    // Given
    var filterVO =
        StockRangeHistoryFilterVO.builder()
            .companyCode("IBS")
            .airlineId(1171)
            .fromStockHolderCode("WRONG")
            .docType("AWB")
            .docSubType("S")
            .build();

    // When
    var result = stockRangeHistoryDao.findStockRangeHistoryList(List.of(filterVO));

    // Then
    assertThat(result).isEmpty();
  }

  @Test
  void shouldDeleteAllById() {
    // Given
    var id = stockRangeHistory.getStockRangeHistorySerialNumber();

    // When
    var initialStock = stockRangeHistoryRepository.findById(id);

    stockRangeHistoryDao.deleteAllById(List.of(id));

    var removedStock = stockRangeHistoryRepository.findById(id);

    // Then
    assertTrue(initialStock.isPresent());
    assertFalse(removedStock.isPresent());
  }

  @Test
  void save() {
    // Given
    var entityToSave = getMockStockRangeHistory();
    entityToSave.setCompanyCode("CC");

    // When
    stockRangeHistoryDao.save(entityToSave);
    StockRangeHistory actual =
        stockRangeHistoryRepository.findAll().stream()
            .filter(var -> var.getCompanyCode().equals("CC"))
            .findFirst()
            .orElse(null);

    // Then
    assertThat(actual).isNotNull();
    assertThat(actual.getCompanyCode()).isEqualTo("CC");
  }
}
