package com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getLoginProfile;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockRangeVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockVO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.AllocateStockFeature;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class AllocateStockInvokerTest {
  @InjectMocks private AllocateStockInvoker allocateStockInvoker;
  @Mock private ContextUtil contextUtil;
  @Mock private AllocateStockFeature allocateStockFeature;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldAllocateWhenAirlineIdentifierNull() throws BusinessException {
    // Given
    var companyCode = "AV";
    var stockHolderCode = "HQ";
    var airlineId = 1123;
    var docType = "AWB";
    var docSubType = "S";
    var stockVO =
        getMockStockVO(
            getMockStockHolderVO(companyCode, stockHolderCode), airlineId, docType, docSubType);
    var rangeVO = getMockRangeVO(companyCode, stockHolderCode, docSubType, 1, 2);
    var stockRequestVO = getMockStockRequestVO();

    // When
    doReturn(getLoginProfile()).when(contextUtil).callerLoginProfile();
    allocateStockInvoker.invoke(stockRequestVO, stockVO, List.of(rangeVO));

    // Then
    allocateStockFeature.perform(any(StockAllocationVO.class));
  }

  @Test
  void shouldAllocateWhenAirlineIdentifierNotNull() throws BusinessException {
    // Given
    var companyCode = "AV";
    var stockHolderCode = "HQ";
    var airlineId = 1123;
    var docType = "AWB";
    var docSubType = "S";
    var stockVO =
        getMockStockVO(
            getMockStockHolderVO(companyCode, stockHolderCode), airlineId, docType, docSubType);
    var rangeVO = getMockRangeVO(companyCode, stockHolderCode, docSubType, 1, 2);
    var stockRequestVO = getMockStockRequestVO();
    stockRequestVO.setAirlineIdentifier("1234");

    // When
    doReturn(getLoginProfile()).when(contextUtil).callerLoginProfile();
    allocateStockInvoker.invoke(stockRequestVO, stockVO, List.of(rangeVO));

    // Then
    allocateStockFeature.perform(any(StockAllocationVO.class));
  }
}
