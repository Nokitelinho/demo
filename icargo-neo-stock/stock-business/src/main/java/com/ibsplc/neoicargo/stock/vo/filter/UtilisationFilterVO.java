package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UtilisationFilterVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private int airlineIdentifier;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private int stockIntroductionPeriod;
  private String currentDate;
  private List<RangeFilterVO> ranges;
}
