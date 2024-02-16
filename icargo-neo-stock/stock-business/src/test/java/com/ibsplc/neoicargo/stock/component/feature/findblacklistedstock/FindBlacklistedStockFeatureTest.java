package com.ibsplc.neoicargo.stock.component.feature.findblacklistedstock;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockPage;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockBlacklistedStockModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockFilterModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockFilterVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockBlacklistedStockVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.BlackListStockMapper;
import com.ibsplc.neoicargo.stock.mapper.StockFilterMapper;
import com.ibsplc.neoicargo.stock.model.BlacklistStockModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockFilterModel;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.util.ArrayList;
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
class FindBlacklistedStockFeatureTest {

  @InjectMocks private FindBlacklistedStockFeature findBlacklistedStockFeature;

  @Mock private StockDao stockDao;

  @Mock private StockFilterMapper stockFilterMapper;

  @Mock private BlackListStockMapper blackListStockMapper;

  private PageRequest pageRequest;
  private StockFilterModel filterModel;
  private StockFilterVO filterVO;
  private BlacklistStockVO blacklistStockVO;
  private BlacklistStockModel blacklistStockModel;
  private List<BlacklistStockVO> blacklistStockVOS;
  private List<BlacklistStockModel> blacklistStockModels;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    pageRequest = PageRequest.of(0, DEFAULT_PAGE_SIZE);
    filterModel = getMockStockFilterModel();
    filterVO = getMockStockFilterVO();
    blacklistStockVO = getMockBlacklistedStockVO();
    blacklistStockModel = getMockBlacklistedStockModel();
    blacklistStockVOS = new ArrayList<>();
    blacklistStockVO.setTotalRecordCount(1);
    blacklistStockVOS.add(blacklistStockVO);
    blacklistStockModels = new ArrayList<>();
    blacklistStockModels.add(blacklistStockModel);
  }

  @Test
  void shouldReturnPageOfBlacklistedStock() {
    var expected = getMockPage(blacklistStockModels, 1, 1, 1);

    doReturn(filterVO).when(stockFilterMapper).mapModelToVo(filterModel);
    doReturn(blacklistStockVOS).when(stockDao).findBlacklistedStock(filterVO, pageRequest);
    doReturn(blacklistStockModels).when(blackListStockMapper).mapVoToModel(blacklistStockVOS);
    doReturn(expected)
        .when(blackListStockMapper)
        .mapVosToPageView(blacklistStockModels, 1, 1, 1, DEFAULT_PAGE_SIZE);

    var actual = findBlacklistedStockFeature.perform(filterModel, 1);

    verify(stockFilterMapper).mapModelToVo(filterModel);
    verify(stockDao).findBlacklistedStock(filterVO, pageRequest);
    verify(blackListStockMapper).mapVoToModel(blacklistStockVOS);
    verify(blackListStockMapper).mapVosToPageView(blacklistStockModels, 1, 1, 1, DEFAULT_PAGE_SIZE);

    assertThat(actual).isNotNull();
    assertThat(actual.getTotalRecordCount()).isEqualTo(expected.getTotalRecordCount());
  }
}
