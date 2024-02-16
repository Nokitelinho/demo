package com.ibsplc.neoicargo.stock.component.feature.findstockrequestdetails;

import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockRequestModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestFilterVO;
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
class FindStockRequestDetailsFeatureTest {

  @InjectMocks private FindStockRequestDetailsFeature findStockRequestDetailsFeature;

  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldFindStockRequestDetails() {
    // Given
    var expected = getMockStockRequestModel();
    var filterVO = getMockStockRequestFilterVO();

    // When
    doReturn(expected).when(stockDao).findStockRequestDetails(filterVO);

    // Then
    var actual = findStockRequestDetailsFeature.perform(filterVO);
    assertThat(actual).isEqualTo(expected);
    verify(stockDao).findStockRequestDetails(filterVO);
  }
}
