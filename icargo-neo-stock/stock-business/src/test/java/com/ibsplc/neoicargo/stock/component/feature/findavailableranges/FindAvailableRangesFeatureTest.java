package com.ibsplc.neoicargo.stock.component.feature.findavailableranges;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockPage;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockRangeModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockFilterModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockFilterVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockRangeVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.mapper.StockFilterMapper;
import com.ibsplc.neoicargo.stock.model.RangeModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockFilterModel;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

@RunWith(JUnitPlatform.class)
class FindAvailableRangesFeatureTest {

  @InjectMocks private FindAvailableRangesFeature findAvailableRangesFeature;

  @Mock private StockDao stockDao;

  @Mock private RangeMapper rangeMapper;

  @Mock private StockFilterMapper stockFilterMapper;

  private PageRequest pageRequest;
  private StockFilterModel filterModel;
  private StockFilterVO filterVO;
  private RangeVO rangeVO;
  private RangeModel rangeModel;
  private List<RangeVO> availableRangesVOS;
  private List<RangeModel> availableRangesModels;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    pageRequest = PageRequest.of(0, DEFAULT_PAGE_SIZE);
    filterModel = getMockStockFilterModel();
    filterVO = getMockStockFilterVO();
    rangeVO = getMockRangeVO("AV", "AWB", "S", 0, 1);
    rangeVO.setTotalRecordCount(1);
    rangeModel = getMockRangeModel();
  }

  @Test
  void shouldReturnPageOfAvailableRanges() {
    Page<RangeModel> page = (Page<RangeModel>) getMockPage(availableRangesModels, 1, 25, 1);
    availableRangesVOS = List.of(rangeVO);
    availableRangesModels = List.of(rangeModel);

    // When
    doReturn(filterVO).when(stockFilterMapper).mapModelToVo(filterModel);
    doReturn(availableRangesVOS).when(stockDao).findAvailableRanges(filterVO, pageRequest);
    doReturn(availableRangesModels).when(rangeMapper).mapVoToModel(List.of(rangeVO));
    doReturn(page)
        .when(rangeMapper)
        .mapVosToPageView(availableRangesModels, 1, 1, 1, DEFAULT_PAGE_SIZE);

    var actual = findAvailableRangesFeature.perform(filterModel);

    // Then
    verify(stockFilterMapper).mapModelToVo(filterModel);
    verify(stockDao).findAvailableRanges(filterVO, pageRequest);
    verify(rangeMapper).mapVoToModel(List.of(rangeVO));
    verify(rangeMapper).mapVosToPageView(availableRangesModels, 1, 1, 1, DEFAULT_PAGE_SIZE);

    assertThat(actual.getTotalRecordCount()).isEqualTo(page.getTotalRecordCount());
  }
}
