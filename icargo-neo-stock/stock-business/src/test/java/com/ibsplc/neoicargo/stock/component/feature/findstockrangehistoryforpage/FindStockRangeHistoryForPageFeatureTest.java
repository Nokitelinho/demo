package com.ibsplc.neoicargo.stock.component.feature.findstockrangehistoryforpage;

import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockPage;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.CQRSDao;
import com.ibsplc.neoicargo.stock.mapper.StockRangeHistoryMapper;
import com.ibsplc.neoicargo.stock.model.StockRangeHistoryModel;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.PageRequest;

@RunWith(JUnitPlatform.class)
class FindStockRangeHistoryForPageFeatureTest {
  @InjectMocks private FindStockRangeHistoryForPageFeature findStockRangeHistoryForPageFeature;

  @Mock private CQRSDao cqrsDao;

  @Spy
  private final StockRangeHistoryMapper stockRangeHistoryMapper =
      Mappers.getMapper(StockRangeHistoryMapper.class);

  private PageRequest pageRequest;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    pageRequest = PageRequest.of(0, 25);
  }

  private StockRangeHistoryVO populateStockRangeHistoryVO() {
    var stockRangeHistoryVO = new StockRangeHistoryVO();
    stockRangeHistoryVO.setTotalRecordCount(1);
    return stockRangeHistoryVO;
  }

  @Test
  void shouldFindStockRangeHistoryForPage() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setPageNumber(1);

    var stockRangeHistoryVOs = List.of(populateStockRangeHistoryVO());

    var page = getMockPage(List.of(new StockRangeHistoryModel()), 1, 1, 1);

    // When
    doReturn(stockRangeHistoryVOs)
        .when(cqrsDao)
        .findStockRangeHistoryForPage(filterVO, pageRequest);

    var result = findStockRangeHistoryForPageFeature.perform(filterVO);

    // Then
    verify(cqrsDao).findStockRangeHistoryForPage(filterVO, pageRequest);

    Assertions.assertThat(result.getTotalRecordCount()).isEqualTo(page.getTotalRecordCount());
  }

  @Test
  void shouldFindStockRangeHistoryForPageWithPageSize() {
    // Given
    var filterVO = new StockRangeFilterVO();
    filterVO.setPageNumber(1);
    filterVO.setPageSize(25);

    var stockRangeHistoryVOs = List.of(populateStockRangeHistoryVO());

    var page = getMockPage(List.of(new StockRangeHistoryModel()), 1, 1, 1);

    // When
    doReturn(stockRangeHistoryVOs)
        .when(cqrsDao)
        .findStockRangeHistoryForPage(filterVO, pageRequest);

    var result = findStockRangeHistoryForPageFeature.perform(filterVO);

    // Then
    verify(cqrsDao).findStockRangeHistoryForPage(filterVO, pageRequest);

    Assertions.assertThat(result.getTotalRecordCount()).isEqualTo(page.getTotalRecordCount());
    Assertions.assertThat(result.getDefaultPageSize()).isEqualTo(25);
  }
}
