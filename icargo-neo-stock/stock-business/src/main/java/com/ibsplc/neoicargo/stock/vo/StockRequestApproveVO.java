package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockRequestApproveVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String approverCode;
  private String documentType;
  private String documentSubType;
  private Collection<StockRequestVO> stockRequests;
  private boolean isStockAvailableCheck;
  private LocalDate lastUpdateTime;
}
