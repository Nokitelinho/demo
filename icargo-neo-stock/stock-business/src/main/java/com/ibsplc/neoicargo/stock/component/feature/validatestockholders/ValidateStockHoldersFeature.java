package com.ibsplc.neoicargo.stock.component.feature.validatestockholders;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DELIMITER;

import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("validateStockHoldersFeature")
@RequiredArgsConstructor
public class ValidateStockHoldersFeature {

  private final StockHolderRepository stockHolderRepository;

  public void perform(String companyCode, List<String> incomeStockHolderCodes)
      throws StockBusinessException {
    log.info("ValidateStockHoldersFeature Invoked");

    var validStockHolders =
        stockHolderRepository.findByCompanyCodeAndStockHolderCodeIn(
            companyCode, incomeStockHolderCodes);

    var invalidStockHolders = new StringJoiner(DELIMITER);

    if (incomeStockHolderCodes.size() != validStockHolders.size()) {
      var stockHolderCodes =
          validStockHolders.stream()
              .map(StockHolder::getStockHolderCode)
              .collect(Collectors.toList());
      incomeStockHolderCodes.removeAll(stockHolderCodes);
      incomeStockHolderCodes.forEach(invalidStockHolders::add);

      var errorVo =
          constructErrorVO(
              NEO_STOCK_011.getErrorCode(),
              NEO_STOCK_011.getErrorMessage(),
              ErrorType.ERROR,
              new String[] {invalidStockHolders.toString()});
      throw new StockBusinessException(errorVo);
    }
  }
}
