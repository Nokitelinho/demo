package com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderVO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.masters.customer.CustomerWebAPI;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.persistor.StockAgentPersistor;
import com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.persistor.StockHolderDetailsPersistor;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class SaveStockHolderDetailsFeatureTest {

  private SaveStockHolderDetailsFeature saveStockHolderDetailsFeature;
  @Mock private StockAgentPersistor stockAgentPersistor;
  @Mock private CustomerWebAPI customerWebAPI;
  @Mock private StockHolderDetailsPersistor stockHolderDetailsPersistor;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    saveStockHolderDetailsFeature =
        new SaveStockHolderDetailsFeature(
            stockAgentPersistor, stockHolderDetailsPersistor, customerWebAPI);
  }

  @ParameterizedTest
  @MethodSource("provideStockHolder")
  void shouldSaveStockHolder(StockHolderVO stockHolderVO) throws BusinessException {
    doReturn(null).when(customerWebAPI).validateAgent(any(CustomerModel.class));
    Assertions.assertDoesNotThrow(() -> saveStockHolderDetailsFeature.perform(stockHolderVO));
    verify(customerWebAPI, atMost(1)).validateAgent(any(CustomerModel.class));
    verify(stockHolderDetailsPersistor, times(1)).save(stockHolderVO);
  }

  @Test
  void shouldSaveStockHolderAndStockAgent() throws BusinessException {
    var stockHolderVO = getMockStockHolderVO("AV", "HQ");
    stockHolderVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
    stockHolderVO.setStockHolderType(StockHolderType.A);
    CustomerModel customerModel = new CustomerModel();
    doReturn(customerModel).when(customerWebAPI).validateAgent(any(CustomerModel.class));
    Assertions.assertDoesNotThrow(() -> saveStockHolderDetailsFeature.perform(stockHolderVO));
    verify(customerWebAPI, times(1)).validateAgent(any(CustomerModel.class));
    verify(stockHolderDetailsPersistor, times(1)).save(stockHolderVO);
    verify(stockAgentPersistor, times(1)).save(stockHolderVO);
  }

  @Test
  public void testSaveStockAgent_StockHolderTypeOtherThanA_NoActionPerformed()
      throws BusinessException {
    StockHolderVO stockHolderVO = new StockHolderVO();
    stockHolderVO.setStockHolderType(StockHolderType.R);
    saveStockHolderDetailsFeature.perform(stockHolderVO);
    verify(stockAgentPersistor, never()).save(any(StockHolderVO.class));
  }

  @Test
  void shouldUpdateStockHolder() throws BusinessException {
    var stockHolderVO = getMockStockHolderVO("AV", "HQ");
    stockHolderVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
    Assertions.assertDoesNotThrow(() -> saveStockHolderDetailsFeature.perform(stockHolderVO));
    verify(stockHolderDetailsPersistor, times(1)).update(stockHolderVO);
  }

  @Test
  void shouldDoNothing() throws BusinessException {
    var stockHolderVo = getMockStockHolderVO("AV", "HQ");
    stockHolderVo.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
    saveStockHolderDetailsFeature.perform(stockHolderVo);
  }

  private static Stream<Arguments> provideStockHolder() {
    var companyCode = "AV";
    var stockHolderCode = "HQ";
    var stockHolderVO1 = getMockStockHolderVO(companyCode, stockHolderCode);
    stockHolderVO1.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
    stockHolderVO1.setStockHolderType(StockHolderType.A);

    var stockHolderVO2 = getMockStockHolderVO(companyCode, stockHolderCode);
    stockHolderVO2.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
    stockHolderVO2.setStockHolderType(StockHolderType.R);

    return Stream.of(Arguments.of(stockHolderVO1), Arguments.of(stockHolderVO2));
  }
}
