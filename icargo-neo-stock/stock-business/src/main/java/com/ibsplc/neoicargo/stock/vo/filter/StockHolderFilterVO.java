package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 201310
 */
@Setter
@Getter
public class StockHolderFilterVO extends AbstractVO {
  /** company code */
  private String companyCode;
  /** stock holder code */
  private String stockHolderCode;
  /** document type */
  private String documentType;
  /** document sub type */
  private String documentSubType;
  /** stock holder type */
  private String stockHolderType;

  private int pageNumber;
  private String airlineIdentifier;
}
