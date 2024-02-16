package com.ibsplc.neoicargo.stock.component.feature.returndocumenttostock;

import com.ibsplc.neoicargo.stock.component.feature.savestockutilisation.SaveStockUtilisationFeature;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class ReturnDocumentToStockFeatureTest {

  @InjectMocks private ReturnDocumentToStockFeature returnDocumentToStockFeature;

  @Mock private SaveStockUtilisationFeature saveStockUtilisationFeature;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void shouldPerform1() {
    var stockAgentVO = new StockAgentVO();
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setCompanyCode("IBS");
    stockAllocationVO.setDocumentType("AWB");
    stockAllocationVO.setDocumentSubType("S");
    stockAllocationVO.setStockHolderCode("CARGOHLD");
    stockAgentVO.setStockHolderCode("CARGOHLD");
    Assertions.assertDoesNotThrow(() -> returnDocumentToStockFeature.perform(stockAllocationVO));
  }
}
