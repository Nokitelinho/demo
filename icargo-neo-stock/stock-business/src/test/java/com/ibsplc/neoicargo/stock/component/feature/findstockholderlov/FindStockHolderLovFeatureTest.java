package com.ibsplc.neoicargo.stock.component.feature.findstockholderlov;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockHolderLovMapper;
import com.ibsplc.neoicargo.stock.model.StockHolderLovModel;
import com.ibsplc.neoicargo.stock.vo.StockHolderLovVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderLovFilterVO;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
class FindStockHolderLovFeatureTest {
  @InjectMocks private FindStockHolderLovFeature findStockHolderLovFeature;
  @Mock private StockDao stockDao;

  @Spy
  private final StockHolderLovMapper stockHolderLovMapper =
      Mappers.getMapper(StockHolderLovMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnPageViewOfStockHolderLovModel() {
    // Given
    var stockHolderLovVO = new StockHolderLovVO();
    stockHolderLovVO.setTotalRecordCount(1);

    // When
    doReturn(List.of(new StockHolderLovModel())).when(stockHolderLovMapper).mapVoToModel(anyList());
    doReturn(List.of(stockHolderLovVO))
        .when(stockDao)
        .findStockHolderLov(any(StockHolderLovFilterVO.class));
    var result = findStockHolderLovFeature.perform(new StockHolderLovFilterVO());

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockDao).findStockHolderLov(any(StockHolderLovFilterVO.class));
  }

  @Test
  void shouldReturnPageViewWithNoResults() {
    // Given
    var stockHolderLovVOs = List.of(new StockHolderLovVO());

    // When
    doReturn(stockHolderLovVOs)
        .when(stockDao)
        .findStockHolderLov(any(StockHolderLovFilterVO.class));
    var result = findStockHolderLovFeature.perform(new StockHolderLovFilterVO());

    // Then
    assertThat(result.getTotalRecordCount()).isZero();
    verify(stockDao).findStockHolderLov(any(StockHolderLovFilterVO.class));
  }
}
