package com.ibsplc.neoicargo.stock.component.feature.findstockholders;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;

import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockHolderDetailsModelMapper;
import com.ibsplc.neoicargo.stock.model.StockHolderDetailsModel;
import com.ibsplc.neoicargo.stock.util.PageableUtil;
import com.ibsplc.neoicargo.stock.vo.StockHolderDetailsVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderFilterVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findStockHoldersFeature")
@RequiredArgsConstructor
public class FindStockHoldersFeature {

  private final StockDao stockDao;
  private final StockHolderDetailsModelMapper mapper;
  private final AirlineWebComponent airlineWebComponent;

  public Page<StockHolderDetailsModel> perform(StockHolderFilterVO stockHolderFilterVO) {
    log.info("findStockHoldersFeature Invoked");
    var stockHolderDetailsVOs = stockDao.findStockHolders(stockHolderFilterVO);

    /*
     * calling findAirlineValidityDetails api from shared-masters to
     * set the awbPrefix
     */
    var map =
        CollectionUtils.emptyIfNull(findAirlineModels(stockHolderDetailsVOs)).stream()
            .collect(
                Collectors.toMap(
                    AirlineModel::getAirlineIdentifier,
                    k -> k.getAirlineValidityDetails().iterator().next().getThreeNumberCode()));

    for (StockHolderDetailsVO detailsVO : CollectionUtils.emptyIfNull(stockHolderDetailsVOs)) {
      var thrnumcod = map.get(detailsVO.getAirlineIdentifier());
      if (StringUtils.isNotBlank(thrnumcod)) {
        detailsVO.setAwbPrefix(thrnumcod);
      }
    }

    var stockHolderDetailsModels = mapper.mapVoToModel(stockHolderDetailsVOs);
    final var totalRecordCount = PageableUtil.getTotalRecordCount(stockHolderDetailsVOs);
    return mapper.mapVosToPageView(
        stockHolderDetailsModels,
        stockHolderFilterVO.getPageNumber(),
        stockHolderDetailsModels.size(),
        totalRecordCount,
        DEFAULT_PAGE_SIZE);
  }

  public List<AirlineModel> findAirlineModels(List<StockHolderDetailsVO> stockHolderDetailsVOs) {
    var arlModelsFilter =
        CollectionUtils.emptyIfNull(stockHolderDetailsVOs).stream()
            .distinct()
            .map(
                stockHolderDetailsVO -> {
                  var arlMdl = new AirlineModel();
                  arlMdl.setAirlineIdentifier(stockHolderDetailsVO.getAirlineIdentifier());
                  return arlMdl;
                })
            .collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(arlModelsFilter)) {
      return airlineWebComponent.findAirlineValidityDetails(arlModelsFilter);
    }
    return Collections.emptyList();
  }
}
