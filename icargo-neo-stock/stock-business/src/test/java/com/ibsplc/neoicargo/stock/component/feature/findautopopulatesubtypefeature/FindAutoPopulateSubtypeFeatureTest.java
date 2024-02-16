package com.ibsplc.neoicargo.stock.component.feature.findautopopulatesubtypefeature;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class FindAutoPopulateSubtypeFeatureTest {
  @InjectMocks private FindAutoPopulateSubtypeFeature findAutoPopulateSubtypeFeature;

  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void shouldfindStockHolderCodeForAgent() {
    var stkAgentMapping = new StockAgentVO();
    var documentFilterVO = new DocumentFilterVO();

    documentFilterVO.setCompanyCode("IBS");
    documentFilterVO.setStockOwner("A1001");
    stkAgentMapping.setStockHolderCode("STKHLD");
    doReturn(stkAgentMapping).when(stockDao).findStockAgent(any(String.class), any(String.class));
    Assertions.assertDoesNotThrow(
        () -> findAutoPopulateSubtypeFeature.findStockHolderCodeForAgent("IBS", "A1001"));
    assertEquals("STKHLD", stkAgentMapping.getStockHolderCode());
    Assertions.assertDoesNotThrow(() -> findAutoPopulateSubtypeFeature.perform(documentFilterVO));
  }

  @Test
  public void shouldfindStockHolderCodeForAgentWhenNull() {
    StockAgentVO stkAgentMapping = null;
    doReturn(null).when(stockDao).findStockAgent(any(String.class), any(String.class));
    Assertions.assertDoesNotThrow(
        () -> findAutoPopulateSubtypeFeature.findStockHolderCodeForAgent("IBS", "D1001"));
    assertNull(stkAgentMapping);
  }
}
