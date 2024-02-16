package com.ibsplc.neoicargo.stock.component.feature.approvestockrequests.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestApproveVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class ApproveStockRequestsValidatorTest {

  @InjectMocks private ApproveStockRequestsValidator approveStockRequestsValidator;

  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateWithoutThrows() {
    // Given
    var vo = new StockRequestApproveVO();
    vo.setApproverCode("AC");
    vo.setCompanyCode("CC");

    // When
    doReturn(new StockHolderVO())
        .when(stockDao)
        .findStockHolderDetails(vo.getCompanyCode(), vo.getApproverCode());

    // Then
    Assertions.assertDoesNotThrow(() -> approveStockRequestsValidator.validate(vo));
  }

  @Test
  void shouldThrowStockNotFound() {
    // Given
    var vo = new StockRequestApproveVO();
    vo.setApproverCode("AC");
    vo.setCompanyCode("CC");

    // When
    doReturn(null).when(stockDao).findStockHolderDetails(vo.getCompanyCode(), vo.getApproverCode());

    // Then
    var thrown =
        assertThrows(BusinessException.class, () -> approveStockRequestsValidator.validate(vo));
    assertEquals(NEO_STOCK_011.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }
}
