package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1366
 */
@Setter
@Getter
@Builder
public class DuplicateRangeFilterVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private int airlineIdentifier;
  private String documentType;
  private String startRange;
  private String endRange;
}
