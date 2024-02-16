package com.ibsplc.neoicargo.stock.component.feature.deletestockholder;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_AWB;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import com.ibsplc.neoicargo.stock.util.StockHolderUtil;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderDetailsVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class DeleteStockHolderFeatureTest {

  @InjectMocks private DeleteStockHolderFeature feature;
  @Mock private StockDao stockDao;
  @Mock private StockHolderUtil util;
  @Mock private AuditUtils<AuditVO> auditUtils;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldThrowExceptionWhenAgentMappingExists() {
    // Given
    StockHolderDetailsVO holderVO = new StockHolderDetailsVO();
    holderVO.setCompanyCode("IBS");
    holderVO.setStockHolderCode("HQ");
    StockAgentVO agentVO = new StockAgentVO();
    agentVO.setAgentCode("A1001");

    // When
    doReturn(Arrays.asList(agentVO))
        .when(stockDao)
        .findStockAgentMappings(any(StockAgentFilterVO.class));
    // Then
    assertThrows(StockBusinessException.class, () -> feature.perform(holderVO));
  }

  @Test
  void shouldThrowExceptionWhenStockHolderIsAnApprover() {
    // Given
    StockHolderDetailsVO holderVO = new StockHolderDetailsVO();
    holderVO.setCompanyCode("IBS");
    holderVO.setStockHolderCode("HQ");

    // When
    doReturn((Collections.EMPTY_LIST))
        .when(stockDao)
        .findStockAgentMappings(any(StockAgentFilterVO.class));
    doReturn(3).when(stockDao).checkApprover(any(String.class), any(String.class));

    // Then
    assertThrows(StockBusinessException.class, () -> feature.perform(holderVO));
  }

  @Test
  void shouldThrowExceptionWhenRangeExistsForStock() {
    // Given
    StockHolderDetailsVO holderVO = new StockHolderDetailsVO();
    holderVO.setCompanyCode("IBS");
    holderVO.setStockHolderCode("HQ");

    StockHolderVO stockHolderVO = new StockHolderVO();
    StockVO stockVO = new StockVO();
    RangeVO vo = new RangeVO();
    vo.setAsciiEndRange(4554548);
    vo.setAsciiStartRange(4554543);
    stockVO.setDocumentType(DOC_TYP_AWB);
    stockVO.setAirlineIdentifier(1134);
    stockHolderVO.setStockHolderType(StockHolderType.A);
    stockHolderVO.setStockHolderCode("HQ");
    stockVO.setRanges(new HashSet<>());
    stockVO.getRanges().add(vo);
    stockHolderVO.setStock(new HashSet<>());
    stockHolderVO.getStock().add(stockVO);

    // When
    doReturn((Collections.EMPTY_LIST))
        .when(stockDao)
        .findStockAgentMappings(any(StockAgentFilterVO.class));
    doReturn(0).when(stockDao).checkApprover(any(String.class), any(String.class));
    doReturn(stockHolderVO).when(util).findStockHolderDetails(any(String.class), any(String.class));

    // Then
    assertThrows(StockBusinessException.class, () -> feature.perform(holderVO));
  }

  @Test
  void shouldThrowExceptionWhenStockHolderDoesNotExists() {
    // Given
    StockHolderDetailsVO holderVO = new StockHolderDetailsVO();
    holderVO.setCompanyCode("IBS");
    holderVO.setStockHolderCode("HQ");

    // When
    doReturn((Collections.EMPTY_LIST))
        .when(stockDao)
        .findStockAgentMappings(any(StockAgentFilterVO.class));
    doReturn(0).when(stockDao).checkApprover(any(String.class), any(String.class));
    doReturn(null).when(util).findStockHolderDetails(any(String.class), any(String.class));

    // Then
    assertThrows(StockBusinessException.class, () -> feature.perform(holderVO));
  }

  @Test
  void shouldDeleteStockHolder() {
    // Given
    StockHolderDetailsVO holderVO = new StockHolderDetailsVO();
    holderVO.setCompanyCode("IBS");
    holderVO.setStockHolderCode("HQ");

    StockHolderVO stockHolderVO = new StockHolderVO();
    StockVO stockVO = new StockVO();
    stockVO.setDocumentType(DOC_TYP_AWB);
    stockVO.setAirlineIdentifier(1134);
    stockHolderVO.setStockHolderType(StockHolderType.A);
    stockHolderVO.setStockHolderCode("HQ");
    stockVO.setRanges(new HashSet<>());
    stockHolderVO.setStock(new HashSet<>());
    stockHolderVO.getStock().add(stockVO);

    // When
    doReturn((Collections.EMPTY_LIST))
        .when(stockDao)
        .findStockAgentMappings(any(StockAgentFilterVO.class));
    doReturn(0).when(stockDao).checkApprover(any(String.class), any(String.class));
    doReturn(stockHolderVO).when(util).findStockHolderDetails(any(String.class), any(String.class));

    // Then
    assertDoesNotThrow(() -> feature.perform(holderVO));
  }
}
