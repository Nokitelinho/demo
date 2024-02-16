package com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.getNumberOfDocs;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.approvestockrequests.ApproveStockRequestsFeature;
import com.ibsplc.neoicargo.stock.vo.StockRequestApproveVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApproveStockRequestInvoker {
  private final ApproveStockRequestsFeature approveStockRequestsFeature;

  public void invoke(StockRequestVO stockRequestVO, StockVO stockVO) throws BusinessException {
    var stockRequestApproveVO = new StockRequestApproveVO();
    stockRequestVO.setApprovedStock(getNumberOfDocs(stockRequestVO, stockVO));
    stockRequestApproveVO.setCompanyCode(stockRequestVO.getCompanyCode());
    stockRequestApproveVO.setApproverCode(stockVO.getStockApproverCode());
    stockRequestApproveVO.setDocumentType(stockRequestVO.getDocumentType());
    stockRequestApproveVO.setDocumentSubType(stockRequestVO.getDocumentSubType());
    stockRequestApproveVO.setStockRequests(List.of(stockRequestVO));
    approveStockRequestsFeature.execute(stockRequestApproveVO);
  }
}
