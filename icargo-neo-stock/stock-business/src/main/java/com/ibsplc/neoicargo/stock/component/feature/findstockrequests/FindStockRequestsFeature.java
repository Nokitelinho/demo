package com.ibsplc.neoicargo.stock.component.feature.findstockrequests;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockRequestMapper;
import com.ibsplc.neoicargo.stock.model.StockRequestModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockRequestFilterModel;
import com.ibsplc.neoicargo.stock.util.PageableUtil;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findStockRequestsFeature")
@RequiredArgsConstructor
public class FindStockRequestsFeature {

  private final StockDao stockDao;
  private final StockRequestMapper stockRequestMapper;

  public Page<StockRequestModel> perform(
      int displayPage, StockRequestFilterModel stockRequestFilterModel) {
    log.info("FindStockRequestsFeature Invoked");
    final var pageSize =
        stockRequestFilterModel.getPageSize() == 0
            ? DEFAULT_PAGE_SIZE
            : stockRequestFilterModel.getPageSize();
    final var pageable = PageRequest.of(displayPage - 1, pageSize);
    final var stockRequestFilterVO = stockRequestMapper.mapModelToVo(stockRequestFilterModel);
    final var stockRequestVOS = stockDao.findStockRequests(stockRequestFilterVO, pageable);
    final var stockRequestModels = stockRequestMapper.mapVoToModel(stockRequestVOS);
    final var totalRecordCount = PageableUtil.getTotalRecordCount(stockRequestVOS);

    return stockRequestMapper.mapVosToPageView(
        stockRequestModels, displayPage, stockRequestModels.size(), totalRecordCount, pageSize);
  }
}
