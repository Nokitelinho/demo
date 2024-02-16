package com.ibsplc.neoicargo.stock.component.feature.checkforblacklisteddocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class CheckForBlacklistedDocumentFeatureTest {

  @InjectMocks private CheckForBlacklistedDocumentFeature checkForBlacklistedDocumentFeature;

  @Mock private StockDao stockDao;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCheckForBlacklistedDocument() {
    doReturn(true).when(stockDao).checkForBlacklistedDocument("IBS", "AWB", "9000000", null);

    var actual = checkForBlacklistedDocumentFeature.perform("IBS", "AWB", "9000000");

    assertThat(actual).isTrue();
    verify(stockDao).checkForBlacklistedDocument("IBS", "AWB", "9000000", null);
  }
}
