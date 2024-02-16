package com.ibsplc.neoicargo.stock.component.feature.findstockrangehistoryforpage;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;

import com.ibsplc.neoicargo.stock.dao.CQRSDao;
import com.ibsplc.neoicargo.stock.mapper.StockRangeHistoryMapper;
import com.ibsplc.neoicargo.stock.model.StockRangeHistoryModel;
import com.ibsplc.neoicargo.stock.util.PageableUtil;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findStockRangeHistoryForPageFeature")
@RequiredArgsConstructor
public class FindStockRangeHistoryForPageFeature {
  private final CQRSDao cqrsDao;
  private final StockRangeHistoryMapper stockRangeHistoryMapper;

  public Page<StockRangeHistoryModel> perform(StockRangeFilterVO stockRangeFilterVO) {
    log.info("findStockRangeHistoryForPageFeature Invoked");
    final var pageSize =
        stockRangeFilterVO.getPageSize() == 0
            ? DEFAULT_PAGE_SIZE
            : stockRangeFilterVO.getPageSize();
    final var pageable = PageRequest.of(stockRangeFilterVO.getPageNumber() - 1, pageSize);

    final var stockRangeHistoryVOs =
        cqrsDao.findStockRangeHistoryForPage(stockRangeFilterVO, pageable);
    final var stockRangeHistoryModels = stockRangeHistoryMapper.mapVoToModel(stockRangeHistoryVOs);
    final var totalRecordCount = PageableUtil.getTotalRecordCount(stockRangeHistoryVOs);

    return stockRangeHistoryMapper.mapVosToPageView(
        stockRangeHistoryModels,
        stockRangeFilterVO.getPageNumber(),
        stockRangeHistoryModels.size(),
        totalRecordCount,
        pageSize);
  }
}
