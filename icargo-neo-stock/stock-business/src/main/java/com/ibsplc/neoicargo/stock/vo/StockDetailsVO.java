package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author A-3184
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDetailsVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private Long stockHolderStockDetailSerialNumber;
  private String companyCode;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private long openingBalance;
  private long receivedStock;
  private long allocatedStock;
  private long transferredStock;
  private long returnStock;
  private long returnUtilisedStock;
  private long blackListedStock;
  private long utilisedStock;
  private long closingBalance;
  private ZonedDateTime transactionDate;
  private ZonedDateTime txnDateUTC;
  private ZonedDateTime lastUpdateTimeUTC;
  private ZonedDateTime lastUpdateTime;
  private String lastUpdateUser;
  private int airlineId;
  private String approverCode;
  private String stockApproverName;
  private String stockHolderName;
  private String avlStartRange;
  private String avlEndRange;
  private long avlNumberDocuments;
  private String allocStartRange;
  private String allocEndRange;
  private long allocNumberDocuments;
  private String usedStartRange;
  private String usedEndRange;
  private long usedNumberDocuments;
  private String awbPrefix;
  private long totalStockAvailed;
  private long requestReceived;
  private long requestPlaced;
  private long availableStock;
  private String allocatedTo;
  private long usedStock;
  private String stockHolderType;
  private List<RangeVO> customerRanges;
  private long returnedStock;
  private long utilizedStock;
  private long returnedUtilizedStock;
  private long availableBalance;
}
