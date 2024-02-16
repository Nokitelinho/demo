package com.ibsplc.neoicargo.stock.mapper;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockPage;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockRequestModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestVO;
import static org.assertj.core.api.Assertions.assertThat;

import com.ibsplc.neoicargo.stock.model.StockRequestModel;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
class StockRequestMapperTest {

  @Spy private StockRequestMapper stockRequestMapper = Mappers.getMapper(StockRequestMapper.class);

  private Page<StockRequestModel> viewPageInfo;
  private static List<StockRequestVO> stockRequestVOS;
  private static List<StockRequestModel> stockRequestModels;
  private StockRequestVO stockRequestVO;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    stockRequestVO = getMockStockRequestVO();
    stockRequestModels = List.of(new StockRequestModel());
    stockRequestVOS = List.of(stockRequestVO);
    viewPageInfo =
        (Page<StockRequestModel>) getMockPage(stockRequestModels, 1, 2, stockRequestVOS.size());
  }

  @Test
  void shouldMapModelToPageView() {
    // Given
    var stockRequestModels = new ArrayList<StockRequestModel>();
    stockRequestModels.add(getMockStockRequestModel());
    stockRequestModels.add(getMockStockRequestModel());

    var stockRequestVos = new ArrayList<StockRequestVO>();
    stockRequestVos.add(stockRequestMapper.mapModelToVo(getMockStockRequestModel()));
    stockRequestVos.add(stockRequestMapper.mapModelToVo(getMockStockRequestModel()));

    // When
    Page<StockRequestModel> actual =
        stockRequestMapper.mapVosToPageView(
            stockRequestModels, 1, 2, stockRequestVos.size(), DEFAULT_PAGE_SIZE);

    // Then
    assertThat(actual.getPageNumber()).isEqualTo(viewPageInfo.getPageNumber());
    assertThat(actual.getActualPageSize()).isEqualTo(viewPageInfo.getActualPageSize());
    assertThat(actual.getStartIndex()).isEqualTo(1);
    assertThat(actual.getEndIndex()).isEqualTo(25);
  }
}
