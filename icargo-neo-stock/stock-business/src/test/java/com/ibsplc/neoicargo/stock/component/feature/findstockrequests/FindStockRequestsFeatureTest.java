package com.ibsplc.neoicargo.stock.component.feature.findstockrequests;

import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockPage;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockRequestFilterModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockRequestModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestFilterVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockRequestMapper;
import com.ibsplc.neoicargo.stock.model.StockRequestModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockRequestFilterModel;
import com.ibsplc.neoicargo.stock.vo.StockRequestFilterVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.ArrayList;
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
import org.springframework.data.domain.PageRequest;

@RunWith(JUnitPlatform.class)
class FindStockRequestsFeatureTest {

  @InjectMocks private FindStockRequestsFeature findStockRequestsFeature;

  @Mock private StockDao stockDao;
  @Spy private StockRequestMapper stockRequestMapper = Mappers.getMapper(StockRequestMapper.class);

  private PageRequest pageRequest;
  private StockRequestFilterModel filterModel;
  private StockRequestFilterVO filterVO;
  private StockRequestVO stockRequestVO;
  private StockRequestModel stockRequestModel;
  private List<StockRequestVO> stockRequestVOS;
  private List<StockRequestModel> stockRequestModels;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    pageRequest = PageRequest.of(0, 2);
    filterModel = getMockStockRequestFilterModel();
    filterVO = getMockStockRequestFilterVO();
    stockRequestVO = getMockStockRequestVO();
    stockRequestVO.setTotalRecordCount(1);
    stockRequestModel = getMockStockRequestModel();
  }

  @Test
  void shouldFindStockRequests() {
    Page<StockRequestModel> page =
        (Page<StockRequestModel>) getMockPage(List.of(getMockStockRequestModel()), 1, 2, 1);
    stockRequestVOS = List.of(stockRequestVO);
    stockRequestModels = List.of(stockRequestModel);

    // When
    doReturn(filterVO).when(stockRequestMapper).mapModelToVo(filterModel);
    doReturn(stockRequestVOS).when(stockDao).findStockRequests(filterVO, pageRequest);
    doReturn(stockRequestModels).when(stockRequestMapper).mapVoToModel(stockRequestVOS);

    var actual = findStockRequestsFeature.perform(1, filterModel);

    // Then
    verify(stockRequestMapper).mapModelToVo(filterModel);
    verify(stockDao).findStockRequests(filterVO, pageRequest);
    verify(stockRequestMapper).mapVoToModel(stockRequestVOS);
    verify(stockRequestMapper).mapVosToPageView(stockRequestModels, 1, 1, 1, 2);

    assertThat(actual.getTotalRecordCount()).isEqualTo(page.getTotalRecordCount());
  }

  @Test
  void shouldFindStockRequestsWithDefaultPageSize() {
    Page<StockRequestModel> page =
        (Page<StockRequestModel>) getMockPage(List.of(getMockStockRequestModel()), 1, 2, 1);
    stockRequestVOS = List.of(stockRequestVO);
    stockRequestModels = List.of(stockRequestModel);
    filterModel.setPageSize(0);
    pageRequest = PageRequest.of(0, 25);

    // When
    doReturn(filterVO).when(stockRequestMapper).mapModelToVo(filterModel);
    doReturn(stockRequestVOS).when(stockDao).findStockRequests(filterVO, pageRequest);
    doReturn(stockRequestModels).when(stockRequestMapper).mapVoToModel(stockRequestVOS);

    var actual = findStockRequestsFeature.perform(1, filterModel);

    // Then
    verify(stockRequestMapper).mapModelToVo(filterModel);
    verify(stockDao).findStockRequests(filterVO, pageRequest);
    verify(stockRequestMapper).mapVoToModel(stockRequestVOS);
    verify(stockRequestMapper).mapVosToPageView(stockRequestModels, 1, 1, 1, 25);

    assertThat(actual.getTotalRecordCount()).isEqualTo(page.getTotalRecordCount());
  }

  @Test
  void shouldFindStockRequestsEmptyPage() {
    Page<StockRequestModel> page =
        (Page<StockRequestModel>) getMockPage(List.of(getMockStockRequestModel()), 1, 2, 0);
    stockRequestVOS = new ArrayList<>();
    stockRequestModels = new ArrayList<>();

    // When
    doReturn(filterVO).when(stockRequestMapper).mapModelToVo(filterModel);
    doReturn(stockRequestVOS).when(stockDao).findStockRequests(filterVO, pageRequest);
    doReturn(stockRequestModels).when(stockRequestMapper).mapVoToModel(stockRequestVOS);

    var actual = findStockRequestsFeature.perform(1, filterModel);

    // Then
    verify(stockRequestMapper).mapModelToVo(filterModel);
    verify(stockDao).findStockRequests(filterVO, pageRequest);
    verify(stockRequestMapper).mapVoToModel(stockRequestVOS);
    verify(stockRequestMapper).mapVosToPageView(stockRequestModels, 1, 0, 0, 2);

    assertThat(actual.getTotalRecordCount()).isEqualTo(page.getTotalRecordCount());
  }
}
