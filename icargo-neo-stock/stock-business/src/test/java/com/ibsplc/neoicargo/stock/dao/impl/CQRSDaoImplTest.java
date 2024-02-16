package com.ibsplc.neoicargo.stock.dao.impl;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_UNUSED;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ibsplc.neoicargo.framework.cqrs.config.CqrsConstants;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.stock.dao.CQRSDao;
import com.ibsplc.neoicargo.stock.dao.mybatis.StockCQRSMapper;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.sql.Timestamp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@AutoConfigureEmbeddedDatabase(beanName = CqrsConstants.DATASOURCE_NAME)
class CQRSDaoImplTest {
  @Autowired private StockCQRSMapper stockCQRSMapper;
  @Autowired private LocalDate localDate;

  private CQRSDao cqrsDao;

  @BeforeEach
  void setUp() {
    cqrsDao = new CQRSDaoImpl(stockCQRSMapper, localDate);
  }

  @Test
  void shouldFindStockUtilisationDetailsStatusUnused() {
    // Given
    var stockRangeFilterVO = new StockRangeFilterVO();
    stockRangeFilterVO.setCompanyCode("IBS");
    stockRangeFilterVO.setAirlineIdentifier(1172);
    stockRangeFilterVO.setDocumentType("AWB");
    stockRangeFilterVO.setDocumentSubType("S");

    // When
    var list = cqrsDao.findStockUtilisationDetailsStatusUnused(stockRangeFilterVO);

    // Then
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindStockUtilisationDetailsStatusUsed() {
    // Given
    var stockRangeFilterVO = new StockRangeFilterVO();
    stockRangeFilterVO.setCompanyCode("IBS");
    stockRangeFilterVO.setAirlineIdentifier(1172);
    stockRangeFilterVO.setDocumentType("AWB");
    stockRangeFilterVO.setDocumentSubType("S");

    // When
    var list = cqrsDao.findStockUtilisationDetailsStatusUsed(stockRangeFilterVO);

    // Then
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindStockUtilisationDetailsStatusEmpty() {
    // Given
    var stockRangeFilterVO = new StockRangeFilterVO();
    stockRangeFilterVO.setCompanyCode("IBS");
    stockRangeFilterVO.setAirlineIdentifier(1172);
    stockRangeFilterVO.setDocumentType("AWB");
    stockRangeFilterVO.setDocumentSubType("S");

    // When
    var list = cqrsDao.findStockUtilisationDetailsStatusEmpty(stockRangeFilterVO);

    // Then
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindStockHistory() {
    // Given
    var stockRangeFilterVO = new StockRangeFilterVO();
    stockRangeFilterVO.setCompanyCode("IBS");
    stockRangeFilterVO.setAirlineIdentifier(1171);
    stockRangeFilterVO.setFromStockHolderCode("HQ");
    stockRangeFilterVO.setDocumentType("AWB");
    stockRangeFilterVO.setDocumentSubType("S");

    // When
    var list = cqrsDao.findStockHistory(stockRangeFilterVO);

    // Then
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindStockHistoryStatusReturn() {
    // Given
    var stockRangeFilterVO = new StockRangeFilterVO();
    stockRangeFilterVO.setCompanyCode("IBS");
    stockRangeFilterVO.setAirlineIdentifier(1171);
    stockRangeFilterVO.setDocumentType("AWB");
    stockRangeFilterVO.setDocumentSubType("S");
    stockRangeFilterVO.setStatus("I");

    // When
    var list = cqrsDao.findStockHistory(stockRangeFilterVO);

    // Then
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindStockHistoryStatusReturnTransfer() {
    // Given
    var stockRangeFilterVO = new StockRangeFilterVO();
    stockRangeFilterVO.setCompanyCode("IBS");
    stockRangeFilterVO.setAirlineIdentifier(1171);
    stockRangeFilterVO.setDocumentType("AWB");
    stockRangeFilterVO.setDocumentSubType("S");
    stockRangeFilterVO.setStatus("H");

    // When
    var list = cqrsDao.findStockHistory(stockRangeFilterVO);

    // Then
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindStockHistoryStatusAllocate() {
    // Given
    var stockRangeFilterVO = new StockRangeFilterVO();
    stockRangeFilterVO.setCompanyCode("IBS");
    stockRangeFilterVO.setAirlineIdentifier(1171);
    stockRangeFilterVO.setDocumentType("AWB");
    stockRangeFilterVO.setDocumentSubType("S");
    stockRangeFilterVO.setStatus("G");

    // When
    var list = cqrsDao.findStockHistory(stockRangeFilterVO);

    // Then
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindStockHistoryWithDates() {
    // Given
    var stockRangeFilterVO = new StockRangeFilterVO();
    stockRangeFilterVO.setCompanyCode("IBS");
    stockRangeFilterVO.setAirlineIdentifier(1171);
    stockRangeFilterVO.setFromStockHolderCode("HQ");
    stockRangeFilterVO.setDocumentType("AWB");
    stockRangeFilterVO.setDocumentSubType("S");
    var endDate = java.time.LocalDate.of(2022, 10, 15);
    var startDate = java.time.LocalDate.of(2022, 10, 12);
    stockRangeFilterVO.setStartDate(Timestamp.valueOf(startDate.atStartOfDay()));
    stockRangeFilterVO.setEndDate(Timestamp.valueOf(endDate.atStartOfDay()));

    // When
    var list = cqrsDao.findStockHistory(stockRangeFilterVO);

    // Then
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindAwbStockDetails() {
    // Give
    StockRangeFilterVO stockRangeFilterVO = new StockRangeFilterVO();
    stockRangeFilterVO.setCompanyCode("IBS");
    stockRangeFilterVO.setAirlineIdentifier(1182);
    stockRangeFilterVO.setFromStockHolderCode("HQ");
    stockRangeFilterVO.setAwb("6665014");

    // When
    var list = cqrsDao.findAwbStockDetails(stockRangeFilterVO);

    // Then
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindBlacklistRangesForBlackList() {
    // Given
    var blacklistStockVO = new BlacklistStockVO();
    blacklistStockVO.setCompanyCode("IBS");
    blacklistStockVO.setStockHolderCode("HQ");
    blacklistStockVO.setDocumentType("AWB");
    blacklistStockVO.setDocumentSubType("S");
    blacklistStockVO.setAirlineIdentifier(1172);
    blacklistStockVO.setRangeFrom("1441499");
    blacklistStockVO.setRangeTo("1441499");

    // When
    var list = cqrsDao.findBlacklistRangesForBlackList(blacklistStockVO);

    // Then
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindStockRangeHistoryForPage_MODE_UNUSED() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setCompanyCode("IBS");
    filterVO.setAirlineIdentifier(1172);
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);
    filterVO.setHistory(false);
    filterVO.setStartRange("0000001");
    filterVO.setEndRange("3000000");
    filterVO.setStatus(MODE_UNUSED);

    // When
    var pageable = PageRequest.of(filterVO.getPageNumber() - 1, filterVO.getPageSize());
    var result = cqrsDao.findStockRangeHistoryForPage(filterVO, pageable);

    // Then
    Assertions.assertThat(result.size()).isEqualTo(16);
  }

  @Test
  void shouldFindStockRangeHistoryForPageWithEmptyStatus() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setCompanyCode("IBS");
    filterVO.setAirlineIdentifier(1172);
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);
    filterVO.setHistory(false);
    filterVO.setStartRange("0000001");
    filterVO.setEndRange("3000000");
    filterVO.setStatus("");

    // When
    var pageable = PageRequest.of(filterVO.getPageNumber() - 1, filterVO.getPageSize());
    var result = cqrsDao.findStockRangeHistoryForPage(filterVO, pageable);

    // Then
    Assertions.assertThat(result.size()).isEqualTo(16);
  }

  @Test
  void shouldFindStockRangeHistoryForPage_MODE_USED() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setCompanyCode("IBS");
    filterVO.setAirlineIdentifier(1172);
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);
    filterVO.setHistory(false);
    filterVO.setStartRange("0000001");
    filterVO.setEndRange("3000000");
    filterVO.setStatus(MODE_USED);

    // When
    var pageable = PageRequest.of(filterVO.getPageNumber() - 1, filterVO.getPageSize());
    var result = cqrsDao.findStockRangeHistoryForPage(filterVO, pageable);

    // Then
    Assertions.assertThat(result.size()).isEqualTo(0);
  }

  @Test
  void shouldFindStockRangeHistoryForPage_Is_Not_History() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setCompanyCode("IBS");
    filterVO.setAirlineIdentifier(1171);
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);
    filterVO.setHistory(true);
    filterVO.setStartRange("0000001");
    filterVO.setEndRange("9000000");
    filterVO.setStatus(MODE_USED);

    // When
    var pageable = PageRequest.of(filterVO.getPageNumber() - 1, filterVO.getPageSize());
    var result = cqrsDao.findStockRangeHistoryForPage(filterVO, pageable);

    // Then
    Assertions.assertThat(result.size()).isEqualTo(1);
  }

  @Test
  void shouldFindStockRangeHistoryForPageBetweenDates() {
    // Given
    var startDate = java.time.LocalDate.parse("2022-01-01");
    var endDate = java.time.LocalDate.parse("2022-12-31");
    var filterVO = new StockRangeFilterVO();
    filterVO.setCompanyCode("IBS");
    filterVO.setAirlineIdentifier(1171);
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);
    filterVO.setHistory(true);
    filterVO.setStartRange("0000001");
    filterVO.setEndRange("9000000");
    filterVO.setStatus(MODE_USED);
    filterVO.setStartDate(Timestamp.valueOf(startDate.atStartOfDay()));
    filterVO.setEndDate(Timestamp.valueOf(endDate.atStartOfDay()));

    // When
    var pageable = PageRequest.of(filterVO.getPageNumber() - 1, filterVO.getPageSize());
    var result = cqrsDao.findStockRangeHistoryForPage(filterVO, pageable);

    // Then
    Assertions.assertThat(result.size()).isEqualTo(1);
  }

  @Test
  void shouldFindStockRangeHistoryForPageWith_G_Status() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setCompanyCode("IBS");
    filterVO.setAirlineIdentifier(1172);
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);
    filterVO.setHistory(true);
    filterVO.setStartRange("0000001");
    filterVO.setEndRange("9000000");
    filterVO.setStatus(StockConstant.RECEIVED_ALLOCATION);

    // When
    var pageable = PageRequest.of(filterVO.getPageNumber() - 1, filterVO.getPageSize());
    var result = cqrsDao.findStockRangeHistoryForPage(filterVO, pageable);

    // Then
    Assertions.assertThat(result.size()).isEqualTo(0);
  }

  @Test
  void shouldFindStockRangeHistoryForPageWith_H_Status() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setCompanyCode("IBS");
    filterVO.setAirlineIdentifier(1172);
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);
    filterVO.setHistory(true);
    filterVO.setStartRange("0000001");
    filterVO.setEndRange("9000000");
    filterVO.setStatus(StockConstant.RECEIVED_TRANSFER);

    // When
    var pageable = PageRequest.of(filterVO.getPageNumber() - 1, filterVO.getPageSize());
    var result = cqrsDao.findStockRangeHistoryForPage(filterVO, pageable);

    // Then
    Assertions.assertThat(result.size()).isEqualTo(0);
  }

  @Test
  void shouldFindStockRangeHistoryForPageWith_I_Status() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setCompanyCode("IBS");
    filterVO.setAirlineIdentifier(1172);
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);
    filterVO.setHistory(true);
    filterVO.setStartRange("0000001");
    filterVO.setEndRange("9000000");
    filterVO.setStatus(StockConstant.RECEIVED_RETURN);

    // When
    var pageable = PageRequest.of(filterVO.getPageNumber() - 1, filterVO.getPageSize());
    var result = cqrsDao.findStockRangeHistoryForPage(filterVO, pageable);

    // Then
    Assertions.assertThat(result.size()).isEqualTo(0);
  }

  @Test
  void shouldFindStockRangeHistoryForPageIfRangeIsNullOREmpty() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setCompanyCode("IBS");
    filterVO.setAirlineIdentifier(1172);
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);
    filterVO.setHistory(true);
    filterVO.setStartRange(null);
    filterVO.setEndRange("");
    filterVO.setStatus(MODE_UNUSED);

    // When
    var pageable = PageRequest.of(filterVO.getPageNumber() - 1, filterVO.getPageSize());
    var result = cqrsDao.findStockRangeHistoryForPage(filterVO, pageable);

    // Then
    Assertions.assertThat(result.size()).isEqualTo(0);
  }

  @Test
  void shouldFindStockRangeHistoryForPageIfRangeIsMoreSevenDigits() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setCompanyCode("IBS");
    filterVO.setAirlineIdentifier(1172);
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);
    filterVO.setHistory(true);
    filterVO.setStartRange("0000001A");
    filterVO.setEndRange("9000000B");
    filterVO.setStatus(MODE_UNUSED);

    // When
    var pageable = PageRequest.of(filterVO.getPageNumber() - 1, filterVO.getPageSize());
    var result = cqrsDao.findStockRangeHistoryForPage(filterVO, pageable);

    // Then
    Assertions.assertThat(result.size()).isEqualTo(0);
  }
}
