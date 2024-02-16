package com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.getNumberOfDocs;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.component.feature.findranges.FindRangesFeature;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindRangesInvoker {
  private final ContextUtil contextUtil;
  private final FindRangesFeature findRangesFeature;

  public List<RangeVO> invoke(StockRequestVO stockRequestVO, StockVO stockVO)
      throws BusinessException {
    var rangeFilterVO = new RangeFilterVO();
    rangeFilterVO.setCompanyCode(stockRequestVO.getCompanyCode());
    rangeFilterVO.setDocumentType(stockRequestVO.getDocumentType());
    rangeFilterVO.setDocumentSubType(stockRequestVO.getDocumentSubType());
    rangeFilterVO.setNumberOfDocuments(String.valueOf(getNumberOfDocs(stockRequestVO, stockVO)));
    rangeFilterVO.setStockHolderCode(stockVO.getStockApproverCode());
    rangeFilterVO.setManualFlag(stockRequestVO.getIsManual());
    rangeFilterVO.setStartRange("0");
    if (stockRequestVO.getAirlineIdentifier() != null) {
      rangeFilterVO.setAirlineIdentifier(Integer.parseInt(stockRequestVO.getAirlineIdentifier()));
    } else {
      rangeFilterVO.setAirlineIdentifier(
          contextUtil.callerLoginProfile().getOwnAirlineIdentifier());
    }
    return findRangesFeature.perform(rangeFilterVO);
  }
}
