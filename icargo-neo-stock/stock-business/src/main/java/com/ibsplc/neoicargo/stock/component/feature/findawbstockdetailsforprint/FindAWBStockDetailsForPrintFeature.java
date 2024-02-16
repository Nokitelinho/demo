package com.ibsplc.neoicargo.stock.component.feature.findawbstockdetailsforprint;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_022;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findAWBStockDetailsForPrintFeature")
@RequiredArgsConstructor
public class FindAWBStockDetailsForPrintFeature {

  private final ContextUtil contextUtil;
  private final StockDao stockDao;
  private final AirlineWebComponent airlineWebComponent;

  public Collection<RangeVO> perform(StockFilterVO stockFilterVO) throws StockBusinessException {

    log.debug(
        "FindAWBStockDetailsForPrintFeature" + " : " + "findAWBStockDetailsForPrint" + " Entering");
    log.info("" + "AWBStockReport Filter " + " " + stockFilterVO);

    var logonAttributes = contextUtil.callerLoginProfile();

    if (stockFilterVO.getStockHolderCode() != null
        && stockFilterVO.getStockHolderCode().trim().length() > 0) {

      StockHolderVO stockHolderVO =
          stockDao.findStockHolderDetails(
              logonAttributes.getCompanyCode(), stockFilterVO.getStockHolderCode());

      log.debug("" + "Stockholder after find is----" + " " + stockHolderVO);

      if (stockHolderVO == null) {
        throw new StockBusinessException(constructErrorVO(NEO_STOCK_022, ERROR));
      }
    }

    List<RangeVO> rngVOs = stockDao.findAWBStockDetailsForPrint(stockFilterVO);
    if (rngVOs == null) {
      throw new StockBusinessException(constructErrorVO(NEO_STOCK_003, ERROR));
    }
    List<AirlineModel> airlineModels = findAirlineModels(rngVOs);
    Collection<RangeVO> rangeVOs = new ArrayList<>();

    for (RangeVO rangeVO : rngVOs) {

      if (rangeVO.getAirlineIdentifier() != 0) {

        for (AirlineModel airlineModel : airlineModels) {
          if (rangeVO.getAirlineIdentifier() == airlineModel.getAirlineIdentifier()) {
            rangeVO.setAwbCheckDigit(airlineModel.getAwbCheckDigit());
            rangeVO.setAwbPrefix(
                airlineModel.getAirlineValidityDetails().iterator().next().getThreeNumberCode());
            break;
          }
        }

        if (rangeVO.getAwbCheckDigit() != 0) {

          int appendStartRange =
              Integer.parseInt(rangeVO.getStartRange()) % (rangeVO.getAwbCheckDigit());
          int appendEndRange =
              Integer.parseInt(rangeVO.getEndRange()) % (rangeVO.getAwbCheckDigit());

          StringBuilder startRange =
              new StringBuilder(rangeVO.getAwbPrefix())
                  .append(StockConstant.HYPHEN)
                  .append(rangeVO.getStartRange())
                  .append(appendStartRange);
          StringBuilder endRange =
              new StringBuilder(rangeVO.getAwbPrefix())
                  .append(StockConstant.HYPHEN)
                  .append(rangeVO.getEndRange())
                  .append(appendEndRange);

          rangeVO.setStartRange(startRange.toString());
          rangeVO.setEndRange(endRange.toString());
        }
      }
      rangeVOs.add(rangeVO);
    }

    log.info("" + "stockRangeVO " + " " + rangeVOs);

    if (rangeVOs.size() == 0) {
      throw new StockBusinessException(constructErrorVO(NEO_STOCK_003, ERROR));
    }

    return rangeVOs;
  }

  protected List<AirlineModel> findAirlineModels(List<RangeVO> rngVOs) {

    var arlModelsFilter =
        CollectionUtils.emptyIfNull(rngVOs).stream()
            .distinct()
            .map(
                rangeVO -> {
                  var arlMdl = new AirlineModel();
                  arlMdl.setAirlineIdentifier(rangeVO.getAirlineIdentifier());
                  return arlMdl;
                })
            .collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(arlModelsFilter)) {
      return airlineWebComponent.findAirlineValidityDetails(arlModelsFilter);
    }
    return Collections.emptyList();
  }
}
