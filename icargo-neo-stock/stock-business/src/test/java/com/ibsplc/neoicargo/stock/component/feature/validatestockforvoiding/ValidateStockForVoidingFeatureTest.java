package com.ibsplc.neoicargo.stock.component.feature.validatestockforvoiding;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.BlackListStockMapper;
import com.ibsplc.neoicargo.stock.mapper.TransitStockMapper;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import java.util.ArrayList;
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
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(JUnitPlatform.class)
class ValidateStockForVoidingFeatureTest {
  @InjectMocks private ValidateStockForVoidingFeature validateStockForVoidingFeature;
  @Mock private RangeDao rangeDao;
  @Mock private StockDao stockDao;

  @Spy
  private BlackListStockMapper blackListStockMapper = Mappers.getMapper(BlackListStockMapper.class);

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(
        blackListStockMapper, "transitStockMapper", Mappers.getMapper(TransitStockMapper.class));
  }

  private StockVO populateStockVO() {
    return StockVO.builder().companyCode("DE").build();
  }

  private StockAgentVO populateStockAgentVOs() {
    var vo = new StockAgentVO();
    vo.setAgentCode("DE-AGNT");

    return vo;
  }

  @Test
  void shouldReturnResults() {
    // Given
    var blacklistStockVO = new BlacklistStockVO();
    var stockVOs = List.of(populateStockVO());
    var stockAgentVOs = List.of(populateStockAgentVOs());

    // When
    doReturn(stockVOs).when(rangeDao).findBlacklistRanges(blacklistStockVO);
    doReturn(stockAgentVOs).when(stockDao).findStockAgentMappings(any(StockAgentFilterVO.class));

    // Then
    var result = validateStockForVoidingFeature.perform(blacklistStockVO);

    Assertions.assertThat(result.getAgentCode()).isEqualTo("DE-AGNT");
    Assertions.assertThat(result.getStockVO().getCompanyCode()).isEqualTo("DE");
    verify(stockDao).findStockAgentMappings(any(StockAgentFilterVO.class));
    verify(rangeDao).findBlacklistRanges(blacklistStockVO);
  }

  @Test
  void shouldReturnEmptyResult() {
    // Given
    var blacklistStockVO = new BlacklistStockVO();
    var stockVOs = new ArrayList<>();

    // When
    doReturn(stockVOs).when(rangeDao).findBlacklistRanges(blacklistStockVO);

    // Then
    var result = validateStockForVoidingFeature.perform(blacklistStockVO);
    Assertions.assertThat(result.getAgentCode()).isNull();
    Assertions.assertThat(result.getStockVO()).isNull();
    verify(rangeDao).findBlacklistRanges(blacklistStockVO);
  }
}
