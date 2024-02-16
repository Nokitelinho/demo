package com.ibsplc.neoicargo.stock.component.feature.returndocumenttostock;

import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_DELETE;

import com.ibsplc.neoicargo.stock.component.feature.savestockutilisation.SaveStockUtilisationFeature;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("returnDocumentToStockFeature")
@Slf4j
@RequiredArgsConstructor
public class ReturnDocumentToStockFeature {

  private final SaveStockUtilisationFeature saveStockUtilisationFeature;

  public void perform(StockAllocationVO stockAllocationVO) {

    saveStockUtilisationFeature.perform(stockAllocationVO, STATUS_DELETE);
  }
}
