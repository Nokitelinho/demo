package com.ibsplc.neoicargo.stock.component.feature.findstockholderlov;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockHolderLovMapper;
import com.ibsplc.neoicargo.stock.model.StockHolderLovModel;
import com.ibsplc.neoicargo.stock.util.PageableUtil;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderLovFilterVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("findStockHolderLovFeature")
@RequiredArgsConstructor
public class FindStockHolderLovFeature {
  private final StockDao stockDao;
  private final StockHolderLovMapper stockHolderLovMapper;

  public Page<StockHolderLovModel> perform(StockHolderLovFilterVO vo) {
    final var stockHolderLovVOs = stockDao.findStockHolderLov(vo);
    final var stockHolderLovModels = stockHolderLovMapper.mapVoToModel(stockHolderLovVOs);
    final var totalRecordCount = PageableUtil.getTotalRecordCount(stockHolderLovVOs);

    return stockHolderLovMapper.mapVosToPageView(
        stockHolderLovModels,
        vo.getPageNumber(),
        stockHolderLovModels.size(),
        totalRecordCount,
        DEFAULT_PAGE_SIZE);
  }
}
