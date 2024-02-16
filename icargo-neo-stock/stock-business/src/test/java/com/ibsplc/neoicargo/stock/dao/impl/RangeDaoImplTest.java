package com.ibsplc.neoicargo.stock.dao.impl;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockRangeFilterVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockRangeVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getStockFilterVO;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.dao.mybatis.RangeQueryMapper;
import com.ibsplc.neoicargo.stock.dao.repository.RangeRepository;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.BlackListFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.DuplicateRangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangesForMergeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAllocationFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockDetailsFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
class RangeDaoImplTest {

  @Autowired private RangeRepository rangeRepository;
  @Autowired private RangeQueryMapper rangeQueryMapper;
  @Autowired private RangeMapper rangeMapper;

  private RangeDao rangeDao;

  @BeforeEach
  void setUp() {
    rangeDao = new RangeDaoImpl(rangeRepository, rangeQueryMapper, rangeMapper);
  }

  @Test
  void shouldFindRangeForTransfer() {
    // Given
    var filterVO =
        StockAllocationFilterVO.builder()
            .companyCode("IBS")
            .stockHolderCode("HQ")
            .airlineIdentifier(1172)
            .documentType("AWB")
            .documentSubType("S")
            .startRange(4000000)
            .build();

    // When
    var result = rangeDao.findRangeForTransfer(filterVO);

    // Then
    assertEquals(7, result.size());
  }

  @Test
  void shouldFindBlacklistRanges() {
    // Given
    var filterVO =
        BlackListFilterVO.builder()
            .companyCode("AV")
            .airlineIdentifier(1000)
            .documentType("AWB")
            .asciiStartRange(4000040)
            .asciiEndRange(4000060)
            .build();

    // When
    var result = rangeDao.checkBlacklistRanges(filterVO);

    // Then
    assertEquals("AV", result);
  }

  @Test
  void shouldNotFindBlacklistRanges() {
    // Given
    var filterVO =
        BlackListFilterVO.builder()
            .companyCode("AV")
            .airlineIdentifier(1729)
            .documentType("AWB")
            .asciiStartRange(1000100)
            .asciiEndRange(1000200)
            .build();

    // When
    var result = rangeDao.checkBlacklistRanges(filterVO);

    // Then
    assertEquals("", result);
  }

  @Test
  void shouldFindDuplicateRanges() {
    // Given
    var filterVO =
        DuplicateRangeFilterVO.builder()
            .companyCode("IBS")
            .airlineIdentifier(1173)
            .documentType("AWB")
            .startRange("1000000")
            .endRange("4000000")
            .build();

    // When
    var result = rangeDao.findDuplicateRanges(filterVO);

    // Then
    assertEquals(1, result.size());
  }

  @Test
  void shouldFindRangesForMerge() {
    // Given
    var filterVO =
        RangesForMergeFilterVO.builder()
            .companyCode("IBS")
            .stockHolderCode("HQ")
            .airlineIdentifier(1173)
            .documentType("AWB")
            .documentSubType("S")
            .manualFlag("N")
            .operationFlag("S")
            .asciiEndRange(2436000)
            .build();

    // When
    var result = rangeDao.findRangesForMerge(filterVO);

    // Then
    assertEquals(1, result.size());
  }

  @Test
  void shouldRemoveAllRanges() {
    // Given
    var rangeVOList =
        List.of(
            RangeVO.builder().rangeSerialNumber(15L).build(),
            RangeVO.builder().rangeSerialNumber(16L).build());

    // When
    var initialRanges = rangeRepository.findAllById(List.of(15L, 16L));
    rangeDao.removeAll(rangeVOList);
    var deletedRanges = rangeRepository.findAllById(List.of(15L, 16L));

    // Then
    assertEquals(2, initialRanges.size());
    assertEquals(0, deletedRanges.size());
  }

  @Test
  void shouldSaveAllRanges() {
    // Given
    var rangeVO1 = getMockRangeVO("DELETE", "AWB", "S", 0L, 99999L);
    var rangeVO2 = getMockRangeVO("DELETE", "AWB", "B", 0L, 99999L);
    var rangeVO3 = getMockRangeVO("DELETE", "AWB", "L", 1L, 2L);

    var filterVO1 = getMockRangeFilterVO(rangeVO1);
    var filterVO2 = getMockRangeFilterVO(rangeVO2);
    var filterVO3 = getMockRangeFilterVO(rangeVO3);

    // When
    var range1 = rangeDao.find(filterVO1);
    var range2 = rangeDao.find(filterVO2);
    var range3 = rangeDao.find(filterVO3);

    rangeDao.remove(range1);
    rangeDao.remove(range2);
    rangeDao.remove(range3);

    var removedRange1 = rangeDao.find(filterVO1);
    var removedRange2 = rangeDao.find(filterVO2);
    var removedRange3 = rangeDao.find(filterVO3);

    // Then
    assertNull(removedRange1);
    assertNull(removedRange2);
    assertNull(removedRange3);
  }

  @Test
  void shouldFindAvailableRanges() {
    // Given
    var stockFilterVO = getStockFilterVO(true, "N", "S", "AWB", 1000, "VR", "AV");

    // When
    var result = rangeDao.findRangesForViewRange(stockFilterVO);

    // Then
    assertEquals(1, result.size());
  }

  @Test
  void shouldFindAllocatedRanges() {
    // Given
    var stockFilterVO = getStockFilterVO(true, "N", "S", "AWB", 1000, "VR", "AV");

    // When
    var result = rangeDao.findAllocatedRanges(stockFilterVO);

    // Then
    assertEquals(1, result.size());
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: FindRanges tests")
  class FindRangesTest {
    private RangeFilterVO populateRangeFilterVO() {
      var rangeFilterVO = new RangeFilterVO();
      rangeFilterVO.setCompanyCode("AV");
      rangeFilterVO.setAirlineIdentifier(1191);
      rangeFilterVO.setStockHolderCode("ALLOCATE5");
      rangeFilterVO.setDocumentType("AWB");
      rangeFilterVO.setDocumentSubType("S");
      rangeFilterVO.setStartRange("0");
      rangeFilterVO.setEndRange("9999999");
      rangeFilterVO.setNumberOfDocuments("100");
      rangeFilterVO.setManualFlag("N");

      return rangeFilterVO;
    }

    @Test
    void shouldReturnRangesList() {
      // Given
      var rangeFilterVO = populateRangeFilterVO();

      // When
      var rangeVOs = rangeDao.findRanges(rangeFilterVO);

      // Then
      assertTrue(
          rangeVOs.stream()
              .anyMatch(e -> e.getStockHolderCode().equals(rangeFilterVO.getStockHolderCode())));
    }

    @Test
    void shouldNotReturnRangesListIfRangesEqualsToNull() {
      // Given
      var rangeFilterVO = populateRangeFilterVO();
      rangeFilterVO.setStartRange(null);
      rangeFilterVO.setEndRange(null);
      rangeFilterVO.setNumberOfDocuments(null);

      // When
      var rangeVOs = rangeDao.findRanges(rangeFilterVO);

      // Then
      Assertions.assertThat(rangeVOs.size()).isEqualTo(1);
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: FindTotalNoOfDocuments tests")
  class FindTotalNoOfDocumentsTest {
    @Test
    void shouldReturnTotalNoOfDocuments() {
      // Given
      var rangeFilterVO = populateStockFilterVO();

      // When
      var result = rangeDao.findTotalNoOfDocuments(rangeFilterVO);

      // Then
      assertEquals(1000001, result);
    }

    private StockFilterVO populateStockFilterVO() {
      var stockFilterVO = new StockFilterVO();
      stockFilterVO.setCompanyCode("AV");
      stockFilterVO.setAirlineIdentifier(1000);
      stockFilterVO.setStockHolderCode("VR");
      stockFilterVO.setDocumentType("AWB");
      stockFilterVO.setDocumentSubType("S");
      stockFilterVO.setManualFlag("N");

      return stockFilterVO;
    }
  }

  @Nested
  @BootstrapTestContextFor(tenant = "AV")
  @DisplayName("Method: ValidateStockForVoiding tests")
  class ValidateStockForVoidingTest {
    @Test
    void shouldValidateStockForVoiding() {
      // Given
      var blacklistStockVO = new BlacklistStockVO();
      blacklistStockVO.setCompanyCode("IBS");
      blacklistStockVO.setAirlineIdentifier(1172);
      blacklistStockVO.setDocumentType("AWB");
      blacklistStockVO.setDocumentSubType("S");
      blacklistStockVO.setRangeFrom("0000001");
      blacklistStockVO.setRangeTo("9000000");

      // When
      var result = rangeDao.findBlacklistRanges(blacklistStockVO);

      // Then
      assertEquals(5, result.size());
    }
  }

  @Test
  void shouldFindAvailableRangesForCustomer() {
    // Given
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setStockHolderCode("ROCKS");
    // When
    var result = rangeDao.findAvailableRangesForCustomer(stockDetailsFilterVO);
    // Then
    assertEquals(1, result.size());
  }

  @Test
  void shouldFindAllocatedRangesForCustomer() {
    // Given
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setStockHolderCode("ROCKS1");
    // When
    var result = rangeDao.findAllocatedRangesForCustomer(stockDetailsFilterVO);
    // Then
    assertEquals(1, result.size());
  }

  @Test
  void shouldFindUsedRangesForCustomer() {
    // Given
    var stockDetailsFilterVO = new StockDetailsFilterVO();
    stockDetailsFilterVO.setCompanyCode("IBS");
    stockDetailsFilterVO.setStockHolderCode("ROCKS");
    // When
    var result = rangeDao.findUsedRangesForCustomer(stockDetailsFilterVO);
    // Then
    assertEquals(1, result.size());
  }
}
