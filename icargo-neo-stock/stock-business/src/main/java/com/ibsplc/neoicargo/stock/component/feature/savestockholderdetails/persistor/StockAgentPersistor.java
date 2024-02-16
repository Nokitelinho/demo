package com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.persistor;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.exception.StockErrors;
import com.ibsplc.neoicargo.stock.mapper.StockAgentMapper;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockAgentPersistor {
  private final StockAgentMapper stockAgentMapper;
  private final StockAgentRepository stockAgentRepository;

  public void save(StockHolderVO stockHolderVO) throws StockBusinessException {
    var stockAgentVO = build(stockHolderVO);
    var stockAgent =
        stockAgentRepository
            .findByCompanyCodeAndAgentCode(
                stockAgentVO.getCompanyCode(), stockAgentVO.getAgentCode())
            .orElse(null);
    if (stockAgent == null) {
      stockAgentRepository.save(stockAgentMapper.mapVoToEntity(stockAgentVO));
    } else {
      throw new StockBusinessException(
          constructErrorVO(
              StockErrors.NEO_STOCK_015.getErrorCode(),
              StockErrors.NEO_STOCK_015.getErrorMessage(),
              ErrorType.ERROR,
              new String[] {stockAgentVO.getAgentCode()}));
    }
  }

  private StockAgentVO build(StockHolderVO stockHolderVO) {
    return StockAgentVO.builder()
        .agentCode(stockHolderVO.getStockHolderCode())
        .stockHolderCode(stockHolderVO.getStockHolderCode())
        .companyCode(stockHolderVO.getCompanyCode())
        .lastUpdateUser(stockHolderVO.getLastUpdateUser())
        .build();
  }
}
