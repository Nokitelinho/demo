package com.ibsplc.neoicargo.stock.component.feature.finddocumentdetails;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STRING_START;
import static com.ibsplc.neoicargo.stock.util.StockConstant.SUBSTRING_COUNT;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findDocumentDetailsFeature")
@RequiredArgsConstructor
public class FindDocumentDetailsFeature {

  private final StockDao stockDao;

  public StockRequestVO perform(String companyCode, int airlineIdentifier, String documentNumber) {
    log.info("FindDocumentDetailsFeature Invoked");
    return stockDao.findDocumentDetails(
        companyCode,
        airlineIdentifier,
        toLong(documentNumber.substring(STRING_START, SUBSTRING_COUNT)));
  }
}
