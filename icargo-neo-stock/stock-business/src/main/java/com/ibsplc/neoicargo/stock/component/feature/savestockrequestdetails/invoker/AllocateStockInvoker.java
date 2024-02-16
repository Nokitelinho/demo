package com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.AllocateStockFeature;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AllocateStockInvoker {
  private final ContextUtil contextUtil;
  private final AllocateStockFeature allocateStockFeature;

  public void invoke(StockRequestVO stockRequestVO, StockVO stockVO, List<RangeVO> rangeVOS)
      throws BusinessException {
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setDocumentType(stockRequestVO.getDocumentType());
    stockAllocationVO.setDocumentSubType(stockRequestVO.getDocumentSubType());
    stockAllocationVO.setStockControlFor(stockVO.getStockApproverCode());
    stockAllocationVO.setStockHolderCode(stockRequestVO.getStockHolderCode());
    stockAllocationVO.setManual(BaseMapper.convertToBoolean(stockRequestVO.getIsManual()));
    stockAllocationVO.setCompanyCode(contextUtil.callerLoginProfile().getCompanyCode());
    stockAllocationVO.setNewStockFlag(false);
    stockAllocationVO.setAllocate(true);
    stockAllocationVO.setTransferMode(StockConstant.MODE_NORMAL);
    stockAllocationVO.setTransactionCode(StockConstant.MODE_ALLOCATE);
    if (stockRequestVO.getAirlineIdentifier() != null) {
      stockAllocationVO.setAirlineIdentifier(
          Integer.parseInt(stockRequestVO.getAirlineIdentifier()));
    } else {
      stockAllocationVO.setAirlineIdentifier(
          contextUtil.callerLoginProfile().getOwnAirlineIdentifier());
    }
    stockAllocationVO.setLastUpdateUser(contextUtil.callerLoginProfile().getUserId());
    stockAllocationVO.setRanges(rangeVOS);
    stockAllocationVO.setRequestRefNumber(stockRequestVO.getRequestRefNumber());
    stockAllocationVO.setAwbPrefix(stockRequestVO.getAwbPrefix());
    stockAllocationVO.setAutoAllocated(AbstractVO.FLAG_YES);
    allocateStockFeature.execute(stockAllocationVO);
  }
}
