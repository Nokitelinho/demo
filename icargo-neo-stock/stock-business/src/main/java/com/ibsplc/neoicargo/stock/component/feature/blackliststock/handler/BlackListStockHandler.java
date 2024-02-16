package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_020;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_BLACKLIST;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.StockDetailsHandler;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.StockHolderHandler;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.helper.SystemParameterHelper;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.invoker.CreateHistoryInvoker;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.helper.RearrangeRangesHelper;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor.BlackListHistoryPersistor;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor.BlackListStockPersistor;
import com.ibsplc.neoicargo.stock.component.feature.monitorstock.validator.StockFilterValidator;
import com.ibsplc.neoicargo.stock.dao.BlacklistStockDao;
import com.ibsplc.neoicargo.stock.dao.CQRSDao;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.BlackListStockMapper;
import com.ibsplc.neoicargo.stock.util.LoggerUtil;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlackListStockHandler {
  private final CQRSDao cqrsDao;
  private final StockDao stockDao;
  private final BlacklistStockDao blacklistStockDao;
  private final StockFilterValidator stockFilterValidator;
  private final RearrangeRangesHelper rearrangeRangesHelper;
  private final SystemParameterHelper systemParameterHelper;
  private final BlackListHistoryPersistor blackListHistoryPersistor;
  private final BlackListStockPersistor blackListStockPersistor;
  private final BlackListMissingStockHandler blackListMissingStockHandler;
  private final StockHolderHandler stockHolderHandler;
  private final StockDetailsHandler stockDetailsHandler;
  private final BlackListStockMapper blacklistStockMapper;
  private final CreateHistoryInvoker createHistoryInvoker;

  public void blacklistStockHolderStock(BlacklistStockVO blacklistStockVO)
      throws BusinessException {
    log.info("BlackListStockHandler Invoked");
    log.info(
        "Income BlacklistStockVO: rangeFrom: {}, rangeTo: {}",
        blacklistStockVO.getRangeFrom(),
        blacklistStockVO.getRangeTo());
    var systemParameters = systemParameterHelper.getSystemParameters();
    var isStockHolder = false;
    if (!blacklistStockDao.alreadyBlackListed(blacklistStockVO)) {
      var blacklistStockVos = cqrsDao.findBlacklistRangesForBlackList(blacklistStockVO);
      log.info("Find ranges for blacklist");
      LoggerUtil.logStocks(blacklistStockVos);
      var isManual = true;
      if (blacklistStockVos == null || blacklistStockVos.size() == 0) {
        log.info("BlacklistRangesForBlackList is empty");
        blacklistStockVos = new ArrayList<>(1);
        blacklistStockVos.add(new StockVO());
      }
      if (blacklistStockVos.size() > 0) {
        for (StockVO stockVO : blacklistStockVos) {
          var newBlacklistStockVO = new BlacklistStockVO();
          ArrayList<RangeVO> rangesArray = null;
          if (stockVO.getCompanyCode() != null) {
            log.info("companyCode is present");
            newBlacklistStockVO.setCompanyCode(blacklistStockVO.getCompanyCode());
            newBlacklistStockVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
            newBlacklistStockVO.setDocumentType(blacklistStockVO.getDocumentType());
            newBlacklistStockVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
            rangesArray = new ArrayList<>(stockVO.getRanges());
            if (rangesArray.get(0).getAsciiStartRange() < toLong(blacklistStockVO.getRangeFrom())) {
              newBlacklistStockVO.setRangeFrom(blacklistStockVO.getRangeFrom());
            } else {
              newBlacklistStockVO.setRangeFrom(rangesArray.get(0).getStartRange());
            }
            if (rangesArray.get(0).getAsciiEndRange() < toLong(blacklistStockVO.getRangeTo())) {
              newBlacklistStockVO.setRangeTo(rangesArray.get(0).getEndRange());
            } else {
              newBlacklistStockVO.setRangeTo(blacklistStockVO.getRangeTo());
            }
            newBlacklistStockVO.setStockHolderCode(stockVO.getStockHolderCode());
            newBlacklistStockVO.setLastUpdateTime(blacklistStockVO.getLastUpdateTime());
            newBlacklistStockVO.setBlacklistDate(blacklistStockVO.getBlacklistDate());
          } else {
            newBlacklistStockVO = blacklistStockVO;
          }
          if (rangesArray != null && rangesArray.size() > 0) {
            isManual = rangesArray.get(0).isManual();
          }
          newBlacklistStockVO.setStatus("A");
          if (rangesArray != null && rangesArray.size() > 0) {
            newBlacklistStockVO.setManual(rangesArray.get(0).isManual());
          }
          newBlacklistStockVO.setRemarks(blacklistStockVO.getRemarks());
          findAndSetStockHolderAppovers(newBlacklistStockVO);
          //  audit event should be implemented
          if (systemParameters.isEnableConfirmationProcess()) {
            blackListMissingStockHandler.blacklistMissingStock(
                blacklistStockMapper.clone(newBlacklistStockVO));
          }
          log.info(
              "New BlacklistStockVO: rangeFrom: {}, rangeTo: {}",
              newBlacklistStockVO.getRangeFrom(),
              newBlacklistStockVO.getRangeTo());
          var blacklistStocks = stockDao.findBlacklistRanges(newBlacklistStockVO);
          var rearrangeRanges =
              rearrangeRangesHelper.rearrangeRanges(blacklistStocks, newBlacklistStockVO);
          for (StockVO stockVo : rearrangeRanges) {
            var stockFilterVO =
                StockFilterVO.builder()
                    .companyCode(stockVo.getCompanyCode())
                    .stockHolderCode(stockVO.getStockHolderCode())
                    .build();
            stockFilterValidator.validate(stockFilterVO);
            var rangesArr = new ArrayList<>(stockVo.getRanges());
            var stockAllocationVo =
                buildStockAllocationVO(blacklistStockVO, isManual, stockVo, rangesArr);
            stockHolderHandler.deplete(stockAllocationVo, true);
            blacklistStockVO.setStockHolderCode(stockVo.getStockHolderCode());
            var blacklistVO = buildBlacklistStockVO(blacklistStockVO, stockVo, rangesArr);
            findAndSetStockHolderAppovers(blacklistVO);
            blackListStockPersistor.persist(blacklistVO);
            stockDetailsHandler.createStockHolderStockDetails(
                stockAllocationVo, systemParameters.getAccountingFlag(), "B");
            isStockHolder = true;
            if (systemParameters.isEnableStockHistory()) {
              stockAllocationVo.setTransactionCode(MODE_BLACKLIST);
              createHistoryInvoker.invoke(stockAllocationVo);
            }
          }
          if (!isStockHolder) {
            blacklistStockVO.setStockHolderCode(" ");
          }
        }
        blackListHistoryPersistor.createBlacklistHistoryForUsedRanges(blacklistStockVO);
      }
    } else {
      throw new StockBusinessException(
          constructErrorVO(NEO_STOCK_020.getErrorCode(), NEO_STOCK_020.getErrorMessage(), ERROR));
    }
  }

  private StockAllocationVO buildStockAllocationVO(
      BlacklistStockVO blacklistStockVO,
      boolean isManual,
      StockVO stockVo,
      ArrayList<RangeVO> rangesArr) {
    var stockAllocationVo = new StockAllocationVO();
    stockAllocationVo.setCompanyCode(stockVo.getCompanyCode());
    stockAllocationVo.setStockHolderCode(stockVo.getStockHolderCode());
    stockAllocationVo.setStockControlFor(stockVo.getStockHolderCode());
    stockAllocationVo.setDocumentType(blacklistStockVO.getDocumentType());
    stockAllocationVo.setDocumentSubType(blacklistStockVO.getDocumentSubType());
    stockAllocationVo.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
    stockAllocationVo.setTransferMode(StockConstant.MODE_RETURN);
    stockAllocationVo.setRemarks(blacklistStockVO.getRemarks());
    stockAllocationVo.setManual(isManual);
    var rangeVo = new RangeVO();
    rangeVo.setStartRange(rangesArr.get(0).getStartRange());
    rangeVo.setEndRange(rangesArr.get(0).getEndRange());
    rangeVo.setBlackList(true);
    stockAllocationVo.setRanges(List.of(rangeVo));
    stockAllocationVo.setManual(rangesArr.get(0).isManual());
    return stockAllocationVo;
  }

  private BlacklistStockVO buildBlacklistStockVO(
      BlacklistStockVO blacklistStockVO, StockVO stockVo, ArrayList<RangeVO> rangesArr) {
    var blacklistVO = new BlacklistStockVO();
    blacklistVO.setCompanyCode(stockVo.getCompanyCode());
    blacklistVO.setStockHolderCode(stockVo.getStockHolderCode());
    blacklistVO.setDocumentType(blacklistStockVO.getDocumentType());
    blacklistVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
    blacklistVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
    blacklistVO.setRangeFrom(rangesArr.get(0).getStartRange());
    blacklistVO.setRangeTo(rangesArr.get(0).getEndRange());
    blacklistVO.setLastUpdateTime(blacklistStockVO.getLastUpdateTime());
    blacklistVO.setLastUpdateUser(blacklistStockVO.getLastUpdateUser());
    blacklistVO.setStatus("A");
    blacklistVO.setManual(rangesArr.get(0).isManual());
    blacklistVO.setBlacklistDate(ZonedDateTime.now());
    blacklistVO.setRemarks(blacklistStockVO.getRemarks());
    return blacklistVO;
  }

  private void findAndSetStockHolderAppovers(BlacklistStockVO blacklistStockVO)
      throws StockBusinessException {
    if (blacklistStockVO != null) {
      StockAllocationVO stockAllocationVO = new StockAllocationVO();
      stockAllocationVO.setCompanyCode(blacklistStockVO.getCompanyCode());
      stockAllocationVO.setDocumentType(blacklistStockVO.getDocumentType());
      stockAllocationVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
      stockAllocationVO.setStockHolderCode(blacklistStockVO.getStockHolderCode());
      stockAllocationVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
      var stk = findStockWithRanges(stockAllocationVO);
      blacklistStockVO.setFirstLevelStockHolder(stk.getStockApproverCode());
    }
  }

  private StockVO findStockWithRanges(StockAllocationVO stockAllocationVO)
      throws StockBusinessException {
    var stockVO =
        stockDao.findStockWithRanges(
            stockAllocationVO.getCompanyCode(),
            stockAllocationVO.getStockHolderCode(),
            stockAllocationVO.getAirlineIdentifier(),
            stockAllocationVO.getDocumentType(),
            stockAllocationVO.getDocumentSubType());
    if (stockVO == null) {
      throw new StockBusinessException(
          constructErrorVO(NEO_STOCK_003.getErrorCode(), NEO_STOCK_003.getErrorMessage(), ERROR));
    }
    return stockVO;
  }
}
