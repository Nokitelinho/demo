package com.ibsplc.neoicargo.stock.component.feature.validateagentforstockholder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.AWBDocumentValidationVO;
import com.ibsplc.neoicargo.stock.vo.AgentDetailVO;
import com.ibsplc.neoicargo.stock.vo.DocumentValidationVO;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class ValidateAgentForStockHolderFeatureTest {
  @InjectMocks private ValidateAgentForStockHolderFeature validateAgentForStockHolderFeature;
  @InjectMocks private AWBDocumentValidationVO documentValidationVO;
  @InjectMocks private DocumentValidationVO docValidationVO;
  @InjectMocks private AgentDetailVO agentDetailVO;

  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    var documentValidationVO = new AWBDocumentValidationVO();
    var agentDetailVO = new AgentDetailVO();
    var stkAgentMapping = new StockAgentVO();
    var stockHolder = new StockHolderVO();
    var stkvo = new StockVO();

    List<AgentDetailVO> agentDetails = new ArrayList<>();
    agentDetailVO.setCompanyCode("AV");
    agentDetailVO.setAgentCode("D1001");
    agentDetails.add(agentDetailVO);
    documentValidationVO.setAgentDetails(agentDetails);
    documentValidationVO.setStockHolderCode("1234567");
    documentValidationVO.setDocumentType("ABC");

    stkAgentMapping.setStockHolderCode("123456");
    doReturn(stkAgentMapping).when(stockDao).findStockAgent(any(String.class), any(String.class));
    Assertions.assertDoesNotThrow(
        () -> validateAgentForStockHolderFeature.findStockHolderCodeForAgent("AV", "D1001"));

    stockHolder.setStockHolderCode("1234567");
    doReturn(stockHolder)
        .when(stockDao)
        .findStockHolderDetails(any(String.class), any(String.class));

    Set<StockVO> stock = new HashSet<>();
    stkvo.setStockApproverCode("HQK");
    stkvo.setDocumentType("ABC");
    stock.add(stkvo);
    stockHolder.setStock(stock);

    Assertions.assertDoesNotThrow(
        () -> validateAgentForStockHolderFeature.obtainApproverForStockHolder(stockHolder, "AC"));

    Assertions.assertDoesNotThrow(
        () -> validateAgentForStockHolderFeature.perform(documentValidationVO));
  }

  @Test
  public void shouldfindStockHolderCodeForAgent() {
    var stkAgentMapping = new StockAgentVO();
    stkAgentMapping.setStockHolderCode("1234567");
    doReturn(stkAgentMapping).when(stockDao).findStockAgent(any(String.class), any(String.class));
    Assertions.assertDoesNotThrow(
        () -> validateAgentForStockHolderFeature.findStockHolderCodeForAgent("AV", "D1001"));
    assertEquals("1234567", stkAgentMapping.getStockHolderCode());
  }

  @Test
  public void shouldfindStockHolderCodeForAgentWhenNull() {
    StockAgentVO stkAgentMapping = null;
    doReturn(null).when(stockDao).findStockAgent(any(String.class), any(String.class));
    Assertions.assertDoesNotThrow(
        () -> validateAgentForStockHolderFeature.findStockHolderCodeForAgent("IBS", "D1001"));
    assertNull(stkAgentMapping);
  }

  @Test
  public void shouldObtainApproverForStockHolder() {
    var stk = new StockHolderVO();
    var stk1 = new StockHolderVO();
    var stkvo = new StockVO();
    Set<StockVO> stock = new HashSet<>();
    stkvo.setStockApproverCode("HQK");
    stkvo.setDocumentType("AWB");
    stock.add(stkvo);
    stk.setStock(stock);

    Assertions.assertDoesNotThrow(
        () -> validateAgentForStockHolderFeature.obtainApproverForStockHolder(stk, "AWB"));
    assertEquals("HQK", stkvo.getStockApproverCode());
    assertEquals("AWB", stkvo.getDocumentType());
    Assertions.assertDoesNotThrow(
        () -> validateAgentForStockHolderFeature.obtainApproverForStockHolder(stk1, "AWB"));
  }
}
