package com.ibsplc.neoicargo.stock.model.viewfilter;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 201310
 */
@Setter
@Getter
public class StockHolderFilterModel extends BaseModel {
  private String companyCode;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private String stockHolderType;
  private int pageNumber;
  private String airlineIdentifier;
}
