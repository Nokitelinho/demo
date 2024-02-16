package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
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
public class StockHolderModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String stockHolderCode;
  private StockHolderType stockHolderType;
  private String stockHolderName;
  private String description;
  private String controlPrivilege;
  private String stockHolderContactDetails;
  private Collection<StockModel> stock;
  private String operationFlag;
  private String lastUpdateUser;
  private LocalDate lastUpdateTime;
}
