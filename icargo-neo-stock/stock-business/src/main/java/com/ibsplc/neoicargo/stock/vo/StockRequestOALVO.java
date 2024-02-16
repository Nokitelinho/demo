package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockRequestOALVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private Long stockRequestOALSerialNumber;
  private String companyCode;
  private String airportCode;
  private String documentType;
  private String documentSubType;
  private String modeOfCommunication;
  private String content;
  private String airlineCode;
  private String address;
  private String operationFlag;
  private int airlineIdentifier;
  private int serialNumber;
  private ZonedDateTime requestedDate;
  private ZonedDateTime actionTime;
  private boolean isRequestCompleted;
  private ZonedDateTime lastUpdateTime;
  private String lastUpdateUser;
}
