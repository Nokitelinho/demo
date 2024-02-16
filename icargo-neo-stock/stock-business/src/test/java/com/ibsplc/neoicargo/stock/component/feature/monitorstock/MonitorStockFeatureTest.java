package com.ibsplc.neoicargo.stock.component.feature.monitorstock;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.MonitorStockMapper;
import com.ibsplc.neoicargo.stock.vo.MonitorStockVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
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

@RunWith(JUnitPlatform.class)
class MonitorStockFeatureTest {
  @InjectMocks private MonitorStockFeature monitorStockFeature;
  @Mock private StockDao stockDao;
  @Spy private MonitorStockMapper monitorStockMapper = Mappers.getMapper(MonitorStockMapper.class);

  private StockFilterVO stockfilterVO;

  @BeforeEach
  public void setup() {
    stockfilterVO = populateStockFilterVO();
    MockitoAnnotations.openMocks(this);
  }

  private StockFilterVO populateStockFilterVO() {
    var stockFilterVO = new StockFilterVO();
    stockFilterVO.setCompanyCode("AVFMD");
    stockFilterVO.setAirlineIdentifier(1191);
    stockFilterVO.setStockHolderCode("HQ");
    stockFilterVO.setDocumentType("AWB");
    stockFilterVO.setDocumentSubType("S");
    stockFilterVO.setPageNumber(1);
    stockFilterVO.setAbsoluteIndex(0);
    stockFilterVO.setPrivilegeLevelType("TEST-level-type");
    stockFilterVO.setPrivilegeLevelValue(null);
    stockFilterVO.setPrivilegeRule("TEST-rule");

    return stockFilterVO;
  }

  private List<MonitorStockVO> populateMonitorStockListVOs(int numberElements, int totalRecords) {
    var monitorStockVOs = new ArrayList<MonitorStockVO>();
    for (int i = 0; i < numberElements; i++) {
      var monitorStockVO = new MonitorStockVO();
      monitorStockVO.setStockHolderCode("HQ" + i);
      monitorStockVO.setDocumentSubType("AWB");
      monitorStockVO.setDocumentSubType("S");
      monitorStockVOs.add(monitorStockVO);
      monitorStockVO.setTotalRecordCount(totalRecords);
    }

    return monitorStockVOs;
  }

  @Test
  void shouldReturnPageWithResults() {
    // Given
    var monitorStockVOs = populateMonitorStockListVOs(5, 5);

    // When
    doReturn(monitorStockVOs).when(stockDao).findMonitorStock(any(StockFilterVO.class));
    doReturn(new StockHolderVO()).when(stockDao).findStockHolderDetails(anyString(), anyString());

    // Then
    var page = monitorStockFeature.perform(stockfilterVO);
    assertThat(page.getDefaultPageSize()).isEqualTo(25);
    assertThat(page.getActualPageSize()).isEqualTo(5);
    assertThat(page.hasNextPage()).isFalse();
    assertThat(page.getStartIndex()).isEqualTo(1);
    assertThat(page.getEndIndex()).isEqualTo(25);
  }

  @Test
  void shouldSetTrueInHasNetPage() {
    // Given
    var monitorStockVOs = populateMonitorStockListVOs(DEFAULT_PAGE_SIZE, 50);

    // When
    doReturn(monitorStockVOs).when(stockDao).findMonitorStock(any(StockFilterVO.class));

    doReturn(new StockHolderVO()).when(stockDao).findStockHolderDetails(anyString(), anyString());

    // Then
    var page = monitorStockFeature.perform(stockfilterVO);
    assertThat(page.getDefaultPageSize()).isEqualTo(25);
    assertThat(page.getActualPageSize()).isEqualTo(25);
    assertThat(page.hasNextPage()).isTrue();
    assertThat(page.getStartIndex()).isEqualTo(1);
    assertThat(page.getEndIndex()).isEqualTo(25);
  }

  @Test
  void shouldReturnEmptyResultsFromPageInfo() {
    // Given
    var monitorStockVOs = new ArrayList<>();

    // When
    doReturn(monitorStockVOs).when(stockDao).findMonitorStock(any(StockFilterVO.class));

    doReturn(new StockHolderVO()).when(stockDao).findStockHolderDetails(anyString(), anyString());

    var page = monitorStockFeature.perform(stockfilterVO);

    // Then
    assertEquals(0, page.getTotalRecordCount());
  }
}
