package com.ibsplc.neoicargo.stock.vo;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RangeVO extends PageableViewVO {
  private static final long serialVersionUID = 1L;
  private Long rangeSerialNumber;
  private String companyCode;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private int airlineIdentifier;
  private String startRange;
  private String awbPrefix;
  private String endRange;
  private long asciiStartRange;
  private long asciiEndRange;
  private long numberOfDocuments;
  private String operationFlag;
  private boolean isBlackList;
  private boolean isManual;
  private ZonedDateTime lastUpdateTime;
  private String lastUpdateUser;
  private String stockHolderName;
  private String avlStartRange;
  private String avlEndRange;
  private long avlNumberOfDocuments;
  private String allocStartRange;
  private String allocEndRange;
  private long allocNumberOfDocuments;
  private String fromStockHolderCode;
  private String usedStartRange;
  private String usedEndRange;
  private String allocatedRange;
  private String availableRange;
  private String usedRange;
  private long usedNumberOfDocuments;
  private ZonedDateTime stockAcceptanceDate;
  private Collection<String> masterDocumentNumbers;
  private LocalDate transactionDate;
  private int awbCheckDigit;

  // not used fields
  private boolean ignoreWarnings;
  private String triggerPoint;
}
