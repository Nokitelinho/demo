package com.ibsplc.neoicargo.stock.vo;

import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
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
public class StockHolderVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private Long stockHolderSerialNumber;
  private String companyCode;
  private String stockHolderCode;
  private StockHolderType stockHolderType;
  private String stockHolderName;
  private String description;
  private String controlPrivilege;
  private String stockHolderContactDetails;
  private Collection<StockVO> stock;
  private String lastUpdateUser;
  private ZonedDateTime lastUpdateTime;
  private String operationFlag;

  // not used fields
  private boolean ignoreWarnings;
  private String triggerPoint;

  @Override
  public String getBusinessId() {
    return companyCode + HYPHEN + stockHolderCode;
  }
}
