package com.ibsplc.neoicargo.stock.component.feature.findtotalnoofdocuments;

import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findTotalNoOfDocumentsFeature")
@RequiredArgsConstructor
public class FindTotalNoOfDocumentsFeature {

  private final RangeDao rangeDao;

  public int perform(StockFilterVO stockFilterVO) {
    log.info("findTotalNoOfDocumentsFeature Invoked");
    return rangeDao.findTotalNoOfDocuments(stockFilterVO);
  }
}
