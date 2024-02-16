package com.ibsplc.neoicargo.stock.util.mock;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENTS_LISTENER_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_GROUP;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_GROUP;
import static org.mockito.Mockito.mock;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditEntryType;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditFieldVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.ChangeGroupDetails;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEventList;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEventPublisher;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderPriorityVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestFilterVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.TransitStockVO;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

public class MockVOGenerator {
  private static final String COMPANY_CODE = "AV";
  private static final String ICO_ADMIN = "ICOADMIN";

  @NotNull
  public static StockHolderVO getMockFullStockHolderVO() {
    var stockHolderVO = getMockStockHolderVO("IBS", "HQ");

    var stockVO1 = getMockStockVO(stockHolderVO, 123, "dt", "dsp");
    var stockVO2 = getMockStockVO(stockHolderVO, 124, "dt", "dsp");

    setRangeToStockVO(stockVO1);
    setRangeToStockVO(stockVO1);
    setRangeToStockVO(stockVO2);

    return stockHolderVO;
  }

  @NotNull
  public static StockHolderVO getMockStockHolderVO(String companyCode, String stockHolderCode) {
    return StockHolderVO.builder()
        .companyCode(companyCode)
        .stockHolderCode(stockHolderCode)
        .stockHolderType(StockHolderType.A)
        .stockHolderName("N1")
        .controlPrivilege("CP1")
        .lastUpdateUser("ICO")
        .lastUpdateTime(ZonedDateTime.now())
        .build();
  }

  @NotNull
  public static StockVO getMockStockVO(
      StockHolderVO stockHolderVO, int airlineId, String docType, String docSubType) {
    var stockVO =
        StockVO.builder()
            .companyCode(stockHolderVO.getCompanyCode())
            .stockHolderCode(stockHolderVO.getStockHolderCode())
            .airlineIdentifier(airlineId)
            .documentType(docType)
            .documentSubType(docSubType)
            .isAutoPopulateFlag(true)
            .isReorderAlertFlag(true)
            .isAutoRequestFlag(true)
            .lastUpdateUser("ICO")
            .lastUpdateTime(ZonedDateTime.now())
            .build();

    if (Objects.isNull(stockHolderVO.getStock())) {
      stockHolderVO.setStock(new HashSet<>());
    }
    stockHolderVO.getStock().add(stockVO);

    return stockVO;
  }

  @NotNull
  private static void setRangeToStockVO(StockVO stockVO) {
    var rangeVO =
        RangeVO.builder()
            .stockHolderCode(stockVO.getStockHolderCode())
            .airlineIdentifier(stockVO.getAirlineIdentifier())
            .documentType(stockVO.getDocumentType())
            .documentSubType(stockVO.getDocumentSubType())
            .stockAcceptanceDate(ZonedDateTime.now())
            .startRange("101")
            .endRange("111")
            .isManual(true)
            .build();

    if (Objects.isNull(stockVO.getRanges())) {
      stockVO.setRanges(new HashSet<>());
    }
    stockVO.getRanges().add(rangeVO);
  }

  @NotNull
  public static DocumentFilterVO getMockDocumentFilterVO() {
    return DocumentFilterVO.builder()
        .airlineIdentifier(1134)
        .customerCode("DHLCDG")
        .documentType("AWB")
        .mstdocnum("09999000")
        .prefix("134")
        .build();
  }

  @NotNull
  public static DocumentVO getMockDocumentVO() {
    var documentVO = new DocumentVO();
    documentVO.setDocumentType("AWB");
    documentVO.setCompanyCode("IBS");
    documentVO.setDocumentSubType("S");
    documentVO.setRange(Set.of(getMockSharedRangeVO(), getMockSharedRangeVO()));
    return documentVO;
  }

  @NotNull
  public static SharedRangeVO getMockSharedRangeVO() {
    var sharedRangeVO = new SharedRangeVO();
    sharedRangeVO.setToRange("99999");
    sharedRangeVO.setFromrange("0");
    sharedRangeVO.setRangeDate(null);
    return sharedRangeVO;
  }

  @NotNull
  public static StockAllocationVO getMockStockAllocationVO() {
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setAllocate(true);
    stockAllocationVO.setAllocatedStock(1);
    stockAllocationVO.setDocumentSubType("S");
    stockAllocationVO.setDocumentSubType("AWB");
    stockAllocationVO.setCompanyCode("IBS");
    stockAllocationVO.setRanges(List.of(getMockRangeVO("IBS", "AWB", "S", 0L, 99999L)));
    return stockAllocationVO;
  }

  @NotNull
  public static RangeVO getMockRangeVO(
      String companyCode,
      String docType,
      String docSubType,
      long asciiStartRange,
      long asciiEndRange) {
    var rangeVO = new RangeVO();
    rangeVO.setDocumentSubType(docSubType);
    rangeVO.setDocumentType(docType);
    rangeVO.setCompanyCode(companyCode);
    rangeVO.setStockHolderCode("HQ");
    rangeVO.setAirlineIdentifier(1172);
    rangeVO.setStartRange(Long.toString(asciiStartRange));
    rangeVO.setEndRange(Long.toString(asciiEndRange));
    rangeVO.setAsciiStartRange(asciiStartRange);
    rangeVO.setAsciiEndRange(asciiEndRange);
    rangeVO.setLastUpdateTime(ZonedDateTime.now());
    return rangeVO;
  }

  @NotNull
  public static RangeFilterVO getMockRangeFilterVO(RangeVO rangeVO) {
    return RangeFilterVO.builder()
        .companyCode(rangeVO.getCompanyCode())
        .stockHolderCode(rangeVO.getStockHolderCode())
        .airlineIdentifier(rangeVO.getAirlineIdentifier())
        .documentType(rangeVO.getDocumentType())
        .documentSubType(rangeVO.getDocumentSubType())
        .asciiStartRange(rangeVO.getAsciiStartRange())
        .asciiEndRange(rangeVO.getAsciiEndRange())
        .build();
  }

  @NotNull
  public static StockFilterVO getStockFilterVO(
      boolean isViewRange,
      String manualFlag,
      String documentSubType,
      String documentType,
      int airlineIdentifier,
      String stockHolderCode,
      String companyCode) {
    return StockFilterVO.builder()
        .companyCode(companyCode)
        .stockHolderCode(stockHolderCode)
        .airlineIdentifier(airlineIdentifier)
        .documentType(documentType)
        .documentSubType(documentSubType)
        .isViewRange(isViewRange)
        .manualFlag(manualFlag)
        .build();
  }

  @NotNull
  public static StockRangeHistoryVO getMockStockRangeHistoryVO() {
    var stockRangeHistoryVO = new StockRangeHistoryVO();
    stockRangeHistoryVO.setStatus(MODE_USED);
    stockRangeHistoryVO.setAirlineIdentifier(1172);
    stockRangeHistoryVO.setDocumentSubType("S");
    stockRangeHistoryVO.setDocumentType("AWB");
    stockRangeHistoryVO.setFromStockHolderCode("HQ");
    stockRangeHistoryVO.setCompanyCode("IBS");
    stockRangeHistoryVO.setTransactionDate(ZonedDateTime.now());
    return stockRangeHistoryVO;
  }

  @NotNull
  public static StockHolderPriorityVO getMockStockHolderPriorityVO() {
    StockHolderPriorityVO vo = new StockHolderPriorityVO();
    vo.setPriority(1);
    vo.setCompanyCode("DNSG");
    vo.setStockHolderDescription("desc");
    vo.setStockHolderCode("HQ");
    vo.setStockHolderType("A");
    vo.setIgnoreWarnings(false);
    vo.setTriggerPoint("TP");
    vo.setOperationFlag("FLAG");
    return vo;
  }

  @NotNull
  public static AuditEventList getMockStockHolderAudit(String transaction) {
    var stockHolderVo = getMockStockHolderVO("AV", "HQ");
    var auditVO = getMockStockHolderAuditVO(stockHolderVo);

    var auditEvent = new AuditEvent(auditVO, STOCK_HOLDER_AUDIT_EVENT_NAME);
    auditEvent.setTransaction(transaction);
    return new AuditEventList(
        mock(AuditEventPublisher.class),
        List.of(auditEvent),
        Set.of(STOCK_HOLDER_AUDIT_EVENT_NAME),
        Set.of(STOCK_AUDIT_EVENTS_LISTENER_NAME));
  }

  @NotNull
  public static AuditEventList getMockStockAudit(String transaction) {
    var stockVo = getMockStockVO(getMockStockHolderVO("AV", "HQ"), 1134, "AWB", "S");
    var auditVO = getMockStockAuditVO(stockVo);

    var auditEvent = new AuditEvent(auditVO, STOCK_AUDIT_EVENT_NAME);
    auditEvent.setChangeGroupDetails(Map.of(STOCK_GROUP, List.of(getChangeGroupDetails())));
    auditEvent.setTransaction(transaction);
    return new AuditEventList(
        mock(AuditEventPublisher.class),
        List.of(auditEvent),
        Set.of(STOCK_AUDIT_EVENT_NAME),
        Set.of(STOCK_AUDIT_EVENTS_LISTENER_NAME));
  }

  private static ChangeGroupDetails getChangeGroupDetails() {
    final var changeGroupDetails = new ChangeGroupDetails();
    final AuditFieldVO auditFieldVO = getAuditFieldVO();
    changeGroupDetails.setFields(List.of(auditFieldVO));
    return changeGroupDetails;
  }

  private static AuditFieldVO getAuditFieldVO() {
    final var auditFieldVO = new AuditFieldVO();
    auditFieldVO.setFieldModified(true);
    auditFieldVO.setField("remarks");
    auditFieldVO.setOldValue("");
    auditFieldVO.setNewValue("updated");
    return auditFieldVO;
  }

  @NotNull
  public static AuditVO getMockStockAuditVO(StockVO stockVO) {
    var auditVO = new AuditVO();

    final var utcNow = ZonedDateTime.now(ZoneId.of("UTC"));
    auditVO.setActionDateTimeUTC(utcNow.toLocalDateTime());
    auditVO.setActionDateTimeStation(utcNow.toLocalDateTime());
    auditVO.setActionDateTime(Date.from(utcNow.toInstant()));

    auditVO.setActionCode("stock Updated");
    auditVO.setActionType(AuditEntryType.UPDATED);
    auditVO.setBusinessObject(stockVO);
    auditVO.setEntityId("Stock-" + stockVO.getBusinessId());

    auditVO.setLoginProfile(getLoginProfile());
    auditVO.setLoggedInUser(ICO_ADMIN);

    auditVO.setListener(STOCK_AUDIT_EVENTS_LISTENER_NAME);
    auditVO.setEventName(STOCK_AUDIT_EVENT_NAME);
    auditVO.setAuditGroup(STOCK_GROUP);

    return auditVO;
  }

  @NotNull
  public static AuditVO getMockStockHolderAuditVO(StockHolderVO stockHolderVO) {
    var auditVO = new AuditVO();

    final var utcNow = ZonedDateTime.now(ZoneId.of("UTC"));
    auditVO.setActionDateTimeUTC(utcNow.toLocalDateTime());
    auditVO.setActionDateTimeStation(utcNow.toLocalDateTime());
    auditVO.setActionDateTime(Date.from(utcNow.toInstant()));

    auditVO.setActionCode("stockHolder Updated");
    auditVO.setActionType(AuditEntryType.UPDATED);
    auditVO.setBusinessObject(stockHolderVO);
    auditVO.setEntityId("StockHolder-" + stockHolderVO.getBusinessId());

    auditVO.setLoginProfile(getLoginProfile());
    auditVO.setLoggedInUser(ICO_ADMIN);

    auditVO.setListener(STOCK_AUDIT_EVENTS_LISTENER_NAME);
    auditVO.setEventName(STOCK_HOLDER_AUDIT_EVENT_NAME);
    auditVO.setAuditGroup(STOCK_HOLDER_GROUP);

    return auditVO;
  }

  public static LoginProfile getLoginProfile() {
    var profile = new LoginProfile();
    profile.setCompanyCode(COMPANY_CODE);
    profile.setTimeZone("UTC");
    profile.setStationCode("CDG");
    profile.setStation_code("CDG");
    profile.setUserId(ICO_ADMIN);
    profile.setRoleGroupCode(ICO_ADMIN);
    profile.setOwnAirlineCode(COMPANY_CODE);

    return profile;
  }

  @NotNull
  public static StockRequestFilterVO getMockStockRequestFilterVO() {
    StockRequestFilterVO vo = new StockRequestFilterVO();
    vo.setCompanyCode("AV");
    vo.setStockHolderCode("HQ");
    vo.setPageSize(2);
    return vo;
  }

  @NotNull
  public static StockRequestVO getMockStockRequestVO() {
    StockRequestVO vo = new StockRequestVO();
    vo.setCompanyCode("AV");
    vo.setStockHolderCode("HQ");
    return vo;
  }

  @NotNull
  public static StockRequestVO getMockStockRequestVO(
      String companyCode,
      String airlineIdentifier,
      String approvalRemarks,
      String requestRefNumber) {
    StockRequestVO vo = new StockRequestVO();
    vo.setCompanyCode(companyCode);
    vo.setAirlineIdentifier(airlineIdentifier);
    vo.setApprovalRemarks(approvalRemarks);
    vo.setRequestRefNumber(requestRefNumber);
    vo.setStockHolderCode("HQ");
    vo.setDocumentType("AWB");
    vo.setDocumentSubType("S");
    return vo;
  }

  public static BlacklistStockVO getMockBlacklistStockVO() {
    var blacklistStockVO = new BlacklistStockVO();
    blacklistStockVO.setStockHolderCode("HQ");
    blacklistStockVO.setCompanyCode("AV");
    blacklistStockVO.setDocumentType("AWB");
    blacklistStockVO.setDocumentSubType("S");
    blacklistStockVO.setAirlineIdentifier(1172);
    blacklistStockVO.setRangeFrom("1000001");
    blacklistStockVO.setRangeTo("1000002");
    blacklistStockVO.setAsciiRangeFrom(1000001L);
    blacklistStockVO.setAsciiRangeTo(1000002L);
    return blacklistStockVO;
  }

  @NotNull
  public static TransitStockVO getMockTransitStockVO(
      String companyCode,
      String stockHolderCode,
      int airlineIdentifier,
      String documentType,
      String documentSubType) {
    return TransitStockVO.builder()
        .companyCode(companyCode)
        .stockHolderCode(stockHolderCode)
        .airlineIdentifier(airlineIdentifier)
        .documentType(documentType)
        .documentSubType(documentSubType)
        .missingStartRange("1000001")
        .missingEndRange("1000002")
        .confirmStatus("N")
        .build();
  }

  @NotNull
  public static BlacklistStockVO getMockBlacklistedStockVO() {
    return BlacklistStockVO.builder()
        .companyCode("AV")
        .airlineIdentifier(987987)
        .rangeFrom("098899")
        .rangeTo("098899")
        .documentSubType("S")
        .documentType("AWB")
        .stockHolderCode("HQ")
        .build();
  }
}
