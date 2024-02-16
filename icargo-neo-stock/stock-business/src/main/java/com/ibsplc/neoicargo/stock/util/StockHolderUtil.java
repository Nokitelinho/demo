package com.ibsplc.neoicargo.stock.util;

import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.mapper.StockHolderMapper;
import com.ibsplc.neoicargo.stock.mapper.StockMapper;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("StockHolderUtil")
@RequiredArgsConstructor
public class StockHolderUtil {

  private final StockHolderRepository stockHolderRepository;
  private final StockMapper stockMapper;
  private final StockHolderMapper stockHolderMapper;

  public StockHolderVO findStockHolderDetails(String companyCode, String stockHolderCode) {
    StockHolderVO stockHolderVO = null;
    Collection<StockVO> stockVOs = new ArrayList<>();
    var stockHolderOptional =
        stockHolderRepository.findByCompanyCodeAndStockHolderCode(companyCode, stockHolderCode);
    if (stockHolderOptional.isPresent()) {
      for (Stock stock : stockHolderOptional.get().getStock()) {
        stockVOs.add(stockMapper.mapEntityToVoWithRanges(stock));
      }
      stockHolderVO = stockHolderOptional.map(stockHolderMapper::mapEntityToVo).orElse(null);
      if (Objects.nonNull(stockHolderVO)) {
        stockHolderVO.setStock(stockVOs);
      }
    }
    return stockHolderVO;
  }
}
