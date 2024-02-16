package com.ibsplc.neoicargo.stock.component.feature.findstockagentmappings;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockPage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockAgentMapper;
import com.ibsplc.neoicargo.stock.model.StockAgentModel;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class FindStockAgentMappingsFeatureTest {
  @InjectMocks private FindStockAgentMappingsFeature findStockAgentMappingsFeature;
  @Mock private StockDao stockDao;
  @Mock private StockAgentMapper stockAgentMapper;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldFindStockAgentMappings() {
    // Given
    var model = new StockAgentModel();
    model.setCompanyCode("AV");
    model.setAgentCode("AGNT");
    model.setStockHolderCode("HQ");

    var page = getMockPage(List.of(model), 1, 2, 1);
    var stockAgentVOs = List.of(new StockAgentVO());
    var stockAgentModels = List.of(new StockAgentModel());
    var stockAgentFilterVO = new StockAgentFilterVO();

    // When
    doReturn(stockAgentVOs).when(stockDao).findStockAgentMappings(stockAgentFilterVO);
    doReturn(stockAgentModels).when(stockAgentMapper).mapVoToModel(stockAgentVOs);
    doReturn(page)
        .when(stockAgentMapper)
        .mapVosToPageView(stockAgentModels, 0, 1, 0, DEFAULT_PAGE_SIZE);

    var actual = findStockAgentMappingsFeature.perform(stockAgentFilterVO);

    // Then
    verify(stockDao).findStockAgentMappings(stockAgentFilterVO);
    verify(stockAgentMapper).mapVoToModel(stockAgentVOs);
    verify(stockAgentMapper).mapVosToPageView(stockAgentModels, 0, 1, 0, DEFAULT_PAGE_SIZE);

    assertThat(actual.getTotalRecordCount()).isEqualTo(page.getTotalRecordCount());
  }
}
