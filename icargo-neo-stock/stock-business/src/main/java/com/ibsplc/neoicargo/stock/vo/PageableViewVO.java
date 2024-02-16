package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableViewVO extends AbstractVO {

  private int totalRecordCount;
}
