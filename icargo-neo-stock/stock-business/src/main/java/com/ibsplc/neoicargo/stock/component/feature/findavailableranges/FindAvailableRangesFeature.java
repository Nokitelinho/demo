package com.ibsplc.neoicargo.stock.component.feature.findavailableranges;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.mapper.StockFilterMapper;
import com.ibsplc.neoicargo.stock.model.RangeModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockFilterModel;
import com.ibsplc.neoicargo.stock.util.PageableUtil;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findAvailableRangesFeature")
@RequiredArgsConstructor
public class FindAvailableRangesFeature {

  private final StockDao stockDao;
  private final StockFilterMapper stockFilterMapper;
  private final RangeMapper rangeMapper;

  public Page<RangeModel> perform(StockFilterModel stockFilterModel) {
    log.info("FindAvailableRangesFeature Invoked");
    final var pageable = PageRequest.of(stockFilterModel.getPageNumber() - 1, DEFAULT_PAGE_SIZE);
    final var stockFilterVO = stockFilterMapper.mapModelToVo(stockFilterModel);
    final var availableRangesVOS = stockDao.findAvailableRanges(stockFilterVO, pageable);
    final var availableRangesModels = rangeMapper.mapVoToModel(availableRangesVOS);
    final var totalRecordCount = PageableUtil.getTotalRecordCount(availableRangesVOS);

    return rangeMapper.mapVosToPageView(
        availableRangesModels,
        stockFilterModel.getPageNumber(),
        availableRangesModels.size(),
        totalRecordCount,
        DEFAULT_PAGE_SIZE);
  }
}
