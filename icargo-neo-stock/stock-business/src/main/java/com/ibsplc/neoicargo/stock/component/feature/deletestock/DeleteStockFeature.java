package com.ibsplc.neoicargo.stock.component.feature.deletestock;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_006;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_DELETED;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.findNumberOfDocuments;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.helper.RangeHelper;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.invoker.CreateHistoryInvoker;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator.StockRangeUtilisationForRangeValidator;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("deleteStockFeature")
@RequiredArgsConstructor
public class DeleteStockFeature {

  private final ContextUtil contextUtil;
  private final RangeDao rangeDao;
  private final StockDao stockDao;
  private final RangeHelper rangeHelper;
  private final RangeMapper rangeMapper;
  private final StockRangeUtilisationForRangeValidator rangeUtilisationValidator;
  private final CreateHistoryInvoker createHistoryInvoker;

  public void perform(List<RangeVO> rangeVOs) throws BusinessException {
    log.info("deleteStockFeature Invoked");

    if (isNotEmpty(rangeVOs)) {
      for (RangeVO rangeVO : rangeVOs) {
        var stockAllocationVO = createStockAllocationFromRange(rangeVO);
        stockAllocationVO.setStockControlFor(rangeVO.getStockHolderCode());
        rangeUtilisationValidator.validate(stockAllocationVO);

        var rangeFilter = rangeMapper.mapVoToFilter(rangeVO);
        var existingRange = rangeDao.find(rangeFilter);
        if (existingRange == null) {
          log.error("Range {} not found", rangeFilter.toString());
          throw new StockBusinessException(
              constructErrorVO(
                  NEO_STOCK_006.getErrorCode(),
                  NEO_STOCK_006.getErrorMessage(),
                  ERROR,
                  new String[] {rangeFilter.toString()}));
        }

        rangeDao.remove(existingRange);

        if (!rangeVO.getStartRange().equals(existingRange.getStartRange())
            || !rangeVO.getEndRange().equals(existingRange.getEndRange())) {
          saveSplitRanges(stockAllocationVO, existingRange);
        }

        deletedStockHistory(rangeVO);
      }
    }
  }

  private void saveSplitRanges(StockAllocationVO allocationVO, RangeVO existingRange)
      throws StockBusinessException {
    var originalRanges =
        Set.of(
            RangeVO.builder()
                .startRange(existingRange.getStartRange())
                .endRange(existingRange.getEndRange())
                .build());

    var splitRanges = rangeHelper.splitRanges(originalRanges, allocationVO.getRanges());
    enrichSplitRanges(splitRanges, existingRange);

    var stockVO =
        stockDao.findStockWithRanges(
            allocationVO.getCompanyCode(),
            allocationVO.getStockHolderCode(),
            allocationVO.getAirlineIdentifier(),
            allocationVO.getDocumentType(),
            allocationVO.getDocumentSubType());
    if (stockVO == null) {
      throw new StockBusinessException(
          constructErrorVO(NEO_STOCK_003.getErrorCode(), NEO_STOCK_003.getErrorMessage(), ERROR));
    }

    stockDao.addRanges(stockVO, splitRanges);
  }

  private void enrichSplitRanges(List<RangeVO> splitRanges, RangeVO existingRange) {
    log.info("ranges to be added----->" + splitRanges);
    splitRanges.forEach(
        range -> {
          range.setCompanyCode(existingRange.getCompanyCode());
          range.setStockHolderCode(existingRange.getStockHolderCode());
          range.setDocumentType(existingRange.getDocumentType());
          range.setDocumentSubType(existingRange.getDocumentSubType());
          range.setAirlineIdentifier(existingRange.getAirlineIdentifier());
          range.setManual(existingRange.isManual());
          range.setAsciiStartRange(toLong(range.getStartRange()));
          range.setAsciiEndRange(toLong(range.getEndRange()));
          range.setNumberOfDocuments(
              findNumberOfDocuments(range.getEndRange(), range.getStartRange()));
          if (range.getStockAcceptanceDate() == null) {
            var ld =
                new LocalDate(
                    contextUtil.callerLoginProfile().getStationCode(), Location.ARP, true);
            range.setStockAcceptanceDate(LocalDateMapper.toZonedDateTime(ld));
          }
        });
  }

  private void deletedStockHistory(RangeVO rangeVO) {
    var stockAllocationVO = createStockAllocationFromRange(rangeVO);
    stockAllocationVO.setTransactionCode(MODE_DELETED);
    createHistoryInvoker.invoke(stockAllocationVO);
  }

  private StockAllocationVO createStockAllocationFromRange(RangeVO rangeVO) {
    return StockAllocationVO.builder()
        .companyCode(rangeVO.getCompanyCode())
        .airlineIdentifier(rangeVO.getAirlineIdentifier())
        .stockHolderCode(rangeVO.getStockHolderCode())
        .documentType(rangeVO.getDocumentType())
        .documentSubType(rangeVO.getDocumentSubType())
        .lastUpdateTime(rangeVO.getLastUpdateTime())
        .lastUpdateUser(contextUtil.callerLoginProfile().getUserId())
        .ranges(List.of(rangeVO))
        .build();
  }
}
