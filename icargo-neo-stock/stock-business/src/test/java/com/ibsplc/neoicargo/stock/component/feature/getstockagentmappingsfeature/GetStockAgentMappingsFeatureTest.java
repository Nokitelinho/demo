package com.ibsplc.neoicargo.stock.component.feature.getstockagentmappingsfeature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockAgentNeoModelMapper;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
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
public class GetStockAgentMappingsFeatureTest {

  @InjectMocks private GetStockAgentMappingsFeature getStockAgentMappingsFeature;
  @Mock private StockDao stockDao;

  @Spy
  private final StockAgentNeoModelMapper mapper = Mappers.getMapper(StockAgentNeoModelMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldGetStockAgentMappings() {
    // Given
    var agentVO = new StockAgentVO();
    agentVO.setCompanyCode("AV");
    agentVO.setAgentCode("AGNT");
    agentVO.setStockHolderCode("HQ");

    var stockAgentVOs = List.of(agentVO);
    var stockAgentFilterVO = new StockAgentFilterVO();

    // When
    doReturn(stockAgentVOs).when(stockDao).getStockAgentMappings(stockAgentFilterVO);

    var actual = getStockAgentMappingsFeature.perform(stockAgentFilterVO);

    // Then
    verify(stockDao).getStockAgentMappings(stockAgentFilterVO);
    verify(mapper).mapVoToModel(stockAgentVOs);
    assertThat(actual).hasSize(1);
  }
}
