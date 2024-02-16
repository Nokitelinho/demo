package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RangeFilterVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private Integer airlineIdentifier;
  private String documentType;
  private String documentSubType;
  private String stockHolderCode;
  private String status;
  private String startRange;
  private String endRange;
  private long asciiStartRange;
  private long asciiEndRange;
  private String numberOfDocuments;
  private String manualFlag;
  private Timestamp txnDat;
}
