package com.ibsplc.neoicargo.stock.vo;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockAgentVO extends PageableViewVO {
  private static final long serialVersionUID = 1L;
  private String triggerPoint;
  private Long stockAgentSerialNumber;
  private String companyCode;
  private String agentCode;
  private String stockHolderCode;
  private String lastUpdateUser;
  private ZonedDateTime lastUpdateTime;
  private String operationFlag;
  private int pageNumber;
}
