package com.ibsplc.neoicargo.stock.component.feature.monitorstock;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;

import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.MonitorStockMapper;
import com.ibsplc.neoicargo.stock.model.MonitorStockModel;
import com.ibsplc.neoicargo.stock.util.PageableUtil;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("monitorStockFeature")
@FeatureConfigSource("stock/monitorstock")
@RequiredArgsConstructor
public class MonitorStockFeature extends AbstractFeature<StockFilterVO> {

  private final StockDao stockDao;
  private final MonitorStockMapper monitorStockMapper;

  @Override
  public Page<MonitorStockModel> perform(StockFilterVO stockFilterVO) {
    log.info("monitorStockFeature Invoked");
    var monitorStockVOs = stockDao.findMonitorStock(stockFilterVO);
    var monitorStockModels = monitorStockMapper.mapVoToModel(monitorStockVOs);
    final var totalRecordCount = PageableUtil.getTotalRecordCount(monitorStockVOs);

    return monitorStockMapper.mapVosToPageView(
        monitorStockModels,
        stockFilterVO.getPageNumber(),
        monitorStockModels.size(),
        totalRecordCount,
        DEFAULT_PAGE_SIZE);
  }
}
