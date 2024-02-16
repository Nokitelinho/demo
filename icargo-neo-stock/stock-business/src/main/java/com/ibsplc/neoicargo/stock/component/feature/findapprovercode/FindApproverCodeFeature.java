package com.ibsplc.neoicargo.stock.component.feature.findapprovercode;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findApproverCodeFeature")
@RequiredArgsConstructor
public class FindApproverCodeFeature {

  private final StockDao stockDao;

  public String perform(
      String companyCode,
      String stockHolderCode,
      // Integer airlineId,
      String docType,
      String docSubType) {
    log.info("FindApproverCodeFeature Invoked");
    return stockDao.findApproverCode(companyCode, stockHolderCode, docType, docSubType);
  }
}
