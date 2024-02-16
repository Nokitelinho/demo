package com.ibsplc.neoicargo.stock.util.mock;

import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.stock.dao.entity.Range;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.dao.entity.StockRangeHistory;
import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class MockEntityGenerator {

  @NotNull
  public static Page<? extends BaseModel> getMockPage(
      List<? extends BaseModel> results,
      Integer pageNumber,
      Integer actualPageSize,
      Integer totalRecordCount) {
    int startIndex = (pageNumber - 1) * actualPageSize;
    int endIndex = startIndex + actualPageSize;
    boolean hasNextPage = endIndex < totalRecordCount;

    return new Page<>(
        results,
        pageNumber,
        DEFAULT_PAGE_SIZE,
        actualPageSize,
        startIndex,
        endIndex,
        hasNextPage,
        totalRecordCount);
  }

  @NotNull
  public static StockHolder getMockFullStockHolderEntity() {
    var stockHolder = getMockStockHolderEntity("IBS", "HQ");

    var stock1 = getMockStockEntity(stockHolder, 123, "dt", "dsp");
    var stock2 = getMockStockEntity(stockHolder, 124, "dt", "dsp");

    getMockRangeEntity(stock1);
    getMockRangeEntity(stock1);
    getMockRangeEntity(stock2);

    return stockHolder;
  }

  @NotNull
  public static StockHolder getMockStockHolderEntity(String companyCode, String stockHolderCode) {
    var stockHolder =
        StockHolder.builder()
            .stockHolderCode(stockHolderCode)
            .stockHolderType(StockHolderType.A)
            .stockHolderName("N1")
            .controlPrivilege("CP1")
            .build();

    setBaseEntityFields(stockHolder, companyCode);
    return stockHolder;
  }

  @NotNull
  public static Stock getMockStockEntity(
      StockHolder stockHolder, int airlineId, String docType, String docSubType) {
    var stock =
        Stock.builder()
            .stockHolder(stockHolder)
            .stockHolderCode(stockHolder.getStockHolderCode())
            .airlineIdentifier(airlineId)
            .documentType(docType)
            .documentSubType(docSubType)
            .autoPopulateFlag(FLAG_YES)
            .reorderAlertFlag(FLAG_YES)
            .autoRequestFlag(FLAG_YES)
            .build();

    if (Objects.isNull(stockHolder.getStock())) {
      stockHolder.setStock(new HashSet<>());
    }
    stockHolder.getStock().add(stock);
    setBaseEntityFields(stock, stockHolder.getCompanyCode());

    return stock;
  }

  @NotNull
  public static Range getMockRangeEntity(Stock stock) {
    var range =
        Range.builder()
            .stock(stock)
            .stockHolderCode(stock.getStockHolderCode())
            .airlineIdentifier(stock.getAirlineIdentifier())
            .documentType(stock.getDocumentType())
            .documentSubType(stock.getDocumentSubType())
            .stockAcceptanceDate(ZonedDateTime.now())
            .startRange("101")
            .endRange("111")
            .isManual("Y")
            .build();

    if (Objects.isNull(stock.getRanges())) {
      stock.setRanges(new HashSet<>());
    }
    stock.getRanges().add(range);
    setBaseEntityFields(range, stock.getCompanyCode());
    return range;
  }

  @NotNull
  public static StockRangeHistory getMockStockRangeHistory() {
    var stockRangeHistory = new StockRangeHistory();
    stockRangeHistory.setStatus(MODE_USED);
    stockRangeHistory.setAirlineIdentifier(1172);
    stockRangeHistory.setDocumentSubType("S");
    stockRangeHistory.setDocumentType("AWB");
    stockRangeHistory.setFromStockHolderCode("HQ");
    stockRangeHistory.setCompanyCode("IBS");
    stockRangeHistory.setTransactionDate(ZonedDateTime.now());
    return stockRangeHistory;
  }

  @NotNull
  public static StockAgent getMockStockAgent() {
    var stockAgent = new StockAgent();
    stockAgent.setAgentCode("AC");
    stockAgent.setStockAgentSerialNumber(1L);
    stockAgent.setCompanyCode("IBS");
    stockAgent.setStockHolderCode("HC");
    return stockAgent;
  }

  @NotNull
  public static StockRequest getMockStockRequest(
      String companyCode,
      Integer airlineIdentifier,
      String requestRefNumber,
      String stockHolderCode) {
    StockRequest entity = new StockRequest();
    entity.setCompanyCode(companyCode);
    entity.setStockHolderCode(stockHolderCode);
    entity.setAirlineIdentifier(airlineIdentifier);
    entity.setDocumentType("AWB");
    entity.setDocumentSubType("S");
    entity.setRequestRefNumber(requestRefNumber);
    entity.setRequestDate(ZonedDateTime.now());
    entity.setStatus("C");
    entity.setLastUpdatedUser("ICOADMIN");
    return entity;
  }

  private static void setBaseEntityFields(BaseEntity entity, String companyCode) {
    entity.setCompanyCode(companyCode);
    entity.setLastUpdatedTime(Timestamp.from(Instant.now()));
    entity.setLastUpdatedUser("ICO");
  }
}
