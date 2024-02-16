package com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockVO;
import static org.mockito.ArgumentMatchers.any;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.approvestockrequests.ApproveStockRequestsFeature;
import com.ibsplc.neoicargo.stock.vo.StockRequestApproveVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class ApproveStockRequestInvokerTest {
  @InjectMocks private ApproveStockRequestInvoker approveStockRequestInvoker;
  @Mock private ApproveStockRequestsFeature approveStockRequestsFeature;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldFindRangeWhenAirlineIdentifierNull() throws BusinessException {
    // Given
    var companyCode = "AV";
    var stockHolderCode = "HQ";
    var airlineId = 1123;
    var docType = "AWB";
    var docSubType = "S";
    var stockVO =
        getMockStockVO(
            getMockStockHolderVO(companyCode, stockHolderCode), airlineId, docType, docSubType);
    var stockRequestVO = getMockStockRequestVO();

    // When
    approveStockRequestInvoker.invoke(stockRequestVO, stockVO);

    // Then
    approveStockRequestsFeature.execute(any(StockRequestApproveVO.class));
  }
}
