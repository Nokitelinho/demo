package com.ibsplc.neoicargo.stock.component.feature.findblacklistedstock;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.BlackListStockMapper;
import com.ibsplc.neoicargo.stock.mapper.StockFilterMapper;
import com.ibsplc.neoicargo.stock.model.BlacklistStockModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockFilterModel;
import com.ibsplc.neoicargo.stock.util.PageableUtil;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findBlacklistedStockFeature")
@RequiredArgsConstructor
public class FindBlacklistedStockFeature {

  private final StockDao stockDao;
  private final BlackListStockMapper blackListStockMapper;
  private final StockFilterMapper stockFilterMapper;

  public Page<BlacklistStockModel> perform(StockFilterModel stockFilterModel, int displayPage) {
    log.info("FindBlacklistedStockFeature Invoked");
    final var pageable = PageRequest.of(displayPage - 1, DEFAULT_PAGE_SIZE);
    final var stockFilterVO = stockFilterMapper.mapModelToVo(stockFilterModel);
    final var stockVOS = stockDao.findBlacklistedStock(stockFilterVO, pageable);
    final var stockModels = blackListStockMapper.mapVoToModel(stockVOS);
    final var totalRecordCount = PageableUtil.getTotalRecordCount(stockVOS);

    return blackListStockMapper.mapVosToPageView(
        stockModels, displayPage, stockModels.size(), totalRecordCount, DEFAULT_PAGE_SIZE);
  }
}
