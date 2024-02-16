package com.ibsplc.neoicargo.stock.component.feature.checkforblacklisteddocument;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("checkForBlacklistedDocumentFeature")
@RequiredArgsConstructor
public class CheckForBlacklistedDocumentFeature {

  private final StockDao stockDao;

  public Boolean perform(
      String originalCompanyCode, String doctype, String originalDocumentNumber) {
    String documentNumber;
    String companyCode;
    Long airlineIdentifier = null;

    if ("AWB".equals(doctype)
        && originalDocumentNumber != null
        && originalDocumentNumber.trim().length() >= 7) {
      documentNumber = originalDocumentNumber.substring(0, 7);
    } else {
      documentNumber = originalDocumentNumber;
    }

    if (originalCompanyCode != null && originalCompanyCode.contains("#")) {
      String[] tokens = originalCompanyCode.split("#");
      companyCode = tokens[0];
      airlineIdentifier = Long.valueOf(tokens[1]);
    } else {
      companyCode = originalCompanyCode;
    }

    return stockDao.checkForBlacklistedDocument(
        companyCode, doctype, documentNumber, airlineIdentifier);
  }
}
