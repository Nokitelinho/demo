package com.ibsplc.neoicargo.stock.util.mock;

import static com.ibsplc.icargo.framework.util.time.LocalDateMapper.toGMTDate;
import static com.ibsplc.icargo.framework.util.time.LocalDateMapper.toLocalDate;

import com.ibsplc.neoicargo.stock.model.BlacklistStockModel;
import com.ibsplc.neoicargo.stock.model.DocumentFilter;
import com.ibsplc.neoicargo.stock.model.MonitorStockModel;
import com.ibsplc.neoicargo.stock.model.RangeModel;
import com.ibsplc.neoicargo.stock.model.StockAllocationModel;
import com.ibsplc.neoicargo.stock.model.StockHolderModel;
import com.ibsplc.neoicargo.stock.model.StockHolderPriorityModel;
import com.ibsplc.neoicargo.stock.model.StockRequestModel;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockRequestFilterModel;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderLovFilterVO;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

public class MockModelGenerator {

  @NotNull
  public static DocumentFilter getMockDocumentFilterModel() {
    return DocumentFilter.builder()
        .companyCode("IBS")
        .airlineIdentifier(1134)
        .customerCode("DHLCDG")
        .build();
  }

  @NotNull
  public static StockHolderModel getMockStockHolderModel(
      String companyCode, String stockHolderCode) {
    var stockHolderModel = new StockHolderModel();
    stockHolderModel.setCompanyCode(companyCode);
    stockHolderModel.setStockHolderCode(stockHolderCode);
    stockHolderModel.setStockHolderType(StockHolderType.A);
    stockHolderModel.setStockHolderName("N1");
    stockHolderModel.setControlPrivilege("CP1");
    return stockHolderModel;
  }

  @NotNull
  public static StockFilterModel getMockStockFilterModel() {
    var stockFilterModel = new StockFilterModel();
    stockFilterModel.setCompanyCode("AV");
    stockFilterModel.setAirlineIdentifier(1004);
    stockFilterModel.setStockHolderCode("ALLOCATE4");
    stockFilterModel.setStockHolderType("H");
    stockFilterModel.setDocumentType("AWB");
    stockFilterModel.setDocumentSubType("S");
    stockFilterModel.setPrivilegeLevelType("STKHLD");
    stockFilterModel.setPrivilegeRule("STK_HLDR_CODE");
    stockFilterModel.setPageNumber(1);
    return stockFilterModel;
  }

  @NotNull
  public static MonitorStockModel getMonitorStockModel(StockFilterModel stockFilterModel) {
    var monitorStockModel = new MonitorStockModel();
    monitorStockModel.setCompanyCode(stockFilterModel.getCompanyCode());
    monitorStockModel.setStockHolderCode(stockFilterModel.getStockHolderCode());
    monitorStockModel.setStockHolderType(stockFilterModel.getStockHolderType());
    monitorStockModel.setDocumentType(stockFilterModel.getDocumentType());
    monitorStockModel.setDocumentSubType(stockFilterModel.getDocumentSubType());
    return monitorStockModel;
  }

  @NotNull
  public static StockAllocationModel getMockStockAllocationModel() {
    var stockAllocationModel = new StockAllocationModel();
    stockAllocationModel.setAllocate(true);
    stockAllocationModel.setAllocatedStock(1);
    stockAllocationModel.setDocumentSubType("S");
    stockAllocationModel.setDocumentSubType("AWB");
    stockAllocationModel.setCompanyCode("IBS");
    stockAllocationModel.setCaptureDateUTC(toGMTDate(ZonedDateTime.now()));
    stockAllocationModel.setExecutionDate(toLocalDate(ZonedDateTime.now()));
    stockAllocationModel.setLastUpdateTime(toLocalDate(ZonedDateTime.now()));
    stockAllocationModel.setLastUpdateTimeForStockReq(toLocalDate(ZonedDateTime.now()));
    return stockAllocationModel;
  }

  @NotNull
  public static StockFilterVO getMockStockFilterVO() {
    var stockFilterVO = new StockFilterVO();
    stockFilterVO.setCompanyCode("AV");
    stockFilterVO.setAirlineIdentifier(1004);
    stockFilterVO.setStockHolderCode("ALLOCATE4");
    stockFilterVO.setStockHolderType("H");
    stockFilterVO.setDocumentType("AWB");
    stockFilterVO.setDocumentSubType("S");
    stockFilterVO.setPrivilegeLevelType("STKHLD");
    stockFilterVO.setPrivilegeRule("STK_HLDR_CODE");
    return stockFilterVO;
  }

  @NotNull
  public static StockFilterVO getMockStockFilterVO(
      String companyCode,
      String stockHolderCode,
      String stockHolderType,
      int airlineIdentifier,
      String manualFlag,
      String documentType,
      String documentSubType) {
    var stockFilterVO = new StockFilterVO();
    stockFilterVO.setCompanyCode(companyCode);
    stockFilterVO.setAirlineIdentifier(airlineIdentifier);
    stockFilterVO.setStockHolderCode(stockHolderCode);
    stockFilterVO.setStockHolderType(stockHolderType);
    stockFilterVO.setDocumentType(documentType);
    stockFilterVO.setDocumentSubType(documentSubType);
    stockFilterVO.setManualFlag(manualFlag);
    return stockFilterVO;
  }

  @NotNull
  public static StockHolderPriorityModel getMockStockHolderPriorityModel() {
    StockHolderPriorityModel model = new StockHolderPriorityModel();
    model.setPriority(1);
    model.setCompanyCode("DNSG");
    model.setStockHolderDescription("desc");
    model.setStockHolderCode("HQ");
    model.setStockHolderType("A");
    model.setIgnoreWarnings(false);
    model.setTriggerPoint("TP");
    model.setOperationFlag("FLAG");
    return model;
  }

  @NotNull
  public static RangeModel getMockRangeModel() {
    var rangeModel = new RangeModel();
    rangeModel.setCompanyCode("AV");
    rangeModel.setAirlineIdentifier(1004);
    rangeModel.setStockHolderCode("ALLOCATE4");
    rangeModel.setDocumentType("AWB");
    rangeModel.setDocumentSubType("S");
    rangeModel.setAsciiStartRange(0);
    rangeModel.setAsciiEndRange(1);
    return rangeModel;
  }

  @NotNull
  public static StockRequestFilterModel getMockStockRequestFilterModel() {
    StockRequestFilterModel model = new StockRequestFilterModel();
    model.setCompanyCode("AV");
    model.setStockHolderCode("HQ");
    model.setPageSize(2);
    return model;
  }

  @NotNull
  public static StockRequestModel getMockStockRequestModel() {
    StockRequestModel model = new StockRequestModel();
    model.setCompanyCode("AV");
    model.setStockHolderCode("HQ");
    return model;
  }

  @NotNull
  public static StockHolderLovFilterVO getMockStockHolderLovFilterVO() {
    var filterVO = new StockHolderLovFilterVO();
    filterVO.setCompanyCode("AV");
    filterVO.setStockHolderCode("ALLOCATE5");
    filterVO.setStockHolderType("H");
    filterVO.setDocumentType("AWB");
    filterVO.setDocumentSubType("S");
    filterVO.setStockHolderName("*QUARTER 2");
    filterVO.setRequestedBy(true);
    filterVO.setApproverCode("HQ");
    filterVO.setPageNumber(1);

    return filterVO;
  }

  @NotNull
  public static BlacklistStockModel getMockBlacklistedStockModel() {
    return BlacklistStockModel.builder()
        .companyCode("AV")
        .airlineIdentifier(987987)
        .rangeFrom("098899")
        .rangeTo("098899")
        .documentSubType("S")
        .documentType("AWB")
        .stockHolderCode("HQ")
        .build();
  }

  public static BlacklistStockModel getMockBlacklistStockModel() {
    var blacklistStockModel = new BlacklistStockModel();
    blacklistStockModel.setStockHolderCode("HQ");
    blacklistStockModel.setCompanyCode("AV");
    blacklistStockModel.setDocumentType("AWB");
    blacklistStockModel.setDocumentSubType("S");
    blacklistStockModel.setAirlineIdentifier(1172);
    blacklistStockModel.setRangeFrom("1000001");
    blacklistStockModel.setRangeTo("1000002");
    blacklistStockModel.setAsciiRangeFrom(1000001L);
    blacklistStockModel.setAsciiRangeTo(1000002L);
    return blacklistStockModel;
  }
}
