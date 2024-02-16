package com.ibsplc.neoicargo.stock.component.feature.findapprovercode;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class FindApproverCodeFeatureTest {

  @InjectMocks private FindApproverCodeFeature findApproverCodeFeature;

  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnStockHolderDetails() {
    // When
    doReturn("SAC").when(stockDao).findApproverCode("IBS", "HQ", "DT", "DST");

    // Then
    assertNotNull(findApproverCodeFeature.perform("IBS", "HQ", "DT", "DST"));
  }
}
